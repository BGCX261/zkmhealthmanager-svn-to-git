package healthmanager.controller.reportes;

import healthmanager.controller.HistoriaClinicaModel;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Planificacion_familiar;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Agudeza_visualService;
import healthmanager.modelo.service.Enfermera_signosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Planificacion_familiarService;
import healthmanager.modelo.service.UsuariosService;
import com.framework.util.Util;
import com.framework.util.UtilidadSignosVitales;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.Agudeza_visualMacro;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;
//import com.framework.util.ManejadorParaclinicos;

public class Planificacion_familiar_reporteAction extends HistoriaClinicaModel
		implements IHistoria_generica {

	private static Logger log = Logger
			.getLogger(Planificacion_familiar_reporteAction.class);

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

	private Paciente paciente;
	private Planificacion_familiar planificacion_familiar;
//	private ESexo sexo;
//	private Timestamp fecha;
	
	
	// Componentes //
	@View(isMacro = true)
	private Agudeza_visualMacro macroAgudeza_visual;
	@View
	private Row rowAgudeza_visual;
	@View
	private Label lbxMetodo_observaciones;
	@View
	private Div divTamizaje;

	@View
	private Div divCitologia;

	@View
	private Radiogroup rdbCitologias;

	@View
	private Textbox tbxNro_inscripcion;
	
	@View
	private Label lbxEnfermedad_actual;
	@View
	private Label lbxMotivo_consulta;
	@View
	private Radiogroup rdbLee;

	@View
	private Intbox ibxMenarquia;
	@View
	private Intbox ibxVida_marital;
	@View
	private Intbox ibxVida_obstetrica;
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
	private Intbox ibxNo_hijos;
	@View
	private Datebox dtbxUltimo_parto;
	@View
	private Radiogroup rdbLactancia_materna;
	@View
	private Radiogroup rdbLactancia_exclusiva;
	@View
	private Radiogroup rdbVida_marital_parto;
	@View
	private Datebox dtbxFum;
	@View
	private Radiogroup rdbCiclos;
	// private Textbox tbxRegular;
	// private Textbox tbxIrregular;
	@View
	private Radiogroup rdbAnticoncepcion;
	@View
	private Textbox tbxCual_anticoncepcion;
	@View
	private Datebox dtbxDesde_cuando;
	@View
	private Intbox ibxNo_parejas;
	@View
	private Radiogroup rdbPromiscuidad;
	@View
	private Label lbxComplicaciones_embarazo;
	@View
	private Datebox dtbxCitologia_cervico;
	@View
	private Label lbxResultado_citologia;

	@View
	private Textbox tbxMetodo1;
	@View
	private Textbox tbxNombre1;
	@View
	private Datebox dtbxDesde_metodo1;
	@View
	private Datebox dtbxHasta_metodo1;
	@View
	private Textbox tbxCausa1;
	@View
	private Textbox tbxMetodo2;
	@View
	private Textbox tbxNombre2;
	@View
	private Datebox dtbxDesde_metodo2;
	@View
	private Datebox dtbxHasta_metodo2;
	@View
	private Textbox tbxCausa2;
	@View
	private Textbox tbxMetodo3;

	@View
	private Textbox tbxNombre3;
	@View
	private Datebox dtbxDesde_metodo3;
	@View
	private Datebox dtbxHasta_metodo3;
	@View
	private Textbox tbxCausa3;
	@View
	private Textbox tbxMetodo4;
	@View
	private Textbox tbxNombre4;
	@View
	private Datebox dtbxDesde_metodo4;
	@View
	private Datebox dtbxHasta_metodo4;
	@View
	private Textbox tbxCausa4;
	@View
	private Textbox tbxMetodo5;
	@View
	private Textbox tbxNombre5;
	@View
	private Datebox dtbxDesde_metodo5;
	@View
	private Datebox dtbxHasta_metodo5;
	@View
	private Textbox tbxCausa5;
	@View
	private Textbox tbxMetodo6;
	@View
	private Textbox tbxNombre6;
	@View
	private Datebox dtbxDesde_metodo6;
	@View
	private Datebox dtbxHasta_metodo6;
	@View
	private Textbox tbxCausa6;

	@View
	private Radiogroup rdbFumadora;
	@View
	private Radiogroup rdbActiva;
	@View
	private Intbox ibxCigarrillos;
	@View
	private Checkbox chbHipertension;
	@View
	private Checkbox chbDiabetes;
	@View
	private Checkbox chbDispidemia;
	@View
	private Checkbox chbObesidad;
	@View
	private Checkbox chbAccidente_cerebrovascular;
	@View
	private Checkbox chbEpilepsia;
	@View
	private Checkbox chbMigrana;
	@View
	private Checkbox chbHipertiroidismo;
	@View
	private Checkbox chbHipotiroidismo;
	@View
	private Checkbox chbBocio;
	@View
	private Checkbox chbTuberculosis;
	@View
	private Checkbox chbEnf_coronaria;
	@View
	private Checkbox chbEnf_valvular;
	@View
	private Checkbox chbHepatitis;
	@View
	private Checkbox chbOtras_hepatitis;
	@View
	private Textbox tbxCual_hepatitis;
	@View
	private Checkbox chbLacteria;
	@View
	private Checkbox chbColectitis;
	@View
	private Checkbox chbColelitiasis;
	@View
	private Checkbox chbEnf_acido;
	@View
	private Checkbox chbTrombosis_venosa;
	@View
	private Checkbox chbVenas_varicosas;
	@View
	private Checkbox chbCa_mama;
	@View
	private Checkbox chbEnf_fibroquistica;
	@View
	private Checkbox chbMastopatia;
	@View
	private Checkbox chbHemorragia;
	@View
	private Checkbox chbEnf_ovarica;
	@View
	private Checkbox chbCa_cervico;
	@View
	private Checkbox chbLesion_intraepitelial;
	@View
	private Checkbox chbCa_endometrio;
	@View
	private Checkbox chbCa_ovarico;
	@View
	private Checkbox chbTricomoniasis;
	@View
	private Checkbox chbVaginosis;
	@View
	private Checkbox chbCandidiasis;
	@View
	private Checkbox chbEnf_pelvica;
	@View
	private Checkbox chbEtc;
	@View
	private Textbox tbxCual_etc;
	@View
	private Checkbox chbAnemia;
	@View
	private Textbox tbxCual_anemia;
	@View
	private Checkbox chbCoagulopatia;
	@View
	private Textbox tbxCual_coagulopatia;
	@View
	private Textbox tbxCirugia;
	@View
	private Textbox tbxDuracion_hospitalizacion;
	@View
	private Label lbxAlergias;
	@View
	private Label lbxUso_medicacion;
	@View
	private Listbox lbxDiabetes_mellitus;
	@View
	private Listbox lbxHipertension_arterial;
	@View
	private Listbox lbxCardio_mas;
	@View
	private Listbox lbxCardio_fem;
	@View
	private Listbox lbxCa_cuello_uterino;
	@View
	private Listbox lbxCa_mama2;
	@View
	private Listbox lbxCa_gastrico;
	@View
	private Listbox lbxCa_colorrectal;
	@View
	private Listbox lbxCa_prostata;
	@View
	private Checkbox chbGrisofolvina;
	@View
	private Checkbox chbAnticonvulsivante;
	@View
	private Checkbox chbRifamplilina;
	@View
	private Checkbox chbAntirenovirales;
	@View
	private Label lbxRevision_sistemas;
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
	private Radiogroup rdbSintomaticos_respiratorio;
	@View
	private Radiogroup rdbSintomaticos_piel;

	@View
	private Checkbox chbxPrueba_embarazo;
	@View
	private Checkbox chbxCitologia;
	@View
	private Checkbox chbxFrotitis;
	@View
	private Label lbxOtro;
	@View
	private Listbox lbxMetodo_adoptado;
	@View
	private Listbox lbxMetodo_adoptado_hombres;
//	private String via_ingreso = "1";
	@View
	private Radiogroup rdbMetodo_ets;
	// ------------------manuel--------------------
	private String tipo_historia;
//	private Long codigo_historia_anterior;
	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@View
	private Datebox dtbxFecha_hasta;

	// Control
	private final String[] idsExcluyentes = { "tbxValue", "lbxParameter",
			"northEditar", "toolbarbuttonPaciente_admisionado1",
			"macroImpresion_diagnostica", "toolbarbuttonPaciente_admisionado2",
			"northEditar", "formReceta", "gridOrdenes_servicio" };

	// Ajustar a Macro
//	private Citas cita;
	private Admision admision;
//	private Opciones_via_ingreso opciones_via_ingreso;
	@View
	private Label lbFecha_hasta;
	@View
	private Label lbporque;
	@View
	private Label lbCantidad;
	@View
	private Intbox ibxCantidad;
	@View
	private Label lbxporque;
	@View
	private Label lbCigarrillos;

//	private Planificacion_familiar historia_anterior;

	// Ocultar Campos Gineco-Obstetricos
	@View
	private Row rowObstetrico;
	
	// macro impresion diagnostica
	@View(isMacro = true)
	private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	
	@View
	private Listbox lbxTamizaje_cuello;

//	private String valida_admision;

//	private String nro_ingreso_admision;

//	private boolean cobrar_agudeza;
	

	public void alarmaExamenFisicoFr() {
		//log.info("Este es el método>>>>>>>>>>>>>");
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((!dbxRespiratoria.getText().isEmpty())
				&& (dbxRespiratoria.getValue() >= 16 && dbxRespiratoria
						.getValue() <= 20) && (edad >= 8 && edad < 45)) {
			//log.info("Este es el método primer if>>>>>>>");
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		} else if (edad < 8 || edad >= 45) {
			//log.info("Este es el método segundo if>>>>>>>");
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:white");
		} else if ((dbxRespiratoria.getText() != "")
				&& (dbxRespiratoria.getValue() < 16 || dbxRespiratoria
						.getValue() > 20) && (edad >= 8 && edad < 45)) {

			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxRespiratoria,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxRespiratoria
					.setStyle("text-transform:uppercase;background-color:red");
		}
	}

	public void alarmaExamenFisicoFc() {
		String POSICION_ALERTA = "end_before";
		Integer TIEMPO_ALERTA = 4000;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));

		if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() >= 60 && dbxCardiaca.getValue() <= 86)
				&& (edad >= 8 && edad < 45)) {
			String mensaje = "Normal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		} else if (edad < 8 || edad >= 45) {
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:white");
		} else if ((dbxCardiaca.getText() != "")
				&& (dbxCardiaca.getValue() < 60 || dbxCardiaca.getValue() > 86)
				&& (edad >= 8 && edad < 45)) {
			String mensaje = "Anormal";
			Clients.showNotification(mensaje,
					Clients.NOTIFICATION_TYPE_WARNING, dbxCardiaca,
					POSICION_ALERTA, TIEMPO_ALERTA, true);
			dbxCardiaca
					.setStyle("text-transform:uppercase;background-color:red");
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
							IVias_ingreso.PLANIFICACION_FAMILIAR);
					paciente = admision.getPaciente();
