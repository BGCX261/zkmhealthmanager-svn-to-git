/*
 * hisc_urgencia_odontologicoAction.java
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
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Hisc_urgencia_odontologico;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Hisc_urgencia_odontologicoService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.ProcedimientosService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.ManejadorPoblacion;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Hisc_urgencia_odontologicoAction extends HistoriaClinicaModel
		implements IHistoria_generica, ISeleccionarComponente {

	private static Logger log = Logger
			.getLogger(Hisc_urgencia_odontologicoAction.class);

	// Componentes //
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
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

	// @View private Textbox tbxCodigo_historia;
	// @View private Textbox tbxIdentificacion;
	// @View private Datebox dtbxFecha_inicial;
	// @View private Textbox tbxNro_ingreso;
	// @View
	// private Textbox tbxCodigo_prestador;
	// @View private Datebox dtbxFecha_ingreso;
	@View
	private Textbox tbxAcompaniante;
	@View
	private Listbox lbxRelacion;
	@View
	private Textbox tbxTel_acompaniante;
	@View
	private Textbox tbxMotivo_consulta;
	@View
	private Textbox tbxEnfermedad_actual;
	@View
	private Radiogroup rdbAnam_tratamiento;
	@View
	private Textbox tbxAnam_cual_tratamiento;
	@View
	private Radiogroup rdbAnam_toma_medicamentos;
	@View
	private Textbox tbxAnam_cual_toma_medicamentos;
	@View
	private Radiogroup rdbAnam_alergias;
	@View
	private Textbox tbxAnam_cual_alergias;
	@View
	private Radiogroup rdbAnam_cardiopatias;
	@View
	private Textbox tbxAnam_cual_cardiopatias;
	@View
	private Radiogroup rdbAnam_alteracion_presion;
	@View
	private Textbox tbxAnam_cual_alteracion_presion;
	@View
	private Radiogroup rdbAnam_embarazo;
	@View
	private Textbox tbxAnam_cual_embarazo;
	@View
	private Radiogroup rdbAnam_diabetes;
	@View
	private Listbox lbxAnam_cual_diabetes;
	@View
	private Radiogroup rdbAnam_hepatitis;
	@View
	private Listbox lbxAnam_cual_hepatitis;
	@View
	private Radiogroup rdbAnam_irradiaciones;
	@View
	private Textbox tbxAnam_cual_irradiaciones;
	@View
	private Radiogroup rdbAnam_discracias;
	@View
	private Textbox tbxAnam_cual_discracias;
	@View
	private Radiogroup rdbAnam_fiebre_reumatica;
	@View
	private Textbox tbxAnam_cual_fiebre_reumatica;
	@View
	private Radiogroup rdbAnam_enfermedad_renal;
	@View
	private Textbox tbxAnam_cual_enfermedad_renal;
	@View
	private Radiogroup rdbAnam_inmunosupresion;
	@View
	private Textbox tbxAnam_cual_inmunosupresion;
	@View
	private Radiogroup rdbAnam_trastornos;
	@View
	private Textbox tbxAnam_cual_trastornos;
	@View
	private Radiogroup rdbAnam_patologia;
	@View
	private Textbox tbxAnam_cual_patologia;
	@View
	private Radiogroup rdbAnam_trastornos_gastricos;
	@View
	private Textbox tbxAnam_cual_trastornos_gastricos;
	@View
	private Radiogroup rdbAnam_epilepsia;
	@View
	private Textbox tbxAnam_cual_epilepsia;
	@View
	private Radiogroup rdbAnam_cirugias;
	@View
	private Textbox tbxAnam_cual_cirugias;
	@View
	private Radiogroup rdbAnam_protasis;
	@View
	private Listbox lbxAnam_cual_protasis;
	@View
	private Radiogroup rdbAnam_otro;
	@View
	private Textbox tbxAnam_cual_otros;
	@View
	private Radiogroup rdbSintoma_respiratorio;
	@View
	private Radiogroup rdbSintoma_piel;
	// @View
	// private Doublebox dbxPulso;
	// @View
	// private Doublebox dbxTemperatura;
	// @View
	// private Doublebox dbxTension_arterial;
	// @View
	// private Doublebox dbxPeso;
	// @View
	// private Doublebox dbxTalla;
	// @View
	// private Doublebox dbxRespiracion;
	// @View
	// private Listbox lbxGrupo_s;
	// @View
	// private Listbox lbxRh;
	// @View
	// private Doublebox dbxDiastolica;
	// @View
	// private Textbox tbxObservaciones_temp;
	@View
	private Listbox lbxCausas_externas;
	@View
	private Textbox tbxOtro_causas_externas;
	@View
	private Listbox lbxTipo_disnostico;
	@View
	private Textbox tbxTratamiento;
	@View
	private Radiogroup rdbHa_sufrido_violencia;
	@View
	private Radiogroup rdbFisico;
	@View
	private Radiogroup rdbSexual;
	@View
	private Radiogroup rdbEmocional;
	@View
	private Radiogroup rdbSiente_riesgo;
	@View
	private Radiogroup rdbQuiere_hablar_del_tema;

	@View
	private Toolbarbutton btnCancelar;

	// Componentes //
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View
	private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	@View
	private Toolbarbutton toolbarbuttonTipo_historia;

	private Opciones_via_ingreso opciones_via_ingreso;
	private Admision admision;
	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;
	// @View(isMacro = true)
	// private SignosVitalesMacro mcSignosVitales;

	@View
	private Textbox tbxNomPrestador;
	@View
	private Listbox lbxProcedimientoPorRealizar;

	private final String[] idsExcluyentes = { "tbxAccion" };
	private List<String> seleccionados = new ArrayList<String>();

	@Override
	public void init() {
		try {
			listarCombos();
			macroImpresion_diagnostica.inicializarMacro(this, admision,
					IVias_ingreso.URGENCIA_ODONTOLOGICO);
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			// mcSignosVitales.setZkWindow(this);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
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
		Utilidades.listarElementoListbox(lbxAnam_cual_diabetes, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxAnam_cual_hepatitis, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxAnam_cual_protasis, true,
				getServiceLocator());
		// Utilidades.listarElementoListbox(lbxGrupo_s, true,
		// getServiceLocator());
		// Utilidades.listarElementoListbox(lbxRh, true, getServiceLocator());
		// Utilidades.listarElementoListbox(lbxCodigo_consulta,true,getServiceLocator());
		// Utilidades.listarElementoListbox(lbxFinalidad_cons,true,getServiceLocator());
		// Utilidades.listarElementoListbox(lbxCausas_externas,true,getServiceLocator());
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar("tipo_diagnostico");

		/* tipos de diasnosticos */
		elementos = this.getServiceLocator().getElementoService()
				.listar("tipo_diagnostico");
		listarElementoListboxFromType(lbxTipo_disnostico, false, elementos,
				false);

		/* causas externas */
		elementos = this.getServiceLocator().getElementoService()
				.listar("causa_externa");
		listarElementoListboxFromType(lbxCausas_externas, false, elementos,
				false);
	}

	public void listarElementoListboxFromType(Listbox listbox, boolean select,
			List<Elemento> elementos, boolean selectEnd) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			if (!selectEnd) {
				listbox.setSelectedIndex(0);
			} else {
				listbox.setSelectedIndex(listbox.getChildren().size() - 1);
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
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
			if (admision != null) {
				cargarInformacion_paciente();
			}
		}

	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		String mensaje = "Los campos marcados con (*) son obligatorios";
		boolean valida = true;

		try {
			FormularioUtil.validarCamposObligatorios(tbxAcompaniante,
					lbxRelacion, tbxTel_acompaniante, tbxMotivo_consulta,
					tbxEnfermedad_actual);
		} catch (Exception e) {
			return false;
		}

		if (!tbxTel_acompaniante.getValue().trim().isEmpty()
				&& !tbxTel_acompaniante.getValue().matches("[0-9]*$")) {
			valida = false;
			mensaje = "El valor del número de telefono no es valido";
			Clients.scrollIntoView(tbxTel_acompaniante);
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

			getServiceLocator().getHisc_urgencia_odontologicoService()
					.setLimit("limit 25 offset 0");

			List<Hisc_urgencia_odontologico> lista_datos = getServiceLocator()
					.getHisc_urgencia_odontologicoService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Hisc_urgencia_odontologico hisc_urgencia_odontologico : lista_datos) {
				rowsResultado.appendChild(crearFilas(
						hisc_urgencia_odontologico, this));
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

		final Hisc_urgencia_odontologico hisc_urgencia_odontologico = (Hisc_urgencia_odontologico) objeto;

		Paciente registro = new Paciente();
		registro.setCodigo_empresa(empresa.getCodigo_empresa());
		registro.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		registro.setNro_identificacion(hisc_urgencia_odontologico
				.getIdentificacion());
		registro = getServiceLocator().getPacienteService().consultar(registro);

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_urgencia_odontologico
				.getCodigo_historia() + ""));
		fila.appendChild(new Label(hisc_urgencia_odontologico
				.getIdentificacion() + ""));

		fila.appendChild(new Label(registro != null ? registro
				.getNombreCompleto() : ""));

		Datebox datebox = new Datebox(
				hisc_urgencia_odontologico.getFecha_inicial());
		datebox.setButtonVisible(false);
		datebox.setFormat("yyyy-MM-dd");
		datebox.setWidth("98%");
		datebox.setReadonly(true);
		fila.appendChild(datebox);

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(hisc_urgencia_odontologico);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			img.setVisible(false);
		}
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
									eliminarDatos(hisc_urgencia_odontologico);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	private Hisc_urgencia_odontologico getBean() {
		Hisc_urgencia_odontologico hisc_urgencia_odontologico = new Hisc_urgencia_odontologico();

		hisc_urgencia_odontologico.setFecha_inicial((new Timestamp(new Date()
				.getTime())));

		hisc_urgencia_odontologico.setCodigo_empresa(empresa
				.getCodigo_empresa());
		hisc_urgencia_odontologico.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		hisc_urgencia_odontologico.setCodigo_historia(infoPacientes
				.getCodigo_historia());
		hisc_urgencia_odontologico
				.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
		// hisc_urgencia_odontologico.setCodigo_historia(tbxCodigo_historia.getValue());
		// hisc_urgencia_odontologico.setIdentificacion(tbxIdentificacion.getValue());
		// hisc_urgencia_odontologico.setFecha_inicial(new
		// Timestamp(dtbxFecha_inicial.getValue().getTime()));
		// hisc_urgencia_odontologico.setNro_ingreso(tbxNro_ingreso.getValue());
		hisc_urgencia_odontologico.setCodigo_prestador(admision
				.getCodigo_medico());
		// hisc_urgencia_odontologico.setFecha_ingreso(new
		// Timestamp(dtbxFecha_ingreso.getValue().getTime()));
		hisc_urgencia_odontologico.setAcompaniante(tbxAcompaniante.getValue());
		hisc_urgencia_odontologico.setRelacion(lbxRelacion.getSelectedItem()
				.getValue().toString());
		hisc_urgencia_odontologico.setTel_acompaniante(tbxTel_acompaniante
				.getValue() + "");
		hisc_urgencia_odontologico.setMotivo_consulta(tbxMotivo_consulta
				.getValue());
		hisc_urgencia_odontologico.setEnfermedad_actual(tbxEnfermedad_actual
				.getValue());
		hisc_urgencia_odontologico.setAnam_tratamiento(rdbAnam_tratamiento
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_tratamiento(tbxAnam_cual_tratamiento.getValue());
		hisc_urgencia_odontologico
				.setAnam_toma_medicamentos(rdbAnam_toma_medicamentos
						.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_toma_medicamentos(tbxAnam_cual_toma_medicamentos
						.getValue());
		hisc_urgencia_odontologico.setAnam_alergias(rdbAnam_alergias
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setAnam_cual_alergias(tbxAnam_cual_alergias
				.getValue());
		hisc_urgencia_odontologico.setAnam_cardiopatias(rdbAnam_cardiopatias
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_cardiopatias(tbxAnam_cual_cardiopatias.getValue());
		hisc_urgencia_odontologico
				.setAnam_alteracion_presion(rdbAnam_alteracion_presion
						.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_alteracion_presion(tbxAnam_cual_alteracion_presion
						.getValue());
		hisc_urgencia_odontologico.setAnam_embarazo(rdbAnam_embarazo
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setAnam_cual_embarazo(tbxAnam_cual_embarazo
				.getValue());
		hisc_urgencia_odontologico.setAnam_diabetes(rdbAnam_diabetes
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setAnam_cual_diabetes(lbxAnam_cual_diabetes
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setAnam_hepatitis(rdbAnam_hepatitis
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_hepatitis(lbxAnam_cual_hepatitis
						.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setAnam_irradiaciones(rdbAnam_irradiaciones
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_irradiaciones(tbxAnam_cual_irradiaciones
						.getValue());
		hisc_urgencia_odontologico.setAnam_discracias(rdbAnam_discracias
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_discracias(tbxAnam_cual_discracias.getValue());
		hisc_urgencia_odontologico
				.setAnam_fiebre_reumatica(rdbAnam_fiebre_reumatica
						.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_fiebre_reumatica(tbxAnam_cual_fiebre_reumatica
						.getValue());
		hisc_urgencia_odontologico
				.setAnam_enfermedad_renal(rdbAnam_enfermedad_renal
						.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_enfermedad_renal(tbxAnam_cual_enfermedad_renal
						.getValue());
		hisc_urgencia_odontologico
				.setAnam_inmunosupresion(rdbAnam_inmunosupresion
						.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_inmunosupresion(tbxAnam_cual_inmunosupresion
						.getValue());
		hisc_urgencia_odontologico.setAnam_trastornos(rdbAnam_trastornos
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_trastornos(tbxAnam_cual_trastornos.getValue());
		hisc_urgencia_odontologico.setAnam_patologia(rdbAnam_patologia
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_patologia(tbxAnam_cual_patologia.getValue());
		hisc_urgencia_odontologico
				.setAnam_trastornos_gastricos(rdbAnam_trastornos_gastricos
						.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_trastornos_gastricos(tbxAnam_cual_trastornos_gastricos
						.getValue());
		hisc_urgencia_odontologico.setAnam_epilepsia(rdbAnam_epilepsia
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setAnam_cual_epilepsia(tbxAnam_cual_epilepsia.getValue());
		hisc_urgencia_odontologico.setAnam_cirugias(rdbAnam_cirugias
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setAnam_cual_cirugias(tbxAnam_cual_cirugias
				.getValue());
		hisc_urgencia_odontologico.setAnam_protasis(rdbAnam_protasis
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setAnam_cual_protasis(lbxAnam_cual_protasis
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setAnam_otro(rdbAnam_otro.getSelectedItem()
				.getValue().toString());
		hisc_urgencia_odontologico.setAnam_cual_otros(tbxAnam_cual_otros
				.getValue());
		hisc_urgencia_odontologico
				.setSintoma_respiratorio(rdbSintoma_respiratorio
						.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setSintoma_piel(rdbSintoma_piel
				.getSelectedItem().getValue().toString());

		// hisc_urgencia_odontologico
		// .setObservaciones_temp(tbxObservaciones_temp.getValue());
		hisc_urgencia_odontologico.setCausas_externas(lbxCausas_externas
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setOtro_causas_externas(tbxOtro_causas_externas.getValue());
		hisc_urgencia_odontologico.setTipo_disnostico(lbxTipo_disnostico
				.getSelectedItem().getValue().toString());

		/* Nuevos campos agregados */
		hisc_urgencia_odontologico.setTratamiento(tbxTratamiento.getValue());
		hisc_urgencia_odontologico
				.setHa_sufrido_violencia(rdbHa_sufrido_violencia
						.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico.setFisico(rdbFisico.getSelectedItem()
				.getValue().toString());
		hisc_urgencia_odontologico.setSexual(rdbSexual.getSelectedItem()
				.getValue().toString());
		hisc_urgencia_odontologico.setEmocional(rdbEmocional.getSelectedItem()
				.getValue().toString());
		hisc_urgencia_odontologico.setSiente_riesgo(rdbSiente_riesgo
				.getSelectedItem().getValue().toString());
		hisc_urgencia_odontologico
				.setQuiere_hablar_del_tema(rdbQuiere_hablar_del_tema
						.getSelectedItem().getValue().toString());

		hisc_urgencia_odontologico.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_urgencia_odontologico.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_urgencia_odontologico.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_urgencia_odontologico.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		hisc_urgencia_odontologico.setCreacion_user(usuarios.getCodigo()
				.toString());
		hisc_urgencia_odontologico.setUltimo_user(usuarios.getCodigo()
				.toString());
		return hisc_urgencia_odontologico;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //

				Hisc_urgencia_odontologico hisc_urgencia_odontologico = getBean();

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("hisc_urgencia_odontologico",
						hisc_urgencia_odontologico);
				// datos.put("sigvitales", mcSignosVitales.obtenerSigvitales());
				datos.put("accion", tbxAccion.getValue());
				datos.put("admision", admision);
				datos.put("datos_procedimiento", getProcedimientos());

				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();
				datos.put("impresion_diagnostica", impresion_diagnostica);

				getServiceLocator().getHisc_urgencia_odontologicoService()
						.guardarDatos(datos);

				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(hisc_urgencia_odontologico
						.getCodigo_historia());

				actualizarRemision(admision, getInformacionClinica(), this);
				cargarServicios();

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

	private void cargarServicios() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", admision.getCodigo_empresa());
		map.put("codigo_sucursal", admision.getCodigo_sucursal());
		map.put("nro_identificacion", admision.getNro_identificacion());
		map.put("nro_ingreso", admision.getNro_ingreso());
		List<Datos_procedimiento> listado_pcd = getServiceLocator()
				.getServicio(Datos_procedimientoService.class).listarTabla(map);
		lbxProcedimientoPorRealizar.getItems().clear();
		ProcedimientosService procedimientosService = getServiceLocator()
				.getServicio(ProcedimientosService.class);
		for (Datos_procedimiento datos_procedimiento : listado_pcd) {
			Procedimientos procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(datos_procedimiento
					.getCodigo_procedimiento()));
			procedimientos = procedimientosService.consultar(procedimientos);
			lbxProcedimientoPorRealizar
					.appendChild(crearListItemProcedimientoPorRealizar(
							datos_procedimiento,
							procedimientos.getDescripcion(), true,
							datos_procedimiento.getUnidades(),
							procedimientos.getCantidad_maxima()));
		}
	}

	private List<Datos_procedimiento> getProcedimientos() {
		List<Datos_procedimiento> listado_datos_procedimientos = new ArrayList<Datos_procedimiento>();
		for (Listitem listitem : lbxProcedimientoPorRealizar.getItems()) {
			listado_datos_procedimientos.add((Datos_procedimiento) listitem
					.getValue());
		}
		return listado_datos_procedimientos;
	}

	public void openPcd() throws Exception {
		Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
				.getManuales_tarifarios(admision);

		String anio = (manuales_tarifarios != null ? (manuales_tarifarios
				.getAnio() != null ? manuales_tarifarios.getAnio() : "") : "");

		String pages = "/pages/openProcedimientos.zul";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				admision.getCodigo_administradora());
		parametros.put("id_plan", admision.getId_plan());
		parametros.put("restringido", "");
		parametros.put("nro_ingreso", admision.getNro_ingreso());
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		// parametros.put("estrato", "");
		parametros.put("admision", admision);
		// parametros.put("quirurgico", "");
		parametros.put("anio", anio);
		parametros.put("seleccionados", seleccionados);
		parametros.put("via_ingreso", admision.getVia_ingreso());
		parametros.put("manual_tarifario", manuales_tarifarios);

		// log.info("Paginas: " + pages);
		Component componente = Executions.createComponents(pages, null,
				parametros);
		final Window ventana = (Window) componente;
		if (ventana instanceof OpenProcedimientosAction) {
			((OpenProcedimientosAction) ventana)
					.setSeleccionar_componente(this);
		}
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
		ventana.setMode("modal");
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_urgencia_odontologico hisc_urgencia_odontologico = (Hisc_urgencia_odontologico) obj;
		try {
			cargarInformacion_paciente();
			infoPacientes.setCodigo_historia(hisc_urgencia_odontologico
					.getCodigo_historia());

			initMostrar_datos(hisc_urgencia_odontologico);

			// tbxCodigo_historia.setValue(hisc_urgencia_odontologico.getCodigo_historia());
			// tbxIdentificacion.setValue(hisc_urgencia_odontologico.getIdentificacion());
			// dtbxFecha_inicial.setValue(hisc_urgencia_odontologico.getFecha_inicial());
			// tbxNro_ingreso.setValue(hisc_urgencia_odontologico.getNro_ingreso());
			// tbxCodigo_prestador.setValue(hisc_urgencia_odontologico
			// .getCodigo_prestador());
			// dtbxFecha_ingreso.setValue(hisc_urgencia_odontologico.getFecha_ingreso());
			tbxAcompaniante.setValue(hisc_urgencia_odontologico
					.getAcompaniante());
			for (int i = 0; i < lbxRelacion.getItemCount(); i++) {
				Listitem listitem = lbxRelacion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(hisc_urgencia_odontologico.getRelacion())) {
					listitem.setSelected(true);
					i = lbxRelacion.getItemCount();
				}
			}
			tbxTel_acompaniante.setValue(hisc_urgencia_odontologico
					.getTel_acompaniante());
			tbxMotivo_consulta.setValue(hisc_urgencia_odontologico
					.getMotivo_consulta());
			tbxEnfermedad_actual.setValue(hisc_urgencia_odontologico
					.getEnfermedad_actual());
			Utilidades.seleccionarRadio(rdbAnam_tratamiento,
					hisc_urgencia_odontologico.getAnam_tratamiento());
			tbxAnam_cual_tratamiento.setValue(hisc_urgencia_odontologico
					.getAnam_cual_tratamiento());
			Utilidades.seleccionarRadio(rdbAnam_toma_medicamentos,
					hisc_urgencia_odontologico.getAnam_toma_medicamentos());
			tbxAnam_cual_toma_medicamentos.setValue(hisc_urgencia_odontologico
					.getAnam_cual_toma_medicamentos());
			Utilidades.seleccionarRadio(rdbAnam_alergias,
					hisc_urgencia_odontologico.getAnam_alergias());
			tbxAnam_cual_alergias.setValue(hisc_urgencia_odontologico
					.getAnam_cual_alergias());
			Utilidades.seleccionarRadio(rdbAnam_cardiopatias,
					hisc_urgencia_odontologico.getAnam_cardiopatias());
			tbxAnam_cual_cardiopatias.setValue(hisc_urgencia_odontologico
					.getAnam_cual_cardiopatias());
			Utilidades.seleccionarRadio(rdbAnam_alteracion_presion,
					hisc_urgencia_odontologico.getAnam_alteracion_presion());
			tbxAnam_cual_alteracion_presion.setValue(hisc_urgencia_odontologico
					.getAnam_cual_alteracion_presion());
			Utilidades.seleccionarRadio(rdbAnam_embarazo,
					hisc_urgencia_odontologico.getAnam_embarazo());
			tbxAnam_cual_embarazo.setValue(hisc_urgencia_odontologico
					.getAnam_cual_embarazo());
			Utilidades.seleccionarRadio(rdbAnam_diabetes,
					hisc_urgencia_odontologico.getAnam_diabetes());
			for (int i = 0; i < lbxAnam_cual_diabetes.getItemCount(); i++) {
				Listitem listitem = lbxAnam_cual_diabetes.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(hisc_urgencia_odontologico
								.getAnam_cual_diabetes())) {
					listitem.setSelected(true);
					i = lbxAnam_cual_diabetes.getItemCount();
				}
			}
			Utilidades.seleccionarRadio(rdbAnam_hepatitis,
					hisc_urgencia_odontologico.getAnam_hepatitis());
			for (int i = 0; i < lbxAnam_cual_hepatitis.getItemCount(); i++) {
				Listitem listitem = lbxAnam_cual_hepatitis.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(hisc_urgencia_odontologico
								.getAnam_cual_hepatitis())) {
					listitem.setSelected(true);
					i = lbxAnam_cual_hepatitis.getItemCount();
				}
			}

			Utilidades.seleccionarRadio(rdbAnam_irradiaciones,
					hisc_urgencia_odontologico.getAnam_irradiaciones());
			tbxAnam_cual_irradiaciones.setValue(hisc_urgencia_odontologico
					.getAnam_cual_irradiaciones());
			Utilidades.seleccionarRadio(rdbAnam_discracias,
					hisc_urgencia_odontologico.getAnam_discracias());
			tbxAnam_cual_discracias.setValue(hisc_urgencia_odontologico
					.getAnam_cual_discracias());
			Utilidades.seleccionarRadio(rdbAnam_fiebre_reumatica,
					hisc_urgencia_odontologico.getAnam_fiebre_reumatica());
			tbxAnam_cual_fiebre_reumatica.setValue(hisc_urgencia_odontologico
					.getAnam_cual_fiebre_reumatica());
			Utilidades.seleccionarRadio(rdbAnam_enfermedad_renal,
					hisc_urgencia_odontologico.getAnam_enfermedad_renal());
			tbxAnam_cual_enfermedad_renal.setValue(hisc_urgencia_odontologico
					.getAnam_cual_enfermedad_renal());
			Utilidades.seleccionarRadio(rdbAnam_inmunosupresion,
					hisc_urgencia_odontologico.getAnam_inmunosupresion());
			tbxAnam_cual_inmunosupresion.setValue(hisc_urgencia_odontologico
					.getAnam_cual_inmunosupresion());
			Utilidades.seleccionarRadio(rdbAnam_trastornos,
					hisc_urgencia_odontologico.getAnam_trastornos());
			tbxAnam_cual_trastornos.setValue(hisc_urgencia_odontologico
					.getAnam_cual_trastornos());
			Utilidades.seleccionarRadio(rdbAnam_patologia,
					hisc_urgencia_odontologico.getAnam_patologia());
			tbxAnam_cual_patologia.setValue(hisc_urgencia_odontologico
					.getAnam_cual_patologia());
			Utilidades.seleccionarRadio(rdbAnam_trastornos_gastricos,
					hisc_urgencia_odontologico.getAnam_trastornos_gastricos());
			tbxAnam_cual_trastornos_gastricos
					.setValue(hisc_urgencia_odontologico
							.getAnam_cual_trastornos_gastricos());
			Utilidades.seleccionarRadio(rdbAnam_epilepsia,
					hisc_urgencia_odontologico.getAnam_epilepsia());
			tbxAnam_cual_epilepsia.setValue(hisc_urgencia_odontologico
					.getAnam_cual_epilepsia());
			Utilidades.seleccionarRadio(rdbAnam_cirugias,
					hisc_urgencia_odontologico.getAnam_cirugias());
			tbxAnam_cual_cirugias.setValue(hisc_urgencia_odontologico
					.getAnam_cual_cirugias());
			Utilidades.seleccionarRadio(rdbAnam_protasis,
					hisc_urgencia_odontologico.getAnam_protasis());
			for (int i = 0; i < lbxAnam_cual_protasis.getItemCount(); i++) {
				Listitem listitem = lbxAnam_cual_protasis.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(hisc_urgencia_odontologico
								.getAnam_cual_protasis())) {
					listitem.setSelected(true);
					i = lbxAnam_cual_protasis.getItemCount();
				}
			}
			Utilidades.seleccionarRadio(rdbAnam_otro,
					hisc_urgencia_odontologico.getAnam_otro());
			tbxAnam_cual_otros.setValue(hisc_urgencia_odontologico
					.getAnam_cual_otros());
			Utilidades.seleccionarRadio(rdbSintoma_respiratorio,
					hisc_urgencia_odontologico.getSintoma_respiratorio());
			Utilidades.seleccionarRadio(rdbSintoma_piel,
					hisc_urgencia_odontologico.getSintoma_piel());

			cargarImpresionDiagnostica(hisc_urgencia_odontologico);

			// dbxPulso.setValue(hisc_urgencia_odontologico.getPulso());
			// dbxTemperatura
			// .setValue(hisc_urgencia_odontologico.getTemperatura());
			// dbxTension_arterial.setValue(hisc_urgencia_odontologico
			// .getTension_arterial());
			// dbxPeso.setValue(hisc_urgencia_odontologico.getPeso());
			// dbxTalla.setValue(hisc_urgencia_odontologico.getTalla());
			// dbxRespiracion
			// .setValue(hisc_urgencia_odontologico.getRespiracion());
			// for (int i = 0; i < lbxGrupo_s.getItemCount(); i++) {
			// Listitem listitem = lbxGrupo_s.getItemAtIndex(i);
			// if (listitem.getValue().toString()
			// .equals(hisc_urgencia_odontologico.getGrupo_s())) {
			// listitem.setSelected(true);
			// i = lbxGrupo_s.getItemCount();
			// }
			// }
			// for (int i = 0; i < lbxRh.getItemCount(); i++) {
			// Listitem listitem = lbxRh.getItemAtIndex(i);
			// if (listitem.getValue().toString()
			// .equals(hisc_urgencia_odontologico.getRh())) {
			// listitem.setSelected(true);
			// i = lbxRh.getItemCount();
			// }
			// }
			// dbxDiastolica.setValue(hisc_urgencia_odontologico.getDiastolica());
			// tbxObservaciones_temp.setValue(hisc_urgencia_odontologico
			// .getObservaciones_temp());
			for (int i = 0; i < lbxCausas_externas.getItemCount(); i++) {
				Listitem listitem = lbxCausas_externas.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(hisc_urgencia_odontologico.getCausas_externas())) {
					listitem.setSelected(true);
					i = lbxCausas_externas.getItemCount();
				}
				// Muestra el texbox de otos cuando seleccionan la opcion en
				// causas externas
				if (lbxCausas_externas.getSelectedItem().getValue().toString()
						.equals("15")) {
					tbxOtro_causas_externas.setVisible(true);
				}
			}

			tbxOtro_causas_externas.setValue(hisc_urgencia_odontologico
					.getOtro_causas_externas());
			for (int i = 0; i < lbxTipo_disnostico.getItemCount(); i++) {
				Listitem listitem = lbxTipo_disnostico.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(hisc_urgencia_odontologico.getTipo_disnostico())) {
					listitem.setSelected(true);
					i = lbxTipo_disnostico.getItemCount();
				}
			}

			tbxTratamiento
					.setValue(hisc_urgencia_odontologico.getTratamiento());
			Utilidades.seleccionarRadio(rdbHa_sufrido_violencia,
					hisc_urgencia_odontologico.getHa_sufrido_violencia());
			Utilidades.seleccionarRadio(rdbFisico,
					hisc_urgencia_odontologico.getFisico());
			Utilidades.seleccionarRadio(rdbSexual,
					hisc_urgencia_odontologico.getSexual());
			Utilidades.seleccionarRadio(rdbEmocional,
					hisc_urgencia_odontologico.getEmocional());
			Utilidades.seleccionarRadio(rdbSiente_riesgo,
					hisc_urgencia_odontologico.getSiente_riesgo());
			Utilidades.seleccionarRadio(rdbQuiere_hablar_del_tema,
					hisc_urgencia_odontologico.getQuiere_hablar_del_tema());

			// cargarSignosVitales(hisc_urgencia_odontologico);
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hisc_urgencia_odontologico hisc_urgencia_odontologico = (Hisc_urgencia_odontologico) obj;
		try {
			int result = getServiceLocator()
					.getHisc_urgencia_odontologicoService().eliminar(
							hisc_urgencia_odontologico);
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
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

			e.printStackTrace();
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	// public void selectedPrestador(Listitem listitem) {
	// if (listitem.getValue() == null) {
	// tbxCodigo_prestador.setValue("");
	// tbxNomPrestador.setValue("");
	// } else {
	// Prestadores dato = (Prestadores) listitem.getValue();
	// tbxCodigo_prestador.setValue(dato.getNro_identificacion());
	// tbxNomPrestador.setValue(dato.getNombres() + " "
	// + dato.getApellidos());
	// }
	// ((Bandbox) tbxCodigo_prestador).close();
	// }
	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			parameters.put("clasificacion", "01");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Cie> list = getServiceLocator().getServicio(
					GeneralExtraService.class).listar(Cie.class, parameters);

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

	public void seleccion(Listbox listbox, int entero,
			AbstractComponent[] abstractComponents) {
		try {
			// //System.Out.Println("" + listbox.getSelectedItem().getValue());

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
			// block
			// //System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_radio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			// //System.Out.Println("" +
			// radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {
					abstractComponent.setVisible(true);
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());

					}
					if (abstractComponent instanceof Listbox) {
						if (((Listbox) abstractComponent).getItemCount() > 0) {
							((Listbox) abstractComponent).setSelectedIndex(0);
						}
						listarElementoListbox(((Listbox) abstractComponent),
								true);

					}

					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
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

					if (abstractComponent instanceof Listbox) {
						((Listbox) abstractComponent).getChildren().clear();
						for (int i = 0; i < ((Listbox) abstractComponent)
								.getItemCount(); i++) {
							Listitem listitem = ((Listbox) abstractComponent)
									.getItemAtIndex(i);
							if (listitem.getValue().toString().equals("")) {
								listitem.setSelected(true);
								break;
							}
						}

					}
					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
					}

					abstractComponent.setVisible(false);
				}
			}
		} catch (Exception e) {
			// block
			// //System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	@Override
	public void cargarInformacion_paciente() {

		// mcSignosVitales.setFecha_nacimiento(admision.getPaciente()
		// .getFecha_nacimiento());
		// mcSignosVitales.setGenero(admision.getPaciente().getSexo());
		// mcSignosVitales.inicializarParametrosAlertas();
		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {
					@Override
					public void ejecutarProceso() {
						if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {

							// Map<String, Object> parametros = new
							// HashMap<String, Object>();
							// parametros.put("codigo_empresa", codigo_empresa);
							// parametros.put("codigo_sucursal",
							// codigo_sucursal);
							// parametros.put("identificacion",
							// admision.getNro_identificacion());
							//
							toolbarbuttonTipo_historia
									.setLabel("Creando historia de Urgencia Odontologica");
							admision.setPrimera_vez("S");
						}
					}
				});
	}

	@Override
	public void initHistoria() throws Exception {
		macroImpresion_diagnostica.inicializarMacro(this, admision,
				admision.getVia_ingreso());
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			// via_ingreso = admision_seleccionada.getVia_ingreso();
			btnCancelar.setVisible(true);
		} else {
			if (admision != null) {
				toolbarbuttonPaciente_admisionado1.setLabel(admision
						.getPaciente().getNombreCompleto());
				toolbarbuttonPaciente_admisionado2.setLabel(admision
						.getPaciente().getNombreCompleto());

				if (admision.getAtendida()) {
					opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;
					Historia_clinica historia_clinica = new Historia_clinica();
					historia_clinica.setCodigo_empresa(admision
							.getCodigo_empresa());
					historia_clinica.setCodigo_sucursal(admision
							.getCodigo_sucursal());
					historia_clinica.setNro_ingreso(admision.getNro_ingreso());
					historia_clinica.setNro_identificacion(admision
							.getNro_identificacion());
					historia_clinica.setVia_ingreso(admision.getVia_ingreso());
					historia_clinica = getServiceLocator().getServicio(
							GeneralExtraService.class).consultar(
							historia_clinica);

					if (historia_clinica != null) {
						Hisc_urgencia_odontologico hisc_urgencia_odontologico = new Hisc_urgencia_odontologico();
						hisc_urgencia_odontologico
								.setCodigo_historia(historia_clinica
										.getCodigo_historia());
						hisc_urgencia_odontologico = getServiceLocator()
								.getServicio(
										Hisc_urgencia_odontologicoService.class)
								.consultar(hisc_urgencia_odontologico);
						mostrarDatos(hisc_urgencia_odontologico);
					} else {
						groupboxEditar.setVisible(false);
						throw new ValidacionRunTimeException(
								"NO hay una historia clinica relacionada a este paciente admitido");
					}

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
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		// Hisc_urgencia_odontologico hisc_urgencia_odontologico =
		// (Hisc_urgencia_odontologico) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] { "northEditar" });
			toolbarbuttonTipo_historia
					.setLabel("Mostrando Historia de Urgencia");
		} else {
			toolbarbuttonTipo_historia
					.setLabel("Modificando Historia de Urgencia");
		}
	}

	// private boolean vaidarIgualdad(String... in) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// for (String inO : in) {
	// if (!inO.trim().isEmpty()) {
	// if (map.containsKey(inO)) {
	// return true;
	// }
	// map.put(inO, inO);
	// }
	// }
	// return false;
	// }

	/*
	 * public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
	 * try { UtilidadSignosVitales.onMostrarAlertaTension(TA_sistolica,
	 * TA_diastolica); } catch (Exception e) { // block
	 * 
	 * MensajesUtil.mensajeError(e, "", this); e.printStackTrace();
	 * 
	 * } }
	 */
	private void cargarImpresionDiagnostica(
			Hisc_urgencia_odontologico hisc_urgencia_odontologico)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(hisc_urgencia_odontologico
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		adicionarPcd(pcd);
	}

	@Override
	public void adicionarPcd(Map<String, Object> pcd) throws Exception {
		Long id_procedimiento = (Long) pcd.get("id_procedimiento");
		String codigo_cups = (String) pcd.get("codigo_cups");

		Datos_procedimiento datos_procedimiento = getDatosPdc(pcd);
		String nombre_procedimiento = (String) pcd.get("nombre_procedimiento");
		int cantidad_maxima = (Integer) pcd.get("cantidad_maxima");

		Integer CANTIDAD_INICIALIZAR = (Integer) pcd
				.get("CANTIDAD_INICIALIZAR");

		lbxProcedimientoPorRealizar
				.appendChild(crearListItemProcedimientoPorRealizar(
						datos_procedimiento, nombre_procedimiento, true,
						CANTIDAD_INICIALIZAR, cantidad_maxima));
		seleccionados.add(id_procedimiento + "");

		Integer frecuencia = (Integer) pcd.get("frecuencia");

		String nombre_pcd = (String) pcd.get("nombre_procedimiento");

		ManejadorPoblacion.validarFrecuenciaOrdenamiento(codigo_cups,
				frecuencia != null ? frecuencia : 0, nombre_pcd, admision,
				getServiceLocator());
	}

	private Datos_procedimiento getDatosPdc(Map<String, Object> pcd) {
		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento.setCodigo_empresa(admision.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(admision.getCodigo_sucursal());
		datos_procedimiento.setNro_identificacion(admision
				.getNro_identificacion());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_procedimiento(pcd.get("id_procedimiento")
				+ "");
		datos_procedimiento.setCodigo_cups(pcd.get("codigo_cups") + "");
		datos_procedimiento.setValor_procedimiento((Double) pcd
				.get("valor_procedimiento"));
		// datos_procedimiento.setUnidades(unidades);
		datos_procedimiento.setDescuento((Double) pcd.get("descuento"));
		datos_procedimiento.setIncremento((Double) pcd.get("incremento"));
		datos_procedimiento.setValor_real((Double) pcd.get("valor_real"));

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(getUsuarios().getCreacion_user());
		datos_procedimiento.setUltimo_user(getUsuarios().getUltimo_user());

		datos_procedimiento
				.setAmbito_procedimiento(IDatosProcedimientos.AMBITO_REALIZACION);
		datos_procedimiento
				.setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
		datos_procedimiento
				.setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
		datos_procedimiento
				.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
		datos_procedimiento
				.setRealizado_en(IDatosProcedimientos.REALIZADO_EN_ODONOTOLOGIA);

		datos_procedimiento.setTipo_identificacion(admision.getPaciente()
				.getTipo_identificacion());
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion("");
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());

		return datos_procedimiento;
	}

	private Listitem crearListItemProcedimientoPorRealizar(
			final Datos_procedimiento datos_procedimiento,
			final String nombre_procedimiento, boolean habilitarEdicion,
			Integer cantidad_inicializar, final int cantidad_maxima) {

		final Listitem listitem = new Listitem();
		listitem.setSelected(false);
		listitem.setValue(datos_procedimiento);

		listitem.appendChild(new Listcell(datos_procedimiento.getCodigo_cups()));
		listitem.appendChild(new Listcell("" + nombre_procedimiento));

		Listcell listcell = new Listcell();
		Intbox intbox_realizadas = new Intbox();
		intbox_realizadas.setHflex("1");

		listcell.appendChild(intbox_realizadas);
		if (cantidad_inicializar != null) {
			intbox_realizadas.setValue(cantidad_inicializar);
			datos_procedimiento.setUnidades(cantidad_inicializar);
		} else {
			intbox_realizadas.setValue(1);
			datos_procedimiento.setUnidades(1);
		}

		intbox_realizadas.addEventListener(Events.ON_BLUR,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Intbox intbox_realizadas = (Intbox) arg0.getTarget();
						FormularioUtil.onAccionValidarCantidadMaxima(
								cantidad_maxima, intbox_realizadas,
								nombre_procedimiento, "PROCEDIMIENTO");
						Integer valor = intbox_realizadas.getValue();
						if (valor != null && valor.intValue() > 0) {
							datos_procedimiento.setUnidades(valor);
						} else {
							datos_procedimiento.setUnidades(1);
							intbox_realizadas.setValue(1);
						}
					}
				});
		listitem.appendChild(listcell);
		return listitem;
	}

	/*
	 * private void cargarSignosVitales( Hisc_urgencia_odontologico
	 * hisc_urgencia_odontologico) { Sigvitales sigvitales = new Sigvitales();
	 * sigvitales.setCodigo_empresa(codigo_empresa);
	 * sigvitales.setCodigo_sucursal(codigo_sucursal);
	 * sigvitales.setCodigo_historia(hisc_urgencia_odontologico
	 * .getCodigo_historia()); sigvitales =
	 * getServiceLocator().getSigvitalesService().consultar( sigvitales);
	 * mcSignosVitales.mostrarSigvitales(sigvitales);
	 * 
	 * }
	 */
}
