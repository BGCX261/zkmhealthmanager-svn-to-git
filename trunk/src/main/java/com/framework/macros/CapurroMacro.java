package com.framework.macros;

import healthmanager.controller.ZKWindow.View;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.framework.res.CargardorDeDatos;
import com.framework.util.MensajesUtil;

public class CapurroMacro extends Grid implements AfterCompose {

//	private static Logger log = Logger.getLogger(CapurroMacro.class);

	@View
	private Label lbResultado_fila1;
	@View
	private Label lbResultado_fila2;
	@View
	private Label lbResultado_fila3;
	@View
	private Label lbResultado_fila4;
	@View
	private Label lbResultado_fila5;
	@View
	private Label lbResultado_parcial;
	@View
	private Label lbEstado_semana;

	private Image imgFila1;
	private Image imgFila2;
	private Image imgFila3;
	private Image imgFila4;
	private Image imgFila5;
	
	private Integer semanas;

	@Override
	public void afterCompose() {
		cargarComponentes();
	}

	private void cargarComponentes() {
		try {
			CargardorDeDatos.initComponents(this);
		} catch (Exception e) {
			MensajesUtil.mensajeAlerta("Error al cargar cmponentes",
					"Error al cargar los componentes del capurro");
		}
	}

	public void seleccionarCapurroFila1(Cell cellCuadro) {
		if (imgFila1 != null) {
			imgFila1.setVisible(false);
		}

		Image image_aux = (Image) cellCuadro.getFirstChild();
		image_aux.setVisible(true);
		imgFila1 = image_aux;
		if (image_aux.hasAttribute("VALOR_CAPURRO")) {
			lbResultado_fila1.setValue(image_aux.getAttribute("VALOR_CAPURRO")
					.toString());
		} else {
			lbResultado_fila1.setValue("");
		}
		
		lbResultado_parcial.setValue(""+getValorParcialFilas());
		
	}

	public void seleccionarCapurroFila2(Cell cellCuadro) {
		if (imgFila2 != null) {
			imgFila2.setVisible(false);
		}

		Image image_aux = (Image) cellCuadro.getFirstChild();
		image_aux.setVisible(true);
		imgFila2 = image_aux;
		if (image_aux.hasAttribute("VALOR_CAPURRO")) {
			lbResultado_fila2.setValue(image_aux.getAttribute("VALOR_CAPURRO")
					.toString());
		} else {
			lbResultado_fila2.setValue("");
		}
		
		lbResultado_parcial.setValue(""+getValorParcialFilas());
		
	}

	public void seleccionarCapurroFila3(Cell cellCuadro) {
		if (imgFila3 != null) {
			imgFila3.setVisible(false);
		}

		Image image_aux = (Image) cellCuadro.getFirstChild();
		image_aux.setVisible(true);
		imgFila3 = image_aux;
		if (image_aux.hasAttribute("VALOR_CAPURRO")) {
			lbResultado_fila3.setValue(image_aux.getAttribute("VALOR_CAPURRO")
					.toString());
		} else {
			lbResultado_fila3.setValue("");
		}
		
		lbResultado_parcial.setValue(""+getValorParcialFilas());
		
	}

	public void seleccionarCapurroFila4(Cell cellCuadro) {
		if (imgFila4 != null) {
			imgFila4.setVisible(false);
		}

		Image image_aux = (Image) cellCuadro.getFirstChild();
		image_aux.setVisible(true);
		imgFila4 = image_aux;
		if (image_aux.hasAttribute("VALOR_CAPURRO")) {
			lbResultado_fila4.setValue(image_aux.getAttribute("VALOR_CAPURRO")
					.toString());
		} else {
			lbResultado_fila4.setValue("");
		}
		
		lbResultado_parcial.setValue(""+getValorParcialFilas());
		
	}

