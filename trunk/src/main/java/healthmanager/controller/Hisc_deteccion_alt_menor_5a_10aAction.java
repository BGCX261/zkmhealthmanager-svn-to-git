/*
 * hisc_deteccion_alt_menor_5a_10aAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Hisc_deteccion_alt_menor_5a_10a;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.bean.Test_puntuacion_figura_humana;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import healthmanager.modelo.service.Test_puntuacion_figura_humanaService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IHistorias_clinicas;
import com.framework.constantes.ITipos_coordenada;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.FiguraHumanaMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.CalculadorUtil;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_deteccion_alt_menor_5a_10aAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Hisc_deteccion_alt_menor_5a_10aAction.class);

	// Componentes //
	// Manuel
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;
	@View
	private Listbox lbxParameter;
	@View
	private Listbox lbxTipoHistoria;
	@View
	private Radiogroup rdbHepatitis_a;
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
	private Textbox tbxNombre_del_acompanante;
	@View
	private Listbox lbxParentesco;
	@View
	private Textbox tbxNombre_del_padre;
	@View
	private Intbox ibxEdad_padre;
	@View
	private Textbox tbxNombre_de_la_madre;
	@View
	private Intbox ibxEdad_madre;
	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Textbox tbxGestion_actual;
	@View
	private Intbox ibxNum_embarazos_g;
	@View
	private Intbox ibxNum_partos_p;
	@View
	private Intbox ibxNum_abortos_a;
	@View
	private Intbox ibxNum_cesarias_c;
	@View
	private Radiogroup rdbRiesgo;
	@View
	private Radiogroup rdbControl_prenatal;
	@View
	private Listbox lbxSem_gestacion;
	@View
	private Intbox ibxEdad_madre_al_nacimiento;
	@View
	private Radiogroup rdbParto;
	@View
	private Radiogroup rdbUnico_multiple;
	@View
	private Radiogroup rdbInstitucional;
	@View
	private Listbox lbxHemoclasificacion;
	@View
	private Doublebox dbxTsh;
	@View
	private Listbox lbxVdlr;
	@View
	private Radiogroup rdbReanimacion;
	@View
	private Radiogroup rdbHospitalizacion_neonatal;
	@View
	private Textbox tbxObservaciones_3_1;
	@View
	private Textbox tbxPatologicos_medicos;
	@View
	private Textbox tbxPatologicos_quirurgicos;
	@View
	private Textbox tbxPatologicos_hospitalizaciones;
	@View
	private Textbox tbxPatologicos_medicacion;
	// ------------- Jose
	
	@View
	private Textbox tbxPatologicos_alergicos;
	
	//-------------------
	@View
	private Textbox tbxObservaciones_3_3;
	@View
	private Radiogroup rdbDolor_masticar;
	@View
	private Radiogroup rdbLimpieza_boca_medio_dia;
	@View
	private Radiogroup rdbUtiliza_crema;
	@View
	private Radiogroup rdbDolor_en_diente;
	@View
	private Radiogroup rdbLimpieza_boca_noche;
	@View
	private Radiogroup rdbUtiliza_seda;
	@View
	private Radiogroup rdbPadre_hnos_caries;
	@View
	private Radiogroup rdbLe_limpia_los_dientes;
	@View
	private Radiogroup rdbAsiste_a_odontologia;
	@View
	private Radiogroup rdbLimpieza_boca_manana;
	@View
	private Radiogroup rdbUtiliza_cepillo;
	@View
	private Radiogroup rdbBcg;
	@View
	private Radiogroup rdbVop_2;
	@View
	private Radiogroup rdbVop_3;
	@View
	private Radiogroup rdbNeumococo_3;
	@View
	private Radiogroup rdbHepatitis_b_rn;
	@View
	private Radiogroup rdbHepatitis_b;
	@View
	private Radiogroup rdbPenta_2;
	@View
	private Radiogroup rdbPenta_3;
	@View
	private Radiogroup rdbTriple_viral;
	@View
	private Radiogroup rdbVop_1;
	@View
	private Radiogroup rdbNeumococo_2;
	@View
	private Radiogroup rdbInfluenza_1;
	@View
	private Radiogroup rdbFiebre_amarilla;
	@View
	private Radiogroup rdbPenta_1;
	@View
	private Radiogroup rdbRotavirus_2;
	@View
	private Radiogroup rdbInfluenza_2;
	@View
	private Radiogroup rdbDpt_r1;
	@View
	private Radiogroup rdbNeumococo_1;
	@View
	private Radiogroup rdbVop_r1;
	@View
	private Radiogroup rdbRotavirus_1;
	@View
	private Radiogroup rdbDpt_r2;
	@View
	private Radiogroup rdbVop_22;
	@View
	private Radiogroup rdbTriple_viral_refuerzo;
	@View
	private Intbox ibxNum_hermanos_vivos;
	@View
	private Intbox ibxNum_hermanos_desnutridos_menor_5anos;
	@View
	private Intbox ibxNum_hermanos_muertos_menor_5anos;
	@View
	private Textbox tbxNum_hermanos_muertos_menor_5anos_causa;
	@View
	private Radiogroup rdbSon_parientes_los_padres;
	@View
	private Radiogroup rdbFamilia_con_problema_mental_o_fisico;
	@View
	private Textbox tbxPaterno_medicos;
	@View
	private Textbox tbxPaterno_alergico;
	@View
	private Intbox ibxPaterno_talla;
	@View
	private Textbox tbxMaterno_medicos;
	@View
	private Textbox tbxMaterno_alergico;
	@View
	private Intbox ibxMaterno_talla;
	@View
	private Textbox tbxOtros_4;
	@View
	private Radiogroup rdbTestigo_relata_maltrato;
	@View
	private Radiogroup rdbEsta_descuidado_nino_salud;
	@View
	private Radiogroup rdbComportamiento_anormal_padres;
	@View
	private Radiogroup rdbEsta_descuidado_nino_higiene;
	@View
	private Radiogroup rdbActitud_anormal_nino;
	@View
	private Radiogroup rdbSe_le_pega_nino;
	@View
	private Doublebox dbxPeso_grs;
	@View
	private Doublebox dbxTalla_cm;
	@View
	private Doublebox dbxImc;
	@View
	private Doublebox dbxFc_min;
	@View
	private Doublebox dbxFr_min;
	@View
	private Doublebox dbxTemperatura_gc;
	@View
	private Radiogroup rdbEmaciacion_visible;
	@View
	private Radiogroup rdbEdema_ambos_pies;
	@View
	private Doublebox dbxT_e_de;
	@View
	private Doublebox dbxImc_e_de;
	@View
	private Radiogroup rdbTendencia_peso;
	@View
	private Textbox tbxAgudeza_visual_lejana_od;
	@View
	private Textbox tbxAgudeza_visual_lejana_oi;
	@View
	private Radiogroup rdbCabeza_cara;
	@View
	private Radiogroup rdbTorax_cardiopulmonar;
	@View
	private Radiogroup rdbAbdomen;
	@View
	private Radiogroup rdbOrgano_de_los_sentidos;
	@View
	private Radiogroup rdbTorax_cardiopulmonar_ritmo_cardiaco;
	@View
	private Radiogroup rdbAbdomen_masas;
	@View
	private Radiogroup rdbCover_uncover_test;
	@View
	private Radiogroup rdbTorax_cardiopulmonar_soplo;
	@View
	private Radiogroup rdbAbdomen_megalias;
	@View
	private Radiogroup rdbBoca;
	@View
	private Radiogroup rdbColumna_vertebral;
	@View
	private Radiogroup rdbGenito_urinario;
	@View
	private Radiogroup rdbCuello;
	@View
	private Radiogroup rdbExtremidades;
	@View
	private Radiogroup rdbNeurologico;
	@View
	private Radiogroup rdbPiel_anexos;
	@View
	private Textbox tbxObservaciones_6_2;
	@View
	private Radiogroup rdbDiagnostico_crecimiento;
	@View
	private Radiogroup rdbDiagnostico_desarrollo;
	@View
	private Checkbox chbMaltrato_fisico;
	@View
	private Checkbox chbMaltrato_emocional;
	@View
	private Checkbox chbSospecha_de_abuso_sexual;
	@View
	private Checkbox chbNo_hay_sospecha_de_maltrato;
	@View
	private Textbox tbxAnalisis_8;
	@View
	private Checkbox chbEstimulo_factores_protectores;
	@View
	private Checkbox chbRecomendaciones_de_buen_trato;
	@View
	private Checkbox chbRecomendaciones_en_salud_oral;
	@View
	private Checkbox chbRecomendaciones_nutricionales;
	@View
	private Checkbox chbImportancia_asistencia_controles;
	@View
	private Checkbox chbRecomendaciones_higienicas;
	@View
	private Checkbox chbRecomendaciones_para_el_desarrollo;
	@View
	private Textbox tbxRecomendaciones_9;
	@View
	private Radiogroup rdbSintomaticos_respiratorio;
	@View
	private Radiogroup rdbSintomaticos_piel;
	@View
	private Row rowCita_anterior;
	@View
	private Row rowPerinatales;
	@View
	private Row rowPatologicos;
	@View
	private Row rowAntecedentesFamiliares;
	@View
	private Row rowAnalisis;

	@View
	private Radiogroup rdbCa_Diagnostico_crecimiento;
	@View
	private Radiogroup rdbCa_Diagnostico_desarrollo;

	@View
	private Toolbarbutton btnCancelar;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	@View
	private Button btnCalcularTallaEdad;
	@View
	private Button btnCalcularImcEdad;

	@View
	private Toolbarbutton btGuardar;

	@View
	private Intbox ibxNum_mortinatos_v;
	@View
	private Textbox tbxComplicaciones_embarazo_parto;
	@View
	private Intbox ibxPeso_al_nacer;
	@View
	private Intbox ibxTalla_al_nacer;
	@View
	private Intbox ibxApgar_minutos;
	@View
	private Intbox ibxApgar_5_minutos;
	@View
	private Textbox tbxObservaciones_vacunales;
	@View
	private Toolbarbutton btnImprimir;
	
	private String nro_ingreso_admision;

	private Paciente paciente;
	private Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a;
	private ESexo sexo;
	private Timestamp fecha;

	private RespuestaInt coordenadaTallaEdad;
	private RespuestaInt coordenadaIMCEdad;

	private List coordenadasTE;
	private List coordenadasIE;

	private String tipo_historia;
	private Long codigo_historia_anterior;

	private Admision admision;
	private Citas cita;
	private Opciones_via_ingreso opciones_via_ingreso;

	private final String[] idsExcluyentes = { "tbxAccion", "tbxValue",
			"lbxParameter", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio","divModuloRemisiones_externas" };

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	@View(isMacro = true)
	private FiguraHumanaMacro macroFiguraHumana;
	// Modulo Farmacologico y Autorizaciones
	@View
	private Div divModuloFarmacologico;
	@View
	private Div divModuloOrdenamiento;

	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	public String valida_admision;
	private Integer edad;

	/* Observaciones */

	@View private Textbox tbxObservaciones_cabeza;
	@View private Textbox tbxObservaciones_sentidos;
	@View private Textbox tbxObservaciones_cover;
	@View private Textbox tbxObservaciones_cuello;
	@View private Textbox tbxObservaciones_torax;
	@View private Textbox tbxObservaciones_cardiaco;
	@View private Textbox tbxObservaciones_soplo;
	@View private Textbox tbxObservaciones_abdomen;
	@View private Textbox tbxObservaciones_masas;
	@View private Textbox tbxObservaciones_megalias;
	@View private Textbox tbxObservaciones_genito;
	@View private Textbox tbxObservaciones_columna;
	@View private Textbox tbxObservaciones_extremidades;
	@View private Textbox tbxObservaciones_piel;
	@View private Textbox tbxObservaciones_neurologico;
	@View private Textbox tbxObservaciones_boca;
	
	@View private Row row1;
	@View private Row row2;
	@View private Row row3;
	@View private Row row4;
	@View private Row row5;
	@View private Row row6;

	@Override
	public void init() {
		try {
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			String codigo_historia = request.getParameter("codigo_historia");
			
			if(codigo_historia!=null){
				//log.info(">>>>>>>>>>>>>codigo_historia>"+codigo_historia);
			}
			
			listarCombos();
			FormularioUtil.inicializarRadiogroupsDefecto(this);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			paciente = admision.getPaciente();
			cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
			sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO
					: ESexo.MASCULINO);
			fecha = paciente.getFecha_nacimiento();
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			edad = Integer.valueOf(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(admision.getPaciente()
					.getFecha_nacimiento()), "1", false));

		}
	}

	public void alarmaCrecimiento(Radio r) {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		// r.isChecked();
		if (r.isChecked()) {
			String mensaje = "Remitir";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, r, POSICION_ALERTA,
					TIEMPO_ALERTA, true);
		}
	}

	public void alarmaCrecimiento(Checkbox ch) {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		if (ch.isChecked()) {
			String mensaje = "Remitir";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, ch, POSICION_ALERTA,
					TIEMPO_ALERTA, true);
		}
	}

	public void alarmaExamenFisicoTemperatura() {
		UtilidadSignosVitales.validarMaxMin(dbxTemperatura_gc, 37.1, 36.4,
				"Anormal", "Anormal", false);
	}

	public void alarmaExamenFisicoFc() {

		if (edad < 8) {
			UtilidadSignosVitales.validarMaxMin(dbxFc_min, 91d, 85d, "Anormal",
					"Anormal", false);
		} else {
			UtilidadSignosVitales.validarMaxMin(dbxFc_min, 87d, 79d, "Anormal",
					"Anormal", false);
		}
	}

	public void alarmaExamenFisicoRespiratoria() {
		if (edad < 8) {
			UtilidadSignosVitales.validarMaxMin(dbxFr_min, 26d, 19d, "Anormal",
					"Anormal", false);
		} else {
			UtilidadSignosVitales.validarMaxMin(dbxFr_min, 21d, 16d, "Anormal",
					"Anormal", false);
		}
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxParentesco, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxHemoclasificacion, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxVdlr, true, getServiceLocator());
		iniciarSemEmbarazo();
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

	public void accionForm(OpcionesFormulario opciones_formulario, Object dato)
			throws Exception {
		tbxAccion.setValue(opciones_formulario.toString());
		if (opciones_formulario.equals(OpcionesFormulario.REGISTRAR)) {
			onOpcionFormularioRegistrar();
		} else if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)
				|| opciones_formulario.equals(OpcionesFormulario.MOSTRAR)) {
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
			mostrarDatos(dato);
			if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)) {
				FormularioUtil.deshabilitarComponentes(groupboxEditar, false,
						idsExcluyentes);
			}
		} else if (opciones_formulario.equals(OpcionesFormulario.CONSULTAR)) {
			onOpcionFormularioConsultar();
		}

	}

	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsulta.setVisible(false);
		groupboxEditar.setVisible(true);
		limpiarDatos();
		if (admision != null) {
			this.nro_ingreso_admision = admision.getNro_ingreso();
			infoPacientes.setFecha_inicio(new Date());
			valida_admision = null;
			cargarInformacion_paciente();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloRemisiones();
		}

		btnImprimir.setVisible(false);
	}

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		infoPacientes.validarInformacionPaciente();

		FormularioUtil.validarCamposObligatorios(tbxNombre_del_acompanante,
				lbxParentesco, tbxMotivo_consulta, tbxObservaciones_3_1,
				tbxPatologicos_medicos, tbxPatologicos_quirurgicos,
				tbxPatologicos_hospitalizaciones, tbxPatologicos_medicacion,
				tbxObservaciones_3_3, tbxAnalisis_8, tbxRecomendaciones_9,
				dbxPeso_grs, dbxTalla_cm, dbxFc_min, dbxFr_min,
				dbxTemperatura_gc,tbxPatologicos_alergicos);

		boolean valida = receta_ripsAction.validarFormExterno();
		// String mensaje = "";
		if (valida)
			valida = orden_servicioAction.validarFormExterno();

		String mensaje = "Los campos marcados con (*) son obligatorios";

