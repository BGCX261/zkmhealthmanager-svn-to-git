package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;
import com.framework.util.Util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.MensajesUtil;

import contaweb.modelo.service.Detalle_facturaService;

public class Matriz_facturacionAction extends ZKWindow implements
		WindowRegistrosIMG {

	private static final String ADMINISTRADORA = "admins_";
	private static final String CENTRO_SALUD = "centro_salud";
	private static final String SERVICIO = "serv";

	private List<String> lista_seleccionados_eps = new ArrayList<String>();
	private List<String> lista_seleccionados_centro = new ArrayList<String>();
	private List<String> lista_seleccionados_servicios = new ArrayList<String>();

	private List<Map<String, Object>> lista_datos_eps = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> lista_datos_centros_salud = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> lista_datos_servicios = new ArrayList<Map<String, Object>>();

	@View
	private Rows rowEps;
	@View
	private Rows rowCentroSalud;
	@View
	private Rows rowServicios;

	@View
	private Checkbox chkAgruparPorContrato;

	@View
	private Datebox dtbxFechaIncio;
	@View
	private Datebox dtbxFechaFinal;

	@View
	private Checkbox chk_entidad_eps;
	@View
	private Checkbox chk_centro_salud;
	@View
	private Checkbox chk_servicios;

	@Override
	public void init() throws Exception {
	}

	public void limpiar() {
		lista_seleccionados_centro.clear();
		lista_seleccionados_eps.clear();
		lista_seleccionados_eps.clear();

		lista_datos_centros_salud.clear();
		lista_datos_eps.clear();
		lista_datos_servicios.clear();

		rowEps.getChildren().clear();
		rowCentroSalud.getChildren().clear();
		rowServicios.getChildren().clear();
		dtbxFechaFinal.setValue(null);
		dtbxFechaIncio.setValue(null);
	}

	// metodo de generacion de reporte
	public void generarExcel() {
		try {
			if (validarForm()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fecha_inicio", dtbxFechaIncio.getValue());
				map.put("fecha_final", dtbxFechaFinal.getValue());
				map.put("codigo_empresa", codigo_empresa);
				map.put("codigo_sucursal", codigo_sucursal);

				// filtros por contratos o por eps
				if (chkAgruparPorContrato.isChecked()
						&& chk_entidad_eps.isChecked()) {
					map.put("contratos", getContratos());
				} else if (chk_entidad_eps.isChecked()) {
					map.put("eps", getEps());
				}

				// filtros por centro de salud
				if (chk_centro_salud.isChecked()) {
					map.put("centros_salud", getCentrosSalud());
				}

				if (chk_servicios.isChecked()) {
					map.put("servicios", getServicios());
				}

				List<Map> resultado_matriz = getServiceLocator()
						.getReportService().getReport(map, "MatrizModel");
				if (resultado_matriz.isEmpty()) {
					MensajesUtil.mensajeAlerta("Advertencia",
							"La consulta no a generado ningún resultado");
				} else {
					convertirAExcel(resultado_matriz);
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private List<Elemento> getServicios() {
		List<Elemento> elementos = new ArrayList<Elemento>();
		for (Map<String, Object> map_ : lista_datos_servicios) {
			Elemento elm = (Elemento) map_.get(SERVICIO);
			elementos.add(elm);
		}
		return elementos;
	}

	public boolean validarForm() throws Exception {
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";
		dtbxFechaIncio
				.setStyle("text-transform:uppercase;background-color:white");
		dtbxFechaFinal
				.setStyle("text-transform:uppercase;background-color:white");

		if (dtbxFechaIncio.getValue() == null) {
			valida = false;
			dtbxFechaIncio
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		}

		if (dtbxFechaFinal.getValue() == null && valida) {
			valida = false;
			dtbxFechaFinal
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		}

		if (valida && chk_entidad_eps.isChecked()
				&& !chkAgruparPorContrato.isChecked() && getEps().isEmpty()) {
			valida = false;
			msj = "Para filtar las eps debes por lo menos seleccionar una!";
		} else if (valida && chk_entidad_eps.isChecked()
				&& chkAgruparPorContrato.isChecked()
				&& getContratos().isEmpty()) {
			valida = false;
			msj = "Para filtar por contrato debe por lo menos seleccionar uno!";
		}

		if (valida && chk_centro_salud.isChecked()
				&& getCentrosSalud().isEmpty()) {
			valida = false;
			msj = "Para filtar los centros de salud debes por lo menos seleccionar uno!";
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", msj);
		}

		return valida;
	}

	private List<Centro_atencion> getCentrosSalud() {
		List<Centro_atencion> centro_atencions = new ArrayList<Centro_atencion>();
		for (Map<String, Object> map_ : lista_datos_centros_salud) {
			Centro_atencion atencion = (Centro_atencion) map_.get(CENTRO_SALUD);
			centro_atencions.add(atencion);
		}
		return centro_atencions;
	}

	private List<Administradora> getEps() {
		List<Administradora> administradoras = new ArrayList<Administradora>();
		for (Map<String, Object> map_ : lista_datos_eps) {
			Administradora administradora = (Administradora) map_
					.get(ADMINISTRADORA);
			administradoras.add(administradora);
		}
		return administradoras;
	}

	private List<Contratos> getContratos() {
		List<Contratos> contratos = new ArrayList<Contratos>();
		List<Component> components = rowEps.getChildren();
		for (Component component : components) {
			Listbox listbox = (Listbox) component.getAttribute("lbxContrato");
			Set<Listitem> listitems = listbox.getSelectedItems();
			for (Listitem listitem : listitems) {
				contratos.add((Contratos) listitem.getValue());
			}
		}
		return contratos;
	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			//log.info("Registro seleccionado: " + registro);
			if (registro instanceof Administradora) {
				onSeleccionarEps((Administradora) registro);
			} else if (registro instanceof Centro_atencion) {
				onSeleccionaCentro((Centro_atencion) registro);
			} else if (registro instanceof Elemento) {
				onSeleccionarServicio((Elemento) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void onSeleccionarServicio(Elemento registro) {
		adicionarDetalleListaUnidadFuncional(cargarServicio(registro));
	}

	private Map<String, Object> cargarServicio(Elemento unidad_funcional) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(SERVICIO, unidad_funcional);
		return bean;
	}

	private void adicionarDetalleListaUnidadFuncional(Map<String, Object> bean) {
		try {
			crearFilasUnidadFuncional(bean);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasUnidadFuncional(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_servicios.add(bean);
		final Elemento centro_de_costo_via_ingreso = (Elemento) bean
				.get(SERVICIO);

		row.setValue(centro_de_costo_via_ingreso);

		Cell cell = new Cell();
		Label label = new Label(""
				+ centro_de_costo_via_ingreso.getDescripcion().toUpperCase());
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_servicios
															.remove(bean);
													rowServicios
															.removeChild(row);
												}
											}
										});
					}
				});
		rowServicios.appendChild(row);
	}

	private void onSeleccionaCentro(Centro_atencion registro) {
		adicionarDetalleListaCentroSalud(cargarCentroSalud(registro));
	}

	private void onSeleccionarEps(Administradora registro) {
		adicionarDetalleListaAdministradora(cargarPrestador(registro));
	}

	private void adicionarDetalleListaAdministradora(Map<String, Object> bean) {
		try {
			crearFilasAdministradora(bean);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void adicionarDetalleListaCentroSalud(
			Map<String, Object> cargarCentroSalud) {
		try {
			crearFilasCentroSalud(cargarCentroSalud);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private Map<String, Object> cargarCentroSalud(Centro_atencion registro) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(CENTRO_SALUD, registro);
		return bean;
	}

	private Map<String, Object> cargarPrestador(Administradora administradora) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(ADMINISTRADORA, administradora);
		return bean;
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if (registro instanceof Administradora) {
			return celdasListitemAdministradora((Administradora) registro);
		} else if (registro instanceof Centro_atencion) {
			return celdasListitemCentroAtencion((Centro_atencion) registro);
		} else if (registro instanceof Elemento) {
			return celdasListitemUnidadFuncional((Elemento) registro);
		}
		return null;
	}

	private Object[] celdasListitemUnidadFuncional(Elemento registro) {
		return new Object[] { registro.getDescripcion().toUpperCase() };
	}

	private Object[] celdasListitemCentroAtencion(Centro_atencion registro) {
		return new Object[] { registro.getCodigo_centro(),
				registro.getNombre_centro() };
	}

	private Object[] celdasListitemAdministradora(Administradora registro) {
		return new Object[] { registro.getCodigo(), registro.getNombre() };
	}

	public void abrirWindowEps() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("cerrado", false);
		String columnas = "código#65px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro("Eps",
				Paquetes.HEALTHMANAGER, "AdministradoraDao.listar", this,
				columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_eps);
	}

	public void abrirWindowServicios() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", "via_ingreso");
		String columnas = "Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Servicios", Paquetes.HEALTHMANAGER, "ElementoDao.listar",
				this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_servicios);
	}

	public void abrirWindowCentrosSalud() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		String columnas = "código#65px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Centros de Salud", Paquetes.HEALTHMANAGER,
				"Centro_atencionDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_centro);
	}

	// metodos de carga
	private void crearFilasAdministradora(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_eps.add(bean);
		final Administradora administradora = (Administradora) bean
				.get(ADMINISTRADORA);

		// cargamos contratos
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_administradora", administradora.getCodigo());

		List<Contratos> lista_contratos = getServiceLocator()
				.getContratosService().listar(parametros);
		Listbox listboxContratos = getListBox(lista_contratos);
		row.setAttribute("lbxContrato", listboxContratos);

		Detail detail = new Detail();
		detail.appendChild(listboxContratos);
		row.appendChild(detail);

		row.setValue(administradora);

		Cell cell = new Cell();
		Label label = new Label("" + administradora.getCodigo());
		cell.appendChild(label);
		row.appendChild(cell);

		cell = new Cell();
		label = new Label("" + administradora.getNombre());
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_eps
															.remove(bean);
													rowEps.removeChild(row);
												}
											}
										});
					}
				});
		rowEps.appendChild(row);
	}

	private Listbox getListBox(List<Contratos> lista_contratos) {
		Listbox listbox = new Listbox();
		listbox.setCheckmark(true);
		listbox.setMultiple(true);
		listbox.setHflex("1");

		Listhead listhead = new Listhead();
		listbox.appendChild(listhead);

		Listheader listheader = new Listheader("ID contrato");
		listheader.setWidth("150px");
		listhead.appendChild(listheader);

		listheader = new Listheader("código contrato");
		listhead.appendChild(listheader);

		listheader = new Listheader("Nombre contrato");
		listhead.appendChild(listheader);

		for (Contratos contratos : lista_contratos) {
			Listitem listitem = new Listitem();
			listitem.setValue(contratos);

			listitem.setSelected(!contratos.getCerrado());
			listitem.appendChild(new Listcell(contratos.getId_plan()));
			listitem.appendChild(new Listcell(contratos.getNro_contrato()));
			listitem.appendChild(new Listcell(contratos.getNombre()));

			listbox.appendChild(listitem);
		}

		if (listbox.getSelectedCount() > 0) {
			listbox.setSelectedIndex(0);
		}
		return listbox;
	}

	private void crearFilasCentroSalud(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_centros_salud.add(bean);

		final Centro_atencion centro_atencion = (Centro_atencion) bean
				.get(CENTRO_SALUD);
		row.setValue(centro_atencion);

		Cell cell = new Cell();
		Label label = new Label("" + centro_atencion.getCodigo_centro());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + centro_atencion.getNombre_centro());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_centros_salud
															.remove(bean);
													rowCentroSalud
															.removeChild(row);
												}
											}
										});
					}
				});
		rowCentroSalud.appendChild(row);
	}

	// Generacion de archivo excel
	private void convertirAExcel(List<Map> resultado_matriz) throws Exception {
		Workbook libroMetasMatriz = new HSSFWorkbook();

		// fecha archivo
		String fecha_archivo = new SimpleDateFormat("yyyyMMddhhmmss")
				.format(Calendar.getInstance().getTime());

		// Directorio de guardado
		File directorioGuardado = getDirectorio();

		String nombre_archivo = "matriz_" + fecha_archivo + ".xls";
		String url_file = directorioGuardado.getAbsolutePath() + File.separator
				+ nombre_archivo;

//		Detalle_factura detalle_factura = new Detalle_factura();

		// creamos cabecera de libro
		Sheet hoja1 = libroMetasMatriz.createSheet("Matriz");

		// creamos frozen
		hoja1.createFreezePane(4, 6);

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

		// cargamos titulos en plantillas
		int row = 0;
		hoja1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		org.apache.poi.ss.usermodel.Row filaEmpresa = hoja1.createRow(row++);
		org.apache.poi.ss.usermodel.Cell celda = filaEmpresa.createCell(0,
				org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
		celda.setCellValue("MATRIZ");
		celda.setCellStyle(styleNombre_empresa);

		// nombre de la empresa
		hoja1.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));
		filaEmpresa = hoja1.createRow(row++);
		celda = filaEmpresa.createCell(0,
				org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
		celda.setCellValue("" + empresa.getNombre_empresa().toUpperCase());
		celda.setCellStyle(styleNombre_empresa);

		// nit de la empresa
		hoja1.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));
		filaEmpresa = hoja1.createRow(row++);
		celda = filaEmpresa.createCell(0,
				org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
		celda.setCellValue("NIT: " + empresa.getNro_identificacion());
		celda.setCellStyle(styleNombre_empresa);

		// direccion
		String direccion = "";
		Municipios municipios = new Municipios();
		municipios.setCoddep(empresa.getCodigo_dpto());
		municipios.setCodigo(empresa.getCodigo_municipio());
		municipios = getServiceLocator().getMunicipiosService().consultar(
				municipios);
		if (municipios != null) {
			direccion = municipios.getNombre();
		}

		Departamentos departamentos = new Departamentos();
		departamentos.setCodigo(empresa.getCodigo_dpto());
		departamentos = getServiceLocator().getServicio(
				DepartamentosService.class).consultar(departamentos);
		if (departamentos != null) {
			direccion += " " + departamentos.getNombre();
		}

		hoja1.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));
		filaEmpresa = hoja1.createRow(row++);
		celda = filaEmpresa.createCell(0,
				org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
		celda.setCellValue(direccion.toUpperCase());
		celda.setCellStyle(styleNombre_empresa);

		hoja1.addMergedRegion(new CellRangeAddress(4, 4, 0, 4));
		filaEmpresa = hoja1.createRow(row++);
		celda = filaEmpresa.createCell(0,
				org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
		celda.setCellValue("INFORMACIÓN DE FACTURAS GENERADAS");
		celda.setCellStyle(styleNombre_empresa);

		// org.apache.poi.ss.usermodel.Row filaColHeader =
		// hoja1.createRow(row++);
		// int initColHeader = 0;

		// Creamos columnas
		int initCol = 0;
		org.apache.poi.ss.usermodel.Row filaCol = hoja1.createRow(row++);

		// creamos columnas
		agregarCelda(filaCol, initCol++, "NOMBRE DE LA EMPRESA", style);
		agregarCelda(filaCol, initCol++, "DOCUMENTO", style);
		agregarCelda(filaCol, initCol++, "PREFIJO", style);
		agregarCelda(filaCol, initCol++, "NRO. DE FACTURA", style);
		agregarCelda(filaCol, initCol++, "FECHA", style);
		agregarCelda(filaCol, initCol++, "CÓDIGO DEL VEBDEDOR", style);
		agregarCelda(filaCol, initCol++, "VENDEDOR", style);
		agregarCelda(filaCol, initCol++, "BENEFICIARIO", style);
		agregarCelda(filaCol, initCol++, "FORMA DE PAGO", style);
		agregarCelda(filaCol, initCol++, "ANULADOPRODUCTO", style);
		agregarCelda(filaCol, initCol++, "BODEGA", style);
		agregarCelda(filaCol, initCol++, "UNIDAD DE MEDIDA", style);
		agregarCelda(filaCol, initCol++, "CANTIDAD", style);
		agregarCelda(filaCol, initCol++, "VALOR UNITARIO", style);
		agregarCelda(filaCol, initCol++, "IVA", style);
		agregarCelda(filaCol, initCol++, "CENTRO DE COSTO", style);
		agregarCelda(filaCol, initCol++, "VENCIMIENTO", style);
		agregarCelda(filaCol, initCol++, "CANTIDAD CONVERSION", style);
		agregarCelda(filaCol, initCol++, "VALOR CONVERSIÓN", style);

		// listamos contenido
		int total_columnas = initCol;
		for (Map<String, Object> mapa : resultado_matriz) {
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
		String centro_salud = (String) mapa.get("nombre_centro");
		String tipo_identificacion = (String) mapa.get("tipo_identificacion");
		String nro_identificacion = (String) mapa.get("nro_identificacion");
		String apellido1 = (String) mapa.get("apellido1");
		String apellido2 = (String) mapa.get("apellido2");
		String nombre1 = (String) mapa.get("nombre1");
		String nombre2 = (String) mapa.get("nombre2");
		Timestamp fecha_nacimiento = (Timestamp) mapa.get("fecha_nacimiento");

		String edad = "* fecha de nacimiento incorrecta";
		if (fecha_nacimiento.compareTo(Calendar.getInstance().getTime()) <= 0) {
			edad = Util.getEdadPrsentacion(fecha_nacimiento);
		}

		String sexo = (String) mapa.get("sexo");
		String codigo_eps = (String) mapa.get("codigo_eps");
		String nombre_eps = (String) mapa.get("nombre_eps");

		Timestamp fecha_ingreso = (Timestamp) mapa.get("fecha_ingreso");
		Timestamp fecha_atencion = (Timestamp) mapa.get("fecha_atencion");

		Timestamp fecha_atencion_correcta = fecha_atencion != null ? fecha_atencion
				: fecha_ingreso;
		String cita_primera_vez = (String) mapa.get("cita_primera_vez");

		Timestamp fecha_cita = (Timestamp) mapa.get("fecha_cita");
		String estado_cita = (String) mapa.get("estado_cita");
		String reemplazo = (String) mapa.get("reemplazo");

		// cargamos datos en el reporte
		agregarCelda(filaCart, initCol++, centro_salud + "", styleHeader);
		agregarCelda(filaCart, initCol++, tipo_identificacion + "", styleHeader);
		agregarCelda(filaCart, initCol++, nro_identificacion + "", styleHeader);
		agregarCelda(filaCart, initCol++, (apellido1 + " " + apellido2 + " "
				+ nombre1 + " " + nombre2)
				+ "", styleHeader);

		String nro_ingreso = (String) mapa.get("nro_ingreso");

		// Servicios prestados
		String servicios_prestados = "";
		double valor_total = 0d;
		// Consultamos servicios
		Map<String, Object> parametroz = new HashMap<String, Object>();
		parametroz.put("codigo_empresa", codigo_empresa);
		parametroz.put("codigo_sucursal", codigo_sucursal);
		parametroz.put("nro_ingreso", nro_ingreso);
		parametroz.put("codigo_tercero", nro_identificacion);
		List<Map<String, Object>> list_detalle_factura = getServiceLocator()
				.getServicio(Detalle_facturaService.class)
				.getInformacionFactura(parametroz);
		int i = 0;
		for (Map<String, Object> map : list_detalle_factura) {
			String nombre_servicio = (String) map.get("detalle");
			String codigo_servicio = (String) map.get("codigo_articulo");

			BigDecimal valor_total_temp = (BigDecimal) map.get("valor_total");
			if (valor_total_temp != null) {
				valor_total += valor_total_temp.doubleValue();
			}
			servicios_prestados += codigo_servicio + " " + nombre_servicio
					+ ((++i < list_detalle_factura.size()) ? "\r\n" : "");
		}
		String nombre_diagnostico = (String) mapa.get("nombre_diagnostico");
		String codigo_diagnostico = (String) mapa.get("codigo_diagnostico");

		String triage = (String) mapa.get("triage");

		String nro_id_medico = (String) mapa.get("nro_id_medico");
		String nombre_medico = (String) mapa.get("nombre_medico");
		String apellido_medico = (String) mapa.get("apellido_medico");

		String remision_ext = (String) mapa.get("remitido");
		String servico_cual_solicita = (String) mapa
				.get("servico_cual_solicita");
		String via_ingreso = (String) mapa.get("via_ingreso");
		String observacion = (String) mapa.get("observacion");

		Timestamp fecha_ingreso_egreso = (Timestamp) mapa
				.get("fecha_ingreso_egreso");
		Timestamp fecha_egreso = (Timestamp) mapa.get("fecha_egreso");

		String hospitalizacion = (String) mapa.get("hospitalizacion");
		String cama = (String) mapa.get("descripcion_cama");

		Timestamp fecha_ingreso_hosp = (Timestamp) mapa
				.get("fecha_ingreso_hosp");
		Timestamp fecha_egreso_hosp = (Timestamp) mapa.get("fecha_egreso_hosp");

		String estado_salida = (String) mapa.get("estado_salida_descripcion");

		agregarCelda(filaCart, initCol++, edad + "", styleHeader);
		agregarCelda(filaCart, initCol++, sexo + "", styleHeader);
		agregarCelda(filaCart, initCol++, codigo_eps + " " + nombre_eps,
				styleHeader);
		agregarCelda(filaCart, initCol++,
				new SimpleDateFormat("yyyy-MM-dd")
						.format(fecha_atencion_correcta), styleHeader);
		agregarCelda(
				filaCart,
				initCol++,
				new SimpleDateFormat("hh:mm a").format(fecha_atencion_correcta),
				styleHeader);
		agregarCelda(filaCart, initCol++, cita_primera_vez + "", styleHeader);
		agregarCelda(
				filaCart,
				initCol++,
				fecha_cita != null ? new SimpleDateFormat("yyyy-MM-dd")
						.format(fecha_atencion_correcta) : "", styleHeader);
		agregarCelda(
				filaCart,
				initCol++,
				fecha_cita != null ? new SimpleDateFormat("hh:mm a")
						.format(fecha_atencion_correcta) : "", styleHeader);
		agregarCelda(filaCart, initCol++, estado_cita != null ? estado_cita
				: "", styleHeader);
		agregarCelda(filaCart, initCol++, reemplazo + "", styleHeader);
		agregarCelda(filaCart, initCol++, servicios_prestados + "", styleHeader);
		agregarCelda(filaCart, initCol++, codigo_diagnostico + " "
				+ nombre_diagnostico, styleHeader);
		agregarCelda(filaCart, initCol++, "" + triage, styleHeader);
		agregarCelda(filaCart, initCol++,
				(nro_id_medico.equals("000000000") ? "" : (nro_id_medico + " "
						+ nombre_medico + " " + apellido_medico)), styleHeader);
		agregarCelda(filaCart, initCol++,
				new DecimalFormat("$ #,##0.##").format(valor_total),
				styleHeader);
		agregarCelda(filaCart, initCol++, ""
				+ (remision_ext != null ? remision_ext : ""), styleHeader);
		agregarCelda(filaCart, initCol++,
				servico_cual_solicita != null ? servico_cual_solicita : "",
				styleHeader);
		agregarCelda(filaCart, initCol++, "" + via_ingreso, styleHeader);
		agregarCelda(filaCart, initCol++, "" + observacion, styleHeader);
		agregarCelda(
				filaCart,
				initCol++,
				fecha_ingreso_egreso != null ? new SimpleDateFormat("hh:mm a")
						.format(fecha_ingreso_egreso) : "", styleHeader);
		agregarCelda(
				filaCart,
				initCol++,
				fecha_egreso != null ? new SimpleDateFormat("hh:mm a")
						.format(fecha_egreso) : "", styleHeader);
		agregarCelda(filaCart, initCol++, "" + hospitalizacion, styleHeader);
		agregarCelda(filaCart, initCol++, "" + cama, styleHeader);
		agregarCelda(filaCart, initCol++,
				fecha_ingreso_hosp != null ? new SimpleDateFormat(
						"yyyy-MM-dd hh:mm a").format(fecha_ingreso_hosp) : "",
				styleHeader);
		agregarCelda(filaCart, initCol++,
				fecha_egreso_hosp != null ? new SimpleDateFormat(
						"yyyy-MM-dd hh:mm a").format(fecha_egreso_hosp) : "",
				styleHeader);
		agregarCelda(filaCart, initCol++, estado_salida != null ? estado_salida
				: "", styleHeader);

		return initCol;
	}

	/**
	 * Este metodo me permite agregar las columnas
	 * 
	 * */
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

		file = new File(file.getAbsolutePath() + "/Matriz"
				+ empresa.getCodigo_empresa() + ""
				+ sucursal.getCodigo_sucursal());
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}
}
