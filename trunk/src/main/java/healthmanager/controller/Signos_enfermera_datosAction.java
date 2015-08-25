/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Parametros_signos;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Parametros_signosService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;
import com.framework.util.Utilidades;

/**
 * @author ferney
 * 
 */
public class Signos_enfermera_datosAction extends ZKWindow {
	private static Logger log = Logger
			.getLogger(Signos_enfermera_datosAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private Admision admision_seleccionada;
	private Enfermera_signos enfermera_signos_seleccionada;

	@View
	private Groupbox gbxExamen_fisico1;
	@View
	private Groupbox gbxExamen_fisico2;
	@View
	private Groupbox gbxExamen_fisico3;
	@View
	private Groupbox gbxExamen_fisico4;
	@View
	private Groupbox gbxExamen_fisico5;
	@View
	private Groupbox gbxExamen_fisico6;
	@View
	private Groupbox gbxExamen_fisico7;
	@View
	private Groupbox gbxExamen_fisico8;
	@View
	private Groupbox gbxExamen_fisico9;
	@View
	private Groupbox gbxExamen_fisico10;
	@View
	private Groupbox gbxExamen_fisico11;
	@View
	private Groupbox gbxExamen_fisico12;
	@View
	private Groupbox gbxExamen_fisico13;

	@View
	private Textbox tbxAccion;

	// componentes del examen fisico numero 6 (consulta externa)
	@View
	private Doublebox mcDbxFrecuencia_cardiaca;
	@View
	private Doublebox mcDbxFrecuencia_respiratoria;
	@View
	private Doublebox mcDbxTemparatura;
	@View
	private Doublebox mcDbxTA_sistolica;
	@View
	private Doublebox mcDbxTA_diastolica;
	@View
	private Doublebox mcDbxPeso;
	@View
	private Doublebox mcDbxTalla;
	@View
	private Doublebox mcDbxIMC;
	@View
	private Doublebox mcDbxSuperficie_corporal;
	@View
	private Doublebox mcDbxTA_media;
	@View
	private Doublebox mcDbxCreatinina_serica;
	@View
	private Doublebox mcDbxTFG;
	@View
	private Doublebox mcDbxPerimetro_cefalico;
	@View
	private Doublebox mcDbxPerimetro_toraxico;
	@View
	private Label mcLabelPerimetro_cefalico;
	@View
	private Label mcLabelPerimetro_toraxico;

	private Parametros_signos parametros_signos;

	private String CONFIG_TALLA = "C";

	private String alerta_imc = "";

	// componentes del examen fisico numero 7
	@View
	private Doublebox dbxPresion;
	@View
	private Doublebox dbxPresion27;
	@View
	private Doublebox dbxTalla;
	@View
	private Doublebox dbxPeso;

	// componentes del examen físico Número 1

	@View
	private Doublebox dbxExamen_fisico_peso;
	@View
	private Doublebox dbxExamen_fisico_talla;
	@View
	private Doublebox dbxExamen_fisico_perimetro_cflico;
	@View
	private Doublebox dbxExamen_fisico_fc;
	@View
	private Doublebox dbxExamen_fisico_fr;
	@View
	private Doublebox dbxExamen_fisico_temp;

	// componente del examen fisico Número 2
	@View
	private Doublebox dbxExamen_fisico_peso1;
	@View
	private Doublebox dbxExamen_fisico_talla1;
	@View
	private Doublebox dbxExamen_fisico_perimetro_cflico1;
	@View
	private Doublebox dbxImc1;
	@View
	private Doublebox dbxExamen_fisico_fc1;
	@View
	private Doublebox dbxExamen_fisico_fr1;
	@View
	private Doublebox dbxExamen_fisico_temp1;
	
	
	// Componente del Examen Fisico 5 - Psiquiatria, Alteracion Joven, Planificacion Familiar
	
	@View
	private Doublebox dbxCardiaca5;
	@View
	private Doublebox dbxRespiratoria5;
	@View
	private Doublebox dbxPeso5;
	@View
	private Doublebox dbxTalla5;
	@View
	private Doublebox dbxPresion5;
	@View
	private Doublebox dbxPresion25;
	@View
	private Doublebox dbxImc5;
	
	// Componente del Examen Fisico 8 - Adulto Mayor
	
	@View
	private Doublebox dbxSentado8;
	@View
	private Doublebox dbxSentado28;
	@View
	private Doublebox dbxAcostado8;
	@View
	private Doublebox dbxAcostado28;
	@View
	private Doublebox dbxPie8;
	@View
	private Doublebox dbxPie28;
	
	@View
	private Doublebox dbxCardiaca8;
	@View
	private Doublebox dbxPeso8;
	@View
	private Doublebox dbxTalla8;
	@View
	private Doublebox dbxImc8;
	
	@View
	private Doublebox dbxCir_adbominal8;
	@View
	private Doublebox dbxCir_cadera8;
	@View
	private Doublebox dbxInd_cintura8;
	

	// Componente del Examen Fisico 4 - Embarazo
	
		@View
		private Doublebox dbxCardiaca4;
		@View
		private Doublebox dbxRespiratoria4;
		@View
		private Doublebox dbxPeso4;
		@View
		private Doublebox dbxTalla4;
		@View
		private Doublebox dbxPresion4;
		@View
		private Doublebox dbxPresion24;
		@View
		private Doublebox dbxImc4;
		@View
		private Doublebox dbxTemperatura4;
		@View
		private Doublebox dbxPesoInicial4;
		@View
		private Doublebox dbxFc_fetal4;
		
	// Componente del Examen Fisico 9 - de 5 a 10 años
		
		@View
		private Doublebox dbxPeso_grs9;
		@View
		private Doublebox dbxTalla_cm9;
		@View
		private Doublebox dbxImc9;
		@View
		private Doublebox dbxFc_min9;
		@View
		private Doublebox dbxFr_min9;
		@View
		private Doublebox dbxTemperatura_gc9;
		
	// Componente del Examen Fisico 10 - HIPERTENSO
		
		@View
		private Doublebox dbxPeso10;
		@View
		private Doublebox dbxTalla10;
		@View
		private Doublebox dbxImc10;
		@View
		private Doublebox dbxSup_corporal10;
		@View
		private Doublebox dbxC_abdominal10;
		@View
		private Doublebox dbxFc_lat_min10;
		@View
		private Doublebox dbxFp_pul_min10;
		@View
		private Doublebox dbxFr_resp_min10;
		@View
		private Doublebox dbxTemp_c10;
		
		@View
		private Doublebox dbxTa_sentado_bd10;
		@View
		private Doublebox dbxTa_sentado_mmhg10;
		@View
		private Doublebox dbxTa_sentado_bi10;
		@View
		private Doublebox dbxTa_sentado_mmhg210;
		@View
		private Doublebox dbxTa_decubito10;
		@View
		private Doublebox dbxTa_decubito_mmhg10;
		@View
		private Doublebox dbxTa_pie10;
		@View
		private Doublebox dbxTa_pie_mmhg10;
		
	// Componente del Examen Fisico 11 - AIEPI 1
		
		@View
		private Doublebox dbxSignos_vitales_fc11;
		@View
		private Doublebox dbxSignos_vitales_fr11;
		@View
		private Doublebox dbxSignos_vitales_peso11;
		@View
		private Doublebox dbxSignos_vitales_talla11;
		@View
		private Doublebox dbxSignos_vitales_imc11;
		@View
		private Doublebox dbxSignos_vitales_pc11;
		@View
		private Doublebox dbxSignos_vitales_taxilar11;
		@View
		private Doublebox dbxSignos_vitales_oximetria11;
		
		private int edad;

		private int meses;
		
		// componentes del examen fisico numero 12 (urgencia)
		@View
		private Doublebox mcDbxFrecuencia_cardiaca12;
		@View
		private Doublebox mcDbxFrecuencia_respiratoria12;
		@View
		private Doublebox mcDbxTemparatura12;
		@View
		private Doublebox mcDbxTA_sistolica12;
		@View
		private Doublebox mcDbxTA_diastolica12;
		@View
		private Doublebox mcDbxPeso12;
		@View
		private Doublebox mcDbxTalla12;
		@View
		private Doublebox mcDbxIMC12;
		@View
		private Doublebox mcDbxSuperficie_corporal12;
		@View
		private Doublebox mcDbxTA_media12;
		
		// componentes del examen fisico numero 13 (urgencia )
		@View
		private Doublebox mcDbxFrecuencia_cardiaca13;
		@View
		private Doublebox mcDbxFrecuencia_respiratoria13;
		@View
		private Doublebox mcDbxTemparatura13;
		@View
		private Doublebox mcDbxTA_sistolica13;
		@View
		private Doublebox mcDbxTA_diastolica13;
		@View
		private Doublebox mcDbxPeso13;
		@View
		private Doublebox mcDbxTalla13;
		@View
		private Doublebox mcDbxIMC13;
		@View
		private Doublebox mcDbxSuperficie_corporal13;
		@View
		private Doublebox mcDbxTA_media13;
		@View
		private Doublebox mcDbxPerimetro_cefalico13;
		@View
		private Doublebox mcDbxPerimetro_toraxico13;
		@View
		private Label mcLabelPerimetro_cefalico13;
		@View
		private Label mcLabelPerimetro_toraxico13;
		
		
	@Override
	public void params(Map<String, Object> map) {
		admision_seleccionada = (Admision) map
				.get(IVias_ingreso.ADMISION_PACIENTE);
		enfermera_signos_seleccionada = (Enfermera_signos) map
				.get(IVias_ingreso.ENFERMERA_SIGNOS_PACIENTE);
		edad = Integer.valueOf(Util.getEdad(new java.text.SimpleDateFormat(
				"dd/MM/yyyy").format(admision_seleccionada.getPaciente()
				.getFecha_nacimiento()), "1", false));
		meses = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision_seleccionada.getPaciente()
						.getFecha_nacimiento()),"2", false));
		dbxExamen_fisico_perimetro_cflico1.setDisabled(!(edad < 4));
	}

	@Override
	public void init() throws Exception {
		inicializarVista();
		if (enfermera_signos_seleccionada != null) {
			mostrarDatos(enfermera_signos_seleccionada);
		} else {
			limpiarVista();
		}
		inicializarParametrosAlertas();
	}

	public void inicializarVista() {
		if (admision_seleccionada != null) {
			String via_ingreso = admision_seleccionada.getVia_ingreso();
			//log.info("admision_seleccionada.getVia_ingreso() -> "+admision_seleccionada.getVia_ingreso());
			if (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA) || via_ingreso.equals(IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
				
				//log.info("meses"+meses);
				
				if(meses >= 0 && meses<= 60){
										
					gbxExamen_fisico1.setVisible(false);
					gbxExamen_fisico2.setVisible(false);
					gbxExamen_fisico3.setVisible(false);
					gbxExamen_fisico4.setVisible(false);
					gbxExamen_fisico5.setVisible(false);
					gbxExamen_fisico6.setVisible(false);
					gbxExamen_fisico7.setVisible(false);
					gbxExamen_fisico8.setVisible(false);
					gbxExamen_fisico9.setVisible(false);
					gbxExamen_fisico10.setVisible(false);
					gbxExamen_fisico11.setVisible(true);
					gbxExamen_fisico12.setVisible(false);
					gbxExamen_fisico13.setVisible(false);
					
				}else if(meses > 60){
					
					gbxExamen_fisico1.setVisible(false);
					gbxExamen_fisico2.setVisible(false);
					gbxExamen_fisico3.setVisible(false);
					gbxExamen_fisico4.setVisible(false);
					gbxExamen_fisico5.setVisible(false);
					gbxExamen_fisico6.setVisible(true);
					gbxExamen_fisico7.setVisible(false);
					gbxExamen_fisico8.setVisible(false);
					gbxExamen_fisico9.setVisible(false);
					gbxExamen_fisico10.setVisible(false);
					gbxExamen_fisico11.setVisible(false);
					gbxExamen_fisico12.setVisible(false);
					gbxExamen_fisico13.setVisible(false);
					
				}
			}else if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
				
				//log.info("meses"+meses);
				
				if(meses >= 0 && meses<= 60){
										
					gbxExamen_fisico1.setVisible(false);
					gbxExamen_fisico2.setVisible(false);
					gbxExamen_fisico3.setVisible(false);
					gbxExamen_fisico4.setVisible(false);
					gbxExamen_fisico5.setVisible(false);
					gbxExamen_fisico6.setVisible(false);
					gbxExamen_fisico7.setVisible(false);
					gbxExamen_fisico8.setVisible(false);
					gbxExamen_fisico9.setVisible(false);
					gbxExamen_fisico10.setVisible(false);
					gbxExamen_fisico11.setVisible(false);
					gbxExamen_fisico12.setVisible(false);
					gbxExamen_fisico13.setVisible(true);
					
				}else if(meses > 60){
					
					gbxExamen_fisico1.setVisible(false);
					gbxExamen_fisico2.setVisible(false);
					gbxExamen_fisico3.setVisible(false);
					gbxExamen_fisico4.setVisible(false);
					gbxExamen_fisico5.setVisible(false);
					gbxExamen_fisico6.setVisible(false);
					gbxExamen_fisico7.setVisible(false);
					gbxExamen_fisico8.setVisible(false);
					gbxExamen_fisico9.setVisible(false);
					gbxExamen_fisico10.setVisible(false);
					gbxExamen_fisico11.setVisible(false);
					gbxExamen_fisico12.setVisible(true);
					gbxExamen_fisico13.setVisible(false);
					
				}
			}else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.SALUD_ORAL, IVias_ingreso.ODONTOLOGIA2)) {
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(false);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(true);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);
			} else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.HC_MENOR_2_MESES,
					IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)) {
				gbxExamen_fisico1.setVisible(true);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(false);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);
			} else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)) {
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(true);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(false);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);

			} else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)) {
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(false);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(true);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);

			} else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.ALTERACION_JOVEN)) {
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(true);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);

			} else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.ADULTO_MAYOR)) {
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(false);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(true);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);

			} else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)) {
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(true);
				gbxExamen_fisico5.setVisible(false);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);

			} else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.PLANIFICACION_FAMILIAR)) {
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(true);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);

			} else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.HIPERTENSO_DIABETICO)) {
				//log.info("ok");
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(false);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(true);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);

			} else if (Utilidades.igualConjuncion(via_ingreso,
					IVias_ingreso.PSIQUIATRIA)) {
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(true);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);

			} else {
				gbxExamen_fisico1.setVisible(false);
				gbxExamen_fisico2.setVisible(false);
				gbxExamen_fisico3.setVisible(false);
				gbxExamen_fisico4.setVisible(false);
				gbxExamen_fisico5.setVisible(false);
				gbxExamen_fisico6.setVisible(false);
				gbxExamen_fisico7.setVisible(false);
				gbxExamen_fisico8.setVisible(false);
				gbxExamen_fisico9.setVisible(false);
				gbxExamen_fisico10.setVisible(false);
				gbxExamen_fisico11.setVisible(false);
				gbxExamen_fisico12.setVisible(false);
				gbxExamen_fisico13.setVisible(false);
			}

			validarFechaNacimiento(admision_seleccionada.getPaciente()
					.getFecha_nacimiento());

		}
	}

	public void limpiarVista() {
		if (admision_seleccionada != null) {
			String via_ingreso = admision_seleccionada.getVia_ingreso();
			
			if (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA) || via_ingreso.equals(IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
				
				if(meses >= 0 && meses<= 60){
					FormularioUtil.limpiarComponentes(gbxExamen_fisico11,new String[] {});
				}else{
					FormularioUtil.limpiarComponentes(gbxExamen_fisico6,new String[] {});	
				}
				
			}else if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
				
				if(meses >= 0 && meses<= 60){
					FormularioUtil.limpiarComponentes(gbxExamen_fisico13,new String[] {});
				}else{
					FormularioUtil.limpiarComponentes(gbxExamen_fisico12,new String[] {});	
				}
				
			} else if (via_ingreso.equals(IVias_ingreso.HC_MENOR_2_MESES)
					|| via_ingreso.equals(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico1,
						new String[] {});
			} else if (via_ingreso.equals(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico2,
						new String[] {});
			} else if (via_ingreso.equals(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico9,
						new String[] {});
			} else if (via_ingreso.equals(IVias_ingreso.ALTERACION_JOVEN)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico5,
						new String[] {});
			} else if (via_ingreso.equals(IVias_ingreso.ADULTO_MAYOR)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico8,
						new String[] {});
			} else if (via_ingreso.equals(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico4,
						new String[] {});
			} else if (via_ingreso.equals(IVias_ingreso.PLANIFICACION_FAMILIAR)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico5,
						new String[] {});
			} else if (via_ingreso.equals(IVias_ingreso.HIPERTENSO_DIABETICO)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico10,
						new String[] {});
			} else if (via_ingreso.equals(IVias_ingreso.HC_AIEPI_2_MESES)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico11,
						new String[] {});
			} else if (via_ingreso.equals(IVias_ingreso.PSIQUIATRIA)) {
				FormularioUtil.limpiarComponentes(gbxExamen_fisico5,
						new String[] {});
			}

		}
	}

	public void mostrarDatos(Enfermera_signos enfermera_signos) {
		if (enfermera_signos.getVia_ingreso().equals(
				IVias_ingreso.CONSULTA_EXTERNA) || enfermera_signos.getVia_ingreso().equals(
						IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
			
			if(meses >= 0 && meses<= 60){
				mostrarDatosTipo11(enfermera_signos);
			}else {
				mostrarDatosTipo6(enfermera_signos);
			}
			
		}else if (enfermera_signos.getVia_ingreso().equals(
				IVias_ingreso.URGENCIA)) {
			
			if(meses >= 0 && meses<= 60){
				mostrarDatosTipo13(enfermera_signos);
			}else {
				mostrarDatosTipo12(enfermera_signos);
			}
			
		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(), IVias_ingreso.SALUD_ORAL,
				IVias_ingreso.ODONTOLOGIA2)) {
			mostrarDatosTipo7(enfermera_signos);
		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.HC_MENOR_2_MESES,
				IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)) {
			mostrarDatosTipo1(enfermera_signos);
		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)) {
			mostrarDatosTipo2(enfermera_signos);

		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)) {
			mostrarDatosTipo9(enfermera_signos);

		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.ALTERACION_JOVEN)) {
			mostrarDatosTipo5(enfermera_signos);

		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.ADULTO_MAYOR)) {
			mostrarDatosTipo8(enfermera_signos);

		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)) {
			mostrarDatosTipo4(enfermera_signos);

		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.PLANIFICACION_FAMILIAR)) {
			mostrarDatosTipo5(enfermera_signos);

		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.HIPERTENSO_DIABETICO)) {
			mostrarDatosTipo10(enfermera_signos);

		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.HC_AIEPI_2_MESES)) {
			mostrarDatosTipo11(enfermera_signos);

		} else if (Utilidades.igualConjuncion(
				enfermera_signos.getVia_ingreso(),
				IVias_ingreso.PSIQUIATRIA)) {
			mostrarDatosTipo5(enfermera_signos);

		}

	}

	public void mostrarDatosTipo1(Enfermera_signos enfermera_signos) {
		dbxExamen_fisico_peso.setValue(enfermera_signos.getPeso());
		dbxExamen_fisico_talla.setValue(enfermera_signos.getTalla());
		dbxExamen_fisico_perimetro_cflico.setValue(enfermera_signos
				.getPerimetro_cefalico());
		dbxExamen_fisico_fc.setValue(enfermera_signos.getFrecuencia_cardiaca());
		dbxExamen_fisico_fr.setValue(enfermera_signos
				.getFrecuencia_respiratoria());
		dbxExamen_fisico_temp.setValue(enfermera_signos.getTemperatura());
	}

	public void mostrarDatosTipo2(Enfermera_signos enfermera_signos) {
		dbxExamen_fisico_peso1.setValue(enfermera_signos.getPeso());
		dbxExamen_fisico_talla1.setValue(enfermera_signos.getTalla());
		dbxExamen_fisico_perimetro_cflico1.setValue(enfermera_signos
				.getPerimetro_cefalico());
		dbxExamen_fisico_fc1
				.setValue(enfermera_signos.getFrecuencia_cardiaca());
		dbxExamen_fisico_fr1.setValue(enfermera_signos
				.getFrecuencia_respiratoria());
		dbxExamen_fisico_temp1.setValue(enfermera_signos.getTemperatura());
		dbxImc1.setValue(enfermera_signos.getImc());

	}

	public void mostrarDatosTipo4(Enfermera_signos enfermera_signos) {
		dbxCardiaca4.setValue(enfermera_signos.getFrecuencia_cardiaca());
		dbxRespiratoria4.setValue(enfermera_signos.getFrecuencia_respiratoria());
		dbxPeso4.setValue(enfermera_signos.getPeso());
		dbxTalla4.setValue(enfermera_signos.getTalla());
		dbxPresion4.setValue(enfermera_signos.getTa_sistolica());
		dbxPresion24.setValue(enfermera_signos.getTa_diastolica());
		dbxImc4.setValue(enfermera_signos.getImc());
		dbxTemperatura4.setValue(enfermera_signos.getTemperatura());
		dbxPesoInicial4.setValue(enfermera_signos.getPeso_inicial());
		dbxFc_fetal4.setValue(enfermera_signos.getFc_fetal());
		
	}
	
	public void mostrarDatosTipo5(Enfermera_signos enfermera_signos) {
		dbxCardiaca5.setValue(enfermera_signos.getFrecuencia_cardiaca());
		dbxRespiratoria5.setValue(enfermera_signos.getFrecuencia_respiratoria());
		dbxPeso5.setValue(enfermera_signos.getPeso());
		dbxTalla5.setValue(enfermera_signos.getTalla());
		dbxPresion5.setValue(enfermera_signos.getTa_sistolica());
		dbxPresion25.setValue(enfermera_signos.getTa_diastolica());
		dbxImc5.setValue(enfermera_signos.getImc());
	}
	
	
	public void mostrarDatosTipo6(Enfermera_signos enfermera_signos) {
		mcDbxFrecuencia_cardiaca.setValue(enfermera_signos
				.getFrecuencia_cardiaca());
		mcDbxFrecuencia_respiratoria.setValue(enfermera_signos
				.getFrecuencia_respiratoria());
		mcDbxTemparatura.setValue(enfermera_signos.getTemperatura());
		mcDbxTA_sistolica.setValue(enfermera_signos.getTa_sistolica());
		mcDbxTA_diastolica.setValue(enfermera_signos.getTa_diastolica());
		mcDbxPeso.setValue(enfermera_signos.getPeso());
		mcDbxTalla.setValue(enfermera_signos.getTalla());
		mcDbxIMC.setValue(enfermera_signos.getImc());
		mcDbxSuperficie_corporal.setValue(enfermera_signos
				.getSuperficie_corporal());
		mcDbxTA_media.setValue(enfermera_signos.getTa_media());
		mcDbxCreatinina_serica
				.setValue(enfermera_signos.getCreatinina_serica());
		mcDbxTFG.setValue(enfermera_signos.getTfg());
		mcDbxPerimetro_cefalico.setValue(enfermera_signos
				.getPerimetro_cefalico());
		mcDbxPerimetro_toraxico.setValue(enfermera_signos
				.getPerimetro_toraxico());
	}
	
	public void mostrarDatosTipo12(Enfermera_signos enfermera_signos) {
		mcDbxFrecuencia_cardiaca12.setValue(enfermera_signos
				.getFrecuencia_cardiaca());
		mcDbxFrecuencia_respiratoria12.setValue(enfermera_signos
				.getFrecuencia_respiratoria());
		mcDbxTemparatura12.setValue(enfermera_signos.getTemperatura());
		mcDbxTA_sistolica12.setValue(enfermera_signos.getTa_sistolica());
		mcDbxTA_diastolica12.setValue(enfermera_signos.getTa_diastolica());
		mcDbxPeso12.setValue(enfermera_signos.getPeso());
		mcDbxTalla12.setValue(enfermera_signos.getTalla());
		mcDbxIMC12.setValue(enfermera_signos.getImc());
		mcDbxSuperficie_corporal12.setValue(enfermera_signos
				.getSuperficie_corporal());
		mcDbxTA_media12.setValue(enfermera_signos.getTa_media());
	}
	
	public void mostrarDatosTipo13(Enfermera_signos enfermera_signos) {
		mcDbxFrecuencia_cardiaca13.setValue(enfermera_signos
				.getFrecuencia_cardiaca());
		mcDbxFrecuencia_respiratoria13.setValue(enfermera_signos
				.getFrecuencia_respiratoria());
		mcDbxTemparatura13.setValue(enfermera_signos.getTemperatura());
		mcDbxTA_sistolica13.setValue(enfermera_signos.getTa_sistolica());
		mcDbxTA_diastolica13.setValue(enfermera_signos.getTa_diastolica());
		mcDbxPeso13.setValue(enfermera_signos.getPeso());
		mcDbxTalla13.setValue(enfermera_signos.getTalla());
		mcDbxIMC13.setValue(enfermera_signos.getImc());
		mcDbxSuperficie_corporal13.setValue(enfermera_signos
				.getSuperficie_corporal());
		mcDbxTA_media13.setValue(enfermera_signos.getTa_media());
		mcDbxPerimetro_cefalico13.setValue(enfermera_signos
				.getPerimetro_cefalico());
		mcDbxPerimetro_toraxico13.setValue(enfermera_signos
				.getPerimetro_toraxico());
	}

	public void mostrarDatosTipo7(Enfermera_signos enfermera_signos) {
		dbxPresion.setValue(enfermera_signos.getTa_sistolica());
		dbxPresion27.setValue(enfermera_signos.getTa_diastolica());
		dbxPeso.setValue(enfermera_signos.getPeso());
		dbxTalla.setValue(enfermera_signos.getTalla());
	}
	
	public void mostrarDatosTipo8(Enfermera_signos enfermera_signos) {
				
		dbxSentado8.setValue(enfermera_signos.getSentado_bd1());
		dbxSentado28.setValue(enfermera_signos.getSentado_bd2());
		dbxAcostado8.setValue(enfermera_signos.getDe_cubito1());
		dbxAcostado28.setValue(enfermera_signos.getDe_cubito2());
		dbxPie8.setValue(enfermera_signos.getDe_pie1());
		dbxPie28.setValue(enfermera_signos.getDe_pie2());
		
		dbxCardiaca8.setValue(enfermera_signos.getFrecuencia_cardiaca());
		dbxPeso8.setValue(enfermera_signos.getPeso());
		dbxTalla8.setValue(enfermera_signos.getTalla());
		dbxImc8.setValue(enfermera_signos.getImc());
		
		dbxCir_adbominal8.setValue(enfermera_signos.getCircunferencia_abdominal());
		dbxCir_cadera8.setValue(enfermera_signos.getCincunferencia_cadera());
		dbxInd_cintura8.setValue(enfermera_signos.getInd_cintura_cadera());
	}
	
	public void mostrarDatosTipo9(Enfermera_signos enfermera_signos) {
				
		dbxPeso_grs9.setValue(enfermera_signos.getPeso());
		dbxTalla_cm9.setValue(enfermera_signos.getTalla());
		dbxImc9.setValue(enfermera_signos.getImc());
		dbxFc_min9.setValue(enfermera_signos.getFrecuencia_cardiaca());
		dbxFr_min9.setValue(enfermera_signos.getFrecuencia_respiratoria());
		dbxTemperatura_gc9.setValue(enfermera_signos.getTemperatura());
		
	}

	public void mostrarDatosTipo10(Enfermera_signos enfermera_signos) {
		dbxPeso10.setValue(enfermera_signos.getPeso());
		dbxTalla10.setValue(enfermera_signos.getTalla());
		dbxImc10.setValue(enfermera_signos.getImc());
		dbxSup_corporal10.setValue(enfermera_signos.getSuperficie_corporal());
		dbxC_abdominal10.setValue(enfermera_signos.getCircunferencia_abdominal());
		dbxFc_lat_min10.setValue(enfermera_signos.getFrecuencia_cardiaca());
		dbxFp_pul_min10.setValue(enfermera_signos.getFrecuencia_pulmonar());
		dbxFr_resp_min10.setValue(enfermera_signos.getFrecuencia_respiratoria());
		dbxTemp_c10.setValue(enfermera_signos.getTemperatura());
		
		dbxTa_sentado_bd10.setValue(enfermera_signos.getSentado_bd1());
		dbxTa_sentado_mmhg10.setValue(enfermera_signos.getSentado_bd2());
		dbxTa_sentado_bi10.setValue(enfermera_signos.getSentado_bi1());
		dbxTa_sentado_mmhg210.setValue(enfermera_signos.getSentado_bi1());
		dbxTa_decubito10.setValue(enfermera_signos.getDe_cubito1());
		dbxTa_decubito_mmhg10.setValue(enfermera_signos.getDe_cubito2());
		dbxTa_pie10.setValue(enfermera_signos.getDe_pie1());
		dbxTa_pie_mmhg10.setValue(enfermera_signos.getDe_pie2());
	}
	
	public void mostrarDatosTipo11(Enfermera_signos enfermera_signos) {
		dbxSignos_vitales_peso11.setValue(enfermera_signos.getPeso());
		dbxSignos_vitales_talla11.setValue(enfermera_signos.getTalla());
		dbxSignos_vitales_imc11.setValue(enfermera_signos.getImc());
		dbxSignos_vitales_pc11.setValue(enfermera_signos.getPerimetro_cefalico());
		dbxSignos_vitales_fc11.setValue(enfermera_signos.getFrecuencia_cardiaca());
		dbxSignos_vitales_fr11.setValue(enfermera_signos.getFrecuencia_respiratoria());
		dbxSignos_vitales_taxilar11.setValue(enfermera_signos.getTemperatura());
		dbxSignos_vitales_oximetria11.setValue(enfermera_signos.getOximetria());
	}

	public void guardarDatos() {
		//log.info("ejecutando metodo @guardarDatos()");
		try {
			if (admision_seleccionada != null) {
				Enfermera_signos enfermera_signos = new Enfermera_signos();
				if (admision_seleccionada.getVia_ingreso().equals(
						IVias_ingreso.CONSULTA_EXTERNA)
						|| admision_seleccionada.getVia_ingreso().equals(
								IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
					
					if(meses >= 0 && meses<= 60){
						enfermera_signos = getObjetoTipo11();
					}else{
						enfermera_signos = getObjetoTipo6();
					}
					
				}else if (admision_seleccionada.getVia_ingreso().equals(
						IVias_ingreso.URGENCIA)) {
					
					if(meses >= 0 && meses<= 60){
						enfermera_signos = getObjetoTipo13();
					}else{
						enfermera_signos = getObjetoTipo12();
					}
					
				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.SALUD_ORAL, IVias_ingreso.ODONTOLOGIA2)) {
					enfermera_signos = getObjetoTipo7();
				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.HC_MENOR_2_MESES,
						IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)) {
					enfermera_signos = getObjetoTipo1();

				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)) {
					enfermera_signos = getObjetoTipo2();

				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)) {
					enfermera_signos = getObjetoTipo9();

				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.ALTERACION_JOVEN)) {
					enfermera_signos = getObjetoTipo5();

				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.ADULTO_MAYOR)) {
					enfermera_signos = getObjetoTipo8();

				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)) {
					enfermera_signos = getObjetoTipo4();

				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.PLANIFICACION_FAMILIAR)) {
					enfermera_signos = getObjetoTipo5();

				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.HIPERTENSO_DIABETICO)) {
					enfermera_signos = getObjetoTipo10();

				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.HC_AIEPI_2_MESES)) {
					enfermera_signos = getObjetoTipo11();

				} else if (Utilidades.igualConjuncion(
						admision_seleccionada.getVia_ingreso(),
						IVias_ingreso.PSIQUIATRIA)) {
					enfermera_signos = getObjetoTipo5();

				}

				enfermera_signos.setCodigo_empresa(codigo_empresa);
				enfermera_signos.setCodigo_sucursal(codigo_sucursal);
				enfermera_signos.setNro_ingreso(admision_seleccionada
						.getNro_ingreso());
				enfermera_signos.setNro_identificacion(admision_seleccionada
						.getNro_identificacion());
				enfermera_signos.setFecha_signos(new Timestamp(new Date()
						.getTime()));
				enfermera_signos.setVia_ingreso(admision_seleccionada
						.getVia_ingreso());

				getServiceLocator().getServicio(Enfermera_signosService.class)
						.guardarDatos(enfermera_signos);

				MensajesUtil.mensajeInformacion("Informacion",
						"Datos guardados satisfactoriamente...");

			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Enfermera_signos getObjetoTipo1() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setPeso(dbxExamen_fisico_peso.getValue());
		enfermera_signos.setTalla(dbxExamen_fisico_talla.getValue());
		enfermera_signos
				.setPerimetro_cefalico(dbxExamen_fisico_perimetro_cflico
						.getValue());
		enfermera_signos.setFrecuencia_cardiaca(dbxExamen_fisico_fc.getValue());
		enfermera_signos.setFrecuencia_respiratoria(dbxExamen_fisico_fr
				.getValue());
		enfermera_signos.setTemperatura(dbxExamen_fisico_temp.getValue());
		return enfermera_signos;
	}

	public Enfermera_signos getObjetoTipo2() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setPeso(dbxExamen_fisico_peso1.getValue());
		enfermera_signos.setTalla(dbxExamen_fisico_talla1.getValue());
		enfermera_signos.setPerimetro_cefalico(dbxExamen_fisico_perimetro_cflico1
						.getValue());
		enfermera_signos.setFrecuencia_cardiaca(dbxExamen_fisico_fc1.getValue());
		enfermera_signos.setFrecuencia_respiratoria(dbxExamen_fisico_fr1
				.getValue());
		enfermera_signos.setTemperatura(dbxExamen_fisico_temp1.getValue());
		enfermera_signos.setImc(dbxImc1.getValue());
		return enfermera_signos;
	}

	public Enfermera_signos getObjetoTipo4() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setTa_sistolica(dbxPresion4.getValue());
		enfermera_signos.setTa_diastolica(dbxPresion24.getValue());
		enfermera_signos.setPeso(dbxPeso4.getValue());
		enfermera_signos.setTalla(dbxTalla4.getValue());
		enfermera_signos.setFrecuencia_cardiaca(dbxCardiaca4.getValue());
		enfermera_signos.setFrecuencia_respiratoria(dbxRespiratoria4.getValue());
		enfermera_signos.setImc(dbxImc4.getValue());
		enfermera_signos.setTemperatura(dbxTemperatura4.getValue());
		enfermera_signos.setFc_fetal(dbxFc_fetal4.getValue());
		enfermera_signos.setPeso_inicial(dbxPesoInicial4.getValue());
		return enfermera_signos;
		
	}
	
	public Enfermera_signos getObjetoTipo5() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setTa_sistolica(dbxPresion5.getValue());
		enfermera_signos.setTa_diastolica(dbxPresion25.getValue());
		enfermera_signos.setPeso(dbxPeso5.getValue());
		enfermera_signos.setTalla(dbxTalla5.getValue());
		enfermera_signos.setFrecuencia_cardiaca(dbxCardiaca5.getValue());
		enfermera_signos.setFrecuencia_respiratoria(dbxRespiratoria5.getValue());
		enfermera_signos.setImc(dbxImc5.getValue());
		return enfermera_signos;
		
	}
	
	public Enfermera_signos getObjetoTipo6() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setFrecuencia_cardiaca(mcDbxFrecuencia_cardiaca
				.getValue());
		enfermera_signos
				.setFrecuencia_respiratoria(mcDbxFrecuencia_respiratoria
						.getValue());
		enfermera_signos.setTemperatura(mcDbxTemparatura.getValue());
		enfermera_signos.setTa_sistolica(mcDbxTA_sistolica.getValue());
		enfermera_signos.setTa_diastolica(mcDbxTA_diastolica.getValue());
		enfermera_signos.setPeso(mcDbxPeso.getValue());
		enfermera_signos.setTalla(mcDbxTalla.getValue());
		enfermera_signos.setImc(mcDbxIMC.getValue());
		enfermera_signos.setSuperficie_corporal(mcDbxSuperficie_corporal
				.getValue());
		enfermera_signos.setTa_media(mcDbxTA_media.getValue());
		enfermera_signos
				.setCreatinina_serica(mcDbxCreatinina_serica.getValue());
		enfermera_signos.setTfg(mcDbxTFG.getValue());
		enfermera_signos.setPerimetro_cefalico(mcDbxPerimetro_cefalico
				.getValue());
		enfermera_signos.setPerimetro_toraxico(mcDbxPerimetro_toraxico
				.getValue());

		return enfermera_signos;
	}
	
	public Enfermera_signos getObjetoTipo13() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setFrecuencia_cardiaca(mcDbxFrecuencia_cardiaca13
				.getValue());
		enfermera_signos
				.setFrecuencia_respiratoria(mcDbxFrecuencia_respiratoria13
						.getValue());
		enfermera_signos.setTemperatura(mcDbxTemparatura13.getValue());
		enfermera_signos.setTa_sistolica(mcDbxTA_sistolica13.getValue());
		enfermera_signos.setTa_diastolica(mcDbxTA_diastolica13.getValue());
		enfermera_signos.setPeso(mcDbxPeso13.getValue());
		enfermera_signos.setTalla(mcDbxTalla13.getValue());
		enfermera_signos.setImc(mcDbxIMC13.getValue());
		enfermera_signos.setSuperficie_corporal(mcDbxSuperficie_corporal13
				.getValue());
		enfermera_signos.setTa_media(mcDbxTA_media13.getValue());
		enfermera_signos.setPerimetro_cefalico(mcDbxPerimetro_cefalico13
				.getValue());
		enfermera_signos.setPerimetro_toraxico(mcDbxPerimetro_toraxico13
				.getValue());

		return enfermera_signos;
	}
	
	public Enfermera_signos getObjetoTipo12() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setFrecuencia_cardiaca(mcDbxFrecuencia_cardiaca12
				.getValue());
		enfermera_signos
				.setFrecuencia_respiratoria(mcDbxFrecuencia_respiratoria12
						.getValue());
		enfermera_signos.setTemperatura(mcDbxTemparatura12.getValue());
		enfermera_signos.setTa_sistolica(mcDbxTA_sistolica12.getValue());
		enfermera_signos.setTa_diastolica(mcDbxTA_diastolica12.getValue());
		enfermera_signos.setPeso(mcDbxPeso12.getValue());
		enfermera_signos.setTalla(mcDbxTalla12.getValue());
		enfermera_signos.setImc(mcDbxIMC12.getValue());
		enfermera_signos.setSuperficie_corporal(mcDbxSuperficie_corporal12
				.getValue());
		enfermera_signos.setTa_media(mcDbxTA_media12.getValue());

		return enfermera_signos;
	}

	public Enfermera_signos getObjetoTipo7() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setTa_sistolica(dbxPresion.getValue());
		enfermera_signos.setTa_diastolica(dbxPresion27.getValue());
		enfermera_signos.setPeso(dbxPeso.getValue());
		enfermera_signos.setTalla(dbxTalla.getValue());
		return enfermera_signos;
	}

	public Enfermera_signos getObjetoTipo8() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		
		enfermera_signos.setSentado_bd1(dbxSentado8.getValue());
		enfermera_signos.setSentado_bd2(dbxSentado28.getValue());
		enfermera_signos.setDe_cubito1(dbxAcostado8.getValue());
		enfermera_signos.setDe_cubito2(dbxAcostado28.getValue());
		enfermera_signos.setDe_pie1(dbxPie8.getValue());
		enfermera_signos.setDe_pie2(dbxPie28.getValue());
		
		enfermera_signos.setFrecuencia_cardiaca(dbxCardiaca8.getValue());
		enfermera_signos.setPeso(dbxPeso8.getValue());
		enfermera_signos.setTalla(dbxTalla8.getValue());
		enfermera_signos.setImc(dbxImc8.getValue());
		
		enfermera_signos.setCircunferencia_abdominal(dbxCir_adbominal8.getValue());
		enfermera_signos.setCincunferencia_cadera(dbxCir_cadera8.getValue());
		enfermera_signos.setInd_cintura_cadera(dbxInd_cintura8.getValue());

			
		return enfermera_signos;
		
	}
	
	public Enfermera_signos getObjetoTipo9() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		
		enfermera_signos.setPeso(dbxPeso_grs9.getValue());
		enfermera_signos.setTalla(dbxTalla_cm9.getValue());
		enfermera_signos.setImc(dbxImc9.getValue());
		enfermera_signos.setFrecuencia_cardiaca(dbxFc_min9.getValue());
		enfermera_signos.setFrecuencia_respiratoria(dbxFr_min9.getValue());
		enfermera_signos.setTemperatura(dbxTemperatura_gc9.getValue());
		return enfermera_signos;
		
	}


	public Enfermera_signos getObjetoTipo10() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		
		
		enfermera_signos.setPeso(dbxPeso10.getValue());
		enfermera_signos.setTalla(dbxTalla10.getValue());
		enfermera_signos.setImc(dbxImc10.getValue());
		enfermera_signos.setSuperficie_corporal(dbxSup_corporal10.getValue());
		enfermera_signos.setCircunferencia_abdominal(dbxC_abdominal10.getValue());
		enfermera_signos.setFrecuencia_cardiaca(dbxFc_lat_min10.getValue());
		enfermera_signos.setFrecuencia_pulmonar(dbxFp_pul_min10.getValue());
		enfermera_signos.setFrecuencia_respiratoria(dbxFr_resp_min10.getValue());
		enfermera_signos.setTemperatura(dbxTemp_c10.getValue());


		enfermera_signos.setSentado_bd1(dbxTa_sentado_bd10.getValue());
		enfermera_signos.setSentado_bd2(dbxTa_sentado_mmhg10.getValue());
		enfermera_signos.setSentado_bi1(dbxTa_sentado_bi10.getValue());
		enfermera_signos.setSentado_bi2(dbxTa_sentado_mmhg210.getValue());
		enfermera_signos.setDe_cubito1(dbxTa_decubito10.getValue());
		enfermera_signos.setDe_cubito2(dbxTa_decubito_mmhg10.getValue());
		enfermera_signos.setDe_pie1(dbxTa_pie10.getValue());
		enfermera_signos.setDe_pie2(dbxTa_pie_mmhg10.getValue());
		
			
		return enfermera_signos;
		
	}
	
	public Enfermera_signos getObjetoTipo11() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setPeso(dbxSignos_vitales_peso11.getValue());
		enfermera_signos.setTalla(dbxSignos_vitales_talla11.getValue());
		enfermera_signos.setImc(dbxSignos_vitales_imc11.getValue());
		enfermera_signos.setPerimetro_cefalico(dbxSignos_vitales_pc11.getValue());
		enfermera_signos.setFrecuencia_cardiaca(dbxSignos_vitales_fc11.getValue());
		enfermera_signos.setFrecuencia_respiratoria(dbxSignos_vitales_fr11.getValue());
		enfermera_signos.setTemperatura(dbxSignos_vitales_taxilar11.getValue());
		enfermera_signos.setOximetria(dbxSignos_vitales_oximetria11.getValue());
		
		return enfermera_signos;
		
		
	}
	
	public void onMostrarAlertaTension() {
		try {
			
			Double ta_sistolica = mcDbxTA_sistolica.getValue();
			Double ta_diastolica = mcDbxTA_diastolica.getValue();
			if (ta_sistolica != null && ta_diastolica != null) {
				mcDbxTA_media
						.setValue((int) ((ta_diastolica) + ((ta_sistolica - ta_diastolica) / 3)));
			} else {
				mcDbxTA_media.setText("");
			}

		} catch (Exception e) {
			mcDbxTA_media.setText("");
			log.error(e.getMessage(), e);
		}
	}
	
	public void onMostrarAlertaTension12() {
		try {
			
			Double ta_sistolica = mcDbxTA_sistolica12.getValue();
			Double ta_diastolica = mcDbxTA_diastolica12.getValue();
			if (ta_sistolica != null && ta_diastolica != null) {
				mcDbxTA_media12
						.setValue((int) ((ta_diastolica) + ((ta_sistolica - ta_diastolica) / 3)));
			} else {
				mcDbxTA_media12.setText("");
			}

		} catch (Exception e) {
			mcDbxTA_media12.setText("");
			log.error(e.getMessage(), e);
		}
	}
	public void onMostrarAlertaTension13() {
		try {
			
			Double ta_sistolica = mcDbxTA_sistolica13.getValue();
			Double ta_diastolica = mcDbxTA_diastolica13.getValue();
			if (ta_sistolica != null && ta_diastolica != null) {
				mcDbxTA_media13
						.setValue((int) ((ta_diastolica) + ((ta_sistolica - ta_diastolica) / 3)));
			} else {
				mcDbxTA_media13.setText("");
			}

		} catch (Exception e) {
			mcDbxTA_media13.setText("");
			log.error(e.getMessage(), e);
		}
	}

	public void onCalcularIMC() {
		try {
			mcDbxIMC.setConstraint("");
			mcDbxIMC.setValue(null);
			Double talla = mcDbxTalla.getValue();
			Double peso = mcDbxPeso.getValue();

			if (talla != null && peso != null) {
				if (talla.compareTo(0.0) != 0) {
					if (CONFIG_TALLA.equals("C")) {
						talla = talla / 100;
					}
					double imc = (peso.doubleValue() / (Math.pow(
							talla.doubleValue(), 2)));
					//log.info("imc: " + imc);
					mcDbxIMC.setValue(imc);
					String alerta = "";
					String color_alerta = "none";
					if (0 <= imc && imc <= 18.4) {
						alerta = "Desnutricion - Riesgo(Aumentado)";
						color_alerta = "red";
					} else if (18.5 <= imc && imc <= 24.9) {
						alerta = "Rango Normal";
						color_alerta = "white";
					} else if (25 <= imc && imc <= 29.9) {
						alerta = "Sobrepeso - Riesgo(Aumentado)";
						color_alerta = "blue";
					} else if (30 <= imc && imc <= 34.9) {
						alerta = "Obesidad grado I - Riesgo(Moderado)";
						color_alerta = "pink";
					} else if (35 <= imc && imc <= 39.9) {
						alerta = "Obesidad grado II - Riesgo(Severo)";
						color_alerta = "#e37676";
					} else if (imc >= 40) {
						alerta = "Obesidad grado III - Riesgo(Muy severo)";
						color_alerta = "#823434";
					}
					if (!alerta.isEmpty()) {
						mcDbxIMC.setTooltiptext(alerta);
						mcDbxIMC.setStyle("background-color:" + color_alerta);
						if (color_alerta.equals("white")) {
							MensajesUtil.notificarInformacion(alerta, mcDbxIMC);
						} else {
							MensajesUtil.notificarAlerta(alerta, mcDbxIMC);
						}
					} else {
						mcDbxIMC.setTooltiptext(null);
						mcDbxIMC.setStyle("background-color:" + color_alerta);

					}
					setAlerta_imc(alerta);
				} else {
					mcDbxIMC.setTooltiptext(null);
					mcDbxIMC.setStyle("background-color:white");
					setAlerta_imc("");
				}
			} else {
				mcDbxIMC.setTooltiptext(null);
				mcDbxIMC.setStyle("background-color:white");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void onCalcularIMC12() {
		try {
			mcDbxIMC12.setConstraint("");
			mcDbxIMC12.setValue(null);
			Double talla = mcDbxTalla12.getValue();
			Double peso = mcDbxPeso12.getValue();

			if (talla != null && peso != null) {
				if (talla.compareTo(0.0) != 0) {
					if (CONFIG_TALLA.equals("C")) {
						talla = talla / 100;
					}
					double imc = (peso.doubleValue() / (Math.pow(
							talla.doubleValue(), 2)));
					//log.info("imc: " + imc);
					mcDbxIMC12.setValue(imc);
					String alerta = "";
					String color_alerta = "none";
					if (0 <= imc && imc <= 18.4) {
						alerta = "Desnutricion - Riesgo(Aumentado)";
						color_alerta = "red";
					} else if (18.5 <= imc && imc <= 24.9) {
						alerta = "Rango Normal";
						color_alerta = "white";
					} else if (25 <= imc && imc <= 29.9) {
						alerta = "Sobrepeso - Riesgo(Aumentado)";
						color_alerta = "blue";
					} else if (30 <= imc && imc <= 34.9) {
						alerta = "Obesidad grado I - Riesgo(Moderado)";
						color_alerta = "pink";
					} else if (35 <= imc && imc <= 39.9) {
						alerta = "Obesidad grado II - Riesgo(Severo)";
						color_alerta = "#e37676";
					} else if (imc >= 40) {
						alerta = "Obesidad grado III - Riesgo(Muy severo)";
						color_alerta = "#823434";
					}
					if (!alerta.isEmpty()) {
						mcDbxIMC12.setTooltiptext(alerta);
						mcDbxIMC12.setStyle("background-color:" + color_alerta);
						if (color_alerta.equals("white")) {
							MensajesUtil.notificarInformacion(alerta, mcDbxIMC12);
						} else {
							MensajesUtil.notificarAlerta(alerta, mcDbxIMC12);
						}
					} else {
						mcDbxIMC12.setTooltiptext(null);
						mcDbxIMC12.setStyle("background-color:" + color_alerta);

					}
					setAlerta_imc(alerta);
				} else {
					mcDbxIMC12.setTooltiptext(null);
					mcDbxIMC12.setStyle("background-color:white");
					setAlerta_imc("");
				}
			} else {
				mcDbxIMC12.setTooltiptext(null);
				mcDbxIMC12.setStyle("background-color:white");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void onCalcularIMC13() {
		try {
			mcDbxIMC13.setConstraint("");
			mcDbxIMC13.setValue(null);
			Double talla = mcDbxTalla13.getValue();
			Double peso = mcDbxPeso13.getValue();

			if (talla != null && peso != null) {
				if (talla.compareTo(0.0) != 0) {
					if (CONFIG_TALLA.equals("C")) {
						talla = talla / 100;
					}
					double imc = (peso.doubleValue() / (Math.pow(
							talla.doubleValue(), 2)));
					//log.info("imc: " + imc);
					mcDbxIMC13.setValue(imc);
					String alerta = "";
					String color_alerta = "none";
					if (0 <= imc && imc <= 18.4) {
						alerta = "Desnutricion - Riesgo(Aumentado)";
						color_alerta = "red";
					} else if (18.5 <= imc && imc <= 24.9) {
						alerta = "Rango Normal";
						color_alerta = "white";
					} else if (25 <= imc && imc <= 29.9) {
						alerta = "Sobrepeso - Riesgo(Aumentado)";
						color_alerta = "blue";
					} else if (30 <= imc && imc <= 34.9) {
						alerta = "Obesidad grado I - Riesgo(Moderado)";
						color_alerta = "pink";
					} else if (35 <= imc && imc <= 39.9) {
						alerta = "Obesidad grado II - Riesgo(Severo)";
						color_alerta = "#e37676";
					} else if (imc >= 40) {
						alerta = "Obesidad grado III - Riesgo(Muy severo)";
						color_alerta = "#823434";
					}
					if (!alerta.isEmpty()) {
						mcDbxIMC13.setTooltiptext(alerta);
						mcDbxIMC13.setStyle("background-color:" + color_alerta);
						if (color_alerta.equals("white")) {
							MensajesUtil.notificarInformacion(alerta, mcDbxIMC13);
						} else {
							MensajesUtil.notificarAlerta(alerta, mcDbxIMC13);
						}
					} else {
						mcDbxIMC13.setTooltiptext(null);
						mcDbxIMC13.setStyle("background-color:" + color_alerta);

					}
					setAlerta_imc(alerta);
				} else {
					mcDbxIMC13.setTooltiptext(null);
					mcDbxIMC13.setStyle("background-color:white");
					setAlerta_imc("");
				}
			} else {
				mcDbxIMC13.setTooltiptext(null);
				mcDbxIMC13.setStyle("background-color:white");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onCalcularSuperficieCorporal() {
		try {
			mcDbxSuperficie_corporal.setConstraint("");
			mcDbxSuperficie_corporal.setValue(null);
			Double talla = mcDbxTalla.getValue();
			Double peso = mcDbxPeso.getValue();
			if (talla != null && peso != null) {
				if (talla.compareTo(0.0) != 0) {
					if (CONFIG_TALLA.equals("M")) {
						talla = talla * 100;
					}
				}

				double superfice_corporal = Math
						.sqrt((peso.doubleValue() * talla.doubleValue()) / 3600);

				mcDbxSuperficie_corporal.setValue(superfice_corporal);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	public void onCalcularSuperficieCorporal12() {
		try {
			mcDbxSuperficie_corporal12.setConstraint("");
			mcDbxSuperficie_corporal12.setValue(null);
			Double talla = mcDbxTalla12.getValue();
			Double peso = mcDbxPeso12.getValue();
			if (talla != null && peso != null) {
				if (talla.compareTo(0.0) != 0) {
					if (CONFIG_TALLA.equals("M")) {
						talla = talla * 100;
					}
				}

				double superfice_corporal = Math
						.sqrt((peso.doubleValue() * talla.doubleValue()) / 3600);

				mcDbxSuperficie_corporal12.setValue(superfice_corporal);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	public void onCalcularSuperficieCorporal13() {
		try {
			mcDbxSuperficie_corporal13.setConstraint("");
			mcDbxSuperficie_corporal13.setValue(null);
			Double talla = mcDbxTalla13.getValue();
			Double peso = mcDbxPeso13.getValue();
			if (talla != null && peso != null) {
				if (talla.compareTo(0.0) != 0) {
					if (CONFIG_TALLA.equals("M")) {
						talla = talla * 100;
					}
				}

				double superfice_corporal = Math
						.sqrt((peso.doubleValue() * talla.doubleValue()) / 3600);

				mcDbxSuperficie_corporal13.setValue(superfice_corporal);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onCalcularTasaFiltracionGlomerural() {
		try {
			mcDbxTFG.setConstraint("");
			mcDbxTFG.setValue(null);
			Double talla = mcDbxTalla.getValue();
			Double peso = mcDbxPeso.getValue();
			Double superfice_corporal = mcDbxSuperficie_corporal.getValue();
			Double creatinina = mcDbxCreatinina_serica.getValue();
			if (talla != null
					&& peso != null
					&& superfice_corporal != null
					&& creatinina != null
					&& admision_seleccionada.getPaciente()
							.getFecha_nacimiento() != null
					&& admision_seleccionada.getPaciente().getSexo() != null) {
				String edad = Util.getEdad(new java.text.SimpleDateFormat(
						"dd/MM/yyyy").format(admision_seleccionada
						.getPaciente().getFecha_nacimiento()), "1", false);

				int edad_anios = Integer.parseInt(edad);

				double tfg_parcial = ((140 - edad_anios) * peso.doubleValue())
						/ (72 * creatinina.doubleValue());

				double tfg_final = tfg_parcial;
				if (admision_seleccionada.getPaciente().getSexo()
						.equalsIgnoreCase("F")) {
					tfg_final = tfg_final * 0.85;
				}

				double tfg_x = (tfg_final * 1.73)
						/ superfice_corporal.doubleValue();

				mcDbxTFG.setValue(tfg_x);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onMostrarAlertaFrecuenciaCardiaca() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getFrecuencia_cardiaca_inf() == 0.0 && parametros_signos
							.getFrecuencia_cardiaca_sup() == 0.0)) {
				mcDbxFrecuencia_cardiaca.setTooltiptext(null);
				if (mcDbxFrecuencia_cardiaca.getValue() != null) {
					double frecuencia_cardiaca = mcDbxFrecuencia_cardiaca
							.getValue();
					if (!(frecuencia_cardiaca >= parametros_signos
							.getFrecuencia_cardiaca_inf() && frecuencia_cardiaca <= parametros_signos
							.getFrecuencia_cardiaca_sup())) {

						String rango = parametros_signos
								.getFrecuencia_cardiaca_inf()
								+ " - "
								+ parametros_signos
										.getFrecuencia_cardiaca_sup();
						if (parametros_signos.getFrecuencia_cardiaca_inf() == parametros_signos
								.getFrecuencia_cardiaca_sup()) {
							rango = ""
									+ parametros_signos
											.getFrecuencia_cardiaca_inf();
						}

						MensajesUtil
								.notificarAlerta(
										"Frecuencia cardiaca anormal RI("
												+ rango + ")",
										mcDbxFrecuencia_cardiaca);
						mcDbxFrecuencia_cardiaca
								.setTooltiptext("Frecuencia cardiaca anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onMostrarAlertaFrecuenciaCardiaca12() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getFrecuencia_cardiaca_inf() == 0.0 && parametros_signos
							.getFrecuencia_cardiaca_sup() == 0.0)) {
				mcDbxFrecuencia_cardiaca12.setTooltiptext(null);
				if (mcDbxFrecuencia_cardiaca12.getValue() != null) {
					double frecuencia_cardiaca = mcDbxFrecuencia_cardiaca12
							.getValue();
					if (!(frecuencia_cardiaca >= parametros_signos
							.getFrecuencia_cardiaca_inf() && frecuencia_cardiaca <= parametros_signos
							.getFrecuencia_cardiaca_sup())) {

						String rango = parametros_signos
								.getFrecuencia_cardiaca_inf()
								+ " - "
								+ parametros_signos
										.getFrecuencia_cardiaca_sup();
						if (parametros_signos.getFrecuencia_cardiaca_inf() == parametros_signos
								.getFrecuencia_cardiaca_sup()) {
							rango = ""
									+ parametros_signos
											.getFrecuencia_cardiaca_inf();
						}

						MensajesUtil
								.notificarAlerta(
										"Frecuencia cardiaca anormal RI("
												+ rango + ")",
										mcDbxFrecuencia_cardiaca12);
						mcDbxFrecuencia_cardiaca12
								.setTooltiptext("Frecuencia cardiaca anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void onMostrarAlertaFrecuenciaCardiaca13() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getFrecuencia_cardiaca_inf() == 0.0 && parametros_signos
							.getFrecuencia_cardiaca_sup() == 0.0)) {
				mcDbxFrecuencia_cardiaca13.setTooltiptext(null);
				if (mcDbxFrecuencia_cardiaca13.getValue() != null) {
					double frecuencia_cardiaca = mcDbxFrecuencia_cardiaca13
							.getValue();
					if (!(frecuencia_cardiaca >= parametros_signos
							.getFrecuencia_cardiaca_inf() && frecuencia_cardiaca <= parametros_signos
							.getFrecuencia_cardiaca_sup())) {

						String rango = parametros_signos
								.getFrecuencia_cardiaca_inf()
								+ " - "
								+ parametros_signos
										.getFrecuencia_cardiaca_sup();
						if (parametros_signos.getFrecuencia_cardiaca_inf() == parametros_signos
								.getFrecuencia_cardiaca_sup()) {
							rango = ""
									+ parametros_signos
											.getFrecuencia_cardiaca_inf();
						}

						MensajesUtil
								.notificarAlerta(
										"Frecuencia cardiaca anormal RI("
												+ rango + ")",
										mcDbxFrecuencia_cardiaca13);
						mcDbxFrecuencia_cardiaca13
								.setTooltiptext("Frecuencia cardiaca anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void onMostrarAlertaFrecuenciaRespiratoria() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getFrecuencia_respiratoria_inf() == 0.0 && parametros_signos
							.getFrecuencia_respiratoria_sup() == 0.0)) {
				mcDbxFrecuencia_respiratoria.setTooltiptext(null);
				if (mcDbxFrecuencia_respiratoria.getValue() != null) {
					double frecuencia_respiratoria = mcDbxFrecuencia_respiratoria
							.getValue();
					if (!(frecuencia_respiratoria >= parametros_signos
							.getFrecuencia_respiratoria_inf() && frecuencia_respiratoria <= parametros_signos
							.getFrecuencia_respiratoria_sup())) {

						String rango = parametros_signos
								.getFrecuencia_respiratoria_inf()
								+ " - "
								+ parametros_signos
										.getFrecuencia_respiratoria_sup();
						if (parametros_signos.getFrecuencia_respiratoria_inf() == parametros_signos
								.getFrecuencia_respiratoria_sup()) {
							rango = ""
									+ parametros_signos
											.getFrecuencia_respiratoria_inf();
						}

						MensajesUtil.notificarAlerta(
								"Frecuencia respiratoria anormal RI(" + rango
										+ ")", mcDbxFrecuencia_respiratoria);
						mcDbxFrecuencia_respiratoria
								.setTooltiptext("Frecuencia respiratoria anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	public void onMostrarAlertaFrecuenciaRespiratoria12() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getFrecuencia_respiratoria_inf() == 0.0 && parametros_signos
							.getFrecuencia_respiratoria_sup() == 0.0)) {
				mcDbxFrecuencia_respiratoria12.setTooltiptext(null);
				if (mcDbxFrecuencia_respiratoria12.getValue() != null) {
					double frecuencia_respiratoria = mcDbxFrecuencia_respiratoria12
							.getValue();
					if (!(frecuencia_respiratoria >= parametros_signos
							.getFrecuencia_respiratoria_inf() && frecuencia_respiratoria <= parametros_signos
							.getFrecuencia_respiratoria_sup())) {

						String rango = parametros_signos
								.getFrecuencia_respiratoria_inf()
								+ " - "
								+ parametros_signos
										.getFrecuencia_respiratoria_sup();
						if (parametros_signos.getFrecuencia_respiratoria_inf() == parametros_signos
								.getFrecuencia_respiratoria_sup()) {
							rango = ""
									+ parametros_signos
											.getFrecuencia_respiratoria_inf();
						}

						MensajesUtil.notificarAlerta(
								"Frecuencia respiratoria anormal RI(" + rango
										+ ")", mcDbxFrecuencia_respiratoria12);
						mcDbxFrecuencia_respiratoria12
								.setTooltiptext("Frecuencia respiratoria anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	public void onMostrarAlertaFrecuenciaRespiratoria13() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getFrecuencia_respiratoria_inf() == 0.0 && parametros_signos
							.getFrecuencia_respiratoria_sup() == 0.0)) {
				mcDbxFrecuencia_respiratoria13.setTooltiptext(null);
				if (mcDbxFrecuencia_respiratoria13.getValue() != null) {
					double frecuencia_respiratoria = mcDbxFrecuencia_respiratoria13
							.getValue();
					if (!(frecuencia_respiratoria >= parametros_signos
							.getFrecuencia_respiratoria_inf() && frecuencia_respiratoria <= parametros_signos
							.getFrecuencia_respiratoria_sup())) {

						String rango = parametros_signos
								.getFrecuencia_respiratoria_inf()
								+ " - "
								+ parametros_signos
										.getFrecuencia_respiratoria_sup();
						if (parametros_signos.getFrecuencia_respiratoria_inf() == parametros_signos
								.getFrecuencia_respiratoria_sup()) {
							rango = ""
									+ parametros_signos
											.getFrecuencia_respiratoria_inf();
						}

						MensajesUtil.notificarAlerta(
								"Frecuencia respiratoria anormal RI(" + rango
										+ ")", mcDbxFrecuencia_respiratoria13);
						mcDbxFrecuencia_respiratoria13
								.setTooltiptext("Frecuencia respiratoria anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onMostrarAlertaTemperatura() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getTemperatura_inf() == 0.0 && parametros_signos
							.getTemperatura_sup() == 0.0)) {
				mcDbxTemparatura.setTooltiptext(null);
				if (mcDbxTemparatura.getValue() != null) {
					double temperatura = mcDbxTemparatura.getValue();
					if (!(temperatura >= parametros_signos.getTemperatura_inf() && temperatura <= parametros_signos
							.getTemperatura_sup())) {

						String rango = parametros_signos.getTemperatura_inf()
								+ " - "
								+ parametros_signos.getTemperatura_sup();
						if (parametros_signos.getTemperatura_inf() == parametros_signos
								.getTemperatura_sup()) {
							rango = "" + parametros_signos.getTemperatura_sup();
						}

						MensajesUtil.notificarAlerta("Temperatura anormal RI("
								+ rango + ")", mcDbxTemparatura);
						mcDbxTemparatura
								.setTooltiptext("Temperatura anormal RI("
										+ rango + ")");
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	public void onMostrarAlertaTemperatura12() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getTemperatura_inf() == 0.0 && parametros_signos
							.getTemperatura_sup() == 0.0)) {
				mcDbxTemparatura12.setTooltiptext(null);
				if (mcDbxTemparatura12.getValue() != null) {
					double temperatura = mcDbxTemparatura.getValue();
					if (!(temperatura >= parametros_signos.getTemperatura_inf() && temperatura <= parametros_signos
							.getTemperatura_sup())) {

						String rango = parametros_signos.getTemperatura_inf()
								+ " - "
								+ parametros_signos.getTemperatura_sup();
						if (parametros_signos.getTemperatura_inf() == parametros_signos
								.getTemperatura_sup()) {
							rango = "" + parametros_signos.getTemperatura_sup();
						}

						MensajesUtil.notificarAlerta("Temperatura anormal RI("
								+ rango + ")", mcDbxTemparatura12);
						mcDbxTemparatura12
								.setTooltiptext("Temperatura anormal RI("
										+ rango + ")");
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	public void onMostrarAlertaTemperatura13() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getTemperatura_inf() == 0.0 && parametros_signos
							.getTemperatura_sup() == 0.0)) {
				mcDbxTemparatura13.setTooltiptext(null);
				if (mcDbxTemparatura13.getValue() != null) {
					double temperatura = mcDbxTemparatura13.getValue();
					if (!(temperatura >= parametros_signos.getTemperatura_inf() && temperatura <= parametros_signos
							.getTemperatura_sup())) {

						String rango = parametros_signos.getTemperatura_inf()
								+ " - "
								+ parametros_signos.getTemperatura_sup();
						if (parametros_signos.getTemperatura_inf() == parametros_signos
								.getTemperatura_sup()) {
							rango = "" + parametros_signos.getTemperatura_sup();
						}

						MensajesUtil.notificarAlerta("Temperatura anormal RI("
								+ rango + ")", mcDbxTemparatura13);
						mcDbxTemparatura13
								.setTooltiptext("Temperatura anormal RI("
										+ rango + ")");
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void onMostrarAlertaTensionSistolica() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getTa_sistolica_inf() == 0.0 && parametros_signos
							.getTa_sistolica_sup() == 0.0)) {
				mcDbxTA_sistolica.setTooltiptext(null);
				if (mcDbxTA_sistolica.getValue() != null) {
					double ta_sistolica = mcDbxTA_sistolica.getValue();
					if (!(ta_sistolica >= parametros_signos
							.getTa_sistolica_inf() && ta_sistolica <= parametros_signos
							.getTa_sistolica_sup())) {

						String rango = parametros_signos.getTa_sistolica_inf()
								+ " - "
								+ parametros_signos.getTa_sistolica_sup();
						if (parametros_signos.getTa_sistolica_inf() == parametros_signos
								.getTa_sistolica_sup()) {
							rango = ""
									+ parametros_signos.getTa_sistolica_inf();
						}

						MensajesUtil.notificarAlerta(
								"Tension arterial sistolica anormal RI("
										+ rango + ")", mcDbxTA_sistolica);
						mcDbxTA_sistolica
								.setTooltiptext("Tension arterial sistolica anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void onMostrarAlertaTensionDiastolica() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getTa_diastolica_inf() == 0.0 && parametros_signos
							.getTa_diastolica_sup() == 0.0)) {
				mcDbxTA_diastolica.setTooltiptext(null);
				if (mcDbxTA_diastolica.getValue() != null) {
					double ta_diastolica = mcDbxTA_diastolica.getValue();
					if (!(ta_diastolica >= parametros_signos
							.getTa_diastolica_inf() && ta_diastolica <= parametros_signos
							.getTa_diastolica_sup())) {

						String rango = parametros_signos.getTa_diastolica_inf()
								+ " - "
								+ parametros_signos.getTa_diastolica_sup();
						if (parametros_signos.getTa_diastolica_inf() == parametros_signos
								.getTa_diastolica_sup()) {
							rango = ""
									+ parametros_signos.getTa_diastolica_inf();
						}

						MensajesUtil.notificarAlerta(
								"Tension arterial diastolica anormal RI("
										+ rango + ")", mcDbxTA_diastolica);
						mcDbxTA_diastolica
								.setTooltiptext("Tension arterial diastolica anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
	
	public void onMostrarAlertaTensionSistolica12() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getTa_sistolica_inf() == 0.0 && parametros_signos
							.getTa_sistolica_sup() == 0.0)) {
				mcDbxTA_sistolica12.setTooltiptext(null);
				if (mcDbxTA_sistolica12.getValue() != null) {
					double ta_sistolica = mcDbxTA_sistolica12.getValue();
					if (!(ta_sistolica >= parametros_signos
							.getTa_sistolica_inf() && ta_sistolica <= parametros_signos
							.getTa_sistolica_sup())) {

						String rango = parametros_signos.getTa_sistolica_inf()
								+ " - "
								+ parametros_signos.getTa_sistolica_sup();
						if (parametros_signos.getTa_sistolica_inf() == parametros_signos
								.getTa_sistolica_sup()) {
							rango = ""
									+ parametros_signos.getTa_sistolica_inf();
						}

						MensajesUtil.notificarAlerta(
								"Tension arterial sistolica anormal RI("
										+ rango + ")", mcDbxTA_sistolica12);
						mcDbxTA_sistolica12
								.setTooltiptext("Tension arterial sistolica anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void onMostrarAlertaTensionDiastolica12() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getTa_diastolica_inf() == 0.0 && parametros_signos
							.getTa_diastolica_sup() == 0.0)) {
				mcDbxTA_diastolica12.setTooltiptext(null);
				if (mcDbxTA_diastolica12.getValue() != null) {
					double ta_diastolica = mcDbxTA_diastolica12.getValue();
					if (!(ta_diastolica >= parametros_signos
							.getTa_diastolica_inf() && ta_diastolica <= parametros_signos
							.getTa_diastolica_sup())) {

						String rango = parametros_signos.getTa_diastolica_inf()
								+ " - "
								+ parametros_signos.getTa_diastolica_sup();
						if (parametros_signos.getTa_diastolica_inf() == parametros_signos
								.getTa_diastolica_sup()) {
							rango = ""
									+ parametros_signos.getTa_diastolica_inf();
						}

						MensajesUtil.notificarAlerta(
								"Tension arterial diastolica anormal RI("
										+ rango + ")", mcDbxTA_diastolica12);
						mcDbxTA_diastolica12
								.setTooltiptext("Tension arterial diastolica anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
	
	
	public void onMostrarAlertaTensionSistolica13() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getTa_sistolica_inf() == 0.0 && parametros_signos
							.getTa_sistolica_sup() == 0.0)) {
				mcDbxTA_sistolica13.setTooltiptext(null);
				if (mcDbxTA_sistolica13.getValue() != null) {
					double ta_sistolica = mcDbxTA_sistolica13.getValue();
					if (!(ta_sistolica >= parametros_signos
							.getTa_sistolica_inf() && ta_sistolica <= parametros_signos
							.getTa_sistolica_sup())) {

						String rango = parametros_signos.getTa_sistolica_inf()
								+ " - "
								+ parametros_signos.getTa_sistolica_sup();
						if (parametros_signos.getTa_sistolica_inf() == parametros_signos
								.getTa_sistolica_sup()) {
							rango = ""
									+ parametros_signos.getTa_sistolica_inf();
						}

						MensajesUtil.notificarAlerta(
								"Tension arterial sistolica anormal RI("
										+ rango + ")", mcDbxTA_sistolica13);
						mcDbxTA_sistolica13
								.setTooltiptext("Tension arterial sistolica anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void onMostrarAlertaTensionDiastolica13() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getTa_diastolica_inf() == 0.0 && parametros_signos
							.getTa_diastolica_sup() == 0.0)) {
				mcDbxTA_diastolica13.setTooltiptext(null);
				if (mcDbxTA_diastolica13.getValue() != null) {
					double ta_diastolica = mcDbxTA_diastolica13.getValue();
					if (!(ta_diastolica >= parametros_signos
							.getTa_diastolica_inf() && ta_diastolica <= parametros_signos
							.getTa_diastolica_sup())) {

						String rango = parametros_signos.getTa_diastolica_inf()
								+ " - "
								+ parametros_signos.getTa_diastolica_sup();
						if (parametros_signos.getTa_diastolica_inf() == parametros_signos
								.getTa_diastolica_sup()) {
							rango = ""
									+ parametros_signos.getTa_diastolica_inf();
						}

						MensajesUtil.notificarAlerta(
								"Tension arterial diastolica anormal RI("
										+ rango + ")", mcDbxTA_diastolica13);
						mcDbxTA_diastolica13
								.setTooltiptext("Tension arterial diastolica anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void onMostrarAlertaCreatininaSerica() {
		try {
			if (parametros_signos != null
					&& !(parametros_signos.getCreatinina_cerica_inf() == 0.0 && parametros_signos
							.getCreatinina_cerica_sup() == 0.0)) {
				mcDbxCreatinina_serica.setTooltiptext(null);
				if (mcDbxCreatinina_serica.getValue() != null) {
					double creatinina_serica = mcDbxCreatinina_serica
							.getValue();
					if (!(creatinina_serica >= parametros_signos
							.getCreatinina_cerica_inf() && creatinina_serica <= parametros_signos
							.getCreatinina_cerica_sup())) {

						String rango = parametros_signos
								.getCreatinina_cerica_inf()
								+ " - "
								+ parametros_signos.getCreatinina_cerica_sup();
						if (parametros_signos.getCreatinina_cerica_inf() == parametros_signos
								.getCreatinina_cerica_sup()) {
							rango = ""
									+ parametros_signos
											.getCreatinina_cerica_inf();
						}

						MensajesUtil.notificarAlerta(
								"Creatinina serica anormal RI(" + rango + ")",
								mcDbxCreatinina_serica);
						mcDbxCreatinina_serica
								.setTooltiptext("Creatinina serica anormal RI("
										+ rango + ")");
					}

				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void inicializarParametrosAlertas() {
		if (admision_seleccionada != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa",
					admision_seleccionada.getCodigo_empresa());
			parametros.put("sexo", admision_seleccionada.getPaciente()
					.getSexo());
			List<Parametros_signos> listado_parametros = getServiceLocator()
					.getServicio(Parametros_signosService.class).listar(
							parametros);
			parametros_signos = obtenerParametrosSignos(listado_parametros,
					admision_seleccionada.getPaciente().getFecha_nacimiento());
		}
	}

	public static Parametros_signos obtenerParametrosSignos(
			List<Parametros_signos> listado_parametros, Date fecha_nacimiento) {
		Parametros_signos parametros_signos_aux = null;
		for (Parametros_signos parametros_signos : listado_parametros) {
			int edad = Integer.parseInt(Util.getEdad(
					new java.text.SimpleDateFormat("dd/MM/yyyy")
							.format(fecha_nacimiento), parametros_signos
							.getMedida_edad(), false));
			if (edad >= parametros_signos.getEdad_inferior()
					&& edad <= parametros_signos.getEdad_superior()) {
				parametros_signos_aux = parametros_signos;
				break;
			}
		}
		return parametros_signos_aux;
	}

	public String getAlerta_imc() {
		return alerta_imc;
	}

	public void setAlerta_imc(String alerta_imc) {
		this.alerta_imc = alerta_imc;
	}

	public void validarFechaNacimiento(Date fecha_nacimiento) {
		String edad = Util.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy")
				.format(admision_seleccionada.getPaciente()
						.getFecha_nacimiento()), "2", false);
		int edad_integer = Integer.parseInt(edad);
		if (edad != null && !edad.isEmpty()) {
			mcLabelPerimetro_toraxico.setVisible((edad_integer <= 120));
			mcLabelPerimetro_cefalico.setVisible((edad_integer <= 120));
			mcDbxPerimetro_cefalico.setVisible((edad_integer <= 120));
			mcDbxPerimetro_toraxico.setVisible((edad_integer <= 120));
		} else {
			mcDbxPerimetro_cefalico.setVisible(true);
			mcDbxPerimetro_toraxico.setVisible(true);
			mcLabelPerimetro_toraxico.setVisible(true);
			mcLabelPerimetro_cefalico.setVisible(true);
		}
	}
	
	
	//  --------------------------------------------------------------------------------
	
	
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
			Date fecha_nacimiento = admision_seleccionada.getPaciente()
					.getFecha_nacimiento();
			String genero = admision_seleccionada.getPaciente().getSexo();

			UtilidadSignosVitales.onMostrarAlertaFrecuenciaCardiaca(dbxFc,
					fecha_nacimiento, genero);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void alarmaExamenFisicoFc() {
		UtilidadSignosVitales.validarMaxMin(dbxCardiaca8, 61d, 39d, "Anormal", "Anormal", false);
	}
	
	public void alarmaExamenFisicoFr_embarazo() {
		
		if (edad >= 8 && edad <= 15) {
			UtilidadSignosVitales.validarMaxMin(dbxRespiratoria4, 21d, 17d,
					"Anormal", "Anormal", false);
		} else {
			UtilidadSignosVitales.validarMaxMin(dbxRespiratoria4, 21d, 15d,
					"Anormal", "Anormal", false);
		}
	}
	
	public void alarmaExamenFisicoFc_embarazo() {
		
		if (edad >= 8 && edad <= 15) {
			UtilidadSignosVitales.validarMaxMin(dbxCardiaca4, 87d, 79d,
					"Anormal", "Anormal", false);
		} else {
			UtilidadSignosVitales.validarMaxMin(dbxCardiaca4, 81d, 59d,
					"Anormal", "Anormal", false);
		}
	}
	
	public void calcularFrecuenciaRespiratoria(Doublebox dbxFr) {
		try {
			Date fecha_nacimiento = admision_seleccionada.getPaciente()
					.getFecha_nacimiento();
			String genero = admision_seleccionada.getPaciente().getSexo();

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
	
	public void alertaTemperatura(Doublebox dbxTemperatura) {
		UtilidadSignosVitales.onMostrarAlertaTemperaturaEmbarazo(dbxTemperatura);
	}
	
	
	// --------------  Menores ------------------
	
	public void alarmaExamenFisicoFc_menores() {
		
		//log.info(">>>>>>>>" + edad);
		if (edad == 0) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc, 161d,
					119d, "Anormal", "Anormal", false);
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc1, 161d,
					119d, "Anormal", "Anormal", false);
		} else if (edad == 1) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc, 131d,
					119d, "Anormal", "Anormal", false);
		} else if (edad == 2) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc1, 121d, 99d,
					"Anormal", "Anormal", false);
		} else if (edad == 3) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc1, 101d, 89d,
					"Anormal", "Anormal", false);
		} else if (edad == 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fc1, 91d, 85d,
					"Anormal", "Anormal", false);
		} else if (edad >= 5 && edad < 8){
			UtilidadSignosVitales.validarMaxMin(dbxFc_min9,91d,85d, 
					"Anormal", "Anormal", false);
		}else if(edad >= 8 && edad <= 10){
			UtilidadSignosVitales.validarMaxMin(dbxFc_min9,87d, 79d, 
					"Anormal", "Anormal", false);
		}
		
	}

	public void alarmaExamenFisicoFr_menores() {
		
		if (edad == 0) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr, 41d, 29d,
					"Anormal", "Anormal", false);
		} else if (edad == 1) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr, 31d, 25d,
					"Anormal", "Anormal", false);
		} else if (edad >= 2 && edad < 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr1, 26d, 24d,
					"Anormal", "Anormal", false);
		} else if (edad == 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_fr1, 26d, 19d,
					"Anormal", "Anormal", false);
		} else if (edad >= 5 && edad < 8){
			UtilidadSignosVitales.validarMaxMin(dbxFr_min9,26d, 19d, 
					"Anormal", "Anormal", false);
		}else if(edad >= 8 && edad <= 10){
			UtilidadSignosVitales.validarMaxMin(dbxFr_min9,21d,16d, 
					"Anormal", "Anormal", false);
		}
	}

	public void alarmaExamenFisicoTemperatura_menores() {
	
		if (edad >= 0 && edad < 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_temp, 37.9,
					36.5, "Anormal", "Anormal", false);
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_temp1, 37.9,
					36.5, "Anormal", "Anormal", false);
		} else if (edad >= 4) {
			UtilidadSignosVitales.validarMaxMin(dbxExamen_fisico_temp1, 37.1,
					36.4, "Anormal", "Anormal", false);
			UtilidadSignosVitales.validarMaxMin(dbxTemperatura_gc9, 37.1,
					36.4, "Anormal", "Anormal", false);

		}

	}
	
	public void calcularSuperficieCorporal(Doublebox dbxTalla,
			Doublebox dbxPeso, Doublebox dbxSup_corporalc) {
		if (dbxPeso.getValue() != null & dbxTalla.getValue() != null) {
			Double peso = dbxPeso.getValue() * 100;
			Double talla = dbxTalla.getValue();
			Double supcor = (peso * talla) / 3600;
			dbxSup_corporalc.setValue(Math.sqrt(supcor));
		}
	}
	
	public void calcularIMCSuperficieCorporal(Doublebox dbxPeso,
			Doublebox dbxTalla, Doublebox dbxImc, Doublebox dbxSup_corporalc) {
		calcularIMC(dbxPeso, dbxTalla, dbxImc);
		calcularSuperficieCorporal(dbxTalla, dbxPeso, dbxSup_corporalc);
	}
	

	public void calcularCintura_adbominal(Doublebox dbxCintura_abdominal) {
		try {
			double min = 0;
			double max = 0;
			//log.info("sexo" + admision_seleccionada.getPaciente().getSexo().equals("F"));
			if (admision_seleccionada.getPaciente().getSexo().equals("F")) {
				max = 81;
			} else {
				max = 90;
			}
			UtilidadSignosVitales.validarMaxMin(dbxCintura_abdominal, max, min,
					"Valor Anormal", "Valor Normal", false);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	public void alertaTemperatura_aiepi(Doublebox dbxSignos_vitales_taxilar) {
		UtilidadSignosVitales.onMostrarAlertaTemperatura(
				dbxSignos_vitales_taxilar, admision_seleccionada.getPaciente().getFecha_nacimiento(), admision_seleccionada.getPaciente().getSexo());
		
	}
	
	

}
