/*
 * his_atencion_embarazadaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.His_atencion_embarazada;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
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

import com.framework.locator.ServiceLocatorWeb;
import com.framework.util.Util;

public class His_atencion_embarazadaAction extends Borderlayout implements
		AfterCompose {

	private static Logger log = Logger
			.getLogger(His_atencion_embarazadaAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	private Borderlayout form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;

	private Textbox tbxCodigo_historia;
	private Datebox dtbxFecha_inicial;
	private Bandbox tbxCodigo_eps;
	private Textbox tbxNombre_eps;
	private Listbox lbxCodigo_dpto;
	private Listbox lbxCodigo_municipio;
	private Bandbox tbxIdentificacion;
	private Textbox tbxNomPaciente;
	private Textbox tbxTipoIdentificacion;
	private Datebox dbxNacimiento;
	private Textbox tbxEdad_madre;
	private Textbox tbxSexo_madre;
	private Textbox tbxMotivo;
	private Textbox tbxTelefono;
	private Radiogroup rdbSeleccion;

	private Listbox lbxGestaciones;
	private Listbox lbxPartos;
	private Listbox lbxCesarias;
	private Listbox lbxAbortos;
	private Listbox lbxEspontaneo;
	private Listbox lbxProvocado;
	private Listbox lbxNacido_muerto;
	private Listbox lbxPrematuro;
	private Listbox lbxHijos_menos;
	private Listbox lbxHijos_mayor;
	private Listbox lbxMalformado;
	private Listbox lbxHipertension;
	private Datebox dtbxFecha_ultimo_parto;
	private Listbox lbxCirugia;
	private Textbox tbxOtro_antecedente;
	private Listbox lbxHemoclasificacion;
	private Listbox lbxRh;
	private Textbox tbxPeso;
	private Textbox tbxTalla;
	private Textbox tbxImc;
	private Textbox tbxTa;
	private Textbox tbxFc;
	private Textbox tbxFr;
	private Textbox tbxTemperatura;
	private Textbox tbxCroomb;
	private Datebox dtbxFecha_ultima_mestruacion;
	private Datebox dtbxFecha_probable_parto;
	private Textbox tbxEdad_gestacional;
	private Listbox lbxControl;
	private Textbox tbxNum_control;
	private Listbox lbxFetales;
	private Listbox lbxFiebre;
	private Listbox lbxLiquido;
	private Listbox lbxFlujo;
	private Listbox lbxEnfermedad;
	private Textbox tbxCual_enfermedad;
	private Listbox lbxCigarrillo;
	private Listbox lbxAlcohol;
	private Textbox tbxCual_alcohol;
	private Listbox lbxDroga;
	private Textbox tbxCual_droga;
	private Listbox lbxViolencia;
	private Textbox tbxCual_violencia;
	private Listbox lbxToxoide;
	private Listbox lbxDosis;
	private Textbox tbxObservaciones_gestion;
	private Textbox tbxAltura_uterina;
	private Checkbox chbCorelacion;
	private Checkbox chbEmbarazo_multiple;
	private Checkbox chbTrasmision_sexual;
	private Radiogroup rdbAnomalia;
	private Radiogroup rdbEdema_gestion;
	private Radiogroup rdbPalidez;
	private Radiogroup rdbConvulciones;
	private Radiogroup rdbConciencia;
	private Radiogroup rdbCavidad_bucal;
	private Textbox tbxHto;
	private Textbox tbxHb;
	private Textbox tbxToxoplasma;
	private Textbox tbxVdrl1;
	private Textbox tbxVdrl2;
	private Textbox tbxVih1;
	private Textbox tbxVih2;
	private Textbox tbxHepb;
	private Textbox tbxOtro;
	private Textbox tbxEcografia;
	private Radiogroup rdbClasificacion_gestion;
	private Listbox lbxContracciones;
	private Listbox lbxNum_contracciones;
	private Listbox lbxHemorragia;
	private Listbox lbxLiquido_vaginal;
	private Textbox tbxColor_liquido;
	private Listbox lbxDolor_cabeza;
	private Listbox lbxVision;
	private Listbox lbxConvulcion;
	private Textbox tbxObservaciones_parto;
	private Textbox tbxContracciones_min;
	private Textbox tbxFc_fera;
	private Textbox tbxDilatacion_cervical;
	private Radiogroup rdbPreentacion;
	private Textbox tbxOtra_presentacion;
	private Radiogroup rdbEdema;
	private Listbox lbxHemorragia_vaginal;
	private Textbox tbxHto_parto;
	private Textbox tbxHb_parto;
	private Textbox tbxHepb_parto;
	private Textbox tbxVdrl_parto;
	private Textbox tbxVih_parto;
	private Radiogroup rdbClasificacion_parto;
	private Datebox dtbxFecha_nac;
	private Textbox tbxIdentificacion_nac;
	private Listbox lbxSexo;
	private Textbox tbxPeso_nac;
	private Textbox tbxTalla_nac;
	private Textbox tbxPc_nac;
	private Textbox tbxFc_nac;
	private Textbox tbxTemper_nac;
	private Textbox tbxEdad;
	private Textbox tbxAdgar1;
	private Textbox tbxAdgar5;
	private Textbox tbxAdgar10;
	private Textbox tbxAdgar20;
	private Textbox tbxObservaciones_nac;
	private Radiogroup rdbClasificacion_nac;
	private Checkbox chbReani_prematuro;
	private Checkbox chbReani_meconio;
	private Checkbox chbReani_respiracion;
	private Checkbox chbReani_hipotonico;
	private Checkbox chbReani_apnea;
	private Checkbox chbReani_jadeo;
	private Checkbox chbReani_deficultosa;
	private Checkbox chbReani_gianosis;
	private Checkbox chbReani_bradicardia;
	private Checkbox chbReani_hipoxemia;
	private Checkbox chbReani_estimulacion;
	private Checkbox chbReani_ventilacion;
	private Checkbox chbReani_comprensiones;
	private Checkbox chbReani_intubacion;
	private Textbox tbxMedicina;
	private Radiogroup rdbClasificacion_reani;
	private Listbox lbxRuptura;
	private Listbox lbxTiempo_ruptura;
	private Textbox tbxLiquido_neo;
	private Listbox lbxFiebre_neo;
	private Listbox lbxTiempo_neo;
	private Textbox tbxCoricamniotis;
	private Radiogroup rdbIntrauterina;
	private Listbox lbxMadre20;
	private Checkbox chbAlcohol_neo;
	private Checkbox chbDrogas_neo;
	private Checkbox chbCigarrillo_neo;
	private Radiogroup rdbRespiracion_neo;
	private Radiogroup rdbLlanto_neo;
	private Radiogroup rdbVetalidad_neo;
	private Checkbox chbTaquicardia_neo;
	private Checkbox chbBradicardia_neo;
	private Checkbox chbPalidez_neo;
	private Checkbox chbCianosis_neo;
	private Listbox lbxAnomalias_congenitas;
	private Textbox tbxCual_anomalias;
	private Textbox tbxLesiones;
	private Textbox tbxOtras_alter;
	private Radiogroup rdbClasificacion_neo;
	private Textbox tbxAlarma;
	private Textbox tbxConsulta_control;
	private Textbox tbxMedidas_preventiva;
	private Textbox tbxRecomendaciones;
	private Textbox tbxDiagnostico;
	private Textbox tbxCodigo_diagnostico;
	private Textbox tbxTratamiento;
	private Textbox tbxRecomendacion_alimentacion;
	private Textbox tbxEvolucion_servicio;

//	private String codigo_historia;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();
	}

	public void loadComponents() {
		form = (Borderlayout) this.getFellow("formHis_atencion_embarazada");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

		tbxCodigo_historia = (Textbox) form.getFellow("tbxCodigo_historia");
		dtbxFecha_inicial = (Datebox) form.getFellow("dtbxFecha_inicial");
		tbxCodigo_eps = (Bandbox) form.getFellow("tbxCodigo_eps");
		tbxNombre_eps = (Textbox) form.getFellow("tbxNombre_eps");
		lbxCodigo_dpto = (Listbox) form.getFellow("lbxCodigo_dpto");
		lbxCodigo_municipio = (Listbox) form.getFellow("lbxCodigo_municipio");

		tbxIdentificacion = (Bandbox) form.getFellow("tbxIdentificacion");
		tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
		tbxTipoIdentificacion = (Textbox) form
				.getFellow("tbxTipoIdentificacion");
		tbxEdad_madre = (Textbox) form.getFellow("tbxEdad_madre");
		tbxSexo_madre = (Textbox) form.getFellow("tbxSexo_madre");
		dbxNacimiento = (Datebox) form.getFellow("dbxNacimiento");
		tbxMotivo = (Textbox) form.getFellow("tbxMotivo");
		tbxTelefono = (Textbox) form.getFellow("tbxTelefono");
		rdbSeleccion = (Radiogroup) form.getFellow("rdbSeleccion");
		lbxGestaciones = (Listbox) form.getFellow("lbxGestaciones");
		lbxPartos = (Listbox) form.getFellow("lbxPartos");
		lbxCesarias = (Listbox) form.getFellow("lbxCesarias");
		lbxAbortos = (Listbox) form.getFellow("lbxAbortos");
		lbxEspontaneo = (Listbox) form.getFellow("lbxEspontaneo");
		lbxProvocado = (Listbox) form.getFellow("lbxProvocado");
		lbxNacido_muerto = (Listbox) form.getFellow("lbxNacido_muerto");
		lbxPrematuro = (Listbox) form.getFellow("lbxPrematuro");
		lbxHijos_menos = (Listbox) form.getFellow("lbxHijos_menos");
		lbxHijos_mayor = (Listbox) form.getFellow("lbxHijos_mayor");
		lbxMalformado = (Listbox) form.getFellow("lbxMalformado");
		lbxHipertension = (Listbox) form.getFellow("lbxHipertension");
		dtbxFecha_ultimo_parto = (Datebox) form
				.getFellow("dtbxFecha_ultimo_parto");
		lbxCirugia = (Listbox) form.getFellow("lbxCirugia");
		tbxOtro_antecedente = (Textbox) form.getFellow("tbxOtro_antecedente");
		lbxHemoclasificacion = (Listbox) form.getFellow("lbxHemoclasificacion");
		lbxRh = (Listbox) form.getFellow("lbxRh");
		tbxPeso = (Textbox) form.getFellow("tbxPeso");
		tbxTalla = (Textbox) form.getFellow("tbxTalla");
		tbxImc = (Textbox) form.getFellow("tbxImc");
		tbxTa = (Textbox) form.getFellow("tbxTa");
		tbxFc = (Textbox) form.getFellow("tbxFc");
		tbxFr = (Textbox) form.getFellow("tbxFr");
		tbxTemperatura = (Textbox) form.getFellow("tbxTemperatura");
		tbxCroomb = (Textbox) form.getFellow("tbxCroomb");
		dtbxFecha_ultima_mestruacion = (Datebox) form
				.getFellow("dtbxFecha_ultima_mestruacion");
		dtbxFecha_probable_parto = (Datebox) form
				.getFellow("dtbxFecha_probable_parto");
		tbxEdad_gestacional = (Textbox) form.getFellow("tbxEdad_gestacional");
		lbxControl = (Listbox) form.getFellow("lbxControl");
		tbxNum_control = (Textbox) form.getFellow("tbxNum_control");
		lbxFetales = (Listbox) form.getFellow("lbxFetales");
		lbxFiebre = (Listbox) form.getFellow("lbxFiebre");
		lbxLiquido = (Listbox) form.getFellow("lbxLiquido");
		lbxFlujo = (Listbox) form.getFellow("lbxFlujo");
		lbxEnfermedad = (Listbox) form.getFellow("lbxEnfermedad");
		tbxCual_enfermedad = (Textbox) form.getFellow("tbxCual_enfermedad");
		lbxCigarrillo = (Listbox) form.getFellow("lbxCigarrillo");
		lbxAlcohol = (Listbox) form.getFellow("lbxAlcohol");
		tbxCual_alcohol = (Textbox) form.getFellow("tbxCual_alcohol");
		lbxDroga = (Listbox) form.getFellow("lbxDroga");
		tbxCual_droga = (Textbox) form.getFellow("tbxCual_droga");
		lbxViolencia = (Listbox) form.getFellow("lbxViolencia");
		tbxCual_violencia = (Textbox) form.getFellow("tbxCual_violencia");
		lbxToxoide = (Listbox) form.getFellow("lbxToxoide");
		lbxDosis = (Listbox) form.getFellow("lbxDosis");
		tbxObservaciones_gestion = (Textbox) form
				.getFellow("tbxObservaciones_gestion");
		tbxAltura_uterina = (Textbox) form.getFellow("tbxAltura_uterina");
		chbCorelacion = (Checkbox) form.getFellow("chbCorelacion");
		chbEmbarazo_multiple = (Checkbox) form
				.getFellow("chbEmbarazo_multiple");
		chbTrasmision_sexual = (Checkbox) form
				.getFellow("chbTrasmision_sexual");
		rdbAnomalia = (Radiogroup) form.getFellow("rdbAnomalia");
		rdbEdema_gestion = (Radiogroup) form.getFellow("rdbEdema_gestion");
		rdbPalidez = (Radiogroup) form.getFellow("rdbPalidez");
		rdbConvulciones = (Radiogroup) form.getFellow("rdbConvulciones");
		rdbConciencia = (Radiogroup) form.getFellow("rdbConciencia");
		rdbCavidad_bucal = (Radiogroup) form.getFellow("rdbCavidad_bucal");
		tbxHto = (Textbox) form.getFellow("tbxHto");
		tbxHb = (Textbox) form.getFellow("tbxHb");
		tbxToxoplasma = (Textbox) form.getFellow("tbxToxoplasma");
		tbxVdrl1 = (Textbox) form.getFellow("tbxVdrl1");
		tbxVdrl2 = (Textbox) form.getFellow("tbxVdrl2");
		tbxVih1 = (Textbox) form.getFellow("tbxVih1");
		tbxVih2 = (Textbox) form.getFellow("tbxVih2");
		tbxHepb = (Textbox) form.getFellow("tbxHepb");
		tbxOtro = (Textbox) form.getFellow("tbxOtro");
		tbxEcografia = (Textbox) form.getFellow("tbxEcografia");
		rdbClasificacion_gestion = (Radiogroup) form
				.getFellow("rdbClasificacion_gestion");
		lbxContracciones = (Listbox) form.getFellow("lbxContracciones");
		lbxNum_contracciones = (Listbox) form.getFellow("lbxNum_contracciones");
		lbxHemorragia = (Listbox) form.getFellow("lbxHemorragia");
		lbxLiquido_vaginal = (Listbox) form.getFellow("lbxLiquido_vaginal");
		tbxColor_liquido = (Textbox) form.getFellow("tbxColor_liquido");
		lbxDolor_cabeza = (Listbox) form.getFellow("lbxDolor_cabeza");
		lbxVision = (Listbox) form.getFellow("lbxVision");
		lbxConvulcion = (Listbox) form.getFellow("lbxConvulcion");
		tbxObservaciones_parto = (Textbox) form
				.getFellow("tbxObservaciones_parto");
		tbxContracciones_min = (Textbox) form.getFellow("tbxContracciones_min");
		tbxFc_fera = (Textbox) form.getFellow("tbxFc_fera");
		tbxDilatacion_cervical = (Textbox) form
				.getFellow("tbxDilatacion_cervical");
		rdbPreentacion = (Radiogroup) form.getFellow("rdbPreentacion");
		tbxOtra_presentacion = (Textbox) form.getFellow("tbxOtra_presentacion");
		rdbEdema = (Radiogroup) form.getFellow("rdbEdema");
		lbxHemorragia_vaginal = (Listbox) form
				.getFellow("lbxHemorragia_vaginal");
		tbxHto_parto = (Textbox) form.getFellow("tbxHto_parto");
		tbxHb_parto = (Textbox) form.getFellow("tbxHb_parto");
		tbxHepb_parto = (Textbox) form.getFellow("tbxHepb_parto");
		tbxVdrl_parto = (Textbox) form.getFellow("tbxVdrl_parto");
		tbxVih_parto = (Textbox) form.getFellow("tbxVih_parto");
		rdbClasificacion_parto = (Radiogroup) form
				.getFellow("rdbClasificacion_parto");
		dtbxFecha_nac = (Datebox) form.getFellow("dtbxFecha_nac");
		tbxIdentificacion_nac = (Textbox) form
				.getFellow("tbxIdentificacion_nac");
		lbxSexo = (Listbox) form.getFellow("lbxSexo");
		tbxPeso_nac = (Textbox) form.getFellow("tbxPeso_nac");
		tbxTalla_nac = (Textbox) form.getFellow("tbxTalla_nac");
		tbxPc_nac = (Textbox) form.getFellow("tbxPc_nac");
		tbxFc_nac = (Textbox) form.getFellow("tbxFc_nac");
		tbxTemper_nac = (Textbox) form.getFellow("tbxTemper_nac");
		tbxEdad = (Textbox) form.getFellow("tbxEdad");
		tbxAdgar1 = (Textbox) form.getFellow("tbxAdgar1");
		tbxAdgar5 = (Textbox) form.getFellow("tbxAdgar5");
		tbxAdgar10 = (Textbox) form.getFellow("tbxAdgar10");
		tbxAdgar20 = (Textbox) form.getFellow("tbxAdgar20");
		tbxObservaciones_nac = (Textbox) form.getFellow("tbxObservaciones_nac");
		rdbClasificacion_nac = (Radiogroup) form
				.getFellow("rdbClasificacion_nac");
		chbReani_prematuro = (Checkbox) form.getFellow("chbReani_prematuro");
		chbReani_meconio = (Checkbox) form.getFellow("chbReani_meconio");
		chbReani_respiracion = (Checkbox) form
				.getFellow("chbReani_respiracion");
		chbReani_hipotonico = (Checkbox) form.getFellow("chbReani_hipotonico");
		chbReani_apnea = (Checkbox) form.getFellow("chbReani_apnea");
		chbReani_jadeo = (Checkbox) form.getFellow("chbReani_jadeo");
		chbReani_deficultosa = (Checkbox) form
				.getFellow("chbReani_deficultosa");
		chbReani_gianosis = (Checkbox) form.getFellow("chbReani_gianosis");
		chbReani_bradicardia = (Checkbox) form
				.getFellow("chbReani_bradicardia");
		chbReani_hipoxemia = (Checkbox) form.getFellow("chbReani_hipoxemia");
		chbReani_estimulacion = (Checkbox) form
				.getFellow("chbReani_estimulacion");
		chbReani_ventilacion = (Checkbox) form
				.getFellow("chbReani_ventilacion");
		chbReani_comprensiones = (Checkbox) form
				.getFellow("chbReani_comprensiones");
		chbReani_intubacion = (Checkbox) form.getFellow("chbReani_intubacion");
		tbxMedicina = (Textbox) form.getFellow("tbxMedicina");
		rdbClasificacion_reani = (Radiogroup) form
				.getFellow("rdbClasificacion_reani");
		lbxRuptura = (Listbox) form.getFellow("lbxRuptura");
		lbxTiempo_ruptura = (Listbox) form.getFellow("lbxTiempo_ruptura");
		tbxLiquido_neo = (Textbox) form.getFellow("tbxLiquido_neo");
		lbxFiebre_neo = (Listbox) form.getFellow("lbxFiebre_neo");
		lbxTiempo_neo = (Listbox) form.getFellow("lbxTiempo_neo");
		tbxCoricamniotis = (Textbox) form.getFellow("tbxCoricamniotis");
		rdbIntrauterina = (Radiogroup) form.getFellow("rdbIntrauterina");
		lbxMadre20 = (Listbox) form.getFellow("lbxMadre20");
		chbAlcohol_neo = (Checkbox) form.getFellow("chbAlcohol_neo");
		chbDrogas_neo = (Checkbox) form.getFellow("chbDrogas_neo");
		chbCigarrillo_neo = (Checkbox) form.getFellow("chbCigarrillo_neo");
		rdbRespiracion_neo = (Radiogroup) form.getFellow("rdbRespiracion_neo");
		rdbLlanto_neo = (Radiogroup) form.getFellow("rdbLlanto_neo");
		rdbVetalidad_neo = (Radiogroup) form.getFellow("rdbVetalidad_neo");
		chbTaquicardia_neo = (Checkbox) form.getFellow("chbTaquicardia_neo");
		chbBradicardia_neo = (Checkbox) form.getFellow("chbBradicardia_neo");
		chbPalidez_neo = (Checkbox) form.getFellow("chbPalidez_neo");
		chbCianosis_neo = (Checkbox) form.getFellow("chbCianosis_neo");
		lbxAnomalias_congenitas = (Listbox) form
				.getFellow("lbxAnomalias_congenitas");
		tbxCual_anomalias = (Textbox) form.getFellow("tbxCual_anomalias");
		tbxLesiones = (Textbox) form.getFellow("tbxLesiones");
		tbxOtras_alter = (Textbox) form.getFellow("tbxOtras_alter");
		rdbClasificacion_neo = (Radiogroup) form
				.getFellow("rdbClasificacion_neo");
		tbxAlarma = (Textbox) form.getFellow("tbxAlarma");
		tbxConsulta_control = (Textbox) form.getFellow("tbxConsulta_control");
		tbxMedidas_preventiva = (Textbox) form
				.getFellow("tbxMedidas_preventiva");
		tbxRecomendaciones = (Textbox) form.getFellow("tbxRecomendaciones");
		tbxDiagnostico = (Textbox) form.getFellow("tbxDiagnostico");
		tbxCodigo_diagnostico = (Textbox) form
				.getFellow("tbxCodigo_diagnostico");
		tbxTratamiento = (Textbox) form.getFellow("tbxTratamiento");
		tbxRecomendacion_alimentacion = (Textbox) form
				.getFellow("tbxRecomendacion_alimentacion");
		tbxEvolucion_servicio = (Textbox) form
				.getFellow("tbxEvolucion_servicio");

	}

	public void initHis_atencion_embarazada() throws Exception {
//		HttpServletRequest request = (HttpServletRequest) Executions
//				.getCurrent().getNativeRequest();
		try {
			accionForm(true, "registrar");

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}

	public void listarCombos() {
		listarParameter();
		listarDepartamentos(lbxCodigo_dpto, true);
		listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
		listarElementoListbox(lbxGestaciones, false);
		listarElementoListbox(lbxPartos, false);
		listarElementoListbox(lbxCesarias, false);
		listarElementoListbox(lbxAbortos, false);
		listarElementoListbox(lbxEspontaneo, false);
		listarElementoListbox(lbxProvocado, false);
		listarElementoListbox(lbxNacido_muerto, false);
		listarElementoListbox(lbxPrematuro, false);
		listarElementoListbox(lbxHijos_menos, false);
		listarElementoListbox(lbxHijos_mayor, false);
		listarElementoListbox(lbxMalformado, false);
		listarElementoListbox(lbxHipertension, false);
		listarElementoListbox(lbxCirugia, false);
		listarElementoListbox(lbxHemoclasificacion, false);
		listarElementoListbox(lbxRh, false);
		listarElementoListbox(lbxControl, false);
		listarElementoListbox(lbxFetales, false);
		listarElementoListbox(lbxFiebre, false);
		listarElementoListbox(lbxLiquido, false);
		listarElementoListbox(lbxFlujo, false);
		listarElementoListbox(lbxEnfermedad, false);
		listarElementoListbox(lbxCigarrillo, false);
		listarElementoListbox(lbxAlcohol, false);
		listarElementoListbox(lbxDroga, false);
		listarElementoListbox(lbxViolencia, false);
		listarElementoListbox(lbxToxoide, false);
		listarElementoListbox(lbxDosis, false);
		listarElementoListbox(lbxContracciones, false);
		listarElementoListbox(lbxNum_contracciones, false);
		listarElementoListbox(lbxHemorragia, false);
		listarElementoListbox(lbxLiquido_vaginal, false);
		listarElementoListbox(lbxDolor_cabeza, false);
		listarElementoListbox(lbxVision, false);
		listarElementoListbox(lbxConvulcion, false);
		listarElementoListbox(lbxHemorragia_vaginal, false);
		listarElementoListbox(lbxSexo, true);
		listarElementoListbox(lbxRuptura, false);
		listarElementoListbox(lbxTiempo_ruptura, false);
		listarElementoListbox(lbxFiebre_neo, false);
		listarElementoListbox(lbxTiempo_neo, false);
		listarElementoListbox(lbxAnomalias_congenitas, false);
		listarElementoListbox(lbxMadre20, false);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha_inicial");
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

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String codigo_empresa = empresa.getCodigo_empresa();
			String codigo_sucursal = sucursal.getCodigo_sucursal();
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getHis_atencion_embarazadaService().setLimit(
					"limit 25 offset 0");

			List<His_atencion_embarazada> lista_datos = getServiceLocator()
					.getHis_atencion_embarazadaService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (His_atencion_embarazada his_atencion_embarazada : lista_datos) {
				rowsResultado.appendChild(crearFilas(his_atencion_embarazada,
						this));
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

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final His_atencion_embarazada his_atencion_embarazada = (His_atencion_embarazada) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(his_atencion_embarazada.getCodigo_historia()
				+ ""));
		fila.appendChild(new Label(his_atencion_embarazada.getIdentificacion()
				+ ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {

				mostrarDatos(his_atencion_embarazada);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		// img.setVisible((permisos_sc.getPermiso_eliminar()?true:false));
		img.setSrc("/images/eliminar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(his_atencion_embarazada);
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
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //

				Map datos = new HashMap();

				His_atencion_embarazada his_atencion_embarazada = new His_atencion_embarazada();
				his_atencion_embarazada.setCodigo_empresa(empresa
						.getCodigo_empresa());
				his_atencion_embarazada.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				his_atencion_embarazada.setCodigo_historia(tbxCodigo_historia
						.getValue());
				his_atencion_embarazada.setFecha_inicial(new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				his_atencion_embarazada.setCodigo_eps(tbxCodigo_eps.getValue());
				his_atencion_embarazada.setCodigo_dpto(lbxCodigo_dpto
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setCodigo_municipio(lbxCodigo_municipio
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setIdentificacion(tbxIdentificacion
						.getValue());
				his_atencion_embarazada.setDireccion(tbxMotivo.getValue());
				his_atencion_embarazada.setTelefono(tbxTelefono.getValue());
				his_atencion_embarazada.setSeleccion(rdbSeleccion
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setGestaciones(lbxGestaciones
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setPartos(lbxPartos.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setCesarias(lbxCesarias
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setAbortos(lbxAbortos.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setEspontaneo(lbxEspontaneo
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setProvocado(lbxProvocado
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setNacido_muerto(lbxNacido_muerto
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setPrematuro(lbxPrematuro
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setHijos_menos(lbxHijos_menos
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setHijos_mayor(lbxHijos_mayor
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setMalformado(lbxMalformado
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setHipertension(lbxHipertension
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setFecha_ultimo_parto(new Timestamp(
						dtbxFecha_ultimo_parto.getValue().getTime()));
				his_atencion_embarazada.setCirugia(lbxCirugia.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setOtro_antecedente(tbxOtro_antecedente
						.getValue());
				his_atencion_embarazada
						.setHemoclasificacion(lbxHemoclasificacion
								.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setRh(lbxRh.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setPeso(tbxPeso.getValue());
				his_atencion_embarazada.setTalla(tbxTalla.getValue());
				his_atencion_embarazada.setImc(tbxImc.getValue());
				his_atencion_embarazada.setTa(tbxTa.getValue());
				his_atencion_embarazada.setFc(tbxFc.getValue());
				his_atencion_embarazada.setFr(tbxFr.getValue());
				his_atencion_embarazada.setTemperatura(tbxTemperatura
						.getValue());
				his_atencion_embarazada.setCroomb(tbxCroomb.getValue());
				his_atencion_embarazada
						.setFecha_ultima_mestruacion(new Timestamp(
								dtbxFecha_ultima_mestruacion.getValue()
										.getTime()));
				his_atencion_embarazada.setFecha_probable_parto(new Timestamp(
						dtbxFecha_probable_parto.getValue().getTime()));
				his_atencion_embarazada.setEdad_gestacional(tbxEdad_gestacional
						.getValue());
				his_atencion_embarazada.setControl(lbxControl.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setNum_control(tbxNum_control
						.getValue());
				his_atencion_embarazada.setFetales(lbxFetales.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setFiebre(lbxFiebre.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setLiquido(lbxLiquido.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setFlujo(lbxFlujo.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setEnfermedad(lbxEnfermedad
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setCual_enfermedad(tbxCual_enfermedad
						.getValue());
				his_atencion_embarazada.setCigarrillo(lbxCigarrillo
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setAlcohol(lbxAlcohol.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setCual_alcohol(tbxCual_alcohol
						.getValue());
				his_atencion_embarazada.setDroga(lbxDroga.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setCual_droga(tbxCual_droga.getValue());
				his_atencion_embarazada.setViolencia(lbxViolencia
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setCual_violencia(tbxCual_violencia
						.getValue());
				his_atencion_embarazada.setToxoide(lbxToxoide.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setDosis(lbxDosis.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada
						.setObservaciones_gestion(tbxObservaciones_gestion
								.getValue());
				his_atencion_embarazada.setAltura_uterina(tbxAltura_uterina
						.getValue());
				his_atencion_embarazada
						.setCorelacion(chbCorelacion.isChecked());
				his_atencion_embarazada
						.setEmbarazo_multiple(chbEmbarazo_multiple.isChecked());
				his_atencion_embarazada
						.setTrasmision_sexual(chbTrasmision_sexual.isChecked());
				his_atencion_embarazada.setAnomalia(rdbAnomalia
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setEdema_gestion(rdbEdema_gestion
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setPalidez(rdbPalidez.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setConvulciones(rdbConvulciones
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setConciencia(rdbConciencia
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setCavidad_bucal(rdbCavidad_bucal
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setHto(tbxHto.getValue());
				his_atencion_embarazada.setHb(tbxHb.getValue());
				his_atencion_embarazada.setToxoplasma(tbxToxoplasma.getValue());
				his_atencion_embarazada.setVdrl1(tbxVdrl1.getValue());
				his_atencion_embarazada.setVdrl2(tbxVdrl2.getValue());
				his_atencion_embarazada.setVih1(tbxVih1.getValue());
				his_atencion_embarazada.setVih2(tbxVih2.getValue());
				his_atencion_embarazada.setHepb(tbxHepb.getValue());
				his_atencion_embarazada.setOtro(tbxOtro.getValue());
				his_atencion_embarazada.setEcografia(tbxEcografia.getValue());
				his_atencion_embarazada
						.setClasificacion_gestion(rdbClasificacion_gestion
								.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setContracciones(lbxContracciones
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada
						.setNum_contracciones(lbxNum_contracciones
								.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setHemorragia(lbxHemorragia
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setLiquido_vaginal(lbxLiquido_vaginal
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setColor_liquido(tbxColor_liquido
						.getValue());
				his_atencion_embarazada.setDolor_cabeza(lbxDolor_cabeza
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setVision(lbxVision.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setConvulcion(lbxConvulcion
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada
						.setObservaciones_parto(tbxObservaciones_parto
								.getValue());
				his_atencion_embarazada
						.setContracciones_min(tbxContracciones_min.getValue());
				his_atencion_embarazada.setFc_fera(tbxFc_fera.getValue());
				his_atencion_embarazada
						.setDilatacion_cervical(tbxDilatacion_cervical
								.getValue());
				his_atencion_embarazada.setPreentacion(rdbPreentacion
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada
						.setOtra_presentacion(tbxOtra_presentacion.getValue());
				his_atencion_embarazada.setEdema(rdbEdema.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada
						.setHemorragia_vaginal(lbxHemorragia_vaginal
								.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setHto_parto(tbxHto_parto.getValue());
				his_atencion_embarazada.setHb_parto(tbxHb_parto.getValue());
				his_atencion_embarazada.setHepb_parto(tbxHepb_parto.getValue());
				his_atencion_embarazada.setVdrl_parto(tbxVdrl_parto.getValue());
				his_atencion_embarazada.setVih_parto(tbxVih_parto.getValue());
				his_atencion_embarazada
						.setClasificacion_parto(rdbClasificacion_parto
								.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setFecha_nac(new Timestamp(
						dtbxFecha_nac.getValue().getTime()));
				his_atencion_embarazada
						.setIdentificacion_nac(tbxIdentificacion_nac.getValue());
				his_atencion_embarazada.setSexo(lbxSexo.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setPeso_nac(tbxPeso_nac.getValue());
				his_atencion_embarazada.setTalla_nac(tbxTalla_nac.getValue());
				his_atencion_embarazada.setPc_nac(tbxPc_nac.getValue());
				his_atencion_embarazada.setFc_nac(tbxFc_nac.getValue());
				his_atencion_embarazada.setTemper_nac(tbxTemper_nac.getValue());
				his_atencion_embarazada.setEdad(tbxEdad.getValue());
				his_atencion_embarazada.setAdgar1(tbxAdgar1.getValue());
				his_atencion_embarazada.setAdgar5(tbxAdgar5.getValue());
				his_atencion_embarazada.setAdgar10(tbxAdgar10.getValue());
				his_atencion_embarazada.setAdgar20(tbxAdgar20.getValue());
				his_atencion_embarazada
						.setObservaciones_nac(tbxObservaciones_nac.getValue());
				his_atencion_embarazada
						.setClasificacion_nac(rdbClasificacion_nac
								.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setReani_prematuro(chbReani_prematuro
						.isChecked());
				his_atencion_embarazada.setReani_meconio(chbReani_meconio
						.isChecked());
				his_atencion_embarazada
						.setReani_respiracion(chbReani_respiracion.isChecked());
				his_atencion_embarazada.setReani_hipotonico(chbReani_hipotonico
						.isChecked());
				his_atencion_embarazada.setReani_apnea(chbReani_apnea
						.isChecked());
				his_atencion_embarazada.setReani_jadeo(chbReani_jadeo
						.isChecked());
				his_atencion_embarazada
						.setReani_deficultosa(chbReani_deficultosa.isChecked());
				his_atencion_embarazada.setReani_gianosis(chbReani_gianosis
						.isChecked());
				his_atencion_embarazada
						.setReani_bradicardia(chbReani_bradicardia.isChecked());
				his_atencion_embarazada.setReani_hipoxemia(chbReani_hipoxemia
						.isChecked());
				his_atencion_embarazada
						.setReani_estimulacion(chbReani_estimulacion
								.isChecked());
				his_atencion_embarazada
						.setReani_ventilacion(chbReani_ventilacion.isChecked());
				his_atencion_embarazada
						.setReani_comprensiones(chbReani_comprensiones
								.isChecked());
				his_atencion_embarazada.setReani_intubacion(chbReani_intubacion
						.isChecked());
				his_atencion_embarazada.setMedicina(tbxMedicina.getValue());
				his_atencion_embarazada
						.setClasificacion_reani(rdbClasificacion_reani
								.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setRuptura(lbxRuptura.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setTiempo_ruptura(lbxTiempo_ruptura
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setLiquido_neo(tbxLiquido_neo
						.getValue());
				his_atencion_embarazada.setFiebre_neo(lbxFiebre_neo
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setTiempo_neo(lbxTiempo_neo
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setCoricamniotis(tbxCoricamniotis
						.getValue());
				his_atencion_embarazada.setIntrauterina(rdbIntrauterina
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setMadre20(lbxMadre20.getSelectedItem()
						.getValue().toString());
				his_atencion_embarazada.setAlcohol_neo(chbAlcohol_neo
						.isChecked());
				his_atencion_embarazada
						.setDrogas_neo(chbDrogas_neo.isChecked());
				his_atencion_embarazada.setCigarrillo_neo(chbCigarrillo_neo
						.isChecked());
				his_atencion_embarazada.setRespiracion_neo(rdbRespiracion_neo
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setLlanto_neo(rdbLlanto_neo
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setVetalidad_neo(rdbVetalidad_neo
						.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setTaquicardia_neo(chbTaquicardia_neo
						.isChecked());
				his_atencion_embarazada.setBradicardia_neo(chbBradicardia_neo
						.isChecked());
				his_atencion_embarazada.setPalidez_neo(chbPalidez_neo
						.isChecked());
				his_atencion_embarazada.setCianosis_neo(chbCianosis_neo
						.isChecked());
				his_atencion_embarazada
						.setAnomalias_congenitas(lbxAnomalias_congenitas
								.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setCual_anomalias(tbxCual_anomalias
						.getValue());
				his_atencion_embarazada.setLesiones(tbxLesiones.getValue());
				his_atencion_embarazada.setOtras_alter(tbxOtras_alter
						.getValue());
				his_atencion_embarazada
						.setClasificacion_neo(rdbClasificacion_neo
								.getSelectedItem().getValue().toString());
				his_atencion_embarazada.setAlarma(tbxAlarma.getValue());
				his_atencion_embarazada.setConsulta_control(tbxConsulta_control
						.getValue());
				his_atencion_embarazada
						.setMedidas_preventiva(tbxMedidas_preventiva.getValue());
				his_atencion_embarazada.setRecomendaciones(tbxRecomendaciones
						.getValue());
				his_atencion_embarazada.setDiagnostico(tbxDiagnostico
						.getValue());
				his_atencion_embarazada
						.setCodigo_diagnostico(tbxCodigo_diagnostico.getValue());
				his_atencion_embarazada.setTratamiento(tbxTratamiento
						.getValue());
				his_atencion_embarazada
						.setRecomendacion_alimentacion(tbxRecomendacion_alimentacion
								.getValue());
				his_atencion_embarazada
						.setEvolucion_servicio(tbxEvolucion_servicio.getValue());

				datos.put("codigo_historia", his_atencion_embarazada);
				datos.put("accion", tbxAccion.getText());

				his_atencion_embarazada = getServiceLocator()
						.getHis_atencion_embarazadaService().guardar(datos);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					accionForm(true, "modificar");
				}
				/*
				 * else{ int result =
				 * getServiceLocator().getHis_atencion_embarazadaService
				 * ().actualizar(his_atencion_embarazada); if(result==0){ throw
				 * new Exception(
				 * "Lo sentimos pero los datos a modificar no se encuentran en base de datos"
				 * ); } }
				 */

