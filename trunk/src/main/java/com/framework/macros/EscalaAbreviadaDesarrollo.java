package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Escala_del_desarrollo;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.Util;

public class EscalaAbreviadaDesarrollo extends Grid implements AfterCompose {

	private static Logger log = Logger
			.getLogger(EscalaAbreviadaDesarrollo.class);

	private Checkbox mcChbPatea_vigorosamente;
	private Checkbox mcChbLevanta_cabeza_prona;
	private Checkbox mcChbLevanta_cab_pecho;
	private Checkbox mcChbSostiene_cabeza;
	private Checkbox mcChbControl_de_cabeza;
	private Checkbox mcChbVoltea_de_lado;
	private Checkbox mcChbIntenta_sentarse;
	private Checkbox mcChbSostiene_sentado;
	private Checkbox mcChbSe_arrastra;
	private Checkbox mcChbSe_sienta_solo;
	private Checkbox mcChbGatea_bien;
	private Checkbox mcChbSe_agarra_sotiene;
	private Checkbox mcChbSe_para_solo;
	private Checkbox mcChbDa_pasitos_solo;
	private Checkbox mcChbCamina_solo_bien;
	private Checkbox mcChbCorre;
	private Checkbox mcChbPatea_la_pelota;
	private Checkbox mcChbLanza_la_pelota;
	private Checkbox mcChbSalta_en_dos_pies;
	private Checkbox mcChbSe_empina;
	private Checkbox mcChbSe_levanta_sin_usar;
	private Checkbox mcChbCamina_hacia_atras;
	private Checkbox mcChbCamina_en_puntas;
	private Checkbox mcChbSe_para_en_pies;
	private Checkbox mcChbLanza_agarra_pelota;
	private Checkbox mcChbCamina_en_linea;
	private Checkbox mcChbTres_pasos_en_pies;
	private Checkbox mcChbHace_rebotar_pelota;
	private Checkbox mcChbSalta_pies_juntillas;
	private Checkbox mcChbHace_caballitos;
	private Checkbox mcChbSalta_alto;
	private Checkbox mcChbSigue_mov_horiz;
	private Checkbox mcChbAbre_mira_manos;
	private Checkbox mcChbSostiene_obj_mano;
	private Checkbox mcChbSe_lleva_obj_boca;
	private Checkbox mcChbAgarra_objetos_volun;
	private Checkbox mcChbSostiene_obj_cada_mano;
	private Checkbox mcChbPasa_obj_a_manos;
	private Checkbox mcChbManipula_varios_obj;
	private Checkbox mcChbAgarra_obj_pequenio;
	private Checkbox mcChbAgarra_cubo;
	private Checkbox mcChbMete_saca_obj;
	private Checkbox mcChbAgarra_tercer_obj;
	private Checkbox mcChbBusca_obj_escondidos;
	private Checkbox mcChbHace_torre;
	private Checkbox mcChbPasa_hojas_libro;
	private Checkbox mcChbAnticipa_salida;
	private Checkbox mcChbTapa_bien_caja;
	private Checkbox mcChbHace_garabatos;
	private Checkbox mcChbHace_torres_varios;
	private Checkbox mcChbEnsarta_mas_cuentas;
	private Checkbox mcChbCopia_linea_hor;
	private Checkbox mcChbSepara_obj_grandes;
	private Checkbox mcChbFigura_humana_rudim;
	private Checkbox mcChbCorta_papel_tijerea;
	private Checkbox mcChbCopia_cuadrado;
	private Checkbox mcChbDibuja_fig_humana;
	private Checkbox mcChbAgrupa_col_form;
	private Checkbox mcChbDibuja_escalera;
	private Checkbox mcChbAgrupa_col_form_tam;
	private Checkbox mcChbReconstruye_escalera;
	private Checkbox mcChbDibuja_casa;
	private Checkbox mcChbSe_sobresalta_en_ruido;
	private Checkbox mcChbBusca_sonido;
	private Checkbox mcChbDos_sonidos_guturales;
	private Checkbox mcChbBalbucea_con_persona;
	private Checkbox mcChbCuatro_sonidos_dif;
	private Checkbox mcChbRie_carcajadas;
	private Checkbox mcChbReacciona_al_llamar;
	private Checkbox mcChbPronuncia_silabas;
	private Checkbox mcChbHace_sonar_campana;
	private Checkbox mcChbPalabra_clara;
	private Checkbox mcChbNiega_con_cabeza;
	private Checkbox mcChbLlama_a_madre;
	private Checkbox mcChbEntiende_orden;
	private Checkbox mcChbReconoce_tres_obj;
	private Checkbox mcChbCombina_dos_palabras;
	private Checkbox mcChbReconoce_seis_obj;
	private Checkbox mcChbNombra_cinco_obj;
	private Checkbox mcChbFrase_tres_palab;
	private Checkbox mcChbVeinte_palabras;
	private Checkbox mcChbDice_su_nombre;
	private Checkbox mcChbConoce_alto_bajo;
	private Checkbox mcChbUsa_oraciones_compl;
	private Checkbox mcChbDefine_cinco_obj;
	private Checkbox mcChbRepite_tres_digit;
	private Checkbox mcChbDescribe_dibujos;
	private Checkbox mcChbCuenta_dedos_manos;
	private Checkbox mcChbDistingue_adel_atras;
	private Checkbox mcChbNombra_colores;
	private Checkbox mcChbExpresa_opiniones;
	private Checkbox mcChbConoce_izq_dere;
	private Checkbox mcChbConoce_dias_sem;
	private Checkbox mcChbSigue_mov_rostro;
	private Checkbox mcChbReconoce_la_madre;
	private Checkbox mcChbSonrie_acariciarlo;
	private Checkbox mcChbVoltea_cuando_sele_habl;
	private Checkbox mcChbCoge_manos_del_exam;
	private Checkbox mcChbAcepta_coge_jugue;
	private Checkbox mcChbPone_atencion_conv;
	private Checkbox mcChbAyuda_sostener_taza;
	private Checkbox mcChbReacciona_imagen;
	private Checkbox mcChbImita_aplauso;
	private Checkbox mcChbEntrega_juguete;
	private Checkbox mcChbPide_un_juguete;
	private Checkbox mcChbBeb_en_taza;
	private Checkbox mcChbSeniala_una_prenda;
	private Checkbox mcChbSeniala_dos_partes_cuerp;
	private Checkbox mcChbAvisa_higiene_personal;
	private Checkbox mcChbSeniala_cinco_partes;
	private Checkbox mcChbTrata_contar_exper;
	private Checkbox mcChbControl_diurno_orina;
	private Checkbox mcChbDiferencia_ninio_ninia;
	private Checkbox mcChbDice_papa_mama;
	private Checkbox mcChbSe_bania_solo;
	private Checkbox mcChbPuede_desvestirse;
	private Checkbox mcChbComparte_juego;
	private Checkbox mcChbTiene_amigo_espec;
	private Checkbox mcChbViste_desviste_solo;
	private Checkbox mcChbSabe_cuantos_anio_tien;
	private Checkbox mcChbOrganiza_juegos;
	private Checkbox mcChbHace_mandados;
	private Checkbox mcChbReconoce_nombre_verda;
	private Checkbox mcChbComenta_vida_famil;
	private Intbox mcTbxMotricidad_gruesa;
	private Intbox mcTbxMitricidad_adaptativa;
	private Intbox mcTbxAudicion_leng;
	private Intbox mcTbxPersonal_social;
	private Intbox mcTbxTotal;
	private Textbox mcTbxObservacion;
//	private Textbox mcTbxMotricidad_gruesa_alerta;
//	private Textbox mcTbxMitricidad_adaptativa_alerta;
//	private Textbox mcTbxAudicion_leng_alerta;
//	private Textbox mcTbxPersonal_social_alerta;

	private Label lbAlertaMotricidad1;
	private Label lbAlertaMotricidad2;
	private Label lbAlertaMotricidad3;
	private Label lbAlertaMotricidad4;
	private Label lbAlertaAdaptativa1;
	private Label lbAlertaAdaptativa2;
	private Label lbAlertaAdaptativa3;
	private Label lbAlertaAdaptativa4;
	private Label lbAlertalenguaje1;
	private Label lbAlertalenguaje2;
	private Label lbAlertalenguaje3;
	private Label lbAlertalenguaje4;
	private Label lbAlertaPersonal1;
	private Label lbAlertaPersonal2;
	private Label lbAlertaPersonal3;
	private Label lbAlertaPersonal4;
	
	// declaraciones ROWs
	private Rows rowsEscala;
	private Row rowEscala1;
	private Row rowEscala2;
	private Row rowEscala3;
	private Row rowEscala4;

	private Row rowEscala4_6;
	private Row rowEscala7_9;
	private Row rowEscala10_12;
	private Row rowEscala13_18;
	private Row rowEscala19_24;
	private Row rowEscala25_36;
	private Row rowEscala37_48;
	private Row rowEscala49_60;
	private Row rowEscala61_72;

	private Row rowEscala111;
	private Row rowEscala112;
	private Row rowEscala113;
	private Row rowEscala114;
	private Row rowEscala115;
	private Row rowEscala116;
	private Row rowEscala117;
	private Row rowEscala118;
	private Row rowEscala119;

	// private Row rowTotal1;
	// private Row rowTotal2;
	// private Row rowTotal3;
	// private Row rowTotal4;
	// private Row rowTotal5;
	// private Row rowTotal6;
	// private Row rowTotal7;
	// private Row rowTotal8;
	// private Row rowTotal9;
	//

	private Label lbAlertaTotal1;
	private Label lbAlertaTotal2;
	private Label lbAlertaTotal3;
	private Label lbAlertaTotal4;

	// declaraciones
	private Integer subtotalNum1 = 0;
	private Integer subtotalNum2 = 0;
	private Integer subtotalNum3 = 0;
	private Integer subtotalNum4 = 0;

	private ZKWindow zkWindow;
	private Escala_del_desarrollo escala_del_desarrollo;

	private Admision admision;

