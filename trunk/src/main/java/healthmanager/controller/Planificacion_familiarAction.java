package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Planificacion_familiar;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import com.framework.util.Util;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
//import com.framework.util.ManejadorParaclinicos;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Planificacion_familiarAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Planificacion_familiarAction.class);

	// Componentes //
//	@View
//	private Textbox tbxDes_vida_Marital;
	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;
	@View
	private Row rowAgudeza_visual;
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	@View
	private Textbox tbxMetodo_observaciones;
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
	private Div divTamizaje;

	@View
	private Div divCitologia;

	@View
	private Radiogroup rdbCitologias;

	@View
	private Textbox tbxNro_inscripcion;
	@View
	private Listbox lbxAtendida;

	@View
	private Textbox tbxEnfermedad_actual;
	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Radiogroup rdbLee;

	@View
	private Intbox ibxMenarquia;
	@View
	private Intbox ibxVida_marital;
	@View
	private Intbox ibxVida_obstetrica;
	@View
	private Intbox ibxObstetricos_g;
	@View
	private Intbox ibxObstetricos_p;
	@View
	private Intbox ibxObstetricos_a;
	@View
	private Intbox ibxObstetricos_c;
	@View
	private Intbox ibxObstetricos_v;
	@View
	private Intbox ibxNo_hijos;
	@View
	private Datebox dtbxUltimo_parto;
	@View
	private Radiogroup rdbLactancia_materna;
	@View
	private Radiogroup rdbLactancia_exclusiva;
	@View
	private Radiogroup rdbVida_marital_parto;
	@View
	private Datebox dtbxFum;
	@View
	private Radiogroup rdbCiclos;
	// private Textbox tbxRegular;
	// private Textbox tbxIrregular;
	@View
	private Radiogroup rdbAnticoncepcion;
	@View
	private Textbox tbxCual_anticoncepcion;
	@View
	private Datebox dtbxDesde_cuando;
	@View
	private Intbox ibxNo_parejas;
	@View
	private Radiogroup rdbPromiscuidad;
	@View
	private Textbox tbxComplicaciones_embarazo;
	@View
	private Datebox dtbxCitologia_cervico;
	@View
	private Textbox tbxResultado_citologia;

	@View
	private Textbox tbxMetodo1;
	@View
	private Textbox tbxNombre1;
	@View
	private Datebox dtbxDesde_metodo1;
	@View
	private Datebox dtbxHasta_metodo1;
	@View
	private Textbox tbxCausa1;
	@View
	private Textbox tbxMetodo2;
	@View
	private Textbox tbxNombre2;
	@View
	private Datebox dtbxDesde_metodo2;
	@View
	private Datebox dtbxHasta_metodo2;
	@View
	private Textbox tbxCausa2;
	@View
	private Textbox tbxMetodo3;

	@View
	private Textbox tbxNombre3;
	@View
	private Datebox dtbxDesde_metodo3;
	@View
	private Datebox dtbxHasta_metodo3;
	@View
	private Textbox tbxCausa3;
	@View
	private Textbox tbxMetodo4;
	@View
	private Textbox tbxNombre4;
	@View
	private Datebox dtbxDesde_metodo4;
	@View
	private Datebox dtbxHasta_metodo4;
	@View
	private Textbox tbxCausa4;
	@View
	private Textbox tbxMetodo5;
	@View
	private Textbox tbxNombre5;
	@View
	private Datebox dtbxDesde_metodo5;
	@View
	private Datebox dtbxHasta_metodo5;
	@View
	private Textbox tbxCausa5;
	@View
	private Textbox tbxMetodo6;
	@View
	private Textbox tbxNombre6;
	@View
	private Datebox dtbxDesde_metodo6;
	@View
	private Datebox dtbxHasta_metodo6;
	@View
	private Textbox tbxCausa6;

	@View
	private Radiogroup rdbFumadora;
	@View
	private Radiogroup rdbActiva;
	@View
	private Intbox ibxCigarrillos;
	@View
	private Checkbox chbHipertension;
	@View
	private Checkbox chbDiabetes;
	@View
	private Checkbox chbDispidemia;
	@View
	private Checkbox chbObesidad;
	@View
	private Checkbox chbAccidente_cerebrovascular;
	@View
	private Checkbox chbEpilepsia;
	@View
	private Checkbox chbMigrana;
	@View
	private Checkbox chbHipertiroidismo;
	@View
	private Checkbox chbHipotiroidismo;
	@View
	private Checkbox chbBocio;
	@View
	private Checkbox chbTuberculosis;
	@View
	private Checkbox chbEnf_coronaria;
	@View
	private Checkbox chbEnf_valvular;
	@View
	private Checkbox chbHepatitis;
	@View
	private Checkbox chbOtras_hepatitis;
	@View
	private Textbox tbxCual_hepatitis;
	@View
	private Checkbox chbLacteria;
	@View
	private Checkbox chbColectitis;
	@View
	private Checkbox chbColelitiasis;
	@View
	private Checkbox chbEnf_acido;
	@View
	private Checkbox chbTrombosis_venosa;
	@View
	private Checkbox chbVenas_varicosas;
	@View
	private Checkbox chbCa_mama;
	@View
	private Checkbox chbEnf_fibroquistica;
	@View
	private Checkbox chbMastopatia;
	@View
	private Checkbox chbHemorragia;
	@View
	private Checkbox chbEnf_ovarica;
	@View
	private Checkbox chbCa_cervico;
	@View
	private Checkbox chbLesion_intraepitelial;
	@View
	private Checkbox chbCa_endometrio;
	@View
	private Checkbox chbCa_ovarico;
	@View
	private Checkbox chbTricomoniasis;
	@View
	private Checkbox chbVaginosis;
	@View
	private Checkbox chbCandidiasis;
	@View
	private Checkbox chbEnf_pelvica;
	@View
	private Checkbox chbEtc;
	@View
	private Textbox tbxCual_etc;
	@View
	private Checkbox chbAnemia;
	@View
	private Textbox tbxCual_anemia;
	@View
	private Checkbox chbCoagulopatia;
	@View
	private Textbox tbxCual_coagulopatia;
	@View
	private Textbox tbxCirugia;
	@View
	private Textbox tbxDuracion_hospitalizacion;
	@View
	private Textbox tbxAlergias;
	@View
	private Textbox tbxUso_medicacion;
	@View
	private Listbox lbxDiabetes_mellitus;
	@View
	private Listbox lbxHipertension_arterial;
	@View
	private Listbox lbxCardio_mas;
	@View
	private Listbox lbxCardio_fem;
	@View
	private Listbox lbxCa_cuello_uterino;
	@View
	private Listbox lbxCa_mama2;
	@View
	private Listbox lbxCa_gastrico;
	@View
	private Listbox lbxCa_colorrectal;
	@View
	private Listbox lbxCa_prostata;
	@View
	private Checkbox chbGrisofolvina;
	@View
	private Checkbox chbAnticonvulsivante;
	@View
	private Checkbox chbRifamplilina;
	@View
	private Checkbox chbAntirenovirales;
	@View
	private Textbox tbxRevision_sistemas;
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
	@View
	private Radiogroup rdbSintomaticos_respiratorio;
	@View
	private Radiogroup rdbSintomaticos_piel;

	@View
	private Checkbox chbxPrueba_embarazo;
	@View
	private Checkbox chbxCitologia;
	@View
	private Checkbox chbxFrotitis;
	@View
	private Textbox tbxOtro;
	@View
	private Listbox lbxMetodo_adoptado;
	@View
	private Listbox lbxMetodo_adoptado_hombres;
