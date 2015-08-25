/*
 * ProcedimientoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */
package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Anio_soat;
import healthmanager.modelo.bean.Descuentos_laboratorios;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_procedimientos;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Tarifas_procedimientos;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.dao.Descuentos_laboratoriosDao;
import healthmanager.modelo.dao.Maestro_manualDao;
import healthmanager.modelo.dao.Manuales_procedimientosDao;
import healthmanager.modelo.dao.Manuales_tarifariosDao;
import healthmanager.modelo.dao.ProcedimientosDao;
import healthmanager.modelo.dao.Tarifas_procedimientosDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("procedimientosService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProcedimientosService implements Serializable {

    private String limit;

    @Autowired
    private ProcedimientosDao procedimientosDao;

    @Autowired
    private GeneralExtraService generalExtraService;

    @Autowired
    private Descuentos_laboratoriosDao descuentos_laboratoriosDao;
    @Autowired
    private Tarifas_procedimientosDao tarifas_procedimientosDao;
    @Autowired
    private Manuales_procedimientosDao manuales_procedimientosDao;
    @Autowired
    private Maestro_manualDao maestro_manualDao;
    @Autowired
    private Manuales_tarifariosDao manuales_tarifariosDao;

    public void actualizarProcedimientosInformacion(
            List<Object> listado_procedimientos) {
        try {
            for (Object dato : listado_procedimientos) {
                if (dato instanceof Procedimientos) {
                    procedimientosDao.actualizar((Procedimientos) dato);
                }
            }
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void crear(Procedimientos procedimientos) {
        try {
            if (consultar(procedimientos) != null) {
                throw new ValidacionRunTimeException(
                        "Procedimiento ya se encuentra en la base de datos");
            }
            procedimientosDao.crear(procedimientos);
        } catch (ValidacionRunTimeException e) {
            throw new ValidacionRunTimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int actualizar(Procedimientos procedimientos) {
        try {
            return procedimientosDao.actualizar(procedimientos);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Procedimientos consultar(Procedimientos procedimientos) {
        try {
            return procedimientosDao.consultar(procedimientos);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminar(Procedimientos procedimientos) {
        try {
            return procedimientosDao.eliminar(procedimientos);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<Procedimientos> listar(Map<String, Object> parameter) {
        try {
            parameter.put("limit", limit);
            return procedimientosDao.listar(parameter);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<String> listar_cups(Map<String, Object> parametros) {
        try {
            return procedimientosDao.listar_cups(parametros);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Long consultarIDPorCups(String codigos_cups) {
        try {
            Procedimientos procedimientos = new Procedimientos();
            procedimientos.setCodigo_cups(codigos_cups);
            return procedimientosDao.consultarIDPorCups(procedimientos);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int actualizarPorDmanda(Procedimientos procedimientos) {
        try {
            return procedimientosDao.actualizarPorDmanda(procedimientos);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Map<String, Object> getProcedimiento(Manuales_tarifarios manual,
            Long id_procedimiento) throws Exception {
        Map<String, Object> bean = new HashMap<String, Object>();

        String nombre_procedimiento = "";
        String sexo_pcd = "A";
        String limite_inferior_pcd = "0";
        String limite_superior_pcd = "0";
        String es_pyp = "N";
        String grupo_uvr = "";
        String codigo_plantilla = "";
        String tipo_procedimiento = "";
        Double valor_porcentaje = 0D;
        double valor_pcd = 0;
        String codigo_cups = "";
        String consulta = "";
        String quirurgico = "";

        Manuales_procedimientos manuales_procedimientos = new Manuales_procedimientos();
        manuales_procedimientos.setId_manual(manual.getId_maestro_manual());
        manuales_procedimientos.setId_procedimiento(id_procedimiento);

        manuales_procedimientos = manuales_procedimientosDao
                .consultar(manuales_procedimientos);

        if (manuales_procedimientos != null) {
            Maestro_manual maestro_manual = new Maestro_manual();
            maestro_manual.setId_manual(manual.getId_maestro_manual());
            maestro_manual = maestro_manualDao.consultar(maestro_manual);

            Procedimientos proc = new Procedimientos();
            proc.setId_procedimiento(id_procedimiento);
            proc = procedimientosDao.consultar(proc);
            nombre_procedimiento = (proc != null ? proc.getDescripcion() : "");
            sexo_pcd = (proc != null ? proc.getSexo() : "");
            limite_inferior_pcd = proc.getLimite_inferior();
            limite_superior_pcd = proc.getLimite_superior();
            es_pyp = (proc != null ? proc.getPyp() : "");
            codigo_plantilla = (proc != null ? proc.getCodigo_contabilidad()
                    : "");
            tipo_procedimiento = (proc != null ? proc.getTipo_procedimiento()
                    : "");
            valor_porcentaje = manuales_procedimientos.getValor();
            codigo_cups = proc != null ? proc.getCodigo_cups() : "";
            consulta = proc != null ? proc.getConsulta() : "";
            quirurgico = proc != null ? proc.getQuirurgico() : "";

            if (maestro_manual.getTipo_manual().equals(
                    IConstantes.TIPO_MANUAL_SOAT)) {
                Anio_soat anio_soat = new Anio_soat();
                anio_soat.setAnio(manual.getAnio());
                anio_soat = generalExtraService.consultar(anio_soat);
                valor_pcd = (anio_soat != null ? (int) (anio_soat
                        .getValor_anio() * valor_porcentaje) : 0);
            } else if (maestro_manual.getTipo_manual().equals(
                    IConstantes.TIPO_MANUAL_ISS01)
                    || maestro_manual.getTipo_manual().equals(
                            IConstantes.TIPO_MANUAL_ISSEXT)) {
                valor_pcd = manuales_procedimientos.getValor();
            } else if (maestro_manual.getTipo_manual().equals(
                    IConstantes.TIPO_MANUAL_ISS04)) {
                valor_pcd = manuales_procedimientos != null ? manuales_procedimientos
                        .getValor() * 100 : 0;
            }

            double valor_real = valor_pcd;
            double descuento = 0, incremento = 0;
            if (manual != null) {
                if (proc.getConsulta().equalsIgnoreCase("S")) {// Cuando es una
                    // consulta
                    if (manual.getTipo_consulta().equalsIgnoreCase("01")) {
                        descuento = (int) (valor_pcd * (manual.getConsulta() / 100));
                        valor_pcd -= descuento;
                    } else if (manual.getTipo_consulta().equalsIgnoreCase("02")) {
                        incremento = (int) (valor_pcd * (manual.getConsulta() / 100));
                        valor_pcd += incremento;
                    }
                } else {// Procedimiento
                    if (manual.getTipo_general().equalsIgnoreCase("01")) {
                        descuento = (int) (valor_pcd * (manual.getGeneral() / 100));
                        valor_pcd -= descuento;
                    } else if (manual.getTipo_general().equalsIgnoreCase("02")) {
                        incremento = (int) (valor_pcd * (manual.getGeneral() / 100));
                        valor_pcd += incremento;
                    }
                }

                // si se manejan tarifas especiales
                // va ha tomar dicho valor
                if (manual.getTarifa_especial().equalsIgnoreCase("S")
                        && proc.getQuirurgico().equalsIgnoreCase("N")) {
                    Tarifas_procedimientos tarifas_procedimientos = new Tarifas_procedimientos();
                    tarifas_procedimientos.setCodigo_empresa(manual
                            .getCodigo_empresa());
                    tarifas_procedimientos.setCodigo_sucursal(manual
                            .getCodigo_sucursal());
                    tarifas_procedimientos.setCodigo_administradora(manual
                            .getCodigo_administradora());
                    tarifas_procedimientos.setId_plan(manual.getId_contrato());
                    tarifas_procedimientos
                            .setId_procedimiento(id_procedimiento);
                    tarifas_procedimientos.setConsecutivo_manual(manual
                            .getConsecutivo());
                    tarifas_procedimientos = tarifas_procedimientosDao
                            .consultar(tarifas_procedimientos);

                    if (tarifas_procedimientos != null) {
                        if (!tarifas_procedimientos.getDescripcion().isEmpty()) {
                            nombre_procedimiento = tarifas_procedimientos
                                    .getDescripcion();
                        }
                        valor_pcd = tarifas_procedimientos.getValor();
                    }
                }

                if (manual.getAplica_descuentos().equalsIgnoreCase("S")) {
                    // Incluimos los descuentos
                    Descuentos_laboratorios descuentos_laboratorios = new Descuentos_laboratorios();
                    descuentos_laboratorios.setCodigo_empresa(manual
                            .getCodigo_empresa());
                    descuentos_laboratorios.setCodigo_sucursal(manual
                            .getCodigo_sucursal());
                    descuentos_laboratorios.setCodigo_administradora(manual
                            .getCodigo_administradora());
                    descuentos_laboratorios.setId_contrato(manual
                            .getId_contrato());
                    descuentos_laboratorios.setConsecutivo_manual(manual
                            .getConsecutivo());
                    descuentos_laboratorios.setCodigo_procedimiento(proc
                            .getId_procedimiento() + "");
                    descuentos_laboratorios = descuentos_laboratoriosDao
                            .consultar(descuentos_laboratorios);
                    if (descuentos_laboratorios != null) {
                        descuento = valor_pcd
                                * descuentos_laboratorios
                                .getPorcentaje_descuento() / 100d;
                        valor_pcd = valor_pcd - descuento;
                    }
                }
            }

            grupo_uvr = manuales_procedimientos.getGrupo_uvr();

            bean.put("nombre_procedimiento", nombre_procedimiento);
            bean.put("sexo_pcd", sexo_pcd);
            bean.put("limite_inferior_pcd", limite_inferior_pcd);
            bean.put("limite_superior_pcd", limite_superior_pcd);
            bean.put("es_pyp", es_pyp);
            bean.put("grupo_uvr", grupo_uvr);
            bean.put("codigo_plantilla", codigo_plantilla);
            bean.put("tipo_procedimiento", tipo_procedimiento);
            bean.put("valor_porcentaje", valor_porcentaje);
            bean.put("valor_pcd", valor_pcd);
            bean.put("descuento", descuento);
            bean.put("incremento", incremento);
            bean.put("valor_real", valor_real);
            bean.put("codigo_cups", codigo_cups);
            bean.put("codigo_manual",
                    manuales_procedimientos.getCodigo_manual());
            bean.put("id_procedimiento", id_procedimiento);
            bean.put("consulta", consulta);
            bean.put("quirurgico", quirurgico);
            bean.put("cantidad_maxima",
                    proc != null ? proc.getCantidad_maxima() : 0);
        } else {
            Procedimientos procedimientos = new Procedimientos();
            procedimientos.setId_procedimiento(id_procedimiento);
            procedimientos = procedimientosDao.consultar(procedimientos);

            throw new ValidacionRunTimeException(
                    "El procedimiento "
                    + (procedimientos != null ? procedimientos
                    .getCodigo_cups()
                    + " "
                    + procedimientos.getDescripcion()
                    : id_procedimiento)
                    + " que intenta consultar no se encuentra relacionado con el manual tarifario en cuestion");
        }

        return bean;

    }

    public Map<String, Object> getProcedimiento(
            Via_ingreso_contratadas via_ingreso_contratadas,
            Long id_procedimiento) throws Exception {

        Manuales_tarifarios manuales_tarifarios = new Manuales_tarifarios();
        manuales_tarifarios.setConsecutivo(via_ingreso_contratadas
                .getConsecutivo_manual());
        manuales_tarifarios.setCodigo_empresa(via_ingreso_contratadas
                .getCodigo_empresa());
        manuales_tarifarios.setCodigo_sucursal(via_ingreso_contratadas
                .getCodigo_sucursal());
        manuales_tarifarios.setCodigo_administradora(via_ingreso_contratadas
                .getCodigo_administradora());
        manuales_tarifarios
                .setId_contrato(via_ingreso_contratadas.getId_plan());
        manuales_tarifarios = manuales_tarifariosDao
                .consultar(manuales_tarifarios);
        return getProcedimiento(manuales_tarifarios, id_procedimiento);

    }

}
