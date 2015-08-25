/*
 * agudeza_visualAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.controller;


import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Solicitud_e1;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Cell;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.res.LabelState;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Reporte_trasladoAction extends ZKWindow implements WindowRegistrosIMG{

	
	private static final String SOLICITUDE1 = "SE1";
	//Componentes // 
	@View private Textbox tbxAccion;
	@View private Groupbox groupboxEditar;
	
	@View private Datebox dtbxFecha_inicial;
	
	
	@View(isMacro = true) private BandboxRegistrosMacro tbxCodigo_eps;
	@View private Textbox tbxNombre_eps;
	
	@View private Listbox lbxFormato;
	
	@View private Rows rowSolicitudes;
	
	@View private Datebox dtbxFechaIncio;
	@View private Datebox dtbxFechaFinal;
	
	private List<String> lista_seleccionados_servicios = new ArrayList<String>();
	
	
	// listado de objetos
	private List<Map<String, Object>> lista_datos_solicitude1 = new ArrayList<Map<String,Object>>();
	
	
	public void initReporte()throws Exception{
		try{
			accionForm(true, "registrar");
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
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
		String msj = "Los campos marcados con (*) son obligatorios";
		try {
			FormularioUtil.validarCamposObligatorios(dtbxFecha_inicial, dtbxFechaFinal, dtbxFechaIncio, tbxCodigo_eps); 
			
			if(rowSolicitudes.getChildren().isEmpty()){
				valida = false;
				msj = "Para realizar esta accion debe seleccionar una solicitud";
			}
		} catch (Exception e) {
			return false;
		}
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...",msj);
		}
		
		return valida;
	}
	
	
	public void buscarEps(String value,Listbox lbx)throws Exception{
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%"+value.toUpperCase().trim()+"%");
			
			getServiceLocator().getAdministradoraService().setLimit("limit 25 offset 0");
			
			List<Administradora> list = getServiceLocator().getAdministradoraService().listar(parameters);
			
			lbx.getItems().clear();
			
			for (Administradora dato : list) {
				
				Listitem listitem = new Listitem();
				listitem.setValue(dato);
				
				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo()+""));
				listitem.appendChild(listcell);
								
				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNit()+""));
				listitem.appendChild(listcell);

				
				lbx.appendChild(listitem);
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
	
	public void selectedEps(Listitem listitem){
		if(listitem.getValue()==null){
			tbxNombre_eps.setValue("");
			tbxCodigo_eps.setValue("");
			
		}else{
			Administradora dato = (Administradora) listitem.getValue();
			tbxNombre_eps.setValue(dato.getNombre());
			tbxCodigo_eps.setValue(dato.getCodigo());
			
			
			}
		tbxCodigo_eps.close();
	}
	
	
	public void imprimir()throws Exception{
		try {
			if(validarForm()){
				Map paramRequest = new HashMap();
				paramRequest.put("name_report", "Reporte_traslado");
				paramRequest.put("codigo_empresa", empresa.getCodigo_empresa());
				paramRequest.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				paramRequest.put("codigo_eps", tbxCodigo_eps.getValue());
				paramRequest.put("fecha_inicial", dtbxFecha_inicial.getValue());
				paramRequest.put("fecha_inicio", dtbxFechaIncio.getValue());
				paramRequest.put("fecha_fin", dtbxFechaFinal.getValue());
				paramRequest.put("formato", lbxFormato.getSelectedItem().getValue().toString());
				paramRequest.put("solicitudes", getSolicitudes()); 
				
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
	
	 
	private List<Solicitud_e1> getSolicitudes() {
		List<Solicitud_e1> solicitud_e1s = new ArrayList<Solicitud_e1>();
		for (Map<String, Object> map : lista_datos_solicitude1) { 
			solicitud_e1s.add((Solicitud_e1)map.get(SOLICITUDE1)); 
		} 
		return solicitud_e1s;
	}

	public void abrirWindowSolicitudes() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_administradora", tbxCodigo_eps.getValue()); 
		String columnas = "Nro identificacion#170px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Solicitudes de translado", Paquetes.HEALTHMANAGER,
				"Solicitud_e1Dao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_servicios);
	}

	@Override
	public void init() throws Exception {
		initReporte();
		parametrizarBandbox();
	}

	private void parametrizarBandbox() {
		parametrizarAdministradora();
	}

	private void parametrizarAdministradora() {
		tbxCodigo_eps.inicializar(tbxNombre_eps, null);
		tbxCodigo_eps
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNit()));
						listitem.appendChild(new Listcell(registro.getNombre()));

						String tipo_aseguradora = "";
						Listcell listcell = new Listcell();
						if (registro
								.getTipo_aseguradora()
								.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) {
							tipo_aseguradora = "PARTICULAR";
							listcell.setStyle("background-color:#96D9FA;");
						}
						listcell.appendChild(new LabelState(tipo_aseguradora,
								true));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								codigo_empresa);
						parametros.put("codigo_sucursal",
								codigo_sucursal);
						parametros
								.put("tipos_aseguradora",
										new String[] {
												IConstantes.TIPO_ASEGURADORA_EPS_REGIMEN_CONTRIBUTIVO,
												IConstantes.TIPO_ASEGURADORA_EPS_REGIMEN_SUBSIDIADO });
						// parametros.put("tercerizada",
						// chkSubcontratacion.isChecked() ? "S" : "N");
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getServicio(AdministradoraService.class).listar(parametros); 
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
							bandbox.setValue(registro.getCodigo());
							textboxInformacion.setValue(registro.getNit() + " "
									+ registro.getNombre());
							rowSolicitudes.getChildren().clear();
							return  true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
                       rowSolicitudes.getChildren().clear();
					}
				});
	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			//log.info("Registro seleccionado: " + registro);
			if (registro instanceof Solicitud_e1) {
				onSeleccionarSolicitud((Solicitud_e1) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
 

	@Override
	public Object[] celdasListitem(Object registro) {
		if(registro instanceof Solicitud_e1){
			return celdasListitemSolicitud_e1((Solicitud_e1)registro);
		}
		return null;
	}
	
	 
	private Object[] celdasListitemSolicitud_e1(Solicitud_e1 registro) {
		return new Object[]{registro.getNro_identificacion(), registro.getInformacion()};
	}


	private void onSeleccionarSolicitud(Solicitud_e1 registro) {
		adicionarSolicitudE1(cargarSolicitudE1(registro));
	}

 

	private void adicionarSolicitudE1(Map<String, Object> cargarSolicitudE1) {
		try {
			crearFilasSolicitudE1(cargarSolicitudE1); 
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasSolicitudE1(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_solicitude1.add(bean);
		final Solicitud_e1 solicitud_e1 = (Solicitud_e1) bean.get(SOLICITUDE1);
		
		row.setValue(solicitud_e1);
		
		Cell cell = new Cell();
		Label label = new Label("" + solicitud_e1.getNro_identificacion());
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + solicitud_e1.getInformacion());
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
									     lista_datos_solicitude1.remove(bean);
										 rowSolicitudes.removeChild(row);
									}
								}
							});
			}
		});
		rowSolicitudes.appendChild(row);
	}

	private Map<String, Object> cargarSolicitudE1(Solicitud_e1 registro) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(SOLICITUDE1, registro); 
		return bean;
	}
	
}
