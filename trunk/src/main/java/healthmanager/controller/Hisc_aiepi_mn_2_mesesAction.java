/*
 * hisc_aiepi_mn_2_mesesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Cuadros_aiepi_resultados;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Hisc_aiepi_mn_2_meses;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Cuadros_aiepi_resultadosService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Hisc_aiepi_mn_2_mesesService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.CuadroAIEPIMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.CuadroAiepiIMG;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_aiepi_mn_2_mesesAction extends HistoriaClinicaModel implements
		IHistoria_generica, CuadroAiepiIMG {

	private static Logger log = Logger
			.getLogger(Hisc_aiepi_mn_2_mesesAction.class);

	private Hisc_aiepi_mn_2_mesesService hisc_aiepi_mn_2_mesesService;

	// Componentes //
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	//private Agudeza_visualAction agudeza_visualAction;

	@View
	private Listbox lbxTipoHistoria;
	@View
	private Listbox lbxParameter;
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
	private Textbox tbxId_acompanante;
	@View
	private Textbox tbxOtro_acompanante;
	@View
	private Textbox tbxNombres_acompanante;
	@View
	private Textbox tbxApellidos_acompanante;
	@View
	private Listbox lbxParentesco_acompanante;
	// @View private Textbox tbxDireccion_acompanante;
	@View
	private Textbox tbxTelefono_acompanante;
	@View
	private Textbox tbxNombres_padre;
	@View
	private Textbox tbxApellidos_padre;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxOcupacion_padre;
	@View
	private Textbox tbxInfoOcupacion_padre;
	@View
	private Toolbarbutton btnLimpiarOcupacion_padre;

	@View
	private Intbox ibxEdad_padre;
	@View
	private Textbox tbxNombres_madre;
	@View
	private Textbox tbxApellidos_madre;
	@View
	private Textbox tbxId_madre;
	@View
	private Textbox tbxId_padre;
	@View
	private Textbox tbxTelefono_madre;
	@View
	private Textbox tbxTelefono_padre;
	
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxOcupacion_madre;
	@View
	private Textbox tbxInfoOcupacion_madre;
	@View
	private Toolbarbutton btnLimpiarOcupacion_madre;

	@View
	private Intbox ibxEdad_madre;
	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Textbox tbxEnfermedad_actual;
	@View
	private Radiogroup rdbNino_registrado;
	@View
	private Textbox tbxAnt_pat_como_fue_embarazo;
	@View
	private Intbox ibxAnt_pat_cuanto_duro_embarazo;
	@View
	private Textbox tbxAnt_pat_hemoclasificacion;
	@View
	private Textbox tbxAnt_pat_como_fue_parto;
	@View
	private Doublebox dbxAnt_pat_cuanto_peso_nacer;
	@View
	private Doublebox dbxAnt_pat_cuanto_midio_nacer;
	@View
	private Textbox tbxAnt_pat_presento_problema_nacer;
	@View
	private Textbox tbxAnt_pat_enfermedades;
	@View
	private Doublebox dbxSignos_vitales_peso;
	@View
	private Doublebox dbxSignos_vitales_talla;
	@View
	private Doublebox dbxSignos_vitales_pc;
	@View
	private Doublebox dbxSignos_vitales_fc;
	@View
	private Doublebox dbxSignos_vitales_fr;
	@View
	private Doublebox dbxSignos_vitales_taxilar;
	@View
	private Doublebox dbxSignos_vitales_oximetria;
	@View
	private Doublebox dbxSignos_vitales_imc;
	@View
	private Radiogroup rdbSignos_vitales_sintomaticos_respiratorio;
	@View
	private Radiogroup rdbSignos_vitales_sintomaticos_piel;
	@View
	private Doublebox dbxSignos_vitales_p_e_de;
	@View
	private Doublebox dbxSignos_vitales_t_e_de;
	@View
	private Doublebox dbxSignos_vitales_p_t_de;
	@View
	private Doublebox dbxSignos_vitales_pc_e_de;
	@View
	private Radiogroup rdbTiene_nino_diarrea;
	@View
	private Checkbox chbVacunales_madre_antitetanica1;
	@View
	private Checkbox chbVacunales_madre_antitetanica2;
	@View
	private Textbox tbxVacunales_madre_otras_vacunas;
	@View
	private Datebox dtbxVacunales_madre_proxima_vacuna;
	@View
	private Checkbox chbVacunales_nino_bcg;
	@View
	private Checkbox chbVacunales_nino_hepb1;
	@View
	private Datebox dtbxVacunales_nino_proxima_vacuna;
	@View
	private Datebox dtbxVolver_consulta_control;
	@View
	private Datebox dtbxVolver_consulta_nino_sano;
	@View
	private Groupbox gbxCuadro2;

	// @View private Textbox tbxPrestador;
	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI1;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI2;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI3;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI4;

	// Modulo Farmacologico y Autorizaciones
	@View
	private Div divModuloFarmacologico;
	@View
	private Div divModuloOrdenamiento;
	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;
	@View
	private Toolbarbutton btGuardar;
	@View
	private Toolbarbutton btnCancelar;

	@View
	private Button btnCalcularPesoEdad;
	@View
	private Button btnCalcularPerimetroCefalicoEdad;
	@View
	private Button btnCalcularPesoTalla;
	@View
	private Button btnCalcularTallaEdad;

	@View
	private Div divModuloRemisiones_externas;

	@View
	private Textbox tbxObservaciones_cuadro1;
	@View
	private Textbox tbxObservaciones_cuadro2;
	@View
	private Textbox tbxObservaciones_cuadro3;
	@View
	private Textbox tbxObservaciones_cuadro4;

	
	private Remisiones_externasAction remisiones_externasAction;

	private Paciente paciente;
	private ESexo sexo;
	private Timestamp fecha;
	private String tipo_historia;
	private Long codigo_historia_anterior;
	private Admision admision;
	private Citas cita;
	private Opciones_via_ingreso opciones_via_ingreso;
	
	@View
	private Hbox hbxLocalizacion;
	@View
	private Label lbActual_presenta;
	@View
	private Label lbCaracteristicas_Dolor;
	@View
	private Label lbEnfermedad_actual;
	@View
	private Label lbEvolucion_cuadro;
	@View
	private Label lbFecha_inicio;
	@View
	private Label lbIntensidad;
	@View
	private Label lbIrradiacion;
	@View
	private Label lbLocalizacion;
	@View
	private Label lbMan_for_inicio;
	@View
	private Label lbPrimera_presentacion;
	@View
	private Label lbRelacionado;
	@View
	private Label lbsintomas_asociados;
	@View
	private Label lbTratamiento_recibido;
	@View
	private Label lbVeces_repetido;
	@View
	private Listbox lbxIntensidad;
	@View
	private Listbox lbxPrimera_presentacion;
	@View
	private Radiogroup rdbseleccion;
	@View
	private Row row10;
	@View
	private Row row11;
	@View
	private Row row12;
	@View
	private Row row13;
	@View
	private Row row14;
	@View
	private Row row2;
	@View
	private Row row3;
	@View
	private Row row4;
	@View
	private Row row5;
	@View
	private Row row6;
	@View
	private Row row7;
	@View
	private Row row8;
	@View
	private Row row9;
	@View
	private Spinner spinnerVeces_repetido;
	@View
	private Textbox tbxActualmente_presenta;
	@View
	private Textbox tbxCaracteristicas_dolor;
	@View
	private Textbox tbxEvolucion;
	@View
	private Textbox tbxFecha_inicio;
	@View
	private Textbox tbxIrradiacion;
	@View
	private Textbox tbxlocalizacion;
	@View
	private Textbox tbxManera_form_inicio;
	@View
	private Textbox tbxParrafo_enfermedad_actual;
	@View
	private Textbox tbxRelacionado;
	@View
	private Textbox tbxSintomas_asociados;
	@View
	private Textbox tbxTratamiento_recibido;
	@View
	private Checkbox cbxAbdomen;
	@View
	private Checkbox cbxCabeza;
	@View
	private Checkbox cbxCuello;
	@View
	private Checkbox cbxToraz;
	@View
	private Toolbarbutton btImprimir;
	
	private final String[] idsExcluyentes = { "tbxAccion", "tbxValue",
			"lbxParameter",
			"toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio","divModuloRemisiones_externas" };

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });
			toolbarbuttonTipo_historia.setLabel("Mostrando Historia Clinica");
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			toolbarbuttonTipo_historia.setLabel("Modificando Historia Clinica");
			((Button) getFellow("btGuardar")).setVisible(true);
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		if (cita != null) {
			tbxId_acompanante.setValue(cita.getCedula_acomp());
			tbxNombres_acompanante.setValue(cita.getAcompaniante());
			tbxApellidos_acompanante.setValue(cita.getApellidos_acomp());
			tbxTelefono_acompanante.setValue(cita.getTel_acompaniante());
			Utilidades.seleccionarListitem(lbxParentesco_acompanante, cita.getRelacion());
			onSeleccionarRelacionAcompaniante();
			tbxOtro_acompanante.setValue(cita.getOtro_acompaniante());			
		}
		
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {
					@Override
					public void ejecutarProceso() {
						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							toolbarbuttonTipo_historia
									.setLabel("Creando historia clinica");
						}
					}
				});
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
			admision.setPrimera_vez("S");
		} else {
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
			admision.setPrimera_vez("N");
		}
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
		Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = (Hisc_aiepi_mn_2_meses)historia_anterior;
		codigo_historia_anterior = hisc_aiepi_mn_2_meses
				.getCodigo_historia();
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			paciente = admision.getPaciente();
			sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO : ESexo.MASCULINO);
			fecha = paciente.getFecha_nacimiento();
			opciones_via_ingreso = (Opciones_via_ingreso) map	.get(IVias_ingreso.OPCION_VIA_INGRESO);
			cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);

			if(cita == null){
				cita = new Citas();
			}
			
			opciones_via_ingreso = (Opciones_via_ingreso) map	.get(IVias_ingreso.OPCION_VIA_INGRESO);
			macroRemision_interna.deshabilitarCheck(admision,	IVias_ingreso.HC_AIEPI_2_MESES);
		}
	}

	@Override
	public void init() {
		try {
			listarCombos();
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void deshabilitarCamposCuadros() {
		// TODO: Deshabilitar los checks automaticos
		String[] ch11 = { "06", "1" };
		String[] ch12 = { "07", "1" };
		String[] ch13 = { "09", "1" };
		macroCuadroAIEPI1.deshabilitarChecks(ch11, ch12, ch13);

		String[] ch31 = { "01", "1" };
		String[] ch32 = { "02", "1" };
		String[] ch33 = { "03", "1" };
		String[] ch34 = { "08", "3" };
		String[] ch35 = { "09", "3" };
		String[] ch36 = { "10", "3" };
		String[] ch37 = { "11", "3" };
		String[] ch38 = { "19", "5" };
		String[] ch39 = { "20", "5" };

		macroCuadroAIEPI3.getCheckbox("20", "5").setChecked(true);
		macroCuadroAIEPI3.evaluarCambio("20", "5");
		macroCuadroAIEPI3.deshabilitarChecks(ch31, ch32, ch33, ch34, ch35,
				ch36, ch37, ch38, ch39);

		String[] ch43 = { "03", "1" };
		macroCuadroAIEPI4.deshabilitarChecks(ch43);
	}

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {
			
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.HC_AIEPI_2_MESES);
			// TODO: Inicializar todos los cuadros aiepi
			macroCuadroAIEPI1.inicializarMacro(this, admision,
					IVias_ingreso.HC_AIEPI_2_MESES, "1");
			macroCuadroAIEPI2.inicializarMacro(this, admision,
					IVias_ingreso.HC_AIEPI_2_MESES, "2");
			macroCuadroAIEPI3.inicializarMacro(this, admision,
					IVias_ingreso.HC_AIEPI_2_MESES, "3");
			macroCuadroAIEPI4.inicializarMacro(this, admision,
					IVias_ingreso.HC_AIEPI_2_MESES, "4");
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.HC_AIEPI_2_MESES);
			deshabilitarCamposCuadros();
			
			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = new Hisc_aiepi_mn_2_meses();
				hisc_aiepi_mn_2_meses.setCodigo_empresa(empresa
						.getCodigo_empresa());
				hisc_aiepi_mn_2_meses.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				hisc_aiepi_mn_2_meses.setIdentificacion(admision
						.getNro_identificacion());
				hisc_aiepi_mn_2_meses.setNro_ingreso(admision.getNro_ingreso());

				hisc_aiepi_mn_2_meses = getServiceLocator().getServicio(
						Hisc_aiepi_mn_2_mesesService.class).consultar(
						hisc_aiepi_mn_2_meses);
				if (hisc_aiepi_mn_2_meses != null) {
					accionForm(OpcionesFormulario.MOSTRAR,
							hisc_aiepi_mn_2_meses);
					infoPacientes.setCodigo_historia(hisc_aiepi_mn_2_meses
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

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxParentesco_acompanante, true,
				getServiceLocator());
		parametrizarBandboxOcupacion(tbxOcupacion_padre,
				tbxInfoOcupacion_padre, btnLimpiarOcupacion_padre);
		parametrizarBandboxOcupacion(tbxOcupacion_madre,
				tbxInfoOcupacion_madre, btnLimpiarOcupacion_madre);
		Utilidades.listarElementoListbox(lbxIntensidad, true,
				getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
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
		seleccion(rdbseleccion);
		limpiarDatos();
		if (admision != null) {
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
			calcularProximaVacuna();
			cambioCuadro("3");
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloRemisiones();
		}
		btImprimir.setVisible(false);
	}

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		 infoPacientes.validarInformacionPaciente();
		FormularioUtil.validarCamposObligatorios(tbxNombres_acompanante,
				tbxApellidos_acompanante, tbxMotivo_consulta,
				tbxEnfermedad_actual, dbxSignos_vitales_peso,
				dbxSignos_vitales_talla, dbxSignos_vitales_pc,
				dbxSignos_vitales_fc, dbxSignos_vitales_fr,
				dbxSignos_vitales_taxilar, lbxParentesco_acompanante);

		boolean valida = receta_ripsAction.validarFormExterno();
		 String mensaje = "";
		if(valida){
			valida =(macroCuadroAIEPI4.cantCheckeadosEstado("1") > 0 ||macroCuadroAIEPI4.cantCheckeadosEstado("2") > 0 ||macroCuadroAIEPI4.cantCheckeadosEstado("3") > 0 ||macroCuadroAIEPI4.cantCheckeadosEstado("4") > 0||macroCuadroAIEPI4.cantCheckeadosEstado("5") > 0);
			if(!valida){
				mensaje="Debe escoger por lo menos una opcion del cuadro Nro. 3\n(EVALUAR Y CLASIFICAR EL DESARROLLO DE TODOS LOS MENORES DE 2 MESES) ";
			}
		}
		if (valida){
			valida = orden_servicioAction.validarFormExterno();
			if(!valida){
				mensaje = "Error al validar las ordenes de servicio";
			}
		}

		if (valida){
			valida = remisiones_externasAction.validarRemision();
			if(!valida){
				mensaje = "Error al validar las remisiones externas";
			}
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

			parameters.put("limite_paginado", "limit 25 offset 0");
			List<Hisc_aiepi_mn_2_meses> lista_datos = hisc_aiepi_mn_2_mesesService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses : lista_datos) {
				rowsResultado.appendChild(crearFilas(hisc_aiepi_mn_2_meses,
						this));
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

		final Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = (Hisc_aiepi_mn_2_meses) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_aiepi_mn_2_meses.getCodigo_historia()
				+ ""));
		fila.appendChild(new Label(hisc_aiepi_mn_2_meses.getIdentificacion()
				+ ""));
		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(hisc_aiepi_mn_2_meses.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(hisc_aiepi_mn_2_meses.getTipo_historia()
				.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ) ? "PRIMERA VEZ"
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
						mostrarDatos(hisc_aiepi_mn_2_meses);
						btGuardar.setVisible(false);
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
													eliminarDatos(hisc_aiepi_mn_2_meses);
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
				Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = getBean();

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("accion", tbxAccion.getText());
				datos.put("historia_clinica", hisc_aiepi_mn_2_meses);
				datos.put("admision", admision);

				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);
				
				Anexo9_entidad anexo9_entidad = remisiones_externasAction.obtenerAnexo9();

				datos.put("anexo9", anexo9_entidad);
				
				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();
				datos.put("impresion_diagnostica", impresion_diagnostica);
				receta_ripsAction
				.actualizarDiagnosticos(impresion_diagnostica);
				
				Map<String, Object> mapReceta = receta_ripsAction
						.obtenerDatos();
				Map<String, Object> mapProcedimientos = orden_servicioAction
						.obtenerDatos();

				datos.put("receta_medica", mapReceta);
				datos.put("orden_servicio", mapProcedimientos);
				datos.put("cita_seleccionada",cita);
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());

				/*Agudeza_visual agudeza_visual = new Agudeza_visualAction()
						.obtenerAgudeza();
				datos.put("agudeza_visual", agudeza_visual);*/

				// TODO: Agregar a la lista los resultados de todos los cuadros.
				List<Cuadros_aiepi_resultados> lista = new ArrayList<Cuadros_aiepi_resultados>();
				lista.addAll(macroCuadroAIEPI1.getLista_resultados());
				lista.addAll(macroCuadroAIEPI2.getLista_resultados());
				lista.addAll(macroCuadroAIEPI3.getLista_resultados());
				lista.addAll(macroCuadroAIEPI4.getLista_resultados());
				datos.put("resultado_cuadro", lista);
				hisc_aiepi_mn_2_mesesService.guardarDatos(datos);

				tbxAccion.setValue("modificar");
				
				mostrarDatosAutorizacion(datos);
				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad
							.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(
							false);
				}
				
				infoPacientes.setCodigo_historia(hisc_aiepi_mn_2_meses
						.getCodigo_historia());

				Receta_rips receta_rips = (Receta_rips) mapReceta
						.get("receta_rips");
				if (receta_rips != null)
					receta_ripsAction.mostrarDatos(receta_rips, false);

				Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
						.get("orden_servicio");
				if (orden_servicio != null)
					orden_servicioAction.mostrarDatos(orden_servicio);

				orden_servicioAction.validarParaImpresion();
				receta_ripsAction.validarParaImpresion();
				
				actualizarAutorizaciones(admision,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);

				actualizarRemision(admision, getInformacionClinica(), this);
				btImprimir.setVisible(true);
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}
		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}

	}

	private Hisc_aiepi_mn_2_meses getBean() {
		Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = new Hisc_aiepi_mn_2_meses();
		hisc_aiepi_mn_2_meses.setCodigo_empresa(empresa
				.getCodigo_empresa());
		hisc_aiepi_mn_2_meses.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		hisc_aiepi_mn_2_meses.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		hisc_aiepi_mn_2_meses
				.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
		hisc_aiepi_mn_2_meses.setFecha_inicial(new Timestamp(
				infoPacientes.getFecha_inicial().getTime()));
		hisc_aiepi_mn_2_meses.setNro_ingreso(admision.getNro_ingreso());
		hisc_aiepi_mn_2_meses
				.setNombres_acompanante(tbxNombres_acompanante
						.getValue());
		hisc_aiepi_mn_2_meses
				.setApellidos_acompanante(tbxApellidos_acompanante
						.getValue());
		hisc_aiepi_mn_2_meses
				.setParentesco_acompanante(lbxParentesco_acompanante
						.getSelectedItem().getValue().toString());
		// hisc_aiepi_mn_2_meses.setDireccion_acompanante(tbxDireccion_acompanante.getValue());
		hisc_aiepi_mn_2_meses
				.setTelefono_acompanante(tbxTelefono_acompanante
						.getValue());
		hisc_aiepi_mn_2_meses.setNombres_padre(tbxNombres_padre
				.getValue());
		hisc_aiepi_mn_2_meses.setApellidos_padre(tbxApellidos_padre
				.getValue());
		// hisc_aiepi_mn_2_meses.setOcupacion_padre(lbxOcupacion_padre.getSelectedItem().getValue().toString());
		hisc_aiepi_mn_2_meses.setOcupacion_padre(tbxOcupacion_padre
				.getValue());
		hisc_aiepi_mn_2_meses
				.setEdad_padre((ibxEdad_padre.getValue() != null ? ibxEdad_padre
						.getValue() : 0));
		hisc_aiepi_mn_2_meses.setNombres_madre(tbxNombres_madre
				.getValue());
		hisc_aiepi_mn_2_meses.setApellidos_madre(tbxApellidos_madre
				.getValue());
		// hisc_aiepi_mn_2_meses.setOcupacion_madre(lbxOcupacion_madre.getSelectedItem().getValue().toString());
		hisc_aiepi_mn_2_meses.setOcupacion_madre(tbxOcupacion_madre
				.getValue());
		hisc_aiepi_mn_2_meses
				.setEdad_madre((ibxEdad_madre.getValue() != null ? ibxEdad_madre
						.getValue() : 0));
		hisc_aiepi_mn_2_meses.setMotivo_consulta(tbxMotivo_consulta
				.getValue());
		hisc_aiepi_mn_2_meses.setEnfermedad_actual(tbxEnfermedad_actual
				.getValue());
		hisc_aiepi_mn_2_meses.setNino_registrado(rdbNino_registrado
				.getSelectedItem().getValue().toString());
		hisc_aiepi_mn_2_meses
				.setAnt_pat_como_fue_embarazo(tbxAnt_pat_como_fue_embarazo
						.getValue());
		hisc_aiepi_mn_2_meses
				.setAnt_pat_cuanto_duro_embarazo((ibxAnt_pat_cuanto_duro_embarazo
						.getValue() != null ? ibxAnt_pat_cuanto_duro_embarazo
						.getValue() : 0));
		hisc_aiepi_mn_2_meses
				.setAnt_pat_hemoclasificacion(tbxAnt_pat_hemoclasificacion
						.getValue());
		hisc_aiepi_mn_2_meses
				.setAnt_pat_como_fue_parto(tbxAnt_pat_como_fue_parto
						.getValue());
		hisc_aiepi_mn_2_meses
				.setAnt_pat_cuanto_peso_nacer((dbxAnt_pat_cuanto_peso_nacer
						.getValue() != null ? dbxAnt_pat_cuanto_peso_nacer
						.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setAnt_pat_cuanto_midio_nacer((dbxAnt_pat_cuanto_midio_nacer
						.getValue() != null ? dbxAnt_pat_cuanto_midio_nacer
						.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setAnt_pat_presento_problema_nacer(tbxAnt_pat_presento_problema_nacer
						.getValue());
		hisc_aiepi_mn_2_meses
				.setAnt_pat_enfermedades(tbxAnt_pat_enfermedades
						.getValue());
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_peso(""
						+ (dbxSignos_vitales_peso.getValue() != null ? dbxSignos_vitales_peso
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_talla(""
						+ (dbxSignos_vitales_talla.getValue() != null ? dbxSignos_vitales_talla
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_pc(""
						+ (dbxSignos_vitales_pc.getValue() != null ? dbxSignos_vitales_pc
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_fc(""
						+ (dbxSignos_vitales_fc.getValue() != null ? dbxSignos_vitales_fc
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_fr(""
						+ (dbxSignos_vitales_fr.getValue() != null ? dbxSignos_vitales_fr
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_taxilar(""
						+ (dbxSignos_vitales_taxilar.getValue() != null ? dbxSignos_vitales_taxilar
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_oximetria(""
						+ (dbxSignos_vitales_oximetria.getValue() != null ? dbxSignos_vitales_oximetria
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_imc(""
						+ (dbxSignos_vitales_imc.getValue() != null ? dbxSignos_vitales_imc
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_sintomaticos_respiratorio(rdbSignos_vitales_sintomaticos_respiratorio
						.getSelectedItem().getValue().toString());
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_sintomaticos_piel(rdbSignos_vitales_sintomaticos_piel
						.getSelectedItem().getValue().toString());
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_p_e_de(""
						+ (dbxSignos_vitales_p_e_de.getValue() != null ? dbxSignos_vitales_p_e_de
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_t_e_de(""
						+ (dbxSignos_vitales_t_e_de.getValue() != null ? dbxSignos_vitales_t_e_de
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_p_t_de(""
						+ (dbxSignos_vitales_p_t_de.getValue() != null ? dbxSignos_vitales_p_t_de
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setSignos_vitales_pc_e_de(""
						+ (dbxSignos_vitales_pc_e_de.getValue() != null ? dbxSignos_vitales_pc_e_de
								.getValue() : 0.00));
		hisc_aiepi_mn_2_meses
				.setTiene_nino_diarrea(rdbTiene_nino_diarrea
						.getSelectedItem().getValue().toString());
		hisc_aiepi_mn_2_meses
				.setVacunales_madre_antitetanica1(chbVacunales_madre_antitetanica1
						.isChecked() ? "S" : "N");
		hisc_aiepi_mn_2_meses
				.setVacunales_madre_antitetanica2(chbVacunales_madre_antitetanica2
						.isChecked() ? "S" : "N");
		hisc_aiepi_mn_2_meses
				.setVacunales_madre_otras_vacunas(tbxVacunales_madre_otras_vacunas
						.getValue());
		hisc_aiepi_mn_2_meses
				.setVacunales_madre_proxima_vacuna(new Timestamp(
						dtbxVacunales_madre_proxima_vacuna.getValue()
								.getTime()));
		hisc_aiepi_mn_2_meses
				.setVacunales_nino_bcg(chbVacunales_nino_bcg
						.isChecked() ? "S" : "N");
		hisc_aiepi_mn_2_meses
				.setVacunales_nino_hepb1(chbVacunales_nino_hepb1
						.isChecked() ? "S" : "N");
		hisc_aiepi_mn_2_meses
				.setVacunales_nino_proxima_vacuna(new Timestamp(
						dtbxVacunales_nino_proxima_vacuna.getValue()
								.getTime()));
		hisc_aiepi_mn_2_meses.setVolver_consulta_control(new Timestamp(
				dtbxVolver_consulta_control.getValue().getTime()));
		hisc_aiepi_mn_2_meses
				.setVolver_consulta_nino_sano(new Timestamp(
						dtbxVolver_consulta_nino_sano.getValue()
								.getTime()));
		hisc_aiepi_mn_2_meses.setPrestador(usuarios.getCodigo());
		hisc_aiepi_mn_2_meses
				.setCodigo_historia_anterior(codigo_historia_anterior);
		hisc_aiepi_mn_2_meses.setTipo_historia(tipo_historia);
		hisc_aiepi_mn_2_meses.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_aiepi_mn_2_meses.setCreacion_user(usuarios.getCodigo()
				.toString());
		hisc_aiepi_mn_2_meses.setDelete_date(null);
		hisc_aiepi_mn_2_meses.setDelete_user(null);
		hisc_aiepi_mn_2_meses.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_aiepi_mn_2_meses.setUltimo_user(usuarios.getCodigo()
				.toString());
		
		hisc_aiepi_mn_2_meses.setObservaciones_cuadro1(tbxObservaciones_cuadro1.getValue());
		hisc_aiepi_mn_2_meses.setObservaciones_cuadro2(tbxObservaciones_cuadro2.getValue());
		hisc_aiepi_mn_2_meses.setObservaciones_cuadro3(tbxObservaciones_cuadro3.getValue());
		hisc_aiepi_mn_2_meses.setObservaciones_cuadro4(tbxObservaciones_cuadro4.getValue());
		
		hisc_aiepi_mn_2_meses.setAbdomen_enf(cbxAbdomen.isChecked());
		hisc_aiepi_mn_2_meses.setCabeza_enf(cbxCabeza.isChecked());
		hisc_aiepi_mn_2_meses.setCuello_enf(cbxCuello.isChecked());
		hisc_aiepi_mn_2_meses.setToraz_enf(cbxToraz.isChecked());
		hisc_aiepi_mn_2_meses.setIntensidad(lbxIntensidad.getSelectedItem().getValue().toString());
		hisc_aiepi_mn_2_meses.setActualmente_presenta(tbxActualmente_presenta.getValue());
		hisc_aiepi_mn_2_meses.setCaracteristicas_dolor(tbxCaracteristicas_dolor.getValue());
		hisc_aiepi_mn_2_meses.setEvolucion(tbxEvolucion.getValue());
		hisc_aiepi_mn_2_meses.setFecha_inicio(tbxFecha_inicio.getValue());
		hisc_aiepi_mn_2_meses.setIrradiacion(tbxIrradiacion.getValue());
		hisc_aiepi_mn_2_meses.setLocalizacion(tbxlocalizacion.getValue());
		hisc_aiepi_mn_2_meses.setManera_form_inicio(tbxManera_form_inicio.getValue());
		hisc_aiepi_mn_2_meses.setParrafo_enfermedad_actual(tbxParrafo_enfermedad_actual.getValue());
		hisc_aiepi_mn_2_meses.setRelacionado(tbxRelacionado.getValue());
		hisc_aiepi_mn_2_meses.setSintomas_asociados(tbxSintomas_asociados.getValue());
		hisc_aiepi_mn_2_meses.setTratamiento_recibido(tbxTratamiento_recibido.getValue());
		
		return hisc_aiepi_mn_2_meses;
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = (Hisc_aiepi_mn_2_meses) obj;
		try {

			infoPacientes.setCodigo_historia(hisc_aiepi_mn_2_meses
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_aiepi_mn_2_meses
					.getFecha_inicial());
			onMostrarModuloRemisiones();
			infoPacientes.setFecha_cierre(true,
					hisc_aiepi_mn_2_meses.getUltimo_update());
			initMostrar_datos(hisc_aiepi_mn_2_meses);
			cargarInformacion_paciente();

			cargarRemisionInterna(hisc_aiepi_mn_2_meses);

			//cargarAgudezaVisual(hisc_aiepi_mn_2_meses);

			tbxNombres_acompanante.setValue(hisc_aiepi_mn_2_meses
					.getNombres_acompanante());
			tbxApellidos_acompanante.setValue(hisc_aiepi_mn_2_meses
					.getApellidos_acompanante());
			Utilidades.seleccionarListitem(lbxParentesco_acompanante,
					hisc_aiepi_mn_2_meses.getParentesco_acompanante());

			// tbxDireccion_acompanante.setValue(hisc_aiepi_mn_2_meses.getDireccion_acompanante());
			tbxTelefono_acompanante.setValue(hisc_aiepi_mn_2_meses
					.getTelefono_acompanante());
			tbxNombres_padre.setValue(hisc_aiepi_mn_2_meses.getNombres_padre());
			tbxApellidos_padre.setValue(hisc_aiepi_mn_2_meses
					.getApellidos_padre());

			Ocupaciones ocupaciones = new Ocupaciones();
			String oc = hisc_aiepi_mn_2_meses.getOcupacion_padre();
			if (oc != null && !oc.isEmpty()) {
				ocupaciones.setId(hisc_aiepi_mn_2_meses.getOcupacion_padre());
				ocupaciones = getServiceLocator().getOcupacionesService()
						.consultar(ocupaciones);
				tbxOcupacion_padre.setValue(hisc_aiepi_mn_2_meses
						.getOcupacion_padre());
				tbxInfoOcupacion_padre
						.setValue((ocupaciones != null ? ocupaciones
								.getNombre() : ""));
			}
			ibxEdad_padre.setValue(hisc_aiepi_mn_2_meses.getEdad_padre());
			tbxNombres_madre.setValue(hisc_aiepi_mn_2_meses.getNombres_madre());
			tbxApellidos_madre.setValue(hisc_aiepi_mn_2_meses
					.getApellidos_madre());

			oc = hisc_aiepi_mn_2_meses.getOcupacion_madre();
			if (oc != null && !oc.isEmpty()) {
				ocupaciones.setId(hisc_aiepi_mn_2_meses.getOcupacion_madre());
				ocupaciones = getServiceLocator().getOcupacionesService()
						.consultar(ocupaciones);
				tbxOcupacion_padre.setValue(hisc_aiepi_mn_2_meses
						.getOcupacion_madre());
				tbxInfoOcupacion_madre
						.setValue((ocupaciones != null ? ocupaciones
								.getNombre() : ""));
			}
			ibxEdad_madre.setValue(hisc_aiepi_mn_2_meses.getEdad_madre());
			tbxMotivo_consulta.setValue(hisc_aiepi_mn_2_meses
					.getMotivo_consulta());
			tbxEnfermedad_actual.setValue(hisc_aiepi_mn_2_meses
					.getEnfermedad_actual());

			Utilidades.seleccionarRadio(rdbNino_registrado,
					hisc_aiepi_mn_2_meses.getNino_registrado());
			tbxAnt_pat_como_fue_embarazo.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_como_fue_embarazo());
			ibxAnt_pat_cuanto_duro_embarazo.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_cuanto_duro_embarazo());
			tbxAnt_pat_hemoclasificacion.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_hemoclasificacion());
			tbxAnt_pat_como_fue_parto.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_como_fue_parto());
			dbxAnt_pat_cuanto_peso_nacer.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_cuanto_peso_nacer());
			dbxAnt_pat_cuanto_midio_nacer.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_cuanto_midio_nacer());
			tbxAnt_pat_presento_problema_nacer.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_presento_problema_nacer());
			tbxAnt_pat_enfermedades.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_enfermedades());
			dbxSignos_vitales_peso.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses.getSignos_vitales_peso()));
			dbxSignos_vitales_talla.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses.getSignos_vitales_talla()));
			dbxSignos_vitales_pc.setValue(Double.valueOf(hisc_aiepi_mn_2_meses
					.getSignos_vitales_pc()));
			dbxSignos_vitales_fc.setValue(Double.valueOf(hisc_aiepi_mn_2_meses
					.getSignos_vitales_fc()));
			dbxSignos_vitales_fr.setValue(Double.valueOf(hisc_aiepi_mn_2_meses
					.getSignos_vitales_fr()));
			dbxSignos_vitales_taxilar
					.setValue(Double.valueOf(hisc_aiepi_mn_2_meses
							.getSignos_vitales_taxilar()));
			dbxSignos_vitales_oximetria.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses
							.getSignos_vitales_oximetria()));
			dbxSignos_vitales_imc.setValue(Double.valueOf(hisc_aiepi_mn_2_meses
					.getSignos_vitales_imc()));
			Utilidades.seleccionarRadio(
					rdbSignos_vitales_sintomaticos_respiratorio,
					hisc_aiepi_mn_2_meses
							.getSignos_vitales_sintomaticos_respiratorio());
			Utilidades
					.seleccionarRadio(rdbSignos_vitales_sintomaticos_piel,
							hisc_aiepi_mn_2_meses
									.getSignos_vitales_sintomaticos_piel());
			dbxSignos_vitales_p_e_de.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses.getSignos_vitales_p_e_de()));
			dbxSignos_vitales_t_e_de.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses.getSignos_vitales_t_e_de()));
			dbxSignos_vitales_p_t_de.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses.getSignos_vitales_p_t_de()));
			dbxSignos_vitales_pc_e_de
					.setValue(Double.valueOf(hisc_aiepi_mn_2_meses
							.getSignos_vitales_pc_e_de()));
			Utilidades.seleccionarRadio(rdbTiene_nino_diarrea,
					hisc_aiepi_mn_2_meses.getTiene_nino_diarrea());
			gbxCuadro2
					.setVisible(rdbTiene_nino_diarrea.getSelectedIndex() == 0);
			chbVacunales_madre_antitetanica1.setChecked(hisc_aiepi_mn_2_meses
					.getVacunales_madre_antitetanica1().equals("S") ? true
					: false);
			chbVacunales_madre_antitetanica2.setChecked(hisc_aiepi_mn_2_meses
					.getVacunales_madre_antitetanica2().equals("S") ? true
					: false);
			tbxVacunales_madre_otras_vacunas.setValue(hisc_aiepi_mn_2_meses
					.getVacunales_madre_otras_vacunas());
			dtbxVacunales_madre_proxima_vacuna.setValue(hisc_aiepi_mn_2_meses
					.getVacunales_madre_proxima_vacuna());
			chbVacunales_nino_bcg.setChecked(hisc_aiepi_mn_2_meses
					.getVacunales_nino_bcg().equals("S") ? true : false);
			chbVacunales_nino_hepb1.setChecked(hisc_aiepi_mn_2_meses
					.getVacunales_nino_hepb1().equals("S") ? true : false);
			dtbxVacunales_nino_proxima_vacuna.setValue(hisc_aiepi_mn_2_meses
					.getVacunales_nino_proxima_vacuna());
			dtbxVolver_consulta_control.setValue(hisc_aiepi_mn_2_meses
					.getVolver_consulta_control());
			dtbxVolver_consulta_nino_sano.setValue(hisc_aiepi_mn_2_meses
					.getVolver_consulta_nino_sano());

			// tbxPrestador.setValue(hisc_aiepi_mn_2_meses.getPrestador());

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa",hisc_aiepi_mn_2_meses.getCodigo_empresa());
			parametros.put("codigo_sucursal",hisc_aiepi_mn_2_meses.getCodigo_sucursal());
			parametros.put("identificacion",	hisc_aiepi_mn_2_meses.getIdentificacion());
			parametros.put("codigo_historia", 	hisc_aiepi_mn_2_meses.getCodigo_historia());
			parametros.put("via_ingreso", IVias_ingreso.HC_AIEPI_2_MESES);
			parametros.put("cuadro", "1");
			List<Cuadros_aiepi_resultados> lista = getServiceLocator()	.getServicio(Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI1.cargarDatos(lista);

			parametros.put("cuadro", "2");
			lista.clear();
			lista = getServiceLocator().getServicio(	Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI2.cargarDatos(lista);
			parametros.put("cuadro", "3");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI3.cargarDatos(lista);

			// TODO: Cargar los resultados por cuadro
			parametros.put("cuadro", "4");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI4.cargarDatos(lista);

			tbxObservaciones_cuadro1.setValue(hisc_aiepi_mn_2_meses.getObservaciones_cuadro1());
			tbxObservaciones_cuadro2.setValue(hisc_aiepi_mn_2_meses.getObservaciones_cuadro2());
			tbxObservaciones_cuadro3.setValue(hisc_aiepi_mn_2_meses.getObservaciones_cuadro3());
			tbxObservaciones_cuadro4.setValue(hisc_aiepi_mn_2_meses.getObservaciones_cuadro4());
 			
			seleccion((Radiogroup) getFellow("rdbseleccion"));
			cbxAbdomen.setChecked(hisc_aiepi_mn_2_meses.getAbdomen_enf());
			cbxCabeza.setChecked(hisc_aiepi_mn_2_meses.getCabeza_enf());
			cbxCuello.setChecked(hisc_aiepi_mn_2_meses.getCuello_enf());
			cbxToraz.setChecked(hisc_aiepi_mn_2_meses.getToraz_enf());
			
			tbxActualmente_presenta.setValue(hisc_aiepi_mn_2_meses.getActualmente_presenta());
			tbxCaracteristicas_dolor.setValue(hisc_aiepi_mn_2_meses.getCaracteristicas_dolor());
			tbxEvolucion.setValue(hisc_aiepi_mn_2_meses.getEvolucion());
			tbxFecha_inicio.setValue(hisc_aiepi_mn_2_meses.getFecha_inicio());
			tbxIrradiacion.setValue(hisc_aiepi_mn_2_meses.getIrradiacion());
			tbxlocalizacion.setValue(hisc_aiepi_mn_2_meses.getLocalizacion());
			tbxManera_form_inicio.setValue(hisc_aiepi_mn_2_meses.getManera_form_inicio());
			tbxParrafo_enfermedad_actual.setValue(hisc_aiepi_mn_2_meses.getParrafo_enfermedad_actual());
			tbxRelacionado.setValue(hisc_aiepi_mn_2_meses.getRelacionado());
			tbxSintomas_asociados.setValue(hisc_aiepi_mn_2_meses.getSintomas_asociados());
			tbxTratamiento_recibido.setValue(hisc_aiepi_mn_2_meses.getTratamiento_recibido());
			
//			Utilidades.seleccionarListitem(lbxIntensidad,	hisc_aiepi_mn_2_meses.getIntensidad());
//			StringTokenizer stringTokenizer = new StringTokenizer(
//					hisc_aiepi_mn_2_meses.getPrimera_presentacion(), "|");
//
//			Utilidades.seleccionarListitem(
//					lbxPrimera_presentacion,
//					stringTokenizer.hasMoreTokens() ? stringTokenizer
//							.nextToken() : "");
//			spinnerVeces_repetido
//					.setText(stringTokenizer.hasMoreTokens() ? stringTokenizer
//							.nextToken() : "1");
			
			cargarImpresionDiagnostica(hisc_aiepi_mn_2_meses);
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);
			btImprimir.setVisible(true);

			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString())) {
				// accionForm(OpcionesFormulario.MODIFICAR,hisc_aiepi_mn_2_meses);
			} else {
				// accionForm(OpcionesFormulario.MOSTRAR,hisc_aiepi_mn_2_meses);
			}
			cargarAnexo9_remision(hisc_aiepi_mn_2_meses);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			MensajesUtil
					.mensajeError(e, null, Hisc_aiepi_mn_2_mesesAction.this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = (Hisc_aiepi_mn_2_meses) obj;
		try {
			hisc_aiepi_mn_2_meses.setDelete_user(getUsuarios().getCodigo());
			int result = hisc_aiepi_mn_2_mesesService
					.eliminar(hisc_aiepi_mn_2_meses);
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

	private void cargarImpresionDiagnostica(
			Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses) throws Exception {

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();

		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(hisc_aiepi_mn_2_meses
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
		orden_servicioAction = (Orden_servicio_internoAction) Executions
				.createComponents("/pages/orden_servicio_interno.zul", null,
						parametros);
		orden_servicioAction.inicializar(this);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
		// cargo_farmacologico = true;
		// }
	}

	// METODOS PARA VISTA

	public void autoOpcionesCuadros() {
		// TODO: validaciones
		// CUADRO 1
		Checkbox chk1_1_6 = macroCuadroAIEPI1.getCheckbox("06", "1");
		Checkbox chk1_1_7 = macroCuadroAIEPI1.getCheckbox("07", "1");
		Checkbox chk1_1_9 = macroCuadroAIEPI1.getCheckbox("09", "1");
		CuadroAIEPIMacro
				.deshabilitarCheck(
						chk1_1_6,
						(dbxSignos_vitales_fr.getValue() != null && (dbxSignos_vitales_fr
								.getValue() >= 60 || dbxSignos_vitales_fr
								.getValue() < 30)));
		macroCuadroAIEPI1.evaluarCambio("06", "1");
		CuadroAIEPIMacro
				.deshabilitarCheck(
						chk1_1_7,
						(dbxSignos_vitales_fc.getValue() != null && (dbxSignos_vitales_fc
								.getValue() > 180 || dbxSignos_vitales_fc
								.getValue() < 100)));
		macroCuadroAIEPI1.evaluarCambio("07", "1");
		CuadroAIEPIMacro
				.deshabilitarCheck(chk1_1_9, (dbxSignos_vitales_taxilar
						.getValue() != null && (dbxSignos_vitales_taxilar
						.getValue() > 38 || dbxSignos_vitales_taxilar
						.getValue() < 35.5)));
		macroCuadroAIEPI1.evaluarCambio("09", "1");
		// CUADRO 3
		Checkbox chk3_1_1 = macroCuadroAIEPI3.getCheckbox("01", "1");
		Checkbox chk3_1_2 = macroCuadroAIEPI3.getCheckbox("02", "1");
		Checkbox chk3_1_3 = macroCuadroAIEPI3.getCheckbox("03", "1");
		CuadroAIEPIMacro
				.deshabilitarCheck(chk3_1_1, (dbxSignos_vitales_p_e_de
						.getValue() != null && (dbxSignos_vitales_p_e_de
						.getValue() < -3)));
		macroCuadroAIEPI3.evaluarCambio("01", "1");
		CuadroAIEPIMacro
				.deshabilitarCheck(chk3_1_2, (dbxSignos_vitales_p_t_de
						.getValue() != null && (dbxSignos_vitales_p_t_de
						.getValue() < -3)));
		macroCuadroAIEPI3.evaluarCambio("02", "1");
		CuadroAIEPIMacro
				.deshabilitarCheck(
						chk3_1_3,
						(dbxSignos_vitales_peso.getValue() != null && (dbxSignos_vitales_peso
								.getValue() < 2000d)));
		macroCuadroAIEPI3.evaluarCambio("03", "1");
		Checkbox chk3_2_1 = macroCuadroAIEPI3.getCheckbox("08", "3");
		Checkbox chk3_2_2 = macroCuadroAIEPI3.getCheckbox("09", "3");
		Checkbox chk3_2_3 = macroCuadroAIEPI3.getCheckbox("10", "3");
		Checkbox chk3_2_4 = macroCuadroAIEPI3.getCheckbox("11", "3");
		CuadroAIEPIMacro
				.deshabilitarCheck(chk3_2_1, (dbxSignos_vitales_p_e_de
						.getValue() != null && (dbxSignos_vitales_p_e_de
						.getValue() >= -3 && dbxSignos_vitales_p_e_de
						.getValue() < -2)));
		macroCuadroAIEPI3.evaluarCambio("08", "3");
		CuadroAIEPIMacro
				.deshabilitarCheck(chk3_2_2, (dbxSignos_vitales_p_t_de
						.getValue() != null && (dbxSignos_vitales_p_t_de
						.getValue() >= -3 && dbxSignos_vitales_p_t_de
						.getValue() < -2)));
		macroCuadroAIEPI3.evaluarCambio("09", "3");
		CuadroAIEPIMacro
				.deshabilitarCheck(chk3_2_3, (dbxSignos_vitales_p_e_de
						.getValue() != null && (dbxSignos_vitales_p_e_de
						.getValue() >= -2 && dbxSignos_vitales_p_e_de
						.getValue() < -1)));
		macroCuadroAIEPI3.evaluarCambio("10", "3");
		CuadroAIEPIMacro
				.deshabilitarCheck(chk3_2_4, (dbxSignos_vitales_p_t_de
						.getValue() != null && (dbxSignos_vitales_p_t_de
						.getValue() >= -2 && dbxSignos_vitales_p_t_de
						.getValue() < -1)));
		macroCuadroAIEPI3.evaluarCambio("11", "3");

		Checkbox chk3_4_1 = macroCuadroAIEPI3.getCheckbox("19", "5");
		CuadroAIEPIMacro
				.deshabilitarCheck(chk3_4_1, (dbxSignos_vitales_p_e_de
						.getValue() != null && (dbxSignos_vitales_p_e_de
						.getValue() > -1)));
		macroCuadroAIEPI3.evaluarCambio("19", "5");
		cambioCuadro("3");

		// CUADRO 4
		Checkbox chk4_1_3 = macroCuadroAIEPI4.getCheckbox("03", "1");
		CuadroAIEPIMacro
				.deshabilitarCheck(chk4_1_3, (dbxSignos_vitales_pc_e_de
						.getValue() != null && (dbxSignos_vitales_pc_e_de
						.getValue() < -2 || dbxSignos_vitales_pc_e_de
						.getValue() > 2)));
		macroCuadroAIEPI4.evaluarCambio("03", "1");
	}

	public void validarBotonesDE(Integer opc) {
		Integer inicio = 0;
		Integer fin = 2;
		Integer meses = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha),
				"2", false));
		if (meses > inicio && meses <= fin) {
			switch (opc) {
			case 0:
				validarTallaEdad();
				validarPesoTalla();
				break;
			case 1:
				validarPerimetroCefalicoEdad();
				break;
			case 2:
				validarPesoTalla();
				break;
			default:
				validarTallaEdad();
				validarPesoTalla();
				validarPerimetroCefalicoEdad();
				break;
			}
		}
	}

	public void validarTallaEdad() {
		double talla;
		double minTalla = 44;
		double maxTalla = 100;
		if (dbxSignos_vitales_talla.getValue() != null) {
			talla = dbxSignos_vitales_talla.getValue();
			if ((talla <= maxTalla && talla > minTalla)) {
				btnCalcularTallaEdad.setDisabled(false);
			} else {
				MensajesUtil.notificarAlerta("Talla: (" + (minTalla + 1) + "-"
						+ maxTalla + ") cm", dbxSignos_vitales_talla);
				btnCalcularTallaEdad.setDisabled(true);
			}
		} else {
			MensajesUtil.notificarAlerta("Talla: (" + (minTalla + 1) + "-"
					+ maxTalla + ") cm", dbxSignos_vitales_talla);
			btnCalcularTallaEdad.setDisabled(true);
		}
	}

	public void validarPesoTalla() {
		double peso = 0;
		double talla = 0;
		double minPeso = 0;
		double maxPeso = 20000;
		double minTalla = 44;
		double maxTalla = 100;
		if (dbxSignos_vitales_peso.getValue() != null
				&& dbxSignos_vitales_talla.getValue() != null) {
			peso = dbxSignos_vitales_peso.getValue();
			talla = dbxSignos_vitales_talla.getValue();

			if ((peso <= maxPeso && peso > minPeso)
					&& (talla <= maxTalla && talla > minTalla)) {
				btnCalcularPesoTalla.setDisabled(false);
				btnCalcularPesoEdad.setDisabled(false);
			} else {
				if (!(peso <= maxPeso && peso > minPeso)
						&& !(talla <= maxTalla && talla > minTalla)) {
					MensajesUtil.notificarAlerta("Peso: (" + (minPeso + 1)
							+ "-" + maxPeso + ") g\nTalla: (" + (minTalla + 1)
							+ "-" + maxTalla + ") cm", dbxSignos_vitales_peso);
				}
				btnCalcularPesoTalla.setDisabled(true);
				btnCalcularPesoEdad.setDisabled(true);
			}
		} else {
			if (!(peso <= maxPeso && peso > minPeso)
					&& !(talla <= maxTalla && talla > minTalla)) {
				MensajesUtil.notificarAlerta("Peso: (" + (minPeso + 1) + "-"
						+ maxPeso + ") g\nTalla: (" + (minTalla + 1) + "-"
						+ maxTalla + ") cm", dbxSignos_vitales_peso);
			}
			btnCalcularPesoTalla.setDisabled(true);
			btnCalcularPesoEdad.setDisabled(true);
		}
	}

	public void validarPerimetroCefalicoEdad() {
		double pc;
		double minPc = 29;
		double maxPc = 60;
		if (dbxSignos_vitales_pc.getValue() != null) {
			pc = dbxSignos_vitales_pc.getValue();
			if ((pc <= maxPc && pc > minPc)) {
				btnCalcularPerimetroCefalicoEdad.setDisabled(false);
			} else {
				MensajesUtil.notificarAlerta("P.C: (" + (minPc + 1) + "-"
						+ maxPc + ") cm", dbxSignos_vitales_pc);
				btnCalcularPerimetroCefalicoEdad.setDisabled(true);
			}
		} else {
			MensajesUtil.notificarAlerta("P.C: (" + (minPc + 1) + "-" + maxPc
					+ ") cm", dbxSignos_vitales_pc);
			btnCalcularPerimetroCefalicoEdad.setDisabled(true);
		}
	}

	public void calcularPesoEdad() {
		double peso;
		double talla;

		if (dbxSignos_vitales_peso.getValue() == null) {
			dbxSignos_vitales_peso.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_peso.setStyle("background-color:white");
			peso = dbxSignos_vitales_peso.getValue() / 1000;
		}

		if (dbxSignos_vitales_talla.getValue() == null) {
			dbxSignos_vitales_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_talla.setStyle("background-color:white");
			talla = dbxSignos_vitales_talla.getValue();
		}

		dbxSignos_vitales_p_e_de.setValue(calcularPesoEdad(peso, talla, fecha)
				.getValor());
		autoOpcionesCuadros();
	}

	public void calcularTallaEdad() {
		double talla;
		if (dbxSignos_vitales_talla.getValue() == null) {
			dbxSignos_vitales_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_talla.setStyle("background-color:white");
			talla = dbxSignos_vitales_talla.getValue();
		}

		dbxSignos_vitales_t_e_de.setValue(calcularTallaEdad(talla, fecha)
				.getValor());
		autoOpcionesCuadros();
	}

	public void calcularPesoTalla() {

		double talla;
		double peso;

		if (dbxSignos_vitales_talla.getValue() == null) {
			dbxSignos_vitales_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_talla.setStyle("background-color:white");
			talla = dbxSignos_vitales_talla.getValue();
		}

		if (dbxSignos_vitales_peso.getValue() == null) {
			dbxSignos_vitales_peso.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_peso.setStyle("background-color:white");
			peso = dbxSignos_vitales_peso.getValue() / 1000;
		}

		dbxSignos_vitales_p_t_de.setValue(calcularPesoTalla(peso, talla)
				.getValor());
		autoOpcionesCuadros();
	}

	public void calcularPerimetroCefalicoEdad() {
		double perimetro_cefalico;

		if (dbxSignos_vitales_pc.getValue() == null) {
			dbxSignos_vitales_pc.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar el perimetro cefálico del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxSignos_vitales_pc.setStyle("background-color:white");
			perimetro_cefalico = dbxSignos_vitales_pc.getValue();
		}
		dbxSignos_vitales_pc_e_de.setValue(calcularPerimetroCefalicoEdad(
				perimetro_cefalico, fecha).getValor());
		autoOpcionesCuadros();
	}

	private RespuestaInt calcularPesoEdad(double peso, double talla,
			Timestamp fecha) {
		return GraficasCalculadorUtils.calcularPesoEdad0$2Anios(sexo, peso,
				talla, fecha);
	}

	private RespuestaInt calcularPerimetroCefalicoEdad(
			double perimetro_cefalico, Timestamp fecha) {
		return GraficasCalculadorUtils.calcularPerimetroCefalicoEdad0$2Anios(
				sexo, perimetro_cefalico, fecha);
	}

	private RespuestaInt calcularPesoTalla(double peso, double talla) {
		return GraficasCalculadorUtils.calcularPesoTalla0$2Anios(sexo, peso,
				talla);
	}

	private RespuestaInt calcularTallaEdad(double talla, Timestamp fecha) {
		return GraficasCalculadorUtils.calcularTallaEdad0$2Anios(sexo, talla,
				fecha);
	}

	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			if (dbxP.getValue() != null && dbxT.getValue() != null) {
				double imc = dbxP.getValue()/1000
						/ (Math.pow(dbxT.getValue() / 100, 2));
				dbxI.setValue(imc);
//			UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
//					UtilidadSignosVitales.TALLA_CENTIMETROS,
//					UtilidadSignosVitales.PESO_GRAMOS);
			}
		} catch (Exception e) {
			// MensajesUtil.mensajeError(e, "", this);
			// e.printStackTrace();
		}
	}

	public void alertaFc(Doublebox dbxSignos_vitales_fc) {
		UtilidadSignosVitales.onMostrarAlertaFrecuenciaCardiaca(
				dbxSignos_vitales_fc, fecha, paciente.getSexo());
	}

	public void alertaFr(Doublebox dbxSignos_vitales_fr) {
		UtilidadSignosVitales.onMostrarAlertaFrecuenciaRespiratoria(
				dbxSignos_vitales_fr, fecha, paciente.getSexo());
	}

	public void alertaTemperatura(Doublebox dbxSignos_vitales_taxilar) {
		UtilidadSignosVitales.onMostrarAlertaTemperatura(
				dbxSignos_vitales_taxilar, fecha, paciente.getSexo());
	}

	public void mostrarAnexo(Integer i) {
		switch (i) {
		case 0:
			CuadroAIEPIMacro.mostrarVentana(new Image(
					"/images/aiepi/observacion_lactancia.png"), this.getPage(),
					"observacion de lactancia materna", 0, 0, 0);
			break;
		case 1:
			CuadroAIEPIMacro
					.mostrarVentana(
							new Image("/images/aiepi/vacunacion_ninos.png"),
							this.getPage(),
							"Verificar los antecedentes de vacunacion de todos los niños",
							0, 0, 0);
			break;
		case 2:
			CuadroAIEPIMacro.mostrarVentana(new Image(
					"/images/aiepi/plan_a_tratar_diarrea.png"), this.getPage(),
					"Tratar la diarrea en la casa", 0, 0, 0);
			break;
		case 3:
			CuadroAIEPIMacro
					.mostrarVentana(
							new Image("/images/aiepi/tos_resfriado.png"),
							this.getPage(),
							"Cuidados del niño con tos o resfriado en casa y medidas preventivas",
							0, 0, 0);
			break;
		case 4:
			CuadroAIEPIMacro
					.mostrarVentana(
							new Image("/images/aiepi/febril.png"),
							this.getPage(),
							"Medidas preventivas para los niños con enfermedades febriles",
							0, 0, 0);
			break;
		case 5:
			CuadroAIEPIMacro
					.mostrarVentana(new Image("/images/aiepi/sro.png"),
							this.getPage(), "Tratar la deshidratacion con sro",
							0, 0, 0);
			break;
		case 6:
			CuadroAIEPIMacro.mostrarVentana(new Image(
					"/images/aiepi/buen_trato.png"), this.getPage(),
					"Medidas de buen trato", 0, 0, 0);
			break;
		}
	}

	public void mostrarIni() {
		if (admision != null
				&& opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
			/*
			CuadroAIEPIMacro.mostrarVentana(new Image(
					"/images/aiepi/lavado_manos_texto.png"), this.getPage(),
					"Lávese las manos", 0, 0, 0);
					*/
		}
	}

	public void calcularProximaVacuna() {
		Calendar prox = Calendar.getInstance();
		prox.setTime(paciente.getFecha_nacimiento());
		prox.set(Calendar.MONTH, prox.get(Calendar.MONTH) + 2);
		dtbxVacunales_nino_proxima_vacuna.setValue(prox.getTime());
	}

	private void parametrizarBandboxOcupacion(
			BandboxRegistrosMacro tbxCodigo_ocupacion,
			Textbox tbxInfoOcupacion, Toolbarbutton btnLimpiarOcupacion) {
		tbxCodigo_ocupacion.inicializar(tbxInfoOcupacion, btnLimpiarOcupacion);
		tbxCodigo_ocupacion
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Ocupaciones>() {

					@Override
					public void renderListitem(Listitem listitem,
							Ocupaciones registro) {
						listitem.appendChild(new Listcell(registro.getId()));
						listitem.appendChild(new Listcell(registro.getNombre()));
					}

					@Override
					public List<Ocupaciones> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						return getServiceLocator().getOcupacionesService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Ocupaciones registro) {
						bandbox.setValue(registro.getId());
						textboxInformacion.setValue(registro.getNombre());

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	@Override
	public void cambioCheck(String cuadro, String estado, String descripcion)
			throws Exception {
		// Messagebox.show("Cuadro:"+cuadro+" estado:"+estado+" descripcion:"+descripcion,
		// "Information", Messagebox.OK, Messagebox.INFORMATION);
		cambioCuadro(cuadro);
	}

	// Validaciones por cambio en los check de los cuadros
	public void cambioCuadro(String cuadro) {
		Integer prox = 365;
		Integer dia = 0;
		// Cuadro 1
		dia = 2;
		if (cuadro.equalsIgnoreCase("1")) {
			if (macroCuadroAIEPI1.cantCheckeadosEstado("2") > 0 && prox > dia) {
				prox = dia;
			}
		}
		// Cuadro 2
		dia = 2;
		if (cuadro.equalsIgnoreCase("2")) {
			if (macroCuadroAIEPI2.cantCheckeadosEstado("2") > 0 && prox > dia) {
				prox = dia;
			}
		}

		// Cuadro 3
		if (cuadro.equalsIgnoreCase("3")) {

			Checkbox chk3_5_2 = macroCuadroAIEPI3.getCheckbox("20", "5");
			Boolean check = macroCuadroAIEPI3.cantCheckeadosEstado("1") == 0
					&& macroCuadroAIEPI3.cantCheckeadosEstado("2") == 0
					&& macroCuadroAIEPI3.cantCheckeadosEstado("3") == 0
					&& macroCuadroAIEPI3.cantCheckeadosEstado("4") == 0;
			CuadroAIEPIMacro.deshabilitarCheck(chk3_5_2, check);
			macroCuadroAIEPI3.evaluarCambio("20", "5");
			dia = 2;
			if ((macroCuadroAIEPI3.cantCheckeadosEstado("3") > 0 || macroCuadroAIEPI3
					.cantCheckeadosEstado("4") > 0) && prox > dia) {
				prox = dia;
			}
			dia = 30;
			if (macroCuadroAIEPI3.cantCheckeadosEstado("5") > 0 && prox > dia) {
				prox = dia;
			}

		}

		dia = 30;
		if (cuadro.equalsIgnoreCase("4")) {
			if ((macroCuadroAIEPI4.cantCheckeadosEstado("2") > 0 || macroCuadroAIEPI4
					.cantCheckeadosEstado("3") > 0) && prox > dia) {
				prox = dia;
			}
		}
		if (prox == 365) {
			prox = 0;
		}
		calcularProximaConsultaControl(prox);
	}

	public void calcularProximaConsultaControl(Integer dias) {
		Calendar prox = Calendar.getInstance();
		prox.setTime(new Date());
		prox.set(Calendar.DAY_OF_YEAR, prox.get(Calendar.DAY_OF_YEAR) + dias);
		dtbxVolver_consulta_control.setValue(prox.getTime());
	}

	private void cargarAnexo9_remision(
			Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_aiepi_mn_2_meses
				.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);
		if(anexo9_entidad!=null){
			remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
		}
	}

	private void cargarRemisionInterna(
			Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses) throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_aiepi_mn_2_meses
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_aiepi_mn_2_meses
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_aiepi_mn_2_meses
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
		informacion_clinica.append("- SIGNOS VITALES\n");
		informacion_clinica.append("\tFrecuencia cardiaca: ")
				.append(dbxSignos_vitales_fc.getValue()).append("\n");
		informacion_clinica.append("\tFrecuencia respiratoria: ")
				.append(dbxSignos_vitales_fr.getValue())
				.append("\n");
		informacion_clinica.append("\tTemperatura: ")
				.append(dbxSignos_vitales_taxilar.getValue()).append("\n");
		informacion_clinica.append("\tPeso(kg): ")
				.append(dbxSignos_vitales_peso.getValue()).append("\n");
		informacion_clinica.append("\tTalla: ")
				.append(dbxSignos_vitales_talla.getValue()).append("\n");
		informacion_clinica.append("\tIMC: ").append(dbxSignos_vitales_imc.getValue())
				.append("\n").append("\n");

		informacion_clinica.append("- DIAGNOSTICOS").append("\n");
		Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
				.obtenerImpresionDiagnostica();
		Cie cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_principal());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica.append("\timpresion diagnóstica: ")
				.append(impresion_diagnostica.getCie_principal()).append(" ")
				.append(cie != null ? cie.getNombre() : "").append("\n");
		;
		informacion_clinica
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_principal(),
						"tipo_diagnostico", this)).append("\n");

		/* relacionado 1 */
		cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_relacionado1());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica.append("\tRelacionado 1: ")
				.append(impresion_diagnostica.getCie_relacionado1())
				.append(" ").append(cie != null ? cie.getNombre() : "")
				.append("\n");

		informacion_clinica
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_relacionado1(),
						"tipo_diagnostico", this)).append("\n");

		/* relacionado 2 */
		cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_relacionado2());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica.append("\tRelacionado 2: ")
				.append(impresion_diagnostica.getCie_relacionado2())
				.append(" ").append(cie != null ? cie.getNombre() : "")
				.append("\n");

		informacion_clinica
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_relacionado2(),
						"tipo_diagnostico", this)).append("\n");

		cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_relacionado3());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica.append("\tRelacionado 3: ")
				.append(impresion_diagnostica.getCie_relacionado3())
				.append(" ").append(cie != null ? cie.getNombre() : "")
				.append("\n");

		informacion_clinica
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_relacionado3(),
						"tipo_diagnostico", this)).append("\n");
		return informacion_clinica.toString();
	}

	public void onSeleccionarRelacionAcompaniante() {
		//madre
		if(lbxParentesco_acompanante.getSelectedItem().getValue().toString().equals("1")){
			tbxId_madre.setValue(cita.getCedula_acomp());
			tbxNombres_madre.setValue(cita.getAcompaniante());
			tbxApellidos_madre.setValue(cita.getApellidos_acomp());
			tbxTelefono_madre.setValue(cita.getTel_acompaniante());
			
			if(tbxId_madre.getValue().equals(tbxId_padre.getValue())){
				tbxId_padre.setValue("");
			}
			if(tbxNombres_madre.getValue().equals(tbxNombres_padre.getValue())){
				tbxNombres_padre.setValue("");
			}
			if(tbxApellidos_madre.getValue().equals(tbxApellidos_padre.getValue())){
				tbxApellidos_padre.setValue("");
			}
			if(tbxTelefono_madre.getValue().equals(tbxTelefono_padre.getValue())){
				tbxTelefono_padre.setValue("");
			}						
		}else if(lbxParentesco_acompanante.getSelectedItem().getValue().toString().equals("2")){
			tbxId_padre.setValue(cita.getCedula_acomp());
			tbxNombres_padre.setValue(cita.getAcompaniante());
			tbxApellidos_padre.setValue(cita.getApellidos_acomp());
			tbxTelefono_padre.setValue(cita.getTel_acompaniante());
			
			if(tbxId_padre.getValue().equals(tbxId_madre.getValue())){
				tbxId_madre.setValue("");
			}
			if(tbxNombres_padre.getValue().equals(tbxNombres_madre.getValue())){
				tbxNombres_madre.setValue("");
			}
			if(tbxApellidos_padre.getValue().equals(tbxApellidos_madre.getValue())){
				tbxApellidos_madre.setValue("");
			}
			if(tbxTelefono_padre.getValue().equals(tbxTelefono_madre.getValue())){
				tbxTelefono_madre.setValue("");
			}
		}
		
		if (lbxParentesco_acompanante.getSelectedItem().getValue().toString().equals("8")) {
			tbxOtro_acompanante.setVisible(true);
		} else {
			tbxOtro_acompanante.setVisible(false);
		}
	}
	public void seleccion(Radiogroup radiogroup) {
		try {

			if (radiogroup.getSelectedItem().getValue().equals("1")) {
				row2.setVisible(false);
				row3.setVisible(false);
				row4.setVisible(false);
				row5.setVisible(false);
				row6.setVisible(false);
				row7.setVisible(false);
				row8.setVisible(false);
				row9.setVisible(false);
				row10.setVisible(false);
				row11.setVisible(false);
				row12.setVisible(false);
				row13.setVisible(false);
				row14.setVisible(false);
				tbxEnfermedad_actual.setVisible(true);
				lbEnfermedad_actual.setVisible(true);
				tbxEnfermedad_actual.invalidate();
				// lbxRepetidos.setVisible(false);
				// lbRepetido.setVisible(false);
				lbPrimera_presentacion.setVisible(false);
				lbxPrimera_presentacion.setVisible(false);
				lbFecha_inicio.setVisible(false);
				tbxFecha_inicio.setVisible(false);
				lbMan_for_inicio.setVisible(false);
				tbxManera_form_inicio.setVisible(false);
				lbsintomas_asociados.setVisible(false);
				tbxSintomas_asociados.setVisible(false);
				lbTratamiento_recibido.setVisible(false);
				tbxTratamiento_recibido.setVisible(false);
				lbActual_presenta.setVisible(false);
				tbxActualmente_presenta.setVisible(false);
				tbxlocalizacion.setVisible(false);
				lbIrradiacion.setVisible(false);
				tbxIrradiacion.setVisible(false);
				lbLocalizacion.setVisible(false);
				hbxLocalizacion.setVisible(false);
				tbxlocalizacion.setVisible(false);
				lbCaracteristicas_Dolor.setVisible(false);
				tbxCaracteristicas_dolor.setVisible(false);
				lbRelacionado.setVisible(false);
				tbxRelacionado.setVisible(false);
				lbEvolucion_cuadro.setVisible(false);
				tbxEvolucion.setVisible(false);
				lbxIntensidad.setVisible(false);
				lbIntensidad.setVisible(false);
				tbxParrafo_enfermedad_actual.setVisible(false);

			} else if (radiogroup.getSelectedItem().getValue().equals("2")) {
				row2.setVisible(true);
				row3.setVisible(true);
				row4.setVisible(true);
				row5.setVisible(true);
				row6.setVisible(true);
				row7.setVisible(true);
				row8.setVisible(true);
				row9.setVisible(true);
				row10.setVisible(false);
				row11.setVisible(false);
				row12.setVisible(false);
				row13.setVisible(false);
				row14.setVisible(true);
				tbxEnfermedad_actual.setVisible(false);
				lbEnfermedad_actual.setVisible(true);
				// lbxRepetidos.setVisible(true);
				// lbRepetido.setVisible(true);
				lbPrimera_presentacion.setVisible(true);
				lbxPrimera_presentacion.setVisible(true);
				lbFecha_inicio.setVisible(true);
				tbxFecha_inicio.setVisible(true);
				lbMan_for_inicio.setVisible(true);
				tbxManera_form_inicio.setVisible(true);
				lbsintomas_asociados.setVisible(true);
				tbxSintomas_asociados.setVisible(true);
				lbTratamiento_recibido.setVisible(true);
				tbxTratamiento_recibido.setVisible(true);
				lbActual_presenta.setVisible(true);
				tbxActualmente_presenta.setVisible(true);
				tbxlocalizacion.setVisible(false);
				lbIrradiacion.setVisible(false);
				tbxIrradiacion.setVisible(false);
				lbLocalizacion.setVisible(false);
				hbxLocalizacion.setVisible(false);
				tbxlocalizacion.setVisible(false);
				lbCaracteristicas_Dolor.setVisible(false);
				tbxCaracteristicas_dolor.setVisible(false);
				lbRelacionado.setVisible(true);
				tbxRelacionado.setVisible(true);
				lbEvolucion_cuadro.setVisible(true);
				tbxEvolucion.setVisible(true);
				lbxIntensidad.setVisible(false);
				lbIntensidad.setVisible(false);
				tbxParrafo_enfermedad_actual.setVisible(true);

			} else if (radiogroup.getSelectedItem().getValue().equals("3")) {
				row2.setVisible(true);
				row3.setVisible(true);
				row4.setVisible(true);
				row5.setVisible(true);
				row6.setVisible(true);
				row7.setVisible(true);
				row8.setVisible(true);
				row9.setVisible(true);
				row10.setVisible(true);
				row11.setVisible(true);
				row12.setVisible(true);
				row13.setVisible(true);
				row14.setVisible(true);
				tbxEnfermedad_actual.setVisible(false);
				lbEnfermedad_actual.setVisible(true);
				// lbxRepetidos.setVisible(true);
				// lbRepetido.setVisible(true);
				lbPrimera_presentacion.setVisible(true);
				lbxPrimera_presentacion.setVisible(true);
				lbFecha_inicio.setVisible(true);
				tbxFecha_inicio.setVisible(true);
				lbMan_for_inicio.setVisible(true);
				tbxManera_form_inicio.setVisible(true);
				lbsintomas_asociados.setVisible(true);
				tbxSintomas_asociados.setVisible(true);
				lbTratamiento_recibido.setVisible(true);
				tbxTratamiento_recibido.setVisible(true);
				lbActual_presenta.setVisible(true);
				tbxActualmente_presenta.setVisible(true);
				tbxlocalizacion.setVisible(true);
				lbIrradiacion.setVisible(true);
				tbxIrradiacion.setVisible(true);
				lbLocalizacion.setVisible(true);
				hbxLocalizacion.setVisible(true);
				lbCaracteristicas_Dolor.setVisible(true);
				tbxCaracteristicas_dolor.setVisible(true);
				lbRelacionado.setVisible(true);
				tbxRelacionado.setVisible(true);
				lbEvolucion_cuadro.setVisible(true);
				tbxEvolucion.setVisible(true);
				lbxIntensidad.setVisible(true);
				lbIntensidad.setVisible(true);
				tbxParrafo_enfermedad_actual.setVisible(true);

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void LimpiarPlantilla(){
		tbxEnfermedad_actual.setText("");
		tbxFecha_inicio.setText("");
		lbxPrimera_presentacion.setSelectedIndex(0);
		spinnerVeces_repetido.setText("1");
		tbxManera_form_inicio.setText("");
		tbxRelacionado.setText("");
		tbxSintomas_asociados.setText("");
		tbxEvolucion.setText("");
		tbxTratamiento_recibido.setText("");
		tbxActualmente_presenta.setText("");
		tbxlocalizacion.setText("");
		tbxIrradiacion.setText("");
		lbxIntensidad.setSelectedIndex(0);
		tbxCaracteristicas_dolor.setText("");
		tbxParrafo_enfermedad_actual.setText("");
	}
	
	public void onSeleccionarPresentacion() {
		String presentacion = lbxPrimera_presentacion.getSelectedItem()
				.getValue().toString();
		if (presentacion.equals("N")) {
			lbVeces_repetido.setVisible(true);
			spinnerVeces_repetido.setVisible(true);
			spinnerVeces_repetido.setValue(1);
		} else {
			lbVeces_repetido.setVisible(false);
			spinnerVeces_repetido.setVisible(false);
			spinnerVeces_repetido.setValue(0);
		}
	}
	
	public void generarParrafoEnfermedadActual() {
		StringBuffer strBuffer = new StringBuffer();

		Integer edad_anios = infoPacientes.getEdadAnios();

		if (edad_anios >= 18) {
			strBuffer
					.append("Paciente quien consulta por cuadro clinico presentado ");
		} else {
			strBuffer.append("Paciente acompañado por su "
					+ lbxParentesco_acompanante.getSelectedItem().getLabel()
					+ " quien consulta por cuadro clínico presentado ");
		}
		if (lbxPrimera_presentacion.getSelectedIndex() == 0) {
			strBuffer.append(" por primera vez");
		} else {
			int veces = spinnerVeces_repetido.getValue();
			if (veces == 1) {
				strBuffer.append(" en una ocasion ");
			} else {
				strBuffer.append(veces + " veces, ");
			}
		}
		strBuffer.append(" que inició ").append(tbxFecha_inicio.getText());
		if (!tbxManera_form_inicio.getValue().isEmpty()) {
			strBuffer.append(" de forma ").append(
					tbxManera_form_inicio.getValue());
		}
		if (!tbxRelacionado.getValue().isEmpty()) {
			strBuffer.append(" el cual el paciente relaciona con ").append(
					tbxRelacionado.getValue() + ("."));
		}
		if (!tbxSintomas_asociados.getValue().isEmpty()) {
			strBuffer.append(" Con los siguientes sintomas y/o signos ")
					.append(tbxSintomas_asociados.getValue());
		}
		if (!tbxEvolucion.getValue().isEmpty()) {
			strBuffer.append(" presentando una evolucion del cuadro ").append(
					tbxEvolucion.getValue() + ("."));
		}
		if (!tbxTratamiento_recibido.getValue().isEmpty()) {
			strBuffer.append(" recibiendo un tratamiento de ").append(
					tbxTratamiento_recibido.getValue());
		}

		if (!tbxActualmente_presenta.getValue().isEmpty()) {
			strBuffer.append(" y actualmente se presenta con ").append(
					tbxActualmente_presenta.getValue() + ("."));
		}

		if (!tbxlocalizacion.getValue().isEmpty()) {
			strBuffer.append("\nDolor localizado en ").append(
					tbxlocalizacion.getValue() + (" "));
		}

		if (rdbseleccion.getSelectedItem().getValue().toString().equals("3")) {
			if (tbxIrradiacion.getValue().equals("no")
					|| tbxIrradiacion.getValue().equals("NO")
					|| tbxIrradiacion.getValue().equals("No")) {
				strBuffer.append(tbxIrradiacion.getValue()).append(
						" irradiado ");
			} else if (!tbxIrradiacion.getValue().isEmpty()) {
				strBuffer.append(" irradiado a ").append(
						tbxIrradiacion.getValue());
			}

			if (!lbxIntensidad.getSelectedItem().getValue().toString()
					.isEmpty()) {
				strBuffer.append(" con una intensidad ").append(
						lbxIntensidad.getSelectedItem().getLabel());
			}

			if (!tbxCaracteristicas_dolor.getValue().isEmpty()) {
				strBuffer.append(" y caracteristica o tipo ").append(
						tbxCaracteristicas_dolor.getValue());
			}

		}

		tbxParrafo_enfermedad_actual.setValue(strBuffer.toString());

	}
	

	public void cargarSignosVitalesEnfermera(){
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setCodigo_empresa(codigo_empresa);
		enfermera_signos.setCodigo_sucursal(codigo_sucursal);
		enfermera_signos.setNro_ingreso(admision.getNro_ingreso());
		enfermera_signos.setNro_identificacion(admision.getNro_identificacion()	);
		enfermera_signos = getServiceLocator().getServicio(Enfermera_signosService.class).consultar(enfermera_signos);
		
		if(enfermera_signos != null){
			
			dbxSignos_vitales_fc.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxSignos_vitales_fr.setValue(enfermera_signos.getFrecuencia_respiratoria());
			dbxSignos_vitales_peso.setValue(enfermera_signos.getPeso());
			dbxSignos_vitales_talla.setValue(enfermera_signos.getTalla());
			dbxSignos_vitales_imc.setValue(enfermera_signos.getImc());
			dbxSignos_vitales_pc.setValue(enfermera_signos.getPerimetro_cefalico());
			dbxSignos_vitales_taxilar.setValue(enfermera_signos.getTemperatura());
			dbxSignos_vitales_oximetria.setValue(enfermera_signos.getOximetria());
			
			
			calcularIMC(dbxSignos_vitales_peso,dbxSignos_vitales_talla,dbxSignos_vitales_imc);
			alertaFc(dbxSignos_vitales_fc);
			alertaFr(dbxSignos_vitales_fr);
			alertaTemperatura(dbxSignos_vitales_taxilar);
			validarBotonesDE(0);
			validarBotonesDE(1);
			validarBotonesDE(2);
			autoOpcionesCuadros();
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
			Clients.evalJavaScript("window.open(\""+request.getContextPath()+"/pages/reports/hisc_aiepi_mn_2_meses_reporte.zul"+parametros_pagina+"\", \"\",\"width=910, top=0, left=0\");");
		}
	}	
}