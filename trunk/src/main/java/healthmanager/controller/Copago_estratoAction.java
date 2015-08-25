/*
 * copago_estratoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Copago_estrato;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Doublebox;
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

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Copago_estratoAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Copago_estratoAction.class);

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
	private Listbox lbxEstrato;
	@View
	private Doublebox dbxPorcentaje;
	@View
	private Doublebox dbxValor_max_contrib;
	@View
	private Doublebox dbxValor_max_sub;
	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxEstrato, true, getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("estrato");
		listitem.setLabel("Estrato");
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
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		lbxEstrato.setStyle("background-color:white");
		dbxPorcentaje.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (lbxEstrato.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxEstrato.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (dbxPorcentaje.getText().trim().equals("")) {
			dbxPorcentaje.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (dbxPorcentaje.getValue() <= 0) {
			mensaje = "El porcentaje no puede ser menor ni igual a cero";
			dbxPorcentaje.setStyle("background-color:#F6BBBE");
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
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getCopago_estratoService().setLimit(
					"limit 25 offset 0");

			List<Copago_estrato> lista_datos = getServiceLocator()
					.getCopago_estratoService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Copago_estrato copago_estrato : lista_datos) {
				rowsResultado.appendChild(crearFilas(copago_estrato, this));
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

		final Copago_estrato copago_estrato = (Copago_estrato) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(copago_estrato.getEstrato() + ""));
		fila.appendChild(new Label(Utilidades.formatDecimal(
				(copago_estrato.getPorcentaje() * 100), "#,##0.00")
				+ " %"));
		fila.appendChild(new Label(Utilidades.formatDecimal(
				copago_estrato.getValor_max_contrib(), "#,##0.00")
				+ ""));
		fila.appendChild(new Label(Utilidades.formatDecimal(
				copago_estrato.getValor_max_sub(), "#,##0.00")
				+ ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(copago_estrato);
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
									eliminarDatos(copago_estrato);
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

				Copago_estrato copago_estrato = new Copago_estrato();
				copago_estrato.setCodigo_empresa(empresa.getCodigo_empresa());
				copago_estrato
						.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				copago_estrato.setEstrato(lbxEstrato.getSelectedItem()
						.getValue().toString());
				copago_estrato
						.setPorcentaje((dbxPorcentaje.getValue() != null ? (dbxPorcentaje
								.getValue() / 100) : 0.00));
				copago_estrato.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				copago_estrato.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				copago_estrato
						.setCreacion_user(usuarios.getCodigo().toString());
				copago_estrato.setUltimo_user(usuarios.getCodigo().toString());
				copago_estrato.setValor_max_contrib((dbxValor_max_contrib
						.getValue() != null ? dbxValor_max_contrib.getValue()
						: 0.00));
				copago_estrato
						.setValor_max_sub((dbxValor_max_sub.getValue() != null ? dbxValor_max_sub
								.getValue() : 0.00));

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getCopago_estratoService().crear(
							copago_estrato);
					accionForm(true, "registrar");
				} else {
					int result = getServiceLocator().getCopago_estratoService()
							.actualizar(copago_estrato);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}
		} catch (Exception e) {
			Messagebox.show(e.getMessage(), "information", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Copago_estrato copago_estrato = (Copago_estrato) obj;
		try {
			for (int i = 0; i < lbxEstrato.getItemCount(); i++) {
				Listitem listitem = lbxEstrato.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(copago_estrato.getEstrato())) {
					listitem.setSelected(true);
					i = lbxEstrato.getItemCount();
				}
			}
			dbxPorcentaje.setValue(copago_estrato.getPorcentaje() * 100);
			dbxValor_max_contrib
					.setValue(copago_estrato.getValor_max_contrib());
			dbxValor_max_sub.setValue(copago_estrato.getValor_max_sub());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Copago_estrato copago_estrato = (Copago_estrato) obj;
		try {
			int result = getServiceLocator().getCopago_estratoService()
					.eliminar(copago_estrato);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
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