/*
 * psicologiaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Psicologia;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.PsicologiaService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.Util;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
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
import com.softcomputo.composer.constantes.IParametrosSesion;
import healthmanager.modelo.service.GeneralExtraService;

public class Psicologia_reporteAction extends HistoriaClinicaModel implements
		IHistoria_generica {

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
	private Psicologia psicologia;
//	private ESexo sexo;
//	private Timestamp fecha;
	
	// Componentes //
	// Manuel
//	private Citas cita;
	private Admision admision;
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
	private Label lbxMotivo;
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
	private Label lbxArea_personal;
	@View
	private Label lbxArea_familiar;
	@View
	private Label lbxArea_social;
	@View
	private Label lbxTratamiento;

	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;

	@View
	private Label lbxAnalisis_diagnostico;

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
	private Label lbxEvolucion;

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

//	private boolean primeraVez;

	@View
	private Label lbFecha_atencion;
	@View
	private Label lbCual_psicofarmacos;
	@View
	private Label lbFecha_hospitalizacion;

	@View
	private Label lbxEnfermedad;
	@View
	private Label lbxEducacional;
	@View
	private Textbox tbxLugar_hospitalizado;

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
							IVias_ingreso.PSICOLOGIA);
//					paciente = admision.getPaciente();
//					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
//					fecha = paciente.getFecha_nacimiento();
				}
			}

			listarCombos();
			
			if(id_codigo_historia!=null){
				psicologia = new Psicologia();
				psicologia.setCodigo_empresa(codigo_empresa);
				psicologia.setCodigo_sucursal(codigo_sucursal);
				psicologia.setCodigo_historia(id_codigo_historia);
				psicologia = getServiceLocator().getServicio(PsicologiaService.class).consultar(psicologia);
				if(psicologia!=null){
					if(psicologia.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DE PSICOLOGIA\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DE PSICOLOGIA\nHISTORIA DE CONTROL");
					}
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(psicologia.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					mostrarDatos(psicologia);
					FormularioUtil.deshabilitarComponentes(gbxContenido, true,idsExcluyentes);
				}
			}
			
			FormularioUtil.inicializarRadiogroupsDefecto(this);
		}catch (Exception e) {
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

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		rol_usuario = (String) request.getSession().getAttribute(IParametrosSesion.PARAM_ROL_USUARIO);
	}

	public void listarCombos() {
		Utilidades
				.listarElementoListbox(lbxRemitido, true, getServiceLocator());

	}

	
	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
		cargarPrestador();
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Psicologia psicologia = (Psicologia) obj;
		try {
			infoPacientes.setCodigo_historia(psicologia.getCodigo_historia());
			infoPacientes.setFecha_inicio(psicologia.getFecha_inicial());
			infoPacientes.setFecha_cierre(true, psicologia.getUltimo_update());

			initMostrar_datos(psicologia);
			cargarInformacion_paciente();
			
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

			lbxMotivo.setValue(psicologia.getMotivo());
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

			lbxArea_personal.setValue(psicologia.getArea_personal());
			lbxArea_familiar.setValue(psicologia.getArea_familiar());
			lbxArea_social.setValue(psicologia.getArea_social());

			cargarImpresionDiagnostica(psicologia);

			lbxAnalisis_diagnostico.setValue(psicologia
					.getAnalisis_diagnostico());
			lbxTratamiento.setValue(psicologia.getTratamiento());
			lbxEnfermedad.setValue(psicologia.getEnfermedad());
			lbxEducacional.setValue(psicologia.getEducacional());
			tbxLugar_hospitalizado
					.setValue(psicologia.getLugar_hospitalizado());
			lbxEvolucion.setValue(psicologia.getEvolucion());

			tipo_historia= psicologia.getTipo_historia();
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
			rowAntecedentes.setVisible(true);
			rowAreas_ajustes.setVisible(true);
			rowEvolucion.setVisible(false);
			// rowTratamiento.setVisible(true);
//			primeraVez = true;
			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
		} else {
			rowMotivo.setVisible(false);
			rowAntecedentes.setVisible(false);
			rowAreas_ajustes.setVisible(false);
			rowEvolucion.setVisible(true);
			// rowTratamiento.setVisible(false);
//			primeraVez = false;
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

		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);

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
		informacion_clinica.append("\t").append(lbxMotivo.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		if (lbxEnfermedad.getValue() != null
				&& !lbxEnfermedad.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(lbxEnfermedad.getValue()).append("\n");
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
}
