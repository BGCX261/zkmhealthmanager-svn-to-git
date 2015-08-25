/*
 * Main.java
 *
 * Created on 25 de agosto de 2008, 10:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.framework.res;

import healthmanager.modelo.bean.Consecutivo;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.framework.locator.ServiceLocatorWeb;

/**
 * 
 * @author FERNEY JIMENEZ LOPEZ
 */
public class Main implements java.io.Serializable {

	private static final long serialVersionUID = -2164209632514006543L;

	public static String getZeroFill(String valor, int zeroFill) {
		String st_zeroFill = "000000000000000000000000000000";
		return st_zeroFill.substring(0, zeroFill - valor.length()) + valor;
	}

	public static String obtenerConsecutivo(ServiceLocatorWeb serviceLocator,
			String codigo_empresa, String codigo_sucursal, String tabla) {
		try {
			Consecutivo consecutivo = new Consecutivo();
			consecutivo.setCodigo_empresa(codigo_empresa);
			consecutivo.setCodigo_sucursal(codigo_sucursal);
			consecutivo.setTipo(tabla);
			Consecutivo consecutivoTemo = serviceLocator
					.getConsecutivoService().consultar(consecutivo);
			if (consecutivoTemo == null) {
				consecutivo.setConsecutivo("1");
				consecutivo.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				consecutivo.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				serviceLocator.getConsecutivoService().crear(consecutivo);
			} else {
				consecutivo = consecutivoTemo;
			}
			return consecutivo.getConsecutivo();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}

	}

