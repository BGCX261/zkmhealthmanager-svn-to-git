package com.framework.macros.manuales_tarifarios;

import healthmanager.controller.OpenArticuloAction;
import healthmanager.controller.ZKWindow;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Contratos_medicamentos;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Servicios_procedimientos;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Tarifas_procedimientos;
import healthmanager.modelo.service.Maestro_manualService;
import healthmanager.modelo.service.Plantilla_pypService;
import healthmanager.modelo.service.Tarifas_procedimientosService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.macros.Servicios_disponiblesMacro;
import com.framework.macros.manuales_tarifarios.interfaces.IAccionEliminar;
import com.framework.macros.manuales_tarifarios.interfaces.IManualesAction;
import com.framework.macros.manuales_tarifarios.validaciones.Validacion;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;

public class ManualesTarifariosMacro extends Groupbox implements
		ISeleccionarComponente {

	public static Logger log = Logger.getLogger(ManualesTarifariosMacro.class);

	private Grid gridManualGlobal;
	private Label lbManual_tarifario;
	private Listbox lbxManual_tarifario;
	private Label lbAnio;
	private Listbox lbxAnio;
	private Listbox lbxConsultas;
	private Listbox lbxGeneral;
	private Radiogroup groupGeneral;
	private Radiogroup groupConsulta;
	private Radio groupConsulta_01;
	private Radio groupConsulta_02;
	private Radio groupGeneral_01;
	private Radio groupGeneral_02;
	private Listbox lbxMedicamentos;
	private Radiogroup groupMedicamento;
	private Radio groupMedicamento_01;
	private Radio groupMedicamento_02;
	private Listbox lbxServicios;
	private Radiogroup groupServicio;
	private Radio groupServicio_01;
	private Radio groupServicio_02;
	// private Grid grid_adicion_servicios;
	private Groupbox gbxRestriccion;
	private Checkbox chbRestriccion;
	private Grid grid_procedimientos;
	private Rows rows_procedimientos;
	private Groupbox gbxTarifa_especial;
	private Checkbox chbTarifa_especial;
	private Grid grid_procedimientos_esp;
	private Rows rows_procedimientos_esp;

	// Descuento
	private Groupbox gbxDescuento;
	private Checkbox chbDescuento;
	private Grid grid_Descuento;
	private Rows rows_descuento;

	private Sucursal sucursal;
	private Manuales_tarifarios manuales_tarifarios;
	private IManualesAction iManuelesAction;
	private List<Map<String, Object>> lista_datos;
	private List<Map<String, Object>> lista_datos_especial;
	private List<Map<String, Object>> lista_datos_descuentos;

	private ManualTarifarioAdministradorMacro manualTarifarioAdministradorMacro;
	private Row _rowPadreContenedora;

	private IAccionEliminar accionEliminar;

	private Maestro_manual maestro_manual_aux;

	private Caption captionAcciones;

	private List<ContenedorInstancias> list_contenedorInstancias;

	/**
	 * Estas son las unidades funcionales disponibles para esta clasificacion
	 * 
	 * @author Luis Miguel
	 * */

	private Rows rowsContenedorTotal;

	private List<Checkbox> listado_checkbox = new ArrayList<Checkbox>();

	private Servicios_disponiblesMacro servicios_disponiblesMacro;

	public ManualesTarifariosMacro(IManualesAction iManuelesAction,
			Manuales_tarifarios manuales_tarifarios) {
		this.sucursal = iManuelesAction.getSucursal();
		this.iManuelesAction = iManuelesAction;
		this.manuales_tarifarios = manuales_tarifarios;

		this.manuales_tarifarios
				.setLista_procedimiento_especificos(new ArrayList<Map<String, Object>>());
		this.manuales_tarifarios
				.setLista_procedimiento_tarifas_especiales(new ArrayList<Map<String, Object>>());

		lista_datos = this.manuales_tarifarios
				.getLista_procedimiento_especificos();
		lista_datos_especial = this.manuales_tarifarios
				.getLista_procedimiento_tarifas_especiales();
		lista_datos_descuentos = this.manuales_tarifarios.getLista_descuentos();
	}

	private void listarCombos() {
		Utilidades.listarAnios_soat(lbxAnio, sucursal.getCodigo_empresa(),
				sucursal.getCodigo_sucursal(), true,
				ServiciosDisponiblesUtils.getServiceLocator());
		listarServicios();
	}

	public void limpiarDatos() throws Exception {
		Utilidades.seleccionarAnio(lbxAnio);

		gbxRestriccion.setOpen(false);
		gbxTarifa_especial.setOpen(false);
		gbxDescuento.setOpen(false);

		seleccionarTarifa();

		borrarTablaTarifas();
	}

	public Validacion validarForm() throws Exception {
		boolean valida = true;
		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (lbxManual_tarifario.getSelectedIndex() == 0) {
			lbxManual_tarifario
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			Clients.scrollIntoView(lbxManual_tarifario);
			valida = false;
		}

		if (lbxManual_tarifario.getSelectedItem().getValue().equals("SOAT")
				&& lbxAnio.getSelectedIndex() == 0) {
			Clients.scrollIntoView(lbxAnio);
			lbxAnio.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida) {
			actualizarPaginaRestriccion();
			if (chbRestriccion.isChecked() && lista_datos.isEmpty()) {
				mensaje = "Para procedimientos especificos la grilla debe tener al menos un servicio";
				valida = false;
				Clients.scrollIntoView(chbRestriccion);
			}
		}

		if (valida) {
			actualizarPaginaDescuento();
			if (chbDescuento.isChecked() && lista_datos_descuentos.isEmpty()) {
				mensaje = "Para realizar los descuentos por lo menos debe tener un descuento";
				valida = false;
				Clients.scrollIntoView(chbDescuento);
			} else if (chbDescuento.isChecked()) {
				for (int i = 0; i < lista_datos_descuentos.size(); i++) {
					Map bean = lista_datos_descuentos.get(i);
					double valor_descuento = (Double) bean.get("descuento");
					if (valor_descuento <= 0) {
						mensaje = "En la tabla descuentos del contrato, fila "
								+ (i + 1)
								+ " el porcentaje no puede ser menor ni igual a cero";
						valida = false;
						break;
					}
				}
			}
		}

		if (valida) {
			if (chbTarifa_especial.isChecked()
					&& lista_datos_especial.isEmpty()) {
				mensaje = "Para contratos con tarifas especiales la grilla debe tener al menos un servicio con tarifa especial";
				valida = false;
				Clients.scrollIntoView(chbTarifa_especial);
			} else {
				actualizarPaginaEspecial();
				for (int i = 0; i < lista_datos_especial.size(); i++) {
					Map bean = lista_datos_especial.get(i);
					double valor_particular = (Double) bean
							.get("valor_particular");
					if (valor_particular <= 0) {
						mensaje = "Grilla de tarifas especiales, fila "
								+ (i + 1)
								+ " el valor no puede ser menor ni igual a cero";
						i = lista_datos_especial.size();
						valida = false;
					}
				}
			}
		}

		if (valida) {
			if (chbTarifa_especial.isChecked() && chbRestriccion.isChecked()) {
				for (int i = 0; i < lista_datos_especial.size(); i++) {
					Map beanEspecial = lista_datos_especial.get(i);
					String codigo_pcd_especial = (String) beanEspecial
							.get("codigo_procedimiento");
					boolean encontro = false;
					for (int j = 0; j < lista_datos_especial.size(); j++) {
						Map bean = lista_datos_especial.get(j);
						String codigo_pcd = (String) bean
								.get("codigo_procedimiento");
						if (codigo_pcd_especial.equals(codigo_pcd)) {
							encontro = true;
							j = lista_datos_especial.size();
						}
					}
					if (!encontro) {
						mensaje = "Grilla de tarifas especiales, fila "
								+ (i + 1)
								+ " el procedimeinto debe estar en especifico";
						i = lista_datos_especial.size();
						valida = false;
					}
				}
			}

		}
		return new Validacion(valida, mensaje);
	}

	public void seleccionarTarifa() {
		Maestro_manual maestro_manual = (Maestro_manual) lbxManual_tarifario
				.getSelectedItem().getValue();

		if (maestro_manual != null
				&& maestro_manual.getTipo_manual().equals(
						IConstantes.TIPO_MANUAL_SOAT)) {
			lbxAnio.setDisabled(false);
		} else {
			lbxAnio.setDisabled(true);
		}
		maestro_manual_aux = lbxManual_tarifario.getSelectedItem().getValue();
		manuales_tarifarios
				.setId_maestro_manual(maestro_manual_aux != null ? maestro_manual_aux
						.getId_manual() : null);
	}

	/**
	 * Este es el metodo el cual permite inicializar el componete con los
	 * parametros entregados
	 * 
	 * @author Luis Miguel
	 * */
	public void _inicializarPara(
			ManualTarifarioAdministradorMacro manualTarifarioAdministradorMacro2) {
		this.manualTarifarioAdministradorMacro = manualTarifarioAdministradorMacro2;
		inicializarComponente();
		listarCombos();
		cargarDatos();
	}

	/**
	 * Este metodo me permite incializar los datos
	 * 
	 * @author Luis Miguel
	 * */
	private void cargarDatos() {
		try {
			Utilidades.setValueFrom(lbxAnio, manuales_tarifarios.getAnio());

			lbxManual_tarifario.setSelectedIndex(0);
			for (Listitem listitem : lbxManual_tarifario.getItems()) {
				Maestro_manual maestro_manual = (Maestro_manual) listitem
						.getValue();
				if (maestro_manual != null
						&& manuales_tarifarios != null
						&& manuales_tarifarios.getMaestro_manual() != null
						&& maestro_manual.getId_manual().equals(
								manuales_tarifarios.getMaestro_manual()
										.getId_manual())) {
					listitem.setSelected(true);
					break;
				}
			}

			Utilidades.setValueFrom(lbxServicios,
					manuales_tarifarios.getServicios());
			Utilidades.setValueFrom(lbxMedicamentos,
					manuales_tarifarios.getMedicamentos());
			Utilidades.setValueFrom(lbxGeneral,
					manuales_tarifarios.getGeneral());
			Utilidades.setValueFrom(lbxConsultas,
					manuales_tarifarios.getConsulta());
			Utilidades.setValueFrom(groupServicio,
					manuales_tarifarios.getTipo_servicio());
			Utilidades.setValueFrom(groupMedicamento,
					manuales_tarifarios.getTipo_medicamento());
			Utilidades.setValueFrom(groupGeneral,
					manuales_tarifarios.getTipo_general());
			Utilidades.setValueFrom(groupConsulta,
					manuales_tarifarios.getTipo_consulta());
			// dbxValor_mes.setValue(manuales_tarifarios.getValor_mes());

			chbTarifa_especial.setChecked(manuales_tarifarios
					.getTarifa_especial() != null ? manuales_tarifarios
					.getTarifa_especial().equals("S") : false);
			chbRestriccion.setChecked(manuales_tarifarios.getRestriccion());
			chbDescuento
					.setChecked(manuales_tarifarios.getAplica_descuentos() != null ? manuales_tarifarios
							.getAplica_descuentos().equals("S") : false);

			gbxRestriccion.setOpen(manuales_tarifarios.getRestriccion());
			gbxTarifa_especial
					.setOpen(manuales_tarifarios.getTarifa_especial() != null ? manuales_tarifarios
							.getTarifa_especial().equals("S") : false);
			gbxDescuento
					.setOpen(manuales_tarifarios.getAplica_descuentos() != null ? manuales_tarifarios
							.getAplica_descuentos().equals("S") : false);

			lbxAnio.setDisabled(manuales_tarifarios.getMaestro_manual() != null ? !manuales_tarifarios
					.getMaestro_manual().getTipo_manual()
					.equals(IConstantes.TIPO_MANUAL_SOAT)
					: true);

			seleccionarTarifa();
			cargamosValoresModoAutomatico();

			if (manuales_tarifarios.getCodigo_administradora() != null
					&& manuales_tarifarios.getCodigo_empresa() != null
					&& manuales_tarifarios.getCodigo_sucursal() != null) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("codigo_empresa",
						manuales_tarifarios.getCodigo_empresa());
				map.put("codigo_sucursal",
						manuales_tarifarios.getCodigo_sucursal());
				map.put("id_plan", manuales_tarifarios.getId_contrato());
				map.put("codigo_administradora",
						manuales_tarifarios.getCodigo_administradora());

				// Generamos los procedimientos de tarifas especiales //
				borrarTablaTarifas();

				if (manuales_tarifarios.getTarifa_especial().equalsIgnoreCase(
						"S")) {

					List<Tarifas_procedimientos> lista_pcd_especiales = ServiciosDisponiblesUtils
							.getServiceLocator()
							.getServicio(Tarifas_procedimientosService.class)
							.listar(map);
					loadTarifasProcedimientos(lista_pcd_especiales,
							manuales_tarifarios);

				}
			}
			crearFilasEspecial();
			actualizarTitulo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo me permite cargar los valores de los componentes de forma
	 * automatica.
	 * 
	 * @author Luis Miguel
	 * */
	private void cargamosValoresModoAutomatico() {
		Res.inyectarValoresDesdeEventos(lbxAnio, "anio", manuales_tarifarios);

		if (manuales_tarifarios != null) {
			lbxManual_tarifario.addEventListener(Events.ON_SELECT,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							Maestro_manual maestro_manual = (Maestro_manual) lbxManual_tarifario
									.getSelectedItem().getValue();
							if (maestro_manual != null) {
								manuales_tarifarios
										.setId_maestro_manual(maestro_manual
												.getId_manual());
							} else {
								manuales_tarifarios.setId_maestro_manual(null);
							}
						}
					});
		}

		Res.inyectarValoresDesdeEventos(lbxServicios, "servicios",
				manuales_tarifarios);
		Res.inyectarValoresDesdeEventos(lbxMedicamentos, "medicamentos",
				manuales_tarifarios);
		Res.inyectarValoresDesdeEventos(lbxGeneral, "general",
				manuales_tarifarios);
		Res.inyectarValoresDesdeEventos(lbxConsultas, "consulta",
				manuales_tarifarios);
		Res.inyectarValoresDesdeEventos(groupServicio, "tipo_servicio",
				manuales_tarifarios);
		Res.inyectarValoresDesdeEventos(groupMedicamento, "tipo_medicamento",
				manuales_tarifarios);
		Res.inyectarValoresDesdeEventos(groupGeneral, "tipo_general",
				manuales_tarifarios);
		Res.inyectarValoresDesdeEventos(groupConsulta, "tipo_consulta",
				manuales_tarifarios);

		// Res.inyectarValoresDesdeEventos(dbxValor_mes, "valor_mes",
		// manuales_tarifarios);

		Res.inyectarValoresDesdeEventos(chbTarifa_especial, "tarifa_especial",
				manuales_tarifarios);
		Res.inyectarValoresDesdeEventos(chbRestriccion, "restriccion",
				manuales_tarifarios);
	}

	/**
	 * Este metodo te permite cargar las tarifas soat
	 * 
	 * @author ferney
	 * @author Luis Miguel
	 * */
	public void loadTarifasProcedimientos(
			List<Tarifas_procedimientos> lista_pcd_especiales,
			Manuales_tarifarios manuales_tarifarios) throws Exception {
		for (Tarifas_procedimientos tarifas_procedimientos : lista_pcd_especiales) {
			Map<String, Object> beanPcd = Utilidades.getProcedimiento(
					manuales_tarifarios,
					tarifas_procedimientos.getId_procedimiento(),
					ServiciosDisponiblesUtils.getServiceLocator());
			Double valor_pcd = (Double) beanPcd.get("valor_pcd");

			Map<String, Object> pcd = new HashMap<String, Object>();

			String codigo_manual = (String) beanPcd.get("codigo_manual");
			String codigo_cups = (String) beanPcd.get("codigo_cups");
			pcd.put("id_procedimiento",
					tarifas_procedimientos.getId_procedimiento());

			pcd.put("codigo_procedimiento",
					tarifas_procedimientos.getId_procedimiento());
			pcd.put("codigo_cups", codigo_manual != null ? codigo_manual
					: codigo_cups);
			pcd.put("valor_particular", tarifas_procedimientos.getValor());
			pcd.put("valor_procedimiento", valor_pcd);
			pcd.put("nombre_procedimiento",
					(tarifas_procedimientos.getDescripcion().isEmpty()) ? (String) beanPcd
							.get("nombre_procedimiento")
							: tarifas_procedimientos.getDescripcion());

			lista_datos_especial.add(pcd);
		}
	}

	public Manuales_tarifarios getManuales_tarifarios() {
		manuales_tarifarios
				.setListado_servicios_disponibles(servicios_disponiblesMacro
						.getListadoSeleccionado());

		manuales_tarifarios
				.setListado_procedimientos_excluyentes(getListadoProcedimientosExcluyentes());

		manuales_tarifarios
				.setListado_medicamentos_contratados(getListadoMedicamentosContratados());

		return manuales_tarifarios;
	}

	/**
	 * Este metodo es un identificador, el cual me indica de que tipo es dicho
	 * manual tarifario
	 * 
	 * @author Luis Miguel HernÃ¡ndez PÃ©rez
	 * */
	public void actualizarTitulo() {
		Long posicion = this.manualTarifarioAdministradorMacro
				.cualEsMiPosicionEnLista(this);
		Object valor = lbxManual_tarifario.getSelectedItem().getValue();
		manuales_tarifarios.setConsecutivo(posicion);
		captionAcciones.setLabel("MANUAL TARIFARIO "
				+ (valor != null ? valor.toString() : "") + "  NRO: "
				+ posicion);
		servicios_disponiblesMacro.setManual((valor != null ? valor.toString()
				: ""));
		servicios_disponiblesMacro.setAnio(lbxAnio.getSelectedItem().getValue()
				.toString());
	}

	public void setServiciosContratados(
			List<Servicios_contratados> listado_servicios_contratados) {
		servicios_disponiblesMacro
				.mostrarDatosArbol(listado_servicios_contratados);
	}

	/**
	 * Este metodo te permite agregar a la lista las unidades funcionales
	 * seleccionadas
	 * 
	 * @author Luis Miguel
	 * @param contenedorInstanciasPadre
	 * */
	public void accionMultiple(ContenedorInstancias contenedorInstancias,
			ContenedorInstancias contenedorInstanciasPadre) {
		if (list_contenedorInstancias == null)
			list_contenedorInstancias = new ArrayList<ManualesTarifariosMacro.ContenedorInstancias>();
		list_contenedorInstancias.add(contenedorInstancias);

		if (contenedorInstanciasPadre != null) {
			if (contenedorInstanciasPadre.contenedorInstancias == null)
				contenedorInstanciasPadre.contenedorInstancias = new ArrayList<ManualesTarifariosMacro.ContenedorInstancias>();
			contenedorInstanciasPadre.contenedorInstancias
					.add(contenedorInstancias);
		}
	}

	/**
	 * Este metodo es el encargado de inicializar el componente, es decir es el
	 * encargado, mostrar los compoenetes en la vista.
	 * 
	 * @author Luis Miguel
	 * */
	private void inicializarComponente() {
		setWidth("100%");
		// setContentStyle("border:0");
		setMold("3d");
		setOpen(false);

		/* accion eliminar */
		captionAcciones = new Caption();
		captionAcciones.setTooltiptext("Preseione para cerrar/abrir");

		Space spaceSeparador = new Space();
		spaceSeparador.setBar(true);
		spaceSeparador.setOrient("vertical");
		captionAcciones.appendChild(spaceSeparador);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						if (accionEliminar != null) {
							Messagebox
									.show("Estas seguro que deseas eliminar este manual tarifario? ",
											"Advertencia",
											Messagebox.YES + Messagebox.NO,
											Messagebox.QUESTION,
											new org.zkoss.zk.ui.event.EventListener<Event>() {
												public void onEvent(Event event)
														throws Exception {
													if ("onYes".equals(event
															.getName())) {
														//log.info("ACCION ELIMINAR");
														accionEliminar
																.onEliminar(ManualesTarifariosMacro.this);
													}
												}
											});
						} else {
							log.warn("La interface de evento accionEliminar es nulo, esto afecta que no se pueda eliminar un manual de la pila.");
						}
					}
				});
		captionAcciones.appendChild(toolbarbutton);
		appendChild(captionAcciones);
		/* fin accion eliminar */

		gridManualGlobal = new Grid();
		appendChild(gridManualGlobal);

		Columns columns = new Columns();
		columns.setVisible(false);
		gridManualGlobal.appendChild(columns);

		Column column = new Column();
		column.setAlign("left");
		columns.appendChild(column);

		Rows rows = new Rows();
		gridManualGlobal.appendChild(rows);

		Row row = new Row();
		rows.appendChild(row);

		Cell cell = new Cell();
		row.appendChild(cell);

		Div div_informacion = new Div();
		cell.appendChild(div_informacion);

		Grid grid = new Grid();
		div_informacion.appendChild(grid);

		Columns columns_2 = new Columns();
		columns_2.setVisible(false);
		grid.appendChild(columns_2);

		Column column_2 = new Column();
		column_2.setWidth("120px");
		column_2.setAlign("left");
		columns_2.appendChild(column_2);

		Column column_3 = new Column();
		column_3.setAlign("left");
		column_3.setWidth("200px");
		columns_2.appendChild(column_3);

		Column column_4 = new Column();
		column_4.setWidth("100px");
		column_4.setAlign("left");
		columns_2.appendChild(column_4);

		Column column_5 = new Column();
		column_5.setAlign("left");
		columns_2.appendChild(column_5);

		Rows rows_2 = new Rows();
		grid.appendChild(rows_2);

		Row row_2 = new Row();
		rows_2.appendChild(row_2);

		Cell cell_2 = new Cell();
		row_2.appendChild(cell_2);

		lbManual_tarifario = new Label();
		lbManual_tarifario.setValue("Manual tarifario: ");
		cell_2.appendChild(lbManual_tarifario);

		Cell cell_3 = new Cell();
		row_2.appendChild(cell_3);

		lbxManual_tarifario = new Listbox();
		lbxManual_tarifario.setMold("select");
		lbxManual_tarifario.setSclass("combobox");
		lbxManual_tarifario.setWidth("170px");
		lbxManual_tarifario.getChildren().clear();
		lbxManual_tarifario.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						verificarPlan();
						actualizarTitulo();
					}
				});

		Listitem listitem = new Listitem();
		listitem.setLabel("-- Seleccione --");
		listitem.setValue(null);
		listitem.setSelected(true);
		lbxManual_tarifario.appendChild(listitem);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", sucursal.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		List<Maestro_manual> listado_manuales = ServiciosDisponiblesUtils
				.getServiceLocator().getServicio(Maestro_manualService.class)
				.listar(parametros);

		for (Maestro_manual maestro_manual : listado_manuales) {
			listitem = new Listitem();
			listitem.setLabel(maestro_manual.getManual());
			listitem.setValue(maestro_manual);
			listitem.setSelected(true);
			lbxManual_tarifario.appendChild(listitem);
		}

		if (lbxManual_tarifario.getItemCount() > 0) {
			lbxManual_tarifario.setSelectedIndex(0);
		}
		cell_3.appendChild(lbxManual_tarifario);

		Cell cell_4 = new Cell();
		row_2.appendChild(cell_4);

		lbAnio = new Label();
		lbAnio.setValue("AÃ±o Salario: ");
		cell_4.appendChild(lbAnio);

		Cell cell_5 = new Cell();
		row_2.appendChild(cell_5);

		lbxAnio = new Listbox();
		lbxAnio.setMold("select");
		lbxAnio.setSclass("combobox");
		lbxAnio.setWidth("170px");
		cell_5.appendChild(lbxAnio);

		lbxAnio.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				actualizarTitulo();
			}
		});

		Utilidades.listarAnios_soat(lbxAnio, sucursal.getCodigo_empresa(),
				sucursal.getCodigo_sucursal(), true,
				ServiciosDisponiblesUtils.getServiceLocator());

		Row row_3 = new Row();
		rows_2.appendChild(row_3);

		Cell cell_6 = new Cell();
		cell_6.setColspan(4);
		row_3.appendChild(cell_6);

		Groupbox groupbox_2 = new Groupbox();
		groupbox_2.setMold("3d");
		groupbox_2.setClosable(false);
		cell_6.appendChild(groupbox_2);

		Caption caption_2 = new Caption();
		caption_2.setLabel("INCREMENTO Y/O DESCUENTOS SOBRES LOS SERVICIOS");
		groupbox_2.appendChild(caption_2);

		Vlayout vlayout = new Vlayout();
		groupbox_2.appendChild(vlayout);

		Hlayout hlayout_c = new Hlayout();
		vlayout.appendChild(hlayout_c);

		Label label_c = new Label();
		label_c.setValue("Consultas: ");
		hlayout_c.appendChild(label_c);

		Space space_c = new Space();
		space_c.setWidth("35px");
		hlayout_c.appendChild(space_c);

		lbxConsultas = new Listbox();
		lbxConsultas.setName("Servicios: ");
		lbxConsultas.setMold("select");
		lbxConsultas.setWidth("100px");
		lbxConsultas.setSclass("combobox");
		hlayout_c.appendChild(lbxConsultas);

		Label label_2c = new Label();
		label_2c.setValue("% ");
		hlayout_c.appendChild(label_2c);

		Space space_2c = new Space();
		hlayout_c.appendChild(space_2c);

		groupConsulta = new Radiogroup();
		hlayout_c.appendChild(groupConsulta);

		groupConsulta_01 = new Radio();
		groupConsulta_01.setValue("01");
		groupConsulta_01.setLabel("Menos");
		groupConsulta_01.setSelected(true);
		groupConsulta.appendChild(groupConsulta_01);

		Space space_3c = new Space();
		space_3c.setWidth("25px");
		groupConsulta.appendChild(space_3c);

		groupConsulta_02 = new Radio();
		groupConsulta_02.setValue("02");
		groupConsulta_02.setLabel("Mas");
		groupConsulta.appendChild(groupConsulta_02);

		Hlayout hlayout = new Hlayout();
		vlayout.appendChild(hlayout);

		Label label = new Label();
		label.setValue("Procedimientos: ");
		hlayout.appendChild(label);

		Space space = new Space();
		space.setWidth("5px");
		hlayout.appendChild(space);

		lbxGeneral = new Listbox();
		lbxGeneral.setName("Servicios: ");
		lbxGeneral.setMold("select");
		lbxGeneral.setWidth("100px");
		lbxGeneral.setSclass("combobox");
		hlayout.appendChild(lbxGeneral);

		Label label_2 = new Label();
		label_2.setValue("% ");
		hlayout.appendChild(label_2);

		Space space_2 = new Space();
		hlayout.appendChild(space_2);

		groupGeneral = new Radiogroup();
		hlayout.appendChild(groupGeneral);

		groupGeneral_01 = new Radio();
		groupGeneral_01.setValue("01");
		groupGeneral_01.setLabel("Menos");
		groupGeneral_01.setSelected(true);
		groupGeneral.appendChild(groupGeneral_01);

		Space space_3 = new Space();
		space_3.setWidth("25px");
		groupGeneral.appendChild(space_3);

		groupGeneral_02 = new Radio();
		groupGeneral_02.setValue("02");
		groupGeneral_02.setLabel("Mas");
		groupGeneral.appendChild(groupGeneral_02);

		Hlayout hlayout_2 = new Hlayout();
		vlayout.appendChild(hlayout_2);

		Label label_3 = new Label();
		label_3.setValue("Medicamentos: ");
		hlayout_2.appendChild(label_3);

		Space space_4 = new Space();
		space_4.setWidth("10px");
		hlayout_2.appendChild(space_4);

		lbxMedicamentos = new Listbox();
		lbxMedicamentos.setName("servicios");
		lbxMedicamentos.setMold("select");
		lbxMedicamentos.setWidth("100px");
		lbxMedicamentos.setSclass("combobox");
		hlayout_2.appendChild(lbxMedicamentos);

		Label label_4 = new Label();
		label_4.setValue("% ");
		hlayout_2.appendChild(label_4);

		Space space_5 = new Space();
		hlayout_2.appendChild(space_5);

		groupMedicamento = new Radiogroup();
		hlayout_2.appendChild(groupMedicamento);

		groupMedicamento_01 = new Radio();
		groupMedicamento_01.setValue("01");
		groupMedicamento_01.setLabel("Menos");
		groupMedicamento_01.setSelected(true);
		groupMedicamento.appendChild(groupMedicamento_01);

		Space space_6 = new Space();
		space_6.setWidth("25px");
		groupMedicamento.appendChild(space_6);

		groupMedicamento_02 = new Radio();
		groupMedicamento_02.setValue("02");
		groupMedicamento_02.setLabel("Mas");
		groupMedicamento.appendChild(groupMedicamento_02);

		Hlayout hlayout_3 = new Hlayout();
		vlayout.appendChild(hlayout_3);

		Label label_5 = new Label();
		label_5.setValue("Otros servicios: ");
		hlayout_3.appendChild(label_5);

		Space space_7 = new Space();
		space_7.setWidth("9px");
		hlayout_3.appendChild(space_7);

		lbxServicios = new Listbox();
		lbxServicios.setName("servicios");
		lbxServicios.setMold("select");
		lbxServicios.setWidth("100px");
		lbxServicios.setSclass("combobox");
		hlayout_3.appendChild(lbxServicios);

		Label label_6 = new Label();
		label_6.setValue("% ");
		hlayout_3.appendChild(label_6);

		Space space_8 = new Space();
		hlayout_3.appendChild(space_8);

		groupServicio = new Radiogroup();
		hlayout_3.appendChild(groupServicio);

		groupServicio_01 = new Radio();
		groupServicio_01.setValue("01");
		groupServicio_01.setLabel("Menos");
		groupServicio_01.setSelected(true);
		groupServicio.appendChild(groupServicio_01);

		Space space_9 = new Space();
		space_9.setWidth("25px");
		groupServicio.appendChild(space_9);

		groupServicio_02 = new Radio();
		groupServicio_02.setValue("02");
		groupServicio_02.setLabel("Mas");
		groupServicio.appendChild(groupServicio_02);

		Row row_4 = new Row();
		row_4.setStyle("background-color:white");
		rows.appendChild(row_4);

		Cell cell_7 = new Cell();
		row_4.appendChild(cell_7);

		Row row_5 = new Row();
		rows.appendChild(row_5);

		Cell cell_8 = new Cell();
		row_5.appendChild(cell_8);

		Groupbox groupbox_3 = new Groupbox();
		groupbox_3.setMold("3d");
		groupbox_3.setClosable(false);
		cell_8.appendChild(groupbox_3);

		Caption caption_3 = new Caption();
		caption_3.setLabel("Ãreas de Servicios");
		groupbox_3.appendChild(caption_3);

		rowsContenedorTotal = new Rows();

		servicios_disponiblesMacro = (Servicios_disponiblesMacro) Executions
				.createComponents("/WEB-INF/macros/servicios_disponibles.zul",
						this, new HashMap<String, Object>());

		servicios_disponiblesMacro.inicializar(iManuelesAction.getZkWindow());

		Div div_servicios = new Div();
		div_servicios.appendChild(servicios_disponiblesMacro);

		actualizarTitulo();

		groupbox_3.appendChild(div_servicios);

		Row row_8 = new Row();
		row_8.setStyle("background-color:white");
		rows.appendChild(row_8);

		Cell cell_14 = new Cell();
		row_8.appendChild(cell_14);

		Row row_9 = new Row();
		rows.appendChild(row_9);

		Cell cell_15 = new Cell();
		row_9.appendChild(cell_15);

		Groupbox groupbox_4 = new Groupbox();
		groupbox_4.setMold("3d");
		groupbox_4.setClosable(false);
		cell_15.appendChild(groupbox_4);

		Caption caption_4 = new Caption();
		caption_4.setLabel("Paramentros del contrato");
		groupbox_4.appendChild(caption_4);

		Grid grid_3 = new Grid();
		grid_3.setSclass("GridSinBorde");
		groupbox_4.appendChild(grid_3);

		Columns columns_5 = new Columns();
		columns_5.setVisible(false);
		grid_3.appendChild(columns_5);

		Column column_12 = new Column();
		column_12.setWidth("120px");
		column_12.setAlign("left");
		columns_5.appendChild(column_12);

		Column column_13 = new Column();
		column_13.setAlign("left");
		column_13.setWidth("200px");
		columns_5.appendChild(column_13);

		Column column_14 = new Column();
		column_14.setWidth("100px");
		column_14.setAlign("left");
		columns_5.appendChild(column_14);

		Column column_15 = new Column();
		column_15.setAlign("left");
		columns_5.appendChild(column_15);

		Rows rows_5 = new Rows();
		grid_3.appendChild(rows_5);

		Row row_11 = new Row();
		rows_5.appendChild(row_11);

		Cell cell_20 = new Cell();
		cell_20.setColspan(4);
		row_11.appendChild(cell_20);

		gbxRestriccion = new Groupbox();
		gbxRestriccion.setClosable(true);
		gbxRestriccion.addEventListener(Events.ON_OPEN,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event evt) throws Exception {
						chbRestriccion.setChecked(((Groupbox) evt.getTarget())
								.isOpen());
					}
				});
		gbxRestriccion.setOpen(false);
		cell_20.appendChild(gbxRestriccion);

		Caption caption_5 = new Caption();
		gbxRestriccion.appendChild(caption_5);

		chbRestriccion = new Checkbox();
		chbRestriccion.setLabel("Procedimientos especificos");
		caption_5.appendChild(chbRestriccion);

		grid_procedimientos = new Grid();
		grid_procedimientos.setHeight("200px");
		gbxRestriccion.appendChild(grid_procedimientos);

		Auxhead auxhead = new Auxhead();
		grid_procedimientos.appendChild(auxhead);

		Auxheader auxheader = new Auxheader();
		auxheader.setAlign("left");
		auxheader.setColspan(5);
		auxhead.appendChild(auxheader);

		Button button = new Button();
		button.setLabel("Agregar Procedimientos");
		button.setMold("trendy");
		button.setImage("/images/add.png");
		button.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws Exception {
				openPcd("1");
			}
		});
		auxheader.appendChild(button);

		Columns columns_6 = new Columns();
		grid_procedimientos.appendChild(columns_6);

		Column column_16 = new Column();
		column_16.setLabel("codigo");
		column_16.setWidth("100px");
		columns_6.appendChild(column_16);

		Column column_17 = new Column();
		column_17.setLabel("Cups");
		column_17.setWidth("100px");
		columns_6.appendChild(column_17);

		Column column_18 = new Column();
		column_18.setLabel("Descripcion");
		columns_6.appendChild(column_18);

		Column column_19 = new Column();
		column_19.setLabel("Requ Aut.");
		column_19.setWidth("80px");
		columns_6.appendChild(column_19);

		Column column_20 = new Column();
		column_20.setLabel("Acciones");
		column_20.setWidth("80px");
		columns_6.appendChild(column_20);

		rows_procedimientos = new Rows();
		grid_procedimientos.appendChild(rows_procedimientos);
		/**
		 * Desde aqui finalizan las restricciones
		 **/

		/**
		 * Desde aqui empiezan las tarifas especiales
		 **/
		Row row_12 = new Row();
		rows_5.appendChild(row_12);

		Cell cell_21 = new Cell();
		cell_21.setColspan(4);
		row_12.appendChild(cell_21);

		gbxTarifa_especial = new Groupbox();
		gbxTarifa_especial.setClosable(true);
		gbxTarifa_especial.addEventListener(Events.ON_OPEN,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event evt) throws Exception {
						chbTarifa_especial.setChecked(((Groupbox) evt
								.getTarget()).isOpen());
					}
				});
		gbxTarifa_especial.setOpen(false);
		cell_21.appendChild(gbxTarifa_especial);

		Caption caption_6 = new Caption();
		gbxTarifa_especial.appendChild(caption_6);

		chbTarifa_especial = new Checkbox();
		chbTarifa_especial.setLabel("Maneja tarifas especiales");
		caption_6.appendChild(chbTarifa_especial);

		grid_procedimientos_esp = new Grid();
		grid_procedimientos_esp.setHeight("300px");
		gbxTarifa_especial.appendChild(grid_procedimientos_esp);

		Auxhead auxhead_2 = new Auxhead();
		grid_procedimientos_esp.appendChild(auxhead_2);

		Auxheader auxheader_2 = new Auxheader();
		auxheader_2.setAlign("left");
		auxheader_2.setColspan(5);
		auxhead_2.appendChild(auxheader_2);

		Button button_2 = new Button();
		button_2.setLabel("Agregar Procedimientos tarifa");
		button_2.setMold("trendy");
		button_2.setImage("/images/add.png");
		button_2.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws Exception {
				openPcd("2");
			}
		});
		auxheader_2.appendChild(button_2);

		Columns columns_7 = new Columns();
		grid_procedimientos_esp.appendChild(columns_7);

		Column column_21 = new Column();
		column_21.setLabel("codigo");
		column_21.setWidth("100px");
		columns_7.appendChild(column_21);

		Column column_22 = new Column();
		column_22.setLabel("Descripcion");
		columns_7.appendChild(column_22);

		Column column_23 = new Column();
		column_23.setLabel("Valor manual");
		column_23.setWidth("100px");
		columns_7.appendChild(column_23);

		Column column_24 = new Column();
		column_24.setLabel("Valor personal");
		column_24.setWidth("100px");
		columns_7.appendChild(column_24);

		Column column_25 = new Column();
		column_25.setLabel("Acciones");
		column_25.setWidth("80px");
		columns_7.appendChild(column_25);

		rows_procedimientos_esp = new Rows();
		grid_procedimientos_esp.appendChild(rows_procedimientos_esp);
		/**
		 * Desde aqui finaliza las tarifas especiales
		 **/

		/**
		 * Tabla de descuentos
		 * */
		Row row_descuentos = new Row();
		rows_5.appendChild(row_descuentos);

		Cell cell_descuentos = new Cell();
		cell_descuentos.setColspan(4);
		row_descuentos.appendChild(cell_descuentos);

		gbxDescuento = new Groupbox();
		gbxDescuento.setClosable(true);
		gbxDescuento.addEventListener(Events.ON_OPEN,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event evt) throws Exception {
						chbDescuento.setChecked(((Groupbox) evt.getTarget())
								.isOpen());
						manuales_tarifarios.setAplica_descuentos(gbxDescuento
								.isOpen() ? "S" : "N");
					}
				});
		// Cargamos por defecto el valor N, para que no aplique descuentos
		manuales_tarifarios.setAplica_descuentos("N");

		gbxDescuento.setOpen(false);
		cell_descuentos.appendChild(gbxDescuento);

		Caption caption_desceunto = new Caption();
		gbxDescuento.appendChild(caption_desceunto);

		chbDescuento = new Checkbox();
		chbDescuento.setLabel("Descuentos por solo toma de muestra");
		caption_desceunto.appendChild(chbDescuento);

		grid_Descuento = new Grid();
		grid_Descuento.setHeight("200px");
		gbxDescuento.appendChild(grid_Descuento);

		Auxhead auxhead_descuento = new Auxhead();
		grid_Descuento.appendChild(auxhead_descuento);

		Auxheader auxheader_descuento = new Auxheader();
		auxheader_descuento.setAlign("left");
		auxheader_descuento.setColspan(5);
		auxhead_descuento.appendChild(auxheader_descuento);

		Button button_descuento = new Button();
		button_descuento.setLabel("Agregar Procedimientos tarifa");
		button_descuento.setMold("trendy");
		button_descuento.setImage("/images/add.png");
		button_descuento.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event evt) throws Exception {
						openPcd("3");
					}
				});
		auxheader_descuento.appendChild(button_descuento);

		Columns columns_descuento = new Columns();
		grid_Descuento.appendChild(columns_descuento);

		Column column_descuento = new Column();
		column_descuento.setLabel("codigo");
		column_descuento.setWidth("80px");
		columns_descuento.appendChild(column_descuento);

		column_descuento = new Column();
		column_descuento.setLabel("Descripcion");
		columns_descuento.appendChild(column_descuento);

		column_descuento = new Column();
		column_descuento.setLabel("Valor manual");
		column_descuento.setWidth("100px");
		columns_descuento.appendChild(column_descuento);

		column_descuento = new Column();
		column_descuento.setLabel("Descuento(%)");
		column_descuento.setWidth("100px");
		columns_descuento.appendChild(column_descuento);

		column_descuento = new Column();
		column_descuento.setLabel("Valor/Descuento");
		column_descuento.setWidth("110px");
		columns_descuento.appendChild(column_descuento);

		column_descuento = new Column();
		column_descuento.setLabel("");
		column_descuento.setWidth("30px");
		columns_descuento.appendChild(column_descuento);

		rows_descuento = new Rows();
		grid_Descuento.appendChild(rows_descuento);

		/**
		 * Fin de descuentos
		 * */

		Row row_13 = new Row();
		row_13.setStyle("background-color:white");
		rows.appendChild(row_13);

		Cell cell_22 = new Cell();
		row_13.appendChild(cell_22);
	}

	protected void eliminarUnidadesFuncionales() {
		Messagebox.show("Esta seguro que desea eliminar este registro? ",
				"Eliminar Registro", Messagebox.YES + Messagebox.NO,
				Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						try {
							if ("onYes".equals(event.getName())) {
								List<Row> listitems = getListadoSeleccionados(
										rowsContenedorTotal, 2);
								if (listitems.isEmpty()) {
									throw new ValidacionRunTimeException(
											"Para realizar esta accion debe seleccionar al menos un servicio");
								} else {
									rowsContenedorTotal
											.getChildren()
											.removeAll(
													getListadoSeleccionados(
															rowsContenedorTotal,
															2));

								}
								Notificaciones
										.mostrarNotificacionInformacion(
												"Informacion",
												"Servicios eliminados satisfactoriamente ",
												3000);
							}
						} catch (ValidacionRunTimeException e) {
							MensajesUtil.mensajeAlerta("Advertencia",
									e.getMessage());
						} catch (Exception e) {
							MensajesUtil.mensajeAlerta("Advertencia",
									e.getMessage());
						}
					}
				});
	}

	private List<String> seleccionados_medicamentos = new ArrayList<String>();

	private Listbox listboxMedicamentosCurrent;

	public void openMedicamentos() throws Exception {
		String pages = "/pages/openArticulo.zul";

		Map<String,Object> parametros = new HashMap<String,Object>();
		parametros.put("codigo_empresa", sucursal.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		parametros.put("seleccionados", seleccionados_medicamentos);

		List<String> area_intervencion_in = new ArrayList<String>();
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_AGUDEZA_VISUAL);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_ADULTO_MAYOR);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_CRECIMIENTO_DESARROLLO);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_DESARROLLO_JOVEN);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_RECIEN_NACIDO);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_ATENCION_PARTO);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_SALUD_ORAL);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_CUELLO_UTERINO);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_ALTERACION_EMBARAZO);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_CANCER_SENO);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_PLANIFICACION_HOMBRES);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_PLANIFICACION_MUJERES);
		area_intervencion_in
				.add(ServiciosDisponiblesUtils.CODSER_PYP_VACUNACION_PAI);

		Map<String, Object> parametros_areas = new HashMap<String, Object>();
		parametros_areas.put("area_intervencion_in", area_intervencion_in);

		List<Plantilla_pyp> plantilla_pyps = ServiciosDisponiblesUtils
				.getServiceLocator().getServicio(Plantilla_pypService.class)
				.listar(parametros_areas);

		List<String> codigos_actividades = new ArrayList<String>();
		for (Plantilla_pyp plantilla_pyp : plantilla_pyps) {
			List<String> codigos = Utilidades.validarCodigo(plantilla_pyp
					.getCodigo_pdc());
			for (String codig : codigos) {
				codigos_actividades.add(codig);
			}
		}

		parametros.put("codigos_actividades", codigos_actividades);

		OpenArticuloAction componente = (OpenArticuloAction) Executions
				.createComponents(pages, null, parametros);

		componente.setSeleccionar_componente(this);

		componente.setWidth("990px");
		componente.setHeight("400px");
		componente.setClosable(true);
		componente.setMaximizable(true);
		componente.setTitle("CONSULTAR MEDICAMENTOS E INSUMOS DE PYP");
		componente.setMode("modal");

	}

	public void quitarMedicamentos() {
		if (listboxMedicamentosCurrent != null) {
			for (Listitem listitem : listboxMedicamentosCurrent
					.getSelectedItems()) {
				listitem.detach();
			}
		}
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		String codigo_articulo = (String) pcd.get("codigo_articulo");

		seleccionados_medicamentos.add(codigo_articulo);

		String nombre_articulo = "";

		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(sucursal.getCodigo_empresa());
		articulo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		articulo.setCodigo_articulo(codigo_articulo);
		articulo = ServiciosDisponiblesUtils.getServiceLocator()
				.getArticuloService().consultar(articulo);
		nombre_articulo = (articulo != null ? articulo.getNombre1() : "");

		final Listitem listitem = new Listitem();
		listitem.setValue(articulo);
		listitem.appendChild(new Listcell());
		listitem.appendChild(new Listcell(codigo_articulo));
		listitem.appendChild(new Listcell(nombre_articulo));
		Listcell listcell = new Listcell();
		Checkbox checkbox = new Checkbox();
		listcell.appendChild(checkbox);
		listitem.appendChild(listcell);
		listboxMedicamentosCurrent.appendChild(listitem);
	}

	/**
	 * Este metodo es el que se encarga de aplicar la seleccion a cada uno de
	 * los checkbox
	 * 
	 * @author Luis Miguel
	 * */
	protected void aplicarEstadoSeleccionAItems(boolean checked) {
		if (list_contenedorInstancias != null) {
			for (ContenedorInstancias contenedorInstancias : list_contenedorInstancias) {
				contenedorInstancias.checkbox.setChecked(checked);
			}
		}
	}

	/*
	 * ********************* METODOS DE ADICION O ACCION
	 * **************************
	 */

	private void listarServicios() {
		for (int i = 0; i <= 100; i++) {
			Listitem listitem = new Listitem();
			listitem.setValue(new Double(i));
			listitem.setLabel("" + i);
			lbxGeneral.appendChild(listitem);
			listitem = new Listitem();
			listitem.setValue(new Double(i));
			listitem.setLabel("" + i);
			lbxConsultas.appendChild(listitem);
			listitem = new Listitem();
			listitem.setValue(new Double(i));
			listitem.setLabel("" + i);
			lbxMedicamentos.appendChild(listitem);
			listitem = new Listitem();
			listitem.setValue(new Double(i));
			listitem.setLabel("" + i);
			lbxServicios.appendChild(listitem);
		}

		if (lbxServicios.getItemCount() > 0) {
			lbxServicios.setSelectedIndex(0);
		}

		if (lbxMedicamentos.getItemCount() > 0) {
			lbxMedicamentos.setSelectedIndex(0);
		}

		if (lbxGeneral.getItemCount() > 0) {
			lbxGeneral.setSelectedIndex(0);
		}

		if (lbxConsultas.getItemCount() > 0) {
			lbxConsultas.setSelectedIndex(0);
		}
	}

	/**
	 * Este metodo te permite vizualizar los compoenetes
	 * 
	 * @author Luis Miguel
	 * */
	public void openPcd(String tipo) throws Exception {
		String nivel = iManuelesAction.getNivel();
		if (nivel.trim().isEmpty()) {
			MensajesUtil.mensajeAlerta("Alerta !!",
					"Debe seleccionar el nivel del contrato");
		} else {
			if (lbxManual_tarifario.getSelectedIndex() == 0) {
				MensajesUtil.mensajeAlerta("Alerta !!",
						"Debe seleccionar el manual tarifario");
			} else {
				String pages = "";
				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(iManuelesAction.getEmpresa()
						.getCodigo_empresa());
				contratos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				contratos.setCodigo_administradora(iManuelesAction
						.getCodigoAdministradora());
				contratos.setId_plan(iManuelesAction.getIdContrato());
				Maestro_manual maestro_manual = (Maestro_manual) lbxManual_tarifario
						.getSelectedItem().getValue();
				String anio = lbxAnio.getSelectedItem().getValue().toString();

				pages = "/pages/openProcedimientos.zul";

				Map<String,Object> parametros = new HashMap<String,Object>();
				parametros.put("codigo_empresa", iManuelesAction.getEmpresa()
						.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				parametros.put("codigo_administradora",
						iManuelesAction.getCodigoAdministradora());
				parametros.put("id_plan", iManuelesAction.getIdContrato());
				parametros.put("anio", anio);
				parametros.put("contratos", "");
				parametros.put("tipo", tipo);
				parametros.put("nivel_orden", nivel);
				parametros.put("ocultar_filtro_procedimiento",
						"ocultar_filtro_procedimiento");
				parametros.put("maestro_manual", maestro_manual);

				// Filtramos que solo salgan los laboratorios
				if (tipo.equals("3")) {
					parametros.put("codigo_tipo_procedimiento_in",
							new String[] {
									ServiciosDisponiblesUtils.TIPO_CITOLOGIA,
									ServiciosDisponiblesUtils.TIPO_RAYOS_X });
				}

				Component componente = Executions.createComponents(pages,
						(Window) iManuelesAction, parametros);
				final Window ventana = (Window) componente;
				ventana.setWidth("990px");
				ventana.setHeight("400px");
				ventana.setClosable(true);
				ventana.setMaximizable(true);
				ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
				ventana.setMode("modal");

				try {
					Method methodSeleccionar_componente = componente.getClass()
							.getDeclaredMethod("setSeleccionar_componente",
									ISeleccionarComponente.class);
					methodSeleccionar_componente.invoke(componente,
							new ISeleccionarComponente() {
								@Override
								public void onSeleccionar(
										Map<String, Object> pcd)
										throws Exception {
									adicionarPcd(pcd);
								}
							});
				} catch (Exception e) {
					log.error("Error al cargar metodo setSeleccionar_componente");
				}
			}
		}
	}

	public void adicionarPcd(Map<String, Object> pcd) throws Exception {
		try {
			if (((String) pcd.get("tipo")).equals("1")) {
				adicionarRestriccion(pcd);
			} else if (((String) pcd.get("tipo")).equals("2")) {
				adicionarEspecial(pcd);
			} else if (((String) pcd.get("tipo")).equals("3")) { // Descuentos
				adicionarDescuentol(pcd);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", (ZKWindow) iManuelesAction);
		}
	}

	private void adicionarDescuentol(Map<String, Object> pcd) {
		try {
			actualizarPaginaDescuento();
			lista_datos_descuentos.add(pcd);
			crearFilasDescuento();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", (ZKWindow) iManuelesAction);
		}
	}

	public void adicionarRestriccion(Map<String, Object> bean) throws Exception {
		try {
			actualizarPaginaRestriccion();
			lista_datos.add(bean);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", (ZKWindow) iManuelesAction);
		}
	}

	// Para tarifas especiales//
	public void adicionarEspecial(Map<String, Object> bean) throws Exception {
		try {
			actualizarPaginaEspecial();
			lista_datos_especial.add(bean);
			crearFilasEspecial();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", (ZKWindow) iManuelesAction);
		}
	}

	public void crearFilasEspecial() throws Exception {
		rows_procedimientos_esp.getChildren().clear();
		for (int j = 0; j < lista_datos_especial.size(); j++) {
			Map<String, Object> bean = lista_datos_especial.get(j);
			crearFilaEspecial(bean, j);
		}
	}

	public void actualizarPaginaEspecial() throws Exception {
		for (int i = 0; i < lista_datos_especial.size(); i++) {
			Map bean = lista_datos_especial.get(i);

			Row fila = (Row) rows_procedimientos_esp.getFellow("fila_especial_"
					+ i);
			Doublebox tbxValor_particular = (Doublebox) fila
					.getFellow("valor_particular_" + i);
			bean.put("valor_particular", tbxValor_particular.getValue());
			lista_datos_especial.set(i, bean);
		}
	}

	public void actualizarPaginaRestriccion() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);

			Row fila = (Row) rows_procedimientos
					.getFellow("fila_restricciones_" + i);
			Checkbox chbRequiere_aut = (Checkbox) fila
					.getFellow("requiere_aut_" + i);
			bean.put("requiere_aut", chbRequiere_aut.isChecked() ? "S" : "N");
			lista_datos.set(i, bean);
		}
	}

	public void actualizarPaginaDescuento() throws Exception {
		if (lista_datos_descuentos == null) {
			lista_datos_descuentos = new ArrayList<Map<String, Object>>();
			this.manuales_tarifarios
					.setLista_descuentos(lista_datos_descuentos);
			;
		}
		for (int i = 0; i < lista_datos_descuentos.size(); i++) {
			Map bean = lista_datos_descuentos.get(i);

			Row fila = (Row) rows_descuento.getFellow("fila_descuento_" + i);
			Doublebox doubleboxDescuento = (Doublebox) fila
					.getFellow("descuento_des_" + i);

			bean.put("descuento", doubleboxDescuento.getValue());
			lista_datos_descuentos.set(i, bean);
		}
	}

	public void crearFilasDescuento() {
		rows_descuento.getChildren().clear();
		for (int j = 0; j < lista_datos_descuentos.size(); j++) {
			Map<String, Object> bean = lista_datos_descuentos.get(j);
			crearFilaDescuento(bean, j);
		}
	}

	private void crearFilaDescuento(Map<String, Object> bean, int j) {
		final int index = j;

		String codigo_procedimiento = (String) bean.get("codigo_procedimiento");
		String nombre_procedimiento = (String) bean.get("nombre_procedimiento");
		// String codigo_cups = (String) bean.get("codigo_cups");
		Double descuento = (Double) bean.get("descuento");
		Double valor_procedimiento = (Double) bean.get("valor_procedimiento");
		// //log.info("codigo_procedimiento: "+codigo_procedimiento+" requiere_aut: "+requiere_aut);

		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo //
		Cell cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_procedimiento = new Textbox(
				codigo_procedimiento);
		tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("codigo_procedimiento_des_" + j);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setReadonly(true);
		cell.appendChild(tbxCodigo_procedimiento);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_procedimiento = new Textbox(
				nombre_procedimiento);
		tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_procedimiento_des_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna aut //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox dbxValorProcedimientos = new Doublebox();
		dbxValorProcedimientos.setId("valor_des_" + j);
		dbxValorProcedimientos.setInplace(true);
		dbxValorProcedimientos.setHflex("1");
		dbxValorProcedimientos.setFormat("#,##0.0");
		dbxValorProcedimientos.setValue(valor_procedimiento);
		dbxValorProcedimientos.setReadonly(true);
		cell.appendChild(dbxValorProcedimientos);
		fila.appendChild(cell);

		// Columna aut //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox dbxDescuentos = new Doublebox();
		dbxDescuentos.setId("descuento_des_" + j);
		dbxDescuentos.setInplace(true);
		dbxDescuentos.setHflex("1");
		dbxDescuentos.setValue(descuento);
		dbxDescuentos.setAttribute("dbxValor", dbxValorProcedimientos);
		cell.appendChild(dbxDescuentos);
		fila.appendChild(cell);

		Double valor_descuento = null;
		if (valor_procedimiento != null && descuento != null) {
			valor_descuento = valor_procedimiento.doubleValue()
					* descuento.doubleValue() / 100D;
			valor_descuento = valor_procedimiento - valor_descuento;
		}

		// Columna aut //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox dbxValorConDescuentos = new Doublebox();
		dbxValorConDescuentos.setId("valor_descuento_des_" + j);
		dbxValorConDescuentos.setInplace(true);
		dbxValorConDescuentos.setHflex("1");
		dbxValorConDescuentos.setValue(valor_descuento);
		dbxValorConDescuentos.setReadonly(true);
		dbxValorConDescuentos.setFormat("#,##0.0");
		cell.appendChild(dbxValorConDescuentos);
		fila.appendChild(cell);
		dbxDescuentos.setAttribute("dbxValorConDescuento",
				dbxValorConDescuentos);

		// Evento del descuento
		dbxDescuentos.addEventListener(Events.ON_BLUR,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Doublebox doublebox = (Doublebox) arg0.getTarget();
						Doublebox valorprocedimiento = (Doublebox) doublebox
								.getAttribute("dbxValor");
						Doublebox valorConDescuento = (Doublebox) doublebox
								.getAttribute("dbxValorConDescuento");
						Double doubleDesxcuento = doublebox.getValue();
						Double valor_descuento = null;
						Double valor_procedimiento = valorprocedimiento
								.getValue();
						if (valor_procedimiento != null
								&& doubleDesxcuento != null) {
							valor_descuento = valor_procedimiento.doubleValue()
									* doubleDesxcuento.doubleValue() / 100D;
							valor_descuento = valor_procedimiento
									- valor_descuento;
						}
						valorConDescuento.setValue(valor_descuento);
					}
				});

		// Columna borrar //
		cell = new Cell();
		cell.setAlign("left");
		cell.setValign("top");
		Image img = new Image("/images/borrar.gif");
		img.setId("img_des_" + j);
		img.setTooltiptext("Quitar registro");
		img.setStyle("cursor:pointer");
		cell.appendChild(img);
		fila.appendChild(cell);

		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									actualizarPaginaDescuento();
									lista_datos_descuentos.remove(index);
									crearFilasDescuento();
								}
							}
						});
			}
		});
		fila.setId("fila_descuento_" + j);
		rows_descuento.appendChild(fila);

		grid_Descuento.setMold("paging");
		grid_Descuento.setPageSize(20);
	}

	public void crearFilaRestriccion(final Map<String, Object> bean, int j)
			throws Exception {
		final int index = j;

		String codigo_procedimiento = (String) bean.get("codigo_procedimiento");
		String nombre_procedimiento = (String) bean.get("nombre_procedimiento");
		String codigo_cups = (String) bean.get("codigo_cups");
		String requiere_aut = (String) bean.get("requiere_aut");
		// //log.info("codigo_procedimiento: "+codigo_procedimiento+" requiere_aut: "+requiere_aut);

		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo //
		Cell cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_procedimiento = new Textbox(
				codigo_procedimiento);
		tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("codigo_procedimiento_" + j);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setReadonly(true);
		cell.appendChild(tbxCodigo_procedimiento);
		fila.appendChild(cell);

		// Columna codigo cups //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_cups = new Textbox(codigo_cups);
		tbxCodigo_cups.setInplace(true);
		tbxCodigo_cups.setId("codigo_cups_" + j);
		tbxCodigo_cups.setHflex("1");
		tbxCodigo_cups.setReadonly(true);
		cell.appendChild(tbxCodigo_cups);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_procedimiento = new Textbox(
				nombre_procedimiento);
		tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_procedimiento_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna aut //
		cell = new Cell();
		cell.setAlign("left");
		final Checkbox chbRequiere_aut = new Checkbox();
		chbRequiere_aut.setId("requiere_aut_" + j);
		if (requiere_aut != null) {
			chbRequiere_aut.setChecked(requiere_aut.equals("S"));
		}
		cell.appendChild(chbRequiere_aut);
		fila.appendChild(cell);

		// Columna borrar //
		cell = new Cell();
		cell.setAlign("left");
		cell.setValign("top");
		Image img = new Image("/images/borrar.gif");
		img.setId("img_" + j);
		img.setTooltiptext("Quitar registro");
		img.setStyle("cursor:pointer");
		cell.appendChild(img);
		fila.appendChild(cell);

		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									actualizarPaginaRestriccion();
									lista_datos.remove(index);

								}
							}
						});
			}
		});
		fila.setId("fila_restricciones_" + j);
		rows_procedimientos.appendChild(fila);

		grid_procedimientos.setMold("paging");
		grid_procedimientos.setPageSize(20);
		// //log.info("fin de agregar");
	}

	public void crearFilaEspecial(final Map<String, Object> bean, int j)
			throws Exception {
		final int index = j;

		String codigo_cups = (String) bean.get("codigo_cups");
		String nombre_procedimiento = (String) bean.get("nombre_procedimiento");
		double valor_procedimiento = (Double) bean.get("valor_procedimiento");
		Double valor_particular = (Double) bean.get("valor_particular");

		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo //
		Cell cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_procedimiento = new Textbox(codigo_cups);
		tbxCodigo_procedimiento.setAttribute("id_procedimiento",
				bean.get("id_procedimiento"));
		tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("codigo_procedimiento_esp_" + j);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setReadonly(true);
		cell.appendChild(tbxCodigo_procedimiento);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_procedimiento = new Textbox(
				nombre_procedimiento);
		tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_procedimiento_esp_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna valor pcd //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValor_procedimiento = new Doublebox(
				valor_procedimiento);
		tbxValor_procedimiento.setInplace(true);
		tbxValor_procedimiento.setId("valor_procedimiento_" + j);
		tbxValor_procedimiento.setFormat("#,##0.00");
		tbxValor_procedimiento.setHflex("1");
		tbxValor_procedimiento.setReadonly(true);
		cell.appendChild(tbxValor_procedimiento);
		fila.appendChild(cell);

		// Columna valor pcd //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValor_particular = new Doublebox(
				(valor_particular != null ? valor_particular.doubleValue()
						: 0.0));
		tbxValor_particular.setInplace(true);
		tbxValor_particular.setId("valor_particular_" + j);
		tbxValor_particular.setFormat("#,##0.00");
		tbxValor_particular.setHflex("1");
		tbxValor_particular.setReadonly(false);
		cell.appendChild(tbxValor_particular);
		fila.appendChild(cell);

		// Columna borrar //
		cell = new Cell();
		cell.setAlign("left");
		cell.setValign("top");
		Image img = new Image("/images/borrar.gif");
		img.setId("img2_" + j);
		img.setTooltiptext("Quitar registro");
		img.setStyle("cursor:pointer");
		cell.appendChild(img);
		fila.appendChild(cell);

		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									actualizarPaginaEspecial();
									lista_datos_especial.remove(index);
									crearFilasEspecial();
								}
							}
						});
			}
		});
		fila.setId("fila_especial_" + j);
		rows_procedimientos_esp.appendChild(fila);

		grid_procedimientos_esp.setMold("paging");
		grid_procedimientos_esp.setPageSize(20);

	}

	public void verificarPlan() throws Exception {
		if (lista_datos.isEmpty() && lista_datos_especial.isEmpty()) {
			seleccionarTarifa();
		} else {
			Messagebox
					.show("Esta seguro que desea cambiar el manual tarifario? usted ya ha cargado procedimientos al detalle y se borraran ",
							"Eliminar Registro",
							Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event event)
										throws Exception {
									if ("onYes".equals(event.getName())) {

										borrarTablaTarifas();
										seleccionarTarifa();
									} else {
										lbxManual_tarifario.setSelectedIndex(0);
										for (int i = 0; i < lbxManual_tarifario
												.getItemCount(); i++) {
											Listitem listitem = lbxManual_tarifario
													.getItemAtIndex(i);
											Maestro_manual maestro_manual = (Maestro_manual) listitem
													.getValue();
											if (maestro_manual != null
													&& maestro_manual_aux != null) {
												if (maestro_manual
														.getId_manual()
														.equals(maestro_manual_aux
																.getId_manual())) {
													listitem.setSelected(true);
												}
											}
										}
									}
								}
							});
		}

	}

	public void borrarTablaDescuento() throws Exception {
		lista_datos_descuentos.clear();
		crearFilasDescuento();
	}

	public void borrarTablaTarifas() throws Exception {
		lista_datos_especial.clear();
		crearFilasEspecial();
	}

	public void setAccionEliminar(IAccionEliminar accionEliminar) {
		this.accionEliminar = accionEliminar;
	}

	public Row get_rowPadreContenedora() {
		return _rowPadreContenedora;
	}

	public void set_rowPadreContenedora(Row _rowPadreContenedora) {
		this._rowPadreContenedora = _rowPadreContenedora;
	}

	/**
	 * Esta clase es para contener las instancias de los rows
	 * 
	 * @author Luis Miguel
	 * */
	class ContenedorInstancias {
		/**
		 * Este es el row donde esta la infromacion la unidad funcional
		 * */
		Row row;
		/**
		 * Este es el padre donde esta ubicado el row, para realizar un detach
		 * ha ese padre.
		 * */
		Rows rows;
		/**
		 * Este es el check el cual indica si estan seleccionado
		 * */
		Checkbox checkbox;

		/**
		 * Esta es la lista de hijos.
		 * */
		List<ContenedorInstancias> contenedorInstancias;

		ContenedorInstancias contenedorInstanciasPadre;
	}

	/**
	 * Este metodo me permite salber si este manual contiene servicions de pyp
	 * 
	 * @author Luis Miguel
	 * @return java.lang.Boolean
	 * */
	public boolean contieneServiciosPyp() {
		return false;
	}

	public List<Map<String, Object>> getLista_datos_descuentos() {
		return lista_datos_descuentos;
	}

	public void setLista_datos_descuentos(
			List<Map<String, Object>> lista_datos_descuentos) {
		this.lista_datos_descuentos = lista_datos_descuentos;
	}

	private List<Row> getListadoSeleccionados(Rows rowsFilas, int columna) {
		List<Row> listado = new ArrayList<Row>();
		for (Component component : rowsFilas.getChildren()) {
			Row fila = (Row) component;
			Checkbox checkbox = (Checkbox) fila.getChildren().get(columna);
			if (checkbox != null && checkbox.isChecked()) {
				listado.add(fila);
			}
		}
		return listado;
	}

	public void verificarProcedimientosSeleccionados(Checkbox checkbox_todos) {
		boolean select = false;
		for (Checkbox checkbox : listado_checkbox) {
			if (checkbox.isChecked()) {
				select = true;
			} else {
				select = false;
				break;
			}
		}
		checkbox_todos.setChecked(select);
	}

	public List<Servicios_procedimientos> getListadoProcedimientosExcluyentes() {
		List<Servicios_procedimientos> listado_procedimientos = new ArrayList<Servicios_procedimientos>();

		return listado_procedimientos;
	}

	public List<Contratos_medicamentos> getListadoMedicamentosContratados() {
		List<Contratos_medicamentos> listado_medicamentos = new ArrayList<Contratos_medicamentos>();
		return listado_medicamentos;
	}

}
