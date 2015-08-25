/*
 * FacturacionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */
package contaweb.modelo.service;

import healthmanager.controller.GlosasDevolucionesAction;
import healthmanager.controller.GlosasDevolucionesAction.EModeloGlosa;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Admision_cama;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Copago_estrato;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Datos_servicio;
import healthmanager.modelo.bean.Detalle_grupos_procedimientos;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.bean.Furips2;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Morbilidad_diagnosticos;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Paquetes_servicios;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.bean.Vacunas_pacientes;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.dao.AdministradoraDao;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Admision_camaDao;
import healthmanager.modelo.dao.CamaDao;
import healthmanager.modelo.dao.Centro_atencionDao;
import healthmanager.modelo.dao.ContratosDao;
import healthmanager.modelo.dao.Contratos_paquetesDao;
import healthmanager.modelo.dao.Copago_estratoDao;
import healthmanager.modelo.dao.Datos_consultaDao;
import healthmanager.modelo.dao.Datos_medicamentosDao;
import healthmanager.modelo.dao.Datos_procedimientoDao;
import healthmanager.modelo.dao.Datos_servicioDao;
import healthmanager.modelo.dao.Detalle_grupos_procedimientosDao;
import healthmanager.modelo.dao.Detalles_paquetes_serviciosDao;
import healthmanager.modelo.dao.Evolucion_medicaDao;
import healthmanager.modelo.dao.Facturacion_medicamentoDao;
import healthmanager.modelo.dao.Facturacion_servicioDao;
import healthmanager.modelo.dao.Furips2Dao;
import healthmanager.modelo.dao.FuripsDao;
import healthmanager.modelo.dao.Grupos_procedimientosDao;
import healthmanager.modelo.dao.HospitalizacionDao;
import healthmanager.modelo.dao.Impresion_diagnosticaDao;
import healthmanager.modelo.dao.Manuales_tarifariosDao;
import healthmanager.modelo.dao.Morbilidad_diagnosticosDao;
import healthmanager.modelo.dao.Orden_servicioDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.Receta_ripsDao;
import healthmanager.modelo.dao.Recien_nacidoDao;
import healthmanager.modelo.dao.ResolucionDao;
import healthmanager.modelo.dao.Servicios_contratadosDao;
import healthmanager.modelo.dao.Solicitud_medicamentoDao;
import healthmanager.modelo.dao.UrgenciasDao;
import healthmanager.modelo.dao.VacunasDao;
import healthmanager.modelo.dao.Vacunas_pacientesDao;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Datos_consultaService;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.Facturacion_medicamentoService;
import healthmanager.modelo.service.Facturacion_servicioService;
import healthmanager.modelo.service.Grupos_iss01Service;
import healthmanager.modelo.service.Grupos_quirurgicosService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Tarifas_procedimientosService;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesGlosas;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.constantes.ITiposServicio;
import com.framework.macros.facturacion.ServiciosFacturacionMacro;
import com.framework.res.L2HContraintDate;
import com.framework.res.LineStringToList;
import com.framework.res.Res;
import com.framework.util.ConvertidorRipAObjeto;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Bodega;
import contaweb.modelo.bean.Bodega_articulo;
import contaweb.modelo.bean.Caja;
import contaweb.modelo.bean.Cartera;
import contaweb.modelo.bean.Centro_costo;
import contaweb.modelo.bean.Comprobante;
import contaweb.modelo.bean.Detalle_caja;
import contaweb.modelo.bean.Detalle_contabilizacion;
import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Detalle_nota;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Impuesto;
import contaweb.modelo.bean.Nota_contable;
import contaweb.modelo.bean.Puc;
import contaweb.modelo.bean.Resolucion;
import contaweb.modelo.bean.Rips_ad;
import contaweb.modelo.bean.Rips_ah;
import contaweb.modelo.bean.Rips_an;
import contaweb.modelo.bean.Rips_au;
import contaweb.modelo.bean.Rips_us;
import contaweb.modelo.bean.Tarifa_medicamento;
import contaweb.modelo.bean.Tercero;
import contaweb.modelo.dao.ArticuloDao;
import contaweb.modelo.dao.Bodega_articuloDao;
import contaweb.modelo.dao.CajaDao;
import contaweb.modelo.dao.CarteraDao;
import contaweb.modelo.dao.Centro_costoDao;
import contaweb.modelo.dao.ComprobanteDao;
import contaweb.modelo.dao.Detalle_cajaDao;
import contaweb.modelo.dao.Detalle_contabilizacionDao;
import contaweb.modelo.dao.Detalle_facturaDao;
import contaweb.modelo.dao.Detalle_notaDao;
import contaweb.modelo.dao.FacturacionDao;
import contaweb.modelo.dao.ImpuestoDao;
import contaweb.modelo.dao.Nota_contableDao;
import contaweb.modelo.dao.PucDao;
import contaweb.modelo.dao.TerceroDao;

