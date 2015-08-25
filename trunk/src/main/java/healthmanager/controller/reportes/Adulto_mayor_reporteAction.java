/*
 * adulto_mayorAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Adulto_mayor;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Adulto_mayorService;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.ConvertidorXmlToMap;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.UtilidadSignosVitales;
import com.framework.util.Utilidades;

public class Adulto_mayor_reporteAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static Logger log = Logger.getLogger(Adulto_mayor_reporteAction.class);

	/*
	 'macroRemision_interna', 'lbxParameter', 'tbxValue', 'tbxAccion', 
	 'groupboxEditar', 'groupboxConsulta', 'rowsResultado', 'gridResultado', 
	 'lbxAtendida', 'btnCancelar', 'toolbarbuttonTipo_historia', 
	 'toolbarbuttonPaciente_admisionado1', 'toolbarbuttonPaciente_admisionado2',
	  'btGuardar', 'lbxTipo_historia', 'vboxParaclinicos', 'divModuloFarmacologico', 
	  'divModuloOrdenamiento', 'divModuloRemisiones_externas', 'btnImprimir', 'lbxFormato',
	 */
	// Componentes //
	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;
	@View
	private Row rowAgudeza_visual;
//	private Adulto_mayor historia_anterior;
	@View
	private Textbox tbxNro_inscripcion;
	

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Label lbxEnfremedad_actual;
	@View
	private Label lbxMotivo_consulta;
	@View
	private Label lbxDiagnostico;
	@View
	private Listbox lbxDiabetes;
	@View
	private Listbox lbxHipertension;
	@View
	private Listbox lbxDislipidemia;
	@View
	private Listbox lbxEnf_cerebrovascular;
	@View
	private Listbox lbxEnf_coronario;
	@View
	private Listbox lbxEnf_arterial;
	@View
	private Bandbox bandboxCardio_mas;
	@View
	private Bandbox bandboxCardio_fem;
	@View
	private Listbox lbxCa_cuello;
	@View
	private Listbox lbxCa_mama;
	@View
	private Listbox lbxCa_gastrico;
	@View
	private Listbox lbxCa_colorrectal;
	@View
	private Listbox lbxCa_prostata;
	@View
	private Label lbxMedico;
	@View
	private Label lbxQuirugicos;
	@View
	private Label lbxAlergicos;
	@View
	private Label lbxTraumaticos;
	@View
	private Label lbxHospitalizaciones;
	@View
	private Radiogroup rdbDiscapacidad;
	@View
	private Textbox tbxCual_discapacidad;
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
	private Checkbox chbPreclampsia;
	@View
	private Checkbox chbDiabetes_gestional;
	@View
	private Checkbox chbMenopausia;
	@View
	private Datebox dtbxFecha_citologia;
	@View
	private Textbox tbxResultado_citologia;
	@View
	private Datebox dtbxFecha_mama;
	@View
	private Textbox tbxResultado_mama;
	@View
	private Radiogroup rdbFecha_mamografia;
	@View
	private Textbox tbxResultado_mamografia;
	@View
	private Radiogroup rdbTabaquismo;
	@View
	private Radiogroup rdbActivoPasivo;
	@View
	private Datebox dtbxFecha_hasta;
	@View
	private Intbox ibxNo_cigarros;
	@View
	private Radiogroup rdbExposicion;
	@View
	private Checkbox chbEventual;
	@View
	private Checkbox chbSocial;
	@View
	private Checkbox chbSemanal;
	@View
	private Checkbox chbDiario;
	@View
	private Radiogroup rdbSustancias;
	@View
	private Textbox tbxCual_sustancias;
	@View
	private Radiogroup rdbEjercicio;
	@View
	private Textbox tbxCual_ejercicio;
	@View
	private Intbox ibxFrecuencia;
	@View
	private Intbox ibxVeces;
	@View
	private Intbox ibxIntensidad_horaria;
	@View
	private Checkbox chbCafalea;
	@View
	private Checkbox chbEpitaxis;
	@View
	private Checkbox chbTinitus;
	@View
	private Checkbox chbPalpitaciones;
	@View
	private Checkbox chbMareo;
	@View
	private Radiogroup rdbAlteraciones_visuales;
	@View
	private Textbox tbxCual_alteraciones_visuales;
	@View
	private Radiogroup rdbEdema;
	@View
	private Textbox tbxDonde_edema;
	@View
	private Checkbox chbPoliuria;
	@View
	private Checkbox chbPolidipsia;
	@View
	private Checkbox chbPolifagia;
	@View
	private Radiogroup rdbPerdida_peso;
	@View
	private Textbox tbxCuanto_peso;
	@View
	private Radiogroup rdbDolor_precordial;
	@View
	private Checkbox chbDisnea;
	@View
	private Checkbox chbDisartria;
	@View
	private Checkbox chbDiplopia;
	@View
	private Checkbox chbVertigo;
	@View
	private Radiogroup rdbDeficit_sensorial;
	@View
	private Textbox tbxDonde_deficit;
	@View
	private Checkbox chbParesias;
	@View
	private Checkbox chbParestesias;
	@View
	private Checkbox chbLipotimias;
	@View
	private Checkbox chbConvulsiones_focales;
	@View
	private Radiogroup rdbDolor_inferiores;
	@View
	private Checkbox chbDiarrea;
	@View
	private Checkbox chbDolor_abdo;
	@View
	private Checkbox chbMelena;
	@View
	private Checkbox chbHematoquexia;
	@View
	private Checkbox chbEstrenimiento;
	@View
	private Checkbox chbDeposiciones;
	@View
	private Checkbox chbAnorexia;
	@View
	private Checkbox chbEpigastralgia;
	@View
	private Checkbox chbDispepsia;
	@View
	private Checkbox chbHematemesis;
	@View
	private Checkbox chbSinusorragia;
	@View
	private Checkbox chbDispareunia;
	@View
	private Checkbox chbMastodinia;
	@View
	private Checkbox chbNicturia;
	@View
	private Checkbox chbUrgencia;
	@View
	private Radiogroup rdbOtros;
	@View
	private Textbox tbxCuales_otros;
	@View
	private Doublebox dbxSentado;
	@View
	private Doublebox dbxSentado2;
	@View
	private Doublebox dbxAcostado;
	@View
	private Doublebox dbxAcostado2;
	@View
	private Doublebox dbxPie;
	@View
	private Doublebox dbxPie2;
	@View
	private Doublebox dbxCardiaca;
	@View
	private Doublebox dbxPeso;
	@View
	private Doublebox dbxTalla;
	@View
	private Doublebox dbxImc;
	@View
	private Doublebox dbxCir_adbominal;
	@View
	private Doublebox dbxCir_cadera;
	@View
	private Doublebox dbxInd_cintura;
	@View
	private Textbox tbxExam_mama;
	@View
	private Label lbExam_mama;
	@View
	private Radiogroup rdbExc_mama;
	@View
	private Radiogroup rdbSintomaticos_respiratorio;
	@View
	private Radiogroup rdbSintomaticos_piel;
	@View
	private Label lbxHallazgos;
	@View
	private Label lbxTacto_rectal;
	@View
	private Label lbxPlan_intervencion;

	@View
	private Checkbox chbColesterol_hdl;
	@View
	private Checkbox chbColesterol_ldl;
	@View
	private Checkbox chbColesterol_total;
	@View
	private Checkbox chbTrigliceridos;
	@View
	private Checkbox chbCreatinina;
	@View
	private Checkbox chbUrianalisis;
	@View
	private Checkbox chbHemograma;
	@View
	private Checkbox chbGlicemia;
	@View
	private Checkbox chbCitologia;

	@View
	private Radiogroup rdbInferiores_esfuerzo;
	@View
	private Radiogroup rdbPrecordial_esfuerzo;

	@View
	private Listbox lbxTamizaje_cuello;

	
	private final String[] idsExcluyentes = {  "tbxValue",
			"northEditar", "macroImpresion_diagnostica",
			 "northEditar", "formReceta",
			"gridOrdenes_servicio" };

	// Ajustar a Macro
	
	private Admision admision;
