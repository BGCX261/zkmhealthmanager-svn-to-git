/*
 * porcentaje_adicional_medicamentoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Porcentaje_adicional_medicamento;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Doublebox;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Tercero;

public class Porcentaje_adicional_medicamentoAction extends ZKWindow{

	private static Logger log = Logger.getLogger(Porcentaje_adicional_medicamentoAction.class);
	
	
	//Componentes //
//	@View private Listbox lbxParameter;
//	@View private Textbox tbxValue;
//	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
//	@View private Groupbox groupboxConsulta;
//	@View private Rows rowsResultado;
//	@View private Grid gridResultado;
	
	@View(isMacro = true) private BandboxRegistrosMacro tbxNro_identificacion;
	@View private Textbox tbxNombPaciente;
//	@View private Textbox tbxCodigo_med;
//	@View private Doublebox dbxPorcentaje;
//	@View private Datebox dtbxDelete_date;
//	@View private Textbox tbxDelete_user;
	
	/* Medicamentos */
	@View private Rows rowsMedicamentos;
	@View private Grid gridMedicamentos;
	
	@View private Button btGuardar;
	
	@View private Button btnAddMedicamento;
	
	private final String[] idsExcluyentes = {};


	private Paciente dato;
	
	private List<Porcentaje_adicional_medicamento> adicional_medicamentosEliminar;

	
	@Override
	public void init(){
		parametrizarBanbbox();
	}
	
	
	private void parametrizarBanbbox() {
		parametrizarBandboxPaciente();
	}

 
	private void parametrizarBandboxPaciente() {
		tbxNro_identificacion.inicializar((Textbox)getFellow("tbxNombPaciente"), (Toolbarbutton)getFellow("btnLimpiarPaciente")); 
		tbxNro_identificacion 
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
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

						return getServiceLocator().getPacienteService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {
						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro
								.getNombreCompleto());
						seleccionarPaciente(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						seleccionarPaciente(null);
					}
				});
	}



	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		tbxNro_identificacion.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";
		if(tbxNro_identificacion.getText().trim().equals("")){
			tbxNro_identificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(rowsMedicamentos.getChildren().isEmpty()){
			msj = "Debe agregar un porcentaje adicional.";
			valida = false;
		}else{
		   int i = 1;
		   for (Component component : rowsMedicamentos.getChildren()) {
			  if(component instanceof Row){
				  Row row = (Row) component;
				  Porcentaje_adicional_medicamento adicional_medicamento = row.getValue();
				  if(adicional_medicamento.getPorcentaje() == 0d){
					  msj = "El porcentaje no puede ser igual ha cero, en la line " + i;
					  valida = false;
				  }
				 i++; 
			  }
		   }
		}
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"" + msj);
		}
		
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
//		try {
//			
//			Map<String,Object> parameters = new HashMap<String,Object>();
//			parameters.put("parameter", "");
//			parameters.put("value", "");
//			
//			getServiceLocator().getPorcentaje_adicional_medicamentoService().setLimit("limit 25 offset 0");
//			
//			List<Porcentaje_adicional_medicamento> lista_datos = getServiceLocator().getPorcentaje_adicional_medicamentoService().listar(parameters);
//			rowsMedicamentos.getChildren().clear();
//			
////			for (Porcentaje_adicional_medicamento porcentaje_adicional_medicamento : lista_datos) {
//////				rowsMedicamentos.appendChild(crearFilas(porcentaje_adicional_medicamento, this));
////			}
//			
//			gridMedicamentos.setVisible(true);
//			gridMedicamentos.setMold("paging");
//			gridMedicamentos.setPageSize(20);
//			
//			gridMedicamentos.applyProperties();
//			gridMedicamentos.invalidate();
//			
//		} catch (Exception e) {
//			MensajesUtil.mensajeError(e, "", this);
//		}
	}
	
	
	public void buscarPaciente(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", 
					"limit 25 offset 0");

			List<Paciente> list = getServiceLocator().getPacienteService()
					.listar(parameters);

			lbx.getItems().clear();

			for (Paciente dato : list) {
				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre1() + " "
						+ dato.getNombre2()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellido1() + " "
						+ dato.getApellido2()));
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

	public void selectedPaciente(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNro_identificacion.setValue("");
			tbxNombPaciente.setValue("");
			dato = null; 
			rowsMedicamentos.getChildren().clear(); 
			btGuardar.setDisabled(true);
			btnAddMedicamento.setDisabled(true);
			adicional_medicamentosEliminar = new ArrayList<Porcentaje_adicional_medicamento>();
		} else {
			dato = (Paciente) listitem.getValue(); 
			adicional_medicamentosEliminar = new ArrayList<Porcentaje_adicional_medicamento>();
			Elemento elemento = new Elemento();
			elemento.setCodigo(dato.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxNro_identificacion.setValue(dato.getNro_identificacion());
			tbxNombPaciente.setValue(dato.getNombreCompleto());
			
			
			
			btGuardar.setDisabled(false);
			btnAddMedicamento.setDisabled(false);
		}
		tbxNro_identificacion.close();
	}
	
	public void seleccionarPaciente(Paciente paciente){
		try {
			rowsMedicamentos.getChildren().clear(); 
			adicional_medicamentosEliminar = new ArrayList<Porcentaje_adicional_medicamento>();
			dato = paciente;
			if(paciente != null){
				btGuardar.setDisabled(false);
				btnAddMedicamento.setDisabled(false);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", codigo_empresa);
				map.put("codigo_sucursal", codigo_sucursal);
				map.put("nro_identificacion", paciente.getNro_identificacion()); 
				List<Porcentaje_adicional_medicamento> adicional_medicamentos = getServiceLocator().getPorcentaje_adicional_medicamentoService().listar(map);
				for (Porcentaje_adicional_medicamento porcentaje_adicional_medicamento : adicional_medicamentos) {
						 rowsMedicamentos.appendChild(crearFilas(porcentaje_adicional_medicamento));
				}
			}else{
				btGuardar.setDisabled(true);
				btnAddMedicamento.setDisabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void openArticulo() throws Exception {
		Map parametros = new HashMap();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("codigo_administradora", dato.getCodigo_administradora());
//		parametros.put("id_plan", dato.getId_plan());
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("grupo", "01");
		parametros.put("ocultaExist", "");
		parametros.put("ocultaValor", "");

		Component componente = Executions.createComponents(
				"/pages/openArticulo.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR MEDICAMENTOS ");
		ventana.setMode("modal");
	}
	
	@Override
	public void adicionarPcd(Map<String, Object> pcd) throws Exception {
		String codigo_medicamento = (String) pcd.get("codigo_articulo");
		
		/* agregamos el porcentaje adicional */
		Porcentaje_adicional_medicamento adicional_medicamento = new Porcentaje_adicional_medicamento();
		adicional_medicamento.setCodigo_empresa(codigo_empresa);
		adicional_medicamento.setCodigo_sucursal(codigo_sucursal);
		adicional_medicamento.setCodigo_med(codigo_medicamento);
		adicional_medicamento.setNro_identificacion(dato.getNro_identificacion());
		adicional_medicamento.setPorcentaje(0d);
		adicional_medicamento.setCreacion_user(usuarios.getCodigo());
		adicional_medicamento.setCreacion_date(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		adicional_medicamento.setUltimo_update(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		adicional_medicamento.setUltimo_user(usuarios.getCodigo());
		
		/* adicionamos para porcentaje adiconal */
		rowsMedicamentos.appendChild(crearFilas(adicional_medicamento));
	}
	
	public Row crearFilas(Object objeto)throws Exception{
		final Row fila = new Row();
		
		
		final Porcentaje_adicional_medicamento porcentaje_adicional_medicamento = (Porcentaje_adicional_medicamento)objeto;
		fila.setValue(porcentaje_adicional_medicamento);
		
		Hbox hbox = new Hbox();
//		Space space = new Space();
		
		
		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(codigo_empresa);
		articulo.setCodigo_sucursal(codigo_sucursal);
		articulo.setCodigo_articulo(porcentaje_adicional_medicamento.getCodigo_med());
		articulo = getServiceLocator().getArticuloService().consultar(articulo);
		
		/* consultamos el fabricante */
		Tercero ter = new Tercero();
		ter.setCodigo_empresa(articulo.getCodigo_empresa());
		ter.setCodigo_sucursal(articulo.getCodigo_sucursal());
		ter.setNro_identificacion(articulo.getFabricante());
		ter = getServiceLocator().getTerceroService().consultar(ter);
		String nombre_fabricante = "*";
		if (ter != null) {
			nombre_fabricante = ter.getNombre1() + " " + ter.getApellido1()
					+ " " + ter.getApellido2();
		}
		
		/* aqui va el porcentaje */
        Doublebox doublebox = new Doublebox(0);
        doublebox.setWidth("90%"); 
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(porcentaje_adicional_medicamento.getCodigo_med()+""));
		fila.appendChild(new Label(articulo != null ? articulo.getNombre1() : ""));
		fila.appendChild(new Label(articulo != null ? articulo.getNombre2() : ""));
		fila.appendChild(new Label(nombre_fabricante+""));
		fila.appendChild(doublebox);
		
		doublebox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				Doublebox doublebox = (Doublebox)event.getTarget();
                Double valorNuevo = doublebox.getValue();
                if(valorNuevo != null){ 
                	porcentaje_adicional_medicamento.setPorcentaje(valorNuevo);
                }else{
                	doublebox.setValue(0d);
                	porcentaje_adicional_medicamento.setPorcentaje(0d);
                }
			}
		});
		doublebox.setValue(porcentaje_adicional_medicamento.getPorcentaje());
	
//		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(porcentaje_adicional_medicamento);
			}
		});
