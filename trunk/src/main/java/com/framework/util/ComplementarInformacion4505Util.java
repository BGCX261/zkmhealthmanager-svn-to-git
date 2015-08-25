package com.framework.util;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Empresa;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.framework.constantes.IResolucion4505Constantes;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.state.GeneradorArchivo4505State;

/**
 * Esta clase me permite complementar la informacion faltante de las variables
 * de la 4505
 * 
 * @author Luis Miguel
 * */
public class ComplementarInformacion4505Util {

	private static final SimpleDateFormat formato_fecha = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static Logger log = Logger
			.getLogger(ComplementarInformacion4505Util.class);

	/**
	 * Respuesta para mujeres que aplican
	 * */
	private static final String RESPUESTA_VARIABLE_10_FEMENINO = "F";
	private static final String RESPUESTA_VARIABLE_14_FEMENINO = "1";
	private static final String RESPUESTA_VARIABLE_15_FEMENINO = "1";
	private static final String RESPUESTA_VARIABLE_15_RECIEN_NACIDO = "2";

	public static final String NOMBRE_RESPUESTA_CONSULTA_DX = "aplica";
	public static final String FECHA_RESPUESTA_CONSULTA_DX = "fecha_dx";

	private static final String DIAGNOSTICO_ANSIEDAD_25 = "1";
	private static final String DIAGNOSTICO_DEPRESION_25 = "2";
	private static final String DIAGNOSTICO_ESQUIZOFRENIA_25 = "3";
	private static final String DIAGNOSTICO_DEFICIT_HIPERACTIVIDAD_25 = "4";
	private static final String DIAGNOSTICO_SUSTANCIAS_PSICOACTIVAS_25 = "5";
	private static final String DIAGNOSTICO_TRASTORNO_BIPOLAR_25 = "6";

	/**
	 * Complementa las variables faltantes
	 * 
	 * @author Luis Miguel
	 * */
	public static Map<String, Object> complemenatarInformacion4505(
			List<Map<String, Object>> resultado_consulta_4505, Administradora administradora,
			Date fecha_inicio, Date fecha_rango_final, Date fecha_final,
			Empresa empresa, String formato, int numero_archivo)
			throws Exception {

		// Listado final
//		List<Map<String, Object>> list_archivo_final = new ArrayList<Map<String, Object>>();

		// Fila de configuracion de la 4505
		String nombre = IResolucion4505Constantes.MODULO_INFORMACION
				+ IResolucion4505Constantes.TIPO_FUENTE
				+ IResolucion4505Constantes.TEMA_INFORMACION
				+ new SimpleDateFormat("yyyyMMdd").format(fecha_final)
				+ empresa.getTipo_identificacion().toUpperCase()
				+ ValidadorArchivoUtils.validarNIT(empresa
						.getNro_identificacion()) + empresa.getRegimen()
				+ (numero_archivo < 10 ? "0" + numero_archivo : numero_archivo)
				+ "." + formato;

		// cuando se valla a generar un archivo plano, el software validara el
		// nombre
		if (formato.equals(GeneradorArchivo4505State.TIPO_ARCHIVO_PLANO)
				&& nombre.length() != IResolucion4505Constantes.LONGITUD_VALIDAD_NOMBRE_ARCHIVO) {
			throw new ValidacionRunTimeException(
					"El nombre no contiene los "
							+ IResolucion4505Constantes.LONGITUD_VALIDAD_NOMBRE_ARCHIVO
							+ " caracteres que se exigen en la resolucion.! El nombre generado: "
							+ nombre);
		}

		Map<String, Object> map_configuracion = new HashMap<String, Object>();
		map_configuracion.put(IResolucion4505Constantes.COLUM_NOMBRE_ARCHIVO,
				nombre);
		map_configuracion.put(IResolucion4505Constantes.COLUM_REGISTRO_CONTROL,
				IResolucion4505Constantes.REGISTRO_CONTROL);
		map_configuracion
				.put(IResolucion4505Constantes.COLUM_CODIGO_ADMINISTRADORA,
						ValidadorArchivoUtils
								.validarCodigoAdministradora(administradora
										.getCodigo()));
		map_configuracion.put(IResolucion4505Constantes.COLUM_FECHA_INICIO,
				formato_fecha.format(fecha_inicio));
		map_configuracion.put(IResolucion4505Constantes.COLUM_FECHA_FINAL,
				formato_fecha.format(fecha_rango_final));
		map_configuracion.put(IResolucion4505Constantes.COLUM_TOTAL_REG,
				resultado_consulta_4505.size());
		
		
		map_configuracion.put(IResolucion4505Constantes.PARAM_LISTADO, resultado_consulta_4505); 
		
//		list_archivo_final.add(map_configuracion);
//
//		// complementacon de variables
//		int consecutivo = 1;
//		for (Map map_resultado : resultado_consulta_4505) {
//			String id_paciente = (String) map_resultado.get("id_paciente");
//			String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo
//			String variable_9_fech_nacimiento = (String) map_resultado
//					.get("variable_9"); // fecha nacimiento
//			String variable_3_tipo_id = (String) map_resultado
//					.get("variable_3"); // tipo identificacion
//
//			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(java.sql.Date
//					.valueOf(variable_9_fech_nacimiento));
//
//			log.info(consecutivo * 100 / resultado_consulta_4505.size() + "%");
// 			
//			// complementamos informacion
//			map_resultado.put("variable_0", 2);
//			map_resultado.put("variable_1", consecutivo++);
//			map_resultado.put("variable_2", administradora.getCodigo());
//			
//			
//			boolean incluir_listado = false;
//
//			// consultamos variable 14
//			if(consultarVariable14(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, variable_10_sexo, map_edad))
//				incluir_listado = true;
//
//			// consultar variable 15
//			if(consultarVariable15(map_resultado, variable_3_tipo_id,
//					variable_10_sexo, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, variable_10_sexo, map_edad))
//				incluir_listado = true;
//
//			// consultamos de la variable 16
//			if(consultarVariable16(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultamos de la variable 17
//			if(consultarVariable17(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			// consultamos de la variable 18
//			if(consultarVariable18(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultamos de la variable 19
//			if(consultarVariable19(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultamos de la variable 20
//			if(consultarVariable20(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// variable 21 se realiza, en el metodo consultarVariable21_29_a_32
//			if(consultarVariable22_23(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			// variable 24
//			if(consultarVariable24(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// variable 25
//			if(consultarVariable25(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// variable 26
//			if(consultarVariable26(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// variable 27
//			if(consultarVariable27(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable 28
//			if(consultarVariable28(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			// consultar variable 29 a la 32
//			if(consultarVariable21_29_a_32(map_resultado, administradora,
//					id_paciente, fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			// consulta variable 33
//			if(consultarVariable33(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, variable_10_sexo, map_edad))
//				incluir_listado = true;
//
//			// consulta variable 34
//			if(consultarVariable34(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, variable_10_sexo, map_edad))
//				incluir_listado = true;
//
//			// Vacunas de la 35 a la 47
//			if(consultarVariable35(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable36(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable37(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable38(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable39(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable40(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable41(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable42(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable43(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable44(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable45(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable46(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable47(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			// consulta variable 48
//			if(consultarVariable48(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			// consultar variable de 49 a la 50
//			if(consultarVariable49_50(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 51
//			if(consultarVariable51(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 52
//			if(consultarVariable52(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 53
//			if(consultarVariable53(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 54
//			if(consultarVariable54(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 55
//			if(consultarVariable55(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 56
//			if(consultarVariable56(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable 57 a la 58
//			if(consultarVariable57_58(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 59
//			if(consultarVariable59(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 60
//			if(consultarVariable60(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 61
//			if(consultarVariable61(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 62
//			if(consultarVariable62(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			// consultar variable de 63
//			if(consultarVariable63(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final, map_edad))
//				incluir_listado = true;
//
//			// consultar variable de 64
//			if(consultarVariable64(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 65
//			if(consultarVariable65(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 66
//			if(consultarVariable66(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 67
//			if(consultarVariable67(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 68
//			if(consultarVariable68(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 69
//			if(consultarVariable69(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 70
//			if(consultarVariable70(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 71
//			if(consultarVariable71(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 72
//			if(consultarVariable72(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			// consultar variable de 73
//			if(consultarVariable73(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_rango_final))
//				incluir_listado = true;
//
//			if(consultarVariable74(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable75(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable76(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable77(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable78_79(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable80_81(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable82_83(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable84_85(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable86(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable87(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable88(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable89(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable90(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable91_92(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable93_95(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable96_98(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final, map_edad))
//				incluir_listado = true;
//
//			if(consultarVariable99(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable100_102(map_resultado, administradora,
//					id_paciente, fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable103(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable104(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable105(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable106_107(map_resultado, administradora,
//					id_paciente, fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable108_109(map_resultado, administradora,
//					id_paciente, fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable110(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable111(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable112(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable113(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable114(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable115(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable116(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable117(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			if(consultarVariable118(map_resultado, administradora, id_paciente,
//					fecha_inicio, fecha_final))
//				incluir_listado = true;
//
//			// verificamos si aplica a alguna de las variables
//			if(incluir_listado){
//				// Agregamos de la referencia, en el listado final
//				list_archivo_final.add(map_resultado);
//			}
//		}
		return map_configuracion;
	}

