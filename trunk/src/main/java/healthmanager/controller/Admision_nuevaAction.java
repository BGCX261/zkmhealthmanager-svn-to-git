package healthmanager.controller;

import healthmanager.controller.Furips2Action.OnAccionFurips;
import healthmanager.controller.SeleccionadorAction.OnSeleccionador;
import healthmanager.controller.SeleccionadorAdministradoraAction.OnSeleccionadorAseguradorasAccion;
import healthmanager.controller.complemento.Admisiones_abiertasAction;
import healthmanager.controller.complemento.VentanaCancelacion;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Admision_cama;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Cama;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_barrio;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Furips2;
import healthmanager.modelo.bean.Habitacion;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Pabellon;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Pyp;
import healthmanager.modelo.bean.Recibo_caja;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.bean.Via_ingreso_especialidad;
import healthmanager.modelo.bean.Via_ingreso_rol;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Admision_camaService;
import healthmanager.modelo.service.Afiliaciones_meService;
import healthmanager.modelo.service.Anio_cuota_moderadoraService;
import healthmanager.modelo.service.Aportes_cotizacionesService;
import healthmanager.modelo.service.BarrioService;
import healthmanager.modelo.service.CamaService;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.EspecialidadService;
import healthmanager.modelo.service.Furips2Service;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.HabitacionService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.PabellonService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Pacientes_contratosService;
import healthmanager.modelo.service.Plan_tratamientoService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Recibo_cajaService;
import healthmanager.modelo.service.Registro_admisionService;
import healthmanager.modelo.service.Salario_anualService;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IEstados;
import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.OnGuardar;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.macros.util.Parametros_busqueda;
import com.framework.res.LabelState;
import com.framework.util.FormularioUtil;
import com.framework.util.ManejadorPoblacion;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesComponentes;

import contaweb.modelo.service.CajaService;
import contaweb.modelo.service.PagareService;

public class Admision_nuevaAction extends GeneralComposer {

	private static final long serialVersionUID = -4397059836559280520L;

	public static Logger log = Logger.getLogger(Admision_nuevaAction.class);

	@WireVariable
	private Impresion_diagnosticaService impresion_diagnosticaService;
	@WireVariable
	private Configuracion_admision_ingresoService configuracion_admision_ingresoService;
	@WireVariable
	private AdministradoraService administradoraService;
	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private AdmisionService admisionService;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private ContratosService contratosService;
	@WireVariable
	private PrestadoresService prestadoresService;
	@WireVariable
	private Centro_atencionService centro_atencionService;
	@WireVariable
	private BarrioService barrioService;
	@WireVariable
	private CitasService citasService;
	@WireVariable
	private DepartamentosService departamentosService;
	@WireVariable
	private MunicipiosService municipiosService;
	@WireVariable
	private Pacientes_contratosService pacientes_contratosService;
	@WireVariable
	private CajaService cajaService;
	@WireVariable
	private PagareService pagareService;
	@WireVariable
	private ProcedimientosService procedimientosService;
	@WireVariable
	private Plan_tratamientoService plan_tratamientoService;
	@WireVariable
	private Registro_admisionService registro_admisionService;
	@WireVariable
	private Admision_camaService admision_camaService;
	@WireVariable
	private CamaService camaService;
	@WireVariable
	private HabitacionService habitacionService;
	@WireVariable
	private PabellonService pabellonService;
	@WireVariable
	private Furips2Service furips2Service;
	@WireVariable
	private Afiliaciones_meService afiliaciones_meService;
	@WireVariable
	private Salario_anualService salario_anualService;
	@WireVariable
	private Aportes_cotizacionesService aportes_cotizacionesService;
	@WireVariable
	private Anio_cuota_moderadoraService anio_cuota_moderadoraService;
	@WireVariable
	private EspecialidadService especialidadService;
	@WireVariable
	private GeneralExtraService generalExtraService;
	@WireVariable
	private Recibo_cajaService recibo_cajaService;

	private List<Contratos> listado_contratos_pacientes = new ArrayList<Contratos>();

	@View
	private Toolbarbutton btGuardar;
	@View
	private Listbox lbxTipo_admision;
	@View
	private Listbox lbxMarca_admision;
	@View
	private Listbox lbxParameter;
	@View
	private Listbox lbxVias_ingreso;
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
	@View
	private Listbox lbxVia_ingreso_defecto;
	@View
	private Listbox lbxFiltro_estado;
	@View
	private Listbox lbxFiltro_atendidas;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_identificacion;
	@View
	private Textbox tbxNro_ingreso;
	@View
	private Textbox tbxNomPaciente;
	@View
	private Toolbarbutton btnLimpiarPaciente;
	@View
	private Textbox tbxNomAdministradora;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_administradora;
	@View
	private Textbox tbxDetalle_plan;
	@View
	private Textbox tbxNacimiento;
	@View
	private Textbox tbxEdad;
	@View
	private Textbox tbxSexo;
	@View
	private Textbox tbxTipo_identificacion;
	@View
	private Textbox tbxEstrato;
	@View
	private Textbox tbxDireccion;
	@View
	private Textbox tbxTel;
	@View
	private Textbox tbxDpto;
	@View
	private Textbox tbxMun;
	@View
	private Textbox tbxTipo_afiliado;
	@View
	private Listbox lbxVia_ingreso;
	@View
	private Label lbCausa_externa;
	@View
	private Datebox dtbxFecha_ingreso;
	@View
	private Textbox tbxNro_autorizacion;
	@View
	private Label lbCama;
	@View
	private Cell cellCama;
	@View
	private Listbox lbxTipo_atencion;
	@View
	private Listbox lbxProcedencia;
	@View
	private Textbox tbxCama;
	@View
	private Textbox tbxNomCama;
	@View
	private Textbox tbxCodigo_atencion;
	@View
	private Textbox tbxCodigo_pabellon;
	@View
	private Textbox tbxCodigo_habitacion;
	@View
	private Toolbarbutton btnLimpiarCama;
	@View
	private Listbox lbxCodigo_cita;
	@View
	private Listbox lbxCodigo_especialidad;
	@View
	private Checkbox chbUrgencias;
	@View
	private Checkbox chbHospitalizacion;
	@View
	private Checkbox chbRecien_nacido;
	@View
	private Listbox lbxCondicion_usuaria;
	@View
	private Listbox lbxCausa_externa;
	@View
	private Listbox lbxTipo_diagnostico;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxDiagnostico_ingreso;
	@View
	private Textbox tbxNomDx;
	@View
	private Toolbarbutton btnLimpiarDx;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_medico;
	@View
	private Label lbPrestador;
	@View
	private Textbox tbxNomPrestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;
	@View
	private Listbox lbxTipo_discapacidad;
	@View
	private Listbox lbxGrado_discapacidad;
	@View
	private Row rowCitaEspecialidad;
	@View
	private Checkbox chbAplicar_triage;
	@View
	private Checkbox chbAplicar_tuberculosis;
	@View
	private Checkbox chbAplicar_lepra;
	@View
	private Radiogroup rdbPaciente_remitido;
	@View
	private Hlayout hlayoutRemitido;
	@View
	private Row rowProcedencia;
	@View
	private Row rowCheckboxsRips;
	@View
	private Row rowMarca_admision;
	@View
	private Toolbarbutton imgConsultar_cama;
	@View
	private Toolbarbutton btnRecibo_caja;
	@View
	private Toolbarbutton btFurips;
	@View
	private Toolbarbutton btnProcedimientos;
	@View
	private Toolbarbutton btnMedicamentos;
	@View
	private Popup popupViasIngreso;
	@View
	private Popup popupCentros_atencion;
	@View
	private Toolbarbutton btEditar;
	@View
	private Toolbarbutton btnFiltro_ingreso;
	@View
	private Label lbTipo_psicologia;
	@View
	private Listbox lbxTipo_psicologia;
	@View
	private Hlayout hlayoutProcedencia;
	@View
	private Row rowTipo_atencion;
	@View
	private Listbox lbxContratos;
	@View
	private Row rowProgramaPyp;
	@View
	private Listbox lbxPrograma_pyp;
	@View
	private Bandbox bandboxBuscar_centros;
	@View
	private Listbox lbxCentros_atencion;
	@View
	private Toolbarbutton btnFiltro_centros;
	@View
	private Datebox dtbxFecha_busqueda;

	private Procedimientos_admisionAction procedimientos_admisionAction;

	private Medicamentos_admisionAction medicamentos_admisionAction;

	private String tipo_cita = "";

	private Configuracion_admision_ingreso configuracion_admision_ingreso;

	private Contratos contratos;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	private Admision admision_current;

	private final String[] idsExcluyentes = { "btCancel", "btGuardar",
			"tbxAccion", "btNew", "dtbxFecha_ingreso",
			"lbxVia_ingreso_defecto", };

	@Override
	public void init() throws Exception {
		listarCombos();
		parametrizarBandbox();
		accionForm(true, "registrar");
		cargarEventos();
		listarCentros();
	}