//	private Citas cita;
//	private Opciones_via_ingreso opciones_via_ingreso;


	private String tipo_historia;
//	private String codigo_historia_anterior;

	// Ocultar Campos Gineco-Obstetricos
	@View
	private Row rowCitologia;
	@View
	private Row rowCitologia2;
	@View
	private Row rowObstetricos;
	@View
	private Row rowGinecologica;

	// Campos Ocultos
	@View
	private Label lbCual_discapacidad;
	@View
	private Label lbFecha_hasta;
	@View
	private Label lbNo_cigarros;
	@View
	private Label lbCual_sustancias;
	@View
	private Hlayout hlay_1;
	@View
	private Label lbEventual;
	@View
	private Label lbSocial;
	@View
	private Label lbSemanal;
	@View
	private Label lbDiario;
	@View
	private Row row2; // row ejercicios
	@View
	private Label lbCual_ejercicio;
	@View
	private Label lbFrecuencia;
	@View
	private Label lbVeces;
	@View
	private Label lbIntensidad_horaria;
	@View
	private Label lbResultado_mamografia;
	@View
	private Cell cellTacto_rectal;

	// macro impresion diagnostica
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	
//	private String valida_admision;

//	private String nro_ingreso_admision;

//	private boolean cobrar_agudeza;
	
	@View
	private Groupbox gbxContenido;
	@View
	private Image imgLogo;
	@View
	private Label lblTitulo;
	@View
	private Label lblMedicoTratante;
	@View
	private Label lblRegMedico;
	@View
	private Image imgFirma;
	
	private Paciente paciente;
	private Adulto_mayor adulto_mayor;
