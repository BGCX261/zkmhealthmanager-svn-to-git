/*
 * psicologiaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Psicologia;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;
import com.framework.util.Util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
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
import com.softcomputo.composer.constantes.IParametrosSesion;
import healthmanager.modelo.service.GeneralExtraService;

public class PsicologiaAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static Logger log = Logger.getLogger(PsicologiaAction.class);

	// Componentes //
	// Manuel
	@View(isMacro = true)
	private Remision_internaMacro macroRemision_interna;
	@View
	private Div divModuloRemisiones_externas;

	private Remisiones_externasAction remisiones_externasAction;
	private Citas cita;
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
	private Toolbarbutton btnCancelar;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	private String tipo_historia;
	private Long codigo_historia_anterior;

	@View
	private Listbox lbxAtendida;

	@View
	private Bandbox bandboxPrestador;
	@View
	private Textbox tbxNomPrestador;

	@View
	private Radiogroup rdbDesplazamiento;
	@View
	private Radiogroup rdbDiscapacidad;
	@View
	private Listbox lbxRemitido;
	@View
	private Textbox tbxCual_remitido;
	@View
	private Textbox tbxNombre_padre;
	@View
	private Intbox ibxEdad_padre;
	@View
	private Textbox tbxNombre_madre;
	@View
	private Intbox ibxEdad_madre;
	@View
	private Textbox tbxMotivo;
	@View
	private Radiogroup rdbAtencion;
	@View
	private Datebox dtbxFecha_atencion;
	@View
	private Radiogroup rdbPsicofarmacos;
	@View
	private Textbox tbxCual_psicofarmacos;
	@View
	private Radiogroup rdbHospitalizacion;
	@View
	private Datebox dtbxFecha_hospitalizacion;
	@View
	private Textbox tbxArea_personal;
	@View
	private Textbox tbxArea_familiar;
	@View
	private Textbox tbxArea_social;
	@View
	private Textbox tbxTratamiento;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	@View
	private Textbox tbxAnalisis_diagnostico;

	@View
	private Row row1;
	@View
	private Row row2;
	@View
	private Label lbNombre_padre;
	@View
	private Label lbEdad_padre;
	@View
	private Label lbNombre_madre;
	@View
	private Label lbEdad_madre;

	// Control
	@View
	private Textbox tbxEvolucion;

	private final String[] idsExcluyentes = { "tbxAccion",
			"btnLimpiar_prestador", "tbxValue", "lbxParameter",
			"infoPacientes", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "lbxRemitido" };

	@View
	private Row rowMotivo;
	@View
	private Row rowAntecedentes;
	@View
	private Row rowAreas_ajustes;
	@View
	private Row rowEvolucion;

	private boolean primeraVez;

	@View
	private Label lbFecha_atencion;
	@View
	private Label lbCual_psicofarmacos;
	@View
	private Label lbFecha_hospitalizacion;

	@View
	private Toolbarbutton btGuardar;

	@View
	private Textbox tbxEnfermedad;
	@View
	private Textbox tbxEducacional;
	@View
	private Textbox tbxLugar_hospitalizado;

	@View
	private Toolbarbutton btnImprimir;
	
	@Override
	public void init() {
		try {
			listarCombos();
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	/*
	 * private void onOpcionFormularioRegistrar() throws Exception {
	 * groupboxConsulta.setVisible(false); groupboxEditar.setVisible(true);
	 * limpiarDatos(); FormularioUtil.cargarRadiogroupsDefecto(this); if
	 * (admision != null) { infoPacientes.setFecha_inicio(new Date());
	 * 
	 * onMostrarModuloRemisiones(); } }
	 */

	/**
	 * Sobreescritura del metodo params(Map<String, Object> map) para obtener
	 * los parametros iniciales con los que trabajara la historia clinica
	 */
	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			cita = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			macroRemision_interna.deshabilitarCheck(admision,
							IVias_ingreso.PSICOLOGIA);
		}
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		rol_usuario = (String) request.getSession().getAttribute(IParametrosSesion.PARAM_ROL_USUARIO);
	}

	public void listarCombos() {
		listarParameter();
		listarAtendida();
		Utilidades
				.listarElementoListbox(lbxRemitido, true, getServiceLocator());

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

		if (!accion.equalsIgnoreCase("registrar")) {

			if (!accion.equalsIgnoreCase("mostrar")) {
				buscarDatos();

			}
			btGuardar.setVisible(false);
			btnImprimir.setVisible(false);

		} else {
			btGuardar.setVisible(true);
			limpiarDatos();
			FormularioUtil.cargarRadiogroupsDefecto(this);
			if (admision != null) {
				infoPacientes.setFecha_inicio(new Date());
				cargarInformacion_paciente();
				onMostrarModuloRemisiones();

			}

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
		String mensaje = "Los campos marcados con (*) son obligatorios";

		boolean valida = true;

		if (!primeraVez) {
			FormularioUtil.validarCamposObligatorios(tbxEducacional,
					tbxAnalisis_diagnostico, lbxRemitido, tbxEvolucion);
		} else {
			FormularioUtil.validarCamposObligatorios(tbxEducacional,
					tbxAnalisis_diagnostico, lbxRemitido, tbxMotivo,
					tbxEnfermedad, tbxArea_personal, tbxArea_familiar,
					tbxArea_social, tbxTratamiento);

		}

		if (valida)
			valida = remisiones_externasAction.validarRemision();

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
		//log.info(mensaje);

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

			parameters.put("limite_paginado","limit 25 offset 0");

			List<Psicologia> lista_datos = getServiceLocator()
					.getPsicologiaService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Psicologia psicologia : lista_datos) {
				rowsResultado.appendChild(crearFilas(psicologia, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Row crearFilas(Object objeto, AbstractComponent componente)
			throws Exception {
		Row fila = new Row();

		final Psicologia psicologia = (Psicologia) objeto;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(psicologia.getCodigo_historia() + ""));
		fila.appendChild(new Label(psicologia.getIdentificacion() + ""));

		fila.appendChild(new Label(admision.getPaciente().getNombre1()
				+ (admision.getPaciente().getNombre2().isEmpty() ? "" : " "
						+ admision.getPaciente().getNombre2()) + " "
				+ admision.getPaciente().getApellido1() + " "
				+ admision.getPaciente().getApellido2() + ""));

		Datebox datebox = new Datebox(psicologia.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		fila.appendChild(new Label(psicologia.getTipo_historia().equals(
				IConstantes.TIPO_HISTORIA_PRIMERA_VEZ) ? "PRIMERA VEZ"
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
						groupboxConsulta.setVisible(false);
						groupboxEditar.setVisible(true);
						mostrarDatos(psicologia);
					}
				});
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);

		return fila;
	}
	
	public Psicologia getBean(){
		Psicologia psicologia = new Psicologia();
		psicologia.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));
		psicologia
				.setUltimo_update(new Timestamp(new Date().getTime()));
		psicologia.setCodigo_empresa(empresa.getCodigo_empresa());
		psicologia.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		psicologia.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		psicologia.setCodigo_prestador(bandboxPrestador.getValue());
		psicologia.setIdentificacion(admision != null ? admision
				.getNro_identificacion() : null);
		psicologia.setFecha_inicial(new Timestamp(infoPacientes
				.getFecha_inicial().getTime()));
		psicologia.setNro_ingreso(admision.getNro_ingreso());
		psicologia.setCentro_atencion("110");
		psicologia.setDesplazamiento(rdbDesplazamiento
				.getSelectedItem().getValue().toString());
		psicologia.setDiscapacidad(rdbDiscapacidad.getSelectedItem()
				.getValue().toString());
		psicologia.setRemitido(lbxRemitido.getSelectedItem().getValue()
				.toString());
		psicologia.setCual_remitido(tbxCual_remitido.getValue());
		psicologia.setNombre_padre(tbxNombre_padre.getValue());
		psicologia
				.setEdad_padre((ibxEdad_padre.getValue() != null ? ibxEdad_padre
						.getValue() + ""
						: ""));
		psicologia.setNombre_madre(tbxNombre_madre.getValue());
		psicologia
				.setEdad_madre((ibxEdad_madre.getValue() != null ? ibxEdad_madre
						.getValue() + ""
						: ""));
		psicologia.setMotivo(tbxMotivo.getValue());
		psicologia.setAtencion(rdbAtencion.getSelectedItem().getValue()
				.toString());
		if (dtbxFecha_atencion.getValue() != null) {
			psicologia.setFecha_atencion(new Timestamp(
					dtbxFecha_atencion.getValue().getTime()));
		} else {
			psicologia.setFecha_atencion(null);
		}
		psicologia.setPsicofarmacos(rdbPsicofarmacos.getSelectedItem()
				.getValue().toString());
		psicologia.setCual_psicofarmacos(tbxCual_psicofarmacos
				.getValue());
		psicologia.setHospitalizacion(rdbHospitalizacion
				.getSelectedItem().getValue().toString());

		if (dtbxFecha_hospitalizacion.getValue() != null) {
			psicologia.setFecha_hospitalizacion(new Timestamp(
					dtbxFecha_hospitalizacion.getValue().getTime()));
		} else {
			psicologia.setFecha_hospitalizacion(null);
		}
		psicologia.setArea_personal(tbxArea_personal.getValue());
		psicologia.setArea_familiar(tbxArea_familiar.getValue());
		psicologia.setArea_social(tbxArea_social.getValue());

		psicologia.setAnalisis_diagnostico(tbxAnalisis_diagnostico
				.getValue());
		psicologia.setTratamiento(tbxTratamiento.getValue());
		psicologia.setEvolucion(tbxEvolucion.getValue());
		psicologia
				.setCodigo_historia_anterior(codigo_historia_anterior);
		psicologia.setTipo_historia(tipo_historia);
		psicologia.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		psicologia.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		psicologia.setCreacion_user(usuarios.getCodigo().toString());
		psicologia.setDelete_date(null);
		psicologia.setUltimo_user(usuarios.getCodigo().toString());
		psicologia.setDelete_user(null);
		psicologia.setEnfermedad(tbxEnfermedad.getValue());
		psicologia.setEducacional(tbxEducacional.getValue());
		psicologia.setLugar_hospitalizado(tbxLugar_hospitalizado
				.getValue());
		return psicologia;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {

			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);

				Map<String, Object> datos = new HashMap<String, Object>();

				Psicologia psicologia = getBean();
				
				datos.put("psicologia", psicologia);
				datos.put("accion", tbxAccion.getText());
				datos.put("admision", admision);
				datos.put("cita_seleccionada", cita);
				
				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();
				datos.put("impresion_diagnostica", impresion_diagnostica);

				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				datos.put("remision_interna", remision_interna);

				Anexo9_entidad anexo9_entidad = remisiones_externasAction.obtenerAnexo9();
				datos.put("anexo9", anexo9_entidad);
				
				psicologia = getServiceLocator().getPsicologiaService()
						.guardar(datos);
				
				infoPacientes.setCodigo_historia(psicologia
						.getCodigo_historia());
				tbxAccion.setValue("modificar");
				
				actualizarAutorizaciones(admision,
						impresion_diagnostica.getCausas_externas(),
						impresion_diagnostica.getCie_principal(),
						impresion_diagnostica.getCie_relacionado1(),
						impresion_diagnostica.getCie_relacionado2(), this);
				actualizarRemision(admision, getInformacionClinica(), this);


				if(anexo9_entidad != null){
					remisiones_externasAction.setCodigo_remision(anexo9_entidad.getCodigo());
					remisiones_externasAction.getBotonImprimir().setDisabled(false);
				}
				
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
				btnImprimir.setVisible(true);

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
		Psicologia psicologia = (Psicologia) obj;
		try {
			infoPacientes.setCodigo_historia(psicologia.getCodigo_historia());
			infoPacientes.setFecha_inicio(psicologia.getFecha_inicial());
			infoPacientes.setFecha_cierre(true, psicologia.getUltimo_update());

			onMostrarModuloRemisiones();
			initMostrar_datos(psicologia);
			cargarInformacion_paciente();
			cargarRemisionInterna(psicologia);

			// listarCombos();

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(psicologia.getCodigo_empresa());
			prestadores.setCodigo_sucursal(psicologia.getCodigo_sucursal());
			prestadores.setNro_identificacion(psicologia.getCodigo_prestador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);

			bandboxPrestador.setValue(psicologia.getCodigo_prestador());
			tbxNomPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));

			if (Integer.parseInt(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(admision.getPaciente()
					.getFecha_nacimiento()), admision.getPaciente()
					.getUnidad_medidad(), false)) <= 18) {
				row1.setVisible(true);
				row2.setVisible(true);
				tbxNombre_padre.setVisible(true);
				tbxNombre_madre.setVisible(true);
				ibxEdad_padre.setVisible(true);
				ibxEdad_madre.setVisible(true);
				lbNombre_padre.setVisible(true);
				lbEdad_padre.setVisible(true);
				lbNombre_madre.setVisible(true);
				lbEdad_madre.setVisible(true);

			} else {
				// label.setVisible(false);
				row1.setVisible(false);
				row2.setVisible(false);
				lbNombre_padre.setVisible(false);
				lbEdad_padre.setVisible(false);
				lbNombre_madre.setVisible(false);
				lbEdad_madre.setVisible(false);

			}

			tbxNombre_padre.setValue(psicologia.getNombre_padre());
			ibxEdad_padre
					.setValue((psicologia.getEdad_padre() != null && !psicologia
							.getEdad_padre().isEmpty()) ? Integer
							.parseInt(psicologia.getEdad_padre()) : null);
			tbxNombre_madre.setValue(psicologia.getNombre_madre());
			ibxEdad_madre
					.setValue((psicologia.getEdad_madre() != null && !psicologia
							.getEdad_madre().isEmpty()) ? Integer
							.parseInt(psicologia.getEdad_madre()) : null);

			Utilidades.seleccionarRadio(rdbDesplazamiento,
					psicologia.getDesplazamiento());
			Utilidades.seleccionarRadio(rdbDiscapacidad,
					psicologia.getDiscapacidad());

			Utilidades.seleccionarListitem(lbxRemitido,
					psicologia.getRemitido());

			lbxRemitido.setDisabled(true);

			
			if (psicologia.getRemitido().equals("6")) {
				tbxCual_remitido.setVisible(true);
				tbxCual_remitido.setValue(psicologia.getCual_remitido());
			} else {
				tbxCual_remitido.setVisible(false);

			}

			tbxMotivo.setValue(psicologia.getMotivo());
			Utilidades.seleccionarRadio(rdbAtencion, psicologia.getAtencion());

			if (psicologia.getAtencion().equals("1")) {
				lbFecha_atencion.setVisible(true);
				dtbxFecha_atencion.setVisible(true);
				dtbxFecha_atencion.setValue(psicologia.getFecha_atencion());
			} else {
				lbFecha_atencion.setVisible(false);
				dtbxFecha_atencion.setVisible(false);

			}
			Utilidades.seleccionarRadio(rdbPsicofarmacos,
					psicologia.getPsicofarmacos());

			if (psicologia.getPsicofarmacos().equals("1")) {
				lbCual_psicofarmacos.setVisible(true);
				tbxCual_psicofarmacos.setVisible(true);
				tbxCual_psicofarmacos.setValue(psicologia
						.getCual_psicofarmacos());
			} else {
				lbCual_psicofarmacos.setVisible(false);
				tbxCual_psicofarmacos.setVisible(false);

			}

			Utilidades.seleccionarRadio(rdbHospitalizacion,	psicologia.getHospitalizacion());

			if (psicologia.getHospitalizacion().equals("1")) {
				lbFecha_hospitalizacion.setVisible(true);
				dtbxFecha_hospitalizacion.setVisible(true);
				dtbxFecha_hospitalizacion.setValue(psicologia
						.getFecha_hospitalizacion());
			} else {
				lbFecha_hospitalizacion.setVisible(false);
				dtbxFecha_hospitalizacion.setVisible(false);

			}

			tbxArea_personal.setValue(psicologia.getArea_personal());
			tbxArea_familiar.setValue(psicologia.getArea_familiar());
			tbxArea_social.setValue(psicologia.getArea_social());

			cargarImpresionDiagnostica(psicologia);

			tbxAnalisis_diagnostico.setValue(psicologia
					.getAnalisis_diagnostico());
			tbxTratamiento.setValue(psicologia.getTratamiento());
			tbxEnfermedad.setValue(psicologia.getEnfermedad());
			tbxEducacional.setValue(psicologia.getEducacional());
			tbxLugar_hospitalizado
					.setValue(psicologia.getLugar_hospitalizado());
			tbxEvolucion.setValue(psicologia.getEvolucion());

			// Mostramos la vista //
			// tbxAccion.setText("modificar");
			// accionForm(true, tbxAccion.getText());
			inicializarVista(tipo_historia);
			cargarAnexo9_remision(psicologia);
			btnImprimir.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Psicologia psicologia = (Psicologia) obj;
		try {
			int result = getServiceLocator().getPsicologiaService().eliminar(
					psicologia);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
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
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
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
			/*
			 * Admision admision = new Admision();
			 * admision.setCodigo_empresa(empresa.getCodigo_empresa());
			 * admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			 * admision.setNro_identificacion(tbxNro_identificacion.getValue());
			 * admision.setNro_ingreso(tbxNro_ingreso.getValue()); admision =
			 * getServiceLocator().getAdmisionService().consultar( admision);
			 */

			if (admision != null) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(admision.getCodigo_empresa());
				prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
				prestadores.setNro_identificacion(admision.getCodigo_medico());
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);

				bandboxPrestador.setValue((prestadores != null ? prestadores
						.getNro_identificacion() : "000000000"));
				tbxNomPrestador.setValue((prestadores != null ? prestadores
						.getNombres() + " " + prestadores.getApellidos()
						: "MEDICO POR DEFECTO"));
			} else {
				bandboxPrestador.setValue("000000000");
				tbxNomPrestador.setValue("MEDICO POR DEFECTO");
			}
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

	public void seleccion(Listbox listbox, int entero,
			AbstractComponent[] abstractComponents) {
		try {
			//System.Out.Println("" + listbox.getSelectedItem().getValue());

			String num = entero + "";
			for (AbstractComponent abstractComponent : abstractComponents) {

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
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_radio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			//System.Out.Println("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {
					abstractComponent.setVisible(true);
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());

					}
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
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public void initHistoria() throws Exception {
		
		macroImpresion_diagnostica.inicializarMacro(this, admision,
				IVias_ingreso.PSICOLOGIA);
		macroRemision_interna.inicializarMacro(this, admision,
				IVias_ingreso.PSICOLOGIA);
		
		
		if (admision != null) {
			toolbarbuttonPaciente_admisionado1.setLabel(admision.getPaciente()
					.getNombreCompleto());
			toolbarbuttonPaciente_admisionado2.setLabel(admision.getPaciente()
					.getNombreCompleto());

			if (admision.getAtendida()) {
				opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
				Psicologia psicologia = new Psicologia();
				psicologia.setCodigo_empresa(empresa.getCodigo_empresa());
				psicologia.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				psicologia.setIdentificacion(admision.getNro_identificacion());
				psicologia.setNro_ingreso(admision.getNro_ingreso());

				//log.info("psicologia - initHistoria " + psicologia);

				psicologia = getServiceLocator().getPsicologiaService()
						.consultar_historia(psicologia);

				//log.info("psicologia " + psicologia);

				if (psicologia != null) {

					accionForm(true, "mostrar");
					mostrarDatos(psicologia);
					btnCancelar.setVisible(true);
					infoPacientes.setCodigo_historia(psicologia
							.getCodigo_historia());
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
		}
	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowMotivo.setVisible(true);
			rowAntecedentes.setVisible(true);
			rowAreas_ajustes.setVisible(true);
			rowEvolucion.setVisible(false);
			// rowTratamiento.setVisible(true);
			primeraVez = true;
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			rowMotivo.setVisible(false);
			rowAntecedentes.setVisible(false);
			rowAreas_ajustes.setVisible(false);
			rowEvolucion.setVisible(true);
			// rowTratamiento.setVisible(false);
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

						Paciente paciente = admision.getPaciente();

						if (Integer.parseInt(Util.getEdad(
								new java.text.SimpleDateFormat("dd/MM/yyyy")
										.format(paciente.getFecha_nacimiento()),
								paciente.getUnidad_medidad(), false)) <= 18) {
							row1.setVisible(true);
							row2.setVisible(true);
							tbxNombre_padre.setVisible(true);
							tbxNombre_madre.setVisible(true);
							ibxEdad_padre.setVisible(true);
							ibxEdad_madre.setVisible(true);
							lbNombre_padre.setVisible(true);
							lbEdad_padre.setVisible(true);
							lbNombre_madre.setVisible(true);
							lbEdad_madre.setVisible(true);

						} else {
							// label.setVisible(false);
							row1.setVisible(false);
							row2.setVisible(false);
							lbNombre_padre.setVisible(false);
							lbEdad_padre.setVisible(false);
							lbNombre_madre.setVisible(false);
							lbEdad_madre.setVisible(false);

							tbxNombre_padre.setValue("");
							ibxEdad_padre.setText("");
							tbxNombre_madre.setValue("");
							ibxEdad_madre.setText("");

						}

						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("identificacion",
									admision.getNro_identificacion());
							parametros.put("order_desc", "order_desc");

							List<Psicologia> listado_historias = getServiceLocator()
									.getPsicologiaService().listar(parametros);

							if (!listado_historias.isEmpty()) {
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								cargarInformacion_historia_anterior(listado_historias
										.get(0));
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia Clínica de Control/Evolucion");
								admision.setPrimera_vez("N");
							} else {
								toolbarbuttonTipo_historia
										.setLabel("Creando Historia Clínica por Primera Vez");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
								admision.setPrimera_vez("S");
							}
						} else {
							if (tipo_historia
									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
								Psicologia psico = new Psicologia();
								psico.setCodigo_empresa(empresa
										.getCodigo_empresa());
								psico.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								psico.setCodigo_historia(codigo_historia_anterior);

								psico = getServiceLocator()
										.getPsicologiaService()
										.consultar(psico);

								if (psico != null) {
									cargarInformacion_historia_anterior(psico);
								}

							}
						}

					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

		/*
		 * Psicologia psicologia = (Psicologia) historia_anterior;
		 * tbxAnalisis_diagnostico
		 * .setValue(psicologia.getAnalisis_diagnostico());
		 * tbxObservacion.setValue(psicologia.getObservacion());
		 * tbxEvolucion.setValue(psicologia.getEvolucion());
		 */

	}

	private void cargarRemisionInterna(Psicologia psicologia) throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(psicologia.getCodigo_historia());
		remision_interna.setCodigo_empresa(psicologia.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(psicologia.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Psicologia psicologia = (Psicologia) historia_clinica;

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });

			if (psicologia.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clínica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Mostrando Historia Clínica de Control/Evolucion");
			}
		} else {

			if (psicologia.getTipo_historia().equals(
					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clínica por Primera Vez");
			} else {
				toolbarbuttonTipo_historia
						.setLabel("Modificando Historia Clínica de Control/Evolucion");
			}
		}

		codigo_historia_anterior = psicologia.getCodigo_historia_anterior();
		tipo_historia = psicologia.getTipo_historia();

	}

	private void cargarImpresionDiagnostica(Psicologia psicologia)
			throws Exception {

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();

		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(psicologia
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);

		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);

	}

	private void cargarAnexo9_remision(Psicologia psicologia) {
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		anexo9_entidad.setCodigo_empresa(codigo_empresa);
		anexo9_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo9_entidad.setNro_historia(psicologia.getCodigo_historia());
		anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
		anexo9_entidad = getServiceLocator().getAnexo9EntidadService()
				.consultar(anexo9_entidad);
		//log.info("cargando anexo9_entidad === >" + anexo9_entidad);
		remisiones_externasAction.mostrarAnexo9(anexo9_entidad);
	}

	public void onMostrarModuloRemisiones() throws Exception {
		if (remisiones_externasAction != null)
			remisiones_externasAction.detach();
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("nro_ingreso", admision.getNro_ingreso());
		parametros.put("estado", admision.getEstado());
		parametros.put("codigo_administradora",	admision.getCodigo_administradora());
		parametros.put("id_plan", admision.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);
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

	
	@Override
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("SALUD MENTAL - psicología");

		return serivicio1.toString();
	}
	
	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t").append(tbxMotivo.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		if (tbxEnfermedad.getValue() != null
				&& !tbxEnfermedad.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(tbxEnfermedad.getValue()).append("\n");
		}

		// faltan los signos vitales para hacer el resumen

		informacion_clinica.append("\n");

		informacion_clinica.append("- DIAGNOSTICOS").append("\n");
		Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
				.obtenerImpresionDiagnostica();
		Cie cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_principal());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		informacion_clinica
				.append("\tDiagnostico principal: ")
				.append(impresion_diagnostica.getCie_principal())
				.append(" ")
				.append(cie != null ? cie.getNombre() : "")
				.append("\t")
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_principal(),
						"tipo_diagnostico", this)).append("\n");

		/* relacionado 1 */
		if (!impresion_diagnostica.getCie_relacionado1().isEmpty()) {
			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado1());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			informacion_clinica.append("\n");

			informacion_clinica
					.append("\tRelacionado 1: ")
					.append(impresion_diagnostica.getCie_relacionado1())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado1(),
							"tipo_diagnostico", this)).append("\n");
		}
		if (!impresion_diagnostica.getCie_relacionado2().isEmpty()) {

			/* relacionado 2 */
			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado2());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			informacion_clinica.append("\n");

			informacion_clinica
					.append("\tRelacionado 2: ")
					.append(impresion_diagnostica.getCie_relacionado2())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado2(),
							"tipo_diagnostico", this)).append("\n");

		}
		if (!impresion_diagnostica.getCie_relacionado3().isEmpty()) {

			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado3());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			informacion_clinica.append("\n");

			informacion_clinica
					.append("\tRelacionado 3: ")
					.append(impresion_diagnostica.getCie_relacionado3())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado3(),
							"tipo_diagnostico", this)).append("\n");
		}

		return informacion_clinica.toString();
	}
	
	public void imprimir() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		Long nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia != null) {
			String parametros_pagina="?codigo_empresa="+codigo_empresa;
			   parametros_pagina+="&codigo_sucursal="+codigo_sucursal;
			   parametros_pagina+="&codigo_historia="+nro_historia;
			   parametros_pagina+="&nro_ingreso="+admision.getNro_ingreso();
			   parametros_pagina+="&nro_identificacion="+admision.getNro_identificacion();
			Clients.evalJavaScript("window.open(\""+request.getContextPath()+"/pages/reports/psicologia_reporte.zul"+parametros_pagina+"\", '_blank');");
		}
	}
}
