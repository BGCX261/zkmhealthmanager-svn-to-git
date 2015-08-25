package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Puntos_graficas_embarazo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;

public class GraficasEmbarazoMacro extends ZKWindow {

	public static Logger log = Logger.getLogger(GraficasEmbarazoMacro.class);

	private List<PuntoGrafica> listado_puntos = new ArrayList<PuntoGrafica>();

	@View
	private Image imgGrafica;
	@View
	private Intbox ibxVariacion_grafica;
	@View
	private Intbox ibxRegion_xi;
	@View
	private Intbox ibxRegion_xf;
	@View
	private Intbox ibxRegion_yi;
	@View
	private Intbox ibxRegion_yf;
	@View
	private Intbox ibxUnidades_x;
	@View
	private Intbox ibxUnidades_y;
	@View
	private Intbox ibxCantidad_inicio_x;
	@View
	private Intbox ibxCantidad_inicio_y;
	@View
	private Intbox ibxIncremento_x;
	@View
	private Intbox ibxIncremento_y;

	private BufferedImage bufferedImage;

	private int region_xi = 91;
	private int region_xf = 750;

	private int region_yi = 556;
	private int region_yf = 23;

	private int unidades_x = 23;
	private int unidades_y = 17;
	
	private int cantidad_inicio_x = 5;
	private int cantidad_inicio_y = 11;
	
	private int incremento_x = 2;
	private int incremento_y = 2;

	private String imagen_fondo = "/images/embarazo/presion_diastolica.png";

	private int VARIACION_PLANO;

	@Override
	public void init() throws Exception {
		inicializarVariables();
	}

	private void inicializarVariables() {
		VARIACION_PLANO = ibxVariacion_grafica.getValue() != null ? ibxVariacion_grafica
				.getValue() : 0;
		region_xi = ibxRegion_xi.getValue() != null ? ibxRegion_xi.getValue()
				: region_xi;
		region_xf = ibxRegion_xf.getValue() != null ? ibxRegion_xf.getValue()
				: region_xf;
		region_yi = ibxRegion_yi.getValue() != null ? ibxRegion_yi.getValue()
				: region_yi;
		region_yf = ibxRegion_yf.getValue() != null ? ibxRegion_yf.getValue()
				: region_yf;
		unidades_x = ibxUnidades_x.getValue() != null ? ibxUnidades_x.getValue()
				: unidades_x;
		unidades_y = ibxUnidades_y.getValue() != null ? ibxUnidades_y.getValue()
				: unidades_y;
		cantidad_inicio_x = ibxCantidad_inicio_x.getValue() != null ? ibxCantidad_inicio_x.getValue()
				: cantidad_inicio_x;
		cantidad_inicio_y = ibxCantidad_inicio_y.getValue() != null ? ibxCantidad_inicio_y.getValue()
				: cantidad_inicio_y;
		incremento_x = ibxIncremento_x.getValue() != null ? ibxIncremento_x.getValue()
				: incremento_x;
		incremento_y = ibxIncremento_y.getValue() != null ? ibxIncremento_y.getValue()
				: incremento_y;
	}
	
	public void inicializar(boolean lineas_unidades) throws Exception{
		dibujarFondo();
		dibujarLineasUnidades(lineas_unidades);
		listado_puntos.clear();
	}

	public void dibujarFondo() throws Exception {
		InputStream imagenInputStream = Executions.getCurrent().getDesktop()
				.getWebApp().getResourceAsStream(imagen_fondo);
		bufferedImage = ImageIO.read(imagenInputStream);
		imgGrafica.setContent(bufferedImage);
	}

	public void dibujarLineasUnidades(boolean lineas_unidades) {
		Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
		graphics2d.setColor(Color.BLACK);

		int aux_yi = region_yi;

		int cant_y = cantidad_inicio_y;

		while (aux_yi > region_yf) {
			if(lineas_unidades)
				graphics2d.fillRect(region_xi, aux_yi, (region_xf - region_xi), 2);
			else
				graphics2d.setStroke(new BasicStroke(2));
			
			if (cant_y != cantidad_inicio_y) {
				graphics2d.drawString("" + cant_y, (region_xi - 15),
						(aux_yi + 3));
			}
			cant_y = cant_y + incremento_y;

			aux_yi = aux_yi - (unidades_y * incremento_y);
		}

		int aux_xi = region_xi;

		int cant_x = cantidad_inicio_x;

		while (aux_xi < region_xf) {
			if(lineas_unidades)
				graphics2d.fillRect(aux_xi, region_yf, 2, (region_yi - region_yf));
			else
				graphics2d.setStroke(new BasicStroke(2));
			
			if (cant_x != cantidad_inicio_x) {
				graphics2d.drawString("" + cant_x, (aux_xi - 5),
						(region_yi + 15));
			}
			cant_x = cant_x + incremento_x;
			aux_xi = aux_xi + (unidades_x * incremento_x);
		}

		imgGrafica.setContent(bufferedImage);

	}

