package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Anexo3_entidad;

import java.lang.reflect.Field;
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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;

import com.framework.res.LabelAlign;
import com.framework.res.LabelState;
import com.framework.res.LabelAlign.AlignText;

public class ChangePrestadorServicioAction extends ZKWindow {
	
	private static Logger log = Logger.getLogger(ChangePrestadorServicioAction.class);
	
	/* contenedor de valores */
	public static final String  _VALOR        = "valor"; 
	public static final String  _TIPO_PCD     = "tipo_pcd"; 
	public static final String  _CANTIDAD     = "cantidad"; 
	public static final String  _NOMBRE       = "nombre"; 
	public static final String  _CODIGO_CUPS  = "codigo_cups"; 
	public static final String  _AUTORIZADO   = "autorizado"; 
	public static final String  _ESTADO_COBRO = "estado_cobro";
	public static final String  _ADMIN        = "admin";
	

	@View 	private Borderlayout groupboxEditar;
	@View	private Grid gridProcedimiento;
	@View	private Rows rowProcedimientos;
	@View	private Toolbarbutton btGuardar;
	@View	private Toolbarbutton btCancel;
	
	
	private Anexo3_entidad anexo_3;

	private List<Map> list_procedimientos;

	private Administradora administradoraGlobal;

	@Override
	public void init() {
		
		loadDataFomParent();
	}

	private void loadDataFomParent() {
		Map datos = Executions.getCurrent().getArg();
		anexo_3 = (Anexo3_entidad) datos.get("Anexo3");
		try { 
			loadPrestadorGlobal();
			cargarProcedimeintos();
		} catch (Exception e) {e.printStackTrace();} 
	}
 
	private void loadPrestadorGlobal() {
		Administradora administradora = new Administradora();
		administradora.setCodigo(anexo_3.getCodigo_ips()); 
		administradora.setCodigo_empresa(codigo_empresa);
		administradora.setCodigo_sucursal(codigo_sucursal);
		administradoraGlobal = getServiceLocator().getAdministradoraService().consultar(administradora);
	} 

	private void cargarProcedimeintos() throws Exception{
		try {
			
			rowProcedimientos.getChildren().clear();
			list_procedimientos = getListFromProcedimeintos(); 
			
			gridProcedimiento.setVisible(true);
			gridProcedimiento.setMold("paging");
			gridProcedimiento.setPageSize(20);
			
			gridProcedimiento.applyProperties();
			gridProcedimiento.invalidate();
			gridProcedimiento.setVisible(true);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	
	private List<Map> getListFromProcedimeintos() throws Exception{ 
		List<Map> list = new ArrayList<Map>();
		for (int i = 1; i <= 20; i++) {
			String endNumber = "" + i;
			
			Field fieldValue = Anexo3_entidad.class.getDeclaredField("valor" + endNumber);
			Field fieldTipo = Anexo3_entidad.class.getDeclaredField("tipo_pcd" + endNumber);
			Field fieldCantidad = Anexo3_entidad.class.getDeclaredField("cantidad" + endNumber);
			Field fieldNombre = Anexo3_entidad.class.getDeclaredField("nombre" + endNumber);
			Field fieldCodigo_cups = Anexo3_entidad.class.getDeclaredField("codigo_cups" + endNumber);
			Field fieldAuto = Anexo3_entidad.class.getDeclaredField("autorizado_" + endNumber);
			Field fieldestado_pago = Anexo3_entidad.class.getDeclaredField("estado_cobro" + endNumber);
			
			fieldCodigo_cups.setAccessible(true);
			
			String codigo_procedimiento = (String) fieldCodigo_cups.get(anexo_3);
			if(!codigo_procedimiento.isEmpty()){
				fieldValue.setAccessible(true);
				fieldTipo.setAccessible(true);
				fieldCantidad.setAccessible(true);
				fieldNombre.setAccessible(true);
				fieldAuto.setAccessible(true);
				fieldestado_pago.setAccessible(true);
				
				/* montamos valores en el mapa */
				Map map = new HashMap();
				map.put(_AUTORIZADO, fieldAuto.get(anexo_3));
				map.put(_CANTIDAD, fieldCantidad.get(anexo_3));
				map.put(_CODIGO_CUPS, fieldCodigo_cups.get(anexo_3));
				map.put(_ESTADO_COBRO, fieldestado_pago.get(anexo_3));
				map.put(_NOMBRE, fieldNombre.get(anexo_3));
				map.put(_TIPO_PCD, fieldTipo.get(anexo_3));
				map.put(_VALOR, fieldValue.get(anexo_3));
				list.add(map);
				rowProcedimientos.appendChild(crearFilas(map, this));
			}
		}
		return list;
	}

	public Row crearFilas(Object objeto, Component componente)throws Exception{
		final Row fila = new Row();
		
		final Map map = (Map)objeto;
		map.put(_ADMIN, administradoraGlobal != null ? administradoraGlobal.getCodigo() : "");
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		Bandbox bandbox = new Bandbox();
		bandbox.setValue(administradoraGlobal != null ?  administradoraGlobal.getCodigo() : "");
		bandbox.setReadonly(true);
		bandbox.setWidth("120px"); 
		
		LabelState  labelNameAdministradora = new LabelState(administradoraGlobal != null ? administradoraGlobal.getNombre() : "", true);
		loadBandBox(bandbox, labelNameAdministradora, map);
		
		fila.setStyle("text-align: justify;nowrap:nowrap;" );
		fila.appendChild(new Label(map.get(_CODIGO_CUPS) + ""));
		fila.appendChild(new Label(map.get(_NOMBRE) + ""));
		fila.appendChild(new LabelAlign(map.get(_CANTIDAD) + "", AlignText.RIGHT));
		fila.appendChild(bandbox);
		fila.appendChild(labelNameAdministradora);
		
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setVisible(false);
		img.setTooltiptext("Imprimir");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
			}
		});
		hbox.appendChild(img);
		
		
		fila.appendChild(hbox);
		
