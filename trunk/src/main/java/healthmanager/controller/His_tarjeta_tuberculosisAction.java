/*
 * his_tarjeta_tuberculosisAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.His_contactos_tuberculosis;
import healthmanager.modelo.bean.His_evaluacion_tuberculosis;
import healthmanager.modelo.bean.His_fases_tuberculosis;
import healthmanager.modelo.bean.His_tarjeta_tuberculosis;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.His_contactos_tuberculosisService;
import healthmanager.modelo.service.His_evaluacion_tuberculosisService;
import healthmanager.modelo.service.His_fases_tuberculosisService;
import healthmanager.modelo.service.His_tarjeta_tuberculosisService;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.CampoMesMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class His_tarjeta_tuberculosisAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(His_tarjeta_tuberculosisAction.class);

	private Map<String, Map<String, His_fases_tuberculosis>> mapa_general_1 = new TreeMap<String, Map<String, His_fases_tuberculosis>>();
	private Map<String, Map<String, His_fases_tuberculosis>> mapa_general_2 = new TreeMap<String, Map<String, His_fases_tuberculosis>>();

	private His_tarjeta_tuberculosisService his_tarjeta_tuberculosisService;
	private His_contactos_tuberculosisService his_contactos_tuberculosisService;
	private His_evaluacion_tuberculosisService his_evaluacion_tuberculosisService;

	// Componentes //

	private Admision admision;
	private Opciones_via_ingreso opciones_via_ingreso;

	@View
	private Listbox listboxFase1;
	@View
	private Listbox listboxFase2;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	@View
	private Listbox lbxParameter;
	@View
	private Listbox lbxTipo_historia;
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
	private Toolbarbutton btGuardar;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	// private String tipo_historia;

	@View
	private Listbox lbxAtendida;
	@View
	private Textbox tbxDireccion_vivienda;
	@View
	private Textbox tbxBarrio_vivienda;
	@View
	private Doublebox dbxTelefono_vivienda;
	@View
	private Textbox tbxDireccion_trabajo;
	@View
	private Textbox tbxBarrio_trabajo;
	@View
	private Doublebox dbxTelefono_trabajo;
	@View
	private Radiogroup rdbTipo_paciente;
	@View
	private Textbox tbxIps;
	@View
	private Radiogroup rdbTipo_tuberculosis;
	@View
	private Textbox tbxLocalizacion_extrapulmonar;
	@View
	private Checkbox chbEnfermedad_renal;
	@View
	private Checkbox chbEnfermedad_hepatica;
	@View
	private Checkbox chbVih_sida;
	@View
	private Checkbox chbDiabetes_mellitus;
	@View
	private Checkbox chbSilicosis;
	@View
	private Checkbox chbDesnutricion;
	@View
	private Checkbox chbAlcoholismo;
	@View
	private Checkbox chbFarmacodependencia;
	@View
	private Checkbox chbEnbarazo;
	@View
	private Radiogroup rdbTerapia_vih;
	@View
	private Doublebox dbxPeso;
	@View
	private Datebox dtbxFecha_fase1;
	@View
	private Datebox dtbxFecha_fase2;
	@View
	private Doublebox dbxRifampicina1;
	@View
	private Doublebox dbxRifampicina2;
	@View
	private Doublebox dbxIsonlacida1;
	@View
	private Doublebox dbxIsonlacida2;
	@View
	private Doublebox dbxPirazinamida1;
	@View
	private Doublebox dbxPirazinamida2;
	@View
	private Doublebox dbxEtambutol1;
	@View
	private Doublebox dbxEtambutol2;
	@View
	private Doublebox dbxEstreptomicina1;
	@View
	private Doublebox dbxEstreptomicina2;
	@View
	private Radiogroup rdbBaciloscopia;
	@View
	private Radiogroup rdbCultivo;
	@View
	private Radiogroup rdbHistopatologia;
	@View
	private Radiogroup rdbCuadro_clinico;
	@View
	private Radiogroup rdbEpidemiologico;
	@View
	private Radiogroup rdbRadiologico;
	@View
	private Radiogroup rdbTuberculinico;
	@View
	private Radiogroup rdbAda;
	@View
	private Textbox tbxBk_1;
	@View
	private Textbox tbxBk_2;
	@View
	private Textbox tbxBk_3;
	@View
	private Datebox dtbxTiempo_sintomas;
	@View
	private Datebox dtbxFecha_diagnostico;
	@View
	private Datebox dtbxFecha_cultivo;
	@View
	private Radiogroup rdbAsesoria_vih;
	@View
	private Radiogroup rdbConfirmatoria_vih;
	@View
	private Datebox dtbxFecha_programada1;
	@View
	private Datebox dtbxFecha_programada2;
	@View
	private Datebox dtbxFecha_programada3;
	@View
	private Datebox dtbxFecha_programada4;
	@View
	private Datebox dtbxFecha_programada5;
	@View
	private Datebox dtbxFecha_programada6;
	@View
	private Datebox dtbxFecha_control_bacteriologico2;
	@View
	private Datebox dtbxFecha_control_bacteriologico4;
	@View
	private Datebox dtbxFecha_control_bacteriologico6;
	@View
	private Textbox tbxResultado_bk2;
	@View
	private Textbox tbxResultado_bk4;
	@View
	private Textbox tbxResultado_bk6;
	@View
	private Datebox dtbxFecha_control_medicina2;
	@View
	private Datebox dtbxFecha_control_medicina4;
	@View
	private Datebox dtbxFecha_control_medicina6;
	@View
	private Datebox dtbxFecha_control_enfermeria1;
	@View
	private Datebox dtbxFecha_control_enfermeria3;
	@View
	private Datebox dtbxFecha_control_enfermeria5;
	@View
	private Textbox tbxPeso1;
	@View
	private Textbox tbxPeso2;
	@View
	private Textbox tbxPeso3;
	@View
	private Textbox tbxPeso4;
	@View
	private Textbox tbxPeso5;
	@View
	private Textbox tbxPeso6;
	@View
	private Textbox tbxObservaciones1;
	@View
	private Textbox tbxObservaciones2;
	@View
	private Textbox tbxObservaciones3;
	@View
	private Textbox tbxObservaciones4;
	@View
	private Textbox tbxObservaciones5;
	@View
	private Textbox tbxObservaciones6;
	@View
	private Radiogroup rdbResultado_tratamiento;
	@View
	private Radiogroup rdbFracaso;
	@View
	private Textbox tbxAbandono;
	@View
	private Textbox tbxFallecio;
	@View
	private Textbox tbxTraslado;
	@View
	private Datebox dtbxFecha_egreso;
	@View
	private Textbox tbxObservaciones_tratamiento;
	@View
	private Row rowDatosAdicionales;
	@View
	private Row rowCondicionIngreso;
	@View
	private Row rowTratamiento;
	@View
	private Row rowDiagnostico;
	@View
	private Row rowControlContactos;
	@View
	private Row rowFasesTratamiento;
	@View
	private Row rowControlesProgramados;
	@View
	private Row rowEvolucion;
	@View
	private Row rowResultado;

	private final String[] idsExcluyentes = { "tbxAccion",
			"btnLimpiar_prestador", "tbxValue", "lbxParameter",
			"infoPacientes", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar" };

	@View
	private Grid gridContactos;
	@View
	private Grid gridEvaluacion;

	@View
	private Listbox lbxAnio;
	
	@View
	private Row rowRemitido;
	@View
	private Label lbIps;
	@View
	private Label lbLocalizacion_extrapulmonar;
	@View
	private Row rowFracaso;
	@View
	private Label lbFracaso;
	@View
	private Row rowAbandono;
	@View
	private Label lbAbandono;
	@View
	private Row rowFallecio;
	@View
	private Label lbFallecio;
	@View
	private Row rowTraslado;
	@View
	private Label lbTraslado;
	
	@View
	private Button btnMostrar_resultado;
	@View
	private Row rowCondiciones;
	@View
	private Row rowFecha_egreso;
	@View
	private Row rowObservaciones_tratamiento;
	@View
	private Row rowTextoObservacion;

	private Boolean entrega_medicamento;
	
	@Override
	public void init() {
		try {
			listarCombos();
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			dtbxFecha_fase1.setText("");
			dtbxFecha_fase2.setText("");
			// mostrarContactos();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	/**
	 * Sobreescritura del metodo params(Map<String, Object> map) para obtener
	 * los parametros iniciales con los que trabajara la historia clinica
	 */
	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {

			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map.get(IVias_ingreso.OPCION_VIA_INGRESO);
			entrega_medicamento = (Boolean) map.get("medicamentos_tuberculosis");
			
			if(entrega_medicamento == null){
				entrega_medicamento = false;
			}
			
			cambiarVistaMedicamentos(entrega_medicamento);

		}
	}

	public void listarCombos() {
		listarParameter();
		llenarListboxAno();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
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

			if (!accion.equalsIgnoreCase("mostrar")) {
				buscarDatos();

			}else{
				infoPacientes.setFecha_inicio(new Date());
				cargarInformacion_paciente();
				mostrarContactos();
				mostrarEvaluacion();

			}

		} else {
			limpiarDatos();
			FormularioUtil.cargarRadiogroupsDefecto(this);
			//log.info("admision >>>>>>" + admision);
			if (admision != null){
				infoPacientes.setFecha_inicio(new Date());
				cargarInformacion_paciente();
				mostrarContactos();
				mostrarEvaluacion();
			}
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		dtbxFecha_fase1.setValue(null);
		dtbxFecha_fase2.setValue(null);
		dtbxFecha_fase1.setText("");
		dtbxFecha_fase2.setText("");
		dtbxTiempo_sintomas.setValue(null);
		dtbxFecha_diagnostico.setValue(null);
		dtbxFecha_cultivo.setValue(null);
		dtbxFecha_programada1.setValue(null);
		dtbxFecha_programada2.setValue(null);
		dtbxFecha_programada3.setValue(null);
		dtbxFecha_programada4.setValue(null);
		dtbxFecha_programada5.setValue(null);
		dtbxFecha_programada6.setValue(null);
		dtbxFecha_control_bacteriologico2.setValue(null);
		dtbxFecha_control_bacteriologico4.setValue(null);
		dtbxFecha_control_bacteriologico6.setValue(null);
		dtbxFecha_control_medicina2.setValue(null);
		dtbxFecha_control_medicina4.setValue(null);
		dtbxFecha_control_medicina6.setValue(null);
		dtbxFecha_control_enfermeria1.setValue(null);
		dtbxFecha_control_enfermeria3.setValue(null);
		dtbxFecha_control_enfermeria5.setValue(null);
		dtbxFecha_egreso.setValue(null);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
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

			his_tarjeta_tuberculosisService.setLimit("limit 25 offset 0");

			List<His_tarjeta_tuberculosis> lista_datos = his_tarjeta_tuberculosisService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (His_tarjeta_tuberculosis his_tarjeta_tuberculosis : lista_datos) {
				rowsResultado.appendChild(crearFilas(his_tarjeta_tuberculosis,
						this));
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

		final His_tarjeta_tuberculosis his_tarjeta_tuberculosis = (His_tarjeta_tuberculosis) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(his_tarjeta_tuberculosis
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(his_tarjeta_tuberculosis.getIdentificacion()
				+ ""));
		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(
				his_tarjeta_tuberculosis.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label("Triage (Urgencias)"));

		Hlayout hlayout = new Hlayout();

		Toolbarbutton btn_mostrar = new Toolbarbutton();
		btn_mostrar.setImage("/images/mostrar_info.png");
		btn_mostrar.setTooltiptext("Mostrar Tarjeta de Tuberculosis");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.setLabel("Mostrar");
		btn_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(his_tarjeta_tuberculosis);
					}
				});
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);

		return fila;
	}

	public void llenarListboxAno(){
		Calendar calendar = Calendar.getInstance();
		
		String a1 = new Integer(calendar.get(Calendar.YEAR)-2).toString();
		String a2 = new Integer(calendar.get(Calendar.YEAR)-1).toString();
		String a3 = new Integer(calendar.get(Calendar.YEAR)).toString();
		
		lbxAnio.getChildren().clear();
		
		lbxAnio.appendItem(a3, a3);
		lbxAnio.appendItem(a2, a2);
		lbxAnio.appendItem(a1, a1);
		lbxAnio.setSelectedIndex(0);
	}
	
	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {

			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);

				His_tarjeta_tuberculosis his_tarjeta_tuberculosis = new His_tarjeta_tuberculosis();
				his_tarjeta_tuberculosis.setCodigo_empresa(empresa
						.getCodigo_empresa());
				his_tarjeta_tuberculosis.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				his_tarjeta_tuberculosis.setCodigo_historia(infoPacientes
						.getCodigo_historia() + "");
				his_tarjeta_tuberculosis
						.setIdentificacion(admision != null ? admision
								.getNro_identificacion() : null);
				his_tarjeta_tuberculosis.setFecha_inicial(new Timestamp(
						infoPacientes.getFecha_inicial().getTime()));
				his_tarjeta_tuberculosis.setCodigo_prestador(usuarios.getCodigo());
				his_tarjeta_tuberculosis.setNro_ingreso(admision
						.getNro_ingreso());
				his_tarjeta_tuberculosis
						.setDireccion_vivienda(tbxDireccion_vivienda.getValue());
				his_tarjeta_tuberculosis.setBarrio_vivienda(tbxBarrio_vivienda
						.getValue());
				his_tarjeta_tuberculosis
						.setTelefono_vivienda((dbxTelefono_vivienda
						.getValue() != null ? dbxTelefono_vivienda.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis
						.setDireccion_trabajo(tbxDireccion_trabajo.getValue());
				his_tarjeta_tuberculosis.setBarrio_trabajo(tbxBarrio_trabajo
						.getValue());
				his_tarjeta_tuberculosis
						.setTelefono_trabajo((dbxTelefono_trabajo
						.getValue() != null ? dbxTelefono_trabajo.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis.setTipo_paciente(rdbTipo_paciente
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis.setIps(tbxIps.getValue());
				his_tarjeta_tuberculosis
						.setTipo_tuberculosis(rdbTipo_tuberculosis
								.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis
						.setLocalizacion_extrapulmonar(tbxLocalizacion_extrapulmonar
								.getValue());
				his_tarjeta_tuberculosis
						.setEnfermedad_renal(chbEnfermedad_renal.isChecked());
				his_tarjeta_tuberculosis
						.setEnfermedad_hepatica(chbEnfermedad_hepatica
								.isChecked());
				his_tarjeta_tuberculosis.setVih_sida(chbVih_sida.isChecked());
				his_tarjeta_tuberculosis
						.setDiabetes_mellitus(chbDiabetes_mellitus.isChecked());
				his_tarjeta_tuberculosis.setSilicosis(chbSilicosis.isChecked());
				his_tarjeta_tuberculosis.setDesnutricion(chbDesnutricion
						.isChecked());
				his_tarjeta_tuberculosis.setAlcoholismo(chbAlcoholismo
						.isChecked());
				his_tarjeta_tuberculosis
						.setFarmacodependencia(chbFarmacodependencia
								.isChecked());
				his_tarjeta_tuberculosis.setEnbarazo(chbEnbarazo.isChecked());
				his_tarjeta_tuberculosis.setTerapia_vih(rdbTerapia_vih
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis
						.setPeso((dbxPeso.getValue() != null ? dbxPeso
								.getValue() + "" : ""));

				if (dtbxFecha_fase1.getValue() != null) {
					his_tarjeta_tuberculosis.setFecha_fase1(new Timestamp(
							dtbxFecha_fase1.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_fase1(null);

				}

				if (dtbxFecha_fase2.getValue() != null) {
					his_tarjeta_tuberculosis.setFecha_fase2(new Timestamp(
							dtbxFecha_fase2.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_fase2(null);
				}

				his_tarjeta_tuberculosis.setRifampicina1((dbxRifampicina1
						.getValue() != null ? dbxRifampicina1.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis.setRifampicina2((dbxRifampicina2
						.getValue() != null ? dbxRifampicina2.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis.setIsonlacida1((dbxIsonlacida1
						.getValue() != null ? dbxIsonlacida1.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis.setIsonlacida2((dbxIsonlacida2
						.getValue() != null ? dbxIsonlacida2.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis.setPirazinamida1((dbxPirazinamida1
						.getValue() != null ? dbxPirazinamida1.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis.setPirazinamida2((dbxPirazinamida2
						.getValue() != null ? dbxPirazinamida2.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis.setEtambutol1((dbxEtambutol1
						.getValue() != null ? dbxEtambutol1.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis.setEtambutol2((dbxEtambutol2
						.getValue() != null ? dbxEtambutol2.getValue() + ""
						: ""));
				his_tarjeta_tuberculosis.setEstreptomicina1((dbxEstreptomicina1
						.getValue() != null ? dbxEstreptomicina1.getValue()
						+ "" : ""));
				his_tarjeta_tuberculosis.setEstreptomicina2((dbxEstreptomicina2
						.getValue() != null ? dbxEstreptomicina2.getValue()
						+ "" : ""));
				his_tarjeta_tuberculosis.setBaciloscopia(rdbBaciloscopia
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis.setCultivo(rdbCultivo
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis.setHistopatologia(rdbHistopatologia
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis.setCuadro_clinico(rdbCuadro_clinico
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis.setEpidemiologico(rdbEpidemiologico
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis.setRadiologico(rdbRadiologico
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis.setTuberculinico(rdbTuberculinico
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis.setAda(rdbAda.getSelectedItem()
						.getValue().toString());
				his_tarjeta_tuberculosis.setBk_1(tbxBk_1.getValue());
				his_tarjeta_tuberculosis.setBk_2(tbxBk_2.getValue());
				his_tarjeta_tuberculosis.setBk_3(tbxBk_3.getValue());

				if (dtbxTiempo_sintomas.getValue() != null) {
					his_tarjeta_tuberculosis.setTiempo_sintomas(new Timestamp(
							dtbxTiempo_sintomas.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setTiempo_sintomas(null);
				}

				if (dtbxFecha_diagnostico.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_diagnostico(new Timestamp(
									dtbxFecha_diagnostico.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_diagnostico(null);
				}

				if (dtbxFecha_cultivo.getValue() != null) {
					his_tarjeta_tuberculosis.setFecha_cultivo(new Timestamp(
							dtbxFecha_cultivo.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_cultivo(null);
				}

				his_tarjeta_tuberculosis.setAsesoria_vih(rdbAsesoria_vih
						.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis
						.setConfirmatoria_vih(rdbConfirmatoria_vih
								.getSelectedItem().getValue().toString());

				if (dtbxFecha_programada1.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_programada1(new Timestamp(
									dtbxFecha_programada1.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_programada1(null);
				}

				if (dtbxFecha_programada2.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_programada2(new Timestamp(
									dtbxFecha_programada2.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_programada2(null);
				}

				if (dtbxFecha_programada3.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_programada3(new Timestamp(
									dtbxFecha_programada3.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_programada3(null);
				}

				if (dtbxFecha_programada4.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_programada4(new Timestamp(
									dtbxFecha_programada4.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_programada4(null);
				}

				if (dtbxFecha_programada5.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_programada5(new Timestamp(
									dtbxFecha_programada5.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_programada5(null);
				}

				if (dtbxFecha_programada6.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_programada6(new Timestamp(
									dtbxFecha_programada6.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_programada6(null);
				}

				if (dtbxFecha_control_bacteriologico2.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_control_bacteriologico2(new Timestamp(
									dtbxFecha_control_bacteriologico2
											.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis
							.setFecha_control_bacteriologico2(null);
				}

				if (dtbxFecha_control_bacteriologico4.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_control_bacteriologico4(new Timestamp(
									dtbxFecha_control_bacteriologico4
											.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis
							.setFecha_control_bacteriologico4(null);
				}

				if (dtbxFecha_control_bacteriologico6.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_control_bacteriologico6(new Timestamp(
									dtbxFecha_control_bacteriologico6
											.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis
							.setFecha_control_bacteriologico6(null);
				}

				his_tarjeta_tuberculosis.setResultado_bk2(tbxResultado_bk2
						.getValue());
				his_tarjeta_tuberculosis.setResultado_bk4(tbxResultado_bk4
						.getValue());
				his_tarjeta_tuberculosis.setResultado_bk6(tbxResultado_bk6
						.getValue());

				if (dtbxFecha_control_medicina2.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_control_medicina2(new Timestamp(
									dtbxFecha_control_medicina2.getValue()
											.getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_control_medicina2(null);
				}

				if (dtbxFecha_control_medicina4.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_control_medicina4(new Timestamp(
									dtbxFecha_control_medicina4.getValue()
											.getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_control_medicina4(null);
				}

				if (dtbxFecha_control_medicina6.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_control_medicina6(new Timestamp(
									dtbxFecha_control_medicina6.getValue()
											.getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_control_medicina6(null);
				}

				if (dtbxFecha_control_enfermeria1.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_control_enfermeria1(new Timestamp(
									dtbxFecha_control_enfermeria1.getValue()
											.getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_control_enfermeria1(null);
				}

				if (dtbxFecha_control_enfermeria3.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_control_enfermeria3(new Timestamp(
									dtbxFecha_control_enfermeria3.getValue()
											.getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_control_enfermeria3(null);
				}

				if (dtbxFecha_control_enfermeria5.getValue() != null) {
					his_tarjeta_tuberculosis
							.setFecha_control_enfermeria5(new Timestamp(
									dtbxFecha_control_enfermeria5.getValue()
											.getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_control_enfermeria5(null);
				}

				his_tarjeta_tuberculosis.setPeso1(tbxPeso1.getValue());
				his_tarjeta_tuberculosis.setPeso2(tbxPeso2.getValue());
				his_tarjeta_tuberculosis.setPeso3(tbxPeso3.getValue());
				his_tarjeta_tuberculosis.setPeso4(tbxPeso4.getValue());
				his_tarjeta_tuberculosis.setPeso5(tbxPeso5.getValue());
				his_tarjeta_tuberculosis.setPeso6(tbxPeso6.getValue());
				his_tarjeta_tuberculosis.setObservaciones1(tbxObservaciones1
						.getValue());
				his_tarjeta_tuberculosis.setObservaciones2(tbxObservaciones2
						.getValue());
				his_tarjeta_tuberculosis.setObservaciones3(tbxObservaciones3
						.getValue());
				his_tarjeta_tuberculosis.setObservaciones4(tbxObservaciones4
						.getValue());
				his_tarjeta_tuberculosis.setObservaciones5(tbxObservaciones5
						.getValue());
				his_tarjeta_tuberculosis.setObservaciones6(tbxObservaciones6
						.getValue());

				his_tarjeta_tuberculosis
						.setResultado_tratamiento(rdbResultado_tratamiento
								.getSelectedItem().getValue().toString());
				his_tarjeta_tuberculosis.setFracaso(rdbFracaso
						.getSelectedItem().getValue().toString());

				his_tarjeta_tuberculosis.setAbandono(tbxAbandono.getValue());
				his_tarjeta_tuberculosis.setFallecio(tbxFallecio.getValue());
				his_tarjeta_tuberculosis.setTraslado(tbxTraslado.getValue());

				if (dtbxFecha_egreso.getValue() != null) {
					his_tarjeta_tuberculosis.setFecha_egreso(new Timestamp(
							dtbxFecha_egreso.getValue().getTime()));

				} else {
					his_tarjeta_tuberculosis.setFecha_egreso(null);
				}

				his_tarjeta_tuberculosis
						.setObservaciones_tratamiento(tbxObservaciones_tratamiento
								.getValue());
				his_tarjeta_tuberculosis.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				his_tarjeta_tuberculosis.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				his_tarjeta_tuberculosis.setCreacion_user(usuarios.getCodigo()
						.toString());
				his_tarjeta_tuberculosis.setDelete_date(null);
				his_tarjeta_tuberculosis.setUltimo_user(usuarios.getCodigo()
						.toString());
				his_tarjeta_tuberculosis.setDelete_user(null);

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("his_tarjeta_tuberculosis", his_tarjeta_tuberculosis);
				datos.put("accion", tbxAccion.getText());
				datos.put("admision", admision);
				datos.put("listado_componentes_contactos",
						obtenerListadoContactos());
				datos.put("listado_componentes_evaluacion",
						obtenerListadoEvaluacion());

				datos.put("mapa_fases_1", mapa_general_1);
				datos.put("mapa_fases_2", mapa_general_2);

				datos.put("entrega_medicamento",entrega_medicamento);
				
				his_tarjeta_tuberculosisService.guardar(datos);

				tbxAccion.setValue("modificar");
				
				
				Long id_codigo_historia = null;
				if (his_tarjeta_tuberculosis.getCodigo_historia() != null
						&& !his_tarjeta_tuberculosis.getCodigo_historia()
								.trim().isEmpty()
						&& his_tarjeta_tuberculosis.getCodigo_historia()
								.matches("[0-9]*$")) {
					id_codigo_historia = Long.parseLong(his_tarjeta_tuberculosis
						.getCodigo_historia()); 
				}
				infoPacientes.setCodigo_historia(id_codigo_historia);

				MensajesUtil.mensajeInformacion("Informacion...",
						"Los datos se guardaron satisfactoriamente");

			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		His_tarjeta_tuberculosis his_tarjeta_tuberculosis = (His_tarjeta_tuberculosis) obj;
		try {
			
			Long id_codigo_historia = null;
			if (his_tarjeta_tuberculosis.getCodigo_historia() != null
					&& !his_tarjeta_tuberculosis.getCodigo_historia()
							.trim().isEmpty()
					&& his_tarjeta_tuberculosis.getCodigo_historia()
							.matches("[0-9]*$")) {
				id_codigo_historia = Long.parseLong(his_tarjeta_tuberculosis
					.getCodigo_historia()); 
			}
			
			infoPacientes.setCodigo_historia(id_codigo_historia);
			infoPacientes.setFecha_inicio(his_tarjeta_tuberculosis
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					his_tarjeta_tuberculosis.getUltimo_update());

			initMostrar_datos(his_tarjeta_tuberculosis);

			// cargarInformacion_paciente();

			
			tbxDireccion_vivienda.setValue(his_tarjeta_tuberculosis
					.getDireccion_vivienda());
			tbxBarrio_vivienda.setValue(his_tarjeta_tuberculosis
					.getBarrio_vivienda());
			dbxTelefono_vivienda.setValue((his_tarjeta_tuberculosis.getTelefono_vivienda() != null && !his_tarjeta_tuberculosis
					.getTelefono_vivienda().isEmpty()) ? Double
					.parseDouble(his_tarjeta_tuberculosis.getTelefono_vivienda()) : null);
			
			tbxDireccion_trabajo.setValue(his_tarjeta_tuberculosis
					.getDireccion_trabajo());
			tbxBarrio_trabajo.setValue(his_tarjeta_tuberculosis
					.getBarrio_trabajo());
			dbxTelefono_trabajo.setValue((his_tarjeta_tuberculosis.getTelefono_trabajo() != null && !his_tarjeta_tuberculosis
					.getTelefono_trabajo().isEmpty()) ? Double
					.parseDouble(his_tarjeta_tuberculosis.getTelefono_trabajo()) : null);
			
			Utilidades.seleccionarRadio(rdbTipo_paciente,
					his_tarjeta_tuberculosis.getTipo_paciente());
			
			if (his_tarjeta_tuberculosis.getTipo_paciente().equals("E")) {
				rowRemitido.setVisible(true);
				lbIps.setVisible(true);
				tbxIps.setVisible(true);
				tbxIps.setValue(his_tarjeta_tuberculosis.getIps());
				
			} else {
				rowRemitido.setVisible(false);
				lbIps.setVisible(false);
				tbxIps.setVisible(false);

			}
			
			Utilidades.seleccionarRadio(rdbTipo_tuberculosis,
					his_tarjeta_tuberculosis.getTipo_tuberculosis());
			
			if (his_tarjeta_tuberculosis.getTipo_tuberculosis().equals("E")) {
				lbLocalizacion_extrapulmonar.setVisible(true);
				tbxLocalizacion_extrapulmonar.setVisible(true);
				tbxLocalizacion_extrapulmonar.setValue(his_tarjeta_tuberculosis
						.getLocalizacion_extrapulmonar());
			} else {
				lbLocalizacion_extrapulmonar.setVisible(false);
				tbxLocalizacion_extrapulmonar.setVisible(false);

			}
			
			
			chbEnfermedad_renal.setChecked(his_tarjeta_tuberculosis
					.getEnfermedad_renal());
			chbEnfermedad_hepatica.setChecked(his_tarjeta_tuberculosis
					.getEnfermedad_hepatica());
			chbVih_sida.setChecked(his_tarjeta_tuberculosis.getVih_sida());
			chbDiabetes_mellitus.setChecked(his_tarjeta_tuberculosis
					.getDiabetes_mellitus());
			chbSilicosis.setChecked(his_tarjeta_tuberculosis.getSilicosis());
			chbDesnutricion.setChecked(his_tarjeta_tuberculosis
					.getDesnutricion());
			chbAlcoholismo
					.setChecked(his_tarjeta_tuberculosis.getAlcoholismo());
			chbFarmacodependencia.setChecked(his_tarjeta_tuberculosis
					.getFarmacodependencia());
			chbEnbarazo.setChecked(his_tarjeta_tuberculosis.getEnbarazo());
			Utilidades.seleccionarRadio(rdbTerapia_vih,
					his_tarjeta_tuberculosis.getTerapia_vih());
			dbxPeso.setValue((his_tarjeta_tuberculosis.getPeso() != null && !his_tarjeta_tuberculosis
					.getPeso().isEmpty()) ? Double
					.parseDouble(his_tarjeta_tuberculosis.getPeso()) : null);
			dtbxFecha_fase1.setValue(his_tarjeta_tuberculosis.getFecha_fase1());
			dtbxFecha_fase2.setValue(his_tarjeta_tuberculosis.getFecha_fase2());
			dbxRifampicina1.setValue((his_tarjeta_tuberculosis
					.getRifampicina1() != null && !his_tarjeta_tuberculosis
					.getRifampicina1().isEmpty()) ? Double
					.parseDouble(his_tarjeta_tuberculosis.getRifampicina1())
					: null);
			dbxRifampicina2.setValue((his_tarjeta_tuberculosis
					.getRifampicina2() != null && !his_tarjeta_tuberculosis
					.getRifampicina2().isEmpty()) ? Double
					.parseDouble(his_tarjeta_tuberculosis.getRifampicina2())
					: null);
			dbxIsonlacida1
					.setValue((his_tarjeta_tuberculosis.getIsonlacida1() != null && !his_tarjeta_tuberculosis
							.getIsonlacida1().isEmpty()) ? Double
							.parseDouble(his_tarjeta_tuberculosis
									.getIsonlacida1()) : null);
			dbxIsonlacida2
					.setValue((his_tarjeta_tuberculosis.getIsonlacida2() != null && !his_tarjeta_tuberculosis
							.getIsonlacida2().isEmpty()) ? Double
							.parseDouble(his_tarjeta_tuberculosis
									.getIsonlacida2()) : null);
			dbxPirazinamida1.setValue((his_tarjeta_tuberculosis
					.getPirazinamida1() != null && !his_tarjeta_tuberculosis
					.getPirazinamida1().isEmpty()) ? Double
					.parseDouble(his_tarjeta_tuberculosis.getPirazinamida1())
					: null);
			dbxPirazinamida2.setValue((his_tarjeta_tuberculosis
					.getPirazinamida2() != null && !his_tarjeta_tuberculosis
					.getPirazinamida2().isEmpty()) ? Double
					.parseDouble(his_tarjeta_tuberculosis.getPirazinamida2())
					: null);
			dbxEtambutol1
					.setValue((his_tarjeta_tuberculosis.getEtambutol1() != null && !his_tarjeta_tuberculosis
							.getEtambutol1().isEmpty()) ? Double
							.parseDouble(his_tarjeta_tuberculosis
									.getEtambutol1()) : null);
			dbxEtambutol2
					.setValue((his_tarjeta_tuberculosis.getEtambutol2() != null && !his_tarjeta_tuberculosis
							.getEtambutol2().isEmpty()) ? Double
							.parseDouble(his_tarjeta_tuberculosis
									.getEtambutol2()) : null);
			dbxEstreptomicina1.setValue((his_tarjeta_tuberculosis
					.getEstreptomicina1() != null && !his_tarjeta_tuberculosis
					.getEstreptomicina1().isEmpty()) ? Double
					.parseDouble(his_tarjeta_tuberculosis.getEstreptomicina1())
					: null);
			dbxEstreptomicina2.setValue((his_tarjeta_tuberculosis
					.getEstreptomicina2() != null && !his_tarjeta_tuberculosis
					.getEstreptomicina2().isEmpty()) ? Double
					.parseDouble(his_tarjeta_tuberculosis.getEstreptomicina2())
					: null);
			Utilidades.seleccionarRadio(rdbBaciloscopia,
					his_tarjeta_tuberculosis.getBaciloscopia());
			Utilidades.seleccionarRadio(rdbCultivo,
					his_tarjeta_tuberculosis.getCultivo());
			Utilidades.seleccionarRadio(rdbHistopatologia,
					his_tarjeta_tuberculosis.getHistopatologia());
			Utilidades.seleccionarRadio(rdbCuadro_clinico,
					his_tarjeta_tuberculosis.getCuadro_clinico());
			Utilidades.seleccionarRadio(rdbEpidemiologico,
					his_tarjeta_tuberculosis.getEpidemiologico());
			Utilidades.seleccionarRadio(rdbRadiologico,
					his_tarjeta_tuberculosis.getRadiologico());
			Utilidades.seleccionarRadio(rdbTuberculinico,
					his_tarjeta_tuberculosis.getTuberculinico());
			Utilidades.seleccionarRadio(rdbAda,
					his_tarjeta_tuberculosis.getAda());
			tbxBk_1.setValue(his_tarjeta_tuberculosis.getBk_1());
			tbxBk_2.setValue(his_tarjeta_tuberculosis.getBk_2());
			tbxBk_3.setValue(his_tarjeta_tuberculosis.getBk_3());
			dtbxTiempo_sintomas.setValue(his_tarjeta_tuberculosis
					.getTiempo_sintomas());
			dtbxFecha_diagnostico.setValue(his_tarjeta_tuberculosis
					.getFecha_diagnostico());
			dtbxFecha_cultivo.setValue(his_tarjeta_tuberculosis
					.getFecha_cultivo());
			Utilidades.seleccionarRadio(rdbAsesoria_vih,
					his_tarjeta_tuberculosis.getAsesoria_vih());
			Utilidades.seleccionarRadio(rdbConfirmatoria_vih,
					his_tarjeta_tuberculosis.getConfirmatoria_vih());
			dtbxFecha_programada1.setValue(his_tarjeta_tuberculosis
					.getFecha_programada1());
			dtbxFecha_programada2.setValue(his_tarjeta_tuberculosis
					.getFecha_programada2());
			dtbxFecha_programada3.setValue(his_tarjeta_tuberculosis
					.getFecha_programada3());
			dtbxFecha_programada4.setValue(his_tarjeta_tuberculosis
					.getFecha_programada4());
			dtbxFecha_programada5.setValue(his_tarjeta_tuberculosis
					.getFecha_programada5());
			dtbxFecha_programada6.setValue(his_tarjeta_tuberculosis
					.getFecha_programada6());
			dtbxFecha_control_bacteriologico2.setValue(his_tarjeta_tuberculosis
					.getFecha_control_bacteriologico2());
			dtbxFecha_control_bacteriologico4.setValue(his_tarjeta_tuberculosis
					.getFecha_control_bacteriologico4());
			dtbxFecha_control_bacteriologico6.setValue(his_tarjeta_tuberculosis
					.getFecha_control_bacteriologico6());
			tbxResultado_bk2.setValue(his_tarjeta_tuberculosis
					.getResultado_bk2());
			tbxResultado_bk4.setValue(his_tarjeta_tuberculosis
					.getResultado_bk4());
			tbxResultado_bk6.setValue(his_tarjeta_tuberculosis
					.getResultado_bk6());
			dtbxFecha_control_medicina2.setValue(his_tarjeta_tuberculosis
					.getFecha_control_medicina2());
			dtbxFecha_control_medicina4.setValue(his_tarjeta_tuberculosis
					.getFecha_control_medicina4());
			dtbxFecha_control_medicina6.setValue(his_tarjeta_tuberculosis
					.getFecha_control_medicina6());
			dtbxFecha_control_enfermeria1.setValue(his_tarjeta_tuberculosis
					.getFecha_control_enfermeria1());
			dtbxFecha_control_enfermeria3.setValue(his_tarjeta_tuberculosis
					.getFecha_control_enfermeria3());
			dtbxFecha_control_enfermeria5.setValue(his_tarjeta_tuberculosis
					.getFecha_control_enfermeria5());
			tbxPeso1.setValue(his_tarjeta_tuberculosis.getPeso1());
			tbxPeso2.setValue(his_tarjeta_tuberculosis.getPeso2());
			tbxPeso3.setValue(his_tarjeta_tuberculosis.getPeso3());
			tbxPeso4.setValue(his_tarjeta_tuberculosis.getPeso4());
			tbxPeso5.setValue(his_tarjeta_tuberculosis.getPeso5());
			tbxPeso6.setValue(his_tarjeta_tuberculosis.getPeso6());
			tbxObservaciones1.setValue(his_tarjeta_tuberculosis
					.getObservaciones1());
			tbxObservaciones2.setValue(his_tarjeta_tuberculosis
					.getObservaciones2());
			tbxObservaciones3.setValue(his_tarjeta_tuberculosis
					.getObservaciones3());
			tbxObservaciones4.setValue(his_tarjeta_tuberculosis
					.getObservaciones4());
			tbxObservaciones5.setValue(his_tarjeta_tuberculosis
					.getObservaciones5());
			tbxObservaciones6.setValue(his_tarjeta_tuberculosis
					.getObservaciones6());
			
			
			Utilidades.seleccionarRadio(rdbResultado_tratamiento,
					his_tarjeta_tuberculosis.getResultado_tratamiento());
			
			if (his_tarjeta_tuberculosis.getTipo_tuberculosis().equals("FR")) {
				
				rowFracaso.setVisible(true);
				lbFracaso.setVisible(true);
				rdbFracaso.setVisible(true);
				Utilidades.seleccionarRadio(rdbFracaso,his_tarjeta_tuberculosis.getFracaso());
				
			} else if (his_tarjeta_tuberculosis.getTipo_tuberculosis().equals("A")) {
				rowAbandono.setVisible(true);
				lbAbandono.setVisible(true);
				tbxAbandono.setVisible(true);
				tbxAbandono.setValue(his_tarjeta_tuberculosis.getAbandono());
				
			} else if (his_tarjeta_tuberculosis.getTipo_tuberculosis().equals("FA")) {
				rowFallecio.setVisible(true);
				lbFallecio.setVisible(true);
				tbxFallecio.setVisible(true);
				tbxFallecio.setValue(his_tarjeta_tuberculosis.getFallecio());
			} else if (his_tarjeta_tuberculosis.getTipo_tuberculosis().equals("TR")) {
				rowTraslado.setVisible(true);
				lbTraslado.setVisible(true);
				tbxTraslado.setVisible(true);
				tbxTraslado.setValue(his_tarjeta_tuberculosis.getTraslado());
			} else {
				rowFracaso.setVisible(false);
				lbFracaso.setVisible(false);
				rdbFracaso.setVisible(false);
				rowAbandono.setVisible(false);
				lbAbandono.setVisible(false);
				tbxAbandono.setVisible(false);
				rowFallecio.setVisible(false);
				lbFallecio.setVisible(false);
				tbxFallecio.setVisible(false);
				rowTraslado.setVisible(false);
				lbTraslado.setVisible(false);
				tbxTraslado.setVisible(false);
				
			}
			dtbxFecha_egreso.setValue(his_tarjeta_tuberculosis
					.getFecha_egreso());
			tbxObservaciones_tratamiento.setValue(his_tarjeta_tuberculosis
					.getObservaciones_tratamiento());
			
			
			if(his_tarjeta_tuberculosis.getFecha_egreso() != null){
				btnMostrar_resultado.setLabel("Ocultar Evaluacion Tuberculosis");
				rowCondiciones.setVisible(true);
				rowFecha_egreso.setVisible(true);
				rowObservaciones_tratamiento.setVisible(true);
				rowTextoObservacion.setVisible(true);
			} else {
				btnMostrar_resultado.setLabel("Mostrar Evaluacion Tuberculosis");
				rowCondiciones.setVisible(false);
				rowFecha_egreso.setVisible(false);
				rowObservaciones_tratamiento.setVisible(false);
				rowTextoObservacion.setVisible(false);
			}

			cargarHistorialFases(his_tarjeta_tuberculosis, "I");
			cargarHistorialFases(his_tarjeta_tuberculosis, "II");

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		His_tarjeta_tuberculosis his_tarjeta_tuberculosis = (His_tarjeta_tuberculosis) obj;
		try {
			int result = his_tarjeta_tuberculosisService
					.eliminar(his_tarjeta_tuberculosis);
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

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {
			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());
			//log.info("opciones_via_ingreso" + opciones_via_ingreso);

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				His_tarjeta_tuberculosis his_tarjeta_tuberculosis = new His_tarjeta_tuberculosis();
				his_tarjeta_tuberculosis.setCodigo_empresa(empresa
						.getCodigo_empresa());
				his_tarjeta_tuberculosis.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				his_tarjeta_tuberculosis.setIdentificacion(admision
						.getNro_identificacion());

				//log.info("his_tarjeta_tuberculosis - initHistoria "
						//+ his_tarjeta_tuberculosis);

				his_tarjeta_tuberculosis = his_tarjeta_tuberculosisService
						.consultar_historia(his_tarjeta_tuberculosis);

				if (his_tarjeta_tuberculosis != null) {
					accionForm(true, "mostrar");
					mostrarDatos(his_tarjeta_tuberculosis);
					lbxAnio.setSelectedIndex(0);
				} else {
					groupboxEditar.setVisible(false);
					throw new Exception(
							"NO hay una historia clinica relacionada a este paciente admitido");

				}
			} else {
				if (opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
					accionForm(true, "mostrar");
					mostrarHistorialFases(lbxAnio.getSelectedItem().getValue().toString(), "I");
					lbxAnio.setSelectedIndex(0);
					cargarFechasProgramadas();
				} else {
					accionForm(false, "consultar");
				}
			}			
		}
	}

	@Override
	public void inicializarVista(String tipo) {

	}

	@Override
	public void cargarInformacion_paciente() {

		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {
						//log.info("tbxAccion >>>>>>>" + tbxAccion.getValue());

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("identificacion",
									admision.getNro_identificacion());
							parametros.put("order_desc", "order_desc");

							List<His_tarjeta_tuberculosis> listado_historias = his_tarjeta_tuberculosisService
									.listar(parametros);

							//log.info("listado_historias" + listado_historias);

							if (!listado_historias.isEmpty()) {
								// inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								cargarInformacion_historia_anterior(listado_historias
										.get(0));

								toolbarbuttonTipo_historia
										.setLabel("Creando Tarjeta de Tuberculosis");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando Tarjeta de Tuberculosis");
								// inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
							}
						} else {

							His_tarjeta_tuberculosis his_tarjeta_tuberculosis = new His_tarjeta_tuberculosis();
							his_tarjeta_tuberculosis.setCodigo_empresa(empresa
									.getCodigo_empresa());
							his_tarjeta_tuberculosis
									.setCodigo_sucursal(sucursal
											.getCodigo_sucursal());
							his_tarjeta_tuberculosis.setIdentificacion(admision
									.getNro_identificacion());

							his_tarjeta_tuberculosis = his_tarjeta_tuberculosisService
									.consultar_historia(his_tarjeta_tuberculosis);

							if (his_tarjeta_tuberculosis != null) {
								cargarInformacion_historia_anterior(his_tarjeta_tuberculosis);
							}

						}
					}

				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
		try {
			accionForm(true, "modificar");
			mostrarDatos(historia_anterior);

		} catch (Exception e) {
			//  block
			e.printStackTrace();
		}

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		// Psiquiatria psiquiatria = (Psiquiatria) historia_clinica;

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil
					.deshabilitarComponentes(groupboxEditar, true,
							new String[] { "tbxAccion", "btnLimpiar_prestador",
									"tbxValue", "lbxParameter",
									"infoPacientes",
									"toolbarbuttonPaciente_admisionado1",
									"toolbarbuttonPaciente_admisionado2",
									"northEditar"});

			toolbarbuttonTipo_historia
					.setLabel("Mostrando Tarjeta de Tuberculosis");
			btGuardar.setVisible(false);
		} else {

			toolbarbuttonTipo_historia
					.setLabel("Modificando Tarjeta de Tuberculosis");
			btGuardar.setVisible(true);

		}

	}

	public void mostrar_conRadio_egreso(ZKWindow form, Radiogroup radiogroup,
			AbstractComponent[] abstractComponentsFracaso,
			AbstractComponent[] abstractComponentsAbandono,
			AbstractComponent[] abstractComponentsFallecio,
			AbstractComponent[] abstractComponentstraslado) {
		try {
			// String valor = "S";

			Radio r = radiogroup.getSelectedItem();
			String valor = r.getValue();

			if (valor.equalsIgnoreCase("FR")) {

				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFracaso);
				valor = "C";
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsAbandono);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFallecio);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentstraslado);

			} else if (valor.equalsIgnoreCase("A")) {

				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsAbandono);
				valor = "C";
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFracaso);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFallecio);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentstraslado);

			} else if (valor.equalsIgnoreCase("FA")) {

				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFallecio);
				valor = "C";
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsAbandono);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFracaso);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentstraslado);

			} else if (valor.equalsIgnoreCase("TR")) {

				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentstraslado);
				valor = "C";
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsAbandono);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFallecio);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFracaso);

			} else {
				valor = "TR";
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsAbandono);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFallecio);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentsFracaso);
				FormularioUtil.mostrarComponentes_conRadiogroup(form,
						radiogroup, valor, abstractComponentstraslado);

			}

		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void mostrar_conRadio(ZKWindow form, Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			// String valor = "S";

			String valor = "E";
			FormularioUtil.mostrarComponentes_conRadiogroup(form, radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void agregarContacto() {
		final Row fila = new Row();

		Cell celda1 = Utilidades.obtenerCell(new Date(), Datebox.class, false, false);
		celda1.setWidth("90%");
		fila.appendChild(celda1);

		Cell celda2 = Utilidades.obtenerCell("", Textbox.class, false, false);
		fila.appendChild(celda2);

		Cell celda3 = new Cell();
		Intbox intbox = new Intbox();
		intbox.setConstraint("no negative:Este valor no puede ser negativo");
		intbox.setWidth("90%");
		intbox.setMaxlength(3);
		intbox.setStyle("text-transform:uppercase");
		celda3.appendChild(intbox);
		fila.appendChild(celda3);

		Cell celda4 = new Cell();
		Listbox listbox_sexo = new Listbox();
		listbox_sexo.setMold("select");
		listbox_sexo.setWidth("98%");
		listbox_sexo.setSclass("combobox");
		listbox_sexo.appendItem("M", "M");
		listbox_sexo.appendItem("F", "F");
		celda4.appendChild(listbox_sexo);
		fila.appendChild(celda4);

		Cell celda5 = Utilidades.obtenerCell("", Textbox.class, false, false);
		fila.appendChild(celda5);

		Cell celda6 = new Cell();
		Listbox listbox_sr = new Listbox();
		listbox_sr.setMold("select");
		listbox_sr.setWidth("98%");
		listbox_sr.setSclass("combobox");
		listbox_sr.appendItem("Sí", "S");
		listbox_sr.appendItem("NO", "N");
		celda6.appendChild(listbox_sr);
		fila.appendChild(celda6);

		Cell celda7 = new Cell();
		Checkbox checkbox = new Checkbox();
		celda7.appendChild(checkbox);
		fila.appendChild(celda7);

		Cell celda8 = new Cell();
		checkbox = new Checkbox();
		celda8.appendChild(checkbox);
		fila.appendChild(celda8);

		Cell celda9 = new Cell();
		checkbox = new Checkbox();
		celda9.appendChild(checkbox);
		fila.appendChild(celda9);

		Cell celda10 = new Cell();
		checkbox = new Checkbox();
		celda10.appendChild(checkbox);
		fila.appendChild(celda10);

		Cell celda11 = new Cell();
		checkbox = new Checkbox();
		celda11.appendChild(checkbox);
		fila.appendChild(celda11);

		Cell celda12 = new Cell();
		checkbox = new Checkbox();
		celda12.appendChild(checkbox);
		fila.appendChild(celda12);

		Cell celda13 = new Cell();
		checkbox = new Checkbox();
		celda13.appendChild(checkbox);
		fila.appendChild(celda13);

		Cell celda14 = new Cell();
		checkbox = new Checkbox();
		celda14.appendChild(checkbox);
		fila.appendChild(celda14);

		Cell celda15 = Utilidades.obtenerCell("", Textbox.class, false, false);
		fila.appendChild(celda15);

		Toolbarbutton imagen_eliminar = new Toolbarbutton("",
				"/images/borrar.gif");
		imagen_eliminar.addEventListener(Events.ON_CLICK,
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
													fila.detach();
												}
											}
										});

					}
				});
		fila.appendChild(imagen_eliminar);

		gridContactos.getRows().appendChild(fila);
		// numero_filas++;
	}

	public List<His_contactos_tuberculosis> obtenerListadoContactos() {
		List<Component> listado_componentes_contactos = gridContactos.getRows()
				.getChildren();

		List<His_contactos_tuberculosis> listado_contactos = new ArrayList<His_contactos_tuberculosis>();

		// int i = 0;
		for (Component componente : listado_componentes_contactos) {
			His_contactos_tuberculosis his_contactos_tuberculosis = new His_contactos_tuberculosis();
			Row fila = (Row) componente;

			Datebox datebox_fecha_contacto = (Datebox) fila.getChildren().get(0).getFirstChild();
			if (datebox_fecha_contacto.getValue() != null) {
				his_contactos_tuberculosis.setFecha(new Timestamp(datebox_fecha_contacto.getValue().getTime()));

			} else {
				his_contactos_tuberculosis.setFecha(null);

			}

			Textbox textbox_nombre_contacto = (Textbox) fila.getChildren()
					.get(1).getFirstChild();
			his_contactos_tuberculosis
					.setNombre_contacto(textbox_nombre_contacto.getValue());

			Intbox intbox_edad = (Intbox) fila.getChildren().get(2)
					.getFirstChild();
			his_contactos_tuberculosis
					.setEdad(intbox_edad.getValue() != null ? intbox_edad
							.getValue() + "" : "");

			Listbox listbox_sexo = (Listbox) fila
					.getChildren().get(3).getFirstChild();
			his_contactos_tuberculosis.setSexo(""
					+ listbox_sexo.getSelectedItem().getValue().toString());
			
			Textbox textbox_relacion = (Textbox) fila.getChildren().get(4)
					.getFirstChild();
			his_contactos_tuberculosis.setRelacion(textbox_relacion.getValue());

			Listbox listbox_sistematico_respiratorio = (Listbox) fila
					.getChildren().get(5).getFirstChild();
			his_contactos_tuberculosis.setSistomatico_respiratorio(""
					+ listbox_sistematico_respiratorio.getSelectedItem().getValue().toString());

			Checkbox checkbox_bk1 = (Checkbox) fila.getChildren().get(6)
					.getFirstChild();
			his_contactos_tuberculosis.setBk1(checkbox_bk1.isChecked());

			Checkbox checkbox_bk2 = (Checkbox) fila.getChildren().get(7)
					.getFirstChild();
			his_contactos_tuberculosis.setBk2(checkbox_bk2.isChecked());

			Checkbox checkbox_bk3 = (Checkbox) fila.getChildren().get(8)
					.getFirstChild();
			his_contactos_tuberculosis.setBk3(checkbox_bk3.isChecked());

			Checkbox checkbox_cultivo = (Checkbox) fila.getChildren().get(9)
					.getFirstChild();
			his_contactos_tuberculosis.setCultivo(checkbox_cultivo.isChecked());

			Checkbox checkbox_bcg = (Checkbox) fila.getChildren().get(10)
					.getFirstChild();
			his_contactos_tuberculosis.setBcg(checkbox_bcg.isChecked());

			Checkbox checkbox_ppd = (Checkbox) fila.getChildren().get(11)
					.getFirstChild();
			his_contactos_tuberculosis.setPpd(checkbox_ppd.isChecked());

			Checkbox checkbox_rx = (Checkbox) fila.getChildren().get(12)
					.getFirstChild();
			his_contactos_tuberculosis.setRx(checkbox_rx.isChecked());

			Checkbox checkbox_profilaxis = (Checkbox) fila.getChildren()
					.get(13).getFirstChild();
			his_contactos_tuberculosis.setProfilaxis(checkbox_profilaxis
					.isChecked());

			Textbox textbox_seguimiento = (Textbox) fila.getChildren().get(14)
					.getFirstChild();
			his_contactos_tuberculosis.setSeguimiento(textbox_seguimiento
					.getValue());

			listado_contactos.add(his_contactos_tuberculosis);

		}

		return listado_contactos;
	}

	public void mostrarContactos() {

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("identificacion", admision.getNro_identificacion());

		//log.info("parametros" + parametros);

		his_contactos_tuberculosisService.setLimit("limit 25 offset 0");

		List<His_contactos_tuberculosis> listado_contactos = his_contactos_tuberculosisService
				.listar(parametros);

		//log.info("listado_contactos" + listado_contactos);

		if (listado_contactos != null) {
			for (His_contactos_tuberculosis his_contactos_tuberculosis : listado_contactos) {
				final Row fila = new Row();
				// His_contactos_tuberculosis his_contactos_tuberculosis = new
				// His_contactos_tuberculosis();
				
				Cell celda1 = new Cell();
				Datebox datebox_fecha_contacto = new Datebox();
				datebox_fecha_contacto.setValue(his_contactos_tuberculosis
						.getFecha());
				datebox_fecha_contacto.setWidth("90%");
				celda1.appendChild(datebox_fecha_contacto);
				fila.appendChild(celda1);

				Cell celda2 = new Cell();
				Textbox textbox_nombre_contacto = new Textbox();
				textbox_nombre_contacto.setValue(his_contactos_tuberculosis
						.getNombre_contacto());
				celda2.appendChild(textbox_nombre_contacto);
				fila.appendChild(celda2);

				Cell celda3 = new Cell();
				Intbox intbox_edad = new Intbox();
				intbox_edad.setWidth("90%");
				intbox_edad
						.setValue((his_contactos_tuberculosis.getEdad() != null && !his_contactos_tuberculosis
								.getEdad().isEmpty()) ? Integer
								.parseInt(his_contactos_tuberculosis.getEdad())
								: null);
				celda3.appendChild(intbox_edad);
				fila.appendChild(celda3);

				Cell celda4 = new Cell();
				Listbox listbox_sexo = new Listbox();
				listbox_sexo.setMold("select");
				listbox_sexo.setWidth("98%");
				listbox_sexo.setSclass("combobox");
				listbox_sexo.appendItem("M", "M");
				listbox_sexo.appendItem("F", "F");
				Utilidades.seleccionarListitem(listbox_sexo,his_contactos_tuberculosis.getSexo());
				celda4.appendChild(listbox_sexo);
				fila.appendChild(celda4);

				Cell celda5 = new Cell();
				Textbox textbox_relacion = new Textbox();
				textbox_relacion.setWidth("98%");
				textbox_relacion.setValue(his_contactos_tuberculosis
						.getRelacion());
				celda5.appendChild(textbox_relacion);
				fila.appendChild(celda5);

				Cell celda6 = new Cell();
				Listbox listbox_sr = new Listbox();
				listbox_sr.setMold("select");
				listbox_sr.setWidth("98%");
				listbox_sr.setSclass("combobox");
				listbox_sr.appendItem("Sí", "S");
				listbox_sr.appendItem("NO", "N");
				Utilidades.seleccionarListitem(listbox_sr,his_contactos_tuberculosis.getSistomatico_respiratorio());
				//log.info("listbox_sr<<<<<<"+Utilidades.seleccionarListitem(listbox_sr,his_contactos_tuberculosis.getSistomatico_respiratorio()));
				celda6.appendChild(listbox_sr);
				fila.appendChild(celda6);

				Cell celda7 = new Cell();
				Checkbox checkbox_bk1 = new Checkbox();
				checkbox_bk1.setChecked(his_contactos_tuberculosis.getBk1());
				celda7.appendChild(checkbox_bk1);
				fila.appendChild(celda7);

				Cell celda8 = new Cell();
				Checkbox checkbox_bk2 = new Checkbox();
				checkbox_bk2.setChecked(his_contactos_tuberculosis.getBk2());
				celda8.appendChild(checkbox_bk2);
				fila.appendChild(celda8);

				Cell celda9 = new Cell();
				Checkbox checkbox_bk3 = new Checkbox();
				checkbox_bk3.setChecked(his_contactos_tuberculosis.getBk3());
				celda9.appendChild(checkbox_bk3);
				fila.appendChild(celda9);

				Cell celda10 = new Cell();
				Checkbox checkbox_cultivo = new Checkbox();
				checkbox_cultivo.setChecked(his_contactos_tuberculosis
						.getCultivo());
				celda10.appendChild(checkbox_cultivo);
				fila.appendChild(celda10);

				Cell celda11 = new Cell();
				Checkbox checkbox_bcg = new Checkbox();
				checkbox_bcg.setChecked(his_contactos_tuberculosis.getBcg());
				celda11.appendChild(checkbox_bcg);
				fila.appendChild(celda11);

				Cell celda12 = new Cell();
				Checkbox checkbox_ppd = new Checkbox();
				checkbox_ppd.setChecked(his_contactos_tuberculosis.getPpd());
				celda12.appendChild(checkbox_ppd);
				fila.appendChild(celda12);

				Cell celda13 = new Cell();
				Checkbox checkbox_rx = new Checkbox();
				checkbox_rx.setChecked(his_contactos_tuberculosis.getRx());
				celda13.appendChild(checkbox_rx);
				fila.appendChild(celda13);

				Cell celda14 = new Cell();
				Checkbox checkbox_profilaxis = new Checkbox();
				checkbox_profilaxis.setChecked(his_contactos_tuberculosis
						.getProfilaxis());
				celda14.appendChild(checkbox_profilaxis);
				fila.appendChild(celda14);

				Cell celda15 = new Cell();
				Textbox textbox_seguimiento = new Textbox();
				textbox_seguimiento.setValue(his_contactos_tuberculosis
						.getSeguimiento());
				celda15.appendChild(textbox_seguimiento);
				fila.appendChild(celda15);

				Toolbarbutton imagen_eliminar = new Toolbarbutton("",
						"/images/borrar.gif");
				imagen_eliminar.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {
								Messagebox
										.show("Esta seguro que desea eliminar este registro? ",
												"Eliminar Registro",
												Messagebox.YES + Messagebox.NO,
												Messagebox.QUESTION,
												new org.zkoss.zk.ui.event.EventListener<Event>() {
													public void onEvent(
															Event event)
															throws Exception {
														if ("onYes".equals(event
																.getName())) {
															// do the thing
															fila.detach();
															// mostrarContactos();
														}
													}
												});

							}
						});
				
				fila.appendChild(imagen_eliminar);
				
				
				gridContactos.getRows().appendChild(fila);
				// numero_filas++;

			}
		}

	}

	// ------------------- Evaluacion -------------------------------------

	public void agregarEvaluacion() {
		// int numero_filas = gridEvaluacion.getRows().getChildren().size();
		final Row fila = new Row();

		Cell fecha = Utilidades.obtenerCell(new Date(), Datebox.class, false, false);
		fecha.setWidth("90%");
		fila.appendChild(fecha);

		Cell celda1 = new Cell();
		Checkbox checkbox = new Checkbox();
		celda1.appendChild(checkbox);
		fila.appendChild(celda1);

		Cell celda2 = new Cell();
		checkbox = new Checkbox();
		celda2.appendChild(checkbox);
		fila.appendChild(celda2);

		Cell celda3 = new Cell();
		checkbox = new Checkbox();
		celda3.appendChild(checkbox);
		fila.appendChild(celda3);

		Cell celda4 = new Cell();
		checkbox = new Checkbox();
		celda4.appendChild(checkbox);
		fila.appendChild(celda4);

		Cell celda5 = new Cell();
		checkbox = new Checkbox();
		celda5.appendChild(checkbox);
		fila.appendChild(celda5);

		Cell celda6 = new Cell();
		checkbox = new Checkbox();
		celda6.appendChild(checkbox);
		fila.appendChild(celda6);

		Cell celda7 = new Cell();
		checkbox = new Checkbox();
		celda7.appendChild(checkbox);
		fila.appendChild(celda7);

		Cell celda8 = new Cell();
		checkbox = new Checkbox();
		celda8.appendChild(checkbox);
		fila.appendChild(celda8);

		Cell celda9 = new Cell();
		checkbox = new Checkbox();
		celda9.appendChild(checkbox);
		fila.appendChild(celda9);

		Cell celda10 = new Cell();
		checkbox = new Checkbox();
		celda10.appendChild(checkbox);
		fila.appendChild(celda10);

		Cell celda11 = new Cell();
		checkbox = new Checkbox();
		celda11.appendChild(checkbox);
		fila.appendChild(celda11);

		Cell celda12 = new Cell();
		checkbox = new Checkbox();
		celda12.appendChild(checkbox);
		fila.appendChild(celda12);

		Cell celda13 = new Cell();
		checkbox = new Checkbox();
		celda13.appendChild(checkbox);
		fila.appendChild(celda13);

		Cell celda14 = new Cell();
		checkbox = new Checkbox();
		celda14.appendChild(checkbox);
		fila.appendChild(celda14);

		Cell celda15 = new Cell();
		checkbox = new Checkbox();
		celda15.appendChild(checkbox);
		fila.appendChild(celda15);

		Cell celda16 = new Cell();
		checkbox = new Checkbox();
		celda16.appendChild(checkbox);
		fila.appendChild(celda16);

		Cell celda17 = new Cell();
		checkbox = new Checkbox();
		celda17.appendChild(checkbox);
		fila.appendChild(celda17);

		Cell celda18 = new Cell();
		checkbox = new Checkbox();
		celda18.appendChild(checkbox);
		fila.appendChild(celda18);

		Cell celda19 = new Cell();
		checkbox = new Checkbox();
		celda19.appendChild(checkbox);
		fila.appendChild(celda19);

		Cell celda20 = new Cell();
		checkbox = new Checkbox();
		celda20.appendChild(checkbox);
		fila.appendChild(celda20);

		Cell celda21 = new Cell();
		checkbox = new Checkbox();
		celda21.appendChild(checkbox);
		fila.appendChild(celda21);

		Cell celda22 = new Cell();
		checkbox = new Checkbox();
		celda22.appendChild(checkbox);
		fila.appendChild(celda22);

		Cell celda23 = new Cell();
		checkbox = new Checkbox();
		celda23.appendChild(checkbox);
		fila.appendChild(celda23);

		Cell celda24 = new Cell();
		Textbox textbox = new Textbox();
		celda24.appendChild(textbox);
		fila.appendChild(celda24);

		Toolbarbutton imagen_eliminar = new Toolbarbutton("",
				"/images/borrar.gif");
		imagen_eliminar.addEventListener(Events.ON_CLICK,
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
													fila.detach();
												}
											}
										});

					}
				});
		fila.appendChild(imagen_eliminar);

		gridEvaluacion.getRows().appendChild(fila);
	}

	public List<His_evaluacion_tuberculosis> obtenerListadoEvaluacion() {
		List<Component> listado_componentes_evaluacion = gridEvaluacion
				.getRows().getChildren();

		List<His_evaluacion_tuberculosis> listado_evaluacion = new ArrayList<His_evaluacion_tuberculosis>();

		// int i = 0;
		for (Component componente : listado_componentes_evaluacion) {
			His_evaluacion_tuberculosis his_evaluacion_tuberculosis = new His_evaluacion_tuberculosis();
			Row fila = (Row) componente;

			Datebox datebox_fecha_evaluacion = (Datebox) fila.getChildren()
					.get(0).getFirstChild();
			if (datebox_fecha_evaluacion.getValue() != null) {
				his_evaluacion_tuberculosis.setFecha(new Timestamp(
						datebox_fecha_evaluacion.getValue().getTime()));

			} else {
				his_evaluacion_tuberculosis.setFecha(null);

			}
			Checkbox checkbox_tos = (Checkbox) fila.getChildren().get(1)
					.getFirstChild();
			his_evaluacion_tuberculosis.setTos(checkbox_tos.isChecked());

			Checkbox checkbox_expetoracion = (Checkbox) fila.getChildren()
					.get(2).getFirstChild();
			his_evaluacion_tuberculosis.setExpetoracion(checkbox_expetoracion
					.isChecked());

			Checkbox checkbox_anorexia = (Checkbox) fila.getChildren().get(3)
					.getFirstChild();
			his_evaluacion_tuberculosis.setAnorexia(checkbox_anorexia
					.isChecked());

			Checkbox checkbox_adinamia = (Checkbox) fila.getChildren().get(4)
					.getFirstChild();
			his_evaluacion_tuberculosis.setAdinamia(checkbox_adinamia
					.isChecked());

			Checkbox checkbox_disnea = (Checkbox) fila.getChildren().get(5)
					.getFirstChild();
			his_evaluacion_tuberculosis.setDisnea(checkbox_disnea.isChecked());

			Checkbox checkbox_sudoracion = (Checkbox) fila.getChildren().get(6)
					.getFirstChild();
			his_evaluacion_tuberculosis.setSudoracion(checkbox_sudoracion
					.isChecked());

			Checkbox checkbox_hemoptisis = (Checkbox) fila.getChildren().get(7)
					.getFirstChild();
			his_evaluacion_tuberculosis.setHemoptisis(checkbox_hemoptisis
					.isChecked());

			Checkbox checkbox_dolor_torax = (Checkbox) fila.getChildren()
					.get(8).getFirstChild();
			his_evaluacion_tuberculosis.setDolor_torax(checkbox_dolor_torax
					.isChecked());

			Checkbox checkbox_ictericia = (Checkbox) fila.getChildren().get(9)
					.getFirstChild();
			his_evaluacion_tuberculosis.setIctericia(checkbox_ictericia
					.isChecked());

			Checkbox checkbox_dispepsia = (Checkbox) fila.getChildren().get(10)
					.getFirstChild();
			his_evaluacion_tuberculosis.setDispepsia(checkbox_dispepsia
					.isChecked());

			Checkbox checkbox_vomito = (Checkbox) fila.getChildren().get(11)
					.getFirstChild();
			his_evaluacion_tuberculosis.setVomito(checkbox_vomito.isChecked());

			Checkbox checkbox_nauseas = (Checkbox) fila.getChildren().get(12)
					.getFirstChild();
			his_evaluacion_tuberculosis
					.setNauseas(checkbox_nauseas.isChecked());

			Checkbox checkbox_mareo = (Checkbox) fila.getChildren().get(13)
					.getFirstChild();
			his_evaluacion_tuberculosis.setMareo(checkbox_mareo.isChecked());

			Checkbox checkbox_vertigo = (Checkbox) fila.getChildren().get(14)
					.getFirstChild();
			his_evaluacion_tuberculosis
					.setVertigo(checkbox_vertigo.isChecked());

			Checkbox checkbox_hipoacusia = (Checkbox) fila.getChildren()
					.get(15).getFirstChild();
			his_evaluacion_tuberculosis.setHipoacusia(checkbox_hipoacusia
					.isChecked());

			Checkbox checkbox_tinnitus = (Checkbox) fila.getChildren().get(16)
					.getFirstChild();
			his_evaluacion_tuberculosis.setTinnitus(checkbox_tinnitus
					.isChecked());

			Checkbox checkbox_artralgia = (Checkbox) fila.getChildren().get(17)
					.getFirstChild();
			his_evaluacion_tuberculosis.setArtralgia(checkbox_artralgia
					.isChecked());

			Checkbox checkbox_rash = (Checkbox) fila.getChildren().get(18)
					.getFirstChild();
			his_evaluacion_tuberculosis.setRash(checkbox_rash.isChecked());

			Checkbox checkbox_prurito = (Checkbox) fila.getChildren().get(19)
					.getFirstChild();
			his_evaluacion_tuberculosis
					.setPrurito(checkbox_prurito.isChecked());

			Checkbox checkbox_alteraciones_visuales = (Checkbox) fila
					.getChildren().get(20).getFirstChild();
			his_evaluacion_tuberculosis
					.setAlteraciones_visuales(checkbox_alteraciones_visuales
							.isChecked());

			Checkbox checkbox_diarrea = (Checkbox) fila.getChildren().get(21)
					.getFirstChild();
			his_evaluacion_tuberculosis
					.setDiarrea(checkbox_diarrea.isChecked());

			Checkbox checkbox_polineuritis = (Checkbox) fila.getChildren()
					.get(22).getFirstChild();
			his_evaluacion_tuberculosis.setPolineuritis(checkbox_polineuritis
					.isChecked());

			Checkbox checkbox_epigastralgia = (Checkbox) fila.getChildren()
					.get(23).getFirstChild();
			his_evaluacion_tuberculosis.setEpigastralgia(checkbox_epigastralgia
					.isChecked());

			Textbox textbox_observaciones = (Textbox) fila.getChildren()
					.get(24).getFirstChild();
			his_evaluacion_tuberculosis.setObservaciones(textbox_observaciones
					.getValue());

			listado_evaluacion.add(his_evaluacion_tuberculosis);

			// i++;
		}

		return listado_evaluacion;
	}

	public void mostrarEvaluacion() {

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("identificacion", admision.getNro_identificacion());

		//log.info("parametros" + parametros);

		his_evaluacion_tuberculosisService.setLimit("limit 25 offset 0");

		List<His_evaluacion_tuberculosis> listado_evaluacion = his_evaluacion_tuberculosisService
				.listar(parametros);

		//log.info("listado_evaluacion" + listado_evaluacion);

		// int numero_filas = 0;
		if (listado_evaluacion != null) {
			for (His_evaluacion_tuberculosis his_evaluacion_tuberculosis : listado_evaluacion) {
				final Row fila = new Row();
				// His_contactos_tuberculosis his_contactos_tuberculosis = new
				// His_contactos_tuberculosis();

				Cell fecha = new Cell();
				Datebox datebox_fecha_evaluacion = new Datebox();
				datebox_fecha_evaluacion.setValue(his_evaluacion_tuberculosis
						.getFecha());
				datebox_fecha_evaluacion.setWidth("90%");
				fecha.appendChild(datebox_fecha_evaluacion);
				fila.appendChild(fecha);

				Cell celda1 = new Cell();
				Checkbox checkbox_tos = new Checkbox();
				checkbox_tos.setChecked(his_evaluacion_tuberculosis.getTos());
				celda1.appendChild(checkbox_tos);
				fila.appendChild(celda1);

				Cell celda2 = new Cell();
				Checkbox checkbox_expetoracion = new Checkbox();
				checkbox_expetoracion.setChecked(his_evaluacion_tuberculosis
						.getExpetoracion());
				celda2.appendChild(checkbox_expetoracion);
				fila.appendChild(celda2);

				Cell celda3 = new Cell();
				Checkbox checkbox_anorexia = new Checkbox();
				checkbox_anorexia.setChecked(his_evaluacion_tuberculosis
						.getAnorexia());
				celda3.appendChild(checkbox_anorexia);
				fila.appendChild(celda3);

				Cell celda4 = new Cell();
				Checkbox checkbox_adinamia = new Checkbox();
				checkbox_adinamia.setChecked(his_evaluacion_tuberculosis
						.getAdinamia());
				celda4.appendChild(checkbox_adinamia);
				fila.appendChild(celda4);

				Cell celda5 = new Cell();
				Checkbox checkbox_disnea = new Checkbox();
				checkbox_disnea.setChecked(his_evaluacion_tuberculosis
						.getDisnea());
				celda5.appendChild(checkbox_disnea);
				fila.appendChild(celda5);

				Cell celda6 = new Cell();
				Checkbox checkbox_sudoracion = new Checkbox();
				checkbox_sudoracion.setChecked(his_evaluacion_tuberculosis
						.getSudoracion());
				celda6.appendChild(checkbox_sudoracion);
				fila.appendChild(celda6);

				Cell celda7 = new Cell();
				Checkbox checkbox_hemoptisis = new Checkbox();
				checkbox_hemoptisis.setChecked(his_evaluacion_tuberculosis
						.getHemoptisis());
				celda7.appendChild(checkbox_hemoptisis);
				fila.appendChild(celda7);

				Cell celda8 = new Cell();
				Checkbox checkbox_dolor_torax = new Checkbox();
				checkbox_dolor_torax.setChecked(his_evaluacion_tuberculosis
						.getDolor_torax());
				celda8.appendChild(checkbox_dolor_torax);
				fila.appendChild(celda8);

				Cell celda9 = new Cell();
				Checkbox checkbox_ictericia = new Checkbox();
				checkbox_ictericia.setChecked(his_evaluacion_tuberculosis
						.getIctericia());
				celda9.appendChild(checkbox_ictericia);
				fila.appendChild(celda9);

				Cell celda10 = new Cell();
				Checkbox checkbox_dispepsia = new Checkbox();
				checkbox_dispepsia.setChecked(his_evaluacion_tuberculosis
						.getDispepsia());
				celda10.appendChild(checkbox_dispepsia);
				fila.appendChild(celda10);

				Cell celda11 = new Cell();
				Checkbox checkbox_vomito = new Checkbox();
				checkbox_vomito.setChecked(his_evaluacion_tuberculosis
						.getVomito());
				celda11.appendChild(checkbox_vomito);
				fila.appendChild(celda11);

				Cell celda12 = new Cell();
				Checkbox checkbox_nausea = new Checkbox();
				checkbox_nausea.setChecked(his_evaluacion_tuberculosis
						.getNauseas());
				celda12.appendChild(checkbox_nausea);
				fila.appendChild(celda12);

				Cell celda13 = new Cell();
				Checkbox checkbox_mareo = new Checkbox();
				checkbox_mareo.setChecked(his_evaluacion_tuberculosis.getMareo());
				celda13.appendChild(checkbox_mareo);
				fila.appendChild(celda13);

				Cell celda14 = new Cell();
				Checkbox checkbox_vertigo = new Checkbox();
				checkbox_vertigo.setChecked(his_evaluacion_tuberculosis
						.getVertigo());
				celda14.appendChild(checkbox_vertigo);
				fila.appendChild(celda14);

				Cell celda15 = new Cell();
				Checkbox checkbox_hipoacusia = new Checkbox();
				checkbox_hipoacusia.setChecked(his_evaluacion_tuberculosis
						.getHipoacusia());
				celda15.appendChild(checkbox_hipoacusia);
				fila.appendChild(celda15);

				Cell celda16 = new Cell();
				Checkbox checkbox_tinnitus = new Checkbox();
				checkbox_tinnitus.setChecked(his_evaluacion_tuberculosis
						.getTinnitus());
				celda16.appendChild(checkbox_tinnitus);
				fila.appendChild(celda16);

				Cell celda17 = new Cell();
				Checkbox checkbox_artralgia = new Checkbox();
				checkbox_artralgia.setChecked(his_evaluacion_tuberculosis
						.getTinnitus());
				celda17.appendChild(checkbox_artralgia);
				fila.appendChild(celda17);

				Cell celda18 = new Cell();
				Checkbox checkbox_rash = new Checkbox();
				checkbox_rash.setChecked(his_evaluacion_tuberculosis.getRash());
				celda18.appendChild(checkbox_rash);
				fila.appendChild(celda18);

				Cell celda19 = new Cell();
				Checkbox checkbox_prurito = new Checkbox();
				checkbox_prurito.setChecked(his_evaluacion_tuberculosis
						.getPrurito());
				celda19.appendChild(checkbox_prurito);
				fila.appendChild(celda19);

				Cell celda20 = new Cell();
				Checkbox checkbox_alteraciones_visuales = new Checkbox();
				checkbox_alteraciones_visuales
						.setChecked(his_evaluacion_tuberculosis
								.getAlteraciones_visuales());
				celda20.appendChild(checkbox_alteraciones_visuales);
				fila.appendChild(celda20);

				Cell celda21 = new Cell();
				Checkbox checkbox_diarrea = new Checkbox();
				checkbox_diarrea.setChecked(his_evaluacion_tuberculosis
						.getDiarrea());
				celda21.appendChild(checkbox_diarrea);
				fila.appendChild(celda21);

				Cell celda22 = new Cell();
				Checkbox checkbox_polineuritis = new Checkbox();
				checkbox_polineuritis.setChecked(his_evaluacion_tuberculosis
						.getPolineuritis());
				celda22.appendChild(checkbox_polineuritis);
				fila.appendChild(celda22);
				
				Cell celda23 = new Cell();
				Checkbox checkbox_epigastralgia = new Checkbox();
				checkbox_epigastralgia.setChecked(his_evaluacion_tuberculosis
						.getEpigastralgia());
				celda23.appendChild(checkbox_epigastralgia);
				fila.appendChild(celda23);

				Cell celda24 = new Cell();
				Textbox textbox_observaciones = new Textbox();
				textbox_observaciones.setValue(his_evaluacion_tuberculosis
						.getObservaciones());
				celda24.appendChild(textbox_observaciones);
				fila.appendChild(celda24);

				Toolbarbutton imagen_eliminar = new Toolbarbutton("",
						"/images/borrar.gif");
				imagen_eliminar.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {
								Messagebox
										.show("Esta seguro que desea eliminar este registro? ",
												"Eliminar Registro",
												Messagebox.YES + Messagebox.NO,
												Messagebox.QUESTION,
												new org.zkoss.zk.ui.event.EventListener<Event>() {
													public void onEvent(
															Event event)
															throws Exception {
														if ("onYes".equals(event
																.getName())) {
															// do the thing
															fila.detach();
															obtenerListadoEvaluacion();
														}
													}
												});

							}
						});
				
				fila.appendChild(imagen_eliminar);

				gridEvaluacion.getRows().appendChild(fila);
				// numero_filas++;

			}
		}

	}

	public void agregarFase() {
		final Window window = (Window) Executions.createComponents(
				"/pages/his_fases_tuberculosis.zul", this, null);
		final Datebox dtbxFecha = (Datebox) window.getFellow("dtbxFecha");
		final Listbox lbxFase = (Listbox) window.getFellow("lbxFase");
		final Textbox tbxObservacion = (Textbox) window.getFellow("tbxObservacion");
		final Listbox lbxAplicacion = (Listbox) window.getFellow("lbxAplicacion");

		final Toolbarbutton toolbarbutton = (Toolbarbutton) window
				.getFellow("btnGuardar");

		dtbxFecha.addEventListener(Events.ON_CHANGE,
				new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(dtbxFecha.getValue());
					Calendar c2 = Calendar.getInstance();
					c2.setTime(new Date());
					//Si la fecha es mayor que el dia de hoy
					if(c1.after(c2)){
						Clients.showNotification("La fecha de aplicacion no puede ser superior a la fecha actual",
								Clients.NOTIFICATION_TYPE_WARNING, dtbxFecha, "end_before", 1000, true);
						toolbarbutton.setDisabled(true);
					}else{
						toolbarbutton.setDisabled(false);
					}
				}
		});
	
		
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {

						Date date = dtbxFecha.getValue();
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String fecha = dateFormat.format(date);

						Date date2 = dtbxFecha.getValue();
						DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
						String fecha_mes = dateFormat2.format(date2);
						
						//log.info("fecha" + fecha + "..." + fecha_mes);

						String key = fecha_mes + "-";

						His_fases_tuberculosis his_fases_tuberculosis = new His_fases_tuberculosis();
						his_fases_tuberculosis.setCodigo_empresa(empresa.getCodigo_empresa());
						his_fases_tuberculosis.setCodigo_sucursal(sucursal.getCodigo_sucursal());
						his_fases_tuberculosis.setCodigo_historia(infoPacientes.getCodigo_historia() + "");
						his_fases_tuberculosis.setIdentificacion(admision != null ? admision.getNro_identificacion() : null);
						his_fases_tuberculosis.setFecha(fecha);
						his_fases_tuberculosis.setFase(lbxFase.getSelectedItem().getValue().toString());
						his_fases_tuberculosis.setObservacion(tbxObservacion.getValue());
						his_fases_tuberculosis.setAplicacion(lbxAplicacion.getSelectedItem().getValue().toString());
						his_fases_tuberculosis.setCreacion_date(new Timestamp(new Date().getTime()));
						his_fases_tuberculosis.setCreacion_user(usuarios.getCodigo());
						
						Map<String, Map<String, His_fases_tuberculosis>> mapa_general = 
								his_fases_tuberculosis.getFase().equals("I") ? mapa_general_1 : mapa_general_2;

						Integer numero = 1;
						
						for (Map<String, His_fases_tuberculosis> mapa : mapa_general.values()) {
						    numero += mapa.size();
						}
						
						if (mapa_general.containsKey(key)) {
							Map<String, His_fases_tuberculosis> datos = mapa_general.get(key);
							datos.put(fecha, his_fases_tuberculosis);
						} else {
							Map<String, His_fases_tuberculosis> datos = new TreeMap<String, His_fases_tuberculosis>();
							datos.put(fecha, his_fases_tuberculosis);
							mapa_general.put(key, datos);
						}

						mostrarHistorialFases(lbxAnio.getSelectedItem().getValue().toString(), "I");
						mostrarHistorialFases(lbxAnio.getSelectedItem().getValue().toString(), "II");
						
						window.onClose();
					}

				});

		window.setTitle("Tratamiento de Control");
		window.setMode("modal");
	}

	public void cargarHistorialFases(His_tarjeta_tuberculosis his_tarjeta_tuberculosis, String fase) {
		Map<String, Map<String, His_fases_tuberculosis>> mapa_general = fase.equals("I") ? mapa_general_1 : mapa_general_2;
		mapa_general.clear();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_historia", his_tarjeta_tuberculosis.getCodigo_historia());
		parametros.put("fase", fase);

		List<His_fases_tuberculosis> listado_fases = getServiceLocator()
				.getServicio(His_fases_tuberculosisService.class).listar(parametros);
		//log.info(">>>>>"+parametros);
		//log.info(">>>>>"+listado_fases);

		for (His_fases_tuberculosis his_fases_tuberculosis : listado_fases) {
			String fecha_mes = his_fases_tuberculosis.getFecha().substring(0, 8);
			if (mapa_general.containsKey(fecha_mes)) {
				Map<String, His_fases_tuberculosis> datos = (Map<String, His_fases_tuberculosis>) mapa_general.get(fecha_mes);
				datos.put(his_fases_tuberculosis.getFecha(), his_fases_tuberculosis);
				
				if(fase.equalsIgnoreCase("I")){
					mapa_general_1.put(fecha_mes, datos);
				}else{
					mapa_general_2.put(fecha_mes, datos);
				}
				
			} else {
				Map<String, His_fases_tuberculosis> datos = new TreeMap<String, His_fases_tuberculosis>();
				datos.put(his_fases_tuberculosis.getFecha(), his_fases_tuberculosis);
				mapa_general.put(fecha_mes, datos);
				
				if(fase.equalsIgnoreCase("I")){
					mapa_general_1.put(fecha_mes, datos);
				}else{
					mapa_general_2.put(fecha_mes, datos);
				}
			}
		}

		mostrarHistorialFases(lbxAnio.getSelectedItem().getValue().toString(), fase);

	}

	public void mostrarHistorialFases(String anio, String fase) {

		Listbox listboxFase = fase.equals("I") ? listboxFase1 : listboxFase2;
		Map<String, Map<String, His_fases_tuberculosis>> mapa_general = fase
				.equals("I") ? mapa_general_1 : mapa_general_2;

		listboxFase.getItems().clear();
		
		Integer tratamientos = 0;
		
		for (int i = 0; i < 12; i++) {
			int mes = (i + 1);
			String mes_string = "0" + mes;
			if (mes > 9)
				mes_string = "" + mes;

			String llave_mes = anio + "-" + mes_string + "-";

			final Map<String, His_fases_tuberculosis> mapa_mes = mapa_general
					.get(llave_mes);

			if (mapa_mes != null) {
				Listitem listitem = new Listitem();
				Listcell listcell_aux = new Listcell(CampoMesMacro.meses[i]);
				listcell_aux.setValue(mes_string);
				listitem.appendChild(listcell_aux);
				for (int j = 0; j < 31; j++) {
					int dia = (j + 1);
					String dia_string = "0" + dia;

					if (dia > 9)
						dia_string = "" + dia;

					listcell_aux = new Listcell();

					if (mapa_mes != null
							&& mapa_mes
									.containsKey(llave_mes + "" + dia_string)) {

						final His_fases_tuberculosis his_fases_tuberculosis = mapa_mes
								.get(llave_mes + "" + dia_string);

							tratamientos ++;
						
						
						//listcell_aux.setLabel("X");
						listcell_aux.setLabel(tratamientos.toString());
						listcell_aux.setStyle("cursor:pointer;font-weight:bold");
						
						listcell_aux.addEventListener(Events.ON_CLICK,
								new EventListener<Event>() {

									@Override
									public void onEvent(Event arg0)
											throws Exception {
										final Window window = (Window) Executions
												.createComponents(
														"/pages/his_fases_tuberculosis.zul",
														His_tarjeta_tuberculosisAction.this,
														null);
										final Datebox dtbxFecha = (Datebox) window.getFellow("dtbxFecha");
										final Listbox lbxFase = (Listbox) window.getFellow("lbxFase");
										final Textbox tbxObservacion = (Textbox) window.getFellow("tbxObservacion");
										final Listbox lbxAplicacion = (Listbox) window.getFellow("lbxAplicacion");

										Toolbarbutton toolbarbutton = (Toolbarbutton) window.getFellow("btnGuardar");
										final Toolbarbutton toolbarbuttonEliminar = (Toolbarbutton) window.getFellow("btnEliminar");
										toolbarbutton.setVisible(false);
										toolbarbuttonEliminar.setVisible(true);
										//dtbxFecha.setText(his_fases_tuberculosis.getFecha());
										
										SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
										Date d = ff.parse(his_fases_tuberculosis.getFecha());
										dtbxFecha.setValue(d);
										
										Utilidades.seleccionarListitem(lbxFase,	his_fases_tuberculosis.getFase());
										Utilidades.seleccionarListitem(lbxAplicacion, his_fases_tuberculosis.getAplicacion()); 
										tbxObservacion.setValue(his_fases_tuberculosis.getObservacion());
										dtbxFecha.setDisabled(true);
										lbxFase.setDisabled(true);
										lbxAplicacion.setDisabled(true);
										tbxObservacion.setReadonly(true);
										
										String hoy = ff.format(new Date());
										String creacion = ff.format(new Date(his_fases_tuberculosis.getCreacion_date().getTime()));
										
										//Deshabilita el eliminado del tratamiento si no se realiza el mismo dia
										if(!hoy.equals(creacion)){
											toolbarbuttonEliminar.setVisible(false);
										}else{
											toolbarbuttonEliminar.setVisible(true);
										}
										
										toolbarbuttonEliminar.addEventListener(
												Events.ON_CLICK,
												new EventListener<Event>() {

													@Override
													public void onEvent(
															Event arg0)
															throws Exception {
														mapa_mes.remove(his_fases_tuberculosis
																.getFecha());
														mostrarHistorialFases(lbxAnio.getSelectedItem().getValue().toString(), "I");
														mostrarHistorialFases(lbxAnio.getSelectedItem().getValue().toString(), "II");
														window.onClose();
													}

												});
										window.doModal();

									}

								});
					}

					listitem.appendChild(listcell_aux);
				}
				listboxFase.appendChild(listitem);
			}
		}

	}

	public void mostrarHistoriales(){
		mostrarHistorialFases(
				lbxAnio.getSelectedItem()
						.getValue()
						.toString(),
				"I");
		mostrarHistorialFases(
				lbxAnio.getSelectedItem()
						.getValue()
						.toString(),
				"II");
	}
	
	public void onMostrarResultado() {
		Boolean visible = rowCondiciones.isVisible();
		if (!visible) {
			btnMostrar_resultado.setLabel("Ocultar Evaluacion Tuberculosis");
			rowCondiciones.setVisible(true);
			rowFecha_egreso.setVisible(true);
			rowObservaciones_tratamiento.setVisible(true);
			rowTextoObservacion.setVisible(true);
		} else {
			btnMostrar_resultado.setLabel("Mostrar Evaluacion Tuberculosis");
			rowCondiciones.setVisible(false);
			rowFecha_egreso.setVisible(false);
			rowObservaciones_tratamiento.setVisible(false);
			rowTextoObservacion.setVisible(false);
		}
	}

	public void calcularFase2(){
		Date fase1 = dtbxFecha_fase1.getValue();
		if(fase1!=null){
			Calendar fase2c = Calendar.getInstance();
			fase2c.setTime(fase1);
			fase2c.set(Calendar.WEEK_OF_YEAR, fase2c.get(Calendar.WEEK_OF_YEAR) + 8);
			dtbxFecha_fase2.setValue(fase2c.getTime());
		}
	}
	public void cargarFechasProgramadas(){
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(new Date());
		dtbxFecha_programada1.setValue(fecha.getTime());
		fecha.set(Calendar.MONTH, fecha.get(Calendar.MONTH) + 1);
		dtbxFecha_programada2.setValue(fecha.getTime());
		fecha.set(Calendar.MONTH, fecha.get(Calendar.MONTH) + 1);
		dtbxFecha_programada3.setValue(fecha.getTime());
		fecha.set(Calendar.MONTH, fecha.get(Calendar.MONTH) + 1);
		dtbxFecha_programada4.setValue(fecha.getTime());
		fecha.set(Calendar.MONTH, fecha.get(Calendar.MONTH) + 1);
		dtbxFecha_programada5.setValue(fecha.getTime());
		fecha.set(Calendar.MONTH, fecha.get(Calendar.MONTH) + 1);
		dtbxFecha_programada6.setValue(fecha.getTime());		
	}
	
	private void cambiarVistaMedicamentos(Boolean medicamento){
		rowDatosAdicionales.setVisible(!medicamento);
		rowCondicionIngreso.setVisible(!medicamento);
		rowTratamiento.setVisible(!medicamento);
		rowDiagnostico.setVisible(!medicamento);
		rowControlContactos.setVisible(!medicamento);
		//rowFasesTratamiento.setVisible(!medicamento);
		rowControlesProgramados.setVisible(!medicamento);
		rowEvolucion.setVisible(!medicamento);
		rowResultado.setVisible(!medicamento);
	}
}