package healthmanager.controller;

import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.Afiliaciones_meService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class ActualizarCobrosAfiliadosAction extends ZKWindow implements
		WindowRegistrosIMG{ 
	
	public static final String AFILIADOS = "PAC";
	private List<String> lista_seleccionados_pacientes = new ArrayList<String>();
	private List<Map<String, Object>> lista_datos_pacientes = new ArrayList<Map<String,Object>>();
	
	private Afiliaciones_meService afiliaciones_meService;
	private final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
	
	@View private Listbox lbxAnios;
	@View private Listbox listboxMeses;
	@View private Checkbox chk_paciente;
	@View private Checkbox chk_valores_cobro_afiliado;
	@View private Rows rowPaciente;

	@Override
	public void init() throws Exception {
      inicializarAnio(); 
	}

	private void inicializarAnio() {
		Utilidades.inicializarAnio(lbxAnios);
		Utilidades.listarMeses(listboxMeses); 
	}
	
	public void actualizarCobros(){
         try {
			if(validarForm()){ 
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(Afiliaciones_meService.PARAM_SUCURSAL, getSucursal());
				params.put(Afiliaciones_meService.PARAM_ULTIMO_APORTE, chk_valores_cobro_afiliado.isChecked());
				params.put(Afiliaciones_meService.PARAM_TODOS_LOS_PACIENTES, chk_paciente.isChecked());
				params.put(Afiliaciones_meService.PARAM_ANIO, lbxAnios.getSelectedItem().getValue());
				params.put(Afiliaciones_meService.PARAM_MES, listboxMeses.getSelectedItem().getValue());
				params.put(Afiliaciones_meService.PARAM_PACIENTES, getPacientes());
				params.put(Afiliaciones_meService.PARAM_SERVICE_LOCATOR, getServiceLocator());
				final List<Map<String, Object>> resultado_actualizacion_cobros = afiliaciones_meService.actualizarCobros(params);
				
				if(!resultado_actualizacion_cobros.isEmpty()){
					Messagebox.show(
							"La actualizacion de cobros ha sido un exito.¿Deseas ver los valores?",
							"Informacion de cobros de Afiliados", Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event event) throws Exception {
									if ("onYes".equals(event.getName())) {
										generarExcel(resultado_actualizacion_cobros); 
									}
								}
							});
				}else{
					MensajesUtil.mensajeAlerta("Advertencia", "El sistema no encontro informacion de afilaidos");
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this); 
		}		
	}
	
	private List<Afiliaciones_me> getPacientes() {  
		List<Afiliaciones_me> afiliados = new ArrayList<Afiliaciones_me>();
		for (Map<String, Object> map_paciente : lista_datos_pacientes) {
			afiliados.add((Afiliaciones_me)map_paciente.get(AFILIADOS));
		} 
		return afiliados;
	}

	private boolean validarForm() {
		boolean valida = true;
		String msj = "";
		
		if (!chk_paciente.isChecked() && rowPaciente.getChildren().isEmpty()) {
			msj = "Para realizar esta accion es obligatorio seleccionar por lo menos un afiliado";
			valida = false;
		}
		
		if(!valida){
			MensajesUtil.mensajeAlerta("Advertencia", msj); 
		}
		
		return valida;
	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			if (registro instanceof Afiliaciones_me) {
				onSeleccionarPaciente((Afiliaciones_me) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if(registro instanceof Afiliaciones_me){
			return celdasListitemPacientes((Afiliaciones_me)registro);
		}
		return null;
	}
	
	private void onSeleccionarPaciente(Afiliaciones_me registro) {
		adicionarDetalleListaPaciente(cargarPaciente(registro)); 
	}
	  
	private void adicionarDetalleListaPaciente(final Map<String, Object> registro) {
		try {
			crearFilasPaciente(registro);  
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasPaciente(final Map<String, Object> registro) {
		final Row row = new Row();
		lista_datos_pacientes.add(registro);
		Afiliaciones_me afiliaciones_me = (Afiliaciones_me) registro.get(AFILIADOS);
		Paciente paciente = afiliaciones_me.getPaciente();
		
		row.setValue(paciente);
		
		Cell cell = new Cell();
		Label label = new Label("" + paciente.getTipo_identificacion());
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + paciente.getNro_identificacion());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + paciente.getNombre1() + " " + paciente.getNombre2());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + paciente.getApellido1() + " " + paciente.getApellido2());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);
		
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif"); 
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				 Messagebox.show("¿Estas seguro que deseas eliminar estos registros?", "Advertencia", Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION, new EventListener<Event>() {
								@Override
								public void onEvent(Event event) throws Exception {
									if("onYes".equals(event.getName())){
									     lista_datos_pacientes.remove(registro);
										 rowPaciente.removeChild(row);
									}
								}
							});
			}
		});
		rowPaciente.appendChild(row); 
	}

	private Map<String, Object> cargarPaciente(Afiliaciones_me registro) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(AFILIADOS, registro);
		return bean;
	}

	private Object[] celdasListitemPacientes(Afiliaciones_me registro) {
		Paciente paciente = registro.getPaciente();
		return new Object[]{paciente.getTipo_identificacion(), paciente.getDocumento(), paciente.getNombre1() + " " + paciente.getNombre2(), paciente.getApellido1() + " " + paciente.getApellido2()};
	}

	public void abrirWindowPaciente() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String columnas = "Tipo ID#65px|identificacion#170px|Nombre|Apellido";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccione el o los Pacientes", Paquetes.HEALTHMANAGER,
				"Afiliaciones_meDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_pacientes);
	}
	
	
	// Generacion de excel
	private void generarExcel(
			List<Map<String, Object>> listado_cobros_actualizados) throws Exception{
		Workbook libroMetasMatriz = new HSSFWorkbook();
		String fecha_archivo = new SimpleDateFormat("yyyyMMddhhmmss")
		.format(Calendar.getInstance().getTime());
		
		// Directorio de guardado
		File directorioGuardado = getDirectorio();
		
		String nombre_archivo = "cobros_afiliados_" + fecha_archivo + ".xls";
		String url_file = directorioGuardado.getAbsolutePath() + File.separator
				+ nombre_archivo;
		
		Sheet hoja1 = libroMetasMatriz.createSheet("Aportes");
		
		// Cargamos estilos
				// cargamos los estilos.
				HSSFFont fontBold = (HSSFFont) libroMetasMatriz.createFont();
				fontBold.setFontName(HSSFFont.FONT_ARIAL);
				fontBold.setFontHeightInPoints((short) 10);
				fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				fontBold.setColor(HSSFColor.WHITE.index);

				HSSFFont fontBoldTitulo = (HSSFFont) libroMetasMatriz.createFont();
				fontBoldTitulo.setFontName(HSSFFont.FONT_ARIAL);
				fontBoldTitulo.setFontHeightInPoints((short) 10);
				fontBoldTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				fontBoldTitulo.setColor(HSSFColor.BLACK.index);

				HSSFFont headerFont = (HSSFFont) libroMetasMatriz.createFont();
				headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				// headerFont.setColor(IndexedColors.WHITE.index);
				fontBold.setFontName(HSSFFont.FONT_ARIAL);
				headerFont.setFontHeightInPoints((short) 9);

				Font fontNormal = libroMetasMatriz.createFont();
				fontNormal.setFontName(HSSFFont.FONT_ARIAL);
				fontNormal.setFontHeightInPoints((short) 10);

				DataFormat format = libroMetasMatriz.createDataFormat();

				CellStyle styleInforme = libroMetasMatriz.createCellStyle();
				styleInforme.setFont(fontNormal);
				styleInforme.setAlignment(CellStyle.ALIGN_CENTER);

				CellStyle styleNomFecha = libroMetasMatriz.createCellStyle();
				styleNomFecha.setFont(fontBold);
				styleNomFecha.setAlignment(CellStyle.ALIGN_CENTER);

				CellStyle styleFecha = libroMetasMatriz.createCellStyle();
				styleFecha.setFont(fontBold);
				styleFecha.setDataFormat(format.getFormat("dd/mm/yyyy"));

				CellStyle styleHeader = libroMetasMatriz.createCellStyle();
				// styleHeader.setFont(headerFont);
				styleHeader.setBorderTop(CellStyle.BORDER_THIN);
				styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
				styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
				styleHeader.setBorderRight(CellStyle.BORDER_THIN);

				CellStyle styleTexto = libroMetasMatriz.createCellStyle();
				styleTexto.setFont(fontNormal);
				styleTexto.setDataFormat((short) BuiltinFormats
						.getBuiltinFormat("text"));
				styleTexto.setBorderTop(CellStyle.BORDER_THIN);
				styleTexto.setBorderBottom(CellStyle.BORDER_THIN);
				styleTexto.setBorderLeft(CellStyle.BORDER_THIN);
				styleTexto.setBorderRight(CellStyle.BORDER_THIN);

				CellStyle styleTextoWrap = libroMetasMatriz.createCellStyle();
				styleTextoWrap.setFont(fontNormal);
				styleTextoWrap.setDataFormat((short) BuiltinFormats
						.getBuiltinFormat("text"));
				styleTextoWrap.setBorderTop(CellStyle.BORDER_THIN);
				styleTextoWrap.setBorderBottom(CellStyle.BORDER_THIN);
				styleTextoWrap.setBorderLeft(CellStyle.BORDER_THIN);
				styleTextoWrap.setBorderRight(CellStyle.BORDER_THIN);
				styleTextoWrap.setWrapText(true);

				CellStyle styleFecha2 = libroMetasMatriz.createCellStyle();
				styleFecha2.setFont(fontNormal);
				styleFecha2.setDataFormat(format.getFormat("yyyy-MM-dd"));
				styleFecha2.setBorderTop(CellStyle.BORDER_THIN);
				styleFecha2.setBorderBottom(CellStyle.BORDER_THIN);
				styleFecha2.setBorderLeft(CellStyle.BORDER_THIN);
				styleFecha2.setBorderRight(CellStyle.BORDER_THIN);

				// agregamos cabecera
				CellStyle styleNombre_empresa = libroMetasMatriz.createCellStyle();
				styleNombre_empresa.setFont(fontBoldTitulo);
				styleNombre_empresa.setAlignment(CellStyle.ALIGN_CENTER);

				HSSFCellStyle styleNombre_meses = (HSSFCellStyle) libroMetasMatriz
						.createCellStyle();
				styleNombre_meses.setAlignment(CellStyle.ALIGN_CENTER);
				styleNombre_meses.setBorderTop(CellStyle.BORDER_THIN);
				styleNombre_meses.setBorderBottom(CellStyle.BORDER_THIN);
				styleNombre_meses.setBorderLeft(CellStyle.BORDER_THIN);
				styleNombre_meses.setBorderRight(CellStyle.BORDER_THIN);
				styleNombre_meses.setRightBorderColor(HSSFColor.WHITE.index);
				styleNombre_meses.setLeftBorderColor(HSSFColor.WHITE.index);
				styleNombre_meses.setFillBackgroundColor(IndexedColors.SEA_GREEN
						.getIndex());
				styleNombre_meses.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				styleNombre_meses.setFont(fontBold);

				CellStyle styleNombreAreaFuncional = libroMetasMatriz.createCellStyle();
				styleNombreAreaFuncional.setFillPattern(CellStyle.SOLID_FOREGROUND);
				styleNombreAreaFuncional
						.setFillBackgroundColor(HSSFColor.SEA_GREEN.index);
				styleNombreAreaFuncional.setFont(fontBold);

				CellStyle styleNormal = libroMetasMatriz.createCellStyle();
				styleNormal.setFont(fontNormal);

				// estylo de prueba

				HSSFCellStyle style = (HSSFCellStyle) libroMetasMatriz
						.createCellStyle();
				// style.setBorderTop((short) 6); // double lines border
				// style.setBorderBottom((short) 1); // single line border
				// style.setFillPattern(HSSFCellStyle.DIAMONDS);
				// style.setFillBackgroundColor(HSSFColor.RED.index);
				style.setBorderTop(CellStyle.BORDER_THIN);
				style.setBorderBottom(CellStyle.BORDER_THIN);
				style.setBorderLeft(CellStyle.BORDER_THIN);
				style.setBorderRight(CellStyle.BORDER_THIN);
				styleNombre_meses.setRightBorderColor(HSSFColor.WHITE.index);
				styleNombre_meses.setLeftBorderColor(HSSFColor.WHITE.index);
				style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontBold);
				
				
				int row = 0;
				hoja1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
				org.apache.poi.ss.usermodel.Row filaEmpresa = hoja1.createRow(row++);
				org.apache.poi.ss.usermodel.Cell celda = filaEmpresa.createCell(0,
						org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
				celda.setCellValue("COBROS DE AFILIADOS");
				celda.setCellStyle(styleNombre_empresa);
				
				
				int initCol = 0;
				org.apache.poi.ss.usermodel.Row filaCol = hoja1.createRow(row++);

				// creamos columnas
				agregarCelda(filaCol, initCol++, "IDENTIFICACIÓN AFILIADO", style);
				agregarCelda(filaCol, initCol++, "AFILIADO", style);
				agregarCelda(filaCol, initCol++, "CUOTA MODERADORA ANTORIO", style);
				agregarCelda(filaCol, initCol++, "PORCENTAJE COPAGO ANTERIOR", style);
				agregarCelda(filaCol, initCol++, "CUOTA MODERADORA ACTUAL", style);
				agregarCelda(filaCol, initCol++, "PORCENTAJE COPAGO ACTUAL", style);
				agregarCelda(filaCol, initCol++, "OBSERVACIÓN", style);
				
				// listamos contenido
				int total_columnas = initCol;
				for (Map<String, Object> mapa : listado_cobros_actualizados) {
					// Obtenemos datos de mapa
					crearFila(mapa, 0, // Especifica desde donde empieza la columna
							hoja1.createRow(row++), styleHeader);
				}

				// actualizamos las columnas
				// autosize columnas
				for (int i = 0; i < total_columnas; i++) {
					hoja1.autoSizeColumn((short) i);
				}
				
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

				// generamos archivo
				byte[] fileContent = null;
				ByteArrayOutputStream theBAOS = new ByteArrayOutputStream();
				FileInputStream theFIS = null;
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
				// Descargamos archivo
				Filedownload.save(fileContent, "xls", nombre_archivo);
				archivo_excel.delete();
	}
	
	private int crearFila(Map<String, Object> mapa, int initCol,
			org.apache.poi.ss.usermodel.Row filaCart, CellStyle styleHeader) {
		
		
		Afiliaciones_me  afiliaciones_me = (Afiliaciones_me) mapa.get(Afiliaciones_meService.AFILIACIONES);
		double cuota_moderadora_anterior = (Double) mapa.get(Afiliaciones_meService.PARAM_CUOTA_MODERADORA_ANTERIOR);
		double pocentaje_copago_anterior = (Double) mapa.get(Afiliaciones_meService.PARAM_PORCENTAJE_COPAGO_ANTERIOR);
		
		double cuota_moderadora = (Double) mapa.get(Afiliaciones_meService.PARAM_CUOTA_MODERADORA);
		double pocentaje_copago = (Double) mapa.get(Afiliaciones_meService.PARAM_PORCENTAJE_COPAGO);
		String observacion = (String) mapa.get(Afiliaciones_meService.PARAM_OBSERVACIONES);
		
		// cargamos datos en el reporte
		agregarCelda(filaCart, initCol++, afiliaciones_me.getPaciente().getDocumento() + "",
				styleHeader);
		agregarCelda(filaCart, initCol++, afiliaciones_me.getPaciente().getNombreCompleto() + "",
				styleHeader);
		agregarCelda(filaCart, initCol++,
				decimalFormat.format(cuota_moderadora_anterior)  + "", styleHeader);
		
		agregarCelda(filaCart, initCol++,
				decimalFormat.format(pocentaje_copago_anterior) + "", styleHeader);
		
		agregarCelda(filaCart, initCol++,
				decimalFormat.format(cuota_moderadora)  + "", styleHeader);
		
		agregarCelda(filaCart, initCol++,
				decimalFormat.format(pocentaje_copago) + "", styleHeader);
		
		agregarCelda(filaCart, initCol++, observacion + "", styleHeader);

		return initCol;
	}
	
	private void agregarCelda(org.apache.poi.ss.usermodel.Row filaCol,
			int initCol, String nombre, CellStyle styleHeader) {
		org.apache.poi.ss.usermodel.Cell celda = filaCol.createCell(initCol,
				org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
		celda.setCellValue("" + nombre);
		celda.setCellStyle(styleHeader);
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

		file = new File(file.getAbsolutePath() + "/ErroresAporte"
				+ getSucursal().getCodigo_empresa() + ""
				+ getSucursal().getCodigo_sucursal());
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}
	
	public void imprimirAfiliadosSinAportes(){
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "AfiliadosSinCotizaciones"); 
		if(!chk_valores_cobro_afiliado.isChecked()){
			paramRequest.put("anio", lbxAnios.getSelectedItem().getValue().toString()); 
			paramRequest.put("mes", listboxMeses.getSelectedItem().getValue().toString()); 
		}
		Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window)componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
}
