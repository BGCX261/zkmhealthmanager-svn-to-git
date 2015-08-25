/*
 * Detalle_receta_ripsServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Contratos_paquetes;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Paquetes_servicios;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Contratos_paquetesDao;
import healthmanager.modelo.dao.Datos_medicamentosDao;
import healthmanager.modelo.dao.Detalle_receta_ripsDao;
import healthmanager.modelo.dao.Detalles_paquetes_serviciosDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.Pacientes_contratosDao;
import healthmanager.modelo.dao.ProcedimientosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.locator.ServiceLocatorWeb;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Bodega_articulo;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Kardex;
import contaweb.modelo.dao.ArticuloDao;
import contaweb.modelo.dao.Bodega_articuloDao;
import contaweb.modelo.service.FacturacionService;

@Service("detalle_receta_ripsService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Detalle_receta_ripsService implements Serializable {

	private static Logger log = Logger
			.getLogger(Detalle_receta_ripsService.class);

	private String limit;

	@Autowired
	private Detalle_receta_ripsDao detalle_receta_ripsDao;
	@Autowired
	private FacturacionService facturacionService;
	@Autowired
	private contaweb.modelo.dao.ResolucionDao resolucionContDao;
	@Autowired
	private healthmanager.modelo.dao.ResolucionDao resolucionDao;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private PacienteDao pacienteDao;
	@Autowired
	private ArticuloDao articuloDao;
	@Autowired
	private Bodega_articuloDao bodega_articuloDao;
	@Autowired
	private Facturacion_medicamentoService facturacion_medicamentoService;

	@Autowired
	private Datos_procedimientoService datos_procedimientoService;

	@Autowired
	private Pacientes_contratosDao pacientes_contratosDao;
	@Autowired
	private Contratos_paquetesDao contratos_paquetesDao;
	@Autowired
	private Detalles_paquetes_serviciosDao detalles_paquetes_serviciosDao;
	@Autowired
	private ProcedimientosDao procedimientosDao;
	@Autowired
	private Datos_medicamentosDao datos_medicamentosDao;

	public void crear(Detalle_receta_rips detalle_receta_rips) {
		try {
			if (consultar(detalle_receta_rips) != null) {
				throw new HealthmanagerException(
						"Detalle_receta_rips ya se encuentra en la base de datos");
			}
			detalle_receta_ripsDao.crear(detalle_receta_rips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_receta_rips detalle_receta_rips) {
		try {
			return detalle_receta_ripsDao.actualizar(detalle_receta_rips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_receta_rips consultar(Detalle_receta_rips detalle_receta_rips) {
		try {
			return detalle_receta_ripsDao.consultar(detalle_receta_rips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_receta_rips detalle_receta_rips) {
		try {
			return detalle_receta_ripsDao.eliminar(detalle_receta_rips);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_receta_rips> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return detalle_receta_ripsDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void guardarEntregaMedicamento(Receta_rips recetaRips,
			List<Detalle_receta_rips> detalle_receta_rips, Usuarios usuarios,
			ServiceLocatorWeb serviceLocator) {
		try {
			Admision admision = new Admision();
			admision.setCodigo_empresa(usuarios.getCodigo_empresa());
			admision.setCodigo_sucursal(usuarios.getCodigo_sucursal());
			admision.setNro_identificacion(recetaRips.getNro_identificacion());
			admision.setNro_ingreso(recetaRips.getNro_ingreso());
			admision = serviceLocator.getAdmisionService().consultar(admision);

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(usuarios.getCodigo_empresa());
			paciente.setCodigo_sucursal(usuarios.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = serviceLocator.getPacienteService().consultar(paciente);

			Facturacion_medicamento facturacionMedicamento = new Facturacion_medicamento();
			facturacionMedicamento.setCodigo_empresa(usuarios
					.getCodigo_empresa());
			facturacionMedicamento.setCodigo_sucursal(usuarios
					.getCodigo_sucursal());
			// facturacionMedicamento.setNro_factura(nro_factura_med);
			facturacionMedicamento.setTipo_identificacion(paciente
					.getTipo_identificacion());
			facturacionMedicamento.setNro_identificacion(paciente
					.getNro_identificacion());
			facturacionMedicamento.setCodigo_administradora(admision
					.getCodigo_administradora());
			facturacionMedicamento.setId_plan(admision.getId_plan());
			facturacionMedicamento.setNro_ingreso(admision.getNro_ingreso());
			facturacionMedicamento.setFecha_medicamento(Timestamp
					.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(recetaRips.getFecha())));
			facturacionMedicamento.setNumero_autorizacion("");
			facturacionMedicamento.setObservacion("");
			facturacionMedicamento.setTipo("01");
			facturacionMedicamento.setCreacion_date(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			facturacionMedicamento.setUltimo_update(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			facturacionMedicamento.setCreacion_user(usuarios.getCodigo()
					.toString());
			facturacionMedicamento.setUltimo_user(usuarios.getCodigo()
					.toString());
			facturacionMedicamento.setCodigo_solicitud("");
			facturacionMedicamento.setCodigo_receta(recetaRips
					.getCodigo_receta());
			facturacionMedicamento.setC_costo("");

			boolean isNewFact = false;
			Facturacion_medicamento facturacionMedicamentoTemp = serviceLocator
					.getFacturacionMedicamentoService().consultar(
							facturacionMedicamento);
			if (facturacionMedicamentoTemp == null) {
				String nro_fact = serviceLocator.getConsecutivoService()
						.crearConsecutivo(usuarios.getCodigo_empresa(),
								usuarios.getCodigo_sucursal(),
								"MEDICAMENTOS-MATERIALES");
				facturacionMedicamento.setNro_factura(nro_fact);
				serviceLocator.getFacturacionMedicamentoService().crear(
						facturacionMedicamento);
				isNewFact = true;
			} else {
				facturacionMedicamento = facturacionMedicamentoTemp;
			}

			guardarRelacionConFactura(admision, facturacionMedicamento);

			Kardex kardex = new Kardex();
			kardex.setCodigo_empresa(facturacionMedicamento.getCodigo_empresa());
			kardex.setCodigo_sucursal(facturacionMedicamento
					.getCodigo_sucursal());
			kardex.setCodigo_fuente("15");
			kardex.setDetalle("Salida Fac # 15 -"
					+ facturacionMedicamento.getNro_factura()
					+ " Por Facturacion de medicamentos");
			kardex.setCodigo_documento(facturacionMedicamento.getNro_factura());
			kardex.setCreacion_date(facturacionMedicamento.getCreacion_date());
			kardex.setUltimo_update(facturacionMedicamento.getUltimo_update());
			kardex.setCreacion_user(facturacionMedicamento.getCreacion_user());
			kardex.setUltimo_user(facturacionMedicamento.getUltimo_user());

			int i = 0;
			log.debug(":::::Size detalles: " + detalle_receta_rips.size());
			for (Detalle_receta_rips detalleRecetaRips : detalle_receta_rips) {

				double cantidadAEntregar = detalleRecetaRips
						.getCantidad_entregada();

				/* entregamos pendientes */
				double cantidad_pendiente_entregada = detalleRecetaRips
						.getEntregarFaltantes() != null ? detalleRecetaRips
						.getEntregarFaltantes() : 0d;
				log.debug("::: Cantidad entregada: "
						+ detalleRecetaRips.getCantidad_entregada());
				log.debug("::: Cantidad faltante a entregar: "
						+ cantidad_pendiente_entregada);
				detalleRecetaRips
						.setCantidad_entregada(detalleRecetaRips
								.getCantidad_entregada()
								+ cantidad_pendiente_entregada);

				/* fin cantidad pendiente entregada */

				double cantidad_entregada = detalleRecetaRips
						.getCantidad_entregada();
				double cantidad_recetada = detalleRecetaRips
						.getCantidad_recetada();
				/* validacion */
				double cantidad_pendiente = cantidad_recetada
						- cantidad_entregada;
				detalleRecetaRips.setCantidad_pendiente(cantidad_pendiente);
				if (detalleRecetaRips.getEntregado().equals("S")) {
					cantidadAEntregar = cantidad_pendiente_entregada;
				} else
					detalleRecetaRips.setEntregado("S");

				log.debug("::: Cantidad entregada: " + cantidadAEntregar);
				detalle_receta_ripsDao.actualizar(detalleRecetaRips);

				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(usuarios.getCodigo_empresa());
				articulo.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				articulo.setCodigo_articulo(detalleRecetaRips
						.getCodigo_articulo());
				articulo = serviceLocator.getArticuloService().consultar(
						articulo);

				if (articulo == null) {
					throw new Exception("Articulo de codigo "
							+ detalleRecetaRips.getCodigo_articulo()
							+ " no existe");
				}

				/* cargamos existencias */
				Bodega_articulo bodega_articulo = new Bodega_articulo();
				bodega_articulo.setCodigo_empresa(articulo.getCodigo_empresa());
				bodega_articulo.setCodigo_sucursal(articulo
						.getCodigo_sucursal());
				bodega_articulo.setCodigo_bodega("01");
				bodega_articulo.setCodigo_articulo(articulo
						.getCodigo_articulo());
				bodega_articulo = serviceLocator.getBodega_articuloService()
						.consultar(bodega_articulo);

				kardex.setCodigo_bodega("01");
				kardex.setCodigo_articulo(detalleRecetaRips
						.getCodigo_articulo());
				kardex.setFecha(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));

				log.debug(":: bucle: " + i + " fact: "
						+ facturacionMedicamento.getNro_factura());

				Datos_medicamentos datos_medicamentos = new Datos_medicamentos();
				datos_medicamentos.setCodigo_empresa(usuarios
						.getCodigo_empresa());
				datos_medicamentos.setCodigo_sucursal(usuarios
						.getCodigo_sucursal());
				datos_medicamentos.setNro_factura(facturacionMedicamento
						.getNro_factura());
				datos_medicamentos.setConsecutivo((i++) + "");
				datos_medicamentos.setCodigo_bodega(bodega_articulo
						.getCodigo_bodega());
				datos_medicamentos.setCodigo_medicamento(articulo
						.getCodigo_articulo());
				datos_medicamentos.setCodigo_lote("");
				datos_medicamentos.setUnidades(((int) Math
						.ceil(detalleRecetaRips.getCantidad_entregada())));
				datos_medicamentos.setValor_unitario(detalleRecetaRips
						.getValor_unitario());
				datos_medicamentos
						.setValor_total(datos_medicamentos.getUnidades()
								* datos_medicamentos.getValor_unitario());
				datos_medicamentos.setReferencia_pyp(articulo.getReferencia());
				datos_medicamentos.setCancelo_copago("N");
				datos_medicamentos.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				datos_medicamentos.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				datos_medicamentos.setCreacion_user(usuarios.getCodigo()
						.toString());
				datos_medicamentos.setUltimo_user(usuarios.getCodigo()
						.toString());
				datos_medicamentos.setValor_real(detalleRecetaRips
						.getValor_real());
				datos_medicamentos.setDescuento(detalleRecetaRips
						.getDescuento());
				datos_medicamentos.setIncremento(detalleRecetaRips
						.getIncremento());
				datos_medicamentos.setCantidad_entregada(datos_medicamentos
						.getUnidades());

				kardex.setUnidad_salida(datos_medicamentos
						.getCantidad_entregada());
				kardex.setValor_salida(datos_medicamentos.getValor_unitario());
				kardex.setTotal_salida(datos_medicamentos.getValor_total());
				kardex.setUnidad_total(bodega_articulo.getCantidad()
						- datos_medicamentos.getCantidad_entregada());
				kardex.setValor_unitario_total(datos_medicamentos
						.getValor_unitario());
				kardex.setValor_total(kardex.getUnidad_total()
						* kardex.getValor_unitario_total());

				/* verificamos eliminacion */
				serviceLocator.getKardexService().eliminar(kardex);
				serviceLocator.getKardexService().crear(kardex);
				/* fin de verificacion de eliminacion */

				bodega_articulo.setCosto(detalleRecetaRips.getValor_unitario());
				bodega_articulo.setCantidad(bodega_articulo.getCantidad()
						- cantidadAEntregar);
				serviceLocator.getBodega_articuloService().actualizar(
						bodega_articulo);

				/* Where datos factura */
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa",
						datos_medicamentos.getCodigo_empresa());
				map.put("codigo_sucursal",
						datos_medicamentos.getCodigo_sucursal());
				map.put("nro_factura", datos_medicamentos.getNro_factura());
				map.put("codigo_bodega", datos_medicamentos.getCodigo_bodega());
				map.put("codigo_medicamento",
						datos_medicamentos.getCodigo_medicamento());
				if (datos_medicamentosDao.existe(map)) {
					datos_medicamentosDao.actualizar(datos_medicamentos);
				} else {
					datos_medicamentosDao.crear(datos_medicamentos);
				}
			}
			if (isNewFact)
				serviceLocator.getConsecutivoService().actualizarConsecutivo(
						usuarios.getCodigo_empresa(),
						usuarios.getCodigo_sucursal(),
						"MEDICAMENTOS-MATERIALES",
						facturacionMedicamento.getNro_factura());
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarEntregaMedicamentos(Receta_rips recetaRips,
			List<Detalle_receta_rips> detalle_receta_rips, Usuarios usuarios) {
		try {
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

			boolean afectar_kardex_fact = false;
			healthmanager.modelo.bean.Resolucion resH = new healthmanager.modelo.bean.Resolucion();
			resH.setCodigo_empresa(usuarios.getCodigo_empresa());
			resH = (healthmanager.modelo.bean.Resolucion) resolucionDao
					.consultar(resH);
			if (resH != null) {
				if (resH.getAfectar_kardex_fact()) {
					afectar_kardex_fact = true;
				}
			}

			Admision admision = new Admision();
			admision.setCodigo_empresa(usuarios.getCodigo_empresa());
			admision.setCodigo_sucursal(usuarios.getCodigo_sucursal());
			admision.setNro_identificacion(recetaRips.getNro_identificacion());
			admision.setNro_ingreso(recetaRips.getNro_ingreso());
			admision = admisionDao.consultar(admision);

			// if (admision == null)
			// throw new ValidacionRunTimeException(
			// "La admision con el numero de ingreso "
			// + recetaRips.getNro_ingreso() + " no existe");

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(usuarios.getCodigo_empresa());
			paciente.setCodigo_sucursal(usuarios.getCodigo_sucursal());
			paciente.setNro_identificacion(recetaRips.getNro_identificacion());
			paciente = pacienteDao.consultar(paciente);

			if (paciente == null)
				throw new ValidacionRunTimeException("El paciente con el nro  "
						+ recetaRips.getNro_identificacion() + " no existe");

			// creamos factura de medicamento
			Facturacion_medicamento facturacionMedicamento = new Facturacion_medicamento();
			facturacionMedicamento.setCodigo_empresa(usuarios
					.getCodigo_empresa());
			facturacionMedicamento.setCodigo_sucursal(usuarios
					.getCodigo_sucursal());
			// facturacionMedicamento.setNro_factura(nro_factura_med);
			facturacionMedicamento.setTipo_identificacion(paciente
					.getTipo_identificacion());
			facturacionMedicamento.setNro_identificacion(paciente
					.getNro_identificacion());
			facturacionMedicamento.setCodigo_administradora(recetaRips
					.getCodigo_administradora());
			facturacionMedicamento.setId_plan(recetaRips.getId_plan());
			facturacionMedicamento.setNro_ingreso(admision != null ? admision
					.getNro_ingreso() : "");
			facturacionMedicamento.setFecha_medicamento(Timestamp
					.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(recetaRips.getFecha())));
			facturacionMedicamento.setNumero_autorizacion("");
			facturacionMedicamento.setObservacion("");
			facturacionMedicamento.setTipo("01");
			facturacionMedicamento.setCreacion_date(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			facturacionMedicamento.setUltimo_update(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			facturacionMedicamento.setCreacion_user(usuarios.getCodigo()
					.toString());
			facturacionMedicamento.setUltimo_user(usuarios.getCodigo()
					.toString());
			facturacionMedicamento.setCodigo_solicitud("");
			facturacionMedicamento.setCodigo_receta(recetaRips
					.getCodigo_receta());
			facturacionMedicamento.setC_costo("");

			boolean facturar_paquete = false;
			Contratos_paquetes contratos_paquetes_aux = null;
			List<Detalles_paquetes_servicios> listado_detalles_medicamentos = new ArrayList<Detalles_paquetes_servicios>();

			if (admision != null && admision.getAdmision_parto().equals("S")) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", admision.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", admision.getCodigo_sucursal());
				parametros.put("nro_identificacion",
						admision.getNro_identificacion());
				parametros.put("codigo_administradora",
						admision.getCodigo_administradora());

				List<Pacientes_contratos> listado_contratos = pacientes_contratosDao
						.listar(parametros);

				for (Pacientes_contratos pacientes_contratos : listado_contratos) {
					Map<String, Object> parametros_paquetes = new HashMap<String, Object>();
					parametros_paquetes.put("codigo_empresa",
							pacientes_contratos.getCodigo_empresa());
					parametros_paquetes.put("codigo_sucursal",
							pacientes_contratos.getCodigo_sucursal());
					parametros_paquetes.put("id_plan",
							pacientes_contratos.getId_codigo());
					parametros_paquetes.put("codigo_administradora",
							pacientes_contratos.getCodigo_administradora());

					List<Contratos_paquetes> listado_paquetes = contratos_paquetesDao
							.listar(parametros_paquetes);

					for (Contratos_paquetes contratos_paquetes : listado_paquetes) {
						if (contratos_paquetes.getPaquetes_servicios()
								.getVia_ingreso()
								.equals(admision.getVia_ingreso())) {
							contratos_paquetes_aux = contratos_paquetes;
							break;
						}
					}

					if (contratos_paquetes_aux != null)
						break;

				}

				if (contratos_paquetes_aux != null) {
					Map<String, Object> parametros_cups = new HashMap<String, Object>();
					parametros_cups.put("codigo_empresa",
							contratos_paquetes_aux.getCodigo_empresa());
					parametros_cups.put("codigo_sucursal",
							contratos_paquetes_aux.getCodigo_sucursal());
					parametros_cups.put("codigo_administradora",
							contratos_paquetes_aux.getCodigo_administradora());
					parametros_cups.put("id_paquete",
							contratos_paquetes_aux.getId_paquete());
					parametros_cups.put("tipo_detalle", "02");
					listado_detalles_medicamentos = detalles_paquetes_serviciosDao
							.listar(parametros_cups);
				}

			}

			List<Datos_medicamentos> listdatos_medicamentos = new ArrayList<Datos_medicamentos>();
			int i = 0;
			boolean facturado_paquete_parto = false;
			for (Detalle_receta_rips detalle_receta_ripsTemp : detalle_receta_rips) {
				facturar_paquete = false;
				// double cantidadAEntregar = detalle_receta_ripsTemp
				// .getCantidad_entregada();

				/* entregamos pendientes */
				double cantidad_pendiente_entregada = detalle_receta_ripsTemp
						.getEntregarFaltantes() != null ? detalle_receta_ripsTemp
						.getEntregarFaltantes() : 0d;

				detalle_receta_ripsTemp
						.setCantidad_entregada(detalle_receta_ripsTemp
								.getCantidad_entregada()
								+ cantidad_pendiente_entregada);

				double cantidad_entregada = detalle_receta_ripsTemp
						.getCantidad_entregada();
				double cantidad_recetada = detalle_receta_ripsTemp
						.getCantidad_recetada();
				/* validacion */
				double cantidad_pendiente = cantidad_recetada
						- cantidad_entregada;
				detalle_receta_ripsTemp
						.setCantidad_pendiente(cantidad_pendiente);
				if (detalle_receta_ripsTemp.getEntregado().equals("S")) {
					// cantidadAEntregar = cantidad_pendiente_entregada;
				} else
					detalle_receta_ripsTemp.setEntregado("S");

				detalle_receta_ripsDao.actualizar(detalle_receta_ripsTemp);

				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(usuarios.getCodigo_empresa());
				articulo.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				articulo.setCodigo_articulo(detalle_receta_ripsTemp
						.getCodigo_articulo());
				articulo = articuloDao.consultar(articulo);

				if (articulo == null) {
					throw new Exception("Articulo de codigo "
							+ detalle_receta_ripsTemp.getCodigo_articulo()
							+ " no existe");
				}

				/* cargamos existencias */
				Bodega_articulo bodega_articulo = new Bodega_articulo();
				bodega_articulo.setCodigo_empresa(articulo.getCodigo_empresa());
				bodega_articulo.setCodigo_sucursal(articulo
						.getCodigo_sucursal());
				bodega_articulo.setCodigo_bodega("01");
				bodega_articulo.setCodigo_articulo(articulo
						.getCodigo_articulo());
				bodega_articulo = bodega_articuloDao.consultar(bodega_articulo);

				if (bodega_articulo == null)
					throw new ValidacionRunTimeException("El articulo "
							+ articulo.getNombre1()
							+ " no se encuentra en bodega");

				for (Detalles_paquetes_servicios detalles_paquetes_servicios : listado_detalles_medicamentos) {
					if (detalles_paquetes_servicios.getCodigo_detalle().equals(
							detalle_receta_ripsTemp.getCodigo_articulo())) {
						facturar_paquete = true;
						break;
					}
				}

				if (facturar_paquete && contratos_paquetes_aux != null) {
					if (!facturado_paquete_parto) {
						guardarDatos_procedimiento_paquete(
								contratos_paquetes_aux.getPaquetes_servicios(),
								admision, admision.getPaciente());
						facturado_paquete_parto = true;
					}
				}

				Datos_medicamentos datos_medicamentos = new Datos_medicamentos();
				datos_medicamentos.setCodigo_empresa(usuarios
						.getCodigo_empresa());
				datos_medicamentos.setCodigo_sucursal(usuarios
						.getCodigo_sucursal());
				datos_medicamentos.setNro_factura(facturacionMedicamento
						.getNro_factura());
				datos_medicamentos.setConsecutivo((++i) + "");
				datos_medicamentos.setCodigo_bodega(bodega_articulo
						.getCodigo_bodega());
				datos_medicamentos.setCodigo_medicamento(articulo
						.getCodigo_articulo());
				datos_medicamentos.setReferencia_pyp(articulo.getReferencia());
				datos_medicamentos.setCodigo_lote("");
				datos_medicamentos
						.setUnidades(((int) Math.ceil(detalle_receta_ripsTemp
								.getCantidad_entregada())));
				datos_medicamentos
						.setValor_unitario((facturar_paquete && contratos_paquetes_aux != null) ? 0.0
								: detalle_receta_ripsTemp.getValor_unitario());
				datos_medicamentos
						.setValor_total((facturar_paquete && contratos_paquetes_aux != null) ? 0.0
								: datos_medicamentos.getUnidades()
										* datos_medicamentos
												.getValor_unitario());
				datos_medicamentos.setCancelo_copago("N");
				datos_medicamentos.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				datos_medicamentos.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				datos_medicamentos.setCreacion_user(usuarios.getCodigo()
						.toString());
				datos_medicamentos.setUltimo_user(usuarios.getCodigo()
						.toString());
				datos_medicamentos
						.setValor_real((facturar_paquete && contratos_paquetes_aux != null) ? 0.0
								: detalle_receta_ripsTemp.getValor_real());
				datos_medicamentos
						.setDescuento((facturar_paquete && contratos_paquetes_aux != null) ? 0.0
								: detalle_receta_ripsTemp.getDescuento());
				datos_medicamentos
						.setIncremento((facturar_paquete && contratos_paquetes_aux != null) ? 0.0
								: detalle_receta_ripsTemp.getIncremento());
				datos_medicamentos.setCantidad_entregada(datos_medicamentos
						.getUnidades());
				listdatos_medicamentos.add(datos_medicamentos);

			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("admision", admision);
			map.put("facturacion_medicamento", facturacionMedicamento);
			map.put("lista_medicamentos", listdatos_medicamentos);
			map.put("accion", "registrar");
			map.put("cont", cont);
			map.put("afectar_kardex_fact", afectar_kardex_fact);
			facturacion_medicamentoService.guardarFacturacion(map, true);
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage());
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private void guardarDatos_procedimiento_paquete(
			Paquetes_servicios paquetes_servicios, Admision admision,
			Paciente paciente) {
		// log.info("facturando Procedimiento ordenando ===> "
		// + paquetes_servicios.getId_procedimiento_principal());

		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(paquetes_servicios
				.getId_procedimiento_principal()));
		procedimientos = procedimientosDao.consultar(procedimientos);

		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento.setCodigo_empresa(paquetes_servicios
				.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(paquetes_servicios
				.getCodigo_sucursal());
		datos_procedimiento.setCodigo_registro(null);

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
		datos_procedimiento.setNumero_autorizacion("");
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
		datos_procedimiento.setCodigo_cups(procedimientos.getCodigo_cups());
		datos_procedimiento.setCodigo_orden(null);

		Datos_procedimiento datos_procedimiento2 = datos_procedimientoService
				.consultar(datos_procedimiento);

		if (datos_procedimiento2 != null) {
			datos_procedimiento.setCodigo_registro(datos_procedimiento2
					.getCodigo_registro());
			datos_procedimientoService.actualizarRegistro(datos_procedimiento);
		} else {
			datos_procedimiento.setCodigo_registro(null);
			datos_procedimientoService.crear(datos_procedimiento);
		}
	}

	private void guardarRelacionConFactura(Admision admision,
			Facturacion_medicamento facturacionMedicamento) {
		Facturacion facturacion = new Facturacion();
		facturacion.setCodigo_empresa(admision.getCodigo_empresa());
		facturacion.setCodigo_sucursal(admision.getCodigo_sucursal());
		facturacion.setCodigo_tercero(admision.getNro_identificacion());
		facturacion.setNro_ingreso(admision.getNro_ingreso());
		facturacion = facturacionService.consultar(facturacion);
		if (facturacion != null) {

		}
	}

}
