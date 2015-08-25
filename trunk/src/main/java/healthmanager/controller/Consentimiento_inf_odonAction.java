/*
 * consentimineto_inf_odonAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Consentimiento_inf_odon;
import healthmanager.modelo.service.Consentimiento_inf_odonService;

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

public class Consentimiento_inf_odonAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Consentimiento_inf_odonAction.class);

	private Consentimiento_inf_odonService consentimineto_inf_odonService;

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
	private Checkbox chbOpe_dent_tej_blando;
	@View
	private Checkbox chbOpe_dent_lux_atm;
	@View
	private Checkbox chbOpe_dent_exp_pul;
	@View
	private Checkbox chbOpe_dent_pul_rev;
	@View
	private Checkbox chbOpe_dent_ulc_trau;
	@View
	private Checkbox chbPer_tej_blando;
	@View
	private Checkbox chbEndodoncia_frac_inst;
	@View
	private Checkbox chbEndodoncia_periodontitis;
	@View
	private Checkbox chbEndodoncia_perforaciones;
	@View
	private Checkbox chbEndodoncia_sobre;
	@View
	private Checkbox chbEndodoncia_tej_blando;
	@View
	private Checkbox chbEndodoncia_inj_hipoclorito;
	@View
	private Checkbox chbEndodoncia_pigmentacion;
	@View
	private Checkbox chbEndodoncia_enfisema;
	@View
	private Checkbox chbCirugia_oral_fractura;
	@View
	private Checkbox chbCirugia_oral_hematoma;
	@View
	private Checkbox chbCirugia_oral_hemorragia;
	@View
	private Checkbox chbCirugia_oral_alveolitis;
	@View
	private Checkbox chbCirugia_oral_tej_blando;
	@View
	private Checkbox chbCirugia_oral_luxacion;
	@View
	private Checkbox chbCirugia_oral_alergia;
	@View
	private Checkbox chbProc_anes_parestecia;
	@View
	private Checkbox chbProc_anes_trismos;
	@View
	private Checkbox chbProc_anes_lac;
	@View
	private Checkbox chbProc_anes_hem_anafi;
	@View
	private Checkbox chbProc_anes_chock;
	@View
	private Checkbox chbProc_anes_reac_aler;
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
		listitem.setValue("fecha_inicio");
		listitem.setLabel("Fecha_inicio");
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

			consentimineto_inf_odonService.setLimit("limit 25 offset 0");

			List<Consentimiento_inf_odon> lista_datos = consentimineto_inf_odonService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Consentimiento_inf_odon consentimineto_inf_odon : lista_datos) {
				rowsResultado.appendChild(crearFilas(consentimineto_inf_odon,
						this));
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

		final Consentimiento_inf_odon consentimineto_inf_odon = (Consentimiento_inf_odon) objeto;

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
				mostrarDatos(consentimineto_inf_odon);
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
									eliminarDatos(consentimineto_inf_odon);
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

				Consentimiento_inf_odon consentimineto_inf_odon = new Consentimiento_inf_odon();
				consentimineto_inf_odon.setCodigo_empresa(empresa
						.getCodigo_empresa());
				consentimineto_inf_odon.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				consentimineto_inf_odon.setCodigo_historia(-1L);
				consentimineto_inf_odon.setFecha_inicio(null);
				consentimineto_inf_odon.setIdentificacion("");
				consentimineto_inf_odon
						.setOpe_dent_tej_blando(chbOpe_dent_tej_blando
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon.setOpe_dent_lux_atm(chbOpe_dent_lux_atm
						.isChecked() ? "S" : "N");
				consentimineto_inf_odon.setOpe_dent_exp_pul(chbOpe_dent_exp_pul
						.isChecked() ? "S" : "N");
				consentimineto_inf_odon.setOpe_dent_pul_rev(chbOpe_dent_pul_rev
						.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setOpe_dent_ulc_trau(chbOpe_dent_ulc_trau.isChecked() ? "S"
								: "N");
				consentimineto_inf_odon.setPer_tej_blando(chbPer_tej_blando
						.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setEndodoncia_frac_inst(chbEndodoncia_frac_inst
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setEndodoncia_periodontitis(chbEndodoncia_periodontitis
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setEndodoncia_perforaciones(chbEndodoncia_perforaciones
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon.setEndodoncia_sobre(chbEndodoncia_sobre
						.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setEndodoncia_tej_blando(chbEndodoncia_tej_blando
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setEndodoncia_inj_hipoclorito(chbEndodoncia_inj_hipoclorito
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setEndodoncia_pigmentacion(chbEndodoncia_pigmentacion
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setEndodoncia_enfisema(chbEndodoncia_enfisema
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setCirugia_oral_fractura(chbCirugia_oral_fractura
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setCirugia_oral_hematoma(chbCirugia_oral_hematoma
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setCirugia_oral_hemorragia(chbCirugia_oral_hemorragia
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setCirugia_oral_alveolitis(chbCirugia_oral_alveolitis
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setCirugia_oral_tej_blando(chbCirugia_oral_tej_blando
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setCirugia_oral_luxacion(chbCirugia_oral_luxacion
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setCirugia_oral_alergia(chbCirugia_oral_alergia
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setProc_anes_parestecia(chbProc_anes_parestecia
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setProc_anes_trismos(chbProc_anes_trismos.isChecked() ? "S"
								: "N");
				consentimineto_inf_odon.setProc_anes_lac(chbProc_anes_lac
						.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setProc_anes_hem_anafi(chbProc_anes_hem_anafi
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon.setProc_anes_chock(chbProc_anes_chock
						.isChecked() ? "S" : "N");
				consentimineto_inf_odon
						.setProc_anes_reac_aler(chbProc_anes_reac_aler
								.isChecked() ? "S" : "N");
				consentimineto_inf_odon.setCodigo_medico("");

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					consentimineto_inf_odonService
							.crear(consentimineto_inf_odon);
					accionForm(true, "registrar");
				} else {
					int result = consentimineto_inf_odonService
							.actualizar(consentimineto_inf_odon);
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
		Consentimiento_inf_odon consentimineto_inf_odon = (Consentimiento_inf_odon) obj;
		try {
			chbOpe_dent_tej_blando.setChecked(consentimineto_inf_odon
					.getOpe_dent_tej_blando().equals("S") ? true : false);
			chbOpe_dent_lux_atm.setChecked(consentimineto_inf_odon
					.getOpe_dent_lux_atm().equals("S") ? true : false);
			chbOpe_dent_exp_pul.setChecked(consentimineto_inf_odon
					.getOpe_dent_exp_pul().equals("S") ? true : false);
			chbOpe_dent_pul_rev.setChecked(consentimineto_inf_odon
					.getOpe_dent_pul_rev().equals("S") ? true : false);
			chbOpe_dent_ulc_trau.setChecked(consentimineto_inf_odon
					.getOpe_dent_ulc_trau().equals("S") ? true : false);
			chbPer_tej_blando.setChecked(consentimineto_inf_odon
					.getPer_tej_blando().equals("S") ? true : false);
			chbEndodoncia_frac_inst.setChecked(consentimineto_inf_odon
					.getEndodoncia_frac_inst().equals("S") ? true : false);
			chbEndodoncia_periodontitis.setChecked(consentimineto_inf_odon
					.getEndodoncia_periodontitis().equals("S") ? true : false);
			chbEndodoncia_perforaciones.setChecked(consentimineto_inf_odon
					.getEndodoncia_perforaciones().equals("S") ? true : false);
			chbEndodoncia_sobre.setChecked(consentimineto_inf_odon
					.getEndodoncia_sobre().equals("S") ? true : false);
			chbEndodoncia_tej_blando.setChecked(consentimineto_inf_odon
					.getEndodoncia_tej_blando().equals("S") ? true : false);
			chbEndodoncia_inj_hipoclorito
					.setChecked(consentimineto_inf_odon
							.getEndodoncia_inj_hipoclorito().equals("S") ? true
							: false);
			chbEndodoncia_pigmentacion.setChecked(consentimineto_inf_odon
					.getEndodoncia_pigmentacion().equals("S") ? true : false);
			chbEndodoncia_enfisema.setChecked(consentimineto_inf_odon
					.getEndodoncia_enfisema().equals("S") ? true : false);
			chbCirugia_oral_fractura.setChecked(consentimineto_inf_odon
					.getCirugia_oral_fractura().equals("S") ? true : false);
			chbCirugia_oral_hematoma.setChecked(consentimineto_inf_odon
					.getCirugia_oral_hematoma().equals("S") ? true : false);
			chbCirugia_oral_hemorragia.setChecked(consentimineto_inf_odon
					.getCirugia_oral_hemorragia().equals("S") ? true : false);
			chbCirugia_oral_alveolitis.setChecked(consentimineto_inf_odon
					.getCirugia_oral_alveolitis().equals("S") ? true : false);
			chbCirugia_oral_tej_blando.setChecked(consentimineto_inf_odon
					.getCirugia_oral_tej_blando().equals("S") ? true : false);
			chbCirugia_oral_luxacion.setChecked(consentimineto_inf_odon
					.getCirugia_oral_luxacion().equals("S") ? true : false);
			chbCirugia_oral_alergia.setChecked(consentimineto_inf_odon
					.getCirugia_oral_alergia().equals("S") ? true : false);
			chbProc_anes_parestecia.setChecked(consentimineto_inf_odon
					.getProc_anes_parestecia().equals("S") ? true : false);
			chbProc_anes_trismos.setChecked(consentimineto_inf_odon
					.getProc_anes_trismos().equals("S") ? true : false);
			chbProc_anes_lac.setChecked(consentimineto_inf_odon
					.getProc_anes_lac().equals("S") ? true : false);
			chbProc_anes_hem_anafi.setChecked(consentimineto_inf_odon
					.getProc_anes_hem_anafi().equals("S") ? true : false);
			chbProc_anes_chock.setChecked(consentimineto_inf_odon
					.getProc_anes_chock().equals("S") ? true : false);
			chbProc_anes_reac_aler.setChecked(consentimineto_inf_odon
					.getProc_anes_reac_aler().equals("S") ? true : false);

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Consentimiento_inf_odon consentimineto_inf_odon = (Consentimiento_inf_odon) obj;
		try {
			int result = consentimineto_inf_odonService
					.eliminar(consentimineto_inf_odon);
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