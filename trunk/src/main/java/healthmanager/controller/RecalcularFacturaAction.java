package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.ContratosService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Toolbarbutton;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.HiloProceso;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Facturacion;

public class RecalcularFacturaAction extends ZKWindow {
	
	private ContratosService contratosService;

	@View
	private Checkbox checkboxSeleccionar_todos;
	@View
	private Timer timerProgreso;
	@View
	private Progressmeter progressmeterProgreso;
	@View
	private Label labelProgreso;
	@View
	private Toolbarbutton btGuardar;
	
	private HiloProceso hiloProceso;

	@View
	private Borderlayout groupboxEditar;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxAseguradora;
	@View
	private Textbox tbxInfoAseguradora;
	@View
	private Toolbarbutton btnLimpiarAseguradora;

	@View
	private Listbox lbxPlan;

	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_fin;

	@View
	private Listbox listboxRegistros;

	private List<Facturacion> lista_datos;

	@View
	private Label lbTotal;
	
	private ServiceLocatorWeb serviceLocator;

	// Este es el contador de procesos
	// private ContadorProcesoAction contadorProcesoAction;

	@Override
	public void init() throws Exception {
		lista_datos = new ArrayList<Facturacion>();
		parametrizarBandbox();

		GregorianCalendar fecha = new GregorianCalendar();
		fecha.set(Calendar.DAY_OF_MONTH, 1);
		dtbxFecha_inicial.setValue(fecha.getTime());

		fecha = new GregorianCalendar();
		fecha.set(Calendar.DAY_OF_MONTH,
				fecha.getActualMaximum(Calendar.DAY_OF_MONTH));
		dtbxFecha_fin.setValue(fecha.getTime());
		serviceLocator = getServiceLocator();
	}

