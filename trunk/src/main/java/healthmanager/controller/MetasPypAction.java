package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Metas_pyp;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.Metas_pypService;
import healthmanager.modelo.service.Plantilla_pypService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.L2HContraintDate;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

/**
 * Este clase me permite controlar la vista de Metas de PYP
 * @author Luis Miguel
 * */
public class MetasPypAction extends ZKWindow {
	/* Aqui van los parametros */
	/**
	 * Este es para el regimen del contrato.<br/>
	 * el parametro es de tipo String.
	 * 
	 * 1 - Contributivo
	 * 2 - subcidiado
	 * */
	public static final String REGIMEN_CONTRATO = "_RegCnt";
	
	/**
	 * Este es el modo conque va ha trabajar
	 * en modo:<br/>
	 *   1. MODO_REGISTRO<br/>
	 *   2. MODO_CONSULTA
	 * */
	public static final String MODO = "modo";
	
	public static final String MODO_REGISTRO = "m_reg";
	public static final String MODO_CONSULTA = "m_cons";
	
	/*
	 * Informacion del contrato 
	 * */
	public static final String ID_CONTRATO = "id_ct";
	public static final String CODIGO_ADMINISTRADORA = "c_am";
	
	
	public static final String METAS_PYP = "mPyp";
	
	
	public static final String PROGRAMAS_PYP_ACTIVOS = "pro_pyp";
	
	@View public Toolbarbutton btGuardar;
	
	@View private Borderlayout groupboxEditar;
	
	/**
	 * Esta es la instancia de
	 * */
	private EventoMetasPyp eventoMetasPyp;
	
	private Plantilla_pypService plantilla_pypService;
	
	private final String CONTIBUTIVO = "1";
//	private final String SUBCIDIADO = "2";
	
	public static final String PAGINA_METAS = "/pages/metas_pyp.zul";
	
	/* Parametros del mapa */
	private static final String CODIGO_PDC        = "codigo_pdc";
	private static final String NOMBRE_PCD        = "nombre_pcd";
	private static final String AREA_INTERVENCION = "area_intervencion";
	private static final String PORCENTAJE_CONT   = "porcentaje_cont";
	private static final String PORCENTAJE_SUB    = "porcentaje_sub";
//	private static final String ANIO    = "anio";
//	private static final String MES    = "mes";
	private static final String METAS   = "metas";
	private static final String CODIGO_AREA_INTERVENCION = "codigo_intervencion";
	
	private static final String FINALIDAD_CONSULTA = "finalidad";
	
	private final String[] idsExcluyentes = {""};
	
	
	@View(isMacro = true) private BandboxRegistrosMacro tbxCodigo_administradora;
	@View private Textbox tbxInfoAseguradora;
	@View private Toolbarbutton btnLimpiarAseguradora;
	@View private Listbox lbxContratos;
	
	@View private Toolbarbutton btImprimir;
	
	private List<Auxheader> auxheaders = new ArrayList<Auxheader>();
	private List<Column> columns = new ArrayList<Column>();
	
	@View private Auxhead auxMesesContarto;
	
	
	private List<Metas_pyp> metas_pyps;
	
	private String regimen = null;
	
	/* componentes */
	@View private Rows rows_metas;
	@View private Columns columnMetas;

	private List<Map<String, Object>> list_metas;
	private List<Map<String, Object>> list_valores_metas;

	private String id_contrato;
	private String codigo_administradora;
	
	@Override
	public void params(Map<String, Object> map) {
		regimen = (String) map.get(REGIMEN_CONTRATO);
		//log.info("Tipo de contrato: " + regimen); 
		id_contrato = (String) map.get(ID_CONTRATO);
		codigo_administradora = (String) map.get(CODIGO_ADMINISTRADORA); 
		list_valores_metas = (List<Map<String,Object>>) map.get(METAS_PYP);
		if(regimen == null){
			regimen = CONTIBUTIVO;
		}
	}

	@Override
	public void init() throws Exception {
		metas_pyps = new ArrayList<Metas_pyp>();
		inicializarListaPlantilla();
		cargarPlantillaPyp();
		inicializarBandox();
		cargarEventos();
	} 
	
