package healthmanager.controller;

import healthmanager.controller.FacturacionCapitadaAgrupadaAction.OnAccionFactura;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.PacienteService;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.L2HContraintDate;
import com.framework.res.L2HContraintDate.TypeInit;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.FacturacionService;

public class ConsultarFacturasCapitadasAgrupadasAction extends ZKWindow {

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_administradora;
	@View
	private Textbox tbxInfoAseguradora;
	@View
	private Toolbarbutton btnLimpiarAseguradora;
	@View
	private Listbox lbxContratos;
	@View
	private Datebox dtbxFecha_inicio;
	@View
	private Datebox dtbxFecha_fin;
	// @View private Datebox dtbxFecha_factura;

	@View
	private Listbox listboxFacturas;

	private final String[] idsExcluyentes = { "tbxCodigo_administradora",
			"tbxInfoAseguradora" };

	private FacturacionService facturacionService;
	// private Manuales_tarifariosService manuales_tarifariosService;
	private PacienteService pacienteService;
	private List<Facturacion> list_facturacions;

	/* Esta es la factura generada */

	public static final String FACTURA_CAPITADA = "AGR";
	public static final String FACTURA_AGRUPADA = "CAP";
	public static final String FACTURA_ANULADA = "ANUL";

	@Override
	public void init() throws Exception {
		inicializarBandox();
		cargarEventos();
		inicializarFechas();
	}

