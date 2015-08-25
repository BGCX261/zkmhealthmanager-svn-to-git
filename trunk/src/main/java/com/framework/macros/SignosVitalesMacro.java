package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Parametros_signos;
import healthmanager.modelo.bean.Sigvitales;
import healthmanager.modelo.service.Parametros_signosService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;

public class SignosVitalesMacro extends Grid implements AfterCompose {

	private static Logger log = Logger.getLogger(SignosVitalesMacro.class);

	private Textbox mcTbxConfig_talla;
	private Label mcLbTalla;
	/**
	 * Indica como va a trabajar la estatura o talla. Es decir si la va a
	 * trabajar por Metros, o por Centimetros. M - Metros, C - Centimetros
	 */
	private String CONFIG_TALLA;

	private Textbox mcTbxConfig_edad;
	/**
	 * Indica como va a trabajar la edad. Es decir si la va a trabajar por Años,
	 * por meses o por dias 1 - Anios, 2 - Meses, 3 - Dias
	 */
	private String CONFIG_EDAD;
	private Date fecha_nacimiento;
	private Integer edad_paciente;
	private String genero;

	private Doublebox mcDbxFrecuencia_cardiaca;
	private Doublebox mcDbxFrecuencia_respiratoria;
	private Doublebox mcDbxTemparatura;
	private Doublebox mcDbxTA_sistolica;
	private Doublebox mcDbxTA_diastolica;
	private Doublebox mcDbxTA_media;
	private Doublebox mcDbxSuperficie_corporal;
	private Doublebox mcDbxPeso;
	private Doublebox mcDbxTalla;
	private Doublebox mcDbxIMC;
	private Doublebox mcDbxCreatinina_serica;
	private Doublebox mcDbxTFG;
	private Doublebox mcDbxPerimetro_cefalico;
	private Doublebox mcDbxPerimetro_toraxico;
	private Hlayout hlayoutPerimetros;

	private ZKWindow zkWindow;
	private Sigvitales sigvitales;

	private String alerta_imc = "";

	private Parametros_signos parametros_signos;

	private boolean perimetro_obligatorio = true;

	@Override
	public void afterCompose() {
		cargarComponentes();
		configurarEdad();
		configurarTalla();
	}

