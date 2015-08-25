package healthmanager.controller.reportes;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Usuarios;
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
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class AutorizacionesRealizadasAction extends ZKWindow implements WindowRegistrosIMG{

   public static final String USUARIO = "USR";
	
	@View private Datebox dtbxFechaIncio;
	@View private Datebox dtbxFechaFinal;
	@View private Rows rowUsuarios;
	
	private List<String> lista_seleccionados_usuarios = new ArrayList<String>();
	
	// listado de objetos
	private List<Map<String, Object>> lista_datos_usuarios = new ArrayList<Map<String,Object>>();

	@Override
	public void init() throws Exception {}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			if (registro instanceof Usuarios) {
				onSeleccionarUsuario((Usuarios) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
 
	@Override
	public Object[] celdasListitem(Object registro) {
		if(registro instanceof Usuarios){
			return celdasListitemUsuarioss((Usuarios)registro);
		}
		return null;
	}
	
	private void onSeleccionarUsuario(Usuarios registro) {
		adicionarDetalleListaUsuarios(cargarUsuarios(registro)); 
	}
	  
	private void adicionarDetalleListaUsuarios(final Map<String, Object> registro) {
		try {
			crearFilasUsuarios(registro);  
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasUsuarios(final Map<String, Object> registro) {
		final Row row = new Row();
		lista_datos_usuarios.add(registro);
		final Usuarios Usuarios = (Usuarios) registro.get(USUARIO);
		
		row.setValue(Usuarios);
		
		Cell cell = new Cell();
		Label label = new Label("" + Usuarios.getCodigo());
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + Usuarios.getNombres());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);
		
		label = new Label("" + Usuarios.getApellidos());
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
									     lista_datos_usuarios.remove(registro);
										 rowUsuarios.removeChild(row);
									}
								}
							});
			}
		});
		rowUsuarios.appendChild(row); 
	}

	private Map<String, Object> cargarUsuarios(Usuarios registro) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(USUARIO, registro);
		return bean;
	}

	private Object[] celdasListitemUsuarioss(Usuarios registro) {
		return new Object[]{registro.getCodigo(), registro.getNombres(), registro.getApellidos()};
	}

	public void abrirWindowUsuarios() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String columnas = "identificacion#170px|Nombre|Apellido";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccione el o los Usuarios", Paquetes.HEALTHMANAGER,
				"UsuariosDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_usuarios);
	}

	
	public void imprimir(){
		try {
			if(validar()){ 
				Map<String,Object> paramRequest = new HashMap<String,Object>();
				paramRequest.put("name_report", "AutorizacionesRealizadas");
				paramRequest.put("codigo_empresa", codigo_empresa);
				paramRequest.put("codigo_sucursal", codigo_sucursal);
				paramRequest.put("usuarios", getUsuarioss()); 
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
		try {
			FormularioUtil.validarCamposObligatorios(dtbxFechaFinal, dtbxFechaIncio); 
		} catch (Exception e) {
			validar = false;
			msj = "" + e.getMessage(); 
		}
		if(validar &&  lista_datos_usuarios.isEmpty()){
			validar = false;
			msj = "Para realizar esta accion debe seleccionar por lo menos un usuario";
		}
		if(!validar){
			MensajesUtil.mensajeAlerta("Advertencia", msj); 
		}
		return validar;
	}

	private List<Usuarios> getUsuarioss() {  
		List<Usuarios> Usuarioss = new ArrayList<Usuarios>();
		for (Map<String, Object> map_Usuarios : lista_datos_usuarios) {
			Usuarioss.add((Usuarios)map_Usuarios.get(USUARIO));
		} 
		return Usuarioss;
	}
	
	public void limpiarDatos() throws Exception {
		rowUsuarios.getChildren().clear();
		dtbxFechaFinal.setValue(null);
		dtbxFechaIncio.setValue(null); 
	}

}
