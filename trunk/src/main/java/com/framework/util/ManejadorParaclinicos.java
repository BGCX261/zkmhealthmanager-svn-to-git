package com.framework.util;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Pexamenes_paraclinicos;
import healthmanager.modelo.bean.Phistorias_paraclinicos;
import healthmanager.modelo.bean.Presultados_paraclinicos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Frozen;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;

public class ManejadorParaclinicos {

//	private static Logger log = Logger.getLogger(ManejadorParaclinicos.class);

	public static final String EXAMEN_SEMANA_GESTACION = "00031";
	public static final String EXAMEN_ALTURA_UTERINA = "00034";
	public static final String EXAMEN_TENSION_ARTERIAL = "00033";
	public static final String EXAMEN_FRECUENCIA_CARDIACA_FETAL = "00037";
	public static final String EXAMEN_INCREMENTO_PESO = "00032";

	public static final String TIPO_PARACLINICO = "1";
	public static final String TIPO_VALORACION_OBSTETRICA = "2";

	private String tipo_examen;
	private Component contenedor;
	private String pcodigo_historia;
	private String nro_documento;
	private ZKWindow zkWindow;

	private Grid gridResultados;
	private Columns columnsResultados;
	private Rows rowsResultado;
	private Auxhead auxheadResultados;

	public ManejadorParaclinicos(String tipo_examen, Component contenedor,
			String pcodigo_historia, String nro_documento, ZKWindow zkWindow) {
		this.tipo_examen = tipo_examen;
		this.contenedor = contenedor;
		this.pcodigo_historia = pcodigo_historia;
		this.zkWindow = zkWindow;
		this.nro_documento = nro_documento;
		inicializar();
	}

	private void inicializar() {
		contenedor.getChildren().clear();
		gridResultados = new Grid();
		gridResultados.setMold("paging");
		gridResultados.setPageSize(20);
		auxheadResultados = new Auxhead();
		columnsResultados = new Columns();

		Auxheader auxheader = new Auxheader();
		auxheader.setAlign("center");
		if (tipo_examen.equals(TIPO_PARACLINICO)) {
			auxheader.setLabel("REGISTROS DE RESULTADOS PARACLINICOS");
		} else if (tipo_examen.equals(TIPO_VALORACION_OBSTETRICA)) {
			auxheader.setLabel("REGISTROS DE VALORACION OBSTETRICA");
		}
		auxheader.setColspan(3);
		auxheadResultados.appendChild(auxheader);

		gridResultados.appendChild(auxheadResultados);

		Column column = new Column("");
		if (tipo_examen.equals(TIPO_PARACLINICO)) {
			column.setLabel("Paraclinico");
		} else if (tipo_examen.equals(TIPO_VALORACION_OBSTETRICA)) {
			column.setLabel("V. obstetrica");
		}
		column.setWidth("170px");
		columnsResultados.appendChild(column);

		column = new Column("Fecha");
		column.setWidth("120px");
		columnsResultados.appendChild(column);

		column = new Column("Resultado");
		column.setWidth("120px");
		columnsResultados.appendChild(column);

		gridResultados.appendChild(columnsResultados);

		Frozen frozen = new Frozen();
		frozen.appendChild(new Div());
		frozen.setColumns(3);

		gridResultados.appendChild(frozen);

		rowsResultado = new Rows();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", zkWindow.codigo_empresa);
		parametros.put("codigo_sucursal", zkWindow.codigo_sucursal);
		parametros.put("codigo_historia", pcodigo_historia);
		parametros.put("tipo_examen", tipo_examen);

		List<Phistorias_paraclinicos> listado = zkWindow.getServiceLocator()
				.getPhistorias_paraclinicosService().listar(parametros);

		for (Phistorias_paraclinicos phistorias_paraclinicos : listado) {
			Row row_fila = new Row();
			Cell celda = Utilidades.obtenerCell(phistorias_paraclinicos
					.getPexamenes_paraclinicos().getNombre(), Textbox.class,
					true, true);
			row_fila.appendChild(celda);

			celda = Utilidades.obtenerCell(null, Datebox.class, false, false);
			Datebox datebox_fecha = (Datebox) celda.getFirstChild();
			datebox_fecha.setId("datebox_fecha_" + tipo_examen + "_"
					+ pcodigo_historia + "_"
					+ phistorias_paraclinicos.getCodigo_examen());
			row_fila.appendChild(celda);

			celda = Utilidades.obtenerCell("", Textbox.class, true, false);
			Textbox textbox_resultado = (Textbox) celda.getFirstChild();
			textbox_resultado.setId("textbox_resultado_" + tipo_examen + "_"
					+ pcodigo_historia + "_"
					+ phistorias_paraclinicos.getCodigo_examen());
			final Popup popupResultados = generarPopupResultados(
					textbox_resultado, false,
					phistorias_paraclinicos.getPexamenes_paraclinicos());
			textbox_resultado.setPopup(popupResultados);
			row_fila.appendChild(celda);

			row_fila.setValue(phistorias_paraclinicos);
			rowsResultado.appendChild(row_fila);

		}

		gridResultados.appendChild(rowsResultado);

		contenedor.appendChild(gridResultados);

	}

