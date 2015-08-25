/*
 * barrioAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Localidad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.XulElement;

import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class BarrioAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(BarrioAction.class);

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Textbox tbxCodigo_barrio;
	@View
	private Textbox tbxBarrio;
	@View
	private Textbox tbxDescripcion;
	@View
	private Listbox lbxTipo;
	@View
	private Listbox lbxCodigo_localidad;
	private final String[] idsExcluyentes = { "btCancel", "btGuardar",
			"tbxAccion", "btNew" };
	
	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxTipo, false, getServiceLocator());
		Utilidades.listarLocalidades(lbxCodigo_localidad, true,
				getServiceLocator());
		parametrizarResultadoPaginado();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_barrio");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("barrio");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		tbxCodigo_barrio.setDisabled(false);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxCodigo_barrio
				.setStyle("text-transform:uppercase;background-color:white");
		tbxBarrio.setStyle("text-transform:uppercase;background-color:white");
		lbxCodigo_localidad.setStyle("background-color:white");

		boolean valida = true;

		if (tbxCodigo_barrio.getText().trim().equals("")) {
			tbxCodigo_barrio
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (tbxBarrio.getText().trim().equals("")) {
			tbxBarrio
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (lbxCodigo_localidad.getSelectedItem().getValue().equals("")) {
			lbxCodigo_localidad.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			
			List<Barrio> lista_datos = getServiceLocator().getBarrioService()
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Barrio barrio : lista_datos) {
				rowsResultado.appendChild(crearFilas(barrio, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			
			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Barrio barrio = (Barrio) objeto;

		Localidad localidad = new Localidad();
		localidad.setCodigo_localidad(barrio != null ? barrio
				.getCodigo_localidad() : "");
		localidad = getServiceLocator().getServicio(GeneralExtraService.class).consultar(
				localidad);

		String tipo = Utilidades.getNombreElemento(barrio.getTipo(),
				"tipo_barrio", this);

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(barrio.getCodigo_barrio() + ""));
		fila.appendChild(new Label(barrio.getBarrio() + ""));
		fila.appendChild(new Label(tipo));
		fila.appendChild(new Label((localidad != null ? localidad
				.getLocalidad() : "")));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(barrio);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(barrio);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //

				Barrio barrio = new Barrio();
				barrio.setCodigo_barrio(tbxCodigo_barrio.getValue());
				barrio.setBarrio(tbxBarrio.getValue());
				barrio.setDescripcion(tbxDescripcion.getValue());
				barrio.setTipo(lbxTipo.getSelectedItem().getValue().toString());
				barrio.setCodigo_localidad(lbxCodigo_localidad
						.getSelectedItem().getValue().toString());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getBarrioService().crear(barrio);
					accionForm(true, "registrar");
				} else {
					int result = getServiceLocator().getBarrioService()
							.actualizar(barrio);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			Messagebox.show(e.getMessage(), "informacion", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Barrio barrio = (Barrio) obj;
		try {
			tbxCodigo_barrio.setValue(barrio.getCodigo_barrio());
			tbxBarrio.setValue(barrio.getBarrio());
			tbxDescripcion.setValue(barrio.getDescripcion());
			for (int i = 0; i < lbxTipo.getItemCount(); i++) {
				Listitem listitem = lbxTipo.getItemAtIndex(i);
				if (listitem.getValue().toString().equals(barrio.getTipo())) {
					listitem.setSelected(true);
					i = lbxTipo.getItemCount();
				}
			}
			for (int i = 0; i < lbxCodigo_localidad.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_localidad.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(barrio.getCodigo_localidad())) {
					listitem.setSelected(true);
					i = lbxCodigo_localidad.getItemCount();
				}
			}

			tbxCodigo_barrio.setDisabled(true);
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Barrio barrio = (Barrio) obj;
		try {
			int result = getServiceLocator().getBarrioService()
					.eliminar(barrio);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		}
	}
	
	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Barrio> listarResultados(
					Map<String, Object> parametros) {
				List<Barrio> listado = getServiceLocator()
						.getBarrioService().listar(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getBarrioService().totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilas(dato, gridResultado);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado,5);
	}

}