//				codigo_historia = his_atencion_embarazada.getCodigo_historia();

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

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		His_atencion_embarazada his_atencion_embarazada = (His_atencion_embarazada) obj;
		try {
			tbxCodigo_historia.setValue(his_atencion_embarazada
					.getCodigo_historia());
			dtbxFecha_inicial.setValue(his_atencion_embarazada
					.getFecha_inicial());

			Administradora administradora = new Administradora();
			administradora.setCodigo(his_atencion_embarazada.getCodigo_eps());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getCodigo_dpto())) {
					listitem.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(his_atencion_embarazada
					.getCodigo_empresa());
			paciente.setCodigo_sucursal(his_atencion_embarazada
					.getCodigo_sucursal());
			paciente.setNro_identificacion(his_atencion_embarazada
					.getIdentificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			Elemento elemento = new Elemento();
			elemento.setCodigo(paciente.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxIdentificacion.setValue(his_atencion_embarazada
					.getIdentificacion());
			tbxNomPaciente.setValue((paciente != null ? paciente.getNombre1()
					+ " " + paciente.getApellido1() : ""));
			tbxTipoIdentificacion.setValue((paciente != null ? paciente
					.getTipo_identificacion() : ""));
			tbxEdad_madre.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
					paciente.getUnidad_medidad(), false));
			tbxSexo_madre.setValue((elemento != null ? elemento
					.getDescripcion() : ""));
			dbxNacimiento.setValue(paciente.getFecha_nacimiento());

			tbxMotivo.setValue(his_atencion_embarazada.getDireccion());
			tbxTelefono.setValue(his_atencion_embarazada.getTelefono());
			Radio radio = (Radio) rdbSeleccion.getFellow("Seleccion"
					+ his_atencion_embarazada.getSeleccion());
			radio.setChecked(true);
			for (int i = 0; i < lbxGestaciones.getItemCount(); i++) {
				Listitem listitem = lbxGestaciones.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getGestaciones())) {
					i = lbxGestaciones.getItemCount();
				}
			}
			for (int i = 0; i < lbxPartos.getItemCount(); i++) {
				Listitem listitem = lbxPartos.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getPartos())) {
					listitem.setSelected(true);
					i = lbxPartos.getItemCount();
				}
			}
			for (int i = 0; i < lbxCesarias.getItemCount(); i++) {
				Listitem listitem = lbxCesarias.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getCesarias())) {
					listitem.setSelected(true);
					i = lbxCesarias.getItemCount();
				}
			}
			for (int i = 0; i < lbxAbortos.getItemCount(); i++) {
				Listitem listitem = lbxAbortos.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getAbortos())) {
					listitem.setSelected(true);
					i = lbxAbortos.getItemCount();
				}
			}
			for (int i = 0; i < lbxEspontaneo.getItemCount(); i++) {
				Listitem listitem = lbxEspontaneo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getEspontaneo())) {
					listitem.setSelected(true);
					i = lbxEspontaneo.getItemCount();
				}
			}
			for (int i = 0; i < lbxProvocado.getItemCount(); i++) {
				Listitem listitem = lbxProvocado.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getProvocado())) {
					listitem.setSelected(true);
					i = lbxProvocado.getItemCount();
				}
			}
			for (int i = 0; i < lbxNacido_muerto.getItemCount(); i++) {
				Listitem listitem = lbxNacido_muerto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getNacido_muerto())) {
					listitem.setSelected(true);
					i = lbxNacido_muerto.getItemCount();
				}
			}
			for (int i = 0; i < lbxPrematuro.getItemCount(); i++) {
				Listitem listitem = lbxPrematuro.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getPrematuro())) {
					listitem.setSelected(true);
					i = lbxPrematuro.getItemCount();
				}
			}
			for (int i = 0; i < lbxHijos_menos.getItemCount(); i++) {
				Listitem listitem = lbxHijos_menos.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getHijos_menos())) {
					listitem.setSelected(true);
					i = lbxHijos_menos.getItemCount();
				}
			}
			for (int i = 0; i < lbxHijos_mayor.getItemCount(); i++) {
				Listitem listitem = lbxHijos_mayor.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getHijos_mayor())) {
					listitem.setSelected(true);
					i = lbxHijos_mayor.getItemCount();
				}
			}
			for (int i = 0; i < lbxMalformado.getItemCount(); i++) {
				Listitem listitem = lbxMalformado.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getMalformado())) {
					listitem.setSelected(true);
					i = lbxMalformado.getItemCount();
				}
			}
			for (int i = 0; i < lbxHipertension.getItemCount(); i++) {
				Listitem listitem = lbxHipertension.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getHipertension())) {
					listitem.setSelected(true);
					i = lbxHipertension.getItemCount();
				}
			}
			dtbxFecha_ultimo_parto.setValue(his_atencion_embarazada
					.getFecha_ultimo_parto());
			for (int i = 0; i < lbxCirugia.getItemCount(); i++) {
				Listitem listitem = lbxCirugia.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getCirugia())) {
					listitem.setSelected(true);
					i = lbxCirugia.getItemCount();
				}
			}
			tbxOtro_antecedente.setValue(his_atencion_embarazada
					.getOtro_antecedente());
			for (int i = 0; i < lbxHemoclasificacion.getItemCount(); i++) {
				Listitem listitem = lbxHemoclasificacion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getHemoclasificacion())) {
					listitem.setSelected(true);
					i = lbxHemoclasificacion.getItemCount();
				}
			}
			for (int i = 0; i < lbxRh.getItemCount(); i++) {
				Listitem listitem = lbxRh.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getRh())) {
					listitem.setSelected(true);
					i = lbxRh.getItemCount();
				}
			}
			tbxPeso.setValue(his_atencion_embarazada.getPeso());
			tbxTalla.setValue(his_atencion_embarazada.getTalla());
			tbxImc.setValue(his_atencion_embarazada.getImc());
			tbxTa.setValue(his_atencion_embarazada.getTa());
			tbxFc.setValue(his_atencion_embarazada.getFc());
			tbxFr.setValue(his_atencion_embarazada.getFr());
			tbxTemperatura.setValue(his_atencion_embarazada.getTemperatura());
			tbxCroomb.setValue(his_atencion_embarazada.getCroomb());
			dtbxFecha_ultima_mestruacion.setValue(his_atencion_embarazada
					.getFecha_ultima_mestruacion());
			dtbxFecha_probable_parto.setValue(his_atencion_embarazada
					.getFecha_probable_parto());
			tbxEdad_gestacional.setValue(his_atencion_embarazada
					.getEdad_gestacional());
			for (int i = 0; i < lbxControl.getItemCount(); i++) {
				Listitem listitem = lbxControl.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getControl())) {
					listitem.setSelected(true);
					i = lbxControl.getItemCount();
				}
			}
			tbxNum_control.setValue(his_atencion_embarazada.getNum_control());
			for (int i = 0; i < lbxFetales.getItemCount(); i++) {
				Listitem listitem = lbxFetales.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getFetales())) {
					listitem.setSelected(true);
					i = lbxFetales.getItemCount();
				}
			}
			for (int i = 0; i < lbxFiebre.getItemCount(); i++) {
				Listitem listitem = lbxFiebre.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getFiebre())) {
					listitem.setSelected(true);
					i = lbxFiebre.getItemCount();
				}
			}
			for (int i = 0; i < lbxLiquido.getItemCount(); i++) {
				Listitem listitem = lbxLiquido.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getLiquido())) {
					listitem.setSelected(true);
					i = lbxLiquido.getItemCount();
				}
			}
			for (int i = 0; i < lbxFlujo.getItemCount(); i++) {
				Listitem listitem = lbxFlujo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getFlujo())) {
					listitem.setSelected(true);
					i = lbxFlujo.getItemCount();
				}
			}
			for (int i = 0; i < lbxEnfermedad.getItemCount(); i++) {
				Listitem listitem = lbxEnfermedad.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getEnfermedad())) {
					listitem.setSelected(true);
					i = lbxEnfermedad.getItemCount();
				}
			}
			tbxCual_enfermedad.setValue(his_atencion_embarazada
					.getCual_enfermedad());
			for (int i = 0; i < lbxCigarrillo.getItemCount(); i++) {
				Listitem listitem = lbxCigarrillo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getCigarrillo())) {
					listitem.setSelected(true);
					i = lbxCigarrillo.getItemCount();
				}
			}
			for (int i = 0; i < lbxAlcohol.getItemCount(); i++) {
				Listitem listitem = lbxAlcohol.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getAlcohol())) {
					listitem.setSelected(true);
					i = lbxAlcohol.getItemCount();
				}
			}
			tbxCual_alcohol.setValue(his_atencion_embarazada.getCual_alcohol());
			for (int i = 0; i < lbxDroga.getItemCount(); i++) {
				Listitem listitem = lbxDroga.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getDroga())) {
					listitem.setSelected(true);
					i = lbxDroga.getItemCount();
				}
			}
			tbxCual_droga.setValue(his_atencion_embarazada.getCual_droga());
			for (int i = 0; i < lbxViolencia.getItemCount(); i++) {
				Listitem listitem = lbxViolencia.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getViolencia())) {
					listitem.setSelected(true);
					i = lbxViolencia.getItemCount();
				}
			}
			tbxCual_violencia.setValue(his_atencion_embarazada
					.getCual_violencia());
			for (int i = 0; i < lbxToxoide.getItemCount(); i++) {
				Listitem listitem = lbxToxoide.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getToxoide())) {
					listitem.setSelected(true);
					i = lbxToxoide.getItemCount();
				}
			}
			for (int i = 0; i < lbxDosis.getItemCount(); i++) {
				Listitem listitem = lbxDosis.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getDosis())) {
					listitem.setSelected(true);
					i = lbxDosis.getItemCount();
				}
			}
			tbxObservaciones_gestion.setValue(his_atencion_embarazada
					.getObservaciones_gestion());
			tbxAltura_uterina.setValue(his_atencion_embarazada
					.getAltura_uterina());
			chbCorelacion.setChecked(his_atencion_embarazada.getCorelacion());
			chbEmbarazo_multiple.setChecked(his_atencion_embarazada
					.getEmbarazo_multiple());
			chbTrasmision_sexual.setChecked(his_atencion_embarazada
					.getTrasmision_sexual());
			Radio radio1 = (Radio) rdbAnomalia.getFellow("Anomalia"
					+ his_atencion_embarazada.getAnomalia());
			radio1.setChecked(true);
			Radio radio2 = (Radio) rdbEdema_gestion.getFellow("Edema_gestion"
					+ his_atencion_embarazada.getEdema_gestion());
			radio2.setChecked(true);
			Radio radio3 = (Radio) rdbPalidez.getFellow("Palidez"
					+ his_atencion_embarazada.getPalidez());
			radio3.setChecked(true);
			Radio radio4 = (Radio) rdbConvulciones.getFellow("Convulciones"
					+ his_atencion_embarazada.getConvulciones());
			radio4.setChecked(true);
			Radio radio5 = (Radio) rdbConciencia.getFellow("Conciencia"
					+ his_atencion_embarazada.getConciencia());
			radio5.setChecked(true);
			Radio radio6 = (Radio) rdbCavidad_bucal.getFellow("Cavidad_bucal"
					+ his_atencion_embarazada.getCavidad_bucal());
			radio6.setChecked(true);
			tbxHto.setValue(his_atencion_embarazada.getHto());
			tbxHb.setValue(his_atencion_embarazada.getHb());
			tbxToxoplasma.setValue(his_atencion_embarazada.getToxoplasma());
			tbxVdrl1.setValue(his_atencion_embarazada.getVdrl1());
			tbxVdrl2.setValue(his_atencion_embarazada.getVdrl2());
			tbxVih1.setValue(his_atencion_embarazada.getVih1());
			tbxVih2.setValue(his_atencion_embarazada.getVih2());
			tbxHepb.setValue(his_atencion_embarazada.getHepb());
			tbxOtro.setValue(his_atencion_embarazada.getOtro());
			tbxEcografia.setValue(his_atencion_embarazada.getEcografia());
			Radio radio7 = (Radio) rdbClasificacion_gestion
					.getFellow("Clasificacion_gestion"
							+ his_atencion_embarazada
									.getClasificacion_gestion());
			radio7.setChecked(true);
			for (int i = 0; i < lbxContracciones.getItemCount(); i++) {
				Listitem listitem = lbxContracciones.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getContracciones())) {
					listitem.setSelected(true);
					i = lbxContracciones.getItemCount();
				}
			}
			for (int i = 0; i < lbxNum_contracciones.getItemCount(); i++) {
				Listitem listitem = lbxNum_contracciones.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getNum_contracciones())) {
					listitem.setSelected(true);
					i = lbxNum_contracciones.getItemCount();
				}
			}
			for (int i = 0; i < lbxHemorragia.getItemCount(); i++) {
				Listitem listitem = lbxHemorragia.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getHemorragia())) {
					listitem.setSelected(true);
					i = lbxHemorragia.getItemCount();
				}
			}
			for (int i = 0; i < lbxLiquido_vaginal.getItemCount(); i++) {
				Listitem listitem = lbxLiquido_vaginal.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getLiquido_vaginal())) {
					listitem.setSelected(true);
					i = lbxLiquido_vaginal.getItemCount();
				}
			}
			tbxColor_liquido.setValue(his_atencion_embarazada
					.getColor_liquido());
			for (int i = 0; i < lbxDolor_cabeza.getItemCount(); i++) {
				Listitem listitem = lbxDolor_cabeza.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getDolor_cabeza())) {
					listitem.setSelected(true);
					i = lbxDolor_cabeza.getItemCount();
				}
			}
			for (int i = 0; i < lbxVision.getItemCount(); i++) {
				Listitem listitem = lbxVision.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getDolor_cabeza())) {
					listitem.setSelected(true);
					i = lbxVision.getItemCount();
				}
			}
			for (int i = 0; i < lbxConvulcion.getItemCount(); i++) {
				Listitem listitem = lbxConvulcion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getConvulcion())) {
					listitem.setSelected(true);
					i = lbxConvulcion.getItemCount();
				}
			}
			tbxObservaciones_parto.setValue(his_atencion_embarazada
					.getObservaciones_parto());
			tbxContracciones_min.setValue(his_atencion_embarazada
					.getContracciones_min());
			tbxFc_fera.setValue(his_atencion_embarazada.getFc_fera());
			tbxDilatacion_cervical.setValue(his_atencion_embarazada
					.getDilatacion_cervical());
			Radio radio8 = (Radio) rdbPreentacion.getFellow("Preentacion"
					+ his_atencion_embarazada.getPreentacion());
			radio8.setChecked(true);
			tbxOtra_presentacion.setValue(his_atencion_embarazada
					.getOtra_presentacion());
			Radio radio9 = (Radio) rdbEdema.getFellow("Edema"
					+ his_atencion_embarazada.getEdema());
			radio9.setChecked(true);
			for (int i = 0; i < lbxHemorragia_vaginal.getItemCount(); i++) {
				Listitem listitem = lbxHemorragia_vaginal.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(his_atencion_embarazada.getHemorragia_vaginal())) {
					listitem.setSelected(true);
					i = lbxHemorragia_vaginal.getItemCount();
				}
			}
			tbxHto_parto.setValue(his_atencion_embarazada.getHto_parto());
			tbxHb_parto.setValue(his_atencion_embarazada.getHb_parto());
			tbxHepb_parto.setValue(his_atencion_embarazada.getHepb_parto());
			tbxVdrl_parto.setValue(his_atencion_embarazada.getVdrl_parto());
			tbxVih_parto.setValue(his_atencion_embarazada.getVih_parto());
			Radio radio10 = (Radio) rdbClasificacion_parto
					.getFellow("Clasificacion_parto"
							+ his_atencion_embarazada.getClasificacion_parto());
			radio10.setChecked(true);
			dtbxFecha_nac.setValue(his_atencion_embarazada.getFecha_nac());
			tbxIdentificacion_nac.setValue(his_atencion_embarazada
					.getIdentificacion_nac());
			for (int i = 0; i < lbxSexo.getItemCount(); i++) {
				Listitem listitem = lbxSexo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getSexo())) {
					listitem.setSelected(true);
					i = lbxSexo.getItemCount();
				}
			}
			tbxPeso_nac.setValue(his_atencion_embarazada.getPeso_nac());
			tbxTalla_nac.setValue(his_atencion_embarazada.getTalla_nac());
			tbxPc_nac.setValue(his_atencion_embarazada.getPc_nac());
			tbxFc_nac.setValue(his_atencion_embarazada.getFc_nac());
			tbxTemper_nac.setValue(his_atencion_embarazada.getTemper_nac());
			tbxEdad.setValue(his_atencion_embarazada.getEdad());
			tbxAdgar1.setValue(his_atencion_embarazada.getAdgar1());
			tbxAdgar5.setValue(his_atencion_embarazada.getAdgar5());
			tbxAdgar10.setValue(his_atencion_embarazada.getAdgar10());
			tbxAdgar20.setValue(his_atencion_embarazada.getAdgar20());
			tbxObservaciones_nac.setValue(his_atencion_embarazada
					.getObservaciones_nac());
			Radio radio11 = (Radio) rdbClasificacion_nac
					.getFellow("Clasificacion_nac"
							+ his_atencion_embarazada.getClasificacion_nac());
			radio11.setChecked(true);
			chbReani_prematuro.setChecked(his_atencion_embarazada
					.getReani_prematuro());
			chbReani_meconio.setChecked(his_atencion_embarazada
					.getReani_meconio());
			chbReani_respiracion.setChecked(his_atencion_embarazada
					.getReani_respiracion());
			chbReani_hipotonico.setChecked(his_atencion_embarazada
					.getReani_hipotonico());
			chbReani_apnea.setChecked(his_atencion_embarazada.getReani_apnea());
			chbReani_jadeo.setChecked(his_atencion_embarazada.getReani_jadeo());
			chbReani_deficultosa.setChecked(his_atencion_embarazada
					.getReani_deficultosa());
			chbReani_gianosis.setChecked(his_atencion_embarazada
					.getReani_gianosis());
			chbReani_bradicardia.setChecked(his_atencion_embarazada
					.getReani_bradicardia());
			chbReani_hipoxemia.setChecked(his_atencion_embarazada
					.getReani_hipoxemia());
			chbReani_estimulacion.setChecked(his_atencion_embarazada
					.getReani_estimulacion());
			chbReani_ventilacion.setChecked(his_atencion_embarazada
					.getReani_ventilacion());
			chbReani_comprensiones.setChecked(his_atencion_embarazada
					.getReani_comprensiones());
			chbReani_intubacion.setChecked(his_atencion_embarazada
					.getReani_intubacion());
			tbxMedicina.setValue(his_atencion_embarazada.getMedicina());
			Radio radio12 = (Radio) rdbClasificacion_reani
					.getFellow("Clasificacion_reani"
							+ his_atencion_embarazada.getClasificacion_reani());
			radio12.setChecked(true);
			for (int i = 0; i < lbxRuptura.getItemCount(); i++) {
				Listitem listitem = lbxRuptura.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getRuptura())) {
					listitem.setSelected(true);
					i = lbxRuptura.getItemCount();
				}
			}
			for (int i = 0; i < lbxTiempo_ruptura.getItemCount(); i++) {
				Listitem listitem = lbxTiempo_ruptura.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getTiempo_ruptura())) {
					listitem.setSelected(true);
					i = lbxTiempo_ruptura.getItemCount();
				}
			}
			tbxLiquido_neo.setValue(his_atencion_embarazada.getLiquido_neo());
			for (int i = 0; i < lbxFiebre_neo.getItemCount(); i++) {
				Listitem listitem = lbxFiebre_neo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getFiebre_neo())) {
					listitem.setSelected(true);
					i = lbxFiebre_neo.getItemCount();
				}
			}
			for (int i = 0; i < lbxTiempo_neo.getItemCount(); i++) {
				Listitem listitem = lbxTiempo_neo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getTiempo_neo())) {
					listitem.setSelected(true);
					i = lbxTiempo_neo.getItemCount();
				}
			}
			tbxCoricamniotis.setValue(his_atencion_embarazada
					.getCoricamniotis());
			Radio radio17 = (Radio) rdbIntrauterina.getFellow("Intrauterina"
					+ his_atencion_embarazada.getIntrauterina());
			radio17.setChecked(true);
			for (int i = 0; i < lbxMadre20.getItemCount(); i++) {
				Listitem listitem = lbxMadre20.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_embarazada.getMadre20())) {
					listitem.setSelected(true);
					i = lbxMadre20.getItemCount();
				}
			}
			chbAlcohol_neo.setChecked(his_atencion_embarazada.getAlcohol_neo());
			chbDrogas_neo.setChecked(his_atencion_embarazada.getDrogas_neo());
			chbCigarrillo_neo.setChecked(his_atencion_embarazada
					.getCigarrillo_neo());
			Radio radio13 = (Radio) rdbRespiracion_neo
					.getFellow("Respiracion_neo"
							+ his_atencion_embarazada.getRespiracion_neo());
			radio13.setChecked(true);
			Radio radio14 = (Radio) rdbLlanto_neo.getFellow("Llanto_neo"
					+ his_atencion_embarazada.getLlanto_neo());
			radio14.setChecked(true);
			Radio radio15 = (Radio) rdbVetalidad_neo.getFellow("Vetalidad_neo"
					+ his_atencion_embarazada.getVetalidad_neo());
			radio15.setChecked(true);
			chbTaquicardia_neo.setChecked(his_atencion_embarazada
					.getTaquicardia_neo());
			chbBradicardia_neo.setChecked(his_atencion_embarazada
					.getBradicardia_neo());
			chbPalidez_neo.setChecked(his_atencion_embarazada.getPalidez_neo());
			chbCianosis_neo.setChecked(his_atencion_embarazada
					.getCianosis_neo());
			for (int i = 0; i < lbxAnomalias_congenitas.getItemCount(); i++) {
				Listitem listitem = lbxAnomalias_congenitas.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(his_atencion_embarazada
								.getAnomalias_congenitas())) {
					listitem.setSelected(true);
					i = lbxAnomalias_congenitas.getItemCount();
				}
			}
			tbxCual_anomalias.setValue(his_atencion_embarazada
					.getCual_anomalias());
			tbxLesiones.setValue(his_atencion_embarazada.getLesiones());
			tbxOtras_alter.setValue(his_atencion_embarazada.getOtras_alter());
			Radio radio16 = (Radio) rdbClasificacion_neo
					.getFellow("Clasificacion_neo"
							+ his_atencion_embarazada.getClasificacion_neo());
			radio16.setChecked(true);
			tbxAlarma.setValue(his_atencion_embarazada.getAlarma());
			tbxConsulta_control.setValue(his_atencion_embarazada
					.getConsulta_control());
			tbxMedidas_preventiva.setValue(his_atencion_embarazada
					.getMedidas_preventiva());
			tbxRecomendaciones.setValue(his_atencion_embarazada
					.getRecomendaciones());
			tbxDiagnostico.setValue(his_atencion_embarazada.getDiagnostico());
			tbxCodigo_diagnostico.setValue(his_atencion_embarazada
					.getCodigo_diagnostico());
			tbxTratamiento.setValue(his_atencion_embarazada.getTratamiento());
			tbxRecomendacion_alimentacion.setValue(his_atencion_embarazada
					.getRecomendacion_alimentacion());
			tbxEvolucion_servicio.setValue(his_atencion_embarazada
					.getEvolucion_servicio());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		His_atencion_embarazada his_atencion_embarazada = (His_atencion_embarazada) obj;
		try {
			int result = getServiceLocator()
					.getHis_atencion_embarazadaService().eliminar(
							his_atencion_embarazada);
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

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

	public void buscarEps(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getOcupacionesService().setLimit(
					"limit 25 offset 0");

			List<Administradora> list = getServiceLocator()
					.getAdministradoraService().listar(parameters);

			lbx.getItems().clear();

			for (Administradora dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNit() + ""));
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

	public void selectedEps(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_eps.setValue("");
			tbxCodigo_eps.setValue("");

		} else {
			Administradora dato = (Administradora) listitem.getValue();
			tbxNombre_eps.setValue(dato.getNombre());
			tbxCodigo_eps.setValue(dato.getCodigo());

		}
		tbxCodigo_eps.close();
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

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		listboxMun.getChildren().clear();
		Listitem listitem;
		String coddep = listboxDpto.getSelectedItem().getValue().toString();
		Map<String, Object> parameters = new HashMap();
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

	public void buscarPaciente(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", 
					"limit 25 offset 0");

			List<Paciente> list = getServiceLocator().getPacienteService()
					.listar(parameters);

			lbx.getItems().clear();

			for (Paciente dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre1() + " "
						+ dato.getNombre2()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellido1() + " "
						+ dato.getApellido2()));
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

	public void selectedPaciente(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxIdentificacion.setValue("");
			tbxNomPaciente.setValue("");
			tbxTipoIdentificacion.setValue("");
			tbxEdad_madre.setValue("");
			tbxSexo_madre.setValue("");
			dbxNacimiento.setValue(null);
			tbxCodigo_eps.setValue("");
			tbxNombre_eps.setValue("");
			tbxTelefono.setValue("");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem1.getValue().toString().equals("")) {
					listitem1.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
				if (listitem1.getValue().toString().equals("")) {
					listitem1.setSelected(true);
					i = lbxCodigo_municipio.getItemCount();
				}
			}

		} else {
			Paciente dato = (Paciente) listitem.getValue();

			Elemento elemento = new Elemento();
			elemento.setCodigo(dato.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxIdentificacion.setValue(dato.getNro_identificacion());
			tbxNomPaciente.setValue(dato.getNombre1() + " " + dato.getNombre2()
					+ " " + dato.getApellido1() + " " + dato.getApellido2());
			tbxTipoIdentificacion.setValue(dato.getTipo_identificacion());
			tbxEdad_madre.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(dato.getFecha_nacimiento()), dato
					.getUnidad_medidad(), false));
			tbxSexo_madre.setValue(elemento != null ? elemento.getDescripcion()
					: "");
			dbxNacimiento.setValue(dato.getFecha_nacimiento());
			tbxTelefono.setValue(dato.getTel_res());

			Administradora administradora = new Administradora();
			administradora.setCodigo(dato.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem1.getValue().toString()
						.equals(dato.getCodigo_dpto())) {
					listitem1.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
				if (listitem1.getValue().toString()
						.equals(dato.getCodigo_municipio())) {
					listitem1.setSelected(true);
					i = lbxCodigo_municipio.getItemCount();
				}
			}

		}

		tbxIdentificacion.close();
	}

}