	public Datebox getDateboxFecha(String pcodigo_historia, String tipo_examen,
			String codigo_examen) {
		Datebox datebox_fecha = null;
		if (contenedor.hasFellow("datebox_fecha_" + tipo_examen + "_"
				+ pcodigo_historia + "_" + codigo_examen)) {
			datebox_fecha = (Datebox) contenedor.getFellow("datebox_fecha_"
					+ tipo_examen + "_" + pcodigo_historia + "_"
					+ codigo_examen);
		}
		return datebox_fecha;
	}

	public Textbox getTextboxFecha(String pcodigo_historia, String tipo_examen,
			String codigo_examen) {
		Textbox textbox_fecha = null;
		if (contenedor.hasFellow("datebox_fecha_" + tipo_examen + "_"
				+ pcodigo_historia + "_" + codigo_examen)) {
			textbox_fecha = (Textbox) contenedor.getFellow("textbox_resultado_"
					+ tipo_examen + "_" + pcodigo_historia + "_"
					+ codigo_examen);
		}
		return textbox_fecha;
	}

	public List<Presultados_paraclinicos> obtenerResultados_paraclinicos() {
		//log.info("ejecutando metodo @obtenerResultados_paraclinicos() ===> "
				//+ nro_documento);
		List<Presultados_paraclinicos> listado = new ArrayList<Presultados_paraclinicos>();
		for (Component row_fila : rowsResultado.getChildren()) {
			Phistorias_paraclinicos phistorias_paraclinicos = (Phistorias_paraclinicos) ((Row) row_fila)
					.getValue();
			Datebox datebox_fecha = (Datebox) gridResultados
					.getFellow("datebox_fecha_" + tipo_examen + "_"
							+ pcodigo_historia + "_"
							+ phistorias_paraclinicos.getCodigo_examen());

			Textbox textbox_resultado = (Textbox) gridResultados
					.getFellow("textbox_resultado_" + tipo_examen + "_"
							+ pcodigo_historia + "_"
							+ phistorias_paraclinicos.getCodigo_examen());

			if (!datebox_fecha.getText().isEmpty()
					&& !textbox_resultado.getText().isEmpty()) {
				Presultados_paraclinicos presultados_paraclinicos = new Presultados_paraclinicos();
				presultados_paraclinicos
						.setCodigo_empresa(zkWindow.codigo_empresa);
				presultados_paraclinicos
						.setCodigo_sucursal(zkWindow.codigo_sucursal);
				presultados_paraclinicos
						.setCodigo_examen(phistorias_paraclinicos
								.getCodigo_examen());
				presultados_paraclinicos.setFecha(datebox_fecha.getText());
				presultados_paraclinicos.setResultado(textbox_resultado
						.getValue());
				presultados_paraclinicos.setNro_documento(nro_documento);

				Object valor_na = textbox_resultado
						.getAttribute("VALOR_NORMAL_ANORMAL");
				Object valor_descripcion_na = textbox_resultado
						.getAttribute("VALOR_DESCRIPCION_NA");

				if (valor_na != null) {
					if (valor_na.toString().equals("A")) {
						valor_descripcion_na = "";
					}
				}

				presultados_paraclinicos
						.setNormal_anormal(valor_na != null ? valor_na
								.toString() : "N");
				presultados_paraclinicos
						.setDescripcion_na(valor_descripcion_na != null ? valor_descripcion_na
								.toString() : "");

				listado.add(presultados_paraclinicos);
			}

		}
		return listado;
	}

