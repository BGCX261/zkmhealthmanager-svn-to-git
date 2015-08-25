package healthmanager.controller;

import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.MensajesUtil;

public class ReporteAfiliadosAction extends ZKWindow implements WindowRegistrosIMG{
	
    private static final String AFILIADOS = "AF";
	private List<String> lista_seleccionados_afiliados = new ArrayList<String>();
	private List<Map<String, Object>> lista_datos_afiliados = new ArrayList<Map<String,Object>>();
	
	
	@View private Listbox lbxEstado;
	@View private Listbox lbxTipoAfiliado;
	@View private Rows rowAfiliados;
	@View private Checkbox chk_afiliados;
	@View private Listbox lbxFormato;

	@Override
	public void init() throws Exception {
		listarCombos();
	}
  
	private void listarCombos() {
		listarElementoTodo(lbxEstado, "estado_afiliacion");
		listarElementoTodo(lbxTipoAfiliado, "tipo_afiliado");
	}
 
	private void listarElementoTodo(Listbox listbox, String tipo) {
		listbox.getChildren().clear();
		Listitem listitem = new Listitem();
	    listitem.setValue(null);
		listitem.setLabel("TODOS");
		listbox.appendChild(listitem);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tipo", tipo); 
		List<Elemento> elementos = getServiceLocator().getServicio(ElementoService.class).listar(params);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
				listbox.setSelectedIndex(0);
		}
	}
	
	
	public void abrirWindowAfiliados() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String columnas = "Nro identificacion#170px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Afiliados", Paquetes.HEALTHMANAGER,
				"Afiliaciones_meDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_afiliados);
	}
	
	
	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			//log.info("Registro seleccionado: " + registro);
			if (registro instanceof Afiliaciones_me) {
				onSeleccionarAfiliaciones((Afiliaciones_me) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
 

	@Override
	public Object[] celdasListitem(Object registro) {
		if(registro instanceof Afiliaciones_me){
			return celdasListitemAfiliaciones((Afiliaciones_me)registro);
		}
		return null;
	}
	
	private Object[] celdasListitemAfiliaciones(Afiliaciones_me registro) {
		return new Object[]{registro.getPaciente().getDocumento(), registro.getPaciente().getNombreCompleto()};
	}


	private void onSeleccionarAfiliaciones(Afiliaciones_me registro) {
		adicionarAfiliaciones(cargarAfiliaciones(registro));
	}

 

	private void adicionarAfiliaciones(Map<String, Object> cargarSolicitudE1) {
		try {
			crearFilasAfiliaciones(cargarSolicitudE1); 
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasAfiliaciones(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_afiliados.add(bean);
		final Afiliaciones_me afiliaciones_me = (Afiliaciones_me) bean.get(AFILIADOS);
		
		row.setValue(afiliaciones_me);
		
		Cell cell = new Cell();
		Label label = new Label("" + afiliaciones_me.getPaciente().getDocumento());
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + afiliaciones_me.getPaciente().getNombreCompleto());
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
									     lista_datos_afiliados.remove(bean);
									     rowAfiliados.removeChild(row);
									}
								}
							});
			}
		});
		rowAfiliados.appendChild(row);
	}

	private Map<String, Object> cargarAfiliaciones(Afiliaciones_me registro) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(AFILIADOS, registro);  
		return bean;
	}
	
	
	public boolean validarForm()throws Exception{
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";
		try {
			if(chk_afiliados.isChecked() && rowAfiliados.getChildren().isEmpty()){
				valida = false;
				msj = "Para realizar esta accion debe seleccionar por lo menos un afiliado";
			}
		} catch (Exception e) {
			return false;
		}
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...",msj);
		}
		return valida;
	}
	
	public void imprimir()throws Exception{
		try {
			if(validarForm()){
				Map paramRequest = new HashMap();
				paramRequest.put("name_report", "ReporteAfiliados");
				paramRequest.put("formato", lbxFormato.getSelectedItem().getValue().toString());
				paramRequest.put("filtro_afiliados", getAfiliados()); 
				paramRequest.put("estado", lbxEstado.getSelectedItem().getValue()); 
				paramRequest.put("tipo_afiliado", getTipoAfiliado()); 
				
				Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
				final Window window = (Window)componente;
				window.setMaximizable(true);
				window.setMaximized(true);
				window.doModal();
			} 
		} catch (Exception e) { 
			MensajesUtil.mensajeError(e, null, this); 
		}
	}
 
	private String getTipoAfiliado() {
		String tipo_afiliado =  lbxTipoAfiliado.getSelectedItem().getValue();
		if(tipo_afiliado != null){
			if(tipo_afiliado.equals("1")){
				return "C";// cotizante
			}else if(tipo_afiliado.equals("2")){
				return "B";// cotizante
			}else{
				return "T";
			}
		}
		return tipo_afiliado;
	}

	private List<Paciente> getAfiliados() {
		if(chk_afiliados.isChecked()){
			List<Paciente> pacientes = new ArrayList<Paciente>();
			for (Map<String, Object> map_pacientes : lista_datos_afiliados) {
				pacientes.add(((Afiliaciones_me)map_pacientes.get(AFILIADOS)).getPaciente());
			}
			return pacientes;
		}else{
			return null;
		}
	}

}
