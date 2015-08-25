/*
 * psiquiatriaAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Psiquiatria;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class PsiquiatriaAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static Logger log = Logger.getLogger(PsiquiatriaAction.class);

	// Componentes //
	// Manuel
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;

	private Admision admision;
	private Citas cita;
	private Opciones_via_ingreso opciones_via_ingreso;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Listbox lbxTipo_historia;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Groupbox groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Toolbarbutton btnCancelar;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	private String tipo_historia;
	private Long codigo_historia_anterior;

	@View
	private Listbox lbxAtendida;

	@View
	private Bandbox bandboxPrestador;
	@View
	private Textbox tbxNomPrestador;

	@View
	private Radiogroup rdbDesplazamiento;
	@View
	private Radiogroup rdbDiscapacidad;
	@View
	private Textbox tbxMotivo;
	@View
	private Textbox tbxEnfermedad_actual;
	@View
	private Textbox tbxArea_personal;
	@View
	private Textbox tbxArea_familiar;
	@View
	private Textbox tbxExamen_mental;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	@View
	private Textbox tbxTratamiento;

	// Control
	@View
	private Textbox tbxEvolucion;
	@View
	private Textbox tbxDiagnostico;

	private final String[] idsExcluyentes = { "tbxAccion",
			"btnLimpiar_prestador", "tbxValue", "lbxParameter",
			"infoPacientes", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar",
			"lbxFisico_cabeza_cara_estado", "lbxFisico_ocular_estado",
			"lbxFisico_endocrinologo_estado", "lbxFisico_otorrino_estado",
			"lbxFisico_osteomuscular_estado",
			"lbxFisico_cardio_pulmonar_estado", "lbxFisico_examen_mama_estado",
			"lbxFisico_cuello_estado", "lbxFisico_vacular_estado",
			"lbxFisico_piel_fanera_estado", "lbxFisico_gastointestinal_estado",
			"lbxFisico_neurologico_estado", "lbxFisico_genitourinario_estado" };

	@View
	private Row rowMotivo;
	@View
	private Row rowAreas_ajustes;
	@View
	private Row rowPlan;
	@View
	private Row rowEvolucion;
	@View
	private Row rowDiagnostico;

	private boolean primeraVez;

	@View
	private Toolbarbutton btGuardar;

	// Signos Vitales
	@View
	private Doublebox dbxCardiaca;
	@View
	private Doublebox dbxRespiratoria;
	@View
	private Doublebox dbxPeso;
	@View
	private Doublebox dbxTalla;
	@View
	private Doublebox dbxPresion;
	@View
	private Doublebox dbxPresion2;
	@View
	private Doublebox dbxImc;

	// Modulo Farmacologico y Autorizaciones
	@View
	private Div divModuloFarmacologico;
	@View
	private Div divModuloOrdenamiento;

	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	// Examenes Fisicos
	@View
	private Textbox tbxFisico_estado_general;
	@View
	private Textbox tbxFisico_cabeza_cara;
	@View
	private Textbox tbxFisico_ocular;
	@View
	private Textbox tbxFisico_endocrinologo;
	@View
	private Textbox tbxFisico_otorrino;
	@View
	private Textbox tbxFisico_osteomuscular;
	@View
	private Textbox tbxFisico_cardio_pulmonar;
	@View
	private Row rowFisico_examen_mama;
	@View
	private Row rowFisico_examen_mama2;
	@View
	private Textbox tbxFisico_examen_mama;
	@View
	private Textbox tbxFisico_cuello;
	@View
	private Textbox tbxFisico_vacular;
	@View
	private Textbox tbxFisico_piel_fanera;
	@View
	private Textbox tbxFisico_gastointestinal;
	@View
	private Textbox tbxFisico_neurologico;
	@View
	private Textbox tbxFisico_genitourinario;

	@View
	private Listbox lbxFisico_cabeza_cara_estado;
	@View
	private Listbox lbxFisico_ocular_estado;
	@View
	private Listbox lbxFisico_endocrinologo_estado;
	@View
	private Listbox lbxFisico_otorrino_estado;
	@View
	private Listbox lbxFisico_osteomuscular_estado;
	@View
	private Listbox lbxFisico_cardio_pulmonar_estado;
	@View
	private Listbox lbxFisico_examen_mama_estado;
	@View
	private Listbox lbxFisico_cuello_estado;
	@View
	private Listbox lbxFisico_vacular_estado;
	@View
	private Listbox lbxFisico_piel_fanera_estado;
	@View
	private Listbox lbxFisico_gastointestinal_estado;
	@View
	private Listbox lbxFisico_neurologico_estado;
	@View
	private Listbox lbxFisico_genitourinario_estado;

	private String valida_admision;

	@View
	private Toolbarbutton btnImprimir;

	@Override
	public void init() {
		try {
			listarCombos();
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	/**
	 * Sobreescritura del metodo params(Map<String, Object> map) para obtener
	 * los parametros iniciales con los que trabajara la historia clinica
	 */
	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			cita =(Citas) map.get(IVias_ingreso.CITA_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.PSIQUIATRIA);
		}
	}

	public void listarCombos() {
		listarParameter();
		listarAtendida();
		Utilidades.listarElementoListbox(lbxFisico_cabeza_cara_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_ocular_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_endocrinologo_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_otorrino_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_osteomuscular_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_cardio_pulmonar_estado,
				false, getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_examen_mama_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_cuello_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_vacular_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_piel_fanera_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_gastointestinal_estado,
				false, getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_neurologico_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_genitourinario_estado,
				false, getServiceLocator());

	}

	public void listarAtendida() {
		lbxAtendida.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue(false);
		listitem.setLabel("Pendiente");
		lbxAtendida.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue(true);
		listitem.setLabel("Atendida");
		lbxAtendida.appendChild(listitem);

		if (lbxAtendida.getItemCount() > 0) {
			lbxAtendida.setSelectedIndex(0);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		tbxAccion.setValue(accion);

		if (!accion.equalsIgnoreCase("registrar")) {

			if (!accion.equalsIgnoreCase("mostrar")) {
				buscarDatos();

			}
			btGuardar.setVisible(false);
			btnImprimir.setVisible(false);

		} else {
			btGuardar.setVisible(true);
			limpiarDatos();
			FormularioUtil.cargarRadiogroupsDefecto(this);
			if (admision != null) {
				infoPacientes.setFecha_inicio(new Date());
				cargarInformacion_paciente();
				onMostrarModuloRemisiones();
			}
			valida_admision = null;
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(true);

		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		cargarPrestador();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		infoPacientes.validarInformacionPaciente();
		boolean valida = true;

		if (!primeraVez) {

			FormularioUtil.validarCamposObligatorios(dbxCardiaca,
					dbxRespiratoria, dbxPeso, dbxTalla, dbxPresion,
					dbxPresion2, dbxImc, tbxExamen_mental,
					tbxFisico_estado_general, tbxEvolucion, tbxDiagnostico);

		} else {

			FormularioUtil.validarCamposObligatorios(dbxCardiaca,
					dbxRespiratoria, dbxPeso, dbxTalla, dbxPresion,
					dbxPresion2, dbxImc, tbxExamen_mental,
					tbxFisico_estado_general, tbxMotivo, tbxEnfermedad_actual,
					tbxArea_personal, tbxArea_familiar, tbxTratamiento);
		}

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		if (valida) {
			valida = receta_ripsAction.validarFormExterno();

			if (valida)
				valida = orden_servicioAction.validarFormExterno();
			if (valida)
				valida = remisiones_externasAction.validarRemision();
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}

			if (parameter.equalsIgnoreCase("fecha_inicial")) {
				parameters.put("fecha_string", value);
			} else {
				parameters.put("parameter", parameter);
				parameters.put("value", "%" + value + "%");
			}

			if (lbxTipo_historia.getSelectedIndex() != 0) {
				parameters.put("tipo_historia", lbxTipo_historia
						.getSelectedItem().getValue());
			}

			parameters.put("limite_paginado","limit 25 offset 0");

			List<Psiquiatria> lista_datos = getServiceLocator()
					.getPsiquiatriaService().listar(parameters);
			rowsResultado.getChildren().clear();

			//log.info("2: " + lista_datos);

			for (Psiquiatria psiquiatria : lista_datos) {
				rowsResultado.appendChild(crearFilas(psiquiatria, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Psiquiatria psiquiatria = (Psiquiatria) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(psiquiatria.getCodigo_historia() + ""));
		fila.appendChild(new Label(psiquiatria.getIdentificacion() + ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(psiquiatria.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(psiquiatria.getTipo_historia().equals(
				IConstantes.TIPO_HISTORIA_PRIMERA_VEZ) ? "PRIMERA VEZ"
				: "CONTROL"));

		Hlayout hlayout = new Hlayout();

		Toolbarbutton btn_mostrar = new Toolbarbutton();
		btn_mostrar.setImage("/images/mostrar_info.png");
		btn_mostrar.setTooltiptext("Mostrar historia Clinica");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.setLabel("Mostrar");
		btn_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						groupboxConsulta.setVisible(false);
						groupboxEditar.setVisible(true);
						mostrarDatos(psiquiatria);
					}
				});
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);

		return fila;
	}

	public Psiquiatria getBean() {
		Psiquiatria psiquiatria = new Psiquiatria();
		psiquiatria.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));
		psiquiatria.setUltimo_update(new Timestamp((new Date()).getTime()));
		psiquiatria.setCodigo_empresa(empresa.getCodigo_empresa());
		psiquiatria.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		psiquiatria.setCodigo_historia(infoPacientes.getCodigo_historia());
		psiquiatria.setCodigo_prestador(bandboxPrestador.getValue());
		psiquiatria.setIdentificacion(admision != null ? admision
				.getNro_identificacion() : null);
		psiquiatria.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));
		psiquiatria.setNro_ingreso(admision.getNro_ingreso());
		psiquiatria.setCentro_atencion("110");
		psiquiatria.setDesplazamiento(rdbDesplazamiento.getSelectedItem()
				.getValue().toString());
		psiquiatria.setDiscapacidad(rdbDiscapacidad.getSelectedItem()
				.getValue().toString());
		psiquiatria.setMotivo(tbxMotivo.getValue());
		psiquiatria.setEnfermedad_actual(tbxEnfermedad_actual.getValue());
		psiquiatria.setArea_personal(tbxArea_personal.getValue());
		psiquiatria.setArea_familiar(tbxArea_familiar.getValue());
		psiquiatria.setExamen_mental(tbxExamen_mental.getValue());

		psiquiatria.setTratamiento(tbxTratamiento.getValue());
		psiquiatria.setEvolucion(tbxEvolucion.getValue());
		psiquiatria.setObservaciones("");
		psiquiatria.setDiagnostico(tbxDiagnostico.getValue());
		psiquiatria.setCodigo_historia_anterior(codigo_historia_anterior);
		psiquiatria.setTipo_historia(tipo_historia);
		psiquiatria.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		psiquiatria.setCreacion_user(usuarios.getCodigo().toString());
		psiquiatria.setDelete_date(null);
		psiquiatria.setUltimo_user(usuarios.getCodigo().toString());
		psiquiatria.setDelete_user(null);
		psiquiatria.setCardiaca((dbxCardiaca.getValue() != null ? dbxCardiaca
				.getValue() + "" : ""));
		psiquiatria
				.setRespiratoria((dbxRespiratoria.getValue() != null ? dbxRespiratoria
						.getValue() + ""
						: ""));
		psiquiatria.setPeso((dbxPeso.getValue() != null ? dbxPeso.getValue()
				+ "" : ""));
		psiquiatria.setTalla((dbxTalla.getValue() != null ? dbxTalla.getValue()
				+ "" : ""));
		psiquiatria.setPresion((dbxPresion.getValue() != null ? dbxPresion
				.getValue() + "" : ""));
		psiquiatria.setPresion2((dbxPresion2.getValue() != null ? dbxPresion2
				.getValue() + "" : ""));
		psiquiatria.setInd_masa((dbxImc.getValue() != null ? dbxImc.getValue()
				+ "" : ""));

		psiquiatria.setFisico_estado_general(tbxFisico_estado_general
				.getValue());
		psiquiatria.setFisico_cabeza_cara(tbxFisico_cabeza_cara.getValue());
		psiquiatria.setFisico_ocular(tbxFisico_ocular.getValue());
		psiquiatria.setFisico_endocrinologo(tbxFisico_endocrinologo.getValue());
		psiquiatria.setFisico_otorrino(tbxFisico_otorrino.getValue());
		psiquiatria.setFisico_osteomuscular(tbxFisico_osteomuscular.getValue());
		psiquiatria.setFisico_cardio_pulmonar(tbxFisico_cardio_pulmonar
				.getValue());
		psiquiatria.setFisico_examen_mama(tbxFisico_examen_mama.getValue());
		psiquiatria.setFisico_cuello(tbxFisico_cuello.getValue());
		psiquiatria.setFisico_vacular(tbxFisico_vacular.getValue());
		psiquiatria.setFisico_piel_fanera(tbxFisico_piel_fanera.getValue());
		psiquiatria.setFisico_gastointestinal(tbxFisico_gastointestinal
				.getValue());
		psiquiatria.setFisico_neurologico(tbxFisico_neurologico.getValue());
		psiquiatria.setFisico_genitourinario(tbxFisico_genitourinario
				.getValue());

		psiquiatria.setFisico_cabeza_cara_estado(lbxFisico_cabeza_cara_estado
				.getSelectedItem().getValue().toString());
		psiquiatria.setFisico_ocular_estado(lbxFisico_ocular_estado
				.getSelectedItem().getValue().toString());
		psiquiatria
				.setFisico_endocrinologo_estado(lbxFisico_endocrinologo_estado
						.getSelectedItem().getValue().toString());
		psiquiatria.setFisico_otorrino_estado(lbxFisico_otorrino_estado
				.getSelectedItem().getValue().toString());
		psiquiatria
				.setFisico_osteomuscular_estado(lbxFisico_osteomuscular_estado
						.getSelectedItem().getValue().toString());
		psiquiatria
				.setFisico_cardio_pulmonar_estado(lbxFisico_cardio_pulmonar_estado
						.getSelectedItem().getValue().toString());
		psiquiatria.setFisico_examen_mama_estado(lbxFisico_examen_mama_estado
				.getSelectedItem().getValue().toString());
		psiquiatria.setFisico_cuello_estado(lbxFisico_cuello_estado
				.getSelectedItem().getValue().toString());
		psiquiatria.setFisico_vacular_estado(lbxFisico_vacular_estado
				.getSelectedItem().getValue().toString());
		psiquiatria.setFisico_piel_fanera_estado(lbxFisico_piel_fanera_estado
				.getSelectedItem().getValue().toString());
		psiquiatria
				.setFisico_gastointestinal_estado(lbxFisico_gastointestinal_estado
						.getSelectedItem().getValue().toString());
		psiquiatria.setFisico_neurologico_estado(lbxFisico_neurologico_estado
				.getSelectedItem().getValue().toString());
		psiquiatria
				.setFisico_genitourinario_estado(lbxFisico_genitourinario_estado
						.getSelectedItem().getValue().toString());
		return psiquiatria;
	}

	// Metodo para guardar la informaciÃ³n //
	public void guardarDatos() throws Exception {
		try {

			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				Psiquiatria psiquiatria = getBean();


				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("historia_clinica", psiquiatria);				
				datos.put("admision", admision);
				datos.put("accion", tbxAccion.getValue());

				// hay que actualualizar los diagnosticos en la receta antes de
				// obtener el objeto receta
				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();

				receta_ripsAction.actualizarDiagnosticos(impresion_diagnostica);

				Map<String, Object> mapReceta = receta_ripsAction
						.obtenerDatos();
				Map<String, Object> mapProcedimientos = orden_servicioAction
						.obtenerDatos();
				datos.put("receta_medica", mapReceta);
				datos.put("orden_servicio", mapProcedimientos);
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());
				datos.put("cita_seleccionada", cita);
				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);	

				datos.put("impresion_diagnostica", impresion_diagnostica);
				Anexo9_entidad anexo9_entidad = remisiones_externasAction
						.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);

				psiquiatria = getServiceLocator().getPsiquiatriaService().guardar(datos);

				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad
							.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(
							false);
				}
				mostrarDatosAutorizacion(datos);
				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(psiquiatria
						.getCodigo_historia());
				Receta_rips receta_rips = (Receta_rips) mapReceta
						.get("receta_rips");
				if (receta_rips != null)
					receta_ripsAction.mostrarDatos(receta_rips, false);
				//hay que llamar este metodo para validar que salga el boton para imprimir despues de guardar la receta
				receta_ripsAction.validarParaImpresion();
				
				Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
						.get("orden_servicio");
				if (orden_servicio != null)
					orden_servicioAction.mostrarDatos(orden_servicio);
				//hay que llamar este metodo para validar que salga el boton para imprimir despues de guardar la orden
				orden_servicioAction.validarParaImpresion();

				actualizarAutorizaciones(admision,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision,
						getInformacionClinica(), this);

				
				
				MensajesUtil.mensajeInformacion("InformaciÃ³n ..",
						"Los datos se guardaron satisfactoriamente");
				
				btnImprimir.setVisible(true);

			}

		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Psiquiatria psiquiatria = (Psiquiatria) obj;
		try {
			infoPacientes.setCodigo_historia(psiquiatria.getCodigo_historia());
			infoPacientes.setFecha_inicio(psiquiatria.getFecha_inicial());
			infoPacientes.setFecha_cierre(true, psiquiatria.getUltimo_update());

			onMostrarModuloRemisiones();
			initMostrar_datos(psiquiatria);
			cargarInformacion_paciente();
			cargarRemisionInterna(psiquiatria);

			Utilidades.seleccionarRadio(rdbDesplazamiento,
					psiquiatria.getDesplazamiento());
			Utilidades.seleccionarRadio(rdbDiscapacidad,
					psiquiatria.getDiscapacidad());
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(psiquiatria.getCodigo_empresa());
			prestadores.setCodigo_sucursal(psiquiatria.getCodigo_sucursal());
			prestadores
					.setNro_identificacion(psiquiatria.getCodigo_prestador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);

			bandboxPrestador.setValue(psiquiatria.getCodigo_prestador());
			tbxNomPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));

			tbxMotivo.setValue(psiquiatria.getMotivo());
			tbxEnfermedad_actual.setValue(psiquiatria.getEnfermedad_actual());
			tbxArea_personal.setValue(psiquiatria.getArea_personal());
			tbxArea_familiar.setValue(psiquiatria.getArea_familiar());
			tbxExamen_mental.setValue(psiquiatria.getExamen_mental());

			cargarImpresionDiagnostica(psiquiatria);

			tbxTratamiento.setValue(psiquiatria.getTratamiento());

			tbxEvolucion.setValue(psiquiatria.getEvolucion());
			tbxDiagnostico.setValue(psiquiatria.getDiagnostico());

			dbxCardiaca
					.setValue((psiquiatria.getCardiaca() != null && !psiquiatria
							.getCardiaca().isEmpty()) ? Double
							.parseDouble(psiquiatria.getCardiaca()) : null);
			dbxRespiratoria
					.setValue((psiquiatria.getRespiratoria() != null && !psiquiatria
							.getRespiratoria().isEmpty()) ? Double
							.parseDouble(psiquiatria.getRespiratoria()) : null);
			dbxPeso.setValue((psiquiatria.getPeso() != null && !psiquiatria
					.getPeso().isEmpty()) ? Double.parseDouble(psiquiatria
					.getPeso()) : null);
			dbxTalla.setValue((psiquiatria.getTalla() != null && !psiquiatria
					.getTalla().isEmpty()) ? Double.parseDouble(psiquiatria
					.getTalla()) : null);
			dbxPresion
					.setValue((psiquiatria.getPresion() != null && !psiquiatria
							.getPresion().isEmpty()) ? Double
							.valueOf(psiquiatria.getPresion()) : null);
			dbxPresion2
					.setValue((psiquiatria.getPresion2() != null && !psiquiatria
							.getPresion2().isEmpty()) ? Double
							.valueOf(psiquiatria.getPresion2()) : null);
			dbxImc.setValue((psiquiatria.getInd_masa() != null && !psiquiatria
					.getInd_masa().isEmpty()) ? Double.parseDouble(psiquiatria
					.getInd_masa()) : null);

			tbxFisico_estado_general.setValue(psiquiatria
					.getFisico_estado_general());
			tbxFisico_cabeza_cara.setValue(psiquiatria.getFisico_cabeza_cara());
			tbxFisico_ocular.setValue(psiquiatria.getFisico_ocular());
			tbxFisico_endocrinologo.setValue(psiquiatria
					.getFisico_endocrinologo());
			tbxFisico_otorrino.setValue(psiquiatria.getFisico_otorrino());
			tbxFisico_osteomuscular.setValue(psiquiatria
					.getFisico_osteomuscular());
			tbxFisico_cardio_pulmonar.setValue(psiquiatria
					.getFisico_cardio_pulmonar());
			tbxFisico_examen_mama.setValue(psiquiatria.getFisico_examen_mama());
			tbxFisico_cuello.setValue(psiquiatria.getFisico_cuello());
			tbxFisico_vacular.setValue(psiquiatria.getFisico_vacular());
			tbxFisico_piel_fanera.setValue(psiquiatria.getFisico_piel_fanera());
			tbxFisico_gastointestinal.setValue(psiquiatria
					.getFisico_gastointestinal());
			tbxFisico_neurologico.setValue(psiquiatria.getFisico_neurologico());
			tbxFisico_genitourinario.setValue(psiquiatria
					.getFisico_genitourinario());

			Utilidades.seleccionarListitem(lbxFisico_cabeza_cara_estado,
					psiquiatria.getFisico_cabeza_cara_estado());
			Utilidades.seleccionarListitem(lbxFisico_ocular_estado,
					psiquiatria.getFisico_ocular_estado());
			Utilidades.seleccionarListitem(lbxFisico_endocrinologo_estado,
					psiquiatria.getFisico_endocrinologo_estado());
			Utilidades.seleccionarListitem(lbxFisico_otorrino_estado,
					psiquiatria.getFisico_otorrino_estado());
			Utilidades.seleccionarListitem(lbxFisico_osteomuscular_estado,
					psiquiatria.getFisico_osteomuscular_estado());
			Utilidades.seleccionarListitem(lbxFisico_cardio_pulmonar_estado,
					psiquiatria.getFisico_cardio_pulmonar_estado());
			Utilidades.seleccionarListitem(lbxFisico_examen_mama_estado,
					psiquiatria.getFisico_examen_mama_estado());
			Utilidades.seleccionarListitem(lbxFisico_cuello_estado,
					psiquiatria.getFisico_cuello_estado());
			Utilidades.seleccionarListitem(lbxFisico_vacular_estado,
					psiquiatria.getFisico_vacular_estado());
			Utilidades.seleccionarListitem(lbxFisico_piel_fanera_estado,
					psiquiatria.getFisico_piel_fanera_estado());
			Utilidades.seleccionarListitem(lbxFisico_gastointestinal_estado,
					psiquiatria.getFisico_gastointestinal_estado());
			Utilidades.seleccionarListitem(lbxFisico_neurologico_estado,
					psiquiatria.getFisico_neurologico_estado());
			Utilidades.seleccionarListitem(lbxFisico_genitourinario_estado,
					psiquiatria.getFisico_genitourinario_estado());

			lbxFisico_cabeza_cara_estado.setDisabled(true);
			lbxFisico_ocular_estado.setDisabled(true);
			lbxFisico_endocrinologo_estado.setDisabled(true);
			lbxFisico_otorrino_estado.setDisabled(true);
			lbxFisico_osteomuscular_estado.setDisabled(true);
			lbxFisico_cardio_pulmonar_estado.setDisabled(true);
			lbxFisico_examen_mama_estado.setDisabled(true);
			lbxFisico_cuello_estado.setDisabled(true);
			lbxFisico_vacular_estado.setDisabled(true);
			lbxFisico_piel_fanera_estado.setDisabled(true);
			lbxFisico_gastointestinal_estado.setDisabled(true);
			lbxFisico_neurologico_estado.setDisabled(true);
			lbxFisico_genitourinario_estado.setDisabled(true);

			valida_admision = psiquiatria.getNro_ingreso();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);

			// Mostramos la vista //
			// tbxAccion.setText("modificar");
			// accionForm(true, tbxAccion.getText());
			inicializarVista(tipo_historia);
			cargarAnexo9_remision(psiquiatria);
			btnImprimir.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Psiquiatria psiquiatria = (Psiquiatria) obj;
		try {
			int result = getServiceLocator().getPsiquiatriaService().eliminar(
					psiquiatria);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("InformaciÃ³n se eliminÃ³ satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void cargarPrestador() throws Exception {
		if (rol_usuario.equals("05")) {
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(empresa.getCodigo_empresa());
			prestadores.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			prestadores.setNro_identificacion(usuarios.getCodigo());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			if (prestadores == null) {
				throw new Exception(
						"Usuario no esta creado en el modulo de prestadore, actualize informaciÃ³n de usuario");
			}
			bandboxPrestador.setValue(prestadores.getNro_identificacion());
			tbxNomPrestador.setValue(prestadores.getNombres() + " "
					+ prestadores.getApellidos());
		} else {
			/*
			 * Admision admision = new Admision();
			 * admision.setCodigo_empresa(empresa.getCodigo_empresa());
			 * admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			 * admision.setNro_identificacion(tbxNro_identificacion.getValue());
			 * admision.setNro_ingreso(tbxNro_ingreso.getValue()); admision =
			 * getServiceLocator().getAdmisionService().consultar( admision);
			 */

			if (admision != null) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(admision.getCodigo_empresa());
				prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
				prestadores.setNro_identificacion(admision.getCodigo_medico());
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);

				bandboxPrestador.setValue((prestadores != null ? prestadores
						.getNro_identificacion() : "000000000"));
				tbxNomPrestador.setValue((prestadores != null ? prestadores
						.getNombres() + " " + prestadores.getApellidos()
						: "MEDICO POR DEFECTO"));
			} else {
				bandboxPrestador.setValue("000000000");
				tbxNomPrestador.setValue("MEDICO POR DEFECTO");
			}
		}
	}

	public void selectedPrestador(Listitem listitem) {
		if (listitem.getValue() == null) {
			bandboxPrestador.setValue("");
			tbxNomPrestador.setValue("");
		} else {
			Prestadores dato = (Prestadores) listitem.getValue();
			bandboxPrestador.setValue(dato.getNro_identificacion());
			tbxNomPrestador.setValue(dato.getNombres() + " "
					+ dato.getApellidos());
		}
		bandboxPrestador.close();
	}

	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Cie> list = getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class, 
					parameters);

			lbx.getItems().clear();

			for (Cie dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getSexo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_inferior()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_superior()));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void selectedDx(Listitem listitem, Bandbox bandbox, Textbox textbox) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
		} else {
			Cie dato = (Cie) listitem.getValue();
			bandbox.setValue(dato.getCodigo());
			textbox.setValue(dato.getNombre());
		}
		bandbox.close();
	}

	public void seleccion(Listbox listbox, int entero,
			AbstractComponent[] abstractComponents) {
		try {
			System.out.println("" + listbox.getSelectedItem().getValue());

			String num = entero + "";
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (listbox.getSelectedItem().getValue().equals(num)) {
					// textbox.setVisible(true);
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);
					}
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}
				}
			}
		} catch (Exception e) {
			//  block
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_radio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			System.out.println("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {
					abstractComponent.setVisible(true);
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());

					}
					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}

					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			//  block
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public void initHistoria() throws Exception {

		macroImpresion_diagnostica.inicializarMacro(this, admision,
				IVias_ingreso.PSIQUIATRIA);
		macroRemision_interna.inicializarMacro(this, admision,
				IVias_ingreso.PSIQUIATRIA);
		

		if (admision != null) {
			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Psiquiatria psiquiatria = new Psiquiatria();
				psiquiatria.setCodigo_empresa(empresa.getCodigo_empresa());
				psiquiatria.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				psiquiatria.setIdentificacion(admision.getNro_identificacion());
				psiquiatria.setNro_ingreso(admision.getNro_ingreso());

				//log.info("psicologia - initHistoria " + psiquiatria);

				psiquiatria = getServiceLocator().getPsiquiatriaService()
						.consultar_historia(psiquiatria);

				if (psiquiatria != null) {
					accionForm(true, "mostrar");
					mostrarDatos(psiquiatria);
					btnCancelar.setVisible(true);
					infoPacientes.setCodigo_historia(psiquiatria
							.getCodigo_historia());
				} else {
					groupboxEditar.setVisible(false);
					throw new Exception(
							"NO hay una historia clinica relacionada a este paciente admitido");
				}
			} else {
				if (opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
					accionForm(true, "registrar");
					btnCancelar.setVisible(false);
				} else {
					accionForm(false, "consultar");
					btnCancelar.setVisible(true);
				}
			}
		}
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowMotivo.setVisible(true);
			rowAreas_ajustes.setVisible(true);
			// rowExamen_mental.setVisible(true);
			rowPlan.setVisible(true);
			rowEvolucion.setVisible(false);
			rowDiagnostico.setVisible(false);
			primeraVez = true;
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			rowMotivo.setVisible(false);
			rowAreas_ajustes.setVisible(false);
			// rowExamen_mental.setVisible(false);
			rowPlan.setVisible(false);
			rowEvolucion.setVisible(true);
			rowDiagnostico.setVisible(true);
			primeraVez = false;
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {

		if (admision.getPaciente().getSexo().equals("M")) {
			rowFisico_examen_mama.setVisible(false);
			rowFisico_examen_mama2.setVisible(false);
		} else {
			rowFisico_examen_mama.setVisible(true);
			rowFisico_examen_mama2.setVisible(true);
		}

		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("identificacion",
									admision.getNro_identificacion());
							parametros.put("order_desc", "order_desc");

							List<Psiquiatria> listado_historias = getServiceLocator()
									.getPsiquiatriaService().listar(parametros);

							if (!listado_historias.isEmpty()) {
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								cargarInformacion_historia_anterior(listado_historias
										.get(0));
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia ClÃ­nica de Control/EvoluciÃ³n");
								admision.setPrimera_vez("N");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia ClÃ­nica por Primera Vez");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
								admision.setPrimera_vez("S");
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {

								Psiquiatria psico = new Psiquiatria();
								psico.setCodigo_empresa(empresa
										.getCodigo_empresa());
								psico.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								psico.setCodigo_historia(codigo_historia_anterior);

								psico = getServiceLocator()
										.getPsiquiatriaService().consultar(
												psico);

								if (psico != null) {
									cargarInformacion_historia_anterior(psico);
								}

							}
						}

					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

		/*
		 * Psiquiatria psiquiatria = (Psiquiatria) historia_anterior;
		 * tbxEvolucion.setValue(psiquiatria.getEvolucion());
		 * tbxObservacion.setValue(psiquiatria.getObservaciones());
		 * tbxDiagnostico.setValue(psiquiatria.getDiagnostico());
		 */
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Psiquiatria psiquiatria = (Psiquiatria) historia_clinica;

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar",
							"lbxFisico_cabeza_cara_estado",
							"lbxFisico_ocular_estado",
							"lbxFisico_endocrinologo_estado",
							"lbxFisico_otorrino_estado",
							"lbxFisico_osteomuscular_estado",
							"lbxFisico_cardio_pulmonar_estado",
							"lbxFisico_examen_mama_estado",
							"lbxFisico_cuello_estado",
							"lbxFisico_vacular_estado",
							"lbxFisico_piel_fanera_estado",
							"lbxFisico_gastointestinal_estado",
							"lbxFisico_neurologico_estado",
							"lbxFisico_genitourinario_estado" });

			if (psiquiatria.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia ClÃ­nica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia ClÃ­nica de Control/EvoluciÃ³n");
			}
		} else {

			if (psiquiatria.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia ClÃ­nica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia ClÃ­nica de Control/EvoluciÃ³n");
			}
		}

		codigo_historia_anterior = psiquiatria.getCodigo_historia_anterior();
		tipo_historia = psiquiatria.getTipo_historia();

	}

	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
					UtilidadSignosVitales.TALLA_CENTIMETROS,
					UtilidadSignosVitales.PESO_KILOS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularFrecuenciaCardiaca(Doublebox dbxFc) {
		try {
			Date fecha_nacimiento = admision.getPaciente()
					.getFecha_nacimiento();
			String genero = admision.getPaciente().getSexo();

			UtilidadSignosVitales.onMostrarAlertaFrecuenciaCardiaca(dbxFc,
					fecha_nacimiento, genero);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularFrecuenciaRespiratoria(Doublebox dbxFr) {
		try {
			Date fecha_nacimiento = admision.getPaciente()
					.getFecha_nacimiento();
			String genero = admision.getPaciente().getSexo();

			UtilidadSignosVitales.onMostrarAlertaFrecuenciaRespiratoria(dbxFr,
					fecha_nacimiento, genero);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
		try {
			UtilidadSignosVitales.onCalcularTension(TA_sistolica,
					TA_diastolica);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void cargarImpresionDiagnostica(Psiquiatria psiquiatria)
			throws Exception {

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();

		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(psiquiatria
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);

		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);

	}

	public void onMostrarModuloFarmacologico() throws Exception {
		if (receta_ripsAction != null)
			receta_ripsAction.detach();
		//log.info("creando la ventana receta_ripsAction");

		Admision adm = new Admision();

		if (valida_admision != null) {
			adm.setCodigo_empresa(empresa.getCodigo_empresa());
			adm.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			adm.setNro_identificacion(admision.getNro_identificacion());
			adm.setNro_ingreso(valida_admision);
			adm = getServiceLocator().getAdmisionService().consultar(adm);
			//log.info("VALIDA" + valida_admision + ">>>>>>>>>>>");
			//log.info("1");

		} else {
			adm = admision;
			//log.info("2");

		}
		//log.info("admision" + adm);

		Map parametros = new HashMap();
		parametros.put("nro_identificacion", adm.getNro_identificacion());
		parametros.put("nro_ingreso", adm.getNro_ingreso());
		parametros.put("estado", adm.getEstado());
		parametros.put("codigo_administradora", adm.getCodigo_administradora());
		parametros.put("id_plan", adm.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", adm);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");

		parametros.put("ocultarRecomendaciones", "_Ocultar");

		receta_ripsAction = (Receta_rips_internoAction) Executions
				.createComponents("/pages/receta_rips_interno.zul", null,
						parametros);
		receta_ripsAction.inicializar(this);
		divModuloFarmacologico.appendChild(receta_ripsAction);

	}

	public void onMostrarModuloOrdenamiento() throws Exception {
		if (orden_servicioAction != null)
			orden_servicioAction.detach();
		// if (!cargo_farmacologico) {
		//log.info("creando la ventana receta_ripsAction");

		Admision adm = new Admision();

		if (valida_admision != null) {
			adm.setCodigo_empresa(empresa.getCodigo_empresa());
			adm.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			adm.setNro_identificacion(admision.getNro_identificacion());
			adm.setNro_ingreso(valida_admision);
			adm = getServiceLocator().getAdmisionService().consultar(adm);

		} else {
			adm = admision;
		}
		//log.info("admision" + adm);

		Map parametros = new HashMap();
		parametros.put("nro_identificacion", adm.getNro_identificacion());
		parametros.put("nro_ingreso", adm.getNro_ingreso());
		parametros.put("estado", adm.getEstado());
		parametros.put("codigo_administradora", adm.getCodigo_administradora());
		parametros.put("id_plan", adm.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", adm);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");
		orden_servicioAction = (Orden_servicio_internoAction) Executions
				.createComponents("/pages/orden_servicio_interno.zul", null,
						parametros);
		orden_servicioAction.inicializar(this);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
		// cargo_farmacologico = true;
		// }

	}

	private void cargarAnexo9_remision(Psiquiatria psiquiatria) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(psiquiatria.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
	}

	private void cargarRemisionInterna(Psiquiatria psiquiatria)
			throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(psiquiatria.getCodigo_historia());
		remision_interna.setCodigo_empresa(psiquiatria.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(psiquiatria.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		//log.info("remision_interna" + remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null)
			remisiones_externasAction.detach();
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", admision.getNro_ingreso());
		parametros.put("estado", admision.getEstado());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());
		parametros.put("id_plan", admision.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");

		parametros.put("ocultarRecomendaciones", "_Ocultar");

		remisiones_externasAction = (Remisiones_externasAction) Executions
				.createComponents("/pages/remisiones_externas.zul", null,
						parametros);
		remisiones_externasAction.inicializar(this);
		divModuloRemisiones_externas.appendChild(remisiones_externasAction);

	}
	
	@Override
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("SALUD MENTAL - PSIQUIATRIA");

		return serivicio1.toString();
	}
	
	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t").append(tbxMotivo.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		if (tbxEnfermedad_actual.getValue() != null
				&& !tbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxEnfermedad_actual.getValue()).append("\n");
		}

		// faltan los signos vitales para hacer el resumen

		informacion_clinica.append("\n");

		informacion_clinica.append("- DIAGNOSTICOS").append("\n");
		Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
				.obtenerImpresionDiagnostica();
		Cie cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_principal());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica
				.append("\tDiagnostico principal: ")
				.append(impresion_diagnostica.getCie_principal())
				.append(" ")
				.append(cie != null ? cie.getNombre() : "")
				.append("\t")
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_principal(),
						"tipo_diagnostico", this)).append("\n");

		/* relacionado 1 */
		if (!impresion_diagnostica.getCie_relacionado1().isEmpty()) {
			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado1());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			informacion_clinica.append("\n");

			informacion_clinica
					.append("\tRelacionado 1: ")
					.append(impresion_diagnostica.getCie_relacionado1())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado1(),
							"tipo_diagnostico", this)).append("\n");
		}
		if (!impresion_diagnostica.getCie_relacionado2().isEmpty()) {

			/* relacionado 2 */
			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado2());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			informacion_clinica.append("\n");

			informacion_clinica
					.append("\tRelacionado 2: ")
					.append(impresion_diagnostica.getCie_relacionado2())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado2(),
							"tipo_diagnostico", this)).append("\n");

		}
		if (!impresion_diagnostica.getCie_relacionado3().isEmpty()) {

			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado3());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			informacion_clinica.append("\n");

			informacion_clinica
					.append("\tRelacionado 3: ")
					.append(impresion_diagnostica.getCie_relacionado3())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado3(),
							"tipo_diagnostico", this)).append("\n");
		}

		return informacion_clinica.toString();
	}
	
	public void cargarSignosVitalesEnfermera(){
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setCodigo_empresa(codigo_empresa);
		enfermera_signos.setCodigo_sucursal(codigo_sucursal);
		enfermera_signos.setNro_ingreso(admision.getNro_ingreso());
		enfermera_signos.setNro_identificacion(admision.getNro_identificacion()	);
		enfermera_signos = getServiceLocator().getServicio(Enfermera_signosService.class).consultar(enfermera_signos);
		if(enfermera_signos != null){
			
			dbxCardiaca.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxRespiratoria.setValue(enfermera_signos.getFrecuencia_respiratoria());
			dbxPeso.setValue(enfermera_signos.getPeso());
			dbxTalla.setValue(enfermera_signos.getTalla());
			dbxPresion.setValue(enfermera_signos.getTa_sistolica());
			dbxPresion2.setValue(enfermera_signos.getTa_diastolica());
			dbxImc.setValue(enfermera_signos.getImc());
			
			calcularTA(dbxPresion,dbxPresion2);
			calcularFrecuenciaCardiaca(dbxCardiaca);
			calcularFrecuenciaRespiratoria(dbxRespiratoria);
			calcularIMC(dbxPeso,dbxTalla,dbxImc);
			
		}
	}

	public void imprimir() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		Long nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia != null) {
			String parametros_pagina="?codigo_empresa="+codigo_empresa;
			   parametros_pagina+="&codigo_sucursal="+codigo_sucursal;
			   parametros_pagina+="&codigo_historia="+nro_historia;
			   parametros_pagina+="&nro_ingreso="+admision.getNro_ingreso();
			   parametros_pagina+="&nro_identificacion="+admision.getNro_identificacion();
			Clients.evalJavaScript("window.open(\""+request.getContextPath()+"/pages/reports/psiquiatria_reporte.zul"+parametros_pagina+"\", '_blank');");
		}
	}

}