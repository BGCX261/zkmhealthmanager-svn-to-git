package com.framework.util;

import java.sql.Timestamp;
import java.util.Date;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Doublebox;

import com.framework.util.MensajesUtil;

public class UtilidadSignosVitales {

	private static final String POSICION_ALERTA = "end_before";
	private static Integer TIEMPO_ALERTA = 4000;
	public static final String TALLA_CENTIMETROS = "C";
	public static final String TALLA_METROS = "M";
	public static final String PESO_GRAMOS = "G";
	public static final String PESO_KILOS = "K";
	
	public static final String DE_TALLA_EDAD= "de_te";
	public static final String DE_PESO_EDAD= "de_pe";
	public static final String DE_PESO_TALLA= "de_pt";
	public static final String DE_IMC_EDAD= "de_ie";
	public static final String DE_PC_EDAD= "de_pce";
	

	public static void onMostrarAlertaTension(Doublebox mcDbxTA_sistolica, Doublebox mcDbxTA_diastolica) throws Exception {
			Double ta_sistolica = mcDbxTA_sistolica.getValue();
			Double ta_diastolica = mcDbxTA_diastolica.getValue();
			if (ta_sistolica != null && ta_diastolica != null) {
				String valor = ((int) ta_sistolica.doubleValue()) + "/"	+ ((int) ta_diastolica.doubleValue());
				// Clase java para convertir texto en una expresion aritmetica
				// Utiliza el eval de javascript //
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("js");
				Object resultado = engine.eval(valor);
				double res_num = (Double) resultado;
				double alerta_anormal = (Double) engine.eval("(130/80)");
				double alerta_alta = (Double) engine.eval("(140/90)");

				if (res_num >= alerta_anormal) {
					mcDbxTA_sistolica.setStyle("background-color:yellow");
					mcDbxTA_diastolica.setStyle("background-color:yellow");
					String mensaje = "Tension Anormal (" + valor + ")";
					mcDbxTA_sistolica.setTooltiptext(mensaje);
					mcDbxTA_diastolica.setTooltiptext(mensaje);
					Clients.showNotification(mensaje, Clients.NOTIFICATION_TYPE_WARNING,mcDbxTA_sistolica, POSICION_ALERTA, TIEMPO_ALERTA,true);
				} else if (res_num >= alerta_alta) {
					mcDbxTA_sistolica.setStyle("background-color:red");
					mcDbxTA_diastolica.setStyle("background-color:red");
					String mensaje = "Tension Alta (" + valor + ")";
					mcDbxTA_sistolica.setTooltiptext(mensaje);
					mcDbxTA_diastolica.setTooltiptext(mensaje);
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_ERROR, mcDbxTA_sistolica,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
				} else {
					mcDbxTA_sistolica.setStyle("background-color:none");
					mcDbxTA_diastolica.setStyle("background-color:none");
					mcDbxTA_sistolica.setTooltiptext("");
					mcDbxTA_diastolica.setTooltiptext("");
				}
			} else {
				mcDbxTA_sistolica.setStyle("background-color:none");
				mcDbxTA_diastolica.setStyle("background-color:none");
				mcDbxTA_sistolica.setTooltiptext("");
				mcDbxTA_diastolica.setTooltiptext("");
			}
		}
	