	/**
	 * 0 - No aplica 1 - Si 2 - No 21- Riesgo no evaluado (Anterior) <br/>
	 * La opcion 2 se usa en menores de 10 aÃ±os y para aquellos de Sexo
	 * Masculino. Se usa 21 si en el momento de recoleccion de la informacion no
	 * se indaga ni se tiene informacion al respecto de la identificacion de
	 * este riesgo. Por estandarizacion se ajustan los valores permitidos, se
	 * modifica la longitud del campo a 2. <br/>
	 * Validar que cuando la variable 14 registre 1 la variable 10 corresponda a
	 * F.
	 * 
	 * @author Luis Miguel
	 * @param map_edad
	 * @param sexo
	 * */
	private static boolean consultarVariable14(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String variable_10,
			Map<String, Integer> map_edad) {

		String NO_APLICA = "0";
		String SI = "1";
		String NO = "2";
		String RIESGO_NO_EVALUADO = "21";

		int anios = (Integer) map_edad.get("anios");
		if (anios <= 10 || variable_10.equalsIgnoreCase("M")) {
			map_resultado.put("variable_14", NO_APLICA);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("diagnosticos",
					IResolucion4505Constantes.CIE_VARIABLE_14_GESTACION);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_14", RIESGO_NO_EVALUADO);
			} else {
				Boolean aplica = (Boolean) ((Map<String, Object>) resultado_consulta_4505
						.get(0)).get(NOMBRE_RESPUESTA_CONSULTA_DX);
				map_resultado.put("variable_14", aplica ? SI : NO);
				return true;
			}
		}
		return false;
	}

	/**
	 * 0- No aplica 1- Si es mujer con sÃ­filis gestacional 2- Si es reciÃ©n
	 * nacido con sÃ­filis congÃ©nita 3- No 21- Riesgo no evaluado <br/>
	 * La opcion 0 se usa cuando corresponde a un hombre que no es RN (<28 dÃ­as
	 * de nacido) y la 21 cuando no se indaga ni se tiene informacion al
	 * respecto de la identificacion de este riesgo. Por estandarizacion se
	 * ajustan los valores permitidos, se modifica la longitud del campo a 2 <br/>
	 * Validar que cuando la variable 15 registre 1 la variable 10 corresponda a
	 * F. Validar que cuando la variable 15 registre 1 la variable 14
	 * corresponda a 1. Validar que cuando la variable 15 registre 2 el cÃ¡lculo
	 * de la edad* debe ser menor de 16 meses.
	 * 
	 * @author Luis Miguel
	 * @param variable_3_tipo_id
	 *            - tipo de identificacion
	 * @param variable_10_sexo
	 * */
	private static boolean consultarVariable15(Map map_resultado,
			String variable_3_tipo_id, String variable_10_sexo,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String variable_10,
			Map<String, Integer> map_edad) {

		String NO = "3";
		String SI_MUJER = "1";
		String SI_RECIEN_NACIDO = "2";
		String NO_APLICA = "0";
		String RIESGO_EVALUADO = "21";

		if (!variable_3_tipo_id.equals("RN")
				&& !variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			map_resultado.put("variable_15", NO_APLICA);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("diagnosticos",
					IResolucion4505Constantes.CIE_VARIABLE_14_GESTACION);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_15", RIESGO_EVALUADO);
			} else {
				Boolean aplica = (Boolean) ((Map<String, Object>) resultado_consulta_4505
						.get(0)).get(NOMBRE_RESPUESTA_CONSULTA_DX);
				if (aplica) {
					int anios = (Integer) map_edad.get("anios");
					int meses = (Integer) map_edad.get("meses");
					if (anios == 0 && meses <= 16) {
						map_resultado.put("variable_15", SI_RECIEN_NACIDO);
					} else {
						map_resultado.put("variable_15", SI_MUJER);
					}
				} else {
					map_resultado.put("variable_15", NO);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Hipertension Inducida por la Gestacion <br/>
	 * La opcion 0 se usa en menores de 10 aÃ±os y para aquellos de Sexo
	 * Masculino y la 21 cuando no se indaga ni se tiene informacion al respecto
	 * de la identificacion de este riesgo. Por estandarizacion se ajustan los
	 * valores permitidos, se modifica la longitud del campo a 2.
	 * 
	 * Validar que cuando la variable 16 registre 1 la variable 10 corresponda a
	 * F. Validar que cuando la variable 16 registre 1 la variable 14
	 * corresponda a 1.
	 * */
	private static boolean consultarVariable16(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO = "2";
		String SI = "1";
		String NO_APLICA = "0";
		String RIESGO_NO_EVALUADO = "21";

		String variable_10_sexo = (String) map_resultado.get("variable_10");
		String variable_14_gestacion = (String) map_resultado
				.get("variable_14");

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)
				&& variable_14_gestacion.equals(RESPUESTA_VARIABLE_14_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put(
					"diagnosticos",
					IResolucion4505Constantes.CIE_VARIABLE_16_HIPERTENCION_INDUCIDA);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (!resultado_consulta_4505.isEmpty()
					&& resultado_consulta_4505.get(0) != null) {
				Boolean aplica = (Boolean) ((Map<String, Object>) resultado_consulta_4505
						.get(0)).get(NOMBRE_RESPUESTA_CONSULTA_DX);
				map_resultado.put("variable_16", aplica ? SI : NO);
				return true;
			} else {
				map_resultado.put("variable_16", RIESGO_NO_EVALUADO);
			}
		} else {
			map_resultado.put("variable_16", NO_APLICA);
		}
		return false;
	}

	/**
	 * Hipotiroidismo CongÃ©nito <br/>
	 * 0- No 1- Si 2- No aplica 3- Riesgo no evaluado La opcion 0 se usa en
	 * mayores de 3 aÃ±os y la 21 cuando no se indaga ni se tiene informacion al
	 * respecto de la identificacion de este riesgo. Por estandarizacion se
	 * ajustan los valores permitidos, se modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable17(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		String NO = "2";
		String SI = "1";
		String NO_APLICA = "0";
		String RIESGO_NO_EVALUADO = "21";

		int anios = (Integer) map_edad.get("anios");
		if (anios < 3) {
			map_resultado.put("variable_17", NO_APLICA);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put(
					"diagnosticos",
					IResolucion4505Constantes.CIE_VARIABLE_17_HIPERTENCION_INDUCIDA);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (!resultado_consulta_4505.isEmpty()
					&& resultado_consulta_4505.get(0) != null) {
				Boolean aplica = (Boolean) ((Map<String, Object>) resultado_consulta_4505
						.get(0)).get(NOMBRE_RESPUESTA_CONSULTA_DX);
				map_resultado.put("variable_17", aplica ? SI : NO);
				return true;
			} else {
				map_resultado.put("variable_17", RIESGO_NO_EVALUADO);
			}
		}
		return false;
	}

	/**
	 * SintomÃ¡tico Respiratorio (poblacion general) <br/>
	 * 1- Si 2- No 21- Riesgo no evaluado<br/>
	 * La opcion 21 se usa cuando no se indaga ni se tiene informacion al
	 * respecto de la identificacion de este riesgo. Por estandarizacion se
	 * ajustan los valores permitidos, se modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable18(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO = "2";
		String SI = "1";
		String RIESGO_NO_EVALUADO = "21";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("diagnosticos",
				IResolucion4505Constantes.CIE_VARIABLE_18_HIPERTENCION_INDUCIDA);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_DX);
		if (!resultado_consulta_4505.isEmpty()
				&& resultado_consulta_4505.get(0) != null) {
			Boolean aplica = (Boolean) ((Map<String, Object>) resultado_consulta_4505
					.get(0)).get(NOMBRE_RESPUESTA_CONSULTA_DX);
			map_resultado.put("variable_18", aplica ? SI : NO);
			return true;
		} else {
			map_resultado.put("variable_18", RIESGO_NO_EVALUADO);
		}
		return false;
	}

	/**
	 * Tuberculosis Multidrogoresistente<br/>
	 * 0- No aplica 1- Si 2- No 21- Riesgo no evaluado <br/>
	 * La opcion 2 para usuarios con diagnÃ³stico de TBC que NO son
	 * Multidrogoresistentes se utiliza en los siguientes casos: a. No tiene
	 * criterios para multidrogoresistencia. b. Se descartÃ³
	 * multidrogoresistencia posterior a pruebas. c. EstÃ¡ en proceso de
	 * descartar multidrogoresistencia pero al corte del reporte aÃºn no se
	 * tienen los resultados. La opcion 0 para el usuario que no tiene
	 * diagnÃ³stico de TBC en ninguna de sus formas. La opcion 21 se usa cuando
	 * no se indaga ni se tiene informacion al respecto de la identificacion de
	 * Ã©ste riesgo. Por estandarizacion se ajustan los valores permitidos, se
	 * modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable19(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		// String NO = "2";
		String SI = "1";
		String NO_APLICA = "0";
		String RIESGO_NO_EVALUADO = "21";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("diagnosticos",
				IResolucion4505Constantes.CIE_VARIABLE_19_TUBERCULOSIS);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_DX);
		if (!resultado_consulta_4505.isEmpty()
				&& resultado_consulta_4505.get(0) != null) {
			Boolean aplica = (Boolean) ((Map<String, Object>) resultado_consulta_4505
					.get(0)).get(NOMBRE_RESPUESTA_CONSULTA_DX);
			if (aplica) {
				// TODO: Pendiente verificar si un paciente es
				// Multidrogoresistentes
				// no cuando presentan no es un
				map_resultado.put("variable_19", SI);
				return true;
			} else {
				map_resultado.put("variable_19", NO_APLICA);
			}
		} else {
			map_resultado.put("variable_19", RIESGO_NO_EVALUADO);
		}
		return false;
	}

	/**
	 * Lepra <br/>
	 * 1- Paucibacilar 2- Multibacilar 3- No 21- Riesgo no evaluado <br/>
	 * La opcion 21 se usa cuando no se indaga ni se tiene informacion al
	 * respecto de la identificacion de este riesgo. En la opcion 1 se registra
	 * la Lepra paucibacilar y la indeterminada. La opcion 3 se usarÃ¡ cuando se
	 * ha indagado si es sintomÃ¡tico de piel y se ha descartado la presencia del
	 * diagnÃ³stico. Por estandarizacion se ajustan los valores permitidos, se
	 * modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable20(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String PAUCIBACILAR = "1";
		// String MULTIBACILAR = "2";
		String NO = "3";
		String RIESGO_NO_EVALUADO = "21";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("diagnosticos",
				IResolucion4505Constantes.CIE_VARIABLE_20_OBESIDAD);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_DX);

		if (!resultado_consulta_4505.isEmpty()
				&& resultado_consulta_4505.get(0) != null) {
			Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
					.get(0);
			Boolean aplica = (Boolean) (map).get(NOMBRE_RESPUESTA_CONSULTA_DX);
			if (aplica) {
				// TODO: que pendiente verificar el tipo de datos a retornar
				// si es PAUCIBACILAR o MULTIBACILAR
				map_resultado.put("variable_20", PAUCIBACILAR);
			} else {
				map_resultado.put("variable_20", NO);
			}
			return true;
		} else {
			map_resultado.put("variable_20", RIESGO_NO_EVALUADO);
		}
		return false;
	}

	/**
	 * VÃ­ctima de Maltrato<br/>
	 * 0- No aplica 1- Si es Mujer vÃ­ctima del maltrato 2- Si es Menor vÃ­ctima
	 * del maltrato 3- No 21- Riesgo no evaluado <br/>
	 * La opcion 21 se usa cuando no se indaga ni se tiene informacion al
	 * respecto de la identificacion de este riesgo. Usar la opcion 0 para
	 * hombres mayores de 19 aÃ±os que no se encuentran incluidos en la guÃ­a de
	 * atencion integral. Por estandarizacion se ajustan los valores permitidos,
	 * se modifica la longitud del campo a 2.<br/>
	 * Validar que cuando la variable 22 registre 1 la variable 10 corresponda a
	 * F. Validar que cuando la variable 22 registre 2 el cÃ¡lculo de edad* debe
	 * ser menor a 19 aÃ±os y 3 meses.
	 * 
	 * */
	private static boolean consultarVariable22_23(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		String NO_APLICA = "0";
		String MUJER_VICTIMA_MALTRATO = "1";
		String MENOR_VICTIMA_MALTRATO = "2";
//		String NO = "3";
		String RIESGO_NO_EVALUADO = "21";

		// variable 23
		String SI_23 = "1";
//		String NO_23 = "2";
		String RIESGO_NO_EVALUADO_23 = "21";

		int ANIO_FILTRO = 19;
		int MES_FILTRO = 3;

		int meses = (Integer) map_edad.get("meses");

		String variable_10_sexo = (String) map_resultado.get("variable_10");

		if (!variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)
				&& meses > (ANIO_FILTRO * 12 + MES_FILTRO)) {
			map_resultado.put("variable_22", NO_APLICA);
			map_resultado.put("variable_23", RIESGO_NO_EVALUADO_23);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("diagnosticos",
					IResolucion4505Constantes.CIE_VARIABLE_22_MALTRATO);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);

			if (!resultado_consulta_4505.isEmpty()
					&& resultado_consulta_4505.get(0) != null) {
				Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
						.get(0);
				Boolean aplica = (Boolean) (map)
						.get(NOMBRE_RESPUESTA_CONSULTA_DX);
				if (aplica) {
					// VARIABLE 22
					if (meses > (ANIO_FILTRO * 12 + MES_FILTRO)) {
						map_resultado
								.put("variable_22", MUJER_VICTIMA_MALTRATO);
					} else {
						map_resultado
								.put("variable_22", MENOR_VICTIMA_MALTRATO);
					}

					// VARIABLE 23
					boolean aplica_violencia_sexual = aplicaViolenciaSexual(map);
					if (aplica_violencia_sexual) {
						map_resultado.put("variable_23", SI_23);
					} else {
						map_resultado.put("variable_23", RIESGO_NO_EVALUADO_23);
					}
					return true;
				} else {
					//TODO se cambio de no a no evaluado por seguridad
					map_resultado.put("variable_22", RIESGO_NO_EVALUADO);
					map_resultado.put("variable_23", RIESGO_NO_EVALUADO_23);
				}
			} else {
				map_resultado.put("variable_22", RIESGO_NO_EVALUADO);
				map_resultado.put("variable_23", RIESGO_NO_EVALUADO_23);
			}
		}
		return false;
	}

	/**
	 * Infecciones de Trasmision Sexual<br/>
	 * 1- Si<br/>
	 * 2- No<br/>
	 * 21- Riesgo no evaluado<br/>
	 * La opcion 21 se usa cuando no se indaga ni se tiene informacion al
	 * respecto a la identificacion de este riesgo. Por estandarizacion se
	 * ajustan los valores permitidos, se modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable24(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String SI = "1";
//		String NO = "2";
		String RIESGO_NO_EVALUADO = "21";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("diagnosticos",
				IResolucion4505Constantes.CIE_VARIABLE_24_INFECCIONES);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_DX);

		if (!resultado_consulta_4505.isEmpty()
				&& resultado_consulta_4505.get(0) != null) {
			Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
					.get(0);
			Boolean aplica = (Boolean) (map).get(NOMBRE_RESPUESTA_CONSULTA_DX);
			map_resultado.put("variable_24", aplica ? SI : RIESGO_NO_EVALUADO);
			return true;
		} else {
			map_resultado.put("variable_24", RIESGO_NO_EVALUADO);
		}
		return false;
	}

	/**
	 * Enfermedad Mental<br/>
	 * 1- Si el diagnÃ³stico es Ansiedad <br/>
	 * 2- Si el diagnÃ³stico es Depresion <br/>
	 * 3- Si el diagnÃ³stico es esquizofrenia <br/>
	 * 4- Si el diagnÃ³stico es DÃ©ficit de atencion por Hiperactividad <br/>
	 * 5- Si el diagnÃ³stico es consumo Sustancias Psicoactivas <br/>
	 * 6- Si el diagnÃ³stico es Trastorno del Ã�nimo Bipolar <br/>
	 * 7- No <br/>
	 * 21- Riesgo no evaluado <br/>
	 * Si tiene una enfermedad mental no incluida en la lista registre la opcion
	 * 7. La opcion 21 se usa cuando no se indaga ni se tiene informacion al
	 * respecto de la identificacion de este riesgo. Si hay mas de un
	 * diagnostico registrar el principal. Por estandarizacion se ajustan los
	 * valores permitidos, se modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable25(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String RIESGO_NO_EVALUADO = "21";
		String NO = "7";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_25);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put("variable_25", RIESGO_NO_EVALUADO);
		} else {
			String resultado = aplicaEnfermedadMental(resultado_consulta_4505.get(0));
			map_resultado.put("variable_25",
					resultado.equals(NO) ? RIESGO_NO_EVALUADO : resultado);
			return !resultado.equals(NO);
		}
		return false;
	}

	private static String aplicaEnfermedadMental(Map<String, Object> map) {
		String NO = "7";

		// OBTENEMOS DX
		String cie_principal = (String) map.get("cie_principal");
		String cie_relacionado1 = (String) map.get("cie_relacionado1");
		String cie_relacionado2 = (String) map.get("cie_relacionado2");
		String cie_relacionado3 = (String) map.get("cie_relacionado3");
		String cie_relacionado4 = (String) map.get("cie_relacionado4");

		String respuesta = consultarRespuestaEnfermedadMental(cie_principal);
		if (respuesta != null && !respuesta.trim().isEmpty())
			return respuesta;
		respuesta = consultarRespuestaEnfermedadMental(cie_relacionado1);
		if (respuesta != null && !respuesta.trim().isEmpty())
			return respuesta;
		respuesta = consultarRespuestaEnfermedadMental(cie_relacionado2);
		if (respuesta != null && !respuesta.trim().isEmpty())
			return respuesta;
		respuesta = consultarRespuestaEnfermedadMental(cie_relacionado3);
		if (respuesta != null && !respuesta.trim().isEmpty())
			return respuesta;
		respuesta = consultarRespuestaEnfermedadMental(cie_relacionado4);
		if (respuesta != null && !respuesta.trim().isEmpty())
			return respuesta;

		return NO;
	}

	private static String consultarRespuestaEnfermedadMental(String codigo_cie) {
		// diagnosticos relacionados
		String CIE_RELACIONADO_ANSIEDAD_F40 = "F40";
		String CIE_RELACIONADO_ANSIEDAD_F41 = "F41";
		String CIE_RELACIONADO_DEPRESEION_F33 = "F33";
		String CIE_RELACIONADO_ESQUIZOFRENIA_F20 = "F20";
		String CIE_RELACIONADO_HIPERACTIVIDAD_F90 = "F90";
		String CIE_RELACIONADO_PSICOACTIVAS_F1 = "F1";
		String CIE_RELACIONADO_BIPOLAR_F31 = "F31";

		// DAMOS PRIORIDAD POR DX PRINCIPAL
		if (codigo_cie.startsWith(CIE_RELACIONADO_ANSIEDAD_F40)
				|| codigo_cie.startsWith(CIE_RELACIONADO_ANSIEDAD_F41)) {
			return DIAGNOSTICO_ANSIEDAD_25;
		}

		if (codigo_cie.startsWith(CIE_RELACIONADO_DEPRESEION_F33)) {
			return DIAGNOSTICO_DEPRESION_25;
		}

		if (codigo_cie.startsWith(CIE_RELACIONADO_ESQUIZOFRENIA_F20)) {
			return DIAGNOSTICO_ESQUIZOFRENIA_25;
		}

		if (codigo_cie.startsWith(CIE_RELACIONADO_HIPERACTIVIDAD_F90)) {
			return DIAGNOSTICO_DEFICIT_HIPERACTIVIDAD_25;
		}

		if (codigo_cie.startsWith(CIE_RELACIONADO_PSICOACTIVAS_F1)) {
			return DIAGNOSTICO_SUSTANCIAS_PSICOACTIVAS_25;
		}

		if (codigo_cie.startsWith(CIE_RELACIONADO_BIPOLAR_F31)) {
			return DIAGNOSTICO_TRASTORNO_BIPOLAR_25;
		}

		return null;
	}

	private static boolean aplicaViolenciaSexual(Map<String, Object> map) {
		String cie_principal = (String) map.get("cie_principal");
		String cie_relacionado1 = (String) map.get("cie_relacionado1");
		String cie_relacionado2 = (String) map.get("cie_relacionado2");
		String cie_relacionado3 = (String) map.get("cie_relacionado3");
		String cie_relacionado4 = (String) map.get("cie_relacionado4");

		for (String cie : IResolucion4505Constantes.CIE_VARIABLE_23_VIOLENCIA_SEXUAL) {
			if (cie_principal.equals(cie) || cie_relacionado1.equals(cie)
					|| cie_relacionado2.equals(cie)
					|| cie_relacionado3.equals(cie)
					|| cie_relacionado4.equals(cie)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * CÃ¡ncer de CÃ©rvix<br/>
	 * 0- No aplica <br/>
	 * 1- Si <br/>
	 * 2- No <br/>
	 * 21- Riesgo no evaluado<br/>
	 * La opcion 21 se usa cuando no se indaga, ni se tiene informacion al
	 * respecto de la identificacion de este riesgo. Usar 0 en usuarios de sexo
	 * masculino. Por estandarizacion se ajustan los valores permitidos, se
	 * modifica la longitud a 2. <br/>
	 * Validar que cuando la variable 26 registre 1 la variable 10 corresponda a
	 * F.
	 * */
	private static boolean consultarVariable26(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO_APLICA = "0";
		String SI = "1";
//		String NO = "2";
		String RIESGO_NO_EVALUADO = "21";

		String variable_10_sexo = (String) map_resultado.get("variable_10");

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("diagnosticos",
					IResolucion4505Constantes.CIE_VARIABLE_26_CANCER_CERVIX);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_26", RIESGO_NO_EVALUADO);
			} else {
				Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
						.get(0);
				Boolean aplica = (Boolean) (map)
						.get(NOMBRE_RESPUESTA_CONSULTA_DX);
				map_resultado.put("variable_26", aplica ? SI : RIESGO_NO_EVALUADO);
				return aplica;
			}
		} else {
			map_resultado.put("variable_26", NO_APLICA);
		}
		return false;
	}

	/**
	 * CÃ¡ncer de Seno (poblacion general)<br/>
	 * 1- Si <br/>
	 * 2- No <br/>
	 * 21- Riesgo no evaluado<br/>
	 * La opcion 21 se usa cuando no se indaga ni se tiene informacion al
	 * respecto de la identificacion de este riesgo. Este riesgo incluye la
	 * identificacion en hombres y sin lÃ­mite de edad. Por estandarizacion se
	 * ajustan los valores permitidos, se modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable27(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String SI = "1";
//		String NO = "2";
		String RIESGO_NO_EVALUADO = "21";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("diagnosticos",
				IResolucion4505Constantes.CIE_VARIABLE_27_CANCER_SENO);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_DX);
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put("variable_27", RIESGO_NO_EVALUADO);
		} else {
			Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
					.get(0);
			Boolean aplica = (Boolean) (map).get(NOMBRE_RESPUESTA_CONSULTA_DX);
			map_resultado.put("variable_27", aplica ? SI : RIESGO_NO_EVALUADO);
			return aplica;
		}
		return false;
	}

	/**
	 * Fluorosis Dental 0- No 1- Si 2- No aplica 3- Riego no evaluado
	 * */
	private static boolean consultarVariable28(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		String NO = "0";
		String SI = "1";
		String NO_APLICA = "2";
		String RIESGO_NO_EVALUADO = "3";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);

		int anios = (Integer) map_edad.get("anios");
		if (anios <= 3) {
			map_resultado.put("variable_28", NO_APLICA);
		} else {
			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_28);
			// la verificacon del contenido del objeto en la posicion 0 es por
			// que aveces retorna null
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_28", RIESGO_NO_EVALUADO);
			} else {
				String resultado = (String) resultado_consulta_4505.get(0).get(
						"odont_grama_presenta_fluorosis");
				if (resultado.equals("S")) {
					map_resultado.put("variable_28", SI);
				} else {
					map_resultado.put("variable_28", NO);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 29) Fecha del Peso AAAA-MM-DD Si no se toma registrar 1800-01-01 FECHA <br/>
	 * 30) Peso en Kilogramos Se registra el dato obtenido de la medicion. Si no
	 * se toma registrar 999 NUMÃ‰RICO <br/>
	 * 31) Fecha de la Talla 10 AAAA-MM-DD Si no se toma registrar 1800-01-01
	 * FECHA<br/>
	 * 32) Talla en CentÃ­metros 4 Se registra el dato obtenido de la medicion.
	 * Si no se toma registrar 999 <br/>
	 * Variable 21 Obesidad o Desnutricion Proteico CalÃ³rica<br/>
	 * 1- Si es Obesidad 2- Si es Desnutricion Proteico CalÃ³rica 3- No 21-
	 * Riesgo no evaluado
	 * 
	 * */
	private static boolean consultarVariable21_29_a_32(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		String NO_SE_TOMO_FECHA = "1800-01-01";
		String NO_SE_TOMO_REGISTRO = "999";

		// estos son para la variable 21 de imc
		String SI_OBESIDAD = "1";
		String SI_DESNUTRICION = "2";
		String NO = "3";
		String RIESGO_NO_EVALUADO = "21";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_29_32);

		if (resultado_consulta_4505.isEmpty()) {
			map_resultado.put("variable_29", NO_SE_TOMO_FECHA);
			map_resultado.put("variable_30", NO_SE_TOMO_REGISTRO);
			map_resultado.put("variable_31", NO_SE_TOMO_FECHA);
			map_resultado.put("variable_32", NO_SE_TOMO_REGISTRO);
			map_resultado.put("variable_21", RIESGO_NO_EVALUADO);
		} else if (resultado_consulta_4505.get(0) != null) {
			Map map = resultado_consulta_4505.get(0);
			Timestamp fecha_toma = (Timestamp) map.get("fecha");
			String peso = (String) map.get("peso");
			String talla = (String) map.get("talla");

			if (fecha_toma != null) {
				String fecha_formato = formato_fecha.format(fecha_toma);

				Double peso_calculo = null;
				Double talla_calculo = null;

				if (peso != null && !peso.trim().isEmpty()
						&& peso.matches("[0-9.]*$")) {
					map_resultado.put("variable_29", fecha_formato);
					map_resultado.put("variable_30", validarTamanio(peso, 4));
					peso_calculo = Double.parseDouble(peso);
				}

				if (talla != null && !talla.trim().isEmpty()
						&& talla.matches("[0-9.]*$")) {
					map_resultado.put("variable_31", fecha_formato);
					map_resultado.put("variable_32", validarTamanio(talla, 4));
					talla_calculo = Double.parseDouble(talla);
				}

				if (peso_calculo != null && talla_calculo != null) {
					Double imc = UtilidadSignosVitales.calcularIMC(
							peso_calculo, talla_calculo);
					if (imc != null) {
						int anios = (Integer) map_edad.get("anios");
						if (anios > 18 && imc.doubleValue() >= 30) {
							map_resultado.put("variable_21", SI_OBESIDAD);
						} else if (imc.doubleValue() < 18.4
								&& imc.doubleValue() > 0) {
							map_resultado.put("variable_21", SI_DESNUTRICION);
						} else {
							map_resultado.put("variable_21", NO);
						}
					} else {
						map_resultado.put("variable_21", RIESGO_NO_EVALUADO);
					}
				} else {
					map_resultado.put("variable_21", RIESGO_NO_EVALUADO);
				}
				return true;
			} else {
				map_resultado.put("variable_21", RIESGO_NO_EVALUADO);
				map_resultado.put("variable_29", NO_SE_TOMO_FECHA);
				map_resultado.put("variable_30", NO_SE_TOMO_REGISTRO);
				map_resultado.put("variable_31", NO_SE_TOMO_FECHA);
				map_resultado.put("variable_32", NO_SE_TOMO_REGISTRO);
			}
		}
		return false;
	}

	/**
	 * Fecha Probable de Parto AAAA-MM-DD Si no se tiene el dato registrar
	 * 1800-01- 01 Si no aplica registrar 1845-01-01
	 * 
	 * @param variable_10_sexo
	 * @param map_edad
	 * */
	private static boolean consultarVariable33(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String variable_10_sexo,
			Map<String, Integer> map_edad) {
		String NO_SE_TIENE_DATO = "1800-01-01";
		String NO_APLICA = "1845-01-01";

		// si es maculino no aplica
		if (variable_10_sexo.equalsIgnoreCase("M")) {
			map_resultado.put("variable_33", NO_APLICA);
		} else {
			int anios = (Integer) map_edad.get("anios");
			if (anios < 12) {
				map_resultado.put("variable_33", NO_APLICA);
			} else {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("codigo_empresa", administradora.getCodigo_empresa());
				params.put("codigo_sucursal",
						administradora.getCodigo_sucursal());
				params.put("codigo_administradora", administradora.getCodigo());
				params.put("nro_identificacion", id_paciente);
				params.put("fecha_inicio", fecha_inicio);
				params.put("fecha_final", fecha_final);

				List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
						.getReportService().getReport(params,
								IResolucion4505Constantes.NAMESPACE_XML,
								IResolucion4505Constantes.VARIABLE_33);

				if (resultado_consulta_4505.isEmpty() || resultado_consulta_4505.get(0) == null) {
					map_resultado.put("variable_33", NO_SE_TIENE_DATO);
				} else {
					Map<String, Object> mapa_respuesta = resultado_consulta_4505
							.get(0);
					if (mapa_respuesta != null) {
						Timestamp timestamp = (Timestamp) mapa_respuesta
								.get("fecha");
						if (timestamp != null) {
							map_resultado.put("variable_33",
									formato_fecha.format(timestamp));
						} else {
							map_resultado.put("variable_33", NO_SE_TIENE_DATO);
						}
					} else {
						map_resultado.put("variable_33", NO_SE_TIENE_DATO);
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Edad Gestacional al Nacer Se registra el dato de la edad gestacional en
	 * semanas. Si no tiene el dato registrar 99 Si no aplica registrar 98
	 * 
	 * @author Luis Miguel
	 * @param map_edad
	 * */
	private static boolean consultarVariable34(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String variable_10_sexo,
			Map<String, Integer> map_edad) {

		String NO_APLICA = "98";
		String NO_SE_TIENE = "99";

		int anios = (Integer) map_edad.get("anios");
		if (anios > 5) {
			map_resultado.put("variable_34", NO_APLICA);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_34);

			if (resultado_consulta_4505.isEmpty() || resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_34", NO_SE_TIENE);
			} else {
				Map<String, Object> mapa_respuesta = resultado_consulta_4505
						.get(0);
				if (mapa_respuesta != null) {
					Number sem_gestacion = (Number) resultado_consulta_4505
							.get(0).get("sem_gestacion");
					map_resultado.put("variable_34",
							sem_gestacion != null ? sem_gestacion.intValue() + ""
									: NO_SE_TIENE);
					return sem_gestacion != null;
				} else {
					map_resultado.put("variable_34", NO_SE_TIENE);
				}
			}
		}
		return false;
	}

	private static boolean consultarVacuna(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, boolean aplica,
			String[] codigo_cups, String nombre_variable) {

		String NO_APLICA = "0";
		String SIN_DATO = "22";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups", codigo_cups);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_VACUNAS);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			if (!aplica) {
				map_resultado.put(nombre_variable, NO_APLICA);
			} else {
				map_resultado.put(nombre_variable, SIN_DATO);
			}
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			String respuesta_4505 = (String) map.get("respuesta_4505");
			if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()) {
				map_resultado.put(nombre_variable, respuesta_4505);
				return true;
			} else {
				map_resultado.put(nombre_variable, SIN_DATO);
			}
		}
		return false;
	}

	/**
	 * BCG <br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * determinadas en los actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * <br/>
	 * La opcion 0 se usa en mayores de 6 aÃ±os de edad, que no tienen
	 * condiciones especiales que ameriten la vacuna. <br/>
	 * La opcion 1 se utiliza: <br/>
	 * a. En todos los RN, incluyendo aquellos que por condiciones de prematurez
	 * o BPN no se vacunan inmediatamente despuÃ©s del nacimiento.<br/>
	 * b. En el registro opcional de persona diferente a los menores reciÃ©n
	 * nacidos que por condiciones especiales actualizados lineamientos del PAI
	 * que se cubren con los biolÃ³gicos suministrados por el programa.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * */
	private static boolean consultarVariable35(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int anios = (Integer) map_edad.get("anios");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, anios <= 6,
				IResolucion4505Constantes.VACUNA_BCG, "variable_35");
	}

	/**
	 * Hepatitis B menores de 1 aÃ±o<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * Registrar solo la dosis de RN. La opcion 0 se usa en mayores de 28 dÃ­as
	 * de nacidos que no tienen condiciones especiales que ameriten el biolÃ³gico
	 * en esta presentacion.<br/>
	 * Se podrÃ¡n registrar de manera opcional aquellas vacunas que se aplican a
	 * poblacion en condiciones especiales determinadas en los lineamientos del
	 * PAI que se cubren con los biolÃ³gicos suministrados por el programa usando
	 * la opcion 1.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * */
	private static boolean consultarVariable36(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int dias = (int) map_edad.get("dias");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, dias <= 28,
				IResolucion4505Constantes.VACUNA_HEPATITIS_B_MENORES_1_ANIO,
				"variable_36");
	}

	/**
	 * Pentavalente<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 2- Dos Dosis<br/>
	 * 3- Tres Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa para mayores de 2 aÃ±os de edad.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.
	 * */
	private static boolean consultarVariable37(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int anios = (Integer) map_edad.get("anios");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, anios <= 2,
				IResolucion4505Constantes.VACUNA_PENTAVALENTE, "variable_37");
	}

	/**
	 * Polio<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 2- Dos Dosis<br/>
	 * 3- Tres Dosis<br/>
	 * 4- Cuatro Dosis<br/>
	 * 5- Cinco Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en mayores de 6 aÃ±os. Se debe incluir el registro de
	 * polio oral e inyectable. Por estandarizacion se ajustan los valores
	 * permitidos, se modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable38(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int anios = (Integer) map_edad.get("anios");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, anios <= 6,
				IResolucion4505Constantes.VACUNA_POLIO, "variable_38");
	}

	/**
	 * DPT menores de 5 aÃ±os<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 4- Cuatro Dosis<br/>
	 * 5- Cinco Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * Registrar las dosis de refuerzos Ãºnicamente. La opcion 0 se usa en
	 * mayores de 6 aÃ±os de edad. Por estandarizacion se ajustan los valores
	 * permitidos, se modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable39(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int anios = (Integer) map_edad.get("anios");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, anios <= 6,
				IResolucion4505Constantes.VACUNA_DPT_MENORES_5_NO_APLICA,
				"variable_39");
	}

	/**
	 * Rotavirus<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 2- Dos Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no *
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en mayores de 8 meses de edad. Por estandarizacion se
	 * ajustan los valores permitidos, se modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable40(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int meses = (Integer) map_edad.get("meses");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, meses <= 8,
				IResolucion4505Constantes.VACUNA_ROTAVIRUS, "variable_40");
	}

	/**
	 * Neumococo<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 2- Dos Dosis<br/>
	 * 3- Tres Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en mayores de 3 aÃ±os.<br/>
	 * Se podrÃ¡n registrar de manera opcional aquellas vacunas que se aplican a
	 * poblacion en condiciones especiales determinadas en los lineamientos del
	 * PAI que se cubren con los biolÃ³gicos suministrados por el programa usando
	 * la opcion 1.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * */
	private static boolean consultarVariable41(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int anios = (Integer) map_edad.get("anios");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, anios <= 3,
				IResolucion4505Constantes.VACUNA_NEUMOCOCO, "variable_41");
	}

	/**
	 * Influenza NiÃ±os<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 2- Dos Dosis<br/>
	 * 3- Tres Dosis Anual<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en mayores de 3 aÃ±os.<br/>
	 * Se podrÃ¡n registrar de manera opcional aquellas vacunas que se aplican a
	 * poblacion en condiciones especiales determinadas en los lineamientos del
	 * PAI que se cubren con los biolÃ³gicos suministrados por el programa usando
	 * la opcion 1.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * */
	private static boolean consultarVariable42(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int anios = (Integer) map_edad.get("anios");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, anios <= 3,
				IResolucion4505Constantes.VACUNA_INFLUENZA_NINIOS,
				"variable_42");
	}

	/**
	 * Fiebre Amarilla niÃ±os de 1 aÃ±o<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en mayores de 2 aÃ±os.<br/>
	 * Se podrÃ¡n registrar de manera opcional aquellas vacunas que se aplican a
	 * poblacion que lo requiere segÃºn lo determinado en los lineamientos del
	 * PAI que se cubren con los biolÃ³gicos suministrados por el programa usando
	 * la opcion 1.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * */
	private static boolean consultarVariable43(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int anios = (Integer) map_edad.get("anios");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, anios <= 2,
				IResolucion4505Constantes.VACUNA_FIEBRE_AMARILLA_NINIOS_1_ANIO,
				"variable_43");
	}

	/**
	 * Hepatitis A<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en mayores de 2 aÃ±os. Por estandarizacion se ajustan
	 * los valores permitidos, se modifica la longitud del campo a 2.
	 * */
	private static boolean consultarVariable44(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int anios = (Integer) map_edad.get("anios");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, anios <= 2,
				IResolucion4505Constantes.VACUNA_HEPATITIS_A, "variable_44");
	}

	/**
	 * Triple Viral NiÃ±os<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 2- Dos Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en mayores de 6 aÃ±os de edad.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * */
	private static boolean consultarVariable45(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		int anios = (Integer) map_edad.get("anios");
		return consultarVacuna(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, anios <= 6,
				IResolucion4505Constantes.VACUNA_TRIPLE_VIRAL_NINIOS,
				"variable_45");
	}

	/**
	 * Virus del Papiloma Humano (VPH)<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 2- Dos Dosis<br/>
	 * 3- Tres Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en personas de sexo Masculino. <br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2. <br/>
	 * Validar que cuando la variable 46 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * */
	private static boolean consultarVariable46(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		String NO_APLICA = "0";
		String variable_10_sexo = (String) map_resultado.get("variable_10");
		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			return consultarVacuna(map_resultado, administradora, id_paciente,
					fecha_inicio, fecha_final, true,
					IResolucion4505Constantes.VACUNA_VIRUS_PAPILOMA_HUMANO,
					"variable_46");
		} else {
			map_resultado.put("variable_46", NO_APLICA);
		}
		return false;
	}

	/**
	 * TD o TT Mujeres en Edad FÃ©rtil 15 a 49 aÃ±os<br/>
	 * Registre el dato del Ãºltimo nÃºmero de dosis aplicada asÃ­:<br/>
	 * 0- No aplica<br/>
	 * 1- Una Dosis<br/>
	 * 2- Dos Dosis<br/>
	 * 3- Tres Dosis<br/>
	 * 4- Cuatro Dosis<br/>
	 * 5- Cinco Dosis<br/>
	 * 16- No se administra por una Tradicion<br/>
	 * 17- No se administra por una Condicion de Salud<br/>
	 * 18- No se administra por Negacion del usuario<br/>
	 * 19- No se administra por tener datos de contacto del usuario no
	 * actualizados<br/>
	 * 20- No se administra por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en personas de sexo Masculino. <br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2. <br/>
	 * Validar que cuando la variable 47 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.
	 * */
	private static boolean consultarVariable47(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		String NO_APLICA = "0";
		String variable_10_sexo = (String) map_resultado.get("variable_10");
		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			return consultarVacuna(
					map_resultado,
					administradora,
					id_paciente,
					fecha_inicio,
					fecha_final,
					true,
					IResolucion4505Constantes.VACUNA_TD_TT_MUJERES_EDAD_FERTIL_15_49_ANIOS,
					"variable_47");
		} else {
			map_resultado.put("variable_47", NO_APLICA);
		}
		return false;
	}

	/**
	 * Control de Placa Bacteriana 0- No se realiza por una Tradicion 1- No se
	 * realiza por una Condicion de Salud 2- No se realiza por Negacion del
	 * usuario 3- No se realiza por tener datos de contacto del usuario no
	 * actualizados 4- No se realiza por otras razones 5- Si â€“ 1ra vez en el aÃ±o
	 * 6- Si â€“ 2da vez en el aÃ±o 7- Sin dato 8- No aplica
	 * */
	private static boolean consultarVariable48(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		String NO_APLICA = "8";
		String SIN_DATO = "7";
		String SEGUNDA_VEZ_ANIO = "6";
		String PRIMERA_VEZ_ANIO = "5";

		int anios = (Integer) map_edad.get("anios");
		if (anios < 2) {
			map_resultado.put("variable_48", NO_APLICA);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put(
					"codigo_cups_control_placa",
					IResolucion4505Constantes.CODIGO_CUPS_CONTROL_PLACA_BACTERIANA);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_48);
			if (resultado_consulta_4505.isEmpty() || resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_48", SIN_DATO);
			} else {
				Map<String, Object> mapa_respuesta = resultado_consulta_4505
						.get(0);
				if (mapa_respuesta != null) {
					Long total_control = (Long) resultado_consulta_4505.get(0)
							.get("total_control");
					if (total_control != null) {
						if (total_control > 1) {
							map_resultado.put("variable_48", SEGUNDA_VEZ_ANIO);
						} else {
							map_resultado.put("variable_48", PRIMERA_VEZ_ANIO);
						}
						return true;
					} else {
						map_resultado.put("variable_48", SIN_DATO);
					}
				} else {
					map_resultado.put("variable_48", SIN_DATO);
				}
			}
		}
		return false;
	}

	/**
	 * Fecha atencion parto cesÃ¡rea<br/>
	 * <h1>
	 * la variable 10</h1> AAAA-MM-DD Si no se tiene el dato registrar
	 * 1800-01-01 Si no aplica registrar 1845-01-01 Validar que cuando la
	 * variable 49 registre un dato diferente a 1845-01-01 la variable 10
	 * corresponda a F.
	 * */
	private static boolean consultarVariable49_50(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO_SE_TIENE_DATO = "1800-01-01";
		String NO_APLICA = "1845-01-01";

		String variable_10_sexo = (String) map_resultado.get("variable_10");
		String variable_14_gestacion = (String) map_resultado
				.get("variable_14");

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)
				&& variable_14_gestacion.equals(RESPUESTA_VARIABLE_14_FEMENINO)) {
			// consultamos informacion de fecha de parto
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups_parto",
					IResolucion4505Constantes.CODIGO_CUPS_TENCION_AL_PARTO);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_49_50);

			if (resultado_consulta_4505.isEmpty() || resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_49", NO_SE_TIENE_DATO);
				map_resultado.put("variable_50", NO_SE_TIENE_DATO);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				if (map != null) {
					Timestamp fecha_atencion = (Timestamp) map
							.get("fecha_atencion");
					Timestamp fecha_egreso = (Timestamp) map
							.get("fecha_egreso");

					map_resultado.put(
							"variable_49",
							fecha_atencion != null ? formato_fecha
									.format(fecha_atencion) : NO_SE_TIENE_DATO);
					map_resultado.put(
							"variable_50",
							fecha_egreso != null ? formato_fecha
									.format(fecha_egreso) : NO_SE_TIENE_DATO);
					return fecha_atencion != null || fecha_egreso != null;
				} else {
					map_resultado.put("variable_49", NO_SE_TIENE_DATO);
					map_resultado.put("variable_50", NO_SE_TIENE_DATO);
				}
			}
		} else {
			map_resultado.put("variable_49", NO_APLICA);
			map_resultado.put("variable_50", NO_APLICA);
		}
		return false;
	}

	/**
	 * Fecha de consejeria materna<br/>
	 * Fecha de ConsejerÃ­a en Lactancia Materna: Registre en el formato
	 * AAAA-MM-DD la fecha en que se hace la primera valoracion mÃ©dica
	 * ambulatoria del reciÃ©n nacido, teniendo en cuenta las especificaciones
	 * del Anexo I en su numeral 1.<br/>
	 * Si no se tiene el dato registrar 1800-01-01,<br/>
	 * si no se realiza por tener una tradicion que se lo impide registrar
	 * 1805-01-01,<br/>
	 * si no se realiza por una condicion de salud registrar 1810-01-01,<br/>
	 * si no se realiza por negacion del usuario registrar 1825-01-01,<br/>
	 * si no se realiza por tener los datos de contacto no actualizados y no se
	 * logra su localizacion registrar 1830-01-01,<br/>
	 * si no se realiza por otras razones registrar 1835-01-01<br/>
	 * o si no aplica registrar 1845-01- 01. TODO Queda pendiente por varificar
	 * la respuesta de esa variable
	 * */
	private static boolean consultarVariable51(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
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

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_51);

		if (!variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			map_resultado.put("variable_51", NO_APLICA);
		} else {
			String variable_9_fech_nacimiento = (String) map_resultado
					.get("variable_9");
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				Map<String, Integer> map_edad = Util
						.getEdadYYYYMMDD(java.sql.Date
								.valueOf(variable_9_fech_nacimiento));
				int anios = (Integer) map_edad.get("anios");
				if (anios > 10) {
					map_resultado.put("variable_51", NO_SE_TIENE_REGISTRO);
				} else {
					map_resultado.put("variable_51", NO_APLICA);
				}
			} else {
				Map<String, Object> mapa_respuesta = resultado_consulta_4505
						.get(0);
				Timestamp fecha_ingreso = (Timestamp) mapa_respuesta
						.get("fecha_ingreso");
				Timestamp fecha_atencion = (Timestamp) mapa_respuesta
						.get("fecha_atencion");
				fecha_atencion = fecha_atencion != null ? fecha_atencion
						: fecha_ingreso;
				if (fecha_atencion != null) {
					Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
							java.sql.Date.valueOf(variable_9_fech_nacimiento),
							fecha_atencion);
					int anios = (Integer) map_edad.get("anios");
					if (anios > 10) {
						map_resultado.put("variable_51",
								formato_fecha.format(fecha_atencion));
						return true;
					} else {
						map_resultado.put("variable_51", NO_APLICA);
					}
				} else {
					Map<String, Integer> map_edad = Util
							.getEdadYYYYMMDD(java.sql.Date
									.valueOf(variable_9_fech_nacimiento));
					int anios = (Integer) map_edad.get("anios");
					if (anios > 10) {
						map_resultado.put("variable_51", NO_SE_TIENE_REGISTRO);
					} else {
						map_resultado.put("variable_51", NO_APLICA);
					}
				}
			}
		}
		return false;
	}

	/**
	 * Control ReciÃ©n Nacido<br/>
	 * AAAA-MM-DD<br/>
	 * Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01- 01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Registrar el control que se debe realizar antes del 1er mes de nacido. La
	 * opcion no aplica 1845-01-01 se utiliza en aquella poblacion no RN. <br/>
	 * Validar que cuando la variable 52 registre un dato diferente a 1845-01-01
	 * el cÃ¡lculo de la edad* debe ser menor a 30 dÃ­as.
	 * */
	private static boolean consultarVariable52(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarVariableFechaConsulta(map_resultado, administradora,
				id_paciente, fecha_inicio, fecha_final,
				IResolucion4505Constantes.CUPS_52_CONTROL_RECIEN_NACIDO,
				"variable_52", true);
	}

	/**
	 * Planificacion Familiar Primera vez<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * El dato se registra como lo reporte la IPS, en los casos donde la IPS
	 * atiende al usuario en la transferencia por ser proveedor Ãºnico en el
	 * municipio. La opcion 1845-01-01 se utiliza en aquella poblacion no objeto
	 * de esta actividad. EstÃ¡ actividad aplica para hombres y mujeres.
	 * */
	private static boolean consultarVariable53(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarVariableFechaConsulta(
				map_resultado,
				administradora,
				id_paciente,
				fecha_inicio,
				fecha_final,
				IResolucion4505Constantes.CUPS_53_PLANIFICACION_FAMILIAR_PRIMERA_VEZ,
				"variable_53", true);
	}

	private static boolean consultarVariableFechaConsulta(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String[] codigo_cups,
			String nombre_variable, boolean habilitar_validacion) {
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
		params.put("codigo_cups", codigo_cups);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService()
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);
		String variable_9_fech_nacimiento = (String) map_resultado
				.get("variable_9"); // fecha nacimiento
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			if (habilitar_validacion) {
				Map<String, Integer> map_edad = Util
						.getEdadYYYYMMDD(java.sql.Date
								.valueOf(variable_9_fech_nacimiento));
				int meses = (Integer) map_edad.get("meses");
				if (meses < 30) {
					map_resultado.put(nombre_variable, NO_SE_TIENE_REGISTRO);
				} else {
					map_resultado.put(nombre_variable, NO_APLICA);
				}
			} else {
				map_resultado.put(nombre_variable, NO_APLICA);
			}
		} else {
			Map<String, Object> mapa_respuesta = resultado_consulta_4505.get(0);
			Timestamp fecha_consulta = (Timestamp) mapa_respuesta
					.get("fecha_consulta");

			if (habilitar_validacion) {
				Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
						java.sql.Date.valueOf(variable_9_fech_nacimiento),
						fecha_consulta);
				int meses = (Integer) map_edad.get("meses");
				if (meses < 30) {
					map_resultado.put(nombre_variable,
							formato_fecha.format(fecha_consulta));
					return true;
				} else {
					map_resultado.put(nombre_variable, NO_APLICA);
				}
			} else {
				map_resultado.put(nombre_variable,
						formato_fecha.format(fecha_consulta));
				return true;
			}
		}
		return false;
	}

	/**
	 * Suministro de MÃ©todo Anticonceptivo<br/>
	 * 0- No aplica<br/>
	 * 1- Dispositivo Intrauterino<br/>
	 * 2- Dispositivo Intrauterino y Barrera<br/>
	 * 3- Implante SubdÃ©rmico<br/>
	 * 4- Implante SubdÃ©rmico y Barrera<br/>
	 * 5- Oral<br/>
	 * 6- Oral y Barrera<br/>
	 * 7- Inyectable Mensual<br/>
	 * 8- Inyectable Mensual y Barrera<br/>
	 * 9- Inyectable Trimestral<br/>
	 * 10- Inyectable Trimestral y Barrera<br/>
	 * 11- Emergencia<br/>
	 * 12- Emergencia y Barrera<br/>
	 * 13- Esterilizacion<br/>
	 * 14- Esterilizacion y Barrera<br/>
	 * 15- Barrera<br/>
	 * 16- No se suministra por una Tradicion<br/>
	 * 17- No se suministra por una Condicion de Salud<br/>
	 * 18- No se suministra por Negacion de la usuaria<br/>
	 * 20- No se suministra por otras razones<br/>
	 * 21- Registro no Evaluado<br/>
	 * <br/>
	 * Registre el mÃ©todo que se entrega al usuario segÃºn la eleccion realizada.<br/>
	 * En el caso que el usuario ya tenga un mÃ©todo aplicado o en los casos que
	 * se tiene planificacion definitiva, se registra la opcion del mÃ©todo que
	 * actualmente se tiene asÃ­ no se suministre en la intervencion actual.<br/>
	 * Para el caso de las mujeres con histerectomÃ­a a las cuales no se
	 * suministra mÃ©todo registre la opcion 17.<br/>
	 * La opcion 0 se utiliza en aquella poblacion no objeto de esta actividad.<br/>
	 * Por estandarizacion se ajustan los valores permitidos.
	 * */
	private static boolean consultarVariable54(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {

		String REGISTRO_NO_EVALUADO = "21";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_54);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put("variable_54", REGISTRO_NO_EVALUADO);
		} else {
			Map<String, Object> map_respuesta_4505 = resultado_consulta_4505
					.get(0);
			String metodo_adoptado = (String) map_respuesta_4505
					.get("metodo_adoptado");
			if (metodo_adoptado != null && !metodo_adoptado.trim().isEmpty()) {
				map_resultado.put("variable_54", metodo_adoptado);
				return true;
			} else {
				map_resultado.put("variable_54", REGISTRO_NO_EVALUADO);
			}
		}
		return false;
	}

	/**
	 * Fecha Suministro de MÃ©todo Anticonceptivo<br/>
	 * AAAA-MM-DD<br/>
	 * Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no aplica registrar 1845-01-01 Registrar la fecha en que se realiza la
	 * entrega efectiva del mÃ©todo.<br/>
	 * Para el caso de los mÃ©todos aplicados previamente y la planificacion
	 * definitiva en que no se tenga la fecha exacta se puede registrar de la
	 * siguiente manera:<br/>
	 * a. Si solo se tiene el aÃ±o se coloca el dÃ­a 01 del mes 01 y el dato del
	 * aÃ±o que se tiene.<br/>
	 * b. Si se tiene el mes y el aÃ±o se coloca el dÃ­a 01 del mes y el aÃ±o que
	 * se tiene.<br/>
	 * c. Si no se tiene el dato se registra la opcion 1800-01-01. La opcion
	 * 1845-01-01 se utiliza en aquella poblacion no objeto de esta actividad.
	 * */
	private static boolean consultarVariable55(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {

		String NO_SE_TIENE_DATO = "1800-01-01";
		String NO_APLICA = "1845-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_55);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put("variable_55", NO_SE_TIENE_DATO);
		} else {
			Map<String, Object> map_respuesta_4505 = resultado_consulta_4505
					.get(0);
			Timestamp fecha_atencion = (Timestamp) map_respuesta_4505
					.get("fecha_atencion");
			Timestamp fecha_ingreso = (Timestamp) map_respuesta_4505
					.get("fecha_ingreso");

			fecha_atencion = fecha_atencion != null ? fecha_atencion
					: fecha_ingreso;
			String variable_9_fech_nacimiento = (String) map_resultado
					.get("variable_9");
			String variable_10_sexo = (String) map_resultado.get("variable_10");

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento),
					fecha_atencion);
			int anios = (Integer) map_edad.get("anios");

			if (ManejadorPoblacion.isAplicaPlanificacion(variable_10_sexo,
					anios)) {
				map_resultado.put("variable_55",
						formato_fecha.format(fecha_atencion));
				return true;
			} else {
				map_resultado.put("variable_55", NO_APLICA);
			}
		}
		return false;
	}

	/**
	 * 56) Control Prenatal de Primera vez <br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01 <br/>
	 * <br/>
	 * El dato se registra como lo reporte la IPS, en los casos donde la IPS
	 * atiende al usuario en la transferencia por ser proveedor Ãºnico en el
	 * municipio. Usar la opcion 1845-01-01 en poblacion masculina y mujeres no
	 * gestantes. <br/>
	 * <br/>
	 * Validar que cuando la variable 56 registre un dato diferente a 1845-01-01
	 * la variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 56 registre un dato diferente a 1845-01-01
	 * la variable 14 corresponda a 1.<br/>
	 * */
	private static boolean consultarVariable56(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		String NO_APLICA = "1845-01-01";

		String variable_10_sexo = (String) map_resultado.get("variable_10");
		String variable_14_gestacion = (String) map_resultado
				.get("variable_14");

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)
				&& variable_14_gestacion.equals(RESPUESTA_VARIABLE_14_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			// para esta variable no aplican las fechas
			// sino que este en control actualmente
			// params.put("fecha_inicio", fecha_inicio);
			// params.put("fecha_final", fecha_final);
			params.put("dx", IResolucion4505Constantes.CIE_CONTROL_PRENATAL);
			params.put(
					"codigo_cups",
					IResolucion4505Constantes.CUPS_CONTROL_56_PRENATAL_PRIMERA_VEZ);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_56", NO_SE_TIENE_REGISTRO);
			} else {
				Map<String, Object> map_respuesta_4505 = resultado_consulta_4505
						.get(0);
				Timestamp fecha_consulta = (Timestamp) map_respuesta_4505
						.get("fecha_consulta");
				if (fecha_consulta != null) {
					map_resultado.put("variable_56",
							formato_fecha.format(fecha_consulta));
					return true;
				} else {
					map_resultado.put("variable_56", NO_SE_TIENE_REGISTRO);
				}
			}
		} else {
			map_resultado.put("variable_56", NO_APLICA);
		}
		return false;
	}

	/**
	 * 57) Control Prenatal <br/>
	 * Registre el nÃºmero de controles que ha tenido en el Ãºltimo perÃ­odo de
	 * reporte durante la gestacion actual. <br/>
	 * Si no tiene el dato registrar 999 <br/>
	 * Si no aplica registrar 0 <br/>
	 * Registrar la cantidad de controles realizados de manera acumulativa desde
	 * el inicio del CPN al corte del reporte. Usar la opcion 0 en poblacion
	 * masculina y mujeres no gestantes. <br/>
	 * Validar que cuando la variable 57 registre un dato diferente a 0 la
	 * variable 10 corresponda a F. Validar que cuando la variable 57 registre
	 * un dato diferente a 0 la variable 14 corresponda a 1.<br/>
	 * <br/>
	 * 
	 * 
	 * 58) Ãšltimo Control Prenatal<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * Se registra la fecha del ultimo control dentro del corte para el que se
	 * genera la informacion a reportar. <br/>
	 * Usar la opcion 1845-01-01 en poblacion masculina y mujeres no gestantes.<br/>
	 * Validar que cuando la variable 58 registre un dato diferente a 1845-01-01
	 * la variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 58 registre un dato diferente a 1845-01-01
	 * la variable 14 corresponda a 1.<br/>
	 * */
	private static boolean consultarVariable57_58(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		String NO_APLICA = "1845-01-01";

		String NO_SE_TIENE_REGISTRO_57 = "999";
		String NO_APLICA_57 = "0";

		String variable_10_sexo = (String) map_resultado.get("variable_10");
		String variable_14_gestacion = (String) map_resultado
				.get("variable_14");

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)
				&& variable_14_gestacion.equals(RESPUESTA_VARIABLE_14_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("inyectar_cantidad", "inyectar_cantidad");
			params.put("codigo_cups",
					IResolucion4505Constantes.CUPS_CONTROL_PRENATAL);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_58", NO_SE_TIENE_REGISTRO);
				map_resultado.put("variable_57", NO_SE_TIENE_REGISTRO_57);
			} else {
				Map<String, Object> map_respuesta_4505 = resultado_consulta_4505
						.get(0);
				Timestamp fecha_consulta = (Timestamp) map_respuesta_4505
						.get("fecha_consulta");
				Long cantidad_controles = (Long) map_respuesta_4505
						.get("cantidad");
				if (fecha_consulta != null && cantidad_controles != null) {
					map_resultado.put("variable_58",
							formato_fecha.format(fecha_consulta));
					map_resultado.put("variable_57", cantidad_controles);
					return true;
				} else {
					map_resultado.put("variable_58", NO_SE_TIENE_REGISTRO);
					map_resultado.put("variable_57", NO_SE_TIENE_REGISTRO_57);
				}
			}
		} else {
			map_resultado.put("variable_58", NO_APLICA);
			map_resultado.put("variable_57", NO_APLICA_57);
		}
		return false;
	}

	/**
	 * Suministro de Ã�cido FÃ³lico en el Ãšltimo Control Prenatal<br/>
	 * 0- No aplica<br/>
	 * 1- Si se suministra<br/>
	 * 16- No se suministra por una Tradicion<br/>
	 * 17- No se suministra por una Condicion de Salud<br/>
	 * 18- No se suministra por Negacion de la usuaria<br/>
	 * 20- No se suministra por otras razones<br/>
	 * 21- Registro no Evaluado<br/>
	 * <br/>
	 * Registrar la entrega efectiva segÃºn corresponda al dato del Ãºltimo
	 * control prenatal dentro del corte para el que se genera la informacion a
	 * reportar. <br/>
	 * La opcion 0 se utiliza en aquellas usuarias que no reciben el Ã¡cido
	 * fÃ³lico mediante la atencion de control prenatal, en poblacion masculina y
	 * mujeres no gestantes. <br/>
	 * Se deberÃ¡ incluir el registro de los multivitamÃ­nicos NO POS. <br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2. <br/>
	 * Registrar la entrega efectiva segÃºn corresponda al dato del Ãºltimo
	 * control prenatal dentro del corte para el que se genera la informacion a
	 * reportar. <br/>
	 * La opcion 0 se utiliza en aquellas usuarias que no reciben el Ã¡cido
	 * fÃ³lico mediante la atencion de control prenatal, en poblacion masculina y
	 * mujeres no gestantes. <br/>
	 * Se deberÃ¡ incluir el registro de los multivitamÃ­nicos NO POS. <br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.
	 * */
	private static boolean consultarVariable59(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarMedicamentos(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_59",
				IResolucion4505Constantes.CODIGO_MEDICAMENTO_ACIDO_FOLICO);
	}

	/**
	 * Suministro de Sulfato Ferroso en el Ãšltimo Control Prenatal<br/>
	 * 0- No aplica<br/>
	 * 1- Si se suministra<br/>
	 * 16- No se suministra por una Tradicion<br/>
	 * 17- No se suministra por una Condicion de Salud<br/>
	 * 18- No se suministra por Negacion de la usuaria<br/>
	 * 20- No se suministra por otras razones<br/>
	 * 21- Registro no Evaluado<br/>
	 * <br/>
	 * Registrar la entrega efectiva segÃºn corresponda al dato del Ãºltimo
	 * control prenatal dentro del corte para el que se genera la informacion a
	 * reportar.<br/>
	 * La opcion 0 se utiliza en aquellas usuarias que no reciben el sulfato
	 * ferroso mediante la atencion de control prenatal, en poblacion masculina
	 * y mujeres no gestantes.<br/>
	 * Se deberÃ¡ incluir el registro de los multivitamÃ­nicos NO POS.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * <br/>
	 * Validar que cuando la variable 60 registre un dato diferente a 0 la
	 * variable 10 corresponda a F. Validar que cuando la variable 60 registre
	 * un dato diferente a 0 la variable 14 corresponda a 1.
	 * */
	private static boolean consultarVariable60(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarMedicamentos(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_60",
				IResolucion4505Constantes.CODIGO_MEDICAMENTO_SULFATO_FERROSO);
	}

	/**
	 * Suministro de Carbonato de Calcio en el Ãšltimo Control Prenatal<br/>
	 * 0- No aplica<br/>
	 * 1- Si se suministra<br/>
	 * 16- No se suministra por una Tradicion<br/>
	 * 17- No se suministra por una Condicion de Salud<br/>
	 * 18- No se suministra por Negacion de la usuaria<br/>
	 * 20- No se suministra por otras razones<br/>
	 * 21- Registro no Evaluado<br/>
	 * <br/>
	 * Registrar la entrega efectiva segÃºn corresponda al dato del Ãºltimo
	 * control prenatal dentro del corte para el que se genera la informacion a
	 * reportar.<br/>
	 * La opcion 0 se utiliza en aquellas usuarias que no reciben el carbonato
	 * de calcio mediante la atencion de control prenatal, en poblacion
	 * masculina y mujeres no gestantes.<br/>
	 * Se deberÃ¡ incluir el registro de los multivitamÃ­nicos NO POS.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * Validar que cuando la variable 61 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 61 registre un dato diferente a 0 la
	 * variable 14 corresponda a 1.<br/>
	 * */
	private static boolean consultarVariable61(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarMedicamentos(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_61",
				IResolucion4505Constantes.CODIGO_MEDICAMENTO_CARBONATO_CALCIO);
	}

	/**
	 * Este metodo trabaja de manera centralizada, para consultar los
	 * medicamentos entregados
	 * 
	 * @author Luis Miguel
	 * */
	private static boolean consultarMedicamentos(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String nombre_variable,
			String[] medicamentos) {
		String NO_APLICA = "0";
		String SI_SE_SUMINISTRA = "1";
		String REGISTRO_NO_EVALUADO = "21";

		String variable_10_sexo = (String) map_resultado.get("variable_10");
		String variable_14_gestacion = (String) map_resultado
				.get("variable_14");

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)
				&& variable_14_gestacion.equals(RESPUESTA_VARIABLE_14_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_medicamento", medicamentos);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_CONSULTAR_MEDICAMENTOS);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put(nombre_variable, REGISTRO_NO_EVALUADO);
			} else {
				map_resultado.put(nombre_variable, SI_SE_SUMINISTRA);
				return true;
			}
		} else {
			map_resultado.put(nombre_variable, NO_APLICA);
		}
		return false;
	}

	/**
	 * Valoracion de la Agudeza Visual<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Se registra como obligatorio los datos de los usuarios de 4.11.16 y 45
	 * aÃ±os y de manera opcional se puede registrar la informacion de los 
	 * usuarios.<br/>
	 * Usar la opcion 1845-01-01 en poblacion no objeto de esta actividad.<br/>
	 * */
	private static boolean consultarVariable62(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		return consultarFechaConsultaProcedimientoParametrizado(map_resultado,
				administradora, id_paciente, fecha_inicio, fecha_final,
				map_edad, "variable_62",
				IResolucion4505Constantes.CUPS_AGUDEZA_VISUAL,
				IResolucion4505Constantes.VARIABLE_CONSULTAR_PROCEDIMIENTO,
				"fecha_procedimiento");
	}

	/**
	 * Consulta por OftalmologÃ­a<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * <br/>
	 * Se registra como obligatorio los datos en los quinquenios a partir de los
	 * 55 aÃ±os y de manera opcional se puede registrar la informacion de los
	 * demÃ¡s usuarios. Usar la opcion 1845-01-01 en poblacion no objeto de esta
	 * actividad.
	 * */
	private static boolean consultarVariable63(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		return consultarFechaConsultaProcedimientoParametrizado(map_resultado,
				administradora, id_paciente, fecha_inicio, fecha_final,
				map_edad, "variable_63",
				IResolucion4505Constantes.CONSULTA_OFTALMOLOGIA,
				IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS,
				"fecha_consulta");
	}

	private static boolean consultarFechaConsultaProcedimientoParametrizado(
			Map map_resultado, Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			Map<String, Integer> map_edad, String nombre_variable,
			String[] coigo_cups, String sql_consulta,
			String nombre_fecha_resultado) {
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

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML, sql_consulta);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			int anios = (Integer) map_edad.get("anios");
			if (anios == 4
					|| anios == 11
					|| anios == 16
					|| (anios >= 45 
					&& ((anios / 5d) + "").endsWith(".0") 
					&& anios <= 80)) {
				map_resultado.put(nombre_variable, NO_SE_TIENE_REGISTRO);
			} else {
				map_resultado.put(nombre_variable, NO_APLICA);
			}
		} else {
			Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
					.get(0);
			Timestamp fecha_procedimiento = (Timestamp) map
					.get(nombre_fecha_resultado);
			if (fecha_procedimiento != null) {
				map_resultado.put(nombre_variable,
						formato_fecha.format(fecha_procedimiento));
				return true;
			} else {
				map_resultado.put(nombre_variable, NO_SE_TIENE_REGISTRO);
			}
		}
		return false;
	}

	/**
	 * Fecha DiagnÃ³stico Desnutricion Proteico CalÃ³rica<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * Usar la opcion 1845-01-01 en aquella poblacion que no tiene este
	 * diagnÃ³stico. <br/>
	 * Validar que cuando la variable 64 registre un valor diferente a
	 * 1845-01-01 la variable 21 corresponda a 2.<br/>
	 * */
	private static boolean consultarVariable64(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO_TIENE_DATO = "1800-01-01";
		String NO_APLICA = "1845-01-01";

		String SI_DESNUTRICION_VARIABLE_21 = "2";

		String resultado_variable_21 = (String) map_resultado
				.get("variable_21");

		if (resultado_variable_21.equals(SI_DESNUTRICION_VARIABLE_21)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put(
					"diagnosticos",
					IResolucion4505Constantes.CIE_DIAGNOSTICO_DESNUTRICION_PROTEICO_CALORICA);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_64", NO_TIENE_DATO);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Boolean aplica = (Boolean) map
						.get(NOMBRE_RESPUESTA_CONSULTA_DX);
				Timestamp fecha_dx = (Timestamp) map
						.get(FECHA_RESPUESTA_CONSULTA_DX);
				map_resultado
						.put("variable_64",
								aplica ? formato_fecha.format(fecha_dx)
										: NO_TIENE_DATO);
				return aplica;
			}
		} else {
			map_resultado.put("variable_64", NO_APLICA);
		}
		return false;
	}

	/**
	 * Consulta Mujer o Menor VÃ­ctima del Maltrato<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * <br/>
	 * Registrar el dato que se obtenga de aquellas personas en las que se
	 * identifica el maltrato o aquellas que son remitidas del servicio de
	 * urgencias marcadas con el riesgo. <br/>
	 * Usar la opcion 1845-01-01 en poblacion que no se identificÃ³ este riesgo. <br/>
	 * <br/>
	 * Validar que cuando la variable 65 registre un valor diferente a
	 * 1845-01-01 la variable 22 corresponda a 1 o 2.
	 * */
	private static boolean consultarVariable65(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
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

		String variable_22 = (String) map_resultado.get("variable_22");

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
			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_65", NO_SE_TIENE_REGISTRO);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Boolean aplica = (Boolean) map
						.get(NOMBRE_RESPUESTA_CONSULTA_DX);
				Timestamp fecha_dx = (Timestamp) map
						.get(FECHA_RESPUESTA_CONSULTA_DX);
				map_resultado.put("variable_65",
						aplica ? formato_fecha.format(fecha_dx)
								: NO_SE_TIENE_REGISTRO);
				return aplica;
			}

		} else {
			map_resultado.put("variable_65", NO_APLICA);
		}
		return false;
	}

	/**
	 * Consulta VÃ­ctimas de Violencia Sexual <br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Registrar el dato que se obtenga de aquellas personas en las que se
	 * identifica violencia sexual o aquellas que son remitidas del servicio de
	 * urgencias marcadas con el riesgo.<br/>
	 * Usar la opcion 1845-01-01 en poblacion que no se identificÃ³ este riesgo.<br/>
	 * Validar que cuando la variable 66 registre un valor diferente a
	 * 1845-01-01 la variable 23 corresponda a 1.
	 * */
	private static boolean consultarVariable66(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		String NO_APLICA = "1845-01-01";

		// respuesta de la variable 23
		String SI_23 = "1";

		String variable_23 = (String) map_resultado.get("variable_23");

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
			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService().getReport(params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_DX);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_66", NO_SE_TIENE_REGISTRO);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Boolean aplica = (Boolean) map
						.get(NOMBRE_RESPUESTA_CONSULTA_DX);
				Timestamp fecha_dx = (Timestamp) map
						.get(FECHA_RESPUESTA_CONSULTA_DX);
				map_resultado.put("variable_66",
						aplica ? formato_fecha.format(fecha_dx)
								: NO_SE_TIENE_REGISTRO);
				return aplica;
			}

		} else {
			map_resultado.put("variable_66", NO_APLICA);
		}
		return false;
	}

	/**
	 * Consulta Nutricion<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
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
	private static boolean consultarVariable67(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarVariableConsulta(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_67",
				IResolucion4505Constantes.CONSULTA_NUTRICION);
	}

	/**
	 * Consulta de PsicologÃ­a<br/>
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
	private static boolean consultarVariable68(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarVariableConsulta(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_68",
				IResolucion4505Constantes.CONSULTA_PSICOLOGIA);
	}

	/**
	 * Metodo centralizado para variables que tienen el mismo comportamiento
	 * */
	public static boolean consultarVariableConsulta(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String nombre_variable,
			String[] codigo_cups) {
		String NO_SE_TIENE_REGISTRO = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		// String NO_APLICA = "1845-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups", codigo_cups);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService()
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put(nombre_variable, NO_SE_TIENE_REGISTRO);
		} else {
			Map<String, Object> mapa_respuesta = resultado_consulta_4505.get(0);
			Timestamp fecha_consulta = (Timestamp) mapa_respuesta
					.get("fecha_consulta");
			map_resultado.put(
					nombre_variable,
					fecha_consulta != null ? formato_fecha
							.format(fecha_consulta) : NO_SE_TIENE_REGISTRO);
			return fecha_consulta != null;
		}
		return false;
	}

	/**
	 * Consulta de Crecimiento y Desarrollo Primera vez<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * El dato se registra como lo reporte la IPS, en los casos donde la IPS
	 * atiende al usuario en la transferencia por ser proveedor Ãºnico en el
	 * municipio. <br/>
	 * Usar la opcion 1845-01-01 en poblacion no objeto de esta actividad. <br/>
	 * Validar que cuando la variable 69 registre un valor diferente a
	 * 1845-01-01 el cÃ¡lculo de la edad* debe ser menor a 10 aÃ±os.
	 * */
	private static boolean consultarVariable69(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
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
		params.put("codigo_cups",
				IResolucion4505Constantes.CONSULTA_CREACIMIENTO_DESARROLLO);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService()
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);
		String variable_9_fech_nacimiento = (String) map_resultado
				.get("variable_9"); // fecha nacimiento
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(java.sql.Date
					.valueOf(variable_9_fech_nacimiento));
			int anios = (Integer) map_edad.get("anios");
			if (anios < 10) {
				map_resultado.put("variable_69", NO_SE_TIENE_REGISTRO);
			} else {
				map_resultado.put("variable_69", NO_APLICA);
			}
		} else {
			Map<String, Object> mapa_respuesta = resultado_consulta_4505.get(0);
			Timestamp fecha_consulta = (Timestamp) mapa_respuesta
					.get("fecha_consulta");

			Timestamp fecha = fecha_consulta != null ? fecha_consulta
					: new Timestamp(System.currentTimeMillis());

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento), fecha);
			int anios = (Integer) map_edad.get("anios");

			if (anios < 10) {
				if (fecha_consulta != null) {
					map_resultado.put(
							"variable_69",
							fecha_consulta != null ? formato_fecha
									.format(fecha_consulta)
									: NO_SE_TIENE_REGISTRO);
					return fecha_consulta != null;
				} else {
					map_resultado.put("variable_69", NO_SE_TIENE_REGISTRO);
				}
			} else {
				map_resultado.put("variable_69", NO_APLICA);
			}
		}
		return false;
	}

	/**
	 * Suministro de Sulfato Ferroso en la Ãšltima Consulta del Menor de 10 aÃ±os<br/>
	 * 0- No aplica<br/>
	 * 1- Si se suministra<br/>
	 * 16- No se suministra por una Tradicion<br/>
	 * 17- No se suministra por una Condicion de Salud<br/>
	 * 18- No se suministra por Negacion de la usuario<br/>
	 * 20- No se suministra por otras razones<br/>
	 * 21- Registro no Evaluado<br/>
	 * <br/>
	 * Registrar la entrega efectiva derivada de cualquier atencion al menor
	 * segÃºn corresponda al dato dentro del corte para el que se genera la
	 * informacion a reportar. <br/>
	 * Usar la opcion 0 en poblacion mayor de 10 aÃ±os.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2. <br/>
	 * Validar que cuando la variable 70 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser menor a 10 aÃ±os.
	 * */
	private static boolean consultarVariable70(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarSuministratoMedicamentoMenor10(map_resultado, administradora,
				id_paciente, fecha_inicio, fecha_final, "variable_70",
				IResolucion4505Constantes.CODIGO_MEDICAMENTO_SULFATO_FERROSO);
	}

	/**
	 * Suministro de Vitamina A en la Ãšltima Consulta del Menor de 10 aÃ±os<br/>
	 * 0- No aplica<br/>
	 * 1- Si se suministra<br/>
	 * 16- No se suministra por una Tradicion<br/>
	 * 17- No se suministra por una Condicion de Salud<br/>
	 * 18- No se suministra por Negacion de la usuario<br/>
	 * 20- No se suministra por otras razones<br/>
	 * 21- Registro no Evaluado<br/>
	 * <br/>
	 * Registrar la entrega efectiva derivada de cualquier atencion al menor
	 * segÃºn corresponda al dato dentro del corte para el que se genera la
	 * informacion a reportar.<br/>
	 * Usar la opcion 0 en poblacion mayor de 10 aÃ±os.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * Validar que cuando la variable 71 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser menor a 10 aÃ±os.
	 * */
	private static boolean consultarVariable71(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarSuministratoMedicamentoMenor10(map_resultado, administradora,
				id_paciente, fecha_inicio, fecha_final, "variable_71",
				IResolucion4505Constantes.CODIGO_MEDICAMENTO_VITAMINA_A);
	}

	public static boolean consultarSuministratoMedicamentoMenor10(
			Map map_resultado, Administradora administradora,
			String id_paciente, Date fecha_inicio, Date fecha_final,
			String nombre_variable, String[] medicamentos) {
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

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService()
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTAR_MEDICAMENTOS);

		String variable_9_fech_nacimiento = (String) map_resultado
				.get("variable_9"); // fecha nacimiento

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(java.sql.Date
					.valueOf(variable_9_fech_nacimiento));
			int anios = (Integer) map_edad.get("anios");
			if (anios < 10) {
				map_resultado.put(nombre_variable, REGISTRO_NO_EVALUADO);
			} else {
				map_resultado.put(nombre_variable, NO_APLICA);
			}
		} else {
			Map<String, Object> map = (Map<String, Object>) resultado_consulta_4505
					.get(0);
			Timestamp fecha_medicamento = (Timestamp) map
					.get("fecha_medicamento");

			Timestamp fecha = fecha_medicamento != null ? fecha_medicamento
					: new Timestamp(System.currentTimeMillis());

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento), fecha);
			int anios = (Integer) map_edad.get("anios");
			if (anios < 10) {
				if (fecha_medicamento != null) {
					map_resultado.put(nombre_variable, SI_SE_SUMINISTRA);
					return true;
				} else {
					map_resultado.put(nombre_variable, REGISTRO_NO_EVALUADO);
				}
			} else {
				map_resultado.put(nombre_variable, NO_APLICA);
			}
		}
		return false;
	}

	/**
	 * Consulta de Joven Primera vez<br/>
	 * AAAA-MM-DD <br/>
	 * <br/>
	 * Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * El dato se registra como lo reporte la IPS, en los casos donde la IPS
	 * atiende al usuario en la transferencia por ser proveedor Ãºnico en el
	 * municipio.<br/>
	 * Usar la opcion 1845-01-01 en poblacion no objeto de esta actividad.<br/>
	 * */
	private static boolean consultarVariable72(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
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
		params.put("codigo_cups",
				IResolucion4505Constantes.CONSULTA_JOVEN_PRIMERA_VEZ);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService()
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);
		String variable_9_fech_nacimiento = (String) map_resultado
				.get("variable_9"); // fecha nacimiento
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(java.sql.Date
					.valueOf(variable_9_fech_nacimiento));
			int anios = (Integer) map_edad.get("anios");
			if (anios >= 10 && anios <= 29) {
				map_resultado.put("variable_72", NO_SE_TIENE_REGISTRO);
			} else {
				map_resultado.put("variable_72", NO_APLICA);
			}
		} else {
			Map<String, Object> mapa_respuesta = resultado_consulta_4505.get(0);
			Timestamp fecha_consulta = (Timestamp) mapa_respuesta
					.get("fecha_consulta");

			Timestamp fecha = fecha_consulta != null ? fecha_consulta
					: new Timestamp(System.currentTimeMillis());

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento), fecha);
			int anios = (Integer) map_edad.get("anios");

			if (anios >= 10 && anios <= 29) {
				if (fecha_consulta != null) {
					map_resultado.put(
							"variable_72",
							fecha_consulta != null ? formato_fecha
									.format(fecha_consulta)
									: NO_SE_TIENE_REGISTRO);
					return fecha_consulta != null;
				} else {
					map_resultado.put("variable_72", NO_SE_TIENE_REGISTRO);
				}
			} else {
				map_resultado.put("variable_72", NO_APLICA);
			}
		}
		return false;
	}

	/**
	 * Consulta de Adulto Primera vez<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * El dato se registra como lo reporte la IPS, en los casos donde la IPS
	 * atiende al usuario en la transferencia por ser proveedor Ãºnico en el
	 * municipio.<br/>
	 * Usar la opcion 1845-01-01 en poblacion no objeto de esta actividad.<br/>
	 * */
	private static boolean consultarVariable73(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
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
		params.put("codigo_cups",
				IResolucion4505Constantes.CONSULTA_ADULTO_PRIEMERA_VEZ);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService()
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTA_CONSULTAS_CUPS);
		String variable_9_fech_nacimiento = (String) map_resultado
				.get("variable_9"); // fecha nacimiento
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(java.sql.Date
					.valueOf(variable_9_fech_nacimiento));
			int anios = (Integer) map_edad.get("anios");
			if (anios >= 45 && ((anios / 5d) + "").endsWith(".0")
					&& anios <= 80) {
				map_resultado.put("variable_73", NO_SE_TIENE_REGISTRO);
			} else {
				map_resultado.put("variable_73", NO_APLICA);
			}
		} else {
			Map<String, Object> mapa_respuesta = resultado_consulta_4505.get(0);
			Timestamp fecha_consulta = (Timestamp) mapa_respuesta
					.get("fecha_consulta");

			Timestamp fecha = fecha_consulta != null ? fecha_consulta
					: new Timestamp(System.currentTimeMillis());

			Map<String, Integer> map_edad = Util.getEdadYYYYMMDD(
					java.sql.Date.valueOf(variable_9_fech_nacimiento), fecha);
			int anios = (Integer) map_edad.get("anios");

			if (anios >= 45 && ((anios / 5d) + "").endsWith(".0")
					&& anios <= 80) {
				if (fecha_consulta != null) {
					map_resultado.put(
							"variable_73",
							fecha_consulta != null ? formato_fecha
									.format(fecha_consulta)
									: NO_SE_TIENE_REGISTRO);
					return fecha_consulta != null;
				} else {
					map_resultado.put("variable_73", NO_SE_TIENE_REGISTRO);
				}
			} else {
				map_resultado.put("variable_73", NO_APLICA);
			}
		}
		return false;
	}

	/**
	 * Preservativos entregados a pacientes con ITS<br/>
	 * Registre el nÃºmero de Preservativos entregados durante el perÃ­odo de
	 * reporte.<br/>
	 * Si no se entrega por otras razones registrar 993<br/>
	 * Si no se entrega por tener datos de contacto del usuario no actualizados
	 * registrar 994<br/>
	 * Si no se entrega por Negacion del usuario registrar 995<br/>
	 * Si no se entrega por una Condicion de Salud registrar 996<br/>
	 * Si no se entrega por una Tradicion registrar 997<br/>
	 * Si no aplica registrar 0<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * <br/>
	 * Aplica para las personas con diagnÃ³stico de ITS y VIH/SIDA, el nÃºmero de
	 * preservativos total efectivamente entregado dentro del periodo de corte a
	 * reportar.<br/>
	 * Usar la opcion 0 en poblacion que no tienen diagnÃ³stico de ITS (incluir
	 * VIH/SIDA).<br/>
	 * TODO Pendiente por verificar informacion
	 * */
	private static boolean consultarVariable74(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		 String SIN_DATO = "999";
//		String NO_APLICA = "0";
		map_resultado.put("variable_74", SIN_DATO);
		return false;
	}

	/**
	 * AsesorÃ­a Pre test Elisa para VIH<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Aplica para la poblacion general Usar la opcion 1845-01-01 para la
	 * poblacion que no tiene criterios en los que aplique su realizacion.
	 * */
	private static boolean consultarVariable75(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String SIN_DATO = "1800-01-01";
//		String NO_APLICA = "1845-01-01";
		map_resultado.put("variable_75", SIN_DATO);
		return false;
	}

	/**
	 * AsesorÃ­a Pos test Elisa para VIH<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Aplica para la poblacion general Usar la opcion 1845-01-01 para la
	 * poblacion que no tiene criterios en los que aplique su realizacion.<br/>
	 * */
	private static boolean consultarVariable76(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String SIN_DATO = "1800-01-01";
//		String NO_APLICA = "1845-01-01";
		map_resultado.put("variable_76", SIN_DATO);
		return false;
	}

	/**
	 * 0- No aplica 1- En proceso de atencion. 2- Si recibiÃ³ atencion por equipo
	 * interdisciplinario completo. Paciente con 16- No recibiÃ³ atencion por
	 * DiagnÃ³stico de: tener una tradicion que se lo Ansiedad, Depresion, impide
	 * Esquizofrenia, dÃ©ficit 17- No recibiÃ³ atencion por una de atencion,
	 * consumo condicion de salud SPA y Bipolaridad 18- No recibiÃ³ atencion por
	 * recibiÃ³ Atencion en negacion del usuario los Ãºltimos 6 meses 19- No
	 * recibiÃ³ atencion porque por Equipo los datos de contacto del
	 * Interdisciplinario usuario no se encuentran Completo actualizados 20- No
	 * recibiÃ³ atencion por otras razones 22- Sin dato<br/>
	 * Usar la opcion 0 cuando:<br/>
	 * a. No tiene un diagnostico de enfermedad mental. <br/>
	 * b. Se tenga diagnostico de enfermedad mental pero no se encuentra en el
	 * listado de diagnÃ³sticos priorizados para su seguimiento. <br/>
	 * La opcion 1 cuando esta recibiendo atencion pero no ha completado la
	 * valoracion por el equipo interdisciplinario completo (EnfermerÃ­a,
	 * PsicologÃ­a, PsiquiatrÃ­a y Trabajo Social). <br/>
	 * Por estandarizacion y revision de opciones se ajustan los valores
	 * permitidos, se modifica la longitud del campo a 2.<br/>
	 * Validar que cuando la variable 77 registre un dato diferente a 0 la
	 * variable 25 corresponda a 1, 2, 3, 4, 5 o 6.
	 * */
	private static boolean consultarVariable77(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO_APLICA = "0";
//		String EN_PROCESO = "1";
		String ATENCION_EQUIPO_INTERDISCIPLINARIO_COMPLETO = "2";
		String SIN_DATO = "22";

		String RIESGO_NO_EVALUADO_25 = "21";

		String variable_25 = map_resultado.get("variable_25") + "";
		if (variable_25.equals(RIESGO_NO_EVALUADO_25)) {
			map_resultado.put("variable_77", SIN_DATO);
		} else if (variable_25.equals(DIAGNOSTICO_ANSIEDAD_25)
				|| variable_25.equals(DIAGNOSTICO_DEFICIT_HIPERACTIVIDAD_25)
				|| variable_25.equals(DIAGNOSTICO_DEPRESION_25)
				|| variable_25.equals(DIAGNOSTICO_ESQUIZOFRENIA_25)
				|| variable_25.equals(DIAGNOSTICO_SUSTANCIAS_PSICOACTIVAS_25)
				|| variable_25.equals(DIAGNOSTICO_TRASTORNO_BIPOLAR_25)) {
			map_resultado.put("variable_77",
					ATENCION_EQUIPO_INTERDISCIPLINARIO_COMPLETO);
			return true;
		} else {
			map_resultado.put("variable_77", NO_APLICA);
		}
		return false;
	}

	/**
	 * 78) Fecha AntÃ­geno de Superficie Hepatitis B en Gestantes<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * La opcion 1845-01-01 se usa en mujeres no gestantes y en sexo masculino <br/>
	 * Validar que cuando la variable 78 registre un dato diferente a 1845-01-01
	 * la variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 78 registre un dato diferente a 1845-01-01
	 * la variable 14 corresponda a 1.<br/>
	 * <br/>
	 * <br/>
	 * 
	 * 79) Resultado AntÃ­geno de Superficie Hepatitis B en Gestantes<br/>
	 * 0- No aplica <br/>
	 * 1- Negativo <br/>
	 * 2- Positivo <br/>
	 * 22- Sin dato <br/>
	 * <br/>
	 * La opcion 0 se usa en mujeres no gestantes y en sexo masculino Por
	 * estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2. <br/>
	 * Validar que cuando la variable 79 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 79 registre un dato diferente a 0 la
	 * variable 14 corresponda a 1.<br/>
	 * */
	private static boolean consultarVariable78_79(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO_SE_TIENE_REGISTRO_78 = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		String NO_APLICA_78 = "1845-01-01";

		String NO_APLICA_79 = "0";
		String NEGATIVO_79 = "1";
		String POSITIVO_79 = "2";
		String SIN_DATO_79 = "22";

		String variable_10 = (String) map_resultado.get("variable_10");
		String variable_14 = (String) map_resultado.get("variable_14");

		if (variable_10.equals(RESPUESTA_VARIABLE_10_FEMENINO)
				&& variable_14.equals(RESPUESTA_VARIABLE_14_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_HEPATITIS_B_GESTANTES);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_78", NO_SE_TIENE_REGISTRO_78);
				map_resultado.put("variable_79", SIN_DATO_79);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				String valor_resultado = (String) map.get("valor_resultado");

				boolean aplica_resultado = false;
				if (fecha_resultado != null) {
					map_resultado.put("variable_78",
							formato_fecha.format(fecha_resultado));
					aplica_resultado = true;
				} else {
					map_resultado.put("variable_78", NO_SE_TIENE_REGISTRO_78);
				}

				if (valor_resultado != null
						&& !valor_resultado.trim().isEmpty()) {
					map_resultado.put("variable_79", valor_resultado
							.toLowerCase().startsWith("pos") ? POSITIVO_79
							: NEGATIVO_79);
					aplica_resultado = true;
				} else {
					map_resultado.put("variable_79", SIN_DATO_79);
				}
				return aplica_resultado;
			}
		} else {
			map_resultado.put("variable_78", NO_APLICA_78);
			map_resultado.put("variable_79", NO_APLICA_79);
		}
		return false;
	}

	/**
	 * 80) Fecha SerologÃ­a para SÃ­filis<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Aplica para la poblacion general.<br/>
	 * La opcion 1845-01-01 para la poblacion que no tiene criterios en los que
	 * aplique su realizacion.<br/>
	 * <br/>
	 * 
	 * 81) Resultado SerologÃ­a para SÃ­filis<br/>
	 * 0- No aplica <br/>
	 * 1- No Reactiva <br/>
	 * 2- Reactiva <br/>
	 * 22- Sin dato <br/>
	 * Aplica para la poblacion general. La opcion 0 para la poblacion que no
	 * tiene criterios en los que aplique su realizacion.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * */
	private static boolean consultarVariable80_81(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {

		String NO_SE_TIENE_REGISTRO_80 = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		// String NO_APLICA_80 = "1845-01-01";
		//
		// String NO_APLICA_81 = "0";
		String NO_REACTIVA_81 = "1";
		String REACTIVA_81 = "2";
		String SIN_DATO_81 = "22";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_SEROLOGIA_PARA_SIFILIS);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put("variable_80", NO_SE_TIENE_REGISTRO_80);
			map_resultado.put("variable_81", SIN_DATO_81);
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			String valor_resultado = (String) map.get("valor_resultado");

			boolean aplica_resultado = false;
			if (fecha_resultado != null) {
				map_resultado.put("variable_80",
						formato_fecha.format(fecha_resultado));
				aplica_resultado = true;
			} else {
				map_resultado.put("variable_80", NO_SE_TIENE_REGISTRO_80);
			}

			if (valor_resultado != null && !valor_resultado.trim().isEmpty()) {
				map_resultado.put("variable_81", valor_resultado.toLowerCase()
						.startsWith("rea") ? REACTIVA_81 : NO_REACTIVA_81);
				aplica_resultado = true;
			} else {
				map_resultado.put("variable_81", SIN_DATO_81);
			}
			
			return aplica_resultado;
		}
		return false;
	}

	/**
	 * 82) Fecha de Toma de Elisa para VIH<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Aplica para la poblacion general.<br/>
	 * La opcion 1845-01-01 para la poblacion que no tiene criterios en los que
	 * aplique su realizacion.<br/>
	 * <br/>
	 * 
	 * 83) Resultado Elisa para VIH<br/>
	 * 0- No aplica<br/>
	 * 1- Negativo<br/>
	 * 2- Positivo<br/>
	 * 22- Sin dato<br/>
	 * Aplica para la poblacion general. La opcion 0 para la poblacion que no
	 * tiene criterios en los que aplique su realizacion.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * */
	private static boolean consultarVariable82_83(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NO_SE_TIENE_REGISTRO_82 = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		// String NO_APLICA_82 = "1845-01-01";
		//
		// String NO_APLICA_83 = "0";
		String NEGATIVO_83 = "1";
		String POSITIVO_83 = "2";
		String SIN_DATO_83 = "22";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_ELISA_VIH);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);
		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put("variable_82", NO_SE_TIENE_REGISTRO_82);
			map_resultado.put("variable_83", SIN_DATO_83);			
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			String valor_resultado = (String) map.get("valor_resultado");

			boolean aplica_resultado = false;
			if (fecha_resultado != null) {
				map_resultado.put("variable_82",
						formato_fecha.format(fecha_resultado));
				aplica_resultado = true;
			} else {
				map_resultado.put("variable_82", NO_SE_TIENE_REGISTRO_82);
			}

			if (valor_resultado != null && !valor_resultado.trim().isEmpty()) {
				map_resultado.put("variable_83", valor_resultado.toLowerCase()
						.startsWith("neg") ? NEGATIVO_83 : POSITIVO_83);
				aplica_resultado = true;
			} else {
				map_resultado.put("variable_83", SIN_DATO_83);
			}
			return aplica_resultado;
		}
		return false;
	}

	/**
	 * 84) Fecha TSH Neonatal<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Registrar el dato de la fecha de toma que debe estar dentro de los 3
	 * primeros dÃ­as de nacido.<br/>
	 * La opcion 1845-01-01 se usa para usuarios no RN. <br/>
	 * Validar que cuando la variable 84 registre un dato diferente a 1845-01-01
	 * el cÃ¡lculo de la edad* debe ser menor a 2 dÃ­as.<br/>
	 * 
	 * 85) Resultado de TSH Neonatal<br/>
	 * 0- No aplica <br/>
	 * 1- Normal <br/>
	 * 2- Anormal <br/>
	 * 22- Sin dato <br/>
	 * <br/>
	 * La opcion 0 se usa para usuarios no RN.<br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 2.<br/>
	 * */
	private static boolean consultarVariable84_85(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		String NO_SE_TIENE_REGISTRO_84 = "1800-01-01";
		// String NO_SE_REALIZA_POR_TENER_TRADICION = "1805-01-01";
		// String NO_SE_REALIZA_POR_UNA_CONDICION = "1810-01-01";
		// String NO_SE_REALIZA_POR_UNA_NEGACION = "1825-01-01";
		// String NO_SE_REALIZA_POR_UNA_NO_TENER_DATOS_ACTUALIZADOS =
		// "1830-01-01";
		// String NO_SE_REALIZA_POR_OTRAS_RAZONES = "1835-01-01";
		String NO_APLICA_84 = "1845-01-01";

		String NO_APLICA_85 = "0";
		String NORMAL_85 = "1";
		String ANORMAL_85 = "2";
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

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			int dias = (Integer) map_edad.get("dias");
			if (dias < 2) {
				map_resultado.put("variable_84", NO_SE_TIENE_REGISTRO_84);
				map_resultado.put("variable_85", SIN_DATO_85);
			} else {
				map_resultado.put("variable_84", NO_APLICA_84);
				map_resultado.put("variable_85", NO_APLICA_85);
			}
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			String valor_resultado = (String) map.get("valor_resultado");
			boolean aplica_resultado = false;
			if (fecha_resultado != null) {
				map_resultado.put("variable_84",
						formato_fecha.format(fecha_resultado));
				aplica_resultado = true;
			} else {
				map_resultado.put("variable_84", NO_SE_TIENE_REGISTRO_84);
			}
			if (valor_resultado != null && !valor_resultado.trim().isEmpty()) {
				map_resultado.put("variable_85", valor_resultado.toLowerCase()
						.startsWith("nor") ? NORMAL_85 : ANORMAL_85);
				aplica_resultado = true;
			} else {
				map_resultado.put("variable_85", SIN_DATO_85);
			}
			return aplica_resultado;
		}
		return false;
	}

	/**
	 * Tamizaje CÃ¡ncer de Cuello Uterino<br/>
	 * 0- No aplica<br/>
	 * 1- CitologÃ­a cervico uterina<br/>
	 * 2- ADN â€“ VPH<br/>
	 * 3- TÃ©cnica de inspeccion Visual<br/>
	 * 16- No se realiza por una Tradicion<br/>
	 * 17- No se realiza por una Condicion de Salud<br/>
	 * 18- No se realiza por Negacion de la usuaria<br/>
	 * 19- No se realiza por tener datos de contacto de la usuaria no
	 * actualizados<br/>
	 * 20- No se realiza por otras razones<br/>
	 * 22- Sin dato<br/>
	 * <br/>
	 * La opcion 0 se usa en caso de personas de sexo masculino.<br/>
	 * Por estandarizacion se ajustan los valores permitidos.<br/>
	 * La opcion 0 se usa en caso de personas de sexo masculino.<br/>
	 * Por estandarizacion se ajustan los valores permitidos.<br/>
	 * Validar que cuando la variable 86 registre un dato diferente a 0 la
	 * variable 10 corresponda a F. Validar que cuando la variable 86 registre
	 * un valor diferente a 0 el cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os.
	 * TODO Pendiente verficar el codigo del tamizaje
	 * */
	private static boolean consultarVariable86(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {

		String NO_APLICA = "0";
		String CITOLOGIA_CERVICO_UTERINA = "1";
		String ADN = "2";
		String TECNICA_INSPECCION_VISUAL = "3";
		String SIN_DATO = "22";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put(
					"codigo_cups",
					IResolucion4505Constantes.LABORATORIO_TAMIZAJE_CANCER_CUELLO_UTERINO);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				int anios = (Integer) map_edad.get("anios");
				if (anios > 10) {
					map_resultado.put("variable_86", SIN_DATO);
				} else {
					map_resultado.put("variable_86", NO_APLICA);
				}
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				String respuesta_4505 = (String) map.get("codigo_respuesta");
				if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()) {
					if (respuesta_4505.equals(CITOLOGIA_CERVICO_UTERINA)
							|| respuesta_4505.equals(ADN)
							|| respuesta_4505.equals(TECNICA_INSPECCION_VISUAL)) {
						map_resultado.put("variable_86", respuesta_4505);
						return true;
					} else {
						map_resultado.put("variable_86", SIN_DATO);
					}
				} else {
					map_resultado.put("variable_86", SIN_DATO);
				}
			}
		} else {
			map_resultado.put("variable_86", NO_APLICA);
		}
		return false;
	}

	/**
	 * CitologÃ­a Cervico uterina<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * La opcion 1845-01-01 se usa en caso de personas de sexo masculino. <br/>
	 * Validar que cuando la variable 87 registre un dato diferente a 1845-01-01
	 * la variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 87 registre un valor diferente a
	 * 1845-01-01 el cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os.<br/>
	 * */
	private static boolean consultarVariable87(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {

		String NO_APLICA = "1845-01-01";
		String SIN_DATO = "1800-01-01";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put(
					"codigo_cups",
					IResolucion4505Constantes.LABORATORIO_CITOLOGIA_CERVICO_UTERINA);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				int anios = (Integer) map_edad.get("anios");
				if (anios > 10) {
					map_resultado.put("variable_87", SIN_DATO);
				} else {
					map_resultado.put("variable_87", NO_APLICA);
				}
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				if (fecha_resultado != null) {
					map_resultado.put("variable_87",
							formato_fecha.format(fecha_resultado));
					return true;
				} else {
					map_resultado.put("variable_87", SIN_DATO);
				}
			}
		} else {
			map_resultado.put("variable_87", NO_APLICA);
		}
		return false;
	}

	/**
	 * CitologÃ­a Cervico uterina Resultados segÃºn Bethesda<br/>
	 * Escamosas:<br/>
	 * 1- ASC-US (cÃ©lulas escamosas atÃ­picas de significado indeterminado)<br/>
	 * 2- ASC-H (cÃ©lulas escamosas atÃ­picas, de significado indeterminado
	 * sugestivo de LEI de alto grado)<br/>
	 * 3- Lesion intraepitelial escamosa (LEI) de bajo grado- HPV (NIC I) (LEI
	 * BG)<br/>
	 * 4- Lesion intraepitelial escamosa (LEI) de alto grado (NIC II-III CA
	 * INSITU) (LEI AG)<br/>
	 * 5- Lesion intraepitelial escamosa de alto grado sospechosa de
	 * infiltracion.<br/>
	 * 6- Carcinoma de cÃ©lulas escamosas (escamocelular) Glandulares:<br/>
	 * 7- CÃ©lulas endocervicales atÃ­picas sin ningÃºn otro significado.<br/>
	 * 8- CÃ©lulas endometriales atÃ­picas sin ningÃºn otro significado.<br/>
	 * 9- CÃ©lulas glandulares atÃ­picas sin ningÃºn otro significado.<br/>
	 * 10- CÃ©lulas endocervicales atÃ­picas sospechosas de neoplasia.<br/>
	 * 11- CÃ©lulas endometriales atÃ­picas sospechosas de neoplasia.<br/>
	 * 12- CÃ©lulas glandulares atÃ­picas sospechosas de neoplasia.<br/>
	 * 13- Adenocarcinoma endocervical in situ.<br/>
	 * 14- Adenocarcinoma endocervical.<br/>
	 * 15- Adenocarcinoma endometrial.<br/>
	 * 16- Otras neoplasias<br/>
	 * 17- Negativa para lesion intraepitelial o neoplasia.<br/>
	 * 18- Inadecuada para lectura.<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * Si no aplica registrar 0<br/>
	 * <br/>
	 * La opcion 0 se usa en caso de personas de sexo masculino. <br/>
	 * En el caso en que el reporte de la citologÃ­a determine una lesion de
	 * cÃ©lulas escamosas y otra de cÃ©lulas glandulares, se deberÃ¡ realizar el
	 * reporte con el dato del tipo de lesion glandular. <br/>
	 * Por estandarizacion y revision de opciones se ajustan los valores
	 * permitidos, se modifica la longitud del campo a 3. <br/>
	 * <br/>
	 * Validar que cuando la variable 88 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 88 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os.<br/>
	 * */
	private static boolean consultarVariable88(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {

		String NO_APLICA = "0";
		String SIN_DATO = "999";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
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

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				int anios = (Integer) map_edad.get("anios");
				if (anios > 10) {
					map_resultado.put("variable_88", SIN_DATO);
				} else {
					map_resultado.put("variable_88", NO_APLICA);
				}
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				String respuesta_4505 = (String) map.get("codigo_respuesta");
				if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
						&& respuesta_4505.matches("[0-9]*$")) {
					int resultado_int = Integer.parseInt(respuesta_4505);
					if (resultado_int > 1 && resultado_int < 18) {
						map_resultado.put("variable_88", respuesta_4505);
						return true;
					} else {
						map_resultado.put("variable_88", SIN_DATO);
					}
				} else {
					map_resultado.put("variable_88", SIN_DATO);
				}
			}
		} else {
			map_resultado.put("variable_88", NO_APLICA);
		}
		return false;
	}

	/**
	 * Calidad en la Muestra de CitologÃ­a Cervicouterina <br/>
	 * 1- Satisfactoria Zona de Transformacion Presente. <br/>
	 * 2- Satisfactoria Zona de Transformacion Ausente<br/>
	 * 3- Insatisfactoria<br/>
	 * 4- Rechazada<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * Si no aplica registrar 0 <br/>
	 * <br/>
	 * La opcion 0 se usa en caso de personas de sexo masculino. <br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 3. <br/>
	 * 
	 * Validar que cuando la variable 89 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 89 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os.<br/>
	 * TODO falta completar la informacion
	 * */
	private static boolean consultarVariable89(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {

		String NO_APLICA = "0";
		String SIN_DATO = "999";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			map_resultado.put("variable_89", SIN_DATO);
		} else {
			map_resultado.put("variable_89", NO_APLICA);
		}
		return false;
	}

	/**
	 * CÃ³digo de habilitacion IPS donde se toma CitologÃ­a Cervicouterina Tabla
	 * REPS (Registro Especial de Prestadores de Servicios de Salud). Si no
	 * tiene el dato registrar 999 <br/>
	 * Si no aplica registrar 0 <br/>
	 * <br/>
	 * La opcion 0 se usa en caso de personas de sexo masculino La opcion 999 se
	 * utiliza: <br/>
	 * a. Cuando no se tiene el dato.<br/>
	 * b. Cuando el cÃ³digo de la IPS aÃºn no se encuentra actualizado en el REPS. <br/>
	 * Validar que cuando la variable 90 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 90 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os.<br/>
	 * TODO falta completar la informacion
	 * */
	private static boolean consultarVariable90(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {
		String NO_APLICA = "0";
		String SIN_DATO = "999";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			map_resultado.put("variable_90", SIN_DATO);
		} else {
			map_resultado.put("variable_90", NO_APLICA);
		}
		return false;
	}

	/**
	 * 91) Fecha Colposcopia<br/>
	 * AAAA-MM-DD <br/>
	 * Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * La opcion 1845-01-01 se usa en caso de personas de sexo masculino o en
	 * mujeres con citologÃ­a reportada como normal. <br/>
	 * Validar que cuando la variable 91 registre un dato diferente a 1845-01-01
	 * la variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 91 registre un valor diferente a
	 * 1845-01-01 el cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os.<br/>
	 * 
	 * 
	 * 92) CÃ³digo de habilitacion IPS donde se toma Colposcopia<br/>
	 * Tabla REPS (Registro Especial de Prestadores de Servicios de Salud).<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * Si no aplica registrar 0 <br/>
	 * La opcion 0 se usa en caso de personas de sexo masculino o en mujeres con
	 * citologÃ­a reportada como normal La opcion 999 se utiliza: <br/>
	 * a. Cuando no se tiene el dato.<br/>
	 * b. Cuando el cÃ³digo de la IPS aÃºn no se encuentra actualizado en el REPS. <br/>
	 * 
	 * Validar que cuando la variable 92 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 92 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os. <br/>
	 * */
	private static boolean consultarVariable91_92(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {

		String NO_APLICA_91 = "1845-01-01";
		String SIN_DATO_91 = "1800-01-01";

		String NO_APLICA_92 = "0";
		String SIN_DATO_92 = "999";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_CITOLOGIA_COLPOSCOPIA);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				int anios = (Integer) map_edad.get("anios");
				if (anios > 10) {
					map_resultado.put("variable_91", SIN_DATO_91);
					map_resultado.put("variable_92", SIN_DATO_92);
				} else {
					map_resultado.put("variable_91", NO_APLICA_91);
					map_resultado.put("variable_92", NO_APLICA_92);
				}
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				if (fecha_resultado != null) {
					map_resultado.put("variable_91",
							formato_fecha.format(fecha_resultado));
				} else {
					map_resultado.put("variable_91", SIN_DATO_91);
				}
				map_resultado.put("variable_92", SIN_DATO_92);
				return fecha_resultado != null;
			}
		} else {
			map_resultado.put("variable_91", NO_APLICA_91);
			map_resultado.put("variable_92", NO_APLICA_92);
		}
		return false;
	}

	/**
	 * 93) Fecha Biopsia Cervical<br/>
	 * AAAA-MM-DD Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * La opcion 1845-01-01 se usa en caso de personas de sexo masculino o en
	 * mujeres con citologÃ­a reportada como normal <br/>
	 * Validar que cuando la variable 93 registre un dato diferente a 1845-01-01
	 * la variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 93 registre un valor diferente a
	 * 1845-01-01 el cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os.<br/>
	 * 
	 * 
	 * 94) Resultado de Biopsia Cervical<br/>
	 * 1- Negativo para Neoplasia<br/>
	 * 2- Infeccion por VPH<br/>
	 * 3- NIC de Bajo Grado - NIC I<br/>
	 * 4- NIC de Alto Grado: NIC II - NIC III<br/>
	 * 5- Neoplasia Micro infiltrante: Escamocelular o Adenocarcinoma<br/>
	 * 6- Neoplasia Infiltrante: Escamocelular o Adenocarcinoma.<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * Si no aplica registrar 0<br/>
	 * <br/>
	 * La opcion 0 se usa en caso de personas de sexo masculino o en mujeres con
	 * citologÃ­a reportada como normal. <br/>
	 * Por estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 3. <br/>
	 * <br/>
	 * 
	 * Validar que cuando la variable 94 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 94 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os.<br/>
	 * 
	 * 95) CÃ³digo de habilitacion IPS donde se toma Biopsia Cervical<br/>
	 * Tabla REPS (Registro Especial de Prestadores de Servicios de Salud).<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * Si no aplica registrar 0<br/>
	 * <br/>
	 * La opcion 0 se usa en caso de personas de sexo masculino o en mujeres con
	 * citologÃ­a reportada como normal La opcion 999 se utiliza: <br/>
	 * a. Cuando no se tiene el dato.<br/>
	 * b. Cuando el cÃ³digo de la IPS aÃºn no se encuentra actualizado en el REPS. <br/>
	 * <br/>
	 * 
	 * Validar que cuando la variable 95 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 95 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser mayor a 10 aÃ±os.<br/>
	 * */
	private static boolean consultarVariable93_95(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {

		String NO_APLICA_93 = "1845-01-01";
		String SIN_DATO_93 = "1800-01-01";

		String NO_APLICA_94 = "0";
		String SIN_DATO_94 = "999";

		String NO_APLICA_95 = "0";
		String SIN_DATO_95 = "999";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_BIOPSIA_CERVICAL);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				int anios = (Integer) map_edad.get("anios");
				if (anios > 10) {
					map_resultado.put("variable_93", SIN_DATO_93);
					map_resultado.put("variable_94", SIN_DATO_94);
					map_resultado.put("variable_95", SIN_DATO_95);
				} else {
					map_resultado.put("variable_93", NO_APLICA_93);
					map_resultado.put("variable_94", NO_APLICA_94);
					map_resultado.put("variable_95", NO_APLICA_95);
				}
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				boolean aplica_resultado = false;
				if (fecha_resultado != null) {
					map_resultado.put("variable_93",
							formato_fecha.format(fecha_resultado));
					aplica_resultado = true;
				} else {
					map_resultado.put("variable_93", SIN_DATO_93);
				}

				String respuesta_4505 = (String) map.get("codigo_respuesta");
				if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
						&& respuesta_4505.matches("[0-9]*$")) {
					int resultado_int = Integer.parseInt(respuesta_4505);
					if (resultado_int > 1 && resultado_int < 6) {
						map_resultado.put("variable_94", respuesta_4505);
						aplica_resultado = true;
					} else {
						map_resultado.put("variable_94", SIN_DATO_94);
					}
				} else {
					map_resultado.put("variable_94", SIN_DATO_94);
				}
				map_resultado.put("variable_95", SIN_DATO_95);
				return aplica_resultado;
			}
		} else {
			map_resultado.put("variable_93", NO_APLICA_93);
			map_resultado.put("variable_94", NO_APLICA_94);
			map_resultado.put("variable_95", NO_APLICA_95);
		}
		return false;
	}

	/**
	 * 96) Fecha MamografÃ­a<br/>
	 * AAAA-MM-DD<br/>
	 * Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Como obligatorio se registran las mujeres mayores de 50 aÃ±os. De forma
	 * Opcional se pueden registrar las realizadas en mujeres de 35 a 49 aÃ±os.<br/>
	 * Usar la opcion 1845-01-01 en poblacion no objeto de esta actividad.<br/>
	 * <br/>
	 * Validar que cuando la variable 96 registre un dato diferente a 1845-01-01
	 * la variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 96 registre un valor diferente a
	 * 1845-01-01 el cÃ¡lculo de la edad* debe ser mayor a 35 aÃ±os.<br/>
	 * 
	 * 
	 * 97) Resultado MamografÃ­a<br/>
	 * Clasificacion BIRADS Registre:<br/>
	 * 1- BIRADS 0: Necesidad de Nuevo Estudio ImagenolÃ³gico o Mamograma previo
	 * para evaluacion<br/>
	 * 2- BIRADS 1: Negativo<br/>
	 * 3- BIRADS 2: Hallazgos Benignos<br/>
	 * 4- BIRADS 3: Probablemente Benigno<br/>
	 * 5- BIRADS 4: Anormalidad Sospechosa<br/>
	 * 6- BIRADS 5: Altamente Sospechoso de Malignidad 7- BIRADS 6: Malignidad
	 * por Biopsia conocida<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * Si no aplica registrar 0<br/>
	 * <br/>
	 * 
	 * Se debe registrar el dato que corresponde con el valor permitido,
	 * teniendo en cuenta que no es el mismo nÃºmero que aplica con la categorÃ­a
	 * del BIRADS.<br/>
	 * Usar la opcion 0 en poblacion no objeto de esta actividad. Por
	 * estandarizacion se ajustan los valores permitidos, se modifica la
	 * longitud del campo a 3.<br/>
	 * <br/>
	 * 
	 * Validar que cuando la variable 97 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 97 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser mayor a 35aÃ±os.<br/>
	 * <br/>
	 * 
	 * 
	 * 98) CÃ³digo de habilitacion IPS donde se toma MamografÃ­a<br/>
	 * Tabla REPS (Registro Especial de Prestadores de Servicios de Salud).<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * Si no aplica registrar 0<br/>
	 * Registrar segÃºn corresponda en mujeres mayores de 50 aÃ±os obligatorio y
	 * opcional en mujeres de 35 a 49 aÃ±os.<br/>
	 * La opcion 0 se usa en caso de personas de sexo masculino o en mujeres
	 * menores de 35 aÃ±os.<br/>
	 * La opcion 999 se utiliza:<br/>
	 * a. Cuando no se tiene el dato.<br/>
	 * b. Cuando el cÃ³digo de la IPS aÃºn no se encuentra actualizado en el REPS.<br/>
	 * <br/>
	 * 
	 * Validar que cuando la variable 98 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * Validar que cuando la variable 98 registre un valor diferente a 0 el
	 * cÃ¡lculo de la edad* debe ser mayor a 35 aÃ±os.<br/>
	 * */
	private static boolean consultarVariable96_98(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, Map<String, Integer> map_edad) {

		String NO_APLICA_96 = "1845-01-01";
		String SIN_DATO_96 = "1800-01-01";

		String NO_APLICA_97 = "0";
		String SIN_DATO_97 = "999";

		String NO_APLICA_98 = "0";
		String SIN_DATO_98 = "999";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_MAMOGRAFIA);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				int anios = (Integer) map_edad.get("anios");
				if (anios > 35) {
					map_resultado.put("variable_96", SIN_DATO_96);
					map_resultado.put("variable_97", SIN_DATO_97);
					map_resultado.put("variable_98", SIN_DATO_98);
				} else {
					map_resultado.put("variable_96", NO_APLICA_96);
					map_resultado.put("variable_97", NO_APLICA_97);
					map_resultado.put("variable_98", NO_APLICA_98);
				}
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				boolean aplica_resultado = false;
				if (fecha_resultado != null) {
					map_resultado.put("variable_96",
							formato_fecha.format(fecha_resultado));
					aplica_resultado = true;
				} else {
					map_resultado.put("variable_96", SIN_DATO_96);
				}

				String respuesta_4505 = (String) map.get("codigo_respuesta");
				if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
						&& respuesta_4505.matches("[0-9]*$")) {
					int resultado_int = Integer.parseInt(respuesta_4505);
					if (resultado_int > 1 && resultado_int < 6) {
						map_resultado.put("variable_97", respuesta_4505);
						aplica_resultado = true;
					} else {
						map_resultado.put("variable_97", SIN_DATO_97);
					}
				} else {
					map_resultado.put("variable_97", SIN_DATO_97);
				}
				map_resultado.put("variable_98", SIN_DATO_98);
				return aplica_resultado;
			}
		} else {
			map_resultado.put("variable_96", NO_APLICA_96);
			map_resultado.put("variable_97", NO_APLICA_97);
			map_resultado.put("variable_98", NO_APLICA_98);
		}
		return false;
	}

	/**
	 * Fecha Toma Biopsia Seno por BACAF<br/>
	 * AAAA-MM-DD<br/>
	 * Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01<br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01<br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * 
	 * Usar la opcion 1845-01-01 en poblacion no objeto de esta actividad y en
	 * mujeres con reporte de mamografÃ­a normal.<br/>
	 * 
	 * Validar que cuando la variable 99 registre un dato diferente a 1845-01-01
	 * la variable 10 corresponda a F.<br/>
	 * */
	private static boolean consultarVariable99(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {

		String NO_APLICA_99 = "1845-01-01";
		String SIN_DATO_99 = "1800-01-01";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_BIOPSIA_SENO);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_CONSULTAR_PROCEDIMIENTO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_99", SIN_DATO_99);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				if (fecha_resultado != null) {
					map_resultado.put("variable_99",
							formato_fecha.format(fecha_resultado));
					return true;
				} else {
					map_resultado.put("variable_99", SIN_DATO_99);
				}
			}
		} else {
			map_resultado.put("variable_99", NO_APLICA_99);
		}
		return false;
	}

	/**
	 * 100) Fecha Resultado Biopsia Seno<br/>
	 * AAAA-MM-DD<br/>
	 * Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Usar la opcion 1845-01-01 en poblacion no objeto de esta actividad y en
	 * mujeres con reporte de mamografÃ­a normal.<br/>
	 * Validar que cuando la variable 100 registre un dato diferente a
	 * 1845-01-01 la variable 10 corresponda a F.<br/>
	 * 
	 * 
	 * 101) Resultado Biopsia Seno<br/>
	 * Registre:<br/>
	 * 1- Benigna<br/>
	 * 2- AtÃ­pica (Indeterminada)<br/>
	 * 3- Malignidad Sospechosa/Probable<br/>
	 * 4- Maligna <br/>
	 * 5- No Satisfactoria<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * Si no aplica registrar 0<br/>
	 * <br/>
	 * 
	 * Usar la opcion 0 en poblacion no objeto de esta actividad y en mujeres
	 * con reporte de mamografÃ­a normal Por estandarizacion se ajustan los
	 * valores permitidos, se modifica la longitud del campo a 3. <br/>
	 * <br/>
	 * 
	 * Validar que cuando la variable 101 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.<br/>
	 * 
	 * 102) CÃ³digo de habilitacion IPS donde se toma Biopsia Seno<br/>
	 * Tabla REPS (Registro Especial de Prestadores de Servicios de Salud).<br/>
	 * Si no tiene el dato registrar 999<br/>
	 * Si no aplica registrar 0<br/>
	 * <br/>
	 * 
	 * La opcion 999 se utiliza: <br/>
	 * a. Cuando no se tiene el dato.<br/>
	 * b. Cuando el cÃ³digo de la IPS aÃºn no se encuentra actualizado en el REPS.<br/>
	 * Usar la opcion 0 en poblacion no objeto de esta actividad y en mujeres
	 * con reporte de mamografÃ­a normal. <br/>
	 * 
	 * 
	 * Validar que cuando la variable 102 registre un dato diferente a 0 la
	 * variable 10 corresponda a F.
	 * 
	 * */
	private static boolean consultarVariable100_102(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {

		String NO_APLICA_100 = "1845-01-01";
		String SIN_DATO_100 = "1800-01-01";

		String NO_APLICA_101 = "0";
		String SIN_DATO_101 = "999";

		String NO_APLICA_102 = "0";
		String SIN_DATO_102 = "999";

		String variable_10_sexo = (String) map_resultado.get("variable_10");// sexo

		if (variable_10_sexo.equals(RESPUESTA_VARIABLE_10_FEMENINO)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", administradora.getCodigo_empresa());
			params.put("codigo_sucursal", administradora.getCodigo_sucursal());
			params.put("codigo_administradora", administradora.getCodigo());
			params.put("nro_identificacion", id_paciente);
			params.put("fecha_inicio", fecha_inicio);
			params.put("fecha_final", fecha_final);
			params.put("codigo_cups",
					IResolucion4505Constantes.LABORATORIO_BIOPSIA_SENO);

			List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
					.getReportService()
					.getReport(
							params,
							IResolucion4505Constantes.NAMESPACE_XML,
							IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

			if (resultado_consulta_4505.isEmpty()
					|| resultado_consulta_4505.get(0) == null) {
				map_resultado.put("variable_100", SIN_DATO_100);
				map_resultado.put("variable_101", SIN_DATO_101);
				map_resultado.put("variable_102", SIN_DATO_102);
			} else {
				Map<String, Object> map = resultado_consulta_4505.get(0);
				Timestamp fecha_resultado = (Timestamp) map
						.get("fecha_resultado");
				boolean aplica_resultado = false;
				if (fecha_resultado != null) {
					map_resultado.put("variable_100",
							formato_fecha.format(fecha_resultado));
					aplica_resultado = true;
				} else {
					map_resultado.put("variable_100", SIN_DATO_100);
				}

				String respuesta_4505 = (String) map.get("codigo_respuesta");
				if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
						&& respuesta_4505.matches("[0-9]*$")) {
					int resultado_int = Integer.parseInt(respuesta_4505);
					if (resultado_int > 1 && resultado_int < 5) {
						map_resultado.put("variable_101", respuesta_4505);
						aplica_resultado = true;
					} else {
						map_resultado.put("variable_101", SIN_DATO_101);
					}
				} else {
					map_resultado.put("variable_101", SIN_DATO_101);
				}
				map_resultado.put("variable_102", SIN_DATO_102);
				return aplica_resultado;
			}
		} else {
			map_resultado.put("variable_100", NO_APLICA_100);
			map_resultado.put("variable_101", NO_APLICA_101);
			map_resultado.put("variable_102", NO_APLICA_102);
		}
		return false;
	}

	/**
	 * 103) Fecha Toma de Hemoglobina<br/>
	 * AAAA-MM-DD<br/>
	 * Si no se tiene el dato registrar 1800-01-01<br/>
	 * Si no se realiza por una Tradicion registrar 1805-01-01 <br/>
	 * Si no se realiza por una Condicion de Salud registrar 1810-01-01 <br/>
	 * Si no se realiza por Negacion del usuario registrar 1825-01-01<br/>
	 * Si no se realiza por tener datos de contacto del usuario no actualizados
	 * registrar 1830-01-01<br/>
	 * Si no se realiza por otras razones registrar 1835-01-01<br/>
	 * Si no aplica registrar 1845-01-01<br/>
	 * <br/>
	 * Es obligatorio para la norma tÃ©cnica de joven (mujeres de 10 a 13 aÃ±os) y
	 * gestantes, opcional para el resto de la poblacion.<br/>
	 * Usar la opcion 1845-01-01 en poblacion no objeto de esta actividad.<br/>
	 * <br/>
	 * 
	 */
	private static boolean consultarVariable103(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarTomaLaboratorio(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_103",
				IResolucion4505Constantes.LABORATORIO_HEMOGLOBINA);
	}

	/**
	 * 104) Hemoglobina<br/>
	 * Registre el dato reportado por el laboratorio. Si no aplica registre 0 <br/>
	 * Usar la opcion 0 en poblacion no objeto de esta actividad. Por
	 * estandarizacion se ajustan los valores permitidos.<br/>
	 * <br/>
	 * 
	 * Validar que cuando la variable 104 registre un dato diferente a 0 el
	 * valor registrado este en el rango de 1,5 y 20.<br/>
	 * <br/>
	 * */
	private static boolean consultarVariable104(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {

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

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put("variable_104", SIN_DATO_104);
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			String respuesta_4505 = (String) map.get("codigo_respuesta");
			if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
					&& respuesta_4505.matches("[0-9.,]*$")) {
				double resultado_int = Double.parseDouble(respuesta_4505);
				if (resultado_int > 1.5 && resultado_int < 20) {
					map_resultado.put("variable_104", respuesta_4505);
					return true;
				} else {
					map_resultado.put("variable_104", NO_APLICA_104);
				}
			} else {
				map_resultado.put("variable_104", SIN_DATO_104);
			}
		}
		return false;
	}

	/**
	 * Fecha de la Toma de Glicemia Basal
	 * */
	private static boolean consultarVariable105(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarTomaLaboratorio(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_105",
				IResolucion4505Constantes.LABORATORIO_GLISEMIA_BASAL);
	}

	/**
	 * 106) Fecha Creatinina<br/>
	 * 107) Creatinina<br/>
	 * Validar que cuando la variable 107 registre un dato diferente a 0 o 999
	 * el valor registrado este en el rango de 0,2 y 25.
	 * */
	private static boolean consultarVariable106_107(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {

//		String NO_APLICA_106 = "1845-01-01";
		String SIN_DATO_106 = "1800-01-01";

		String NO_APLICA_107 = "0";
		String SIN_DATO_107 = "999";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_CREATININA);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put("variable_106", SIN_DATO_106);
			map_resultado.put("variable_107", SIN_DATO_107);
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			boolean aplica_resultado = false;
			if (fecha_resultado != null) {
				map_resultado.put("variable_106",
						formato_fecha.format(fecha_resultado));
				aplica_resultado = true;
			} else {
				map_resultado.put("variable_106", SIN_DATO_106);
			}

			String respuesta_4505 = (String) map.get("codigo_respuesta");
			if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
					&& respuesta_4505.matches("[0-9.,]*$")) {
				double resultado_int = Double.parseDouble(respuesta_4505);
				if (resultado_int > 0.2 && resultado_int < 25) {
					map_resultado.put("variable_107", respuesta_4505);
					aplica_resultado = true;
				} else {
					map_resultado.put("variable_107", NO_APLICA_107);
				}
			} else {
				map_resultado.put("variable_107", SIN_DATO_107);
			}
			return aplica_resultado;
		}
		return false;
	}

	/**
	 * 108) Fecha Hemoglobina Glicosilada<br/>
	 * 109) Hemoglobina Glicosilada<br/>
	 * Validar que cuando la variable 109 registre un dato diferente a 0 o 999
	 * el valor registrado este en el rango de 5 y 20.<br/>
	 * <br/>
	 * */
	private static boolean consultarVariable108_109(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {

		// String NO_APLICA_108 = "1845-01-01";
		String SIN_DATO_108 = "1800-01-01";

		String NO_APLICA_109 = "0";
		String SIN_DATO_109 = "999";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_GLICOSILADA);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put("variable_108", SIN_DATO_108);
			map_resultado.put("variable_109", SIN_DATO_109);
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			boolean aplica_resultado = false;
			if (fecha_resultado != null) {
				map_resultado.put("variable_108",
						formato_fecha.format(fecha_resultado));
				aplica_resultado = true;
			} else {
				map_resultado.put("variable_108", SIN_DATO_108);
			}

			String respuesta_4505 = (String) map.get("codigo_respuesta");
			if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()
					&& respuesta_4505.matches("[0-9.,]*$")) {
				double resultado_int = Double.parseDouble(respuesta_4505);
				if (resultado_int > 0.2 && resultado_int < 25) {
					map_resultado.put("variable_109", respuesta_4505);
					aplica_resultado = true;
				} else {
					map_resultado.put("variable_109", NO_APLICA_109);
				}
			} else {
				map_resultado.put("variable_109", SIN_DATO_109);
			}
			return aplica_resultado;
		}
		return false;
	}

	private static boolean consultarVariable110(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarTomaLaboratorio(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_110",
				IResolucion4505Constantes.LABORATORIO_MICROALBUMINURIA);
	}

	private static boolean consultarVariable111(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarTomaLaboratorio(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_111",
				IResolucion4505Constantes.LABORATORIO_HDL);
	}

	private static boolean consultarVariable112(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		return consultarTomaLaboratorio(map_resultado, administradora, id_paciente,
				fecha_inicio, fecha_final, "variable_112",
				IResolucion4505Constantes.LABORATORIO_BACILOSCOPIA_DIAGNOSTICO);
	}

	/**
	 * Baciloscopia de DiagnÃ³stico<br/>
	 * 1- Negativa<br/>
	 * 2- Positiva<br/>
	 * 3- En proceso<br/>
	 * 4- No<br/>
	 * 22- Sin dato<br/>
	 * */
	private static boolean consultarVariable113(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {

		String NEGATIVA = "1";
		String POSITIVA = "2";
		String EN_PROCESO = "3";
//		String NO = "4";
		String SIN_DATO = "22";

		String SIN_DATO_112 = "1800-01-01";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", administradora.getCodigo_empresa());
		params.put("codigo_sucursal", administradora.getCodigo_sucursal());
		params.put("codigo_administradora", administradora.getCodigo());
		params.put("nro_identificacion", id_paciente);
		params.put("fecha_inicio", fecha_inicio);
		params.put("fecha_final", fecha_final);
		params.put("codigo_cups",
				IResolucion4505Constantes.LABORATORIO_GLICOSILADA);

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService().getReport(params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_LABORATORIO_CLINICO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			String variable_112 = map_resultado.get("variable_112") + "";
			if (variable_112.equals(SIN_DATO_112)) {
				map_resultado.put("variable_113", SIN_DATO);
			} else {
				map_resultado.put("variable_113", EN_PROCESO);
				return true;
			}
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			String respuesta_4505 = (String) map.get("codigo_respuesta");
			if (respuesta_4505 != null && !respuesta_4505.trim().isEmpty()) {
				map_resultado.put("variable_113",
						respuesta_4505.equals("pos") ? POSITIVA : NEGATIVA);
				return true;
			} else {
				map_resultado.put("variable_113", SIN_DATO);
			}
		}
		return false;
	}

	/**
	 * Tratamiento para Hipotiroidismo CongÃ©nito
	 * */
	private static boolean consultarVariable114(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
//		String NO_APLICA = "0";
		String SIN_DATO = "22";
		map_resultado.put("variable_114", SIN_DATO);
		return false;
	}

	/**
	 * Tratamiento para SÃ­filis gestacional
	 * */
	private static boolean consultarVariable115(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String SIN_DATO = "22";
		String NO_APLICA = "0";

		String variable_10 = (String) map_resultado.get("variable_10");
		String variable_14 = (String) map_resultado.get("variable_14");
		String variable_15 = (String) map_resultado.get("variable_15");

		if (variable_10.equals(RESPUESTA_VARIABLE_10_FEMENINO)
				&& variable_14.equals(RESPUESTA_VARIABLE_14_FEMENINO)
				&& variable_15.equals(RESPUESTA_VARIABLE_15_FEMENINO)) {
			map_resultado.put("variable_115", SIN_DATO);
		} else {
			map_resultado.put("variable_115", NO_APLICA);
		}
		return false;
	}

	/**
	 * Tratamiento para SÃ­filis CongÃ©nita
	 * */
	private static boolean consultarVariable116(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String SIN_DATO = "22";
		String NO_APLICA = "0";

		String variable_15 = (String) map_resultado.get("variable_15");

		if (variable_15.equals(RESPUESTA_VARIABLE_15_RECIEN_NACIDO)) {
			map_resultado.put("variable_116", SIN_DATO);
		} else {
			map_resultado.put("variable_116", NO_APLICA);
		}
		return false;
	}

	private static boolean consultarVariable117(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String SIN_DATO = "22";
		String NO_APLICA = "0";

		String PAUCIBACILAR_20 = "1";
		String MULTIBACILAR_20 = "2";

		String variable_20 = (String) map_resultado.get("variable_20");

		if (variable_20.equals(PAUCIBACILAR_20)
				|| variable_20.equals(MULTIBACILAR_20)) {
			map_resultado.put("variable_117", SIN_DATO);
		} else {
			map_resultado.put("variable_117", NO_APLICA);
		}
		return false;
	}

	/**
	 * Fecha de Terminacion Tratamiento para Leishmaniasis
	 * */
	private static boolean consultarVariable118(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final) {
		String NOAPLICA = "1845-01-01";
		map_resultado.put("variable_118", NOAPLICA);
		return false;
	}

	private static boolean consultarTomaLaboratorio(Map map_resultado,
			Administradora administradora, String id_paciente,
			Date fecha_inicio, Date fecha_final, String nombre_variable,
			String[] codigo_cups) {

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

		List<Map<String, Object>> resultado_consulta_4505 = getServiceLocator()
				.getReportService()
				.getReport(
						params,
						IResolucion4505Constantes.NAMESPACE_XML,
						IResolucion4505Constantes.VARIABLE_CONSULTAR_PROCEDIMIENTO);

		if (resultado_consulta_4505.isEmpty()
				|| resultado_consulta_4505.get(0) == null) {
			map_resultado.put(nombre_variable, SIN_DATO);
		} else {
			Map<String, Object> map = resultado_consulta_4505.get(0);
			Timestamp fecha_resultado = (Timestamp) map.get("fecha_resultado");
			if (fecha_resultado != null) {
				map_resultado.put(nombre_variable,
						formato_fecha.format(fecha_resultado));
				return true;
			} else {
				map_resultado.put(nombre_variable, SIN_DATO);
			}
		}
		return false;
	}

	public static String validarTamanio(String valor, int tamanio_maximo) {
		if (valor.length() < tamanio_maximo) {
			return valor;
		} else {
			return valor.substring(0, tamanio_maximo - 1);
		}
	}

	public static ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}
}
