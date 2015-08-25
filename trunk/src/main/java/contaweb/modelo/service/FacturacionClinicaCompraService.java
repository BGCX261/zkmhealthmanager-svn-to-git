package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Detalle_anexo4;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.dao.Anexo4_entidadDao;
import healthmanager.modelo.dao.ContratosDao;
import healthmanager.modelo.dao.Datos_consultaDao;
import healthmanager.modelo.dao.Datos_medicamentosDao;
import healthmanager.modelo.dao.Datos_procedimientoDao;
import healthmanager.modelo.dao.Datos_servicioDao;
import healthmanager.modelo.dao.Detalle_anexo4Dao;
import healthmanager.modelo.dao.ProcedimientosDao;
import healthmanager.modelo.service.Facturacion_servicioService;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.res.Res;

import contaweb.modelo.bean.Cartera;
import contaweb.modelo.bean.Detalle_contabilizacion;
import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Detalle_nota;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Impuesto;
import contaweb.modelo.bean.Nota_contable;
import contaweb.modelo.bean.Resolucion;
import contaweb.modelo.bean.Tercero;
import contaweb.modelo.dao.CarteraDao;
import contaweb.modelo.dao.Detalle_contabilizacionDao;
import contaweb.modelo.dao.Detalle_facturaDao;
import contaweb.modelo.dao.Detalle_notaDao;
import contaweb.modelo.dao.FacturacionDao;
import contaweb.modelo.dao.ImpuestoDao;
import contaweb.modelo.dao.Nota_contableDao;
import contaweb.modelo.dao.PucDao;
import contaweb.modelo.dao.ResolucionDao;
import contaweb.modelo.dao.TerceroDao;

