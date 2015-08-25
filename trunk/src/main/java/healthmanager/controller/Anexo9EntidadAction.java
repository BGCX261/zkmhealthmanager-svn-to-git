package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo10_entidad;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Contrato_prestadores;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Procedimientos;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.res.BandboxObject;
import com.framework.res.CargardorDeDatos;
import com.framework.res.ListItemtObject3;
import com.framework.res.Main;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Anexo9EntidadAction extends ZKWindow {

	private static Logger log = Logger.getLogger(Anexo3_entidadAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private Window form;

	// Componentes //
	@View
	private Textbox tbxNumero_solicitud;
	@View
	private Datebox dtbxFecha;
	@View(classField = Anexo9_entidad.class, field = "codigo_prestador")
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
	@View(classField = Anexo9_entidad.class, field = "codigo_paciente")
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
	@View(classField = Anexo9_entidad.class, field = "nombre_solicita")
	private Textbox tbxNombre_solicita;
	@View(classField = Anexo9_entidad.class, field = "telefono_solicita")
	private Textbox tbxTelefono;
	@View
	private BandboxObject tbxServico1;
	@View
	private BandboxObject tbxServico2;
	@View(classField = Anexo9_entidad.class, field = "cel_solicita")
	private Textbox tbxCel;
	@View(classField = Anexo9_entidad.class, field = "informacion_clinica")
	private Textbox tbxInformacionClinica;

	@View
	Listbox lbx_Servico1;
	@View
	Listbox lbx_Servico2;

	@View
	private Textbox tbxNombre_responsable;
	@View
	private Textbox tbxApellido_responsable;
	@View
	private Textbox tbxNro_id_responsable;
	@View
	private Textbox tbxDir_responsable;
	@View
	private Textbox tbxTer_responsable;
	@View
	private Listbox lbxDep_responsable;
	@View
	private Listbox lbxMun_responsable;
	// @View private Textbox tbxCel_responsable;
	@View
	private Listbox lbxTipo_id_responsable;

	@View
	Toolbarbutton btn_nuevo;

	@View
	private Textbox tbxNomServicio1;
	@View
	private Textbox tbxNomServicio2;

	private String codigo_remsion;
	private Long codigo_contra_remision;

	private final String[] idsExcluyentes = { "lbxParameter", "tbxValue",
			"tbxAccion", "rowsResultado" };

	@View
	private Button btn_imprimir_a10;

	@View
	private Row rowPrestadorServicio;
	private Admision admision;
	private String informacion_clinica;

	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Groupbox gbxDatos_paciente;

	private void cargamosDatosDeMedico() {
		
		tbxNombre_solicita.setText("" + usuarios.getNombres() + " "
				+ usuarios.getApellidos());
		tbxTelefono.setText("" + usuarios.getTel_res());
		tbxCel.setText("" + usuarios.getCelular());
	}

	// Limpiamos los campos del formulario //
	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(this, sw, new String[] {
				"tbxValue", "lbxFormato", "northEditar" });
	}

	private void verificamosSiYaTieneHistoriaClinica() throws Exception {
		
		Map parametros = Executions.getCurrent().getArg();
//		String nro_identificacion = (String) parametros
//				.get("nro_identificacion");
//		String nro_ingreso = (String) parametros.get("nro_ingreso");
//		String tipo_hc = (String) parametros.get("tipo_hc");
		admision = (Admision) parametros.get(IVias_ingreso.ADMISION_PACIENTE);
		informacion_clinica = (String) parametros
				.get(IVias_ingreso.INFORMACION_CLINICA);
		cargamosDatosDelPaciente();

		if (admision != null) {
			if (admision.getAtendida()) {
				cargarInformacionClinicaAdmision();
				// Historia_clinica_uci historia = new Historia_clinica_uci();
				// historia.setCodigo_empresa(empresa.getCodigo_empresa());
				// historia.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				// historia.setNro_identificacion(nro_identificacion);
				// historia.setNro_ingreso(nro_ingreso);
				// historia.setTipo_hc(tipo_hc);
				// historia =
				// getServiceLocator().getHistoria_clinica_uciService()
				// .consultar(historia);
				// if (historia != null)
				// if (!(historia.getNro_historia() + "").matches("[0-9]*$"))
				// historia = null;
				// // //log.info("historia: "+historia);
				// if (historia != null) {
				// historia_clinica = historia;
				// /* verificamos si existe el anexo 9 */
				// Anexo9_entidad anexo9Entidad = new Anexo9_entidad();
				// anexo9Entidad.setCodigo_empresa(sucursal.getCodigo_empresa());
				// anexo9Entidad.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				// anexo9Entidad.setNro_historia(historia_clinica
				// .getNro_historia());
				// anexo9Entidad = getServiceLocator().getAnexo9EntidadService()
				// .consultar(anexo9Entidad);
				// //System.Out.Println("anexo 9");
				// if (anexo9Entidad != null) {
				//
				// }
				// } else {
				// deshabilitarButtom(true);
				// deshabilitarCampos(true);
				// }
			} else {
				deshabilitarButtom(true);
				deshabilitarCampos(true);
			}
		}

		if (parametros.containsKey("MOSTRAR_INFORMACION_PACIENTE")) {
			Boolean ver = (Boolean) parametros
					.get("MOSTRAR_INFORMACION_PACIENTE");
			gbxDatos_paciente.setVisible(ver);
		}

	}

	private void cargarInformacionClinicaAdmision() {
		tbxInformacionClinica.setValue(informacion_clinica);
	}

	private void deshabilitarButtom(boolean sw) throws Exception {
		//System.Out.Println("metodo desabilitar");
		Collection<Component> collection = form.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Button) {
				((Button) abstractComponent).setDisabled(sw);
			}
		}
		btn_nuevo.setDisabled(false);
	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

			getServiceLocator().getAdministradoraService().setLimit(
					"limit 25 offset 0");

			List<Map> list = getServiceLocator().getAdministradoraService()
					.listarDesdeContratos(parameters);

			lbx.getItems().clear();

			for (Map dato : list) {

				String codigo = (String) dato.get("codigo");
//				String nit = (String) dato.get("nit");
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

	private void cargamosDatosDelPaciente() {
		
		if (admision != null) {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(empresa.getCodigo_empresa());
			paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);
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
			Map dato = (HashMap) listitem.getValue();

			String codigo = (String) dato.get("codigo");
			String nit = (String) dato.get("nit");
			String nombre = (String) dato.get("nombre");
			String codigo_dpto = (String) dato.get("codigo_dpto");
			String direccion = (String) dato.get("direccion");
			String codigo_municipio = (String) dato.get("codigo_municipio");

			tbxCodigo_prestador.setValue(codigo);
			tbxNomPrestador.setValue(nombre);
			tbxDepartamento.setText(getNombreDepartamento(codigo_dpto));
			tbxMunicipio.setText(getNombreMunicipio(codigo_dpto,
					codigo_municipio));
			tbxdirecion.setText("" + direccion);
			tbxId.setText("" + nit);
		}
		tbxCodigo_prestador.close();
	}

	private void setPrestador(String codigo_prestador) {
		Administradora prestadoresCaja = new Administradora();
		prestadoresCaja.setCodigo(codigo_prestador);
		prestadoresCaja = getServiceLocator().getAdministradoraService()
				.consultar(prestadoresCaja);

		if (prestadoresCaja == null) {
			tbxCodigo_prestador.setValue("");
			tbxNomPrestador.setValue("");
			tbxDepartamento.setText("");
			tbxMunicipio.setText("");
			tbxdirecion.setText("");
			tbxId.setText("");
		} else {
			tbxCodigo_prestador.setValue(prestadoresCaja.getCodigo());
			tbxNomPrestador.setValue(prestadoresCaja.getNombre());
			tbxDepartamento.setText(getNombreDepartamento(prestadoresCaja
					.getCodigo_dpto()));
			tbxMunicipio.setText(getNombreMunicipio(
					prestadoresCaja.getCodigo_dpto(),
					prestadoresCaja.getCodigo_municipio()));
			tbxdirecion.setText("" + prestadoresCaja.getDireccion());
			tbxId.setText("" + prestadoresCaja.getNit());
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
			parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("consulta_especializada", "S");

			getServiceLocator().getContratoPrestadoresService().setLimit(
					"limit 25 offset 0");

			List<Contrato_prestadores> list = getServiceLocator()
					.getContratoPrestadoresService().listar(parameters);
			//System.Out.Println("::- Procedimientos: " + list.size());
			lbx.getItems().clear();

			for (Contrato_prestadores dato : list) {

				Procedimientos procediemientoCaja = new Procedimientos();
				procediemientoCaja.setId_procedimiento(new Long(dato
						.getCodigo_soat()));
				procediemientoCaja = getServiceLocator()
						.getProcedimientosService().consultar(
								procediemientoCaja);

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
			String nro_identificacion_prestador = tbxCodigo_prestador
					.getValue();

			Map<String, Object> parametersContratos = new HashMap<String, Object>();
			parametersContratos.put("codigo_empresa",
					sucursal.getCodigo_empresa());
			parametersContratos.put("codigo_sucursal",
					sucursal.getCodigo_sucursal());
			if (!Utilidades.noUsarPrestador(parametros_empresa, "02"))
				parametersContratos.put("codigo_administradora",
						nro_identificacion_prestador);

			/* En esta parte dependiendo del medico, cargamos los procedimientos */
			Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
					.getManuales_tarifarios(admision);
			parametersContratos.put("consulta_especializada", "S");
			parametersContratos.put("manual", manuales_tarifarios
					.getMaestro_manual().getTipo_manual());
			
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
		tbxCodigo_prestador.setStyle("background-color:white");
		tbxNomPrestador.setStyle("background-color:white");

		tbxNombre_responsable
				.setStyle("text-transform:uppercase;background-color:white");
		tbxApellido_responsable
				.setStyle("text-transform:uppercase;background-color:white");
		tbxDir_responsable
				.setStyle("text-transform:uppercase;background-color:white");
		tbxTer_responsable
				.setStyle("text-transform:uppercase;background-color:white");
		lbxDep_responsable
				.setStyle("text-transform:uppercase;background-color:white");
		lbxTipo_id_responsable
				.setStyle("text-transform:uppercase;background-color:white");
		tbxNro_id_responsable
				.setStyle("text-transform:uppercase;background-color:white");

		lbx_Servico1.setStyle("background-color:white");
		lbx_Servico2.setStyle("background-color:white");
		tbxServico1.setStyle("background-color:white");
		tbxServico2.setStyle("background-color:white");

		boolean valida = true;

		// if(lbx_Servico1.getSelectedIndex() == 0){
		// lbx_Servico1.setStyle("background-color:#F6BBBE");
		// valida = false;
		// }else
		// if(lbx_Servico1.getSelectedItem().getValue().toString().equals("04")){
		// if(tbxServico1.getValue().isEmpty()){
		// valida = false;
		// tbxServico1.setStyle("background-color:#F6BBBE");
		// }
		// }
		//
		//
		// if(lbx_Servico2.getSelectedIndex() == 0){
		// lbx_Servico2.setStyle("background-color:#F6BBBE");
		// valida = false;
		// }else
		// if(lbx_Servico2.getSelectedItem().getValue().toString().equals("04")){
		// if(tbxServico2.getValue().isEmpty()){
		// valida = false;
		// tbxServico2.setStyle("background-color:#F6BBBE");
		// }
		// }

		if (tbxNro_id_responsable.getText().trim().equals("")) {
			tbxNro_id_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (tbxNombre_responsable.getText().trim().equals("")) {
			tbxNombre_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxApellido_responsable.getText().trim().equals("")) {
			tbxApellido_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxDir_responsable.getText().trim().equals("")) {
			tbxDir_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxTer_responsable.getText().trim().equals("")) {
			tbxTer_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (lbxDep_responsable.getSelectedIndex() == 0) {
			lbxDep_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		// if(lbxMun_responsable.getSelectedIndex() == 0){
		// lbxMun_responsable.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		// valida = false;
		// }
		if (lbxTipo_id_responsable.getSelectedIndex() == 0) {
			lbxTipo_id_responsable
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (tbxCodigo_prestador.getText().trim().equals("")
				&& !Utilidades.noUsarPrestador(parametros_empresa, "02")) {
			tbxCodigo_prestador.setStyle("background-color:#F6BBBE");
			tbxNomPrestador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

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
				Anexo9_entidad anexo9Entidad = new Anexo9_entidad();
				anexo9Entidad.setCodigo_empresa(empresa.getCodigo_empresa());
				anexo9Entidad.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				String codigo = Main.obtenerConsecutivo(getServiceLocator(),
						sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "ANEXO_9");
				String numero_solicitud = Main.obtenerNro_solicitud(
						getServiceLocator(), sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "NRO_ANEXO_9");

				codigo_remsion = codigo;

				DecimalFormat decimalFormat = new DecimalFormat("0000000000");
				numero_solicitud = decimalFormat.format(Double
						.parseDouble(numero_solicitud));

				anexo9Entidad.setCodigo(codigo);
				anexo9Entidad.setFecha(new Timestamp(dtbxFecha.getValue()
						.getTime()));
				CargardorDeDatos.cargarDatosViewEnBean(this,
						Anexo9_entidad.class, anexo9Entidad);

				anexo9Entidad.setCreacion_date(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				anexo9Entidad.setUltimo_update(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				anexo9Entidad.setCreacion_user(getUsuarios().getCodigo().toString());
				anexo9Entidad.setUltimo_user(getUsuarios().getCodigo().toString());
				anexo9Entidad.setCodigo_medico(getUsuarios().getCodigo());
				anexo9Entidad.setNro_historia(-1L);
				anexo9Entidad.setNombre_responsable(tbxNombre_responsable
						.getValue());
				anexo9Entidad.setApellido_responsable(tbxApellido_responsable
						.getValue());
				anexo9Entidad.setNro_id_responsable(tbxNro_id_responsable
						.getValue());
				anexo9Entidad.setDir_responsable(tbxDir_responsable.getValue());
				anexo9Entidad.setTer_responsable(tbxTer_responsable.getValue());
				anexo9Entidad.setDep_responsable(lbxDep_responsable
						.getSelectedItem().getValue().toString());
				anexo9Entidad.setMun_responsable(lbxMun_responsable
						.getSelectedItem().getValue().toString());
				anexo9Entidad.setCel_responsable("");
				anexo9Entidad.setTipo_id_responsable(lbxTipo_id_responsable
						.getSelectedItem().getValue().toString());
				anexo9Entidad.setLeida("N");

				// ListItemtObject3 object1 = (ListItemtObject3)
				// lbx_Servico1.getSelectedItem();
				// ListItemtObject3 object2 = (ListItemtObject3)
				// lbx_Servico2.getSelectedItem();
				//
				// String codigo_servicio_solicita = tbxServico1.getValue();
				// String codigo_servicio_cual_solicita =
				// tbxServico2.getValue();
				// String nombre_servicio1 =
				// ((Elemento)object1.getObject()).getDescripcion();
				// String nombre_servicio2 =
				// ((Elemento)object2.getObject()).getDescripcion();

				/* verificamos servicios en medicamentos */
				// if(lbx_Servico1.getSelectedItem().getValue().toString().equals("04")){
				// codigo_servicio_solicita =
				// lbx_Servico1.getSelectedItem().getValue().toString();
				String codigo_servicio_solicita = tbxServico1.getValue();
				String nombre_servicio1 = tbxNomServicio1.getValue();
				String tipo1 = (String) ((Map<String, Object>) tbxServico1
						.getObject()).get("tipo");
				// }

				// if(lbx_Servico2.getSelectedItem().getValue().toString().equals("04")){
				// codigo_servicio_cual_solicita =
				// lbx_Servico2.getSelectedItem().getValue().toString();
				String codigo_servicio_cual_solicita = tbxServico2.getValue();
				String nombre_servicio2 = tbxNomServicio2.getValue();
				String tipo2 = (String) ((Map<String, Object>) tbxServico1
						.getObject()).get("tipo");
				// }
				/* fin de servicios en medicamentos */

				anexo9Entidad
						.setCodigo_servicio_solicita(codigo_servicio_solicita);
				anexo9Entidad
						.setCodigo_servicio_cual_solicita(codigo_servicio_cual_solicita);
				anexo9Entidad.setTipo_solicitud1(tipo1);
				anexo9Entidad.setServicio_solicita(nombre_servicio1);
				anexo9Entidad.setServico_cual_solicita(nombre_servicio2);
				anexo9Entidad.setTipo_solicitud2(tipo2);

				getServiceLocator().getAnexo9EntidadService().crear(
						anexo9Entidad);

				Main.actualizarConsecutivo(getServiceLocator(),
						sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "ANEXO_9", codigo);
				Main.actualizarConsecutivo(getServiceLocator(),
						sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "NRO_ANEXO_9",
						numero_solicitud);
				deshabilitarButtom(true);
				Messagebox
						.show("Los datos se guardaron satisfactoriamente",
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
		form = (Window) this.getFellow("formAnexo9_entidad");
		listarElementoListbox(lbxTipo_id_responsable, true);
		listarElementoListbox(lbx_Servico1, true);
		listarElementoListbox(lbx_Servico2, true);
		listarDepartamentos(lbxDep_responsable, true);
		listarMunicipios(lbxMun_responsable, lbxDep_responsable);
		listarParameter();
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

	public void imprimir() throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Anexo9Remision");
		paramRequest.put("codigo", codigo_remsion + "");
		paramRequest.put("codigo_empresa", sucursal.getCodigo_empresa());
		paramRequest.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", form, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void imprimirAnexo10() throws Exception {
		Map paramRequest = new HashMap();
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
		List<Elemento> elementos = getServiceLocator().getElementoService()
				.listar(tipo);

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

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		rowPrestadorServicio.setVisible(!Utilidades.noUsarPrestador(
				parametros_empresa, "02"));
	}

	@Override
	public void init() throws Exception {
		loadComponents();
		verificamosSiYaTieneHistoriaClinica();
		cargamosDatosDeMedico();
		crearNuevaReceta();
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
			parameters.put("codigo_paciente", admision.getNro_identificacion());
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getAnexo9EntidadService().setLimit(
					"limit 25 offset 0");

			List<Anexo9_entidad> lista_datos = getServiceLocator()
					.getAnexo9EntidadService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Anexo9_entidad anexo9_entidad : lista_datos) {
				rowsResultado.appendChild(crearFilas(anexo9_entidad, this));
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

		final Anexo9_entidad anexo9_entidad = (Anexo9_entidad) objeto;

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(codigo_empresa);
		administradora.setCodigo_sucursal(codigo_sucursal);
		administradora.setCodigo(anexo9_entidad.getCodigo_prestador());
		administradora = getServiceLocator().getAdministradoraService()
				.consultar(administradora);

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

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("fecha::varchar");
		listitem.setLabel("Fecha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		cargamosDatosDelPaciente();
		cargarInformacionClinicaAdmision();
		tbxServico1.setReadonly(true);
		tbxServico2.setReadonly(true);
		tbxIdentificacionPaciente.setReadonly(true);
		tbxapellido1Paciente.setReadonly(true);
		tbxapellido2paciente.setReadonly(true);
		tbxdirPaciente.setReadonly(true);
		tbxFechNacpaciente.setReadonly(true);
		tbxnombre1Paciente.setReadonly(true);
		tbxnombre2paciente.setReadonly(true);
		tbxtelpaciente.setReadonly(true);
		tbxNomServicio1.setReadonly(true);
		tbxNomServicio2.setReadonly(true);
	}

	protected void mostrarDatos(Anexo9_entidad anexo9_entidad) {
		try {
			limpiarDatos();
			deshabilitarButtom(true);
			codigo_remsion = anexo9_entidad.getCodigo();
			setPrestador(anexo9_entidad.getCodigo_prestador());
			//System.Out.Println("antes anexo 9");
			CargardorDeDatos.mostrarEnVista(this, Anexo9_entidad.class,
					anexo9_entidad);
			tbxNombre_responsable.setValue(anexo9_entidad
					.getNombre_responsable());
			tbxApellido_responsable.setValue(anexo9_entidad
					.getApellido_responsable());
			tbxNro_id_responsable.setValue(anexo9_entidad
					.getNro_id_responsable());
			tbxDir_responsable.setValue(anexo9_entidad.getDir_responsable());
			tbxTer_responsable.setValue(anexo9_entidad.getTer_responsable());
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
			tbxServico1.setValue(""
					+ anexo9_entidad.getCodigo_servicio_solicita());
			tbxServico2.setValue(""
					+ anexo9_entidad.getCodigo_servicio_cual_solicita());
			tbxNomServicio1
					.setValue("" + anexo9_entidad.getServicio_solicita());
			tbxNomServicio2.setValue(""
					+ anexo9_entidad.getServico_cual_solicita());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo", anexo9_entidad.getTipo_solicitud1());
			tbxServico1.setObject(map);
			map = new HashMap<String, Object>();
			map.put("tipo", anexo9_entidad.getTipo_solicitud2());
			tbxServico2.setObject(map);

			deshabilitarButtom(true);
			deshabilitarCampos(true);

			/* verificamos contraremision */
			Anexo10_entidad anexo10Entidad = new Anexo10_entidad();
			anexo10Entidad.setCodigo_empresa(sucursal.getCodigo_empresa());
			anexo10Entidad.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			anexo10Entidad.setCodigo_remision(anexo9_entidad.getCodigo());
			anexo10Entidad = getServiceLocator().getAnexo10EntidadService()
					.consultar(anexo10Entidad);
			if (anexo10Entidad != null) {
				codigo_contra_remision = anexo10Entidad.getCodigo();
				btn_imprimir_a10.setVisible(false);
				btn_imprimir_a10.setDisabled(false);
			}
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void cancel() {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
	}

	public void crearNuevaReceta() throws Exception {
		deshabilitarCampos(false);
		limpiarDatos();
		groupboxConsulta.setVisible(false);
		groupboxEditar.setVisible(true);
	}

}
