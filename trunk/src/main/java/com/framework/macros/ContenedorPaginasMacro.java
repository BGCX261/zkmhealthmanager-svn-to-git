package com.framework.macros;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

import com.framework.util.Util;
import com.framework.util.Utilidades;

/**
 * Macrocomponente que controla las paginas que se van abriendo segun las
 * opciones de menu que van seleccionado. Agregando cada pagina que se abre en
 * un tab de un contendor de paginas
 * 
 * @author Dario
 * 
 */
public class ContenedorPaginasMacro extends Tabbox implements AfterCompose {

	private static Logger log = Logger.getLogger(ContenedorPaginasMacro.class);
	private static final long serialVersionUID = -4417628888209795937L;

	public static final String EVENTO_TAB = "EVENTO_TAB";

	// componentes propios del macro
	private Tabs mcTabsContenedor;
	private Tabpanels mcTabpanelsContenedor;

	private Map<String, Component> mapa_componentes = new HashMap<String, Component>();

	private String urlPaginaInicio = "";
	private String labelTabInicio = "";

	@Override
	public void afterCompose() {
		mcTabsContenedor = new Tabs();
		mcTabpanelsContenedor = new Tabpanels();
		this.appendChild(mcTabsContenedor);
		this.appendChild(mcTabpanelsContenedor);
	}

	/**
	 * Metodo para inicializar la pagina de incio en el contenedor de paginas.
	 * 
	 * @param cerrable
	 *            Determina si la pagina de incio es cerrable o no
	 * @return Retorna el tabpanel donde se crea el contendio del tab que se
	 *         agrega
	 */

	public Tabpanel inicializarInicio(boolean cerrable) {
		return abrirPaginaTab(cerrable, urlPaginaInicio, labelTabInicio);
	}

	/**
	 * Metodo para inicializar la pagina de incio en el contenedor de paginas.
	 * 
	 * @param cerrable
	 *            Determina si la pagina de incio es cerrable o no
	 * 
	 * @param parametros
	 *            Parametros con los que se quiere que se inicialize la pestaña
	 *            de inicio
	 */

	public void inicializarInicio(boolean cerrable,
			Map<String, Object> parametros) {
		abrirPaginaTab(cerrable, urlPaginaInicio, labelTabInicio, parametros);
	}

	/**
	 * Metodo para abrir una pagina nueva en un tab (Esta pagina puede ser la
	 * que se configura en la opcion de menu)
	 * 
	 * @param cerrable
	 *            Determina si la pagina que se abre es cerrable o no
	 * @param url
	 *            Indica cual el la direccion URL de la pagina que se desea
	 *            abrir
	 * @param label
	 *            Indica cual es el nombre del tab que se desea abrir (Nombre
	 *            personalizado)
	 * @return Retorna el tabpanel donde se crea el contendio del tab que se
	 *         agrega
	 */
	public Tabpanel abrirPaginaTab(boolean cerrable, String url, String label) {
		return abrirPaginaTab(cerrable, url, label,
				new HashMap<String, Object>());
	}

	/**
	 * Metodo para abrir una pagina nueva en un tab (Esta pagina puede ser la
	 * que se configura en la opcion de menu)
	 * 
	 * @param cerrable
	 *            Determina si la pagina que se abre es cerrable o no
	 * @param url
	 *            Indica cual el la direccion URL de la pagina que se desea
	 *            abrir
	 * @param label
	 *            Indica cual es el nombre del tab que se desea abrir (Nombre
	 *            personalizado)
	 * @param parametros
	 *            Parametros con los que se quiere que se inicialize la pestaña
	 *            que se desea abrir
	 * @return Retorna el tabpanel donde se crea el contendio del tab que se
	 *         agrega
	 */
	public Tabpanel abrirPaginaTab(boolean cerrable, String url, String label,
			Map<String, Object> parametros) {
		log.info("url ===> " + url);
		Tabpanel tabpanelPagina = null;
		String url_original = url;
		Map<String, Object> mapUrl = Utilidades.descomponerUrl(url);
		Util.mostrarParametros("parametros url", mapUrl);
		url = (String) mapUrl.get("url");
		@SuppressWarnings("unchecked")
		List<Map<String, String>> lista_parametros = (List<Map<String, String>>) mapUrl
				.get("lista_parametros");

		if (parametros == null) {
			parametros = new HashMap<String, Object>();
		}

		for (Map<String, String> mapBean : lista_parametros) {
			for (String key_map : mapBean.keySet()) {
				parametros.put(key_map, mapBean.get(key_map));
			}
		}
		// //log.info("param: " + parametros);
		int indice = -1;
		Component componenteAux;
		for (int i = 0; i < mcTabsContenedor.getChildren().size(); i++) {
			componenteAux = mcTabsContenedor.getChildren().get(i);
			if (componenteAux.getAttribute("URL_PAGINA_TAB").toString()
					.equals(url_original)) {
				indice = i;
				break;
			}
		}

		if (indice == -1) {
			componenteAux = new Tab(label);
			componenteAux.setAttribute("URL_PAGINA_TAB", url_original);
			mcTabsContenedor.appendChild(componenteAux);

			((Tab) componenteAux).setClosable(cerrable);

			final Tabpanel tabpanelPaginaAux = tabpanelPagina = new Tabpanel();

			Component component = Executions.createComponents(url,
					tabpanelPagina, parametros);
			Window window = new Window();
			window.setWidth("100%");
			window.setHeight("100%");
			window.appendChild(component);
			tabpanelPagina.appendChild(window);
			mcTabpanelsContenedor.appendChild(tabpanelPagina);
			mapa_componentes.put("TAB_" + url_original, componenteAux);
			mapa_componentes.put("TABPANEL_" + url_original, tabpanelPaginaAux);
			this.setSelectedTab((Tab) componenteAux);
		} else {
			this.setSelectedIndex(indice);
			tabpanelPagina = (Tabpanel) mapa_componentes.get("TABPANEL_"
					+ url_original);
		}

		if (this.getParent() != null) {
			this.getParent().invalidate();
		}

		return tabpanelPagina;

	}

