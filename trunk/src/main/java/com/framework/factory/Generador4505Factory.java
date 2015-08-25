package com.framework.factory;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.service.ReportService;
import healthmanager.modelo.service.Resolucion4505Service;
import healthmanager.modelo.service.Resolucion4505Service.OnResultSet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.zkoss.zul.Filedownload;

import com.framework.constantes.IResolucion4505Constantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.state.GeneradorArchivo4505State;
import com.framework.state.impl.GeneradorArchivo4505Impl;
import com.framework.state.impl.LineaBean;
import com.framework.util.Util;
import com.framework.util.ValidadorArchivoUtils;

public class Generador4505Factory {

	public static Logger log = Logger.getLogger(Generador4505Factory.class);

	private final String FORMATO_COMPRIMIDO = ".zip";
	private final int BUFFER = 2048;

	private static final String INCLUIR = ":S";
	private static final String EXCLUIR = ":N";
	private static final String REEMPLAZAR = ":N|:S";

	private final String DIAGNOSTICO_ANSIEDAD_25 = "1";
	private final String DIAGNOSTICO_DEPRESION_25 = "2";
	private final String DIAGNOSTICO_ESQUIZOFRENIA_25 = "3";
	private final String DIAGNOSTICO_DEFICIT_HIPERACTIVIDAD_25 = "4";
	private final String DIAGNOSTICO_SUSTANCIAS_PSICOACTIVAS_25 = "5";
	private final String DIAGNOSTICO_TRASTORNO_BIPOLAR_25 = "6";

	private static final SimpleDateFormat formato_fecha = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final String NOMBRE_RESPUESTA_CONSULTA_DX = "aplica";
	public static final String FECHA_RESPUESTA_CONSULTA_DX = "fecha_dx";

	public interface IProceso {
		void iniciar();

		void setPosicion(int pos);

		void setMaximo(long maximo);

		void incrementar();

		void setTitle(String titulo);

		void doMostrarInfo();
	}

	public void generarArchivoResolucion4505(Administradora administradora,
			Date fecha_inicio, Date fecha_final, File archivo_guardado,
			String separador, String formato, Boolean generar_meses,
			Empresa empresa, ServiceLocatorWeb serviceLocator) throws Exception {

		// proceso.setTitulo("ESTABLECIENDO DIRECTORIO TEMPOTAL");
		File file_archivos = new File(archivo_guardado.getAbsolutePath()
				+ File.separator + "plain");
		if (!file_archivos.exists()) {
			file_archivos.mkdir();
		}

		List<File> files_archivos_resolucion = new ArrayList<File>();
		Date fecha_inicial_temp = fecha_inicio;
		Date fecha_final_temp = fecha_final;
		int mes = getMes(fecha_inicio);
		int mes_final = getMes(fecha_final);
		int cantidad_archivos = 1;
		do {
			// proceso.setTitulo("RECOPILANDO INFORMACIÓN");
			// verificamos las fecha en las cuales se va a generar la
			// informacion
			if (generar_meses) {
				fecha_inicial_temp = getFecha(mes, fecha_inicio, true);
				fecha_final_temp = getFecha(mes++, fecha_final, false);
			}

			// Realizamos nuestro mapa de busqueda
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("fecha_inicio", fecha_inicial_temp);
			params.put("fecha_final", fecha_final_temp);

			// realizamos la consulta, con su archivo correspondiente
			File file = getArchivo(params, cantidad_archivos++, fecha_inicio,
					fecha_final_temp, formato, separador, empresa,
					administradora, serviceLocator, file_archivos);
			if (file != null) {
				files_archivos_resolucion.add(file);
			}
		} while (generar_meses && mes <= mes_final);

		// enviamos al usuario
		if (files_archivos_resolucion.isEmpty()) {
			throw new ValidacionRunTimeException(
					"No se encontro ningún registro");
		} else {
			// proceso.setTitulo("DESCARGANDO");
			if (files_archivos_resolucion.size() == 1) {
				Filedownload.save(files_archivos_resolucion.get(0),
						getTipoContenidoMIME(formato));
			} else {
				Filedownload.save(
						empaquetarZip(files_archivos_resolucion,
								archivo_guardado, administradora),
						"application/zip");
			}
		}
		// eliminamos el directorio temporal
		archivo_guardado.delete();
	}

	private String getTipoContenidoMIME(String formato) {
		return "application/" + formato;
	}

	private int getMes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	private Date getFecha(int mes, Date fecha, boolean isInicial) {
		if (mes == getMes(fecha)) {
			return fecha;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			calendar.set(Calendar.MONTH, mes);
			calendar.set(Calendar.DAY_OF_MONTH,
					isInicial ? 1 : calendar.getMaximum(Calendar.DAY_OF_MONTH));
			return calendar.getTime();
		}
	}

