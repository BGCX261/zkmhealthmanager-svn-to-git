/*
 * CajaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Caja;
import contaweb.modelo.bean.Detalle_caja;
import contaweb.modelo.bean.Detalle_nota;
import contaweb.modelo.bean.Nota_contable;
import contaweb.modelo.bean.Resolucion;
import contaweb.modelo.dao.CajaDao;
import contaweb.modelo.dao.Detalle_cajaDao;
import contaweb.modelo.dao.Detalle_notaDao;
import contaweb.modelo.dao.Nota_contableDao;
import contaweb.modelo.dao.ResolucionDao;

@Service("cajaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CajaService implements Serializable{

	private String limit;

	private static Logger log = Logger.getLogger(CajaService.class);

	@Autowired
	private CajaDao cajaDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private ResolucionDao resolucionDao;
	@Autowired
	private Detalle_cajaDao detalle_cajaDao;
	@Autowired
	private Nota_contableDao nota_contableDao;
	@Autowired
	private Detalle_notaDao detalle_notaDao;

	public Caja guardarCuotaModeradora(Map<String, Object> datos) {
		try {
			String codigo_empresa = (String) datos.get("codigo_empresa");
			String codigo_sucursal = (String) datos.get("codigo_sucursal");
			String fuente = "05";
			String tipo_recibo = (String) datos.get("tipo_recibo");
			String codigo_tercero = (String) datos.get("codigo_tercero");
			String codigo_anexo4 = (String) datos.get("codigo_anexo4");
			String codigo_receta = (String) datos.get("codigo_receta");
			String codigo_anexo9 = (String) datos.get("codigo_anexo9");
			String copago_autorizaciones = (String) datos
					.get("copago_autorizaciones");
			String copago_medicamentos = (String) datos
					.get("copago_medicamentos");
			String tipo_tercero = tipo_recibo.equals("04")
					|| tipo_recibo.equals("05") || tipo_recibo.equals("06") ? "02"
					: "01";
			Timestamp fecha = (Timestamp) datos.get("fecha_pago");
			double valor_recibo = (Double) datos.get("total");
			double descuento = (Double) datos.get("dto");
			double efectivo = (Double) datos.get("efectivo");
			double valor_tarjeta = (Double) datos.get("valor_tarjeta");
			String numero_tarjeta = (String) datos.get("numero_tarjeta");
			String banco_tarjeta = (String) datos.get("banco_tarjeta");
			String tipo_tarjeta = (String) datos.get("tipo_tarjeta");
			String tipo_cuenta = (String) datos.get("tipo_cuenta");
			// String cuenta_tarjeta = request.getParameter("cuenta_tarjeta");
			// String amparador = request.getParameter("amparador");
			double valor_cheque = (Double) datos.get("valor_cheque");
			String numero_cheque = (String) datos.get("numero_cheque");
			String cuenta_cheque = (String) datos.get("cuenta_cheque");
			String banco_cheque = (String) datos.get("banco_cheque");
			// String sucursal_cheque = request.getParameter("sucursal_cheque");
			String medio_pago = (String) datos.get("medio_pago");
			Timestamp fecha_cheque = (datos.get("fecha_cheque") != null ? (Timestamp) datos
					.get("fecha_cheque") : null);
			Timestamp creacion_date = new Timestamp(
					new GregorianCalendar().getTimeInMillis());
			Timestamp ultimo_update = new Timestamp(
					new GregorianCalendar().getTimeInMillis());
			String creacion_user = (String) datos.get("id_usuario");
			String ultimo_user = (String) datos.get("id_usuario");
			List<Map<String, Object>> lista_detalle = (List<Map<String, Object>>) datos
					.get("lista_detalle");
			String codigo_cita = (String) datos.get("codigo_cita");
			String nro_ingreso = (String) datos.get("nro_ingreso");
			Boolean convension = (Boolean) datos.get("convension");

			String codigo_orden = datos.get("codigo_orden") + "";

			boolean cont = false;
			Resolucion res = new Resolucion();
			res.setCodigo_empresa(codigo_empresa);
			res = resolucionDao.consultar(res);
			if (res != null) {
				if (res.getContabiliza_aut()) {
					cont = true;
				}
			}

			// Validamos que no valla a salir error
			if (nro_ingreso == null) {
				nro_ingreso = "";
			}

			Caja caja = new Caja();
			caja.setCodigo_empresa(codigo_empresa);
			caja.setCodigo_sucursal(codigo_sucursal);
			caja.setFuente(fuente);
			caja.setCodigo_comprobante("");
			caja.setCodigo_documento("");
			caja.setNro_ingreso(nro_ingreso);
			caja.setCodigo_tercero(codigo_tercero);
			caja.setTipo_tercero(tipo_tercero);
			caja.setTipo_recibo(tipo_recibo);
			caja.setCodigo_administradora("");
			caja.setId_plan("");
			caja.setFecha(fecha);
			caja.setValor_recibo(valor_recibo);
			caja.setDescuento(descuento);
			caja.setEfectivo(efectivo);
			caja.setValor_tarjeta(valor_tarjeta);
			caja.setNumero_tarjeta(numero_tarjeta);
			caja.setBanco_tarjeta(banco_tarjeta);
			caja.setTipo_tarjeta(tipo_tarjeta);
			caja.setTipo_cuenta(tipo_cuenta);
			caja.setCuenta_tarjeta("");
			caja.setAmparador("");
			caja.setValor_cheque(valor_cheque);
			caja.setNumero_cheque(numero_cheque);
			caja.setCuenta_cheque(cuenta_cheque);
			caja.setBanco_cheque(banco_cheque);
			caja.setSucursal_cheque("");
			caja.setFecha_cheque(fecha_cheque);
			caja.setCreacion_date(creacion_date);
			caja.setUltimo_update(ultimo_update);
			caja.setCreacion_user(creacion_user);
			caja.setUltimo_user(ultimo_user);
			caja.setCodigo_orden(codigo_orden);
			caja.setConvension(convension ? "S" : "N");

			String concepto = "PARTICULAR";
			if (copago_autorizaciones.equals("S")) {
				concepto = "COPAGO";
			} else if (copago_medicamentos.equals("S")) {
				concepto = "CUOTA MODERADORA";
			}

			caja.setVendedor("");
			caja.setReferencia("");
			caja.setCodigo_credito("");
			caja.setConcepto("RECIBO DE CAJA POR " + concepto);
			caja.setPrefijo("");
			caja.setAnio(new java.text.SimpleDateFormat("yyyy").format(fecha));
			caja.setMes(new java.text.SimpleDateFormat("MM").format(fecha));
			caja.setCodigo_cita(codigo_cita);
			caja.setMedio_pago(medio_pago);
			caja.setCodigo_anexo4(codigo_anexo4);
			caja.setCopago_autorizaciones(copago_autorizaciones);
			caja.setCopago_medicamentos(copago_medicamentos);
			caja.setCodigo_receta(codigo_receta);
			caja.setCodigo_anexo9(codigo_anexo9);

			if (!codigo_cita.trim().isEmpty()) {
				cont = false;
			}

			if (copago_autorizaciones.equals("S")) {
				Caja cajaAux = new Caja(false);
				cajaAux.setCodigo_empresa(caja.getCodigo_empresa());
				cajaAux.setCodigo_sucursal(caja.getCodigo_sucursal());
				cajaAux.setCodigo_orden(caja.getCodigo_orden());
				cajaAux.setCopago_autorizaciones("S");
				cajaAux = cajaDao.consultar(cajaAux);
				if (cajaAux != null) {
					throw new Exception("Ya se generó copago por este anexo");
				}
			}

			String codigo_recibo = consecutivoService.getZeroFill(
					consecutivoService.crearConsecutivoNota(codigo_empresa,
							codigo_sucursal, "05"), 20);
			caja.setCodigo_recibo(codigo_recibo);
			cajaDao.crear(caja);
			consecutivoService.actualizarConsecutivoNota(
					caja.getCodigo_empresa(), caja.getCodigo_sucursal(),
					caja.getFuente(), caja.getCodigo_recibo());

			int i = 1;
			for (Map<String, Object> bean : lista_detalle) {
				String codigo_articulo = (String) bean.get("codigo_articulo");
				String nombre_articulo = (String) bean.get("detalle");
				double cantidad = (Double) bean.get("cant");
				double valor_unitario = (Double) bean.get("valor_unit");
				double valor_total = (Double) bean.get("valor_total");
				double copago = (Double) bean.get("copago");
				Double valor_adicionar = (Double) bean.get("valor_adicional");
				double dto = 0;
				if (copago == 0) {
					dto = (((valor_total * 100) / (valor_recibo + descuento)) / 100)
							* descuento;
				} else {
					dto = (((copago * 100) / (valor_recibo + descuento)) / 100)
							* descuento;
				}

				Detalle_caja detalle = new Detalle_caja();
				detalle.setCodigo_empresa(codigo_empresa);
				detalle.setCodigo_sucursal(codigo_sucursal);
				detalle.setFuente(fuente);
				detalle.setCodigo_recibo(caja.getCodigo_recibo()); 
				detalle.setConsecutivo(i + "");
				detalle.setNro_cuota("1");
				detalle.setCodigo_articulo(codigo_articulo);
				detalle.setDetalle(nombre_articulo);
				detalle.setCodigo_bodega("");
				detalle.setCodigo_lote("");
				detalle.setTipo("");
				detalle.setCantidad(cantidad);
				detalle.setValor_unitario(valor_unitario);
				detalle.setValor_total(valor_total);
				detalle.setCopago(copago);
				detalle.setDescuento(dto);
				detalle.setCreacion_date(creacion_date);
				detalle.setUltimo_update(ultimo_update);
				detalle.setCreacion_user(creacion_user);
				detalle.setUltimo_user(ultimo_user);
				detalle.setValor_adicional(valor_adicionar != null ? valor_adicionar
						: 0d);
				detalle_cajaDao.crear(detalle);
				i++;
			}

			caja = consultar(caja);

			log.debug("caja: " + caja);

			if (cont) {
				Map<String, Object> param_cont = new HashMap<String, Object>();
				param_cont.put("codigo_empresa", codigo_empresa);
				param_cont.put("codigo_sucursal", codigo_sucursal);
				param_cont.put("codigo_recibo", caja.getCodigo_recibo());
				param_cont.put("fuente", caja.getFuente());
				guardarContabilizacionCopago(param_cont);
			}

			return caja;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Nota_contable guardarContabilizacionCopago(Map<String, Object> datos) {
		try {
			String codigo_empresa = (String) datos.get("codigo_empresa");
			String codigo_sucursal = (String) datos.get("codigo_sucursal");
			String codigo_recibo = (String) datos.get("codigo_recibo");
			String fuente = (String) datos.get("fuente");

			Caja caja = new Caja(false);
			caja.setCodigo_empresa(codigo_empresa);
			caja.setCodigo_sucursal(codigo_sucursal);
			caja.setFuente(fuente);
			caja.setCodigo_recibo(codigo_recibo);
			// log.info("caja contabilizar copago antes: " + caja);
			caja = cajaDao.consultar(caja);

			if (caja == null) {
				throw new HealthmanagerException(
						"Este documento no se ha guardado todavia");
			}

			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(caja.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(caja.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(fuente);
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

			if (nota_contableDao.consultar(nota_contable) != null) {
				nota_contableDao.eliminar(nota_contable);
			}
			nota_contableDao.crear(nota_contable);

			contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
			res.setCodigo_empresa(codigo_empresa);
			res = resolucionDao.consultar(res);

			double saldo = 0;
			List<Detalle_caja> lista_detalle = caja.getLista_detalle();
			for (Detalle_caja detalle_caja : lista_detalle) {
				saldo += (detalle_caja.getCopago() == 0 ? detalle_caja
						.getValor_total() : detalle_caja.getCopago());
			}

			String cuenta_caja = (res != null ? res.getCuenta_caja() : "");
			String cuenta_copago = (res != null ? res.getCuenta_copago() : "");

			if (cuenta_caja.equals("")) {
				throw new HealthmanagerException(
						"Parametro cuenta caja no esta definido");
			}

			if (cuenta_copago.equals("")) {
				throw new HealthmanagerException(
						"Parametro cuenta ingreso copago no esta definido");
			}

			Detalle_nota detalle_nota = new Detalle_nota();
			detalle_nota.setCodigo_empresa(codigo_empresa);
			detalle_nota.setCodigo_sucursal(codigo_sucursal);
			detalle_nota.setCodigo_comprobante(fuente);
			detalle_nota.setCodigo_documento(codigo_recibo);
			detalle_nota.setConsecutivo("0");
			detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
			detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
			detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
			detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
			detalle_nota.setCodigo_cuenta(cuenta_caja);
			detalle_nota.setConcepto(caja.getConcepto());
			detalle_nota.setTercero(caja.getCodigo_tercero());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(saldo);
			detalle_nota.setCredito(0.0);
			detalle_nota.setC_costo("");
			detalle_nota.setAbona("");
			detalle_nota.setVence(caja.getFecha());
			detalle_notaDao.crear(detalle_nota);

			detalle_nota = new Detalle_nota();
			detalle_nota.setCodigo_empresa(codigo_empresa);
			detalle_nota.setCodigo_sucursal(codigo_sucursal);
			detalle_nota.setCodigo_comprobante(fuente);
			detalle_nota.setCodigo_documento(codigo_recibo);
			detalle_nota.setConsecutivo("1");
			detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
			detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
			detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
			detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
			detalle_nota.setCodigo_cuenta(cuenta_copago);
			detalle_nota.setConcepto(caja.getConcepto());
			detalle_nota.setTercero(caja.getCodigo_tercero());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(0.0);
			detalle_nota.setCredito(saldo);
			detalle_nota.setC_costo("");
			detalle_nota.setAbona("");
			detalle_nota.setVence(caja.getFecha());
			detalle_notaDao.crear(detalle_nota);

			nota_contable = nota_contableDao.consultar(nota_contable);

			return nota_contable;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Caja caja) {
		try {
			if (consultar(caja) != null) {
				throw new HealthmanagerException(
						"Caja ya se encuentra en la base de datos");
			}
			cajaDao.crear(caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Caja caja) {
		try {
			return cajaDao.actualizar(caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Caja consultar(Caja caja) {
		try {
			return cajaDao.consultar(caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Caja caja) {
		try {
			return cajaDao.eliminar(caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Caja> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return cajaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public double totalCopagos(Caja caja) {
		try {
			Double total_copago = cajaDao.totalCopagos(caja);
			return total_copago != null ? total_copago : 0d;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public int actualizarFecha(Caja caja) { 
		try {
			return cajaDao.actualizarFecha(caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
