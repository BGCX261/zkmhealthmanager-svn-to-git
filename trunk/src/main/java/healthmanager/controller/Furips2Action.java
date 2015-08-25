/*
 * furips2Action.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Furips2;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Furips2Service;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Furips2Action extends GeneralComposer {
	@WireVariable
	private Furips2Service furips2Service;
	@WireVariable
	private PrestadoresService prestadoresService;
	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private DepartamentosService departamentosService;
	@WireVariable
	private MunicipiosService municipiosService;
	@WireVariable
	private GeneralExtraService generalExtraService;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private AdmisionService admisionService;

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

	@View
	private Toolbarbutton btGuardarTemp;
	@View
	private Toolbarbutton btCancelar;
	@View
	private Toolbarbutton btGuardar;
	@View
	private Toolbarbutton btNew;
	@View
	private Toolbarbutton btCancel;

	@View
	private Datebox dtbxFecha_radicacion;
	@View
	private Checkbox cbxRg;
	@View
	private Textbox tbxNro_radicacion;
	@View
	private Textbox tbxNro_radicacion_anterior;
	@View
	private Textbox tbxNro_factura;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_identificacion;
	@View
	private Radiogroup rdbCondicion_accidentado;
	@View
	private Checkbox tbxNaturaleza_evento;
	@View
	private Radiogroup rdbEventosNaturales;
	@View
	private Radiogroup rdbEventosTerroristas;
	@View
	private Checkbox cbxOtros;
	@View
	private Textbox tbxDescripcion_otro_evento;
	@View
	private Textbox tbxDireccion_evento;
	@View
	private Datebox dtbxFecha_evento_accidentente;
	@View
	private Listbox lbxCodigo_departamento_evento;
	@View
	private Listbox lbxCodigo_municipio_evento;
	@View
	private Textbox tbxDescripcion_breve_evento;
	@View
	private Radiogroup rdbEstadoAseguramiento;
	@View
	private Textbox tbxMarca_vehiculo;
	@View
	private Textbox tbxPlaca_vehiculo;
	@View
	private Radiogroup rdbTipo_servicio_vehiculo;
	@View
	private Textbox tbxCodigo_aseguradora_vehiculo;
	@View
	private Textbox tbxNro_poliza_vehiculo;
	@View
	private Radiogroup rdbIntervencion_autoridad_vehiculo;
	@View
	private Datebox dtbxVigencia_desde_vehiculo;
	@View
	private Datebox dtbxVigencia_hasta_vehiculo;
	@View
	private Radiogroup rdbCobroExcedentePoliza;
	@View
	private Textbox tbxPropietario_apellido1;
	@View
	private Textbox tbxPropietario_apellido2;
	@View
	private Textbox tbxPropietario_nombre1;
	@View
	private Textbox tbxPropietario_nombre2;
	@View
	private Listbox lbxPropietario_tipoDocumento;
	@View
	private Textbox tbxPropietario_nro_identificacion;
	@View
	private Textbox tbxPropietario_direccion;
	@View
	private Listbox lbxPropietario_codigo_departamento;
	@View
	private Listbox lbxPropietario_codigo_municipio;
	@View
	private Textbox tbxPropietario_telefono;
	@View
	private Intbox ibxPropietario_total_folios;
	@View
	private Textbox tbxConductor_apellido1;
	@View
	private Textbox tbxConductor_apellido2;
	@View
	private Textbox tbxConductor_nombre1;
	@View
	private Textbox tbxConductor_nombre2;
	@View
	private Listbox lbxConductor_tipodocumento;
	@View
	private Textbox tbxConductor_nro_documento;
	@View
	private Textbox tbxConductor_direccion_res;
	@View
	private Listbox lbxConductor_codigo_departamento;
	@View
	private Listbox lbxConductor_codigo_municipio;
	@View
	private Textbox tbxConductor_telefono;
	@View
	private Radiogroup rdbRemision_tiporeferencia;
	@View
	private Datebox dtbxRemision_fecha;
	@View
	private Textbox tbxRemision_prestador_remite;
	@View
	private Textbox tbxRemision_codigo_inscripcion_remite;
	@View
	private Textbox tbxRemision_profesional;
	@View
	private Textbox tbxRemision_cargo_remite;
	@View
	private Datebox dtbxRemision_fecha_aceptacion;
	@View
	private Textbox tbxRemision_prestador_recibe;
	@View
	private Textbox tbxRemision_codigo_inscripcion_recibe;
	@View
	private Textbox tbxRemision_personal_recibe;
	@View
	private Textbox tbxRemision_cargo_recibe;
	@View
	private Textbox tbxAmparo_placa_nro;
	@View
	private Textbox tbxAmparo_transporto_victima_desde;
	@View
	private Textbox tbxAmparo_transporto_victima_hasta;
	@View
	private Radiogroup rdbAmparo_tipo_transporte;
	@View
	private Radiogroup rdbAmparo_lugar_donde_recoje_victima;
	@View
	private Datebox dtbxIx_fecha_ingreso;
	@View
	private Datebox dtbxIx_fecha_egreso;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxIx_codigo_diagnostico_ingreso;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxIx_codigo_diagnostico_egreso;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxIx_otro_codigo_diagnostico_ingreso;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxIx_otro_codigo_diagnostico_egreso;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxIx_otro_codigo_diagnostico_ingreso2;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxIx_otro_codigo_diagnostico_egreso2;
	@View
	private Textbox tbxIx_primer_apellido_medico;
	@View
	private Textbox tbxIx_segundo_apellido_medico;
	@View
	private Textbox tbxIx_primer_nombre_medico;
	@View
	private Textbox tbxIx_segundo_nombre_medico;
	@View
	private Listbox lbxIx_tipo_documento_medico;

	@View
	private Listbox lbxTipoDocumento;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxIx_nro_documento_medico;
	@View
	private Textbox tbxIx_nro_registro_medico;

	@View
	private Toolbarbutton btnlimpiarMedico;

	@View
	private Doublebox dbxGasto_medico_quirurgicos_valor_facturado;
	@View
	private Doublebox dbxGasto_medico_quirurgicos_valor_reclamado;
	@View
	private Doublebox dbxGasto_transporte_movilizacion_valor_facturado;
	@View
	private Doublebox dbxGasto_transporte_movilizacion_valor_reclamado;

	@View
	private Textbox tbxRazonSocial;
	@View
	private Textbox tbxCodigoHabilitacion;
	@View
	private Textbox tbxNit;

	@View
	private Textbox tbxApellido1;
	@View
	private Textbox tbxApellido2;
	@View
	private Textbox tbxNombre1;
	@View
	private Textbox tbxNombre2;
	@View
	private Datebox dtbxFecha_nacimiento;
	@View
	private Textbox tbxSexo;
	@View
	private Textbox tbxDireccion;
	@View
	private Listbox lbxDepartamento;
	@View
	private Listbox lbxCodigo_municipio;
	@View
	private Textbox tbxTelefono;
	@View
	private Radiogroup rdbZonaDatosDondeOcurrio;

	@View
	private Toolbarbutton btnLimpiarNroIdentificacion;

	@View
	private Toolbarbutton btImprimir;
	@View
	private Label lbFormatoImpresion;
	@View
	private Listbox lbxFormato;

	@View
	private Groupbox groupConductor;
	@View
	private Groupbox groupPropietario;
	@View
	private Groupbox groupDatiosVehiculo;

	@View
	private Radio radioAsegurado;
	@View
	private Radio radioNoAsegurado;
	@View
	private Radio radioFantasma;
	@View
	private Radio radioPoliza;
	@View
	private Radio radioFuga;

	private Furips2 furips2Modificar;

	private OnAccionFurips onAccionFurips;

	private static final String CONDUCTOR = "01";

	public static final String PARAM_NIVEL = "n_parametro";
	public static final String PARAM_EVENTO = "param_evento";
	public static final String PARAM_PACIENTE = "PARAM_paci";
	public static final String PARAM_FURIPS = "PARAM_FPS";
	public static final String PARAM_MEDICO = "PARAM_medico";
	public static final String PARAM_CAUSA_EXTERNA = "PARAM_cext";

	public static final String REGISTRO_DESDE_ADMISION = "01";
	public static final String REGISTRO_DESDE_EVENTO = "evento";

	public static final String VEHICULO_FANTASMA = "03";
	public static final String VEHICULO_EN_FUGA = "05";
	public static final String VEHICULO_ASEGURADO = "01";

	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
	}

	private void inicializar() {
		listarCombos();
		cargaDatosDelaEmpresa();
		pararametrizarBandbox();
		Utilidades.setdisable(rdbCondicion_accidentado, true);
		inicializarValidacionRadioGroup();
	}

	private void habilitarImpresion(boolean habilitar) {
		btImprimir.setVisible(habilitar);
		lbFormatoImpresion.setVisible(habilitar);
		lbxFormato.setVisible(habilitar);
	}

	@Override
	public void params(Map<String, Object> map) {
		inicializar();
		String parametro_inicializacion = (String) map.get(PARAM_NIVEL);
		if (parametro_inicializacion != null
				&& parametro_inicializacion.equals(REGISTRO_DESDE_ADMISION)) {
			try {
				accionForm(true, "registrar");
			} catch (Exception e) {
				e.printStackTrace();
			}

			btGuardarTemp.setVisible(true);
			btCancelar.setVisible(true);
			btGuardar.setVisible(false);
			btNew.setVisible(false);
			btCancel.setVisible(false);
			onAccionFurips = (OnAccionFurips) map.get(PARAM_EVENTO);

			Furips2 furips2 = (Furips2) map.get(PARAM_FURIPS);
			if (furips2 != null) {
				try {
					mostrarDatos(furips2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Paciente paciente = (Paciente) map.get(PARAM_PACIENTE);
				if (paciente != null) {
					seleccionarPaciente(paciente);
					tbxNro_identificacion.seleccionarRegistro(paciente,
							paciente.getNro_identificacion(),
							paciente.getNombreCompleto());
					btnLimpiarNroIdentificacion.setVisible(false);
					tbxNro_identificacion.setButtonVisible(false);
					tbxNro_identificacion.setObject(paciente);
				}
				dtbxIx_fecha_ingreso.setValue(Calendar.getInstance().getTime());
			}
			habilitarImpresion(false);

			String causa_externa = (String) map.get(PARAM_CAUSA_EXTERNA);
			if (causa_externa != null) {
				if (causa_externa
						.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRANSITO)) {
					tbxNaturaleza_evento.setChecked(true);
				}
				tbxNaturaleza_evento.setDisabled(true);
			}

			// Cargamos datos de los medicos
			Map<String, Object> map_medico = (Map<String, Object>) map
					.get(PARAM_MEDICO);
			if (map_medico != null) {
				String nro_identificacion = (String) map_medico
						.get("nro_identificacion");
				String codigo_empresa = (String) map_medico
						.get("codigo_empresa");
				String codigo_sucursal = (String) map_medico
						.get("codigo_sucursal");
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(codigo_empresa);
				prestadores.setCodigo_sucursal(codigo_sucursal);
				prestadores.setNro_identificacion(nro_identificacion);
				prestadores = prestadoresService.consultar(prestadores);
				if (prestadores != null) {
					cargarDatosMedico(prestadores);
					tbxIx_nro_documento_medico.seleccionarRegistro(prestadores,
							prestadores.getNro_identificacion(), "");
				}
			}
			accionNaturalezaEvento();
		}
	}

	/**
	 * */
	public void accionNaturalezaEvento() {
		Utilidades.setdisable(rdbEventosNaturales,
				tbxNaturaleza_evento.isChecked());
		Utilidades.setdisable(rdbEventosTerroristas,
				tbxNaturaleza_evento.isChecked());

		if (tbxNaturaleza_evento.isChecked()) {
			Utilidades.setDeseleccionar(rdbEventosNaturales);
			Utilidades.setDeseleccionar(rdbEventosTerroristas);
			FormularioUtil.deshabilitarComponentes(groupDatiosVehiculo, false);

		} else {
			FormularioUtil.deshabilitarComponentes(groupDatiosVehiculo, true);
		}
		accionEstadoAseguramiento();
	}

	public interface OnAccionFurips {
		void cerrar();

		void onAgregar(Furips2 furips2);
	}

	private void inicializarValidacionRadioGroup() {
		// EventListener eventListener = new EventListener<Event>() {
		// @Override
		// public void onEvent(Event evts) throws Exception {
		// Radio radio = (Radio) evts.getTarget();
		// if(radio != null){
		// if(radio.isSelected()){
		// radio.setSelected(false);
		// }
		// }
		// }
		// };
		// int i = 0;
		// Radiogroup[] RADIO_GROUPS = { rdbAmparo_lugar_donde_recoje_victima,
		// rdbAmparo_tipo_transporte, rdbCobroExcedentePoliza,
		// rdbCondicion_accidentado, rdbEstadoAseguramiento,
		// rdbEventosNaturales, rdbEventosTerroristas,
		// rdbIntervencion_autoridad_vehiculo, rdbRemision_tiporeferencia,
		// rdbTipo_servicio_vehiculo, rdbZonaDatosDondeOcurrio};
		// for (Radiogroup radiogroup : RADIO_GROUPS) {
		// i++;
		// if(radiogroup != null){
		// radiogroup.addEventListener(Events.ON_CHECK, eventListener);
		// }else{
		// //log.info("nulo: " + i);
		// }
		// }
	}

	private void cargaDatosDelaEmpresa() {
		tbxRazonSocial.setValue("" + empresa.getNombre_empresa());
		tbxCodigoHabilitacion.setValue("" + empresa.getCodigo_habilitacion());
		tbxNit.setValue("" + empresa.getNro_identificacion());
	}

	public void listarCombos() {
		listarParameter();
		listarTipoIdentificacion();
		listarDepartamentosMunicipios();
	}

	private void listarDepartamentosMunicipios() {
		Utilidades.listarDepartamentos(lbxDepartamento, true,
				departamentosService);
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxDepartamento,
				municipiosService);

		Utilidades.listarDepartamentos(lbxCodigo_departamento_evento, true,
				departamentosService);
		Utilidades.listarMunicipios(lbxCodigo_municipio_evento,
				lbxCodigo_departamento_evento, municipiosService);

		Utilidades.listarDepartamentos(lbxPropietario_codigo_departamento,
				true, departamentosService);
		Utilidades.listarMunicipios(lbxPropietario_codigo_municipio,
				lbxPropietario_codigo_departamento, municipiosService);

		Utilidades.listarDepartamentos(lbxConductor_codigo_departamento, true,
				departamentosService);
		Utilidades.listarMunicipios(lbxConductor_codigo_municipio,
				lbxConductor_codigo_departamento, municipiosService);
	}

	private void pararametrizarBandbox() {
		parametrizarBanbboxAdmision();
		parametrizarBanbboxCie();
		parametrizarBandboxMedico();
	}

	/**
	 * */
	private void parametrizarBandboxMedico() {
		tbxIx_nro_documento_medico.inicializar(null, btnlimpiarMedico);
		BandboxRegistrosIMG<Prestadores> bandboxRegistrosIMG = new BandboxRegistrosIMG<Prestadores>() {
			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Prestadores registro) {
				cargarDatosMedico(registro);
				bandbox.setValue(registro.getNro_identificacion());
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem, Prestadores registro) {
				listitem.appendChild(new Listcell(""
						+ registro.getTipo_identificacion()));
				listitem.appendChild(new Listcell(""
						+ registro.getNro_identificacion()));
				listitem.appendChild(new Listcell("" + registro.getNombres()));
				listitem.appendChild(new Listcell("" + registro.getApellidos()));
			}

			@Override
			public List<Prestadores> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("codigo_empresa", codigo_empresa);
				parametros.put("codigo_sucursal", codigo_sucursal);
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				return prestadoresService.listar(
								parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				tbxIx_primer_apellido_medico.setValue("");
				tbxIx_segundo_apellido_medico.setValue("");
				tbxIx_primer_nombre_medico.setValue("");
				tbxIx_segundo_nombre_medico.setValue("");
				lbxIx_tipo_documento_medico.setSelectedIndex(0);
				tbxIx_nro_registro_medico.setValue("");
			}
		};
		tbxIx_nro_documento_medico.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	private void parametrizarBanbboxCie() {
		tbxIx_codigo_diagnostico_ingreso
				.inicializar(
						null,
						(Toolbarbutton) getFellow("btnIx_codigo_diagnostico_ingresoLimpiar"));
		tbxIx_codigo_diagnostico_egreso
				.inicializar(
						null,
						(Toolbarbutton) getFellow("btnIx_codigo_diagnostico_egresoLimpiar"));
		tbxIx_otro_codigo_diagnostico_ingreso
				.inicializar(
						null,
						(Toolbarbutton) getFellow("btnIx_otro_codigo_diagnostico_ingresoLimpiar"));
		tbxIx_otro_codigo_diagnostico_egreso
				.inicializar(
						null,
						(Toolbarbutton) getFellow("btnIx_otro_codigo_diagnostico_egresoLimpiar"));
		tbxIx_otro_codigo_diagnostico_ingreso2
				.inicializar(
						null,
						(Toolbarbutton) getFellow("btnIx_otro_codigo_diagnostico_ingreso2Limpiar"));
		tbxIx_otro_codigo_diagnostico_egreso2
				.inicializar(
						null,
						(Toolbarbutton) getFellow("btnIx_otro_codigo_diagnostico_egreso2Limpiar"));

		BandboxRegistrosIMG<Cie> bandboxRegistrosIMG = new BandboxRegistrosIMG<Cie>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Cie registro) {
				bandbox.setValue("" + registro.getCodigo() + " - "
						+ registro.getNombre());
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem, Cie registro) {
				listitem.appendChild(new Listcell("" + registro.getCodigo()));
				listitem.appendChild(new Listcell("" + registro.getNombre()));
			}

			@Override
			public List<Cie> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				parametros.put("limite_paginado", "limit 25 offset 0");

				return generalExtraService
						.listar(Cie.class,parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}
		};
		tbxIx_codigo_diagnostico_ingreso
				.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxIx_codigo_diagnostico_egreso
				.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxIx_otro_codigo_diagnostico_ingreso
				.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxIx_otro_codigo_diagnostico_egreso
				.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxIx_otro_codigo_diagnostico_ingreso2
				.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxIx_otro_codigo_diagnostico_egreso2
				.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	private void parametrizarBanbboxAdmision() {
		BandboxRegistrosIMG<Admision> bandboxRegistrosIMG = new BandboxRegistrosIMG<Admision>() {
			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Admision registro) {
				Paciente paciente = new Paciente();
				paciente.setCodigo_empresa(registro.getCodigo_empresa());
				paciente.setCodigo_sucursal(registro.getCodigo_sucursal());
				paciente.setNro_identificacion(registro.getNro_identificacion());
				paciente = pacienteService.consultar(paciente);
				seleccionarPaciente(paciente);
				dtbxIx_fecha_ingreso.setValue(registro.getFecha_ingreso());
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem, Admision registro) {
				listitem.appendChild(new Listcell(""
						+ registro.getPaciente().getTipo_identificacion()));
				listitem.appendChild(new Listcell(""
						+ registro.getPaciente().getNro_identificacion()));
				listitem.appendChild(new Listcell(""
						+ registro.getPaciente().getNombre1() + " "
						+ registro.getPaciente().getNombre2()));
				listitem.appendChild(new Listcell(""
						+ registro.getPaciente().getApellido1() + " "
						+ registro.getPaciente().getApellido2()));
			}

			@Override
			public List<Admision> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				parametros.put("estado", "1");
				parametros.put("order", "fecha_ingreso DESC");
				parametros
						.put("causa_externa_in",
								new String[] {
										ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRANSITO,
										ServiciosDisponiblesUtils.CAUSA_EXTERNA_EVENTO_CATASTROFICO });
				return admisionService
						.listarResultados(parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				seleccionarPaciente(null);
				((BandboxRegistrosMacro) bandbox).setObject(null);
			}
		};
		tbxNro_identificacion.inicializar(null, btnLimpiarNroIdentificacion);
		tbxNro_identificacion.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	protected void seleccionarPaciente(Paciente paciente) {
		if (paciente != null) {
			tbxNro_identificacion.setValue(""
					+ paciente.getNro_identificacion());
			tbxNro_identificacion.setObject(paciente);
			tbxApellido1.setValue("" + paciente.getApellido1());
			tbxApellido2.setValue("" + paciente.getApellido2());
			tbxNombre1.setValue("" + paciente.getNombre1());
			tbxNombre2.setValue("" + paciente.getNombre2());
			Utilidades.setValueFrom(lbxTipoDocumento,
					paciente.getTipo_identificacion());
			dtbxFecha_nacimiento.setValue(paciente.getFecha_nacimiento());
			tbxSexo.setValue(paciente.getSexo().equals("M") ? "Masculino"
					: "Femenino");
			tbxDireccion.setValue(paciente.getDireccion());

			Utilidades.setValueFrom(lbxDepartamento, paciente.getCodigo_dpto());
			Utilidades.listarMunicipios(lbxCodigo_municipio, lbxDepartamento,
					municipiosService);
			Utilidades.setdisable(rdbCondicion_accidentado, false);

			Utilidades.setValueFrom(lbxCodigo_municipio,
					paciente.getCodigo_municipio());
			tbxTelefono.setValue(paciente.getTel_res());
		} else {
			tbxNro_identificacion.setValue("");
			tbxApellido1.setValue("");
			tbxApellido2.setValue("");
			tbxNombre1.setValue("");
			tbxNombre2.setValue("");
			lbxTipoDocumento.setSelectedIndex(0);
			dtbxFecha_nacimiento.setValue(null);
			tbxSexo.setValue("");
			tbxDireccion.setValue("");
			tbxTelefono.setValue("");
			lbxDepartamento.setSelectedIndex(0);
			Utilidades.setdisable(rdbCondicion_accidentado, true);
			Utilidades.setDeseleccionar(rdbCondicion_accidentado);
			if (lbxCodigo_municipio.getItemCount() > 0)
				lbxCodigo_municipio.setSelectedIndex(0);
		}
	}

	private void listarTipoIdentificacion() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tipo", "tipo_id");
		map.put("permitidos5", "_Filtrar");
		List<Elemento> elementos = elementoService.listar(map);
		Utilidades.listarElementoListboxFromType(lbxIx_tipo_documento_medico,
				true, elementos, false);
		Utilidades.listarElementoListboxFromType(lbxTipoDocumento, true,
				elementos, false);
		Utilidades.listarElementoListboxFromType(lbxPropietario_tipoDocumento,
				true, elementos, false);
		Utilidades.listarElementoListboxFromType(lbxConductor_tipodocumento,
				true, elementos, false);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("propietario_tipodocumento || ' ' || propietario_nro_documento || ' ' ||  propietario_apellido1 || ' ' || propietario_apellido2"
				+ " || ' ' || propietario_nombre1 || ' ' || propietario_nombre2");
		listitem.setLabel("Propietario");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("conductor_tipodocumento || ' ' || conductor_nro_documento || ' ' ||  conductor_apellido1 || ' ' || conductor_apellido2"
				+ " || ' ' || conductor_nombre1 || ' ' || conductor_nombre2");
		listitem.setLabel("Conductor");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("pac.tipo_identificacion || ' ' || pac.nro_identificacion || ' ' ||  pac.apellido1 || ' ' || pac.apellido2"
				+ " || ' ' || pac.nombre1 || ' ' || pac.nombre2");
		listitem.setLabel("Victima");
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
		Datebox[] dateboxs = { dtbxFecha_evento_accidentente,
				dtbxFecha_nacimiento, dtbxFecha_radicacion,
				dtbxIx_fecha_egreso, dtbxIx_fecha_ingreso, dtbxRemision_fecha,
				dtbxRemision_fecha_aceptacion, dtbxVigencia_desde_vehiculo,
				dtbxVigencia_hasta_vehiculo };
		for (Datebox datebox : dateboxs) {
			datebox.setValue(null);
		}
		cargaDatosDelaEmpresa();
		Utilidades.setdisable(rdbCondicion_accidentado, true);

		Radiogroup[] RADIO_GROUPS = { rdbAmparo_lugar_donde_recoje_victima,
				rdbAmparo_tipo_transporte, rdbCobroExcedentePoliza,
				rdbCondicion_accidentado, rdbEstadoAseguramiento,
				rdbEventosNaturales, rdbEventosTerroristas,
				rdbIntervencion_autoridad_vehiculo, rdbRemision_tiporeferencia,
				rdbTipo_servicio_vehiculo, rdbZonaDatosDondeOcurrio };
		for (Radiogroup radiogroup : RADIO_GROUPS) {
			Utilidades.setDeseleccionar(radiogroup);
		}
		habilitarImpresion(false);
		furips2Modificar = null;
		dbxGasto_medico_quirurgicos_valor_facturado.setValue(null);
		dbxGasto_medico_quirurgicos_valor_reclamado.setValue(null);
		dbxGasto_transporte_movilizacion_valor_facturado.setValue(null);
		dbxGasto_transporte_movilizacion_valor_reclamado.setValue(null);
		accionNaturalezaEvento();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		try {
			String mensaje = "Los campos marcados con (*) son obligatorios";

			FormularioUtil.validarCamposObligatorios(tbxNro_identificacion,
					tbxDireccion_evento, dtbxFecha_evento_accidentente,
					lbxCodigo_departamento_evento, lbxCodigo_municipio_evento,
					rdbZonaDatosDondeOcurrio, dtbxIx_fecha_ingreso);

			if (tbxNaturaleza_evento.isChecked()) {

				if (radioAsegurado.isChecked() || radioNoAsegurado.isChecked()
						|| radioPoliza.isChecked()) {
					FormularioUtil.validarCamposObligatorios(
							rdbEstadoAseguramiento, tbxMarca_vehiculo,
							tbxPlaca_vehiculo, rdbCobroExcedentePoliza,
							tbxNro_poliza_vehiculo,
							rdbIntervencion_autoridad_vehiculo,
							tbxPropietario_apellido1, tbxPropietario_apellido2,
							tbxPropietario_nombre1,
							lbxPropietario_tipoDocumento,
							tbxPropietario_nro_identificacion,
							tbxPropietario_direccion,
							lbxPropietario_codigo_departamento,
							lbxPropietario_codigo_municipio,
							tbxPropietario_telefono, tbxConductor_apellido1,
							tbxConductor_apellido2, tbxConductor_nombre1,
							lbxConductor_tipodocumento,
							tbxConductor_nro_documento,
							tbxConductor_direccion_res,
							lbxConductor_codigo_departamento,
							lbxConductor_codigo_municipio,
							tbxConductor_telefono);

					Radiogroup[] RADIO_GROUPS = { rdbCobroExcedentePoliza,
							rdbIntervencion_autoridad_vehiculo,
							rdbTipo_servicio_vehiculo };

					for (Radiogroup radiogroup : RADIO_GROUPS) {
						radiogroup.setStyle("background-color:white");
					}

					for (Radiogroup radiogroup : RADIO_GROUPS) {
						if (radiogroup.getSelectedItem() == null) {
							radiogroup.setStyle("background-color:#F6BBBE");
							Clients.scrollIntoView(radiogroup);
							valida = false;
						}
					}

				}

				//
				// if(!radioAsegurado.isChecked()){
				// FormularioUtil.validarCamposObligatorios(tbxCodigo_aseguradora_vehiculo);
				// }
				//
				Radiogroup[] RADIO_GROUPS = { rdbCondicion_accidentado,
						rdbEstadoAseguramiento, rdbZonaDatosDondeOcurrio };

				for (Radiogroup radiogroup : RADIO_GROUPS) {
					radiogroup.setStyle("background-color:white");
				}

				for (Radiogroup radiogroup : RADIO_GROUPS) {
					if (radiogroup.getSelectedItem() == null) {
						radiogroup.setStyle("background-color:#F6BBBE");
						Clients.scrollIntoView(radiogroup);
						valida = false;
					}
				}
			}

			if (cbxOtros.isChecked() && valida) {
				FormularioUtil
						.validarCamposObligatorios(tbxDescripcion_otro_evento);
			}

			if (!valida) {
				MensajesUtil.mensajeAlerta(usuarios.getNombres()
						+ " recuerde que...", mensaje);
			}
		} catch (WrongValueException e) {
			valida = false;
		}
		return valida;
	}

	public void accionOtrosEvento() {
		getFellow("lbDescripcion_otro_evento").setVisible(cbxOtros.isChecked());
		getFellow("cellOtroEvento").setVisible(cbxOtros.isChecked());
		tbxDescripcion_otro_evento.setValue("");
		if (!cbxOtros.isChecked())
			FormularioUtil
					.limpiarComponentesRestricciones(tbxDescripcion_otro_evento);
	}

	public void onSeleccionarCondicionAccidentado(Radio radio) {
		if (radio != null) {
			if (radio.getValue().toString().equals(CONDUCTOR)
					&& tbxNro_identificacion.getObject() != null) {
				Paciente paciente = (Paciente) tbxNro_identificacion
						.getObject();
				tbxConductor_apellido1.setValue(paciente.getApellido1());
				tbxConductor_apellido2.setValue(paciente.getApellido2());
				tbxConductor_nombre1.setValue(paciente.getNombre1());
				tbxConductor_nombre2.setValue(paciente.getNombre2());
				Utilidades.setValueFrom(lbxConductor_tipodocumento,
						paciente.getTipo_identificacion());
				tbxConductor_nro_documento.setValue(paciente
						.getNro_identificacion());
				tbxConductor_direccion_res.setValue(paciente.getDireccion());
				Utilidades.setValueFrom(lbxConductor_codigo_departamento,
						paciente.getCodigo_dpto());
				Utilidades.listarMunicipios(lbxConductor_codigo_municipio,
						lbxConductor_codigo_departamento, municipiosService);
				Utilidades.setValueFrom(lbxConductor_codigo_municipio,
						paciente.getCodigo_municipio());
				tbxConductor_telefono.setValue(paciente.getTel_res());
			} else {
				tbxConductor_apellido1.setValue("");
				tbxConductor_apellido2.setValue("");
				tbxConductor_nombre1.setValue("");
				tbxConductor_nombre2.setValue("");

				lbxConductor_tipodocumento.setSelectedIndex(0);
				tbxConductor_nro_documento.setValue("");
				tbxConductor_direccion_res.setValue("");

				lbxConductor_codigo_departamento.setSelectedIndex(0);
				if (lbxConductor_codigo_municipio.getItemCount() > 0) {
					lbxConductor_codigo_municipio.setSelectedIndex(0);
				}
				tbxConductor_telefono.setValue("");
			}
		}
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("filtrarPaciente", "_Look");
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			furips2Service.setLimit("limit 25 offset 0");

			List<Furips2> lista_datos = furips2Service.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Furips2 furips2 : lista_datos) {
				rowsResultado.appendChild(crearFilas(furips2, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void accionEstadoAseguramiento() {
		Radio radio = rdbEstadoAseguramiento.getSelectedItem();
		if (radio != null) {
			if (VEHICULO_EN_FUGA.equals(radio.getValue())
					|| VEHICULO_FANTASMA.equals(radio.getValue())) {
				FormularioUtil.deshabilitarComponentes(groupConductor, true);
				FormularioUtil.deshabilitarComponentes(groupPropietario, true);
				FormularioUtil.deshabilitarComponentes(groupDatiosVehiculo,
						true);
				radioAsegurado.setDisabled(false);
				radioNoAsegurado.setDisabled(false);
				radioFantasma.setDisabled(false);
				radioPoliza.setDisabled(false);
				radioFuga.setDisabled(false);
			} else if (VEHICULO_ASEGURADO.equals(radio.getValue())) {// Habilitamos
																		// la
																		// carga
																		// de
																		// los
																		// campos
				FormularioUtil.deshabilitarComponentes(groupConductor, false);
				FormularioUtil.deshabilitarComponentes(groupPropietario, false);
				FormularioUtil.deshabilitarComponentes(groupDatiosVehiculo,
						false);
				tbxCodigo_aseguradora_vehiculo.setDisabled(true);

			} else {// Habilitamos la carga de los campos
				FormularioUtil.deshabilitarComponentes(groupConductor, false);
				FormularioUtil.deshabilitarComponentes(groupPropietario, false);
				FormularioUtil.deshabilitarComponentes(groupDatiosVehiculo,
						false);
			}
		} else {// Habilitamos la carga de los campos
			FormularioUtil.deshabilitarComponentes(groupConductor, false);
			FormularioUtil.deshabilitarComponentes(groupPropietario, false);
			FormularioUtil.deshabilitarComponentes(groupDatiosVehiculo, false);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Furips2 furips2 = (Furips2) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		// Cargamos las victimas
		StringBuilder victima_evento = new StringBuilder();
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa("" + furips2.getCodigo_empresa());
		paciente.setCodigo_sucursal("" + furips2.getCodigo_sucursal());
		paciente.setNro_identificacion("" + furips2.getNro_identificacion());
		paciente = pacienteService
				.consultar(paciente);

		if (paciente != null) {
			victima_evento.append("(" + paciente.getTipo_identificacion()
					+ ") ");
			victima_evento.append("" + paciente.getNro_identificacion());
			victima_evento.append(" - " + paciente.getNombreCompleto());
		}

		// Cargamos los datos del propietario
		StringBuilder propietario = new StringBuilder();
		propietario.append("(" + furips2.getPropietario_tipodocumento() + ") ");
		propietario.append("" + furips2.getPropietario_nro_documento());
		propietario.append(" - " + furips2.getPropietario_apellido1());
		propietario.append(" " + furips2.getPropietario_apellido2());
		propietario.append(" " + furips2.getPropietario_nombre1());
		propietario.append(" " + furips2.getPropietario_nombre2());

		// Cargamos el conductor
		StringBuilder conductor = new StringBuilder();
		conductor.append("(" + furips2.getConductor_tipodocumento() + ") ");
		conductor.append("" + furips2.getConductor_nro_documento());
		conductor.append(" - " + furips2.getConductor_apellido1());
		conductor.append(" - " + furips2.getConductor_apellido2());
		conductor.append(" - " + furips2.getConductor_nombre1());
		conductor.append(" - " + furips2.getConductor_nombre2());

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label("" + victima_evento));
		fila.appendChild(new Label("" + propietario));
		fila.appendChild(new Label("" + conductor));
		fila.appendChild(new Label("" + furips2.getPlaca_vehiculo()));
		fila.appendChild(new Label("" + furips2.getDireccion_evento()));
		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(furips2);
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(furips2);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		if (!furips2.getNro_factura().isEmpty()) {
			img = new Image();
			img.setSrc("/images/print_ico.gif");
			img.setTooltiptext("Imprimir");
			img.setStyle("cursor: pointer");
			img.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					imprimir(furips2);
				}
			});
			hbox.appendChild(space);
			hbox.appendChild(img);
		}

		fila.appendChild(hbox);

		return fila;
	}

	private String getValor(Radiogroup radiogroup) {
		Radio radio = radiogroup.getSelectedItem();
		if (radio != null) {
			return radio.getValue().toString();
		}
		return "";
	}

	private String getValue(Listitem listitem) {
		return listitem != null ? listitem.getValue().toString() : "";
	}

	public Furips2 getBean() {
		Furips2 furips2 = new Furips2();
		furips2.setCodigo_empresa(empresa.getCodigo_empresa());
		furips2.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		// furips2.setCodigo_currips();
		furips2.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		furips2.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		furips2.setCreacion_user(usuarios.getCodigo().toString());
		furips2.setUltimo_user(usuarios.getCodigo().toString());

		if (dtbxFecha_radicacion.getValue() != null)
			furips2.setFecha_radicacion(new Timestamp(dtbxFecha_radicacion
					.getValue().getTime()));

		furips2.setRg(cbxRg.isChecked() ? "S" : "N");
		furips2.setNro_radicacion(tbxNro_radicacion.getValue());
		furips2.setNro_radicacion_anterior(tbxNro_radicacion_anterior
				.getValue());
		furips2.setNro_factura(tbxNro_factura.getValue());
		furips2.setNro_identificacion(tbxNro_identificacion.getValue());
		furips2.setCondicion_accidentado(getValor(rdbCondicion_accidentado));
		furips2.setNaturaleza_evento(tbxNaturaleza_evento.isChecked() ? "S"
				: "N");
		furips2.setEventos_naturales(getValor(rdbEventosNaturales));
		furips2.setEventos_terroristas(getValor(rdbEventosTerroristas));
		furips2.setOtro_evento(cbxOtros.isChecked() ? "S" : "N");
		furips2.setDescripcion_otro_evento(tbxDescripcion_otro_evento
				.getValue());
		furips2.setDireccion_evento(tbxDireccion_evento.getValue());

		if (dtbxFecha_evento_accidentente.getValue() != null) {
			furips2.setFecha_evento_accidentente(new Timestamp(
					dtbxFecha_evento_accidentente.getValue().getTime()));
			furips2.setHora_evento(""
					+ (new SimpleDateFormat("hh:mm a")
							.format(dtbxFecha_evento_accidentente.getValue())));
		}
		furips2.setCodigo_departamento_evento(getValue(lbxCodigo_departamento_evento
				.getSelectedItem()));
		furips2.setCodigo_municipio_evento(getValue(lbxCodigo_municipio_evento
				.getSelectedItem()));
		furips2.setDescripcion_breve_evento(tbxDescripcion_breve_evento
				.getValue());
		furips2.setEstado_aseguramiento(getValor(rdbEstadoAseguramiento));
		furips2.setMarca_vehiculo(tbxMarca_vehiculo.getValue());
		furips2.setPlaca_vehiculo(tbxPlaca_vehiculo.getValue());
		furips2.setTipo_servicio_vehiculo(getValor(rdbTipo_servicio_vehiculo));
		furips2.setCodigo_aseguradora_vehiculo(tbxCodigo_aseguradora_vehiculo
				.getValue());
		furips2.setNro_poliza_vehiculo(tbxNro_poliza_vehiculo.getValue());
		furips2.setIntervencion_autoridad_vehiculo(getValor(rdbIntervencion_autoridad_vehiculo));

		if (dtbxVigencia_desde_vehiculo.getValue() != null)
			furips2.setVigencia_desde_vehiculo(new Timestamp(
					dtbxVigencia_desde_vehiculo.getValue().getTime()));

		if (dtbxVigencia_hasta_vehiculo.getValue() != null)
			furips2.setVigencia_hasta_vehiculo(new Timestamp(
					dtbxVigencia_hasta_vehiculo.getValue().getTime()));
		furips2.setCobro_excedente_poliza_vehiculo(getValor(rdbCobroExcedentePoliza));
		furips2.setPropietario_apellido1(tbxPropietario_apellido1.getValue());
		furips2.setPropietario_apellido2(tbxPropietario_apellido2.getValue());
		furips2.setPropietario_nombre1(tbxPropietario_nombre1.getValue());
		furips2.setPropietario_nombre2(tbxPropietario_nombre2.getValue());
		furips2.setPropietario_tipodocumento(lbxPropietario_tipoDocumento
				.getSelectedItem().getValue().toString());
		furips2.setPropietario_nro_documento(tbxPropietario_nro_identificacion
				.getValue());
		furips2.setPropietario_direccion_res(tbxPropietario_direccion
				.getValue());
		furips2.setPropietario_codigo_departamento(lbxPropietario_codigo_departamento
				.getSelectedItem().getValue().toString());
		furips2.setPropietario_codigo_municipio(getValue(lbxPropietario_codigo_municipio
				.getSelectedItem()));
		furips2.setPropietario_telefono(tbxPropietario_telefono.getValue());
		furips2.setPropietario_total_folios((ibxPropietario_total_folios
				.getValue() != null ? ibxPropietario_total_folios.getValue()
				: 0));
		furips2.setConductor_apellido1(tbxConductor_apellido1.getValue());
		furips2.setConductor_apellido2(tbxConductor_apellido2.getValue());
		furips2.setConductor_nombre1(tbxConductor_nombre1.getValue());
		furips2.setConductor_nombre2(tbxConductor_nombre2.getValue());
		furips2.setConductor_tipodocumento(lbxConductor_tipodocumento
				.getSelectedItem().getValue().toString());
		furips2.setConductor_nro_documento(tbxConductor_nro_documento
				.getValue());
		furips2.setConductor_direccion_res(tbxConductor_direccion_res
				.getValue());
		furips2.setConductor_codigo_departamento(lbxConductor_codigo_departamento
				.getSelectedItem().getValue().toString());
		furips2.setConductor_codigo_municipio(getValue(lbxConductor_codigo_municipio
				.getSelectedItem()));
		furips2.setConductor_telefono(tbxConductor_telefono.getValue());
		furips2.setRemision_tiporeferencia(getValor(rdbAmparo_tipo_transporte));

		if (dtbxRemision_fecha.getValue() != null)
			furips2.setRemision_fecha(new Timestamp(dtbxRemision_fecha
					.getValue().getTime()));
		furips2.setRemision_prestador_remite(tbxRemision_prestador_remite
				.getValue());
		furips2.setRemision_codigo_inscripcion_remite(tbxRemision_codigo_inscripcion_remite
				.getValue());
		furips2.setRemision_profesional(tbxRemision_profesional.getValue());
		furips2.setRemision_cargo_remite(tbxRemision_cargo_remite.getValue());

		if (dtbxRemision_fecha_aceptacion.getValue() != null)
			furips2.setRemision_fecha_aceptacion(new Timestamp(
					dtbxRemision_fecha_aceptacion.getValue().getTime()));

		furips2.setRemision_prestador_recibe(tbxRemision_prestador_recibe
				.getValue());
		furips2.setRemision_codigo_inscripcion_recibe(tbxRemision_codigo_inscripcion_recibe
				.getValue());
		furips2.setRemision_personal_recibe(tbxRemision_personal_recibe
				.getValue());
		furips2.setRemision_cargo_recibe(tbxRemision_cargo_recibe.getValue());
		furips2.setAmparo_placa_nro(tbxAmparo_placa_nro.getValue());
		furips2.setAmparo_transporto_victima_desde(tbxAmparo_transporto_victima_desde
				.getValue());
		furips2.setAmparo_transporto_victima_hasta(tbxAmparo_transporto_victima_hasta
				.getValue());
		furips2.setAmparo_tipo_transporte(getValor(rdbAmparo_tipo_transporte));
		furips2.setAmparo_lugar_donde_recoje_victima(getValor(rdbAmparo_lugar_donde_recoje_victima));

		if (dtbxIx_fecha_ingreso.getValue() != null)
			furips2.setIx_fecha_ingreso(new Timestamp(dtbxIx_fecha_ingreso
					.getValue().getTime()));

		if (dtbxIx_fecha_egreso.getValue() != null)
			furips2.setIx_fecha_egreso(new Timestamp(dtbxIx_fecha_egreso
					.getValue().getTime()));

		furips2.setIx_codigo_diagnostico_ingreso(getValorDx(tbxIx_codigo_diagnostico_ingreso));
		furips2.setIx_codigo_diagnostico_egreso(getValorDx(tbxIx_codigo_diagnostico_egreso));
		furips2.setIx_otro_codigo_diagnostico_ingreso(getValorDx(tbxIx_otro_codigo_diagnostico_ingreso));
		furips2.setIx_otro_codigo_diagnostico_egreso(getValorDx(tbxIx_otro_codigo_diagnostico_egreso));
		furips2.setIx_otro_codigo_diagnostico_ingreso2(getValorDx(tbxIx_otro_codigo_diagnostico_ingreso2));
		furips2.setIx_otro_codigo_diagnostico_egreso2(getValorDx(tbxIx_otro_codigo_diagnostico_egreso2));

		furips2.setIx_nro_documento_medico(tbxIx_nro_documento_medico
				.getValue());
		furips2.setGasto_medico_quirurgicos_valor_facturado((dbxGasto_medico_quirurgicos_valor_facturado
				.getValue() != null ? dbxGasto_medico_quirurgicos_valor_facturado
				.getValue() : 0.00));
		furips2.setGasto_medico_quirurgicos_valor_reclamado(dbxGasto_medico_quirurgicos_valor_reclamado
				.getValue() != null ? dbxGasto_medico_quirurgicos_valor_reclamado
				.getValue() : 0);
		furips2.setGasto_transporte_movilizacion_valor_facturado(dbxGasto_transporte_movilizacion_valor_facturado
				.getValue() != null ? dbxGasto_transporte_movilizacion_valor_facturado
				.getValue() : 0);
		furips2.setGasto_medico_quirurgicos_valor_reclamado(dbxGasto_transporte_movilizacion_valor_reclamado
				.getValue() != null ? dbxGasto_transporte_movilizacion_valor_reclamado
				.getValue() : 0);

		furips2.setZona_ocurrecia(getValor(rdbZonaDatosDondeOcurrio));
		return furips2;
	}

	private String getValorDx(BandboxRegistrosMacro bandboxRegistrosMacro) {
		Cie cie = bandboxRegistrosMacro.getRegistroSeleccionado();
		if (cie != null) {
			return cie.getCodigo();
		} else {
			return "";
		}
	}

	public void guardarTempFurrips() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				Furips2 furips2 = getBean();
				if (furips2Modificar != null) {
					furips2.setCodigo_currips(furips2Modificar
							.getCodigo_currips());
				}
				if (onAccionFurips != null) {
					onAccionFurips.onAgregar(furips2);
				}
				detach();
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void cancelarFurips() {
		Messagebox
				.show("¿Esta seguro que deseas cancelar el furips si lo cancelas, perderas los datos ingresados?",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									if (onAccionFurips != null)
										onAccionFurips.cerrar();
									onClose();
								}
							}
						});
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //
				Furips2 furips2 = getBean();
				Admision admision = tbxNro_identificacion
						.getRegistroSeleccionado();
				furips2.setNro_ingreso(admision.getNro_ingreso());
				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					furips2Modificar = furips2Service.crear(furips2);
					accionForm(true, "registrar");
				} else {
					furips2.setCodigo_currips(furips2Modificar
							.getCodigo_currips());
					int result = furips2Service.actualizar(furips2);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				// habilitarImpresion(true);
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Furips2 furips2 = (Furips2) obj;
		furips2Modificar = furips2;
		try {
			dtbxFecha_radicacion.setValue(furips2.getFecha_radicacion());
			cbxRg.setChecked(furips2.getRg().equals("S"));
			tbxNro_radicacion.setValue(furips2.getNro_radicacion());
			tbxNro_radicacion_anterior.setValue(furips2
					.getNro_radicacion_anterior());
			tbxNro_factura.setValue(furips2.getNro_factura());
			tbxNro_identificacion.setValue(furips2.getNro_identificacion());

			Utilidades.setValueFrom(rdbCondicion_accidentado,
					furips2.getCondicion_accidentado());
			tbxNaturaleza_evento.setChecked(furips2.getNaturaleza_evento()
					.equals("S"));
			Utilidades.setValueFrom(rdbEstadoAseguramiento,
					furips2.getEstado_aseguramiento());
			accionNaturalezaEvento();

			Utilidades.setValueFrom(rdbEventosNaturales,
					furips2.getEventos_naturales());
			Utilidades.setValueFrom(rdbEventosTerroristas,
					furips2.getEventos_terroristas());
			cbxOtros.setChecked(furips2.getOtro_evento().equals("S"));
			accionOtrosEvento();
			tbxDescripcion_otro_evento.setValue(furips2
					.getDescripcion_otro_evento());
			tbxDireccion_evento.setValue(furips2.getDireccion_evento());
			dtbxFecha_evento_accidentente.setValue(furips2
					.getFecha_evento_accidentente());

			Utilidades.setValueFrom(lbxCodigo_departamento_evento,
					furips2.getCodigo_departamento_evento());
			Utilidades.listarMunicipios(lbxCodigo_municipio_evento,
					lbxCodigo_departamento_evento, municipiosService);
			Utilidades.setValueFrom(lbxCodigo_municipio_evento,
					furips2.getCodigo_municipio_evento());
			tbxDescripcion_breve_evento.setValue(furips2
					.getDescripcion_breve_evento());

			tbxMarca_vehiculo.setValue(furips2.getMarca_vehiculo());
			tbxPlaca_vehiculo.setValue(furips2.getPlaca_vehiculo());

			Utilidades.setValueFrom(rdbTipo_servicio_vehiculo,
					furips2.getTipo_servicio_vehiculo());
			tbxCodigo_aseguradora_vehiculo.setValue(furips2
					.getCodigo_aseguradora_vehiculo());
			tbxNro_poliza_vehiculo.setValue(furips2.getNro_poliza_vehiculo());

			Utilidades.setValueFrom(rdbIntervencion_autoridad_vehiculo,
					furips2.getIntervencion_autoridad_vehiculo());
			dtbxVigencia_desde_vehiculo.setValue(furips2
					.getVigencia_desde_vehiculo());
			dtbxVigencia_hasta_vehiculo.setValue(furips2
					.getVigencia_hasta_vehiculo());

			Utilidades.setValueFrom(rdbCobroExcedentePoliza,
					furips2.getCobro_excedente_poliza_vehiculo());
			tbxPropietario_apellido1.setValue(furips2
					.getPropietario_apellido1());
			tbxPropietario_apellido2.setValue(furips2
					.getPropietario_apellido2());
			tbxPropietario_nombre1.setValue(furips2.getPropietario_nombre1());
			tbxPropietario_nombre2.setValue(furips2.getPropietario_nombre2());

			Utilidades.setValueFrom(lbxPropietario_tipoDocumento,
					furips2.getPropietario_tipodocumento());
			tbxPropietario_nro_identificacion.setValue(furips2
					.getPropietario_nro_documento());
			tbxPropietario_direccion.setValue(furips2
					.getPropietario_direccion_res());

			Utilidades.setValueFrom(lbxPropietario_codigo_departamento,
					furips2.getPropietario_codigo_departamento());
			Utilidades.listarMunicipios(lbxPropietario_codigo_municipio,
					lbxPropietario_codigo_departamento, municipiosService);
			Utilidades.setValueFrom(lbxPropietario_codigo_municipio,
					furips2.getPropietario_codigo_municipio());

			tbxPropietario_telefono.setValue(furips2.getPropietario_telefono());
			ibxPropietario_total_folios.setValue(furips2
					.getPropietario_total_folios());
			tbxConductor_apellido1.setValue(furips2.getConductor_apellido1());
			tbxConductor_apellido2.setValue(furips2.getConductor_apellido2());
			tbxConductor_nombre1.setValue(furips2.getConductor_nombre1());
			tbxConductor_nombre2.setValue(furips2.getConductor_nombre2());

			Utilidades.setValueFrom(lbxConductor_tipodocumento,
					furips2.getConductor_tipodocumento());
			tbxConductor_nro_documento.setValue(furips2
					.getConductor_nro_documento());
			tbxConductor_direccion_res.setValue(furips2
					.getConductor_direccion_res());

			Utilidades.setValueFrom(lbxConductor_codigo_departamento,
					furips2.getConductor_codigo_departamento());
			Utilidades.listarMunicipios(lbxConductor_codigo_municipio,
					lbxConductor_codigo_departamento, municipiosService);
			Utilidades.setValueFrom(lbxConductor_codigo_municipio,
					furips2.getConductor_codigo_municipio());

			tbxConductor_telefono.setValue(furips2.getConductor_telefono());

			Utilidades.setValueFrom(rdbRemision_tiporeferencia,
					furips2.getRemision_tiporeferencia());
			dtbxRemision_fecha.setValue(furips2.getRemision_fecha());
			tbxRemision_prestador_remite.setValue(furips2
					.getRemision_prestador_remite());
			tbxRemision_codigo_inscripcion_remite.setValue(furips2
					.getRemision_codigo_inscripcion_remite());
			tbxRemision_profesional.setValue(furips2.getRemision_profesional());
			tbxRemision_cargo_remite.setValue(furips2
					.getRemision_cargo_remite());
			dtbxRemision_fecha_aceptacion.setValue(furips2
					.getRemision_fecha_aceptacion());
			tbxRemision_prestador_recibe.setValue(furips2
					.getRemision_prestador_recibe());
			tbxRemision_codigo_inscripcion_recibe.setValue(furips2
					.getRemision_codigo_inscripcion_recibe());
			tbxRemision_personal_recibe.setValue(furips2
					.getRemision_personal_recibe());
			tbxRemision_cargo_recibe.setValue(furips2
					.getRemision_cargo_recibe());
			tbxAmparo_placa_nro.setValue(furips2.getAmparo_placa_nro());
			tbxAmparo_transporto_victima_desde.setValue(furips2
					.getAmparo_transporto_victima_desde());
			tbxAmparo_transporto_victima_hasta.setValue(furips2
					.getAmparo_transporto_victima_hasta());

			Utilidades.setValueFrom(rdbAmparo_tipo_transporte,
					furips2.getAmparo_tipo_transporte());
			Utilidades.setValueFrom(rdbAmparo_lugar_donde_recoje_victima,
					furips2.getAmparo_lugar_donde_recoje_victima());
			dtbxIx_fecha_ingreso.setValue(furips2.getIx_fecha_ingreso());
			dtbxIx_fecha_egreso.setValue(furips2.getIx_fecha_egreso());

			cargarDXBandBox(tbxIx_codigo_diagnostico_ingreso,
					furips2.getIx_codigo_diagnostico_ingreso());
			cargarDXBandBox(tbxIx_codigo_diagnostico_egreso,
					furips2.getIx_codigo_diagnostico_egreso());
			cargarDXBandBox(tbxIx_otro_codigo_diagnostico_ingreso,
					furips2.getIx_otro_codigo_diagnostico_ingreso());
			cargarDXBandBox(tbxIx_otro_codigo_diagnostico_egreso,
					furips2.getIx_otro_codigo_diagnostico_egreso());
			cargarDXBandBox(tbxIx_otro_codigo_diagnostico_ingreso2,
					furips2.getIx_otro_codigo_diagnostico_ingreso2());
			cargarDXBandBox(tbxIx_otro_codigo_diagnostico_egreso2,
					furips2.getIx_otro_codigo_diagnostico_egreso2());

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(furips2.getCodigo_empresa());
			prestadores.setCodigo_sucursal(furips2.getCodigo_sucursal());
			prestadores.setNro_identificacion(furips2
					.getIx_nro_documento_medico());
			prestadores = prestadoresService.consultar(prestadores);

			if (prestadores != null) {
				tbxIx_nro_documento_medico.seleccionarRegistro(prestadores,
						prestadores.getNro_identificacion(), "");
				cargarDatosMedico(prestadores);
			}

			dbxGasto_medico_quirurgicos_valor_facturado.setValue(furips2
					.getGasto_medico_quirurgicos_valor_facturado());
			dbxGasto_medico_quirurgicos_valor_reclamado.setValue(furips2
					.getGasto_medico_quirurgicos_valor_reclamado());
			dbxGasto_transporte_movilizacion_valor_facturado.setValue(furips2
					.getGasto_transporte_movilizacion_valor_facturado());
			dbxGasto_transporte_movilizacion_valor_reclamado.setValue(furips2
					.getGasto_transporte_movilizacion_valor_reclamado());

			Utilidades.setValueFrom(rdbZonaDatosDondeOcurrio,
					furips2.getZona_ocurrecia());

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(furips2.getCodigo_empresa());
			paciente.setCodigo_sucursal(furips2.getCodigo_sucursal());
			paciente.setNro_identificacion(furips2.getNro_identificacion());
			paciente = pacienteService
					.consultar(paciente);

			Admision admision = new Admision();
			admision.setCodigo_empresa(furips2.getCodigo_empresa());
			admision.setCodigo_sucursal(furips2.getCodigo_sucursal());
			admision.setNro_identificacion(furips2.getNro_identificacion());
			admision.setNro_ingreso(furips2.getNro_ingreso());
			admision = admisionService
					.consultar(admision);

			if (admision != null) {
				tbxNro_identificacion.seleccionarRegistro(admision,
						admision.getNro_identificacion(), "");
			}
			Radio radio = rdbEstadoAseguramiento.getSelectedItem();
			if (VEHICULO_ASEGURADO.equals(radio.getValue())) {
				// log.info("furips ingreso ==> " + furips2.getNro_ingreso());
				// log.info("admision antes ===> " + admision);
				if (admision != null) {
					tbxCodigo_aseguradora_vehiculo.setValue(admision
							.getCodigo_administradora());
				}

			}

			if (paciente != null) {
				seleccionarPaciente(paciente);
			}

			habilitarImpresion(furips2.getNro_factura() != null
					&& !furips2.getNro_factura().trim().isEmpty());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void cargarDXBandBox(BandboxRegistrosMacro bandboxRegistrosMacro,
			String codigo_dx) {
		Cie cie = new Cie();
		cie.setCodigo(codigo_dx);
		cie = generalExtraService.consultar(cie);
		if (cie != null) {
			bandboxRegistrosMacro.seleccionarRegistro(cie, cie.getCodigo()
					+ " - " + cie.getNombre(), "");
		}
	}

	private void cargarDatosMedico(Prestadores prestadores) {
		String[] apellidos = Utilidades.separarApellido(prestadores
				.getApellidos());
		String[] nombres = Utilidades.separarNombre(prestadores.getNombres());

		tbxIx_primer_apellido_medico.setValue(apellidos[0]);
		tbxIx_segundo_apellido_medico.setValue(apellidos[1]);
		tbxIx_primer_nombre_medico.setValue(nombres[0]);
		tbxIx_segundo_nombre_medico.setValue(nombres[1]);

		Utilidades.setValueFrom(lbxIx_tipo_documento_medico,
				prestadores.getTipo_identificacion());
		tbxIx_nro_registro_medico.setValue(prestadores.getRegistro_medico());
	}

	public void eliminarDatos(Object obj) throws Exception {
		Furips2 furips2 = (Furips2) obj;
		try {
			int result = furips2Service.eliminar(furips2);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Informacion", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	public void imprimir() throws Exception {
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Furips");
		paramRequest.put("empresa", furips2Modificar.getCodigo_empresa());
		paramRequest.put("sucursal", furips2Modificar.getCodigo_sucursal());
		paramRequest
				.put("codigo_currips", furips2Modificar.getCodigo_currips());
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void imprimir(Furips2 furips2) throws Exception {
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Furips");
		paramRequest.put("empresa", furips2.getCodigo_empresa());
		paramRequest.put("sucursal", furips2.getCodigo_sucursal());
		paramRequest.put("codigo_currips", furips2.getCodigo_currips());
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
}