//	private String via_ingreso = "1";
	@View
	private Radiogroup rdbMetodo_ets;
	// ------------------manuel--------------------
	private Planificacion_familiar planificacion_familiar;
	private String tipo_historia;
	private Long codigo_historia_anterior;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;
	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Datebox dtbxFecha_hasta;

	// Control
	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "toolbarbuttonPaciente_admisionado1",
			"macroImpresion_diagnostica", "toolbarbuttonPaciente_admisionado2",
			"northEditar", "formReceta", "gridOrdenes_servicio","divModuloRemisiones_externas" };

	// Ajustar a Macro
	private Citas cita;
	private Admision admision;
	private Opciones_via_ingreso opciones_via_ingreso;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Label lbFecha_hasta;
	@View
	private Label lbporque;
	@View
	private Label lbCantidad;
	@View
	private Intbox ibxCantidad;
	@View
	private Textbox tbxporque;
	@View
	private Label lbCigarrillos;

	private Planificacion_familiar historia_anterior;

	// Ocultar Campos Gineco-Obstetricos
	@View
	private Row rowObstetrico;
	@View
	private Toolbarbutton btGuardar;
	@View
	private Toolbarbutton btnCancelar;

	// macro impresion diagnostica
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	@View
	private Div divModuloFarmacologico;
	@View
	private Div divModuloOrdenamiento;
	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	@View
	private Div divModuloRemisiones_externas;

	@View
	private Listbox lbxTamizaje_cuello;

	private Remisiones_externasAction remisiones_externasAction;

	private String valida_admision;

	private String nro_ingreso_admision;

	private boolean cobrar_agudeza;

	@View
	private Toolbarbutton btnImprimir;

	@View
	private Listbox lbxFormato;

	public void alarmaExamenFisicoFr() {
		//log.info("Este es el método>>>>>>>>>>>>>");
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((!dbxRespiratoria.getText().isEmpty())
				&& (dbxRespiratoria.getValue() >= 16 && dbxRespiratoria
						.getValue() <= 20) && (edad >= 8 && edad < 45)) {
			//log.info("Este es el método primer if>>>>>>>");
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		} else if (edad < 8 || edad >= 45) {
			//log.info("Este es el método segundo if>>>>>>>");
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		} else if ((dbxRespiratoria.getText() != "")
				&& (dbxRespiratoria.getValue() < 16 || dbxRespiratoria
						.getValue() > 20) && (edad >= 8 && edad < 45)) {

			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:red");
		}
	}

	public void alarmaExamenFisicoFc() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() >= 60 && dbxCardiaca.getValue() <= 86)
				&& (edad >= 8 && edad < 45)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		} else if (edad < 8 || edad >= 45) {
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		} else if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() < 60 || dbxCardiaca.getValue() > 86)
				&& (edad >= 8 && edad < 45)) {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:red");
		}
	}

	@Override
	public void init() {
		try {
			listarCombos();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
		}

	}

	public void listarCombos() {
		listarParameter();
		listarAtendida();

		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar("ante_familiares");
		Utilidades.listarElementoListboxFromType(lbxDiabetes_mellitus, true,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxHipertension_arterial,
				true, elementos, true);
		Utilidades.listarElementoListbox(lbxCardio_mas, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxCardio_fem, true,
				getServiceLocator());
		Utilidades.listarElementoListboxFromType(lbxCa_cuello_uterino, true,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxCa_mama2, true, elementos,
				true);
		Utilidades.listarElementoListboxFromType(lbxCa_gastrico, true,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxCa_colorrectal, true,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxCa_prostata, true,
				elementos, true);
		Utilidades.listarElementoListbox(lbxTamizaje_cuello, true,
				getServiceLocator());
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

	// // Accion del formulario si es nuevo o consultar //
	// public void accionForm(boolean sw, String accion) throws Exception {
	// groupboxConsulta.setVisible(!sw);
	// groupboxEditar.setVisible(sw);
	//
	// if (!accion.equalsIgnoreCase("registrar")) {
	// buscarDatos();
	// btGuardar.setVisible(false);
	// } else {
	// btGuardar.setVisible(true);
	// limpiarDatos();
	// FormularioUtil.cargarRadiogroupsDefecto(this);
	// if (admision != null)
	// infoPacientes.setFecha_inicio(new Date());
	// cargarInformacion_paciente();
	//
	// valida_admision = null;
	// onMostrarModuloFarmacologico();
	// receta_ripsAction.obtenerBotonAgregar().setVisible(true);
	// onMostrarModuloOrdenamiento();
	// orden_servicioAction.obtenerBotonAgregar().setVisible(true);
	//
	// }
	// tbxAccion.setValue(accion);
	// }

	public void accionForm(OpcionesFormulario opciones_formulario, Object dato)
			throws Exception {
		tbxAccion.setValue(opciones_formulario.toString());
		if (opciones_formulario.equals(OpcionesFormulario.REGISTRAR)) {
			onOpcionFormularioRegistrar();
			dtbxUltimo_parto.setValue(null);
			dtbxFum.setValue(null);
			dtbxDesde_cuando.setValue(null);
			dtbxCitologia_cervico.setValue(null);
			dtbxDesde_metodo1.setValue(null);
			dtbxHasta_metodo1.setValue(null);
			dtbxDesde_metodo2.setValue(null);
			dtbxHasta_metodo2.setValue(null);
			dtbxDesde_metodo3.setValue(null);
			dtbxHasta_metodo3.setValue(null);
			dtbxDesde_metodo4.setValue(null);
			dtbxHasta_metodo4.setValue(null);
			dtbxDesde_metodo5.setValue(null);
			dtbxHasta_metodo5.setValue(null);
			dtbxDesde_metodo6.setValue(null);
			dtbxHasta_metodo6.setValue(null);
			dtbxFecha_hasta.setValue(null);

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
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		// manejadorParaclinicos.limpiarResultados_paraclinicos();
	}

	public void limpiarValidar() {
		tbxEnfermedad_actual
				.setStyle("text-transform:uppercase;background-color:white");
		tbxMotivo_consulta
				.setStyle("text-transform:uppercase;background-color:white");
		ibxNo_parejas.setStyle("background-color:white");
		tbxUso_medicacion
				.setStyle("text-transform:uppercase;background-color:white");
		tbxRevision_sistemas
				.setStyle("text-transform:uppercase;background-color:white");
		dbxCardiaca.setStyle("background-color:white");
		dbxRespiratoria.setStyle("background-color:white");
		dbxPeso.setStyle("background-color:white");
		dbxTalla.setStyle("background-color:white");
		dbxPresion.setStyle("background-color:white");
		dbxPresion2.setStyle("background-color:white");
		dbxImc.setStyle("background-color:white");

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		limpiarValidar();

		boolean valida = true;
		infoPacientes.validarInformacionPaciente();
		FormularioUtil.validarCamposObligatorios(tbxEnfermedad_actual,
				tbxMotivo_consulta, tbxUso_medicacion, tbxUso_medicacion,
				tbxRevision_sistemas, dbxCardiaca, dbxRespiratoria, dbxPeso,
				dbxTalla, dbxPresion, dbxPresion2, dbxImc);

		if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
			if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
				valida = macroAgudeza_visual.validarCamposObligatorios();
			}
		}

		if (valida)
			valida = receta_ripsAction.validarFormExterno();

		if (valida)
			valida = orden_servicioAction.validarFormExterno();

		if (valida)
			valida = remisiones_externasAction.validarRemision();

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

			List<Planificacion_familiar> lista_datos = getServiceLocator()
					.getPlanificacion_familiarService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Planificacion_familiar planificacion_familiar : lista_datos) {
				rowsResultado.appendChild(crearFilas(planificacion_familiar,
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

		final Planificacion_familiar planificacion_familiar = (Planificacion_familiar) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(planificacion_familiar.getCodigo_historia()
				+ ""));
		fila.appendChild(new Label(planificacion_familiar.getIdentificacion()
				+ ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(planificacion_familiar.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(planificacion_familiar.getTipo_historia()
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
						mostrarDatos(planificacion_familiar);
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
													eliminarDatos(planificacion_familiar);
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

	private Planificacion_familiar getBean() {
		Planificacion_familiar planificacion_familiar = new Planificacion_familiar();
		planificacion_familiar.setCodigo_empresa(empresa.getCodigo_empresa());
		planificacion_familiar.setTipo_historia(tipo_historia);
		planificacion_familiar
				.setCodigo_historia_anterior(planificacion_familiar
						.getCodigo_historia());

		// planificacion_familiar.setVia_ingreso(via_ingreso);
		planificacion_familiar
				.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		planificacion_familiar.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		planificacion_familiar.setIdentificacion(admision
				.getNro_identificacion());

		planificacion_familiar.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));

		planificacion_familiar.setUltimo_update(new Timestamp((new Date())
				.getTime()));

		planificacion_familiar.setNro_ingreso(admision.getNro_ingreso());
		planificacion_familiar
				.setNro_inscripcion(tbxNro_inscripcion.getValue());
		planificacion_familiar.setLee(rdbLee.getSelectedItem().getValue()
				.toString());
		planificacion_familiar.setEnfermedad_actual(tbxEnfermedad_actual
				.getValue());
//		planificacion_familiar.setDes_vida_Marital(tbxDes_vida_Marital
//				.getValue());

		planificacion_familiar
				.setMotivo_consulta(tbxMotivo_consulta.getValue());
		planificacion_familiar
				.setMenarquia((ibxMenarquia.getValue() != null ? ibxMenarquia
						.getValue() + "" : ""));
		planificacion_familiar
				.setVida_marital((ibxVida_marital.getValue() != null ? ibxVida_marital
						.getValue() + ""
						: ""));
		planificacion_familiar.setCitologias(rdbCitologias.getSelectedItem()
				.getValue().toString());

		planificacion_familiar.setVida_obstetrica((ibxVida_obstetrica
				.getValue() != null ? ibxVida_obstetrica.getValue() + "" : ""));
		planificacion_familiar
				.setObstetricos_g((ibxObstetricos_g.getValue() != null ? ibxObstetricos_g
						.getValue() + ""
						: ""));
		planificacion_familiar
				.setObstetricos_p((ibxObstetricos_p.getValue() != null ? ibxObstetricos_p
						.getValue() + ""
						: ""));
		planificacion_familiar
				.setObstetricos_a((ibxObstetricos_a.getValue() != null ? ibxObstetricos_a
						.getValue() + ""
						: ""));
		planificacion_familiar
				.setObstetricos_c((ibxObstetricos_c.getValue() != null ? ibxObstetricos_c
						.getValue() + ""
						: ""));
		planificacion_familiar
				.setObstetricos_v((ibxObstetricos_v.getValue() != null ? ibxObstetricos_v
						.getValue() + ""
						: ""));
		planificacion_familiar
				.setNo_hijos((ibxNo_hijos.getValue() != null ? ibxNo_hijos
						.getValue() + "" : ""));
		if (dtbxUltimo_parto.getValue() != null) {
			planificacion_familiar.setUltimo_parto(new Timestamp(
					dtbxUltimo_parto.getValue().getTime()));
		}
		planificacion_familiar.setLactancia_materna(rdbLactancia_materna
				.getSelectedItem().getValue().toString());
		planificacion_familiar.setLactancia_exclusiva(rdbLactancia_exclusiva
				.getSelectedItem().getValue().toString());
		planificacion_familiar.setVida_marital_parto(rdbVida_marital_parto
				.getSelectedItem().getValue().toString());
		if (dtbxFum.getValue() != null) {
			planificacion_familiar.setFum(new Timestamp(dtbxFum.getValue()
					.getTime()));
		}
		planificacion_familiar.setCiclos(rdbCiclos.getSelectedItem().getValue()
				.toString());
		planificacion_familiar.setRegular("");
		planificacion_familiar.setIrregular("");
		planificacion_familiar.setAnticoncepcion(rdbAnticoncepcion
				.getSelectedItem().getValue().toString());
		planificacion_familiar.setCual_anticoncepcion(tbxCual_anticoncepcion
				.getValue());
		if (dtbxDesde_cuando.getValue() != null) {
			planificacion_familiar.setDesde_cuando(new Timestamp(
					dtbxDesde_cuando.getValue().getTime()));
		}
		planificacion_familiar
				.setNo_parejas((ibxNo_parejas.getValue() != null ? ibxNo_parejas
						.getValue() + ""
						: ""));
		planificacion_familiar.setPromiscuidad(rdbPromiscuidad
				.getSelectedItem().getValue().toString());
		planificacion_familiar
				.setComplicaciones_embarazo(tbxComplicaciones_embarazo
						.getValue());
		if (dtbxCitologia_cervico.getValue() != null) {
			planificacion_familiar.setCitologia_cervico(new Timestamp(
					dtbxCitologia_cervico.getValue().getTime()));
		}
		planificacion_familiar.setResultado_citologia(tbxResultado_citologia
				.getValue());
		planificacion_familiar.setMetodo1(tbxMetodo1.getValue());
		planificacion_familiar.setNombre1(tbxNombre1.getValue());
		if (dtbxDesde_metodo1.getValue() != null) {
			planificacion_familiar.setDesde_metodo1(new Timestamp(
					dtbxDesde_metodo1.getValue().getTime()));
		}
		if (dtbxHasta_metodo1.getValue() != null) {
			planificacion_familiar.setHasta_metodo1(new Timestamp(
					dtbxHasta_metodo1.getValue().getTime()));
		}
		planificacion_familiar.setCausa1(tbxCausa1.getValue());
		planificacion_familiar.setMetodo2(tbxMetodo2.getValue());
		planificacion_familiar.setNombre2(tbxNombre2.getValue());
		if (dtbxDesde_metodo2.getValue() != null) {
			planificacion_familiar.setDesde_metodo2(new Timestamp(
					dtbxDesde_metodo2.getValue().getTime()));
		}
		if (dtbxHasta_metodo2.getValue() != null) {
			planificacion_familiar.setHasta_metodo2(new Timestamp(
					dtbxHasta_metodo2.getValue().getTime()));
		}
		planificacion_familiar.setCausa2(tbxCausa2.getValue());
		planificacion_familiar.setMetodo3(tbxMetodo3.getValue());
		planificacion_familiar.setNombre3(tbxNombre3.getValue());
		if (dtbxDesde_metodo3.getValue() != null) {
			planificacion_familiar.setDesde_metodo3(new Timestamp(
					dtbxDesde_metodo3.getValue().getTime()));
		}
		if (dtbxHasta_metodo3.getValue() != null) {
			planificacion_familiar.setHasta_metodo3(new Timestamp(
					dtbxHasta_metodo3.getValue().getTime()));
		}
		planificacion_familiar.setCausa3(tbxCausa3.getValue());
		planificacion_familiar.setMetodo4(tbxMetodo4.getValue());
		planificacion_familiar.setNombre4(tbxNombre4.getValue());
		if (dtbxDesde_metodo4.getValue() != null) {
			planificacion_familiar.setDesde_metodo4(new Timestamp(
					dtbxDesde_metodo4.getValue().getTime()));
		}
		if (dtbxHasta_metodo4.getValue() != null) {
			planificacion_familiar.setHasta_metodo4(new Timestamp(
					dtbxHasta_metodo4.getValue().getTime()));
		}
		planificacion_familiar.setCausa4(tbxCausa4.getValue());
		planificacion_familiar.setMetodo5(tbxMetodo5.getValue());
		planificacion_familiar.setNombre5(tbxNombre5.getValue());
		if (dtbxDesde_metodo5.getValue() != null) {
			planificacion_familiar.setDesde_metodo5(new Timestamp(
					dtbxDesde_metodo5.getValue().getTime()));
		}
		if (dtbxHasta_metodo5.getValue() != null) {
			planificacion_familiar.setHasta_metodo5(new Timestamp(
					dtbxHasta_metodo5.getValue().getTime()));
		}
		planificacion_familiar.setCausa5(tbxCausa5.getValue());
		planificacion_familiar.setMetodo6(tbxMetodo6.getValue());
		planificacion_familiar.setNombre6(tbxNombre6.getValue());
		if (dtbxDesde_metodo6.getValue() != null) {
			planificacion_familiar.setDesde_metodo6(new Timestamp(
					dtbxDesde_metodo6.getValue().getTime()));
		}
		if (dtbxHasta_metodo6.getValue() != null) {
			planificacion_familiar.setHasta_metodo6(new Timestamp(
					dtbxHasta_metodo6.getValue().getTime()));
		}
		planificacion_familiar.setCausa6(tbxCausa6.getValue());
		planificacion_familiar.setFumadora(rdbFumadora.getSelectedItem()
				.getValue().toString());
		planificacion_familiar.setActiva(rdbActiva.getSelectedItem().getValue()
				.toString());
		planificacion_familiar
				.setCigarrillos((ibxCigarrillos.getValue() != null ? ibxCigarrillos
						.getValue() + ""
						: ""));
		planificacion_familiar.setHipertension(chbHipertension.isChecked());
		planificacion_familiar.setDiabetes(chbDiabetes.isChecked());
		planificacion_familiar.setDispidemia(chbDispidemia.isChecked());
		planificacion_familiar.setObesidad(chbObesidad.isChecked());
		planificacion_familiar
				.setAccidente_cerebrovascular(chbAccidente_cerebrovascular
						.isChecked());
		planificacion_familiar.setEpilepsia(chbEpilepsia.isChecked());
		planificacion_familiar.setMigrana(chbMigrana.isChecked());
		planificacion_familiar.setHipertiroidismo(chbHipertiroidismo
				.isChecked());
		planificacion_familiar.setHipotiroidismo(chbHipotiroidismo.isChecked());
		planificacion_familiar.setBocio(chbBocio.isChecked());
		planificacion_familiar.setTuberculosis(chbTuberculosis.isChecked());
		planificacion_familiar.setEnf_coronaria(chbEnf_coronaria.isChecked());
		planificacion_familiar.setEnf_valvular(chbEnf_valvular.isChecked());
		planificacion_familiar.setHepatitis(chbHepatitis.isChecked());
		planificacion_familiar.setOtras_hepatitis(chbOtras_hepatitis
				.isChecked());
		planificacion_familiar.setCual_hepatitis(tbxCual_hepatitis.getValue());
		planificacion_familiar.setLacteria(chbLacteria.isChecked());
		planificacion_familiar.setColectitis(chbColectitis.isChecked());
		planificacion_familiar.setColelitiasis(chbColelitiasis.isChecked());
		planificacion_familiar.setEnf_acido(chbEnf_acido.isChecked());
		planificacion_familiar.setTrombosis_venosa(chbTrombosis_venosa
				.isChecked());
		planificacion_familiar.setVenas_varicosas(chbVenas_varicosas
				.isChecked());
		planificacion_familiar.setCa_mama(chbCa_mama.isChecked());
		planificacion_familiar.setEnf_fibroquistica(chbEnf_fibroquistica
				.isChecked());
		planificacion_familiar.setMastopatia(chbMastopatia.isChecked());
		planificacion_familiar.setHemorragia(chbHemorragia.isChecked());
		planificacion_familiar.setEnf_ovarica(chbEnf_ovarica.isChecked());
		planificacion_familiar.setCa_cervico(chbCa_cervico.isChecked());
		planificacion_familiar
				.setLesion_intraepitelial(chbLesion_intraepitelial.isChecked());
		planificacion_familiar.setCa_endometrio(chbCa_endometrio.isChecked());
		planificacion_familiar.setCa_ovarico(chbCa_ovarico.isChecked());
		planificacion_familiar.setTricomoniasis(chbTricomoniasis.isChecked());
		planificacion_familiar.setVaginosis(chbVaginosis.isChecked());
		planificacion_familiar.setCandidiasis(chbCandidiasis.isChecked());
		planificacion_familiar.setEnf_pelvica(chbEnf_pelvica.isChecked());
		planificacion_familiar.setEtc(chbEtc.isChecked());
		planificacion_familiar.setCual_etc(tbxCual_etc.getValue());
		planificacion_familiar.setAnemia(chbAnemia.isChecked());
		planificacion_familiar.setCual_anemia(tbxCual_anemia.getValue());
		planificacion_familiar.setCoagulopatia(chbCoagulopatia.isChecked());
		planificacion_familiar.setCual_coagulopatia(tbxCual_coagulopatia
				.getValue());
		planificacion_familiar.setCirugia(tbxCirugia.getValue());
		planificacion_familiar
				.setDuracion_hospitalizacion(tbxDuracion_hospitalizacion
						.getValue());
		planificacion_familiar.setAlergias(tbxAlergias.getValue());
		planificacion_familiar.setUso_medicacion(tbxUso_medicacion.getValue());
		planificacion_familiar.setDiabetes_mellitus(lbxDiabetes_mellitus
				.getSelectedItem().getValue().toString());
		planificacion_familiar
				.setHipertension_arterial(lbxHipertension_arterial
						.getSelectedItem().getValue().toString());
		planificacion_familiar.setCardio_mas(lbxCardio_mas.getSelectedItem()
				.getValue().toString());
		planificacion_familiar.setCardio_fem(lbxCardio_fem.getSelectedItem()
				.getValue().toString());
		planificacion_familiar.setCa_cuello_uterino(lbxCa_cuello_uterino
				.getSelectedItem().getValue().toString());
		planificacion_familiar.setCa_mama2(lbxCa_mama2.getSelectedItem()
				.getValue().toString());
		planificacion_familiar.setCa_prostata(lbxCa_prostata.getSelectedItem()
				.getValue().toString());
		planificacion_familiar.setCa_gastrico(lbxCa_gastrico.getSelectedItem()
				.getValue().toString());
		planificacion_familiar.setCa_colorrectal(lbxCa_colorrectal
				.getSelectedItem().getValue().toString());
		planificacion_familiar.setGrisofolvina(chbGrisofolvina.isChecked());
		planificacion_familiar.setAnticonvulsivante(chbAnticonvulsivante
				.isChecked());
		planificacion_familiar.setRifamplilina(chbRifamplilina.isChecked());
		planificacion_familiar.setAntirenovirales(chbAntirenovirales
				.isChecked());
		planificacion_familiar.setRevision_sistemas(tbxRevision_sistemas
				.getValue());
		planificacion_familiar
				.setCardiaca((dbxCardiaca.getValue() != null ? dbxCardiaca
						.getValue() + "" : ""));
		planificacion_familiar
				.setRespiratoria((dbxRespiratoria.getValue() != null ? dbxRespiratoria
						.getValue() + ""
						: ""));
		planificacion_familiar.setPeso((dbxPeso.getValue() != null ? dbxPeso
				.getValue() + "" : ""));
		planificacion_familiar.setTalla((dbxTalla.getValue() != null ? dbxTalla
				.getValue() + "" : ""));
		planificacion_familiar
				.setPresion((dbxPresion.getValue() != null ? dbxPresion
						.getValue() + "" : ""));
		planificacion_familiar
				.setPresion2((dbxPresion2.getValue() != null ? dbxPresion2
						.getValue() + "" : ""));
		planificacion_familiar.setInd_masa((dbxImc.getValue() != null ? dbxImc
				.getValue() + "" : ""));

		planificacion_familiar
				.setSintomaticos_respiratorio(rdbSintomaticos_respiratorio
						.getSelectedItem().getValue().toString());

		planificacion_familiar.setSintomaticos_piel(rdbSintomaticos_piel
				.getSelectedItem().getValue().toString());

		planificacion_familiar.setPrueba_embarazo(chbxPrueba_embarazo
				.isChecked() + "");
		planificacion_familiar.setCitologia(chbxCitologia.isChecked() + "");
		planificacion_familiar.setFrotis(chbxFrotitis.isChecked() + "");
		planificacion_familiar.setOtro(tbxOtro.getValue());
		planificacion_familiar.setMetodo_adoptado(lbxMetodo_adoptado
				.getSelectedItem().getValue().toString());
		planificacion_familiar
				.setMetodo_adoptado_hombres(lbxMetodo_adoptado_hombres
						.getSelectedItem().getValue().toString());
		planificacion_familiar.setRemision("");
		planificacion_familiar.setMetodo_ets(rdbMetodo_ets.getSelectedItem()
				.getValue().toString());
		planificacion_familiar.setMetodo_observaciones(tbxMetodo_observaciones
				.getValue());

		if (dtbxFecha_hasta.getValue() != null) {
			planificacion_familiar.setFecha_hasta(new Timestamp(dtbxFecha_hasta
					.getValue().getTime()));
		}
		planificacion_familiar.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		planificacion_familiar.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		planificacion_familiar
				.setCreacion_user(usuarios.getCodigo().toString());
		planificacion_familiar.setDelete_date(null);
		planificacion_familiar.setUltimo_user(usuarios.getCodigo().toString());
		planificacion_familiar.setDelete_user(null);
		planificacion_familiar.setTamizaje_cuello(lbxTamizaje_cuello
				.getSelectedItem().getValue().toString());

		planificacion_familiar.setTipo_historia(tipo_historia);

		if (historia_anterior != null) {
			planificacion_familiar
					.setCodigo_historia_anterior(historia_anterior
							.getCodigo_historia());
		}
		return planificacion_familiar;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {

			if (validarForm()) {
				// Cargamos los componentes //
				FormularioUtil.setUpperCase(groupboxEditar);

				planificacion_familiar = getBean();

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("historia_clinica", planificacion_familiar);
				datos.put("admision", admision);
				datos.put("anio_periodo", anio);
				datos.put("accion", tbxAccion.getValue());
				datos.put("cobrar_agudeza", cobrar_agudeza);
				datos.put("cita_seleccionada", cita);

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
				datos.put("cobrar_agudeza", cobrar_agudeza);
				datos.put(PARAM_AUTORIZACION, obtenerDatosAutorizacion());

				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);

				Agudeza_visual agudeza_visual = macroAgudeza_visual
						.obtenerAgudezaVisual();
				agudeza_visual.setAnio(anio);
				datos.put("agudeza_visual", agudeza_visual);

				datos.put("impresion_diagnostica", impresion_diagnostica);
				Anexo9_entidad anexo9_entidad = remisiones_externasAction
						.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);

				getServiceLocator().getPlanificacion_familiarService()
						.guardarDatos(datos);

				if (anexo9_entidad != null) {
					remisiones_externasAction.setCodigo_remision(anexo9_entidad
							.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(
							false);
				}
				mostrarDatosAutorizacion(datos);
				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(planificacion_familiar
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

				MensajesUtil.mensajeInformacion("Informacion ..",
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
		Planificacion_familiar planificacion_familiar = (Planificacion_familiar) obj;
		try {
			this.nro_ingreso_admision = planificacion_familiar.getNro_ingreso();
			// this.via_ingreso = planificacion_familiar.getVia_ingreso();
			infoPacientes.setCodigo_historia(planificacion_familiar
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(planificacion_familiar
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					planificacion_familiar.getUltimo_update());
			tbxNro_inscripcion.setValue(planificacion_familiar
					.getNro_inscripcion());

			cargarAgudezaVisual(planificacion_familiar);

			onMostrarModuloRemisiones();
			initMostrar_datos(planificacion_familiar);

			cargarInformacion_paciente();

			cargarRemisionInterna(planificacion_familiar);

			Utilidades.seleccionarRadio(rdbCitologias,
					planificacion_familiar.getCitologias());
			onCambiarCitologia();

			tbxEnfermedad_actual.setValue(planificacion_familiar
					.getEnfermedad_actual());
			tbxMotivo_consulta.setValue(planificacion_familiar
					.getMotivo_consulta());
			ibxMenarquia
					.setValue((planificacion_familiar.getMenarquia() != null && !planificacion_familiar
							.getMenarquia().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getMenarquia())
							: null);
			ibxVida_marital
					.setValue((planificacion_familiar.getVida_marital() != null && !planificacion_familiar
							.getVida_marital().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getVida_marital())
							: null);
//			tbxDes_vida_Marital.setValue(planificacion_familiar
//					.getDes_vida_Marital());
			ibxVida_obstetrica.setValue((planificacion_familiar
					.getVida_obstetrica() != null && !planificacion_familiar
					.getVida_obstetrica().isEmpty()) ? Integer
					.parseInt(planificacion_familiar.getVida_obstetrica())
					: null);
			ibxObstetricos_g
					.setValue((planificacion_familiar.getObstetricos_g() != null && !planificacion_familiar
							.getObstetricos_g().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_g())
							: null);
			ibxObstetricos_p
					.setValue((planificacion_familiar.getObstetricos_p() != null && !planificacion_familiar
							.getObstetricos_p().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_p())
							: null);
			ibxObstetricos_a
					.setValue((planificacion_familiar.getObstetricos_a() != null && !planificacion_familiar
							.getObstetricos_a().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_a())
							: null);
			ibxObstetricos_c
					.setValue((planificacion_familiar.getObstetricos_c() != null && !planificacion_familiar
							.getObstetricos_c().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_c())
							: null);

			ibxObstetricos_v
					.setValue((planificacion_familiar.getObstetricos_v() != null && !planificacion_familiar
							.getObstetricos_v().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_v())
							: null);

			ibxNo_hijos
					.setValue((planificacion_familiar.getNo_hijos() != null && !planificacion_familiar
							.getNo_hijos().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getNo_hijos())
							: null);
			dtbxUltimo_parto.setValue(planificacion_familiar.getUltimo_parto());
			Utilidades.seleccionarRadio(rdbLactancia_materna,
					planificacion_familiar.getLactancia_materna());
			Utilidades.seleccionarRadio(rdbLactancia_exclusiva,
					planificacion_familiar.getLactancia_exclusiva());
			Utilidades.seleccionarRadio(rdbVida_marital_parto,
					planificacion_familiar.getVida_marital_parto());
			dtbxFum.setValue(planificacion_familiar.getFum());
			Utilidades.seleccionarRadio(rdbCiclos,
					planificacion_familiar.getCiclos());
			Utilidades.seleccionarRadio(rdbAnticoncepcion,
					planificacion_familiar.getAnticoncepcion());
			tbxCual_anticoncepcion.setValue(planificacion_familiar
					.getCual_anticoncepcion());
			dtbxDesde_cuando.setValue(planificacion_familiar.getDesde_cuando());
			ibxNo_parejas
					.setValue((planificacion_familiar.getNo_parejas() != null && !planificacion_familiar
							.getNo_parejas().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getNo_parejas())
							: null);

			Utilidades.seleccionarRadio(rdbPromiscuidad,
					planificacion_familiar.getPromiscuidad());
			tbxComplicaciones_embarazo.setValue(planificacion_familiar
					.getComplicaciones_embarazo());
			dtbxCitologia_cervico.setValue(planificacion_familiar
					.getCitologia_cervico());
			tbxResultado_citologia.setValue(planificacion_familiar
					.getResultado_citologia());
			tbxMetodo1.setValue(planificacion_familiar.getMetodo1());
			tbxNombre1.setValue(planificacion_familiar.getNombre1());
			dtbxDesde_metodo1.setValue(planificacion_familiar
					.getDesde_metodo1());
			dtbxHasta_metodo1.setValue(planificacion_familiar
					.getHasta_metodo1());
			tbxCausa1.setValue(planificacion_familiar.getCausa1());
			tbxMetodo2.setValue(planificacion_familiar.getMetodo2());
			tbxNombre2.setValue(planificacion_familiar.getNombre2());
			dtbxDesde_metodo2.setValue(planificacion_familiar
					.getDesde_metodo2());
			dtbxHasta_metodo2.setValue(planificacion_familiar
					.getHasta_metodo2());
			tbxCausa2.setValue(planificacion_familiar.getCausa2());
			tbxMetodo3.setValue(planificacion_familiar.getMetodo3());
			tbxNombre3.setValue(planificacion_familiar.getNombre3());
			dtbxDesde_metodo3.setValue(planificacion_familiar
					.getDesde_metodo3());
			dtbxHasta_metodo3.setValue(planificacion_familiar
					.getHasta_metodo3());
			tbxCausa3.setValue(planificacion_familiar.getCausa3());
			tbxMetodo4.setValue(planificacion_familiar.getMetodo4());
			tbxNombre4.setValue(planificacion_familiar.getNombre4());
			dtbxDesde_metodo4.setValue(planificacion_familiar
					.getDesde_metodo4());
			dtbxHasta_metodo4.setValue(planificacion_familiar
					.getHasta_metodo4());
			tbxCausa4.setValue(planificacion_familiar.getCausa4());
			tbxMetodo5.setValue(planificacion_familiar.getMetodo5());
			tbxNombre5.setValue(planificacion_familiar.getNombre5());
			dtbxDesde_metodo5.setValue(planificacion_familiar
					.getDesde_metodo5());
			dtbxHasta_metodo5.setValue(planificacion_familiar
					.getHasta_metodo5());
			tbxCausa5.setValue(planificacion_familiar.getCausa5());
			tbxMetodo6.setValue(planificacion_familiar.getMetodo6());
			tbxNombre6.setValue(planificacion_familiar.getNombre6());
			dtbxDesde_metodo6.setValue(planificacion_familiar
					.getDesde_metodo6());
			dtbxHasta_metodo6.setValue(planificacion_familiar
					.getHasta_metodo6());
			tbxCausa6.setValue(planificacion_familiar.getCausa6());
			Utilidades.seleccionarRadio(rdbFumadora,
					planificacion_familiar.getFumadora());

			Utilidades.seleccionarRadio(rdbActiva,
					planificacion_familiar.getActiva());
			ibxCigarrillos
					.setValue((planificacion_familiar.getCigarrillos() != null && !planificacion_familiar
							.getCigarrillos().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getCigarrillos())
							: null);
			chbHipertension
					.setChecked(planificacion_familiar.getHipertension());
			chbDiabetes.setChecked(planificacion_familiar.getDiabetes());
			chbDispidemia.setChecked(planificacion_familiar.getDispidemia());
			chbObesidad.setChecked(planificacion_familiar.getObesidad());
			chbAccidente_cerebrovascular.setChecked(planificacion_familiar
					.getAccidente_cerebrovascular());
			chbEpilepsia.setChecked(planificacion_familiar.getEpilepsia());
			chbMigrana.setChecked(planificacion_familiar.getMigrana());
			chbHipertiroidismo.setChecked(planificacion_familiar
					.getHipertiroidismo());
			chbHipotiroidismo.setChecked(planificacion_familiar
					.getHipotiroidismo());
			chbBocio.setChecked(planificacion_familiar.getBocio());
			chbTuberculosis
					.setChecked(planificacion_familiar.getTuberculosis());
			chbEnf_coronaria.setChecked(planificacion_familiar
					.getEnf_coronaria());
			chbEnf_valvular
					.setChecked(planificacion_familiar.getEnf_valvular());
			chbHepatitis.setChecked(planificacion_familiar.getHepatitis());
			chbOtras_hepatitis.setChecked(planificacion_familiar
					.getOtras_hepatitis());

			if (chbOtras_hepatitis.isChecked()) {
				tbxCual_hepatitis.setVisible(true);
				tbxCual_hepatitis.setValue(planificacion_familiar
						.getCual_hepatitis());
			} else {
				tbxCual_hepatitis.setVisible(false);

			}
			chbLacteria.setChecked(planificacion_familiar.getLacteria());
			chbColectitis.setChecked(planificacion_familiar.getColectitis());
			chbColelitiasis
					.setChecked(planificacion_familiar.getColelitiasis());
			chbEnf_acido.setChecked(planificacion_familiar.getEnf_acido());
			chbTrombosis_venosa.setChecked(planificacion_familiar
					.getTrombosis_venosa());
			chbVenas_varicosas.setChecked(planificacion_familiar
					.getVenas_varicosas());
			chbCa_mama.setChecked(planificacion_familiar.getCa_mama());
			chbEnf_fibroquistica.setChecked(planificacion_familiar
					.getEnf_fibroquistica());
			chbMastopatia.setChecked(planificacion_familiar.getMastopatia());
			chbHemorragia.setChecked(planificacion_familiar.getHemorragia());
			chbEnf_ovarica.setChecked(planificacion_familiar.getEnf_ovarica());
			chbCa_cervico.setChecked(planificacion_familiar.getCa_cervico());
			chbLesion_intraepitelial.setChecked(planificacion_familiar
					.getLesion_intraepitelial());
			chbCa_endometrio.setChecked(planificacion_familiar
					.getCa_endometrio());
			chbCa_ovarico.setChecked(planificacion_familiar.getCa_ovarico());
			chbTricomoniasis.setChecked(planificacion_familiar
					.getTricomoniasis());
			chbVaginosis.setChecked(planificacion_familiar.getVaginosis());
			chbCandidiasis.setChecked(planificacion_familiar.getCandidiasis());
			chbEnf_pelvica.setChecked(planificacion_familiar.getEnf_pelvica());
			chbEtc.setChecked(planificacion_familiar.getEtc());
			tbxCual_etc.setValue(planificacion_familiar.getCual_etc());
			chbAnemia.setChecked(planificacion_familiar.getAnemia());
			tbxCual_anemia.setValue(planificacion_familiar.getCual_anemia());
			chbCoagulopatia
					.setChecked(planificacion_familiar.getCoagulopatia());
			tbxCual_coagulopatia.setValue(planificacion_familiar
					.getCual_coagulopatia());
			tbxCirugia.setValue(planificacion_familiar.getCirugia());
			tbxDuracion_hospitalizacion.setValue(planificacion_familiar
					.getDuracion_hospitalizacion());
			tbxAlergias.setValue(planificacion_familiar.getAlergias());
			tbxUso_medicacion.setValue(planificacion_familiar
					.getUso_medicacion());

			Utilidades.seleccionarListitem(lbxDiabetes_mellitus,
					planificacion_familiar.getDiabetes_mellitus());

			Utilidades.seleccionarListitem(lbxHipertension_arterial,
					planificacion_familiar.getHipertension_arterial());

			Utilidades.seleccionarListitem(lbxCardio_mas,
					planificacion_familiar.getCardio_mas());

			Utilidades.seleccionarListitem(lbxCardio_fem,
					planificacion_familiar.getCardio_fem());

			Utilidades.seleccionarListitem(lbxCa_cuello_uterino,
					planificacion_familiar.getCa_cuello_uterino());

			Utilidades.seleccionarListitem(lbxCa_mama2,
					planificacion_familiar.getCa_mama2());

			Utilidades.seleccionarListitem(lbxCa_prostata,
					planificacion_familiar.getCa_prostata());

			Utilidades.seleccionarListitem(lbxCa_gastrico,
					planificacion_familiar.getCa_gastrico());

			Utilidades.seleccionarListitem(lbxCa_colorrectal,
					planificacion_familiar.getCa_colorrectal());

			chbGrisofolvina
					.setChecked(planificacion_familiar.getGrisofolvina());
			chbAnticonvulsivante.setChecked(planificacion_familiar
					.getAnticonvulsivante());
			chbRifamplilina
					.setChecked(planificacion_familiar.getRifamplilina());
			chbAntirenovirales.setChecked(planificacion_familiar
					.getAntirenovirales());
			tbxRevision_sistemas.setValue(planificacion_familiar
					.getRevision_sistemas());
			dbxCardiaca
					.setValue((planificacion_familiar.getCardiaca() != null && !planificacion_familiar
							.getCardiaca().isEmpty()) ? Double
							.valueOf(planificacion_familiar.getCardiaca())
							: null);
			dbxRespiratoria
					.setValue((planificacion_familiar.getRespiratoria() != null && !planificacion_familiar
							.getRespiratoria().isEmpty()) ? Double
							.valueOf(planificacion_familiar.getRespiratoria())
							: null);
			dbxPeso.setValue((planificacion_familiar.getPeso() != null && !planificacion_familiar
					.getPeso().isEmpty()) ? Double
					.valueOf(planificacion_familiar.getPeso()) : null);
			dbxTalla.setValue((planificacion_familiar.getTalla() != null && !planificacion_familiar
					.getTalla().isEmpty()) ? Double
					.valueOf(planificacion_familiar.getTalla()) : null);
			dbxPresion
					.setValue((planificacion_familiar.getPresion() != null && !planificacion_familiar
							.getPresion().isEmpty()) ? Double
							.valueOf(planificacion_familiar.getPresion())
							: null);
			dbxPresion2
					.setValue((planificacion_familiar.getPresion2() != null && !planificacion_familiar
							.getPresion2().isEmpty()) ? Double
							.valueOf(planificacion_familiar.getPresion2())
							: null);
			dbxImc.setValue((planificacion_familiar.getInd_masa() != null && !planificacion_familiar
					.getInd_masa().isEmpty()) ? Double
					.valueOf(planificacion_familiar.getInd_masa()) : null);

			chbxPrueba_embarazo.setChecked(Boolean
					.getBoolean(planificacion_familiar.getPrueba_embarazo()));
			chbxCitologia.setChecked(Boolean.getBoolean(planificacion_familiar
					.getCitologia()));
			chbxFrotitis.setChecked(Boolean.getBoolean(planificacion_familiar
					.getFrotis()));
			tbxOtro.setValue(planificacion_familiar.getOtro());
			Utilidades.seleccionarListitem(lbxMetodo_adoptado,
					planificacion_familiar.getMetodo_adoptado());
			Utilidades.seleccionarListitem(lbxMetodo_adoptado_hombres,
					planificacion_familiar.getMetodo_adoptado_hombres());
			Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
					planificacion_familiar.getSintomaticos_respiratorio());
			Utilidades.seleccionarRadio(rdbSintomaticos_piel,
					planificacion_familiar.getSintomaticos_piel());

			dtbxFecha_hasta.setValue(planificacion_familiar.getFecha_hasta());

			Utilidades.seleccionarRadio(rdbMetodo_ets,
					planificacion_familiar.getMetodo_ets());
			tbxMetodo_observaciones.setValue(planificacion_familiar
					.getMetodo_observaciones());

			seleccion_tabaquismo();
			Utilidades.seleccionarListitem(lbxTamizaje_cuello,
					planificacion_familiar.getTamizaje_cuello());

			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString())) {
				infoPacientes.setCodigo_historia(planificacion_familiar
						.getCodigo_historia());
				inicializarVista(tipo_historia);
			}

			cargarImpresionDiagnostica(planificacion_familiar);

			valida_admision = planificacion_familiar.getNro_ingreso();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);

			tbxAccion.setText("modificar");

			inicializarVista(tipo_historia);
			cargarAnexo9_remision(planificacion_familiar);

			btnImprimir.setVisible(true);

			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Planificacion_familiar planificacion_familiar = (Planificacion_familiar) obj;
		try {
			int result = getServiceLocator().getPlanificacion_familiarService()
					.eliminar(planificacion_familiar);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
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
			Component[] abstractComponents) {
		try {
			String num = entero + "";
			for (Component abstractComponent : abstractComponents) {

				if (listbox.getSelectedItem().getValue().equals(num)) {
					abstractComponent.setVisible(true);
					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}
					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_radio2(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			//System.Out.Println("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue(null);
						((Textbox) abstractComponent).setReadonly(false);

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setValue(null);
						((Intbox) abstractComponent).setReadonly(false);

					}
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());
						((Datebox) abstractComponent).setReadonly(false);
						((Datebox) abstractComponent).setButtonVisible(true);

					}

					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
						((Checkbox) abstractComponent).setDisabled(false);

					}

				} else {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue(null);
						((Textbox) abstractComponent).setReadonly(true);

					}
					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setValue(null);
						((Intbox) abstractComponent).setReadonly(true);

					}

					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);
						((Datebox) abstractComponent).setReadonly(true);
						((Datebox) abstractComponent).setButtonVisible(false);

					}

					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
						((Checkbox) abstractComponent).setDisabled(true);
					}
				}
			}
		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked()) {
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);
						((Textbox) abstractComponent).setVisible(true);

					}
					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);
						((Textbox) abstractComponent).setVisible(false);
					}
				}
			}
		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	//
	// @Override
	// public void initHistoria() throws Exception {
	// if (admision != null) {
	// toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
	// .getNombreCompleto());
	// toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
	// .getNombreCompleto());
	//
	// if (opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
	// accionForm(true, "registrar");
	// btnCancelar.setVisible(false);
	// //log.info("no");
	//
	// } else {
	// accionForm(false, "mostrar");
	// btnCancelar.setVisible(true);
	// //log.info("visible");
	// }
	//
	// }
	// }
	//

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {

			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.PLANIFICACION_FAMILIAR);
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.PLANIFICACION_FAMILIAR);

			macroAgudeza_visual.inicializarMacro(this, admision,
					IVias_ingreso.PLANIFICACION_FAMILIAR);
			Utilidades.listarElementoListbox(lbxMetodo_adoptado, true,
					getServiceLocator());
			Utilidades.listarElementoListbox(lbxMetodo_adoptado_hombres, true,
					getServiceLocator());
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			tbxNro_inscripcion.setValue("1");
			dtbxUltimo_parto.setValue(null);
			dtbxFum.setValue(null);
			dtbxDesde_cuando.setValue(null);
			dtbxCitologia_cervico.setValue(null);
			dtbxDesde_metodo1.setValue(null);
			dtbxHasta_metodo1.setValue(null);
			dtbxDesde_metodo2.setValue(null);
			dtbxHasta_metodo2.setValue(null);
			dtbxDesde_metodo3.setValue(null);
			dtbxHasta_metodo3.setValue(null);
			dtbxDesde_metodo4.setValue(null);
			dtbxHasta_metodo4.setValue(null);
			dtbxDesde_metodo5.setValue(null);
			dtbxHasta_metodo5.setValue(null);
			dtbxDesde_metodo6.setValue(null);
			dtbxHasta_metodo6.setValue(null);
			dtbxFecha_hasta.setValue(null);

			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.PLANIFICACION_FAMILIAR);

			if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
				accionForm(OpcionesFormulario.CONSULTAR, null);
				btnCancelar.setVisible(true);
			} else if (admision != null) {
				toolbarbuttonPaciente_admisionado1.setLabel(admision
						.getPaciente().getNombreCompleto());
				toolbarbuttonPaciente_admisionado2.setLabel(admision
						.getPaciente().getNombreCompleto());

				//log.info(">empresa>>>"
						//+ admision.getPaciente().getCodigo_empresa());
				//log.info(">sucursal>>>"
						//+ admision.getPaciente().getCodigo_sucursal());
				//log.info(">nr identificacion>>>"
						//+ admision.getPaciente().getNro_identificacion());
				//log.info(">nombre paciente>>>"
						//+ admision.getPaciente().getNombreCompleto());

				if (admision.getAtendida()) {
					//log.info(">nombre paciente>>>"
							//+ admision.getPaciente().getNombreCompleto());
					opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
					Planificacion_familiar planificacion_familiar = new Planificacion_familiar();
					planificacion_familiar.setCodigo_empresa(empresa
							.getCodigo_empresa());
					planificacion_familiar.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					planificacion_familiar.setIdentificacion(admision
							.getNro_identificacion());
					planificacion_familiar.setNro_ingreso(admision
							.getNro_ingreso());

					//log.info(">nr ingreso>" + admision.getNro_ingreso());

					// planificacion_familiar.set

					planificacion_familiar = getServiceLocator()
							.getPlanificacion_familiarService().consultar(
									planificacion_familiar);
					//log.info(">>>>hisc planificacion familiar>>>>"
							//+ planificacion_familiar);

					if (planificacion_familiar != null) {
						accionForm(OpcionesFormulario.MOSTRAR,
								planificacion_familiar);
						infoPacientes.setCodigo_historia(planificacion_familiar
								.getCodigo_historia());
					} else {
						groupboxEditar.setVisible(false);
						throw new Exception(
								"NO hay una historia clinica relacionada a este paciente admitido");
					}
				} else {
					if (opciones_via_ingreso
							.equals(Opciones_via_ingreso.REGISTRAR)) {
						accionForm(OpcionesFormulario.REGISTRAR, null);
						btnCancelar.setVisible(false);
					} else {
						accionForm(OpcionesFormulario.CONSULTAR, null);
						btnCancelar.setVisible(true);
					}
				}
			}
			if (ServiciosDisponiblesUtils.realizarAgudeza(admision, anio)) {
				if (Agudeza_visualMacro.aplicaAgudeza(admision)) {
					cobrar_agudeza = true;
					macroAgudeza_visual.validarCamposObligatorios();
					rowAgudeza_visual.setVisible(true);
				}
			}
		}

	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}

	}

	@Override
	public void cargarInformacion_paciente() {
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

						Paciente paciente = admision.getPaciente();
						//log.info("paciente" + paciente);

						if (paciente.getSexo().toUpperCase().equals("F")) {
							//log.info("1" + paciente.getSexo());
							rowObstetrico.setVisible(true);
							lbxMetodo_adoptado.setVisible(true);
							lbxMetodo_adoptado_hombres.setVisible(false);

						} else {
							//log.info("2" + paciente.getSexo());
							rowObstetrico.setVisible(false);
							lbxMetodo_adoptado.setVisible(false);
							lbxMetodo_adoptado_hombres.setVisible(true);
						}

						//log.info("tbxAccion.getValue()  === > "
								//+ tbxAccion.getValue());

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("identificacion",
									admision.getNro_identificacion());
							parametros.put("order_desc", "order_desc");

							List<Planificacion_familiar> listado_historias = getServiceLocator()
									.getPlanificacion_familiarService().listar(
											parametros);

							// manuel
							if (!listado_historias.isEmpty()) {
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								historia_anterior = listado_historias.get(0);
								codigo_historia_anterior = historia_anterior
										.getCodigo_historia();
								cargarInformacion_historia_anterior(historia_anterior);
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia Clínica de Control/Evolucion");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia Clínica por Primera Vez");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								Planificacion_familiar planificacion_familiar = new Planificacion_familiar();
								planificacion_familiar
										.setCodigo_empresa(empresa
												.getCodigo_empresa());
								planificacion_familiar
										.setCodigo_sucursal(sucursal
												.getCodigo_sucursal());
								planificacion_familiar
										.setCodigo_historia(codigo_historia_anterior);

								planificacion_familiar = getServiceLocator()
										.getPlanificacion_familiarService()
										.consultar(planificacion_familiar);

								if (planificacion_familiar != null) {
									cargarInformacion_historia_anterior(planificacion_familiar);
								}

							}
						}

					}
				});

	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
	}

	public void onCambiarCitologia() {
		String value = rdbCitologias.getSelectedItem().getValue();
		if (value.equals("1")) {
			divCitologia.setVisible(true);
			divTamizaje.setVisible(false);
			lbxTamizaje_cuello.setSelectedIndex(6);
		} else {
			divCitologia.setVisible(false);
			divTamizaje.setVisible(true);
			lbxTamizaje_cuello.setSelectedIndex(0);
		}
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Planificacion_familiar planificacion_familiar = (Planificacion_familiar) historia_clinica;
		//log.info("hola");
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					idsExcluyentes);

			if (planificacion_familiar.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clínica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clínica de Control/Evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			if (planificacion_familiar.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clínica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clínica de Control/Evolucion");
			}
			((Button) getFellow("btGuardar")).setVisible(true);
		}

		codigo_historia_anterior = planificacion_familiar
				.getCodigo_historia_anterior();
		tipo_historia = planificacion_familiar.getTipo_historia();

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

	public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
		try {
			UtilidadSignosVitales.onMostrarAlertaTension(TA_sistolica,
					TA_diastolica);
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

	public void seleccion_tabaquismo() {
		if (rdbFumadora.getSelectedItem().getValue().equals("2")) {
			rdbActiva.setVisible(false);
			lbFecha_hasta.setVisible(false);
			dtbxFecha_hasta.setVisible(false);
			lbCigarrillos.setVisible(false);
			ibxCigarrillos.setVisible(false);
			ibxCigarrillos.setValue(0);
		} else {
			rdbActiva.setVisible(true);
			seleccion_activo_pasivo();
		}
	}

	public void seleccion_activo_pasivo() {
		if (rdbActiva.getSelectedItem().getValue().equals("2")) {
			lbFecha_hasta.setVisible(false);
			dtbxFecha_hasta.setVisible(false);
			lbCigarrillos.setVisible(false);
			ibxCigarrillos.setVisible(false);
			ibxCigarrillos.setValue(0);
		} else {
			lbFecha_hasta.setVisible(true);
			dtbxFecha_hasta.setVisible(true);
			lbCigarrillos.setVisible(true);
			ibxCigarrillos.setVisible(true);
			ibxCigarrillos.setValue(0);
		}
	}

	public void seleccion_metodo() {
		if (rdbMetodo_ets.getSelectedItem().getValue().equals("2")) {
			lbporque.setVisible(true);
			tbxporque.setVisible(true);
			lbCantidad.setVisible(false);
			ibxCantidad.setVisible(false);
		} else {

			lbCantidad.setVisible(true);
			ibxCantidad.setVisible(true);
			ibxCantidad.setValue(0);
			lbporque.setVisible(false);
			tbxporque.setVisible(false);

		}
	}

	private void cargarImpresionDiagnostica(
			Planificacion_familiar planificacion_familiar) throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(planificacion_familiar
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	private void cargarRemisionInterna(
			Planificacion_familiar planificacion_familiar) throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(planificacion_familiar
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(planificacion_familiar
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(planificacion_familiar
				.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	private void cargarAgudezaVisual(
			Planificacion_familiar planificacion_familiar) throws Exception {
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual.setCodigo_historia(planificacion_familiar
				.getCodigo_historia());
		agudeza_visual.setCodigo_empresa(planificacion_familiar
				.getCodigo_empresa());
		agudeza_visual.setCodigo_sucursal(planificacion_familiar
				.getCodigo_sucursal());

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultar(agudeza_visual);

		macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
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
			//log.info("VALIDA>>" + valida_admision + ">>>>>>>>>>>");
			//log.info("1");

		} else {
			adm = admision;
			//log.info("2");

		}
		//log.info("admision" + adm);

		Map parametros = new HashMap();
		parametros.put("nro_identificacion", nro_ingreso_admision);
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

	private void cargarAnexo9_remision(
			Planificacion_familiar planificacion_familiar) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(planificacion_familiar
				.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(planificacion_familiar.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);
		boolean creada = false;
		if (anexo9_entidad != null)
			creada = true;
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
		if (creada) {
			remisiones_externasAction.getBotonGenerar().setVisible(false);
		}
		remisiones_externasAction.setNro_historia(planificacion_familiar
				.getCodigo_historia());
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);
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
		
		//log.info("===> parametros " + parametros);

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
		serivicio1.append("PYP PLANIFICACION FAMILIAR");

		return serivicio1.toString();
	}

	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t").append(tbxMotivo_consulta.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		if (tbxEnfermedad_actual.getValue() != null
				&& !tbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxEnfermedad_actual.getValue()).append("\n");
		}

		// faltan los signos vitales para hacer el resumen

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

			dbxCardiaca.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxRespiratoria.setValue(enfermera_signos
					.getFrecuencia_respiratoria());
			dbxPeso.setValue(enfermera_signos.getPeso());
			dbxTalla.setValue(enfermera_signos.getTalla());
			dbxPresion.setValue(enfermera_signos.getTa_sistolica());
			dbxPresion2.setValue(enfermera_signos.getTa_diastolica());
			dbxImc.setValue(enfermera_signos.getImc());

			calcularTA(dbxPresion, dbxPresion2);
			calcularFrecuenciaCardiaca(dbxCardiaca);
			calcularFrecuenciaRespiratoria(dbxRespiratoria);
			calcularIMC(dbxPeso, dbxTalla, dbxImc);

		}
	}

	/*
	public void imprimir() throws Exception {
		String nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia.equals("")) {
			Messagebox.show("La historia no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Historia_pyp_planificacion_familiar");
		paramRequest.put("nro_historia", nro_historia);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		paramRequest.put("sexo", admision.getPaciente().getSexo());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

	*/
	
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
			Clients.evalJavaScript("window.open(\""+request.getContextPath()+"/pages/reports/planificacion_familiar_reporte.zul"+parametros_pagina+"\", '_blank');");
		}
	}
}
