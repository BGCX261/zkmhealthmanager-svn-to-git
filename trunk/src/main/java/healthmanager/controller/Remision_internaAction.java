/*
 * remision_internaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Remision_internaService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Remision_internaAction extends ZKWindow {

	private Remision_internaService remision_internaService;

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
	private Checkbox chbCrecimiento_desarrollo;
	@View
	private Checkbox chbDet_alteracion_joven;
	@View
	private Checkbox chbDet_alteracion_adulto_mayor;
	@View
	private Checkbox chbControl_prenatal;
	@View
	private Checkbox chbUrgencia;
	@View
	private Checkbox chbDet_alteracion_agudeza_visual;
	@View
	private Checkbox chbProg_hipertencion_arterial;
	@View
	private Checkbox chbProg_planificacion_fami;
	@View
	private Checkbox chbPsicologia;
	@View
	private Checkbox chbNutricion;
	@View
	private Checkbox chbExamen_fisico;
	@View
	private Checkbox chbPrev_salud_bucal;
	@View
	private Checkbox chbVacunacion;
	@View
	private Checkbox chbVacunacion_combinada;
	@View
	private Checkbox chbCitologia_servicio;
	@View
	private Checkbox chbAtencion_recien_nacido;
	@View
	private Checkbox chbProg_diabetes;
	@View
	private Checkbox chbProg_tbc;
	@View
	private Checkbox chbProg_lepra;
	@View
	private Checkbox chbOdontologia;
	@View
	private Checkbox chbConsulta_externa;
	@View
	private Textbox tbxGestor;
	@View
	private Textbox tbxObservacion;
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
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("codigo_paciente");
		listitem.setLabel("Codigo_paciente");
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

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Remision_interna> lista_datos = remision_internaService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Remision_interna remision_interna : lista_datos) {
				rowsResultado.appendChild(crearFilas(remision_interna, this));
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

		final Remision_interna remision_interna = (Remision_interna) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(remision_interna);
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
									eliminarDatos(remision_interna);
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

				Remision_interna remision_interna = new Remision_interna();
				remision_interna.setCodigo_empresa(empresa.getCodigo_empresa());
				remision_interna.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				remision_interna.setCodigo_historia(null);
				remision_interna.setCodigo_paciente("");
				remision_interna.setFecha_inicio(new Timestamp(new Date()
						.getTime()));
				remision_interna.setIdentificacion("");
				remision_interna
						.setCrecimiento_desarrollo(chbCrecimiento_desarrollo
								.isChecked() ? "S" : "N");
				remision_interna
						.setDet_alteracion_joven(chbDet_alteracion_joven
								.isChecked() ? "S" : "N");
				remision_interna
						.setDet_alteracion_adulto_mayor(chbDet_alteracion_adulto_mayor
								.isChecked() ? "S" : "N");
				remision_interna.setControl_prenatal(chbControl_prenatal
						.isChecked() ? "S" : "N");
				remision_interna.setUrgencia(chbUrgencia
						.isChecked() ? "S" : "N");
				remision_interna
						.setDet_alteracion_agudeza_visual(chbDet_alteracion_agudeza_visual
								.isChecked() ? "S" : "N");
				remision_interna
						.setProg_hipertencion_arterial(chbProg_hipertencion_arterial
								.isChecked() ? "S" : "N");
				remision_interna
						.setProg_planificacion_fami(chbProg_planificacion_fami
								.isChecked() ? "S" : "N");
				remision_interna.setPsicologia(chbPsicologia.isChecked() ? "S"
						: "N");
				remision_interna.setNutricion(chbNutricion.isChecked() ? "S"
						: "N");
				remision_interna
						.setExamen_fisico(chbExamen_fisico.isChecked() ? "S"
								: "N");
				remision_interna.setPrev_salud_bucal(chbPrev_salud_bucal
						.isChecked() ? "S" : "N");
				remision_interna.setVacunacion(chbVacunacion.isChecked() ? "S"
						: "N");
				remision_interna.setCitologia_servicio(chbCitologia_servicio
						.isChecked() ? "S" : "N");
				remision_interna
						.setAtencion_recien_nacido(chbAtencion_recien_nacido
								.isChecked() ? "S" : "N");
				remision_interna
						.setProg_diabetes(chbProg_diabetes.isChecked() ? "S"
								: "N");
				remision_interna.setProg_tbc(chbProg_tbc.isChecked() ? "S"
						: "N");
				remision_interna.setProg_lepra(chbProg_lepra.isChecked() ? "S"
						: "N");
				remision_interna
						.setOdontologia(chbOdontologia.isChecked() ? "S" : "N");
				remision_interna.setConsulta_externa(chbConsulta_externa
						.isChecked() ? "S" : "N");
				remision_interna.setGestor(tbxGestor.getValue());
				remision_interna.setObservacion(tbxObservacion.getValue());
				remision_interna.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				remision_interna.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				remision_interna.setDelete_date(null);
				remision_interna.setCreacion_user(usuarios.getCodigo()
						.toString());
				remision_interna
						.setUltimo_user(usuarios.getCodigo().toString());
				remision_interna.setDelete_user(null);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					remision_internaService.crear(remision_interna);
					accionForm(true, "registrar");
				} else {
					int result = remision_internaService
							.actualizar(remision_interna);
					if (result == 0) {
						throw new Exception(
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
		Remision_interna remision_interna = (Remision_interna) obj;
		try {
			chbCrecimiento_desarrollo.setChecked(remision_interna
					.getCrecimiento_desarrollo().equals("S") ? true : false);
			chbDet_alteracion_joven.setChecked(remision_interna
					.getDet_alteracion_joven().equals("S") ? true : false);
			chbDet_alteracion_adulto_mayor.setChecked(remision_interna
					.getDet_alteracion_adulto_mayor().equals("S") ? true
					: false);
			chbControl_prenatal.setChecked(remision_interna
					.getControl_prenatal().equals("S") ? true : false);
			chbUrgencia.setChecked(remision_interna
					.getUrgencia().equals("S") ? true : false);
			chbDet_alteracion_agudeza_visual.setChecked(remision_interna
					.getDet_alteracion_agudeza_visual().equals("S") ? true
					: false);
			chbProg_hipertencion_arterial
					.setChecked(remision_interna
							.getProg_hipertencion_arterial().equals("S") ? true
							: false);
			chbProg_planificacion_fami.setChecked(remision_interna
					.getProg_planificacion_fami().equals("S") ? true : false);
			chbPsicologia.setChecked(remision_interna.getPsicologia().equals(
					"S") ? true : false);
			chbNutricion
					.setChecked(remision_interna.getNutricion().equals("S") ? true
							: false);
			chbExamen_fisico.setChecked(remision_interna.getExamen_fisico()
					.equals("S") ? true : false);
			chbPrev_salud_bucal.setChecked(remision_interna
					.getPrev_salud_bucal().equals("S") ? true : false);
			chbVacunacion.setChecked(remision_interna.getVacunacion().equals(
					"S") ? true : false);
			chbCitologia_servicio.setChecked(remision_interna
					.getCitologia_servicio().equals("S") ? true : false);
			chbAtencion_recien_nacido.setChecked(remision_interna
					.getAtencion_recien_nacido().equals("S") ? true : false);
			chbProg_diabetes.setChecked(remision_interna.getProg_diabetes()
					.equals("S") ? true : false);
			chbProg_tbc
					.setChecked(remision_interna.getProg_tbc().equals("S") ? true
							: false);
			chbProg_lepra.setChecked(remision_interna.getProg_lepra().equals(
					"S") ? true : false);
			chbOdontologia.setChecked(remision_interna.getOdontologia().equals(
					"S") ? true : false);
			chbConsulta_externa.setChecked(remision_interna
					.getConsulta_externa().equals("S") ? true : false);
			tbxGestor.setValue(remision_interna.getGestor());
			tbxObservacion.setValue(remision_interna.getObservacion());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Remision_interna remision_interna = (Remision_interna) obj;
		try {
			int result = remision_internaService.eliminar(remision_interna);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminar satisfactoriamente !!",
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