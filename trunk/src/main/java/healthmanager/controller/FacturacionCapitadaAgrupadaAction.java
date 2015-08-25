package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Pacientes_contratosService;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.LabelAlign;
import com.framework.res.Res;
import com.framework.res.LabelAlign.AlignText;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Impuesto;
import contaweb.modelo.service.FacturacionService;
import contaweb.modelo.service.ImpuestoService;

public class FacturacionCapitadaAgrupadaAction extends ZKWindow {

	/* inicalizamos los componentes */
	@View
	private Toolbarbutton btGuardar;
	@View
	private Toolbarbutton btNew;
	@View
	private Toolbarbutton btCancel;
	@View
	private Toolbarbutton btImprimir;
	@View
	private Toolbarbutton tbnBuscarFacturas;

	@View
	private Listbox lbxFormato;
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
	@View
	private Datebox dtbxFecha_factura;

	@View
	private Rows rowFacturas;

	@View
	private Doublebox dtxTotalFactura;
	@View
	private Intbox ibxTotalFacturas;
	@View
	private Doublebox dtxretefuente;

	@View
	private Listbox lbxAnioRetefuente;
	@View
	private Listbox lbxBase;
	@View
	private Doublebox dtxNetoPagar;

	@View
	private Textbox tbxObservacion;

	@View
	private Listbox listboxValores;

	@View
	private Row rowsaccioncapitadaNroDias;

	@View
	private Intbox ibxNroDiasReportados;

	@View
	private Doublebox dtxTotalFacturadoSum;

	@View
	private Textbox tbxMesFacturado;
	@View
	private Intbox ibxPoblacionSegunDB;
	@View
	private Textbox tbxContratoNo;
	@View
	private Textbox tbxTipoServicio;

	@View
	private Row rwInfoMesPoblacion;
	@View
	private Row rwInfoContratoServicio;

	@View
	private Grid gridFacturas;

	private final String[] idsExcluyentes = { "tbxCodigo_administradora",
			"tbxInfoAseguradora", "btCancel" };

	private FacturacionService facturacionService;

	private PacienteService pacienteService;
	private ImpuestoService impuestoService;

	/* Esta es la factura generada */
	private Facturacion facturacion;

