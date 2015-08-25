/*
 * Hisc_aiepi_mn_2_meses_5_aniosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Cuadros_aiepi_resultados;
import healthmanager.modelo.bean.Hisc_aiepi_mn_2_meses_5_anios;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Cuadros_aiepi_resultadosService;
import healthmanager.modelo.service.Hisc_aiepi_mn_2_meses_5_aniosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
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
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.CuadroAIEPIMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Hisc_aiepi_mn_2_meses_5_aniosAction extends
		HistoriaClinicaModel implements IHistoria_generica {
	private static Logger log = Logger
			.getLogger(Hisc_aiepi_mn_2_meses_5_aniosAction.class);

	// Componentes //
	@View
	private Textbox tbxId_acompanante;
	@View
	private Textbox tbxNombres_acompanante;
	@View
	private Textbox tbxApellidos_acompanante;
	@View
	private Listbox lbxParentesco_acompanante;
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
	private Intbox ibxEdad_madre;
	@View
	private Textbox tbxOtro_acompanante;
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
	private Label lbxMotivo_consulta;
	@View
	private Label lbxEnfermedad_actual;
	@View
	private Label lbxComo_fue_el_embarazo;
	@View
	private Intbox ibxCuanto_duro_embarazo;
	@View
	private Label lbxComo_fue_el_parto;
	@View
	private Doublebox dbxPeso_al_nacer;
	@View
	private Doublebox dbxTalla_al_nacer;
	@View
	private Label lbxPresento_algun_problema_neonatal;
	@View
	private Label lbxEnfermedades_previas;
	@View
	private Label lbxHospitalizaciones;
	@View
	private Doublebox dbxSignos_vitales_fc;
	@View
	private Doublebox dbxSignos_vitales_fr;
	@View
	private Doublebox dbxSignos_vitales_talla;
	@View
	private Doublebox dbxSignos_vitales_peso;
	@View
	private Doublebox dbxSignos_vitales_pc;
	@View
	private Doublebox dbxSignos_vitales_imc;
	@View
	private Doublebox dbxSignos_vitales_taxilar;
	@View
	private Doublebox dbxSignos_vitales_oximetria;
	@View
	private Radiogroup rdbNino_registrado;
	@View
	private Radiogroup rdbTiene_tos_o_dificultad_para_respirar;
	@View
	private Radiogroup rdbTiene_diarrea;
	@View
	private Radiogroup rdbTiene_fiebre;
	@View
	private Radiogroup rdbTiene_problemas_de_oido;
	@View
	private Radiogroup rdbTiene_un_problema_de_garganta;
	@View
	private Radiogroup rdbSignos_vitales_sintomaticos_respiratorio;
	@View
	private Radiogroup rdbSignos_vitales_sintomaticos_piel;
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
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
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI5;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI6;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI7;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI8;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI9;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI10;
	@View(isMacro = true)
	private CuadroAIEPIMacro macroCuadroAIEPI11;
	@View
	private Doublebox dbxP_e_de;
	@View
	private Doublebox dbxT_e_de;
	@View
	private Doublebox dbxP_t_de;
	@View
	private Doublebox dbxPC_e_de;
	@View
	private Doublebox dbxImc_e_de;

	@View
	private Label lbxObservaciones_cuadro1;
	@View
	private Label lbxObservaciones_cuadro2;
	@View
	private Label lbxObservaciones_cuadro3;
	@View
	private Label lbxObservaciones_cuadro4;
	@View
	private Label lbxObservaciones_cuadro5;
	@View
	private Label lbxObservaciones_cuadro6;
	@View
	private Label lbxObservaciones_cuadro7;
	@View
	private Label lbxObservaciones_cuadro8;
	@View
	private Label lbxObservaciones_cuadro9;
	@View
	private Label lbxObservaciones_cuadro10;
	@View
	private Label lbxObservaciones_cuadro11;

	private Admision admision;
//	private String tipo_historia;
	private Paciente paciente;
	private ESexo sexo;
	private Timestamp fecha;

	// groubox de antecedentes vacunales
	@View
	private Row row_ultima_dosis;
	@View
	private Checkbox cbx_influenza;
	@View
	private Datebox dbx_ultima_dosis;
	@View
	private Row row_fiebre_amarilla;
	@View
	private Intbox ibx_edad;
	@View
	private Checkbox cbx_fiebre_amarilla;
	@View
	private Checkbox cbx_bcg__1;
	@View
	private Checkbox cbx_hepatitis_b_rn;
	@View
	private Checkbox cbx_hepatitis_b_1;
	@View
	private Checkbox cbx_hepatitis_b_2;
	@View
	private Checkbox cbx_hepatitis_b_3;
	@View
	private Checkbox cbx_vop_1;
	@View
	private Checkbox cbx_vop_2;
	@View
	private Checkbox cbx_vop_3;
	@View
	private Checkbox cbx_vop_r1;
	@View
	private Checkbox cbx_vop_r2;
	@View
	private Checkbox cbx_rotavirus_1;
	@View
	private Checkbox cbx_rotavirus_2;
	@View
	private Textbox tbx_otras_vacunas;
	@View
	private Checkbox cbx_dtp_1;
	@View
	private Checkbox cbx_dtp_2;
	@View
	private Checkbox cbx_dtp_3;
	@View
	private Checkbox cbx_dtp_r1;
	@View
	private Checkbox cbx_dtp_r2;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_b1;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_b2;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_b3;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_r1;
	@View
	private Checkbox cbx_haemophilus_influenza_tipo_r2;
	@View
	private Checkbox cbx_strectococo_neumonia_1;
	@View
	private Checkbox cbx_strectococo_neumonia_2;
	@View
	private Checkbox cbx_strectococo_neumonia_3;
	@View
	private Checkbox cbx_srp_2;
	@View
	private Checkbox cbx_srp_1;
	@View
	private Datebox dtbxVolver_consulta_control;
	@View
	private Row row_cuadro_aiepi_2;
	@View
	private Row row_cuadro_aiepi_3;
	@View
	private Row row_cuadro_aiepi_4;
	@View
	private Row row_cuadro_aiepi_5;
	@View
	private Row row_cuadro_aiepi_6;
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
	
	private final String[] idsExcluyentes = { };
	Integer meses;

	private Citas cita;

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
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "1");
					macroCuadroAIEPI2.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "2");
					macroCuadroAIEPI3.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "3");
					macroCuadroAIEPI4.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "4");
					macroCuadroAIEPI5.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "5");
					macroCuadroAIEPI6.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "6");
					macroCuadroAIEPI7.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "7");
					macroCuadroAIEPI8.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "8");
					macroCuadroAIEPI9.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "9");
					macroCuadroAIEPI10.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "10");
					macroCuadroAIEPI11.inicializarMacro(this, admision,
							IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS, "11");

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
			
			if (id_codigo_historia != null) {
				Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios = new Hisc_aiepi_mn_2_meses_5_anios();
				hisc_aiepi_mn_2_meses_5_anios.setCodigo_empresa(codigo_empresa);
				hisc_aiepi_mn_2_meses_5_anios.setCodigo_sucursal(codigo_sucursal);
				hisc_aiepi_mn_2_meses_5_anios.setCodigo_historia(id_codigo_historia);
				hisc_aiepi_mn_2_meses_5_anios = getServiceLocator().getServicio(Hisc_aiepi_mn_2_meses_5_aniosService.class).consultar(hisc_aiepi_mn_2_meses_5_anios);
				if(hisc_aiepi_mn_2_meses_5_anios!=null){
					if(hisc_aiepi_mn_2_meses_5_anios.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DE CONSULTA EXTERNA\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DE CONSULTA EXTERNA\nHISTORIA DE CONTROL");
					}

					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(hisc_aiepi_mn_2_meses_5_anios.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,idsExcluyentes);
					mostrarDatos(hisc_aiepi_mn_2_meses_5_anios);
				
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	
	}

	// METODOS PARA DESABILITAR CHECK

//	private void deshabilitarCamposCuadros() {
//		// TODO: Deshabilitar los checks automaticos
//		// CUADRO 2
//
//		// estado 1:
//		String[] ch04 = { "04", "1" };
//		macroCuadroAIEPI2.getCheckbox("04", "1").setChecked(true);
//		macroCuadroAIEPI2.evaluarCambio("04", "1");
//		macroCuadroAIEPI2.deshabilitarChecks(ch04);
//
//		// estado 2:
//		String[] ch05 = { "09", "2" };
//		macroCuadroAIEPI2.getCheckbox("09", "2").setChecked(true);
//		macroCuadroAIEPI2.getCheckbox("09", "2");
//		macroCuadroAIEPI2.evaluarCambio("09", "2");
//		macroCuadroAIEPI2.deshabilitarChecks(ch05);
//
//		// estado 3:
//		String[] ch14 = { "14", "3" };
//		// macroCuadroAIEPI2.getCheckbox("14", "3").setChecked(true);
//		macroCuadroAIEPI2.getCheckbox("14", "3");
//		macroCuadroAIEPI2.evaluarCambio("14", "3");
//		macroCuadroAIEPI2.deshabilitarChecks(ch14);
//
//		// estado 4:
//		String[] ch19 = { "19", "4" };
//		macroCuadroAIEPI2.getCheckbox("19", "4").setChecked(true);
//		macroCuadroAIEPI2.getCheckbox("19", "4");
//		macroCuadroAIEPI2.evaluarCambio("19", "4");
//		macroCuadroAIEPI2.deshabilitarChecks(ch19);
//
//		// estado 5:
//		String[] ch24 = { "24", "5" };
//		macroCuadroAIEPI2.getCheckbox("24", "5").setChecked(true);
//		macroCuadroAIEPI2.getCheckbox("24", "5");
//		macroCuadroAIEPI2.evaluarCambio("24", "5");
//		macroCuadroAIEPI2.deshabilitarChecks(ch24);
//
//		// estado 6:
//		String[] ch29 = { "29", "6" };
//		macroCuadroAIEPI2.getCheckbox("29", "6");
//		macroCuadroAIEPI2.evaluarCambio("29", "6");
//		macroCuadroAIEPI2.deshabilitarChecks(ch29);
//
//		// CUADRO 3 :
//
//		// estado 5
//		String[] ch15 = { "15", "5" };
//		macroCuadroAIEPI3.getCheckbox("15", "5").setChecked(true);
//		macroCuadroAIEPI3.getCheckbox("15", "5");
//		macroCuadroAIEPI3.evaluarCambio("15", "5");
//		macroCuadroAIEPI3.deshabilitarChecks(ch15);
//
//		// estado 6
//		String[] ch17 = { "17", "6" };
//		macroCuadroAIEPI3.getCheckbox("17", "6").setChecked(true);
//		macroCuadroAIEPI3.getCheckbox("17", "6");
//		macroCuadroAIEPI3.evaluarCambio("17", "6");
//		macroCuadroAIEPI3.deshabilitarChecks(ch17);
//
//		// CUADRO 4 :
//
//		// estado 1:
//		String[] ch1 = { "01", "1" };
//		macroCuadroAIEPI4.getCheckbox("01", "1");
//		macroCuadroAIEPI4.evaluarCambio("01", "1");
//		macroCuadroAIEPI4.deshabilitarChecks(ch1);
//
//		// estado 6:
//		String[] ch43 = { "43", "7" };
//		macroCuadroAIEPI4.getCheckbox("43", "7");
//		macroCuadroAIEPI4.evaluarCambio("43", "7");
//		macroCuadroAIEPI4.deshabilitarChecks(ch43);
//
//		// CUADRO 8 :
//
//		// estado 1
//		String[] chk1 = { "01", "1" };
//		macroCuadroAIEPI8.getCheckbox("01", "1");
//		macroCuadroAIEPI8.evaluarCambio("01", "1");
//		macroCuadroAIEPI8.deshabilitarChecks(chk1);
//
//		String[] chk2 = { "02", "1" };
//		macroCuadroAIEPI8.getCheckbox("02", "1");
//		macroCuadroAIEPI8.evaluarCambio("02", "1");
//		macroCuadroAIEPI8.deshabilitarChecks(chk2);
//
//		// estado 2
//
//		String[] chk3 = { "03", "2" };
//		macroCuadroAIEPI8.getCheckbox("03", "2");
//		macroCuadroAIEPI8.evaluarCambio("03", "2");
//		macroCuadroAIEPI8.deshabilitarChecks(chk3);
//
//		String[] chk4 = { "04", "2" };
//		macroCuadroAIEPI8.getCheckbox("04", "2");
//		macroCuadroAIEPI8.evaluarCambio("04", "2");
//		macroCuadroAIEPI8.deshabilitarChecks(chk4);
//
//		// estado 3
//		String[] chk7 = { "07", "3" };
//		macroCuadroAIEPI8.getCheckbox("07", "3");
//		macroCuadroAIEPI8.evaluarCambio("07", "3");
//		macroCuadroAIEPI8.deshabilitarChecks(chk7);
//
//		String[] chk8 = { "08", "3" };
//		macroCuadroAIEPI8.getCheckbox("08", "3");
//		macroCuadroAIEPI8.evaluarCambio("08", "3");
//		macroCuadroAIEPI8.deshabilitarChecks(chk8);
//
//		// estado 4
//		String[] chk9 = { "09", "4" };
//		macroCuadroAIEPI8.getCheckbox("09", "4");
//		macroCuadroAIEPI8.evaluarCambio("09", "4");
//		macroCuadroAIEPI8.deshabilitarChecks(chk9);
//
//		String[] chk10 = { "10", "4" };
//		macroCuadroAIEPI8.getCheckbox("10", "4");
//		macroCuadroAIEPI8.evaluarCambio("10", "4");
//		macroCuadroAIEPI8.deshabilitarChecks(chk10);
//
//		String[] chk11 = { "11", "4" };
//		macroCuadroAIEPI8.getCheckbox("11", "4");
//		macroCuadroAIEPI8.evaluarCambio("11", "4");
//		macroCuadroAIEPI8.deshabilitarChecks(chk11);
//
//		// estado 5
//		String[] chk12 = { "12", "5" };
//		macroCuadroAIEPI8.getCheckbox("12", "5");
//		macroCuadroAIEPI8.evaluarCambio("12", "5");
//		macroCuadroAIEPI8.deshabilitarChecks(chk12);
//
//		String[] chk13 = { "13", "5" };
//		macroCuadroAIEPI8.getCheckbox("13", "5");
//		macroCuadroAIEPI8.evaluarCambio("13", "5");
//		macroCuadroAIEPI8.deshabilitarChecks(chk13);
//
//		String[] chk14 = { "14", "5" };
//		macroCuadroAIEPI8.getCheckbox("14", "5");
//		macroCuadroAIEPI8.evaluarCambio("14", "5");
//		macroCuadroAIEPI8.deshabilitarChecks(chk14);
//
//		// estado 6
//
//		String[] chk16 = { "16", "6" };
//		macroCuadroAIEPI8.getCheckbox("16", "6");
//		macroCuadroAIEPI8.evaluarCambio("16", "6");
//		macroCuadroAIEPI8.deshabilitarChecks(chk16);
//
//		String[] ch16 = { "16", "6" };
//		macroCuadroAIEPI8.getCheckbox("16", "6");
//		macroCuadroAIEPI8.evaluarCambio("16", "6");
//		macroCuadroAIEPI8.deshabilitarChecks(ch16);
//
//		String[] chk17 = { "17", "6" };
//		macroCuadroAIEPI8.getCheckbox("17", "6");
//		macroCuadroAIEPI8.evaluarCambio("17", "6");
//		macroCuadroAIEPI8.deshabilitarChecks(chk17);
//
//		String[] chk18 = { "18", "6" };
//		macroCuadroAIEPI8.getCheckbox("18", "6");
//		macroCuadroAIEPI8.evaluarCambio("18", "6");
//		macroCuadroAIEPI8.deshabilitarChecks(chk18);
//
//		String[] chk19 = { "19", "6" };
//		macroCuadroAIEPI8.getCheckbox("19", "6");
//		macroCuadroAIEPI8.evaluarCambio("19", "6");
//		macroCuadroAIEPI8.deshabilitarChecks(chk19);
//
//		String[] chk20 = { "20", "6" };
//
//		macroCuadroAIEPI8.getCheckbox("20", "6");
//		macroCuadroAIEPI8.evaluarCambio("20", "6");
//		macroCuadroAIEPI8.deshabilitarChecks(chk20);
//
//	}

	public void autoOpcionesCuadros_1() {

		// CUADRO 2

		// estado 1:
		Checkbox chk1_4 = macroCuadroAIEPI2.getCheckbox("04", "1");
		CuadroAIEPIMacro.deshabilitarCheck(chk1_4, (meses < 3));
		macroCuadroAIEPI2.evaluarCambio("04", "1");

		// estado 2:
		Checkbox chk1_5 = macroCuadroAIEPI2.getCheckbox("09", "2");
		CuadroAIEPIMacro.deshabilitarCheck(chk1_5, (meses < 3));
		macroCuadroAIEPI2.evaluarCambio("09", "2");

		// estado 4 :
		Checkbox chk1_19 = macroCuadroAIEPI2.getCheckbox("19", "4");
		CuadroAIEPIMacro.deshabilitarCheck(chk1_19, (meses > 3));
		macroCuadroAIEPI2.evaluarCambio("19", "4");

		// esatdo 5 :
		Checkbox chk1_24 = macroCuadroAIEPI2.getCheckbox("24", "5");
		CuadroAIEPIMacro.deshabilitarCheck(chk1_24, (meses > 3));
		macroCuadroAIEPI2.evaluarCambio("24", "5");

		// CUADRO 3:

		// estado 5:
		Checkbox chk_15 = macroCuadroAIEPI3.getCheckbox("15", "5");
		CuadroAIEPIMacro.deshabilitarCheck(chk_15, (meses < 6));
		macroCuadroAIEPI3.evaluarCambio("15", "5");

		// estado 6:
		Checkbox chk_17 = macroCuadroAIEPI3.getCheckbox("17", "6");
		CuadroAIEPIMacro.deshabilitarCheck(chk_17, (meses > 6));
		macroCuadroAIEPI3.evaluarCambio("17", "6");

		// CUADRO 4:

		// estado 6:
		Checkbox chk_43 = macroCuadroAIEPI4.getCheckbox("43", "7");
		CuadroAIEPIMacro.deshabilitarCheck(chk_43, (meses < 60));
		macroCuadroAIEPI4.evaluarCambio("43", "7");
	}

	public void autoOpcionesCuadros() {
		// estado 1
		if (dbxImc_e_de.getValue() == null) {
		} else {

			Checkbox chk1 = macroCuadroAIEPI8.getCheckbox("01", "1");
			CuadroAIEPIMacro.deshabilitarCheck(chk1,
					(dbxImc_e_de.getValue() >= 2));
			macroCuadroAIEPI8.evaluarCambio("01", "1");
		}
		if (dbxP_t_de.getValue() == null) {
		} else {
			Checkbox chk2 = macroCuadroAIEPI8.getCheckbox("02", "1");
			CuadroAIEPIMacro
					.deshabilitarCheck(chk2, (dbxP_t_de.getValue() > 2));
			macroCuadroAIEPI8.evaluarCambio("02", "1");
		}
		if (dbxImc_e_de.getValue() == null) {
		} else {

			// estado 2 :
			Checkbox chk3 = macroCuadroAIEPI8.getCheckbox("03", "2");
			CuadroAIEPIMacro
					.deshabilitarCheck(chk3, (dbxImc_e_de.getValue() >= 1)
							&& dbxImc_e_de.getValue() < 2);
			macroCuadroAIEPI8.evaluarCambio("03", "2");
		}
		if (dbxP_t_de.getValue() == null) {
		} else {
			Checkbox chk4 = macroCuadroAIEPI8.getCheckbox("04", "2");
			CuadroAIEPIMacro.deshabilitarCheck(chk4, (dbxP_t_de.getValue() > 1)
					&& dbxP_t_de.getValue() <= 2);
			macroCuadroAIEPI8.evaluarCambio("04", "2");
		}
		// //estado 3

		if (dbxP_t_de.getValue() == null) {
		} else {
			Checkbox chk7 = macroCuadroAIEPI8.getCheckbox("07", "3");
			CuadroAIEPIMacro.deshabilitarCheck(chk7,
					(dbxP_t_de.getValue() < -3));
			macroCuadroAIEPI8.evaluarCambio("07", "3");
		}
		if (dbxP_e_de.getValue() == null) {
		} else {

			Checkbox chk8 = macroCuadroAIEPI8.getCheckbox("08", "3");
			CuadroAIEPIMacro.deshabilitarCheck(chk8,
					(dbxP_e_de.getValue() < -3));
			macroCuadroAIEPI8.evaluarCambio("08", "3");
		}
		if (dbxP_t_de.getValue() == null) {
		} else {
			Checkbox chk9 = macroCuadroAIEPI8.getCheckbox("09", "4");
			CuadroAIEPIMacro.deshabilitarCheck(chk9,
					(dbxP_t_de.getValue() >= -2) && dbxP_t_de.getValue() <= -3);
			macroCuadroAIEPI8.evaluarCambio("09", "4");
		}
		if (dbxP_e_de.getValue() == null) {

		} else {

			Checkbox chk10 = macroCuadroAIEPI8.getCheckbox("10", "4");
			CuadroAIEPIMacro.deshabilitarCheck(chk10,
					(dbxP_e_de.getValue() >= -2) && dbxP_e_de.getValue() <= -3);
			macroCuadroAIEPI8.evaluarCambio("10", "4");
		}

		if (dbxT_e_de.getValue() == null) {
		} else {
			Checkbox chk11 = macroCuadroAIEPI8.getCheckbox("11", "4");
			CuadroAIEPIMacro.deshabilitarCheck(chk11,
					(dbxT_e_de.getValue() < -2));
			macroCuadroAIEPI8.evaluarCambio("11", "4");
		}
		// //estado 5

		if (dbxP_t_de.getValue() == null) {
		} else {
			Checkbox chk12 = macroCuadroAIEPI8.getCheckbox("12", "5");
			CuadroAIEPIMacro.deshabilitarCheck(chk12,
					(dbxP_t_de.getValue() >= -2) && dbxP_t_de.getValue() < -1);
			macroCuadroAIEPI8.evaluarCambio("12", "5");
		}

		if (dbxP_e_de.getValue() == null) {
		} else {
			Checkbox chk13 = macroCuadroAIEPI8.getCheckbox("13", "5");
			CuadroAIEPIMacro.deshabilitarCheck(chk13,
					(dbxP_e_de.getValue() >= -2) && dbxP_e_de.getValue() < -1);
			macroCuadroAIEPI8.evaluarCambio("13", "5");
		}
		if (dbxT_e_de.getValue() == null) {
		} else {

			Checkbox chk14 = macroCuadroAIEPI8.getCheckbox("14", "5");
			CuadroAIEPIMacro.deshabilitarCheck(chk14,
					(dbxT_e_de.getValue() >= -2) && dbxT_e_de.getValue() < -1);
			macroCuadroAIEPI8.evaluarCambio("14", "5");
		}

		// //estado 6

		if (dbxP_t_de.getValue() == null) {
		} else {
			Checkbox chk16 = macroCuadroAIEPI8.getCheckbox("16", "6");
			CuadroAIEPIMacro.deshabilitarCheck(chk16,
					(dbxP_t_de.getValue() >= -1) && dbxP_t_de.getValue() <= 1);
			macroCuadroAIEPI8.evaluarCambio("16", "6");
		}

		if (dbxP_e_de.getValue() == null) {
		} else {
			Checkbox chk17 = macroCuadroAIEPI8.getCheckbox("17", "6");
			CuadroAIEPIMacro.deshabilitarCheck(chk17,
					(dbxP_e_de.getValue() >= -1) && dbxP_e_de.getValue() <= 1);
			macroCuadroAIEPI8.evaluarCambio("17", "6");

		}
		if (dbxT_e_de.getValue() == null) {

		} else {
			Checkbox chk18 = macroCuadroAIEPI8.getCheckbox("18", "6");
			CuadroAIEPIMacro.deshabilitarCheck(chk18,
					(dbxT_e_de.getValue() >= -1));
			macroCuadroAIEPI8.evaluarCambio("18", "6");
		}

		if (dbxImc_e_de.getValue() == null) {

		} else {
			Checkbox chk19 = macroCuadroAIEPI8.getCheckbox("19", "6");
			CuadroAIEPIMacro.deshabilitarCheck(chk19,
					(dbxImc_e_de.getValue() < 1));
			macroCuadroAIEPI8.evaluarCambio("19", "6");
		}

	}

	public void activar_check_20() {

		Checkbox chk_20 = macroCuadroAIEPI8.getCheckbox("20", "6");
		Boolean check = macroCuadroAIEPI8.cantCheckeadosEstado("1") == 0
				&& macroCuadroAIEPI8.cantCheckeadosEstado("2") == 0
				&& macroCuadroAIEPI8.cantCheckeadosEstado("3") == 0
				&& macroCuadroAIEPI8.cantCheckeadosEstado("4") == 0
				&& macroCuadroAIEPI8.cantCheckeadosEstado("5") == 0;
		CuadroAIEPIMacro.deshabilitarCheck(chk_20, check);
		macroCuadroAIEPI8.evaluarCambio("20", "5");

	}

	public void activar_check_oximetria() {
		try {
			double oxiometria = dbxSignos_vitales_oximetria.getValue() != null ?  dbxSignos_vitales_oximetria.getValue() : 0.0;
			Checkbox chk1_14 = macroCuadroAIEPI2.getCheckbox("14", "3");
			Checkbox chk1_29 = macroCuadroAIEPI2.getCheckbox("29", "6");
			CuadroAIEPIMacro.deshabilitarCheck(chk1_14,
					(oxiometria == 84));
			CuadroAIEPIMacro.deshabilitarCheck(chk1_29,
					(oxiometria > 84));
			macroCuadroAIEPI2.evaluarCambio("14", "3");
			macroCuadroAIEPI2.evaluarCambio("29", "6");
		} catch (WrongValueException e) {
			log.error(e.getMessage(),e);
		}
	}

	public void activar_check_tem() {
		Checkbox chk_1 = macroCuadroAIEPI4.getCheckbox("01", "1");
		CuadroAIEPIMacro.deshabilitarCheck(chk_1, (meses < 3)
				&& dbxSignos_vitales_taxilar.getValue() >= 38);
		macroCuadroAIEPI4.evaluarCambio("01", "1");

	}

	// metodos para habilitar filas no visibles

	public void mostrar_filas_ocultas() {
		mostrar_filas_ultimadosis();
		mostrar_fila_fiebre_amarilla();
		mostrar_fila_aiepi_cuadro_2();
		mostrar_fila_aiepi_cuadro_3();
		mostrar_fila_aiepi_cuadro_4();
		mostrar_fila_aiepi_cuadro_5();
		mostrar_fila_aiepi_cuadro_6();
	}

	public void mostrar_filas_ultimadosis() {
		if (cbx_influenza.isChecked() == true) {
			row_ultima_dosis.setVisible(true);
		} else {
			row_ultima_dosis.setVisible(false);

		}
	}

	public void mostrar_fila_fiebre_amarilla() {
		if (cbx_fiebre_amarilla.isChecked() == true) {
			row_fiebre_amarilla.setVisible(true);
		} else {
			row_fiebre_amarilla.setVisible(false);
			ibx_edad.setText("");
		}
	}

	public void mostrar_fila_aiepi_cuadro_2() {

		if (rdbTiene_tos_o_dificultad_para_respirar.getSelectedItem()
				.getValue().equals("S")) {
			row_cuadro_aiepi_2.setVisible(true);
		} else {
			row_cuadro_aiepi_2.setVisible(false);
		}
	}

	public void mostrar_fila_aiepi_cuadro_3() {

		if (rdbTiene_diarrea.getSelectedItem().getValue().equals("S")) {
			row_cuadro_aiepi_3.setVisible(true);
		} else {
			row_cuadro_aiepi_3.setVisible(false);
		}
	}

	public void mostrar_fila_aiepi_cuadro_4() {

		if (rdbTiene_fiebre.getSelectedItem().getValue().equals("S")) {
			row_cuadro_aiepi_4.setVisible(true);
		} else {
			row_cuadro_aiepi_4.setVisible(false);
		}
	}

	public void mostrar_fila_aiepi_cuadro_5() {

		if (rdbTiene_problemas_de_oido.getSelectedItem().getValue().equals("S")) {
			row_cuadro_aiepi_5.setVisible(true);
		} else {
			row_cuadro_aiepi_5.setVisible(false);
		}
	}

	public void mostrar_fila_aiepi_cuadro_6() {

		if (rdbTiene_un_problema_de_garganta.getSelectedItem().getValue()
				.equals("S")) {
			row_cuadro_aiepi_6.setVisible(true);
		} else {
			row_cuadro_aiepi_6.setVisible(false);
		}
	}

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

	@Override
	public void cargarInformacion_paciente() {
		if (cita != null) {
			tbxId_acompanante.setValue(cita.getCedula_acomp());
			tbxNombres_acompanante.setValue(cita.getAcompaniante());
			tbxApellidos_acompanante.setValue(cita.getApellidos_acomp());
			tbxTelefono_acompanante.setValue(cita.getTel_acompaniante());
			Utilidades.seleccionarListitem(lbxParentesco_acompanante,
					cita.getRelacion());
			onSeleccionarRelacionAcompaniante();
			tbxOtro_acompanante.setValue(cita.getOtro_acompaniante());
		}

		if (paciente.getTipo_identificacion().equalsIgnoreCase("AS")
				|| paciente.getTipo_identificacion().equalsIgnoreCase("MS")) {
			rdbNino_registrado.setSelectedIndex(1);
		} else {
			rdbNino_registrado.setSelectedIndex(0);
		}
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {
					@Override
					public void ejecutarProceso() {
						
					}
				});
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios = (Hisc_aiepi_mn_2_meses_5_anios) obj;
		try {

			infoPacientes
					.setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
							.getCodigo_historia());
			infoPacientes
					.setFecha_inicio(hisc_aiepi_mn_2_meses_5_anios
							.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_aiepi_mn_2_meses_5_anios
							.getUltimo_update());
			initMostrar_datos(hisc_aiepi_mn_2_meses_5_anios);
			cargarInformacion_paciente();

			tbxNombres_acompanante
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getNombre_acompananate());
			tbxApellidos_acompanante
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getApellido_acompanante());
			Utilidades.seleccionarListitem(lbxParentesco_acompanante,
					hisc_aiepi_mn_2_meses_5_anios
							.getParentesco_acompanante());
			Ocupaciones ocupaciones = new Ocupaciones();
			String oc = hisc_aiepi_mn_2_meses_5_anios
					.getOcupacion_padre();
			if (oc != null && !oc.isEmpty()) {
				ocupaciones
						.setId(hisc_aiepi_mn_2_meses_5_anios
								.getOcupacion_padre());
				ocupaciones = getServiceLocator().getOcupacionesService()
						.consultar(ocupaciones);
				tbxOcupacion_padre
						.setValue(hisc_aiepi_mn_2_meses_5_anios
								.getOcupacion_padre());
				tbxInfoOcupacion_padre
						.setValue((ocupaciones != null ? ocupaciones
								.getNombre() : ""));
			}
			oc = hisc_aiepi_mn_2_meses_5_anios
					.getOcupacion_madre();
			if (oc != null && !oc.isEmpty()) {
				ocupaciones
						.setId(hisc_aiepi_mn_2_meses_5_anios
								.getOcupacion_madre());
				ocupaciones = getServiceLocator().getOcupacionesService()
						.consultar(ocupaciones);
				tbxOcupacion_padre
						.setValue(hisc_aiepi_mn_2_meses_5_anios
								.getOcupacion_madre());
				tbxInfoOcupacion_madre
						.setValue((ocupaciones != null ? ocupaciones
								.getNombre() : ""));
			}
			tbxTelefono_acompanante
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getTelefono_acompanante());
			tbxNombres_padre
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getNombre_padre());
			tbxApellidos_padre
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getApellidos_padre());
			ibxEdad_padre
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getEdad_padre());
			tbxNombres_madre
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getNombre_madre());
			tbxApellidos_madre
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getApellidos_madre());
			ibxEdad_madre
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getEdad_madre());
			lbxMotivo_consulta
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getMotivo_consulta());
			lbxEnfermedad_actual
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getEnfermedad_actual());
			lbxComo_fue_el_embarazo
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getComo_fue_el_embarazo());
			ibxCuanto_duro_embarazo
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getCuanto_duro_embarazo());
			lbxComo_fue_el_parto
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getComo_fue_el_parto());

			dbxPeso_al_nacer
					.setValue(Double
							.parseDouble(hisc_aiepi_mn_2_meses_5_anios
									.getPeso_al_nacer()));

			dbxTalla_al_nacer
					.setValue(Double
							.parseDouble(hisc_aiepi_mn_2_meses_5_anios
									.getTalla_al_nacer()));
			lbxPresento_algun_problema_neonatal
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getPresento_algun_problema_neonatal());
			lbxEnfermedades_previas
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getEnfermedades_previas());
			lbxHospitalizaciones
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getHospitalizaciones());
			dbxSignos_vitales_fc.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_fc()));
			dbxSignos_vitales_fr.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_fr()));
			dbxSignos_vitales_talla.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_talla()));
			dbxSignos_vitales_peso.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_peso()));
			dbxSignos_vitales_pc.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_pc()));
			dbxSignos_vitales_imc.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_imc()));
			dbxSignos_vitales_taxilar.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_taxilar()));
			dbxSignos_vitales_oximetria.setValue(Double
					.valueOf(hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_oximetria()));
			Utilidades.seleccionarRadio(
					rdbTiene_tos_o_dificultad_para_respirar,
					hisc_aiepi_mn_2_meses_5_anios
							.getTiene_tos_o_dificultad_para_respirar());
			Utilidades.seleccionarRadio(rdbTiene_diarrea,
					hisc_aiepi_mn_2_meses_5_anios
							.getTiene_diarrea());
			Utilidades.seleccionarRadio(rdbTiene_fiebre,
					hisc_aiepi_mn_2_meses_5_anios
							.getTiene_fiebre());
			Utilidades.seleccionarRadio(rdbTiene_problemas_de_oido,
					hisc_aiepi_mn_2_meses_5_anios
							.getTiene_problemas_de_oido());
			Utilidades.seleccionarRadio(rdbTiene_un_problema_de_garganta,
					hisc_aiepi_mn_2_meses_5_anios
							.getTiene_un_problema_de_garganta());
			Utilidades.seleccionarRadio(rdbSignos_vitales_sintomaticos_piel,
					hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_sintomaticos_piel());
			Utilidades.seleccionarRadio(
					rdbSignos_vitales_sintomaticos_respiratorio,
					hisc_aiepi_mn_2_meses_5_anios
							.getSignos_vitales_sintomaticos_respiratorio());

			// TODO: mostrar datos antecedentes vacunaion

			cbx_bcg__1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getBcg_1().equals("S") ? true : false);
			cbx_hepatitis_b_rn
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getHepatitis_b_rn().equals("S") ? true : false);
			cbx_hepatitis_b_1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getHepatitis_b1().equals("S") ? true : false);
			cbx_hepatitis_b_2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getHepatitis_b2().equals("S") ? true : false);
			cbx_hepatitis_b_3
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getHepatitis_b3().equals("S") ? true : false);
			cbx_vop_1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getVop_1().equals("S") ? true : false);
			cbx_vop_2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getVop_2().equals("S") ? true : false);
			cbx_vop_3
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getVop_3().equals("S") ? true : false);
			cbx_vop_r1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getVop_r1().equals("S") ? true : false);
			cbx_vop_r2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getVop_r2().equals("S") ? true : false);
			cbx_rotavirus_1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getRotavirus1().equals("S") ? true : false);
			cbx_rotavirus_2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getRotavirus2().equals("S") ? true : false);
			cbx_influenza
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getInfluenza().equals("S") ? true : false);
			tbx_otras_vacunas
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getOtras_vaunas());
			cbx_dtp_1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getDpt1().equals("S") ? true : false);
			cbx_dtp_2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getDpt2().equals("S") ? true : false);
			cbx_dtp_3
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getDpt3().equals("S") ? true : false);
			cbx_dtp_r1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getDpt_r1().equals("S") ? true : false);
			cbx_dtp_r2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getDpt_r2().equals("S") ? true : false);
			cbx_haemophilus_influenza_tipo_b1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getHaemophilus_influenza_tipo_b1().equals("S") ? true
							: false);
			cbx_haemophilus_influenza_tipo_b2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getHaemophilus_influenza_tipo_b2().equals("S") ? true
							: false);
			cbx_haemophilus_influenza_tipo_b3
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getHaemophilus_influenza_tipo_b3().equals("S") ? true
							: false);
			cbx_haemophilus_influenza_tipo_r1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getHaemophilus_influenza_tipo_r1().equals("S") ? true
							: false);
			cbx_haemophilus_influenza_tipo_r2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getHaemophilus_influenza_tipo_r2().equals("S") ? true
							: false);
			cbx_strectococo_neumonia_1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getStrectococo_neumonia1().equals("S") ? true
							: false);
			cbx_strectococo_neumonia_2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getStrectococo_neumonia2().equals("S") ? true
							: false);
			cbx_strectococo_neumonia_3
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getStrectococo_neumonia3().equals("S") ? true
							: false);
			cbx_srp_1
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getSrp1().equals("S") ? true : false);
			cbx_srp_2
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getSrp2().equals("S") ? true : false);
			cbx_fiebre_amarilla
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getFiebre_amarilla().equals("S") ? true : false);
			ibx_edad.setValue(hisc_aiepi_mn_2_meses_5_anios
					.getEdad());
			dbx_ultima_dosis
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getUltima_dosis());

			lbxObservaciones_cuadro1
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro1());
			lbxObservaciones_cuadro2
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro2());
			lbxObservaciones_cuadro3
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro3());
			lbxObservaciones_cuadro4
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro4());
			lbxObservaciones_cuadro5
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro5());
			lbxObservaciones_cuadro6
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro6());
			lbxObservaciones_cuadro7
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro7());
			lbxObservaciones_cuadro8
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro8());
			lbxObservaciones_cuadro9
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro9());
			lbxObservaciones_cuadro10
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro10());
			lbxObservaciones_cuadro11
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getObservaciones_cuadro11());

			cbxAbdomen
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getAbdomen_enf());
			cbxCabeza
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getCabeza_enf());
			cbxCuello
					.setChecked(hisc_aiepi_mn_2_meses_5_anios
							.getCuello_enf());
			cbxToraz.setChecked(hisc_aiepi_mn_2_meses_5_anios
					.getToraz_enf());

			tbxActualmente_presenta
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getActualmente_presenta());
			tbxCaracteristicas_dolor
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getCaracteristicas_dolor());
			tbxEvolucion
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getEvolucion());
			tbxFecha_inicio
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getFecha_inicio());
			tbxIrradiacion
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getIrradiacion());
			tbxlocalizacion
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getLocalizacion());
			tbxManera_form_inicio
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getManera_form_inicio());
			lbxParrafo_enfermedad_actual
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getParrafo_enfermedad_actual());
			tbxRelacionado
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getRelacionado());
			tbxSintomas_asociados
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getSintomas_asociados());
			tbxTratamiento_recibido
					.setValue(hisc_aiepi_mn_2_meses_5_anios
							.getTratamiento_recibido());

			Utilidades.seleccionarListitem(lbxIntensidad,
					hisc_aiepi_mn_2_meses_5_anios
							.getIntensidad());
			StringTokenizer stringTokenizer = new StringTokenizer(
					hisc_aiepi_mn_2_meses_5_anios
							.getPrimera_presentacion(),
					"|");

			Utilidades.seleccionarListitem(
					lbxPrimera_presentacion,
					stringTokenizer.hasMoreTokens() ? stringTokenizer
							.nextToken() : "");
			spinnerVeces_repetido
					.setText(stringTokenizer.hasMoreTokens() ? stringTokenizer
							.nextToken() : "1");

			seleccion((Radiogroup) getFellow("rdbseleccion"));

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa",
					hisc_aiepi_mn_2_meses_5_anios
							.getCodigo_empresa());
			parametros.put("codigo_sucursal",
					hisc_aiepi_mn_2_meses_5_anios
							.getCodigo_sucursal());
			parametros.put("identificacion",
					hisc_aiepi_mn_2_meses_5_anios
							.getIdentificacion());
			parametros.put("codigo_historia",
					hisc_aiepi_mn_2_meses_5_anios
							.getCodigo_historia());
			parametros.put("via_ingreso", IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS);

			parametros.put("cuadro", "1");
			List<Cuadros_aiepi_resultados> lista = getServiceLocator()
					.getServicio(Cuadros_aiepi_resultadosService.class).listar(
							parametros);
			macroCuadroAIEPI1.cargarDatos(lista);
			//log.info(">>>>>>>>>>>>" + parametros);
			//log.info(">>>>>>>>>>>>" + lista.size());

			parametros.put("cuadro", "2");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI2.cargarDatos(lista);
			//log.info(">>>>>>>>>>>>" + parametros);
			//log.info(">>>>>>>>>>>>" + lista.size());

			parametros.put("cuadro", "3");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI3.cargarDatos(lista);
			//log.info(">>>>>>>>>>>>" + parametros);
			//log.info(">>>>>>>>>>>>" + lista.size());

			parametros.put("cuadro", "4");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI4.cargarDatos(lista);

			parametros.put("cuadro", "5");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI5.cargarDatos(lista);

			parametros.put("cuadro", "6");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI6.cargarDatos(lista);

			parametros.put("cuadro", "7");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI7.cargarDatos(lista);

			parametros.put("cuadro", "8");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI8.cargarDatos(lista);

			parametros.put("cuadro", "9");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI9.cargarDatos(lista);

			parametros.put("cuadro", "10");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI10.cargarDatos(lista);

			parametros.put("cuadro", "11");
			lista.clear();
			lista = getServiceLocator().getServicio(
					Cuadros_aiepi_resultadosService.class).listar(parametros);
			macroCuadroAIEPI11.cargarDatos(lista);

			mostrar_filas_ocultas();

			cargarImpresionDiagnostica(hisc_aiepi_mn_2_meses_5_anios);
			
			infoPacientes
					.setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
							.getCodigo_historia());
			// accionForm(true, tbxAccion.getText());
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void cargarImpresionDiagnostica(
			Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios)
			throws Exception {

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();

		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica
				.setCodigo_historia(hisc_aiepi_mn_2_meses_5_anios
						.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);
	}

	@Override
	public void initHistoria() throws Exception {
	}

	@Override
	public void inicializarVista(String tipo) {

	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {

	}

	@Override
	public void params(Map<String, Object> map) {
	
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
			peso = dbxSignos_vitales_peso.getValue();
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

		dbxP_e_de.setValue(calcularPesoEdad(peso, talla, fecha).getValor());
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

		dbxT_e_de.setValue(calcularTallaEdad(talla, fecha).getValor());
		autoOpcionesCuadros();
	}

	public void calcularPesoTalla() {

		double talla = 0;
		double peso = 0;
		double imc = 0;

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
			peso = dbxSignos_vitales_peso.getValue();
		}

		if (talla != 0 && peso != 0) {
			imc = dbxSignos_vitales_imc.getValue();
			dbxImc_e_de.setValue(calcularImcEdad(imc, fecha).getValor());
		}

		dbxP_t_de.setValue(calcularPesoTalla(peso, talla).getValor());
		autoOpcionesCuadros();
	}

	public void calcularPerimetroCefalicoEdad() {
		double perimetro_cefalico;
		if (meses <= 23) {
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
			dbxPC_e_de.setValue(calcularPerimetroCefalicoEdad(
					perimetro_cefalico, fecha).getValor());
			// autoOpcionesCuadros();
		}
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

	private RespuestaInt calcularPesoEdad(double peso, double talla,
			Timestamp fecha) {
		if (meses <= 23) {
			return GraficasCalculadorUtils.calcularPesoEdad0$2Anios(sexo, peso,
					talla, fecha);
		} else {
			return GraficasCalculadorUtils.calcularPesoEdad2$5Anios(sexo, peso,
					talla, fecha);
		}
	}

	private RespuestaInt calcularPerimetroCefalicoEdad(
			double perimetro_cefalico, Timestamp fecha) {
		// TODO:Solo a menor de 2 años
		return GraficasCalculadorUtils.calcularPerimetroCefalicoEdad0$2Anios(
				sexo, perimetro_cefalico, fecha);
	}

	private RespuestaInt calcularPesoTalla(double peso, double talla) {
		if (meses <= 23) {
			return GraficasCalculadorUtils.calcularPesoTalla0$2Anios(sexo,
					peso, talla);
		} else {
			return GraficasCalculadorUtils.calcularPesoTalla2$5Anios(sexo,
					peso, talla);
		}
	}

	private RespuestaInt calcularTallaEdad(double talla, Timestamp fecha) {
		if (meses <= 23) {
			return GraficasCalculadorUtils.calcularTallaEdad0$2Anios(sexo,
					talla, fecha);
		} else {
			return GraficasCalculadorUtils.calcularTallaEdad2$5Anios(sexo,
					talla, fecha);
		}
	}

	private RespuestaInt calcularImcEdad(double imc, Timestamp fecha) {
		return GraficasCalculadorUtils
				.calcularIMCEdad0$5Anios(sexo, imc, fecha);
	}

	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			if (dbxP.getValue() != null && dbxT.getValue() != null) {
				double imc = dbxP.getValue()
						/ (Math.pow(dbxT.getValue() / 100, 2));
				dbxI.setValue(imc);
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
		activar_check_tem();
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
					"/images/aiepi/buen_trato_2_5.png"), this.getPage(),
					"Medidas de buen trato", 0, 0, 0);
			break;
		}
	}

	@Override
	public String getInformacionClinica() {
		return "";
	}

	public void onSeleccionarRelacionAcompaniante() {
		if (lbxParentesco_acompanante.getSelectedItem().getValue().toString()
				.equals("1")) {
			// Madre

			tbxId_madre.setValue(tbxId_acompanante.getValue());
			tbxNombres_madre.setValue(tbxNombres_acompanante.getValue());
			tbxApellidos_madre.setValue(tbxApellidos_acompanante.getValue());
			tbxTelefono_madre.setValue(tbxTelefono_acompanante.getValue());

			if (tbxId_madre.getValue().equals(tbxId_padre.getValue())) {
				tbxId_padre.setValue("");
			}
			if (tbxNombres_madre.getValue().equals(tbxNombres_padre.getValue())) {
				tbxNombres_padre.setValue("");
			}
			if (tbxApellidos_madre.getValue().equals(
					tbxApellidos_padre.getValue())) {
				tbxApellidos_padre.setValue("");
			}
			if (tbxTelefono_madre.getValue().equals(
					tbxTelefono_padre.getValue())) {
				tbxTelefono_padre.setValue("");
			}
		} else if (lbxParentesco_acompanante.getSelectedItem().getValue()
				.toString().equals("2")) {
			// Padre

			tbxId_padre.setValue(tbxId_acompanante.getValue());
			tbxNombres_padre.setValue(tbxNombres_acompanante.getValue());
			tbxApellidos_padre.setValue(tbxApellidos_acompanante.getValue());
			tbxTelefono_padre.setValue(tbxTelefono_acompanante.getValue());

			if (tbxId_padre.getValue().equals(tbxId_madre.getValue())) {
				tbxId_madre.setValue("");
			}
			if (tbxNombres_padre.getValue().equals(tbxNombres_madre.getValue())) {
				tbxNombres_madre.setValue("");
			}
			if (tbxApellidos_padre.getValue().equals(
					tbxApellidos_madre.getValue())) {
				tbxApellidos_madre.setValue("");
			}
			if (tbxTelefono_padre.getValue().equals(
					tbxTelefono_madre.getValue())) {
				tbxTelefono_madre.setValue("");
			}
		}

		if (lbxParentesco_acompanante.getSelectedItem().getValue().toString()
				.equals("8")) {
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
				lbxEnfermedad_actual.invalidate();
				lbEnfermedad_actual.setVisible(true);
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

	public void LimpiarPlantilla() {
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
}