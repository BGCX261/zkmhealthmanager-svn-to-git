/*
 * ImpuestoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Detalle_contabilizacion;
import contaweb.modelo.bean.Empresa;
import contaweb.modelo.bean.Impuesto;
import contaweb.modelo.bean.Puc;
import contaweb.modelo.bean.Tercero;
import contaweb.modelo.dao.ArticuloDao;
import contaweb.modelo.dao.Detalle_contabilizacionDao;
import contaweb.modelo.dao.EmpresaDao;
import contaweb.modelo.dao.ImpuestoDao;
import contaweb.modelo.dao.PucDao;
import contaweb.modelo.dao.TerceroDao;

@Service("impuestoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ImpuestoService implements Serializable{

	private String limit;

	@Autowired
	private ImpuestoDao impuestoDao;
	@Autowired
	private TerceroDao terceroDao;
	@Autowired
	private ArticuloDao articuloDao;
	@Autowired
	private Detalle_contabilizacionDao detalle_contabilizacionDao;
	@Autowired
	private PucDao pucDao;
	@Autowired
	private EmpresaDao empresaDao;

	public void crear(Impuesto impuesto) {
		try {
			if (consultar(impuesto) != null) {
				throw new HealthmanagerException(
						"Impuesto ya se encuentra en la base de datos");
			}
			impuestoDao.crear(impuesto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Impuesto impuesto) {
		try {
			return impuestoDao.actualizar(impuesto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Impuesto consultar(Impuesto impuesto) {
		try {
			return impuestoDao.consultar(impuesto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Map<String, Double> consultarImpuestos(Map<String, Object> datos) {
		try {
			String codigo_empresa = (String) datos.get("codigo_empresa");
			String codigo_sucursal = (String) datos.get("codigo_sucursal");
			String periodo_anio = (String) datos.get("periodo_anio");
			String tipo = (String) datos.get("tipo");
			String doc = (tipo.equals("COMPRA") || tipo.equals("ORDEN") ? "FC"
					: "FV");
			String codigo_tercero = (String) datos.get("codigo_tercero");
			String forma_pago = (String) datos.get("forma_pago");
			List<Map<String, Object>> lista_detalle = (List<Map<String, Object>>) datos.get("lista_detalle");
			String tipo_contribuyente = "";

			Tercero tercero = new Tercero();
			tercero.setCodigo_empresa(codigo_empresa);
			tercero.setCodigo_sucursal(codigo_sucursal);
			tercero.setNro_identificacion(codigo_tercero);
			tercero = terceroDao.consultar(tercero);
			tipo_contribuyente = (tercero != null ? tercero
					.getTipo_contribuyente() : "");
			// double tarifa_ica = (tercero != null ? tercero.getTarifa_ica() :
			// 0.0);
			boolean aplica_reteica = (tercero != null ? tercero
					.getAplica_reteica() : false);

			Empresa empresa = new Empresa();
			empresa.setCodigo_empresa(codigo_empresa);
			empresa = empresaDao.consultar(empresa);

			@SuppressWarnings("unused")
			double total = 0, retencion = 0, total_iva = 0, reteiva = 0, reteica = 0, total_descuentos = 0, total_gravado = 0;
			Map<String, Object> retenciones = new HashMap<String, Object>();
			Map<String, Object> reteivas = new HashMap<String, Object>();
			Map<String, Object> reteicas = new HashMap<String, Object>();
			for (Map<String, Object> map : lista_detalle) {
				double valor_total = (Double) map.get("valor_total");
				double descuento = (Double) map.get("descuento");
				double iva = (Double) map.get("iva");
				double iva_teorico = (Double) map.get("iva_teorico");
				total += (valor_total);
				total_iva += iva;
				total_descuentos += descuento;
				total_gravado += iva_teorico;
			}

			for (Map<String, Object> map : lista_detalle) {
				String codigo_articulo = (String) map.get("codigo_articulo");
				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(codigo_empresa);
				articulo.setCodigo_sucursal(codigo_sucursal);
				articulo.setCodigo_articulo(codigo_articulo);
				articulo = articuloDao.consultar(articulo);

				boolean servicio_gravado = (articulo != null ? articulo
						.getServicio_gravado() : false);

				String codigo_plantilla = (articulo != null ? articulo
						.getCodigo_contabilidad() : "");
				Map<String, Object> param = new HashMap();
				param.put("codigo_empresa", codigo_empresa);
				param.put("codigo_sucursal", codigo_sucursal);
				param.put("doc", doc);
				param.put("tipo_contribuyente", tipo_contribuyente);
				param.put("forma_pago", forma_pago);
				param.put("codigo", codigo_plantilla);
				param.put("limit", limit);
				List<Detalle_contabilizacion> lista_detalle_cont = detalle_contabilizacionDao
						.listar(param);
				for (int j = 0; j < lista_detalle_cont.size(); j++) {
					Detalle_contabilizacion aux2 = lista_detalle_cont.get(j);
					String codigo_cuenta = aux2.getCodigo_cuenta();
					String tipo_cuenta = "";
					Puc puc = new Puc();
					puc.setCodigo_empresa(codigo_empresa);
					puc.setCodigo_sucursal(codigo_sucursal);
					puc.setCodigo_cuenta(aux2.getCodigo_cuenta());
					puc = pucDao.consultar(puc);
					tipo_cuenta = (puc != null ? puc.getTipo_cuenta() : "");
					if (tipo_cuenta.equals("08")) {
						Impuesto impuesto = new Impuesto();
						impuesto.setCodigo_empresa(codigo_empresa);
						impuesto.setCodigo_sucursal(codigo_sucursal);
						impuesto.setCodigo_cuenta(codigo_cuenta);
						impuesto.setAnio(periodo_anio);
						impuesto = impuestoDao.consultar(impuesto);
						if (impuesto != null) {
							if (impuesto.getBase() <= total) {
								if (retenciones.get(codigo_cuenta) == null) {
									retenciones
											.put(codigo_cuenta,
													new Double(
															((total - total_descuentos) * impuesto
																	.getPorcentaje())));
								}
							}
						}
					} else if (tipo_cuenta.equals("14")) {
						if (reteivas.get(codigo_cuenta) == null) {
							// //System.Out.Println("(total_iva / 2): "+(total_iva
							// / 2));
							if (tipo_contribuyente.equals("01")) {
								if (!servicio_gravado
										|| !empresa.getTipo_contribuyente()
												.equals("02")) {
									reteivas.put(codigo_cuenta, new Double(
											(total_iva)));
								}

							} else {
								reteivas.put(codigo_cuenta, new Double(
										(total_iva / 2)));
							}
						}
					} else if (tipo_cuenta.equals("15")) {

						Impuesto impuesto = new Impuesto();
						impuesto.setCodigo_empresa(codigo_empresa);
						impuesto.setCodigo_sucursal(codigo_sucursal);
						impuesto.setCodigo_cuenta(codigo_cuenta);
						impuesto.setAnio(periodo_anio);
						impuesto = impuestoDao.consultar(impuesto);
						// log.info("impuesto: "+impuesto);
						// log.info("total: "+total);
						// log.info("aplica_reteica: "+aplica_reteica);

						if (impuesto != null) {
							if (impuesto.getBase() <= total && aplica_reteica) {
								if (reteicas.get(codigo_cuenta) == null) {
									reteicas.put(
											codigo_cuenta,
											new Double(
													((total - total_descuentos) * impuesto
															.getPorcentaje())));
								}
							}
						}
					} /*
					 * else if (tipo_cuenta.equals("15")) { if
					 * (reteicas.get(codigo_cuenta) == null) {
					 * reteicas.put(codigo_cuenta, new
					 * Double(((total-total_descuentos) * tarifa_ica))); } }
					 */
				}
			}

			Iterator it = retenciones.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				retencion += ((Double) e.getValue()).doubleValue();
			}
			it = reteivas.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				// //System.Out.Println("rete: "+((Double)
				// e.getValue()).doubleValue());
				reteiva += ((Double) e.getValue()).doubleValue();
			}
			it = reteicas.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				// //System.Out.Println("rete: "+((Double)
				// e.getValue()).doubleValue());
				reteica += ((Double) e.getValue()).doubleValue();
			}

			Map<String, Double> result = new HashMap<String, Double>();
			result.put("retencion", retencion);
			result.put("reteiva", reteiva);
			result.put("reteica", reteica);

			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Impuesto impuesto) {
		try {
			return impuestoDao.eliminar(impuesto);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Impuesto> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return impuestoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.impuestoDao.existe(parameters);
	}

}
