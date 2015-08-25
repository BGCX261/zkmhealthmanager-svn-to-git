/*
 * solicitud_tecnicoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Detalle_solicitud_tecnico;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Solicitud_tecnico;
import healthmanager.modelo.bean.Usuarios;

import com.framework.res.Funcion_getEdad;
import com.framework.res.L2HContraintDate;
import com.framework.res.LabelState;
import com.framework.res.L2HContraintDate.TypeInit;
import com.framework.util.Util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IRelacionSeleccion;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.CampoObservacionesPopupMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import healthmanager.modelo.service.GeneralExtraService;

public class Solicitud_tecnicoAction extends ZKWindow {

	// Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;

	@View private Textbox tbxNroidentificacion;
	@View private Textbox tbxTipoidentificacion;
	@View private Textbox tbxapellido1Paciente;
	@View private Textbox tbxapellido2paciente;
	@View private Textbox tbxnombre1Paciente;
	@View private Textbox tbxnombre2paciente;
	@View private Textbox tbxEdadPaciente;
	@View private Textbox tbxMesePacientes;
	@View private Textbox tbxAnios;
	@View private Datebox tbxFecha;
	@View private Textbox tbxCodigo;
	@View private Textbox tbxNomCie;
	@View private Textbox tbxResumen;
	@View private Textbox tbxTratamiento;
	@View private Textbox tbxJustificacion;
	@View private Textbox tbxNombre_solicita;
	@View private Textbox tbxTelefono;
	@View private Textbox tbxCargo;
	@View private Textbox tbxCel;
	@View private Textbox tbxRegistroMedico;
	@View private Textbox tbxTipoAfiliado;
	
	@View(isMacro = true) private BandboxRegistrosMacro bandboxPrincipal_cie;

	@View private Rows rowMedicamentos;
	@View private Grid gridMedicamentos;

	@View private Hbox hboxIMprimir;

	@View private Toolbarbutton btRevision;
	@View private Toolbarbutton btAtras;
	@View private Toolbarbutton btGuardar;
	
	@View private Row rowDx;
	
	@View private Datebox dtbxFecha_inicial;
	@View private Datebox dtbxFecha_final;

	private List<Detalle_receta_rips> listado_medicamentos;
	private List<Detalle_solicitud_tecnico> listado_medicamentos_solicitud;

	private Solicitud_tecnico solicitud_tecnico;

	private Admision admision;
	
	private IRelacionSeleccion<Solicitud_tecnico> onSeleccionar;
	private String nro_identificacion;
	
	public static final String ids[] = { "tbxNroidentificacion",
			"tbxTipoidentificacion", "tbxapellido1Paciente",
			"tbxapellido2paciente", "tbxnombre1Paciente", "btAtras",
			"tbxEdadPaciente", "tbxTipoAfiliado", "tbxNomCie", "tbxResumen",
			"tbxTratamiento", "tbxJustificacion", "tbxCargo", "tbxCel", "tbxRegistroMedico", 
			"tbxNombre_solicita", "tbxnombre2paciente", "tbxTelefono", "btRevision", "btImprimir", "lbxFormato"};

	@Override
	public void params(Map<String, Object> map) {
		admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
		nro_identificacion = (String) map.get(IConstantes.CONS_NRO_IDENTIFICACION_PACIENTE);   
		listado_medicamentos = (List<Detalle_receta_rips>) map
				.get(IConstantes.CONS_DETALLE_RECETA);
		listado_medicamentos_solicitud = new ArrayList<Detalle_solicitud_tecnico>();
		String msj = (String) map.get(IConstantes.CONS_MENSAJE);
		Solicitud_tecnico solicitud_tecnico = (Solicitud_tecnico) map.get(IConstantesAutorizaciones.CONS_SOLICITUD_TECNICA);
		listarCombos();
		//log.info("Listado medicamentos a solicitar: " + listado_medicamentos); 
		
		if(admision != null){
			cargarDatosHistoria();
			cargarDetallesReceta();
			cargamosDatosDeMedico(usuarios);
			btGuardar.setLabel("Agregar solicitud");
			if(solicitud_tecnico != null){
				mostrarDatos(solicitud_tecnico); 
				validamosnuevosmedicamentos(solicitud_tecnico.getDetalleSolicitudTecnicos()); 
			}
			bandboxPrincipal_cie.setVisible(false);
			rowDx.setVisible(false);
		}else if(map.get(IConstantes.CONS_RECETA_EXTERNA) == null){
				habilitarListadoSolicitud();
				activarAcciones();
//				cargarSolicitudes();
				deshabilitarCampos(true);
				hboxIMprimir.setVisible(true);
				bandboxPrincipal_cie.setVisible(true);
				bandboxPrincipal_cie.setButtonVisible(false); 
		}else{ // cuando sea una receta externa
			cargarDatosPaciente(nro_identificacion);
			cargarDetallesReceta();
			habilitarCamposExternos(); 
		}
		
		// esto es para mostrar msj
		if(msj != null){
			MensajesUtil.mensajeAlerta("Advertencia", msj + "");  
		}
	}

	private void habilitarCamposExternos() {
		tbxNombre_solicita.setReadonly(false);
		tbxTelefono.setReadonly(false);
		tbxCargo.setReadonly(false);
		tbxCel.setReadonly(false);
		tbxRegistroMedico.setReadonly(false);
	}
	
	
	private void parametrizarBandBox(){
		parametrizarCieBandBox();
	}
 
	private void parametrizarCieBandBox() {
		bandboxPrincipal_cie.inicializar(tbxNomCie, null); 
		bandboxPrincipal_cie.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Cie>() {
			@Override
			public void renderListitem(Listitem listitem, Cie registro) {
				listitem.appendChild(new Listcell(registro.getCodigo()));
				listitem.appendChild(new Listcell(registro.getNombre()));
				listitem.appendChild(new Listcell(registro.getSexo()));
				listitem.appendChild(new Listcell(registro.getLimite_inferior()));
				listitem.appendChild(new Listcell(registro.getLimite_superior()));
			}

			@Override
			public List<Cie> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");

				parametros.put("limite_paginado", "limit 25 offset 0");
				
				return getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class,parametros); 
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Cie registro) {
				textboxInformacion.setValue(registro.getNombre());
				bandbox.setValue(registro.getCodigo()); 
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
			}
		});
	}

	/**
	 * Este metodo me permite verificar si ahi medicamentos 
	 * que no estaban cargados
	 * @param list 
	 * */
	private void validamosnuevosmedicamentos(List<Detalle_solicitud_tecnico> list) { 
		try {
			int i = listado_medicamentos_solicitud.size();
			for (Detalle_receta_rips dtt_receta : listado_medicamentos) {
				 boolean existe = false;
				 for (Detalle_solicitud_tecnico detalle_solicitud_tecnico : list) {
					 if(detalle_solicitud_tecnico.getCodigo_medicamento().equals(dtt_receta.getCodigo_articulo())){
						 existe = true;break;
					 }
				 }
				 if(!existe){
					 rowMedicamentos.appendChild(crearFilasMedicamentos(
								convertToDetalleSolicitud(dtt_receta), i++));
				 }
			}
			
			gridMedicamentos.setMold("paging");
			gridMedicamentos.setPageSize(20);

			gridMedicamentos.applyProperties();
			gridMedicamentos.invalidate();
			gridMedicamentos.setVisible(true);
		} catch (Exception e) { 
			MensajesUtil.mensajeError(e, null, this); 
		}
	}

	private void habilitarListadoSolicitud() {
		groupboxEditar.setVisible(false);
		groupboxConsulta.setVisible(true);
	}

