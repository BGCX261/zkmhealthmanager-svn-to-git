/**
 * 
 */
package com.framework.macros;

import healthmanager.modelo.service.MacrocomponenteService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.interfaces.IImprimirRegistros;
import com.framework.interfaces.WindowStandar;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.Utilidades;



/**
 * @author Usuario
 *
 */
public class WindowRegistrosMacro  extends Window{
	private static Logger log = Logger.getLogger(WindowRegistrosMacro.class);

	private WindowStandar zkWindow;
	private boolean usaCodigo_empresa = true;
	private boolean usaCodigo_sucursal = true;
	private Map<String, Object> parametros_local = new HashMap<String, Object>();
	private Map<String, Object> parametros;
	private String statementConsulta;
	private String parametroTodo;

	private List<String> lista_seleccionados;
	private Map<String, String> mapa_seleccionados = new HashMap<String, String>();

	private String columnas;

	// componentes internos
	private Listbox mcListboxResultados;
	private Textbox mcTextboxValor;
	private Toolbar toolbar;
	
	private Paquetes paquetes;
	
	private boolean seleccionar = true;
	
	private IImprimirRegistros iImprimirRegistros;
	
	public WindowRegistrosMacro(String titulo, Paquetes paquetes, String statementConsulta,
			WindowStandar zkWindow, String columnas) {
		this.statementConsulta = statementConsulta;
		this.zkWindow = zkWindow;
		this.columnas = columnas;
		this.parametros = new HashMap<String, Object>();
		this.paquetes = paquetes;
		crearComponentes();
		this.setClosable(true);
		this.setWidth("80%");
		this.setParent((Component)zkWindow); 
		this.setTitle(titulo);
		this.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				//log.info("lista_seleccionados ===> " + lista_seleccionados);
				detach();
			}

		});
	}

	public WindowRegistrosMacro(String titulo, Paquetes paquetes, String statementConsulta,
			WindowStandar zkWindow, String columnas, Map<String, Object> parametros) {
		this(titulo, paquetes, statementConsulta, zkWindow, columnas);
		this.parametros = parametros;
	}
	
	private void crearComponentes() {
		Vbox vbox = new Vbox();
		setToolbar(new Toolbar());
		getToolbar().appendChild(new Label("Criterio de búsqueda: "));
		getToolbar().appendChild(new Space());

		mcTextboxValor = new Textbox();
		mcTextboxValor.setWidth("250px");
		mcTextboxValor.setStyle("text-transform:uppercase");
		mcTextboxValor.addEventListener(Events.ON_OK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						onConsultarRegistros();
					}
				});

		getToolbar().appendChild(mcTextboxValor);
		getToolbar().appendChild(new Space());

		Toolbarbutton toolbarbutton = new Toolbarbutton("",
				"/images/Magnifier.gif");
		toolbarbutton.setLabel("Consultar registros");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						onConsultarRegistros();
					}
				});

		getToolbar().appendChild(toolbarbutton);
		
		Toolbarbutton toolbarbutton2 = new Toolbarbutton("",
				"/images/add.png");
		toolbarbutton2.setTooltiptext("Seleccionar todos los registros");
		toolbarbutton2.setLabel("Seleccionar todos");
		toolbarbutton2.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {					
						List<Listitem> listitems = mcListboxResultados.getItems();
						for (Listitem listitem : listitems) {
							if(!listitem.isSelected()){
								seleccionarListItem(listitem);
							}
						}
					}
				});

		getToolbar().appendChild(toolbarbutton2);
		
		vbox.appendChild(getToolbar());

		mcListboxResultados = new Listbox();
		mcListboxResultados.setMold("paging");
		mcListboxResultados.setPageSize(15);
		mcListboxResultados.setHeight("350px");
		mcListboxResultados.setWidth("100%");
		mcListboxResultados.setAttribute("org.zkoss.zul.listbox.rod", true);

		Listhead mcListheadResultados = new Listhead();

		mcListboxResultados.appendChild(mcListheadResultados);

		vbox.appendChild(mcListboxResultados);

		this.appendChild(vbox);
	}

	private void agregarColumnas(String columnas) {
		for (String valor : columnas.split("\\|")) {
			Listheader listheader = new Listheader();
			if (valor.contains("#")) {
				String vals[] = valor.split("\\#");
				listheader.setLabel(vals[0]);
				if (vals.length > 1)
					listheader.setWidth(vals[1]);
			} else {
				listheader.setLabel(valor);
			}
			mcListboxResultados.getListhead().appendChild(listheader);
		}
		
		////
		if(iImprimirRegistros!=null){
			Listheader listheader = new Listheader("", "", "80px");
			mcListboxResultados.getListhead().appendChild(listheader);
		}
	}
	
	public void onConsultarRegistros() {
		try {
			parametros_local.clear();
			if (parametros != null) {
				for (String key_map : parametros.keySet()) {
					parametros_local.put(key_map, parametros.get(key_map));
				}
			}

			String valorBusqueda = mcTextboxValor.getValue().toLowerCase();
			mcListboxResultados.getItems().clear();
			if (usaCodigo_empresa)
				parametros_local.put("codigo_empresa", zkWindow.getEmpresa()
						.getCodigo_empresa());
			if (usaCodigo_sucursal)
				parametros_local.put("codigo_sucursal", zkWindow.getSucursal()
						.getCodigo_sucursal());
			parametros_local.put((parametroTodo != null && !parametroTodo
					.isEmpty()) ? parametroTodo : IConstantes.PARAMETRO_TODO,
					valorBusqueda);

			parametros_local.put("limit", "limit 25 offset 0");
			
			//log.info("statementConsulta: "+statementConsulta);
			//log.info("parametros_local: "+parametros_local);

			List<Object> listado = ServiceLocatorWeb
					.getInstance().getServicio(MacrocomponenteService.class)
					.listar(paquetes, statementConsulta, parametros_local); 
			
			//log.info("listado: "+listado);

//			mapa_seleccionados.clear();

			if (lista_seleccionados != null) {
				for (String seleccionado : lista_seleccionados) {
					mapa_seleccionados.put(seleccionado, seleccionado);
				}
			}

			for (final Object registro : listado) {
				////log.info("registro: "+registro);
				final Listitem listitem = new Listitem();
				if (zkWindow instanceof WindowRegistrosIMG) {
					////log.info("registro: "+registro);
					renderizarListitem(listitem,
							((WindowRegistrosIMG) zkWindow)
									.celdasListitem(registro));
				}
				if(iImprimirRegistros!=null){
					Listcell listcell = new Listcell();
					
					Image img = new Image();
					img.setSrc("/images/print_ico.gif");
					img.setTooltiptext("Editar");
					img.setStyle("cursor: pointer");
					img.addEventListener("onClick", new EventListener() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							iImprimirRegistros.imprimirRegistro(registro);
						}
					});
					listcell.appendChild(img);
					
					listitem.appendChild(listcell);
					
				}
				listitem.setValue(registro);

				if(seleccionar){
					listitem.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0) throws Exception {
									seleccionarListItem(listitem);
								}
							});
				}
				

				if (mapa_seleccionados.containsKey(registro.toString())) {
					listitem.setDisabled(true);
					listitem.setSelected(true);
					listitem.setTooltiptext("Este registro ya se encuentra seleccionado");
				}

				mcListboxResultados.appendChild(listitem);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private void seleccionarListItem(Listitem listitem){
		Object registro = listitem.getValue();
		if (!listitem.isDisabled()) {
			if (zkWindow instanceof WindowRegistrosIMG) {
				((WindowRegistrosIMG) zkWindow)
						.onSeleccionarRegistro(registro);
			}
//			listitem.setDisabled(true);
//			listitem.setTooltiptext("Este registro ya se encuentra seleccionado");
////			mapa_seleccionados.put(registro.toString(),
////					registro.toString());
//			//log.info("===> mapa seleccionados bryan " + mapa_seleccionados);
//			if (lista_seleccionados != null)
//				lista_seleccionados.add(registro
//						.toString());
		}
	}


	private void renderizarListitem(Listitem listitem, Object[] componentes) {
		if (componentes != null) {
			for (Object componente : componentes) {
				Listcell listcell = new Listcell();
				if (componente instanceof String) {
					listcell = Utilidades.obtenerListcell(componente, Textbox.class, true,true);
				}else if (componente instanceof Double){
					listcell = Utilidades.obtenerListcell(componente, Doublebox.class, true,true);
				} else if (componente instanceof Component) {
					listcell.appendChild((Component) componente);
				} else {
					listcell.appendChild(new Label());
				}
				listitem.appendChild(listcell);
			}
		}
		
	}

	public void mostrarWindow(List<String> seleccionados) {
		this.lista_seleccionados = seleccionados;
		agregarColumnas(columnas);
		this.setVisible(true);
		this.setMode("modal");
		//log.info("lista_seleccionados ===> " + lista_seleccionados);
	}

	public void setUsaCodigo_empresa(boolean usaCodigo_empresa) {
		this.usaCodigo_empresa = usaCodigo_empresa;
	}

	public boolean getUsaCodigo_empresa() {
		return usaCodigo_empresa;
	}

	public void setUsaCodigo_sucursal(boolean usaCodigo_sucursal) {
		this.usaCodigo_sucursal = usaCodigo_sucursal;
	}

	public boolean getUsaCodigo_sucursal() {
		return usaCodigo_sucursal;
	}

	public void setStatementConsulta(String statementConsulta) {
		this.statementConsulta = statementConsulta;
	}

	public String getStatementConsulta() {
		return statementConsulta;
	}

	public void setParametroTodo(String parametroTodo) {
		this.parametroTodo = parametroTodo;
	}

	public String getParametroTodo() {
		return parametroTodo;
	}

	public void setToolbar(Toolbar toolbar) {
		this.toolbar = toolbar;
	}

	public Toolbar getToolbar() {
		return toolbar;
	}

	public void setSeleccionar(boolean seleccionar) {
		this.seleccionar = seleccionar;
	}

	public boolean isSeleccionar() {
		return seleccionar;
	}

	public void setiImprimirRegistros(IImprimirRegistros iImprimirRegistros) {
		this.iImprimirRegistros = iImprimirRegistros;
	}

	public IImprimirRegistros getiImprimirRegistros() {
		return iImprimirRegistros;
	}
	
	
}