		return fila;
	}
	

	public void buscarPrestador(String value, Listbox lbx, Map map) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			
			/* parametros de busqueda de procedimiento */
			parameters.put("inner_process", "_sowUp");
			parameters.put("codigo_cups", map.get(_CODIGO_CUPS)); 
			parameters.put("tipo", map.get(_TIPO_PCD));
			/* agregacion de procedimiento */

			getServiceLocator().getAdministradoraService().setLimit(
					"limit 25 offset 0");

			List<Map> list = getServiceLocator()
					.getAdministradoraService().listarDesdeContratos(parameters);

			lbx.getItems().clear();

			for (Map dato : list) {
				
				String codigo = (String) dato.get("codigo");
				String nit = (String) dato.get("nit");
				String nombre = (String) dato.get("nombre");

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(codigo + ""));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(nit + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(nombre + ""));
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
	
	public void selectedPrestador(Listitem listitem, Bandbox bandbox, Label textbox, Map map) { 
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
			map.put(_ADMIN, "");
		} else {
			Map dato = (HashMap) listitem.getValue();
			
			String codigo = (String) dato.get("codigo");
//			String nit = (String) dato.get("nit");
			String nombre = (String) dato.get("nombre");
//			String codigo_dpto = (String) dato.get("codigo_dpto");
//			String direccion = (String) dato.get("direccion");
//			String codigo_municipio = (String) dato.get("codigo_municipio");
			map.put(_ADMIN, codigo);
			bandbox.setValue(codigo);
			textbox.setValue(nombre);
		}
		bandbox.close();
	}
	
	private void loadBandBox(final Bandbox bandbox, final Label textboxNombre, final Map map) { 
		bandbox.setAutodrop(true);
		Bandpopup bandpopup = new Bandpopup();
		Vbox vbox = new Vbox();
		bandpopup.appendChild(vbox);
		
		/* creamos la barra */
		Toolbar toolbar = new Toolbar();
		final Listbox listbox = new Listbox();
		
		Label label = new Label("Buscar: ");
		final Textbox tbxValuePrestador = new Textbox(""); 
		tbxValuePrestador.setStyle("text-transform:uppercase;"); 
		
		
		bandbox.addEventListener("onOpen", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				buscarPrestador(tbxValuePrestador.getValue(), listbox, map);
			}
		});
		
		tbxValuePrestador.addEventListener("onChanging", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				//log.info("::: Buscar:> " + tbxValuePrestador.getValue()); 
				buscarPrestador(tbxValuePrestador.getValue(), listbox, map);
			}
		});
		
		tbxValuePrestador.addEventListener("onOk", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				//log.info("::: Buscar:> " + tbxValuePrestador.getValue()); 
				buscarPrestador(tbxValuePrestador.getValue(), listbox, map);
			}
		});
		
		Toolbarbutton toolbarbuttonBuscar = new Toolbarbutton();
		toolbarbuttonBuscar.setImage("/images/Magnifier.gif"); 
		toolbarbuttonBuscar.setTooltiptext("Buscar"); 
		toolbarbuttonBuscar.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				buscarPrestador(tbxValuePrestador.getValue(), listbox, map);
			}
		});
		
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif"); 
		toolbarbutton.setTooltiptext("Quitar"); 
		toolbarbutton.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				selectedPrestador(new Listitem(), bandbox, textboxNombre, map);
			}
		});
		
		toolbar.appendChild(label);
		toolbar.appendChild(tbxValuePrestador);
		toolbar.appendChild(toolbarbuttonBuscar);
		toolbar.appendChild(toolbarbutton);
		vbox.appendChild(toolbar);
		
		/* agregamos ListBox */
		listbox.setHeight("300px");
		listbox.setWidth("600px");
		listbox.setMold("paging"); 
		listbox.setPageSize(8);
		listbox.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				selectedPrestador(listbox.getSelectedItem(), bandbox, textboxNombre, map);
			}
		});
		
		Listhead listhead = new Listhead();
		Listheader listheader = new Listheader("Codigo");
		listheader.setWidth("150px");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Nit");
		listheader.setWidth("170px");
		listhead.appendChild(listheader);
		
		listheader = new Listheader("Nombre");
		listhead.appendChild(listheader);
		listbox.appendChild(listhead);
		vbox.appendChild(listbox);
		bandbox.appendChild(bandpopup);
		bandbox.applyProperties();
	}

	public void guardar() throws Exception{
		/* clonamos anexos diferentes */
		try {
			Map mapContent = new HashMap();
			/* Repartimos procedimientos correspondientes */
			boolean existAnother = false;
			List<Map<String,Object>> procedimientosSeQuedan = new ArrayList<Map<String,Object>>();
			for (Map map : list_procedimientos) {
			   String codigo_prestador = (String) map.get(_ADMIN);
			   boolean autorizado = (Boolean) map.get(_AUTORIZADO);
			   
			   if(!codigo_prestador.equals(administradoraGlobal != null ? administradoraGlobal.getCodigo() : "")){
				   existAnother = true;
				   Change change = null;
				   if(mapContent.containsKey(codigo_prestador)){
					   change = (Change) mapContent.get(codigo_prestador);
					   change.procedimientos.add(map);
				   }else{
					   change = new Change();
					   change.anexo3Entidad = cloneThis(); 
					   change.procedimientos.add(map);
					   mapContent.put(codigo_prestador, change);
				   }
				   if(!autorizado){change.needAutorizacion = true;}
			   }else{
//				   //System.Out.Println("Procedimientos para la misma: " + map.get(_CODIGO_CUPS)); 
				   procedimientosSeQuedan.add(map);
			   }
			}
			
			if(existAnother){
				saveAnexo3(mapContent, procedimientosSeQuedan); 
			}else{
				throw new Exception("Para esta opcion debe seleccionar un prestador diferente al actual");
			}
			Messagebox.show("Los datos se guardaron satisfactoriamente",
					"Informacion ..", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
			.show("" + e.getMessage(),
					"Informacion ..", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}
	
	private void saveAnexo3(Map mapContent, List<Map<String, Object>> procedimientosSeQuedan) throws Exception{  
		getServiceLocator().getAnexo3EntidadService().guardarNuevosAnexos(mapContent, procedimientosSeQuedan, getServiceLocator(), sucursal, usuarios, anexo_3);
	}

	private Anexo3_entidad cloneThis(){
		try {
				Field[] fields =  Anexo3_entidad.class.getDeclaredFields();
				Anexo3_entidad newObject = Anexo3_entidad.class.newInstance();
				newObject.setCreacion_date(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				newObject.setUltimo_update(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				newObject.setCreacion_user(usuarios.getCodigo().toString());
				newObject.setUltimo_user(usuarios.getCodigo().toString());
				for (Field field : fields) {
					field.setAccessible(true);
					healthmanager.controller.Anexo3_entidadAction.cloneThis clone = field
							.getAnnotation(healthmanager.controller.Anexo3_entidadAction.cloneThis.class);
					if(clone != null){field.set(newObject, field.get(anexo_3)); }
				 }
				return newObject;
	    } catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} 
	}

	public class Change{
		public  Anexo3_entidad anexo3Entidad;
		public  List<Map<String,Object>> procedimientos;
		public boolean needAutorizacion;
		
		public Change() {
			procedimientos = new ArrayList<Map<String,Object>>();
		}
	}

}
