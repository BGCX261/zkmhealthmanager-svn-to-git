/*
 * his_triageAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Sigvitales;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.His_triageService;

import com.framework.util.Util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.INivelesTriage;
//import bsh.util.Util;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.GlasgowMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.SignosVitalesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.softcomputo.composer.constantes.IParametrosSesion;

public class His_triageAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private His_triageService his_triageService;

	private Admision admision;
	private Opciones_via_ingreso opciones_via_ingreso;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Groupbox groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Bandbox bandboxPrestador;
	@View
	private Textbox tbxNomPrestador;

	// Acompañante
	@View
	private Textbox tbxAcompanante;
	@View
	private Doublebox dbxIdentificacion_acompanante;
	@View
	private Listbox lbxRelacion;
	@View
	private Intbox ibxEdad_acompanante;
	@View
	private Listbox lbxEscolaridad_acompanante;
	@View
	private Doublebox dbxTelefono_acompanante;

	@View
	private Textbox tbxMotivo;
	@View
	private Textbox tbxObservacion;
	@View
	private Radiogroup rdbAdmitido;
	@View
	private Listbox lbxAdmitido_si;
	@View
	private Textbox tbxAdmitido_no;

	private String nivel_triage;

	@View
	private Button btnMostrar_triage_i;
	@View
	private Button btnMostrar_triage_ii;
	@View
	private Button btnMostrar_triage_iii;
	@View
	private Button btnMostrar_triage_iv;

	@View
	private Label lbAdmitido_si;
	@View
	private Row rowAdmitido_no;
	@View
	private Label lbAdmitido_no;

	@View
	private Checkbox chkAplica_acompaniante;

	@View(isMacro = true)
	private SignosVitalesMacro mcSignosVitales;

	@View
	private Groupbox gbxSigVitales;
	@View
	private Separator sepAntesSigVitales;

	private final String[] idsExcluyentes = { "tbxAccion", "lbxRelacion",
			"lbxEscolaridad_acompanante", "chkAplica_acompaniante",
			"btnLimpiar_prestador", "tbxValue", "lbxParameter",
			"toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar",
			"lbxAdmitido_si", "formReceta", "gridOrdenes_servicio" };

	// Botones
	@View
	private Toolbarbutton btnCancelar;
	@View
	private Toolbarbutton btGuardar;

	// Macros
	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View(isMacro = true)
	private GlasgowMacro macroGlasgow;
	@View
	private Groupbox gbxGlasgow;

	@View
	private Button btnMostrar_escala_glasgow;

	@View
	private Toolbarbutton btImprimir;

	@Override
	public void init() throws Exception {
		listarCombos();
		if (parametros_empresa != null) {
			if (parametros_empresa.getHabilitar_triage4().equals("S")) {
				btnMostrar_triage_iv.setVisible(true);
			} else {
				btnMostrar_triage_iv.setVisible(false);
			}
		}
		inicializarSignosVitales();
	}

	/**
	 * Inicializacion centralizada de los signos vitales
	 * */
	private void inicializarSignosVitales() {
		mcSignosVitales.setZkWindow(this);
		mcSignosVitales.getLabelCreatinina().setVisible(false);
		mcSignosVitales.getDoubleboxCreatinina().setVisible(false);
		mcSignosVitales.getLabelTFG().setVisible(false);
		mcSignosVitales.getDoubleboxTFG().setVisible(false);

		// Verificamos si se va a trabajar
		// con signos vitales en el triage
		if (getParametros_empresa().getCargar_signos_vitales_triage().equals(
				"S")) {
			cargarSignosVitalesEnfermera();
		} else {
			gbxSigVitales.setVisible(false);
			sepAntesSigVitales.detach();
		}
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		rol_usuario = (String) request.getSession().getAttribute(
				IParametrosSesion.PARAM_ROL_USUARIO);
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
		}
	}

	public void listarCombos() {
		listarParameter();
		Utilidades
				.listarElementoListbox(lbxRelacion, true, getServiceLocator());
		Utilidades.listarNivel_educativo(lbxEscolaridad_acompanante, true,
				getServiceLocator());

		listarListboxAdmitido();

	}

	public void listarListboxAdmitido() {
		Integer edad = Integer.parseInt(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		String sexo = admision.getPaciente().getSexo();

		// log.info("edad" + edad);
		// log.info("sexo" + sexo);
		lbxAdmitido_si.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("-- seleccione --");
		lbxAdmitido_si.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("1");
		listitem.setLabel("Urgencia General");
		lbxAdmitido_si.appendChild(listitem);

		if (sexo.equals("F") && edad >= 10 && edad <= 49) {
			listitem = new Listitem();
			listitem.setValue("2");
			listitem.setLabel("Urgencia Obstétrica");
			lbxAdmitido_si.appendChild(listitem);

		}

		// if (edad < 10) {
		// listitem = new Listitem();
		// listitem.setValue("3");
		// listitem.setLabel("Urgencia Pediátrica");
		// lbxAdmitido_si.appendChild(listitem);
		//
		// }

		if (lbxAdmitido_si.getItemCount() > 0) {
			if (lbxAdmitido_si.getItemCount() == 2) {
				lbxAdmitido_si.setSelectedIndex(1);
			} else {
				lbxAdmitido_si.setSelectedIndex(0);
			}

		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		// listitem.setValue("codigo_historia");
		// listitem.setLabel("Codigo historia");
		// lbxParameter.appendChild(listitem);
		//
		// listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		tbxAccion.setValue(accion);

		if (!accion.equalsIgnoreCase("registrar")) {
			btImprimir.setVisible(true);
			if (!accion.equalsIgnoreCase("mostrar")) {
				buscarDatos();
				lbxRelacion.setDisabled(false);
				lbxEscolaridad_acompanante.setDisabled(false);
			}
			btGuardar.setVisible(false);
		} else {
			btGuardar.setVisible(true);
			btImprimir.setVisible(false);
			limpiarDatos();
			FormularioUtil.cargarRadiogroupsDefecto(this);
			if (admision != null) {
				infoPacientes.setFecha_inicio(new Date());
				cargarInformacion_paciente();
			}

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(codigo_empresa);
			prestadores.setCodigo_sucursal(codigo_sucursal);
			prestadores.setNro_identificacion(usuarios.getCodigo());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);

			bandboxPrestador.setValue(prestadores != null ? prestadores
					.getNro_identificacion() : "000000000");
			tbxNomPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos()
					: "MEDICO POR DEFECTO"));

		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		cargarPrestador();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		infoPacientes.validarInformacionPaciente();

		boolean valida = true;
		String mensaje = "";

		// FormularioUtil.limpiarComponentesRestricciones(this);
		FormularioUtil.limpiarComponentesRestricciones(tbxAcompanante);
		FormularioUtil
				.limpiarComponentesRestricciones(dbxIdentificacion_acompanante);
		FormularioUtil.limpiarComponentesRestricciones(dbxTelefono_acompanante);
		FormularioUtil.limpiarComponentesRestricciones(lbxRelacion);
		FormularioUtil.limpiarComponentesRestricciones(ibxEdad_acompanante);
		FormularioUtil
				.limpiarComponentesRestricciones(lbxEscolaridad_acompanante);

		if (chkAplica_acompaniante.isChecked()) {
			FormularioUtil.validarCamposObligatorios(tbxMotivo, tbxAcompanante,
					dbxIdentificacion_acompanante, dbxTelefono_acompanante,
					lbxRelacion, ibxEdad_acompanante,
					lbxEscolaridad_acompanante, tbxObservacion);
			FormularioUtil.validarCaracteresMinimos(5, tbxMotivo,
					tbxAcompanante);
			FormularioUtil.validarCaracteresMinimos(6,
					dbxIdentificacion_acompanante);
			FormularioUtil.validarCaracteresMinimos(7, dbxTelefono_acompanante);
		} else {
			FormularioUtil.validarCamposObligatorios(tbxMotivo, tbxObservacion);
			FormularioUtil.validarCaracteresMinimos(5, tbxMotivo);
		}

		if (rdbAdmitido.getSelectedItem() != null) {
			if (rdbAdmitido.getSelectedItem().getValue().toString().equals("S")) {
				if (lbxAdmitido_si.getSelectedIndex() == 0) {
					lbxAdmitido_si
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					valida = false;
				}
			} else {
				if (tbxAdmitido_no.getText().trim().equals("")) {
					tbxAdmitido_no
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					valida = false;
				}
			}
		} else {
			valida = false;
		}

		if (valida) {
			if (nivel_triage != null && !nivel_triage.isEmpty()) {
				mensaje = "Debe seleccionar el nivel de triage";
				Clients.scrollIntoView(tbxObservacion);
			}
		}

		if (!valida) {

			if (rdbAdmitido.getSelectedItem() != null) {
				mensaje = "Los campos marcados con (*) son obligatorios";
			} else {
				mensaje = "Se debe seleccionar una opción del campo Admitido ";
			}

			Clients.scrollIntoView(rdbAdmitido);

			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}

			if (parameter.equalsIgnoreCase("fecha_inicial")) {
				parameters.put("fecha_string", value);
			} else {
				parameters.put("parameter", parameter);
				parameters.put("value", "%" + value + "%");
			}

			his_triageService.setLimit("limit 25 offset 0");

			List<His_triage> lista_datos = his_triageService.listar(parameters);
			rowsResultado.getChildren().clear();

			for (His_triage his_triage : lista_datos) {
				rowsResultado.appendChild(crearFilas(his_triage, this));
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

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final His_triage his_triage = (His_triage) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(his_triage.getCodigo_historia() + ""));
		fila.appendChild(new Label(his_triage.getIdentificacion() + ""));
		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(his_triage.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label("Triage (Urgencias)"));

		Hlayout hlayout = new Hlayout();

		Toolbarbutton btn_mostrar = new Toolbarbutton();
		btn_mostrar.setImage("/images/mostrar_info.png");
		btn_mostrar.setTooltiptext("Mostrar Triage (Urgencias)");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.setLabel("Mostrar");
		btn_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(his_triage);
					}
				});
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);

		return fila;
	}

	// Metodo para guardar la informaciÃƒÂ³n //
	public void guardarDatos() throws Exception {
		try {
			// log.info("---"+"---"+"---"+"---");
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				His_triage his_triage = new His_triage();
				his_triage.setCodigo_empresa(empresa.getCodigo_empresa());
				his_triage.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				his_triage.setCodigo_historia(infoPacientes
						.getCodigo_historia_aux());
				his_triage.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				his_triage.setFecha_inicial(new Timestamp(infoPacientes
						.getFecha_inicial().getTime()));
				his_triage.setCodigo_prestador(bandboxPrestador.getValue());

				// Acompañante
				his_triage.setAcompanante(tbxAcompanante.getValue());
				his_triage
						.setIdentificacion_acompanante((dbxIdentificacion_acompanante
								.getValue() != null ? dbxIdentificacion_acompanante
								.getText() + ""
								: ""));
				his_triage.setRelacion(lbxRelacion.getSelectedItem().getValue()
						.toString());
				his_triage
						.setEdad_acompanante((ibxEdad_acompanante.getValue() != null ? ibxEdad_acompanante
								.getValue() + ""
								: ""));
				his_triage
						.setEscolaridad_acompanante(lbxEscolaridad_acompanante
								.getSelectedItem().getValue().toString());
				his_triage.setTelefono_acompanante((dbxTelefono_acompanante
						.getValue() != null ? dbxTelefono_acompanante.getText()
						+ "" : ""));

				his_triage.setMotivo(tbxMotivo.getValue());
				his_triage.setAdmitido(rdbAdmitido.getSelectedItem().getValue()
						.toString() != null ? rdbAdmitido.getSelectedItem()
						.getValue().toString() : "");
				his_triage.setAdmitido_si(""
						+ lbxAdmitido_si.getSelectedIndex());
				his_triage.setAdmitido_no(tbxAdmitido_no.getValue());
				his_triage.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				his_triage.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				his_triage.setCreacion_user(usuarios.getCodigo().toString());
				his_triage.setDelete_date(null);
				his_triage.setUltimo_user(usuarios.getCodigo().toString());
				his_triage.setDelete_user(null);
				his_triage.setNivel_triage(nivel_triage);
				his_triage.setNro_ingreso(admision.getNro_ingreso());

				if (his_triage.getAdmitido_si().equals("2")) {
					admision.setAdmision_parto("S");
				} else {
					admision.setAdmision_parto("N");
				}

				if (gbxGlasgow.isVisible()) {
					his_triage.setEscala_glasgow("S");
					his_triage.setRespuesta_motora(macroGlasgow
							.obtenerRespuestaMotora());
					his_triage.setRespuesta_verbal(macroGlasgow
							.obtenerRespuestaVerbal());
					his_triage.setApertura_ocular(macroGlasgow
							.obtenerAperturaOcular());
				} else {
					his_triage.setEscala_glasgow("N");
				}

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("his_triage", his_triage);
				datos.put("accion", tbxAccion.getText());
				datos.put("admision", admision);

				// Habilitamos el cargado solo
				// cuando este activo el parametro
				// para evitar que no deje guardar el triage
				if (getParametros_empresa().getCargar_signos_vitales_triage()
						.equals("S")) {
					datos.put("sigvitales", mcSignosVitales.obtenerSigvitales());
				}

				his_triage = getServiceLocator().getHis_triageService()
						.guardar(datos);

				tbxAccion.setValue("modificar");

				String codigo_historia = his_triage.getCodigo_historia();
				
				infoPacientes.setCodigo_historia_aux(codigo_historia);

				MensajesUtil.mensajeInformacion("Información ..",
						"Los datos se guardaron satisfactoriamente");

			}

		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			} else {
				log.error(e.getMessage(), e);
			}
		}
	}

	public void cargarSignosVitalesEnfermera() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setCodigo_empresa(admision.getCodigo_empresa());
		enfermera_signos.setCodigo_sucursal(admision.getCodigo_sucursal());
		enfermera_signos.setNro_ingreso(admision.getNro_ingreso());
		enfermera_signos
				.setNro_identificacion(admision.getNro_identificacion());
		enfermera_signos = getServiceLocator().getServicio(
				Enfermera_signosService.class).consultar(enfermera_signos);
		if (enfermera_signos != null) {
			Sigvitales sigvitales = new Sigvitales();
			sigvitales.setCreatinina_cerica(enfermera_signos
					.getCreatinina_serica());
			sigvitales.setFrecuencia_cardiaca(enfermera_signos
					.getFrecuencia_cardiaca());
			sigvitales.setFrecuencia_respiratoria(enfermera_signos
					.getFrecuencia_respiratoria());
			sigvitales.setImc(enfermera_signos.getImc());
			sigvitales.setPerimetro_cefalico(enfermera_signos
					.getPerimetro_cefalico());
			sigvitales.setPerimetro_toraxico(enfermera_signos
					.getPerimetro_toraxico());
			sigvitales.setPeso(enfermera_signos.getPeso());
			sigvitales.setSuperficie_corporal(enfermera_signos
					.getSuperficie_corporal());
			sigvitales.setTa_diastolica(enfermera_signos.getTa_diastolica());
			sigvitales.setTa_sistolica(enfermera_signos.getTa_sistolica());
			sigvitales.setTa_media(enfermera_signos.getTa_media());
			sigvitales.setTalla(enfermera_signos.getTalla());
			sigvitales.setTemparatura(enfermera_signos.getTemperatura());
			sigvitales.setTfg(enfermera_signos.getTfg());
			mcSignosVitales.mostrarSigvitalesInformacion(sigvitales);
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		His_triage his_triage = (His_triage) obj;
		try {

			String codigo_historia = his_triage.getCodigo_historia();

			infoPacientes.setCodigo_historia_aux(codigo_historia);
			infoPacientes.setFecha_inicio(his_triage.getFecha_inicial());
			infoPacientes.setFecha_cierre(true, his_triage.getUltimo_update());

			initMostrar_datos(his_triage);

			cargarInformacion_paciente();

			if (his_triage.getNivel_triage() != null
					&& !his_triage.getNivel_triage().isEmpty()) {
				onMostrarTriage(Integer.parseInt(his_triage.getNivel_triage()));
				deshabilitarButton(Integer.parseInt(his_triage
						.getNivel_triage()));
			} else {
				onMostrarTriage(0);
			}

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(his_triage.getCodigo_empresa());
			prestadores.setCodigo_sucursal(his_triage.getCodigo_sucursal());
			prestadores.setNro_identificacion(his_triage.getCodigo_prestador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);

			bandboxPrestador.setValue(his_triage.getCodigo_prestador());
			tbxNomPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));

			// Acompañante
			tbxAcompanante.setValue(his_triage.getAcompanante());
			dbxIdentificacion_acompanante.setValue(ConvertidorDatosUtil
					.convertirDato(his_triage.getIdentificacion_acompanante()));
			Utilidades.seleccionarListitem(lbxRelacion,
					his_triage.getRelacion());
			ibxEdad_acompanante
					.setValue((his_triage.getEdad_acompanante() != null && !his_triage
							.getEdad_acompanante().isEmpty()) ? Integer
							.parseInt(his_triage.getEdad_acompanante()) : null);
			Utilidades.seleccionarListitem(lbxEscolaridad_acompanante,
					his_triage.getEscolaridad_acompanante());
			dbxTelefono_acompanante.setValue(ConvertidorDatosUtil
					.convertirDato(his_triage.getTelefono_acompanante()));

			tbxMotivo.setValue(his_triage.getMotivo());
			Utilidades.seleccionarRadio(rdbAdmitido, his_triage.getAdmitido());

			// esto sirve para cargar la copia de los signos vitales que se hace
			// en la misma tabla (Manuel Aguilar)
			Sigvitales sigvitales = new Sigvitales();
			sigvitales.setCodigo_empresa(codigo_empresa);
			sigvitales.setCodigo_sucursal(codigo_sucursal);
			sigvitales.setNro_identificacion(infoPacientes.getCodigo_historiaFac());
			sigvitales.setCreatinina_cerica(his_triage.getCreatinina_cerica());
			sigvitales.setFrecuencia_cardiaca(his_triage
					.getFrecuencia_cardiaca());
			sigvitales.setFrecuencia_respiratoria(his_triage
					.getFrecuencia_respiratoria());
			sigvitales.setImc(his_triage.getImc());
			sigvitales
					.setPerimetro_cefalico(his_triage.getPerimetro_cefalico());
			sigvitales
					.setPerimetro_toraxico(his_triage.getPerimetro_toraxico());
			sigvitales.setPeso(his_triage.getPeso());
			sigvitales.setSuperficie_corporal(his_triage
					.getSuperficie_corporal());
			sigvitales.setTa_diastolica(his_triage.getTa_diastolica());
			sigvitales.setTa_sistolica(his_triage.getTa_sistolica());
			sigvitales.setTa_media(his_triage.getTa_media());
			sigvitales.setTalla(his_triage.getTalla());
			sigvitales.setTemparatura(his_triage.getTemparatura());
			sigvitales.setTfg(his_triage.getTfg());

			mcSignosVitales.mostrarSigvitales(sigvitales);

			if (his_triage.getAdmitido().equals("S")) {
				lbAdmitido_si.setVisible(true);
				lbxAdmitido_si.setVisible(true);
				// Utilidades.seleccionarListitem(lbxAdmitido_si,
				// his_triage.getAdmitido_si());
				lbxAdmitido_si.setSelectedIndex(Integer.parseInt(his_triage
						.getAdmitido_si()));
				rowAdmitido_no.setVisible(false);
				lbAdmitido_no.setVisible(false);
				tbxAdmitido_no.setVisible(false);

			} else {
				lbAdmitido_si.setVisible(false);
				lbxAdmitido_si.setVisible(false);
				rowAdmitido_no.setVisible(true);
				lbAdmitido_no.setVisible(true);
				tbxAdmitido_no.setVisible(true);
				tbxAdmitido_no.setValue(his_triage.getAdmitido_no());

			}

			dbxIdentificacion_acompanante.setDisabled(true);
			dbxTelefono_acompanante.setDisabled(true);
			lbxRelacion.setDisabled(true);
			lbxEscolaridad_acompanante.setDisabled(true);

			macroGlasgow.limpiarEscalaGlasgow();
			if (his_triage.getEscala_glasgow().equals("S")) {
				gbxGlasgow.setVisible(true);
				macroGlasgow.mostrarEscalaGlasgow(
						his_triage.getRespuesta_motora(),
						his_triage.getRespuesta_verbal(),
						his_triage.getApertura_ocular(), false);
			} else {
				gbxGlasgow.setVisible(false);
			}
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		His_triage his_triage = (His_triage) obj;
		try {
			int result = his_triageService.eliminar(his_triage);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Información se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
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

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Prestadores> list = getServiceLocator()
					.getPrestadoresService().listar(parameters);

			lbx.getItems().clear();

			for (Prestadores dato : list) {

				Especialidad especialidad = new Especialidad();
				especialidad.setCodigo(dato.getCodigo_especialidad());
				especialidad = getServiceLocator().getEspecialidadService()
						.consultar(especialidad);

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombres()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellidos()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(
						(especialidad != null ? especialidad.getNombre() : "")));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	public void selectedPrestador(Listitem listitem) {
		if (listitem.getValue() == null) {
			bandboxPrestador.setValue("");
			tbxNomPrestador.setValue("");
		} else {
			Prestadores dato = (Prestadores) listitem.getValue();
			bandboxPrestador.setValue(dato.getNro_identificacion());
			tbxNomPrestador.setValue(dato.getNombres() + " "
					+ dato.getApellidos());
		}
		bandboxPrestador.close();
	}

	public void cargarPrestador() throws Exception {
		if (rol_usuario.equals("05")) {
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(empresa.getCodigo_empresa());
			prestadores.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			prestadores.setNro_identificacion(usuarios.getCodigo());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			if (prestadores == null) {
				throw new Exception(
						"Usuario no esta creado en el modulo de prestadore, actualize informacion de usuario");
			}
			bandboxPrestador.setValue(prestadores.getNro_identificacion());
			tbxNomPrestador.setValue(prestadores.getNombres() + " "
					+ prestadores.getApellidos());
		} else {

			bandboxPrestador.setValue("000000000");
			tbxNomPrestador.setValue("MEDICO POR DEFECTO");
		}
	}

	public void onMostrarTriage(Integer opc) {
		switch (opc) {
		case 1:
			nivel_triage = "1";
			tbxObservacion.setText(INivelesTriage.VARIABLE_NIVEL1);
			tbxObservacion.setStyle("background-color:#FF0000;color:white");
			break;
		case 2:
			nivel_triage = "2";
			tbxObservacion.setText(INivelesTriage.VARIABLE_NIVEL2);
			tbxObservacion.setStyle("background-color:orange;color:white");
			break;
		case 3:
			nivel_triage = "3";
			tbxObservacion.setText(INivelesTriage.VARIABLE_NIVEL3);
			tbxObservacion
					.setStyle("background-color:yellow;color:white;color:black");
			break;
		case 4:
			nivel_triage = "4";
			tbxObservacion.setText(INivelesTriage.VARIABLE_NIVEL4);
			tbxObservacion.setStyle("background-color:green;color:white");
			break;
		default:
			nivel_triage = null;
			tbxObservacion.setText("");
			break;
		}

		rdbAdmitido.setSelectedIndex(0);
		onSeleccionarRadio();

	}

	public void deshabilitarButton(Integer opc) {

		switch (opc) {
		case 1:
			btnMostrar_triage_i.setDisabled(false);
			btnMostrar_triage_ii.setDisabled(true);
			btnMostrar_triage_iii.setDisabled(true);
			btnMostrar_triage_iv.setDisabled(true);
			break;
		case 2:
			btnMostrar_triage_i.setDisabled(true);
			btnMostrar_triage_ii.setDisabled(false);
			btnMostrar_triage_iii.setDisabled(true);
			btnMostrar_triage_iv.setDisabled(true);
			break;
		case 3:
			btnMostrar_triage_i.setDisabled(true);
			btnMostrar_triage_ii.setDisabled(true);
			btnMostrar_triage_iii.setDisabled(false);
			btnMostrar_triage_iv.setDisabled(true);
			break;
		case 4:
			btnMostrar_triage_i.setDisabled(true);
			btnMostrar_triage_ii.setDisabled(true);
			btnMostrar_triage_iii.setDisabled(true);
			btnMostrar_triage_iv.setDisabled(false);
			break;

		}

	}

	public void onSeleccionarRadio() {
		if (rdbAdmitido.getSelectedItem() != null) {
			String valor = rdbAdmitido.getSelectedItem().getValue().toString();
			if (valor.equals("S")) {
				lbAdmitido_si.setVisible(true);
				lbxAdmitido_si.setVisible(true);
				rowAdmitido_no.setVisible(false);
				lbAdmitido_no.setVisible(false);
				tbxAdmitido_no.setVisible(false);
				listarListboxAdmitido();
			} else {
				lbAdmitido_si.setVisible(false);
				lbxAdmitido_si.setVisible(false);
				rowAdmitido_no.setVisible(true);
				lbAdmitido_no.setVisible(true);
				tbxAdmitido_no.setVisible(true);
			}
		}

	}

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {
			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (admision.getRealizo_triage()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				His_triage his_triage = new His_triage();
				his_triage.setCodigo_empresa(empresa.getCodigo_empresa());
				his_triage.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				his_triage.setIdentificacion(admision.getNro_identificacion());
				his_triage.setNro_ingreso(admision.getNro_ingreso());

				// log.info("his_triage - initHistoria " + his_triage);

				his_triage = getServiceLocator().getHis_triageService()
						.consultar_historia(his_triage);

				if (his_triage != null) {
					accionForm(true, "mostrar");
					mostrarDatos(his_triage);
					btnCancelar.setVisible(true);

					String codigo_historia = his_triage.getCodigo_historia();
					infoPacientes.setCodigo_historia_aux(codigo_historia);
				} else {
					groupboxEditar.setVisible(false);
					throw new Exception(
							"NO hay una historia clinica relacionada a este paciente admitido");
				}
			} else {
				if (opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
					accionForm(true, "registrar");
					btnCancelar.setVisible(false);
				} else {
					accionForm(false, "consultar");
					btnCancelar.setVisible(true);

				}
			}
			if (infoPacientes.getEdadAnios() != null
					&& infoPacientes.getEdadAnios().compareTo(16) <= 0) {
				chkAplica_acompaniante.setDisabled(true);
			}

			// onCheckAcompaniante();

		}
	}

	@Override
	public void inicializarVista(String tipo) {

	}

	@Override
	public void cargarInformacion_paciente() {
		mcSignosVitales.setFecha_nacimiento(admision.getPaciente()
				.getFecha_nacimiento());
		mcSignosVitales.setGenero(admision.getPaciente().getSexo());
		mcSignosVitales.inicializarParametrosAlertas();
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("identificacion",
									admision.getNro_identificacion());
							parametros.put("order_desc", "order_desc");

							List<His_triage> listado_historias = getServiceLocator()
									.getHis_triageService().listar(parametros);

							if (!listado_historias.isEmpty()) {
								// inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								cargarInformacion_historia_anterior(listado_historias
										.get(0));
								toolbarbuttonTipo_historia
										.setLabel("Creando Triage (Urgencias)");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando Triage (Urgencias)");
								// inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
							}
						}

					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		// Psiquiatria psiquiatria = (Psiquiatria) historia_clinica;

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar",
							"lbxFisico_cabeza_cara_estado",
							"lbxFisico_ocular_estado",
							"lbxFisico_endocrinologo_estado",
							"lbxFisico_otorrino_estado",
							"lbxFisico_osteomuscular_estado",
							"lbxFisico_cardio_pulmonar_estado",
							"lbxFisico_examen_mama_estado",
							"lbxFisico_cuello_estado",
							"lbxFisico_vacular_estado",
							"lbxFisico_piel_fanera_estado",
							"lbxFisico_gastointestinal_estado",
							"lbxFisico_neurologico_estado",
							"lbxFisico_genitourinario_estado", "lbxRelacion",
							"lbxEscolaridad_acompanante" });

			onCheckAcompaniante();

			toolbarbuttonTipo_historia.setLabel("Mostrando Triage (Urgencias)");

		} else {

			toolbarbuttonTipo_historia
					.setLabel("Modificando Triage (Urgencias)");

		}

	}

	public void onCheckAcompaniante() {
		if (!chkAplica_acompaniante.isChecked()) {
			tbxAcompanante.setDisabled(true);
			lbxRelacion.setDisabled(true);
			dbxIdentificacion_acompanante.setDisabled(true);
			ibxEdad_acompanante.setDisabled(true);
			lbxEscolaridad_acompanante.setDisabled(true);
			dbxTelefono_acompanante.setDisabled(true);
			chkAplica_acompaniante.setTooltiptext("No aplica acompañante");
		} else {
			tbxAcompanante.setDisabled(false);
			lbxRelacion.setDisabled(false);
			dbxIdentificacion_acompanante.setDisabled(false);
			ibxEdad_acompanante.setDisabled(false);
			lbxEscolaridad_acompanante.setDisabled(false);
			dbxTelefono_acompanante.setDisabled(false);
			chkAplica_acompaniante
					.setTooltiptext("Diligenciar la información del acompañante");
		}
	}

	public void onMostrarEscalaGlasgow() {
		Boolean visible = gbxGlasgow.isVisible();
		if (!visible) {
			btnMostrar_escala_glasgow.setLabel("Ocultar Escala de Glasgow");
			gbxGlasgow.setVisible(true);
		} else {
			btnMostrar_escala_glasgow.setLabel("Mostrar Escala de Glasgow");
			gbxGlasgow.setVisible(false);
		}
	}

	public void imprimir(String codigo_historia_aux) {
		String codigo_historia = "";
		if (codigo_historia_aux != null) {
			codigo_historia = codigo_historia_aux;
		} else {
			codigo_historia = infoPacientes.getCodigo_historia_aux();
		}

		if (codigo_historia != null && !codigo_historia.isEmpty()) {
			Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", "ReporteTriage");
			paramRequest.put("codigo_historia", codigo_historia);

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, paramRequest);
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