	private void cargarEventos() {
		lbxCausa_externa.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onSeleccionarCausaExterna();
					}
				});
	}

	private void onSeleccionarCausaExterna() {
		final String causa_externa = lbxCausa_externa.getSelectedItem()
				.getValue();
		if (causa_externa
				.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRABAJO)
				|| causa_externa
						.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRANSITO)
				|| causa_externa
						.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_EVENTO_CATASTROFICO)) {
			// pedimos seleccion de administradora, dependiendo
			// si es soat o arl
			Messagebox
					.show("Para esta causa externa se debe solicitar un tipo especifico de administradora. ¿Esta seguro que desea solicitarla? ",
							"Solicitar administradora de tipo especifico",
							Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event event)
										throws Exception {
									if ("onYes".equals(event.getName())) {
										solicitarAseguradora(causa_externa);
									} else {
										lbxCausa_externa.setSelectedIndex(0);
									}
								}
							});
		}

	}

	private void parametrizarAdministradora() {
		tbxCodigo_administradora.inicializar(tbxNomAdministradora, null);
		tbxCodigo_administradora
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

						Paciente paciente = tbxNro_identificacion
								.getRegistroSeleccionado();

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

						// parametros.put("tercerizada",
						// chkSubcontratacion.isChecked() ? "S" : "N");
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return administradoraService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						Paciente paciente = tbxNro_identificacion
								.getRegistroSeleccionado();
						if (paciente == null) {
							MensajesUtil
									.mensajeAlerta("Advertencia",
											"Para realizar el cambio debe seleccionar el paciente");
							return false;
						} else {
							bandbox.setValue(registro.getCodigo());
							textboxInformacion.setValue(registro.getNit() + " "
									+ registro.getNombre());

							// cambiar prestador
							cambiarAdministradora(paciente, registro);
							btnProcedimientos.setVisible(false);
							btnMedicamentos.setVisible(false);
							return true;
						}
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	/**
	 * Este metodo me permite realizar el cambio de administradora siempre y
	 * cuando sea una ARL o una SOAT
	 *
	 */
	protected void solicitarAseguradora(String causa_externa) {
		OnSeleccionadorAseguradorasAccion seleccionadorAseguradorasAccion = new OnSeleccionadorAseguradorasAccion() {
			@Override
			public void onSeleccionAseguradora(Administradora administradora) {
				// seleccionarAdministradora(administradora, false);
			}

			@Override
			public void onCancelar() {
				lbxCausa_externa.setSelectedIndex(0);
				// Administradora administradora = (Administradora)
				// tbxNro_identificacion
				// .getAttribute("admin");
				// seleccionarAdministradora(administradora, true);
			}
		};

		Map<String, Object> param = new HashMap<String, Object>();
		param.put(SeleccionadorAdministradoraAction._TIPO_ASEGURADORA,
				causa_externa);
		param.put(
				SeleccionadorAdministradoraAction._EVENTO_SELECCIONAR_ASEGURADORA,
				seleccionadorAseguradorasAccion);
		Executions.createComponents("/pages/seleccionar_administradora.zul",
				this, param);
	}

	// public void seleccionarAdministradora(Administradora administradora,
	// boolean propia) {
	// Paciente paciente = (Paciente) tbxNro_identificacion
	// .getAttribute("paciente");
	// cambiarAdministradora(paciente, paciente.getCodigo_empresa(),
	// paciente.getCodigo_sucursal(), administradora.getCodigo(),
	// propia);
	// }
	public void listarCombos() {
		listarParameter();
		cargarVias(lbxVias_ingreso);
		UtilidadesComponentes.listarElementosListbox(true, false,
				elementoService, lbxTipo_admision, lbxTipo_atencion,
				lbxProcedencia, lbxCondicion_usuaria, lbxCausa_externa,
				lbxTipo_discapacidad, lbxGrado_discapacidad,
				lbxVia_ingreso_defecto);

		Utilidades.listarEspecialidades(lbxCodigo_especialidad, true,
				especialidadService);

		UtilidadesComponentes.listarElementosListbox(false, false,
				elementoService, lbxMarca_admision, lbxTipo_diagnostico,
				lbxFiltro_atendidas, lbxFiltro_estado);

		validarLepraTuberculosis();
		if (rol_usuario.equals("15")) {
			lbxVia_ingreso.appendItem("Urgencias", "1");
			lbxVia_ingreso.appendItem("Hospitalizaciones", "26");
			lbxVia_ingreso.setSelectedIndex(0);
			seleccionarVia_ingreso(false, false);
		}
	}

	private void cargarVias(Listbox listbox) {
		Listitem listitem;
		String tipo = listbox.getName();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo);
		parametros.put("orden_descripcion", "orden_descripcion");

		List<Elemento> elementos = elementoService.listar(parametros);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
	}

	private void validarLepraTuberculosis() {
		Boolean lepra = false;
		Boolean tuberculosis = false;
		if (tbxNro_identificacion.getValue() != null
				&& !tbxNro_identificacion.getValue().isEmpty()) {
			lepra = aplicaLepra(tbxNro_identificacion.getValue());
			tuberculosis = aplicaTuberculosis(tbxNro_identificacion.getValue());
		}

		for (Listitem litem : lbxVia_ingreso.getItems()) {
			if (litem.getValue().equals("35") && !lepra) {
				lbxVia_ingreso.getItems().remove(litem);
			}
			if (litem.getValue().equals("34") && !tuberculosis) {
				lbxVia_ingreso.getItems().remove(litem);
			}
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre1||' '||nombre2");
		listitem.setLabel("Nombres");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("apellido1||' '||apellido2");
		listitem.setLabel("Apellidos");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_ingreso");
		listitem.setLabel("Nro ingreso");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_medico");
		listitem.setLabel("Codigo prestador");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombres_prestador||apellidos_prestador");
		listitem.setLabel("Prestador");
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
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		admision_current = null;
		lbxPrograma_pyp.setDisabled(false);
		listarCitasPendientesPaciente(null, true);
		tbxDiagnostico_ingreso.setValue("");
		tbxNomDx.setValue("");

		tbxCodigo_medico.limpiarSeleccion(true);

		tbxNro_identificacion.setDisabled(false);
		btnLimpiarPaciente.setVisible(true);
		lbxCodigo_cita.setDisabled(false);
		lbxVia_ingreso.setDisabled(true);
		tbxNro_identificacion.setReadonly(false);
		tbxNro_identificacion.setFocus(true);
		tbxNro_identificacion.limpiarSeleccion(true);

		lbxCondicion_usuaria.setSelectedIndex(0);

		seleccionarCita();
		seleccionarTipo_atencion();
		rowCitaEspecialidad.setVisible(false);
		btFurips.setVisible(false);
		btnProcedimientos.setVisible(false);
		btnMedicamentos.setVisible(false);
		limpiarOrdenProcedimiento();
		btnRecibo_caja.setVisible(false);
		lbxTipo_psicologia.setSelectedIndex(0);
		lbxContratos.getChildren().clear();
		tbxCodigo_administradora.limpiarSeleccion(true);
		tbxCodigo_administradora.setButtonVisible(false);
		tbxNro_ingreso.setValue("");
		tbxNro_autorizacion.setValue("");
		btGuardar.setDisabled(false);
	}

	private void limpiarOrdenProcedimiento() {
		if (procedimientos_admisionAction != null) {
			procedimientos_admisionAction.detach();
			procedimientos_admisionAction = null;
		}
	}

	private String cargarDX(String codigo_dx) {
		Cie cie = new Cie();
		cie.setCodigo(codigo_dx);
		cie = generalExtraService.consultar(cie);

		return (cie != null ? cie.getNombre() : "");
	}

	/**
	 * En este metodo se parametriza todos los bandbox
	 *
	 */
	private void parametrizarBandbox() {
		parametrizarBandboxPaciente();
		parametrizarBandboxCama();
		parametrizarBandboxPrestador();
		parametrizarBandboxDx();
		parametrizarAdministradora();
		parametrizarResultadoPaginado();
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Admision> listarResultados(
					Map<String, Object> parametros) {
				List<Admision> listado = admisionService
						.listarResultados(parametros);
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = admisionService.totalResultados(parametros);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilas(dato, gridResultado);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado, 10);
	}

	private void parametrizarBandboxPaciente() {
		tbxNro_identificacion.setTrabajarConParametros(true);
		tbxNro_identificacion.inicializar(tbxNomPaciente, btnLimpiarPaciente);
		tbxNro_identificacion.setMostrarVacio(true);
		tbxNro_identificacion
				.setMensajeVacio("Este paciente no se encuentra en la base de datos..!!");
		tbxNro_identificacion
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

						parametros.put("limite_paginado", "limit 25 offset 0");

						return pacienteService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						// Esta validacion aplica cuando trabaja como la Caja
						if (getSucursal().getTipo().equals(
								IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
							if (!registro.getEstado_afiliacion().equals(
									IConstantes.ESTADO_AFILIACION_ACTIVO)) {
								String descripcion = Utilidades.getDescripcionElemento(
										registro.getEstado_afiliacion(),
										"estado_afiliacion", elementoService);
								MensajesUtil.mensajeAlerta("Advertencia",
										"Este paciente no puede ser atendido tiene estado "
												+ descripcion);
								return false;
							}
						}

						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro
								.getNombreCompleto());

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						borrarDatosPaciente();
						btEditar.setVisible(false);
						tbxDetalle_plan.setValue("");
						lbxContratos.getChildren().clear();
						listarCitasPendientesPaciente(null, true);
						lbxVia_ingreso.setDisabled(true);
						lbxCondicion_usuaria.setSelectedIndex(0);
						tbxCodigo_administradora.limpiarSeleccion(true);
						if (procedimientos_admisionAction != null) {
							procedimientos_admisionAction.limpiarDatos();
						}
						if (medicamentos_admisionAction != null) {
							medicamentos_admisionAction.limpiarDatos();
						}
						tbxCodigo_administradora.setButtonVisible(false);
						tbxCodigo_administradora.limpiarSeleccion(false);
					}
				});

		tbxNro_identificacion
				.setSeleccionarItem(new BandboxRegistrosIMG.ISeleccionarItem() {
					public void accion(Object registro) {
						seleccionarPaciente((Paciente) registro);
					}
				});
		List<Parametros_busqueda> listado_parametros = new ArrayList<Parametros_busqueda>();
		listado_parametros.add(new Parametros_busqueda("Documento aux",
				"documento"));
		listado_parametros.add(new Parametros_busqueda("Nro identificacion",
				"nro_identificacion"));
		listado_parametros.add(new Parametros_busqueda("Primer nombre",
				"nombre1"));
		listado_parametros.add(new Parametros_busqueda("Segundo nombre",
				"nombre2"));
		listado_parametros.add(new Parametros_busqueda("Primer apellido",
				"apellido1"));
		listado_parametros.add(new Parametros_busqueda("Segundo apellido",
				"apellido2"));
		tbxNro_identificacion.listarParametros(listado_parametros, true);
	}

	public void seleccionarPaciente(Paciente paciente) {
		Map<String, Object> paramAdmision = new HashMap<String, Object>();
		paramAdmision.put("codigo_empresa", codigo_empresa);
		paramAdmision.put("codigo_sucursal", codigo_sucursal);
		paramAdmision.put("nro_identificacion",
				paciente.getNro_identificacion());
		paramAdmision.put("order", "fecha_ingreso desc");
		paramAdmision.put("estado", "1");

		List<Admision> listado_admisiones = admisionService
				.listarTabla(paramAdmision);

		if (listado_admisiones.isEmpty()) {
			seleccionarPacienteAux(paciente);
		} else {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("paciente", paciente);
			parametros.put("listado_admisiones", listado_admisiones);
			parametros.put("modos", Admisiones_abiertasAction.Modos.ADMISION);
			Window ventana = (Window) Executions.createComponents(
					"/pages/complemento/admisiones_abiertas.zul", this,
					parametros);
			ventana.doModal();
		}

	}

	public void seleccionarPacienteAux(Paciente paciente) {
		if (procedimientos_admisionAction != null) {
			procedimientos_admisionAction.limpiarDatos();
		}
		tbxNro_ingreso.setText("");
		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(paciente.getCodigo_empresa());
		administradora.setCodigo_sucursal(paciente.getCodigo_sucursal());
		administradora.setCodigo(paciente.getCodigo_administradora());
		administradora = administradoraService.consultar(administradora);
		tbxCodigo_administradora.setValue(paciente.getCodigo_administradora());

		if (administradora != null) {
			tbxCodigo_administradora.seleccionarRegistro(administradora,
					administradora.getCodigo(), administradora.getNombre());
			tbxCodigo_administradora.setButtonVisible(true);
		}
		cambiarAdministradora(paciente, administradora);
		btEditar.setVisible(true);

		ServiciosDisponiblesUtils.validarInformacionPaciente(paciente);
		if (parametros_empresa != null
				&& parametros_empresa.getGestion_informacion().equals("S")) {
			if (validarDatosPaciente(paciente)) {
				modificarPaciente(paciente);
			} else {
				cargarDesdeSeleccionPaciente(paciente);
			}
		} else {
			cargarDesdeSeleccionPaciente(paciente);
		}
		Utilidades.validacionFechasFuturas(dtbxFecha_ingreso);
		rowCitaEspecialidad.setVisible(true);
		lbxVia_ingreso.setFocus(true);
	}

	public void cambiarAdministradora(Paciente paciente,
			Administradora administradora) {
		listado_contratos_pacientes.clear();
		try {
			if (isParticularAtencion(administradora)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", paciente.getCodigo_empresa());
				map.put("codigo_sucursal", paciente.getCodigo_sucursal());
				map.put("codigo_administradora", administradora.getCodigo());
				map.put("cerrado", false);
				List<Contratos> contratoslistado = contratosService.listar(map);
				if (contratoslistado.isEmpty()) {
					throw new ValidacionRunTimeException(
							"Esta aseguradora no tiene contrato configurado");
				} else if (contratoslistado.size() == 1) {
					setContrato(contratoslistado.get(0));
				} else {
					seleccionarContrato(contratoslistado);
				}
			} else {
				cambiarPacientesContratos(paciente, administradora.getCodigo());
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private void parametrizarBandboxCama() {

	}

	private void parametrizarBandboxPrestador() {
		tbxCodigo_medico.inicializar(tbxNomPrestador, btnLimpiarPrestador);
		tbxCodigo_medico
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Map<String, Object>>() {

					@Override
					public void renderListitem(Listitem listitem,
							Map<String, Object> registro) {

						// Extraemos valores
						String nro_identificacion = (String) registro
								.get("nro_identificacion");
						String nombres = (String) registro.get("nombres");
						String apellidos = (String) registro.get("apellidos");
						Integer citas_del_dia = (Integer) registro
								.get("citas_del_dia");
						Integer citas_asignadas = (Integer) registro
								.get("citas_asignadas");
						Integer citas_pendientes = (Integer) registro
								.get("citas_pendientes");
						String tipo_prestador = (String) registro
								.get("tipo_prestador");

						// Mostramos valores en vista
						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(nro_identificacion));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(nombres + " "
								+ apellidos));
						listitem.appendChild(listcell);

						String nombre_medico = tipo_prestador.equals("01") ? "médico"
								: "ENFERMERA";
						if (tipo_prestador.equals("03")) {
							nombre_medico = "VACUNADOR";
						} else if (!tipo_prestador.equals("01")
								&& !tipo_prestador.equals("02")) {
							nombre_medico = "PRESTADOR";
						}
						String via_ingreso = lbxVia_ingreso.getSelectedItem()
								.getValue();
						if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)
								|| via_ingreso
										.equals(IVias_ingreso.ODONTOLOGIA2)
								|| via_ingreso
										.equals(IVias_ingreso.URGENCIA_ODONTOLOGICO)
								|| via_ingreso.equals(IVias_ingreso.SALUD_ORAL)) {
							nombre_medico = "ODONTOLOGO";
						}

						listcell = new Listcell();
						listcell.appendChild(new Label(nombre_medico));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(
								citas_del_dia != null ? citas_del_dia + ""
										: "0"));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(
								citas_asignadas != null ? citas_asignadas + ""
										: "0"));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(
								citas_pendientes != null ? citas_pendientes
										+ "" : "0"));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Map<String, Object>> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						String via_ingreso = lbxVia_ingreso.getSelectedItem()
								.getValue().toString();
						if (!via_ingreso.isEmpty()) {
							parametros.put("paramTodo", "");
							parametros.put("value", "%"
									+ valorBusqueda.toUpperCase().trim() + "%");
							parametros.put("codigo_empresa",
									sucursal.getCodigo_empresa());
							parametros.put("codigo_sucursal",
									sucursal.getCodigo_sucursal());
							parametros.put("fecha_solicitada", new Timestamp(
									dtbxFecha_ingreso.getValue().getTime()));

							parametros.put("rol", getRolViaIngreso());
							// Esta validacion se va a utilizar cuando sea
							// vacunacion
							if (!via_ingreso
									.equals(IVias_ingreso.VIA_VACUNACION)) {
								parametros
										.put("codigo_centro_dh",
												centro_atencion_session != null ? centro_atencion_session
														.getCodigo_centro()
														: IConstantes.CENTRO_ATENCION_CUALQUIERA);
								String tipo_prestador = habilitarFiltroEnfermera();
								if (!tipo_prestador.equals("00")) {
									parametros.put("tipo_prestador",
											tipo_prestador);
								}
							}
							parametros.put("limite_paginado",
									"limit 25 offset 0");

							// //log.info(">>>>>>>>>>>>>" + parametros);
							return prestadoresService
									.listarPrestadoresPorRolCentro(parametros);
						} else {
							return new ArrayList<Map<String, Object>>();
						}
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion,
							Map<String, Object> registro) {

						String nro_identificacion = (String) registro
								.get("nro_identificacion");
						String nombre = (registro.get("nombres") + " " + registro
								.get("apellidos"));

						bandbox.setValue(nro_identificacion);
						textboxInformacion.setValue(nombre);

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
					}

				});
	}

	private List<Via_ingreso_rol> getRolViaIngreso() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codigo_via_ingreso", lbxVia_ingreso.getSelectedItem()
				.getValue().toString());
		param.put("codigo_empresa", codigo_empresa);
		// //log.info("Map de vias ingreso: " + param);
		return generalExtraService.listar(Via_ingreso_rol.class, param);
	}

	private void parametrizarBandboxDx() {
		tbxDiagnostico_ingreso.inicializar(tbxNomDx, btnLimpiarDx);

		BandboxRegistrosIMG<Cie> bandboxRegistrosIMG = new BandboxRegistrosIMG<Cie>() {

			@Override
			public void renderListitem(Listitem listitem, Cie registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getSexo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getLimite_inferior()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getLimite_superior()));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Cie> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");

				parametros.put("limite_paginado", "limit 25 offset 0");

				return generalExtraService.listar(Cie.class, parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Cie registro) {
				bandbox.setValue(registro.getCodigo());
				textboxInformacion.setValue(registro.getNombre());
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
			}

		};

		tbxDiagnostico_ingreso.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	@SuppressWarnings("unused")
	private String getCodigoCentro(Paciente paciente) {
		if (paciente != null) {
			Adicional_paciente adicional_paciente = new Adicional_paciente();
			adicional_paciente.setCodigo_empresa(codigo_empresa);
			adicional_paciente.setCodigo_sucursal(codigo_sucursal);
			adicional_paciente.setNro_identificacion(paciente
					.getNro_identificacion());
			adicional_paciente = generalExtraService
					.consultar(adicional_paciente);
			if (adicional_paciente != null) {
				Barrio barrio = new Barrio();
				barrio.setCodigo_barrio(adicional_paciente.getCodigo_barrio());
				barrio = barrioService.consultar(barrio);
				// //log.info("barrio: " + barrio);

				Centro_barrio centro_barrio = new Centro_barrio();
				centro_barrio.setCodigo_barrio(barrio != null ? barrio
						.getCodigo_barrio() : "");
				centro_barrio = generalExtraService.consultar(centro_barrio);

				Centro_atencion centro_atencion = new Centro_atencion();
				centro_atencion.setCodigo_empresa(paciente.getCodigo_empresa());
				centro_atencion.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				centro_atencion
						.setCodigo_centro((centro_barrio != null ? centro_barrio
								.getCodigo_centro() : ""));
				centro_atencion = centro_atencionService
						.consultar(centro_atencion);
				return (centro_atencion != null ? centro_atencion
						.getCodigo_centro() : "");
			} else {
				return "110";
			}
		} else {
			return "";
		}
	}

	public void listarCitasPendientesPaciente(Paciente paciente, boolean select) {
		lbxCodigo_cita.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			lbxCodigo_cita.appendChild(listitem);
		}

		if (paciente != null) {
			// String codigo_centro = getCodigoCentro(paciente);
			String codigo_centro = centro_atencion_session != null ? centro_atencion_session
					.getCodigo_centro() : "110";

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", codigo_empresa);
			map.put("codigo_sucursal", codigo_sucursal);
			map.put("nro_identificacion",
					(paciente != null ? paciente.getNro_identificacion() : ""));
			map.put("estado", "1");
			map.put("codigo_centro", codigo_centro);
			// //log.info("Mapa de consulta: " + map);
			List<Citas> lista_citas = citasService.listar(map);
			// //log.info("Listado de citas: " + lista_citas.size());
			for (Citas citas : lista_citas) {

				Elemento elementoTipo_consulta = new Elemento();
				elementoTipo_consulta.setCodigo(citas.getTipo_consulta());
				elementoTipo_consulta.setTipo("via_ingreso");
				elementoTipo_consulta = elementoService
						.consultar(elementoTipo_consulta);

				String tipoConsulta = elementoTipo_consulta != null ? elementoTipo_consulta
						.getDescripcion() : "";

				listitem = new Listitem();
				listitem.setValue(citas.getCodigo_cita());
				listitem.setLabel(new java.text.SimpleDateFormat("yyyy-MM-dd")
						.format(citas.getFecha_cita())
						+ " "
						+ citas.getHora()
						+ " - " + tipoConsulta);
				lbxCodigo_cita.appendChild(listitem);
			}
		}
		if (lbxCodigo_cita.getItemCount() > 0) {
			lbxCodigo_cita.setSelectedIndex(0);
		}
	}

	public List<Citas> citasPendientesPaciente(Paciente paciente,
			String via_ingreso) {

		String codigo_centro = centro_atencion_session != null ? centro_atencion_session
				.getCodigo_centro() : "110";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("nro_identificacion",
				(paciente != null ? paciente.getNro_identificacion() : ""));
		map.put("estado", "1");
		map.put("codigo_centro", codigo_centro);
		map.put("tipo_consulta", (via_ingreso != null ? via_ingreso : ""));
		// //log.info("Mapa de consulta: " + map);

		List<Citas> lista_citas = citasService.listar(map);
		// //log.info("Listado de citas: " + lista_citas.size());

		return lista_citas;
	}

	public void cargarDatosPaciente(Paciente paciente) {
		Departamentos departamentos = new Departamentos();
		departamentos.setCodigo((paciente != null ? paciente.getCodigo_dpto()
				: ""));
		departamentos = departamentosService.consultar(departamentos);

		Municipios municipios = new Municipios();
		municipios
				.setCoddep((paciente != null ? paciente.getCodigo_dpto() : ""));
		municipios.setCodigo((paciente != null ? paciente.getCodigo_municipio()
				: ""));
		municipios = municipiosService.consultar(municipios);

		Elemento elementoTipo_af = new Elemento();
		elementoTipo_af.setTipo("tipo_afiliado");
		elementoTipo_af.setCodigo((paciente != null ? paciente
				.getTipo_afiliado() : ""));
		elementoTipo_af = elementoService.consultar(elementoTipo_af);

		tbxNacimiento
				.setValue((paciente != null ? new java.text.SimpleDateFormat(
						"dd/MM/yyyy").format(paciente.getFecha_nacimiento())
						: ""));
		// tbxEdad.setValue((paciente != null ? Util.getEdad(
		// new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente
		// .getFecha_nacimiento()), "1", true) : ""));

		tbxEdad.setValue(Util.getEdadPrsentacionSimple(
				paciente.getFecha_nacimiento(),
				new Timestamp(new Date().getTime())));

		tbxSexo.setValue((paciente != null ? Utilidades.getNombreElemento(
				paciente.getSexo(), "sexo", elementoService) : ""));
		tbxTipo_identificacion.setValue((paciente != null ? paciente
				.getTipo_identificacion() : ""));
		tbxEstrato.setValue((paciente != null ? paciente.getEstrato() : ""));

		tbxDireccion
				.setValue((paciente != null ? paciente.getDireccion() : ""));

		tbxTel.setValue((paciente != null ? paciente.getTel_res() : ""));

		tbxDpto.setValue((departamentos != null ? departamentos.getNombre()
				: ""));
		tbxMun.setValue((municipios != null ? municipios.getNombre() : ""));

		// //log.info("Paciente:" + paciente);
		if (paciente != null) {
			int edad = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"1", false));

			int edad_meses = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"2", false));

			int edad_dia = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"3", false));

			if (edad_dia > 1 || edad_meses >= 1 || edad < 18) {
				tbxTipo_afiliado.setValue("Beneficiario");
			}
			if (edad >= 18) {
				tbxTipo_afiliado
						.setValue((elementoTipo_af != null ? elementoTipo_af
								.getDescripcion() : ""));
			}
		}
	}

	public void cambiarPacientesContratos(Paciente paciente,
			String codigo_administradora) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("nro_identificacion", paciente.getNro_identificacion());
		parametros.put("codigo_administradora", codigo_administradora);

		List<Pacientes_contratos> listado_contratos = pacientes_contratosService
				.listar(parametros);

		StringBuilder stringBuilder = new StringBuilder();

		for (Pacientes_contratos pacientes_contratos : listado_contratos) {
			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(codigo_empresa);
			contratos.setCodigo_sucursal(codigo_sucursal);
			contratos.setCodigo_administradora(pacientes_contratos
					.getCodigo_administradora());
			contratos.setId_plan(pacientes_contratos.getId_codigo());
			contratos = contratosService.consultar(contratos);
			if (contratos != null) {
				stringBuilder.append("Contrato : ").append(
						contratos.getNro_contrato() + " - "
								+ contratos.getNombre());
				if (contratos.getObservacion() != null
						&& !contratos.getObservacion().isEmpty()) {
					stringBuilder.append("\tObservaciones : "
							+ contratos.getObservacion());
				}
				stringBuilder.append("\n");
				listado_contratos_pacientes.add(contratos);
			}
		}
		lbxContratos.getChildren().clear();
		tbxDetalle_plan.setValue(stringBuilder.toString());
		tbxDetalle_plan.setRows(listado_contratos.size() + 1);
	}

	/**
	 * TODO agregar el cambio de contrato
	 *
	 */
	public void cambiarAdministradora(Paciente paciente, String codigo_empresa,
			String codigo_sucursal, String codigo_administradora) {
		try {
			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(codigo_empresa);
			administradora.setCodigo_sucursal(codigo_sucursal);
			administradora.setCodigo(codigo_administradora);
			administradora = administradoraService.consultar(administradora);
			tbxCodigo_administradora.seleccionarRegistro(administradora,
					administradora.getCodigo(), administradora.getNombre());
			cambiarAdministradora(paciente, administradora);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private void solicitarFurips() {
		String causa_externa = lbxCausa_externa.getSelectedItem().getValue();
		if (causa_externa.equals(IConstantes.CAUSA_EXTERNA_ACCIDENTE_TRANSITO)
				|| causa_externa
						.equals(IConstantes.CAUSA_EXTERNA_EVENTO_CATASTROFICO)) {
			mostrarFurips(null);
		}
	}

	private void mostrarFurips(Furips2 furips2) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Furips2Action.PARAM_NIVEL,
				Furips2Action.REGISTRO_DESDE_ADMISION);
		param.put(Furips2Action.PARAM_PACIENTE,
				tbxNro_identificacion.getRegistroSeleccionado());
		param.put(Furips2Action.PARAM_FURIPS, furips2);
		param.put(Furips2Action.PARAM_CAUSA_EXTERNA, lbxCausa_externa
				.getSelectedItem().getValue());
		param.put(Furips2Action.PARAM_MEDICO,
				tbxCodigo_medico.getRegistroSeleccionado());
		param.put(Furips2Action.PARAM_EVENTO, new OnAccionFurips() {
			@Override
			public void cerrar() {
				lbxCausa_externa.setSelectedIndex(0);
				onSeleccionarCausaExterna();
				tbxNro_identificacion.setAttribute(IConstantes.LLAVE_FURIPS,
						null);
				btFurips.setVisible(false);
			}

			@Override
			public void onAgregar(Furips2 furips2) {
				cargarFurips(furips2);
			}
		});
		Window window = (Window) Executions.createComponents(
				"/pages/furips2.zul", this, param);
		window.setTitle("FURIPS");
		if (furips2 != null) {
			window.setClosable(true);
		}
		window.setWidth("840px");
		window.doModal();
	}

	/**
	 * Este metodo me permite notificar que esta admision tiene un furips
	 *
	 * @author Luis Miguel
	 *
	 */
	protected void cargarFurips(Furips2 furips2) {
		tbxNro_identificacion.setAttribute(IConstantes.LLAVE_FURIPS, furips2);
		btFurips.setVisible(true);
		Clients.showNotification(
				"Para abrir el furips presione aqui por favor",
				Clients.NOTIFICATION_TYPE_INFO, btFurips, "after_center", 3000);
	}

	/**
	 * TODO: abrir furips
	 *
	 */
	public void abrirFurips() {
		Furips2 furips2 = (Furips2) tbxNro_identificacion
				.getAttribute(IConstantes.LLAVE_FURIPS);
		mostrarFurips(furips2);
	}

	private void seleccionarContrato(final List<Contratos> contratoslistado) {
		SeleccionadorAction seleccionadorAction = (SeleccionadorAction) Executions
				.createComponents("/pages/seleccionador.zul", this, null);
		OnSeleccionador<Contratos> onSeleccionador = new OnSeleccionador<Contratos>() {

			@Override
			public void onSeleccionar(Contratos contratos) {
				setContrato(contratos);
				solicitarFurips();
			}

			@Override
			public String getTitulo() {
				return "SELECCIONE CONTRATO";
			}

			@Override
			public Listhead getListhead() {
				Listhead listhead = new Listhead();
				listhead.appendChild(new Listheader("Nro contrato"));
				listhead.appendChild(new Listheader("Nombre contrato"));
				return listhead;
			}

			@Override
			public List<Contratos> getListado() {
				return contratoslistado;
			}

			@Override
			public void agregar(Listitem listitem) {
				Contratos contratos = listitem.getValue();
				listitem.appendChild(new Listcell(contratos.getNro_contrato()));
				listitem.appendChild(new Listcell(contratos.getNombre()));
			}
		};
		seleccionadorAction.setOnSeleccionar(onSeleccionador);
		seleccionadorAction.doModal();
	}

	private void setContrato(Contratos contratos) {
		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();
		StringBuilder stringBuilder = new StringBuilder();
		if (contratos != null) {
			stringBuilder.append("Contrato : ")
					.append(contratos.getNro_contrato() + " - "
							+ contratos.getNombre());
			if (contratos.getObservacion() != null
					&& !contratos.getObservacion().isEmpty()) {
				stringBuilder.append("\tObservaciones : "
						+ contratos.getObservacion());
			}
			stringBuilder.append("\n");
			listado_contratos_pacientes.add(contratos);
		}
		tbxDetalle_plan.setValue(stringBuilder.toString());
		tbxDetalle_plan.setRows(2);

		Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();

		ServiciosDisponiblesUtils.validarTipoViaIngresoAdmisionNuevo(
				lbxVia_ingreso, paciente,
				administradora != null ? administradora.getCodigo() : "",
				false, listado_contratos_pacientes);

		lbxContratos.getChildren().clear();
	}

	public void borrarDatosPaciente() {
		tbxNacimiento.setValue("");
		tbxEdad.setValue("");
		tbxSexo.setValue("");
		tbxTipo_identificacion.setValue("");
		tbxEstrato.setValue("");

		tbxDireccion.setValue("");
		tbxTel.setValue("");

		tbxDpto.setValue("");
		tbxMun.setValue("");
		tbxEstrato.setValue("");
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			if (!value.isEmpty()) {
				if (Utilidades.igualConjuncion(parameter, "nro_ingreso",
						"nro_identificacion", "codigo_medico",
						"fecha_ingreso_string")) {
					parameters.put(parameter, value);
				} else {
					parameters.put("parameter", parameter);
					parameters.put("value", "" + value + "%");
				}
			}
			parameters.put("order_defecto", "_order_defecto");
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			if (dtbxFecha_busqueda.getValue() != null) {
				parameters.put("fecha_ingreso", dtbxFecha_busqueda.getValue());
			}

			String atendida = lbxFiltro_atendidas.getSelectedItem().getValue();
			if (!atendida.equalsIgnoreCase("0")) {
				parameters.put("atendida", atendida.equalsIgnoreCase("2"));
			}

			String tipo_admision = lbxTipo_admision.getSelectedItem()
					.getValue().toString();

			if (!tipo_admision.isEmpty()) {
				if (tipo_admision.equalsIgnoreCase("0")) {
					parameters.put("marca_admision", "001");
				} else if (tipo_admision.equalsIgnoreCase("1")) {
					parameters.put("marca_admision", "002");
				} else {
					parameters.put("marca_admision", "003");
				}
			}

			String estado = lbxFiltro_estado.getSelectedItem().getValue();
			if (!estado.equalsIgnoreCase("0")) {
				parameters.put("estado", estado);
			}

			if (lbxVias_ingreso.getSelectedItems().size() > 0) {
				List<String> listado_vias = new ArrayList<String>();
				for (Listitem listitem : lbxVias_ingreso.getSelectedItems()) {
					listado_vias.add((String) listitem.getValue());
				}
				btnFiltro_ingreso.setImage("/images/filtro1.png");
				parameters.put("vias_ingreso", listado_vias);
			} else {
				btnFiltro_ingreso.setImage("/images/filtro.png");
			}

			List<String> listado_centros = new ArrayList<String>();
			if (!lbxCentros_atencion.getSelectedItems().isEmpty()) {
				for (Listitem listitem : lbxCentros_atencion.getSelectedItems()) {
					Centro_atencion centro_atencion = (Centro_atencion) listitem
							.getValue();
					listado_centros.add(centro_atencion.getCodigo_centro());
				}
				btnFiltro_centros.setImage("/images/filtro1.png");
				parameters.put("listado_centros", listado_centros);
			} else {
				btnFiltro_centros.setImage("/images/filtro.png");
			}
			popupViasIngreso.close();
			popupCentros_atencion.close();
			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Admision admision = (Admision) objeto;

		Paciente paciente = admision.getPaciente();
		Prestadores prestador = admision.getPrestadores();

		String estado = "";
		if (!admision.getAtendida()) {
			estado = "Pendiente";
		} else {
			estado = "Atendida";
		}

		String fact = "";
		if (admision.getEstado().equals(IEstados.ADMISION_ACTIVA)) {
			fact = "Activo";
		} else if (admision.getEstado().equals(IEstados.ADMISION_FACTURADA)) {
			fact = "Facturada";
		} else if (admision.getEstado().equals(IEstados.ADMISION_CANCELADA)) {
			fact = "Cancelada";
		}

		String marca = "";
		String codigo_marca = admision.getMarca_admision() != null ? admision
				.getMarca_admision() : "";
		if (!codigo_marca.isEmpty()) {
			if (codigo_marca.equalsIgnoreCase("001")) {
				marca = "Ninguna";
			} else if (admision.getMarca_admision().equalsIgnoreCase("002")) {
				marca = "Autorizacion";
			} else {
				marca = "Certificacion";
			}
		}

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(admision.getNro_ingreso() + ""));
		fila.appendChild(new Label(admision.getNro_identificacion() + ""));
		fila.appendChild(new Label((paciente != null ? paciente
				.getNombreCompleto() : "")));
		fila.appendChild(new Label((prestador != null ? prestador.getNombres()
				+ " " + prestador.getApellidos() : "")));

		Elemento via = new Elemento();
		via.setTipo("via_ingreso");
		via.setCodigo(admision.getVia_ingreso());
		via = elementoService.consultar(via);

		String via_string = (via != null ? via.getDescripcion() : "");

		fila.appendChild(new Label(via_string));
		fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd HH:mm")
				.format(admision.getFecha_ingreso())));
		fila.appendChild(new Label(marca));
		fila.appendChild(new Label(estado));
		final Label label_fact = new Label(fact);
		fila.appendChild(label_fact);
		hbox.appendChild(space);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/editar.gif");
		toolbarbutton.setTooltiptext("Editar");
		toolbarbutton.setStyle("cursor: pointer");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(admision, true);
					}
				});
		hbox.appendChild(toolbarbutton);
		hbox.appendChild(new Space());

		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/editar_.png");
		toolbarbutton.setTooltiptext("Editar Planes");
		toolbarbutton.setStyle("cursor: pointer");
		toolbarbutton.setDisabled(admision.getEstado().equals(
				IEstados.ADMISION_CANCELADA));
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarVentanaCambioPlanes(admision);
					}
				});
		hbox.appendChild(toolbarbutton);
		hbox.appendChild(new Space());

		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/cancelar_mini.png");
		toolbarbutton.setTooltiptext("Cancelar");
		toolbarbutton.setStyle("cursor: pointer");
		toolbarbutton.setDisabled(admision.getEstado().equals(
				IEstados.ADMISION_CANCELADA));
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						cancelarAdmision(admision, arg0.getTarget(), label_fact);
					}
				});
		hbox.appendChild(toolbarbutton);

		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		toolbarbutton.setTooltiptext("Eliminar");
		toolbarbutton.setStyle("cursor: pointer");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("Esta seguro que desea eliminar este registro? ",
										"Eliminar Registro",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													// do the thing
													eliminarDatos(admision);
													buscarDatos();
												}
											}
										});
					}
				});
		hbox.appendChild(space);
		hbox.appendChild(toolbarbutton);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para validar campos del formulario //
	public Map<String, Object> validarForm() throws Exception {
		tbxNro_identificacion
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_administradora
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_medico
				.setStyle("text-transform:uppercase;background-color:white");
		lbxVia_ingreso
				.setStyle("text-transform:uppercase;background-color:#white");
		lbxCodigo_cita
				.setStyle("text-transform:uppercase;background-color:#white");

		String mensaje = "Los campos marcados con (*) son obligatorios";
		boolean generar_caja = false;

		boolean valida = true;
		if (tbxNro_identificacion.getText().trim().equals("")) {
			tbxNro_identificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();

		if (administradora == null) {
			tbxCodigo_administradora
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		/* Esta validacion es para cuando no pueda acceder a ningun servicio */
		if (lbxVia_ingreso.getSelectedItem() == null
				|| !lbxVia_ingreso.getSelectedItem().isVisible()
				|| lbxVia_ingreso.getSelectedItem().getValue().toString()
						.isEmpty()) {
			lbxVia_ingreso
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida) {
			if (configuracion_admision_ingreso == null) {
				valida = false;
				mensaje = "La via de ingreso "
						+ lbxVia_ingreso.getSelectedItem().getLabel()
						+ " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta";
			} else if (contratos == null) {
				valida = false;
				mensaje = "Para guardar los Datos se debe seleccionar un contrato valido";
				Clients.scrollIntoView(lbxContratos);
				MensajesUtil.notificarAlerta("Seleccionar contrato",
						lbxContratos);
			}
		}

		if (valida && configuracion_admision_ingreso.getSolicitar_prestador()) {
			if (tbxCodigo_medico.getText().trim().equals("")) {
				tbxCodigo_medico
						.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				valida = false;
			}
		}

		if (valida) {
			if (configuracion_admision_ingreso.getObligatorio_cita()) {
				if (lbxCodigo_cita.getSelectedItem().getValue().toString()
						.equals("")) {
					valida = false;
					mensaje = "Para admitir a este paciente es de carácter obligatorio registrar una cita.";
					lbxCodigo_cita
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				}
			} else {
				if (!lbxCodigo_cita.getSelectedItem().getValue().toString()
						.equals("")) {
					String codigo_cita = lbxCodigo_cita.getSelectedItem()
							.getValue().toString();

					Citas citas = new Citas();
					citas.setCodigo_empresa(codigo_empresa);
					citas.setCodigo_sucursal(codigo_sucursal);
					citas.setCodigo_cita(codigo_cita);
					citas = citasService.consultar(citas);
					if (citas == null) {
						valida = false;
						mensaje = "Registro de cita " + codigo_cita
								+ " no se encuentre en base de datos";
					} else {
						Map<String, Object> result = validarTipo_consultaVia_ingreso(citas);
						if (!((Boolean) result.get("success"))) {
							valida = false;
							mensaje = (String) result.get("msg");
						}
					}
				}
			}
		}

		if (contratos != null) {
			if (contratos.getAutorizacion_obligatoria()) {
				if (contratos.getVias_ingreso_obligatorias().isEmpty()) {
					if (tbxNro_autorizacion.getValue().trim().isEmpty()) {
						valida = false;
						mensaje = "Para este contrato la autorizacion es obligatoria. Diligenciar el Nro de autorizacion para continuar el proceso";
					}
				} else {
					String via_ingreso = lbxVia_ingreso.getSelectedItem()
							.getValue().toString();
					if (contratos.getVias_ingreso_obligatorias().contains(
							"(" + via_ingreso + ")")) {
						if (tbxNro_autorizacion.getValue().trim().isEmpty()) {
							valida = false;
							mensaje = "Para este contrato la autorizacion es obligatoria. Diligenciar el Nro de autorizacion para continuar el proceso";
						}
					}
				}
			}
		}

		if (valida
				&& lbxVia_ingreso.getSelectedItem().getValue()
						.equals(IVias_ingreso.PSICOLOGIA)) {
			if (lbxTipo_psicologia.getSelectedItem().getValue().toString()
					.isEmpty()) {
				MensajesUtil
						.mensajeAlerta(usuarios.getNombres()
								+ " recuerde que...",
								"Debe seleccionar el tipo de psicologia para este paciente");
				MensajesUtil.notificarAlerta(
						"Debe seleccionar el tipo de psicologia",
						lbxTipo_psicologia);
				valida = false;
			}
		}

		boolean isParticular = isParticularAtencion(administradora);

		// solicitar procedimientos
		if (valida && isParticular) {
			String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue()
					.toString();
			if (via_ingreso.equals(IVias_ingreso.MEDICAMENTOS_PYP)
					|| via_ingreso
							.equals(IVias_ingreso.MEDICAMENTOS_TUBERCULOSIS)) {
				if (medicamentos_admisionAction != null) {
					List<Map<String, Object>> detalle_medicamentos = medicamentos_admisionAction
							.getListadoMedicamentos();
					if (detalle_medicamentos.isEmpty()) {
						valida = false;
						mensaje = "Para esto debe seleccionar que medicamentos de "
								+ lbxVia_ingreso.getSelectedItem().getLabel()
								+ ", va a facturar";
						medicamentos_admisionAction.setVisible(true);
					} else {
						generar_caja = true;
					}
				} else {
					valida = false;
					mensaje = "Es de caracter obligatorio, cargar medicamentos para esta via de ingreso. Ya que en el caso que aplique seran necesarios para generar recibos de caja";
				}
			} else {
				if (procedimientos_admisionAction != null) {
					List<Map<String, Object>> detalle_ordens = procedimientos_admisionAction
							.getListadoProcedimientos();
					if (detalle_ordens.isEmpty()) {
						valida = false;
						mensaje = "Para esto debe seleccionar que servicios de "
								+ lbxVia_ingreso.getSelectedItem().getLabel()
								+ ", va a facturar";
						procedimientos_admisionAction.setVisible(true);
					} else {
						generar_caja = true;
					}
				} else if (!via_ingreso.equals(IVias_ingreso.URGENCIA)
						&& !via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES)) {
					valida = false;
					mensaje = "Es de caracter obligatorio, cargar procedimientos para esta via de ingreso. Ya que en el caso que aplique seran necesarios para generar recibos de caja";
				}
			}

		}

		Map<String, Object> mapa_resultado = new HashMap<String, Object>();
		mapa_resultado.put("valida", valida);
		mapa_resultado.put("generar_caja", generar_caja);
		if (!valida) {
			MensajesUtil.mensajeAlerta("Recuerde que", mensaje);
		} else {
			tbxNro_identificacion
					.setStyle("text-transform:uppercase;background-color:white");
			tbxCodigo_administradora
					.setStyle("text-transform:uppercase;background-color:white");
			// lbxId_plan.setStyle("background-color:white");
			tbxCodigo_medico
					.setStyle("text-transform:uppercase;background-color:white");
			tbxDiagnostico_ingreso
					.setStyle("text-transform:uppercase;background-color:white");
		}

		return mapa_resultado;
	}

	// Metodo para guardar la informacion //
	public boolean guardarDatos() throws Exception {
		try {
			Map<String, Object> mapa_resultado = validarForm();
			boolean valida = (Boolean) mapa_resultado.get("valida");
			boolean generar_caja = (Boolean) mapa_resultado.get("generar_caja");
			if (valida) {
				if (!generar_caja) {
					procesoDeGuardado();
				} else {
					generarReciboCaja(false);
					// procesoDeGuardado();
				}
			}
			return valida;
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
			return false;
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
			return false;
		}
	}

	public Admision procesoDeGuardado() throws Exception {
		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();
		FormularioUtil.setUpperCase(groupboxEditar);
		// Cargamos los componentes //
		String codigo_cie = "Z000";
		Admision admision = new Admision();
		admision.setCodigo_empresa(empresa.getCodigo_empresa());
		admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		admision.setNro_ingreso(tbxNro_ingreso.getValue());
		admision.setNro_identificacion(tbxNro_identificacion.getValue());

		if (admision_current == null) {
			admision = new Admision();
			admision.setCodigo_empresa(empresa.getCodigo_empresa());
			admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			admision.setNro_ingreso(tbxNro_ingreso.getValue());
			admision.setNro_identificacion(tbxNro_identificacion.getValue());
			admision.setEstado("1");
			admision.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			admision.setAtendida(false);
		} else {
			admision = admision_current;
		}
		admision.setCodigo_administradora(tbxCodigo_administradora.getValue());

		if (contratos != null) {
			admision.setId_plan(contratos.getId_plan());
		}

		admision.setCodigo_medico(tbxCodigo_medico.getValue());

		admision.setFecha_ingreso(new Timestamp(dtbxFecha_ingreso.getValue()
				.getTime()));
		admision.setNro_autorizacion(tbxNro_autorizacion.getValue());
		admision.setVia_ingreso(lbxVia_ingreso.getSelectedItem().getValue()
				.toString());
		admision.setTipo_atencion(lbxTipo_atencion.getSelectedItem().getValue()
				.toString());
		admision.setMarca_admision(lbxMarca_admision.getSelectedItem()
				.getValue().toString());
		admision.setMotivo_cancelacion("");
		admision.setProcedencia(lbxProcedencia.getSelectedItem().getValue()
				.toString());
		admision.setCodigo_especialidad(lbxCodigo_especialidad
				.getSelectedItem().getValue().toString());
		admision.setCama(tbxCama.getValue());
		admision.setIngreso_programa("N");
		admision.setPrimera_vez("N");
		admision.setCondicion_usuaria(lbxCondicion_usuaria.getSelectedItem()
				.getValue().toString());
		admision.setCausa_externa(lbxCausa_externa.getSelectedItem().getValue()
				.toString());
		admision.setTipo_diagnostico(lbxTipo_diagnostico.getSelectedItem()
				.getValue().toString());
		admision.setDiagnostico_ingreso(codigo_cie);
		admision.setTipo_discapacidad(lbxTipo_discapacidad.getSelectedItem()
				.getValue().toString());
		admision.setGrado_discapacidad(lbxGrado_discapacidad.getSelectedItem()
				.getValue().toString());
		admision.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		admision.setCreacion_user(usuarios.getCodigo().toString());
		admision.setUltimo_user(usuarios.getCodigo().toString());
		admision.setUrgencias(chbUrgencias.isChecked() ? "S" : "N");
		admision.setHospitalizacion(chbHospitalizacion.isChecked() ? "S" : "N");
		admision.setRecien_nacido(chbRecien_nacido.isChecked() ? "S" : "N");

		admision.setCodigo_cita(lbxCodigo_cita.getSelectedItem().getValue()
				.toString());

		admision.setAplica_triage(chbAplicar_triage.isChecked());
		admision.setAplica_tuberculosis(chbAplicar_tuberculosis.isChecked());
		admision.setRealizo_triage(false);
		admision.setAplica_lepra(chbAplicar_lepra.isChecked());
		admision.setPaciente_remitido(rdbPaciente_remitido.getSelectedItem()
				.getValue().toString());
		admision.setTipo_consulta("");
		admision.setTipo_adicional_via_ingreso("");
		admision.setAdmision_parto("N");

		admision.setCodigo_centro(centro_atencion_session.getCodigo_centro());
		admision.setPaciente((Paciente) tbxNro_identificacion
				.getRegistroSeleccionado());

		admision.setParticular(administradora != null
				&& administradora
						.getTipo_aseguradora()
						.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES) ? "S"
				: "N");

		if (lbxTipo_psicologia.getSelectedItem().getValue()
				.equals(IVias_ingreso.PSICOLOGIA)) {
			admision.setTipo_psicologia(lbxTipo_psicologia.getSelectedItem()
					.getValue().toString());
		} else {
			admision.setTipo_psicologia("");
		}
		admision.setPaciente((Paciente) tbxNro_identificacion
				.getRegistroSeleccionado());

		admision.setPrograma_lab_pyp(configuracion_admision_ingreso
				.getLaboratorio_pyp() ? lbxPrograma_pyp.getSelectedItem()
				.getValue().toString() : "");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("admision", admision);
		map.put("accion", tbxAccion.getText());
		map.put("codigo_atencion", tbxCodigo_atencion.getValue());
		map.put("codigo_pabellon", tbxCodigo_pabellon.getValue());
		map.put("codigo_habitacion", tbxCodigo_habitacion.getValue());
		map.put("listado_contratos", listado_contratos_pacientes);
		map.put("contratos", contratos);
		map.put(IConstantes.LLAVE_FURIPS,
				tbxNro_identificacion.getAttribute(IConstantes.LLAVE_FURIPS));

		map.put("empresa", empresa);
		map.put("administradora",
				tbxCodigo_administradora.getRegistroSeleccionado());
		map.put("dtt_servicios",
				procedimientos_admisionAction != null ? procedimientos_admisionAction
						.getListadoProcedimientos() : null);

		map.put("dtt_medicamentos",
				medicamentos_admisionAction != null ? medicamentos_admisionAction
						.getListadoMedicamentos() : null);
		map.put("parametros_empresa", parametros_empresa);

		final Admision auxAdmision = admisionService.guardar(map);

		if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {

		}

		mostrarDatos(auxAdmision, false);

		final Admision admision_aux = admision;

		Messagebox.show("Los datos se guardaron satisfactoriamente.!!! ",
				"Informacion", Messagebox.OK, Messagebox.INFORMATION,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						validarFechaIngreso(admision_aux);
					}

				});
		return admision;

	}

	public void generarReciboCaja(boolean actualizacion) {
		OnGuardar onGuardar = new OnGuardar() {

			@Override
			public Object onGuardar(Object object) throws Exception {
				return procesoDeGuardado();
			}
		};
		String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue()
				.toString();
		List<Map<String, Object>> listado_datos;
		if (via_ingreso.equals(IVias_ingreso.MEDICAMENTOS_PYP)
				|| via_ingreso.equals(IVias_ingreso.MEDICAMENTOS_TUBERCULOSIS)) {
			listado_datos = medicamentos_admisionAction
					.getListadoMedicamentos();
		} else {
			listado_datos = procedimientos_admisionAction
					.getListadoProcedimientos();
		}
		for (Map<String, Object> mapa : listado_datos) {
			mapa.put("via_ingreso", via_ingreso);
			if (via_ingreso.equals(IVias_ingreso.MEDICAMENTOS_PYP)
					|| via_ingreso
							.equals(IVias_ingreso.MEDICAMENTOS_TUBERCULOSIS)) {
				mapa.put("tipo_registro", "MEDICAMENTO");
			} else {
				mapa.put("tipo_registro", "PROCEDIMIENTO");
			}
		}

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("paciente",
				tbxNro_identificacion.getRegistroSeleccionado());
		parametros.put("administradora",
				tbxCodigo_administradora.getRegistroSeleccionado());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("actualizacion", actualizacion);
		parametros.put("listado_datos", listado_datos);
		parametros.put("via_ingreso", via_ingreso);
		parametros.put("accion_form", "registrar");
		parametros.put("onGuardar", onGuardar);
		Window window = (Window) Executions.createComponents(
				"/pages/recibo_caja.zul", this, parametros);
		window.setWidth("80%");
		window.setHeight("90%");
		window.setPage(getPage());
		window.doModal();
	}

	public void mostrarReciboCaja() {
		Paciente paciente = (Paciente) tbxNro_identificacion
				.getRegistroSeleccionado();
		Recibo_caja recibo_caja = new Recibo_caja();
		recibo_caja.setCodigo_empresa(codigo_empresa);
		recibo_caja.setCodigo_sucursal(codigo_sucursal);
		recibo_caja.setNro_identificacion(paciente.getNro_identificacion());
		recibo_caja.setNro_ingreso(tbxNro_ingreso.getValue());
		recibo_caja = recibo_cajaService.consultar_recibo(recibo_caja);
		if (recibo_caja != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("paciente",
					tbxNro_identificacion.getRegistroSeleccionado());
			parametros.put("administradora",
					tbxCodigo_administradora.getRegistroSeleccionado());
			parametros.put("recibo_caja", recibo_caja);
			parametros.put("accion_form", "modificar");
			Window window = (Window) Executions.createComponents(
					"/pages/recibo_caja.zul", this, parametros);
			window.setWidth("80%");
			window.setHeight("90%");
			window.setPage(getPage());
			window.doModal();
		} else {
			Messagebox
					.show("No se ha genardo un recibo de caja para este paciente, "
							+ "¿Desea generar un recibo de caja para este paciente?. "
							+ "En el caso de generarlo debe agregar los procedimientos a relacionar",
							"Recibo de caja", Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event event)
										throws Exception {
									if ("onYes".equals(event.getName())) {
										generarReciboCaja(true);
									}
								}
							});
		}
	}

	public void cancelarAdmision(final Admision admision_aux,
			final Component target, final Label label_fact) {

		final Admision admision = admisionService.consultar(admision_aux);
		if (admision.getEstado().equals("1")) {
			if (admision.getAtendida()) {
				final VentanaCancelacion window_aux = new VentanaCancelacion(
						"Esta admision ya ha sido atendida, ¿Esta seguro que desea cancelarla?",
						getPage());
				window_aux.setClosable(true);
				window_aux.setEvento(new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						admision.setEstado("3");
						admision.setMotivo_cancelacion(window_aux.getValor());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("admision", admision);
						// map.put("parametros_empresa", parametros_empresa);
						admisionService.actualizar(map);
						MensajesUtil.mensajeInformacion("Informacion",
								"Admision cancelda satisfactoriamente",
								new EventListener<Event>() {

									@Override
									public void onEvent(Event arg0)
											throws Exception {
										window_aux.onClose();
										((Toolbarbutton) target)
												.setDisabled(true);
										label_fact.setValue("Cancelada");
									}
								});

					}
				});
				window_aux.doModal();
			} else {
				final VentanaCancelacion window_aux = new VentanaCancelacion(
						"¿Esta seguro que desea cancelar esta admision?",
						getPage());
				admision.setMotivo_cancelacion(window_aux.getValor());
				window_aux.setClosable(true);
				window_aux.setEvento(new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						admision.setEstado("3");
						admision.setMotivo_cancelacion(window_aux.getValor());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("admision", admision);
						// map.put("parametros_empresa", parametros_empresa);
						admisionService.actualizar(map);
						MensajesUtil.mensajeInformacion("Informacion",
								"Admision cancelda satisfactoriamente",
								new EventListener<Event>() {

									@Override
									public void onEvent(Event arg0)
											throws Exception {
										window_aux.onClose();
										((Toolbarbutton) target)
												.setDisabled(true);
										label_fact.setValue("Cancelada");
									}
								});
					}
				});
				window_aux.doModal();

			}

			rol_usuario.equals("");

		} else if (admision.getEstado().equals("2")
				&& rol_usuario.equals(IRoles.FACTURADOR_ADMINISTRATIVA)
				|| rol_usuario.equals(IRoles.ADMINISTRADOR)) {
			final VentanaCancelacion window_aux = new VentanaCancelacion(
					"Esta admision esta marcada como facturada. ¿Esta seguro que desea cancelarla?",
					getPage());
			window_aux.setClosable(true);
			window_aux.setEvento(new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					admision.setEstado("3");
					admision.setMotivo_cancelacion(window_aux.getValor());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("admision", admision);
					// map.put("parametros_empresa", parametros_empresa);
					admisionService.actualizar(map);
					MensajesUtil.mensajeInformacion("Informacion",
							"Admision cancelda satisfactoriamente",
							new EventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									window_aux.onClose();
									((Toolbarbutton) target).setDisabled(true);
									label_fact.setValue("Cancelada");
								}
							});
				}
			});
			window_aux.doModal();
		} else if (admision.getEstado().equals("2")
				&& rol_usuario.equals(IRoles.FACTURADOR_UPAS)
				|| rol_usuario.equals(IRoles.FACTURADOR_CAPS)
				|| rol_usuario.equals(IRoles.FACTURADOR)) {
			Messagebox
					.show("Para cancelar esta admision debe solicitar permiso de un facturador de la parte administrativa!!!",
							"Informacion", Messagebox.OK,
							Messagebox.EXCLAMATION);

		} else if (admision.getEstado().equals("3")) {
			Messagebox
					.show("No se puede cancelar la admision porque ya esta admision esta cancelada !!!",
							"Informacion", Messagebox.OK,
							Messagebox.EXCLAMATION);
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj, boolean accion) throws Exception {
		Admision admision = (Admision) obj;
		try {
			tbxAccion.setText("modificar");
			admision = admisionService.consultar(admision);
			admision_current = admision;
			Paciente paciente = admision.getPaciente();

			tbxNro_ingreso.setValue(admision.getNro_ingreso());
			tbxNro_identificacion.seleccionarRegistro(paciente,
					paciente.getNro_identificacion(),
					paciente.getNombreCompleto());
			cargarDatosPaciente(paciente);

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(admision.getCodigo_empresa());
			administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
			administradora.setCodigo(admision.getCodigo_administradora());
			administradora = administradoraService.consultar(administradora);
			tbxCodigo_administradora.seleccionarRegistro(administradora,
					administradora != null ? administradora.getCodigo()
							: admision.getCodigo_administradora(),
					administradora != null ? administradora.getNombre() : "");
			lbxContratos.getChildren().clear();
			Contratos contratos = ServiciosDisponiblesUtils
					.getContratosAdmision(admision);
			Listitem listitem_aux = new Listitem(
					contratos != null ? contratos.getNombre() + " - "
							+ contratos.getNro_contrato() : "No registrado",
					contratos);
			listitem_aux.setAttribute("CONTRATO_CONSULTADO", contratos);
			lbxContratos.appendChild(listitem_aux);
			lbxContratos.setSelectedIndex(0);
			this.contratos = contratos;

			dtbxFecha_ingreso.setValue(admision.getFecha_ingreso());
			tbxNro_autorizacion.setValue(admision.getNro_autorizacion());

			Utilidades.listarElementoListbox(lbxVia_ingreso, true,
					elementoService);
			Utilidades.seleccionarListitem(lbxVia_ingreso,
					admision.getVia_ingreso());

			Utilidades.seleccionarListitem(lbxCodigo_especialidad,
					admision.getCodigo_especialidad());

			Utilidades.seleccionarListitem(lbxCondicion_usuaria,
					admision.getCondicion_usuaria());

			Utilidades.seleccionarListitem(lbxTipo_diagnostico,
					admision.getTipo_diagnostico());

			tbxDiagnostico_ingreso.setValue(admision.getDiagnostico_ingreso());
			tbxNomDx.setValue(cargarDX(admision.getDiagnostico_ingreso()));

			Utilidades.seleccionarListitem(lbxTipo_discapacidad,
					admision.getTipo_discapacidad());

			Utilidades.seleccionarListitem(lbxGrado_discapacidad,
					admision.getGrado_discapacidad());

			chbUrgencias.setChecked(admision.getUrgencias().equals("S") ? true
					: false);
			chbHospitalizacion.setChecked(admision.getHospitalizacion().equals(
					"S") ? true : false);
			chbRecien_nacido
					.setChecked(admision.getRecien_nacido().equals("S") ? true
							: false);
			citasPendientesPaciente(paciente, admision.getVia_ingreso());
			Utilidades.seleccionarListitem(lbxCodigo_cita,
					admision.getCodigo_cita());

			rowCitaEspecialidad.setVisible(true);
			lbxVia_ingreso.setDisabled(true);

			seleccionarVia_ingreso_mostrar(false);

			// Cargamos prestador
			// despues de seleccionar via de ingreso
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(admision.getCodigo_empresa());
			prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
			prestadores.setNro_identificacion(admision.getCodigo_medico());
			prestadores = prestadoresService.consultar(prestadores);

			if (prestadores != null) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("nro_identificacion", prestadores.getNombres());
				parametros.put("apellidos", prestadores.getApellidos());
				parametros.put("nombres", prestadores.getNro_identificacion());
				tbxCodigo_medico.seleccionarRegistro(
						parametros,
						prestadores.getNro_identificacion(),
						prestadores.getNombres() + " "
								+ prestadores.getApellidos());
			}

			Utilidades.seleccionarListitem(lbxTipo_atencion,
					admision.getTipo_atencion());

			Utilidades.seleccionarListitem(lbxMarca_admision,
					admision.getMarca_admision());

			Utilidades.seleccionarListitem(lbxProcedencia,
					admision.getTipo_atencion());

			Admision_cama admision_cama = new Admision_cama();
			admision_cama.setCodigo_empresa(admision.getCodigo_empresa());
			admision_cama.setCodigo_sucursal(admision.getCodigo_sucursal());
			admision_cama.setNro_ingreso(admision.getNro_ingreso());
			admision_cama.setNro_identificacion(admision
					.getNro_identificacion());
			admision_cama = admision_camaService.consultar(admision_cama);

			Cama cama = new Cama();
			cama.setCodigo_empresa(empresa.getCodigo_empresa());
			cama.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			cama.setTipo_atencion((admision_cama != null ? admision_cama
					.getTipo_atencion() : ""));
			cama.setCodigo_pabellon((admision_cama != null ? admision_cama
					.getCodigo_pabellon() : 0));
			cama.setCodigo_habitacion((admision_cama != null ? admision_cama
					.getCodigo_habitacion() : 0));
			cama.setCodigo((admision_cama != null ? admision_cama
					.getCodigo_cama() : 0));
			cama.setCodigo_centro(admision.getCodigo_centro());
			cama = camaService.consultar(cama);
			Pabellon pab = new Pabellon();
			pab.setCodigo_empresa(empresa.getCodigo_empresa());
			pab.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			pab.setTipo_atencion((admision_cama != null ? admision_cama
					.getTipo_atencion() : ""));
			pab.setCodigo((admision_cama != null ? admision_cama
					.getCodigo_pabellon() : 0));
			pab.setCodigo_centro(admision.getCodigo_centro());
			pab = pabellonService.consultar(pab);

			Habitacion habitacion = new Habitacion();
			habitacion.setCodigo_empresa(empresa.getCodigo_empresa());
			habitacion.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			habitacion.setTipo_atencion((admision_cama != null ? admision_cama
					.getTipo_atencion() : ""));
			habitacion
					.setCodigo_pabellon((admision_cama != null ? admision_cama
							.getCodigo_pabellon() : 0));
			habitacion.setCodigo((admision_cama != null ? admision_cama
					.getCodigo_habitacion() : 0));
			habitacion.setCodigo_centro(admision.getCodigo_centro());
			habitacion = habitacionService.consultar(habitacion);

			String pabellon = (pab != null ? pab.getNombre() + " " : "")
					+ (habitacion != null ? habitacion.getNombre() + " " : "")
					+ (cama != null ? cama.getNombre() : "");

			tbxCama.setValue(admision.getCama());
			tbxCodigo_atencion.setValue(cama != null ? cama.getTipo_atencion()
					+ "" : "");
			tbxCodigo_pabellon.setValue(cama != null ? cama
					.getCodigo_pabellon() + "" : "");
			tbxCodigo_habitacion.setValue(cama != null ? cama
					.getCodigo_habitacion() + "" : "");
			// tbxCama.setObject(cama);
			tbxNomCama.setValue(pabellon);

			chbAplicar_triage.setChecked(admision.isAplica_triage());
			chbAplicar_tuberculosis
					.setChecked(admision.isAplica_tuberculosis());
			chbAplicar_lepra.setChecked(admision.isAplica_lepra());
			Utilidades.seleccionarRadio(rdbPaciente_remitido,
					admision.getPaciente_remitido());

			tbxNro_identificacion.setDisabled(true);
			btnLimpiarPaciente.setVisible(false);
			lbxCodigo_cita.setDisabled(true);

			mostrarVerificacionFurips(admision);

			if (configuracion_admision_ingreso != null) {
				if (configuracion_admision_ingreso.getLaboratorio_pyp()) {
					Elemento elemento = new Elemento();
					elemento.setCodigo(admision.getPrograma_lab_pyp());
					elemento.setTipo("via_ingreso");
					elemento = elementoService.consultar(elemento);
					Listitem listitem = new Listitem();
					listitem.setLabel(elemento != null ? elemento
							.getDescripcion() : admision.getPrograma_lab_pyp());
					listitem.setValue(admision.getPrograma_lab_pyp());
					lbxPrograma_pyp.appendChild(listitem);
					lbxPrograma_pyp.setDisabled(true);
					listitem.setSelected(true);
				}
			}

			// Mostramos la vista //

			btGuardar.setDisabled(true);
			if (accion) {
				accionForm(true, tbxAccion.getText());
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	/**
	 * TODO: mostrarVerificacionFurips
	 *
	 */
	private void mostrarVerificacionFurips(Admision admision) {
		if (admision.getCausa_externa().equals(
				ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRANSITO)
				|| admision
						.getCausa_externa()
						.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_EVENTO_CATASTROFICO)) {
			Furips2 furips2 = new Furips2();
			furips2.setCodigo_empresa(admision.getCodigo_empresa());
			furips2.setCodigo_sucursal(admision.getCodigo_sucursal());
			furips2.setNro_ingreso(admision.getNro_ingreso());
			furips2.setNro_identificacion(admision.getNro_identificacion());
			furips2 = furips2Service.consultarPorParametros(furips2);
			if (furips2 != null) {
				cargarFurips(furips2);
			}
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Admision admision = (Admision) obj;
		try {
			admision.setDelete_user(getUsuarios().getCodigo());
			int result = admisionService.eliminar(admision);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (Exception r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	public void seleccionarVia_ingreso(boolean mostrar, boolean pyp) {
		try {
			Administradora administradora = tbxCodigo_administradora
					.getRegistroSeleccionado();
			String via_ingreso = !pyp ? lbxVia_ingreso.getSelectedItem()
					.getValue().toString() : lbxPrograma_pyp.getSelectedItem()
					.getValue().toString();

			// limpiamos el medico
			tbxCodigo_medico.limpiarSeleccion(false);

			Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();

			if (!via_ingreso.trim().isEmpty()) {
				configuracion_admision_ingreso
						.setCodigo_empresa(codigo_empresa);
				configuracion_admision_ingreso
						.setCodigo_sucursal(codigo_sucursal);
				configuracion_admision_ingreso.setVia_ingreso(via_ingreso);
				configuracion_admision_ingreso = configuracion_admision_ingresoService
						.consultar(configuracion_admision_ingreso);

				if (configuracion_admision_ingreso == null) {
					btnProcedimientos.setVisible(false);
					btnMedicamentos.setVisible(false);
					throw new ValidacionRunTimeException(
							"La via de ingreso "
									+ lbxVia_ingreso.getSelectedItem()
											.getLabel()
									+ " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
				}
			} else {
				configuracion_admision_ingreso = Configuracion_admision_ingreso
						.getConfiguracionVacia();
			}

			if (!pyp) {
				this.configuracion_admision_ingreso = configuracion_admision_ingreso;
				if (configuracion_admision_ingreso.getLaboratorio_pyp()) {
					listarProgramasPyp(lbxVia_ingreso, lbxPrograma_pyp);
					rowProgramaPyp.setVisible(true);
				} else {
					rowProgramaPyp.setVisible(false);
				}
			}

			for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
				Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
				listitem.setVisible(true);
			}

			rowTipo_atencion.setVisible(false);
			rowCheckboxsRips.setVisible(false);
			rowProcedencia.setVisible(false);

			if (!pyp) {
				rowMarca_admision.setVisible(configuracion_admision_ingreso
						.getSolicitar_marca());
				btnProcedimientos.setVisible(false);
				btnMedicamentos.setVisible(false);
			}

			if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
				hlayoutRemitido.setVisible(true);
				chbAplicar_triage.setVisible(true);
				chbAplicar_triage.setChecked(true);
				chbAplicar_tuberculosis.setVisible(false);
				chbAplicar_lepra.setVisible(false);
				chbUrgencias.setVisible(true);
				chbUrgencias.setChecked(true);
				chbHospitalizacion.setVisible(false);
				chbHospitalizacion.setChecked(false);
				chbRecien_nacido.setVisible(false);
				chbRecien_nacido.setChecked(false);
				lbxTipo_atencion.setSelectedIndex(0);
				rowProcedencia.setVisible(true);
				hlayoutProcedencia.setVisible(false);
				rowCheckboxsRips.setVisible(true);
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "001",
							"003")) {
						listitem.setVisible(false);
					}
				}
			} else if (via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES)) {
				chbAplicar_triage.setVisible(false);
				hlayoutRemitido.setVisible(false);
				chbAplicar_triage.setChecked(false);
				chbAplicar_tuberculosis.setVisible(false);
				chbAplicar_lepra.setVisible(false);
				chbUrgencias.setVisible(false);
				chbUrgencias.setChecked(false);
				chbHospitalizacion.setVisible(true);
				chbHospitalizacion.setChecked(true);
				chbRecien_nacido.setVisible(false);
				chbRecien_nacido.setChecked(false);
				rowProcedencia.setVisible(true);
				hlayoutProcedencia.setVisible(true);
				Utilidades.seleccionarListitem(lbxTipo_atencion, "001");
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "002",
							"003", "004")) {
						listitem.setVisible(false);
					}
				}
				rowTipo_atencion.setVisible(true);
				rowCheckboxsRips.setVisible(true);

			} else if (via_ingreso
					.equals(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO)) {
				chbAplicar_triage.setVisible(false);
				hlayoutRemitido.setVisible(false);
				chbAplicar_triage.setChecked(false);
				chbAplicar_tuberculosis.setVisible(false);
				chbAplicar_lepra.setVisible(false);
				chbUrgencias.setVisible(false);
				chbUrgencias.setChecked(false);
				chbHospitalizacion.setVisible(false);
				chbHospitalizacion.setChecked(false);
				chbRecien_nacido.setVisible(true);
				chbRecien_nacido.setChecked(true);
				lbxTipo_atencion.setSelectedIndex(0);
				rowProcedencia.setVisible(false);
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "001",
							"003")) {
						listitem.setVisible(false);
					}
				}
				rowCheckboxsRips.setVisible(true);
			} else if (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA)
					|| via_ingreso.equals(IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
				chbAplicar_triage.setVisible(false);
				hlayoutRemitido.setVisible(false);
				chbAplicar_triage.setChecked(false);
				chbAplicar_tuberculosis.setVisible(true);
				chbAplicar_lepra.setVisible(true);
				chbUrgencias.setVisible(false);
				chbUrgencias.setChecked(false);
				chbHospitalizacion.setVisible(false);
				chbHospitalizacion.setChecked(false);
				chbRecien_nacido.setVisible(false);
				chbRecien_nacido.setChecked(false);
				rowProcedencia.setVisible(false);
				Utilidades.seleccionarListitem(lbxTipo_atencion, "003");
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "001",
							"002", "004")) {
						listitem.setVisible(false);
					}
				}
				boolean tuberculosis = aplicaTuberculosis(tbxNro_identificacion
						.getValue());
				chbAplicar_tuberculosis.setChecked(tuberculosis);
				chbAplicar_tuberculosis.setVisible(tuberculosis);

				boolean lepra = aplicaLepra(tbxNro_identificacion.getValue());
				chbAplicar_lepra.setChecked(lepra);
				chbAplicar_lepra.setVisible(lepra);

				mostrarItemPorDefecto();
			} else {
				chbAplicar_triage.setVisible(false);
				hlayoutRemitido.setVisible(false);
				chbAplicar_triage.setChecked(false);
				chbAplicar_tuberculosis.setVisible(false);
				chbAplicar_lepra.setVisible(false);
				chbUrgencias.setVisible(false);
				chbUrgencias.setChecked(false);
				chbHospitalizacion.setVisible(false);
				chbHospitalizacion.setChecked(false);
				chbRecien_nacido.setVisible(false);
				chbRecien_nacido.setChecked(false);
				rowProcedencia.setVisible(false);
				Utilidades.seleccionarListitem(lbxTipo_atencion, "003");
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "001",
							"002", "004")) {
						listitem.setVisible(false);
					}
				}
			}

			seleccionarTipo_atencion();

			if (!pyp) {
				rowCitaEspecialidad.setVisible(configuracion_admision_ingreso
						.getMostrar_cita());
			}

			lbCausa_externa.setVisible(via_ingreso
					.equals(IVias_ingreso.URGENCIA)
					|| via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES));

			lbxCausa_externa.setVisible(via_ingreso
					.equals(IVias_ingreso.URGENCIA)
					|| via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES));

			lbxCausa_externa.setDisabled(!via_ingreso
					.equals(IVias_ingreso.URGENCIA)
					&& !via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES));
			tbxCama.setDisabled(!lbxVia_ingreso.getSelectedItem().getValue()
					.toString().equals(IVias_ingreso.URGENCIA)
					&& !lbxVia_ingreso.getSelectedItem().getValue().toString()
							.equals(IVias_ingreso.HOSPITALIZACIONES));
			// esto es para que haga el cambio
			chbHospitalizacion.setChecked(via_ingreso
					.equals(IVias_ingreso.HOSPITALIZACIONES));
			// chbRecien_nacido.setChecked(lbxVia_ingreso.getSelectedItem().getValue().toString().equals(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO));
			boolean isParicular = isParticularAtencion(administradora);

			btnRecibo_caja.setVisible(true);

			verificarMultiplesContratos(via_ingreso,
					configuracion_admision_ingreso.getLaboratorio_pyp());

			if (isParicular) {
				if (configuracion_admision_ingreso
						.getFac_automatica_particular()) {
					btnProcedimientos.setVisible(true);

					if (!configuracion_admision_ingreso.getVia_ingreso()
							.equals(IVias_ingreso.MEDICAMENTOS_PYP)) {
						btnProcedimientos.setVisible(true);
						if (procedimientos_admisionAction != null) {
							if (!procedimientos_admisionAction.getVia_ingreso()
									.equals(lbxVia_ingreso.getSelectedItem()
											.getValue().toString())) {
								limpiarOrdenProcedimiento();
								mostrarVentanaProcedimientos(tbxAccion
										.getValue().equalsIgnoreCase(
												"registrar"));
							}
						} else {
							mostrarVentanaProcedimientos(tbxAccion.getValue()
									.equalsIgnoreCase("registrar"));
						}
					} else {
						btnMedicamentos.setVisible(true);
						if (medicamentos_admisionAction != null) {
							if (!medicamentos_admisionAction.getVia_ingreso()
									.equals(lbxVia_ingreso.getSelectedItem()
											.getValue().toString())) {
								mostrarVentanaMedicamentos(tbxAccion.getValue()
										.equalsIgnoreCase("registrar"));
							}
						} else {
							mostrarVentanaMedicamentos(tbxAccion.getValue()
									.equalsIgnoreCase("registrar"));
						}
					}
				} else {
					btnRecibo_caja.setVisible(false);
					btnProcedimientos.setVisible(false);
					btnMedicamentos.setVisible(false);
				}

			} else if (configuracion_admision_ingreso
					.getSolicitar_informacion().equals(
							Configuracion_admision_ingreso.TIPO_PROCEDIMIENTOS)
					&& !pyp) {

				btnProcedimientos.setVisible(true);
				btnMedicamentos.setVisible(false);
			} else if (configuracion_admision_ingreso
					.getSolicitar_informacion().equals(
							Configuracion_admision_ingreso.TIPO_MEDICAMENTOS)
					&& !pyp) {
				btnProcedimientos.setVisible(false);
				btnMedicamentos.setVisible(true);
			} else {
				if (!pyp) {
					btnMedicamentos.setVisible(false);
					btnProcedimientos.setVisible(false);
				}
			}

			if (!pyp) {
				if (!configuracion_admision_ingreso.getSolicitar_prestador()) {
					getFellow("rowPrestador").setVisible(false);
					tbxCodigo_medico
							.setValue(IConstantes.CODIGO_MEDICO_DEFECTO);
				} else {
					lbPrestador
							.setValue(lbxVia_ingreso.getSelectedItem()
									.getValue().toString()
									.equals(IVias_ingreso.VIA_VACUNACION) ? "Vacunador: "
									: "Prestador: ");
					getFellow("rowPrestador").setVisible(true);
					tbxCodigo_medico.setValue(tbxCodigo_medico.getValue()
							.equals(IConstantes.CODIGO_MEDICO_DEFECTO) ? ""
							: tbxCodigo_medico.getValue());

					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("codigo_empresa", codigo_empresa);
					parameters.put("codigo_sucursal", codigo_sucursal);
					parameters.put("nro_identificacion",
							tbxNro_identificacion.getValue());

					parameters.put("anio", anio);
					parameters.put("via_ingreso", lbxVia_ingreso
							.getSelectedItem().getValue().toString());

					List<Historia_clinica> listado_historias = generalExtraService
							.listar(Historia_clinica.class, parameters);
					if (!listado_historias.isEmpty()) {
						tipo_cita = "2";
					} else {
						tipo_cita = "1";
					}

					lbTipo_psicologia.setVisible(via_ingreso
							.equals(IVias_ingreso.PSICOLOGIA));
					lbxTipo_psicologia.setVisible(via_ingreso
							.equals(IVias_ingreso.PSICOLOGIA));

					dtbxFecha_ingreso.setWidth("110px");
					tbxNro_autorizacion.setWidth("80px");
					habilitarEspecialidadesRelacionadas(via_ingreso);
				}
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void seleccionarVia_ingreso_mostrar(boolean pyp) {
		try {

			String via_ingreso = !pyp ? lbxVia_ingreso.getSelectedItem()
					.getValue().toString() : lbxPrograma_pyp.getSelectedItem()
					.getValue().toString();

			// limpiamos el medico
			tbxCodigo_medico.limpiarSeleccion(false);

			Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();

			if (!via_ingreso.trim().isEmpty()) {
				configuracion_admision_ingreso
						.setCodigo_empresa(codigo_empresa);
				configuracion_admision_ingreso
						.setCodigo_sucursal(codigo_sucursal);
				configuracion_admision_ingreso.setVia_ingreso(via_ingreso);
				configuracion_admision_ingreso = configuracion_admision_ingresoService
						.consultar(configuracion_admision_ingreso);

				if (configuracion_admision_ingreso == null) {
					btnProcedimientos.setVisible(false);
					btnMedicamentos.setVisible(false);
					throw new ValidacionRunTimeException(
							"La via de ingreso "
									+ lbxVia_ingreso.getSelectedItem()
											.getLabel()
									+ " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
				}
			} else {
				configuracion_admision_ingreso = Configuracion_admision_ingreso
						.getConfiguracionVacia();
			}

			if (!pyp) {
				this.configuracion_admision_ingreso = configuracion_admision_ingreso;
				if (configuracion_admision_ingreso.getLaboratorio_pyp()) {
					listarProgramasPyp(lbxVia_ingreso, lbxPrograma_pyp);
					rowProgramaPyp.setVisible(true);
				} else {
					rowProgramaPyp.setVisible(false);
				}
			}

			for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
				Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
				listitem.setVisible(true);
			}

			rowTipo_atencion.setVisible(false);
			rowCheckboxsRips.setVisible(false);
			rowProcedencia.setVisible(false);

			if (!pyp) {
				rowMarca_admision.setVisible(configuracion_admision_ingreso
						.getSolicitar_marca());
				btnProcedimientos.setVisible(false);
				btnMedicamentos.setVisible(false);
			}

			if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
				hlayoutRemitido.setVisible(true);
				chbAplicar_triage.setVisible(true);
				chbAplicar_triage.setChecked(true);
				chbAplicar_tuberculosis.setVisible(false);
				chbAplicar_lepra.setVisible(false);
				chbUrgencias.setVisible(true);
				chbUrgencias.setChecked(true);
				chbHospitalizacion.setVisible(false);
				chbHospitalizacion.setChecked(false);
				chbRecien_nacido.setVisible(false);
				chbRecien_nacido.setChecked(false);
				lbxTipo_atencion.setSelectedIndex(0);
				rowProcedencia.setVisible(true);
				hlayoutProcedencia.setVisible(false);
				rowCheckboxsRips.setVisible(true);
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "001",
							"003")) {
						listitem.setVisible(false);
					}
				}
			} else if (via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES)) {
				chbAplicar_triage.setVisible(false);
				hlayoutRemitido.setVisible(false);
				chbAplicar_triage.setChecked(false);
				chbAplicar_tuberculosis.setVisible(false);
				chbAplicar_lepra.setVisible(false);
				chbUrgencias.setVisible(false);
				chbUrgencias.setChecked(false);
				chbHospitalizacion.setVisible(true);
				chbHospitalizacion.setChecked(true);
				chbRecien_nacido.setVisible(false);
				chbRecien_nacido.setChecked(false);
				rowProcedencia.setVisible(true);
				hlayoutProcedencia.setVisible(true);
				Utilidades.seleccionarListitem(lbxTipo_atencion, "001");
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "002",
							"003", "004")) {
						listitem.setVisible(false);
					}
				}
				rowTipo_atencion.setVisible(true);
				rowCheckboxsRips.setVisible(true);

			} else if (via_ingreso
					.equals(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO)) {
				chbAplicar_triage.setVisible(false);
				hlayoutRemitido.setVisible(false);
				chbAplicar_triage.setChecked(false);
				chbAplicar_tuberculosis.setVisible(false);
				chbAplicar_lepra.setVisible(false);
				chbUrgencias.setVisible(false);
				chbUrgencias.setChecked(false);
				chbHospitalizacion.setVisible(false);
				chbHospitalizacion.setChecked(false);
				chbRecien_nacido.setVisible(true);
				chbRecien_nacido.setChecked(true);
				lbxTipo_atencion.setSelectedIndex(0);
				rowProcedencia.setVisible(false);
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "001",
							"003")) {
						listitem.setVisible(false);
					}
				}
				rowCheckboxsRips.setVisible(true);
			} else if (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA)
					|| via_ingreso.equals(IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
				chbAplicar_triage.setVisible(false);
				hlayoutRemitido.setVisible(false);
				chbAplicar_triage.setChecked(false);
				chbAplicar_tuberculosis.setVisible(true);
				chbAplicar_lepra.setVisible(true);
				chbUrgencias.setVisible(false);
				chbUrgencias.setChecked(false);
				chbHospitalizacion.setVisible(false);
				chbHospitalizacion.setChecked(false);
				chbRecien_nacido.setVisible(false);
				chbRecien_nacido.setChecked(false);
				rowProcedencia.setVisible(false);
				Utilidades.seleccionarListitem(lbxTipo_atencion, "003");
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "001",
							"002", "004")) {
						listitem.setVisible(false);
					}
				}
				boolean tuberculosis = aplicaTuberculosis(tbxNro_identificacion
						.getValue());
				chbAplicar_tuberculosis.setChecked(tuberculosis);
				chbAplicar_tuberculosis.setVisible(tuberculosis);

				boolean lepra = aplicaLepra(tbxNro_identificacion.getValue());
				chbAplicar_lepra.setChecked(lepra);
				chbAplicar_lepra.setVisible(lepra);

				mostrarItemPorDefecto();
			} else {
				chbAplicar_triage.setVisible(false);
				hlayoutRemitido.setVisible(false);
				chbAplicar_triage.setChecked(false);
				chbAplicar_tuberculosis.setVisible(false);
				chbAplicar_lepra.setVisible(false);
				chbUrgencias.setVisible(false);
				chbUrgencias.setChecked(false);
				chbHospitalizacion.setVisible(false);
				chbHospitalizacion.setChecked(false);
				chbRecien_nacido.setVisible(false);
				chbRecien_nacido.setChecked(false);
				rowProcedencia.setVisible(false);
				Utilidades.seleccionarListitem(lbxTipo_atencion, "003");
				for (int i = 0; i < lbxTipo_atencion.getItemCount(); i++) {
					Listitem listitem = lbxTipo_atencion.getItemAtIndex(i);
					if (Utilidades.igualConjuncion(listitem.getValue(), "001",
							"002", "004")) {
						listitem.setVisible(false);
					}
				}
			}

			seleccionarTipo_atencion();

			if (!pyp) {
				rowCitaEspecialidad.setVisible(configuracion_admision_ingreso
						.getMostrar_cita());
			}

			lbCausa_externa.setVisible(via_ingreso
					.equals(IVias_ingreso.URGENCIA)
					|| via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES));

			lbxCausa_externa.setVisible(via_ingreso
					.equals(IVias_ingreso.URGENCIA)
					|| via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES));

			lbxCausa_externa.setDisabled(!via_ingreso
					.equals(IVias_ingreso.URGENCIA)
					&& !via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES));
			tbxCama.setDisabled(!lbxVia_ingreso.getSelectedItem().getValue()
					.toString().equals(IVias_ingreso.URGENCIA)
					&& !lbxVia_ingreso.getSelectedItem().getValue().toString()
							.equals(IVias_ingreso.HOSPITALIZACIONES));
			// esto es para que haga el cambio
			chbHospitalizacion.setChecked(via_ingreso
					.equals(IVias_ingreso.HOSPITALIZACIONES));

			btnRecibo_caja.setVisible(true);
			boolean mostrar = tbxAccion.getValue()
					.equalsIgnoreCase("registrar");
			log.info("mostrar ===> " + mostrar);
			Administradora administradora = tbxCodigo_administradora
					.getRegistroSeleccionado();
			boolean isParicular = isParticularAtencion(administradora);

			if (isParicular) {
				if (configuracion_admision_ingreso
						.getFac_automatica_particular()) {
					btnProcedimientos.setVisible(true);

					if (!configuracion_admision_ingreso.getVia_ingreso()
							.equals(IVias_ingreso.MEDICAMENTOS_PYP)) {
						btnProcedimientos.setVisible(true);
						if (procedimientos_admisionAction != null) {
							if (!procedimientos_admisionAction.getVia_ingreso()
									.equals(lbxVia_ingreso.getSelectedItem()
											.getValue().toString())) {
								limpiarOrdenProcedimiento();
								mostrarVentanaProcedimientos(mostrar);
							}
						} else {
							mostrarVentanaProcedimientos(mostrar);
						}
					} else {
						btnMedicamentos.setVisible(true);
						if (medicamentos_admisionAction != null) {
							if (!medicamentos_admisionAction.getVia_ingreso()
									.equals(lbxVia_ingreso.getSelectedItem()
											.getValue().toString())) {
								mostrarVentanaMedicamentos(mostrar);
							}
						} else {
							mostrarVentanaMedicamentos(mostrar);
						}
					}
				} else {
					btnProcedimientos.setVisible(false);
					btnMedicamentos.setVisible(false);
				}

			} else if (configuracion_admision_ingreso
					.getSolicitar_informacion().equals(
							Configuracion_admision_ingreso.TIPO_PROCEDIMIENTOS)
					&& !pyp) {
				btnProcedimientos.setVisible(true);
				btnMedicamentos.setVisible(false);
			} else if (configuracion_admision_ingreso
					.getSolicitar_informacion().equals(
							Configuracion_admision_ingreso.TIPO_MEDICAMENTOS)
					&& !pyp) {
				btnProcedimientos.setVisible(false);
				btnMedicamentos.setVisible(true);
			} else {
				if (!pyp) {
					btnMedicamentos.setVisible(false);
					btnProcedimientos.setVisible(false);
				}
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void verificarMultiplesContratos(String via_ingreso, boolean lab_pyp) {
		// log.info("ejecutando metodo @verificarMultiplesContratos() "
		// + via_ingreso + " " + lab_pyp);
		lbxContratos.getChildren().clear();
		if (!via_ingreso.isEmpty() && !lab_pyp) {
			this.contratos = null;
			List<Contratos> listado_contratos_aux = getListadoMultiplesContratos(via_ingreso);
			if (!listado_contratos_aux.isEmpty()) {
				if (listado_contratos_aux.size() == 1) {
					Contratos contratos_aux = listado_contratos_aux.get(0);
					if (contratos_aux == null) {
						MensajesUtil
								.mensajeAlerta("Verificar contratos",
										"Al parecer los contratos relacionados con este paciente estan cerrados");
					} else {
						Listitem listitem = new Listitem(
								contratos_aux != null ? contratos_aux.getNombre()
										+ " - "
										+ contratos_aux.getNro_contrato()
										: "No registrado", contratos_aux);
						listitem.setAttribute("CONTRATO_CONSULTADO",
								contratos_aux);
						lbxContratos.appendChild(listitem);
						lbxContratos.setSelectedIndex(0);
						this.contratos = contratos_aux;
					}
				} else {
					lbxContratos.appendItem("-- Seleccionar contrato --", "");
					int capitados = 0;
					for (Contratos contratos_aux : listado_contratos_aux) {
						Listitem listitem = new Listitem(
								contratos_aux != null ? contratos_aux.getNombre()
										+ " - "
										+ contratos_aux.getNro_contrato()
										: "No registrado", contratos_aux);
						listitem.setAttribute("CONTRATO_CONSULTADO",
								contratos_aux);
						if (contratos_aux != null
								&& contratos_aux.getTipo_facturacion().equals(
										IConstantes.TIPO_CONTRATO_CAPITADA)) {
							capitados++;
							listitem.setSelected(true);
							listitem.setTooltiptext("Este contrato es capitado");
							this.contratos = contratos_aux;
						}

						lbxContratos.appendChild(listitem);
					}
					if (capitados == 0 || capitados > 1) {
						lbxContratos.setSelectedIndex(0);
						Clients.scrollIntoView(lbxContratos);
						MensajesUtil.notificarAlerta("Seleccionar contrato",
								lbxContratos);
					}
				}
			}
		}
	}

	public void seleccionarContrato() {
		if (lbxContratos.getSelectedItem().getValue() instanceof Manuales_tarifarios) {
			this.contratos = (Contratos) lbxContratos.getSelectedItem()
					.getAttribute("CONTRATO_CONSULTADO");
		} else if (lbxContratos.getSelectedItem().getValue() instanceof Contratos) {
			Contratos contratos_aux = (Contratos) lbxContratos
					.getSelectedItem().getValue();
			this.contratos = contratos_aux;
		} else {
			this.contratos = null;
			MensajesUtil.notificarAlerta("Seleccionar contrato", lbxContratos);
		}
	}

	private boolean isParticularAtencion(Administradora administradora) {
		return administradora != null ? administradora
				.getTipo_aseguradora()
				.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)
				: false;
	}

	public String habilitarFiltroEnfermera() {
		String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue()
				.toString();
		return Utilidades.habilitarFiltroEnfermera(via_ingreso, tipo_cita,
				getParametros_empresa());
	}

	public boolean aplicaTuberculosis(String identificacion) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", identificacion);
		parameters.put("parameters", IConstantes.CODIGOS_CIE_TUBERCULOSIS);
		parameters.put("limite_paginado", "limit 25 offset 0");
		// //log.info("parameters>>>>" + parameters);
		List<Impresion_diagnostica> lista_datos = impresion_diagnosticaService
				.listar_paciente_contuberculosis_lepra(parameters);
		// //log.info("lista_datos>>>>" + lista_datos);
		return (lista_datos != null && !lista_datos.isEmpty());
	}

	public boolean aplicaLepra(String identificacion) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", tbxNro_identificacion.getValue());
		parameters.put("parameters", IConstantes.CODIGOS_CIE_LEPRA);
		parameters.put("limite_paginado", "limit 25 offset 0");
		// //log.info("parameters>>>>" + parameters);
		List<Impresion_diagnostica> lista_datos = impresion_diagnosticaService
				.listar_paciente_contuberculosis_lepra(parameters);
		// //log.info("lista_datos>>>>" + lista_datos);
		return (lista_datos != null && !lista_datos.isEmpty());
	}

	public void seleccionarTipo_atencion() {
		if (lbxTipo_atencion.getSelectedItem().getValue().equals("001")) {
			imgConsultar_cama.setVisible(true);
			// tbxCama.setDisabled(false);
			btnLimpiarCama.setVisible(true);
			lbCama.setVisible(true);
			cellCama.setVisible(true);
		} else {
			imgConsultar_cama.setVisible(false);
			tbxCama.setValue("");
			tbxCodigo_atencion.setValue("");
			tbxCodigo_habitacion.setValue("");
			tbxCodigo_pabellon.setValue("");
			tbxNomCama.setValue("");
			btnLimpiarCama.setVisible(false);
			lbCama.setVisible(false);
			cellCama.setVisible(false);
		}
	}

	public void mostrarItemPorDefecto() {
		if (Utilidades.igualConjuncion(lbxVia_ingreso.getSelectedItem()
				.getValue(), "2", "6", "7", "8", "9", "10", "11", "12", "13",
				"14", "23", "24")) {
			Utilidades.seleccionarListitem(lbxTipo_atencion, "003");
		} else {
			UtilidadesComponentes.listarElementosListbox(true, true,
					elementoService, lbxTipo_atencion);
		}
	}

	public void seleccionarCita() {
		try {
			if (lbxCodigo_cita.getSelectedItem().getValue().equals("")) {
				tbxNomAdministradora.setDisabled(false);
				tbxCodigo_medico.setDisabled(false);
				btnLimpiarPrestador.setVisible(true);
				if (lbxVia_ingreso.getItemCount() > 0) {
					lbxVia_ingreso.setSelectedIndex(0);
				}
				lbxVia_ingreso.setDisabled(tbxNro_identificacion
						.getRegistroSeleccionado() == null);
			} else {
				String codigo_cita = lbxCodigo_cita.getSelectedItem()
						.getValue().toString();

				Citas citas = new Citas();
				citas.setCodigo_empresa(codigo_empresa);
				citas.setCodigo_sucursal(codigo_sucursal);
				citas.setCodigo_cita(codigo_cita);
				citas = citasService.consultar(citas);
				if (citas == null) {
					throw new Exception("Registro de cita " + codigo_cita
							+ " no se encuentre en basa de datos");
				}

				String fecha_ingreso = new java.text.SimpleDateFormat(
						"dd/MM/yyyy").format(dtbxFecha_ingreso.getValue());
				String fecha_cita = new java.text.SimpleDateFormat("dd/MM/yyyy")
						.format(citas.getFecha_cita());

				if (!fecha_cita.equals(fecha_ingreso)) {
					lbxCodigo_cita.setSelectedIndex(0);
					throw new ValidacionRunTimeException(
							"La fecha de la cita no coincide con la fecha de la admision");
				}

				Administradora administradora = new Administradora();
				administradora.setCodigo_empresa(citas.getCodigo_empresa());
				administradora.setCodigo_sucursal(citas.getCodigo_sucursal());
				administradora.setCodigo(citas.getCodigo_administradora());
				administradora = administradoraService
						.consultar(administradora);
				if (administradora != null) {
					tbxCodigo_administradora.setValue(administradora
							.getCodigo());
					tbxNomAdministradora.setValue(administradora.getNombre());
					tbxNomAdministradora.setDisabled(true);

				}

				lbxVia_ingreso.setDisabled(true);
				Utilidades.seleccionarListitem(lbxVia_ingreso,
						citas.getTipo_consulta());
				seleccionarVia_ingreso(true, false);

				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(citas.getCodigo_empresa());
				prestadores.setCodigo_sucursal(citas.getCodigo_sucursal());
				prestadores.setNro_identificacion(citas.getCodigo_prestador());
				prestadores = prestadoresService.consultar(prestadores);
				log.info("prestadores ===> " + prestadores);
				if (prestadores != null) {
					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("nro_identificacion",
							prestadores.getNombres());
					parametros.put("apellidos", prestadores.getApellidos());
					parametros.put("nombres",
							prestadores.getNro_identificacion());
					tbxCodigo_medico.seleccionarRegistro(
							parametros,
							prestadores.getNro_identificacion(),
							prestadores.getNombres() + " "
									+ prestadores.getApellidos());
					tbxCodigo_medico.setDisabled(true);
					btnLimpiarPrestador.setVisible(false);
				}
			}
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	private Map<String, Object> validarTipo_consultaVia_ingreso(Citas citas)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "");
		result.put("success", true);

		Elemento elemento_tipo_consulta = new Elemento();
		elemento_tipo_consulta.setTipo("motivo_consulta_ese");
		elemento_tipo_consulta.setCodigo(citas.getTipo_consulta());
		elemento_tipo_consulta = elementoService
				.consultar(elemento_tipo_consulta);
		String nombre_tipo_consulta = (elemento_tipo_consulta != null ? elemento_tipo_consulta
				.getDescripcion() : "");

		String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue()
				.toString();

		if (citas.getTipo_consulta().equals("1") && !via_ingreso.equals("2")) {
			String nombre_via_ingreso = Utilidades.getNombreElemento("2",
					"via_ingreso", elementoService);
			result.put("msg", "Para citas de tipo " + nombre_tipo_consulta
					+ "debe escoger vía de ingreso " + nombre_via_ingreso);
			result.put("success", false);
		} else if (citas.getTipo_consulta().equals("3")
				&& !via_ingreso.equals("17")) {
			String nombre_via_ingreso = Utilidades.getNombreElemento("17",
					"via_ingreso", elementoService);
			result.put("msg", "Para citas de tipo " + nombre_tipo_consulta
					+ "debe escoger vía de ingreso " + nombre_via_ingreso);
			result.put("success", false);
		} else if (citas.getTipo_consulta().equals("20")
				&& !via_ingreso.equals("15")) {
			String nombre_via_ingreso = Utilidades.getNombreElemento("15",
					"via_ingreso", elementoService);
			result.put("msg", "Para citas de tipo " + nombre_tipo_consulta
					+ "debe escoger vía de ingreso " + nombre_via_ingreso);
			result.put("success", false);
		} else if (citas.getTipo_consulta().equals("19")
				&& !via_ingreso.equals("16")) {
			String nombre_via_ingreso = Utilidades.getNombreElemento("16",
					"via_ingreso", elementoService);
			result.put("msg", "Para citas de tipo " + nombre_tipo_consulta
					+ "debe escoger vía de ingreso " + nombre_via_ingreso);
			result.put("success", false);
		} else if (citas.getTipo_consulta().equals("4")) {
			Pyp pyp = new Pyp();
			pyp.setCodigo(citas.getArea_intervencion());
			pyp = generalExtraService.consultar(pyp);
			if (pyp == null) {
				throw new Exception("Area de pyp no se encuentra disponible");
			}

			if (pyp.getCodigo().equals("13") && !via_ingreso.equals("6")) {
				String nombre_via_ingreso = Utilidades.getNombreElemento("6",
						"via_ingreso", elementoService);
				result.put("msg", "Para citas pyp programa " + pyp.getNombre()
						+ "debe escoger vía de ingreso " + nombre_via_ingreso);
				result.put("success", false);
			} else if (pyp.getCodigo().equals("04")
					&& (!via_ingreso.equals("7") && !via_ingreso.equals("8")
							&& !via_ingreso.equals("9") && !via_ingreso
								.equals("10"))) {
				String nombre_via_ingreso1 = Utilidades.getNombreElemento("7",
						"via_ingreso", elementoService);
				String nombre_via_ingreso2 = Utilidades.getNombreElemento("8",
						"via_ingreso", elementoService);
				String nombre_via_ingreso3 = Utilidades.getNombreElemento("9",
						"via_ingreso", elementoService);
				String nombre_via_ingreso4 = Utilidades.getNombreElemento("10",
						"via_ingreso", elementoService);
				result.put("msg", "Para citas pyp programa " + pyp.getNombre()
						+ "debe escoger las siguientes vías de ingreso "
						+ nombre_via_ingreso1 + "," + nombre_via_ingreso2 + ","
						+ nombre_via_ingreso3 + " o " + nombre_via_ingreso4);
				result.put("success", false);
			} else if (pyp.getCodigo().equals("06")
					&& !via_ingreso.equals("11")) {
				String nombre_via_ingreso = Utilidades.getNombreElemento("11",
						"via_ingreso", elementoService);
				result.put("msg", "Para citas pyp programa " + pyp.getNombre()
						+ "debe escoger vía de ingreso " + nombre_via_ingreso);
				result.put("success", false);
			} else if (pyp.getCodigo().equals("07")
					&& !via_ingreso.equals("12")) {
				String nombre_via_ingreso = Utilidades.getNombreElemento("12",
						"via_ingreso", elementoService);
				result.put("msg", "Para citas pyp programa " + pyp.getNombre()
						+ "debe escoger vía de ingreso " + nombre_via_ingreso);
				result.put("success", false);
			} else if (pyp.getCodigo().equals("05")
					&& !via_ingreso.equals("13")) {
				String nombre_via_ingreso = Utilidades.getNombreElemento("13",
						"via_ingreso", elementoService);
				result.put("msg", "Para citas pyp programa " + pyp.getNombre()
						+ "debe escoger vía de ingreso " + nombre_via_ingreso);
				result.put("success", false);
			} else if (pyp.getCodigo().equals("03")
					&& !via_ingreso.equals("14")) {
				String nombre_via_ingreso = Utilidades.getNombreElemento("14",
						"via_ingreso", elementoService);
				result.put("msg", "Para citas pyp programa " + pyp.getNombre()
						+ "debe escoger vía de ingreso " + nombre_via_ingreso);
				result.put("success", false);
			}
		}

		return result;
	}

	public void imprimir() throws Exception {
		// Imprime reporte unico
		if (tbxNro_ingreso.getValue().equals("")) {
			MensajesUtil.mensajeAlerta("Alerta !!",
					"debe seleccionar un registro de admision !!");
			return;
		}
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Admision");
		paramRequest.put("nro_ingreso", tbxNro_ingreso.getValue());
		paramRequest
				.put("nro_identificacion", tbxNro_identificacion.getValue());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void mostrarProgramasPaciente(Paciente paciente, ZKWindow zkWindow) {
		List<Elemento> lista_programas = ManejadorPoblacion
				.obtenerListadoProgramasPaciente(paciente, elementoService);
		lbxVia_ingreso.getChildren().clear();
		for (Elemento programa : lista_programas) {
			lbxVia_ingreso.appendItem(programa.getDescripcion(),
					programa.getCodigo());
		}
	}

	public void openPcd() throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo_atencion", lbxTipo_atencion.getSelectedItem()
				.getValue().toString());
		parametros.put("seleccionar", true);
		parametros.put("editar", false);

		Component componente = Executions.createComponents(
				"/pages/configuracion_cama.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("850px");
		ventana.setHeight("490px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("SELECCIONAR CAMAS ");
		ventana.setMode("modal");
	}

	public void quitarPcd() throws Exception {
		tbxCama.setValue("");
		tbxCodigo_atencion.setValue("");
		tbxCodigo_habitacion.setValue("");
		tbxCodigo_pabellon.setValue("");
		tbxNomCama.setValue("");
	}

	public void cargarCama(String codigo_atencion, String codigo_pabellon,
			String codigo_habitacion, String codigo_cama, String codigo_centro) {

		Cama cama = new Cama();
		cama.setCodigo_empresa(empresa.getCodigo_empresa());
		cama.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		cama.setTipo_atencion(codigo_atencion);
		cama.setCodigo_pabellon(Integer.parseInt(codigo_pabellon));
		cama.setCodigo_habitacion(Integer.parseInt(codigo_habitacion));
		cama.setCodigo(Integer.parseInt(codigo_cama));
		cama.setCodigo_centro(codigo_centro);
		cama = camaService.consultar(cama);

		Pabellon pab = new Pabellon();
		pab.setCodigo_empresa(empresa.getCodigo_empresa());
		pab.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		pab.setTipo_atencion((cama != null ? cama.getTipo_atencion() : ""));
		pab.setCodigo((cama != null ? cama.getCodigo_pabellon() : 0));
		pab.setCodigo_centro(codigo_centro);
		pab = pabellonService.consultar(pab);

		Habitacion habitacion = new Habitacion();
		habitacion.setCodigo_empresa(empresa.getCodigo_empresa());
		habitacion.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		habitacion.setTipo_atencion((cama != null ? cama.getTipo_atencion()
				: ""));
		habitacion.setCodigo_pabellon((cama != null ? cama.getCodigo_pabellon()
				: 0));
		habitacion.setCodigo((cama != null ? cama.getCodigo_habitacion() : 0));
		habitacion.setCodigo_centro(codigo_centro);
		habitacion = habitacionService.consultar(habitacion);

		String pabellon = (pab != null ? pab.getNombre() + " " : "")
				+ (habitacion != null ? habitacion.getNombre() + " " : "")
				+ (cama != null ? cama.getNombre() : "");

		tbxCama.setValue(codigo_cama);
		tbxCodigo_atencion.setValue(cama != null ? cama.getTipo_atencion() + ""
				: "");
		tbxCodigo_pabellon.setValue(cama != null ? cama.getCodigo_pabellon()
				+ "" : "");
		tbxCodigo_habitacion.setValue(cama != null ? cama
				.getCodigo_habitacion() + "" : "");
		// tbxCama.setObject(cama);
		tbxNomCama.setValue(pabellon);
	}

	public boolean validarDatosPaciente(final Paciente registro) {

		Date fecha_nacimiento = registro.getFecha_nacimiento();
		String pertenencia = registro.getPertenencia_etnica();
		String direccion = registro.getDireccion();
		String telefono = registro.getTel_res();
		String ocupacion = registro.getCodigo_ocupacion();
		String nivel_educativo = registro.getCodigo_educativo();

		if (fecha_nacimiento == null || pertenencia.isEmpty()
				|| direccion == null || telefono == null || ocupacion.isEmpty()
				|| nivel_educativo.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	public void editarPaciente() {
		if (!tbxNro_identificacion.getValue().isEmpty()) {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(tbxNro_identificacion.getValue());
			paciente = pacienteService.consultar(paciente);
			if (paciente != null) {
				modificarPaciente(paciente);
			}
		}
	}

	public void modificarPaciente(final Paciente registro) {

		Map<String, Object> mapa_datos = new HashMap<String, Object>();
		mapa_datos.put("paciente", registro);
		mapa_datos.put("usuarios", usuarios);
		final Gestion_informacionAction window = (Gestion_informacionAction) Executions
				.createComponents("/pages/gestion_informacion.zul", this,
						mapa_datos);
		window.setTitle("Gestion Informacion del Paciente");
		window.setMode("modal");
		window.setClosable(false);
		window.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				cargarDesdeSeleccionPaciente(registro);
			}
		});

		window.getBotonGuardar().addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						boolean guardar = window.guardarDatos();
						if (guardar) {
							cargarDesdeSeleccionPaciente(registro);
							window.onClose();
						}
					}

				});
		window.getBotonCerrar().addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						cargarDesdeSeleccionPaciente(registro);
						window.onClose();
					}

				});
	}

	public void cargarDesdeSeleccionPaciente(Paciente registro) {
		cargarDatosPaciente(registro);
		listarCitasPendientesPaciente(registro, true);
		btnProcedimientos.setVisible(false);
		btnMedicamentos.setVisible(false);
		/*
		 * este para validar los servicios que se les puede brindar al paciente
		 */
		boolean tiene_servicios = ServiciosDisponiblesUtils
				.validarTipoViaIngresoAdmisionNuevo(lbxVia_ingreso, registro,
						registro.getCodigo_administradora(), false,
						listado_contratos_pacientes);

		lbxCodigo_cita.setDisabled(!tiene_servicios);
		lbxVia_ingreso.setDisabled(!tiene_servicios);

		if (lbxVia_ingreso_defecto.getSelectedIndex() != 0) {
			String via_ingreso_defecto = lbxVia_ingreso_defecto
					.getSelectedItem().getValue().toString();
			Listitem listitem = ServiciosDisponiblesUtils.getListitem(
					lbxVia_ingreso, via_ingreso_defecto);
			if (listitem != null && listitem.isVisible()) {
				listitem.setSelected(true);
			} else {
				lbxVia_ingreso.setSelectedIndex(0);
			}
			seleccionarVia_ingreso(false, false);
			mostrarItemPorDefecto();
		}
	}

	@SuppressWarnings("unchecked")
	public void listarProgramasPyp(Listbox listbox, Listbox lbxProgramas) {
		lbxProgramas.getChildren().clear();
		lbxProgramas.appendItem("-- Seleccione --", "");
		List<Listitem> listado_items = listbox.getItems();
		for (Listitem listitem : listado_items) {
			if (listitem.isVisible()
					&& !listitem.getValue().toString().isEmpty()) {
				Map<String, Object> mapa_datos = (Map<String, Object>) listitem
						.getAttribute("MAPA_SERVICIO_CONSECUTIVO");
				if (mapa_datos != null) {
					Configuracion_admision_ingreso configuracion_aux = (Configuracion_admision_ingreso) mapa_datos
							.get("CONFIGURACION_ADMISION");
					if (configuracion_aux != null
							&& configuracion_aux.getEs_pyp()
							&& configuracion_aux.getPrograma_lab_pyp()
							&& !configuracion_aux.getLaboratorio_pyp()) {
						Listitem listitem_aux = new Listitem();
						listitem_aux.setValue(listitem.getValue());
						listitem_aux.setLabel(listitem.getLabel());
						lbxProgramas.appendChild(listitem_aux);
					}
				}
			}
		}
		lbxProgramas.setSelectedIndex(0);
	}

	public void mostrarVentanaProcedimientos(boolean mostrar) {
		// log.info("Ejecutando metodo ===> mostrarVentanaProcedimientos()");
		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();
		if (configuracion_admision_ingreso != null
				&& configuracion_admision_ingreso.getLaboratorio_pyp()) {
			if (lbxPrograma_pyp.getSelectedItem() == null
					|| lbxPrograma_pyp.getSelectedItem().getValue().toString()
							.isEmpty()) {
				Clients.scrollIntoView(lbxPrograma_pyp);
				MensajesUtil.notificarAlerta("Seleccionar el programa pyp",
						lbxPrograma_pyp);
				return;
			}
		}
		if (tbxNro_identificacion.getRegistroSeleccionado() != null) {
			Contratos contratos = null;
			if (lbxContratos.getSelectedItem() != null
					&& !(lbxContratos.getSelectedItem().getValue() instanceof String)) {
				if (lbxContratos.getSelectedItem().getValue() instanceof Manuales_tarifarios) {
					contratos = (Contratos) lbxContratos.getSelectedItem()
							.getAttribute("CONTRATO_CONSULTADO");
				} else if (lbxContratos.getSelectedItem().getValue() instanceof Contratos) {
					contratos = (Contratos) lbxContratos.getSelectedItem()
							.getValue();
				}
				if (procedimientos_admisionAction == null) {
					Map<String, Object> mapa_datos = new HashMap<String, Object>();
					procedimientos_admisionAction = (Procedimientos_admisionAction) Executions
							.createComponents(
									"/pages/procedimientos_admision.zul", this,
									mapa_datos);

					procedimientos_admisionAction
							.setTitle("Procedimientos a facturar");
					procedimientos_admisionAction.setMode("modal");
					procedimientos_admisionAction.setVisible(false);
				}

				boolean laboratorio_pyp = configuracion_admision_ingreso
						.getLaboratorio_pyp();

				String via_ingreso = !laboratorio_pyp ? lbxVia_ingreso
						.getSelectedItem().getValue().toString()
						: lbxPrograma_pyp.getSelectedItem().getValue()
								.toString();

				procedimientos_admisionAction.mostrarServicios(via_ingreso,
						laboratorio_pyp, (Paciente) tbxNro_identificacion
								.getRegistroSeleccionado(),
						isParticularAtencion(administradora), contratos);

				procedimientos_admisionAction.setVisible(mostrar);

			} else {
				Clients.scrollIntoView(lbxContratos);
				MensajesUtil.notificarAlerta("Seleccionar contrato",
						lbxContratos);
			}
		}
	}

	public void mostrarVentanaMedicamentos(boolean mostrar) {
		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();
		if (tbxNro_identificacion.getRegistroSeleccionado() != null) {
			if (medicamentos_admisionAction == null) {
				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				medicamentos_admisionAction = (Medicamentos_admisionAction) Executions
						.createComponents("/pages/medicamentos_admision.zul",
								this, mapa_datos);

				medicamentos_admisionAction.setTitle("Medicamentos a facturar");
				medicamentos_admisionAction.setMode("modal");
				medicamentos_admisionAction.setVisible(false);
			}

			// Orden_servicio orden_servicio = getOrdenServicio();
			// if (orden_servicio != null) {
			// procedimientos_admisionAction.mostrarOrdenServicio(orden_servicio);
			medicamentos_admisionAction.mostrarServicios(lbxVia_ingreso
					.getSelectedItem().getValue().toString(),
					(Paciente) tbxNro_identificacion.getRegistroSeleccionado(),
					isParticularAtencion(administradora), contratos);
			// }

			medicamentos_admisionAction.setVisible(mostrar);

		}
	}

	public void validarFechaIngreso(Object objeto) {

		final Admision admision = (Admision) objeto;

		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String fecha_sistema = df.format(date);

		Date anio_ingreso = dtbxFecha_ingreso.getValue();
		DateFormat df2 = DateFormat.getDateInstance();
		String fecha_ingreso = df2.format(anio_ingreso);

		if (!fecha_ingreso.equals(fecha_sistema)) {
			Messagebox.show("La fecha de ingreso  " + fecha_ingreso
					+ "  es diferente a la fecha actual " + fecha_sistema
					+ " \n Desea cambiarla? ", "Alerta", Messagebox.YES
					+ Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								mostrarDatos(admision, false);
								dtbxFecha_ingreso.setFocus(true);
								dtbxFecha_ingreso
										.setStyle("text-transform:uppercase;background-color:#F6BBBE");
							}
						}
					});
		}

	}

	public void habilitarEspecialidadesRelacionadas(String via_ingreso) {
		for (Listitem listitem : lbxCodigo_especialidad.getItems()) {
			if (!listitem.getValue().toString().isEmpty()) {
				listitem.setVisible(false);
			}
		}
		if (!via_ingreso.isEmpty()) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_via_ingreso", via_ingreso);
			List<Via_ingreso_especialidad> listado_especialidades = generalExtraService
					.listar(Via_ingreso_especialidad.class, parametros);
			int contador = 0;
			for (Listitem listitem : lbxCodigo_especialidad.getItems()) {
				String codigo_esp = listitem.getValue().toString();
				if (!codigo_esp.isEmpty()) {
					for (Via_ingreso_especialidad especialidad : listado_especialidades) {
						if (especialidad.getCodigo_especialidad().equals(
								codigo_esp)) {
							listitem.setVisible(true);
							listitem.setSelected(true);
							contador++;
							break;
						}
					}
				}
			}

			if (contador != 1) {
				lbxCodigo_especialidad.setSelectedIndex(0);
			}
		} else {
			for (Listitem listitem : lbxCodigo_especialidad.getItems()) {
				if (!listitem.getValue().toString().isEmpty()) {
					listitem.setVisible(true);
				}
			}
		}
	}

	private List<Contratos> getListadoMultiplesContratos(String via_ingreso) {
		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();
		Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
		Admision admision_aux = new Admision();
		admision_aux.setCodigo_empresa(codigo_empresa);
		admision_aux.setCodigo_sucursal(codigo_sucursal);
		admision_aux.setNro_identificacion(paciente.getNro_identificacion());
		admision_aux.setCodigo_administradora(administradora.getCodigo());
		admision_aux.setVia_ingreso(via_ingreso);
		admision_aux.setId_plan(contratos != null ? contratos.getId_plan()
				: null);
		admision_aux
				.setParticular((administradora != null && administradora
						.getTipo_aseguradora()
						.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) ? "S"
						: "N");

		List<Contratos> listado_aux = new ArrayList<Contratos>();

		List<Via_ingreso_contratadas> listado_vias_ingreso = contratos != null ? ServiciosDisponiblesUtils
				.getListado_via_ingreso_contratadas(admision_aux)
				: ServiciosDisponiblesUtils.getListado_via_ingreso_contratadas(
						admision_aux, listado_contratos_pacientes);
		if (listado_vias_ingreso.size() == 1) {
			Via_ingreso_contratadas via_ingreso_contratadas = listado_vias_ingreso
					.get(0);
			for (Contratos contratos_aux : listado_contratos_pacientes) {
				if (contratos_aux.getId_plan().equals(
						via_ingreso_contratadas.getId_plan())) {
					listado_aux.add(contratos_aux);
				}
			}
		} else if (!listado_vias_ingreso.isEmpty()) {
			for (Via_ingreso_contratadas via_ingreso_contratadas : listado_vias_ingreso) {
				log.info("via_ingreso_contratadas ===> "
						+ via_ingreso_contratadas);
				boolean encontrado = false;
				for (Contratos contratos_aux : listado_contratos_pacientes) {
					if (contratos_aux.getId_plan().equals(
							via_ingreso_contratadas.getId_plan())
							&& contratos_aux.getCodigo_administradora().equals(
									via_ingreso_contratadas
											.getCodigo_administradora())) {
						listado_aux.add(contratos_aux);
						encontrado = true;
						break;
					}
				}
				if (!encontrado) {
					Contratos contratos_aux = new Contratos();
					contratos_aux.setCodigo_empresa(via_ingreso_contratadas
							.getCodigo_empresa());
					contratos_aux.setCodigo_sucursal(via_ingreso_contratadas
							.getCodigo_sucursal());
					contratos_aux.setId_plan(via_ingreso_contratadas
							.getId_plan());
					contratos_aux
							.setCodigo_administradora(via_ingreso_contratadas
									.getCodigo_administradora());
					contratos_aux = contratosService.consultar(contratos_aux);
					listado_aux.add(contratos_aux);
				}
			}
		}
		return listado_aux;

	}

	public void mostrarVentanaCambioPlanes(Admision admision) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("ADMISION", admision);
		final Window window = (Window) Executions.createComponents(
				"/pages/cambio_planes.zul", this, parametros);
		window.doModal();

	}

	public void buscarCentro() {
		String valor = bandboxBuscar_centros.getValue().trim().toUpperCase();
		if (!valor.isEmpty()) {
			List<Listitem> listado = lbxCentros_atencion.getItems();
			for (Listitem listitem : listado) {
				Centro_atencion centro_atencion = (Centro_atencion) listitem
						.getValue();
				if (centro_atencion.getNombre_centro().toUpperCase()
						.contains(valor)
						|| valor.equals(centro_atencion.getCodigo_centro())) {
					Clients.scrollIntoView(listitem);
					MensajesUtil.notificarInformacion("Resultado encontrado",
							listitem);
					break;
				}
			}
		}
	}

	public void listarCentros() {
		lbxCentros_atencion.getItems().clear();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		List<Centro_atencion> listado_centros = centro_atencionService
				.listar(parametros);
		for (Centro_atencion centro_atencion : listado_centros) {
			Listitem listitem = new Listitem();
			listitem.setValue(centro_atencion);
			listitem.appendChild(new Listcell());
			listitem.appendChild(new Listcell(centro_atencion
					.getCodigo_centro()
					+ " - "
					+ centro_atencion.getNombre_centro()));
			if (centro_atencion_session != null) {
				if (centro_atencion.getCodigo_centro().equals(
						centro_atencion_session.getCodigo_centro())) {
					listitem.setSelected(true);
				}
			}
			lbxCentros_atencion.appendChild(listitem);
		}
	}

}
