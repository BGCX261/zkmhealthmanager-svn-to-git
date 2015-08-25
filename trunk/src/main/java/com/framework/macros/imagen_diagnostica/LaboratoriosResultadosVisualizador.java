package com.framework.macros.imagen_diagnostica;

import healthmanager.modelo.bean.Paciente;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

public class LaboratoriosResultadosVisualizador extends Window {

	private static Logger log = Logger
			.getLogger(LaboratoriosResultadosVisualizador.class);

	private Iframe iframe_pdf;

	public LaboratoriosResultadosVisualizador() {
		iframe_pdf = new Iframe();
		iframe_pdf.setWidth("100%");
		iframe_pdf.setHeight("100%");
		appendChild(iframe_pdf);
		
	}

	public void mostrarPDF(File file, Paciente paciente) {
		try {
			setTitle(paciente != null ? paciente.getNombreCompleto()
					: "Visor de Resultados");

			AMedia amedia = new AMedia(file.getPath(), "pdf",
					"application/pdf", new FileInputStream(file));
			iframe_pdf.setContent(amedia);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
