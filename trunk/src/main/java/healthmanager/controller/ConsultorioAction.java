/*
 * consultorioAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Consultorio_prestador;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
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
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class ConsultorioAction extends ZKWindow implements WindowRegistrosIMG{

//	private static Logger log = Logger.getLogger(ConsultorioAction.class);
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_consultorio;
	@View private Textbox tbxNombre;
	@View(isMacro = true)private BandboxRegistrosMacro tbxCodigo_centro;
	@View private Textbox tbxInfoCentro;
	@View private Toolbarbutton btnLimpiarCentro;
	
	private List<Map<String, Object>> lista_datos;
	@View private Grid gridPrestadores;
	@View private Rows rowsPrestadores;
	
	private List<String> lista_seleccionados = new ArrayList<String>();
	
	private final String[] idsExcluyentes = { "btCancel", "btGuardar",
			"tbxAccion", "btNew" };

	
	@Override
	public void init(){
		listarCombos();
		lista_datos = new ArrayList<Map<String, Object>>();
		parametrizarBandbox();
	}
	
	private void parametrizarBandbox() {
		parametrizarBandboxCentro();
	}
	
	private void parametrizarBandboxCentro() {
		tbxCodigo_centro.inicializar(tbxInfoCentro,
				btnLimpiarCentro);
		tbxCodigo_centro
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {
						listitem.appendChild(new Listcell(registro.getCodigo_centro()));
						listitem.appendChild(new Listcell(registro.getNombre_centro()));
					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa", empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						parametros.put("limite_paginado", "limit 25 offset 0");
						return getServiceLocator().getCentro_atencionService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Centro_atencion registro) {
						bandbox.setValue(registro.getCodigo_centro());
						textboxInformacion.setValue(registro.getNombre_centro());
						
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
		listitem.setValue("cons.codigo_consultorio");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("cons.nombre");
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
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
		lista_seleccionados.clear();
		
		lista_datos.clear();
		crearFilas();
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		tbxNombre.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(tbxNombre.getText().trim().equals("")){
			tbxNombre.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
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
			
			getServiceLocator().getConsultorioService().setLimit("limit 25 offset 0");
			
			List<Consultorio> lista_datos = getServiceLocator().getConsultorioService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Consultorio consultorio : lista_datos) {
				rowsResultado.appendChild(crearFilas(consultorio, this));
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
		
		final Consultorio consultorio = (Consultorio)objeto;
		
		Centro_atencion centro_atencion = new Centro_atencion();
		centro_atencion.setCodigo_empresa(consultorio.getCodigo_empresa());
		centro_atencion.setCodigo_sucursal(consultorio.getCodigo_sucursal());
		centro_atencion.setCodigo_centro(consultorio.getCodigo_centro());
		centro_atencion = getServiceLocator().getCentro_atencionService().consultar(centro_atencion);
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(consultorio.getCodigo_consultorio()+""));
		fila.appendChild(new Label(consultorio.getNombre()+""));
		fila.appendChild(new Label((centro_atencion!=null?centro_atencion.getNombre_centro():"")));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(consultorio);
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
								eliminarDatos(consultorio);
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
				
				Consultorio consultorio = new Consultorio();
				consultorio.setCodigo_empresa(empresa.getCodigo_empresa());
				consultorio.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				consultorio.setCodigo_consultorio(tbxCodigo_consultorio.getValue());
				consultorio.setNombre(tbxNombre.getValue());
				consultorio.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				consultorio.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				consultorio.setCreacion_user(usuarios.getCodigo().toString());
				consultorio.setUltimo_user(usuarios.getCodigo().toString());
				consultorio.setCodigo_centro((!tbxCodigo_centro.getValue().equals("")?tbxCodigo_centro.getValue():null));
				
				final Map datos = new HashMap();
				datos.put("consultorio", consultorio);
//				datos.put("lista_detalle", lista_datos);
				datos.put("accion", tbxAccion.getText());
				consultorio = getServiceLocator().getConsultorioService().guardar(datos);
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					accionForm(true,"registrar");
				}else{
					mostrarDatos(consultorio);
				}
				
				MensajesUtil.mensajeInformacion("Informacion ..","Los datos se guardaron satisfactoriamente");
			}
			
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Consultorio consultorio = (Consultorio)obj;
		try{
			tbxCodigo_consultorio.setValue(consultorio.getCodigo_consultorio());
			tbxNombre.setValue(consultorio.getNombre());
			if(consultorio.getCodigo_centro()!=null){
				Centro_atencion centro_atencion = new Centro_atencion();
				centro_atencion.setCodigo_empresa(consultorio.getCodigo_empresa());
				centro_atencion.setCodigo_sucursal(consultorio.getCodigo_sucursal());
				centro_atencion.setCodigo_centro(consultorio.getCodigo_centro());
				centro_atencion = getServiceLocator().getCentro_atencionService().consultar(centro_atencion);
				tbxCodigo_centro.setValue(consultorio.getCodigo_centro());
				tbxInfoCentro.setValue((centro_atencion!=null?centro_atencion.getNombre_centro():""));
			}else{
				tbxCodigo_centro.setValue("");
				tbxInfoCentro.setValue("");
			}
			
			
//			Map paramConsultorio_prestador = new HashMap();
//			paramConsultorio_prestador.put("codigo_empresa", consultorio.getCodigo_empresa());
//			paramConsultorio_prestador.put("codigo_sucursal", consultorio.getCodigo_sucursal());
//			paramConsultorio_prestador.put("codigo_consultorio", consultorio.getCodigo_consultorio());
//			List<Consultorio_prestador> listaConsultorio_prestador = getServiceLocator().getConsultorio_prestadorService().listar(paramConsultorio_prestador);
//			for (Consultorio_prestador consultorio_prestador : listaConsultorio_prestador) {
//				
//				Prestadores prestadores = new Prestadores();
//				prestadores.setCodigo_empresa(consultorio_prestador.getCodigo_empresa());
//				prestadores.setCodigo_sucursal(consultorio_prestador.getCodigo_sucursal());
//				prestadores.setNro_identificacion(consultorio_prestador.getCodigo_prestador());
//				prestadores = getServiceLocator().getPrestadoresService().consultar(prestadores);
//				Map<String, Object> bean = cargarPrestadores(prestadores);
//
//				lista_datos.add(bean);
//				
//				lista_seleccionados.add(prestadores.toString());
//			}
//			crearFilas();
			
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Consultorio consultorio = (Consultorio)obj;
		try{
			int result = getServiceLocator().getConsultorioService().eliminar(consultorio);
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
	
	public void abrirWindowPrestadores() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String columnas = "Codigo#100px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"CONSULTAR PRESTADORES", Paquetes.HEALTHMANAGER,
				"PrestadoresDao.listar", this, columnas,
				parametros);
		windowRegistros.mostrarWindow(lista_seleccionados);
	}
	
	@Override
	public void onSeleccionarRegistro(Object registro) {
		Prestadores prestadores = (Prestadores) registro;
		Map<String, Object> bean = cargarPrestadores(prestadores);
		adicionarDetalleListaPrestadores(bean);
	}
	
	@Override
	public Object[] celdasListitem(Object registro) {
		Prestadores prestadores = (Prestadores) registro;
		return new Object[] {
				prestadores.getNro_identificacion(),
				prestadores.getNombres()+" "+prestadores.getApellidos()};
	}
	
	public void adicionarDetalleListaPrestadores(Map<String, Object> bean){
		try {
			lista_datos.add(bean);
			crearFilas();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	// Este metodo genera las filas nuevamente //
	public void crearFilas() {
		rowsPrestadores.getChildren().clear();
		for (int j = 0; j < lista_datos.size(); j++) {
			Map<String, Object> bean = lista_datos.get(j);
			crearFilaDetallePrestadores(bean, j);
		}
	}
	
	// Metodo para crear la grilla cuando se consulte los servicios cargados //
	public void crearFilaDetallePrestadores(Map<String, Object> bean, int j){
		
		final int index = j;
		Prestadores prestadores = (Prestadores) bean.get("prestadores");
		
		String codigo_user = (prestadores!=null?prestadores.getNro_identificacion():"");
		String nombre_user = (prestadores!=null?prestadores.getNombres()+" "+prestadores.getApellidos():"");
		
		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo barrio //
		Cell cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_barrio = new Textbox(codigo_user);
		tbxCodigo_barrio.setInplace(true);
		tbxCodigo_barrio.setId("codigo_prestador_" + j);
		tbxCodigo_barrio.setHflex("1");
		tbxCodigo_barrio.setReadonly(true);
		cell.appendChild(tbxCodigo_barrio);
		fila.appendChild(cell);
		
		// Columna nombre barrio //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_barrio = new Textbox(nombre_user);
		tbxNombre_barrio.setInplace(true);
		tbxNombre_barrio.setId("nombre_prestador_" + j);
		tbxNombre_barrio.setHflex("1");
		tbxNombre_barrio.setReadonly(true);
		cell.appendChild(tbxNombre_barrio);
		fila.appendChild(cell);
		
		// Columna borrar //
		cell = new Cell();
		cell.setAlign("left");
		cell.setValign("top");
		Image img = new Image("/images/borrar.gif");
		img.setId("img_prestador_" + j);
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
									lista_datos.remove(index);
									crearFilas();
								}
							}
						});
			}
		});
		
		fila.setId("fila_prestadores_" + j);

		rowsPrestadores.appendChild(fila);

		gridPrestadores.setVisible(true);
		gridPrestadores.setMold("paging");
		gridPrestadores.setPageSize(20);
		gridPrestadores.applyProperties();
		gridPrestadores.invalidate();
	}
	
	private Map<String, Object> cargarPrestadores(Prestadores prestadores){
		Map<String, Object> bean = new HashMap<String, Object>();
		
		Consultorio_prestador consultorio_prestadores = new Consultorio_prestador();
		consultorio_prestadores.setCodigo_empresa(prestadores.getCodigo_empresa());
		consultorio_prestadores.setCodigo_sucursal(prestadores.getCodigo_sucursal());
		consultorio_prestadores.setCodigo_prestador(prestadores.getNro_identificacion());
		
		bean.put("prestadores", prestadores);
		bean.put("consultorio_prestador", consultorio_prestadores);
		
		return bean;
		
	}
}