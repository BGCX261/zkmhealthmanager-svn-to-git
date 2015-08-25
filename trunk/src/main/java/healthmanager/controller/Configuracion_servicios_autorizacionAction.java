/*
 * configuracion_servicios_autorizacionAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Configuracion_autorizacion_tipo_usuario;
import healthmanager.modelo.bean.Configuracion_autorizacion_via_ingreso;
import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;
import healthmanager.modelo.bean.Detalle_config_servicios_autorizacion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Servicios_disponibles;
import healthmanager.modelo.service.Configuracion_autorizacion_tipo_usuarioService;
import healthmanager.modelo.service.Configuracion_autorizacion_via_ingresoService;
import healthmanager.modelo.service.Configuracion_servicios_autorizacionService;
import healthmanager.modelo.service.Detalle_config_servicios_autorizacionService;
import healthmanager.modelo.service.ElementoService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.macros.Servicios_disponiblesMacro;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Configuracion_servicios_autorizacionAction extends ZKWindow {

	private Configuracion_servicios_autorizacionService configuracion_servicios_autorizacionService;
	private Detalle_config_servicios_autorizacionService detalle_config_servicios_autorizacionService;
	private ElementoService elementoService;
	private Configuracion_autorizacion_tipo_usuarioService configuracion_autorizacion_tipo_usuarioService;
	private Configuracion_autorizacion_via_ingresoService configuracion_autorizacion_via_ingresoService;

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Textbox tbxNombre;
	@View
	private Listbox lbxEstado;
	@View
	private Listbox lbxTipo_usuarios;
	@View
	private Listbox lbxVia_ingreso;
	@View private Listbox lbxModoCobro;
	
	@View private Div servicios_arbol;
	@View private Listbox lbxTipoServicio;
	@View private Checkbox chkSolicitarTipoProcedimientos;
	@View private Row rwProcedimientos;
	
	private final String[] idsExcluyentes = {"lbxVia_ingreso", "lbxTipo_usuarios"};

	private Servicios_disponiblesMacro servicios_disponiblesMacro;

	private Configuracion_servicios_autorizacion configuracion_servicios_autorizacion;

	@Override
	public void init() {
		listarCombos();
		inicializarArbolServicios();
	}
 
	/**
	 * Este se activa cuando se selecciona los tipos de servicio
	 * @author Luis Miguel
	 * @param b 
	 * */
	public void onSeleccionarTipoServicio(boolean selecion){ 
		String tipo_servicio = lbxTipoServicio.getSelectedItem().getValue();
		if(!tipo_servicio.trim().isEmpty()){
				if(selecion){
				Utilidades
						.setValueFrom(
								lbxModoCobro,
								tipo_servicio
										.equals(IConstantes.TIPO_SERVICIO_AUTO_PROCEDIMIENTOS) ? IConstantes.MODO_COBRO_RECIBOS_CAJA_PROCEDIMIENTOS
										: IConstantes.MODO_COBRO_RECIBOS_CAJA_MEDICAMENTOS);
				}
				rwProcedimientos.setVisible(tipo_servicio.equals(IConstantes.TIPO_SERVICIO_AUTO_PROCEDIMIENTOS));
				if(tipo_servicio.equals(IConstantes.TIPO_SERVICIO_AUTO_MEDICAMENTOS)){
					chkSolicitarTipoProcedimientos.setChecked(false);
				}
		}else{
			chkSolicitarTipoProcedimientos.setChecked(false); 
			rwProcedimientos.setVisible(false);
		}
	}

	private void inicializarArbolServicios() { 
		if(servicios_disponiblesMacro != null){
			servicios_disponiblesMacro.detach();
		}
		servicios_disponiblesMacro = (Servicios_disponiblesMacro) Executions
				.createComponents("/WEB-INF/macros/servicios_disponibles.zul",
						this, new HashMap<String, Object>());
		servicios_disponiblesMacro.inicializar(this); 
		servicios_arbol.appendChild(servicios_disponiblesMacro);
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxVia_ingreso, false, getServiceLocator(), false);
		Utilidades.listarElementoListbox(lbxTipo_usuarios, false, getServiceLocator(), false);
		Utilidades.listarElementoListbox(lbxEstado, true, getServiceLocator()); 
		Utilidades.listarElementoListbox(lbxTipoServicio, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxModoCobro, true, getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nombre");
		listitem.setLabel("Descripcion");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else if(!accion.equalsIgnoreCase("modificar")){
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		Utilidades.quitarSeleccion(lbxVia_ingreso); 
		Utilidades.quitarSeleccion(lbxTipo_usuarios);
		inicializarArbolServicios();
	}
	
	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";
		FormularioUtil.validarCamposObligatorios(tbxNombre, lbxEstado, lbxModoCobro);
		
		Set<Listitem> listitems = lbxTipo_usuarios.getSelectedItems();
		if(listitems.isEmpty()){
			valida = false;
			msj = "El tipo de usuario a seleccionar es obligatorio"; 
		}else if(lbxVia_ingreso.getSelectedItems().isEmpty()){ 
			boolean habilitado_para_medico = false;
			if(listitems.size() == 1){
				habilitado_para_medico = !listitems.iterator().next()
						.getValue()
						.equals(IConstantes.TIPO_USUARIO_CONFIG_AUDITOR);
			}else{
				habilitado_para_medico = true;
			}
			if(habilitado_para_medico){
				valida = false;
				msj = "Cuando un tipo de usuario seleccionado es médico, es de caracter obligatorio seleccionar la via de ingreso";
			}
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(getUsuarios().getNombres()
					+ " recuerde que...",
					"" + msj);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			configuracion_servicios_autorizacionService
					.setLimit("limit 25 offset 0");

			List<Configuracion_servicios_autorizacion> lista_datos = configuracion_servicios_autorizacionService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Configuracion_servicios_autorizacion configuracion_servicios_autorizacion : lista_datos) {
				rowsResultado.appendChild(crearFilas(
						configuracion_servicios_autorizacion, this));
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

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Configuracion_servicios_autorizacion configuracion_servicios_autorizacion = (Configuracion_servicios_autorizacion) objeto;

		Elemento elemento_estado = new Elemento();
		elemento_estado.setCodigo(configuracion_servicios_autorizacion.getEstado()); 
		elemento_estado.setTipo("estado"); 
		elemento_estado = elementoService.consultar(elemento_estado); 
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(configuracion_servicios_autorizacion.getNombre() + ""));  
		fila.appendChild(new Label("Via Ingreso"));  
		fila.appendChild(new Label("Tipo Usuario"));  
		fila.appendChild(new Label(elemento_estado != null ? elemento_estado.getDescripcion() : "(SIN DATO)"));   

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(configuracion_servicios_autorizacion);
			}
		});
		hbox.appendChild(img);

