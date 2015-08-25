package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.Detalle_ordenService;
import healthmanager.modelo.service.Orden_servicioService;
import healthmanager.modelo.service.ProcedimientosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;

public class TomaMuestraAction extends ZKWindow {

	private Admision admision;

	@View
	private Listbox lbxLaboratoriosOrdenados;

	@View
	private Textbox tbxCodigo_orden;
	@View
	private Datebox dtbxFecha_orden;
	@View
	private Textbox tbxNro_ingreso;
	@View
	private Textbox tbxCodigo_paciente;
	@View
	private Textbox tbxPaciente;
	@View
	private Textbox tbxCodigo_administradora;
	@View
	private Textbox tbxCodigo_ordenador;

	@View
	private Toolbarbutton btApartarCitas;

	private Orden_servicio orden_servicio;

	private Prestadores prestadores;

	@Override
	public void init() throws Exception {
		cargarOrdenLaboratorioAdmision();
		cargarDatosPaciente();
	}

	private void cargarOrdenLaboratorioAdmision() {
		try {
			lbxLaboratoriosOrdenados.getItems().clear();
			if (admision.getCodigo_orden() != null
					&& !admision.getCodigo_orden().isEmpty()) {
				orden_servicio = new Orden_servicio();
				orden_servicio.setCodigo_empresa(admision.getCodigo_empresa());
				orden_servicio
						.setCodigo_sucursal(admision.getCodigo_sucursal());
				orden_servicio.setCodigo_orden(admision.getCodigo_orden());
				orden_servicio = getServiceLocator().getServicio(
						Orden_servicioService.class).consultar(orden_servicio);
				if (orden_servicio != null) {
					tbxCodigo_orden.setValue(orden_servicio.getCodigo_orden());
					dtbxFecha_orden.setValue(orden_servicio.getFecha_orden());

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("codigo_empresa",
							orden_servicio.getCodigo_empresa());
					map.put("codigo_sucursal",
							orden_servicio.getCodigo_sucursal());
					map.put("codigo_orden", orden_servicio.getCodigo_orden());
					List<Map<String, Object>> detalle_ordens = getServiceLocator()
							.getServicio(Detalle_ordenService.class)
							.listarLaboratoriosRegistrado(map);
					Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
							.getManuales_tarifarios(admision);
					for (Map<String, Object> mapDetalleOrden : detalle_ordens) {
						lbxLaboratoriosOrdenados.appendChild(crearFila(
								mapDetalleOrden, manuales_tarifarios));
					}
				} else {
					// log.info("No ahi orden");
					Notificaciones.mostrarNotificacionAlerta("Advertencia",
							"No hay ordenen disponible", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
				}
			} else {
				Notificaciones.mostrarNotificacionAlerta("Advertencia",
						"No hay ordenes disponible", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", admision.getCodigo_empresa());
				map.put("codigo_sucursal", admision.getCodigo_sucursal());
				map.put("id_plan", admision.getId_plan());
				map.put("nro_ingreso", admision.getNro_ingreso());
				map.put("nro_identificacion", admision.getNro_identificacion());

				List<Datos_procedimiento> listado_datos = getServiceLocator()
						.getServicio(Datos_procedimientoService.class)
						.listarTabla(map);
				for (Datos_procedimiento datos_procedimiento : listado_datos) {
					lbxLaboratoriosOrdenados
							.appendChild(crearFila2(datos_procedimiento));
				}

			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private Listitem crearFila(Map<String, Object> mapDetalleOrden,
			Manuales_tarifarios manuales_tarifarios) throws Exception {
		Listitem listitem = new Listitem();
		listitem.setValue(mapDetalleOrden);

		String realizado = (String) mapDetalleOrden.get("realizado");
		String codigo_procedimiento = (String) mapDetalleOrden
				.get("codigo_procedimiento");

		// if(manuales_tarifarios.getManual_tarifario().equals("SOAT")){
		// Map<String, Object> mapProcedimiento =
		// Utilidades.getProcedimiento(manuales_tarifarios, codigo_cups,
		// getServiceLocator());
		// // String nombre_procedimiento = (String)
		// mapProcedimiento.get("nombre_procedimiento");
		// codigo_cups = (String) mapProcedimiento.get("codigo_cups");
		// }
		//
		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(codigo_procedimiento));
		procedimientos = getServiceLocator().getServicio(
				ProcedimientosService.class).consultar(procedimientos);

		listitem.appendChild(new Listcell(codigo_procedimiento));
		listitem.appendChild(new Listcell(
				procedimientos != null ? procedimientos.getDescripcion() : ""));

		if (realizado.equals("S")) {
			listitem.setStyle("background-color:#f0f0f0");
			listitem.setTooltiptext("Toma de muestra completada");
			listitem.setDisabled(true);
			listitem.setSelected(true);
		}
		return listitem;
	}

	private Listitem crearFila2(Datos_procedimiento datos_procedimiento)
			throws Exception {
		Listitem listitem = new Listitem();
		listitem.setValue(datos_procedimiento);

		String realizado = "*";
		String codigo_procedimiento = datos_procedimiento
				.getCodigo_procedimiento();

		//
		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(codigo_procedimiento));
		procedimientos = getServiceLocator().getServicio(
				ProcedimientosService.class).consultar(procedimientos);

		listitem.appendChild(new Listcell(codigo_procedimiento));
		listitem.appendChild(new Listcell(
				procedimientos != null ? procedimientos.getDescripcion() : ""));

		if (realizado.equals("S")) {
			listitem.setStyle("background-color:#f0f0f0");
			listitem.setTooltiptext("Toma de muestra completada");
			listitem.setDisabled(true);
			listitem.setSelected(true);
		}
		return listitem;
	}

	private void cargarDatosPaciente() {
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(admision.getCodigo_empresa());
		paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
		paciente.setNro_identificacion(admision.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		tbxNro_ingreso.setValue(admision.getNro_ingreso());
		if (paciente != null) {
			tbxCodigo_paciente.setText(paciente.getTipo_identificacion() + "-"
					+ paciente.getNro_identificacion());
			tbxPaciente.setText(paciente.getNombreCompleto());

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(admision.getCodigo_empresa());
			administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
			administradora.setCodigo(admision.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			if (administradora != null) {
				tbxCodigo_administradora.setValue(administradora.getCodigo()
						+ " " + administradora.getNombre());
			}

			if (orden_servicio != null) {
				prestadores = new Prestadores();
				prestadores.setCodigo_empresa(admision.getCodigo_empresa());
				prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
				prestadores.setNro_identificacion(orden_servicio
						.getCodigo_ordenador());
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);
				if (prestadores != null) {
					tbxCodigo_ordenador.setValue(prestadores
							.getNro_identificacion()
							+ "-"
							+ prestadores.getApellidos()
							+ " "
							+ prestadores.getNombres());
				}
			}
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
		if (admision == null) {
			MensajesUtil.mensajeAlerta("Advertencia", "La admision no valida");
			onClose();
		} else {
			if (admision.getCodigo_orden() == null) {
				MensajesUtil.mensajeAlerta("Advertencia",
						"La admision no es valida, no tiene orden asignada");
				// onClose();
			} else if (admision.getCodigo_orden().trim().isEmpty()) {
				MensajesUtil.mensajeAlerta("Advertencia",
						"La admision no es valida, no tiene orden asignada");
				// onClose();
			}
		}
	}

	public void guardarDatos() {
		try {
			if (validarForm()) {
				if (orden_servicio != null) {
					getServiceLocator().getDetalle_ordenService()
							.actualizarEstado(getListadoLaboratorios(),
									admision);
				} else {
					getServiceLocator().getDetalle_ordenService()
							.actualizarEstado(
									new ArrayList<Map<String, Object>>(),
									admision);
				}

				if (parametros_empresa.getApartar_cita_toma_laboratorios()
						.equals("S")) {
					Messagebox
							.show("Los datos se guardaron satisfactoriamente. Desea dar una cita al paciente? ",
									"impresion",
									Messagebox.YES + Messagebox.NO,
									Messagebox.QUESTION,
									new org.zkoss.zk.ui.event.EventListener<Event>() {
										public void onEvent(Event event)
												throws Exception {
											if ("onYes".equals(event.getName())) {
												abrirModuloCitas();
												btApartarCitas.setVisible(true);
											}
										}
									});
				} else {
					MensajesUtil.mensajeInformacion("Informacion",
							"Los datos se guardaron satisfactoriamente");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private List<Map<String, Object>> getListadoLaboratorios() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Set<Listitem> listitems = lbxLaboratoriosOrdenados.getSelectedItems();
		for (Listitem listitem : listitems) {
			list.add((Map<String, Object>) listitem.getValue());
		}
		return list;
	}

	public void abrirModuloCitas() {
		Admision admisionOrden = new Admision();
		admisionOrden.setCodigo_empresa(admision.getCodigo_empresa());
		admisionOrden.setCodigo_sucursal(admision.getCodigo_sucursal());
		admisionOrden.setNro_ingreso(orden_servicio.getNro_ingreso());
		admisionOrden.setNro_identificacion(admision.getNro_identificacion());
		admisionOrden = getServiceLocator().getAdmisionService().consultar(
				admisionOrden);

		if (admisionOrden != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("admision", admisionOrden);
			map.put("prestador", prestadores);
			Citas_corregidoAction citas_corregidoAction = (Citas_corregidoAction) Executions
					.createComponents("/pages/cita_corregido.zul", this, map);
			citas_corregidoAction.setWidth("100%");
			citas_corregidoAction.setHeight("100%");
			citas_corregidoAction.doModal();
		} else {
			MensajesUtil.mensajeAlerta("Advertencia",
					"La admision a con la que envio la orden no encontrada nro ingreso: "
							+ orden_servicio.getNro_ingreso());
		}
	}

	private boolean validarForm() {
		boolean valido = true;
		String msj = "";
		Set<Listitem> listitems = lbxLaboratoriosOrdenados.getSelectedItems();
		if (listitems.isEmpty()) {
			msj = "Para realizar esta opcion debe por lo menos seleccionar un laboratorio";
			valido = false;
		}
		if (!valido) {
			MensajesUtil.mensajeAlerta("Advertencia", msj);
		}

		return valido;
	}
}