	@Override
	public void afterCompose() {
		try {
			cargarComponentes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarComponentes() {

		rowsEscala = (Rows) this.getFellow("rowsEscala");
		rowEscala1 = (Row) this.getFellow("rowEscala1");
		rowEscala2 = (Row) this.getFellow("rowEscala2");

		rowEscala3 = (Row) this.getFellow("rowEscala3");
		rowEscala4 = (Row) this.getFellow("rowEscala4");

		rowEscala4_6 = (Row) this.getFellow("rowEscala4_6");
		rowEscala7_9 = (Row) this.getFellow("rowEscala7_9");
		rowEscala10_12 = (Row) this.getFellow("rowEscala10_12");
		rowEscala13_18 = (Row) this.getFellow("rowEscala13_18");
		rowEscala19_24 = (Row) this.getFellow("rowEscala19_24");
		rowEscala25_36 = (Row) this.getFellow("rowEscala25_36");
		rowEscala37_48 = (Row) this.getFellow("rowEscala37_48");
		rowEscala49_60 = (Row) this.getFellow("rowEscala49_60");
		rowEscala61_72 = (Row) this.getFellow("rowEscala61_72");

		rowEscala111 = (Row) this.getFellow("rowEscala111");
		rowEscala112 = (Row) this.getFellow("rowEscala112");
		rowEscala113 = (Row) this.getFellow("rowEscala113");
		rowEscala114 = (Row) this.getFellow("rowEscala114");
		rowEscala115 = (Row) this.getFellow("rowEscala115");
		rowEscala116 = (Row) this.getFellow("rowEscala116");
		rowEscala117 = (Row) this.getFellow("rowEscala117");
		rowEscala118 = (Row) this.getFellow("rowEscala118");
		rowEscala119 = (Row) this.getFellow("rowEscala119");

		// rowTotal1 = (Row) this.getFellow("rowTotal1");
		// rowTotal2 = (Row) this.getFellow("rowTotal2");
		// rowTotal3 = (Row) this.getFellow("rowTotal3");
		// rowTotal4 = (Row) this.getFellow("rowTotal4");
		// rowTotal5 = (Row) this.getFellow("rowTotal5");
		// rowTotal6 = (Row) this.getFellow("rowTotal6");
		// rowTotal7 = (Row) this.getFellow("rowTotal7");
		// rowTotal8 = (Row) this.getFellow("rowTotal8");
		// rowTotal9 = (Row) this.getFellow("rowTotal9");
		//
//		lbAlertaTotal1 = (Label) this.getFellow("lbAlertaTotal1");
//		lbAlertaTotal2 = (Label) this.getFellow("lbAlertaTotal2");
//		lbAlertaTotal3 = (Label) this.getFellow("lbAlertaTotal3");
//		lbAlertaTotal4 = (Label) this.getFellow("lbAlertaTotal4");

		mcChbPatea_vigorosamente = (Checkbox) this
				.getFellow("mcChbPatea_vigorosamente");
		mcChbLevanta_cabeza_prona = (Checkbox) this
				.getFellow("mcChbLevanta_cabeza_prona");
		mcChbLevanta_cab_pecho = (Checkbox) this
				.getFellow("mcChbLevanta_cab_pecho");
		mcChbSostiene_cabeza = (Checkbox) this
				.getFellow("mcChbSostiene_cabeza");
		mcChbControl_de_cabeza = (Checkbox) this
				.getFellow("mcChbControl_de_cabeza");
		mcChbVoltea_de_lado = (Checkbox) this.getFellow("mcChbVoltea_de_lado");
		mcChbIntenta_sentarse = (Checkbox) this
				.getFellow("mcChbIntenta_sentarse");
		mcChbSostiene_sentado = (Checkbox) this
				.getFellow("mcChbSostiene_sentado");
		mcChbSe_arrastra = (Checkbox) this.getFellow("mcChbSe_arrastra");
		mcChbSe_sienta_solo = (Checkbox) this.getFellow("mcChbSe_sienta_solo");
		mcChbGatea_bien = (Checkbox) this.getFellow("mcChbGatea_bien");
		mcChbSe_agarra_sotiene = (Checkbox) this
				.getFellow("mcChbSe_agarra_sotiene");
		mcChbSe_para_solo = (Checkbox) this.getFellow("mcChbSe_para_solo");
		mcChbDa_pasitos_solo = (Checkbox) this
				.getFellow("mcChbDa_pasitos_solo");
		mcChbCamina_solo_bien = (Checkbox) this
				.getFellow("mcChbCamina_solo_bien");
		mcChbCorre = (Checkbox) this.getFellow("mcChbCorre");
		mcChbPatea_la_pelota = (Checkbox) this
				.getFellow("mcChbPatea_la_pelota");
		mcChbLanza_la_pelota = (Checkbox) this
				.getFellow("mcChbLanza_la_pelota");
		mcChbSalta_en_dos_pies = (Checkbox) this
				.getFellow("mcChbSalta_en_dos_pies");
		mcChbSe_empina = (Checkbox) this.getFellow("mcChbSe_empina");
		mcChbSe_levanta_sin_usar = (Checkbox) this
				.getFellow("mcChbSe_levanta_sin_usar");
		mcChbCamina_hacia_atras = (Checkbox) this
				.getFellow("mcChbCamina_hacia_atras");
		mcChbCamina_en_puntas = (Checkbox) this
				.getFellow("mcChbCamina_en_puntas");
		mcChbSe_para_en_pies = (Checkbox) this
				.getFellow("mcChbSe_para_en_pies");
		mcChbLanza_agarra_pelota = (Checkbox) this
				.getFellow("mcChbLanza_agarra_pelota");
		mcChbCamina_en_linea = (Checkbox) this
				.getFellow("mcChbCamina_en_linea");
		mcChbTres_pasos_en_pies = (Checkbox) this
				.getFellow("mcChbTres_pasos_en_pies");
		mcChbHace_rebotar_pelota = (Checkbox) this
				.getFellow("mcChbHace_rebotar_pelota");
		mcChbSalta_pies_juntillas = (Checkbox) this
				.getFellow("mcChbSalta_pies_juntillas");
		mcChbHace_caballitos = (Checkbox) this
				.getFellow("mcChbHace_caballitos");
		mcChbSalta_alto = (Checkbox) this.getFellow("mcChbSalta_alto");
		mcChbSigue_mov_horiz = (Checkbox) this
				.getFellow("mcChbSigue_mov_horiz");
		mcChbAbre_mira_manos = (Checkbox) this
				.getFellow("mcChbAbre_mira_manos");
		mcChbSostiene_obj_mano = (Checkbox) this
				.getFellow("mcChbSostiene_obj_mano");
		mcChbSe_lleva_obj_boca = (Checkbox) this
				.getFellow("mcChbSe_lleva_obj_boca");
		mcChbAgarra_objetos_volun = (Checkbox) this
				.getFellow("mcChbAgarra_objetos_volun");
		mcChbSostiene_obj_cada_mano = (Checkbox) this
				.getFellow("mcChbSostiene_obj_cada_mano");
		mcChbPasa_obj_a_manos = (Checkbox) this
				.getFellow("mcChbPasa_obj_a_manos");
		mcChbManipula_varios_obj = (Checkbox) this
				.getFellow("mcChbManipula_varios_obj");
		mcChbAgarra_obj_pequenio = (Checkbox) this
				.getFellow("mcChbAgarra_obj_pequenio");
		mcChbAgarra_cubo = (Checkbox) this.getFellow("mcChbAgarra_cubo");
		mcChbMete_saca_obj = (Checkbox) this.getFellow("mcChbMete_saca_obj");
		mcChbAgarra_tercer_obj = (Checkbox) this
				.getFellow("mcChbAgarra_tercer_obj");
		mcChbBusca_obj_escondidos = (Checkbox) this
				.getFellow("mcChbBusca_obj_escondidos");
		mcChbHace_torre = (Checkbox) this.getFellow("mcChbHace_torre");
		mcChbPasa_hojas_libro = (Checkbox) this.getFellow("mcChbHace_torre");
		mcChbAnticipa_salida = (Checkbox) this
				.getFellow("mcChbAnticipa_salida");
		mcChbTapa_bien_caja = (Checkbox) this.getFellow("mcChbTapa_bien_caja");
		mcChbHace_garabatos = (Checkbox) this.getFellow("mcChbHace_garabatos");
		mcChbHace_torres_varios = (Checkbox) this
				.getFellow("mcChbHace_torres_varios");
		mcChbEnsarta_mas_cuentas = (Checkbox) this
				.getFellow("mcChbEnsarta_mas_cuentas");
		mcChbCopia_linea_hor = (Checkbox) this
				.getFellow("mcChbCopia_linea_hor");
		mcChbSepara_obj_grandes = (Checkbox) this
				.getFellow("mcChbSepara_obj_grandes");
		mcChbFigura_humana_rudim = (Checkbox) this
				.getFellow("mcChbFigura_humana_rudim");
		mcChbCorta_papel_tijerea = (Checkbox) this
				.getFellow("mcChbCorta_papel_tijerea");
		mcChbCopia_cuadrado = (Checkbox) this.getFellow("mcChbCopia_cuadrado");
		mcChbDibuja_fig_humana = (Checkbox) this
				.getFellow("mcChbDibuja_fig_humana");
		mcChbAgrupa_col_form = (Checkbox) this
				.getFellow("mcChbAgrupa_col_form");
		mcChbDibuja_escalera = (Checkbox) this
				.getFellow("mcChbDibuja_escalera");
		mcChbAgrupa_col_form_tam = (Checkbox) this
				.getFellow("mcChbAgrupa_col_form_tam");
		mcChbReconstruye_escalera = (Checkbox) this
				.getFellow("mcChbReconstruye_escalera");
		mcChbDibuja_casa = (Checkbox) this.getFellow("mcChbDibuja_casa");
		mcChbSe_sobresalta_en_ruido = (Checkbox) this
				.getFellow("mcChbSe_sobresalta_en_ruido");
		mcChbBusca_sonido = (Checkbox) this.getFellow("mcChbBusca_sonido");
		mcChbDos_sonidos_guturales = (Checkbox) this
				.getFellow("mcChbDos_sonidos_guturales");
		mcChbBalbucea_con_persona = (Checkbox) this
				.getFellow("mcChbBalbucea_con_persona");
		mcChbCuatro_sonidos_dif = (Checkbox) this
				.getFellow("mcChbCuatro_sonidos_dif");
		mcChbRie_carcajadas = (Checkbox) this.getFellow("mcChbRie_carcajadas");
		mcChbReacciona_al_llamar = (Checkbox) this
				.getFellow("mcChbReacciona_al_llamar");
		mcChbPronuncia_silabas = (Checkbox) this
				.getFellow("mcChbPronuncia_silabas");
		mcChbHace_sonar_campana = (Checkbox) this
				.getFellow("mcChbHace_sonar_campana");
		mcChbPalabra_clara = (Checkbox) this.getFellow("mcChbPalabra_clara");
		mcChbNiega_con_cabeza = (Checkbox) this
				.getFellow("mcChbNiega_con_cabeza");
		mcChbLlama_a_madre = (Checkbox) this.getFellow("mcChbLlama_a_madre");
		mcChbEntiende_orden = (Checkbox) this.getFellow("mcChbEntiende_orden");
		mcChbReconoce_tres_obj = (Checkbox) this
				.getFellow("mcChbReconoce_tres_obj");
		mcChbCombina_dos_palabras = (Checkbox) this
				.getFellow("mcChbCombina_dos_palabras");
		mcChbReconoce_seis_obj = (Checkbox) this
				.getFellow("mcChbReconoce_seis_obj");
		mcChbNombra_cinco_obj = (Checkbox) this
				.getFellow("mcChbNombra_cinco_obj");
		mcChbFrase_tres_palab = (Checkbox) this
				.getFellow("mcChbFrase_tres_palab");
		mcChbVeinte_palabras = (Checkbox) this
				.getFellow("mcChbVeinte_palabras");
		mcChbDice_su_nombre = (Checkbox) this.getFellow("mcChbDice_su_nombre");
		mcChbConoce_alto_bajo = (Checkbox) this
				.getFellow("mcChbConoce_alto_bajo");
		mcChbUsa_oraciones_compl = (Checkbox) this
				.getFellow("mcChbUsa_oraciones_compl");
		mcChbDefine_cinco_obj = (Checkbox) this
				.getFellow("mcChbDefine_cinco_obj");
		mcChbRepite_tres_digit = (Checkbox) this
				.getFellow("mcChbRepite_tres_digit");
		mcChbDescribe_dibujos = (Checkbox) this
				.getFellow("mcChbDescribe_dibujos");
		mcChbCuenta_dedos_manos = (Checkbox) this
				.getFellow("mcChbCuenta_dedos_manos");
		mcChbDistingue_adel_atras = (Checkbox) this
				.getFellow("mcChbDistingue_adel_atras");
		mcChbNombra_colores = (Checkbox) this.getFellow("mcChbNombra_colores");
		mcChbExpresa_opiniones = (Checkbox) this
				.getFellow("mcChbExpresa_opiniones");
		mcChbConoce_izq_dere = (Checkbox) this
				.getFellow("mcChbConoce_izq_dere");
		mcChbConoce_dias_sem = (Checkbox) this
				.getFellow("mcChbConoce_dias_sem");
		mcChbSigue_mov_rostro = (Checkbox) this
				.getFellow("mcChbSigue_mov_rostro");
		mcChbReconoce_la_madre = (Checkbox) this
				.getFellow("mcChbReconoce_la_madre");
		mcChbSonrie_acariciarlo = (Checkbox) this
				.getFellow("mcChbSonrie_acariciarlo");
		mcChbVoltea_cuando_sele_habl = (Checkbox) this
				.getFellow("mcChbVoltea_cuando_sele_habl");
		mcChbCoge_manos_del_exam = (Checkbox) this
				.getFellow("mcChbCoge_manos_del_exam");
		mcChbAcepta_coge_jugue = (Checkbox) this
				.getFellow("mcChbAcepta_coge_jugue");
		mcChbPone_atencion_conv = (Checkbox) this
				.getFellow("mcChbPone_atencion_conv");
		mcChbAyuda_sostener_taza = (Checkbox) this
				.getFellow("mcChbAyuda_sostener_taza");
		mcChbReacciona_imagen = (Checkbox) this
				.getFellow("mcChbReacciona_imagen");
		mcChbImita_aplauso = (Checkbox) this.getFellow("mcChbImita_aplauso");
		mcChbEntrega_juguete = (Checkbox) this
				.getFellow("mcChbEntrega_juguete");
		mcChbPide_un_juguete = (Checkbox) this
				.getFellow("mcChbPide_un_juguete");
		mcChbBeb_en_taza = (Checkbox) this.getFellow("mcChbBeb_en_taza");
		mcChbSeniala_una_prenda = (Checkbox) this
				.getFellow("mcChbSeniala_una_prenda");
		mcChbSeniala_dos_partes_cuerp = (Checkbox) this
				.getFellow("mcChbSeniala_dos_partes_cuerp");
		mcChbAvisa_higiene_personal = (Checkbox) this
				.getFellow("mcChbAvisa_higiene_personal");
		mcChbSeniala_cinco_partes = (Checkbox) this
				.getFellow("mcChbSeniala_cinco_partes");
		mcChbTrata_contar_exper = (Checkbox) this
				.getFellow("mcChbTrata_contar_exper");
		mcChbControl_diurno_orina = (Checkbox) this
				.getFellow("mcChbControl_diurno_orina");
		mcChbDiferencia_ninio_ninia = (Checkbox) this
				.getFellow("mcChbDiferencia_ninio_ninia");
		mcChbDice_papa_mama = (Checkbox) this.getFellow("mcChbDice_papa_mama");
		mcChbSe_bania_solo = (Checkbox) this.getFellow("mcChbSe_bania_solo");
		mcChbPuede_desvestirse = (Checkbox) this
				.getFellow("mcChbPuede_desvestirse");
		mcChbComparte_juego = (Checkbox) this.getFellow("mcChbComparte_juego");
		mcChbTiene_amigo_espec = (Checkbox) this
				.getFellow("mcChbTiene_amigo_espec");
		mcChbViste_desviste_solo = (Checkbox) this
				.getFellow("mcChbViste_desviste_solo");
		mcChbSabe_cuantos_anio_tien = (Checkbox) this
				.getFellow("mcChbSabe_cuantos_anio_tien");
		mcChbOrganiza_juegos = (Checkbox) this
				.getFellow("mcChbOrganiza_juegos");
		mcChbHace_mandados = (Checkbox) this.getFellow("mcChbHace_mandados");
		mcChbReconoce_nombre_verda = (Checkbox) this
				.getFellow("mcChbReconoce_nombre_verda");
		mcChbComenta_vida_famil = (Checkbox) this
				.getFellow("mcChbComenta_vida_famil");
		mcChbAnticipa_salida = (Checkbox) this
				.getFellow("mcChbAnticipa_salida");
		mcChbTapa_bien_caja = (Checkbox) this.getFellow("mcChbTapa_bien_caja");
		mcChbHace_garabatos = (Checkbox) this.getFellow("mcChbHace_garabatos");
		mcChbHace_torres_varios = (Checkbox) this
				.getFellow("mcChbHace_torres_varios");
		mcChbEnsarta_mas_cuentas = (Checkbox) this
				.getFellow("mcChbEnsarta_mas_cuentas");
		mcChbCopia_linea_hor = (Checkbox) this
				.getFellow("mcChbCopia_linea_hor");
		mcChbSepara_obj_grandes = (Checkbox) this
				.getFellow("mcChbSepara_obj_grandes");
		mcChbFigura_humana_rudim = (Checkbox) this
				.getFellow("mcChbFigura_humana_rudim");
		mcChbCorta_papel_tijerea = (Checkbox) this
				.getFellow("mcChbCorta_papel_tijerea");
		mcChbCopia_cuadrado = (Checkbox) this.getFellow("mcChbCopia_cuadrado");
		mcChbDibuja_fig_humana = (Checkbox) this
				.getFellow("mcChbDibuja_fig_humana");
		mcChbAgrupa_col_form = (Checkbox) this
				.getFellow("mcChbAgrupa_col_form");
		mcChbDibuja_escalera = (Checkbox) this
				.getFellow("mcChbDibuja_escalera");
		mcChbAgrupa_col_form_tam = (Checkbox) this
				.getFellow("mcChbAgrupa_col_form_tam");
		mcChbReconstruye_escalera = (Checkbox) this
				.getFellow("mcChbReconstruye_escalera");
		mcChbDibuja_casa = (Checkbox) this.getFellow("mcChbDibuja_casa");
		mcChbSe_sobresalta_en_ruido = (Checkbox) this
				.getFellow("mcChbSe_sobresalta_en_ruido");
		mcChbBusca_sonido = (Checkbox) this.getFellow("mcChbBusca_sonido");
		mcChbDos_sonidos_guturales = (Checkbox) this
				.getFellow("mcChbDos_sonidos_guturales");
		mcChbBalbucea_con_persona = (Checkbox) this
				.getFellow("mcChbBalbucea_con_persona");
		mcChbCuatro_sonidos_dif = (Checkbox) this
				.getFellow("mcChbCuatro_sonidos_dif");
		mcChbRie_carcajadas = (Checkbox) this.getFellow("mcChbRie_carcajadas");
		mcChbReacciona_al_llamar = (Checkbox) this
				.getFellow("mcChbReacciona_al_llamar");
		mcChbPronuncia_silabas = (Checkbox) this
				.getFellow("mcChbPronuncia_silabas");
		mcChbHace_sonar_campana = (Checkbox) this
				.getFellow("mcChbHace_sonar_campana");
		mcChbPalabra_clara = (Checkbox) this.getFellow("mcChbPalabra_clara");
		mcChbNiega_con_cabeza = (Checkbox) this
				.getFellow("mcChbNiega_con_cabeza");
		mcChbLlama_a_madre = (Checkbox) this.getFellow("mcChbLlama_a_madre");
		mcChbEntiende_orden = (Checkbox) this.getFellow("mcChbEntiende_orden");
		mcChbReconoce_tres_obj = (Checkbox) this
				.getFellow("mcChbReconoce_tres_obj");
		mcChbCombina_dos_palabras = (Checkbox) this
				.getFellow("mcChbCombina_dos_palabras");
		mcChbReconoce_seis_obj = (Checkbox) this
				.getFellow("mcChbReconoce_seis_obj");
		mcChbNombra_cinco_obj = (Checkbox) this
				.getFellow("mcChbNombra_cinco_obj");
		mcChbFrase_tres_palab = (Checkbox) this
				.getFellow("mcChbFrase_tres_palab");
		mcChbVeinte_palabras = (Checkbox) this
				.getFellow("mcChbVeinte_palabras");
		mcChbDice_su_nombre = (Checkbox) this.getFellow("mcChbDice_su_nombre");
		mcChbConoce_alto_bajo = (Checkbox) this
				.getFellow("mcChbConoce_alto_bajo");
		mcChbUsa_oraciones_compl = (Checkbox) this
				.getFellow("mcChbUsa_oraciones_compl");
		mcChbDefine_cinco_obj = (Checkbox) this
				.getFellow("mcChbDefine_cinco_obj");
		mcChbRepite_tres_digit = (Checkbox) this
				.getFellow("mcChbRepite_tres_digit");
		mcChbDescribe_dibujos = (Checkbox) this
				.getFellow("mcChbDescribe_dibujos");
		mcChbCuenta_dedos_manos = (Checkbox) this
				.getFellow("mcChbCuenta_dedos_manos");
		mcChbDistingue_adel_atras = (Checkbox) this
				.getFellow("mcChbDistingue_adel_atras");
		mcChbNombra_colores = (Checkbox) this.getFellow("mcChbNombra_colores");
		mcChbExpresa_opiniones = (Checkbox) this
				.getFellow("mcChbExpresa_opiniones");
		mcChbConoce_izq_dere = (Checkbox) this
				.getFellow("mcChbConoce_izq_dere");
		mcChbConoce_dias_sem = (Checkbox) this
				.getFellow("mcChbConoce_dias_sem");
		mcChbSigue_mov_rostro = (Checkbox) this
				.getFellow("mcChbSigue_mov_rostro");
		mcChbReconoce_la_madre = (Checkbox) this
				.getFellow("mcChbReconoce_la_madre");
		mcChbSonrie_acariciarlo = (Checkbox) this
				.getFellow("mcChbSonrie_acariciarlo");
		mcChbVoltea_cuando_sele_habl = (Checkbox) this
				.getFellow("mcChbVoltea_cuando_sele_habl");
		mcChbCoge_manos_del_exam = (Checkbox) this
				.getFellow("mcChbCoge_manos_del_exam");
		mcChbAcepta_coge_jugue = (Checkbox) this
				.getFellow("mcChbAcepta_coge_jugue");
		mcChbPone_atencion_conv = (Checkbox) this
				.getFellow("mcChbPone_atencion_conv");
		mcChbAyuda_sostener_taza = (Checkbox) this
				.getFellow("mcChbAyuda_sostener_taza");
		mcChbReacciona_imagen = (Checkbox) this
				.getFellow("mcChbReacciona_imagen");
		mcChbImita_aplauso = (Checkbox) this.getFellow("mcChbImita_aplauso");
		mcChbEntrega_juguete = (Checkbox) this
				.getFellow("mcChbEntrega_juguete");
		mcChbPide_un_juguete = (Checkbox) this
				.getFellow("mcChbPide_un_juguete");
		mcChbBeb_en_taza = (Checkbox) this.getFellow("mcChbBeb_en_taza");
		mcChbSeniala_una_prenda = (Checkbox) this
				.getFellow("mcChbSeniala_una_prenda");
		mcChbSeniala_dos_partes_cuerp = (Checkbox) this
				.getFellow("mcChbSeniala_dos_partes_cuerp");
		mcChbAvisa_higiene_personal = (Checkbox) this
				.getFellow("mcChbAvisa_higiene_personal");
		mcChbSeniala_cinco_partes = (Checkbox) this
				.getFellow("mcChbSeniala_cinco_partes");
		mcChbTrata_contar_exper = (Checkbox) this
				.getFellow("mcChbTrata_contar_exper");
		mcChbControl_diurno_orina = (Checkbox) this
				.getFellow("mcChbControl_diurno_orina");
		mcChbDiferencia_ninio_ninia = (Checkbox) this
				.getFellow("mcChbDiferencia_ninio_ninia");
		mcChbDice_papa_mama = (Checkbox) this.getFellow("mcChbDice_papa_mama");
		mcChbSe_bania_solo = (Checkbox) this.getFellow("mcChbSe_bania_solo");
		mcChbPuede_desvestirse = (Checkbox) this
				.getFellow("mcChbPuede_desvestirse");
		mcChbComparte_juego = (Checkbox) this.getFellow("mcChbComparte_juego");
		mcChbTiene_amigo_espec = (Checkbox) this
				.getFellow("mcChbTiene_amigo_espec");
		mcChbViste_desviste_solo = (Checkbox) this
				.getFellow("mcChbViste_desviste_solo");
		mcChbSabe_cuantos_anio_tien = (Checkbox) this
				.getFellow("mcChbSabe_cuantos_anio_tien");
		mcChbOrganiza_juegos = (Checkbox) this
				.getFellow("mcChbOrganiza_juegos");
		mcChbHace_mandados = (Checkbox) this.getFellow("mcChbHace_mandados");
		mcChbReconoce_nombre_verda = (Checkbox) this
				.getFellow("mcChbReconoce_nombre_verda");
		mcChbComenta_vida_famil = (Checkbox) this
				.getFellow("mcChbComenta_vida_famil");
		mcTbxMotricidad_gruesa = (Intbox) this
				.getFellow("mcTbxMotricidad_gruesa");
		mcTbxMitricidad_adaptativa = (Intbox) this
				.getFellow("mcTbxMitricidad_adaptativa");
		mcTbxAudicion_leng = (Intbox) this.getFellow("mcTbxAudicion_leng");
		mcTbxPersonal_social = (Intbox) this.getFellow("mcTbxPersonal_social");
		mcTbxTotal = (Intbox) this.getFellow("mcTbxTotal");
		mcTbxObservacion = (Textbox) this.getFellow("mcTbxObservacion");
		
		lbAlertaMotricidad1 = (Label) this.getFellow("lbAlertaMotricidad1");
		lbAlertaMotricidad2 = (Label) this.getFellow("lbAlertaMotricidad2");
		lbAlertaMotricidad3 = (Label) this.getFellow("lbAlertaMotricidad3");
		lbAlertaMotricidad4 = (Label) this.getFellow("lbAlertaMotricidad4");
		lbAlertaAdaptativa1 = (Label) this.getFellow("lbAlertaAdaptativa1");
		lbAlertaAdaptativa2 = (Label) this.getFellow("lbAlertaAdaptativa2");
		lbAlertaAdaptativa3 = (Label) this.getFellow("lbAlertaAdaptativa3");
		lbAlertaAdaptativa4 = (Label) this.getFellow("lbAlertaAdaptativa4");
		lbAlertalenguaje1 = (Label) this.getFellow("lbAlertalenguaje1");
		lbAlertalenguaje2 = (Label) this.getFellow("lbAlertalenguaje2");
		lbAlertalenguaje3 = (Label) this.getFellow("lbAlertalenguaje3");
		lbAlertalenguaje4 = (Label) this.getFellow("lbAlertalenguaje4");
		lbAlertaPersonal1 = (Label) this.getFellow("lbAlertaPersonal1");
		lbAlertaPersonal2 = (Label) this.getFellow("lbAlertaPersonal2");
		lbAlertaPersonal3 = (Label) this.getFellow("lbAlertaPersonal3");
		lbAlertaPersonal4 = (Label) this.getFellow("lbAlertaPersonal4");
	}

	public void mostrarColorRandoEdad4_6() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		
		seleccionarTodosChecks(rowEscala111);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala4_6.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad7_9() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		
		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala7_9.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad10_12() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala10_12.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad12_18() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala13_18.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad19_24() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		
		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala19_24.setStyle("background-color:red;");

	}

	public void mostrarColorRandoEdad25_36() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala25_36.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad37_48() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala37_48.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad49_60() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);
		seleccionarTodosChecks(rowEscala49_60);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		seleccionarTodosChecks(rowEscala118);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
				
