/*
 * ficha_epidemiologiaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.service.Ficha_epidemiologiaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.util.Utilidades;

public class Ficha_epidemiologiaAction extends ZKWindow{

	
	private Ficha_epidemiologiaService ficha_epidemiologiaService;
	
	//Componentes //
	
	@View
	private Listbox listboxHistorias;

	
	@Override
	public void init(){
		consultarFichaEpidemiologica();
	}
	
	
	public void consultarFichaEpidemiologica() {
		listboxHistorias.getItems().clear();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);

		List<Ficha_epidemiologia> listado = ficha_epidemiologiaService.listar(parametros);

		for (final Ficha_epidemiologia ficha : listado) {
			Listitem listitem = new Listitem();
			listitem.appendChild(new Listcell());
			listitem.appendChild(Utilidades.obtenerListcell(
					ficha.getCodigo(), Textbox.class, true, false));
			listitem.appendChild(Utilidades.obtenerListcell(
					ficha.getTitulo(), Textbox.class, true, false));
			Checkbox checkbox = new Checkbox();
			checkbox.setChecked(ficha.isEstado());
			checkbox.setDisabled(true);
			Listcell listcell = new Listcell();
			listcell.appendChild(checkbox);
			listitem.appendChild(listcell);
			listitem.setValue(ficha);
			listboxHistorias.appendChild(listitem);

			listitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					editarFichaEpidemiologia(ficha);
				}
			});
			
		}


	}


	public void editarFichaEpidemiologia(final Ficha_epidemiologia ficha){
		
		final Window window = (Window) Executions.createComponents(
				"/pages/editar_ficha.zul", this, null);
		final Textbox tbxNombre = (Textbox) window.getFellow("tbxNombre");
		tbxNombre.setValue(ficha.getTitulo());
		final Textbox tbxDescripcion = (Textbox) window.getFellow("tbxDescripcion");
		tbxDescripcion.setValue(ficha.getDescripcion());
		final Checkbox chkEstado = (Checkbox)window.getFellow("chkEstado");
		chkEstado.setChecked(ficha.isEstado());
		Toolbarbutton toolbarbutton = (Toolbarbutton) window.getFellow("btnGuardar");
		
		toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				Ficha_epidemiologia ficha_aux = new Ficha_epidemiologia();
				ficha_aux.setCodigo_empresa(codigo_empresa);
				ficha_aux.setCodigo_sucursal(codigo_sucursal);
				
				ficha_aux.setTitulo(tbxNombre.getValue().toUpperCase());
				ficha_aux.setDescripcion(tbxDescripcion.getValue().toUpperCase());
				ficha_aux.setEstado(chkEstado.isChecked());
				ficha_aux.setCodigo(ficha.getCodigo());
				ficha_aux.setUrl_pagina(ficha.getUrl_pagina());
				ficha_aux.setRepetitivo(ficha.isRepetitivo());
				

				ficha_epidemiologiaService.actualizar(ficha_aux);

				consultarFichaEpidemiologica();
				
				window.onClose();
			}
		});
		window.setTitle("Actualizar Ficha Epidemiologica");
		window.setMode("modal");
	}

}