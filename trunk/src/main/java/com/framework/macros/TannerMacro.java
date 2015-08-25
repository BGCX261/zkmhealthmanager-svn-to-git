package com.framework.macros;

import healthmanager.controller.ZKWindow.View;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import com.framework.res.CargardorDeDatos;
import com.framework.util.MensajesUtil;

public class TannerMacro extends Grid implements AfterCompose {

	private static Logger log = Logger.getLogger(TannerMacro.class);

	@View
	private Label lbResultado_fila1;
	@View
	private Label lbResultado_fila2;
	@View
	private Label lbResultado_fila3;
	@View
	private Label lbResultado_fila4;

	private Image imgFila1;
	private Image imgFila2;
	private Image imgFila3;
	private Image imgFila4;
	
	@View private Row rowMasculino1;
	@View private Row rowEstadio_genital;
	@View private Row rowFemenino;
	@View private Row rowEstadio_mamario;
	@View private Row rowEstudio_publicoF;
	@View private Row rowFemenino2;
	@View private Row rowMasculino2;
	@View private Row rowEstadioPublicoM;
	
	
	// registramos instancias de imagenes
	@View private Image img_estado_genital_1_chulo_m;
	@View private Image img_estado_genital_1_m;
	@View private Image img_estado_genital_2_chulo_m;
	@View private Image img_estado_genital_2_m;
	@View private Image img_estado_genital_3_chulo_m;
	@View private Image img_estado_genital_3_m;
	@View private Image img_estado_genital_4_chulo_m;
	@View private Image img_estado_genital_4_m;
	@View private Image img_estado_genital_5_chulo_m;
	@View private Image img_estado_genital_5_m;
	       
	@View private Image img_estado_genital_1_chulo_f;
	@View private Image img_estado_genital_1_f;
	@View private Image img_estado_genital_2_chulo_f;
	@View private Image img_estado_genital_2_f;
	@View private Image img_estado_genital_3_chulo_f;
	@View private Image img_estado_genital_3_f;
	@View private Image img_estado_genital_4_chulo_f;
	@View private Image img_estado_genital_4_f;
	@View private Image img_estado_genital_5_chulo_f;
	@View private Image img_estado_genital_5_f;
	       
	@View private Image img_estado_pubico_1_chulito_f;
	@View private Image img_estado_pubico_1_f;
	@View private Image img_estado_pubico_2_chulito_f;
	@View private Image img_estado_pubico_2_f;
	@View private Image img_estado_pubico_3_chulito_f;
	@View private Image img_estado_pubico_3_f;
	@View private Image img_estado_pubico_4_chulito_f;
	@View private Image img_estado_pubico_4_f;
	@View private Image img_estado_pubico_5_chulito_f;
	@View private Image img_estado_pubico_5_f;
	       
	@View private Image img_estado_pubico_1_chulito_m;
	@View private Image img_estado_pubico_1_m;
	@View private Image img_estado_pubico_2_chulito_m;
	@View private Image img_estado_pubico_2_m;
	@View private Image img_estado_pubico_3_chulito_m;
	@View private Image img_estado_pubico_3_m;
	@View private Image img_estado_pubico_4_chulito_m;
	@View private Image img_estado_pubico_4_m;
	@View private Image img_estado_pubico_5_chulito_m;
	@View private Image img_estado_pubico_5_m;

	@Override
	public void afterCompose() {
		cargarComponentes();
	}

	private void cargarComponentes() {
		try {
			CargardorDeDatos.initComponents(this);
		} catch (Exception e) {
			MensajesUtil.mensajeAlerta("Error al cargar componentes",
					"Error al cargar los componentes del estadio tanner");
		}
	}

	public void seleccionarCapurroFila1(Cell cellCuadro) {
		if (imgFila1 != null) {
			imgFila1.setVisible(false);
		}

		Image image_aux = (Image) cellCuadro.getFirstChild();
		image_aux.setVisible(true);
		imgFila1 = image_aux;
		if (image_aux.hasAttribute("VALOR_TANNER")) {
			lbResultado_fila1.setValue(image_aux.getAttribute("VALOR_TANNER")
					.toString());
		} else {
			lbResultado_fila1.setValue("");
		}
	}

	public void seleccionarCapurroFila2(Cell cellCuadro) {
		if (imgFila2 != null) {
			imgFila2.setVisible(false);
		}

		Image image_aux = (Image) cellCuadro.getFirstChild();
		image_aux.setVisible(true);
		imgFila2 = image_aux;
		if (image_aux.hasAttribute("VALOR_TANNER")) {
			lbResultado_fila2.setValue(image_aux.getAttribute("VALOR_TANNER")
					.toString());
		} else {
			lbResultado_fila2.setValue("");
		}
		
	}

	public void seleccionarCapurroFila3(Cell cellCuadro) {
		if (imgFila3 != null) {
			imgFila3.setVisible(false);
		}

		Image image_aux = (Image) cellCuadro.getFirstChild();
		image_aux.setVisible(true);
		imgFila3 = image_aux;
		if (image_aux.hasAttribute("VALOR_TANNER")) {
			lbResultado_fila3.setValue(image_aux.getAttribute("VALOR_TANNER")
					.toString());
		} else {
			lbResultado_fila3.setValue("");
		}
		
	}

