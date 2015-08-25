/*
 * horarios_nuevoAction.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Horarios_nuevo;
import healthmanager.modelo.service.Horarios_nuevoService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Horarios_nuevoAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Horarios_nuevoAction.class);

	private Horarios_nuevoService horarios_nuevoService;

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
	private Textbox tbxCodigo_horario;
	@View
	private Textbox tbxDescripcion;
	@View
	private Textbox tbxCodigo_centro;

	@View
	private Datebox dtbxFecha_inicio;

	@View
	private Datebox dtbxFecha_final;

	@View
	private Listbox lbxAnios;
	@View
	private Listbox lbxMeses;

	@View
	private Spinner spinnerTiempo_cita;
	
	private Calendar calendar_current;

	private final String[] idsExcluyentes = { "lbxAnios", "lbxMeses" };

	@Override
	public void init() {
		calendar_current = Calendar.getInstance();
		listarCombos();
		inicializarAnios();
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {

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
		dtbxFecha_inicio.setText("");
		dtbxFecha_final.setText("");
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;
		XulElement xulElement = null;
		if (tbxCodigo_horario.getValue().trim().isEmpty()) {
			MensajesUtil
					.notificarError("El campo Codigo horario es obligatorio",
							tbxCodigo_horario);
			valida = false;
			if (xulElement == null)
				xulElement = tbxCodigo_horario;
		}
		if (tbxDescripcion.getValue().trim().isEmpty()) {
			MensajesUtil.notificarError("El campo Descripcion es obligatorio",
					tbxDescripcion);
			valida = false;
			if (xulElement == null)
				xulElement = tbxDescripcion;
		}
		if (tbxCodigo_centro.getValue().trim().isEmpty()) {
			MensajesUtil.notificarError(
					"El campo Codigo centro es obligatorio", tbxCodigo_centro);
			valida = false;
			if (xulElement == null)
				xulElement = tbxCodigo_centro;
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

			parameters.put("value", "%" + value + "%");

			horarios_nuevoService.setLimit("limit 25 offset 0");

			List<Horarios_nuevo> lista_datos = horarios_nuevoService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Horarios_nuevo horarios_nuevo : lista_datos) {
				rowsResultado.appendChild(crearFilas(horarios_nuevo, this));
			}

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Horarios_nuevo horarios_nuevo = (Horarios_nuevo) objeto;

		fila.appendChild(new Label(horarios_nuevo.getCodigo_horario() + ""));
		fila.appendChild(new Label(horarios_nuevo.getDescripcion() + ""));
		fila.appendChild(new Label(horarios_nuevo.getCodigo_centro() + ""));

		Hlayout hlayout = new Hlayout();
		fila.setStyle("text-align: justify;nowrap:nowrap");
		Toolbarbutton toolbarbutton = new Toolbarbutton("Editar");
		toolbarbutton.setImage("/images/editar.gif");
		toolbarbutton.setTooltiptext("Editar registro");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(horarios_nuevo);
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
													eliminarDatos(horarios_nuevo);
													buscarDatos();
												}
											}
										});
					}
				});
		hlayout.appendChild(new Space());
		hlayout.appendChild(toolbarbutton);
		fila.appendChild(hlayout);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			//log.info("ejecutando metodo guardarDatos()");
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				Horarios_nuevo horarios_nuevo = new Horarios_nuevo();
				horarios_nuevo.setCodigo_empresa(codigo_empresa);
				horarios_nuevo.setCodigo_sucursal(codigo_sucursal);
				horarios_nuevo.setCodigo_horario(tbxCodigo_horario.getValue());
				horarios_nuevo.setDescripcion(tbxDescripcion.getValue());
				horarios_nuevo.setCodigo_centro(tbxCodigo_centro.getValue());
				horarios_nuevo.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				horarios_nuevo
						.setCreacion_user(usuarios.getCodigo().toString());
				horarios_nuevo.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				horarios_nuevo.setUltimo_user(usuarios.getCodigo().toString());
				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("horarios_nuevo", horarios_nuevo);
				mapa_datos.put("accion", tbxAccion.getValue());
				horarios_nuevoService.guardarDatos(mapa_datos);
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
		Horarios_nuevo horarios_nuevo = (Horarios_nuevo) obj;
		try {
			tbxCodigo_horario.setValue(horarios_nuevo.getCodigo_horario());
			tbxDescripcion.setValue(horarios_nuevo.getDescripcion());
			tbxCodigo_centro.setValue(horarios_nuevo.getCodigo_centro());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Horarios_nuevo horarios_nuevo = (Horarios_nuevo) obj;
		try {
			int result = horarios_nuevoService.eliminar(horarios_nuevo);
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

	private void inicializarAnios() {
		int anio_sistema = anio != null ? anio : calendar_current
				.get(Calendar.YEAR);

		for (int i = (anio_sistema - 5); i < (anio_sistema + 5); i++) {
			Listitem listitem = new Listitem();
			listitem.setValue(i);
			listitem.setLabel("" + i);
			if (i == anio_sistema) {
				listitem.setSelected(true);
			}
			lbxAnios.appendChild(listitem);
		}

		lbxAnios.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Date date = Calendar.getInstance().getTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.set(
						Calendar.YEAR,
						Integer.parseInt(lbxAnios.getSelectedItem().getValue()
								.toString()));
			}
		});

		lbxMeses.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Date date = Calendar.getInstance().getTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.set(Calendar.MONTH, lbxMeses.getSelectedIndex());
			}
		});
		lbxMeses.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
	}

	public void agregarSecuenciaHorario() throws Exception {
		try {
			// if (lbxRoles.getSelectedItem() != null) {
			// Roles_usuarios roles_usuarios = (Roles_usuarios) lbxRoles
			// .getSelectedItem().getValue();
			Map map = new HashMap();
			map.put("date", calendar_current.getTime());
			map.put("tiempo_cita", spinnerTiempo_cita.getValue());
			// map.put("horas_medico", roles_usuarios.getHoras_medico());
//			map.put("codigo_medico", tbxCodigo_prestador.getValue().toString());
//			Roles_usuarios roles_usuarios = (lbxRoles.getSelectedItem() != null ? (Roles_usuarios) lbxRoles
//					.getSelectedItem().getValue() : null);
//			map.put("rol_seleccionado",
//					roles_usuarios != null ? roles_usuarios.getRol() : "");

			Component componente = Executions.createComponents(
					"/pages/secuencia_horario_nuevo.zul", this, map);

			final Window ventana = (Window) componente;
			ventana.doModal();
			// } else {
			// MensajesUtil
			// .mensajeAlerta(
			// "Seleccionar Rol",
			// "Debe seleccionar el rol de usuario para poder agregar una secuencia de horario "
			// +
			// "ya que el rol como tal contiene la cantidad de horas que trabaja este prestador");
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}