	private File empaquetarZip(List<File> files_archivos_resolucion,
			File directorio, Administradora administradora) throws Exception {
		String nombreArchivoZip = directorio.getAbsolutePath() + File.separator
				+ "Resolucion4505" + administradora.getCodigo()
				+ FORMATO_COMPRIMIDO;
		BufferedInputStream origin = null;
		FileOutputStream dest = new FileOutputStream(nombreArchivoZip);
		ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(dest));
		byte data[] = new byte[BUFFER];
		for (File file : files_archivos_resolucion) {
			FileInputStream fi = new FileInputStream(file);
			origin = new BufferedInputStream(fi, BUFFER);
			// creamos una entrada zip
			ZipEntry entry = new ZipEntry(file.getName());
			// agregamos entradas zip al archivo
			out.putNextEntry(entry);
			int count;
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
		}
		out.close();
		FileInputStream archivo = new FileInputStream(nombreArchivoZip);
		archivo.read(new byte[archivo.available()]);
		archivo.close();
		File archivo_zip = new File(nombreArchivoZip);
		return archivo_zip;
	}

	/**
	 * TOMA DE INFORMACION 4505
	 * 
	 * @param serviceLocator
	 * @param file_archivos
	 * @param proceso
	 * */
	private File getArchivo(Map<String, Object> params, int numero_archivo,
			final Date fecha_inicio, final Date fecha_final, String formato,
			String separador, final Empresa empresa,
			final Administradora administradora,
			final ServiceLocatorWeb serviceLocator, File file_archivos)
			throws Exception {

		String nombre = IResolucion4505Constantes.MODULO_INFORMACION
				+ IResolucion4505Constantes.TIPO_FUENTE
				+ IResolucion4505Constantes.TEMA_INFORMACION
				+ new SimpleDateFormat("yyyyMMdd").format(fecha_final)
				+ empresa.getTipo_identificacion().toUpperCase()
				+ ValidadorArchivoUtils.validarNIT(empresa
						.getNro_identificacion()) + empresa.getRegimen()
				+ (numero_archivo < 10 ? "0" + numero_archivo : numero_archivo)
				+ "." + formato;

		if (formato.equals(GeneradorArchivo4505State.TIPO_ARCHIVO_PLANO)
				&& nombre.length() != IResolucion4505Constantes.LONGITUD_VALIDAD_NOMBRE_ARCHIVO) {
			throw new ValidacionRunTimeException(
					"El nombre no contiene los "
							+ IResolucion4505Constantes.LONGITUD_VALIDAD_NOMBRE_ARCHIVO
							+ " caracteres que se exigen en la resolucion.! El nombre generado: "
							+ nombre);
		}

		// alimentamos informacion inicial del archivo
		final GeneradorArchivo4505Impl generadorArchivo4505State = GeneradorArchivo4505Factory
				.getGeneradorArchivo4505S(formato);
		generadorArchivo4505State.setNombre(nombre);
		generadorArchivo4505State
				.setRegistroControl(IResolucion4505Constantes.REGISTRO_CONTROL);
		generadorArchivo4505State
				.setCodigo_administradora(ValidadorArchivoUtils
						.validarCodigoAdministradora(administradora.getCodigo()));
		generadorArchivo4505State.setFecha_inicio(formato_fecha
				.format(fecha_inicio));
		generadorArchivo4505State.setFecha_final(formato_fecha
				.format(fecha_final));
		generadorArchivo4505State.setSeparador(separador);

		// En este cache se van a adicionar
		final Resolucion4505Service resolucion4505Service = serviceLocator
				.getServicio(Resolucion4505Service.class);
		resolucion4505Service.listar(params, new OnResultSet() {

			@Override
			public LineaBean onNuevo(ResultSet resultado, long contador,
					long total) {
				LineaBean lineaBean = new LineaBean();
				lineaBean.setVar0("2");
				lineaBean.setVar1(contador + "");
				lineaBean.setVar2(generadorArchivo4505State
						.getCodigo_administradora());
//				log.info("contador: " + contador);
				try {
					maperarResultado(generadorArchivo4505State, resultado,
							administradora, resolucion4505Service, fecha_inicio,
							fecha_final, serviceLocator, empresa, lineaBean);
				} catch (Exception e) { 
					e.printStackTrace();
				}
				return lineaBean;
			}

			@Override
			public void onFinalizar() {
				generadorArchivo4505State.onMontarCabecera();
			}

			@Override
			public void onCalcularTotalVerificar(long total) {
				// proceso.setMaximo(total);
				generadorArchivo4505State.setTotal_verificar(total);
			}

			@Override
			public void onComplementar(List<LineaBean> listado) {
			}
		});

		// retornamos archivo de respuesta
		return generadorArchivo4505State.getFile(file_archivos);
	}

	protected boolean maperarResultado(
			GeneradorArchivo4505Impl generadorArchivo4505Impl,
			ResultSet resultado, Administradora administradora,
			Resolucion4505Service resolucion4505Service, Date fecha_incio,
			Date fecha_final, ServiceLocatorWeb serviceLocator, Empresa empresa,
			LineaBean lineaBean) throws Exception{
			boolean aplica = false;

			String id_paciente = resultado.getString(1);

			lineaBean.setId_paciente(id_paciente);

			lineaBean.setVar3(resultado.getString(2));
			lineaBean.setVar4(resultado.getString(3));
			lineaBean.setVar5(resultado.getString(4));
			lineaBean.setVar6(resultado.getString(5));
			lineaBean.setVar7(resultado.getString(6));
			lineaBean.setVar8(resultado.getString(7));
			lineaBean.setVar9(resultado.getString(8));
			lineaBean.setVar10(resultado.getString(9));
			lineaBean.setVar11(resultado.getString(10));
			lineaBean.setVar12(resultado.getString(11));
			lineaBean.setVar13(resultado.getString(12));

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(lineaBean.getVar9()), fecha_final);

			int anio = map_edad.get("anios");
			int meses = map_edad.get("meses");
			int dias = map_edad.get("dias");

			lineaBean.setAnio(anio);
			lineaBean.setMes(meses);
			lineaBean.setDia(dias);

			// consultamos variable 14
			StringBuilder sql14 = new StringBuilder();
			sql14.append("SELECT resolucion4505.fn_variable14(");
			sql14.append("'" + administradora.getCodigo_empresa() + "', ");
			sql14.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql14.append("'" + administradora.getCodigo() + "', ");
			sql14.append("'" + id_paciente + "', ");
			sql14.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql14.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql14.append(anio + ", '");
			sql14.append(lineaBean.getVar10() + "');");

			String var14 = resolucion4505Service.consultar(sql14.toString());

			if (!aplica && var14.contains(INCLUIR)) {
				aplica = true;
			}
			lineaBean.setVar14(var14.replaceAll(REEMPLAZAR, ""));

			if (aplica) {
				lineaBean.setAplica(true);
			}
			
			// variable 15
			StringBuilder sql15 = new StringBuilder();
			sql15.append("SELECT resolucion4505.fn_variable15(");
			sql15.append("'" + administradora.getCodigo_empresa() + "', ");
			sql15.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql15.append("'" + administradora.getCodigo() + "', ");
			sql15.append("'" + lineaBean.getId_paciente() + "', ");
			sql15.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql15.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql15.append(lineaBean.getAnio() + ", ");
			sql15.append(lineaBean.getMes() + ",");
			sql15.append("'" + lineaBean.getVar10() + "', ");
			sql15.append("'" + lineaBean.getVar3() + "');");

			String var15 = resolucion4505Service.consultar(sql15.toString());
			if (!lineaBean.isAplica() && var15.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar15(var15.replaceAll(REEMPLAZAR, ""));

			// variable 16
			StringBuilder sql16 = new StringBuilder();
			sql16.append("SELECT resolucion4505.fn_variable16(");
			sql16.append("'" + administradora.getCodigo_empresa() + "', ");
			sql16.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql16.append("'" + administradora.getCodigo() + "', ");
			sql16.append("'" + lineaBean.getId_paciente() + "', ");
			sql16.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql16.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql16.append(lineaBean.getAnio() + ", ");
			sql16.append("'" + lineaBean.getVar10() + "', ");
			sql16.append("'" + lineaBean.getVar14() + "');");

			String var16 = resolucion4505Service.consultar(sql16.toString());
			if (!lineaBean.isAplica() && var16.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar16(var16.replaceAll(REEMPLAZAR, ""));
			
			// variable 17
			StringBuilder sql17 = new StringBuilder();
			sql17.append("SELECT resolucion4505.fn_variable17(");
			sql17.append("'" + administradora.getCodigo_empresa() + "', ");
			sql17.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql17.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql17.append("'" + lineaBean.getId_paciente() + "', ");
			sql17.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql17.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql17.append(lineaBean.getMes() + ");");
			String var17 = resolucion4505Service.consultar(sql17.toString());
			if (!lineaBean.isAplica() && var17.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar17(var17.replaceAll(REEMPLAZAR, ""));

			// variable 18
			StringBuilder sql18 = new StringBuilder();
			sql18.append("SELECT resolucion4505.fn_variable18(");
			sql18.append("'" + administradora.getCodigo_empresa() + "', ");
			sql18.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql18.append("'" + administradora.getCodigo() + "', ");
			sql18.append("'" + lineaBean.getId_paciente() + "', ");
			sql18.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql18.append("'" + formato_fecha.format(fecha_final) + "');");
			String var18 = resolucion4505Service.consultar(sql18.toString());
			if (!lineaBean.isAplica() && var18.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar18(var18.replaceAll(REEMPLAZAR, ""));

			
			// variable 19
			StringBuilder sql19 = new StringBuilder();
			sql19.append("SELECT resolucion4505.fn_variable19(");
			sql19.append("'" + administradora.getCodigo_empresa() + "', ");
			sql19.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql19.append("'" + administradora.getCodigo() + "', ");
			sql19.append("'" + lineaBean.getId_paciente() + "', ");
			sql19.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql19.append("'" + formato_fecha.format(fecha_final) + "');");
			String var19 = resolucion4505Service.consultar(sql19.toString());
			if (!lineaBean.isAplica() && var19.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar19(var19.replaceAll(REEMPLAZAR, ""));

			// variable 20
			StringBuilder sql20 = new StringBuilder();
			sql20.append("SELECT resolucion4505.fn_variable20(");
			sql20.append("'" + administradora.getCodigo_empresa() + "', ");
			sql20.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql20.append("'" + administradora.getCodigo() + "', ");
			sql20.append("'" + lineaBean.getId_paciente() + "', ");
			sql20.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql20.append("'" + formato_fecha.format(fecha_final) + "');");
			String var20 = resolucion4505Service.consultar(sql20.toString());
			if (!lineaBean.isAplica() && var20.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar20(var20.replaceAll(REEMPLAZAR, ""));
			
			StringBuilder sql21_64 = new StringBuilder();
			sql21_64.append("SELECT resolucion4505.fn_variable21_64(");
			sql21_64.append("'" + administradora.getCodigo_empresa() + "', ");
			sql21_64.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql21_64.append("'" + administradora.getCodigo() + "', ");
			sql21_64.append("'" + lineaBean.getId_paciente() + "', ");
			sql21_64.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql21_64.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql21_64.append(lineaBean.getAnio() + "); ");
			String var21_64 = resolucion4505Service.consultar(sql21_64.toString());
			if (!lineaBean.isAplica() && var21_64.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			String[] sql21_64_vec = var21_64.replaceAll(REEMPLAZAR, "")
					.replace("|", ":").split(":");
			lineaBean.setVar21(sql21_64_vec[0]);
			lineaBean.setVar64(sql21_64_vec[1]);
			
			// variable 22 - 23
			StringBuilder sql22_23 = new StringBuilder();
			sql22_23.append("SELECT resolucion4505.fn_variable22_23(");
			sql22_23.append("'" + administradora.getCodigo_empresa() + "', ");
			sql22_23.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql22_23.append("'" + administradora.getCodigo() + "', ");
			sql22_23.append("'" + lineaBean.getId_paciente() + "', ");
			sql22_23.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql22_23.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql22_23.append(lineaBean.getMes() + ", ");
			sql22_23.append("'" + lineaBean.getVar10() + "');");
			String var22_23 = resolucion4505Service.consultar(sql22_23.toString());
			if (!lineaBean.isAplica() && var22_23.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			var22_23 = var22_23.replaceAll(REEMPLAZAR, "");
			String[] var22_23_vec = var22_23.replace("|", ":").split(":");
			lineaBean.setVar22(var22_23_vec[0]);
			lineaBean.setVar23(var22_23_vec[1]);

			// variable 24
			StringBuilder sql24 = new StringBuilder();
			sql24.append("SELECT resolucion4505.fn_variable24(");
			sql24.append("'" + administradora.getCodigo_empresa() + "', ");
			sql24.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql24.append("'" + administradora.getCodigo() + "', ");
			sql24.append("'" + lineaBean.getId_paciente() + "', ");
			sql24.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql24.append("'" + formato_fecha.format(fecha_final) + "');");
			String var24 = resolucion4505Service.consultar(sql24.toString());
			if (!lineaBean.isAplica() && var24.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar24(var24.replaceAll(REEMPLAZAR, ""));

			// variable 25
			StringBuilder sql25 = new StringBuilder();
			sql25.append("SELECT resolucion4505.fn_variable25(");
			sql25.append("'" + administradora.getCodigo_empresa() + "', ");
			sql25.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql25.append("'" + administradora.getCodigo() + "', ");
			sql25.append("'" + lineaBean.getId_paciente() + "', ");
			sql25.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql25.append("'" + formato_fecha.format(fecha_final) + "');");
			String var25 = resolucion4505Service.consultar(sql25.toString());
			if (!lineaBean.isAplica() && var25.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			var25 = var25.replaceAll(REEMPLAZAR, "");
			String[] var25_vec = var25.replace("|", ":").split(":");
			lineaBean.setVar25(var25_vec[0]);
			
			String fecha_dx_25_string = var25_vec[1];
			Date fecha_dx_25 = null;
			if (!fecha_dx_25_string.equals("0000-00-00")) {
				fecha_dx_25 = java.sql.Date.valueOf(fecha_dx_25_string);
			}
			
			// variable 26
			StringBuilder sql26 = new StringBuilder();
			sql26.append("SELECT resolucion4505.fn_variable26(");
			sql26.append("'" + administradora.getCodigo_empresa() + "', ");
			sql26.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql26.append("'" + administradora.getCodigo() + "', ");
			sql26.append("'" + lineaBean.getId_paciente() + "', ");
			sql26.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql26.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql26.append("'" + lineaBean.getVar10() + "', ");
			sql26.append(lineaBean.getAnio() + ");");
			String var26 = resolucion4505Service.consultar(sql26.toString());
			if (!lineaBean.isAplica() && var26.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar26(var26.replaceAll(REEMPLAZAR, ""));

			// variable 27
			StringBuilder sql27 = new StringBuilder();
			sql27.append("SELECT resolucion4505.fn_variable27(");
			sql27.append("'" + administradora.getCodigo_empresa() + "', ");
			sql27.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql27.append("'" + administradora.getCodigo() + "', ");
			sql27.append("'" + lineaBean.getId_paciente() + "', ");
			sql27.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql27.append("'" + formato_fecha.format(fecha_final) + "');");
			String var27 = resolucion4505Service.consultar(sql27.toString());
			if (!lineaBean.isAplica() && var27.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar27(var27.replaceAll(REEMPLAZAR, ""));

			// variable 28
			StringBuilder sql28 = new StringBuilder();
			sql28.append("SELECT resolucion4505.fn_variable28(");
			sql28.append("'" + administradora.getCodigo_empresa() + "', ");
			sql28.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql28.append("'" + administradora.getCodigo() + "', ");
			sql28.append("'" + lineaBean.getId_paciente() + "', ");
			sql28.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql28.append("'" + formato_fecha.format(fecha_final) + "');");
			String var28 = resolucion4505Service.consultar(sql28.toString());
			if (!lineaBean.isAplica() && var28.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar28(var28.replaceAll(REEMPLAZAR, ""));
			
			// variable 29_a_32
			StringBuilder sql29_a_32 = new StringBuilder();
			sql29_a_32.append("SELECT resolucion4505.fn_variable29_a_32(");
			sql29_a_32.append("'" + administradora.getCodigo_empresa() + "', ");
			sql29_a_32.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql29_a_32.append("'" + administradora.getCodigo() + "', ");
			sql29_a_32.append("'" + lineaBean.getId_paciente() + "', ");
			sql29_a_32.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql29_a_32.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql29_a_32.append(lineaBean.getAnio() + ");");
			String var29_a_32 = resolucion4505Service.consultar(sql29_a_32
					.toString());
			if (!lineaBean.isAplica() && var29_a_32.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}

			String[] v29_a_32 = var29_a_32.replaceAll(REEMPLAZAR, "")
					.replace("|", ":").split(":");
			lineaBean.setVar29(v29_a_32[0]);
			lineaBean.setVar30(v29_a_32[1]);
			lineaBean.setVar31(v29_a_32[2]);
			lineaBean.setVar32(v29_a_32[3]);

			// variable 33
			StringBuilder sql33 = new StringBuilder();
			sql33.append("SELECT resolucion4505.fn_variable33(");
			sql33.append("'" + administradora.getCodigo_empresa() + "', ");
			sql33.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql33.append("'" + administradora.getCodigo() + "', ");
			sql33.append("'" + lineaBean.getId_paciente() + "', ");
			sql33.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql33.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql33.append("'" + lineaBean.getVar10() + "', ");
			sql33.append("'" + lineaBean.getVar14() + "');");
			String var33 = resolucion4505Service.consultar(sql33.toString());
			if (!lineaBean.isAplica() && var33.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar33(var33.replaceAll(REEMPLAZAR, ""));
			
			
			// variable 34
			StringBuilder sql34 = new StringBuilder();
			sql34.append("SELECT resolucion4505.fn_variable34(");
			sql34.append("'" + administradora.getCodigo_empresa() + "', ");
			sql34.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql34.append("'" + administradora.getCodigo() + "', ");
			sql34.append("'" + lineaBean.getId_paciente() + "', ");
			sql34.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql34.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql34.append(lineaBean.getAnio() + ");");
			String var34 = resolucion4505Service.consultar(sql34.toString());
			if (!lineaBean.isAplica() && var34.contains(INCLUIR)) {
				lineaBean.setAplica(false);
			}
			lineaBean.setVar34(var34.replaceAll(REEMPLAZAR, ""));

			// variable 35
			StringBuilder sql35 = new StringBuilder();
			sql35.append("SELECT resolucion4505.fn_variable35(");
			sql35.append("'" + administradora.getCodigo_empresa() + "', ");
			sql35.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql35.append("'" + administradora.getCodigo() + "', ");
			sql35.append("'" + lineaBean.getId_paciente() + "', ");
			sql35.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql35.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql35.append(lineaBean.getAnio() + ");");
			String var35 = resolucion4505Service.consultar(sql35.toString());
			if (!lineaBean.isAplica() && var35.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar35(var35.replaceAll(REEMPLAZAR, ""));

			// variable 36
			StringBuilder sql36 = new StringBuilder();
			sql36.append("SELECT resolucion4505.fn_variable36(");
			sql36.append("'" + administradora.getCodigo_empresa() + "', ");
			sql36.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql36.append("'" + administradora.getCodigo() + "', ");
			sql36.append("'" + lineaBean.getId_paciente() + "', ");
			sql36.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql36.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql36.append(lineaBean.getAnio() + ");");
			String var36 = resolucion4505Service.consultar(sql36.toString());
			if (!lineaBean.isAplica() && var36.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar36(var36.replaceAll(REEMPLAZAR, ""));
			
			
			StringBuilder sql37 = new StringBuilder();
			sql37.append("SELECT resolucion4505.fn_variable37(");
			sql37.append("'" + administradora.getCodigo_empresa() + "', ");
			sql37.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql37.append("'" + administradora.getCodigo() + "', ");
			sql37.append("'" + lineaBean.getId_paciente() + "', ");
			sql37.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql37.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql37.append(lineaBean.getAnio() + ");");
			String var37 = resolucion4505Service.consultar(sql37.toString());
			if (!lineaBean.isAplica() && var37.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar37(var37.replaceAll(REEMPLAZAR, ""));

			// variable 38
			StringBuilder sql38 = new StringBuilder();
			sql38.append("SELECT resolucion4505.fn_variable38(");
			sql38.append("'" + administradora.getCodigo_empresa() + "', ");
			sql38.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql38.append("'" + administradora.getCodigo() + "', ");
			sql38.append("'" + lineaBean.getId_paciente() + "', ");
			sql38.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql38.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql38.append(lineaBean.getAnio() + ");");
			String var38 = resolucion4505Service.consultar(sql38.toString());
			if (!lineaBean.isAplica() && var38.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar38(var38.replaceAll(REEMPLAZAR, ""));
			
			
			// variable 39
			StringBuilder sql39 = new StringBuilder();
			sql39.append("SELECT resolucion4505.fn_variable39(");
			sql39.append("'" + administradora.getCodigo_empresa() + "', ");
			sql39.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql39.append("'" + administradora.getCodigo() + "', ");
			sql39.append("'" + lineaBean.getId_paciente() + "', ");
			sql39.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql39.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql39.append(lineaBean.getAnio() + ");");
			String var39 = resolucion4505Service.consultar(sql39.toString());
			if (!lineaBean.isAplica() && var39.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar39(var39.replaceAll(REEMPLAZAR, ""));

			// variable 40
			StringBuilder sql40 = new StringBuilder();
			sql40.append("SELECT resolucion4505.fn_variable40(");
			sql40.append("'" + administradora.getCodigo_empresa() + "', ");
			sql40.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql40.append("'" + administradora.getCodigo() + "', ");
			sql40.append("'" + lineaBean.getId_paciente() + "', ");
			sql40.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql40.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql40.append(lineaBean.getMes() + ");");
			String var40 = resolucion4505Service.consultar(sql40.toString());
			if (!lineaBean.isAplica() && var40.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar40(var40.replaceAll(REEMPLAZAR, ""));
			
			
			// variable 41
			StringBuilder sql41 = new StringBuilder();
			sql41.append("SELECT resolucion4505.fn_variable41(");
			sql41.append("'" + administradora.getCodigo_empresa() + "', ");
			sql41.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql41.append("'" + administradora.getCodigo() + "', ");
			sql41.append("'" + lineaBean.getId_paciente() + "', ");
			sql41.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql41.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql41.append(lineaBean.getAnio() + ");");
			String var41 = resolucion4505Service.consultar(sql41.toString());
			if (!lineaBean.isAplica() && var41.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar41(var41.replaceAll(REEMPLAZAR, ""));

			// variable 42
			StringBuilder sql42 = new StringBuilder();
			sql42.append("SELECT resolucion4505.fn_variable42(");
			sql42.append("'" + administradora.getCodigo_empresa() + "', ");
			sql42.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql42.append("'" + administradora.getCodigo() + "', ");
			sql42.append("'" + lineaBean.getId_paciente() + "', ");
			sql42.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql42.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql42.append(lineaBean.getAnio() + ");");
			String var42 = resolucion4505Service.consultar(sql42.toString());
			if (!lineaBean.isAplica() && var42.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar42(var42.replaceAll(REEMPLAZAR, ""));

			// variable 43
			StringBuilder sql43 = new StringBuilder();
			sql43.append("SELECT resolucion4505.fn_variable43(");
			sql43.append("'" + administradora.getCodigo_empresa() + "', ");
			sql43.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql43.append("'" + administradora.getCodigo() + "', ");
			sql43.append("'" + lineaBean.getId_paciente() + "', ");
			sql43.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql43.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql43.append(lineaBean.getAnio() + ");");
			String var43 = resolucion4505Service.consultar(sql43.toString());
			if (!lineaBean.isAplica() && var43.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar43(var43.replaceAll(REEMPLAZAR, ""));
			
			
			// variable 44
			StringBuilder sql44 = new StringBuilder();
			sql44.append("SELECT resolucion4505.fn_variable44(");
			sql44.append("'" + administradora.getCodigo_empresa() + "', ");
			sql44.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql44.append("'" + administradora.getCodigo() + "', ");
			sql44.append("'" + lineaBean.getId_paciente() + "', ");
			sql44.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql44.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql44.append(lineaBean.getAnio() + ");");
			String var44 = resolucion4505Service.consultar(sql44.toString());
			if (!lineaBean.isAplica() && var44.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar44(var44.replaceAll(REEMPLAZAR, ""));

			// variable 45
			StringBuilder sql45 = new StringBuilder();
			sql45.append("SELECT resolucion4505.fn_variable45(");
			sql45.append("'" + administradora.getCodigo_empresa() + "', ");
			sql45.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql45.append("'" + administradora.getCodigo() + "', ");
			sql45.append("'" + lineaBean.getId_paciente() + "', ");
			sql45.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql45.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql45.append(lineaBean.getAnio() + ");");
			String var45 = resolucion4505Service.consultar(sql45.toString());
			if (!lineaBean.isAplica() && var45.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar45(var45.replaceAll(REEMPLAZAR, ""));

			// variable 46
			StringBuilder sql46 = new StringBuilder();
			sql46.append("SELECT resolucion4505.fn_variable46(");
			sql46.append("'" + administradora.getCodigo_empresa() + "', ");
			sql46.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql46.append("'" + administradora.getCodigo() + "', ");
			sql46.append("'" + lineaBean.getId_paciente() + "', ");
			sql46.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql46.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql46.append("'" + lineaBean.getVar10() + "');");
			String var46 = resolucion4505Service.consultar(sql46.toString());
			if (!lineaBean.isAplica() && var46.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar46(var46.replaceAll(REEMPLAZAR, ""));
			
			
			// variable 47
			StringBuilder sql47 = new StringBuilder();
			sql47.append("SELECT resolucion4505.fn_variable47(");
			sql47.append("'" + administradora.getCodigo_empresa() + "', ");
			sql47.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql47.append("'" + administradora.getCodigo() + "', ");
			sql47.append("'" + lineaBean.getId_paciente() + "', ");
			sql47.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql47.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql47.append("'" + lineaBean.getVar10() + "', ");
			sql47.append(lineaBean.getAnio() + ");");
			String var47 = resolucion4505Service.consultar(sql47.toString());
			if (!lineaBean.isAplica() && var47.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar47(var47.replaceAll(REEMPLAZAR, ""));

			// variable 48
			StringBuilder sql48 = new StringBuilder();
			sql48.append("SELECT resolucion4505.fn_variable48(");
			sql48.append("'" + administradora.getCodigo_empresa() + "', ");
			sql48.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql48.append("'" + administradora.getCodigo() + "', ");
			sql48.append("'" + lineaBean.getId_paciente() + "', ");
			sql48.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql48.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql48.append(lineaBean.getAnio() + ", ");
			sql48.append("'" + lineaBean.getVar3() + "', ");
			sql48.append("'" + lineaBean.getVar4() + "');");
			String var48 = resolucion4505Service.consultar(sql48.toString());
			if (!lineaBean.isAplica() && var48.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar48(var48.replaceAll(REEMPLAZAR, ""));

			// variable 49_50
			StringBuilder sql49_50 = new StringBuilder();
			sql49_50.append("SELECT resolucion4505.fn_variable49_50(");
			sql49_50.append("'" + administradora.getCodigo_empresa() + "', ");
			sql49_50.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql49_50.append("'" + administradora.getCodigo() + "', ");
			sql49_50.append("'" + lineaBean.getId_paciente() + "', ");
			sql49_50.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql49_50.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql49_50.append("'" + lineaBean.getVar10() + "', ");
			sql49_50.append(lineaBean.getAnio() + ");");
			String var49_50 = resolucion4505Service.consultar(sql49_50.toString());

			boolean incluir_49_50 = var49_50.contains(INCLUIR);
			if (!lineaBean.isAplica() && incluir_49_50) {
				lineaBean.setAplica(true);
			}

			String[] var49_50_array = var49_50.replaceAll(REEMPLAZAR, "")
					.replace("|", ":").split(":");

			lineaBean.setVar49(var49_50_array[0]);
			lineaBean.setVar50(var49_50_array[1]);

			String respuesta_50 = var49_50.replace("|", ":").split(":")[1];
			if (incluir_49_50 && respuesta_50.contains(INCLUIR)) {
				Calendar calendarFechaEgreso = Calendar.getInstance();
				calendarFechaEgreso.setTime(java.sql.Date.valueOf(respuesta_50
						.replaceAll(REEMPLAZAR, "")));

				Calendar calendarParto = Calendar.getInstance();
				calendarParto.setTime(java.sql.Date.valueOf(lineaBean.getVar49()));
				calendarParto.set(Calendar.DAY_OF_MONTH,
						calendarParto.get(Calendar.DAY_OF_MONTH) + 2);

				if (calendarParto.getTime().after(calendarFechaEgreso.getTime())) {
					calendarParto.set(Calendar.DAY_OF_MONTH,
							calendarParto.get(Calendar.DAY_OF_MONTH) - 1);
					lineaBean
							.setVar50(formato_fecha.format(calendarParto.getTime()));
				}
			} else if (incluir_49_50) {
				lineaBean.setVar50(lineaBean.getVar49());
			}
			
			
			// variable 51
			StringBuilder sql51 = new StringBuilder();
			sql51.append("SELECT resolucion4505.fn_variable51(");
			sql51.append("'" + administradora.getCodigo_empresa() + "', ");
			sql51.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql51.append("'" + administradora.getCodigo() + "', ");
			sql51.append("'" + lineaBean.getId_paciente() + "', ");
			sql51.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql51.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql51.append("'" + lineaBean.getVar10() + "', ");
			sql51.append("'" + lineaBean.getVar14() + "');");
			String var51 = resolucion4505Service.consultar(sql51.toString());
			if (!lineaBean.isAplica() && var51.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar51(var51.replaceAll(REEMPLAZAR, ""));

			// variable 52
			StringBuilder sql52 = new StringBuilder();
			sql52.append("SELECT resolucion4505.fn_variable52(");
			sql52.append("'" + administradora.getCodigo_empresa() + "', ");
			sql52.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql52.append("'" + administradora.getCodigo() + "', ");
			sql52.append("'" + lineaBean.getId_paciente() + "', ");
			sql52.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql52.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql52.append(lineaBean.getDia() + ", ");
			sql52.append("'" + lineaBean.getVar9() + "');");
			String var52 = resolucion4505Service.consultar(sql52.toString());
			if (!lineaBean.isAplica() && var52.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar52(var52.replaceAll(REEMPLAZAR, ""));

			// variable 53
			StringBuilder sql53 = new StringBuilder();
			sql53.append("SELECT resolucion4505.fn_variable53(");
			sql53.append("'" + administradora.getCodigo_empresa() + "', ");
			sql53.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql53.append("'" + administradora.getCodigo() + "', ");
			sql53.append("'" + lineaBean.getId_paciente() + "', ");
			sql53.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql53.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql53.append(lineaBean.getAnio() + ", ");
			sql53.append("'" + lineaBean.getVar9() + "');");
			String var53 = resolucion4505Service.consultar(sql53.toString());
			if (!lineaBean.isAplica() && var53.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			lineaBean.setVar53(var53.replaceAll(REEMPLAZAR, ""));
			
			// variable 54_55
			StringBuilder sql54_55 = new StringBuilder();
			sql54_55.append("SELECT resolucion4505.fn_variable54_55(");
			sql54_55.append("'" + administradora.getCodigo_empresa() + "', ");
			sql54_55.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql54_55.append("'" + administradora.getCodigo() + "', ");
			sql54_55.append("'" + lineaBean.getId_paciente() + "', ");
			sql54_55.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql54_55.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql54_55.append(lineaBean.getAnio() + ", ");
			sql54_55.append("'" + lineaBean.getVar9() + "', ");
			sql54_55.append("'" + lineaBean.getVar10() + "');");
			String var54_55 = resolucion4505Service.consultar(sql54_55.toString());
			if (!lineaBean.isAplica() && var54_55.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			String[] var54_55_array = var54_55.replaceAll(REEMPLAZAR, "")
					.replace("|", ":").split(":");
			lineaBean.setVar54(var54_55_array[0]);
			lineaBean.setVar55(var54_55_array[1]);

			// variable 56, 57, 58

			// fecha de 9 meses
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha_final);
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 9);
			Date fecha_9_meses = calendar.getTime();

			StringBuilder sql56_a_58 = new StringBuilder();
			sql56_a_58.append("SELECT resolucion4505.fn_variable56_a_58(");
			sql56_a_58.append("'" + administradora.getCodigo_empresa() + "', ");
			sql56_a_58.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql56_a_58.append("'" + administradora.getCodigo() + "', ");
			sql56_a_58.append("'" + lineaBean.getId_paciente() + "', ");
			sql56_a_58.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql56_a_58.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql56_a_58.append("'" + lineaBean.getVar10() + "', ");
			sql56_a_58.append("'" + lineaBean.getVar14() + "', ");
			sql56_a_58.append("'" + lineaBean.getVar3() + "', ");
			sql56_a_58.append("'" + lineaBean.getVar4() + "', ");
			sql56_a_58.append("'" + formato_fecha.format(fecha_9_meses) + "');");
			String var56_a_58 = resolucion4505Service.consultar(sql56_a_58
					.toString());
			if (!lineaBean.isAplica() && var56_a_58.contains(INCLUIR)) {
				lineaBean.setAplica(true);
			}
			String[] var56_a_58_array = var56_a_58.replaceAll(REEMPLAZAR, "")
					.replace("|", ":").split(":");
			lineaBean.setVar56(var56_a_58_array[0]);
			lineaBean.setVar57(var56_a_58_array[1]);
			lineaBean.setVar58(var56_a_58_array[2]);
			
			// 59, 60, 61
			StringBuilder sql59_a_61 = new StringBuilder();
			sql59_a_61.append("SELECT resolucion4505.fn_variable59_a_61(");
			sql59_a_61.append("'" + administradora.getCodigo_empresa() + "', ");
			sql59_a_61.append("'" + administradora.getCodigo_sucursal() + "', ");
			sql59_a_61.append("'" + administradora.getCodigo() + "', ");
			sql59_a_61.append("'" + lineaBean.getId_paciente() + "', ");
			sql59_a_61.append("'" + formato_fecha.format(fecha_incio) + "', ");
			sql59_a_61.append("'" + formato_fecha.format(fecha_final) + "', ");
			sql59_a_61.append("'" + lineaBean.getVar10() + "', ");
			sql59_a_61.append("'" + lineaBean.getVar14() + "');");
			String var59_a_61 = resolucion4505Service.consultar(sql59_a_61
					.toString());
			if (!lineaBean.isAplica() && var59_a_61.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			String[] var59_a_61_array = var59_a_61.replaceAll(REEMPLAZAR, "")
					.replace("|", ":").split(":");
			lineaBean.setVar59(var59_a_61_array[0]);
			lineaBean.setVar60(var59_a_61_array[1]);
			lineaBean.setVar61(var59_a_61_array[2]);
			
			// pendientes optimizar
			String var_62 = consultarVariable62(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean.getAnio());
			if (!lineaBean.isAplica() && var_62.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar62(var_62.replaceAll(REEMPLAZAR, ""));

			String var_63 = consultarVariable63(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean.getAnio());
			if (!lineaBean.isAplica() && var_63.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar63(var_63.replaceAll(REEMPLAZAR, ""));

			// String var_64 = consultarVariable64(codigo_administradora,
			// id_paciente, fecha_incio, fecha_final, lineaBean);
			// if(!aplica && var_64.contains(INCLUIR)){
			// aplica = true;
			// }
			// lineaBean.setVar64(var_64.replaceAll(REEMPLAZAR, ""));

			String var_65 = consultarVariable65(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_65.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar65(var_65.replaceAll(REEMPLAZAR, ""));

			String var_66 = consultarVariable66(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_66.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar66(var_66.replaceAll(REEMPLAZAR, ""));

			String var_67 = consultarVariable67(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_67.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar67(var_67.replaceAll(REEMPLAZAR, ""));

			String var_68 = consultarVariable68(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_68.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar68(var_68.replaceAll(REEMPLAZAR, ""));

			String var_69 = consultarVariable69(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_69.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar69(var_69.replaceAll(REEMPLAZAR, ""));

			String var_70 = consultarVariable70(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_70.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar70(var_70.replaceAll(REEMPLAZAR, ""));

			String var_71 = consultarVariable71(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_71.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar71(var_71.replaceAll(REEMPLAZAR, ""));

			String var_72 = consultarVariable72(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_72.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar72(var_72.replaceAll(REEMPLAZAR, ""));

			String var_73 = consultarVariable73(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_73.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar73(var_73.replaceAll(REEMPLAZAR, ""));
			// fin pendientes optimizar

			// 74, 75, 76, 77
			String var_74 = consultarVariable74(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean);
			if (!lineaBean.isAplica() && var_74.contains(INCLUIR)) {
				lineaBean.setAplica(true); 
			}
			lineaBean.setVar74(var_74.replaceAll(REEMPLAZAR, ""));
			
			lineaBean.setVar77(getVar77(lineaBean, fecha_dx_25,
					resolucion4505Service, lineaBean.getId_paciente(), administradora));

			// optimizar
			if (consultarVariable78_79(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean)) {
				lineaBean.setAplica(true); 
			}

			if (consultarVariable80_81(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean)) {
				lineaBean.setAplica(true); 
			}

			// lineaBean.setVar80("1845-01-01");
			// lineaBean.setVar81("0");
			// lineaBean.setVar82("1845-01-01");
			// lineaBean.setVar83("0");

			if (consultarVariable75_76_82_83(administradora, lineaBean.getId_paciente(),
					fecha_incio, fecha_final, lineaBean)) {
				lineaBean.setAplica(true); 
			}

			if (consultarVariable84_85(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean.getDia(), lineaBean)) { 
				lineaBean.setAplica(true); 
			}

			// if(consultarVariable86(codigo_administradora, id_paciente,
			// fecha_incio, fecha_final, anio, lineaBean)){
			// aplica = true;
			// }

			// if(consultarVariable87(codigo_administradora, id_paciente,
			// fecha_incio, fecha_final, lineaBean, anio)){
			// aplica = true;
			// }
			//
			if (consultarVariable88(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean.getAnio(), lineaBean)) {
				lineaBean.setAplica(true); 
			}

			// fin optimizar

			// 89, 90
			if (consultarVariable86_a_90(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean.getAnio(), lineaBean, empresa)) {
				lineaBean.setAplica(true); 
			}

			// String respuesta_multiple = getVarMujer(lineaBean, anio);
			// lineaBean.setVar90(respuesta_multiple);

			// optimizar
			if (consultarVariable91_92(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean.getAnio(), lineaBean, empresa)) {
				lineaBean.setAplica(true); 
			}

			// if(consultarVariable93_95(codigo_administradora, id_paciente,
			// fecha_incio, fecha_final, anio, lineaBean)){
			// aplica = true;
			// }
			if (lineaBean.getVar10().equals("F") && lineaBean.getAnio() >= 10) {
				lineaBean.setVar93("1800-01-01");
				lineaBean.setVar94("999");
				lineaBean.setVar95("999");
			} else {
				lineaBean.setVar93("1845-01-01");
				lineaBean.setVar94("0");
				lineaBean.setVar95("0");
			}

			lineaBean.setVar96("1800-01-01");
			lineaBean.setVar97("999");
			lineaBean.setVar98("999");
			lineaBean.setVar99("1800-01-01");
			lineaBean.setVar100("1800-01-01");
			lineaBean.setVar101("999");
			lineaBean.setVar102("999");
			// lineaBean.setVar103("1845-01-01");

			// if(consultarVariable96_98(codigo_administradora, id_paciente,
			// fecha_incio, fecha_final, anio, lineaBean)){
			// aplica = true;
			// }

			// if(consultarVariable99(codigo_administradora, id_paciente,
			// fecha_incio, fecha_final, lineaBean)){
			// aplica = true;
			// }

			// if(consultarVariable100_102(codigo_administradora, id_paciente,
			// fecha_incio, fecha_final, lineaBean)){
			// aplica = true;
			// }

			if (consultarVariable103(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean, lineaBean.getAnio())) {
				lineaBean.setAplica(true); 
			}

			if (consultarVariable104(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean, lineaBean.getAnio())) {
				lineaBean.setAplica(true); 
			}

			// lineaBean.setVar104("0");

			if (consultarVariable105(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean, lineaBean.getAnio())) {
				lineaBean.setAplica(true); 
			}

			if (consultarVariable106_107(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean)) {
				lineaBean.setAplica(true); 
			}

			lineaBean.setVar108("1835-01-01");
			lineaBean.setVar109("0");
			// if(consultarVariable108_109(codigo_administradora, id_paciente,
			// fecha_incio, fecha_final, lineaBean)){
			// aplica = true;
			// }

			// if(consultarVariable110(codigo_administradora, id_paciente,
			// fecha_incio, fecha_final, lineaBean)){
			// aplica = true;
			// }
			lineaBean.setVar110("1845-01-01");

			if (consultarVariable111(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean)) {
				lineaBean.setAplica(true); 
			}

			if (consultarVariable112(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean)) {
				lineaBean.setAplica(true); 
			}

			// lineaBean.setVar111("1845-01-01");
			// lineaBean.setVar112("1845-01-01");

			if (consultarVariable113(administradora, lineaBean.getId_paciente(), fecha_incio,
					fecha_final, lineaBean)) {
				lineaBean.setAplica(true); 
			}

			// fin optimizar

			// 114 a 118
			lineaBean.setVar114("0");
			lineaBean.setVar115(getVar115(lineaBean));
			lineaBean.setVar116(getVar116(lineaBean));
			lineaBean.setVar117(getVar117(lineaBean));
			lineaBean.setVar118("1845-01-01");
			
			if(lineaBean.isAplica()){
				generadorArchivo4505Impl.onAgregarLinea(lineaBean); 
			}
			
			return aplica;
	}


	private boolean consultarVariable86_a_90(Administradora administradora,
			String id_paciente, Date fecha_incio, Date fecha_final, int anio,
			LineaBean lineaBean, Empresa empresa) {
		String SIN_DATO = "999";
		String NO_APLICA = "0";
		String SIN_DATOS_86 = "22";

		String NO_APLICA_87 = "1845-01-01";
		String SIN_DATO_87 = "1800-01-01";

		String variable_10 = lineaBean.getVar10();

		if (variable_10.equals("F") && anio >= 10) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_incio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_CITOLOGIA);

			List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
					.getInstance()
					.getServicio(ReportService.class)
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				lineaBean.setVar86(SIN_DATOS_86);
				lineaBean.setVar87(SIN_DATO_87);
				lineaBean.setVar89(SIN_DATO);
				lineaBean.setVar90(SIN_DATO);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				String codigo_respuesta = (String) map.get("codigo_respuesta");
				Date fecha_resultado = (Date) map.get("fecha_resultado");
				if (codigo_respuesta != null
						&& !codigo_respuesta.trim().isEmpty()) {
					lineaBean.setVar86("1");
					lineaBean.setVar87(formato_fecha.format(fecha_resultado));
					lineaBean.setVar89(codigo_respuesta.equals("18") ? "4"
							: "1");
					lineaBean.setVar90(empresa.getCodigo_habilitacion());
					return true;
				} else {
					lineaBean.setVar86(SIN_DATOS_86);
					lineaBean.setVar87(SIN_DATO_87);
					lineaBean.setVar89(SIN_DATO);
					lineaBean.setVar90(SIN_DATO);
				}
			}
		} else {
			lineaBean.setVar86(NO_APLICA);
			lineaBean.setVar87(NO_APLICA_87);
			lineaBean.setVar89(NO_APLICA);
			lineaBean.setVar90(NO_APLICA);
		}
		return false;
	}

	private String consultarVariable74(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean lineaBean) {
		String tipo_id = lineaBean.getVar3();
		String nro_id = lineaBean.getVar4();

		String NO_APLICA = "0";
		String SIN_DATO = "999";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("tipo_identificacion", tipo_id);
		;
		params.put("nro_identificacion", nro_id);
		params.put("id_paciente", id_paciente);
		params.put("codigo_dx", IResolucion4505Constantes.CIE_VIH);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_DXDC);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			return NO_APLICA + EXCLUIR;
		} else {
			// verificamos que se tome el medicamento
			params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_medicamento",
					IResolucion4505Constantes.CODIGO_MEDICAMENTO_VIH);

			resultado_consulta_4505 = ServiceLocatorWeb
					.getInstance()
					.getServicio(ReportService.class)
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_CONTADOR_MEDICAMENTOS);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				return SIN_DATO + EXCLUIR;
			} else {
				Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
						.get(0);
				Long cantidad = (Long) map.get("cantidad");
				if (cantidad != null && cantidad.longValue() > 0) {
					return cantidad.longValue() + INCLUIR;
				} else {
					return SIN_DATO + EXCLUIR;
				}
			}
		}
	}

	private String getVar117(LineaBean LineaBean) {
		String SIN_DATO = "22";
		String NO_APLICA = "0";
		String PAUCIBACILAR_20 = "1";
		String MULTIBACILAR_20 = "2";
		String variable_20 = LineaBean.getVar20();

		if (variable_20.equals(PAUCIBACILAR_20)
				|| variable_20.equals(MULTIBACILAR_20)) {
			return SIN_DATO;
		} else {
			return NO_APLICA;
		}
	}

	private String getVar116(LineaBean LineaBean) {
		String SIN_DATO = "22";
		String NO_APLICA = "0";
		String variable_15 = LineaBean.getVar15();
		if (variable_15.equals("1")) {
			return SIN_DATO;
		} else {
			return NO_APLICA;
		}
	}

	private String getVar115(LineaBean LineaBean) {
		String SIN_DATO = "22";
		String NO_APLICA = "0";

		String variable_10 = LineaBean.getVar10();
		String variable_14 = LineaBean.getVar14();
		String variable_15 = LineaBean.getVar15();

		if (variable_10.equals("F") && variable_14.equals("1")
				&& variable_15.equals("1")) {
			return SIN_DATO;
		} else {
			return NO_APLICA;
		}
	}

	// private String getVarMujer(
	// LineaBean generadorArchivo4505State, int anios) {
	// String NO_APLICA = "0";
	// String SIN_DATO = "999";
	//
	// String var10 = (String) generadorArchivo4505State.getVar10();
	//
	// if (var10.equals("F") && anios >= 10) {
	// return SIN_DATO;
	// } else {
	// return NO_APLICA;
	// }
	// }

	private String getVar77(LineaBean generadorArchivo4505State,
			Date fecha_dx_25, Resolucion4505Service resolucion4505Service,
			String id_paciente, Administradora administradora) throws Exception {
		String NO_APLICA = "0";
		String EN_PROCESO_ATENCION = "1";
		// String ATENCION_EQUIPO_INTERDISCIPLINARIO_COMPLETO = "2";
		String SIN_DATO = "22";
		// String RIESGO_NO_EVALUADO_25 = "21";

		String var25 = generadorArchivo4505State.getVar25();
		if (var25.equals(DIAGNOSTICO_ANSIEDAD_25)
				|| var25.equals(DIAGNOSTICO_DEFICIT_HIPERACTIVIDAD_25)
				|| var25.equals(DIAGNOSTICO_DEPRESION_25)
				|| var25.equals(DIAGNOSTICO_ESQUIZOFRENIA_25)
				|| var25.equals(DIAGNOSTICO_SUSTANCIAS_PSICOACTIVAS_25)
				|| var25.equals(DIAGNOSTICO_TRASTORNO_BIPOLAR_25)) {
			if (fecha_dx_25 != null) {
				// fecha corte
				Calendar calendar_fecha_final = Calendar.getInstance();
				calendar_fecha_final.setTime(fecha_dx_25);
				calendar_fecha_final.set(Calendar.DAY_OF_MONTH,
						calendar_fecha_final.get(Calendar.DAY_OF_MONTH) - 1);

				// fecha 6 meses atras
				Calendar calendar_fecha_inicio = Calendar.getInstance();
				calendar_fecha_inicio.setTime(calendar_fecha_final.getTime());
				calendar_fecha_inicio.set(Calendar.DAY_OF_MONTH, 1);
				calendar_fecha_inicio.set(Calendar.MONTH,
						calendar_fecha_inicio.get(Calendar.MONTH) - 6);

				StringBuilder sql77 = new StringBuilder();
				sql77.append("SELECT EXISTS  (SELECT 1 FROM resolucion4505.vr_diagnostico AS vrd ");
				sql77.append("INNER JOIN public.admision AS ads ON (ads.via_ingreso IN ('15', '16') ");
				sql77.append("AND ads.nro_identificacion = vrd.nro_identificacion AND ads.nro_ingreso = vrd.nro_ingreso ");
				sql77.append("AND ads.codigo_empresa = vrd.codigo_empresa AND ads.codigo_sucursal = vrd.codigo_sucursal) ");
				sql77.append("WHERE '(F10)(F11)(F12)(F13)(F14)(F15)(F16)(F17)(F18)(F19)(F20)(F31)(F33)(F40)(F41)(F90)' LIKE '%(' || substr(vrd.codigo, 1, 3) || ')%' ");
				sql77.append("AND  vrd.codigo_empresa = '"
						+ administradora.getCodigo_empresa() + "' ");
				sql77.append("AND  vrd.codigo_sucursal =  '"
						+ administradora.getCodigo_sucursal() + "' ");
				sql77.append("AND  CAST(vrd.fecha AS DATE) BETWEEN '"
						+ formato_fecha.format(calendar_fecha_inicio.getTime())
						+ "' AND '"
						+ formato_fecha.format(calendar_fecha_final.getTime())
						+ "' ");
				sql77.append("AND  vrd.nro_identificacion = '" + id_paciente
						+ "' ");
				sql77.append("LIMIT 1) AS respuesta;");

				if (resolucion4505Service.consultarBoolean(sql77.toString())) {
					return EN_PROCESO_ATENCION;
				} else {
					return SIN_DATO;
				}
			} else {
				return SIN_DATO;
			}
		} else {
			return NO_APLICA;
		}
	}

	/***
	 * VARIABLES PENDIENTES POR OPTIMIZAR
	 * */
	private String consultarVariable62(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final, int anio) {
		boolean valido = anio == 4
				|| anio == 11
				|| anio == 16
				|| (anio >= 45 && ((anio / 5d) + "").endsWith(".0") && anio <= 80);
		return consultarFechaConsultaProcedimientoParametrizado(administradora,
				id_paciente, fecha_inicio, fecha_final, "variable_62",
				IResolucion4505Constantes.CUPS_AGUDEZA_VISUAL,
				IResolucion4505Constantes.VARIABLE_CONSULTAR_PROCEDIMIENTO,
				"fecha_procedimiento", valido);
	}

	private String consultarVariable63(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final, int anio) {
		boolean valido = (anio >= 55 && ((anio / 5d) + "").endsWith(".0"));
		return consultarFechaConsultaProcedimientoParametrizado(administradora,
				id_paciente, fecha_inicio, fecha_final, "variable_63",
				IResolucion4505Constantes.CONSULTA_OFTALMOLOGIA,
				IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS,
				"fecha_consulta", valido);
	}

	private String consultarFechaConsultaProcedimientoParametrizado(
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String nombre_variable,
			String[] coigo_cups, String sql_consulta,
			String nombre_fecha_resultado, boolean validado) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		String NO_APLICA = "1845-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups", coigo_cups);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						sql_consulta);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			if (validado) {
				return NO_SE_TIENE_REGISTRO + EXCLUIR;
			} else {
				return NO_APLICA + EXCLUIR;
			}
		} else {
			Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
					.get(0);
			Timestamp fecha_procedimiento = (Timestamp) map
					.get(nombre_fecha_resultado);
			if (fecha_procedimiento != null) {
				return formato_fecha.format(fecha_procedimiento) + INCLUIR;
			} else {
				return NO_SE_TIENE_REGISTRO + EXCLUIR;
			}
		}
	}

	// private String consultarVariable64(
	// Administradora administradora,
	// String id_paciente, Date fecha_inicio, Date fecha_final,
	//
	// LineaBean LineaBean) {
	// String NO_TIENE_DATO = "1800-01-01";
	// String NO_APLICA = "1845-01-01";
	//
	// String SI_DESNUTRICION_VARIABLE_21 = "2";
	//
	// String resultado_variable_21 = LineaBean.getVar21();
	//
	// if (resultado_variable_21.equals(SI_DESNUTRICION_VARIABLE_21)) {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("codigo_empresa", CODIGO_EMPRESA);
	// params.put("codigo_sucursal", CODIGO_SUCURSAL);
	// params.put("codigo_administradora", codigo_administradora);
	// params.put("nro_identificacion", id_paciente);
	// params.put("fecha_inicio", fecha_inicio);
	// params.put("fecha_final", fecha_final);
	// params.put(
	// "diagnosticos",
	// IResolucion4505Constantes.CIE_DIAGNOSTICO_DESNUTRICION_PROTEICO_CALORICA);
	//
	// List<Map<String, Object>> resultado_consulta_4505 =
	// ServiceLocator.getInstance()
	// .getServicio(ReportService.class).getReport(params,
	// IResolucion4505Constantes.NAMESPACE_XML,
	// IResolucion4505Constantes.VARIABLE_DX);
	// if (resultado_consulta_4505.isEmpty()
	// || resultado_consulta_4505.get(0) == null) {
	// return NO_TIENE_DATO + EXCLUIR;
	// } else {
	// Map<String, Object> map = resultado_consulta_4505.get(0);
	// Boolean aplica = (Boolean) map
	// .get(NOMBRE_RESPUESTA_CONSULTA_DX);
	// Timestamp fecha_dx = (Timestamp) map
	// .get(FECHA_RESPUESTA_CONSULTA_DX);
	// return aplica ? formato_fecha.format(fecha_dx) + INCLUIR : NO_TIENE_DATO
	// + EXCLUIR;
	// }
	// } else {
	// return NO_APLICA + EXCLUIR;
	// }
	// }

	private String consultarVariable65(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		String NO_APLICA = "1845-01-01";

		// respuesta de la variable 22
		String MUJER_VICTIMA_MALTRATO_V_22 = "1";
		String MENOR_VICTIMA_MALTRATO_V_22 = "2";

		String variable_22 = LineaBean.getVar22();

		if (variable_22.equals(MUJER_VICTIMA_MALTRATO_V_22)
				|| variable_22.equals(MENOR_VICTIMA_MALTRATO_V_22)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put(
					"diagnosticos",
					IResolucion4505Constantes.CIE_DIAGNOSTICO_MUJER_O_MENOR_VECTIMA_MALTRATO);
			List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
					.getInstance()
					.getServicio(ReportService.class)
					.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				return NO_SE_TIENE_REGISTRO + EXCLUIR;
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Boolean aplica = (Boolean) map
						.get(NOMBRE_RESPUESTA_CONSULTA_DX);
				Timestamp fecha_dx = (Timestamp) map
						.get(FECHA_RESPUESTA_CONSULTA_DX);
				return aplica ? formato_fecha.format(fecha_dx) + INCLUIR
						: NO_SE_TIENE_REGISTRO + EXCLUIR;
			}
		} else {
			return NO_APLICA + EXCLUIR;
		}
	}

	private String consultarVariable66(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,

			LineaBean LineaBean) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		String NO_APLICA = "1845-01-01";

		// respuesta de la variable 23
		String SI_23 = "1";

		String variable_23 = LineaBean.getVar23();

		if (variable_23.equals(SI_23)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put(
					"diagnosticos",
					IResolucion4505Constantes.CIE_DIAGNOSTICO_MUJER_O_MENOR_VECTIMA_MALTRATO);
			List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
					.getInstance()
					.getServicio(ReportService.class)
					.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				return NO_SE_TIENE_REGISTRO + EXCLUIR;
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Boolean aplica = (Boolean) map
						.get(NOMBRE_RESPUESTA_CONSULTA_DX);
				Timestamp fecha_dx = (Timestamp) map
						.get(FECHA_RESPUESTA_CONSULTA_DX);
				return aplica ? formato_fecha.format(fecha_dx) + INCLUIR
						: NO_SE_TIENE_REGISTRO + EXCLUIR;
			}
		} else {
			return NO_APLICA + EXCLUIR;
		}
	}

	private String consultarVariable67(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean lineaBean) {
		boolean valido = lineaBean.getVar21().equals("1")
				|| lineaBean.getVar21().equals("2");
		return consultarVariableConsulta(administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_67",
				IResolucion4505Constantes.CONSULTA_NUTRICION, valido);
	}

	/**
	 * Consulta de Psicología<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Aplica para la poblacion general Usar la opcion 1845-01-01 en poblacion
	 * que no requiere esta intervencion.
	 * */
	private String consultarVariable68(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean lineaBean) {
		boolean valido = (!lineaBean.getVar25().equals("21") && !lineaBean
				.getVar25().equals("999"));
		return consultarVariableConsulta(administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_68",
				IResolucion4505Constantes.CONSULTA_PSICOLOGIA, valido);
	}

	/**
	 * Metodo centralizado para variables que tienen el mismo comportamiento
	 * 
	 * @param valido
	 * */
	public String consultarVariableConsulta(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			String nombre_variable, String[] codigo_cups, boolean valido) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		String NO_APLICA = "1845-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups", codigo_cups);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			if (valido) {
				return NO_SE_TIENE_REGISTRO + EXCLUIR;
			} else {
				return NO_APLICA + EXCLUIR;
			}
		} else {
			Map<String, Object> mapa_respuesta = resultado_consulta_4505.get(0);
			Timestamp fecha_consulta = (Timestamp) mapa_respuesta
					.get("fecha_consulta");
			return fecha_consulta != null ? formato_fecha
					.format(fecha_consulta) + INCLUIR : NO_SE_TIENE_REGISTRO
					+ EXCLUIR;
		}
	}

	private String consultarVariable69(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		String NO_APLICA = "1845-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.CONSULTA_CREACIMIENTO_DESARROLLO);
		params.put("ftro_dx",
				IResolucion4505Constantes.CIE_CREACIMIENTO_DESARROLLO);
		params.put("ftro_finalidad",
				IResolucion4505Constantes.FINALIDAD_CONSULTA_CRECIMIENTO);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);
		String variable_9_fech_nacimiento = LineaBean.getVar9();
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento),
					fecha_final);
			int anios = (Integer) map_edad.get("anios");
			if (anios < 10) {
				return NO_SE_TIENE_REGISTRO + EXCLUIR;
			} else {
				return NO_APLICA + EXCLUIR;
			}
		} else {
			Map<String, Object> mapa_respuesta = resultado_consulta_4505.get(0);
			Timestamp fecha_consulta = (Timestamp) mapa_respuesta
					.get("fecha_consulta");

			// Timestamp fecha = fecha_consulta != null ? fecha_consulta
			// : new Timestamp(System.currentTimeMillis());

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento),
					fecha_final);
			int anios = (Integer) map_edad.get("anios");

			if (anios < 10) {
				if (fecha_consulta != null) {
					return fecha_consulta != null ? formato_fecha
							.format(fecha_consulta) + INCLUIR
							: NO_SE_TIENE_REGISTRO + EXCLUIR;
				} else {
					return NO_SE_TIENE_REGISTRO + EXCLUIR;
				}
			} else {
				return NO_APLICA + EXCLUIR;
			}
		}
	}

	private String consultarVariable70(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {
		return consultarSuministratoMedicamentoMenor10(administradora,
				id_paciente, fecha_inicio, fecha_final, "variable_70",
				IResolucion4505Constantes.CODIGO_MEDICAMENTO_SULFATO_FERROSO,
				LineaBean);
	}

	private String consultarVariable71(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {
		return consultarSuministratoMedicamentoMenor10(administradora,
				id_paciente, fecha_inicio, fecha_final, "variable_71",
				IResolucion4505Constantes.CODIGO_MEDICAMENTO_VITAMINA_A,
				LineaBean);
	}

	public String consultarSuministratoMedicamentoMenor10(
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String nombre_variable,
			String[] medicamentos,

			LineaBean LineaBean) {
		String NO_APLICA = "0";
		String SI_SE_SUMINISTRA = "1";
		String REGISTRO_NO_EVALUADO = "21";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_medicamento", medicamentos);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTAR_MEDICAMENTOS);

		String variable_9_fech_nacimiento = LineaBean.getVar9();

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento),
					fecha_final);
			int anios = (Integer) map_edad.get("anios");
			if (anios < 10) {
				return REGISTRO_NO_EVALUADO + EXCLUIR;
			} else {
				return NO_APLICA + EXCLUIR;
			}
		} else {
			Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
					.get(0);
			Timestamp fecha_medicamento = (Timestamp) map
					.get("fecha_medicamento");

			// Timestamp fecha = fecha_medicamento != null ? fecha_medicamento
			// : new Timestamp(System.currentTimeMillis());

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento),
					fecha_final);
			int anios = (Integer) map_edad.get("anios");
			if (anios < 10) {
				if (fecha_medicamento != null) {
					return SI_SE_SUMINISTRA + INCLUIR;
				} else {
					return REGISTRO_NO_EVALUADO + EXCLUIR;
				}
			} else {
				return NO_APLICA + EXCLUIR;
			}
		}
	}

	private String consultarVariable72(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		String NO_APLICA = "1845-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.CONSULTA_JOVEN_PRIMERA_VEZ);
		params.put("ftro_dx", IResolucion4505Constantes.CIE_JOVEN_PRIMERA_VEZ);
		params.put("ftro_finalidad",
				IResolucion4505Constantes.FINALIDAD_JOVEN_PRIMERA_VEZ);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);
		String variable_9_fech_nacimiento = LineaBean.getVar9();
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento),
					fecha_final);
			int anios = (Integer) map_edad.get("anios");
			if (anios >= 10 && anios <= 29) {
				return NO_SE_TIENE_REGISTRO + EXCLUIR;
			} else {
				return NO_APLICA + EXCLUIR;
			}
		} else {
			Map<String, Object> mapa_respuesta = resultado_consulta_4505.get(0);
			Timestamp fecha_consulta = (Timestamp) mapa_respuesta
					.get("fecha_consulta");

			// Timestamp fecha = fecha_consulta != null ? fecha_consulta
			// : new Timestamp(System.currentTimeMillis());

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento),
					fecha_final);
			int anios = (Integer) map_edad.get("anios");

			if (anios >= 10 && anios <= 29) {
				if (fecha_consulta != null) {
					return fecha_consulta != null ? formato_fecha
							.format(fecha_consulta) + INCLUIR
							: NO_SE_TIENE_REGISTRO + EXCLUIR;
				} else {
					return NO_SE_TIENE_REGISTRO + EXCLUIR;
				}
			} else {
				return NO_APLICA + EXCLUIR;
			}
		}
	}

	private String consultarVariable73(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		String NO_APLICA = "1845-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.CONSULTA_ADULTO_PRIEMERA_VEZ);
		params.put("ftro_dx", IResolucion4505Constantes.CIE_ADULTO_PRIEMERA_VEZ);
		params.put("ftro_finalidad",
				IResolucion4505Constantes.FINALIDAD_ADULTO_PRIEMERA_VEZ);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);
		String variable_9_fech_nacimiento = LineaBean.getVar9();
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento),
					fecha_final);
			int anios = (Integer) map_edad.get("anios");
			if (anios >= 45 && ((anios / 5d) + "").endsWith(".0")) {
				return NO_SE_TIENE_REGISTRO + EXCLUIR;
			} else {
				return NO_APLICA + EXCLUIR;
			}
		} else {
			Map<String, Object> mapa_respuesta = resultado_consulta_4505.get(0);
			Timestamp fecha_consulta = (Timestamp) mapa_respuesta
					.get("fecha_consulta");

			// Timestamp fecha = fecha_consulta != null ? fecha_consulta
			// : new Timestamp(System.currentTimeMillis());

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento),
					fecha_final);
			int anios = (Integer) map_edad.get("anios");

			if (anios >= 45 && ((anios / 5d) + "").endsWith(".0")) {
				if (fecha_consulta != null) {
					return fecha_consulta != null ? formato_fecha
							.format(fecha_consulta) + INCLUIR
							: NO_SE_TIENE_REGISTRO + EXCLUIR;
				} else {
					return NO_SE_TIENE_REGISTRO + EXCLUIR;
				}
			} else {
				return NO_APLICA + EXCLUIR;
			}
		}
	}

	private boolean consultarVariable78_79(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {
		String NO_SE_TIENE_REGISTRO_78 = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		String NO_APLICA_78 = "1845-01-01";

		String NO_APLICA_79 = "0";
		// String NEGATIVO_79 = "1";
		// String POSITIVO_79 = "2";
		String SIN_DATO_79 = "22";

		String variable_10 = LineaBean.getVar10();
		String variable_14 = LineaBean.getVar14();

		if (variable_10.equals("F") && variable_14.equals("1")) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_HEPATITIS_B_GESTANTES);

			List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
					.getInstance()
					.getServicio(ReportService.class)
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				LineaBean.setVar78(NO_SE_TIENE_REGISTRO_78);
				LineaBean.setVar79(SIN_DATO_79);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				// String valor_resultado = (String) map.get("valor_resultado");
				String codigo_respuesta = (String) map.get("codigo_respuesta");

				boolean aplica_resultado = false;
				if (fecha_resultado != null) {
					LineaBean.setVar78(formato_fecha.format(fecha_resultado));
					aplica_resultado = true;
				} else {
					LineaBean.setVar78(NO_SE_TIENE_REGISTRO_78);
				}

				if (codigo_respuesta != null
						&& !codigo_respuesta.trim().isEmpty()) {
					// LineaBean.setVar79(codigo_respuesta
					// .toLowerCase().startsWith("pos") ? POSITIVO_79
					// : NEGATIVO_79);
					LineaBean.setVar79(codigo_respuesta);
					aplica_resultado = true;
				} else {
					LineaBean.setVar79(SIN_DATO_79);
				}
				return aplica_resultado;
			}
		} else {
			LineaBean.setVar78(NO_APLICA_78);
			LineaBean.setVar79(NO_APLICA_79);
		}
		return false;
	}

	private boolean consultarVariable80_81(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {

		// String NO_SE_TIENE_REGISTRO_80 = "1800-01-01";
		String NO_APLICA_FECHA = "1845-01-01";
		// String NO_REACTIVA_81 = "1";
		// String REACTIVA_81 = "2";
		// String SIN_DATO_81 = "22";
		String NO_APLICA = "0";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_SEROLOGIA_PARA_SIFILIS);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			LineaBean.setVar80(NO_APLICA_FECHA);
			LineaBean.setVar81(NO_APLICA);
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			String codigo_respuesta = (String) map.get("codigo_respuesta");

			boolean aplica_resultado = false;
			if (fecha_resultado != null) {
				LineaBean.setVar80(formato_fecha.format(fecha_resultado));
				aplica_resultado = true;
			} else {
				LineaBean.setVar80(NO_APLICA_FECHA);
			}

			if (codigo_respuesta != null && !codigo_respuesta.trim().isEmpty()) {
				// LineaBean.setVar81(codigo_respuesta.toLowerCase()
				// .startsWith("rea") ? REACTIVA_81 : NO_REACTIVA_81);
				LineaBean.setVar81(codigo_respuesta);
				aplica_resultado = true;
			} else {
				LineaBean.setVar81(NO_APLICA);
			}
			return aplica_resultado;
		}
		return false;
	}

	private boolean consultarVariable75_76_82_83(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean lineaBean) {
		// String NO_SE_TIENE_REGISTRO_82 = "1800-01-01";
		// String NEGATIVO_83 = "1";
		// String POSITIVO_83 = "2";
		// String SIN_DATO_83 = "22";

		String NO_APLICA_FECHA = "1845-01-01";
		String NO_APLICA = "0";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_ELISA_VIH);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			lineaBean.setVar82(NO_APLICA_FECHA);
			lineaBean.setVar83(NO_APLICA);
			lineaBean.setVar75("1800-01-01");
			lineaBean.setVar76("1800-01-01");
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			String codigo_respuesta = (String) map.get("codigo_respuesta");

			boolean aplica_resultado = false;
			if (fecha_resultado != null) {
				lineaBean.setVar82(formato_fecha.format(fecha_resultado));
				aplica_resultado = true;

				// consultamos post-elisa
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fecha_resultado);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
				params.put("fecha_inicio", calendar.getTime());

				calendar = Calendar.getInstance();
				calendar.setTime(fecha_resultado);
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) - 1);
				params.put("fecha_final", calendar.getTime());

				Date fecha_pos = getFechaPorDx("Z114", params,
						new String[] { IVias_ingreso.URGENCIA,
								IVias_ingreso.HOSPITALIZACIONES }, true);
				if (fecha_pos == null) {
					calendar = Calendar.getInstance();
					calendar.setTime(fecha_resultado);
					calendar.set(Calendar.DAY_OF_MONTH,
							calendar.get(Calendar.DAY_OF_MONTH) - 1);
					lineaBean
							.setVar75(formato_fecha.format(calendar.getTime()));
				} else {
					lineaBean.setVar75(formato_fecha.format(fecha_pos));
				}

				// consultamos pre-elisa
				calendar = Calendar.getInstance();
				calendar.setTime(fecha_resultado);
				calendar.set(Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH) + 1);
				params.put("fecha_inicio", calendar.getTime());
				params.put("fecha_final", fecha_final);
				Date fecha_pre = getFechaPorDx("Z117", params, new String[] {
						IVias_ingreso.PSICOLOGIA, IVias_ingreso.PSIQUIATRIA },
						false);
				if (fecha_pre == null) {
					lineaBean
							.setVar76(formato_fecha.format(calendar.getTime()));
				} else {
					if (fecha_pre.compareTo(fecha_final) <= 0) {
						lineaBean.setVar76(formato_fecha.format(fecha_pre));
					} else {
						lineaBean.setVar76(formato_fecha.format(calendar
								.getTime()));
					}
				}
			} else {
				lineaBean.setVar82(NO_APLICA_FECHA);
				lineaBean.setVar75("1800-01-01");
				lineaBean.setVar76("1800-01-01");
			}

			if (codigo_respuesta != null && !codigo_respuesta.trim().isEmpty()) {
				// LineaBean.setVar83(codigo_respuesta.toLowerCase()
				// .startsWith("neg") ? NEGATIVO_83 : POSITIVO_83);
				lineaBean.setVar83(codigo_respuesta);
				aplica_resultado = true;
			} else {
				lineaBean.setVar83(NO_APLICA);
			}
			return aplica_resultado;
		}
		return false;
	}

	private Date getFechaPorDx(String codigo_dx, Map<String, Object> params,
			String[] vias_ingreso, boolean incluir_not) {
		params.put("listado_dx", vias_ingreso);
		params.put("codigo_dx", codigo_dx);
		params.put("incluir", incluir_not ? "incluir" : null);
		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_DX_CONSULTA);
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			return null;
		} else {
			Map<String, Object> resultado = resultado_consulta_4505.get(0);
			return (Date) resultado.get("fecha_dx");
		}
	}

	private boolean consultarVariable84_85(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final, int dias,

			LineaBean LineaBean) {
		String NO_SE_TIENE_REGISTRO_84 = "1800-01-01";
		String NO_APLICA_84 = "1845-01-01";

		String NO_APLICA_85 = "0";
		// String NORMAL_85 = "1";
		// String ANORMAL_85 = "2";
		String SIN_DATO_85 = "22";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_TSH_NEONATAL);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			if (dias < 2) {
				LineaBean.setVar84(NO_SE_TIENE_REGISTRO_84);
				LineaBean.setVar85(SIN_DATO_85);
			} else {
				LineaBean.setVar84(NO_APLICA_84);
				LineaBean.setVar85(NO_APLICA_85);
			}
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			String codigo_respuesta = (String) map.get("codigo_respuesta");
			boolean aplica_resultado = false;
			if (fecha_resultado != null) {
				LineaBean.setVar84(formato_fecha.format(fecha_resultado));
				aplica_resultado = true;
			} else {
				LineaBean.setVar84(NO_SE_TIENE_REGISTRO_84);
			}
			if (codigo_respuesta != null && !codigo_respuesta.trim().isEmpty()) {
				// LineaBean.setVar85(codigo_respuesta.toLowerCase()
				// .startsWith("nor") ? NORMAL_85 : ANORMAL_85);
				LineaBean.setVar85(codigo_respuesta);
				aplica_resultado = true;
			} else {
				LineaBean.setVar85(SIN_DATO_85);
			}
			return aplica_resultado;
		}
		return false;
	}

	// private boolean consultarVariable86(Administradora administradora,String
	// id_paciente,
	// Date fecha_inicio, Date fecha_final, int anios,
	// LineaBean LineaBean) {
	//
	// String NO_APLICA = "0";
	// String CITOLOGIA_CERVICO_UTERINA = "1";
	// String ADN = "2";
	// String TECNICA_INSPECCION_VISUAL = "3";
	// String SIN_DATO = "22";
	//
	// String variable_10_sexo = LineaBean.getVar10();
	//
	// if (variable_10_sexo.equals("F") && anios >= 10) {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("codigo_empresa", CODIGO_EMPRESA);
	// params.put("codigo_sucursal", CODIGO_SUCURSAL);
	// params.put("codigo_administradora", codigo_administradora);
	// params.put("nro_identificacion", id_paciente);
	// params.put("fecha_inicio", fecha_inicio);
	// params.put("fecha_final", fecha_final);
	// params.put(
	// "codigo_cups",
	// IResolucion4505Constantes.LABORATORIO_TAMIZAJE_CANCER_CUELLO_UTERINO);
	//
	// List<Map<String, Object>> resultado_consulta_4505 =
	// ServiceLocator.getInstance()
	// .getServicio(ReportService.class)
	// .getReport(
	// params,
	// IResolucion4505Constantes.NAMESPACE_XML,
	// IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
	//
	// if (resultado_consulta_4505.isEmpty()
	// || resultado_consulta_4505.get(0) == null) {
	// if (anios > 10) {
	// LineaBean.setVar86(SIN_DATO);
	// } else {
	// LineaBean.setVar86(NO_APLICA);
	// }
	// } else {
	// Map<String, Object> map = resultado_consulta_4505.get(0);
	// String respuesta_4505 = (String) map.get("codigo_respuesta");
	// if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()) {
	// if (respuesta_4505.equals(CITOLOGIA_CERVICO_UTERINA)
	// || respuesta_4505.equals(ADN)
	// || respuesta_4505.equals(TECNICA_INSPECCION_VISUAL)) {
	// LineaBean.setVar86(respuesta_4505);
	// return true;
	// } else {
	// LineaBean.setVar86(SIN_DATO);
	// }
	// } else {
	// LineaBean.setVar86(SIN_DATO);
	// }
	// }
	// } else {
	// LineaBean.setVar86(NO_APLICA);
	// }
	// return false;
	// }

	// private boolean consultarVariable87(Administradora administradora,
	// String id_paciente, Date fecha_inicio, Date fecha_final,
	//
	// LineaBean LineaBean, int anios) {
	//
	// String NO_APLICA = "1845-01-01";
	// String SIN_DATO = "1800-01-01";
	//
	// String variable_10_sexo = LineaBean.getVar10();
	//
	// if (variable_10_sexo.equals("F")) {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("codigo_empresa", CODIGO_EMPRESA);
	// params.put("codigo_sucursal", CODIGO_SUCURSAL);
	// params.put("codigo_administradora", codigo_administradora);
	// params.put("nro_identificacion", id_paciente);
	// params.put("fecha_inicio", fecha_inicio);
	// params.put("fecha_final", fecha_final);
	// params.put(
	// "codigo_cups",
	// IResolucion4505Constantes.LABORATORIO_CITOLOGIA_CERVICO_UTERINA);
	//
	// List<Map<String, Object>> resultado_consulta_4505 =
	// ServiceLocator.getInstance()
	// .getServicio(ReportService.class)
	// .getReport(
	// params,
	// IResolucion4505Constantes.NAMESPACE_XML,
	// IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
	//
	// if (resultado_consulta_4505.isEmpty()
	// || resultado_consulta_4505.get(0) == null) {
	// if (anios > 10) {
	// LineaBean.setVar87(SIN_DATO);
	// } else {
	// LineaBean.setVar87(NO_APLICA);
	// }
	// } else {
	// Map<String, Object> map = resultado_consulta_4505.get(0);
	// Timestamp fecha_resultado = (Timestamp) map
	// .get("fecha_resultado");
	// if (fecha_resultado != null) {
	// LineaBean.setVar87(formato_fecha.format(fecha_resultado));
	// return true;
	// } else {
	// LineaBean.setVar87(SIN_DATO);
	// }
	// }
	// } else {
	// LineaBean.setVar87(NO_APLICA);
	// }
	// return false;
	// }

	private boolean consultarVariable88(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final, int anios,
			LineaBean LineaBean) {

		String NO_APLICA = "0";
		String SIN_DATO = "999";

		String variable_10_sexo = LineaBean.getVar10();

		if (variable_10_sexo.equals("F")) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put(
					"codigo_cups",
					IResolucion4505Constantes.LABORATORIO_CITOLOGIA_CERVICO_UTERINA_SEGUN_BETHESDA);

			List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
					.getInstance()
					.getServicio(ReportService.class)
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				if (anios > 10) {
					LineaBean.setVar88(SIN_DATO);
				} else {
					LineaBean.setVar88(NO_APLICA);
				}
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				String respuesta_4505 = (String) map.get("codigo_respuesta");
				if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
						&& respuesta_4505.matches("[0-9]*$")) {
					int resultado_int = Integer.parseInt(respuesta_4505);
					if (resultado_int > 1 && resultado_int < 18) {
						LineaBean.setVar88(respuesta_4505);
						return true;
					} else {
						LineaBean.setVar88(SIN_DATO);
					}
				} else {
					LineaBean.setVar88(SIN_DATO);
				}
			}
		} else {
			LineaBean.setVar88(NO_APLICA);
		}
		return false;
	}

	private boolean consultarVariable91_92(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final, int anios,
			LineaBean lineabean, Empresa empresa) {

		String NO_APLICA_91 = "1845-01-01";
		String SIN_DATO_91 = "1800-01-01";

		String NO_APLICA_92 = "0";
		String SIN_DATO_92 = "999";

		String variable_10_sexo = lineabean.getVar10();

		if (variable_10_sexo.equals("F") && anios >= 10) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_CITOLOGIA_COLPOSCOPIA);

			List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
					.getInstance()
					.getServicio(ReportService.class)
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				// if (anios > 10) {
				lineabean.setVar91(SIN_DATO_91);
				lineabean.setVar92(SIN_DATO_92);
				// } else {
				// LineaBean.setVar91(NO_APLICA_91);
				// LineaBean.setVar92(NO_APLICA_92);
				// }
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				if (fecha_resultado != null) {
					lineabean.setVar91(formato_fecha.format(fecha_resultado));
					lineabean.setVar92(empresa.getCodigo_habilitacion());
				} else {
					lineabean.setVar91(SIN_DATO_91);
					lineabean.setVar92(SIN_DATO_92);
				}
				return fecha_resultado != null;
			}
		} else {
			lineabean.setVar91(NO_APLICA_91);
			lineabean.setVar92(NO_APLICA_92);
		}
		return false;
	}

	// private boolean consultarVariable93_95(Administradora administradora,
	// String id_paciente,
	// Date fecha_inicio, Date fecha_final, int anios,
	//
	// LineaBean LineaBean) {
	//
	// String NO_APLICA_93 = "1845-01-01";
	// String SIN_DATO_93 = "1800-01-01";
	//
	// String NO_APLICA_94 = "0";
	// String SIN_DATO_94 = "999";
	//
	// String NO_APLICA_95 = "0";
	// String SIN_DATO_95 = "999";
	//
	// String variable_10_sexo = LineaBean.getVar10();
	//
	// if (variable_10_sexo.equals("F")) {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("codigo_empresa", CODIGO_EMPRESA);
	// params.put("codigo_sucursal", CODIGO_SUCURSAL);
	// params.put("codigo_administradora", codigo_administradora);
	// params.put("nro_identificacion", id_paciente);
	// params.put("fecha_inicio", fecha_inicio);
	// params.put("fecha_final", fecha_final);
	// params.put("codigo_cups",
	// IResolucion4505Constantes.LABORATORIO_BIOPSIA_CERVICAL);
	//
	// List<Map<String, Object>> resultado_consulta_4505 =
	// ServiceLocator.getInstance()
	// .getServicio(ReportService.class)
	// .getReport(
	// params,
	// IResolucion4505Constantes.NAMESPACE_XML,
	// IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
	//
	// if (resultado_consulta_4505.isEmpty()
	// || resultado_consulta_4505.get(0) == null) {
	// if (anios > 10) {
	// LineaBean.setVar93(SIN_DATO_93);
	// LineaBean.setVar94(SIN_DATO_94);
	// LineaBean.setVar95(SIN_DATO_95);
	// } else {
	// LineaBean.setVar93(SIN_DATO_93);
	// // LineaBean.setVar93(NO_APLICA_93);
	// LineaBean.setVar94(NO_APLICA_94);
	// LineaBean.setVar95(NO_APLICA_95);
	// }
	// } else {
	// Map<String, Object> map = resultado_consulta_4505.get(0);
	// Timestamp fecha_resultado = (Timestamp) map
	// .get("fecha_resultado");
	// boolean aplica_resultado = false;
	// if (fecha_resultado != null) {
	// LineaBean.setVar93(formato_fecha.format(fecha_resultado));
	// aplica_resultado = true;
	// } else {
	// LineaBean.setVar93(SIN_DATO_93);
	// }
	//
	// String respuesta_4505 = (String) map.get("codigo_respuesta");
	// if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
	// && respuesta_4505.matches("[0-9]*$")) {
	// int resultado_int = Integer.parseInt(respuesta_4505);
	// if (resultado_int > 1 && resultado_int < 6) {
	// LineaBean.setVar94(respuesta_4505);
	// aplica_resultado = true;
	// } else {
	// LineaBean.setVar94(SIN_DATO_94);
	// }
	// } else {
	// LineaBean.setVar94(SIN_DATO_94);
	// }
	// LineaBean.setVar95(SIN_DATO_95);
	// return aplica_resultado;
	// }
	// } else {
	// LineaBean.setVar93(NO_APLICA_93);
	// LineaBean.setVar94(NO_APLICA_94);
	// LineaBean.setVar95(NO_APLICA_95);
	// }
	// return false;
	// }
	//
	// private boolean consultarVariable96_98(Administradora administradora,
	// String id_paciente,
	// Date fecha_inicio, Date fecha_final, int anios,
	//
	// LineaBean LineaBean) {
	//
	// String NO_APLICA_96 = "1845-01-01";
	// String SIN_DATO_96 = "1800-01-01";
	//
	// String NO_APLICA_97 = "0";
	// String SIN_DATO_97 = "999";
	//
	// String NO_APLICA_98 = "0";
	// String SIN_DATO_98 = "999";
	//
	// String variable_10_sexo = LineaBean.getVar10();
	//
	// if (variable_10_sexo.equals("F")) {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("codigo_empresa", CODIGO_EMPRESA);
	// params.put("codigo_sucursal", CODIGO_SUCURSAL);
	// params.put("codigo_administradora", codigo_administradora);
	// params.put("nro_identificacion", id_paciente);
	// params.put("fecha_inicio", fecha_inicio);
	// params.put("fecha_final", fecha_final);
	// params.put("codigo_cups",
	// IResolucion4505Constantes.LABORATORIO_MAMOGRAFIA);
	//
	// List<Map<String, Object>> resultado_consulta_4505 =
	// ServiceLocator.getInstance()
	// .getServicio(ReportService.class)
	// .getReport(
	// params,
	// IResolucion4505Constantes.NAMESPACE_XML,
	// IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
	//
	// if (resultado_consulta_4505.isEmpty()
	// || resultado_consulta_4505.get(0) == null) {
	// if (anios > 35) {
	// LineaBean.setVar96(SIN_DATO_96);
	// LineaBean.setVar97(SIN_DATO_97);
	// LineaBean.setVar98(SIN_DATO_98);
	// } else {
	// LineaBean.setVar96(NO_APLICA_96);
	// LineaBean.setVar97(NO_APLICA_97);
	// LineaBean.setVar98(NO_APLICA_98);
	// }
	// } else {
	// Map<String, Object> map = resultado_consulta_4505.get(0);
	// Timestamp fecha_resultado = (Timestamp) map
	// .get("fecha_resultado");
	// boolean aplica_resultado = false;
	// if (fecha_resultado != null) {
	// LineaBean.setVar96(formato_fecha.format(fecha_resultado));
	// aplica_resultado = true;
	// } else {
	// LineaBean.setVar96(SIN_DATO_96);
	// }
	//
	// String respuesta_4505 = (String) map.get("codigo_respuesta");
	// if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
	// && respuesta_4505.matches("[0-9]*$")) {
	// int resultado_int = Integer.parseInt(respuesta_4505);
	// if (resultado_int > 1 && resultado_int < 6) {
	// LineaBean.setVar97(respuesta_4505);
	// aplica_resultado = true;
	// } else {
	// LineaBean.setVar97(SIN_DATO_97);
	// }
	// } else {
	// LineaBean.setVar97(SIN_DATO_97);
	// }
	// LineaBean.setVar98(SIN_DATO_98);
	// return aplica_resultado;
	// }
	// } else {
	// LineaBean.setVar96(NO_APLICA_96);
	// LineaBean.setVar97(NO_APLICA_97);
	// LineaBean.setVar98(NO_APLICA_98);
	// }
	// return false;
	// }
	//
	// private boolean consultarVariable99(Administradora administradora,
	// String id_paciente, Date fecha_inicio, Date fecha_final,
	//
	// LineaBean LineaBean) {
	//
	// String NO_APLICA_99 = "1845-01-01";
	// String SIN_DATO_99 = "1800-01-01";
	//
	// String variable_10_sexo = LineaBean.getVar10();
	//
	// if (variable_10_sexo.equals("F")) {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("codigo_empresa", CODIGO_EMPRESA);
	// params.put("codigo_sucursal", CODIGO_SUCURSAL);
	// params.put("codigo_administradora", codigo_administradora);
	// params.put("nro_identificacion", id_paciente);
	// params.put("fecha_inicio", fecha_inicio);
	// params.put("fecha_final", fecha_final);
	// params.put("codigo_cups",
	// IResolucion4505Constantes.LABORATORIO_BIOPSIA_SENO);
	//
	// List<Map<String, Object>> resultado_consulta_4505 =
	// ServiceLocator.getInstance()
	// .getServicio(ReportService.class)
	// .getReport(
	// params,
	// IResolucion4505Constantes.NAMESPACE_XML,
	// IResolucion4505Constantes.VARIABLE_CONSULTAR_PROCEDIMIENTO);
	//
	// if (resultado_consulta_4505.isEmpty()
	// || resultado_consulta_4505.get(0) == null) {
	// LineaBean.setVar99(SIN_DATO_99);
	// } else {
	// Map<String, Object> map = resultado_consulta_4505.get(0);
	// Timestamp fecha_resultado = (Timestamp) map
	// .get("fecha_resultado");
	// if (fecha_resultado != null) {
	// LineaBean.setVar99(formato_fecha.format(fecha_resultado));
	// return true;
	// } else {
	// LineaBean.setVar99(SIN_DATO_99);
	// }
	// }
	// } else {
	// LineaBean.setVar99(NO_APLICA_99);
	// }
	// return false;
	// }
	//
	//
	// private boolean consultarVariable100_102(Administradora administradora,
	// String id_paciente,
	// Date fecha_inicio, Date fecha_final,
	// LineaBean LineaBean) {
	//
	// String NO_APLICA_100 = "1845-01-01";
	// String SIN_DATO_100 = "1800-01-01";
	//
	// String NO_APLICA_101 = "0";
	// String SIN_DATO_101 = "999";
	//
	// String NO_APLICA_102 = "0";
	// String SIN_DATO_102 = "999";
	//
	// String variable_10_sexo = LineaBean.getVar10();
	//
	// if (variable_10_sexo.equals("F")) {
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("codigo_empresa", CODIGO_EMPRESA);
	// params.put("codigo_sucursal", CODIGO_SUCURSAL);
	// params.put("codigo_administradora", codigo_administradora);
	// params.put("nro_identificacion", id_paciente);
	// params.put("fecha_inicio", fecha_inicio);
	// params.put("fecha_final", fecha_final);
	// params.put("codigo_cups",
	// IResolucion4505Constantes.LABORATORIO_BIOPSIA_SENO);
	//
	// List<Map<String, Object>> resultado_consulta_4505 =
	// ServiceLocator.getInstance()
	// .getServicio(ReportService.class)
	// .getReport(
	// params,
	// IResolucion4505Constantes.NAMESPACE_XML,
	// IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
	//
	// if (resultado_consulta_4505.isEmpty()
	// || resultado_consulta_4505.get(0) == null) {
	// LineaBean.setVar100(SIN_DATO_100);
	// LineaBean.setVar101(SIN_DATO_101);
	// LineaBean.setVar102(SIN_DATO_102);
	// } else {
	// Map<String, Object> map = resultado_consulta_4505.get(0);
	// Timestamp fecha_resultado = (Timestamp) map
	// .get("fecha_resultado");
	// boolean aplica_resultado = false;
	// if (fecha_resultado != null) {
	// LineaBean.setVar100(formato_fecha.format(fecha_resultado));
	// aplica_resultado = true;
	// } else {
	// LineaBean.setVar100(SIN_DATO_100);
	// }
	//
	// String respuesta_4505 = (String) map.get("codigo_respuesta");
	// if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
	// && respuesta_4505.matches("[0-9]*$")) {
	// int resultado_int = Integer.parseInt(respuesta_4505);
	// if (resultado_int > 1 && resultado_int < 5) {
	// LineaBean.setVar101(respuesta_4505);
	// aplica_resultado = true;
	// } else {
	// LineaBean.setVar101(SIN_DATO_101);
	// }
	// } else {
	// LineaBean.setVar101(SIN_DATO_101);
	// }
	// LineaBean.setVar102(SIN_DATO_102);
	// return aplica_resultado;
	// }
	//
	// } else {
	// LineaBean.setVar100(NO_APLICA_100);
	// LineaBean.setVar101(NO_APLICA_101);
	// LineaBean.setVar102(NO_APLICA_102);
	// }
	// return false;
	// }
	//
	private boolean consultarVariable103(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean lineaBean, int anio) {
		String SIN_DATO = "1800-01-01";
		String var = consultarTomaLaboratorio(administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_103",
				IResolucion4505Constantes.LABORATORIO_HEMOGLOBINA, lineaBean);

		if (var.equals(SIN_DATO + EXCLUIR)) {
			if (lineaBean.getVar14().equals("1")
					|| (lineaBean.getVar10().equals("F") && anio >= 10 && anio <= 13)) {
				lineaBean.setVar103(var.replaceAll(REEMPLAZAR, ""));
				return var.contains(INCLUIR);
			} else {
				lineaBean.setVar103("1845-01-01");
				return false;
			}
		} else {
			lineaBean.setVar103(var.replaceAll(REEMPLAZAR, ""));
			return var.contains(INCLUIR);
		}
	}

	//
	//
	private boolean consultarVariable104(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean lineaBean, int anio) {

		String NO_APLICA_104 = "0";
		String SIN_DATO_104 = "999";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_HEMOGLOBINA);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			if (lineaBean.getVar14().equals("1")
					|| (lineaBean.getVar10().equals("F") && anio >= 10 && anio <= 13)) {
				lineaBean.setVar104(SIN_DATO_104);
			} else {
				lineaBean.setVar104(NO_APLICA_104);
			}
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			String respuesta_4505 = (String) map.get("codigo_respuesta");
			if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
					&& respuesta_4505.matches("[0-9.,]*$")) {
				double resultado_int = Double.parseDouble(respuesta_4505);
				if (resultado_int > 1.5 && resultado_int < 20) {
					lineaBean.setVar104(respuesta_4505);
					return true;
				} else {
					lineaBean.setVar104(NO_APLICA_104);
					lineaBean.setVar103("1845-01-01");
				}
			} else {
				lineaBean.setVar104(NO_APLICA_104);
				lineaBean.setVar103("1845-01-01");
			}
		}
		return false;
	}

	// private boolean consultarVariable105(Administradora administradora,
	// String id_paciente, Date fecha_inicio, Date fecha_final,
	//
	// LineaBean LineaBean) {
	// String var = consultarTomaLaboratorio(codigo_administradora, id_paciente,
	// fecha_inicio, fecha_final, "variable_105",
	// IResolucion4505Constantes.LABORATORIO_GLISEMIA_BASAL,
	// LineaBean);
	// LineaBean.setVar105(var.replaceAll(REEMPLAZAR, ""));
	// return var.contains(INCLUIR);
	// }

	private boolean consultarVariable105(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean lineabean, int anio) {
		String NO_APLICA_105 = "1845-01-01";
		String SIN_DATO_105 = "1800-01-01";

		if (lineabean.getVar14().equals("1")
				|| (anio >= 45 && ((anio / 5d) + "").endsWith(".0"))) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_GLICOSILADA);

			List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
					.getInstance()
					.getServicio(ReportService.class)
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				lineabean.setVar105(SIN_DATO_105);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				boolean aplica_resultado = false;
				if (fecha_resultado != null) {
					lineabean.setVar105(formato_fecha.format(fecha_resultado));
					aplica_resultado = true;
				} else {
					lineabean.setVar105(SIN_DATO_105);
				}
				return aplica_resultado;
			}
		} else {
			lineabean.setVar105(NO_APLICA_105);
		}
		return false;
	}

	private boolean consultarVariable106_107(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {

		String NO_APLICA_106 = "1845-01-01";
		// String SIN_DATO_106 = "1800-01-01";

		String NO_APLICA_107 = "0";
		// String SIN_DATO_107 = "999";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_CREATININA);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			LineaBean.setVar106(NO_APLICA_106);
			LineaBean.setVar107(NO_APLICA_107);
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			boolean aplica_resultado = false;
			if (fecha_resultado != null) {
				LineaBean.setVar106(formato_fecha.format(fecha_resultado));
				aplica_resultado = true;
			} else {
				LineaBean.setVar106(NO_APLICA_106);
			}

			String respuesta_4505 = (String) map.get("codigo_respuesta");
			if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
					&& respuesta_4505.matches("[0-9.,]*$")) {
				double resultado_int = Double.parseDouble(respuesta_4505);
				if (resultado_int > 0.2 && resultado_int < 25) {
					LineaBean.setVar107(respuesta_4505);
					aplica_resultado = true;
				} else {
					LineaBean.setVar107(NO_APLICA_107);
				}
			} else {
				LineaBean.setVar107(NO_APLICA_107);
			}
			return aplica_resultado;
		}
		return false;
	}

	// private boolean consultarVariable108_109(Administradora
	// administradora,String id_paciente,
	// Date fecha_inicio, Date fecha_final,
	// LineaBean LineaBean) {
	//
	// // String NO_APLICA_108 = "1845-01-01";
	// String SIN_DATO_108 = "1800-01-01";
	//
	// String NO_APLICA_109 = "0";
	// String SIN_DATO_109 = "999";
	//
	// Map<String, Object> params = new HashMap<String, Object>();
	// params.put("codigo_empresa", CODIGO_EMPRESA);
	// params.put("codigo_sucursal", CODIGO_SUCURSAL);
	// params.put("codigo_administradora", codigo_administradora);
	// params.put("nro_identificacion", id_paciente);
	// params.put("fecha_inicio", fecha_inicio);
	// params.put("fecha_final", fecha_final);
	// params.put("codigo_cups",
	// IResolucion4505Constantes.LABORATORIO_GLICOSILADA);
	//
	// List<Map<String, Object>> resultado_consulta_4505 =
	// ServiceLocator.getInstance()
	// .getServicio(ReportService.class).getReport(params,
	// IResolucion4505Constantes.NAMESPACE_XML,
	// IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
	//
	// if (resultado_consulta_4505.isEmpty()
	// || resultado_consulta_4505.get(0) == null) {
	// LineaBean.setVar108(SIN_DATO_108);
	// LineaBean.setVar109(SIN_DATO_109);
	// } else {
	// Map<String, Object> map = resultado_consulta_4505.get(0);
	// Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
	// boolean aplica_resultado = false;
	// if (fecha_resultado != null) {
	// LineaBean.setVar108(formato_fecha.format(fecha_resultado));
	// aplica_resultado = true;
	// } else {
	// LineaBean.setVar108(SIN_DATO_108);
	// }
	//
	// String respuesta_4505 = (String) map.get("codigo_respuesta");
	// if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
	// && respuesta_4505.matches("[0-9.,]*$")) {
	// double resultado_int = Double.parseDouble(respuesta_4505);
	// if (resultado_int > 5 && resultado_int < 20) {
	// LineaBean.setVar109(respuesta_4505);
	// aplica_resultado = true;
	// } else {
	// LineaBean.setVar109(NO_APLICA_109);
	// }
	// } else {
	// LineaBean.setVar109(SIN_DATO_109);
	// }
	// return aplica_resultado;
	// }
	// return false;
	// }

	// private boolean consultarVariable110(Administradora administradora,
	// String id_paciente, Date fecha_inicio, Date fecha_final,
	//
	// LineaBean LineaBean) {
	// String var = consultarTomaLaboratorio(codigo_administradora, id_paciente,
	// fecha_inicio, fecha_final, "variable_110",
	// IResolucion4505Constantes.LABORATORIO_MICROALBUMINURIA,
	// LineaBean);
	// LineaBean.setVar110(var.replaceAll(REEMPLAZAR, ""));
	// return var.contains(INCLUIR);
	// }
	//
	private boolean consultarVariable111(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean lineaBean) {
		String var = consultarTomaLaboratorio(administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_111",
				IResolucion4505Constantes.LABORATORIO_HDL, lineaBean);

		if (var.contains(INCLUIR)) {
			lineaBean.setVar111(var.replaceAll(REEMPLAZAR, ""));
			return true;
		} else {
			lineaBean.setVar111("1845-01-01");
			return false;
		}
	}

	private boolean consultarVariable112(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean lineaBean) {
		String var = consultarTomaLaboratorio(administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_112",
				IResolucion4505Constantes.LABORATORIO_BACILOSCOPIA_DIAGNOSTICO,
				lineaBean);
		if (var.contains(INCLUIR)) {
			lineaBean.setVar112(var.replaceAll(REEMPLAZAR, ""));
			return true;
		} else {
			lineaBean.setVar112("1845-01-01");
			return false;
		}
	}

	private boolean consultarVariable113(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			LineaBean LineaBean) {

		// String NEGATIVA = "1";
		// String POSITIVA = "2";
		// String EN_PROCESO = "3";
		// String NO = "4";
		String SIN_DATO = "22";

		// String SIN_DATO_112 = "1800-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_BACILOSCOPIA_DIAGNOSTICO);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			// String variable_112 = LineaBean.getVar112();
			// if (variable_112.equals(SIN_DATO_112)) {
			LineaBean.setVar113(SIN_DATO);
			// } else {
			// LineaBean.setVar113(EN_PROCESO);
			// return true;
			// }
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			String respuesta_4505 = (String) map.get("codigo_respuesta");
			if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
					&& isValido(respuesta_4505, "1", "2", "3", "4", "22")) {
				LineaBean.setVar113(respuesta_4505);
				return true;
			} else {
				LineaBean.setVar113(SIN_DATO);
			}
		}
		return false;
	}

	/**
	 * Verifica que los resultados de cada variable
	 * */
	private boolean isValido(String respuesta_4505, String... comparar) {
		for (String comp : comparar) {
			if (comp.equals(respuesta_4505)) {
				return true;
			}
		}
		return false;
	}

	private String consultarTomaLaboratorio(Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			String nombre_variable, String[] codigo_cups, LineaBean LineaBean) {

		// String NO_APLICA = "1845-01-01";
		String SIN_DATO = "1800-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups", codigo_cups);

		List<Map<String, Object>> resultado_consulta_4505 = ServiceLocatorWeb
				.getInstance()
				.getServicio(ReportService.class)
				.getReport(params, IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			return SIN_DATO + EXCLUIR;
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			if (fecha_resultado != null) {
				return formato_fecha.format(fecha_resultado) + INCLUIR;
			} else {
				return SIN_DATO + EXCLUIR;
			}
		}
	}
}
