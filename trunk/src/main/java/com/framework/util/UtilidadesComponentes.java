package com.framework.util;

import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.service.ElementoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.framework.locator.ServiceLocatorWeb;

/**
 * @author Dario Perez Campillo
 * @Descripcion Utilidades para inicializar los componentes en los actions,
 *              seleccionar componentes apartir de valores, entre otras cosas.
 */
public class UtilidadesComponentes {

	public static Logger log = Logger.getLogger(UtilidadesComponentes.class);

	/**
	 * Metodo para inicializar una o varias listas desplegable con valores que
	 * se ecuentran en la tabla elemento en la base de datos. Los listboxes van
	 * acorde a su tipo.
	 * 
	 * @param listboxes
	 *            Componente o componentes que tendran los elementos o items
	 * @param seleccione
	 *            Variable que me indica si quiero adicionar un item con la
	 *            palabra seleccione
	 * @param ordenado
	 *            parametro que indica si el listado va a ser en orden
	 *            alfabetico.
	 * @param serviceLocator
	 *            Contenedor de servicios, el cual contiene el servicio
	 *            {@link ElementoService} para consultar los elementos
	 */
	public static void listarElementosListbox(boolean seleccione,
			boolean ordenado, ServiceLocatorWeb serviceLocator,
			Listbox... listboxes) {
		if (listboxes != null) {
			for (Listbox listbox : listboxes) {
				listbox.getItems().clear();
				Listitem listitem;
				if (seleccione) {
					listitem = new Listitem();
					listitem.setValue("");
					listitem.setLabel("-- seleccione --");
					listbox.appendChild(listitem);
				}

				String tipo = listbox.getName();
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("tipo", tipo);
				if (ordenado)
					parametros.put("orden_descripcion", "orden_descripcion");

				List<Elemento> elementos = serviceLocator.getElementoService()
						.listar(parametros);

				for (Elemento elemento : elementos) {
					listitem = new Listitem();
					listitem.setValue(elemento.getCodigo());
					listitem.setLabel(elemento.getDescripcion());
					listbox.appendChild(listitem);
				}

				if (listbox.getItemCount() > 0) {
					listbox.setSelectedIndex(0);
				}
			}
		}

	}

	/**
	 * Metodo para inicializar una o varias listas desplegable con valores que
	 * se ecuentran en la tabla elemento en la base de datos. Los listboxes van
	 * acorde a su tipo.
	 * 
	 * @param listboxes
	 *            Componente o componentes que tendran los elementos o items
	 * @param seleccione
	 *            Variable que me indica si quiero adicionar un item con la
	 *            palabra seleccione
	 * @param ordenado
	 *            parametro que indica si el listado va a ser en orden
	 *            alfabetico.
	 * @param serviceLocator
	 *            Contenedor de servicios, el cual contiene el servicio
	 *            {@link ElementoService} para consultar los elementos
	 */
	public static void listarElementosListbox(boolean seleccione,
			boolean ordenado, ElementoService elementoService,
			Listbox... listboxes) {
		if (listboxes != null) {
			for (Listbox listbox : listboxes) {
				listbox.getItems().clear();
				Listitem listitem;
				if (seleccione) {
					listitem = new Listitem();
					listitem.setValue("");
					listitem.setLabel("-- seleccione --");
					listbox.appendChild(listitem);
				}

				String tipo = listbox.getName();
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("tipo", tipo);
				if (ordenado)
					parametros.put("orden_descripcion", "orden_descripcion");

				List<Elemento> elementos = elementoService.listar(parametros);
				for (Elemento elemento : elementos) {
					listitem = new Listitem();
					listitem.setValue(elemento.getCodigo());
					listitem.setLabel(elemento.getDescripcion());
					listbox.appendChild(listitem);
				}

				if (listbox.getItemCount() > 0) {
					listbox.setSelectedIndex(0);
				}
			}
		}

	}

	/**
	 * Metodo para inicializar una o varias listas desplegable con valores que
	 * se ecuentran en la tabla elemento en la base de datos. Los listboxes
	 * tienen los mismos elementos, por defecto se coje el tipo del primero.
	 * 
	 * @param listboxes
	 *            Componente o componentes que tendran los elementos o items
	 * @param seleccione
	 *            Variable que me indica si quiero adicionar un item con la
	 *            palabra seleccione
	 * @param ordenado
	 *            parametro que indica si el listado va a ser en orden
	 *            alfabetico.
	 * @param tipo
	 *            Es el tipo de elemento que se buscara en la base de datos para
	 *            llenar todos los listboxes
	 * @param serviceLocator
	 *            Contenedor de servicios, el cual contiene el servicio
	 *            {@link ElementoService} para consultar los elementos
	 */
	public static void listarElementosListboxMultiple(boolean seleccione,
			boolean ordenado, String tipo, ServiceLocatorWeb serviceLocator,
			Listbox... listboxes) {
		if (listboxes != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("tipo", tipo);
			if (ordenado)
				parametros.put("orden_descripcion", "orden_descripcion");

			List<Elemento> elementos = serviceLocator.getElementoService()
					.listar(parametros);
			for (Listbox listbox : listboxes) {
				listbox.getItems().clear();
				Listitem listitem;
				if (seleccione) {
					listitem = new Listitem();
					listitem.setValue("");
					listitem.setLabel("-- seleccione --");
					listbox.appendChild(listitem);
				}

				for (Elemento elemento : elementos) {
					listitem = new Listitem();
					listitem.setValue(elemento.getCodigo());
					listitem.setLabel(elemento.getDescripcion());
					listbox.appendChild(listitem);
				}

				if (listbox.getItemCount() > 0) {
					listbox.setSelectedIndex(0);
				}
			}
		}

	}

	/**
	 * Este metodo es para llenar los listboxes apartir de una lista de
	 * elementos.
	 * 
	 * @param listboxes
	 *            Elementos que se van a llenar a partir de la lista de
	 *            elementos
	 * @param select
	 *            Variable que me indica si quiero adicionar un item con la
	 *            palabra seleccione
	 * @param elementos
	 *            Listado de elementos que van a contener los listboxes
	 * 
	 * @param selectEnd
	 *            parametro que me indica si selecciono el primero o el ultimo
	 *            item de la lista
	 */

	public static void listarElementosListbox(boolean select,
			List<Elemento> elementos, boolean selectEnd, Listbox... listboxes) {
		if (listboxes != null) {
			for (Listbox listbox : listboxes) {
				listbox.getChildren().clear();
				Listitem listitem;
				if (select) {
					listitem = new Listitem();
					listitem.setValue("");
					listitem.setLabel("-- seleccione --");
					listbox.appendChild(listitem);
				}

				for (Elemento elemento : elementos) {
					listitem = new Listitem();
					listitem.setValue(elemento.getCodigo());
					listitem.setLabel(elemento.getDescripcion());
					listbox.appendChild(listitem);
				}

				if (listbox.getItemCount() > 0) {
					if (!selectEnd) {
						listbox.setSelectedIndex(0);
					} else {
						listbox.setSelectedIndex(listbox.getChildren().size() - 1);
					}
				}
			}
		}
	}

}