@Service("facturacionClinicaCompraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class FacturacionClinicaCompraService implements Serializable{

	private static Logger log = Logger
			.getLogger(FacturacionClinicaCompraService.class);

	private String limit;

	@Autowired
	private FacturacionDao facturacionDao;
	@Autowired
	private Detalle_facturaDao detalle_facturaDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Nota_contableDao nota_contableDao;
	@Autowired
	private Detalle_notaDao detalle_notaDao;
	@Autowired
	private Detalle_contabilizacionDao detalle_contabilizacionDao;
	@Autowired
	private ResolucionDao resolucionDao;
	@Autowired
	private CarteraDao carteraDao;
	@Autowired
	private TerceroDao terceroDao;
	@Autowired
	private PucDao pucDao;
	@Autowired
	private ImpuestoDao impuestoDao;

	@Autowired
	private Datos_consultaDao datos_consultaDao;
	@Autowired
	private Datos_procedimientoDao datos_procedimientoDao;
	@Autowired
	private Datos_medicamentosDao datos_medicamentosDao;
	@Autowired
	private Datos_servicioDao datos_servicioDao;
	@Autowired
	private ContratosDao contratosDao;

	@Autowired
	private ProcedimientosDao procedimientosDao;

	@Autowired
	private Anexo4_entidadDao anexo4_entidadDao;
	@Autowired
	private Detalle_anexo4Dao detalle_anexo4Dao;

	@Autowired
	private Nota_contableService nota_contableService;

	@Autowired
	private Facturacion_servicioService facturacion_servicioService;

	public Nota_contable guardarContabilizacion(Map<String, Object> datos,boolean sw) {
		try {
			Long id_factura = (Long)datos.get("id_factura");
			String tipo = (String) datos.get("tipo");
			String doc = "PCD";
			String forma_pago = "02";

			Facturacion facturacion = new Facturacion();
			facturacion.setId_factura(id_factura);
			facturacion = facturacionDao.consultar(facturacion);

			if (facturacion == null && sw) {
				throw new Exception("Este documento no se ha guardado todavia");
			}
			if (!facturacion.getTipo().equals("FHC") && !facturacion.getTipo().equals("FCC")) {
				throw new Exception("Esta factura no es clinica por lo tanto no se puede contabilizar");
			}
			if (facturacion != null) {
				tipo = facturacion.getTipo();
			}

			String codigo_empresa = facturacion.getCodigo_empresa();
			String codigo_sucursal = facturacion.getCodigo_sucursal();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("id_factura", facturacion.getId_factura());
			List<Detalle_factura> lista_detalle = (List<Detalle_factura>) datos.get("lista_detalle");
			if (sw) {
				lista_detalle = detalle_facturaDao.listar(parameters);
			} else if (facturacion != null) {
				lista_detalle = detalle_facturaDao.listar(parameters);
			}
			// log.info("lista_detalle: " + lista_detalle);

			Resolucion res = new Resolucion();
			res.setCodigo_empresa(facturacion.getCodigo_empresa());
			res = resolucionDao.consultar(res);

			String cuenta_glosa = (res != null ? res.getCuenta_glosa() : "");
			Tercero tercero = new Tercero();
			tercero.setCodigo_empresa(codigo_empresa);
			tercero.setCodigo_sucursal(codigo_sucursal);
			tercero.setNro_identificacion(facturacion.getCodigo_tercero());
			tercero = terceroDao.consultar(tercero);
			// log.info("tercero:" + tercero);

			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(codigo_empresa);
			nota_contable.setCodigo_sucursal(codigo_sucursal);
			nota_contable.setCodigo_comprobante(facturacion
					.getCodigo_comprobante());
			nota_contable
					.setCodigo_documento(facturacion.getCodigo_documento_res());
			nota_contable.setPrefijo(facturacion.getPrefijo());
			nota_contable.setAnio(facturacion.getAnio());
			nota_contable.setMes(facturacion.getMes());
			nota_contable.setFecha(facturacion.getFecha());
			nota_contable
					.setElaboro(facturacion.getCodigo_empleado() != null ? facturacion
							.getCodigo_empleado() : "");
			nota_contable.setCodigo_tercero(facturacion.getCodigo_tercero());
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

			Contratos plan = new Contratos();
			plan.setCodigo_empresa(codigo_empresa);
			plan.setCodigo_sucursal(codigo_sucursal);
			plan.setCodigo_administradora(facturacion
					.getCodigo_administradora());
			plan.setId_plan(facturacion.getId_plan());
			plan = contratosDao.consultar(plan);

			Map<String, Object> paramDetalle_factura = new HashMap<String, Object>();
			paramDetalle_factura.put("codigo_empresa", codigo_empresa);
			paramDetalle_factura.put("codigo_sucursal", codigo_sucursal);
			paramDetalle_factura.put("codigo_comprobante",
					(facturacion != null ? facturacion.getCodigo_comprobante()
							: ""));
			paramDetalle_factura.put("codigo_documento",
					(facturacion != null ? facturacion.getCodigo_documento_res()
							: ""));
			paramDetalle_factura.put("facturable", new Boolean(true));

			Map<String, Object> map = new HashMap<String, Object>();
			// double total = facturacion.getValor_total() -
			// facturacion.getCop_canc();
			Double result = detalle_facturaDao
					.totalFacturaClinica(paramDetalle_factura)
					- facturacion.getCop_canc();
			double total = (result != null ? result : 0.0);
			double copago = facturacion.getCop_canc();
			
			double retencion = 0;
			Impuesto impuesto = new Impuesto();
			impuesto.setCodigo_empresa(codigo_empresa);
			impuesto.setCodigo_sucursal(codigo_sucursal);
			impuesto.setCodigo_cuenta((tercero != null ? tercero.getCuenta_retencion() : ""));
			impuesto.setAnio(facturacion.getAnio());
			impuesto = impuestoDao.consultar(impuesto);
			if (impuesto != null) {
				if (impuesto.getBase() <= total) {
					retencion = total * impuesto.getPorcentaje();
				}
			}
			retencion = (Math.rint(retencion*1)/1);
			
			double reteica = 0;
	        impuesto = new Impuesto();
	        impuesto.setCodigo_empresa(codigo_empresa);
	        impuesto.setCodigo_sucursal(codigo_sucursal);
	        impuesto.setCodigo_cuenta((tercero != null ? tercero.getCuenta_reteica() : ""));
	        impuesto.setAnio(facturacion.getAnio());
	        impuesto = impuestoDao.consultar(impuesto);
	        if (impuesto != null) {
	            if (impuesto.getBase() <= total) {
	            	reteica = total * impuesto.getPorcentaje();
	            }
	        }
	        reteica = (Math.rint(reteica*1)/1);

			double valor_glosa = facturacion.getValor_glosa_aceptada() <= 0 ? facturacion
					.getValor_glosa_inicial() : facturacion
					.getValor_glosa_aceptada();

			double descuento_glosa = (total - retencion-reteica - (tipo.equals("FCC") ? total
					: (facturacion.getValor_pagar_factura()-retencion-reteica - valor_glosa)));
			// log.info("descuento glosa: " + descuento_glosa);

			int consecutivo = 0;

			if (tercero != null) {
				if (tercero.getCuenta_pagar().trim().equals("")) {
					throw new Exception("La aseguradora "
							+ tercero.getNombre1()
							+ " debe tener una cuenta por pagar asignada");
				}
				if (descuento_glosa < 0) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar el valor a pagar de es mayor que el valor neto de la factura");
				}

				Detalle_nota detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(facturacion.getCodigo_comprobante());
				detalle_nota.setCodigo_documento(facturacion.getCodigo_documento_res());
				detalle_nota.setConsecutivo(consecutivo + "");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota.setCodigo_cuenta(tercero.getCuenta_pagar());
				detalle_nota.setConcepto(facturacion.getDescripcion());
				detalle_nota.setTercero(facturacion.getCodigo_tercero());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(0.0);
				detalle_nota.setCredito(total - retencion-reteica - descuento_glosa);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("FC " + facturacion.getCodigo_comprobante() + "-"
						+ Integer.parseInt(facturacion.getCodigo_documento_res()));
				detalle_nota.setVence(facturacion.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				map.put(tercero.getCuenta_pagar() + "01", detalle_nota);

				if (!tercero.getCuenta_retencion().trim().equals("")
						&& retencion > 0) {
					detalle_nota = new Detalle_nota();
					detalle_nota.setCodigo_empresa(codigo_empresa);
					detalle_nota.setCodigo_sucursal(codigo_sucursal);
					detalle_nota.setCodigo_comprobante(facturacion.getCodigo_comprobante());
					detalle_nota.setCodigo_documento(facturacion.getCodigo_documento_res());
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
					detalle_nota.setConcepto("RETENCION COMPRA");
					detalle_nota.setTercero(facturacion.getCodigo_tercero());
					detalle_nota.setCheque("");
					detalle_nota.setDebito(0);
					detalle_nota.setCredito(retencion);
					detalle_nota.setC_costo("");
					detalle_nota.setAbona("");
					detalle_nota.setVence(facturacion.getFecha());
					detalle_nota.setBanco("");
					detalle_nota.setIva(0);
					detalle_nota.setCheque_consignado("N");
					detalle_nota.setTipo("");
					detalle_nota.setConcepto_abona("");
					// detalle_notaDao.crear(detalle_nota);
					map.put(tercero.getCuenta_retencion() + "01", detalle_nota);
				}
				
				if (!tercero.getCuenta_reteica().trim().equals("") && reteica>0) {
	            	detalle_nota = new Detalle_nota();
	                detalle_nota.setCodigo_empresa(codigo_empresa);
	                detalle_nota.setCodigo_sucursal(codigo_sucursal);
	                detalle_nota.setCodigo_comprobante(facturacion.getCodigo_comprobante());
	                detalle_nota.setCodigo_documento(facturacion.getCodigo_documento_res());
	                detalle_nota.setConsecutivo(consecutivo + "");
	                detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
	                detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
	                detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
	                detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
	                detalle_nota.setCodigo_cuenta(tercero.getCuenta_reteica());
	                detalle_nota.setConcepto("RETEICA COMPRA");
	                detalle_nota.setTercero(facturacion.getCodigo_tercero());
	                detalle_nota.setCheque("");
	                detalle_nota.setDebito(0);
	                detalle_nota.setCredito(reteica);
	                detalle_nota.setC_costo("");
	                detalle_nota.setAbona("");
	                detalle_nota.setVence(facturacion.getFecha());
	                detalle_nota.setBanco("");
                    detalle_nota.setIva(0);
                    detalle_nota.setCheque_consignado("N");
                    detalle_nota.setTipo("");
                    detalle_nota.setConcepto_abona("");
	                //detalle_notaDao.crear(detalle_nota);
	                map.put(tercero.getCuenta_reteica() + "01", detalle_nota);
	            }

				if (descuento_glosa > 0) {
					if (cuenta_glosa.equals("")) {
						throw new Exception(
								"El documento "
										+ facturacion.getCodigo_documento_res()
										+ " no se pudo contabilizar configure una cuenta de glosa en parametros de sistema en contaweb");
					}
					if (facturacion.getValor_pagar_factura() <= 0 && sw) {
						throw new Exception(
								"El documento "
										+ facturacion.getCodigo_documento_res()
										+ " no se pudo contabilizar el valor a pagar de es menor o igual a cero");
					}
					detalle_nota = new Detalle_nota();
					detalle_nota.setCodigo_empresa(codigo_empresa);
					detalle_nota.setCodigo_sucursal(codigo_sucursal);
					detalle_nota.setCodigo_comprobante(facturacion.getCodigo_comprobante());
					detalle_nota.setCodigo_documento(facturacion.getCodigo_documento_res());
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
					detalle_nota.setTercero(facturacion.getCodigo_tercero());
					detalle_nota.setCheque("");
					detalle_nota.setDebito(0);
					detalle_nota.setCredito(descuento_glosa);
					detalle_nota.setC_costo("");
					detalle_nota.setAbona("");
					detalle_nota.setVence(facturacion.getFecha());
					detalle_nota.setBanco("");
					detalle_nota.setIva(0);
					detalle_nota.setCheque_consignado("N");
					detalle_nota.setTipo("");
					detalle_nota.setConcepto_abona("");
					// detalle_notaDao.crear(detalle_nota);
					map.put(tercero.getCuenta_retencion() + "01", detalle_nota);

					// consecutivo++;
				}
			}
			String manual = "ISS01";
			// FIN DE TERCERO != NULL
			for (Detalle_factura detalle_factura : lista_detalle) {

				if (tipo.equals("FCC")) {
					Detalle_anexo4 detalle_anexo4 = new Detalle_anexo4();
					detalle_anexo4.setCodigo_empresa(detalle_factura
							.getCodigo_empresa());
					detalle_anexo4.setCodigo_sucursal(detalle_factura
							.getCodigo_sucursal());
					detalle_anexo4.setCodigo_orden(detalle_factura
							.getCodigo_anexo4());
					detalle_anexo4.setCodigo_procedimiento(detalle_factura
							.getCodigo_articulo());
					detalle_anexo4 = detalle_anexo4Dao
							.consultarPcd(detalle_anexo4);
					if (detalle_anexo4 != null) {
						manual = (detalle_anexo4.getManual_tarifario() != null ? (!detalle_anexo4
								.getManual_tarifario().isEmpty() ? detalle_anexo4
								.getManual_tarifario() : "ISS01")
								: "ISS01");
					}
				}

				String codigo_plantilla = obtenerCodigoPlantilla(
						detalle_factura, res, manual);
				double dto = (((detalle_factura.getValor_total() * 100) / (total + copago)) / 100)
						* copago;
				double valor = detalle_factura.getValor_total() - dto;

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
				List lista_detalle_cont = detalle_contabilizacionDao
						.listar(paramDetalle_cont);
				// log.info("lista_detalle_cont: " + lista_detalle_cont);
				for (int j = 0; j < lista_detalle_cont.size(); j++) {
					Detalle_contabilizacion aux2 = (Detalle_contabilizacion) lista_detalle_cont
							.get(j);
					String codigo_cuenta = aux2.getCodigo_cuenta();
					String manejo = aux2.getMenajo();
					if (map.get(codigo_cuenta + manejo) == null) {
						if (valor != 0) {
							// //System.Out.Println("Codigo_cuenta: " +
							// codigo_cuenta);
							Detalle_nota detalle_nota = new Detalle_nota();
							detalle_nota.setCodigo_empresa(codigo_empresa);
							detalle_nota.setCodigo_sucursal(codigo_sucursal);
							detalle_nota
									.setCodigo_comprobante(facturacion.getCodigo_comprobante());
							detalle_nota.setCodigo_documento(facturacion.getCodigo_documento_res());
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
									.getCodigo_tercero());
							detalle_nota.setCheque("");
							detalle_nota.setDebito(aux2.getMenajo()
									.equals("01") ? valor : 0);
							detalle_nota.setCredito(aux2.getMenajo().equals(
									"02") ? valor : 0);
							detalle_nota.setC_costo("");
							detalle_nota.setAbona("");
							detalle_nota.setVence(facturacion.getFecha());
							detalle_nota.setBanco("");
							detalle_nota.setIva(0);
							detalle_nota.setCheque_consignado("N");
							detalle_nota.setTipo("");
							detalle_nota.setConcepto_abona("");

							map.put(aux2.getCodigo_cuenta() + manejo,
									detalle_nota);
						}
					} else {
						Detalle_nota detalle_nota = (Detalle_nota) map
								.get(codigo_cuenta + manejo);
						detalle_nota.setDebito(detalle_nota.getDebito()
								+ (aux2.getMenajo().equals("01") ? valor : 0));
						detalle_nota.setCredito(detalle_nota.getCredito()
								+ (aux2.getMenajo().equals("02") ? valor : 0));
						map.put(aux2.getCodigo_cuenta() + manejo, detalle_nota);
					}
				}

			}
			// Fin de armar contabilizaciones //
			Iterator it = map.entrySet().iterator();
			int index = 1;
			double dbt = 0, cdt = 0;
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Detalle_nota detalle_nota = (Detalle_nota) e.getValue();
				detalle_nota.setDebito(Double.parseDouble(String.format(
						Locale.ENGLISH, "%.2f", detalle_nota.getDebito())));
				detalle_nota.setCredito(Double.parseDouble(String.format(
						Locale.ENGLISH, "%.2f", detalle_nota.getCredito())));
				// //System.Out.Println(detalle_nota.getCodigo_cuenta()+":"+detalle_nota.getDebito()+"   "+detalle_nota.getCredito());
				dbt += detalle_nota.getDebito();
				cdt += detalle_nota.getCredito();
			}
			dbt = Double.parseDouble(String.format(Locale.ENGLISH, "%.2f", dbt));
			cdt = Double.parseDouble(String.format(Locale.ENGLISH, "%.2f", cdt));
			
			//log.info("dbt: "+dbt);
			//log.info("cdt: "+cdt);

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
				it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry e = (Map.Entry) it.next();
					Detalle_nota detalle_nota = (Detalle_nota) e.getValue();
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
				throw new Exception(
						"Los saldos  "
								+ facturacion.getCodigo_documento_res()
								+ " del asiento contable suma 0. Revise la contabilizacion de los servicios del detalle del documento");
			}
			if ((dbt != cdt) && sw) {
				throw new Exception(
						"El documento "
								+ facturacion.getCodigo_documento_res()
								+ " esta descuadrado. Revise la contabilizacion de los servicios del detalle del documento");
			}

			List<Detalle_nota> lista_detalle_nota = new LinkedList<Detalle_nota>();
			it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Detalle_nota detalle_nota = (Detalle_nota) e.getValue();
				detalle_nota.setConsecutivo(index + "");
				lista_detalle_nota.add(detalle_nota);
				if (sw) {
					detalle_notaDao.crear(detalle_nota);
				}
				index++;
			}

			Cartera cartera = new Cartera();
			cartera.setCodigo_empresa(codigo_empresa);
			cartera.setCodigo_sucursal(codigo_sucursal);
			cartera.setCodigo_comprobante(facturacion.getCodigo_comprobante());
			cartera.setCodigo_documento(facturacion.getCodigo_documento_res());
			cartera.setNro_cuota("1");
			cartera.setVencimiento(facturacion.getFecha_vencimiento());
			cartera.setAbono(0);
			cartera.setSaldos(total - retencion-reteica - descuento_glosa);
			cartera.setTotal(total - retencion-reteica - descuento_glosa);
			cartera.setTipo("FH");
			cartera.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cartera.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cartera.setCreacion_user(nota_contable.getCreacion_user());
			cartera.setUltimo_user(nota_contable.getCreacion_user());
			cartera.setManejo("CP");
			cartera.setCodigo_tercero(facturacion.getCodigo_administradora());
			cartera.setCuenta((tercero != null ? tercero.getCuenta_pagar() : ""));
			cartera.setConcepto("");
			if (carteraDao.consultar(cartera) != null) {
				cartera = carteraDao.consultar(cartera);
				cartera.setSaldos(total - retencion-reteica - descuento_glosa);
				cartera.setTotal(total - retencion-reteica - descuento_glosa);
				if (cartera.getSaldos() - cartera.getAbono() < 0) {
					throw new Exception(
							"La factura "
									+ facturacion.getCodigo_documento_res()
									+ " ya se le ha hecho abonos que superan el nuevo saldo");
				}
				cartera.setSaldos(cartera.getSaldos() - cartera.getAbono());
				if (sw) {
					carteraDao.actualizar(cartera);
				}
			} else {
				if (sw) {
					carteraDao.crear(cartera);
				}
			}

			if (sw) {
				facturacion.setEstado("CONT");
				facturacionDao.actualizar(facturacion);
			}

			return nota_contable;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private String obtenerCodigoPlantilla(Detalle_factura detalle_factura,
			Resolucion res, String manual) throws Exception {
		String codigo_plantilla = "";
		// Verificacion de consulta o procedimiento //
		if (detalle_factura.getTipo().equals("CONSULTA")
				|| detalle_factura.getTipo().equals("PROCEDIMIENTO")) {
			Procedimientos pd = new Procedimientos();
			pd.setId_procedimiento(new Long(detalle_factura
					.getCodigo_articulo()));
			pd.setCodigo_cups(detalle_factura.getCodigo_articulo());
			pd = procedimientosDao.consultar(pd);
			codigo_plantilla = (pd != null ? pd.getCodigo_contabilidad() : "");
			if (codigo_plantilla.trim().equals("")) {
				throw new Exception("El procedimiento "
						+ (pd != null ? pd.getDescripcion() : "")
						+ " no tiene contabilizacion asignada");
			}

		}
		// Verificacion de medicamento //
		else if (detalle_factura.getTipo().equals("MEDICAMENTO")) {
			codigo_plantilla = res.getCodigo_contabilidad_medicamentos();
		}
		// Verificacion de otros servicios //
		else if (detalle_factura.getTipo().equals("SERVICIO")) {
			Facturacion_servicio facturacion_servicio = new Facturacion_servicio();
			facturacion_servicio.setCodigo_empresa(detalle_factura
					.getCodigo_empresa());
			facturacion_servicio.setCodigo_sucursal(detalle_factura
					.getCodigo_sucursal());
			facturacion_servicio.setNro_factura(detalle_factura
					.getFactura_concepto());
			facturacion_servicio = facturacion_servicioService
					.consultar(facturacion_servicio);
			if (facturacion_servicio != null) {
				if (facturacion_servicio.getTipo().equals("1")) {
					codigo_plantilla = res.getCodigo_contabilidad_materiales();
				} else if (facturacion_servicio.getTipo().equals("2")) {
					codigo_plantilla = res.getCodigo_contabilidad_traslados();
				} else if (facturacion_servicio.getTipo().equals("3")) {
					codigo_plantilla = res.getCodigo_contabilidad_estancias();
				} else if (facturacion_servicio.getTipo().equals("4")) {
					codigo_plantilla = res.getCodigo_contabilidad_traslados();
				}
			}
		}

		return codigo_plantilla;
	}

	public Nota_contable guardarContabilizacionGlosa(Map<String, Object> datos, boolean sw) {
		try {
			Long id_factura = (Long)datos.get("id_factura");
			String tipo = (String) datos.get("tipo");
			String doc = "PCD";

			String forma_pago = "02";

			Facturacion facturacion = new Facturacion();
			facturacion.setId_factura(id_factura);
			facturacion = facturacionDao.consultar(facturacion);

			if (facturacion == null && sw) {
				throw new Exception("Este documento no se ha guardado todavia");
			}

			if (!sw) {
				facturacion = (Facturacion) datos.get("facturacion");
				facturacion.setTipo(tipo);
			}

			if (!facturacion.getTipo().equals("FHC")
					&& !facturacion.getTipo().equals("FCC")) {
				throw new Exception(
						"Esta factura no es clinica por lo tanto no se puede contabilizar");
			}
			
			String codigo_empresa = facturacion.getCodigo_empresa();
			String codigo_sucursal = facturacion.getCodigo_sucursal();
			String codigo_comprobante = facturacion.getCodigo_comprobante();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("id_factura", id_factura);
			List<Detalle_factura> lista_detalle = (List<Detalle_factura>) datos
					.get("lista_detalle");
			if (sw) {
				lista_detalle = detalle_facturaDao.listar(parameters);
			}

			Resolucion res = new Resolucion();
			res.setCodigo_empresa(codigo_empresa);
			res = resolucionDao.consultar(res);
			
			Timestamp fecha_contabilizacion = null;
			if(facturacion.getFecha_contabilizacion()!=null){
				fecha_contabilizacion = facturacion.getFecha_contabilizacion();
			}else{
				fecha_contabilizacion = facturacion.getFecha();
			}

			Tercero tercero = new Tercero();
			tercero.setCodigo_empresa(codigo_empresa);
			tercero.setCodigo_sucursal(codigo_sucursal);
			tercero.setNro_identificacion(facturacion.getCodigo_tercero());
			tercero = terceroDao.consultar(tercero);

			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(facturacion.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(facturacion.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(facturacion
					.getCodigo_comprobante());
			nota_contable
					.setCodigo_documento(facturacion.getCodigo_documento_res());
			nota_contable.setPrefijo(facturacion.getPrefijo());
			nota_contable.setFecha(fecha_contabilizacion);
			nota_contable.setAnio(new SimpleDateFormat("yyyy").format(fecha_contabilizacion));
			nota_contable.setMes(new SimpleDateFormat("MM").format(fecha_contabilizacion));
			
			nota_contable
					.setElaboro(facturacion.getCodigo_empleado() != null ? facturacion
							.getCodigo_empleado() : "");
			nota_contable.setCodigo_tercero(facturacion.getCodigo_tercero());
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
			// double total = facturacion.getValor_total() -
			// facturacion.getCop_canc();
			double total = 0;
			if (!sw) {
				for (Detalle_factura detalle_factura : lista_detalle) {
					total += detalle_factura.getValor_total();
				}
				total = total - facturacion.getValor_copago();
			} else {
				Double result = detalle_facturaDao
						.totalFacturaClinica(paramDetalle_factura)
						- facturacion.getCop_canc();
				total = (result != null ? result : 0.0);
			}

			double copago = facturacion.getCop_canc();
			double retencion = 0;

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
			retencion = (Math.rint(retencion * 1) / 1);
			
			double reteica = 0;
	        impuesto = new Impuesto();
	        impuesto.setCodigo_empresa(codigo_empresa);
	        impuesto.setCodigo_sucursal(codigo_sucursal);
	        impuesto.setCodigo_cuenta((tercero != null ? tercero.getCuenta_reteica() : ""));
	        impuesto.setAnio(facturacion.getAnio());
	        impuesto = impuestoDao.consultar(impuesto);
	        if (impuesto != null) {
	            if (impuesto.getBase() <= total) {
	            	reteica = total * impuesto.getPorcentaje();
	            }
	        }
	        reteica = (Math.rint(reteica*1)/1);

			double valor_glosa = facturacion.getValor_glosa_aceptada() <= 0 ? facturacion
					.getValor_glosa_inicial() : facturacion
					.getValor_glosa_aceptada();

			double descuento_glosa = (total - retencion-reteica - (facturacion
					.getTipo().equals("FCC") ? (total - retencion-reteica)
					: ((facturacion.getValor_pagar_factura() - retencion-reteica) - valor_glosa)));

			int consecutivo = 0;

			if (tercero != null) {
				if (tercero.getCuenta_pagar().trim().equals("")) {
					throw new Exception("La aseguradora "
							+ tercero.getNombre1()
							+ " debe tener una cuenta por pagar asignada");
				}

				if (descuento_glosa < 0) {
					throw new Exception(
							"El documento "
									+ facturacion.getCodigo_documento_res()
									+ " no se pudo contabilizar el valor a pagar de es mayor que el valor neto de la factura");
				}

				Detalle_nota detalle_nota = new Detalle_nota();
				detalle_nota.setCodigo_empresa(codigo_empresa);
				detalle_nota.setCodigo_sucursal(codigo_sucursal);
				detalle_nota.setCodigo_comprobante(codigo_comprobante);
				detalle_nota.setCodigo_documento(facturacion.getCodigo_documento_res());
				detalle_nota.setConsecutivo(consecutivo + "");
				detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
				detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
				detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
				detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
				detalle_nota.setCodigo_cuenta(tercero.getCuenta_pagar());
				detalle_nota.setConcepto(facturacion.getDescripcion());
				detalle_nota.setTercero(facturacion.getCodigo_tercero());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(0.0);
				detalle_nota.setCredito(total - retencion-reteica);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("FHC "
						+ codigo_comprobante
						+ "-"
						+ (!facturacion.getCodigo_documento_res().equals("") ? Integer
								.parseInt(facturacion.getCodigo_documento_res()) : ""));
				detalle_nota.setVence(fecha_contabilizacion);
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				map.put(tercero.getCuenta_pagar() + "01", detalle_nota);

				if (!tercero.getCuenta_retencion().trim().equals("")
						&& retencion > 0) {
					detalle_nota = new Detalle_nota();
					detalle_nota.setCodigo_empresa(codigo_empresa);
					detalle_nota.setCodigo_sucursal(codigo_sucursal);
					detalle_nota.setCodigo_comprobante(codigo_comprobante);
					detalle_nota.setCodigo_documento(facturacion.getCodigo_documento_res());
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
					detalle_nota.setConcepto("RETENCION COMPRA");
					detalle_nota.setTercero(facturacion.getCodigo_tercero());
					detalle_nota.setCheque("");
					detalle_nota.setDebito(0);
					detalle_nota.setCredito(retencion);
					detalle_nota.setC_costo("");
					detalle_nota.setAbona("");
					detalle_nota.setVence(fecha_contabilizacion);
					detalle_nota.setBanco("");
					detalle_nota.setIva(0);
					detalle_nota.setCheque_consignado("N");
					detalle_nota.setTipo("");
					detalle_nota.setConcepto_abona("");
					// detalle_notaDao.crear(detalle_nota);
					map.put(tercero.getCuenta_retencion() + "01", detalle_nota);
				}
				
				if (!tercero.getCuenta_reteica().trim().equals("") && reteica>0) {
	            	detalle_nota = new Detalle_nota();
	                detalle_nota.setCodigo_empresa(codigo_empresa);
	                detalle_nota.setCodigo_sucursal(codigo_sucursal);
	                detalle_nota.setCodigo_comprobante(codigo_comprobante);
	                detalle_nota.setCodigo_documento(facturacion.getCodigo_documento_res());
	                detalle_nota.setConsecutivo(consecutivo + "");
	                detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
	                detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
	                detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
	                detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
	                detalle_nota.setCodigo_cuenta(tercero.getCuenta_reteica());
	                detalle_nota.setConcepto("RETEICA COMPRA");
	                detalle_nota.setTercero(facturacion.getCodigo_tercero());
	                detalle_nota.setCheque("");
	                detalle_nota.setDebito(0);
	                detalle_nota.setCredito(reteica);
	                detalle_nota.setC_costo("");
	                detalle_nota.setAbona("");
	                detalle_nota.setVence(fecha_contabilizacion);
	                detalle_nota.setBanco("");
                    detalle_nota.setIva(0);
                    detalle_nota.setCheque_consignado("N");
                    detalle_nota.setTipo("");
                    detalle_nota.setConcepto_abona("");
	                //detalle_notaDao.crear(detalle_nota);
	                map.put(tercero.getCuenta_reteica() + "01", detalle_nota);
	            }
			}

			String manual = "ISS01";
			// FIN DE TERCERO != NULL
			for (Detalle_factura detalle_factura : lista_detalle) {
				String codigo_plantilla = obtenerCodigoPlantilla(
						detalle_factura, res, manual);
				double dto = (((detalle_factura.getValor_total() * 100) / (total + copago)) / 100)
						* copago;
				double valor = detalle_factura.getValor_total() - dto;

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
				List lista_detalle_cont = detalle_contabilizacionDao
						.listar(paramDetalle_cont);
				// log.info("lista_detalle_cont: " + lista_detalle_cont);

				for (int j = 0; j < lista_detalle_cont.size(); j++) {
					Detalle_contabilizacion aux2 = (Detalle_contabilizacion) lista_detalle_cont
							.get(j);
					String codigo_cuenta = aux2.getCodigo_cuenta();
					String manejo = aux2.getMenajo();
					if (map.get(codigo_cuenta + manejo) == null) {
						if (valor != 0) {
							// //System.Out.Println("Codigo_cuenta: " +
							// codigo_cuenta);
							Detalle_nota detalle_nota = new Detalle_nota();
							detalle_nota.setCodigo_empresa(codigo_empresa);
							detalle_nota.setCodigo_sucursal(codigo_sucursal);
							detalle_nota
									.setCodigo_comprobante(codigo_comprobante);
							detalle_nota.setCodigo_documento(facturacion.getCodigo_documento_res());
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
									.getCodigo_tercero());
							detalle_nota.setCheque("");
							detalle_nota.setDebito(aux2.getMenajo()
									.equals("01") ? valor : 0);
							detalle_nota.setCredito(aux2.getMenajo().equals(
									"02") ? valor : 0);
							detalle_nota.setC_costo("");
							detalle_nota.setAbona("");
							detalle_nota.setVence(fecha_contabilizacion);
							detalle_nota.setBanco("");
							detalle_nota.setIva(0);
							detalle_nota.setCheque_consignado("N");
							detalle_nota.setTipo("");
							detalle_nota.setConcepto_abona("");

							map.put(aux2.getCodigo_cuenta() + manejo,
									detalle_nota);
						}
					} else {
						Detalle_nota detalle_nota = (Detalle_nota) map
								.get(codigo_cuenta + manejo);
						detalle_nota.setDebito(detalle_nota.getDebito()
								+ (aux2.getMenajo().equals("01") ? valor : 0));
						detalle_nota.setCredito(detalle_nota.getCredito()
								+ (aux2.getMenajo().equals("02") ? valor : 0));
						map.put(aux2.getCodigo_cuenta() + manejo, detalle_nota);
					}
				}
			}

			// Fin de armar contabilizaciones //
			Iterator it = map.entrySet().iterator();
			int index = 1;
			double dbt = 0, cdt = 0;
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Detalle_nota detalle_nota = (Detalle_nota) e.getValue();
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
				it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry e = (Map.Entry) it.next();
					Detalle_nota detalle_nota = (Detalle_nota) e.getValue();
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
				throw new Exception(
						"Los saldos  "
								+ facturacion.getCodigo_documento_res()
								+ " del asiento contable suma 0. Revise la contabilizacion de los servicios del detalle del documento");
			}
			if ((dbt != cdt) && sw) {
				throw new Exception(
						"El documento "
								+ facturacion.getCodigo_documento_res()
								+ " esta descuadrado. Revise la contabilizacion de los servicios del detalle del documento");
			}

			List<Detalle_nota> lista_detalle_nota = new LinkedList<Detalle_nota>();
			it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Detalle_nota detalle_nota = (Detalle_nota) e.getValue();
				detalle_nota.setConsecutivo(index + "");
				lista_detalle_nota.add(detalle_nota);
				if (sw) {
					detalle_notaDao.crear(detalle_nota);
				}
				index++;
			}

			Cartera cartera = new Cartera();
			cartera.setCodigo_empresa(codigo_empresa);
			cartera.setCodigo_sucursal(codigo_sucursal);
			cartera.setCodigo_comprobante(codigo_comprobante);
			cartera.setCodigo_documento(facturacion.getCodigo_documento_res());
			cartera.setNro_cuota("1");
			cartera.setVencimiento(facturacion.getFecha_vencimiento());
			cartera.setAbono(0);
			cartera.setSaldos(total - retencion-reteica - descuento_glosa);
			cartera.setTotal(total - retencion-reteica - descuento_glosa);
			cartera.setTipo(facturacion.getTipo());
			cartera.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cartera.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cartera.setCreacion_user(nota_contable.getCreacion_user());
			cartera.setUltimo_user(nota_contable.getCreacion_user());
			cartera.setManejo("CP");
			cartera.setCodigo_tercero(facturacion.getCodigo_tercero());
			cartera.setCuenta((tercero != null ? tercero.getCuenta_pagar() : ""));
			cartera.setConcepto("");
			if (carteraDao.consultar(cartera) != null) {
				cartera = carteraDao.consultar(cartera);
				cartera.setSaldos(total - retencion-reteica - descuento_glosa);
				cartera.setTotal(total - retencion-reteica - descuento_glosa);
				if (cartera.getSaldos() - cartera.getAbono() < 0) {
					throw new Exception(
							"La factura "
									+ facturacion.getCodigo_documento_res()
									+ " ya se le ha hecho abonos que superan el nuevo saldo");
				}
				cartera.setSaldos(cartera.getSaldos() - cartera.getAbono());
				if (sw) {
					carteraDao.actualizar(cartera);
				}
			} else {
				if (sw) {
					carteraDao.crear(cartera);
				}
			}

			nota_contable.setLista_detalle(lista_detalle_nota);

			if (descuento_glosa > 0) {
				Map<String, Object> datosNota_credito = new HashMap<String, Object>();
				datosNota_credito.put("nota_contable", nota_contable);
				datosNota_credito.put("lista_detalle", lista_detalle);
				datosNota_credito.put("cuenta_retencion",
						(tercero != null ? tercero.getCuenta_retencion() : ""));
				datosNota_credito.put("fecha_glosa",
						facturacion.getFecha_glosa());
				datosNota_credito.put("valor_glosa", valor_glosa);
				datosNota_credito.put("sw", sw);
				datosNota_credito.put("res", res);
				datosNota_credito.put("total", total);
				datosNota_credito.put("copago", copago);
				Nota_contable nota_credito = crearNotaCredito(datosNota_credito);
				if (!sw) {
					List<Detalle_nota> lista_detalle_nota_credito = nota_credito
							.getLista_detalle();
					for (Detalle_nota detalle_nota : lista_detalle_nota_credito) {
						lista_detalle_nota.add(detalle_nota);
					}
				}

			}

			if (sw) {
				facturacion.setEstado("CONT");
				facturacionDao.actualizar(facturacion);
			}

			return nota_contable;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private Nota_contable crearNotaCredito(Map<String, Object> datos) throws Exception {

		Nota_contable nota_contable = (Nota_contable) datos
				.get("nota_contable");
		List<Detalle_factura> lista_detalle = (List<Detalle_factura>) datos
				.get("lista_detalle");
		String cuenta_retencion = (String) datos.get("cuenta_retencion");
		Date fecha_glosa = (Date) datos.get("fecha_glosa");
		double valor_glosa = (Double) datos.get("valor_glosa");
		boolean sw = (Boolean) datos.get("sw");
		Resolucion res = (Resolucion) datos.get("res");
		double total = (Double) datos.get("total");
		double copago = (Double) datos.get("copago");

		String doc = "PCD";
		String forma_pago = "02";

		Nota_contable nota_credito = Res.clonar(nota_contable);
		nota_credito.setFuente_factura_glosa(nota_contable
				.getCodigo_comprobante());
		nota_credito.setDocumento_factura_glosa(nota_contable
				.getCodigo_documento());

		Nota_contable auxNota_credito = nota_contableDao
				.consultarNota_credito_glosa(nota_credito);

		String codigo_empresa = nota_credito.getCodigo_empresa();
		String codigo_sucursal = nota_credito.getCodigo_sucursal();
		String codigo_comprobante = "";
		String codigo_documento = "";
		if (auxNota_credito == null) {
			codigo_comprobante = "NCR";
			// Obtenermos el consecutivo //
			codigo_documento = consecutivoService.getZeroFill(
					consecutivoService.crearConsecutivoNota(
							nota_credito.getCodigo_empresa(),
							nota_credito.getCodigo_sucursal(),
							codigo_comprobante), 20);
		} else {
			codigo_comprobante = auxNota_credito.getCodigo_comprobante();
			codigo_documento = auxNota_credito.getCodigo_documento();
		}

		Tercero tercero = new Tercero();
		tercero.setCodigo_empresa(codigo_empresa);
		tercero.setCodigo_sucursal(codigo_sucursal);
		tercero.setNro_identificacion(nota_contable.getCodigo_tercero());
		tercero = terceroDao.consultar(tercero);

		nota_credito.setCodigo_comprobante(codigo_comprobante);
		nota_credito.setCodigo_documento(codigo_documento);
		nota_credito.setConcepto("NOTA CREDITO GLOSA DOCUMENTO "
				+ nota_contable.getCodigo_comprobante() + " "
				+ nota_contable.getCodigo_documento());
		nota_credito.setFecha(new Timestamp(fecha_glosa.getTime()));
		nota_credito.setAnio(new SimpleDateFormat("yyyy").format(fecha_glosa));
		nota_credito.setMes(new SimpleDateFormat("MM").format(fecha_glosa));
		nota_credito.setFuente_factura_glosa(nota_contable
				.getCodigo_comprobante());
		nota_credito.setDocumento_factura_glosa(nota_contable
				.getCodigo_documento());
		if (sw) {
			if (auxNota_credito != null) {
				nota_contableDao.eliminar(auxNota_credito);
			}
			nota_contableDao.crear(nota_credito);
			if (auxNota_credito == null) {
				consecutivoService.actualizarConsecutivoNota(
						nota_credito.getCodigo_empresa(),
						nota_credito.getCodigo_sucursal(), codigo_comprobante,
						codigo_documento);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();

		double retencion = 0;

		Impuesto impuesto = new Impuesto();
		impuesto.setCodigo_empresa(codigo_empresa);
		impuesto.setCodigo_sucursal(codigo_sucursal);
		impuesto.setCodigo_cuenta(cuenta_retencion);
		impuesto.setAnio(nota_credito.getAnio());
		impuesto = impuestoDao.consultar(impuesto);
		if (impuesto != null) {
			if (impuesto.getBase() <= valor_glosa) {
				retencion = valor_glosa * impuesto.getPorcentaje();
			}
		}

		retencion = (Math.rint(retencion * 1) / 1);

		int consecutivo = 0;
		if (tercero != null) {
			if (tercero.getCuenta_pagar().trim().equals("")) {
				throw new Exception("La aseguradora " + tercero.getNombre1()
						+ " debe tener una cuenta por pagar asignada");
			}

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
			detalle_nota.setCodigo_cuenta(tercero.getCuenta_pagar());
			detalle_nota.setConcepto(nota_credito.getConcepto());
			detalle_nota.setTercero(nota_credito.getCodigo_tercero());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(valor_glosa - retencion);
			detalle_nota.setCredito(0.0);
			detalle_nota.setC_costo("");
			detalle_nota
					.setAbona("FHC "
							+ nota_contable.getCodigo_comprobante()
							+ "-"
							+ (!nota_contable.getCodigo_documento().equals("") ? Integer
									.parseInt(nota_contable
											.getCodigo_documento()) : ""));
			detalle_nota.setVence(nota_credito.getFecha());
			detalle_nota.setBanco("");
			detalle_nota.setIva(0);
			detalle_nota.setCheque_consignado("N");
			detalle_nota.setTipo("");
			detalle_nota.setConcepto_abona("");
			map.put(tercero.getCuenta_pagar() + "01", detalle_nota);

			if (!cuenta_retencion.trim().equals("") && retencion > 0) {
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
				detalle_nota.setCodigo_cuenta(tercero.getCuenta_retencion());
				detalle_nota.setConcepto("RETENCION COMPRA");
				detalle_nota.setTercero(nota_credito.getCodigo_tercero());
				detalle_nota.setCheque("");
				detalle_nota.setDebito(retencion);
				detalle_nota.setCredito(0.0);
				detalle_nota.setC_costo("");
				detalle_nota.setAbona("");
				detalle_nota.setVence(nota_credito.getFecha());
				detalle_nota.setBanco("");
				detalle_nota.setIva(0);
				detalle_nota.setCheque_consignado("N");
				detalle_nota.setTipo("");
				detalle_nota.setConcepto_abona("");
				// detalle_notaDao.crear(detalle_nota);
				map.put(tercero.getCuenta_retencion() + "01", detalle_nota);
			}
		}
		// FIN DE TERCERO != NULL
		String manual = "ISS01";

		for (Detalle_factura detalle_factura : lista_detalle) {
			String codigo_plantilla = obtenerCodigoPlantilla(detalle_factura,
					res, manual);
			double dto = (((detalle_factura.getValor_total() * 100) / (total + copago)) / 100)
					* copago;
			double saldo = detalle_factura.getValor_total() - dto;
			double valor = ((((saldo * 100) / (total)) / 100) * valor_glosa);

			Map<String, Object> paramDetalle_cont = new HashMap<String, Object>();
			paramDetalle_cont.put("codigo_empresa", codigo_empresa);
			paramDetalle_cont.put("codigo_sucursal", codigo_sucursal);
			paramDetalle_cont.put("doc", doc);
			paramDetalle_cont.put("tipo_contribuyente",
					(tercero != null ? tercero.getTipo_contribuyente() : "01"));
			paramDetalle_cont.put("forma_pago", forma_pago);
			paramDetalle_cont.put("codigo", codigo_plantilla);
			paramDetalle_cont.put("tipo_aseguradora",
					(tercero != null ? tercero.getTipo_aseguradora() : "01"));
			// log.info("paramDetalle_cont Nota credito: " + paramDetalle_cont);
			List lista_detalle_cont = detalle_contabilizacionDao
					.listar(paramDetalle_cont);
			// log.info("lista_detalle_cont Nota credito: " +
			// lista_detalle_cont);
			for (int j = 0; j < lista_detalle_cont.size(); j++) {
				Detalle_contabilizacion aux2 = (Detalle_contabilizacion) lista_detalle_cont
						.get(j);
				String codigo_cuenta = aux2.getCodigo_cuenta();
				String manejo = aux2.getMenajo();
				if (map.get(codigo_cuenta + manejo) == null) {
					if (valor != 0) {
						// //System.Out.Println("Codigo_cuenta: " +
						// codigo_cuenta);
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
						detalle_nota.setCodigo_cuenta(aux2.getCodigo_cuenta());
						detalle_nota.setConcepto(nota_credito.getConcepto());
						detalle_nota.setTercero(nota_credito
								.getCodigo_tercero());
						detalle_nota.setCheque("");
						detalle_nota
								.setDebito(aux2.getMenajo().equals("02") ? valor
										: 0);
						detalle_nota
								.setCredito(aux2.getMenajo().equals("01") ? valor
										: 0);
						detalle_nota.setC_costo("");
						detalle_nota.setAbona("");
						detalle_nota.setVence(nota_credito.getFecha());
						detalle_nota.setBanco("");
						detalle_nota.setIva(0);
						detalle_nota.setCheque_consignado("N");
						detalle_nota.setTipo("");
						detalle_nota.setConcepto_abona("");

						map.put(aux2.getCodigo_cuenta() + manejo, detalle_nota);
					}
				} else {
					Detalle_nota detalle_nota = (Detalle_nota) map
							.get(codigo_cuenta + manejo);
					detalle_nota.setDebito(detalle_nota.getDebito()
							+ (aux2.getMenajo().equals("02") ? valor : 0));
					detalle_nota.setCredito(detalle_nota.getCredito()
							+ (aux2.getMenajo().equals("01") ? valor : 0));
					map.put(aux2.getCodigo_cuenta() + manejo, detalle_nota);
				}
			}

		}

		// Fin de armar contabilizaciones //
		Iterator it = map.entrySet().iterator();
		int index = 1;
		double dbt = 0, cdt = 0;
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			Detalle_nota detalle_nota = (Detalle_nota) e.getValue();
			detalle_nota.setDebito(Double.parseDouble(String.format(
					Locale.ENGLISH, "%.2f", detalle_nota.getDebito())));
			detalle_nota.setCredito(Double.parseDouble(String.format(
					Locale.ENGLISH, "%.2f", detalle_nota.getCredito())));
			// //System.Out.Println(detalle_nota.getCodigo_cuenta()+":"+detalle_nota.getDebito()+"   "+detalle_nota.getCredito());
			dbt += detalle_nota.getDebito();
			cdt += detalle_nota.getCredito();
		}
		dbt = Double.parseDouble(String.format(Locale.ENGLISH, "%.2f", dbt));
		cdt = Double.parseDouble(String.format(Locale.ENGLISH, "%.2f", cdt));

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
			it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				Detalle_nota detalle_nota = (Detalle_nota) e.getValue();
				if (dif < 0 && detalle_nota.getCredito() > 0 && !entro) {
					detalle_nota.setCredito(detalle_nota.getCredito()
							- Math.abs(dif));
					entro = true;
				} else if (dif > 0 && detalle_nota.getDebito() > 0 && !entro) {
					detalle_nota.setDebito(detalle_nota.getDebito()
							- Math.abs(dif));
					entro = true;
				}
			}
		}
		// log.info("dbt: " + dbt);
		 log.debug("cdt: " + cdt);

		if ((dbt == 0 && cdt == 0) && sw) {
			throw new Exception(
					"Los saldos  "
							+ nota_credito.getCodigo_documento()
							+ " de la nota a credito suma 0. Revise la contabilizacion de los servicios del detalle del documento");
		}
		if ((dbt != cdt) && sw) {
			throw new Exception(
					"El documento "
							+ nota_credito.getCodigo_documento()
							+ " de la nota a credito esta descuadrado. Revise la contabilizacion de los servicios del detalle del documento");
		}

		List<Detalle_nota> lista_detalle_nota = new LinkedList<Detalle_nota>();
		it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			Detalle_nota detalle_nota = (Detalle_nota) e.getValue();
			detalle_nota.setConsecutivo(index + "");
			lista_detalle_nota.add(detalle_nota);
			if (sw) {
				detalle_notaDao.crear(detalle_nota);
			}
			index++;
		}
		nota_credito.setLista_detalle(lista_detalle_nota);

		return nota_credito;

	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