		public static void onCalcularTension(Doublebox mcDbxTA_sistolica, Doublebox mcDbxTA_diastolica, Double max_sistolica, Double max_diastolica, Double min_sistolica, Double min_diastolica) throws Exception {
			
			Double ta_sistolica = mcDbxTA_sistolica.getValue();
			Double ta_diastolica = mcDbxTA_diastolica.getValue();
			
			if (ta_sistolica != null) {
				if(ta_sistolica>=max_sistolica){
					mcDbxTA_sistolica.setStyle("background-color:red");
					String mensaje = "tension Sistólica Alta (" + ta_sistolica.intValue() + ")>="+max_sistolica.intValue();
					mcDbxTA_sistolica.setTooltiptext(mensaje);
					//Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, mcDbxTA_sistolica,POSICION_ALERTA, TIEMPO_ALERTA, true);
				}else if(ta_sistolica<min_sistolica){
					mcDbxTA_sistolica.setStyle("background-color:yellow");
					String mensaje = "tension Sistólica Baja (" + ta_sistolica.intValue() + ")<"+min_sistolica.intValue();
					mcDbxTA_sistolica.setTooltiptext(mensaje);
					//Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_WARNING, mcDbxTA_sistolica,POSICION_ALERTA, TIEMPO_ALERTA, true);
				}else{
					mcDbxTA_sistolica.setStyle("background-color:none");
					mcDbxTA_sistolica.setTooltiptext("");
				}			
			}else{
				mcDbxTA_sistolica.setStyle("background-color:none");
				mcDbxTA_sistolica.setTooltiptext("");
			}
			if (ta_diastolica != null){
				if(ta_diastolica<min_diastolica){
					mcDbxTA_diastolica.setStyle("background-color:yellow");
					String mensaje = "tension Diastólica Baja (" + ta_diastolica.intValue() + ")<"+min_diastolica.intValue();
					mcDbxTA_diastolica.setTooltiptext(mensaje);
					//Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_WARNING, mcDbxTA_diastolica,POSICION_ALERTA, TIEMPO_ALERTA, true);
				}else if(ta_diastolica>=max_diastolica){
					mcDbxTA_diastolica.setStyle("background-color:red");
					String mensaje = "tension Diastólica Alta (" + ta_diastolica.intValue() + ")>="+max_diastolica.intValue();
					mcDbxTA_diastolica.setTooltiptext(mensaje);
					//Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, mcDbxTA_diastolica,POSICION_ALERTA, TIEMPO_ALERTA, true);
				} else {
					mcDbxTA_diastolica.setStyle("background-color:none");
					mcDbxTA_diastolica.setTooltiptext("");
				}
			}else{
				mcDbxTA_diastolica.setStyle("background-color:none");
				mcDbxTA_diastolica.setTooltiptext("");
			}
			
			if(ta_sistolica != null && ta_diastolica != null){			
				if((ta_sistolica>=min_sistolica && ta_sistolica<max_sistolica) && (ta_diastolica>=min_diastolica && ta_diastolica<max_diastolica)){
//					mcDbxTA_sistolica.setStyle("background-color:#ADEDAD");
//					mcDbxTA_diastolica.setStyle("background-color:#ADEDAD");
//					String mensaje = "tension Arterial Normal (" + ta_sistolica.intValue() +"/"+ta_diastolica.intValue()+ ")";
//					mcDbxTA_sistolica.setTooltiptext(mensaje);
//					mcDbxTA_diastolica.setTooltiptext(mensaje);
//					Clients.showNotification(mensaje,
//							Clients.NOTIFICATION_TYPE_INFO, mcDbxTA_sistolica,
//							POSICION_ALERTA, TIEMPO_ALERTA, true);
				} else if((ta_sistolica<=min_sistolica && (ta_diastolica<=max_diastolica && ta_diastolica>min_diastolica)) ||
						(ta_diastolica<=min_diastolica && (ta_sistolica<=max_sistolica && ta_sistolica>min_sistolica))){
					String mensaje = "tension Arterial Baja (" + ta_sistolica.intValue() +"/"+ta_diastolica.intValue()+ ")";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcDbxTA_sistolica,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
				}else if((ta_sistolica>=max_sistolica && (ta_diastolica<=max_diastolica && ta_diastolica>min_diastolica)) ||
						(ta_diastolica>=max_diastolica && (ta_sistolica<=max_sistolica && ta_sistolica>min_sistolica))){
					String mensaje = "tension Arterial Alta (" + ta_sistolica.intValue() +"/"+ta_diastolica.intValue()+ ")";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_ERROR, mcDbxTA_sistolica,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
				}else{
					String mensaje = "tension Arterial Anormal (" + ta_sistolica.intValue() +"/"+ta_diastolica.intValue()+ ")";
					Clients.showNotification(mensaje,
							Clients.NOTIFICATION_TYPE_WARNING, mcDbxTA_sistolica,
							POSICION_ALERTA, TIEMPO_ALERTA, true);
				}
			}
		}

		public static void onCalcularTensionEmbarazo(Doublebox mcDbxTA_sistolica, Doublebox mcDbxTA_diastolica) throws Exception {
			Double max_sistolica = 130d;
			Double max_diastolica = 90d;
			Double min_sistolica = 80d;
			Double min_diastolica = 50d;
			onCalcularTension(mcDbxTA_sistolica, mcDbxTA_diastolica, max_sistolica, max_diastolica, min_sistolica, min_diastolica);
		}

		
		public static void onCalcularTension(Doublebox mcDbxTA_sistolica, Doublebox mcDbxTA_diastolica) throws Exception {
			Double max_sistolica = 140d;
			Double max_diastolica = 90d;
			Double min_sistolica = 80d;
			Double min_diastolica = 50d;
			onCalcularTension(mcDbxTA_sistolica, mcDbxTA_diastolica, max_sistolica, max_diastolica, min_sistolica, min_diastolica);
		}

