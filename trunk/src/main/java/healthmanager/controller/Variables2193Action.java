package healthmanager.controller;

import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Variables2193Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

public class Variables2193Action extends ZKWindow {
	@View
	private Button btnExportar;
	@View
	private Rows rowsVariables;
	@View
	private Datebox dtbxFechaIncio;
	@View
	private Datebox dtbxFechaFinal;
	@View
	private Column columnChecks;
	
	private List<Elemento> seleccionados;
	private List<Elemento> variables;
	private Map<String, List<Map<String,Object>>> resultados;
	
	@Override
	public void init() throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", "variable_2193");
		parametros.put("orden_codigo_num", "orden_codigo_num");
		
		variables = getServiceLocator().getServicio(ElementoService.class).listar(parametros);
		for (Elemento elemento : variables) {
			rowsVariables.appendChild(crearFilas(elemento));
		}
		if(variables.size()>0){
			final Checkbox chkSeleccionar = new Checkbox();
			columnChecks.appendChild(chkSeleccionar);
			chkSeleccionar.addEventListener(Events.ON_CHECK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							seleccionarTodos(chkSeleccionar.isChecked());
						}
					});
		}
		seleccionados = new ArrayList<Elemento>();
		resultados = new TreeMap<String, List<Map<String,Object>>>();
	}
	
	public void exportarArchivo(){
		if(seleccionados.size()<=0){
			Messagebox.show("Debe seleccionar al menos una variable para imprimir el reporte!!!",
					"Seleccionar variables", Messagebox.OK, Messagebox.EXCLAMATION);
		}else if(dtbxFechaIncio.getText().isEmpty()){
			Clients.showNotification(
					"Debe diligenciar la fecha de inicio",
					Clients.NOTIFICATION_TYPE_WARNING, dtbxFechaIncio,
					"after_center", 3000, true);
		}else if(dtbxFechaFinal.getText().isEmpty()){
			Clients.showNotification(
					"Debe diligenciar la fecha final",
					Clients.NOTIFICATION_TYPE_WARNING, dtbxFechaFinal,
					"after_center", 3000, true);
		}else if(dtbxFechaFinal.getValue().before(dtbxFechaIncio.getValue())){
			Clients.showNotification(
					"La fecha final no puede ser superior a la fecha inicial",
					Clients.NOTIFICATION_TYPE_WARNING, dtbxFechaFinal,
					"after_center", 3000, true);
		}else{
			Messagebox.show("Tenga en cuenta que este proceso puede tardar varios minutos dependiendo de la cantidad de pacientes y el rango de fechas especificado!!!",
					"Variables 2193", Messagebox.OK, Messagebox.EXCLAMATION);
			for (Elemento variable : seleccionados) {
				List<Map<String,Object>> variable_list = getVariable(variable.getCodigo(), dtbxFechaIncio.getValue(), dtbxFechaFinal.getValue());
				resultados.put(variable.getCodigo(), variable_list);				
			}
			imprimir(resultados);
		}
	}
	
	
	private void imprimir(Map<String, List<Map<String,Object>>> resultados){
		if (resultados != null && !resultados.isEmpty()) {
		
			Map<String, Object> parametros = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			parametros.put("nombre_reporte", "VARIABLES 2193\n["+ sdf.format(dtbxFechaIncio.getValue())+" - "+sdf.format(dtbxFechaFinal.getValue())+"]");
			parametros.put("formato", "pdf");
			parametros.put("listado_variables", resultados);
			parametros.put("name_report", "Listado_variables2193");

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, parametros);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			Messagebox.show("No existen datos para imprimir este reporte",
					"Sin datos", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	private void seleccionarTodos(Boolean seleccionar){
		for (Elemento elemento : variables) {
			for (Component componente : rowsVariables.getChildren()) {
				if(componente instanceof Row){
					Row row = (Row) componente;
					if(row.hasFellow("chkVariable"+elemento.getCodigo())){
						Checkbox chkbox = (Checkbox)row.getFellow("chkVariable"+elemento.getCodigo());
						chkbox.setChecked(seleccionar);
						cambiarSeleccionado(chkbox.isChecked(),elemento,row);
					}					
				}
			}
		}
	}
	
	
	private List<Map<String,Object>> getVariable(final String variable, final Date fecha_inicial, final Date fecha_final){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codigo_empresa", codigo_empresa);
		param.put("codigo_sucursal", codigo_sucursal);
		if(fecha_inicial!=null && fecha_final!=null){
			param.put("fecha_inicial", new Timestamp(fecha_inicial.getTime()));
			param.put("fecha_final", new Timestamp(fecha_final.getTime()));
		}
		return getServiceLocator().getServicio(Variables2193Service.class).getVariable(variable, param);
	}
	
	public Row crearFilas(final Elemento elemento) throws Exception {
		final Row fila = new Row();
		fila.setStyle("text-align:justify;nowrap:nowrap");
		final Checkbox checkbox = new Checkbox();
		checkbox.setId("chkVariable"+elemento.getCodigo());
		checkbox.addEventListener(Events.ON_CHECK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						cambiarSeleccionado(checkbox.isChecked(),elemento,fila);						
					}
				});
		fila.appendChild(checkbox);
		//fila.appendChild(new Label(elemento.getCodigo() + ""));
		fila.appendChild(new Label(elemento.getDescripcion() + ""));
		return fila;
	}
	
	private void cambiarSeleccionado(Boolean seleccionado, Elemento elemento, Row fila){
		if(seleccionado){
			seleccionados.add(elemento);
			fila.setStyle("text-align:justify;nowrap:nowrap;background: #ADED87;font-weight:bold;");
		}else{
			seleccionados.remove(elemento);
			fila.setStyle("text-align:justify;nowrap:nowrap");
		}
	}
}