	private OnAccionFactura onAccionFactura;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() throws Exception {
		inicializarBandox();
		parametrizarResultadoPaginado();
		inicializarAniosRetefuente();
		cargarEventos();
		inicializarFechas();
		listarCombox();
		dtxTotalFactura.addEventListener(Events.ON_BLUR,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						if (!dtxTotalFactura.isReadonly()) {
							calcularRetencion();
						}
					}
				});
		if (getParametros_empresa().getTipo_reporte_facturacion().equals(
				IConstantes.TIPO_REPORTE_FACTURA_ESE_CARTAGENA)) {
			rwInfoContratoServicio.setVisible(true);
			rwInfoMesPoblacion.setVisible(true);
		}
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Facturacion> listarResultados(
					Map<String, Object> parametros) {
				List<Facturacion> listado = getServiceLocator()
						.getFacturacionService().listarRegistros(parametros);
				return listado;

			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getFacturacionService()
						.totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearRowFactura((Facturacion) dato);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridFacturas, 5);
	}

	private void listarCombox() {
		listarImpuestos();
	}

	private void listarImpuestos() {
		lbxBase.getItems().clear();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("anio", lbxAnioRetefuente.getSelectedItem().getValue()
				.toString());
		List<Impuesto> impuestos = impuestoService.listar(map);

		Listitem listitem = new Listitem();
		listitem.setValue(null);
		listitem.setLabel(" -seleccionar- ");
		lbxBase.appendChild(listitem);
		for (Impuesto impuesto : impuestos) {
			listitem = new Listitem();
			listitem.setValue(impuesto);
			listitem.setLabel("Base: "
					+ impuesto.getBase()
					+ "  -Porc: "
					+ String.format(Locale.ENGLISH, "%.2f",
							(impuesto.getPorcentaje() * 100)) + "%  -Cuenta:"
					+ impuesto.getCodigo_cuenta());
			lbxBase.appendChild(listitem);
		}

		if (lbxBase.getItemCount() > 0) {
			lbxBase.setSelectedIndex(0);
		}
	}

	private void inicializarFechas() {
		dtbxFecha_factura.setValue(Calendar.getInstance().getTime());

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		dtbxFecha_inicio.setValue(calendar.getTime());

		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		dtbxFecha_fin.setValue(calendar.getTime());
	}

	private void inicializarAniosRetefuente() {
		Utilidades.listarAnios(lbxAnioRetefuente, 3);
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
						rowFacturas.getChildren().clear();
					}
				});
	}

	protected void listarContratos(Administradora registro) {
		if (registro == null) {
			lbxContratos.getItems().clear();
			tbnBuscarFacturas.setDisabled(true);
			listboxValores.getItems().clear();
		} else {
			listarContratosConValorObjeto(lbxContratos, registro.getCodigo(),
					true, codigo_empresa, codigo_sucursal, getServiceLocator());
		}
	}

	public void listarContratosConValorObjeto(Listbox listbox,
			String codigo_admin, boolean solo_abiertos, String codigo_empresa,
			String codigo_sucursal, ServiceLocatorWeb serviceLocator) {

		listbox.getItems().clear();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("codigo_administradora", codigo_admin);
		parameters.put("tipo_facturacion", "01");
		if (solo_abiertos) {
			parameters.put("cerrado", false);
		}
		List<Contratos> lista_contratos = serviceLocator.getContratosService()
				.listar(parameters);

		for (final Contratos contratos : lista_contratos) {
			final Listitem listitem_aux = new Listitem();
			listitem_aux.setValue(contratos);
			Listcell celda = new Listcell();
			listitem_aux.appendChild(celda);
			celda = new Listcell(contratos.getId_plan());
			listitem_aux.appendChild(celda);
			celda = new Listcell(contratos.getNombre());
			listitem_aux.appendChild(celda);
			celda = new Listcell(Utilidades.getNombreElemento(
					contratos.getTipo_facturacion(), "tipo_contrato",
					getServiceLocator()));
			listitem_aux.appendChild(celda);

			listitem_aux.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {

							if (!listitem_aux.isSelected()) {
								for (int i = 0; i < listboxValores
										.getItemCount(); i++) {
									Listitem listitem_aux = listboxValores
											.getItemAtIndex(i);
									if (((Contratos) listitem_aux.getValue())
											.getId_plan().equals(
													contratos.getId_plan())) {
										listitem_aux.detach();
										break;
									}
								}
							}

							Set<Listitem> listado_seleccion = lbxContratos
									.getSelectedItems();
							tbnBuscarFacturas.setDisabled(listado_seleccion
									.isEmpty());
							rowFacturas.getChildren().clear();
							if (!listado_seleccion.isEmpty()) {
								List<Contratos> lista_contratos = new ArrayList<Contratos>();
								for (Listitem listitem : listado_seleccion) {
									lista_contratos.add((Contratos) listitem
											.getValue());
								}
								seleccionarContrato(lista_contratos);
							} else {
								limpiarAccionContrato();
							}

						}
					});

			listbox.appendChild(listitem_aux);
		}
	}

	private void limpiarAccionContrato() {
		tbxObservacion.setValue("");
		listboxValores.getItems().clear();
	}

	private void cargarEventos() {
		lbxAnioRetefuente.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						listarImpuestos();
					}
				});

		lbxBase.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				calcularRetencion();
			}
		});
	}

	public void seleccionarContrato(List<Contratos> listado_contratos) {
		String observaciones = "";

		boolean mostrar_dias = false;

		for (int i = 0; i < listado_contratos.size(); i++) {
			Contratos contratos = listado_contratos.get(i);
			if (i == 0) {
				observaciones = contratos.getNombre() + ": "
						+ contratos.getObservacion();
			} else {
				observaciones = observaciones + "\n" + contratos.getNombre()
						+ ": " + contratos.getObservacion();
			}

			boolean existe = false;

			for (Listitem listitem : listboxValores.getItems()) {
				String id_plan = ((Contratos) listitem.getValue()).getId_plan();
				if (id_plan.equals(contratos.getId_plan())) {
					existe = true;
					break;
				}
			}

			if (!existe) {
				Listitem listitem = crearListitemValores(contratos);
				listboxValores.appendChild(listitem);
			}

			if (!mostrar_dias && contratos.getModo_facturacion().equals("01")) {
				mostrar_dias = true;
			}

		}

		rowsaccioncapitadaNroDias.setVisible(mostrar_dias);

		tbxObservacion.setValue(observaciones);

		dtxTotalFactura.setReadonly(false);
	}

	private Listitem crearListitemValores(Contratos contratos) {
		Listitem listitem = new Listitem();
		listitem.setValue(contratos);
		listitem.setId(contratos.getId_plan());
		Listcell listcell = new Listcell(contratos.getId_plan());
		listitem.appendChild(listcell);

		Intbox intbox_cant_us = new Intbox();
		intbox_cant_us.setValue(contratos.getCantidad_usuarios());
		intbox_cant_us.setHflex("1");
		intbox_cant_us.setInplace(true);
		listcell = new Listcell();
		listcell.appendChild(intbox_cant_us);
		listitem.appendChild(listcell);

		Res.cargarAutomatica(intbox_cant_us, contratos, "cantidad_usuarios");

		Doublebox doublebox_us_mes = new Doublebox();
		doublebox_us_mes.setValue(contratos.getUpc_mes());
		doublebox_us_mes.setHflex("1");
		doublebox_us_mes.setFormat("#,##0.00");
		doublebox_us_mes.setReadonly(true);
		doublebox_us_mes.setInplace(true);
		listcell = new Listcell();
		listcell.appendChild(doublebox_us_mes);
		listitem.appendChild(listcell);

		Doublebox doublebox_us_dia = new Doublebox();
		doublebox_us_dia.setValue(contratos.getUpc_dia());
		doublebox_us_dia.setHflex("1");
		doublebox_us_dia.setReadonly(true);
		doublebox_us_dia.setInplace(true);
		doublebox_us_dia.setFormat("#,##0.00");
		listcell = new Listcell();
		listcell.appendChild(doublebox_us_dia);
		listitem.appendChild(listcell);

		doublebox_us_dia.setValue(contratos.getUpc_dia());
		doublebox_us_mes.setValue(contratos.getUpc_mes());

		return listitem;
	}

	public void calcularRetencion() {
		double retencion = 0;
		Impuesto impuesto = lbxBase.getSelectedItem().getValue();
		double valor_total = dtxTotalFactura.getValue() != null ? dtxTotalFactura
				.getValue() : 0.0;
		if (impuesto != null) {
			if (valor_total >= impuesto.getBase()) {
				retencion = valor_total * impuesto.getPorcentaje();
			}
		}
		dtxretefuente.setValue(retencion);
		dtxNetoPagar.setValue(valor_total - retencion);
	}

	public void listarFacturas() {
		try {
			Administradora administradora = (Administradora) tbxCodigo_administradora
					.getObject();
			rowFacturas.getChildren().clear();
			dtxTotalFacturadoSum.setValue(0);
			dtxTotalFactura.setValue(0);
			ibxTotalFacturas.setValue(0);
			dtxNetoPagar.setValue(0);

			Set<Listitem> listado_seleccion = lbxContratos.getSelectedItems();

			List<Contratos> listado_contratos = new ArrayList<Contratos>();

			for (Listitem listitem : listado_seleccion) {
				listado_contratos.add((Contratos) listitem.getValue());
			}

			if (listado_contratos.isEmpty())
				throw new ValidacionRunTimeException(
						"No se encontraron contratos validos");

			if (rowsaccioncapitadaNroDias.isVisible()) {
				if (ibxNroDiasReportados == null) {
					Clients.scrollIntoView(ibxNroDiasReportados);
					ibxNroDiasReportados.focus();
					throw new ValidacionRunTimeException(
							"Para esta opcion es de caracter obligatorio ingresar el Número de días reportados");
				}
			}

			List<String> contratos_listado = new ArrayList<String>();
			for (Listitem listitem : listboxValores.getItems()) {
				Contratos contratos = (Contratos) listitem.getValue();
				contratos_listado.add(contratos.getId_plan());
			}

			String tipo = "CAP";

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", empresa.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param.put("tipo", tipo);
			param.put("codigo_administradora", administradora.getCodigo());
			param.put("contratos_listado", contratos_listado);
			param.put("fecha_in", dtbxFecha_inicio.getValue());
			param.put("fecha_fn", dtbxFecha_fin.getValue());
			param.put("estado", "PEND");

			resultadoPaginadoMacro.buscarDatos(param);

			cargarInformacionEnVista(param, listado_contratos.get(0),
					administradora);

			if (resultadoPaginadoMacro.totalRegistros() <= 0) {
				btGuardar.setDisabled(true);
			} else {
				btGuardar.setDisabled(false);
			}

		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private void cargarInformacionEnVista(Map<String, Object> parametros,
			Contratos contratos, Administradora administradora) {

		Double total = 0.0;
		Double total_sum = facturacionService
				.totalizarSumasDescuentos(parametros);
		
		for(String key_mapa : parametros.keySet())
			log.info(key_mapa+" ===> "+parametros.get(key_mapa));
		
		log.info("total_sum ===> "+total_sum);

		double valor_mes = contratos.getValor_mes();

		double valor_usuarios = valor_mes
				/ contratos.getCantidad_usuarios().intValue();
		// log.info("Valor usuario: " + valor_usuarios);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo_empresa", contratos.getCodigo_empresa());
		params.put("codigo_sucursal", contratos.getCodigo_sucursal());
		params.put("codigo_administradora",
				contratos.getCodigo_administradora());

		params.put("id_codigo", contratos.getId_plan());

		if (contratos.getModo_facturacion().equals("01")) {
			int nro_dias_reportados = ibxNroDiasReportados.getValue() != null ? ibxNroDiasReportados
					.getValue() : 0;
			total = nro_dias_reportados * contratos.getUpc_dia();
		} else if (contratos.getModo_facturacion().equals("02")) {
			total = valor_usuarios
					* getServiceLocator().getServicio(
							Pacientes_contratosService.class).getPoblacionReal(
							params);
		} else if (contratos.getModo_facturacion().equals("03")) {
			total = valor_mes;
		} else {
			total = total_sum;
		}

		dtxTotalFactura
				.setValue(dtxTotalFactura.getValue() != null ? (dtxTotalFactura
						.getValue() + total) : total);
		dtxTotalFacturadoSum
				.setValue(dtxTotalFacturadoSum.getValue() != null ? (dtxTotalFacturadoSum
						.getValue() + total_sum) : total_sum);
		ibxTotalFacturas.setValue(resultadoPaginadoMacro.totalRegistros());
		calcularRetencion();
	}

	private void cargarInformacionEnVista(Map<String, Object> parametros,Facturacion facturacion_aux) {

		Double total_sum = facturacionService
				.totalizarSumasDescuentos(parametros);

		dtxTotalFactura.setValue(facturacion_aux.getValor_pagar_factura());
		dtxTotalFacturadoSum
				.setValue(dtxTotalFacturadoSum.getValue() != null ? (dtxTotalFacturadoSum
						.getValue() + total_sum) : total_sum);
		ibxTotalFacturas
				.setValue(resultadoPaginadoMacro.totalRegistros());
		calcularRetencion();
		dtxNetoPagar.setValue(facturacion_aux.getValor_total());
	}

	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(getFellow("groupboxEditar"),
				idsExcluyentes);
		FormularioUtil.limpiarComponentes(getFellow("rowContedorValores"),
				idsExcluyentes);
		FormularioUtil.deshabilitarComponentes(getFellow("groupboxEditar"),
				false, new String[] { "btGuardar", "btNew",
						"tbnBuscarFacturas", "btImprimir",
						"tbxCodigo_administradora", "tbxInfoAseguradora",
						"ibxTotalFacturas", "dtxNetoPagar", "dtxretefuente",
						"dtxTotalFactura" });
		rowFacturas.getChildren().clear();
		Utilidades.listarAnios(lbxAnioRetefuente, 3);
		ibxTotalFacturas.setValue(0);
		dtxNetoPagar.setValue(0);
		dtxretefuente.setValue(0);
		dtxTotalFactura.setValue(0);
		btGuardar.setDisabled(true);
		tbnBuscarFacturas.setDisabled(true);
		btImprimir.setDisabled(true);
		inicializarFechas();
		tbxCodigo_administradora.setButtonVisible(true);
		facturacion = null;
		limpiarAccionContrato();
	}

	private Row crearRowFactura(Facturacion facturacion) {
		Row row = new Row();

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(empresa.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(facturacion.getCodigo_tercero());
		paciente = pacienteService.consultar(paciente);

		row.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd")
				.format(facturacion.getFecha())));
		row.appendChild(new Label(facturacion.getNro_atencion()));
		row.appendChild(new LabelAlign(new DecimalFormat("#,##0.00")
				.format(facturacion.getValor_total()), AlignText.RIGHT));
		row.appendChild(new Label(paciente != null ? paciente
				.getNro_identificacion() : ""));
		row.appendChild(new Label(paciente != null ? paciente
				.getNombreCompleto() : ""));
		return row;
	}

	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(getFellow("groupboxEditar"));
			if (validarForm()) {
				// Cargamos los componentes //
				Messagebox
						.show(facturacion != null ? "Estas seguro que deseas modificar esta factura?"
								: "Esta seguro que desea generar esta factura? ",
								"Guardar Registro",
								Messagebox.YES + Messagebox.NO,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener<Event>() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {

											String tarifa_usuario_dia = "";

											List<Contratos> lista_contratos = new ArrayList<Contratos>();
											for (int i = 0; i < listboxValores
													.getItems().size(); i++) {
												Listitem listitem = listboxValores
														.getItems().get(i);
												Contratos contratos = (Contratos) listitem
														.getValue();
												lista_contratos.add(contratos);
												Doublebox doublebox_usu_dia = (Doublebox) listitem
														.getChildren().get(3)
														.getFirstChild();
												String valor = doublebox_usu_dia
														.getValue() != null ? (doublebox_usu_dia
														.getValue() + "") : "0";
												tarifa_usuario_dia = tarifa_usuario_dia
														+ (i != 0 ? "|" : "")
														+ contratos
																.getId_plan()
														+ "#" + valor;
											}

											Map<String, Object> map = new HashMap<String, Object>();
											map.put("parametros_busqueda",
													resultadoPaginadoMacro.getParametrosBusqueda());
											map.put("anio", lbxAnioRetefuente
													.getSelectedItem()
													.getValue());
											map.put("base", lbxBase
													.getSelectedItem()
													.getValue());
											map.put("retencion",
													dtxretefuente.getValue());
											map.put("valor_total",
													dtxNetoPagar.getValue());
											map.put("valor_factura",
													dtxTotalFactura.getValue());
											map.put("tipo_factura", "CAP");
											map.put("fecha_inicio",
													dtbxFecha_inicio.getValue());
											map.put("fecha_final",
													dtbxFecha_fin.getValue());
											map.put("usuario", usuarios);
											map.put("fecha_fact",
													dtbxFecha_factura
															.getValue());
											map.put("lista_contratos",
													lista_contratos);
											map.put("factura", facturacion);
											map.put("concepto",
													tbxObservacion.getValue());
											map.put("nro_dias_reportados",
													ibxNroDiasReportados
															.getValue());
											map.put("tarifa_usuario_dia",
													tarifa_usuario_dia);

											map.put("descipcion_mes_facturado",
													tbxMesFacturado.getValue());
											map.put("poblacion_segun_base_datos",
													ibxPoblacionSegunDB
															.getValue());
											map.put("descripcion_nro_contrato",
													tbxContratoNo.getValue());
											map.put("descripcion_tipo_servicio",
													tbxTipoServicio.getValue());

											if (facturacion == null)
												facturacion = facturacionService
														.guardarGeneracionFacturaAgrupadaCapitada(map);
											else {
												facturacion
														.setTarifa_usuario_dia(tarifa_usuario_dia);
												facturacion
														.setConcepto(tbxObservacion
																.getValue());
												facturacion
														.setNro_dias_reportados(ibxNroDiasReportados
																.getValue());
												facturacionService
														.actualizarFactura(map);
												// log.info("Accion actualizar "
												// + onAccionFactura);
												if (onAccionFactura != null) {
													onAccionFactura
															.facturaModificadaExitosamente(facturacion);
												}
											}

											FormularioUtil
													.deshabilitarComponentes(
															getFellow("groupboxEditar"),
															true,
															new String[] {
																	"btGuardar",
																	"btNew",
																	"tbnBuscarFacturas",
																	"btImprimir",
																	"tbxCodigo_administradora",
																	"lbxFormato" });
											btImprimir.setDisabled(false);
											btGuardar.setDisabled(true);
											// Notificaciones.mostrarNotificacionInformacion("Informacion",
											// "Los datos se guardaron satisfactoriamente",
											// 2000);
											Messagebox
													.show("Los datos se guardaron satisfactoriamente, ¿Deseas imprimir el documento? ",
															"Informacion",
															Messagebox.YES
																	+ Messagebox.NO,
															Messagebox.QUESTION,
															new org.zkoss.zk.ui.event.EventListener<Event>() {
																public void onEvent(
																		Event event)
																		throws Exception {
																	if ("onYes"
																			.equals(event
																					.getName())) {
																		for (Listitem listitem : listboxValores
																				.getItems()) {
																			Contratos contratos = (Contratos) listitem
																					.getValue();
																			getServiceLocator()
																					.getContratosService()
																					.actualizar(
																							contratos);
																		}
																		imprimir();
																	}
																}
															});
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

	public boolean mostar(final Facturacion facturacion) {
		try {
			listboxValores.getItems().clear();
			this.facturacion = facturacion;
			tbxCodigo_administradora.setDisabled(true);
			lbxContratos.setDisabled(true);

			Administradora administradoraTemp = new Administradora();
			administradoraTemp.setCodigo_empresa(facturacion
					.getCodigo_empresa());
			administradoraTemp.setCodigo_sucursal(facturacion
					.getCodigo_sucursal());
			administradoraTemp
					.setCodigo(facturacion.getCodigo_administradora());
			final Administradora administradora = getServiceLocator()
					.getAdministradoraService().consultar(administradoraTemp);

			if (administradora == null) {
				throw new ValidacionRunTimeException(
						"No se encuentra registro de la administradora "
								+ facturacion.getCodigo_administradora());
			}

			tbxCodigo_administradora.setValue(administradora.getCodigo());
			tbxInfoAseguradora.setValue(administradora.getNombre());

			listarContratos(administradora);

			StringTokenizer stringTokenizer = new StringTokenizer(
					facturacion.getId_plan(), "|");

			final List<Contratos> listado_contratos = new ArrayList<Contratos>();

			while (stringTokenizer.hasMoreTokens()) {
				String id_plan = stringTokenizer.nextToken();
				Contratos contratosTemp = new Contratos();
				contratosTemp
						.setCodigo_empresa(facturacion.getCodigo_empresa());
				contratosTemp.setCodigo_sucursal(facturacion
						.getCodigo_sucursal());
				contratosTemp.setCodigo_administradora(facturacion
						.getCodigo_administradora());
				contratosTemp.setId_plan(id_plan);
				final Contratos contratos = getServiceLocator()
						.getContratosService().consultar(contratosTemp);

				if (contratos == null) {
					throw new ValidacionRunTimeException(
							"No se encuentra registro del contrato " + id_plan
									+ " que pertenece a la administradora "
									+ facturacion.getCodigo_administradora());
				}
				listado_contratos.add(contratos);

				/* seleccionamos contratos */
				for (Listitem listitem : lbxContratos.getItems()) {
					if (listitem.getValue() != null) {
						Contratos contratosTemp2 = listitem.getValue();
						if (contratosTemp2.getId_plan().equals(
								contratos.getId_plan())) {
							listitem.setSelected(true);
							break;
						}
					}
				}

				listboxValores.appendChild(crearListitemValores(contratos));

			}

			seleccionarContrato(listado_contratos);

			dtbxFecha_inicio.setValue(facturacion.getFecha_inicio());
			dtbxFecha_fin.setValue(facturacion.getFecha_final());
			dtbxFecha_factura.setValue(facturacion.getFecha());
			tbxObservacion
					.setValue(facturacion.getConcepto() != null ? facturacion
							.getConcepto() : "");
			ibxNroDiasReportados.setValue(facturacion.getNro_dias_reportados());
			// dbxTarifaUsuario_dia
			// .setValue(facturacion.getTarifa_usuario_dia() != null ?
			// facturacion
			// .getTarifa_usuario_dia() : listado_contratos.get(0)
			// .getUpc_dia());
			tbxMesFacturado.setValue(facturacion.getDescipcion_mes_facturado());
			ibxPoblacionSegunDB.setValue(facturacion
					.getPoblacion_segun_base_datos());
			tbxContratoNo.setValue(facturacion.getDescripcion_nro_contrato());
			tbxTipoServicio
					.setValue(facturacion.getDescripcion_tipo_servicio());

			btNew.setVisible(false);

			Utilidades.setValueFrom(lbxAnioRetefuente,
					facturacion.getAnio_retencion());
			listarImpuestos();

			for (Listitem listitem : lbxBase.getItems()) {
				if (listitem.getValue() != null) {
					Impuesto impuestoTemp = listitem.getValue();
					if (impuestoTemp.getCodigo_cuenta().equals(
							facturacion.getCuenta_retencion())) {
						listitem.setSelected(true);
						break;
					}
				}
			}

			// caculamos valores de facturas
			final Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", empresa.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param.put("factura", facturacion.getId_factura()+"");
			
			resultadoPaginadoMacro.buscarDatos(param);
			
			cargarInformacionEnVista(param,facturacion);

			if (!facturacion.getTipo().equals(
					ConsultarFacturasCapitadasAgrupadasAction.FACTURA_ANULADA)) {
				btImprimir.setDisabled(false);
			}

			ibxNroDiasReportados.addEventListener(Events.ON_BLUR,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							cargarInformacionEnVista(param,facturacion);
						}
					});

			if (facturacion.getRadicado().equals("S")
					|| facturacion
							.getTipo()
							.equalsIgnoreCase(
									ConsultarFacturasCapitadasAgrupadasAction.FACTURA_ANULADA)) {
				btGuardar.setDisabled(true);
				tbnBuscarFacturas.setDisabled(true);
				MensajesUtil
						.mensajeInformacion("Informacion",
								"Esta factura no se puede editar por que ya se encuentra radicada!");
			} else {
				// tbnBuscarFacturas.setDisabled(true);
				btGuardar.setLabel("Modificar factura");
			}
		} catch (ValidacionRunTimeException e) {
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					e.getMessage(), IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			MensajesUtil.mensajeError(e, null, this);
			return false;
		}
		return true;
	}

	public void imprimir() {
		if (facturacion == null) {
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					"Este documento no se ha guardado aún", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		} else {
			Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", "Facturacion_cap");
			paramRequest.put("id_factura",
					facturacion.getId_factura());
			paramRequest.put("codigo_comprobante",
					facturacion.getCodigo_comprobante());
			paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
					.toString());
			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, paramRequest);
			final Window window = (Window) componente;
			window.setWidth("100%");
			window.setHeight("100%");
			window.doModal();
		}
	}

	private boolean validarForm() {
		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		dtbxFecha_inicio.setStyle("background-color:white");
		dtbxFecha_fin.setStyle("background-color:white");
		dtxTotalFactura.setStyle("background-color:white");
		dtbxFecha_factura.setStyle("background-color:white");
		tbxObservacion.setStyle("background-color:white");

		if (dtbxFecha_fin.getValue() == null) {
			valida = false;
			dtbxFecha_fin.setStyle("background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_fin);
		}

		if (resultadoPaginadoMacro.totalRegistros() <= 0 && valida) {
			valida = false;
			mensaje = "Para realizar esta opcion debe haber por lo menos un detalle de factura";
		}

		if (dtbxFecha_inicio.getValue() == null && valida) {
			valida = false;
			dtbxFecha_inicio.setStyle("background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_inicio);
		}

		if (dtbxFecha_factura.getValue() == null && valida) {
			valida = false;
			dtbxFecha_factura.setStyle("background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_factura);
		}

		if (dtxTotalFactura.getValue() == 0D && valida) {
			valida = false;
			dtxTotalFactura.setStyle("background-color:#F6BBBE");
			mensaje = "El valor de la factura no puede ser menor ni igual a Cero (0)";
			Clients.scrollIntoView(dtxTotalFactura);
		}

		if (rowsaccioncapitadaNroDias.isVisible()) {
			if (ibxNroDiasReportados.getValue() == null) {
				valida = false;
				Clients.scrollIntoView(ibxNroDiasReportados);
				ibxNroDiasReportados.focus();
				mensaje = "El Número de días reportados es requerido";
			}
		}

		if (tbxObservacion.getValue().trim().isEmpty() && valida) {
			valida = false;
			tbxObservacion.setStyle("background-color:#F6BBBE");
			mensaje = "El concepto es obligatorio.";
			Clients.scrollIntoView(dtxTotalFactura);
		}

		if (!valida) {
			Notificaciones.mostrarNotificacionAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje, IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		}

		return valida;
	}

	public interface OnAccionFactura {
		void facturaModificadaExitosamente(Facturacion facturacion);
	}

	public OnAccionFactura getOnAccionFactura() {
		return onAccionFactura;
	}

	public void setOnAccionFactura(OnAccionFactura onAccionFactura) {
		// log.info("Accion factura " + onAccionFactura);
		this.onAccionFactura = onAccionFactura;
	}
}