//		hbox.appendChild(img);
		
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
								rowsMedicamentos.removeChild(fila);
								adicional_medicamentosEliminar.add(porcentaje_adicional_medicamento);
							}
						}
					});
			}
		});
//		hbox.appendChild(space);
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
//				Porcentaje_adicional_medicamento porcentaje_adicional_medicamento = new Porcentaje_adicional_medicamento();
//				porcentaje_adicional_medicamento.setCodigo_empresa(empresa.getCodigo_empresa());
//				porcentaje_adicional_medicamento.setCodigo_sucursal(sucursal.getCodigo_sucursal());
//				porcentaje_adicional_medicamento.setNro_identificacion(tbxNro_identificacion.getValue());
////				porcentaje_adicional_medicamento.setCodigo_med(tbxCodigo_med.getValue());
////				porcentaje_adicional_medicamento.setPorcentaje((dbxPorcentaje.getValue()!=null?dbxPorcentaje.getValue():0.00));
//				porcentaje_adicional_medicamento.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
//				porcentaje_adicional_medicamento.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
//				porcentaje_adicional_medicamento.setCreacion_user(usuarios.getCodigo().toString());
//				porcentaje_adicional_medicamento.setUltimo_user(usuarios.getCodigo().toString());
////				porcentaje_adicional_medicamento.setDelete_user(tbxDelete_user.getValue());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", rowsMedicamentos.getChildren());
				map.put("listDel", adicional_medicamentosEliminar);
				getServiceLocator().getPorcentaje_adicional_medicamentoService().guardar(map);
				
		        MensajesUtil.mensajeInformacion("Informacion ..","Los datos se guardaron satisfactoriamente");
			}		
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Porcentaje_adicional_medicamento porcentaje_adicional_medicamento = (Porcentaje_adicional_medicamento)obj;
		try{
			tbxNro_identificacion.setValue(porcentaje_adicional_medicamento.getNro_identificacion());
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Porcentaje_adicional_medicamento porcentaje_adicional_medicamento = (Porcentaje_adicional_medicamento)obj;
		try{
			int result = getServiceLocator().getPorcentaje_adicional_medicamentoService().eliminar(porcentaje_adicional_medicamento);
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