/*
 * Datos_consultaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */
package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Via_ingreso_consultas;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.ContratosDao;
import healthmanager.modelo.dao.Datos_consultaDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.Parametro_codigo_consultaDao;
import healthmanager.modelo.dao.PrestadoresDao;
import healthmanager.modelo.dao.ProcedimientosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.service.FacturacionService;

@Service("datos_consultaService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Datos_consultaService implements Serializable {

    @Autowired
    private Datos_consultaDao datos_consultaDao;
    @Autowired
    private PrestadoresDao prestadoresDao;
    @Autowired
    private FacturacionService facturacionService;

	// private static Logger log = Logger.getLogger(Datos_consultaAction.class);
    public void guardar(Map<String, Object> datos) {

        List<Datos_consulta> lista_consulta = (List<Datos_consulta>) datos
                .get("lista_consulta");
        String accion = (String) datos.get("accion");

        // log.info("lista_consulta---->" + lista_consulta);
        for (Datos_consulta datos_consulta : lista_consulta) {
            if (accion.equalsIgnoreCase("registrar")) {
                crearRegistro(datos_consulta);
                // log.info("datos_consulta" + datos_consulta);
            } else {
                actualizarRegistro(datos_consulta);
                // log.info("datos_consulta_actualizar" + datos_consulta);
            }
        }
        if (!lista_consulta.isEmpty()) {
            Datos_consulta datos_consulta = lista_consulta.get(0);
            facturacionService.actualizarFacturaAutomatico(
                    datos_consulta.getCodigo_empresa(),
                    datos_consulta.getCodigo_sucursal(),
                    datos_consulta.getNro_ingreso(),
                    datos_consulta.getNro_identificacion());
        }
    }

    public void guardarDatosConsultasAut(Map<String, Object> map) {
        try {
            String codigo_empresa = (String) map.get("codigo_empresa");
            String codigo_sucursal = (String) map.get("codigo_sucursal");
            String nro_identificacion = (String) map.get("nro_identificacion");
            String nro_ingreso = (String) map.get("nro_ingreso");
            String codigo_prestador = (String) map.get("codigo_prestador");
            String codigo_dx = (String) map.get("codigo_dx");
            Long codigo_registro = (Long) map.get("codigo_registro");
            Timestamp fecha_consulta = (Timestamp) map.get("fecha_consulta");
            Timestamp creacion_date = (Timestamp) map.get("creacion_date");
            Timestamp ultimo_update = (Timestamp) map.get("ultimo_update");
            String creacion_user = (String) map.get("creacion_user");
            String ultimo_user = (String) map.get("ultimo_user");
            String tipo_hc = (String) map.get("tipo_hc");
            Manuales_tarifarios manuales_tarifarios = (Manuales_tarifarios) map
                    .get("manuales_tarifarios");
            Admision admision = (Admision) map.get("admision");

            String finalidad_consulta = (String) map.get("finalidad_consulta");
            String tipo_diasnostico = (String) map.get("tipo_diasnostico");
            // String codigo_pyp = (String) map.get("codigo_pyp");
            String codigo_programa = (String) map.get("codigo_programa");
            String causa_externa = (String) map.get("causa_externa");
            String codigo_consulta = (String) map.get("codigo_consulta");
            Prestadores prestadores = (Prestadores) map.get("prestadores");

            Double valor_consulta_paquete = (Double) map
                    .get("valor_consulta_paquete");

            String tipo_identificacion = (admision.getPaciente() != null ? admision
                    .getPaciente().getTipo_identificacion() : "CC");
            // String sexo = paciente != null ? paciente.getSexo() : "M";
            if (prestadores == null) {
                prestadores = new Prestadores();
                prestadores.setCodigo_empresa(codigo_empresa);
                prestadores.setCodigo_sucursal(codigo_sucursal);
                prestadores.setNro_identificacion(codigo_prestador);
                prestadores = prestadoresDao.consultar(prestadores);
            }

            if (prestadores == null) {
                throw new ValidacionRunTimeException(
                        "EL usuario que esta en la session no es un prestador apto para realizar historias clinicas");
            }

            /* Esto me permite cargar la consulta que voy a cobrar */
            if (codigo_consulta == null) {
                codigo_consulta = getCodigoConsulta(admision,
                        prestadores.getTipo_prestador());
            }

            double valor = 0;
            double valor_real = 0;
            double descuento = 0, incremento = 0;

            Map<String, Object> mapa_pcd = Utilidades.getProcedimiento(
                    manuales_tarifarios, new Long(codigo_consulta),
                    ServiciosDisponiblesUtils.getServiceLocator());

            valor = (Double) mapa_pcd.get("valor_pcd");
            valor_real = (Double) mapa_pcd.get("valor_real");
            String codigo_cups = (String) mapa_pcd.get("codigo_cups");

            boolean cups_contratado = ServiciosDisponiblesUtils
                    .getCupsContratado(codigo_cups, admision.getVia_ingreso(),
                            manuales_tarifarios);

			// log.info("cups_contratado [" + codigo_consulta + "] ===> ("
            // + cups_contratado + ")");
            if (!cups_contratado) {
                throw new ValidacionRunTimeException(
                        "EL procedimiento con codigo cups "
                        + codigo_consulta
                        + " no se encuentra contratado para este paciente");
            }

            Datos_consulta datos_consulta = new Datos_consulta();
            datos_consulta.setCodigo_empresa(codigo_empresa);
            datos_consulta.setCodigo_sucursal(codigo_sucursal);
            datos_consulta.setCodigo_registro(codigo_registro);
            datos_consulta.setTipo_identificacion(tipo_identificacion);
            datos_consulta.setNro_identificacion(nro_identificacion);
            datos_consulta
                    .setCodigo_administradora((admision != null ? admision
                            .getCodigo_administradora() : ""));
            datos_consulta.setId_plan((admision != null ? admision.getId_plan()
                    : ""));
            datos_consulta.setCodigo_prestador(codigo_prestador);
            datos_consulta.setNro_ingreso(nro_ingreso);
            datos_consulta.setCodigo_consulta((codigo_consulta));
            datos_consulta.setFecha_consulta(fecha_consulta);
            datos_consulta.setNumero_autorizacion("");
            datos_consulta
                    .setValor_consulta(valor_consulta_paquete != null ? valor_consulta_paquete
                            : valor);
            datos_consulta.setValor_cuota(0.0);
            datos_consulta
                    .setValor_neto(valor_consulta_paquete != null ? valor_consulta_paquete
                            : valor);
            datos_consulta.setFinalidad_consulta(finalidad_consulta);
            datos_consulta.setCausa_externa(causa_externa);
            datos_consulta.setTipo_diagnostico(tipo_diasnostico);
            datos_consulta.setCodigo_diagnostico_principal(codigo_dx);
            datos_consulta.setCodigo_diagnostico1("");
            datos_consulta.setCodigo_diagnostico2("");
            datos_consulta.setCodigo_diagnostico3("");
            datos_consulta.setCancelo_copago("N");
            datos_consulta.setCreacion_date(creacion_date);
            datos_consulta.setUltimo_update(ultimo_update);
            datos_consulta.setCreacion_user(creacion_user);
            datos_consulta.setUltimo_user(ultimo_user);
            datos_consulta
                    .setValor_real(valor_consulta_paquete != null ? valor_consulta_paquete
                            : valor_real);
            datos_consulta.setDescuento(valor_consulta_paquete != null ? 0.0
                    : descuento);
            datos_consulta.setIncremento(valor_consulta_paquete != null ? 0.0
                    : incremento);
            datos_consulta.setCodigo_orden("");
            datos_consulta.setTipo_hc(tipo_hc);
            datos_consulta.setCodigo_programa(codigo_programa);
			// log.info(datos_consulta.getCodigo_programa().equals("")
            // && !datos_consulta.getFinalidad_consulta().equals("10"));
            if (datos_consulta.getCodigo_programa().equals("")
                    && !datos_consulta.getFinalidad_consulta().equals("10")) {
                datos_consulta.setCodigo_programa(datos_consulta
                        .getFinalidad_consulta());
            }
            crearRegistro(datos_consulta);
        } catch (ValidacionRunTimeException e) {
            throw new ValidacionRunTimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public String getCodigoConsulta(Admision admision, String tipo_prestador) {
        Via_ingreso_consultas via_ingreso_consultas = ServiciosDisponiblesUtils
                .getVia_ingreso_consultas(admision);
        String codigo_consulta = "";
		// SI EL PRESTADOR ES DE TIPO 01 ENTONCES ES MEDICO Y DEBE FACTURAR
        // LA CONSULTA DE MEDICO
        if (tipo_prestador.equalsIgnoreCase("01")) {
            codigo_consulta = admision.getPrimera_vez().equals("S") ? via_ingreso_consultas
                    .getPro_consulta_primera() : via_ingreso_consultas
                    .getPro_consulta_control();
			// SI EL PRESTADOR ES DE TIPO 02 ENTONCES ES ENFERMERA Y DEBE
            // FACTURAR LA CONSULTA DE ENFERMERIA
        } else if (tipo_prestador.equalsIgnoreCase("02")) {
            codigo_consulta = admision.getPrimera_vez().equals("S") ? via_ingreso_consultas
                    .getPro_enfermeria_primera() : via_ingreso_consultas
                    .getPro_enfermeria_control();
        }
        if (admision.getVia_ingreso().equals(IVias_ingreso.PSICOLOGIA)
                && admision.getTipo_psicologia().equals("02")) {
            codigo_consulta = ServiciosDisponiblesUtils.CODIGO_CUPS_ASESORIA_PSICOLOGIA;
        }
        return codigo_consulta;
    }

    public void crearRegistro(Datos_consulta datos_consulta) {
        try {
            datos_consultaDao.crear(datos_consulta);
        } catch (Exception e) {
            datos_consulta.setCodigo_registro(null);
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void crearActualizarFactura(
            Datos_consulta datos_consulta) {
        try {
            datos_consultaDao.crear(datos_consulta);
            facturacionService.actualizarFacturaAutomatico(
                    datos_consulta.getCodigo_empresa(),
                    datos_consulta.getCodigo_sucursal(),
                    datos_consulta.getNro_ingreso(),
                    datos_consulta.getNro_identificacion());
        } catch (Exception e) {
            datos_consulta.setCodigo_registro(null);
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void crearLote(List<Datos_consulta> listaDatos_consultas) {
        try {
            for (Datos_consulta datos_consulta : listaDatos_consultas) {
                crearRegistro(datos_consulta);
            }
            if (!listaDatos_consultas.isEmpty()) {
                Datos_consulta datos_consulta = listaDatos_consultas.get(0);
                facturacionService.actualizarFacturaAutomatico(
                        datos_consulta.getCodigo_empresa(),
                        datos_consulta.getCodigo_sucursal(),
                        datos_consulta.getNro_ingreso(),
                        datos_consulta.getNro_identificacion());
            }
        } catch (ValidacionRunTimeException e) {
            throw new ValidacionRunTimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int actualizarRegistro(Datos_consulta datos_consulta) {
        try {
            int result = datos_consultaDao.actualizar(datos_consulta);
            return result;
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int actualizarActualizarFactura(Datos_consulta datos_consulta) {
        try {
            int result = datos_consultaDao.actualizar(datos_consulta);
            facturacionService.actualizarFacturaAutomatico(
                    datos_consulta.getCodigo_empresa(),
                    datos_consulta.getCodigo_sucursal(),
                    datos_consulta.getNro_ingreso(),
                    datos_consulta.getNro_identificacion());
            return result;
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Datos_consulta consultar(Datos_consulta datos_consulta) {
        try {
            return datos_consultaDao.consultar(datos_consulta);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminarActualizarFactura(Datos_consulta datos_consulta) {
        try {
            int result = datos_consultaDao.eliminar(datos_consulta);
            facturacionService.actualizarFacturaAutomatico(
                    datos_consulta.getCodigo_empresa(),
                    datos_consulta.getCodigo_sucursal(),
                    datos_consulta.getNro_ingreso(),
                    datos_consulta.getNro_identificacion());
            return result;
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminarRegistro(Datos_consulta datos_consulta) {
        try {
            int result = datos_consultaDao.eliminar(datos_consulta);
            return result;
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<Datos_consulta> listarTabla(Map<String, Object> parameter) {
        try {
            return datos_consultaDao.listarTabla(parameter);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<Datos_consulta> listarResultados(Map<String, Object> parameter) {
        try {
            return datos_consultaDao.listarResultados(parameter);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Integer totalResultados(Map<String, Object> parametros) {
        try {
            return datos_consultaDao.totalResultados(parametros);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Datos_consultaDao getDatos_consultaDao() {
        return datos_consultaDao;
    }

    public boolean existe(Map<String, Object> parameters) {
        return datos_consultaDao.existe(parameters);
    }

    public Map<String, Object> getFechaRealizacion(Map<String, Object> map) {
        return datos_consultaDao.getFechaRealizacion(map);
    }

}
