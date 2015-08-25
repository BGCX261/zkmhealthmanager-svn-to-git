package com.framework.macros;

import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;

public class GlasgowMacro extends Grid implements AfterCompose {

	private static Logger log = Logger.getLogger(GlasgowMacro.class);

	private Radiogroup mcRgpRespuesta_motora;
	private Radiogroup mcRgpRespuesta_verbal;
	private Radiogroup mcRgpApertura_ocular;

	private Intbox mcIbxTotal_puntos;
	private Textbox mcTbxGrado_estado;
	private Textbox mcTbxValoracion;

	@Override
	public void afterCompose() {
		cargarComponentes();
	}

	private void cargarComponentes() {
		mcRgpRespuesta_motora = (Radiogroup) this
				.getFellow("mcRgpRespuesta_motora");
		mcRgpRespuesta_verbal = (Radiogroup) this
				.getFellow("mcRgpRespuesta_verbal");
		mcRgpApertura_ocular = (Radiogroup) this
				.getFellow("mcRgpApertura_ocular");
		mcIbxTotal_puntos = (Intbox) this.getFellow("mcIbxTotal_puntos");
		mcTbxGrado_estado = (Textbox) this.getFellow("mcTbxGrado_estado");
		mcTbxValoracion = (Textbox) this.getFellow("mcTbxValoracion");
	}

	public void onCalcularGlasgow(Boolean alerta) {
		try {
			if (mcRgpRespuesta_motora.getSelectedItem() != null
					&& mcRgpRespuesta_verbal.getSelectedItem() != null
					&& mcRgpApertura_ocular.getSelectedItem() != null) {
				Radio respuesta_motora = mcRgpRespuesta_motora
						.getSelectedItem();
				Radio respuesta_verbal = mcRgpRespuesta_verbal
						.getSelectedItem();
				Radio apertura_ocular = mcRgpApertura_ocular.getSelectedItem();

				Integer total = Integer.parseInt(respuesta_motora.getValue()
						.toString())
						+ Integer.parseInt(respuesta_verbal.getValue()
								.toString())
						+ Integer.parseInt(apertura_ocular.getValue()
								.toString());

				mcIbxTotal_puntos.setValue(total);

				if (total >= 14) {
					mcTbxValoracion.setStyle("background-color:#298A08");
					if (alerta) {
						MensajesUtil.notificarInformacion("Normal",
								mcTbxValoracion);
					}
					mcTbxValoracion.setTooltiptext("Normal");
				} else if (total < 14 && total >= 9) {
					mcTbxValoracion.setStyle("background-color:#D7DF01");
					if (alerta) {
						MensajesUtil.notificarAlerta("Moderado",
								mcTbxValoracion);
					}
					mcTbxValoracion.setTooltiptext("Moderado");
				} else {
					mcTbxValoracion.setStyle("background-color:#DF3A01");
					if (alerta) {
						MensajesUtil.notificarError("Grave", mcTbxValoracion);
					}
					mcTbxValoracion.setTooltiptext("Grave");
				}

				mcTbxValoracion.setValue(validarTotalPuntuacion(total));

			} else {
				mcIbxTotal_puntos.setText("");
				mcTbxGrado_estado.setValue("");
				mcTbxGrado_estado.setStyle("background-color:white");
				mcTbxGrado_estado.setTooltiptext(null);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public Integer obtenerRespuestaMotora() {
		if (mcRgpRespuesta_motora.getSelectedItem() != null) {
			return Integer.parseInt(mcRgpRespuesta_motora.getSelectedItem()
					.getValue().toString());
		}
		return 0;
	}

	public Integer obtenerRespuestaVerbal() {
		if (mcRgpRespuesta_verbal.getSelectedItem() != null) {
			return Integer.parseInt(mcRgpRespuesta_verbal.getSelectedItem()
					.getValue().toString());
		}
		return 0;
	}

	public Integer obtenerAperturaOcular() {
		if (mcRgpApertura_ocular.getSelectedItem() != null) {
			return Integer.parseInt(mcRgpApertura_ocular.getSelectedItem()
					.getValue().toString());
		}
		return 0;
	}

	public void mostrarEscalaGlasgow(Integer respuesta_motora,
			Integer respuesta_verbal, Integer apertura_ocular, Boolean alerta) {
		if (respuesta_motora != null) {
			List<Radio> listado_radios = mcRgpRespuesta_motora.getItems();
			for (Radio radio : listado_radios) {
				if (radio.getValue().toString()
						.equals(respuesta_motora.toString())) {
					radio.setChecked(true);
					break;
				}
			}
		}

		if (respuesta_verbal != null) {
			List<Radio> listado_radios = mcRgpRespuesta_verbal.getItems();
			for (Radio radio : listado_radios) {
				if (radio.getValue().toString()
						.equals(respuesta_verbal.toString())) {
					radio.setChecked(true);
					break;
				}
			}
		}

		if (apertura_ocular != null) {
			List<Radio> listado_radios = mcRgpApertura_ocular.getItems();
			for (Radio radio : listado_radios) {
				if (radio.getValue().toString()
						.equals(apertura_ocular.toString())) {
					radio.setChecked(true);
					break;
				}
			}
		}
		onCalcularGlasgow(alerta);
	}

	public void limpiarEscalaGlasgow() {
		List<Radio> listado_radios = mcRgpRespuesta_motora.getItems();
		for (Radio radio : listado_radios) {
			radio.setChecked(false);
		}
		listado_radios = mcRgpRespuesta_verbal.getItems();
		for (Radio radio : listado_radios) {
			radio.setChecked(false);
		}
		listado_radios = mcRgpApertura_ocular.getItems();
		for (Radio radio : listado_radios) {
			radio.setChecked(false);
		}
		mcIbxTotal_puntos.setText("");
		mcTbxGrado_estado.setText("");
		mcTbxGrado_estado.setStyle("background-color:white");
		mcTbxGrado_estado.setTooltiptext(null);
		mcTbxValoracion.setText("");

	}

	public static String validarTotalPuntuacion(int total) {
		StringBuilder resultado = new StringBuilder();
		if (total >= 14) {
			resultado.append("Normal");
		} else if (total < 14 && total >= 9) {
			resultado.append("Moderado");
		} else {
			resultado.append("Grave");
		}

		resultado.append(" - ");

		if (total >= 14) {
			resultado.append("Alerta");
		} else if (total < 14 && total >= 10) {
			resultado.append("Estupor");
		} else if (total < 10 && total >= 5) {
			resultado.append("Obnubilado");
		} else {
			resultado.append("Coma");
		}

		return resultado.toString();

	}

}
