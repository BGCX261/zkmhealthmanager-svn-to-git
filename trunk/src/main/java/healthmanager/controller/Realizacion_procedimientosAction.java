/*
 * hoja_gastosAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Realizacion_procedimientosAction extends ZKWindow {

	private static Logger log = Logger
			.getLogger(Realizacion_procedimientosAction.class);

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxAdmision;
	@View
	private Textbox tbxdirPaciente;
	@View
	private Textbox tbxtelpaciente;
	@View
	private Datebox tbxFechNacpaciente;

	@View
	private Listbox lbxProcedimientoPorRealizar;
	@View
	private Textbox tbxInfoPaciente;

	@View
	private Textbox tbxNroIngreso;

	@View
	private Toolbarbutton btnLimpiarPaciente;

	@View
	private Toolbarbutton btGuardar;

	@View
	private Toolbarbutton btCancel;

	@View
	private Groupbox groupboxPaciente;

	/* parametros */
	private final String ADMISION = "adson";
	private final String ADMINITRADORA = "admin";

	private Admision admision_seleccionada;
	private Long codigo_orden_servicio;

	private final String[] idsExcluyentes = {};

	private Ordenes_medicas_enfermeraAction ordenes_medicas_enfermeraAction;

	@Override
	public void init() {
		listarCombos();
		parametrizarBandBox();
		if (admision_seleccionada != null) {
			btCancel.setDisabled(true);
			((BandboxRegistrosIMG<Admision>) bandboxAdmision
					.getBandboxRegistrosIMG()).seleccionarRegistro(
					bandboxAdmision, tbxInfoPaciente, admision_seleccionada);
			bandboxAdmision.setDisabled(true);
			btnLimpiarPaciente.setVisible(false);
			groupboxPaciente.setVisible(false);
			cargamosOrdenServicio();
		}
		if (getParent() instanceof Ordenes_medicas_enfermeraAction) {
			ordenes_medicas_enfermeraAction = (Ordenes_medicas_enfermeraAction) getParent();
		}
	}

	private void parametrizarBandBox() {
		parametrizarBandboxAdmision();
	}

	public void listarCombos() {
		listarParameter();
	}

	@Override
	public void params(Map<String, Object> map) {
		admision_seleccionada = (Admision) map.get("admision_seleccionada");
		codigo_orden_servicio = (Long) map.get("codigo_orden_servicio");
	}

	private void parametrizarBandboxAdmision() {
		bandboxAdmision.inicializar(tbxInfoPaciente, btnLimpiarPaciente);
		bandboxAdmision
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Admision>() {

					@Override
					public void renderListitem(Listitem listitem,
							Admision registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_ingreso() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getPaciente()
								.getNombre1()
								+ " "
								+ registro.getPaciente().getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getPaciente()
								.getApellido1()
								+ " "
								+ registro.getPaciente().getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Admision> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("limite_paginado", "limit 25 offset 0");

						return getServiceLocator().getAdmisionService().listarResultados(
								parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Admision registro) {

						List<Admision> listaAdmisiones = Utilidades
								.listarAdmisiones(
										registro.getNro_identificacion(), "",
										false,
										Realizacion_procedimientosAction.this);

						if (listaAdmisiones.isEmpty()) {
							Messagebox
									.show("No se ha registrado el Ingreso del Paciente",
											"Paciente no admisionado",
											Messagebox.OK,
											Messagebox.EXCLAMATION);
							limpiarDatos();
							return false;
						}

						bandbox.setValue(registro.getPaciente()
								.getTipo_identificacion()
								+ " - "
								+ registro.getNro_identificacion());
						textboxInformacion.setValue(registro.getPaciente()
								.getNombreCompleto());

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						limpiarDatos();
					}
				});
	}

	public void limpiarDatos() {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		bandboxAdmision.setValue("");
		bandboxAdmision.setDisabled(false);
		setVisibleAccionAdicionar(false);
		bandboxAdmision.removeAttribute(ADMISION);
		bandboxAdmision.removeAttribute(ADMINITRADORA);
		btGuardar.setDisabled(true);
		lbxProcedimientoPorRealizar.getItems().clear();
	}

	public void setVisibleAccionAdicionar(boolean visible) {
		getFellow("auxHeadAccion").setVisible(visible);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("pac.nro_identificacion ||' '|| pac.nombre1||' '||pac.nombre2 ||' '|| pac.apellido1||' '||pac.apellido2");
		listitem.setLabel("Paciente");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("hgasto.nro_ingreso");
		listitem.setLabel("Nro ingreso");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("hgasto.creacion_date::varchar");
		listitem.setLabel("Fecha(yyyy-MM-dd)");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {

		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		List<Listitem> listado_items = lbxProcedimientoPorRealizar.getItems();
		int contador = 0;
		for (Listitem listitem : listado_items) {
			Detalle_orden detalle_orden = (Detalle_orden) listitem.getValue();
			Intbox intbox_realizadas = (Intbox) listitem
					.getFellow("_intbox_realizadas_"
							+ detalle_orden.getConsecutivo());
			if (intbox_realizadas.getValue() != null
					&& intbox_realizadas.getValue().intValue() > 0) {
				contador++;
			}
		}
		String mensaje = "";
		if (contador == 0) {
			valida = false;
			mensaje = "Para guardar los datos por los menos debe digitar cantidades de procedimientos realizados";
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
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //
				Messagebox
						.show("Esta seguro que desea guardar los procedimeintos realizados y facturarlos?",
								"Guardar Registro",
								Messagebox.YES + Messagebox.NO,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener<Event>() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											List<Listitem> listado_seleccionados = lbxProcedimientoPorRealizar
													.getItems();

											List<Detalle_orden> lista_detalles = new ArrayList<Detalle_orden>();

											for (Listitem listitem : listado_seleccionados) {
												Detalle_orden detalle_orden = (Detalle_orden) listitem
														.getValue();
												if (!listitem.isDisabled()) {
													Intbox intbox_realizadas = (Intbox) listitem.getFellow("_intbox_realizadas_"
															+ detalle_orden
																	.getConsecutivo());
													if (intbox_realizadas
															.getValue() != null
															&& intbox_realizadas
																	.getValue()
																	.intValue() != 0) {

														Integer unidades_realizadas = (Integer) intbox_realizadas
																.getAttribute("unidades_realizadas");
														if (unidades_realizadas == null)
															unidades_realizadas = 0;

														if (intbox_realizadas
																.getValue() > unidades_realizadas
																&& intbox_realizadas
																		.getValue() <= detalle_orden
																		.getUnidades()) {
															detalle_orden
																	.setUnidades_realizadas(intbox_realizadas
																			.getValue());

															lista_detalles
																	.add(detalle_orden);
														} else {
															throw new ValidacionRunTimeException(
																	"Debe tener en cuanta que en el item cups "
																			+ detalle_orden
																					.getCodigo_cups()
																			+ " la cantidad de procedimientos realizados ("
																			+ intbox_realizadas
																					.getValue()
																			+ ")  debe ser mayor a la cantidad de procedimientos que actualmente estan facturados ("
																			+ unidades_realizadas
																			+ ") y menor que la cantidad de procedimientos ordenados ("
																			+ detalle_orden
																					.getUnidades()
																			+ ")");
														}
													} else {
														throw new ValidacionRunTimeException(
																"Se necesita diligenciar la cantidad de procedimientos realizados en el item cups "
																		+ detalle_orden
																				.getCodigo_cups());
													}

												}
											}

											if (lista_detalles.isEmpty()) {
												throw new ValidacionRunTimeException(
														"No hay items seleccionados para guardar la informacion de los procedimientos realizados");
											}

											Map<String, Object> mapa_datos = new HashMap<String, Object>();
											mapa_datos.put("lista_detalles",
													lista_detalles);
											mapa_datos.put("admision",
													admision_seleccionada);

											getServiceLocator()
													.getAdmisionService()
													.guardarFacturacionProcedimientosUrgencia(
															mapa_datos);

											Notificaciones
													.mostrarNotificacionInformacion(
															"Informacion ..",
															"Los datos se guardaron satisfactoriamente",
															3000);

											Orden_servicio orden_servicio = cargamosOrdenServicio();

											if (ordenes_medicas_enfermeraAction != null) {
												ordenes_medicas_enfermeraAction
														.mostrarInformacionOrdenServicio(
																orden_servicio,
																false);
											}

										}
									}
								});
			}
		} catch (Exception e) {
			if (e instanceof ValidacionRunTimeException) {
				MensajesUtil.mensajeAlerta(
						"No se puede guardar la informacion", e.getMessage());
			} else {
				MensajesUtil.mensajeError(e, "", this);
			}
		}

	}

	private Orden_servicio cargamosOrdenServicio() {
		try {
			Orden_servicio orden_servicio = new Orden_servicio();
			orden_servicio.setId(codigo_orden_servicio);

			orden_servicio = getServiceLocator().getOrden_servicioService()
					.consultar(orden_servicio);

			if (orden_servicio != null) {
				List<Detalle_orden> detalle_ordens = orden_servicio
						.getLista_detalle();
				// log.info("Total de ordenes enviadas ===> "
				// + detalle_ordens.size());
				lbxProcedimientoPorRealizar.getItems().clear();
				for (Detalle_orden detalle_orden : detalle_ordens) {
					if (detalle_orden.getUnidades().compareTo(
							detalle_orden.getUnidades_realizadas()) != 0) {
						lbxProcedimientoPorRealizar
								.appendChild(crearListItemProcedimientoPorRealizar(
										detalle_orden, true));
					} else {
						lbxProcedimientoPorRealizar
								.appendChild(crearListItemProcedimientoPorRealizar(
										detalle_orden, false));
					}
				}
			}
			return orden_servicio;
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
		return null;
	}

	private Listitem crearListItemProcedimientoPorRealizar(
			Detalle_orden detalle_orden, boolean habilitarEdicion) {

		Listitem listitem = new Listitem();
		listitem.setSelected(false);
		listitem.setValue(detalle_orden);
		listitem.setDisabled(!habilitarEdicion);

		Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
				.getManuales_tarifarios(admision_seleccionada);
		String nombre_procedimiento = "";
		String codigo_cups = detalle_orden.getCodigo_procedimiento();
		try {
			Map<String, Object> mapProcedimiento = Utilidades.getProcedimiento(
					manuales_tarifarios,
					new Long(detalle_orden.getCodigo_procedimiento()),
					getServiceLocator());
			nombre_procedimiento = (String) mapProcedimiento
					.get("nombre_procedimiento");
			codigo_cups = (String) mapProcedimiento.get("codigo_cups");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		listitem.appendChild(new Listcell("" + codigo_cups));
		listitem.appendChild(new Listcell("" + nombre_procedimiento));

		Listcell listcell = new Listcell();
		Intbox intbox_realizadas = new Intbox();
		intbox_realizadas.setId("_intbox_realizadas_"
				+ detalle_orden.getConsecutivo());
		intbox_realizadas.setAttribute("unidades_realizadas",
				detalle_orden.getUnidades_realizadas());
		intbox_realizadas.setValue(detalle_orden.getUnidades_realizadas());
		if (intbox_realizadas.getValue() != null
				&& intbox_realizadas.getValue().intValue() == 0)
			intbox_realizadas.setText("");
		intbox_realizadas.setHflex("1");
		listcell.appendChild(intbox_realizadas);
		listitem.appendChild(listcell);
		listitem.setAttribute("intbox_realizadas", intbox_realizadas);

		Intbox intbox_ordenadas = new Intbox((detalle_orden.getUnidades()));
		intbox_ordenadas.setHflex("1");
		intbox_ordenadas.setReadonly(true);
		intbox_ordenadas.setInplace(true);

		listcell = new Listcell();
		listcell.appendChild(intbox_ordenadas);
		listitem.appendChild(listcell);

		return listitem;
	}

}