	public void seleccionarCapurroFila5(Cell cellCuadro) {
		if (imgFila5 != null) {
			imgFila5.setVisible(false);
		}

		Image image_aux = (Image) cellCuadro.getFirstChild();
		image_aux.setVisible(true);
		imgFila5 = image_aux;
		if (image_aux.hasAttribute("VALOR_CAPURRO")) {
			lbResultado_fila5.setValue(image_aux.getAttribute("VALOR_CAPURRO")
					.toString());
		} else {
			lbResultado_fila5.setValue("");
		}
		
		lbResultado_parcial.setValue(""+getValorParcialFilas());
		
	}

	public int getValorFila1() {
		if (lbResultado_fila1.getValue().isEmpty()) {
			return -1;
		} else {
			return Integer.parseInt(lbResultado_fila1.getValue());
		}
	}

	public int getValorFila2() {
		if (lbResultado_fila2.getValue().isEmpty()) {
			return -1;
		} else {
			return Integer.parseInt(lbResultado_fila2.getValue());
		}
	}

	public int getValorFila3() {
		if (lbResultado_fila3.getValue().isEmpty()) {
			return -1;
		} else {
			return Integer.parseInt(lbResultado_fila3.getValue());
		}
	}

	public int getValorFila4() {
		if (lbResultado_fila4.getValue().isEmpty()) {
			return -1;
		} else {
			return Integer.parseInt(lbResultado_fila4.getValue());
		}
	}

	public int getValorFila5() {
		if (lbResultado_fila5.getValue().isEmpty()) {
			return -1;
		} else {
			return Integer.parseInt(lbResultado_fila5.getValue());
		}
	}
	
	public int getValorParcialFilas(){
		int fila1 = getValorFila1();
		int fila2 = getValorFila2();
		int fila3 = getValorFila3();
		int fila4 = getValorFila4();
		int fila5 = getValorFila5();
		
		int total = (fila1 != -1 ? fila1 : 0) +
					(fila2 != -1 ? fila2 : 0) +
					(fila3 != -1 ? fila3 : 0) +
					(fila4 != -1 ? fila4 : 0) +
					(fila5 != -1 ? fila5 : 0);
		
		if (total < 32) {
			semanas = (204 + total) / 7;
			lbEstado_semana.setValue(" Semana " +semanas);
			lbEstado_semana.setStyle("font-size:25px;font-weight:bold");
			MensajesUtil.notificarInformacion("El recien nacido se encuentra en estado (Prematuro Externo)", lbEstado_semana);
			lbEstado_semana.setTooltiptext("Estado (PREMATURO EXTERNO)");
		}else if(total >=32 && total < 35){
			semanas = (204 + total) / 7;
			lbEstado_semana.setValue(" Semana " +semanas);
			lbEstado_semana.setStyle("font-size:25px;font-weight:bold");
			MensajesUtil.notificarInformacion("El recien nacido se encuentra en estado (Prematuro Moderado)", lbEstado_semana);
			lbEstado_semana.setTooltiptext("Estado (PREMATURO MODERADO)");
		}else if(total >=35 && total < 37){
			semanas = (204 + total) / 7;
			lbEstado_semana.setValue(" Semana " +semanas);
			lbEstado_semana.setStyle("font-size:25px;font-weight:bold");
			MensajesUtil.notificarInformacion("El recien nacido se encuentra en estado (Prematuro Leve)", lbEstado_semana);
			lbEstado_semana.setTooltiptext("Estado (PREMATURO LEVE)");
		}else if(total >=37 && total < 42){
			semanas = (204 + total) / 7;
			lbEstado_semana.setValue(" Semana " +semanas);
			lbEstado_semana.setStyle("font-size:25px;font-weight:bold");
			MensajesUtil.notificarInformacion("El recien nacido se encuentra en estado (A Término)", lbEstado_semana);
			lbEstado_semana.setTooltiptext("Estado (PREMATURO EXTERNO)");
		}else if(total >=42){
			semanas = (204 + total) / 7;
			lbEstado_semana.setValue(" Semana " +semanas);
			lbEstado_semana.setStyle("font-size:25px;font-weight:bold");
			MensajesUtil.notificarInformacion("El recien nacido se encuentra en estado (Postmaduro)", lbEstado_semana);
			lbEstado_semana.setTooltiptext("Estado (POSTMADURO)");
		}
		
		return total;
	}
	
//	PUBLIC VOID CALCULARSEMANA(){
//		
//		LBESTADO_SEMANA.SETVALUE(SENAMA+"");
//	}

