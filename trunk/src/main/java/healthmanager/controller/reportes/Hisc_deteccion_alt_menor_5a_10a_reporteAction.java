/*
 * hisc_deteccion_alt_menor_5a_10aAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Hisc_deteccion_alt_menor_5a_10a;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Test_puntuacion_figura_humana;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Hisc_deteccion_alt_menor_5a_10aService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Test_puntuacion_figura_humanaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IHistorias_clinicas;
import com.framework.constantes.ITipos_coordenada;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.FiguraHumanaMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.CalculadorUtil;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Hisc_deteccion_alt_menor_5a_10a_reporteAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Hisc_deteccion_alt_menor_5a_10a_reporteAction.class);

	// Componentes //
	// Manuel
	@View
	private Radiogroup rdbHepatitis_a;

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
	private Label lbxMotivo_consulta;
	@View
	private Label lbxGestion_actual;
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
	private Label lbxObservaciones_3_1;
	@View
	private Label lbxPatologicos_medicos;
	@View
	private Label lbxPatologicos_quirurgicos;
	@View
	private Label lbxPatologicos_hospitalizaciones;
	@View
	private Label lbxPatologicos_medicacion;
	// ------------- Jose
	
		@View
		private Label lbxPatologicos_alergicos;
		
		//-------------------
		
	@View
	private Label lbxObservaciones_3_3;
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
	private Label lbxPaterno_medicos;
	@View
	private Textbox tbxPaterno_alergico;
	@View
	private Intbox ibxPaterno_talla;
	@View
	private Label lbxMaterno_medicos;
	@View
	private Textbox tbxMaterno_alergico;
	@View
	private Intbox ibxMaterno_talla;
	@View
	private Label lbxOtros_4;
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
	private Label lbxObservaciones_6_2;
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
	private Label lbxAnalisis_8;
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
	private Label lbxRecomendaciones_9;
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
	private Groupbox gbxContenido;

	@View
	private Radiogroup rdbCa_Diagnostico_crecimiento;
	@View
	private Radiogroup rdbCa_Diagnostico_desarrollo;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;
	@View
	private Intbox ibxNum_mortinatos_v;
	@View
	private Label lbxComplicaciones_embarazo_parto;
	@View
	private Intbox ibxPeso_al_nacer;
	@View
	private Intbox ibxTalla_al_nacer;
	@View
	private Intbox ibxApgar_minutos;
	@View
	private Intbox ibxApgar_5_minutos;
	@View
	private Label lbxObservaciones_vacunales;
	
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
	private Hisc_deteccion_alt_menor_5a_10a hisc_deteccion_alt_menor_5a_10a;
	private ESexo sexo;
	private Timestamp fecha;

	private RespuestaInt coordenadaTallaEdad;
	private RespuestaInt coordenadaIMCEdad;

	private List coordenadasTE;
	private List coordenadasIE;

	private Admision admision;

	private final String[] idsExcluyentes = {};

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	@View(isMacro = true)
	private FiguraHumanaMacro macroFiguraHumana;

	public String valida_admision;
	

	/* Observaciones */

	@View private Label lbxObservaciones_cabeza;
	@View private Label lbxObservaciones_sentidos;
	@View private Label lbxObservaciones_cover;
	@View private Label lbxObservaciones_cuello;
	@View private Label lbxObservaciones_torax;
	@View private Label lbxObservaciones_cardiaco;
	@View private Label lbxObservaciones_soplo;
	@View private Label lbxObservaciones_abdomen;
	@View private Label lbxObservaciones_masas;
	@View private Label lbxObservaciones_megalias;
	@View private Label lbxObservaciones_genito;
	@View private Label lbxObservaciones_columna;
	@View private Label lbxObservaciones_extremidades;
	@View private Label lbxObservaciones_piel;
	@View private Label lbxObservaciones_neurologico;
	@View private Label lbxObservaciones_boca;
	
	@View private Row row1;
	@View private Row row2;
	@View private Row row3;
	@View private Row row4;
	@View private Row row5;
	@View private Row row6;
	

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
							IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A);
					paciente = admision.getPaciente();
					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
					fecha = paciente.getFecha_nacimiento();
				}
			}
			
			if (id_codigo_historia != null) {
				hisc_deteccion_alt_menor_5a_10a = new Hisc_deteccion_alt_menor_5a_10a();
				hisc_deteccion_alt_menor_5a_10a.setCodigo_empresa(codigo_empresa);
				hisc_deteccion_alt_menor_5a_10a.setCodigo_sucursal(codigo_sucursal);
				hisc_deteccion_alt_menor_5a_10a.setCodigo_historia(id_codigo_historia);
				hisc_deteccion_alt_menor_5a_10a = getServiceLocator().getServicio(Hisc_deteccion_alt_menor_5a_10aService.class).consultar(hisc_deteccion_alt_menor_5a_10a);
				
				if(hisc_deteccion_alt_menor_5a_10a!=null){
					
					if(hisc_deteccion_alt_menor_5a_10a.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DETECCIÓN DE ALTERACIÓN AL MENOR DE 5 A 10 AÑOS\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DETECCIÓN DE ALTERACIÓN AL MENOR DE 5 A 10 AÑOS\nHISTORIA DE CONTROL");
					}

					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(hisc_deteccion_alt_menor_5a_10a.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,idsExcluyentes);
					mostrarDatos(hisc_deteccion_alt_menor_5a_10a);
					
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void params(Map<String, Object> map) {

	}

	public void listarCombos() {
		Utilidades.listarElementoListbox(lbxParentesco, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxHemoclasificacion, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxVdlr, true, getServiceLocator());
		iniciarSemEmbarazo();
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		hisc_deteccion_alt_menor_5a_10a = (Hisc_deteccion_alt_menor_5a_10a) obj;
		try {
			infoPacientes.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_deteccion_alt_menor_5a_10a
					.getFecha_inicial());

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
			lbxMotivo_consulta.setValue(hisc_deteccion_alt_menor_5a_10a
					.getMotivo_consulta());
			lbxGestion_actual.setValue(hisc_deteccion_alt_menor_5a_10a
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
			lbxObservaciones_3_1.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_3_1());
			lbxPatologicos_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPatologicos_medicos());
			lbxPatologicos_quirurgicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPatologicos_quirurgicos());
			lbxPatologicos_hospitalizaciones
					.setValue(hisc_deteccion_alt_menor_5a_10a
							.getPatologicos_hospitalizaciones());
			lbxPatologicos_medicacion.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPatologicos_medicacion());
			lbxPatologicos_alergicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getAntecedentes_alergicos());
			lbxObservaciones_3_3.setValue(hisc_deteccion_alt_menor_5a_10a
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
			lbxPaterno_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPaterno_medicos());
			tbxPaterno_alergico.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPaterno_alergico());
			ibxPaterno_talla.setValue(hisc_deteccion_alt_menor_5a_10a
					.getPaterno_talla());
			lbxMaterno_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getMaterno_medicos());
			tbxMaterno_alergico.setValue(hisc_deteccion_alt_menor_5a_10a
					.getMaterno_alergico());
			ibxMaterno_talla.setValue(hisc_deteccion_alt_menor_5a_10a
					.getMaterno_talla());
			lbxOtros_4.setValue(hisc_deteccion_alt_menor_5a_10a.getOtros_4());
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
			lbxObservaciones_6_2.setValue(hisc_deteccion_alt_menor_5a_10a
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

			lbxAnalisis_8.setValue(hisc_deteccion_alt_menor_5a_10a
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
			lbxRecomendaciones_9.setValue(hisc_deteccion_alt_menor_5a_10a
					.getRecomendaciones_9());

			ibxNum_mortinatos_v.setValue(hisc_deteccion_alt_menor_5a_10a
					.getNum_mortinatos_v());
			lbxComplicaciones_embarazo_parto
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

			List<Test_puntuacion_figura_humana> puntos = getServiceLocator()
					.getServicio(Test_puntuacion_figura_humanaService.class)
					.listar(param);
			macroFiguraHumana.cargarPuntos(puntos);

			lbxObservaciones_vacunales.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_vacunales());
			

			FormularioUtil.mostrarObservaciones(rdbCabeza_cara, "2", lbxObservaciones_cabeza, row1);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar, "2", lbxObservaciones_torax, row1);
			FormularioUtil.mostrarObservaciones(rdbAbdomen,"2",lbxObservaciones_abdomen,row1);
			FormularioUtil.mostrarObservaciones(rdbOrgano_de_los_sentidos,"2",lbxObservaciones_sentidos,row2);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar_ritmo_cardiaco, "2", lbxObservaciones_cardiaco, row2);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_masas,"2",lbxObservaciones_masas,row2);
			FormularioUtil.mostrarObservaciones(rdbCover_uncover_test, "2", lbxObservaciones_cover, row3);
			FormularioUtil.mostrarObservaciones(rdbTorax_cardiopulmonar_soplo, "2", lbxObservaciones_soplo, row3);
			FormularioUtil.mostrarObservaciones(rdbAbdomen_megalias, "2", lbxObservaciones_megalias, row3);
			FormularioUtil.mostrarObservaciones(rdbBoca,"2",lbxObservaciones_boca,row4);
			FormularioUtil.mostrarObservaciones(rdbColumna_vertebral, "2", lbxObservaciones_columna, row4);
			FormularioUtil.mostrarObservaciones(rdbGenito_urinario, "2", lbxObservaciones_genito, row4);
			FormularioUtil.mostrarObservaciones(rdbCuello, "2", lbxObservaciones_cuello, row5);
			FormularioUtil.mostrarObservaciones(rdbExtremidades, "2", lbxObservaciones_extremidades, row5);			
			FormularioUtil.mostrarObservaciones(rdbPiel_anexos, "2", lbxObservaciones_piel, row6);
			FormularioUtil.mostrarObservaciones(rdbNeurologico,"2",lbxObservaciones_neurologico,row6);

			lbxObservaciones_cabeza.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_cabeza());
			lbxObservaciones_abdomen.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_abdomen());
			lbxObservaciones_neurologico.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_neurologico());
			
			lbxObservaciones_sentidos.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_sentidos());
			lbxObservaciones_masas.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_masas());
			
			lbxObservaciones_cover.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_cover());
			lbxObservaciones_megalias.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_megalias());
			
			lbxObservaciones_cuello.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_cuello());
			lbxObservaciones_genito.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_genito());

			lbxObservaciones_torax.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_torax());
			lbxObservaciones_columna.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_columna());

			lbxObservaciones_cardiaco.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_cardiaco());
			lbxObservaciones_extremidades.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_extremidades());

			lbxObservaciones_soplo.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_soplo());
			lbxObservaciones_piel.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_piel());
			
			lbxObservaciones_boca.setValue(hisc_deteccion_alt_menor_5a_10a
					.getObservaciones_boca());

			

			calcularCoordenadas(false);

			// Mostramos la vista //
			// tbxAccion.setText("modificar");
			infoPacientes.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
					.getCodigo_historia());
			// accionForm(true, tbxAccion.getText());
			inicializarVista(hisc_deteccion_alt_menor_5a_10a.getTipo_historia());
			// FormularioUtil.deshabilitarComponentes(rowCita_anterior,true,
			// new
			// String[] {});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void initHistoria() throws Exception {

	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowCita_anterior.setVisible(false);
			rowPerinatales.setVisible(true);
			rowPatologicos.setVisible(true);
			rowAntecedentesFamiliares.setVisible(true);
			rowAnalisis.setVisible(true);
		} else {
			rowCita_anterior.setVisible(true);
			rowPerinatales.setVisible(false);
			rowPatologicos.setVisible(false);
			rowAntecedentesFamiliares.setVisible(false);
			rowAnalisis.setVisible(false);
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

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
		lbxComplicaciones_embarazo_parto
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
		lbxObservaciones_3_1.setValue(hisc_deteccion_alt_menor_5a_10a
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
		lbxPaterno_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
				.getPaterno_medicos());
		tbxPaterno_alergico.setValue(hisc_deteccion_alt_menor_5a_10a
				.getPaterno_alergico());
		ibxPaterno_talla.setValue(hisc_deteccion_alt_menor_5a_10a
				.getPaterno_talla());
		lbxMaterno_medicos.setValue(hisc_deteccion_alt_menor_5a_10a
				.getMaterno_medicos());
		tbxMaterno_alergico.setValue(hisc_deteccion_alt_menor_5a_10a
				.getMaterno_alergico());
		ibxMaterno_talla.setValue(hisc_deteccion_alt_menor_5a_10a
				.getMaterno_talla());
		lbxOtros_4.setValue(hisc_deteccion_alt_menor_5a_10a.getOtros_4());

		// Datos del paciente
		tbxNombre_del_padre.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNombre_del_padre());
		ibxEdad_padre.setValue(hisc_deteccion_alt_menor_5a_10a.getEdad_padre());
		tbxNombre_de_la_madre.setValue(hisc_deteccion_alt_menor_5a_10a
				.getNombre_de_la_madre());
		ibxEdad_madre.setValue(hisc_deteccion_alt_menor_5a_10a.getEdad_madre());
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		
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
		impresion_diagnostica.setCodigo_historia(hisc_deteccion_alt_menor_5a_10a
						.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);

		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);

	}

	@Override
	public String getInformacionClinica() {
		return "";
	}
}