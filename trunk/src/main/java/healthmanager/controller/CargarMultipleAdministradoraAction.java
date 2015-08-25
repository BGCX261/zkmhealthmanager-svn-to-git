package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.notificaciones.Notificaciones;

public class CargarMultipleAdministradoraAction extends ZKWindow implements
		WindowRegistrosIMG {

	public static final String ADMINISTRADORA = "admin";
	public static final String CONTRATO = "cont_";
	public static final String MANUALES_TARIFARIOS = "manuales_";
	private ICargarMultipleAdministradoraEvent cargarMultipleAdministradoraEvent;
	
	public static final String CODIGO_CONTRATO = "codigo_contrato";
	
	private List<Map<String, Object>> lista_datos_administradora = new ArrayList<Map<String,Object>>();
	private List<String> lista_seleccionados_administradora = new ArrayList<String>();
	
	@View private Rows rowsAdministradoras;
	private Contratos contrato;
	private List<Manuales_tarifarios> manuales_tarifarios;
	
	
	@Override
	public void params(Map<String, Object> map) {
		// administradora seleccionada
		Administradora administradoraParameter = (Administradora) map
				.get(ADMINISTRADORA);
		contrato = (Contratos) map.get(CONTRATO);
		manuales_tarifarios = (List<Manuales_tarifarios>) map.get(MANUALES_TARIFARIOS); 
		//log.info("Administradora: " + administradoraParameter); 
		if (administradoraParameter != null) {
			crearFilasAdministradora(cargarAdministradora(administradoraParameter));
			lista_seleccionados_administradora.add(administradoraParameter.toString()); 
		}
	}
	
	
	public void abrirWindowAdministradora() {
		Map<String, Object> parametros = new HashMap<String, Object>();
//		parametros.put("no_rol_medico", "");
		String columnas = "código#65px|Nombres";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Administradora", Paquetes.HEALTHMANAGER,
				"AdministradoraDao.listar", this, columnas,
				parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_administradora);
	}
	
	public void guardar(){
		if(cargarMultipleAdministradoraEvent != null){
			cargarMultipleAdministradoraEvent.guardarAdministradoras(lista_datos_administradora, contrato, manuales_tarifarios); 
		}else{ 
			Notificaciones.mostrarNotificacionAlerta("Advertencia", "Para realizar esta accion falta cargar el evento", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		}
	}

	@Override
	public void init() throws Exception {

	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
       crearFilasAdministradora(cargarAdministradora((Administradora)registro)); 
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		Administradora administradoraTemp = (Administradora) registro;
		return new Object[] {administradoraTemp.getCodigo(), administradoraTemp.getNombre()};
	}
	
	
	private Map<String, Object> cargarAdministradora(Administradora  administradora) {  
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(ADMINISTRADORA, administradora);
		return bean; 
	}
	
	private void crearFilasAdministradora(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_administradora.add(bean);
		final Administradora administradora = (Administradora) bean.get(ADMINISTRADORA);
		
		row.setValue(administradora);
		
		Cell cell = new Cell();
		Label label = new Label("" + administradora.getCodigo());
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + administradora.getNombre());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);
		
		// aqui cargamos el nro del contrato
		Textbox textbox = new Textbox();
		textbox.setValue(contrato.getNro_contrato()); 
		textbox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
               Textbox textbox = (Textbox) arg0.getTarget();
               bean.put(CODIGO_CONTRATO, textbox.getValue()); 
               //log.info("Actualizamos codigo del contrato");  
			}
		});
		
		cell = new Cell();
		cell.appendChild(textbox);
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
									     lista_datos_administradora.remove(bean);
									     rowsAdministradoras.removeChild(row);
									}
								}
							});
			}
		});
		rowsAdministradoras.appendChild(row); 
	}

	// interface
	public interface ICargarMultipleAdministradoraEvent { 
		void guardarAdministradoras(List<Map<String, Object>> administradoras, Contratos contratos, List<Manuales_tarifarios> manuales_tarifarios);
	}

	public ICargarMultipleAdministradoraEvent getCargarMultipleAdministradoraEvent() {
		return cargarMultipleAdministradoraEvent;
	}

	public void setCargarMultipleAdministradoraEvent(
			ICargarMultipleAdministradoraEvent cargarMultipleAdministradoraEvent) {
		this.cargarMultipleAdministradoraEvent = cargarMultipleAdministradoraEvent;
	}

}
