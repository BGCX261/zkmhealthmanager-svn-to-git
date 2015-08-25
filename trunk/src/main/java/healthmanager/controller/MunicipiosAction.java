/*
 * municipiosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos softcomputo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Pais;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.MunicipiosService;
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

public class MunicipiosAction extends ZKWindow{

	private MunicipiosService municipiosService;
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
	
	@View(isMacro = true) private BandboxRegistrosMacro tbxPais;
	@View private Textbox tbxInfoPais;
	@View private Toolbarbutton btnLimpiarPais;
	@View private Label lbId_departamento;
	@View(isMacro = true) private BandboxRegistrosMacro tbxDepartamento;
	@View private Textbox tbxInfoDepartamento;
	@View private Toolbarbutton btnLimpiarDepartamento;
	
	@View private Caption capTitulo;
	
	
	private final String TITULO_REGISTRO_MUNICIPIO = "REGISTRAR MUNICIPIO";
	private final String TITULO_MODIFICAR_MUNICIPIO = "MODIFICAR MUNICIPIO";
	
	
	private final String[] idsExcluyentes = {};
	private Municipios municipios_modificar;

	
	@Override
	public void init(){
		listarCombos();
		parametrizarBandBox();
	}
	
	private void parametrizarBandBox() {
		parametrizarBandboxPais();
		parametrizarBandboxDpto();
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
	
	private void parametrizarBandboxDpto() {
		tbxDepartamento.inicializar(tbxInfoDepartamento, btnLimpiarDepartamento);
		tbxDepartamento.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Departamentos>() {

			@Override
			public void renderListitem(Listitem listitem, Departamentos registro) {
				listitem.appendChild(new Listcell(registro.getCodigo()));
				listitem.appendChild(new Listcell(registro.getNombre()));
			}

			@Override
			public List<Departamentos> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				Pais pais = tbxPais.getRegistroSeleccionado();
				parametros.put("id_pais", pais != null ? pais.getId() : -1L);
				parametros.put("paramTodo", "paramTodo");
				parametros.put("value", valorBusqueda);
				return departamentosService.listar(parametros); 
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Departamentos registro) {
				bandbox.setValue(registro.getCodigo());
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
		listitem.setLabel("Código");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("upper(nombre)");
		listitem.setLabel("Nómbre");
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
		setTitulo(TITULO_REGISTRO_MUNICIPIO); 
		municipios_modificar = null;
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
			
			municipiosService.setLimit("limit 300 offset 0");
			
			List<Municipios> lista_datos = municipiosService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Municipios municipios : lista_datos) {
				rowsResultado.appendChild(crearFilas(municipios, this));
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
		
		final Municipios municipios = (Municipios)objeto;
		
		Departamentos departamentos = new Departamentos();
		departamentos.setCodigo(municipios.getCoddep()); 
		departamentos = departamentosService.consultar(departamentos);
		
		Pais pais = new Pais();
		pais.setId(departamentos.getId_pais());
		pais = paisService.consultarID(pais);
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label("" + (pais != null ? pais.getCodigo_pais() + " " + pais.getNombre() : "")));
		fila.appendChild(new Label("" + (departamentos  != null ? departamentos.getCodigo() + " " + departamentos.getNombre() : "")));
		fila.appendChild(new Label("" + municipios.getCodigo()));
		fila.appendChild(new Label("" + municipios.getNombre()));
		
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(municipios);
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
								eliminarDatos(municipios);
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
				Departamentos departamentos = tbxDepartamento.getRegistroSeleccionado();
				Municipios municipios = municipios_modificar;
				if(municipios == null){
					municipios = new Municipios();
				}
				
				municipios.setCodigo(tbxCodigo.getValue());
				municipios.setNombre(tbxNombre.getValue());
//				municipios.setCodigo_empresa(empresa.getCodigo_empresa());
//				municipios.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				municipios.setCoddep(departamentos.getCodigo());
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					municipiosService.crear(municipios);
					accionForm(true,"registrar");
				}else{
					int result = municipiosService.actualizar(municipios);
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
		municipios_modificar = (Municipios)obj; 
		try{
			setTitulo(TITULO_MODIFICAR_MUNICIPIO); 
			
			tbxCodigo.setValue(municipios_modificar.getCodigo());
			tbxNombre.setValue(municipios_modificar.getNombre());
			
			Departamentos departamentos = new Departamentos();
			departamentos.setCodigo(municipios_modificar.getCoddep());  
			departamentos = departamentosService.consultar(departamentos);
			if(departamentos != null){
				tbxDepartamento.seleccionarRegistro(departamentos, departamentos.getCodigo(), departamentos.getNombre()); 
			}
			
			Pais pais = new Pais();
			pais.setId(departamentos.getId_pais());
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
		Municipios municipios = (Municipios)obj;
		try{
			int result = municipiosService.eliminar(municipios);
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