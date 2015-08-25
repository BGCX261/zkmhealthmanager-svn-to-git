package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Anexo4_entidad;
import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;
import healthmanager.modelo.bean.Detalle_anexo4;
import healthmanager.modelo.service.Anexo4_entidadService;
import healthmanager.modelo.service.Detalle_anexo4Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.macros.macro_row.BandBoxRowMacro.IConfiguracionBandbox;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.ServiciosDisponiblesUtils.NIVEL_CONSULTA;

public class SeleccionarPrestadorAction extends ZKWindow {

	private IAccion accion;
	private Anexo4_entidad anexo4_entidad;
	private Boolean paga_copago;
	private Image imgImprimir;
	private Image imgCopago;
	private Image imgCargarPrestador;
	
	@View(isMacro = true) private BandboxRegistrosMacro tbxCodigo_administradora;
	@View private Textbox tbxInfoAseguradora;
	@View private Toolbarbutton btnLimpiarAseguradora;
	@View private Rows row_servicios;
	private Configuracion_servicios_autorizacion config;
	private IConfiguracionBandbox<Administradora> configuracionBandbox;
	
	private static final String PARAM_BANDBOX_PRESTADOR = "_PBP";
	private static final String PARAM_VALIDADO = "_PVLO";
	
	
	public static final String PARAM_ADMINISTRADORA = "_PA"; 
	public static final String PARAM_LISTADO_ANEXO4 = "_LA4";
	private static final String PARAM_LISTADO_PRESTADORES_CON_SERVICIOS = "_PLPCS";
	
	
	public interface IAccion {
		void onCambiarPrestador(boolean paga_copago, Anexo4_entidad anexo4_entidad);
		void onGenerarCopago(Anexo4_entidad anexo4_entidad, Configuracion_servicios_autorizacion config);
		void onImprimir(Anexo4_entidad anexo4_entidad, boolean paga_copago);
	}

	@Override
	public void init() throws Exception {
       parametrizarBandbox();
	}
	 
	private void parametrizarBandbox() {
		parametrizarAdministradora();
	}
	
	public void onAsignarPrestador(){
		Map<String, Object> respuesta_validada = validarCambio();
		if((Boolean)respuesta_validada.get(PARAM_VALIDADO)){  
			List<Map<String, Object>> listado_prestadores = (List<Map<String, Object>>) respuesta_validada
					.get(PARAM_LISTADO_PRESTADORES_CON_SERVICIOS);
			if(listado_prestadores.size() == 1){
				Map<String, Object> map = listado_prestadores.get(0);
				Administradora administradora = (Administradora) map.get(PARAM_ADMINISTRADORA);
				List<Detalle_anexo4> listado_detalle_anexo = (List<Detalle_anexo4>) map.get(PARAM_LISTADO_ANEXO4);
				asignarPrestador(administradora, listado_detalle_anexo); 
			}else{
				asignarPrestadorMultiple(listado_prestadores); 
			}
		}
	}
	 
