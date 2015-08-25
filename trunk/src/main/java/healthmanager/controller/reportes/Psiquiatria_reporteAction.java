/*
 * psiquiatriaAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Psiquiatria;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.PsiquiatriaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.UtilidadSignosVitales;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Psiquiatria_reporteAction extends HistoriaClinicaModel implements
		IHistoria_generica {

	private static Logger log = Logger.getLogger(Psiquiatria_reporteAction.class);

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
	
//	private Paciente paciente;
	private Psiquiatria psiquiatria;
//	private ESexo sexo;
//	private Timestamp fecha;
	
	// Componentes //
	private Admision admision;
//	private Citas cita;
//	private Opciones_via_ingreso opciones_via_ingreso;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	private String tipo_historia;
//	private String codigo_historia_anterior;

	@View
	private Bandbox bandboxPrestador;
	@View
	private Textbox tbxNomPrestador;

	@View
	private Radiogroup rdbDesplazamiento;
	@View
	private Radiogroup rdbDiscapacidad;
	@View
	private Label lbxMotivo;
	@View
	private Label lbxEnfermedad_actual;
	@View
	private Label lbxArea_personal;
	@View
	private Label lbxArea_familiar;
	@View
	private Label lbxExamen_mental;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	@View
	private Label lbxTratamiento;

	// Control
	@View
	private Label lbxEvolucion;
	@View
	private Label lbxDiagnostico;

	private final String[] idsExcluyentes = { "tbxAccion",
			"btnLimpiar_prestador", "tbxValue", "lbxParameter",
			"infoPacientes", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar",
			"lbxFisico_cabeza_cara_estado", "lbxFisico_ocular_estado",
			"lbxFisico_endocrinologo_estado", "lbxFisico_otorrino_estado",
			"lbxFisico_osteomuscular_estado",
			"lbxFisico_cardio_pulmonar_estado", "lbxFisico_examen_mama_estado",
			"lbxFisico_cuello_estado", "lbxFisico_vacular_estado",
			"lbxFisico_piel_fanera_estado", "lbxFisico_gastointestinal_estado",
			"lbxFisico_neurologico_estado", "lbxFisico_genitourinario_estado" };

	@View
	private Row rowMotivo;
	@View
	private Row rowAreas_ajustes;
	@View
	private Row rowPlan;
	@View
	private Row rowEvolucion;
	@View
	private Row rowDiagnostico;

//	private boolean primeraVez;

	// Signos Vitales
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

	
	// Examenes Fisicos
	@View
	private Label lbxFisico_estado_general;
	@View
	private Label lbxFisico_cabeza_cara;
	@View
	private Label lbxFisico_ocular;
	@View
	private Label lbxFisico_endocrinologo;
	@View
	private Label lbxFisico_otorrino;
	@View
	private Label lbxFisico_osteomuscular;
	@View
	private Label lbxFisico_cardio_pulmonar;
	@View
	private Row rowFisico_examen_mama;
	@View
	private Row rowFisico_examen_mama2;
	@View
	private Label lbxFisico_examen_mama;
	@View
	private Label lbxFisico_cuello;
	@View
	private Label lbxFisico_vacular;
	@View
	private Label lbxFisico_piel_fanera;
	@View
	private Label lbxFisico_gastointestinal;
	@View
	private Label lbxFisico_neurologico;
	@View
	private Label lbxFisico_genitourinario;

	@View
	private Listbox lbxFisico_cabeza_cara_estado;
	@View
	private Listbox lbxFisico_ocular_estado;
	@View
	private Listbox lbxFisico_endocrinologo_estado;
	@View
	private Listbox lbxFisico_otorrino_estado;
	@View
	private Listbox lbxFisico_osteomuscular_estado;
	@View
	private Listbox lbxFisico_cardio_pulmonar_estado;
	@View
	private Listbox lbxFisico_examen_mama_estado;
	@View
	private Listbox lbxFisico_cuello_estado;
	@View
	private Listbox lbxFisico_vacular_estado;
	@View
	private Listbox lbxFisico_piel_fanera_estado;
	@View
	private Listbox lbxFisico_gastointestinal_estado;
	@View
	private Listbox lbxFisico_neurologico_estado;
	@View
	private Listbox lbxFisico_genitourinario_estado;

//	private String valida_admision;

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
							IVias_ingreso.PSIQUIATRIA);
//					paciente = admision.getPaciente();
//					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
//					fecha = paciente.getFecha_nacimiento();
				}
			}

			listarCombos();
			
			if (id_codigo_historia != null) {
				psiquiatria = new Psiquiatria();
				psiquiatria.setCodigo_empresa(codigo_empresa);
				psiquiatria.setCodigo_sucursal(codigo_sucursal);
				psiquiatria.setCodigo_historia(id_codigo_historia);
				psiquiatria = getServiceLocator().getServicio(PsiquiatriaService.class).consultar(psiquiatria);
				if(psiquiatria!=null){
					if(psiquiatria.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DE PSIQUIATRIA\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DE PSIQUIATRIA\nHISTORIA DE CONTROL");
					}
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(psiquiatria.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					mostrarDatos(psiquiatria);
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,idsExcluyentes);
				}
			}
			
			FormularioUtil.inicializarRadiogroupsDefecto(this);
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
		
	}

	public void listarCombos() {
		Utilidades.listarElementoListbox(lbxFisico_cabeza_cara_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_ocular_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_endocrinologo_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_otorrino_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_osteomuscular_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_cardio_pulmonar_estado,
				false, getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_examen_mama_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_cuello_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_vacular_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_piel_fanera_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_gastointestinal_estado,
				false, getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_neurologico_estado, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFisico_genitourinario_estado,
				false, getServiceLocator());

	}

	
	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
		cargarPrestador();
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Psiquiatria psiquiatria = (Psiquiatria) obj;
		try {
			infoPacientes.setCodigo_historia(psiquiatria.getCodigo_historia());
			infoPacientes.setFecha_inicio(psiquiatria.getFecha_inicial());
			infoPacientes.setFecha_cierre(true, psiquiatria.getUltimo_update());

			initMostrar_datos(psiquiatria);
			cargarInformacion_paciente();
			
			Utilidades.seleccionarRadio(rdbDesplazamiento,
					psiquiatria.getDesplazamiento());
			Utilidades.seleccionarRadio(rdbDiscapacidad,
					psiquiatria.getDiscapacidad());
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(psiquiatria.getCodigo_empresa());
			prestadores.setCodigo_sucursal(psiquiatria.getCodigo_sucursal());
			prestadores
					.setNro_identificacion(psiquiatria.getCodigo_prestador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);

			bandboxPrestador.setValue(psiquiatria.getCodigo_prestador());
			tbxNomPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));

			lbxMotivo.setValue(psiquiatria.getMotivo());
			lbxEnfermedad_actual.setValue(psiquiatria.getEnfermedad_actual());
			lbxArea_personal.setValue(psiquiatria.getArea_personal());
			lbxArea_familiar.setValue(psiquiatria.getArea_familiar());
			lbxExamen_mental.setValue(psiquiatria.getExamen_mental());

			cargarImpresionDiagnostica(psiquiatria);

			lbxTratamiento.setValue(psiquiatria.getTratamiento());

			lbxEvolucion.setValue(psiquiatria.getEvolucion());
			lbxDiagnostico.setValue(psiquiatria.getDiagnostico());

			dbxCardiaca
					.setValue((psiquiatria.getCardiaca() != null && !psiquiatria
							.getCardiaca().isEmpty()) ? Double
							.parseDouble(psiquiatria.getCardiaca()) : null);
			dbxRespiratoria
					.setValue((psiquiatria.getRespiratoria() != null && !psiquiatria
							.getRespiratoria().isEmpty()) ? Double
							.parseDouble(psiquiatria.getRespiratoria()) : null);
			dbxPeso.setValue((psiquiatria.getPeso() != null && !psiquiatria
					.getPeso().isEmpty()) ? Double.parseDouble(psiquiatria
					.getPeso()) : null);
			dbxTalla.setValue((psiquiatria.getTalla() != null && !psiquiatria
					.getTalla().isEmpty()) ? Double.parseDouble(psiquiatria
					.getTalla()) : null);
			dbxPresion
					.setValue((psiquiatria.getPresion() != null && !psiquiatria
							.getPresion().isEmpty()) ? Double
							.valueOf(psiquiatria.getPresion()) : null);
			dbxPresion2
					.setValue((psiquiatria.getPresion2() != null && !psiquiatria
							.getPresion2().isEmpty()) ? Double
							.valueOf(psiquiatria.getPresion2()) : null);
			dbxImc.setValue((psiquiatria.getInd_masa() != null && !psiquiatria
					.getInd_masa().isEmpty()) ? Double.parseDouble(psiquiatria
					.getInd_masa()) : null);

			lbxFisico_estado_general.setValue(psiquiatria
					.getFisico_estado_general());
			lbxFisico_cabeza_cara.setValue(psiquiatria.getFisico_cabeza_cara());
			lbxFisico_ocular.setValue(psiquiatria.getFisico_ocular());
			lbxFisico_endocrinologo.setValue(psiquiatria
					.getFisico_endocrinologo());
			lbxFisico_otorrino.setValue(psiquiatria.getFisico_otorrino());
			lbxFisico_osteomuscular.setValue(psiquiatria
					.getFisico_osteomuscular());
			lbxFisico_cardio_pulmonar.setValue(psiquiatria
					.getFisico_cardio_pulmonar());
			lbxFisico_examen_mama.setValue(psiquiatria.getFisico_examen_mama());
			lbxFisico_cuello.setValue(psiquiatria.getFisico_cuello());
			lbxFisico_vacular.setValue(psiquiatria.getFisico_vacular());
			lbxFisico_piel_fanera.setValue(psiquiatria.getFisico_piel_fanera());
			lbxFisico_gastointestinal.setValue(psiquiatria
					.getFisico_gastointestinal());
			lbxFisico_neurologico.setValue(psiquiatria.getFisico_neurologico());
			lbxFisico_genitourinario.setValue(psiquiatria
					.getFisico_genitourinario());

			Utilidades.seleccionarListitem(lbxFisico_cabeza_cara_estado,
					psiquiatria.getFisico_cabeza_cara_estado());
			Utilidades.seleccionarListitem(lbxFisico_ocular_estado,
					psiquiatria.getFisico_ocular_estado());
			Utilidades.seleccionarListitem(lbxFisico_endocrinologo_estado,
					psiquiatria.getFisico_endocrinologo_estado());
			Utilidades.seleccionarListitem(lbxFisico_otorrino_estado,
					psiquiatria.getFisico_otorrino_estado());
			Utilidades.seleccionarListitem(lbxFisico_osteomuscular_estado,
					psiquiatria.getFisico_osteomuscular_estado());
			Utilidades.seleccionarListitem(lbxFisico_cardio_pulmonar_estado,
					psiquiatria.getFisico_cardio_pulmonar_estado());
			Utilidades.seleccionarListitem(lbxFisico_examen_mama_estado,
					psiquiatria.getFisico_examen_mama_estado());
			Utilidades.seleccionarListitem(lbxFisico_cuello_estado,
					psiquiatria.getFisico_cuello_estado());
			Utilidades.seleccionarListitem(lbxFisico_vacular_estado,
					psiquiatria.getFisico_vacular_estado());
			Utilidades.seleccionarListitem(lbxFisico_piel_fanera_estado,
					psiquiatria.getFisico_piel_fanera_estado());
			Utilidades.seleccionarListitem(lbxFisico_gastointestinal_estado,
					psiquiatria.getFisico_gastointestinal_estado());
			Utilidades.seleccionarListitem(lbxFisico_neurologico_estado,
					psiquiatria.getFisico_neurologico_estado());
			Utilidades.seleccionarListitem(lbxFisico_genitourinario_estado,
					psiquiatria.getFisico_genitourinario_estado());

			lbxFisico_cabeza_cara_estado.setDisabled(true);
			lbxFisico_ocular_estado.setDisabled(true);
			lbxFisico_endocrinologo_estado.setDisabled(true);
			lbxFisico_otorrino_estado.setDisabled(true);
			lbxFisico_osteomuscular_estado.setDisabled(true);
			lbxFisico_cardio_pulmonar_estado.setDisabled(true);
			lbxFisico_examen_mama_estado.setDisabled(true);
			lbxFisico_cuello_estado.setDisabled(true);
			lbxFisico_vacular_estado.setDisabled(true);
			lbxFisico_piel_fanera_estado.setDisabled(true);
			lbxFisico_gastointestinal_estado.setDisabled(true);
			lbxFisico_neurologico_estado.setDisabled(true);
			lbxFisico_genitourinario_estado.setDisabled(true);

//			valida_admision = psiquiatria.getNro_ingreso();
			
			tipo_historia= psiquiatria.getTipo_historia();
			inicializarVista(tipo_historia);
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
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
			System.out.println("" + listbox.getSelectedItem().getValue());

			String num = entero + "";
			for (AbstractComponent abstractComponent : abstractComponents) {

				if (listbox.getSelectedItem().getValue().equals(num)) {
					// textbox.setVisible(true);
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(false);
					}
				} else {
					// label.setVisible(false);
					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);

					}
				}
			}
		} catch (Exception e) {
			//  block
			System.out.println("Excepcion loaded");
			e.printStackTrace();
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

	@Override
	public void initHistoria() throws Exception {

	}

	@Override
	public void inicializarVista(String tipo) {
		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
			rowMotivo.setVisible(true);
			rowAreas_ajustes.setVisible(true);
			// rowExamen_mental.setVisible(true);
			rowPlan.setVisible(true);
			rowEvolucion.setVisible(false);
			rowDiagnostico.setVisible(false);
//			primeraVez = true;
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			rowMotivo.setVisible(false);
			rowAreas_ajustes.setVisible(false);
			// rowExamen_mental.setVisible(false);
			rowPlan.setVisible(false);
			rowEvolucion.setVisible(true);
			rowDiagnostico.setVisible(true);
//			primeraVez = false;
			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
		}
	}

	@Override
	public void cargarInformacion_paciente() {

		if (admision.getPaciente().getSexo().equals("M")) {
			rowFisico_examen_mama.setVisible(false);
			rowFisico_examen_mama2.setVisible(false);
		} else {
			rowFisico_examen_mama.setVisible(true);
			rowFisico_examen_mama2.setVisible(true);
		}

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

	public void calcularIMC(Doublebox dbxP, Doublebox dbxT, Doublebox dbxI) {
		try {
			UtilidadSignosVitales.onCalcularIMC(dbxP, dbxT, dbxI,
					UtilidadSignosVitales.TALLA_CENTIMETROS,
					UtilidadSignosVitales.PESO_KILOS);
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

	public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
		try {
			UtilidadSignosVitales.onCalcularTension(TA_sistolica,
					TA_diastolica);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void cargarImpresionDiagnostica(Psiquiatria psiquiatria)
			throws Exception {

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();

		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(psiquiatria
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);

		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);

	}

		
	@Override
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("SALUD MENTAL - PSIQUIATRÍA");

		return serivicio1.toString();
	}
	
	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t").append(lbxMotivo.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		if (lbxEnfermedad_actual.getValue() != null
				&& !lbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(lbxEnfermedad_actual.getValue()).append("\n");
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
	
	public void cargarSignosVitalesEnfermera(){
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setCodigo_empresa(codigo_empresa);
		enfermera_signos.setCodigo_sucursal(codigo_sucursal);
		enfermera_signos.setNro_ingreso(admision.getNro_ingreso());
		enfermera_signos.setNro_identificacion(admision.getNro_identificacion()	);
		enfermera_signos = getServiceLocator().getServicio(Enfermera_signosService.class).consultar(enfermera_signos);
		if(enfermera_signos != null){
			
			dbxCardiaca.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxRespiratoria.setValue(enfermera_signos.getFrecuencia_respiratoria());
			dbxPeso.setValue(enfermera_signos.getPeso());
			dbxTalla.setValue(enfermera_signos.getTalla());
			dbxPresion.setValue(enfermera_signos.getTa_sistolica());
			dbxPresion2.setValue(enfermera_signos.getTa_diastolica());
			dbxImc.setValue(enfermera_signos.getImc());
			
			calcularTA(dbxPresion,dbxPresion2);
			calcularFrecuenciaCardiaca(dbxCardiaca);
			calcularFrecuenciaRespiratoria(dbxRespiratoria);
			calcularIMC(dbxPeso,dbxTalla,dbxImc);
			
		}
	}


}