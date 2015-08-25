/*
 * Hoja_gastosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Datos_servicio;
import healthmanager.modelo.bean.Detalle_hoja_gasto;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.bean.Hoja_gastos;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.dao.Detalle_hoja_gastoDao;
import healthmanager.modelo.dao.Hoja_gastosDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.ResolucionDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConsecutivos;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.dao.ArticuloDao;

@Service("hoja_gastosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Hoja_gastosService implements Serializable{

	@Autowired
	private Hoja_gastosDao hoja_gastosDao;
	@Autowired
	private ResolucionDao resolucionDao;
	@Autowired
	private Detalle_hoja_gastoDao detalle_hoja_gastoDao;
	@Autowired
	private Facturacion_medicamentoService facturacion_medicamentoService;
	@Autowired
	private Facturacion_servicioService facturacion_servicioService;
	@Autowired
	private contaweb.modelo.dao.ResolucionDao resolucionContDao;
	@Autowired
	private PacienteDao pacienteDao;
	@Autowired
	private ArticuloDao articuloDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	private String limit;

	public void crear(Hoja_gastos hoja_gastos) {
		try {
			if (consultar(hoja_gastos) != null) {
				throw new HealthmanagerException(
						"Hoja_gastos ya se encuentra en la base de datos");
			}
			hoja_gastosDao.crear(hoja_gastos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Hoja_gastos hoja_gastos) {
		try {
			return hoja_gastosDao.actualizar(hoja_gastos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Hoja_gastos consultar(Hoja_gastos hoja_gastos) {
		try {
			return hoja_gastosDao.consultar(hoja_gastos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Hoja_gastos hoja_gastos) {
		try {
			return hoja_gastosDao.eliminar(hoja_gastos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Hoja_gastos> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return hoja_gastosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return hoja_gastosDao.existe(parameters);
	}

	/**
	 * <b>Nota:</b> Este metodo se utiliza tambien a la hora de guardar la
	 * historia de Urgencia
	 * */
	public void guardar(Map<String, Object> map) {
		try {
			String accion = (String) map.get("accion");
			List<Map<String, Object>> listitems = (List<Map<String, Object>>) map
					.get("listado_detalle");
			Hoja_gastos hoja_gastos = (Hoja_gastos) map.get("hoja_gasto");
			Admision admision = (Admision) map.get("admision");
			String codigo_receta = (String) map.get("codigo_receta");

			if (codigo_receta == null) {
				codigo_receta = "";
			}

			List<Datos_medicamentos> datos_medicamentos = new ArrayList<Datos_medicamentos>();
			List<Datos_servicio> datos_servicios = new ArrayList<Datos_servicio>();

			if (hoja_gastosDao.consultar(hoja_gastos) != null) {
				int result = hoja_gastosDao.actualizar(hoja_gastos);
				if (result == 0) {
					throw new ValidacionRunTimeException(
							"Lo sentimos pero los datos a modificar de la hoja de gastos no se encuentran en base de datos");
				}
			} else {
				hoja_gastosDao.crear(hoja_gastos);
			}

			boolean afectar_kardex_fact = true;
			boolean cont = false;

			Paciente paciente = admision.getPaciente();

			if (paciente == null) {
				throw new ValidacionRunTimeException(
						"El paciente con la identificacion "
								+ admision.getNro_identificacion()
								+ " no existe");
			}

			healthmanager.modelo.bean.Resolucion res2 = new healthmanager.modelo.bean.Resolucion();
			res2.setCodigo_empresa(hoja_gastos.getCodigo_empresa());
			res2 = resolucionDao.consultar(res2);
			if (res2 != null) {
				afectar_kardex_fact = res2.getAfectar_kardex_fact();
			}

			contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
			res.setCodigo_empresa(hoja_gastos.getCodigo_empresa());
			res = resolucionContDao.consultar(res);
			if (res != null) {
				if (res.getContabiliza_aut()) {
					cont = true;
				}
			}

			for (Map<String, Object> mapDetalle : listitems) {
				//log.info("mapDetalle ===> " + mapDetalle);
				// boolean noafectabodega = mapDetalle.get("noafectabodega") !=
				// null;
				String tipo = (String) mapDetalle.get("tipo");
				String consecutivo = (String) mapDetalle.get("consecutivo");
				if (tipo == null) {
					tipo = "01";
				}
				// if (!noafectabodega) {
				String codigo_articulo = (String) mapDetalle
						.get("codigo_articulo");
				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(hoja_gastos.getCodigo_empresa());
				articulo.setCodigo_sucursal(hoja_gastos.getCodigo_sucursal());
				articulo.setCodigo_articulo(codigo_articulo);
				articulo = articuloDao.consultar(articulo);

				if (articulo == null)
					throw new ValidacionRunTimeException(
							"El articulo con el codigo " + codigo_articulo
									+ " no existe");

				Detalle_hoja_gasto detalle_hoja_gasto = convertirMapADetalleHoja(
						mapDetalle, hoja_gastos);
				if (detalle_hoja_gasto.getConsecutivo() != null
						&& !detalle_hoja_gasto.getConsecutivo().equals("")) {
					detalle_hoja_gastoDao.actualizar(detalle_hoja_gasto);
				} else {
					consecutivo = consecutivoService.crearConsecutivo(
							hoja_gastos.getCodigo_empresa(),
							hoja_gastos.getCodigo_sucursal(),
							IConsecutivos.CONS_DETALLE_HOJA_GASTOS);
					detalle_hoja_gasto.setConsecutivo(consecutivo);
					detalle_hoja_gastoDao.crear(detalle_hoja_gasto);
					consecutivoService
							.actualizarConsecutivo(
									hoja_gastos.getCodigo_empresa(),
									hoja_gastos.getCodigo_sucursal(),
									IConsecutivos.CONS_DETALLE_HOJA_GASTOS,
									consecutivo);
				}

				// if (articulo.getFacturable()) {
				if (tipo.equals("01")) {// medicamentos
					Datos_medicamentos datos_medicamentosTemp = convertirDetalleHojaADatosMedicamentos(detalle_hoja_gasto);
					datos_medicamentosTemp.setReferencia_pyp(articulo
							.getReferencia());
					// tipo de servicio materiales de insumos
					datos_medicamentosTemp.setTipo_medicamento(articulo
							.getPos().equals("S") ? "1" : "2");
					datos_medicamentos.add(datos_medicamentosTemp);
				} else {// servicos
					Datos_servicio datos_servicioTemp = convertirDetalleHojaADatosServicios(detalle_hoja_gasto);
					datos_servicioTemp.setReferencia_pyp(articulo
							.getReferencia());
					// tipo de servicio materiales de insumos
					datos_servicioTemp.setTipo_servicio("1");
					datos_servicios.add(datos_servicioTemp);
				}
				// } else {
				// //log.info("El articulo "
				// + articulo.getCodigo_articulo() + " "
				// + articulo.getNombre1()
				// + " no es facturable");
				// }

			}

			// }

			// facturamos medicamentos
			if (!datos_medicamentos.isEmpty()) {
				Map<String, Object> datos_guardar = new HashMap<String, Object>();
				datos_guardar.put("lista_medicamentos", datos_medicamentos);
				datos_guardar.put(
						"facturacion_medicamento",
						getFacturacionMedicamento(admision, hoja_gastos,
								paciente, codigo_receta));
				datos_guardar.put("accion", accion);
				datos_guardar.put("cont", cont);
				datos_guardar.put("afectar_kardex_fact", afectar_kardex_fact);
				facturacion_medicamentoService
						.guardarFacturacion(datos_guardar, true);
			}
			// facturamos servicios
			if (!datos_servicios.isEmpty()) {
				Map<String, Object> datos = new HashMap<String, Object>();
				Facturacion_servicio facturacion_servicio = getFacturacionServicio(
						admision, hoja_gastos, paciente);
				Facturacion_servicio facturacion_servicio2 = facturacion_servicioService
						.consultar(facturacion_servicio);
				String accion_ser = "registrar";
				if (facturacion_servicio2 != null) {
					accion_ser = "modificar";
					facturacion_servicio.setNro_factura(facturacion_servicio2
							.getNro_factura());
				}
				datos.put("facturacion_servicio", facturacion_servicio);
				datos.put("lista_detalle", datos_servicios);
				datos.put("accion", accion_ser);
				facturacion_servicioService.guardar(datos, true);
			}

		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private Facturacion_medicamento getFacturacionMedicamento(
			Admision admision, Hoja_gastos hoja_gastos, Paciente paciente,
			String codigo_receta) {
		Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
		facturacion_medicamento.setCodigo_empresa(admision.getCodigo_empresa());
		facturacion_medicamento.setCodigo_sucursal(admision
				.getCodigo_sucursal());
		facturacion_medicamento.setNro_factura("");
		facturacion_medicamento.setTipo_identificacion(paciente
				.getTipo_identificacion());
		facturacion_medicamento.setNro_identificacion(paciente
				.getNro_identificacion());
		facturacion_medicamento.setCodigo_administradora(admision
				.getCodigo_administradora());
		facturacion_medicamento.setId_plan(admision.getId_plan());
		facturacion_medicamento.setNro_ingreso(admision.getNro_ingreso());
		facturacion_medicamento.setFecha_medicamento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		facturacion_medicamento.setNumero_autorizacion("");
		facturacion_medicamento
				.setObservacion("FACTURA DE MEDICAMENTOS POR HOJA DE GASTOS");
		facturacion_medicamento.setTipo("01");

		facturacion_medicamento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_medicamento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		facturacion_medicamento.setDelete_date(new Timestamp((new Date())
				.getTime()));
		facturacion_medicamento
				.setCreacion_user(hoja_gastos.getCreacion_user());
		facturacion_medicamento.setUltimo_user(hoja_gastos.getUltimo_user());
		facturacion_medicamento.setCodigo_solicitud("");
		facturacion_medicamento.setCodigo_receta(codigo_receta);
		facturacion_medicamento.setC_costo("");
		return facturacion_medicamento;
	}

	private Facturacion_servicio getFacturacionServicio(Admision admision,
			Hoja_gastos hoja_gastos, Paciente paciente) {
		Facturacion_servicio facturacion_servicio = new Facturacion_servicio();
		facturacion_servicio.setCodigo_empresa(admision.getCodigo_empresa());
		facturacion_servicio.setCodigo_sucursal(admision.getCodigo_sucursal());
		facturacion_servicio.setTipo_identificacion(paciente
				.getTipo_identificacion());
		facturacion_servicio.setNro_identificacion(paciente
				.getNro_identificacion());
		facturacion_servicio.setCodigo_administradora(admision
				.getCodigo_administradora());
		facturacion_servicio.setId_plan(admision.getId_plan());
		facturacion_servicio.setNro_ingreso(admision.getNro_ingreso());
		facturacion_servicio.setFecha_servicio(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		facturacion_servicio.setNumero_autorizacion("");
		facturacion_servicio
				.setObservacion("FACTURA DE SERVICIO POR HOJA DE GASTO");
		facturacion_servicio.setTipo("01");
		facturacion_servicio.setCreacion_date(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		facturacion_servicio.setUltimo_update(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		facturacion_servicio.setCreacion_user(hoja_gastos.getCreacion_user());
		facturacion_servicio.setUltimo_user(hoja_gastos.getUltimo_user());
		return facturacion_servicio;
	}

	private Datos_servicio convertirDetalleHojaADatosServicios(
			Detalle_hoja_gasto detalle_hoja_gasto) {
		Datos_servicio datos_servicio = new Datos_servicio();
		datos_servicio
				.setCodigo_empresa(detalle_hoja_gasto.getCodigo_empresa());
		datos_servicio.setCodigo_sucursal(detalle_hoja_gasto
				.getCodigo_sucursal());
		datos_servicio.setCodigo_servicio(detalle_hoja_gasto
				.getCodigo_articulo());
		datos_servicio
				.setValor_unitario(detalle_hoja_gasto.getValor_unitario());
		datos_servicio.setCancelo_copago("N");
		// datos_servicio.setValor_total((Double) pcd.get("valor_total"));
		datos_servicio.setUnidades(detalle_hoja_gasto.getCantidad());
		datos_servicio.setValor_total(detalle_hoja_gasto.getCantidad()
				.intValue() * detalle_hoja_gasto.getValor_unitario());
		datos_servicio.setDescuento(detalle_hoja_gasto.getDescuento());
		datos_servicio.setIncremento(detalle_hoja_gasto.getIncremento());
		datos_servicio.setValor_real(detalle_hoja_gasto.getValor_real());
		datos_servicio.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		datos_servicio.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		datos_servicio.setCreacion_user(detalle_hoja_gasto.getCreacion_user());
		datos_servicio.setUltimo_user(detalle_hoja_gasto.getUltimo_user());
		return datos_servicio;
	}

	private Datos_medicamentos convertirDetalleHojaADatosMedicamentos(
			Detalle_hoja_gasto detalle_hoja_gasto) {
		Datos_medicamentos datos_medicamentos = new Datos_medicamentos();
		datos_medicamentos.setCodigo_medicamento(detalle_hoja_gasto
				.getCodigo_articulo());
		datos_medicamentos.setCodigo_empresa(detalle_hoja_gasto
				.getCodigo_empresa());
		datos_medicamentos.setCodigo_sucursal(detalle_hoja_gasto
				.getCodigo_sucursal());
		datos_medicamentos.setCantidad_entregada(detalle_hoja_gasto
				.getCantidad());
		datos_medicamentos.setCodigo_bodega(detalle_hoja_gasto
				.getCodigo_bodega());
		datos_medicamentos.setCodigo_lote("");
		datos_medicamentos.setCancelo_copago("");
		datos_medicamentos.setConsecutivo("");
		datos_medicamentos.setCreacion_date(new Timestamp((new Date())
				.getTime()));
		datos_medicamentos.setCreacion_user(detalle_hoja_gasto
				.getCreacion_user());
		datos_medicamentos.setDescuento(detalle_hoja_gasto.getDescuento());
		datos_medicamentos.setIncremento(detalle_hoja_gasto.getIncremento());
		datos_medicamentos.setNro_factura("");
		datos_medicamentos.setUltimo_update(new Timestamp((new Date())
				.getTime()));
		datos_medicamentos.setUltimo_user(detalle_hoja_gasto.getUltimo_user());
		datos_medicamentos.setUnidades(detalle_hoja_gasto.getCantidad());
		datos_medicamentos.setValor_real(detalle_hoja_gasto.getValor_real());
		datos_medicamentos.setValor_total(detalle_hoja_gasto.getCantidad()
				.intValue() * detalle_hoja_gasto.getValor_unitario());
		datos_medicamentos.setValor_unitario(detalle_hoja_gasto
				.getValor_unitario());
		return datos_medicamentos;
	}

	private Detalle_hoja_gasto convertirMapADetalleHoja(
			Map<String, Object> map, Hoja_gastos hoja_gastos) {
		String consecutivo = (String) map.get("consecutivo");
		String codigo_articulo = (String) map.get("codigo_articulo");
		int cantidad = 0;
		Object objectCantidad = (Object) map.get("cantidad_recetada");
		if (objectCantidad != null) {
			if (objectCantidad instanceof Double) {
				cantidad = ((Double) objectCantidad).intValue();
			} else if (objectCantidad instanceof Integer) {
				cantidad = ((Integer) objectCantidad);
			}
			//log.info("cantidad ===> " + cantidad + " --- " + objectCantidad
					//+ " ---- " + objectCantidad.getClass().getName());
		}

		Double valor_unitario = (Double) map.get("valor_unitario");
		Double valor_real = (Double) map.get("valor_real");
		Double descuento = (Double) map.get("descuento");
		Double incremento = (Double) map.get("incremento");
		String bodega = (String) map.get("bodega");

		if (bodega == null)
			bodega = "01";

		Detalle_hoja_gasto detalle_hoja_gasto = new Detalle_hoja_gasto();
		detalle_hoja_gasto.setCodigo_empresa(hoja_gastos.getCodigo_empresa());
		detalle_hoja_gasto.setCodigo_sucursal(hoja_gastos.getCodigo_sucursal());
		detalle_hoja_gasto.setNro_ingreso(hoja_gastos.getNro_ingreso());
		detalle_hoja_gasto.setNro_identificacion(hoja_gastos
				.getNro_identificacion());
		detalle_hoja_gasto.setCodigo_articulo(codigo_articulo);
		detalle_hoja_gasto.setCantidad(cantidad);
		detalle_hoja_gasto.setValor_unitario(valor_unitario);
		detalle_hoja_gasto.setValor_real(valor_real);
		detalle_hoja_gasto.setDescuento(descuento);
		detalle_hoja_gasto.setIncremento(incremento);
		detalle_hoja_gasto.setCreacion_date(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		detalle_hoja_gasto.setUltimo_update(hoja_gastos.getUltimo_update());
		detalle_hoja_gasto.setCreacion_user(hoja_gastos.getUltimo_user());
		detalle_hoja_gasto.setUltimo_user(hoja_gastos.getUltimo_user());
		detalle_hoja_gasto.setCodigo_bodega(bodega);
		detalle_hoja_gasto.setConsecutivo(consecutivo != null ? consecutivo
				: "");
		return detalle_hoja_gasto;
	}

}