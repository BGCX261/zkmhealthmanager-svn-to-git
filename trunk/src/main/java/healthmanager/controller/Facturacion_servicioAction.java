package healthmanager.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Datos_servicio;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.AdmisionService;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
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
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.service.ArticuloService;

public class Facturacion_servicioAction  extends ZKWindow{
	
	private static final long serialVersionUID = 2183354871530698149L;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_identificacion;
	@View private Textbox tbxNomPaciente;
	@View private Toolbarbutton btnLimpiarPaciente;
	
	@View private Textbox tbxNro_factura;
	@View private Listbox lbxNro_ingreso;
	@View private Datebox dtbxFecha_servicio;
	@View private Textbox tbxNumero_autorizacion;
	@View private Textbox tbxObservacion;
	@View private Textbox tbxTipo;
	
	@View private Grid gridReceta;
	@View private Rows rowsReceta;
	
	@View private Doublebox dbxTotal;
	
	private List<Datos_servicio> lista_datos;
	
	@View private Listbox lbxFormato;

	private Map<String, Object> datos_seleccion = new HashMap<String, Object>();
	private final String[] idsExcluyentes = new String[] { "tbxNro_identificacion",
			"btnLimpiarPaciente","tbxTipo","btCancel","btGuardar","tbxAccion",
			"btNew","lbxFormato","btImprimir"};

	@Override
	public void init() throws Exception {
		
		lista_datos = new ArrayList<Datos_servicio>();
		tbxNro_identificacion.inicializar(tbxNomPaciente, btnLimpiarPaciente);
		parametrizarBandbox();
		listarCombos();
		deshabilitarCampos(true);
		
		boolean ocultarConsulta = false;
		String nro_ingreso = "";
		String nro_identificacion = "";
		String nro_factura = "";
		Map parametros = Executions.getCurrent().getArg();
		if(parametros!=null){
			if(parametros.isEmpty()){
				parametros = null;
			}
		}
		if(parametros!=null){
			ocultarConsulta = (Boolean) parametros.get("ocultarConsulta");
			nro_ingreso = (String) parametros.get("nro_ingreso");
			nro_identificacion = (String) parametros.get("nro_identificacion");
			nro_factura = (String) parametros.get("nro_factura");
		}
		
		if(parametros!=null){
			cargarDatosModuloFactura(nro_ingreso,nro_identificacion,nro_factura,ocultarConsulta);
		}
	}
	
	private void cargarDatosModuloFactura(String nro_ingreso,
			String nro_identificacion, String nro_factura,
			boolean ocultarConsulta)throws Exception {
		
		accionForm(true, "Registrar");
		tbxNro_identificacion.setDisabled(true);
		btnLimpiarPaciente.setVisible(false);
    	lbxNro_ingreso.setDisabled(true);
    	ocultarBotoConsultar(ocultarConsulta);
    	
    	
		Facturacion_servicio facturacion_servicio = new Facturacion_servicio();
		facturacion_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
		facturacion_servicio.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		facturacion_servicio.setNro_factura(nro_factura);
		facturacion_servicio = getServiceLocator().getFacturacion_servicioService().consultar(facturacion_servicio);
    	if(facturacion_servicio!=null){
    		mostrarDatos(facturacion_servicio);
    	}else{
    		Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(empresa.getCodigo_empresa());
            paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            paciente.setNro_identificacion(nro_identificacion);
            paciente = getServiceLocator().getPacienteService().consultar(paciente);
            if(paciente!=null){
            	tbxNro_identificacion.setValue(paciente.getNro_identificacion());
            	tbxNomPaciente.setValue(paciente.getNombreCompleto());
            	datos_seleccion.put("sexo", paciente.getSexo());
            	datos_seleccion.put("tipo_identificacion", paciente.getTipo_identificacion());
            	datos_seleccion.put("fecha_nac", new java.text.SimpleDateFormat("dd/MM/yyyy")
    					.format(paciente.getFecha_nacimiento()));
            	
            	Admision aux2 = new Admision();
            	aux2.setCodigo_empresa(empresa.getCodigo_empresa());
            	aux2.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            	aux2.setNro_identificacion(nro_identificacion);
            	aux2.setNro_ingreso(nro_ingreso);
            	aux2 = getServiceLocator().getServicio(AdmisionService.class).consultar(aux2);
            	
            	if(aux2!=null){
            		////log.info("aux2: "+aux2);
            		
            		Administradora admin = new Administradora();
        			admin.setCodigo_empresa(aux2.getCodigo_empresa());
        			admin.setCodigo_sucursal(aux2.getCodigo_sucursal());
        			admin.setCodigo(aux2.getCodigo_administradora());
        			admin = getServiceLocator().getAdministradoraService().consultar(
        					admin);

            		lbxNro_ingreso.getItems().clear();
            		Listitem listitem = new Listitem();
            		listitem.setSelected(true);
            		listitem.setLabel(aux2.getNro_ingreso() + " -- "
        					+ (admin != null ? admin.getNombre() : ""));
            		listitem.setValue(aux2);
            		cargarAdmisiones(aux2);
            		lbxNro_ingreso.appendChild(listitem);
            		btnLimpiarPaciente.setVisible(false);
            		
            	}
            }
    	}
	}
	
