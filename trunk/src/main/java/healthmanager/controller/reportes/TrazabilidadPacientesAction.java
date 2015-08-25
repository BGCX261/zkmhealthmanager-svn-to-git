package healthmanager.controller.reportes;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.MensajesUtil;

public class TrazabilidadPacientesAction extends ZKWindow implements
		WindowRegistrosIMG {
	public static final String PACIENTES = "PAC";
	
	@View private Datebox dtbxFechaIncio;
	@View private Datebox dtbxFechaFinal;
	@View private Rows rowPaciente;
	
	private List<String> lista_seleccionados_pacientes = new ArrayList<String>();
	
	// listado de objetos
	private List<Map<String, Object>> lista_datos_pacientes = new ArrayList<Map<String,Object>>();

	@Override
	public void init() throws Exception {

	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			if (registro instanceof Paciente) {
				onSeleccionarPaciente((Paciente) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
 
	@Override
	public Object[] celdasListitem(Object registro) {
		if(registro instanceof Paciente){
			return celdasListitemPacientes((Paciente)registro);
		}
		return null;
	}
	
	
	private void onSeleccionarPaciente(Paciente registro) {
		adicionarDetalleListaPaciente(cargarPaciente(registro)); 
	}
	  
	private void adicionarDetalleListaPaciente(final Map<String, Object> registro) {
		try {
			crearFilasPaciente(registro);  
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasPaciente(final Map<String, Object> registro) {
		final Row row = new Row();
		lista_datos_pacientes.add(registro);
		final Paciente paciente = (Paciente) registro.get(PACIENTES);
		
		row.setValue(paciente);
		
		Cell cell = new Cell();
		Label label = new Label("" + paciente.getTipo_identificacion());
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + paciente.getNro_identificacion());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + paciente.getNombre1() + " " + paciente.getNombre2());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + paciente.getApellido1() + " " + paciente.getApellido2());
		cell = new Cell();
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
									     lista_datos_pacientes.remove(registro);
										 rowPaciente.removeChild(row);
									}
								}
							});
			}
		});
		rowPaciente.appendChild(row); 
	}

	private Map<String, Object> cargarPaciente(Paciente registro) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(PACIENTES, registro);
		return bean;
	}

	private Object[] celdasListitemPacientes(Paciente registro) {
		return new Object[]{registro.getTipo_identificacion(), registro.getDocumento(), registro.getNombre1() + " " + registro.getNombre2(), registro.getApellido1() + " " + registro.getApellido2()};
	}

	public void abrirWindowPaciente() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String columnas = "Tipo ID#65px|identificacion#170px|Nombre|Apellido";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccione el o los Pacientes", Paquetes.HEALTHMANAGER,
				"PacienteDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_pacientes);
	}

	
	public void imprimir(){
		try {
			if(validar()){ 
				Map paramRequest = new HashMap();
				paramRequest.put("name_report", "ReporteTrazabilidadPaciente");
				paramRequest.put("codigo_empresa", codigo_empresa);
				paramRequest.put("codigo_sucursal", codigo_sucursal);
				paramRequest.put("pacientes", getPacientes()); 
				paramRequest.put("fecha_inicio", dtbxFechaIncio.getValue());
				paramRequest.put("fecha_final", dtbxFechaFinal.getValue());
				paramRequest.put("impreso_por", usuarios.getNombres() + " " + usuarios.getApellidos());
				Component componente = Executions.createComponents(
						"/pages/printInformes.zul", this, paramRequest);

				Window window = (Window) componente;
				window.setMaximizable(true);
				window.setMaximized(true);
				window.doModal();
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private boolean validar() {
		boolean validar = true;
		String msj = "";
		if(lista_datos_pacientes.isEmpty()){
			validar = false;
			msj = "Para realizar esta accion debe seleccionar el paciente";
		}
		if(!validar){
			MensajesUtil.mensajeAlerta("Advertencia", msj); 
		}
		return validar;
	}

	private List<Paciente> getPacientes() {  
		List<Paciente> pacientes = new ArrayList<Paciente>();
		for (Map<String, Object> map_paciente : lista_datos_pacientes) {
			pacientes.add((Paciente)map_paciente.get(PACIENTES));
		} 
		return pacientes;
	}
	
	public void limpiarDatos() throws Exception {
		rowPaciente.getChildren().clear();
		dtbxFechaFinal.setValue(null);
		dtbxFechaIncio.setValue(null); 
	}
}