	public void dibujarPunto(int semana, int altura) {
		//log.info("Graficando el punto semana=" + semana + ", altura=" + altura);
		int punto_x = (semana * ((int) unidades_x)) - (cantidad_inicio_x * ((int) unidades_x))
				- 2;
		int punto_y = (altura * ((int) unidades_y)) - (cantidad_inicio_y * ((int) unidades_y))
				+ 2;

		PuntoGrafica punto_grafica = new PuntoGrafica();
		punto_grafica.setPunto_x(punto_x);
		punto_grafica.setPunto_y(punto_y);
		listado_puntos.add(punto_grafica);

		Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
		graphics2d.setColor(Color.RED);
		graphics2d.fillOval((punto_x + region_xi - VARIACION_PLANO), (region_yi
				- punto_y - VARIACION_PLANO), 10, 10);
		imgGrafica.setContent(bufferedImage);
	}

	public void dibujarPuntos(List<Puntos_graficas_embarazo> listado_puntos) {
		for (Puntos_graficas_embarazo puntos_graficas_embarazo : listado_puntos) {
			dibujarPunto(puntos_graficas_embarazo.getPunto_x(),
					puntos_graficas_embarazo.getPunto_y());
		}
	}

	public void dibujarLineasSeguimiento() {
		if (listado_puntos.size() > 1) {
			for (int i = 0; i < listado_puntos.size(); i++) {
				PuntoGrafica punto1 = listado_puntos.get(i);
				if ((i + 1) < listado_puntos.size()) {
					PuntoGrafica punto2 = listado_puntos.get(i + 1);
					Graphics2D graphics2d = (Graphics2D) bufferedImage
							.getGraphics();
					graphics2d.setStroke(new BasicStroke(3));
					graphics2d.setColor(Color.RED);
					graphics2d
							.drawLine(
									(punto1.getPunto_x() + region_xi),
									(region_yi - punto1.getPunto_y() + VARIACION_PLANO),
									(punto2.getPunto_x() + region_xi + VARIACION_PLANO),
									(region_yi - punto2.getPunto_y()));
				}

			}
			imgGrafica.setContent(bufferedImage);
		}
	}

	public int getRegion_xi() {
		return region_xi;
	}

	public void setRegion_xi(int region_xi) {
		this.region_xi = region_xi;
	}

	public int getRegion_xf() {
		return region_xf;
	}

	public void setRegion_xf(int region_xf) {
		this.region_xf = region_xf;
	}

	public int getRegion_yi() {
		return region_yi;
	}

	public void setRegion_yi(int region_yi) {
		this.region_yi = region_yi;
	}

	public int getRegion_yf() {
		return region_yf;
	}

	public void setRegion_yf(int region_yf) {
		this.region_yf = region_yf;
	}

	public int getUnidades_x() {
		return unidades_x;
	}

	public void setUnidades_x(int unidades_x) {
		this.unidades_x = unidades_x;
	}

	public int getUnidades_y() {
		return unidades_y;
	}

	public void setUnidades_y(int unidades_y) {
		this.unidades_y = unidades_y;
	}

	public String getImagen_fondo() {
		return imagen_fondo;
	}

	public void setImagen_fondo(String imagen_fondo) {
		this.imagen_fondo = imagen_fondo;
	}

	public int getCantidad_inicio_x() {
		return cantidad_inicio_x;
	}

	public void setCantidad_inicio_x(int cantidad_inicio_x) {
		this.cantidad_inicio_x = cantidad_inicio_x;
	}

	public int getCantidad_inicio_y() {
		return cantidad_inicio_y;
	}

	public void setCantidad_inicio_y(int cantidad_inicio_y) {
		this.cantidad_inicio_y = cantidad_inicio_y;
	}

	public int getIncremento_x() {
		return incremento_x;
	}

	public void setIncremento_x(int incremento_x) {
		this.incremento_x = incremento_x;
	}

	public int getIncremento_y() {
		return incremento_y;
	}

	public void setIncremento_y(int incremento_y) {
		this.incremento_y = incremento_y;
	}
	
	public InputStream getInputStreamGrafica(){
		InputStream inputStream = new ByteArrayInputStream(imgGrafica.getContent().getByteData());
		return inputStream;
	}

	public class PuntoGrafica {

		private int punto_x;
		private int punto_y;

		public int getPunto_x() {
			return punto_x;
		}

		public void setPunto_x(int punto_x) {
			this.punto_x = punto_x;
		}

		public int getPunto_y() {
			return punto_y;
		}

		public void setPunto_y(int punto_y) {
			this.punto_y = punto_y;
		}
	}

}