	private void cargarComponentes() {
		mcTbxConfig_talla = (Textbox) this.getFellow("mcTbxConfig_talla");
		mcTbxConfig_edad = (Textbox) this.getFellow("mcTbxConfig_edad");
		mcLbTalla = (Label) this.getFellow("mcLbTalla");
		mcDbxFrecuencia_cardiaca = (Doublebox) this
				.getFellow("mcDbxFrecuencia_cardiaca");
		mcDbxFrecuencia_respiratoria = (Doublebox) this
				.getFellow("mcDbxFrecuencia_respiratoria");
		mcDbxTemparatura = (Doublebox) this.getFellow("mcDbxTemparatura");
		mcDbxTA_sistolica = (Doublebox) this.getFellow("mcDbxTA_sistolica");
		mcDbxTA_diastolica = (Doublebox) this.getFellow("mcDbxTA_diastolica");
		mcDbxTA_media = (Doublebox) this.getFellow("mcDbxTA_media");
		mcDbxSuperficie_corporal = (Doublebox) this
				.getFellow("mcDbxSuperficie_corporal");
		mcDbxPeso = (Doublebox) this.getFellow("mcDbxPeso");
		mcDbxTalla = (Doublebox) this.getFellow("mcDbxTalla");
		mcDbxIMC = (Doublebox) this.getFellow("mcDbxIMC");
		mcDbxCreatinina_serica = (Doublebox) this
				.getFellow("mcDbxCreatinina_serica");
		mcDbxTFG = (Doublebox) this.getFellow("mcDbxTFG");
		mcDbxPerimetro_cefalico = (Doublebox) this
				.getFellow("mcDbxPerimetro_cefalico");
		mcDbxPerimetro_toraxico = (Doublebox) this
				.getFellow("mcDbxPerimetro_toraxico");
		hlayoutPerimetros = (Hlayout) this.getFellow("hlayoutPerimetros");
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
					if(edad_paciente>10){
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
					alerta_imc = alerta;
				} else {
					mcDbxIMC.setTooltiptext(null);
					mcDbxIMC.setStyle("background-color:white");
					alerta_imc = "";
				}
			} else {
				mcDbxIMC.setTooltiptext(null);
				mcDbxIMC.setStyle("background-color:white");
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

	public void onCalcularTasaFiltracionGlomerural() {
		try {
			mcDbxTFG.setConstraint("");
			mcDbxTFG.setValue(null);
			Double talla = mcDbxTalla.getValue();
			Double peso = mcDbxPeso.getValue();
			Double superfice_corporal = mcDbxSuperficie_corporal.getValue();
			Double creatinina = mcDbxCreatinina_serica.getValue();
			if (talla != null && peso != null && superfice_corporal != null
					&& creatinina != null && fecha_nacimiento != null
					&& genero != null) {
				String edad = Util
						.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy")
								.format(this.fecha_nacimiento), "1", false);

				int edad_anios = Integer.parseInt(edad);

				double tfg_parcial = ((140 - edad_anios) * peso.doubleValue())
						/ (72 * creatinina.doubleValue());

				double tfg_final = tfg_parcial;
				if (genero.equalsIgnoreCase("F")) {
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

	private void configurarTalla() {
		if (mcTbxConfig_talla.getValue() != null) {
			String config_talla = mcTbxConfig_talla.getValue();
			if (config_talla.equalsIgnoreCase("C")) {
				mcLbTalla.setValue("(Cm)");
				mcLbTalla.setTooltiptext("Centrimetros");
				CONFIG_TALLA = "C";
			} else {
				mcLbTalla.setValue("(Mts)");
				mcLbTalla.setTooltiptext("Metros");
				CONFIG_TALLA = "M";
			}
		} else {
			mcLbTalla.setValue("(Mts)");
			mcLbTalla.setTooltiptext("Metros");
			CONFIG_TALLA = "M";
		}
	}

	private void configurarEdad() {
		if (mcTbxConfig_edad.getValue() != null) {
			String config_edad = mcTbxConfig_edad.getValue();
			if (config_edad.equalsIgnoreCase("D")) {
				CONFIG_EDAD = "3";
			} else if (config_edad.equalsIgnoreCase("M")) {
				CONFIG_EDAD = "2";
			} else {
				CONFIG_EDAD = "1";
			}
		} else {
			CONFIG_EDAD = "1";
		}
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
		String edad = Util.getEdad(new java.text.SimpleDateFormat("dd/MM/yyyy")
				.format(this.fecha_nacimiento), CONFIG_EDAD, false);
		if (edad != null && !edad.isEmpty()) {
			int edad_integer = Integer.parseInt(edad);
			if (CONFIG_EDAD.equals("1")) {
				hlayoutPerimetros.setVisible((edad_integer <= 10));
				mcDbxPerimetro_cefalico.setVisible((edad_integer <= 10));
				mcDbxPerimetro_toraxico.setVisible((edad_integer <= 10));
				if (edad_integer < 4) {
					perimetro_obligatorio = true;
				}
			} else if (CONFIG_EDAD.equals("2")) {
				hlayoutPerimetros.setVisible((edad_integer <= 120));
				mcDbxPerimetro_cefalico.setVisible((edad_integer <= 120));
				mcDbxPerimetro_toraxico.setVisible((edad_integer <= 120));
				if (edad_integer < 48) {
					perimetro_obligatorio = true;
				}
			} else if (CONFIG_EDAD.equals("3")) {
				hlayoutPerimetros.setVisible((edad_integer <= 3650));
				mcDbxPerimetro_cefalico.setVisible((edad_integer <= 3650));
				mcDbxPerimetro_toraxico.setVisible((edad_integer <= 3650));
				if (edad_integer < 1460) {
					perimetro_obligatorio = true;
				}
			}
			this.edad_paciente = edad_integer;
		} else {
			hlayoutPerimetros.setVisible(true);
			mcDbxPerimetro_cefalico.setVisible(true);
			mcDbxPerimetro_toraxico.setVisible(true);
		}
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public Sigvitales obtenerSigvitales() throws Exception {
		if (perimetro_obligatorio) {
			FormularioUtil.validarCamposObligatorios(mcDbxFrecuencia_cardiaca,
					mcDbxFrecuencia_respiratoria, mcDbxTemparatura,
					mcDbxTA_sistolica, mcDbxTA_diastolica, mcDbxTA_media,
					mcDbxSuperficie_corporal, mcDbxPeso, mcDbxTalla, mcDbxIMC,
					mcDbxPerimetro_cefalico, mcDbxPerimetro_toraxico);
		} else {
			FormularioUtil.validarCamposObligatorios(mcDbxFrecuencia_cardiaca,
					mcDbxFrecuencia_respiratoria, mcDbxTemparatura,
					mcDbxTA_sistolica, mcDbxTA_diastolica, mcDbxTA_media,
					mcDbxSuperficie_corporal, mcDbxPeso, mcDbxTalla, mcDbxIMC);
		}
		sigvitales = new Sigvitales();
		sigvitales.setCodigo_empresa(zkWindow.codigo_empresa);
		sigvitales.setCodigo_sucursal(zkWindow.codigo_sucursal);
		sigvitales.setEdad(edad_paciente);
		sigvitales.setCreatinina_cerica(mcDbxCreatinina_serica.getValue());
		sigvitales.setFrecuencia_cardiaca(mcDbxFrecuencia_cardiaca.getValue());
		sigvitales.setFrecuencia_respiratoria(mcDbxFrecuencia_respiratoria
				.getValue());
		sigvitales.setImc(mcDbxIMC.getValue());
		sigvitales.setPerimetro_cefalico(mcDbxPerimetro_cefalico.getValue());
		sigvitales.setPerimetro_toraxico(mcDbxPerimetro_toraxico.getValue());
		sigvitales.setPeso(mcDbxPeso.getValue());
		sigvitales.setSuperficie_corporal(mcDbxSuperficie_corporal.getValue());
		sigvitales.setTa_diastolica(mcDbxTA_diastolica.getValue());
		sigvitales.setTa_media(mcDbxTA_media.getValue());
		sigvitales.setTa_sistolica(mcDbxTA_sistolica.getValue());
		sigvitales.setTalla(mcDbxTalla.getValue());
		sigvitales.setTemparatura(mcDbxTemparatura.getValue());
		sigvitales.setTfg(mcDbxTFG.getValue());

		return sigvitales;
	}

	public void mostrarSigvitales(Sigvitales sigvitales) {
		this.sigvitales = sigvitales;
		if (this.sigvitales == null) {
			this.sigvitales = new Sigvitales();
			this.sigvitales.setCodigo_empresa(zkWindow.codigo_empresa);
			this.sigvitales.setCodigo_sucursal(zkWindow.codigo_sucursal);
			sigvitales = this.sigvitales;
		}
		edad_paciente = sigvitales.getEdad();
		mcDbxCreatinina_serica.setValue(sigvitales.getCreatinina_cerica());
		mcDbxFrecuencia_cardiaca.setValue(sigvitales.getFrecuencia_cardiaca());
		mcDbxFrecuencia_respiratoria.setValue(sigvitales
				.getFrecuencia_respiratoria());
		mcDbxIMC.setValue(sigvitales.getImc());
		mcDbxPerimetro_cefalico.setValue(sigvitales.getPerimetro_cefalico());
		mcDbxPerimetro_toraxico.setValue(sigvitales.getPerimetro_toraxico());
		mcDbxPeso.setValue(sigvitales.getPeso());
		mcDbxSuperficie_corporal.setValue(sigvitales.getSuperficie_corporal());
		mcDbxTA_diastolica.setValue(sigvitales.getTa_diastolica());
		mcDbxTA_media.setValue(sigvitales.getTa_media());
		mcDbxTA_sistolica.setValue(sigvitales.getTa_sistolica());
		mcDbxTalla.setValue(sigvitales.getTalla());
		mcDbxTemparatura.setValue(sigvitales.getTemparatura());
		mcDbxTFG.setValue(sigvitales.getTfg());
		// onMostrarAlertaTension();
		// onMostrarAlertaTemperatura();
		// onCalcularIMC();

	}
	
	public void mostrarSigvitalesInformacion(Sigvitales sigvitales) {
		mcDbxCreatinina_serica.setValue(sigvitales.getCreatinina_cerica());
		mcDbxFrecuencia_cardiaca.setValue(sigvitales.getFrecuencia_cardiaca());
		mcDbxFrecuencia_respiratoria.setValue(sigvitales
				.getFrecuencia_respiratoria());
		mcDbxIMC.setValue(sigvitales.getImc());
		mcDbxPerimetro_cefalico.setValue(sigvitales.getPerimetro_cefalico());
		mcDbxPerimetro_toraxico.setValue(sigvitales.getPerimetro_toraxico());
		mcDbxPeso.setValue(sigvitales.getPeso());
		mcDbxSuperficie_corporal.setValue(sigvitales.getSuperficie_corporal());
		mcDbxTA_diastolica.setValue(sigvitales.getTa_diastolica());
		mcDbxTA_media.setValue(sigvitales.getTa_media());
		mcDbxTA_sistolica.setValue(sigvitales.getTa_sistolica());
		mcDbxTalla.setValue(sigvitales.getTalla());
		mcDbxTemparatura.setValue(sigvitales.getTemparatura());
		mcDbxTFG.setValue(sigvitales.getTfg());
		// onMostrarAlertaTension();
		// onMostrarAlertaTemperatura();
		// onCalcularIMC();
	}

	public void inicializarParametrosAlertas() {
		if (zkWindow != null && genero != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", zkWindow.codigo_empresa);
			parametros.put("sexo", genero);
			List<Parametros_signos> listado_parametros = zkWindow
					.getServiceLocator()
					.getServicio(Parametros_signosService.class)
					.listar(parametros);
			parametros_signos = obtenerParametrosSignos(listado_parametros,
					fecha_nacimiento);
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

	public void setZkWindow(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
	}

	public ZKWindow getZkWindow() {
		return zkWindow;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	// este metodo va para formulario util

	public Label getLabelCreatinina() {
		return (Label) this.getFellow("mcLbCreatinina_serica");
	}

	public Doublebox getDoubleboxCreatinina() {
		return (Doublebox) this.getFellow("mcDbxCreatinina_serica");
	}

	public Label getLabelTFG() {
		return (Label) this.getFellow("mcLbTFG");
	}

	public Doublebox getDoubleboxTFG() {
		return (Doublebox) this.getFellow("mcDbxTFG");
	}

	public String getFrecuencia_cardiaca() {
		return mcDbxFrecuencia_cardiaca.getText();
	}

	public String getFrecuencia_respiratoria() {
		return mcDbxFrecuencia_respiratoria.getText();
	}

	public String getTemperatura() {
		return mcDbxTemparatura.getText();
	}

	public String getPeso() {
		return mcDbxPeso.getText();
	}

	public String getTalla() {
		if (mcDbxTalla.getText().isEmpty()) {
			return "";
		}
		return mcDbxTalla.getText() + " " + mcLbTalla.getValue();
	}

	public String getIMC() {
		return mcDbxIMC.getText();
	}

	public String getAlerta_imc() {
		return alerta_imc;
	}

	public static Logger getLog() {
		return log;
	}

	public String getMcDbxFrecuencia_cardiaca() {
		return mcDbxFrecuencia_cardiaca.getText();
	}

	public String getMcDbxFrecuencia_respiratoria() {
		return mcDbxFrecuencia_respiratoria.getText();
	}

	public String getMcDbxTemparatura() {
		return mcDbxTemparatura.getText();
	}

	public String getMcDbxTA_sistolica() {
		return mcDbxTA_sistolica.getText();
	}

	public String getMcDbxTA_diastolica() {
		return mcDbxTA_diastolica.getText();
	}

	public String getMcDbxTA_media() {
		return mcDbxTA_media.getText();
	}

	public String getMcDbxSuperficie_corporal() {
		return mcDbxSuperficie_corporal.getText();
	}

	public String getMcDbxCreatinina_serica() {
		return mcDbxCreatinina_serica.getText();
	}

	public String getMcDbxTFG() {
		return mcDbxTFG.getText();
	}

	public String getMcDbxPerimetro_cefalico() {
		return mcDbxPerimetro_cefalico.getText();
	}

	public String getMcDbxPerimetro_toraxico() {
		return mcDbxPerimetro_toraxico.getText();
	}

}
