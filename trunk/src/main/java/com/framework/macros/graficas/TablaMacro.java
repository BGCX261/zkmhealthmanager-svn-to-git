package com.framework.macros.graficas;

import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.HealthmanagerException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import tablas_crecimiento_desarrollo.modelo.bean.Tabla_anios_meses;
import tablas_crecimiento_desarrollo.modelo.bean.Tabla_talla_peso;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.res.CargardorDeDatos;
import com.framework.util.MensajesUtil;

/**
 * Este componente te permitira, mostrar las tablas conque se calculan las
 * graficas.
 * 
 * @author Luis Miguel hernandez Perez
 * */
public class TablaMacro extends Grid implements AfterCompose {
	public enum Tipo_tabla {
		ANIOS_MESE, NUMERIC_HEGTH, NUMERIC_LENGTH
	}

	private Tipo_tabla tipo_tabla;

	private final Class<?>[] classPermitidas = { Tabla_anios_meses.class,
			Tabla_talla_peso.class };

	/* cargamos componentes */
	@View
	private Auxheader auxTitle;
	@View
	private Auxheader auxValorRetorno;
	@View
	private Column columnYearMont;
	@View
	private Column columnMonth;
	@View
	private Column columnSd;
	@View
	private Rows rowsTabla;
	@View
	private Columns columsContent;

	@View
	private Auxhead auxheadbarra;

	/* Campos del Convertidor */
	public static final String SEXO = "sexo";
	public static final String TIPO = "tipo";
	public static final String EDAD_INICIAL = "edad_inicial";
	public static final String TIPO_EDAD_INICIAL = "tipo_edad_inicial";
	public static final String EDAD_FINAL = "edad_final";
	public static final String TIPO_EDAD_FINAL = "tipo_edad_final";
	public static final String ANIO_MES = "anio_mes";
	public static final String TALLA_PESO = "talla_peso";
	public static final String MES = "mes";
	public static final String L = "l";
	public static final String M = "m";
	public static final String S = "s";
	public static final String SD = "sd";
	public static final String MENOS_3_SD = "menos_3_sd";
	public static final String MENOS_2_SD = "menos_2_sd";
	public static final String MENOS_1_SD = "menos_1_sd";
	public static final String MEDIA = "media";
	public static final String MAS_1_SD = "mas_1_sd";
	public static final String MAS_2_SD = "mas_2_sd";
	public static final String MAS_3_SD = "mas_3_sd";

	private ESexo sexo;

	@Override
	public void afterCompose() {
		try {
			CargardorDeDatos.initComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
			MensajesUtil.mensajeAlerta("Adverntencia",
					"Ha ocurido un error en el componente Tabla Grafica");
		}
	};

	/**
	 * Metodo para mostrar la tabla
	 * */
	public void mostrarTabla(List<?> tabla) {
		try {
			rowsTabla.getChildren().clear();
			for (Object object : tabla) {
				rowsTabla.appendChild(createRow(convertToMap(object)));
			}
			applyProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ESexo getSexo() {
		return sexo;
	}

	public void setSexo(ESexo sexo) {
		this.sexo = sexo;
		if (sexo == ESexo.MASCULINO) {
			auxheadbarra.setStyle("background: #90AEF0;");
		}
	}

	public Tipo_tabla getTipo_tabla() {
		return tipo_tabla;
	}

	public void setTitle(String title) {
		auxTitle.setLabel(title);
	}

	public void setTitleValorReturn(String title_return) {
		auxValorRetorno.setLabel(title_return);
	}

	public void setTipo_tabla(Tipo_tabla tipo_tabla) {
		this.tipo_tabla = tipo_tabla;
		if (tipo_tabla == Tipo_tabla.NUMERIC_HEGTH) {
			columnYearMont.setLabel("Heigth");
			// columnMonth.setVisible(false);
			// columsContent.removeChild(columnMonth);
		} else if (tipo_tabla == Tipo_tabla.NUMERIC_LENGTH) {
			columnYearMont.setLabel("Length");
			// columnMonth.setVisible(false);
			// columsContent.removeChild(columnMonth);
		} else {
			columnMonth.setVisible(true);
			auxTitle.setColspan(6);
		}
	}

	private Map<String, Object> convertToMap(Object object) throws Exception {
		validateClass(object.getClass());
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(object));
		}
		return map;
	}

	private void validateClass(Class<? extends Object> class1) {
		boolean exist = false;
		for (Class<? extends Object> clss_permitidas : classPermitidas) {
			if (clss_permitidas == class1) {
				exist = true;
			}
		}
		if (!exist) {
			throw new HealthmanagerException(
					"Ooop, Listado de datos no permitidos...");
		}
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

	/**
	 * Metodo en el cual se va ha crear la tabla
	 * */
	private Component createRow(Map<String, Object> convertToMap) {
		Row row = new Row();

		Cell cell = new Cell();
		if (tipo_tabla == Tipo_tabla.ANIOS_MESE)
			cell.appendChild(new Label(convertToMap.get(ANIO_MES) + ""));
		else
			cell.appendChild(new Label(convertToMap.get(TALLA_PESO) + ""));
		row.appendChild(cell);

		cell = new Cell();
		Label label = new Label(convertToMap.get(MES) + "");
		cell.appendChild(label);
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(L) + ""));
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(M) + ""));
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(S) + ""));
		row.appendChild(cell);

		cell = new Cell();
		String sd = convertToMap.get(SD) + "";
		columnSd.setVisible(!sd.equals("-1") && !sd.equals("-1.0"));
		cell.appendChild(new Label(sd));
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(MENOS_3_SD) + ""));
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(MENOS_2_SD) + ""));
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(MENOS_1_SD) + ""));
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(MEDIA) + ""));
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(MAS_1_SD) + ""));
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(MAS_2_SD) + ""));
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label(convertToMap.get(MAS_3_SD) + ""));
		row.appendChild(cell);

		return row;
	}
}
