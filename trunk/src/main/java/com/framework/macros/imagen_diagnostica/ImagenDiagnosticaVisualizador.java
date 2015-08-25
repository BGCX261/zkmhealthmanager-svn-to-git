package com.framework.macros.imagen_diagnostica;

import healthmanager.modelo.bean.Paciente;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Image;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.softcomputo.decodificador.DicomReader;
import com.softcomputo.decodificador.DicomReaderFactory;
import com.softcomputo.decodificador.util.DecodificadorUtils;

public class ImagenDiagnosticaVisualizador extends Window {

	private static Logger log = Logger
			.getLogger(ImagenDiagnosticaVisualizador.class);

	private Textbox tbxDescripcion_imagen;
	private Image imagen;
	private North north;
	private South south;
	private Toolbarbutton toolbarbutton_guardar;
	

	public ImagenDiagnosticaVisualizador() {
		tbxDescripcion_imagen = new Textbox();
		tbxDescripcion_imagen.setHflex("1");
		tbxDescripcion_imagen.setVflex("1");
		tbxDescripcion_imagen.setRows(5);
		imagen = new Image();
		Borderlayout borderlayout = new Borderlayout();
		north = new North();
		toolbarbutton_guardar = new Toolbarbutton("Guardar descripcion", "/images/Save16.gif");
		north.appendChild(toolbarbutton_guardar);
		north.setVisible(false);
		borderlayout.appendChild(north);
		Center center = new Center();
		center.setAutoscroll(true);
		center.setStyle("text-align:center");
		center.appendChild(imagen);
		south = new South();
		south.setHeight("100px");
		south.setCollapsible(true);
		south.setSplittable(true);
		south.setVisible(false);
		south.setTitle("Descripcion de la imagen");
		south.appendChild(tbxDescripcion_imagen);
		borderlayout.appendChild(south);
		borderlayout.appendChild(center);
		appendChild(borderlayout);
	}

	public void mostrarImagenDCM(File file, Paciente paciente) {
		try {
			setTitle(paciente != null ? paciente.getNombreCompleto()
					: "Visor de imagenes");
			InputStream inputStream = new FileInputStream(file);
			byte[] bs = new byte[inputStream.available()];
			inputStream.read(bs);
			DicomReader dicomReader = DicomReaderFactory.crearDicomReader(bs);
			BufferedImage bufferedImage = new BufferedImage(500, 500,
					BufferedImage.TYPE_3BYTE_BGR);
			bufferedImage.getGraphics()
					.drawImage(
							DecodificadorUtils.toBufferedImage(dicomReader
									.getImagen()), 0, 0, 500, 500, null);
			imagen.setContent(bufferedImage);
			inputStream.close();
		} catch (IOException ioe) {
			imagen.setSrc("/images/error_decodificar.png");
			log.error(ioe.getMessage(), ioe);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Textbox getTbxDescripcion_imagen() {
		return tbxDescripcion_imagen;
	}

	public void setTbxDescripcion_imagen(Textbox tbxDescripcion_imagen) {
		this.tbxDescripcion_imagen = tbxDescripcion_imagen;
	}

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}

	public South getSouth() {
		return south;
	}

	public void setSouth(South south) {
		this.south = south;
	}

	public North getNorth() {
		return north;
	}

	public void setNorth(North north) {
		this.north = north;
	}

	public Toolbarbutton getToolbarbutton_guardar() {
		return toolbarbutton_guardar;
	}

	public void setToolbarbutton_guardar(Toolbarbutton toolbarbutton_guardar) {
		this.toolbarbutton_guardar = toolbarbutton_guardar;
	}

}
