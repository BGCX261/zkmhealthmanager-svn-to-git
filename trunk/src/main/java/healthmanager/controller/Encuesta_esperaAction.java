/*
 * encuesta_esperaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Encuesta_espera;
import healthmanager.modelo.service.Encuesta_esperaService;

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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Encuesta_esperaAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Encuesta_esperaAction.class);

	private Encuesta_esperaService encuesta_esperaService;

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
	private Datebox dtbxFecha_inicial;
	@View
	private Textbox tbxCodigo_encuesta;
	@View
	private Textbox tbxVersion;
	@View
	private Textbox tbxServisio;
	@View
	private Textbox tbxRegime;
	@View
	private Radiogroup rdbVigilante1_1;
	@View
	private Radiogroup rdbFacturado1_2;
	@View
	private Radiogroup rdbMedico1_3;
	@View
	private Radiogroup rdbEnfermera1_4;
	@View
	private Radiogroup rdbOdontologo1_5;
	@View
	private Radiogroup rdbAux_enfermera1_6;
	@View
	private Radiogroup rdbAux_toma_muesra1_7;
	@View
	private Radiogroup rdbAux_rayos_x1_8;
	@View
	private Radiogroup rdbTiemp_tras_dia_solicitud2_1;
	@View
	private Radiogroup rdbTiemp_esp_asig_cita3_1;
	@View
	private Radiogroup rdbExplicaron_mot_demora3_2;
	@View
	private Radiogroup rdbVigi_ubi_ser4_1;
	@View
	private Radiogroup rdbFuncionario_tramite_ingreso4_2;
	@View
	private Radiogroup rdbProfesional_salud4_3;
	@View
	private Radiogroup rdbMecanismo_pres_queja4_4;
	@View
	private Radiogroup rdbDeberes_derecho4_5;
	@View
	private Radiogroup rdbOtros_aspectos4_6;
	@View
	private Radiogroup rdbArea_cita5_1;
	@View
	private Radiogroup rdbSala_espera5_2;
	@View
	private Radiogroup rdbConsultorios5_3;
	@View
	private Radiogroup rdbSala_observacion5_4;
	@View
	private Radiogroup rdbBanios5_5;
	@View
	private Radiogroup rdbRecomendacion5_6;
	@View
	private Textbox tbxPorque5_7;
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
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha_inicial");
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

			encuesta_esperaService.setLimit("limit 25 offset 0");

			List<Encuesta_espera> lista_datos = encuesta_esperaService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Encuesta_espera encuesta_espera : lista_datos) {
				rowsResultado.appendChild(crearFilas(encuesta_espera, this));
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

		final Encuesta_espera encuesta_espera = (Encuesta_espera) objeto;

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
				mostrarDatos(encuesta_espera);
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
									eliminarDatos(encuesta_espera);
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

				Encuesta_espera encuesta_espera = new Encuesta_espera();
				encuesta_espera.setCodigo_empresa(empresa.getCodigo_empresa());
				encuesta_espera.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				encuesta_espera.setIdentificacion("1007889718");
				encuesta_espera.setCodigo_encuesta(tbxCodigo_encuesta
						.getValue());
				encuesta_espera.setFecha_inicial(new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				encuesta_espera.setVersion(tbxVersion.getValue());
				encuesta_espera.setServisio(tbxServisio.getValue());
				encuesta_espera.setRegime(tbxRegime.getValue());
				encuesta_espera.setVigilante1_1(rdbVigilante1_1
						.getSelectedItem().getValue().toString());
				encuesta_espera.setFacturado1_2(rdbFacturado1_2
						.getSelectedItem().getValue().toString());
				encuesta_espera.setMedico1_3(rdbMedico1_3.getSelectedItem()
						.getValue().toString());
				encuesta_espera.setEnfermera1_4(rdbEnfermera1_4
						.getSelectedItem().getValue().toString());
				encuesta_espera.setOdontologo1_5(rdbOdontologo1_5
						.getSelectedItem().getValue().toString());
				encuesta_espera.setAux_enfermera1_6(rdbAux_enfermera1_6
						.getSelectedItem().getValue().toString());
				encuesta_espera.setAux_toma_muesra1_7(rdbAux_toma_muesra1_7
						.getSelectedItem().getValue().toString());
				encuesta_espera.setAux_rayos_x1_8(rdbAux_rayos_x1_8
						.getSelectedItem().getValue().toString());
				encuesta_espera
						.setTiemp_tras_dia_solicitud2_1(rdbTiemp_tras_dia_solicitud2_1
								.getSelectedItem().getValue().toString());
				encuesta_espera
						.setTiemp_esp_asig_cita3_1(rdbTiemp_esp_asig_cita3_1
								.getSelectedItem().getValue().toString());
				encuesta_espera
						.setExplicaron_mot_demora3_2(rdbExplicaron_mot_demora3_2
								.getSelectedItem().getValue().toString());
				encuesta_espera.setVigi_ubi_ser4_1(rdbVigi_ubi_ser4_1
						.getSelectedItem().getValue().toString());
				encuesta_espera
						.setFuncionario_tramite_ingreso4_2(rdbFuncionario_tramite_ingreso4_2
								.getSelectedItem().getValue().toString());
				encuesta_espera.setProfesional_salud4_3(rdbProfesional_salud4_3
						.getSelectedItem().getValue().toString());
				encuesta_espera
						.setMecanismo_pres_queja4_4(rdbMecanismo_pres_queja4_4
								.getSelectedItem().getValue().toString());
				encuesta_espera.setDeberes_derecho4_5(rdbDeberes_derecho4_5
						.getSelectedItem().getValue().toString());
				encuesta_espera.setOtros_aspectos4_6(rdbOtros_aspectos4_6
						.getSelectedItem().getValue().toString());
				encuesta_espera.setArea_cita5_1(rdbArea_cita5_1
						.getSelectedItem().getValue().toString());
				encuesta_espera.setSala_espera5_2(rdbSala_espera5_2
						.getSelectedItem().getValue().toString());
				encuesta_espera.setConsultorios5_3(rdbConsultorios5_3
						.getSelectedItem().getValue().toString());
				encuesta_espera.setSala_observacion5_4(rdbSala_observacion5_4
						.getSelectedItem().getValue().toString());
				encuesta_espera.setBanios5_5(rdbBanios5_5.getSelectedItem()
						.getValue().toString());
				encuesta_espera.setRecomendacion5_6(rdbRecomendacion5_6
						.getSelectedItem().getValue().toString());
				encuesta_espera.setPorque5_7(tbxPorque5_7.getValue());
				encuesta_espera.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				encuesta_espera.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				encuesta_espera.setCreacion_user(usuarios.getCodigo()
						.toString());
				encuesta_espera.setDelete_date(null);
				encuesta_espera.setUltimo_user(usuarios.getCodigo().toString());
				encuesta_espera.setDelete_user(null);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					encuesta_esperaService.crear(encuesta_espera);
					accionForm(true, "registrar");
				} else {
					int result = encuesta_esperaService
							.actualizar(encuesta_espera);
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
		Encuesta_espera encuesta_espera = (Encuesta_espera) obj;
		try {
			dtbxFecha_inicial.setValue(encuesta_espera.getFecha_inicial());
			tbxCodigo_encuesta.setValue(encuesta_espera.getCodigo_encuesta());
			tbxVersion.setValue(encuesta_espera.getVersion());
			tbxServisio.setValue(encuesta_espera.getServisio());
			tbxRegime.setValue(encuesta_espera.getRegime());
			Utilidades.seleccionarRadio(rdbVigilante1_1,
					encuesta_espera.getVigilante1_1());
			Utilidades.seleccionarRadio(rdbFacturado1_2,
					encuesta_espera.getFacturado1_2());
			Utilidades.seleccionarRadio(rdbMedico1_3,
					encuesta_espera.getMedico1_3());
			Utilidades.seleccionarRadio(rdbEnfermera1_4,
					encuesta_espera.getEnfermera1_4());
			Utilidades.seleccionarRadio(rdbOdontologo1_5,
					encuesta_espera.getOdontologo1_5());
			Utilidades.seleccionarRadio(rdbAux_enfermera1_6,
					encuesta_espera.getAux_enfermera1_6());
			Utilidades.seleccionarRadio(rdbAux_toma_muesra1_7,
					encuesta_espera.getAux_toma_muesra1_7());
			Utilidades.seleccionarRadio(rdbAux_rayos_x1_8,
					encuesta_espera.getAux_rayos_x1_8());
			Utilidades.seleccionarRadio(rdbTiemp_tras_dia_solicitud2_1,
					encuesta_espera.getTiemp_tras_dia_solicitud2_1());
			Utilidades.seleccionarRadio(rdbTiemp_esp_asig_cita3_1,
					encuesta_espera.getTiemp_esp_asig_cita3_1());
			Utilidades.seleccionarRadio(rdbExplicaron_mot_demora3_2,
					encuesta_espera.getExplicaron_mot_demora3_2());
			Utilidades.seleccionarRadio(rdbVigi_ubi_ser4_1,
					encuesta_espera.getVigi_ubi_ser4_1());
			Utilidades.seleccionarRadio(rdbFuncionario_tramite_ingreso4_2,
					encuesta_espera.getFuncionario_tramite_ingreso4_2());
			Utilidades.seleccionarRadio(rdbProfesional_salud4_3,
					encuesta_espera.getProfesional_salud4_3());
			Utilidades.seleccionarRadio(rdbMecanismo_pres_queja4_4,
					encuesta_espera.getMecanismo_pres_queja4_4());
			Utilidades.seleccionarRadio(rdbDeberes_derecho4_5,
					encuesta_espera.getDeberes_derecho4_5());
			Utilidades.seleccionarRadio(rdbOtros_aspectos4_6,
					encuesta_espera.getOtros_aspectos4_6());
			Utilidades.seleccionarRadio(rdbArea_cita5_1,
					encuesta_espera.getArea_cita5_1());
			Utilidades.seleccionarRadio(rdbSala_espera5_2,
					encuesta_espera.getSala_espera5_2());
			Utilidades.seleccionarRadio(rdbConsultorios5_3,
					encuesta_espera.getConsultorios5_3());
			Utilidades.seleccionarRadio(rdbSala_observacion5_4,
					encuesta_espera.getSala_observacion5_4());
			Utilidades.seleccionarRadio(rdbBanios5_5,
					encuesta_espera.getBanios5_5());
			Utilidades.seleccionarRadio(rdbRecomendacion5_6,
					encuesta_espera.getRecomendacion5_6());
			tbxPorque5_7.setValue(encuesta_espera.getPorque5_7());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Encuesta_espera encuesta_espera = (Encuesta_espera) obj;
		try {
			int result = encuesta_esperaService.eliminar(encuesta_espera);
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