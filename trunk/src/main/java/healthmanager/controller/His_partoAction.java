/*
 * his_partoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.His_parto;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Plantilla_pyp;
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
import healthmanager.modelo.service.GeneralExtraService;

public class His_partoAction extends Borderlayout implements AfterCompose {

	private static Logger log = Logger.getLogger(His_partoAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

//	private ServiceLocator serviceLocator;

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
	private Textbox tbxCodigo_eps;
	private Textbox tbxNombre_eps;
	private Listbox lbxCodigo_dpto;
	private Listbox lbxCodigo_municipio;
	private Textbox tbxContrato;
	private Textbox tbxCodigo_contrato;

	private Bandbox tbxIdentificacion;
	private Textbox tbxNomPaciente;
	private Textbox tbxTipoIdentificacion;
	private Datebox dbxNacimiento;
	private Textbox tbxEdad;
	private Textbox tbxSexo;
	private Textbox tbxDireccion;

	private Textbox tbxConyugue;
	private Textbox tbxId_conyugue;
	private Textbox tbxEdad_conyugue;
	// private Listbox lbxEscolaridad_conyugue;
	private Bandbox tbxEscolaridad_nombre;
	private Textbox tbxEscolaridad_conyugue;
	private Textbox tbxMotivo;
	private Textbox tbxEnfermedad_actual;
	private Textbox tbxAntecedentes_familiares;
	private Radiogroup rdbPreeclampsia;
	private Radiogroup rdbHipertension;
	private Radiogroup rdbEclampsia;
	private Radiogroup rdbH1;
	private Radiogroup rdbH2;
	private Radiogroup rdbPostimadurez;
	private Radiogroup rdbRot_pre;
	private Radiogroup rdbPolihidramnios;
	private Radiogroup rdbRcui;
	private Radiogroup rdbPelvis;
	private Radiogroup rdbFeto_macrosonico;
	private Radiogroup rdbIsoinmunizacion;
	private Radiogroup rdbAo;
	private Radiogroup rdbCirc_cordon;
	private Radiogroup rdbPostparto;
	private Radiogroup rdbDiabetes;
	private Radiogroup rdbCardiopatia;
	private Radiogroup rdbAnemias;
	private Radiogroup rdbEnf_autoinmunes;
	private Radiogroup rdbEnf_renales;
	private Radiogroup rdbInf_urinaria;
	private Radiogroup rdbEpilepsia;
	private Radiogroup rdbTbc;
	private Radiogroup rdbMiomas;
	private Radiogroup rdbHb;
	private Radiogroup rdbFumadora;
	private Radiogroup rdbInf_puerperal;
	private Radiogroup rdbInfertilidad;
	private Textbox tbxMenarquia;
	private Textbox tbxCiclos;
	private Textbox tbxVida_marital;
	private Textbox tbxVida_obstetrica;
	private Listbox lbxNo_gestaciones;
	private Listbox lbxParto;
	private Listbox lbxAborto;
	private Listbox lbxCesarias;
	private Datebox dtbxFecha_um;
	private Textbox tbxUm;
	private Textbox tbxFep;
	private Listbox lbxGemerales;
	private Listbox lbxMalformados;
	private Listbox lbxNacidos_vivos;
	private Listbox lbxNacidos_muertos;
	private Listbox lbxPreterminos;
	private Checkbox chbMedico;
	private Checkbox chbEnfermera;
	private Checkbox chbAuxiliar;
	private Checkbox chbPartera;
	private Checkbox chbOtros;
	private Radiogroup rdbControlado;
	private Radiogroup rdbLugar_consulta;
	private Listbox lbxNo_consultas;
	private Checkbox chbEspecialistas;
	private Checkbox chbGeneral;
	private Checkbox chbPartera_consulta;
	private Radiogroup rdbSangrado_vaginal;
	private Radiogroup rdbCefalias;
	private Radiogroup rdbEdema;
	private Radiogroup rdbEpigastralgia;
	private Radiogroup rdbVomitos;
	private Radiogroup rdbTinutus;
	private Radiogroup rdbEscotomas;
	private Radiogroup rdbInfeccion_urinaria;
	private Radiogroup rdbInfeccion_vaginal;
	private Textbox tbxOtros_embarazo;
	private Textbox tbxCardiaca;
	private Textbox tbxRespiratoria;
	private Textbox tbxPeso;
	private Textbox tbxTalla;
	private Textbox tbxTempo;
	private Textbox tbxPresion;
	private Textbox tbxPresion2;
	private Textbox tbxInd_masa;
	private Textbox tbxSus_masa;
	private Textbox tbxCabeza_cuello;
	private Textbox tbxCardiovascular;
	private Textbox tbxMamas;
	private Textbox tbxPulmones;
	private Textbox tbxAbdomen;
	private Textbox tbxUterina;
	private Textbox tbxSituacion;
	private Textbox tbxPresentacion;
	private Textbox tbxV_presentacion;
	private Textbox tbxPosicion;
	private Textbox tbxV_posicion;
	private Textbox tbxC_uterina;
	private Textbox tbxTono;
	private Textbox tbxFcf;
	private Textbox tbxGenitales;
	private Textbox tbxDilatacion;
	private Textbox tbxBorramiento;
	private Textbox tbxEstacion;
	private Textbox tbxMembranas;
	private Textbox tbxExtremidades;
	private Textbox tbxSnc;
	private Textbox tbxObservacion;
	private Listbox lbxCodigo_consulta_pyp;
	private Listbox lbxFinalidad_cons;
	private Listbox lbxCausas_externas;
	private Listbox lbxTipo_disnostico;
	private Textbox tbxTratamiento;

	private Bandbox tbxTipo_principal;
	private Textbox tbxNomDx;
	// private Textbox tbxPlan;

	private Bandbox tbxTipo_relacionado_1;
	private Bandbox tbxTipo_relacionado_2;
	private Bandbox tbxTipo_relacionado_3;

	private Textbox tbxNomDxRelacionado_1;
	private Textbox tbxNomDxRelacionado_2;
	private Textbox tbxNomDxRelacionado_3;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();
	}

	public void loadComponents() {
		form = (Borderlayout) this.getFellow("formHis_parto");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

		tbxCodigo_historia = (Textbox) form.getFellow("tbxCodigo_historia");
		dtbxFecha_inicial = (Datebox) form.getFellow("dtbxFecha_inicial");
		tbxCodigo_eps = (Textbox) form.getFellow("tbxCodigo_eps");
		tbxNombre_eps = (Textbox) form.getFellow("tbxNombre_eps");
		lbxCodigo_dpto = (Listbox) form.getFellow("lbxCodigo_dpto");
		lbxCodigo_municipio = (Listbox) form.getFellow("lbxCodigo_municipio");
		tbxContrato = (Textbox) form.getFellow("tbxContrato");
		tbxCodigo_contrato = (Textbox) form.getFellow("tbxCodigo_contrato");

		tbxIdentificacion = (Bandbox) form.getFellow("tbxIdentificacion");
		tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
		tbxTipoIdentificacion = (Textbox) form
				.getFellow("tbxTipoIdentificacion");
		tbxEdad = (Textbox) form.getFellow("tbxEdad");
		tbxSexo = (Textbox) form.getFellow("tbxSexo");
		dbxNacimiento = (Datebox) form.getFellow("dbxNacimiento");
		tbxDireccion = (Textbox) form.getFellow("tbxDireccion");

		tbxConyugue = (Textbox) form.getFellow("tbxConyugue");
		tbxId_conyugue = (Textbox) form.getFellow("tbxId_conyugue");
		tbxEdad_conyugue = (Textbox) form.getFellow("tbxEdad_conyugue");
		tbxEscolaridad_nombre = (Bandbox) form
				.getFellow("tbxEscolaridad_nombre");
		tbxEscolaridad_conyugue = (Textbox) form
				.getFellow("tbxEscolaridad_conyugue");

		tbxMotivo = (Textbox) form.getFellow("tbxMotivo");
		tbxEnfermedad_actual = (Textbox) form.getFellow("tbxEnfermedad_actual");
		tbxAntecedentes_familiares = (Textbox) form
				.getFellow("tbxAntecedentes_familiares");
		rdbPreeclampsia = (Radiogroup) form.getFellow("rdbPreeclampsia");
		rdbHipertension = (Radiogroup) form.getFellow("rdbHipertension");
		rdbEclampsia = (Radiogroup) form.getFellow("rdbEclampsia");
		rdbH1 = (Radiogroup) form.getFellow("rdbH1");
		rdbH2 = (Radiogroup) form.getFellow("rdbH2");
		rdbPostimadurez = (Radiogroup) form.getFellow("rdbPostimadurez");
		rdbRot_pre = (Radiogroup) form.getFellow("rdbRot_pre");
		rdbPolihidramnios = (Radiogroup) form.getFellow("rdbPolihidramnios");
		rdbRcui = (Radiogroup) form.getFellow("rdbRcui");
		rdbPelvis = (Radiogroup) form.getFellow("rdbPelvis");
		rdbFeto_macrosonico = (Radiogroup) form
				.getFellow("rdbFeto_macrosonico");
		rdbIsoinmunizacion = (Radiogroup) form.getFellow("rdbIsoinmunizacion");
		rdbAo = (Radiogroup) form.getFellow("rdbAo");
		rdbCirc_cordon = (Radiogroup) form.getFellow("rdbCirc_cordon");
		rdbPostparto = (Radiogroup) form.getFellow("rdbPostparto");
		rdbDiabetes = (Radiogroup) form.getFellow("rdbDiabetes");
		rdbCardiopatia = (Radiogroup) form.getFellow("rdbCardiopatia");
		rdbAnemias = (Radiogroup) form.getFellow("rdbAnemias");
		rdbEnf_autoinmunes = (Radiogroup) form.getFellow("rdbEnf_autoinmunes");
		rdbEnf_renales = (Radiogroup) form.getFellow("rdbEnf_renales");
		rdbInf_urinaria = (Radiogroup) form.getFellow("rdbInf_urinaria");
		rdbEpilepsia = (Radiogroup) form.getFellow("rdbEpilepsia");
		rdbTbc = (Radiogroup) form.getFellow("rdbTbc");
		rdbMiomas = (Radiogroup) form.getFellow("rdbMiomas");
		rdbHb = (Radiogroup) form.getFellow("rdbHb");
		rdbFumadora = (Radiogroup) form.getFellow("rdbFumadora");
		rdbInf_puerperal = (Radiogroup) form.getFellow("rdbInf_puerperal");
		rdbInfertilidad = (Radiogroup) form.getFellow("rdbInfertilidad");
		tbxMenarquia = (Textbox) form.getFellow("tbxMenarquia");
		tbxCiclos = (Textbox) form.getFellow("tbxCiclos");
		tbxVida_marital = (Textbox) form.getFellow("tbxVida_marital");
		tbxVida_obstetrica = (Textbox) form.getFellow("tbxVida_obstetrica");
		lbxNo_gestaciones = (Listbox) form.getFellow("lbxNo_gestaciones");
		lbxParto = (Listbox) form.getFellow("lbxParto");
		lbxAborto = (Listbox) form.getFellow("lbxAborto");
		lbxCesarias = (Listbox) form.getFellow("lbxCesarias");
		dtbxFecha_um = (Datebox) form.getFellow("dtbxFecha_um");
		tbxUm = (Textbox) form.getFellow("tbxUm");
		tbxFep = (Textbox) form.getFellow("tbxFep");
		lbxGemerales = (Listbox) form.getFellow("lbxGemerales");
		lbxMalformados = (Listbox) form.getFellow("lbxMalformados");
		lbxNacidos_vivos = (Listbox) form.getFellow("lbxNacidos_vivos");
		lbxNacidos_muertos = (Listbox) form.getFellow("lbxNacidos_muertos");
		lbxPreterminos = (Listbox) form.getFellow("lbxPreterminos");
		chbMedico = (Checkbox) form.getFellow("chbMedico");
		chbEnfermera = (Checkbox) form.getFellow("chbEnfermera");
		chbAuxiliar = (Checkbox) form.getFellow("chbAuxiliar");
		chbPartera = (Checkbox) form.getFellow("chbPartera");
		chbOtros = (Checkbox) form.getFellow("chbOtros");
		rdbControlado = (Radiogroup) form.getFellow("rdbControlado");
		rdbLugar_consulta = (Radiogroup) form.getFellow("rdbLugar_consulta");
		lbxNo_consultas = (Listbox) form.getFellow("lbxNo_consultas");
		chbEspecialistas = (Checkbox) form.getFellow("chbEspecialistas");
		chbGeneral = (Checkbox) form.getFellow("chbGeneral");
		chbPartera_consulta = (Checkbox) form.getFellow("chbPartera_consulta");
		rdbSangrado_vaginal = (Radiogroup) form
				.getFellow("rdbSangrado_vaginal");
		rdbCefalias = (Radiogroup) form.getFellow("rdbCefalias");
		rdbEdema = (Radiogroup) form.getFellow("rdbEdema");
		rdbEpigastralgia = (Radiogroup) form.getFellow("rdbEpigastralgia");
		rdbVomitos = (Radiogroup) form.getFellow("rdbVomitos");
		rdbTinutus = (Radiogroup) form.getFellow("rdbTinutus");
		rdbEscotomas = (Radiogroup) form.getFellow("rdbEscotomas");
		rdbInfeccion_urinaria = (Radiogroup) form
				.getFellow("rdbInfeccion_urinaria");
		rdbInfeccion_vaginal = (Radiogroup) form
				.getFellow("rdbInfeccion_vaginal");
		tbxOtros_embarazo = (Textbox) form.getFellow("tbxOtros_embarazo");
		tbxCardiaca = (Textbox) form.getFellow("tbxCardiaca");
		tbxRespiratoria = (Textbox) form.getFellow("tbxRespiratoria");
		tbxPeso = (Textbox) form.getFellow("tbxPeso");
		tbxTalla = (Textbox) form.getFellow("tbxTalla");
		tbxTempo = (Textbox) form.getFellow("tbxTempo");
		tbxPresion = (Textbox) form.getFellow("tbxPresion");
		tbxPresion2 = (Textbox) form.getFellow("tbxPresion2");
		tbxInd_masa = (Textbox) form.getFellow("tbxInd_masa");
		tbxSus_masa = (Textbox) form.getFellow("tbxSus_masa");
		tbxCabeza_cuello = (Textbox) form.getFellow("tbxCabeza_cuello");
		tbxCardiovascular = (Textbox) form.getFellow("tbxCardiovascular");
		tbxMamas = (Textbox) form.getFellow("tbxMamas");
		tbxPulmones = (Textbox) form.getFellow("tbxPulmones");
		tbxAbdomen = (Textbox) form.getFellow("tbxAbdomen");
		tbxUterina = (Textbox) form.getFellow("tbxUterina");
		tbxSituacion = (Textbox) form.getFellow("tbxSituacion");
		tbxPresentacion = (Textbox) form.getFellow("tbxPresentacion");
		tbxV_presentacion = (Textbox) form.getFellow("tbxV_presentacion");
		tbxPosicion = (Textbox) form.getFellow("tbxPosicion");
		tbxV_posicion = (Textbox) form.getFellow("tbxV_posicion");
		tbxC_uterina = (Textbox) form.getFellow("tbxC_uterina");
		tbxTono = (Textbox) form.getFellow("tbxTono");
		tbxFcf = (Textbox) form.getFellow("tbxFcf");
		tbxGenitales = (Textbox) form.getFellow("tbxGenitales");
		tbxDilatacion = (Textbox) form.getFellow("tbxDilatacion");
		tbxBorramiento = (Textbox) form.getFellow("tbxBorramiento");
		tbxEstacion = (Textbox) form.getFellow("tbxEstacion");
		tbxMembranas = (Textbox) form.getFellow("tbxMembranas");
		tbxExtremidades = (Textbox) form.getFellow("tbxExtremidades");
		tbxSnc = (Textbox) form.getFellow("tbxSnc");
		tbxObservacion = (Textbox) form.getFellow("tbxObservacion");
		lbxCodigo_consulta_pyp = (Listbox) form
				.getFellow("lbxCodigo_consulta_pyp");
		lbxFinalidad_cons = (Listbox) form.getFellow("lbxFinalidad_cons");
		lbxCausas_externas = (Listbox) form.getFellow("lbxCausas_externas");
		lbxTipo_disnostico = (Listbox) form.getFellow("lbxTipo_disnostico");
		tbxTipo_principal = (Bandbox) form.getFellow("tbxTipo_principal");
		tbxNomDx = (Textbox) form.getFellow("tbxNomDx");
		tbxTipo_relacionado_1 = (Bandbox) form
				.getFellow("tbxTipo_relacionado_1");
		tbxTipo_relacionado_2 = (Bandbox) form
				.getFellow("tbxTipo_relacionado_2");
		tbxTipo_relacionado_3 = (Bandbox) form
				.getFellow("tbxTipo_relacionado_3");
		tbxNomDxRelacionado_1 = (Textbox) form
				.getFellow("tbxNomDxRelacionado_1");
		tbxNomDxRelacionado_2 = (Textbox) form
				.getFellow("tbxNomDxRelacionado_2");
		tbxNomDxRelacionado_3 = (Textbox) form
				.getFellow("tbxNomDxRelacionado_3");

		tbxTratamiento = (Textbox) form.getFellow("tbxTratamiento");

	}

	public void initHis_parto() throws Exception {
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

		// listarElementoListbox(lbxEscolaridad_conyugue,true);
		listarElementoListbox(lbxNo_gestaciones, false);
		listarElementoListbox(lbxParto, false);
		listarElementoListbox(lbxAborto, false);
		listarElementoListbox(lbxCesarias, false);
		listarElementoListbox(lbxGemerales, false);
		listarElementoListbox(lbxMalformados, false);
		listarElementoListbox(lbxNacidos_vivos, false);
		listarElementoListbox(lbxNacidos_muertos, false);
		listarElementoListbox(lbxPreterminos, false);
		listarElementoListbox(lbxNo_consultas, false);

		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar("ante_familiares");
		/* tipos de diasnosticos */
		elementos = this.getServiceLocator().getElementoService()
				.listar("tipo_diagnostico");
		listarElementoListboxFromType(lbxTipo_disnostico, true, elementos,
				false);

		/* listar finalidad */
		elementos = this.getServiceLocator().getElementoService()
				.listar("finalidad_cons");
		listarElementoListboxFromType(lbxFinalidad_cons, false, elementos,
				false);

		/* causas externas */
		elementos = this.getServiceLocator().getElementoService()
				.listar("causa_externa");
		listarElementoListboxFromType(lbxCausas_externas, true, elementos,
				false);

	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
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

		tbxIdentificacion
				.setStyle("text-transform:uppercase;background-color:white");
		lbxTipo_disnostico
				.setStyle("text-transform:uppercase;background-color:white");
		lbxCodigo_consulta_pyp
				.setStyle("text-transform:uppercase;background-color:white");

		String mensaje = "Los campos marcados con (*) son obligatorios";

		boolean valida = true;

		if (tbxIdentificacion.getText().trim().equals("")) {
			tbxIdentificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (lbxTipo_disnostico.getSelectedIndex() == 0) {
			lbxTipo_disnostico
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (!lbxFinalidad_cons.getSelectedItem().getValue().toString()
				.equalsIgnoreCase("10")
				&& lbxCodigo_consulta_pyp.getSelectedIndex() == 0) {
			lbxCodigo_consulta_pyp
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (tbxTipo_principal.getText().trim().equals("")) {
			mensaje = "Debe digitar la impresion diagnostica";
			valida = false;
		} else if (vaidarIgualdad(tbxTipo_principal.getText(),
				tbxTipo_relacionado_1.getText(),
				tbxTipo_relacionado_2.getText(),
				tbxTipo_relacionado_3.getText())) {
			mensaje = "no se puede repetir la impresion diagnostica";
			valida = false;
		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	private boolean vaidarIgualdad(String... in) {
		Map map = new HashMap();
		for (String inO : in) {
			if (!inO.trim().isEmpty()) {
				if (map.containsKey(inO)) {
					return true;
				}
				map.put(inO, inO);
			}
		}
		return false;
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

			getServiceLocator().getHis_partoService().setLimit(
					"limit 25 offset 0");

			List<His_parto> lista_datos = getServiceLocator()
					.getHis_partoService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (His_parto his_parto : lista_datos) {
				rowsResultado.appendChild(crearFilas(his_parto, this));
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

		final His_parto his_parto = (His_parto) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(his_parto.getCodigo_historia() + ""));
		fila.appendChild(new Label(his_parto.getIdentificacion() + ""));
		fila.appendChild(new Label(his_parto.getFecha_inicial() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {

				mostrarDatos(his_parto);
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
									eliminarDatos(his_parto);
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

				His_parto his_parto = new His_parto();
				his_parto.setCodigo_empresa(empresa.getCodigo_empresa());
				his_parto.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				his_parto.setCodigo_historia(tbxCodigo_historia.getValue());
				his_parto.setIdentificacion(tbxIdentificacion.getValue());
				his_parto.setFecha_inicial(new Timestamp(dtbxFecha_inicial
						.getValue().getTime()));
				his_parto.setConyugue(tbxConyugue.getValue());
				his_parto.setId_conyugue(tbxId_conyugue.getValue());
				his_parto.setEdad_conyugue(tbxEdad_conyugue.getValue());
				his_parto.setEscolaridad_conyugue(tbxEscolaridad_conyugue
						.getValue());
				his_parto.setMotivo(tbxMotivo.getValue());
				his_parto.setEnfermedad_actual(tbxEnfermedad_actual.getValue());
				his_parto.setAntecedentes_familiares(tbxAntecedentes_familiares
						.getValue());
				his_parto.setPreeclampsia(rdbPreeclampsia.getSelectedItem()
						.getValue().toString());
				his_parto.setHipertension(rdbHipertension.getSelectedItem()
						.getValue().toString());
				his_parto.setEclampsia(rdbEclampsia.getSelectedItem()
						.getValue().toString());
				his_parto.setH1(rdbH1.getSelectedItem().getValue().toString());
				his_parto.setH2(rdbH2.getSelectedItem().getValue().toString());
				his_parto.setPostimadurez(rdbPostimadurez.getSelectedItem()
						.getValue().toString());
				his_parto.setRot_pre(rdbRot_pre.getSelectedItem().getValue()
						.toString());
				his_parto.setPolihidramnios(rdbPolihidramnios.getSelectedItem()
						.getValue().toString());
				his_parto.setRcui(rdbRcui.getSelectedItem().getValue()
						.toString());
				his_parto.setPelvis(rdbPelvis.getSelectedItem().getValue()
						.toString());
				his_parto.setFeto_macrosonico(rdbFeto_macrosonico
						.getSelectedItem().getValue().toString());
				his_parto.setIsoinmunizacion(rdbIsoinmunizacion
						.getSelectedItem().getValue().toString());
				his_parto.setAo(rdbAo.getSelectedItem().getValue().toString());
				his_parto.setCirc_cordon(rdbCirc_cordon.getSelectedItem()
						.getValue().toString());
				his_parto.setPostparto(rdbPostparto.getSelectedItem()
						.getValue().toString());
				his_parto.setDiabetes(rdbDiabetes.getSelectedItem().getValue()
						.toString());
				his_parto.setCardiopatia(rdbCardiopatia.getSelectedItem()
						.getValue().toString());
				his_parto.setAnemias(rdbAnemias.getSelectedItem().getValue()
						.toString());
				his_parto.setEnf_autoinmunes(rdbEnf_autoinmunes
						.getSelectedItem().getValue().toString());
				his_parto.setEnf_renales(rdbEnf_renales.getSelectedItem()
						.getValue().toString());
				his_parto.setInf_urinaria(rdbInf_urinaria.getSelectedItem()
						.getValue().toString());
				his_parto.setEpilepsia(rdbEpilepsia.getSelectedItem()
						.getValue().toString());
				his_parto
						.setTbc(rdbTbc.getSelectedItem().getValue().toString());
				his_parto.setMiomas(rdbMiomas.getSelectedItem().getValue()
						.toString());
				his_parto.setHb(rdbHb.getSelectedItem().getValue().toString());
				his_parto.setFumadora(rdbFumadora.getSelectedItem().getValue()
						.toString());
				his_parto.setInf_puerperal(rdbInf_puerperal.getSelectedItem()
						.getValue().toString());
				his_parto.setInfertilidad(rdbInfertilidad.getSelectedItem()
						.getValue().toString());
				his_parto.setMenarquia(tbxMenarquia.getValue());
				his_parto.setCiclos(tbxCiclos.getValue());
				his_parto.setVida_marital(tbxVida_marital.getValue());
				his_parto.setVida_obstetrica(tbxVida_obstetrica.getValue());
				his_parto.setNo_gestaciones(lbxNo_gestaciones.getSelectedItem()
						.getValue().toString());
				his_parto.setParto(lbxParto.getSelectedItem().getValue()
						.toString());
				his_parto.setAborto(lbxAborto.getSelectedItem().getValue()
						.toString());
				his_parto.setCesarias(lbxCesarias.getSelectedItem().getValue()
						.toString());
				his_parto.setFecha_um(new Timestamp(dtbxFecha_um.getValue()
						.getTime()));
				his_parto.setUm(tbxUm.getValue());
				his_parto.setFep(tbxFep.getValue());
				his_parto.setGemerales(lbxGemerales.getSelectedItem()
						.getValue().toString());
				his_parto.setMalformados(lbxMalformados.getSelectedItem()
						.getValue().toString());
				his_parto.setNacidos_vivos(lbxNacidos_vivos.getSelectedItem()
						.getValue().toString());
				his_parto.setNacidos_muertos(lbxNacidos_muertos
						.getSelectedItem().getValue().toString());
				his_parto.setPreterminos(lbxPreterminos.getSelectedItem()
						.getValue().toString());
				his_parto.setMedico(chbMedico.isChecked());
				his_parto.setEnfermera(chbEnfermera.isChecked());
				his_parto.setAuxiliar(chbAuxiliar.isChecked());
				his_parto.setPartera(chbPartera.isChecked());
				his_parto.setOtros(chbOtros.isChecked());
				his_parto.setControlado(rdbControlado.getSelectedItem()
						.getValue().toString());
				his_parto.setLugar_consulta(rdbLugar_consulta.getSelectedItem()
						.getValue().toString());
				his_parto.setNo_consultas(lbxNo_consultas.getSelectedItem()
						.getValue().toString());
				his_parto.setEspecialistas(chbEspecialistas.isChecked());
				his_parto.setGeneral(chbGeneral.isChecked());
				his_parto.setPartera_consulta(chbPartera_consulta.isChecked());
				his_parto.setSangrado_vaginal(rdbSangrado_vaginal
						.getSelectedItem().getValue().toString());
				his_parto.setCefalias(rdbCefalias.getSelectedItem().getValue()
						.toString());
				his_parto.setEdema(rdbEdema.getSelectedItem().getValue()
						.toString());
				his_parto.setEpigastralgia(rdbEpigastralgia.getSelectedItem()
						.getValue().toString());
				his_parto.setVomitos(rdbVomitos.getSelectedItem().getValue()
						.toString());
				his_parto.setTinutus(rdbTinutus.getSelectedItem().getValue()
						.toString());
				his_parto.setEscotomas(rdbEscotomas.getSelectedItem()
						.getValue().toString());
				his_parto.setInfeccion_urinaria(rdbInfeccion_urinaria
						.getSelectedItem().getValue().toString());
				his_parto.setInfeccion_vaginal(rdbInfeccion_vaginal
						.getSelectedItem().getValue().toString());
				his_parto.setOtros_embarazo(tbxOtros_embarazo.getValue());
				his_parto.setCardiaca(tbxCardiaca.getValue());
				his_parto.setRespiratoria(tbxRespiratoria.getValue());
				his_parto.setPeso(tbxPeso.getValue());
				his_parto.setTalla(tbxTalla.getValue());
				his_parto.setTempo(tbxTempo.getValue());
				his_parto.setPresion(tbxPresion.getValue());
				his_parto.setPresion2(tbxPresion2.getValue());
				his_parto.setInd_masa(tbxInd_masa.getValue());
				his_parto.setSus_masa(tbxSus_masa.getValue());
				his_parto.setCabeza_cuello(tbxCabeza_cuello.getValue());
				his_parto.setCardiovascular(tbxCardiovascular.getValue());
				his_parto.setMamas(tbxMamas.getValue());
				his_parto.setPulmones(tbxPulmones.getValue());
				his_parto.setAbdomen(tbxAbdomen.getValue());
				his_parto.setUterina(tbxUterina.getValue());
				his_parto.setSituacion(tbxSituacion.getValue());
				his_parto.setPresentacion(tbxPresentacion.getValue());
				his_parto.setV_presentacion(tbxV_presentacion.getValue());
				his_parto.setPosicion(tbxPosicion.getValue());
				his_parto.setV_posicion(tbxV_posicion.getValue());
				his_parto.setC_uterina(tbxC_uterina.getValue());
				his_parto.setTono(tbxTono.getValue());
				his_parto.setFcf(tbxFcf.getValue());
				his_parto.setGenitales(tbxGenitales.getValue());
				his_parto.setDilatacion(tbxDilatacion.getValue());
				his_parto.setBorramiento(tbxBorramiento.getValue());
				his_parto.setEstacion(tbxEstacion.getValue());
				his_parto.setMembranas(tbxMembranas.getValue());
				his_parto.setExtremidades(tbxExtremidades.getValue());
				his_parto.setSnc(tbxSnc.getValue());
				his_parto.setObservacion(tbxObservacion.getValue());
				his_parto.setCodigo_consulta_pyp(lbxCodigo_consulta_pyp
						.getSelectedItem().getValue().toString());
				his_parto.setFinalidad_cons(lbxFinalidad_cons.getSelectedItem()
						.getValue().toString());
				his_parto.setCausas_externas(lbxCausas_externas
						.getSelectedItem().getValue().toString());
				his_parto.setTipo_disnostico(lbxTipo_disnostico
						.getSelectedItem().getValue().toString());
				his_parto.setTipo_principal(tbxTipo_principal.getValue());
				his_parto.setTipo_relacionado_1(tbxTipo_relacionado_1
						.getValue());
				his_parto.setTipo_relacionado_2(tbxTipo_relacionado_2
						.getValue());
				his_parto.setTipo_relacionado_3(tbxTipo_relacionado_3
						.getValue());
				his_parto.setTratamiento(tbxTratamiento.getValue());

				datos.put("codigo_historia", his_parto);
				datos.put("accion", tbxAccion.getText());

				his_parto = getServiceLocator().getHis_partoService().guardar(
						datos);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					accionForm(true, "modificar");
				}

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
		His_parto his_parto = (His_parto) obj;
		try {
			tbxCodigo_historia.setValue(his_parto.getCodigo_historia());
			dtbxFecha_inicial.setValue(his_parto.getFecha_inicial());

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(his_parto.getCodigo_empresa());
			paciente.setCodigo_sucursal(his_parto.getCodigo_sucursal());
			paciente.setNro_identificacion(his_parto.getIdentificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			Elemento elemento = new Elemento();
			elemento.setCodigo(paciente.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxIdentificacion.setValue(his_parto.getIdentificacion());
			tbxNomPaciente.setValue((paciente != null ? paciente.getNombre1()
					+ " " + paciente.getApellido1() : ""));
			tbxTipoIdentificacion.setValue((paciente != null ? paciente
					.getTipo_identificacion() : ""));
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
					paciente.getUnidad_medidad(), false));
			tbxSexo.setValue((elemento != null ? elemento.getDescripcion() : ""));
			dbxNacimiento.setValue(paciente.getFecha_nacimiento());
			tbxDireccion.setValue(paciente.getDireccion());

			Administradora administradora = new Administradora();
			administradora.setCodigo(paciente.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(his_parto.getCodigo_empresa());
			contratos.setCodigo_sucursal(his_parto.getCodigo_sucursal());
			contratos.setCodigo_administradora(paciente.getCodigo_administradora());
//			contratos.setId_plan(paciente.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(contratos);

			tbxCodigo_contrato.setValue(contratos != null ? contratos.getId_plan()
					: "");
			tbxContrato.setValue(contratos != null ? contratos.getNombre() : "");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(paciente.getCodigo_dpto())) {
					listitem.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

			tbxConyugue.setValue(his_parto.getConyugue());
			tbxId_conyugue.setValue(his_parto.getId_conyugue());
			tbxEdad_conyugue.setValue(his_parto.getEdad_conyugue());

			Nivel_educativo nivel_educativo = new Nivel_educativo();
			nivel_educativo.setId(his_parto.getEscolaridad_conyugue());
			nivel_educativo = getServiceLocator().getNivel_educativoService()
					.consultar(nivel_educativo);
			tbxEscolaridad_nombre
					.setValue(nivel_educativo != null ? nivel_educativo
							.getNombre() : "");
			tbxEscolaridad_conyugue
					.setValue(nivel_educativo != null ? nivel_educativo.getId()
							: "");

			tbxMotivo.setValue(his_parto.getMotivo());
			tbxEnfermedad_actual.setValue(his_parto.getEnfermedad_actual());
			tbxAntecedentes_familiares.setValue(his_parto
					.getAntecedentes_familiares());
			Radio radio = (Radio) rdbPreeclampsia.getFellow("Preeclampsia"
					+ his_parto.getPreeclampsia());
			radio.setChecked(true);
			Radio radio1 = (Radio) rdbHipertension.getFellow("Hipertension"
					+ his_parto.getHipertension());
			radio1.setChecked(true);
			Radio radio2 = (Radio) rdbEclampsia.getFellow("Eclampsia"
					+ his_parto.getEclampsia());
			radio2.setChecked(true);
			Radio radio3 = (Radio) rdbH1.getFellow("H1" + his_parto.getH1());
			radio3.setChecked(true);
			Radio radio4 = (Radio) rdbH2.getFellow("H2" + his_parto.getH2());
			radio4.setChecked(true);
			Radio radio5 = (Radio) rdbPostimadurez.getFellow("Postimadurez"
					+ his_parto.getPostimadurez());
			radio5.setChecked(true);
			Radio radio6 = (Radio) rdbRot_pre.getFellow("Rot_pre"
					+ his_parto.getRot_pre());
			radio6.setChecked(true);
			Radio radio7 = (Radio) rdbPolihidramnios.getFellow("Polihidramnios"
					+ his_parto.getPolihidramnios());
			radio7.setChecked(true);
			Radio radio8 = (Radio) rdbRcui.getFellow("Rcui"
					+ his_parto.getRcui());
			radio8.setChecked(true);
			Radio radio9 = (Radio) rdbPelvis.getFellow("Pelvis"
					+ his_parto.getPelvis());
			radio9.setChecked(true);
			Radio radio10 = (Radio) rdbFeto_macrosonico
					.getFellow("Feto_macrosonico"
							+ his_parto.getFeto_macrosonico());
			radio10.setChecked(true);
			Radio radio11 = (Radio) rdbIsoinmunizacion
					.getFellow("Isoinmunizacion"
							+ his_parto.getIsoinmunizacion());
			radio11.setChecked(true);
			Radio radio12 = (Radio) rdbAo.getFellow("Ao" + his_parto.getAo());
			radio12.setChecked(true);
			Radio radio13 = (Radio) rdbCirc_cordon.getFellow("Circ_cordon"
					+ his_parto.getCirc_cordon());
			radio13.setChecked(true);
			Radio radio14 = (Radio) rdbPostparto.getFellow("Postparto"
					+ his_parto.getPostparto());
			radio14.setChecked(true);
			Radio radio15 = (Radio) rdbDiabetes.getFellow("Diabetes"
					+ his_parto.getDiabetes());
			radio15.setChecked(true);
			Radio radio16 = (Radio) rdbCardiopatia.getFellow("Cardiopatia"
					+ his_parto.getCardiopatia());
			radio16.setChecked(true);
			Radio radio17 = (Radio) rdbAnemias.getFellow("Anemias"
					+ his_parto.getAnemias());
			radio17.setChecked(true);
			Radio radio18 = (Radio) rdbEnf_autoinmunes
					.getFellow("Enf_autoinmunes"
							+ his_parto.getEnf_autoinmunes());
			radio18.setChecked(true);
			Radio radio19 = (Radio) rdbEnf_renales.getFellow("Enf_renales"
					+ his_parto.getEnf_renales());
			radio19.setChecked(true);
			Radio radio20 = (Radio) rdbInf_urinaria.getFellow("Inf_urinaria"
					+ his_parto.getInf_urinaria());
			radio20.setChecked(true);
			Radio radio21 = (Radio) rdbEpilepsia.getFellow("Epilepsia"
					+ his_parto.getEpilepsia());
			radio21.setChecked(true);
			Radio radio22 = (Radio) rdbTbc
					.getFellow("Tbc" + his_parto.getTbc());
			radio22.setChecked(true);
			Radio radio23 = (Radio) rdbMiomas.getFellow("Miomas"
					+ his_parto.getMiomas());
			radio23.setChecked(true);
			Radio radio24 = (Radio) rdbHb.getFellow("Hb" + his_parto.getHb());
			radio24.setChecked(true);
			Radio radio25 = (Radio) rdbFumadora.getFellow("Fumadora"
					+ his_parto.getFumadora());
			radio25.setChecked(true);
			Radio radio26 = (Radio) rdbInf_puerperal.getFellow("Inf_puerperal"
					+ his_parto.getInf_puerperal());
			radio26.setChecked(true);
			Radio radio27 = (Radio) rdbInfertilidad.getFellow("Infertilidad"
					+ his_parto.getInfertilidad());
			radio27.setChecked(true);
			tbxMenarquia.setValue(his_parto.getMenarquia());
			tbxCiclos.setValue(his_parto.getCiclos());
			tbxVida_marital.setValue(his_parto.getVida_marital());
			tbxVida_obstetrica.setValue(his_parto.getVida_obstetrica());
			for (int i = 0; i < lbxNo_gestaciones.getItemCount(); i++) {
				Listitem listitem = lbxNo_gestaciones.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getNo_gestaciones())) {
					listitem.setSelected(true);
					i = lbxNo_gestaciones.getItemCount();
				}
			}
			for (int i = 0; i < lbxParto.getItemCount(); i++) {
				Listitem listitem = lbxParto.getItemAtIndex(i);
				if (listitem.getValue().toString().equals(his_parto.getParto())) {
					listitem.setSelected(true);
					i = lbxParto.getItemCount();
				}
			}
			for (int i = 0; i < lbxAborto.getItemCount(); i++) {
				Listitem listitem = lbxAborto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getAborto())) {
					listitem.setSelected(true);
					i = lbxAborto.getItemCount();
				}
			}
			for (int i = 0; i < lbxCesarias.getItemCount(); i++) {
				Listitem listitem = lbxCesarias.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getCesarias())) {
					listitem.setSelected(true);
					i = lbxCesarias.getItemCount();
				}
			}
			dtbxFecha_um.setValue(his_parto.getFecha_um());
			tbxUm.setValue(his_parto.getUm());
			tbxFep.setValue(his_parto.getFep());
			for (int i = 0; i < lbxGemerales.getItemCount(); i++) {
				Listitem listitem = lbxGemerales.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getGemerales())) {
					listitem.setSelected(true);
					i = lbxGemerales.getItemCount();
				}
			}
			for (int i = 0; i < lbxMalformados.getItemCount(); i++) {
				Listitem listitem = lbxMalformados.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getMalformados())) {
					listitem.setSelected(true);
					i = lbxMalformados.getItemCount();
				}
			}
			for (int i = 0; i < lbxNacidos_vivos.getItemCount(); i++) {
				Listitem listitem = lbxNacidos_vivos.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getNacidos_vivos())) {
					listitem.setSelected(true);
					i = lbxNacidos_vivos.getItemCount();
				}
			}
			for (int i = 0; i < lbxNacidos_muertos.getItemCount(); i++) {
				Listitem listitem = lbxNacidos_muertos.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getNacidos_muertos())) {
					listitem.setSelected(true);
					i = lbxNacidos_muertos.getItemCount();
				}
			}
			for (int i = 0; i < lbxPreterminos.getItemCount(); i++) {
				Listitem listitem = lbxPreterminos.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getPreterminos())) {
					listitem.setSelected(true);
					i = lbxPreterminos.getItemCount();
				}
			}
			chbMedico.setChecked(his_parto.getMedico());
			chbEnfermera.setChecked(his_parto.getEnfermera());
			chbAuxiliar.setChecked(his_parto.getAuxiliar());
			chbPartera.setChecked(his_parto.getPartera());
			chbOtros.setChecked(his_parto.getOtros());
			Radio radio28 = (Radio) rdbControlado.getFellow("Controlado"
					+ his_parto.getControlado());
			radio28.setChecked(true);
			Radio radio29 = (Radio) rdbLugar_consulta
					.getFellow("Lugar_consulta" + his_parto.getLugar_consulta());
			radio29.setChecked(true);
			for (int i = 0; i < lbxNo_consultas.getItemCount(); i++) {
				Listitem listitem = lbxNo_consultas.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getNo_consultas())) {
					listitem.setSelected(true);
					i = lbxNo_consultas.getItemCount();
				}
			}
			chbEspecialistas.setChecked(his_parto.getEspecialistas());
			chbGeneral.setChecked(his_parto.getGeneral());
			chbPartera_consulta.setChecked(his_parto.getPartera_consulta());
			Radio radio30 = (Radio) rdbSangrado_vaginal
					.getFellow("Sangrado_vaginal"
							+ his_parto.getSangrado_vaginal());
			radio30.setChecked(true);
			Radio radio31 = (Radio) rdbCefalias.getFellow("Cefalias"
					+ his_parto.getCefalias());
			radio31.setChecked(true);
			Radio radio32 = (Radio) rdbEdema.getFellow("Edema"
					+ his_parto.getEdema());
			radio32.setChecked(true);
			Radio radio33 = (Radio) rdbEpigastralgia.getFellow("Epigastralgia"
					+ his_parto.getEpigastralgia());
			radio33.setChecked(true);
			Radio radio34 = (Radio) rdbVomitos.getFellow("Vomitos"
					+ his_parto.getVomitos());
			radio34.setChecked(true);
			Radio radio35 = (Radio) rdbTinutus.getFellow("Tinutus"
					+ his_parto.getTinutus());
			radio35.setChecked(true);
			Radio radio36 = (Radio) rdbEscotomas.getFellow("Escotomas"
					+ his_parto.getEscotomas());
			radio36.setChecked(true);
			Radio radio37 = (Radio) rdbInfeccion_urinaria
					.getFellow("Infeccion_urinaria"
							+ his_parto.getInfeccion_urinaria());
			radio37.setChecked(true);
			Radio radio38 = (Radio) rdbInfeccion_vaginal
					.getFellow("Infeccion_vaginal"
							+ his_parto.getInfeccion_vaginal());
			radio38.setChecked(true);
			tbxOtros_embarazo.setValue(his_parto.getOtros_embarazo());
			tbxCardiaca.setValue(his_parto.getCardiaca());
			tbxRespiratoria.setValue(his_parto.getRespiratoria());
			tbxPeso.setValue(his_parto.getPeso());
			tbxTalla.setValue(his_parto.getTalla());
			tbxTempo.setValue(his_parto.getTempo());
			tbxPresion.setValue(his_parto.getPresion());
			tbxPresion2.setValue(his_parto.getPresion2());
			tbxInd_masa.setValue(his_parto.getInd_masa());
			tbxSus_masa.setValue(his_parto.getSus_masa());
			tbxCabeza_cuello.setValue(his_parto.getCabeza_cuello());
			tbxCardiovascular.setValue(his_parto.getCardiovascular());
			tbxMamas.setValue(his_parto.getMamas());
			tbxPulmones.setValue(his_parto.getPulmones());
			tbxAbdomen.setValue(his_parto.getAbdomen());
			tbxUterina.setValue(his_parto.getUterina());
			tbxSituacion.setValue(his_parto.getSituacion());
			tbxPresentacion.setValue(his_parto.getPresentacion());
			tbxV_presentacion.setValue(his_parto.getV_presentacion());
			tbxPosicion.setValue(his_parto.getPosicion());
			tbxV_posicion.setValue(his_parto.getV_posicion());
			tbxC_uterina.setValue(his_parto.getC_uterina());
			tbxTono.setValue(his_parto.getTono());
			tbxFcf.setValue(his_parto.getFcf());
			tbxGenitales.setValue(his_parto.getGenitales());
			tbxDilatacion.setValue(his_parto.getDilatacion());
			tbxBorramiento.setValue(his_parto.getBorramiento());
			tbxEstacion.setValue(his_parto.getEstacion());
			tbxMembranas.setValue(his_parto.getMembranas());
			tbxExtremidades.setValue(his_parto.getExtremidades());
			tbxSnc.setValue(his_parto.getSnc());
			tbxObservacion.setValue(his_parto.getObservacion());
			for (int i = 0; i < lbxFinalidad_cons.getItemCount(); i++) {
				Listitem listitem = lbxFinalidad_cons.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getFinalidad_cons())) {
					listitem.setSelected(true);
					i = lbxFinalidad_cons.getItemCount();
				}
			}
			seleccionarProgramaPyp();

			for (int i = 0; i < lbxCausas_externas.getItemCount(); i++) {
				Listitem listitem = lbxCausas_externas.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getCausas_externas())) {
					listitem.setSelected(true);
					i = lbxCausas_externas.getItemCount();
				}
			}
			for (int i = 0; i < lbxCodigo_consulta_pyp.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_consulta_pyp.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getCodigo_consulta_pyp())) {
					listitem.setSelected(true);
					i = lbxCodigo_consulta_pyp.getItemCount();
				}
			}
			for (int i = 0; i < lbxTipo_disnostico.getItemCount(); i++) {
				Listitem listitem = lbxTipo_disnostico.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_parto.getTipo_disnostico())) {
					listitem.setSelected(true);
					i = lbxTipo_disnostico.getItemCount();
				}
			}

			Cie cie = new Cie();
			cie.setCodigo(his_parto.getTipo_principal());
			//log.info("antes: " + cie);
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
			//log.info("despues: " + cie);
			tbxTipo_principal.setValue(his_parto.getTipo_principal());
			tbxNomDx.setValue((cie != null ? cie.getNombre() : ""));

			/* relacionado 1 */
			cie = new Cie();
			cie.setCodigo(his_parto.getTipo_relacionado_1());
			//log.info("antes: " + cie);
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
			//log.info("despues: " + cie);

			tbxTipo_relacionado_1.setValue(his_parto.getTipo_relacionado_1());
			tbxNomDxRelacionado_1
					.setValue((cie != null ? cie.getNombre() : ""));

			/* relacionado 2 */
			cie = new Cie();
			cie.setCodigo(his_parto.getTipo_relacionado_2());
			//log.info("antes: " + cie);
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
			//log.info("despues: " + cie);

			tbxTipo_relacionado_2.setValue(his_parto.getTipo_relacionado_2());
			tbxNomDxRelacionado_2
					.setValue((cie != null ? cie.getNombre() : ""));

			/* relacionado 3 */
			cie = new Cie();
			cie.setCodigo(his_parto.getTipo_relacionado_3());
			//log.info("antes: " + cie);
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
			//log.info("despues: " + cie);

			tbxTipo_relacionado_3.setValue(his_parto.getTipo_relacionado_3());
			tbxNomDxRelacionado_3
					.setValue((cie != null ? cie.getNombre() : ""));

			tbxTratamiento.setValue(his_parto.getTratamiento());

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
		His_parto his_parto = (His_parto) obj;
		try {
			int result = getServiceLocator().getHis_partoService().eliminar(
					his_parto);
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
			tbxEdad.setValue("");
			tbxSexo.setValue("");
			dbxNacimiento.setValue(null);
			tbxCodigo_eps.setValue("");
			tbxNombre_eps.setValue("");
			tbxDireccion.setValue("");
			tbxCodigo_contrato.setValue("");
			tbxContrato.setValue("");

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
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(dato.getFecha_nacimiento()), dato
					.getUnidad_medidad(), false));
			tbxSexo.setValue(elemento != null ? elemento.getDescripcion() : "");
			dbxNacimiento.setValue(dato.getFecha_nacimiento());
			tbxDireccion.setValue(dato.getDireccion());

			Administradora administradora = new Administradora();
			administradora.setCodigo(dato.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(dato.getCodigo_empresa());
			contratos.setCodigo_sucursal(dato.getCodigo_sucursal());
			contratos.setCodigo_administradora(dato.getCodigo_administradora());
//			contratos.setId_plan(dato.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(contratos);

			tbxCodigo_contrato.setValue(contratos != null ? contratos.getId_plan()
					: "");
			tbxContrato.setValue(contratos != null ? contratos.getNombre() : "");

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

	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
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

	public void seleccionarProgramaPyp() {
		String codigo_finalidad = ""
				+ lbxFinalidad_cons.getSelectedItem().getValue();
		if (!codigo_finalidad.trim().isEmpty()) {
			Map<String, Object> parameters = new HashMap();
			parameters.put("area_intervencion", codigo_finalidad);
			List<Plantilla_pyp> plantillaPyps = getServiceLocator()
					.getPlantillaPypService().listar(parameters);
			lbxCodigo_consulta_pyp.getChildren().clear();
			Listitem listitem;
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			lbxCodigo_consulta_pyp.appendChild(listitem);

			for (Plantilla_pyp plantillaPyp : plantillaPyps) {
				listitem = new Listitem();
				listitem.setValue(plantillaPyp.getCodigo_pdc());
				listitem.setLabel(plantillaPyp.getCodigo_pdc() + "-"
						+ plantillaPyp.getNombre_pcd());
				lbxCodigo_consulta_pyp.appendChild(listitem);
			}
			if (lbxCodigo_consulta_pyp.getItemCount() > 0) {
				lbxCodigo_consulta_pyp.setSelectedIndex(0);
			}
		} else {
			initPypList();
		}

	}

	private void initPypList() {
		lbxCodigo_consulta_pyp.getChildren().clear();
		Listitem listitem;
		listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("-- seleccione --");
		lbxCodigo_consulta_pyp.appendChild(listitem);

		if (lbxCodigo_consulta_pyp.getItemCount() > 0) {
			lbxCodigo_consulta_pyp.setSelectedIndex(0);
		}
	}

	public void listarElementoListboxFromType(Listbox listbox, boolean select,
			List<Elemento> elementos, boolean selectEnd) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			if (!selectEnd) {
				listbox.setSelectedIndex(0);
			} else {
				listbox.setSelectedIndex(listbox.getChildren().size() - 1);
			}
		}
	}

	public void buscarCodigo_educativo(String value, Listbox lbx)
			throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getNivel_educativoService().setLimit(
					"limit 25 offset 0");

			List<Nivel_educativo> list = getServiceLocator()
					.getNivel_educativoService().listar(parameters);

			lbx.getItems().clear();

			for (Nivel_educativo dato : list) {

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

	public void selectedCodigo_educativo(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxEscolaridad_nombre.setValue("");
			tbxEscolaridad_conyugue.setValue("");

		} else {
			Nivel_educativo dato = (Nivel_educativo) listitem.getValue();
			tbxEscolaridad_nombre.setValue(dato.getNombre());
			tbxEscolaridad_conyugue.setValue(dato.getId());

		}
		tbxEscolaridad_nombre.close();
	}

}
