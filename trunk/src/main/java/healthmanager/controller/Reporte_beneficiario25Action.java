/*
 * agudeza_visualAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.controller;


import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.util.Util;

public class Reporte_beneficiario25Action extends Window implements AfterCompose{

	private static Logger log = Logger.getLogger(Agudeza_visualAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
//	private ServiceLocator serviceLocator;
	
	//private Permisos_sc permisos_sc;
	
	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;
	
	private Window form;
	
	//Componentes //
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	
	private Datebox dtbxFecha_inicial;
	private Date date_vencimiento;
	
	
	private Bandbox tbxIdentificacion;
	private Textbox tbxNomPaciente;
	private Textbox tbxTipoIdentificacion;
	
	private Listbox lbxFormato;
	
	@Override
	public void afterCompose(){
		loadComponents();
		cargarDatosSesion();
		
	}
	
	public void loadComponents(){
		form = (Window) this.getFellow("formReporte_beneficiario25");
		
		tbxAccion = (Textbox)form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		
		dtbxFecha_inicial = (Datebox)form.getFellow("dtbxFecha_inicial");
		tbxIdentificacion = (Bandbox)form.getFellow("tbxIdentificacion");
		tbxNomPaciente = (Textbox)form.getFellow("tbxNomPaciente");
		tbxTipoIdentificacion = (Textbox)form.getFellow("tbxTipoIdentificacion");
		
		
		lbxFormato = (Listbox)form.getFellow("lbxFormato");
	
	}
	
	public void initReporte()throws Exception{
//		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		try{
			accionForm(true, "registrar");
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
		
	public void cargarDatosSesion(){
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		
		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}
	
	
	public void listarElementoListbox(Listbox listbox,boolean select){
		listbox.getChildren().clear();
		Listitem listitem;
		if(select){
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		
		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator().getElementoService().listar(tipo);
		
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
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			//buscarDatos();
		}else{
			//buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent).setText(((Textbox) abstractComponent).getText().trim().toUpperCase());
				} 
			}
		}
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection){
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")){
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals("lbxParameter")){
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setSelectedIndex(0);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setText("0.00");
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setText("");
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setValue(new Date());
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setChecked(false);
			}
			if (abstractComponent instanceof Radiogroup) {
				((Radio)((Radiogroup) abstractComponent).getFirstChild()).setChecked(true);
			}
		}
		
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		
		boolean valida = true;
		
		
		if(!valida){
			Messagebox.show("Los campos marcados con (*) son obligatorios", 
				usuarios.getNombres()+" recuerde que...", 
				Messagebox.OK, Messagebox.EXCLAMATION);
		}
		
		return valida;
	}
	
	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}
	
	
		
	public void buscarPaciente(String value,Listbox lbx)throws Exception{
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%"+value.toUpperCase().trim()+"%");
			
			getServiceLocator().getAfiliacionesMeService().setLimit("limit 25 offset 0");
			
			List<Afiliaciones_me> list = getServiceLocator().getAfiliacionesMeService().listar(parameters);
			
			lbx.getItems().clear();
			
			for (Afiliaciones_me dato : list) {
				
				if(dato.getTipo_afiliado().equals("B")){
				
				Paciente paciente = new Paciente();
				paciente.setCodigo_empresa(dato.getCodigo_empresa());
				paciente.setCodigo_sucursal(dato.getCodigo_sucursal());
				paciente.setNro_identificacion(dato.getNro_identificacion_afiliado());
				paciente = getServiceLocator().getPacienteService().consultar(paciente);
				
				String edad = Util.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente.getFecha_nacimiento()), paciente.getUnidad_medidad(), false);
				
				//log.info("edad: "+edad);
				
				if(edad.equals("24")){
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(paciente.getFecha_nacimiento());
					int dia = calendar.get(Calendar.DAY_OF_MONTH);
					int mes_nac = calendar.get(Calendar.MONTH);
					//log.info("mes_nac: "+mes_nac);
					
					Date date = new Date();
					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(date);
					int mes = calendar2.get(Calendar.MONTH);
					int anno = calendar2.get(Calendar.YEAR);
					//log.info("mes: "+mes);
					
					calendar2.set(Calendar.DAY_OF_MONTH, dia);
					calendar2.set(Calendar.MONTH, mes_nac);
					calendar2.set(Calendar.YEAR, anno);
					
					date_vencimiento = calendar2.getTime();
					
					
					if(mes_nac == mes){
						
						Listitem listitem = new Listitem();
						listitem.setValue(dato);
						
						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(paciente.getTipo_identificacion()+""));
						listitem.appendChild(listcell);
						
						listcell = new Listcell();
						listcell.appendChild(new Label(paciente.getNro_identificacion()+""));
						listitem.appendChild(listcell);
						
						listcell = new Listcell();
						listcell.appendChild(new Label(paciente.getNombre1()+" "+paciente.getNombre2()));
						listitem.appendChild(listcell);
						
						listcell = new Listcell();
						listcell.appendChild(new Label(paciente.getApellido1()+" "+paciente.getApellido2()));
						listitem.appendChild(listcell);
						
						lbx.appendChild(listitem);
					}
				  }
				}
			}
			
			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void selectedPaciente(Listitem listitem){
		if(listitem.getValue()==null){
			tbxIdentificacion.setValue("");
			tbxNomPaciente.setValue("");
			tbxTipoIdentificacion.setValue("");
			
			
															
		}else{
			Afiliaciones_me dato = (Afiliaciones_me) listitem.getValue();
			
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(dato.getCodigo_empresa());
			paciente.setCodigo_sucursal(dato.getCodigo_sucursal());
			paciente.setNro_identificacion(dato.getNro_identificacion_afiliado());
			paciente = getServiceLocator().getPacienteService().consultar(paciente);
			
			tbxIdentificacion.setValue(paciente.getNro_identificacion());
			tbxNomPaciente.setValue(paciente.getNombre1()+" "+paciente.getNombre2()+" "+paciente.getApellido1()+" "+paciente.getApellido2());
			tbxTipoIdentificacion.setValue(paciente.getTipo_identificacion());
			
		}
		
		tbxIdentificacion.close();
	}
	
	public void imprimir()throws Exception{
		if(tbxIdentificacion.equals("")){
			Messagebox.show("Ingrese un paciente", 
					"Informacion .." ,
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Reporte_beneficiario25");
		paramRequest.put("codigo_empresa", empresa.getCodigo_empresa());
		paramRequest.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		paramRequest.put("identificacion", tbxIdentificacion.getValue());
		paramRequest.put("fecha_inicial", dtbxFecha_inicial.getValue());
		paramRequest.put("date_vencimiento", date_vencimiento);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue().toString());
		//log.info(tbxIdentificacion.getValue()+" - "+date_vencimiento);
		
		Component componente = Executions.createComponents("/pages/printInformes.zul", form, paramRequest);
		final Window window = (Window)componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
		
	}
}
