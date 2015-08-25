package healthmanager.controller;

import healthmanager.controller.Recibo_cajaAction.IOnGuardarCaja;
import healthmanager.controller.SeleccionadorAction.OnSeleccionador;
import healthmanager.controller.SeleccionadorAdministradoraAction.OnSeleccionadorAseguradorasAccion;
import healthmanager.controller.complemento.VentanaCancelacion;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anio_cuota_moderadora;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Hospitalizacion;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Recien_nacido;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Urgencias;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.bean.Via_ingreso_rol;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Admision_camaService;
import healthmanager.modelo.service.Afiliaciones_meService;
import healthmanager.modelo.service.Anio_cuota_moderadoraService;
import healthmanager.modelo.service.Aportes_cotizacionesService;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Copago_estratoService;
import healthmanager.modelo.service.Datos_consultaService;
import healthmanager.modelo.service.Datos_medicamentosService;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.Datos_servicioService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Facturacion_medicamentoService;
import healthmanager.modelo.service.Facturacion_servicioService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.HospitalizacionService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Pacientes_contratosService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Recien_nacidoService;
import healthmanager.modelo.service.Registro_admisionService;
import healthmanager.modelo.service.Salario_anualService;
import healthmanager.modelo.service.Servicios_contratadosService;
import healthmanager.modelo.service.UrgenciasService;
import healthmanager.modelo.service.Via_ingreso_contratadasService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IEstados;
import com.framework.constantes.IKeyCode;
import com.framework.constantes.IRoles;
import com.framework.constantes.ITiposServicio;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro.ETIPO_SERVICIO;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.LabelState;
import com.framework.res.Res;
import com.framework.res.ResCalculadorDeRangoCuota;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Bodega;
import contaweb.modelo.bean.Caja;
import contaweb.modelo.bean.Detalle_caja;
import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Detalle_pagare;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Nota_contable;
import contaweb.modelo.bean.Pagare;
import contaweb.modelo.service.ArticuloService;
import contaweb.modelo.service.BodegaService;
import contaweb.modelo.service.CajaService;
import contaweb.modelo.service.FacturacionService;
import contaweb.modelo.service.PagareService;

public class Anular_facturasAction extends GeneralComposer {

	/**
	 * 	
	 */
	private static final long serialVersionUID = -1034064804816436142L;
	// Componentes //
	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private Centro_atencionService centro_atencionService;
	@WireVariable
	private FacturacionService facturacionService;
	@WireVariable
	private PrestadoresService prestadoresService;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private AdmisionService admisionService;
	@WireVariable
	private AdministradoraService administradoraService;
	@WireVariable
	private Configuracion_admision_ingresoService configuracion_admision_ingresoService;
	@WireVariable
	private BodegaService bodegaService;
	@WireVariable
	private ContratosService contratosService;
	@WireVariable
	private Via_ingreso_contratadasService via_ingreso_contratadasService;
	@WireVariable
	private Pacientes_contratosService pacientes_contratosService;
	@WireVariable
	private ProcedimientosService procedimientosService;
	@WireVariable
	private Datos_servicioService datos_servicioService;
	@WireVariable
	private Admision_camaService admision_camaService;
	@WireVariable
	private Datos_medicamentosService datos_medicamentosService;
	@WireVariable
	private Facturacion_servicioService facturacion_servicioService;
	@WireVariable
	private Facturacion_medicamentoService facturacion_medicamentoService;
	@WireVariable
	private UrgenciasService urgenciasService;
	@WireVariable
	private HospitalizacionService hospitalizacionService;
	@WireVariable
	private Recien_nacidoService recien_nacidoService;
	@WireVariable
	private CajaService cajaService;
	@WireVariable
	private Registro_admisionService registro_admisionService;
	@WireVariable
	private Afiliaciones_meService afiliaciones_meService;
	@WireVariable
	private Salario_anualService salario_anualService;
	@WireVariable
	private Aportes_cotizacionesService aportes_cotizacionesService;
	@WireVariable
	private Anio_cuota_moderadoraService anio_cuota_moderadoraService;
	@WireVariable
	private PagareService pagareService;
	@WireVariable
	private Copago_estratoService copago_estratoService;
	@WireVariable
	private Datos_consultaService datos_consultaService;
	@WireVariable
	private Datos_procedimientoService datos_procedimientoService;
	@WireVariable
	private ArticuloService articuloService;
	@WireVariable
	private Servicios_contratadosService servicios_contratadosService;
	@WireVariable
	private GeneralExtraService generalExtraService;

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
	private BandboxRegistrosMacro tbxNro_identificacion;
	@View
	private Textbox tbxNomPaciente;
	@View
	private Toolbarbutton btnLimpiarPaciente;

	@View
	private Listbox lbxNro_ingreso;
	@View
	private Textbox tbxAseguradora;

	@View
	private Datebox dtbxFecha;
	@View
	private Datebox dtbxFecha_ingreso;
	@View
	private Datebox dtbxFecha_egreso;
	@View
	private Textbox tbxNro_autorizacion;

	@View
	private Textbox tbxNro_poliza;
	@View
	private Textbox tbxObservacion;

	@View
	private Intbox ibxPlazo;
	@View
	private Textbox tbxDescripcion;

	@View(isMacro = true)
	private ServiciosFacturacionMacro lbxServicios;

	@View
	private Listbox lbxFormato;

	@View
	private Toolbarbutton btAnular;
	@View
	private Caption capPaciente;

	@View
	private Toolbarbutton btImprimir;
	@View
	private Toolbarbutton btImprimir_pre;
	@View
	private Toolbarbutton btCont;
	@View
	private Toolbarbutton btPrintCont;
	@View
	private Toolbarbutton btPrintVerCont;
	@View
	private Toolbarbutton btPrintCop;

	// centro de salud
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCentro_salud;
	@View
	private Textbox tbxNomCentroAtencion;
	@View
	private Toolbarbutton btnLimpiarCentroAtencion;

	// Prestador
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxPrestador;
	@View
	private Textbox tbxNomPrestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;

	@View
	private Textbox tbxContrato;
	@View
	private Listbox lbxVia_ingreso;
	@View
	private Listbox lbxCausa_externa;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_administradora;
	@View
	private Textbox tbxNomAdministradora;
	@View
	private Popup popup;
	@View
	private Toolbarbutton btnConfiguracionBusqeuda;

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
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_final;

	@View
	private Listbox lbxContratos;

	@View
	private Bandbox bandboxBuscar_centros;
	@View
	private Listbox lbxCentros_atencion;
	@View
	private Toolbarbutton btnFiltro_centros;

	@View
	private Row rowProgramaPyp;

	@View
	private Listbox lbxPrograma_pyp;

	@View
	private Datebox dtbxFechaAccesoRapido;

	private Configuracion_admision_ingreso configuracion_admision_ingreso;

	// private Map<String, Object> datos_seleccion = new HashMap<String,
	// Object>();
	private static final String CONTRATO = "Cont";
	private static final String PRESTADOR = "Pre";

	@View
	private Label lbAutorizacion;

	// Codigo de la actualizacion de la seleccion de bodegas
	@View
	private Div divBodega_centro;
	@View
	private Listbox lbxBodegas_centros;
	@View
	private Checkbox chkAdmision_parto;
	@View
	private Toolbarbutton btnFiltro_ingreso;
	@View
	private Listbox lbxVias_ingreso;
	@View
	private Listbox lbxTipos_filtro;

	private final String[] idsExcluyentes = new String[] {
			"tbxNro_identificacion", "btnLimpiarPaciente", "tbxTipo",
			"btCancel", "btGuardar", "tbxAccion", "btNew", "lbxFormato",
			"btImprimir", "btImprimir_pre", "btPrintCont", "btCont",
			"btPrintVerCont", "btPrintCop", "toolbQuitarServicio",
			"tbxNomCentroAtencion", "tbxNomPrestador", "tbxNomPaciente",
			"btGuardarServicios", "btAnular", "btnConfiguracionBusqeuda",
			"btnFiltro_centros", "Refrescar", "tbxContrato",
			"tbxNomAdministradora", "lbxCausa_externa", "tbxNacimiento",
			"tbxEdad", "tbxSexo", "tbxTipo_identificacion", "tbxEstrato",
			"buttonInfo", "lbxNro_ingreso", "dtbxFechaAccesoRapido",
			"btVerificar_abiertas", "btnEgreso_urgencia" };
	private String tipo_cita = "";
	private Facturacion facturacion_current;

	private static final String CODIGO_COMPROBANTE_FHC = "15";

	private IOnGuardarCaja onGuardarCaja;

	public interface IOnGuardar {

		void guardar();

		boolean validar(Boolean confirmacion, String mensaje);
	}

	public static final String TIPO_FACTURA = "_TF";

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	/**
	 * @see healthmanager.controller.ZKWindow#init()
	 */
	@Override
	public void init() throws Exception {
		inicializarServicios();
		tbxNro_identificacion.inicializar(tbxNomPaciente, btnLimpiarPaciente);
		parametrizarBandbox();
		listarCombos();
		deshabilitarCampos(true);
		inicializarEventos();
		habilitarBusqueda();
		verificarTipoSucursal();
	}

	private void verificarTipoSucursal() {
		if (getSucursal().getTipo().equals(
				IConstantes.TIPOS_SUCURSAL_MEDICO_AUDITORES)) {
			lbAutorizacion.setValue("Nro caso: ");
		}
	}

	@Override
	public void _despuesIniciar() {
		tbxNro_identificacion.setFocus(true);
		aplicarCambiosModoFactura();
	}

	private void aplicarCambiosModoFactura() {
	}

	private boolean validarAccesoRapidoFecha() {
		if (dtbxFechaAccesoRapido.getValue() != null) {
			dtbxFecha_ingreso.setValue(dtbxFechaAccesoRapido.getValue());
			cargarFechaEgreso(dtbxFechaAccesoRapido.getValue());
			return true;
		}
		return false;
	}

	private void inicializarServicios() {
		lbxServicios.setGeneralComposer(this);
		lbxServicios.setPacienteService(pacienteService);
		lbxServicios.setFacturacionService(facturacionService);
		lbxServicios.setCopago_estratoService(copago_estratoService);
		lbxServicios
				.setConfiguracion_admision_ingresoService(configuracion_admision_ingresoService);
		lbxServicios.setDatos_consultaService(datos_consultaService);
		lbxServicios.setDatos_procedimientoService(datos_procedimientoService);
		lbxServicios.setProcedimientosService(procedimientosService);
		lbxServicios.setDatos_servicioService(datos_servicioService);
		lbxServicios.setAdmision_camaService(admision_camaService);
		lbxServicios.setDatos_medicamentosService(datos_medicamentosService);
		lbxServicios
				.setFacturacion_servicioService(facturacion_servicioService);
		lbxServicios
				.setFacturacion_medicamentoService(facturacion_medicamentoService);
		lbxServicios.setContratosService(contratosService);
		lbxServicios
				.setVia_ingreso_contratadasService(via_ingreso_contratadasService);
		lbxServicios.setElementoService(elementoService);
		lbxServicios.setArticuloService(articuloService);
	}

	/**
	 * Este metodo me permite habilitar consulta rapita para los bandbox
	 *
	 */
	private void habilitarBusqueda() {
		tbxNro_identificacion.setReadonly(false);
		tbxCentro_salud.setReadonly(false);
		tbxPrestador.setReadonly(false);
		tbxCentro_salud.setReadonly(true);
	}

	public void listarCombos() {
		listarParameter();
		cargarVias(lbxVias_ingreso);
		listarCentros();
		Utilidades.listarElementoListboxOrdenado(lbxVia_ingreso, true,
				elementoService);
		Utilidades.listarElementoListbox(lbxCausa_externa, true, 
				elementoService);
		inicializarNroIngreso();
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
		listitem.setValue("codigo_documento");
		listitem.setLabel("Nro registro");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("fecha::varchar");
		listitem.setLabel("Fecha(aaaa-mm-dd)");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	/**
	 * PARAMETRIZACION DE BANDBOX *
	 */
	private void parametrizarBandbox() {
		parametrizarBandboxPaciente();
		parametrizarAdministradora();
		parametrizarBandboxCentro();
		parametrizarBandboxPrestador();
		parametrizarResultadoPaginado();
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Facturacion> listarResultados(
					Map<String, Object> parametros) {
				List<Facturacion> listado = facturacionService
						.listarRegistros(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = facturacionService.totalResultados(parametros);
				// log.info("total ====> " + total);
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

	private void parametrizarBandboxPrestador() {
		tbxPrestador.inicializar(tbxNomPrestador, btnLimpiarPrestador);
		tbxPrestador
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Map<String, Object>>() {

					@Override
					public void renderListitem(Listitem listitem,
							Map<String, Object> registro) {

						// Extraemos valores
						String nro_identificacion = (String) registro
								.get("nro_identificacion");
						String nombres = (String) registro.get("nombres");
						String apellidos = (String) registro.get("apellidos");
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
						}
						String via_ingreso = lbxVia_ingreso.getSelectedItem()
								.getValue();
						if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)
								|| via_ingreso
										.equals(IVias_ingreso.ODONTOLOGIA2)
								|| via_ingreso
										.equals(IVias_ingreso.URGENCIA_ODONTOLOGICO)
								|| via_ingreso.equals(IVias_ingreso.SALUD_ORAL)) {
							nombre_medico = "ODONTÓLOGO";
						}

						listcell = new Listcell();
						listcell.appendChild(new Label(nombre_medico));
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
							// parametros.put("fecha_solicitada", new Timestamp(
							// dtbxFecha_ingreso.getValue().getTime()));

							List<Via_ingreso_rol> via_ingreso_rols = getRolViaIngreso();
							if (!via_ingreso_rols.isEmpty()) {
								parametros.put("rol", via_ingreso_rols);
							}
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

						Prestadores prestadores = new Prestadores();
						prestadores.setCodigo_empresa(getSucursal()
								.getCodigo_empresa());
						prestadores.setCodigo_sucursal(getSucursal()
								.getCodigo_sucursal());
						prestadores.setNro_identificacion(nro_identificacion);
						prestadores = prestadoresService.consultar(prestadores);
						lbxServicios.setPrestadores(prestadores);
						bandbox.setAttribute(PRESTADOR, prestadores);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
					}

				});
	}

	public String habilitarFiltroEnfermera() {
		String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue()
				.toString();
		return Utilidades.habilitarFiltroEnfermera(via_ingreso, tipo_cita,
				getParametros_empresa());
	}

