package com.framework.macros.odontograma.util;

import java.awt.Graphics2D;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;

public interface ISectorDiente {

	public enum TypeSector {NONE,
		EXTRAIDO, CARIES_OPTURACION, CORONABUENA, CORONA_DESADAPTADA,
		DIENTEERUPCION, AUSENTE,
		DIENTE_SANO, DIENTE_SELLADO, ENDODONCIA_POR_RELLENAR,
		EXODONCIA_SIMPLE, ENDODONCIA, IONOMEROS, NUCLEO_POSTES,
		PROTESIS_FIJA, PRETESIS_REMOVIBLE, RESTAURACION_DESADAPTADA,
		SUPERFICIE_AMALGAMA, SUPERFICIE_MANCHADA, SUPERFICIE_POR_SELLAR,
		SUPERFICIE_RECINA, DIENTES_SANOS_CHULO, ABRASION, OPTURACION_TEMPORAL
	};

	public void onDraw(Graphics2D graphics2d);

	public TypeSector getTypeSector();
	
	public void onRemover(DienteMacro  dienteMacro, SECTOR sector);
}