	public String validarResultados_paraclinicos() {
		String mensaje = "";
		for (Component row_fila : rowsResultado.getChildren()) {
			Phistorias_paraclinicos phistorias_paraclinicos = (Phistorias_paraclinicos) ((Row) row_fila)
					.getValue();
			Datebox datebox_fecha = (Datebox) gridResultados
					.getFellow("datebox_fecha_" + tipo_examen + "_"
							+ pcodigo_historia + "_"
							+ phistorias_paraclinicos.getCodigo_examen());

			Textbox textbox_resultado = (Textbox) gridResultados
					.getFellow("textbox_resultado_" + tipo_examen + "_"
							+ pcodigo_historia + "_"
							+ phistorias_paraclinicos.getCodigo_examen());

			if (!datebox_fecha.getText().isEmpty()
					&& textbox_resultado.getText().isEmpty()) {
				mensaje = "Para registrar "
						+ (tipo_examen.equals(TIPO_PARACLINICO) ? "el resultado paraclinico"
								: tipo_examen
										.equals(TIPO_VALORACION_OBSTETRICA) ? "la valoracion obstetrica"
										: "")
						+ phistorias_paraclinicos.getPexamenes_paraclinicos()
								.getNombre() + "' debe "
						+ "ingresar la descripcion del resultado";
				break;
			} else if (datebox_fecha.getText().isEmpty()
					&& !textbox_resultado.getText().isEmpty()) {
				mensaje = "Para registrar "
						+ (tipo_examen.equals(TIPO_PARACLINICO) ? "el resultado paraclinico"
								: tipo_examen
										.equals(TIPO_VALORACION_OBSTETRICA) ? "la valoracion obstetrica"
										: "")
						+ phistorias_paraclinicos.getPexamenes_paraclinicos()
								.getNombre() + "' debe "
						+ "ingresar la fecha del resultado";
				break;
			}

		}
		return mensaje;
	}

	public void cargarHistorial_resultados() {
		//log.info("ejecutando metodo @cargarHistorial_resultados() ===> "
				//+ nro_documento);
		int mayor = 0;
		Map<String, List<Presultados_paraclinicos>> mapa_resultados = new HashMap<String, List<Presultados_paraclinicos>>();
		for (Component row_fila : rowsResultado.getChildren()) {
			Phistorias_paraclinicos phistorias_paraclinicos = (Phistorias_paraclinicos) ((Row) row_fila)
					.getValue();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", zkWindow.codigo_empresa);
			parameters.put("codigo_sucursal", zkWindow.codigo_sucursal);
			parameters.put("nro_documento", nro_documento);
			parameters.put("codigo_examen",
					phistorias_paraclinicos.getCodigo_examen());

			List<Presultados_paraclinicos> lista_resultados = zkWindow
					.getServiceLocator().getPresultados_paraclinicosService()
					.listar(parameters);

			if (lista_resultados.size() > mayor) {
				mayor = lista_resultados.size();
			}

			mapa_resultados.put(phistorias_paraclinicos.getCodigo_examen(),
					lista_resultados);

		}

		// Generar columnas froze
		Column columna;
		Auxheader auxheader;
		for (int i = mayor; i != 0; i--) {
			columna = new Column("Fecha");
			columna.setWidth("110px");
			columnsResultados.appendChild(columna);

			columna = new Column("Resultado");
			columna.setWidth("150px");
			columnsResultados.appendChild(columna);

			auxheader = new Auxheader();
			auxheader.setLabel("Examen " + i);
			auxheader.setColspan(2);
			auxheader.setAlign("center");
			auxheader.setStyle("font-weight:bold");
			auxheadResultados.appendChild(auxheader);
		}

		for (Component row_fila : rowsResultado.getChildren()) {
			Phistorias_paraclinicos phistorias_paraclinicos = (Phistorias_paraclinicos) ((Row) row_fila)
					.getValue();
			List<Presultados_paraclinicos> lista_resultados = mapa_resultados
					.get(phistorias_paraclinicos.getCodigo_examen());
			int vacias = mayor - lista_resultados.size();
			for (int i = 0; i < vacias; i++) {
				cargarHistorialResultadosParaclinicos(((Row) row_fila), null);
			}
			for (Presultados_paraclinicos presultados_paraclinicos : lista_resultados) {
				cargarHistorialResultadosParaclinicos(((Row) row_fila),
						presultados_paraclinicos);
			}
		}
	}

