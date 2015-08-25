package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Pyp;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;
import healthmanager.modelo.service.GeneralExtraService;

public class BuscarCitasPorPacienteAction extends ZKWindow {
	
	
	@View private Bandbox tbxNro_identificacion;
	@View private Textbox tbxNombPaciente;
	
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	private Paciente dato;
	private Administradora administradora;
	

	@Override
	public void init() {
		

	}
	
	public void buscarPaciente(String value,Listbox lbx)throws Exception{
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%"+value.toUpperCase().trim()+"%");
			
			parameters.put("limite_paginado", "limit 25 offset 0");
			
			List<Paciente> list = getServiceLocator().getPacienteService().listar(parameters);
			
			lbx.getItems().clear();
			
			for (Paciente dato : list) {
				Listitem listitem = new Listitem();
				listitem.setValue(dato);
				
				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()+""));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()+""));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre1()+" "+dato.getNombre2()));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellido1()+" "+dato.getApellido2()));
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
	
	public void selectedPaciente(Listitem listitem) throws Exception{
		if(listitem.getValue()==null){
			tbxNro_identificacion.setValue("");
			tbxNombPaciente.setValue("");
			dato = null;
			administradora = null;
		}else{
			dato = (Paciente) listitem.getValue(); 
			
			tbxNro_identificacion.setValue(dato.getNro_identificacion());
			tbxNombPaciente.setValue(dato.getNombreCompleto());
			 
			administradora = new Administradora();
			administradora.setCodigo_empresa(codigo_empresa);
			administradora.setCodigo_sucursal(codigo_sucursal);
			administradora.setCodigo(dato.getCodigo_administradora());
			administradora = (Administradora) getServiceLocator().getAdministradoraService().consultar(administradora);
			
		}
		buscarDatos();
		tbxNro_identificacion.close();
	}
	
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("nro_identificacion", tbxNro_identificacion.getValue());
			
			getServiceLocator().getCitasService().setLimit("limit 25 offset 0");
			
			List<Citas> lista_datos = getServiceLocator().getCitasService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Citas citas : lista_datos) {
				rowsResultado.appendChild(crearFilas(citas, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			MensajesUtil.mensajeError(e, null, this);
		}
	}
	
	
	public Row crearFilas(Object objeto, AbstractComponent componente)throws Exception{
		Row fila = new Row();
		
		final Citas citas = (Citas)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
//		Paciente paciente = new Paciente();
//		paciente.setCodigo_empresa(codigo_empresa);
//		paciente.setCodigo_sucursal(codigo_sucursal);
//		paciente.setNro_identificacion(citas.getNro_identificacion());
		
		String nombreAdmin = "";
//		String nombrePac = "";
		
		
		if(dato != null){
			nombreAdmin = administradora != null ? administradora.getNombre() : "";
		}
		
		Elemento elemento = new Elemento();
		elemento.setCodigo("" + citas.getTipo_consulta());
		elemento.setTipo("motivo_consulta_hc");
		elemento = getServiceLocator().getElementoService().consultar(elemento);
		
		
		Elemento elementoEstado = new Elemento();
		elementoEstado.setCodigo("" + citas.getEstado());
		elementoEstado.setTipo("estado_cita");
		elementoEstado = getServiceLocator().getElementoService().consultar(elementoEstado);
	
		String programa = "";
		String actividad = "";
		Pyp pyp = new  Pyp();
		pyp.setCodigo(citas.getArea_intervencion());
		pyp = getServiceLocator().getServicio(GeneralExtraService.class).consultar(pyp);
		
		if(pyp != null){
			programa = pyp.getNombre();
			
			Plantilla_pyp plantilla_pyp = new Plantilla_pyp();
			plantilla_pyp.setArea_intervencion(pyp.getCodigo());
			plantilla_pyp.setCodigo_pdc(citas.getCodigo_plantilla());
			plantilla_pyp = getServiceLocator().getPlantillaPypService().consultar(plantilla_pyp);
			if(plantilla_pyp != null){
				actividad = plantilla_pyp.getNombre_pcd();
			}
		}
		
		
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(nombreAdmin+""));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy").format(citas.getFecha_cita()))); 
		fila.appendChild(new Label(citas.getHora()));
//		fila.appendChild(new Label(nombrePac+""));
		fila.appendChild(new Label(elemento != null ? elemento.getDescripcion() : ""));
		fila.appendChild(new Label(programa+""));
		fila.appendChild(new Label(actividad+""));
		fila.appendChild(new Label(elementoEstado != null ? elementoEstado.getDescripcion().toUpperCase() : "ESTADO NO VALIDO"));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
//				mostrarDatos(citas);
			}
		});
//		hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setTooltiptext("Imprimir");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				imprimir(citas.getCodigo_cita());
			}
		});
		hbox.appendChild(space);
		if(citas.getEstado().equals("1"))
		hbox.appendChild(img); 
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	public void imprimir(String codigo_cita) throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "RecordatorioCitas");
		paramRequest.put("codigo_cita", codigo_cita + "");
 
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

}
