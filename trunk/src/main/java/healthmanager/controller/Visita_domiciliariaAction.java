/*
 * visita_domiciliariaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.bean.Visita_domiciliaria;
import healthmanager.modelo.service.Remision_internaService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Visita_domiciliariaAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Visita_domiciliariaAction.class);

	// Componentes //

	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	
	private Admision admision;
	private Opciones_via_ingreso opciones_via_ingreso;

	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

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
	private Listbox lbxTipo_historia;

	@View
	private Toolbarbutton btnCancelar;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	private String tipo_historia;
	private Long codigo_historia_anterior;

	@View
	private Listbox lbxAtendida;
	@View
	private Textbox tbxEstado;
	@View
	private Textbox tbxId_plan;

	@View
	private Listbox lbxNotifica;
	@View
	private Textbox tbxCodigo_usuario;
	@View
	private Listbox lbxParentesco;
	@View
	private Textbox tbxFamiliar_nombre1;
	@View
	private Intbox ibxFamiliar_edad1;
	@View
	private Textbox tbxFamiliar_escolaridad1;
	@View
	private Listbox tbxFamiliar_parentesco1;
	@View
	private Textbox tbxFamiliar_ocupacion1;
	@View
	private Textbox tbxFamiliar_nombre2;
	@View
	private Intbox ibxFamiliar_edad2;
	@View
	private Textbox tbxFamiliar_escolaridad2;
	@View
	private Listbox tbxFamiliar_parentesco2;
	@View
	private Textbox tbxFamiliar_ocupacion2;
	@View
	private Textbox tbxFamiliar_nombre3;
	@View
	private Intbox ibxFamiliar_edad3;
	@View
	private Textbox tbxFamiliar_escolaridad3;
	@View
	private Listbox tbxFamiliar_parentesco3;
	@View
	private Textbox tbxFamiliar_ocupacion3;
	@View
	private Textbox tbxFamiliar_nombre4;
	@View
	private Intbox ibxFamiliar_edad4;
	@View
	private Textbox tbxFamiliar_escolaridad4;
	@View
	private Listbox tbxFamiliar_parentesco4;
	@View
	private Textbox tbxFamiliar_ocupacion4;
	@View
	private Textbox tbxFamiliar_nombre5;
	@View
	private Intbox ibxFamiliar_edad5;
	@View
	private Textbox tbxFamiliar_escolaridad5;
	@View
	private Listbox tbxFamiliar_parentesco5;
	@View
	private Textbox tbxFamiliar_ocupacion5;
	@View
	private Textbox tbxFamiliar_nombre6;
	@View
	private Intbox ibxFamiliar_edad6;
	@View
	private Textbox tbxFamiliar_escolaridad6;
	@View
	private Listbox tbxFamiliar_parentesco6;
	@View
	private Textbox tbxFamiliar_ocupacion6;
	@View
	private Textbox tbxFamiliar_nombre7;
	@View
	private Intbox ibxFamiliar_edad7;
	@View
	private Textbox tbxFamiliar_escolaridad7;
	@View
	private Listbox tbxFamiliar_parentesco7;
	@View
	private Textbox tbxFamiliar_ocupacion7;
	@View
	private Textbox tbxFamiliar_nombre8;
	@View
	private Intbox ibxFamiliar_edad8;
	@View
	private Textbox tbxFamiliar_escolaridad8;
	@View
	private Listbox tbxFamiliar_parentesco8;
	@View
	private Textbox tbxFamiliar_ocupacion8;
	@View
	private Textbox tbxFamiliar_nombre9;
	@View
	private Intbox ibxFamiliar_edad9;
	@View
	private Textbox tbxFamiliar_escolaridad9;
	@View
	private Listbox tbxFamiliar_parentesco9;
	@View
	private Textbox tbxFamiliar_ocupacion9;
	@View
	private Textbox tbxFamiliar_nombre10;
	@View
	private Intbox ibxFamiliar_edad10;
	@View
	private Textbox tbxFamiliar_escolaridad10;
	@View
	private Listbox tbxFamiliar_parentesco10;
	@View
	private Textbox tbxFamiliar_ocupacion10;
	@View
	private Listbox lbxTecho;
	@View
	private Listbox lbxPiso;
	@View
	private Listbox lbxParedes;
	@View
	private Listbox lbxNo_habitaciones;
	@View
	private Listbox lbxEliminacion_excretas;
	@View
	private Radiogroup rdbRecoleccion;
	@View
	private Radiogroup rdbAgua_potable;
	@View
	private Textbox tbxEnfermedad_actual;
	@View
	private Radiogroup rdbRecibido_medico;
	@View
	private Textbox tbxTipo_atencion;
	@View
	private Textbox tbxEntidad;
	@View
	private Textbox tbxProfesional;
	@View
	private Textbox tbxMotivo;
	@View
	private Textbox tbxHistoria_enfermedad_actual;
	@View
	private Textbox tbxAcciones_pyp;
	@View
	private Radiogroup rdbCanalizacion;
	@View
	private Textbox tbxEntidad_canalizacion;
	@View
	private Textbox tbxTratamiento_actual;
	@View
	private Textbox tbxActitud_familiares;
	@View
	private Textbox tbxPercepcion;
	@View
	private Textbox tbxDisposicion;
	@View
	private Textbox tbxPercepcion_paciente;
	@View
	private Textbox tbxOtros_notifica;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	@View
	private Textbox tbxObservacion;

	// Control
	@View
	private Textbox tbxSeguimiento;
	@View
	private Textbox tbxRecomendacion;

	private final String[] idsExcluyentes = { "tbxAccion" };

	@View
	private Row rowComposicion_familiar;
	@View
	private Row rowDescripcion_vivienda;
	@View
	private Row rowCondiciones;
	@View
	private Row rowDescripcion;
	@View
	private Row rowEnfermedad_actual;
	@View
	private Row rowIntervencion_visita;
	@View
	private Row rowTratamiento;
	@View
	private Row rowEvaluacion;
	@View
	private Row rowSeguimiento;
	@View
	private Row rowObservaciones;
	@View
	private Row rowRecomendaciones;

	private boolean primeraVez;

	// Bandbox

	@View
	private Bandbox tbxNombre_Familiar_escolaridad1;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion1;
	@View
	private Bandbox tbxNombre_Familiar_escolaridad2;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion2;
	@View
	private Bandbox tbxNombre_Familiar_escolaridad3;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion3;
	@View
	private Bandbox tbxNombre_Familiar_escolaridad4;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion4;
	@View
	private Bandbox tbxNombre_Familiar_escolaridad5;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion5;
	@View
	private Bandbox tbxNombre_Familiar_escolaridad6;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion6;
	@View
	private Bandbox tbxNombre_Familiar_escolaridad7;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion7;
	@View
	private Bandbox tbxNombre_Familiar_escolaridad8;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion8;
	@View
	private Bandbox tbxNombre_Familiar_escolaridad9;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion9;
	@View
	private Bandbox tbxNombre_Familiar_escolaridad10;
	@View
	private Bandbox tbxNombre_Familiar_ocupacion10;

	@View
	private Textbox tbxOtros_piso;
	@View
	private Textbox tbxOtros_paredes;
	@View
	private Textbox tbxOtros_techo;
	@View
	private Textbox tbxOtros_excretas;

	@View
	private Row rowMot1;
	@View
	private Row rowMot2;
	@View
	private Row rowMot3;
	@View
	private Row rowMot4;
	@View
	private Row rowCon;
	@View
	private Label lbTipo_atencion;
	@View
	private Label lbEntidad;
	@View
	private Label lbProfesional;
	@View
	private Label lbMotivo;
	@View
	private Label lbMensaje;

	@View
	private Toolbarbutton btGuardar;

	@Override
	public void init() {
		try {
			listarCombos();
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.VISITA_DOMICILIARIA);
			macroRemision_interna.inicializarMacro(this, admision,
					IVias_ingreso.VISITA_DOMICILIARIA);
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
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			macroRemision_interna.deshabilitarCheck(admision,
					IVias_ingreso.VISITA_DOMICILIARIA);
		}
	}

	public void listarCombos() {
		listarParameter();
		listarAtendida();
		Utilidades.listarElementoListbox(lbxNo_habitaciones, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxPiso, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxParedes, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxTecho, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxEliminacion_excretas, true,
				getServiceLocator());
		Utilidades
				.listarElementoListbox(lbxNotifica, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxParentesco, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco1, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco2, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco3, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco4, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco5, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco6, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco7, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco8, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco9, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(tbxFamiliar_parentesco10, true,
				getServiceLocator());

	}

	public void listarAtendida() {
		lbxAtendida.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue(false);
		listitem.setLabel("Pendiente");
		lbxAtendida.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue(true);
		listitem.setLabel("Atendida");
		lbxAtendida.appendChild(listitem);

		if (lbxAtendida.getItemCount() > 0) {
			lbxAtendida.setSelectedIndex(0);
		}

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
		//log.info("accion " + tbxAccion.getValue());

		if (!accion.equalsIgnoreCase("registrar")) {
			//log.info("1");
			buscarDatos();
			btGuardar.setVisible(false);
		} else {
			btGuardar.setVisible(true);
			limpiarDatos();
			FormularioUtil.cargarRadiogroupsDefecto(this);

			if (admision != null) {
				infoPacientes.setFecha_inicio(new Date());
				cargarInformacion_paciente();

			}
		}
		//log.info("2");
		tbxAccion.setValue(accion);
		//log.info("accion " + tbxAccion.getValue());
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;

		if (!primeraVez) {
			FormularioUtil.validarCamposObligatorios(lbxNotifica,
					lbxParentesco, tbxCodigo_usuario, tbxSeguimiento,
					tbxRecomendacion);
		} else {
			FormularioUtil.validarCamposObligatorios(lbxNotifica,
					lbxParentesco, tbxCodigo_usuario, lbxTecho, lbxPiso,
					lbxParedes, lbxEliminacion_excretas, tbxEnfermedad_actual,
					tbxHistoria_enfermedad_actual, tbxAcciones_pyp,
					tbxTratamiento_actual, tbxActitud_familiares,
					tbxPercepcion, tbxDisposicion, tbxPercepcion_paciente,
					tbxObservacion);
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

			if (lbxTipo_historia.getSelectedIndex() != 0) {
				parameters.put("tipo_historia", lbxTipo_historia
						.getSelectedItem().getValue());
			}

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Visita_domiciliaria> lista_datos = getServiceLocator()
					.getVisita_domiciliariaService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Visita_domiciliaria visita_domiciliaria : lista_datos) {
				rowsResultado
						.appendChild(crearFilas(visita_domiciliaria, this));
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

		final Visita_domiciliaria visita_domiciliaria = (Visita_domiciliaria) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(visita_domiciliaria.getCodigo_historia()
				+ ""));
		fila.appendChild(new Label(visita_domiciliaria.getIdentificacion() + ""));
		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(visita_domiciliaria.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(visita_domiciliaria.getTipo_historia()
				.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ) ? "PRIMERA VEZ"
				: "CONTROL"));

		Hlayout hlayout = new Hlayout();

		Toolbarbutton btn_mostrar = new Toolbarbutton();
		btn_mostrar.setImage("/images/mostrar_info.png");
		btn_mostrar.setTooltiptext("Mostrar historia Clinica");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.setLabel("Mostrar");
		btn_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(visita_domiciliaria);
					}
				});
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				Map<String, Object> datos = new HashMap<String, Object>();

				Visita_domiciliaria visita_domiciliaria = new Visita_domiciliaria();
				visita_domiciliaria.setCodigo_empresa(empresa
						.getCodigo_empresa());
				visita_domiciliaria.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				visita_domiciliaria.setCodigo_historia(infoPacientes
						.getCodigo_historia());
				visita_domiciliaria
						.setIdentificacion(admision != null ? admision
								.getNro_identificacion() : null);
				visita_domiciliaria.setFecha_inicial(new Timestamp(
						infoPacientes.getFecha_inicial().getTime()));
				visita_domiciliaria.setNro_ingreso("");
				visita_domiciliaria.setNotifica(lbxNotifica.getSelectedItem()
						.getValue().toString());
				visita_domiciliaria.setOtros_notifica(tbxOtros_notifica
						.getValue());
				visita_domiciliaria.setBarrio("");
				visita_domiciliaria.setLocalidad("");
				visita_domiciliaria.setCodigo_usuario(tbxCodigo_usuario
						.getValue());
				visita_domiciliaria.setParentesco(lbxParentesco
						.getSelectedItem().getValue().toString());
				visita_domiciliaria.setFamiliar_nombre1(tbxFamiliar_nombre1
						.getValue());
				visita_domiciliaria.setFamiliar_edad1((ibxFamiliar_edad1
						.getValue() != null ? ibxFamiliar_edad1.getValue() + ""
						: ""));
				visita_domiciliaria
						.setFamiliar_escolaridad1(tbxFamiliar_escolaridad1
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco1(tbxFamiliar_parentesco1
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion1(tbxFamiliar_ocupacion1
								.getValue());
				visita_domiciliaria.setFamiliar_nombre2(tbxFamiliar_nombre2
						.getValue());
				visita_domiciliaria.setFamiliar_edad2((ibxFamiliar_edad2
						.getValue() != null ? ibxFamiliar_edad2.getValue() + ""
						: ""));
				visita_domiciliaria
						.setFamiliar_escolaridad2(tbxFamiliar_escolaridad2
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco2(tbxFamiliar_parentesco2
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion2(tbxFamiliar_ocupacion2
								.getValue());
				visita_domiciliaria.setFamiliar_nombre3(tbxFamiliar_nombre3
						.getValue());
				visita_domiciliaria.setFamiliar_edad3((ibxFamiliar_edad3
						.getValue() != null ? ibxFamiliar_edad3.getValue() + ""
						: ""));
				visita_domiciliaria
						.setFamiliar_escolaridad3(tbxFamiliar_escolaridad3
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco3(tbxFamiliar_parentesco3
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion3(tbxFamiliar_ocupacion3
								.getValue());
				visita_domiciliaria.setFamiliar_nombre4(tbxFamiliar_nombre4
						.getValue());
				visita_domiciliaria.setFamiliar_edad4((ibxFamiliar_edad4
						.getValue() != null ? ibxFamiliar_edad4.getValue() + ""
						: ""));
				visita_domiciliaria
						.setFamiliar_escolaridad4(tbxFamiliar_escolaridad4
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco4(tbxFamiliar_parentesco4
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion4(tbxFamiliar_ocupacion4
								.getValue());
				visita_domiciliaria.setFamiliar_nombre5(tbxFamiliar_nombre5
						.getValue());
				visita_domiciliaria.setFamiliar_edad5((ibxFamiliar_edad5
						.getValue() != null ? ibxFamiliar_edad5.getValue() + ""
						: ""));
				visita_domiciliaria
						.setFamiliar_escolaridad5(tbxFamiliar_escolaridad5
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco5(tbxFamiliar_parentesco5
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion5(tbxFamiliar_ocupacion5
								.getValue());
				visita_domiciliaria.setFamiliar_nombre6(tbxFamiliar_nombre6
						.getValue());
				visita_domiciliaria.setFamiliar_edad6((ibxFamiliar_edad6
						.getValue() != null ? ibxFamiliar_edad6.getValue() + ""
						: ""));
				visita_domiciliaria
						.setFamiliar_escolaridad6(tbxFamiliar_escolaridad6
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco6(tbxFamiliar_parentesco6
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion6(tbxFamiliar_ocupacion6
								.getValue());
				visita_domiciliaria.setFamiliar_nombre7(tbxFamiliar_nombre7
						.getValue());
				visita_domiciliaria.setFamiliar_edad7((ibxFamiliar_edad7
						.getValue() != null ? ibxFamiliar_edad7.getValue() + ""
						: ""));
				visita_domiciliaria
						.setFamiliar_escolaridad7(tbxFamiliar_escolaridad7
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco7(tbxFamiliar_parentesco7
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion7(tbxFamiliar_ocupacion7
								.getValue());
				visita_domiciliaria.setFamiliar_nombre8(tbxFamiliar_nombre8
						.getValue());
				visita_domiciliaria.setFamiliar_edad8((ibxFamiliar_edad8
						.getValue() != null ? ibxFamiliar_edad8.getValue() + ""
						: ""));
				visita_domiciliaria
						.setFamiliar_escolaridad8(tbxFamiliar_escolaridad8
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco8(tbxFamiliar_parentesco8
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion8(tbxFamiliar_ocupacion8
								.getValue());
				visita_domiciliaria.setFamiliar_nombre9(tbxFamiliar_nombre9
						.getValue());
				visita_domiciliaria.setFamiliar_edad9((ibxFamiliar_edad9
						.getValue() != null ? ibxFamiliar_edad9.getValue() + ""
						: ""));
				visita_domiciliaria
						.setFamiliar_escolaridad9(tbxFamiliar_escolaridad9
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco9(tbxFamiliar_parentesco9
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion9(tbxFamiliar_ocupacion9
								.getValue());
				visita_domiciliaria.setFamiliar_nombre10(tbxFamiliar_nombre10
						.getValue());
				visita_domiciliaria.setFamiliar_edad10((ibxFamiliar_edad10
						.getValue() != null ? ibxFamiliar_edad10.getValue()
						+ "" : ""));
				visita_domiciliaria
						.setFamiliar_escolaridad10(tbxFamiliar_escolaridad10
								.getValue());
				visita_domiciliaria
						.setFamiliar_parentesco10(tbxFamiliar_parentesco10
								.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setFamiliar_ocupacion10(tbxFamiliar_ocupacion10
								.getValue());
				visita_domiciliaria.setTecho(lbxTecho.getSelectedItem()
						.getValue().toString());
				visita_domiciliaria.setPiso(lbxPiso.getSelectedItem()
						.getValue().toString());
				visita_domiciliaria.setParedes(lbxParedes.getSelectedItem()
						.getValue().toString());
				visita_domiciliaria.setNo_habitaciones(lbxNo_habitaciones
						.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setEliminacion_excretas(lbxEliminacion_excretas
								.getSelectedItem().getValue().toString());
				visita_domiciliaria.setRecoleccion(rdbRecoleccion
						.getSelectedItem().getValue().toString());
				visita_domiciliaria.setAgua_potable(rdbAgua_potable
						.getSelectedItem().getValue().toString());
				visita_domiciliaria.setEnfermedad_actual(tbxEnfermedad_actual
						.getValue());
				visita_domiciliaria.setRecibido_medico(rdbRecibido_medico
						.getSelectedItem().getValue().toString());
				visita_domiciliaria.setTipo_atencion(tbxTipo_atencion
						.getValue());
				visita_domiciliaria.setEntidad(tbxEntidad.getValue());
				visita_domiciliaria.setProfesional(tbxProfesional.getValue());
				visita_domiciliaria.setMotivo(tbxMotivo.getValue());
				visita_domiciliaria
						.setHistoria_enfermedad_actual(tbxHistoria_enfermedad_actual
								.getValue());
				visita_domiciliaria.setAcciones_pyp(tbxAcciones_pyp.getValue());
				visita_domiciliaria.setCanalizacion(rdbCanalizacion
						.getSelectedItem().getValue().toString());
				visita_domiciliaria
						.setEntidad_canalizacion(tbxEntidad_canalizacion
								.getValue());
				visita_domiciliaria.setTratamiento_actual(tbxTratamiento_actual
						.getValue());
				visita_domiciliaria.setActitud_familiares(tbxActitud_familiares
						.getValue());
				visita_domiciliaria.setPercepcion(tbxPercepcion.getValue());
				visita_domiciliaria.setDisposicion(tbxDisposicion.getValue());
				visita_domiciliaria
						.setPercepcion_paciente(tbxPercepcion_paciente
								.getValue());

				visita_domiciliaria.setObservacion(tbxObservacion.getValue());

				visita_domiciliaria.setSeguimiento(tbxSeguimiento.getValue());
				visita_domiciliaria.setRecomendacion(tbxRecomendacion
						.getValue());
				visita_domiciliaria
						.setCodigo_historia_anterior(codigo_historia_anterior);
				visita_domiciliaria.setTipo_historia(tipo_historia);
				visita_domiciliaria.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				visita_domiciliaria.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				visita_domiciliaria.setCreacion_user(usuarios.getCodigo()
						.toString());
				visita_domiciliaria.setDelete_date(null);
				visita_domiciliaria.setUltimo_user(usuarios.getCodigo()
						.toString());
				visita_domiciliaria.setDelete_user(null);

				visita_domiciliaria.setOtros_piso(tbxOtros_piso.getValue());
				visita_domiciliaria.setOtros_paredes(tbxOtros_paredes
						.getValue());
				visita_domiciliaria.setOtros_techo(tbxOtros_techo.getValue());
				visita_domiciliaria.setOtros_excretas(tbxOtros_excretas
						.getValue());

				datos.put("visita_domiciliaria", visita_domiciliaria);
				datos.put("admision", admision);
				datos.put("accion", tbxAccion.getText());

				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();
				datos.put("impresion_diagnostica", impresion_diagnostica);

				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);

				visita_domiciliaria = getServiceLocator()
						.getVisita_domiciliariaService().guardar(datos);
				infoPacientes.setCodigo_historia(visita_domiciliaria
						.getCodigo_historia());
				tbxAccion.setValue("modificar");

				actualizarAutorizaciones(admision,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision, getInformacionClinica(), this);

				MensajesUtil.mensajeInformacion("Informacion ..",
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
		Visita_domiciliaria visita_domiciliaria = (Visita_domiciliaria) obj;
		try {
			infoPacientes.setCodigo_historia(visita_domiciliaria
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(visita_domiciliaria
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					visita_domiciliaria.getUltimo_update());

			initMostrar_datos(visita_domiciliaria);

			cargarInformacion_paciente();
			cargarRemisionInterna(visita_domiciliaria);

			for (int i = 0; i < lbxNotifica.getItemCount(); i++) {
				Listitem listitem = lbxNotifica.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getNotifica())) {
					listitem.setSelected(true);
					i = lbxNotifica.getItemCount();
				}
			}

			tbxOtros_notifica.setValue(visita_domiciliaria.getOtros_notifica());

			tbxCodigo_usuario.setValue(visita_domiciliaria.getCodigo_usuario());
			for (int i = 0; i < lbxParentesco.getItemCount(); i++) {
				Listitem listitem = lbxParentesco.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getParentesco())) {
					listitem.setSelected(true);
					i = lbxParentesco.getItemCount();
				}
			}

			tbxFamiliar_nombre1.setValue(visita_domiciliaria
					.getFamiliar_nombre1());
			ibxFamiliar_edad1
					.setValue((visita_domiciliaria.getFamiliar_edad1() != null && !visita_domiciliaria
							.getFamiliar_edad1().isEmpty()) ? Integer
							.parseInt(visita_domiciliaria.getFamiliar_edad1())
							: null);

			Nivel_educativo educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad1());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);

			tbxNombre_Familiar_escolaridad1
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad1.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco1.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco1.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco1())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco1.getItemCount();
				}
			}

			Ocupaciones ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion1());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion1
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion1.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			tbxFamiliar_nombre2.setValue(visita_domiciliaria
					.getFamiliar_nombre2());
			ibxFamiliar_edad2
					.setValue((visita_domiciliaria.getFamiliar_edad2() != null && !visita_domiciliaria
							.getFamiliar_edad2().isEmpty()) ? Integer
							.parseInt(visita_domiciliaria.getFamiliar_edad2())
							: null);

			educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad2());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);
			tbxNombre_Familiar_escolaridad2
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad2.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco2.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco2.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco2())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco2.getItemCount();
				}
			}

			ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion2());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion2
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion2.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			tbxFamiliar_nombre3.setValue(visita_domiciliaria
					.getFamiliar_nombre3());
			ibxFamiliar_edad3
					.setValue((visita_domiciliaria.getFamiliar_edad3() != null && !visita_domiciliaria
							.getFamiliar_edad3().isEmpty()) ? Integer
							.parseInt(visita_domiciliaria.getFamiliar_edad3())
							: null);

			educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad3());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);
			tbxNombre_Familiar_escolaridad3
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad3.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco3.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco3.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco3())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco3.getItemCount();
				}
			}
			ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion3());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion3
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion3.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			tbxFamiliar_nombre4.setValue(visita_domiciliaria
					.getFamiliar_nombre4());
			ibxFamiliar_edad4
					.setValue((visita_domiciliaria.getFamiliar_edad4() != null && !visita_domiciliaria
							.getFamiliar_edad4().isEmpty()) ? Integer
							.parseInt(visita_domiciliaria.getFamiliar_edad4())
							: null);

			educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad4());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);
			tbxNombre_Familiar_escolaridad4
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad4.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco4.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco4.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco4())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco4.getItemCount();
				}
			}

			ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion4());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion4
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion4.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			tbxFamiliar_nombre5.setValue(visita_domiciliaria
					.getFamiliar_nombre5());
			ibxFamiliar_edad5
					.setValue((visita_domiciliaria.getFamiliar_edad5() != null && !visita_domiciliaria
							.getFamiliar_edad5().isEmpty()) ? Integer
							.parseInt(visita_domiciliaria.getFamiliar_edad5())
							: null);

			educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad5());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);
			tbxNombre_Familiar_escolaridad5
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad5.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco5.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco5.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco5())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco5.getItemCount();
				}
			}

			ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion5());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion5
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion5.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			tbxFamiliar_nombre6.setValue(visita_domiciliaria
					.getFamiliar_nombre6());
			ibxFamiliar_edad6
					.setValue((visita_domiciliaria.getFamiliar_edad6() != null && !visita_domiciliaria
							.getFamiliar_edad6().isEmpty()) ? Integer
							.parseInt(visita_domiciliaria.getFamiliar_edad6())
							: null);

			educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad6());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);
			tbxNombre_Familiar_escolaridad6
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad6.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco6.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco6.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco6())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco6.getItemCount();
				}
			}

			ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion6());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion6
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion6.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			tbxFamiliar_nombre7.setValue(visita_domiciliaria
					.getFamiliar_nombre7());
			ibxFamiliar_edad7
					.setValue((visita_domiciliaria.getFamiliar_edad7() != null && !visita_domiciliaria
							.getFamiliar_edad7().isEmpty()) ? Integer
							.parseInt(visita_domiciliaria.getFamiliar_edad7())
							: null);

			educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad7());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);
			tbxNombre_Familiar_escolaridad7
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad7.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco7.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco7.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco7())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco7.getItemCount();
				}
			}

			ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion7());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion7
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion7.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			tbxFamiliar_nombre8.setValue(visita_domiciliaria
					.getFamiliar_nombre8());
			ibxFamiliar_edad8
					.setValue((visita_domiciliaria.getFamiliar_edad8() != null && !visita_domiciliaria
							.getFamiliar_edad8().isEmpty()) ? Integer
							.parseInt(visita_domiciliaria.getFamiliar_edad8())
							: null);

			educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad8());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);
			tbxNombre_Familiar_escolaridad8
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad8.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco8.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco8.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco8())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco8.getItemCount();
				}
			}

			ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion8());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion8
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion8.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			tbxFamiliar_nombre9.setValue(visita_domiciliaria
					.getFamiliar_nombre9());
			ibxFamiliar_edad9
					.setValue((visita_domiciliaria.getFamiliar_edad9() != null && !visita_domiciliaria
							.getFamiliar_edad9().isEmpty()) ? Integer
							.parseInt(visita_domiciliaria.getFamiliar_edad9())
							: null);

			educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad9());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);
			tbxNombre_Familiar_escolaridad9
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad9.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco9.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco9.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco9())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco9.getItemCount();
				}
			}

			ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion9());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion9
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion9.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			tbxFamiliar_nombre10.setValue(visita_domiciliaria
					.getFamiliar_nombre10());
			ibxFamiliar_edad10.setValue((visita_domiciliaria
					.getFamiliar_edad10() != null && !visita_domiciliaria
					.getFamiliar_edad10().isEmpty()) ? Integer
					.parseInt(visita_domiciliaria.getFamiliar_edad10()) : null);

			educativo = new Nivel_educativo();
			educativo.setId(visita_domiciliaria.getFamiliar_escolaridad10());
			educativo = getServiceLocator().getNivel_educativoService()
					.consultar(educativo);
			tbxNombre_Familiar_escolaridad10
					.setValue(educativo != null ? educativo.getNombre() : "");
			tbxFamiliar_escolaridad10.setValue(educativo != null ? educativo
					.getId() : "");

			for (int i = 0; i < tbxFamiliar_parentesco10.getItemCount(); i++) {
				Listitem listitem = tbxFamiliar_parentesco10.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getFamiliar_parentesco10())) {
					listitem.setSelected(true);
					i = tbxFamiliar_parentesco10.getItemCount();
				}
			}

			ocupacion = new Ocupaciones();
			ocupacion.setId(visita_domiciliaria.getFamiliar_ocupacion10());
			ocupacion = getServiceLocator().getOcupacionesService().consultar(
					ocupacion);
			tbxNombre_Familiar_ocupacion10
					.setValue(ocupacion != null ? ocupacion.getNombre() : "");
			tbxFamiliar_ocupacion10.setValue(ocupacion != null ? ocupacion
					.getId() : "");

			for (int i = 0; i < lbxTecho.getItemCount(); i++) {
				Listitem listitem = lbxTecho.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getTecho())) {
					listitem.setSelected(true);
					i = lbxTecho.getItemCount();
				}
			}

			if (visita_domiciliaria.getTecho().equals("5")) {
				tbxOtros_techo.setVisible(true);
				tbxOtros_techo.setValue(visita_domiciliaria.getOtros_techo());
			} else {
				tbxOtros_techo.setVisible(false);

			}

			for (int i = 0; i < lbxPiso.getItemCount(); i++) {
				Listitem listitem = lbxPiso.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getPiso())) {
					listitem.setSelected(true);
					i = lbxPiso.getItemCount();
				}
			}

			if (visita_domiciliaria.getPiso().equals("5")) {
				tbxOtros_piso.setVisible(true);
				tbxOtros_piso.setValue(visita_domiciliaria.getOtros_piso());
			} else {
				tbxOtros_piso.setVisible(false);

			}

			for (int i = 0; i < lbxParedes.getItemCount(); i++) {
				Listitem listitem = lbxParedes.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getParedes())) {
					listitem.setSelected(true);
					i = lbxParedes.getItemCount();
				}
			}

			if (visita_domiciliaria.getParedes().equals("6")) {
				tbxOtros_paredes.setVisible(true);
				tbxOtros_paredes.setValue(visita_domiciliaria
						.getOtros_paredes());
			} else {
				tbxOtros_paredes.setVisible(false);

			}

			for (int i = 0; i < lbxNo_habitaciones.getItemCount(); i++) {
				Listitem listitem = lbxNo_habitaciones.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getNo_habitaciones())) {
					listitem.setSelected(true);
					i = lbxNo_habitaciones.getItemCount();
				}
			}

			for (int i = 0; i < lbxEliminacion_excretas.getItemCount(); i++) {
				Listitem listitem = lbxEliminacion_excretas.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(visita_domiciliaria.getEliminacion_excretas())) {
					listitem.setSelected(true);
					i = lbxEliminacion_excretas.getItemCount();
				}
			}

			if (visita_domiciliaria.getEliminacion_excretas().equals("4")) {
				tbxOtros_excretas.setVisible(true);
				tbxOtros_excretas.setValue(visita_domiciliaria
						.getOtros_excretas());
			} else {
				tbxOtros_excretas.setVisible(false);

			}

			Utilidades.seleccionarRadio(rdbRecoleccion,
					visita_domiciliaria.getRecoleccion());
			Utilidades.seleccionarRadio(rdbAgua_potable,
					visita_domiciliaria.getAgua_potable());
			tbxEnfermedad_actual.setValue(visita_domiciliaria
					.getEnfermedad_actual());
			Utilidades.seleccionarRadio(rdbRecibido_medico,
					visita_domiciliaria.getRecibido_medico());

			if (visita_domiciliaria.getRecibido_medico().equals("1")) {
				rowMot1.setVisible(true);
				rowMot2.setVisible(true);
				rowMot3.setVisible(true);
				rowMot4.setVisible(true);
				lbTipo_atencion.setVisible(true);
				lbEntidad.setVisible(true);
				lbProfesional.setVisible(true);
				lbMotivo.setVisible(true);

				tbxTipo_atencion.setVisible(true);
				tbxEntidad.setVisible(true);
				tbxProfesional.setVisible(true);
				tbxMotivo.setVisible(true);
				tbxTipo_atencion.setValue(visita_domiciliaria
						.getTipo_atencion());
				tbxEntidad.setValue(visita_domiciliaria.getEntidad());
				tbxProfesional.setValue(visita_domiciliaria.getProfesional());
				tbxMotivo.setValue(visita_domiciliaria.getMotivo());

			} else {
				rowMot1.setVisible(false);
				rowMot2.setVisible(false);
				rowMot3.setVisible(false);
				rowMot4.setVisible(false);
				lbTipo_atencion.setVisible(false);
				lbEntidad.setVisible(false);
				lbProfesional.setVisible(false);
				lbMotivo.setVisible(false);

				tbxTipo_atencion.setVisible(false);
				tbxEntidad.setVisible(false);
				tbxProfesional.setVisible(false);
				tbxMotivo.setVisible(false);

			}

			tbxHistoria_enfermedad_actual.setValue(visita_domiciliaria
					.getHistoria_enfermedad_actual());
			tbxAcciones_pyp.setValue(visita_domiciliaria.getAcciones_pyp());
			Utilidades.seleccionarRadio(rdbCanalizacion,
					visita_domiciliaria.getCanalizacion());

			if (visita_domiciliaria.getCanalizacion().equals("1")) {
				rowCon.setVisible(true);
				lbMensaje.setVisible(true);

				tbxEntidad_canalizacion.setVisible(true);
				tbxEntidad_canalizacion.setValue(visita_domiciliaria
						.getEntidad_canalizacion());

			} else {
				rowCon.setVisible(false);
				lbMensaje.setVisible(false);

			}

			tbxTratamiento_actual.setValue(visita_domiciliaria
					.getTratamiento_actual());
			tbxActitud_familiares.setValue(visita_domiciliaria
					.getActitud_familiares());
			tbxPercepcion.setValue(visita_domiciliaria.getPercepcion());
			tbxDisposicion.setValue(visita_domiciliaria.getDisposicion());
			tbxPercepcion_paciente.setValue(visita_domiciliaria
					.getPercepcion_paciente());

			cargarImpresionDiagnostica(visita_domiciliaria);

			tbxObservacion.setValue(visita_domiciliaria.getObservacion());

			tbxSeguimiento.setValue(visita_domiciliaria.getSeguimiento());
			tbxRecomendacion.setValue(visita_domiciliaria.getRecomendacion());

			tbxOtros_piso.setValue(visita_domiciliaria.getOtros_piso());
			tbxOtros_paredes.setValue(visita_domiciliaria.getOtros_paredes());
			tbxOtros_techo.setValue(visita_domiciliaria.getOtros_techo());
			tbxOtros_excretas.setValue(visita_domiciliaria.getOtros_excretas());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
			inicializarVista(tipo_historia);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Visita_domiciliaria visita_domiciliaria = (Visita_domiciliaria) obj;
		try {
			int result = getServiceLocator().getVisita_domiciliariaService()
					.eliminar(visita_domiciliaria);
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

	public void buscarPaciente(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado",
					"limit 25 offset 0");

			List<Paciente> list = getServiceLocator().getPacienteService()
					.listar(parameters);

			lbx.getItems().clear();

			for (Paciente dato : list) {

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
				listcell.appendChild(new Label(dato.getNombre1() + " "
						+ dato.getNombre2()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellido1() + " "
						+ dato.getApellido2()));
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

	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Cie> list = getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class, 
					parameters);

			lbx.getItems().clear();

			for (Cie dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getSexo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_inferior()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_superior()));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void selectedDx(Listitem listitem, Bandbox bandbox, Textbox textbox) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
		} else {
			Cie dato = (Cie) listitem.getValue();
			bandbox.setValue(dato.getCodigo());
			textbox.setValue(dato.getNombre());
		}
		bandbox.close();
	}

	public void seleccion_radio(Radiogroup radiogroup,
			Component[] abstractComponents) {
		try {
			System.out.println("" + radiogroup.getSelectedItem().getValue());

			for (Component abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {
					abstractComponent.setVisible(true);
					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}

					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			//  block
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void buscarOcupaciones(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getOcupacionesService().setLimit(
					"limit 25 offset 0");

			List<Ocupaciones> list = getServiceLocator()
					.getOcupacionesService().listar(parameters);

			lbx.getItems().clear();

			for (Ocupaciones dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getId() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
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

	public void selectedOcupaciones(Listitem listitem, Textbox textbox,
			Bandbox bandbox) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");

		} else {
			Ocupaciones dato = (Ocupaciones) listitem.getValue();
			bandbox.setValue(dato.getNombre());
			textbox.setValue(dato.getId());

		}
		bandbox.close();
	}

	public void buscarCodigo_educativo(String value, Listbox lbx)
			throws Exception {
		try {
			//log.info("vALORES: " + value);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getNivel_educativoService().setLimit(
					"limit 25 offset 0");

			List<Nivel_educativo> list = getServiceLocator()
					.getNivel_educativoService().listar(parameters);

			lbx.getItems().clear();

			for (Nivel_educativo dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getId() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
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

	public void selectedCodigo_educativo(Listitem listitem, Textbox textbox,
			Bandbox bandbox) {

		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");

		} else {
			Nivel_educativo dato = (Nivel_educativo) listitem.getValue();
			bandbox.setValue(dato.getNombre());
			textbox.setValue(dato.getId());

		}
		bandbox.close();
	}

	public void seleccion(Listbox listbox, int entero,
			Component[] abstractComponents) {
		try {
			System.out.println("" + listbox.getSelectedItem().getValue());

			String num = entero + "";
			for (Component abstractComponent : abstractComponents) {

				if (listbox.getSelectedItem().getValue().equals(num)) {
					abstractComponent.setVisible(true);
					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}
					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			//  block
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public void initHistoria() throws Exception {
		if (admision != null) {
			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (opciones_via_ingreso.equals(Opciones_via_ingreso.REGISTRAR)) {
				accionForm(true, "registrar");
				btnCancelar.setVisible(false);
			} else {
				accionForm(false, "mostrar");
				btnCancelar.setVisible(true);
			}
		}
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowComposicion_familiar.setVisible(true);
			rowDescripcion_vivienda.setVisible(true);
			rowCondiciones.setVisible(true);
			rowDescripcion.setVisible(true);
			rowEnfermedad_actual.setVisible(true);
			rowIntervencion_visita.setVisible(true);
			rowTratamiento.setVisible(true);
			rowEvaluacion.setVisible(true);
			rowSeguimiento.setVisible(false);
			rowObservaciones.setVisible(true);
			rowRecomendaciones.setVisible(false);
			primeraVez = true;
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;

		} else {
			rowComposicion_familiar.setVisible(false);
			rowDescripcion_vivienda.setVisible(false);
			rowCondiciones.setVisible(false);
			rowDescripcion.setVisible(false);
			rowEnfermedad_actual.setVisible(false);
			rowIntervencion_visita.setVisible(false);
			rowTratamiento.setVisible(false);
			rowEvaluacion.setVisible(false);
			rowSeguimiento.setVisible(true);
			rowObservaciones.setVisible(false);
			rowRecomendaciones.setVisible(true);
			primeraVez = false;
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {
						//log.info("tbxAccion.getValue()  === > "
								//+ tbxAccion.getValue());

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("identificacion",
									admision.getNro_identificacion());
							parametros.put("order_desc", "order_desc");

							List<Visita_domiciliaria> listado_historias = getServiceLocator()
									.getVisita_domiciliariaService().listar(
											parametros);

							if (!listado_historias.isEmpty()) {
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								cargarInformacion_historia_anterior(listado_historias
										.get(0));
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia Clínica de Control/Evolucion");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia Clínica por Primera Vez");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								Visita_domiciliaria visita = new Visita_domiciliaria();
								visita.setCodigo_empresa(empresa
										.getCodigo_empresa());
								visita.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								visita.setCodigo_historia(codigo_historia_anterior);

								visita = getServiceLocator()
										.getVisita_domiciliariaService()
										.consultar(visita);

								if (visita != null) {
									cargarInformacion_historia_anterior(visita);
								}

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
		Visita_domiciliaria visita = (Visita_domiciliaria) historia_clinica;

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });

			if (visita.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clínica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clínica de Control/Evolucion");
			}
		} else {

			if (visita.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clínica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clínica de Control/Evolucion");
			}
		}

		codigo_historia_anterior = visita.getCodigo_historia_anterior();
		tipo_historia = visita.getTipo_historia();

	}

	private void cargarImpresionDiagnostica(
			Visita_domiciliaria visita_domiciliaria) throws Exception {

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();

		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(visita_domiciliaria
				.getCodigo_historia());
		
		impresion_diagnostica.setCie_principal(visita_domiciliaria.getCie_principal());
		impresion_diagnostica.setTipo_principal(visita_domiciliaria.getTipo_principal());
		impresion_diagnostica.setCie_relacionado1(visita_domiciliaria.getCie_relacionado1());
		impresion_diagnostica.setTipo_relacionado1(visita_domiciliaria.getTipo_relacionado1());
		impresion_diagnostica.setCie_relacionado2(visita_domiciliaria.getCie_relacionado2());
		impresion_diagnostica.setTipo_relacionado2(visita_domiciliaria.getTipo_relacionado2());
		impresion_diagnostica.setCie_relacionado3(visita_domiciliaria.getCie_relacionado3());
		impresion_diagnostica.setTipo_relacionado3(visita_domiciliaria.getTipo_relacionado3());
		impresion_diagnostica.setFinalidad_consulta(visita_domiciliaria.getFinalidad_consulta());
		impresion_diagnostica.setCausas_externas(visita_domiciliaria.getCausas_externas());
		impresion_diagnostica.setCodigo_consulta_pyp(visita_domiciliaria.getCodigo_consulta_pyp());

		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);

	}
	
	private void cargarRemisionInterna(Visita_domiciliaria visita_domiciliaria) throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(visita_domiciliaria.getCodigo_historia());
		remision_interna.setCodigo_empresa(visita_domiciliaria.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(visita_domiciliaria.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	
}