	/**
	 * Este metodo me permite inicializar la plantilla de PYP
	 * @author Luis Miguel
	 * */
	private void inicializarListaPlantilla(){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id_contrato", id_contrato);
			params.put("codigo_administradora", codigo_administradora);
			params.put("codigo_empresa", codigo_empresa);
			params.put("codigo_sucursal", codigo_sucursal);
			list_metas = plantilla_pypService.listarMetasPyp(params); 
	}
	
	
	/**
	 * Este metodo me permite listar la plantilla PYP.
	 * @author Luis Miguel
	 * */
	private void cargarPlantillaPyp() { 
		rows_metas.getChildren().clear();
		for (Map<String, Object> map : list_metas) { 
			String codigo_pro = (String) map.get(CODIGO_PDC);
			String nombre_pcd = (String) map.get(NOMBRE_PCD);
			String area_intervencion = (String) map.get(AREA_INTERVENCION);
			Number porcentaje_cont = (Number) map.get(PORCENTAJE_CONT);
			Number porcentaje_sub = (Number) map.get(PORCENTAJE_SUB);
//			String metas = (String) map.get(METAS);
			
			Row row = new Row();
			
			row.appendChild(new Label(area_intervencion));
			row.appendChild(new Label(codigo_pro));
			row.appendChild(new Label(nombre_pcd));
			
			
			/* meses del contrato */
			if( lbxContratos.getSelectedItem() != null){
				/* Aqui verificamos las metas */
				Contratos contratos = lbxContratos.getSelectedItem().getValue();
				if(contratos != null){
					String regimen = contratos.getTipo_usuario();
					
					row.appendChild(new Label((regimen.equals("1") ? porcentaje_cont + "" : porcentaje_sub + "")));
					
					int meses =  Util.getDiferenciaEntreMeses(contratos.getFecha_inicio(), contratos.getFecha_fin());
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(contratos.getFecha_inicio());  
					calendar.set(Calendar.DAY_OF_MONTH, 1); 
					
					for (int i = 1; i <= meses; i++) {
						agregarCellMetas(calendar.getTime(), contratos, row, map);
						calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
					}
					// habilitamos la opcion de exportar esta informacion a excel
					if(meses > 0){
						btImprimir.setDisabled(false);
					}
				}
			}
            rows_metas.appendChild(row); 
		}
		getFellow("gridMetas").invalidate();
//		rows_metas.invalidate();
	}
	
	
	
	public void limpiar(){
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
		btGuardar.setDisabled(true); 
		metas_pyps.clear();
		tbxCodigo_administradora.setValue("");
		tbxInfoAseguradora.setValue("");
		listarContratos(null);
		limpiarPlantilla();
		cargarPlantillaPyp();
		btImprimir.setDisabled(true);
	}
	 
	private void agregarCellMetas(Date time, Contratos contratos, Row row, Map<String, Object> map) {
		Metas_pyp metas_pyp = new Metas_pyp();
		metas_pyp.setAnio("" +  L2HContraintDate.getAnio(time));
		
		int mes =  L2HContraintDate.getMes(time) + 1;
		
		metas_pyp.setMes(mes < 10 ? "0" + mes : mes+ ""); 
		metas_pyp.setCodigo_administradora(contratos.getCodigo_administradora());
		metas_pyp.setCodigo_empresa(contratos.getCodigo_empresa());
		metas_pyp.setCodigo_sucursal(contratos.getCodigo_sucursal());
		metas_pyp.setCodigo_pcd((String) map.get(CODIGO_PDC)); 
		metas_pyp.setId_plan(contratos.getId_plan()); 
		metas_pyp.setArea_intervencion((String)map.get(CODIGO_AREA_INTERVENCION)); 
//		//log.info("Metas a consulta:" + metas_pyp); 
		
		Timestamp fecha_inicio = L2HContraintDate.getAsInitOfMonth(time);
		Timestamp fecha_final = L2HContraintDate.getAsEndOfMonth(time);
		
		Metas_pypService metas_pypService = getServiceLocator().getServicio(Metas_pypService.class);
		metas_pyp =  metas_pypService.consultar(metas_pyp);
		
		Integer metas = null;
		/* metas */
		Intbox intbox = new Intbox();
		if(metas_pyp != null){
			if(metas_pyp.getMetas() != null ){
				if(!metas_pyp.getMetas().trim().isEmpty() && metas_pyp.getMetas().matches("[0-9]*$")) {
				    metas =  Integer.parseInt(metas_pyp.getMetas());
					intbox.setValue(metas);  
				}
			}
		}
		if(metas_pyp == null){
			metas_pyp = crearMeta(contratos, map, time); 
		}
		metas_pyps.add(metas_pyp);
		metodoAutomatico(intbox, metas_pyp);
		intbox.setWidth("70px");
		row.appendChild(intbox);
		
		
		String procedimiento = (String) map.get(CODIGO_PDC);
		String finalidad = (String) map.get(FINALIDAD_CONSULTA);
		Integer metas_realizadas = null;
		Double por_metas_realizadas = null;
		if(metas != null && metas != null){
			metas_realizadas = metas_pypService.getMetasRealizadas(metas_pyp, procedimiento, fecha_inicio, fecha_final, finalidad);
			if(metas_realizadas != null){
				  por_metas_realizadas = metas_realizadas.intValue() * 100D / metas.intValue(); 
			}
		}
		
		/* metas cumplidas */
		row.appendChild(new Label(metas_realizadas != null ? metas_realizadas.toString() : ""));
		
		Integer metas_por_cumplir = null;
		Double por_metas_por_realizar = null;
		if(metas != null && metas_realizadas != null){
			if(metas.intValue() > metas_realizadas.intValue()){
				metas_por_cumplir = metas.intValue() - metas_realizadas.intValue();
				por_metas_por_realizar = metas_por_cumplir.intValue() * 100D / metas.intValue();
			}
		}
		
		DecimalFormat decimalFormat = new DecimalFormat("#0.0#");
		
		/* metas por cumplir */
		row.appendChild(new Label(metas_por_cumplir != null ? metas_por_cumplir.toString() : ""));
		
		/* % metas realizadas */ 
		row.appendChild(new Label(por_metas_realizadas != null ? decimalFormat.format(por_metas_realizadas) + "%" : ""));
		
		/* % metas por realizar */
		row.appendChild(new Label(por_metas_por_realizar != null ? decimalFormat.format(por_metas_por_realizar) + "%" : ""));
	}
	
	
	private Metas_pyp crearMeta(Contratos contratos, Map<String, Object> map, Date fecha) {
		Metas_pyp metas_pyp = MetasPypAction.convertir(map); 
		metas_pyp.setCodigo_empresa(contratos.getCodigo_empresa());
		metas_pyp.setCodigo_sucursal(contratos.getCodigo_sucursal());
		metas_pyp.setCodigo_administradora(contratos.getCodigo_administradora());
		metas_pyp.setId_plan(contratos.getId_plan()); 
		
		metas_pyp.setCreacion_date(contratos.getCreacion_date());
		metas_pyp.setCreacion_user(contratos.getCreacion_user()); 
		

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		
		int mes =  calendar.get(Calendar.MONTH) + 1;
		
		metas_pyp.setMes(mes < 10 ? "0" + mes : mes+ ""); 
		metas_pyp.setAnio("" + calendar.get(Calendar.YEAR)); 
		metas_pyp.setUltimo_update(contratos.getUltimo_update());
		metas_pyp.setUltimo_user(contratos.getUltimo_user()); 
		return metas_pyp;
	}

