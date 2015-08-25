package healthmanager.controller;

import healthmanager.modelo.bean.Resultado_laboratorios;
import healthmanager.modelo.service.Resultado_laboratoriosService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;
import com.framework.util.PoiUtil;

/**
 * Esta clase es para importar los laboratorios que apliquen para la 4505
 * 
 * @author Luis Miguel
 * */
public class ImportadorLaboratorioAction extends ZKWindow {

	private String FORMATO_XLS = ".xls";
	private String FORMATO_XLSX = ".xlsx";

	private enum ECabeceras {
		CODIGO_PROCEDIMIENTO, FECHA_REALIZACION, NUMERO_IDENTIFICACION, RESULTADO_4505;

		public String getObservacion() {
			if (this == CODIGO_PROCEDIMIENTO) {
				return "Codigo cups del procedimiento";
			} else if (this == FECHA_REALIZACION) {
				return "Fecha realizacion del laboratorio con el siguiente formato yyyy-MM-dd";
			} else if (this == NUMERO_IDENTIFICACION) {
				return "Numero de identificación del paciente";
			} else if (this == RESULTADO_4505) {
				return "Resultado de la 4505 con base al laboratorio";
			}
			return "";
		}
	}

	private int pos_codigo_procedimiento;
	private int pos_fecha_realizacion;
	private int pos_numero_identificacion;
	private int pos_resultado_4505;

	private Resultado_laboratoriosService resultado_laboratoriosService;

	private POIFSFileSystem fsFileSystem;

	@View
	private Textbox tbxDireccion;

	@Override
	public void init() throws Exception {

	}