	/**
	 * Metodo para abrir una pagina nueva, y carga la informacion por demanda en
	 * un tab cuando se hace OnClick o se selecciona (Esta pagina puede ser la
	 * que se configura en la opcion de menu)
	 * 
	 * @param cerrable
	 *            Determina si la pagina que se abre es cerrable o no
	 * @param url
	 *            Indica cual el la direccion URL de la pagina que se desea
	 *            abrir
	 * @param label
	 *            Indica cual es el nombre del tab que se desea abrir (Nombre
	 *            personalizado)
	 * @param parametros
	 *            Parametros con los que se quiere que se inicialize la pestaña
	 *            que se desea abrir
	 * @return Retorna el tabpanel donde se crea el contendio del tab que se
	 *         agrega
	 */
	public Tabpanel abrirPaginaTabDemanda(boolean cerrable, String url,
			String label, Map<String, Object> parametros) {
		Tabpanel tabpanelPagina = null;
		String url_original = url;
		Map<String, Object> mapUrl = Utilidades.descomponerUrl(url);
		url = (String) mapUrl.get("url");
		// log.info("url : " + url);
		@SuppressWarnings("unchecked")
		List<Map<String, String>> lista_parametros = (List<Map<String, String>>) mapUrl
				.get("lista_parametros");

		if (parametros == null) {
			parametros = new HashMap<String, Object>();
		}

		for (Map<String, String> mapBean : lista_parametros) {
			Iterator<Entry<String, String>> it = mapBean.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> e = (Map.Entry<String, String>) it
						.next();
				parametros.put(e.getKey(), e.getValue());
			}
		}

		final Map<String, Object> parametrosAux = parametros;
		final String urlAux = url;
		Component componenteAux;
		int indice = -1;
		for (int i = 0; i < mcTabsContenedor.getChildren().size(); i++) {
			componenteAux = mcTabsContenedor.getChildren().get(i);
			if (componenteAux.getAttribute("URL_PAGINA_TAB").toString()
					.equals(url_original)) {
				indice = i;
				break;
			}
		}

		if (indice == -1) {
			componenteAux = new Tab(label);
			componenteAux.setAttribute("URL_PAGINA_TAB", url_original);
			mcTabsContenedor.appendChild(componenteAux);
			((Tab) componenteAux).setClosable(cerrable);
			final Tabpanel tabpanelPaginaAux = tabpanelPagina = new Tabpanel();

			final Tab tabAux = (Tab) componenteAux;

			EventListener<Event> eventListener = new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					tabpanelPaginaAux.getChildren().clear();
					// log.info("url Pagina : " + urlAux);
					// log.info("parametros : " + parametrosAux);
					Component component = Executions.createComponents(urlAux,
							tabpanelPaginaAux, parametrosAux);
					Window window = new Window();
					window.setWidth("100%");
					window.setHeight("100%");
					window.appendChild(component);
					tabpanelPaginaAux.appendChild(window);
					/* Eliminamos el evento para que recargue */
					tabAux.removeEventListener(Events.ON_CLICK, this);
				}

			};
			tabpanelPaginaAux.setAttribute("EVENTO_TAB", eventListener);
			componenteAux.setAttribute("EVENTO_TAB", eventListener);
			componenteAux.setAttribute("PARAMETROS_MAP", parametrosAux);

			componenteAux.addEventListener(Events.ON_CLICK, eventListener);

			mcTabpanelsContenedor.appendChild(tabpanelPagina);

			mapa_componentes.put("TAB_" + url_original, componenteAux);
			mapa_componentes.put("TABPANEL_" + url_original, tabpanelPaginaAux);
			// this.setSelectedTab(tabPagina);
		} else {
			tabpanelPagina = (Tabpanel) mapa_componentes.get("TABPANEL_"
					+ url_original);
		}

		if (this.getParent() != null) {
			this.getParent().invalidate();
		}
		return tabpanelPagina;
	}

	/**
	 * Recarga el contenido de un tab cuando es por demanda
	 * 
	 * @param tabpanel
	 * @throws Exception
	 */
	public void recargarContenidoTab(Tabpanel tabpanel) throws Exception {
		EventListener<Event> eventListener = (EventListener<Event>) tabpanel
				.getAttribute(EVENTO_TAB);
		if (eventListener != null) {
			eventListener.onEvent(null);
		}
	}

	public void cerrarTabs() {
		mcTabsContenedor.getChildren().clear();
		mcTabpanelsContenedor.getChildren().clear();
	}

	public String getUrlPaginaInicio() {
		return urlPaginaInicio;
	}

	public void setUrlPaginaInicio(String urlPaginaInicio) {
		this.urlPaginaInicio = urlPaginaInicio;
	}

	public String getLabelTabInicio() {
		return labelTabInicio;
	}

	public void setLabelTabInicio(String labelTabInicio) {
		this.labelTabInicio = labelTabInicio;
	}

	public Tabs getTabsContenedor() {
		return mcTabsContenedor;
	}

	public Map<String, Component> getMapa_componentes() {
		return mapa_componentes;
	}
}