	private void asignarPrestadorMultiple(final List<Map<String, Object>> listado_prestadores) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("anexo4", anexo4_entidad);
		map.put("config", config);
		map.put("listado_prestadores", listado_prestadores);
		map.put("servicelocator", getServiceLocator());
		List<Map<String, Object>> map_resultado =  getServiceLocator().getServicio(Anexo4_entidadService.class)
				.guardarCambioPrestadorMultiple(map);
		Notificaciones.mostrarNotificacionInformacion("Informacion",
				"Asignacion de prestador realizado exitosamente", IConstantes.TIEMPO_NOTIFICACIONES_LARGO);
		onMostarVistaInformacion(map_resultado); 
	}

	protected void onMostarVistaInformacion(
			List<Map<String, Object>> map_resultado) {
		Window window = new Window("Ordenes para " + config.getNombre(), "normal", true);  
		window.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				SeleccionarPrestadorAction.this.detach();
			}
		});
		
		Grid grid = new Grid();
		window.appendChild(grid);
		
		// creamos columnas
		Columns columns = new Columns();
		grid.appendChild(columns);
		
		columns.appendChild(new Column("Nro Orden", null, "100px"));
		columns.appendChild(new Column("Prestador"));
		columns.appendChild(new Column("", null, "100px"));
		
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		// creamos contenido
		for (Map<String, Object> map : map_resultado) {
			final Anexo4_entidad anexo4_entidad = (Anexo4_entidad) map.get("anexo4"); 
			Administradora administradora = (Administradora) map.get("administradora");
			
			Row row = new Row();
			row.appendChild(new Label("" + anexo4_entidad.getCodigo())); 
			row.appendChild(new Label("" + administradora.getNombre())); 
			
			Hbox hbox = new Hbox();
			if(paga_copago){
				hbox.appendChild(getBoton("/images/caja.jpg", "Pagar Copago",  new EventListener<Event>() { 
					@Override
					public void onEvent(Event arg0) throws Exception {
						getAccion().onGenerarCopago(anexo4_entidad, config); 
					}
				}));
			}
			
			hbox.appendChild(getBoton("/images/print_ico.gif", "Imprimir",  new EventListener<Event>() { 
				@Override
				public void onEvent(Event arg0) throws Exception {
					getAccion().onImprimir(anexo4_entidad, paga_copago);  
				}
			}));
			row.appendChild(hbox); 
			rows.appendChild(row);
		}
		
		window.setWidth("500px"); 
		window.setHeight("90%");   
		window.setPage(getPage());
		window.doModal();
	}
	
	private Toolbarbutton getBoton(String src, String tooltiptext, EventListener eventListener){
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage(src); 
		toolbarbutton.setTooltiptext(tooltiptext); 
		toolbarbutton.addEventListener(Events.ON_CLICK, eventListener);
		return toolbarbutton;
	}
	

	private Map<String, Object> validarCambio() {
		Map<String, Object> respuesta = new HashMap<String, Object>();
		boolean validado = true;
		String msj = "";
		
		try {
			List<Map<String, Object>> listado_prestadores = getProcedimientosOrganizadosPorPrestador();
			respuesta.put(PARAM_LISTADO_PRESTADORES_CON_SERVICIOS, listado_prestadores);
		} catch (Exception e) { 
			msj = e.getMessage();
			validado = false;
		}
		
		respuesta.put(PARAM_VALIDADO, validado);
		if(!validado){
			MensajesUtil.mensajeAlerta("Advertencia", msj); 
		}
		
		return respuesta;
	}
 
	private List<Map<String, Object>> getProcedimientosOrganizadosPorPrestador() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		List<Component> components = row_servicios.getChildren();
		for (Component component : components) { 
			if(component instanceof Row){
				BandBoxRowMacro bandBoxRowMacro = ((BandBoxRowMacro) ((Row) component)
						.getAttribute(PARAM_BANDBOX_PRESTADOR));
				Administradora administradora = (Administradora) bandBoxRowMacro.getRegistroSeleccionado();
				if(administradora != null){
					Map<String, Object> mapa_administradora = consultarMapaAdministradora(
							administradora.getCodigo(), list); 
					List<Detalle_anexo4> listado_anexo4_entidads;
					if(mapa_administradora == null){
						mapa_administradora = new HashMap<String, Object>();
						listado_anexo4_entidads = new ArrayList<Detalle_anexo4>();
						
						// cargamos administradora con detalles
						mapa_administradora.put(PARAM_ADMINISTRADORA, administradora);
						mapa_administradora.put(PARAM_LISTADO_ANEXO4, listado_anexo4_entidads);
						
						// adicionamos registro 
						list.add(mapa_administradora); 
					}else{
						listado_anexo4_entidads = (List<Detalle_anexo4>) mapa_administradora
								.get(PARAM_LISTADO_ANEXO4);  
					}
					listado_anexo4_entidads.add((Detalle_anexo4)((Row) component).getValue());   
				}else{
					Clients.scrollIntoView(bandBoxRowMacro);  
					throw new ValidacionRunTimeException("Para realizar esta accion es de caracter obligatorio seleccionar un prestador para todos los servicios"); 
				}
			}
		}
		
		return list;
	}

	private Map<String, Object> consultarMapaAdministradora(String codigo, List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			 Administradora administradora = (Administradora) map.get(PARAM_ADMINISTRADORA);
			 if(administradora.getCodigo().equalsIgnoreCase(codigo)){
				 return map;
			 }
		}
		return null;
	}
 
	private void asignarPrestador(final Administradora administradora, final List<Detalle_anexo4> listado_detalle_anexo){
		if(administradora != null){
			Messagebox.show(
					"Estas seguro que deseas asignar la orden a " + administradora.getNombre(), 
					"Informacion", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								anexo4_entidad.setCodigo_prestador(administradora.getCodigo());
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("anexo4", anexo4_entidad);
								map.put("config", config);
								map.put("administradora", administradora);
								map.put("servicelocator", getServiceLocator());
								map.put("accion", "modificar"); 
								map.put("accion_detalle", "modificar"); 
								map.put("detalles", listado_detalle_anexo); 
								getServiceLocator().getServicio(Anexo4_entidadService.class).guardarCambioPrestador(map);
								String msj = "Cambio de prestador realizado exitosamente, desea imprimir esta autorizacion?";
								if(paga_copago){
									imgCopago.setVisible(true); 
									msj = "Cambio de prestador realizado exitosamente, desea realizar el cobro del copago?";
								}
								Messagebox.show(
										"" + msj,
										"Informacion", Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event) throws Exception {
												getAccion().onCambiarPrestador(paga_copago, anexo4_entidad); 
												imgCargarPrestador.setVisible(false);
												imgImprimir.setVisible(true);
												if(paga_copago){
													imgCopago.setVisible(true); 
												}
												detach();
											}
										});
							}
						}
					});
			
		}else{
			MensajesUtil.mensajeAlerta("Advertencia", "Para realizar esta accion debe seleccionar un prestador");  
		}
	}
 
	private void parametrizarAdministradora() {
		tbxCodigo_administradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		tbxCodigo_administradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								getSucursal().getCodigo_empresa());
						parametros.put("codigo_sucursal",
								getSucursal().getCodigo_sucursal());
						parametros.put("tercerizada", "S"); 
						parametros.put("id_configuracion_autorizacion", anexo4_entidad.getTipo_servicio()); 
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listarAdministradorasServicio(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getCodigo());
						bandbox.setAttribute("admin", registro);
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						verificarServiciosDisponible(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	@Override
	public void params(Map<String, Object> map) {
		anexo4_entidad = (Anexo4_entidad) map.get("anexo4_entidad");
		paga_copago = (Boolean) map.get("paga_copago"); 
		imgCopago = (Image) map.get("imgCopago"); 
		imgImprimir = (Image) map.get("imgImprimir"); 
		imgCargarPrestador = (Image) map.get("imgCargarPrestador"); 
		config = (Configuracion_servicios_autorizacion) map.get("config");  
		if(anexo4_entidad != null){
			cargarServicios();
		}
	}

	private void cargarServicios() {
		Map<String, Object> paramns = new HashMap<String, Object>();
		paramns.put("codigo_empresa", anexo4_entidad.getCodigo_empresa());
		paramns.put("codigo_sucursal", anexo4_entidad.getCodigo_sucursal());
		paramns.put("codigo_orden", anexo4_entidad.getCodigo());
		List<Detalle_anexo4> detalle_ordens = getServiceLocator().getServicio(Detalle_anexo4Service.class).listar(paramns);
		for (Detalle_anexo4 detalle_anexo4 : detalle_ordens) {
			row_servicios.appendChild(crearFila(detalle_anexo4)); 
		}
	}
 
	private Row crearFila(Detalle_anexo4 detalle_anexo4) {
		Row row = new Row();
		row.setValue(detalle_anexo4);  
		row.appendChild(new Label(detalle_anexo4.getCodigo_cups()));
		row.appendChild(new Label(detalle_anexo4.getNombre_pcd()));
		
		BandBoxRowMacro bandBoxRowMacro = new BandBoxRowMacro()
				.configurar(getConfiguracionBandBoxRow(detalle_anexo4));
		bandBoxRowMacro.setHflex("1");  
		row.setAttribute(PARAM_BANDBOX_PRESTADOR, bandBoxRowMacro);
		row.appendChild(bandBoxRowMacro); 
		return row;
	}
 
	private IConfiguracionBandbox getConfiguracionBandBoxRow(final Detalle_anexo4 detalle_anexo4) {
		if(configuracionBandbox == null){
			configuracionBandbox = new IConfiguracionBandbox<Administradora>() { 

				@Override
				public void onSeleccionar(Administradora registro, Bandbox bandbox) {
					// Validados estado de seleccion
					List<String> listado_servicios = ServiciosDisponiblesUtils
							.getFiltroServiciosPermitidos( 
									registro.getCodigo_empresa(),
									registro.getCodigo_sucursal(),
									registro.getCodigo(),
									null, config.getId(), null, NIVEL_CONSULTA.CONTRATO);
					if (!validarEstaPermitidoParaAseguradora(
							detalle_anexo4.getCodigo_cups(), listado_servicios)) {
						((BandBoxRowMacro)bandbox).limpiarSeleccion(false); 
						Notificaciones
								.mostrarNotificacionAlerta(
										"Advertencia",
										"Este servicio no lo tiene contratado, verifique la contratacion",
										3000); 
					}
				}

				@Override
				public String getCabecera(Bandbox bandbox) {
					return "Código#65px|Nombres|Nit#100px";
				}

				@Override
				public String getWidthListBox() {
					return "600px";
				}

				@Override
				public String getHeightListBox() {
					return "300px";
				}

				@Override
				public void renderListitem(Listitem listitem,
						Administradora registro, Bandbox bandbox) {
					listitem.appendChild(new Listcell(registro.getCodigo()));
					listitem.appendChild(new Listcell(registro.getNombre()));
					listitem.appendChild(new Listcell(registro.getNit()));
				}

				@Override
				public List<?> listarRegistros(String valorBusqueda,
						Map<String, Object> parametros, Bandbox bandbox) {
					parametros.put("codigo_empresa",
							getSucursal().getCodigo_empresa());
					parametros.put("codigo_sucursal",
							getSucursal().getCodigo_sucursal());
					parametros.put("tercerizada", "S"); 
					parametros.put("id_configuracion_autorizacion", anexo4_entidad.getTipo_servicio()); 
					parametros.put("paramTodo", "paramTodo");
					parametros.put("value", valorBusqueda);
					return getServiceLocator().getAdministradoraService()
							.listarAdministradorasServicio(parametros);
				}

				@Override
				public boolean seleccionarRegistro(Bandbox bandbox,
						Administradora registro) {
					bandbox.setValue(registro.getCodigo() + " " + registro.getNombre());
					return true;
				}

				@Override
				public void limpiarSeleccion(Bandbox bandbox) {
					
				} 
			};
		}
		return configuracionBandbox;
	}
	
	protected void verificarServiciosDisponible(Administradora administradora) {
		List<String> listado_servicios = ServiciosDisponiblesUtils
			.getFiltroServiciosPermitidos( 
					administradora.getCodigo_empresa(),
					administradora.getCodigo_sucursal(),
					administradora.getCodigo(),
					null, config.getId(), null, NIVEL_CONSULTA.CONTRATO);   
		List<Component> servicios_ordenados = row_servicios.getChildren(); 
		int contador_items = 0;
		int contador_validos = 0;
		for (Component component : servicios_ordenados) {
			if(component instanceof Row){
				Detalle_anexo4 detalle_anexo4 = ((Row)component).getValue();
				BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) ((Row) component)
						.getAttribute(PARAM_BANDBOX_PRESTADOR);
				if (validarEstaPermitidoParaAseguradora(
						detalle_anexo4.getCodigo_cups(), listado_servicios)) {
					bandBoxRowMacro.seleccionarRegistro(
							administradora,
							administradora.getCodigo() + " "
									+ administradora.getNombre());  
					contador_validos++;
				}else{
					bandBoxRowMacro.limpiarSeleccion(false); 
				}
				contador_items++;
			}
		}
		if(contador_items != contador_validos){
			if(contador_validos > 0){
				Notificaciones.mostrarNotificacionInformacion("Informacion",
						"Este prestador solo tiene contratado " + contador_validos + " servicios.",
						IConstantes.TIEMPO_NOTIFICACIONES_GENERALES); 
			}else{
				MensajesUtil
						.mensajeAlerta(
								"Advertencia",
								"Este prestador no tiene contratado ninguno de estos servicios, verifique el contrato");  
			}
		}
	}
 
	private boolean validarEstaPermitidoParaAseguradora(String codigo_cups,
			List<String> listado_servicios) {
		for (String codigo_cups_servicio_contratado : listado_servicios) {
			 if(codigo_cups.equalsIgnoreCase(codigo_cups_servicio_contratado)){
				 return true;
			 }
		}
		return false;
	}

	public IAccion getAccion() {
		return accion;
	}

	public void setAccion(IAccion accion) {
		this.accion = accion;
	}

}
