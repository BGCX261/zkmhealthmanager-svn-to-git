/*
 * anio_soatAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Elemento;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Totales_resultadosAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Vias_ingresoAction.class);

	// Componentes //
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
	private Textbox tbxCodigo;
	@View
	private Textbox tbxNombre;

	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {

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
		tbxCodigo.setDisabled(false);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxCodigo.setStyle("background-color:white");
		tbxNombre.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo.getValue().trim().equals("")) {
			tbxCodigo.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (tbxNombre.getValue().trim().equals("")) {
			tbxNombre.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String value = tbxValue.getValue().toLowerCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parametroTodo", value);
			parameters.put("tipo", "via_ingreso");

			List<Elemento> lista_datos = getServiceLocator()
					.getElementoService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Elemento elemento : lista_datos) {
				rowsResultado.appendChild(crearFilas(elemento, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Elemento elemento = (Elemento) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(elemento.getCodigo() + ""));
		fila.appendChild(new Label(elemento.getDescripcion()));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(elemento);
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
									eliminarDatos(elemento);
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

				Elemento elemento = new Elemento();
				elemento.setCodigo(tbxCodigo.getValue().trim());
				elemento.setTipo("via_ingreso");
				elemento.setDescripcion(tbxNombre.getValue().trim());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getElementoService().crear(elemento);
					accionForm(true, "registrar");
				} else {
					int result = getServiceLocator().getElementoService()
							.actualizar(elemento);
					if (result == 0) {
						throw new ValidacionRunTimeException(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Elemento elemento = (Elemento) obj;
		try {
			tbxCodigo.setValue(elemento.getCodigo());
			tbxCodigo.setDisabled(true);
			tbxNombre.setValue(elemento.getDescripcion());
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Elemento elemento = (Elemento) obj;
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("via_ingreso", elemento.getCodigo());

			if (!getServiceLocator().getAdmisionService().existe(parametros)) {
				int result = getServiceLocator().getElementoService().eliminar(
						elemento);
				if (result == 0) {
					throw new Exception(
							"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
				}

				Messagebox.show("Informacion se eliminó satisfactoriamente !!",
						"Information", Messagebox.OK, Messagebox.INFORMATION);
			} else {
				MensajesUtil
						.mensajeAlerta(
								"Admisiones creadas existentes",
								"Existen admisiones creadas para esta via de ingreso por lo que se hace imposible poder eliminar");
			}

		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}
}