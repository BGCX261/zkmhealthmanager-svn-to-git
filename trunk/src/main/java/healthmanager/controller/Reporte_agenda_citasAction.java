/*
 * agudeza_visualAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Reporte_agenda_citasAction extends ZKWindow {

	private static Logger log = Logger
			.getLogger(Reporte_agenda_citasAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxCentro_salud;

	@View
	private Textbox tbxNombre_centro;

	@View
	private Toolbarbutton btnLimpiar_centro;

	@View
	private Listbox lbxEstados;

	@View
	private Listbox lbxVia_ingreso;

	@View
	private Listbox lbxTipo_cita;

	@View
	private Listbox lbxPrestadores;

	@View
	private Textbox tbxValue;

	@View
	private Groupbox groupboxPrestadores;

	@View
	private Datebox dtbxFecha_inicial;

	@View
	private Datebox dtbxFecha_final;

	@View
	private Listbox listboxResultados;

	@Override
	public void init() {
		parametrizarBandboxCentro();
		listarEstados();
		listarTipos();
		listarVias_ingreso();
		buscarPrestadores();
		if (centro_atencion_session != null) {
			bandboxCentro_salud.seleccionarRegistro(centro_atencion_session,
					centro_atencion_session.getCodigo_centro(),
					centro_atencion_session.getNombre_centro());
		}
	}

	private void parametrizarBandboxCentro() {
		bandboxCentro_salud.inicializar(tbxNombre_centro, btnLimpiar_centro);
		bandboxCentro_salud
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getCodigo_centro() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNombre_centro() + ""));
						listitem.appendChild(listcell);

						listitem.appendChild(listcell);

					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", valorBusqueda.toLowerCase()
								.trim());

						parametros.put("limite_paginado",
								"limit 25 offset 0");

						return Reporte_agenda_citasAction.this
								.getServiceLocator()
								.getCentro_atencionService().listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Centro_atencion registro) {

						bandbox.setValue(registro.getCodigo_centro());
						textboxInformacion.setValue(registro.getNombre_centro());

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						dtbxFecha_final.setText("");
						dtbxFecha_inicial.setText("");

					}
				});
	}

	public void listarEstados() {
		lbxEstados.getItems().clear();
		List<Elemento> listado_estados = getServiceLocator()
				.getElementoService().listar("estado_cita");
		for (Elemento elemento : listado_estados) {
			Listitem listitem = new Listitem();
			listitem.appendChild(new Listcell());
			listitem.appendChild(Utilidades.obtenerListcell(
					elemento.getDescripcion(), Textbox.class, true, true));
			listitem.setValue(elemento.getCodigo());
			lbxEstados.appendChild(listitem);
		}
	}

	public void listarVias_ingreso() {
		lbxVia_ingreso.getItems().clear();
		String tipo = lbxTipo_cita.getSelectedItem().getValue().toString();
		List<Elemento> listado_estados = getServiceLocator()
				.getElementoService().listar("via_ingreso");
		for (Elemento elemento : listado_estados) {
			if (tipo.equals("01")) {
				lbxEstados.setVisible(false);
				lbxPrestadores.setVisible(false);
				groupboxPrestadores.setVisible(false);
				if (elemento.getCodigo().equals("27")
						|| elemento.getCodigo().equals("29")) {
					Listitem listitem = new Listitem();
					listitem.appendChild(new Listcell());
					listitem.appendChild(Utilidades.obtenerListcell(
							elemento.getDescripcion(), Textbox.class, true,
							true));
					listitem.setValue(elemento.getCodigo());
					lbxVia_ingreso.appendChild(listitem);
				}
			} else if (tipo.equals("02")) {
				lbxEstados.setVisible(true);
				lbxPrestadores.setVisible(true);
				groupboxPrestadores.setVisible(true);
				if (!elemento.getCodigo().equals("27")
						&& !elemento.getCodigo().equals("29")
						&& !elemento.getCodigo().equals(IVias_ingreso.URGENCIA)
						&& !elemento.getCodigo().equals(
								IVias_ingreso.HOSPITALIZACIONES)) {
					Listitem listitem = new Listitem();
					listitem.appendChild(new Listcell());
					listitem.appendChild(Utilidades.obtenerListcell(
							elemento.getDescripcion(), Textbox.class, true,
							true));
					listitem.setValue(elemento.getCodigo());
					lbxVia_ingreso.appendChild(listitem);
				}
			} else {
				lbxEstados.setVisible(false);
				lbxPrestadores.setVisible(true);
				groupboxPrestadores.setVisible(true);

				if (elemento.getCodigo().equals(IVias_ingreso.URGENCIA)
						|| elemento.getCodigo().equals(
								IVias_ingreso.HOSPITALIZACIONES)) {
					Listitem listitem = new Listitem();
					listitem.appendChild(new Listcell());
					listitem.appendChild(Utilidades.obtenerListcell(
							elemento.getDescripcion(), Textbox.class, true,
							true));
					listitem.setValue(elemento.getCodigo());
					lbxVia_ingreso.appendChild(listitem);
				}

			}

		}
	}

	public void listarTipos() {
		lbxTipo_cita.getChildren().clear();
		lbxTipo_cita.appendItem("Labotarios, Rayos X", "01");
		lbxTipo_cita.appendItem("Consultas", "02");
		lbxTipo_cita.appendItem("Urgencia - Hospitalizacion", "03");
		lbxTipo_cita.setSelectedIndex(1);
	}

	public void buscarPrestadores() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("parametroTodo", tbxValue.getValue().trim()
				.toLowerCase());

		List<Prestadores> listado_prestadores = getServiceLocator()
				.getPrestadoresService().listar(parametros);

		lbxPrestadores.getItems().clear();

		for (Prestadores prestadores : listado_prestadores) {

			Especialidad especialidad = new Especialidad();
			especialidad.setCodigo(prestadores.getCodigo_especialidad());
			especialidad = getServiceLocator().getEspecialidadService()
					.consultar(especialidad);

			Listitem listitem = new Listitem();
			listitem.appendChild(new Listcell());
			listitem.appendChild(Utilidades.obtenerListcell(
					prestadores.getNro_identificacion(), Textbox.class, true,
					true));
			listitem.appendChild(Utilidades.obtenerListcell(
					prestadores.getNombres(), Textbox.class, true, true));
			listitem.appendChild(Utilidades.obtenerListcell(
					prestadores.getApellidos(), Textbox.class, true, true));
			listitem.appendChild(Utilidades.obtenerListcell(
					especialidad.getNombre(), Textbox.class, true, true));

			listitem.setValue(prestadores);
			lbxPrestadores.appendChild(listitem);
		}

	}

	public void generarReporte() {
		try {
			if (bandboxCentro_salud.getRegistroSeleccionado() != null) {
				Date fecha_inicio = dtbxFecha_inicial.getValue();
				Date fecha_final = dtbxFecha_final.getValue();

				if (fecha_inicio != null && fecha_final != null) {
					if (fecha_inicio.compareTo(fecha_final) > 0) {
						throw new ValidacionRunTimeException(
								"La fecha inicial no puede ser mayor a la fecha final");
					}
				}

				Centro_atencion centro_atencion = bandboxCentro_salud
						.getRegistroSeleccionado();

				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", codigo_empresa);
				parametros.put("codigo_sucursal", codigo_sucursal);
				parametros.put("codigo_centro",
						centro_atencion.getCodigo_centro());

				if (fecha_inicio != null)
					parametros.put("fecha_inicio", fecha_inicio);

				if (fecha_final != null)
					parametros.put("fecha_final", fecha_final);

				if (lbxVia_ingreso.isVisible()) {
					Set<Listitem> listado_vias = lbxVia_ingreso
							.getSelectedItems();
					StringBuilder stringBuilderVias = new StringBuilder();
					for (Listitem listitem : listado_vias) {
						stringBuilderVias.append(",").append("'")
								.append(listitem.getValue().toString())
								.append("'");
					}
					if (!stringBuilderVias.toString().isEmpty()) {
						String filtro_tipo_consulta = stringBuilderVias
								.toString().substring(1,
										stringBuilderVias.toString().length());
						parametros.put("filtro_tipo_consulta", "("
								+ filtro_tipo_consulta + ")");
					}
				}

				if (lbxEstados.isVisible()) {
					Set<Listitem> listado_estados = lbxEstados
							.getSelectedItems();
					StringBuilder stringBuilderEstados = new StringBuilder();
					for (Listitem listitem : listado_estados) {
						stringBuilderEstados.append(",").append("'")
								.append(listitem.getValue().toString())
								.append("'");
					}
					if (!stringBuilderEstados.toString().isEmpty()) {
						String filtro_estados = stringBuilderEstados.toString()
								.substring(
										1,
										stringBuilderEstados.toString()
												.length());
						parametros.put("filtro_estados", "(" + filtro_estados
								+ ")");
					}
				}

				if (lbxPrestadores.isVisible()) {
					Set<Listitem> listado_prestadores = lbxPrestadores
							.getSelectedItems();
					StringBuilder stringBuilderPrestadores = new StringBuilder();
					for (Listitem listitem : listado_prestadores) {
						Prestadores prestadores = (Prestadores) listitem
								.getValue();
						stringBuilderPrestadores.append(",").append("'")
								.append(prestadores.getNro_identificacion())
								.append("'");
					}
					if (!stringBuilderPrestadores.toString().isEmpty()) {
						String filtro_prestadores = stringBuilderPrestadores
								.toString().substring(
										1,
										stringBuilderPrestadores.toString()
												.length());
						parametros.put("filtro_prestadores", "("
								+ filtro_prestadores + ")");
					}
				}

				List<Citas> lista_citas = getServiceLocator().getCitasService()
						.listar(parametros);

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"dd/MM/yyyy");

				listboxResultados.getItems().clear();
				int contador = 1;

				for (Citas citas : lista_citas) {
					Paciente paciente = new Paciente();
					paciente.setCodigo_empresa(codigo_empresa);
					paciente.setCodigo_sucursal(codigo_sucursal);
					paciente.setNro_identificacion(citas.getNro_identificacion());
					paciente = getServiceLocator().getPacienteService().consultar(paciente);
					Prestadores prestadores = null;
					if (citas.getTipo_consulta().equals(
							IVias_ingreso.LABORATORIOS)
							|| citas.getTipo_consulta().equals(
									IVias_ingreso.RAYOS_X)) {
						prestadores = null;
					} else {
						prestadores = new Prestadores();
						prestadores.setCodigo_empresa(codigo_empresa);
						prestadores.setCodigo_sucursal(codigo_sucursal);
						prestadores.setNro_identificacion(citas
								.getCodigo_prestador());
						prestadores = getServiceLocator()
								.getPrestadoresService().consultar(prestadores);
					}

					citas.setPrestadores(prestadores);

					Elemento elementoTipo_consulta = new Elemento();
					elementoTipo_consulta.setCodigo(citas.getTipo_consulta());
					elementoTipo_consulta.setTipo("via_ingreso");
					elementoTipo_consulta = getServiceLocator()
							.getElementoService().consultar(
									elementoTipo_consulta);

					citas.setElementoTipo_consulta(elementoTipo_consulta);

					Elemento elementoEstado = citas.getElementoEstado();

					Listitem listitem = new Listitem();
					listitem.appendChild(new Listcell(contador + ""));
					listitem.appendChild(Utilidades.obtenerListcell(
							simpleDateFormat.format(citas.getFecha_cita())
									+ " " + citas.getHora(), Textbox.class,
							true, true));
					listitem.appendChild(Utilidades.obtenerListcell(
							prestadores != null ? (prestadores
									.getNro_identificacion()
									+ " "
									+ prestadores.getApellidos() + " " + prestadores
									.getNombres())
									: "", Textbox.class, true, true));
					listitem.appendChild(Utilidades.obtenerListcell(
							paciente != null ? (paciente
									.getNro_identificacion() + " " + paciente
									.getNombreCompleto()) : citas
									.getNro_identificacion(), Textbox.class,
							true, true));
					listitem.appendChild(Utilidades.obtenerListcell(
							elementoEstado != null ? (elementoTipo_consulta
									.getDescripcion()) : citas.getEstado(),
							Textbox.class, true, true));
					listitem.appendChild(Utilidades
							.obtenerListcell(
									elementoTipo_consulta != null ? (elementoTipo_consulta
											.getDescripcion()) : citas
											.getTipo_consulta(), Textbox.class,
									true, true));
					listitem.setValue(citas);
					listboxResultados.appendChild(listitem);
					contador++;
				}

				parametros.put("lista_citas", lista_citas);

				imprimir(parametros);

			} else {
				MensajesUtil
						.mensajeAlerta(
								"No hay centro de salud",
								"Debe seleccionar un centro de salud para generar el reporte",
								new EventListener<Event>() {

									@Override
									public void onEvent(Event arg0)
											throws Exception {
										Clients.scrollIntoView(bandboxCentro_salud);
										bandboxCentro_salud.setFocus(true);
									}
								});
			}
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Los datos son incorrectos",
					e.getMessage());
			log.error(e);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void initReporte() throws Exception {
		try {
		} catch (Exception e) {
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void imprimir(Map<String, Object> parametros) throws Exception {

		Centro_atencion centro_atencion = bandboxCentro_salud
				.getRegistroSeleccionado();
		parametros.put("name_report", "AgendaCitasCAPS");
		parametros.put("formato", "pdf");
		parametros.put("nombre_caps",
				centro_atencion != null ? centro_atencion.getCodigo_centro()
						+ " " + centro_atencion.getNombre_centro() : "");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, parametros);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

}
