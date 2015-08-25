package healthmanager.controller;

import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo10_entidad;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Contrato_prestadores;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Anexo9_entidadService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.res.BandboxObject;
import com.framework.res.CargardorDeDatos;
import com.framework.res.ListItemtObject3;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Remisiones_externasAction extends Grid implements AfterCompose {

	private static Logger log = Logger
			.getLogger(Remisiones_externasAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// Componentes //
	@View
	private Textbox tbxNumero_solicitud;
	@View
	private Datebox dtbxFecha_remision;

	@View
	private Textbox tbxNombre_entidad;

	@View
	private Textbox tbxId_entidad;
	@View
	private Textbox tbxDirecion_entidad;
	@View
	private Listbox lbxDepartamento_entidad;
	@View
	private Listbox lbxMunicipio_entidad;
	@View
	private Textbox tbxIdentificacion_paciente;
	@View
	private Textbox tbxDatos_paciente;
	@View
	private Textbox tbxDireccion_paciente;
	@View
	private Doublebox dbxTelefono_paciente;
	@View
	private Datebox tbxFecha_nac_paciente;

	@View
	private Doublebox dbxTelefono_solicitante;
	@View
	private Doublebox dbxCel_solicitante;

	@View
	private Textbox tbxInformacionClinica;

	@View
	private Textbox tbxResponsable;
	@View
	private Textbox tbxNro_id_responsable;
	@View
	private Textbox tbxDir_responsable;
	@View
	private Doublebox dbxTel_responsable;
	@View
	private Listbox lbxDep_responsable;
	@View
	private Listbox lbxMun_responsable;

	@View
	private Listbox lbxTipo_id_responsable;

	@View
	private Toolbarbutton btnGenerar;

	@View
	private Textbox tbxNomServicio1;
	@View
	private Textbox tbxNomServicio2;

	@View
	private Toolbarbutton btnGuardarRemision;

	private String codigo_remsion;
	private Long codigo_contra_remision;

	private final String[] idsExcluyentes = { "lbxParameter", "tbxValue",
			"tbxAccion_remision", "tbxDirecion_entidad", "tbxNombre_entidad",
			"tbxId_entidad", "lbxDepartamento_entidad", "lbxMunicipio_entidad" };

	private Admision admision;

	private ZKWindow zkWindow;
	@View
	private Textbox tbxNombre_solictante;
	@View
	private Textbox tbxApellido_solicitante;
	@View
	private Toolbarbutton btn_imprimir_a9;
	@View
	private Toolbarbutton btn_eliminar_a9;

	private Long nro_historia;

	private Anexo9_entidad anexo9_entidad;

	private Prestadores prestadores_usuario;

	private String opcion_formulario_historia;

	public void inicializar(ZKWindow zkWindow) throws Exception {
		this.zkWindow = zkWindow;
		loadComponents();
		cargarDatosIniciales();
		crearNuevaRemision();
		generarRemision(false);
	}

	public void cargarParametros() {
		Map<String, Object> params = (Map<String, Object>) Executions
				.getCurrent().getArg();
		admision = (Admision) params.get("admision");
		opcion_formulario_historia = (String) params
				.get("opcion_formulario_historia");
	}

	public void cargarDatosIniciales() {
		Empresa empresa = zkWindow.getEmpresa();
		Centro_atencion centro_atencion = zkWindow.centro_atencion_session;
		tbxNombre_entidad.setValue(empresa.getNombre_empresa()
				+ (centro_atencion != null ? " - "
						+ centro_atencion.getNombre_centro() : ""));
		tbxId_entidad.setText("" + empresa.getNro_identificacion());
		tbxDirecion_entidad.setText("" + empresa.getDireccion());
		Utilidades.seleccionarListitem(lbxDepartamento_entidad,
				empresa.getCodigo_dpto());
		listarMunicipios(lbxMunicipio_entidad, lbxDepartamento_entidad);
		Utilidades.seleccionarListitem(lbxMunicipio_entidad,
				empresa.getCodigo_municipio());

		Utilidades.seleccionarListitem(lbxDep_responsable, "13");
		listarMunicipios(lbxMun_responsable, lbxDep_responsable);
		Utilidades.seleccionarListitem(lbxMunicipio_entidad, "001");

		if (admision != null) {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(zkWindow.getEmpresa()
					.getCodigo_empresa());
			paciente.setCodigo_sucursal(zkWindow.getSucursal()
					.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = zkWindow.getServiceLocator().getPacienteService()
					.consultar(paciente);
			if (paciente != null) {
				tbxIdentificacion_paciente.setText(""
						+ paciente.getNro_identificacion());
				tbxDatos_paciente.setText(paciente.getApellido1() + " "
						+ paciente.getApellido2() + " " + paciente.getNombre1()
						+ " " + paciente.getNombre2());
				tbxDireccion_paciente.setText("" + paciente.getDireccion());
				dbxTelefono_paciente.setValue(ConvertidorDatosUtil
						.convertirDato(paciente.getTel_res() != null ? paciente
								.getTel_res() : ""));
				tbxFecha_nac_paciente.setValue(paciente.getFecha_nacimiento());
			}
		}

		// lbxTipo_id_responsable.setSelectedIndex(2);
		// tbxNro_id_responsable.setValue(citas.getCedula_acomp());
		// tbxTer_responsable.setValue(citas.getTel_acompaniante());

	}

	// Limpiamos los campos del formulario //
	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(this, sw, new String[] {
				"tbxValue", "lbxFormato", "northEditar", "tbxDirecion_entidad",
				"tbxNombre_entidad", "tbxId_entidad",
				"lbxDepartamento_entidad", "lbxMunicipio_entidad",
				"btnGuardarRemision" });
	}

	public void generarRemision(boolean nueva) {
		try {
			btnGenerar.setDisabled(nueva);
			// btn_imprimir_a9.setDisabled(!nueva);
			btn_eliminar_a9.setDisabled(!nueva);
			this.getRows().setVisible(nueva);
			if (nueva) {
				if (opcion_formulario_historia != null) {
					if (opcion_formulario_historia.equals("REGISTRAR")) {
						btnGuardarRemision.setVisible(false);
					} else {
						btnGuardarRemision.setVisible(true);
					}
				} else {
					btnGuardarRemision.setVisible(false);
				}
				deshabilitarCampos(false);
				if (zkWindow instanceof HistoriaClinicaModel) {
					tbxInformacionClinica
							.setValue(((HistoriaClinicaModel) zkWindow)
									.getInformacionClinica());
					tbxResponsable.setValue(((HistoriaClinicaModel) zkWindow)
							.getPersonaAcompaniante());
					tbxNro_id_responsable
							.setValue(((HistoriaClinicaModel) zkWindow)
									.getIdentificacionAcompaniante());
					dbxTel_responsable.setValue(ConvertidorDatosUtil
							.convertirDato(((HistoriaClinicaModel) zkWindow)
									.getTelefonoAcompaniante()));
					tbxNomServicio1.setValue(((HistoriaClinicaModel) zkWindow)
							.getServicioSolicitaReferencia1());
				}
				dtbxFecha_remision.setValue(new Date());
				Utilidades.seleccionarListitem(lbxTipo_id_responsable, "CC");

				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(zkWindow.getEmpresa()
						.getCodigo_empresa());
				prestadores.setCodigo_sucursal(zkWindow.getSucursal()
						.getCodigo_sucursal());
				prestadores.setNro_identificacion(admision.getCodigo_medico());
				prestadores = zkWindow.getServiceLocator()
						.getPrestadoresService().consultar(prestadores);
				if (prestadores != null) {
					prestadores_usuario = prestadores;
					tbxNombre_solictante.setValue(prestadores.getNombres());
					tbxApellido_solicitante
							.setValue(prestadores.getApellidos());
					dbxTelefono_solicitante
							.setValue(ConvertidorDatosUtil
									.convertirDato(zkWindow.getEmpresa()
											.getTelefonos()));
					// dbxCel_solicitante.setValue(ConvertidorDatosUtil.convertirDato(prestadores.getCelular()));
				}

			}
			this.invalidate();
		} catch (Exception e) {
			if (e instanceof WrongValueException) {
				Clients.scrollIntoView(((WrongValueException) e).getComponent());
				Clients.wrongValue(((WrongValueException) e).getComponent(), "");
			}
			generarRemision(false);
		}

	}

	public void cancelarRemision() {
		Messagebox.show(
				"¿Esta seguro que desea cancelar la remision ya diligenciada?",
				"Confirmacion cancelacion", Messagebox.YES + Messagebox.NO,
				Messagebox.QUESTION, new EventListener<Event>() {

					@Override
					public void onEvent(Event evento) throws Exception {
						if (evento.getName().equals("onYes")) {
							generarRemision(false);

							if (anexo9_entidad != null)
								zkWindow.getServiceLocator()
										.getAnexo9EntidadService()
										.eliminar(anexo9_entidad);

							btnGuardarRemision.setVisible(false);
						}
					}
				});
	}

	private void deshabilitarButtom(boolean sw) throws Exception {
		System.out.println("metodo desabilitar");
		Collection<Component> collection = this.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Button) {
				((Button) abstractComponent).setDisabled(sw);
			}
		}
		btnGenerar.setDisabled(false);
	}

	private void setPrestador(String codigo_prestador) {
		Administradora prestadoresCaja = new Administradora();
		prestadoresCaja.setCodigo(codigo_prestador);
		prestadoresCaja = zkWindow.getServiceLocator()
				.getAdministradoraService().consultar(prestadoresCaja);

		// if (prestadoresCaja == null) {
		// bandboxPrestador_remision.limpiarSeleccion(true);
		// tbxNombre_entidad.setValue("");
		// tbxDirecion_entidad.setText("");
		// tbxId_entidad.setText("");
		// } else {
		// bandboxPrestador_remision.setValue(prestadoresCaja.getCodigo());
		// tbxNombre_entidad.setValue(prestadoresCaja.getNombre());
		// tbxDirecion_entidad.setText("" + prestadoresCaja.getDireccion());
		// tbxId_entidad.setText("" + prestadoresCaja.getNit());
		// }
	}

	public void setUpperCase() {
		Collection<Component> collection = this.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent)
							.setText(((Textbox) abstractComponent).getText()
									.trim().toUpperCase());
				}
			}
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
			parameters.put("codigo_empresa", zkWindow.getSucursal()
					.getCodigo_empresa());
			parameters.put("codigo_sucursal", zkWindow.getSucursal()
					.getCodigo_sucursal());
			parameters.put("consulta_especializada", "S");

			zkWindow.getServiceLocator().getContratoPrestadoresService()
					.setLimit("limit 25 offset 0");

			List<Contrato_prestadores> list = zkWindow.getServiceLocator()
					.getContratoPrestadoresService().listar(parameters);
			System.out.println("::- Procedimientos: " + list.size());
			lbx.getItems().clear();

			for (Contrato_prestadores dato : list) {
				Procedimientos procediemientoCaja = new Procedimientos();
				procediemientoCaja.setId_procedimiento(new Long(dato
						.getCodigo_soat()));
				procediemientoCaja = zkWindow.getServiceLocator()
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

	public void buscarProcedimiento(String value, Listbox lbx) throws Exception {
		try {
			String nro_identificacion_prestador = prestadores_usuario != null ? prestadores_usuario
					.getNro_identificacion() : "";

			Map<String, Object> parametersContratos = new HashMap<String, Object>();

			parametersContratos.put("codigo_empresa", zkWindow.getSucursal()
					.getCodigo_empresa());
			parametersContratos.put("codigo_sucursal", zkWindow.getSucursal()
					.getCodigo_sucursal());
			if (!Utilidades.noUsarPrestador(zkWindow.getParametros_empresa(),
					"02"))
				parametersContratos.put("codigo_administradora",
						nro_identificacion_prestador);
			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(admision);
			/*
			 * En esta parte dependiendo del medico, cargamos los procedimientos
			 */

			parametersContratos.put("consulta_especializada", "S");
			parametersContratos.put("manual", manuales_tarifarios
					.getMaestro_manual().getTipo_manual());

			parametersContratos.put("limite_paginado", "limit 25 offset 0");
			List<Map<String, Object>> map_procedimeintos = zkWindow
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

	public void seleccion_especialidad(String value, Hbox hbox) {
		hbox.setVisible((!value.isEmpty() && value.equals("04")));
	}

	public boolean validarForm() throws Exception {
		boolean valida = true;

		if (tbxNro_id_responsable.getText().trim().equals("")) {
			tbxNro_id_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (tbxDir_responsable.getText().trim().equals("")) {
			tbxDir_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (dbxTel_responsable.getText().trim().equals("")) {
			dbxTel_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (lbxDep_responsable.getSelectedIndex() == 0) {
			lbxDep_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (lbxTipo_id_responsable.getSelectedIndex() == 0) {
			lbxTipo_id_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					zkWindow.getUsuarios().getNombres() + " recuerde que...",
					Messagebox.OK, Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public void guardarDatos() throws Exception {
		try {
			Anexo9_entidad anexo9_entidad = obtenerAnexo9();
			anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
			anexo9_entidad.setNro_historia(nro_historia);
			if (zkWindow.getServiceLocator().getAnexo9EntidadService()
					.consultar(anexo9_entidad) != null) {
				zkWindow.getServiceLocator().getAnexo9EntidadService()
						.actualizar(anexo9_entidad);
			} else {
				zkWindow.getServiceLocator().getAnexo9EntidadService()
						.guardar(anexo9_entidad);
			}
			mostrarAnexo9(anexo9_entidad);
			MensajesUtil.mensajeInformacion("Remision generada",
					"La remision externa ha sido generada satisfactoriamente");
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", zkWindow);
			} else {
				log.error(((WrongValueException) e).getComponent().getId()
						+ " esta presentando error", e);
			}
		}
	}

	public void loadComponents() {
		// bandboxPrestador_remision = (BandboxRegistrosMacro) this.getFellow(
		// "bandboxPrestador_remision").getFirstChild();
		listarElementoListbox(lbxTipo_id_responsable, true);
		Utilidades.seleccionarListitem(lbxTipo_id_responsable, "CC");
		listarDepartamentos(lbxDep_responsable, true);
		listarDepartamentos(lbxDepartamento_entidad, true);
		lbxDepartamento_entidad.setSelectedIndex(7);
		listarMunicipios(lbxMun_responsable, lbxDep_responsable);
		listarMunicipios(lbxMunicipio_entidad, lbxDepartamento_entidad);
	}

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		listboxMun.getChildren().clear();
		Listitem listitem;
		String coddep = listboxDpto.getSelectedItem().getValue().toString();
		Map<String, Object> parameters = new HashMap();
		parameters.put("coddep", coddep);
		List<Municipios> municipios = zkWindow.getServiceLocator()
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

	public void imprimir() throws Exception {
		Anexo9_entidad anexo9_entidad_aux = new Anexo9_entidad();
		anexo9_entidad_aux.setCodigo_empresa(zkWindow.getSucursal()
				.getCodigo_empresa());
		anexo9_entidad_aux.setCodigo_sucursal(zkWindow.getSucursal()
				.getCodigo_sucursal());
		anexo9_entidad_aux.setCodigo(codigo_remsion);

		anexo9_entidad_aux = zkWindow.getServiceLocator()
				.getServicio(Anexo9_entidadService.class)
				.consultar(anexo9_entidad_aux);
		
		//log.info("anexo9_entidad_aux ===> "+anexo9_entidad_aux);

		if (anexo9_entidad_aux != null) {
			Map paramRequest = new HashMap();
			paramRequest.put("name_report", "Anexo9Remision");
			paramRequest.put("codigo", codigo_remsion + "");
			paramRequest.put("codigo_empresa", zkWindow.getSucursal()
					.getCodigo_empresa());
			paramRequest.put("codigo_sucursal", zkWindow.getSucursal()
					.getCodigo_sucursal());

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", zkWindow, paramRequest);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			MensajesUtil
					.mensajeAlerta("No se ha generado Remision externa",
							"La remision externa no se puede imprimir porque no se ha generado aun");
		}
	}

	public void imprimirAnexo10() throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Anexo10Remision");
		paramRequest.put("codigo", codigo_contra_remision + "");
		paramRequest.put("codigo_empresa", zkWindow.getSucursal()
				.getCodigo_empresa());
		paramRequest.put("codigo_sucursal", zkWindow.getSucursal()
				.getCodigo_sucursal());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", zkWindow, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
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
		List<Departamentos> departamentos = zkWindow.getServiceLocator()
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

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		ListItemtObject3 listitem;
		if (select) {
			listitem = new ListItemtObject3();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = zkWindow.getServiceLocator()
				.getElementoService().listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new ListItemtObject3();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listitem.setObject(elemento);
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void afterCargarDatosSession(HttpServletRequest request) {

	}

	@Override
	public void afterCompose() {
		try {
			cargarParametros();
			CargardorDeDatos.initComponents(this);
		} catch (Exception e) {
			MensajesUtil.mensajeAlerta("Error en el metodo afterCompose",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Anexo9_entidad anexo9_entidad = (Anexo9_entidad) objeto;

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(zkWindow.codigo_empresa);
		administradora.setCodigo_sucursal(zkWindow.codigo_sucursal);
		administradora.setCodigo(anexo9_entidad.getCodigo_prestador());
		administradora = zkWindow.getServiceLocator()
				.getAdministradoraService().consultar(administradora);

		String nombre_prestador = " -- ";
		if (administradora != null) {
			nombre_prestador = administradora.getNombre();
		}

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.setTooltiptext(anexo9_entidad.getInformacion_clinica());
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(anexo9_entidad.getFecha())));
		fila.appendChild(new Label(anexo9_entidad.getNombre_solicita() + ""));
		fila.appendChild(new Label(nombre_prestador + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/vista_previa.png");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(anexo9_entidad);
			}
		});
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	public void limpiarDatos() {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
		cargarDatosIniciales();
		tbxIdentificacion_paciente.setReadonly(true);
		tbxDireccion_paciente.setReadonly(true);
		tbxFecha_nac_paciente.setReadonly(true);
		tbxDatos_paciente.setReadonly(true);
		dbxTelefono_paciente.setReadonly(true);
	}

	protected void mostrarDatos(Anexo9_entidad anexo9_entidad) {
		try {
			limpiarDatos();
			deshabilitarButtom(true);
			codigo_remsion = anexo9_entidad.getCodigo();
			setPrestador(anexo9_entidad.getCodigo_prestador());
			// CargardorDeDatos.mostrarEnVista(this, Anexo9_entidad.class,
			// anexo9_entidad);
			tbxResponsable.setValue(anexo9_entidad.getNombre_responsable());
			tbxNro_id_responsable.setValue(anexo9_entidad
					.getNro_id_responsable());
			tbxDir_responsable.setValue(anexo9_entidad.getDir_responsable());
			dbxTel_responsable.setValue(ConvertidorDatosUtil
					.convertirDato(anexo9_entidad.getTer_responsable()));
			for (int i = 0; i < lbxDep_responsable.getItemCount(); i++) {
				Listitem listitem = lbxDep_responsable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(anexo9_entidad.getDep_responsable())) {
					listitem.setSelected(true);
					i = lbxDep_responsable.getItemCount();
				}
			}
			listarMunicipios(lbxMun_responsable, lbxDep_responsable);
			for (int i = 0; i < lbxMun_responsable.getItemCount(); i++) {
				Listitem listitem = lbxMun_responsable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(anexo9_entidad.getMun_responsable())) {
					listitem.setSelected(true);
					i = lbxMun_responsable.getItemCount();
				}
			}
			// tbxCel_responsable.setValue(anexo9Entidad.getCel_responsable());
			for (int i = 0; i < lbxTipo_id_responsable.getItemCount(); i++) {
				Listitem listitem = lbxTipo_id_responsable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(anexo9_entidad.getTipo_id_responsable())) {
					listitem.setSelected(true);
					i = lbxTipo_id_responsable.getItemCount();
				}
			}

			/* mostramos servicios autorizados */

			tbxNomServicio1
					.setValue("" + anexo9_entidad.getServicio_solicita());
			tbxNomServicio2.setValue(""
					+ anexo9_entidad.getServico_cual_solicita());

			deshabilitarButtom(true);
			deshabilitarCampos(true);

			/* verificamos contraremision */
			Anexo10_entidad anexo10Entidad = new Anexo10_entidad();
			anexo10Entidad.setCodigo_empresa(zkWindow.getSucursal()
					.getCodigo_empresa());
			anexo10Entidad.setCodigo_sucursal(zkWindow.getSucursal()
					.getCodigo_sucursal());
			anexo10Entidad.setCodigo_remision(anexo9_entidad.getCodigo());
			anexo10Entidad = zkWindow.getServiceLocator()
					.getAnexo10EntidadService().consultar(anexo10Entidad);
			if (anexo10Entidad != null) {
				codigo_contra_remision = anexo10Entidad.getCodigo();
			}
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void crearNuevaRemision() throws Exception {
		deshabilitarCampos(false);
		limpiarDatos();
	}

	public boolean validarRemision() throws Exception {
		boolean valida = true;
		if (this.getRows().isVisible()) {
			// valida = false;
			FormularioUtil.validarCamposObligatorios(tbxDirecion_entidad,
					tbxResponsable, tbxNro_id_responsable, tbxDir_responsable,
					dbxTel_responsable, tbxNombre_solictante,
					tbxApellido_solicitante, dbxTelefono_solicitante,
					tbxNomServicio1, tbxNomServicio2, lbxDepartamento_entidad,
					lbxMunicipio_entidad, lbxTipo_id_responsable,
					lbxDep_responsable, lbxMun_responsable);

		}

		return valida;
	}

	public Anexo9_entidad obtenerAnexo9() throws Exception {
		if (!this.getRows().isVisible()) {
			return null;
		}
		Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
		if (validarRemision()) {
			anexo9_entidad.setCodigo_empresa(zkWindow.getEmpresa()
					.getCodigo_empresa());
			anexo9_entidad.setCodigo_sucursal(zkWindow.getSucursal()
					.getCodigo_sucursal());
			anexo9_entidad.setApellido_responsable("");
			anexo9_entidad.setCel_responsable("");
			anexo9_entidad.setCel_solicita(ConvertidorDatosUtil
					.convertirDato(dbxCel_solicitante.getValue()));
			anexo9_entidad
					.setCodigo(tbxNumero_solicitud.getValue().isEmpty() ? null
							: tbxNumero_solicitud.getValue());
			anexo9_entidad
					.setCodigo_medico(prestadores_usuario != null ? prestadores_usuario
							.getNro_identificacion() : "");
			anexo9_entidad.setCodigo_paciente(admision.getNro_identificacion());
			anexo9_entidad
					.setCodigo_prestador(prestadores_usuario != null ? prestadores_usuario
							.getNro_identificacion() : "");

			anexo9_entidad.setCreacion_date(new Timestamp((new Date())
					.getTime()));
			anexo9_entidad.setCreacion_user(zkWindow.getUsuarios().getCodigo());
			anexo9_entidad
					.setDelete_date(new Timestamp((new Date()).getTime()));
			anexo9_entidad.setDelete_user(zkWindow.getUsuarios().getCodigo());
			anexo9_entidad.setDep_responsable(lbxDep_responsable
					.getSelectedItem().getValue().toString());
			anexo9_entidad.setDir_responsable(tbxDir_responsable.getValue());
			anexo9_entidad.setInformacion_clinica(tbxInformacionClinica
					.getValue());
			anexo9_entidad.setFecha(new Timestamp(dtbxFecha_remision.getValue()
					.getTime()));
			anexo9_entidad.setLeida("");
			anexo9_entidad.setMun_responsable(lbxMun_responsable
					.getSelectedItem().getValue().toString());
			anexo9_entidad.setNombre_responsable(tbxResponsable.getValue());
			anexo9_entidad.setNombre_solicita(tbxNombre_solictante.getValue());
			anexo9_entidad.setNro_id_responsable(tbxNro_id_responsable
					.getValue());
			anexo9_entidad.setServicio_solicita(tbxNomServicio1.getValue());
			anexo9_entidad.setServico_cual_solicita(tbxNomServicio2.getValue());
			anexo9_entidad.setTelefono_solicita(dbxTelefono_solicitante
					.getText());
			anexo9_entidad.setTer_responsable(dbxTel_responsable.getText());
			anexo9_entidad.setTipo_id_responsable(lbxTipo_id_responsable
					.getSelectedItem().getValue().toString());
			anexo9_entidad.setTipo_solicitud1("");
			anexo9_entidad.setTipo_solicitud2("");
			anexo9_entidad.setUltimo_update(new Timestamp((new Date())
					.getTime()));
			anexo9_entidad.setUltimo_user(zkWindow.getUsuarios().getCodigo());

			anexo9_entidad.setDireccion_prestador(tbxDirecion_entidad
					.getValue());
			anexo9_entidad.setDepartamento_prestador(lbxDepartamento_entidad
					.getSelectedItem().getValue().toString());
			anexo9_entidad.setMunicipio_prestador(lbxMunicipio_entidad
					.getSelectedItem().getValue().toString());
			anexo9_entidad.setNombre_completo_prestador(tbxNombre_entidad
					.getValue());

		}
		return anexo9_entidad;
	}

	public void mostrarAnexo9(Anexo9_entidad anexo9_entidad) {
		this.anexo9_entidad = anexo9_entidad;
		if (this.anexo9_entidad == null) {
			this.anexo9_entidad = new Anexo9_entidad();
			this.anexo9_entidad.setCodigo_empresa(zkWindow.codigo_empresa);
			this.anexo9_entidad.setCodigo_sucursal(zkWindow.codigo_sucursal);
			anexo9_entidad = this.anexo9_entidad;
			generarRemision(false);
		} else {
			generarRemision(true);
			btn_imprimir_a9.setVisible(true);
			btn_imprimir_a9.setDisabled(false);
			codigo_remsion = anexo9_entidad.getCodigo();
		}

		dbxCel_solicitante.setValue(ConvertidorDatosUtil
				.convertirDato(anexo9_entidad.getCel_solicita()));
		tbxNumero_solicitud.setValue(anexo9_entidad.getCodigo());
		tbxNombre_entidad.setValue(anexo9_entidad
				.getNombre_completo_prestador());

		Prestadores prestadores = new Prestadores();
		prestadores.setCodigo_empresa(zkWindow.codigo_empresa);
		prestadores.setCodigo_sucursal(zkWindow.codigo_sucursal);
		prestadores.setNro_identificacion(anexo9_entidad.getCodigo_medico());
		prestadores = zkWindow.getServiceLocator().getPrestadoresService()
				.consultar(prestadores);
		// bandboxPrestador_remision.seleccionarRegistro(prestadores,
		// anexo9_entidad.getCodigo_medico(),
		// prestadores != null ? prestadores.getNombres() + " "
		// + prestadores.getApellidos() : "");
		// bandboxPrestador_remision.getButtonLimpiar().setVisible(false);
		tbxId_entidad
				.setValue(prestadores != null ? prestadores
						.getNro_identificacion() : anexo9_entidad
						.getCodigo_prestador());
		Utilidades.seleccionarListitem(lbxDep_responsable,
				anexo9_entidad.getDep_responsable());
		listarMunicipios(lbxMun_responsable, lbxDep_responsable);
		tbxDir_responsable.setValue(anexo9_entidad.getDir_responsable());
		tbxInformacionClinica.setValue(anexo9_entidad.getInformacion_clinica());
		dtbxFecha_remision.setValue(anexo9_entidad.getFecha());
		Utilidades.seleccionarListitem(lbxMun_responsable,
				anexo9_entidad.getMun_responsable());
		tbxResponsable.setValue(anexo9_entidad.getNombre_responsable());
		tbxNombre_solictante.setValue(anexo9_entidad.getNombre_solicita());
		tbxNro_id_responsable.setValue(anexo9_entidad.getNro_id_responsable());
		tbxNomServicio1.setValue(anexo9_entidad.getServicio_solicita());
		tbxNomServicio2.setValue(anexo9_entidad.getServico_cual_solicita());
		dbxTelefono_solicitante.setValue(ConvertidorDatosUtil
				.convertirDato(anexo9_entidad.getTelefono_solicita()));
		dbxTel_responsable.setValue(ConvertidorDatosUtil
				.convertirDato(anexo9_entidad.getTer_responsable()));
		Utilidades.seleccionarListitem(lbxTipo_id_responsable,
				anexo9_entidad.getTipo_id_responsable());

		tbxDirecion_entidad.setValue(anexo9_entidad.getDireccion_prestador());
		Utilidades.seleccionarListitem(lbxDepartamento_entidad,
				anexo9_entidad.getDepartamento_prestador());
		listarMunicipios(lbxMunicipio_entidad, lbxDepartamento_entidad);
		Utilidades.seleccionarListitem(lbxMunicipio_entidad,
				anexo9_entidad.getMunicipio_prestador());

		cargarDatosIniciales();

	}

	public void setCodigo_remision(String codigo) {
		tbxNumero_solicitud.setValue(codigo);
		codigo_remsion = codigo;
	}

	public Toolbarbutton getBotonImprimir() {
		return btn_imprimir_a9;
	}

	public Toolbarbutton getBotonGenerar() {
		return btnGenerar;
	}

	public Toolbarbutton getBotonGuardarRemision() {
		return btnGuardarRemision;
	}

	public ZKWindow getZkWindow() {
		return zkWindow;
	}

	public void setZkWindow(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
	}

	public Long getNro_historia() {
		return nro_historia;
	}

	public void setNro_historia(Long nro_historia) {
		this.nro_historia = nro_historia;
	}

}
