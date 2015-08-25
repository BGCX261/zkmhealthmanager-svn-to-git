/*
 * localidadAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.His_triageService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.PrestadoresService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.softcomputo.composer.ControladorComposer;

import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.Detalle_facturaService;
import contaweb.modelo.service.FacturacionService;

public class Verificar_pacienteAction extends ControladorComposer<Window> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1626577806624050074L;

	public static final SimpleDateFormat formatFechaCompleta = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	public static final SimpleDateFormat formatFecha = new SimpleDateFormat(
			"yyyy-MM-dd");

	@WireVariable
	private GeneralExtraService generalExtraService;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private AdmisionService admisionService;
	@WireVariable
	private His_triageService his_triageService;
	@WireVariable
	private PrestadoresService prestadoresService;
	@WireVariable
	private Centro_atencionService centro_atencionService;
	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private FacturacionService facturacionService;
	@WireVariable
	private Detalle_facturaService detalle_facturaService;

	@Wire
	private Textbox tbxNro_identificacion;
	@Wire
	private Listbox lbxParametro;
	@Wire
	private Panel panelPacientes;
	@Wire
	private Listbox listboxPacientes;
	@Wire
	private Listbox listboxInformacion;
	@Wire
	private Listbox listboxRegistrosPaciente;

	@Override
	public void init() {
	}

	@Listen("onClick = #btnConsular;onOK=#tbxNro_identificacion")
	public void buscarDatos() {
		try {
			listboxPacientes.getItems().clear();
			listboxInformacion.getItems().clear();
			listboxRegistrosPaciente.getItems().clear();
			String parametro = lbxParametro.getSelectedItem().getValue()
					.toString();
			String nro_identificacion = tbxNro_identificacion.getValue().trim()
					.toUpperCase();
			if (!nro_identificacion.isEmpty()) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("limite_paginado", "limit 25 offset 0");
				if (parametro.equals("1")) {
					parametros.put("nro_identificacion", nro_identificacion);
				} else {
					parametros.put("nro_identificacion_like", "%"
							+ nro_identificacion + "%");
				}
				List<Paciente> listado_pacientes = pacienteService
						.listar(parametros);
				if (!listado_pacientes.isEmpty()) {
					verificarListadoPacientes(listado_pacientes);
				} else {
					parametros.put("documento_like", "%" + nro_identificacion
							+ "%");
					listado_pacientes = pacienteService.listar(parametros);
					if (!listado_pacientes.isEmpty()) {

					} else {
						MensajesUtil
								.mensajeAlerta("No existe paciente",
										"No se encontraron pacientes con el parametro ingresado");
					}
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this.getSelf());
		}
	}

	public void verificarListadoPacientes(List<Paciente> listado_pacientes) {
		if (listado_pacientes.size() == 1) {
			seleccionarPaciente(listado_pacientes.get(0));
		} else {
			for (Paciente paciente : listado_pacientes) {
				Listitem listitem = new Listitem();
				listitem.setValue(paciente);
				listitem.appendChild(new Listcell(paciente
						.getNro_identificacion()));
				listitem.appendChild(new Listcell(paciente.getNombreCompleto()));
				listitem.appendChild(new Listcell(formatFecha.format(paciente
						.getFecha_nacimiento())));
				listitem.appendChild(new Listcell(paciente.getSexo()));
				listboxPacientes.appendChild(listitem);
			}
			panelPacientes.setVisible(true);
		}
	}

	@Listen("onSelect = #listboxPacientes")
	public void seleccionarPaciente(Object dato) {
		Paciente paciente = (Paciente) dato;
		if (panelPacientes.isVisible()) {
			panelPacientes.setVisible(false);
		}
		Listitem listitem = new Listitem();
		listitem.setValue(paciente);
		listitem.appendChild(new Listcell(paciente.getNro_identificacion()));
		listitem.appendChild(new Listcell(paciente.getNombreCompleto()));
		listitem.appendChild(new Listcell(formatFecha.format(paciente
				.getFecha_nacimiento())));
		listitem.appendChild(new Listcell(paciente.getSexo()));
		listboxInformacion.appendChild(listitem);
		buscarRegistrosPaciente(paciente);
	}

	public void buscarRegistrosPaciente(Paciente paciente) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion", paciente.getNro_identificacion());
		List<Admision> listado_admisiones = admisionService
				.listarResultados(parametros);
		for (Admision admision : listado_admisiones) {
			Centro_atencion centro_atencion = new Centro_atencion();
			centro_atencion.setCodigo_empresa(admision.getCodigo_empresa());
			centro_atencion.setCodigo_sucursal(admision.getCodigo_sucursal());
			centro_atencion.setCodigo_centro(admision.getCodigo_centro());
			centro_atencion = centro_atencionService.consultar(centro_atencion);
			String via = admision.getElemento_via_ingreso() != null ? admision
					.getElemento_via_ingreso().getDescripcion() : admision
					.getVia_ingreso();
			String label = "Nro ingreso: " + admision.getNro_ingreso()
					+ " Via de ingreso " + via;
			Elemento elemento = new Elemento();
			elemento.setCodigo(admision.getEstado());
			elemento.setTipo("admision_estado");
			elemento = elementoService.consultar(elemento);
			if (elemento != null) {
				label = label + " Estado: " + elemento.getDescripcion();
			}
			if (admision.isFacturadas_manual()) {
				label = label + " (FACTURADA MANUAL)";
			}
			Listgroup listgroup = new Listgroup(label);
			listboxRegistrosPaciente.appendChild(listgroup);
			if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)) {
				His_triage his_triage = new His_triage();
				his_triage.setCodigo_empresa(admision.getCodigo_empresa());
				his_triage.setCodigo_sucursal(admision.getCodigo_sucursal());
				his_triage.setNro_ingreso(admision.getNro_ingreso());
				his_triage.setIdentificacion(admision.getNro_identificacion());
				his_triage = his_triageService.consultar_historia(his_triage);
				if (his_triage != null) {
					Prestadores prestadores = new Prestadores();
					prestadores.setCodigo_empresa(admision.getCodigo_empresa());
					prestadores.setCodigo_sucursal(admision
							.getCodigo_sucursal());
					prestadores.setNro_identificacion(his_triage
							.getCreacion_user());
					prestadores = prestadoresService.consultar(prestadores);
					Listitem listitem = new Listitem();
					listitem.appendChild(new Listcell("TRIAGE"));
					listitem.appendChild(new Listcell(formatFechaCompleta
							.format(his_triage.getFecha_inicial())));
					listitem.appendChild(new Listcell(admision
							.getAdministradora() != null ? (admision
							.getCodigo_administradora() + " " + admision
							.getAdministradora().getNombre()) : admision
							.getCodigo_administradora()));
					listitem.appendChild(new Listcell(
							prestadores != null ? (prestadores
									.getNro_identificacion()
									+ " "
									+ prestadores.getNombres() + " " + prestadores
									.getApellidos())
									: his_triage.getCreacion_user()));
					listitem.appendChild(new Listcell(
							centro_atencion != null ? (centro_atencion
									.getCodigo_centro() + " " + centro_atencion
									.getNombre_centro()) : admision
									.getCodigo_centro()));

					Listcell listcell = new Listcell();
					Toolbarbutton toolbarbutton = new Toolbarbutton("",
							"/images/print_ico.gif");
					listcell.appendChild(toolbarbutton);
					listitem.appendChild(listcell);

					final String codigo_historia = his_triage
							.getCodigo_historia();
					toolbarbutton.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									imprimirTriage(codigo_historia);
								}

							});

					listboxRegistrosPaciente.appendChild(listitem);
				}
			}

			if (Utilidades
					.igualConjuncion(admision.getVia_ingreso(),
							IVias_ingreso.ODONTOLOGIA2,
							IVias_ingreso.HIPERTENSO_DIABETICO,
							IVias_ingreso.HC_MENOR_2_MESES,
							IVias_ingreso.HC_MENOR_2_MESES_2_ANOS,
							IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS,
							IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A,
							IVias_ingreso.HC_DETECCION_ALT_EMBARAZO,
							IVias_ingreso.ADULTO_MAYOR,
							IVias_ingreso.ALTERACION_JOVEN,
							IVias_ingreso.PLANIFICACION_FAMILIAR,
							IVias_ingreso.PSICOLOGIA,
							IVias_ingreso.PSIQUIATRIA,
							IVias_ingreso.URGENCIA_ODONTOLOGICO,
							IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO,
							IVias_ingreso.URGENCIA,
							IVias_ingreso.CONSULTA_EXTERNA,
							IVias_ingreso.ECOGRAFIA,
							IVias_ingreso.HOSPITALIZACIONES,
							IVias_ingreso.SALUD_ORAL,
							IVias_ingreso.SERVICIOS_AMIGABLES)) {
				Historia_clinica historia_clinica = new Historia_clinica();
				historia_clinica
						.setCodigo_empresa(admision.getCodigo_empresa());
				historia_clinica.setCodigo_sucursal(admision
						.getCodigo_sucursal());
				historia_clinica.setNro_identificacion(admision
						.getNro_identificacion());
				historia_clinica.setNro_ingreso(admision.getNro_ingreso());
				historia_clinica = generalExtraService
						.consultar(historia_clinica);
				log.info("historia_clinica ===> " + historia_clinica);
				if (historia_clinica != null) {
					Prestadores prestadores = new Prestadores();
					prestadores.setCodigo_empresa(admision.getCodigo_empresa());
					prestadores.setCodigo_sucursal(admision
							.getCodigo_sucursal());
					prestadores.setNro_identificacion(historia_clinica
							.getPrestador());
					prestadores = prestadoresService.consultar(prestadores);

					Listitem listitem = new Listitem();
					listitem.appendChild(new Listcell(via));
					listitem.appendChild(new Listcell(formatFechaCompleta
							.format(historia_clinica.getFecha_historia())));
					listitem.appendChild(new Listcell(admision
							.getAdministradora() != null ? (admision
							.getCodigo_administradora() + " " + admision
							.getAdministradora().getNombre()) : admision
							.getCodigo_administradora()));
					listitem.appendChild(new Listcell(
							prestadores != null ? (prestadores
									.getNro_identificacion()
									+ " "
									+ prestadores.getNombres() + " " + prestadores
									.getApellidos())
									: historia_clinica.getPrestador()));
					listitem.appendChild(new Listcell(
							centro_atencion != null ? (centro_atencion
									.getCodigo_centro() + " " + centro_atencion
									.getNombre_centro()) : admision
									.getCodigo_centro()));

					Listcell listcell = new Listcell();
					Toolbarbutton toolbarbutton = new Toolbarbutton("",
							"/images/print_ico.gif");
					listcell.appendChild(toolbarbutton);
					listitem.appendChild(listcell);
					listboxRegistrosPaciente.appendChild(listitem);
				}
			}

			Facturacion facturacion = new Facturacion();
			facturacion.setCodigo_empresa(admision.getCodigo_empresa());
			facturacion.setCodigo_sucursal(admision.getCodigo_sucursal());
			facturacion.setNro_ingreso(admision.getNro_ingreso());
			facturacion.setCodigo_tercero(admision.getNro_identificacion());
			facturacion = facturacionService.consultar_informacion(facturacion);
			if (facturacion != null) {
				parametros.clear();
				parametros.put("id_factura", facturacion.getId_factura());
				List<Detalle_factura> listado_detalles = detalle_facturaService
						.listar(parametros);
				Listitem listitem = new Listitem();
				listitem.appendChild(new Listcell("FACTURACION"));
				listitem.appendChild(new Listcell(formatFechaCompleta
						.format(facturacion.getFecha())));
				listitem.appendChild(new Listcell(
						admision.getAdministradora() != null ? (admision
								.getCodigo_administradora() + " " + admision
								.getAdministradora().getNombre()) : admision
								.getCodigo_administradora()));
				listitem.appendChild(new Listcell(listado_detalles.size()
						+ " servicios facturados"));
				listitem.appendChild(new Listcell(
						centro_atencion != null ? (centro_atencion
								.getCodigo_centro() + " " + centro_atencion
								.getNombre_centro()) : admision
								.getCodigo_centro()));

				Listcell listcell = new Listcell();
				Toolbarbutton toolbarbutton = new Toolbarbutton("",
						"/images/print_ico.gif");
				listcell.appendChild(toolbarbutton);
				listitem.appendChild(listcell);
				listboxRegistrosPaciente.appendChild(listitem);
			} else {
				Listitem listitem = new Listitem();
				listitem.appendChild(new Listcell("NO HAY FACTURACION"));
				listitem.appendChild(new Listcell("---"));
				listitem.appendChild(new Listcell("---"));
				listitem.appendChild(new Listcell("---"));
				listitem.appendChild(new Listcell("---"));

				Listcell listcell = new Listcell();
				Toolbarbutton toolbarbutton = new Toolbarbutton("",
						"/images/print_ico.gif");
				listcell.appendChild(toolbarbutton);
				listitem.appendChild(listcell);
				listboxRegistrosPaciente.appendChild(listitem);
			}
		}
	}

	@Listen("onClick = #btnCerrar_panel")
	public void onCerrarPanel() {
		listboxPacientes.getItems().clear();
		listboxInformacion.getItems().clear();
		listboxRegistrosPaciente.getItems().clear();
		panelPacientes.setVisible(false);
	}

	public void imprimirTriage(String codigo_historia) {
		if (codigo_historia != null && !codigo_historia.isEmpty()) {
			Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", "ReporteTriage");
			paramRequest.put("codigo_historia", codigo_historia);

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", getSelf(), paramRequest);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			MensajesUtil.mensajeAlerta(
					"No hay un triage seleccionado para imprimir !!",
					"debe seleccionar un triage !!");
			return;
		}

	}

}