		rowEscala49_60.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad60_72() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);
		seleccionarTodosChecks(rowEscala49_60);
		seleccionarTodosChecks(rowEscala61_72);
		
		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		seleccionarTodosChecks(rowEscala118);
		seleccionarTodosChecks(rowEscala119);

		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala61_72.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad4_6MFA() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		
		seleccionarTodosChecks(rowEscala111);

		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala4_6.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad7_9MFA() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala7_9.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad10_12MFA() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala10_12.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad12_18MFA() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala13_18.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad19_24MFA() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala19_24.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad25_36MFA() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala25_36.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad37_48MFA() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala37_48.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad49_60MFA() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);
		seleccionarTodosChecks(rowEscala49_60);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		seleccionarTodosChecks(rowEscala118);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala49_60.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad60_72MFA() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);
		seleccionarTodosChecks(rowEscala49_60);
		seleccionarTodosChecks(rowEscala61_72);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		seleccionarTodosChecks(rowEscala118);
		seleccionarTodosChecks(rowEscala119);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala61_72.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad4_6AL() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		
		seleccionarTodosChecks(rowEscala111);
				
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala111.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad7_9AL() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
				
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala112.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad10_12AL() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala113.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad12_18AL() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala114.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad19_24AL() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala115.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad25_36AL() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala116.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad37_48AL() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala117.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad49_60AL() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);
		seleccionarTodosChecks(rowEscala49_60);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		seleccionarTodosChecks(rowEscala118);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala118.setStyle("background-color:red;");

	}

	public void mostrarColorRandoEdad60_72AL() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);
		seleccionarTodosChecks(rowEscala49_60);
		seleccionarTodosChecks(rowEscala61_72);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		seleccionarTodosChecks(rowEscala118);
		seleccionarTodosChecks(rowEscala119);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala119.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad4_6PS() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);

		seleccionarTodosChecks(rowEscala111);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala111.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad7_9PS() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala112.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad10_12PS() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala113.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad12_18PS() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala114.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad19_24PS() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		
		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala115.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad25_36PS() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala116.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad37_48PS() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala117.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad49_60PS() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);
		seleccionarTodosChecks(rowEscala49_60);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		seleccionarTodosChecks(rowEscala118);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala118.setStyle("background-color:red;");
	}

	public void mostrarColorRandoEdad60_72PS() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);
		seleccionarTodosChecks(rowEscala4_6);
		seleccionarTodosChecks(rowEscala7_9);
		seleccionarTodosChecks(rowEscala10_12);
		seleccionarTodosChecks(rowEscala13_18);
		seleccionarTodosChecks(rowEscala19_24);
		seleccionarTodosChecks(rowEscala25_36);
		seleccionarTodosChecks(rowEscala37_48);
		seleccionarTodosChecks(rowEscala49_60);
		seleccionarTodosChecks(rowEscala61_72);

		seleccionarTodosChecks(rowEscala111);
		seleccionarTodosChecks(rowEscala112);
		seleccionarTodosChecks(rowEscala113);
		seleccionarTodosChecks(rowEscala114);
		seleccionarTodosChecks(rowEscala115);
		seleccionarTodosChecks(rowEscala116);
		seleccionarTodosChecks(rowEscala117);
		seleccionarTodosChecks(rowEscala118);
		seleccionarTodosChecks(rowEscala119);
		
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala119.setStyle("background-color:red;");
	}

	public void calcularEscalaDesarrollo(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(rowsEscala, subt);

		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB04")) {
			subtotalNum4 = 0;
			calcularSubtotales(rowsEscala, subt);
		}
		mostrarTotal();
		// rowTotal1.setVisible(true);

	}

	public void calcularEscalaDesarrollo2(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(rowsEscala, subt);

		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB04")) {
			subtotalNum4 = 0;
			calcularSubtotales(rowsEscala, subt);
		}

		mostrarTotal();
	}

	public void calcularEscalaDesarrollo3(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB04")) {
			subtotalNum4 = 0;
			calcularSubtotales(rowsEscala, subt);
		}

		mostrarTotal();

		// rowTotal3.setVisible(true);
	}

	public void calcularEscalaDesarrollo4(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(rowsEscala, subt);

		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB04")) {
			subtotalNum4 = 0;
			calcularSubtotales(rowsEscala, subt);
		}
		mostrarTotal();

		// rowTotal4.setVisible(true);
	}

	public void calcularEscalaDesarrollo5(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(rowsEscala, subt);

		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB04")) {
			subtotalNum4 = 0;
			calcularSubtotales(rowsEscala, subt);
		}
		mostrarTotal();

		// rowTotal5.setVisible(true);
	}

	public void calcularEscalaDesarrollo6(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(rowsEscala, subt);

		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB04")) {
			subtotalNum4 = 0;
			calcularSubtotales(rowsEscala, subt);
		}
		mostrarTotal();

		// rowTotal6.setVisible(true);
	}

	public void calcularEscalaDesarrollo7(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(rowsEscala, subt);

		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB04")) {
			subtotalNum4 = 0;
			calcularSubtotales(rowsEscala, subt);
		}
		mostrarTotal();
		// rowTotal7.setVisible(true);
	}

	public void calcularEscalaDesarrollo8(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(rowsEscala, subt);

		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB04")) {
			subtotalNum4 = 0;
			calcularSubtotales(rowsEscala, subt);
		}
		mostrarTotal();

		// rowTotal8.setVisible(true);
	}

	public void calcularEscalaDesarrollo9(String subt) {
		if (subt.contains("SUB01")) {
			subtotalNum1 = 0;
			calcularSubtotales(rowsEscala, subt);

		} else if (subt.contains("SUB02")) {
			subtotalNum2 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB03")) {
			subtotalNum3 = 0;
			calcularSubtotales(rowsEscala, subt);
		} else if (subt.contains("SUB04")) {
			subtotalNum4 = 0;
			calcularSubtotales(rowsEscala, subt);
		}
		mostrarTotal();

		// rowTotal9.setVisible(true);
	}

	private void calcularSubtotales(Component component, String subt) {

		List<Component> listado = component.getChildren();
		for (Component component2 : listado) {
			if (component2 instanceof Checkbox) {
				if (!(component2 instanceof Radio)) {
					if (((Checkbox) component2).isChecked()) {
						String valor = ((Checkbox) component2).getValue();
						StringTokenizer stringTokenizer = new StringTokenizer(
								valor, "-");
						if (stringTokenizer.countTokens() == 2) {
							String subtotal = stringTokenizer.nextToken();
							String numero = stringTokenizer.nextToken();
							if (subt.contains(subtotal)
									&& subt.contains("SUB01")) {
								Integer numeroInt = new Integer(numero.trim());
								subtotalNum1 += numeroInt;
							} else if (subt.contains(subtotal)
									&& subt.contains("SUB02")) {
								Integer numeroInt = new Integer(numero.trim());
								subtotalNum2 += numeroInt;
							} else if (subt.contains(subtotal)
									&& subt.contains("SUB03")) {
								Integer numeroInt = new Integer(numero.trim());
								subtotalNum3 += numeroInt;
							} else if (subt.contains(subtotal)
									&& subt.contains("SUB04")) {
								Integer numeroInt = new Integer(numero.trim());
								subtotalNum4 += numeroInt;
							}
						}
					}
				}
			}
			if (!component2.getChildren().isEmpty()) {
				calcularSubtotales(component2, subt);
			}

		}
	}

	public void mostrarColorFila1Edad() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);

		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala1.setStyle("background-color:red;");
		rowEscala2.setStyle("background-color:red;");
	}

	public void mostrarColorFila11Edad() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);

		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala1.setStyle("background-color:red;");
		rowEscala2.setStyle("background-color:red;");

	}

	public void mostrarColorFila12Edad() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);

		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala3.setStyle("background-color:red;");
		rowEscala4.setStyle("background-color:red;");

	}

	public void mostrarColorFila13Edad() {
		seleccionarTodosChecks(rowEscala1);
		seleccionarTodosChecks(rowEscala2);
		seleccionarTodosChecks(rowEscala3);
		seleccionarTodosChecks(rowEscala4);

		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
		
		rowEscala3.setStyle("background-color:red;");
		rowEscala4.setStyle("background-color:red;");

	}

	public void mostrarColorFila14Edad() {
		try {
			String POSICION_ALERTA = "before_end";
			Integer TIEMPO_ALERTA = 4000;
			Integer valor1 = ConvertidorDatosUtil.convertirDatot(mcTbxTotal
					.getValue().toString().trim());
			if (valor1 > 0) {
				if (valor1 >= 0 && valor1 <= 6) {
					lbAlertaTotal1.setVisible(true);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
					String mensaje = "Remitir el niño para valoracion médica";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcTbxTotal,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
				} else if (valor1 >= 7 && valor1 <= 13) {
					lbAlertaTotal2.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 14 && valor1 <= 22) {
					lbAlertaTotal3.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 23 && valor1 <= 35) {
					lbAlertaTotal4.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal2.setVisible(false);
				}
			}
		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public void mostrarColorFila14Edad4_6() {
		try {
			String POSICION_ALERTA = "before_end";
			Integer TIEMPO_ALERTA = 4000;
			Integer valor1 = ConvertidorDatosUtil.convertirDatot(mcTbxTotal
					.getValue().toString().trim());
			if (valor1 > 0) {
				if (valor1 >= 0 && valor1 <= 19) {
					lbAlertaTotal1.setVisible(true);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
					String mensaje = "Remitir el niño para valoracion médica";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcTbxTotal,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
					mcTbxTotal.setTooltiptext(mensaje);
				} else if (valor1 >= 20 && valor1 <= 27) {
					lbAlertaTotal2.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 28 && valor1 <= 34) {
					lbAlertaTotal3.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 35 && valor1 <= 90) {
					lbAlertaTotal4.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal2.setVisible(false);
				}
			}
		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public void mostrarColorFila14Edad7_9() {
		try {
			String POSICION_ALERTA = "before_end";
			Integer TIEMPO_ALERTA = 4000;
			Integer valor1 = ConvertidorDatosUtil.convertirDatot(mcTbxTotal
					.getValue().toString().trim());
			if (valor1 > 0) {
				if (valor1 >= 0 && valor1 <= 31) {
					lbAlertaTotal1.setVisible(true);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
					String mensaje = "Remitir el niño para valoracion médica";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcTbxTotal,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
					mcTbxTotal.setTooltiptext(mensaje);
				} else if (valor1 >= 32 && valor1 <= 39) {
					lbAlertaTotal2.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 40 && valor1 <= 48) {
					lbAlertaTotal3.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 49 && valor1 <= 90) {
					lbAlertaTotal4.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal2.setVisible(false);
				}
			}
		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public void mostrarColorFila14Edad10_12() {
		try {
			String POSICION_ALERTA = "before_end";
			Integer TIEMPO_ALERTA = 4000;
			Integer valor1 = ConvertidorDatosUtil.convertirDatot(mcTbxTotal
					.getValue().toString().trim());
			if (valor1 > 0) {
				if (valor1 >= 0 && valor1 <= 42) {
					lbAlertaTotal1.setVisible(true);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
					String mensaje = " Remitir el niño para valoracion médica";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcTbxTotal,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
					mcTbxTotal.setTooltiptext(mensaje);
				} else if (valor1 >= 43 && valor1 <= 49) {
					lbAlertaTotal2.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 50 && valor1 <= 56) {
					lbAlertaTotal3.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 57 && valor1 <= 90) {
					lbAlertaTotal4.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal2.setVisible(false);
				}
			}
		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public void mostrarColorFila14Edad13_18() {
		try {
			String POSICION_ALERTA = "before_end";
			Integer TIEMPO_ALERTA = 4000;
			Integer valor1 = ConvertidorDatosUtil.convertirDatot(mcTbxTotal
					.getValue().toString().trim());
			if (valor1 > 0) {
				if (valor1 >= 0 && valor1 <= 51) {
					lbAlertaTotal1.setVisible(true);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
					String mensaje = "Remitir el niño para valoracion médica";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcTbxTotal,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
					mcTbxTotal.setTooltiptext(mensaje);
				} else if (valor1 >= 52 && valor1 <= 60) {
					lbAlertaTotal2.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 61 && valor1 <= 69) {
					lbAlertaTotal3.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 70 && valor1 <= 150) {
					lbAlertaTotal4.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal2.setVisible(false);
				}
			}
		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public void mostrarColorFila14Edad19_24() {
		try {
			String POSICION_ALERTA = "before_end";
			Integer TIEMPO_ALERTA = 4000;
			Integer valor1 = ConvertidorDatosUtil.convertirDatot(mcTbxTotal
					.getValue().toString().trim());
			if (valor1 > 0) {
				if (valor1 >= 0 && valor1 <= 61) {
					lbAlertaTotal1.setVisible(true);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
					String mensaje = " Remitir el niño para valoracion médica";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcTbxTotal,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
					mcTbxTotal.setTooltiptext(mensaje);
				} else if (valor1 >= 62 && valor1 <= 71) {
					lbAlertaTotal2.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 72 && valor1 <= 83) {
					lbAlertaTotal3.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 84 && valor1 <= 150) {
					lbAlertaTotal4.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal2.setVisible(false);
				}
			}
		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public void mostrarColorFila14Edad25_36() {
		try {
			String POSICION_ALERTA = "before_end";
			Integer TIEMPO_ALERTA = 4000;
			Integer valor1 = ConvertidorDatosUtil.convertirDatot(mcTbxTotal
					.getValue().toString().trim());
			if (valor1 > 0) {
				if (valor1 >= 0 && valor1 <= 74) {
					lbAlertaTotal1.setVisible(true);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
					String mensaje = " Remitir el niño para valoracion médica";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcTbxTotal,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
					mcTbxTotal.setTooltiptext(mensaje);
				} else if (valor1 >= 75 && valor1 <= 86) {
					lbAlertaTotal2.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 87 && valor1 <= 100) {
					lbAlertaTotal3.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 101 && valor1 <= 150) {
					lbAlertaTotal4.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal2.setVisible(false);
				}
			}
		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public void mostrarColorFila14Edad37_48() {
		try {
			String POSICION_ALERTA = "before_end";
			Integer TIEMPO_ALERTA = 4000;
			Integer valor1 = ConvertidorDatosUtil.convertirDatot(mcTbxTotal
					.getValue().toString().trim());
			if (valor1 > 0) {
				if (valor1 >= 0 && valor1 <= 101) {
					lbAlertaTotal1.setVisible(true);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
					String mensaje = " Remitir  el niño para valoracion médica";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcTbxTotal,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
					mcTbxTotal.setTooltiptext(mensaje);
				} else if (valor1 >= 102 && valor1 <= 114) {
					lbAlertaTotal2.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 115 && valor1 <= 120) {
					lbAlertaTotal3.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 121 && valor1 <= 150) {
					lbAlertaTotal4.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal2.setVisible(false);
				}
			}
		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public void mostrarColorFila14Edad49_60() {
		try {
			String POSICION_ALERTA = "before_end";
			Integer TIEMPO_ALERTA = 4000;
			Integer valor1 = ConvertidorDatosUtil.convertirDatot(mcTbxTotal
					.getValue().toString().trim());
			if (valor1 > 0) {
				if (valor1 >= 0 && valor1 <= 51) {
					lbAlertaTotal1.setVisible(true);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
					String mensaje = "Remitir el niño para valoracion médica";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcTbxTotal,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
					mcTbxTotal.setTooltiptext(mensaje);
				} else if (valor1 >= 52 && valor1 <= 60) {
					lbAlertaTotal2.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 61 && valor1 <= 69) {
					lbAlertaTotal3.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal2.setVisible(false);
					lbAlertaTotal4.setVisible(false);
				} else if (valor1 >= 70 && valor1 <= 150) {
					lbAlertaTotal4.setVisible(true);
					lbAlertaTotal1.setVisible(false);
					lbAlertaTotal3.setVisible(false);
					lbAlertaTotal2.setVisible(false);
				}
			}
		} catch (Exception e) {
			log.error("" + e.getMessage(), e);
		}
	}

	public Escala_del_desarrollo obtenerEscala() throws Exception {
		escala_del_desarrollo = new Escala_del_desarrollo();
		escala_del_desarrollo.setCodigo_empresa(zkWindow.codigo_empresa);
		escala_del_desarrollo.setCodigo_sucursal(zkWindow.codigo_sucursal);
		escala_del_desarrollo.setPatea_vigorosamente(mcChbPatea_vigorosamente
				.isChecked());
		escala_del_desarrollo.setLevanta_cabeza_prona(mcChbLevanta_cabeza_prona
				.isChecked());
		escala_del_desarrollo.setLevanta_cab_pecho(mcChbLevanta_cab_pecho
				.isChecked());
		escala_del_desarrollo.setSostiene_cabeza(mcChbSostiene_cabeza
				.isChecked());
		escala_del_desarrollo.setControl_de_cabeza(mcChbControl_de_cabeza
				.isChecked());
		escala_del_desarrollo
				.setVoltea_de_lado(mcChbVoltea_de_lado.isChecked());
		escala_del_desarrollo.setIntenta_sentarse(mcChbIntenta_sentarse
				.isChecked());
		escala_del_desarrollo.setSostiene_sentado(mcChbSostiene_sentado
				.isChecked());
		escala_del_desarrollo.setSe_arrastra(mcChbSe_arrastra.isChecked());
		escala_del_desarrollo
				.setSe_sienta_solo(mcChbSe_sienta_solo.isChecked());
		escala_del_desarrollo.setGatea_bien(mcChbGatea_bien.isChecked());
		escala_del_desarrollo.setSe_agarra_sotiene(mcChbSe_agarra_sotiene
				.isChecked());
		escala_del_desarrollo.setSe_para_solo(mcChbSe_para_solo.isChecked());
		escala_del_desarrollo.setDa_pasitos_solo(mcChbDa_pasitos_solo
				.isChecked());
		escala_del_desarrollo.setCamina_solo_bien(mcChbCamina_solo_bien
				.isChecked());
		escala_del_desarrollo.setCorre(mcChbCorre.isChecked());
		escala_del_desarrollo.setPatea_la_pelota(mcChbPatea_la_pelota
				.isChecked());
		escala_del_desarrollo.setLanza_la_pelota(mcChbLanza_la_pelota
				.isChecked());
		escala_del_desarrollo.setSalta_en_dos_pies(mcChbSalta_en_dos_pies
				.isChecked());
		escala_del_desarrollo.setSe_empina(mcChbSe_empina.isChecked());
		escala_del_desarrollo.setSe_levanta_sin_usar(mcChbSe_levanta_sin_usar
				.isChecked());
		escala_del_desarrollo.setCamina_hacia_atras(mcChbCamina_hacia_atras
				.isChecked());
		escala_del_desarrollo.setCamina_en_puntas(mcChbCamina_en_puntas
				.isChecked());
		escala_del_desarrollo.setSe_para_en_pies(mcChbSe_para_en_pies
				.isChecked());
		escala_del_desarrollo.setLanza_agarra_pelota(mcChbLanza_agarra_pelota
				.isChecked());
		escala_del_desarrollo.setCamina_en_linea(mcChbCamina_en_linea
				.isChecked());
		escala_del_desarrollo.setTres_pasos_en_pies(mcChbTres_pasos_en_pies
				.isChecked());
		escala_del_desarrollo.setHace_rebotar_pelota(mcChbHace_rebotar_pelota
				.isChecked());
		escala_del_desarrollo.setSalta_pies_juntillas(mcChbSalta_pies_juntillas
				.isChecked());
		escala_del_desarrollo.setHace_caballitos(mcChbHace_caballitos
				.isChecked());
		escala_del_desarrollo.setSalta_alto(mcChbSalta_alto.isChecked());
		escala_del_desarrollo.setSigue_mov_horiz(mcChbSigue_mov_horiz
				.isChecked());
		escala_del_desarrollo.setAbre_mira_manos(mcChbAbre_mira_manos
				.isChecked());
		escala_del_desarrollo.setSostiene_obj_mano(mcChbSostiene_obj_mano
				.isChecked());
		escala_del_desarrollo.setSe_lleva_obj_boca(mcChbSe_lleva_obj_boca
				.isChecked());
		escala_del_desarrollo.setAgarra_objetos_volun(mcChbAgarra_objetos_volun
				.isChecked());
		escala_del_desarrollo
				.setSostiene_obj_cada_mano(mcChbSostiene_obj_cada_mano
						.isChecked());
		escala_del_desarrollo.setPasa_obj_a_manos(mcChbPasa_obj_a_manos
				.isChecked());
		escala_del_desarrollo.setManipula_varios_obj(mcChbManipula_varios_obj
				.isChecked());
		escala_del_desarrollo.setAgarra_obj_pequenio(mcChbAgarra_obj_pequenio
				.isChecked());
		escala_del_desarrollo.setAgarra_cubo(mcChbAgarra_cubo.isChecked());
		escala_del_desarrollo.setMete_saca_obj(mcChbMete_saca_obj.isChecked());
		escala_del_desarrollo.setAgarra_tercer_obj(mcChbAgarra_tercer_obj
				.isChecked());
		escala_del_desarrollo.setBusca_obj_escondidos(mcChbBusca_obj_escondidos
				.isChecked());
		escala_del_desarrollo.setHace_torre(mcChbHace_torre.isChecked());
		escala_del_desarrollo.setPasa_hojas_libro(mcChbPasa_hojas_libro
				.isChecked());
		escala_del_desarrollo.setAnticipa_salida(mcChbAnticipa_salida
				.isChecked());
		escala_del_desarrollo
				.setTapa_bien_caja(mcChbTapa_bien_caja.isChecked());
		escala_del_desarrollo
				.setHace_garabatos(mcChbHace_garabatos.isChecked());
		escala_del_desarrollo.setHace_torres_varios(mcChbHace_torres_varios
				.isChecked());
		escala_del_desarrollo.setEnsarta_mas_cuentas(mcChbEnsarta_mas_cuentas
				.isChecked());
		escala_del_desarrollo.setCopia_linea_hor(mcChbCopia_linea_hor
				.isChecked());
		escala_del_desarrollo.setSepara_obj_grandes(mcChbSepara_obj_grandes
				.isChecked());
		escala_del_desarrollo.setFigura_humana_rudim(mcChbFigura_humana_rudim
				.isChecked());
		escala_del_desarrollo.setCorta_papel_tijerea(mcChbCorta_papel_tijerea
				.isChecked());
		escala_del_desarrollo
				.setCopia_cuadrado(mcChbCopia_cuadrado.isChecked());
		escala_del_desarrollo.setDibuja_fig_humana(mcChbDibuja_fig_humana
				.isChecked());
		escala_del_desarrollo.setAgrupa_col_form(mcChbAgrupa_col_form
				.isChecked());
		escala_del_desarrollo.setDibuja_escalera(mcChbDibuja_escalera
				.isChecked());
		escala_del_desarrollo.setAgrupa_col_form_tam(mcChbAgrupa_col_form_tam
				.isChecked());
		escala_del_desarrollo.setReconstruye_escalera(mcChbReconstruye_escalera
				.isChecked());
		escala_del_desarrollo.setDibuja_casa(mcChbDibuja_casa.isChecked());
		escala_del_desarrollo
				.setSe_sobresalta_en_ruido(mcChbSe_sobresalta_en_ruido
						.isChecked());
		escala_del_desarrollo.setBusca_sonido(mcChbBusca_sonido.isChecked());
		escala_del_desarrollo
				.setDos_sonidos_guturales(mcChbDos_sonidos_guturales
						.isChecked());
		escala_del_desarrollo.setBalbucea_con_persona(mcChbBalbucea_con_persona
				.isChecked());
		escala_del_desarrollo.setCuatro_sonidos_dif(mcChbCuatro_sonidos_dif
				.isChecked());
		escala_del_desarrollo
				.setRie_carcajadas(mcChbRie_carcajadas.isChecked());
		escala_del_desarrollo.setReacciona_al_llamar(mcChbReacciona_al_llamar
				.isChecked());
		escala_del_desarrollo.setPronuncia_silabas(mcChbPronuncia_silabas
				.isChecked());
		escala_del_desarrollo.setHace_sonar_campana(mcChbHace_sonar_campana
				.isChecked());
		escala_del_desarrollo.setPalabra_clara(mcChbPalabra_clara.isChecked());
		escala_del_desarrollo.setNiega_con_cabeza(mcChbNiega_con_cabeza
				.isChecked());
		escala_del_desarrollo.setLlama_a_madre(mcChbLlama_a_madre.isChecked());
		escala_del_desarrollo
				.setEntiende_orden(mcChbEntiende_orden.isChecked());
		escala_del_desarrollo.setReconoce_tres_obj(mcChbReconoce_tres_obj
				.isChecked());
		escala_del_desarrollo.setCombina_dos_palabras(mcChbCombina_dos_palabras
				.isChecked());
		escala_del_desarrollo.setReconoce_seis_obj(mcChbReconoce_seis_obj
				.isChecked());
		escala_del_desarrollo.setNombra_cinco_obj(mcChbNombra_cinco_obj
				.isChecked());
		escala_del_desarrollo.setFrase_tres_palab(mcChbFrase_tres_palab
				.isChecked());
		escala_del_desarrollo.setVeinte_palabras(mcChbVeinte_palabras
				.isChecked());
		escala_del_desarrollo
				.setDice_su_nombre(mcChbDice_su_nombre.isChecked());
		escala_del_desarrollo.setConoce_alto_bajo(mcChbConoce_alto_bajo
				.isChecked());
		escala_del_desarrollo.setUsa_oraciones_compl(mcChbUsa_oraciones_compl
				.isChecked());
		escala_del_desarrollo.setDefine_cinco_obj(mcChbDefine_cinco_obj
				.isChecked());
		escala_del_desarrollo.setRepite_tres_digit(mcChbRepite_tres_digit
				.isChecked());
		escala_del_desarrollo.setDescribe_dibujos(mcChbDescribe_dibujos
				.isChecked());
		escala_del_desarrollo.setCuenta_dedos_manos(mcChbCuenta_dedos_manos
				.isChecked());
		escala_del_desarrollo.setDistingue_adel_atras(mcChbDistingue_adel_atras
				.isChecked());
		escala_del_desarrollo
				.setNombra_colores(mcChbNombra_colores.isChecked());
		escala_del_desarrollo.setExpresa_opiniones(mcChbExpresa_opiniones
				.isChecked());
		escala_del_desarrollo.setConoce_izq_dere(mcChbConoce_izq_dere
				.isChecked());
		escala_del_desarrollo.setConoce_dias_sem(mcChbConoce_dias_sem
				.isChecked());
		escala_del_desarrollo.setSigue_mov_rostro(mcChbSigue_mov_rostro
				.isChecked());
		escala_del_desarrollo.setReconoce_la_madre(mcChbReconoce_la_madre
				.isChecked());
		escala_del_desarrollo.setSonrie_acariciarlo(mcChbSonrie_acariciarlo
				.isChecked());
		escala_del_desarrollo
				.setVoltea_cuando_sele_habl(mcChbVoltea_cuando_sele_habl
						.isChecked());
		escala_del_desarrollo.setCoge_manos_del_exam(mcChbCoge_manos_del_exam
				.isChecked());
		escala_del_desarrollo.setAcepta_coge_jugue(mcChbAcepta_coge_jugue
				.isChecked());
		escala_del_desarrollo.setPone_atencion_conv(mcChbPone_atencion_conv
				.isChecked());
		escala_del_desarrollo.setAyuda_sostener_taza(mcChbAyuda_sostener_taza
				.isChecked());
		escala_del_desarrollo.setReacciona_imagen(mcChbReacciona_imagen
				.isChecked());
		escala_del_desarrollo.setImita_aplauso(mcChbImita_aplauso.isChecked());
		escala_del_desarrollo.setEntrega_juguete(mcChbEntrega_juguete
				.isChecked());
		escala_del_desarrollo.setPide_un_juguete(mcChbPide_un_juguete
				.isChecked());
		escala_del_desarrollo.setBeb_en_taza(mcChbBeb_en_taza.isChecked());
		escala_del_desarrollo.setSeniala_una_prenda(mcChbSeniala_una_prenda
				.isChecked());
		escala_del_desarrollo
				.setSeniala_dos_partes_cuerp(mcChbSeniala_dos_partes_cuerp
						.isChecked());
		escala_del_desarrollo
				.setAvisa_higiene_personal(mcChbAvisa_higiene_personal
						.isChecked());
		escala_del_desarrollo.setSeniala_cinco_partes(mcChbSeniala_cinco_partes
				.isChecked());
		escala_del_desarrollo.setTrata_contar_exper(mcChbTrata_contar_exper
				.isChecked());
		escala_del_desarrollo.setControl_diurno_orina(mcChbControl_diurno_orina
				.isChecked());
		escala_del_desarrollo
				.setDiferencia_ninio_ninia(mcChbDiferencia_ninio_ninia
						.isChecked());
		escala_del_desarrollo
				.setDice_papa_mama(mcChbDice_papa_mama.isChecked());
		escala_del_desarrollo.setSe_bania_solo(mcChbSe_bania_solo.isChecked());
		escala_del_desarrollo.setPuede_desvestirse(mcChbPuede_desvestirse
				.isChecked());
		escala_del_desarrollo
				.setComparte_juego(mcChbComparte_juego.isChecked());
		escala_del_desarrollo.setTiene_amigo_espec(mcChbTiene_amigo_espec
				.isChecked());
		escala_del_desarrollo.setViste_desviste_solo(mcChbViste_desviste_solo
				.isChecked());
		escala_del_desarrollo
				.setSabe_cuantos_anio_tien(mcChbSabe_cuantos_anio_tien
						.isChecked());
		escala_del_desarrollo.setOrganiza_juegos(mcChbOrganiza_juegos
				.isChecked());
		escala_del_desarrollo.setHace_mandados(mcChbHace_mandados.isChecked());
		escala_del_desarrollo
				.setReconoce_nombre_verda(mcChbReconoce_nombre_verda
						.isChecked());
		escala_del_desarrollo.setComenta_vida_famil(mcChbComenta_vida_famil
				.isChecked());
		escala_del_desarrollo.setMotricidad_gruesa(mcTbxMotricidad_gruesa
				.getValue());
		escala_del_desarrollo
				.setMitricidad_adaptativa(mcTbxMitricidad_adaptativa.getValue());
		escala_del_desarrollo.setAudicion_leng(mcTbxAudicion_leng.getValue());
		escala_del_desarrollo.setPersonal_social(mcTbxPersonal_social
				.getValue());
		escala_del_desarrollo.setTotal(mcTbxTotal.getValue());
		escala_del_desarrollo.setObservacion(mcTbxObservacion.getValue());

		return escala_del_desarrollo;
	}

	public void mostrarEscalaDesarrollo(
			Escala_del_desarrollo escala_del_desarrollo,Admision admision) {
		this.escala_del_desarrollo = escala_del_desarrollo;
		this.admision = admision;
		if (this.escala_del_desarrollo == null) {
			this.escala_del_desarrollo = new Escala_del_desarrollo();
			this.escala_del_desarrollo
					.setCodigo_empresa(zkWindow.codigo_empresa);
			this.escala_del_desarrollo
					.setCodigo_sucursal(zkWindow.codigo_sucursal);
			escala_del_desarrollo = this.escala_del_desarrollo;
		}
		mcChbPatea_vigorosamente.setChecked(escala_del_desarrollo
				.getPatea_vigorosamente());
		mcChbLevanta_cabeza_prona.setChecked(escala_del_desarrollo
				.getLevanta_cabeza_prona());
		mcChbLevanta_cab_pecho.setChecked(escala_del_desarrollo
				.getLevanta_cab_pecho());
		mcChbSostiene_cabeza.setChecked(escala_del_desarrollo
				.getSostiene_cabeza());
		mcChbControl_de_cabeza.setChecked(escala_del_desarrollo
				.getControl_de_cabeza());
		mcChbVoltea_de_lado.setChecked(escala_del_desarrollo
				.getVoltea_de_lado());
		mcChbIntenta_sentarse.setChecked(escala_del_desarrollo
				.getIntenta_sentarse());
		mcChbSostiene_sentado.setChecked(escala_del_desarrollo
				.getSostiene_sentado());
		mcChbSe_arrastra.setChecked(escala_del_desarrollo.getSe_arrastra());
		mcChbSe_sienta_solo.setChecked(escala_del_desarrollo
				.getSe_sienta_solo());
		mcChbGatea_bien.setChecked(escala_del_desarrollo.getGatea_bien());
		mcChbSe_agarra_sotiene.setChecked(escala_del_desarrollo
				.getSe_agarra_sotiene());
		mcChbSe_para_solo.setChecked(escala_del_desarrollo.getSe_para_solo());
		mcChbDa_pasitos_solo.setChecked(escala_del_desarrollo
				.getDa_pasitos_solo());
		mcChbCamina_solo_bien.setChecked(escala_del_desarrollo
				.getCamina_solo_bien());
		mcChbCorre.setChecked(escala_del_desarrollo.getCorre());
		mcChbPatea_la_pelota.setChecked(escala_del_desarrollo
				.getPatea_la_pelota());
		mcChbLanza_la_pelota.setChecked(escala_del_desarrollo
				.getLanza_la_pelota());
		mcChbSalta_en_dos_pies.setChecked(escala_del_desarrollo
				.getSalta_en_dos_pies());
		mcChbSe_empina.setChecked(escala_del_desarrollo.getSe_empina());
		mcChbSe_levanta_sin_usar.setChecked(escala_del_desarrollo
				.getSe_levanta_sin_usar());
		mcChbCamina_hacia_atras.setChecked(escala_del_desarrollo
				.getCamina_hacia_atras());
		mcChbCamina_en_puntas.setChecked(escala_del_desarrollo
				.getCamina_en_puntas());
		mcChbSe_para_en_pies.setChecked(escala_del_desarrollo
				.getSe_para_en_pies());
		mcChbLanza_agarra_pelota.setChecked(escala_del_desarrollo
				.getLanza_agarra_pelota());
		mcChbCamina_en_linea.setChecked(escala_del_desarrollo
				.getCamina_en_linea());
		mcChbTres_pasos_en_pies.setChecked(escala_del_desarrollo
				.getTres_pasos_en_pies());
		mcChbHace_rebotar_pelota.setChecked(escala_del_desarrollo
				.getHace_rebotar_pelota());
		mcChbSalta_pies_juntillas.setChecked(escala_del_desarrollo
				.getSalta_pies_juntillas());
		mcChbHace_caballitos.setChecked(escala_del_desarrollo
				.getHace_caballitos());
		mcChbSalta_alto.setChecked(escala_del_desarrollo.getSalta_alto());
		mcChbSigue_mov_horiz.setChecked(escala_del_desarrollo
				.getSigue_mov_horiz());
		mcChbAbre_mira_manos.setChecked(escala_del_desarrollo
				.getAbre_mira_manos());
		mcChbSostiene_obj_mano.setChecked(escala_del_desarrollo
				.getSostiene_obj_mano());
		mcChbSe_lleva_obj_boca.setChecked(escala_del_desarrollo
				.getSe_lleva_obj_boca());
		mcChbAgarra_objetos_volun.setChecked(escala_del_desarrollo
				.getAgarra_objetos_volun());
		mcChbSostiene_obj_cada_mano.setChecked(escala_del_desarrollo
				.getSostiene_obj_cada_mano());
		mcChbPasa_obj_a_manos.setChecked(escala_del_desarrollo
				.getPasa_obj_a_manos());
		mcChbManipula_varios_obj.setChecked(escala_del_desarrollo
				.getManipula_varios_obj());
		mcChbAgarra_obj_pequenio.setChecked(escala_del_desarrollo
				.getAgarra_obj_pequenio());
		mcChbAgarra_cubo.setChecked(escala_del_desarrollo.getAgarra_cubo());
		mcChbMete_saca_obj.setChecked(escala_del_desarrollo.getMete_saca_obj());
		mcChbAgarra_tercer_obj.setChecked(escala_del_desarrollo
				.getAgarra_tercer_obj());
		mcChbBusca_obj_escondidos.setChecked(escala_del_desarrollo
				.getBusca_obj_escondidos());
		mcChbHace_torre.setChecked(escala_del_desarrollo.getHace_torre());
		mcChbPasa_hojas_libro.setChecked(escala_del_desarrollo
				.getPasa_hojas_libro());
		mcChbAnticipa_salida.setChecked(escala_del_desarrollo
				.getAnticipa_salida());
		mcChbTapa_bien_caja.setChecked(escala_del_desarrollo
				.getTapa_bien_caja());
		mcChbHace_garabatos.setChecked(escala_del_desarrollo
				.getHace_garabatos());
		mcChbHace_torres_varios.setChecked(escala_del_desarrollo
				.getHace_torres_varios());
		mcChbEnsarta_mas_cuentas.setChecked(escala_del_desarrollo
				.getEnsarta_mas_cuentas());
		mcChbCopia_linea_hor.setChecked(escala_del_desarrollo
				.getCopia_linea_hor());
		mcChbSepara_obj_grandes.setChecked(escala_del_desarrollo
				.getSepara_obj_grandes());
		mcChbFigura_humana_rudim.setChecked(escala_del_desarrollo
				.getFigura_humana_rudim());
		mcChbCorta_papel_tijerea.setChecked(escala_del_desarrollo
				.getCorta_papel_tijerea());
		mcChbCopia_cuadrado.setChecked(escala_del_desarrollo
				.getCopia_cuadrado());
		mcChbDibuja_fig_humana.setChecked(escala_del_desarrollo
				.getDibuja_fig_humana());
		mcChbAgrupa_col_form.setChecked(escala_del_desarrollo
				.getAgrupa_col_form());
		mcChbDibuja_escalera.setChecked(escala_del_desarrollo
				.getDibuja_escalera());
		mcChbAgrupa_col_form_tam.setChecked(escala_del_desarrollo
				.getAgrupa_col_form_tam());
		mcChbReconstruye_escalera.setChecked(escala_del_desarrollo
				.getReconstruye_escalera());
		mcChbDibuja_casa.setChecked(escala_del_desarrollo.getDibuja_casa());
		mcChbSe_sobresalta_en_ruido.setChecked(escala_del_desarrollo
				.getSe_sobresalta_en_ruido());
		mcChbBusca_sonido.setChecked(escala_del_desarrollo.getBusca_sonido());
		mcChbDos_sonidos_guturales.setChecked(escala_del_desarrollo
				.getDos_sonidos_guturales());
		mcChbBalbucea_con_persona.setChecked(escala_del_desarrollo
				.getBalbucea_con_persona());
		mcChbCuatro_sonidos_dif.setChecked(escala_del_desarrollo
				.getCuatro_sonidos_dif());
		mcChbRie_carcajadas.setChecked(escala_del_desarrollo
				.getRie_carcajadas());
		mcChbReacciona_al_llamar.setChecked(escala_del_desarrollo
				.getReacciona_al_llamar());
		mcChbPronuncia_silabas.setChecked(escala_del_desarrollo
				.getPronuncia_silabas());
		mcChbHace_sonar_campana.setChecked(escala_del_desarrollo
				.getHace_sonar_campana());
		mcChbPalabra_clara.setChecked(escala_del_desarrollo.getPalabra_clara());
		mcChbNiega_con_cabeza.setChecked(escala_del_desarrollo
				.getNiega_con_cabeza());
		mcChbLlama_a_madre.setChecked(escala_del_desarrollo.getLlama_a_madre());
		mcChbEntiende_orden.setChecked(escala_del_desarrollo
				.getEntiende_orden());
		mcChbReconoce_tres_obj.setChecked(escala_del_desarrollo
				.getReconoce_tres_obj());
		mcChbCombina_dos_palabras.setChecked(escala_del_desarrollo
				.getCombina_dos_palabras());
		mcChbReconoce_seis_obj.setChecked(escala_del_desarrollo
				.getReconoce_seis_obj());
		mcChbNombra_cinco_obj.setChecked(escala_del_desarrollo
				.getNombra_cinco_obj());
		mcChbFrase_tres_palab.setChecked(escala_del_desarrollo
				.getFrase_tres_palab());
		mcChbVeinte_palabras.setChecked(escala_del_desarrollo
				.getVeinte_palabras());
		mcChbDice_su_nombre.setChecked(escala_del_desarrollo
				.getDice_su_nombre());
		mcChbConoce_alto_bajo.setChecked(escala_del_desarrollo
				.getConoce_alto_bajo());
		mcChbUsa_oraciones_compl.setChecked(escala_del_desarrollo
				.getUsa_oraciones_compl());
		mcChbDefine_cinco_obj.setChecked(escala_del_desarrollo
				.getDefine_cinco_obj());
		mcChbRepite_tres_digit.setChecked(escala_del_desarrollo
				.getRepite_tres_digit());
		mcChbDescribe_dibujos.setChecked(escala_del_desarrollo
				.getDescribe_dibujos());
		mcChbCuenta_dedos_manos.setChecked(escala_del_desarrollo
				.getCuenta_dedos_manos());
		mcChbDistingue_adel_atras.setChecked(escala_del_desarrollo
				.getDistingue_adel_atras());
		mcChbNombra_colores.setChecked(escala_del_desarrollo
				.getNombra_colores());
		mcChbExpresa_opiniones.setChecked(escala_del_desarrollo
				.getExpresa_opiniones());
		mcChbConoce_izq_dere.setChecked(escala_del_desarrollo
				.getConoce_izq_dere());
		mcChbConoce_dias_sem.setChecked(escala_del_desarrollo
				.getConoce_dias_sem());
		mcChbSigue_mov_rostro.setChecked(escala_del_desarrollo
				.getSigue_mov_rostro());
		mcChbReconoce_la_madre.setChecked(escala_del_desarrollo
				.getReconoce_la_madre());
		mcChbSonrie_acariciarlo.setChecked(escala_del_desarrollo
				.getSonrie_acariciarlo());
		mcChbVoltea_cuando_sele_habl.setChecked(escala_del_desarrollo
				.getVoltea_cuando_sele_habl());
		mcChbCoge_manos_del_exam.setChecked(escala_del_desarrollo
				.getCoge_manos_del_exam());
		mcChbAcepta_coge_jugue.setChecked(escala_del_desarrollo
				.getAcepta_coge_jugue());
		mcChbPone_atencion_conv.setChecked(escala_del_desarrollo
				.getPone_atencion_conv());
		mcChbAyuda_sostener_taza.setChecked(escala_del_desarrollo
				.getAyuda_sostener_taza());
		mcChbReacciona_imagen.setChecked(escala_del_desarrollo
				.getReacciona_imagen());
		mcChbImita_aplauso.setChecked(escala_del_desarrollo.getImita_aplauso());
		mcChbEntrega_juguete.setChecked(escala_del_desarrollo
				.getEntrega_juguete());
		mcChbPide_un_juguete.setChecked(escala_del_desarrollo
				.getPide_un_juguete());
		mcChbBeb_en_taza.setChecked(escala_del_desarrollo.getBeb_en_taza());
		mcChbSeniala_una_prenda.setChecked(escala_del_desarrollo
				.getSeniala_una_prenda());
		mcChbSeniala_dos_partes_cuerp.setChecked(escala_del_desarrollo
				.getSeniala_dos_partes_cuerp());
		mcChbAvisa_higiene_personal.setChecked(escala_del_desarrollo
				.getAvisa_higiene_personal());
		mcChbSeniala_cinco_partes.setChecked(escala_del_desarrollo
				.getSeniala_cinco_partes());
		mcChbTrata_contar_exper.setChecked(escala_del_desarrollo
				.getTrata_contar_exper());
		mcChbControl_diurno_orina.setChecked(escala_del_desarrollo
				.getControl_diurno_orina());
		mcChbDiferencia_ninio_ninia.setChecked(escala_del_desarrollo
				.getDiferencia_ninio_ninia());
		mcChbDice_papa_mama.setChecked(escala_del_desarrollo
				.getDice_papa_mama());
		mcChbSe_bania_solo.setChecked(escala_del_desarrollo.getSe_bania_solo());
		mcChbPuede_desvestirse.setChecked(escala_del_desarrollo
				.getPuede_desvestirse());
		mcChbComparte_juego.setChecked(escala_del_desarrollo
				.getComparte_juego());
		mcChbTiene_amigo_espec.setChecked(escala_del_desarrollo
				.getTiene_amigo_espec());
		mcChbViste_desviste_solo.setChecked(escala_del_desarrollo
				.getViste_desviste_solo());
		mcChbSabe_cuantos_anio_tien.setChecked(escala_del_desarrollo
				.getSabe_cuantos_anio_tien());
		mcChbOrganiza_juegos.setChecked(escala_del_desarrollo
				.getOrganiza_juegos());
		mcChbHace_mandados.setChecked(escala_del_desarrollo.getHace_mandados());
		mcChbReconoce_nombre_verda.setChecked(escala_del_desarrollo
				.getReconoce_nombre_verda());
		mcChbComenta_vida_famil.setChecked(escala_del_desarrollo
				.getComenta_vida_famil());
		mcTbxMotricidad_gruesa.setValue(escala_del_desarrollo
				.getMotricidad_gruesa());
		mcTbxMitricidad_adaptativa.setValue(escala_del_desarrollo
				.getMitricidad_adaptativa());
		mcTbxAudicion_leng.setValue(escala_del_desarrollo.getAudicion_leng());
		mcTbxPersonal_social.setValue(escala_del_desarrollo
				.getPersonal_social());
		mcTbxTotal.setValue(escala_del_desarrollo.getTotal());
		mcTbxObservacion.setValue(escala_del_desarrollo.getObservacion());
		calcularEscalaDesarrollo("SUB01");
		calcularEscalaDesarrollo("SUB02");
		calcularEscalaDesarrollo("SUB03");
		calcularEscalaDesarrollo("SUB04");
		mostrarTotal();
	}
	
	public void seleccionarTodosChecks(Row row){
		List<Component> cells = row.getChildren();
		for (Component componentl1 : cells) {
			for(Component componentl2 : componentl1.getChildren()) {
				if(componentl2 instanceof Vlayout){
					for(Component componentl3 : componentl2.getChildren()) {
						if(componentl3 instanceof Checkbox){
							Checkbox chk = (Checkbox)componentl3;
							chk.setChecked(true);
						}
					}
				}
			}
		}
	}

	public void setZkWindow(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
	}

	public ZKWindow getZkWindow() {
		return zkWindow;
	}

	public void ocultarFilas(Checkbox checkbox, Row row, Intbox intbox,
			Intbox intbox2) {
		if (checkbox.isChecked()) {
			row.setVisible(true);
			intbox2.setVisible(true);
		} else {
			row.setVisible(false);
			intbox2.setVisible(false);
		}
	}

	public void calcularPuntageTotal(Intbox intbox1, Intbox intbox2,
			Intbox intbox3, Intbox intbox4, Intbox intbox5, Intbox intbox6,
			Intbox intbox7, Intbox intbox8) {
		try {
			int total = 0;
			int total1 = 0;
			int total2 = 0;
			int total3 = 0;
			int total4 = 0;
			int total5 = 0;
			int total6 = 0;
			int total7 = 0;
			int total8 = 0;

			if (intbox2.getValue() != null && intbox4.getValue() != null
					&& intbox6.getValue() != null && intbox8.getValue() != null) {
				total1 = intbox1.getValue();
				total2 = intbox2.getValue();
				total3 = intbox3.getValue();
				total4 = intbox4.getValue();
				total5 = intbox5.getValue();
				total6 = intbox6.getValue();
				total7 = intbox7.getValue();
				total8 = intbox8.getValue();
				total = total1 + total2 + total3 + total4 + total5 + total6
						+ total7 + total8;

				mcTbxTotal.setText(total + "");

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void deshabilitar_conRadio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			String valor = "S";
			FormularioUtil.deshabilitarComponentes_conRadiogroup(radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public Integer mostrarMensaje(Integer edad_mes, Integer total, Integer tipo) {
//		String[] mensaje = { "Alerta", "Medio", "Medio alto", "Alto" };
		
		Integer[][] rango_edadmeses = { { 0, 3 }, { 4, 6 }, { 7, 9 },	{ 10, 12 }, { 13, 18 }, { 19, 24 }, { 25, 36 }, { 37, 48 },{ 49, 60 } };
		
		Integer[][] rango1_1 = { { 0, 1 }, { 2, 3 }, { 4, 5 }, { 6, 10 } };
		Integer[][] rango1_2 = { { 0, 4 }, { 5, 6 }, { 7, 9 }, { 10, 10 } };
		Integer[][] rango1_3 = { { 0, 7 }, { 8, 10 }, { 11, 13 }, { 17, 10 } };
		Integer[][] rango1_4 = { { 0, 11 }, { 12, 13 }, { 14, 16 }, { 20, 10 } };
		Integer[][] rango1_5 = { { 0, 13 }, { 14, 16 }, { 17, 19 }, { 24, 10 } };
		Integer[][] rango1_6 = { { 0, 16 }, { 17, 19 }, { 20, 23 }, { 28, 10 } };
		Integer[][] rango1_7 = { { 0, 16 }, { 17, 19 }, { 20, 23 }, { 28, 10 } };
		Integer[][] rango1_8 = { { 0, 19 }, { 20, 23 }, { 24, 27 }, { 30, 10 } };
		Integer[][] rango1_9 = { { 0, 6 }, { 27, 29 }, { 30, 10 } };

		Integer[][] rango2_1 = { { 0, 1 }, { 2, 3 }, { 4, 5 }, { 6, 10 } };
		Integer[][] rango2_2 = { { 0, 4 }, { 5, 6 }, { 7, 9 }, { 10, 10 } };
		Integer[][] rango2_3 = { { 0, 7 }, { 8, 10 }, { 11, 12 }, { 13, 10 } };
		Integer[][] rango2_4 = { { 0, 9 }, { 10, 12 }, { 13, 14 }, { 15, 10 } };
		Integer[][] rango2_5 = { { 0, 12 }, { 13, 15 }, { 16, 18 }, { 19, 10 } };
		Integer[][] rango2_6 = { { 0, 14 }, { 15, 18 }, { 19, 20 }, { 21, 10 } };
		Integer[][] rango2_7 = { { 0, 18 }, { 19, 21 }, { 22, 24 }, { 25, 10 } };
		Integer[][] rango2_8 = { { 0, 21 }, { 22, 24 }, { 25, 28 }, { 29, 10 } };
		Integer[][] rango2_9 = { { 0, 23 }, { 24, 28 }, { 29, 10 } };

		Integer[][] rango3_1 = { { 0, 1 }, { 2, 3 }, { 4, 5 }, { 6, 10 } };
		Integer[][] rango3_2 = { { 0, 4 }, { 5, 6 }, { 7, 9 }, { 10, 10 } };
		Integer[][] rango3_3 = { { 0, 7 }, { 8, 9 }, { 10, 12 }, { 13, 10 } };
		Integer[][] rango3_4 = { { 0, 9 }, { 10, 12 }, { 13, 14 }, { 15, 10 } };
		Integer[][] rango3_5 = { { 0, 12 }, { 13, 14 }, { 15, 17 }, { 18, 10 } };
		Integer[][] rango3_6 = { { 0, 13 }, { 14, 17 }, { 18, 20 }, { 21, 10 } };
		Integer[][] rango3_7 = { { 0, 17 }, { 18, 21 }, { 22, 24 }, { 25, 10 } };
		Integer[][] rango3_8 = { { 0, 21 }, { 22, 25 }, { 26, 29 }, { 30, 10 } };
		Integer[][] rango3_9 = { { 0, 24 }, { 25, 28 }, { 29, 10 } };

		Integer[][] rango4_1 = { { 0, 1 }, { 2, 3 }, { 4, 5 }, { 6, 10 } };
		Integer[][] rango4_2 = { { 0, 4 }, { 5, 6 }, { 7, 9 }, { 10, 10 } };
		Integer[][] rango4_3 = { { 0, 7 }, { 8, 9 }, { 10, 12 }, { 13, 10 } };
		Integer[][] rango4_4 = { { 0, 9 }, { 10, 12 }, { 13, 14 }, { 15, 10 } };
		Integer[][] rango4_5 = { { 0, 12 }, { 13, 14 }, { 15, 17 }, { 18, 10 } };
		Integer[][] rango4_6 = { { 0, 14 }, { 15, 17 }, { 18, 22 }, { 23, 10 } };
		Integer[][] rango4_7 = { { 0, 18 }, { 19, 22 }, { 23, 27 }, { 28, 10 } };
		Integer[][] rango4_8 = { { 0, 22 }, { 23, 26 }, { 27, 29 }, { 30, 10 } };
		Integer[][] rango4_9 = { { 0, 25 }, { 26, 28 }, { 29, 10 } };

		Integer r = -1;
		for (Integer i = 0; i < rango_edadmeses.length; i++) {
			if (edad_mes >= rango_edadmeses[i][0]
					&& edad_mes <= rango_edadmeses[i][1]) {
				r = i;
				break;
			}
		}
		if (r >= 0) {
			Integer resultado = -1;
			switch (tipo) {
			case 1:
				switch (r) {
				case 0:
					 resultado =evaluarArreglo(rango1_1,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 1:
					resultado =evaluarArreglo(rango1_2,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 2:
					resultado =evaluarArreglo(rango1_3,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 3:
					resultado =evaluarArreglo(rango1_4,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 4:
					resultado =evaluarArreglo(rango1_5,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 5:
					resultado =evaluarArreglo(rango1_6,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 6:
					resultado =evaluarArreglo(rango1_7,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 7:
					resultado =evaluarArreglo(rango1_8,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 8:
					resultado =evaluarArreglo(rango1_9,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				}
				break;
			case 2:
				switch (r) {
				case 0:
					 resultado =evaluarArreglo(rango2_1,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 1:
					resultado =evaluarArreglo(rango2_2,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 2:
					resultado =evaluarArreglo(rango2_3,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 3:
					resultado =evaluarArreglo(rango2_4,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 4:
					resultado =evaluarArreglo(rango2_5,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 5:
					resultado =evaluarArreglo(rango2_6,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 6:
					resultado =evaluarArreglo(rango2_7,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 7:
					resultado =evaluarArreglo(rango2_8,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 8:
					resultado =evaluarArreglo(rango2_9,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				}
				break;
			case 3:
				switch (r) {
				case 0:
					 resultado =evaluarArreglo(rango3_1,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 1:
					resultado =evaluarArreglo(rango3_2,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 2:
					resultado =evaluarArreglo(rango3_3,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 3:
					resultado =evaluarArreglo(rango3_4,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 4:
					resultado =evaluarArreglo(rango3_5,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 5:
					resultado =evaluarArreglo(rango3_6,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 6:
					resultado =evaluarArreglo(rango3_7,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 7:
					resultado =evaluarArreglo(rango3_8,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 8:
					resultado =evaluarArreglo(rango3_9,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				}
				break;
			case 4:
				switch (r) {
				case 0:
					 resultado =evaluarArreglo(rango4_1,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 1:
					resultado =evaluarArreglo(rango4_2,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 2:
					resultado =evaluarArreglo(rango4_3,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 3:
					resultado =evaluarArreglo(rango4_4,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 4:
					resultado =evaluarArreglo(rango4_5,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 5:
					resultado =evaluarArreglo(rango4_6,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 6:
					resultado =evaluarArreglo(rango4_7,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 7:
					resultado =evaluarArreglo(rango4_8,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				case 8:
					resultado =evaluarArreglo(rango4_9,total); 
					if(resultado>-1){
						return resultado;
					}
					break;
				}
				break;
			}
		}
		return -1;
	}

	private Integer evaluarArreglo(Integer[][] rango, Integer total){
		Integer r = -1;
		for(int i=0;i<rango.length;i++){
			if (total >= rango[i][0]	&& total <= rango[i][1]) {
				r= i;
				break;
			}
		}
		return r;
	}

	private void mostrarTotal(){
		Integer edad_mes = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy")
						.format(admision.getPaciente()
								.getFecha_nacimiento()), "2", false));

		mcTbxPersonal_social.setText(subtotalNum4 + "");
		mcTbxAudicion_leng.setText(subtotalNum3 + "");
		mcTbxMitricidad_adaptativa.setText(subtotalNum2 + "");
		mcTbxMotricidad_gruesa.setText(subtotalNum1 + "");
		
//		mcTbxPersonal_social_alerta.setText();
//		mcTbxAudicion_leng_alerta.setText(mostrarMensaje(edad_mes, subtotalNum3, 2));
//		mcTbxMitricidad_adaptativa_alerta.setText(mostrarMensaje(edad_mes, subtotalNum2, 3));
//		mcTbxMotricidad_gruesa_alerta.setText(mostrarMensaje(edad_mes, subtotalNum1, 4));
		int r1 =mostrarMensaje(edad_mes, subtotalNum1, 1);
		int r2 =mostrarMensaje(edad_mes, subtotalNum2, 2);
		int r3 =mostrarMensaje(edad_mes, subtotalNum3, 3);
		int r4 =mostrarMensaje(edad_mes, subtotalNum4, 4);
		
		if (r1==0){		 
			lbAlertaMotricidad1.setVisible(true);
			lbAlertaMotricidad2.setVisible(false);
			lbAlertaMotricidad3.setVisible(false);
			lbAlertaMotricidad4.setVisible(false);
		}else if(r1==1){
			lbAlertaMotricidad1.setVisible(false);
			lbAlertaMotricidad2.setVisible(true);
			lbAlertaMotricidad3.setVisible(false);
			lbAlertaMotricidad4.setVisible(false);
		}else if(r1==2){
			lbAlertaMotricidad1.setVisible(false);
			lbAlertaMotricidad2.setVisible(false);
			lbAlertaMotricidad3.setVisible(true);
			lbAlertaMotricidad4.setVisible(false);
		}else if(r1==3){
			lbAlertaMotricidad1.setVisible(false);
			lbAlertaMotricidad2.setVisible(false);
			lbAlertaMotricidad3.setVisible(false);
			lbAlertaMotricidad4.setVisible(true);
		}

		if (r2==0){
			lbAlertaAdaptativa1.setVisible(true);
			lbAlertaAdaptativa2.setVisible(false);
			lbAlertaAdaptativa3.setVisible(false);
			lbAlertaAdaptativa4.setVisible(false);
		}else if(r2==1){
			lbAlertaAdaptativa1.setVisible(false);
			lbAlertaAdaptativa2.setVisible(true);
			lbAlertaAdaptativa3.setVisible(false);
			lbAlertaAdaptativa4.setVisible(false);
		}else if(r2==2){
			lbAlertaAdaptativa1.setVisible(false);
			lbAlertaAdaptativa2.setVisible(false);
			lbAlertaAdaptativa3.setVisible(true);
			lbAlertaAdaptativa4.setVisible(false);
		}else if(r2==3){
			lbAlertaAdaptativa1.setVisible(false);
			lbAlertaAdaptativa2.setVisible(false);
			lbAlertaAdaptativa3.setVisible(false);
			lbAlertaAdaptativa4.setVisible(true);
		}
		
		if (r3==0){
			lbAlertalenguaje1.setVisible(true);
			lbAlertalenguaje2.setVisible(false);
			lbAlertalenguaje3.setVisible(false);
			lbAlertalenguaje4.setVisible(false);
		}else if(r3==1){
			lbAlertalenguaje1.setVisible(false);
			lbAlertalenguaje2.setVisible(true);
			lbAlertalenguaje3.setVisible(false);
			lbAlertalenguaje4.setVisible(false);
		}else if(r3==2){
			lbAlertalenguaje1.setVisible(false);
			lbAlertalenguaje2.setVisible(false);
			lbAlertalenguaje3.setVisible(true);
			lbAlertalenguaje4.setVisible(false);
		}else if(r3==3){
			lbAlertalenguaje1.setVisible(false);
			lbAlertalenguaje2.setVisible(false);
			lbAlertalenguaje3.setVisible(false);
			lbAlertalenguaje4.setVisible(true);
		}

		if (r4==0){
			lbAlertaPersonal1.setVisible(true);
			lbAlertaPersonal2.setVisible(false);
			lbAlertaPersonal3.setVisible(false);
			lbAlertaPersonal4.setVisible(false);
		}else if(r4==1){
			lbAlertaPersonal1.setVisible(false);
			lbAlertaPersonal2.setVisible(true);
			lbAlertaPersonal3.setVisible(false);
			lbAlertaPersonal4.setVisible(false);
		}else if(r4==2){
			lbAlertaPersonal1.setVisible(false);
			lbAlertaPersonal2.setVisible(false);
			lbAlertaPersonal3.setVisible(true);
			lbAlertaPersonal4.setVisible(false);
		}else if(r4==3){
			lbAlertaPersonal1.setVisible(false);
			lbAlertaPersonal2.setVisible(false);
			lbAlertaPersonal3.setVisible(false);
			lbAlertaPersonal4.setVisible(true);
		}
		
		Integer total = subtotalNum1 + subtotalNum2 + subtotalNum3
				+ subtotalNum4;
		mcTbxTotal.setText(total + "");
	}
	
	public Admision getAdmision() {
		return admision;
	}

	public void setAdmision(Admision admision) {
		this.admision = admision;
	}
}
