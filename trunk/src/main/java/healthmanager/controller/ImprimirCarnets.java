package healthmanager.controller;

import healthmanager.modelo.bean.Paciente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;

public class ImprimirCarnets extends ZKWindow {

	@View private Grid listAfilkiados;
	@View private Center center;
	private Window searchPaciente;
	@View private Listbox lbxFormato;
	@View private Rows rowsAfiliados;
	
	@View private Checkbox chkPrintBack;
	
	@View private Checkbox chkAll;
	
	private Textbox tbxValuePaciente;
	private Rows rowsResultadoPacientes;
	
	@Override
	public void init() {} 
	
	
	private void loadComponentsInParents() {
         tbxValuePaciente = (Textbox) searchPaciente.getFellow("tbxValuePaciente");	
         rowsResultadoPacientes = (Rows) searchPaciente.getFellow("rowsResultadoPacientes");	
	}

	public void lookUpAfilaidos() throws Exception{
		searchPaciente = (Window) Executions.createComponents("/pages/openAfiliados.zul", this, null);
		loadComponentsInParents();
		searchPaciente.doModal();
	}


	public void imprimir() throws Exception{
		if(!rowsAfiliados.getChildren().isEmpty() || chkAll.isChecked()){
			Map paramRequest = new HashMap();
			paramRequest.put("name_report", "Carnets");
			paramRequest.put("formato", lbxFormato.getSelectedItem().getValue().toString());
			paramRequest.put("filtro", getFiltroParaGenerarCarnets()); 
			paramRequest.put("prin_back", chkPrintBack.isChecked());
			
			Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
			final Window window = (Window)componente;
			window.setMaximizable(true);
			window.setMaximized(true);
			window.doModal();
		}else{
			MensajesUtil.mensajeAlerta("Advertencia", "Para esta opcion debe agregar un afiliado");
		}
	}
	
	
	private List<?> getFiltroParaGenerarCarnets(){
		if(!chkAll.isChecked()){
			return rowsAfiliados.getChildren();
		}
		return null;
	}
	

	public void buscarPaciente()throws Exception{
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%"+tbxValuePaciente.getValue().toUpperCase().trim()+"%");
			
			parameters.put("limite_paginado", "limit 25 offset 0");
			
			List<Paciente> list = getServiceLocator().getPacienteService().listar(parameters);
			
			rowsResultadoPacientes.getChildren().clear();
			
			for (Paciente dato : list) {
				final Row fila = new Row();
				fila.setValue(dato);
				
				fila.appendChild(new Label(dato.getTipo_identificacion()+""));
				fila.appendChild(new Label(dato.getNro_identificacion() + "")); 
				fila.appendChild(new Label(dato.getNombre1()+" "+dato.getNombre2()+""));
				fila.appendChild(new Label(dato.getApellido1()+" "+dato.getApellido2()+""));
				
				
				Image img = new Image();
				img.setSrc("/images/add.png");
				img.setTooltiptext("Agregar");
				img.setStyle("cursor: pointer");
				img.addEventListener("onClick", new EventListener() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						selectedPaciente(fila);
					}
				});
				fila.appendChild(img);
				
				rowsResultadoPacientes.appendChild(fila);
			}
			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void selectedPaciente(final Row listitem){
			listitem.getChildren().clear();
			Paciente dato = (Paciente) listitem.getValue();  
			
			listitem.appendChild(new Label(dato.getNombreCompleto()));
			
			Image img = new Image();
			img.setSrc("/images/borrar.gif");
			img.setTooltiptext("Eliminar");
			img.setStyle("cursor: pointer");
			img.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					rowsAfiliados.removeChild(listitem);
				}
			});
			listitem.appendChild(img);
			rowsAfiliados.appendChild(listitem);
	}

}
