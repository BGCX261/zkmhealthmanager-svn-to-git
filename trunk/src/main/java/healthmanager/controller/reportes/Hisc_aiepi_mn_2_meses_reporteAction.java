/*
 * hisc_aiepi_mn_2_mesesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Cuadros_aiepi_resultados;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Hisc_aiepi_mn_2_meses;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Cuadros_aiepi_resultadosService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Hisc_aiepi_mn_2_mesesService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.CuadroAIEPIMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.CuadroAiepiIMG;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Hisc_aiepi_mn_2_meses_reporteAction extends HistoriaClinicaModel implements
		IHistoria_generica, CuadroAiepiIMG {

	private static Logger log = Logger
			.getLogger(Hisc_aiepi_mn_2_meses_reporteAction.class);

//	private Hisc_aiepi_mn_2_mesesService hisc_aiepi_mn_2_mesesService;

	// Componentes //
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
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
	private Label lbxMotivo_consulta;
	@View
	private Label lbxEnfermedad_actual;
	@View
	private Radiogroup rdbNino_registrado;
	@View
	private Label lbxAnt_pat_como_fue_embarazo;
	@View
	private Intbox ibxAnt_pat_cuanto_duro_embarazo;
	@View
	private Textbox tbxAnt_pat_hemoclasificacion;
	@View
	private Label lbxAnt_pat_como_fue_parto;
	@View
	private Doublebox dbxAnt_pat_cuanto_peso_nacer;
	@View
	private Doublebox dbxAnt_pat_cuanto_midio_nacer;
	@View
	private Label lbxAnt_pat_presento_problema_nacer;
	@View
	private Label lbxAnt_pat_enfermedades;
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
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI1;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI2;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI3;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI4;
	@View
	private Label lbxObservaciones_cuadro1;
	@View
	private Label lbxObservaciones_cuadro2;
	@View
	private Label lbxObservaciones_cuadro3;
	@View
	private Label lbxObservaciones_cuadro4;

	private Paciente paciente;
	private ESexo sexo;
	private Timestamp fecha;
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
	private Label lbxParrafo_enfermedad_actual;
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
	private Image imgLogo;
	@View
	private Label lblTitulo;
	@View
	private Label lblMedicoTratante;
	@View
	private Label lblRegMedico;
	@View
	private Image imgFirma;
	@View
	private Groupbox gbxContenido;
	
	private final String[] idsExcluyentes = {};

	@Override
	public void initMostrar_datos(Object historia_clinica) {
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
					}
				});
	}

	@Override
	public void inicializarVista(String tipo) {
		
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

	}

	@Override
	public void params(Map<String, Object> map) {

	}

	@Override
	public void init() {
		try {
			
			listarCombos();
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			String codigo_historia = request.getParameter("codigo_historia");
			String nro_ingreso = request.getParameter("nro_ingreso");
			String nro_identificacion = request.getParameter("nro_identificacion");
			String codigo_empresa = request.getParameter("codigo_empresa");
			String codigo_sucursal = request.getParameter("codigo_sucursal");
			
            Long id_codigo_historia = null;
			
			if (codigo_historia != null && !codigo_historia.trim().isEmpty()
					&& codigo_historia.matches("[0-9]*$")) {
				id_codigo_historia = Long.parseLong(codigo_historia);
			}
			
			if(nro_identificacion!=null){
				admision = new Admision();
				admision.setCodigo_empresa(codigo_empresa);
				admision.setCodigo_sucursal(codigo_sucursal);
				admision.setNro_identificacion(nro_identificacion);
				admision.setNro_ingreso(nro_ingreso);
				admision = getServiceLocator().getServicio(AdmisionService.class).consultar(admision);
				if(admision!=null){
					macroImpresion_diagnostica.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
					macroCuadroAIEPI1.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES, "1");
					macroCuadroAIEPI2.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES, "2");
					macroCuadroAIEPI3.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES, "3");
					macroCuadroAIEPI4.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES, "4");

//					deshabilitarCamposCuadros();
//					activar_check_20();
//					autoOpcionesCuadros_1();

					macroImpresion_diagnostica.inicializarMacro(this, admision, 
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);
					paciente = admision.getPaciente();
					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
					fecha = paciente.getFecha_nacimiento();
				}
			}
			
			if(id_codigo_historia!=null){
				Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = new Hisc_aiepi_mn_2_meses();
				hisc_aiepi_mn_2_meses.setCodigo_empresa(codigo_empresa);
				hisc_aiepi_mn_2_meses.setCodigo_sucursal(codigo_sucursal);
				hisc_aiepi_mn_2_meses.setCodigo_historia(id_codigo_historia);
				hisc_aiepi_mn_2_meses = getServiceLocator().getServicio(Hisc_aiepi_mn_2_mesesService.class).consultar(hisc_aiepi_mn_2_meses);
				if(hisc_aiepi_mn_2_meses!=null){
					lblTitulo.setValue("HISTORIA CLINICA DE CONSULTA EXTERNA");
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(hisc_aiepi_mn_2_meses.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,idsExcluyentes);
					mostrarDatos(hisc_aiepi_mn_2_meses);
				
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

//	private void deshabilitarCamposCuadros() {
//		String[] ch11 = { "06", "1" };
//		String[] ch12 = { "07", "1" };
//		String[] ch13 = { "09", "1" };
//		macroCuadroAIEPI1.deshabilitarChecks(ch11, ch12, ch13);
//
//		String[] ch31 = { "01", "1" };
//		String[] ch32 = { "02", "1" };
//		String[] ch33 = { "03", "1" };
//		String[] ch34 = { "08", "3" };
//		String[] ch35 = { "09", "3" };
//		String[] ch36 = { "10", "3" };
//		String[] ch37 = { "11", "3" };
//		String[] ch38 = { "19", "5" };
//		String[] ch39 = { "20", "5" };
//
//		macroCuadroAIEPI3.getCheckbox("20", "5").setChecked(true);
//		macroCuadroAIEPI3.evaluarCambio("20", "5");
//		macroCuadroAIEPI3.deshabilitarChecks(ch31, ch32, ch33, ch34, ch35,
//				ch36, ch37, ch38, ch39);
//
//		String[] ch43 = { "03", "1" };
//		macroCuadroAIEPI4.deshabilitarChecks(ch43);
//	}

	@Override
	public void initHistoria() throws Exception {}

	public void listarCombos() {
		Utilidades.listarElementoListbox(lbxParentesco_acompanante, true,
				getServiceLocator());
		parametrizarBandboxOcupacion(tbxOcupacion_padre,
				tbxInfoOcupacion_padre, btnLimpiarOcupacion_padre);
		parametrizarBandboxOcupacion(tbxOcupacion_madre,
				tbxInfoOcupacion_madre, btnLimpiarOcupacion_madre);
		Utilidades.listarElementoListbox(lbxIntensidad, true,
				getServiceLocator());
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses = (Hisc_aiepi_mn_2_meses) obj;
		try {

			infoPacientes.setCodigo_historia(hisc_aiepi_mn_2_meses
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_aiepi_mn_2_meses
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_aiepi_mn_2_meses.getUltimo_update());
			initMostrar_datos(hisc_aiepi_mn_2_meses);
			cargarInformacion_paciente();

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
			lbxMotivo_consulta.setValue(hisc_aiepi_mn_2_meses
					.getMotivo_consulta());
			lbxEnfermedad_actual.setValue(hisc_aiepi_mn_2_meses
					.getEnfermedad_actual());

			Utilidades.seleccionarRadio(rdbNino_registrado,
					hisc_aiepi_mn_2_meses.getNino_registrado());
			lbxAnt_pat_como_fue_embarazo.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_como_fue_embarazo());
			ibxAnt_pat_cuanto_duro_embarazo.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_cuanto_duro_embarazo());
			tbxAnt_pat_hemoclasificacion.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_hemoclasificacion());
			lbxAnt_pat_como_fue_parto.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_como_fue_parto());
			dbxAnt_pat_cuanto_peso_nacer.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_cuanto_peso_nacer());
			dbxAnt_pat_cuanto_midio_nacer.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_cuanto_midio_nacer());
			lbxAnt_pat_presento_problema_nacer.setValue(hisc_aiepi_mn_2_meses
					.getAnt_pat_presento_problema_nacer());
			lbxAnt_pat_enfermedades.setValue(hisc_aiepi_mn_2_meses
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

			parametros.put("cuadro", "4");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI4.cargarDatos(lista);

			lbxObservaciones_cuadro1.setValue(hisc_aiepi_mn_2_meses.getObservaciones_cuadro1());
			lbxObservaciones_cuadro2.setValue(hisc_aiepi_mn_2_meses.getObservaciones_cuadro2());
			lbxObservaciones_cuadro3.setValue(hisc_aiepi_mn_2_meses.getObservaciones_cuadro3());
			lbxObservaciones_cuadro4.setValue(hisc_aiepi_mn_2_meses.getObservaciones_cuadro4());
 			
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
			lbxParrafo_enfermedad_actual.setValue(hisc_aiepi_mn_2_meses.getParrafo_enfermedad_actual());
			tbxRelacionado.setValue(hisc_aiepi_mn_2_meses.getRelacionado());
			tbxSintomas_asociados.setValue(hisc_aiepi_mn_2_meses.getSintomas_asociados());
			tbxTratamiento_recibido.setValue(hisc_aiepi_mn_2_meses.getTratamiento_recibido());

			cargarImpresionDiagnostica(hisc_aiepi_mn_2_meses);
			
			infoPacientes
					.setCodigo_historia(hisc_aiepi_mn_2_meses
							.getCodigo_historia());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			MensajesUtil
					.mensajeError(e, null, Hisc_aiepi_mn_2_meses_reporteAction.this);
		}
	}

	// METODOS PARA VISTA
	public void autoOpcionesCuadros() {
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
			CuadroAIEPIMacro.mostrarVentana(new Image(
					"/images/aiepi/lavado_manos_texto.png"), this.getPage(),
					"Lávese las manos", 0, 0, 0);
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
	
	@Override
	public String getInformacionClinica() {
		return "";
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
				lbxEnfermedad_actual.setVisible(true);
				lbEnfermedad_actual.setVisible(true);
				lbxEnfermedad_actual.invalidate();
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
				lbxParrafo_enfermedad_actual.setVisible(false);

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
				lbxEnfermedad_actual.setVisible(false);
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
				lbxParrafo_enfermedad_actual.setVisible(true);

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
				lbxEnfermedad_actual.setVisible(false);
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
				lbxParrafo_enfermedad_actual.setVisible(true);

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void LimpiarPlantilla(){
		lbxEnfermedad_actual.setValue("");
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
		lbxParrafo_enfermedad_actual.setValue("");
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

		lbxParrafo_enfermedad_actual.setValue(strBuffer.toString());

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
			autoOpcionesCuadros();
				
		}
	}
	
	private void cargarImpresionDiagnostica(
			Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses)
			throws Exception {

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();

		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica
				.setCodigo_historia(hisc_aiepi_mn_2_meses
						.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);
	}	
}