	private void parametrizarBandbox() {
		bandboxAseguradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		bandboxAseguradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						lbxPlan.setDisabled(false);
						Utilidades.listarcontratosPorAdministradora(lbxPlan,
								true, registro.getCodigo(),
								RecalcularFacturaAction.this, contratosService);
						listboxRegistros.getItems().clear();
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						Utilidades.listarcontratosPorAdministradora(lbxPlan,
								true, "", RecalcularFacturaAction.this, contratosService);
						lbxPlan.setDisabled(true);
						listboxRegistros.getItems().clear();
						setTotalLabel(null);
					}
				});
	}

	private void setTotalLabel(Integer total) {
		lbTotal.setValue((total != null ? "Total: " + total : ""));
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("codigo_administradora",
					bandboxAseguradora.getValue());

			Object seleccionado = lbxPlan.getSelectedItem().getValue();

			if (seleccionado instanceof Contratos) {
				Contratos contratos = (Contratos) seleccionado;
				parameters.put("id_plan", contratos.getId_plan());
				String tipo = contratos.getTipo_facturacion().equals("01") ? "CAP"
						: (contratos.getTipo_facturacion().equals("02") ? "IND"
								: "AGR");
				parameters.put("tipo", tipo);

			}

			parameters.put("fecha_in", dtbxFecha_inicial.getValue());
			parameters.put("fecha_fn", dtbxFecha_fin.getValue());
			parameters.put("no_anulada", "_no_anulada");
			parameters.put("orden_factura", "order by codigo_documento");

			lista_datos = getServiceLocator().getFacturacionService()
					.listarRegistros(parameters);
			crearFilas();
			setTotalLabel(lista_datos.size());

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// Este metodo genera las filas nuevamente //
	public void crearFilas() {
		listboxRegistros.getItems().clear();
		for (int j = 0; j < lista_datos.size(); j++) {
			Facturacion facturacion = lista_datos.get(j);
			crearFilaDetalle(facturacion, j);
		}
		verificarSeleccion();
	}

	// Metodo para crear la grilla cuando se consulte los servicios cargados //
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void crearFilaDetalle(Facturacion facturacion, int j) {
		Listitem listitem = new Listitem();
		Paciente paciente = facturacion.getAdmision().getPaciente();

		listitem.appendChild(new Listcell());

		Listcell listcell = new Listcell();
		Textbox textbox = new Textbox(facturacion.getCodigo_tercero());
		textbox.setReadonly(true);
		textbox.setWidth("100px");
		textbox.setInplace(true);
		listcell.appendChild(textbox);
		listitem.appendChild(listcell);

		listcell = new Listcell();
		textbox = new Textbox(paciente != null ? paciente.getNombreCompleto()
				: "");
		textbox.setReadonly(true);
		textbox.setWidth("97%");
		textbox.setInplace(true);
		listcell.appendChild(textbox);
		listitem.appendChild(listcell);

		listcell = new Listcell();
		Datebox datebox = new Datebox(facturacion.getFecha());
		datebox.setReadonly(true);
		datebox.setWidth("97%");
		datebox.setInplace(true);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setButtonVisible(false);
		listcell.appendChild(datebox);
		listitem.appendChild(listcell);

		listcell = new Listcell();
		textbox = new Textbox(facturacion.getCodigo_documento_res());
		textbox.setReadonly(true);
		textbox.setWidth("97%");
		textbox.setInplace(true);
		listcell.appendChild(textbox);
		listitem.appendChild(listcell);

		listcell = new Listcell();
		Doublebox doublebox = new Doublebox(facturacion.getValor_total());
		doublebox.setReadonly(true);
		doublebox.setWidth("97%");
		doublebox.setFormat("#,##0.00");
		doublebox.setInplace(true);

		doublebox.setId("doublebox_facturacion_"
				+ facturacion.getId_factura());

		listcell.appendChild(doublebox);
		listitem.appendChild(listcell);

		// verificamos si tiene valor en cero
		if (facturacion.getValor_total() <= 0) {
			listitem.setStyle("background-color:#FAB387");
		} else {
			listitem.setSelected(true);
		}

		listitem.setValue(facturacion);
		listboxRegistros.appendChild(listitem);

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (valida && listboxRegistros.getSelectedItems().isEmpty()) {
			mensaje = "Debe seleccionar al menos un registro de en la grilla";
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

		return valida;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				timerProgreso.start();
				hiloProceso = new HiloProceso("Hilo proceso",
						new HiloProceso.IProceso() {

							@Override
							public void procesar() {
								List<Facturacion> lista_facturas = new ArrayList<Facturacion>();
								for (int i = 0; i < listboxRegistros
										.getItemCount(); i++) {
									Listitem listitem = listboxRegistros
											.getItemAtIndex(i);
									if (listitem.isSelected())
										lista_facturas
												.add((Facturacion) listitem
														.getValue());
								}

								Map<String, Integer> map = serviceLocator
										.getFacturacionService()
										.guardarRecalculoFacturas(
												lista_facturas);
								int facturas_recalculadas = map
										.get("recalculada");
								int facturas_radicada = map.get("radicadas");
								int facturas_facturada_capitada = map
										.get("facturada");
								int admision_sin_fact = map
										.get("admision_sin_fact");
								int admision_sin_servicio = map
										.get("admision_sin_servicio");
								MensajesUtil
										.mensajeInformacion(
												"Informacion ..",
												"Número de facturas recalculadas: "
														+ facturas_recalculadas
														+ ""
														+ "\n Facturas no recalculadas: "
														+ "\n * Número de facturas radicadas: "
														+ facturas_radicada
														+ "\n * Número de facturas capitadas: "
														+ facturas_facturada_capitada
														+ "\n * Número de admisiones sin facturas: "
														+ admision_sin_fact
														+ "\n * Número de admisiones sin servicio contratado: "
														+ admision_sin_servicio);
							}
							
							public void finalizar(){
								
							}
							
						});

				hiloProceso.start();
				Clients.showBusy("Procesando");
				btGuardar.setDisabled(true);
				
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void seleccionarTodos() {
		List<Listitem> listado_items = listboxRegistros.getItems();
		for (Listitem listitem : listado_items) {
			listitem.setSelected(checkboxSeleccionar_todos.isChecked());
		}
	}

	public void verificarSeleccion() {
		List<Listitem> listado_items = listboxRegistros.getItems();
		if (!listado_items.isEmpty()) {
			boolean todos = true;
			for (Listitem listitem : listado_items) {
				if (!listitem.isSelected()) {
					todos = false;
					break;
				}
			}
			checkboxSeleccionar_todos.setChecked(todos);
		} else {
			checkboxSeleccionar_todos.setChecked(false);
		}
	}

	@SuppressWarnings("deprecation")
	public void onTimerProgreso() {
		Double progreso = getServiceLocator().getFacturacionService()
				.getPorcentaje_reclaculo();
		if (progreso < 100.0) {
			progressmeterProgreso.setValue(progreso.intValue());
			labelProgreso.setValue(progreso.intValue()+"%");
		} else {
			timerProgreso.stop();
			if(hiloProceso != null){
				hiloProceso.stop();
			}
			btGuardar.setDisabled(false);
			Clients.clearBusy();
		}
	}

}