	private List<Via_ingreso_rol> getRolViaIngreso() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codigo_via_ingreso", lbxVia_ingreso.getSelectedItem()
				.getValue().toString());
		param.put("codigo_empresa", codigo_empresa);
		return generalExtraService.listar(Via_ingreso_rol.class, param);
	}

	private void parametrizarBandboxCentro() {
		tbxCentro_salud.inicializar(tbxNomCentroAtencion,
				btnLimpiarCentroAtencion);
		tbxCentro_salud
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {
						listitem.appendChild(new Listcell(registro
								.getCodigo_centro()));
						listitem.appendChild(new Listcell(registro
								.getNombre_centro()));
					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						return centro_atencionService.listar(parametros);
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

					}
				});
	}

	private void parametrizarBandboxPaciente() {
		tbxNro_identificacion.setReadonly(false);
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
						try {
							bandbox.setValue(registro.getDocumento());
							textboxInformacion.setValue(registro
									.getNombreCompleto());
							cargarDatosPacientes(null, registro, true);
							// if (lbxNro_ingreso.getSelectedItem() != null)
							// selectedAdmision(
							// lbxNro_ingreso.getSelectedItem(), true);
							lbxVia_ingreso.setFocus(true);
							lbxVia_ingreso.focus();
							try {
								Utilidades.validacionFechasFuturas(false,
										dtbxFecha_ingreso, dtbxFecha);
							} catch (WrongValueException e) {
								Notificaciones
										.mostrarNotificacionAlerta(
												"Advertencia",
												"La fecha de la admision es mayor que la fecha actual ",
												3000);
							}
						} catch (Exception e) {
							MensajesUtil.mensajeError(e, "",
									Anular_facturasAction.this);
						}
						return true;

					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						limpiarDatos();
					}
				});
	}

	private boolean cargarDatosPacientes(Admision admision, Paciente registro,
			boolean consultar_facturas) throws Exception {
		tbxNacimiento
				.setValue((registro != null ? new java.text.SimpleDateFormat(
						"dd/MM/yyyy").format(registro.getFecha_nacimiento())
						: ""));
		tbxEdad.setValue(Util.getEdadPrsentacionSimple(
				registro.getFecha_nacimiento(),
				new Timestamp(new Date().getTime())));

		tbxSexo.setValue((registro != null ? Utilidades.getNombreElemento(
				registro.getSexo(), "sexo", elementoService) : ""));
		tbxTipo_identificacion.setValue((registro != null ? registro
				.getTipo_identificacion() : ""));
		tbxEstrato.setValue((registro != null ? registro.getEstrato() : ""));

		if (admision == null) {
			Facturacion facturacion = new Facturacion();
			facturacion.setCodigo_empresa(registro.getCodigo_empresa());
			facturacion.setCodigo_sucursal(registro.getCodigo_sucursal());
			facturacion.setCodigo_tercero(registro.getNro_identificacion());
			List<Admision> listaAdmisiones = listarAdmisiones(facturacion,
					false);
			listarIngresos(lbxNro_ingreso, listaAdmisiones,
					!listaAdmisiones.isEmpty());
			admision = (!listaAdmisiones.isEmpty() ? listaAdmisiones.get(0)
					: null);

		} else {
			lbxNro_ingreso.getChildren().clear();
			Administradora admin = new Administradora();
			admin.setCodigo_empresa(admision.getCodigo_empresa());
			admin.setCodigo_sucursal(admision.getCodigo_sucursal());
			admin.setCodigo(admision.getCodigo_administradora());
			admin = administradoraService.consultar(admin);

			Listitem listitem = new Listitem();
			listitem.setValue(admision);
			listitem.setLabel(admision.getNro_ingreso() + " -- "
					+ (admin != null ? admin.getNombre() : ""));
			lbxNro_ingreso.appendChild(listitem);
			lbxNro_ingreso.setSelectedIndex(0);
		}

		deshabilitarCampos(false);

		ServiciosDisponiblesUtils.validarInformacionPaciente(registro);

		if (admision == null) {
			// cargamos informacion de administradora
			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(registro.getCodigo_empresa());
			administradora.setCodigo_sucursal(registro.getCodigo_sucursal());
			administradora.setCodigo(registro.getCodigo_administradora());
			administradora = administradoraService.consultar(administradora);
			tbxCodigo_administradora.setValue(registro
					.getCodigo_administradora());
			if (administradora != null) {
				tbxCodigo_administradora.seleccionarRegistro(administradora,
						administradora.getCodigo(), administradora.getNombre());
				tbxNomAdministradora
						.setValue((administradora != null ? administradora
								.getNombre() : ""));
				cambiarAdministradora(registro, administradora, true);
				tbxCodigo_administradora.setButtonVisible(true);
				// tbxCodigo_administradora.setReadonly(false);
			}
		} else {
			admision.setPaciente(registro);
			lbxServicios.setPaciente(registro);
			habilitarAccionCobroCopago(admision);
		}

		/*
		 * este para validar los servicios que se les puede brindar al paciente
		 */
		boolean tiene_servicios = ServiciosDisponiblesUtils
				.validarTipoViaIngresoAdmision(lbxVia_ingreso, registro,
						admision != null ? admision.getCodigo_administradora()
								: registro.getCodigo_administradora(), true,
						false, null, admision != null ? admision
								.getParticular().equals("S") : false);

		lbxVia_ingreso.setDisabled(!tiene_servicios);
		if (!tiene_servicios) {

		}

		// Seleccionamos la via de ingreso
		if (admision != null) {
			Utilidades.seleccionarListitem(lbxVia_ingreso,
					admision.getVia_ingreso());
			if (!admision.getPrograma_lab_pyp().isEmpty()) {
				rowProgramaPyp.setVisible(true);
				lbxPrograma_pyp.getItems().clear();
				Listitem listitem_aux_programa = new Listitem();
				listitem_aux_programa.setValue(admision.getPrograma_lab_pyp());
				Elemento elemento = new Elemento();
				elemento.setCodigo(admision.getPrograma_lab_pyp());
				elemento.setTipo("via_ingreso");
				elemento = elementoService.consultar(elemento);
				listitem_aux_programa.setLabel(elemento != null ? elemento
						.getDescripcion() : admision.getPrograma_lab_pyp());
				lbxPrograma_pyp.appendChild(listitem_aux_programa);
				listitem_aux_programa.setSelected(true);
			} else {
				rowProgramaPyp.setVisible(false);
			}
			Configuracion_admision_ingreso configuracion_admision_ingreso_aux = new Configuracion_admision_ingreso();
			configuracion_admision_ingreso_aux
					.setCodigo_empresa(codigo_empresa);
			configuracion_admision_ingreso_aux
					.setCodigo_sucursal(codigo_sucursal);
			configuracion_admision_ingreso_aux.setVia_ingreso(admision
					.getVia_ingreso());

			configuracion_admision_ingreso_aux = configuracion_admision_ingresoService
					.consultar(configuracion_admision_ingreso_aux);
			this.configuracion_admision_ingreso = configuracion_admision_ingreso_aux;
			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(admision.getCodigo_empresa());
			contratos.setCodigo_sucursal(admision.getCodigo_sucursal());
			contratos.setCodigo_administradora(admision
					.getCodigo_administradora());
			contratos.setId_plan(admision.getId_plan());
			contratos = contratosService.consultar(contratos);
			lbxServicios.setContratos(contratos);
			cargarAdmisiones(admision, registro, consultar_facturas);
			actualizarServicio(contratos, registro);

			lbxContratos.getChildren().clear();
			Listitem listitem_aux = new Listitem(
					contratos != null ? contratos.getNombre() + " - "
							+ contratos.getNro_contrato() : "No registrado",
					contratos);
			lbxContratos.appendChild(listitem_aux);
			listitem_aux.setSelected(true);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			mostrarComponenteBodegas(parameters);

			Administradora administradora = tbxCodigo_administradora
					.getRegistroSeleccionado();

			if (!contratos.getTipo_facturacion().equals(
					IConstantes.TIPO_CONTRATO_INDIVIDUAL_EVENTO)) {

			} else {

				if (rol_usuario.equalsIgnoreCase(IRoles.FACTURADOR)
						&& !parametros_empresa.getPermitir_facturador_evento()
						&& !administradora
								.getTipo_aseguradora()
								.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) {

				} else {

				}
			}

		} else {

		}

		return tiene_servicios;
	}

	public void seleccionarVia_ingreso() throws Exception {
		lbxServicios.limpiar(true);
		divBodega_centro.setVisible(false);

		String via_ingreso_sel = lbxVia_ingreso.getSelectedItem().getValue()
				.toString();

		chkAdmision_parto.setVisible(via_ingreso_sel
				.equalsIgnoreCase(IVias_ingreso.URGENCIA));

		Listitem listitem_programa_pyp = lbxPrograma_pyp.getSelectedItem();
		String via_ingreso_lab = listitem_programa_pyp != null ? listitem_programa_pyp
				.getValue().toString() : "";

		Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion",
				paciente != null ? paciente.getNro_identificacion() : "");

		parameters.put("anio", anio);
		parameters.put("via_ingreso", via_ingreso_sel);

		// log.info("configuracion_admision_ingreso ===> "
		// + configuracion_admision_ingreso);
		lbxContratos.getChildren().clear();

		if (!via_ingreso_sel.isEmpty()) {
			if (configuracion_admision_ingreso != null) {
				if (configuracion_admision_ingreso.getLaboratorio_pyp()) {
					if (!via_ingreso_lab.isEmpty()) {
						Listitem listitem_via_ingreso = lbxPrograma_pyp
								.getSelectedItem();
						Elemento via_ingreso = new Elemento(
								listitem_via_ingreso.getValue().toString(),
								"via_ingreso", listitem_via_ingreso.getLabel());
						// Actualizamos la via de ingreso en los servicios
						lbxServicios
								.setElemento_via_ingreso_principal(via_ingreso);
					}
				} else {
					Listitem listitem_via_ingreso = lbxVia_ingreso
							.getSelectedItem();
					Elemento via_ingreso = new Elemento(listitem_via_ingreso
							.getValue().toString(), "via_ingreso",
							listitem_via_ingreso.getLabel());
					// Actualizamos la via de ingreso en los servicios
					lbxServicios.setElemento_via_ingreso_principal(via_ingreso);
				}

				mostrarComponenteBodegas(parameters);

			}
		}

		lbxServicios.aplicarViaIngreso();

		List<Historia_clinica> listado_historias = generalExtraService.listar(
				Historia_clinica.class, parameters);
		if (!listado_historias.isEmpty()) {
			tipo_cita = "2";
		} else {
			tipo_cita = "1";
		}

		dtbxFecha.setFocus(true);
		validarSeleccionServicio();
	}

	private void mostrarComponenteBodegas(Map<String, Object> parameters) {
		if (parametros_empresa.getHabilitar_bodega_centro()) {
			if (configuracion_admision_ingreso.getSolicitar_informacion()
					.equals(Configuracion_admision_ingreso.TIPO_MEDICAMENTOS)) {
				divBodega_centro.setVisible(true);
				parameters.put("codigo_centro_bodega",
						centro_atencion_session.getCodigo_centro());
				List<Bodega> listado_bodegas = bodegaService.listar(parameters);
				lbxBodegas_centros.getItems().clear();
				if (!listado_bodegas.isEmpty()) {
					if (listado_bodegas.size() == 1) {
						Bodega bodega = listado_bodegas.get(0);
						Listitem listitem = new Listitem(bodega.getCodigo()
								+ " " + bodega.getNombre(), bodega);
						listitem.setSelected(true);
						lbxBodegas_centros.appendChild(listitem);
					} else {
						lbxBodegas_centros.appendItem(
								"-- Seleccione bodega --", "");
						for (Bodega bodega : listado_bodegas) {
							Listitem listitem = new Listitem(bodega.getCodigo()
									+ "-" + bodega.getNombre(), bodega);
							lbxBodegas_centros.appendChild(listitem);
						}
						lbxBodegas_centros.setSelectedIndex(0);
					}
				}

			}
		}
	}

	public void validarSeleccionServicio() {
		Admision admision = (Admision) lbxNro_ingreso.getSelectedItem()
				.getValue();
		if (admision == null
				|| !admision.getVia_ingreso().equals(
						lbxVia_ingreso.getSelectedItem().getValue())) {
			Calendar calendar = Calendar.getInstance();
			dtbxFecha.setValue(calendar.getTime());
			if (!validarAccesoRapidoFecha()) {
				dtbxFecha_ingreso.setValue(calendar.getTime());
				cargarFechaEgreso(calendar.getTime());
			}
		}
	}

	private void actualizarServicio(Contratos contratos_aux, Paciente registro)
			throws Exception {
		Listitem listitem = lbxVia_ingreso.getSelectedItem();
		if (listitem != null) {
			lbxServicios.habilitarAcciones(true);// Habilitaciones las acciones
			// para los servicios
			Elemento via_ingreso = new Elemento(listitem.getValue().toString(),
					"via_ingreso", listitem.getLabel());

			Administradora administradora = tbxCodigo_administradora
					.getRegistroSeleccionado();
			Prestadores prestadores = (Prestadores) tbxPrestador
					.getAttribute(PRESTADOR);
			Listitem listitem_nro_ingreso = lbxNro_ingreso.getSelectedItem();

			Admision admision = null;
			if (listitem_nro_ingreso != null) {
				admision = listitem_nro_ingreso.getValue();
			}

			lbxServicios.cargarDatos(registro, administradora, via_ingreso,
					prestadores, dtbxFecha.getValue(),
					dtbxFecha_ingreso.getValue(), dtbxFecha_egreso.getValue(),
					admision, contratos_aux);
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
							bandbox.setAttribute("admin", registro);
							textboxInformacion.setValue(registro.getNit() + " "
									+ registro.getNombre());

							// cambiar prestador
							cambiarAdministradora(
									paciente,
									paciente.getCodigo_empresa(),
									paciente.getCodigo_sucursal(),
									registro.getCodigo(),
									paciente.getCodigo_administradora().equals(
											registro.getCodigo()));
							return true;
						}
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	/**
	 * TODO agregar el cambio de contrato
	 *
	 */
	public void cambiarAdministradora(Paciente paciente, String codigo_empresa,
			String codigo_sucursal, String codigo_administradora, boolean propia) {
		try {
			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(codigo_empresa);
			administradora.setCodigo_sucursal(codigo_sucursal);
			administradora.setCodigo(codigo_administradora);
			administradora = administradoraService.consultar(administradora);
			tbxCodigo_administradora.seleccionarRegistro(administradora,
					administradora.getCodigo(), administradora.getNombre());
			cambiarAdministradora(paciente, administradora, propia);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	public void cambiarAdministradora(Paciente paciente,
			Administradora administradora, boolean propia) {
		try {
			if (!propia) {
				Map<String, Object> map = new HashMap<String, Object>();

				// Esta validacion es que busque cuando el servicio no sea para
				// particulares
				if (!isParticularAtencion()) {
					map.put("serviciosContrarados",
							ServiciosDisponiblesUtils.CODSER_URGENCIAS);
					// map.put("fecha_actual", "_fecha");
				}
				map.put("codigo_empresa", paciente.getCodigo_empresa());
				map.put("codigo_sucursal", paciente.getCodigo_sucursal());
				map.put("codigo_administradora", administradora.getCodigo());
				map.put("cerrado", false);
				List<Contratos> contratoslistado = contratosService.listar(map);
				if (contratoslistado.isEmpty()) {
					throw new ValidacionRunTimeException(
							"Esta aseguradora no tiene contrato de Urgencia configurado");
				} else if (contratoslistado.size() == 1) {
					setContrato(contratoslistado.get(0));
				} else {
					seleccionarContrato(contratoslistado);
				}
			} else {
				cambiarPacientesContratos(paciente, administradora.getCodigo());
			}
			lbxServicios
					.setAdministradora_particular(administradora
							.getTipo_aseguradora()
							.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES));

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
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
			}
		}
		tbxContrato.setValue(stringBuilder.toString());
		tbxContrato.setRows(listado_contratos.size() + 1);
		lbxContratos.getChildren().clear();
	}

	private void seleccionarContrato(final List<Contratos> contratoslistado) {
		SeleccionadorAction seleccionadorAction = (SeleccionadorAction) Executions
				.createComponents("/pages/seleccionador.zul", this, null);
		OnSeleccionador<Contratos> onSeleccionador = new OnSeleccionador<Contratos>() {

			@Override
			public void onSeleccionar(Contratos t) {
				setContrato(t);
				// solicitarFurips();
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
		tbxCodigo_administradora.setAttribute(CONTRATO, contratos);
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
		}
		tbxContrato.setValue(stringBuilder.toString());
		tbxContrato.setRows(2);

		Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();

		// Verificamos si particular la aseguradora
		if (isParticularAtencion()) {
			// buscamos los servicios de este contrato
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", contratos.getCodigo_empresa());
			param.put("codigo_sucursal", contratos.getCodigo_sucursal());
			param.put("codigo_administradora",
					contratos.getCodigo_administradora());
			param.put("id_contrato", contratos.getId_plan());

			// consultamos los servicios
			List<Servicios_contratados> listado_servicios_contratados = servicios_contratadosService
					.listar(param);

			if (listado_servicios_contratados.isEmpty()) {
				MensajesUtil.mensajeAlerta("Advertencia",
						"El contrato " + contratos.getNro_contrato() + " "
								+ contratos.getNombre()
								+ " no tiene servicios configurados");
			} else {
				ServiciosDisponiblesUtils.validarTipoViaIngresoAdmision(
						lbxVia_ingreso, paciente, listado_servicios_contratados
								.get(0).getCodigo_administradora(), true,
						false, listado_servicios_contratados.get(0)
								.getId_contrato(), true);
				lbxContratos.getChildren().clear();
			}
		} else {
			ServiciosDisponiblesUtils.validarTipoViaIngresoAdmision(
					lbxVia_ingreso, paciente,
					contratos.getCodigo_administradora(), true, false,
					contratos.getId_plan(), false);
			lbxContratos.getChildren().clear();
		}
	}

	private boolean isParticularAtencion() {
		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();
		return administradora != null ? administradora
				.getTipo_aseguradora()
				.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)
				: false;
	}

	/**
	 * FIN DE PARAMETRIZACION DE BANDBOX
	 *
	 */
	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			// buscarDatos();
		} else {
			limpiarDatos();
			lbxVia_ingreso.setDisabled(true);
			rowProgramaPyp.setVisible(false);

		}
		tbxAccion.setValue(accion);
	}

	private void inicializarNroIngreso() {
		lbxNro_ingreso.getChildren().clear();
		lbxNro_ingreso.appendChild(new Listitem("  --  ", ""));
		lbxNro_ingreso.setSelectedIndex(0);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		lbxContratos.getChildren().clear();
		tbxNro_identificacion.setValue("");
		tbxNro_identificacion.setDisabled(false);
		tbxNro_identificacion.limpiarSeleccion(false);
		tbxContrato.setValue(null);
		tbxDescripcion.setValue("FACTURACION DE PROCEDIMIENTOS Y CONSULTAS");
		ibxPlazo.setValue(30);

		btAnular.setDisabled(true);

		limpiarDatosPacientes();

		// dtbxFecha.setValue();
		// dtbxFecha_ingreso.setButtonVisible(false);
		// dtbxFecha_ingreso.setReadonly(true);
		dtbxFecha.setConstraint("");
		dtbxFecha_ingreso.setConstraint("");

		habilitarDatos(true);

		lbxVia_ingreso.setDisabled(false);
		lbxServicios.limpiar(true);
		inicializarNroIngreso();

		listarIngresos(lbxNro_ingreso, new ArrayList<Admision>(), true);
		deshabilitarCampos(true);

		lbxVia_ingreso.setDisabled(false);
		lbxNro_ingreso.setDisabled(false);

		facturacion_current = null;
		lbxCausa_externa.setDisabled(true);

		setTitulo("");

		if (centro_atencion_session != null) {
			tbxCentro_salud.seleccionarRegistro(centro_atencion_session,
					centro_atencion_session.getCodigo_centro(),
					centro_atencion_session.getNombre_centro());
		}
		_despuesIniciar();
	}

	private void limpiarDatosPacientes() {
		listarIngresos(lbxNro_ingreso, new ArrayList<Admision>(), true);
		deshabilitarCampos(true);
		tbxCodigo_administradora.limpiarSeleccion(false);
		tbxCodigo_administradora.setButtonVisible(false);
		// tbxCodigo_administradora.setReadonly(true);
	}

	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			if (!value.isEmpty()) {
				if (parameter.equals("codigo_tercero")) {
					parameters.put(parameter, value);
				} else {
					parameters.put("parameter", parameter);
					parameters.put("value", "%" + value + "%");
				}
			}

			agregarFiltrosFacturacion(parameters);

			parameters.put("post", "N");
			if (dtbxFecha_inicial.getValue() != null) {
				parameters.put("fecha_in", dtbxFecha_inicial.getValue());
			}
			if (dtbxFecha_final.getValue() != null) {
				parameters.put("fecha_fn", dtbxFecha_final.getValue());
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

			parameters.put("orden_factura", "order by id_factura");
			// resultadoPaginadoMacro.buscarDatos(parameters);
			resultadoPaginadoMacro.buscarDatos(parameters);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void agregarFiltrosFacturacion(Map<String, Object> parametros) {
		String seleccion = lbxTipos_filtro.getSelectedItem().getValue()
				.toString();
		if (seleccion.equals("01")) {
			// No hace nada
			// En esta seccion no se puede dejar sin filtro, ya que tambien trae
			// las facturas capitadas
			// ((tipo)::text = ANY ('{CAP,IND}'::text[]))
			// se utiliza a IN tiene menor costo
			parametros.put("tipo_in", new String[] {
					IConstantes.TIPO_FACTURA_CAP_INTERNA,
					IConstantes.TIPO_FACTURA_EVENTO_IND,
					IConstantes.TIPO_FACTURA_AGRUPADA,
					IConstantes.TIPO_FACTURA_PORTABILIDAD });
		} else if (seleccion.equals("02")) {
			// capitadas
			parametros.put("tipo", IConstantes.TIPO_FACTURA_CAP_INTERNA);
		} else if (seleccion.equals("03")) {
			parametros.put("tipo", IConstantes.TIPO_FACTURA_EVENTO_IND);
		} else if (seleccion.equals("04")) {
			parametros.put("tipo", IConstantes.TIPO_FACTURA_EVENTO_IND);
			parametros.put("codigo_documento_notnull",
					"codigo_documento_notnull");
		} else if (seleccion.equals("05")) {
			parametros.put("tipo", IConstantes.TIPO_FACTURA_EVENTO_IND);
			parametros.put("codigo_documento_null", "codigo_documento_null");
		} else if (seleccion.equals("06")) {
			parametros.put("tipo", IConstantes.TIPO_FACTURA_PORTABILIDAD);
		} else if (seleccion.equals("07")) {
			parametros.put("tipo", IConstantes.TIPO_FACTURA_PORTABILIDAD);
			parametros.put("codigo_documento_notnull",
					"codigo_documento_notnull");
		} else if (seleccion.equals("08")) {
			parametros.put("tipo", IConstantes.TIPO_FACTURA_PORTABILIDAD);
			parametros.put("codigo_documento_null", "codigo_documento_null");
		}
	}

	/**
	 * Este metodo me permite filtrar los tipos de facturas
	 *
	 * @author Luis Miguel
	 *
	 */

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Facturacion facturacion = (Facturacion) objeto;

		Admision admision = facturacion.getAdmision();

		Paciente paciente = admision != null ? admision.getPaciente() : null;

		String nombres_paciente = "(* PACIENTE NO EXISTE *)";
		String apellidos_paciente = "(* PACIENTE NO EXISTE *)";

		String via_ingreso = "";

		if (paciente != null) {
			nombres_paciente = paciente.getNombre1() + " "
					+ paciente.getNombre2();
			apellidos_paciente = paciente.getApellido1() + " "
					+ paciente.getApellido2();
			via_ingreso = facturacion.getEvia() != null ? facturacion.getEvia()
					.getDescripcion() : "(NO EXISTE CODIGO: "
					+ admision.getVia_ingreso() + ")";
		}

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(FormularioUtil.getLabelSize(
				facturacion.getCodigo_documento_res() != null ? facturacion
						.getCodigo_documento_res() : "", "11px"));
		fila.appendChild(FormularioUtil.getLabelSize(
				facturacion.getNro_atencion() + "", "11px"));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(facturacion.getFecha()) + ""));
		fila.appendChild(new Label(facturacion.getCodigo_tercero() + ""));
		fila.appendChild(new Label(facturacion.getNro_ingreso() + ""));
		fila.appendChild(new Label(apellidos_paciente + ""));
		fila.appendChild(new Label(nombres_paciente + ""));
		fila.appendChild(new Label(via_ingreso));
		fila.appendChild(new Label(
				facturacion.getEstado().equals("CONT") ? "SI" : "NO"));
		final Label label_estado = new Label(
				facturacion.getDelete_date() != null ? "SI" : "NO");
		fila.appendChild(label_estado);

		hbox.appendChild(space);

		Toolbarbutton toolbarbutton = new Toolbarbutton("",
				"/images/editar.gif");
		toolbarbutton.setTooltiptext("Editar");
		toolbarbutton.setStyle("cursor: pointer");
		toolbarbutton.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(facturacion);
			}
		});
		hbox.appendChild(toolbarbutton);

		final Toolbarbutton toolbarbutton2 = new Toolbarbutton("",
				"/images/cancelar_mini.png");
		toolbarbutton2.setTooltiptext("Anular");
		toolbarbutton2.setStyle("cursor: pointer");

		toolbarbutton2.setDisabled(facturacion.getEstado().equals(
				IEstados.FACTURACION_ANULADA));

		toolbarbutton2.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que deseas anular este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									anularCompletamenteFacturacion(facturacion,
											toolbarbutton2, label_estado);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(toolbarbutton2);

		fila.appendChild(hbox);

		return fila;
	}

	public void anularFactura() {
		if (facturacion_current != null) {
			Messagebox.show("Esta seguro que deseas anular este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								anularCompletamenteFacturacion(
										facturacion_current, null, null);
								buscarDatos();
							}
						}
					});
		} else {
			MensajesUtil.mensajeAlerta("Advertencia",
					"La factura no se ha generado todabia");
		}
	}

	public void anularCompletamenteFacturacion(
			final Facturacion facturacion_aux, final Component target,
			final Label label_fact) {

		final VentanaCancelacion window_aux = new VentanaCancelacion(
				"Anulando Factura. Escriba una observacion por la cual esta factura sera anulada",
				getPage());
		window_aux.setClosable(true);
		window_aux.setEvento(new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Facturacion facturacion = facturacionService
						.consultar(facturacion_aux);
				facturacion.setEstado(IEstados.FACTURACION_ANULADA);
				facturacion.setDelete_date(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				facturacion.setDelete_user(getUsuarios().getCodigo());
				facturacion.setMotivo_anulacion(window_aux.getValor());
				facturacionService.anularFactura(facturacion);
				btAnular.setDisabled(true);
				MensajesUtil.mensajeInformacion("Informacion",
						"Admision cancelda satisfactoriamente",
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {
								window_aux.onClose();
								if (target != null)
									((Toolbarbutton) target).setDisabled(true);
								if (label_fact != null)
									label_fact.setValue("SI");
							}
						});

			}
		});
		window_aux.doModal();

	}

	public void deshabilitarCampos(boolean sw) {
		FormularioUtil.deshabilitarComponentes(groupboxEditar, sw,
				idsExcluyentes);
		// bloqueoBotonGuardar(sw);
		if (sw) {
			listarIngresos(lbxNro_ingreso, new ArrayList<Admision>(), true);
		}
	}

	public void listarIngresos(Listbox listbox, List<Admision> listaAdmisiones,
			boolean select) {
		listbox.getItems().clear();
		Listitem listitem;

		for (Admision a : listaAdmisiones) {
			Administradora admin = new Administradora();
			admin.setCodigo_empresa(a.getCodigo_empresa());
			admin.setCodigo_sucursal(a.getCodigo_sucursal());
			admin.setCodigo(a.getCodigo_administradora());
			admin = administradoraService.consultar(admin);

			listitem = new Listitem();
			listitem.setValue(a);
			listitem.setLabel(a.getNro_ingreso() + " -- "
					+ (admin != null ? admin.getNombre() : ""));
			listbox.appendChild(listitem);
		}

		if (select) {
			listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("-- --");
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		} else {
			listbox.appendChild(new Listitem("NO SE ENCONTRO ADMISION", null));
			listbox.setSelectedIndex(0);
		}
	}

	public List<Admision> listarAdmisiones(Facturacion facturacion, boolean sw) {
		Map<String, Object> paramAdmision = new HashMap<String, Object>();
		paramAdmision.put("codigo_empresa", facturacion.getCodigo_empresa());
		paramAdmision.put("codigo_sucursal", facturacion.getCodigo_sucursal());
		paramAdmision
				.put("nro_identificacion", facturacion.getCodigo_tercero());
		paramAdmision.put("order", "fecha_ingreso desc");
		if (sw) {
			paramAdmision.put("nro_ingreso", facturacion.getNro_ingreso());
		} else {
			paramAdmision.put("estado", "1");
		}

		List<Admision> listaAdmisiones = admisionService
				.listarTabla(paramAdmision);

		return listaAdmisiones;
	}

	public void cargarAdmisiones(Admision aux2, Paciente registro,
			boolean consultar_facturas) {
		if (aux2 != null) {
			dtbxFecha.setValue(aux2.getFecha_ingreso());
			deshabilitarCampos(false);

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(aux2.getCodigo_empresa());
			administradora.setCodigo_sucursal(aux2.getCodigo_sucursal());
			administradora.setCodigo(aux2.getCodigo_administradora());
			administradora = administradoraService.consultar(administradora);
			tbxCodigo_administradora.setValue(aux2.getCodigo_administradora());
			if (administradora != null) {
				if (registro == null) {
					registro = new Paciente();
					registro.setCodigo_empresa(aux2.getCodigo_empresa());
					registro.setCodigo_sucursal(aux2.getCodigo_sucursal());
					registro.setNro_identificacion(aux2.getNro_identificacion());
					registro = pacienteService.consultar(registro);
				}

				tbxCodigo_administradora.seleccionarRegistro(administradora,
						administradora.getCodigo(), administradora.getNombre());
				tbxNomAdministradora
						.setValue((administradora != null ? administradora
								.getNombre() : ""));
				cambiarAdministradora(registro, administradora, true);
				tbxCodigo_administradora.setButtonVisible(true);
				// tbxCodigo_administradora.setReadonly(false);
			}

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(aux2.getCodigo_empresa());
			contratos.setCodigo_sucursal(aux2.getCodigo_sucursal());
			contratos.setCodigo_administradora(aux2.getCodigo_administradora());
			contratos.setId_plan(aux2.getId_plan());
			contratos = contratosService.consultar(contratos);

			if (contratos != null) {
				lbxServicios.setIncluirPaquetes(contratos.getIncluir_paquetes()
						.equals("S"));
			}

			// Cargamos informacion del medico
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(aux2.getCodigo_empresa());
			prestadores.setCodigo_sucursal(aux2.getCodigo_sucursal());
			prestadores.setNro_identificacion(aux2.getCodigo_medico());
			prestadores = prestadoresService.consultar(prestadores);

			if (prestadores != null) {
				Map<String, Object> map_prestador = new HashMap<String, Object>();
				map_prestador.put("nro_identificacion",
						prestadores.getNro_identificacion());
				map_prestador.put("nombres", prestadores.getNombres());
				map_prestador.put("apellidos", prestadores.getApellidos());
				tbxPrestador.seleccionarRegistro(
						map_prestador,
						prestadores.getNro_identificacion(),
						prestadores.getNombres() + " "
								+ prestadores.getApellidos());
				tbxPrestador.setAttribute(PRESTADOR, prestadores);
			}

			tbxNro_autorizacion.setValue(aux2.getNro_autorizacion());

			// consultamos centro de salud
			Centro_atencion centro_atencion = new Centro_atencion();
			centro_atencion.setCodigo_empresa(aux2.getCodigo_empresa());
			centro_atencion.setCodigo_sucursal(aux2.getCodigo_sucursal());
			centro_atencion.setCodigo_centro(aux2.getCodigo_centro());
			centro_atencion = centro_atencionService.consultar(centro_atencion);

			if (centro_atencion != null) {
				tbxCentro_salud.seleccionarRegistro(centro_atencion,
						centro_atencion.getCodigo_centro(),
						centro_atencion.getNombre_centro());
			}

			dtbxFecha_ingreso.setValue(aux2.getFecha_ingreso());
			cargarFechaEgreso(aux2.getFecha_ingreso());
			if (consultar_facturas) {
				consultarServiciosFacturas(true);
			}

			// dtbxFecha_ingreso.setButtonVisible(false);
			// dtbxFecha_ingreso.setReadonly(true);
			habilitarAccionCobroCopago(aux2);
		}
	}

	public void selectedAdmision(Listitem listitem,
			boolean seleccionar_via_ingreso) throws Exception {

		Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
				.getValue());
		if (admision == null) {
			if (lbxVia_ingreso.getItemCount() > 0) {
				lbxVia_ingreso.setSelectedIndex(0);
				tbxPrestador.limpiarSeleccion(false);
				setTitulo("");
			}
		} else {
			cargarAdmisiones(admision, admision.getPaciente(), true);
			if (seleccionar_via_ingreso) {
				Utilidades.setValueFrom(lbxVia_ingreso,
						admision.getVia_ingreso());
				lbxServicios.setPaciente(admision.getPaciente());
				pasarDatosSerivicio(admision.getPaciente());
			}
		}
	}

	public void nuevoRegistro() throws Exception {
		Messagebox
				.show("Perdera esta informacion que esta digitando, seguro que va a crear un nuevo registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									accionForm(true, "registrar");
								}
							}
						});
	}

	// Metodo para consultar los servicios que se han cargado a la admision //
	@SuppressWarnings("unchecked")
	public void consultarServiciosFacturas(boolean sw) {
		if (lbxNro_ingreso.getSelectedItem() != null) {
			Admision aux2 = lbxNro_ingreso.getSelectedItem().getValue();
			if (aux2 == null) {
				MensajesUtil.mensajeAlerta("Error al cargar servicios",
						"Debe seleccionar una admision valida");
				return;
			}
			Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
			Timestamp fecha = new Timestamp(dtbxFecha_egreso.getValue()
					.getTime());

			List<Detalle_factura> lista_datos = lbxServicios.getServicios();

			Map<String, Object> serviciosCargados = facturacionService
					.consultarAdmisionFactura(aux2, fecha, lista_datos,
							lbxServicios.getVia_ingreso_contratadas(),
							tbxAccion.getValue());

			lista_datos = (List<Detalle_factura>) serviciosCargados
					.get("lista_detalle");
			if (lista_datos.isEmpty()) {
				cargarFechaEgreso(dtbxFecha_ingreso.getValue());
			}
			if (sw) {
				Via_ingreso_contratadas via_ingreso_contratadas = ServiciosDisponiblesUtils
						.getVia_ingreso_contratadas(aux2);
				crearFilas(via_ingreso_contratadas, lista_datos);
				Facturacion facturacion_totales = (Facturacion) serviciosCargados
						.get("facturacion_totales");
				log.info("facturacion_totales ==> " + facturacion_totales);
				lbxServicios.cargarTotalFactura(facturacion_totales);
				lbxServicios
						.setConfiguracion_admision_ingreso(configuracion_admision_ingreso);
			}
			if (paciente != null && lbxServicios.isNocopago()) {
				habilitarAccionCobroCopago(aux2);
			}
		}
	}

	protected void habilitarAccionCobroCopago(Admision registro) {
		boolean paga_copago = ServiciosDisponiblesUtils.pagaCopago(registro);
		lbxServicios.setNoCopago(!paga_copago);
		lbxServicios.calcularCopago(lbxServicios.isNocopago());
	}

	// Este metodo genera las filas nuevamente //
	public void crearFilas(Via_ingreso_contratadas via_ingreso_contratadas,
			List<Detalle_factura> lista_datos) {
		lbxServicios.limpiar(false);
		lbxServicios.habilitarAcciones(true);
		lbxServicios.setVia_ingreso_contratadas(via_ingreso_contratadas);
		Admision admision_seleccionada = (Admision) lbxNro_ingreso
				.getSelectedItem().getValue();
		lbxServicios.setAdmision(admision_seleccionada);
		for (int j = 0; j < lista_datos.size(); j++) {
			Detalle_factura detalle_factura = lista_datos.get(j);
			// log.info("detalle_factura ===> " + detalle_factura);
			if (admision_seleccionada != null) {
				if (admision_seleccionada.getAdmision_parto().equals("S")
						&& detalle_factura.getValor_total() == 0.0) {
					if (lbxServicios.isIncluirPaquetes()) {
						lbxServicios.crearServicio(detalle_factura,
								ETIPO_SERVICIO.getTipo(detalle_factura));
					}
				} else {
					lbxServicios.crearServicio(detalle_factura,
							ETIPO_SERVICIO.getTipo(detalle_factura));
				}
			}
		}
	}

	// Metodo para crear la grilla cuando se consulte los servicios cargados //
	public void crearFilaDetalle_factura(final Detalle_factura detalle_factura,
			int j, Manuales_tarifarios manuales_tarifarios) {
		final Listitem fila = new Listitem();
		fila.setId("fila_" + j);
		fila.setValue(j + "");

		String codigo_articulo = detalle_factura.getCodigo_articulo();
		String nombre_articulo = detalle_factura.getDetalle();
		double unidades = detalle_factura.getCantidad();
		double valor_unitario = detalle_factura.getValor_unitario();
		double valor_total = detalle_factura.getValor_total();
		String tipo = detalle_factura.getTipo();

		// Columna facturable
		Listcell cell = new Listcell();

		// chbFacturable.setDisabled((tipo.equals("MEDICAMENTO")
		// || tipo.equals("MATERIALES/INSUMOS") ? false : true));
		fila.appendChild(cell);

		String codigo_articulo_aux = codigo_articulo;

		if (manuales_tarifarios != null) {
			Procedimientos procedimiento = new Procedimientos();
			procedimiento.setId_procedimiento(new Long(codigo_articulo));
			procedimiento = procedimientosService.consultar(procedimiento);
			if (procedimiento != null) {
				codigo_articulo_aux = procedimiento.getCodigo_cups();
			}
		}

		// Columna codigo
		cell = new Listcell();
		Label label = new Label(codigo_articulo_aux);
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Listcell();
		label = new Label(nombre_articulo);
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna cantidad //
		cell = new Listcell();
		label = new Label(Utilidades.formatDecimal(unidades, "#0"));
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna valor unitario //
		cell = new Listcell();
		label = new Label(Utilidades.formatDecimal(valor_unitario, "#,##0"));
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna valor total //
		cell = new Listcell();
		label = new Label(Utilidades.formatDecimal(valor_total, "#,##0"));
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		// Columna tipo //
		cell = new Listcell();
		label = new Label(tipo);
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);

		final Listcell listcell = new Listcell();
		fila.appendChild(listcell);

		final Toolbarbutton toolbarbutton_seleccion = new Toolbarbutton();

		if (tipo.equals("MEDICAMENTO") || tipo.equals("MATERIALES/INSUMOS")) {
			if (detalle_factura.getFacturable()) {
				toolbarbutton_seleccion.setImage("/images/si_seleccionado.png");
				toolbarbutton_seleccion
						.setTooltiptext("Haga click para marcar este detalle como NO facturable");
			} else {
				toolbarbutton_seleccion.setImage("/images/no_seleccionado.png");
				toolbarbutton_seleccion
						.setTooltiptext("Haga click para marcar este detalle como facturable");
			}
			listcell.setStyle("font-size:9px;");
			toolbarbutton_seleccion.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							if (!detalle_factura.getFacturable()) {
								toolbarbutton_seleccion
										.setImage("/images/si_seleccionado.png");
								detalle_factura.setFacturable(true);
								toolbarbutton_seleccion
										.setTooltiptext("Haga click para marcar este detalle como NO facturable");
							} else {
								toolbarbutton_seleccion
										.setImage("/images/no_seleccionado.png");
								detalle_factura.setFacturable(false);
								toolbarbutton_seleccion
										.setTooltiptext("Haga click para marcar este detalle como facturable");
							}
							actualizarPagina();
						}
					});
		} else {
			listcell.setValue(true);
			listcell.setStyle("font-size:9px;cursor:pointer;background-color:yellow;border-style:solid;border-width:1px;");
			listcell.setTooltiptext("Este detalle de factura obligatoriamente es facturable");
			toolbarbutton_seleccion.setImage("/images/si_seleccionado.png");
		}
		listcell.appendChild(toolbarbutton_seleccion);

		lbxServicios.appendChild(fila);
		lbxServicios.setMold("paging");
		lbxServicios.setPageSize(20);
	}

	// Este metodo es para actualizar el bean detalle factura cuando se marque o
	// desmarque la casilla facturable //
	public void actualizarPagina() throws Exception {
		calcularTotal();
	}

	// Metodo para calcular el copago cuando se marque la casilla no copago //
	// Metodo para recalcular total de la factura cuando se marque o desnarque
	// la casilla faturable de la grilla //
	public void calcularTotal() {

	}

	public void cargarModulo(int accion) {

		// lbxModulo.setSelectedIndex(accion);
	}

	// Este metodo es para adicionar o editar un servcio realizado a la
	// admision
	// este modulo llama a los Rips de CONSULTAS, PROCEDIMIENTO, PROCEDIMIENTOS
	// QUIRURGICOS, MEDICAMENTOS,MATERIALES E INSUMOS Y SERVICIOS //
	public void cargarServiciosAdmsion(String accion) {

	}

	// Este metodo es para cargar egreso de urgencias,hospitalizado o recien
	// nacido //
	public Window cargarSalida_paciente(String page, String width,
			IOnGuardar onGuardar, Boolean confirmacion, String mensaje) {
		try {
			Admision admision = lbxNro_ingreso.getSelectedItem().getValue();
			if (admision == null) {
				throw new Exception(
						"Debe seleccionar un paciente con admsion valida");
			}
			Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", empresa.getCodigo_empresa());
			parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parametros.put("nro_ingreso", admision.getNro_ingreso());
			parametros.put("nro_identificacion",
					paciente != null ? paciente.getNro_identificacion()
							: "-1111");
			parametros.put("ocultarConsulta", true);
			if (onGuardar != null) {
				parametros.put("IOnGuardar", onGuardar);
			}
			if (confirmacion != null) {
				parametros.put("confirmacion", confirmacion);
			}
			if (mensaje != null) {
				parametros.put("mensaje", mensaje);
			}

			Component componente = Executions.createComponents(page, this,
					parametros);
			final Window ventana = (Window) componente;
			ventana.setWidth(width);
			// ventana.setHeight("580px");
			ventana.setClosable(true);
			// ventana.setMaximizable(true);
			ventana.setTitle("MODULO FACTURACION");
			ventana.setMode("modal");
			return ventana;
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
			return null;
		}
	}

	// Este metodo es para cargar egreso de urgencias,hospitalizado o recien
	// nacido //
	public void actualizarEgresoPaciente() {
		try {
			Admision admision = lbxNro_ingreso.getSelectedItem().getValue();
			if (admision == null) {
				throw new Exception(
						"Debe seleccionar un paciente con admsion valida");
			}

			if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("ocultarConsulta", true);
				Urgencias urgencias = new Urgencias();
				urgencias.setCodigo_empresa(codigo_empresa);
				urgencias.setCodigo_sucursal(codigo_sucursal);
				urgencias.setNro_identificacion(admision
						.getNro_identificacion());
				urgencias.setNro_ingreso(admision.getNro_ingreso());
				urgencias = urgenciasService.consultar(urgencias);

				IOnGuardar iOnGuardar = new IOnGuardar() {

					@Override
					public boolean validar(Boolean confirmacion, String mensaje) {
						return true;
					}

					@Override
					public void guardar() {
						consultarServiciosFacturas(true);
					}
				};

				parametros.put("IOnGuardar", iOnGuardar);

				if (urgencias == null) {
					Paciente paciente = tbxNro_identificacion
							.getRegistroSeleccionado();
					parametros.put("codigo_empresa",
							empresa.getCodigo_empresa());
					parametros.put("codigo_sucursal",
							sucursal.getCodigo_sucursal());
					parametros.put("nro_ingreso", admision.getNro_ingreso());
					parametros.put("nro_identificacion",
							paciente != null ? paciente.getNro_identificacion()
									: "-1111");
					parametros.put("ocultarConsulta", true);
				}

				UrgenciasAction ventana = (UrgenciasAction) Executions
						.createComponents("/pages/urgencias.zul", this,
								parametros);
				ventana.setWidth("700px");
				ventana.setBorder("normal");
				ventana.setHeight("600px");
				ventana.setClosable(true);
				ventana.ocultarBotoConsultar(true);
				// ventana.setMaximizable(true);
				ventana.setTitle("MODULO FACTURACION - EGRESO DE URGENCIAS");

				if (urgencias != null) {
					ventana.mostrarDatos(urgencias);
				}
				ventana.setMode("modal");
			} else if (admision.getVia_ingreso().equals(
					IVias_ingreso.HOSPITALIZACIONES)) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("ocultarConsulta", true);
				Hospitalizacion hospitalizacion = new Hospitalizacion();
				hospitalizacion.setCodigo_empresa(codigo_empresa);
				hospitalizacion.setCodigo_sucursal(codigo_sucursal);
				hospitalizacion.setNro_identificacion(admision
						.getNro_identificacion());
				hospitalizacion.setNro_ingreso(admision.getNro_ingreso());
				hospitalizacion = hospitalizacionService
						.consultar(hospitalizacion);

				IOnGuardar iOnGuardar = new IOnGuardar() {

					@Override
					public boolean validar(Boolean confirmacion, String mensaje) {
						return true;
					}

					@Override
					public void guardar() {
						consultarServiciosFacturas(true);
					}
				};

				parametros.put("IOnGuardar", iOnGuardar);

				if (hospitalizacion == null) {
					Paciente paciente = tbxNro_identificacion
							.getRegistroSeleccionado();
					parametros.put("codigo_empresa",
							empresa.getCodigo_empresa());
					parametros.put("codigo_sucursal",
							sucursal.getCodigo_sucursal());
					parametros.put("nro_ingreso", admision.getNro_ingreso());
					parametros.put("nro_identificacion",
							paciente != null ? paciente.getNro_identificacion()
									: "-1111");
					parametros.put("ocultarConsulta", true);
				}

				HospitalizacionAction ventana = (HospitalizacionAction) Executions
						.createComponents("/pages/hospitalizacion.zul", this,
								parametros);
				ventana.setWidth("800px");
				ventana.setBorder("normal");
				ventana.setHeight("550px");
				ventana.setClosable(true);
				// ventana.setMaximizable(true);
				ventana.setTitle("MODULO FACTURACION - EGRESO DE HOSPITALIZACION");
				ventana.ocultarBotoConsultar(true);
				if (hospitalizacion != null) {
					ventana.mostrarDatos(hospitalizacion);
				}
				ventana.setMode("modal");
			} else {
				MensajesUtil
						.mensajeInformacion(
								"No aplica generacion de egreso",
								"A esta via de ingreso no se le puede generar un egreso de urgencias, hospitalizacion o recien nacido");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// Metodo para validar campos del formulario //
	@SuppressWarnings("unchecked")
	public void validarForm(final IOnGuardar onGuardar) throws Exception {

		tbxNro_identificacion
				.setStyle("text-transform:uppercase;background-color:white");
		lbxNro_ingreso.setStyle("background-color:white");
		tbxDescripcion
				.setStyle("text-transform:uppercase;background-color:white");
		ibxPlazo.setStyle("background-color:white");

		dtbxFecha_ingreso.setStyle("background-color:white");
		lbxVia_ingreso.setStyle("background-color:white");
		tbxPrestador.setStyle("background-color:white");
		tbxCodigo_administradora.setStyle("background-color:white");
		tbxCentro_salud.setStyle("background-color:white");

		boolean valida = true;
		boolean confirmacion = false;
		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxNro_identificacion.getText().trim().equals("")) {
			tbxNro_identificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida && lbxVia_ingreso.getSelectedIndex() == 0) {
			lbxVia_ingreso
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida && tbxPrestador.getRegistroSeleccionado() == null) {
			tbxPrestador
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida
				&& tbxCodigo_administradora.getRegistroSeleccionado() == null) {
			tbxCodigo_administradora
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida && tbxCentro_salud.getRegistroSeleccionado() == null) {
			tbxCentro_salud
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida
				&& dtbxFecha_ingreso.getValue().compareTo(
						dtbxFecha_egreso.getValue()) > 0) {
			dtbxFecha_ingreso.setStyle("background-color:#F6BBBE");
			mensaje = "La fecha de ingreso no puede ser mayor a la fecha de egreso";
			valida = false;
		}

		if (valida && parametros_empresa.getHabilitar_bodega_centro()) {
			if (configuracion_admision_ingreso != null
					&& configuracion_admision_ingreso
							.getSolicitar_informacion()
							.equals(Configuracion_admision_ingreso.TIPO_MEDICAMENTOS)) {
				if (lbxBodegas_centros.getSelectedItem() != null) {
					Object bodega_selecccionada = lbxBodegas_centros
							.getSelectedItem().getValue();
					if (!(bodega_selecccionada instanceof Bodega)) {
						valida = false;
						mensaje = "Para realizar la facturacion debe seleccionar la bodega";
						MensajesUtil.notificarAlerta("Seleccionar bodega",
								lbxBodegas_centros);
					}
				} else {
					valida = false;
					mensaje = "Para realizar la facturacion debe seleccionar la bodega y no se encontraron relacionadas con el centro "
							+ centro_atencion_session.getNombre_centro();
					MensajesUtil.notificarAlerta("Seleccionar bodega",
							lbxBodegas_centros);
				}

			}
		}

		if (valida
				&& parametros_empresa.getHabilitar_restriccion_autorizacion()
				&& !tbxNro_autorizacion.getValue().trim().isEmpty()) {
			Admision admision = getBeanAdmision();
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("nro_autorizacion", tbxNro_autorizacion.getValue()
					.trim());
			parametros.put("codigo_administradora",
					tbxCodigo_administradora.getValue());

			Map<String, Object> ultima_autorizacion = admisionService
					.getUltimaAutorizacion(parametros);

			if (ultima_autorizacion != null) {
				String nro_ingreso = (String) ultima_autorizacion
						.get("nro_ingreso");
				String nro_identificacion = (String) ultima_autorizacion
						.get("nro_identificacion");
				if (!(nro_ingreso.equals(admision.getNro_ingreso()) && nro_identificacion
						.equals(admision.getNro_identificacion()))) {
					valida = false;
					mensaje = "No se pueden guardar estos servicios con esta autorizacion porque esta siendo usada en otra admision. Nro-ingreso: "
							+ nro_ingreso
							+ " Identificacion: "
							+ nro_identificacion;
				}
			}

		}

		if (valida) {
			Map<String, Object> map = lbxServicios.validarForm();
			valida = (Boolean) map.get("valida");
			mensaje = (String) map.get("mensaje");
			confirmacion = (Boolean) map.get("confirmacion");

			if (valida && confirmacion) {
				StringBuilder builder = new StringBuilder(mensaje);
				List<String> list_servicios = (List<String>) map
						.get("list_servicios");
				for (String servicios : list_servicios) {
					builder.append("\n * " + servicios);
				}
				mensaje = builder.toString()
						+ "\n ¿Estas seguro que deseas continuar?";
				valida = false;
			} else {
				confirmacion = false;
			}
		}

		if (!valida) {
			if (confirmacion) {
				final Boolean confirmacion_aux = confirmacion;
				final String mensaje_aux = mensaje;
				Messagebox.show("" + mensaje, usuarios.getNombres()
						+ " recuerde que...", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									if (onGuardar.validar(confirmacion_aux,
											mensaje_aux)) {
										onGuardar.guardar();
									}

								}
							}
						});
			} else {
				if (mensaje != null && !mensaje.isEmpty()) {
					Messagebox.show(mensaje, usuarios.getNombres()
							+ " recuerde que...", Messagebox.OK,
							Messagebox.EXCLAMATION);
				}
			}
		} else {
			if (onGuardar.validar(confirmacion, mensaje)) {
				onGuardar.guardar();
			}
		}
	}

	// metodo para validar si existe rips de urgencias //
	public boolean existeUrgencia() throws Exception {
		if (lbxNro_ingreso.getSelectedItem() != null) {
			Admision admision = lbxNro_ingreso.getSelectedItem().getValue();
			Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
			Urgencias urgencias = new Urgencias();
			urgencias.setCodigo_empresa(empresa.getCodigo_empresa());
			urgencias.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			urgencias.setNro_identificacion(paciente != null ? paciente
					.getNro_identificacion() : "");
			urgencias.setNro_ingreso(admision != null ? admision
					.getNro_ingreso() : "");
			urgencias = urgenciasService.consultar(urgencias);
			return (urgencias != null);
		} else {
			return false;
		}
	}

	// metodo para validar si existe rips de hospitalizado //
	public boolean existeHospitalizacion() throws Exception {
		if (lbxNro_ingreso.getSelectedItem() != null) {
			Admision admision = lbxNro_ingreso.getSelectedItem().getValue();
			if (admision == null) {
				throw new Exception(
						"Debe seleccionar un paciente con admision valida");
			}
			Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
			Hospitalizacion hospitalizacion = new Hospitalizacion();
			hospitalizacion.setCodigo_empresa(empresa.getCodigo_empresa());
			hospitalizacion.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			hospitalizacion.setNro_identificacion(paciente != null ? paciente
					.getNro_identificacion() : "");
			hospitalizacion.setNro_ingreso(admision.getNro_ingreso());
			hospitalizacion = hospitalizacionService.consultar(hospitalizacion);
			return (hospitalizacion != null);
		} else {
			return false;
		}
	}

	// metodo para validar si existe rips de recien nacido //
	public boolean existeRecien_nacido() throws Exception {
		if (lbxNro_ingreso.getSelectedItem() != null) {
			Admision admision = lbxNro_ingreso.getSelectedItem().getValue();
			if (admision == null) {
				throw new Exception(
						"Debe seleccionar un paciente con admsion valida");
			}
			Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
			Recien_nacido recien_nacido = new Recien_nacido();
			recien_nacido.setCodigo_empresa(empresa.getCodigo_empresa());
			recien_nacido.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			recien_nacido.setNro_identificacion(paciente != null ? paciente
					.getNro_identificacion() : "");
			recien_nacido.setNro_ingreso(admision.getNro_ingreso());
			recien_nacido = recien_nacidoService.consultar(recien_nacido);
			return (recien_nacido != null);
		} else {
			return false;
		}
	}

	// Metodo para verificar si ya existe un egreso ya se de
	// urgencias,hospitalizado o recien nacido //
	public boolean verificarEgreso_paciente(final IOnGuardar onGuardar,
			final Boolean confirmacion, final String mensaje) {
		try {
			if (isUrgencia().equals("S") && !existeUrgencia()) {
				Messagebox
						.show("Para realizar esta accion debe guardar el egreso de urgencia \n Desea guardarlo ahora?",
								"Generar Egreso",
								Messagebox.YES + Messagebox.NO,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener<Event>() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											// do the thing
											cargarSalida_paciente(
													"urgencias.zul", "700px",
													onGuardar, confirmacion,
													mensaje);
										}
									}
								});

				Via_ingreso_contratadas via_ingreso_contratadas = lbxServicios
						.getVia_ingreso_contratadas();

				if (via_ingreso_contratadas != null) {
					List<Detalle_factura> lista_datos = lbxServicios
							.getServicios();
					for (int j = 0; j < lista_datos.size(); j++) {
						Detalle_factura detalle_factura = lista_datos.get(j);
						if (detalle_factura.getTipo().equalsIgnoreCase(
								"CONSULTA")
								|| detalle_factura.getTipo().equalsIgnoreCase(
										"PROCEDIMIENTO")
								|| detalle_factura.getTipo().equalsIgnoreCase(
										"ESTANCIA")
								|| detalle_factura.getTipo().equalsIgnoreCase(
										"PROCEDIMIENTO_MULT")) {

							String codigo_articulo = detalle_factura
									.getCodigo_articulo();

							String codigo_articulo_aux = codigo_articulo;

							Procedimientos procedimiento = new Procedimientos();
							procedimiento.setId_procedimiento(new Long(
									codigo_articulo));
							procedimiento = procedimientosService
									.consultar(procedimiento);

							if (procedimiento != null) {
								codigo_articulo_aux = procedimiento
										.getCodigo_cups();
							}

							if (codigo_articulo_aux
									.equals(IConstantes.TRASLADO_SIMPLE)
									|| codigo_articulo_aux
											.equals(IConstantes.TRASLADO_MECANIZADO_SIMPLE)
									|| codigo_articulo_aux
											.equals(IConstantes.TRASLADO_REDONDO_BASICO)
									|| codigo_articulo_aux
											.equals(IConstantes.TRASLADO_REDONDO_MECALIZADO)) {
								Messagebox
										.show("El paciente tiene un traslado  \n Desea realizarlo ahora? ",
												"Generar Egreso",
												Messagebox.YES + Messagebox.NO,
												Messagebox.QUESTION,
												new org.zkoss.zk.ui.event.EventListener<Event>() {
													public void onEvent(
															Event event)
															throws Exception {
														if ("onYes".equals(event
																.getName())) {
															// do the thingd
															cargarSalida_paciente(
																	"furtran.zul",
																	"950px",
																	onGuardar,
																	confirmacion,
																	mensaje);
														}
													}
												});
							}
						}

					}
				}
				return false;
			} else if (isHospitalizacion().equals("S")
					&& !existeHospitalizacion()) {

				Messagebox
						.show("Para realizar esta accion? debe guardar el egreso de Hospitalizaci? \n Desea guardarlo ahora? ",
								"Generar Egreso",
								Messagebox.YES + Messagebox.NO,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener<Event>() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											// do the thing
											cargarSalida_paciente(
													"hospitalizacion.zul",
													"780px", onGuardar,
													confirmacion, mensaje);
										}
									}
								});
				// }
				return false;

			} else if (isRecienNacido().equals("S") && !existeRecien_nacido()) {
				Messagebox
						.show("A este paciente no se le ha dado egreso por recien nacido \n Desea hacer el egreso ahora? ",
								"Generar Egreso",
								Messagebox.YES + Messagebox.NO,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener<Event>() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											// do the thing
											cargarSalida_paciente(
													"recien_nacido.zul",
													"780px", onGuardar,
													confirmacion, mensaje);
										}
									}
								});
				return false;
			}

			return true;
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

		return false;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		// log.info("Ejecutando metodo @guardarDatos()");
		try {
			FormularioUtil.setUpperCase(groupboxEditar);

			IOnGuardar iOnGuardar = new IOnGuardar() {
				@SuppressWarnings("unchecked")
				@Override
				public void guardar() {
					try {
						Map<String, Object> datos_prefactura = getDatosPreFactura();

						consultarServiciosFacturas(false);

						final Map<String, Object> datos_facturacion = new HashMap<String, Object>();

						Facturacion facturacion = getFacturacion();
						log.info("facturacion ===> " + facturacion);
						datos_facturacion.put("facturacion", facturacion);
						datos_facturacion.put("lista_detalle",
								lbxServicios.getServicios());
						datos_facturacion.put("accion", tbxAccion.getText());

						datos_prefactura.put("editar_ordenamiento_prestador",
								parametros_empresa
										.getEditar_ordenamiento_prestador());

						Map<String, Object> map_resultado = facturacionService
								.guardarDatosCompletos(datos_prefactura,
										datos_facturacion);

						// Después que el usuario presiona ok el sistema
						// continua con el proceso
						facturacion = (Facturacion) map_resultado
								.get("facturacion");

						Messagebox.show(
								"Los datos se guardaron satisfactoriamente... Nro de atencion :"
										+ facturacion.getNro_atencion(),
								"Informacion ..", Messagebox.OK,
								Messagebox.INFORMATION);

						lbxServicios.habilitarServicios(false);

						if (facturacion.getAdmision() == null) {
							Map<String, Object> map_admision = (Map<String, Object>) datos_prefactura
									.get(ITiposServicio.PARAM_ADMISION);
							Admision admision = (Admision) map_admision
									.get("admision");
							facturacion.setAdmision(admision);
						}
						mostrarDatos(facturacion);
					} catch (Exception e) {
						MensajesUtil.mensajeError(e, "",
								Anular_facturasAction.this);
					}
				}

				@Override
				public boolean validar(Boolean confirmacion, String mensaje) {
					procesarAdmisionNueva();
					return verificarEgreso_paciente(this, confirmacion, mensaje);
				}
			};

			validarForm(iOnGuardar);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@SuppressWarnings("unchecked")
	public void procesarAdmisionNueva() {
		Map<String, Object> datos_prefactura = getDatosPreFactura();
		Map<String, Object> datos_admision = (Map<String, Object>) datos_prefactura
				.get(ITiposServicio.PARAM_ADMISION);
		boolean nueva_admision = (Boolean) datos_admision.get("nueva");
		if (nueva_admision) {
			Admision admision = admisionService.guardar(datos_admision);
			datos_admision.put("nueva", false);
			datos_admision.put("admision", admision);

			List<Admision> listaAdmisiones = new ArrayList<Admision>();
			listaAdmisiones.add(admision);
			listarIngresos(lbxNro_ingreso, listaAdmisiones, false);
		}
	}

	private Map<String, Object> getDatosPreFactura() {
		Map<String, Object> datos = new HashMap<String, Object>();
		datos.put(ITiposServicio.PARAM_ADMISION, getAdmisionRegistro());
		datos.put("lista_detalle", lbxServicios.getServiciosCompletos());
		datos.put("accion", tbxAccion.getText());
		datos.put("configuracion_admision_ingreso",
				configuracion_admision_ingreso);
		datos.put("administradora",
				tbxCodigo_administradora.getRegistroSeleccionado());
		datos.put("user", getUsuarios());
		Object bodega_selecccionada = lbxBodegas_centros.getSelectedItem() != null ? lbxBodegas_centros
				.getSelectedItem().getValue() : new Object();
		if (bodega_selecccionada instanceof Bodega) {
			datos.put("bodega_seleccionada", bodega_selecccionada);
		}
		return datos;
	}

	public void guardar() {
		try {
			if (facturacion_current != null) {
				guardarDatos();
			} else {
				guardarServicios();
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	public void guardarServicios() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			validarForm(new IOnGuardar() {
				@Override
				public void guardar() {

				}

				@Override
				public boolean validar(Boolean confirmacion, String mensaje) {
					return true;
				}
			});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private Map<String, Object> getAdmisionRegistro() {
		Admision admision = getBeanAdmision();
		if (configuracion_admision_ingreso != null
				&& configuracion_admision_ingreso.getLaboratorio_pyp()) {
			admision.setVia_ingreso(configuracion_admision_ingreso
					.getVia_ingreso());
			admision.setPrograma_lab_pyp(lbxPrograma_pyp.getSelectedItem()
					.getValue().toString());
		} else {
			admision.setPrograma_lab_pyp("");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("admision", admision);
		map.put("accion", tbxAccion.getText());
		map.put("codigo_atencion", "");
		map.put("codigo_pabellon", "");
		map.put("codigo_habitacion", "");
		map.put(IConstantes.LLAVE_FURIPS, null);
		map.put("empresa", empresa);
		map.put("dtt_servicios", null);
		map.put("dtt_medicamentos", null);
		map.put("nueva", admision.getNro_ingreso() == null
				|| admision.getNro_ingreso().isEmpty());
		map.put("administradora",
				tbxCodigo_administradora.getRegistroSeleccionado());
		return map;
	}

	private Admision getBeanAdmision() {
		String codigo_cie = "Z000";

		Admision admision = null;
		Listitem listitem = lbxNro_ingreso.getSelectedItem();
		if (listitem != null) {
			admision = listitem.getValue();
		}

		if (admision == null) {
			admision = new Admision();
			admision.setCodigo_empresa(empresa.getCodigo_empresa());
			admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
			admision.setNro_identificacion(paciente != null ? paciente
					.getNro_identificacion() : "");
			admision.setEstado("1");
			admision.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			admision.setAtendida(false);
			admision.setCodigo_cita("");

			admision.setAplica_triage(false);
			admision.setAplica_tuberculosis(false);
			admision.setRealizo_triage(false);
			admision.setAplica_lepra(false);
			admision.setPaciente_remitido("N");
			admision.setTipo_consulta("");
			admision.setTipo_adicional_via_ingreso("");
			admision.setAdmision_parto("N");
			admision.setTipo_psicologia("");

			admision.setVia_ingreso(lbxVia_ingreso.getSelectedItem().getValue()
					.toString());
			admision.setTipo_atencion(getTipoAtencion());
			admision.setMarca_admision(getMarcaAdmision());
			admision.setMotivo_cancelacion("");
			admision.setProcedencia("");
			admision.setCodigo_especialidad("");
			admision.setCama("");
			admision.setIngreso_programa("N");
			admision.setPrimera_vez("N");
			admision.setCondicion_usuaria("");
			admision.setTipo_diagnostico("");
			admision.setDiagnostico_ingreso(codigo_cie);
			admision.setTipo_discapacidad("");
			admision.setGrado_discapacidad("");
			admision.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			admision.setCreacion_user(usuarios.getCodigo().toString());
			admision.setUltimo_user(usuarios.getCodigo().toString());
			admision.setUrgencias(isUrgencia());
			admision.setHospitalizacion(isHospitalizacion());
			admision.setRecien_nacido(isRecienNacido());
			admision.setFacturadas_manual(true);
		} else {
			codigo_cie = admision.getDiagnostico_ingreso();
		}
		admision.setCodigo_administradora(tbxCodigo_administradora.getValue());

		Contratos contratos = (Contratos) tbxCodigo_administradora
				.getAttribute(CONTRATO);
		String id_plan_seleccionado = null;

		if (lbxContratos.getSelectedItem().getValue() instanceof Manuales_tarifarios) {
			Manuales_tarifarios manuales_tarifarios = (Manuales_tarifarios) lbxContratos
					.getSelectedItem().getValue();
			id_plan_seleccionado = manuales_tarifarios.getId_contrato();
		} else if (lbxContratos.getSelectedItem().getValue() instanceof Contratos) {
			Contratos contratos_aux = (Contratos) lbxContratos
					.getSelectedItem().getValue();
			id_plan_seleccionado = contratos_aux.getId_plan();
		}

		if (contratos != null) {
			admision.setId_plan(contratos.getId_plan());
		}

		if (id_plan_seleccionado != null) {
			admision.setId_plan(id_plan_seleccionado);
		}

		admision.setCodigo_medico(tbxPrestador.getValue());
		admision.setFecha_ingreso(new Timestamp(dtbxFecha_ingreso.getValue()
				.getTime()));
		admision.setNro_autorizacion(tbxNro_autorizacion.getValue());
		admision.setCodigo_centro(tbxCentro_salud.getValue());
		admision.setCausa_externa(lbxCausa_externa.getSelectedItem().getValue()
				.toString());

		// verificamos si es para un particular
		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();
		admision.setParticular(administradora != null
				&& administradora
						.getTipo_aseguradora()
						.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES) ? "S"
				: "N");
		admision.setPaciente((Paciente) tbxNro_identificacion
				.getRegistroSeleccionado());
		if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)) {
			admision.setAdmision_parto(chkAdmision_parto.isChecked() ? "S"
					: "N");
		}
		return admision;
	}

	/* elemento -> marca_admision */
	private String getMarcaAdmision() {
		return "001";
	}

	private String getTipoAtencion() {
		if (getViaIngreso().equals(IVias_ingreso.URGENCIA)) {
			return IConstantes.TIPO_ATENCION_URGENCIAS_OBSERVACION;
		} else if (getViaIngreso().equals(IVias_ingreso.HOSPITALIZACIONES)) {
			return IConstantes.TIPO_ATENCION_HOSPITALIZACION;
		} else if (lbxServicios.getTipoContrato().equals(
				IConstantes.TIPO_CONTRATO_INDIVIDUAL_EVENTO)) {
			return IConstantes.TIPO_ATENCION_EVENTO;
		} else {
			return IConstantes.TIPO_ATENCION_AMBULATORIO;
		}
	}

	private String isRecienNacido() {
		return "N";
	}

	private String isHospitalizacion() {
		return getViaIngreso().equals(IVias_ingreso.HOSPITALIZACIONES) ? "S"
				: "N";
	}

	private String isUrgencia() {
		return getViaIngreso().equals(IVias_ingreso.URGENCIA) ? "S" : "N";
	}

	private String getViaIngreso() {
		if (lbxVia_ingreso.getSelectedItem() != null) {
			return lbxVia_ingreso.getSelectedItem().getValue();
		}
		return "";
	}

	private Facturacion getFacturacion() throws Exception {
		// Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
		// .getValue());

		GregorianCalendar vence = new GregorianCalendar();
		vence.setTimeInMillis(dtbxFecha.getValue().getTime());
		vence.set(Calendar.DATE, vence.get(Calendar.DATE) + ibxPlazo.getValue());

		Facturacion facturacion = new Facturacion();
		facturacion.setCodigo_empresa(empresa.getCodigo_empresa());
		facturacion.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		facturacion.setId_factura(facturacion_current.getId_factura());
		facturacion = facturacionService.consultar(facturacion);
		if (facturacion == null) {
			facturacion = new Facturacion();
			facturacion.setCodigo_empresa(empresa.getCodigo_empresa());
			facturacion.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			facturacion.setCodigo_comprobante(CODIGO_COMPROBANTE_FHC);
			facturacion.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
		}

		Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
		String id_plan_seleccionado = "";
		String administradora_seleccionado = "";

		if (lbxContratos.getSelectedItem().getValue() instanceof Manuales_tarifarios) {
			Manuales_tarifarios manuales_tarifarios = (Manuales_tarifarios) lbxContratos
					.getSelectedItem().getValue();
			id_plan_seleccionado = manuales_tarifarios.getId_contrato();
			administradora_seleccionado = manuales_tarifarios
					.getCodigo_administradora();
		} else if (lbxContratos.getSelectedItem().getValue() instanceof Contratos) {
			Contratos contratos_aux = (Contratos) lbxContratos
					.getSelectedItem().getValue();
			id_plan_seleccionado = contratos_aux.getId_plan();
			administradora_seleccionado = contratos_aux
					.getCodigo_administradora();
		}

		facturacion.setTipo_identificacion(paciente.getTipo_identificacion());
		facturacion.setCodigo_tercero(paciente.getNro_identificacion());
		facturacion.setDocumento_externo("");
		facturacion.setCodigo_empleado("");
		facturacion.setFecha(new Timestamp(dtbxFecha.getValue().getTime()));
		facturacion
				.setFecha_vencimiento(new Timestamp(vence.getTimeInMillis()));
		facturacion.setFecha_inicio(new Timestamp(dtbxFecha_ingreso.getValue()
				.getTime()));
		facturacion.setFecha_final(new Timestamp(dtbxFecha_egreso.getValue()
				.getTime()));
		facturacion.setCodigo_administradora(administradora_seleccionado);
		facturacion.setId_plan(id_plan_seleccionado);
		facturacion.setNro_contrato("");
		facturacion.setNro_poliza(tbxNro_poliza.getValue());
		facturacion.setTotal_cuotas(1);
		facturacion.setPlazo(ibxPlazo.getValue());
		facturacion.setForma_pago("02");
		facturacion.setDescripcion(tbxDescripcion.getValue());
		facturacion.setObservacion(tbxObservacion.getValue());
		facturacion.setValor_total(lbxServicios.getValorTotal());
		facturacion.setValor_cuota(lbxServicios.getValor_cuota());
		facturacion.setValor_copago(lbxServicios.getValor_copago());
		facturacion.setNocopago(lbxServicios.isNocopago() ? "S" : "N");
		facturacion.setDto_factura(lbxServicios.getDto_factura());
		facturacion.setDto_copago(0.0);
		facturacion.setCop_canc(lbxServicios.getCop_canc());
		facturacion.setAplica_descuento(lbxServicios.getAplicaDescuento());
		// facturacion.setTipo("IND");
		facturacion.setEstado("PEND");
		// facturacion.setFactura(factura);
		facturacion.setPost("N");
		facturacion.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		facturacion.setCreacion_user(usuarios.getCodigo().toString());
		facturacion.setUltimo_user(usuarios.getCodigo().toString());
		facturacion.setCodigo_credito("");
		facturacion.setRemision("");
		facturacion.setPrefijo("");
		facturacion.setAnio(new java.text.SimpleDateFormat("yyyy")
				.format(dtbxFecha.getValue()));
		facturacion.setMes(new java.text.SimpleDateFormat("MM")
				.format(dtbxFecha.getValue()));
		facturacion.setClasificacion("");
		facturacion.setVerificado("N");
		facturacion.setRetencion(0.0);
		facturacion.setFactura("");
		facturacion.setRadicado("N");
		facturacion.setAuditado("N");
		facturacion.setValor_pagar_factura(facturacion.getValor_total());

		return facturacion;
	}

	private void cargarInformacionFactura() {
		String tipo_facturacion = Facturacion_ripsAction
				.getTipo(facturacion_current);
		String descripcion_facturacion = Utilidades.getDescripcionElemento(
				tipo_facturacion, "tipo_contrato", elementoService);
		if (descripcion_facturacion != null) {
			setTitulo(" - MODO FACTURACION: "
					+ descripcion_facturacion.toUpperCase()
					+ " - "
					+ getDescripcionNroFactura(tipo_facturacion,
							facturacion_current));
		}

		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();
		if (!tipo_facturacion
				.equals(IConstantes.TIPO_CONTRATO_INDIVIDUAL_EVENTO)) {

		} else {

			if (rol_usuario.equalsIgnoreCase(IRoles.FACTURADOR)
					&& !parametros_empresa.getPermitir_facturador_evento()
					&& !administradora
							.getTipo_aseguradora()
							.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) {

			} else {

			}
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatosAdmision(Object obj) throws Exception {
		try {
			Admision admision = admisionService.consultar((Admision) obj);
			Paciente paciente = admision.getPaciente();
			tbxNro_identificacion.seleccionarRegistro(paciente,
					paciente.getDocumento(), paciente.getNombreCompleto());
			cargarDatosPacientes(admision, paciente, true);
			lbxVia_ingreso.setFocus(true);
			lbxVia_ingreso.focus();

			try {
				Utilidades.validacionFechasFuturas(false, dtbxFecha_ingreso,
						dtbxFecha);
			} catch (WrongValueException e) {
				Notificaciones
						.mostrarNotificacionAlerta(
								"Advertencia",
								"La fecha de la admision es mayor que la fecha actual ",
								3000);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		facturacion_current = (Facturacion) obj;
		facturacion_current = facturacionService.consultar(facturacion_current);
		try {
			cargarInformacionFactura();
			if (facturacion_current.getEstado().equals("ANUL")) {
				btAnular.setDisabled(true);
			} else {
				btAnular.setDisabled(false);
			}

			Admision admision = null;
			if (facturacion_current.getAdmision() == null) {
				admision = new Admision();
				admision.setCodigo_empresa(facturacion_current
						.getCodigo_empresa());
				admision.setCodigo_sucursal(facturacion_current
						.getCodigo_sucursal());
				admision.setNro_ingreso(facturacion_current.getNro_ingreso());
				admision.setNro_identificacion(facturacion_current
						.getCodigo_tercero());
				admision = admisionService.consultar(admision);
			} else {
				admision = facturacion_current.getAdmision();
			}

			if (admision == null) {
				throw new ValidacionRunTimeException(
						"Esta factura no tiene una admision creada, revisar el estado de la admision ya que pudo haber sido eliminada");
			}

			if (!admision.getPrograma_lab_pyp().isEmpty()) {
				rowProgramaPyp.setVisible(true);
				lbxPrograma_pyp.getItems().clear();
				Listitem listitem_aux_programa = new Listitem();
				listitem_aux_programa.setValue(admision.getPrograma_lab_pyp());
				Elemento elemento = new Elemento();
				elemento.setCodigo(admision.getPrograma_lab_pyp());
				elemento.setTipo("via_ingreso");
				elemento = elementoService.consultar(elemento);
				listitem_aux_programa.setLabel(elemento != null ? elemento
						.getDescripcion() : admision.getPrograma_lab_pyp());
				lbxPrograma_pyp.appendChild(listitem_aux_programa);
				listitem_aux_programa.setSelected(true);
			} else {
				rowProgramaPyp.setVisible(false);
			}

			cargarDatosPacientes(admision, admision.getPaciente(), false);

			tbxNro_identificacion.seleccionarRegistro(admision.getPaciente(),
					admision.getPaciente().getDocumento(), admision
							.getPaciente().getNombreCompleto());

			btnLimpiarPaciente.setVisible(false);

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(facturacion_current
					.getCodigo_empresa());
			administradora.setCodigo_sucursal(facturacion_current
					.getCodigo_sucursal());
			administradora.setCodigo(facturacion_current
					.getCodigo_administradora());
			administradora = administradoraService.consultar(administradora);

			Contratos contratos = new Contratos();
			contratos
					.setCodigo_empresa(facturacion_current.getCodigo_empresa());
			contratos.setCodigo_sucursal(facturacion_current
					.getCodigo_sucursal());
			contratos.setCodigo_administradora(facturacion_current
					.getCodigo_administradora());
			contratos.setId_plan(facturacion_current.getId_plan());
			contratos = contratosService.consultar(contratos);

			if (administradora != null) {
				tbxCodigo_administradora.seleccionarRegistro(administradora,
						administradora.getCodigo(), administradora.getNombre());
			}

			if (contratos != null) {
				lbxServicios.setIncluirPaquetes(contratos.getIncluir_paquetes()
						.equals("S"));
			}

			dtbxFecha.setValue(facturacion_current.getFecha());
			dtbxFecha_ingreso.setValue(facturacion_current.getFecha_inicio());
			dtbxFecha_egreso.setValue(facturacion_current.getFecha_final());

			tbxNro_poliza.setValue(facturacion_current.getNro_poliza());
			tbxObservacion.setValue(facturacion_current.getObservacion());

			ibxPlazo.setValue(facturacion_current.getPlazo());
			tbxDescripcion.setValue(facturacion_current.getDescripcion());

			lbxContratos.getChildren().clear();

			Listitem listitem_aux = new Listitem(
					contratos != null ? contratos.getNombre() + " - "
							+ contratos.getNro_contrato() : "No registrado",
					contratos);
			lbxContratos.appendChild(listitem_aux);
			listitem_aux.setSelected(true);

			// deshabilitamos datos
			habilitarDatos(false);

			lbxServicios.limpiar(false);

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(admision.getCodigo_empresa());
			prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
			prestadores.setNro_identificacion(admision.getCodigo_medico());
			prestadores = prestadoresService.consultar(prestadores);

			if (prestadores != null) {
				Map<String, Object> map_prestador = new HashMap<String, Object>();
				map_prestador.put("nro_identificacion",
						prestadores.getNro_identificacion());
				map_prestador.put("nombres", prestadores.getNombres());
				map_prestador.put("apellidos", prestadores.getApellidos());
				tbxPrestador.seleccionarRegistro(
						map_prestador,
						prestadores.getNro_identificacion(),
						prestadores.getNombres() + " "
								+ prestadores.getApellidos());
			}
			Utilidades.setValueFrom(lbxVia_ingreso, admision.getVia_ingreso());
			Listitem listitem = lbxVia_ingreso.getSelectedItem();
			if (listitem != null) {
				listitem.setVisible(true);
				lbxServicios.habilitarAcciones(true);
				Elemento via_ingreso = new Elemento(listitem.getValue()
						.toString(), "via_ingreso", listitem.getLabel());

				// Esto si el servicio no esta contratado
				if (listitem.getValue().toString().isEmpty()) {
					Elemento elemento = new Elemento();
					elemento.setCodigo(admision.getVia_ingreso());
					elemento.setTipo("via_ingreso");
					elemento = elementoService.consultar(elemento);
					if (elemento != null) {
						Listitem listitem_via_ingreso = new Listitem();
						listitem_via_ingreso.setValue(elemento.getCodigo());
						listitem_via_ingreso
								.setLabel(elemento.getDescripcion());
						lbxVia_ingreso.appendChild(listitem_via_ingreso);
						listitem_via_ingreso.setSelected(true);
						// listitem_via_ingreso.setVisible(true);
						// lbxVia_ingreso.invalidate();

						via_ingreso = new Elemento(listitem_via_ingreso
								.getValue().toString(), "via_ingreso",
								listitem_via_ingreso.getLabel());
					}
				}

				lbxServicios.cargarDatos(admision.getPaciente(),
						administradora, via_ingreso, prestadores,
						dtbxFecha.getValue(), dtbxFecha_ingreso.getValue(),
						dtbxFecha_egreso.getValue(), admision, contratos);
				tbxNro_autorizacion.setValue(admision.getNro_autorizacion());
			}

			lbxVia_ingreso.setDisabled(true);
			lbxNro_ingreso.setDisabled(true);
			lbxServicios.habilitarServicios(false);

			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());

			consultarServiciosFacturas(true);
			// actualizamos valores
			// lbxServicios.setValorTotal(facturacion.getValor_total());
			// lbxServicios.setValor_cuota(facturacion.getValor_cuota());
			lbxServicios.setValor_copago(facturacion_current.getValor_copago());
			lbxServicios.setNocopago(facturacion_current.getNocopago()
					.equalsIgnoreCase("S"));
			lbxServicios.setDto_factura(facturacion_current.getDto_factura());
			lbxServicios.setCop_canc(facturacion_current.getCop_canc());
			lbxServicios.setAplicaDescuento(facturacion_current
					.getAplica_descuento() != null ? facturacion_current
					.getAplica_descuento() : false);
			lbxServicios.calcularPorcentajeApartir(facturacion_current
					.getAplica_descuento() != null ? facturacion_current
					.getAplica_descuento() : false);
			// lbxServicios.calcularCopago(lbxServicios.isNocopago());
			// fin de actualizacion de valores

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	private String getDescripcionNroFactura(String tipo_facturacion,
			Facturacion facturacion_aux) {
		if (facturacion_aux != null) {
			if ((tipo_facturacion
					.equals(IConstantes.TIPO_CONTRATO_INDIVIDUAL_EVENTO) || tipo_facturacion
					.equals(IConstantes.TIPO_FACTURA_PORTABILIDAD))
					&& (facturacion_aux.getCodigo_documento_res() != null && !facturacion_aux
							.getCodigo_documento_res().isEmpty())) {
				return "NUMERO FACT: "
						+ facturacion_aux.getCodigo_documento_res();
			} else {
				return "NUMERO ATENCION: " + facturacion_aux.getNro_atencion();
			}
		} else {
			return "NUMERO ATENCION: ";
		}
	}

	private void habilitarDatos(boolean habilitar) {
		tbxNro_identificacion.setReadonly(!habilitar);
		tbxNro_identificacion.setButtonVisible(habilitar);
		// tbxCodigo_administradora.setReadonly(!habilitar);
		tbxCodigo_administradora.setButtonVisible(habilitar);
		// tbxCentro_salud.setReadonly(!habilitar);
		// tbxCentro_salud.setButtonVisible(habilitar);
	}

	public void imprimir(Long id_factura) throws Exception {
		if (id_factura != null) {
			Messagebox.show("No se ha guardado el registro", "Informacion ..",
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Facturacion_rips");
		paramRequest.put("id_factura", id_factura);
		paramRequest.put("codigo_comprobante", CODIGO_COMPROBANTE_FHC);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());
		paramRequest.put("agrupar", lbxServicios.isAgrupar() ? "" : null);
		paramRequest.put("mediana", lbxServicios.isMediana() ? "2" : "");
		paramRequest.put("incluir_paquete",
				lbxServicios.isIncluirPaquetes() ? "S" : "N");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void prefactura(Long id_factura) throws Exception {
		if (lbxNro_ingreso.getSelectedItem() == null) {
			Messagebox.show(
					"Debe guardar los servicios, para generar una prefactura",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
				.getValue());
		if (admision == null) {
			Messagebox.show("Debe seleccionar una admision", "Informacion ..",
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		consultarServiciosFacturas(false);
		Facturacion facturacion = getFacturacion();

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Prefactura");
		paramRequest.put("codigo_comprobante", CODIGO_COMPROBANTE_FHC);
		paramRequest.put("id_factura", id_factura);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());
		paramRequest.put("agrupar", lbxServicios.isAgrupar() ? "" : null);
		paramRequest.put("facturacion", facturacion);
		List<Detalle_factura> lista_datos = lbxServicios.getServicios();
		if (lbxServicios.isIncluirPaquetes()) {
			paramRequest.put("lista_detalle", lista_datos);
		} else {
			Admision admision_seleccionada = (Admision) lbxNro_ingreso
					.getSelectedItem().getValue();
			List<Detalle_factura> listado_detalle_aux = new ArrayList<Detalle_factura>();
			for (Detalle_factura detalle_factura : lista_datos) {
				if (admision_seleccionada != null) {
					if (admision_seleccionada.getAdmision_parto().equals("S")
							&& detalle_factura.getValor_total() == 0.0) {
						if (lbxServicios.isIncluirPaquetes()) {
							listado_detalle_aux.add(detalle_factura);
						}
					} else {
						listado_detalle_aux.add(detalle_factura);
					}
				}
			}
			paramRequest.put("lista_detalle", listado_detalle_aux);
		}

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void contabilizar(String codigo_documento, boolean sw)
			throws Exception {
		try {
			if (codigo_documento.equals("") && sw) {
				Messagebox
						.show("El documento no se ha guardado aún",
								"Informacion ..", Messagebox.OK,
								Messagebox.EXCLAMATION);
				return;
			}

			Map<String, Object> param_cont = new HashMap<String, Object>();
			param_cont.put("codigo_empresa", empresa.getCodigo_empresa());
			param_cont.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param_cont.put("codigo_documento", codigo_documento);

			if (!sw) {
				param_cont.put("facturacion", getFacturacion());
			}
			Nota_contable nota_contable = facturacionService
					.guardarContabilizacionIndividual(param_cont, sw, true);
			if (sw && nota_contable != null) {
				Messagebox
						.show("El documento fue contabilizado satisfactoriamente",
								"Informacion ..", Messagebox.OK,
								Messagebox.INFORMATION);
			} else if (!sw) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("nota_contable", nota_contable);
				Component componente = Executions.createComponents(
						"/pages/ver_contabilizacion.zul", this, parametros);
				final Window ventana = (Window) componente;
				ventana.setWidth("850px");
				ventana.setHeight("450px");
				ventana.setClosable(true);
				ventana.setMaximizable(true);
				ventana.setTitle("CONTABILIZAcion DEL DOCUMENTO");
				ventana.setMode("modal");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void imprimirNota_contable(String codigo_documento) throws Exception {
		if (codigo_documento.equals("")) {
			Messagebox.show("El documento no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Nota_contable");
		paramRequest.put("codigo_comprobante", CODIGO_COMPROBANTE_FHC);
		paramRequest.put("codigo_documento", codigo_documento);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void imprimirCopago(String codigo_documento) throws Exception {
		if (codigo_documento.equals("")) {
			Messagebox.show("El documento no se ha guardado aun",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
				.getValue());
		if (admision == null) {
			Messagebox.show("Debe seleccionar una admision", "Informacion ..",
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
		if (paciente == null) {
			Messagebox.show(
					"Para realizar esta opcion debe seleccionar el paciente",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Caja caja_aux = new Caja();
		caja_aux.setCodigo_empresa(empresa.getCodigo_empresa());
		caja_aux.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		caja_aux.setCodigo_tercero(paciente.getNro_identificacion());
		caja_aux.setNro_ingreso(admision.getNro_ingreso());
		caja_aux = cajaService.consultar(caja_aux);
		if (caja_aux == null) {
			Messagebox.show("Esta factura no ha generado copago",
					"Informacin ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Recibo_caja_paciente");
		paramRequest.put("fuente", caja_aux.getFuente());
		paramRequest.put("codigo_recibo", caja_aux.getCodigo_recibo());
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());
		paramRequest.put("src", "Recibo_caja_paciente.jasper");
		paramRequest.put("cuota_moderadora", new Double(0));

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public String getNroAutorizacion() {
		return tbxNro_autorizacion.getValue();
	}

	public void setNroAutorizacion(String nro_autorizacion) {
		tbxNro_autorizacion.setValue(nro_autorizacion);
	}

	/**
	 * Este metodo nos permite cargar el evento para acceder por medio de teclas
	 * al modulo
	 *
	 * @author Luis Miguel
	 *
	 */
	private void inicializarEventos() {
		addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				ejecutarEvento(((KeyEvent) event).getKeyCode());
			}
		});
		lbxVia_ingreso.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						try {
							String via_ingreso = lbxVia_ingreso
									.getSelectedItem().getValue().toString();

							Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
							configuracion_admision_ingreso
									.setCodigo_empresa(codigo_empresa);
							configuracion_admision_ingreso
									.setCodigo_sucursal(codigo_sucursal);
							configuracion_admision_ingreso
									.setVia_ingreso(via_ingreso);
							configuracion_admision_ingreso = configuracion_admision_ingresoService
									.consultar(configuracion_admision_ingreso);

							Anular_facturasAction.this.configuracion_admision_ingreso = configuracion_admision_ingreso;

							if (configuracion_admision_ingreso != null
									&& configuracion_admision_ingreso
											.getLaboratorio_pyp()) {
								rowProgramaPyp.setVisible(true);
								listarProgramasPyp(lbxVia_ingreso,
										lbxPrograma_pyp);
							} else {
								rowProgramaPyp.setVisible(false);
							}

							Listitem listitem = lbxVia_ingreso
									.getSelectedItem();
							if (listitem != null && !via_ingreso.isEmpty()) {
								lbxServicios.habilitarAcciones(true);
								seleccionarAdmision(via_ingreso);
								pasarDatosSerivicio((Paciente) tbxNro_identificacion
										.getRegistroSeleccionado());
								if (via_ingreso.isEmpty()) {
									setTitulo("");
								}
							} else {
								lbxServicios.limpiarCargaDatos();
								setTitulo("");
							}

							seleccionarVia_ingreso();

						} catch (Exception e) {
							MensajesUtil.mensajeError(e, "",
									Anular_facturasAction.this);
						}
					}
				});

		lbxPrograma_pyp.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						try {
							Listitem listitem = lbxPrograma_pyp
									.getSelectedItem();
							if (listitem != null) {
								lbxServicios.habilitarAcciones(true);
								String via_ingreso = listitem.getValue();
								seleccionarAdmision(via_ingreso);
								pasarDatosSerivicio((Paciente) tbxNro_identificacion
										.getRegistroSeleccionado());
								if (lbxPrograma_pyp.getSelectedIndex() == 0) {
									setTitulo("");
								}
							} else {
								lbxServicios.limpiarCargaDatos();
								setTitulo("");
							}
							seleccionarVia_ingreso();
						} catch (Exception e) {
							MensajesUtil.mensajeError(e, "",
									Anular_facturasAction.this);
						}
					}
				});

		lbxCausa_externa.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						onseleccionarCausaExterna();
					}
				});
		Utilidades.aplicarOnOk(dtbxFecha, dtbxFecha_ingreso);
		Utilidades.aplicarOnOk(dtbxFecha_ingreso, dtbxFecha_egreso);
		Utilidades.aplicarOnOk(dtbxFecha_egreso, tbxPrestador);

		dtbxFecha.addEventListener(Events.ON_CHANGE,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						if (!tbxAccion.getValue().equals("modificar")) {
							Date date = dtbxFecha.getValue();
							dtbxFecha_ingreso.setValue(date);
							cargarFechaEgreso(date);
						}
					}
				});

		dtbxFecha_ingreso.addEventListener(Events.ON_CHANGE,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						cargarFechaEgreso(dtbxFecha_ingreso.getValue());
					}
				});

		lbxContratos.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Object object = lbxContratos.getSelectedItem()
								.getValue();
						if (object != null && !(object instanceof String)) {
							Via_ingreso_contratadas via_ingreso_contratadas = (Via_ingreso_contratadas) object;
							lbxServicios.habilitarAcciones(true);
							lbxServicios
									.setVia_ingreso_contratadas(via_ingreso_contratadas);

							Administradora administradora = tbxCodigo_administradora
									.getRegistroSeleccionado();
							Contratos contratos = (Contratos) lbxContratos
									.getSelectedItem().getAttribute(
											"CONTRATO_CONSULTADO");
							if (!contratos
									.getTipo_facturacion()
									.equals(IConstantes.TIPO_CONTRATO_INDIVIDUAL_EVENTO)) {

							} else {

								if (rol_usuario
										.equalsIgnoreCase(IRoles.FACTURADOR)
										&& !parametros_empresa
												.getPermitir_facturador_evento()
										&& !administradora
												.getTipo_aseguradora()
												.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) {

								} else {

								}
							}
						} else {
							lbxServicios.setVia_ingreso_contratadas(null);
							MensajesUtil.notificarAlerta(
									"Seleccionar contrato", lbxContratos);
							lbxServicios.habilitarAcciones(false);
						}
					}
				});

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

	private void pasarDatosSerivicio(Paciente paciente) throws Exception {

		Listitem listitem = lbxVia_ingreso.getSelectedItem();
		Listitem listitem_aux = lbxPrograma_pyp.getSelectedItem();

		Elemento via_ingreso = null;

		if (configuracion_admision_ingreso != null
				&& configuracion_admision_ingreso.getLaboratorio_pyp()) {
			if (!listitem_aux.getValue().toString().isEmpty()) {
				via_ingreso = new Elemento(listitem_aux.getValue().toString(),
						"via_ingreso", listitem_aux.getLabel());
			}
		} else {
			via_ingreso = new Elemento(listitem.getValue().toString(),
					"via_ingreso", listitem.getLabel());
		}

		Administradora administradora = tbxCodigo_administradora
				.getRegistroSeleccionado();
		Prestadores prestadores = (Prestadores) tbxPrestador
				.getAttribute(PRESTADOR);
		Listitem listitem_nro_ingreso = lbxNro_ingreso.getSelectedItem();

		Admision admision = null;
		if (listitem_nro_ingreso != null) {
			admision = listitem_nro_ingreso.getValue();
		}

		lbxServicios.cargarDatos(paciente, administradora, via_ingreso,
				prestadores, dtbxFecha.getValue(),
				dtbxFecha_ingreso.getValue(), dtbxFecha_egreso.getValue(),
				admision, null);

		lbxCausa_externa.setDisabled(!lbxVia_ingreso.getSelectedItem()
				.getValue().toString().equals(IVias_ingreso.URGENCIA)
				&& !lbxVia_ingreso.getSelectedItem().getValue().toString()
						.equals(IVias_ingreso.HOSPITALIZACIONES));
	}

	protected void seleccionarAdmision(String via_ingreso) throws Exception {

		boolean seleccion = false;
		for (Listitem listitem : lbxNro_ingreso.getItems()) {
			Admision admision = (Admision) listitem.getValue();
			if (admision != null
					&& admision.getVia_ingreso().equals(via_ingreso)) {
				listitem.setSelected(true);
				seleccion = true;
				selectedAdmision(listitem, false);
			}
		}
		// Seleccionamos el ultimo
		if (!seleccion) {
			if (lbxNro_ingreso.getItemCount() > 0) {
				lbxNro_ingreso
						.setSelectedIndex(lbxNro_ingreso.getItemCount() - 1);
				tbxPrestador.limpiarSeleccion(false);
			}
		}
	}

	public void onCargarContrato(Contratos contratos,
			Via_ingreso_contratadas via_ingreso_contratadas, boolean agregar) {
		if (facturacion_current != null) {
			cargarInformacionFactura();
		} else if (contratos != null) {
			String tipo_facturacion = Utilidades.getDescripcionElemento(
					contratos.getTipo_facturacion(), "tipo_contrato",
					elementoService);
			if (tipo_facturacion != null) {
				setTitulo(" - MODO FACTURACION: "
						+ tipo_facturacion.toUpperCase());
			} else {
				setTitulo("");
			}
			if (agregar) {
				Listitem listitem = new Listitem(
						contratos != null ? contratos.getNombre() + " - "
								+ contratos.getNro_contrato() : "No registrado",
						via_ingreso_contratadas);
				listitem.setAttribute("CONTRATO_CONSULTADO", contratos);
				lbxContratos.appendChild(listitem);
				lbxContratos.setSelectedIndex(0);
			}

			Administradora administradora = tbxCodigo_administradora
					.getRegistroSeleccionado();

			if (!contratos.getTipo_facturacion().equals(
					IConstantes.TIPO_CONTRATO_INDIVIDUAL_EVENTO)) {

			} else {

				if (rol_usuario.equalsIgnoreCase(IRoles.FACTURADOR)
						&& !parametros_empresa.getPermitir_facturador_evento()
						&& !administradora
								.getTipo_aseguradora()
								.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) {

				} else {

				}
			}

		}
	}

	private void setTitulo(String titulo) {
		capPaciente.setLabel("DATOS DEL PACIENTE" + titulo);
	}

	private void cargarFechaEgreso(Date date) {
		if (date != null) {
			Calendar calendar_aux = Calendar.getInstance();
			calendar_aux.setTime(date);
			calendar_aux.set(Calendar.MINUTE,
					calendar_aux.get(Calendar.MINUTE) + 15);
			dtbxFecha_egreso.setValue(calendar_aux.getTime());
		} else {
			dtbxFecha_egreso.setValue(date);
		}
	}

	private void onseleccionarCausaExterna() {
		String causa_externa = lbxCausa_externa.getSelectedItem().getValue();
		if (causa_externa
				.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRABAJO)
				|| causa_externa
						.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRANSITO)
				|| causa_externa
						.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_EVENTO_CATASTROFICO)) {
			// pedimos seleccion de administradora, dependiendo
			// si es soat o arl
			solicitarAseguradora(causa_externa);
		} else {
			Paciente paciente = (Paciente) tbxNro_identificacion
					.getAttribute("paciente");
			if (paciente != null) {
				cambiarAdministradora(paciente, paciente.getCodigo_empresa(),
						paciente.getCodigo_sucursal(),
						paciente.getCodigo_administradora(), true);
				tbxCodigo_administradora.removeAttribute(CONTRATO);
			} else {
				MensajesUtil.notificarAlerta(
						"El paciente a tomar la admision no existe "
								+ tbxNro_identificacion.getValue(), this);
			}
		}
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
				seleccionarAdministradora(administradora, false);
			}

			@Override
			public void onCancelar() {
				lbxCausa_externa.setSelectedIndex(0);
				Administradora administradora = tbxCodigo_administradora
						.getRegistroSeleccionado();
				seleccionarAdministradora(administradora, true);
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

	public void seleccionarAdministradora(Administradora administradora,
			boolean propia) {
		Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
		cambiarAdministradora(paciente, paciente.getCodigo_empresa(),
				paciente.getCodigo_sucursal(), administradora.getCodigo(),
				propia);
	}

	/**
	 * Este metodo me permite ejecutar un metodo dependiendo del codigo
	 * seleccionado
	 *
	 * @author Luis Miguel
	 *
	 */
	private void ejecutarEvento(int keycode) {
		try {
			// log.info("Key: " + keycode);
			switch (keycode) {
			case IKeyCode.ALT_P: // Esta primera convinacion me permite cargar
				// un paciente
				tbxNro_identificacion.accederAutomatico();
				break;
			case IKeyCode.ALT_S: // Esta primera convinacion me permite cargar
				// un paciente
				lbxServicios
						.agregarServicio(
								ServiciosFacturacionMacro.ETIPO_SERVICIO.SERVICIO,
								true);
				break;
			case IKeyCode.ALT_A: // Esta primera convinacion me permite cargar
				// un paciente
				lbxServicios
						.agregarServicio(
								ServiciosFacturacionMacro.ETIPO_SERVICIO.ARTICULO,
								true);
				break;
			case IKeyCode.ALT_M: // Para llamar dirigirce al campo medico
				tbxPrestador.accederAutomatico();
				break;
			case IKeyCode.ALT_U: // para agregar el foco en unidad funcional
				lbxVia_ingreso.setFocus(true);
				break;
			case IKeyCode.ALT_C: // Para agregar el foco en centros de salud
				tbxCentro_salud.setFocus(true);
				break;
			case IKeyCode.ALT_O: // Para agregar el foco en centros de salud
				popup.open(btnConfiguracionBusqeuda);
				break;
			case IKeyCode.ALT_R: // Para agregar el foco en centros de salud
				consultarServiciosFacturas(true);
				break;
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	public void realizarAccionMultiplesContratos(
			List<Via_ingreso_contratadas> listado_vias_contratadas) {
		// log.info("Ejecutando metodo @realizarAccionMultiplesContratos() ===> "
		// + listado_manuales);
		lbxContratos.getChildren().clear();
		if (listado_vias_contratadas.size() == 1) {
			Via_ingreso_contratadas via_ingreso_contratadas = listado_vias_contratadas
					.get(0);
			if (via_ingreso_contratadas == null) {
				MensajesUtil
						.mensajeAlerta("Verificar contratos",
								"Al parecer los contratos relacionados con este paciente estan cerrados");
			} else {
				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(via_ingreso_contratadas
						.getCodigo_empresa());
				contratos.setCodigo_sucursal(via_ingreso_contratadas
						.getCodigo_sucursal());
				contratos.setCodigo_administradora(via_ingreso_contratadas
						.getCodigo_administradora());
				contratos.setId_plan(via_ingreso_contratadas.getId_plan());
				contratos = contratosService.consultar(contratos);
				Listitem listitem = new Listitem(
						contratos != null ? contratos.getNombre() + " - "
								+ contratos.getNro_contrato() : "No registrado",
						via_ingreso_contratadas);
				listitem.setAttribute("CONTRATO_CONSULTADO", contratos);
				lbxContratos.appendChild(listitem);

				lbxContratos.setSelectedIndex(0);
			}
		} else {
			int capitados = 0;
			lbxContratos.appendItem("-- Seleccionar contrato --", "");
			for (Via_ingreso_contratadas via_ingreso_contratadas : listado_vias_contratadas) {
				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(via_ingreso_contratadas
						.getCodigo_empresa());
				contratos.setCodigo_sucursal(via_ingreso_contratadas
						.getCodigo_sucursal());
				contratos.setCodigo_administradora(via_ingreso_contratadas
						.getCodigo_administradora());
				contratos.setId_plan(via_ingreso_contratadas.getId_plan());
				contratos = contratosService.consultar(contratos);
				Listitem listitem = new Listitem(
						contratos != null ? contratos.getNombre() + " - "
								+ contratos.getNro_contrato() : "No registrado",
						via_ingreso_contratadas);
				listitem.setAttribute("CONTRATO_CONSULTADO", contratos);
				if (contratos != null
						&& contratos.getTipo_facturacion().equals("01")) {
					capitados++;
					listitem.setSelected(true);
					listitem.setTooltiptext("Este contrato es capitado");
					lbxServicios
							.setVia_ingreso_contratadas(via_ingreso_contratadas);
				}
				lbxContratos.appendChild(listitem);
			}
			if (capitados == 0 || capitados > 1) {
				lbxServicios.setVia_ingreso_contratadas(null);
				lbxContratos.setSelectedIndex(0);
				Clients.scrollIntoView(lbxContratos);
				MensajesUtil.notificarAlerta("Seleccionar contrato",
						lbxContratos);
			}
		}
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

	public Date getFechaIngreso() {
		return dtbxFecha_ingreso.getValue();
	}

	public Date getFechaEgreso() {
		return dtbxFecha_egreso.getValue();
	}

	public void abrirVentanaAdmisionesaAbiertas() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		Facturacion_rips_abiertasAction facturacion_rips_abiertas = (Facturacion_rips_abiertasAction) Executions
				.createComponents("/pages/facturacion_rips_abiertas.zul", this,
						parametros);
		facturacion_rips_abiertas.setTitle("Verificar admisiones abiertas ["
				+ centro_atencion_session.getNombre_centro() + "]");
		facturacion_rips_abiertas.doModal();
	}

	public void generarReciboCaja() throws Exception {
		try {
			List<Detalle_factura> listado_servicios = lbxServicios
					.getServicios();
			String via_ingreso = lbxVia_ingreso.getSelectedItem().getValue()
					.toString();
			Paciente paciente = (Paciente) tbxNro_identificacion
					.getRegistroSeleccionado();
			boolean isParticular = isParticularAtencion();
			if (isParticular) {
				if (!listado_servicios.isEmpty()) {
					Caja caja = new Caja(false);
					caja.setCodigo_empresa(codigo_empresa);
					caja.setCodigo_sucursal(codigo_sucursal);
					caja.setCodigo_orden(getCodigoRecibo());
					caja = cajaService.consultar(caja);

					if (!ServiciosDisponiblesUtils.pagaCopago(paciente,
							via_ingreso) && !isParticular) {
						throw new ValidacionRunTimeException(
								"Este paciente no paga copago.");
					}

					String anioo = new SimpleDateFormat("yyyy")
							.format(new Date());

					Anio_cuota_moderadora anio_cuota_moderadora = ResCalculadorDeRangoCuota
							.getGrupo(paciente, afiliaciones_meService,
									salario_anualService,
									aportes_cotizacionesService,
									anio_cuota_moderadoraService, anioo,
									getSucursal());

					double porcentaje_copago = (anio_cuota_moderadora != null ? anio_cuota_moderadora
							.getPorcentaje_copago() : 0);

					// para que cobre el 10 por ciento cuando se a un
					// subcidiado, con un nivel mayor de 1
					if (paciente.getTipo_usuario().equals(
							IConstantes.REGIMEN_SUBCIDIADO)
							&& !isParticular) {
						// si es subcidiado paga el 10 porciento.
						porcentaje_copago = 10;
					}

					Paciente pacienteAux = Res.clonar(paciente);
					pacienteAux
							.setCodigo_administradora(tbxCodigo_administradora
									.getValue());

					String nro_ingreso_proximo = Utilidades
							.generarCodigoProximoAdmision(paciente,
									registro_admisionService);

					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("caja", caja);
					parametros.put("paciente", pacienteAux);
					parametros.put("nro_ingreso", nro_ingreso_proximo);
					parametros.put("nro_autorizacion", "");
					parametros.put("copago_autorizaciones", "S");
					parametros.put("copago_medicamentos", "N");
					parametros.put("cuota_moderadora", porcentaje_copago);
					parametros.put("codigo_orden", getCodigoRecibo());

					if (caja == null) {
						List<Detalle_caja> lista_detalle = new ArrayList<Detalle_caja>();
						for (Detalle_factura detalle_factura_aux : listado_servicios) {

							String nombre_procedimiento = detalle_factura_aux
									.getDetalle();
							boolean es_pyp = configuracion_admision_ingreso != null ? configuracion_admision_ingreso
									.getEs_pyp() : false;

							Detalle_caja detalle = new Detalle_caja();
							detalle.setCodigo_empresa(empresa
									.getCodigo_empresa());
							detalle.setCodigo_sucursal(sucursal
									.getCodigo_sucursal());
							detalle.setCodigo_articulo(detalle_factura_aux
									.getCodigo_articulo());
							detalle.setDetalle(nombre_procedimiento);
							detalle.setCantidad(detalle_factura_aux
									.getCantidad());
							detalle.setValor_unitario(detalle_factura_aux
									.getValor_unitario());
							if (isParticular) {
								detalle.setValor_total((detalle_factura_aux
										.getValor_unitario() * detalle_factura_aux
										.getCantidad()));
							} else {
								detalle.setValor_total((detalle_factura_aux
										.getValor_unitario() * (porcentaje_copago / 100))
										* detalle_factura_aux.getCantidad());
							}
							detalle.setPorcentaje_adicionar(0);

							detalle.setCopago(porcentaje_copago);
							if (!es_pyp || isParticular) {
								lista_detalle.add(detalle);
							}
						}

						if (lista_detalle.isEmpty()) {
							throw new ValidacionRunTimeException(
									"No se puede cobrar copago, por que los servicios ordenados son de PyP.");
						}
						parametros.put("lista_detalle", lista_detalle);
					}
					Map<String, Object> mapPagare = getPagare();
					parametros.put("mapPagare", mapPagare);
					mostarVistaCaja(parametros);
				} else {
					throw new ValidacionRunTimeException(
							"Para esto debe seleccionar que servicios de "
									+ lbxVia_ingreso.getSelectedItem()
											.getLabel() + ", va a facturar");
				}

			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Map<String, Object> getPagare() throws Exception {
		List<Detalle_factura> listado_servicios = lbxServicios.getServicios();
		Paciente paciente = (Paciente) tbxNro_identificacion
				.getRegistroSeleccionado();
		boolean isParticular = isParticularAtencion();
		Map<String, Object> mapPagare = new HashMap<String, Object>();
		Pagare pagare = null;
		if (isParticular) {
			if (!listado_servicios.isEmpty()) {
				pagare = new Pagare(false);
				pagare.setCodigo_empresa(codigo_empresa);
				pagare.setCodigo_sucursal(codigo_sucursal);
				pagare.setCodigo_orden(getCodigoRecibo());
				pagare = pagareService.consultarAdmision(pagare);

				String anioo = new SimpleDateFormat("yyyy").format(new Date());

				Anio_cuota_moderadora anio_cuota_moderadora = ResCalculadorDeRangoCuota
						.getGrupo(paciente, afiliaciones_meService,
								salario_anualService,
								aportes_cotizacionesService,
								anio_cuota_moderadoraService, anioo,
								getSucursal());

				double porcentaje_copago = (anio_cuota_moderadora != null ? anio_cuota_moderadora
						.getPorcentaje_copago() : 0);

				// para que cobre el 10 por ciento cuando se a un
				// subcidiado, con un nivel mayor de 1
				if (paciente.getTipo_usuario().equals(
						IConstantes.REGIMEN_SUBCIDIADO)
						&& !isParticular) {
					// si es subcidiado paga el 10 porciento.
					porcentaje_copago = 10;
				}

				Paciente pacienteAux = Res.clonar(paciente);
				pacienteAux.setCodigo_administradora(tbxCodigo_administradora
						.getValue());

				if (pagare == null) {
					List<Detalle_pagare> lista_detalle = new ArrayList<Detalle_pagare>();
					for (Detalle_factura detalle_factura_aux : listado_servicios) {
						String nombre_procedimiento = detalle_factura_aux
								.getDetalle();
						boolean es_pyp = configuracion_admision_ingreso != null ? configuracion_admision_ingreso
								.getEs_pyp() : false;

						Detalle_pagare detalle = new Detalle_pagare();
						detalle.setCodigo_empresa(empresa.getCodigo_empresa());
						detalle.setCodigo_sucursal(sucursal
								.getCodigo_sucursal());
						detalle.setCodigo_servicio(detalle_factura_aux
								.getCodigo_articulo());
						detalle.setConcepto(nombre_procedimiento);
						detalle.setCantidad(detalle_factura_aux.getCantidad());
						detalle.setValor_unitario(detalle_factura_aux
								.getValor_unitario());
						if (isParticular) {
							detalle.setValor_total((detalle_factura_aux
									.getValor_unitario() * detalle_factura_aux
									.getCantidad()));
						} else {
							detalle.setValor_total((detalle_factura_aux
									.getValor_unitario() * (porcentaje_copago / 100))
									* detalle_factura_aux.getCantidad());
						}

						detalle.setCopago(porcentaje_copago);
						if (!es_pyp || isParticular) {
							lista_detalle.add(detalle);
						}

					}
					mapPagare.put("lista_detalle_pagare", lista_detalle);
				}
				mapPagare.put("pagare", pagare);
			}

		}

		return mapPagare;
	}

	private Window mostarVistaCaja(Map<String, Object> parametros) {
		Recibo_cajaAction componente = (Recibo_cajaAction) Executions
				.createComponents("/pages/caja_cuota_moderadora.zul", this,
						parametros);
		// Esto es para cuando se guarde el recibo de caja, tambien guarde la
		// admision
		if (onGuardarCaja == null) {
			onGuardarCaja = new IOnGuardarCaja() {
				@Override
				public boolean onGuardar() {
					try {
						return true;
					} catch (Exception e) {
						return false;
					}
				}
			};
		}
//		componente.setOnGuardarCaja(onGuardarCaja);

		Window win = (Window) componente;
		win.setWidth("100%");
		win.setHeight("100%");
		win.doModal();
		return win;
	}

	private String getCodigoRecibo() {
		Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
		if (paciente != null) {
			String codigo_recibo_nuevo = Utilidades.getCodigoReciboCaja(
					paciente, registro_admisionService);
			Listitem listitem = lbxNro_ingreso.getSelectedItem();
			if (listitem != null && listitem.getValue() instanceof Admision) {
				Admision admision = (Admision) listitem.getValue();
				codigo_recibo_nuevo = paciente.getTipo_identificacion()
						+ admision.getNro_ingreso()
						+ paciente.getNro_identificacion();
			}
			return codigo_recibo_nuevo;
		} else {
			return "";
		}
	}

}
