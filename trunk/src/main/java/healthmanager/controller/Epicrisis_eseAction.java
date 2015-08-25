/*
 * epicrisis_eseAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo2;
import healthmanager.modelo.bean.Epicrisis_ese;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Sigvitales;
import healthmanager.modelo.service.Antecedentes_personalesService;
import healthmanager.modelo.service.Epicrisis_eseService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Hisc_urgenciaService;
import healthmanager.modelo.service.Impresion_diagnosticaService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.SignosVitalesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.ConvertidorXmlToMap;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

public class Epicrisis_eseAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static Logger log = Logger.getLogger(Epicrisis_eseAction.class);

	private Epicrisis_eseService epicrisis_eseService;

	private GeneralExtraService generalExtraService;

	// Componentes //
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

	// @View private Textbox tbxCie_principal;
	// @View private Listbox lbxTipo_principal;
	// @View private Textbox tbxCie_relacionado1;
	// @View private Listbox lbxTipo_relacionado1;
	// @View private Textbox tbxCie_relacionado2;
	// @View private Listbox lbxTipo_relacionado2;
	// @View private Textbox tbxCie_relacionado3;
	// @View private Listbox lbxTipo_relacionado3;
	@View
	private Datebox dtbxIngreso;
	@View
	private Datebox dtbxEgreso;
	@View
	private Textbox tbxEstancia;
	@View
	private Textbox tbxCausa_salida;
	@View
	private Listbox lbxDestino;
	@View
	private Listbox lbxEstado_salida;
	@View
	private Listbox lbxServicio_ingreso;
	@View
	private Listbox lbxServicio_egreso;
	@View
	private Textbox tbxAntecedentes;
	@View
	private Textbox tbxSintesis_enfermedad;
	@View
	private Checkbox chbHemograma;
	@View
	private Checkbox chbN_ureico;
	@View
	private Checkbox chbC_antibiograma;
	@View
	private Checkbox chbCitoquimico;
	@View
	private Checkbox chbPatologia;
	@View
	private Checkbox chbV_sedimentacion;
	@View
	private Checkbox chbCoprologico;
	@View
	private Checkbox chbBilirrubinemia;
	@View
	private Checkbox chbInmunologicos;
	@View
	private Checkbox chbRx_simple;
	@View
	private Checkbox chbH_clasificar;
	@View
	private Checkbox chbFalciformia;
	@View
	private Checkbox chbTransaminasas;
	@View
	private Checkbox chbVih;
	@View
	private Checkbox chbRx_contrastada;
	@View
	private Checkbox chbP_cruzada;
	@View
	private Checkbox chbProtelnograma;
	@View
	private Checkbox chbP_upidlco;
	@View
	private Checkbox chbE_cardiograma;
	@View
	private Checkbox chbRx_invasivos;
	@View
	private Checkbox chbTpt;
	@View
	private Checkbox chbElectroferesis_hb;
	@View
	private Checkbox chbExt_sangre;
	@View
	private Checkbox chbEcocardiograma;
	@View
	private Checkbox chbTac;
	@View
	private Checkbox chbT_p;
	@View
	private Checkbox chbVdrl;
	@View
	private Checkbox chbIonograma;
	@View
	private Checkbox chbDoppler;
	@View
	private Checkbox chbR_magnetica;
	@View
	private Checkbox chbR_plaquetas;
	@View
	private Checkbox chbWidal;
	@View
	private Checkbox chbG_arteriales;
	@View
	private Checkbox chbE_encefalograma;
	@View
	private Checkbox chbEcografia;
	@View
	private Checkbox chbGlicemia;
	@View
	private Checkbox chbAsto;
	@View
	private Checkbox chbEnzimas;
	@View
	private Checkbox chbBiopsia;
	@View
	private Checkbox chbGamagrafias;
	@View
	private Checkbox chbUrianalisis;
	@View
	private Checkbox chbProteina_cr;
	@View
	private Checkbox chbHormonas;
	@View
	private Checkbox chbMedulograma;
	@View
	private Checkbox chbBaciloscopias;
	@View
	private Checkbox chbCreatinina;
	@View
	private Checkbox chbGram;
	@View
	private Checkbox chbDrogas_sericas;
	@View
	private Checkbox chbEndoscopia;
	@View
	private Checkbox chbOtros;
	@View
	private Textbox tbxOtros_txt;
	@View
	private Textbox tbxComentarios_paraclinicos_positivos;
	@View
	private Textbox tbxCirugia_tratamiento_medico;
	@View
	private Textbox tbxInterconsultas_juntas_medicas;
	@View
	private Textbox tbxComplicaciones;
	@View
	private Textbox tbxRecomendaciones;

	// @View private Toolbarbutton btnFicha_principal;
	// @View private Toolbarbutton btnFicha_relacionado1;
	// @View private Toolbarbutton btnFicha_relacionado2;
	// @View private Toolbarbutton btnFicha_relacionado3;

	// @View(isMacro = true) private BandboxRegistrosMacro bandboxCie_principal;
	// @View(isMacro = true) private BandboxRegistrosMacro
	// bandboxCie_relacionado1;
	// @View(isMacro = true) private BandboxRegistrosMacro
	// bandboxCie_relacionado2;
	// @View(isMacro = true) private BandboxRegistrosMacro
	// bandboxCie_relacionado3;

	@View
	Row rowOtros1;
	@View
	Row rowOtros2;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
    @View(isMacro = true)
    private ImpresionDiagnosticaMacro macroImpresion_diagnosticaEpicrisis;

	private final String[] idsExcluyentes = {};

	private Admision admision;
	private Opciones_via_ingreso opciones_via_ingreso;
	private Hisc_urgencia historia_clinica;
	

	@View(isMacro = true)
	private SignosVitalesMacro mcSignosVitales;

	@Override
	public void init() {
		listarCombos();
		mcSignosVitales.setZkWindow(this);
		mcSignosVitales.getLabelCreatinina().setVisible(false);
		mcSignosVitales.getDoubleboxCreatinina().setVisible(false);
		mcSignosVitales.getLabelTFG().setVisible(false);
		mcSignosVitales.getDoubleboxTFG().setVisible(false);
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			if (admision != null) {
				cargarInformacion_paciente();
				macroImpresion_diagnostica.inicializarMacro(this, admision,
						IVias_ingreso.URGENCIA);
				 macroImpresion_diagnosticaEpicrisis.inicializarMacro(this,
				 admision, IVias_ingreso.URGENCIA);
				Historia_clinica hc = new Historia_clinica();
				hc.setCodigo_empresa(admision.getCodigo_empresa());
				hc.setCodigo_sucursal(admision.getCodigo_sucursal());
				hc.setNro_ingreso(admision.getNro_ingreso());
				hc.setNro_identificacion(admision.getNro_identificacion());
				hc = this.getServiceLocator()
						.getServicio(GeneralExtraService.class)
						.consultar(hc);

				if (hc != null) {
					historia_clinica = new Hisc_urgencia();
					historia_clinica.setCodigo_empresa(hc.getCodigo_empresa());
					historia_clinica
							.setCodigo_sucursal(hc.getCodigo_sucursal());
					historia_clinica
							.setCodigo_historia(hc.getCodigo_historia());
					historia_clinica = this.getServiceLocator()
							.getServicio(Hisc_urgenciaService.class)
							.consultar(historia_clinica);
					if (historia_clinica != null) {
						try {
							infoPacientes.setFecha_inicio(historia_clinica
									.getFecha_ingreso());
							infoPacientes.setFecha_inicio(historia_clinica
									.getFecha_cierre());
							infoPacientes.setCodigo_historia(historia_clinica
									.getCodigo_historia());
							cargarImpresionDiagnostica(historia_clinica);
							cargarImpresionDiagnostica2(historia_clinica);
							cargarEpicrisis(historia_clinica);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void cargarEpicrisis(Hisc_urgencia hisc) {
		tbxSintesis_enfermedad.setValue(hisc.getEnfermedad_actual());
		//log.info("===> antece " + tbxAntecedentes.getValue());

		Sigvitales sigvitales = new Sigvitales();
		sigvitales.setCodigo_empresa(codigo_empresa);
		sigvitales.setCodigo_sucursal(codigo_sucursal);
		sigvitales.setCodigo_historia(hisc.getCodigo_historia());
		sigvitales = getServiceLocator().getSigvitalesService().consultar(
				sigvitales);
		//log.info("====> sigvitalesss " + sigvitales);
		mcSignosVitales.mostrarSigvitales(sigvitales);
		//log.info("==> mcro sigvita " + mcSignosVitales);

		Map<String, Object> parameters = new HashMap<String, Object>();

		// parameters.put("nro_historia", hisc.getn);
		parameters.put("codigo_historia", hisc.getCodigo_historia());
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);

		List<Map<String, Object>> listado_antecedentes = getServiceLocator()
				.getServicio(Antecedentes_personalesService.class)
				.listar_reporte(parameters);

		//log.info("====> list antece " + listado_antecedentes);

		StringBuilder antecedentes = new StringBuilder();

		antecedentes.append("- ANTECEDENTES PERSONALES").append("\n");
		for (int i = 0; i < 17; i++) {
			Map<String, Object> mapa_datos = listado_antecedentes.get(i);
			String respuesta = (String) mapa_datos.get("respuesta");
			if (respuesta != null && respuesta.equals("S")) {
				antecedentes.append(mapa_datos.get("antecedente")).append(": ");
				antecedentes.append(mapa_datos.get("observacion")).append("\t");
			}
		}

		for (int i = 17; i < listado_antecedentes.size(); i++) {
			Map<String, Object> mapa_datos = listado_antecedentes.get(i);
			String respuesta = (String) mapa_datos.get("respuesta");
			if (respuesta != null && respuesta.equals("S")) {
				antecedentes.append(mapa_datos.get("antecedente")).append(": ");
				antecedentes.append(mapa_datos.get("observacion")).append("\t");
			}
		}
		antecedentes.append("\n");

		Map<String, Object> mapa_antecedente1 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_hipertension());
		Map<String, Object> mapa_antecedente2 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_alergicos());
		Map<String, Object> mapa_antecedente3 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_asma());
		Map<String, Object> mapa_antecedente4 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_cancer());
		Map<String, Object> mapa_antecedente5 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_diabetes());
		Map<String, Object> mapa_antecedente6 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_dislipidemia());
		Map<String, Object> mapa_antecedente7 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_ecv());
		Map<String, Object> mapa_antecedente8 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_enf_coronaria());
		Map<String, Object> mapa_antecedente9 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_enf_mental());
		Map<String, Object> mapa_antecedente11 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_hematologia());
		Map<String, Object> mapa_antecedente12 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_infecciosa_hepatitisb());
		Map<String, Object> mapa_antecedente13 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_infecciosa_lepra());
		Map<String, Object> mapa_antecedente14 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_infecciosa_sifilis());
		Map<String, Object> mapa_antecedente15 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_infecciosa_tuberculosis());
		Map<String, Object> mapa_antecedente16 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_infecciosa_vih());
		Map<String, Object> mapa_antecedente17 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_muerte_im_acv());
		Map<String, Object> mapa_antecedente18 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_nefropatias());
		Map<String, Object> mapa_antecedente19 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_neoplasicos());
		Map<String, Object> mapa_antecedente20 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_obesos());
		Map<String, Object> mapa_antecedente22 = ConvertidorXmlToMap.convertirToMap(hisc.getAnte_fam_otros());

		antecedentes.append("- ANTECEDENTES FAMILIARES").append("\n");
		if (!mapa_antecedente1.isEmpty()) {
			antecedentes.append("Hipertension: ").append(mapa_antecedente1+",").append("\t");
		}if (!mapa_antecedente2.isEmpty()) {
			antecedentes.append("Alergicos: ").append(mapa_antecedente2+",").append("\t");
		}if (!mapa_antecedente3.isEmpty()) {
			antecedentes.append("Asma: ").append(mapa_antecedente3+",").append("\t");
		}if (!mapa_antecedente4.isEmpty()) {
			antecedentes.append("Cancer: ").append(mapa_antecedente4+",").append("\t");
		}if (!mapa_antecedente5.isEmpty()) {
			antecedentes.append("Diabetes: ").append(mapa_antecedente5+",").append("\t");
		}if (!mapa_antecedente6.isEmpty()) {
			antecedentes.append("Dislipidemia: ").append(mapa_antecedente6+",").append("\t");
		}if (!mapa_antecedente7.isEmpty()) {
			antecedentes.append("Enfermedad cerebro-vascular: ").append(mapa_antecedente7+",").append("\t");
		}if (!mapa_antecedente8.isEmpty()) {
			antecedentes.append("Enf. Coronaria: ").append(mapa_antecedente8+",").append("\t");
		}if (!mapa_antecedente9.isEmpty()) {
			antecedentes.append("Enfermedad Mental: ").append(mapa_antecedente9+",").append("\t");
		}if (!mapa_antecedente11.isEmpty()) {
			antecedentes.append("Hematología: ").append(mapa_antecedente11+",").append("\t");
		}if (!mapa_antecedente12.isEmpty()) {
			antecedentes.append("Hepatitis B: ").append(mapa_antecedente12+",").append("\t");
		}if (!mapa_antecedente13.isEmpty()) {
			antecedentes.append("Lepra: ").append(mapa_antecedente13+",").append("\t");
		}if (!mapa_antecedente14.isEmpty()) {
			antecedentes.append("Sifilis: ").append(mapa_antecedente14+",").append("\t");
		}if (!mapa_antecedente15.isEmpty()) {
			antecedentes.append("Tuberculosis: ").append(mapa_antecedente15+",").append("\t");
		}if (!mapa_antecedente16.isEmpty()) {
			antecedentes.append("VIH: ").append(mapa_antecedente16+",").append("\t");
		}if (!mapa_antecedente17.isEmpty()) {
			antecedentes.append("Muerte en < 60 años (Infarto al miocardio): ").append(mapa_antecedente17+",").append("\t");
		}if (!mapa_antecedente18.isEmpty()) {
			antecedentes.append("Nefropatías: ").append(mapa_antecedente18+",").append("\t");
		}if (!mapa_antecedente19.isEmpty()) {
			antecedentes.append("Neoplásicos: ").append(mapa_antecedente19+",").append("\t");
		}if (!mapa_antecedente20.isEmpty()) {
			antecedentes.append("Obesos: ").append(mapa_antecedente20+",").append("\t");
		}if (!mapa_antecedente22.isEmpty()) {
			antecedentes.append("Otros: ").append(mapa_antecedente22+",").append("\t");			
		}if(hisc.getAnte_fam_observaciones() != null){
		antecedentes.append("Observaciones: ").append(hisc.getAnte_fam_observaciones()+",").append("\n");
		}
		
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy")
						.format(admision.getPaciente()
								.getFecha_nacimiento()), "1", false));
		if (admision.getPaciente().getSexo().equals("F")) {
			if(edad >=9){
		antecedentes.append("- ANTECEDENTES GINECOBSTÉTRICOS").append("\n");
		if(hisc.getDescripcion_menarca() != null){
			antecedentes.append("Menarca: ").append(hisc.getDescripcion_menarca()+",").append("\t");
				}if(hisc.getGineco_ciclo1().equals("1")){
					antecedentes.append("Ciclo: Regular "+",").append("\t");
				}else if(hisc.getGineco_ciclo1().equals("2")){
					antecedentes.append("Ciclo: Iregular "+",").append("\t");
				}else if(hisc.getGineco_ciclo1().equals("3")){
					antecedentes.append("Ciclo: " + hisc.getOtro_ciclo()+",").append("\t");
				}
				
				if(hisc.getGineco_fur() != null){
					antecedentes.append("Fecha Última mestruacion: ").append(hisc.getGineco_fur()+",").append("\t");
				}if(hisc.getGineco_fep() != null){
					antecedentes.append("Fecha Esperada de parto: ").append(hisc.getGineco_fep()+",").append("\t");
				}if(hisc.getGineco_generales() !=null){
					antecedentes.append("Parto Gemelares: ").append(hisc.getGineco_generales()+",").append("\t");
				}if(hisc.getGineco_malformaciones() != null){
					antecedentes.append("Malformaciones: ").append(hisc.getGineco_malformaciones()+",").append("\t");
				}
//				if(hisc.getGineco_menopaudia() != null){
//					antecedentes.append("Menopausia: ").append(hisc.getGineco_menopaudia()+",").append("\t");
//				}
				if(hisc.getGineco_nacidos_muertos() != null){
					antecedentes.append("Nacidos muertos: ").append(hisc.getGineco_nacidos_muertos()+",").append("\t");
				}if(hisc.getGineco_nacidos_vivos() != null){
					antecedentes.append("Nacidos vivos: ").append(hisc.getGineco_nacidos_vivos()+",").append("\t");
				}if(hisc.getGineco_nro_abortos() != null){
					antecedentes.append("Número de aborto: ").append(hisc.getGineco_nro_abortos()+",").append("\t");
				}if(hisc.getGineco_nro_cesarias() != null){
					antecedentes.append("Número de Cesarias: ").append(hisc.getGineco_nro_cesarias()+",").append("\t");
				}if(hisc.getGineco_nro_gestaciones() != null){
					antecedentes.append("Gestaciones: ").append(hisc.getGineco_nro_gestaciones()+",").append("\t");
				}if(hisc.getGineco_nro_partos() != null){
					antecedentes.append("Partos vaginales: ").append(hisc.getGineco_nro_partos()+",").append("\t");
				}if(hisc.getGineco_preterminos() != null){
					antecedentes.append("Preterminos: ").append(hisc.getGineco_preterminos()+",").append("\t");
				}if(hisc.getVida_marital().equals("S")){
					antecedentes.append("Vida marital - Inicio: ").append(hisc.getFecha_vida_marital()+",").append("\t");
				}if(hisc.getOtro_ginecostetrico().equals("S")){
					antecedentes.append("Vida obstetrica - Cuál: ").append(hisc.getOtro_ginecostetrico()+",").append("\t");
				}if(hisc.getAnt_gin_tiene_citologia() != false){
					antecedentes.append("Citología - Fecha y Resultado: ")
					.append(hisc.getAnt_gin_fecha_ultima_citologia() + " -- " )
					.append(hisc.getAnt_gin_citologia_resultado() + ",")
					.append("\t");
				}if(hisc.getPatologia_embarazo_parto() != null){
					antecedentes.append("Patologia de Embarazo o parto : ").append(hisc.getPatologia_embarazo_parto()).append("\t");
				}
			}
		}

		tbxAntecedentes.setValue(antecedentes.toString());

	}

	public void listarCombos() {
		// List<Elemento> elementos =
		// this.getServiceLocator().getElementoService().listar("tipo_diagnostico");
		// Utilidades.listarElementoListboxFromType(lbxTipo_principal,
		// true,elementos, false);
		// lbxTipo_principal.setSelectedIndex(1);
		// Utilidades.listarElementoListboxFromType(lbxTipo_relacionado1,
		// true,elementos, false);
		// Utilidades.listarElementoListboxFromType(lbxTipo_relacionado2,
		// true,elementos, false);
		// Utilidades.listarElementoListboxFromType(lbxTipo_relacionado3,
		// true,elementos, false);
		listarParameter();
		Utilidades
				.listarElementoListbox(lbxDestino, false, getServiceLocator());
		Utilidades.listarElementoListbox(lbxEstado_salida, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxServicio_ingreso, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxServicio_egreso, false,
				getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		
		listitem.setValue("codigo_historia");
		listitem.setLabel("Codigo historia");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
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

			epicrisis_eseService.setLimit("limit 25 offset 0");

			List<Epicrisis_ese> lista_datos = epicrisis_eseService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Epicrisis_ese epicrisis_ese : lista_datos) {
				rowsResultado.appendChild(crearFilas(epicrisis_ese, this));
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

		final Epicrisis_ese epicrisis_ese = (Epicrisis_ese) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(epicrisis_ese.getCodigo_historia() + ""));
		fila.appendChild(new Label(epicrisis_ese.getIdentificacion() + ""));
		
		fila.appendChild(new Label(
				admision.getPaciente().getNombre1()
						+ (admision.getPaciente().getNombre2()
								.isEmpty() ? "" : " "
								+ admision.getPaciente()
										.getNombre2()) + " "
						+ admision.getPaciente().getApellido1()
						+ " "
						+ admision.getPaciente().getApellido2()
						+ ""));

		Datebox datebox = new Datebox(epicrisis_ese.getIngreso());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		datebox.setInplace(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(historia_clinica.getTipo_historia().equals(
				IConstantes.TIPO_HISTORIA_PRIMERA_VEZ) ? "PRIMERA VEZ"
				: "CONTROL"));

		hbox.appendChild(space);

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
						groupboxEditar.setVisible(true);
						groupboxConsulta.setVisible(false);
						mostrarDatos(epicrisis_ese);
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
													// do the thingr
													eliminarDatos(epicrisis_ese);
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
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //

				Epicrisis_ese epicrisis_ese = new Epicrisis_ese();
				epicrisis_ese.setCodigo_empresa(empresa.getCodigo_empresa());
				epicrisis_ese.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				epicrisis_ese.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				epicrisis_ese.setIdentificacion(historia_clinica
						.getNro_identificacion());
				epicrisis_ese.setNro_ingreso(historia_clinica.getNro_ingreso());
				
				 Impresion_diagnostica id =
				 macroImpresion_diagnosticaEpicrisis
				 .obtenerImpresionDiagnostica();

					 epicrisis_ese.setCie_principal(id.getCie_principal());		
				 epicrisis_ese.setTipo_principal(id.getTipo_principal());
				 epicrisis_ese.setCie_relacionado1(id.getCie_relacionado1());
				 epicrisis_ese.setTipo_relacionado1(id.getTipo_relacionado1());
				 epicrisis_ese.setCie_relacionado2(id.getCie_relacionado2());
				 epicrisis_ese.setTipo_relacionado2(id.getTipo_relacionado2());
				 epicrisis_ese.setCie_relacionado3(id.getCie_relacionado3());
				 epicrisis_ese.setTipo_relacionado3(id.getTipo_relacionado3());

				epicrisis_ese.setIngreso(new Timestamp(dtbxIngreso.getValue()
						.getTime()));
				epicrisis_ese.setEgreso(new Timestamp(dtbxEgreso.getValue()
						.getTime()));
				epicrisis_ese.setEstancia(tbxEstancia.getValue());
				epicrisis_ese.setCausa_salida(tbxCausa_salida.getValue());
				epicrisis_ese.setDestino(lbxDestino.getSelectedItem()
						.getValue().toString());
				epicrisis_ese.setEstado_salida(lbxEstado_salida
						.getSelectedItem().getValue().toString());
				epicrisis_ese.setServicio_ingreso(lbxServicio_ingreso
						.getSelectedItem().getValue().toString());
				epicrisis_ese.setServicio_egreso(lbxServicio_egreso
						.getSelectedItem().getValue().toString());
				epicrisis_ese.setAntecedentes(tbxAntecedentes.getValue());
				epicrisis_ese.setSintesis_enfermedad(tbxSintesis_enfermedad
						.getValue());
				epicrisis_ese
						.setHemograma(chbHemograma.isChecked() ? "S" : "N");
				epicrisis_ese.setN_ureico(chbN_ureico.isChecked() ? "S" : "N");
				epicrisis_ese
						.setC_antibiograma(chbC_antibiograma.isChecked() ? "S"
								: "N");
				epicrisis_ese.setCitoquimico(chbCitoquimico.isChecked() ? "S"
						: "N");
				epicrisis_ese
						.setPatologia(chbPatologia.isChecked() ? "S" : "N");
				epicrisis_ese
						.setV_sedimentacion(chbV_sedimentacion.isChecked() ? "S"
								: "N");
				epicrisis_ese.setCoprologico(chbCoprologico.isChecked() ? "S"
						: "N");
				epicrisis_ese
						.setBilirrubinemia(chbBilirrubinemia.isChecked() ? "S"
								: "N");
				epicrisis_ese
						.setInmunologicos(chbInmunologicos.isChecked() ? "S"
								: "N");
				epicrisis_ese
						.setRx_simple(chbRx_simple.isChecked() ? "S" : "N");
				epicrisis_ese.setH_clasificar(chbH_clasificar.isChecked() ? "S"
						: "N");
				epicrisis_ese.setFalciformia(chbFalciformia.isChecked() ? "S"
						: "N");
				epicrisis_ese
						.setTransaminasas(chbTransaminasas.isChecked() ? "S"
								: "N");
				epicrisis_ese.setVih(chbVih.isChecked() ? "S" : "N");
				epicrisis_ese
						.setRx_contrastada(chbRx_contrastada.isChecked() ? "S"
								: "N");
				epicrisis_ese
						.setP_cruzada(chbP_cruzada.isChecked() ? "S" : "N");
				epicrisis_ese
						.setProtelnograma(chbProtelnograma.isChecked() ? "S"
								: "N");
				epicrisis_ese
						.setP_upidlco(chbP_upidlco.isChecked() ? "S" : "N");
				epicrisis_ese
						.setE_cardiograma(chbE_cardiograma.isChecked() ? "S"
								: "N");
				epicrisis_ese.setRx_invasivos(chbRx_invasivos.isChecked() ? "S"
						: "N");
				epicrisis_ese.setTpt(chbTpt.isChecked() ? "S" : "N");
				epicrisis_ese.setElectroferesis_hb(chbElectroferesis_hb
						.isChecked() ? "S" : "N");
				epicrisis_ese.setExt_sangre(chbExt_sangre.isChecked() ? "S"
						: "N");
				epicrisis_ese
						.setEcocardiograma(chbEcocardiograma.isChecked() ? "S"
								: "N");
				epicrisis_ese.setTac(chbTac.isChecked() ? "S" : "N");
				epicrisis_ese.setT_p(chbT_p.isChecked() ? "S" : "N");
				epicrisis_ese.setVdrl(chbVdrl.isChecked() ? "S" : "N");
				epicrisis_ese
						.setIonograma(chbIonograma.isChecked() ? "S" : "N");
				epicrisis_ese.setDoppler(chbDoppler.isChecked() ? "S" : "N");
				epicrisis_ese.setR_magnetica(chbR_magnetica.isChecked() ? "S"
						: "N");
				epicrisis_ese.setR_plaquetas(chbR_plaquetas.isChecked() ? "S"
						: "N");
				epicrisis_ese.setWidal(chbWidal.isChecked() ? "S" : "N");
				epicrisis_ese.setG_arteriales(chbG_arteriales.isChecked() ? "S"
						: "N");
				epicrisis_ese
						.setE_encefalograma(chbE_encefalograma.isChecked() ? "S"
								: "N");
				epicrisis_ese
						.setEcografia(chbEcografia.isChecked() ? "S" : "N");
				epicrisis_ese.setGlicemia(chbGlicemia.isChecked() ? "S" : "N");
				epicrisis_ese.setAsto(chbAsto.isChecked() ? "S" : "N");
				epicrisis_ese.setEnzimas(chbEnzimas.isChecked() ? "S" : "N");
				epicrisis_ese.setBiopsia(chbBiopsia.isChecked() ? "S" : "N");
				epicrisis_ese.setGamagrafias(chbGamagrafias.isChecked() ? "S"
						: "N");
				epicrisis_ese.setUrianalisis(chbUrianalisis.isChecked() ? "S"
						: "N");
				epicrisis_ese.setProteina_cr(chbProteina_cr.isChecked() ? "S"
						: "N");
				epicrisis_ese.setHormonas(chbHormonas.isChecked() ? "S" : "N");
				epicrisis_ese.setMedulograma(chbMedulograma.isChecked() ? "S"
						: "N");
				epicrisis_ese
						.setBaciloscopias(chbBaciloscopias.isChecked() ? "S"
								: "N");
				epicrisis_ese.setCreatinina(chbCreatinina.isChecked() ? "S"
						: "N");
				epicrisis_ese.setGram(chbGram.isChecked() ? "S" : "N");
				epicrisis_ese
						.setDrogas_sericas(chbDrogas_sericas.isChecked() ? "S"
								: "N");
				epicrisis_ese.setEndoscopia(chbEndoscopia.isChecked() ? "S"
						: "N");
				epicrisis_ese.setOtros(chbOtros.isChecked() ? "S" : "N");
				epicrisis_ese.setOtros_txt(tbxOtros_txt.getValue());
				epicrisis_ese
						.setComentarios_paraclinicos_positivos(tbxComentarios_paraclinicos_positivos
								.getValue());
				epicrisis_ese
						.setCirugia_tratamiento_medico(tbxCirugia_tratamiento_medico
								.getValue());
				epicrisis_ese
						.setInterconsultas_juntas_medicas(tbxInterconsultas_juntas_medicas
								.getValue());
				epicrisis_ese.setComplicaciones(tbxComplicaciones.getValue());
				epicrisis_ese.setRecomendaciones(tbxRecomendaciones.getValue());
				epicrisis_ese.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				epicrisis_ese.setCreacion_user(usuarios.getCodigo().toString());
				// epicrisis_ese.setDelete_date();
				// epicrisis_ese.setDelete_user();
				epicrisis_ese.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				epicrisis_ese.setUltimo_user(usuarios.getCodigo().toString());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					epicrisis_eseService.crear(epicrisis_ese);
//					accionForm(true, "registrar");
					tbxAccion.setValue("modificar");
					((Button) getFellow("btnCancelar")).setVisible(false);
				} else {
					int result = epicrisis_eseService.actualizar(epicrisis_ese);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				Anexo2 anexo2 = new Anexo2();
				anexo2.setCodigo_empresa(codigo_empresa);
				anexo2.setCodigo_sucursal(codigo_sucursal);
				anexo2.setNro_ingreso(admision.getNro_ingreso());
				anexo2.setNro_documento(admision.getNro_identificacion());

				anexo2 = generalExtraService.consultar(anexo2);

				if (anexo2 != null) {
					anexo2.setDestino_paciente(epicrisis_ese.getDestino());
					generalExtraService.actualizar(anexo2);
				}
				
				((Button) getFellow("btnCancelar")).setVisible(true);

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
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

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Epicrisis_ese epicrisis_ese = (Epicrisis_ese) obj;
		try {

			infoPacientes.setCodigo_historia(historia_clinica
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(historia_clinica.getCreacion_date());
			infoPacientes.setFecha_cierre(true,
					historia_clinica.getUltimo_update());

			dtbxIngreso.setValue(epicrisis_ese.getIngreso());
			dtbxEgreso.setValue(epicrisis_ese.getEgreso());
			tbxEstancia.setValue(epicrisis_ese.getEstancia());
			tbxCausa_salida.setValue(epicrisis_ese.getCausa_salida());

			Utilidades.seleccionarListitem(lbxDestino,
					epicrisis_ese.getDestino());
			Utilidades.seleccionarListitem(lbxEstado_salida,
					epicrisis_ese.getEstado_salida());

			Utilidades.seleccionarListitem(lbxServicio_ingreso,
					epicrisis_ese.getServicio_ingreso());
			Utilidades.seleccionarListitem(lbxServicio_egreso,
					epicrisis_ese.getServicio_egreso());

			tbxAntecedentes.setValue(epicrisis_ese.getAntecedentes());
			tbxSintesis_enfermedad.setValue(epicrisis_ese
					.getSintesis_enfermedad());
			chbHemograma
					.setChecked(epicrisis_ese.getHemograma().equals("S") ? true
							: false);
			chbN_ureico
					.setChecked(epicrisis_ese.getN_ureico().equals("S") ? true
							: false);
			chbC_antibiograma.setChecked(epicrisis_ese.getC_antibiograma()
					.equals("S") ? true : false);
			chbCitoquimico.setChecked(epicrisis_ese.getCitoquimico()
					.equals("S") ? true : false);
			chbPatologia
					.setChecked(epicrisis_ese.getPatologia().equals("S") ? true
							: false);
			chbV_sedimentacion.setChecked(epicrisis_ese.getV_sedimentacion()
					.equals("S") ? true : false);
			chbCoprologico.setChecked(epicrisis_ese.getCoprologico()
					.equals("S") ? true : false);
			chbBilirrubinemia.setChecked(epicrisis_ese.getBilirrubinemia()
					.equals("S") ? true : false);
			chbInmunologicos.setChecked(epicrisis_ese.getInmunologicos()
					.equals("S") ? true : false);
			chbRx_simple
					.setChecked(epicrisis_ese.getRx_simple().equals("S") ? true
							: false);
			chbH_clasificar.setChecked(epicrisis_ese.getH_clasificar().equals(
					"S") ? true : false);
			chbFalciformia.setChecked(epicrisis_ese.getFalciformia()
					.equals("S") ? true : false);
			chbTransaminasas.setChecked(epicrisis_ese.getTransaminasas()
					.equals("S") ? true : false);
			chbVih.setChecked(epicrisis_ese.getVih().equals("S") ? true : false);
			chbRx_contrastada.setChecked(epicrisis_ese.getRx_contrastada()
					.equals("S") ? true : false);
			chbP_cruzada
					.setChecked(epicrisis_ese.getP_cruzada().equals("S") ? true
							: false);
			chbProtelnograma.setChecked(epicrisis_ese.getProtelnograma()
					.equals("S") ? true : false);
			chbP_upidlco
					.setChecked(epicrisis_ese.getP_upidlco().equals("S") ? true
							: false);
			chbE_cardiograma.setChecked(epicrisis_ese.getE_cardiograma()
					.equals("S") ? true : false);
			chbRx_invasivos.setChecked(epicrisis_ese.getRx_invasivos().equals(
					"S") ? true : false);
			chbTpt.setChecked(epicrisis_ese.getTpt().equals("S") ? true : false);
			chbElectroferesis_hb.setChecked(epicrisis_ese
					.getElectroferesis_hb().equals("S") ? true : false);
			chbExt_sangre
					.setChecked(epicrisis_ese.getExt_sangre().equals("S") ? true
							: false);
			chbEcocardiograma.setChecked(epicrisis_ese.getEcocardiograma()
					.equals("S") ? true : false);
			chbTac.setChecked(epicrisis_ese.getTac().equals("S") ? true : false);
			chbT_p.setChecked(epicrisis_ese.getT_p().equals("S") ? true : false);
			chbVdrl.setChecked(epicrisis_ese.getVdrl().equals("S") ? true
					: false);
			chbIonograma
					.setChecked(epicrisis_ese.getIonograma().equals("S") ? true
							: false);
			chbDoppler.setChecked(epicrisis_ese.getDoppler().equals("S") ? true
					: false);
			chbR_magnetica.setChecked(epicrisis_ese.getR_magnetica()
					.equals("S") ? true : false);
			chbR_plaquetas.setChecked(epicrisis_ese.getR_plaquetas()
					.equals("S") ? true : false);
			chbWidal.setChecked(epicrisis_ese.getWidal().equals("S") ? true
					: false);
			chbG_arteriales.setChecked(epicrisis_ese.getG_arteriales().equals(
					"S") ? true : false);
			chbE_encefalograma.setChecked(epicrisis_ese.getE_encefalograma()
					.equals("S") ? true : false);
			chbEcografia
					.setChecked(epicrisis_ese.getEcografia().equals("S") ? true
							: false);
			chbGlicemia
					.setChecked(epicrisis_ese.getGlicemia().equals("S") ? true
							: false);
			chbAsto.setChecked(epicrisis_ese.getAsto().equals("S") ? true
					: false);
			chbEnzimas.setChecked(epicrisis_ese.getEnzimas().equals("S") ? true
					: false);
			chbBiopsia.setChecked(epicrisis_ese.getBiopsia().equals("S") ? true
					: false);
			chbGamagrafias.setChecked(epicrisis_ese.getGamagrafias()
					.equals("S") ? true : false);
			chbUrianalisis.setChecked(epicrisis_ese.getUrianalisis()
					.equals("S") ? true : false);
			chbProteina_cr.setChecked(epicrisis_ese.getProteina_cr()
					.equals("S") ? true : false);
			chbHormonas
					.setChecked(epicrisis_ese.getHormonas().equals("S") ? true
							: false);
			chbMedulograma.setChecked(epicrisis_ese.getMedulograma()
					.equals("S") ? true : false);
			chbBaciloscopias.setChecked(epicrisis_ese.getBaciloscopias()
					.equals("S") ? true : false);
			chbCreatinina
					.setChecked(epicrisis_ese.getCreatinina().equals("S") ? true
							: false);
			chbGram.setChecked(epicrisis_ese.getGram().equals("S") ? true
					: false);
			chbDrogas_sericas.setChecked(epicrisis_ese.getDrogas_sericas()
					.equals("S") ? true : false);
			chbEndoscopia
					.setChecked(epicrisis_ese.getEndoscopia().equals("S") ? true
							: false);
			chbOtros.setChecked(epicrisis_ese.getOtros().equals("S") ? true
					: false);
			rowOtros1.setVisible(chbOtros.isChecked());
			rowOtros2.setVisible(chbOtros.isChecked());
			tbxOtros_txt.setValue(epicrisis_ese.getOtros_txt());

			tbxComentarios_paraclinicos_positivos.setValue(epicrisis_ese
					.getComentarios_paraclinicos_positivos());
			tbxCirugia_tratamiento_medico.setValue(epicrisis_ese
					.getCirugia_tratamiento_medico());
			tbxInterconsultas_juntas_medicas.setValue(epicrisis_ese
					.getInterconsultas_juntas_medicas());
			tbxComplicaciones.setValue(epicrisis_ese.getComplicaciones());
			tbxRecomendaciones.setValue(epicrisis_ese.getRecomendaciones());

			Impresion_diagnostica id = new Impresion_diagnostica();
			id.setCie_principal(epicrisis_ese.getCie_principal());
			id.setTipo_principal(epicrisis_ese.getTipo_principal());
			id.setCie_relacionado1(epicrisis_ese.getCie_relacionado1());
			id.setTipo_relacionado1(epicrisis_ese.getTipo_relacionado1());
			id.setCie_relacionado2(epicrisis_ese.getCie_relacionado2());
			id.setTipo_relacionado2(epicrisis_ese.getTipo_relacionado2());
			id.setCie_relacionado3(epicrisis_ese.getCie_relacionado3());
			id.setTipo_relacionado3(epicrisis_ese.getTipo_relacionado3());
			id.setFinalidad_consulta("10");
			 macroImpresion_diagnosticaEpicrisis.mostrarImpresionDiagnostica(id,
			 true);
			// Mostramos la vista //
			tbxAccion.setText("modificar");
				
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Epicrisis_ese epicrisis_ese = (Epicrisis_ese) obj;
		try {
			int result = epicrisis_eseService.eliminar(epicrisis_ese);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente!!",
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

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {
			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Epicrisis_ese epic = new Epicrisis_ese();
				epic.setCodigo_empresa(empresa.getCodigo_empresa());
				epic.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				epic.setCodigo_historia(historia_clinica.getCodigo_historia());
				epic = getServiceLocator().getServicio(
						Epicrisis_eseService.class).consultar(epic);
				if (epic != null) {
					mostrarDatos(epic);
				}
			}
		}
	}

	@Override
	public void inicializarVista(String tipo) {
		

	}

	@Override
	public void cargarInformacion_paciente() {

		mcSignosVitales.setFecha_nacimiento(admision.getPaciente()
				.getFecha_nacimiento());
		mcSignosVitales.setGenero(admision.getPaciente().getSexo());
		mcSignosVitales.inicializarParametrosAlertas();

		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {
					@Override
					public void ejecutarProceso() {
					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior)
			throws Exception {
		

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			((Button) getFellow("btGuardar")).setVisible(true);
		}
		
	}

	private void cargarImpresionDiagnostica(Hisc_urgencia hisc)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(hisc.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}
	
	private void cargarImpresionDiagnostica2(Hisc_urgencia hisc)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(hisc.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnosticaEpicrisis.mostrarImpresionDiagnostica2(
				impresion_diagnostica, true);
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
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
		}
	}

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}
	
	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(this, sw, new String[] { "northEditar" });
		if (!sw) {
			((Button) getFellow("btGuardar")).setDisabled(false);
			((Button) getFellow("btGuardar")).setImage("/images/Save16.gif");
		} else {
			((Button) getFellow("btGuardar")).setDisabled(true);
			((Button) getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");
		}
	}
	
	public void imprimir() throws Exception {
		Long nro_historia = infoPacientes.getCodigo_historia();
		
		Epicrisis_ese epicrisis_ese = new Epicrisis_ese();
		epicrisis_ese.setCodigo_empresa(admision.getCodigo_empresa());
		epicrisis_ese.setCodigo_sucursal(admision.getCodigo_sucursal());
		epicrisis_ese.setCodigo_historia(nro_historia);
		epicrisis_ese = getServiceLocator().getServicio(Epicrisis_eseService.class).consultar(epicrisis_ese);
		
		if(epicrisis_ese == null) {
			Messagebox.show("La Epicrisis no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Epicrisis_ese");
		paramRequest.put("nro_historia", nro_historia);
		paramRequest.put("formato", "pdf");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}
	
	
	
}