//	private ESexo sexo;
//	private Timestamp fecha;
	
	
	public void alarmaExamenFisicoFc() {
		UtilidadSignosVitales.validarMaxMin(dbxCardiaca, 61d, 39d, "Anormal",
				"Anormal", false);
	}

	@Override
	public void init() {
		try {
			/*
			crearResultadosParaclinicos();
			tbxNro_inscripcion.setValue("1");
			dtbxFecha_citologia.setValue(null);
			dtbxFecha_mama.setValue(null);
			*/
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			String codigo_historia = request.getParameter("codigo_historia");
			
			Long id_codigo_historia = null;
			
			if (codigo_historia != null && !codigo_historia.trim().isEmpty()
					&& codigo_historia.matches("[0-9]*$")) {
				id_codigo_historia = Long.parseLong(codigo_historia);
			}
			
			String nro_ingreso = request.getParameter("nro_ingreso");
			String nro_identificacion = request.getParameter("nro_identificacion");
			String codigo_empresa = request.getParameter("codigo_empresa");
			String codigo_sucursal = request.getParameter("codigo_sucursal");
			
			if(nro_identificacion!=null){
				admision = new Admision();
				admision.setCodigo_empresa(codigo_empresa);
				admision.setCodigo_sucursal(codigo_sucursal);
				admision.setNro_identificacion(nro_identificacion);
				admision.setNro_ingreso(nro_ingreso);
				admision = getServiceLocator().getServicio(AdmisionService.class).consultar(admision);
				if(admision!=null){
					macroImpresion_diagnostica.inicializarMacro(this, admision, 
							IVias_ingreso.ADULTO_MAYOR);
					paciente = admision.getPaciente();
//					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
//					fecha = paciente.getFecha_nacimiento();
				}
			}
			
			listarCombos();
			
			if(paciente.getSexo().equals("F")){
				rowObstetricos.setVisible(true);
			}else{
				rowObstetricos.setVisible(false);
			}
			
			if(id_codigo_historia!=null){
				adulto_mayor = new Adulto_mayor();
				adulto_mayor.setCodigo_empresa(codigo_empresa);
				adulto_mayor.setCodigo_sucursal(codigo_sucursal);
				adulto_mayor.setCodigo_historia(id_codigo_historia);
				adulto_mayor = getServiceLocator().getServicio(Adulto_mayorService.class).consultar(adulto_mayor);
				if(adulto_mayor!=null){
					if(adulto_mayor.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DETECcion DE ADULTO MAYOR\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DETECcion DE ADULTO MAYOR\nHISTORIA DE CONTROL");
					}
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(adulto_mayor.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,idsExcluyentes);
					mostrarDatos(adulto_mayor);
					
				}

				FormularioUtil.inicializarRadiogroupsDefecto(this);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	
	@Override
	public void params(Map<String, Object> map) {
		
	}

	public void listarCombos() {
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar("ante_familiares");
		Utilidades.listarElementoListboxFromType(lbxDiabetes, false, elementos,
				true);
		Utilidades.listarElementoListboxFromType(lbxHipertension, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxDislipidemia, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxEnf_cerebrovascular, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxEnf_coronario, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxEnf_arterial, false,
				elementos, true);
		// Utilidades.listarElementoListbox(lbxCardio_mas,
		// false,getServiceLocator());
		// Utilidades.listarElementoListbox(lbxCardio_fem,
		// false,getServiceLocator());
		Utilidades.listarElementoListboxFromType(lbxCa_cuello, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxCa_mama, false, elementos,
				true);
		Utilidades.listarElementoListboxFromType(lbxCa_gastrico, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxCa_colorrectal, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxCa_prostata, false,
				elementos, true);
		Utilidades.listarElementoListbox(lbxTamizaje_cuello, true,
				getServiceLocator());

	}

	
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
		//manejadorParaclinicos.limpiarResultados_paraclinicos();
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Adulto_mayor adulto_mayor = (Adulto_mayor) obj;
		try {
//			this.nro_ingreso_admision = adulto_mayor.getNro_ingreso();

			infoPacientes.setCodigo_historia(adulto_mayor.getCodigo_historia());
			infoPacientes.setFecha_inicio(adulto_mayor.getFecha_inicial());
			infoPacientes
					.setFecha_cierre(true, adulto_mayor.getUltimo_update());
			// infoPacientes.setFecha_cierre(true,
			// adultoMayor.getFecha_cierre());

			tbxNro_inscripcion.setValue(adulto_mayor.getNro_inscripcion());
			//onMostrarModuloRemisiones();
			initMostrar_datos(adulto_mayor);

			//cargarRemisionInterna(adulto_mayor);
			cargarAgudezaVisual(adulto_mayor);

			cargarInformacion_paciente();

			lbxEnfremedad_actual.setValue(adulto_mayor.getEnfremedad_actual());
			lbxMotivo_consulta.setValue(adulto_mayor.getMotivo_consulta());
			lbxDiagnostico.setValue(adulto_mayor.getDiagnostico());
			chbColesterol_hdl.setChecked(adulto_mayor.getColesterol_hdl());
			chbColesterol_ldl.setChecked(adulto_mayor.getColesterol_ldl());
			chbColesterol_total.setChecked(adulto_mayor.getColesterol_total());
			chbTrigliceridos.setChecked(adulto_mayor.getTrigliceridos());
			chbCreatinina.setChecked(adulto_mayor.getCreatinina());
			chbUrianalisis.setChecked(adulto_mayor.getUrianalisis());
			chbHemograma.setChecked(adulto_mayor.getHemograma());
			chbGlicemia.setChecked(adulto_mayor.getGlicemia());
			chbCitologia.setChecked(adulto_mayor.getCitologia());

			for (int i = 0; i < lbxDiabetes.getItemCount(); i++) {
				Listitem listitem = lbxDiabetes.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getDiabetes())) {
					listitem.setSelected(true);
					i = lbxDiabetes.getItemCount();
				}
			}

			for (int i = 0; i < lbxHipertension.getItemCount(); i++) {
				Listitem listitem = lbxHipertension.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getHipertension())) {
					listitem.setSelected(true);
					i = lbxHipertension.getItemCount();
				}
			}

			for (int i = 0; i < lbxDislipidemia.getItemCount(); i++) {
				Listitem listitem = lbxDislipidemia.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getDislipidemia())) {
					listitem.setSelected(true);
					i = lbxDislipidemia.getItemCount();
				}
			}

			for (int i = 0; i < lbxEnf_cerebrovascular.getItemCount(); i++) {
				Listitem listitem = lbxEnf_cerebrovascular.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getEnf_cerebrovascular())) {
					listitem.setSelected(true);
					i = lbxEnf_cerebrovascular.getItemCount();
				}
			}
			for (int i = 0; i < lbxEnf_coronario.getItemCount(); i++) {
				Listitem listitem = lbxEnf_coronario.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getEnf_coronario())) {
					listitem.setSelected(true);
					i = lbxEnf_coronario.getItemCount();
				}
			}
			for (int i = 0; i < lbxEnf_arterial.getItemCount(); i++) {
				Listitem listitem = lbxEnf_arterial.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getEnf_arterial())) {
					listitem.setSelected(true);
					i = lbxEnf_arterial.getItemCount();
				}
			}
			for (int i = 0; i < lbxCa_cuello.getItemCount(); i++) {
				Listitem listitem = lbxCa_cuello.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getCa_cuello())) {
					listitem.setSelected(true);
					i = lbxCa_cuello.getItemCount();
				}
			}
			for (int i = 0; i < lbxCa_mama.getItemCount(); i++) {
				Listitem listitem = lbxCa_mama.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getCa_mama())) {
					listitem.setSelected(true);
					i = lbxCa_mama.getItemCount();
				}
			}
			for (int i = 0; i < lbxCa_gastrico.getItemCount(); i++) {
				Listitem listitem = lbxCa_gastrico.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getCa_gastrico())) {
					listitem.setSelected(true);
					i = lbxCa_gastrico.getItemCount();
				}
			}
			for (int i = 0; i < lbxCa_gastrico.getItemCount(); i++) {
				Listitem listitem = lbxCa_gastrico.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getCa_colorrectal())) {
					listitem.setSelected(true);
					i = lbxCa_gastrico.getItemCount();
				}
			}
			for (int i = 0; i < lbxCa_prostata.getItemCount(); i++) {
				Listitem listitem = lbxCa_prostata.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(adulto_mayor.getCa_prostata())) {
					listitem.setSelected(true);
					i = lbxCa_prostata.getItemCount();
				}
			}

			mostrarXmlTextbox(bandboxCardio_mas,
					ConvertidorXmlToMap.convertirToMap(adulto_mayor
							.getCardio_mas()));
			mostrarXmlTextbox(bandboxCardio_fem,
					ConvertidorXmlToMap.convertirToMap(adulto_mayor
							.getCardio_fem()));

			lbxMedico.setValue(adulto_mayor.getMedico());
			lbxQuirugicos.setValue(adulto_mayor.getQuirugicos());
			lbxAlergicos.setValue(adulto_mayor.getAlergicos());
			lbxTraumaticos.setValue(adulto_mayor.getTraumaticos());
			lbxHospitalizaciones.setValue(adulto_mayor.getHospitalizaciones());
			Utilidades.seleccionarRadio(rdbDiscapacidad,
					adulto_mayor.getDiscapacidad());

			if (adulto_mayor.getDiscapacidad().equals("1")) {
				lbCual_discapacidad.setVisible(true);
				tbxCual_discapacidad.setVisible(true);
				tbxCual_discapacidad.setValue(adulto_mayor
						.getCual_discapacidad());
			} else {
				lbCual_discapacidad.setVisible(false);
				tbxCual_discapacidad.setVisible(false);

			}

			ibxObstetricos_g
					.setValue((adulto_mayor.getObstetricos_g() != null && !adulto_mayor
							.getObstetricos_g().isEmpty()) ? Integer
							.parseInt(adulto_mayor.getObstetricos_g()) : null);
			ibxObstetricos_p
					.setValue((adulto_mayor.getObstetricos_p() != null && !adulto_mayor
							.getObstetricos_p().isEmpty()) ? Integer
							.parseInt(adulto_mayor.getObstetricos_p()) : null);
			ibxObstetricos_a
					.setValue((adulto_mayor.getObstetricos_a() != null && !adulto_mayor
							.getObstetricos_a().isEmpty()) ? Integer
							.parseInt(adulto_mayor.getObstetricos_a()) : null);
			ibxObstetricos_c
					.setValue((adulto_mayor.getObstetricos_c() != null && !adulto_mayor
							.getObstetricos_c().isEmpty()) ? Integer
							.parseInt(adulto_mayor.getObstetricos_c()) : null);

			ibxObstetricos_v
					.setValue((adulto_mayor.getObstetricos_v() != null && !adulto_mayor
							.getObstetricos_v().isEmpty()) ? Integer
							.parseInt(adulto_mayor.getObstetricos_v()) : null);

			chbPreclampsia.setChecked(adulto_mayor.getPreclampsia());
			chbDiabetes_gestional.setChecked(adulto_mayor
					.getDiabetes_gestional());
			chbMenopausia.setChecked(adulto_mayor.getMenopausia());

			dtbxFecha_citologia.setValue(adulto_mayor.getFecha_hasta());
			tbxResultado_citologia.setValue(adulto_mayor
					.getResultado_citologia());
			dtbxFecha_mama.setValue(adulto_mayor.getFecha_hasta());
			tbxResultado_mama.setValue(adulto_mayor.getResultado_mama());
			Utilidades.seleccionarRadio(rdbFecha_mamografia,
					adulto_mayor.getFecha_mamografia());

			if (adulto_mayor.getFecha_mamografia().equals("1")) {
				lbResultado_mamografia.setVisible(true);
				tbxResultado_mamografia.setVisible(true);
				tbxResultado_mamografia.setValue(adulto_mayor
						.getResultado_mamografia());
			} else {
				lbResultado_mamografia.setVisible(false);
				tbxResultado_mamografia.setVisible(false);

			}

			Utilidades.seleccionarRadio(rdbTabaquismo,
					adulto_mayor.getTabaquismo());
			seleccion_tabaquismo();
			Utilidades.seleccionarRadio(rdbActivoPasivo,
					adulto_mayor.getActivoPasivo());
			seleccion_activo_pasivo();

			if (adulto_mayor.getTabaquismo().equals("1")) {
				dtbxFecha_hasta.setValue(adulto_mayor.getFecha_hasta());
				ibxNo_cigarros
						.setValue((adulto_mayor.getNo_cigarros() != null && !adulto_mayor
								.getNo_cigarros().isEmpty()) ? Integer
								.parseInt(adulto_mayor.getNo_cigarros()) : null);
			}

			Utilidades.seleccionarRadio(rdbExposicion,
					adulto_mayor.getExposicion());

			if (adulto_mayor.getExposicion().equals("1")) {

				hlay_1.setVisible(true);
				lbEventual.setVisible(true);
				lbSocial.setVisible(true);
				lbSemanal.setVisible(true);
				lbDiario.setVisible(true);

				chbEventual.setVisible(true);
				chbSocial.setVisible(true);
				chbSemanal.setVisible(true);
				chbDiario.setVisible(true);

				chbEventual.setChecked(adulto_mayor.getEventual());
				chbSocial.setChecked(adulto_mayor.getSocial());
				chbSemanal.setChecked(adulto_mayor.getSemanal());
				chbDiario.setChecked(adulto_mayor.getDiario());
			} else {

				hlay_1.setVisible(false);
				lbEventual.setVisible(false);
				lbSocial.setVisible(false);
				lbSemanal.setVisible(false);
				lbDiario.setVisible(false);

				chbEventual.setVisible(false);
				chbSocial.setVisible(false);
				chbSemanal.setVisible(false);
				chbDiario.setVisible(false);

			}

			Utilidades.seleccionarRadio(rdbSustancias,
					adulto_mayor.getSustancias());

			if (adulto_mayor.getSustancias().equals("1")) {
				lbCual_sustancias.setVisible(true);
				tbxCual_sustancias.setVisible(true);
				tbxCual_sustancias.setValue(adulto_mayor.getCual_sustancias());
			} else {
				lbCual_sustancias.setVisible(false);
				tbxCual_sustancias.setVisible(false);

			}

			Utilidades.seleccionarRadio(rdbEjercicio,
					adulto_mayor.getEjercicio());

			if (adulto_mayor.getEjercicio().equals("1")) {
				row2.setVisible(true);
				lbCual_ejercicio.setVisible(true);
				lbFrecuencia.setVisible(true);
				lbVeces.setVisible(true);
				lbIntensidad_horaria.setVisible(true);

				tbxCual_ejercicio.setVisible(true);
				ibxFrecuencia.setVisible(true);
				ibxVeces.setVisible(true);
				ibxIntensidad_horaria.setVisible(true);

				tbxCual_ejercicio.setValue(adulto_mayor.getCual_ejercicio());
				ibxFrecuencia
						.setValue((adulto_mayor.getFrecuencia() != null && !adulto_mayor
								.getFrecuencia().isEmpty()) ? Integer
								.parseInt(adulto_mayor.getFrecuencia()) : null);
				ibxVeces.setValue((adulto_mayor.getVeces() != null && !adulto_mayor
						.getVeces().isEmpty()) ? Integer.parseInt(adulto_mayor
						.getVeces()) : null);
				ibxIntensidad_horaria.setValue((adulto_mayor
						.getIntensidad_horaria() != null && !adulto_mayor
						.getIntensidad_horaria().isEmpty()) ? Integer
						.parseInt(adulto_mayor.getIntensidad_horaria()) : null);
			} else {
				row2.setVisible(false);
				lbCual_ejercicio.setVisible(false);
				lbFrecuencia.setVisible(false);
				lbVeces.setVisible(false);
				lbIntensidad_horaria.setVisible(false);

				tbxCual_ejercicio.setVisible(false);
				ibxFrecuencia.setVisible(false);
				ibxVeces.setVisible(false);
				ibxIntensidad_horaria.setVisible(false);

			}

			chbCafalea.setChecked(adulto_mayor.getCafalea());
			chbEpitaxis.setChecked(adulto_mayor.getEpitaxis());
			chbTinitus.setChecked(adulto_mayor.getTinitus());
			chbPalpitaciones.setChecked(adulto_mayor.getPalpitaciones());
			chbMareo.setChecked(adulto_mayor.getMareo());
			Utilidades.seleccionarRadio(rdbAlteraciones_visuales,
					adulto_mayor.getAlteraciones_visuales());
			tbxCual_alteraciones_visuales.setValue(adulto_mayor
					.getCual_alteraciones_visuales());
			Utilidades.seleccionarRadio(rdbEdema, adulto_mayor.getEdema());
			tbxDonde_edema.setValue(adulto_mayor.getDonde_edema());
			chbPoliuria.setChecked(adulto_mayor.getPoliuria());
			chbPolidipsia.setChecked(adulto_mayor.getPolidipsia());
			chbPolifagia.setChecked(adulto_mayor.getPolifagia());
			Utilidades.seleccionarRadio(rdbPerdida_peso,
					adulto_mayor.getPerdida_peso());
			tbxCuanto_peso.setValue(adulto_mayor.getCuanto_peso());
			Utilidades.seleccionarRadio(rdbDolor_precordial,
					adulto_mayor.getDolor_precordial());
			seleccion_radio2(rdbDolor_precordial,
					new AbstractComponent[] { rdbPrecordial_esfuerzo });
			Utilidades.seleccionarRadio(rdbPrecordial_esfuerzo,
					adulto_mayor.getPrecordial_esfuerzo());
			chbDisnea.setChecked(adulto_mayor.getDisnea());
			chbDisartria.setChecked(adulto_mayor.getDisartria());
			chbDiplopia.setChecked(adulto_mayor.getDiplopia());
			chbVertigo.setChecked(adulto_mayor.getVertigo());
			Utilidades.seleccionarRadio(rdbDeficit_sensorial,
					adulto_mayor.getDeficit_sensorial());
			tbxDonde_deficit.setValue(adulto_mayor.getDonde_deficit());
			chbParesias.setChecked(adulto_mayor.getParesias());
			chbParestesias.setChecked(adulto_mayor.getParestesias());
			chbLipotimias.setChecked(adulto_mayor.getLipotimias());
			chbConvulsiones_focales.setChecked(adulto_mayor
					.getConvulsiones_focales());
			Utilidades.seleccionarRadio(rdbDolor_inferiores,
					adulto_mayor.getDolor_inferiores());
			seleccion_radio2(rdbDolor_inferiores,
					new AbstractComponent[] { rdbInferiores_esfuerzo });
			Utilidades.seleccionarRadio(rdbInferiores_esfuerzo,
					adulto_mayor.getInferiores_esfuerzo());
			chbDiarrea.setChecked(adulto_mayor.getDiarrea());
			chbDolor_abdo.setChecked(adulto_mayor.getDolor_abdo());
			chbMelena.setChecked(adulto_mayor.getMelena());
			chbHematoquexia.setChecked(adulto_mayor.getHematoquexia());
			chbEstrenimiento.setChecked(adulto_mayor.getEstrenimiento());
			chbDeposiciones.setChecked(adulto_mayor.getDeposiciones());
			chbAnorexia.setChecked(adulto_mayor.getAnorexia());
			chbEpigastralgia.setChecked(adulto_mayor.getEpigastralgia());
			chbDispepsia.setChecked(adulto_mayor.getDispepsia());
			chbHematemesis.setChecked(adulto_mayor.getHematemesis());
			chbSinusorragia.setChecked(adulto_mayor.getSinusorragia());
			chbDispareunia.setChecked(adulto_mayor.getDispareunia());
			chbMastodinia.setChecked(adulto_mayor.getMastodinia());
			chbNicturia.setChecked(adulto_mayor.getNicturia());
			chbUrgencia.setChecked(adulto_mayor.getUrgencia());
			Utilidades.seleccionarRadio(rdbOtros, adulto_mayor.getOtros());
			tbxCuales_otros.setValue(adulto_mayor.getCuales_otros());

			dbxSentado
					.setValue((adulto_mayor.getSentado() != null && !adulto_mayor
							.getSentado().isEmpty()) ? Double
							.valueOf(adulto_mayor.getSentado()) : null);

			dbxSentado2
					.setValue((adulto_mayor.getSentado2() != null && !adulto_mayor
							.getSentado2().isEmpty()) ? Double
							.valueOf(adulto_mayor.getSentado2()) : null);

			dbxAcostado
					.setValue((adulto_mayor.getAcostado() != null && !adulto_mayor
							.getAcostado().isEmpty()) ? Double
							.valueOf(adulto_mayor.getAcostado()) : null);

			dbxAcostado2
					.setValue((adulto_mayor.getAcostado2() != null && !adulto_mayor
							.getAcostado2().isEmpty()) ? Double
							.valueOf(adulto_mayor.getAcostado2()) : null);

			dbxPie.setValue((adulto_mayor.getPie() != null && !adulto_mayor
					.getPie().isEmpty()) ? ConvertidorDatosUtil
					.convertirDato(adulto_mayor.getPie()) : null);

			dbxPie2.setValue((adulto_mayor.getPie2() != null && !adulto_mayor
					.getPie2().isEmpty()) ? ConvertidorDatosUtil
					.convertirDato(adulto_mayor.getPie2()) : null);

			dbxCardiaca
					.setValue((adulto_mayor.getCardiaca() != null && !adulto_mayor
							.getCardiaca().isEmpty()) ? Double
							.valueOf(adulto_mayor.getCardiaca()) : null);
			dbxPeso.setValue((adulto_mayor.getPeso() != null && !adulto_mayor
					.getPeso().isEmpty()) ? ConvertidorDatosUtil
					.convertirDato(adulto_mayor.getPeso()) : null);
			dbxTalla.setValue((adulto_mayor.getTalla() != null && !adulto_mayor
					.getTalla().isEmpty()) ? ConvertidorDatosUtil
					.convertirDato(adulto_mayor.getTalla()) : null);
			dbxImc.setValue((adulto_mayor.getImc() != null && !adulto_mayor
					.getImc().isEmpty()) ? ConvertidorDatosUtil
					.convertirDato(adulto_mayor.getImc()) : null);

			dbxCir_adbominal.setValue(ConvertidorDatosUtil
					.convertirDato(adulto_mayor.getCir_adbominal()));
			dbxCir_cadera.setValue(ConvertidorDatosUtil
					.convertirDato(adulto_mayor.getCir_cadera()));
			dbxInd_cintura.setValue(ConvertidorDatosUtil
					.convertirDato(adulto_mayor.getInd_cintura()));

			Utilidades
					.seleccionarRadio(rdbExc_mama, adulto_mayor.getExc_mama());

			if (adulto_mayor.getExc_mama().equals("1")) {
				lbExam_mama.setVisible(true);
				tbxExam_mama.setVisible(true);
				tbxExam_mama.setValue(adulto_mayor.getExam_mama());
			} else {
				lbExam_mama.setVisible(false);
				tbxExam_mama.setVisible(false);
			}

			Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
					adulto_mayor.getSintomaticos_respiratorio());
			Utilidades.seleccionarRadio(rdbSintomaticos_piel,
					adulto_mayor.getSintomaticos_piel());
			lbxHallazgos.setValue(adulto_mayor.getHallazgos());
			lbxTacto_rectal.setValue(adulto_mayor.getTacto_rectal());

			lbxPlan_intervencion.setValue(adulto_mayor.getPlan_intervencion());

			tipo_historia = adulto_mayor.getTipo_historia();

			Utilidades.seleccionarListitem(lbxTamizaje_cuello,
					adulto_mayor.getTamizaje_cuello());

			
			cargarImpresionDiagnostica(adulto_mayor);

