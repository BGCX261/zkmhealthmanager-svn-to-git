/*
 * Hisc_servicios_amigablesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Hisc_servicios_amigables;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Hisc_servicios_amigablesService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.TannerMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Hisc_servicios_amigables_reporteAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static Logger log = Logger.getLogger(Hisc_servicios_amigables_reporteAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@View
	private Groupbox gbxContenido;
	@View
	private Image imgLogo;
	@View
	private Label lblTitulo;
	@View
	private Label lblMedicoTratante;
	@View
	private Label lblRegMedico;
	@View
	private Image imgFirma;

	// Componentes //
	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;
	@View
	private Row rowAgudeza_visual;
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;


	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;
	@View
	private Label lbxMotivo_consulta;
	@View
	private Radiogroup rdbEts;
	@View
	private Textbox tbxCual_ets;
	@View
	private Intbox ibxObstetricos_g;
	@View
	private Intbox ibxObstetricos_p;
	@View
	private Intbox ibxObstetricos_a;
	@View
	private Intbox ibxObstetricos_c;
	@View
	private Intbox ibxObstetricos_v;
	@View
	private Datebox dtbxFum;
	@View
	private Doublebox dbxCardiaca;
	@View
	private Doublebox dbxRespiratoria;
	@View
	private Doublebox dbxPeso;
	@View
	private Doublebox dbxTalla;
	@View
	private Doublebox dbxPresion;
	@View
	private Doublebox dbxPresion2;
	@View
	private Doublebox dbxImc;
	@View
	private Textbox tbxEstado_tanner;
	@View
	private Label lbxPlan_intervencion;

	private String tipo_historia;
//	private String codigo_historia_anterior;
//	private boolean cobrar_agudeza;

	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar",
			"toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2"};

	// Ajustar a Macro
	private Admision admision;
//	private Citas cita;
//	private Opciones_via_ingreso opciones_via_ingreso;


	// Campos ocultos Gineco-Obstetricos
	@View
	private Row rowObstetricos;
	@View
	private Row rowObstetricos2;

	// Campos Ocultos
	@View
	private Label lbCual_ets;
	@View
	private Datebox dtbxMenarca;
	@View
	private Datebox dtbxEsperma;
	@View
	private Radiogroup rdbCiclos_regulares;
	@View
	private Radiogroup rdbDismenorrea;
	@View
	private Radiogroup rdbFlujo_patologico;
	@View
	private Textbox tbxObservacion;
	@View
	private Radiogroup rdbConoce_corresponde;
	@View
	private Radiogroup rdbEs_confiable;
	@View
	private Row rowEstadioTanner1;
	@View
	private Row rowEstadioTanner2;

	@View(isMacro = true)
	private TannerMacro macroTanner;

	public boolean hbRealizado;
	public boolean htoRealizado;

//	private String nro_ingreso_admision;
	
//	private Paciente paciente;
	private Hisc_servicios_amigables hisc_servicios_amigables;
//	private ESexo sexo;
//	private Timestamp fecha;

	public void habilitarIntbox(Radiogroup r, Intbox t) {
		if (r.getSelectedItem().getValue().equals("1")) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	public void habilitarTexboxRadio(Radiogroup r, Textbox t) {
		if (r.getSelectedItem().getValue().equals("2")) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	public void validarcheck() {
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if (edad >= 10 && edad <= 14) {
		}
	}

	public void habilitarTexboxChecbo(Checkbox c, Textbox t) {
		if (c.isChecked()) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	public void alarmaExamenFisicoTemperatura() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() >= 80 && dbxCardiaca.getValue() <= 86)
				&& (edad >= 10 && edad <= 15)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		}
		if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() >= 60 && dbxCardiaca.getValue() <= 80)
				&& (edad > 16)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:red");
		}

	}

	public void alarmaExamenFisicoRespiracion() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if ((dbxRespiratoria.getText() != "")
				&& (dbxRespiratoria.getValue() >= 18 && dbxRespiratoria
						.getValue() <= 20) && (edad >= 10 && edad <= 15)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		}
		if ((dbxRespiratoria.getText() != "")
				&& (dbxRespiratoria.getValue() >= 16 && dbxRespiratoria
						.getValue() <= 20) && (edad > 16)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:red");
		}
	}

	public void alarmaExamenTensionSistolica() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((dbxPresion.getText() != "")
				&& (dbxPresion.getValue() == (edad + 100))
				&& (edad >= 10 && edad <= 15)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
					POSICION_ALERTA, TIEMPO_ALERTA, true);

		}
		if ((dbxPresion.getText() != "")
				&& (dbxPresion.getValue() == (edad + 100) && (edad > 16))) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		}

	}

	public void alarmaExamenTensionDiastolica() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((dbxPresion2.getText() != "")
				&& (dbxPresion2.getValue() == (((edad + 100) / 2) + 10) && (edad >= 10 && edad <= 15))) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
					POSICION_ALERTA, TIEMPO_ALERTA, true);

		}
		if ((dbxPresion2.getText() != "")
				&& (dbxPresion2.getValue() == (((edad + 100) / 2) + 10) && (edad > 16))) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		} else {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxPresion2,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
		}

	}

	public void habilitarlistbox(Listbox l, Textbox t) {
		if (l.getSelectedItem().getValue().equals("3")) {
			t.setReadonly(false);
		} else {
			t.setReadonly(true);
			t.setText("");
		}
	}

	@Override
	public void init() {
		try {
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			String codigo_historia = request.getParameter("codigo_historia");
			String nro_ingreso = request.getParameter("nro_ingreso");
			String nro_identificacion = request.getParameter("nro_identificacion");
			String codigo_empresa = request.getParameter("codigo_empresa");
			String codigo_sucursal = request.getParameter("codigo_sucursal");
			
            Long id_codigo_historia = null;
			
			if (codigo_historia != null && !codigo_historia.trim().isEmpty()
					&& codigo_historia.matches("[0-9]*$")) {
				id_codigo_historia = Long.parseLong(codigo_historia);
			}
			
			if(nro_identificacion!=null){
				admision = new Admision();
				admision.setCodigo_empresa(codigo_empresa);
				admision.setCodigo_sucursal(codigo_sucursal);
				admision.setNro_identificacion(nro_identificacion);
				admision.setNro_ingreso(nro_ingreso);
				admision = getServiceLocator().getServicio(AdmisionService.class).consultar(admision);
				if(admision!=null){
					macroImpresion_diagnostica.inicializarMacro(this, admision, 
							IVias_ingreso.SERVICIOS_AMIGABLES);
//					paciente = admision.getPaciente();
//					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
//					fecha = paciente.getFecha_nacimiento();
				}
			}

			listarCombos();
			
			if (id_codigo_historia != null) {
				hisc_servicios_amigables = new Hisc_servicios_amigables();
				hisc_servicios_amigables.setCodigo_empresa(codigo_empresa);
				hisc_servicios_amigables.setCodigo_sucursal(codigo_sucursal);
				hisc_servicios_amigables.setCodigo_historia(id_codigo_historia);
				hisc_servicios_amigables = getServiceLocator().getServicio(Hisc_servicios_amigablesService.class).consultar(hisc_servicios_amigables);
				if(hisc_servicios_amigables!=null){
					if(hisc_servicios_amigables.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DE SERVICIOS AMIGABLES\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DE SERVICIOS AMIGABLES\nHISTORIA DE CONTROL");
					}
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(hisc_servicios_amigables.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					mostrarDatos(hisc_servicios_amigables);
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,idsExcluyentes);
				}
			}
			
			FormularioUtil.inicializarRadiogroupsDefecto(this);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		
	}

	public void listarCombos() {
		
	}


	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
	}

	
	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Hisc_servicios_amigables hisc_servicios_amigables = (Hisc_servicios_amigables) obj;
		try {
//			this.nro_ingreso_admision = hisc_servicios_amigables.getNro_ingreso();
			infoPacientes.setCodigo_historia(hisc_servicios_amigables.getCodigo_historia());
			infoPacientes.setFecha_inicio(hisc_servicios_amigables.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,	hisc_servicios_amigables.getUltimo_update());

			initMostrar_datos(hisc_servicios_amigables);
			cargarInformacion_paciente();

			lbxMotivo_consulta.setValue(hisc_servicios_amigables.getMotivo_consulta());

			dbxPresion.setValue(ConvertidorDatosUtil.convertirDato(hisc_servicios_amigables.getTa_sistolica()));
			dbxPresion2.setValue(ConvertidorDatosUtil.convertirDato(hisc_servicios_amigables.getTa_diastolica()));
			dbxCardiaca.setValue(ConvertidorDatosUtil.convertirDato(hisc_servicios_amigables.getFrec_cardiaca()));
			dbxRespiratoria.setValue(ConvertidorDatosUtil.convertirDato(hisc_servicios_amigables.getFrec_respiratoria()));
			dbxPeso.setValue(ConvertidorDatosUtil.convertirDato(hisc_servicios_amigables.getPeso()));
			dbxTalla.setValue(ConvertidorDatosUtil.convertirDato(hisc_servicios_amigables.getTalla()));
			dbxImc.setValue(ConvertidorDatosUtil.convertirDato(hisc_servicios_amigables.getImc()));
			
			macroTanner.cargarValorFila1(hisc_servicios_amigables.getTanner_1());
			macroTanner.cargarValorFila2(hisc_servicios_amigables.getTanner_2());
			macroTanner.cargarValorFila3(hisc_servicios_amigables.getTanner_3());
			macroTanner.cargarValorFila4(hisc_servicios_amigables.getTanner_4());
			
			lbxPlan_intervencion.setValue(hisc_servicios_amigables.getPlan_intervencion());
			
			ibxObstetricos_g
					.setValue((hisc_servicios_amigables.getGestaciones() != null && !hisc_servicios_amigables
							.getGestaciones().isEmpty()) ? Integer
							.parseInt(hisc_servicios_amigables.getGestaciones())
							: null);
			ibxObstetricos_p
					.setValue((hisc_servicios_amigables.getPartos() != null && !hisc_servicios_amigables
							.getPartos().isEmpty()) ? Integer
							.parseInt(hisc_servicios_amigables.getPartos())
							: null);
			ibxObstetricos_a
					.setValue((hisc_servicios_amigables.getAbortos() != null && !hisc_servicios_amigables
							.getAbortos().isEmpty()) ? Integer
							.parseInt(hisc_servicios_amigables.getAbortos())
							: null);
			ibxObstetricos_c
					.setValue((hisc_servicios_amigables.getCesareas() != null && !hisc_servicios_amigables
							.getCesareas().isEmpty()) ? Integer
							.parseInt(hisc_servicios_amigables.getCesareas())
							: null);
			ibxObstetricos_v
					.setValue((hisc_servicios_amigables.getNacidos_vivos() != null && !hisc_servicios_amigables
							.getNacidos_vivos().isEmpty()) ? Integer
							.parseInt(hisc_servicios_amigables.getNacidos_vivos())
							: null);
			
			if(hisc_servicios_amigables.getMenarca()!=null){
				dtbxMenarca.setValue(hisc_servicios_amigables.getMenarca());
			}
			
			if(hisc_servicios_amigables.getEsperma()!=null){
				dtbxEsperma.setValue(hisc_servicios_amigables.getEsperma());
			}
			
			Utilidades.seleccionarRadio(rdbFlujo_patologico, hisc_servicios_amigables.getFlujo_patologico());
			Utilidades.seleccionarRadio(rdbCiclos_regulares, hisc_servicios_amigables.getCiclos_regulares());
			Utilidades.seleccionarRadio(rdbDismenorrea, hisc_servicios_amigables.getDismenorrea());
			
			if(hisc_servicios_amigables.getFum()!=null){
				dtbxFum.setValue(hisc_servicios_amigables.getFum());
			}
			
			Utilidades.seleccionarRadio(rdbConoce_corresponde, hisc_servicios_amigables.getFum_no_conoce());
			Utilidades.seleccionarRadio(rdbEs_confiable, hisc_servicios_amigables.getFum_es_confiable());
			
			if (hisc_servicios_amigables.getIts().equals("1")) {
				lbCual_ets.setVisible(true);
				tbxCual_ets.setVisible(true);
				tbxCual_ets.setValue(hisc_servicios_amigables.getCual_its());
			} else {
				lbCual_ets.setVisible(false);
				tbxCual_ets.setVisible(false);
			}
			
			tbxObservacion.setValue(hisc_servicios_amigables.getObservaciones());
						
			tipo_historia = hisc_servicios_amigables.getTipo_historia();

			// Mostramos la vista //
			cargarImpresionDiagnostica(hisc_servicios_amigables);

			cargarAgudezaVisual(hisc_servicios_amigables);
			// calcularCoordenadas();
			
			inicializarVista(tipo_historia);
			

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	

	public void buscarOcupaciones(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
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

	public void seleccion_radio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			System.out.println("" + radiogroup.getSelectedItem().getValue());

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
						Utilidades.listarElementoListbox(
								((Listbox) abstractComponent), true,
								getServiceLocator());

					}

					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
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

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setText("");
					}

					if (abstractComponent instanceof Listbox) {
						((Listbox) abstractComponent).getChildren().clear();
						for (int i = 0; i < ((Listbox) abstractComponent)
								.getItemCount(); i++) {
							Listitem listitem = ((Listbox) abstractComponent)
									.getItemAtIndex(i);
							if (listitem.getValue().toString().equals("")) {
								listitem.setSelected(true);
								i = ((Listbox) abstractComponent)
										.getItemCount();
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
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check3(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked()) {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}

				}
			}
		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check2(Checkbox checkbox, Checkbox checkbox2,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked() || checkbox2.isChecked()) {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}

				}
			}
		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_check(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (checkbox.isChecked()) {
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);

					}

					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}
				}
			}
		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion(Listbox listbox, int entero,
			Component[] abstractComponents) {
		try {
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
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public void initHistoria() throws Exception {

	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {

		infoPacientes.cargarInformacion(admision, this,
				new InformacionPacienteIMG() {

					@Override
					public void ejecutarProceso() {

						
					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior) {

	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		
	}

	public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
		try {
			UtilidadSignosVitales
					.onCalcularTension(TA_sistolica, TA_diastolica);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularFrecuenciaCardiaca(Doublebox dbxFc) {
		try {
			Date fecha_nacimiento = admision.getPaciente()
					.getFecha_nacimiento();
			String genero = admision.getPaciente().getSexo();

			UtilidadSignosVitales.onMostrarAlertaFrecuenciaCardiaca(dbxFc,
					fecha_nacimiento, genero);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void calcularFrecuenciaRespiratoria(Doublebox dbxFr) {
		try {
			Date fecha_nacimiento = admision.getPaciente()
					.getFecha_nacimiento();
			String genero = admision.getPaciente().getSexo();

			UtilidadSignosVitales.onMostrarAlertaFrecuenciaRespiratoria(dbxFr,
					fecha_nacimiento, genero);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void deshabilitar_conCheck(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {

			FormularioUtil.deshabilitarComponentes_conCheckbox(checkbox,
					abstractComponents);

		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}
	
	private void cargarImpresionDiagnostica(
			Hisc_servicios_amigables hisc_servicios_amigables)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);

		impresion_diagnostica.setCodigo_historia(hisc_servicios_amigables
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);
	}
	
	private void cargarAgudezaVisual(Hisc_servicios_amigables hisc_servicios_amigables)
			throws Exception {
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual
				.setCodigo_historia(hisc_servicios_amigables.getCodigo_historia());
		agudeza_visual.setCodigo_empresa(hisc_servicios_amigables.getCodigo_empresa());
		agudeza_visual
				.setCodigo_sucursal(hisc_servicios_amigables.getCodigo_sucursal());

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultar(agudeza_visual);

		macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
	}

	public void deshabilitar_conRadio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			String valor = "S";
			FormularioUtil.deshabilitarComponentes_conRadiogroup(radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	@Override
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("PYP SERVICIOS AMIGABLES");

		return serivicio1.toString();
	}

	@Override
	public String getInformacionClinica() throws WrongValueException {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t").append(lbxMotivo_consulta.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- SIGNOS VITALES: \t");
		informacion_clinica.append("Tension arterial Sistólica: ")
				.append(dbxPresion.getValue() + (",")).append("\t")
				.append("Tension arterial Diastólica: ")
				.append(dbxPresion2.getValue() + (",")).append("\t")
				.append("Frecuencia cardiaca: ")
				.append(dbxCardiaca.getValue() + (",")).append("\n")
				.append("\t").append("Frecuencia Respiratoria: ")
				.append(dbxRespiratoria.getValue() + (",")).append("\t");
		informacion_clinica.append("Talla: ")
				.append(dbxTalla.getValue() + (",")).append("\t")
				.append("Peso: ").append(dbxPeso.getValue() + (","))
				.append("\t").append("IMC: ").append(dbxImc.getValue() + (","))
				.append("\n").append("\n");

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

		informacion_clinica.append("\n:");


		return informacion_clinica.toString();

	}

	public void validarParaclinicosRealizados() {

	}


}