	public static String obtenerNro_informe(ServiceLocatorWeb serviceLocator,
			String codigo_empresa, String codigo_sucursal, String tabla) {
		GregorianCalendar hoy = new GregorianCalendar();
		try {
			Consecutivo consecutivo = new Consecutivo();
			consecutivo.setCodigo_empresa(codigo_empresa);
			consecutivo.setCodigo_sucursal(codigo_sucursal);
			consecutivo.setTipo(tabla);
			Consecutivo consecutivoTemp = serviceLocator
					.getConsecutivoService().consultar(consecutivo);
			if (consecutivoTemp == null) {
				consecutivo.setConsecutivo("1");
				consecutivo.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				consecutivo.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				serviceLocator.getConsecutivoService().crear(consecutivo);
			} else {
				consecutivo = consecutivoTemp;
				GregorianCalendar ultm = new GregorianCalendar();
				ultm.setTimeInMillis(consecutivo.getCreacion_date().getTime());
				if (ultm.get(Calendar.YEAR) != hoy.get(Calendar.YEAR)
						|| (ultm.get(Calendar.YEAR) == hoy.get(Calendar.YEAR) && ultm
								.get(Calendar.MONTH) != hoy.get(Calendar.MONTH))
						|| (ultm.get(Calendar.YEAR) == hoy.get(Calendar.YEAR)
								&& ultm.get(Calendar.MONTH) == hoy
										.get(Calendar.MONTH) && ultm
								.get(Calendar.DAY_OF_MONTH) != hoy
								.get(Calendar.DAY_OF_MONTH))) {
					consecutivo.setConsecutivo("1");
					consecutivo.setCreacion_date(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					consecutivo.setUltimo_update(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					serviceLocator.getConsecutivoService().actualizar(
							consecutivo);
					consecutivo = serviceLocator.getConsecutivoService()
							.consultar(consecutivo);
				}
			}
			return consecutivo.getConsecutivo();
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}

	}

	public static String obtenerNro_solicitud(ServiceLocatorWeb serviceLocator,
			String codigo_empresa, String codigo_sucursal, String tabla) {
		GregorianCalendar hoy = new GregorianCalendar();
		try {
			Consecutivo consecutivo = new Consecutivo();
			consecutivo.setCodigo_empresa(codigo_empresa);
			consecutivo.setCodigo_sucursal(codigo_sucursal);
			consecutivo.setTipo(tabla);
			Consecutivo consecutivortemp = serviceLocator
					.getConsecutivoService().consultar(consecutivo);
			if (consecutivortemp == null) {
				consecutivo.setConsecutivo("1");
				consecutivo.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				consecutivo.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				serviceLocator.getConsecutivoService().crear(consecutivo);
			} else {
				consecutivo = consecutivortemp;
				GregorianCalendar ultm = new GregorianCalendar();
				ultm.setTimeInMillis(consecutivo.getCreacion_date().getTime());
				if (ultm.get(Calendar.YEAR) != hoy.get(Calendar.YEAR)) {
					consecutivo.setConsecutivo("1");
					consecutivo.setCreacion_date(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					consecutivo.setUltimo_update(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					serviceLocator.getConsecutivoService().actualizar(
							consecutivo);
					consecutivo = serviceLocator.getConsecutivoService()
							.consultar(consecutivo);
				}
			}

			return consecutivo.getConsecutivo();

		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}

	}

	public static void actualizarConsecutivo(ServiceLocatorWeb serviceLocator,
			String codigo_empresa, String codigo_sucursal, String tabla,
			String codigo) throws Exception {
		try {
			Consecutivo consecutivo = new Consecutivo();
			consecutivo.setCodigo_empresa(codigo_empresa);
			consecutivo.setCodigo_sucursal(codigo_sucursal);
			consecutivo.setTipo(tabla);
			consecutivo = serviceLocator.getConsecutivoService().consultar(
					consecutivo);
			if (consecutivo != null) {
				consecutivo.setConsecutivo((Integer.parseInt(codigo) + 1) + "");
				serviceLocator.getConsecutivoService().actualizar(consecutivo);
			}

		} catch (Exception exception) {
			throw new Exception("Error al actualizar consecutivo");
		}

	}

	// public static String getElement(DaoFactory daoFactory, String codigo,
	// String tipo) {
	// try {
	// ElementoDao elementoDao = daoFactory.getElementoDao();
	// Elemento e = new Elemento();
	// e.setCodigo(codigo);
	// e.setTipo(tipo);
	// e = (Elemento) elementoDao.consultar(e);
	// if (e != null) {
	// return e.getDescripcion();
	// } else {
	// return "";
	// }
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return "";
	// }
	//
	// public static String getElementC(DaoFactory daoFactory, String
	// descripcion, String tipo) {
	// try {
	// ElementoDao elementoDao = daoFactory.getElementoDao();
	// Elemento e = new Elemento();
	// List lista_elemento = elementoDao.listar(e, tipo, descripcion);
	// if (!lista_elemento.isEmpty()) {
	// return ((Elemento) lista_elemento.get(0)).getCodigo();
	// } else {
	// return "";
	// }
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return "";
	// }
	//
	// public static String getDepartamento(DaoFactory daoFactory, String
	// codigo_departamento) {
	// try {
	// DepartamentosDao departamentosDao = daoFactory.getDepartamentosDao();
	// Departamentos d = new Departamentos();
	// d.setCodigo(codigo_departamento);
	// d = (Departamentos) departamentosDao.consultar(d);
	// if (d != null) {
	// return d.getNombre();
	// } else {
	// return "";
	// }
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return "";
	// }
	//
	// public static String getMunicipio(DaoFactory daoFactory, String
	// codigo_departamento, String codigo_municipio) {
	// try {
	// MunicipiosDao municipiosDao = daoFactory.getMunicipiosDao();
	// Municipios m = new Municipios();
	// m.setCoddep(codigo_departamento);
	// m.setCodigo(codigo_municipio);
	// m = (Municipios) municipiosDao.consultar(m);
	// if (m != null) {
	// return m.getNombre();
	// } else {
	// return "";
	// }
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return "";
	// }
	//
	// public static InputStream cargaImagen(DaoFactory daoFactory, String
	// codigo_empresa, String path) {
	// InputStream imagen = null;
	// try {
	// Resolucion res = new Resolucion();
	// res.setCodigo_empresa(codigo_empresa);
	// imagen = ((Resolucion)
	// daoFactory.getResolucionDao().consultar(res)).getLogo();
	//
	// } catch (Exception exception) {
	// imagen = null;
	// }
	// return imagen;
	// }
	//
	// public static String cargaResolucion(DaoFactory daoFactory, String
	// codigo_empresa) {
	// String resolucion = "";
	// try {
	// Resolucion res = new Resolucion();
	// res.setCodigo_empresa(codigo_empresa);
	// resolucion = ((Resolucion)
	// daoFactory.getResolucionDao().consultar(res)).getResolucion();
	//
	// } catch (Exception exception) {
	// resolucion = "";
	// }
	// return resolucion;
	// }
	//
	// public static String getEdad(String fecha) {
	// int mesActual = new java.util.Date().getMonth() + 1;
	// int anioActual = new java.util.Date().getYear() + 1900;
	// int mes = 0;
	// int anio = 0;
	// StringTokenizer st = new StringTokenizer(fecha, "/");
	// int cont = 0;
	// while (st.hasMoreTokens()) {
	// String tokens = st.nextToken();
	// if (cont == 1) {
	// mes = Integer.parseInt(tokens, 10);
	// } else if (cont == 2) {
	// anio = Integer.parseInt(tokens, 10);
	// }
	// cont++;
	// }
	//
	// int ed = 0;
	// ed = anioActual - anio;
	// if (mesActual < mes) {
	// ed--;
	// }
	// if (ed <= 0) {
	// ed = 1;
	// }
	// return ed + "";
	// }
	// public static String getManual(DaoFactory daoFactory, String codigo) {
	// try {
	// Manual_usoDao manual_usoDao = daoFactory.getManual_usoDao();
	// Manual_uso manual = new Manual_uso();
	// manual.setCodigo(codigo);
	// manual = (Manual_uso) manual_usoDao.consultar(manual);
	// if (manual != null) {
	// return manual.getNombre();
	// } else {
	// return "";
	// }
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return "";
	// }
	//
	// public static String formatDate(java.util.Date date, String format) {
	// return new java.text.SimpleDateFormat(format).format(date);
	// }
	//
	// public static String formatDate(java.sql.Date date, String format) {
	// return formatDate((java.util.Date) date, format);
	// }
	//
	// public static String formatDate(java.sql.Timestamp date, String format) {
	// return formatDate((java.util.Date) date, format);
	// }
	//
	// public static String formatDecimal(double valor, String format) {
	// Locale.setDefault(Locale.ENGLISH);
	// return new DecimalFormat(format).format(valor);
	// }
	//
	// public static String formatText(String text) {
	// String format = "";
	// for (int i = 0; i < text.length(); i++) {
	// char ch = text.charAt(i);
	// if (ch != '.' && ch != ',' && ch != '/' && ch != '|' && ch != '-' && ch
	// != '_' && ch != '\\') {
	// format += ch + "";
	// }
	// }
	// return format;
	// }

}