//		if (valida) {
//			if (!tallaValida(dbxTalla_cm.getValue())) {
//				valida = false;
//				mensaje = "La talla no se encuentra en el rango permitido!";
//				dbxTalla_cm.setFocus(true);
//			}
//		}

		if (valida)
			valida = remisiones_externasAction.validarRemision();

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

			if (lbxTipoHistoria.getSelectedIndex() != 0) {
				parameters.put("tipo_historia", lbxTipoHistoria
						.getSelectedItem().getValue());
			}

			parameters.put("limite_paginado","limit 25 offset 0");

			List<Hisc_deteccion_alt_menor_5a_10a> lista_datos = getServiceLocator()
					.getHisc_deteccion_alt_menor_5a_10aService().listar(
							parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a : lista_datos) {
				rowsResultado.appendChild(crearFilas(
						hisc_deteccion_alt_menor_5a_10a, this));
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

		final Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a = (Hisc_deteccion_alt_menor_5a_10a) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_deteccion_alt_menor_5a_10a
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(hisc_deteccion_alt_menor_5a_10a
				.getIdentificacion() + ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(
				hisc_deteccion_alt_menor_5a_10a.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(hisc_deteccion_alt_menor_5a_10a
				.getTipo_historia().equals(
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
						mostrarDatos(hisc_deteccion_alt_menor_5a_10a);
					}
				});
		hlayout.appendChild(btn_mostrar);

		btn_mostrar = new Toolbarbutton();
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			btn_mostrar.setVisible(false);
		}
		btn_mostrar.setImage("/images/borrar.gif");
		btn_mostrar.setTooltiptext("Eliminar");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.addEventListener(Events.ON_CLICK,
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
													// do the thing
													eliminarDatos(hisc_deteccion_alt_menor_5a_10a);
													buscarDatos();
												}
											}
										});
					}
				});
		hlayout.appendChild(new Space());
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);
		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				hisc_deteccion_alt_menor_5a_10a = getBean();

				//log.info("accion ====> " + tbxAccion.getValue());

				calcularCoordenadas(false);
				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("historia_clinica", hisc_deteccion_alt_menor_5a_10a);
				datos.put("admision", admision);
				
				datos.put("accion", tbxAccion.getValue());
				datos.put("anio", ConvertidorDatosUtil.convertirDato(anio));
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());
				datos.put("cita_seleccionada", cita);
				
				Boolean val = tallaValida(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_5a_10a.getTalla_cm()));
				
				if(val){
				// Coordenada (T/E)
				Coordenadas_graficas cg1 = new Coordenadas_graficas();
				cg1.setCodigo_empresa(codigo_empresa);
				cg1.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
						.getCodigo_historia());
				cg1.setCodigo_sucursal(codigo_sucursal);
				cg1.setFecha_historia(hisc_deteccion_alt_menor_5a_10a
						.getFecha_inicial());
				cg1.setTipo_coordenada(ITipos_coordenada.T_E);
				cg1.setIdentificacion(paciente.getNro_identificacion());
				cg1.setValor("" + coordenadaTallaEdad.getValor());
				cg1.setX("" + coordenadaTallaEdad.getX());
				cg1.setY("" + coordenadaTallaEdad.getY());
				cg1.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_5_ANOS_10_ANOS);

				// Coordenada (IMC/E)
				Coordenadas_graficas cg2 = new Coordenadas_graficas();
				cg2.setCodigo_empresa(codigo_empresa);
				cg2.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
						.getCodigo_historia());
				cg2.setCodigo_sucursal(codigo_sucursal);
				cg2.setFecha_historia(hisc_deteccion_alt_menor_5a_10a
						.getFecha_inicial());
				cg2.setTipo_coordenada(ITipos_coordenada.IMC_E);
				cg2.setIdentificacion(paciente.getNro_identificacion());
				cg2.setValor("" + coordenadaIMCEdad.getValor());
				cg2.setX("" + coordenadaIMCEdad.getX());
				cg2.setY("" + coordenadaIMCEdad.getY());
				cg2.setIhistoria_clinica(IHistorias_clinicas.HC_MENOR_5_ANOS_10_ANOS);

				ArrayList<Coordenadas_graficas> coordenadas = new ArrayList<Coordenadas_graficas>();
				coordenadas.add(cg1);
				coordenadas.add(cg2);
				datos.put("coordenadas", coordenadas);
				}
				
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

				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);

				datos.put("impresion_diagnostica", impresion_diagnostica);
				Anexo9_entidad anexo9_entidad = remisiones_externasAction
						.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);

				getServiceLocator().getHisc_deteccion_alt_menor_5a_10aService()
						.guardarDatos(datos);

				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad
							.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(
							false);
				}
				mostrarDatosAutorizacion(datos);
				tbxAccion.setValue("modificar");
				infoPacientes
						.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
								.getCodigo_historia());
				Receta_rips receta_rips = (Receta_rips) mapReceta
						.get("receta_rips");
				if (receta_rips != null)
					receta_ripsAction.mostrarDatos(receta_rips, false);
				// hay que llamar este metodo para validar que salga el boton
				// para imprimir despues de guardar la receta
				receta_ripsAction.validarParaImpresion();

				Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
						.get("orden_servicio");
				if (orden_servicio != null)
					orden_servicioAction.mostrarDatos(orden_servicio);
				// hay que llamar este metodo para validar que salga el boton
				// para imprimir despues de guardar la orden
				orden_servicioAction.validarParaImpresion();

				actualizarAutorizaciones(admision,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision, getInformacionClinica(), this);

				MensajesUtil.mensajeInformacion("Informacion ...",
						"Los datos se guardaron satisfactoriamente");
				btnImprimir.setVisible(true);
			}
		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			} else {
				log.error(((WrongValueException) e).getComponent().getId()
						+ " esta presentando error", e);
			}
		}

	}

	private Hisc_deteccion_alt_menor_5a_10a getBean() {
		hisc_deteccion_alt_menor_5a_10a = new Hisc_deteccion_alt_menor_5a_10a();

		hisc_deteccion_alt_menor_5a_10a.setFecha_inicial(new Timestamp(
				infoPacientes.getFecha_inicial().getTime()));
		hisc_deteccion_alt_menor_5a_10a.setUltimo_update(new Timestamp(
				(new Date()).getTime()));
		hisc_deteccion_alt_menor_5a_10a
				.setSintomaticos_respiratorio(rdbSintomaticos_respiratorio
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setSintomaticos_piel(rdbSintomaticos_piel
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setCodigo_empresa(empresa
				.getCodigo_empresa());
		hisc_deteccion_alt_menor_5a_10a.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());

		hisc_deteccion_alt_menor_5a_10a
				.setCodigo_historia(infoPacientes.getCodigo_historia());
		hisc_deteccion_alt_menor_5a_10a
				.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
		hisc_deteccion_alt_menor_5a_10a.setNro_ingreso(admision
				.getNro_ingreso());

		hisc_deteccion_alt_menor_5a_10a
				.setNombre_del_acompanante(tbxNombre_del_acompanante
						.getValue());
		hisc_deteccion_alt_menor_5a_10a.setParentesco(lbxParentesco
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setNombre_del_padre(tbxNombre_del_padre.getValue());
		hisc_deteccion_alt_menor_5a_10a.setEdad_padre((ibxEdad_padre
				.getValue() != null ? ibxEdad_padre.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a
				.setNombre_de_la_madre(tbxNombre_de_la_madre.getValue());
		hisc_deteccion_alt_menor_5a_10a.setEdad_madre((ibxEdad_madre
				.getValue() != null ? ibxEdad_madre.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a
				.setMotivo_consulta(tbxMotivo_consulta.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setGestion_actual(tbxGestion_actual.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setNum_embarazos_g((ibxNum_embarazos_g.getValue() != null ? ibxNum_embarazos_g
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a
				.setNum_partos_p((ibxNum_partos_p.getValue() != null ? ibxNum_partos_p
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a
				.setNum_abortos_a((ibxNum_abortos_a.getValue() != null ? ibxNum_abortos_a
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a
				.setNum_cesarias_c((ibxNum_cesarias_c.getValue() != null ? ibxNum_cesarias_c
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a.setRiesgo(rdbRiesgo
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setControl_prenatal(rdbControl_prenatal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setSem_gestacion(lbxSem_gestacion.getSelectedIndex());
		hisc_deteccion_alt_menor_5a_10a
				.setEdad_madre_al_nacimiento((ibxEdad_madre_al_nacimiento
						.getValue() != null ? ibxEdad_madre_al_nacimiento
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a.setParto(rdbParto
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setUnico_multiple(rdbUnico_multiple.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setInstitucional(rdbInstitucional.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setHemoclasificacion(lbxHemoclasificacion
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setTsh((dbxTsh.getValue() != null ? dbxTsh.getValue()
						.toString() : "0.00"));
		hisc_deteccion_alt_menor_5a_10a.setVdrl_materno(lbxVdlr
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setReanimacion(rdbReanimacion
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setHospitalizacion_neonatal(rdbHospitalizacion_neonatal
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setObservaciones_3_1(tbxObservaciones_3_1.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setPatologicos_medicos(tbxPatologicos_medicos
						.getValue());
		
		hisc_deteccion_alt_menor_5a_10a
				.setPatologicos_quirurgicos(tbxPatologicos_quirurgicos
						.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setPatologicos_hospitalizaciones(tbxPatologicos_hospitalizaciones
						.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setPatologicos_medicacion(tbxPatologicos_medicacion
						.getValue());
		hisc_deteccion_alt_menor_5a_10a.setAntecedentes_alergicos(tbxPatologicos_alergicos
				.getValue());	
		hisc_deteccion_alt_menor_5a_10a
				.setObservaciones_3_3(tbxObservaciones_3_3.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setDolor_masticar(rdbDolor_masticar.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setLimpieza_boca_medio_dia(rdbLimpieza_boca_medio_dia
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setUtiliza_crema(rdbUtiliza_crema.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setDolor_en_diente(rdbDolor_en_diente
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setLimpieza_boca_noche(rdbLimpieza_boca_noche
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setUtiliza_seda(rdbUtiliza_seda
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setPadre_hnos_caries(rdbPadre_hnos_caries
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setLe_limpia_los_dientes(rdbLe_limpia_los_dientes
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setAsiste_a_odontologia(rdbAsiste_a_odontologia
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setLimpieza_boca_manana(rdbLimpieza_boca_manana
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setUtiliza_cepillo(rdbUtiliza_cepillo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setBcg(rdbBcg.getSelectedItem()
				.getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setVop_2(rdbVop_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setVop_3(rdbVop_3
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setNeumococo_3(rdbNeumococo_3
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setHepatitis_b_rn(rdbHepatitis_b_rn.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setHepatitis_a(rdbHepatitis_a
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setHepatitis_b(rdbHepatitis_b
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setPenta_2(rdbPenta_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setPenta_3(rdbPenta_3
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setTriple_viral(rdbTriple_viral
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setVop_1(rdbVop_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setNeumococo_2(rdbNeumococo_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setInfluenza_1(rdbInfluenza_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setFiebre_amarilla(rdbFiebre_amarilla
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setPenta_1(rdbPenta_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setRotavirus_2(rdbRotavirus_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setInfluenza_2(rdbInfluenza_2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setDpt_r1(rdbDpt_r1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setNeumococo_1(rdbNeumococo_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setVop_r1(rdbVop_r1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setRotavirus_1(rdbRotavirus_1
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setDpt_r2(rdbDpt_r2
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setVop_22(rdbVop_22
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setTriple_viral_refuerzo(rdbTriple_viral_refuerzo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setNum_hermanos_vivos((ibxNum_hermanos_vivos
						.getValue() != null ? ibxNum_hermanos_vivos
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a
				.setNum_hermanos_desnutridos_menor_5anos((ibxNum_hermanos_desnutridos_menor_5anos
						.getValue() != null ? ibxNum_hermanos_desnutridos_menor_5anos
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a
				.setNum_hermanos_muertos_menor_5anos((ibxNum_hermanos_muertos_menor_5anos
						.getValue() != null ? ibxNum_hermanos_muertos_menor_5anos
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a
				.setNum_hermanos_muertos_menor_5anos_causa(tbxNum_hermanos_muertos_menor_5anos_causa
						.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setSon_parientes_los_padres(rdbSon_parientes_los_padres
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setFamilia_con_problema_mental_o_fisico(rdbFamilia_con_problema_mental_o_fisico
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setPaterno_medicos(tbxPaterno_medicos.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setPaterno_alergico(tbxPaterno_alergico.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setPaterno_talla((ibxPaterno_talla.getValue() != null ? ibxPaterno_talla
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a
				.setMaterno_medicos(tbxMaterno_medicos.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setMaterno_alergico(tbxMaterno_alergico.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setMaterno_talla((ibxMaterno_talla.getValue() != null ? ibxMaterno_talla
						.getValue() : 0));
		hisc_deteccion_alt_menor_5a_10a.setOtros_4(tbxOtros_4
				.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setTestigo_relata_maltrato(rdbTestigo_relata_maltrato
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setEsta_descuidado_nino_salud(rdbEsta_descuidado_nino_salud
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setComportamiento_anormal_padres(rdbComportamiento_anormal_padres
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setEsta_descuidado_nino_higiene(rdbEsta_descuidado_nino_higiene
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setActitud_anormal_nino(rdbActitud_anormal_nino
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setSe_le_pega_nino(rdbSe_le_pega_nino
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setPeso_grs((dbxPeso_grs
				.getValue() != null ? dbxPeso_grs.getValue().toString()
				: "0.00"));
		hisc_deteccion_alt_menor_5a_10a.setTalla_cm((dbxTalla_cm
				.getValue() != null ? dbxTalla_cm.getValue().toString()
				: "0.00"));
		hisc_deteccion_alt_menor_5a_10a
				.setFc_min((dbxFc_min.getValue() != null ? dbxFc_min
						.getValue().toString() : "0"));
		hisc_deteccion_alt_menor_5a_10a
				.setFr_min((dbxFr_min.getValue() != null ? dbxFr_min
						.getValue().toString() : "0"));
		hisc_deteccion_alt_menor_5a_10a
				.setTemperatura_gc((dbxTemperatura_gc.getValue() != null ? dbxTemperatura_gc
						.getValue().toString() : "0.00"));
		hisc_deteccion_alt_menor_5a_10a
				.setEmaciacion_visible(rdbEmaciacion_visible
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setEdema_ambos_pies(rdbEdema_ambos_pies
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setT_e_de(dbxT_e_de.getValue() != null ? dbxT_e_de.getValue()
				.toString() : "");
		hisc_deteccion_alt_menor_5a_10a.setImc_e_de(dbxImc_e_de.getValue() != null ? dbxImc_e_de
				.getValue().toString() : "");
		hisc_deteccion_alt_menor_5a_10a
				.setTendencia_peso(rdbTendencia_peso.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setAgudeza_visual_lejana_od(tbxAgudeza_visual_lejana_od
						.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setAgudeza_visual_lejana_oi(tbxAgudeza_visual_lejana_oi
						.getValue());
		hisc_deteccion_alt_menor_5a_10a.setCabeza_cara(rdbCabeza_cara
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setTorax_cardiopulmonar(rdbTorax_cardiopulmonar
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setAbdomen(rdbAbdomen
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setOrgano_de_los_sentidos(rdbOrgano_de_los_sentidos
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setTorax_cardiopulmonar_ritmo_cardiaco(rdbTorax_cardiopulmonar_ritmo_cardiaco
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setAbdomen_masas(rdbAbdomen_masas.getSelectedItem()
						.getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setCover_uncover_test(rdbCover_uncover_test
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setTorax_cardiopulmonar_soplo(rdbTorax_cardiopulmonar_soplo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setAbdomen_megalias(rdbAbdomen_megalias
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setBoca(rdbBoca
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setColumna_vertebral(rdbColumna_vertebral
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setGenito_urinario(rdbGenito_urinario
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setCuello(rdbCuello
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setExtremidades(rdbExtremidades
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setNeurologico(rdbNeurologico
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a.setPiel_anexos(rdbPiel_anexos
				.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setObservaciones_6_2(tbxObservaciones_6_2.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setDiagnostico_crecimiento(rdbDiagnostico_crecimiento
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setDiagnostico_desarrollo(rdbDiagnostico_desarrollo
						.getSelectedItem().getValue().toString());
		hisc_deteccion_alt_menor_5a_10a
				.setMaltrato_fisico(chbMaltrato_fisico.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setMaltrato_emocional(chbMaltrato_emocional
						.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setSospecha_de_abuso_sexual(chbSospecha_de_abuso_sexual
						.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setNo_hay_sospecha_de_maltrato(chbNo_hay_sospecha_de_maltrato
						.isChecked());

		hisc_deteccion_alt_menor_5a_10a.setAnalisis_8(tbxAnalisis_8
				.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setEstimulo_factores_protectores(chbEstimulo_factores_protectores
						.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setRecomendaciones_de_buen_trato(chbRecomendaciones_de_buen_trato
						.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setRecomendaciones_en_salud_oral(chbRecomendaciones_en_salud_oral
						.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setRecomendaciones_nutricionales(chbRecomendaciones_nutricionales
						.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setImportancia_asistencia_controles(chbImportancia_asistencia_controles
						.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setRecomendaciones_higienicas(chbRecomendaciones_higienicas
						.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setRecomendaciones_para_el_desarrollo(chbRecomendaciones_para_el_desarrollo
						.isChecked());
		hisc_deteccion_alt_menor_5a_10a
				.setRecomendaciones_9(tbxRecomendaciones_9.getValue());
		hisc_deteccion_alt_menor_5a_10a.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_deteccion_alt_menor_5a_10a.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_deteccion_alt_menor_5a_10a.setCreacion_user(usuarios
				.getCodigo().toString());
		hisc_deteccion_alt_menor_5a_10a.setDelete_date(null);
		hisc_deteccion_alt_menor_5a_10a.setUltimo_user(usuarios
				.getCodigo().toString());
		hisc_deteccion_alt_menor_5a_10a.setDelete_user(null);

		hisc_deteccion_alt_menor_5a_10a.setTipo_historia(tipo_historia);
		hisc_deteccion_alt_menor_5a_10a
				.setCodigo_historia_anterior(codigo_historia_anterior);

		hisc_deteccion_alt_menor_5a_10a
				.setNum_mortinatos_v(ibxNum_mortinatos_v.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setComplicaciones_embarazo_parto(tbxComplicaciones_embarazo_parto
						.getValue());
		hisc_deteccion_alt_menor_5a_10a
				.setPeso_al_nacer(ibxPeso_al_nacer.getValue() + "");
		hisc_deteccion_alt_menor_5a_10a
				.setTalla_al_nacer(ibxTalla_al_nacer.getValue() + "");
		hisc_deteccion_alt_menor_5a_10a
				.setApgar_minutos(ibxApgar_minutos.getValue() + "");
		hisc_deteccion_alt_menor_5a_10a
				.setApgar_5_minutos(ibxApgar_5_minutos.getValue() + "");
		hisc_deteccion_alt_menor_5a_10a
				.setObservaciones_vacunales(tbxObservaciones_vacunales
						.getValue());


		hisc_deteccion_alt_menor_5a_10a.setObservaciones_cabeza(tbxObservaciones_cabeza.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_sentidos(tbxObservaciones_sentidos.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_cover(tbxObservaciones_cover.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_cuello(tbxObservaciones_cuello.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_torax(tbxObservaciones_torax.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_cardiaco(tbxObservaciones_cardiaco.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_soplo(tbxObservaciones_soplo.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_abdomen(tbxObservaciones_abdomen.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_masas(tbxObservaciones_masas.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_megalias(tbxObservaciones_megalias.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_genito(tbxObservaciones_genito.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_columna(tbxObservaciones_columna.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_extremidades(tbxObservaciones_extremidades.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_piel(tbxObservaciones_piel.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_neurologico(tbxObservaciones_neurologico.getValue());
		hisc_deteccion_alt_menor_5a_10a.setObservaciones_boca(tbxObservaciones_boca.getValue());
		
		return hisc_deteccion_alt_menor_5a_10a;
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		hisc_deteccion_alt_menor_5a_10a = (Hisc_deteccion_alt_menor_5a_10a) obj;
		try {

			this.nro_ingreso_admision = hisc_deteccion_alt_menor_5a_10a
					.getNro_ingreso();

			infoPacientes.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_deteccion_alt_menor_5a_10a
					.getFecha_inicial());

			onMostrarModuloRemisiones();

			cargarRemisionInterna(hisc_deteccion_alt_menor_5a_10a);

			infoPacientes.setFecha_cierre(true,
					hisc_deteccion_alt_menor_5a_10a.getUltimo_update());
			initMostrar_datos(hisc_deteccion_alt_menor_5a_10a);
			cargarInformacion_paciente();

			tbxNombre_del_acompanante.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNombre_del_acompanante());
			Utilidades.seleccionarListitem(lbxParentesco,
					hisc_deteccion_alt_menor_5a_10a.getParentesco());

			tbxNombre_del_padre.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNombre_del_padre());
			ibxEdad_padre.setValue(hisc_deteccion_alt_menor_5a_10a
					.getEdad_padre());
			tbxNombre_de_la_madre.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNombre_de_la_madre());
			ibxEdad_madre.setValue(hisc_deteccion_alt_menor_5a_10a
					.getEdad_madre());
			tbxMotivo_consulta.setValue(hisc_deteccion_alt_menor_5a_10a
					.getMotivo_consulta());
			tbxGestion_actual.setValue(hisc_deteccion_alt_menor_5a_10a
					.getGestion_actual());
			ibxNum_embarazos_g.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNum_embarazos_g());
			ibxNum_partos_p.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNum_partos_p());
			ibxNum_abortos_a.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNum_abortos_a());
			ibxNum_cesarias_c.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNum_cesarias_c());
			Utilidades.seleccionarRadio(rdbRiesgo,
					hisc_deteccion_alt_menor_5a_10a.getRiesgo());
			Utilidades.seleccionarRadio(rdbControl_prenatal,
					hisc_deteccion_alt_menor_5a_10a.getControl_prenatal());
			Utilidades.seleccionarListitem(lbxSem_gestacion,
					hisc_deteccion_alt_menor_5a_10a.getSem_gestacion()
							.toString());
			ibxEdad_madre_al_nacimiento
					.setValue(hisc_deteccion_alt_menor_5a_10a
							.getEdad_madre_al_nacimiento());
			Utilidades.seleccionarRadio(rdbParto,
					hisc_deteccion_alt_menor_5a_10a.getParto());
			Utilidades.seleccionarRadio(rdbUnico_multiple,
					hisc_deteccion_alt_menor_5a_10a.getUnico_multiple());
			Utilidades.seleccionarRadio(rdbInstitucional,
					hisc_deteccion_alt_menor_5a_10a.getInstitucional());
			Utilidades.seleccionarListitem(lbxHemoclasificacion,
					hisc_deteccion_alt_menor_5a_10a.getHemoclasificacion());
			dbxTsh.setValue(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_5a_10a
					.getTsh()));
			Utilidades.seleccionarListitem(lbxVdlr,
					hisc_deteccion_alt_menor_5a_10a.getVdrl_materno());
			Utilidades.seleccionarRadio(rdbReanimacion,
					hisc_deteccion_alt_menor_5a_10a.getReanimacion());
			Utilidades.seleccionarRadio(rdbHospitalizacion_neonatal,
					hisc_deteccion_alt_menor_5a_10a
							.getHospitalizacion_neonatal());
			tbxObservaciones_3_1.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_3_1());
			tbxPatologicos_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPatologicos_medicos());
			tbxPatologicos_quirurgicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPatologicos_quirurgicos());
			tbxPatologicos_hospitalizaciones
					.setValue(hisc_deteccion_alt_menor_5a_10a
							.getPatologicos_hospitalizaciones());
			tbxPatologicos_medicacion.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPatologicos_medicacion());
			tbxPatologicos_alergicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getAntecedentes_alergicos());
			tbxObservaciones_3_3.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_3_3());
			Utilidades.seleccionarRadio(rdbDolor_masticar,
					hisc_deteccion_alt_menor_5a_10a.getDolor_masticar());
			Utilidades.seleccionarRadio(rdbLimpieza_boca_medio_dia,
					hisc_deteccion_alt_menor_5a_10a
							.getLimpieza_boca_medio_dia());
			Utilidades.seleccionarRadio(rdbUtiliza_crema,
					hisc_deteccion_alt_menor_5a_10a.getUtiliza_crema());
			Utilidades.seleccionarRadio(rdbDolor_en_diente,
					hisc_deteccion_alt_menor_5a_10a.getDolor_en_diente());
			Utilidades.seleccionarRadio(rdbLimpieza_boca_noche,
					hisc_deteccion_alt_menor_5a_10a.getLimpieza_boca_noche());
			Utilidades.seleccionarRadio(rdbUtiliza_seda,
					hisc_deteccion_alt_menor_5a_10a.getUtiliza_seda());
			Utilidades.seleccionarRadio(rdbPadre_hnos_caries,
					hisc_deteccion_alt_menor_5a_10a.getPadre_hnos_caries());
			Utilidades.seleccionarRadio(rdbLe_limpia_los_dientes,
					hisc_deteccion_alt_menor_5a_10a.getLe_limpia_los_dientes());
			Utilidades.seleccionarRadio(rdbAsiste_a_odontologia,
					hisc_deteccion_alt_menor_5a_10a.getAsiste_a_odontologia());
			Utilidades.seleccionarRadio(rdbLimpieza_boca_manana,
					hisc_deteccion_alt_menor_5a_10a.getLimpieza_boca_manana());
			Utilidades.seleccionarRadio(rdbUtiliza_cepillo,
					hisc_deteccion_alt_menor_5a_10a.getUtiliza_cepillo());
			Utilidades.seleccionarRadio(rdbBcg,
					hisc_deteccion_alt_menor_5a_10a.getBcg());
			Utilidades.seleccionarRadio(rdbVop_2,
					hisc_deteccion_alt_menor_5a_10a.getVop_2());
			Utilidades.seleccionarRadio(rdbVop_3,
					hisc_deteccion_alt_menor_5a_10a.getVop_3());
			Utilidades.seleccionarRadio(rdbNeumococo_3,
					hisc_deteccion_alt_menor_5a_10a.getNeumococo_3());
			Utilidades.seleccionarRadio(rdbHepatitis_b_rn,
					hisc_deteccion_alt_menor_5a_10a.getHepatitis_b_rn());
			Utilidades.seleccionarRadio(rdbHepatitis_b,
					hisc_deteccion_alt_menor_5a_10a.getHepatitis_b());
			Utilidades.seleccionarRadio(rdbHepatitis_a,
					hisc_deteccion_alt_menor_5a_10a.getHepatitis_a());
			Utilidades.seleccionarRadio(rdbPenta_2,
					hisc_deteccion_alt_menor_5a_10a.getPenta_2());
			Utilidades.seleccionarRadio(rdbPenta_3,
					hisc_deteccion_alt_menor_5a_10a.getPenta_3());
			Utilidades.seleccionarRadio(rdbTriple_viral,
					hisc_deteccion_alt_menor_5a_10a.getTriple_viral());
			Utilidades.seleccionarRadio(rdbVop_1,
					hisc_deteccion_alt_menor_5a_10a.getVop_1());
			Utilidades.seleccionarRadio(rdbNeumococo_2,
					hisc_deteccion_alt_menor_5a_10a.getNeumococo_2());
			Utilidades.seleccionarRadio(rdbInfluenza_1,
					hisc_deteccion_alt_menor_5a_10a.getInfluenza_1());
			Utilidades.seleccionarRadio(rdbFiebre_amarilla,
					hisc_deteccion_alt_menor_5a_10a.getFiebre_amarilla());
			Utilidades.seleccionarRadio(rdbPenta_1,
					hisc_deteccion_alt_menor_5a_10a.getPenta_1());
			Utilidades.seleccionarRadio(rdbRotavirus_2,
					hisc_deteccion_alt_menor_5a_10a.getRotavirus_2());
			Utilidades.seleccionarRadio(rdbInfluenza_2,
					hisc_deteccion_alt_menor_5a_10a.getInfluenza_2());
			Utilidades.seleccionarRadio(rdbDpt_r1,
					hisc_deteccion_alt_menor_5a_10a.getDpt_r1());
			Utilidades.seleccionarRadio(rdbNeumococo_1,
					hisc_deteccion_alt_menor_5a_10a.getNeumococo_1());
			Utilidades.seleccionarRadio(rdbVop_r1,
					hisc_deteccion_alt_menor_5a_10a.getVop_r1());
			Utilidades.seleccionarRadio(rdbRotavirus_1,
					hisc_deteccion_alt_menor_5a_10a.getRotavirus_1());
			Utilidades.seleccionarRadio(rdbDpt_r2,
					hisc_deteccion_alt_menor_5a_10a.getDpt_r2());
			Utilidades.seleccionarRadio(rdbVop_22,
					hisc_deteccion_alt_menor_5a_10a.getVop_22());
			Utilidades.seleccionarRadio(rdbTriple_viral_refuerzo,
					hisc_deteccion_alt_menor_5a_10a.getTriple_viral_refuerzo());
			Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
					hisc_deteccion_alt_menor_5a_10a
							.getSintomaticos_respiratorio());
			Utilidades.seleccionarRadio(rdbSintomaticos_piel,
					hisc_deteccion_alt_menor_5a_10a.getSintomaticos_piel());
			ibxNum_hermanos_vivos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNum_hermanos_vivos());
			ibxNum_hermanos_desnutridos_menor_5anos
					.setValue(hisc_deteccion_alt_menor_5a_10a
							.getNum_hermanos_desnutridos_menor_5anos());
			ibxNum_hermanos_muertos_menor_5anos
					.setValue(hisc_deteccion_alt_menor_5a_10a
							.getNum_hermanos_muertos_menor_5anos());
			tbxNum_hermanos_muertos_menor_5anos_causa
					.setValue(hisc_deteccion_alt_menor_5a_10a
							.getNum_hermanos_muertos_menor_5anos_causa());
			Utilidades.seleccionarRadio(rdbSon_parientes_los_padres,
					hisc_deteccion_alt_menor_5a_10a
							.getSon_parientes_los_padres());
			Utilidades.seleccionarRadio(
					rdbFamilia_con_problema_mental_o_fisico,
					hisc_deteccion_alt_menor_5a_10a
							.getFamilia_con_problema_mental_o_fisico());
			tbxPaterno_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPaterno_medicos());
			tbxPaterno_alergico.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPaterno_alergico());
			ibxPaterno_talla.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPaterno_talla());
			tbxMaterno_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getMaterno_medicos());
			tbxMaterno_alergico.setValue(hisc_deteccion_alt_menor_5a_10a
					.getMaterno_alergico());
			ibxMaterno_talla.setValue(hisc_deteccion_alt_menor_5a_10a
					.getMaterno_talla());
			tbxOtros_4.setValue(hisc_deteccion_alt_menor_5a_10a.getOtros_4());
			Utilidades.seleccionarRadio(rdbTestigo_relata_maltrato,
					hisc_deteccion_alt_menor_5a_10a
							.getTestigo_relata_maltrato());
			Utilidades.seleccionarRadio(rdbEsta_descuidado_nino_salud,
					hisc_deteccion_alt_menor_5a_10a
							.getEsta_descuidado_nino_salud());
			Utilidades.seleccionarRadio(rdbComportamiento_anormal_padres,
					hisc_deteccion_alt_menor_5a_10a
							.getComportamiento_anormal_padres());
			Utilidades.seleccionarRadio(rdbEsta_descuidado_nino_higiene,
					hisc_deteccion_alt_menor_5a_10a
							.getEsta_descuidado_nino_higiene());
			Utilidades.seleccionarRadio(rdbActitud_anormal_nino,
					hisc_deteccion_alt_menor_5a_10a.getActitud_anormal_nino());
			Utilidades.seleccionarRadio(rdbSe_le_pega_nino,
					hisc_deteccion_alt_menor_5a_10a.getSe_le_pega_nino());
			dbxPeso_grs.setValue(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_5a_10a
					.getPeso_grs()));
			dbxTalla_cm.setValue(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_5a_10a
					.getTalla_cm()));
			dbxFc_min.setValue(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_5a_10a
					.getFc_min()));
			dbxFr_min.setValue(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_5a_10a
					.getFr_min()));
			dbxTemperatura_gc.setValue(Double
					.valueOf(hisc_deteccion_alt_menor_5a_10a
							.getTemperatura_gc()));
			Utilidades.seleccionarRadio(rdbEmaciacion_visible,
					hisc_deteccion_alt_menor_5a_10a.getEmaciacion_visible());
			Utilidades.seleccionarRadio(rdbEdema_ambos_pies,
					hisc_deteccion_alt_menor_5a_10a.getEdema_ambos_pies());
			dbxT_e_de.setValue(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_5a_10a
					.getT_e_de()));
			dbxImc_e_de.setValue(ConvertidorDatosUtil.convertirDato(hisc_deteccion_alt_menor_5a_10a
					.getImc_e_de()));
			Utilidades.seleccionarRadio(rdbTendencia_peso,
					hisc_deteccion_alt_menor_5a_10a.getTendencia_peso());
			tbxAgudeza_visual_lejana_od
					.setValue(hisc_deteccion_alt_menor_5a_10a
							.getAgudeza_visual_lejana_od());
			tbxAgudeza_visual_lejana_oi
					.setValue(hisc_deteccion_alt_menor_5a_10a
							.getAgudeza_visual_lejana_oi());
			Utilidades.seleccionarRadio(rdbCabeza_cara,
					hisc_deteccion_alt_menor_5a_10a.getCabeza_cara());
			Utilidades.seleccionarRadio(rdbTorax_cardiopulmonar,
					hisc_deteccion_alt_menor_5a_10a.getTorax_cardiopulmonar());
			Utilidades.seleccionarRadio(rdbAbdomen,
					hisc_deteccion_alt_menor_5a_10a.getAbdomen());
			Utilidades
					.seleccionarRadio(rdbOrgano_de_los_sentidos,
							hisc_deteccion_alt_menor_5a_10a
									.getOrgano_de_los_sentidos());
			Utilidades.seleccionarRadio(rdbTorax_cardiopulmonar_ritmo_cardiaco,
					hisc_deteccion_alt_menor_5a_10a
							.getTorax_cardiopulmonar_ritmo_cardiaco());
			Utilidades.seleccionarRadio(rdbAbdomen_masas,
					hisc_deteccion_alt_menor_5a_10a.getAbdomen_masas());
			Utilidades.seleccionarRadio(rdbCover_uncover_test,
					hisc_deteccion_alt_menor_5a_10a.getCover_uncover_test());
			Utilidades.seleccionarRadio(rdbTorax_cardiopulmonar_soplo,
					hisc_deteccion_alt_menor_5a_10a
							.getTorax_cardiopulmonar_soplo());
			Utilidades.seleccionarRadio(rdbAbdomen_megalias,
					hisc_deteccion_alt_menor_5a_10a.getAbdomen_megalias());
			Utilidades.seleccionarRadio(rdbBoca,
					hisc_deteccion_alt_menor_5a_10a.getBoca());
			Utilidades.seleccionarRadio(rdbColumna_vertebral,
					hisc_deteccion_alt_menor_5a_10a.getColumna_vertebral());
			Utilidades.seleccionarRadio(rdbGenito_urinario,
					hisc_deteccion_alt_menor_5a_10a.getGenito_urinario());
			Utilidades.seleccionarRadio(rdbCuello,
					hisc_deteccion_alt_menor_5a_10a.getCuello());
			Utilidades.seleccionarRadio(rdbExtremidades,
					hisc_deteccion_alt_menor_5a_10a.getExtremidades());
			Utilidades.seleccionarRadio(rdbNeurologico,
					hisc_deteccion_alt_menor_5a_10a.getNeurologico());
			Utilidades.seleccionarRadio(rdbPiel_anexos,
					hisc_deteccion_alt_menor_5a_10a.getPiel_anexos());
			tbxObservaciones_6_2.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_6_2());
			Utilidades.seleccionarRadio(rdbDiagnostico_crecimiento,
					hisc_deteccion_alt_menor_5a_10a
							.getDiagnostico_crecimiento());
			Utilidades
					.seleccionarRadio(rdbDiagnostico_desarrollo,
							hisc_deteccion_alt_menor_5a_10a
									.getDiagnostico_desarrollo());
			chbMaltrato_fisico.setChecked(hisc_deteccion_alt_menor_5a_10a
					.getMaltrato_fisico());
			chbMaltrato_emocional.setChecked(hisc_deteccion_alt_menor_5a_10a
					.getMaltrato_emocional());
			chbSospecha_de_abuso_sexual
					.setChecked(hisc_deteccion_alt_menor_5a_10a
							.getSospecha_de_abuso_sexual());
			chbNo_hay_sospecha_de_maltrato
					.setChecked(hisc_deteccion_alt_menor_5a_10a
							.getNo_hay_sospecha_de_maltrato());

			cargarImpresionDiagnostica(hisc_deteccion_alt_menor_5a_10a);

			valida_admision = hisc_deteccion_alt_menor_5a_10a.getNro_ingreso();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);

			tbxAnalisis_8.setValue(hisc_deteccion_alt_menor_5a_10a
					.getAnalisis_8());
			chbEstimulo_factores_protectores
					.setChecked(hisc_deteccion_alt_menor_5a_10a
							.getEstimulo_factores_protectores());
			chbRecomendaciones_de_buen_trato
					.setChecked(hisc_deteccion_alt_menor_5a_10a
							.getRecomendaciones_de_buen_trato());
			chbRecomendaciones_en_salud_oral
					.setChecked(hisc_deteccion_alt_menor_5a_10a
							.getRecomendaciones_en_salud_oral());
			chbRecomendaciones_nutricionales
					.setChecked(hisc_deteccion_alt_menor_5a_10a
							.getRecomendaciones_nutricionales());
			chbImportancia_asistencia_controles
					.setChecked(hisc_deteccion_alt_menor_5a_10a
							.getImportancia_asistencia_controles());
			chbRecomendaciones_higienicas
					.setChecked(hisc_deteccion_alt_menor_5a_10a
							.getRecomendaciones_higienicas());
			chbRecomendaciones_para_el_desarrollo
					.setChecked(hisc_deteccion_alt_menor_5a_10a
							.getRecomendaciones_para_el_desarrollo());
			tbxRecomendaciones_9.setValue(hisc_deteccion_alt_menor_5a_10a
					.getRecomendaciones_9());

			ibxNum_mortinatos_v.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNum_mortinatos_v());
			tbxComplicaciones_embarazo_parto
					.setValue(hisc_deteccion_alt_menor_5a_10a
							.getComplicaciones_embarazo_parto());
			ibxPeso_al_nacer.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_menor_5a_10a
							.getPeso_al_nacer()));
			ibxTalla_al_nacer.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_menor_5a_10a
							.getTalla_al_nacer()));
			ibxApgar_minutos.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_menor_5a_10a
							.getApgar_minutos()));
			ibxApgar_5_minutos.setValue(ConvertidorDatosUtil
					.convertirDatot(hisc_deteccion_alt_menor_5a_10a
							.getApgar_5_minutos()));

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", codigo_empresa);
			param.put("codigo_sucursal", codigo_sucursal);
			param.put("historia",
					hisc_deteccion_alt_menor_5a_10a.getCodigo_historia());
			param.put("via_ingreso", admision.getVia_ingreso());

			cargarAnexo9_remision(hisc_deteccion_alt_menor_5a_10a);

			List<Test_puntuacion_figura_humana> puntos = getServiceLocator()
					.getServicio(Test_puntuacion_figura_humanaService.class)
					.listar(param);
			macroFiguraHumana.cargarPuntos(puntos);

			tbxObservaciones_vacunales.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_vacunales());
			
			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "2", tbxObservaciones_cabeza, row1);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar, "2", tbxObservaciones_torax, row1);
			FormularioUtil.mostrarObservaciones(rdbAbdomen,"2",tbxObservaciones_abdomen,row1);
			FormularioUtil.mostrarObservaciones(rdbOrgano_de_los_sentidos,"2",tbxObservaciones_sentidos,row2);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar_ritmo_cardiaco, "2", tbxObservaciones_cardiaco, row2);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_masas,"2",tbxObservaciones_masas,row2);
			FormularioUtil.mostrarObservaciones(rdbCover_uncover_test, "2", tbxObservaciones_cover, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar_soplo, "2", tbxObservaciones_soplo, row3);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_megalias, "2", tbxObservaciones_megalias, row3);
			FormularioUtil.mostrarObservaciones(rdbBoca,"2",tbxObservaciones_boca,row4);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "2", tbxObservaciones_columna, row4);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2", tbxObservaciones_genito, row4);
			FormularioUtil.mostrarObservaciones(rdbCuello, "2", tbxObservaciones_cuello, row5);
			FormularioUtil.mostrarObservaciones(rdbExtremidades, "2", tbxObservaciones_extremidades, row5);			
			FormularioUtil.mostrarObservaciones(rdbPiel_anexos, "2", tbxObservaciones_piel, row6);
			FormularioUtil.mostrarObservaciones(rdbNeurologico,"2",tbxObservaciones_neurologico,row6);

			tbxObservaciones_cabeza.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_cabeza());
			tbxObservaciones_abdomen.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_abdomen());
			tbxObservaciones_neurologico.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_neurologico());
			
			tbxObservaciones_sentidos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_sentidos());
			tbxObservaciones_masas.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_masas());
			
			tbxObservaciones_cover.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_cover());
			tbxObservaciones_megalias.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_megalias());
			
			tbxObservaciones_cuello.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_cuello());
			tbxObservaciones_genito.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_genito());

			tbxObservaciones_torax.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_torax());
			tbxObservaciones_columna.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_columna());

			tbxObservaciones_cardiaco.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_cardiaco());
			tbxObservaciones_extremidades.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_extremidades());

			tbxObservaciones_soplo.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_soplo());
			tbxObservaciones_piel.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_piel());
			
			tbxObservaciones_boca.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_boca());

			

			calcularCoordenadas(false);

			// Mostramos la vista //
			// tbxAccion.setText("modificar");
			infoPacientes.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
					.getCodigo_historia());
			// accionForm(true, tbxAccion.getText());
			inicializarVista(tipo_historia);
			// FormularioUtil.deshabilitarComponentes(rowCita_anterior,true,
			// new
			// String[] {});
			btnImprimir.setVisible(true);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a = (Hisc_deteccion_alt_menor_5a_10a) obj;
		try {
			int result = getServiceLocator()
					.getHisc_deteccion_alt_menor_5a_10aService().eliminar(
							hisc_deteccion_alt_menor_5a_10a);
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

	// metodo seleccionar causas externas
	public void seleccion(Listbox listbox, int entero,
			Component[] abstractComponents) {
		try {
			String num = entero + "";
			for (Component abstractComponent : abstractComponents) {
				if (listbox.getSelectedItem().getValue().equals(num)) {
					abstractComponent.setVisible(true);
				} else {
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
					}
					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	// METODO SELECIONAR DX
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

	// METODO BUSCAR DX
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
			
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}

	}

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A);
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A);
			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A);
			Integer meses = Integer.valueOf(Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha),
					"2", false));
			macroFiguraHumana.iniciarMacroFiguraHumana(this, paciente, meses);

			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a = new Hisc_deteccion_alt_menor_5a_10a();
				hisc_deteccion_alt_menor_5a_10a.setCodigo_empresa(empresa
						.getCodigo_empresa());
				hisc_deteccion_alt_menor_5a_10a.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				hisc_deteccion_alt_menor_5a_10a.setIdentificacion(admision
						.getNro_identificacion());
				hisc_deteccion_alt_menor_5a_10a.setNro_ingreso(admision
						.getNro_ingreso());

				hisc_deteccion_alt_menor_5a_10a = getServiceLocator()
						.getHisc_deteccion_alt_menor_5a_10aService().consultar(
								hisc_deteccion_alt_menor_5a_10a);
				if (hisc_deteccion_alt_menor_5a_10a != null) {
					accionForm(OpcionesFormulario.MOSTRAR,
							hisc_deteccion_alt_menor_5a_10a);
					infoPacientes
							.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
									.getCodigo_historia());
				} else {
					groupboxEditar.setVisible(false);
					throw new Exception(
							"NO hay una historia clinica relacionada a este paciente admitido");
				}
			} else {
				if (opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
					accionForm(OpcionesFormulario.REGISTRAR, null);
					btnCancelar.setVisible(false);
				} else {
					accionForm(OpcionesFormulario.CONSULTAR, null);
					btnCancelar.setVisible(true);
				}
			}
		}
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowCita_anterior.setVisible(false);
			rowPerinatales.setVisible(true);
			rowPatologicos.setVisible(true);
			rowAntecedentesFamiliares.setVisible(true);
			rowAnalisis.setVisible(true);
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			rowCita_anterior.setVisible(true);
			rowPerinatales.setVisible(false);
			rowPatologicos.setVisible(false);
			rowAntecedentesFamiliares.setVisible(false);
			rowAnalisis.setVisible(false);
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {
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

							List<Hisc_deteccion_alt_menor_5a_10a> listado_historias = getServiceLocator()
									.getHisc_deteccion_alt_menor_5a_10aService()
									.listar(parametros);

							if (!listado_historias.isEmpty()) {
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								cargarInformacion_historia_anterior(listado_historias
										.get(0));
								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica de control/evolucion");
								admision.setPrimera_vez("N");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando historia clinica por primera vez");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
								admision.setPrimera_vez("S");
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								Hisc_deteccion_alt_menor_5a_10a hisc_det = new Hisc_deteccion_alt_menor_5a_10a();
								hisc_det.setCodigo_empresa(empresa
										.getCodigo_empresa());
								hisc_det.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								hisc_det.setCodigo_historia(codigo_historia_anterior);

								hisc_det = getServiceLocator()
										.getHisc_deteccion_alt_menor_5a_10aService()
										.consultar(hisc_det);

								if (hisc_det != null) {
									cargarInformacion_historia_anterior(hisc_det);
								}
							}
						}

					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
		Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a = (Hisc_deteccion_alt_menor_5a_10a) historia_anterior;
		Utilidades.seleccionarRadio(rdbCa_Diagnostico_crecimiento,
				hisc_deteccion_alt_menor_5a_10a.getDiagnostico_crecimiento());
		Utilidades.seleccionarRadio(rdbCa_Diagnostico_desarrollo,
				hisc_deteccion_alt_menor_5a_10a.getDiagnostico_desarrollo());
		// Vacunales
		Utilidades.seleccionarRadio(rdbBcg,
				hisc_deteccion_alt_menor_5a_10a.getBcg());
		Utilidades.seleccionarRadio(rdbVop_2,
				hisc_deteccion_alt_menor_5a_10a.getVop_2());
		Utilidades.seleccionarRadio(rdbVop_3,
				hisc_deteccion_alt_menor_5a_10a.getVop_3());
		Utilidades.seleccionarRadio(rdbNeumococo_3,
				hisc_deteccion_alt_menor_5a_10a.getNeumococo_3());
		Utilidades.seleccionarRadio(rdbHepatitis_b_rn,
				hisc_deteccion_alt_menor_5a_10a.getHepatitis_b_rn());
		Utilidades.seleccionarRadio(rdbPenta_2,
				hisc_deteccion_alt_menor_5a_10a.getPenta_2());
		Utilidades.seleccionarRadio(rdbPenta_3,
				hisc_deteccion_alt_menor_5a_10a.getPenta_3());
		Utilidades.seleccionarRadio(rdbTriple_viral,
				hisc_deteccion_alt_menor_5a_10a.getTriple_viral());
		Utilidades.seleccionarRadio(rdbVop_1,
				hisc_deteccion_alt_menor_5a_10a.getVop_1());
		Utilidades.seleccionarRadio(rdbNeumococo_2,
				hisc_deteccion_alt_menor_5a_10a.getNeumococo_2());
		Utilidades.seleccionarRadio(rdbInfluenza_1,
				hisc_deteccion_alt_menor_5a_10a.getInfluenza_1());
		Utilidades.seleccionarRadio(rdbFiebre_amarilla,
				hisc_deteccion_alt_menor_5a_10a.getFiebre_amarilla());
		Utilidades.seleccionarRadio(rdbPenta_1,
				hisc_deteccion_alt_menor_5a_10a.getPenta_1());
		Utilidades.seleccionarRadio(rdbRotavirus_2,
				hisc_deteccion_alt_menor_5a_10a.getRotavirus_2());
		Utilidades.seleccionarRadio(rdbInfluenza_2,
				hisc_deteccion_alt_menor_5a_10a.getInfluenza_2());
		Utilidades.seleccionarRadio(rdbHepatitis_b,
				hisc_deteccion_alt_menor_5a_10a.getHepatitis_b());
		Utilidades.seleccionarRadio(rdbNeumococo_1,
				hisc_deteccion_alt_menor_5a_10a.getNeumococo_1());
		Utilidades.seleccionarRadio(rdbDpt_r1,
				hisc_deteccion_alt_menor_5a_10a.getDpt_r1());
		Utilidades.seleccionarRadio(rdbRotavirus_1,
				hisc_deteccion_alt_menor_5a_10a.getRotavirus_1());
		Utilidades.seleccionarRadio(rdbHepatitis_a,
				hisc_deteccion_alt_menor_5a_10a.getHepatitis_a());
		Utilidades.seleccionarRadio(rdbVop_r1,
				hisc_deteccion_alt_menor_5a_10a.getVop_r1());
		Utilidades.seleccionarRadio(rdbDpt_r2,
				hisc_deteccion_alt_menor_5a_10a.getDpt_r2());
		Utilidades.seleccionarRadio(rdbVop_22,
				hisc_deteccion_alt_menor_5a_10a.getVop_22());
		Utilidades.seleccionarRadio(rdbTriple_viral_refuerzo,
				hisc_deteccion_alt_menor_5a_10a.getTriple_viral_refuerzo());

		// perinatales
		ibxNum_embarazos_g.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNum_embarazos_g());
		ibxNum_partos_p.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNum_partos_p());
		ibxNum_abortos_a.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNum_abortos_a());
		ibxNum_cesarias_c.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNum_cesarias_c());
		ibxNum_mortinatos_v.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNum_mortinatos_v());
		Utilidades.seleccionarListitem(lbxVdlr,
				hisc_deteccion_alt_menor_5a_10a.getVdrl_materno());
		ibxEdad_madre_al_nacimiento.setValue(hisc_deteccion_alt_menor_5a_10a
				.getEdad_madre_al_nacimiento());
		Utilidades.seleccionarListitem(lbxSem_gestacion,
				hisc_deteccion_alt_menor_5a_10a.getSem_gestacion().toString());
		dbxTsh.setValue(ConvertidorDatosUtil
				.convertirDato(hisc_deteccion_alt_menor_5a_10a.getTsh()));
		Utilidades.seleccionarRadio(rdbParto,
				hisc_deteccion_alt_menor_5a_10a.getParto());
		Utilidades.seleccionarRadio(rdbUnico_multiple,
				hisc_deteccion_alt_menor_5a_10a.getUnico_multiple());
		Utilidades.seleccionarRadio(rdbInstitucional,
				hisc_deteccion_alt_menor_5a_10a.getInstitucional());
		Utilidades.seleccionarRadio(rdbRiesgo,
				hisc_deteccion_alt_menor_5a_10a.getRiesgo());
		Utilidades.seleccionarRadio(rdbControl_prenatal,
				hisc_deteccion_alt_menor_5a_10a.getControl_prenatal());
		Utilidades.seleccionarListitem(lbxHemoclasificacion,
				hisc_deteccion_alt_menor_5a_10a.getHemoclasificacion());
		tbxComplicaciones_embarazo_parto
				.setValue(hisc_deteccion_alt_menor_5a_10a
						.getComplicaciones_embarazo_parto());
		ibxPeso_al_nacer.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_deteccion_alt_menor_5a_10a
						.getPeso_al_nacer()));
		ibxTalla_al_nacer.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_deteccion_alt_menor_5a_10a
						.getTalla_al_nacer()));
		ibxApgar_minutos.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_deteccion_alt_menor_5a_10a
						.getApgar_minutos()));
		ibxApgar_5_minutos.setValue(ConvertidorDatosUtil
				.convertirDatot(hisc_deteccion_alt_menor_5a_10a
						.getApgar_5_minutos()));
		Utilidades.seleccionarRadio(rdbReanimacion,
				hisc_deteccion_alt_menor_5a_10a.getReanimacion());
		Utilidades.seleccionarRadio(rdbHospitalizacion_neonatal,
				hisc_deteccion_alt_menor_5a_10a.getHospitalizacion_neonatal());
		tbxObservaciones_3_1.setValue(hisc_deteccion_alt_menor_5a_10a
				.getObservaciones_3_1());

		// Antecedentes Familiares
		ibxNum_hermanos_vivos.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNum_hermanos_vivos());
		ibxNum_hermanos_desnutridos_menor_5anos
				.setValue(hisc_deteccion_alt_menor_5a_10a
						.getNum_hermanos_desnutridos_menor_5anos());
		ibxNum_hermanos_muertos_menor_5anos
				.setValue(hisc_deteccion_alt_menor_5a_10a
						.getNum_hermanos_muertos_menor_5anos());
		tbxNum_hermanos_muertos_menor_5anos_causa
				.setValue(hisc_deteccion_alt_menor_5a_10a
						.getNum_hermanos_muertos_menor_5anos_causa());
		Utilidades.seleccionarRadio(rdbSon_parientes_los_padres,
				hisc_deteccion_alt_menor_5a_10a.getSon_parientes_los_padres());
		Utilidades.seleccionarRadio(rdbFamilia_con_problema_mental_o_fisico,
				hisc_deteccion_alt_menor_5a_10a
						.getFamilia_con_problema_mental_o_fisico());
		tbxPaterno_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
				.getPaterno_medicos());
		tbxPaterno_alergico.setValue(hisc_deteccion_alt_menor_5a_10a
				.getPaterno_alergico());
		ibxPaterno_talla.setValue(hisc_deteccion_alt_menor_5a_10a
				.getPaterno_talla());
		tbxMaterno_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
				.getMaterno_medicos());
		tbxMaterno_alergico.setValue(hisc_deteccion_alt_menor_5a_10a
				.getMaterno_alergico());
		ibxMaterno_talla.setValue(hisc_deteccion_alt_menor_5a_10a
				.getMaterno_talla());
		tbxOtros_4.setValue(hisc_deteccion_alt_menor_5a_10a.getOtros_4());

		// Datos del paciente
		tbxNombre_del_padre.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNombre_del_padre());
		ibxEdad_padre.setValue(hisc_deteccion_alt_menor_5a_10a.getEdad_padre());
		tbxNombre_de_la_madre.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNombre_de_la_madre());
		ibxEdad_madre.setValue(hisc_deteccion_alt_menor_5a_10a.getEdad_madre());
		codigo_historia_anterior = hisc_deteccion_alt_menor_5a_10a.getCodigo_historia();
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a = (Hisc_deteccion_alt_menor_5a_10a) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });

			if (hisc_deteccion_alt_menor_5a_10a.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			if (hisc_deteccion_alt_menor_5a_10a.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clinica de control/evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(true);
		}

		codigo_historia_anterior = hisc_deteccion_alt_menor_5a_10a
				.getCodigo_historia_anterior();
		tipo_historia = hisc_deteccion_alt_menor_5a_10a.getTipo_historia();
	}

	public void iniciarSemEmbarazo() {
		lbxSem_gestacion.appendItem("-- seleccione --", "-1");
		for (int i = 1; i <= 42; i++) {
			String sem = String.valueOf(i);
			lbxSem_gestacion.appendItem(sem, sem);
		}
	}

	// DATOS CURVAS CRECIMIENTO.
	public void calcularCoordenadas(Boolean mostrarAlerta) {
		calcularImcEdad(mostrarAlerta);
		calcularTallaEdad(mostrarAlerta);
	}

	private List<RespuestaInt> cargarRespuestas(
			List<Coordenadas_graficas> coordenadas,
			Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a) {
		List<RespuestaInt> respuestas = new ArrayList<RespuestaInt>();
		for (Coordenadas_graficas cg : coordenadas) {
			RespuestaInt r = cargarResuestaInt(cg);
			if (hisc_deteccion_alt_menor_5a_10a != null
					&& cg.getCodigo_historia().equals(
							hisc_deteccion_alt_menor_5a_10a
									.getCodigo_historia())) {
				r.setActual(true);
			}
			respuestas.add(r);
		}
		return respuestas;
	}

	private RespuestaInt cargarResuestaInt(Coordenadas_graficas cg) {
		RespuestaInt r = new RespuestaInt();
		r.setValor(ConvertidorDatosUtil.convertirDato(cg.getValor()));
		r.setX(ConvertidorDatosUtil.convertirDato(cg.getX()));
		r.setY(ConvertidorDatosUtil.convertirDato(cg.getY()));
		return r;
	}

	// METODOS PARA VISTA
	public void calcularTallaEdad(Boolean alerta) {
		double talla;
		if (dbxTalla_cm.getValue() == null) {
			dbxTalla_cm.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxTalla_cm.setStyle("background-color:white");
			talla = dbxTalla_cm.getValue();
		}

		if (dbxTalla_cm.getValue() != null && tallaValida(talla)) {
			coordenadaTallaEdad = calcularTallaEdad(talla, fecha);
			dbxT_e_de.setValue(coordenadaTallaEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(dbxT_e_de,
						paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_TALLA_EDAD);
			}
			btnCalcularTallaEdad.setDisabled(false);
		} else {
			if(alerta){
			if (!(tallaValida(talla))) {
				Messagebox.show(
						"La talla está por fuera del rango [100-200]!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			}
		}
	}

	public void calcularImcEdad(Boolean alerta) {

		double talla;
		double peso;

		if (dbxTalla_cm.getValue() == null) {
			dbxTalla_cm.setStyle("background-color:#F6BBBE");
			if(alerta){
				Messagebox
						.show("Debe digitar la talla del paciente para realizar este cálculo!!",
								"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxTalla_cm.setStyle("background-color:white");
			talla = dbxTalla_cm.getValue();
		}

		if (dbxPeso_grs.getValue() == null) {
			dbxPeso_grs.setStyle("background-color:#F6BBBE");
			if(alerta){
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			return;
		} else {
			dbxPeso_grs.setStyle("background-color:white");
			peso = dbxPeso_grs.getValue();
		}

		if (dbxPeso_grs.getValue() != null && dbxPeso_grs.getValue() != null
				&& tallaValida(talla)) {
			double imc = CalculadorUtil.calcularIMC(peso, talla / 100);
			coordenadaIMCEdad = calcularImcEdad(imc, fecha);
			dbxImc_e_de.setValue(coordenadaIMCEdad.getValor());
			if (alerta) {
				UtilidadSignosVitales.mostrarAlertasSignosVitalesDE(
						dbxImc_e_de, paciente.getFecha_nacimiento(),
						UtilidadSignosVitales.DE_IMC_EDAD);
			}
			btnCalcularImcEdad.setDisabled(false);
		} else {
			if(alerta){
			if (!(tallaValida(talla))) {
				Messagebox.show(
						"La talla está por fuera del rango [100-200]!!",
						"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			}
			}
		}
	}

	public boolean tallaValida(Double talla) {
		Double min = 100.0;
		Double max = 200.0;
		return (talla >= min && talla <= max);
	}

	private RespuestaInt calcularTallaEdad(double talla, Timestamp fecha) {
		return GraficasCalculadorUtils.calcularTallaEdad5$19Anios(sexo, talla,
				fecha);
	}

	private RespuestaInt calcularImcEdad(double imc, Timestamp fecha) {
		return GraficasCalculadorUtils.calcularIMCEdad5$19Anios(sexo, imc,
				fecha);
	}

	/* Graficamos */
	public void graficarTallaEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.T_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_5_ANOS_10_ANOS);
		List<Coordenadas_graficas> tes = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasTE = cargarRespuestas(tes, hisc_deteccion_alt_menor_5a_10a);

		List coordenadas_te = coordenadasTE;
		if (!verificarActivo(coordenadasTE) && coordenadaTallaEdad != null) {
			coordenadas_te.add(coordenadaTallaEdad);
		}
		imprimirArreglo(coordenadas_te);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_5_19,
				coordenadas_te, this, sexo);
	}

	public void graficarIMCEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.IMC_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_5_ANOS_10_ANOS);
		List<Coordenadas_graficas> imces = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasIE = cargarRespuestas(imces, hisc_deteccion_alt_menor_5a_10a);

		List coordenadas_imce = coordenadasIE;
		if (!verificarActivo(coordenadasIE) && coordenadaIMCEdad != null) {
			coordenadas_imce.add(coordenadaIMCEdad);
		}

		imprimirArreglo(coordenadas_imce);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.IMC_EDAD_5_A_19,
				coordenadas_imce, this, sexo);
	}

	public void mostrarTablaTallaEdad() {
		GraficasCalculadorUtils
				.mostrarTabla(
						GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_5_19,
						this, sexo);
	};

	public void mostrarTablaIMCEdad() {
		GraficasCalculadorUtils
				.mostrarTabla(
						GraficasCalculadorUtils.TipoGrafica.IMC_EDAD_5_A_19,
						this, sexo);
	};

	public void imprimirArreglo(List<RespuestaInt> arreglo) {
//		for (RespuestaInt ri : arreglo) {
//			//log.info(">>>>>>>>>>>>" + ri.isActual());
//		}
	}

	public boolean verificarActivo(List<RespuestaInt> arreglo) {
		boolean actual = false;
		for (RespuestaInt ri : arreglo) {
			if (ri.isActual()) {
				actual = true;
				break;
			}
		}
		return actual;
	}

	// FIN CURVAS CRECIMIENTO

	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			UtilidadSignosVitales.calcularIMC(dbxP, dbxT, dbxI,
					UtilidadSignosVitales.TALLA_CENTIMETROS,
					UtilidadSignosVitales.PESO_KILOS);
		} catch (WrongValueException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
			e.printStackTrace();
		}
	}

	private void cargarImpresionDiagnostica(
			Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a)
			throws Exception {

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();

		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica
				.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
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

		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", nro_ingreso_admision);
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

		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", nro_ingreso_admision);
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
		orden_servicioAction = (Orden_servicio_internoAction) Executions
				.createComponents("/pages/orden_servicio_interno.zul", null,
						parametros);
		orden_servicioAction.inicializar(this);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
		// cargo_farmacologico = true;
		// }

	}

	public void notificarAlerta(Component comp) {
		MensajesUtil.notificarAlerta("Remitir", comp);
	}

	private void cargarAnexo9_remision(
			Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_deteccion_alt_menor_5a_10a
				.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
	}

	private void cargarRemisionInterna(
			Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a)
			throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_deteccion_alt_menor_5a_10a
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_deteccion_alt_menor_5a_10a
				.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null)
			remisiones_externasAction.detach();
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", nro_ingreso_admision);
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

		parametros.put("opcion_formulario_historia",
				opciones_via_ingreso.toString());
		
		remisiones_externasAction = (Remisiones_externasAction) Executions
				.createComponents("/pages/remisiones_externas.zul", null,
						parametros);
		remisiones_externasAction.inicializar(this);
		divModuloRemisiones_externas.appendChild(remisiones_externasAction);

	}

	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :\n");
		informacion_clinica.append("\t").append(tbxMotivo_consulta.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :\n");
		if (tbxGestion_actual.getValue() != null
				&& !tbxGestion_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxGestion_actual.getValue()).append("\n");
		} else if (tbxGestion_actual.getValue() != null
				&& !tbxGestion_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxGestion_actual.getValue()).append("\n");
		}

		informacion_clinica.append("\n");

		informacion_clinica.append("- SIGNOS VITALES\n");
		informacion_clinica.append("\tFrecuencia cardíaca(min): ")
				.append(dbxFc_min.getValue() + ("\n")).append("\t")
				.append("Frecuencia respiratoria(min): ")
				.append(dbxFr_min.getValue() + ("\n"));
		informacion_clinica.append("\tTemperatura: ")
				.append(dbxTemperatura_gc.getValue() + ("\n"))
				.append(" \tPeso(kg): ")
				.append(dbxPeso_grs.getValue() + ("\n"))
				.append(" \tTalla(Cm): ")
				.append(dbxTalla_cm.getValue() + ("\n"))
				.append(" \tI.M.C.(kg/m2): ")
				.append(dbxImc.getValue() + ("\n"));
		informacion_clinica.append("\n").append("\n");

		informacion_clinica.append("- DIAGNOSTICOS\n");
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

	public void cargarSignosVitalesEnfermera() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setCodigo_empresa(codigo_empresa);
		enfermera_signos.setCodigo_sucursal(codigo_sucursal);
		enfermera_signos.setNro_ingreso(admision.getNro_ingreso());
		enfermera_signos
				.setNro_identificacion(admision.getNro_identificacion());
		enfermera_signos = getServiceLocator().getServicio(
				Enfermera_signosService.class).consultar(enfermera_signos);
		if (enfermera_signos != null) {

			dbxPeso_grs.setValue(enfermera_signos.getPeso());
			dbxTalla_cm.setValue(enfermera_signos.getTalla());
			dbxFc_min.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxFr_min.setValue(enfermera_signos.getFrecuencia_respiratoria());
			dbxTemperatura_gc.setValue(enfermera_signos.getTemperatura());
			dbxImc.setValue(enfermera_signos.getImc());

			calcularIMC(dbxPeso_grs, dbxTalla_cm, dbxImc);
			alarmaExamenFisicoFc();
			alarmaExamenFisicoRespiratoria();
			alarmaExamenFisicoTemperatura();

		}
	}

	public void imprimir() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		Long nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia != null) {
			String parametros_pagina="?codigo_empresa="+codigo_empresa;
			   parametros_pagina+="&codigo_sucursal="+codigo_sucursal;
			   parametros_pagina+="&codigo_historia="+nro_historia;
			   parametros_pagina+="&nro_ingreso="+admision.getNro_ingreso();
			   parametros_pagina+="&nro_identificacion="+admision.getNro_identificacion();
			Clients.evalJavaScript("window.open(\""+request.getContextPath()+"/pages/reports/hisc_deteccion_alt_menor_5a_10a_reporte.zul"+parametros_pagina+"\", \"\",\"width=910, top=0, left=0\");");
		}
	}
}