	private void ocultarBotoConsultar(boolean sw) {
		
		if (!sw) {
			((Button) groupboxEditar.getFellow("btCancel")).setVisible(true);
			((Button) groupboxEditar.getFellow("btNew")).setVisible(true);
		} else {
			((Button) groupboxEditar.getFellow("btCancel")).setVisible(false);
			((Button) groupboxEditar.getFellow("btNew")).setVisible(false);
		}
	}
	
	public void listarCombos() {
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("t2.nro_identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("t2.nombre1||' '||t2.nombre2");
		listitem.setLabel("Nombres");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("t2.apellido1||' '||t2.apellido2");
		listitem.setLabel("Apellidos");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_factura");
		listitem.setLabel("Nro registro");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("fecha_servicio");
		listitem.setLabel("Fecha(aaaa-mm-dd)");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	private void parametrizarBandbox() {
		inicializarBandboxPaciente();
	}
	
	private void inicializarBandboxPaciente(){
		tbxNro_identificacion.setBandboxRegistrosIMG(new  BandboxRegistrosIMG<Paciente>(){

			@Override
			public void renderListitem(Listitem listitem, Paciente registro) {
				
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro
						.getTipo_identificacion() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro
						.getNro_identificacion() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre1()
						+ " " + registro.getNombre2()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getApellido1()
						+ " " + registro.getApellido2()));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Paciente> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
					
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						
						parametros.put("limite_paginado", 
						"limit 25 offset 0");
				
						return Facturacion_servicioAction.this
								.getServiceLocator().getPacienteService()
								.listar(parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Paciente registro) {
				Facturacion_servicio facturacion_servicio = new Facturacion_servicio();
				facturacion_servicio.setCodigo_empresa(registro
						.getCodigo_empresa());
				facturacion_servicio.setCodigo_sucursal(registro
						.getCodigo_sucursal());
				facturacion_servicio.setNro_identificacion(registro
						.getNro_identificacion());

				List<Admision> listaAdmisiones = listarAdmisiones(
						facturacion_servicio, false);
				if (listaAdmisiones.isEmpty()) {
					Messagebox
							.show("No se ha registrado el Ingreso del Paciente",
									"Paciente no admisionado",
									Messagebox.OK,
									Messagebox.EXCLAMATION);
					limpiarDatos();
					deshabilitarCampos(true);
					return false;
				}

				bandbox.setValue(registro.getNro_identificacion());
				textboxInformacion.setValue(registro
						.getNombreCompleto());
				datos_seleccion.put("tipo_identificacion",
						registro.getTipo_identificacion());

				deshabilitarCampos(false);

				listarIngresos(lbxNro_ingreso, listaAdmisiones, false);
				Admision aux2 = (!listaAdmisiones.isEmpty() ? listaAdmisiones
						.get(0) : null);
				cargarAdmisiones(aux2);
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				
				limpiarDatos();
				datos_seleccion.remove("tipo_identificacion");
				listarIngresos(lbxNro_ingreso,
						new LinkedList<Admision>(), true);
				deshabilitarCampos(true);
			}
		});
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
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos() {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		tbxNro_identificacion.setValue("");
		tbxNro_identificacion.setDisabled(false);
		deshabilitarCampos(true);
		lista_datos.clear();
		try {
			crearFilas();
			calcularTotal();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		tbxNro_identificacion.setStyle("text-transform:uppercase;background-color:white");
		lbxNro_ingreso.setStyle("background-color:white");
		
		Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
				.getValue());
		
		boolean valida = true;
		
		String mensaje = "Los campos marcados con (*) son obligatorios";
		
		if(tbxNro_identificacion.getText().trim().equals("")){
			tbxNro_identificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (admision == null) {
			lbxNro_ingreso.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		
		if (valida && lista_datos.size() <= 0) {
			mensaje = "Debe crear al menos un registro de servicios";
			valida = false;
		}
		
		if (valida) {
			actualizarPagina();
			for (int i = 0; i < lista_datos.size(); i++) {
				Datos_servicio datos_servicio = lista_datos.get(i);
				if (datos_servicio.getUnidades() <= 0) {
					mensaje = "El valor de las cantidades en el servicio "
							+ datos_servicio.getCodigo_servicio()
							+ " no puede ser menor ni igual a cero";
					valida = false;
					i = lista_datos.size();
				}
				if (datos_servicio.getValor_unitario() <= 0) {
					mensaje = "El valor unitario en el servicio "
							+ datos_servicio.getCodigo_servicio()
							+ " no puede ser menor ni igual a cero";
					valida = false;
					i = lista_datos.size();
				}
				if (valida) {
					for (int j = i + 1; j < lista_datos.size(); j++) {
						Datos_servicio datos_servicioAux = lista_datos.get(j);
						if (datos_servicio.getCodigo_servicio().equals(
								datos_servicioAux.getCodigo_servicio())) {
							valida = false;
							mensaje = "El servicio "
									+ datos_servicio.getCodigo_servicio()
									+ " se encuentra repetido";
							i = lista_datos.size();
							j = lista_datos.size();
						}
					}
				}
			}
		}
		
		if(!valida){
			Messagebox.show(mensaje, 
				usuarios.getNombres()+" recuerde que...", 
				Messagebox.OK, Messagebox.EXCLAMATION);
		}
		
		return valida;
	}
	
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			getServiceLocator().getFacturacion_servicioService().setLimit("limit 25 offset 0");
			
			List<Facturacion_servicio> lista_datos = getServiceLocator().getFacturacion_servicioService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Facturacion_servicio facturacion_servicio : lista_datos) {
				rowsResultado.appendChild(crearFilas(facturacion_servicio, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
			
		} catch (Exception e) {
			
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Facturacion_servicio facturacion_servicio = (Facturacion_servicio)objeto;
		
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(facturacion_servicio.getCodigo_empresa());
		paciente.setCodigo_sucursal(facturacion_servicio.getCodigo_sucursal());
		paciente.setNro_identificacion(facturacion_servicio
				.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		String nombres_paciente = (paciente != null ? paciente.getNombre1()
				+ " " + paciente.getNombre2() : "");
		String apellidos_paciente = (paciente != null ? paciente.getApellido1()
				+ " " + paciente.getApellido2() : "");
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(facturacion_servicio.getNro_factura()+""));
		fila.appendChild(new Label(facturacion_servicio.getTipo_identificacion()+""));
		fila.appendChild(new Label(facturacion_servicio.getNro_identificacion()+""));
		fila.appendChild(new Label(facturacion_servicio.getNro_ingreso()+""));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
		.format(facturacion_servicio.getFecha_servicio()) + ""));
		fila.appendChild(new Label(apellidos_paciente + ""));
		fila.appendChild(new Label(nombres_paciente + ""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(facturacion_servicio);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		String estado_admision = Utilidades.getEstado_admision(facturacion_servicio.getNro_ingreso(), facturacion_servicio.getNro_identificacion(), this);
		if (estado_admision.equals("2")) {
			img.setVisible(false);
		}
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(facturacion_servicio);
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
				final Map datos = new HashMap();
				
				Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
						.getValue());
				
				Facturacion_servicio facturacion_servicio = new Facturacion_servicio();
				facturacion_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
				facturacion_servicio.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				facturacion_servicio.setNro_factura(tbxNro_factura.getValue());
				facturacion_servicio = getServiceLocator()
						.getFacturacion_servicioService().consultar(
								facturacion_servicio);
				if(facturacion_servicio==null){
					facturacion_servicio = new Facturacion_servicio();
					facturacion_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
					facturacion_servicio.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					facturacion_servicio.setNro_factura(tbxNro_factura.getValue());
				}
				facturacion_servicio.setTipo_identificacion((String) datos_seleccion
						.get("tipo_identificacion"));
				facturacion_servicio.setNro_identificacion(tbxNro_identificacion.getValue());
				facturacion_servicio.setCodigo_administradora((String) datos_seleccion
						.get("codigo_administradora"));
				facturacion_servicio.setId_plan((String) datos_seleccion.get("id_plan"));
				facturacion_servicio.setNro_ingreso(admision.getNro_ingreso());
				facturacion_servicio.setFecha_servicio(new Timestamp(dtbxFecha_servicio.getValue().getTime()));
				facturacion_servicio.setNumero_autorizacion(tbxNumero_autorizacion.getValue());
				facturacion_servicio.setObservacion(tbxObservacion.getValue());
				facturacion_servicio.setTipo(tbxTipo.getValue());
				facturacion_servicio.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				facturacion_servicio.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				facturacion_servicio.setCreacion_user(usuarios.getCodigo().toString());
				facturacion_servicio.setUltimo_user(usuarios.getCodigo().toString());
				
				datos.put("facturacion_servicio", facturacion_servicio);
				datos.put("lista_detalle", lista_datos);
				datos.put("accion", tbxAccion.getText());
				
				facturacion_servicio = getServiceLocator()
						.getFacturacion_servicioService().guardar(datos, true);
				
				mostrarDatos(facturacion_servicio);
				
				
				Messagebox
				.show("Los datos se guardaron satisfactoriamente",
						"Informacion ..", Messagebox.OK,
						Messagebox.INFORMATION);
				
				if(this.getParent() instanceof Facturacion_ripsAction){
					Facturacion_ripsAction action = (Facturacion_ripsAction) this
							.getParent();
					action.consultarServiciosFacturas(true);
					this.detach();
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Facturacion_servicio facturacion_servicio = (Facturacion_servicio)obj;
		try{
			deshabilitarCampos(false);
			tbxNro_factura.setValue(facturacion_servicio.getNro_factura());
			
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(facturacion_servicio.getCodigo_empresa());
			paciente.setCodigo_sucursal(facturacion_servicio
					.getCodigo_sucursal());
			paciente.setNro_identificacion(facturacion_servicio
					.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);
			
			tbxNro_identificacion.seleccionarRegistro(paciente,
					facturacion_servicio.getNro_identificacion(),
					(paciente != null ? paciente.getNombreCompleto() : ""));
			datos_seleccion.put("tipo_identificacion", (paciente != null ? paciente.getTipo_identificacion()
					: ""));
			datos_seleccion.put("codigo_administradora",
					facturacion_servicio.getCodigo_administradora());
			datos_seleccion.put("id_plan", facturacion_servicio.getId_plan());
			listarIngresos(lbxNro_ingreso,
					listarAdmisiones(facturacion_servicio, true), false);
			
			Utilidades.seleccionarListitem(lbxNro_ingreso, facturacion_servicio.getNro_ingreso());
			dtbxFecha_servicio.setValue(facturacion_servicio.getFecha_servicio());
			tbxNumero_autorizacion.setValue(facturacion_servicio.getNumero_autorizacion());
			tbxObservacion.setValue(facturacion_servicio.getObservacion());
			tbxTipo.setValue(facturacion_servicio.getTipo());
			
			validarRegistroEditar(facturacion_servicio);
			
			Map paramDatos_servicios = new HashMap();
			paramDatos_servicios.put("codigo_empresa", facturacion_servicio.getCodigo_empresa());
			paramDatos_servicios.put("codigo_sucursal", facturacion_servicio.getCodigo_sucursal());
			paramDatos_servicios.put("nro_factura", facturacion_servicio.getNro_factura());
			lista_datos = getServiceLocator().getDatos_servicioService().listar(paramDatos_servicios);
			rowsReceta.getChildren().clear();
			crearFilas();
			calcularTotal();
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Facturacion_servicio facturacion_servicio = (Facturacion_servicio)obj;
		try{
			int result = getServiceLocator().getFacturacion_servicioService().eliminarActualizarFactura(facturacion_servicio);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}catch(RuntimeException r){
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	private void validarRegistroEditar(Facturacion_servicio facturacion_servicio)
			throws Exception {
		String estado_admision = Utilidades.getEstado_admision(
				facturacion_servicio.getNro_ingreso(),
				facturacion_servicio.getNro_identificacion(), this);
		if(this.getParent() instanceof Facturacion_ripsAction){
			estado_admision = "1";
		}
		if (estado_admision.equals("2")) {
			bloqueoBotonGuardar(true);
		} else {
			bloqueoBotonGuardar(false);
		}
	}

	private void bloqueoBotonGuardar(boolean sw) {
		
		if (!sw) {
			((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(false);
			((Button) groupboxEditar.getFellow("btGuardar"))
					.setImage("/images/Save16.gif");
		} else {
			((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(true);
			((Button) groupboxEditar.getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");
		}
	}
	
	public void deshabilitarCampos(boolean sw) {
		FormularioUtil.deshabilitarComponentes(groupboxEditar, sw,
				idsExcluyentes);
		bloqueoBotonGuardar(sw);
		if (sw) {
			listarIngresos(lbxNro_ingreso, new LinkedList<Admision>(), true);
		}
		if(!sw){
			// Grilla //
			lista_datos.clear();
			try {
				crearFilas();
			} catch (Exception e) {
				MensajesUtil.mensajeError(e, "", this);
			}
			
		}
	}
	
	public void listarIngresos(Listbox listbox, List<Admision> listaAdmisiones,
			boolean select) {
		listbox.getItems().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("-- --");
			listbox.appendChild(listitem);
		}

		for (Admision a : listaAdmisiones) {
			Administradora admin = new Administradora();
			admin.setCodigo_empresa(a.getCodigo_empresa());
			admin.setCodigo_sucursal(a.getCodigo_sucursal());
			admin.setCodigo(a.getCodigo_administradora());
			admin = getServiceLocator().getAdministradoraService().consultar(
					admin);

			listitem = new Listitem();
			listitem.setValue(a);
			listitem.setLabel(a.getNro_ingreso() + " -- "
					+ (admin != null ? admin.getNombre() : ""));
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}
	
	public List<Admision> listarAdmisiones(
			Facturacion_servicio facturacion_servicio, boolean sw) {
		Map paramAdmision = new HashMap();
		paramAdmision.put("codigo_empresa",
				facturacion_servicio.getCodigo_empresa());
		paramAdmision.put("codigo_sucursal",
				facturacion_servicio.getCodigo_sucursal());
		paramAdmision.put("nro_identificacion",
				facturacion_servicio.getNro_identificacion());
		paramAdmision.put("order", "fecha_ingreso desc");
		if (sw) {
			paramAdmision.put("nro_ingreso",
					facturacion_servicio.getNro_ingreso());
		} else {
			paramAdmision.put("estado", "1");
		}

		List<Admision> listaAdmisiones = getServiceLocator()
				.getServicio(AdmisionService.class).listarTabla(paramAdmision);

		return listaAdmisiones;
	}
	
	public void cargarAdmisiones(Admision aux2) {
		if (aux2 != null) {
			deshabilitarCampos(false);

			datos_seleccion.put("codigo_administradora",
					aux2.getCodigo_administradora());
			datos_seleccion.put("id_plan", aux2.getId_plan());
			
			dtbxFecha_servicio.setValue(aux2.getFecha_ingreso());
		}
	}
	
	public void nuevoRegistro() throws Exception {
		Messagebox
				.show("perderá esta informacion que esta digitando, seguro que va a crear un nuevo registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									accionForm(true, "registrar");
								}
							}
						});
	}
	
	public void openArticulo() throws Exception {
		Admision admision = lbxNro_ingreso.getSelectedItem().getValue();
		if(admision==null){
			MensajesUtil.mensajeAlerta("Alerta", "Debe seleccionar un paciente con admision activa");
		}
		Map parametros = new HashMap();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				datos_seleccion.get("codigo_administradora"));
		parametros.put("id_plan",datos_seleccion.get("id_plan"));
		parametros.put("nro_ingreso", admision.getNro_ingreso());
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("grupo", "03");
		parametros.put("ocultaExist", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);

		Component componente = Executions.createComponents(
				"/pages/openArticulo.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR SERVICIOS");
		ventana.setMode("modal");

	}
	@Override
	public void adicionarPcd(Map<String, Object> pcd) throws Exception {
		actualizarPagina();
		
		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(codigo_empresa);
		articulo.setCodigo_sucursal(codigo_sucursal);
		articulo.setCodigo_articulo((String) pcd
				.get("codigo_articulo"));
		articulo = getServiceLocator().getServicio(ArticuloService.class).consultar(articulo);
		
		Datos_servicio datos_servicio = new Datos_servicio();
		datos_servicio.setCodigo_empresa((String) pcd
				.get("codigo_empresa"));
		datos_servicio.setCodigo_sucursal((String) pcd
				.get("codigo_sucursal"));
		datos_servicio.setCodigo_servicio((String) pcd
				.get("codigo_articulo"));
		datos_servicio.setValor_unitario((Double) pcd
				.get("valor_unitario"));
		datos_servicio.setCancelo_copago("N");
		datos_servicio.setReferencia_pyp(articulo != null ? articulo.getReferencia() : "");  
		//datos_servicio.setValor_total((Double) pcd.get("valor_total"));
		datos_servicio.setDescuento((Double) pcd.get("descuento"));
		datos_servicio.setIncremento((Double) pcd.get("incremento"));
		datos_servicio.setValor_real((Double) pcd.get("valor_real"));
		datos_servicio.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
		datos_servicio.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
		datos_servicio.setCreacion_user(usuarios.getCodigo());
		datos_servicio.setUltimo_user(usuarios.getCodigo());
		datos_servicio.setReferencia_pyp(""); 
		////log.info("pcd: "+pcd);
		adicionarDatos_servicio(datos_servicio);
	}
	
	public void adicionarDatos_servicio(Datos_servicio datos_servicio) throws Exception {
		try {
			lista_datos.add(datos_servicio);
			crearFilas();
			// repintarGridCuentas();
		} catch (Exception e) {
			
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void crearFilas() throws Exception {
		rowsReceta.getChildren().clear();
		for (int j = 0; j < lista_datos.size(); j++) {
			Datos_servicio datos_servicio = lista_datos.get(j);
			crearFilaDatos_servicio(datos_servicio, j);
		}
	}
	
	public void crearFilaDatos_servicio(final Datos_servicio datos_servicio, int j)throws Exception {
		final int index = j;
		
		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(empresa.getCodigo_empresa());
		articulo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		articulo.setCodigo_articulo(datos_servicio.getCodigo_servicio());
		articulo = getServiceLocator().getArticuloService().consultar(articulo);
		
		String codigo_articulo = datos_servicio.getCodigo_servicio();
		String nombre_articulo = (articulo!=null?articulo.getNombre1():"");
		double unidades = datos_servicio.getUnidades();
		double valor_unitario = datos_servicio.getValor_unitario();
		double valor_total = datos_servicio.getValor_total();
		double descuento = datos_servicio.getDescuento();
		double incremento = datos_servicio.getIncremento();
		double valor_real = datos_servicio.getValor_real();
		
		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo //
		Cell cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_procedimiento = new Textbox(codigo_articulo);
		tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("codigo_articulo_" + j);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setReadonly(true);
		cell.appendChild(tbxCodigo_procedimiento);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_procedimiento = new Textbox(nombre_articulo);
		tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_articulo_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna cantidad recetada //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxUnidades = new Doublebox(unidades);
		tbxUnidades.setInplace(true);
		tbxUnidades.setId("unidades_" + j);
		tbxUnidades.setFormat("#,##0");
		tbxUnidades.setHflex("1");
		tbxUnidades.setReadonly(false);
		cell.appendChild(tbxUnidades);
		fila.appendChild(cell);

		// Columna cantidad recetada //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValor_unitario = new Doublebox(valor_unitario);
		tbxValor_unitario.setInplace(true);
		tbxValor_unitario.setId("valor_unitario_" + j);
		tbxValor_unitario.setFormat("#,##0.00");
		tbxValor_unitario.setHflex("1");
		tbxValor_unitario.setReadonly(false);
		cell.appendChild(tbxValor_unitario);
		fila.appendChild(cell);

		// Columna cantidad recetada //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValor_total = new Doublebox(valor_total);
		tbxValor_total.setInplace(true);
		tbxValor_total.setId("valor_total_" + j);
		tbxValor_total.setFormat("#,##0.00");
		tbxValor_total.setHflex("1");
		tbxValor_total.setReadonly(true);
		cell.appendChild(tbxValor_total);
		
		final Doublebox tbxDescuento = new Doublebox(descuento);
		tbxDescuento.setId("descuento_" + j);
		tbxDescuento.setVisible(false);
		cell.appendChild(tbxDescuento);

		final Doublebox tbxIncremento = new Doublebox(incremento);
		tbxIncremento.setId("incremento_" + j);
		tbxIncremento.setVisible(false);
		cell.appendChild(tbxIncremento);

		final Doublebox tbxValor_real = new Doublebox(valor_real);
		tbxValor_real.setId("valor_real_" + j);
		tbxValor_real.setVisible(false);
		cell.appendChild(tbxValor_real);
		
		fila.appendChild(cell);
		
		// Columna borrar //
		cell = new Cell();
		cell.setAlign("left");
		cell.setValign("top");
		Image img = new Image("/images/borrar.gif");
		img.setId("img_" + j);
		img.setTooltiptext("Quitar registro");
		img.setStyle("cursor:pointer");
		cell.appendChild(img);
		fila.appendChild(cell);
		
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									actualizarPagina();
									lista_datos.remove(index);
									crearFilas();
								}
							}
						});
			}
		});
		
		tbxUnidades.addEventListener("onChange", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				double cant = (tbxUnidades.getValue() != null ? tbxUnidades
						.getValue() : 0.0);
				double valor_unit = (tbxValor_unitario.getValue() != null ? tbxValor_unitario
						.getValue() : 0.0);

				if (tbxUnidades.getValue() != null) {
					tbxValor_total.setValue((cant * valor_unit));
					calcularTotal();
				}
			}
		});
		
		tbxValor_unitario.addEventListener("onChange", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				double cant = (tbxUnidades.getValue() != null ? tbxUnidades
						.getValue() : 0.0);
				double valor_unit = (tbxValor_unitario.getValue() != null ? tbxValor_unitario
						.getValue() : 0.0);

				if (tbxValor_unitario.getValue() != null) {
					tbxValor_total.setValue((cant * valor_unit));
					calcularTotal();
				}
			}
		});
		
		fila.setId("fila_" + j);

		rowsReceta.appendChild(fila);

		gridReceta.setVisible(true);
		gridReceta.setMold("paging");
		gridReceta.setPageSize(20);
		gridReceta.applyProperties();
		gridReceta.invalidate();

	}
	
