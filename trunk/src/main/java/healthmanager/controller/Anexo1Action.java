/*
 * anexo1Action.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Anexo1;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.Anexo1Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
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

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Anexo1Action extends ZKWindow {

	private Anexo1Service anexo1Service;

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;
	@View
	private Button btImprimir;	
	@View
	private Intbox ibxNumero_informe;
	@View
	private Datebox dtbxFecha;
	@View
	private Radiogroup rdbTipo_inconsistencia;
	@View
	private Toolbarbutton btnLimpiarPaciente;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_paciente;
	@View
	private Radiogroup rdbCobertura;
	@View
	private Checkbox chbVariable_apellido1;
	@View
	private Checkbox chbVariable_apellido2;
	@View
	private Checkbox chbVariable_nombre1;
	@View
	private Checkbox chbVariable_nombre2;
	@View
	private Checkbox chbVariable_tipo_documento;
	@View
	private Checkbox chbVariable_nro_documento;
	@View
	private Checkbox chbVariable_fecha_nacimiento;
	@View
	private Textbox tbxNuevo_apellido1;
	@View
	private Textbox tbxNuevo_apellido2;
	@View
	private Textbox tbxNuevo_nombre1;
	@View
	private Textbox tbxNuevo_nombre2;
	@View
	private Listbox lbxNuevo_tipo_documento;
	@View
	private Textbox tbxNuevo_nro_documento;
	@View
	private Datebox dtbxNuevo_fecha_nacimiento;
	@View
	private Textbox tbxObservaciones;
	@View
	private Textbox tbxCodigo_paciente2;
	@View
	private Listbox lbxTipo_documento;
	@View
	private Textbox tbxNombre1;
	@View
	private Textbox tbxNombre2;
	@View
	private Textbox tbxApellido1;
	@View
	private Textbox tbxApellido2;
	@View
	private Datebox dtbxFecha_nacimiento;
	@View
	private Textbox tbxDireccion;
	@View
	private Listbox lbxCodigo_dpto;
	@View
	private Listbox lbxCodigo_municipio;
	@View
	private Textbox tbxTelefono;

	@View
	private Row rowInconsistencia;

	private Paciente paciente;
	private Anexo1 anexo1;
	private final String[] idsExcluyentes = {"northEditar"};

	@Override
	public void init() {
		listarCombos();
		cargarInformacionPacinente();
		parametrizarBandboxPaciente();
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxNuevo_tipo_documento, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_documento, true,
				getServiceLocator());
		Utilidades.listarDepartamentos(lbxCodigo_dpto, true,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
				getServiceLocator());
	}

	private void parametrizarBandboxPaciente() {
		tbxCodigo_paciente.inicializar(tbxCodigo_paciente2, btnLimpiarPaciente);
		tbxCodigo_paciente
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						if (centro_atencion_session != null) {
							parametros.put("codigo_centro",
									centro_atencion_session.getCodigo_centro());
						}

						parametros.put("limite_paginado", 
								"limit 25 offset 0");

						return Anexo1Action.this.getServiceLocator()
								.getPacienteService().listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro
								.getNombreCompleto());
						textboxInformacion.setWidth("210px");

						seleccionarPaciente(registro);

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbxCodigo_paciente2.setText("");
						lbxTipo_documento.setSelectedIndex(0);
						tbxNombre1.setText("");
						tbxNombre2.setText("");
						tbxApellido1.setText("");
						tbxApellido2.setText("");
						dtbxFecha_nacimiento.setText("");
						tbxDireccion.setText("");
						lbxCodigo_dpto.setSelectedIndex(0);
						lbxCodigo_municipio.setSelectedIndex(0);
						tbxTelefono.setText("");
					}
				});
	}

	private void cargarInformacionPacinente() {
		paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(tbxCodigo_paciente.getText());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null) {
			tbxCodigo_paciente.seleccionarRegistro(paciente,
					paciente.getNro_identificacion(),
					paciente.getNombreCompleto());
			seleccionarPaciente(paciente);
		}
		// tbxCodigo_paciente.setDisabled(true);
	}

	private void seleccionarPaciente(Paciente paciente) {
		this.paciente = paciente;
		tbxCodigo_paciente2.setText(paciente.getNro_identificacion());
		btnLimpiarPaciente.setVisible(true);
		Utilidades.seleccionarListitem(lbxTipo_documento,
				paciente.getTipo_identificacion());
		tbxNombre1.setText(paciente.getNombre1());
		tbxNombre2.setText(paciente.getNombre2());
		tbxApellido1.setText(paciente.getApellido1());
		tbxApellido2.setText(paciente.getApellido2());
		dtbxFecha_nacimiento.setValue(paciente.getFecha_nacimiento());
		tbxDireccion.setText(paciente.getDireccion());
		Utilidades.seleccionarListitem(lbxCodigo_dpto,
				paciente.getCodigo_dpto());
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
				getServiceLocator());
		tbxTelefono.setText(paciente.getTel_res());

		String opc_cob = paciente.getTipo_usuario();
		if (opc_cob.equals("1")) {
			rdbCobertura.setSelectedIndex(0);
		} else if (opc_cob.equals("2")
				&& paciente.getTipo_afiliado().equals("1")) {
			rdbCobertura.setSelectedIndex(1);
		} else if (opc_cob.equals("2")
				&& paciente.getTipo_afiliado().equals("2")) {
			rdbCobertura.setSelectedIndex(2);
		} else if (opc_cob.equals("3") && !paciente.getNivel_sisben().isEmpty()) {
			rdbCobertura.setSelectedIndex(3);
		} else if (opc_cob.equals("3") && paciente.getNivel_sisben().isEmpty()) {
			rdbCobertura.setSelectedIndex(4);
		} else if (opc_cob.equals("1")
				&& paciente.getTipo_afiliado().equals("3")) {
			rdbCobertura.setSelectedIndex(6);
		} else if (opc_cob.equals("5")) {
			rdbCobertura.setSelectedIndex(7);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("numero_informe");
		listitem.setLabel("Numero_informe");
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

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		ibxNumero_informe.setDisabled(false);
		dtbxFecha_nacimiento.setText("");
		lbxCodigo_dpto.setSelectedIndex(0);
		//lbxCodigo_municipio.setSelectedIndex(0);
		dtbxNuevo_fecha_nacimiento.setText("");
		dtbxFecha.setValue(new Date());
		rdbCobertura.setSelectedIndex(0);
		cargarCambiosVariable();
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
				getServiceLocator());
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		FormularioUtil.validarCamposObligatorios(ibxNumero_informe,tbxCodigo_paciente);

		String mensaje = "";
		boolean valida = true;
	
		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			anexo1Service.setLimit("limit 25 offset 0");

			List<Anexo1> lista_datos = anexo1Service.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Anexo1 anexo1 : lista_datos) {
				rowsResultado.appendChild(crearFilas(anexo1, this));
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
	
	public void imprimir() throws Exception{
		imprimir(anexo1.getCodigo());		
	}

	public void imprimir(String codigo) throws Exception{
		if(anexo1!=null && anexo1.getCodigo()!=null){
			Map<String,Object> paramRequest = new HashMap<String,Object>();
			paramRequest.put("name_report", "Anexo1");
			paramRequest.put("codigo", codigo);
			paramRequest.put("codigo_empresa", sucursal.getCodigo_empresa());
			paramRequest.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			
			Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
			final Window window = (Window)componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Anexo1 anexo1 = (Anexo1)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(anexo1.getFecha()+""));
		fila.appendChild(new Label(anexo1.getNumero_informe()+""));
		fila.appendChild(new Label(anexo1.getCodigo_paciente()+""));
	
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(anexo1);
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
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(anexo1);
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
	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				Anexo1 anexo1 = new Anexo1();
				anexo1.setCodigo_empresa(empresa.getCodigo_empresa());
				anexo1.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				// anexo1.setCodigo();
				anexo1.setNumero_informe(ibxNumero_informe.getValue().toString());
				anexo1.setFecha(new Timestamp(dtbxFecha.getValue().getTime()));
				if(paciente!=null){
					anexo1.setCodigo_administradora(paciente.getCodigo_administradora());
				}
				anexo1.setTipo_inconsistencia(rdbTipo_inconsistencia.getSelectedItem().getValue().toString());
				anexo1.setCodigo_paciente(tbxCodigo_paciente.getValue());
				anexo1.setCobertura(rdbCobertura.getSelectedItem().getValue()	.toString());
				anexo1.setVariable_apellido1(chbVariable_apellido1.isChecked() ? "S"	: "N");
				anexo1.setVariable_apellido2(chbVariable_apellido2.isChecked() ? "S"	: "N");
				anexo1.setVariable_nombre1(chbVariable_nombre1.isChecked() ? "S"	: "N");
				anexo1.setVariable_nombre2(chbVariable_nombre2.isChecked() ? "S"	: "N");
				anexo1.setVariable_tipo_documento(chbVariable_tipo_documento.isChecked() ? "S" : "N");
				anexo1.setVariable_nro_documento(chbVariable_nro_documento.isChecked() ? "S" : "N");
				anexo1.setVariable_fecha_nacimiento(chbVariable_fecha_nacimiento	.isChecked() ? "S" : "N");
				anexo1.setNuevo_apellido1(tbxNuevo_apellido1.getValue());
				anexo1.setNuevo_apellido2(tbxNuevo_apellido2.getValue());
				anexo1.setNuevo_nombre1(tbxNuevo_nombre1.getValue());
				anexo1.setNuevo_nombre2(tbxNuevo_nombre2.getValue());
				anexo1.setNuevo_tipo_documento(lbxNuevo_tipo_documento.getSelectedItem().getValue().toString());
				anexo1.setNuevo_nro_documento(tbxNuevo_nro_documento.getValue());
				if(chbVariable_fecha_nacimiento.isChecked()){
					anexo1.setNuevo_fecha_nacimiento(new Timestamp(dtbxNuevo_fecha_nacimiento.getValue().getTime()));
				}
				anexo1.setObservaciones(tbxObservaciones.getValue());
				anexo1.setNombre_reporta(usuarios.getNombres() + " " + usuarios.getApellidos());
				String rol = Utilidades.getNombreElemento(usuarios.getRol(), "rol", getServiceLocator());
				anexo1.setCargo_reporta(rol);
				anexo1.setTel_reporta(usuarios.getTel_oficina().split("\\.")[0]);
				anexo1.setCel_reporta(usuarios.getCelular().split("\\.")[0]);
				anexo1.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				anexo1.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				// anexo1.setDelete_date();
				anexo1.setCreacion_user(usuarios.getCodigo().toString());
				anexo1.setUltimo_user(usuarios.getCodigo().toString());
				// anexo1.setDelete_user();
				 anexo1.setDireccion_prestador(sucursal.getDireccion_sucursal());
				anexo1.setDepartamento_prestador(empresa.getCodigo_dpto());
				anexo1.setMunicipio_prestador(empresa.getCodigo_municipio());
				anexo1.setNombre_completo_prestador(empresa.getNombre_empresa());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					this.anexo1 = anexo1Service.crear(anexo1);
					btImprimir.setVisible(true);
					accionForm(true, "registrar");
				} else {
					int result = anexo1Service.actualizar(anexo1);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				throw new Exception(e.getMessage(), e);
			} else {
				//log.info("el compoente del error es: ===> "
					//	+ ((WrongValueException) e).getComponent().getId());
			}
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Anexo1 anexo1 = (Anexo1) obj;
		this.anexo1 = anexo1;
		try {
			ibxNumero_informe.setValue(ConvertidorDatosUtil.convertirDatot(anexo1.getNumero_informe()));
			ibxNumero_informe.setDisabled(true);
			dtbxFecha.setValue(anexo1.getFecha());
			// tbxCodigo_administradora.setValue(anexo1.getCodigo_administradora());
			Utilidades.seleccionarRadio(rdbTipo_inconsistencia,
					anexo1.getTipo_inconsistencia());
			tbxCodigo_paciente.setValue(anexo1.getCodigo_paciente());
			
			paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(tbxCodigo_paciente.getText());
			paciente = getServiceLocator().getPacienteService().consultar(paciente);
			if (paciente != null) {
				seleccionarPaciente(paciente);
			}
			
			Utilidades.seleccionarRadio(rdbCobertura, anexo1.getCobertura());
			chbVariable_apellido1.setChecked(anexo1.getVariable_apellido1()
					.equals("S") ? true : false);
			chbVariable_apellido2.setChecked(anexo1.getVariable_apellido2()
					.equals("S") ? true : false);
			chbVariable_nombre1.setChecked(anexo1.getVariable_nombre1().equals(
					"S") ? true : false);
			chbVariable_nombre2.setChecked(anexo1.getVariable_nombre2().equals(
					"S") ? true : false);
			chbVariable_tipo_documento.setChecked(anexo1
					.getVariable_tipo_documento().equals("S") ? true : false);
			chbVariable_nro_documento.setChecked(anexo1
					.getVariable_nro_documento().equals("S") ? true : false);
			chbVariable_fecha_nacimiento.setChecked(anexo1
					.getVariable_fecha_nacimiento().equals("S") ? true : false);
			tbxNuevo_apellido1.setValue(anexo1.getNuevo_apellido1());
			tbxNuevo_apellido2.setValue(anexo1.getNuevo_apellido2());
			tbxNuevo_nombre1.setValue(anexo1.getNuevo_nombre1());
			tbxNuevo_nombre2.setValue(anexo1.getNuevo_nombre2());
			Utilidades.seleccionarListitem(lbxNuevo_tipo_documento,
					anexo1.getNuevo_tipo_documento());
			tbxNuevo_nro_documento.setValue(anexo1.getNuevo_nro_documento());
			dtbxNuevo_fecha_nacimiento.setValue(anexo1
					.getNuevo_fecha_nacimiento());
			tbxObservaciones.setValue(anexo1.getObservaciones());
			cargarCambiosVariable();
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
			btImprimir.setVisible(true);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Anexo1 anexo1 = (Anexo1) obj;
		try {
			int result = anexo1Service.eliminar(anexo1);
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

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		Utilidades.listarMunicipios(listboxMun, listboxDpto,
				getServiceLocator());
	}

	public void onCambioTipoInconsistencia() {
		boolean paciente_no_existe = rdbTipo_inconsistencia.getSelectedIndex() == 1;
		tbxCodigo_paciente2.setDisabled(paciente_no_existe);
		lbxTipo_documento.setDisabled(paciente_no_existe);
		tbxNombre1.setDisabled(paciente_no_existe);
		tbxNombre2.setDisabled(paciente_no_existe);
		tbxApellido1.setDisabled(paciente_no_existe);
		tbxApellido2.setDisabled(paciente_no_existe);
		dtbxFecha_nacimiento.setDisabled(paciente_no_existe);
		tbxDireccion.setDisabled(paciente_no_existe);
		lbxCodigo_dpto.setDisabled(paciente_no_existe);
		lbxCodigo_municipio.setDisabled(paciente_no_existe);
		tbxTelefono.setDisabled(paciente_no_existe);
		tbxCodigo_paciente.setVisible(paciente_no_existe);
		tbxCodigo_paciente2.setVisible(!paciente_no_existe);
		rowInconsistencia.setVisible(paciente_no_existe);
	}

	public void cargarCambiosVariable(){
		onCambioVariable(0,chbVariable_apellido1.isChecked());
		onCambioVariable(1,chbVariable_apellido2.isChecked());
		onCambioVariable(2,chbVariable_nombre1.isChecked());
		onCambioVariable(3,chbVariable_nombre2.isChecked());
		onCambioVariable(4,chbVariable_tipo_documento.isChecked());
		onCambioVariable(5,chbVariable_nro_documento.isChecked());
		onCambioVariable(6,chbVariable_fecha_nacimiento.isChecked());
	}
	
	public void onCambioVariable(Integer opcion, Boolean habilitar) {
		switch (opcion) {
		case 0:
			tbxNuevo_apellido1.setDisabled(!habilitar);
			if(habilitar){
				tbxNuevo_apellido1.setText(tbxApellido1.getText());
			}else{
				tbxNuevo_apellido1.setText("");
			}
			break;
		case 1:
			tbxNuevo_apellido2.setDisabled(!habilitar);
			if(habilitar){
				tbxNuevo_apellido2.setText(tbxApellido2.getText());
			}else{
				tbxNuevo_apellido2.setText("");
			}
			break;
		case 2:
			tbxNuevo_nombre1.setDisabled(!habilitar);
			if(habilitar){
				tbxNuevo_nombre1.setText(tbxNombre1.getText());
			}else{
				tbxNuevo_nombre1.setText("");
			}
			break;
		case 3:
			tbxNuevo_nombre2.setDisabled(!habilitar);
			if(habilitar){
				tbxNuevo_nombre2.setText(tbxNombre2.getText());
			}else{
				tbxNuevo_nombre2.setText("");
			}
			break;
		case 4:
			lbxNuevo_tipo_documento.setDisabled(!habilitar);
			if(habilitar){
				lbxNuevo_tipo_documento.setSelectedIndex(lbxTipo_documento.getSelectedIndex());
			}else{
				lbxNuevo_tipo_documento.setSelectedIndex(0);
			}
			break;
		case 5:
			tbxNuevo_nro_documento.setDisabled(!habilitar);
			if(habilitar){
				tbxNuevo_nro_documento.setText(tbxCodigo_paciente2.getText());
			}else{
				tbxNuevo_nro_documento.setText("");
			}
			break;
		case 6:
			dtbxNuevo_fecha_nacimiento.setDisabled(!habilitar);
			if(habilitar){
				dtbxNuevo_fecha_nacimiento.setValue(dtbxFecha_nacimiento.getValue());
			}else{
				dtbxNuevo_fecha_nacimiento.setText("");
			}
			break;
		default:
			break;
		}
	}
}