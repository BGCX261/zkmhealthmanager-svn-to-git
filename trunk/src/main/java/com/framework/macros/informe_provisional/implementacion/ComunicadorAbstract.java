package com.framework.macros.informe_provisional.implementacion;

import healthmanager.modelo.service.ReportService;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Cell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Frozen;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.macros.informe_provisional.TabInformeProvisionalMacro.IComunidadorAccion;

/**
 * 
 * */
public abstract class ComunicadorAbstract implements IComunidadorAccion{

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();
	private Long cantidad;
	
	protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	protected DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
	private Grid grid;
	
	@Override
	public void mostarInformacion(Map<String, Object> fecha_seleccionada,
			Map<String, Object> param, Div contenedor, String titulo){
		// montamos 
	    montarCabecera(getColumns(contenedor, titulo, fecha_seleccionada));   
		parametrizarResultadoPaginado();
		consultar(fecha_seleccionada, param);  
	}
	
	private Columns getColumns(Div contenedor, String titulo, Map<String, Object> fecha_seleccionada) { 
		grid = new Grid(); 
		grid.setVflex("1");
		grid.setHeight("100%");
		grid.setStyle("border-top:0px;border-bottom:0px;border-right:0px;");
		
		Date fecha = (Date) fecha_seleccionada.get("fecha");
		Long cantidad = (Long) fecha_seleccionada.get("cantidad");
		
		Center center = (Center) contenedor.getParent();
		center.setTitle(titulo + " " + dateFormat.format(fecha) + " - Total: "
				+ cantidad); 

		Frozen frozen = new Frozen();
		frozen.setStyle("background: #DFDED8");
		frozen.setColumns(2);
		grid.appendChild(frozen);

		Columns columns = new Columns();
		columns.setHeight("30px");
		columns.setSizable(true);
		
		// Agregamos columna de estado
		Column column = new Column();
		column.setWidth("10px"); 
		columns.appendChild(column);
		
		grid.appendChild(columns);
		grid.appendChild(new Rows()); 
		
		contenedor.getChildren().clear();
		contenedor.appendChild(grid);
		return columns;
	}

	protected abstract void montarCabecera(Columns columnContenedor);

	protected abstract String getIdXml();

	/**
	 * metodo para consultar informacion
	 * @param string 
	 * */
	private void consultar(Map<String, Object> fecha_seleccionada,
			Map<String, Object> param) { 
		try {
			cantidad = (Long) fecha_seleccionada.get("cantidad");
			param.put("fecha_seleccionada", (Date)fecha_seleccionada.get("fecha"));    
//			System.out.println("" + param); 
			resultadoPaginadoMacro.buscarDatos(param);
		} catch (Exception e) {
			e.printStackTrace();
//			MensajesUtil.mensajeError(e, "", contenedor);
		}
	}
	
	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Map<String, Object>> listarResultados(
					Map<String, Object> parametros) {
				return getServiceLocator()
						.getServicio(ReportService.class).getReport(parametros,
								"informeprovisionarModel",
								getIdXml()); 
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				return cantidad.intValue();
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearRowMarcarEstado(dato, new Row());
			}
		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				grid, grid.getColumns().getChildren()
						.size());
	}
	
	protected Row crearRowMarcarEstado(Object dato, Row row) {
		if(dato != null && dato instanceof Map){
			String estado = (String) ((Map<String, Object>)dato).get("estado"); 
			if(estado != null){
				Cell cell = new Cell();
				if(estado.equals(IConstantes.ESTADO_ADMISION_ACTIVA)){
					cell.setStyle(IConstantes.ESTILO_COLOR_ADMISION_ACTIVA);
					cell.setTooltiptext("ADMISIÓN ACTIVA");
				}else if(estado.equals(IConstantes.ESTADO_ADMISION_FACTURADA)){
					cell.setStyle(IConstantes.ESTILO_COLOR_ADMISION_FACTURADA);
					cell.setTooltiptext("ADMISIÓN FACTURADA");
				}else if(estado.equals(IConstantes.ESTADO_ADMISION_CANCELADA)){
					cell.setStyle(IConstantes.ESTILO_COLOR_ADMISION_CANCELADA);
					cell.setTooltiptext("ADMISIÓN CANCELADA");
				}
				row.appendChild(cell);
			}
		}else{
			row.appendChild(new Cell());
		}
		crearFilas(dato, row); 
		return row;
	}

	protected abstract void crearFilas(Object dato, Row row);
	
	protected Column getColumn(String titulo, String width) {
		Column column = new Column(titulo);
		column.setWidth(width); 
		return column;
	}
	
	protected String getNombreTipo(String tipo_factura) {
		if(tipo_factura.equals("01")){
		  return "CAPITADO";	
		}else if(tipo_factura.equals("02")){
		  return "EVENTO";
		}else if(tipo_factura.equals("03")){
		  return "AGRUPADO";
		}else{
		  return "";
		}
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

}
