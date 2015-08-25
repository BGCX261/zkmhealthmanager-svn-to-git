/*
 * aportes_cotizacionesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Aportantes_ma;
import healthmanager.modelo.bean.Aportes_cotizaciones;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especificaciones_aportes;
import healthmanager.modelo.service.Aportes_cotizacionesService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.res.LabelAlign;
import com.framework.res.LineStringToList;
import com.framework.util.MensajesUtil;

public class Aportes_cotizacionesAction extends ZKWindow {

	private Aportes_cotizacionesService aportes_cotizacionesService;

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Groupbox groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Bandbox tbxNro_identificacion;
	@View
	private Datebox dtbxFecha_ingreso;
	@View
	private Datebox dtbxMes;
	@View
	private Bandbox tbxCodigo_aportadores;
	@View
	private Doublebox dbxValor_cotiza;
	@View
	private Doublebox dbxIbc;
	@View
	private Checkbox chbEstado;

	@View
	private Textbox tbxNomAportante;
	@View
	private Textbox tbxNomCotizante;
	@View
	private Textbox tbxNomAportante2;
	@View
	private Bandbox tbxCodigo_aportadores2;

	/* componentes especificaciones */
	@View
	private Listbox lbxEspecificaciones;
	@View
	private Row row_aportante;
	@View
	private Textbox tbxDescipcionEspecificaciones;

	@View
	private Listbox lbxNovedades;

	private final String PARAM_APORTES = "_pa";
	private final String PARAM_MOTIVO = "_pm";

	private final String MOTIVO_NO_EXISTE_PACIENTE = "El paciente no existe!";

	private String aporte_txt;
	private Aportes_cotizaciones aportes_cotizaciones;

	public void listarCombos() {
		listarParameter();
		listarEspecificaciones();
		listarElementoListbox(lbxNovedades, true);
	}

	public void listarEspecificaciones() {
		lbxEspecificaciones.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("-seleccione-");
		lbxEspecificaciones.appendChild(listitem);

		Map<String, Object> parameters = new HashMap();
		parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
		parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		List<Especificaciones_aportes> especificacionesAportes = getServiceLocator()
				.getEspecificacionesAportesService().listar(parameters);
		for (Especificaciones_aportes especificaciones : especificacionesAportes) {
			listitem = new Listitem();
			listitem.setValue(especificaciones);
			listitem.setLabel("" + especificaciones.getNombre());
			lbxEspecificaciones.appendChild(listitem);
		}

		listitem = new Listitem();
		listitem.setValue("&new@$");
		listitem.setLabel("-- Agergar nueva especificacion --");
		listitem.setStyle("color: red;font-weight: bold;");
		lbxEspecificaciones.appendChild(listitem);

		if (lbxEspecificaciones.getItemCount() > 0) {
			lbxEspecificaciones.setSelectedIndex(0);
		}

		lbxEspecificaciones.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if ((lbxEspecificaciones.getSelectedItem().getValue() + "")
						.toString().equals("&new@$")) {
					cargarMenuEspecificaciones();
				} else if (!(lbxEspecificaciones.getSelectedItem().getValue() + "")
						.toString().equals("")) {
					Especificaciones_aportes especificacionesAportes = (Especificaciones_aportes) lbxEspecificaciones
							.getSelectedItem().getValue();
					tbxDescipcionEspecificaciones
							.setValue(especificacionesAportes.getDescripcion());
				} else {
					tbxDescipcionEspecificaciones.setValue("");
				}
			}
		});
	}

	protected void cargarMenuEspecificaciones() throws Exception {
		Map params = new HashMap();
		params.put("anexo3", null);

		Component componente = Executions.createComponents(
				"/pages/especificaciones_aportes.zul", this, params);
		final Window ventana = (Window) componente;
		ventana.setWidth("800px");
		// ventana.setHeight("90%");
		ventana.setVflex("1");
		ventana.doModal();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("am.nro_identificacion_afiliado || ' ' || p.nro_identificacion || ' ' || p.nombre1||' '||p.nombre2 || ' ' || p.apellido1||' '||p.apellido2");
		listitem.setLabel("Datos del afiliado");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("fecha_ingreso::varchar");
		listitem.setLabel("Fecha Ingreso");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("apm.nro_identificacon || ' ' || apm.nombre");
		listitem.setLabel("Datos del aportante");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent)
							.setText(((Textbox) abstractComponent).getText()
									.trim().toUpperCase());
				}
			}
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals(
						"lbxParameter")) {
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setSelectedIndex(0);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setText("0.00");
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setText("");
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setValue(new Date());
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setChecked(false);
			}
			if (abstractComponent instanceof Radiogroup) {
				((Radio) ((Radiogroup) abstractComponent).getFirstChild())
						.setChecked(true);
			}
		}
		chbEstado.setChecked(true);
		setReadOnlyCamposLLaves(false);
		aporte_txt = null;
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxNro_identificacion
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_aportadores
				.setStyle("text-transform:uppercase;background-color:white");
		dbxValor_cotiza.setStyle("background-color:white");
		dbxIbc.setStyle("background-color:white");
		// lbxNovedades.setStyle("background-color:white");

		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";

		// if (lbxNovedades.getSelectedItem().getValue().toString().trim()
		// .equals("")) {
		// lbxNovedades.setStyle("background-color:#F6BBBE");
		// valida = false;
		// }

		if (tbxNro_identificacion.getText().trim().equals("")) {
			tbxNro_identificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxCodigo_aportadores.getText().trim().equals("")) {
			tbxCodigo_aportadores
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (dbxValor_cotiza.getText().trim().equals("")) {
			dbxValor_cotiza.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (dbxIbc.getText().trim().equals("")) {
			dbxIbc.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (dbxIbc.getValue() != null) {
			if (dbxIbc.getValue().doubleValue() == 0d) {
				dbxIbc.focus();
				msj = "El valor del IBC no puede ser igual a cero";
				valida = false;
			}
		}

		if (!valida) {
			Messagebox
					.show("" + msj, usuarios.getNombres() + " recuerde que...",
							Messagebox.OK, Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm2() throws Exception {

		tbxCodigo_aportadores2.setStyle("background-color:white");
		lbxEspecificaciones.setStyle("background-color:white");

		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo_aportadores2.getValue().trim().isEmpty()) {
			tbxCodigo_aportadores2.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxEspecificaciones.getSelectedIndex() == 0) {
			lbxEspecificaciones.setStyle("background-color:#F6BBBE");
			valida = false;
		} else {
			try {
				Especificaciones_aportes especificacionesAportes = (Especificaciones_aportes) lbxEspecificaciones
						.getSelectedItem().getValue();
				Especificaciones_aportesAction
						.validarPosiciones(especificacionesAportes);
			} catch (ValidacionRunTimeException e) {
				valida = false;
				msj = e.getMessage();
			}
		}

		if (aporte_txt == null) {
			valida = false;
			msj = "Debe cargar el archivo TXT";
		}

		if (!valida) {
			Messagebox
					.show("" + msj, usuarios.getNombres() + " recuerde que...",
							Messagebox.OK, Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public void buscarAportante(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", value.toUpperCase().trim());

			getServiceLocator().getAportantesMaService().setLimit(
					"limit 25 offset 0");

			List<Aportantes_ma> list = getServiceLocator()
					.getAportantesMaService().listar(parameters);

			lbx.getItems().clear();

			for (Aportantes_ma dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				String addDV = dato.getDv() != null ? (!dato.getDv().isEmpty() ? "-"
						+ dato.getDv()
						: "")
						: "";

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacon()
						+ addDV));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre() + ""));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void buscarCotizante(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			parameters.put("tipo_afiliado", "C");

			getServiceLocator().getAfiliacionesMeService().setLimit(
					"limit 25 offset 0");

			List<Afiliaciones_me> list = getServiceLocator()
					.getAfiliacionesMeService().listar(parameters);

			lbx.getItems().clear();

			for (Afiliaciones_me dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getPaciente()
						.getTipo_identificacion() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getPaciente()
						.getNro_identificacion()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getPaciente()
						.getNombreCompleto() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getPaciente().getSexo()
						.equals("M") ? "Masculino" : "Femenino"));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void selectedAportante(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxCodigo_aportadores.setValue("");
			tbxNomAportante.setValue("");
			tbxCodigo_aportadores.setAttribute("aportante", null);
		} else {
			Aportantes_ma dato = (Aportantes_ma) listitem.getValue();
			tbxCodigo_aportadores.setValue(dato.getCodigo());
			tbxCodigo_aportadores.setAttribute("aportante", dato);
			tbxNomAportante.setValue(dato.getNombre());

			// Esto me indica, que el aporte a campiado de aportante
			if (aportes_cotizaciones != null) {
				if (aportes_cotizaciones.getCodigo_aportadores().equals(
						dato.getCodigo())) {
					tbxAccion.setText("modificar");
				} else {
					tbxAccion.setText("registrar");
				}
			}
		}
		tbxCodigo_aportadores.close();
	}

	public void selectedAportante2(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxCodigo_aportadores2.setValue("");
			tbxNomAportante2.setValue("");
			tbxCodigo_aportadores2.setAttribute("aportante", null);
		} else {
			Aportantes_ma dato = (Aportantes_ma) listitem.getValue();
			tbxCodigo_aportadores2.setValue(dato.getCodigo());
			tbxNomAportante2.setValue(dato.getNombre());
			tbxCodigo_aportadores2.setAttribute("aportante", dato);
		}
		tbxCodigo_aportadores2.close();
	}

	public void selectedCotizante(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNro_identificacion.setValue("");
			tbxNomCotizante.setValue("");
		} else {
			Afiliaciones_me dato = (Afiliaciones_me) listitem.getValue();
			tbxNro_identificacion.setValue(dato
					.getNro_identificacion_afiliado());
			tbxNomCotizante.setValue(dato.getPaciente().getNombreCompleto());
		}
		tbxNro_identificacion.close();
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getAportesCotizacionesService().setLimit(
					"limit 25 offset 0");

			List<Aportes_cotizaciones> lista_datos = getServiceLocator()
					.getAportesCotizacionesService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Aportes_cotizaciones aportes_cotizaciones : lista_datos) {
				rowsResultado
						.appendChild(crearFilas(aportes_cotizaciones, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Aportes_cotizaciones aportes_cotizaciones = (Aportes_cotizaciones) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		Afiliaciones_me afiliacionesMe = new Afiliaciones_me();
		afiliacionesMe.setCodigo_empresa(getSucursal().getCodigo_empresa());
		afiliacionesMe.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
		afiliacionesMe.setNro_identificacion_afiliado(aportes_cotizaciones
				.getNro_identificacion());
		afiliacionesMe = getServiceLocator().getAfiliacionesMeService()
				.consultar(afiliacionesMe);

		Aportantes_ma aportantesMa = new Aportantes_ma();
		aportantesMa.setCodigo_empresa(getSucursal().getCodigo_empresa());
		aportantesMa.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
		aportantesMa.setCodigo(aportes_cotizaciones.getCodigo_aportadores());
		aportantesMa = getServiceLocator().getAportantesMaService().consultar(
				aportantesMa);

		/* cargamos la fecha */
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.YEAR,
				Integer.parseInt(aportes_cotizaciones.getAnio()));
		calendar.set(Calendar.MONTH,
				Integer.parseInt(aportes_cotizaciones.getMes()) - 1);
		/* fin */

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(aportes_cotizaciones.getNro_identificacion()
				+ ""));
		fila.appendChild(new Label(afiliacionesMe != null ? afiliacionesMe
				.getPaciente().getNombreCompleto() : ""));
		fila.appendChild(new Label(new SimpleDateFormat("dd-MM-yyyy")
				.format(aportes_cotizaciones.getFecha_ingreso()) + ""));
		fila.appendChild(new Label(new SimpleDateFormat("yyyy-MMMMMMMMM")
				.format(calendar.getTime()).toUpperCase()));
		fila.appendChild(new Label(aportes_cotizaciones.getCodigo_aportadores()
				+ ""));
		fila.appendChild(new Label(aportantesMa != null ? aportantesMa
				.getNombre() : ""));
		fila.appendChild(new LabelAlign(aportes_cotizaciones.getValor_cotiza(),
				"#,##0.00"));
		fila.appendChild(new LabelAlign(aportes_cotizaciones.getIbc(),
				"#,##0.00"));
		fila.appendChild(new Label(
				aportes_cotizaciones.getEstado().equals("S") ? "Activo"
						: "Anulado"));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(aportes_cotizaciones);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(aportes_cotizaciones);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //

				final Aportes_cotizaciones aportes_cotizaciones = new Aportes_cotizaciones();
				aportes_cotizaciones.setCodigo_empresa(getSucursal()
						.getCodigo_empresa());
				aportes_cotizaciones.setCodigo_sucursal(getSucursal()
						.getCodigo_sucursal());
				aportes_cotizaciones
						.setNro_identificacion(tbxNro_identificacion.getValue());
				aportes_cotizaciones.setFecha_ingreso(new Timestamp(
						dtbxFecha_ingreso.getValue().getTime()));
				aportes_cotizaciones.setMes(new SimpleDateFormat("MM")
						.format(new Timestamp(dtbxMes.getValue().getTime())));
				aportes_cotizaciones.setAnio(new SimpleDateFormat("yyyy")
						.format(new Timestamp(dtbxMes.getValue().getTime())));
				aportes_cotizaciones
						.setCodigo_aportadores(tbxCodigo_aportadores.getValue());
				aportes_cotizaciones
						.setValor_cotiza((dbxValor_cotiza.getValue() != null ? dbxValor_cotiza
								.getValue() : 0.00));
				aportes_cotizaciones.setIbc((dbxIbc.getValue() != null ? dbxIbc
						.getValue() : 0.00));
				aportes_cotizaciones.setEstado(chbEstado.isChecked() ? "S"
						: "N");
				aportes_cotizaciones.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				aportes_cotizaciones.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				aportes_cotizaciones.setCreacion_user(usuarios.getCodigo()
						.toString());
				aportes_cotizaciones.setUltimo_user(usuarios.getCodigo()
						.toString());
				aportes_cotizaciones.setNovedades(lbxNovedades
						.getSelectedItem().getValue().toString());

				boolean mostrar_msj_satisfaccion = true;
				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					final Aportes_cotizaciones aportes_cotizaciones_temp = aportes_cotizacionesService
							.consultar(aportes_cotizaciones);
					if (aportes_cotizaciones_temp != null) {

						Aportantes_ma aportantesMa = new Aportantes_ma();
						aportantesMa.setCodigo_empresa(getSucursal()
								.getCodigo_empresa());
						aportantesMa.setCodigo_sucursal(getSucursal()
								.getCodigo_sucursal());
						aportantesMa.setCodigo(aportes_cotizaciones_temp
								.getCodigo_aportadores());
						aportantesMa = getServiceLocator()
								.getAportantesMaService().consultar(
										aportantesMa);

						mostrar_msj_satisfaccion = false;
						DecimalFormat decimalFormat = new DecimalFormat(
								"#,##0.00");
						Messagebox
								.show("Ya existe un aporte para el paciente "
										+ tbxNomCotizante.getValue()
										+ (aportantesMa != null ? " y el aportante "
												+ aportantesMa.getCodigo()
												+ " "
												+ aportantesMa.getNombre()
												: "")
										+ "\n en el mes de "
										+ new SimpleDateFormat(
												"MMMMMMM 'de' yyyy", IConstantes.locale)
												.format(new Timestamp(dtbxMes
														.getValue().getTime()))
												.toUpperCase()
										+ " con los siguientes valores:  "
										+ "\n\t * IBC: "
										+ decimalFormat
												.format(aportes_cotizaciones_temp
														.getIbc())
										+ "\n\t * Cotizaciones: "
										+ decimalFormat
												.format(aportes_cotizaciones_temp
														.getValor_cotiza())
										+ "\n  ¿Deseas adicionarlo?",
										"Informacion de Aporte",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													// Incrementamos el IBC
													aportes_cotizaciones_temp.setIbc(aportes_cotizaciones
															.getIbc()
															+ aportes_cotizaciones_temp
																	.getIbc());

													// Incrementamos las
													// cotizaciones
													aportes_cotizaciones_temp.setValor_cotiza(aportes_cotizaciones
															.getValor_cotiza()
															+ aportes_cotizaciones_temp
																	.getValor_cotiza());
													// actualizamos el aporte
													actualizarAporte(aportes_cotizaciones_temp);

													// eliminamos el aporte
													// anterior
													eliminarDatos(Aportes_cotizacionesAction.this.aportes_cotizaciones);

													// Mostramos el aporte
													// actual
													mostrarDatos(aportes_cotizaciones_temp);
												}
											}
										});
					} else {
						guardarAporte(aportes_cotizaciones);
					}
				} else {
					actualizarAporte(aportes_cotizaciones);
				}

				if (mostrar_msj_satisfaccion) {
					Messagebox.show(
							"Los datos se guardaron satisfactoriamente",
							"Informacion ..", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void guardarAporte(Aportes_cotizaciones aportes_cotizaciones)
			throws Exception {
		aportes_cotizacionesService.crear(aportes_cotizaciones);
		accionForm(true, "registrar");
	}

	private void actualizarAporte(Aportes_cotizaciones aportes_cotizaciones)
			throws Exception {
		int result = aportes_cotizacionesService
				.actualizar(aportes_cotizaciones);
		if (result == 0) {
			throw new Exception(
					"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
		}
	}

	public void guardarDatos2() throws Exception {
		try {
			setUpperCase();
			if (validarForm2()) {
				// Cargamos los componentes //
				Especificaciones_aportes especificacionesAportes = (Especificaciones_aportes) lbxEspecificaciones
						.getSelectedItem().getValue();
				StringTokenizer stringTokenizer = new StringTokenizer(
						aporte_txt, "\n");
				List<Aportes_cotizaciones> list_ap = new ArrayList<Aportes_cotizaciones>();
				List<Map<String, Object>> list_no_validos = new ArrayList<Map<String, Object>>();
				while (stringTokenizer.hasMoreTokens()) {
					String in = stringTokenizer.nextToken();
					List<String> list = LineStringToList.toList(in,
							especificacionesAportes.getSeparado_por());

					Aportes_cotizaciones aportes_cotizaciones = new Aportes_cotizaciones();
					aportes_cotizaciones.setCodigo_empresa(getSucursal()
							.getCodigo_empresa());
					aportes_cotizaciones.setCodigo_sucursal(getSucursal()
							.getCodigo_sucursal());
					aportes_cotizaciones.setNro_identificacion(list
							.get(especificacionesAportes.getPos_cedula() - 1));
					aportes_cotizaciones.setFecha_ingreso(new Timestamp(
							Calendar.getInstance().getTimeInMillis()));
					aportes_cotizaciones.setMes(validar((getDouble(
							list.get(especificacionesAportes.getPos_mes() - 1),
							especificacionesAportes.getPos_mes()).intValue())));
					aportes_cotizaciones.setAnio(list
							.get(especificacionesAportes.getPos_anio() - 1));
					aportes_cotizaciones
							.setCodigo_aportadores(tbxCodigo_aportadores2
									.getValue());
					aportes_cotizaciones.setValor_cotiza(getDouble(
							list.get(especificacionesAportes
									.getPos_cotizacion() - 1),
							especificacionesAportes.getPos_cotizacion()));
					aportes_cotizaciones.setIbc(getDouble(
							list.get(especificacionesAportes.getPos_ibc() - 1),
							especificacionesAportes.getPos_ibc()));
					aportes_cotizaciones.setEstado("S");
					aportes_cotizaciones.setCreacion_date(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					aportes_cotizaciones.setUltimo_update(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					aportes_cotizaciones.setCreacion_user(getUsuarios()
							.getCodigo().toString());
					aportes_cotizaciones.setUltimo_user(getUsuarios()
							.getCodigo().toString());
					aportes_cotizaciones.setNovedades("");

					Afiliaciones_me afiliacionesMe = new Afiliaciones_me();
					afiliacionesMe.setCodigo_empresa(getSucursal()
							.getCodigo_empresa());
					afiliacionesMe.setCodigo_sucursal(getSucursal()
							.getCodigo_sucursal());
					afiliacionesMe
							.setNro_identificacion_afiliado(aportes_cotizaciones
									.getNro_identificacion());

					if (getServiceLocator().getAfiliacionesMeService()
							.consultar(afiliacionesMe) == null) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put(PARAM_APORTES, aportes_cotizaciones);
						map.put(PARAM_MOTIVO, MOTIVO_NO_EXISTE_PACIENTE);
						list_no_validos.add(map);
					} else {
						list_ap.add(aportes_cotizaciones);
					}
				}

				for (Aportes_cotizaciones aportesCotizaciones : list_ap) {
					getServiceLocator().getAportesCotizacionesService().crear(
							aportesCotizaciones);
				}

				if (!list_no_validos.isEmpty()) {
					generarExcelAportesNoValidos(list_no_validos);
				}

				accionForm(true, "registrar");
				Messagebox
						.show("Los datos se guardaron satisfactoriamente",
								"Informacion ..", Messagebox.OK,
								Messagebox.INFORMATION);

			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}

	}

	// private String getCodigoAportante(Bandbox bandboxRegistrosMacro){
	// String codigo = "";
	// Aportantes_ma aportantes_ma = (Aportantes_ma)
	// bandboxRegistrosMacro.getAttribute("aportante");
	// if(aportantes_ma != null){
	// codigo = aportantes_ma.getCodigo();
	// }
	// return codigo;
	// }

	private String validar(int i) {
		if (i > 11)
			throw new ValidacionRunTimeException("Mes no valido " + (i + 1));
		if (i <= 9)
			return "0" + i;
		return i + "";
	}

	private Double getDouble(String in, int pos) {
		if (!in.trim().isEmpty()) {
			if (in.trim().length() == 1 && in.matches("[0-9]$")) {
				return Double.parseDouble(in);
			} else if (in.matches("[0-9][//.0-9]*[0-9]$")
					&& in.replaceAll("[0-9]", "").length() < 2) {
				return Double.parseDouble(in);
			} else
				throw new ValidacionRunTimeException(
						"Error al cargar valor ?1 en la posicion ?2".replace(
								"?1", in).replace("?2", pos + ""));
		} else
			return 0d;
	}

	private void setReadOnlyCamposLLaves(boolean b) {
		tbxNro_identificacion.setDisabled(b);
		// dtbxMes.setDisabled(b);
		// tbxCodigo_aportadores.setDisabled(b);
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		aportes_cotizaciones = (Aportes_cotizaciones) obj;
		try {
			/* cargamos la fecha */
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.YEAR,
					Integer.parseInt(aportes_cotizaciones.getAnio()));
			calendar.set(Calendar.MONTH,
					Integer.parseInt(aportes_cotizaciones.getMes()) - 1);
			/* fin */

			setReadOnlyCamposLLaves(true);
			tbxNro_identificacion.setValue(aportes_cotizaciones
					.getNro_identificacion());
			dtbxFecha_ingreso.setValue(aportes_cotizaciones.getFecha_ingreso());
			dtbxMes.setValue(calendar.getTime());
			tbxCodigo_aportadores.setValue(aportes_cotizaciones
					.getCodigo_aportadores());
			dbxValor_cotiza.setValue(aportes_cotizaciones.getValor_cotiza());
			dbxIbc.setValue(aportes_cotizaciones.getIbc());
			chbEstado.setChecked(aportes_cotizaciones.getEstado().equals("S"));

			for (int i = 0; i < lbxNovedades.getItemCount(); i++) {
				Listitem listitem = lbxNovedades.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(aportes_cotizaciones.getNovedades())) {
					listitem.setSelected(true);
					break;
				}
			}
			buscarAfiliador(aportes_cotizaciones.getNro_identificacion());
			buscarAportante(aportes_cotizaciones.getCodigo_aportadores());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void buscarAfiliador(String nro_id) {
		Afiliaciones_me afiliacionesMe = new Afiliaciones_me();
		afiliacionesMe.setCodigo_empresa(sucursal.getCodigo_empresa());
		afiliacionesMe.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		afiliacionesMe.setNro_identificacion_afiliado(nro_id + "");
		afiliacionesMe = getServiceLocator().getAfiliacionesMeService()
				.consultar(afiliacionesMe);
		tbxNomCotizante.setValue(afiliacionesMe != null ? afiliacionesMe
				.getPaciente().getNombreCompleto() : "");
	}

	private void buscarAportante(String codigo) {
		Aportantes_ma aportantesMa = new Aportantes_ma();
		aportantesMa.setCodigo_empresa(getSucursal().getCodigo_empresa());
		aportantesMa.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
		aportantesMa.setCodigo(codigo);
		aportantesMa = getServiceLocator().getAportantesMaService().consultar(
				aportantesMa);
		tbxNomAportante.setValue(aportantesMa != null ? aportantesMa
				.getNombre() : "(NO EXISTE)");
	}

	public void eliminarDatos(Object obj) throws Exception {
		Aportes_cotizaciones aportes_cotizaciones = (Aportes_cotizaciones) obj;
		try {
			int result = getServiceLocator().getAportesCotizacionesService()
					.eliminar(aportes_cotizaciones);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public String getAporte_txt() {
		return aporte_txt;
	}

	public void setAporte_txt(String aporteTxt) {
		aporte_txt = aporteTxt;
	}

	@Override
	public void init() throws Exception {
		listarCombos();
		cargarEvento();
	}

	private void cargarEvento() {
		dtbxMes.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// Esto me indica, que el aporte a campiado de aportante
				if (aportes_cotizaciones != null && dtbxMes.getValue() != null) {
					String mes = new SimpleDateFormat("MM")
							.format(new Timestamp(dtbxMes.getValue().getTime()));
					String anio = new SimpleDateFormat("yyyy")
							.format(new Timestamp(dtbxMes.getValue().getTime()));
					if (aportes_cotizaciones.getMes().equals(mes)
							&& aportes_cotizaciones.getAnio().equals(anio)) {
						tbxAccion.setText("modificar");
					} else {
						tbxAccion.setText("registrar");
					}
				}
			}
		});
	}
	
	
	private void generarExcelAportesNoValidos(
			List<Map<String, Object>> list_no_validos) throws Exception{
		Workbook libroMetasMatriz = new HSSFWorkbook();
		String fecha_archivo = new SimpleDateFormat("yyyyMMddhhmmss")
		.format(Calendar.getInstance().getTime());
		
		// Directorio de guardado
		File directorioGuardado = getDirectorio();
		
		String nombre_archivo = "error_aporte_" + fecha_archivo + ".xls";
		String url_file = directorioGuardado.getAbsolutePath() + File.separator
				+ nombre_archivo;
		
		Sheet hoja1 = libroMetasMatriz.createSheet("Aportes");
		
		// Cargamos estilos
				// cargamos los estilos.
				HSSFFont fontBold = (HSSFFont) libroMetasMatriz.createFont();
				fontBold.setFontName(HSSFFont.FONT_ARIAL);
				fontBold.setFontHeightInPoints((short) 10);
				fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				fontBold.setColor(HSSFColor.WHITE.index);

				HSSFFont fontBoldTitulo = (HSSFFont) libroMetasMatriz.createFont();
				fontBoldTitulo.setFontName(HSSFFont.FONT_ARIAL);
				fontBoldTitulo.setFontHeightInPoints((short) 10);
				fontBoldTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				fontBoldTitulo.setColor(HSSFColor.BLACK.index);

				HSSFFont headerFont = (HSSFFont) libroMetasMatriz.createFont();
				headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
				// headerFont.setColor(IndexedColors.WHITE.index);
				fontBold.setFontName(HSSFFont.FONT_ARIAL);
				headerFont.setFontHeightInPoints((short) 9);

				Font fontNormal = libroMetasMatriz.createFont();
				fontNormal.setFontName(HSSFFont.FONT_ARIAL);
				fontNormal.setFontHeightInPoints((short) 10);

				DataFormat format = libroMetasMatriz.createDataFormat();

				CellStyle styleInforme = libroMetasMatriz.createCellStyle();
				styleInforme.setFont(fontNormal);
				styleInforme.setAlignment(CellStyle.ALIGN_CENTER);

				CellStyle styleNomFecha = libroMetasMatriz.createCellStyle();
				styleNomFecha.setFont(fontBold);
				styleNomFecha.setAlignment(CellStyle.ALIGN_CENTER);

				CellStyle styleFecha = libroMetasMatriz.createCellStyle();
				styleFecha.setFont(fontBold);
				styleFecha.setDataFormat(format.getFormat("dd/mm/yyyy"));

				CellStyle styleHeader = libroMetasMatriz.createCellStyle();
				// styleHeader.setFont(headerFont);
				styleHeader.setBorderTop(CellStyle.BORDER_THIN);
				styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
				styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
				styleHeader.setBorderRight(CellStyle.BORDER_THIN);

				CellStyle styleTexto = libroMetasMatriz.createCellStyle();
				styleTexto.setFont(fontNormal);
				styleTexto.setDataFormat((short) BuiltinFormats
						.getBuiltinFormat("text"));
				styleTexto.setBorderTop(CellStyle.BORDER_THIN);
				styleTexto.setBorderBottom(CellStyle.BORDER_THIN);
				styleTexto.setBorderLeft(CellStyle.BORDER_THIN);
				styleTexto.setBorderRight(CellStyle.BORDER_THIN);

				CellStyle styleTextoWrap = libroMetasMatriz.createCellStyle();
				styleTextoWrap.setFont(fontNormal);
				styleTextoWrap.setDataFormat((short) BuiltinFormats
						.getBuiltinFormat("text"));
				styleTextoWrap.setBorderTop(CellStyle.BORDER_THIN);
				styleTextoWrap.setBorderBottom(CellStyle.BORDER_THIN);
				styleTextoWrap.setBorderLeft(CellStyle.BORDER_THIN);
				styleTextoWrap.setBorderRight(CellStyle.BORDER_THIN);
				styleTextoWrap.setWrapText(true);

				CellStyle styleFecha2 = libroMetasMatriz.createCellStyle();
				styleFecha2.setFont(fontNormal);
				styleFecha2.setDataFormat(format.getFormat("yyyy-MM-dd"));
				styleFecha2.setBorderTop(CellStyle.BORDER_THIN);
				styleFecha2.setBorderBottom(CellStyle.BORDER_THIN);
				styleFecha2.setBorderLeft(CellStyle.BORDER_THIN);
				styleFecha2.setBorderRight(CellStyle.BORDER_THIN);

				// agregamos cabecera
				CellStyle styleNombre_empresa = libroMetasMatriz.createCellStyle();
				styleNombre_empresa.setFont(fontBoldTitulo);
				styleNombre_empresa.setAlignment(CellStyle.ALIGN_CENTER);

				HSSFCellStyle styleNombre_meses = (HSSFCellStyle) libroMetasMatriz
						.createCellStyle();
				styleNombre_meses.setAlignment(CellStyle.ALIGN_CENTER);
				styleNombre_meses.setBorderTop(CellStyle.BORDER_THIN);
				styleNombre_meses.setBorderBottom(CellStyle.BORDER_THIN);
				styleNombre_meses.setBorderLeft(CellStyle.BORDER_THIN);
				styleNombre_meses.setBorderRight(CellStyle.BORDER_THIN);
				styleNombre_meses.setRightBorderColor(HSSFColor.WHITE.index);
				styleNombre_meses.setLeftBorderColor(HSSFColor.WHITE.index);
				styleNombre_meses.setFillBackgroundColor(IndexedColors.SEA_GREEN
						.getIndex());
				styleNombre_meses.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				styleNombre_meses.setFont(fontBold);

				CellStyle styleNombreAreaFuncional = libroMetasMatriz.createCellStyle();
				styleNombreAreaFuncional.setFillPattern(CellStyle.SOLID_FOREGROUND);
				styleNombreAreaFuncional
						.setFillBackgroundColor(HSSFColor.SEA_GREEN.index);
				styleNombreAreaFuncional.setFont(fontBold);

				CellStyle styleNormal = libroMetasMatriz.createCellStyle();
				styleNormal.setFont(fontNormal);

				// estylo de prueba

				HSSFCellStyle style = (HSSFCellStyle) libroMetasMatriz
						.createCellStyle();
				// style.setBorderTop((short) 6); // double lines border
				// style.setBorderBottom((short) 1); // single line border
				// style.setFillPattern(HSSFCellStyle.DIAMONDS);
				// style.setFillBackgroundColor(HSSFColor.RED.index);
				style.setBorderTop(CellStyle.BORDER_THIN);
				style.setBorderBottom(CellStyle.BORDER_THIN);
				style.setBorderLeft(CellStyle.BORDER_THIN);
				style.setBorderRight(CellStyle.BORDER_THIN);
				styleNombre_meses.setRightBorderColor(HSSFColor.WHITE.index);
				styleNombre_meses.setLeftBorderColor(HSSFColor.WHITE.index);
				style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFont(fontBold);
				
				
				int row = 0;
				hoja1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
				org.apache.poi.ss.usermodel.Row filaEmpresa = hoja1.createRow(row++);
				org.apache.poi.ss.usermodel.Cell celda = filaEmpresa.createCell(0,
						org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
				celda.setCellValue("APORTES NO IMPORTADOS");
				celda.setCellStyle(styleNombre_empresa);
				
				
				int initCol = 0;
				org.apache.poi.ss.usermodel.Row filaCol = hoja1.createRow(row++);

				// creamos columnas
				agregarCelda(filaCol, initCol++, "AÑO", style);
				agregarCelda(filaCol, initCol++, "MES", style);
				agregarCelda(filaCol, initCol++, "CEDULA", style);
				agregarCelda(filaCol, initCol++, "IBC", style);
				agregarCelda(filaCol, initCol++, "COTIZACION", style);
				agregarCelda(filaCol, initCol++, "MOTIVO", style);
				
				// listamos contenido
				int total_columnas = initCol;
				for (Map<String, Object> mapa : list_no_validos) {
					// Obtenemos datos de mapa
					crearFila(mapa, 0, // Especifica desde donde empieza la columna
							hoja1.createRow(row++), styleHeader);
				}

				// actualizamos las columnas
				// autosize columnas
				for (int i = 0; i < total_columnas; i++) {
					hoja1.autoSizeColumn((short) i);
				}
				
				File archivo_excel = new File(url_file);
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(archivo_excel);
					libroMetasMatriz.write(fos);
				} finally {
					if (fos != null) {
						try {
							fos.flush();
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				// generamos archivo
				byte[] fileContent = null;
				ByteArrayOutputStream theBAOS = new ByteArrayOutputStream();
				FileInputStream theFIS = null;
				try {
					theBAOS.reset();
					theFIS = new FileInputStream(archivo_excel);
					BufferedInputStream theBIS = new BufferedInputStream(theFIS);
					byte[] buffer = new byte[4096];
					int bytesRead;
					while ((bytesRead = theBIS.read(buffer)) >= 0) {
						theBAOS.write(buffer, 0, bytesRead);
					}
					theBAOS.flush();
					fileContent = theBAOS.toByteArray();
					theBIS.close();
				} finally {
					if (theBAOS != null) {
						theBAOS.reset();
					}
					if (theFIS != null) {
						try {
							theFIS.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				// Descargamos archivo
				Filedownload.save(fileContent, "xls", nombre_archivo);
				archivo_excel.delete();
	}
	
	private int crearFila(Map<String, Object> mapa, int initCol,
			org.apache.poi.ss.usermodel.Row filaCart, CellStyle styleHeader) {
		
		Aportes_cotizaciones aportes_cotizaciones = (Aportes_cotizaciones) mapa.get(PARAM_APORTES);
		String motivo = (String) mapa.get(PARAM_MOTIVO); 
		
		// cargamos datos en el reporte
		agregarCelda(filaCart, initCol++, aportes_cotizaciones.getAnio() + "",
				styleHeader);
		agregarCelda(filaCart, initCol++, aportes_cotizaciones.getMes() + "",
				styleHeader);
		agregarCelda(filaCart, initCol++,
				aportes_cotizaciones.getNro_identificacion() + "", styleHeader);
		
		agregarCelda(filaCart, initCol++,
				aportes_cotizaciones.getIbc() + "", styleHeader);
		
		agregarCelda(filaCart, initCol++,
				aportes_cotizaciones.getValor_cotiza() + "", styleHeader);
		
		agregarCelda(filaCart, initCol++, motivo+ "", styleHeader);

		return initCol;
	}
	
	private void agregarCelda(org.apache.poi.ss.usermodel.Row filaCol,
			int initCol, String nombre, CellStyle styleHeader) {
		org.apache.poi.ss.usermodel.Cell celda = filaCol.createCell(initCol,
				org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
		celda.setCellValue("" + nombre);
		celda.setCellStyle(styleHeader);
	}
	
	private File getDirectorio() {
		File file = new File(Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("")
				+ "/Files");
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(file.getAbsolutePath() + "/Temp");
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(file.getAbsolutePath() + "/ErroresAporte"
				+ getSucursal().getCodigo_empresa() + ""
				+ getSucursal().getCodigo_sucursal());
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}
}