	public static void onCalcularIMC(Doublebox mcDbxPeso, Doublebox mcDbxTalla, Doublebox mcDbxIMC, String config_talla, String config_peso) throws Exception{
			Double talla = mcDbxTalla.getValue();
			Double peso = mcDbxPeso.getValue();

			if (talla != null && peso != null) {
				if (talla.compareTo(0.0) != 0 && peso.compareTo(0.0) != 0 ) {
					if (config_talla.equals(TALLA_CENTIMETROS)) {
						talla /= 100;
					}
					if (config_peso.equals(PESO_GRAMOS)) {
						peso /= 1000;
					}
					double imc = (peso/(Math.pow(talla, 2)));
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
						alerta = "Sobrepeso I - Riesgo(Aumentado)";
						color_alerta = "blue";
					} else if (30 <= imc && imc <= 39.9) {
						alerta = "Obesidad grado I - Riesgo(Alto)";
						color_alerta = "pink";
					} else if (40 <= imc && imc <= 45) {
						alerta = "Obesidad grado II - Riesgo(Muy Alto)";
						color_alerta = "#e37676";
					} else if (imc > 45) {
						alerta = "Obesidad grado III - Riesgo(Extremadamente Alto)";
						color_alerta = "#823434";
					}
					if (!alerta.isEmpty()) {
						mcDbxIMC.setTooltiptext(alerta);
						mcDbxIMC.setStyle("background-color:" + color_alerta);
						if (color_alerta.equals("white")) {
							Clients.showNotification(alerta,
									Clients.NOTIFICATION_TYPE_INFO, mcDbxIMC,
									POSICION_ALERTA, TIEMPO_ALERTA, true);
						} else {
							Clients.showNotification(alerta,
									Clients.NOTIFICATION_TYPE_WARNING,
									mcDbxIMC, POSICION_ALERTA, TIEMPO_ALERTA,
									true);
						}
					} else {
						mcDbxIMC.setTooltiptext(null);
						mcDbxIMC.setStyle("background-color:" + color_alerta);

					}

				} else {
					mcDbxIMC.setTooltiptext(null);
					mcDbxIMC.setStyle("background-color:white");
				}
			} else {
				mcDbxIMC.setTooltiptext(null);
				mcDbxIMC.setStyle("background-color:white");
			}
	}
	
	/**
	 * Calular IMC, teniendo el peso y la talla
	 * para la 4505
	 * @author Luis Miguel 
	 * */
	public static Double calcularIMC(Double peso, Double talla){
		if(peso != null && talla != null){
			return (peso/(Math.pow(talla, 2)));
		}else{
			return null;
		}
	}
	
	public static void calcularIMC(Doublebox mcDbxPeso, Doublebox mcDbxTalla, Doublebox mcDbxIMC, String config_talla, String config_peso) throws Exception{
			Double talla = mcDbxTalla.getValue();
			Double peso = mcDbxPeso.getValue();
	
			if (talla != null && peso != null) {
				if (talla.compareTo(0.0) != 0 && peso.compareTo(0.0) != 0 ) {
					if (config_talla.equals(TALLA_CENTIMETROS)) {
						talla /= 100;
					}
					if (config_peso.equals(PESO_GRAMOS)) {
						peso /= 1000;
					}
					double imc = (peso/(Math.pow(talla, 2)));
					mcDbxIMC.setValue(imc);
				}
			}
}
	
	public static void onMostrarAlertaFrecuenciaCardiaca(Doublebox dbxFc, Date fecha_nacimiento, String genero) {
			dbxFc.setTooltiptext(null);
			if (dbxFc.getValue() != null) {
				String edad = Util
						.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy")
								.format(fecha_nacimiento), "1", false);
				int edad_integer = Integer.parseInt(edad);
				double frecuencia = dbxFc.getValue();
				if (edad_integer < 1) {
					if(frecuencia < 120 || frecuencia > 160){
						MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(120-160)", dbxFc);
						dbxFc.setTooltiptext("Frecuencia cardiaca anormal RI(120-160)");
					}
				} else if(edad_integer == 1){
					if(frecuencia < 120 || frecuencia > 130){
						MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(120-130)", dbxFc);
						dbxFc.setTooltiptext("Frecuencia cardiaca anormal (120-130)");
					}
				} else if(edad_integer == 2){
					if(frecuencia < 100 || frecuencia > 120){
						MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(100-120)", dbxFc);
						dbxFc.setTooltiptext("Frecuencia cardiaca anormal RI(100-120)");
					}
				} else if(edad_integer == 3){ 
					if(frecuencia < 90 || frecuencia > 100){
						MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(90-100)", dbxFc);
						dbxFc.setTooltiptext("Frecuencia cardiaca anormal RI(90-100)");
					}
				} else if(edad_integer >= 4 && edad_integer <= 8){
					if(frecuencia < 86 || frecuencia > 90){
						MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(86-90)", dbxFc);
						dbxFc.setTooltiptext("Frecuencia cardiaca anormal RI(86-90)");
					}
				} else if(edad_integer >= 9 && edad_integer <= 15){
					if(frecuencia < 80 || frecuencia > 86){
						MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(80-86)", dbxFc);
						dbxFc.setTooltiptext("Frecuencia cardiaca anormal RI(80-86)");
					}
				} else{
					if(genero.equals("M")){
						if(edad_integer >= 16 && edad_integer <= 55){
							if(frecuencia < 60 || frecuencia > 80){
								MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(60-80)", dbxFc);
								dbxFc.setTooltiptext("Frecuencia cardiaca anormal RI(60-80)");
							}
						}else{
							if(frecuencia > 60){
								MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(<=60)", dbxFc);
								dbxFc.setTooltiptext("Frecuencia cardiaca anormal RI(<=60)");
							}
						}
					}else{
						if(edad_integer >= 16 && edad_integer <= 65){
							if(frecuencia < 60 || frecuencia > 80){
								MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(60-80)", dbxFc);
								dbxFc.setTooltiptext("Frecuencia cardiaca anormal RI(60-80)");
							}
						}else{
							if(frecuencia > 60){
								MensajesUtil.notificarAlerta("Frecuencia cardiaca anormal RI(<=60)", dbxFc);
								dbxFc.setTooltiptext("Frecuencia cardiaca anormal RI(<=60)");
							}
						}
					}
				}
			} else {
				dbxFc.setTooltiptext(null);
			}
	}
	
	public static void onMostrarAlertaFrecuenciaRespiratoria(Doublebox dbxFr, Date fecha_nacimiento, String genero) {
		dbxFr.setTooltiptext(null);
		if (dbxFr.getValue() != null) {
			String edad = Util.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha_nacimiento), "1", false);
			int edad_integer = Integer.parseInt(edad);
			double frecuencia = dbxFr.getValue();
			if (edad_integer < 1) {
				if(frecuencia < 30 || frecuencia > 40){
					MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(30-40)", dbxFr);
					dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(30-40)");
				}
			} else if(edad_integer == 1){
				if(frecuencia < 26 || frecuencia > 30){
					MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(26-30)", dbxFr);
					dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(26-30)");
				}
			} else if(edad_integer == 2){
				if(frecuencia != 25){
					MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(25)", dbxFr);
					dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(25)");
				}
			} else if(edad_integer == 3){ 
				if(frecuencia != 25){
					MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(25)", dbxFr);
					dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(25)");
				}
			} else if(edad_integer >= 4 && edad_integer <= 8){
				if(frecuencia < 20 || frecuencia > 25){
					MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(20-25)", dbxFr);
					dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(20-25)");
				}
			} else if(edad_integer >= 9 && edad_integer <= 15){
				if(frecuencia < 18 || frecuencia > 20){
					MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(18-20)", dbxFr);
					dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(18-20)");
				}
			} else{
				if(genero.equals("M")){
					if(edad_integer >= 16 && edad_integer <= 55){
						if(frecuencia < 16 || frecuencia > 20){
							MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(16-20)", dbxFr);
							dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(16-20)");
						}
					}else{
						if(frecuencia < 14 || frecuencia > 16){
							MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(14-16)", dbxFr);
							dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(14-16)");
						}
					}
				}else{
					if(edad_integer >= 16 && edad_integer <= 65){
						if(frecuencia < 16 || frecuencia > 20){
							MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(16-20)", dbxFr);
							dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(16-20)");
						}
					}else{
						if(frecuencia < 14 || frecuencia > 16){
							MensajesUtil.notificarAlerta("Frecuencia respiratoria anormal RI(14-16)", dbxFr);
							dbxFr.setTooltiptext("Frecuencia respiratoria anormal RI(14-16)");
						}
					}
				}
			}
		} else {
			dbxFr.setTooltiptext(null);
		}
	}
	
	public static void onMostrarAlertaTemperatura(Doublebox dbxTemperatura, Date fecha_nacimiento, String genero) {
		dbxTemperatura.setTooltiptext(null);
		if (dbxTemperatura.getValue() != null) {
			String edad = Util
					.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy")
							.format(fecha_nacimiento), "1", false);
			int edad_integer = Integer.parseInt(edad);
			double temperatura = dbxTemperatura.getValue();
			if (edad_integer < 1) {
				if(temperatura < 36.6 || temperatura > 37.8){
					MensajesUtil.notificarAlerta("Temperatura anormal RI(36.6-37.8)", dbxTemperatura);
					dbxTemperatura.setTooltiptext("Temperatura anormal RI(36.6-37.8)");
				}
			} else if(edad_integer == 1){
				if(temperatura < 36.6 || temperatura > 37.8){
					MensajesUtil.notificarAlerta("Temperatura anormal RI(36.6-37.8)", dbxTemperatura);
					dbxTemperatura.setTooltiptext("Temperatura anormal RI(36.6-37.8)");
				}
			} else if(edad_integer == 2){
				if(temperatura < 36.6 || temperatura > 37.8){
					MensajesUtil.notificarAlerta("Temperatura anormal RI(36.6-37.8)", dbxTemperatura);
					dbxTemperatura.setTooltiptext("Temperatura anormal RI(36.6-37.8)");
				}
			} else if(edad_integer == 3){ 
				if(temperatura < 36.6 || temperatura > 37.8){
					MensajesUtil.notificarAlerta("Temperatura anormal RI(36.6-37.8)", dbxTemperatura);
					dbxTemperatura.setTooltiptext("Temperatura anormal RI(36.6-37.8)");
				}
			} else if(edad_integer >= 4 && edad_integer <= 8){
				if(temperatura < 36.5 || temperatura > 37){
					MensajesUtil.notificarAlerta("Temperatura anormal RI(36.5-37)", dbxTemperatura);
					dbxTemperatura.setTooltiptext("Temperatura anormal RI(36.5-37)");
				}
			} else if(edad_integer >= 9 && edad_integer <= 15){
				if(temperatura < 36.5 || temperatura > 37){
					MensajesUtil.notificarAlerta("Temperatura anormal RI(36.5-37)", dbxTemperatura);
					dbxTemperatura.setTooltiptext("Temperatura anormal RI(36.5-37)");
				}
			} else{
				if(genero.equals("M")){
					if(edad_integer >= 16 && edad_integer <= 55){
						if(temperatura != 36.5){
							MensajesUtil.notificarAlerta("Temperatura anormal RI(36.5)", dbxTemperatura);
							dbxTemperatura.setTooltiptext("Temperatura anormal RI(36.5)");
						}
					}else{
						if(temperatura > 36){
							MensajesUtil.notificarAlerta("Temperatura anormal RI(<=36)", dbxTemperatura);
							dbxTemperatura.setTooltiptext("Temperatura anormal RI(<=36)");
						}
					}
				}else{
					if(edad_integer >= 16 && edad_integer <= 65){
						if(temperatura != 36.5){
							MensajesUtil.notificarAlerta("Temperatura anormal RI(36.5)", dbxTemperatura);
							dbxTemperatura.setTooltiptext("Temperatura anormal RI(36.5)");
						}
					}else{
						if(temperatura > 36){
							MensajesUtil.notificarAlerta("Temperatura anormal RI(<=36)", dbxTemperatura);
							dbxTemperatura.setTooltiptext("Temperatura anormal RI(<=36)");
						}
					}
				}
			}
		} else {
			dbxTemperatura.setTooltiptext(null);
		}
	}
	
	public static void onMostrarAlertaTemperaturaEmbarazo(Doublebox dbxTemperatura) {
		dbxTemperatura.setTooltiptext(null);
		if (dbxTemperatura.getValue() != null) {
				Double temperatura = dbxTemperatura.getValue();
				if(temperatura >= 38){
					MensajesUtil.notificarAlerta("Temperatura anormal", dbxTemperatura);
					dbxTemperatura.setTooltiptext("Temperatura anormal");
				} else {
					dbxTemperatura.setTooltiptext(null);
				}		
			}		
	}
	
	public static void mostrarAlertasSignosVitalesDE(Doublebox doublebox,Timestamp fecha_nacimiento, String tipo_alerta){
		
		String posicion = "before_center";
		Integer tiempo = 4000;
		
		String[] coloresTexto = {"#B35900","#99001A","#009933"};
		String[] coloresFondo = {"#FFBEA8","#FFED9E","#E0FF9E"};
		String mensaje = "";
		
//		Integer  edad = 	Integer.valueOf(Util.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha_nacimiento),"1", false));
//		Integer  edad_meses = 	Integer.valueOf(Util.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha_nacimiento),"2", false));
		
		Long valor = Math.round(doublebox.getValue());
		
		
		
		if(tipo_alerta.equalsIgnoreCase(DE_TALLA_EDAD)){
			if(valor< -2){
				mensaje = "Talla baja para la edad o retraso en talla (Remitir)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
			}else if (valor >= -2 && valor < -1){
				mensaje = "Riesgo de talla baja (Educacion)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[1]+";font-weight:bold;text-align:center;background:"+coloresFondo[1]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_WARNING, doublebox,posicion, tiempo, true);
			}else if(valor >= -1){
				doublebox.setStyle("color:black;font-weight:bold;text-align:center;background:white");
				doublebox.setTooltiptext("");
			}else{
				doublebox.setStyle("color:black;font-weight:bold;text-align:center;background:white");
				doublebox.setTooltiptext("");
			}
		}else if(tipo_alerta.equalsIgnoreCase(DE_PESO_EDAD)){
			if(valor< -3){
				mensaje = "Peso muy bajo para la edad o esnutricion global severa (Remitir)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
			}else if(valor< -2){
				mensaje = "Peso bajo para la edad o desnutricion global (Remitir)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
			}else if (valor >= -2 && valor < -1){
				mensaje = "Riesgo de peso bajo para la edad (Educacion)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[1]+";font-weight:bold;text-align:center;background:"+coloresFondo[1]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_WARNING, doublebox,posicion, tiempo, true);
			}else if(valor >= -1 && valor<=1){
				doublebox.setStyle("color:black;font-weight:bold;text-align:center;background:white");
				doublebox.setTooltiptext("");
			}else{
				doublebox.setStyle("color:black;font-weight:bold;text-align:center;background:white");
				doublebox.setTooltiptext("");
			}
		}else if(tipo_alerta.equalsIgnoreCase(DE_PESO_TALLA)){
			if(valor<= -3){
				mensaje = "Peso muy bajo para la talla o desnutricion aguda severa (Remitir)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
			}else if(valor< -2){
				mensaje = "Peso bajo para la talla o desnutricion aguda (Remitir)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
			}else if (valor >= -2 && valor < -1){
				mensaje = "Riesgo de peso bajo para la talla (Educacion)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[1]+";font-weight:bold;text-align:center;background:"+coloresFondo[1]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_WARNING, doublebox,posicion, tiempo, true);
			}else if(valor > 1 && valor<=2){
				mensaje = "Sobrepeso (Remitir)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
			}else if(valor >2){
				mensaje = "Obesidad (Remitir)";
				doublebox.setTooltiptext(mensaje);
				doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
				Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
			}else if(valor>=-1 && valor <= 1){
				doublebox.setStyle("color:black;font-weight:bold;text-align:center;background:white");
				doublebox.setTooltiptext("");
			}else{
				doublebox.setStyle("color:black;font-weight:bold;text-align:center;background:white");
				doublebox.setTooltiptext("");
			}
		}else if(tipo_alerta.equalsIgnoreCase(DE_IMC_EDAD)){
			 if(valor > 1 && valor<=2){
					mensaje = "Sobrepeso (Educacion)";
					doublebox.setTooltiptext(mensaje);
					doublebox.setStyle("color:"+coloresTexto[1]+";font-weight:bold;text-align:center;background:"+coloresFondo[1]);
					Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_WARNING, doublebox,posicion, tiempo, true);
				}else if(valor >2){
					mensaje = "Obesidad (Remitir)";
					doublebox.setTooltiptext(mensaje);
					doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
					Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
				}else if(valor <=-1 && valor >-2){
					mensaje = "Riesgo de desnutricion (Educacion)";
					doublebox.setTooltiptext(mensaje);
					doublebox.setStyle("color:"+coloresTexto[1]+";font-weight:bold;text-align:center;background:"+coloresFondo[1]);
					Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_WARNING, doublebox,posicion, tiempo, true);
				}else if(valor <=-2 && valor >-3){
					mensaje = "Desnutricion (Remitir)";
					doublebox.setTooltiptext(mensaje);
					doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
					Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
				}else if(valor <=-3){
						mensaje = "Desnutricion Severa (Remitir)";
						doublebox.setTooltiptext(mensaje);
						doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
						Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
					
				}else{
					doublebox.setStyle("color:black;font-weight:bold;text-align:center;background:white");
					doublebox.setTooltiptext("");
				}
		}else if(tipo_alerta.equalsIgnoreCase(DE_PC_EDAD)){
			 if(valor< -2){
					mensaje = "Factor de riesgo para el neurodesarrollo (Educacion)";
					doublebox.setTooltiptext(mensaje);
					doublebox.setStyle("color:"+coloresTexto[1]+";font-weight:bold;text-align:center;background:"+coloresFondo[1]);
					Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_WARNING, doublebox,posicion, tiempo, true);
				}else if(valor <-2){
					doublebox.setStyle("color:black;font-weight:bold;text-align:center;background:white");
					doublebox.setTooltiptext("");
				}else if(valor >2){
					mensaje = "Factor de riesgo para el neurodesarrollo (Educacion)";
					doublebox.setTooltiptext(mensaje);
					doublebox.setStyle("color:"+coloresTexto[1]+";font-weight:bold;text-align:center;background:"+coloresFondo[1]);
					Clients.showNotification(mensaje,Clients.NOTIFICATION_TYPE_WARNING, doublebox,posicion, tiempo, true);
				}else{
					doublebox.setStyle("color:black;font-weight:bold;text-align:center;background:white");
					doublebox.setTooltiptext("");
				}
		}		
	}
	
	public static void mostrarAlertasSignosVitalesDE(Doublebox doublebox){
		String posicion = "before_center";
		Integer tiempo = 4000;
		
		String[] coloresTexto = {"#B35900","#99001A","#009933"};
		String[] coloresFondo = {"#FFBEA8","#FFED9E","#E0FF9E"};
		
		String[] mensajes = {"Remitir", "Educacion", "Paciente en buenas condiciones"};
		Long valor = Math.round(doublebox.getValue());
		
		if(valor<=-3 || valor>=3){
			doublebox.setTooltiptext(mensajes[0]);
			doublebox.setStyle("color:"+coloresTexto[0]+";font-weight:bold;text-align:center;background:"+coloresFondo[0]);
			Clients.showNotification(mensajes[0],Clients.NOTIFICATION_TYPE_ERROR, doublebox,posicion, tiempo, true);
		}else if(valor ==-2 || valor==2){
			doublebox.setTooltiptext(mensajes[1]);
			doublebox.setStyle("color:"+coloresTexto[1]+";font-weight:bold;text-align:center;background:"+coloresFondo[1]);
			Clients.showNotification(mensajes[1],Clients.NOTIFICATION_TYPE_WARNING, doublebox,posicion, tiempo, true);
		}else if(valor <=1 && valor>-2){
//			doublebox.setStyle("color:"+coloresTexto[2]+";font-weight:bold;text-align:center;background:"+coloresFondo[2]);
//			doublebox.setTooltiptext(mensajes[2]);
//			Clients.showNotification(mensajes[2],Clients.NOTIFICATION_TYPE_INFO, doublebox,posicion, tiempo, true);
		}
	}
	
	public static void validarMaxMin(Doublebox dbxValor, double max, double min, String alerta_max, String alerta_min, Boolean obligatorio){
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		String NOTIFICACION = Clients.NOTIFICATION_TYPE_WARNING;
		String mensaje = "";
		String color_alerta = "#FFBEA8";

		if(obligatorio){
			if(dbxValor==null || dbxValor.getText().isEmpty()){
				mensaje = "Debe digitar este campo";
				Clients.showNotification(mensaje, NOTIFICACION, dbxValor, POSICION_ALERTA, TIEMPO_ALERTA, true);
				dbxValor.setFocus(true);
				return;
			}		
		}

		if(dbxValor!=null && !dbxValor.getText().isEmpty()){
			Double valor = dbxValor.getValue();
			////log.info(">>>>>>>>>>>>>>>>>>>>>Valor: "+valor+" min: "+min +" max: "+max );
			if(valor <= min){
				mensaje = alerta_min;
				Clients.showNotification(mensaje, NOTIFICACION, dbxValor, POSICION_ALERTA, TIEMPO_ALERTA, true);
				dbxValor.setStyle("text-transform:uppercase;background-color:"+color_alerta);
				dbxValor.setFocus(false);
			}else if(valor>=max){
				mensaje = alerta_max;
				Clients.showNotification(mensaje, NOTIFICACION, dbxValor, POSICION_ALERTA, TIEMPO_ALERTA, true);
				dbxValor.setStyle("text-transform:uppercase;background-color:"+color_alerta);
				dbxValor.setFocus(false);
			}else{
				dbxValor.setStyle("text-transform:uppercase;background-color:white");
			}
		}
	}
	
	public static void validarTFG(Doublebox dbxValor){
//		ESTADIO 1: Daño renal (proteínas en la orina) con filtrado glomerular normal (>90).
//		ESTADIO 2: Daño renal con leve descenso del filtrado glomerular (entre 60 y 89).
//		ESTADIO 3: Descenso moderado del filtrado glomerular (entre 30 y 59).
//		ESTADIO 4: Descenso severo del filtrado glomerular (entre 15 y 29).
//		ESTADIO 5: Este estadío se considera fallo renal. El filtrado glomerular se encuentra por debajo de 15.
		
		if(dbxValor != null){
			
			String POSICION_ALERTA = "end_before";
			Integer TIEMPO_ALERTA = 4000;
			String NOTIFICACION = Clients.NOTIFICATION_TYPE_WARNING;
			String mensaje = "";
			String color_alerta = "#FFBEA8";
			
			Double valor = dbxValor.getValue();

			if(valor>90){
				mensaje = "ESTADIO 1: Daño renal (proteínas en la orina) con filtrado glomerular normal.";
				Clients.showNotification(mensaje, NOTIFICACION, dbxValor, POSICION_ALERTA, TIEMPO_ALERTA, true);
				dbxValor.setStyle("text-transform:uppercase;background-color:"+color_alerta);
				dbxValor.setFocus(false);	
			}else if (valor >=60 && valor <=89){
				mensaje = "ESTADIO 2: Daño renal con leve descenso del filtrado glomerular.";
				Clients.showNotification(mensaje, NOTIFICACION, dbxValor, POSICION_ALERTA, TIEMPO_ALERTA, true);
				dbxValor.setStyle("text-transform:uppercase;background-color:"+color_alerta);
				dbxValor.setFocus(false);	
			}else if (valor >=30 && valor <=59){
				mensaje = "ESTADIO 3: Descenso moderado del filtrado glomerular.";
				Clients.showNotification(mensaje, NOTIFICACION, dbxValor, POSICION_ALERTA, TIEMPO_ALERTA, true);
				dbxValor.setStyle("text-transform:uppercase;background-color:"+color_alerta);
				dbxValor.setFocus(false);	
			}else if (valor >=15 && valor <=29){
				mensaje = "ESTADIO 4: Descenso severo del filtrado glomerular.";
				Clients.showNotification(mensaje, NOTIFICACION, dbxValor, POSICION_ALERTA, TIEMPO_ALERTA, true);
				dbxValor.setStyle("text-transform:uppercase;background-color:"+color_alerta);
				dbxValor.setFocus(false);	
			}else if (valor < 15){
				mensaje = "ESTADIO 5: Fallo renal.";
				Clients.showNotification(mensaje, NOTIFICACION, dbxValor, POSICION_ALERTA, TIEMPO_ALERTA, true);
				dbxValor.setStyle("text-transform:uppercase;background-color:"+color_alerta);
				dbxValor.setFocus(false);	
			}else{
				dbxValor.setStyle("text-transform:uppercase;background-color:#ffffff");
			}

		}	
	}
	
	public static Double calculoTFGCockcroft(Double creatinina, Integer edad, Double peso, Boolean mujer){
		//FG = [140 - edad]*peso(kg)/[creatinina plasmática*72]*0,85, si mujer
		Double tfg = (140-edad)*peso/(creatinina*72);
		if(mujer){
			tfg *= 0.85;
		}
		return tfg;
	}
	
	public static Double calculoTFG(Double creatinina, Integer edad, Boolean mujer, Boolean afro){
		//186 x Creatinina-1.154 x Edad^-0.203 x 1.21 si es afroamericano x 0.742 si es mujer
		Double tfg = 186*Math.pow(creatinina, -1.154)*Math.pow(edad,-0.203);
		if(mujer){
			tfg *= 0.742;
		}
		if(afro){
			tfg *= 1.21;
		}
		return tfg;
	}
}
