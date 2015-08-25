package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class ReporteResLaboratorioFueraRangoAction extends ZKWindow implements WindowRegistrosIMG {

	private List<String> lista_seleccionados_eps = new ArrayList<String>();
	private List<String> lista_seleccionados_caps = new ArrayList<String>();
	private List<Map<String, Object>> lista_datos_eps = new ArrayList<Map<String,Object>>();
	private List<Map<String, Object>> lista_datos_caps = new ArrayList<Map<String,Object>>();
	
	@View private Checkbox chk_entidad_eps;
	@View private Checkbox chk_caps;
	@View private Datebox dtbxFechaIncio;
	@View private Datebox dtbxFechaFinal;
	@View private Borderlayout groupboxEditar;
	@View private Rows rowEps;
	@View private Rows rowCaps;
	@View private Listbox lbxFormato;
	private final String[] idsExcluyentes = {};
	
	private static final String ADMINISTRADORA = "admin";
	private static final String CENTRO_ATENCION = "centro";
	
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		lista_seleccionados_eps.clear();
		lista_seleccionados_caps.clear();
	}

	
	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			//log.info("Registro seleccionado: " + registro);
			if (registro instanceof Centro_atencion)  {
				onSeleccionarCaps((Centro_atencion) registro);
			} else if (registro instanceof Administradora) {
				onSeleccionarEps((Administradora) registro);
			} 
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}


	private void onSeleccionarEps(Administradora registro) {
      adicionarDetalleListaAdministradora(cargarPrestador(registro)); 
	}

	private void onSeleccionarCaps(Centro_atencion registro) {
      adicionarDetalleListaCentro_atencion(cargarPrestador(registro)); 
	}
		
		private void adicionarDetalleListaAdministradora(Map<String, Object> bean) {
			try {
				crearFilasAdministradora(bean); 
			} catch (Exception e) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}
		private void adicionarDetalleListaCentro_atencion(Map<String, Object> bean) {
			try {
				crearFilasCentro_atencion(bean); 
			} catch (Exception e) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}

		private Map<String, Object> cargarPrestador(Centro_atencion centro_atencion) {
			Map<String, Object> bean = new HashMap<String, Object>();
			bean.put(CENTRO_ATENCION, centro_atencion);
			return bean;
		}
		
		private Map<String, Object> cargarPrestador(Administradora administradora) {
			Map<String, Object> bean = new HashMap<String, Object>();
			bean.put(ADMINISTRADORA, administradora); 
			return bean;
		}
		
	@Override
	public Object[] celdasListitem(Object registro) {
		if(registro instanceof Administradora){
			return celdasListitemAdministradora((Administradora)registro);
		}else if(registro instanceof Centro_atencion){
			return celdasListitemCentro_atencion((Centro_atencion)registro);
		}
		return null;
	}

	private void crearFilasAdministradora(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_eps.add(bean);
		final Administradora administradora = (Administradora) bean.get(ADMINISTRADORA);
		
		Cell cell = new Cell();
		Label label = new Label("" + administradora.getCodigo());
		cell.appendChild(label);
		row.appendChild(cell);
		
		cell = new Cell();
		label = new Label("" + administradora.getNombre());
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
									     lista_datos_eps.remove(bean);
										 rowEps.removeChild(row);
									}
								}
							});
			}
		});
		rowEps.appendChild(row); 
	}
	private void crearFilasCentro_atencion(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_caps.add(bean);
		final Centro_atencion centro_atencion = (Centro_atencion) bean.get(CENTRO_ATENCION);
		
		Cell cell = new Cell();
		Label label = new Label("" + centro_atencion.getCodigo_centro());
		cell.appendChild(label);
		row.appendChild(cell);
		
		cell = new Cell();
		label = new Label("" + centro_atencion.getNombre_centro());
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
							lista_datos_caps.remove(bean);
							rowCaps.removeChild(row);
						}
					}
				});
			}
		});
		rowCaps.appendChild(row); 
	}
	
	@Override
	public void init() throws Exception {
		
	}
	
	public boolean validarForm()throws Exception{
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";
		dtbxFechaIncio.setStyle("text-transform:uppercase;background-color:white");
		dtbxFechaIncio.setStyle("text-transform:uppercase;background-color:white");
		
		if(dtbxFechaIncio.getValue() ==  null){
			valida = false;
			dtbxFechaIncio.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		}
		
		if(dtbxFechaFinal.getValue() ==  null && valida){
			valida = false;
			dtbxFechaFinal.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		}
		
		if(chk_caps.isChecked() && lista_datos_caps.isEmpty()){
			msj = "Para realizar esta accion debe seleccionar por lo menos un centro de salud";
			valida = false;
		}
		
		if(chk_entidad_eps.isChecked() && lista_datos_eps.isEmpty()){
			msj = "Para realizar esta accion debe seleccionar por lo menos una administradora";
			valida = false;
		}

		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...",msj);
		}
		
		return valida;
	}

	public void generarReporte(){
		try {
			//List<String> listitems = getListitemsOrdenados(); 
			if(validarForm()){
				Map<String, Object> map = new HashMap<String, Object>();
				
				if(dtbxFechaIncio.getValue()!=null && dtbxFechaFinal.getValue()!=null){
					map.put("fecha_inicio", new Timestamp(dtbxFechaIncio.getValue().getTime())); 
					map.put("fecha_final", new Timestamp(dtbxFechaFinal.getValue().getTime()));
				}
				
				if(chk_caps.isChecked()){
					map.put("lista_cap",getCaps());
				}
				
				if(chk_entidad_eps.isChecked()){
					map.put("lista_eps",getEps());
				}
				
				map.put("impreso_por",  usuarios.getCodigo() + " - " +  usuarios.getNombres() + " " + usuarios.getApellidos()); 
				map.put("titulo", "Reporte de resultados de laboratorio fuera del rango normal o positivos");
				map.put("name_report", "ReporteResLaboratorioFueraRango");
				map.put("formato", lbxFormato.getSelectedItem().getValue()
						.toString());
				
				Component componente = Executions.createComponents("/pages/printInformes.zul", this, map);
				final Window window = (Window) componente;
				window.setWidth("100%");
				window.setHeight("100%");  
				window.setMode("modal");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this); 
		}
	}
	
	// cargar eps
		public void abrirWindowEps() {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("cerrado", false); 
			String columnas = "código#65px|Nombre";
			WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro("Eps",	Paquetes.HEALTHMANAGER, "AdministradoraDao.listar", this,	columnas, parametros);
			windowRegistros.mostrarWindow(lista_seleccionados_eps);
		}
	
		public void abrirWindowCaps() {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("cerrado", false); 
			String columnas = "código#65px|Nombre";
			WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro("Caps",	Paquetes.HEALTHMANAGER, "Centro_atencionDao.listar", this,	columnas, parametros);
			windowRegistros.mostrarWindow(lista_seleccionados_caps);
		}
		
		private List<Centro_atencion> getCaps() {
			List<Centro_atencion> centros_atencion = new ArrayList<Centro_atencion>();
		    for (Map<String, Object> map_ : lista_datos_caps) {
		    	Centro_atencion centro_atencion = (Centro_atencion) map_.get(CENTRO_ATENCION);
		    	centros_atencion.add(centro_atencion);
			}
			return centros_atencion;
		}
		
		private List<Administradora> getEps() {
			List<Administradora> administradoras = new ArrayList<Administradora>();
		    for (Map<String, Object> map_ : lista_datos_eps) {
				  Administradora administradora = (Administradora) map_.get(ADMINISTRADORA);
				  administradoras.add(administradora);
			}
			return administradoras;
		}
		
		private Object[] celdasListitemAdministradora(Administradora registro) {
			return new Object[]{registro.getCodigo(), registro.getNombre()};
		}
		
		private Object[] celdasListitemCentro_atencion(Centro_atencion registro) {
			return new Object[]{registro.getCodigo_centro(), registro.getNombre_centro()};
		}
		
}