	public void actualizarPagina() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Datos_servicio datos_servicio = lista_datos.get(i);

			Row fila = (Row) rowsReceta.getFellow("fila_" + i);
			Doublebox tbxUnidades = (Doublebox) fila
					.getFellow("unidades_" + i);
			Doublebox tbxValor_unitario = (Doublebox) fila
					.getFellow("valor_unitario_" + i);
			Doublebox tbxValor_total = (Doublebox) fila
					.getFellow("valor_total_" + i);
			
			datos_servicio.setUnidades(tbxUnidades.getValue().intValue());
			datos_servicio.setValor_unitario(tbxValor_unitario.getValue());
			datos_servicio.setValor_total(tbxValor_total.getValue());
			
			lista_datos.set(i, datos_servicio);
		}
	}
	
	public void selectedAdmision(Listitem listitem) throws Exception {
		Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
				.getValue());

		if (admision == null) {
			limpiarDatos();
			deshabilitarCampos(true);
		} else {
			cargarAdmisiones(admision);
		}
	}
	
	public void calcularTotal()throws Exception{
		actualizarPagina();
		dbxTotal.setValue(0.0);
		double sum = 0;
		for (Datos_servicio datos_servicio : lista_datos) {
			sum+=datos_servicio.getValor_total();
		}
		dbxTotal.setValue(sum);
	}
	
	public void imprimir(String nro_factura) throws Exception {
		if (nro_factura.equals("")) {
			Messagebox.show("No se ha guardado el registro",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Facturacion_servicio");
		paramRequest.put("nro_factura", nro_factura);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

}