//					sexo = (paciente.getSexo().equals("F") ? ESexo.FEMENINO	: ESexo.MASCULINO);
//					fecha = paciente.getFecha_nacimiento();
				}
			}

			listarCombos();
			if(paciente.getSexo().equals("F")){
				rowObstetrico.setVisible(true);
			}else{
				rowObstetrico.setVisible(false);
			}
			
			if (id_codigo_historia != null) {
				planificacion_familiar = new Planificacion_familiar();
				planificacion_familiar.setCodigo_empresa(codigo_empresa);
				planificacion_familiar.setCodigo_sucursal(codigo_sucursal);
				planificacion_familiar.setCodigo_historia(id_codigo_historia);
				planificacion_familiar = getServiceLocator().getServicio(Planificacion_familiarService.class).consultar(planificacion_familiar);
				if(planificacion_familiar!=null){
					if(planificacion_familiar.getTipo_historia().equalsIgnoreCase("1")){
						lblTitulo.setValue("HISTORIA CLINICA DE PLANIFICACION FAMILIAR\nHISTORIA DE PRIMERA VEZ");
					}else{
						lblTitulo.setValue("HISTORIA CLINICA DE PLANIFICACION FAMILIAR\nHISTORIA DE CONTROL");
					}					
					
					resolucion = new Resolucion();
					resolucion.setCodigo_empresa(codigo_empresa);
					resolucion = getServiceLocator().getResolucionService().consultar(resolucion);
					imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
					
					Usuarios prestador = new Usuarios();
					prestador.setCodigo_empresa(codigo_empresa);
					prestador.setCodigo_sucursal(codigo_sucursal);
					prestador.setCodigo(planificacion_familiar.getCreacion_user());
					prestador = getServiceLocator().getServicio(UsuariosService.class).consultar(prestador);
					if(prestador!=null){
						lblRegMedico.setValue(prestador.getRegistro_medico());
						lblMedicoTratante.setValue(prestador.getNombres()+" "+prestador.getApellidos());
						if(prestador.getFirma()!=null){
							imgFirma.setContent(new AImage("firma", prestador.getFirma()));
						}
					}
					mostrarDatos(planificacion_familiar);
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
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar("ante_familiares");
		Utilidades.listarElementoListboxFromType(lbxDiabetes_mellitus, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxHipertension_arterial,
				false, elementos, true);
		Utilidades.listarElementoListbox(lbxCardio_mas, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxCardio_fem, false,
				getServiceLocator());
		Utilidades.listarElementoListboxFromType(lbxCa_cuello_uterino, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxCa_mama2, false, elementos,
				true);
		Utilidades.listarElementoListboxFromType(lbxCa_gastrico, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxCa_colorrectal, false,
				elementos, true);
		Utilidades.listarElementoListboxFromType(lbxCa_prostata, false,
				elementos, true);
		Utilidades.listarElementoListbox(lbxTamizaje_cuello, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxMetodo_adoptado, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxMetodo_adoptado_hombres, true,
				getServiceLocator());
	}

	
	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
		// manejadorParaclinicos.limpiarResultados_paraclinicos();
	}

	
	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Planificacion_familiar planificacion_familiar = (Planificacion_familiar) obj;
		try {
//			this.nro_ingreso_admision = planificacion_familiar.getNro_ingreso();
			// this.via_ingreso = planificacion_familiar.getVia_ingreso();
			infoPacientes.setCodigo_historia(planificacion_familiar
					.getCodigo_historia());
			infoPacientes.setFecha_inicio(planificacion_familiar
					.getFecha_inicial());
			infoPacientes.setFecha_cierre(true,
					planificacion_familiar.getUltimo_update());
			tbxNro_inscripcion.setValue(planificacion_familiar
					.getNro_inscripcion());

			cargarAgudezaVisual(planificacion_familiar);

			initMostrar_datos(planificacion_familiar);

			cargarInformacion_paciente();

			
			Utilidades.seleccionarRadio(rdbCitologias,
					planificacion_familiar.getCitologias());
			onCambiarCitologia();

			lbxEnfermedad_actual.setValue(planificacion_familiar
					.getEnfermedad_actual());
			lbxMotivo_consulta.setValue(planificacion_familiar
					.getMotivo_consulta());
			ibxMenarquia
					.setValue((planificacion_familiar.getMenarquia() != null && !planificacion_familiar
							.getMenarquia().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getMenarquia())
							: null);
			ibxVida_marital
					.setValue((planificacion_familiar.getVida_marital() != null && !planificacion_familiar
							.getVida_marital().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getVida_marital())
							: null);
			ibxVida_obstetrica.setValue((planificacion_familiar
					.getVida_obstetrica() != null && !planificacion_familiar
					.getVida_obstetrica().isEmpty()) ? Integer
					.parseInt(planificacion_familiar.getVida_obstetrica())
					: null);
			ibxObstetricos_g
					.setValue((planificacion_familiar.getObstetricos_g() != null && !planificacion_familiar
							.getObstetricos_g().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_g())
							: null);
			ibxObstetricos_p
					.setValue((planificacion_familiar.getObstetricos_p() != null && !planificacion_familiar
							.getObstetricos_p().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_p())
							: null);
			ibxObstetricos_a
					.setValue((planificacion_familiar.getObstetricos_a() != null && !planificacion_familiar
							.getObstetricos_a().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_a())
							: null);
			ibxObstetricos_c
					.setValue((planificacion_familiar.getObstetricos_c() != null && !planificacion_familiar
							.getObstetricos_c().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_c())
							: null);

			ibxObstetricos_v
					.setValue((planificacion_familiar.getObstetricos_v() != null && !planificacion_familiar
							.getObstetricos_v().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getObstetricos_v())
							: null);

			ibxNo_hijos
					.setValue((planificacion_familiar.getNo_hijos() != null && !planificacion_familiar
							.getNo_hijos().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getNo_hijos())
							: null);
			dtbxUltimo_parto.setValue(planificacion_familiar.getUltimo_parto());
			Utilidades.seleccionarRadio(rdbLactancia_materna,
					planificacion_familiar.getLactancia_materna());
			Utilidades.seleccionarRadio(rdbLactancia_exclusiva,
					planificacion_familiar.getLactancia_exclusiva());
			Utilidades.seleccionarRadio(rdbVida_marital_parto,
					planificacion_familiar.getVida_marital_parto());
			dtbxFum.setValue(planificacion_familiar.getFum());
			Utilidades.seleccionarRadio(rdbCiclos,
					planificacion_familiar.getCiclos());
			Utilidades.seleccionarRadio(rdbAnticoncepcion,
					planificacion_familiar.getAnticoncepcion());
			tbxCual_anticoncepcion.setValue(planificacion_familiar
					.getCual_anticoncepcion());
			dtbxDesde_cuando.setValue(planificacion_familiar.getDesde_cuando());
			ibxNo_parejas
					.setValue((planificacion_familiar.getNo_parejas() != null && !planificacion_familiar
							.getNo_parejas().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getNo_parejas())
							: null);

			Utilidades.seleccionarRadio(rdbPromiscuidad,
					planificacion_familiar.getPromiscuidad());
			lbxComplicaciones_embarazo.setValue(planificacion_familiar
					.getComplicaciones_embarazo());
			dtbxCitologia_cervico.setValue(planificacion_familiar
					.getCitologia_cervico());
			lbxResultado_citologia.setValue(planificacion_familiar
					.getResultado_citologia());
			tbxMetodo1.setValue(planificacion_familiar.getMetodo1());
			tbxNombre1.setValue(planificacion_familiar.getNombre1());
			dtbxDesde_metodo1.setValue(planificacion_familiar
					.getDesde_metodo1());
			dtbxHasta_metodo1.setValue(planificacion_familiar
					.getHasta_metodo1());
			tbxCausa1.setValue(planificacion_familiar.getCausa1());
			tbxMetodo2.setValue(planificacion_familiar.getMetodo2());
			tbxNombre2.setValue(planificacion_familiar.getNombre2());
			dtbxDesde_metodo2.setValue(planificacion_familiar
					.getDesde_metodo2());
			dtbxHasta_metodo2.setValue(planificacion_familiar
					.getHasta_metodo2());
			tbxCausa2.setValue(planificacion_familiar.getCausa2());
			tbxMetodo3.setValue(planificacion_familiar.getMetodo3());
			tbxNombre3.setValue(planificacion_familiar.getNombre3());
			dtbxDesde_metodo3.setValue(planificacion_familiar
					.getDesde_metodo3());
			dtbxHasta_metodo3.setValue(planificacion_familiar
					.getHasta_metodo3());
			tbxCausa3.setValue(planificacion_familiar.getCausa3());
			tbxMetodo4.setValue(planificacion_familiar.getMetodo4());
			tbxNombre4.setValue(planificacion_familiar.getNombre4());
			dtbxDesde_metodo4.setValue(planificacion_familiar
					.getDesde_metodo4());
			dtbxHasta_metodo4.setValue(planificacion_familiar
					.getHasta_metodo4());
			tbxCausa4.setValue(planificacion_familiar.getCausa4());
			tbxMetodo5.setValue(planificacion_familiar.getMetodo5());
			tbxNombre5.setValue(planificacion_familiar.getNombre5());
			dtbxDesde_metodo5.setValue(planificacion_familiar
					.getDesde_metodo5());
			dtbxHasta_metodo5.setValue(planificacion_familiar
					.getHasta_metodo5());
			tbxCausa5.setValue(planificacion_familiar.getCausa5());
			tbxMetodo6.setValue(planificacion_familiar.getMetodo6());
			tbxNombre6.setValue(planificacion_familiar.getNombre6());
			dtbxDesde_metodo6.setValue(planificacion_familiar
					.getDesde_metodo6());
			dtbxHasta_metodo6.setValue(planificacion_familiar
					.getHasta_metodo6());
			tbxCausa6.setValue(planificacion_familiar.getCausa6());
			Utilidades.seleccionarRadio(rdbFumadora,
					planificacion_familiar.getFumadora());

			Utilidades.seleccionarRadio(rdbActiva,
					planificacion_familiar.getActiva());
			ibxCigarrillos
					.setValue((planificacion_familiar.getCigarrillos() != null && !planificacion_familiar
							.getCigarrillos().isEmpty()) ? Integer
							.parseInt(planificacion_familiar.getCigarrillos())
							: null);
			chbHipertension
					.setChecked(planificacion_familiar.getHipertension());
			chbDiabetes.setChecked(planificacion_familiar.getDiabetes());
			chbDispidemia.setChecked(planificacion_familiar.getDispidemia());
			chbObesidad.setChecked(planificacion_familiar.getObesidad());
			chbAccidente_cerebrovascular.setChecked(planificacion_familiar
					.getAccidente_cerebrovascular());
			chbEpilepsia.setChecked(planificacion_familiar.getEpilepsia());
			chbMigrana.setChecked(planificacion_familiar.getMigrana());
			chbHipertiroidismo.setChecked(planificacion_familiar
					.getHipertiroidismo());
			chbHipotiroidismo.setChecked(planificacion_familiar
					.getHipotiroidismo());
			chbBocio.setChecked(planificacion_familiar.getBocio());
			chbTuberculosis
					.setChecked(planificacion_familiar.getTuberculosis());
			chbEnf_coronaria.setChecked(planificacion_familiar
					.getEnf_coronaria());
			chbEnf_valvular
					.setChecked(planificacion_familiar.getEnf_valvular());
			chbHepatitis.setChecked(planificacion_familiar.getHepatitis());
			chbOtras_hepatitis.setChecked(planificacion_familiar
					.getOtras_hepatitis());

			if (chbOtras_hepatitis.isChecked()) {
				tbxCual_hepatitis.setVisible(true);
				tbxCual_hepatitis.setValue(planificacion_familiar
						.getCual_hepatitis());
			} else {
				tbxCual_hepatitis.setVisible(false);

			}
			chbLacteria.setChecked(planificacion_familiar.getLacteria());
			chbColectitis.setChecked(planificacion_familiar.getColectitis());
			chbColelitiasis
					.setChecked(planificacion_familiar.getColelitiasis());
			chbEnf_acido.setChecked(planificacion_familiar.getEnf_acido());
			chbTrombosis_venosa.setChecked(planificacion_familiar
					.getTrombosis_venosa());
			chbVenas_varicosas.setChecked(planificacion_familiar
					.getVenas_varicosas());
			chbCa_mama.setChecked(planificacion_familiar.getCa_mama());
			chbEnf_fibroquistica.setChecked(planificacion_familiar
					.getEnf_fibroquistica());
			chbMastopatia.setChecked(planificacion_familiar.getMastopatia());
			chbHemorragia.setChecked(planificacion_familiar.getHemorragia());
			chbEnf_ovarica.setChecked(planificacion_familiar.getEnf_ovarica());
			chbCa_cervico.setChecked(planificacion_familiar.getCa_cervico());
			chbLesion_intraepitelial.setChecked(planificacion_familiar
					.getLesion_intraepitelial());
			chbCa_endometrio.setChecked(planificacion_familiar
					.getCa_endometrio());
			chbCa_ovarico.setChecked(planificacion_familiar.getCa_ovarico());
			chbTricomoniasis.setChecked(planificacion_familiar
					.getTricomoniasis());
			chbVaginosis.setChecked(planificacion_familiar.getVaginosis());
			chbCandidiasis.setChecked(planificacion_familiar.getCandidiasis());
			chbEnf_pelvica.setChecked(planificacion_familiar.getEnf_pelvica());
			chbEtc.setChecked(planificacion_familiar.getEtc());
			tbxCual_etc.setValue(planificacion_familiar.getCual_etc());
			chbAnemia.setChecked(planificacion_familiar.getAnemia());
			tbxCual_anemia.setValue(planificacion_familiar.getCual_anemia());
			chbCoagulopatia
					.setChecked(planificacion_familiar.getCoagulopatia());
			tbxCual_coagulopatia.setValue(planificacion_familiar
					.getCual_coagulopatia());
			tbxCirugia.setValue(planificacion_familiar.getCirugia());
			tbxDuracion_hospitalizacion.setValue(planificacion_familiar
					.getDuracion_hospitalizacion());
			lbxAlergias.setValue(planificacion_familiar.getAlergias());
			lbxUso_medicacion.setValue(planificacion_familiar
					.getUso_medicacion());

			Utilidades.seleccionarListitem(lbxDiabetes_mellitus,
					planificacion_familiar.getDiabetes_mellitus());

			Utilidades.seleccionarListitem(lbxHipertension_arterial,
					planificacion_familiar.getHipertension_arterial());

			Utilidades.seleccionarListitem(lbxCardio_mas,
					planificacion_familiar.getCardio_mas());

			Utilidades.seleccionarListitem(lbxCardio_fem,
					planificacion_familiar.getCardio_fem());

			Utilidades.seleccionarListitem(lbxCa_cuello_uterino,
					planificacion_familiar.getCa_cuello_uterino());

			Utilidades.seleccionarListitem(lbxCa_mama2,
					planificacion_familiar.getCa_mama2());

			Utilidades.seleccionarListitem(lbxCa_prostata,
					planificacion_familiar.getCa_prostata());

			Utilidades.seleccionarListitem(lbxCa_gastrico,
					planificacion_familiar.getCa_gastrico());

			Utilidades.seleccionarListitem(lbxCa_colorrectal,
					planificacion_familiar.getCa_colorrectal());

			chbGrisofolvina
					.setChecked(planificacion_familiar.getGrisofolvina());
			chbAnticonvulsivante.setChecked(planificacion_familiar
					.getAnticonvulsivante());
			chbRifamplilina
					.setChecked(planificacion_familiar.getRifamplilina());
			chbAntirenovirales.setChecked(planificacion_familiar
					.getAntirenovirales());
			lbxRevision_sistemas.setValue(planificacion_familiar
					.getRevision_sistemas());
			dbxCardiaca
					.setValue((planificacion_familiar.getCardiaca() != null && !planificacion_familiar
							.getCardiaca().isEmpty()) ? Double
							.valueOf(planificacion_familiar.getCardiaca())
							: null);
			dbxRespiratoria
					.setValue((planificacion_familiar.getRespiratoria() != null && !planificacion_familiar
							.getRespiratoria().isEmpty()) ? Double
							.valueOf(planificacion_familiar.getRespiratoria())
							: null);
			dbxPeso.setValue((planificacion_familiar.getPeso() != null && !planificacion_familiar
					.getPeso().isEmpty()) ? Double
					.valueOf(planificacion_familiar.getPeso()) : null);
			dbxTalla.setValue((planificacion_familiar.getTalla() != null && !planificacion_familiar
					.getTalla().isEmpty()) ? Double
					.valueOf(planificacion_familiar.getTalla()) : null);
			dbxPresion
					.setValue((planificacion_familiar.getPresion() != null && !planificacion_familiar
							.getPresion().isEmpty()) ? Double
							.valueOf(planificacion_familiar.getPresion())
							: null);
			dbxPresion2
					.setValue((planificacion_familiar.getPresion2() != null && !planificacion_familiar
							.getPresion2().isEmpty()) ? Double
							.valueOf(planificacion_familiar.getPresion2())
							: null);
			dbxImc.setValue((planificacion_familiar.getInd_masa() != null && !planificacion_familiar
					.getInd_masa().isEmpty()) ? Double
					.valueOf(planificacion_familiar.getInd_masa()) : null);

			chbxPrueba_embarazo.setChecked(Boolean
					.getBoolean(planificacion_familiar.getPrueba_embarazo()));
			chbxCitologia.setChecked(Boolean.getBoolean(planificacion_familiar
					.getCitologia()));
			chbxFrotitis.setChecked(Boolean.getBoolean(planificacion_familiar
					.getFrotis()));
			lbxOtro.setValue(planificacion_familiar.getOtro());
			Utilidades.seleccionarListitem(lbxMetodo_adoptado,
					planificacion_familiar.getMetodo_adoptado());
			Utilidades.seleccionarListitem(lbxMetodo_adoptado_hombres,
					planificacion_familiar.getMetodo_adoptado_hombres());
			Utilidades.seleccionarRadio(rdbSintomaticos_respiratorio,
					planificacion_familiar.getSintomaticos_respiratorio());
			Utilidades.seleccionarRadio(rdbSintomaticos_piel,
					planificacion_familiar.getSintomaticos_piel());

			dtbxFecha_hasta.setValue(planificacion_familiar.getFecha_hasta());

			Utilidades.seleccionarRadio(rdbMetodo_ets,
					planificacion_familiar.getMetodo_ets());
			lbxMetodo_observaciones.setValue(planificacion_familiar
					.getMetodo_observaciones());

			seleccion_tabaquismo();
			Utilidades.seleccionarListitem(lbxTamizaje_cuello,
					planificacion_familiar.getTamizaje_cuello());

			
			cargarImpresionDiagnostica(planificacion_familiar);

//			valida_admision = planificacion_familiar.getNro_ingreso();
			
			inicializarVista(tipo_historia);
			
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
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
			//  block
			System.out.println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void seleccion_radio2(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			System.out.println("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("1")) {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue(null);
						((Textbox) abstractComponent).setReadonly(false);

					}

					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setValue(null);
						((Intbox) abstractComponent).setReadonly(false);

					}
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());
						((Datebox) abstractComponent).setReadonly(false);
						((Datebox) abstractComponent).setButtonVisible(true);

					}

					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
						((Checkbox) abstractComponent).setDisabled(false);

					}

				} else {

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue(null);
						((Textbox) abstractComponent).setReadonly(true);

					}
					if (abstractComponent instanceof Intbox) {
						((Intbox) abstractComponent).setValue(null);
						((Intbox) abstractComponent).setReadonly(true);

					}

					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(null);
						((Datebox) abstractComponent).setReadonly(true);
						((Datebox) abstractComponent).setButtonVisible(false);

					}

					if (abstractComponent instanceof Checkbox) {
						((Checkbox) abstractComponent).setChecked(false);
						((Checkbox) abstractComponent).setDisabled(true);
					}
				}
			}
		} catch (Exception e) {
			//  block
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
						((Textbox) abstractComponent).setVisible(true);

					}
					// textbox.setVisible(true);
				} else {
					// label.setVisible(false);

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");
						((Textbox) abstractComponent).setReadonly(true);
						((Textbox) abstractComponent).setVisible(false);
					}
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

	public void onCambiarCitologia() {
		String value = rdbCitologias.getSelectedItem().getValue();
		if (value.equals("1")) {
			divCitologia.setVisible(true);
			divTamizaje.setVisible(false);
			lbxTamizaje_cuello.setSelectedIndex(6);
		} else {
			divCitologia.setVisible(false);
			divTamizaje.setVisible(true);
			lbxTamizaje_cuello.setSelectedIndex(0);
		}
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
		Planificacion_familiar planificacion_familiar = (Planificacion_familiar) historia_clinica;
		
//		codigo_historia_anterior = planificacion_familiar
//				.getCodigo_historia_anterior();
		tipo_historia = planificacion_familiar.getTipo_historia();

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

	public void calcularTA(Doublebox TA_sistolica, Doublebox TA_diastolica) {
		try {
			UtilidadSignosVitales.onMostrarAlertaTension(TA_sistolica,
					TA_diastolica);
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

	public void seleccion_tabaquismo() {
		if (rdbFumadora.getSelectedItem().getValue().equals("2")) {
			rdbActiva.setVisible(false);
			lbFecha_hasta.setVisible(false);
			dtbxFecha_hasta.setVisible(false);
			lbCigarrillos.setVisible(false);
			ibxCigarrillos.setVisible(false);
			ibxCigarrillos.setValue(0);
		} else {
			rdbActiva.setVisible(true);
			seleccion_activo_pasivo();
		}
	}

	public void seleccion_activo_pasivo() {
		if (rdbActiva.getSelectedItem().getValue().equals("2")) {
			lbFecha_hasta.setVisible(false);
			dtbxFecha_hasta.setVisible(false);
			lbCigarrillos.setVisible(false);
			ibxCigarrillos.setVisible(false);
			ibxCigarrillos.setValue(0);
		} else {
			lbFecha_hasta.setVisible(true);
			dtbxFecha_hasta.setVisible(true);
			lbCigarrillos.setVisible(true);
			ibxCigarrillos.setVisible(true);
			ibxCigarrillos.setValue(0);
		}
	}

	public void seleccion_metodo() {
		if (rdbMetodo_ets.getSelectedItem().getValue().equals("2")) {
			lbporque.setVisible(true);
			lbxporque.setVisible(true);
			lbCantidad.setVisible(false);
			ibxCantidad.setVisible(false);
		} else {

			lbCantidad.setVisible(true);
			ibxCantidad.setVisible(true);
			ibxCantidad.setValue(0);
			lbporque.setVisible(false);
			lbxporque.setVisible(false);

		}
	}

	private void cargarImpresionDiagnostica(
			Planificacion_familiar planificacion_familiar) throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(planificacion_familiar
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica_reportes(
				impresion_diagnostica, true);
	}

	
	private void cargarAgudezaVisual(
			Planificacion_familiar planificacion_familiar) throws Exception {
		Agudeza_visual agudeza_visual = new Agudeza_visual();
		agudeza_visual.setCodigo_historia(planificacion_familiar
				.getCodigo_historia());
		agudeza_visual.setCodigo_empresa(planificacion_familiar
				.getCodigo_empresa());
		agudeza_visual.setCodigo_sucursal(planificacion_familiar
				.getCodigo_sucursal());

		agudeza_visual = getServiceLocator().getServicio(
				Agudeza_visualService.class).consultar(agudeza_visual);

		macroAgudeza_visual.mostrarAgudezaVisual(agudeza_visual, true);
	}

	
	@Override
	public String getServicioSolicitaReferencia1() {
		StringBuffer serivicio1 = new StringBuffer();
		/* */
		serivicio1.append("PYP PLANIFICACION FAMILIAR");

		return serivicio1.toString();
	}

	@Override
	public String getInformacionClinica() {
		StringBuffer informacion_clinica = new StringBuffer();
		/* */
		informacion_clinica.append("- MOTIVO DE CONSULTA :");
		informacion_clinica.append("\t").append(lbxMotivo_consulta.getValue())
				.append("\n");

		informacion_clinica.append("\n");

		informacion_clinica.append("- ENFERMEDAD ACTUAL :");
		if (lbxEnfermedad_actual.getValue() != null
				&& !lbxEnfermedad_actual.getValue().isEmpty()) {
			informacion_clinica.append("\t")
					.append(lbxEnfermedad_actual.getValue()).append("\n");
		}

		// faltan los signos vitales para hacer el resumen

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

		return informacion_clinica.toString();
	}

	public void cargarSignosVitalesEnfermera() {
		Enfermera_signos enfermera_signos = new Enfermera_signos();
		enfermera_signos.setCodigo_empresa(codigo_empresa);
		enfermera_signos.setCodigo_sucursal(codigo_sucursal);
		enfermera_signos.setNro_ingreso(admision.getNro_ingreso());
		enfermera_signos
				.setNro_identificacion(admision.getNro_identificacion());
		enfermera_signos = getServiceLocator().getServicio(
				Enfermera_signosService.class).consultar(enfermera_signos);
		if (enfermera_signos != null) {

			dbxCardiaca.setValue(enfermera_signos.getFrecuencia_cardiaca());
			dbxRespiratoria.setValue(enfermera_signos
					.getFrecuencia_respiratoria());
			dbxPeso.setValue(enfermera_signos.getPeso());
			dbxTalla.setValue(enfermera_signos.getTalla());
			dbxPresion.setValue(enfermera_signos.getTa_sistolica());
			dbxPresion2.setValue(enfermera_signos.getTa_diastolica());
			dbxImc.setValue(enfermera_signos.getImc());

			calcularTA(dbxPresion, dbxPresion2);
			calcularFrecuenciaCardiaca(dbxCardiaca);
			calcularFrecuenciaRespiratoria(dbxRespiratoria);
			calcularIMC(dbxPeso, dbxTalla, dbxImc);

		}
	}
	
	/*
	public void imprimir() throws Exception {
		String nro_historia = infoPacientes.getCodigo_historia();
		if (nro_historia.equals("")) {
			Messagebox.show("La historia no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Historia_pyp_planificacion_familiar");
		paramRequest.put("nro_historia", nro_historia);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		paramRequest.put("sexo", admision.getPaciente().getSexo());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}
	*/

}
