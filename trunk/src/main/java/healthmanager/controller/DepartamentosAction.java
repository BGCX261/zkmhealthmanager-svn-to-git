/*
 * departamentosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos softcomputo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Pais;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.PaisService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
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
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class DepartamentosAction extends ZKWindow{

	
	private DepartamentosService departamentosService;
	private PaisService paisService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo;
	@View private Textbox tbxNombre;
	@View private Caption capTitulo;
	
	@View(isMacro = true) private BandboxRegistrosMacro tbxPais;
	@View private Textbox tbxInfoPais;
	@View private Toolbarbutton btnLimpiarPais;
	
	private final String TITULO_REGISTRO_DEPARTAMENTO = "REGISTRAR DEPARTAMENTO";
	private final String TITULO_MODIFICAR_DEPARTAMENTO = "MODIFICAR DEPARTAMENTO";
	
	private final String[] idsExcluyentes = {};
    private Departamentos departamentos_modificar;
	
	@Override
	public void init(){
		listarCombos();
		parametrizarBandBox();
	}
	
	private void parametrizarBandBox() {
		parametrizarBandboxPais();
	}
 
	private void parametrizarBandboxPais() {
		tbxPais.inicializar(tbxInfoPais, btnLimpiarPais);
		tbxPais.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Pais>() {

			@Override
			public void renderListitem(Listitem listitem, Pais registro) {
				listitem.appendChild(new Listcell(registro.getCodigo_pais()));
				listitem.appendChild(new Listcell(registro.getNombre()));
			}

			@Override
			public List<Pais> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("codigo_empresa", codigo_empresa);
				parametros.put("codigo_sucursal", codigo_sucursal);
				parametros.put("paramTodo", "paramTodo");
				parametros.put("value", valorBusqueda);
				return paisService.listar(parametros); 
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Pais registro) {
				bandbox.setValue(registro.getCodigo_pais());
				textboxInformacion.setValue(registro.getNombre());
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				
			}
		});
	}

	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("upper(nombre)");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			buscarDatos();
		}else{
			//buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	private void setTitulo(String titulo){
		capTitulo.setLabel(titulo); 
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
		setTitulo(TITULO_REGISTRO_DEPARTAMENTO); 
		tbxCodigo.setReadonly(false); 
		departamentos_modificar = null;
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		
		boolean valida = true;
		
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...","Los campos marcados con (*) son obligatorios");
		}
		
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			departamentosService.setLimit("limit 300 offset 0");
			
			List<Departamentos> lista_datos = departamentosService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Departamentos departamentos : lista_datos) {
				rowsResultado.appendChild(crearFilas(departamentos, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Departamentos departamentos = (Departamentos)objeto;
		
		Pais pais = new Pais();
		pais.setId(departamentos.getId_pais());
		pais = paisService.consultarID(pais);
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label("" + departamentos.getCodigo()));
		fila.appendChild(new Label("" + departamentos.getNombre()));
		fila.appendChild(new Label("" + (pais != null ? pais.getNombre() : "")));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(departamentos);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(departamentos);
								buscarDatos();
							}
						}
					});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if(validarForm()){
				//Cargamos los componentes //
				
				Pais pais = tbxPais.getRegistroSeleccionado();
				
				Departamentos departamentos = departamentos_modificar;
				if(departamentos == null){
					departamentos = new Departamentos();
				}
				departamentos.setCodigo(tbxCodigo.getValue());
				departamentos.setNombre(tbxNombre.getValue());
//				departamentos.setCodigo_empresa(empresa.getCodigo_empresa());
//				departamentos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				departamentos.setId_pais(pais.getId()); 
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					departamentosService.crear(departamentos);
					accionForm(true,"registrar");
				}else{
					int result = departamentosService.actualizar(departamentos);
					if(result==0){
						throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				
		MensajesUtil.mensajeInformacion("Informacion ..","Los datos se guardaron satisfactoriamente");
			}
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		departamentos_modificar = (Departamentos)obj;
		try{
			tbxCodigo.setReadonly(true); 
			
			tbxCodigo.setValue(departamentos_modificar.getCodigo());
			tbxNombre.setValue(departamentos_modificar.getNombre());
			
			setTitulo(TITULO_MODIFICAR_DEPARTAMENTO); 
			
			Pais pais = new Pais();
			pais.setId(departamentos_modificar.getId_pais());
			pais = paisService.consultarID(pais);
			if(pais != null){
				tbxPais.seleccionarRegistro(pais, pais.getCodigo_pais(), pais.getNombre()); 
			}
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Departamentos departamentos = (Departamentos)obj;
		try{
			int result = departamentosService.eliminar(departamentos);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (HealthmanagerException e) {
			MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
		}catch(RuntimeException r){
			MensajesUtil.mensajeError(r, "", this);
		}
	}
}