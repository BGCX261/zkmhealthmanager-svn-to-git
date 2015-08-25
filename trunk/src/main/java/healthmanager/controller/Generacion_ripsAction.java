/*
 * facturacion_medicamentoAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Contratos_paquetes;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Facturas_relacionadas_rips;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Contratos_paquetesService;
import healthmanager.modelo.service.Detalles_paquetes_serviciosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Generacion_ripsService;
import healthmanager.modelo.service.GeneralExtraService;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.FacturacionService;

public class Generacion_ripsAction extends GeneralComposer {

	private static Logger log = Logger.getLogger(Generacion_ripsAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	public static final SimpleDateFormat formato_fecha = new SimpleDateFormat(
			"dd/MM/yyyy");

	public static final SimpleDateFormat formato_fecha_hora = new SimpleDateFormat(
			"dd/MM/yyyy hh:mm a");

	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private AdministradoraService administradoraService;
	@WireVariable
	private ContratosService contratosService;
	@WireVariable
	private FacturacionService facturacionService;
	@WireVariable
	private GeneralExtraService generalExtraService;
	@WireVariable
	private Contratos_paquetesService contratos_paquetesService;
	@WireVariable
	private Detalles_paquetes_serviciosService detalles_paquetes_serviciosService;
	@WireVariable
	private Generacion_ripsService generacion_ripsService;

	@View
	private Borderlayout borderlayoutEditar;

	@View
	private Groupbox groupboxEditar;

	@View
	private Listbox listboxRegistros;

	@View
	private Checkbox checkboxSeleccionar_todos;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxAseguradora;

	@View
	private Textbox tbxInfoAseguradora;

	@View
	private Toolbarbutton btnLimpiarAseguradora;

	@View
	private Checkbox chkCapitada;
	@View
	private Listbox lbxContratos;
	@View
	private Checkbox chkPrueba;
	@View
	private Radiogroup rgpFiltroFacturas;
	@View
	private Label lbRangoInicial;
	@View
	private Label lbRangoFinal;
	@View
	private Datebox dtbxRango_inicio;
	@View
	private Datebox dtbxRango_final;

	@View
	private Doublebox dbxTotal;

	@View
	private Checkbox chkIncluir_paquetes;
	@View
	private Label lbCantidadFacturas;

	@View
	private Checkbox chkContinuar_error;

	@View
	private Listbox lbxOrden_factura;
	@View
	private Textbox tbxCodigos_facturas;

	@View
	private Listbox lbxVias_ingreso;
	@View
	private Toolbarbutton btnFiltro_ingreso;

	private File byte_datos;
	private String nombre_archivo;

	// private final String[] idsExcluyentes = new String[] {
	// "bandboxAseguradora", "btnLimpiarAseguradora" };
	@Override
	public void init() {
		cargarVias(lbxVias_ingreso);
		parametrizarBandbox();
		chequearFiltroFacturas();
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
						return administradoraService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						limpiarListaFactura();
						dbxTotal.setValue(0.0);
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						lbxContratos.setDisabled(false);
						Utilidades.listarcontratosPorAdministradora(
								lbxContratos, false, registro.getCodigo(),
								Generacion_ripsAction.this, contratosService);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						lbxContratos.setDisabled(true);
						lbxContratos.getItems().clear();
						limpiarListaFactura();
					}
				});
	}

	/**
	 * Este metodo me permite limpiar en la vista las facturas cargadas
	 *
	 */
	public void limpiarListaFactura() {
		listboxRegistros.getItems().clear();
		actualizarCantidadFacturas(listboxRegistros.getItems().size());
		dbxTotal.setValue(0d);
	}

	public void chequearFiltroFacturas() {
		try {
			String valor = rgpFiltroFacturas.getSelectedItem().getValue();
			if (valor.equalsIgnoreCase("todos")) {
				lbRangoInicial.setVisible(false);
				lbRangoFinal.setVisible(false);
				dtbxRango_inicio.setVisible(false);
				dtbxRango_final.setVisible(false);
			} else if (valor.equalsIgnoreCase("rango")) {
				lbRangoInicial.setVisible(true);
				lbRangoFinal.setVisible(true);
				lbRangoInicial.setValue("Rango Inicial");
				lbRangoFinal.setValue("Rango Final");
				dtbxRango_inicio.setVisible(false);
				dtbxRango_final.setVisible(false);
			} else {
				lbRangoInicial.setVisible(true);
				lbRangoFinal.setVisible(true);
				lbRangoInicial.setValue("Fecha Inicial");
				lbRangoFinal.setValue("Fecha Final");
				dtbxRango_inicio.setVisible(true);
				dtbxRango_final.setVisible(true);
				dtbxRango_inicio.setDisabled(false);
				dtbxRango_final.setDisabled(false);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void buscarFacturas() {
		try {
			if (bandboxAseguradora.getRegistroSeleccionado() != null) {
				if (lbxContratos.getSelectedItem() != null
						&& !lbxContratos.getSelectedItem().getValue()
								.toString().isEmpty()) {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("codigo_empresa",
							empresa.getCodigo_empresa());
					parametros.put("codigo_sucursal",
							sucursal.getCodigo_sucursal());
					parametros.put("codigo_administradora",
							bandboxAseguradora.getValue());

					if (!chkCapitada.isChecked()) {
						Set<Listitem> listitems_seleccionados = lbxVias_ingreso
								.getSelectedItems();
						if (!listitems_seleccionados.isEmpty()) {
							if (listitems_seleccionados.size() != lbxVias_ingreso
									.getItems().size()) {
								List<String> listado_vias = new ArrayList<String>();
								for (Listitem listitem : listitems_seleccionados) {
									listado_vias.add((String) listitem
											.getValue());
								}
								parametros.put("vias_ingreso", listado_vias);
							}
							btnFiltro_ingreso.setImage("/images/filtro1.png");
						} else {
							btnFiltro_ingreso.setImage("/images/filtro.png");
						}
					}

					Contratos contratos = (Contratos) lbxContratos
							.getSelectedItem().getValue();

					if (contratos == null) {
						throw new ValidacionRunTimeException(
								"El contrato seleccionado no existe");
					}

					String tipo = getTipoFacturacion(contratos);

					String valor = rgpFiltroFacturas.getSelectedItem()
							.getValue();
					if (!valor.equalsIgnoreCase("todos")) {
						if (valor.equals("rango")) {
							// String rango_in = tbxRango_inicio.getValue();
							// String rango_fn = tbxRango_final.getValue();
						} else {
							Date fecha_in = dtbxRango_inicio.getValue();
							Date fecha_fn = dtbxRango_final.getValue();

							if (fecha_in != null && fecha_fn != null) {
								if (fecha_in.compareTo(fecha_fn) > 0) {
									MensajesUtil
											.mensajeAlerta(
													"Error en los rangos de fechas",
													"Para realizar la consulta de las facturas la fecha inicial no puede ser mayor a la fecha final");
									return;
								} else {
									parametros.put("fecha_in", fecha_in);
									parametros.put("fecha_fn", fecha_fn);
								}
							} else if (fecha_in != null && fecha_fn == null) {
								parametros.put("fecha_in", fecha_in);
							} else if (fecha_in == null && fecha_fn != null) {
								parametros.put("fecha_fn", fecha_fn);
							}
						}
					}

					if (chkCapitada.isChecked()) {
						tipo = "GEN_" + tipo;
						parametros.put("id_plan_bus", contratos.getId_plan());
					} else {
						if (!(contratos.getTipo_facturacion().equals(
								IConstantes.TIPO_CONTRATO_INDIVIDUAL_EVENTO) || contratos
								.getTipo_facturacion().equals(
										IConstantes.TIPO_CONTRATO_PORTABILIDAD))) {
							parametros.put("limite_paginado",
									"limit 1000 offset 0");
						}
						parametros.put("id_plan", contratos.getId_plan());
					}

					chkIncluir_paquetes.setChecked(contratos
							.getIncluir_paquetes().equals("S"));

					parametros.put("tipo", tipo);
					parametros.put("no_anulada", "_no_anulada");

					String orden_factura = lbxOrden_factura.getSelectedItem()
							.getValue().toString();
					if (orden_factura.equals("1")) {
						parametros.put("orden_factura",
								"order by codigo_documento");
					} else if (orden_factura.equals("2")) {
						parametros.put("orden_factura",
								"order by valor_total desc");
					}

					List<Facturacion> lista_facturas = facturacionService
							.listarFacturacion_rips(parametros);

					listboxRegistros.getItems().clear();
					actualizarCantidadFacturas(lista_facturas.size());

					Double acumulador = 0.0;

					for (int i = 0; i < lista_facturas.size(); i++) {
						Facturacion facturacion = lista_facturas.get(i);
						if (facturacion.getTotal_factura_clinica() == null) {
							facturacion.setTotal_factura_clinica(0.0);
						}

						double copago = (facturacion.getNocopago().equals("S") ? 0
								: facturacion.getValor_copago());
						if (facturacion.getAdmision() != null) {
							if ((facturacion.getTotal_factura_clinica()
									.doubleValue() - copago) != (facturacion
									.getValor_total() - copago)) {
								log.info("facturacion codigo    ==> "
										+ facturacion.getCodigo_documento_res());
								log.info("facturacion total     ==> "
										+ (facturacion
												.getTotal_factura_clinica() - copago));
								log.info("facturacion diferente ==> "
										+ (facturacion.getValor_total() - copago));
								facturacion.getAdmision().setCodigo_empresa(
										codigo_empresa);
								facturacion.getAdmision().setCodigo_sucursal(
										codigo_sucursal);
								facturacion
										.getAdmision()
										.setNro_identificacion(
												facturacion.getCodigo_tercero());
								facturacion
										.getAdmision()
										.setCodigo_administradora(
												facturacion
														.getCodigo_administradora());
								facturacion.getAdmision().setNro_ingreso(
										facturacion.getNro_ingreso());
								facturacionService
										.actualizarFacturaAutomatico(facturacion);
							}
						}

						Listitem listitem = new Listitem();
						Paciente paciente = facturacion.getAdmision() != null ? facturacion
								.getAdmision().getPaciente() : null;

						listitem.appendChild(new Listcell());

						Listcell listcell = new Listcell();
						Textbox textbox = new Textbox(
								facturacion.getCodigo_documento_res());
						textbox.setReadonly(true);
						textbox.setWidth("97%");
						textbox.setInplace(true);
						listcell.appendChild(textbox);
						listitem.appendChild(listcell);
						listcell = new Listcell();
						textbox = new Textbox(facturacion.getCodigo_tercero());
						textbox.setReadonly(true);
						textbox.setWidth("100px");
						textbox.setInplace(true);
						listcell.appendChild(textbox);
						listitem.appendChild(listcell);

						listcell = new Listcell();
						textbox = new Textbox(
								paciente != null ? paciente.getNombreCompleto()
										: "");
						textbox.setReadonly(true);
						textbox.setWidth("97%");
						textbox.setInplace(true);
						listcell.appendChild(textbox);
						listitem.appendChild(listcell);

						listcell = new Listcell();
						textbox = new Textbox(
								facturacion.getEvia() != null ? facturacion
										.getEvia().getDescripcion() : "");
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
						Doublebox doublebox = new Doublebox(
								facturacion.getValor_total());
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

						acumulador += facturacion.getValor_total();

						listitem.setValue(facturacion);
						listboxRegistros.appendChild(listitem);
					}

					dbxTotal.setValue(acumulador);
					actualizarCantidadFacturas(listboxRegistros.getItems()
							.size());
					verificarSeleccion();

					if (lista_facturas.isEmpty()) {
						MensajesUtil.mensajeInformacion(
								"No se encontraron facturas",
								"No se encontraron facturas para la Aseguradora "
										+ bandboxAseguradora.getValue()
										+ " con el plan "
										+ lbxContratos.getSelectedItem()
												.getLabel());
					}

					Facturas_relacionadas_rips facturas_relacionadas_rips = new Facturas_relacionadas_rips();
					facturas_relacionadas_rips
							.setCodigo_empresa(codigo_empresa);
					facturas_relacionadas_rips
							.setCodigo_sucursal(codigo_sucursal);
					facturas_relacionadas_rips
							.setCodigo_administradora(contratos
									.getCodigo_administradora());
					facturas_relacionadas_rips.setId_contrato(contratos
							.getId_plan());
					facturas_relacionadas_rips
							.setFecha_inicial(dtbxRango_inicio.getText());
					facturas_relacionadas_rips.setFecha_final(dtbxRango_final
							.getText());
					final Facturas_relacionadas_rips facturas_aux = generalExtraService
							.consultar(facturas_relacionadas_rips);
					if (facturas_aux != null) {
						Messagebox
								.show("Hay una seleccion guardada el "
										+ formato_fecha_hora
												.format(facturas_aux
														.getCreacion_date())
										+ " ¿Deseas cargarla?",
										"Relacion de facturas guardadas",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													seleccionarCodigosFacturas(facturas_aux
															.getFacturas_relacionadas());
												}
											}
										});
					}

				} else {
					MensajesUtil
							.mensajeAlerta(
									"Seleccionar Plan",
									"Para realizar la consulta de facturas debe seleccionar la aseguradora y el plan");
				}
			} else {
				MensajesUtil
						.mensajeAlerta(
								"Seleccionar Aseguradora",
								"Para realizar la consulta de facturas debe seleccionar la aseguradora y el plan");
			}
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public static String getTipoFacturacion(Contratos contratos) {
		if (contratos.getTipo_facturacion().equals(
				IConstantes.TIPO_CONTRATO_CAPITADA)) {
			return IConstantes.TIPO_FACTURA_CAP_INTERNA;
		} else if (contratos.getTipo_facturacion().equals(
				IConstantes.TIPO_CONTRATO_INDIVIDUAL_EVENTO)) {
			return IConstantes.TIPO_FACTURA_EVENTO_IND;
		} else if (contratos.getTipo_facturacion().equals(
				IConstantes.TIPO_CONTRATO_PORTABILIDAD)) {
			return IConstantes.TIPO_FACTURA_PORTABILIDAD;
		} else {
			return IConstantes.TIPO_FACTURA_AGRUPADA;
		}
	}

	/**
	 * Este metodo me permite visualizar el total de las facturas cargadas
	 *
	 */
	private void actualizarCantidadFacturas(int cantidad) {
		lbCantidadFacturas.setValue("Cantidad: " + cantidad);
	}

	public void seleccionarFacturas() {
		try {
			Set<Listitem> seleccionados = listboxRegistros.getSelectedItems();
			Double acumulador = 0.0;
			for (Listitem listitem : seleccionados) {
				Facturacion facturacion = (Facturacion) listitem.getValue();
				if (listboxRegistros.hasFellow("doublebox_facturacion_"
						+ facturacion.getId_factura())) {
					Doublebox doublebox = (Doublebox) listboxRegistros
							.getFellow("doublebox_facturacion_"
									+ facturacion.getId_factura());
					acumulador += doublebox.getValue();
				}
			}
			dbxTotal.setValue(acumulador);
			actualizarCantidadFacturas(seleccionados.size());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void generarRips() {
		try {
			log.info("ejecutando metodo @generarRips()");
			final HttpServletRequest request = getHttpServletRequest();
			final HttpServletResponse response = getHttpServletResponse();
			final ServletContext servletContext = request.getSession()
					.getServletContext();
			log.info("iniciando el metodo procesar del hilo");

			List<Facturacion> listado_facturas = new ArrayList<Facturacion>();
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < listboxRegistros.getItems().size(); i++) {
				Listitem listitem = listboxRegistros.getItemAtIndex(i);
				if (listitem.isSelected()) {
					Facturacion facturacion = (Facturacion) listitem.getValue();
					listado_facturas.add(facturacion);
					stringBuilder.append(facturacion.getId_factura()).append(
							",");
				}
			}

			if (!listado_facturas.isEmpty()) {
				guardarRelacionFacturasRips(stringBuilder.toString());

				log.info("Obteniendo el request y el response");
				Boolean prueba = chkPrueba.isChecked();
				String codigo_administradora = bandboxAseguradora.getValue();
				Contratos contratos = (Contratos) lbxContratos
						.getSelectedItem().getValue();

				String id_plan = contratos.getId_plan();

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("codigo_usuario", usuarios.getCodigo());
				datos.put("prueba", prueba);
				datos.put("codigo_administradora", codigo_administradora);
				datos.put("id_plan", id_plan);
				datos.put("codigo_empresa", empresa.getCodigo_empresa());
				datos.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				datos.put("empresa", empresa);
				datos.put("tipo", chkCapitada.isChecked() ? "TRUE" : null);
				datos.put("servletContext", servletContext);
				datos.put("response", response);
				datos.put("listado_facturas", listado_facturas);
				datos.put("contratos", contratos);
				datos.put("codigo_habilitacion",
						empresa.getCodigo_habilitacion());
				datos.put("longitud_mascara", getParametros_empresa()
						.getNumero_mascara_factura());

				Map<String, Object> parametros_paquetes = new HashMap<String, Object>();
				parametros_paquetes.put("codigo_empresa",
						contratos.getCodigo_empresa());
				parametros_paquetes.put("codigo_sucursal",
						contratos.getCodigo_sucursal());
				parametros_paquetes.put("id_plan", contratos.getId_plan());
				parametros_paquetes.put("codigo_administradora",
						contratos.getCodigo_administradora());

				List<Contratos_paquetes> listado_paquetes = contratos_paquetesService
						.listar(parametros_paquetes);

				log.info("Listando contratos paquetes ===> "
						+ listado_paquetes.size());

				List<Detalles_paquetes_servicios> listado_paquetes_servicios = new ArrayList<Detalles_paquetes_servicios>();

				for (Contratos_paquetes contratos_paquetes : listado_paquetes) {
					Map<String, Object> parametros_cups = new HashMap<String, Object>();
					parametros_cups.put("codigo_empresa",
							contratos_paquetes.getCodigo_empresa());
					parametros_cups.put("codigo_sucursal",
							contratos_paquetes.getCodigo_sucursal());
					parametros_cups.put("codigo_administradora",
							contratos_paquetes.getCodigo_administradora());
					parametros_cups.put("id_paquete",
							contratos_paquetes.getId_paquete());
					List<Detalles_paquetes_servicios> listado_detalles = detalles_paquetes_serviciosService
							.listar(parametros_cups);
					listado_paquetes_servicios.addAll(listado_detalles);
				}

				log.info("Listando paquetes servicios ===> "
						+ listado_paquetes_servicios.size());

				datos.put("listado_paquetes_servicios",
						listado_paquetes_servicios);
				datos.put("continuar_error", chkContinuar_error.isChecked());

				String formato_comprimido = formato_comprimido(codigo_empresa,
						codigo_sucursal, codigo_administradora);
				nombre_archivo = "Rips-" + codigo_administradora + "-"
						+ id_plan + "-"
						+ Utilidades.formatDate(new Date(), "yyyyMMddHHmmss")
						+ "." + formato_comprimido;

				byte_datos = generacion_ripsService
						.guardarRipsPorFacturasAdministradora(datos);
				Filedownload.save(byte_datos, "application/octet-stream");
				generacion_ripsService.setRips_generacion("FIN");
			} else {
				MensajesUtil
						.mensajeAlerta("No hay facturas seleccionadas",
								"No hay facturas seleccionadas para la generacion de rips");
			}
			log.info("nombre_archivo ===> " + nombre_archivo);

			// Filedownload.save(byte_datos,
			// "application/octet-stream", nombre_archivo);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", Generacion_ripsAction.this);
		}

	}

	public void imprimirRelacion() throws Exception {
		// Imprime reporte unico
		List<Facturacion> listado_facturas = new ArrayList<Facturacion>();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < listboxRegistros.getItems().size(); i++) {
			Listitem listitem = listboxRegistros.getItemAtIndex(i);
			if (listitem.isSelected()) {
				Facturacion facturacion = (Facturacion) listitem.getValue();
				listado_facturas.add(facturacion);
				stringBuilder.append(facturacion.getId_factura()).append(",");
			}
		}

		if (!listado_facturas.isEmpty()) {
			guardarRelacionFacturasRips(stringBuilder.toString());
			Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", "RelacionRips");
			paramRequest.put("nombre_reporte",
					"RELACION DE FACTURAS A ENTREGAR");
			paramRequest.put("listado_facturas", listado_facturas);
			paramRequest.put("valor_total", dbxTotal.getValue());
			paramRequest.put(
					"periodo_empresa",
					"Desde "
							+ formato_fecha.format(dtbxRango_inicio.getValue())
							+ " hasta "
							+ formato_fecha.format(dtbxRango_final.getValue()));
			paramRequest.put("nombre_empresa", tbxInfoAseguradora.getValue()
					+ " - " + lbxContratos.getSelectedItem().getLabel());

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, paramRequest);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		}
	}

	public void guardarRelacionFacturasRips(String relacion_facturas) {
		Contratos contratos = (Contratos) lbxContratos.getSelectedItem()
				.getValue();
		Facturas_relacionadas_rips facturas_relacionadas_rips = new Facturas_relacionadas_rips();
		facturas_relacionadas_rips.setCodigo_empresa(codigo_empresa);
		facturas_relacionadas_rips.setCodigo_sucursal(codigo_sucursal);
		facturas_relacionadas_rips.setCodigo_administradora(contratos
				.getCodigo_administradora());
		facturas_relacionadas_rips.setId_contrato(contratos.getId_plan());
		facturas_relacionadas_rips.setFecha_inicial(dtbxRango_inicio.getText());
		facturas_relacionadas_rips.setFecha_final(dtbxRango_final.getText());
		facturas_relacionadas_rips.setFacturas_relacionadas(relacion_facturas);
		facturas_relacionadas_rips.setCreacion_user(usuarios.getCodigo());
		facturas_relacionadas_rips.setCreacion_date(new Timestamp(new Date()
				.getTime()));

		Facturas_relacionadas_rips facturas_aux = generalExtraService
				.consultar(facturas_relacionadas_rips);

		if (facturas_aux != null) {
			facturas_relacionadas_rips.setCodigo_registro(facturas_aux
					.getCodigo_registro());
			generalExtraService.actualizar(facturas_relacionadas_rips);
		} else {
			generalExtraService.crear(facturas_relacionadas_rips);
		}

		log.info("facturas_relacionadas_rips ===> "
				+ facturas_relacionadas_rips);
	}

	public String formato_comprimido(String codigo_empresa,
			String codigo_sucursal, String codigo_administradora)
			throws Exception {

		String formato_comprimido = ".zip";

		Administradora admin = new Administradora();
		admin.setCodigo(codigo_administradora);
		admin = administradoraService.consultar(admin);

		formato_comprimido = (admin != null ? admin.getFormato_rips() : ".zip");

		return formato_comprimido;
	}

	public void seleccionarTodos() {
		seleccionarTodos(checkboxSeleccionar_todos.isChecked());
	}

	public void seleccionarTodos(boolean todos) {
		List<Listitem> listado_items = listboxRegistros.getItems();
		for (Listitem listitem : listado_items) {
			listitem.setSelected(todos);
		}
		seleccionarFacturas();
	}

	public void verificarSeleccion() {
		int seleccionados = listboxRegistros.getSelectedItems().size();
		checkboxSeleccionar_todos.setChecked(seleccionados == listboxRegistros
				.getItemCount());
	}

	public void seleccionarCodigosFacturas(String contenido) {
		log.info("Contenido ===> " + contenido);
		seleccionarTodos(false);
		StringTokenizer stoken = new StringTokenizer(contenido, ",");
		Map<String, String> mapa = new HashMap<String, String>();
		while (stoken.hasMoreTokens()) {
			String token = stoken.nextToken();
			mapa.put(token, token);
		}
		for (int i = 0; i < listboxRegistros.getItemCount(); i++) {
			Listitem listitem = listboxRegistros.getItemAtIndex(i);
			Facturacion facturacion = (Facturacion) listitem.getValue();
			if (mapa.containsKey(facturacion.getId_factura() + "")) {
				listitem.setSelected(true);
			} else {
				listitem.setSelected(false);
			}
		}
		verificarSeleccion();
		seleccionarFacturas();
	}

	private void cargarVias(Listbox listbox) {
		Listitem listitem;
		String tipo = listbox.getName();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo);
		parametros.put("orden_descripcion", "orden_descripcion");

		List<Elemento> elementos = elementoService.listar(parametros);

		for (Elemento elemento : elementos) {
			if (!elemento.getCodigo().equals(IVias_ingreso.URGENCIA)
					&& !elemento.getCodigo().equals(
							IVias_ingreso.HOSPITALIZACIONES)) {
				listitem = new Listitem();
				listitem.setValue(elemento.getCodigo());
				listitem.setLabel(elemento.getDescripcion());
				listbox.appendChild(listitem);
			}

		}
	}

}