	public void onImportar() {
		try {
			if (fsFileSystem != null) {
				HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem);
				HSSFSheet hssfSheet = workBook.getSheetAt(0);

				if (hssfSheet.getLastRowNum() <= 0) {
					MensajesUtil.mensajeAlerta("Advertencia",
							"El archivo esta vacio");
				} else if (hssfSheet.getLastRowNum() == 1) {
					MensajesUtil.mensajeAlerta("Advertencia",
							"El archivo esta vacio");
				} else {
					StringBuilder builder = new StringBuilder();

					// verificamos las posiciones
					pos_codigo_procedimiento = existeCabecera(
							hssfSheet.getRow(0),
							ECabeceras.CODIGO_PROCEDIMIENTO.toString());
					pos_fecha_realizacion = existeCabecera(hssfSheet.getRow(0),
							ECabeceras.FECHA_REALIZACION.toString());
					pos_numero_identificacion = existeCabecera(
							hssfSheet.getRow(0),
							ECabeceras.NUMERO_IDENTIFICACION.toString());
					pos_resultado_4505 = existeCabecera(hssfSheet.getRow(0),
							ECabeceras.RESULTADO_4505.toString());

					// verificamos si existen
					if (pos_codigo_procedimiento == -1) {
						builder.append("\n * "
								+ ECabeceras.CODIGO_PROCEDIMIENTO.toString());
					}

					if (pos_fecha_realizacion == -1) {
						builder.append("\n * "
								+ ECabeceras.FECHA_REALIZACION.toString());
					}

					if (pos_numero_identificacion == -1) {
						builder.append("\n * "
								+ ECabeceras.NUMERO_IDENTIFICACION.toString());
					}

					if (pos_resultado_4505 == -1) {
						builder.append("\n * "
								+ ECabeceras.RESULTADO_4505.toString());
					}

					// verificamos
					if (builder.toString().isEmpty()) {
						onImportar(hssfSheet, new IOnFinalizar() {
							@Override
							public void onFinalizar(
									List<Map<Long, Object>> log_error,
									long cantidad_importado, long cantidad_error) {
								MensajesUtil.mensajeInformacion("Informacion",
										"Datos importador satisfactoriamente total "
												+ cantidad_importado);
								if (!log_error.isEmpty()) {
									mostrarErrores(log_error);
								}
							}
						});
					} else {
						MensajesUtil.mensajeAlerta("Advertencia",
								"Para realizar la importacion, se necesitan los siguientes campos obligatorios"
										+ builder.toString());
					}
				}
			} else {
				MensajesUtil.mensajeAlerta("Advertencia",
						"No se ha cargado ningun archivo");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	/**
	 * Este metodo me permite importar los laboratorio a la 4505
	 * */
	public void onMontar(Media media) {
		try {
			if (media != null) {
				if (media.getFormat().endsWith(FORMATO_XLS)
						|| media.getFormat().endsWith(FORMATO_XLSX)) {
					fsFileSystem = new POIFSFileSystem(media.getStreamData());
					tbxDireccion.setValue("" + media.getName());
				} else {
					MensajesUtil.mensajeAlerta("Advertencia",
							"Archivo no valido");
				}
			} else {
				MensajesUtil.mensajeAlerta("Advertencia",
						"formato no valido debe ser " + FORMATO_XLS + " o "
								+ FORMATO_XLSX);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	/*
	 * 
	 * */
	protected void mostrarErrores(List<Map<Long, Object>> log_error) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map<Long, Object> map : log_error) {
			stringBuilder.append(map).append("\n");
		}
		Filedownload
				.save(stringBuilder.toString(), "text/plain", "errores.txt");
	}

	private void onImportar(HSSFSheet hssfSheet, IOnFinalizar onFinalizar)
			throws Exception {
		log.info("Ejecutando metodo @onImportar");
		List<Map<Long, Object>> log_errores = new ArrayList<Map<Long, Object>>();

		Iterator<Row> rowIterator = hssfSheet.rowIterator();
		rowIterator.next();// quitar la cabecera
		long pos_row = 0L;
		long con_impor = 0;
		long con_error = 0;
		while (rowIterator.hasNext()) {
			log.info("pos_row ===> " + pos_row);
			HSSFRow hssfRow = (HSSFRow) rowIterator.next();
			pos_row++;
			Map<Long, Object> map_error = null;
			Resultado_laboratorios resultado_laboratorios = new Resultado_laboratorios();

			HSSFCell hssfCell_codigo_pcd = hssfRow
					.getCell(pos_codigo_procedimiento);
			HSSFCell hssfCell_fecha_pcd = hssfRow
					.getCell(pos_fecha_realizacion);
			HSSFCell hssfCell_numero_id = hssfRow
					.getCell(pos_numero_identificacion);
			HSSFCell hssfCell_resultado = hssfRow.getCell(pos_resultado_4505);

			// validamos los codigos de los procedimientos
			if (hssfCell_codigo_pcd != null) {
				String codigo_cups = hssfCell_codigo_pcd.toString();
				if (codigo_cups.length() == 6) {
					resultado_laboratorios.setCodigo_cups(codigo_cups);
				} else {
					map_error = new HashMap<Long, Object>();
					map_error.put(pos_row, new StringBuilder(
							"* El campo código procedimientos no es valido "
									+ codigo_cups));
				}
			} else {
				map_error = new HashMap<Long, Object>();
				map_error.put(pos_row, new StringBuilder(
						"* El campo código procedimientos no puede ser nulo"));
			}

			// validamos fecha de realizacion
			if (hssfCell_fecha_pcd != null) {
				String fecha_realizacion = hssfCell_fecha_pcd.toString();
				if (fecha_realizacion.matches("[0-9]{4}[-][0-9]{2}[-][0-9]{2}")) {
					resultado_laboratorios.setFecha_resultado(Timestamp
							.valueOf(fecha_realizacion + " 00:00:00"));
				} else {
					if (map_error == null) {
						map_error = new HashMap<Long, Object>();
					}
					map_error
							.put(pos_row,
									new StringBuilder(
											"* El campo fecha de realizacion, formato no valido el formato debe ser yyyy-MM-dd"));
				}
			} else {
				if (map_error == null) {
					map_error = new HashMap<Long, Object>();
				}
				map_error.put(pos_row, new StringBuilder(
						"* El campo fecha de realizacion no puede ser nula"));
			}

			// numero de identificacion
			if (hssfCell_numero_id != null) {
				String numero_id = hssfCell_numero_id.toString();
				if (numero_id.trim().isEmpty()) {
					if (map_error == null) {
						map_error = new HashMap<Long, Object>();
					}
					map_error.put(pos_row, new StringBuilder(
							"* El campo número de identificacion del paciente no puede estar vacio "
									+ numero_id));
				} else {
					resultado_laboratorios.setNro_identificacion(numero_id);
				}
			} else {
				if (map_error == null) {
					map_error = new HashMap<Long, Object>();
				}
				map_error
						.put(pos_row,
								new StringBuilder(
										"* El campo número de identificacion del paciente no puede ser nulo"));
			}

			// resultado laboratorio
			if (hssfCell_resultado != null) {
				String resultado = hssfCell_resultado.toString();
				if (resultado.trim().isEmpty()
						&& !(resultado_laboratorios.getCodigo_cups().equals(
								"903815") || resultado_laboratorios
								.getCodigo_cups().equals("902213"))) {
					if (map_error == null) {
						map_error = new HashMap<Long, Object>();
					}
					map_error.put(pos_row, new StringBuilder(
							"* El campo resultado no puede estar vacio "
									+ resultado));
				} else {
					resultado = resultado.replace(",", ".");
					resultado_laboratorios.setCodigo_respuesta(resultado);
				}
			} else {
				if (map_error == null) {
					map_error = new HashMap<Long, Object>();
				}
				map_error.put(pos_row, new StringBuilder(
						"* El campo resultado no puede ser nulo"));
			}

			if (map_error == null) {
				resultado_laboratorios.setCodigo_empresa(codigo_empresa);
				resultado_laboratorios.setCodigo_sucursal(codigo_sucursal);
				resultado_laboratorios.setCreacion_date(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				resultado_laboratorios.setCreacion_user(getUsuarios()
						.getCodigo());
				resultado_laboratorios
						.setUltimo_user(getUsuarios().getCodigo());
				resultado_laboratorios.setUltimo_update(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				resultado_laboratorios.setCodigo_item(resultado_laboratorios
						.getCodigo_cups());
				resultado_laboratorios.setValor_resultado("");
				resultado_laboratorios.setValor_referencia_inicial("");
				resultado_laboratorios.setValor_referencia_final("");
				resultado_laboratorios.setVisto("N");
				resultado_laboratorios.setUnidad_medida("");
				resultado_laboratorios.setDescripcion_resultado("");
				resultado_laboratorios.setCodigo_diagnostico("");
				resultado_laboratorios.setObservaciones("");
				resultado_laboratorios.setCodigo_orden("");

				// creamos registro
				resultado_laboratoriosService
						.crearImportado(resultado_laboratorios);
				con_impor++;
			} else {
				log_errores.add(map_error);
				con_error++;
			}
		}
		onFinalizar.onFinalizar(log_errores, con_impor, con_error);
	}

	private interface IOnFinalizar {
		void onFinalizar(List<Map<Long, Object>> log_error,
				long cantidad_importado, long cantidad_error);
	}

	/**
	 * Este metodo me permite verificar si una columna se encuentra referenciada
	 * como titulo
	 * 
	 * @author Luis Miguel
	 * */
	private static int existeCabecera(HSSFRow hssfRow, String nombre) {
		for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
			HSSFCell hssfCell = hssfRow.getCell(i);
			if (hssfCell != null && hssfCell.toString().equals(nombre)) {
				return i;
			}
		}
		return -1;
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

		file = new File(file.getAbsolutePath() + "/ImpLab"
				+ empresa.getCodigo_empresa() + ""
				+ sucursal.getCodigo_sucursal());
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	/**
	 * Este metodo me permite generar un archivo .xls con las columnas de
	 * necesarias para importar los laboratorios
	 * 
	 * @author Luis Miguel Hernandez Perez
	 * */
	public void onGenerarArchivoFormatoImportacion() throws Exception {
		Workbook libroMetasMatriz = new HSSFWorkbook();
		File directorioGuardado = getDirectorio();

		String fecha_archivo = new SimpleDateFormat("yyyyMMddhhmmss")
				.format(Calendar.getInstance().getTime());

		String nombre_archivo = "importar_lab_" + fecha_archivo + ".xls";
		String url_file = directorioGuardado.getAbsolutePath() + File.separator
				+ nombre_archivo;

		Sheet hoja1 = libroMetasMatriz.createSheet("laboratorios");
		hoja1.createFreezePane(0, 1);

		// estilo de filas
		org.apache.poi.ss.usermodel.Row filaCol = hoja1.createRow(0);
		HSSFCellStyle style = (HSSFCellStyle) libroMetasMatriz
				.createCellStyle();
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);

		PoiUtil.agregarCelda(filaCol, 0,
				ECabeceras.CODIGO_PROCEDIMIENTO.toString(),
				ECabeceras.CODIGO_PROCEDIMIENTO.getObservacion(), style);
		PoiUtil.agregarCelda(filaCol, 1,
				ECabeceras.FECHA_REALIZACION.toString(),
				ECabeceras.FECHA_REALIZACION.getObservacion(), style);
		PoiUtil.agregarCelda(filaCol, 2,
				ECabeceras.NUMERO_IDENTIFICACION.toString(),
				ECabeceras.NUMERO_IDENTIFICACION.getObservacion(), style);
		PoiUtil.agregarCelda(filaCol, 3, ECabeceras.RESULTADO_4505.toString(),
				ECabeceras.RESULTADO_4505.getObservacion(), style);

		hoja1.autoSizeColumn((short) 0);
		hoja1.autoSizeColumn((short) 1);
		hoja1.autoSizeColumn((short) 2);
		hoja1.autoSizeColumn((short) 3);

		File archivo_excel = new File(url_file);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(archivo_excel);
			libroMetasMatriz.write(fos);
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

		ByteArrayOutputStream theBAOS = new ByteArrayOutputStream();
		FileInputStream theFIS = null;
		byte[] fileContent = null;
		try {
			theBAOS.reset();
			theFIS = new FileInputStream(archivo_excel);
			BufferedInputStream theBIS = new BufferedInputStream(theFIS);
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = theBIS.read(buffer)) >= 0) {
				theBAOS.write(buffer, 0, bytesRead);
			}
			theBAOS.flush();
			fileContent = theBAOS.toByteArray();
			theBIS.close();
		} finally {
			if (theBAOS != null) {
				theBAOS.reset();
			}
			if (theFIS != null) {
				try {
					theFIS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Filedownload.save(fileContent, "xls", nombre_archivo);
		archivo_excel.delete();
	}
}
