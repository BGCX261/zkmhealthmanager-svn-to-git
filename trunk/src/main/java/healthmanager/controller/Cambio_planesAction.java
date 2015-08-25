/*
 * centro_atencionAction.java

 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.bean.Facturacion_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Datos_consultaService;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.Facturacion_medicamentoService;
import healthmanager.modelo.service.Facturacion_servicioService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Pacientes_contratosService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.LabelState;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.FacturacionService;

public class Cambio_planesAction extends GeneralComposer {

	@WireVariable
	private ContratosService contratosService;
	@WireVariable
	private AdministradoraService administradoraService;
	@WireVariable
	private AdmisionService admisionService;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private Pacientes_contratosService pacientes_contratosService;
	@WireVariable
	private Datos_procedimientoService datos_procedimientoService;
	@WireVariable
	private Datos_consultaService datos_consultaService;
	@WireVariable
	private Facturacion_medicamentoService facturacion_medicamentoService;
	@WireVariable
	private Facturacion_servicioService facturacion_servicioService;
	@WireVariable
	private FacturacionService facturacionService;

	@View
	private Listbox lbxContratos_aux;
	@View
	private Textbox tbxNomAdministradora_aux;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_administradora_aux;

	@View
	private Textbox tbxAdministradora;
	@View
	private Textbox tbxContrato;
	@View
	private Textbox tbxDatos_paciente;

	private Admision admision;

	@Override
	public void init() throws Exception {
		parametrizarBandboxAdminsitradora();
		Administradora administradora = new Administradora();
		administradora.setCodigo(admision.getCodigo_administradora());
		administradora.setCodigo_empresa(admision.getCodigo_empresa());
		administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
		administradora = administradoraService.consultar(administradora);
		Paciente paciente = admision.getPaciente();
		tbxAdministradora.setValue(admision.getCodigo_administradora() + " "
				+ (administradora != null ? administradora.getNombre() : ""));
		tbxDatos_paciente.setValue(admision.getNro_identificacion() + " "
				+ paciente.getNombreCompleto());
		Contratos contratos = new Contratos();
		contratos.setCodigo_empresa(admision.getCodigo_empresa());
		contratos.setCodigo_sucursal(admision.getCodigo_sucursal());
		contratos.setCodigo_administradora(admision.getCodigo_administradora());
		contratos.setId_plan(admision.getId_plan());
		contratos = contratosService.consultar(contratos);
		tbxContrato.setValue(contratos != null ? (contratos.getNro_contrato()
				+ " " + contratos.getNombre()) : admision.getId_plan());

	}

	@Override
	public void params(Map<String, Object> map) {
		admision = (Admision) map.get("ADMISION");
	}

	public void parametrizarBandboxAdminsitradora() {
		tbxCodigo_administradora_aux
				.inicializar(tbxNomAdministradora_aux, null);
		tbxCodigo_administradora_aux
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));

						String tipo_aseguradora = "";
						Listcell listcell = new Listcell();
						if (registro
								.getTipo_aseguradora()
								.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) {
							tipo_aseguradora = "PARTICULAR";
							listcell.setStyle("background-color:#96D9FA;");
						}
						listcell.appendChild(new LabelState(tipo_aseguradora,
								true));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {

						Paciente paciente = new Paciente();
						paciente.setCodigo_empresa(admision.getCodigo_empresa());
						paciente.setCodigo_sucursal(admision
								.getCodigo_sucursal());
						paciente.setNro_identificacion(admision
								.getNro_identificacion());
						paciente = pacienteService.consultar(paciente);
						admision.setPaciente(paciente);
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());

						parametros.put(
								"mostrar_particular_propia",
								paciente != null ? paciente
										.getCodigo_administradora() : "");
						parametros
								.put("tipo_particular",
										IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES);

						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return administradoraService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						Paciente paciente = admision.getPaciente();

						if (paciente == null) {
							MensajesUtil
									.mensajeAlerta("Advertencia",
											"Para realizar el cambio debe seleccionar el paciente");
							return false;
						} else {
							bandbox.setValue(registro.getCodigo());
							bandbox.setAttribute("admin", registro);
							textboxInformacion.setValue(registro.getNit() + " "
									+ registro.getNombre());
							listarContratos(paciente, registro);
							return true;
						}
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	public void listarContratos(Paciente paciente, Administradora administradora) {
		if (paciente.getCodigo_administradora().equals(
				administradora.getCodigo())) {
			lbxContratos_aux.getChildren().clear();
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("nro_identificacion",
					paciente.getNro_identificacion());
			parametros.put("codigo_administradora", administradora.getCodigo());

			List<Pacientes_contratos> listado_contratos = pacientes_contratosService
					.listar(parametros);
			lbxContratos_aux.appendItem("-- Seleccione --", "");

			for (Pacientes_contratos pacientes_contratos : listado_contratos) {
				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(codigo_empresa);
				contratos.setCodigo_sucursal(codigo_sucursal);
				contratos.setId_plan(pacientes_contratos.getId_codigo());
				contratos.setCodigo_administradora(pacientes_contratos
						.getCodigo_administradora());
				contratos = contratosService.consultar(contratos);
				lbxContratos_aux
						.appendChild(new Listitem(
								contratos != null ? (contratos
										.getNro_contrato() + " " + contratos
										.getNombre()) : pacientes_contratos
										.getId_codigo(), contratos));
			}
			lbxContratos_aux.setSelectedIndex(0);
		} else {
			Utilidades.listarContratos(lbxContratos_aux,
					administradora.getCodigo(), true, true, codigo_empresa,
					codigo_sucursal, contratosService);
		}
	}

	public void onCambiarPlanes() {
		try {
			Administradora administradora = tbxCodigo_administradora_aux
					.getRegistroSeleccionado();
			if (administradora != null) {
				Object object = lbxContratos_aux.getSelectedItem().getValue();
				if (object instanceof Contratos) {
					Contratos contratos = (Contratos) object;
					if (admision.getCodigo_administradora().equals(
							administradora.getCodigo())
							&& admision.getId_plan().equals(
									contratos.getId_plan())) {
						MensajesUtil
								.mensajeAlerta("No hay cambio de planes",
										"No se puede hacer el cambio de planes porque no se reflejan cambios");
					} else {
						Map<String, Object> parametros = new HashMap<String, Object>();
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros
								.put("nro_ingreso", admision.getNro_ingreso());
						parametros.put("nro_identificacion",
								admision.getNro_identificacion());

						List<Datos_consulta> listado_consultas = datos_consultaService
								.listarTabla(parametros);

						for (Datos_consulta datos_consulta : listado_consultas) {
							datos_consulta
									.setCodigo_administradora(administradora
											.getCodigo());
							datos_consulta.setId_plan(contratos.getId_plan());
							datos_consultaService
									.actualizarRegistro(datos_consulta);
						}

						List<Datos_procedimiento> lista_datos_cons = datos_procedimientoService
								.listarTabla(parametros);

						for (Datos_procedimiento datos_procedimiento : lista_datos_cons) {
							datos_procedimiento
									.setCodigo_administradora(administradora
											.getCodigo());
							datos_procedimiento.setId_plan(contratos
									.getId_plan());
							datos_procedimientoService
									.actualizarRegistro(datos_procedimiento);
						}

						List<Facturacion_medicamento> listado_facturaciones_med = facturacion_medicamentoService
								.listar(parametros);

						for (Facturacion_medicamento facturacion_medicamento : listado_facturaciones_med) {
							facturacion_medicamento
									.setCodigo_administradora(administradora
											.getCodigo());
							facturacion_medicamento.setId_plan(contratos
									.getId_plan());
							facturacion_medicamentoService
									.actualizar(facturacion_medicamento);
						}

						List<Facturacion_servicio> listado_facturaciones_ser = facturacion_servicioService
								.listar(parametros);

						for (Facturacion_servicio facturacion_servicio : listado_facturaciones_ser) {
							facturacion_servicio
									.setCodigo_administradora(administradora
											.getCodigo());
							facturacion_servicio.setId_plan(contratos
									.getId_plan());
							facturacion_servicioService
									.actualizar(facturacion_servicio);
						}

						Facturacion fac = new Facturacion();
						fac.setCodigo_empresa(admision.getCodigo_empresa());
						fac.setCodigo_sucursal(admision.getCodigo_sucursal());
						fac.setNro_ingreso(admision.getNro_ingreso());
						fac.setCodigo_tercero(admision.getNro_identificacion());
						fac = facturacionService.consultar_informacion(fac);

						if (fac != null) {
							fac.setCodigo_administradora(administradora
									.getCodigo());
							fac.setId_plan(contratos.getId_plan());
							facturacionService.actualizar(fac);
						}

						Admision admision_aux = admisionService
								.consultar(admision);
						if (admision_aux != null) {
							admision_aux
									.setCodigo_administradora(administradora
											.getCodigo());
							admision_aux.setId_plan(contratos.getId_plan());
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("admision", admision_aux);
							// map.put("parametros_empresa",
							// parametros_empresa);
							admisionService.actualizar(map);
						}

						admision.setCodigo_administradora(administradora
								.getCodigo());
						admision.setId_plan(contratos.getId_plan());
						MensajesUtil
								.mensajeInformacion(
										"Cambio de planes",
										"Los planes(contratos) se cambiaron satisfactoriamente",
										new EventListener<Event>() {

											@Override
											public void onEvent(Event arg0)
													throws Exception {
												onClose();
											}
										});
					}
				} else {
					MensajesUtil
							.mensajeAlerta("Seleccionar contrato",
									"Para hacer el cambio de planes debe seleccionar un contrato");
				}
			} else {
				MensajesUtil
						.mensajeAlerta("Seleccionar administradora",
								"Para hacer el cambio de planes debe seleccionar una administradora");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

}