//	private void cargarSolicitudes() {
//		try {
//			buscarDatos();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private void activarAcciones() {
		btRevision.setVisible(true);
		btAtras.setVisible(true);
		btGuardar.setVisible(false);
	}

	public void imprimir(String formato) throws Exception {
		if (solicitud_tecnico != null) {
			String codigo = solicitud_tecnico.getCodigo();
			imprimir(codigo, formato); 
		} else {
			MensajesUtil.mensajeAlerta("INFORMACION",
					"Aun no se ha guardado la solicitud");
		}
	}
	
	
	private void imprimir(String codigo, String formato){
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "SolicitudCTC");
		paramRequest.put("codigo", codigo);
		paramRequest.put("formato", formato);

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setWidth("100%");
		window.setHeight("100%");
		window.doModal();
	}

	private void cargarDetallesReceta() {
		try {
			rowMedicamentos.getChildren().clear();
			listado_medicamentos_solicitud.clear();
			int i = 0;
			for (Detalle_receta_rips detalleRecetaRip : listado_medicamentos) {
				rowMedicamentos.appendChild(crearFilasMedicamentos(
						convertToDetalleSolicitud(detalleRecetaRip), ++i));
			}

			gridMedicamentos.setMold("paging");
			gridMedicamentos.setPageSize(20);

			gridMedicamentos.applyProperties();
			gridMedicamentos.invalidate();
			gridMedicamentos.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadDetalles(
			List<Detalle_solicitud_tecnico> detalleSolicitudTecnicos) {
		try {
			rowMedicamentos.getChildren().clear();
			listado_medicamentos_solicitud.clear();
			int i = 0;
			for (Detalle_solicitud_tecnico detalleSolicitudTecnico : detalleSolicitudTecnicos) {
				rowMedicamentos.appendChild(crearFilasMedicamentos(
						detalleSolicitudTecnico, ++i));
			}

			gridMedicamentos.setMold("paging");
			gridMedicamentos.setPageSize(20);

			gridMedicamentos.applyProperties();
			gridMedicamentos.invalidate();
			gridMedicamentos.setVisible(true);
		} catch (Exception e) {
			
		}
	}

	/**
	 * Este metodo me convierte un detalle de receta a un detalle de solicitud
	 * */
	private Detalle_solicitud_tecnico convertToDetalleSolicitud(
			Detalle_receta_rips detalleRecetaRip) {
		Detalle_solicitud_tecnico dtt_soliccitud = new Detalle_solicitud_tecnico();
		dtt_soliccitud.setCodigo_empresa(getSucursal().getCodigo_empresa());
		dtt_soliccitud.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
		dtt_soliccitud.setDia_tto(detalleRecetaRip.getTiempo_tto());
		dtt_soliccitud.setDosis_diaria(0d);
		dtt_soliccitud.setDosis_total(0d);
		dtt_soliccitud.setCodigo_medicamento(detalleRecetaRip
				.getCodigo_articulo());
		dtt_soliccitud.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		dtt_soliccitud.setCreacion_user(getUsuarios().getCodigo());
		dtt_soliccitud.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		dtt_soliccitud.setUltimo_user(getUsuarios().getCodigo());
		dtt_soliccitud.setVia_administracion(detalleRecetaRip
				.getVia_administracion());
		return dtt_soliccitud;
	}

	private void cargarDatosHistoria() {
		try {
			if (admision != null) {
				cargarDiagnostico(admision.getDiagnostico_ingreso(), tbxNomCie); 
				cargarDatosPaciente(admision.getNro_identificacion());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cargarDiagnostico(String codigoDx, Textbox textboxDx){
		Cie cie = new Cie();
		cie.setCodigo(codigoDx);
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
   
		if(cie == null){
			MensajesUtil.mensajeAlerta("Advertencia", "El diagnostico " + codigoDx + " no existe en la base de datos");
		}else{
			bandboxPrincipal_cie.seleccionarRegistro(cie, cie.getCodigo(), cie.getNombre()); 
//			bandboxPrincipal_cie.setValue(codigoDx);  
//			textboxDx.setAttribute("codigo", codigoDx);
//			textboxDx.setValue(codigoDx + " "
//						+ (cie != null ? cie.getNombre() : ""));
		}
	}

	private void cargarDatosPaciente(String nroIdentificacion) {
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(empresa.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(nroIdentificacion);
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null) {
			cargarDatosPaciente(paciente);
		}
	}

	private void cargarDatosPaciente(Paciente paciente) {
		String tipo_identificacion =  Utilidades.getDescripcionElemento(paciente.getTipo_identificacion(), "tipo_id", getServiceLocator());
		String tipo_afiliado =  Utilidades.getDescripcionElemento(paciente.getTipo_afiliado(), "tipo_afiliado", getServiceLocator());
		
		tbxapellido1Paciente.setText("" + paciente.getApellido1());
		tbxapellido2paciente.setText("" + paciente.getApellido2());
		tbxnombre1Paciente.setText("" + paciente.getNombre1());
		tbxnombre2paciente.setText("" + paciente.getNombre2());
		tbxEdadPaciente.setValue(Util.getEdadPrsentacion(paciente.getFecha_nacimiento())); 
		tbxMesePacientes.setValue(Funcion_getEdad.getMeses(paciente
				.getFecha_nacimiento()));
		tbxAnios.setValue(Funcion_getEdad.getYears(paciente
				.getFecha_nacimiento()));
		tbxFecha.setValue(Calendar.getInstance().getTime());
		tbxNroidentificacion.setValue("" + paciente.getNro_identificacion());
		tbxTipoidentificacion.setValue("(" + paciente.getTipo_identificacion() + ") " +  tipo_identificacion);
		tbxTipoAfiliado.setValue("" + tipo_afiliado);
	} 

	private void cargamosDatosDeMedico(Usuarios usuarios) {
		tbxNombre_solicita.setText("" + usuarios.getNombres() + " "
				+ usuarios.getApellidos());
		tbxTelefono.setText("" + usuarios.getTel_res());
		tbxCargo.setText(usuarios.getTipo_med() == null ? "MEDICO" : (usuarios
				.getTipo_med().equals("01") ? "MEDICO GENERAL"
				: "MEDICO ESPECIALISTA"));
		tbxCel.setText("" + usuarios.getEmail());
		tbxRegistroMedico.setText("" + usuarios.getRegistro_medico());
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

	public void listarCombos() {
		listarParameter();
		// listarElementoListbox(lbxTipoAfiliado, true);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("paciente.nro_identificacion || ' ' || paciente.apellido1 || ' ' || paciente.apellido2 || ' ' || paciente.nombre1 || ' ' || paciente.nombre2");
		listitem.setLabel("Paciente");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("solicitud.codigo");
		listitem.setLabel("código");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
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

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
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

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals(
						"lbxParameter")) {
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setSelectedIndex(0);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setText("0.00");
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setText("");
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setValue(new Date());
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setChecked(false);
			}
			if (abstractComponent instanceof Radiogroup) {
				((Radio) ((Radiogroup) abstractComponent).getFirstChild())
						.setChecked(true);
			}
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";
		try {
			if(admision != null){
				FormularioUtil.validarCamposObligatorios(tbxResumen, tbxTratamiento, tbxJustificacion);
			}else{
				FormularioUtil.validarCamposObligatorios(tbxResumen, tbxTratamiento, tbxJustificacion, bandboxPrincipal_cie);
			}
		} catch (WrongValueException e) {
			return false;
		}
		
		String result = isValidList(listado_medicamentos_solicitud);
		if (result != null) {
			valida = false;
			msj = "" + result;
		}
		if (!valida) {
			Messagebox.show(msj, usuarios.getNombres() + " recuerde que...",
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return valida;
	}

	public static String isValidList(List<Detalle_solicitud_tecnico> listMedicamentos2) {
		int i = 1;
		for (Detalle_solicitud_tecnico detalleSolicitudTecnico : listMedicamentos2) {
			if (detalleSolicitudTecnico.getDia_tto() == 0d)
				return "Los cantidad de dias que se debe tomar no puede ser igual a cero en la posicion "
						+ i;
			if (detalleSolicitudTecnico.getDosis_diaria() == 0d)
				return "La dosis diaria no puede ser igual a cero en la posicion "
						+ i;
			i++;
		}
		return null;
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
			parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("fecha_inicio", new Timestamp(dtbxFecha_inicial.getValue().getTime())); 
			parameters.put("fecha_final", new Timestamp(dtbxFecha_final.getValue().getTime())); 

			getServiceLocator().getSolicitudTecnicoService().setLimit(
					"limit 25 offset 0");

			List<Solicitud_tecnico> lista_datos = getServiceLocator()
					.getSolicitudTecnicoService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Solicitud_tecnico solicitud_tecnico : lista_datos) {
				rowsResultado.appendChild(crearFilas(solicitud_tecnico, this));
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

	public Row crearFilasMedicamentos(
			final Detalle_solicitud_tecnico detalleSolicitudTecnico, int nro)
			throws Exception {
		Row fila = new Row();

		listado_medicamentos_solicitud.add(detalleSolicitudTecnico);

		Hbox hbox = new Hbox();
		Space space = new Space();

		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(sucursal.getCodigo_empresa());
		articulo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		articulo.setCodigo_articulo(detalleSolicitudTecnico
				.getCodigo_medicamento());
		articulo = getServiceLocator().getArticuloService().consultar(articulo);

		String nameComercial = "";
		String nameGenerico = "";
		String concentracion = "";
		if (articulo != null) {
			nameGenerico = articulo.getNombre1();
			nameComercial = articulo.getNombre2();
			concentracion = articulo.getConcentracion();
		}
		String via = Utilidades.getDescripcionElemento(detalleSolicitudTecnico.getVia_administracion(), "via_administracion_receta", getServiceLocator());

		/* agregamos campos */
		final Doublebox textboxDosisDiaria = new Doublebox();
		textboxDosisDiaria.setHflex("1");

		final Doublebox textboxDiaTto = new Doublebox(detalleSolicitudTecnico.getDia_tto());
		textboxDiaTto.setHflex("1");

		final Doublebox textboxDiaTotal = new Doublebox();
		textboxDiaTotal.setHflex("1");
		textboxDiaTotal.setReadonly(true);
		/**/

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(nro + ""));
		fila.appendChild(new LabelState(nameGenerico + "", 9));
		fila.appendChild(new Label(nameComercial + ""));
		fila.appendChild(new Label(concentracion + ""));
		fila.appendChild(new Label(via + ""));
		fila.appendChild(textboxDosisDiaria);
		fila.appendChild(textboxDiaTto);
		fila.appendChild(textboxDiaTotal);

		/* eventos de los campos */
		textboxDosisDiaria.addEventListener("onBlur",
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						detalleSolicitudTecnico
								.setDosis_diaria(textboxDosisDiaria.getValue() != null ? textboxDosisDiaria
										.getValue() : 0d);
						if (textboxDosisDiaria.getValue() == null) {
							textboxDosisDiaria.setValue(0d);
						}
						cargarTotal(textboxDosisDiaria.getValue(),
								textboxDiaTto.getValue(), textboxDiaTotal);
					}
				});
		textboxDosisDiaria.setValue(detalleSolicitudTecnico.getDosis_diaria());

		textboxDiaTto.addEventListener("onBlur", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				detalleSolicitudTecnico
						.setDia_tto(textboxDiaTto.getValue() != null ? textboxDiaTto
								.getValue() : 0d);
				if (textboxDiaTto.getValue() == null) {
					textboxDiaTto.setValue(0d);
				}
				cargarTotal(textboxDosisDiaria.getValue(),
						textboxDiaTto.getValue(), textboxDiaTotal);
			}
		});
		textboxDiaTto.setValue(detalleSolicitudTecnico.getDia_tto());
		textboxDiaTotal.setValue(detalleSolicitudTecnico.getDosis_diaria()
				* detalleSolicitudTecnico.getDia_tto());

//		if (map.get(AutoZs) != null) {
//			textboxDosisDiaria.setReadonly(true);
//			textboxDiaTto.setReadonly(true);
//			textboxDiaTotal.setReadonly(true);
//		}
		/* fin de eventos */

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// mostrarDatos(solicitud_tecnico);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
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
									// eliminarDatos(solicitud_tecnico);
									// buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		// fila.appendChild(hbox);

		return fila;
	}

	private void cargarTotal(double cantidad_diaria, double tomar_diariario,
			Doublebox total) {
		total.setValue(cantidad_diaria * tomar_diariario);
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Solicitud_tecnico solicitud_tecnico = (Solicitud_tecnico) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(sucursal.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(solicitud_tecnico
				.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		
		Cie cie = new Cie();
		cie.setCodigo(solicitud_tecnico.getDiasnotico());
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(solicitud_tecnico.getNro_ingreso() + ""));
		fila.appendChild(new Label(solicitud_tecnico.getCodigo() + ""));
		fila.appendChild(new Label(solicitud_tecnico.getNro_identificacion() + (paciente != null ? paciente
				.getNombreCompleto() : "")));
		fila.appendChild(new Label(solicitud_tecnico.getDiasnotico() + " " + (cie != null ? cie.getNombre() : "")));
		
		
		
		fila.appendChild(getInformador(solicitud_tecnico.getResumen_historia())); 
		fila.appendChild(getInformador(solicitud_tecnico.getTratamiento_actual()
				+ ""));
		fila.appendChild(getInformador(solicitud_tecnico.getJustificacion() + ""));
		
//		fila.appendChild(new Label(solicitud_tecnico.getCodigo_receta() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(solicitud_tecnico);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
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
									eliminarDatos(solicitud_tecnico);
									buscarDatos();
								}
							}
						});
			}
		});
		// hbox.appendChild(space);
		// hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setTooltiptext("Imprimir");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				imprimir(solicitud_tecnico.getCodigo(),  "pdf"); 
			}
		});
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}
	
	public CampoObservacionesPopupMacro getInformador(String info){
		CampoObservacionesPopupMacro campoObservacionesPopupMacro = new CampoObservacionesPopupMacro(this);
		campoObservacionesPopupMacro.setValue(info); 
		campoObservacionesPopupMacro.setReadonly(true); 
		campoObservacionesPopupMacro.setInplace(true); 
		return campoObservacionesPopupMacro;
	}
	

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //

				/* esto no se crea desde aqui */

				final Solicitud_tecnico solicitud_tecnico = new Solicitud_tecnico();
				solicitud_tecnico.setCodigo(tbxCodigo.getValue());
				solicitud_tecnico.setNro_identificacion(tbxNroidentificacion.getValue());
				solicitud_tecnico.setNro_ingreso(admision != null  ? admision.getNro_ingreso() : "EXT"); 
				 solicitud_tecnico.setDiasnotico(bandboxPrincipal_cie.getValue());
				solicitud_tecnico.setResumen_historia(tbxResumen.getValue());
				solicitud_tecnico.setTratamiento_actual(tbxTratamiento
						.getValue());
				solicitud_tecnico.setJustificacion(tbxJustificacion.getValue());
				solicitud_tecnico.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				solicitud_tecnico.setCreacion_user(usuarios.getCodigo()
						.toString());
				solicitud_tecnico.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				solicitud_tecnico.setUltimo_user(usuarios.getCodigo()
						.toString());
				solicitud_tecnico
						.setCodigo_empresa(empresa.getCodigo_empresa());
				solicitud_tecnico.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				solicitud_tecnico.setCodigo_receta(IConstantesAutorizaciones.CODIGO_RECETA_ASISTENCIAL);  
				solicitud_tecnico
						.setDetalleSolicitudTecnicos(listado_medicamentos_solicitud);
				solicitud_tecnico.setNombre_solicita(tbxNombre_solicita.getValue());
				solicitud_tecnico.setTelefono_solicita(tbxTelefono.getValue());
				solicitud_tecnico.setCelular_solicita(tbxCel.getValue());
				solicitud_tecnico.setCargo_solicita(tbxCargo.getValue());
				solicitud_tecnico.setRegistro_medico_solicita(tbxRegistroMedico.getValue()); 
				if(onSeleccionar != null){
					onSeleccionar.onSeleccionar(solicitud_tecnico); 
					onClose();
				}
				