	public void guardarMetas(){
		try {
			if (validarForm()) {
				// Cargamos los componentes //
				Messagebox.show("Esta seguro que deseas guardar esta malla de pyp?",
						"Guardar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									Contratos contratos = lbxContratos.getSelectedItem().getValue();
									getServiceLocator().getServicio(Metas_pypService.class).guardarMetas(metas_pyps, contratos);
									Notificaciones.mostrarNotificacionInformacion("Informacion", "Los datos se guardaron satisfactoriamente", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
									cargarMetasPypContrato(contratos); 
							  }
							}
						});
			}
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage()); 
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	
public boolean validarForm()throws Exception{
		
		tbxCodigo_administradora.setStyle("text-transform:uppercase;background-color:white");
		lbxContratos.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		String mensaje = "Los campos marcados con (*) son obligatorios";
		
		if(tbxCodigo_administradora.getValue().equals("")){
			tbxCodigo_administradora.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			Clients.scrollIntoView(tbxCodigo_administradora); 
		}
		
		if(valida && lbxContratos.getSelectedIndex() == 0){
			lbxContratos.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			Clients.scrollIntoView(lbxContratos); 
		}
		
		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}
		
		return valida;
	}
	
	
	private void metodoAutomatico(Intbox intbox, final Metas_pyp metas_pyp){
		intbox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Intbox intbox = (Intbox) arg0.getTarget();
				Integer meta = intbox.getValue();
				if(meta != null){
					metas_pyp.setMetas(meta.toString());
				}else{
					metas_pyp.setMetas(""); 
				}
			}
		});
	}

	/**
	 * Este metodo me permite imprimir la informacion
	 * en excel
	 * */
	public void imprimir(){
		try {
			Workbook libroMetasPyp = new HSSFWorkbook();
			/* 1ro listamos las metas de Pyp.*/

			
			// verificamos nombre del archivo 
			
			Calendar calendar = Calendar.getInstance();
		    String fecha_archivo = calendar.get(Calendar.YEAR) + ""
		                + calendar.get(Calendar.MONTH) + ""
		                + calendar.get(Calendar.DAY_OF_MONTH) + ""
		                + calendar.get(Calendar.HOUR) + ""
		                + calendar.get(Calendar.MINUTE) + ""
		                + calendar.get(Calendar.SECOND);
		    
		    File directorioGuardado = getDirectorio();
			
		    String nombre_archivo = "plantilla_pyp_"+fecha_archivo+".xls";
			String url_file = directorioGuardado.getAbsolutePath()  +  File.separator +  nombre_archivo;
			
			// creamos cabecera de libro 
			Sheet hoja1 = libroMetasPyp.createSheet("Plantilla metas PyP "); 
			
			// creamos frozen
			hoja1.createFreezePane(1, 8);
			
			// cargamos los estilos.
			HSSFFont fontBold = (HSSFFont)libroMetasPyp.createFont();
	        fontBold.setFontName(HSSFFont.FONT_ARIAL);
	        fontBold.setFontHeightInPoints((short) 10);
	        fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        fontBold.setColor(HSSFColor.WHITE.index);
	        
	        HSSFFont fontBoldTitulo = (HSSFFont)libroMetasPyp.createFont();
	        fontBoldTitulo.setFontName(HSSFFont.FONT_ARIAL);
	        fontBoldTitulo.setFontHeightInPoints((short) 10);
	        fontBoldTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        fontBoldTitulo.setColor(HSSFColor.BLACK.index);
			
			
	        HSSFFont headerFont = (HSSFFont) libroMetasPyp.createFont();
	        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        //headerFont.setColor(IndexedColors.WHITE.index);
	        fontBold.setFontName(HSSFFont.FONT_ARIAL);
	        headerFont.setFontHeightInPoints((short)9);
	        
	        Font fontNormal = libroMetasPyp.createFont();
	        fontNormal.setFontName(HSSFFont.FONT_ARIAL);
	        fontNormal.setFontHeightInPoints((short)10);
	        
	        
	        DataFormat format = libroMetasPyp.createDataFormat();
	
	        CellStyle styleInforme = libroMetasPyp.createCellStyle();
			styleInforme.setFont(fontNormal);
			styleInforme.setAlignment(CellStyle.ALIGN_CENTER);
			
			CellStyle styleNomFecha = libroMetasPyp.createCellStyle();
			styleNomFecha.setFont(fontBold);
			styleNomFecha.setAlignment(CellStyle.ALIGN_CENTER);
			
			CellStyle styleFecha = libroMetasPyp.createCellStyle();
			styleFecha.setFont(fontBold);
			styleFecha.setDataFormat(format.getFormat("dd/mm/yyyy"));
	        
	        CellStyle styleHeader = libroMetasPyp.createCellStyle();
			styleHeader.setFont(headerFont);
			styleHeader.setBorderTop(CellStyle.BORDER_THIN);
			styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
			styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
			styleHeader.setBorderRight(CellStyle.BORDER_THIN);
	
			CellStyle styleTexto = libroMetasPyp.createCellStyle();
			styleTexto.setFont(fontNormal);
			styleTexto.setDataFormat((short)BuiltinFormats.getBuiltinFormat("text"));
			styleTexto.setBorderTop(CellStyle.BORDER_THIN);
			styleTexto.setBorderBottom(CellStyle.BORDER_THIN);
			styleTexto.setBorderLeft(CellStyle.BORDER_THIN);
			styleTexto.setBorderRight(CellStyle.BORDER_THIN);
			
			CellStyle styleTextoWrap = libroMetasPyp.createCellStyle();
			styleTextoWrap.setFont(fontNormal);
			styleTextoWrap.setDataFormat((short)BuiltinFormats.getBuiltinFormat("text"));
			styleTextoWrap.setBorderTop(CellStyle.BORDER_THIN);
			styleTextoWrap.setBorderBottom(CellStyle.BORDER_THIN);
			styleTextoWrap.setBorderLeft(CellStyle.BORDER_THIN);
			styleTextoWrap.setBorderRight(CellStyle.BORDER_THIN);
			styleTextoWrap.setWrapText(true); 
			
			
			CellStyle styleFecha2 = libroMetasPyp.createCellStyle();
			styleFecha2.setFont(fontNormal);
			styleFecha2.setDataFormat(format.getFormat("yyyy-MM-dd"));
			styleFecha2.setBorderTop(CellStyle.BORDER_THIN);
			styleFecha2.setBorderBottom(CellStyle.BORDER_THIN);
			styleFecha2.setBorderLeft(CellStyle.BORDER_THIN);
			styleFecha2.setBorderRight(CellStyle.BORDER_THIN);
			
			 // agregamos cabecera 
	        CellStyle styleNombre_empresa = libroMetasPyp.createCellStyle();
			styleNombre_empresa.setFont(fontBoldTitulo);
			styleNombre_empresa.setAlignment(CellStyle.ALIGN_CENTER);
			
			
			HSSFCellStyle styleNombre_meses = (HSSFCellStyle) libroMetasPyp.createCellStyle();
			styleNombre_meses.setAlignment(CellStyle.ALIGN_CENTER);
			styleNombre_meses.setBorderTop(CellStyle.BORDER_THIN);
			styleNombre_meses.setBorderBottom(CellStyle.BORDER_THIN);
			styleNombre_meses.setBorderLeft(CellStyle.BORDER_THIN);
			styleNombre_meses.setBorderRight(CellStyle.BORDER_THIN);
			styleNombre_meses.setRightBorderColor(HSSFColor.WHITE.index);
			styleNombre_meses.setLeftBorderColor(HSSFColor.WHITE.index); 
			styleNombre_meses.setFillBackgroundColor(IndexedColors.SEA_GREEN.getIndex()); 
			styleNombre_meses.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			styleNombre_meses.setFont(fontBold);
			
			
			CellStyle styleNombreAreaFuncional = libroMetasPyp.createCellStyle();
			styleNombreAreaFuncional.setFillPattern(CellStyle.SOLID_FOREGROUND);
			styleNombreAreaFuncional.setFillBackgroundColor(HSSFColor.SEA_GREEN.index); 
			styleNombreAreaFuncional.setFont(fontBold);
			
			  CellStyle styleNormal = libroMetasPyp.createCellStyle();
			  styleNormal.setFont(fontNormal);
			
			
			// estylo de prueba
			
			    HSSFCellStyle style = (HSSFCellStyle)libroMetasPyp.createCellStyle();
//		        style.setBorderTop((short) 6); // double lines border
//		        style.setBorderBottom((short) 1); // single line border
//		        style.setFillPattern(HSSFCellStyle.DIAMONDS);
//		        style.setFillBackgroundColor(HSSFColor.RED.index);
			    styleNombre_meses.setRightBorderColor(HSSFColor.WHITE.index);
				styleNombre_meses.setLeftBorderColor(HSSFColor.WHITE.index); 
		        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		 
		        //
		        // We also define the font that we are going to use for displaying the
		        // data of the cell. We set the font to ARIAL with 20pt in size and
		        // make it BOLD and give blue as the color.
		        //
		        
		        style.setFont(fontBold);
			
			// cargamos titulos en plantillas
			int row = 0;
			org.apache.poi.ss.usermodel.Row filaEmpresa =  hoja1.createRow(row++);
			Cell celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
			celda.setCellValue("PLANTILLA METAS PYP"); 
			celda.setCellStyle(styleNombre_empresa);
			
			// nombre de la empresa
			filaEmpresa =  hoja1.createRow(row++);
			celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
			celda.setCellValue("" + empresa.getNombre_empresa().toUpperCase()); 
			celda.setCellStyle(styleNombre_empresa);
			
			// nit de la empresa
			filaEmpresa =  hoja1.createRow(row++);
			celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
			celda.setCellValue("NIT: " + empresa.getNro_identificacion()); 
			celda.setCellStyle(styleNombre_empresa);
			
			// direccion
		    String direccion = "";
		    Municipios municipios = new Municipios();
		    municipios.setCoddep(empresa.getCodigo_dpto());
		    municipios.setCodigo(empresa.getCodigo_municipio());
		    municipios = getServiceLocator().getMunicipiosService().consultar(municipios);
		    if(municipios != null){
		    	direccion = municipios.getNombre();
		    }
		    
		    Departamentos departamentos = new Departamentos();
		    departamentos.setCodigo(empresa.getCodigo_dpto());
		    departamentos = getServiceLocator().getServicio(DepartamentosService.class).consultar(departamentos);
		    if(departamentos != null){
		    	direccion += " " + departamentos.getNombre();
		    }
			
			
			filaEmpresa =  hoja1.createRow(row++);
			celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
			celda.setCellValue(direccion.toUpperCase()); 
			celda.setCellStyle(styleNombre_empresa);
			
			// CONSOLIDADO MENSUAL DE DETECCION TEMPRANA Y PROTECCION ESPECIFICA
			
			filaEmpresa =  hoja1.createRow(row++);
			celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
			celda.setCellValue("CONSOLIDADO MENSUAL DE DETECCION TEMPRANA Y PROTECCION ESPECIFICA"); 
			celda.setCellStyle(styleNombre_empresa);
			
			filaEmpresa =  hoja1.createRow(row++);
			celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
			celda.setCellValue("EPS: " + tbxInfoAseguradora.getText() + " - CONTRATO: " + lbxContratos.getSelectedItem().getLabel()); 
			celda.setCellStyle(styleNombre_empresa);
			
			
			org.apache.poi.ss.usermodel.Row filaColHeader =  hoja1.createRow(row++);
			int initColHeader = 0;
			
			
			// creamos columnas 
//			celda = filaColHeader.createCell(initColHeader, Cell.CELL_TYPE_STRING);celda.setCellValue("");initColHeader++;
//			celda = filaColHeader.createCell(initColHeader, Cell.CELL_TYPE_STRING);celda.setCellValue("");initColHeader++;
			celda = filaColHeader.createCell(initColHeader, Cell.CELL_TYPE_STRING);celda.setCellValue("");initColHeader++;
			
			
			// cargamos nueva linea 
            int initCol = 0;
            org.apache.poi.ss.usermodel.Row filaCol =  hoja1.createRow(row++);
			
			
			// creamos columnas 
			celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue("ACTIVIDADES");celda.setCellStyle(styleHeader);initCol++;
			
			
			// adicionamos las demas culumnas
			Contratos contratos = lbxContratos.getSelectedItem().getValue();
			int meses =  Util.getDiferenciaEntreMeses(contratos.getFecha_inicio(), contratos.getFecha_fin());
			
			calendar = Calendar.getInstance();
		calendar.setTime(contratos.getFecha_inicio());  
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMMMMMMMMMMM yyyy", IConstantes.locale);
		
		Calendar calendarFinal = Calendar.getInstance();
		calendarFinal.setTime(contratos.getFecha_inicio());  
		calendarFinal.set(Calendar.DAY_OF_MONTH, 1); 
		calendarFinal.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + (meses - 1)); 
		
			// cargamos titulo del reporte 
			for (int i = 1; i <= meses; i++) {
				// de las un colspan
				hoja1.addMergedRegion(new CellRangeAddress(6, 6, initColHeader, initColHeader + 4));
				celda = filaColHeader.createCell(initColHeader, Cell.CELL_TYPE_STRING);celda.setCellValue(new HSSFRichTextString(dateFormat.format(calendar.getTime()).toUpperCase()));celda.setCellStyle(style);initColHeader+=5;
				celda = filaColHeader.createCell(initColHeader, Cell.CELL_TYPE_STRING);celda.setCellValue("");celda.setCellStyle(styleHeader);initColHeader++;
				
				celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue("METAS");celda.setCellStyle(styleHeader);initCol++;
				celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue("CUMPLIDAS");celda.setCellStyle(styleHeader);initCol++;
				celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue("POR CUMPLIR");celda.setCellStyle(styleHeader);initCol++;
				celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue("% CUMPLIDO");celda.setCellStyle(styleHeader);initCol++;
				celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue("% POR CUMPLIR");celda.setCellStyle(styleHeader);initCol++;
				celda = filaCol.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue("");celda.setCellStyle(style);initCol++;
				
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			}
			
			
			// cargamos informacion de metas de PyP
			String area_intervancion_acarreo = "";
			for (Map<String, Object> map : list_metas) { 
//				String codigo_pro = (String) map.get(CODIGO_PDC);
				String nombre_pcd = (String) map.get(NOMBRE_PCD);
				String area_intervencion = (String) map.get(AREA_INTERVENCION);
//				Number porcentaje_cont = (Number) map.get(PORCENTAJE_CONT);
//				Number porcentaje_sub = (Number) map.get(PORCENTAJE_SUB);
//				String metas = (String) map.get(METAS);
				
				if(!area_intervancion_acarreo.equals(area_intervencion)){
					initCol = 0;
					org.apache.poi.ss.usermodel.Row filaCart =  hoja1.createRow(row++);
					celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue(area_intervencion);celda.setCellStyle(style);
					area_intervancion_acarreo = area_intervencion;
				}
				
				// creamos fila 
				org.apache.poi.ss.usermodel.Row filaCart =  hoja1.createRow(row++);
				initCol = 0;
				
				// agregamos a la lista 
//				celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue(area_intervencion);celda.setCellStyle(styleTexto);initCol++;
//				celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue(codigo_pro);celda.setCellStyle(styleTexto);initCol++;
				celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue(nombre_pcd);celda.setCellStyle(styleTexto);initCol++;
				
				
				// adicionamos los valores de las demas columnas
				if( lbxContratos.getSelectedItem() != null){
					/* Aqui verificamos las metas */
					if(contratos != null){
//						String regimen = contratos.getTipo_usuario();
						
//						String porcentaje = (regimen.equals("1") ? porcentaje_cont + "" : porcentaje_sub + "");
						
						calendar = Calendar.getInstance();
						calendar.setTime(contratos.getFecha_inicio());  
						calendar.set(Calendar.DAY_OF_MONTH, 1); 
						
						for (int i = 1; i <= meses; i++) {
							Metas_pyp metas_pyp = new Metas_pyp();
							metas_pyp.setAnio("" +  L2HContraintDate.getAnio(calendar.getTime()));
							
							int mes =  L2HContraintDate.getMes(calendar.getTime()) + 1;
							
							metas_pyp.setMes(mes < 10 ? "0" + mes : mes+ ""); 
							metas_pyp.setCodigo_administradora(contratos.getCodigo_administradora());
							metas_pyp.setCodigo_empresa(contratos.getCodigo_empresa());
							metas_pyp.setCodigo_sucursal(contratos.getCodigo_sucursal());
							metas_pyp.setCodigo_pcd((String) map.get(CODIGO_PDC)); 
							metas_pyp.setId_plan(contratos.getId_plan()); 
							metas_pyp.setArea_intervencion((String)map.get(CODIGO_AREA_INTERVENCION)); 
//							//log.info("Metas a consulta:" + metas_pyp); 
							
							Timestamp fecha_inicio = L2HContraintDate.getAsInitOfMonth(calendar.getTime());
							Timestamp fecha_final = L2HContraintDate.getAsEndOfMonth(calendar.getTime());
							
							Metas_pypService metas_pypService = getServiceLocator().getServicio(Metas_pypService.class);
							metas_pyp =  metas_pypService.consultar(metas_pyp);
							
							Integer metas = null;
							/* metas */
//							Intbox intbox = new Intbox();
							if(metas_pyp != null){
								if(metas_pyp.getMetas() != null ){
									if(!metas_pyp.getMetas().trim().isEmpty() && metas_pyp.getMetas().matches("[0-9]*$")) {
									    metas = Integer.parseInt(metas_pyp.getMetas());
									}
								}
							}
							celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue(metas == null ? "" : metas + "");celda.setCellStyle(styleTexto);initCol++;
							
							
							String procedimiento = (String) map.get(CODIGO_PDC);
							String finalidad = (String) map.get(FINALIDAD_CONSULTA);
							Integer metas_realizadas = null;
							Double por_metas_realizadas = null;
							if(metas != null && metas != null){
								metas_realizadas = metas_pypService.getMetasRealizadas(metas_pyp, procedimiento, fecha_inicio, fecha_final, finalidad);
								if(metas_realizadas != null){
									  por_metas_realizadas = metas_realizadas.intValue() * 100D / metas.intValue(); 
								}
							}
							
							/* metas cumplidas */
							celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue((metas_realizadas != null ? metas_realizadas.toString() : ""));celda.setCellStyle(styleTexto);initCol++;
							
							Integer metas_por_cumplir = null;
							Double por_metas_por_realizar = null;
							if(metas != null && metas_realizadas != null){
								if(metas.intValue() > metas_realizadas.intValue()){
									metas_por_cumplir = metas.intValue() - metas_realizadas.intValue();
									por_metas_por_realizar = metas_por_cumplir.intValue() * 100D / metas.intValue();
								}
							}
							
							DecimalFormat decimalFormat = new DecimalFormat("#0.0#");
							
							/* metas por cumplir */
							celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue((metas_por_cumplir != null ? metas_por_cumplir.toString() : ""));celda.setCellStyle(styleTexto);initCol++;
							
							/* % metas realizadas */ 
							celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue((por_metas_realizadas != null ? decimalFormat.format(por_metas_realizadas) + "%" : ""));celda.setCellStyle(styleTexto);initCol++;
							
							/* % metas por realizar */
							celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue((por_metas_por_realizar != null ? decimalFormat.format(por_metas_por_realizar) + "%" : ""));celda.setCellStyle(styleTexto);initCol++;
							
							celda = filaCart.createCell(initCol, Cell.CELL_TYPE_STRING);celda.setCellValue("");celda.setCellStyle(style);initCol++;
							calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
						}
					}
				}
			}
			
			// informacion de quien lo imprimio
			filaEmpresa =  hoja1.createRow(++row);
			celda = filaEmpresa.createCell(0, Cell.CELL_TYPE_STRING);
			celda.setCellValue("ELABORADO POR: " + usuarios.getNombres()  + " " + usuarios.getApellidos()); 
			celda.setCellStyle(styleNormal);
			
			
			// autosize columnas
			for (int i = 0; i < (meses * 6) + 1; i++) {
				hoja1.autoSizeColumn((short)i);
			}
			
			File archivo_excel = new File(url_file);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(archivo_excel);
				libroMetasPyp.write(fos);
				
				// auto size
				
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
		    
		    Filedownload.save(fileContent, "xls", nombre_archivo);
		    archivo_excel.delete();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this); 
		}
	}
	
	
	private File getDirectorio() {
		File file = new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath("") + "/Files");
        if (!file.exists()) { 
            file.mkdir();
        }
        
        file = new File(file.getAbsolutePath() + "/Temp");
        if (!file.exists()) { 
            file.mkdir();
        }
        
        file = new File(file.getAbsolutePath() + "/MetasPyp" + empresa.getCodigo_empresa()+""+sucursal.getCodigo_sucursal());
        if (!file.exists()) { 
            file.mkdir();
        }
		return file;
	}

	private void inicializarBandox() {
		parametrizarBandboxAdministradora();
	}

	private void parametrizarBandboxAdministradora() {
		tbxCodigo_administradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		tbxCodigo_administradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						((BandboxRegistrosMacro) bandbox).setObject(registro); 
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						listarContratos(registro); 
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
                         listarContratos(null);
                         limpiarPlantilla();
                         btGuardar.setDisabled(true);
					}
				});
	}

	protected void listarContratos(Administradora registro) {
		if(registro == null){
			lbxContratos.getItems().clear();
		}else{
		    Utilidades.listarContratosConValorObjeto(lbxContratos, registro.getCodigo(), true, true, codigo_empresa, codigo_sucursal, getServiceLocator());
		}
	}
	
	private void cargarEventos(){
		lbxContratos.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				metas_pyps.clear();
				Listbox listbox = (Listbox) arg0.getTarget();
				limpiarPlantilla();
				if(listbox.getSelectedIndex() > 0){
					Contratos contratos = listbox.getSelectedItem().getValue();
					cargarMetasPypContrato(contratos); 
					btGuardar.setDisabled(false);
 				}else{
					btGuardar.setDisabled(true);
					btImprimir.setDisabled(true); 
				}
			}
		});
		 
	} 
	
	protected void cargarMetasPypContrato(Contratos contratos) {
		limpiamosCabecera();
		cargarMesesContratados(contratos);
		cargarPlantillaPyp();
	}
 
	private void cargarMesesContratados(Contratos contratos) {
		int meses =  Util.getDiferenciaEntreMeses(contratos.getFecha_inicio(), contratos.getFecha_fin());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(contratos.getFecha_inicio());  
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMMMMMMMMMMM yyyy", IConstantes.locale);
		
		Calendar calendarFinal = Calendar.getInstance();
		calendarFinal.setTime(contratos.getFecha_inicio());  
		calendarFinal.set(Calendar.DAY_OF_MONTH, 1); 
		calendarFinal.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + (meses - 1)); 
		
		for (int i = 1; i <= meses; i++) {
			Auxheader auxheader = new Auxheader(dateFormat.format(calendar.getTime()).toUpperCase()); 
			auxheader.setStyle("border-top:0px;padding:1px");
			auxheader.setAlign("center");
			auxheader.setColspan(5);  
			auxMesesContarto.appendChild(auxheader);
			auxheaders.add(auxheader);
			if(i == 1){
				Clients.showNotification("Malla de Pyp, de " + dateFormat.format(calendar.getTime()) + " hasta " + dateFormat.format(calendarFinal.getTime()), auxheader);  
			}
			
			crearColumnaParaMes(calendar.getTime(), contratos); 
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		}
	}

	private void crearColumnaParaMes(Date time, Contratos contratos) {
		/* metas */
		Column column = new Column();
		column.setWidth("100px");
		column.appendChild(new Label("Metas"));
		columnMetas.appendChild(column);
		columns.add(column);
		
		
		/* metas cumplidas */
		column = new Column();
		column.setWidth("120px");
		column.appendChild(new Label("Metas cumplidas"));
		columnMetas.appendChild(column);
		columns.add(column);
		
		
		/* metas por cumplir */
		column = new Column();
		column.setWidth("120px");
		column.appendChild(new Label("Metas por cumplir"));
		columnMetas.appendChild(column);
		columns.add(column);
		
		/* % cumplido */
		column = new Column();
		column.setWidth("100px");
		column.appendChild(new Label("% cumplido"));
		columnMetas.appendChild(column);
		columns.add(column);
		
		/* % por cumplir */
		column = new Column();
		column.setWidth("100px");
		column.appendChild(new Label("% por cumplir"));
		columnMetas.appendChild(column);
		columns.add(column);
	}

	protected void limpiarPlantilla() {
		limpiamosCabecera();
	}
 
	private void limpiamosCabecera() {
		auxMesesContarto.getChildren().removeAll(auxheaders);
		columnMetas.getChildren().removeAll(columns);
		columnMetas.invalidate();
		auxheaders.clear();
		columns.clear();
	}

	/**
	 * Este metodo me ayuda a cargar los valores establecidos por la metas
	 * @author Luis Miguel
	 * @param intbox 
	 * */
	public void cargarValorMeta(Map<String, Object> map, Intbox intbox) { 
		if(list_valores_metas != null)
       	if(!list_valores_metas.isEmpty()){
       		String codigo_meta = (String) map.get(CODIGO_PDC);	
       		String codigo_area_intervencion = (String) map.get(AREA_INTERVENCION);
       		
       		for (Map<String, Object> meta_temp : list_valores_metas) { 
       			String codigo_meta_temp = (String) meta_temp.get(CODIGO_PDC);	
           		String codigo_area_intervencion_temp = (String) map.get(AREA_INTERVENCION);
           		
           		if(codigo_area_intervencion.equals(codigo_area_intervencion_temp) && codigo_meta.equals(codigo_meta_temp)){
           			String meta = (String) meta_temp.get(METAS);
           			map.put(METAS,  meta);
           			if(meta != null &&  (!meta.trim().isEmpty() && meta.matches("[0-9]*$"))){
           				intbox.setValue(Integer.parseInt(meta)); 
           			}
           			break;
           		}
			}
       	}
	}

	/**
	 * Este metodo me permite guardar automaticamente
	 * lo digitado en cada campo, es decir por demanda.
	 * @author Luis Miguel
	 * */
	public void eventoAutomaticoParaMetas(Intbox intbox, final Map<String, Object> map){
		intbox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Intbox intbox = (Intbox) arg0.getTarget();
				map.put(METAS,  intbox.getValue() != null ? intbox.getValue().toString() : ""); 
			}
		});
	}

	@Override
	public void onClose() {
		super.onClose();
		if(eventoMetasPyp != null)eventoMetasPyp.onCerrar();
	}
	
	public void cerrar(){
		if(eventoMetasPyp != null)eventoMetasPyp.onCancelar();
		detach();
	}
	
	public void registrar(){
		if(eventoMetasPyp != null)eventoMetasPyp.onRegistar(list_metas);
		onClose();
	}
	
	public EventoMetasPyp getEventoMetasPyp() {
		return eventoMetasPyp;
	}

	public void setEventoMetasPyp(EventoMetasPyp eventoMetasPyp) {
		this.eventoMetasPyp = eventoMetasPyp;
	}

	/* eventos de metas PYP */
	/**
	 * Esta clase me permite transferir los eventos que pasen en la vista por medio de
	 * la interface.
	 * @author Luis Miguel
	 * */
    public interface EventoMetasPyp{
    	void onCerrar();
    	void onRegistar(List<Map<String, Object>> list_metas); 
    	void onCancelar();
    }
    
    public static boolean validarMetasPyp(List<Map<String, Object>> list_metas){
    	boolean validad = true;
    	for (Map<String, Object> map : list_metas) {
			 String meta = (String) map.get(METAS);
			 if(meta == null){
				 validad = false;break;
			 }else if(meta.trim().isEmpty()){
				 validad = false;break;
			 }
		}
    	return validad;
    }
    
    
    /**
     * Este metodo me permite convertir un mapa de metas pyp
     * a un objeto metas_pyp
     * @author Luis Miguel
     * */
    public static Metas_pyp convertir(Map<String, Object> map){
    	Metas_pyp metas_pyp = new Metas_pyp();
    	
    	String codigo_pro = (String) map.get(CODIGO_PDC);
		String area_intervencion = (String) map.get(CODIGO_AREA_INTERVENCION);
		String metas = (String) map.get(METAS);
		
		metas_pyp.setCodigo_pcd(codigo_pro);
		metas_pyp.setArea_intervencion(area_intervencion);
		metas_pyp.setMetas(metas); 
    	
    	return metas_pyp;
    }
}
