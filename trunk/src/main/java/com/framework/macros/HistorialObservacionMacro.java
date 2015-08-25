package com.framework.macros;

import healthmanager.controller.ZKWindow.View;
import healthmanager.modelo.bean.Historial_observaciones;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.UsuariosService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.CampoObservacionesPopupMacro.OnCambioTexto;
import com.framework.res.CargardorDeDatos;
import com.framework.res.LabelAlign.AlignText;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;

public class HistorialObservacionMacro extends Groupbox implements AfterCompose{
	
	@View private Rows rowObservaciones;
	private Toolbarbutton toolbarbutton;
	
	
	public static final String HISTORIAL = "HO";
	private static final String USUARIO = "US";
	
	private List<Map<String, Object>> list_observaciones;

	@Override
	public void afterCompose() {
		try {
			CargardorDeDatos.initComponents(this);
			inicializarObservaciones();
		} catch (Exception e) { 
			MensajesUtil.mensajeError(e, null, this); 
		} 
	}

	// Configuracion de carga de observaciones
	public void inicializarObservaciones(){
		if(list_observaciones == null){
			list_observaciones = new ArrayList<Map<String,Object>>();
		}else{
			list_observaciones.clear();
		}
		rowObservaciones.getChildren().clear();
		agregarBotonAdicion();
	}
	
	
	public void limpiar(){
		inicializarObservaciones();
	}
	
	private void invalidarTabla() {
		getFellow("gridRegistros").invalidate();
	}
	
	private void agregarBotonAdicion() {
		final Row row = new Row();

		Cell cell = new Cell();
		cell.setColspan(4);
		cell.setAlign(AlignText.RIGHT.toString().toLowerCase());
		row.appendChild(cell);

		toolbarbutton = new Toolbarbutton(); 
		toolbarbutton.setImage("/images/add.png");
		cell.appendChild(toolbarbutton);

		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						agregarNuevaFilaObservaciones(row, new HashMap<String, Object>());
						agregarBotonAdicion();
						invalidarTabla();
					}
				});
		rowObservaciones.appendChild(row);
	}
	
	
	protected void agregarNuevaFilaObservaciones(Row row,
			final Map<String, Object> map) {
		if (row != null)
			row.getChildren().clear();
		else {
			row = new Row();
			rowObservaciones.appendChild(row);
		}
		
		boolean nuevo = false;
		Historial_observaciones historial_observaciones = (Historial_observaciones) map.get(HISTORIAL);
		if(historial_observaciones == null){
			historial_observaciones = new Historial_observaciones();
			nuevo = true;
			map.put(HISTORIAL, historial_observaciones);
		}
		
		Usuarios usuarios = (Usuarios) map.get(USUARIO); 
		if(usuarios == null){
			usuarios = getUsuariosSistema();
			map.put(USUARIO, usuarios);
		}

		/* Edad Inicial */
		Datebox datebox = new Datebox(Calendar.getInstance().getTime());
		datebox.setFormat("yyyy-MM-dd hh:mm a"); 
		datebox.setButtonVisible(nuevo);
		datebox.setReadonly(!nuevo); 
		datebox.setHflex("1"); 
		Res.cargarAutomatica(datebox, historial_observaciones, "fecha_observacion");
		Cell cell = new Cell();
		cell.appendChild(datebox);
		row.appendChild(cell);
		
		/* observaciones */
		CampoObservacionesPopupMacro textboxObservaciones = new CampoObservacionesPopupMacro(this);
		textboxObservaciones.setHflex("1"); 
//		textboxObservaciones.setRows(3); 
		textboxObservaciones.setReadonly(!nuevo); 
		final Historial_observaciones historial_observacionesFinal = historial_observaciones;
		textboxObservaciones.setOnCambioTexto(new OnCambioTexto() {
			@Override
			public void texto(String texto) {
				historial_observacionesFinal.setObservacion(texto); 
			}
		});
		textboxObservaciones.setValue(historial_observaciones.getObservacion()); 
		cell = new Cell();
		cell.appendChild(textboxObservaciones);
		row.appendChild(cell);
		
		cell = new Cell();
		cell.appendChild(new Label(usuarios != null ? usuarios.getNombres() + " " + usuarios.getApellidos() : "*Usuario no encontrado*")); 
		row.appendChild(cell);
		
		// agregar boton eliminar en el caso que sea nuevo
		if(nuevo){
			list_observaciones.add(map);
			Toolbarbutton toolbarbutton = new Toolbarbutton();
			toolbarbutton.setAttribute("row", row);
			toolbarbutton.setAttribute("historial", map);
			toolbarbutton.setImage("/images/borrar.gif");
			toolbarbutton.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(final Event arg0) throws Exception {
							Messagebox
									.show("Esta seguro que desea eliminar este registro? ",
											"Eliminar Registro",
											Messagebox.YES + Messagebox.NO,
											Messagebox.QUESTION,
											new org.zkoss.zk.ui.event.EventListener<Event>() {
												public void onEvent(Event event)
														throws Exception {
													if ("onYes".equals(event
															.getName())) {
														rowObservaciones
																.removeChild((Row) arg0
																		.getTarget()
																		.getAttribute(
																				"row"));
														list_observaciones.remove(map);
														invalidarTabla();
													}
												}
											});
						}
					});
			cell = new Cell();
			cell.appendChild(toolbarbutton);
			row.appendChild(cell);
		}else{
			row.appendChild(new Cell()); 
		}
	}
	
	
	public List<Map<String, Object>> getHistorial_observaciones(){
		return list_observaciones; 
	}
	
	public void setHistorialObservaciones(List<Historial_observaciones> historial_observaciones){
		Map<String, Object> usuariosMAP = new HashMap<String, Object>();
		rowObservaciones.getChildren().clear();
		for (Historial_observaciones historial_observacionesTemp : historial_observaciones) {
			// Cargamos datos de usuario
			Usuarios usuarios = (Usuarios) usuariosMAP.get(historial_observacionesTemp.getCreacion_user()); 
			if(usuarios == null){
				usuarios = new Usuarios();
				usuarios.setCodigo_empresa(historial_observacionesTemp.getCodigo_empresa());
				usuarios.setCodigo_sucursal(historial_observacionesTemp.getCodigo_sucursal());
				usuarios.setCodigo(historial_observacionesTemp.getCreacion_user()); 
				usuarios = getServiceLocator().getServicio(UsuariosService.class).consultar(usuarios);
				if(usuarios != null){
					usuariosMAP.put(usuarios.getCodigo(), usuarios);
				}
			}
			
			 // empaquetamos
			 Map<String, Object> map = new HashMap<String, Object>();
			 map.put(HISTORIAL, historial_observacionesTemp);
			 map.put(USUARIO, usuarios);
			 agregarNuevaFilaObservaciones(null, map); 
		}
		agregarBotonAdicion();
	}
	
	private Usuarios getUsuariosSistema(){
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		return ServiceLocatorWeb.getUsuarios(request);
	}
	
	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}
}