//		img = new Image();
//		img.setSrc("/images/borrar.gif");
//		img.setTooltiptext("Eliminar");
//		img.setStyle("cursor: pointer");
//		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				Messagebox.show(
//						"Esta seguro que desea eliminar este registro? ",
//						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
//						Messagebox.QUESTION,
//						new org.zkoss.zk.ui.event.EventListener<Event>() {
//							public void onEvent(Event event) throws Exception {
//								if ("onYes".equals(event.getName())) {
//									// do the thing
//									eliminarDatos(configuracion_servicios_autorizacion);
//									buscarDatos();
//								}
//							}
//						});
//			}
//		});
//		hbox.appendChild(space);
//		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //

				Configuracion_servicios_autorizacion configuracion_servicios_autorizacion = new Configuracion_servicios_autorizacion();
				
				if(this.configuracion_servicios_autorizacion != null){
					configuracion_servicios_autorizacion
							.setId(this.configuracion_servicios_autorizacion
									.getId());
				}
				
				configuracion_servicios_autorizacion.setCodigo_empresa(getEmpresa()
						.getCodigo_empresa());
				configuracion_servicios_autorizacion
						.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
				configuracion_servicios_autorizacion.setNombre(tbxNombre
						.getValue());
				configuracion_servicios_autorizacion
						.setCreacion_date(new Timestamp(new GregorianCalendar()
								.getTimeInMillis()));
				configuracion_servicios_autorizacion.setCreacion_user(usuarios
						.getCodigo().toString());
				configuracion_servicios_autorizacion.setEstado(lbxEstado
						.getSelectedItem().getValue().toString());
				configuracion_servicios_autorizacion.setTipo_servicio(lbxTipoServicio.getSelectedItem().getValue().toString()); 
				configuracion_servicios_autorizacion.setMostrar_tipo_pcd(chkSolicitarTipoProcedimientos.isChecked());
				configuracion_servicios_autorizacion.setModo_cobro(lbxModoCobro.getSelectedItem().getValue().toString()); 
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Configuracion_servicios_autorizacionService.PARAM_CONFIGURACION,
						configuracion_servicios_autorizacion); 
				map.put(Configuracion_servicios_autorizacionService.PARAM_DETALLE_CONFIGURACION,
						convertirDetalleConfiguracion(servicios_disponiblesMacro
								.getListadoSeleccionado()));  
				map.put(Configuracion_servicios_autorizacionService.PARAM_ACCION, tbxAccion.getText());
				map.put(Configuracion_servicios_autorizacionService.PARAM_CONFIGURACION_VIA_INGRESO,
						convertirConfiguracionViaIngreso(lbxVia_ingreso.getSelectedItems()));  
				map.put(Configuracion_servicios_autorizacionService.PARAM_CONFIGURACION_TIPO_USUARIO,
						convertirConfiguracionTipoUsuario(lbxTipo_usuarios.getSelectedItems()));  
				configuracion_servicios_autorizacionService.guardar(map); 
				
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	private List<Configuracion_autorizacion_tipo_usuario> convertirConfiguracionTipoUsuario(
			Set<Listitem> listitems) {
		List<Configuracion_autorizacion_tipo_usuario> lista = new ArrayList<Configuracion_autorizacion_tipo_usuario>();
		for (Listitem Listitem : listitems) {
			lista.add(new Configuracion_autorizacion_tipo_usuario(null, Listitem.getValue().toString()));   
		}
		return lista;
	}
	
	private List<Configuracion_autorizacion_via_ingreso> convertirConfiguracionViaIngreso(
			Set<Listitem> listitems) {
		List<Configuracion_autorizacion_via_ingreso> lista = new ArrayList<Configuracion_autorizacion_via_ingreso>();
		for (Listitem Listitem : listitems) {
			lista.add(new Configuracion_autorizacion_via_ingreso(null, Listitem.getValue().toString()));   
		}
		return lista;
	}
	

	private List<Detalle_config_servicios_autorizacion> convertirDetalleConfiguracion(
			List<Servicios_disponibles> listadoSeleccionado) {
		List<Detalle_config_servicios_autorizacion> dtt_configs = new ArrayList<Detalle_config_servicios_autorizacion>();
		for (Servicios_disponibles servicios_disponibles : listadoSeleccionado) {
			Detalle_config_servicios_autorizacion dtt_config = new Detalle_config_servicios_autorizacion();
			dtt_config.setCodigo_servicio(servicios_disponibles.getCodigo()); 
			dtt_configs.add(dtt_config); 
		}
		return dtt_configs;
	}
	
	private List<Servicios_contratados> convertirServiciosContratados(List<Detalle_config_servicios_autorizacion> dtt_configs){
		List<Servicios_contratados> listadoSeleccionado = new ArrayList<Servicios_contratados>();
		for (Detalle_config_servicios_autorizacion dtt_config : dtt_configs) {
			Servicios_contratados disponibles = new Servicios_contratados();
			 disponibles.setCodigo_servicio(dtt_config.getCodigo_servicio());
			 listadoSeleccionado.add(disponibles);
		}
		return listadoSeleccionado;
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		configuracion_servicios_autorizacion = (Configuracion_servicios_autorizacion) obj; 
		try {
			tbxNombre
					.setValue(configuracion_servicios_autorizacion.getNombre());
			Utilidades.setValueFrom(lbxEstado,
					configuracion_servicios_autorizacion.getEstado());
			Utilidades.setValueFrom(lbxTipoServicio, configuracion_servicios_autorizacion.getTipo_servicio());
			onSeleccionarTipoServicio(false);
			chkSolicitarTipoProcedimientos.setChecked(configuracion_servicios_autorizacion.isMostrar_tipo_pcd());
		    Utilidades.setValueFrom(lbxModoCobro, configuracion_servicios_autorizacion.getModo_cobro());
			
			Map<String, Object> map_params = new HashMap<String, Object>();
			map_params.put("id_configuracion", configuracion_servicios_autorizacion.getId()); 
			
			// consultamos tipos de usuario
			List<Configuracion_autorizacion_tipo_usuario> dtt_config_tipo_usuarios = configuracion_autorizacion_tipo_usuarioService
					.listar(map_params);
			Utilidades.setValueDesde(lbxTipo_usuarios, dtt_config_tipo_usuarios, "codigo_tipo_usuario");   
			
			List<Configuracion_autorizacion_via_ingreso> dtt_config_via_ingreso = configuracion_autorizacion_via_ingresoService
					.listar(map_params);
			Utilidades.setValueDesde(lbxVia_ingreso, dtt_config_via_ingreso, "codigo_via_ingreso"); 
			
			// consultamos detalles
			List<Detalle_config_servicios_autorizacion> dtt_configs = detalle_config_servicios_autorizacionService
					.listar(map_params);
			inicializarArbolServicios();
			servicios_disponiblesMacro.mostrarDatosArbol(convertirServiciosContratados(dtt_configs));  
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Configuracion_servicios_autorizacion configuracion_servicios_autorizacion = (Configuracion_servicios_autorizacion) obj;
		try {
			int result = configuracion_servicios_autorizacionService
					.eliminar(configuracion_servicios_autorizacion);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}
}