@Service("facturacionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FacturacionService implements Serializable {

	private static final long serialVersionUID = 3745728908547654982L;

	private static Logger log = Logger.getLogger(FacturacionService.class);

	@Autowired
	private FacturacionDao facturacionDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	@Autowired
	private healthmanager.modelo.service.ConsecutivoService consecutivoHealthmanagerService;

	@Autowired
	private Detalle_facturaDao detalleFacturaDao;

	@Autowired
	private ProcedimientosService procedimientosService;
	@Autowired
	private Tarifas_procedimientosService tarifas_procedimientosService;

	@Autowired
	private ContratosDao contratosDao;
	@Autowired
	private PacienteDao pacienteDao;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private AdmisionService admisionService;
	@Autowired
	private Admision_camaDao admision_camaDao;

	@Autowired
	private Grupos_quirurgicosService grupos_quirurgicosService;
	@Autowired
	private Grupos_iss01Service grupos_iss01Service;
	@Autowired
	private Datos_medicamentosDao datos_medicamentosDao;
	@Autowired
	private Datos_servicioDao datos_servicioDao;
	@Autowired
	private ArticuloDao articuloDao;
	@Autowired
	private Copago_estratoDao copago_estratoDao;

	@Autowired
	private Datos_consultaService datos_consultaService;
	@Autowired
	private Datos_procedimientoService datos_procedimientoService;
	@Autowired
	private Facturacion_medicamentoService facturacion_medicamentoService;
	@Autowired
	private Facturacion_servicioService facturacion_servicioService;
	@Autowired
	private ResolucionDao resolucionDao;
	@Autowired
	private contaweb.modelo.dao.ResolucionDao resolucionContDao;

	@Autowired
	private CajaService cajaService;
	@Autowired
	private Nota_contableDao nota_contableDao;
	@Autowired
	private Detalle_cajaDao detalle_cajaDao;
	@Autowired
	private TerceroDao terceroDao;
	@Autowired
	private ImpuestoDao impuestoDao;
	@Autowired
	private Detalle_contabilizacionDao detalle_contabilizacionDao;
	@Autowired
	private PucDao pucDao;
	@Autowired
	private Bodega_articuloDao bodega_articuloDao;
	@Autowired
	private Detalle_notaDao detalle_notaDao;
	@Autowired
	private CarteraDao carteraDao;

	@Autowired
	private CamaDao camaDao;
	@Autowired
	private Evolucion_medicaDao evolucion_medicaDao;
	@Autowired
	private Orden_servicioDao orden_servicioDao;
	@Autowired
	private FuripsDao furipsDao;
	@Autowired
	private Solicitud_medicamentoDao solicitud_medicamentoDao;
	@Autowired
	private Receta_ripsDao receta_ripsDao;
	@Autowired
	private UrgenciasDao urgenciasDao;
	@Autowired
	private HospitalizacionDao hospitalizacionDao;
	@Autowired
	private Recien_nacidoDao recien_nacidoDao;

	@Autowired
	private Datos_consultaDao datos_consultaDao;
	@Autowired
	private Datos_procedimientoDao datos_procedimientoDao;
	@Autowired
	private Facturacion_medicamentoDao facturacion_medicamentoDao;
	@Autowired
	private Facturacion_servicioDao facturacion_servicioDao;

	@Autowired
	private Impresion_diagnosticaDao impresion_diagnosticaDao;

	@Autowired
	private PacienteService pacienteService;

	@Autowired
	private ComprobanteDao comprobanteDao;
	@Autowired
	private CajaDao cajaDao;
	@Autowired
	private Furips2Dao furips2Dao;

	@Autowired
	private AdministradoraDao administradoraDao;

	@Autowired
	private AdministradoraService administradoraService;

	@Autowired
	private Morbilidad_diagnosticosDao morbilidad_diagnosticosDao;

	@Autowired
	private Rips_usService rips_usService;
	@Autowired
	private Rips_ahService rips_ahService;
	@Autowired
	private Rips_auService rips_auService;
	@Autowired
	private Rips_anService rips_anService;
	@Autowired
	private Rips_adService rips_adService;
	@Autowired
	private Tarifa_medicamentoService tarifa_medicamentoService;
	@Autowired
	private Manuales_tarifariosDao manuales_tarifariosDao;
	@Autowired
	private Grupos_procedimientosDao grupos_procedimientosDao;
	@Autowired
	private Detalle_grupos_procedimientosDao detalle_grupos_procedimientosDao;

	@Autowired
	private Servicios_contratadosDao servicios_contratadosDao;

	@Autowired
	private FacturacionClinicaCompraService facturacionClinicaCompraService;

	@Autowired
	private Centro_costoDao centro_costoDao;
	@Autowired
	private VacunasDao vacunasDao;
	@Autowired
	private Vacunas_pacientesDao vacunas_pacientesDao;
	@Autowired
	private Contratos_paquetesDao contratos_paquetesDao;
	@Autowired
	private Detalles_paquetes_serviciosDao detalles_paquetes_serviciosDao;

	@Autowired
	private Centro_atencionDao centro_atencionDao;

	private Double porcentaje_reclaculo = 0.0;

	public void crear(Facturacion facturacion) {
		try {
			facturacionDao.crear(facturacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Facturacion facturacion) {
		return facturacionDao.existe(facturacion);
	}

	public boolean existePorParametro(Map<String, Object> param) {
		return facturacionDao.existePorParametro(param);
	}

	public int actualizar(Facturacion facturacion) {
		try {
			return facturacionDao.actualizar(facturacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void actualizarAnulacionFacturaCapAgru(Facturacion facturacion) {
		try {
			actualizar(facturacion);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", facturacion.getCodigo_empresa());
			parametros.put("codigo_sucursal", facturacion.getCodigo_sucursal());
			parametros.put("id_factura", facturacion.getId_factura());
			parametros.put("codigo_comprobante",
					facturacion.getCodigo_comprobante());
			parametros.put("delete_user", facturacion.getDelete_user());

			int actualiza = anularFacturaAgrupadaCapitada(parametros);
			log.info("cantidad de registros ==> " + actualiza);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Facturacion consultar(Facturacion facturacion) {
		try {
			return facturacionDao.consultar(facturacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Facturacion consultar_informacion(Facturacion facturacion) {
		try {
			return facturacionDao.consultar_informacion(facturacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Facturacion facturacion) {
		try {
			return facturacionDao.eliminar(facturacion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Facturacion> listarRegistros(Map<String, Object> parametros) {
		try {
			return facturacionDao.listarRegistros(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Facturacion> listarFacturacion_rips(
			Map<String, Object> parametros) {
		try {
			return facturacionDao.listarFacturacion_rips(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer totalResultados(Map<String, Object> parameters) {
		try {
			return facturacionDao.totalResultados(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Map<String, Object> consultarAdmisionFactura(Admision aux2,
			Timestamp fecha, List<Detalle_factura> lista_detalle_externo,
			Via_ingreso_contratadas via_ingreso_contratadas, String accion) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Paciente paciente = null;
			if (aux2.getPaciente() != null) {
				paciente = aux2.getPaciente();
			} else {
				paciente = new Paciente();
				paciente.setCodigo_empresa(aux2.getCodigo_empresa());
				paciente.setCodigo_sucursal(aux2.getCodigo_sucursal());
				paciente.setNro_identificacion(aux2.getNro_identificacion());
				paciente = pacienteDao.consultar(paciente);
			}

			Facturacion facturacion = new Facturacion();
			facturacion.setTipo_identificacion((paciente != null ? paciente
					.getTipo_identificacion() : ""));
			facturacion.setCodigo_administradora(aux2
					.getCodigo_administradora());
			facturacion.setId_plan(aux2.getId_plan());
			facturacion.setEstado("");
			facturacion.setFecha_inicio(aux2.getFecha_ingreso());
			facturacion.setNro_ingreso(aux2.getNro_ingreso());
			facturacion.setCodigo_tercero(aux2.getNro_identificacion());

			String servicios[] = ServiciosDisponiblesUtils.getServicios(aux2);

			String regimen = "2";
			String estrato = "";

			if (paciente != null) {
				regimen = paciente.getTipo_usuario();
				estrato = paciente.getEstrato();
			}

			List<Detalle_factura> lista_detalle = new ArrayList<Detalle_factura>();

			if (via_ingreso_contratadas == null) {
				throw new ValidacionRunTimeException(
						"No se puede culminar el proceso porque esta via de ingreso no se encuentra relacionada en el contrato seleccionado. Verificar los contratos");
			}

			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(via_ingreso_contratadas);

			double total = 0;
			// Cargamos las estancias de camas //
			Map<String, Object> result_lista = getLista_admisionCama(
					lista_detalle, via_ingreso_contratadas, aux2, fecha);
			total += ((Double) result_lista.get("total"));

			// Cargamos las consultas //
			result_lista = getLista_consultas(lista_detalle,
					manuales_tarifarios, aux2);
			total += ((Double) result_lista.get("total"));

			// Cargamos los procedimientos //
			result_lista = getLista_procedimientos(lista_detalle,
					manuales_tarifarios, aux2);
			total += ((Double) result_lista.get("total"));

			// Cargamos los medicamentos //
			result_lista = getLista_medicamentos(lista_detalle, aux2,
					lista_detalle_externo, accion, "01");
			total += ((Double) result_lista.get("total"));

			// Cargamos los insumos // //
			result_lista = getLista_medicamentos(lista_detalle, aux2,
					lista_detalle_externo, accion, "02");
			total += ((Double) result_lista.get("total"));

			// Cargamos los servicios //
			result_lista = getLista_servicios(lista_detalle, aux2);
			total += ((Double) result_lista.get("total"));

			Caja caja = new Caja();
			caja.setCodigo_empresa(aux2.getCodigo_empresa());
			caja.setCodigo_sucursal(aux2.getCodigo_sucursal());
			caja.setCodigo_tercero(aux2.getNro_identificacion());
			caja.setNro_ingreso(aux2.getNro_ingreso());
			double cop_canc = cajaService.totalCopagos(caja);
			caja = cajaService.consultar(caja);
			double dto = (caja != null ? caja.getDescuento() : 0.0);

			Copago_estrato ce = new Copago_estrato();
			ce.setCodigo_empresa(aux2.getCodigo_empresa());
			ce.setCodigo_sucursal(aux2.getCodigo_sucursal());
			ce.setEstrato(estrato);
			ce = copago_estratoDao.consultar(ce);
			if (ce == null) {
				ce = new Copago_estrato();
			}
			double valor_copago = (int) (ce.getPorcentaje() * total);
			if (ce.getValor_max_contrib() < valor_copago && regimen.equals("1")
					&& ce.getValor_max_contrib() > 0) {
				valor_copago = ce.getValor_max_contrib();
			} else if (ce.getValor_max_sub() < valor_copago
					&& regimen.equals("2") && ce.getValor_max_sub() > 0) {
				valor_copago = ce.getValor_max_sub();
			}

			if (!accion.equalsIgnoreCase("Modificar")) {
				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(aux2.getCodigo_empresa());
				contratos.setCodigo_sucursal(aux2.getCodigo_sucursal());
				contratos.setCodigo_administradora(aux2
						.getCodigo_administradora());
				contratos.setId_plan(aux2.getId_plan());
				contratos = contratosDao.consultar(contratos);
				boolean es_pyp = ServiciosDisponiblesUtils
						.isServicioPyp(servicios);
				if (contratos.getCobrar_copago().equalsIgnoreCase("S")
						&& !es_pyp) {
					facturacion.setNocopago("N");
					facturacion.setValor_copago(((int) (valor_copago)));
					facturacion.setValor_total((total));
					facturacion.setDto_copago(dto);
					facturacion.setCop_canc(cop_canc);
				} else {
					facturacion.setNocopago("S");
					facturacion.setValor_copago(0.0);
					facturacion.setValor_total(total);
					facturacion.setDto_copago(0.0);
					facturacion.setCop_canc(0.0);
				}

			} else {
				Facturacion fac = new Facturacion();
				fac.setCodigo_empresa(aux2.getCodigo_empresa());
				fac.setCodigo_sucursal(aux2.getCodigo_sucursal());
				fac.setNro_ingreso(aux2.getNro_ingreso());
				fac.setCodigo_tercero(aux2.getNro_identificacion());
				fac = facturacionDao.consultar_informacion(fac);

				if (fac.getNocopago().equals("S")) {
					facturacion.setNocopago("S");
					facturacion.setValor_copago(0.0);
					facturacion.setValor_total((fac.getValor_total()));
					facturacion.setDto_copago(0.0);
					facturacion.setCop_canc(0.0);
				} else {
					double copago_recalculado = (total * fac.getValor_copago())
							/ fac.getValor_total();
					facturacion.setNocopago("N");
					facturacion.setValor_copago(((int) copago_recalculado));
					facturacion.setValor_total(((int) (total)));
					facturacion.setDto_copago(fac.getDto_copago());
					facturacion.setCop_canc(fac.getCop_canc());
				}

				facturacion.setNro_contrato(fac.getNro_contrato());
				facturacion.setEstado(fac.getEstado());
				facturacion.setFecha_inicio(fac.getFecha_inicio());
			}

			map.put("lista_detalle", lista_detalle);
			map.put("facturacion_totales", facturacion);

			return map;
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private Map<String, Object> getLista_servicios(
			List<Detalle_factura> lista_detalle, Admision aux2)
			throws Exception {

		Map<String, Object> result_lista = new HashMap<String, Object>();
		result_lista.put("nocancelo", false);

		double total = 0.0;

		Map<String, Object> mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		List<Datos_servicio> lista_datos_sers = datos_servicioDao
				.listar(mapDatos);
		for (Datos_servicio elem : lista_datos_sers) {
			if (elem.getCancelo_copago().equals("N")) {
				result_lista.put("nocancelo", true);
			}

			Articulo pd = new Articulo();
			pd.setCodigo_empresa(elem.getCodigo_empresa());
			pd.setCodigo_sucursal(elem.getCodigo_sucursal());
			pd.setCodigo_articulo(elem.getCodigo_servicio());
			pd = articuloDao.consultar(pd);

			Detalle_factura detalle = new Detalle_factura();
			detalle.setCodigo_empresa(aux2.getCodigo_empresa());
			detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
			detalle.setFacturable(true);
			detalle.setFactura_concepto(elem.getNro_factura());
			detalle.setValor_real(elem.getValor_real());
			detalle.setCodigo_articulo(elem.getCodigo_servicio());
			detalle.setDetalle((pd != null ? pd.getNombre1() : ""));
			detalle.setCantidad(elem.getUnidades());
			detalle.setValor_unitario(elem.getValor_unitario());
			detalle.setValor_total(elem.getValor_total());
			detalle.setTipo("SERVICIO");

			lista_detalle.add(detalle);
			total += (elem.getValor_total());
		}

		result_lista.put("total", total);

		return result_lista;
	}

	private Map<String, Object> getLista_medicamentos(
			List<Detalle_factura> lista_detalle, Admision aux2,
			List<Detalle_factura> lista_detalle_externo, String accion,
			String tipo) throws Exception {

		Map<String, Object> result_lista = new HashMap<String, Object>();
		result_lista.put("nocancelo", false);

		double total = 0.0;

		Map<String, Object> mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		mapDatos.put("tipo", tipo);
		List<Datos_medicamentos> lista_datos_med = datos_medicamentosDao
				.listar(mapDatos);
		for (Datos_medicamentos elem : lista_datos_med) {
			if (elem.getCancelo_copago().equals("N")) {
				result_lista.put("nocancelo", true);
			}

			boolean checked = true;
			Articulo pd = new Articulo();
			pd.setCodigo_empresa(elem.getCodigo_empresa());
			pd.setCodigo_sucursal(elem.getCodigo_sucursal());
			pd.setCodigo_articulo(elem.getCodigo_medicamento());
			pd = articuloDao.consultar(pd);

			if (!accion.equalsIgnoreCase("Modificar")) {
				if (pd != null) {
					checked = pd.getFacturable();
				}
			}

			// Recorremos lista de detalle factura para verificar si el registro
			// ya se encuentra registrado //
			for (int i = 0; i < lista_detalle_externo.size(); i++) {
				Detalle_factura auxDetalle_factura = lista_detalle_externo
						.get(i);
				if (auxDetalle_factura.getFactura_concepto().equals(
						elem.getNro_factura())
						&& elem.getCodigo_medicamento().equals(
								auxDetalle_factura.getCodigo_articulo())) {
					checked = auxDetalle_factura.getFacturable();
					i = lista_detalle_externo.size();
				}
			}
			Detalle_factura detalle = new Detalle_factura();
			detalle.setCodigo_empresa(aux2.getCodigo_empresa());
			detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
			detalle.setFacturable(checked);
			detalle.setFactura_concepto(elem.getNro_factura());
			detalle.setValor_real(elem.getValor_real());
			detalle.setCodigo_articulo(elem.getCodigo_medicamento());
			detalle.setDetalle((pd != null ? pd.getNombre1() : ""));
			detalle.setCantidad(elem.getUnidades());
			detalle.setValor_unitario(elem.getValor_unitario());
			detalle.setValor_total(elem.getValor_total());
			detalle.setTipo((tipo.equals("01") ? "MEDICAMENTO"
					: "MATERIALES/INSUMOS"));

			if (checked) {
				total += (elem.getValor_total());
			}
			lista_detalle.add(detalle);

		}

		result_lista.put("total", total);

		return result_lista;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getLista_procedimientos(
			List<Detalle_factura> lista_detalle,
			Manuales_tarifarios manuales_tarifarios, Admision aux2)
			throws Exception {
		Map<String, Object> result_lista = new HashMap<String, Object>();
		result_lista.put("nocancelo", false);

		String manual = (manuales_tarifarios != null ? manuales_tarifarios
				.getMaestro_manual().getTipo_manual() : "");

		Map<String, Object> servicios = new HashMap<String, Object>();
		servicios.put("grupos_quirurgicosService", grupos_quirurgicosService);
		servicios.put("grupos_iss01Service", grupos_iss01Service);

		double total = 0.0;
		Map<String, Object> mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		List<Datos_procedimiento> lista_datos_cons = datos_procedimientoService
				.listarTabla(mapDatos);

		// log.info("lista_datos_cons ===> " + lista_datos_cons);
		for (Datos_procedimiento elem : lista_datos_cons) {
			if (elem.getCancelo_copago() != null
					&& elem.getCancelo_copago().equals("N")) {
				result_lista.put("nocancelo", true);
			}

			Map<String, Object> pro = Utilidades.getProcedimiento(
					manuales_tarifarios,
					new Long(elem.getCodigo_procedimiento()),
					ServiciosDisponiblesUtils.getServiceLocator());
			String nombre = (String) pro.get("nombre_procedimiento");
			String grupo = (String) pro.get("grupo");

			Detalle_factura detalle = new Detalle_factura();
			detalle.setCodigo_empresa(aux2.getCodigo_empresa());
			detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
			detalle.setFacturable(true);
			detalle.setFactura_concepto(elem.getCodigo_registro() + "");
			detalle.setValor_real(elem.getValor_real());
			detalle.setCodigo_articulo(elem.getCodigo_procedimiento());
			detalle.setDetalle(nombre);
			detalle.setCantidad(elem.getUnidades());
			detalle.setValor_unitario(elem.getValor_procedimiento());
			detalle.setValor_total(elem.getValor_neto());
			detalle.setTipo((elem.getEs_quirurgico().equals("S") ? "PROCEDIMIENTO MULT"
					: "PROCEDIMIENTO"));

			lista_detalle.add(detalle);
			total += (elem.getValor_neto());

			if (elem.getEs_quirurgico().equals("S")) {
				if (elem.getCobra_cirujano().equals("S")) {
					Map<String, Object> mapBean = Utilidades
							.getNomGrupoCirugia(manual, grupo, "GR01", "39756",
									servicios, new HashMap<String, Object>());
					String nom_pdc = (String) mapBean.get("procedimiento");

					detalle = new Detalle_factura();
					detalle.setCodigo_empresa(aux2.getCodigo_empresa());
					detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
					detalle.setFacturable(true);
					detalle.setFactura_concepto(elem.getCodigo_registro() + "");
					detalle.setValor_real(elem.getValor_cirujano());
					detalle.setCodigo_articulo(elem.getCodigo_procedimiento());
					detalle.setDetalle(nom_pdc);
					detalle.setCantidad(elem.getUnidades());
					detalle.setValor_unitario(elem.getValor_cirujano());
					detalle.setValor_total(0.0);
					detalle.setTipo((elem.getEs_quirurgico().equals("S") ? "PROCEDIMIENTO MULT"
							: "PROCEDIMIENTO"));

					if (!nom_pdc.trim().equals("")
							&& (detalle.getValor_unitario() > 0)) {
						lista_detalle.add(detalle);
					}
				}
				if (elem.getCobra_anestesiologo().equals("S")) {
					Map<String, Object> mapBean = Utilidades
							.getNomGrupoCirugia(manual, grupo, "GR02", "38564",
									servicios, new HashMap<String, Object>());
					String nom_pdc = (String) mapBean.get("procedimiento");

					detalle = new Detalle_factura();
					detalle.setCodigo_empresa(aux2.getCodigo_empresa());
					detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
					detalle.setFacturable(true);
					detalle.setFactura_concepto(elem.getCodigo_registro() + "");
					detalle.setValor_real(elem.getValor_anestesiologo());
					detalle.setCodigo_articulo(elem.getCodigo_procedimiento());
					detalle.setDetalle(nom_pdc);
					detalle.setCantidad(elem.getUnidades());
					detalle.setValor_unitario(elem.getValor_anestesiologo());
					detalle.setValor_total(0.0);
					detalle.setTipo((elem.getEs_quirurgico().equals("S") ? "PROCEDIMIENTO MULT"
							: "PROCEDIMIENTO"));

					if (!nom_pdc.trim().equals("")
							&& (detalle.getValor_unitario() > 0)) {
						lista_detalle.add(detalle);
					}
				}
				if (elem.getCobra_ayudante().equals("S")) {
					Map<String, Object> mapBean = Utilidades
							.getNomGrupoCirugia(manual, grupo, "GR03", "39456",
									servicios, new HashMap<String, Object>());
					String nom_pdc = (String) mapBean.get("procedimiento");

					detalle = new Detalle_factura();
					detalle.setCodigo_empresa(aux2.getCodigo_empresa());
					detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
					detalle.setFacturable(true);
					detalle.setFactura_concepto(elem.getCodigo_registro() + "");
					detalle.setValor_real(elem.getValor_ayudante());
					detalle.setCodigo_articulo(elem.getCodigo_procedimiento());
					detalle.setDetalle(nom_pdc);
					detalle.setCantidad(elem.getUnidades());
					detalle.setValor_unitario(elem.getValor_ayudante());
					detalle.setValor_total(0.0);
					detalle.setTipo((elem.getEs_quirurgico().equals("S") ? "PROCEDIMIENTO MULT"
							: "PROCEDIMIENTO"));

					if (!nom_pdc.trim().equals("")
							&& (detalle.getValor_unitario() > 0)) {
						lista_detalle.add(detalle);
					}
				}
				if (elem.getCobra_sala().equals("S")) {
					Map<String, Object> mapBean = Utilidades
							.getNomGrupoCirugia(manual, grupo, "GR04", "",
									servicios, new HashMap<String, Object>());
					String nom_pdc = (String) mapBean.get("procedimiento");
					if (nom_pdc.equals("")) {
						nom_pdc = "Sala de cirugía";
					}

					detalle = new Detalle_factura();
					detalle.setCodigo_empresa(aux2.getCodigo_empresa());
					detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
					detalle.setFacturable(true);
					detalle.setFactura_concepto(elem.getCodigo_registro() + "");
					detalle.setValor_real(elem.getValor_sala());
					detalle.setCodigo_articulo(elem.getCodigo_procedimiento());
					detalle.setDetalle(nom_pdc);
					detalle.setCantidad(elem.getUnidades());
					detalle.setValor_unitario(elem.getValor_sala());
					detalle.setValor_total(0.0);
					detalle.setTipo((elem.getEs_quirurgico().equals("S") ? "PROCEDIMIENTO MULT"
							: "PROCEDIMIENTO"));

					if (!nom_pdc.trim().equals("")
							&& (detalle.getValor_unitario() > 0)) {
						lista_detalle.add(detalle);
					}
				}
				if (elem.getCobra_materiales().equals("S")) {
					Map<String, Object> mapBean = Utilidades
							.getNomGrupoCirugia(manual, grupo, "GR05", "41205",
									servicios, new HashMap<String, Object>());
					String nom_pdc = (String) mapBean.get("procedimiento");

					detalle = new Detalle_factura();
					detalle.setCodigo_empresa(aux2.getCodigo_empresa());
					detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
					detalle.setFacturable(true);
					detalle.setFactura_concepto(elem.getCodigo_registro() + "");
					detalle.setValor_real(elem.getValor_materiales());
					detalle.setCodigo_articulo(elem.getCodigo_procedimiento());
					detalle.setDetalle(nom_pdc);
					detalle.setCantidad(elem.getUnidades());
					detalle.setValor_unitario(elem.getValor_materiales());
					detalle.setValor_total(0.0);
					detalle.setTipo((elem.getEs_quirurgico().equals("S") ? "PROCEDIMIENTO MULT"
							: "PROCEDIMIENTO"));

					if (!nom_pdc.trim().equals("")
							&& (detalle.getValor_unitario() > 0)) {
						lista_detalle.add(detalle);
					}
				}
				if (elem.getCobra_perfusionista().equals("S")) {
					Map<String, Object> mapBean = Utilidades
							.getNomGrupoCirugia(manual, grupo, "GR06", "",
									servicios, new HashMap<String, Object>());
					String nom_pdc = (String) mapBean.get("procedimiento");

					detalle = new Detalle_factura();
					detalle.setCodigo_empresa(aux2.getCodigo_empresa());
					detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
					detalle.setFacturable(true);
					detalle.setFactura_concepto(elem.getCodigo_registro() + "");
					detalle.setValor_real(elem.getValor_perfusionista());
					detalle.setCodigo_articulo(elem.getCodigo_procedimiento());
					detalle.setDetalle(nom_pdc);
					detalle.setCantidad(elem.getUnidades());
					detalle.setValor_unitario(elem.getValor_perfusionista());
					detalle.setValor_total(0.0);
					detalle.setTipo((elem.getEs_quirurgico().equals("S") ? "PROCEDIMIENTO MULT"
							: "PROCEDIMIENTO"));

					if (!nom_pdc.trim().equals("")
							&& (detalle.getValor_unitario() > 0)) {
						lista_detalle.add(detalle);
					}
				}
			}
		}

		result_lista.put("total", total);

		return result_lista;

	}

	private Map<String, Object> getLista_consultas(
			List<Detalle_factura> lista_detalle,
			Manuales_tarifarios manuales_tarifarios, Admision aux2)
			throws Exception {
		Map<String, Object> result_lista = new HashMap<String, Object>();
		result_lista.put("nocancelo", false);

		double total = 0.0;
		Map<String, Object> mapDatos = new HashMap<String, Object>();
		mapDatos.put("codigo_empresa", aux2.getCodigo_empresa());
		mapDatos.put("codigo_sucursal", aux2.getCodigo_sucursal());
		mapDatos.put("nro_ingreso", aux2.getNro_ingreso());
		mapDatos.put("nro_identificacion", aux2.getNro_identificacion());
		List<Datos_consulta> lista_datos_cons = datos_consultaService
				.listarTabla(mapDatos);
		for (Datos_consulta elem : lista_datos_cons) {
			if (elem.getCancelo_copago() != null
					&& elem.getCancelo_copago().equals("N")) {
				result_lista.put("nocancelo", true);
			}

			Map<String, Object> pro = Utilidades.getProcedimiento(
					manuales_tarifarios, new Long(elem.getCodigo_consulta()),
					ServiciosDisponiblesUtils.getServiceLocator());
			String nombre = (String) pro.get("nombre_procedimiento");
			Detalle_factura detalle = new Detalle_factura();
			detalle.setCodigo_empresa(aux2.getCodigo_empresa());
			detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
			detalle.setFacturable(true);
			detalle.setFactura_concepto(elem.getCodigo_registro() + "");
			detalle.setValor_real(elem.getValor_real());
			detalle.setCodigo_articulo(elem.getCodigo_consulta());
			detalle.setDetalle(nombre);
			detalle.setCantidad(1);
			detalle.setValor_unitario(elem.getValor_consulta());
			detalle.setValor_total(elem.getValor_neto());
			detalle.setTipo("CONSULTA");

			lista_detalle.add(detalle);
			total += (elem.getValor_neto());

		}

		result_lista.put("total", total);

		return result_lista;

	}

	private Map<String, Object> getLista_admisionCama(
			List<Detalle_factura> lista_detalle,
			Via_ingreso_contratadas via_ingreso_contratadas, Admision aux2,
			Timestamp fecha) throws Exception {

		Map<String, Object> result_lista = new HashMap<String, Object>();
		result_lista.put("nocancelo", false);

		double total = 0.0;

		Admision_cama admision_cama = new Admision_cama();
		admision_cama.setCodigo_empresa(aux2.getCodigo_empresa());
		admision_cama.setCodigo_sucursal(aux2.getCodigo_sucursal());
		admision_cama.setNro_ingreso(aux2.getNro_ingreso());
		admision_cama.setNro_identificacion(aux2.getNro_identificacion());
		admision_cama = admision_camaDao.consultar(admision_cama);

		if (admision_cama != null) {
			// Obtenemos nombre del procedimiento dependiendo del manual
			// tarifario //
			Map<String, Object> pro = Utilidades.getProcedimiento(
					via_ingreso_contratadas,
					new Long(admision_cama.getCodigo_procedimiento()),
					ServiciosDisponiblesUtils.getServiceLocator());
			String nombre = (String) pro.get("nombre_procedimiento");

			double valor = 0;
			int cantidad = 0;
			long segundos = Math.abs(fecha.getTime()
					- admision_cama.getFecha_ingreso().getTime()) / 1000;
			/*
			 * long segundos =
			 * Math.abs(admision_cama.getFecha_ingreso().getTime() -
			 * fecha.getTime()) / 1000;
			 */
			long dias = segundos / 86400;
			if (dias == 0 || dias == 1) {
				cantidad = 1;
				valor = admision_cama.getValor_dia();
			} else {
				cantidad = (int) dias;
				valor = (dias - 1) * admision_cama.getValor_dia();
			}

			Detalle_factura detalle = new Detalle_factura();
			detalle.setCodigo_empresa(aux2.getCodigo_empresa());
			detalle.setCodigo_sucursal(aux2.getCodigo_sucursal());
			detalle.setFacturable(true);
			detalle.setFactura_concepto("");
			detalle.setValor_real(0.0);
			detalle.setCodigo_articulo(admision_cama.getCodigo_procedimiento());
			detalle.setDetalle(nombre);
			detalle.setCantidad(cantidad);
			detalle.setValor_unitario(admision_cama.getValor_dia());
			detalle.setValor_total(valor);
			detalle.setTipo("ESTANCIA");

			lista_detalle.add(detalle);
			total += valor;

		}

		result_lista.put("total", total);

		return result_lista;
	}

	/**
	 * Este metodo me permite crear una factura capitada
	 *
	 * @author Luis Miguel
	 * @author Ferney Jimenez
	 * @param map
	 *
	 */
	@SuppressWarnings("unchecked")
	public Facturacion guardarGeneracionFacturaAgrupadaCapitada(
			Map<String, Object> map) {
		try {
			/* cargamos parametros */
			String anio = (String) map.get("anio");
			Impuesto base = (Impuesto) map.get("base");
			double retencion = (Double) map.get("retencion");
			double valor_total = (Double) map.get("valor_total");
			double valor_factura = (Double) map.get("valor_factura");
			String tipo_factura = (String) map.get("tipo_factura");
			Date fecha_inicio = (Date) map.get("fecha_inicio");
			Date fecha_final = (Date) map.get("fecha_final");
			Usuarios usuarios = (Usuarios) map.get("usuario");
			Date fecha_factura = (Date) map.get("fecha_fact");

			String descipcion_mes_facturado = (String) map
					.get("descipcion_mes_facturado");
			Integer poblacion_segun_base_datos = (Integer) map
					.get("poblacion_segun_base_datos");
			String descripcion_nro_contrato = (String) map
					.get("descripcion_nro_contrato");
			String descripcion_tipo_servicio = (String) map
					.get("descripcion_tipo_servicio");

			List<Contratos> lista_contratos = (List<Contratos>) map
					.get("lista_contratos");

			String concepto = (String) map.get("concepto");
			Integer nro_dias_reportados = (Integer) map
					.get("nro_dias_reportados");
			String tarifa_usuario_dia = (String) map.get("tarifa_usuario_dia");

			int plazo = 30;

			/* factura agrupada o capitada */
			String comprobante = IConstantes.COMPROBANTE_FACTURA_CAPITADA;

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha_factura);
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) + plazo);
			Date fecha_vencimiento = calendar.getTime();
			// String tipo_identificacion = null;
			String nro_identificacion = null;
			String nro_ingreso = "";
			String documento_externo = "";
			String codigo_empleado = "";

			String nro_contrato = "";
			String nro_poliza = "";
			int total_cuotas = 1;
			String forma_pago = "02";

			String codigo_administradora = lista_contratos.get(0)
					.getCodigo_administradora();
			String id_plan = "";

			for (int i = 0; i < lista_contratos.size(); i++) {
				Contratos contratos = lista_contratos.get(i);
				id_plan = id_plan + (i != 0 ? "|" : "")
						+ contratos.getId_plan();
			}

			double valor_cuota = 0.0;
			double valor_copago = 0.0;
			String nocopago = null;
			double dto_factura = 0.0;
			double dto_copago = 0.0;
			double cop_canc = 0.0;
			String tipo = "GEN_" + tipo_factura;
			String estado = "FACT";

			Timestamp creacion_date = new Timestamp(
					new GregorianCalendar().getTimeInMillis());
			Timestamp ultimo_update = new Timestamp(
					new GregorianCalendar().getTimeInMillis());
			String creacion_user = usuarios.getCodigo();
			String ultimo_user = usuarios.getCodigo();

			/* verificamos si tiene contabilizacion automatica */
			boolean cont = false;
			contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
			res.setCodigo_empresa(usuarios.getCodigo_empresa());
			res = (contaweb.modelo.bean.Resolucion) resolucionContDao
					.consultar(res);
			if (res != null) {
				if (res.getContabiliza_aut()) {
					cont = true;
				}
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd/MM/yyyy");
			String codigo_documento_res = consecutivoService.getZeroFill(
					consecutivoService.crearConsecutivo(
							usuarios.getCodigo_empresa(),
							usuarios.getCodigo_sucursal(), "VENTA"), 11);

			/* creamos factura */
			Facturacion facturacion = new Facturacion();
			facturacion.setCodigo_empresa(usuarios.getCodigo_empresa());
			facturacion.setCodigo_sucursal(usuarios.getCodigo_sucursal());
			facturacion.setCodigo_comprobante(comprobante);
			facturacion.setCodigo_documento_res(codigo_documento_res);
			facturacion.setTipo_identificacion("");
			facturacion.setCodigo_tercero(nro_identificacion);
			facturacion.setNro_ingreso(nro_ingreso);
			facturacion.setDocumento_externo(documento_externo);
			facturacion.setCodigo_empleado(codigo_empleado);
			facturacion.setFecha(new Timestamp(fecha_factura.getTime()));
			facturacion.setFecha_vencimiento(new Timestamp(fecha_vencimiento
					.getTime()));
			facturacion.setFecha_inicio(new Timestamp(fecha_inicio.getTime()));
			facturacion.setFecha_final(new Timestamp(fecha_final.getTime()));
			facturacion.setCodigo_administradora(codigo_administradora);
			facturacion.setId_plan(id_plan);
			facturacion.setNro_contrato(nro_contrato);
			facturacion.setNro_poliza(nro_poliza);
			facturacion.setTotal_cuotas(total_cuotas);
			facturacion.setPlazo(plazo);
			facturacion.setForma_pago(forma_pago);
			facturacion.setDescripcion("Periodo: "
					+ simpleDateFormat.format(fecha_inicio) + " a "
					+ simpleDateFormat.format(fecha_final));
			facturacion.setObservacion("");
			facturacion.setValor_total(valor_total);
			facturacion.setValor_cuota(valor_cuota);
			facturacion.setValor_copago(valor_copago);
			facturacion.setNocopago(nocopago != null ? "S" : "N");
			facturacion.setDto_factura(dto_factura);
			facturacion.setDto_copago(dto_copago);
			facturacion.setCop_canc(cop_canc);
			facturacion.setTipo(tipo);
			facturacion.setEstado(estado);
			facturacion.setFactura(null);
			facturacion.setPost("N");
			facturacion.setCreacion_date(creacion_date);
			facturacion.setUltimo_update(ultimo_update);
			facturacion.setCreacion_user(creacion_user);
			facturacion.setUltimo_user(ultimo_user);
			facturacion.setCodigo_credito("");
			facturacion.setRemision("");
			facturacion.setPrefijo("");
			facturacion.setAnio(new java.text.SimpleDateFormat("yyyy")
					.format(fecha_factura));
			facturacion.setMes(new java.text.SimpleDateFormat("MM")
					.format(fecha_factura));
			facturacion.setClasificacion("");
			facturacion.setVerificado("N");
			facturacion.setRetencion(0.0);
			facturacion.setFactura("");
			facturacion.setRadicado("N");
			facturacion
					.setTarifa_usuario_dia(tarifa_usuario_dia != null ? tarifa_usuario_dia
							: "");
			facturacion.setConcepto(concepto);
			facturacion
					.setNro_dias_reportados(nro_dias_reportados != null ? nro_dias_reportados
							: 0);

			facturacion.setAuditado("N");
			facturacion.setAnio_retencion(anio);
			facturacion.setCuenta_retencion(base != null ? base
					.getCodigo_cuenta() : "");
			facturacion.setRetencion(retencion);
			facturacion.setValor_pagar_factura(valor_factura);
			facturacion.setCodigo_centro("");
			facturacion.setDescipcion_mes_facturado(descipcion_mes_facturado);
			facturacion
					.setPoblacion_segun_base_datos(poblacion_segun_base_datos);
			facturacion.setDescripcion_nro_contrato(descripcion_nro_contrato);
			facturacion.setDescripcion_tipo_servicio(descripcion_tipo_servicio);
			facturacion.setNro_atencion("GC" + codigo_documento_res);

			facturacionDao.crear(facturacion);
			consecutivoService.actualizarConsecutivo(
					usuarios.getCodigo_empresa(),
					usuarios.getCodigo_sucursal(), "VENTA",
					codigo_documento_res);

			Detalle_factura detalle = new Detalle_factura();
			detalle.setCodigo_empresa(usuarios.getCodigo_empresa());
			detalle.setCodigo_sucursal(usuarios.getCodigo_sucursal());
			detalle.setId_factura(facturacion.getId_factura());

			detalle.setTipo(tipo);
			detalle.setCodigo_articulo("DET_FAC_CAP_0001");
			detalle.setDetalle("Periodo: "
					+ simpleDateFormat.format(fecha_inicio) + " a "
					+ simpleDateFormat.format(fecha_final));
			double valor_total_fact = (facturacion.getValor_total()
					- facturacion.getDto_factura()
					- facturacion.getValor_copago() - facturacion
					.getDto_copago());
			detalle.setValor_unitario(valor_total_fact);
			detalle.setCantidad(1);
			detalle.setValor_total(valor_total_fact);
			detalle.setCreacion_date(facturacion.getCreacion_date());
			detalle.setUltimo_update(ultimo_update);
			detalle.setCreacion_user(creacion_user);
			detalle.setUltimo_user(ultimo_user);
			detalle.setFactura_concepto("");
			detalle.setPlan("");
			detalle.setC_costos("");
			detalle.setValor_real(valor_total_fact);
			detalle.setFacturable(true);
			detalleFacturaDao.crear(detalle);

			Map<String, Object> parametros_busqueda = (Map<String, Object>) map
					.get("parametros_busqueda");

			parametros_busqueda.put("factura_cap", codigo_documento_res);

			/* actualizamos factura */
			facturacionDao.actualizarCodigoFacturaCapitada(parametros_busqueda);

			if (cont) {
				Map<String, Object> param_cont = new HashMap<String, Object>();
				param_cont.put("id_factura", facturacion.getId_factura());
				param_cont.put("codigo_empresa", usuarios.getCodigo_empresa());
				param_cont
						.put("codigo_sucursal", usuarios.getCodigo_sucursal());
				param_cont.put("codigo_documento",
						facturacion.getCodigo_documento_res());
				param_cont.put("codigo_comprobante", "16");
				guardarContabilizacionCapitada(param_cont, true);
			}
			return facturacion;
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void eliminarServicioRips(Detalle_factura detalle_factura,
			Usuarios usuarios, boolean editar_ordenamiento_prestador) {
		try {
			String tipo = detalle_factura.getTipo();
			if (tipo.equals("CONSULTA")) {
				Datos_consulta datos = new Datos_consulta();
				datos.setCodigo_empresa(detalle_factura.getCodigo_empresa());
				datos.setCodigo_sucursal(detalle_factura.getCodigo_sucursal());
				datos.setCodigo_registro(Utilidades
						.getValorLong(detalle_factura.getFactura_concepto()));
				datos.setTipo_hc(null);
				datos = datos_consultaService.consultar(datos);
				if (datos != null) {
					if (datos.getCodigo_orden() != null
							&& !datos.getCodigo_orden().equals("")
							&& !editar_ordenamiento_prestador) {
						throw new Exception(
								"Esta consulta no se puede eliminar por que fue hecho de una orden de servicio");
					} else if (datos.getCita_pyp()
							&& !editar_ordenamiento_prestador) {
						throw new Exception(
								"Esta consulta no se puede eliminar por que fue hecha automaticamente de una cita");
					} else if (datos.getTipo_hc() != null
							&& !datos.getTipo_hc().equals("")
							&& !editar_ordenamiento_prestador) {
						throw new Exception(
								"Esta consulta no se puede eliminar por que fue hecha automaticamente de una historia");
					}
					datos.setDelete_user(usuarios.getCodigo());
					datos_consultaService.eliminarRegistro(datos);
				}
			} else if (tipo.equals("PROCEDIMIENTO")
					|| tipo.equals("PROCEDIMIENTO MULT")) {
				Datos_procedimiento datos = new Datos_procedimiento();
				datos.setCodigo_empresa(detalle_factura.getCodigo_empresa());
				datos.setCodigo_sucursal(detalle_factura.getCodigo_sucursal());
				datos.setCodigo_registro(Utilidades
						.getValorLong(detalle_factura.getFactura_concepto()));
				datos.setConsecutivo_registro("1");
				datos.setCodigo_orden(null);
				datos.setNro_ingreso(null);
				datos = datos_procedimientoService.consultar(datos);
				if (datos != null) {
					if (datos.getCodigo_orden() != null
							&& !datos.getCodigo_orden().equals("")
							&& !editar_ordenamiento_prestador) {
						throw new Exception(
								"Esta consulta no se puede eliminar por que fue hecho de una orden de servicio");
					}
					datos.setConsecutivo_registro(null);
					datos.setDelete_user(usuarios.getCodigo());
					datos_procedimientoService.eliminarRegistro(datos);
				}
			} else if (tipo.equals("MEDICAMENTO")
					|| tipo.equals("MATERIALES/INSUMOS")) {
				Facturacion_medicamento datos = new Facturacion_medicamento();
				datos.setCodigo_empresa(detalle_factura.getCodigo_empresa());
				datos.setCodigo_sucursal(detalle_factura.getCodigo_sucursal());
				datos.setNro_factura(detalle_factura.getFactura_concepto());
				datos = facturacion_medicamentoService.consultar(datos);

				if (datos != null) {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa", datos.getCodigo_empresa());
					parametros.put("codigo_sucursal",
							datos.getCodigo_sucursal());
					parametros.put("nro_factura", datos.getNro_factura());
					List<Datos_medicamentos> lista_medicamentos = datos_medicamentosDao
							.listar(parametros);
					Iterator<Datos_medicamentos> it = lista_medicamentos
							.iterator();
					while (it.hasNext()) {
						Datos_medicamentos datos_medicamentos = (Datos_medicamentos) it
								.next();
						if (datos_medicamentos.getCodigo_medicamento().equals(
								detalle_factura.getCodigo_articulo())) {
							it.remove();
						}
					}

					boolean cont = false;
					boolean afectar_kardex_fact = true;
					contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
					res.setCodigo_empresa(datos.getCodigo_empresa());
					res = resolucionContDao.consultar(res);
					if (res != null) {
						if (res.getContabiliza_aut()) {
							cont = true;
						}
					}

					healthmanager.modelo.bean.Resolucion res2 = new healthmanager.modelo.bean.Resolucion();
					res2.setCodigo_empresa(datos.getCodigo_empresa());
					res2 = resolucionDao.consultar(res2);
					if (res2 != null) {
						afectar_kardex_fact = res2.getAfectar_kardex_fact();
					}

					datos.setDelete_user(usuarios.getCodigo());

					Map<String, Object> datos_guardar = new HashMap<String, Object>();
					datos_guardar.put("lista_medicamentos", lista_medicamentos);
					datos_guardar.put("facturacion_medicamento", datos);
					datos_guardar.put("accion", "Modificar");
					datos_guardar.put("cont", cont);
					datos_guardar.put("afectar_kardex_fact",
							afectar_kardex_fact);

					facturacion_medicamentoService.guardarFacturacion(
							datos_guardar, false);
					if (lista_medicamentos.isEmpty()) {
						facturacion_medicamentoService.eliminar(datos_guardar,
								false);
					}

				}
			} else if (tipo.equals("SERVICIO")) {
				Facturacion_servicio datos = new Facturacion_servicio();
				datos.setCodigo_empresa(detalle_factura.getCodigo_empresa());
				datos.setCodigo_sucursal(detalle_factura.getCodigo_sucursal());
				datos.setNro_factura(detalle_factura.getFactura_concepto());
				datos = facturacion_servicioService.consultar(datos);

				if (datos != null) {
					Map<String, Object> paramDatos_servicios = new HashMap<String, Object>();
					paramDatos_servicios.put("codigo_empresa",
							datos.getCodigo_empresa());
					paramDatos_servicios.put("codigo_sucursal",
							datos.getCodigo_sucursal());
					paramDatos_servicios.put("nro_factura",
							datos.getNro_factura());
					List<Datos_servicio> lista_datos = datos_servicioDao
							.listar(paramDatos_servicios);
					Iterator<Datos_servicio> it = lista_datos.iterator();
					while (it.hasNext()) {
						Datos_servicio datos_servicio = (Datos_servicio) it
								.next();

						if (datos_servicio.getCodigo_servicio().equals(
								detalle_factura.getCodigo_articulo())) {
							it.remove();
						}

					}

					Map<String, Object> datosGuardar = new HashMap<String, Object>();
					datosGuardar.put("facturacion_servicio", datos);
					datosGuardar.put("lista_detalle", lista_datos);
					datosGuardar.put("accion", "Modificar");
					facturacion_servicioService.guardar(datosGuardar, false);

					if (lista_datos.isEmpty()) {
						datos.setDelete_user(usuarios.getCodigo());
						facturacion_servicioService.eliminarRegistro(datos);
					}
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}

	}

	/**
	 * Este metodo permite crear una admision, una prefactura que alimenta los
	 * rips y una factura
	 *
	 * @param datos_prefactura
	 * @param datos_facturacion
	 * @author Luis Miguel
	 *
	 */
	public Map<String, Object> guardarDatosCompletos(
			Map<String, Object> datos_prefactura,
			Map<String, Object> datos_facturacion) {
		Object[] datos_retorno = guardarDatosServicios(datos_prefactura,
				datos_facturacion);

		Admision admision = (Admision) datos_retorno[0];
		Boolean recargar_informacion = (Boolean) datos_retorno[1];

		// Creamos la factura
		Facturacion facturacion = (Facturacion) datos_facturacion
				.get("facturacion");
		facturacion.setId_plan(admision.getId_plan());
		facturacion.setNro_ingreso(admision.getNro_ingreso());
		datos_facturacion.put("admision_aux", admision);

		facturacion = guardarDatos(datos_facturacion);

		// Creamos mapa de respuesta
		Map<String, Object> map_resultado = new HashMap<String, Object>();
		map_resultado.put("admision", admision);
		map_resultado.put("facturacion", facturacion);
		map_resultado.put("recargar_informacion", recargar_informacion);
		return map_resultado;
	}

	@SuppressWarnings("unchecked")
	public Facturacion guardarDatos(Map<String, Object> datos) {
		log.info("ejecutando metodo @guardarDatos() ");
		try {
			Facturacion facturacion = (Facturacion) datos.get("facturacion");
			List<Detalle_factura> lista_detalle = (List<Detalle_factura>) datos
					.get("lista_detalle");
			String accion = (String) datos.get("accion");
			String tipo = "";

			boolean cont = false;
			contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
			res.setCodigo_empresa(facturacion.getCodigo_empresa());
			res = (contaweb.modelo.bean.Resolucion) resolucionContDao
					.consultar(res);
			if (res != null) {
				if (res.getContabiliza_aut()) {
					cont = true;
				}
			}

			Contratos contratos = (Contratos) datos.get("contratos");
			Admision admision = (Admision) datos.get("admision_aux");

			if (admision == null) {
				admision = new Admision();
				admision.setCodigo_empresa(facturacion.getCodigo_empresa());
				admision.setCodigo_sucursal(facturacion.getCodigo_sucursal());
				admision.setNro_identificacion(facturacion.getCodigo_tercero());
				admision.setNro_ingreso(facturacion.getNro_ingreso());
				admision = admisionDao.consultar(admision);
			}

			String nro_atencion = "";

			if (contratos.getTipo_facturacion().equals(
					IConstantes.TIPO_CONTRATO_CAPITADA)) {
				tipo = IConstantes.TIPO_FACTURA_CAP_INTERNA;
				nro_atencion = "A" + admision.getNro_ingreso() + "_"
						+ admision.getNro_identificacion();
			} else if (contratos.getTipo_facturacion().equals(
					IConstantes.TIPO_CONTRATO_AGRUPADA)) {
				tipo = IConstantes.TIPO_FACTURA_AGRUPADA;
				nro_atencion = "G" + admision.getNro_ingreso() + "_"
						+ admision.getNro_identificacion();
			} else if (contratos.getTipo_facturacion().equals(
					IConstantes.TIPO_CONTRATO_PORTABILIDAD)) {
				tipo = IConstantes.TIPO_FACTURA_PORTABILIDAD;
				nro_atencion = "P" + admision.getNro_ingreso() + "_"
						+ admision.getNro_identificacion();
			} else {
				tipo = IConstantes.TIPO_FACTURA_EVENTO_IND;
				nro_atencion = "E" + admision.getNro_ingreso() + "_"
						+ admision.getNro_identificacion();
			}

			facturacion.setTipo(tipo);
			facturacion.setNro_atencion(nro_atencion);

			if (accion.equalsIgnoreCase("registrar")) {
				facturacion.setCodigo_centro(admision.getCodigo_centro());

				Map<String, Object> param = new HashMap<String, Object>();
				param.put("codigo_empresa", facturacion.getCodigo_empresa());
				param.put("codigo_sucursal", facturacion.getCodigo_sucursal());
				param.put("codigo_tercero", facturacion.getCodigo_tercero());
				param.put("nro_ingreso", facturacion.getNro_ingreso());

				if (facturacionDao.existePorParametro(param)) {
					throw new ValidacionRunTimeException(
							"Admision nro "
									+ facturacion.getNro_ingreso()
									+ " de este paciente ya tiene factura asignada, por lo tanto no se puede guardar la factura");
				}

				facturacionDao.crear(facturacion);
			} else {
				facturacion.setCodigo_centro(admision.getCodigo_centro());
				// log.info("valor total antes actualizar: "
				// + facturacion.getValor_total());
				int result = facturacionDao.actualizar(facturacion);
				log.debug("actualizo factura: " + result);

				Detalle_factura detalle_facturaAux = new Detalle_factura();
				detalle_facturaAux.setId_factura(facturacion.getId_factura());
				detalleFacturaDao.eliminar_factura(detalle_facturaAux);
			}

			for (Detalle_factura detalle : lista_detalle) {
				if (!detalle.getExcluir_guardado()) {
					detalle.setId_factura(facturacion.getId_factura());
					detalle.setCreacion_date(facturacion.getCreacion_date());
					detalle.setUltimo_update(facturacion.getUltimo_update());
					detalle.setCreacion_user(facturacion.getCreacion_user());
					detalle.setUltimo_user(facturacion.getUltimo_user());
					detalle.setPlan("");
					detalle.setC_costos("");
					detalleFacturaDao.crear(detalle);
				}

			}
			double valor_copago = facturacion.getValor_copago();
			// REGISTRAMOS EL RECIBO DE CAJA //
			if (valor_copago > 0 && facturacion.getNocopago().equals("N")) {
				crearCopago(facturacion, lista_detalle, cont);
			} else {
				eliminarCopago(facturacion);
			}

			actualizarServicios(facturacion, admision);

			// log.info("facturacion al terminar de guardar: " + facturacion);
			return facturacion;
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarFacturacion(Facturacion facturacion, Admision admision) {
		try {
			boolean cont = false;
			contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
			res.setCodigo_empresa(facturacion.getCodigo_empresa());
			res = (contaweb.modelo.bean.Resolucion) resolucionContDao
					.consultar(res);
			if (res != null) {
				if (res.getContabiliza_aut()) {
					cont = true;
				}
			}

			if (facturacion.getCodigo_documento_res() != null) {
				actualizar(facturacion);
			} else {
				String codigo_documento_res = obtenerConsecutivoVenta(facturacion);
				facturacion.setCodigo_documento_res(codigo_documento_res);
				actualizar(facturacion);
			}
			if (facturacion.getTipo().equals(
					IConstantes.TIPO_FACTURA_EVENTO_IND)
					|| facturacion.getTipo().equals(
							IConstantes.TIPO_FACTURA_PORTABILIDAD)) {
				if (cont) {
					Map<String, Object> param_cont = new HashMap<String, Object>();
					param_cont.put("codigo_empresa",
							facturacion.getCodigo_empresa());
					param_cont.put("codigo_sucursal",
							facturacion.getCodigo_sucursal());
					param_cont.put("codigo_documento",
							facturacion.getCodigo_documento_res());
					param_cont.put("admision", admision);
					guardarContabilizacionIndividual(param_cont, true, true);
				}
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private synchronized String obtenerConsecutivoVenta(Facturacion facturacion) {
		String codigo_documento_res = consecutivoService.getZeroFill(
				consecutivoService.crearConsecutivo(
						facturacion.getCodigo_empresa(),
						facturacion.getCodigo_sucursal(), "VENTA"), 11);
		consecutivoService
				.actualizarConsecutivo(facturacion.getCodigo_empresa(),
						facturacion.getCodigo_sucursal(), "VENTA",
						codigo_documento_res);
		return codigo_documento_res;
	}

	/**
	 * Este metodo es para crear la cartera cuando no se contabilice la factura
	 * de manera automatica.
	 *
	 */
	@SuppressWarnings("unused")
	private void crearCarteraTercero(Facturacion facturacion, Admision admision) {
		String codigo_tercero = facturacion.getCodigo_administradora();
		if (admision != null && admision.getParticular().equals("S")) {
			codigo_tercero = admision.getNro_identificacion();
		}

		Tercero tercero = new Tercero();
		tercero.setCodigo_empresa(admision.getCodigo_empresa());
		tercero.setCodigo_sucursal(admision.getCodigo_sucursal());
		tercero.setNro_identificacion(facturacion.getCodigo_administradora());
		tercero = terceroDao.consultar(tercero);

		// calculamos valores
		Map<String, Object> paramDetalle_factura = new HashMap<String, Object>();
		paramDetalle_factura.put("id_factura", facturacion.getId_factura());
		paramDetalle_factura.put("facturable", new Boolean(true));

		double total = Utilidades.obtenerValorPrimitivo(detalleFacturaDao
				.totalFacturaClinica(paramDetalle_factura))
				- facturacion.getCop_canc();
		double retencion = 0;

		Impuesto impuesto = new Impuesto();
		impuesto.setCodigo_empresa(admision.getCodigo_empresa());
		impuesto.setCodigo_sucursal(admision.getCodigo_sucursal());
		impuesto.setCodigo_cuenta((tercero != null ? tercero
				.getCuenta_retencion() : ""));
		impuesto.setAnio(facturacion.getAnio());
		impuesto = impuestoDao.consultar(impuesto);
		if (impuesto != null) {
			if (impuesto.getBase() <= total) {
				retencion = total * impuesto.getPorcentaje();
			}
		}

		double descuento_glosa = (facturacion.getRadicado().equals("S") ? total
				- retencion - facturacion.getValor_pagar_factura() : 0);

		String codigo_comprobante = "15";

		// inicializamos el bean de cartera
		Cartera cartera = new Cartera();
		cartera.setCodigo_empresa(admision.getCodigo_empresa());
		cartera.setCodigo_sucursal(admision.getCodigo_sucursal());
		cartera.setCodigo_comprobante(codigo_comprobante);
		cartera.setCodigo_documento(facturacion.getCodigo_documento_res());
		cartera.setNro_cuota("1");
		cartera.setVencimiento(facturacion.getFecha_vencimiento());
		cartera.setAbono(0);
		cartera.setSaldos(total - retencion - descuento_glosa);
		cartera.setTotal(total - retencion - descuento_glosa);
		cartera.setTipo("FH");
		cartera.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		cartera.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		cartera.setCreacion_user(facturacion.getCreacion_user());
		cartera.setUltimo_user(facturacion.getCreacion_user());
		cartera.setManejo("CC");
		cartera.setCodigo_tercero(codigo_tercero);
		cartera.setCuenta((tercero != null ? tercero.getCuenta_cobrar() : ""));
		cartera.setConcepto("");
		Cartera cartera_temp = carteraDao.consultar(cartera);
		if (cartera_temp != null) {
			cartera = cartera_temp;
			cartera.setSaldos(total - retencion - descuento_glosa);
			cartera.setTotal(total - retencion - descuento_glosa);
			if (cartera.getSaldos() - cartera.getAbono() < 0) {
				throw new ValidacionRunTimeException(
						"La factura "
								+ facturacion.getCodigo_documento_res()
								+ " ya se le ha hecho abonos que superan el nuevo saldo");
			}
			cartera.setSaldos(cartera.getSaldos() - cartera.getAbono());
			carteraDao.actualizar(cartera);
		} else {
			carteraDao.crear(cartera);
		}
	}

	private void actualizarServicios(Facturacion facturacion, Admision admision)
			throws Exception {
		log.info("ejecutando metodo @actualizarServicios()");
		if (admision != null) {
			admision.setEstado("2");
			admisionDao.actualizar(admision);

			// Verificamos si es accidente de transito o evento catastrofico
			if (admision.getCausa_externa().equals(
					ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRANSITO)
					|| admision
							.getCausa_externa()
							.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_EVENTO_CATASTROFICO)) {
				Furips2 furips2 = new Furips2();
				furips2.setCodigo_empresa(admision.getCodigo_empresa());
				furips2.setCodigo_sucursal(admision.getCodigo_sucursal());
				furips2.setNro_ingreso(admision.getNro_ingreso());
				furips2.setNro_identificacion(admision.getNro_identificacion());
				furips2 = furips2Dao.consultarPorParametros(furips2);
				if (furips2 != null) {
					furips2.setNro_factura(facturacion.getId_factura() + "");
					furips2.setGasto_medico_quirurgicos_valor_facturado(facturacion
							.getValor_total());
					furips2Dao.actualizar(furips2);
				}
			}
		}
	}

	// Metodo para generar el copago //
	private void crearCopago(Facturacion facturacion,
			List<Detalle_factura> lista_detalle, boolean cont) {
		String fuente = "05";
		String codigo_recibo = consecutivoService.getZeroFill(
				consecutivoService.crearConsecutivoNota(
						facturacion.getCodigo_empresa(),
						facturacion.getCodigo_sucursal(), fuente), 20);

		// Elimninamos el recibo si ya existe //
		Caja caja_aux = new Caja(false);
		caja_aux.setCodigo_empresa(facturacion.getCodigo_empresa());
		caja_aux.setCodigo_sucursal(facturacion.getCodigo_sucursal());
		caja_aux.setCodigo_tercero(facturacion.getCodigo_tercero());
		caja_aux.setNro_ingreso(facturacion.getNro_ingreso());
		caja_aux = cajaService.consultar(caja_aux);
		if (caja_aux != null) {
			fuente = caja_aux.getFuente();
			codigo_recibo = caja_aux.getCodigo_recibo();
			cajaService.eliminar(caja_aux);
		}

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(facturacion.getCodigo_empresa());
		administradora.setCodigo_sucursal(facturacion.getCodigo_sucursal());
		administradora.setCodigo(facturacion.getCodigo_administradora());
		log.info("administradora consultar: " + administradora);
		administradora = administradoraDao.consultar(administradora);

		log.info("admision particular: "
				+ (administradora != null ? administradora
						.getTipo_aseguradora() : "NULL"));
		boolean es_particular = (administradora != null ? administradora
				.getTipo_aseguradora().equals("04") ? true : false : false);

		Caja caja = new Caja();
		caja.setCodigo_empresa(facturacion.getCodigo_empresa());
		caja.setCodigo_sucursal(facturacion.getCodigo_sucursal());
		caja.setFuente(fuente);
		caja.setCodigo_recibo(codigo_recibo);
		caja.setCodigo_comprobante(facturacion.getCodigo_comprobante());
		caja.setCodigo_documento(facturacion.getCodigo_documento_res());
		caja.setNro_ingreso(facturacion.getNro_ingreso());
		caja.setCodigo_tercero(facturacion.getCodigo_tercero());
		caja.setTipo_tercero("02");
		caja.setTipo_recibo((!es_particular ? "03" : "06"));
		caja.setCodigo_administradora(facturacion.getCodigo_administradora());
		caja.setId_plan(facturacion.getId_plan());
		caja.setFecha(facturacion.getFecha());
		caja.setValor_recibo(facturacion.getValor_copago());
		caja.setDescuento(facturacion.getDto_copago());
		caja.setEfectivo(facturacion.getValor_copago());
		caja.setValor_tarjeta(0.0);
		caja.setNumero_tarjeta("");
		caja.setBanco_tarjeta("");
		caja.setTipo_tarjeta("");
		caja.setTipo_cuenta("");
		caja.setCuenta_tarjeta("");
		caja.setAmparador("");
		caja.setValor_cheque(0.0);
		caja.setNumero_cheque("");
		caja.setCuenta_cheque("");
		caja.setBanco_cheque("");
		caja.setSucursal_cheque(null);
		caja.setFecha_cheque(null);
		caja.setCreacion_date(facturacion.getCreacion_date());
		caja.setUltimo_update(facturacion.getUltimo_update());
		caja.setCreacion_user(facturacion.getCreacion_user());
		caja.setUltimo_user(facturacion.getUltimo_user());
		caja.setVendedor("");
		caja.setReferencia("");
		caja.setCodigo_credito("");
		caja.setConcepto("RECIBO DE CAJA COPAGO");
		caja.setPrefijo("");
		caja.setAnio(new java.text.SimpleDateFormat("yyyy").format(facturacion
				.getFecha()));
		caja.setMes(new java.text.SimpleDateFormat("MM").format(facturacion
				.getFecha()));

		cajaService.crear(caja);
		consecutivoService.actualizarConsecutivoNota(
				facturacion.getCodigo_empresa(),
				facturacion.getCodigo_sucursal(), fuente, codigo_recibo);

		double valor_copago = facturacion.getValor_copago();
		double valor_total = facturacion.getValor_total();
		double dto_copago = facturacion.getDto_copago();

		int i = 1;
		for (Detalle_factura detalle_factura : lista_detalle) {
			String tipo_caja = "";
			boolean fact = true;
			String tipo_detalle = detalle_factura.getTipo();

			// Obtenemos el tipo de detalle caja //
			if (detalle_factura.getTipo().equals("ESTANCIA")) {
				tipo_caja = "05";
			} else if (detalle_factura.getTipo().equals("CONSULTA")
					|| detalle_factura.getTipo().equals("PROCEDIMIENTO")
					|| detalle_factura.getTipo().equals("PROCEDIMIENTO MULT")) {
				tipo_caja = "01";
			} else if (detalle_factura.getTipo().equals("SERVICIO")) {
				tipo_caja = "02";
			} else if (detalle_factura.getTipo().equals("MEDICAMENTO")
					|| detalle_factura.getTipo().equals("MATERIALES/INSUMOS")) {
				tipo_caja = "03";
				fact = detalle_factura.getFacturable();
			}

			if (fact) {
				double valor_detalle = detalle_factura.getValor_total();
				double copago = (valor_copago * valor_detalle / valor_total);
				double dto = (dto_copago * valor_detalle / valor_total);

				Detalle_caja detalle = new Detalle_caja();
				detalle.setCodigo_empresa(caja.getCodigo_empresa());
				detalle.setCodigo_sucursal(caja.getCodigo_sucursal());
				detalle.setFuente(fuente);
				detalle.setCodigo_recibo(codigo_recibo);
				detalle.setConsecutivo(i + "");
				detalle.setNro_cuota("1");
				detalle.setCodigo_articulo(detalle_factura.getCodigo_articulo());
				detalle.setDetalle(detalle_factura.getDetalle());
				detalle.setCodigo_bodega(null);
				detalle.setCodigo_lote(null);
				detalle.setTipo(tipo_caja);
				detalle.setCantidad(detalle_factura.getCantidad());
				detalle.setValor_unitario(detalle_factura.getValor_unitario());
				detalle.setValor_total(detalle_factura.getValor_total());
				detalle.setCopago(copago);
				detalle.setDescuento(dto);
				detalle.setCreacion_date(caja.getCreacion_date());
				detalle.setUltimo_update(caja.getUltimo_update());
				detalle.setCreacion_user(caja.getCreacion_user());
				detalle.setUltimo_user(caja.getUltimo_user());
				detalle_cajaDao.crear(detalle);

				if (tipo_detalle.equals("ESTANCIA")) {
					Admision_cama admision_cama = new Admision_cama();
					admision_cama
							.setCodigo_empresa(detalle.getCodigo_empresa());
					admision_cama.setCodigo_sucursal(detalle
							.getCodigo_sucursal());
					admision_cama.setNro_ingreso(caja.getNro_ingreso());
					admision_cama.setNro_identificacion(caja
							.getCodigo_tercero());
					admision_cama = admision_camaDao.consultar(admision_cama);
					if (admision_cama != null) {
						admision_cama.setCancelo_copago("S");
						admision_camaDao.actualizar(admision_cama);
					}
				} else if (tipo_detalle.equals("CONSULTA")) {
					Datos_consulta dc = new Datos_consulta();
					dc.setCodigo_empresa(detalle_factura.getCodigo_empresa());
					dc.setCodigo_sucursal(detalle_factura.getCodigo_sucursal());
					dc.setCodigo_registro(Utilidades
							.getValorLong(detalle_factura.getFactura_concepto()));
					dc = datos_consultaDao.consultar(dc);
					if (dc != null) {
						dc.setCancelo_copago("S");
						datos_consultaDao.actualizar(dc);
					}
				} else if (tipo_detalle.equals("PROCEDIMIENTO")
						|| tipo_detalle.equals("PROCEDIMIENTO MULT")) {
					Datos_procedimiento dp = new Datos_procedimiento();
					dp.setCodigo_empresa(detalle_factura.getCodigo_empresa());
					dp.setCodigo_sucursal(detalle_factura.getCodigo_sucursal());
					dp.setCodigo_registro(Utilidades
							.getValorLong(detalle_factura.getFactura_concepto()));
					dp.setConsecutivo_registro("1");
					dp = datos_procedimientoDao.consultar(dp);
					if (dp != null) {
						dp.setConsecutivo_registro(null);
						dp.setCancelo_copago("S");
						datos_procedimientoDao.actualizar(dp);
					}
				} else if (tipo_detalle.equals("MEDICAMENTO")
						|| tipo_detalle.equals("MATERIALES/INSUMOS")) {
					Datos_medicamentos datos_med = new Datos_medicamentos();
					datos_med.setCodigo_empresa(detalle_factura
							.getCodigo_empresa());
					datos_med.setCodigo_sucursal(detalle_factura
							.getCodigo_sucursal());
					datos_med.setCodigo_medicamento(detalle_factura
							.getCodigo_articulo());
					datos_med.setNro_factura(detalle_factura
							.getFactura_concepto());
					datos_med = datos_medicamentosDao.consultar(datos_med);
					if (datos_med != null) {
						datos_med.setCancelo_copago("S");
						datos_medicamentosDao.actualizar(datos_med);
					}
				} else if (tipo_detalle.equals("SERVICIO")) {
					Datos_servicio datos_serv = new Datos_servicio();
					datos_serv.setCodigo_empresa(detalle_factura
							.getCodigo_empresa());
					datos_serv.setCodigo_sucursal(detalle_factura
							.getCodigo_sucursal());
					datos_serv.setCodigo_servicio(detalle_factura
							.getCodigo_articulo());
					datos_serv.setNro_factura(detalle_factura
							.getFactura_concepto());
					datos_serv = datos_servicioDao.consultar(datos_serv);
					if (datos_serv != null) {
						datos_serv.setCancelo_copago("S");
						datos_servicioDao.actualizar(datos_serv);
					}
				}
				i++;
			}
		}

		if (cont
				&& !(facturacion.getTipo().equals(
						IConstantes.TIPO_FACTURA_EVENTO_IND) || facturacion
						.getTipo()
						.equals(IConstantes.TIPO_FACTURA_PORTABILIDAD))) {
			Map<String, Object> param_cont = new HashMap<String, Object>();
			param_cont.put("codigo_empresa", caja.getCodigo_empresa());
			param_cont.put("codigo_sucursal", caja.getCodigo_sucursal());
			param_cont.put("codigo_recibo", caja.getCodigo_recibo());
			param_cont.put("fuente", caja.getFuente());
			log.info("Aqui....");
			cajaService.guardarContabilizacionCopago(param_cont);
		}

		facturacion.setValor_copago(cajaService.totalCopagos(caja));
		facturacion.setCop_canc(cajaService.totalCopagos(caja));
		facturacion.setDto_copago(caja.getDescuento());
		facturacionDao.actualizar(facturacion);
	}

	// Borrar el copago si ya no aplica //
	private void eliminarCopago(Facturacion facturacion) {
		Caja caja_aux = new Caja();
		caja_aux.setCodigo_empresa(facturacion.getCodigo_empresa());
		caja_aux.setCodigo_sucursal(facturacion.getCodigo_sucursal());
		caja_aux.setCodigo_tercero(facturacion.getCodigo_tercero());
		caja_aux.setNro_ingreso(facturacion.getNro_ingreso());
		caja_aux = cajaService.consultar(caja_aux);
		if (caja_aux != null) {
			cajaService.eliminar(caja_aux);

			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(caja_aux.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(caja_aux.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(caja_aux.getFuente());
			nota_contable.setCodigo_documento(caja_aux.getCodigo_recibo());
			nota_contable = nota_contableDao.consultar(nota_contable);
			if (nota_contable != null) {
				nota_contableDao.eliminar(nota_contable);
			}
		}

	}

	/**
	 * Este metodo es para guardar la contabilizacion de una factura individual
	 * que son las de tipo EVENTO O PORTABILIDAD.
	 * 
	 * @param datos
	 * @param sw
	 * @param validaSiEvento
	 * @return
	 */
	public Nota_contable guardarContabilizacionIndividual(
			Map<String, Object> datos, boolean sw, boolean validaSiEvento) {
		try {
			Long id_factura = (Long) datos.get("id_factura");
			// Admision admision_inicial = (Admision) datos.get("admision");
			String codigo_comprobante = "15";
			// String tipo = "VENTA";
			String doc = "FH";
			String forma_pago = "02";

			Facturacion facturacion = new Facturacion();
			facturacion.setId_factura(id_factura);
			facturacion = facturacionDao.consultar(facturacion);
			if (facturacion == null && sw) {
				throw new ValidacionRunTimeException(
						"Este documento no se ha guardado todavia");
			}

			if (!sw && facturacion == null) {
				facturacion = (Facturacion) datos.get("facturacion");
				facturacion.setTipo(IConstantes.TIPO_FACTURA_EVENTO_IND);
			}

			if (!(facturacion.getTipo().equals(
					IConstantes.TIPO_FACTURA_EVENTO_IND) || facturacion
					.getTipo().equals(IConstantes.TIPO_FACTURA_PORTABILIDAD))
					&& validaSiEvento) {
				throw new ValidacionRunTimeException(
						"Esta factura no es evento por lo tanto no se puede contabilizar");
			}

			String codigo_empresa = facturacion.getCodigo_empresa();
			String codigo_sucursal = facturacion.getCodigo_sucursal();

			Resolucion res = new Resolucion();
			res.setCodigo_empresa(codigo_empresa);
			res = resolucionContDao.consultar(res);

			String cuenta_glosa = (res != null ? res.getCuenta_glosa() : "");
			String cuenta_particular = (res != null ? res
					.getCuenta_particular() : "");

			Contratos plan = new Contratos();
			plan.setCodigo_empresa(codigo_empresa);
			plan.setCodigo_sucursal(codigo_sucursal);
			plan.setCodigo_administradora(facturacion
					.getCodigo_administradora());
			plan.setId_plan(facturacion.getId_plan());
			plan = contratosDao.consultar(plan);
			String tipo_regimen = (plan != null ? plan.getTipo_usuario() : "2");

			Admision admision = new Admision();
			admision.setCodigo_empresa(plan.getCodigo_empresa());
			admision.setCodigo_sucursal(plan.getCodigo_sucursal());
			admision.setNro_identificacion(facturacion.getCodigo_tercero());
			admision.setNro_ingreso(facturacion.getNro_ingreso());
			admision = admisionDao.consultar(admision);
			String codigo_centro = (admision != null ? admision
					.getCodigo_centro() : "");
			String via_ingreso = (admision != null ? admision.getVia_ingreso()
					: "");
			boolean es_particular = (admision != null ? admision
					.getParticular().equals("S") ? true : false : false);

			Tercero tercero = new Tercero();
			tercero.setCodigo_empresa(codigo_empresa);
			tercero.setCodigo_sucursal(codigo_sucursal);
			tercero.setNro_identificacion(facturacion
					.getCodigo_administradora());
			tercero = terceroDao.consultar(tercero);

			// double tarifa_ica = (tercero != null ? tercero.getTarifa_ica() :
			// 0.0);
			// List<Detalle_factura> lista_detalle =
			// facturacion.getLista_detalle();
			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(facturacion.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(facturacion
					.getCodigo_comprobante());
			nota_contable.setCodigo_documento(facturacion
					.getCodigo_documento_res());
			nota_contable.setPrefijo(facturacion.getPrefijo());
			nota_contable.setAnio(facturacion.getAnio());
			nota_contable.setMes(facturacion.getMes());
			nota_contable.setFecha(facturacion.getFecha());
			nota_contable
					.setElaboro(facturacion.getCodigo_empleado() != null ? facturacion
							.getCodigo_empleado() : "");
			nota_contable.setCodigo_tercero(facturacion
					.getCodigo_administradora());
			nota_contable.setClasificacion(facturacion.getClasificacion());
			nota_contable.setConcepto(facturacion.getDescripcion());
			nota_contable.setVerificado(facturacion.getVerificado());
			nota_contable.setCreacion_date(facturacion.getCreacion_date());
			nota_contable.setUltimo_update(facturacion.getUltimo_update());
			nota_contable.setCreacion_user(facturacion.getCreacion_user());
			nota_contable.setUltimo_user(facturacion.getUltimo_user());
			nota_contable.setEstado("CANC");
			nota_contable.setForma_pago("");
			nota_contable.setDocumento_externo("");
			nota_contable.setBanco("");
			nota_contable.setMedio_pago("");
			nota_contable.setCodigo_rp(facturacion.getCodigo_rp());
			nota_contable.setCodigo_obligacion(facturacion
					.getCodigo_obligacion());
			nota_contable.setDocumento_externo(facturacion
					.getDocumento_externo());

			if (sw) {
				if (nota_contableDao.consultar(nota_contable) != null) {
					nota_contableDao.eliminar(nota_contable);
				}
				nota_contableDao.crear(nota_contable);
			}

			Map<String, Object> paramDetalle_factura = new HashMap<String, Object>();
			paramDetalle_factura.put("id_factura", facturacion.getId_factura());
			paramDetalle_factura.put("facturable", new Boolean(true));

			Map<String, Object> map = new HashMap<String, Object>();
			double total = 0;
			if (!sw) {
				total = facturacion.getValor_total()
						- facturacion.getValor_copago();
			} else {
				total = Utilidades.obtenerValorPrimitivo(detalleFacturaDao
						.totalFacturaClinica(paramDetalle_factura))
						- facturacion.getCop_canc();
			}

			double copago = facturacion.getCop_canc();
			double retencion = 0, valor_autoretencion = 0;
			String cuenta_autorete1 = (tercero != null ? tercero
					.getCuenta_autorete1() : "");
			String cuenta_autorete2 = (tercero != null ? tercero
					.getCuenta_autorete2() : "");

			Impuesto impuesto = new Impuesto();
			impuesto.setCodigo_empresa(codigo_empresa);
			impuesto.setCodigo_sucursal(codigo_sucursal);
			impuesto.setCodigo_cuenta((tercero != null ? tercero
					.getCuenta_retencion() : ""));
			impuesto.setAnio(facturacion.getAnio());
			impuesto = impuestoDao.consultar(impuesto);
			if (impuesto != null) {
				if (impuesto.getBase() <= total) {
					retencion = total * impuesto.getPorcentaje();
				}
			}

			impuesto = new Impuesto();
			impuesto.setCodigo_empresa(codigo_empresa);
			impuesto.setCodigo_sucursal(codigo_sucursal);
			impuesto.setCodigo_cuenta(cuenta_autorete1);
			impuesto.setAnio(facturacion.getAnio());
			impuesto = impuestoDao.consultar(impuesto);
			if (impuesto != null) {
				if (impuesto.getBase() <= total) {
					valor_autoretencion = total * impuesto.getPorcentaje();
				}
			}

			double descuento_glosa = (facturacion.getRadicado().equals("S") ? total
					- retencion - facturacion.getValor_pagar_factura()
					: 0);

			int consecutivo = 0;

			if (tercero != null) {
				String cuenta_cobrar = "";
				String nombre_regimen = "";
				if (!es_particular) {
					if (tipo_regimen.equals("1")) {
						cuenta_cobrar = tercero.getCuenta_cobrar2();
						nombre_regimen = "Contributivo";
					} else if (tipo_regimen.equals("2")) {
						cuenta_cobrar = tercero.getCuenta_cobrar();
						nombre_regimen = "Subsidiado";
					}
				} else {
					cuenta_cobrar = cuenta_particular;
					nombre_regimen = "Particular";
				}

				if (cuenta_cobrar.trim().equals("")) {
					throw new ValidacionRunTimeException("La aseguradora "
							+ tercero.getNombre1()
							+ " debe tener una cuenta por cobrar "
							+ nombre_regimen + " asignada");
				}
				if (descuento_glosa < 0) {
					throw new ValidacionRunTimeException(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar el valor a pagar de es mayor que el valor neto de la factura");
				}
				Detalle_nota detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(codigo_comprobante);
				detalle_nota.setCodigo_documento(facturacion
						.getCodigo_documento_res());
				detalle_nota.setConsecutivo(consecutivo + "");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota.setCodigo_cuenta(cuenta_cobrar);
				detalle_nota.setConcepto(facturacion.getDescripcion());
				detalle_nota.setTercero(facturacion.getCodigo_administradora());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(total - retencion - descuento_glosa);
				detalle_nota.setCredito(0);
				detalle_nota.setC_costo("");
				detalle_nota
						.setAbona("FV "
								+ codigo_comprobante
								+ "-"
								+ (!facturacion.getCodigo_documento_res()
										.equals("") ? Integer
										.parseInt(facturacion
												.getCodigo_documento_res())
										: ""));
				detalle_nota.setVence(facturacion.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				if (validaSiEvento) {
					map.put(tercero.getCuenta_cobrar() + "01", detalle_nota);
				}
				// detalle_notaDao.crear(detalle_nota);

				// consecutivo++;
				if (!tercero.getCuenta_retencion().trim().equals("")
						&& retencion > 0) {
					detalle_nota = new Detalle_nota();
					detalle_nota.setCodigo_empresa(codigo_empresa);
					detalle_nota.setCodigo_sucursal(codigo_sucursal);
					detalle_nota.setCodigo_comprobante(codigo_comprobante);
					detalle_nota.setCodigo_documento(facturacion
							.getCodigo_documento_res());
					detalle_nota.setConsecutivo(consecutivo + "");
					detalle_nota.setCreacion_date(nota_contable
							.getCreacion_date());
					detalle_nota.setUltimo_update(nota_contable
							.getUltimo_update());
					detalle_nota.setCreacion_user(nota_contable
							.getCreacion_user());
					detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
					detalle_nota
							.setCodigo_cuenta(tercero.getCuenta_retencion());
					detalle_nota.setConcepto("ANTICIPO RETENCION");
					detalle_nota.setTercero(facturacion
							.getCodigo_administradora());
					detalle_nota.setCheque("");
					detalle_nota.setDebito(retencion);
					detalle_nota.setCredito(0);
					detalle_nota.setC_costo("");
					detalle_nota.setAbona("");
					detalle_nota.setVence(facturacion.getFecha());
					detalle_nota.setBanco("");
					detalle_nota.setIva(0);
					detalle_nota.setCheque_consignado("N");
					detalle_nota.setTipo("");
					detalle_nota.setConcepto_abona("");
					// detalle_notaDao.crear(detalle_nota);
					if (validaSiEvento) {
						map.put(tercero.getCuenta_retencion() + "01",
								detalle_nota);
					}

					// consecutivo++;
				}

				if (valor_autoretencion > 0) {
					if (!cuenta_autorete1.isEmpty()) {
						detalle_nota = new Detalle_nota();
						detalle_nota.setCodigo_empresa(codigo_empresa);
						detalle_nota.setCodigo_sucursal(codigo_sucursal);
						detalle_nota.setCodigo_comprobante(codigo_comprobante);
						detalle_nota.setCodigo_documento(facturacion
								.getCodigo_documento_res());
						detalle_nota.setConsecutivo(consecutivo + "");
						detalle_nota.setCreacion_date(nota_contable
								.getCreacion_date());
						detalle_nota.setUltimo_update(nota_contable
								.getUltimo_update());
						detalle_nota.setCreacion_user(nota_contable
								.getCreacion_user());
						detalle_nota.setUltimo_user(nota_contable
								.getUltimo_user());
						detalle_nota.setCodigo_cuenta(cuenta_autorete1);
						detalle_nota.setConcepto("AUTORETENCION");
						detalle_nota.setTercero(facturacion
								.getCodigo_administradora());
						detalle_nota.setCheque("");
						detalle_nota.setDebito(valor_autoretencion);
						detalle_nota.setCredito(0);
						detalle_nota.setC_costo("");
						detalle_nota.setAbona("");
						detalle_nota.setVence(facturacion.getFecha());
						detalle_nota.setBanco("");
						detalle_nota.setIva(0);
						detalle_nota.setCheque_consignado("N");
						detalle_nota.setTipo("");
						detalle_nota.setConcepto_abona("");
						// detalle_notaDao.crear(detalle_nota);
						if (validaSiEvento) {
							map.put(cuenta_autorete1 + "01", detalle_nota);
						}
					}

					if (!cuenta_autorete2.isEmpty()) {
						detalle_nota = new Detalle_nota();
						detalle_nota.setCodigo_empresa(codigo_empresa);
						detalle_nota.setCodigo_sucursal(codigo_sucursal);
						detalle_nota.setCodigo_comprobante(codigo_comprobante);
						detalle_nota.setCodigo_documento(facturacion
								.getCodigo_documento_res());
						detalle_nota.setConsecutivo(consecutivo + "");
						detalle_nota.setCreacion_date(nota_contable
								.getCreacion_date());
						detalle_nota.setUltimo_update(nota_contable
								.getUltimo_update());
						detalle_nota.setCreacion_user(nota_contable
								.getCreacion_user());
						detalle_nota.setUltimo_user(nota_contable
								.getUltimo_user());
						detalle_nota.setCodigo_cuenta(cuenta_autorete2);
						detalle_nota.setConcepto("AUTORETENCION");
						detalle_nota.setTercero(facturacion
								.getCodigo_administradora());
						detalle_nota.setCheque("");
						detalle_nota.setDebito(0);
						detalle_nota.setCredito(valor_autoretencion);
						detalle_nota.setC_costo("");
						detalle_nota.setAbona("");
						detalle_nota.setVence(facturacion.getFecha());
						detalle_nota.setBanco("");
						detalle_nota.setIva(0);
						detalle_nota.setCheque_consignado("N");
						detalle_nota.setTipo("");
						detalle_nota.setConcepto_abona("");
						// detalle_notaDao.crear(detalle_nota);
						if (validaSiEvento) {
							map.put(cuenta_autorete2 + "01", detalle_nota);
						}
					}

					// consecutivo++;
				}

				if (facturacion.getRadicado().equals("S")
						&& descuento_glosa > 0) {
					if (cuenta_glosa.equals("")) {
						throw new ValidacionRunTimeException(
								"El documento "
										+ facturacion.getCodigo_documento_res()
										+ " no se pudo contabilizar configure una cuenta de glosa en parametros de sistema en contaweb");
					}
					if (facturacion.getValor_pagar_factura() <= 0) {
						throw new ValidacionRunTimeException(
								"El documento "
										+ facturacion.getCodigo_documento_res()
										+ " no se pudo contabilizar el valor a pagar de es menor o igual a cero");
					}
					detalle_nota = new Detalle_nota();
					detalle_nota.setCodigo_empresa(codigo_empresa);
					detalle_nota.setCodigo_sucursal(codigo_sucursal);
					detalle_nota.setCodigo_comprobante(codigo_comprobante);
					detalle_nota.setCodigo_documento(facturacion
							.getCodigo_documento_res());
					detalle_nota.setConsecutivo(consecutivo + "");
					detalle_nota.setCreacion_date(nota_contable
							.getCreacion_date());
					detalle_nota.setUltimo_update(nota_contable
							.getUltimo_update());
					detalle_nota.setCreacion_user(nota_contable
							.getCreacion_user());
					detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
					detalle_nota.setCodigo_cuenta(cuenta_glosa);
					detalle_nota.setConcepto("DESCUENTO POR GLOSA");
					detalle_nota.setTercero(facturacion
							.getCodigo_administradora());
					detalle_nota.setCheque("");
					detalle_nota.setDebito(descuento_glosa);
					detalle_nota.setCredito(0);
					detalle_nota.setC_costo("");
					detalle_nota.setAbona("");
					detalle_nota.setVence(facturacion.getFecha());
					detalle_nota.setBanco("");
					detalle_nota.setIva(0);
					detalle_nota.setCheque_consignado("N");
					detalle_nota.setTipo("");
					detalle_nota.setConcepto_abona("");
					// detalle_notaDao.crear(detalle_nota);
					if (validaSiEvento) {
						map.put(cuenta_glosa + "01", detalle_nota);
					}

					// consecutivo++;
				}
			}

			Map<String, Object> paramCentro = new HashMap<String, Object>();
			paramCentro.put("codigo_empresa", codigo_empresa);
			paramCentro.put("codigo_sucursal", codigo_sucursal);
			paramCentro.put("codigo_centro", codigo_centro);
			paramCentro.put("via_ingreso", via_ingreso);
			// log.info("paramCentro: " + paramCentro);
			// log.info("codigo_documento: " + codigo_documento);
			Centro_costo centro_costo = centro_costoDao
					.consultarVia_ingreso(paramCentro);
			String c_costo = "";
			if (centro_costo != null) {
				// c_costo =
				// consecutivoService.getZeroFill(centro_costo.getCodigo(), 4) ;
				c_costo = centro_costo.getCodigo();
			}

			// Contabilizacion estancias //
			Admision_cama admision_cama = new Admision_cama();
			admision_cama.setCodigo_empresa(plan.getCodigo_empresa());
			admision_cama.setCodigo_sucursal(plan.getCodigo_sucursal());
			admision_cama.setNro_ingreso(facturacion.getNro_ingreso());
			admision_cama
					.setNro_identificacion(facturacion.getCodigo_tercero());
			admision_cama = admision_camaDao.consultar(admision_cama);
			if (admision_cama != null) {

				Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
						.getManuales_tarifarios(admision);
				String codigo_plantilla = "";

				Map<String, Object> pcd = Utilidades.getProcedimiento(
						manuales_tarifarios,
						new Long(admision_cama.getCodigo_procedimiento()),
						ServiciosDisponiblesUtils.getServiceLocator());
				codigo_plantilla = (String) pcd.get("codigo_plantilla");

				double valor = 0;
				/*
				 * Timestamp date = facturacion.getFecha(); Timestamp date_sol =
				 * admision_cama.getFecha_ingreso();
				 */
				Timestamp date = admision_cama.getFecha_ingreso();
				Timestamp date_sol = facturacion.getFecha_final();
				long segundos = Math.abs(date.getTime() - date_sol.getTime()) / 1000;
				long dias = segundos / 86400;
				if (dias == 0 || dias == 1) {
					valor = admision_cama.getValor_dia();
				} else {
					valor = (dias - 1) * admision_cama.getValor_dia();
				}
				double dto = (((valor * 100) / (total + copago)) / 100)
						* copago;
				valor = valor - dto;

				Map<String, Object> paramDetalle_cont = new HashMap<String, Object>();
				paramDetalle_cont.put("codigo_empresa", codigo_empresa);
				paramDetalle_cont.put("codigo_sucursal", codigo_sucursal);
				paramDetalle_cont.put("doc", doc);
				paramDetalle_cont.put("tipo_contribuyente",
						(tercero != null ? tercero.getTipo_contribuyente()
								: "01"));
				paramDetalle_cont.put("forma_pago", forma_pago);
				paramDetalle_cont.put("codigo", codigo_plantilla);
				paramDetalle_cont
						.put("tipo_aseguradora",
								(tercero != null ? tercero
										.getTipo_aseguradora() : "01"));
				// log.info("paramDetalle_cont: " + paramDetalle_cont);
				// paramDetalle_cont.put("limit", limit);
				List<Detalle_contabilizacion> lista_detalle_cont = detalle_contabilizacionDao
						.listar(paramDetalle_cont);
				for (int j = 0; j < lista_detalle_cont.size(); j++) {
					Detalle_contabilizacion aux2 = (Detalle_contabilizacion) lista_detalle_cont
							.get(j);
					String codigo_cuenta = aux2.getCodigo_cuenta();
					String manejo = aux2.getMenajo();
					// String tipo_cuenta = "";
					if (map.get(codigo_cuenta + manejo + c_costo
							+ facturacion.getCodigo_documento_res()) == null) {
						if (valor != 0) {
							// //System.Out.Println("Codigo_cuenta: " +
							// codigo_cuenta);
							Detalle_nota detalle_nota = new Detalle_nota();
							detalle_nota.setCodigo_empresa(codigo_empresa);
							detalle_nota.setCodigo_sucursal(codigo_sucursal);
							detalle_nota
									.setCodigo_comprobante(codigo_comprobante);
							detalle_nota.setCodigo_documento(facturacion
									.getCodigo_documento_res());
							detalle_nota.setCreacion_date(nota_contable
									.getCreacion_date());
							detalle_nota.setUltimo_update(nota_contable
									.getUltimo_update());
							detalle_nota.setCreacion_user(nota_contable
									.getCreacion_user());
							detalle_nota.setUltimo_user(nota_contable
									.getUltimo_user());
							detalle_nota.setCodigo_cuenta(aux2
									.getCodigo_cuenta());
							detalle_nota.setConcepto(facturacion
									.getDescripcion());
							detalle_nota.setTercero(facturacion
									.getCodigo_administradora());
							detalle_nota.setCheque("");
							detalle_nota.setDebito(aux2.getMenajo()
									.equals("01") ? valor : 0);
							detalle_nota.setCredito(aux2.getMenajo().equals(
									"02") ? valor : 0);
							detalle_nota.setC_costo(c_costo);
							detalle_nota.setAbona("");
							detalle_nota.setVence(facturacion.getFecha());
							detalle_nota.setBanco("");
							detalle_nota.setIva(0);
							detalle_nota.setCheque_consignado("N");
							detalle_nota.setTipo("");
							detalle_nota.setConcepto_abona("");

							map.put(aux2.getCodigo_cuenta() + manejo + c_costo
									+ facturacion.getCodigo_documento_res(),
									detalle_nota);
						}
					} else {
						Detalle_nota detalle_nota = (Detalle_nota) map
								.get(codigo_cuenta + manejo + c_costo
										+ facturacion.getCodigo_documento_res());
						detalle_nota.setDebito(detalle_nota.getDebito()
								+ (aux2.getMenajo().equals("01") ? valor : 0));
						detalle_nota.setCredito(detalle_nota.getCredito()
								+ (aux2.getMenajo().equals("02") ? valor : 0));
						map.put(aux2.getCodigo_cuenta() + manejo + c_costo
								+ facturacion.getCodigo_documento_res(),
								detalle_nota);
					}
				}

			}

			// Contabilizacion datos consultas //
			Map<String, Object> paramDatos_consultas = new HashMap<String, Object>();
			paramDatos_consultas.put("codigo_empresa", codigo_empresa);
			paramDatos_consultas.put("codigo_sucursal", codigo_sucursal);
			paramDatos_consultas.put("nro_ingreso",
					facturacion.getNro_ingreso());
			paramDatos_consultas.put("nro_identificacion",
					facturacion.getCodigo_tercero());
			// //log.info("paramDatos_consultas: "+paramDatos_consultas);
			List<Datos_consulta> lista_datos_cons = datos_consultaService
					.listarTabla(paramDatos_consultas);
			// //log.info("lista_datos_cons: "+lista_datos_cons.size());
			for (Datos_consulta elem : lista_datos_cons) {
				double dto = (((elem.getValor_neto() * 100) / (total + copago)) / 100)
						* copago;
				double valor = elem.getValor_neto() - dto;

				Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
						.getManuales_tarifarios(admision);

				Map<String, Object> pcd = Utilidades.getProcedimiento(
						manuales_tarifarios,
						new Long(elem.getCodigo_consulta()),
						ServiciosDisponiblesUtils.getServiceLocator());
				String codigo_plantilla = (String) pcd.get("codigo_plantilla");
				String nombre_pcd = (String) pcd.get("nombre_procedimiento");
				if (codigo_plantilla.trim().equals("")) {
					throw new Exception("El procedimiento " + nombre_pcd
							+ " no tiene contabilizacion asignada");
				}

				Map<String, Object> paramDetalle_cont = new HashMap<String, Object>();
				paramDetalle_cont.put("codigo_empresa", codigo_empresa);
				paramDetalle_cont.put("codigo_sucursal", codigo_sucursal);
				paramDetalle_cont.put("doc", doc);
				paramDetalle_cont.put("tipo_contribuyente",
						(tercero != null ? tercero.getTipo_contribuyente()
								: "01"));
				paramDetalle_cont.put("forma_pago", forma_pago);
				paramDetalle_cont.put("codigo", codigo_plantilla);
				paramDetalle_cont
						.put("tipo_aseguradora",
								(tercero != null ? tercero
										.getTipo_aseguradora() : "01"));
				// log.info("paramDetalle_cont: " + paramDetalle_cont);
				// paramDetalle_cont.put("limit", limit);
				List<Detalle_contabilizacion> lista_detalle_cont = detalle_contabilizacionDao
						.listar(paramDetalle_cont);
				// log.info("lista_detalle_cont: " + lista_detalle_cont);
				for (int j = 0; j < lista_detalle_cont.size(); j++) {
					Detalle_contabilizacion aux2 = (Detalle_contabilizacion) lista_detalle_cont
							.get(j);
					String codigo_cuenta = aux2.getCodigo_cuenta();
					String manejo = aux2.getMenajo();
					// String tipo_cuenta = "";
					if (map.get(codigo_cuenta + manejo + c_costo
							+ facturacion.getCodigo_documento_res()) == null) {
						if (valor != 0) {
							// //System.Out.Println("Codigo_cuenta: " +
							// codigo_cuenta);
							Detalle_nota detalle_nota = new Detalle_nota();
							detalle_nota.setCodigo_empresa(codigo_empresa);
							detalle_nota.setCodigo_sucursal(codigo_sucursal);
							detalle_nota
									.setCodigo_comprobante(codigo_comprobante);
							detalle_nota.setCodigo_documento(facturacion
									.getCodigo_documento_res());
							detalle_nota.setCreacion_date(nota_contable
									.getCreacion_date());
							detalle_nota.setUltimo_update(nota_contable
									.getUltimo_update());
							detalle_nota.setCreacion_user(nota_contable
									.getCreacion_user());
							detalle_nota.setUltimo_user(nota_contable
									.getUltimo_user());
							detalle_nota.setCodigo_cuenta(aux2
									.getCodigo_cuenta());
							detalle_nota.setConcepto(facturacion
									.getDescripcion());
							detalle_nota.setTercero(facturacion
									.getCodigo_administradora());
							detalle_nota.setCheque("");
							detalle_nota.setDebito(aux2.getMenajo()
									.equals("01") ? valor : 0);
							detalle_nota.setCredito(aux2.getMenajo().equals(
									"02") ? valor : 0);
							detalle_nota.setC_costo(c_costo);
							detalle_nota.setAbona("");
							detalle_nota.setVence(facturacion.getFecha());
							detalle_nota.setBanco("");
							detalle_nota.setIva(0);
							detalle_nota.setCheque_consignado("N");
							detalle_nota.setTipo("");
							detalle_nota.setConcepto_abona("");

							map.put(aux2.getCodigo_cuenta() + manejo + c_costo
									+ facturacion.getCodigo_documento_res(),
									detalle_nota);
						}
					} else {
						Detalle_nota detalle_nota = (Detalle_nota) map
								.get(codigo_cuenta + manejo + c_costo
										+ facturacion.getCodigo_documento_res());
						detalle_nota.setDebito(detalle_nota.getDebito()
								+ (aux2.getMenajo().equals("01") ? valor : 0));
						detalle_nota.setCredito(detalle_nota.getCredito()
								+ (aux2.getMenajo().equals("02") ? valor : 0));
						map.put(aux2.getCodigo_cuenta() + manejo + c_costo
								+ facturacion.getCodigo_documento_res(),
								detalle_nota);
					}
				}
			}

			// Contabilizacion datos procedimientos //
			Map<String, Object> paramDatos_procedimientos = new HashMap<String, Object>();
			paramDatos_procedimientos.put("codigo_empresa", codigo_empresa);
			paramDatos_procedimientos.put("codigo_sucursal", codigo_sucursal);
			paramDatos_procedimientos.put("nro_ingreso",
					facturacion.getNro_ingreso());
			paramDatos_procedimientos.put("nro_identificacion",
					facturacion.getCodigo_tercero());
			List<Datos_procedimiento> lista_datos_pro = datos_procedimientoService
					.listarTabla(paramDatos_procedimientos);
			for (Datos_procedimiento elem : lista_datos_pro) {

				Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
						.getManuales_tarifarios(admision);

				double dto = (((elem.getValor_neto() * 100) / (total + copago)) / 100)
						* copago;
				double valor = elem.getValor_neto() - dto;

				Map<String, Object> pcd = Utilidades.getProcedimiento(
						manuales_tarifarios,
						new Long(elem.getCodigo_procedimiento()),
						ServiciosDisponiblesUtils.getServiceLocator());
				String codigo_plantilla = (String) pcd.get("codigo_plantilla");
				String nombre_pcd = (String) pcd.get("nombre_procedimiento");
				if (codigo_plantilla.trim().equals("") && sw) {
					throw new ValidacionRunTimeException("El procedimiento "
							+ nombre_pcd + " no tiene contabilizacion asignada");
				}

				Map<String, Object> paramDetalle_cont = new HashMap<String, Object>();
				paramDetalle_cont.put("codigo_empresa", codigo_empresa);
				paramDetalle_cont.put("codigo_sucursal", codigo_sucursal);
				paramDetalle_cont.put("doc", doc);
				paramDetalle_cont.put("tipo_contribuyente",
						(tercero != null ? tercero.getTipo_contribuyente()
								: "01"));
				paramDetalle_cont.put("forma_pago", forma_pago);
				paramDetalle_cont.put("codigo", codigo_plantilla);
				paramDetalle_cont
						.put("tipo_aseguradora",
								(tercero != null ? tercero
										.getTipo_aseguradora() : "01"));
				List<Detalle_contabilizacion> lista_detalle_cont = detalle_contabilizacionDao
						.listar(paramDetalle_cont);
				for (int j = 0; j < lista_detalle_cont.size(); j++) {
					Detalle_contabilizacion aux2 = (Detalle_contabilizacion) lista_detalle_cont
							.get(j);
					String codigo_cuenta = aux2.getCodigo_cuenta();
					String manejo = aux2.getMenajo();
					if (map.get(codigo_cuenta + manejo + c_costo
							+ facturacion.getCodigo_documento_res()) == null) {
						if (valor != 0) {
							Detalle_nota detalle_nota = new Detalle_nota();
							detalle_nota.setCodigo_empresa(codigo_empresa);
							detalle_nota.setCodigo_sucursal(codigo_sucursal);
							detalle_nota
									.setCodigo_comprobante(codigo_comprobante);
							detalle_nota.setCodigo_documento(facturacion
									.getCodigo_documento_res());
							detalle_nota.setCreacion_date(nota_contable
									.getCreacion_date());
							detalle_nota.setUltimo_update(nota_contable
									.getUltimo_update());
							detalle_nota.setCreacion_user(nota_contable
									.getCreacion_user());
							detalle_nota.setUltimo_user(nota_contable
									.getUltimo_user());
							detalle_nota.setCodigo_cuenta(aux2
									.getCodigo_cuenta());
							detalle_nota.setConcepto(facturacion
									.getDescripcion());
							detalle_nota.setTercero(facturacion
									.getCodigo_administradora());
							detalle_nota.setCheque("");
							detalle_nota.setDebito(aux2.getMenajo()
									.equals("01") ? valor : 0);
							detalle_nota.setCredito(aux2.getMenajo().equals(
									"02") ? valor : 0);
							detalle_nota.setC_costo(c_costo);
							detalle_nota.setAbona("");
							detalle_nota.setVence(facturacion.getFecha());
							detalle_nota.setBanco("");
							detalle_nota.setIva(0);
							detalle_nota.setCheque_consignado("N");
							detalle_nota.setTipo("");
							detalle_nota.setConcepto_abona("");

							map.put(aux2.getCodigo_cuenta() + manejo + c_costo
									+ facturacion.getCodigo_documento_res(),
									detalle_nota);
						}

					} else {
						Detalle_nota detalle_nota = (Detalle_nota) map
								.get(codigo_cuenta + manejo + c_costo
										+ facturacion.getCodigo_documento_res());
						detalle_nota.setDebito(detalle_nota.getDebito()
								+ (aux2.getMenajo().equals("01") ? valor : 0));
						detalle_nota.setCredito(detalle_nota.getCredito()
								+ (aux2.getMenajo().equals("02") ? valor : 0));
						map.put(aux2.getCodigo_cuenta() + manejo + c_costo
								+ facturacion.getCodigo_documento_res(),
								detalle_nota);
					}
				}
			}

			// Contabilizacion medicamentos/insumos //
			Map<String, Object> mapDatos = new HashMap<String, Object>();
			mapDatos.put("codigo_empresa", codigo_empresa);
			mapDatos.put("codigo_sucursal", codigo_sucursal);
			mapDatos.put("nro_ingreso", facturacion.getNro_ingreso());
			mapDatos.put("nro_identificacion", facturacion.getCodigo_tercero());
			// mapDatos.put("tipo", tipo);
			List<Datos_medicamentos> lista_aux_med = datos_medicamentosDao
					.listar(mapDatos);
			List<Datos_medicamentos> lista_datos_med = new LinkedList<Datos_medicamentos>();
			for (Datos_medicamentos dm : lista_aux_med) {
				Detalle_factura detalle_factura = new Detalle_factura();
				detalle_factura.setCodigo_empresa(codigo_empresa);
				detalle_factura.setCodigo_sucursal(codigo_sucursal);
				detalle_factura.setId_factura(id_factura);
				detalle_factura.setCodigo_articulo(dm.getCodigo_medicamento());
				detalle_factura.setFactura_concepto(dm.getNro_factura());
				detalle_factura.setFacturable(true);
				detalle_factura = detalleFacturaDao
						.consultar_facturable(detalle_factura);

				boolean fact = true;
				if (detalle_factura != null) {
					fact = detalle_factura.getFacturable();
				}
				if (fact) {
					lista_datos_med.add(dm);
				}
			}

			for (Datos_medicamentos elem : lista_datos_med) {
				Articulo pd = new Articulo();
				pd.setCodigo_empresa(codigo_empresa);
				pd.setCodigo_sucursal(codigo_sucursal);
				pd.setCodigo_articulo(elem.getCodigo_medicamento());
				pd = articuloDao.consultar(pd);

				Bodega_articulo bod = new Bodega_articulo();
				bod.setCodigo_empresa(codigo_empresa);
				bod.setCodigo_sucursal(codigo_sucursal);
				bod.setCodigo_sucursal(codigo_sucursal);
				bod.setCodigo_bodega(elem.getCodigo_bodega());
				bod.setCodigo_articulo(elem.getCodigo_medicamento());
				bod = bodega_articuloDao.consultar(bod);
				double costo = (bod != null ? (bod.getCosto() * elem
						.getUnidades()) : 0);

				double dto = (((elem.getValor_total() * 100) / (total + copago)) / 100)
						* copago;
				double valor = elem.getValor_total() - dto;
				String codigo_plantilla = (pd != null ? pd
						.getCodigo_contabilidad() : "");
				if (codigo_plantilla.trim().equals("")) {
					throw new ValidacionRunTimeException("El medicamento "
							+ (pd != null ? pd.getNombre1() : "")
							+ " no tiene contabilizacion asignada");
				}
				Map<String, Object> paramDetalle_cont = new HashMap<String, Object>();
				paramDetalle_cont.put("codigo_empresa", codigo_empresa);
				paramDetalle_cont.put("codigo_sucursal", codigo_sucursal);
				paramDetalle_cont.put("doc", doc);
				paramDetalle_cont.put("tipo_contribuyente",
						(tercero != null ? tercero.getTipo_contribuyente()
								: "01"));
				paramDetalle_cont.put("forma_pago", forma_pago);
				paramDetalle_cont.put("codigo", codigo_plantilla);
				paramDetalle_cont
						.put("tipo_aseguradora",
								(tercero != null ? tercero
										.getTipo_aseguradora() : "01"));
				List<Detalle_contabilizacion> lista_detalle_cont = detalle_contabilizacionDao
						.listar(paramDetalle_cont);
				for (int j = 0; j < lista_detalle_cont.size(); j++) {
					Detalle_contabilizacion aux2 = (Detalle_contabilizacion) lista_detalle_cont
							.get(j);
					String codigo_cuenta = aux2.getCodigo_cuenta();
					String manejo = aux2.getMenajo();
					Puc puc = new Puc();
					puc.setCodigo_empresa(codigo_empresa);
					puc.setCodigo_sucursal(codigo_sucursal);
					puc.setCodigo_cuenta(codigo_cuenta);
					puc = pucDao.consultar(puc);
					String tipo_cuenta = (puc != null ? puc.getTipo_cuenta()
							: "");
					double saldo = 0;
					if (tipo_cuenta.equals("04") || tipo_cuenta.equals("11")) {
						saldo = costo;
					} else {
						saldo = valor;
					}
					if (map.get(codigo_cuenta + manejo + c_costo
							+ facturacion.getCodigo_documento_res()) == null) {
						if (valor != 0) {
							Detalle_nota detalle_nota = new Detalle_nota();
							detalle_nota.setCodigo_empresa(codigo_empresa);
							detalle_nota.setCodigo_sucursal(codigo_sucursal);
							detalle_nota
									.setCodigo_comprobante(codigo_comprobante);
							detalle_nota.setCodigo_documento(facturacion
									.getCodigo_documento_res());
							detalle_nota.setCreacion_date(nota_contable
									.getCreacion_date());
							detalle_nota.setUltimo_update(nota_contable
									.getUltimo_update());
							detalle_nota.setCreacion_user(nota_contable
									.getCreacion_user());
							detalle_nota.setUltimo_user(nota_contable
									.getUltimo_user());
							detalle_nota.setCodigo_cuenta(aux2
									.getCodigo_cuenta());
							detalle_nota.setConcepto(facturacion
									.getDescripcion());
							detalle_nota.setTercero(facturacion
									.getCodigo_administradora());
							detalle_nota.setCheque("");
							detalle_nota.setDebito(aux2.getMenajo()
									.equals("01") ? saldo : 0);
							detalle_nota.setCredito(aux2.getMenajo().equals(
									"02") ? saldo : 0);
							detalle_nota.setC_costo(c_costo);
							detalle_nota.setAbona("");
							detalle_nota.setVence(facturacion.getFecha());
							detalle_nota.setBanco("");
							detalle_nota.setIva(0);
							detalle_nota.setCheque_consignado("N");
							detalle_nota.setTipo("");
							detalle_nota.setConcepto_abona("");

							map.put(aux2.getCodigo_cuenta() + manejo + c_costo
									+ facturacion.getCodigo_documento_res(),
									detalle_nota);
						}

					} else {
						Detalle_nota detalle_nota = (Detalle_nota) map
								.get(codigo_cuenta + manejo + c_costo
										+ facturacion.getCodigo_documento_res());
						detalle_nota.setDebito(detalle_nota.getDebito()
								+ (aux2.getMenajo().equals("01") ? valor : 0));
						detalle_nota.setCredito(detalle_nota.getCredito()
								+ (aux2.getMenajo().equals("02") ? valor : 0));
						map.put(aux2.getCodigo_cuenta() + manejo + c_costo
								+ facturacion.getCodigo_documento_res(),
								detalle_nota);
					}
				}

			}

			// Contabilizacion servicios //
			Map<String, Object> mapDatosServicios = new HashMap<String, Object>();
			mapDatosServicios.put("codigo_empresa", codigo_empresa);
			mapDatosServicios.put("codigo_sucursal", codigo_sucursal);
			mapDatosServicios.put("nro_ingreso", facturacion.getNro_ingreso());
			mapDatosServicios.put("nro_identificacion",
					facturacion.getCodigo_tercero());
			List<Datos_servicio> lista_datos_sers = datos_servicioDao
					.listar(mapDatosServicios);

			for (Datos_servicio elem : lista_datos_sers) {

				Articulo pd = new Articulo();
				pd.setCodigo_empresa(codigo_empresa);
				pd.setCodigo_sucursal(codigo_sucursal);
				pd.setCodigo_articulo(elem.getCodigo_servicio());
				pd = articuloDao.consultar(pd);

				double dto = (((elem.getValor_total() * 100) / (total + copago)) / 100)
						* copago;
				double valor = elem.getValor_total() - dto;
				String codigo_plantilla = (pd != null ? pd
						.getCodigo_contabilidad() : "");
				if (codigo_plantilla.trim().equals("")) {
					throw new ValidacionRunTimeException("El servicio "
							+ (pd != null ? pd.getNombre1() : "")
							+ " no tiene contabilizacion asignada");
				}

				Map<String, Object> paramDetalle_cont = new HashMap<String, Object>();
				paramDetalle_cont.put("codigo_empresa", codigo_empresa);
				paramDetalle_cont.put("codigo_sucursal", codigo_sucursal);
				paramDetalle_cont.put("doc", doc);
				paramDetalle_cont.put("tipo_contribuyente",
						(tercero != null ? tercero.getTipo_contribuyente()
								: "01"));
				paramDetalle_cont.put("forma_pago", forma_pago);
				paramDetalle_cont.put("codigo", codigo_plantilla);
				paramDetalle_cont
						.put("tipo_aseguradora",
								(tercero != null ? tercero
										.getTipo_aseguradora() : "01"));
				List<Detalle_contabilizacion> lista_detalle_cont = detalle_contabilizacionDao
						.listar(paramDetalle_cont);

				for (int j = 0; j < lista_detalle_cont.size(); j++) {
					Detalle_contabilizacion aux2 = (Detalle_contabilizacion) lista_detalle_cont
							.get(j);
					String codigo_cuenta = aux2.getCodigo_cuenta();
					String manejo = aux2.getMenajo();

					if (map.get(codigo_cuenta + manejo + c_costo
							+ facturacion.getCodigo_documento_res()) == null) {
						if (valor != 0) {
							Detalle_nota detalle_nota = new Detalle_nota();
							detalle_nota.setCodigo_empresa(codigo_empresa);
							detalle_nota.setCodigo_sucursal(codigo_sucursal);
							detalle_nota
									.setCodigo_comprobante(codigo_comprobante);
							detalle_nota.setCodigo_documento(facturacion
									.getCodigo_documento_res());
							detalle_nota.setCreacion_date(nota_contable
									.getCreacion_date());
							detalle_nota.setUltimo_update(nota_contable
									.getUltimo_update());
							detalle_nota.setCreacion_user(nota_contable
									.getCreacion_user());
							detalle_nota.setUltimo_user(nota_contable
									.getUltimo_user());
							detalle_nota.setCodigo_cuenta(aux2
									.getCodigo_cuenta());
							detalle_nota.setConcepto(facturacion
									.getDescripcion());
							detalle_nota.setTercero(facturacion
									.getCodigo_administradora());
							detalle_nota.setCheque("");
							detalle_nota.setDebito(aux2.getMenajo()
									.equals("01") ? valor : 0);
							detalle_nota.setCredito(aux2.getMenajo().equals(
									"02") ? valor : 0);
							detalle_nota.setC_costo(c_costo);
							detalle_nota.setAbona("");
							detalle_nota.setVence(facturacion.getFecha());
							detalle_nota.setBanco("");
							detalle_nota.setIva(0);
							detalle_nota.setCheque_consignado("N");
							detalle_nota.setTipo("");
							detalle_nota.setConcepto_abona("");

							map.put(aux2.getCodigo_cuenta() + manejo + c_costo
									+ facturacion.getCodigo_documento_res(),
									detalle_nota);
						}

					} else {
						Detalle_nota detalle_nota = (Detalle_nota) map
								.get(codigo_cuenta + manejo + c_costo
										+ facturacion.getCodigo_documento_res());
						// //System.Out.Println("detalle_nota: " +
						// detalle_nota);
						detalle_nota.setDebito(detalle_nota.getDebito()
								+ (aux2.getMenajo().equals("01") ? valor : 0));
						detalle_nota.setCredito(detalle_nota.getCredito()
								+ (aux2.getMenajo().equals("02") ? valor : 0));
						map.put(aux2.getCodigo_cuenta() + manejo + c_costo
								+ facturacion.getCodigo_documento_res(),
								detalle_nota);
					}
				}

			}

			// //
			int index = 1;
			double dbt = 0, cdt = 0;
			for (String key_map : map.keySet()) {
				Detalle_nota detalle_nota = (Detalle_nota) map.get(key_map);
				detalle_nota.setDebito(Double.parseDouble(String.format(
						Locale.ENGLISH, "%.2f", detalle_nota.getDebito())));
				detalle_nota.setCredito(Double.parseDouble(String.format(
						Locale.ENGLISH, "%.2f", detalle_nota.getCredito())));
				// //System.Out.Println(detalle_nota.getCodigo_cuenta()+":"+detalle_nota.getDebito()+"   "+detalle_nota.getCredito());
				dbt += detalle_nota.getDebito();
				cdt += detalle_nota.getCredito();
			}
			dbt = Double
					.parseDouble(String.format(Locale.ENGLISH, "%.2f", dbt));
			cdt = Double
					.parseDouble(String.format(Locale.ENGLISH, "%.2f", cdt));

			double dif = 0;
			if (((int) dbt) == ((int) cdt)) {
				dif = dbt - cdt;
			}
			if (dif != 0) {
				if (dif < 0) {
					cdt -= Math.abs(dif);
				} else {
					dbt -= Math.abs(dif);
				}
				boolean entro = false;
				for (String key_map : map.keySet()) {
					Detalle_nota detalle_nota = (Detalle_nota) map.get(key_map);
					if (dif < 0 && detalle_nota.getCredito() > 0 && !entro) {
						detalle_nota.setCredito(detalle_nota.getCredito()
								- Math.abs(dif));
						entro = true;
					} else if (dif > 0 && detalle_nota.getDebito() > 0
							&& !entro) {
						detalle_nota.setDebito(detalle_nota.getDebito()
								- Math.abs(dif));
						entro = true;
					}
				}
			}
			// log.info("dbt: " + dbt);
			// log.info("cdt: " + cdt);

			if ((dbt == 0 && cdt == 0) && sw) {
				throw new ValidacionRunTimeException(
						"Los saldos  "
								+ facturacion.getCodigo_documento_res()
								+ " del asiento contable suma 0. Revise la contabilizacion de los servicios del detalle del documento");
			}
			if ((dbt != cdt) && sw) {
				throw new ValidacionRunTimeException(
						"El documento "
								+ facturacion.getCodigo_documento_res()
								+ " esta descuadrado. Revise la contabilizacion de los servicios del detalle del documento");
			}

			List<Detalle_nota> lista_detalle_nota = new ArrayList<Detalle_nota>();
			for (String key_map : map.keySet()) {
				Detalle_nota detalle_nota = (Detalle_nota) map.get(key_map);
				detalle_nota.setConsecutivo(index + "");
				lista_detalle_nota.add(detalle_nota);
				if (sw) {
					detalle_notaDao.crear(detalle_nota);
				}
				index++;
			}
			nota_contable.setLista_detalle(lista_detalle_nota);
			// //

			// String codigo_tercero = facturacion.getCodigo_administradora();
			// if (admision_inicial != null
			// && admision_inicial.getParticular().equals("S")) {
			// codigo_tercero = admision_inicial.getNro_identificacion();
			// }
			if (sw) {
				facturacion.setEstado("CONT");
				facturacionDao.actualizar(facturacion);
			}

			Caja caja = new Caja();
			caja.setCodigo_empresa(codigo_empresa);
			caja.setCodigo_sucursal(codigo_sucursal);
			caja.setNro_ingreso(facturacion.getNro_ingreso());
			caja.setCodigo_tercero(facturacion.getCodigo_tercero());
			caja = cajaService.consultar(caja);

			if (caja != null && sw) {
				Map<String, Object> paramDetalle_caja = new HashMap<String, Object>();
				paramDetalle_caja.put("codigo_empresa", codigo_empresa);
				paramDetalle_caja.put("codigo_sucursal", codigo_sucursal);
				paramDetalle_caja.put("fuente", caja.getFuente());
				paramDetalle_caja.put("codigo_recibo", caja.getCodigo_recibo());
				List<Detalle_caja> lista_detalle_caja = detalle_cajaDao
						.listar(paramDetalle_caja);

				nota_contable = new Nota_contable();
				nota_contable.setCodigo_empresa(caja.getCodigo_empresa());
				nota_contable.setCodigo_sucursal(caja.getCodigo_sucursal());
				nota_contable.setCodigo_comprobante(caja.getFuente());
				nota_contable.setCodigo_documento(caja.getCodigo_recibo());
				nota_contable.setPrefijo("");
				nota_contable.setAnio(new java.text.SimpleDateFormat("yyyy")
						.format(caja.getFecha()));
				nota_contable.setMes(new java.text.SimpleDateFormat("MM")
						.format(caja.getFecha()));
				nota_contable.setFecha(caja.getFecha());
				nota_contable.setElaboro("");
				nota_contable.setCodigo_tercero(caja.getCodigo_tercero());
				nota_contable.setClasificacion("");
				nota_contable.setConcepto("RECIBO DE CAJA");
				nota_contable.setVerificado("");
				nota_contable.setCreacion_date(caja.getCreacion_date());
				nota_contable.setUltimo_update(caja.getUltimo_update());
				nota_contable.setCreacion_user(caja.getCreacion_user());
				nota_contable.setUltimo_user(caja.getUltimo_user());
				nota_contable.setEstado("CANC");
				nota_contable.setForma_pago("");
				nota_contable.setDocumento_externo("");
				nota_contable.setBanco("");
				nota_contable.setMedio_pago("");
				if (sw) {
					if (nota_contableDao.consultar(nota_contable) != null) {
						nota_contableDao.eliminar(nota_contable);
					}
					nota_contableDao.crear(nota_contable);
				}

				double saldo = 0;
				for (int i = 0; i < lista_detalle_caja.size(); i++) {
					Detalle_caja aux = (Detalle_caja) lista_detalle_caja.get(i);
					saldo += (aux.getCopago() == 0 ? aux.getValor_total() : aux
							.getCopago());
				}
				if (res == null) {
					throw new ValidacionRunTimeException(
							"Debe parametrizar en Parametros de la Empresa en Contaweb cual es su cuenta de caja y de Copago");
				} else {
					if (res.getCuenta_caja().trim().equals("") && sw) {
						throw new ValidacionRunTimeException(
								"Debe parametrizar en Parametros de la Empresa en Contaweb cual es su cuenta de caja");
					}
					if (res.getCuenta_copago().trim().equals("") && sw) {
						throw new ValidacionRunTimeException(
								"Debe parametrizar en Parametros de la Empresa en Contaweb cual es su cuenta de ingreso por copago");
					}
				}

				Detalle_nota detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(caja.getFuente());
				detalle_nota.setCodigo_documento(caja.getCodigo_recibo());
				detalle_nota.setConsecutivo("0");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota.setCodigo_cuenta((res != null ? res
						.getCuenta_caja() : ""));
				detalle_nota.setConcepto(caja.getConcepto());
				detalle_nota.setTercero(caja.getCodigo_tercero());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(Integer.parseInt(Utilidades
						.formatDecimal(saldo, "#0")));
				detalle_nota.setCredito(0.0);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("");
				detalle_nota.setVence(caja.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				detalle_notaDao.crear(detalle_nota);

				detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(caja.getFuente());
				detalle_nota.setCodigo_documento(caja.getCodigo_recibo());
				detalle_nota.setConsecutivo("1");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota.setCodigo_cuenta((res != null ? res
						.getCuenta_copago() : ""));
				detalle_nota.setConcepto(caja.getConcepto());
				detalle_nota.setTercero(caja.getCodigo_tercero());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(0.0);
				detalle_nota.setCredito(Integer.parseInt(Utilidades
						.formatDecimal(saldo, "#0")));
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("");
				detalle_nota.setVence(caja.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				detalle_notaDao.crear(detalle_nota);
			}
			return nota_contable;
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Nota_contable guardarContabilizacionCapitada(
			Map<String, Object> datos, boolean sw) {
		try {
			String codigo_empresa = (String) datos.get("codigo_empresa");

			Resolucion res = new Resolucion();
			res.setCodigo_empresa(codigo_empresa);
			res = resolucionContDao.consultar(res);

			boolean contabilizar_servicios_capita = res
					.getContabilizar_servicios_capita();
			if (contabilizar_servicios_capita) {
				return guardarContabilizacionCapitadaConServicios(datos, sw);
			} else {
				return guardarContabilizacionCapitadaSinServicios(datos, sw);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
     *
     *
     */
	@SuppressWarnings("unchecked")
	public Nota_contable guardarContabilizacionCapitadaConServicios(
			Map<String, Object> datos, boolean sw) {
		try {
			Long id_factura = (Long) datos.get("id_factura");
			// String tipo = "VENTA";
			String cuenta_cartera = "";

			Facturacion facturacion = new Facturacion();
			facturacion.setId_factura(id_factura);
			facturacion = facturacionDao.consultar(facturacion);

			String codigo_empresa = facturacion.getCodigo_empresa();
			String codigo_sucursal = facturacion.getCodigo_sucursal();
			String codigo_comprobante = facturacion.getCodigo_comprobante();

			Resolucion res = new Resolucion();
			res.setCodigo_empresa(codigo_empresa);
			res = resolucionContDao.consultar(res);

			String cuenta_margen_gasto_capita = (res != null ? res
					.getCuenta_margen_gasto_capita() : "");
			String cuenta_margen_ingreso_capita = (res != null ? res
					.getCuenta_margen_ingreso_capita() : "");

			String cuenta_glosa = (res != null ? res.getCuenta_glosa() : "");

			Tercero tercero = new Tercero();
			tercero.setCodigo_empresa(codigo_empresa);
			tercero.setCodigo_sucursal(codigo_sucursal);
			tercero.setNro_identificacion(facturacion
					.getCodigo_administradora());
			tercero = terceroDao.consultar(tercero);

			Map<String, Object> param_facturas_internas = new HashMap<String, Object>();
			param_facturas_internas.put("codigo_empresa",
					facturacion.getCodigo_empresa());
			param_facturas_internas.put("codigo_sucursal",
					facturacion.getCodigo_sucursal());
			param_facturas_internas.put("factura",
					facturacion.getCodigo_documento_res());
			param_facturas_internas.put("tipo", "CAP");

			List<Facturacion> listado_facturas_internas = facturacionDao
					.listarRegistros(param_facturas_internas);

			double saldo = 0;
			if (listado_facturas_internas.size() > 0
					&& facturacion.getTipo().equals("GEN_AGR")) {
				saldo = facturacion.getValor_total()
						- facturacion.getRetencion();
			} else if (facturacion.getTipo().equals("GEN_CAP")) {
				saldo = (facturacion.getValor_total() - facturacion
						.getRetencion());
			}
			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(facturacion.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(facturacion
					.getCodigo_comprobante());
			nota_contable.setCodigo_documento(facturacion
					.getCodigo_documento_res());
			nota_contable.setPrefijo(facturacion.getPrefijo());
			nota_contable.setAnio(facturacion.getAnio());
			nota_contable.setMes(facturacion.getMes());
			nota_contable.setFecha(facturacion.getFecha());
			nota_contable
					.setElaboro(facturacion.getCodigo_empleado() != null ? facturacion
							.getCodigo_empleado() : "");
			nota_contable.setCodigo_tercero(facturacion
					.getCodigo_administradora());
			nota_contable.setClasificacion(facturacion.getClasificacion());
			nota_contable.setConcepto(facturacion.getDescripcion());
			nota_contable.setVerificado(facturacion.getVerificado());
			nota_contable.setCreacion_date(facturacion.getCreacion_date());
			nota_contable.setUltimo_update(facturacion.getUltimo_update());
			nota_contable.setCreacion_user(facturacion.getCreacion_user());
			nota_contable.setUltimo_user(facturacion.getUltimo_user());
			nota_contable.setEstado("CANC");
			nota_contable.setForma_pago("");
			nota_contable.setDocumento_externo("");
			nota_contable.setBanco("");
			nota_contable.setMedio_pago("");
			// nota_contable.setCodigo_rp(facturacion.getCodigo_rp());
			// nota_contable.setCodigo_obligacion(facturacion.getCodigo_obligacion());
			nota_contable.setDocumento_externo(facturacion
					.getDocumento_externo());

			if (sw) {
				if (nota_contableDao.consultar(nota_contable) != null) {
					nota_contableDao.eliminar(nota_contable);
				}
				nota_contableDao.crear(nota_contable);
			}

			Contratos plan = new Contratos();
			plan.setCodigo_empresa(codigo_empresa);
			plan.setCodigo_sucursal(codigo_sucursal);
			plan.setCodigo_administradora(facturacion
					.getCodigo_administradora());
			plan.setId_plan(facturacion.getId_plan());
			plan = contratosDao.consultar(plan);
			String tipo_regimen = (plan != null ? plan.getTipo_usuario() : "2");

			String nombre_regimen = "";
			if (tipo_regimen.equals("1")) {
				cuenta_cartera = tercero.getCuenta_cobrar2();
				nombre_regimen = "Contributivo";
			} else if (tipo_regimen.equals("2")) {
				cuenta_cartera = tercero.getCuenta_cobrar();
				nombre_regimen = "Subsidiado";
			}

			if (saldo == 0 && sw) {
				throw new Exception("El saldo del asiento de la factura "
						+ facturacion.getCodigo_comprobante() + "-"
						+ facturacion.getCodigo_documento_res() + " es cero");
			}

			if (cuenta_cartera.equals("") && sw) {
				throw new Exception(
						"La cuenta por cobrar "
								+ nombre_regimen
								+ " esta en blanco configurela en el tercero y/o parametros de la empresa de la factura "
								+ facturacion.getCodigo_comprobante() + "-"
								+ facturacion.getCodigo_documento_res() + "");
			}

			double descuento_glosa = (facturacion.getRadicado().equals("S") ? saldo
					- facturacion.getValor_pagar_factura()
					: 0);

			if (descuento_glosa < 0 && sw) {
				throw new Exception(
						"El documento "
								+ facturacion.getCodigo_documento_res()
								+ " no se pudo contabilizar el valor a pagar de es mayor que el valor neto de la factura");
			}

			List<Detalle_nota> lista_detalle_nota = new LinkedList<Detalle_nota>();

			int consecutivo = 0;

			Detalle_nota detalle_nota = new Detalle_nota();
			detalle_nota.setCodigo_empresa(codigo_empresa);
			detalle_nota.setCodigo_sucursal(codigo_sucursal);
			detalle_nota.setCodigo_comprobante(codigo_comprobante);
			detalle_nota.setCodigo_documento(facturacion
					.getCodigo_documento_res());
			detalle_nota.setConsecutivo(consecutivo + "");
			detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
			detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
			detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
			detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
			detalle_nota.setCodigo_cuenta(cuenta_cartera);
			detalle_nota.setConcepto(facturacion.getDescripcion());
			detalle_nota.setTercero(facturacion.getCodigo_administradora());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(saldo - descuento_glosa);
			detalle_nota.setCredito(0.0);
			detalle_nota.setC_costo("");
			detalle_nota.setAbona("FV " + codigo_comprobante + "-"
					+ Integer.parseInt(facturacion.getCodigo_documento_res()));
			detalle_nota.setVence(facturacion.getFecha_vencimiento());
			detalle_nota.setBanco("");
			detalle_nota.setIva(0);
			detalle_nota.setCheque_consignado("N");
			detalle_nota.setTipo("");
			detalle_nota.setConcepto_abona("");
			lista_detalle_nota.add(detalle_nota);
			if (sw) {
				detalle_notaDao.crear(detalle_nota);
			}

			consecutivo++;

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("id_factura", facturacion.getId_factura());
			List<Detalle_factura> lista_detalle_factura = (List<Detalle_factura>) datos
					.get("lista_detalle");
			if (sw) {
				lista_detalle_factura = detalleFacturaDao.listar(parameters);
				// log.info("aqui............");
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			for (Detalle_factura detalle_factura : lista_detalle_factura) {
				Map<String, Object> parametroInd = new HashMap<String, Object>();
				parametroInd.put("id_factura", facturacion.getId_factura());

				Facturacion facturacionInd = new Facturacion();
				facturacionInd.setCodigo_empresa(codigo_empresa);
				facturacionInd.setCodigo_sucursal(codigo_sucursal);
				facturacionInd.setCodigo_comprobante("15");
				facturacionInd.setCodigo_documento_res(detalle_factura
						.getCodigo_articulo());
				// //log.info("facturacionInd antes: "+facturacionInd);
				facturacionInd = consultar(facturacionInd);

				if (!sw) {
					parametroInd.put("facturacion", facturacionInd);
				}

				Nota_contable nota_contableInd = guardarContabilizacionIndividual(
						parametroInd, false, false);
				List<Detalle_nota> lista_detalle_aux = nota_contableInd
						.getLista_detalle();
				for (Detalle_nota detalle_notaAux : lista_detalle_aux) {
					String codigo_cuenta = detalle_notaAux.getCodigo_cuenta();
					String c_costo = detalle_notaAux.getC_costo();

					if (Double.isNaN(detalle_notaAux.getDebito())) {
						detalle_notaAux.setDebito(0);
					}
					if (Double.isNaN(detalle_notaAux.getCredito())) {
						detalle_notaAux.setCredito(0);
					}

					if (map.get(codigo_cuenta + c_costo) == null) {
						map.put(codigo_cuenta + c_costo, detalle_notaAux);
					} else {
						Detalle_nota detalle_nota2 = (Detalle_nota) map
								.get(codigo_cuenta + c_costo);
						detalle_nota2.setDebito(detalle_nota2.getDebito()
								+ detalle_notaAux.getDebito());
						detalle_nota2.setCredito(detalle_nota2.getCredito()
								+ detalle_notaAux.getCredito());
						map.put(codigo_cuenta + c_costo, detalle_nota2);
					}
				}
			}

			double saldo_ingresos = 0;
			for (Object key_map : map.keySet()) {
				Detalle_nota detalle_nota2 = (Detalle_nota) map.get(key_map);
				detalle_nota2.setCodigo_comprobante(codigo_comprobante);
				detalle_nota2.setCodigo_documento(facturacion
						.getCodigo_documento_res());
				detalle_nota2.setConsecutivo(consecutivo + "");
				lista_detalle_nota.add(detalle_nota2);
				if (sw) {
					detalle_notaDao.crear(detalle_nota2);
				}
				saldo_ingresos += (detalle_nota2.getDebito() + detalle_nota2
						.getCredito());
				consecutivo++;
			}

			if (saldo_ingresos > saldo) {
				if (cuenta_margen_gasto_capita.equals("") && sw) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar configure una cuenta de margen de gastos en parametros de sistema en contaweb");
				}

				detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(codigo_comprobante);
				detalle_nota.setCodigo_documento(facturacion
						.getCodigo_documento_res());
				detalle_nota.setConsecutivo(consecutivo + "");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota.setCodigo_cuenta(cuenta_margen_gasto_capita);
				detalle_nota.setConcepto("MARGEN DE CONTRATACIÓN");
				detalle_nota.setTercero(facturacion.getCodigo_administradora());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(saldo_ingresos - saldo);
				detalle_nota.setCredito(0);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("");
				detalle_nota.setVence(facturacion.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				lista_detalle_nota.add(detalle_nota);
				if (sw) {
					detalle_notaDao.crear(detalle_nota);
				}
				consecutivo++;
			} else if (saldo_ingresos < saldo) {
				if (cuenta_margen_gasto_capita.equals("") && sw) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar configure una cuenta de margen de gastos en parametros de sistema en contaweb");
				}

				detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(codigo_comprobante);
				detalle_nota.setCodigo_documento(facturacion
						.getCodigo_documento_res());
				detalle_nota.setConsecutivo(consecutivo + "");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota.setCodigo_cuenta(cuenta_margen_ingreso_capita);
				detalle_nota.setConcepto("MARGEN DE CONTRATACIÓN");
				detalle_nota.setTercero(facturacion.getCodigo_administradora());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(0.0);
				detalle_nota.setCredito(saldo - saldo_ingresos);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("");
				detalle_nota.setVence(facturacion.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				lista_detalle_nota.add(detalle_nota);
				if (sw) {
					detalle_notaDao.crear(detalle_nota);
				}
				consecutivo++;
			}

			if (facturacion.getRadicado().equals("S") && descuento_glosa > 0) {
				if (cuenta_glosa.equals("")) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar configure una cuenta de glosa en parametros de sistema en contaweb");
				}
				if (facturacion.getValor_pagar_factura() <= 0) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar el valor a pagar de es menor o igual a cero");
				}
				detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(codigo_comprobante);
				detalle_nota.setCodigo_documento(facturacion
						.getCodigo_documento_res());
				detalle_nota.setConsecutivo(consecutivo + "");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota.setCodigo_cuenta(cuenta_glosa);
				detalle_nota.setConcepto("DESCUENTO POR GLOSA");
				detalle_nota.setTercero(facturacion.getCodigo_administradora());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(descuento_glosa);
				detalle_nota.setCredito(0);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("");
				detalle_nota.setVence(facturacion.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				lista_detalle_nota.add(detalle_nota);
				if (sw) {
					detalle_notaDao.crear(detalle_nota);
				}
				consecutivo++;
			}

			double valor_autoretencion = 0;
			String cuenta_autorete1 = (tercero != null ? tercero
					.getCuenta_autorete1() : "");
			String cuenta_autorete2 = (tercero != null ? tercero
					.getCuenta_autorete2() : "");

			Impuesto impuesto = new Impuesto();
			impuesto.setCodigo_empresa(codigo_empresa);
			impuesto.setCodigo_sucursal(codigo_sucursal);
			impuesto.setCodigo_cuenta(cuenta_autorete1);
			impuesto.setAnio(facturacion.getAnio());
			impuesto = impuestoDao.consultar(impuesto);
			if (impuesto != null) {
				if (impuesto.getBase() <= facturacion.getValor_total()) {
					valor_autoretencion = facturacion.getValor_total()
							* impuesto.getPorcentaje();
				}
			}

			if (valor_autoretencion > 0) {
				if (!cuenta_autorete1.isEmpty()) {
					detalle_nota = new Detalle_nota();
					detalle_nota.setCodigo_empresa(codigo_empresa);
					detalle_nota.setCodigo_sucursal(codigo_sucursal);
					detalle_nota.setCodigo_comprobante(codigo_comprobante);
					detalle_nota.setCodigo_documento(facturacion
							.getCodigo_documento_res());
					detalle_nota.setConsecutivo(consecutivo + "");
					detalle_nota.setCreacion_date(nota_contable
							.getCreacion_date());
					detalle_nota.setUltimo_update(nota_contable
							.getUltimo_update());
					detalle_nota.setCreacion_user(nota_contable
							.getCreacion_user());
					detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
					detalle_nota.setCodigo_cuenta(cuenta_autorete1);
					detalle_nota.setConcepto("AUTORETENCION");
					detalle_nota.setTercero(facturacion
							.getCodigo_administradora());
					detalle_nota.setCheque("");
					detalle_nota.setDebito(valor_autoretencion);
					detalle_nota.setCredito(0);
					detalle_nota.setC_costo("");
					detalle_nota.setAbona("");
					detalle_nota.setVence(facturacion.getFecha());
					detalle_nota.setBanco("");
					detalle_nota.setIva(0);
					detalle_nota.setCheque_consignado("N");
					detalle_nota.setTipo("");
					detalle_nota.setConcepto_abona("");
					lista_detalle_nota.add(detalle_nota);
					if (sw) {
						detalle_notaDao.crear(detalle_nota);
					}
					consecutivo++;
				}

				if (!cuenta_autorete2.isEmpty()) {
					detalle_nota = new Detalle_nota();
					detalle_nota.setCodigo_empresa(codigo_empresa);
					detalle_nota.setCodigo_sucursal(codigo_sucursal);
					detalle_nota.setCodigo_comprobante(codigo_comprobante);
					detalle_nota.setCodigo_documento(facturacion
							.getCodigo_documento_res());
					detalle_nota.setConsecutivo(consecutivo + "");
					detalle_nota.setCreacion_date(nota_contable
							.getCreacion_date());
					detalle_nota.setUltimo_update(nota_contable
							.getUltimo_update());
					detalle_nota.setCreacion_user(nota_contable
							.getCreacion_user());
					detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
					detalle_nota.setCodigo_cuenta(cuenta_autorete2);
					detalle_nota.setConcepto("AUTORETENCION");
					detalle_nota.setTercero(facturacion
							.getCodigo_administradora());
					detalle_nota.setCheque("");
					detalle_nota.setDebito(0);
					detalle_nota.setCredito(valor_autoretencion);
					detalle_nota.setC_costo("");
					detalle_nota.setAbona("");
					detalle_nota.setVence(facturacion.getFecha());
					detalle_nota.setBanco("");
					detalle_nota.setIva(0);
					detalle_nota.setCheque_consignado("N");
					detalle_nota.setTipo("");
					detalle_nota.setConcepto_abona("");
					lista_detalle_nota.add(detalle_nota);
					if (sw) {
						detalle_notaDao.crear(detalle_nota);
					}
					consecutivo++;
				}

				// consecutivo++;
			}

			if (facturacion.getRetencion() > 0) {
				if (facturacion.getAnio_retencion().equals("")) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar no hay año de impuesto seleccionado");
				}
				if (facturacion.getCuenta_retencion().equals("")) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar no hay cuenta de impuesto seleccionada");
				}
				detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(codigo_comprobante);
				detalle_nota.setCodigo_documento(facturacion
						.getCodigo_documento_res());
				detalle_nota.setConsecutivo(consecutivo + "");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota
						.setCodigo_cuenta(facturacion.getCuenta_retencion());
				detalle_nota.setConcepto("RETENCION EN LA FUENTE");
				detalle_nota.setTercero(facturacion.getCodigo_administradora());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(facturacion.getRetencion());
				detalle_nota.setCredito(0);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("");
				detalle_nota.setVence(facturacion.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				lista_detalle_nota.add(detalle_nota);
				if (sw) {
					detalle_notaDao.crear(detalle_nota);
				}
				consecutivo++;
			}

			facturacion.setEstado("CONT");
			if (sw) {
				facturacionDao.actualizar(facturacion);
			}

			/*
			 * double sum_debito=0,sum_credito=0; for (Detalle_nota
			 * detalle_nota2 : lista_detalle_nota) {
			 * sum_debito+=detalle_nota2.getDebito();
			 * sum_credito+=detalle_nota2.getCredito(); }
			 * 
			 * //log.info("debito:"+sum_debito);
			 * //log.info("credito:"+sum_credito);
			 */
			nota_contable.setLista_detalle(lista_detalle_nota);

			return nota_contable;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	// Este metodo es para actualizar la factura cuando se cargue o se elimine
	// un servicio al borrar la factura//
	@SuppressWarnings("unchecked")
	public void actualizarFacturaAutomatico(Facturacion facturacion) {
		try {
			if (facturacion != null) {
				Admision admision = admisionDao.consultar(facturacion
						.getAdmision());

				if (admision == null) {
					throw new Exception(
							"Lo sentimos el dato no se puede guardar, no se encuentra registro de admision");
				}

				Map<String, Object> paramDetalle_factura = new HashMap<String, Object>();
				paramDetalle_factura.put("id_factura",
						facturacion.getId_factura());
				List<Detalle_factura> lista_datos = detalleFacturaDao
						.listar(paramDetalle_factura);
				// log.info("Antes de consultar lista");
				Via_ingreso_contratadas via_ingreso_contratadas = ServiciosDisponiblesUtils
						.getVia_ingreso_contratadas(admision);
				Map<String, Object> serviciosCargados = this
						.consultarAdmisionFactura(admision,
								facturacion.getFecha_final(), lista_datos,
								via_ingreso_contratadas, "modificar");
				// log.info("Despues de consultar lista");
				List<Detalle_factura> lista_detalle = (List<Detalle_factura>) serviciosCargados
						.get("lista_detalle");

				double subtotal = 0;
				for (int i = 0; i < lista_detalle.size(); i++) {
					Detalle_factura detalle_factura = lista_detalle.get(i);
					if (detalle_factura.getFacturable()) {
						subtotal += detalle_factura.getValor_total();
					}
					detalleFacturaDao.actualizar(detalle_factura);
				}
				facturacion.setValor_total(subtotal);
				facturacionDao.actualizar(facturacion);

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("facturacion", facturacion);
				datos.put("lista_detalle", lista_detalle);
				datos.put("accion", "modificar");
				datos.put("contratos", ServiciosDisponiblesUtils
						.getContratosAdmision(admision));
				// log.info("Antes de guardar: " +
				// facturacion.getValor_total());
				this.guardarDatos(datos);
				// log.info("Despues de guardar: " +
				// facturacion.getValor_total());
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	// Este metodo es para actualizar la factura cuando se cargue o se elimine
	// un servicio al borrar la factura//
	@SuppressWarnings("unchecked")
	public void actualizarFacturaAutomatico(String cod_emp, String cod_suc,
			String nro_ing, String nro_id) {
		try {
			Facturacion facturacion = new Facturacion();
			facturacion.setCodigo_empresa(cod_emp);
			facturacion.setCodigo_sucursal(cod_suc);
			facturacion.setCodigo_tercero(nro_id);
			facturacion.setNro_ingreso(nro_ing);
			// //log.info("facturacion antes: "+facturacion);
			facturacion = facturacionDao.consultar_informacion(facturacion);
			// //log.info("facturacion actualizar: "+facturacion);
			if (facturacion != null) {
				Admision admision = new Admision();
				admision.setCodigo_empresa(cod_emp);
				admision.setCodigo_sucursal(cod_suc);
				admision.setNro_identificacion(nro_id);
				admision.setNro_ingreso(nro_ing);
				admision = admisionDao.consultar(admision);
				if (admision == null) {
					throw new Exception(
							"Lo sentimos el dato no se puede guardar, no se encuentra registro de admision");
				}

				Via_ingreso_contratadas via_ingreso_contratadas = ServiciosDisponiblesUtils
						.getVia_ingreso_contratadas(admision);
				Map<String, Object> paramDetalle_factura = new HashMap<String, Object>();
				paramDetalle_factura.put("id_factura",
						facturacion.getId_factura());
				List<Detalle_factura> lista_datos = detalleFacturaDao
						.listar(paramDetalle_factura);
				// log.info("Antes de consultar lista");
				Map<String, Object> serviciosCargados = this
						.consultarAdmisionFactura(admision,
								facturacion.getFecha_final(), lista_datos,
								via_ingreso_contratadas, "modificar");
				// log.info("Despues de consultar lista");
				List<Detalle_factura> lista_detalle = (List<Detalle_factura>) serviciosCargados
						.get("lista_detalle");

				double subtotal = 0;
				for (int i = 0; i < lista_detalle.size(); i++) {
					Detalle_factura detalle_factura = lista_detalle.get(i);
					if (detalle_factura.getFacturable()) {
						subtotal += detalle_factura.getValor_total();
					}
					detalleFacturaDao.actualizar(detalle_factura);
				}
				facturacion.setValor_total(subtotal);
				facturacionDao.actualizar(facturacion);

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("facturacion", facturacion);
				datos.put("lista_detalle", lista_detalle);
				datos.put("accion", "modificar");
				datos.put("contratos", ServiciosDisponiblesUtils
						.getContratosAdmision(admision));
				// log.info("Antes de guardar: " +
				// facturacion.getValor_total());
				this.guardarDatos(datos);
				// log.info("Despues de guardar: " +
				// facturacion.getValor_total());
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Nota_contable guardarContabilizacionCapitadaSinServicios(
			Map<String, Object> datos, boolean sw) {
		try {
			Long id_factura = (Long) datos.get("id_factura");
			String codigo_empresa = (String) datos.get("codigo_empresa");
			String codigo_sucursal = (String) datos.get("codigo_sucursal");
			String codigo_documento = (String) datos.get("codigo_documento");
			String codigo_comprobante = (String) datos
					.get("codigo_comprobante");
			// String tipo = "VENTA";
			String cuenta_cartera = "";

			Facturacion facturacion = new Facturacion();
			facturacion.setId_factura(id_factura);
			facturacion = facturacionDao.consultar(facturacion);

			Resolucion res = new Resolucion();
			res.setCodigo_empresa(codigo_empresa);
			res = resolucionContDao.consultar(res);

			String cuenta_ingreso = (res != null ? res.getCuenta_ingreso() : "");

			String cuenta_glosa = (res != null ? res.getCuenta_glosa() : "");

			Tercero tercero = new Tercero();
			tercero.setCodigo_empresa(codigo_empresa);
			tercero.setCodigo_sucursal(codigo_sucursal);
			tercero.setNro_identificacion(facturacion
					.getCodigo_administradora());
			tercero = terceroDao.consultar(tercero);

			Contratos plan = new Contratos();
			plan.setCodigo_empresa(codigo_empresa);
			plan.setCodigo_sucursal(codigo_sucursal);
			plan.setCodigo_administradora(facturacion
					.getCodigo_administradora());
			plan.setId_plan(facturacion.getId_plan());
			plan = contratosDao.consultar(plan);
			String tipo_regimen = (plan != null ? plan.getTipo_usuario() : "2");
			if (plan != null) {
				if (!plan.getCuenta_ingreso().isEmpty()) {
					cuenta_ingreso = plan.getCuenta_ingreso();
				}
			}

			if (tipo_regimen.equals("1")) {
				cuenta_cartera = tercero.getCuenta_cobrar2();
			} else if (tipo_regimen.equals("2")) {
				cuenta_cartera = tercero.getCuenta_cobrar();
			}

			Map<String, Object> detalleFactura = new HashMap<String, Object>();
			detalleFactura.put("id_factura", id_factura);
			List<Detalle_factura> lista_detalle = detalleFacturaDao
					.listar(detalleFactura);
			double saldo = 0;
			if (lista_detalle.size() > 0
					&& facturacion.getTipo().equals("GEN_AGR")) {
				Detalle_factura aux = lista_detalle.get(0);
				saldo = aux.getValor_total() - facturacion.getRetencion();
			} else if (facturacion.getTipo().equals("GEN_CAP")) {
				saldo = (facturacion.getValor_total() - facturacion
						.getRetencion());
			}

			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(facturacion.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(facturacion
					.getCodigo_comprobante());
			nota_contable.setCodigo_documento(facturacion
					.getCodigo_documento_res());
			nota_contable.setPrefijo(facturacion.getPrefijo());
			nota_contable.setAnio(facturacion.getAnio());
			nota_contable.setMes(facturacion.getMes());
			nota_contable.setFecha(facturacion.getFecha());
			nota_contable
					.setElaboro(facturacion.getCodigo_empleado() != null ? facturacion
							.getCodigo_empleado() : "");
			nota_contable.setCodigo_tercero(facturacion
					.getCodigo_administradora());
			nota_contable.setClasificacion(facturacion.getClasificacion());
			nota_contable.setConcepto(facturacion.getDescripcion());
			nota_contable.setVerificado(facturacion.getVerificado());
			nota_contable.setCreacion_date(facturacion.getCreacion_date());
			nota_contable.setUltimo_update(facturacion.getUltimo_update());
			nota_contable.setCreacion_user(facturacion.getCreacion_user());
			nota_contable.setUltimo_user(facturacion.getUltimo_user());
			nota_contable.setEstado("CANC");
			nota_contable.setForma_pago("");
			nota_contable.setDocumento_externo("");
			nota_contable.setBanco("");
			nota_contable.setMedio_pago("");
			nota_contable.setDocumento_externo(facturacion
					.getDocumento_externo());
			if (sw) {
				if (nota_contableDao.consultar(nota_contable) != null) {
					nota_contableDao.eliminar(nota_contable);
				}
				nota_contableDao.crear(nota_contable);
			}

			if (saldo == 0 && sw) {
				throw new Exception("El saldo del asiento de la factura "
						+ facturacion.getCodigo_comprobante() + "-"
						+ facturacion.getCodigo_documento_res() + " es cero");
			}

			if (cuenta_cartera.equals("") && sw) {
				throw new Exception(
						"La cuenta por cobrar esta en blanco configurela en el tercero y/o parametros de la empresa de la factura "
								+ facturacion.getCodigo_comprobante()
								+ "-"
								+ facturacion.getCodigo_documento_res() + "");
			}

			double descuento_glosa = (facturacion.getRadicado().equals("S") ? saldo
					- facturacion.getValor_pagar_factura()
					: 0);

			if (descuento_glosa < 0 && sw) {
				throw new Exception(
						"El documento "
								+ facturacion.getCodigo_documento_res()
								+ " no se pudo contabilizar el valor a pagar de es mayor que el valor neto de la factura");
			}

			int consecutivo = 0;

			List<Detalle_nota> lista_detalle_nota = new LinkedList<Detalle_nota>();
			Detalle_nota detalle_nota = new Detalle_nota();
			detalle_nota.setCodigo_empresa(codigo_empresa);
			detalle_nota.setCodigo_sucursal(codigo_sucursal);
			detalle_nota.setCodigo_comprobante(codigo_comprobante);
			detalle_nota.setCodigo_documento(codigo_documento);
			detalle_nota.setConsecutivo(consecutivo + "");
			detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
			detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
			detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
			detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
			detalle_nota.setCodigo_cuenta(cuenta_cartera);
			detalle_nota.setConcepto(facturacion.getDescripcion());
			detalle_nota.setTercero(facturacion.getCodigo_administradora());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(saldo - descuento_glosa);
			detalle_nota.setCredito(0.0);
			detalle_nota.setC_costo("");
			detalle_nota.setAbona("FV " + codigo_comprobante + "-"
					+ Integer.parseInt(codigo_documento));
			detalle_nota.setVence(facturacion.getFecha_vencimiento());
			detalle_nota.setBanco("");
			detalle_nota.setIva(0);
			detalle_nota.setCheque_consignado("N");
			detalle_nota.setTipo("");
			detalle_nota.setConcepto_abona("");
			lista_detalle_nota.add(detalle_nota);
			if (sw) {
				detalle_notaDao.crear(detalle_nota);
			}

			consecutivo++;

			if (facturacion.getRadicado().equals("S") && descuento_glosa > 0) {
				if (cuenta_glosa.equals("")) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar configure una cuenta de glosa en parametros de sistema en contaweb");
				}
				if (facturacion.getValor_pagar_factura() <= 0) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar el valor a pagar de es menor o igual a cero");
				}
				detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(codigo_comprobante);
				detalle_nota.setCodigo_documento(codigo_documento);
				detalle_nota.setConsecutivo(consecutivo + "");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota.setCodigo_cuenta(cuenta_glosa);
				detalle_nota.setConcepto("DESCUENTO POR GLOSA");
				detalle_nota.setTercero(facturacion.getCodigo_administradora());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(descuento_glosa);
				detalle_nota.setCredito(0);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("");
				detalle_nota.setVence(facturacion.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				lista_detalle_nota.add(detalle_nota);
				if (sw) {
					detalle_notaDao.crear(detalle_nota);
				}
				consecutivo++;
			}

			double valor_autoretencion = 0;
			String cuenta_autorete1 = (tercero != null ? tercero
					.getCuenta_autorete1() : "");
			String cuenta_autorete2 = (tercero != null ? tercero
					.getCuenta_autorete2() : "");

			Impuesto impuesto = new Impuesto();
			impuesto.setCodigo_empresa(codigo_empresa);
			impuesto.setCodigo_sucursal(codigo_sucursal);
			impuesto.setCodigo_cuenta(cuenta_autorete1);
			impuesto.setAnio(facturacion.getAnio());
			impuesto = impuestoDao.consultar(impuesto);
			if (impuesto != null) {
				if (impuesto.getBase() <= facturacion.getValor_total()) {
					valor_autoretencion = facturacion.getValor_total()
							* impuesto.getPorcentaje();
				}
			}

			if (valor_autoretencion > 0) {
				if (!cuenta_autorete1.isEmpty()) {
					detalle_nota = new Detalle_nota();
					detalle_nota.setCodigo_empresa(codigo_empresa);
					detalle_nota.setCodigo_sucursal(codigo_sucursal);
					detalle_nota.setCodigo_comprobante(codigo_comprobante);
					detalle_nota.setCodigo_documento(codigo_documento);
					detalle_nota.setConsecutivo(consecutivo + "");
					detalle_nota.setCreacion_date(nota_contable
							.getCreacion_date());
					detalle_nota.setUltimo_update(nota_contable
							.getUltimo_update());
					detalle_nota.setCreacion_user(nota_contable
							.getCreacion_user());
					detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
					detalle_nota.setCodigo_cuenta(cuenta_autorete1);
					detalle_nota.setConcepto("AUTORETENCION");
					detalle_nota.setTercero(facturacion
							.getCodigo_administradora());
					detalle_nota.setCheque("");
					detalle_nota.setDebito(valor_autoretencion);
					detalle_nota.setCredito(0);
					detalle_nota.setC_costo("");
					detalle_nota.setAbona("");
					detalle_nota.setVence(facturacion.getFecha());
					detalle_nota.setBanco("");
					detalle_nota.setIva(0);
					detalle_nota.setCheque_consignado("N");
					detalle_nota.setTipo("");
					detalle_nota.setConcepto_abona("");
					lista_detalle_nota.add(detalle_nota);
					if (sw) {
						detalle_notaDao.crear(detalle_nota);
					}
					consecutivo++;
				}

				if (!cuenta_autorete2.isEmpty()) {
					detalle_nota = new Detalle_nota();
					detalle_nota.setCodigo_empresa(codigo_empresa);
					detalle_nota.setCodigo_sucursal(codigo_sucursal);
					detalle_nota.setCodigo_comprobante(codigo_comprobante);
					detalle_nota.setCodigo_documento(codigo_documento);
					detalle_nota.setConsecutivo(consecutivo + "");
					detalle_nota.setCreacion_date(nota_contable
							.getCreacion_date());
					detalle_nota.setUltimo_update(nota_contable
							.getUltimo_update());
					detalle_nota.setCreacion_user(nota_contable
							.getCreacion_user());
					detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
					detalle_nota.setCodigo_cuenta(cuenta_autorete2);
					detalle_nota.setConcepto("AUTORETENCION");
					detalle_nota.setTercero(facturacion
							.getCodigo_administradora());
					detalle_nota.setCheque("");
					detalle_nota.setDebito(0);
					detalle_nota.setCredito(valor_autoretencion);
					detalle_nota.setC_costo("");
					detalle_nota.setAbona("");
					detalle_nota.setVence(facturacion.getFecha());
					detalle_nota.setBanco("");
					detalle_nota.setIva(0);
					detalle_nota.setCheque_consignado("N");
					detalle_nota.setTipo("");
					detalle_nota.setConcepto_abona("");
					lista_detalle_nota.add(detalle_nota);
					if (sw) {
						detalle_notaDao.crear(detalle_nota);
					}
					consecutivo++;
				}
			}

			if (facturacion.getRetencion() > 0) {
				if (facturacion.getAnio_retencion().equals("")) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar no hay año de impuesto seleccionado");
				}
				if (facturacion.getCuenta_retencion().equals("")) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar no hay cuenta de impuesto seleccionada");
				}
				detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(codigo_comprobante);
				detalle_nota.setCodigo_documento(codigo_documento);
				detalle_nota.setConsecutivo(consecutivo + "");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota
						.setCodigo_cuenta(facturacion.getCuenta_retencion());
				detalle_nota.setConcepto("RETENCION EN LA FUENTE");
				detalle_nota.setTercero(facturacion.getCodigo_administradora());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(facturacion.getRetencion());
				detalle_nota.setCredito(0);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("");
				detalle_nota.setVence(facturacion.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				lista_detalle_nota.add(detalle_nota);
				if (sw) {
					detalle_notaDao.crear(detalle_nota);
				}
				consecutivo++;
			}

			detalle_nota = new Detalle_nota();
			detalle_nota.setCodigo_empresa(codigo_empresa);
			detalle_nota.setCodigo_sucursal(codigo_sucursal);
			detalle_nota.setCodigo_comprobante(codigo_comprobante);
			detalle_nota.setCodigo_documento(codigo_documento);
			detalle_nota.setConsecutivo(consecutivo + "");
			detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
			detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
			detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
			detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
			detalle_nota.setCodigo_cuenta(cuenta_ingreso);
			detalle_nota.setConcepto(facturacion.getDescripcion());
			detalle_nota.setTercero(facturacion.getCodigo_administradora());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(0.0);
			detalle_nota.setCredito(saldo + facturacion.getRetencion());
			detalle_nota.setC_costo("");
			detalle_nota.setAbona("");
			detalle_nota.setVence(facturacion.getFecha_vencimiento());
			detalle_nota.setBanco("");
			detalle_nota.setIva(0);
			detalle_nota.setCheque_consignado("N");
			detalle_nota.setTipo("");
			detalle_nota.setConcepto_abona("");
			lista_detalle_nota.add(detalle_nota);
			if (sw) {
				detalle_notaDao.crear(detalle_nota);
			}

			/*
			 * Cartera cartera = new Cartera();
			 * cartera.setCodigo_empresa(codigo_empresa);
			 * cartera.setCodigo_sucursal(codigo_sucursal);
			 * cartera.setCodigo_comprobante(codigo_comprobante);
			 * cartera.setCodigo_documento(codigo_documento);
			 * cartera.setNro_cuota("1");
			 * cartera.setVencimiento(facturacion.getFecha_vencimiento());
			 * cartera.setAbono(0); cartera.setSaldos(saldo-descuento_glosa);
			 * cartera.setTotal(saldo-descuento_glosa); cartera.setTipo("FH");
			 * cartera.setCreacion_date(new Timestamp(new
			 * GregorianCalendar().getTimeInMillis()));
			 * cartera.setUltimo_update(new Timestamp(new
			 * GregorianCalendar().getTimeInMillis()));
			 * cartera.setCreacion_user(nota_contable.getCreacion_user());
			 * cartera.setUltimo_user(nota_contable.getUltimo_user());
			 * cartera.setManejo(tipo.equals("COMPRA") ? "CP" : "CC");
			 * cartera.setCodigo_tercero
			 * (facturacion.getCodigo_administradora());
			 * cartera.setCuenta(cuenta_cartera); cartera.setConcepto(""); if
			 * (carteraDao.consultar(cartera)!=null) { cartera =
			 * carteraDao.consultar(cartera);
			 * cartera.setSaldos(saldo-descuento_glosa);
			 * cartera.setTotal(saldo-descuento_glosa);
			 * if(cartera.getSaldos()-cartera.getAbono()<0){ throw new
			 * Exception("La factura "+facturacion.getCodigo_documento()+
			 * " ya se le ha hecho abonos que superan el nuevo saldo"); }
			 * cartera.setSaldos(cartera.getSaldos()-cartera.getAbono());
			 * if(sw){ carteraDao.actualizar(cartera); }
			 * 
			 * } else { if(sw){ carteraDao.crear(cartera); } }
			 */
			facturacion.setEstado("CONT");
			if (sw) {
				facturacionDao.actualizar(facturacion);
			}

			nota_contable.setLista_detalle(lista_detalle_nota);

			return nota_contable;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void actualizarFactura(Map<String, Object> map) {
		try {
			Usuarios usuarios = (Usuarios) map.get("usuario");
			Date fecha_factura = (Date) map.get("fecha_fact");
			Date fecha_inicio = (Date) map.get("fecha_inicio");
			Date fecha_final = (Date) map.get("fecha_final");
			Facturacion facturacion = (Facturacion) map.get("factura");

			int plazo = 30;

			/* factura agrupada o capitada */
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha_factura);
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) + plazo);
			Date fecha_vencimiento = calendar.getTime();

			boolean cont = false;
			contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
			res.setCodigo_empresa(usuarios.getCodigo_empresa());
			res = (contaweb.modelo.bean.Resolucion) resolucionContDao
					.consultar(res);
			if (res != null) {
				if (res.getContabiliza_aut()) {
					cont = true;
				}
			}

			Facturacion facturacionTemp = new Facturacion();
			facturacionTemp.setId_factura(facturacion.getId_factura());
			facturacionTemp = facturacionDao.consultar(facturacion);
			if (facturacionTemp == null) {
				throw new ValidacionRunTimeException("Factura "
						+ facturacion.getCodigo_documento_res()
						+ " no se encuentra registrada");
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd/MM/yyyy");
			facturacion.setFecha(new Timestamp(fecha_factura.getTime()));
			facturacion.setFecha_vencimiento(new Timestamp(fecha_vencimiento
					.getTime()));
			facturacion.setAnio(new java.text.SimpleDateFormat("yyyy")
					.format(fecha_factura));
			facturacion.setMes(new java.text.SimpleDateFormat("MM")
					.format(fecha_factura));
			facturacion.setUltimo_update(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			facturacion.setUltimo_user(usuarios.getCodigo());
			facturacion.setDescripcion("Periodo: "
					+ simpleDateFormat.format(fecha_inicio) + " a "
					+ simpleDateFormat.format(fecha_final));
			facturacionDao.actualizar(facturacion);

			if (cont) {
				// Map param_cont = new HashMap();
				// param_cont.put("codigo_empresa",
				// usuarios.getCodigo_empresa());
				// param_cont.put("codigo_sucursal",
				// usuarios.getCodigo_sucursal());
				// param_cont.put("codigo_documento",
				// facturacion.getCodigo_documento());
				// contabilizar_evento(param_cont, true);
			}

		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarFacturasRadicadas(List<Facturacion> lista_detalle,
			String codigo_usuario) {
		try {
			for (Facturacion facturacion : lista_detalle) {
				facturacion.setUltimo_user(codigo_usuario);
				facturacion
						.setUltimo_update(new Timestamp(new Date().getTime()));
				facturacionDao.actualizar(facturacion);

				if (facturacion.getRadicado().equals("S")) {
					guardarContabilizacionRadicados(facturacion, codigo_usuario);
				} else {
					eliminarRadicados(facturacion);
				}
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void eliminarRadicados(Facturacion facturacion) {
		try {
			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(facturacion.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(facturacion
					.getFuenta_radicado());
			nota_contable.setCodigo_documento(facturacion
					.getDocumento_radicado());
			nota_contable = nota_contableDao.consultar(nota_contable);
			if (nota_contable != null) {

				Caja caja = new Caja(false);
				caja.setCodigo_empresa(nota_contable.getCodigo_empresa());
				caja.setCodigo_sucursal(nota_contable.getCodigo_sucursal());
				caja.setCodigo_comprobante(nota_contable
						.getCodigo_comprobante());
				caja.setCodigo_documento(nota_contable.getCodigo_documento());
				// log.info("caja antes: " + caja);
				caja = cajaDao.consultar(caja);
				// log.info("caja: " + caja);
				if (caja != null && caja.getDelete_date() == null) {
					throw new Exception(
							"El documento radicado generado de la factura "
									+ facturacion.getCodigo_comprobante() + "-"
									+ facturacion.getCodigo_documento_res()
									+ " ya se le han hecho abonos ");
				}

				nota_contableDao.eliminar(nota_contable);

				Cartera cartera = new Cartera();
				cartera.setCodigo_empresa(nota_contable.getCodigo_empresa());
				cartera.setCodigo_sucursal(nota_contable.getCodigo_sucursal());
				cartera.setCodigo_comprobante(nota_contable
						.getCodigo_comprobante());
				cartera.setCodigo_documento(nota_contable.getCodigo_documento());
				cartera.setCodigo_tercero(nota_contable.getCodigo_tercero());
				carteraDao.eliminar(cartera);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarContabilizacionRadicados(Facturacion facturacion,
			String codigo_usuario) {
		try {

			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(facturacion.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(facturacion
					.getCodigo_comprobante());
			nota_contable.setCodigo_documento(facturacion
					.getCodigo_documento_res());
			nota_contable = nota_contableDao.consultar(nota_contable);
			if (nota_contable == null) {
				throw new ValidacionRunTimeException(
						"No se ha generado asiento contable para la factura "
								+ facturacion.getCodigo_comprobante() + "-"
								+ facturacion.getCodigo_documento_res());
			}

			String cuenta_cobrar = "";
			String cuenta_cobrar_radicada = "";
			double saldo = 0.0;

			List<Detalle_nota> lista_Detalle = nota_contable.getLista_detalle();
			Puc puc = null;
			for (Detalle_nota detalle_nota : lista_Detalle) {
				puc = new Puc();
				puc.setCodigo_empresa(detalle_nota.getCodigo_empresa());
				puc.setCodigo_sucursal(detalle_nota.getCodigo_sucursal());
				puc.setCodigo_cuenta(detalle_nota.getCodigo_cuenta());
				puc = pucDao.consultar(puc);

				if (puc != null && puc.getTipo_cuenta().equals("02")) {
					cuenta_cobrar = puc.getCodigo_cuenta();
					saldo = detalle_nota.getDebito()
							+ detalle_nota.getCredito();
					break;
				}
			}

			Resolucion resolucion = new Resolucion();
			resolucion.setCodigo_empresa(facturacion.getCodigo_empresa());
			resolucion = resolucionContDao.consultar(resolucion);
			if (resolucion != null) {
				cuenta_cobrar_radicada = resolucion.getCuenta_cobrar_radicada();
			}

			if (puc == null) {
				throw new ValidacionRunTimeException(
						"No hay cuenta por cobrar disponible en documento "
								+ facturacion.getCodigo_comprobante() + "-"
								+ facturacion.getCodigo_documento_res());
			}

			if (cuenta_cobrar.equals("")) {
				throw new ValidacionRunTimeException(
						"No hay cuenta por cobrar disponible en documento "
								+ facturacion.getCodigo_comprobante() + "-"
								+ facturacion.getCodigo_documento_res());
			}

			if (saldo <= 0) {
				throw new ValidacionRunTimeException(
						"No hay valor de la cuenta por cobrar disponible en documento "
								+ facturacion.getCodigo_comprobante() + "-"
								+ facturacion.getCodigo_documento_res());
			}

			if (cuenta_cobrar_radicada.equals("")) {
				throw new ValidacionRunTimeException(
						"Debe configurar en el modulo contable la cuenta por cobrar radicada");
			}

			nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(facturacion.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(facturacion
					.getFuenta_radicado());
			nota_contable.setCodigo_documento(facturacion
					.getDocumento_radicado());
			nota_contable = nota_contableDao.consultar(nota_contable);

			boolean registrar = false;
			if (nota_contable == null) {
				registrar = true;
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("codigo_empresa",
						facturacion.getCodigo_empresa());
				parameters.put("codigo_sucursal",
						facturacion.getCodigo_sucursal());
				parameters.put("tipo_dct", "37");
				List<Comprobante> lista_comprobante = comprobanteDao
						.listar(parameters);
				if (lista_comprobante.isEmpty()) {
					throw new HealthmanagerException(
							"Debe crear un comprobante de tipo factura radicada");
				}
				String fuente = lista_comprobante.get(0).getCodigo();

				String cod_doc = consecutivoService.getZeroFill(
						consecutivoService.crearConsecutivoNota(
								facturacion.getCodigo_empresa(),
								facturacion.getCodigo_sucursal(), fuente), 10);

				nota_contable = new Nota_contable();
				nota_contable
						.setCodigo_empresa(facturacion.getCodigo_empresa());
				nota_contable.setCodigo_sucursal(facturacion
						.getCodigo_sucursal());
				nota_contable.setCodigo_comprobante(fuente);
				nota_contable.setCodigo_documento(cod_doc);
				nota_contable.setCreacion_date(new Timestamp(new Date()
						.getTime()));
				nota_contable.setCreacion_user(codigo_usuario);
			}
			nota_contable.setFecha(new Timestamp(facturacion
					.getFecha_erradicacion().getTime()));
			nota_contable.setPrefijo(facturacion.getPrefijo());
			nota_contable.setAnio(new SimpleDateFormat("yyyy")
					.format(nota_contable.getFecha()));
			nota_contable.setMes(new SimpleDateFormat("MM")
					.format(nota_contable.getFecha()));
			nota_contable
					.setElaboro(facturacion.getCodigo_empleado() != null ? facturacion
							.getCodigo_empleado() : "");
			nota_contable.setCodigo_tercero(facturacion
					.getCodigo_administradora());
			nota_contable.setClasificacion(facturacion.getClasificacion());
			nota_contable.setConcepto(facturacion.getDescripcion());
			nota_contable.setVerificado(facturacion.getVerificado());
			nota_contable.setUltimo_update(facturacion.getUltimo_update());
			nota_contable.setUltimo_user(facturacion.getUltimo_user());
			nota_contable.setEstado("CANC");
			nota_contable.setForma_pago("");
			nota_contable.setDocumento_externo("");
			nota_contable.setBanco("");
			nota_contable.setMedio_pago("");
			// nota_contable.setCodigo_rp(facturacion.getCodigo_rp());
			// nota_contable.setCodigo_obligacion(facturacion.getCodigo_obligacion());
			nota_contable.setDocumento_externo(facturacion
					.getDocumento_externo());

			if (nota_contableDao.consultar(nota_contable) != null) {
				nota_contableDao.eliminar(nota_contable);
			}
			nota_contableDao.crear(nota_contable);
			if (registrar) {
				consecutivoService.actualizarConsecutivoNota(
						nota_contable.getCodigo_empresa(),
						nota_contable.getCodigo_sucursal(),
						nota_contable.getCodigo_comprobante(),
						nota_contable.getCodigo_documento());
			}

			int consecutivo = 0;

			List<Detalle_nota> lista_detalle_nota = new LinkedList<Detalle_nota>();
			Detalle_nota detalle_nota = new Detalle_nota();
			detalle_nota.setCodigo_empresa(nota_contable.getCodigo_empresa());
			detalle_nota.setCodigo_sucursal(nota_contable.getCodigo_sucursal());
			detalle_nota.setCodigo_comprobante(nota_contable
					.getCodigo_comprobante());
			detalle_nota.setCodigo_documento(nota_contable
					.getCodigo_documento());
			detalle_nota.setConsecutivo(consecutivo + "");
			detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
			detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
			detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
			detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
			detalle_nota.setCodigo_cuenta(cuenta_cobrar_radicada);
			detalle_nota.setConcepto(facturacion.getDescripcion());
			detalle_nota.setTercero(facturacion.getCodigo_administradora());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(saldo);
			detalle_nota.setCredito(0.0);
			detalle_nota.setC_costo("");
			detalle_nota.setAbona("FV " + nota_contable.getCodigo_comprobante()
					+ "-"
					+ Integer.parseInt(nota_contable.getCodigo_documento()));
			detalle_nota.setVence(facturacion.getFecha_vencimiento());
			detalle_nota.setBanco("");
			detalle_nota.setIva(0);
			detalle_nota.setCheque_consignado("N");
			detalle_nota.setTipo("");
			detalle_nota.setConcepto_abona("");
			detalle_notaDao.crear(detalle_nota);
			lista_detalle_nota.add(detalle_nota);

			consecutivo++;

			detalle_nota = new Detalle_nota();
			detalle_nota.setCodigo_empresa(nota_contable.getCodigo_empresa());
			detalle_nota.setCodigo_sucursal(nota_contable.getCodigo_sucursal());
			detalle_nota.setCodigo_comprobante(nota_contable
					.getCodigo_comprobante());
			detalle_nota.setCodigo_documento(nota_contable
					.getCodigo_documento());
			detalle_nota.setConsecutivo(consecutivo + "");
			detalle_nota.setConsecutivo(consecutivo + "");
			detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
			detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
			detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
			detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
			detalle_nota.setCodigo_cuenta(cuenta_cobrar);
			detalle_nota.setConcepto(facturacion.getDescripcion());
			detalle_nota.setTercero(facturacion.getCodigo_administradora());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(0.0);
			detalle_nota.setCredito(saldo);
			detalle_nota.setC_costo("");
			detalle_nota.setAbona("");
			detalle_nota.setVence(facturacion.getFecha_vencimiento());
			detalle_nota.setBanco("");
			detalle_nota.setIva(0);
			detalle_nota.setCheque_consignado("N");
			detalle_nota.setTipo("");
			detalle_nota.setConcepto_abona("");
			detalle_notaDao.crear(detalle_nota);
			lista_detalle_nota.add(detalle_nota);

			Cartera cartera = new Cartera();
			cartera.setCodigo_empresa(nota_contable.getCodigo_empresa());
			cartera.setCodigo_sucursal(nota_contable.getCodigo_sucursal());
			cartera.setCodigo_comprobante(nota_contable.getCodigo_comprobante());
			cartera.setCodigo_documento(nota_contable.getCodigo_documento());
			cartera.setNro_cuota("1");
			cartera.setVencimiento(facturacion.getFecha_vencimiento());
			cartera.setAbono(0);
			cartera.setSaldos(saldo);
			cartera.setTotal(saldo);
			cartera.setTipo("FH");
			cartera.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cartera.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cartera.setCreacion_user(nota_contable.getCreacion_user());
			cartera.setUltimo_user(nota_contable.getUltimo_user());
			cartera.setManejo("CC");
			cartera.setCodigo_tercero(facturacion.getCodigo_administradora());
			cartera.setCuenta(cuenta_cobrar_radicada);
			cartera.setConcepto(facturacion.getCodigo_documento_res());
			cartera.setDocumento_soporte(facturacion.getCodigo_documento_res());
			if (carteraDao.consultar(cartera) != null) {
				cartera = carteraDao.consultar(cartera);
				cartera.setSaldos(saldo);
				cartera.setTotal(saldo);
				if (cartera.getSaldos() - cartera.getAbono() < 0) {
					throw new ValidacionRunTimeException(
							"La factura "
									+ facturacion.getCodigo_documento_res()
									+ " ya se le ha hecho abonos que superan el nuevo saldo");
				}
				cartera.setSaldos(cartera.getSaldos() - cartera.getAbono());
				carteraDao.actualizar(cartera);

			} else {
				carteraDao.crear(cartera);
			}

			facturacion.setFuenta_radicado(nota_contable
					.getCodigo_comprobante());
			facturacion.setDocumento_radicado(nota_contable
					.getCodigo_documento());
			facturacionDao.actualizar(facturacion);

		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Facturacion guardarGlosasFact(Facturacion facturacion,
			List<Detalle_factura> lista_datos, String tipo,
			EModeloGlosa modeloGlosa) {
		// boolean validate = false;
		double valorGlosa = 0;
		if (modeloGlosa == EModeloGlosa.INDIVIDUAL) {
			for (Detalle_factura detalleFactura : lista_datos) {
				boolean item_glosado = false;
				if (tipo.equals("3")) {
					item_glosado = !(detalleFactura.getRespuesta_definitiva() + "")
							.trim().isEmpty();
				} else {
					item_glosado = !(detalleFactura.getEstado_glosa() + "")
							.trim().isEmpty();
				}

				if (item_glosado
						&& !detalleFactura.getEstado_final_glosa().equals(
								GlosasDevolucionesAction.GLOSA_LEVANTADA)) {
					// validate = true;
					valorGlosa += detalleFactura.getValor_total();
				}
				detalleFactura.setGlosado(item_glosado ? "S" : "N");
				detalleFacturaDao.actualizar(detalleFactura);
			}
			// if(!validate && !tipo.equals("3"))throw new
			// ValidacionRunTimeException("Debe agregar un motivo un detalle de la factura");
		}
		/* actualizamos la factura cuando detalles para glosar */
		if (!tipo.equals("3")) {
			facturacion.setTipo_glosa(tipo);
			// facturacion.setValor_glosa_inicial(valorGlosa);
			facturacion.setFact_glosada("S");

			// Verfiicamos si no tiene estado
			boolean realizar_accion_cambio_estado = true;
			if (tipo.equals("1")
					&& (facturacion.getEstado_respuesta_glosa() + "").trim()
							.equals("")) {
				facturacion.setEstado_respuesta_glosa("01");
				realizar_accion_cambio_estado = false;
			}

			double valor_total = 0;
			if (modeloGlosa == EModeloGlosa.GRUPAL) {
				if ((facturacion.getEstado_respuesta_glosa() + "").trim()
						.equals("02")) {
					valorGlosa = facturacion.getValor_glosa_aceptada();
				} else if ((facturacion.getEstado_respuesta_glosa() + "")
						.trim().equals("01")) {
					valorGlosa = facturacion.getValor_glosa_inicial();
				}
				valor_total = facturacion.getValor_pagar_factura() - valorGlosa;
			} else if ((facturacion.getEstado_respuesta_glosa() + "").trim()
					.equals("02")) {
				facturacion.setValor_glosa_aceptada(valorGlosa);

				if (facturacion.getValor_pagar_factura() >= facturacion
						.getValor_glosa_aceptada()) {
					valor_total = facturacion.getValor_pagar_factura()
							- facturacion.getValor_glosa_aceptada();
				}
			} else {
				facturacion.setValor_glosa_inicial(valorGlosa);
				if (facturacion.getValor_pagar_factura() >= facturacion
						.getValor_glosa_inicial()) {
					valor_total = facturacion.getValor_pagar_factura()
							- facturacion.getValor_glosa_inicial();
				}
			}

			if (valor_total < 0) {
				valor_total = 0;
			}

			facturacion.setValor_total(valor_total);
			facturacion.setEstado_glosa(tipo.equals("2") ? "03" : "04");
			if (tipo.equals("1")) {
				/* Agregamos la fecha del dia en que se realiza la glosa */
				// facturacion.setFact_glosada("S");
				// facturacion.setFecha_glosa(new
				// java.sql.Date(Calendar.getInstance().getTimeInMillis()));
				if (realizar_accion_cambio_estado) {
					if ((facturacion.getEstado_respuesta_glosa() + "").trim()
							.equals("01")) {
						facturacion.setEstado_respuesta_glosa("02");
					} else if ((facturacion.getEstado_respuesta_glosa() + "")
							.trim().equals("02")) {
						facturacion.setEstado_respuesta_glosa("00");
					}
				}
			} else {
				if (facturacion.getFecha_devolucion_factura() == null) {
					facturacion.setFecha_devolucion_factura(new Timestamp(
							Calendar.getInstance().getTimeInMillis()));
				}
			}
		} else {
			double valor_total = 0;
			if (facturacion.getValor_pagar_factura() >= facturacion
					.getValor_glosa_inicial()) {
				valor_total = facturacion.getValor_pagar_factura()
						- facturacion.getValor_glosa_inicial();
			}
			facturacion.setValor_glosa_aceptada(valorGlosa);
			facturacion.setValor_total(valor_total);
			facturacion.setEstado_glosa("02");
			facturacion.setEstado_respuesta_glosa("02");
		}
		facturacionDao.actualizar(facturacion);

		boolean cont = false;
		Resolucion res = new Resolucion();
		res = resolucionContDao.consultar(res);
		if (res != null) {
			if (res.getContabiliza_aut()) {
				cont = true;
			}
		}

		if (!cont) {
			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(facturacion.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(facturacion
					.getCodigo_comprobante());
			nota_contable.setCodigo_documento(facturacion
					.getCodigo_documento_res());
			if (nota_contableDao.consultar(nota_contable) != null) {
				cont = true;
			}
		}

		if (cont) {
			if (facturacion.getTipo().equals("FHC")) {
				Map<String, Object> datos_cont = new HashMap<String, Object>();
				datos_cont.put("id_factura", facturacion.getId_factura());
				datos_cont.put("tipo", facturacion.getTipo());
				facturacionClinicaCompraService.guardarContabilizacionGlosa(
						datos_cont, true);
			} else if (facturacion.getTipo().equals(
					IConstantes.TIPO_FACTURA_EVENTO_IND)
					|| facturacion.getTipo().equals(
							IConstantes.TIPO_FACTURA_PORTABILIDAD)) {
				Admision admision = new Admision();
				admision.setCodigo_empresa(facturacion.getCodigo_empresa());
				admision.setCodigo_sucursal(facturacion.getCodigo_sucursal());
				admision.setNro_identificacion(facturacion.getCodigo_tercero());
				admision.setNro_ingreso(facturacion.getNro_ingreso());
				admision = admisionDao.consultar(admision);

				Map<String, Object> param_cont = new HashMap<String, Object>();
				param_cont.put("id_factura", facturacion.getId_factura());
				param_cont.put("admision", admision);
				guardarContabilizacionIndividual(param_cont, true, true);
			} else {
				Map<String, Object> param_cont = new HashMap<String, Object>();
				param_cont.put("codigo_empresa",
						facturacion.getCodigo_empresa());
				param_cont.put("codigo_sucursal",
						facturacion.getCodigo_sucursal());
				param_cont.put("id_factura", facturacion.getId_factura());
				guardarContabilizacionCapitada(param_cont, true);
			}

		}

		return facturacion;
	}

	/**
	 * Este metodo me devuelve lo facturado y lo recuperado en una admision
	 *
	 */
	public Map<String, Object> getValorTotalYRecuperado(
			Map<String, Object> parametros) {
		return facturacionDao.getValorTotalYRecuperado(parametros);
	}

	// Metodo para hacer el recalculo de las facturas por objetos//
	public Map<String, Integer> guardarRecalculoFacturas(
			List<Facturacion> lista_facturas) {
		int facturas_recalculadas = 0;
		int facturas_radicada = 0;
		int facturas_facturada_capitada = 0;
		int admision_sin_fact = 0;
		int admision_sin_servicio = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			porcentaje_reclaculo = 0.0;
			for (int i = 0; i < lista_facturas.size(); i++) {
				Facturacion facturacion = lista_facturas.get(i);
				Map<String, Boolean> mapRespuesta = guardarRecalculoFacturas(
						facturacion, true);
				if (mapRespuesta.get("recalculada")) {
					facturas_recalculadas++;
				}
				if (mapRespuesta.get("radicadas")) {
					facturas_radicada++;
				}
				if (mapRespuesta.get("facturada")) {
					facturas_facturada_capitada++;
				}
				if (mapRespuesta.get("admision_sin_fact")) {
					admision_sin_fact++;
				}
				if (mapRespuesta.get("admision_sin_servicio")) {
					admision_sin_servicio++;
				}
				porcentaje_reclaculo = ((new Double(i + 1)) / new Double(
						lista_facturas.size())) * new Double(100);
				// comunicador.setValorDescripcionDefault(++i);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
		map.put("recalculada", facturas_recalculadas);
		map.put("radicadas", facturas_radicada);
		map.put("facturada", facturas_facturada_capitada);
		map.put("admision_sin_fact", admision_sin_fact);
		map.put("admision_sin_servicio", admision_sin_servicio);
		return map;
	}

	// Metodo para hacer el recalculo de las facturas por listas//
	public Map<String, Boolean> guardarRecalculoFacturas(
			Facturacion facturacion, boolean consultar_admision) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		boolean radicadas = false;
		boolean facturada = false;
		boolean recalculada = false;
		boolean admision_sin_fact = false;
		boolean admision_sin_servicio = false;
		try {
			String radicado = "N", facturado = "N";
			Admision admision = new Admision();
			if (consultar_admision) {
				admision.setCodigo_empresa(facturacion.getCodigo_empresa());
				admision.setCodigo_sucursal(facturacion.getCodigo_sucursal());
				admision.setNro_ingreso(facturacion.getNro_ingreso());
				admision.setNro_identificacion(facturacion.getCodigo_tercero());
				admision = admisionDao.consultar(admision);
			} else {
				admision = facturacion.getAdmision();
			}

			radicado = (facturacion != null ? facturacion.getRadicado() : "N");
			facturado = (facturacion != null ? (facturacion.getFactura()
					.equals("") ? "N" : "S") : "N");

			if (radicado.equals("S")) {
				radicadas = true;
			}

			if (facturado.equals("S")) {
				facturada = true;
			}

			if (!radicado.equals("S") && !facturado.equals("S")
					&& !admision_sin_fact) {
				Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
						.getManuales_tarifarios(admision);

				if (manuales_tarifarios != null) {

					// Consultamos el diagnostico para que nos actualice las
					// finalidades
					Impresion_diagnostica impresion_diagnostica = impresion_diagnosticaDao
							.getInformacionDiagnostica(admision);

					// Recalculamos los datos consultas //
					Map<String, Object> mapDatosCons = new HashMap<String, Object>();
					mapDatosCons.put("codigo_empresa",
							admision.getCodigo_empresa());
					mapDatosCons.put("codigo_sucursal",
							admision.getCodigo_sucursal());
					mapDatosCons.put("nro_ingreso", admision.getNro_ingreso());
					mapDatosCons.put("nro_identificacion",
							admision.getNro_identificacion());
					List<Datos_consulta> lista_datos_cons = datos_consultaService
							.listarTabla(mapDatosCons);
					for (Datos_consulta datos_consulta : lista_datos_cons) {
						if (datos_consulta.getCodigo_consulta() != null
								&& !datos_consulta.getCodigo_consulta()
										.isEmpty()) {
							Map<String, Object> pcd = obtenerValorPcd(
									manuales_tarifarios,
									datos_consulta.getCodigo_consulta());
							datos_consulta.setValor_consulta((Double) pcd
									.get("valor_pcd"));
							datos_consulta.setValor_neto((Double) pcd
									.get("valor_pcd"));
							datos_consulta.setValor_real((Double) pcd
									.get("valor_real"));
							datos_consulta.setDescuento((Double) pcd
									.get("descuento"));
							datos_consulta.setIncremento((Double) pcd
									.get("incremento"));

							// Actualizamos los valores de los rips
							if (impresion_diagnostica != null) {
								datos_consulta
										.setFinalidad_consulta(impresion_diagnostica
												.getFinalidad_consulta() != null ? impresion_diagnostica
												.getFinalidad_consulta() : "");
								datos_consulta
										.setCausa_externa(impresion_diagnostica
												.getCausas_externas() != null ? impresion_diagnostica
												.getCausas_externas() : "");
							}
							datos_consultaDao.actualizar(datos_consulta);
						}
					}

					// Recalculamos los datos procedimientos //
					Map<String, Object> mapDatosProc = new HashMap<String, Object>();
					mapDatosProc.put("codigo_empresa",
							admision.getCodigo_empresa());
					mapDatosProc.put("codigo_sucursal",
							admision.getCodigo_sucursal());
					mapDatosProc.put("nro_ingreso", admision.getNro_ingreso());
					mapDatosProc.put("nro_identificacion",
							admision.getNro_identificacion());
					List<Datos_procedimiento> lista_datos_pro = datos_procedimientoService
							.listarTabla(mapDatosProc);
					Map<String, Object> pacientes_para_cirugias = new TreeMap<String, Object>();
					for (Datos_procedimiento datos_procedimiento : lista_datos_pro) {
						if (!pacientes_para_cirugias
								.containsKey(datos_procedimiento
										.getCodigo_empresa()
										+ "_"
										+ datos_procedimiento
												.getCodigo_sucursal()
										+ "_"
										+ datos_procedimiento.getNro_ingreso()
										+ "_"
										+ datos_procedimiento
												.getNro_identificacion())) {
							pacientes_para_cirugias.put(
									datos_procedimiento.getCodigo_empresa()
											+ "_"
											+ datos_procedimiento
													.getCodigo_sucursal()
											+ "_"
											+ datos_procedimiento
													.getNro_ingreso()
											+ "_"
											+ datos_procedimiento
													.getNro_identificacion(),
									datos_procedimiento);
						}
						if (datos_procedimiento.getEs_quirurgico().equals("N")) {
							// verificamos si es un grupo
							Map<String, Object> param = new HashMap<String, Object>();
							param.put("codigo_empresa",
									datos_procedimiento.getCodigo_empresa());
							param.put("codigo_sucursal",
									datos_procedimiento.getCodigo_sucursal());
							param.put("codigo_cups_grupo", datos_procedimiento
									.getCodigo_procedimiento());
							if (grupos_procedimientosDao.existe(param)) {
								param.put("codigo_grupo", datos_procedimiento
										.getCodigo_procedimiento());
								List<Detalle_grupos_procedimientos> detalle_grupos_procedimientos = detalle_grupos_procedimientosDao
										.listar(param);
								for (Detalle_grupos_procedimientos dtt_grupo : detalle_grupos_procedimientos) {
									// realizamos un copia del objeto
									Datos_procedimiento datos_procedimientoTemp = Res
											.clonar(datos_procedimiento);

									// calculamos el valor del nuevo
									// procedimiento
									Map<String, Object> pcd = obtenerValorPcd(
											manuales_tarifarios,
											dtt_grupo.getId_procedimiento());
									datos_procedimientoTemp
											.setCodigo_procedimiento(dtt_grupo
													.getId_procedimiento());
									datos_procedimientoTemp
											.setValor_procedimiento((Double) pcd
													.get("valor_pcd"));
									datos_procedimientoTemp
											.setValor_neto(datos_procedimiento
													.getUnidades()
													* datos_procedimientoTemp
															.getValor_procedimiento());
									datos_procedimientoTemp
											.setValor_real((Double) pcd
													.get("valor_real"));
									datos_procedimientoTemp
											.setDescuento((Double) pcd
													.get("descuento"));
									datos_procedimientoTemp
											.setIncremento((Double) pcd
													.get("incremento"));
									datos_procedimientoService
											.crear(datos_procedimientoTemp);
								}
								// eliminamos el grupo encontrado
								if (!detalle_grupos_procedimientos.isEmpty()) {
									datos_procedimientoDao
											.eliminar(datos_procedimiento);
								}
							} else {
								Map<String, Object> pcd = obtenerValorPcd(
										manuales_tarifarios,
										datos_procedimiento
												.getCodigo_procedimiento());
								datos_procedimiento
										.setValor_procedimiento((Double) pcd
												.get("valor_pcd"));
								datos_procedimiento
										.setValor_neto(datos_procedimiento
												.getUnidades()
												* datos_procedimiento
														.getValor_procedimiento());
								datos_procedimiento.setValor_real((Double) pcd
										.get("valor_real"));
								datos_procedimiento.setDescuento((Double) pcd
										.get("descuento"));
								datos_procedimiento.setIncremento((Double) pcd
										.get("incremento"));
								datos_procedimientoDao
										.actualizar(datos_procedimiento);
							}
						}

					}

					for (String key_map : pacientes_para_cirugias.keySet()) {
						Datos_procedimiento dp = (Datos_procedimiento) pacientes_para_cirugias
								.get(key_map);
						datos_procedimientoService
								.actualizarCirugias(dp.getCodigo_empresa(),
										dp.getCodigo_sucursal(),
										dp.getNro_identificacion(),
										dp.getNro_ingreso());
					}

					// Recalculamos la facturacion de medicamentos y materiales
					// //
					for (int i = 1; i <= 2; i++) {
						Map<String, Object> mapDatos = new HashMap<String, Object>();
						mapDatos.put("codigo_empresa",
								admision.getCodigo_empresa());
						mapDatos.put("codigo_sucursal",
								admision.getCodigo_sucursal());
						mapDatos.put("nro_ingreso", admision.getNro_ingreso());
						mapDatos.put("nro_identificacion",
								admision.getNro_identificacion());
						mapDatos.put("tipo", "0" + i);
						List<Datos_medicamentos> lista_datos_med = datos_medicamentosDao
								.listar(mapDatos);
						for (Datos_medicamentos di : lista_datos_med) {
							Articulo articulo = new Articulo();
							articulo.setCodigo_empresa(admision
									.getCodigo_empresa());
							articulo.setCodigo_sucursal(admision
									.getCodigo_sucursal());
							articulo.setCodigo_articulo(di
									.getCodigo_medicamento());

							articulo = articuloDao.consultar(articulo);

							if (articulo != null) {
								double descuento = 0, incremento = 0;
								double valor = articulo.getPrecio1();
								double valor_real = valor;
								if (manuales_tarifarios != null) {
									if (manuales_tarifarios
											.getTarifa_especial().equals("S")) {
										Tarifa_medicamento tarifa = new Tarifa_medicamento();
										tarifa.setCodigo_empresa(manuales_tarifarios
												.getCodigo_empresa());
										tarifa.setCodigo_sucursal(manuales_tarifarios
												.getCodigo_sucursal());
										tarifa.setCodigo_administradora(manuales_tarifarios
												.getCodigo_administradora());
										tarifa.setId_plan(manuales_tarifarios
												.getId_contrato());
										tarifa.setCodigo_pcd(articulo
												.getCodigo_articulo());
										tarifa = tarifa_medicamentoService
												.consultar(tarifa);
										if (tarifa != null) {
											valor = tarifa.getValor();
											valor_real = valor;
										}
									}
								}

								if (manuales_tarifarios != null) {
									if (articulo.getGrupo1().equals("01")) {
										if (manuales_tarifarios
												.getTipo_medicamento().equals(
														"01")) {
											descuento = (int) (valor * (manuales_tarifarios
													.getMedicamentos() / 100));
											valor -= descuento;
										} else {
											incremento = (int) (valor * (manuales_tarifarios
													.getMedicamentos() / 100));
											valor += incremento;
										}
									} else if (articulo.getGrupo1()
											.equals("02")) {
										if (manuales_tarifarios
												.getTipo_servicio()
												.equals("01")) {
											descuento = (int) (valor * (manuales_tarifarios
													.getServicios() / 100));
											valor -= descuento;
										} else {
											incremento = (int) (valor * (manuales_tarifarios
													.getServicios() / 100));
											valor += incremento;
										}
									}
								}

								if (i == 1) {
									if (manuales_tarifarios.getTipo_servicio()
											.equals("01")) {
										descuento = (int) (valor * (manuales_tarifarios
												.getMedicamentos() / 100));
										valor -= descuento;
									} else if (manuales_tarifarios
											.getTipo_servicio().equals("02")) {
										incremento = (int) (valor * (manuales_tarifarios
												.getMedicamentos() / 100));
										valor += incremento;
									}
								} else {
									if (manuales_tarifarios.getTipo_servicio()
											.equals("01")) {
										descuento = (int) (valor * (manuales_tarifarios
												.getServicios() / 100));
										valor -= descuento;
									} else if (manuales_tarifarios
											.getTipo_servicio().equals("02")) {
										incremento = (int) (valor * (manuales_tarifarios
												.getServicios() / 100));
										valor += incremento;
									}
								}

								di.setValor_unitario(valor);
								di.setValor_total(valor * di.getUnidades());
								di.setValor_real(valor_real);
								di.setDescuento(descuento);
								di.setIncremento(incremento);
								datos_medicamentosDao.actualizar(di);
							} else {
								// log.info("no existe medicamento con el codigo ====> "
								// + di.getCodigo_medicamento());
							}
						}
					}

					// Recalculamos datos servicios //
					Map<String, Object> mapDatos = new HashMap<String, Object>();
					mapDatos.put("codigo_empresa", admision.getCodigo_empresa());
					mapDatos.put("codigo_sucursal",
							admision.getCodigo_sucursal());
					mapDatos.put("nro_ingreso", admision.getNro_ingreso());
					mapDatos.put("nro_identificacion",
							admision.getNro_identificacion());
					List<Datos_servicio> lista_datos_sers = datos_servicioDao
							.listar(mapDatos);
					for (Datos_servicio ds : lista_datos_sers) {
						Articulo articulo = new Articulo();
						articulo.setCodigo_empresa(admision.getCodigo_empresa());
						articulo.setCodigo_sucursal(admision
								.getCodigo_sucursal());
						articulo.setCodigo_articulo(ds.getCodigo_servicio());

						articulo = articuloDao.consultar(articulo);

						if (articulo != null) {
							double valor = articulo.getPrecio1();
							double valor_real = valor;
							double descuento = 0, incremento = 0;
							if (manuales_tarifarios.getServicios() == 0) {
								valor = articulo.getPrecio1();
							}
							if (manuales_tarifarios.getTipo_servicio().equals(
									"01")) {
								descuento = (int) (valor * (manuales_tarifarios
										.getServicios() / 100));
								valor -= descuento;
							} else if (manuales_tarifarios.getTipo_servicio()
									.equals("02")) {
								incremento = (int) (valor * (manuales_tarifarios
										.getServicios() / 100));
								valor += incremento;
							}

							ds.setValor_unitario(valor);
							ds.setValor_total(valor * ds.getUnidades());
							ds.setValor_real(valor_real);
							ds.setDescuento(descuento);
							ds.setIncremento(incremento);
							datos_servicioDao.actualizar(ds);
						}
					}

					if (facturacion != null) {
						this.actualizarFacturaAutomatico(
								admision.getCodigo_empresa(),
								admision.getCodigo_sucursal(),
								admision.getNro_ingreso(),
								admision.getNro_identificacion());
					}
					recalculada = true;
				} else {
					admision_sin_servicio = true;
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
		map.put("recalculada", recalculada);
		map.put("radicadas", radicadas);
		map.put("facturada", facturada);
		map.put("admision_sin_fact", admision_sin_fact);
		map.put("admision_sin_servicio", admision_sin_servicio);
		return map;
	}

	public Map<String, Object> obtenerValorPcd(
			Manuales_tarifarios manuales_tarifarios, String codigo) {

		try {
			return Utilidades.getProcedimiento(manuales_tarifarios, new Long(
					codigo), ServiciosDisponiblesUtils.getServiceLocator());
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}

	}

	/**
	 * Este subclase va ha tener el codigo de la factura de rips y el
	 * codigo_documento_asignado
	 *
	 */
	public class FacturaDocumento {

		String nro_factura_rips;
		String codigo_documento;

		public FacturaDocumento(String nro_factura_rips, String codigo_documento) {
			super();
			this.nro_factura_rips = nro_factura_rips;
			this.codigo_documento = codigo_documento;
		}
	}

	public void crearFacturasDesdeRips(Map<String, Object> params) {
		try {
			String ac = (String) params.get("ac");
			String af = (String) params.get("af");
			String am = (String) params.get("am");
			String ap = (String) params.get("ap");
			String at = (String) params.get("at");
			String ct = (String) params.get("ct");

			String us = (String) params.get("us");
			String ad = (String) params.get("ad");
			String ah = (String) params.get("ah");
			String au = (String) params.get("au");
			String an = (String) params.get("an");

			Administradora administradora = (Administradora) params
					.get("admin");
			Contratos contratos = (Contratos) params.get("contrato");
			Timestamp fecha_contabilizacion = (Timestamp) params
					.get("fecha_contabilizacion");

			// List<FacturaDocumento> facturaDocumentos = new
			// ArrayList<FacturaDocumento>();
			Usuarios usuario = (Usuarios) params.get("usuario");
			Map<String, Object> map_consecutivos_facturas = new HashMap<String, Object>();
			Map<String, Object> map_facturas = new HashMap<String, Object>();

			/* controles de cuantas detalles de facturas son */
			// boolean thereAF = false;
			boolean encontroAC = false;
			boolean encontroAP = false;
			boolean encontroAM = false;
			boolean encontroAT = false;

			int tamanioAF = 0;
			int tamanioAC = 0;
			int tamanioAP = 0;
			int tamanioAM = 0;
			int tamanioAT = 0;

			// adicionales
			boolean encontroUS = false;
			boolean encontroAD = false;
			boolean encontroAH = false;
			boolean encontroAU = false;
			boolean encontroAN = false;

			int tamanioUS = 0;
			int tamanioAH = 0;
			int tamanioAU = 0;
			int tamanioAN = 0;
			int tamanioAD = 0;

			// String nro_factura = "";

			/* verificamos el archivo de control */
			StringTokenizer stringTokenizer = new StringTokenizer(ct, "\n");
			while (stringTokenizer.hasMoreTokens()) {
				String facturaString = stringTokenizer.nextToken();
				List<String> in = LineStringToList.toList(facturaString);
				if (in == null) {
					throw new ValidacionRunTimeException(
							"error al cargar el archivo ct");
				}

				// String codigo_prestador = "" + in.get(0);
				// Timestamp fecha_remision = LineStringToList.convertTo("" +
				// in.get(1));
				String codigo_archivo = "" + in.get(2);
				String total_reg = ("" + in.get(3)).trim();

				/* cargamos cantidades */
				if (codigo_archivo.startsWith("AF")) {
					// thereAF = true;
					tamanioAF = Integer.parseInt(total_reg);
				} else if (codigo_archivo.startsWith("AD")) {
					// thereAF = true;
					encontroAD = true;
					tamanioAD = Integer.parseInt(total_reg);
					if (ad == null) {
						throw new ValidacionRunTimeException("Archivo "
								+ codigo_archivo + " requerido");
					}
				} else if (codigo_archivo.startsWith("AC")) {
					encontroAC = true;
					tamanioAC = Integer.parseInt(total_reg);
					if (ac == null) {
						throw new ValidacionRunTimeException("Archivo "
								+ codigo_archivo + " requerido");
					}
				} else if (codigo_archivo.startsWith("AP")) {
					encontroAP = true;
					tamanioAP = Integer.parseInt(total_reg);
					if (ap == null) {
						throw new ValidacionRunTimeException("Archivo "
								+ codigo_archivo + " requerido");
					}
				} else if (codigo_archivo.startsWith("AM")) {
					encontroAM = true;
					tamanioAM = Integer.parseInt(total_reg);
					if (am == null) {
						throw new ValidacionRunTimeException("Archivo "
								+ codigo_archivo + " requerido");
					}
				} else if (codigo_archivo.startsWith("AT")) {
					encontroAT = true;
					tamanioAT = Integer.parseInt(total_reg);
					if (at == null) {
						throw new ValidacionRunTimeException("Archivo "
								+ codigo_archivo + " requerido");
					}
				} else {
					if (codigo_archivo.toUpperCase().startsWith("US")) {
						encontroUS = true;
						tamanioUS = Integer.parseInt(total_reg);
						if (us == null) {
							throw new ValidacionRunTimeException("Archivo "
									+ codigo_archivo + " requerido");
						}
					} else if (codigo_archivo.toUpperCase().startsWith("AH")) {
						encontroAH = true;
						tamanioAH = Integer.parseInt(total_reg);
						if (ah == null) {
							throw new ValidacionRunTimeException("Archivo "
									+ codigo_archivo + " requerido");
						}
					} else if (codigo_archivo.toUpperCase().startsWith("AU")) {
						encontroAU = true;
						tamanioAU = Integer.parseInt(total_reg);
						if (au == null) {
							throw new ValidacionRunTimeException("Archivo "
									+ codigo_archivo + " requerido");
						}
					} else if (codigo_archivo.toUpperCase().startsWith("AN")) {
						encontroAN = true;
						tamanioAN = Integer.parseInt(total_reg);
						if (an == null) {
							throw new ValidacionRunTimeException("Archivo "
									+ codigo_archivo + " requerido");
						}
					}
				}
			}
			// Administradora administradora = null;
			stringTokenizer = new StringTokenizer(af, "\n");
			if (stringTokenizer.countTokens() != tamanioAF) {
				throw new ValidacionRunTimeException(
						"El archivo AF no esta completo faltan "
								+ (tamanioAF - stringTokenizer.countTokens()
										+ " facturas.\n Total en CT: "
										+ tamanioAF + "\n Total en AF: " + stringTokenizer
											.countTokens()));
			}
			while (stringTokenizer.hasMoreTokens()) {
				String facturaString = stringTokenizer.nextToken();
				List<String> in = LineStringToList.toList(facturaString);
				if (in == null) {
					throw new ValidacionRunTimeException(
							"error al cargar facturas");
				}

				administradoraService
						.guardarTerceroAdministradora(administradora);
				// }

				Facturacion facturacion = new Facturacion();
				facturacion.setCodigo_empresa(usuario.getCodigo_empresa());
				facturacion.setCodigo_sucursal(usuario.getCodigo_sucursal());
				facturacion
						.setCodigo_comprobante(IConstantes.COMPROBANTE_FACTURA_GLOSA);
				facturacion.setCodigo_tercero("" + in.get(3));
				facturacion
						.setFecha(LineStringToList.convertTo("" + in.get(5)));
				facturacion.setFactura("" + in.get(4));

				// nro_factura = facturacion.getCodigo_documento();

				/* calculamos fecha de vencimiento */
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(facturacion.getFecha());
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) + 30);
				facturacion.setFecha_vencimiento(new Timestamp(calendar
						.getTimeInMillis()));
				facturacion.setTotal_cuotas(0);
				facturacion.setCodigo_tercero("" + in.get(3));
				facturacion.setPlazo(30);
				facturacion.setForma_pago("02");
				facturacion
						.setDescripcion("FACTURA DE COMPRA POR PRESTACION DE SERVICIOS");
				facturacion.setObservacion("");
				facturacion.setTipo(IConstantes.TIPO_FACTURA_GLOSA);
				facturacion.setEstado("FACT");
				facturacion.setCreacion_date(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				facturacion.setUltimo_update(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				facturacion.setCreacion_user(usuario.getCodigo());
				facturacion.setUltimo_user(usuario.getCodigo());
				facturacion.setCodigo_credito("");
				facturacion.setRemision("");
				facturacion.setPrefijo("");
				facturacion.setAnio(new SimpleDateFormat("yyyy")
						.format(facturacion.getFecha()));
				facturacion.setMes(new SimpleDateFormat("MM")
						.format(facturacion.getFecha()));
				facturacion.setClasificacion("");
				facturacion.setVerificado("N");
				facturacion.setRetencion(0);
				facturacion.setNro_ingreso("");
				facturacion.setFecha_inicio(LineStringToList.convertTo(""
						+ in.get(6)));
				facturacion.setFecha_final(LineStringToList.convertTo(""
						+ in.get(7)));
				facturacion
						.setCodigo_administradora(administradora.getCodigo());
				// facturacion.setId_plan("" + in.get(11));
				facturacion.setNro_contrato("" + in.get(10));
				facturacion.setId_plan(contratos.getId_plan());

				facturacion.setNro_poliza("" + in.get(12));
				facturacion.setValor_total(Double.parseDouble(in.get(16)));
				facturacion.setValor_cuota(0);
				facturacion.setValor_copago(Double.parseDouble(in.get(13)));
				facturacion.setNocopago("");
				facturacion.setDto_factura(Double.parseDouble(in.get(15)));
				facturacion.setDto_copago(0);
				facturacion.setCop_canc(0);
				facturacion.setFactura("");

				// Este campo llega el codigo de la factura externa
				facturacion.setDocumento_externo("" + in.get(4));

				facturacion.setPost("N");
				facturacion.setTipo_identificacion("");
				facturacion.setDia_pago(0);
				facturacion.setEs_credito(false);
				facturacion.setRadicado("S");
				facturacion.setValor_negocio(0);
				facturacion.setPorc_interes(0);
				facturacion.setC_inicial(0);
				facturacion.setAbono_inicial(0);
				facturacion.setDescuento_pp(0);
				facturacion.setMedio_pago("");
				facturacion.setReteica(0);
				facturacion.setReteiva(0);
				facturacion.setFecha_glosa(null);
				facturacion.setValor_glosa_inicial(0);
				facturacion.setValor_glosa_aceptada(0);
				facturacion.setValor_glosa_noaceptada(0);
				facturacion.setObservaciones("");
				facturacion.setEstado_glosa("01");
				facturacion.setMotivo_glosa("");
				facturacion.setValor_glosa_levantada(0);
				facturacion.setValor_glosa_ratificada(0);
				facturacion.setCuenta_retencion("");
				facturacion.setAnio_retencion("");
				facturacion.setTipo_glosa("0");
				facturacion
						.setValor_pagar_factura(facturacion.getValor_total());
				facturacion.setFact_glosada("N");
				facturacion.setFecha_contabilizacion(fecha_contabilizacion);

				// consultar por factura externa
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", facturacion.getCodigo_empresa());
				map.put("codigo_sucursal", facturacion.getCodigo_sucursal());
				map.put("documento_externo", facturacion.getDocumento_externo()
						+ "");
				map.put("codigo_administradora",
						facturacion.getCodigo_administradora());
				map.put("tipo", IConstantes.TIPO_FACTURA_GLOSA);

				if (existePorParametro(map)) {
					throw new ValidacionRunTimeException(
							"Nro de factura de venta "
									+ facturacion.getDocumento_externo()
									+ " del prestador "
									+ administradora.getNombre()
									+ " ya se encuentra en la base de datos");
				}

				facturacionDao.crear(facturacion);

				map_facturas.put(facturacion.getDocumento_externo(),
						facturacion);
			}

			/* generamos los detalles ac - consulta */
			if (ac != null && !ac.trim().isEmpty()) {
				if (!encontroAC) {
					throw new ValidacionRunTimeException(
							"El archivo AC no esta registrado");
				}

				int i_ac = 0;
				stringTokenizer = new StringTokenizer(ac, "\n");
				if (stringTokenizer.countTokens() != tamanioAC) {
					throw new ValidacionRunTimeException(
							"El archivo AC no esta completo faltan "
									+ (tamanioAC
											- stringTokenizer.countTokens()
											+ " .\n Total en CT: " + tamanioAC
											+ "\n Total en AC: " + stringTokenizer
												.countTokens()));
				}
				while (stringTokenizer.hasMoreTokens()) {
					String facturaString = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(facturaString);
					if (in == null) {
						throw new ValidacionRunTimeException(
								"Error al cargar la consulta nro " + i_ac
										+ " no trae ningún dato");
					}
					i_ac++;
					// creamos la consulta
					guardarConsultaGlosa(in, map_facturas,
							map_consecutivos_facturas, usuario, i_ac, contratos);

				}
			}

			/* generamos los detalles ap - procedimiento */
			if (ap != null && !ap.trim().isEmpty()) {
				if (!encontroAP) {
					throw new ValidacionRunTimeException(
							"El archivo AP no esta registrado");
				}
				int i_ap = 0;
				stringTokenizer = new StringTokenizer(ap, "\n");
				if (stringTokenizer.countTokens() != tamanioAP) {
					throw new ValidacionRunTimeException(
							"El archivo AP no esta completo faltan "
									+ (tamanioAP
											- stringTokenizer.countTokens()
											+ " .\n Total en CT: " + tamanioAP
											+ "\n Total en AP: " + stringTokenizer
												.countTokens()));
				}
				while (stringTokenizer.hasMoreTokens()) {
					String facturaString = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(facturaString);
					if (in == null) {
						throw new ValidacionRunTimeException(
								"error al detalle de factura");
					}

					i_ap++;
					if (in.size() != 15) {
						throw new ValidacionRunTimeException(
								"Error al cargar la linea ? de AP".replace("?",
										"" + i_ap));
					}

					guardarProcesamientoGlosa(in, map_facturas,
							map_consecutivos_facturas, usuario, i_ap, contratos);
				}
			}
			// /* generamos los detalles am */
			if (am != null && !am.trim().isEmpty()) {
				if (!encontroAM) {
					throw new ValidacionRunTimeException(
							"El archivo AM no esta registrado");
				}
				int i_am = 0;
				stringTokenizer = new StringTokenizer(am, "\n");
				if (stringTokenizer.countTokens() != tamanioAM) {
					throw new ValidacionRunTimeException(
							"El archivo AM no esta completo faltan "
									+ (tamanioAM
											- stringTokenizer.countTokens()
											+ " .\n Total en CT: " + tamanioAM
											+ "\n Total en AM: " + stringTokenizer
												.countTokens()));
				}
				while (stringTokenizer.hasMoreTokens()) {
					String facturaString = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(facturaString);
					if (in == null) {
						throw new ValidacionRunTimeException(
								"error al cargar facturas");
					}
					i_am++;
					if (in.size() != 14) {
						throw new ValidacionRunTimeException(
								"Error al cargar la linea ? de AM".replace("?",
										"" + i_am));
					}

					guardarMedicamentosGlosa(in, map_facturas,
							map_consecutivos_facturas, usuario, i_am, contratos);
				}
			}
			// /* generamos los detalles at */
			if (at != null && !at.trim().isEmpty()) {
				if (!encontroAT) {
					throw new ValidacionRunTimeException(
							"El archivo AT no esta registrado");
				}
				int i_at = 0;
				stringTokenizer = new StringTokenizer(at, "\n");
				if (stringTokenizer.countTokens() != tamanioAT) {
					throw new ValidacionRunTimeException(
							"El archivo AT no esta completo faltan "
									+ (tamanioAT
											- stringTokenizer.countTokens()
											+ " .\n Total en CT: " + tamanioAT
											+ "\n Total en AT: " + stringTokenizer
												.countTokens()));
				}
				while (stringTokenizer.hasMoreTokens()) {
					String facturaString = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(facturaString);
					if (in == null) {
						throw new ValidacionRunTimeException(
								"error al cargar facturas");
					}
					i_at++;
					if (in.size() != 11) {
						throw new ValidacionRunTimeException(
								"Error al cargar la linea ? de AT".replace("?",
										"" + i_at));
					}
					guardarOtrosServiciosGlosa(in, map_facturas,
							map_consecutivos_facturas, usuario, i_at, contratos);
				}
			}

			/* generamos los detalles us */
			if (us != null && !us.trim().isEmpty()) {
				if (!encontroUS) {
					throw new ValidacionRunTimeException(
							"El archivo US no esta registrado");
				}
				int i_us = 0;
				stringTokenizer = new StringTokenizer(us, "\n");
				if (stringTokenizer.countTokens() != tamanioUS) {
					throw new ValidacionRunTimeException(
							"El archivo US no esta completo faltan "
									+ (tamanioUS
											- stringTokenizer.countTokens()
											+ " .\n Total en CT: " + tamanioUS
											+ "\n Total en US: " + stringTokenizer
												.countTokens()));
				}
				while (stringTokenizer.hasMoreTokens()) {
					String facturaString = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(facturaString);
					if (in == null) {
						throw new ValidacionRunTimeException(
								"error al cargar facturas");
					}
					i_us++;
					if (in.size() != 14) {
						throw new ValidacionRunTimeException(
								"Error al cargar la linea ? de US".replace("?",
										"" + i_us));
					}
					Rips_us rips_us = ConvertidorRipAObjeto.convertirAObjeto(
							in, Rips_us.class);
					rips_us.setCodigo_empresa(usuario.getCodigo_empresa());
					rips_us.setCodigo_sucursal(usuario.getCodigo_sucursal());
					rips_us.setCreacion_date(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_us.setUltimo_update(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_us.setCreacion_user(usuario.getCodigo());
					rips_us.setUltimo_user(usuario.getCodigo());
					if (rips_us.getEdad().trim().isEmpty()) {
						throw new ValidacionRunTimeException(
								"La edad en el archivo US esta vacia, en la linea: "
										+ i_us);
					} else if (!rips_us.getEdad().matches("[0-9]*$")) {
						throw new ValidacionRunTimeException(
								"La edad en el archivo US no es un numero valido, en la linea: "
										+ i_us);
					}
					rips_usService.crear(rips_us);
				}
			}

			/* generamos los detalles ah */
			if (ah != null && !ah.trim().isEmpty()) {
				if (!encontroAH) {
					throw new ValidacionRunTimeException(
							"El archivo AH no esta registrado");
				}
				int i_ah = 0;
				stringTokenizer = new StringTokenizer(ah, "\n");
				if (stringTokenizer.countTokens() != tamanioAH) {
					throw new ValidacionRunTimeException(
							"El archivo US no esta completo faltan "
									+ (tamanioUS
											- stringTokenizer.countTokens()
											+ " .\n Total en CT: " + tamanioAH
											+ "\n Total en AH: " + stringTokenizer
												.countTokens()));
				}
				while (stringTokenizer.hasMoreTokens()) {
					String facturaString = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(facturaString);
					if (in == null) {
						throw new ValidacionRunTimeException(
								"error al cargar facturas");
					}
					i_ah++;
					if (in.size() != 19) {
						throw new ValidacionRunTimeException(
								"Error al cargar la linea ? de AH".replace("?",
										"" + i_ah));
					}
					Rips_ah rips_ah = ConvertidorRipAObjeto.convertirAObjeto(
							in, Rips_ah.class);
					rips_ah.setCodigo_empresa(usuario.getCodigo_empresa());
					rips_ah.setCodigo_sucursal(usuario.getCodigo_sucursal());
					rips_ah.setCreacion_date(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_ah.setUltimo_update(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_ah.setCreacion_user(usuario.getCodigo());
					rips_ah.setUltimo_user(usuario.getCodigo());
					rips_ah.setCodigo_comprobante(IConstantes.COMPROBANTE_FACTURA_GLOSA);
					Facturacion facturacion = (Facturacion) map_facturas
							.get(rips_ah.getNro_factura());
					if (facturacion == null) {
						throw new ValidacionRunTimeException(
								"La factura ?3 para el detalle ?1 en la line ?2 no se encuentra"
										.replace("?3", rips_ah.getNro_factura())
										.replace("?1", "")
										.replace("?2", i_ah + ""));
					}

					rips_ah.setCodigo_documento(facturacion
							.getCodigo_documento_res());

					rips_ahService.crear(rips_ah);

					guardarDiagnosticoHaMorbilidad(
							rips_ah.getDiagnostico_causa_basica(),
							IConstantesGlosas.MORTALIDAD_GENERAL, usuario,
							rips_ah.getNro_id(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_ah.getDiagnostico_complicacion(),
							IConstantesGlosas.MORBILIDAD_HOSPITALIZACION,
							usuario, rips_ah.getNro_id(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_ah.getDiagnostico_principal_egreso(),
							IConstantesGlosas.MORBILIDAD_HOSPITALIZACION,
							usuario, rips_ah.getNro_id(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_ah.getDiagnostico_principal_egreso_1(),
							IConstantesGlosas.MORBILIDAD_HOSPITALIZACION,
							usuario, rips_ah.getNro_id(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_ah.getDiagnostico_principal_egreso_2(),
							IConstantesGlosas.MORBILIDAD_HOSPITALIZACION,
							usuario, rips_ah.getNro_id(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_ah.getDiagnostico_principal_egreso_3(),
							IConstantesGlosas.MORBILIDAD_HOSPITALIZACION,
							usuario, rips_ah.getNro_id(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_ah.getDiagnostico_principal_ingreso(),
							IConstantesGlosas.MORBILIDAD_HOSPITALIZACION,
							usuario, rips_ah.getNro_id(),
							facturacion.getCodigo_administradora());
					/* fin guardar morbilidad */
				}
			}

			/* generamos los detalles au */
			if (au != null && !au.trim().isEmpty()) {
				if (!encontroAU) {
					throw new ValidacionRunTimeException(
							"El archivo AU no esta registrado");
				}
				int i_au = 0;
				stringTokenizer = new StringTokenizer(au, "\n");
				if (stringTokenizer.countTokens() != tamanioAU) {
					throw new ValidacionRunTimeException(
							"El archivo US no esta completo faltan "
									+ (tamanioUS
											- stringTokenizer.countTokens()
											+ " .\n Total en CT: " + tamanioAU
											+ "\n Total en AU: " + stringTokenizer
												.countTokens()));
				}
				while (stringTokenizer.hasMoreTokens()) {
					String facturaString = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(facturaString);
					if (in == null) {
						throw new ValidacionRunTimeException(
								"error al cargar facturas");
					}
					i_au++;
					if (in.size() != 15) {
						throw new ValidacionRunTimeException(
								"Error al cargar la linea ? de AU".replace("?",
										"" + i_au));
					}
					Rips_au rips_au = ConvertidorRipAObjeto.convertirAObjeto(
							in, Rips_au.class);
					rips_au.setCodigo_empresa(usuario.getCodigo_empresa());
					rips_au.setCodigo_sucursal(usuario.getCodigo_sucursal());
					rips_au.setCreacion_date(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_au.setUltimo_update(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_au.setCreacion_user(usuario.getCodigo());
					rips_au.setUltimo_user(usuario.getCodigo());

					Facturacion facturacion = (Facturacion) map_facturas
							.get(rips_au.getNro_factura());
					if (facturacion == null) {
						throw new ValidacionRunTimeException(
								"La factura ?3 para el detalle ?1 en la line ?2 no se encuentra"
										.replace("?3", rips_au.getNro_factura())
										.replace("?1", "AU")
										.replace("?2", i_au + ""));
					}
					rips_au.setCodigo_documento(facturacion
							.getCodigo_documento_res());
					rips_au.setCodigo_comprobante(IConstantes.COMPROBANTE_FACTURA_GLOSA);
					rips_auService.crear(rips_au);

					/* diagnostico */
					//
					guardarDiagnosticoHaMorbilidad(
							rips_au.getDiagnostico_relacionado_1_salida(),
							IConstantesGlosas.MORBILIDAD_URGENCIA, usuario,
							rips_au.getNro_id(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_au.getDiagnostico_relacionado_2_salida(),
							IConstantesGlosas.MORBILIDAD_URGENCIA, usuario,
							rips_au.getNro_id(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_au.getDiagnostico_relacionado_3_salida(),
							IConstantesGlosas.MORBILIDAD_URGENCIA, usuario,
							rips_au.getNro_id(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_au.getDiagnostico_salida(),
							IConstantesGlosas.MORBILIDAD_URGENCIA, usuario,
							rips_au.getNro_id(),
							facturacion.getCodigo_administradora());
				}
			}

			// registro de rips ad
			if (ad != null && !ad.trim().isEmpty()) {
				if (!encontroAD) {
					throw new ValidacionRunTimeException(
							"El archivo AD no esta registrado");
				}
				int i_ad = 0;
				stringTokenizer = new StringTokenizer(ad, "\n");
				if (stringTokenizer.countTokens() != tamanioAD) {
					throw new ValidacionRunTimeException(
							"El archivo AD no esta completo faltan "
									+ (tamanioAD
											- stringTokenizer.countTokens()
											+ " .\n Total en CT: " + tamanioAD
											+ "\n Total en AD: " + stringTokenizer
												.countTokens()));
				}
				while (stringTokenizer.hasMoreTokens()) {
					String facturaString = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(facturaString);
					if (in == null) {
						throw new ValidacionRunTimeException(
								"error al cargar facturas");
					}
					i_ad++;
					if (in.size() != 6) {
						throw new ValidacionRunTimeException(
								"Error al cargar la linea ? de AD".replace("?",
										"" + i_ad));
					}

					Rips_ad rips_ad = ConvertidorRipAObjeto.convertirAObjeto(
							in, Rips_ad.class);
					rips_ad.setCodigo_empresa(usuario.getCodigo_empresa());
					rips_ad.setCodigo_sucursal(usuario.getCodigo_sucursal());
					rips_ad.setCreacion_date(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_ad.setUltimo_update(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_ad.setCreacion_user(usuario.getCodigo());
					rips_ad.setUltimo_user(usuario.getCodigo());
					Facturacion facturacion = (Facturacion) map_facturas
							.get(rips_ad.getNro_factura());
					if (facturacion == null) {
						throw new ValidacionRunTimeException(
								"La factura ?3 para el detalle ?1 en la line ?2 no se encuentra"
										.replace("?3", rips_ad.getNro_factura())
										.replace("?1", "AD")
										.replace("?2", i_ad + ""));
					}
					rips_ad.setCodigo_documento(facturacion
							.getCodigo_documento_res());
					rips_ad.setCodigo_comprobante(IConstantes.COMPROBANTE_FACTURA_GLOSA);
					rips_adService.crear(rips_ad);
				}
			}

			/* generamos los detalles au */
			if (an != null && !an.trim().isEmpty()) {
				if (!encontroAN) {
					throw new ValidacionRunTimeException(
							"El archivo AN no esta registrado");
				}
				int i_an = 0;
				stringTokenizer = new StringTokenizer(an, "\n");
				if (stringTokenizer.countTokens() != tamanioAN) {
					throw new ValidacionRunTimeException(
							"El archivo AN no esta completo faltan "
									+ (tamanioAN
											- stringTokenizer.countTokens()
											+ " .\n Total en CT: " + tamanioAN
											+ "\n Total en AN: " + stringTokenizer
												.countTokens()));
				}
				while (stringTokenizer.hasMoreTokens()) {
					String facturaString = stringTokenizer.nextToken();
					List<String> in = LineStringToList.toList(facturaString);
					if (in == null) {
						throw new ValidacionRunTimeException(
								"error al cargar facturas");
					}
					i_an++;
					if (in.size() != 18) {
						throw new ValidacionRunTimeException(
								"Error al cargar la linea ? de AN".replace("?",
										"" + i_an));
					}
					Rips_an rips_an = ConvertidorRipAObjeto.convertirAObjeto(
							in, Rips_an.class);
					rips_an.setCodigo_empresa(usuario.getCodigo_empresa());
					rips_an.setCodigo_sucursal(usuario.getCodigo_sucursal());
					rips_an.setCreacion_date(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_an.setUltimo_update(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					rips_an.setCreacion_user(usuario.getCodigo());
					rips_an.setUltimo_user(usuario.getCodigo());
					Facturacion facturacion = (Facturacion) map_facturas
							.get(rips_an.getNro_factura());
					if (facturacion == null) {
						throw new ValidacionRunTimeException(
								"La factura ?3 para el detalle ?1 en la line ?2 no se encuentra"
										.replace("?3", rips_an.getNro_factura())
										.replace("?1", "AN")
										.replace("?2", i_an + ""));
					}
					rips_an.setCodigo_documento(facturacion
							.getCodigo_documento_res());
					rips_an.setCodigo_comprobante(IConstantes.COMPROBANTE_FACTURA_GLOSA);
					rips_anService.crear(rips_an);

					//
					guardarDiagnosticoHaMorbilidad(
							rips_an.getDiagnostico_recien_nacido(),
							IConstantesGlosas.MORBILIDAD_HOSPITALIZACION,
							usuario, rips_an.getNro_id_madre(),
							facturacion.getCodigo_administradora());
					//
					guardarDiagnosticoHaMorbilidad(
							rips_an.getCausa_basica_muerte(),
							IConstantesGlosas.MORTALIDAD_GENERAL, usuario,
							rips_an.getNro_id_madre(),
							facturacion.getCodigo_administradora());
				}
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite guardar lso tipos de morbilidad
	 *
	 */
	public void guardarDiagnosticoHaMorbilidad(String codigo_diagnostico,
			String tipo_morbilidad, Usuarios usuarios, String nro_idenficacion,
			String codigo_tercero) {
		try {
			if (codigo_diagnostico != null) {
				if (!codigo_diagnostico.trim().isEmpty()) {
					Morbilidad_diagnosticos morbilidad_diagnosticos = new Morbilidad_diagnosticos();
					morbilidad_diagnosticos.setCodigo_empresa(usuarios
							.getCodigo_empresa());
					morbilidad_diagnosticos.setCodigo_sucursal(usuarios
							.getCodigo_sucursal());
					morbilidad_diagnosticos
							.setCodigo_diagnostico(codigo_diagnostico);
					morbilidad_diagnosticos.setTipo_morbilidad(tipo_morbilidad);
					morbilidad_diagnosticos
							.setNro_identificacion(nro_idenficacion);
					morbilidad_diagnosticos.setCreacion_date(new Timestamp(
							Calendar.getInstance().getTimeInMillis()));
					morbilidad_diagnosticos.setCreacion_user(usuarios
							.getCodigo());
					morbilidad_diagnosticos
							.setUltimo_user(usuarios.getCodigo());
					morbilidad_diagnosticos.setCodigo_tercero(codigo_tercero);
					morbilidad_diagnosticosDao.crear(morbilidad_diagnosticos);
				}
			}
		} catch (Exception e) {
			// log.info(e);
			e.printStackTrace();
			MensajesUtil.mensajeAlerta("Advertencia!!",
					"Se presento un error, a la hora de guardar los diagnosticos.\n"
							+ "por favor comuniquese con su provedor");
		}
	}

	public void guardarDiagnosticoHaMorbilidad(String codigo_diagnostico,
			Usuarios usuarios, String nro_idenficacion, String codigo_tercero) {
		guardarDiagnosticoHaMorbilidad(codigo_diagnostico,
				getTipoMorbilidad(codigo_diagnostico), usuarios,
				nro_idenficacion, codigo_tercero);
	}

	public static String getTipoMorbilidad(String codigo_diasnostico) {
		/* verificamos si es de odontologia */
		boolean esOdonotologico = esOdonotologico(codigo_diasnostico);
		return esOdonotologico ? IConstantesGlosas.MORBILIDAD_ODONTOLOGIA
				: IConstantesGlosas.MORBILIDAD_CONSULTA_EXTERNA;
	}

	private static boolean esOdonotologico(String codigo_diasnostico) {
		return ("@K000" + "@K001" + "@K002" + "@K003" + "@K004" + "@K005"
				+ "@K006" + "@K007" + "@K008" + "@K009" + "@K010" + "@K011"
				+ "@K020" + "@K021" + "@K022" + "@K023" + "@K024" + "@K028"
				+ "@K029" + "@K030" + "@K031" + "@K032" + "@K033" + "@K034"
				+ "@K035" + "@K036" + "@K037" + "@K038" + "@K039" + "@K040"
				+ "@K041" + "@K042" + "@K043" + "@K044" + "@K045" + "@K046"
				+ "@K047" + "@K048" + "@K049" + "@K050" + "@K051" + "@K052"
				+ "@K053" + "@K054" + "@K055" + "@K056" + "@K060" + "@K061"
				+ "@K062" + "@K068" + "@K069" + "@K070" + "@K071" + "@K072"
				+ "@K073" + "@K074" + "@K075" + "@K076" + "@K078" + "@K079"
				+ "@K080" + "@K081" + "@K082" + "@K083" + "@K088" + "@K089"
				+ "@K090" + "@K091" + "@K092" + "@K098" + "@K099" + "@K100"
				+ "@K101" + "@K102" + "@K103" + "@K108" + "@K109" + "@K110"
				+ "@K111" + "@K112" + "@K113" + "@K114" + "@K115" + "@K116"
				+ "@K117" + "@K118" + "@S025" + "@Z012" + "@Z463" + "@Z464"
				+ "@Z762" + "@Z965" + "@Z972").contains(codigo_diasnostico
				.toUpperCase());
	}

	/* CREACION DE RIPS */
	private void guardarConsultaGlosa(List<String> in,
			Map<String, Object> map_facturas,
			Map<String, Object> map_consecutivos, Usuarios usuarios,
			int consecutivo_rips, Contratos contratos) {
		String codigo_documento = "" + in.get(0);
		Facturacion facturacion = (Facturacion) map_facturas
				.get(codigo_documento);
		if (facturacion == null) {
			throw new ValidacionRunTimeException(
					"La factura numero ?3 no se encuentra".replace("?3",
							codigo_documento));
		}

		// verificamos los planes que tenga contratado las consultas
		String nro_identificacion = "" + in.get(3);
		// String id_plan = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", usuarios.getCodigo_empresa());
		map.put("codigo_sucursal", usuarios.getCodigo_sucursal());
		map.put("nro_identificacion", nro_identificacion);
		if (!pacienteDao.existe(map)) {
			Paciente pacienteNoExiste = ServiciosDisponiblesUtils
					.getPacienteNoExistente(usuarios.getCodigo_empresa(),
							usuarios.getCodigo_sucursal());
			nro_identificacion = pacienteNoExiste.getNro_identificacion();
			// id_plan = IConstantes._TIPO_CONTRATO_NO_EXISTENTE;
		}

		// reaultados
		double valor_consulta = 0;
		double valor_cuota = 0;
		double valor_neto = 0;

		// valores en los rips
		String rips_valor_consulta = "" + in.get(14);
		String rips_valor_cuota = "" + in.get(15);
		String rips_valor_neto = "" + in.get(16);

		if (!rips_valor_consulta.trim().isEmpty()
				&& rips_valor_consulta.matches("[0-9.,]*$")) {
			valor_consulta = Double.parseDouble(rips_valor_consulta);
		}

		if (!rips_valor_cuota.trim().isEmpty()
				&& rips_valor_cuota.matches("[0-9.,]*$")) {
			valor_cuota = Double.parseDouble(rips_valor_cuota);
		}

		if (!rips_valor_neto.trim().isEmpty()
				&& rips_valor_neto.matches("[0-9.,]*$")) {
			valor_neto = Double.parseDouble(rips_valor_neto);
		}

		Datos_consulta datos_consulta = new Datos_consulta();
		datos_consulta.setCodigo_empresa(usuarios.getCodigo_empresa());
		datos_consulta.setCodigo_sucursal(usuarios.getCodigo_sucursal());

		String codigo_cups = in.get(6) + "";
		Long id_procedimiento = procedimientosService
				.consultarIDPorCups(codigo_cups);
		if (id_procedimiento == null) {
			throw new ValidacionRunTimeException(
					"El procedimiento con el código cups " + codigo_cups
							+ " no existe.");
		}

		// datos_consulta.setNro_factura(facturacion.getCodigo_documento());
		datos_consulta.setTipo_identificacion("" + in.get(2));
		datos_consulta.setNro_identificacion(nro_identificacion);
		datos_consulta.setCodigo_administradora(contratos
				.getCodigo_administradora());
		datos_consulta.setId_plan(contratos.getId_plan());
		datos_consulta.setCodigo_prestador(IConstantes.MEDICO_POR_DEFECTO);
		datos_consulta.setNro_ingreso("EXT");
		datos_consulta.setCodigo_consulta(id_procedimiento + "");
		datos_consulta.setFecha_consulta(LineStringToList.convertTo(""
				+ in.get(4)));
		datos_consulta.setNumero_autorizacion("" + in.get(5));
		datos_consulta.setValor_consulta(valor_consulta);
		datos_consulta.setValor_cuota(valor_cuota);
		datos_consulta.setValor_neto(valor_neto);
		datos_consulta.setFinalidad_consulta("" + in.get(7));
		datos_consulta.setCausa_externa("" + in.get(8));
		datos_consulta.setTipo_diagnostico("" + in.get(13));
		datos_consulta.setCodigo_diagnostico_principal("" + in.get(9));
		datos_consulta.setCodigo_diagnostico1("" + in.get(10));
		datos_consulta.setCodigo_diagnostico2("" + in.get(11));
		datos_consulta.setCodigo_diagnostico3("" + in.get(12));
		datos_consulta.setCancelo_copago("N");
		datos_consulta.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		datos_consulta.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		datos_consulta.setCreacion_user(usuarios.getCodigo().toString());
		datos_consulta.setUltimo_user(usuarios.getCodigo().toString());
		datos_consulta.setValor_real(Double.parseDouble("" + in.get(14)));
		datos_consulta.setDescuento(0);
		datos_consulta.setIncremento(0);
		datos_consulta.setCodigo_orden("");
		datos_consulta.setTipo_hc("");
		datos_consulta.setCodigo_programa("");
		datos_consulta.setCita_pyp(false);
		datos_consulta.setCodigo_cita("");

		datos_consultaDao.crear(datos_consulta);

		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(datos_consulta
				.getCodigo_consulta()));
		procedimientos = procedimientosService.consultar(procedimientos);

		// creamos ademas de consulta el detalle
		Detalle_factura detalle = new Detalle_factura();
		detalle.setCodigo_empresa(datos_consulta.getCodigo_empresa());
		detalle.setCodigo_sucursal(datos_consulta.getCodigo_sucursal());
		detalle.setId_factura(facturacion.getId_factura());

		detalle.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		detalle.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		detalle.setCreacion_user(usuarios.getCodigo().toString());
		detalle.setUltimo_user(usuarios.getCodigo().toString());

		detalle.setFacturable(true);
		detalle.setFactura_concepto(datos_consulta.getCodigo_registro() + "");
		detalle.setValor_real(datos_consulta.getValor_real());
		detalle.setCodigo_articulo(datos_consulta.getCodigo_consulta());
		detalle.setDetalle(procedimientos != null ? procedimientos
				.getDescripcion() : "");
		detalle.setCantidad(1);
		detalle.setValor_unitario(datos_consulta.getValor_consulta());
		detalle.setValor_total(datos_consulta.getValor_neto());
		detalle.setTipo("CONSULTA");

		detalle.setC_costos("");
		detalle.setPlan("");

		detalleFacturaDao.crear(detalle);
		// creamos morbilidad
		guardarDiagnosticoHaMorbilidad(
				datos_consulta.getCodigo_diagnostico_principal(), usuarios,
				datos_consulta.getNro_identificacion(),
				datos_consulta.getCodigo_administradora());
		guardarDiagnosticoHaMorbilidad(datos_consulta.getCodigo_diagnostico1(),
				usuarios, datos_consulta.getNro_identificacion(),
				datos_consulta.getCodigo_administradora());
		guardarDiagnosticoHaMorbilidad(datos_consulta.getCodigo_diagnostico2(),
				usuarios, datos_consulta.getNro_identificacion(),
				datos_consulta.getCodigo_administradora());
		guardarDiagnosticoHaMorbilidad(datos_consulta.getCodigo_diagnostico3(),
				usuarios, datos_consulta.getNro_identificacion(),
				datos_consulta.getCodigo_administradora());
	}

	/*
	 * @revisar {Este metodo hay que probarlo con la nueva estructura de
	 * contratos}
	 */
	private void guardarProcesamientoGlosa(List<String> in,
			Map<String, Object> map_facturas,
			Map<String, Object> map_consecutivos_facturas, Usuarios usuario,
			int i_ap, Contratos contratos) {
		String codigo_documento = "" + in.get(0);
		Facturacion facturacion = (Facturacion) map_facturas
				.get(codigo_documento);
		if (facturacion == null) {
			throw new ValidacionRunTimeException(
					"La factura numero ?3 no se encuentra".replace("?3",
							codigo_documento));
		}

		// verificamos los planes que tenga contratado las consultas
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("codigo_empresa", facturacion.getCodigo_empresa());
		mapa.put("codigo_sucursal", facturacion.getCodigo_sucursal());
		mapa.put("codigo_administradora",
				facturacion.getCodigo_administradora());
		mapa.put("servicios", new String[] {
				ServiciosDisponiblesUtils.CODSER_CONSULTA_EXTERNA,
				ServiciosDisponiblesUtils.CODSER_ODONTOLOGIA_GENERAL,
				ServiciosDisponiblesUtils.CODSER_CONSULTA_ESPECIALIZADA });
		List<Servicios_contratados> listado_servicios = servicios_contratadosDao
				.listar(mapa);

		if (!listado_servicios.isEmpty()) {

			String nro_identificacion = "" + in.get(3);
			// String id_plan = "";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", usuario.getCodigo_empresa());
			map.put("codigo_sucursal", usuario.getCodigo_sucursal());
			map.put("nro_identificacion", nro_identificacion);
			if (!pacienteDao.existe(map)) {
				Paciente pacienteNoExiste = ServiciosDisponiblesUtils
						.getPacienteNoExistente(usuario.getCodigo_empresa(),
								usuario.getCodigo_sucursal());
				nro_identificacion = pacienteNoExiste.getNro_identificacion();
			}

			String valor_procedimiento_rips = "" + in.get(14);
			double valor_procedimiento = 0;
			if (!(!valor_procedimiento_rips.trim().isEmpty() || valor_procedimiento_rips
					.matches("[0-9.,]*$"))) {
				throw new ValidacionRunTimeException(
						"El valor del procedimiento en la fila # " + i_ap
								+ " no tiene valor valido");
			}

			if (!valor_procedimiento_rips.trim().isEmpty()
					&& valor_procedimiento_rips.matches("[0-9.,]*$")) {
				valor_procedimiento = Double
						.parseDouble(valor_procedimiento_rips);
			}

			String codigo_cups = in.get(6) + "";
			Long id_procedimiento = procedimientosService
					.consultarIDPorCups(codigo_cups);
			if (id_procedimiento == null) {
				throw new ValidacionRunTimeException(
						"El procedimiento con el código cups " + codigo_cups
								+ " no existe.");
			}

			// creacion de bean de procedimiento
			Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
			datos_procedimiento.setCodigo_empresa(usuario.getCodigo_empresa());
			datos_procedimiento
					.setCodigo_sucursal(usuario.getCodigo_sucursal());
			// datos_procedimiento.setNro_factura(tbxNro_factura.getValue());
			datos_procedimiento.setTipo_identificacion("" + in.get(2));
			datos_procedimiento.setNro_identificacion(nro_identificacion);
			datos_procedimiento.setCodigo_administradora(contratos
					.getCodigo_administradora());
			datos_procedimiento.setId_plan(contratos.getId_plan());
			datos_procedimiento
					.setCodigo_prestador(IConstantes.MEDICO_POR_DEFECTO);
			datos_procedimiento.setNro_ingreso("EXT");
			datos_procedimiento.setFecha_procedimiento(LineStringToList
					.convertTo("" + in.get(4)));
			datos_procedimiento.setCodigo_procedimiento(id_procedimiento + "");
			datos_procedimiento.setNumero_autorizacion("" + in.get(5));
			datos_procedimiento.setValor_procedimiento(valor_procedimiento);
			datos_procedimiento.setUnidades(1);
			datos_procedimiento.setCopago(0);
			datos_procedimiento.setValor_neto(valor_procedimiento);
			datos_procedimiento.setAmbito_procedimiento("" + in.get(7));
			datos_procedimiento.setFinalidad_procedimiento("" + in.get(8));
			datos_procedimiento.setPersonal_atiende("" + in.get(9));
			datos_procedimiento.setForma_realizacion("" + in.get(13));
			datos_procedimiento
					.setCodigo_diagnostico_principal("" + in.get(10));
			datos_procedimiento.setCodigo_diagnostico_relacionado(""
					+ in.get(11));
			datos_procedimiento.setComplicacion("" + in.get(12));
			datos_procedimiento.setCancelo_copago("N");
			datos_procedimiento.setCreacion_date(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			datos_procedimiento.setUltimo_update(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			datos_procedimiento
					.setCreacion_user(usuario.getCodigo().toString());
			datos_procedimiento.setUltimo_user(usuario.getCodigo().toString());
			datos_procedimiento.setCodigo_programa("");
			datos_procedimiento.setTipo_intervencion("");
			datos_procedimiento.setEs_quirurgico("");
			datos_procedimiento.setCobra_cirujano("");
			datos_procedimiento.setCobra_anestesiologo("");
			datos_procedimiento.setCobra_ayudante("");
			datos_procedimiento.setCobra_sala("");
			datos_procedimiento.setCobra_materiales("");
			datos_procedimiento.setCobra_perfusionista("");
			datos_procedimiento.setGrupo("");
			datos_procedimiento.setValor_cirujano(0);
			datos_procedimiento.setValor_anestesiologo(0);
			datos_procedimiento.setValor_ayudante(0);
			datos_procedimiento.setValor_sala(0);
			datos_procedimiento.setValor_materiales(0);
			datos_procedimiento.setValor_perfusionista(0);
			datos_procedimiento.setCodigo_anestesiologo("");
			datos_procedimiento.setCodigo_ayudante("");
			datos_procedimiento.setConsecutivo_registro("");
			datos_procedimiento.setCirugia_conjunto("");
			datos_procedimiento.setIncruento("");
			datos_procedimiento.setTipo_sala("");
			datos_procedimiento.setValor_real(Double.parseDouble(in.get(14)));
			datos_procedimiento.setDescuento(0);
			datos_procedimiento.setIncremento(0);
			datos_procedimiento.setCodigo_orden(null);
			datos_procedimiento.setRealizado_en("");
			datos_procedimiento.setCodigo_cups(datos_procedimiento
					.getCodigo_procedimiento());

			datos_procedimientoDao.crear(datos_procedimiento);

			Procedimientos procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(datos_procedimiento
					.getCodigo_procedimiento()));
			procedimientos = procedimientosService.consultar(procedimientos);

			Detalle_factura detalle = new Detalle_factura();
			detalle.setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
			detalle.setCodigo_sucursal(datos_procedimiento.getCodigo_sucursal());
			detalle.setId_factura(facturacion.getId_factura());

			detalle.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			detalle.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			detalle.setCreacion_user(usuario.getCodigo().toString());
			detalle.setUltimo_user(usuario.getCodigo().toString());

			detalle.setFacturable(true);
			detalle.setFactura_concepto(datos_procedimiento
					.getCodigo_registro() + "");
			detalle.setValor_real(datos_procedimiento.getValor_real());
			detalle.setCodigo_articulo(datos_procedimiento
					.getCodigo_procedimiento());
			detalle.setDetalle(procedimientos != null ? procedimientos
					.getDescripcion() : "");
			detalle.setCantidad(1);
			detalle.setValor_unitario(datos_procedimiento
					.getValor_procedimiento());
			detalle.setValor_total(datos_procedimiento.getValor_neto());
			detalle.setTipo("PROCEDIMIENTO");

			detalle.setC_costos("");
			detalle.setPlan("");

			detalleFacturaDao.crear(detalle);

			guardarDiagnosticoHaMorbilidad(
					datos_procedimiento.getCodigo_diagnostico_principal(),
					usuario, nro_identificacion,
					facturacion.getCodigo_administradora());
		}
	}

	/* RIPS DE MEDICAMENTO */
	/*
	 * @revisar {Este metodo hay que probarlo con la nueva estructura de
	 * contratos}
	 */
	private void guardarMedicamentosGlosa(List<String> in,
			Map<String, Object> map_facturas,
			Map<String, Object> map_consecutivos_facturas, Usuarios usuario,
			int i_am, Contratos contratos) {
		String codigo_documento = "" + in.get(0);
		Facturacion facturacion = (Facturacion) map_facturas
				.get(codigo_documento);
		if (facturacion == null) {
			throw new ValidacionRunTimeException(
					"La factura numero ?3 no se encuentra".replace("?3",
							codigo_documento));
		}

		String nro_identificacion = "" + in.get(3);
		// String id_plan = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", usuario.getCodigo_empresa());
		map.put("codigo_sucursal", usuario.getCodigo_sucursal());
		map.put("nro_identificacion", nro_identificacion);
		if (!pacienteDao.existe(map)) {
			Paciente pacienteNoExiste = ServiciosDisponiblesUtils
					.getPacienteNoExistente(usuario.getCodigo_empresa(),
							usuario.getCodigo_sucursal());
			nro_identificacion = pacienteNoExiste.getNro_identificacion();
		}

		// verficamos que ya se realice una factura de medicamentos
		Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
		facturacion_medicamento.setCodigo_empresa(usuario.getCodigo_empresa());
		facturacion_medicamento
				.setCodigo_sucursal(usuario.getCodigo_sucursal());
		// facturacion_medicamento.setNro_factura(tbxNro_factura.getValue());
		facturacion_medicamento.setTipo_identificacion("" + in.get(2));
		facturacion_medicamento.setNro_identificacion(nro_identificacion);
		facturacion_medicamento.setCodigo_administradora(contratos
				.getCodigo_administradora());
		facturacion_medicamento.setId_plan(contratos.getId_plan());
		facturacion_medicamento.setNro_ingreso("EXT");
		facturacion_medicamento.setFecha_medicamento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		facturacion_medicamento.setNumero_autorizacion("" + in.get(4));
		facturacion_medicamento.setObservacion("MEDICAMENTO PRESTADOR EXTERNO");
		facturacion_medicamento.setTipo("01"); // 01 tipo medicamento
		facturacion_medicamento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_medicamento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_medicamento
				.setCreacion_user(usuario.getCodigo().toString());
		facturacion_medicamento.setUltimo_user(usuario.getCodigo().toString());
		facturacion_medicamento.setCodigo_solicitud("");
		facturacion_medicamento.setCodigo_receta("");
		facturacion_medicamento.setC_costo("");

		facturacion_medicamentoDao.crear(facturacion_medicamento);

		double valor_unitario = 0;
		double valor_total = 0;
		int unidades = 0;

		String valor_unitario_r = in.get(12);
		String valor_total_r = in.get(13);
		String unidades_r = in.get(11);

		if (!valor_unitario_r.trim().isEmpty()
				&& valor_unitario_r.matches("[0-9.,]*$")) {
			valor_unitario = Double.parseDouble(valor_unitario_r);
		}

		if (!valor_total_r.trim().isEmpty()
				&& valor_total_r.matches("[0-9.,]*$")) {
			valor_total = Double.parseDouble(valor_total_r);
		}

		if (!unidades_r.trim().isEmpty() && unidades_r.matches("[0-9]*$")) {
			unidades = Integer.parseInt(unidades_r);
		}

		// crear los detalle de la factura de medicamentos
		Datos_medicamentos datos_medicamentos = new Datos_medicamentos();
		datos_medicamentos.setCodigo_empresa(usuario.getCodigo_empresa());
		datos_medicamentos.setCodigo_sucursal(usuario.getCodigo_sucursal());
		datos_medicamentos.setNro_factura(facturacion_medicamento
				.getNro_factura());
		datos_medicamentos.setConsecutivo(1 + "");
		datos_medicamentos.setCodigo_bodega("");
		datos_medicamentos.setCodigo_medicamento("" + in.get(5));
		datos_medicamentos.setCodigo_lote("");
		datos_medicamentos.setUnidades(unidades);
		datos_medicamentos.setValor_unitario(valor_unitario);
		datos_medicamentos.setValor_total(valor_total);
		datos_medicamentos.setCancelo_copago("N");
		datos_medicamentos.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_medicamentos.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_medicamentos.setCreacion_user(usuario.getCodigo().toString());
		datos_medicamentos.setUltimo_user(usuario.getCodigo().toString());
		datos_medicamentos.setValor_real(valor_unitario);
		datos_medicamentos.setDescuento(0);
		datos_medicamentos.setIncremento(0);
		datos_medicamentos.setCantidad_entregada(unidades);
		datos_medicamentos.setReferencia_pyp("");

		datos_medicamentos.setTipo_medicamento("" + in.get(6));
		datos_medicamentos.setNombre_generico("" + in.get(7));
		datos_medicamentos.setForma_farmaceutica("" + in.get(8));
		datos_medicamentos.setConcentracion_medicamento("" + in.get(9));
		datos_medicamentos.setUnidad_medida("" + in.get(10));

		datos_medicamentosDao.crear(datos_medicamentos);

		Detalle_factura detalle = new Detalle_factura();
		detalle.setCodigo_empresa(datos_medicamentos.getCodigo_empresa());
		detalle.setCodigo_sucursal(datos_medicamentos.getCodigo_sucursal());
		detalle.setId_factura(facturacion.getId_factura());

		detalle.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		detalle.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		detalle.setCreacion_user(usuario.getCodigo().toString());
		detalle.setUltimo_user(usuario.getCodigo().toString());

		detalle.setFacturable(true);
		detalle.setFactura_concepto(datos_medicamentos.getNro_factura());
		detalle.setValor_real(datos_medicamentos.getValor_real());
		detalle.setCodigo_articulo(datos_medicamentos.getCodigo_medicamento());
		detalle.setDetalle("" + in.get(7));
		detalle.setCantidad(1);
		detalle.setValor_unitario(datos_medicamentos.getValor_unitario());
		detalle.setValor_total(datos_medicamentos.getValor_total());
		detalle.setTipo("MEDICAMENTO");

		detalle.setC_costos("");
		detalle.setPlan("");

		detalleFacturaDao.crear(detalle);
	}

	/*
	 * @revisar {Este metodo hay que probarlo con la nueva estructura de
	 * contratos}
	 */
	private void guardarOtrosServiciosGlosa(List<String> in,
			Map<String, Object> map_facturas,
			Map<String, Object> map_consecutivos_facturas, Usuarios usuario,
			int i_at, Contratos contratos) {
		String codigo_documento = "" + in.get(0);
		Facturacion facturacion = (Facturacion) map_facturas
				.get(codigo_documento);
		if (facturacion == null) {
			throw new ValidacionRunTimeException(
					"La factura numero ?3 no se encuentra".replace("?3",
							codigo_documento));
		}

		String nro_identificacion = "" + in.get(3);
		// String id_plan = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", usuario.getCodigo_empresa());
		map.put("codigo_sucursal", usuario.getCodigo_sucursal());
		map.put("nro_identificacion", nro_identificacion);
		if (!pacienteDao.existe(map)) {
			Paciente pacienteNoExiste = ServiciosDisponiblesUtils
					.getPacienteNoExistente(usuario.getCodigo_empresa(),
							usuario.getCodigo_sucursal());
			nro_identificacion = pacienteNoExiste.getNro_identificacion();
		}

		Facturacion_servicio facturacion_servicio = new Facturacion_servicio();
		facturacion_servicio.setCodigo_empresa(usuario.getCodigo_empresa());
		facturacion_servicio.setCodigo_sucursal(usuario.getCodigo_sucursal());
		// facturacion_servicio.setNro_factura(tbxNro_factura.getValue());
		facturacion_servicio.setTipo_identificacion("" + in.get(2));
		facturacion_servicio.setNro_identificacion(nro_identificacion);
		facturacion_servicio.setCodigo_administradora(contratos
				.getCodigo_administradora());
		facturacion_servicio.setId_plan(contratos.getId_plan());
		facturacion_servicio.setNro_ingreso("EXT");
		facturacion_servicio.setFecha_servicio(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		facturacion_servicio.setNumero_autorizacion("" + in.get(4));
		facturacion_servicio.setObservacion("SERVICIO POR PRESTADOR EXTERNO");
		facturacion_servicio.setTipo("" + in.get(5));
		facturacion_servicio.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_servicio.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_servicio.setCreacion_user(usuario.getCodigo().toString());
		facturacion_servicio.setUltimo_user(usuario.getCodigo().toString());

		facturacion_servicioDao.crear(facturacion_servicio);

		Integer unidades = 0;
		double valor_unitario = 0;
		double valor_total = 0;

		String valor_unitario_r = in.get(8);
		String valor_total_r = in.get(9);
		String unidades_r = in.get(10);

		if (!valor_unitario_r.trim().isEmpty()
				&& valor_unitario_r.matches("[0-9.,]*$")) {
			valor_unitario = Double.parseDouble(valor_unitario_r);
		}

		if (!valor_total_r.trim().isEmpty()
				&& valor_total_r.matches("[0-9.,]*$")) {
			valor_total = Double.parseDouble(valor_total_r);
		}

		if (!unidades_r.trim().isEmpty() && unidades_r.matches("[0-9]*$")) {
			unidades = Integer.parseInt(unidades_r);
		}

		Datos_servicio datos_servicio = new Datos_servicio();
		datos_servicio.setCodigo_empresa(usuario.getCodigo_empresa());
		datos_servicio.setCodigo_sucursal(usuario.getCodigo_sucursal());
		datos_servicio.setNro_factura(facturacion_servicio.getNro_factura());
		datos_servicio.setConsecutivo(1 + "");
		datos_servicio.setCodigo_servicio("" + in.get(6));
		datos_servicio.setUnidades(unidades);
		datos_servicio.setValor_unitario(valor_unitario);
		datos_servicio.setValor_total(valor_total);
		datos_servicio.setCancelo_copago("N");
		datos_servicio.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		datos_servicio.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		datos_servicio.setCreacion_user(usuario.getCodigo().toString());
		datos_servicio.setUltimo_user(usuario.getCodigo().toString());
		datos_servicio.setValor_real(valor_unitario);
		datos_servicio.setDescuento(0);
		datos_servicio.setIncremento(0);
		datos_servicio.setReferencia_pyp("");
		datos_servicio.setTipo_servicio("" + in.get(5));
		datos_servicioDao.crear(datos_servicio);

		Detalle_factura detalle = new Detalle_factura();
		detalle.setCodigo_empresa(datos_servicio.getCodigo_empresa());
		detalle.setCodigo_sucursal(datos_servicio.getCodigo_sucursal());
		detalle.setId_factura(facturacion.getId_factura());

		detalle.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		detalle.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		detalle.setCreacion_user(usuario.getCodigo().toString());
		detalle.setUltimo_user(usuario.getCodigo().toString());

		detalle.setFacturable(true);
		detalle.setFactura_concepto(datos_servicio.getNro_factura());
		detalle.setValor_real(datos_servicio.getValor_real());
		detalle.setCodigo_articulo(datos_servicio.getCodigo_servicio());
		detalle.setDetalle("" + in.get(7));
		detalle.setCantidad(datos_servicio.getUnidades());
		detalle.setValor_unitario(datos_servicio.getValor_unitario());
		detalle.setValor_total(datos_servicio.getValor_total());
		detalle.setTipo("SERVICIO");

		detalle.setC_costos("");
		detalle.setPlan("");

		detalleFacturaDao.crear(detalle);
	}

	@SuppressWarnings("unchecked")
	private Object[] guardarDatosServicios(Map<String, Object> datos,
			Map<String, Object> datos_facturacion) {
		log.info("ejecutando metodo @guardarDatosServicios() ");
		try {
			Paquetes_servicios paquetes_servicios = (Paquetes_servicios) datos_facturacion
					.get("paquetes_servicios");
			Boolean recargar_informacion = false;
			List<Detalle_factura> lista_detalle = (List<Detalle_factura>) datos_facturacion
					.get("lista_detalle");
			boolean editar_ordenamiento_prestador = (Boolean) datos
					.get("editar_ordenamiento_prestador");
			Bodega bodega = (Bodega) datos.get("bodega_seleccionada");
			Configuracion_admision_ingreso configuracion_admision_ingreso = (Configuracion_admision_ingreso) datos
					.get("configuracion_admision_ingreso");

			Usuarios usuarios = (Usuarios) datos.get("user");
			Map<String, Object> map_admision = (Map<String, Object>) datos
					.get(ITiposServicio.PARAM_ADMISION);
			Admision admision = (Admision) map_admision.get("admision");
			Parametros_empresa parametros_empresa = (Parametros_empresa) map_admision
					.get("parametros_empresa");
			if ((Boolean) map_admision.get("nueva")) {
				Paciente paciente_seleccionado = (Paciente) datos
						.get("PACIENTE_SELECCIONADO");

				admision = admisionService.guardar(map_admision);
				admision.setPaciente(paciente_seleccionado);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("admision", admision);
				map.put("parametros_empresa", parametros_empresa);
				admisionService.actualizar(map);
			}

			Map<String, Object> map_servicios = (Map<String, Object>) datos
					.get("lista_detalle");

			Map<String, Object> map_facturacion_servicio = (Map<String, Object>) map_servicios
					.get(ServiciosFacturacionMacro.MAP_FACT_SERVICIOS);

			// Eliminamos servicios
			List<Detalle_factura> detalle_facturas = (List<Detalle_factura>) map_servicios
					.get(ServiciosFacturacionMacro.MAP_SERVICIOS_ELIMINAR);
			for (Detalle_factura detalle_factura : detalle_facturas) {
				eliminarServicioRips(detalle_factura, usuarios,
						editar_ordenamiento_prestador);
			}

			crearFacturaServicios(map_facturacion_servicio, admision);

			List<Map<String, Object>> listado_servicios = (List<Map<String, Object>>) map_servicios
					.get(ServiciosFacturacionMacro.MAP_SERVICIOS);

			Map<String, Object> mapa_facturaciones_medicamentos = new HashMap<String, Object>();
			Paciente paciente = admision.getPaciente();
			if (paciente == null) {
				paciente = new Paciente();
				paciente.setCodigo_empresa(admision.getCodigo_empresa());
				paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
				paciente.setNro_identificacion(admision.getNro_identificacion());
				paciente = pacienteDao.consultar(paciente);
				admision.setPaciente(paciente);
			}
			int totalidad_paquetes = 0;
			Detalle_factura detalle_factura_paquete = null;
			for (Map<String, Object> mapa_servicios : listado_servicios) {
				Object object = mapa_servicios.get(ITiposServicio.PARAM_RIPS);
				Detalle_factura detalle_factura = (Detalle_factura) mapa_servicios
						.get(ServiciosFacturacionMacro.DETALLE_FACTURA);
				detalle_factura.setExcluir_guardado(false);
				Boolean detalle_nuevo = (Boolean) mapa_servicios
						.get(ServiciosFacturacionMacro.DETALLE_NUEVO);
				// realizamos registros de servicios
				if (bodega != null) {
					detalle_factura.setCodigo_bodega(bodega.getCodigo());
				}

				if (object != null) {
					Datos_procedimiento datos_procedimiento_paquete = null;
					if (paquetes_servicios != null) {
						if (object instanceof Datos_consulta) {
							datos_procedimiento_paquete = verificarFacturacionPaquete(
									((Datos_consulta) object)
											.getCodigo_consulta(),
									ITiposServicio.CONSULTA, admision,
									paciente, paquetes_servicios);
						} else if (object instanceof Datos_procedimiento) {
							datos_procedimiento_paquete = verificarFacturacionPaquete(
									((Datos_procedimiento) object)
											.getCodigo_procedimiento(),
									ITiposServicio.PROCEDIMIENTO, admision,
									paciente, paquetes_servicios);
						} else if (object instanceof Datos_medicamentos) {
							datos_procedimiento_paquete = verificarFacturacionPaquete(
									((Datos_medicamentos) object)
											.getCodigo_medicamento(),
									ITiposServicio.MEDICAMENTO, admision,
									paciente, paquetes_servicios);
						} else if (object instanceof Datos_servicio) {
							datos_procedimiento_paquete = verificarFacturacionPaquete(
									((Datos_servicio) object)
											.getCodigo_servicio(),
									ITiposServicio.SERVICIO, admision,
									paciente, paquetes_servicios);
						}

						if (datos_procedimiento_paquete != null) {
							if (object instanceof Datos_consulta) {
								Datos_consulta datos_consulta = (Datos_consulta) object;
								datos_consulta.setValor_consulta(0.0);
								datos_consulta.setValor_neto(0.0);
								datos_consulta.setValor_real(0.0);
							} else if (object instanceof Datos_procedimiento) {
								Datos_procedimiento datos_procedimiento = (Datos_procedimiento) object;
								datos_procedimiento.setValor_neto(0.0);
								datos_procedimiento.setValor_real(0.0);
								datos_procedimiento.setValor_procedimiento(0.0);
							} else if (object instanceof Datos_medicamentos) {
								Datos_medicamentos datos_medicamentos = (Datos_medicamentos) object;
								datos_medicamentos.setValor_real(0.0);
								datos_medicamentos.setValor_total(0.0);
								datos_medicamentos.setValor_unitario(0.0);
							} else if (object instanceof Datos_servicio) {
								Datos_servicio datos_servicio = (Datos_servicio) object;
								datos_servicio.setValor_real(0.0);
								datos_servicio.setValor_total(0.0);
								datos_servicio.setValor_unitario(0.0);
							}
							detalle_factura.setValor_total(0.0);
							detalle_factura.setValor_real(0.0);
							totalidad_paquetes++;
							if (detalle_factura_paquete == null) {
								detalle_factura_paquete = new Detalle_factura(
										true);
								detalle_factura_paquete
										.setUltimo_update(new Timestamp(
												new Date().getTime()));
								detalle_factura_paquete.setUltimo_user(admision
										.getUltimo_user());
								detalle_factura_paquete
										.setCreacion_date(new Timestamp(
												new Date().getTime()));
								detalle_factura_paquete
										.setCreacion_user(admision
												.getCreacion_user());
								detalle_factura_paquete
										.setCodigo_empresa(datos_procedimiento_paquete
												.getCodigo_empresa());
								detalle_factura_paquete
										.setCodigo_sucursal(datos_procedimiento_paquete
												.getCodigo_sucursal());
								detalle_factura_paquete
										.setFactura_concepto(datos_procedimiento_paquete
												.getCodigo_registro() + "");
								detalle_factura_paquete
										.setCodigo_articulo(datos_procedimiento_paquete
												.getCodigo_procedimiento());
								detalle_factura_paquete.setCantidad(1);
								detalle_factura_paquete
										.setValor_unitario(datos_procedimiento_paquete
												.getValor_procedimiento());
								detalle_factura_paquete
										.setValor_real(datos_procedimiento_paquete
												.getValor_procedimiento());

								detalle_factura_paquete
										.setValor_total(datos_procedimiento_paquete
												.getValor_procedimiento());
								detalle_factura_paquete
										.setTipo("PROCEDIMIENTO");
								detalle_factura_paquete
										.setDetalle(paquetes_servicios
												.getNombre_paquete()
												+ " ( "
												+ datos_procedimiento_paquete
														.getCodigo_cups() + ")");
								detalle_factura_paquete.setFacturable(true);
							}

							if (object instanceof Datos_consulta) {
								Datos_consulta datos_consulta = (Datos_consulta) object;
								if (datos_procedimiento_paquete
										.getCodigo_procedimiento().equals(
												datos_consulta
														.getCodigo_consulta())) {
									detalle_factura.setExcluir_guardado(true);
								}
							} else if (object instanceof Datos_procedimiento) {
								Datos_procedimiento datos_procedimiento = (Datos_procedimiento) object;
								if (datos_procedimiento_paquete
										.getCodigo_procedimiento()
										.equals(datos_procedimiento
												.getCodigo_procedimiento())) {
									detalle_factura.setExcluir_guardado(true);
								}
							}

						}
					}
					if (!detalle_factura.getExcluir_guardado()) {
						guardarDatosServicio(object, admision, detalle_factura,
								mapa_servicios, configuracion_admision_ingreso);
					}

				}

				if (!detalle_nuevo && (object instanceof Datos_medicamentos)) {
					Datos_medicamentos datos_medicamentos = (Datos_medicamentos) object;
					if (detalle_factura.getCodigo_bodega() != null
							&& !detalle_factura.getCodigo_bodega().isEmpty()) {
						datos_medicamentos.setCodigo_bodega(detalle_factura
								.getCodigo_bodega());
					}
					String nro_autorizacion = (String) mapa_servicios
							.get(ServiciosFacturacionMacro.NRO_AUTORIZACION);
					Date fecha_medicamento = (Date) mapa_servicios
							.get(ServiciosFacturacionMacro.FECHA_REALIZACION);
					if (mapa_facturaciones_medicamentos
							.containsKey(datos_medicamentos.getNro_factura())) {
						Map<String, Object> map_datos = (Map<String, Object>) mapa_facturaciones_medicamentos
								.get(datos_medicamentos.getNro_factura());
						List<Datos_medicamentos> listado_datos_medicamentos = (List<Datos_medicamentos>) map_datos
								.get(ServiciosFacturacionMacro.DETALLE_FACTURA);
						listado_datos_medicamentos.add(datos_medicamentos);
						map_datos.put(
								ServiciosFacturacionMacro.NRO_AUTORIZACION,
								nro_autorizacion);
						map_datos.put(
								ServiciosFacturacionMacro.FECHA_REALIZACION,
								fecha_medicamento);
					} else {
						Map<String, Object> map_datos = new HashMap<String, Object>();
						// Cargamos numero de autorizacion
						map_datos.put(
								ServiciosFacturacionMacro.NRO_AUTORIZACION,
								nro_autorizacion);
						map_datos.put(
								ServiciosFacturacionMacro.FECHA_REALIZACION,
								fecha_medicamento);

						// Cargamos listado de medicamentos
						List<Datos_medicamentos> listado_datos_medicamentos = new ArrayList<Datos_medicamentos>();
						listado_datos_medicamentos.add(datos_medicamentos);
						map_datos.put(
								ServiciosFacturacionMacro.DETALLE_FACTURA,
								listado_datos_medicamentos);

						// Cargamos mapa completo
						mapa_facturaciones_medicamentos.put(
								datos_medicamentos.getNro_factura(), map_datos);
					}
				}
			}

			if (totalidad_paquetes > 0) {
				lista_detalle.add(detalle_factura_paquete);
				recargar_informacion = true;
			}

			if (!mapa_facturaciones_medicamentos.isEmpty()) {
				guardarFacturaMedicamentoExistente(
						mapa_facturaciones_medicamentos, admision);
			}

			return new Object[] { admision, recargar_informacion };
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private void crearFacturaServicios(
			Map<String, Object> map_facturacion_servicio, Admision admision) {
		if (map_facturacion_servicio != null) {
			Facturacion_servicio facturacion_medicamento = (Facturacion_servicio) map_facturacion_servicio
					.get("facturacion_servicio");
			facturacion_medicamento.setNro_ingreso(admision.getNro_ingreso());
			facturacion_servicioService
					.guardar(map_facturacion_servicio, false);
		}
	}

	private void guardarFacturaMedicamentoNueva(
			Map<String, Object> map_facturacion_medicamento, Admision admision,
			Detalle_factura detalle_factura, Map<String, Object> map_detalle) {
		if (map_facturacion_medicamento != null) {
			String tipo = (detalle_factura.getTipo().equals(
					ITiposServicio.MEDICAMENTO) ? "01" : "02");
			String observaciones = (detalle_factura.getTipo().equals(
					ITiposServicio.MEDICAMENTO) ? "FACTURACION DE MEDICAMENTOS"
					: "FACTURACION DE MATERIALES/INSUMOS");

			Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
			facturacion_medicamento.setNumero_autorizacion(admision
					.getNro_autorizacion());
			crearFacturacionDefecto(tipo, observaciones,
					facturacion_medicamento, admision, map_detalle);
			facturacion_medicamento.setNro_ingreso(admision.getNro_ingreso());
			map_facturacion_medicamento.put("facturacion_medicamento",
					facturacion_medicamento);
			facturacion_medicamentoService.guardarFacturacion(
					map_facturacion_medicamento, false);

			// Actualizamos el concepto
			detalle_factura.setFactura_concepto(facturacion_medicamento
					.getNro_factura());
		}
	}

	@SuppressWarnings("unchecked")
	private void guardarFacturaMedicamentoExistente(
			Map<String, Object> mapa_facturaciones_medicamentos,
			Admision admision) {
		for (String key_map : mapa_facturaciones_medicamentos.keySet()) {
			boolean cont = false;
			boolean afectar_kardex_fact = true;
			contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
			res.setCodigo_empresa(admision.getCodigo_empresa());
			res = (contaweb.modelo.bean.Resolucion) resolucionContDao
					.consultar(res);
			if (res != null) {
				if (res.getContabiliza_aut()) {
					cont = true;
				}
			}

			healthmanager.modelo.bean.Resolucion res2 = new healthmanager.modelo.bean.Resolucion();
			res2.setCodigo_empresa(admision.getCodigo_empresa());
			res2 = (healthmanager.modelo.bean.Resolucion) resolucionDao
					.consultar(res2);
			if (res2 != null) {
				afectar_kardex_fact = res2.getAfectar_kardex_fact();
			}

			Map<String, Object> datos_guardar = new HashMap<String, Object>();
			Map<String, Object> map_datos = (Map<String, Object>) mapa_facturaciones_medicamentos
					.get(key_map);
			List<Datos_medicamentos> listado_datos_medicamentos = (List<Datos_medicamentos>) map_datos
					.get(ServiciosFacturacionMacro.DETALLE_FACTURA);
			String nro_autorizacion = (String) map_datos
					.get(ServiciosFacturacionMacro.NRO_AUTORIZACION);
			Date fecha = (Date) map_datos
					.get(ServiciosFacturacionMacro.FECHA_REALIZACION);

			Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
			facturacion_medicamento.setNumero_autorizacion(admision
					.getNro_autorizacion());
			facturacion_medicamento.setCodigo_empresa(admision
					.getCodigo_empresa());
			facturacion_medicamento.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			facturacion_medicamento.setNro_factura(key_map);

			facturacion_medicamento = facturacion_medicamentoDao
					.consultar(facturacion_medicamento);
			facturacion_medicamento.setNro_ingreso(admision.getNro_ingreso());
			facturacion_medicamento
					.setNumero_autorizacion(nro_autorizacion != null ? nro_autorizacion
							: admision.getNro_autorizacion());

			if (fecha != null) {
				facturacion_medicamento.setFecha_medicamento(new Timestamp(
						fecha.getTime()));
			}

			datos_guardar.put("facturacion_medicamento",
					facturacion_medicamento);
			datos_guardar.put("lista_medicamentos", listado_datos_medicamentos);
			datos_guardar.put("cont", cont);
			datos_guardar.put("afectar_kardex_fact", afectar_kardex_fact);
			datos_guardar.put("accion", "modificar");

			facturacion_medicamentoService.guardarFacturacion(datos_guardar,
					false);

		}
	}

	private void crearFacturacionDefecto(String tipo, String observaciones,
			Facturacion_medicamento facturacion_medicamento, Admision admision,
			Map<String, Object> map_detalle) {
		String nro_autorizacion = (String) map_detalle
				.get(ServiciosFacturacionMacro.NRO_AUTORIZACION);
		Date fecha = (Date) map_detalle
				.get(ServiciosFacturacionMacro.FECHA_REALIZACION);

		facturacion_medicamento.setCodigo_empresa(admision.getCodigo_empresa());
		facturacion_medicamento.setCodigo_sucursal(admision
				.getCodigo_sucursal());
		facturacion_medicamento.setNro_factura("");
		facturacion_medicamento.setTipo_identificacion(admision.getPaciente()
				.getTipo_identificacion());
		facturacion_medicamento.setNro_identificacion(admision
				.getNro_identificacion());
		facturacion_medicamento.setCodigo_administradora(admision
				.getCodigo_administradora());
		facturacion_medicamento.setId_plan(admision.getId_plan());
		facturacion_medicamento.setNro_ingreso("");
		facturacion_medicamento
				.setFecha_medicamento(fecha != null ? new Timestamp(fecha
						.getTime()) : new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
		facturacion_medicamento
				.setNumero_autorizacion(nro_autorizacion != null ? nro_autorizacion
						: admision.getNro_autorizacion());
		facturacion_medicamento.setObservacion("" + observaciones);
		facturacion_medicamento.setTipo(tipo); // 01 tipo medicamento
		facturacion_medicamento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_medicamento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_medicamento.setCreacion_user(admision.getCreacion_user());
		facturacion_medicamento.setUltimo_user(admision.getUltimo_user());
		facturacion_medicamento.setCodigo_solicitud("");
		facturacion_medicamento.setCodigo_receta("");
		facturacion_medicamento.setC_costo("");
	}

	private Map<String, Object> getFacturacionMedicamentosMateriales(
			Datos_medicamentos datos_medicamentos) {
		boolean cont = false;
		boolean afectar_kardex_fact = true;
		contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
		res.setCodigo_empresa(datos_medicamentos.getCodigo_empresa());
		res = (contaweb.modelo.bean.Resolucion) resolucionContDao
				.consultar(res);
		if (res != null) {
			if (res.getContabiliza_aut()) {
				cont = true;
			}
		}

		healthmanager.modelo.bean.Resolucion res2 = new healthmanager.modelo.bean.Resolucion();
		res2.setCodigo_empresa(datos_medicamentos.getCodigo_empresa());
		res2 = (healthmanager.modelo.bean.Resolucion) resolucionDao
				.consultar(res2);
		if (res2 != null) {
			afectar_kardex_fact = res2.getAfectar_kardex_fact();
		}

		Map<String, Object> datos_guardar = new HashMap<String, Object>();
		List<Datos_medicamentos> listado_datos_medicamentos = new ArrayList<Datos_medicamentos>();
		listado_datos_medicamentos.add(datos_medicamentos);

		datos_guardar.put("lista_medicamentos", listado_datos_medicamentos);
		datos_guardar.put("cont", cont);
		datos_guardar.put("afectar_kardex_fact", afectar_kardex_fact);
		datos_guardar.put("accion", "registrar");

		return datos_guardar;
	}

	public void guardarDatosServicio(Object servicio, Admision admision,
			Detalle_factura detalle_factura, Map<String, Object> map_detalle,
			Configuracion_admision_ingreso configuracion_admision_ingreso) {
		Boolean detalle_nuevo = (Boolean) map_detalle
				.get(ServiciosFacturacionMacro.DETALLE_NUEVO);

		if (servicio instanceof Datos_consulta) {
			Datos_consulta datos_consulta = (Datos_consulta) servicio;
			datos_consulta.setNumero_autorizacion(admision
					.getNro_autorizacion());
			if (datos_consulta.getCodigo_registro() != null
					&& datos_consultaDao.existe(datos_consulta)) {
				datos_consultaService.actualizarRegistro(datos_consulta);
			} else {
				datos_consulta.setNro_ingreso(admision.getNro_ingreso());
				datos_consultaService.crearRegistro(datos_consulta);
			}
			detalle_factura.setFactura_concepto(datos_consulta
					.getCodigo_registro() + "");
		} else if (servicio instanceof Datos_procedimiento) {
			Datos_procedimiento datos_procedimiento = (Datos_procedimiento) servicio;
			datos_procedimiento
					.setAmbito_procedimiento(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
							.getAmbito_realizacion() : datos_procedimiento
							.getAmbito_procedimiento());
			datos_procedimiento.setNumero_autorizacion(admision
					.getNro_autorizacion());
			if (detalle_factura.getTipo().equals(
					ITiposServicio.PROCEDIMIENTO_MULT)) {
				if (datos_procedimiento.getNro_ingreso() == null
						|| datos_procedimiento.getNro_ingreso().trim()
								.isEmpty()) {
					datos_procedimiento.setNro_ingreso(admision
							.getNro_ingreso());
				}
				// Guardamos de forma quirurgica
				List<Datos_procedimiento> listaDatos_procedimientos = new ArrayList<Datos_procedimiento>();
				listaDatos_procedimientos.add(datos_procedimiento);
				datos_procedimientoService.crearCirugia(
						listaDatos_procedimientos,
						datos_procedimiento.getCodigo_empresa(),
						datos_procedimiento.getCodigo_sucursal(),
						datos_procedimiento.getCodigo_registro());
				detalle_factura.setFactura_concepto(datos_procedimiento
						.getCodigo_registro() + "");
			} else {
				if (datos_procedimiento.getCodigo_registro() != null
						&& datos_procedimientoDao.existe(datos_procedimiento)) {
					datos_procedimientoService
							.actualizarRegistro(datos_procedimiento);
					detalle_factura.setFactura_concepto(datos_procedimiento
							.getCodigo_registro() + "");
				} else {
					if (datos_procedimiento.getFecha_procedimiento() == null) {
						datos_procedimiento
								.setFecha_procedimiento(new Timestamp(
										new Date().getTime()));
					}

					datos_procedimiento.setCodigo_empresa(admision
							.getCodigo_empresa());
					datos_procedimiento.setCodigo_sucursal(admision
							.getCodigo_sucursal());
					datos_procedimiento.setNro_identificacion(admision
							.getNro_identificacion());
					datos_procedimiento.setNro_ingreso(admision
							.getNro_ingreso());
					datos_procedimiento.setTipo_identificacion(admision
							.getPaciente().getTipo_identificacion());
					datos_procedimiento.setCodigo_administradora(admision
							.getCodigo_administradora());
					datos_procedimiento.setId_plan(admision.getId_plan());
					datos_procedimiento.setCreacion_date(new Timestamp(
							new Date().getTime()));
					datos_procedimiento.setUltimo_update(datos_procedimiento
							.getCreacion_date());
					datos_procedimiento.setNumero_autorizacion(admision
							.getNro_autorizacion());
					datos_procedimientoService.crear(datos_procedimiento);

					detalle_factura.setFactura_concepto(datos_procedimiento
							.getCodigo_registro() + "");

					// registramos vacuna
					if (map_detalle
							.containsKey(ITiposServicio.APLICA_ESQUEMA_VACUNACION)) {
						Esquema_vacunacion esquema_vacunacion = (Esquema_vacunacion) map_detalle
								.get(ITiposServicio.PARAM_ESQUEMA_VACUNACION);
						if (esquema_vacunacion != null) {
							guardarVacuna(esquema_vacunacion,
									admision.getPaciente(), admision,
									datos_procedimiento);
						}
					}
				}
			}
		} else if (servicio instanceof Admision_cama) {
			Admision_cama admision_cama = (Admision_cama) servicio;
			if (admision_camaDao.consultar(admision_cama) != null) {
				admision_camaDao.actualizar(admision_cama);
			} else {
				admision_cama.setNro_ingreso(admision.getNro_ingreso());
				admision_camaDao.crear(admision_cama);
			}
		} else if (servicio instanceof Datos_medicamentos) {
			if (detalle_nuevo) {
				Datos_medicamentos datos_medicamentos = (Datos_medicamentos) servicio;
				if (detalle_factura.getCodigo_bodega() != null
						&& !detalle_factura.getCodigo_bodega().isEmpty()) {
					datos_medicamentos.setCodigo_bodega(detalle_factura
							.getCodigo_bodega());
				}
				Map<String, Object> mapa_facturacion_medicamentos = getFacturacionMedicamentosMateriales(datos_medicamentos);
				guardarFacturaMedicamentoNueva(mapa_facturacion_medicamentos,
						admision, detalle_factura, map_detalle);
			}
		}
	}

	private void guardarVacuna(Esquema_vacunacion esquema_vacunacion,
			Paciente paciente, Admision admision,
			Datos_procedimiento datos_procedimiento) {
		if (paciente != null) {
			int anio = L2HContraintDate.getAnio(paciente.getFecha_nacimiento());
			int mes = L2HContraintDate.getMes(paciente.getFecha_nacimiento());

			Vacunas vacunas = new Vacunas();
			vacunas.setCodigo_empresa(paciente.getCodigo_empresa());
			vacunas.setCodigo_sucursal(paciente.getCodigo_sucursal());
			vacunas.setCodigo_procedimiento(esquema_vacunacion
					.getCodigo_vacuna());
			vacunas = vacunasDao.consultar(vacunas);

			if (vacunas != null) {
				// Agregamos el diagnostico de la vacuna
				// Registramos relacionamos la vacuna con el paciente
				Vacunas_pacientes vacunas_pacientes = new Vacunas_pacientes();
				vacunas_pacientes.setCodigo_empresa(paciente
						.getCodigo_empresa());
				vacunas_pacientes.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				vacunas_pacientes
						.setCodigo_procedimiento_vacuna(esquema_vacunacion
								.getCodigo_vacuna());
				vacunas_pacientes.setNro_identificacion(paciente
						.getNro_identificacion());
				vacunas_pacientes.setId_esquema_vacunacion(esquema_vacunacion
						.getConsecutivo());
				vacunas_pacientes.setDosis(esquema_vacunacion.getDosis());

				vacunas_pacientes.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				vacunas_pacientes.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				vacunas_pacientes.setCreacion_user(datos_procedimiento
						.getCreacion_user());
				vacunas_pacientes.setUltimo_user(datos_procedimiento
						.getUltimo_user());

				vacunas_pacientes.setCodigo_jeringa("");
				vacunas_pacientes.setValor_jeringa(0);
				vacunas_pacientes.setAnio(anio);
				vacunas_pacientes.setMeses(mes + 1);
				vacunas_pacientes
						.setVia_administracion(esquema_vacunacion != null ? esquema_vacunacion
								.getVia_administracion() : "");
				vacunas_pacientes.setDescripcion_edad(vacunas.getDescripcion());
				vacunas_pacientes.setFecha_vacunacion(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				vacunas_pacientes
						.setRespuesta_4505(esquema_vacunacion != null ? esquema_vacunacion
								.getRespuesta_4505() + ""
								: "");
				vacunas_pacientes.setEstado(IConstantes.ESTADO_VACUNA_NUEVA);
				vacunas_pacientes.setObservacion_estado("");
				vacunas_pacientes.setNro_ingreso(admision.getNro_ingreso());
				vacunas_pacientesDao.crear(vacunas_pacientes);
			} else {
				throw new ValidacionRunTimeException(
						"Para factuarar la vacuna necesita un registrar esta vacuna en el esquema de vacunacion, por favor comuniquese con el administrador del sistema");
			}
		} else {
			throw new ValidacionRunTimeException(
					"A la hora de registrar la vacuna no se encuantra el paciente");
		}
	}

	public List<Map<String, Object>> listar_facturas(
			Map<String, Object> parametros) {
		try {
			return facturacionDao.listar_facturas(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizarCentro(Admision admision) {
		try {
			return facturacionDao.actualizar_centro(admision);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private Datos_procedimiento verificarFacturacionPaquete(
			String codigo_servicio, String tipo, Admision admision,
			Paciente paciente, Paquetes_servicios paquetes_servicios) {

		boolean continuar = true;
		boolean factura_paquete = false;
		if (paquetes_servicios != null) {
			if (tipo.equals(ITiposServicio.PROCEDIMIENTO)
					|| tipo.equals(ITiposServicio.CONSULTA)
					|| tipo.equals(ITiposServicio.ESTANCIA)) {
				if (paquetes_servicios.getId_procedimiento_principal().equals(
						new Long(codigo_servicio))) {
					factura_paquete = true;
					continuar = false;
				}
			}

			if (continuar) {
				Map<String, Object> parametros_cups = new HashMap<String, Object>();
				parametros_cups.put("id_paquete", paquetes_servicios.getId());
				List<Detalles_paquetes_servicios> listado_detalles = detalles_paquetes_serviciosDao
						.listar(parametros_cups);
				for (Detalles_paquetes_servicios detalles_paquetes_servicios : listado_detalles) {
					if (detalles_paquetes_servicios.getCodigo_detalle().equals(
							codigo_servicio)) {
						factura_paquete = true;
						break;
					}
				}
			}

		}

		if (factura_paquete) {
			return guardarDatos_procedimiento_paquete(paquetes_servicios,
					admision, admision.getPaciente());
		}

		return null;

	}

	private Datos_procedimiento guardarDatos_procedimiento_paquete(
			Paquetes_servicios paquetes_servicios, Admision admision,
			Paciente paciente) {
		log.info("Ejecutando metodo @guardarDatos_procedimiento_paquete()");
		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(paquetes_servicios
				.getId_procedimiento_principal()));
		procedimientos = procedimientosService.consultar(procedimientos);
		// log.info("facturando Procedimiento ordenando ===> "
		// + paquetes_servicios.getId_procedimiento_principal());
		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento.setConsecutivo_registro(null);
		datos_procedimiento.setCodigo_empresa(paquetes_servicios
				.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(paquetes_servicios
				.getCodigo_sucursal());
		datos_procedimiento.setTipo_identificacion(paciente
				.getTipo_identificacion());
		datos_procedimiento.setNro_identificacion(paciente
				.getNro_identificacion());
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_procedimiento(paquetes_servicios
				.getId_procedimiento_principal() + "");
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion(admision
				.getNro_autorizacion());
		datos_procedimiento.setValor_procedimiento(paquetes_servicios
				.getValor());
		datos_procedimiento.setUnidades(1);
		datos_procedimiento.setCopago(0.0);
		datos_procedimiento.setValor_neto(paquetes_servicios.getValor());

		datos_procedimiento
				.setAmbito_procedimiento(IDatosProcedimientos.AMBITO_REALIZACION);
		datos_procedimiento
				.setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
		datos_procedimiento
				.setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
		datos_procedimiento
				.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
		datos_procedimiento.setCodigo_diagnostico_principal(admision
				.getDiagnostico_ingreso());
		datos_procedimiento.setCodigo_diagnostico_relacionado("");
		datos_procedimiento.setComplicacion("");
		datos_procedimiento.setCancelo_copago("N");
		datos_procedimiento.setCodigo_programa("");

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(admision.getCreacion_user());
		datos_procedimiento.setUltimo_user(admision.getCreacion_user());
		datos_procedimiento.setValor_real(paquetes_servicios.getValor());
		datos_procedimiento.setDescuento(0);
		datos_procedimiento.setIncremento(0);
		datos_procedimiento.setRealizado_en("");
		datos_procedimiento
				.setCodigo_cups(procedimientos != null ? procedimientos
						.getCodigo_cups() : "");
		datos_procedimiento.setConsecutivo_registro("1");

		Datos_procedimiento datos_procedimiento2 = datos_procedimientoService
				.consultar_auxiliar(datos_procedimiento);

		if (datos_procedimiento2 != null) {
			datos_procedimiento.setCodigo_registro(datos_procedimiento2
					.getCodigo_registro());
			datos_procedimiento.setConsecutivo_registro(datos_procedimiento2
					.getConsecutivo_registro());
			datos_procedimientoService.actualizarRegistro(datos_procedimiento);
		} else {
			datos_procedimiento.setCodigo_registro(null);

			datos_procedimientoService.crear(datos_procedimiento);
		}

		return datos_procedimiento;
	}

	public int anularFacturaAgrupadaCapitada(Map<String, Object> parametros) {
		try {
			Nota_contable nota_factura = new Nota_contable();
			nota_factura.setCodigo_empresa((String) parametros
					.get("codigo_empresa"));
			nota_factura.setCodigo_sucursal((String) parametros
					.get("codigo_sucursal"));
			nota_factura.setCodigo_comprobante((String) parametros
					.get("codigo_comprobante"));
			nota_factura.setCodigo_documento(parametros.get("id_factura")
					.toString());
			nota_factura = nota_contableDao.consultar(nota_factura);
			if (nota_factura != null) {
				nota_factura.setDelete_user((String) parametros
						.get("delete_user"));
				nota_factura.setDelete_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				nota_contableDao.actualizar(nota_factura);
			}
			return facturacionDao.anularFacturaAgrupadaCapitada(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Double totalizarSumasDescuentos(Map<String, Object> parametros) {
		try {
			Double total = facturacionDao.totalizarSumasDescuentos(parametros);
			return total != null ? total : 0D;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void anularFactura(Facturacion facturacion) {
		try {
			actualizar(facturacion);
			Nota_contable nota_factura = new Nota_contable();
			nota_factura.setCodigo_empresa(facturacion.getCodigo_empresa());
			nota_factura.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			nota_factura.setCodigo_comprobante(facturacion
					.getCodigo_comprobante());
			nota_factura.setCodigo_documento(facturacion.getId_factura() + "");
			nota_factura = nota_contableDao.consultar(nota_factura);
			if (nota_factura != null) {
				nota_factura.setDelete_user(facturacion.getDelete_user());
				nota_factura.setDelete_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				nota_contableDao.actualizar(nota_factura);
			}
			Admision admision_aux = new Admision();
			admision_aux.setCodigo_empresa(facturacion.getCodigo_empresa());
			admision_aux.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			admision_aux.setNro_identificacion(facturacion.getCodigo_tercero());
			admision_aux.setNro_ingreso(facturacion.getNro_ingreso());
			admision_aux = admisionService.consultar(admision_aux);
			if (admision_aux != null) {
				admision_aux.setMotivo_cancelacion(facturacion
						.getMotivo_anulacion());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("admision", admision_aux);
				admisionService.actualizar(map);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Double getPorcentaje_reclaculo() {
		return porcentaje_reclaculo;
	}

	public void setPorcentaje_reclaculo(Double porcentaje_reclaculo) {
		this.porcentaje_reclaculo = porcentaje_reclaculo;
	}

	public Long getTotalServicios(Map<String, Object> map) {
		return facturacionDao.getTotalServicios(map);
	}

	public List<Map<String, Object>> listarServicios(Map<String, Object> map) {
		return facturacionDao.listarServicios(map);
	}

	public Integer getCantidadMaximaServicio(Map<String, Object> map) {
		return facturacionDao.getCantidadMaximaServicio(map);
	}
}
