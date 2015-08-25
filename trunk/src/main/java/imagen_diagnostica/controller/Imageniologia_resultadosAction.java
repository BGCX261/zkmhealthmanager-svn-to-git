/*
 * imagenes_diagnosticasAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package imagen_diagnostica.controller;

import healthmanager.controller.EcografiaAction;
import healthmanager.controller.ZKWindow;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Ecografia;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.EcografiaService;
import healthmanager.modelo.service.Resultado_laboratoriosService;
import imagen_diagnostica.modelo.bean.Imagen_diagnostica;
import imagen_diagnostica.modelo.bean.Laboratorios_resultados;
import imagen_diagnostica.modelo.service.Laboratorios_resultadosService;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.West;

import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.imagen_diagnostica.ImagenDiagnosticaVisualizador;
import com.framework.macros.imagen_diagnostica.LaboratoriosResultadosVisualizador;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Imageniologia_resultadosAction extends ZKWindow {

	private static Logger log = Logger
			.getLogger(Imageniologia_resultadosAction.class);

	private SimpleDateFormat formato_fecha = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss a");

	@View
	private Groupbox groupboxConsulta;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxPaciente;
	@View
	private Textbox tbxInfoPaciente;
	@View
	private Toolbarbutton btnLimpiarPaciente;

	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_final;

	@View
	private Column columnAcciones;

	@View
	private Rows rowsImagenes;

	@View
	private Rows rowsLaboratorios;

	@View
	private Rows rowsEcografias;

	@View
	private East eastEcografias;
	@View
	private West westImagenes;

	@View
	private Div divPopups_informacion;

	private Admision admision;

	@Override
	public void init() {
		listarCombos();
		parametrizarBandboxPaciente();
		if (admision != null) {
			columnAcciones.setWidth("90px");
			if (admision.getPaciente().getSexo().equals("M")) {
				westImagenes.setWidth("50%");
				eastEcografias.setVisible(false);
			} else {
				westImagenes.setWidth("34%");
				eastEcografias.setVisible(true);
			}
		} else {
			columnAcciones.setWidth("140px");
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
	}

	private void parametrizarBandboxPaciente() {
		bandboxPaciente.inicializar(tbxInfoPaciente, btnLimpiarPaciente);
		bandboxPaciente
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado",
								"limit 25 offset 0");

						return Imageniologia_resultadosAction.this
								.getServiceLocator().getPacienteService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {
						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro
								.getNombreCompleto());
						if (registro.getSexo().equals("M")) {
							westImagenes.setWidth("50%");
							eastEcografias.setVisible(false);
						} else {
							westImagenes.setWidth("34%");
							eastEcografias.setVisible(true);
						}
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						westImagenes.setWidth("34%");
						eastEcografias.setVisible(true);
					}
				});

		if (admision != null) {
			bandboxPaciente.setDisabled(true);
			bandboxPaciente.setValue(admision.getPaciente()
					.getNro_identificacion());
			tbxInfoPaciente
					.setValue(admision.getPaciente().getNombreCompleto());
		}
	}

	public void listarCombos() {

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {

	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		return true;
	}

	public void buscarDatos() throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if (dtbxFecha_inicial.getValue() != null
				&& dtbxFecha_final.getValue() != null) {
			if (dtbxFecha_inicial.getValue().compareTo(
					dtbxFecha_final.getValue()) > 0) {
				throw new ValidacionRunTimeException(
						"La fecha inicial de la busqueda no puede ser mayor que la fecha final");
			} else {
				parametros.put("fecha_inicial_p", new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				parametros.put("fecha_final_p", new Timestamp(dtbxFecha_final
						.getValue().getTime()));
			}
		} else {
			if (dtbxFecha_inicial.getValue() != null) {
				parametros.put("fecha_inicial_p", new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
			}
			if (dtbxFecha_final.getValue() != null) {
				parametros.put("fecha_final_p", new Timestamp(dtbxFecha_final
						.getValue().getTime()));
			}
		}

		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		if (!bandboxPaciente.getValue().isEmpty()) {
			parametros.put("nro_identificacion", bandboxPaciente.getValue());
			parametros.put("identificacion", bandboxPaciente.getValue());
		}

		divPopups_informacion.getChildren().clear();

		//log.info("parametros de busqueda ===> " + parametros);

		buscarDatosImagenes(parametros);
		buscarDatosLaboratorios(parametros);
		buscarDatosEcografias(parametros);

	}

	// Metodo para buscar //
	public void buscarDatosImagenes(Map<String, Object> parametros)
			throws Exception {
		//log.info("ejecutando metodo @buscarDatosImagenes");
		try {
			List<Imagen_diagnostica> listado_imagenes = getServiceLocator()
					.getImagen_diagnosticaService().listar(parametros);

			rowsImagenes.getChildren().clear();

			for (final Imagen_diagnostica imagen_diagnostica : listado_imagenes) {
				Row fila = new Row();
				Paciente paciente = null;

				Textbox textbox_paciente = new Textbox();
				textbox_paciente.setReadonly(true);
				textbox_paciente.setHflex("1");

				if (!bandboxPaciente.getValue().isEmpty()) {
					paciente = bandboxPaciente.getRegistroSeleccionado();
					textbox_paciente.setValue(bandboxPaciente.getValue() + "-"
							+ tbxInfoPaciente.getValue());
				} else {
					paciente = new Paciente();
					paciente.setCodigo_empresa(imagen_diagnostica
							.getCodigo_empresa());
					paciente.setCodigo_sucursal(imagen_diagnostica
							.getCodigo_sucursal());
					paciente.setNro_identificacion(imagen_diagnostica
							.getNro_identificacion());
					paciente = getServiceLocator().getPacienteService()
							.consultar(paciente);
					if (paciente != null) {
						textbox_paciente.setValue(paciente
								.getNro_identificacion()
								+ "-"
								+ paciente.getNombreCompleto());
					} else {
						textbox_paciente.setValue("Paciente no encontrado");
					}
				}

				fila.appendChild(textbox_paciente);

				Label label_fecha = new Label(
						formato_fecha.format(imagen_diagnostica
								.getFecha_realizacion()));

				fila.appendChild(label_fecha);

				Popup popup_informacion = new Popup();
				Html html = new Html();
				popup_informacion.appendChild(html);
				divPopups_informacion.appendChild(popup_informacion);

				html.setContent("Paciente: "
						+ textbox_paciente.getValue()
						+ "<br/>"
						+ "Fecha Realizacion : "
						+ formato_fecha.format(imagen_diagnostica
								.getFecha_realizacion()) + "<br />"
						+ "Lugar : " + imagen_diagnostica.getLugar() + "<br />"
						+ "Descripcion : "
						+ imagen_diagnostica.getDescripcion());

				label_fecha.setStyle("cursor : help");
				label_fecha.setTooltip(popup_informacion);
				textbox_paciente.setStyle("cursor : help");
				textbox_paciente.setTooltip(popup_informacion);

				Hlayout hlayout = new Hlayout();
				Toolbarbutton toolbarbutton = new Toolbarbutton("Mostrar",
						"/images/mostrar_info.png");
				toolbarbutton.setTooltiptext("Mostrar imagen");
				toolbarbutton.setStyle("cursor:pointer");

				final Paciente paciente_aux = paciente;

				toolbarbutton.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								mostrarImagen(imagen_diagnostica, paciente_aux);
							}
						});

				hlayout.appendChild(toolbarbutton);

				if (admision == null) {
					if (rol_usuario.equals("18") || rol_usuario.equals("01")) {
						toolbarbutton = new Toolbarbutton("Editar",
								"/images/editar.gif");
						toolbarbutton
								.setTooltiptext("Editar descripcion de imagen");
						toolbarbutton.setStyle("cursor:pointer");
						toolbarbutton.addEventListener(Events.ON_CLICK,
								new EventListener<Event>() {

									@Override
									public void onEvent(Event event)
											throws Exception {
										editarImagen(imagen_diagnostica,
												paciente_aux);
									}
								});

						hlayout.appendChild(toolbarbutton);
					}
				}

				fila.appendChild(hlayout);

				rowsImagenes.appendChild(fila);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void mostrarImagen(Imagen_diagnostica imagen_diagnostica,
			Paciente paciente) {
		String directorio = imagen_diagnostica.getDireccion_archivo();
		File file = new File(directorio);
		if (file.exists()) {
			ImagenDiagnosticaVisualizador diagnosticaVisualizador = new ImagenDiagnosticaVisualizador();
			diagnosticaVisualizador.setPage(getPage());
			diagnosticaVisualizador.getSouth().setVisible(true);
			diagnosticaVisualizador.getTbxDescripcion_imagen()
					.setReadonly(true);
			diagnosticaVisualizador.getTbxDescripcion_imagen().setValue(
					imagen_diagnostica.getDescripcion());
			diagnosticaVisualizador.setClosable(true);
			diagnosticaVisualizador.setMaximizable(true);
			diagnosticaVisualizador.mostrarImagenDCM(file, paciente);
			diagnosticaVisualizador.setHeight("75%");
			diagnosticaVisualizador.setWidth("70%");
			diagnosticaVisualizador.doModal();
		} else {
			MensajesUtil.mensajeAlerta("Advertencia",
					"El examen realizado no ha sido encontrado..");
		}
	}

	public void editarImagen(final Imagen_diagnostica imagen_diagnostica,
			Paciente paciente) {
		String directorio = imagen_diagnostica.getDireccion_archivo();
		File file = new File(directorio);
		if (file.exists()) {
			final ImagenDiagnosticaVisualizador diagnosticaVisualizador = new ImagenDiagnosticaVisualizador();
			diagnosticaVisualizador.setPage(getPage());
			diagnosticaVisualizador.getSouth().setVisible(true);
			diagnosticaVisualizador.getNorth().setVisible(true);
			diagnosticaVisualizador.getTbxDescripcion_imagen().setReadonly(
					false);
			diagnosticaVisualizador.getTbxDescripcion_imagen().setValue(
					imagen_diagnostica.getDescripcion());
			diagnosticaVisualizador.setClosable(true);
			diagnosticaVisualizador.setMaximizable(true);
			diagnosticaVisualizador.mostrarImagenDCM(file, paciente);
			diagnosticaVisualizador.setHeight("75%");
			diagnosticaVisualizador.setWidth("70%");

			diagnosticaVisualizador.getToolbarbutton_guardar()
					.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									imagen_diagnostica
											.setDescripcion(diagnosticaVisualizador
													.getTbxDescripcion_imagen()
													.getValue());
									// label_descripcion
									// .setValue(diagnosticaVisualizador
									// .getTbxDescripcion_imagen()
									// .getValue());
									Imageniologia_resultadosAction.this
											.getServiceLocator()
											.getImagen_diagnosticaService()
											.actualizar(imagen_diagnostica);
									MensajesUtil
											.mensajeInformacion(
													"Informacion guardada",
													"La descripcion de la imagen fue actualizada correctamente");
								}

							});

			diagnosticaVisualizador.doModal();
		} else {
			MensajesUtil.mensajeAlerta("Advertencia",
					"El examen realizado no ha sido encontrado..");
		}
	}

	// Metodo para buscar //
	public void buscarDatosLaboratorios(Map<String, Object> parametros)
			throws Exception {
		//log.info("ejecutando metodo @buscarDatosLaboratorios");
		try {

			List<Laboratorios_resultados> listado_laboratorios = getServiceLocator()
					.getServicio(Laboratorios_resultadosService.class).listar(
							parametros);

			rowsLaboratorios.getChildren().clear();

			for (final Laboratorios_resultados laboratorios_resultados : listado_laboratorios) {
				Row fila = new Row();
				Paciente paciente = null;
				
				
				/* Verificamos si esta fuera de rango */
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", laboratorios_resultados.getCodigo_empresa());
				map.put("codigo_sucursal", laboratorios_resultados.getCodigo_sucursal());
				map.put("nro_identificacion", laboratorios_resultados.getNro_identificacion());
				map.put("codigo_orden", laboratorios_resultados.getCodigo_orden());
				boolean fuera_rango = getServiceLocator().getServicio(Resultado_laboratoriosService.class).resultadosFueraRango(map);

				if(fuera_rango){
					fila.setStyle("background-color:#FFB347");
					fila.setTooltiptext("Resultado anormal o positivo");  
				}
				
				Textbox textbox_paciente = new Textbox();
				textbox_paciente.setReadonly(true);
				textbox_paciente.setHflex("1");

				if (!bandboxPaciente.getValue().isEmpty()) {
					paciente = bandboxPaciente.getRegistroSeleccionado();
					textbox_paciente.setValue(bandboxPaciente.getValue() + "-"
							+ tbxInfoPaciente.getValue());
				} else {
					paciente = new Paciente();
					paciente.setCodigo_empresa(laboratorios_resultados
							.getCodigo_empresa());
					paciente.setCodigo_sucursal(laboratorios_resultados
							.getCodigo_sucursal());
					paciente.setNro_identificacion(laboratorios_resultados
							.getNro_identificacion());
					paciente = getServiceLocator().getPacienteService()
							.consultar(paciente);
					if (paciente != null) {
						textbox_paciente.setValue(paciente
								.getNro_identificacion()
								+ "-"
								+ paciente.getNombreCompleto());
					} else {
						textbox_paciente.setValue("Paciente no encontrado");
					}
				}

				fila.appendChild(textbox_paciente);

				Label label_fecha = new Label(
						formato_fecha.format(laboratorios_resultados
								.getFecha_realizacion()));

				fila.appendChild(label_fecha);

				Popup popup_informacion = new Popup();
				Html html = new Html();
				popup_informacion.appendChild(html);
				divPopups_informacion.appendChild(popup_informacion);

				html.setContent("Paciente: "
						+ textbox_paciente.getValue()
						+ "<br/>"
						+ "Fecha Realizacion : "
						+ formato_fecha.format(laboratorios_resultados
								.getFecha_realizacion()) + "<br />"
						+ "Lugar : " + laboratorios_resultados.getLugar());

				label_fecha.setStyle("cursor : help");
				label_fecha.setTooltip(popup_informacion);
				textbox_paciente.setStyle("cursor : help");
				textbox_paciente.setTooltip(popup_informacion);

				Hlayout hlayout = new Hlayout();
				Toolbarbutton toolbarbutton = new Toolbarbutton("Mostrar",
						"/images/mostrar_info.png");
				toolbarbutton.setTooltiptext("Mostrar laboratorio");
				toolbarbutton.setStyle("cursor:pointer");

				final Paciente paciente_aux = paciente;

				toolbarbutton.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								mostrarPDF(laboratorios_resultados,
										paciente_aux);
							}
						});

				hlayout.appendChild(toolbarbutton);

				fila.appendChild(hlayout);

				rowsLaboratorios.appendChild(fila);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void mostrarPDF(Laboratorios_resultados laboratorios_resultados,
			Paciente paciente) {
		String directorio = laboratorios_resultados.getDireccion_archivo();
		File file = new File(directorio);
		if (file.exists()) {
			LaboratoriosResultadosVisualizador resultadosVisualizador = new LaboratoriosResultadosVisualizador();
			resultadosVisualizador.setPage(getPage());
			resultadosVisualizador.setClosable(true);
			resultadosVisualizador.setMaximizable(true);
			resultadosVisualizador.mostrarPDF(file, paciente);
			resultadosVisualizador.setHeight("75%");
			resultadosVisualizador.setWidth("70%");
			resultadosVisualizador.doModal();
		} else {
			MensajesUtil.mensajeAlerta("Advertencia",
					"El resultado realizado no ha sido encontrado..");
		}
	}

	// Metodo para buscar //
	public void buscarDatosEcografias(Map<String, Object> parametros)
			throws Exception {
		//log.info("ejecutando metodo @buscarDatosEcografias");
		try {

			List<Ecografia> listado_ecografias = getServiceLocator()
					.getServicio(EcografiaService.class).listar(parametros);

			rowsEcografias.getChildren().clear();

			for (final Ecografia ecografia : listado_ecografias) {
				Row fila = new Row();
				Paciente paciente = null;

				Textbox textbox_paciente = new Textbox();
				textbox_paciente.setReadonly(true);
				textbox_paciente.setHflex("1");

				if (!bandboxPaciente.getValue().isEmpty()) {
					paciente = bandboxPaciente.getRegistroSeleccionado();
					textbox_paciente.setValue(bandboxPaciente.getValue() + "-"
							+ tbxInfoPaciente.getValue());
				} else {
					paciente = new Paciente();
					paciente.setCodigo_empresa(ecografia.getCodigo_empresa());
					paciente.setCodigo_sucursal(ecografia.getCodigo_sucursal());
					paciente.setNro_identificacion(ecografia
							.getIdentificacion());
					paciente = getServiceLocator().getPacienteService()
							.consultar(paciente);
					if (paciente != null) {
						textbox_paciente.setValue(paciente
								.getNro_identificacion()
								+ "-"
								+ paciente.getNombreCompleto());
					} else {
						textbox_paciente.setValue("Paciente no encontrado");
					}
				}

				fila.appendChild(textbox_paciente);

				Label label_fecha = new Label(formato_fecha.format(ecografia
						.getFecha_inicial()));

				fila.appendChild(label_fecha);

				Popup popup_informacion = new Popup();
				Html html = new Html();
				popup_informacion.appendChild(html);
				divPopups_informacion.appendChild(popup_informacion);

				html.setContent("Paciente: " + textbox_paciente.getValue()
						+ "<br/>" + "Fecha Realizacion : "
						+ formato_fecha.format(ecografia.getFecha_inicial()));

				label_fecha.setStyle("cursor : help");
				label_fecha.setTooltip(popup_informacion);
				textbox_paciente.setStyle("cursor : help");
				textbox_paciente.setTooltip(popup_informacion);

				Hlayout hlayout = new Hlayout();
				Toolbarbutton toolbarbutton = new Toolbarbutton("Mostrar",
						"/images/mostrar_info.png");
				toolbarbutton.setTooltiptext("Mostrar laboratorio");
				toolbarbutton.setStyle("cursor:pointer");

				final Paciente paciente_aux = paciente;

				toolbarbutton.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								mostrarEcografia(ecografia, paciente_aux);
							}
						});

				hlayout.appendChild(toolbarbutton);

				fila.appendChild(hlayout);

				rowsEcografias.appendChild(fila);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void mostrarEcografia(Ecografia ecografia, Paciente paciente) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			EcografiaAction ecografiaAction = (EcografiaAction) Executions
					.createComponents("/pages/ecografia.zul", null, parametros);
			ecografiaAction.afterCompose();
			ecografiaAction.setClosable(true);
			ecografiaAction.setMaximizable(true);
			ecografiaAction.setPage(getPage());
			ecografiaAction.setTitle(paciente != null ? paciente
					.getNombreCompleto() : "Visor de Ecografias");
			ecografiaAction.mostrarDatos(ecografia);
			ecografiaAction.setWidth("850px");
			FormularioUtil.deshabilitarComponentes(ecografiaAction, true,
					new String[] {});
			ecografiaAction.habialitarImpresion(true); 
			ecografiaAction.doModal();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {

			if (validarForm()) {
				// Cargamos los componentes //

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
//		Imagenes_diagnosticas imagenes_diagnosticas = (Imagenes_diagnosticas) obj;
//		try {
//
//		} catch (Exception e) {
//			MensajesUtil.mensajeError(e, "", this);
//		}
	}

	public void eliminarDatos(Object obj) throws Exception {
//		Imagenes_diagnosticas imagenes_diagnosticas = (Imagenes_diagnosticas) obj;
//		try {
//
//		} catch (HealthmanagerException e) {
//			MensajesUtil
//					.mensajeError(
//							e,
//							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
//							this);
//		} catch (RuntimeException r) {
//			MensajesUtil.mensajeError(r, "", this);
//		}
	}
}