//			valida_admision = adulto_mayor.getNro_ingreso();
			inicializarVista(tipo_historia);

			

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	
	
	public void seleccion(Listbox listbox, int entero,
			AbstractComponent[] abstractComponents) {
		try {
			//log.info("" + listbox.getSelectedItem().getValue());

			String num = entero + "";
			for (AbstractComponent abstractComponent : abstractComponents) {

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
			//log.info("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_radio2(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			System.out.println("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setValue(null);
						((Intbox) abstractComponent).setReadonly(false);

					}
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					if (abstractComponent instanceof Radiogroup) {
						Radiogroup rg = (Radiogroup) abstractComponent;
						Utilidades.seleccionarRadio(
								((Radiogroup) abstractComponent), "2");
						for (Radio r : rg.getItems()) {
							r.setDisabled(false);
						}
					}
				} else {

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setValue(null);
						((Intbox) abstractComponent).setReadonly(true);

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}

					if (abstractComponent instanceof Radiogroup) {
						Radiogroup rg = (Radiogroup) abstractComponent;
						// Utilidades.seleccionarRadio(((Radiogroup)
						// abstractComponent),"2");
						for (Radio r : rg.getItems()) {
							r.setDisabled(true);
							r.setChecked(false);
						}
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
					if (abstractComponent instanceof Listbox) {
						if (((Listbox) abstractComponent).getItemCount() > 0) {
							((Listbox) abstractComponent).setSelectedIndex(0);
						}
						Utilidades.listarElementoListbox(
								((Listbox) abstractComponent), true,
								getServiceLocator());
					}

					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
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

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Listbox) {
						((Listbox) abstractComponent).getChildren().clear();
						for (int i = 0; i < ((Listbox) abstractComponent)
								.getItemCount(); i++) {
							Listitem listitem = ((Listbox) abstractComponent)
									.getItemAtIndex(i);
							if (listitem.getValue().toString().equals("")) {
								listitem.setSelected(true);
								i = ((Listbox) abstractComponent)
										.getItemCount();
							}
						}

					}
					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
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
		
		if (!admision.getAtendida()) {
			Notificaciones
					.mostrarNotificacionInformacion(
							"Advertencia",
							"Recuerde practicar exámen de mama y educacion al paciente",
							10000);
		}
		
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

						
					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		
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
			// UtilidadSignosVitales.onMostrarAlertaTension(TA_sistolica,
			// TA_diastolica);
			UtilidadSignosVitales
					.onCalcularTension(TA_sistolica, TA_diastolica);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularIcadera(Doublebox dbxCir_adbominal,
			Doublebox dbxCir_cadera, Doublebox dbxInd_cintura) {
		try {
			// UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
			// UtilidadSignosVitales.TALLA_METROS);
			double icc = (dbxCir_adbominal.getValue() / dbxCir_cadera
					.getValue());
			dbxInd_cintura.setValue(icc);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void seleccion_tabaquismo() {
		if (rdbTabaquismo.getSelectedItem().getValue().equals("2")) {
			rdbActivoPasivo.setVisible(false);
			lbFecha_hasta.setVisible(false);
			dtbxFecha_hasta.setVisible(false);
			lbNo_cigarros.setVisible(false);
			ibxNo_cigarros.setVisible(false);
			ibxNo_cigarros.setValue(0);
		} else {
			rdbActivoPasivo.setVisible(true);
			seleccion_activo_pasivo();
		}
	}

	public void seleccion_activo_pasivo() {
		if (rdbActivoPasivo.getSelectedItem().getValue().equals("2")) {
			lbFecha_hasta.setVisible(false);
			dtbxFecha_hasta.setVisible(false);
			lbNo_cigarros.setVisible(false);
			ibxNo_cigarros.setVisible(false);
			ibxNo_cigarros.setValue(0);
		} else {
			lbFecha_hasta.setVisible(true);
			dtbxFecha_hasta.setVisible(true);
			lbNo_cigarros.setVisible(true);
			ibxNo_cigarros.setVisible(true);
		}
	}

	public void cambiosPorSexo(String sexo) {
		if (sexo.toUpperCase().equals("M")) {
			cellTacto_rectal.setVisible(true);
			rowCitologia.setVisible(false);
			rowCitologia2.setVisible(false);
			rowObstetricos.setVisible(false);
			rowGinecologica.setVisible(false);
		} else {
			cellTacto_rectal.setVisible(false);
			rowCitologia.setVisible(true);
			rowCitologia2.setVisible(true);
			rowObstetricos.setVisible(true);
			rowGinecologica.setVisible(true);
		}
	}

	private void cargarImpresionDiagnostica(Adulto_mayor adulto_mayor)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(adulto_mayor
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);
	}

	private void cargarAgudezaVisual(Adulto_mayor adulto_mayor)
			throws Exception {
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual.setCodigo_historia(adulto_mayor.getCodigo_historia());
		agudeza_visual.setCodigo_empresa(adulto_mayor.getCodigo_empresa());
		agudeza_visual.setCodigo_sucursal(adulto_mayor.getCodigo_sucursal());

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultar(agudeza_visual);

		macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
	}

	

	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t")
				.append("'" + lbxMotivo_consulta.getValue() + "'").append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		informacion_clinica.append("\t")
				.append("'" + lbxEnfremedad_actual.getValue() + "'")
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- SIGNOS VITALES\n");
		informacion_clinica.append("\tFrecuencia cardiaca: ")
				.append(dbxCardiaca.getText()).append("\n");
		informacion_clinica.append("\tPeso(kg): ").append(dbxPeso.getText())
				.append("\t");
		informacion_clinica.append("\tTalla: ").append(dbxTalla.getText())
				.append("\t");
		informacion_clinica.append("\tIMC: ").append(dbxImc.getText())
				.append("\t");
		informacion_clinica.append("\tCircunferencia Abdominal (cm): ")
				.append(dbxCir_adbominal.getText()).append("\t");
		informacion_clinica.append("\tCircunferencia de Cadera (cm): ")
				.append(dbxCir_cadera.getText()).append("\t");
		informacion_clinica.append("\tInd. Cintura/Cadera: ")
				.append(dbxInd_cintura.getText()).append("\n").append("\n");

		informacion_clinica.append("- EXAMEN FISICO").append("\n");
		informacion_clinica.append("\tEstado general: ").append("-----")
				.append("\n");
		informacion_clinica.append("\tAnalisis y recomendaciones: ");
		informacion_clinica.append("-----").append("\n");

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

	public void generarContendioAntecedentesFamiliares(
			Bandbox bandbox_antecedente, Integer sexo) {
		String contenido = obtenerXmlTextbox(bandbox_antecedente);
		Bandpopup bandpopup = null;
		Grid grid = null;
		Rows filas = null;

		if (bandbox_antecedente.getFirstChild() != null
				&& (bandbox_antecedente.getFirstChild() instanceof Bandpopup)) {
			bandpopup = (Bandpopup) bandbox_antecedente.getFirstChild();
		} else {
			bandpopup = new Bandpopup();
		}

		bandpopup.getChildren().clear();
		bandpopup.setWidth("170px");
		grid = new Grid();
		filas = new Rows();
		grid.appendChild(filas);
		bandpopup.appendChild(grid);

		Map<String, Object> mapa_aux = null;
		if (!contenido.isEmpty()) {
			try {
				mapa_aux = ConvertidorXmlToMap.convertirToMap(contenido);
			} catch (Exception e) {
				mapa_aux = new HashMap<String, Object>();
			}
		}

		final Map<String, Object> mapa_contenido = mapa_aux != null ? mapa_aux
				: new HashMap<String, Object>();

		final Checkbox checkbox_padre = new Checkbox("Padre");
		checkbox_padre.setChecked(mapa_contenido.containsKey("padre"));

		final Checkbox checkbox_hermano = new Checkbox("Hermano");
		checkbox_hermano.setChecked(mapa_contenido.containsKey("hermano"));

		final Spinner spinner_hermano = new Spinner();
		spinner_hermano.setConstraint("no empty,min 1");
		spinner_hermano.setVisible(mapa_contenido.containsKey("hermano"));
		spinner_hermano
				.setValue((mapa_contenido.get("hermano") != null ? (Integer) mapa_contenido
						.get("hermano") : 1));
		spinner_hermano.setWidth("70px");

		final Checkbox checkbox_abuelo_paterno = new Checkbox(
				"Abuelo (Paterno)");
		checkbox_abuelo_paterno.setChecked(mapa_contenido
				.containsKey("abuelo_paterno"));

		final Checkbox checkbox_abuelo_materno = new Checkbox(
				"Abuelo (Materno)");
		checkbox_abuelo_materno.setChecked(mapa_contenido
				.containsKey("abuelo_materno"));

		final Checkbox checkbox_madre = new Checkbox("Madre");
		checkbox_madre.setChecked(mapa_contenido.containsKey("madre"));

		final Checkbox checkbox_hermana = new Checkbox("Hermana");
		checkbox_hermana.setChecked(mapa_contenido.containsKey("hermana"));

		final Spinner spinner_hermana = new Spinner();
		spinner_hermana.setConstraint("no empty,min 1");
		spinner_hermana.setVisible(mapa_contenido.containsKey("hermana"));
		spinner_hermana
				.setValue((mapa_contenido.get("hermana") != null ? (Integer) mapa_contenido
						.get("hermana") : 1));
		spinner_hermana.setWidth("70px");

		final Checkbox checkbox_abuela_paterna = new Checkbox(
				"Abuela (Paterna)");
		checkbox_abuela_paterna.setChecked(mapa_contenido
				.containsKey("abuela_paterna"));

		final Checkbox checkbox_abuela_materna = new Checkbox(
				"Abuela (Materna)");
		checkbox_abuela_materna.setChecked(mapa_contenido
				.containsKey("abuela_materna"));

		Row fila = new Row();
		Hlayout hlayout = new Hlayout();
		if (sexo == 0) {
			hlayout.appendChild(checkbox_padre);
		} else {
			hlayout.appendChild(checkbox_madre);
		}
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		Space space = new Space();
		space.setWidth("7px");
		if (sexo == 0) {
			hlayout.appendChild(checkbox_hermano);
			hlayout.appendChild(space);
			hlayout.appendChild(spinner_hermano);
		} else {
			hlayout.appendChild(checkbox_hermana);
			hlayout.appendChild(space);
			hlayout.appendChild(spinner_hermana);
		}
		hlayout.appendChild(new Space());
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();

		if (sexo == 0) {
			hlayout.appendChild(checkbox_abuelo_paterno);
		} else {
			hlayout.appendChild(checkbox_abuela_paterna);
		}
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		if (sexo == 0) {
			hlayout.appendChild(checkbox_abuelo_materno);
		} else {
			hlayout.appendChild(checkbox_abuela_materna);
		}
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		final Bandbox bandbox = bandbox_antecedente;

		EventListener<Event> eventListener = new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getTarget() != null) {
					if (checkbox_padre.equals(event.getTarget())) {
						if (checkbox_padre.isChecked()) {
							mapa_contenido.put("padre", 1);
						} else {
							mapa_contenido.remove("padre");
						}
					} else if (checkbox_madre.equals(event.getTarget())) {
						if (checkbox_madre.isChecked()) {
							mapa_contenido.put("madre", 1);
						} else {
							mapa_contenido.remove("madre");
						}
					} else if (checkbox_hermano.equals(event.getTarget())) {
						spinner_hermano
								.setVisible(checkbox_hermano.isChecked());
						if (checkbox_hermano.isChecked()) {
							mapa_contenido.put("hermano", 1);
						} else {
							mapa_contenido.remove("hermano");
						}
					} else if (checkbox_hermana.equals(event.getTarget())) {
						spinner_hermana
								.setVisible(checkbox_hermana.isChecked());
						if (checkbox_hermana.isChecked()) {
							mapa_contenido.put("hermana", 1);
						} else {
							mapa_contenido.remove("hermana");
						}
					} else if (checkbox_abuelo_paterno
							.equals(event.getTarget())) {
						if (checkbox_abuelo_paterno.isChecked()) {
							mapa_contenido.put("abuelo_paterno", 1);
						} else {
							mapa_contenido.remove("abuelo_paterno");
						}
					} else if (checkbox_abuelo_materno
							.equals(event.getTarget())) {
						if (checkbox_abuelo_materno.isChecked()) {
							mapa_contenido.put("abuelo_materno", 1);
						} else {
							mapa_contenido.remove("abuelo_materno");
						}
					} else if (checkbox_abuela_paterna
							.equals(event.getTarget())) {
						if (checkbox_abuela_paterna.isChecked()) {
							mapa_contenido.put("abuela_paterna", 1);
						} else {
							mapa_contenido.remove("abuela_paterna");
						}
					} else if (checkbox_abuela_materna
							.equals(event.getTarget())) {
						if (checkbox_abuela_materna.isChecked()) {
							mapa_contenido.put("abuela_materna", 1);
						} else {
							mapa_contenido.remove("abuela_materna");
						}
					} else if (spinner_hermano.equals(event.getTarget())) {
						mapa_contenido.put("hermano",
								spinner_hermano.getValue());
					} else if (spinner_hermana.equals(event.getTarget())) {
						mapa_contenido.put("hermana",
								spinner_hermana.getValue());
					}
					mostrarXmlTextbox(bandbox, mapa_contenido);
				}
			}
		};

		checkbox_abuela_materna
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_abuela_paterna
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_abuelo_materno
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_abuelo_paterno
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_hermano.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_hermana.addEventListener(Events.ON_CHECK, eventListener);
		spinner_hermano.addEventListener(Events.ON_CHANGE, eventListener);
		spinner_hermana.addEventListener(Events.ON_CHANGE, eventListener);
		checkbox_madre.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_padre.addEventListener(Events.ON_CHECK, eventListener);
	}

	public void mostrarXmlTextbox(Textbox textbox,
			Map<String, Object> mapa_contenido) {
		try {
			textbox.setAttribute("XML_CONTENIDO",
					ConvertidorXmlToMap.convertirToXML(mapa_contenido));
			StringBuffer stringBuffer = new StringBuffer();
			for (Object key : mapa_contenido.keySet()) {
				Object valor = mapa_contenido.get(key);
				if (key.equals(valor)) {
					stringBuffer.append(key).append(";");
				} else {
					if (!valor.toString().isEmpty()) {
						stringBuffer.append(key).append("=").append(valor)
								.append(";");
					}
				}
			}
			textbox.setText(stringBuffer.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public String obtenerXmlTextbox(Textbox textbox) {
		if (textbox.hasAttribute("XML_CONTENIDO")) {
			return (String) textbox.getAttribute("XML_CONTENIDO");
		} else {
			return "";
		}
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

			dbxSentado.setValue(enfermera_signos.getSentado_bd1());
			dbxSentado2.setValue(enfermera_signos.getSentado_bd2());
			dbxAcostado.setValue(enfermera_signos.getDe_cubito1());
			dbxAcostado2.setValue(enfermera_signos.getDe_cubito2());
			dbxPie.setValue(enfermera_signos.getDe_pie1());
			dbxPie2.setValue(enfermera_signos.getDe_pie2());

			dbxCardiaca.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxPeso.setValue(enfermera_signos.getPeso());
			dbxTalla.setValue(enfermera_signos.getTalla());
			dbxImc.setValue(enfermera_signos.getImc());

			dbxCir_adbominal.setValue(enfermera_signos
					.getCircunferencia_abdominal());
			dbxCir_cadera.setValue(enfermera_signos.getCincunferencia_cadera());
			dbxInd_cintura.setValue(enfermera_signos.getInd_cintura_cadera());

			calcularTA(dbxSentado, dbxSentado2);
			calcularTA(dbxAcostado, dbxAcostado2);
			calcularTA(dbxPie, dbxPie2);
			alarmaExamenFisicoFc();
			calcularIMC(dbxPeso, dbxTalla, dbxImc);
			calcularIcadera(dbxCir_adbominal, dbxCir_cadera, dbxInd_cintura);

		}
	}
	
}
