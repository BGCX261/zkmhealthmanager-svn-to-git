package com.framework.macros.odontograma.util;

import healthmanager.modelo.bean.Diente;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.OdontogramaMacro;

public class DienteDibujadorUtil {
	public static InputStream convertir(Diente diente) {
		try {
			DienteMacro dienteMacro = convertirDienteMacro(diente);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(dienteMacro.getPaint(), "png", baos);
			return new ByteArrayInputStream(baos.toByteArray());
		} catch (IOException e) {
			return null;
		}
	}
	
	
	public static BufferedImage convertirBufferedImage(Diente diente) {
		try {
			DienteMacro dienteMacro = convertirDienteMacro(diente);
			return dienteMacro.getPaint();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	private static DienteMacro convertirDienteMacro(Diente diente){
		ISelector iSelector = OdontogramaMacro.getSelectorFromType(diente
				.getSector_bottom());
		DienteMacro dienteMacro = new DienteMacro();
		if (iSelector != null) {
			dienteMacro.setiSelector(iSelector);
			dienteMacro.setSectorFromIselector(SECTOR.BOTTOM);
		}

		iSelector = OdontogramaMacro
				.getSelectorFromType(diente.getSector_top());
		if (iSelector != null) {
			dienteMacro.setiSelector(iSelector);
			dienteMacro.setSectorFromIselector(SECTOR.TOP);
		}

		iSelector = OdontogramaMacro.getSelectorFromType(diente
				.getSector_left());
		if (iSelector != null) {
			dienteMacro.setiSelector(iSelector);
			dienteMacro.setSectorFromIselector(SECTOR.LEFT);
		}

		iSelector = OdontogramaMacro.getSelectorFromType(diente
				.getSector_rigth());
		if (iSelector != null) {
			dienteMacro.setiSelector(iSelector);
			dienteMacro.setSectorFromIselector(SECTOR.RIGTH);
		}

		iSelector = OdontogramaMacro.getSelectorFromType(diente
				.getSector_center());
		if (iSelector != null) {
			dienteMacro.setiSelector(iSelector);
			dienteMacro.setSectorFromIselector(SECTOR.CENTER);
		}

		iSelector = OdontogramaMacro.getSelectorFromType(diente
				.getSector_superior());
		if (iSelector != null) {
			dienteMacro.setiSelector(iSelector);
			dienteMacro.setSectorFromIselector(SECTOR.NONE);
		}
		return dienteMacro;
	}
	
}
