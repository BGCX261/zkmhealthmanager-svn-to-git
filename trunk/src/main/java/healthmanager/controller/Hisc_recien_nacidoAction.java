/*
 * hisc_recien_nacidoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Coordenadas_graficas;
import healthmanager.modelo.bean.Hisc_recien_nacido;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.service.Hisc_recien_nacidoService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IHistorias_clinicas;
import com.framework.constantes.ITipos_coordenada;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.CapurroMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.CalculadorUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.GraficasCalculadorUtils;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Hisc_recien_nacidoAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Hisc_recien_nacidoAction.class);

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
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	@View
	private Toolbarbutton btnCancelar;
	private String tipo_historia;
	private Long codigo_historia_anterior;

	private Integer subtotalNum1 = 0;
	@View
	private Grid gridPuntaje_apgar;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	private String via_ingreso = "1";
	private Opciones_via_ingreso opciones_via_ingreso;
	private Admision admision_seleccionada;
	private Citas cita;
	private Paciente paciente;
	private ESexo sexo;
	private Timestamp fecha;
	@View
	private ZKWindow formHisc_recien_nacido;
	private Hisc_recien_nacido hisc_recien_nacido;
	@View
	private Textbox tbxNombre_padre;
	@View
	private Intbox ibxTelefono;
	@View
	private Textbox tbxAnte_patologicos_materno;
	@View
	private Textbox tbxAnte_patologicos_paternos;
	@View
	private Intbox ibxPerinatales_g;
	@View
	private Intbox ibxPerinatales_p;
	@View
	private Intbox ibxPerinatales_c;
	@View
	private Intbox ibxPerinatales_a;
	@View
	private Intbox ibxPerinatales_v;
	// @View private Textbox tbxProducto_del_embarazo;
	// @View private Textbox tbxNombre_madre;
	// @View private Intbox ibxEdad_madre;
	@View
	private Textbox tbxComp_embarazo;
	@View
	private Textbox tbxComp_trabajo_parto;
	@View
	private Textbox tbxComp_del_parto;
	@View
	private Radiogroup rdbReanimacion;
	@View
	private Radiogroup rdbEdad_gestacional;
	@View
	private Radiogroup rdbFrecue_rep0;
	@View
	private Intbox ibxFrecue_rep1_min;
	@View
	private Intbox ibxFrecue_rep5_min;
	@View
	private Intbox ibxFrecue_rep10_min;
	@View
	private Radiogroup rdbFrecue_card0;
	@View
	private Intbox ibxFrecue_card1_min;
	@View
	private Intbox ibxFrecue_card5_min;
	@View
	private Intbox ibxFrecue_card10_min;
	@View
	private Radiogroup rdbMuscular0;
	@View
	private Intbox ibxMuscular1_min;
	@View
	private Intbox ibxMuscular5_min;
	@View
	private Intbox ibxMuscular10_min;
	@View
	private Radiogroup rdbReflejo0;
	@View
	private Intbox ibxReflejo1_min;
	@View
	private Intbox ibxReflejo5_min;
	@View
	private Intbox ibxReflejo10_min;
	@View
	private Radiogroup rdbColor_piel0;
	@View
	private Intbox ibxColor_piel1_min;
	@View
	private Intbox ibxColor_piel5_min;
	@View
	private Intbox ibxColor_piel0_min;
	@View
	private Textbox tbxResultado_apgar1;
	@View
	private Textbox tbxResultado_apgar5;
	@View
	private Textbox tbxResultado_apgar10;
	@View
	private Radiogroup rdbBcg;
	@View
	private Radiogroup rdbHep_b1;
	@View
	private Radiogroup rdbVitamina_k;
	@View
	private Doublebox dbxExamen_fisico_peso;
	@View
	private Doublebox dbxExamen_fisico_talla;
	@View
	private Doublebox dbxExamen_fisico_perimetro_cflico;
	@View
	private Doublebox dbxImc;
	@View
	private Doublebox dbxExamen_fisico_fc;
	@View
	private Doublebox dbxExamen_fisico_fr;
	@View
	private Doublebox dbxExamen_fisico_temp;
	@View
	private Doublebox dbxP_t;
	@View
	private Doublebox dbxDesviacion_estandar1;
	@View
	private Doublebox dbxDesviacion_estandar2;
	@View
	private Doublebox dbxDesviacion_estandar3;
	@View
	private Doublebox dbxDesviacion_estandar4;
	@View
	private Radiogroup rdbSintomaticos_respiratorio;
	@View
	private Radiogroup rdbSintomaticos_piel;
	@View
	private Textbox tbxPiel;
	@View
	private Listbox lbxPiel_faneras_list;
	@View
	private Textbox tbxCabeza_cara;
	@View
	private Listbox lbxCabezacara_list;
	@View
	private Textbox tbxCuello;
	@View
	private Listbox lbxCuello_list;
	@View
	private Textbox tbxTorax_pulmonares;
	@View
	private Listbox lbxTorax_list;
	@View
	private Textbox tbxCorazon_vascular;
	@View
	private Listbox lbxVascular_list;
	@View
	private Textbox tbxAbdomen;
	@View
	private Listbox lbxAdomen_list;
	@View
	private Textbox tbxGenitales;
	@View
	private Listbox lbxGenitales_list;
	@View
	private Textbox tbxAno;
	@View
	private Listbox lbxAno_list;
	@View
	private Textbox tbxSistema_muscular;
	@View
	private Listbox lbxMuscular_list;
	@View
	private Textbox tbxNeurologico;
	@View
	private Listbox lbxNeurologico_list;
	@View
	private Textbox tbxConducta;
	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;

	// @View private Button btnCalcularPerimetroCefalicoEdad;
	// @View private Button btnCalcularPesoTalla;
	// @View private Button btnCalcularTallaEdad;
	// @View private Button btnCalcularImcEdad;

	private RespuestaInt coordenadaTallaEdad;
	private RespuestaInt coordenadaPesoTalla;
	private RespuestaInt coordenadaIMCEdad;
	private RespuestaInt coordenadaPerimetroCefalicoEdad;

	private List coordenadasTE;
	private List coordenadasPT;
	private List coordenadasIE;
	private List coordenadasPCE;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	@View
	private Div divModuloFarmacologico;
	@View
	private Div divModuloOrdenamiento;
	private Receta_rips_internoAction receta_ripsAction;
	private Orden_servicio_internoAction orden_servicioAction;

	@View
	private Radio rdbMinuto1;
	@View
	private Radio rdbMinuto5;
	@View
	private Radio rdbMinuto10;

	@View(isMacro = true)
	private CapurroMacro macroCapurro;

	private final String[] idsExcluyentes = { "tbxAccion", "tbxValue", "lbxParameter",
			"northEditar", "lbxCabezacara_list",
			"lbxGenitales_list", "lbxTorax_list", "lbxCuello_list",
			"lbxAdomen_list", "lbxAno_list", "lbxMuscular_list",
			"lbxVascular_list", "lbxPiel_faneras_list", "lbxNeurologico_list",
			"toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio" };

	@Override
	public void init() throws Exception {
		listarCombos();
		macroImpresion_diagnostica.inicializarMacro(this,
				admision_seleccionada,
				IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO);
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision_seleccionada = (Admision) map
					.get(IVias_ingreso.ADMISION_PACIENTE);
			cita = (Citas) map
					.get(IVias_ingreso.CITA_PACIENTE);
			paciente = admision_seleccionada.getPaciente();
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO
					: ESexo.MASCULINO);
			fecha = paciente.getFecha_nacimiento();
			if (map.containsKey("via_ingreso")) {
				via_ingreso = (String) map.get("via_ingreso");
			}
		}
	}

	public void listarCombos() {
		listarParameter();
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

	public void accionForm(OpcionesFormulario opciones_formulario, Object dato)
			throws Exception {
		tbxAccion.setValue(opciones_formulario.toString());
		if (opciones_formulario.equals(OpcionesFormulario.REGISTRAR)) {
			onOpcionFormularioRegistrar();
		} else if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)
				|| opciones_formulario.equals(OpcionesFormulario.MOSTRAR)) {
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
			mostrarDatos(dato);
			if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)) {
				FormularioUtil.deshabilitarComponentes(groupboxEditar, false,
						idsExcluyentes);
				lbxCabezacara_list.setDisabled(false);
				lbxPiel_faneras_list.setDisabled(false);
				lbxTorax_list.setDisabled(false);
				lbxCuello_list.setDisabled(false);
				lbxAdomen_list.setDisabled(false);
				lbxGenitales_list.setDisabled(false);
				lbxAno_list.setDisabled(false);
				lbxMuscular_list.setDisabled(false);
				lbxVascular_list.setDisabled(false);
				lbxNeurologico_list.setDisabled(false);
			}
		} else if (opciones_formulario.equals(OpcionesFormulario.CONSULTAR)) {
			onOpcionFormularioConsultar();
		}

	}

	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsulta.setVisible(false);
		groupboxEditar.setVisible(true);
		limpiarDatos();
		if (admision_seleccionada != null) {
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(true);
			onMostrarModuloRemisiones();
		}
	}

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		 infoPacientes.validarInformacionPaciente();
		FormularioUtil.validarCamposObligatorios(dbxExamen_fisico_peso,
				dbxExamen_fisico_talla,
				dbxExamen_fisico_perimetro_cflico,
				dbxExamen_fisico_fc,
				dbxExamen_fisico_fr,
				dbxExamen_fisico_temp,
				dbxP_t);
		

		boolean valida = receta_ripsAction.validarFormExterno();
		// String mensaje = "";
		if (valida)
			valida = orden_servicioAction.validarFormExterno();

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (valida)
			valida = remisiones_externasAction.validarRemision();
		
		if (macroCapurro.getSemanas() == null){
			valida = false;
			Clients.scrollIntoView(macroCapurro);
			mensaje = "Debe completar el test de madurez!";
		}
		
		
		if (!valida) {
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("tipo_area_asistencial", via_ingreso);

			if (admision_seleccionada != null) {
				parameters.put("nro_identificacion",
						admision_seleccionada.getNro_identificacion());
			}

			if (parameter.equalsIgnoreCase("fecha_inicial")) {
				parameters.put("fecha_string", value);
			} else {
				parameters.put("parameter", parameter);
				parameters.put("value", "%" + value + "%");
			}
			getServiceLocator().getServicio(Hisc_recien_nacidoService.class)
					.setLimit("limit 25 offset 0");
			List<Hisc_recien_nacido> lista_datos = getServiceLocator()
					.getServicio(Hisc_recien_nacidoService.class).listar(
							parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_recien_nacido hisc_recien_nacido : lista_datos) {
				rowsResultado.appendChild(crearFilas(hisc_recien_nacido, this));
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

		final Hisc_recien_nacido hisc_recien_nacido = (Hisc_recien_nacido) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_recien_nacido.getCodigo_historia() + ""));
		fila.appendChild(new Label(hisc_recien_nacido.getIdentificacion() + ""));

		fila.appendChild(new Label(
				admision_seleccionada.getPaciente().getNombre1()
						+ (admision_seleccionada.getPaciente().getNombre2()
								.isEmpty() ? "" : " "
								+ admision_seleccionada.getPaciente()
										.getNombre2()) + " "
						+ admision_seleccionada.getPaciente().getApellido1()
						+ " "
						+ admision_seleccionada.getPaciente().getApellido2()
						+ ""));

		Datebox datebox = new Datebox(hisc_recien_nacido.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		datebox.setInplace(true);
		fila.appendChild(datebox);

		Hlayout hlayout = new Hlayout();

		Toolbarbutton btn_mostrar = new Toolbarbutton();
		btn_mostrar.setImage("/images/mostrar_info.png");
		btn_mostrar.setTooltiptext("Mostrar historia Recien Nacido");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.setLabel("Mostrar");
		btn_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(hisc_recien_nacido);
					}
				});
		hlayout.appendChild(btn_mostrar);

		btn_mostrar = new Toolbarbutton();
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			btn_mostrar.setVisible(false);
		}
		btn_mostrar.setImage("/images/borrar.gif");
		btn_mostrar.setTooltiptext("Eliminar");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.addEventListener(Events.ON_CLICK,
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
													eliminarDatos(hisc_recien_nacido);
													buscarDatos();
												}
											}
										});
					}
				});
		hlayout.appendChild(new Space());
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				Hisc_recien_nacido hisc_recien_nacido = new Hisc_recien_nacido();
				hisc_recien_nacido.setCodigo_empresa(empresa
						.getCodigo_empresa());
				hisc_recien_nacido.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				hisc_recien_nacido.setCodigo_historia(infoPacientes
						.getCodigo_historia());
				hisc_recien_nacido
						.setCodigo_historia_anterior(codigo_historia_anterior);
				hisc_recien_nacido.setUltimo_update(new Timestamp((new Date())
						.getTime()));
				hisc_recien_nacido
						.setNro_ingreso(admision_seleccionada != null ? admision_seleccionada
								.getNro_ingreso() : null);
				hisc_recien_nacido.setTipo_historia(tipo_historia);
				hisc_recien_nacido.setVia_ingreso(via_ingreso);

				hisc_recien_nacido
						.setIdentificacion(admision_seleccionada != null ? admision_seleccionada
								.getNro_identificacion() : null);
				hisc_recien_nacido.setFecha_inicial(new Timestamp(infoPacientes
						.getFecha_inicial().getTime()));
				hisc_recien_nacido.setNombre_padre(tbxNombre_padre.getValue());
				hisc_recien_nacido
						.setTelefono((ibxTelefono.getValue() != null ? ibxTelefono
								.getValue() + ""
								: ""));
				hisc_recien_nacido
						.setAnte_patologicos_materno(tbxAnte_patologicos_materno
								.getValue());
				hisc_recien_nacido
						.setAnte_patologicos_paternos(tbxAnte_patologicos_paternos
								.getValue());
				hisc_recien_nacido.setPerinatales_g((ibxPerinatales_g
						.getValue() != null ? ibxPerinatales_g.getValue() : 0));
				hisc_recien_nacido.setPerinatales_p((ibxPerinatales_p
						.getValue() != null ? ibxPerinatales_p.getValue() : 0));
				hisc_recien_nacido.setPerinatales_c((ibxPerinatales_c
						.getValue() != null ? ibxPerinatales_c.getValue() : 0));
				hisc_recien_nacido.setPerinatales_a((ibxPerinatales_a
						.getValue() != null ? ibxPerinatales_a.getValue() : 0));
				hisc_recien_nacido.setPerinatales_v((ibxPerinatales_v
						.getValue() != null ? ibxPerinatales_v.getValue() : 0));
				hisc_recien_nacido.setPeso(dbxExamen_fisico_peso.getValue());
				hisc_recien_nacido
						.setTalla(dbxExamen_fisico_talla.getValue() != null ? dbxExamen_fisico_talla
								.getValue() : 0d);
				hisc_recien_nacido
						.setPerimetro_cefalico(dbxExamen_fisico_perimetro_cflico
								.getValue() != null ? dbxExamen_fisico_perimetro_cflico
								.getValue() : 0d);
				hisc_recien_nacido.setFrec_cardiaca(dbxExamen_fisico_fc
						.getValue() != null ? dbxExamen_fisico_fc.getValue()
						: 0d);
				hisc_recien_nacido.setFrec_respiratoria(dbxExamen_fisico_fr
						.getValue() != null ? dbxExamen_fisico_fr.getValue()
						: 0d);
				hisc_recien_nacido.setTemperatura(dbxExamen_fisico_temp
						.getValue());
				hisc_recien_nacido.setImc(dbxImc.getValue());
				hisc_recien_nacido.setP_t(dbxP_t.getValue());
				// hisc_recien_nacido.setProducto_del_embarazo(tbxProducto_del_embarazo.getValue());
				// hisc_recien_nacido.setNombre_madre(tbxNombre_madre.getValue());
				// hisc_recien_nacido.setEdad_madre((ibxEdad_madre.getValue()!=null?ibxEdad_madre.getValue():0));
				hisc_recien_nacido
						.setComp_embarazo(tbxComp_embarazo.getValue());
				hisc_recien_nacido.setComp_trabajo_parto(tbxComp_trabajo_parto
						.getValue());
				hisc_recien_nacido.setComp_del_parto(tbxComp_del_parto
						.getValue());
				hisc_recien_nacido.setReanimacion(rdbReanimacion
						.getSelectedItem().getValue().toString());
				if(rdbFrecue_rep0.getSelectedItem()!=null){
					hisc_recien_nacido.setFrecue_rep0(rdbFrecue_rep0.getSelectedItem().getValue().toString());
				}
				if(rdbEdad_gestacional.getSelectedItem()!=null){
					hisc_recien_nacido.setMin1(rdbEdad_gestacional.getSelectedItem().getValue().toString());
				}
				hisc_recien_nacido.setFrecue_rep1_min(ibxFrecue_rep1_min
						.getText());
				hisc_recien_nacido.setFrecue_rep5_min(ibxFrecue_rep5_min
						.getText());
				hisc_recien_nacido.setFrecue_rep10_min(ibxFrecue_rep10_min
						.getText());
				if(rdbFrecue_card0.getSelectedItem()!=null){
					hisc_recien_nacido.setFrecue_card0(rdbFrecue_card0
						.getSelectedItem().getValue().toString());
				}
				hisc_recien_nacido.setFrecue_card1_min(ibxFrecue_card1_min
						.getText());
				hisc_recien_nacido.setFrecue_card5_min(ibxFrecue_card5_min
						.getText());
				hisc_recien_nacido.setFrecue_card10_min(ibxFrecue_card10_min
						.getText());
				if(rdbMuscular0.getSelectedItem()!=null){
					hisc_recien_nacido.setMuscular0(rdbMuscular0.getSelectedItem()
						.getValue().toString());
				}
				hisc_recien_nacido.setMuscular1_min(ibxMuscular1_min.getText());
				hisc_recien_nacido.setMuscular5_min(ibxMuscular5_min.getText());
				hisc_recien_nacido.setMuscular10_min(ibxMuscular10_min
						.getText());
				if(rdbMuscular0.getSelectedItem()!=null){
					hisc_recien_nacido.setReflejo0(rdbReflejo0.getSelectedItem()
							.getValue().toString());
				}
				hisc_recien_nacido.setReflejo1_min(ibxReflejo1_min.getText());
				hisc_recien_nacido.setReflejo5_min(ibxReflejo5_min.getText());
				hisc_recien_nacido.setReflejo10_min(ibxReflejo10_min.getText());
				if(rdbColor_piel0.getSelectedItem()!=null){
				hisc_recien_nacido.setColor_piel0(rdbColor_piel0
						.getSelectedItem().getValue().toString());
				}
				hisc_recien_nacido.setColor_piel1_min(ibxColor_piel1_min
						.getText());
				hisc_recien_nacido.setColor_piel5_min(ibxColor_piel5_min
						.getText());
				hisc_recien_nacido.setColor_piel0_min(ibxColor_piel0_min
						.getText());
				hisc_recien_nacido.setResultado_apgar(tbxResultado_apgar1
						.getValue());// arreglar este
				// metodo
				hisc_recien_nacido.setResultado_apgar5(tbxResultado_apgar5
						.getValue());
				hisc_recien_nacido.setResultado_apgar10(tbxResultado_apgar10
						.getValue());
				if(rdbBcg.getSelectedItem()!=null){
				hisc_recien_nacido.setBcg(rdbBcg.getSelectedItem().getValue()
						.toString());
				}
				if(rdbHep_b1.getSelectedItem()!=null){
				hisc_recien_nacido.setHep_b1(rdbHep_b1.getSelectedItem()
						.getValue().toString());
				}
				if(rdbVitamina_k.getSelectedItem()!=null){
				hisc_recien_nacido.setVitamina_k(rdbVitamina_k
						.getSelectedItem().getValue().toString());
				}
				
				if(rdbSintomaticos_respiratorio.getSelectedItem()!=null){
				hisc_recien_nacido
						.setSintomaticos_respiratorios(rdbSintomaticos_respiratorio
								.getSelectedItem().getValue().toString());
				}
				
				if(rdbSintomaticos_piel.getSelectedItem()!=null){
				hisc_recien_nacido.setSintomaticos_piel(rdbSintomaticos_piel
						.getSelectedItem().getValue().toString());
				}
				
				hisc_recien_nacido
						.setDesviacion_estandar1(dbxDesviacion_estandar1
								.getValue());
				hisc_recien_nacido
						.setDesviacion_estandar2(dbxDesviacion_estandar2
								.getValue());
				hisc_recien_nacido
						.setDesviacion_estandar3(dbxDesviacion_estandar3
								.getValue());
				hisc_recien_nacido
						.setDesviacion_estandar4(dbxDesviacion_estandar4
								.getValue());
				hisc_recien_nacido.setPiel(tbxPiel.getValue());
				hisc_recien_nacido.setPiel_faneras_list(lbxPiel_faneras_list
						.getSelectedItem().getValue().toString());
				hisc_recien_nacido.setCabeza_cara(tbxCabeza_cara.getValue());
				hisc_recien_nacido.setCabezacara_list(lbxCabezacara_list
						.getSelectedItem().getValue().toString());
				hisc_recien_nacido.setCuello(tbxCuello.getValue());
				hisc_recien_nacido.setCuello_list(lbxCuello_list
						.getSelectedItem().getValue().toString());
				hisc_recien_nacido.setTorax_pulmonares(tbxTorax_pulmonares
						.getValue());
				hisc_recien_nacido.setTorax_list(lbxTorax_list
						.getSelectedItem().getValue().toString());
				hisc_recien_nacido.setCorazon_vascular(tbxCorazon_vascular
						.getValue());
				hisc_recien_nacido.setVascular_list(lbxVascular_list
						.getSelectedItem().getValue().toString());
				hisc_recien_nacido.setAbdomen(tbxAbdomen.getValue());
				hisc_recien_nacido.setAdomen_list(lbxAdomen_list
						.getSelectedItem().getValue().toString());
				hisc_recien_nacido.setGenitales(tbxGenitales.getValue());
				hisc_recien_nacido.setGenitales_list(lbxGenitales_list
						.getSelectedItem().getValue().toString());
				hisc_recien_nacido.setAno(tbxAno.getValue());
				hisc_recien_nacido.setAno_list(lbxAno_list.getSelectedItem()
						.getValue().toString());
				hisc_recien_nacido.setSistema_muscular(tbxSistema_muscular
						.getValue());
				hisc_recien_nacido.setMuscular_list(lbxMuscular_list
						.getSelectedItem().getValue().toString());
				hisc_recien_nacido.setNeurologico(tbxNeurologico.getValue());
				hisc_recien_nacido.setNeurologico_list(lbxNeurologico_list
						.getSelectedItem().getValue().toString());
				hisc_recien_nacido.setConducta(tbxConducta.getValue());
				hisc_recien_nacido.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				hisc_recien_nacido.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				hisc_recien_nacido.setCreacion_user(usuarios.getCodigo()
						.toString());
				hisc_recien_nacido.setDelete_date(null);
				hisc_recien_nacido.setUltimo_user(usuarios.getCodigo()
						.toString());
				hisc_recien_nacido.setDelete_user(null);
				
				hisc_recien_nacido.setSem_gestacion(macroCapurro.getSemanas());
				//log.info(">>>>>>>>>>>>"+macroCapurro.getSemanas());

				calcularCoordenadas();

				// Coordenada (P/E)
				Coordenadas_graficas cg1 = new Coordenadas_graficas();
				cg1.setCodigo_empresa(codigo_empresa);
				cg1.setCodigo_historia(hisc_recien_nacido.getCodigo_historia());
				cg1.setCodigo_sucursal(codigo_sucursal);
				cg1.setFecha_historia(hisc_recien_nacido.getFecha_inicial());
				cg1.setTipo_coordenada(ITipos_coordenada.P_E);
				cg1.setIdentificacion(paciente.getNro_identificacion());
				cg1.setValor("" + coordenadaTallaEdad.getValor());
				cg1.setX("" + coordenadaTallaEdad.getX());
				cg1.setY("" + coordenadaTallaEdad.getY());
				cg1.setIhistoria_clinica(IHistorias_clinicas.HC_RECIEN_NACIDO);

				// Coordenada (T/E)
				Coordenadas_graficas cg2 = new Coordenadas_graficas();
				cg2.setCodigo_empresa(codigo_empresa);
				cg2.setCodigo_historia(hisc_recien_nacido.getCodigo_historia());
				cg2.setCodigo_sucursal(codigo_sucursal);
				cg2.setFecha_historia(hisc_recien_nacido.getFecha_inicial());
				cg2.setTipo_coordenada(ITipos_coordenada.T_E);
				cg2.setIdentificacion(paciente.getNro_identificacion());
				cg2.setValor("" + coordenadaPesoTalla.getValor());
				cg2.setX("" + coordenadaPesoTalla.getX());
				cg2.setY("" + coordenadaPesoTalla.getY());
				cg2.setIhistoria_clinica(IHistorias_clinicas.HC_RECIEN_NACIDO);

				// Coordenada (P/T)
				Coordenadas_graficas cg3 = new Coordenadas_graficas();
				cg3.setCodigo_empresa(codigo_empresa);
				cg3.setCodigo_historia(hisc_recien_nacido.getCodigo_historia());
				cg3.setCodigo_sucursal(codigo_sucursal);
				cg3.setFecha_historia(hisc_recien_nacido.getFecha_inicial());
				cg3.setTipo_coordenada(ITipos_coordenada.P_T);
				cg3.setIdentificacion(paciente.getNro_identificacion());
				cg3.setValor("" + coordenadaIMCEdad.getValor());
				cg3.setX("" + coordenadaIMCEdad.getX());
				cg3.setY("" + coordenadaIMCEdad.getY());
				cg3.setIhistoria_clinica(IHistorias_clinicas.HC_RECIEN_NACIDO);

				// Coordenada (PC/E)
				Coordenadas_graficas cg4 = new Coordenadas_graficas();
				cg4.setCodigo_empresa(codigo_empresa);
				cg4.setCodigo_historia(hisc_recien_nacido.getCodigo_historia());
				cg4.setCodigo_sucursal(codigo_sucursal);
				cg4.setFecha_historia(hisc_recien_nacido.getFecha_inicial());
				cg4.setTipo_coordenada(ITipos_coordenada.PC_E);
				cg4.setIdentificacion(paciente.getNro_identificacion());
				cg4.setValor("" + coordenadaPerimetroCefalicoEdad.getValor());
				cg4.setX("" + coordenadaPerimetroCefalicoEdad.getX());
				cg4.setY("" + coordenadaPerimetroCefalicoEdad.getY());
				cg4.setIhistoria_clinica(IHistorias_clinicas.HC_RECIEN_NACIDO);

				ArrayList<Coordenadas_graficas> coordenadas = new ArrayList<Coordenadas_graficas>();
				coordenadas.add(cg1);
				coordenadas.add(cg2);
				coordenadas.add(cg3);
				coordenadas.add(cg4);

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("accion", tbxAccion.getText());
				datos.put("historia_clinica", hisc_recien_nacido);
				datos.put("admision", admision_seleccionada);
				datos.put("coordenadas", coordenadas);
				datos.put("cita_seleccionada", cita);
				
				Map<String, Object> mapReceta = receta_ripsAction
						.obtenerDatos();
				Map<String, Object> mapProcedimientos = orden_servicioAction
						.obtenerDatos();
				datos.put("receta_medica", mapReceta);
				datos.put("orden_servicio", mapProcedimientos);
				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();
				datos.put("impresion_diagnostica", impresion_diagnostica);
				datos.put("anexo9", remisiones_externasAction.obtenerAnexo9());

				getServiceLocator()
						.getServicio(Hisc_recien_nacidoService.class)
						.guardarDatos(datos);

				tbxAccion.setValue("modificar");
				Receta_rips receta_rips = (Receta_rips) mapReceta
						.get("receta_rips");
				if (receta_rips != null)
					receta_ripsAction.mostrarDatos(receta_rips, false);
				Orden_servicio orden_servicio = (Orden_servicio) mapProcedimientos
						.get("orden_servicio");
				if (orden_servicio != null)
					orden_servicioAction.mostrarDatos(orden_servicio);
				
				

				actualizarAutorizaciones(admision_seleccionada,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision_seleccionada,
						getInformacionClinica(), this);

				// infoPacientes.setCodigo_historia(hisc_recien_nacido.getCodigo_historia());

				MensajesUtil.mensajeInformacion("Informacion ...",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_recien_nacido hisc_recien_nacido = (Hisc_recien_nacido) obj;
		try {
			this.via_ingreso = hisc_recien_nacido.getVia_ingreso();
			infoPacientes.setCodigo_historia(hisc_recien_nacido
					.getCodigo_historia());
			infoPacientes
					.setFecha_inicio(hisc_recien_nacido.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					hisc_recien_nacido.getUltimo_update());

			initMostrar_datos(hisc_recien_nacido);

			cargarInformacion_paciente();

			tbxNombre_padre.setValue(hisc_recien_nacido.getNombre_padre());
			ibxTelefono
					.setValue((hisc_recien_nacido.getTelefono() != null && !hisc_recien_nacido
							.getTelefono().isEmpty()) ? Integer
							.parseInt(hisc_recien_nacido.getTelefono()) : null);
			tbxAnte_patologicos_materno.setValue(hisc_recien_nacido
					.getAnte_patologicos_materno());
			tbxAnte_patologicos_paternos.setValue(hisc_recien_nacido
					.getAnte_patologicos_paternos());
			ibxPerinatales_g.setValue(hisc_recien_nacido.getPerinatales_g());
			ibxPerinatales_p.setValue(hisc_recien_nacido.getPerinatales_p());
			ibxPerinatales_c.setValue(hisc_recien_nacido.getPerinatales_c());
			ibxPerinatales_a.setValue(hisc_recien_nacido.getPerinatales_a());
			ibxPerinatales_v.setValue(hisc_recien_nacido.getPerinatales_v());
			dbxExamen_fisico_peso.setValue(hisc_recien_nacido.getPeso());
			dbxExamen_fisico_talla.setValue(hisc_recien_nacido.getTalla());
			dbxExamen_fisico_perimetro_cflico.setValue(hisc_recien_nacido
					.getPerimetro_cefalico());
			dbxExamen_fisico_fc.setValue(hisc_recien_nacido.getFrec_cardiaca());
			dbxExamen_fisico_fr.setValue(hisc_recien_nacido
					.getFrec_respiratoria());
			dbxExamen_fisico_temp.setValue(hisc_recien_nacido.getTemperatura());
			dbxImc.setValue(hisc_recien_nacido.getImc());
			dbxP_t.setValue(hisc_recien_nacido.getP_t());
			// tbxProducto_del_embarazo.setValue(hisc_recien_nacido.getProducto_del_embarazo());
			// tbxNombre_madre.setValue(hisc_recien_nacido.getNombre_madre());
			// ibxEdad_madre.setValue(hisc_recien_nacido.getEdad_madre());
			tbxComp_embarazo.setValue(hisc_recien_nacido.getComp_embarazo());
			tbxComp_trabajo_parto.setValue(hisc_recien_nacido
					.getComp_trabajo_parto());
			tbxComp_del_parto.setValue(hisc_recien_nacido.getComp_del_parto());
			Utilidades.seleccionarRadio(rdbReanimacion,
					hisc_recien_nacido.getReanimacion());
			Utilidades.seleccionarRadio(rdbFrecue_rep0,
					hisc_recien_nacido.getFrecue_rep0());
			Utilidades.seleccionarRadio(rdbEdad_gestacional,
					hisc_recien_nacido.getMin1());
			ibxFrecue_rep1_min.setText(hisc_recien_nacido.getFrecue_rep1_min());
			ibxFrecue_rep5_min.setText(hisc_recien_nacido.getFrecue_rep5_min());
			ibxFrecue_rep10_min.setText(hisc_recien_nacido
					.getFrecue_rep10_min());
			Utilidades.seleccionarRadio(rdbFrecue_card0,
					hisc_recien_nacido.getFrecue_card0());
			ibxFrecue_card1_min.setText(hisc_recien_nacido
					.getFrecue_card1_min());
			ibxFrecue_card5_min.setText(hisc_recien_nacido
					.getFrecue_card5_min());
			ibxFrecue_card10_min.setText(hisc_recien_nacido
					.getFrecue_card10_min());
			Utilidades.seleccionarRadio(rdbMuscular0,
					hisc_recien_nacido.getMuscular0());
			ibxMuscular1_min.setText(hisc_recien_nacido.getMuscular1_min());
			ibxMuscular5_min.setText(hisc_recien_nacido.getMuscular5_min());
			ibxMuscular10_min.setText(hisc_recien_nacido.getMuscular10_min());
			Utilidades.seleccionarRadio(rdbReflejo0,
					hisc_recien_nacido.getReflejo0());
			ibxReflejo1_min.setText(hisc_recien_nacido.getReflejo1_min());
			ibxReflejo5_min.setText(hisc_recien_nacido.getReflejo5_min());
			ibxReflejo10_min.setText(hisc_recien_nacido.getReflejo10_min());
			Utilidades.seleccionarRadio(rdbColor_piel0,
					hisc_recien_nacido.getColor_piel0());
			ibxColor_piel1_min.setText(hisc_recien_nacido.getColor_piel1_min());
			ibxColor_piel5_min.setText(hisc_recien_nacido.getColor_piel5_min());
			ibxColor_piel0_min.setText(hisc_recien_nacido.getColor_piel0_min());
			tbxResultado_apgar1.setValue(hisc_recien_nacido
					.getResultado_apgar());
			tbxResultado_apgar5.setValue(hisc_recien_nacido
					.getResultado_apgar5());
			tbxResultado_apgar10.setValue(hisc_recien_nacido
					.getResultado_apgar10());
			Utilidades.seleccionarRadio(rdbBcg, hisc_recien_nacido.getBcg());
			Utilidades.seleccionarRadio(rdbHep_b1,
					hisc_recien_nacido.getHep_b1());
			Utilidades.seleccionarRadio(rdbVitamina_k,
					hisc_recien_nacido.getVitamina_k());
			Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
					hisc_recien_nacido.getSintomaticos_respiratorios());
			Utilidades.seleccionarRadio(rdbSintomaticos_piel,
					hisc_recien_nacido.getSintomaticos_piel());
			dbxDesviacion_estandar1.setValue(hisc_recien_nacido
					.getDesviacion_estandar1());
			dbxDesviacion_estandar2.setValue(hisc_recien_nacido
					.getDesviacion_estandar2());
			dbxDesviacion_estandar3.setValue(hisc_recien_nacido
					.getDesviacion_estandar3());
			dbxDesviacion_estandar4.setValue(hisc_recien_nacido
					.getDesviacion_estandar4());
			tbxConducta.setValue(hisc_recien_nacido.getConducta());

			Utilidades.seleccionarListitem(lbxCabezacara_list,
					hisc_recien_nacido.getCabezacara_list());
			Utilidades.seleccionarListitem(lbxPiel_faneras_list,
					hisc_recien_nacido.getPiel_faneras_list());
			Utilidades.seleccionarListitem(lbxTorax_list,
					hisc_recien_nacido.getTorax_pulmonares());
			Utilidades.seleccionarListitem(lbxCuello_list,
					hisc_recien_nacido.getCuello_list());
			Utilidades.seleccionarListitem(lbxVascular_list,
					hisc_recien_nacido.getVascular_list());
			Utilidades.seleccionarListitem(lbxAdomen_list,
					hisc_recien_nacido.getAdomen_list());
			Utilidades.seleccionarListitem(lbxGenitales_list,
					hisc_recien_nacido.getGenitales_list());
			Utilidades.seleccionarListitem(lbxAno_list,
					hisc_recien_nacido.getAno_list());
			Utilidades.seleccionarListitem(lbxMuscular_list,
					hisc_recien_nacido.getMuscular_list());
			Utilidades.seleccionarListitem(lbxPiel_faneras_list,
					hisc_recien_nacido.getPiel_faneras_list());
			Utilidades.seleccionarListitem(lbxNeurologico_list,
					hisc_recien_nacido.getNeurologico_list());

			onSeleccionarAntecendestes1(lbxCabezacara_list, tbxCabeza_cara);
			onSeleccionarAntecendestes1(lbxPiel_faneras_list, tbxPiel);
			onSeleccionarAntecendestes1(lbxTorax_list, tbxTorax_pulmonares);
			onSeleccionarAntecendestes1(lbxCuello_list, tbxCuello);
			onSeleccionarAntecendestes1(lbxVascular_list, tbxCorazon_vascular);
			onSeleccionarAntecendestes1(lbxAdomen_list, tbxAbdomen);
			onSeleccionarAntecendestes1(lbxGenitales_list, tbxGenitales);
			onSeleccionarAntecendestes1(lbxAno_list, tbxAno);
			onSeleccionarAntecendestes1(lbxMuscular_list, tbxSistema_muscular);
			onSeleccionarAntecendestes1(lbxNeurologico_list, tbxNeurologico);

			if (!tbxAccion.getValue().equals(
					OpcionesFormulario.MOSTRAR.toString()))
				cargarImpresionDiagnostica(hisc_recien_nacido);

			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(false);
			onMostrarModuloOrdenamiento();
			orden_servicioAction.obtenerBotonAgregar().setVisible(false);
			
			cargarAnexo9_remision(hisc_recien_nacido);

			if (!tbxAccion.getValue().equals(OpcionesFormulario.MOSTRAR.toString())) {
			} else {
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_recien_nacido hisc_recien_nacido = (Hisc_recien_nacido) obj;
		try {
			int result = getServiceLocator().getServicio(
					Hisc_recien_nacidoService.class).eliminar(
					hisc_recien_nacido);
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

	// DATOS CURVAS CRECIMIENTO.
	public void calcularCoordenadas() {
		calcularImcEdad();
		calcularPerimetroCefalicoEdad();
		calcularPesoTalla();
		calcularTallaEdad();
	}

	private List<RespuestaInt> cargarRespuestas(
			List<Coordenadas_graficas> coordenadas,
			Hisc_recien_nacido hisc_recien_nacido) {
		List<RespuestaInt> respuestas = new ArrayList<RespuestaInt>();
		for (Coordenadas_graficas cg : coordenadas) {
			RespuestaInt r = cargarResuestaInt(cg);
			if (hisc_recien_nacido != null
					&& cg.getCodigo_historia().equals(
							hisc_recien_nacido.getCodigo_historia())) {
				r.setActual(true);
			}
			respuestas.add(r);
		}
		return respuestas;
	}

	private RespuestaInt cargarResuestaInt(Coordenadas_graficas cg) {
		RespuestaInt r = new RespuestaInt();
		r.setValor(Double.valueOf(cg.getValor()));
		r.setX(Double.valueOf(cg.getX()));
		r.setY(Double.valueOf(cg.getY()));
		return r;
	}

	// METODOS PARA VISTA
	public void calcularTallaEdad() {
		double talla;
		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxExamen_fisico_talla.setStyle("background-color:white");
			talla = dbxExamen_fisico_talla.getValue();
		}
		coordenadaTallaEdad = calcularTallaEdad(talla, fecha);
		dbxDesviacion_estandar1.setValue(coordenadaTallaEdad.getValor());
		// btnCalcularTallaEdad.setDisabled(false);
	}

	public void calcularPesoTalla() {

		double talla;
		double peso;

		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxExamen_fisico_talla.setStyle("background-color:white");
			talla = dbxExamen_fisico_talla.getValue();
		}

		if (dbxExamen_fisico_peso.getValue() == null) {
			dbxExamen_fisico_peso.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxExamen_fisico_peso.setStyle("background-color:white");
			peso = dbxExamen_fisico_peso.getValue();
		}

		coordenadaPesoTalla = calcularPesoTalla(peso, talla);
		dbxDesviacion_estandar2.setValue(coordenadaPesoTalla.getValor());
		// btnCalcularPesoTalla.setDisabled(false);
	}

	public void calcularImcEdad() {

		double talla;
		double peso;

		if (dbxExamen_fisico_talla.getValue() == null) {
			dbxExamen_fisico_talla.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar la talla del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxExamen_fisico_talla.setStyle("background-color:white");
			talla = dbxExamen_fisico_talla.getValue();
		}

		if (dbxExamen_fisico_peso.getValue() == null) {
			dbxExamen_fisico_peso.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar el peso del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxExamen_fisico_peso.setStyle("background-color:white");
			peso = dbxExamen_fisico_peso.getValue();
		}

		double imc = CalculadorUtil.calcularIMC(peso, talla/100);
		coordenadaIMCEdad = calcularImcEdad(imc, fecha);
		dbxDesviacion_estandar3.setValue(coordenadaIMCEdad.getValor());
		// btnCalcularImcEdad.setDisabled(false);
	}

	public void calcularPerimetroCefalicoEdad() {
		double perimetro_cefalico;

		if (dbxExamen_fisico_perimetro_cflico.getValue() == null) {
			dbxExamen_fisico_perimetro_cflico
					.setStyle("background-color:#F6BBBE");
			Messagebox
					.show("Debe digitar el perimetro cefálico del paciente para realizar este cálculo!!",
							"Alerta", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			dbxExamen_fisico_perimetro_cflico
					.setStyle("background-color:white");
			perimetro_cefalico = dbxExamen_fisico_perimetro_cflico.getValue();
		}

		coordenadaPerimetroCefalicoEdad = calcularPerimetroCefalicoEdad(perimetro_cefalico);
		dbxDesviacion_estandar4.setValue(coordenadaPerimetroCefalicoEdad
				.getValor());

		// btnCalcularPerimetroCefalicoEdad.setDisabled(false);
	}

	private RespuestaInt calcularTallaEdad(double talla, Timestamp fecha) {
		return GraficasCalculadorUtils.calcularTallaEdad0$2Anios(sexo, talla,
				fecha);
	}

	private RespuestaInt calcularPesoTalla(double peso, double talla) {
		return GraficasCalculadorUtils.calcularPesoTalla0$2Anios(sexo, peso,
				talla);
	}

	private RespuestaInt calcularImcEdad(double imc, Timestamp fecha) {
		return GraficasCalculadorUtils
				.calcularIMCEdad0$5Anios(sexo, imc, fecha);
	}

	private RespuestaInt calcularPerimetroCefalicoEdad(double perimetro_cefalico) {
		return GraficasCalculadorUtils.calcularPerimetroCefalicoEdad0$2Anios(
				sexo, perimetro_cefalico, fecha);
	}

	/* Graficamos */
	public void graficarTallaEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.T_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
		List<Coordenadas_graficas> tes = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasTE = cargarRespuestas(tes, hisc_recien_nacido);

		List coordenadas_te = coordenadasTE;
		if (!verificarActivo(coordenadasTE)) {
			coordenadas_te.add(coordenadaTallaEdad);
		}
		imprimirArreglo(coordenadas_te);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_DE_0_A_5,
				coordenadas_te, this, sexo);
	}

	public void mostrarTablaTallaEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.TALLA_EDAD_DE_0_A_5, this,
				sexo);
	};

	public void graficarPesoTalla() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.P_T);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
		List<Coordenadas_graficas> pts = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasPT = cargarRespuestas(pts, hisc_recien_nacido);

		List coordenadas_pt = coordenadasPT;
		if (!verificarActivo(coordenadasPT)) {
			coordenadas_pt.add(coordenadaPesoTalla);
		}
		imprimirArreglo(coordenadas_pt);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_2_A_5,
				coordenadas_pt, this, sexo);
	}

	public void mostrarTablaPesoTalla() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.PESO_TALLA_2_A_5, this,
				sexo);
	};

	public void graficarIMCEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.IMC_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
		List<Coordenadas_graficas> imces = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);
		coordenadasIE = cargarRespuestas(imces, hisc_recien_nacido);

		List coordenadas_imce = coordenadasIE;
		if (!verificarActivo(coordenadasIE)) {
			coordenadas_imce.add(coordenadaIMCEdad);
		}

		imprimirArreglo(coordenadas_imce);
		GraficasCalculadorUtils.mostrarGrafica(
				GraficasCalculadorUtils.TipoGrafica.IMC_EDAD_0_A_5,
				coordenadas_imce, this, sexo);
	}

	public void mostrarTablaIMCEdad() {
		GraficasCalculadorUtils.mostrarTabla(
				GraficasCalculadorUtils.TipoGrafica.IMC_EDAD_0_A_5, this, sexo);
	};

	public void graficarPcEdad() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("identificacion", paciente.getNro_identificacion());
		parameters.put("tipo_coordenada", ITipos_coordenada.PC_E);
		parameters.put("ihistoria_clinica",
				IHistorias_clinicas.HC_MENOR_2_ANOS_5_ANOS);
		List<Coordenadas_graficas> pces = getServiceLocator()
				.getCoordenadas_graficasService().listar(parameters);

		coordenadasPCE = cargarRespuestas(pces, hisc_recien_nacido);

		List coordenadas_pce = coordenadasPCE;
		if (!verificarActivo(coordenadasPCE)) {
			coordenadas_pce.add(coordenadaPerimetroCefalicoEdad);
		}
		imprimirArreglo(coordenadas_pce);
		GraficasCalculadorUtils
				.mostrarGrafica(
						GraficasCalculadorUtils.TipoGrafica.PERIMETRO_CEFALICO_EDAD_0_5,
						coordenadas_pce, this, sexo);
	}

	public void mostrarTablaPcEdad() {
		GraficasCalculadorUtils
				.mostrarTabla(
						GraficasCalculadorUtils.TipoGrafica.PERIMETRO_CEFALICO_EDAD_0_5,
						this, sexo);
	};

	public void imprimirArreglo(List<RespuestaInt> arreglo) {
//		for (RespuestaInt ri : arreglo) {
//			//log.info(">>>>>>>>>>>>" + ri.isActual());
//		}
	}

	public boolean verificarActivo(List<RespuestaInt> arreglo) {
		boolean actual = false;
		for (RespuestaInt ri : arreglo) {
			if (ri.isActual()) {
				actual = true;
				break;
			}
		}
		return actual;
	}

	// FIN CURVAS CRECIMIENTO
	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
					UtilidadSignosVitales.TALLA_CENTIMETROS,
					UtilidadSignosVitales.PESO_KILOS);
		} catch (Exception e) {
			//  block
//			MensajesUtil.mensajeError(e, "", this);
//			e.printStackTrace();

		}
	}

	// listar combox hallazgos examen fisico
	public void onSeleccionarAntecendestes1(Listbox listboxSintomatico,
			Textbox textboxAntecedentes) {
		if (listboxSintomatico.getSelectedItem().getValue().toString()
				.equals("S")) {
			textboxAntecedentes.setFocus(true);
		} else {
			textboxAntecedentes.setFocus(true);
		}
	}

	@Override
	public void initHistoria() throws Exception {
		if (admision_seleccionada != null) {
			toolbarbuttonPaciente_admisionado1.setLabel(admision_seleccionada
					.getPaciente().getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision_seleccionada
					.getPaciente().getNombreCompleto());

			if (admision_seleccionada.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Hisc_recien_nacido hisc_recien_nacido = new Hisc_recien_nacido();
				hisc_recien_nacido.setCodigo_empresa(empresa
						.getCodigo_empresa());
				hisc_recien_nacido.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				hisc_recien_nacido.setIdentificacion(admision_seleccionada
						.getNro_identificacion());
				hisc_recien_nacido.setNro_ingreso(admision_seleccionada
						.getNro_ingreso());

				hisc_recien_nacido = getServiceLocator().getServicio(
						Hisc_recien_nacidoService.class).consultar(
						hisc_recien_nacido);

				if (hisc_recien_nacido != null) {
					accionForm(OpcionesFormulario.MOSTRAR, hisc_recien_nacido);
					infoPacientes.setCodigo_historia(hisc_recien_nacido
							.getCodigo_historia());
				} else {
					groupboxEditar.setVisible(false);
					throw new Exception(
							"NO hay una historia clinica relacionada a este paciente admitido");
				}
			} else {
				if (opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
					accionForm(OpcionesFormulario.REGISTRAR, null);
					btnCancelar.setVisible(false);
				} else {
					accionForm(OpcionesFormulario.CONSULTAR, null);
					btnCancelar.setVisible(true);
				}
			}

		}
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
		} else {
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		infoPacientes.cargarInformacion(admision_seleccionada, this,
				new InformacionPacienteIMG() {
					@Override
					public void ejecutarProceso() {
						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							toolbarbuttonTipo_historia
									.setLabel("Creando historia Recien Nacido");
						}
					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		// Hisc_deteccion_alt_mn_2mes hisc_deteccion_alt_mn_2mes =
		// (Hisc_deteccion_alt_mn_2mes) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });
			toolbarbuttonTipo_historia.setLabel("Mostrando Historia Recien Nacido");
			((Button) getFellow("btGuardar")).setVisible(false);
		} else {
			toolbarbuttonTipo_historia.setLabel("Modificando Historia Recien Nacido");
			((Button) getFellow("btGuardar")).setVisible(true);
		}
	}

	public void onChequearPuntajeApgar(Radiogroup radiogroup_seleccionado,
			Row fila_seleccionado) {
		String edad_gestacional = rdbEdad_gestacional.getSelectedItem()
				.getValue().toString();
		String valor_seleccionado = radiogroup_seleccionado.getSelectedItem()
				.getValue();
		Textbox tbxResultado = null;
		List<Component> lista_componenetes = fila_seleccionado.getChildren();
		if (edad_gestacional.equals(rdbMinuto1.getValue().toString())) {
			((Intbox) lista_componenetes.get(4).getFirstChild())
					.setText(valor_seleccionado);
			tbxResultado = tbxResultado_apgar1;
		} else if (edad_gestacional.equals(rdbMinuto5.getValue().toString())) {
			((Intbox) lista_componenetes.get(5).getFirstChild())
					.setText(valor_seleccionado);
			tbxResultado = tbxResultado_apgar5;
		} else if (edad_gestacional.equals(rdbMinuto10.getValue().toString())) {
			((Intbox) lista_componenetes.get(6).getFirstChild())
					.setText(valor_seleccionado);
			tbxResultado = tbxResultado_apgar10;
		}
		calcularPuntucion_Apgar(tbxResultado);
	}

	private void calcularPuntucion_Apgar(Textbox tbxResultado) {
		subtotalNum1 = 0;
		Integer frec_resp = 0;
		Integer frec_card = 0;
		Integer muscular = 0;
		Integer reflejo = 0;
		Integer color_piel = 0;

		if (rdbFrecue_rep0.getSelectedItem() != null) {
			frec_resp = new Integer(rdbFrecue_rep0.getSelectedItem().getValue().toString());
		}
		if (rdbFrecue_card0.getSelectedItem() != null) {
			frec_card = new Integer(rdbFrecue_card0.getSelectedItem()
					.getValue().toString());
		}
		if (rdbMuscular0.getSelectedItem() != null) {
			muscular = new Integer(rdbMuscular0.getSelectedItem().getValue()
					.toString());
		}
		if (rdbReflejo0.getSelectedItem() != null) {
			reflejo = new Integer(rdbReflejo0.getSelectedItem().getValue()
					.toString());
		}
		if (rdbColor_piel0.getSelectedItem() != null) {
			color_piel = new Integer(rdbColor_piel0.getSelectedItem()
					.getValue().toString());
		}
		subtotalNum1 = subtotalNum1 + frec_resp + frec_card + muscular
				+ reflejo + color_piel;

		tbxResultado.setText(subtotalNum1 + "");

		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		int total = Integer.parseInt(tbxResultado.getValue());
		//log.info("" + total);

		if (total >= 0 && total < 7) {
			String mensaje = "ANORMAL.- El niño necesita atencion médica";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, tbxResultado,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			tbxResultado.setTooltiptext(mensaje);
			tbxResultado.setStyle("background-color:#FF5252;color:white");
		} else if (total >= 7 && total < 13) {
			String mensaje = "NORMAL.- El niño se encuentra en buen estado de salud";
			Clients.showNotification(mensaje, Clients.NOTIFICATION_TYPE_INFO,
					tbxResultado, POSICION_ALERTA, TIEMPO_ALERTA, true);
			tbxResultado.setTooltiptext(mensaje);
			tbxResultado.setStyle("background-color:#66FF33");
		}

	}

	// edad gestacional
	public void onChequearEdadGestacional() {
		String edad_gestacional = rdbEdad_gestacional.getSelectedItem()
				.getValue().toString();
		if (edad_gestacional.equals(rdbMinuto1.getValue().toString())) {
			for (Radio radio : rdbFrecue_rep0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxFrecue_rep1_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

			for (Radio radio : rdbFrecue_card0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxFrecue_card1_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

			for (Radio radio : rdbMuscular0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxMuscular1_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

			for (Radio radio : rdbReflejo0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxReflejo1_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

			for (Radio radio : rdbColor_piel0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxColor_piel1_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

		} else if (edad_gestacional.equals(rdbMinuto5.getValue().toString())) {
			for (Radio radio : rdbFrecue_rep0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxFrecue_rep5_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}
			for (Radio radio : rdbFrecue_card0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxFrecue_card5_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

			for (Radio radio : rdbMuscular0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxMuscular5_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}
			for (Radio radio : rdbReflejo0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxReflejo5_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

			for (Radio radio : rdbColor_piel0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxColor_piel5_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

		} else if (edad_gestacional.equals(rdbMinuto10.getValue().toString())) {
			for (Radio radio : rdbFrecue_rep0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxFrecue_rep10_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}
			for (Radio radio : rdbFrecue_card0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxFrecue_card10_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

			for (Radio radio : rdbMuscular0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxMuscular10_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

			for (Radio radio : rdbReflejo0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxReflejo10_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

			for (Radio radio : rdbColor_piel0.getItems()) {
				if (radio.getValue().toString()
						.equals(ibxColor_piel0_min.getText())) {
					radio.setChecked(true);
				} else {
					radio.setChecked(false);
				}
			}

		}
	}

	// public String getValoresConcatenados(InputElement ...inputElements){
	// StringBuffer valores = new StringBuffer();
	// for(int i=0; i<inputElements.length; i++){
	// InputElement iElement = inputElements[i];
	// if(i == 0){
	// valores.append(iElement.getText());
	// }else{
	// valores.append("|").append(iElement.getText());
	// }
	// }
	// return valores.toString();
	// }
	//
	// public void setValoresConcatenados(String valores,InputElement
	// ...inputElements){
	// StringTokenizer stringTokenizer = new StringTokenizer(valores,"|");
	// inputElements[0].setText(stringTokenizer.nextToken());
	// inputElements[1].setText(stringTokenizer.nextToken());
	// inputElements[2].setText(stringTokenizer.nextToken());
	// }

	public void onMostrarModuloFarmacologico() throws Exception {
		if (receta_ripsAction != null)
			receta_ripsAction.detach();
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision_seleccionada);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");

		parametros.put("ocultarRecomendaciones", "_Ocultar");

		receta_ripsAction = (Receta_rips_internoAction) Executions
				.createComponents("/pages/receta_rips_interno.zul", null,
						parametros);
		receta_ripsAction.inicializar(this);
		divModuloFarmacologico.appendChild(receta_ripsAction);

	}

	public void onMostrarModuloOrdenamiento() throws Exception {
		if (orden_servicioAction != null)
			orden_servicioAction.detach();
		// if (!cargo_farmacologico) {
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision_seleccionada);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");
		orden_servicioAction = (Orden_servicio_internoAction) Executions
				.createComponents("/pages/orden_servicio_interno.zul", null,
						parametros);
		orden_servicioAction.inicializar(this);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
		// cargo_farmacologico = true;
		// }

	}

	private void cargarImpresionDiagnostica(
			Hisc_recien_nacido hisc_recien_nacido) throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(hisc_recien_nacido
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	public void alarmaExamenFisicoTemperatura() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy")
						.format(admision_seleccionada.getPaciente()
								.getFecha_nacimiento()), "1", false));

		if ((dbxExamen_fisico_temp.getText() != "")
				&& (dbxExamen_fisico_temp.getValue() >= 36.6 && dbxExamen_fisico_temp
						.getValue() <= 37.8) && (edad > 2 && edad < 4)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxExamen_fisico_temp,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxExamen_fisico_temp
					.setStyle("text-transform:uppercase;background-color:white");

		} else if (edad <= 2 || edad >= 4) {
			dbxExamen_fisico_temp
					.setStyle("text-transform:uppercase;background-color:white");
		} else if ((dbxExamen_fisico_temp.getText() != "")
				&& (dbxExamen_fisico_temp.getValue() < 36.6 || dbxExamen_fisico_temp
						.getValue() > 37.8) && (edad > 2 && edad < 4)) {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxExamen_fisico_temp,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxExamen_fisico_temp
					.setStyle("text-transform:uppercase;background-color:red");
		}
	}

	public void alarmaExamenFisicoRespiracion() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy")
						.format(admision_seleccionada.getPaciente()
								.getFecha_nacimiento()), "1", false));

		if ((dbxExamen_fisico_fr.getText() != "")
				&& (dbxExamen_fisico_fr.getValue() == 25)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxExamen_fisico_fr,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxExamen_fisico_fr
					.setStyle("text-transform:uppercase;background-color:white");

		} else if (edad <= 2 || edad >= 4) {
			dbxExamen_fisico_fr
					.setStyle("text-transform:uppercase;background-color:white");
		} else if ((dbxExamen_fisico_fr.getText() != "")
				&& (dbxExamen_fisico_fr.getValue() != 25)
				&& (edad > 2 && edad < 4)) {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxExamen_fisico_fr,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxExamen_fisico_fr
					.setStyle("text-transform:uppercase;background-color:red");
		}
	}

	public void alarmaExamenFisicoFc() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy")
						.format(admision_seleccionada.getPaciente()
								.getFecha_nacimiento()), "1", false));

		if ((dbxExamen_fisico_fc.getText() != "")
				&& (dbxExamen_fisico_fc.getValue() >= 90 && dbxExamen_fisico_fc
						.getValue() <= 100) && (edad > 2 && edad < 4)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxExamen_fisico_fc,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxExamen_fisico_fc
					.setStyle("text-transform:uppercase;background-color:white");

		} else if (edad <= 2 || edad >= 4) {
			dbxExamen_fisico_fc
					.setStyle("text-transform:uppercase;background-color:white");
		} else if ((dbxExamen_fisico_fc.getText() != "")
				&& (dbxExamen_fisico_fc.getValue() < 90 || dbxExamen_fisico_fc
						.getValue() > 100) && (edad > 2 && edad < 4)) {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxExamen_fisico_fc,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxExamen_fisico_fc
					.setStyle("text-transform:uppercase;background-color:red");
		}
	}

	private void cargarAnexo9_remision(	Hisc_recien_nacido hisc_recien_nacido) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(hisc_recien_nacido
				.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision_seleccionada.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);		
		boolean creada = false;
		if (anexo9_entidad != null)
			creada = true;
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
		if (creada) {
			remisiones_externasAction.getBotonGenerar().setVisible(false);
		}
		remisiones_externasAction.setNro_historia(hisc_recien_nacido
				.getCodigo_historia());
	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null)
			remisiones_externasAction.detach();
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora", admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision_seleccionada);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");

		parametros.put("ocultarRecomendaciones", "_Ocultar");

		remisiones_externasAction = (Remisiones_externasAction) Executions
				.createComponents("/pages/remisiones_externas.zul", null,
						parametros);
		remisiones_externasAction.inicializar(this);
		divModuloRemisiones_externas.appendChild(remisiones_externasAction);

	}

}