	private void inicializarFechas() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		dtbxFecha_inicio.setValue(calendar.getTime());

		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		dtbxFecha_fin.setValue(calendar.getTime());
	}

	private void inicializarBandox() {
		parametrizarBandboxAdministradora();
	}

	private void parametrizarBandboxAdministradora() {
		tbxCodigo_administradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		tbxCodigo_administradora
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
						((BandboxRegistrosMacro) bandbox).setObject(registro);
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						listarContratos(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						listarContratos(null);
					}
				});
	}

	protected void listarContratos(Administradora registro) {
		if (registro == null) {
			lbxContratos.getItems().clear();

		} else {
			Utilidades.listarContratosConValorObjeto(lbxContratos,
					registro.getCodigo(), true, true, codigo_empresa,
					codigo_sucursal, getServiceLocator());
		}
	}

	private void cargarEventos() {
		lbxContratos.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {

					}
				});
	}

	public void buscarDatos() {
		try {
			listboxFacturas.getItems().clear();
			Administradora administradora = (Administradora) tbxCodigo_administradora
					.getRegistroSeleccionado();

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", empresa.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());

			if (lbxContratos.getSelectedItem() != null
					&& lbxContratos.getSelectedItem().getValue() instanceof Contratos) {
				Contratos contratos = (Contratos) lbxContratos
						.getSelectedItem().getValue();
				param.put("id_plan_bus", contratos.getId_plan());
			}

			param.put("tipo_in", getTiposFacturas());
			if (administradora != null)
				param.put("codigo_administradora", administradora.getCodigo());
			if (dtbxFecha_inicio.getValue() != null)
				param.put("fecha_inicio", L2HContraintDate.initFechaInHHMMSS(
						dtbxFecha_inicio.getValue(), TypeInit.Init00_00_00));

			if (dtbxFecha_fin.getValue() != null)
				param.put(
						"fecha_final",
						L2HContraintDate.initFechaInHHMMSS(
								dtbxFecha_fin.getValue(), TypeInit.end23_59_59));
			// param.put("estado", "PEND");
			// //log.info(param);
			list_facturacions = facturacionService.listarRegistros(param);
			// //log.info("size: " + list_facturacions.size());

			if (list_facturacions.isEmpty()) {
				MensajesUtil.mensajeInformacion("Informacion",
						"No se encontraron facturas");
			}

			for (Facturacion facturacion : list_facturacions) {
				listboxFacturas.appendChild(crearRowFactura(facturacion));
			}
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private List<String> getTiposFacturas() {
		List<String> in = new ArrayList<String>();
		in.add("GEN_CAP");
		in.add("GEN_AGR");
		in.add(FACTURA_ANULADA);
		return in;
	}

	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(getFellow("groupboxEditar"),
				idsExcluyentes);
		FormularioUtil.limpiarComponentes(getFellow("rowContedorValores"),
				idsExcluyentes);
		FormularioUtil.deshabilitarComponentes(getFellow("groupboxEditar"),
				false, new String[] { "tbxCodigo_administradora",
						"tbxInfoAseguradora", "ibxTotalFacturas",
						"dtxNetoPagar", "dtxretefuente", "dtxTotalFactura" });
		inicializarFechas();
		tbxCodigo_administradora.setButtonVisible(true);
	}

	private Listitem crearRowFactura(final Facturacion facturacion) {
		final Listitem listitem = new Listitem();

		/* verificamos si tiene contabilizacion automatica */
		boolean cont = false;
		contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
		res.setCodigo_empresa(facturacion.getCodigo_empresa());
		res = (contaweb.modelo.bean.Resolucion) getServiceLocator()
				.getResolucion_contService().consultar(res);
		if (res != null) {
			if (res.getContabiliza_aut()) {
				cont = true;
			}
		}

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(empresa.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(facturacion.getCodigo_tercero());
		paciente = pacienteService.consultar(paciente);

		if (facturacion.getRadicado().equals("S")) {
			listitem.setStyle("background-color:#FFBF80");
			listitem.setTooltiptext("FACTURA RADICADA "
					+ new SimpleDateFormat("yyyy-MM-dd").format(facturacion
							.getFecha_erradicacion()));
		} else if (facturacion.getTipo().equalsIgnoreCase(FACTURA_ANULADA)) {
			listitem.setStyle("background-color:#FF8A8A");
			listitem.setTooltiptext("FACTURA ANULADA "
					+ new SimpleDateFormat("yyyy-MM-dd").format(facturacion
							.getDelete_date()));
		}

		final Listcell listcellFechaFactura = new Listcell(
				new SimpleDateFormat("yyyy-MM-dd").format(facturacion
						.getFecha()));
		listitem.appendChild(listcellFechaFactura);

		listitem.appendChild(new Listcell(facturacion.getCodigo_documento_res()));

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(codigo_empresa);
		administradora.setCodigo_sucursal(codigo_sucursal);
		administradora.setCodigo(facturacion.getCodigo_administradora());
		administradora = getServiceLocator().getAdministradoraService()
				.consultar(administradora);

		listitem.appendChild(new Listcell(
				administradora != null ? administradora.getCodigo() + " "
						+ administradora.getNombre() : ""));

		String descripcion_contratos = "";
		StringTokenizer stringTokenizer = new StringTokenizer(
				facturacion.getId_plan(), "|");
		while (stringTokenizer.hasMoreTokens()) {
			String id_plan = stringTokenizer.nextToken();
			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(codigo_empresa);
			contratos.setCodigo_sucursal(codigo_sucursal);
			contratos.setCodigo_administradora(facturacion
					.getCodigo_administradora());
			contratos.setId_plan(id_plan);
			contratos = getServiceLocator().getContratosService().consultar(
					contratos);
			if (contratos != null) {
				descripcion_contratos += (contratos.getNro_contrato() + " "
						+ contratos.getNombre() + " ");
			}
		}

		listitem.appendChild(new Listcell(descripcion_contratos));

		listitem.appendChild(new Listcell(new DecimalFormat("#,##0.00")
				.format(facturacion.getValor_total())));
		final Listcell labelFechaDescripcion = new Listcell(
				facturacion.getDescripcion());
		listitem.appendChild(labelFechaDescripcion);
		listitem.appendChild(new Listcell(
				facturacion.getRadicado().equals("S") ? "SI" : "NO"));

		/* acciones de factura */
		Hbox hbox = new Hbox();
		// hbox.appendChild(new Space());

		/* imprimir factura */
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/print_ico.gif");
		toolbarbutton.setTooltiptext("Imprimir");
		// toolbarbutton.setVisible(false);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						imprimir(facturacion);
					}
				});
		if (!facturacion.getTipo().equalsIgnoreCase(FACTURA_ANULADA))
			hbox.appendChild(toolbarbutton);

		toolbarbutton = new Toolbarbutton();
		toolbarbutton
				.setImage(facturacion.getRadicado().equals("S")
						|| facturacion.getTipo().equalsIgnoreCase(
								FACTURA_ANULADA) ? "/images/vista_previa.png"
						: "/images/editar.gif");
		toolbarbutton
				.setTooltiptext(facturacion.getRadicado().equals("S") ? "Mostar"
						: "Editar");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostarFactura(facturacion, listcellFechaFactura,
								labelFechaDescripcion);
					}
				});
		// hbox.appendChild(new Space());
		hbox.appendChild(toolbarbutton);

		/* contabilizar */
		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/contabilizar.gif");
		toolbarbutton.setTooltiptext("Contabilizar");
		if (!cont) {
			toolbarbutton.setVisible(false);
		}
		// toolbarbutton.setDisabled(true);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Map<String, Object> param_cont = new HashMap<String, Object>();
						param_cont.put("codigo_empresa",
								usuarios.getCodigo_empresa());
						param_cont.put("codigo_sucursal",
								usuarios.getCodigo_sucursal());
						param_cont.put("id_factura",
								facturacion.getId_factura());
						
						getServiceLocator().getFacturacionService()
								.guardarContabilizacionCapitada(param_cont,
										true);

						MensajesUtil
								.mensajeInformacion("Exito",
										"El documento se contabilizó satisfactoriamente");
					}
				});
		// hbox.appendChild(new Space());
		if (!facturacion.getTipo().equalsIgnoreCase(FACTURA_ANULADA))
			hbox.appendChild(toolbarbutton);

		/* Imprimir asiento contable */
		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/caja.jpg");
		toolbarbutton.setTooltiptext("Imprimir asiento factura");
		// toolbarbutton.setDisabled(true);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						imprimirNota_contable(facturacion.getId_factura());
					}
				});
		// hbox.appendChild(new Space());
		hbox.appendChild(toolbarbutton);

		/* Eliminar */
		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/eliminar.gif");
		toolbarbutton.setTooltiptext("Anular factura");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("Esta seguro que deseas anular esta factura? ",
										"Anulacion de factura",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													anularFactura(facturacion,
															listitem);
												}
											}
										});
					}
				});
		if (facturacion.getRadicado().equals("N")
				&& (!facturacion.getTipo().equalsIgnoreCase(FACTURA_ANULADA))) {
			hbox.appendChild(toolbarbutton);
		}

		Listcell listcell = new Listcell();
		listcell.appendChild(hbox);

		listitem.appendChild(listcell);
		return listitem;
	}

	/**
	 * Este metodo me permite anular la factura
	 * 
	 * @author Luis Miguel Hernandez
	 * @param facturacion
	 * */
	protected void anularFactura(Facturacion facturacion, Listitem listitem) {
		facturacion.setTipo(FACTURA_ANULADA);
		facturacion.setDelete_date(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		facturacion.setDelete_user(usuarios.getCodigo());
		facturacionService.actualizarAnulacionFacturaCapAgru(facturacion);
		listitem.setStyle("background-color:#FF8A8A");
		listitem.setTooltiptext("FACTURA ANULADA "
				+ new SimpleDateFormat("yyyy-MM-dd").format(facturacion
						.getDelete_date()));
		buscarDatos();
	}

	/**
	 * Este metodo me permite mostrar las facturas
	 * 
	 * @author Luis Miguel
	 * @param labelFechaDescripcion
	 * @param labelFechaFactura
	 * */
	protected void mostarFactura(Facturacion facturacion,
			final Listcell labelFechaFactura,
			final Listcell labelFechaDescripcion) {
		FacturacionCapitadaAgrupadaAction componente = (FacturacionCapitadaAgrupadaAction) Executions
				.createComponents("/pages/facturacion_agrupada_capitada.zul",
						this, null);

		componente.setOnAccionFactura(new OnAccionFactura() {
			@Override
			public void facturaModificadaExitosamente(Facturacion facturacion) {
				// log.info("facturacion actualizada correctamente");
				labelFechaFactura.setValue(new SimpleDateFormat("yyyy-MM-dd")
						.format(facturacion.getFecha()));
				labelFechaDescripcion.setValue(facturacion.getDescripcion());
			}
		});
		componente.setHeight("90%");
		componente.setWidth("730px");
		componente.setTitle("FACTURA "
				+ (facturacion.getTipo().equals("GEN_CAP") ? "CAPITADA"
						: "AGRUPADA") + " "
				+ facturacion.getDescripcion().toUpperCase());
		componente.setClosable(true);
		boolean mostrado = componente.mostar(facturacion);
		if (mostrado)
			componente.doModal();
	}

	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(getFellow("groupboxEditar"));
			if (validarForm()) {
				// Cargamos los componentes //
				Messagebox.show("Esta seguro que desea generar esta factura? ",
						"Guardar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// Administradora administradora =
									// (Administradora)
									// tbxCodigo_administradora.getObject();

									Contratos contratos = lbxContratos
											.getSelectedItem().getValue();

									Map<String, Object> map = new HashMap<String, Object>();
									map.put("facturas", list_facturacions);
									map.put("tipo_factura",
											contratos.getTipo_facturacion()
													.equals("02") ? "IND"
													: "CAP");
									map.put("fecha_inicio",
											dtbxFecha_inicio.getValue());
									map.put("fecha_final",
											dtbxFecha_fin.getValue());
									map.put("usuario", usuarios);
									map.put("contrato", contratos);
									// facturacion =
									// facturacionService.generarFacturaAgrupadaCapitada(map);

									FormularioUtil
											.deshabilitarComponentes(
													getFellow("groupboxEditar"),
													true,
													new String[] { "tbxCodigo_administradora", });

									Notificaciones
											.mostrarNotificacionInformacion(
													"Informacion",
													"Los datos se guardaron satisfactoriamente",
													2000);
								}
							}
						});
			}
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	public void imprimir(Facturacion facturacion) {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Facturacion_cap");
		paramRequest.put("id_factura", facturacion.getId_factura());
		paramRequest.put("codigo_comprobante",
				facturacion.getCodigo_comprobante());
		paramRequest.put("formato", "pdf");
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
	}

	public void imprimirNota_contable(Long id_factura) throws Exception {
		if (id_factura == null) {
			Messagebox.show("El documento no se ha guardado aun",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Nota_contable");
		paramRequest.put("codigo_comprobante", "16");
		paramRequest.put("id_factura", id_factura);
		paramRequest.put("formato", "pdf");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	private boolean validarForm() {
		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		dtbxFecha_inicio.setStyle("background-color:white");
		dtbxFecha_fin.setStyle("background-color:white");

		if (dtbxFecha_fin.getValue() == null) {
			valida = false;
			dtbxFecha_fin.setStyle("background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_fin);
		}

		if (list_facturacions == null && valida) {
			valida = false;
			mensaje = "Para realizar esta opcion debe haber por lo menos un detalle de factura";
		}

		if (dtbxFecha_inicio.getValue() == null && valida) {
			valida = false;
			dtbxFecha_inicio.setStyle("background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_inicio);
		}

		if (!valida) {
			Notificaciones.mostrarNotificacionAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje, IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		}

		return valida;
	}

}
