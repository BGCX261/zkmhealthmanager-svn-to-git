/*
 * PagareServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Cartera;
import contaweb.modelo.bean.Configuracion_pagares;
import contaweb.modelo.bean.Detalle_nota;
import contaweb.modelo.bean.Detalle_pagare;
import contaweb.modelo.bean.Nota_contable;
import contaweb.modelo.bean.Pagare;
import contaweb.modelo.bean.Resolucion;
import contaweb.modelo.dao.CarteraDao;
import contaweb.modelo.dao.Configuracion_pagaresDao;
import contaweb.modelo.dao.Detalle_notaDao;
import contaweb.modelo.dao.Detalle_pagareDao;
import contaweb.modelo.dao.Nota_contableDao;
import contaweb.modelo.dao.PagareDao;
import contaweb.modelo.dao.ResolucionDao;

@Service("pagareService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PagareService implements Serializable{

	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private PagareDao pagareDao;
	@Autowired
	private Detalle_pagareDao detalle_pagareDao;
	@Autowired
	private Nota_contableDao nota_contableDao;
	@Autowired
	private Detalle_notaDao detalle_notaDao;
	@Autowired
	private ResolucionDao resolucionDao;
	@Autowired
	private CarteraDao carteraDao;
	@Autowired
	private Configuracion_pagaresDao configuracion_pagaresDao;
	private String limit;

	public Pagare guardarPagare(Map<String, Object> datos) {
		try {
			String codigo_empresa = (String) datos.get("codigo_empresa");
			String codigo_sucursal = (String) datos.get("codigo_sucursal");
			String codigo_comprobante = "PG";
			String tipo_recibo = (String) datos.get("tipo_recibo");
			String codigo_documento = consecutivoService.getZeroFill(
					consecutivoService.crearConsecutivoNota(codigo_empresa,
							codigo_sucursal, "PG"), 20);

			String codigo_tercero = (String) datos.get("codigo_tercero");
			String codigo_anexo4 = (String) datos.get("codigo_anexo4");
			String codigo_receta = (String) datos.get("codigo_receta");
			String codigo_anexo9 = (String) datos.get("codigo_anexo9");
			String copago_autorizaciones = (String) datos
					.get("copago_autorizaciones");
			String copago_medicamentos = (String) datos
					.get("copago_medicamentos");
			@SuppressWarnings("unused")
			String tipo_tercero = tipo_recibo.equals("04")
					|| tipo_recibo.equals("05") || tipo_recibo.equals("06") ? "02"
					: "01";
			Timestamp fecha = (Timestamp) datos.get("fecha_pagare");
			Timestamp primer_vencimiento = (Timestamp) datos
					.get("primer_vencimiento");
			int nro_cuota = (Integer) datos.get("nro_cuota");
			double valor_pagare = (Double) datos.get("valor_pagare");
			double interes = (Double) datos.get("interes");
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

			String concepto = "PARTICULAR";
			if (copago_autorizaciones.equals("S")) {
				concepto = "COPAGO";
			} else if (copago_medicamentos.equals("S")) {
				concepto = "CUOTA MODERADORA";
			}

			Configuracion_pagares configuracion_pagares = new Configuracion_pagares();
			configuracion_pagares.setCodigo_empresa(codigo_empresa);
			configuracion_pagares.setCodigo_sucursal(codigo_sucursal);
			configuracion_pagares
					.setAnio(new java.text.SimpleDateFormat("yyyy")
							.format(fecha));
			configuracion_pagares = configuracion_pagaresDao
					.consultar(configuracion_pagares);
			double valor_minimo_pagare = 0.0;
			double valor_minimo_cuotas = 0.0;
			int numero_maximo_cuotas = 0;
			if (configuracion_pagares != null) {
				valor_minimo_pagare = configuracion_pagares
						.getValor_minimo_pagare();
				valor_minimo_cuotas = configuracion_pagares
						.getValor_minimo_cuotas();
				numero_maximo_cuotas = configuracion_pagares
						.getNumero_maximo_cuotas();
			}

			if ((valor_pagare + interes) < valor_minimo_pagare) {
				throw new Exception(
						"El valor del pagare no llega al minimo establecido \""
								+ valor_minimo_pagare + "\"");
			}

			if ((nro_cuota) > numero_maximo_cuotas) {
				throw new Exception(
						"El nro maximo de cuotas no puede sobrepasar al maximo establecido \""
								+ numero_maximo_cuotas + "\"");
			}

			Pagare pagare = new Pagare();
			pagare.setCodigo_empresa(codigo_empresa);
			pagare.setCodigo_sucursal(codigo_sucursal);
			pagare.setCodigo_comprobante(codigo_comprobante);
			pagare.setCodigo_documento(codigo_documento);
			pagare.setFecha(fecha);
			pagare.setConcepto("PAGARE POR " + concepto);
			pagare.setAnio(new java.text.SimpleDateFormat("yyyy").format(fecha));
			pagare.setMes(new java.text.SimpleDateFormat("MM").format(fecha));
			pagare.setNro_ingreso(nro_ingreso);
			pagare.setCodigo_administradora("");
			pagare.setCodigo_tercero(codigo_tercero);
			pagare.setTipo_recibo(tipo_recibo);
			pagare.setId_plan("");
			pagare.setCodigo_cita(codigo_cita);
			pagare.setCodigo_anexo4(codigo_anexo4);
			pagare.setCodigo_receta(codigo_receta);
			pagare.setCodigo_anexo9(codigo_anexo9);
			pagare.setCodigo_orden(codigo_orden);
			pagare.setCreacion_date(creacion_date);
			pagare.setUltimo_update(ultimo_update);
			pagare.setCreacion_user(creacion_user);
			pagare.setUltimo_user(ultimo_user);
			pagare.setValor_pagare(valor_pagare);
			pagare.setNro_cuota(nro_cuota);
			pagare.setPrimer_vencimiento(primer_vencimiento);
			pagare.setInteres(interes);

			if (copago_autorizaciones.equals("S")) {
				Pagare auxPagare = new Pagare();
				auxPagare.setCodigo_empresa(pagare.getCodigo_empresa());
				auxPagare.setCodigo_sucursal(pagare.getCodigo_sucursal());
				auxPagare.setCodigo_orden(pagare.getCodigo_orden());
				auxPagare.setCopago_autorizaciones("S");
				auxPagare = pagareDao.consultarAdmision(auxPagare);
				if (auxPagare != null) {
					throw new Exception("Ya se generó pagaré por este anexo");
				}
			}

			// log.info("pagare: "+pagare.getCodigo_cita());

			pagareDao.crear(pagare);
			consecutivoService.actualizarConsecutivoNota(
					pagare.getCodigo_empresa(), pagare.getCodigo_sucursal(),
					pagare.getCodigo_comprobante(),
					pagare.getCodigo_documento());

			int i = 1;
			for (Map<String, Object> bean : lista_detalle) {
				String codigo_articulo = (String) bean.get("codigo_articulo");
				String nombre_articulo = (String) bean.get("detalle");
				double cantidad = (Double) bean.get("cant");
				double valor_unitario = (Double) bean.get("valor_unit");
				double valor_total = (Double) bean.get("valor_total");
				double copago = (Double) bean.get("copago");

				Detalle_pagare detalle_pagare = new Detalle_pagare();
				detalle_pagare.setCodigo_empresa(codigo_empresa);
				detalle_pagare.setCodigo_sucursal(codigo_sucursal);
				detalle_pagare.setCodigo_comprobante(codigo_comprobante);
				detalle_pagare.setCodigo_documento(codigo_documento);
				detalle_pagare.setConsecutivo(i + "");
				detalle_pagare.setCodigo_servicio(codigo_articulo);
				detalle_pagare.setConcepto(nombre_articulo);
				detalle_pagare.setCantidad(cantidad);
				detalle_pagare.setValor_unitario(valor_unitario);
				detalle_pagare.setValor_total(valor_total);
				detalle_pagare.setCopago(copago);
				detalle_pagareDao.crear(detalle_pagare);
				i++;

			}

			pagare = consultar(pagare);

			guardarCartera(pagare, valor_minimo_cuotas);

			if (cont) {
				Map<String, Object> param_cont = new HashMap<String, Object>();
				param_cont.put("codigo_empresa", codigo_empresa);
				param_cont.put("codigo_sucursal", codigo_sucursal);
				param_cont
						.put("codigo_documento", pagare.getCodigo_documento());
				param_cont.put("codigo_comprobante",
						pagare.getCodigo_comprobante());
				guardarContabilizacionPagare(param_cont);
			}

			return pagare;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private void guardarCartera(Pagare pagare, double valor_minimo_cuotas)
			throws Exception {
		Timestamp vence = new Timestamp(pagare.getPrimer_vencimiento()
				.getTime());
		int nro_cuota = pagare.getNro_cuota();
		double acum_valor_cuota = 0.0;
		double valor_neto = pagare.getValor_pagare() + pagare.getInteres();
		// log.info("valor_neto: "+valor_neto);
		for (int i = 1; i <= nro_cuota; i++) {
			if (i > 1) {
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTimeInMillis(vence.getTime());
				gc.set(Calendar.DAY_OF_MONTH, gc.get(Calendar.DAY_OF_MONTH)
						+ (i != 0 ? 30 : (30 * i)));
				vence.setTime(gc.getTimeInMillis());
			}

			double valor_cuota = (valor_neto / nro_cuota);
			valor_cuota = (Math.rint(valor_cuota * 1) / 1);
			acum_valor_cuota += valor_cuota;
			if (i == nro_cuota) {
				// //log.info("acum_valor_cuota: "+acum_valor_cuota);
				// //log.info("valor_cuota: "+valor_cuota);
				double dif = acum_valor_cuota - valor_neto;
				// //log.info("dif: "+dif);
				if (dif < 0) {
					valor_cuota += Math.abs(dif);
				} else {
					valor_cuota -= Math.abs(dif);
				}
			}

			if (valor_cuota < valor_minimo_cuotas) {
				throw new Exception("El valor de la cuota \"" + valor_cuota
						+ "\" no llega al minimo establecido \""
						+ valor_minimo_cuotas + "\"");
			}

			Cartera cartera = new Cartera();
			cartera.setCodigo_empresa(pagare.getCodigo_empresa());
			cartera.setCodigo_sucursal(pagare.getCodigo_sucursal());
			cartera.setCodigo_comprobante(pagare.getCodigo_comprobante());
			cartera.setCodigo_documento(pagare.getCodigo_documento());
			cartera.setNro_cuota(i + "");
			cartera.setVencimiento(vence);
			cartera.setAbono(0);
			cartera.setSaldos(valor_cuota);
			cartera.setTotal(valor_cuota);
			cartera.setTipo("PG");
			cartera.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cartera.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cartera.setCreacion_user(pagare.getCreacion_user());
			cartera.setUltimo_user(pagare.getUltimo_user());
			cartera.setManejo("CC");
			cartera.setCodigo_tercero(pagare.getCodigo_tercero());
			cartera.setCuenta("");
			cartera.setConcepto("");
			// cartera.setConcepto(next);
			if (carteraDao.consultar(cartera) != null) {
				Cartera aux = (Cartera) carteraDao.consultar(cartera);
				cartera.setCreacion_date(aux.getCreacion_date());
				cartera.setCreacion_user(aux.getCreacion_user());
				carteraDao.actualizar(cartera);
			} else {
				cartera.setCreacion_date(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				cartera.setCreacion_user(pagare.getCreacion_user());
				carteraDao.crear(cartera);
			}
		}
	}

	public Nota_contable guardarContabilizacionPagare(Map<String, Object> datos) {
		try {
			String codigo_empresa = (String) datos.get("codigo_empresa");
			String codigo_sucursal = (String) datos.get("codigo_sucursal");
			String codigo_documento = (String) datos.get("codigo_documento");
			String codigo_comprobante = (String) datos
					.get("codigo_comprobante");

			Pagare pagare = new Pagare();
			pagare.setCodigo_empresa(codigo_empresa);
			pagare.setCodigo_sucursal(codigo_sucursal);
			pagare.setCodigo_comprobante(codigo_comprobante);
			pagare.setCodigo_documento(codigo_documento);
			pagare = pagareDao.consultar(pagare);

			if (pagare == null) {
				throw new HealthmanagerException(
						"Este documento no se ha guardado todavia");
			}

			Nota_contable nota_contable = new Nota_contable();
			nota_contable.setCodigo_empresa(pagare.getCodigo_empresa());
			nota_contable.setCodigo_sucursal(pagare.getCodigo_sucursal());
			nota_contable.setCodigo_comprobante(codigo_comprobante);
			nota_contable.setCodigo_documento(pagare.getCodigo_documento());
			nota_contable.setPrefijo("");
			nota_contable.setAnio(new java.text.SimpleDateFormat("yyyy")
					.format(pagare.getFecha()));
			nota_contable.setMes(new java.text.SimpleDateFormat("MM")
					.format(pagare.getFecha()));
			nota_contable.setFecha(pagare.getFecha());
			nota_contable.setElaboro("");
			nota_contable.setCodigo_tercero(pagare.getCodigo_tercero());
			nota_contable.setClasificacion("");
			nota_contable.setConcepto(pagare.getConcepto());
			nota_contable.setVerificado("");
			nota_contable.setCreacion_date(pagare.getCreacion_date());
			nota_contable.setUltimo_update(pagare.getUltimo_update());
			nota_contable.setCreacion_user(pagare.getCreacion_user());
			nota_contable.setUltimo_user(pagare.getUltimo_user());

			if (nota_contableDao.consultar(nota_contable) != null) {
				nota_contableDao.eliminar(nota_contable);
			}
			nota_contableDao.crear(nota_contable);

			contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
			res.setCodigo_empresa(codigo_empresa);
			res = resolucionDao.consultar(res);

			double saldo = 0;
			List<Detalle_pagare> lista_detalle = pagare.getLista_detalle();
			for (Detalle_pagare detalle_pagare : lista_detalle) {
				saldo += (detalle_pagare.getCopago() == 0 ? detalle_pagare
						.getValor_total() : detalle_pagare.getCopago());
			}

			saldo += pagare.getInteres();

			String cuenta_tercero = (res != null ? res.getCuenta_cobrar() : "");
			String cuenta_copago = (res != null ? res.getCuenta_copago() : "");

			if (cuenta_tercero.equals("")) {
				throw new HealthmanagerException(
						"Parametro cuenta por cobrar no esta definido");
			}

			if (cuenta_copago.equals("")) {
				throw new HealthmanagerException(
						"Parametro cuenta ingreso copago no esta definido");
			}

			Detalle_nota detalle_nota = new Detalle_nota();
			detalle_nota.setCodigo_empresa(codigo_empresa);
			detalle_nota.setCodigo_sucursal(codigo_sucursal);
			detalle_nota.setCodigo_comprobante(codigo_comprobante);
			detalle_nota.setCodigo_documento(codigo_documento);
			detalle_nota.setConsecutivo("0");
			detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
			detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
			detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
			detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
			detalle_nota.setCodigo_cuenta(cuenta_tercero);
			detalle_nota.setConcepto(pagare.getConcepto());
			detalle_nota.setTercero(pagare.getCodigo_tercero());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(saldo);
			detalle_nota.setCredito(0.0);
			detalle_nota.setC_costo("");
			detalle_nota.setAbona("PG " + codigo_comprobante + "-"
					+ Integer.parseInt(codigo_documento));
			detalle_nota.setVence(pagare.getFecha());
			detalle_notaDao.crear(detalle_nota);

			detalle_nota = new Detalle_nota();
			detalle_nota.setCodigo_empresa(codigo_empresa);
			detalle_nota.setCodigo_sucursal(codigo_sucursal);
			detalle_nota.setCodigo_comprobante(codigo_comprobante);
			detalle_nota.setCodigo_documento(codigo_documento);
			detalle_nota.setConsecutivo("1");
			detalle_nota.setCreacion_date(nota_contable.getCreacion_date());
			detalle_nota.setUltimo_update(nota_contable.getUltimo_update());
			detalle_nota.setCreacion_user(nota_contable.getCreacion_user());
			detalle_nota.setUltimo_user(nota_contable.getUltimo_user());
			detalle_nota.setCodigo_cuenta(cuenta_copago);
			detalle_nota.setConcepto(pagare.getConcepto());
			detalle_nota.setTercero(pagare.getCodigo_tercero());
			detalle_nota.setCheque("");
			detalle_nota.setDebito(0.0);
			detalle_nota.setCredito(saldo);
			detalle_nota.setC_costo("");
			detalle_nota.setAbona("");
			detalle_nota.setVence(pagare.getFecha());
			detalle_notaDao.crear(detalle_nota);

			nota_contable = nota_contableDao.consultar(nota_contable);

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", codigo_empresa);
			param.put("codigo_sucursal", codigo_sucursal);
			param.put("codigo_comprobante", codigo_comprobante);
			param.put("codigo_documento", codigo_documento);
			param.put("manejo", "CC");
			param.put("codigo_tercero", pagare.getCodigo_tercero());
			List<Cartera> lista_cartera = carteraDao.listar(param);
			for (Cartera aux : lista_cartera) {
				aux.setCuenta(cuenta_tercero);
				carteraDao.actualizar(aux);
			}

			return nota_contable;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Pagare pagare) {
		try {
			if (consultar(pagare) != null) {
				throw new HealthmanagerException(
						"Pagare ya se encuentra en la base de datos");
			}
			pagareDao.crear(pagare);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Pagare pagare) {
		try {
			return pagareDao.actualizar(pagare);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Pagare consultar(Pagare pagare) {
		try {
			return pagareDao.consultar(pagare);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Pagare consultarAdmision(Pagare pagare) {
		try {
			return pagareDao.consultarAdmision(pagare);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Pagare pagare) {
		try {
			return pagareDao.eliminar(pagare);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Pagare> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return pagareDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return pagareDao.existe(parameters);
	}

}