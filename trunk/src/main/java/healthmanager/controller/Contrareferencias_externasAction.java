package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo10_entidad;
import healthmanager.modelo.bean.Contrato_prestadores;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Anexo10_entidadService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Hisc_urgenciaService;

import java.sql.Timestamp;
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
import org.zkoss.zul.Bandbox;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.res.BandboxObject;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Contrareferencias_externasAction extends ZKWindow {

	private static Logger log = Logger
			.getLogger(Contrareferencias_externasAction.class);

	private Anexo10_entidadService anexo10_entidadService;

	private ZKWindow zkWindow;

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
	private Intbox tbxPrestador_remision;
	@View
	private Textbox tbxNombre_prestador;

	@View
	private Textbox tbxId;
	@View
	private Textbox tbxDirecion;
	@View
	private Listbox lbxDepartamento;
	@View
	private Listbox lbxMunicipio;
	@View
	private Textbox tbxIdentificacion_paciente;
	@View
	private Textbox tbxDatos_paciente;
	@View
	private Textbox tbxDireccion_paciente;
	@View
	private Textbox tbxTelefono_paciente;
	@View
	private Datebox tbxFecha_nac_paciente;

	@View
	private Doublebox dbxTelefono;
	@View
	private Doublebox dbxCel;

	@View
	private Textbox tbxInformacionClinica;

	@View
	private Textbox tbxNombre_responsable;
	@View
	private Textbox tbxApellido_responsable;
	@View
	private Textbox tbxNro_id_responsable;
	@View
	private Textbox tbxDir_responsable;
	@View
	private Intbox ibxTer_responsable;
	@View
	private Listbox lbxDep_responsable;
	@View
	private Listbox lbxMun_responsable;

	@View
	private Listbox lbxTipo_id_responsable;

	// private Textbox tbxCodigo_servicio_contraref;
	@View
	private Textbox tbxNombre_contrarefiere;

	private Hisc_urgencia historia_clinica;
	private Admision admision;

	private final String[] idsExcluyentes = { "lbxMunicipio", "lbxDepartamento" };
	@View
	private Textbox tbxNombre_solictante;
	@View
	private Textbox tbxApellido_solicitante;

	private Long codigo_anexo10;

	@Override
	public void init() throws Exception {
		listarCombos();
	}

	public void cargarInformacionPrestador(Admision admision) {
		tbxPrestador_remision.setValue(ConvertidorDatosUtil
				.convertirDatot((admision.getCodigo_medico().toString())));
		tbxPrestador_remision.setReadonly(true);
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);

			if (admision != null) {
				cargarInformacionPrestador(admision);
				cargarDatosIniciales();
				Utilidades.seleccionarListitem(lbxDep_responsable,
						empresa.getCodigo_dpto());
				listarMunicipios(lbxMun_responsable, lbxDep_responsable);
				Utilidades.seleccionarListitem(lbxMun_responsable,
						empresa.getCodigo_municipio());
				cargamosDatosDelPaciente();
				Historia_clinica hc = new Historia_clinica();
				hc.setCodigo_empresa(admision.getCodigo_empresa());
				hc.setCodigo_sucursal(admision.getCodigo_sucursal());
				hc.setNro_ingreso(admision.getNro_ingreso());
				hc.setNro_identificacion(admision.getNro_identificacion());
				hc.setVia_ingreso(admision.getVia_ingreso());
				hc = this.getServiceLocator()
						.getServicio(GeneralExtraService.class)
						.consultar(hc);

				if (hc != null) {
					Anexo10_entidad anexo10_entidad = new Anexo10_entidad();
					historia_clinica = new Hisc_urgencia();
					historia_clinica.setCodigo_empresa(hc.getCodigo_empresa());
					historia_clinica
							.setCodigo_sucursal(hc.getCodigo_sucursal());
					historia_clinica
							.setCodigo_historia(hc.getCodigo_historia());

					historia_clinica = this.getServiceLocator()
							.getServicio(Hisc_urgenciaService.class)
							.consultar(historia_clinica);
					if (historia_clinica != null) {
						try {
							anexo10_entidad.setCodigo(historia_clinica
									.getCodigo_historia());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		LISTITEM.SETVALUE("CODIGO_HISTORIA");
//		LISTITEM.SETLABEL("CODIGO_HISTORIA");
//		LBXPARAMETER.APPENDCHILD(LISTITEM);
//
//		LISTITEM = NEW LISTITEM();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
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
			}
		} else if (opciones_formulario.equals(OpcionesFormulario.CONSULTAR)) {
			onOpcionFormularioConsultar();
		}

	}

	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsulta.setVisible(false);
		groupboxEditar.setVisible(true);
		limpiarDatos();
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

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		listboxMun.getChildren().clear();
		Listitem listitem;
		String coddep = listboxDpto.getSelectedItem().getValue().toString();
		Map<String, Object> parameters = new HashMap();
		parameters.put("coddep", coddep);
		List<Municipios> municipios = this.getServiceLocator()
				.getMunicipiosService().listar(parameters);

		if (municipios.size() > 0) {
			for (Municipios mun : municipios) {
				listitem = new Listitem();
				listitem.setValue(mun.getCodigo());
				listitem.setLabel(mun.getNombre());
				listboxMun.appendChild(listitem);
			}
		} else {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel(" -seleccione- ");
			listboxMun.appendChild(listitem);
		}

		if (listboxMun.getItemCount() > 0) {
			listboxMun.setSelectedIndex(0);
		}
	}

	// Limpiamos los campos del formulario //
	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(this, sw, new String[] {
				"tbxValue", "lbxFormato", "northEditar" });
	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

			this.getServiceLocator().getAdministradoraService()
					.setLimit("limit 25 offset 0");

			List<Map> list = this.getServiceLocator()
					.getAdministradoraService()
					.listarDesdeContratos(parameters);

			lbx.getItems().clear();

			for (Map dato : list) {

				String codigo = (String) dato.get("codigo");
				String nombre = (String) dato.get("nombre");

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(codigo + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(nombre + ""));
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

	public void listarDepartamentos(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		List<Departamentos> departamentos = this.getServiceLocator()
				.getDepartamentosService().listar(new HashMap());

		for (Departamentos dpto : departamentos) {
			listitem = new Listitem();
			listitem.setValue(dpto.getCodigo());
			listitem.setLabel(dpto.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void loadComponents() {
		Utilidades.seleccionarListitem(lbxTipo_id_responsable, "CC");
		listarDepartamentos(lbxDepartamento, true);
		lbxDepartamento.setSelectedIndex(7);
		listarMunicipios(lbxMunicipio, lbxDepartamento);
		listarMunicipios(lbxMunicipio, lbxDepartamento);
		lbxDepartamento.setSelectedIndex(7);

		listarDepartamentos(lbxDep_responsable, true);
		lbxDep_responsable.setSelectedIndex(7);
		listarMunicipios(lbxMun_responsable, lbxDep_responsable);
		listarMunicipios(lbxMun_responsable, lbxDep_responsable);
		lbxDep_responsable.setSelectedIndex(7);
	}

	private void cargamosDatosDelPaciente() {
		Map parametros = Executions.getCurrent().getArg();
		admision = (Admision) parametros.get(IVias_ingreso.ADMISION_PACIENTE);
		if (admision != null) {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(empresa.getCodigo_empresa());
			paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = this.getServiceLocator().getPacienteService()
					.consultar(paciente);

			if (paciente != null) {
				tbxIdentificacion_paciente.setText(""
						+ paciente.getNro_identificacion());
				tbxDatos_paciente.setText(paciente.getApellido1() + " "
						+ paciente.getApellido2() + " " + paciente.getNombre1()
						+ " " + paciente.getNombre2());
				tbxDireccion_paciente.setText("" + paciente.getDireccion());
				tbxTelefono_paciente.setText("" + paciente.getTel_res());
				tbxFecha_nac_paciente.setValue(paciente.getFecha_nacimiento());
				tbxDirecion.setValue(admision.getAdministradora()
						.getDireccion());
			}

		}
	}

	// private void cargarDatosEntidad() {
	// 
	//
	// }

	public void cargarDatosIniciales() {
		Map parametros = Executions.getCurrent().getArg();
		admision = (Admision) parametros.get(IVias_ingreso.ADMISION_PACIENTE);
		loadComponents();
		tbxNombre_prestador.setValue(empresa.getNombre_empresa());
		tbxId.setText("" + empresa.getIdentificador());
		tbxDirecion.setValue(empresa.getDireccion());
		Utilidades.seleccionarListitem(lbxDepartamento,
				empresa.getCodigo_dpto());
		listarMunicipios(lbxMunicipio, lbxDepartamento);
		Utilidades.seleccionarListitem(lbxMunicipio,
				empresa.getCodigo_municipio());
		if (zkWindow instanceof HistoriaClinicaModel) {
			//log.info(""
					//+ ((HistoriaClinicaModel) zkWindow)
						//	.getIdentificacionAcompaniante());
			tbxNombre_responsable.setValue(((HistoriaClinicaModel) zkWindow)
					.getIdentificacionAcompaniante());
			Utilidades.seleccionarListitem(lbxTipo_id_responsable, "CC");
			tbxNro_id_responsable.setValue(((HistoriaClinicaModel) zkWindow)
					.getIdentificacionAcompaniante());
			ibxTer_responsable.setValue(ConvertidorDatosUtil
					.convertirDatot(((HistoriaClinicaModel) zkWindow)
							.getTelefonoAcompaniante()));
			Utilidades.seleccionarListitem(lbxDep_responsable,
					empresa.getCodigo_dpto());
			listarMunicipios(lbxMun_responsable, lbxDep_responsable);
			Utilidades.seleccionarListitem(lbxMun_responsable,
					empresa.getCodigo_municipio());

		}

	}

	// private void cargarInformacionEntidad(Admision admision) {
	// Map parametros = Executions.getCurrent().getArg();
	// admision = (Admision) parametros.get(IVias_ingreso.ADMISION_PACIENTE);
	// loadComponents();
	// tbxNombre_prestador.setValue(empresa.getNombre_empresa());
	// tbxId.setText("" + empresa.getIdentificador());
	// tbxDirecion.setValue(empresa.getDireccion());
	// Utilidades.seleccionarListitem(lbxDep_responsable,
	// empresa.getCodigo_dpto());
	// listarMunicipios(lbxMun_responsable, lbxDep_responsable);
	// Utilidades.seleccionarListitem(lbxMun_responsable,
	// empresa.getCodigo_municipio());
	//
	// }

	private void setPrestador(String codigo_prestador) {
		Administradora prestadoresCaja = new Administradora();
		prestadoresCaja.setCodigo(codigo_prestador);
		prestadoresCaja = this.getServiceLocator().getAdministradoraService()
				.consultar(prestadoresCaja);

		if (prestadoresCaja == null) {
			tbxPrestador_remision.setText("");
			tbxNombre_prestador.setValue("");
			tbxDirecion.setText("");
			tbxId.setText("");
		} else {
			tbxPrestador_remision
					.setValue((prestadoresCaja.getCodigo() != null && prestadoresCaja
							.getCodigo().isEmpty()) ? Integer
							.parseInt(prestadoresCaja.getCodigo()) : null);
			tbxNombre_prestador.setValue(prestadoresCaja.getNombre());
			tbxDirecion.setText("" + prestadoresCaja.getDireccion());
			tbxId.setText("" + prestadoresCaja.getNit());
		}
	}

	public void selectedProcedimientoCaja(Listitem listitem, Bandbox bandbox,
			Textbox textbox) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
		} else {
			Map<String, Object> dato = (Map<String, Object>) listitem
					.getValue();
			final String codigo_sups = (String) dato.get("codigo_cups");
			final String nombreve = (String) dato.get("descripcion");
			bandbox.setValue("" + codigo_sups);
			textbox.setValue("" + nombreve);
		}
		bandbox.close();
	}

	public void buscarProcediemntoCaja(String value, Listbox lbx)
			throws Exception {
		try {

			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("consulta_especializada", "S");

			this.getServiceLocator().getContratoPrestadoresService()
					.setLimit("limit 25 offset 0");

			List<Contrato_prestadores> list = this.getServiceLocator()
					.getContratoPrestadoresService().listar(parameters);
			//System.Out.Println("::- Procedimientos: " + list.size());
			lbx.getItems().clear();

			for (Contrato_prestadores dato : list) {
				Procedimientos procediemientoCaja = new Procedimientos();
				procediemientoCaja.setId_procedimiento(new Long(dato
						.getCodigo_soat()));
				procediemientoCaja = this.getServiceLocator()
						.getProcedimientosService()
						.consultar(procediemientoCaja);

				Listitem listitem = new Listitem();
				listitem.setValue(procediemientoCaja);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(procediemientoCaja
						.getCodigo_cups() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(procediemientoCaja
						.getDescripcion() + ""));
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

	public void selectedProcedimiento(Listitem listitem, BandboxObject bandbox,
			Textbox textbox) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
		} else {
			Map dato = (HashMap) listitem.getValue();

			String codigo_sups = (String) dato.get("codigo_cups");
			String nombreve = (String) dato.get("descripcion");
			bandbox.setValue("" + codigo_sups);
			bandbox.setObject(dato);
			textbox.setValue("" + nombreve);
		}
		bandbox.close();
	}

	public void buscarProcediemnto(String value, Listbox lbx) throws Exception {
		try {
			Integer nro_identificacion_prestador = tbxPrestador_remision
					.getValue();

			Map<String, Object> parametersContratos = new HashMap<String, Object>();
			parametersContratos.put("codigo_empresa",
					empresa.getCodigo_empresa());
			parametersContratos.put("codigo_sucursal",
					sucursal.getCodigo_sucursal());
			if (!Utilidades.noUsarPrestador(this.getParametros_empresa(), "02"))
				parametersContratos.put("codigo_administradora",
						nro_identificacion_prestador);
			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(admision);

			/* En esta parte dependiendo del medico, cargamos los procedimientos */

			parametersContratos.put("consulta_especializada", "S");
			parametersContratos.put("manual", manuales_tarifarios
					.getMaestro_manual().getTipo_manual());
			parametersContratos.put("limite_paginado", "limit 25 offset 0");
			List<Map<String, Object>> map_procedimeintos = this
					.getServiceLocator().getContratosService()
					.listarProcedimientos(parametersContratos);

			lbx.getItems().clear();

			for (Map<String, Object> dato : map_procedimeintos) {

				String nombreve = (String) dato.get("descripcion");
				String codigo = (String) dato.get("codigo");

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(codigo + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(nombreve + ""));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", "" + e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public boolean validarForm() throws Exception {
		tbxPrestador_remision
				.setStyle("text-transform:uppercase;background-color:white");
		tbxNombre_prestador
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		FormularioUtil.validarCamposObligatorios(tbxNro_id_responsable,
				tbxNombre_responsable, tbxApellido_responsable,
				tbxDir_responsable, ibxTer_responsable, lbxDep_responsable,
				lbxDepartamento, tbxDir_responsable, tbxNombre_prestador,
				tbxId, tbxDirecion, tbxNombre_responsable,
				lbxTipo_id_responsable, tbxPrestador_remision);

		if (!Utilidades.noUsarPrestador(this.getParametros_empresa(), "02")) {
			tbxPrestador_remision.setStyle("background-color:#F6BBBE");
			tbxNombre_prestador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					this.getUsuarios().getNombres() + " recuerde que...",
					Messagebox.OK, Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public void guardarDatos() throws Exception {
		try {

			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //
				Anexo10_entidad anexo10Entidad = new Anexo10_entidad();
				anexo10Entidad.setCodigo(codigo_anexo10);
				anexo10Entidad.setCodigo_empresa(empresa.getCodigo_empresa());
				anexo10Entidad
						.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				// anexo10Entidad.setNro_ingreso(historia_clinica.getNro_ingreso());

				// CargardorDeDatos.cargarDatosViewEnBean(zkWindow,
				// Anexo10_entidad.class, anexo10Entidad);

				anexo10Entidad.setCodigo_medico(this.getUsuarios().getCodigo());
				anexo10Entidad.setNombre_responsable(tbxNombre_responsable
						.getValue());
				anexo10Entidad.setInformacion_clinica(tbxInformacionClinica
						.getValue());
				anexo10Entidad.setApellido_responsable(tbxApellido_responsable
						.getValue());
				anexo10Entidad.setNro_id_responsable(tbxNro_id_responsable
						.getValue());
				anexo10Entidad.setCodigo_paciente(tbxIdentificacion_paciente
						.getValue());
				anexo10Entidad
						.setDir_responsable(tbxDir_responsable.getValue());
				anexo10Entidad
						.setTer_responsable(ibxTer_responsable.getValue());
				anexo10Entidad.setDep_responsable(lbxDep_responsable
						.getSelectedItem().getValue().toString());
				anexo10Entidad.setMun_responsable(lbxMun_responsable
						.getSelectedItem().getValue().toString());
				anexo10Entidad.setCel_responsable("");
				anexo10Entidad.setTipo_id_responsable(lbxTipo_id_responsable
						.getSelectedItem().getValue().toString());
				anexo10Entidad.setLeida("N");

				anexo10Entidad.setNombre_solicita(tbxNombre_solictante
						.getValue());
				anexo10Entidad.setNombre_contrarefiere(tbxNombre_contrarefiere
						.getValue());
				anexo10Entidad.setCodigo_servicio_contarefiere("122");
				anexo10Entidad.setTelefono_solicita(ConvertidorDatosUtil
						.convertirDato(dbxTelefono.getValue()));
				anexo10Entidad.setCel_solicita(ConvertidorDatosUtil
						.convertirDato(dbxCel.getValue()));
				anexo10Entidad.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				anexo10Entidad
						.setCreacion_user(usuarios.getCodigo().toString());
				// anexo10_entidad.setDelete_date();
				// anexo10_entidad.setDelete_user();

				anexo10Entidad.setDireccion_prestador(tbxDirecion.getValue());
				anexo10Entidad.setDepartamento_prestador(lbxDepartamento
						.getSelectedItem().getValue().toString());
				anexo10Entidad.setMunicipio_prestador(lbxMunicipio
						.getSelectedItem().getValue().toString());

				anexo10Entidad.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				anexo10Entidad.setUltimo_user(usuarios.getCodigo().toString());

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("historia_clinica", anexo10Entidad);
				//log.info("tbxAccion.getText() ===> " + tbxAccion.getText());
				datos.put("accion", tbxAccion.getText());
				getServiceLocator().getAnexo10EntidadService().guardarDatos(
						datos);

				codigo_anexo10 = anexo10Entidad.getCodigo();

				tbxAccion.setValue("modificar");

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}

	}

	public void listarCombos() {
		listarParameter();
		// listarElementoListbox(lbxTipo_identificacion,false);
		Utilidades.listarDepartamentos(lbxDep_responsable, true,
				getServiceLocator());
		Utilidades.listarDepartamentos(lbxDepartamento, true,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxMun_responsable, lbxDep_responsable,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_id_responsable, true,
				getServiceLocator());

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

			anexo10_entidadService.setLimit("limit 25 offset 0");

			List<Anexo10_entidad> lista_datos = anexo10_entidadService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Anexo10_entidad anexo10_entidad : lista_datos) {
				rowsResultado.appendChild(crearFilas(anexo10_entidad, this));
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

		final Anexo10_entidad anexo10_entidad = (Anexo10_entidad) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(anexo10_entidad);
			}
		});
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
									eliminarDatos(anexo10_entidad);
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

	protected void mostrarDatos(Object obj) throws Exception {
		Anexo10_entidad anexo10_entidad = (Anexo10_entidad) obj;
		try {
			// historia_clinica.setCodigo_historia(anexo10_entidad.getCodigo());
			setPrestador(anexo10_entidad.getCodigo_prestador());
			// CargardorDeDatos.mostrarEnVista(this, Anexo10_entidad.class,
			// anexo10_entidad);
			tbxNombre_responsable.setValue(anexo10_entidad
					.getNombre_responsable());
			tbxApellido_responsable.setValue(anexo10_entidad
					.getApellido_responsable());
			tbxNro_id_responsable.setValue(anexo10_entidad
					.getNro_id_responsable());
			tbxInformacionClinica.setValue(anexo10_entidad
					.getInformacion_clinica());
			tbxDir_responsable.setValue(anexo10_entidad.getDir_responsable());
			ibxTer_responsable.setValue(anexo10_entidad.getTer_responsable());
			tbxPrestador_remision.setValue(Integer.parseInt(anexo10_entidad
					.getCodigo_medico()));
			tbxNombre_solictante.setValue(anexo10_entidad.getNombre_solicita());
			dbxTelefono.setValue(ConvertidorDatosUtil
					.convertirDato(anexo10_entidad.getTelefono_solicita()));
			dbxCel.setValue(ConvertidorDatosUtil.convertirDato(anexo10_entidad
					.getCel_solicita()));
			tbxNombre_contrarefiere.setValue(""
					+ anexo10_entidad.getNombre_contrarefiere());
			for (int i = 0; i < lbxDep_responsable.getItemCount(); i++) {
				Listitem listitem = lbxDep_responsable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(anexo10_entidad.getDep_responsable())) {
					listitem.setSelected(true);
					i = lbxDep_responsable.getItemCount();
				}
			}
			Utilidades.listarMunicipios(lbxMun_responsable, lbxDep_responsable,
					getServiceLocator());
			for (int i = 0; i < lbxMun_responsable.getItemCount(); i++) {
				Listitem listitem = lbxMun_responsable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(anexo10_entidad.getMun_responsable())) {
					listitem.setSelected(true);
					i = lbxMun_responsable.getItemCount();
				}
			}
			// tbxCel_responsable.setValue(anexo10Entidad.getCel_responsable());
			for (int i = 0; i < lbxTipo_id_responsable.getItemCount(); i++) {
				Listitem listitem = lbxTipo_id_responsable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(anexo10_entidad.getTipo_id_responsable())) {
					listitem.setSelected(true);
					i = lbxTipo_id_responsable.getItemCount();
				}
			}

			tbxDirecion.setValue(anexo10_entidad.getDireccion_prestador());
			Utilidades.seleccionarListitem(lbxDepartamento,
					anexo10_entidad.getDepartamento_prestador());
			listarMunicipios(lbxMunicipio, lbxDepartamento);
			Utilidades.seleccionarListitem(lbxMunicipio,
					anexo10_entidad.getMunicipio_prestador());

			deshabilitarCampos(true);

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Anexo10_entidad anexo10_entidad = (Anexo10_entidad) obj;
		try {
			int result = anexo10_entidadService.eliminar(anexo10_entidad);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente!!",
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

}
