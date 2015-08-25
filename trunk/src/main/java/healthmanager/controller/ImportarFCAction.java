package healthmanager.controller;

import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.service.Cie_epidemiologiaService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.util.media.Media;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

import com.framework.util.MensajesUtil;
import healthmanager.modelo.service.GeneralExtraService;

public class ImportarFCAction extends ZKWindow {

	private Media file;

	@View
	private Button buttonEliminar;
	@View
	private Button buttonAdjuntar;
	@View
	private Label labelArchivo_adjunto;
	@View
	private Button buttonImportar;
	@View
	private Vlayout vlayoutResultados;

	@Override
	public void init() throws Exception {

	}

	public void adjuntarArchivo(Media media) throws Exception {
		try {

			if (!(media.getFormat().endsWith("xls") || media.getFormat()
					.endsWith("xlsx"))) {
				throw new Exception(
						"El importador de pacientes solo soporta formatos xls o xlsx");
			}

			if (file != null) {
				throw new Exception("El archivo ya fue cargado");
			}

			buttonAdjuntar.setVisible(false);
			buttonEliminar.setVisible(true);
			buttonImportar.setDisabled(false);

			labelArchivo_adjunto.setValue(media.getName());

			file = media;

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarArchivo() {
		labelArchivo_adjunto.setValue("");
		buttonAdjuntar.setVisible(true);
		buttonEliminar.setVisible(false);
		buttonImportar.setDisabled(true);
		file = null;
	}

	public void importarDatos() throws Exception {
		try {
			vlayoutResultados.getChildren().clear();
			if (file == null) {
				throw new Exception(
						"Debe cargar un archivo de excel para la importacion");
			}

			String formato = file.getFormat();

			int importados = 0;
			Map<String, String> mapa_noencontrados = new HashMap<String, String>();
			StringBuffer stringBuffer = new StringBuffer();

			Workbook workbook;
			if (formato.endsWith("xls")) {
				workbook = new HSSFWorkbook(file.getStreamData());
			} else {
				workbook = new XSSFWorkbook(file.getStreamData());
			}

			final String COLUMNAS_ARCHIVO[] = { "codigo_empresa",
					"codigo_sucursal", "ficha", "cie", "detalle" };

			Sheet sheet = workbook.getSheetAt(0);

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

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				fila = sheet.getRow(i);
				String codigo_empresa = getValorCeldaTexto(
						fila.getCell(cols[0].getColumnIndex())).toUpperCase();
				String codigo_sucursal = getValorCeldaTexto(
						fila.getCell(cols[1].getColumnIndex())).toUpperCase();
				String codigo_ficha = getValorCeldaTexto(
						fila.getCell(cols[2].getColumnIndex())).toUpperCase();
				String codigo_cie = getValorCeldaTexto(
						fila.getCell(cols[3].getColumnIndex())).toUpperCase();
				String detalle = getValorCeldaTexto(
						fila.getCell(cols[4].getColumnIndex())).toUpperCase();

				List<String> listado_agregados = new ArrayList<String>();

				if (!detalle.isEmpty()) {
					if (detalle.contains("-")) {
						StringTokenizer stringTokenizer = new StringTokenizer(
								detalle, "-");
						int rango_menor = Integer.parseInt(stringTokenizer
								.nextToken());
						int rango_mayor = Integer.parseInt(stringTokenizer
								.nextToken());
						for (int j = rango_menor; j <= rango_mayor; j++) {
							listado_agregados.add(j + "");
						}
					} else if (detalle.contains(",")) {
						StringTokenizer stringTokenizer = new StringTokenizer(
								detalle, ",");
						while (stringTokenizer.hasMoreTokens()) {
							listado_agregados.add(stringTokenizer.nextToken());
						}
					}
				}

				if (listado_agregados.isEmpty()) {
					Cie cie = new Cie();
					cie.setCodigo(codigo_cie);

					cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

					if (cie != null) {
						Cie_epidemiologia cie_epidemiologia = new Cie_epidemiologia(
								codigo_empresa, codigo_sucursal, codigo_ficha,
								codigo_cie);
						if (getServiceLocator().getServicio(
								Cie_epidemiologiaService.class).consultar(
								cie_epidemiologia) == null) {
							getServiceLocator().getServicio(
									Cie_epidemiologiaService.class).crear(
									cie_epidemiologia);
							importados++;
						}
					} else {
						mapa_noencontrados.put(codigo_cie, codigo_cie);
					}
				} else {
					for (String codigo_agregado : listado_agregados) {
						Cie cie = new Cie();
						cie.setCodigo(codigo_cie + "" + codigo_agregado);

						cie = getServiceLocator().getServicio(GeneralExtraService.class)
								.consultar(cie);

						if (cie != null) {
							Cie_epidemiologia cie_epidemiologia = new Cie_epidemiologia(
									codigo_empresa, codigo_sucursal,
									codigo_ficha, codigo_cie + ""
											+ codigo_agregado);
							if (getServiceLocator().getServicio(
									Cie_epidemiologiaService.class).consultar(
									cie_epidemiologia) == null) {
								getServiceLocator().getServicio(
										Cie_epidemiologiaService.class).crear(
										cie_epidemiologia);
								importados++;
							}
						} else {
							mapa_noencontrados.put(codigo_cie + ""
									+ codigo_agregado, codigo_cie + ""
									+ codigo_agregado);
						}
					}
				}

			}

			if (!mapa_noencontrados.isEmpty()) {
				for (String key_mapa : mapa_noencontrados.keySet()) {
					stringBuffer.append("No aparece cie").append(key_mapa)
							.append("</br>");
				}
			}

			if (importados > 0) {
				mensaje("Importacion correcta se importaron " + importados
						+ " Registros", 1);

			} else {
				mensaje("ADVERTENCIA !!: No se importaron registros la hoja por que ya se encuentran registrados",
						3);
			}

			if (!stringBuffer.toString().isEmpty()) {

				mensaje("No se encuentraron " + mapa_noencontrados.size()
						+ " cie </br>" + stringBuffer.toString(), 1);
			}

		} catch (Exception e) {
			mensaje(e.getMessage(), 2);
			e.printStackTrace();
		}
	}

	private Cell buscarCelda(String valor, Row fila) {
		Cell celda = null;
		for (short i = fila.getFirstCellNum(); i < fila.getLastCellNum(); i++) {
			Cell celda_aux = fila.getCell(i);
			if (celda_aux == null)
				break;
			if (valor.equals(getValorCeldaTexto(celda_aux))) {
				celda = celda_aux;
				break;
			}
		}
		return celda;
	}

	private String getValorCeldaTexto(Cell celda) {
		String valor = "";
		switch (celda.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			valor = celda.getStringCellValue();
			break;

		case Cell.CELL_TYPE_FORMULA:
			valor = celda.getCellFormula();
			break;

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(celda)) {
				valor = celda.getDateCellValue().toString();
			} else {
				double valor_double = celda.getNumericCellValue();
				DecimalFormat formato = new DecimalFormat("#");
				valor = formato.format(valor_double);
			}
			break;

		case Cell.CELL_TYPE_BLANK:
			valor = "";
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			valor = Boolean.toString(celda.getBooleanCellValue());
			break;

		}
		return valor;
	}

	public void mensaje(String mensaje, int tipo) {
		String color = "";
		if (tipo == 1) {
			color = "blue";
		} else if (tipo == 2) {
			color = "red";
		} else if (tipo == 3) {
			color = "orange";
		}
		Hlayout hlayout = new Hlayout();
		Textbox textbox = new Textbox(mensaje);
		textbox.setWidth("700px");
		textbox.setRows(6);
		textbox.setReadonly(true);
		textbox.setStyle("background-color:transparent;color:red;border-color:transparent");
		hlayout.setStyle("color:" + color);

		// Label label = new Label(mensaje);
		if (tipo == 1 || tipo == 3) {
			Html html = new Html("<p>" + mensaje + "</p>");
			hlayout.appendChild(html);
		} else {
			hlayout.appendChild(textbox);
		}

		vlayoutResultados.appendChild(hlayout);

	}

}
