/*
 * Facturacion_medicamentoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.dao.Datos_medicamentosDao;
import healthmanager.modelo.dao.Facturacion_medicamentoDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Bodega;
import contaweb.modelo.bean.Bodega_articulo;
import contaweb.modelo.bean.Detalle_contabilizacion;
import contaweb.modelo.bean.Detalle_nota;
import contaweb.modelo.bean.Kardex;
import contaweb.modelo.bean.Nota_contable;
import contaweb.modelo.bean.Puc;
import contaweb.modelo.dao.ArticuloDao;
import contaweb.modelo.dao.BodegaDao;
import contaweb.modelo.dao.Bodega_articuloDao;
import contaweb.modelo.dao.Detalle_contabilizacionDao;
import contaweb.modelo.dao.Detalle_notaDao;
import contaweb.modelo.dao.KardexDao;
import contaweb.modelo.dao.Nota_contableDao;
import contaweb.modelo.dao.PucDao;
import contaweb.modelo.service.FacturacionService;

@Service("facturacion_medicamentoService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Facturacion_medicamentoService implements Serializable {

	private String limit;

	// private static Logger log = Logger
	// .getLogger(Facturacion_medicamentoService.class);

	@Autowired
	private Facturacion_medicamentoDao facturacion_medicamentoDao;
	@Autowired
	private Datos_medicamentosDao datos_medicamentosDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Bodega_articuloDao bodega_articuloDao;
	@Autowired
	private KardexDao kardexDao;

	@Autowired
	private Nota_contableDao nota_contableDao;
	@Autowired
	private ArticuloDao articuloDao;
	@Autowired
	private Detalle_notaDao detalle_notaDao;
	@Autowired
	private Detalle_contabilizacionDao detalle_contabilizacionDao;
	@Autowired
	private PucDao pucDao;

	@Autowired
	private BodegaDao bodegaDao;

	@Autowired
	private FacturacionService facturacionService;

	public synchronized void crear(
			Facturacion_medicamento facturacion_medicamento) {
		try {
			facturacion_medicamentoDao.crear(facturacion_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void guardarFacturacion(Map<String, Object> datos,
			boolean fact_aut) {
		try {
			Facturacion_medicamento facturacion_medicamento = (Facturacion_medicamento) datos
					.get("facturacion_medicamento");
			List<Datos_medicamentos> lista_medicamentos = (List<Datos_medicamentos>) datos
					.get("lista_medicamentos");
			String accion = (String) datos.get("accion");
			Boolean cont = (Boolean) datos.get("cont");
			Boolean afectar_kardex_fact = (Boolean) datos
					.get("afectar_kardex_fact");

			String nro_factura = "";
			if (accion.equalsIgnoreCase("registrar")) {
				facturacion_medicamentoDao.crear(facturacion_medicamento);
				nro_factura = facturacion_medicamento.getNro_factura();
			} else {
				nro_factura = facturacion_medicamento.getNro_factura();
				actualizar(facturacion_medicamento);
				if (afectar_kardex_fact) {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa",
							facturacion_medicamento.getCodigo_empresa());
					parametros.put("codigo_sucursal",
							facturacion_medicamento.getCodigo_sucursal());
					parametros.put("nro_factura", nro_factura);

					List<Datos_medicamentos> listado_medicamentos_aux = datos_medicamentosDao
							.listar(parametros);
					for (Datos_medicamentos datos_medicamentos : listado_medicamentos_aux) {
						Kardex kardex = new Kardex();
						kardex.setCodigo_empresa(facturacion_medicamento
								.getCodigo_empresa());
						kardex.setCodigo_sucursal(facturacion_medicamento
								.getCodigo_sucursal());
						kardex.setCodigo_fuente("15");
						kardex.setCodigo_documento(datos_medicamentos
								.getNro_factura());
						kardex.setCodigo_bodega(datos_medicamentos
								.getCodigo_bodega());
						kardex.setCodigo_articulo(datos_medicamentos
								.getCodigo_medicamento());
						kardex = kardexDao.consultar(kardex);

						Bodega_articulo bodega_articulo = new Bodega_articulo();
						bodega_articulo
								.setCodigo_empresa(facturacion_medicamento
										.getCodigo_empresa());
						bodega_articulo
								.setCodigo_sucursal(facturacion_medicamento
										.getCodigo_sucursal());
						bodega_articulo.setCodigo_bodega(datos_medicamentos
								.getCodigo_bodega());
						bodega_articulo.setCodigo_articulo(datos_medicamentos
								.getCodigo_medicamento());
						bodega_articulo = bodega_articuloDao
								.consultar(bodega_articulo);
						if (bodega_articulo != null && kardex != null) {
							bodega_articulo.setCantidad(bodega_articulo
									.getCantidad()
									+ datos_medicamentos.getUnidades());
							bodega_articuloDao.actualizar(bodega_articulo);
						}

						if (kardex != null) {
							kardexDao.eliminar(kardex);
						}
					}
				}
			}

			Datos_medicamentos datos_aux = new Datos_medicamentos();
			datos_aux.setCodigo_empresa(facturacion_medicamento
					.getCodigo_empresa());
			datos_aux.setCodigo_sucursal(facturacion_medicamento
					.getCodigo_sucursal());
			datos_aux.setNro_factura(nro_factura);

			datos_medicamentosDao.eliminar(datos_aux);

			int consecutivo = 1;
			for (Datos_medicamentos datos_medicamentos : lista_medicamentos) {
				datos_medicamentos.setNro_factura(nro_factura);
				datos_medicamentos.setConsecutivo(consecutivo + "");
				consecutivo++;
				datos_medicamentosDao.crear(datos_medicamentos);
				if (afectar_kardex_fact) {
					Bodega_articulo bodega_articulo = new Bodega_articulo();
					bodega_articulo.setCodigo_empresa(facturacion_medicamento
							.getCodigo_empresa());
					bodega_articulo.setCodigo_sucursal(facturacion_medicamento
							.getCodigo_sucursal());
					bodega_articulo.setCodigo_bodega(datos_medicamentos
							.getCodigo_bodega());
					bodega_articulo.setCodigo_articulo(datos_medicamentos
							.getCodigo_medicamento());
					bodega_articulo = (Bodega_articulo) bodega_articuloDao
							.consultar(bodega_articulo);

					if (bodega_articulo != null) {

						Kardex kardex = new Kardex();
						kardex.setCodigo_empresa(facturacion_medicamento
								.getCodigo_empresa());
						kardex.setCodigo_sucursal(facturacion_medicamento
								.getCodigo_sucursal());
						kardex.setCodigo_fuente("15");
						kardex.setCodigo_documento(nro_factura);
						kardex.setCreacion_date(datos_medicamentos
								.getCreacion_date());
						kardex.setUltimo_update(datos_medicamentos
								.getUltimo_update());
						kardex.setCreacion_user(datos_medicamentos
								.getCreacion_user());
						kardex.setUltimo_user(datos_medicamentos
								.getUltimo_user());
						kardex.setCodigo_bodega(datos_medicamentos
								.getCodigo_bodega());
						kardex.setCodigo_articulo(datos_medicamentos
								.getCodigo_medicamento());
						kardex.setFecha(facturacion_medicamento
								.getFecha_medicamento());
						kardex.setDetalle("Salida Fac # 15 -" + nro_factura
								+ " Por Facturacion de medicamentos");
						kardex.setUnidad_salida(datos_medicamentos
								.getUnidades());
						kardex.setValor_salida(bodega_articulo.getCosto());
						kardex.setTotal_salida(datos_medicamentos.getUnidades()
								* (bodega_articulo.getCosto()));
						kardex.setUnidad_total(bodega_articulo.getCantidad()
								- datos_medicamentos.getUnidades());
						kardex.setValor_unitario_total(bodega_articulo
								.getCosto());
						kardex.setValor_total(kardex.getUnidad_total()
								* kardex.getValor_unitario_total());

						// Este cambio en la opcion que le permite a la factura
						// cargar mas de un servicio
						// repetido
						Kardex kardex_temp = kardexDao.consultar(kardex);
						if (kardex_temp != null) {
							double total_salida = datos_medicamentos
									.getUnidades()
									* (bodega_articulo.getCosto());
							double unidad_total = bodega_articulo.getCantidad()
									- datos_medicamentos.getUnidades();
							double valor_total = kardex.getUnidad_total()
									* kardex.getValor_unitario_total();

							kardex_temp.setUnidad_salida(datos_medicamentos
									.getUnidades()
									+ kardex_temp.getUnidad_salida());
							kardex_temp.setTotal_salida(total_salida
									+ kardex_temp.getTotal_salida());
							kardex_temp.setUnidad_total(unidad_total
									+ kardex_temp.getUnidad_total());
							kardex_temp.setValor_total(valor_total
									+ kardex_temp.getValor_total());
							kardexDao.actualizar(kardex_temp);
						} else {
							kardexDao.crear(kardex);
						}

						bodega_articulo.setCosto(bodega_articulo.getCosto());
						bodega_articulo.setCantidad(bodega_articulo
								.getCantidad()
								- datos_medicamentos.getUnidades());
						bodega_articuloDao.actualizar(bodega_articulo);

					} else {
						throw new ValidacionRunTimeException(
								"El medicamento con codigo '"
										+ datos_medicamentos
												.getCodigo_medicamento()
										+ "' no tiene existencias en bodega ("
										+ datos_medicamentos.getCodigo_bodega()
										+ ")");
					}
				}
			}

			if (cont && afectar_kardex_fact) {
				Map<String, Object> param_cont = new HashMap<String, Object>();
				param_cont.put("codigo_empresa",
						facturacion_medicamento.getCodigo_empresa());
				param_cont.put("codigo_sucursal",
						facturacion_medicamento.getCodigo_sucursal());
				param_cont.put("codigo_documento", nro_factura);
				param_cont.put("codigo_comprobante", "12");
				guardarContabilizacionMedicamento(param_cont, true);
			}

			if (fact_aut) {
				facturacionService.actualizarFacturaAutomatico(
						facturacion_medicamento.getCodigo_empresa(),
						facturacion_medicamento.getCodigo_sucursal(),
						facturacion_medicamento.getNro_ingreso(),
						facturacion_medicamento.getNro_identificacion());
			}
		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Nota_contable guardarContabilizacionMedicamento(
			Map<String, Object> datos, boolean sw) {
		// log.info("Ejecutando el metodo @contabilizar_medicamento");
		try {
			String codigo_empresa = (String) datos.get("codigo_empresa");
			String codigo_sucursal = (String) datos.get("codigo_sucursal");
			String codigo_comprobante = (String) datos
					.get("codigo_comprobante");
			String codigo_documento = (String) datos.get("codigo_documento");
			String doc = "SM";
			String forma_pago = "06";

			Facturacion_medicamento inventario = new Facturacion_medicamento();
			inventario.setCodigo_empresa(codigo_empresa);
			inventario.setCodigo_sucursal(codigo_sucursal);
			inventario.setNro_factura(codigo_documento);
			inventario = facturacion_medicamentoDao.consultar(inventario);

			Map<String, Object> datosMedicamentos = new HashMap<String, Object>();
			datosMedicamentos.put("codigo_empresa", codigo_empresa);
			datosMedicamentos.put("codigo_sucursal", codigo_sucursal);
			datosMedicamentos.put("nro_factura", inventario.getNro_factura());
			List<Datos_medicamentos> lista_detalle = datos_medicamentosDao
					.listar(datosMedicamentos);

			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(inventario.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(inventario.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(codigo_comprobante);
			nota_contable.setCodigo_documento(inventario.getNro_factura());
			nota_contable.setPrefijo("");
			nota_contable.setAnio(new java.text.SimpleDateFormat("yyyy")
					.format(inventario.getFecha_medicamento()));
			nota_contable.setMes(new java.text.SimpleDateFormat("MM")
					.format(inventario.getFecha_medicamento()));
			nota_contable.setFecha(inventario.getFecha_medicamento());
			nota_contable.setElaboro("");
			nota_contable.setCodigo_tercero(inventario.getNro_identificacion());
			nota_contable.setClasificacion("");
			nota_contable.setConcepto("FACTURACION DE "
					+ (inventario.getTipo().equals("01") ? "MEDICAMENTOS"
							: "MATERIALES"));
			nota_contable.setVerificado("");
			nota_contable.setCreacion_date(inventario.getCreacion_date());
			nota_contable.setUltimo_update(inventario.getUltimo_update());
			nota_contable.setCreacion_user(inventario.getCreacion_user());
			nota_contable.setUltimo_user(inventario.getUltimo_user());
			nota_contable.setEstado("CANC");
			nota_contable.setForma_pago("");
			nota_contable.setDocumento_externo("");
			nota_contable.setBanco("");
			nota_contable.setMedio_pago("");

			if (sw) {
				if (nota_contableDao.consultar(nota_contable) != null) {
					nota_contableDao.eliminar(nota_contable);
				}
				if (!lista_detalle.isEmpty()) {
					nota_contableDao.crear(nota_contable);
				}

			}

			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < lista_detalle.size(); i++) {
				Datos_medicamentos aux = (Datos_medicamentos) lista_detalle
						.get(i);
				double saldo = aux.getValor_total();
				double costo = 0.0;

				Bodega bodega = new Bodega();
				bodega.setCodigo_empresa(aux.getCodigo_empresa());
				bodega.setCodigo_sucursal(aux.getCodigo_sucursal());
				bodega.setCodigo(aux.getCodigo_bodega());
				bodega = bodegaDao.consultar(bodega);
				String cuenta_bodega_inventario = (bodega != null ? bodega
						.getCuenta_inventario() : "");
				String cuenta_bodega_costo = (bodega != null ? bodega
						.getCuenta_costo() : "");

				Kardex kardex = new Kardex();
				kardex.setCodigo_empresa(aux.getCodigo_empresa());
				kardex.setCodigo_sucursal(aux.getCodigo_sucursal());
				kardex.setCodigo_fuente("15");
				kardex.setCodigo_documento(aux.getNro_factura());
				kardex.setCodigo_bodega(aux.getCodigo_bodega());
				kardex.setCodigo_articulo(aux.getCodigo_medicamento());
				kardex = kardexDao.consultar(kardex);
				costo = (kardex != null ? kardex.getTotal_salida() : (aux
						.getCantidad_entregada() * aux.getValor_unitario()));

				String c_costo = "";

				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(aux.getCodigo_empresa());
				articulo.setCodigo_sucursal(aux.getCodigo_sucursal());
				articulo.setCodigo_articulo(aux.getCodigo_medicamento());
				articulo = articuloDao.consultar(articulo);

				String codigo_plantilla = (articulo != null ? articulo
						.getCodigo_contabilidad() : "");
				Map<String, Object> paramDetalle_cont = new HashMap<String, Object>();
				paramDetalle_cont.put("codigo_empresa", codigo_empresa);
				paramDetalle_cont.put("codigo_sucursal", codigo_sucursal);
				paramDetalle_cont.put("doc", doc);
				paramDetalle_cont.put("forma_pago", forma_pago);
				paramDetalle_cont.put("codigo", codigo_plantilla);
				List<Detalle_contabilizacion> lista_detalle_cont = detalle_contabilizacionDao
						.listar(paramDetalle_cont);

				for (int j = 0; j < lista_detalle_cont.size(); j++) {
					c_costo = "";
					Detalle_contabilizacion aux2 = lista_detalle_cont.get(j);
					Puc puc = new Puc();
					puc.setCodigo_empresa(codigo_empresa);
					puc.setCodigo_sucursal(codigo_sucursal);
					puc.setCodigo_cuenta(aux2.getCodigo_cuenta());
					puc = pucDao.consultar(puc);
					String tipo_cuenta = (puc != null ? puc.getTipo_cuenta()
							: "");
					String codigo_cuenta = aux2.getCodigo_cuenta();
					double valor = 0;

					if (tipo_cuenta.equals("05")) {// Ingresos
						valor = saldo;
						c_costo = inventario.getC_costo();
					} else if (tipo_cuenta.equals("11")) {// costos
						valor = costo;
						c_costo = inventario.getC_costo();
						if (!cuenta_bodega_costo.isEmpty()) {
							codigo_cuenta = cuenta_bodega_costo;
						}
					} else if (tipo_cuenta.equals("13")) {// Gastos
						valor = saldo;
						c_costo = inventario.getC_costo();
					} else if (tipo_cuenta.equals("04")) {// Inventario
						valor = costo;
						if (!cuenta_bodega_inventario.isEmpty()) {
							codigo_cuenta = cuenta_bodega_inventario;
						}
					} else {
						valor = saldo;
					}

					if (map.get(codigo_cuenta + aux2.getMenajo() + c_costo) == null) {
						Detalle_nota detalle_nota = new Detalle_nota();
						detalle_nota.setCodigo_empresa(codigo_empresa);
						detalle_nota.setCodigo_sucursal(codigo_sucursal);
						detalle_nota.setCodigo_comprobante(codigo_comprobante);
						detalle_nota.setCodigo_documento(codigo_documento);
						detalle_nota.setCreacion_date(nota_contable
								.getCreacion_date());
						detalle_nota.setUltimo_update(nota_contable
								.getUltimo_update());
						detalle_nota.setCreacion_user(nota_contable
								.getCreacion_user());
						detalle_nota.setUltimo_user(nota_contable
								.getUltimo_user());
						detalle_nota.setCodigo_cuenta(codigo_cuenta);
						detalle_nota
								.setConcepto("FACTURACION DE "
										+ (inventario.getTipo().equals("01") ? "MEDICAMENTOS"
												: "MATERIALES"));
						detalle_nota.setTercero(inventario
								.getNro_identificacion());
						detalle_nota.setCheque("");
						detalle_nota
								.setDebito(aux2.getMenajo().equals("01") ? valor
										: 0);
						detalle_nota
								.setCredito(aux2.getMenajo().equals("02") ? valor
										: 0);
						detalle_nota.setC_costo(c_costo);
						detalle_nota.setAbona("");
						detalle_nota
								.setVence(inventario.getFecha_medicamento());
						detalle_nota.setBanco("");
						detalle_nota.setIva(0);
						detalle_nota.setCheque_consignado("N");
						detalle_nota.setTipo("");
						detalle_nota.setConcepto_abona("");
						map.put(codigo_cuenta + aux2.getMenajo() + c_costo,
								detalle_nota);
					} else {
						Detalle_nota detalle_nota = (Detalle_nota) map
								.get(codigo_cuenta + aux2.getMenajo() + c_costo);
						detalle_nota.setDebito(detalle_nota.getDebito()
								+ (aux2.getMenajo().equals("01") ? valor : 0));
						detalle_nota.setCredito(detalle_nota.getCredito()
								+ (aux2.getMenajo().equals("02") ? valor : 0));
						map.put(codigo_cuenta + aux2.getMenajo() + c_costo,
								detalle_nota);
					}
				}

			}

			int index = 0;
			double dbt = 0, cdt = 0;
			for (String key_map : map.keySet()) {
				Detalle_nota detalle_nota = (Detalle_nota) map.get(key_map);
				// System.out.println(detalle_nota.getCodigo_cuenta()+":"+detalle_nota.getDebito()+"   "+detalle_nota.getCredito());
				dbt += detalle_nota.getDebito();
				cdt += detalle_nota.getCredito();
			}
			dbt = Double
					.parseDouble(String.format(Locale.ENGLISH, "%.2f", dbt));
			cdt = Double
					.parseDouble(String.format(Locale.ENGLISH, "%.2f", cdt));
			/*
			 * System.out.println("dbt: "+dbt); System.out.println("cdt: "+cdt);
			 */
			if ((dbt == 0 && cdt == 0) && sw && !lista_detalle.isEmpty()) {
				throw new Exception(
						"Los saldos del asiento contable "
								+ nota_contable.getCodigo_documento()
								+ " suma 0. Revise la contabilizacion de los servicios del detalle del documento");
			}
			if ((dbt != cdt) && sw && !lista_detalle.isEmpty()) {
				throw new Exception(
						"Este documento esta descuadrado. Revise la contabilizacion de los servicios del detalle del documento "
								+ nota_contable.getCodigo_documento() + "");
			}

			List<Detalle_nota> listaDetalle_notas = new ArrayList<Detalle_nota>();
			for (String key_map : map.keySet()) {
				Detalle_nota detalle_nota = (Detalle_nota) map.get(key_map);
				detalle_nota.setConsecutivo(index + "");
				listaDetalle_notas.add(detalle_nota);
				if (sw && !lista_detalle.isEmpty()) {
					detalle_notaDao.crear(detalle_nota);
				}

				index++;
			}

			nota_contable.setLista_detalle(listaDetalle_notas);

			return nota_contable;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Facturacion_medicamento facturacion_medicamento) {
		try {
			return facturacion_medicamentoDao
					.actualizar(facturacion_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Facturacion_medicamento consultar(
			Facturacion_medicamento facturacion_medicamento) {
		try {
			return facturacion_medicamentoDao
					.consultar(facturacion_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Map<String, Object> datos, boolean fact_aut) {
		try {
			// Boolean cont = (Boolean) datos.get("cont");
			Boolean afectar_kardex_fact = (Boolean) datos
					.get("afectar_kardex_fact");

			Facturacion_medicamento facturacion_medicamento = (Facturacion_medicamento) datos
					.get("facturacion_medicamento");

			if (afectar_kardex_fact) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa",
						facturacion_medicamento.getCodigo_empresa());
				parametros.put("codigo_sucursal",
						facturacion_medicamento.getCodigo_sucursal());
				parametros.put("nro_factura",
						facturacion_medicamento.getNro_factura());

				List<Datos_medicamentos> listado_medicamentos_aux = datos_medicamentosDao
						.listar(parametros);
				for (Datos_medicamentos datos_medicamentos : listado_medicamentos_aux) {
					Kardex kardex = new Kardex();
					kardex.setCodigo_empresa(facturacion_medicamento
							.getCodigo_empresa());
					kardex.setCodigo_sucursal(facturacion_medicamento
							.getCodigo_sucursal());
					kardex.setCodigo_fuente("15");
					kardex.setCodigo_documento(datos_medicamentos
							.getNro_factura());
					kardex.setCodigo_bodega(datos_medicamentos
							.getCodigo_bodega());
					kardex.setCodigo_articulo(datos_medicamentos
							.getCodigo_medicamento());
					kardex = kardexDao.consultar(kardex);

					Bodega_articulo bodega_articulo = new Bodega_articulo();
					bodega_articulo.setCodigo_empresa(facturacion_medicamento
							.getCodigo_empresa());
					bodega_articulo.setCodigo_sucursal(facturacion_medicamento
							.getCodigo_sucursal());
					bodega_articulo.setCodigo_bodega(datos_medicamentos
							.getCodigo_bodega());
					bodega_articulo.setCodigo_articulo(datos_medicamentos
							.getCodigo_medicamento());
					bodega_articulo = bodega_articuloDao
							.consultar(bodega_articulo);
					if (bodega_articulo != null && kardex != null) {
						bodega_articulo.setCantidad(bodega_articulo
								.getCantidad()
								+ datos_medicamentos.getUnidades());
						bodega_articuloDao.actualizar(bodega_articulo);
					}

					if (kardex != null) {
						kardexDao.eliminar(kardex);
					}
				}

				Nota_contable nota_contable = new Nota_contable();
				nota_contable.setCodigo_empresa(facturacion_medicamento
						.getCodigo_empresa());
				nota_contable.setCodigo_sucursal(facturacion_medicamento
						.getCodigo_sucursal());
				nota_contable.setCodigo_documento(facturacion_medicamento
						.getNro_factura());
				nota_contable.setCodigo_comprobante("12");
				nota_contable = nota_contableDao.consultar(nota_contable);
				if (nota_contable != null) {
					nota_contableDao.eliminar(nota_contable);
				}
			}

			int result = facturacion_medicamentoDao
					.eliminar(facturacion_medicamento);

			if (fact_aut) {
				facturacionService.actualizarFacturaAutomatico(
						facturacion_medicamento.getCodigo_empresa(),
						facturacion_medicamento.getCodigo_sucursal(),
						facturacion_medicamento.getNro_ingreso(),
						facturacion_medicamento.getNro_identificacion());
			}
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Facturacion_medicamento> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return facturacion_medicamentoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