//				Messagebox.show("Los datos se guardaron satisfactoriamente",
//						"Informacion ..", Messagebox.OK,
//						Messagebox.INFORMATION, new EventListener<Event>() {
//
//							@Override
//							public void onEvent(Event arg0) throws Exception {
//								// if(parent instanceof Receta_ripsAction){
//								// Receta_ripsAction ripsAction =
//								// (Receta_ripsAction) parent;
//								// ripsAction.loadStateSave(recetaRips.getCodigo_receta());
//								// }else if(parent instanceof
//								// Receta_rips_internoAction){
//								// Receta_rips_internoAction ripsAction =
//								// (Receta_rips_internoAction) parent;
//								// ripsAction.setSolicitud_tecnico(solicitud_tecnico);
//								// }else{
//								// IngresoRecetasExternasAction action =
//								// (IngresoRecetasExternasAction) parent;
//								// action.disableBUtton();
//								// }
//								onClose();
//							}
//						});

			}

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	public void deshabilitarCampos(boolean sw) {
		FormularioUtil.deshabilitarComponentes(groupboxEditar, sw, ids);
	}

	public void agregarRevision() throws Exception {
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("solicitud", solicitud_tecnico);

		Component componente = Executions.createComponents(
				"/pages/revision_comite.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.doModal();
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) {
		solicitud_tecnico = (Solicitud_tecnico) obj;
		try {
			tbxCodigo.setValue(solicitud_tecnico.getCodigo());
			// tbxNro_identificacion.setValue(solicitud_tecnico.getNro_identificacion());
			// tbxDias.setValue(solicitud_tecnico.getDiasnotico());
			tbxResumen.setValue(solicitud_tecnico.getResumen_historia());
			tbxTratamiento.setValue(solicitud_tecnico.getTratamiento_actual());
			tbxJustificacion.setValue(solicitud_tecnico.getJustificacion());

			Usuarios usuariosMedico = new Usuarios();
			usuariosMedico.setCodigo_empresa(sucursal.getCodigo_empresa());
			usuariosMedico.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			usuariosMedico.setCodigo(solicitud_tecnico.getCreacion_user());
			usuariosMedico = getServiceLocator().getUsuariosService()
					.consultar(usuarios);

			if (usuariosMedico == null)
				throw new HealthmanagerException(
						"El usuario que creo esta solicitud no existe en la base de datos "
								+ solicitud_tecnico.getCreacion_user());
			cargamosDatosDeMedico(usuariosMedico);
			cargarDatosPaciente(solicitud_tecnico.getNro_identificacion());
			loadDetalles(solicitud_tecnico.getDetalleSolicitudTecnicos());
			cargarDiagnostico(solicitud_tecnico.getDiasnotico(), tbxNomCie); 

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("" + e.getMessage(), "Error", Messagebox.OK,
					Messagebox.ERROR);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Solicitud_tecnico solicitud_tecnico = (Solicitud_tecnico) obj;
		try {
			int result = getServiceLocator().getSolicitudTecnicoService()
					.eliminar(solicitud_tecnico);
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

	@Override
	public void init() {
		parametrizarBandBox();
		inicializarFechas();
	}

	public IRelacionSeleccion<Solicitud_tecnico> getOnSeleccionar() {
		return onSeleccionar;
	}

	public void setOnSeleccionar(IRelacionSeleccion<Solicitud_tecnico> onSeleccionar) {
		this.onSeleccionar = onSeleccionar;
	}
	
	private void inicializarFechas() { 
		dtbxFecha_inicial.setValue(L2HContraintDate.initFechaInHHMMSS(Calendar.getInstance().getTime(), TypeInit.Init00_00_00));
		dtbxFecha_final.setValue(L2HContraintDate.initFechaInHHMMSS(Calendar.getInstance().getTime(), TypeInit.end23_59_59));
	}
}
