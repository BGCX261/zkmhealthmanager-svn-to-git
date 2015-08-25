/*
 * manuales_procedimientosAction.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Manuales_procedimientos;
import healthmanager.modelo.service.Manuales_procedimientosService;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.XulElement;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Manuales_procedimientosAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Manuales_procedimientosAction.class);

	private Manuales_procedimientosService manuales_procedimientosService;

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
	private Listbox listboxResultado;

	@View
	private Longbox lgxId_manual;
	@View
	private Longbox lgxId_procedimiento;
	@View
	private Doublebox dbxValor;
	@View
	private Textbox tbxCodigo_manual;
	@View
	private Textbox tbxGrupo_uvr;
	@View
	private Div divPopups;
	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("id_manual");
		listitem.setLabel("Id_manual");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("id_procedimiento");
		listitem.setLabel("Id_procedimiento");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("valor");
		listitem.setLabel("Valor");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_manual");
		listitem.setLabel("Codigo_manual");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("grupo_uvr");
		listitem.setLabel("Grupo_uvr");
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

		boolean valida = true;
		XulElement xulElement = null;
		if (lgxId_manual.getValue() == null) {
			MensajesUtil.notificarError("El campo Id manual es obligatorio",
					lgxId_manual);
			valida = false;
			if (xulElement == null)
				xulElement = lgxId_manual;
		}
		if (lgxId_procedimiento.getValue() == null) {
			MensajesUtil.notificarError(
					"El campo Id procedimiento es obligatorio",
					lgxId_procedimiento);
			valida = false;
			if (xulElement == null)
				xulElement = lgxId_procedimiento;
		}
		if (dbxValor.getValue() == null) {
			MensajesUtil.notificarError("El campo Valor es obligatorio",
					dbxValor);
			valida = false;
			if (xulElement == null)
				xulElement = dbxValor;
		}
		if (tbxCodigo_manual.getValue().trim().isEmpty()) {
			MensajesUtil.notificarError(
					"El campo Codigo manual es obligatorio", tbxCodigo_manual);
			valida = false;
			if (xulElement == null)
				xulElement = tbxCodigo_manual;
		}
		if (tbxGrupo_uvr.getValue().trim().isEmpty()) {
			MensajesUtil.notificarError("El campo Grupo uvr es obligatorio",
					tbxGrupo_uvr);
			valida = false;
			if (xulElement == null)
				xulElement = tbxGrupo_uvr;
		}

		if (xulElement != null) {
			Clients.scrollIntoView(xulElement);
			xulElement.setFocus(true);
		}
		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			String value = tbxValue.getValue().trim().toUpperCase();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();

			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			manuales_procedimientosService.setLimit("limit 25 offset 0");

			List<Manuales_procedimientos> lista_datos = manuales_procedimientosService
					.listar(parameters);
			listboxResultado.getItems().clear();
			divPopups.getChildren().clear();

			for (Manuales_procedimientos manuales_procedimientos : lista_datos) {
				listboxResultado.appendChild(crearFilas(
						manuales_procedimientos, this));
			}

			listboxResultado.applyProperties();
			listboxResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente)
			throws Exception {
		Listitem fila = new Listitem();

		final Manuales_procedimientos manuales_procedimientos = (Manuales_procedimientos) objeto;

		fila.appendChild(new Listcell(manuales_procedimientos.getId_manual()
				+ ""));
		fila.appendChild(new Listcell(manuales_procedimientos
				.getId_procedimiento() + ""));
		fila.appendChild(new Listcell(manuales_procedimientos.getValor() + ""));
		fila.appendChild(new Listcell(manuales_procedimientos
				.getCodigo_manual() + ""));
		fila.appendChild(new Listcell(manuales_procedimientos.getGrupo_uvr()
				+ ""));

		Hlayout hlayout = new Hlayout();
		fila.setStyle("text-align: justify;nowrap:nowrap");
		Toolbarbutton toolbarbutton = new Toolbarbutton("Editar");
		toolbarbutton.setImage("/images/editar.gif");
		toolbarbutton.setTooltiptext("Editar registro");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(manuales_procedimientos);
					}
				});
		hlayout.appendChild(toolbarbutton);
		toolbarbutton = new Toolbarbutton("Eliminar");
		toolbarbutton.setImage("/images/eliminar.png");
		toolbarbutton.setTooltiptext("Eliminar registro");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("Esta seguro que desea eliminar este registro? ",
										"Eliminar Registro",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													eliminarDatos(manuales_procedimientos);
													buscarDatos();
												}
											}
										});
					}
				});
		hlayout.appendChild(toolbarbutton);
		Listcell listcell = new Listcell();
		listcell.appendChild(hlayout);
		fila.appendChild(listcell);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			//log.info("ejecutando metodo guardarDatos()");
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				Manuales_procedimientos manuales_procedimientos = new Manuales_procedimientos();
				manuales_procedimientos
						.setId_manual((lgxId_manual.getValue() != null ? lgxId_manual
								.getValue() : 0));
				manuales_procedimientos
						.setId_procedimiento((lgxId_procedimiento.getValue() != null ? lgxId_procedimiento
								.getValue() : 0));
				manuales_procedimientos
						.setValor((dbxValor.getValue() != null ? dbxValor
								.getValue() : 0.00));
				manuales_procedimientos.setCodigo_manual(tbxCodigo_manual
						.getValue());
				manuales_procedimientos.setGrupo_uvr(tbxGrupo_uvr.getValue());
				manuales_procedimientos.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				manuales_procedimientos.setCreacion_user(usuarios.getCodigo()
						.toString());
				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("manuales_procedimientos",
						manuales_procedimientos);
				mapa_datos.put("accion", tbxAccion.getValue());
				manuales_procedimientosService.guardarDatos(mapa_datos);
				tbxAccion.setValue("modificar");
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Manuales_procedimientos manuales_procedimientos = (Manuales_procedimientos) obj;
		try {
			lgxId_manual.setValue(manuales_procedimientos.getId_manual());
			lgxId_procedimiento.setValue(manuales_procedimientos
					.getId_procedimiento());
			dbxValor.setValue(manuales_procedimientos.getValor());
			tbxCodigo_manual.setValue(manuales_procedimientos
					.getCodigo_manual());
			tbxGrupo_uvr.setValue(manuales_procedimientos.getGrupo_uvr());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Manuales_procedimientos manuales_procedimientos = (Manuales_procedimientos) obj;
		try {
			int result = manuales_procedimientosService
					.eliminar(manuales_procedimientos);
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