	private void cargarHistorialResultadosParaclinicos(Row fila,
			Presultados_paraclinicos presultados_paraclinicos) {
		if (presultados_paraclinicos == null) {
			fila.appendChild(Utilidades
					.obtenerCell("", Label.class, true, true));
			fila.appendChild(Utilidades
					.obtenerCell("", Label.class, true, true));
		} else {
			Pexamenes_paraclinicos pexamenes_paraclinicos = new Pexamenes_paraclinicos();
			pexamenes_paraclinicos.setCodigo_empresa(presultados_paraclinicos
					.getCodigo_empresa());
			pexamenes_paraclinicos.setCodigo_sucursal(presultados_paraclinicos
					.getCodigo_sucursal());
			pexamenes_paraclinicos.setCodigo(presultados_paraclinicos
					.getCodigo_examen());

			pexamenes_paraclinicos = zkWindow.getServiceLocator()
					.getPexamenes_paraclinicosService()
					.consultar(pexamenes_paraclinicos);

			fila.appendChild(Utilidades.obtenerCell(
					presultados_paraclinicos.getFecha(), Datebox.class, true,
					false));
			Cell celda = Utilidades.obtenerCell(
					presultados_paraclinicos.getResultado(), Textbox.class,
					true, false);
			Textbox textbox_resultado = (Textbox) celda.getFirstChild();

			textbox_resultado.setAttribute("VALOR_NORMAL_ANORMAL",
					presultados_paraclinicos.getNormal_anormal());
			textbox_resultado.setAttribute("VALOR_DESCRIPCION_NA",
					presultados_paraclinicos.getDescripcion_na());

			textbox_resultado.setPopup(generarPopupResultados(
					textbox_resultado, false, pexamenes_paraclinicos));
			fila.appendChild(celda);
		}
	}

	public void limpiarResultados_paraclinicos() {
		auxheadResultados.detach();
		columnsResultados.detach();
		rowsResultado.detach();
		gridResultados.detach();
		inicializar();
	}

	public Popup generarPopupResultados(Textbox textboxResultado,
			boolean readonly, Pexamenes_paraclinicos pexamenes_paraclinicos) {
		// final Image image = (Image)form.getFellow("img_"+id_alumno);
		final Textbox tbxRes = textboxResultado;
		// popup.setId("popupResultados_" + key);
		Vlayout vlayout = new Vlayout();

		if (pexamenes_paraclinicos.getNormal_anormal().equals("S")) {
			String valor_na = (String) tbxRes
					.getAttribute("VALOR_NORMAL_ANORMAL");
			String valor_descripcion_na = (String) tbxRes
					.getAttribute("VALOR_DESCRIPCION_NA");
			final Radiogroup radiogroup_anormal = new Radiogroup();
			Radio radio_normal = new Radio("Normal");
			radio_normal.setValue("N");
			radiogroup_anormal.appendChild(radio_normal);
			radiogroup_anormal.appendChild(new Space());
			Radio radio_anormal = new Radio("Anormal");
			radio_anormal.setValue("A");
			radiogroup_anormal.appendChild(radio_anormal);

			vlayout.appendChild(radiogroup_anormal);

			final Textbox textbox_anormal = new Textbox();
			textbox_anormal.setWidth("400px");
			textbox_anormal.setRows(4);
			textbox_anormal.setVisible(false);

			if (valor_na != null) {
				if (valor_na.equals("S")) {
					radio_normal.setChecked(true);
					radio_anormal.setChecked(false);
					textbox_anormal.setVisible(true);
				} else {
					radio_anormal.setChecked(true);
					radio_normal.setChecked(false);
					textbox_anormal.setVisible(false);
				}
			} else {
				radio_normal.setChecked(true);
				radio_anormal.setChecked(false);
				textbox_anormal.setVisible(false);
			}

			if (valor_descripcion_na != null) {
				textbox_anormal.setValue(valor_descripcion_na);
			}

			radiogroup_anormal.addEventListener(Events.ON_CHECK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							Radio radio_seleccionado = radiogroup_anormal
									.getSelectedItem();
							if (radio_seleccionado != null) {
								if (radio_seleccionado.getValue().toString()
										.equals("A")) {
									textbox_anormal.setVisible(true);
								} else {
									textbox_anormal.setVisible(false);
								}
								tbxRes.setAttribute("VALOR_NORMAL_ANORMAL",
										radio_seleccionado.getValue()
												.toString());
							}
						}
					});

			textbox_anormal.addEventListener(Events.ON_CHANGING,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							InputEvent inputEvent = (InputEvent) arg0;
							tbxRes.setAttribute("VALOR_DESCRIPCION_NA",
									inputEvent.getValue());
						}
					});

			vlayout.appendChild(textbox_anormal);
		}

		final Textbox textbox = new Textbox();
		textbox.setWidth("400px");
		textbox.setRows(8);

		vlayout.appendChild(new Label("Resultado paraclinico"));
		vlayout.appendChild(textbox);
		textbox.setValue(tbxRes.getValue());
		textbox.addEventListener("onChanging", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				InputEvent inputevent = (InputEvent) event;
				tbxRes.setValue(inputevent.getValue());
			}
		});
		final Popup popup = new Popup();
		popup.addEventListener(Events.ON_OPEN, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				textbox.setValue(tbxRes.getValue());
				textbox.setFocus(true);
			}
		});
		popup.appendChild(vlayout);
		contenedor.appendChild(popup);
		return popup;
	}

}