	public void seleccionarCapurroFila4(Cell cellCuadro) {
		if (imgFila4 != null) {
			imgFila4.setVisible(false);
		}

		Image image_aux = (Image) cellCuadro.getFirstChild();
		image_aux.setVisible(true);
		imgFila4 = image_aux;
		if (image_aux.hasAttribute("VALOR_TANNER")) {
			lbResultado_fila4.setValue(image_aux.getAttribute("VALOR_TANNER")
					.toString());
		} else {
			lbResultado_fila4.setValue("");
		}
		
	}


	public String getValorFila1() {
		if (lbResultado_fila1.getValue().isEmpty()) {
			return "";
		} else {
			return lbResultado_fila1.getValue();
		}
	}

	public String getValorFila2() {
		if (lbResultado_fila2.getValue().isEmpty()) {
			return "";
		} else {
			return  lbResultado_fila2.getValue();
		}
	}

	public String getValorFila3() {
		if (lbResultado_fila3.getValue().isEmpty()) {
			return "";
		} else {
			return lbResultado_fila3.getValue();
		}
	}

	public String getValorFila4() {
		if (lbResultado_fila4.getValue().isEmpty()) {
			return "";
		} else {
			return lbResultado_fila4.getValue();
		}
	}

	public void cargarValorFila1(String valor){
		Row fila1 = (Row)this.getRows().getChildren().get(1);
		for(Component component : fila1.getChildren()){
			if(component instanceof Cell){
				if(component.getChildren().size() == 2){
					Image image_aux = (Image)component.getChildren().get(0);
					if (image_aux.hasAttribute("VALOR_TANNER")) {
						String valor_interno = image_aux.getAttribute("VALOR_TANNER").toString();
						if(valor_interno.equalsIgnoreCase(valor)){
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

	public void cargarValorFila2(String valor){
		Row fila2 = (Row)this.getRows().getChildren().get(3);
		for(Component component : fila2.getChildren()){
			if(component instanceof Cell){
				if(component.getChildren().size() == 2){
					Image image_aux = (Image)component.getChildren().get(0);
					if (image_aux.hasAttribute("VALOR_TANNER")) {
						String valor_interno = image_aux.getAttribute("VALOR_TANNER")
								.toString();
						if(valor_interno.equalsIgnoreCase(valor)){
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
	
	/**
	 * Estadio pubico femenino
	 * @param valor
	 */
	public void cargarValorFila3(String valor){
		Row fila3 = (Row)this.getRows().getChildren().get(5);
		for(Component component : fila3.getChildren()){
			if(component instanceof Cell){
				if(component.getChildren().size() == 2){
					Image image_aux = (Image)component.getChildren().get(0);
					if (image_aux.hasAttribute("VALOR_TANNER")) {
						String valor_interno = image_aux.getAttribute("VALOR_TANNER")
								.toString();
						if(valor_interno.equalsIgnoreCase(valor)){
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

	/**
	 * Estadio pubico masculino
	 * @param valor
	 */
	public void cargarValorFila4(String valor){
		Row fila4 = (Row)this.getRows().getChildren().get(7);
		for(Component component : fila4.getChildren()){
			if(component instanceof Cell){
				if(component.getChildren().size() == 2){
					Image image_aux = (Image)component.getChildren().get(0);
					if (image_aux.hasAttribute("VALOR_TANNER")) {
						String valor_interno = image_aux.getAttribute("VALOR_TANNER")
								.toString();
						if(valor_interno.equalsIgnoreCase(valor)){
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
	
	public void mostrarFilasMasculino(){
		rowEstadio_genital.setVisible(true);
		rowMasculino1.setVisible(true);
		rowMasculino2.setVisible(true);
		rowEstadioPublicoM.setVisible(true);
	}
	
	public void mostrarFilasFemenino(){
		rowFemenino.setVisible(true);
		rowEstadio_mamario.setVisible(true);
		rowEstudio_publicoF.setVisible(true);
		rowFemenino2.setVisible(true);
	}

	/***
	 * Este metodo mapea las el contenido de las imagenes
	 * */
	public void complementarInformacion(Map<String, Object> map, String sexo) {
		if(sexo.equals("M")){ // Masculino
			map.put("titulo_tanner_1", "Estadio Genital");
			map.put("titulo_tanner_2", "Estadio Púbico Masculino");
			
			agregarAlMapa(img_estado_genital_1_chulo_m, "img_estado_genital_1_chulo", map);  
			agregarAlMapa(img_estado_genital_1_m, "img_estado_genital_1", map);     
			agregarAlMapa(img_estado_genital_2_chulo_m, "img_estado_genital_2_chulo", map); 
			agregarAlMapa(img_estado_genital_2_m, "img_estado_genital_2", map);       
			agregarAlMapa(img_estado_genital_3_chulo_m, "img_estado_genital_3_chulo", map);
			agregarAlMapa(img_estado_genital_3_m, "img_estado_genital_3", map);       
			agregarAlMapa(img_estado_genital_4_chulo_m, "img_estado_genital_4_chulo", map);
			agregarAlMapa(img_estado_genital_4_m, "img_estado_genital_4", map);     
			agregarAlMapa(img_estado_genital_5_chulo_m, "img_estado_genital_5_chulo", map); 
			agregarAlMapa(img_estado_genital_5_m, "img_estado_genital_5", map);
			
			map.put("img_estado_genital_descripcion", lbResultado_fila1.getValue()); 
			map.put("img_estado_pubico_descripcion", lbResultado_fila4.getValue());
			
			// pubico
			agregarAlMapa(img_estado_pubico_1_chulito_m, "img_estado_pubico_1_chulito", map);  
			agregarAlMapa(img_estado_pubico_1_m, "img_estado_pubico_1", map);     
			agregarAlMapa(img_estado_pubico_2_chulito_m, "img_estado_pubico_2_chulito", map); 
			agregarAlMapa(img_estado_pubico_2_m, "img_estado_pubico_2", map);       
			agregarAlMapa(img_estado_pubico_3_chulito_m, "img_estado_pubico_3_chulito", map);
			agregarAlMapa(img_estado_pubico_3_m, "img_estado_pubico_3", map);       
			agregarAlMapa(img_estado_pubico_4_chulito_m, "img_estado_pubico_4_chulito", map);
			agregarAlMapa(img_estado_pubico_4_m, "img_estado_pubico_4", map);     
			agregarAlMapa(img_estado_pubico_5_chulito_m, "img_estado_pubico_5_chulito", map); 
			agregarAlMapa(img_estado_pubico_5_m, "img_estado_pubico_5", map); 
		}else if(sexo.equals("F")){  // Femenino
			map.put("titulo_tanner_1", "Estadio Mamario");
			map.put("titulo_tanner_2", "Estadio Púbico Femenino");
			
			agregarAlMapa(img_estado_genital_1_chulo_f, "img_estado_genital_1_chulo", map);  
			agregarAlMapa(img_estado_genital_1_f, "img_estado_genital_1", map);     
			agregarAlMapa(img_estado_genital_2_chulo_f, "img_estado_genital_2_chulo", map); 
			agregarAlMapa(img_estado_genital_2_f, "img_estado_genital_2", map);       
			agregarAlMapa(img_estado_genital_3_chulo_f, "img_estado_genital_3_chulo", map);
			agregarAlMapa(img_estado_genital_3_f, "img_estado_genital_3", map);       
			agregarAlMapa(img_estado_genital_4_chulo_f, "img_estado_genital_4_chulo", map);
			agregarAlMapa(img_estado_genital_4_f, "img_estado_genital_4", map);     
			agregarAlMapa(img_estado_genital_5_chulo_f, "img_estado_genital_5_chulo", map); 
			agregarAlMapa(img_estado_genital_5_f, "img_estado_genital_5", map);  
			
			//pubico
			agregarAlMapa(img_estado_pubico_1_chulito_f, "img_estado_pubico_1_chulito", map);  
			agregarAlMapa(img_estado_pubico_1_f, "img_estado_pubico_1", map);     
			agregarAlMapa(img_estado_pubico_2_chulito_f, "img_estado_pubico_2_chulito", map); 
			agregarAlMapa(img_estado_pubico_2_f, "img_estado_pubico_2", map);       
			agregarAlMapa(img_estado_pubico_3_chulito_f, "img_estado_pubico_3_chulito", map);
			agregarAlMapa(img_estado_pubico_3_f, "img_estado_pubico_3", map);       
			agregarAlMapa(img_estado_pubico_4_chulito_f, "img_estado_pubico_4_chulito", map);
			agregarAlMapa(img_estado_pubico_4_f, "img_estado_pubico_4", map);     
			agregarAlMapa(img_estado_pubico_5_chulito_f, "img_estado_pubico_5_chulito", map); 
			agregarAlMapa(img_estado_pubico_5_f, "img_estado_pubico_5", map); 
			
			
			map.put("img_estado_genital_descripcion", lbResultado_fila2.getValue()); 
			map.put("img_estado_pubico_descripcion", lbResultado_fila3.getValue());
		}
	}
	
	public void agregarAlMapa(Image image, String id, Map<String, Object> map){
		try {
			if(image.isVisible()){
				ServletContext context =  ((HttpServletRequest) Executions
						.getCurrent().getNativeRequest()).getSession().getServletContext();
				String SUBREPORT_DIR = context.getRealPath(image.getSrc());
				File file = new File(SUBREPORT_DIR);
				if(file.exists()){
					map.put("" + id, new FileInputStream(file)); 
					//log.info("Imagen agregada");  
				}
			}
		} catch (Exception e) { 
			log.error(e.getMessage(), e); 
		}
	}

}
