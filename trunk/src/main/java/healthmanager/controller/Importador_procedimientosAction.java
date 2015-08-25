/*
 * parametros_signosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Procedimientos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.framework.util.MensajesUtil;

public class Importador_procedimientosAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Importador_procedimientosAction.class);

	@View
	private Button btnAdjuntar_archivo;

	@View
	private Button btnImportar;

	@View
	private Label labelArchivo_adjunto;

	@View
	private Listbox lbxManuales;

	private Media media_file;

	private Workbook workbook_descarga;

	private final int BUFFER = 2048;

	@Override
	public void init() {

	}

	public void adjuntarArchivo(Media media) {
		if (!lbxManuales.getSelectedItem().getValue().toString().isEmpty()) {
			if (media.getFormat().equals("xls")
					|| media.getFormat().equals("xlsx")) {
				media_file = media;
				labelArchivo_adjunto.setValue(media.getName());
				btnImportar.setDisabled(false);
			} else {
				MensajesUtil.mensajeAlerta("Error en el formato",
						"Solo se permiten formtos en excel");
				btnImportar.setDisabled(true);
			}
		} else {
			btnImportar.setDisabled(true);
			MensajesUtil
					.mensajeAlerta("Se debe seleccionar un manual",
							"Seleccionar manual para poder hacer la importacion de los registros");
		}

	}

	public void onClickContinuarProceso() {
		//log.info("ejecutandi metodo @onClickContinuarProceso()");
		Messagebox.show("¿Esta seguro que desea continuar con el proceso?",
				"Confiramcion", Messagebox.YES + Messagebox.NO,
				Messagebox.QUESTION, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onYes")) {
							try {
								iniciarImportacion();
								descargarArchivoInformacion();
							} catch (Exception e) {
								MensajesUtil.mensajeError(e,
										"Error en la importacion",
										Importador_procedimientosAction.this);
							}
						}
					}
				});
	}

	public void iniciarImportacion() throws Exception {
//		String manual = lbxManuales.getSelectedItem().getValue().toString();
		workbook_descarga = null;
		if (media_file.getFormat().endsWith("xls")) {
			workbook_descarga = new HSSFWorkbook(media_file.getStreamData());
		} else {
			workbook_descarga = new XSSFWorkbook(media_file.getStreamData());
		}

		iniciarImportacionSoat();

	}

	public void iniciarImportacionSoat() throws Exception {
		final String COLUMNAS_ARCHIVO[] = { "codigo_soat", "codigo_cups",
				"descripcion", "porcentaje", "clasificacion",
				"codigo_unidad_funcional", "codigo_tipo_procedimiento",
				"consulta", "quirurgico", "tipo_quirurgico", "urgencias",
				"hospitalizacion", "recien_nacido", "creacion_date",
				"ultimo_update", "delete_date", "creacion_user", "ultimo_user",
				"delete_user", "area_intervencion", "meta", "grupo",
				"codigo_contabilidad", "sexo", "limite_inferior",
				"limite_superior", "pyp", "cobra_copago", "tipo_procedimiento",
				"frecuencia_orden", "consulta_especializada",
				"consulta_especializada_med_esp", "aut_medi_general",
				"aut_medi_especialista" };

		Sheet sheet = workbook_descarga.getSheetAt(0);

		Row fila = sheet.getRow(0);

		Cell cols[] = new Cell[COLUMNAS_ARCHIVO.length];
		for (int i = 0; i < COLUMNAS_ARCHIVO.length; i++) {
			String name_col = COLUMNAS_ARCHIVO[i];
			cols[i] = buscarCelda(name_col, fila);
			if (cols[i] == null) {
				throw new Exception("Columna \"" + name_col
						+ "\" no se encuentra");
			}
		}

		//log.info("importacion soat : columnas encontradas correctamente");

		List<Procedimientos> listado_procedimientos_archivo = new ArrayList<Procedimientos>();

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			/**
			 * fila = sheet.getRow(i); String codigo_soat =
			 * getValorCeldaTexto(fila.getCell(cols[0] .getColumnIndex()));
			 * String codigo_cups = getValorCeldaTexto(fila.getCell(cols[1]
			 * .getColumnIndex())); String descripcion =
			 * getValorCeldaTexto(fila.getCell(cols[2] .getColumnIndex()));
			 * String porcentaje = getValorCeldaTexto(fila.getCell(cols[3]
			 * .getColumnIndex())); String clasificacion =
			 * getValorCeldaTexto(fila.getCell(cols[4] .getColumnIndex()));
			 * String codigo_unidad_funcional = getValorCeldaTexto(fila
			 * .getCell(cols[5].getColumnIndex())); String
			 * codigo_tipo_procedimiento = getValorCeldaTexto(fila
			 * .getCell(cols[6].getColumnIndex())); String consulta =
			 * getValorCeldaTexto(fila.getCell(cols[7] .getColumnIndex()));
			 * String quirurgico = getValorCeldaTexto(fila.getCell(cols[8]
			 * .getColumnIndex())); String tipo_quirurgico =
			 * getValorCeldaTexto(fila.getCell(cols[9] .getColumnIndex()));
			 * String urgencias = getValorCeldaTexto(fila.getCell(cols[10]
			 * .getColumnIndex())); String hospitalizacion =
			 * getValorCeldaTexto(fila.getCell(cols[11] .getColumnIndex()));
			 * String recien_nacido = getValorCeldaTexto(fila.getCell(cols[12]
			 * .getColumnIndex()));
			 * 
			 * String area_intervencion =
			 * getValorCeldaTexto(fila.getCell(cols[19] .getColumnIndex()));
			 * String meta = getValorCeldaTexto(fila.getCell(cols[20]
			 * .getColumnIndex())); String grupo =
			 * getValorCeldaTexto(fila.getCell(cols[21] .getColumnIndex()));
			 * String codigo_contabilidad = getValorCeldaTexto(fila
			 * .getCell(cols[22].getColumnIndex())); String sexo =
			 * getValorCeldaTexto(fila.getCell(cols[23] .getColumnIndex()));
			 * String limite_inferior = getValorCeldaTexto(fila.getCell(cols[24]
			 * .getColumnIndex())); String limite_superior =
			 * getValorCeldaTexto(fila.getCell(cols[25] .getColumnIndex()));
			 * String pyp = getValorCeldaTexto(fila.getCell(cols[26]
			 * .getColumnIndex())); String cobra_copago =
			 * getValorCeldaTexto(fila.getCell(cols[27] .getColumnIndex()));
			 * String tipo_procedimiento = getValorCeldaTexto(fila
			 * .getCell(cols[28].getColumnIndex())); String frecuencia_orden =
			 * getValorCeldaTexto(fila.getCell(cols[29] .getColumnIndex()));
			 * String consulta_especializada = getValorCeldaTexto(fila
			 * .getCell(cols[30].getColumnIndex())); String
			 * consulta_especializada_med_esp = getValorCeldaTexto(fila
			 * .getCell(cols[31].getColumnIndex())); String aut_medi_general =
			 * getValorCeldaTexto(fila.getCell(cols[32] .getColumnIndex()));
			 * String aut_medi_especialista = getValorCeldaTexto(fila
			 * .getCell(cols[33].getColumnIndex()));
			 * 
			 * Procedimientos procedimiento = new Procedimientos();
			 * procedimiento.setArea_intervencion(area_intervencion);
			 * procedimiento.setAut_medi_especialista(aut_medi_especialista);
			 * procedimiento.setAut_medi_general(aut_medi_general);
			 * procedimiento.setClasificacion(clasificacion);
			 * procedimiento.setCobra_copago(cobra_copago); procedimiento
			 * .setCodigo_contabilidad(codigo_contabilidad != null ?
			 * codigo_contabilidad : "");
			 * procedimiento.setCodigo_cups(codigo_cups);
			 * procedimiento.setCodigo_soat(codigo_soat); procedimiento
			 * .setCodigo_tipo_procedimiento(codigo_tipo_procedimiento);
			 * procedimiento
			 * .setCodigo_unidad_funcional(codigo_unidad_funcional);
			 * procedimiento.setConsulta(consulta);
			 * procedimiento.setConsulta_especializada(consulta_especializada);
			 * procedimiento .setConsulta_especializada_med_esp(
			 * consulta_especializada_med_esp);
			 * procedimiento.setCreacion_date(new Timestamp(new
			 * Date().getTime())); procedimiento.setCreacion_user("ADMIN");
			 * procedimiento.setDelete_date(new Timestamp(new
			 * Date().getTime())); procedimiento.setDelete_user("ADMIN");
			 * procedimiento.setDescripcion(descripcion);
			 * procedimiento.setFrecuencia_orden(new Integer(frecuencia_orden));
			 * procedimiento.setGrupo(grupo);
			 * procedimiento.setHospitalizacion(hospitalizacion);
			 * procedimiento.setLimite_inferior(limite_inferior);
			 * procedimiento.setLimite_superior(limite_superior);
			 * procedimiento.setMeta(meta == null ? null : new Double(meta));
			 * procedimiento.setPorcentaje(new Double(porcentaje));
			 * procedimiento.setPyp(pyp);
			 * procedimiento.setQuirurgico(quirurgico);
			 * procedimiento.setRecien_nacido(recien_nacido);
			 * procedimiento.setSexo(sexo);
			 * procedimiento.setTipo_procedimiento(tipo_procedimiento);
			 * procedimiento.setTipo_quirurgico(tipo_quirurgico);
			 * procedimiento.setUltimo_update(new Timestamp(new
			 * Date().getTime())); procedimiento.setUltimo_user("ADMIN");
			 * procedimiento.setUrgencias(urgencias);
			 * 
			 * listado_procedimientos_archivo.add(procedimiento);
			 */
		}

		//log.info("importacion soat : " + listado_procedimientos_archivo.size()
				//+ " procedimientos cargados del excel");

		Map<String, Object> parametros = new HashMap<String, Object>();

		List<Procedimientos> listado_procedimientos = getServiceLocator()
				.getProcedimientosService().listar(parametros);

		Map<Integer, Integer> mapa_indices = new HashMap<Integer, Integer>();

		Sheet sheet_importados = workbook_descarga.createSheet("importados");
		crearFilaEncabezado(sheet_importados, new String[] { "codigo_soat",
				"codigo_cups", "descripcion", "porcentaje", "clasificacion",
				"codigo_unidad_funcional", "codigo_tipo_procedimiento",
				"consulta", "quirurgico", "tipo_quirurgico", "urgencias",
				"hospitalizacion", "recien_nacido", "creacion_date",
				"ultimo_update", "delete_date", "creacion_user", "ultimo_user",
				"delete_user", "area_intervencion", "meta", "grupo",
				"codigo_contabilidad", "sexo", "limite_inferior",
				"limite_superior", "pyp", "cobra_copago", "tipo_procedimiento",
				"frecuencia_orden", "consulta_especializada",
				"consulta_especializada_med_esp", "aut_medi_general",
				"aut_medi_especialista" });
		int indice_fila_importado = 1;

		Sheet sheet_repetidos = workbook_descarga.createSheet("repetidos");
		crearFilaEncabezado(sheet_repetidos, new String[] { "codigo_soat",
				"codigo_cups", "descripcion", "porcentaje", "clasificacion",
				"codigo_unidad_funcional", "codigo_tipo_procedimiento",
				"consulta", "quirurgico", "tipo_quirurgico", "urgencias",
				"hospitalizacion", "recien_nacido", "creacion_date",
				"ultimo_update", "delete_date", "creacion_user", "ultimo_user",
				"delete_user", "area_intervencion", "meta", "grupo",
				"codigo_contabilidad", "sexo", "limite_inferior",
				"limite_superior", "pyp", "cobra_copago", "tipo_procedimiento",
				"frecuencia_orden", "consulta_especializada",
				"consulta_especializada_med_esp", "aut_medi_general",
				"aut_medi_especialista" });
		int indice_fila_repetido = 1;

		for (Procedimientos procedimiento : listado_procedimientos) {
			List<Integer> listado_indices = indicesProcedimientosCups(
					procedimiento, listado_procedimientos_archivo);
			if (listado_indices.size() == 1) {
				Procedimientos procedimiento_aux = listado_procedimientos_archivo
						.get(listado_indices.get(0));
				procedimiento_aux.setNivel(procedimiento.getNivel());
				getServiceLocator().getProcedimientosService().actualizar(
						procedimiento_aux);
				crearFilaProcedimiento(sheet_importados, procedimiento_aux,
						indice_fila_importado);
				indice_fila_importado++;
				/*log.info("importacion soat : procedimiento soat actualizado ("
						+ indice_fila_importado + ") "
						+ procedimiento_aux.getId_procedimiento()
						+ " codigo_cups :" + procedimiento_aux.getCodigo_cups());*/
			} else if (listado_indices.size() > 1) {
				for (Integer indice : listado_indices) {
					mapa_indices.put(indice, indice);
				}
			}
		}

		for (Integer indice : mapa_indices.keySet()) {
			Procedimientos procedimiento = listado_procedimientos_archivo
					.get(indice);
			crearFilaProcedimiento(sheet_repetidos, procedimiento,
					indice_fila_repetido);
			indice_fila_repetido++;
			/*log.info("importacion soat : procedimiento soat repetido ("
					+ indice_fila_repetido + ") "
					+ procedimiento.getId_procedimiento() + " codigo_cups :"
					+ procedimiento.getCodigo_cups());*/
		}

	}

	public List<Integer> indicesProcedimientosCups(
			Procedimientos procedimiento,
			List<Procedimientos> lista_procedimientos) {
		List<Integer> lista_indices = new ArrayList<Integer>();
		for (int indice = 0; indice < lista_procedimientos.size(); indice++) {
			Procedimientos procedimiento2 = lista_procedimientos.get(indice);
			if (procedimiento.getCodigo_cups().equals(
					procedimiento2.getCodigo_cups())) {
				lista_indices.add(indice);
			}
		}
		return lista_indices;
	}

	public void crearFilaEncabezado(Sheet hoja_excel, String[] columnas_titulos) {
		Row fila_encabezado = hoja_excel.createRow(0);
		for (int i = 0; i < columnas_titulos.length; i++) {
			Cell celda = fila_encabezado.createCell(i);
			celda.setCellValue(columnas_titulos[i]);
		}
	}

	public void crearFilaProcedimiento(Sheet hoja_excel,
			Procedimientos procedimiento, int indice) {

		Row fila = hoja_excel.createRow(indice);
		Cell celda = fila.createCell(1);
		celda.setCellValue(procedimiento.getCodigo_cups());

		celda = fila.createCell(2);
		celda.setCellValue(procedimiento.getDescripcion());

		celda = fila.createCell(4);
		celda.setCellValue(procedimiento.getClasificacion());

		celda = fila.createCell(7);
		celda.setCellValue(procedimiento.getConsulta());

		celda = fila.createCell(8);
		celda.setCellValue(procedimiento.getQuirurgico());

		celda = fila.createCell(9);
		celda.setCellValue(procedimiento.getTipo_quirurgico());

		celda = fila.createCell(10);
		celda.setCellValue(procedimiento.getUrgencias());

		celda = fila.createCell(11);
		celda.setCellValue(procedimiento.getHospitalizacion());

		celda = fila.createCell(12);
		celda.setCellValue(procedimiento.getRecien_nacido());

		celda = fila.createCell(13);
		celda.setCellValue(procedimiento.getCreacion_date());

		celda = fila.createCell(14);
		celda.setCellValue(procedimiento.getUltimo_update());

		celda = fila.createCell(15);
		celda.setCellValue(procedimiento.getDelete_date());

		celda = fila.createCell(16);
		celda.setCellValue(procedimiento.getCreacion_user());

		celda = fila.createCell(17);
		celda.setCellValue(procedimiento.getUltimo_user());

		celda = fila.createCell(18);
		celda.setCellValue(procedimiento.getDelete_user());

		celda = fila.createCell(22);
		celda.setCellValue(procedimiento.getCodigo_contabilidad());

		celda = fila.createCell(23);
		celda.setCellValue(procedimiento.getSexo());

		celda = fila.createCell(24);
		celda.setCellValue(procedimiento.getLimite_inferior());

		celda = fila.createCell(25);
		celda.setCellValue(procedimiento.getLimite_superior());

		celda = fila.createCell(26);
		celda.setCellValue(procedimiento.getPyp());

		celda = fila.createCell(27);
		celda.setCellValue(procedimiento.getCobra_copago());

		celda = fila.createCell(28);
		celda.setCellValue(procedimiento.getTipo_procedimiento());

		celda = fila.createCell(29);
		celda.setCellValue(procedimiento.getFrecuencia_orden());

		celda = fila.createCell(32);
		celda.setCellValue(procedimiento.getAut_medi_general());

		celda = fila.createCell(33);
		celda.setCellValue(procedimiento.getAut_medi_especialista());

	}

	public void descargarArchivoInformacion() throws Exception {
		if (workbook_descarga != null) {
			File fileDirectorio = getDirectorio();
			File fileZip = new File(fileDirectorio.getAbsolutePath()
					+ File.separator + "ZIP");
			if (!fileZip.exists()) {
				fileZip.mkdir();
			}

			File file = new File(fileDirectorio.getAbsolutePath()
					+ File.separator + "xlsTemp");
			if (!file.exists()) {
				file.mkdir();
			}

			// creamos los archivos
			crearArchivo(file, "_imp_" + media_file.getName(),
					workbook_descarga);

			String nameOfFileZip = fileZip.getAbsolutePath() + File.separator
					+ "prcedimientos_informacion.zip";
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(nameOfFileZip);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));
			byte data[] = new byte[BUFFER];
			String files[] = file.list();
			for (int i = 0; i < files.length; i++) {
				FileInputStream fi = new FileInputStream(file.getAbsolutePath()
						+ File.separator + files[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				// creamos una entrada zip
				ZipEntry entry = new ZipEntry(files[i]);
				// agregamos entradas zip al archivo
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
				File aux = new File(file.getAbsolutePath() + File.separator
						+ files[i]);
				if (aux.exists()) {
					aux.delete();
				}
			}
			out.close();

			FileInputStream archivo = new FileInputStream(nameOfFileZip);
			int longitud = archivo.available();
			byte[] datos = new byte[longitud];
			archivo.read(datos);
			archivo.close();
			File del = new File(nameOfFileZip);
			Filedownload.save(del, "application/zip");

		}

	}

	private void crearArchivo(File file, String title, Workbook libro)
			throws Exception {
		File archivo_excel = new File(file.getAbsolutePath() + File.separator
				+ title);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(archivo_excel);
			libro.write(fos);
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private File getDirectorio() {
		File file = new File(Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("")
				+ "/Files");
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(file.getAbsolutePath() + "/Temp");
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(file.getAbsolutePath() + "/"
				+ empresa.getCodigo_empresa() + ""
				+ sucursal.getCodigo_sucursal());
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	private Cell buscarCelda(String valor, Row fila) {
		Cell celda = null;
		for (short i = fila.getFirstCellNum(); i < fila.getLastCellNum(); i++) {
			Cell celda_aux = fila.getCell(i);
			if (celda_aux == null)
				break;
			if (valor.equalsIgnoreCase(getValorCeldaTexto(celda_aux))) {
				celda = celda_aux;
				break;
			}
		}
		return celda;
	}

	private String getValorCeldaTexto(Cell celda) {
		if (celda == null)
			return null;
		String valor = "";
		int tipo = celda != null ? celda.getCellType() : Cell.CELL_TYPE_STRING;
		switch (tipo) {
		case Cell.CELL_TYPE_STRING:
			valor = celda != null ? celda.getStringCellValue() : "";
			break;

		case Cell.CELL_TYPE_FORMULA:
			valor = celda != null ? celda.getCellFormula() : "";
			break;

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(celda)) {
				valor = celda.getDateCellValue().toString();
			} else {
				double valor_double = celda != null ? celda
						.getNumericCellValue() : 0;
				DecimalFormat formato = new DecimalFormat("#");
				valor = formato.format(valor_double);
			}
			break;

		case Cell.CELL_TYPE_BLANK:
			valor = "";
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			valor = Boolean.toString(celda != null ? celda
					.getBooleanCellValue() : false);
			break;

		}
		return valor.toUpperCase();
	}

}