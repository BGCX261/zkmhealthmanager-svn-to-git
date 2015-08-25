package healthmanager.controller;

import healthmanager.controller.ZKWindow.View;
import healthmanager.modelo.bean.Anexo10_entidad;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Prestadores_caja;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.CargardorDeDatos;
import com.framework.res.Main;
import com.framework.res.RadioGroupL2H;

public class Anexo10_entidadAction extends Window implements AfterCompose {

	private static Logger log = Logger.getLogger(Anexo3_entidadAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	private Window form;

	// Componentes //
	@View
	private Textbox tbxNumero_solicitud;
	@View
	private Datebox dtbxFecha;
	@View(classField = Anexo10_entidad.class, field = "codigo_prestador")
	private Bandbox tbxCodigo_prestador;
	@View
	private Textbox tbxNomPrestador;
	// @View private Textbox tbxCodigo;
	@View
	private Textbox tbxId;
	@View
	private Textbox tbxdirecion;
	@View
	private Textbox tbxDepartamento;
	@View
	private Textbox tbxMunicipio;
	@View(classField = Anexo10_entidad.class, field = "codigo_paciente")
	private Textbox tbxIdentificacionPaciente;
	@View
	private Textbox tbxTipoId;
	@View
	private Textbox tbxapellido1Paciente;
	@View
	private Textbox tbxapellido2paciente;
	@View
	private Textbox tbxnombre1Paciente;
	@View
	private Textbox tbxnombre2paciente;
	@View
	private Textbox tbxdirPaciente;
	@View
	private Textbox tbxtelpaciente;
	@View
	private Datebox tbxFechNacpaciente;
	@View(classField = Anexo10_entidad.class, field = "nombre_solicita")
	private Textbox tbxNombre_solicita;
	@View(classField = Anexo10_entidad.class, field = "telefono_solicita")
	private Textbox tbxTelefono;

	@View(classField = Anexo10_entidad.class, field = "cel_solicita")
	private Textbox tbxCel;
	@View(classField = Anexo10_entidad.class, field = "informacion_clinica")
	private Textbox tbxInformacionClinica;

	@View
	private Bandbox tbxServico1;
	@View
	private Textbox tbxNomServicio1;

	@View
	private Textbox tbxNombre_responsable;
	@View
	private Textbox tbxApellido_responsable;
	@View
	private Textbox tbxNro_id_responsable;
	@View
	private Textbox tbxDir_responsable;
	@View
	private Intbox tbxTer_responsable;
	@View
	private Listbox lbxDep_responsable;
	@View
	private Listbox lbxMun_responsable;
	// @View private Textbox tbxCel_responsable;
	@View
	private Listbox lbxTipo_id_responsable;

	private Anexo9_entidad anexo9Entidad;
	private Long codigo_contra_remision;

	@Override
	public void afterCompose() {
		
		try {
			loadComponents();
			cargarDatosSesion();
			verificamosSiYaTieneHistoriaClinica();
			cargamosDatosDeMedico();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargamosDatosDeMedico() {
		
		tbxNombre_solicita.setText("" + usuarios.getNombres() + " "
				+ usuarios.getApellidos());
		tbxTelefono.setText("" + usuarios.getTel_res());
		tbxCel.setText("" + usuarios.getCelular());
	}

	// Limpiamos los campos del formulario //
	public void deshabilitarCampos(boolean sw) throws Exception {
		Collection<Component> collection = form.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setDisabled(sw);
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals("lbxFormato")) {
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setDisabled(sw);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Bandbox) {
				((Bandbox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Radio) {
				((Radio) abstractComponent).setDisabled(sw);
			}

			if (abstractComponent instanceof RadioGroupL2H) {
				((RadioGroupL2H) abstractComponent).setDisabled(sw);
			}

			if (abstractComponent instanceof Radiogroup) {
				for (Object abstractComponentTemp : ((Radiogroup) abstractComponent)
						.getChildren()) {
					if (abstractComponentTemp instanceof Radio)
						((Radio) abstractComponentTemp).setDisabled(sw);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void verificamosSiYaTieneHistoriaClinica() throws Exception {
		
		Map<String, Object> parametros = (Map<String, Object>) Executions
				.getCurrent().getArg();
		anexo9Entidad = (Anexo9_entidad) parametros.get("anexo9");

		if (anexo9Entidad == null) {
			deshabilitarButtom(true);
		} else {
			/* verificamos si existe el anexo 9 */
			Anexo10_entidad anexo10Entidad = new Anexo10_entidad();
			anexo10Entidad.setCodigo_empresa(sucursal.getCodigo_empresa());
			anexo10Entidad.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			anexo10Entidad.setCodigo_remision(anexo9Entidad.getCodigo());
			anexo10Entidad = getServiceLocator().getAnexo10EntidadService()
					.consultar(anexo10Entidad);
			//System.Out.Println("anexo 10: " + anexo10Entidad);
			setPrestador(anexo9Entidad.getCodigo_prestador());
			cargamosDatosDelPaciente(anexo9Entidad.getCodigo_paciente());
			//System.Out.Println("antes anexo 9");

			tbxNombre_solicita.setValue(anexo9Entidad.getNombre_solicita());
			tbxTelefono.setValue(anexo9Entidad.getTelefono_solicita());
			tbxCel.setValue(anexo9Entidad.getCel_solicita());
			// CargardorDeDatos.mostrarEnVista(this, Anexo10_entidad.class,
			// anexo9Entidad);
			tbxNombre_responsable.setValue(anexo9Entidad
					.getNombre_responsable());
			tbxApellido_responsable.setValue(anexo9Entidad
					.getApellido_responsable());
			tbxNro_id_responsable.setValue(anexo9Entidad
					.getNro_id_responsable());
			tbxDir_responsable.setValue(anexo9Entidad.getDir_responsable());
			for (int i = 0; i < lbxDep_responsable.getItemCount(); i++) {
				Listitem listitem = lbxDep_responsable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(anexo9Entidad.getDep_responsable())) {
					listitem.setSelected(true);
					i = lbxDep_responsable.getItemCount();
				}
			}
			listarMunicipios(lbxMun_responsable, lbxDep_responsable);
			for (int i = 0; i < lbxMun_responsable.getItemCount(); i++) {
				Listitem listitem = lbxMun_responsable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(anexo9Entidad.getMun_responsable())) {
					listitem.setSelected(true);
					i = lbxMun_responsable.getItemCount();
				}
			}
			// tbxCel_responsable.setValue(anexo9Entidad.getCel_responsable());
			for (int i = 0; i < lbxTipo_id_responsable.getItemCount(); i++) {
				Listitem listitem = lbxTipo_id_responsable.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(anexo9Entidad.getTipo_id_responsable())) {
					listitem.setSelected(true);
					i = lbxTipo_id_responsable.getItemCount();
				}
			}
			deshabilitarCampos(true);
			tbxServico1.setDisabled(false);
			tbxInformacionClinica.setDisabled(false);
			if (anexo10Entidad != null) {
				deshabilitarButtom(true);
				tbxServico1.setReadonly(true);
				tbxInformacionClinica.setReadonly(true);
				codigo_contra_remision = anexo10Entidad.getCodigo();
				tbxServico1
						.setValue(anexo10Entidad.getServicio_contrarefiere());
				tbxInformacionClinica.setValue(anexo10Entidad
						.getInformacion_clinica());
				tbxServico1.setValue(anexo10Entidad
						.getCodigo_servicio_contarefiere());
				tbxNomServicio1.setValue(anexo10Entidad
						.getServicio_contrarefiere());
			}
		}
	}

	@SuppressWarnings("unused")
	private String getDescripcionElement(String codigo, String tipo) {
		Elemento elemento = new Elemento();
		elemento.setTipo(tipo);
		elemento.setCodigo(codigo);
		elemento = getServiceLocator().getElementoService().consultar(elemento);
		return elemento != null ? elemento.getDescripcion() : "";
	}

	private void deshabilitarButtom(boolean sw) throws Exception {
		//System.Out.Println("metodo desabilitar");
		Collection<Component> collection = form.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Button) {
				((Button) abstractComponent).setDisabled(sw);
			}
		}
	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

			getServiceLocator().getAdministradoraService().setLimit(
					"limit 25 offset 0");

			List<Prestadores> list = getServiceLocator()
					.getPrestadoresService().listar(parameters);

			lbx.getItems().clear();

			for (Prestadores dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombres() + ""));
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

	private void cargamosDatosDelPaciente(String nroIdentificacion) {
		
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(empresa.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(nroIdentificacion);
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null) {
			tbxIdentificacionPaciente.setText(""
					+ paciente.getNro_identificacion());
			tbxTipoId.setText("" + paciente.getTipo_identificacion());
			tbxapellido1Paciente.setText("" + paciente.getApellido1());
			tbxapellido2paciente.setText("" + paciente.getApellido2());
			tbxnombre1Paciente.setText("" + paciente.getNombre1());
			tbxnombre2paciente.setText("" + paciente.getNombre2());
			tbxdirPaciente.setText("" + paciente.getDireccion());
			tbxtelpaciente.setText("" + paciente.getTel_res());
			tbxFechNacpaciente.setValue(paciente.getFecha_nacimiento());
		}
	}

	public void selectedPrestador(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxCodigo_prestador.setValue("");
			tbxNomPrestador.setValue("");
			tbxDepartamento.setText("");
			tbxMunicipio.setText("");
			tbxdirecion.setText("");
			tbxId.setText("");
		} else {
			Prestadores_caja dato = (Prestadores_caja) listitem.getValue();
			tbxCodigo_prestador.setValue(dato.getCodigo());
			tbxNomPrestador.setValue(dato.getNombres());
			tbxDepartamento.setText(getNombreDepartamento(dato.getDptdo()));
			tbxMunicipio.setText(getNombreMunicipio(dato.getDptdo(),
					dato.getMunicipio()));
			tbxdirecion.setText("" + dato.getDireccion());
			tbxId.setText("" + dato.getNro_identificacion());
		}
		tbxCodigo_prestador.close();
	}

	@SuppressWarnings("unchecked")
	public void selectedProcedimiento(Listitem listitem, Bandbox bandbox,
			Textbox textbox) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
		} else {
			Map<String, Object> dato = (HashMap<String, Object>) listitem
					.getValue();

			String codigo_sups = (String) dato.get("codigo_cups");
			String nombreve = (String) dato.get("descripcion");
			bandbox.setValue("" + codigo_sups);
			textbox.setValue("" + nombreve);
		}
		bandbox.close();
	}

	public void buscarProcediemnto(String value, Listbox lbx) throws Exception {
		try {
			if (anexo9Entidad != null) {
				String nro_identificacion_prestador = anexo9Entidad
						.getCodigo_prestador();

				Map<String, Object> parametersContratos = new HashMap<String, Object>();
				parametersContratos.put("codigo_empresa",
						sucursal.getCodigo_empresa());
				parametersContratos.put("codigo_sucursal",
						sucursal.getCodigo_sucursal());
				parametersContratos.put("codigo_administradora",
						nro_identificacion_prestador);
				parametersContratos.put("consulta_especializada", "S");
				parametersContratos.put("limite_paginado", "limit 25 offset 0");
				List<Map<String, Object>> map_procedimeintos = getServiceLocator()
						.getContratosService().listarProcedimientos(
								parametersContratos);

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
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void setPrestador(String codigo_prestador) {
		Prestadores prestadoresCaja = new Prestadores();
		prestadoresCaja.setCodigo_empresa(sucursal.getCodigo_empresa());
		prestadoresCaja.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		prestadoresCaja.setNro_identificacion(codigo_prestador);
		prestadoresCaja = getServiceLocator().getPrestadoresService()
				.consultar(prestadoresCaja);

		if (prestadoresCaja == null) {
			tbxCodigo_prestador.setValue("");
			tbxNomPrestador.setValue("");
			tbxDepartamento.setText("");
			tbxMunicipio.setText("");
			tbxdirecion.setText("");
			tbxId.setText("");
		} else {
			tbxCodigo_prestador.setValue(prestadoresCaja
					.getNro_identificacion());
			tbxNomPrestador.setValue(prestadoresCaja.getNombres());
			tbxDepartamento.setText(getNombreDepartamento(""));
			tbxMunicipio.setText(getNombreMunicipio("", ""));
			tbxdirecion.setText("" + prestadoresCaja.getDireccion());
			tbxId.setText("" + prestadoresCaja.getNro_identificacion());
		}
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

	public boolean validarForm() throws Exception {
		tbxCodigo_prestador.setStyle("background-color:white");
		tbxNomPrestador.setStyle("background-color:white");

		boolean valida = true;

		// if (tbxCodigo_prestador.getText().trim().equals("")) {
		// tbxCodigo_prestador.setStyle("background-color:#F6BBBE");
		// tbxNomPrestador.setStyle("background-color:#F6BBBE");
		// valida = false;
		// }

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //
				Anexo10_entidad anexo10Entidad = new Anexo10_entidad();
				anexo10Entidad.setCodigo_empresa(empresa.getCodigo_empresa());
				anexo10Entidad
						.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				String codigo = Main.obtenerConsecutivo(getServiceLocator(),
						sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "ANEXO_10");
				String numero_solicitud = Main.obtenerNro_solicitud(
						getServiceLocator(), sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "NRO_ANEXO_10");

				if(!codigo.trim().isEmpty() && codigo.matches("[0-9]*$")){
					codigo_contra_remision = Long.parseLong(codigo); 
				}

				DecimalFormat decimalFormat = new DecimalFormat("0000000000");
				numero_solicitud = decimalFormat.format(Double
						.parseDouble(numero_solicitud));

				anexo10Entidad.setCodigo(codigo_contra_remision);
				CargardorDeDatos.cargarDatosViewEnBean(this,
						Anexo10_entidad.class, anexo10Entidad);

				anexo10Entidad.setCreacion_date(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				anexo10Entidad.setUltimo_update(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				anexo10Entidad
						.setCreacion_user(usuarios.getCodigo().toString());
				anexo10Entidad.setUltimo_user(usuarios.getCodigo().toString());
				anexo10Entidad.setCodigo_medico(usuarios.getCodigo());
				anexo10Entidad.setCodigo_remision(anexo9Entidad.getCodigo());
				anexo10Entidad.setNombre_responsable(tbxNombre_responsable
						.getValue());
				anexo10Entidad.setApellido_responsable(tbxApellido_responsable
						.getValue());
				anexo10Entidad.setNro_id_responsable(tbxNro_id_responsable
						.getValue());
				anexo10Entidad
						.setDir_responsable(tbxDir_responsable.getValue());
				anexo10Entidad
						.setTer_responsable(tbxTer_responsable.getValue());
				anexo10Entidad.setDep_responsable(lbxDep_responsable
						.getSelectedItem().getValue().toString());
				anexo10Entidad.setMun_responsable(lbxMun_responsable
						.getSelectedItem().getValue().toString());
				anexo10Entidad.setCel_responsable("");
				anexo10Entidad.setTipo_id_responsable(lbxTipo_id_responsable
						.getSelectedItem().getValue().toString());
				anexo10Entidad.setLeida("N");
				anexo10Entidad.setCodigo_servicio_contarefiere(tbxServico1
						.getValue());
				anexo10Entidad.setServicio_contrarefiere(tbxNomServicio1
						.getValue());

				getServiceLocator().getAnexo10EntidadService().crear(
						anexo10Entidad);

				Main.actualizarConsecutivo(getServiceLocator(),
						sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "ANEXO_10", codigo);
				Main.actualizarConsecutivo(getServiceLocator(),
						sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "NRO_ANEXO_10",
						numero_solicitud);
				deshabilitarButtom(true);
				Messagebox.show("Los datos se guardaron satisfactoriamente",
						"Informacion ..", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private String getNombreDepartamento(String codigo_dpto) {
		Departamentos departamentos = new Departamentos();
		departamentos.setCodigo(codigo_dpto);
		departamentos = getServiceLocator().getDepartamentosService()
				.consultar(departamentos);
		return departamentos != null ? departamentos.getNombre() : "";
	}

	private String getNombreMunicipio(String codigo_dpto, String codigo_mun) {
		Municipios municipios = new Municipios();
		municipios.setCoddep(codigo_dpto);
		municipios.setCodigo(codigo_mun);
		municipios = getServiceLocator().getMunicipiosService().consultar(
				municipios);
		return municipios != null ? municipios.getNombre() : "";
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formAnexo10_entidad");
		CargardorDeDatos.initComponents(this);
		listarElementoListbox(lbxTipo_id_responsable, true);
		listarDepartamentos(lbxDep_responsable, true);
		listarMunicipios(lbxMun_responsable, lbxDep_responsable);
	}

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		listboxMun.getChildren().clear();
		Listitem listitem;
		String coddep = listboxDpto.getSelectedItem().getValue().toString();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("coddep", coddep);
		List<Municipios> municipios = this.getServiceLocator()
				.getMunicipiosService().listar(parameters);

		for (Municipios mun : municipios) {
			listitem = new Listitem();
			listitem.setValue(mun.getCodigo());
			listitem.setLabel(mun.getNombre());
			listboxMun.appendChild(listitem);
		}
		if (listboxMun.getItemCount() > 0) {
			listboxMun.setSelectedIndex(0);
		}
	}

	public void imprimir() throws Exception {
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Anexo10Remision");
		paramRequest.put("codigo", codigo_contra_remision + "");
		paramRequest.put("codigo_empresa", sucursal.getCodigo_empresa());
		paramRequest.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", form, paramRequest);
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
		List<Departamentos> departamentos = this.getServiceLocator()
				.getDepartamentosService()
				.listar(new HashMap<String, Object>());

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
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = getServiceLocator().getElementoService()
				.listar(tipo);

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

	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

}