	public void cargarValoresFilas(int valor1, int valor2, int valor3,
			int valor4, int valor5) {
		cargarValorFila1(valor1);
		cargarValorFila1(valor2);
		cargarValorFila1(valor3);
		cargarValorFila1(valor4);
		cargarValorFila1(valor5);
	}
	
	public void cargarValorFila1(int valor){
		Row fila1 = (Row)this.getRows().getChildren().get(0);
		for(Component component : fila1.getChildren()){
			if(component instanceof Cell){
				if(component.getChildren().size() == 2){
					Image image_aux = (Image)component.getChildren().get(0);
					if (image_aux.hasAttribute("VALOR_CAPURRO")) {
						String valor_interno = image_aux.getAttribute("VALOR_CAPURRO")
								.toString();
						if(valor_interno.equals(valor+"")){
							lbResultado_fila1.setValue(valor_interno);
							image_aux.setVisible(true);
							imgFila1 = image_aux;
							break;
						}
						
					}
				}
			}
		}
	}
	
	public void cargarValorFila2(int valor){
		Row fila2 = (Row)this.getRows().getChildren().get(1);
		for(Component component : fila2.getChildren()){
			if(component instanceof Cell){
				if(component.getChildren().size() == 2){
					Image image_aux = (Image)component.getChildren().get(0);
					if (image_aux.hasAttribute("VALOR_CAPURRO")) {
						String valor_interno = image_aux.getAttribute("VALOR_CAPURRO")
								.toString();
						if(valor_interno.equals(valor+"")){
							lbResultado_fila2.setValue(valor_interno);
							image_aux.setVisible(true);
							imgFila2 = image_aux;
							break;
						}
						
					}
				}
			}
		}
	}
	
	public void cargarValorFila3(int valor){
		Row fila3 = (Row)this.getRows().getChildren().get(2);
		for(Component component : fila3.getChildren()){
			if(component instanceof Cell){
				if(component.getChildren().size() == 2){
					Image image_aux = (Image)component.getChildren().get(0);
					if (image_aux.hasAttribute("VALOR_CAPURRO")) {
						String valor_interno = image_aux.getAttribute("VALOR_CAPURRO")
								.toString();
						if(valor_interno.equals(valor+"")){
							lbResultado_fila3.setValue(valor_interno);
							image_aux.setVisible(true);
							imgFila3 = image_aux;
							break;
						}
						
					}
				}
			}
		}
	}
	
	public void cargarValorFila4(int valor){
		Row fila4 = (Row)this.getRows().getChildren().get(3);
		for(Component component : fila4.getChildren()){
			if(component instanceof Cell){
				if(component.getChildren().size() == 2){
					Image image_aux = (Image)component.getChildren().get(0);
					if (image_aux.hasAttribute("VALOR_CAPURRO")) {
						String valor_interno = image_aux.getAttribute("VALOR_CAPURRO")
								.toString();
						if(valor_interno.equals(valor+"")){
							lbResultado_fila4.setValue(valor_interno);
							image_aux.setVisible(true);
							imgFila4 = image_aux;
							break;
						}
						
					}
				}
			}
		}
	}
	
	public void cargarValorFila5(int valor){
		Row fila5 = (Row)this.getRows().getChildren().get(4);
		for(Component component : fila5.getChildren()){
			if(component instanceof Cell){
				if(component.getChildren().size() == 2){
					Image image_aux = (Image)component.getChildren().get(0);
					if (image_aux.hasAttribute("VALOR_CAPURRO")) {
						String valor_interno = image_aux.getAttribute("VALOR_CAPURRO")
								.toString();
						if(valor_interno.equals(valor+"")){
							lbResultado_fila5.setValue(valor_interno);
							image_aux.setVisible(true);
							imgFila5 = image_aux;
							break;
						}
					}
				}
			}
		}
	}

	public Integer getSemanas() {
		return semanas;
	}
}
