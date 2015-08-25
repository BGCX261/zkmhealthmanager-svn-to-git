/*
 * his_atencion_mesesAction.java
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
import healthmanager.modelo.bean.His_atencion_meses;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Ocupaciones;
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

public class His_atencion_mesesAction extends Borderlayout implements
		AfterCompose {

	private static Logger log = Logger
			.getLogger(His_atencion_mesesAction.class);
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
	private Textbox tbxAcompanante;
	private Listbox lbxParentesco;

	private Bandbox tbxIdentificacion;
	private Textbox tbxNomPaciente;
	private Textbox tbxTipoIdentificacion;
	private Datebox dbxNacimiento;
	private Textbox tbxEdad;
	private Textbox tbxSexo;

	private Textbox tbxNombre_padre;
	private Bandbox tbxOcupacion_padre;
	private Textbox tbxNombre_madre;
	private Bandbox tbxOcupacion_madre;

	private Textbox tbxDireccion;
	private Textbox tbxTelefono;
	private Radiogroup rdbSeleccion;

	private Textbox tbxMotivo;
	private Listbox lbxHemoclasificacion;
	private Listbox lbxRh;
	private Textbox tbxEmbarazo;
	private Textbox tbxDuracion_meses;
	private Textbox tbxDuracion_dias;
	private Textbox tbxParto;
	private Textbox tbxDuracion_nacer;
	private Textbox tbxMedida;
	private Textbox tbxProblema;
	private Textbox tbxDesc_problema;
	private Textbox tbxEnfermedades;
	private Textbox tbxPeso_actual;
	private Textbox tbxTalla_actual;
	private Textbox tbxPc_actual;
	private Textbox tbxFc_actual;
	private Textbox tbxFr_actual;
	private Textbox tbxTemp_auxiliar;
	private Listbox lbxPecho;
	private Listbox lbxVomito;
	private Listbox lbxVomita_todo;
	private Listbox lbxRespiro;
	private Textbox tbxExplique;
	private Listbox lbxFiebre;
	private Listbox lbxHipotermia;
	private Listbox lbxConvulsiones;
	private Textbox tbxPanales;
	private Radiogroup rdbEstimulo;
	private Radiogroup rdbPalidez;
	private Radiogroup rdbFc_mayor;
	private Radiogroup rdbFr_mayor;
	private Radiogroup rdbApnea;
	private Radiogroup rdbQuejido;
	private Radiogroup rdbSuperacion;
	private Radiogroup rdbPustulas;
	private Radiogroup rdbPlacas;
	private Radiogroup rdbDistencion;
	private Radiogroup rdbVih;
	private Radiogroup rdbNeonatal;
	private Radiogroup rdbClasificacion;
	private Listbox lbxDiarrea;
	private Textbox tbxDuracion_diarrea;
	private Listbox lbxSangre;
	private Radiogroup rdbEstado_general;
	private Radiogroup rdbEstado_comportamiento;
	private Radiogroup rdbOjos_hundidos;
	private Radiogroup rdbClasificacion_diarrea;
	private Listbox lbxDificultad;
	private Textbox tbxCual;
	private Listbox lbxComer;
	private Textbox tbxDuracion_comer;
	private Listbox lbxLeche_materna;
	private Listbox lbxForma;
	private Listbox lbxVeces;
	private Listbox lbxOtra_leche;
	private Textbox tbxFrecuencia;
	private Textbox tbxPreparacion;
	private Textbox tbxUtiliza;
	private Listbox lbxChupo;
	private Textbox tbxPeso_edad;
	private Textbox tbxPeso_talla;
	private Textbox tbxPerdida_peso;
	private Radiogroup rdbTendencia_peso;
	private Radiogroup rdbAgarre;
	private Radiogroup rdbPosicion;
	private Textbox tbxSuccion;
	private Radiogroup rdbClasificacion_alimentacion;
	private Listbox lbxParientes;
	private Listbox lbxFamiliar_problemas;
	private Textbox tbxCaida;
	private Textbox tbxDesarrollo;
	private Textbox tbxParto_neonatal;
	private Textbox tbxPc_dllo;
	private Textbox tbxPce_dllo;
	private Checkbox chbMoro;
	private Checkbox chbReflejo_succion;
	private Checkbox chbManos_cerradas;
	private Checkbox chbVacaliza;
	private Checkbox chbSonrisa;
	private Textbox tbxFenitipica;
	private Radiogroup rdbClasificacion_desarrollo;
	private Textbox tbxTemperatura;
	private Textbox tbxFc;
	private Textbox tbxFr;
	private Textbox tbxTalla;
	private Textbox tbxPeso;
	private Textbox tbxPc;
	private Textbox tbxImc;
	private Checkbox chbVacunas_ant1;
	private Checkbox chbVacunas_ant2;
	private Textbox tbxVacunas_otras;
	private Datebox dtbxFecha_madre;
	private Checkbox chbBcg;
	private Checkbox chbHepb;
	private Datebox dtbxFecha_hijo;
	private Textbox tbxAlarma;
	private Textbox tbxConsulta_control;
	private Textbox tbxConsulta_sano;
	private Textbox tbxReferido_consulta;
	private Textbox tbxMedidas_preventiva;
	private Textbox tbxRecomendaciones;
	private Listbox lbxAdecuada_lactancia;
	private Textbox tbxDiagnostico;
	private Textbox tbxCodigo_diagnostico;
	private Textbox tbxTratamiento;

//	private String codigo_historia;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();
	}

	public void loadComponents() {
		form = (Borderlayout) this.getFellow("formHis_atencion_meses");

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
		tbxAcompanante = (Textbox) form.getFellow("tbxAcompanante");
		lbxParentesco = (Listbox) form.getFellow("lbxParentesco");

		tbxIdentificacion = (Bandbox) form.getFellow("tbxIdentificacion");
		tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
		tbxTipoIdentificacion = (Textbox) form
				.getFellow("tbxTipoIdentificacion");
		tbxEdad = (Textbox) form.getFellow("tbxEdad");
		tbxSexo = (Textbox) form.getFellow("tbxSexo");
		dbxNacimiento = (Datebox) form.getFellow("dbxNacimiento");

		tbxOcupacion_padre = (Bandbox) form.getFellow("tbxOcupacion_padre");
		tbxNombre_padre = (Textbox) form.getFellow("tbxNombre_padre");
		tbxOcupacion_madre = (Bandbox) form.getFellow("tbxOcupacion_madre");
		tbxNombre_madre = (Textbox) form.getFellow("tbxNombre_madre");
		tbxDireccion = (Textbox) form.getFellow("tbxDireccion");
		tbxTelefono = (Textbox) form.getFellow("tbxTelefono");
		rdbSeleccion = (Radiogroup) form.getFellow("rdbSeleccion");

		tbxMotivo = (Textbox) form.getFellow("tbxMotivo");

		lbxHemoclasificacion = (Listbox) form.getFellow("lbxHemoclasificacion");
		lbxRh = (Listbox) form.getFellow("lbxRh");
		tbxEmbarazo = (Textbox) form.getFellow("tbxEmbarazo");
		tbxDuracion_meses = (Textbox) form.getFellow("tbxDuracion_meses");
		tbxDuracion_dias = (Textbox) form.getFellow("tbxDuracion_dias");
		tbxParto = (Textbox) form.getFellow("tbxParto");
		tbxDuracion_nacer = (Textbox) form.getFellow("tbxDuracion_nacer");
		tbxMedida = (Textbox) form.getFellow("tbxMedida");
		tbxProblema = (Textbox) form.getFellow("tbxProblema");
		tbxDesc_problema = (Textbox) form.getFellow("tbxDesc_problema");
		tbxEnfermedades = (Textbox) form.getFellow("tbxEnfermedades");
		tbxPeso_actual = (Textbox) form.getFellow("tbxPeso_actual");
		tbxTalla_actual = (Textbox) form.getFellow("tbxTalla_actual");
		tbxPc_actual = (Textbox) form.getFellow("tbxPc_actual");
		tbxFc_actual = (Textbox) form.getFellow("tbxFc_actual");
		tbxFr_actual = (Textbox) form.getFellow("tbxFr_actual");
		tbxTemp_auxiliar = (Textbox) form.getFellow("tbxTemp_auxiliar");
		lbxPecho = (Listbox) form.getFellow("lbxPecho");
		lbxVomito = (Listbox) form.getFellow("lbxVomito");
		lbxVomita_todo = (Listbox) form.getFellow("lbxVomita_todo");
		lbxRespiro = (Listbox) form.getFellow("lbxRespiro");
		tbxExplique = (Textbox) form.getFellow("tbxExplique");
		lbxFiebre = (Listbox) form.getFellow("lbxFiebre");
		lbxHipotermia = (Listbox) form.getFellow("lbxHipotermia");
		lbxConvulsiones = (Listbox) form.getFellow("lbxConvulsiones");
		tbxPanales = (Textbox) form.getFellow("tbxPanales");
		rdbEstimulo = (Radiogroup) form.getFellow("rdbEstimulo");
		rdbPalidez = (Radiogroup) form.getFellow("rdbPalidez");
		rdbFc_mayor = (Radiogroup) form.getFellow("rdbFc_mayor");
		rdbFr_mayor = (Radiogroup) form.getFellow("rdbFr_mayor");
		rdbApnea = (Radiogroup) form.getFellow("rdbApnea");
		rdbQuejido = (Radiogroup) form.getFellow("rdbQuejido");
		rdbSuperacion = (Radiogroup) form.getFellow("rdbSuperacion");
		rdbPustulas = (Radiogroup) form.getFellow("rdbPustulas");
		rdbPlacas = (Radiogroup) form.getFellow("rdbPlacas");
		rdbDistencion = (Radiogroup) form.getFellow("rdbDistencion");
		rdbVih = (Radiogroup) form.getFellow("rdbVih");
		rdbNeonatal = (Radiogroup) form.getFellow("rdbNeonatal");
		rdbClasificacion = (Radiogroup) form.getFellow("rdbClasificacion");
		lbxDiarrea = (Listbox) form.getFellow("lbxDiarrea");
		tbxDuracion_diarrea = (Textbox) form.getFellow("tbxDuracion_diarrea");
		lbxSangre = (Listbox) form.getFellow("lbxSangre");
		rdbEstado_general = (Radiogroup) form.getFellow("rdbEstado_general");
		rdbEstado_comportamiento = (Radiogroup) form
				.getFellow("rdbEstado_comportamiento");
		rdbOjos_hundidos = (Radiogroup) form.getFellow("rdbOjos_hundidos");
		rdbClasificacion_diarrea = (Radiogroup) form
				.getFellow("rdbClasificacion_diarrea");
		lbxDificultad = (Listbox) form.getFellow("lbxDificultad");
		tbxCual = (Textbox) form.getFellow("tbxCual");
		lbxComer = (Listbox) form.getFellow("lbxComer");
		tbxDuracion_comer = (Textbox) form.getFellow("tbxDuracion_comer");
		lbxLeche_materna = (Listbox) form.getFellow("lbxLeche_materna");
		lbxForma = (Listbox) form.getFellow("lbxForma");
		lbxVeces = (Listbox) form.getFellow("lbxVeces");
		lbxOtra_leche = (Listbox) form.getFellow("lbxOtra_leche");
		tbxFrecuencia = (Textbox) form.getFellow("tbxFrecuencia");
		tbxPreparacion = (Textbox) form.getFellow("tbxPreparacion");
		tbxUtiliza = (Textbox) form.getFellow("tbxUtiliza");
		lbxChupo = (Listbox) form.getFellow("lbxChupo");
		tbxPeso_edad = (Textbox) form.getFellow("tbxPeso_edad");
		tbxPeso_talla = (Textbox) form.getFellow("tbxPeso_talla");
		tbxPerdida_peso = (Textbox) form.getFellow("tbxPerdida_peso");
		rdbTendencia_peso = (Radiogroup) form.getFellow("rdbTendencia_peso");
		rdbAgarre = (Radiogroup) form.getFellow("rdbAgarre");
		rdbPosicion = (Radiogroup) form.getFellow("rdbPosicion");
		tbxSuccion = (Textbox) form.getFellow("tbxSuccion");
		rdbClasificacion_alimentacion = (Radiogroup) form
				.getFellow("rdbClasificacion_alimentacion");
		lbxParientes = (Listbox) form.getFellow("lbxParientes");
		lbxFamiliar_problemas = (Listbox) form
				.getFellow("lbxFamiliar_problemas");
		tbxCaida = (Textbox) form.getFellow("tbxCaida");
		tbxDesarrollo = (Textbox) form.getFellow("tbxDesarrollo");
		tbxParto_neonatal = (Textbox) form.getFellow("tbxParto_neonatal");
		tbxPc_dllo = (Textbox) form.getFellow("tbxPc_dllo");
		tbxPce_dllo = (Textbox) form.getFellow("tbxPce_dllo");
		chbMoro = (Checkbox) form.getFellow("chbMoro");
		chbReflejo_succion = (Checkbox) form.getFellow("chbReflejo_succion");
		chbManos_cerradas = (Checkbox) form.getFellow("chbManos_cerradas");
		chbVacaliza = (Checkbox) form.getFellow("chbVacaliza");
		chbSonrisa = (Checkbox) form.getFellow("chbSonrisa");
		tbxFenitipica = (Textbox) form.getFellow("tbxFenitipica");
		rdbClasificacion_desarrollo = (Radiogroup) form
				.getFellow("rdbClasificacion_desarrollo");
		tbxTemperatura = (Textbox) form.getFellow("tbxTemperatura");
		tbxFc = (Textbox) form.getFellow("tbxFc");
		tbxFr = (Textbox) form.getFellow("tbxFr");
		tbxTalla = (Textbox) form.getFellow("tbxTalla");
		tbxPeso = (Textbox) form.getFellow("tbxPeso");
		tbxPc = (Textbox) form.getFellow("tbxPc");
		tbxImc = (Textbox) form.getFellow("tbxImc");
		chbVacunas_ant1 = (Checkbox) form.getFellow("chbVacunas_ant1");
		chbVacunas_ant2 = (Checkbox) form.getFellow("chbVacunas_ant2");
		tbxVacunas_otras = (Textbox) form.getFellow("tbxVacunas_otras");
		dtbxFecha_madre = (Datebox) form.getFellow("dtbxFecha_madre");
		chbBcg = (Checkbox) form.getFellow("chbBcg");
		chbHepb = (Checkbox) form.getFellow("chbHepb");
		dtbxFecha_hijo = (Datebox) form.getFellow("dtbxFecha_hijo");
		tbxAlarma = (Textbox) form.getFellow("tbxAlarma");
		tbxConsulta_control = (Textbox) form.getFellow("tbxConsulta_control");
		tbxConsulta_sano = (Textbox) form.getFellow("tbxConsulta_sano");
		tbxReferido_consulta = (Textbox) form.getFellow("tbxReferido_consulta");
		tbxMedidas_preventiva = (Textbox) form
				.getFellow("tbxMedidas_preventiva");
		tbxRecomendaciones = (Textbox) form.getFellow("tbxRecomendaciones");
		lbxAdecuada_lactancia = (Listbox) form
				.getFellow("lbxAdecuada_lactancia");
		tbxDiagnostico = (Textbox) form.getFellow("tbxDiagnostico");
		tbxCodigo_diagnostico = (Textbox) form
				.getFellow("tbxCodigo_diagnostico");
		tbxTratamiento = (Textbox) form.getFellow("tbxTratamiento");

	}

	public void initHis_atencion_meses() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		try {
			String id_m = request.getParameter("id_menu");
			if (id_m != null) {
				// crearPermisos(new Integer(id_m));
			}
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
		listarElementoListbox(lbxParentesco, false);
		listarElementoListbox(lbxHemoclasificacion, false);
		listarElementoListbox(lbxRh, false);
		listarElementoListbox(lbxPecho, false);
		listarElementoListbox(lbxVomito, false);
		listarElementoListbox(lbxVomita_todo, false);
		listarElementoListbox(lbxRespiro, false);
		listarElementoListbox(lbxFiebre, false);
		listarElementoListbox(lbxHipotermia, false);
		listarElementoListbox(lbxConvulsiones, false);
		listarElementoListbox(lbxDiarrea, false);
		listarElementoListbox(lbxSangre, false);
		listarElementoListbox(lbxDificultad, false);
		listarElementoListbox(lbxComer, false);
		listarElementoListbox(lbxLeche_materna, false);
		listarElementoListbox(lbxForma, false);
		listarElementoListbox(lbxVeces, false);
		listarElementoListbox(lbxOtra_leche, false);
		listarElementoListbox(lbxChupo, false);
		listarElementoListbox(lbxParientes, false);
		listarElementoListbox(lbxFamiliar_problemas, false);
		listarElementoListbox(lbxAdecuada_lactancia, false);
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

			getServiceLocator().getHis_atencion_mesesService().setLimit(
					"limit 25 offset 0");

			List<His_atencion_meses> lista_datos = getServiceLocator()
					.getHis_atencion_mesesService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (His_atencion_meses his_atencion_meses : lista_datos) {
				rowsResultado.appendChild(crearFilas(his_atencion_meses, this));
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

		final His_atencion_meses his_atencion_meses = (His_atencion_meses) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(his_atencion_meses.getCodigo_historia() + ""));
		fila.appendChild(new Label(his_atencion_meses.getIdentificacion() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {

				mostrarDatos(his_atencion_meses);
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
									eliminarDatos(his_atencion_meses);
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

				His_atencion_meses his_atencion_meses = new His_atencion_meses();
				his_atencion_meses.setCodigo_empresa(empresa
						.getCodigo_empresa());
				his_atencion_meses.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				his_atencion_meses.setCodigo_historia(tbxCodigo_historia
						.getValue());
				his_atencion_meses.setFecha_inicial(new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				his_atencion_meses.setCodigo_eps(tbxCodigo_eps.getValue());
				his_atencion_meses.setCodigo_dpto(lbxCodigo_dpto
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setCodigo_municipio(lbxCodigo_municipio
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setIdentificacion(tbxIdentificacion
						.getValue());
				his_atencion_meses.setDireccion(tbxDireccion.getValue());
				his_atencion_meses.setTelefono(tbxTelefono.getValue());
				his_atencion_meses.setSeleccion(rdbSeleccion.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setAcompanante(tbxAcompanante.getValue());
				his_atencion_meses.setParentesco(lbxParentesco
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setOcupacion_padre(tbxOcupacion_padre
						.getValue());
				his_atencion_meses.setOcupacion_madre(tbxOcupacion_madre
						.getValue());
				his_atencion_meses.setMotivo(tbxMotivo.getValue());
				his_atencion_meses.setHemoclasificacion(lbxHemoclasificacion
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setRh(lbxRh.getSelectedItem().getValue()
						.toString());
				his_atencion_meses.setEmbarazo(tbxEmbarazo.getValue());
				his_atencion_meses.setDuracion_meses(tbxDuracion_meses
						.getValue());
				his_atencion_meses
						.setDuracion_dias(tbxDuracion_dias.getValue());
				his_atencion_meses.setParto(tbxParto.getValue());
				his_atencion_meses.setDuracion_nacer(tbxDuracion_nacer
						.getValue());
				his_atencion_meses.setMedida(tbxMedida.getValue());
				his_atencion_meses.setProblema("0");
				his_atencion_meses
						.setDesc_problema(tbxDesc_problema.getValue());
				his_atencion_meses.setEnfermedades(tbxEnfermedades.getValue());
				his_atencion_meses.setPeso_actual(tbxPeso_actual.getValue());
				his_atencion_meses.setTalla_actual(tbxTalla_actual.getValue());
				his_atencion_meses.setPc_actual(tbxPc_actual.getValue());
				his_atencion_meses.setFc_actual(tbxFc_actual.getValue());
				his_atencion_meses.setFr_actual(tbxFr_actual.getValue());
				his_atencion_meses
						.setTemp_auxiliar(tbxTemp_auxiliar.getValue());
				his_atencion_meses.setPecho(lbxPecho.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setVomito(lbxVomito.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setVomita_todo(lbxVomita_todo
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setRespiro(lbxRespiro.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setExplique(tbxExplique.getValue());
				his_atencion_meses.setFiebre(lbxFiebre.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setHipotermia(lbxHipotermia
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setConvulsiones(lbxConvulsiones
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setPanales(tbxPanales.getValue());
				his_atencion_meses.setEstimulo(rdbEstimulo.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setPalidez(rdbPalidez.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setFc_mayor(rdbFc_mayor.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setFr_mayor(rdbFr_mayor.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setApnea(rdbApnea.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setQuejido(rdbQuejido.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setSuperacion(rdbSuperacion
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setPustulas(rdbPustulas.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setPlacas(rdbPlacas.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setDistencion(rdbDistencion
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setVih(rdbVih.getSelectedItem().getValue()
						.toString());
				his_atencion_meses.setNeonatal(rdbNeonatal.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setClasificacion(rdbClasificacion
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setDiarrea(lbxDiarrea.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setDuracion_diarrea(tbxDuracion_diarrea
						.getValue());
				his_atencion_meses.setSangre(lbxSangre.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setEstado_general(rdbEstado_general
						.getSelectedItem().getValue().toString());
				his_atencion_meses
						.setEstado_comportamiento(rdbEstado_comportamiento
								.getSelectedItem().getValue().toString());
				his_atencion_meses.setOjos_hundidos(rdbOjos_hundidos
						.getSelectedItem().getValue().toString());
				his_atencion_meses
						.setClasificacion_diarrea(rdbClasificacion_diarrea
								.getSelectedItem().getValue().toString());
				his_atencion_meses.setDificultad(lbxDificultad
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setCual(tbxCual.getValue());
				his_atencion_meses.setComer(lbxComer.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setDuracion_comer(tbxDuracion_comer
						.getValue());
				his_atencion_meses.setLeche_materna(lbxLeche_materna
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setForma(lbxForma.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setVeces(lbxVeces.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setOtra_leche(lbxOtra_leche
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setFrecuencia(tbxFrecuencia.getValue());
				his_atencion_meses.setPreparacion(tbxPreparacion.getValue());
				his_atencion_meses.setUtiliza(tbxUtiliza.getValue());
				his_atencion_meses.setChupo(lbxChupo.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setPeso_edad(tbxPeso_edad.getValue());
				his_atencion_meses.setPeso_talla(tbxPeso_talla.getValue());
				his_atencion_meses.setPerdida_peso(tbxPerdida_peso.getValue());
				his_atencion_meses.setTendencia_peso(rdbTendencia_peso
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setAgarre(rdbAgarre.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setPosicion(rdbPosicion.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setSuccion(tbxSuccion.getValue());
				his_atencion_meses
						.setClasificacion_alimentacion(rdbClasificacion_alimentacion
								.getSelectedItem().getValue().toString());
				his_atencion_meses.setParientes(lbxParientes.getSelectedItem()
						.getValue().toString());
				his_atencion_meses.setFamiliar_problemas(lbxFamiliar_problemas
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setCaida(tbxCaida.getValue());
				his_atencion_meses.setDesarrollo(tbxDesarrollo.getValue());
				his_atencion_meses.setParto_neonatal(tbxParto_neonatal
						.getValue());
				his_atencion_meses.setPc_dllo(tbxPc_dllo.getValue());
				his_atencion_meses.setPce_dllo(tbxPce_dllo.getValue());
				his_atencion_meses.setMoro(chbMoro.isChecked());
				his_atencion_meses.setReflejo_succion(chbReflejo_succion
						.isChecked());
				his_atencion_meses.setManos_cerradas(chbManos_cerradas
						.isChecked());
				his_atencion_meses.setVacaliza(chbVacaliza.isChecked());
				his_atencion_meses.setSonrisa(chbSonrisa.isChecked());
				his_atencion_meses.setFenitipica(tbxFenitipica.getValue());
				his_atencion_meses
						.setClasificacion_desarrollo(rdbClasificacion_desarrollo
								.getSelectedItem().getValue().toString());
				his_atencion_meses.setTemperatura(tbxTemperatura.getValue());
				his_atencion_meses.setFc(tbxFc.getValue());
				his_atencion_meses.setFr(tbxFr.getValue());
				his_atencion_meses.setTalla(tbxTalla.getValue());
				his_atencion_meses.setPeso(tbxPeso.getValue());
				his_atencion_meses.setPc(tbxPc.getValue());
				his_atencion_meses.setImc(tbxImc.getValue());
				his_atencion_meses.setVacunas_ant1(chbVacunas_ant1.isChecked());
				his_atencion_meses.setVacunas_ant2(chbVacunas_ant2.isChecked());
				his_atencion_meses
						.setVacunas_otras(tbxVacunas_otras.getValue());
				his_atencion_meses.setFecha_madre(new Timestamp(dtbxFecha_madre
						.getValue().getTime()));
				his_atencion_meses.setBcg(chbBcg.isChecked());
				his_atencion_meses.setHepb(chbHepb.isChecked());
				his_atencion_meses.setFecha_hijo(new Timestamp(dtbxFecha_hijo
						.getValue().getTime()));
				his_atencion_meses.setAlarma(tbxAlarma.getValue());
				his_atencion_meses.setConsulta_control(tbxConsulta_control
						.getValue());
				his_atencion_meses
						.setConsulta_sano(tbxConsulta_sano.getValue());
				his_atencion_meses.setReferido_consulta(tbxReferido_consulta
						.getValue());
				his_atencion_meses.setMedidas_preventiva(tbxMedidas_preventiva
						.getValue());
				his_atencion_meses.setRecomendaciones(tbxRecomendaciones
						.getValue());
				his_atencion_meses.setAdecuada_lactancia(lbxAdecuada_lactancia
						.getSelectedItem().getValue().toString());
				his_atencion_meses.setDiagnostico(tbxDiagnostico.getValue());
				his_atencion_meses.setCodigo_diagnostico(tbxCodigo_diagnostico
						.getValue());
				his_atencion_meses.setTratamiento(tbxTratamiento.getValue());

				datos.put("codigo_historia", his_atencion_meses);
				datos.put("accion", tbxAccion.getText());

				his_atencion_meses = getServiceLocator()
						.getHis_atencion_mesesService().guardar(datos);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					accionForm(true, "modificar");
				}
				/*
				 * else{ int result =
				 * getServiceLocator().getHis_cancer_infantilService
				 * ().actualizar(his_cancer_infantil); if(result==0){ throw new
				 * Exception(
				 * "Lo sentimos pero los datos a modificar no se encuentran en base de datos"
				 * ); }
				 * 
				 * }
				 */

//				codigo_historia = his_atencion_meses.getCodigo_historia();

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
		His_atencion_meses his_atencion_meses = (His_atencion_meses) obj;
		try {
			tbxCodigo_historia
					.setValue(his_atencion_meses.getCodigo_historia());
			dtbxFecha_inicial.setValue(his_atencion_meses.getFecha_inicial());

			Administradora administradora = new Administradora();
			administradora.setCodigo(his_atencion_meses.getCodigo_eps());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getCodigo_dpto())) {
					listitem.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

			tbxAcompanante.setValue(his_atencion_meses.getAcompanante());

			for (int i = 0; i < lbxParentesco.getItemCount(); i++) {
				Listitem listitem = lbxParentesco.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getParentesco())) {
					listitem.setSelected(true);
					i = lbxParentesco.getItemCount();
				}
			}

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(his_atencion_meses.getCodigo_empresa());
			paciente.setCodigo_sucursal(his_atencion_meses.getCodigo_sucursal());
			paciente.setNro_identificacion(his_atencion_meses
					.getIdentificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			Elemento elemento = new Elemento();
			elemento.setCodigo(paciente.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxIdentificacion.setValue(his_atencion_meses.getIdentificacion());
			tbxNomPaciente.setValue((paciente != null ? paciente.getNombre1()
					+ " " + paciente.getApellido1() : ""));
			tbxTipoIdentificacion.setValue((paciente != null ? paciente
					.getTipo_identificacion() : ""));
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
					paciente.getUnidad_medidad(), false));
			tbxSexo.setValue((elemento != null ? elemento.getDescripcion() : ""));
			dbxNacimiento.setValue(paciente.getFecha_nacimiento());

			Ocupaciones ocupacion_padre = new Ocupaciones();
			ocupacion_padre.setId(his_atencion_meses.getOcupacion_padre());
			ocupacion_padre = getServiceLocator().getOcupacionesService()
					.consultar(ocupacion_padre);
			tbxNombre_padre.setValue(ocupacion_padre != null ? ocupacion_padre
					.getNombre() : "");
			tbxOcupacion_padre
					.setValue(ocupacion_padre != null ? ocupacion_padre.getId()
							: "");

			Ocupaciones ocupacion_madre = new Ocupaciones();
			ocupacion_madre.setId(his_atencion_meses.getOcupacion_madre());
			ocupacion_madre = getServiceLocator().getOcupacionesService()
					.consultar(ocupacion_madre);
			tbxNombre_madre.setValue(ocupacion_madre != null ? ocupacion_madre
					.getNombre() : "");
			tbxOcupacion_madre
					.setValue(ocupacion_madre != null ? ocupacion_madre.getId()
							: "");

			tbxDireccion.setValue(his_atencion_meses.getDireccion());
			tbxTelefono.setValue(his_atencion_meses.getTelefono());
			Radio radio = (Radio) rdbSeleccion.getFellow("Seleccion"
					+ his_atencion_meses.getSeleccion());
			radio.setChecked(true);

			tbxMotivo.setValue(his_atencion_meses.getMotivo());
			for (int i = 0; i < lbxHemoclasificacion.getItemCount(); i++) {
				Listitem listitem = lbxHemoclasificacion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getHemoclasificacion())) {
					listitem.setSelected(true);
					i = lbxHemoclasificacion.getItemCount();
				}
			}
			for (int i = 0; i < lbxRh.getItemCount(); i++) {
				Listitem listitem = lbxRh.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getRh())) {
					listitem.setSelected(true);
					i = lbxRh.getItemCount();
				}
			}
			tbxEmbarazo.setValue(his_atencion_meses.getEmbarazo());
			tbxDuracion_meses.setValue(his_atencion_meses.getDuracion_meses());
			tbxDuracion_dias.setValue(his_atencion_meses.getDuracion_dias());
			tbxParto.setValue(his_atencion_meses.getParto());
			tbxDuracion_nacer.setValue(his_atencion_meses.getDuracion_nacer());
			tbxMedida.setValue(his_atencion_meses.getMedida());
			tbxProblema.setValue(his_atencion_meses.getProblema());
			tbxDesc_problema.setValue(his_atencion_meses.getDesc_problema());
			tbxEnfermedades.setValue(his_atencion_meses.getEnfermedades());
			tbxPeso_actual.setValue(his_atencion_meses.getPeso_actual());
			tbxTalla_actual.setValue(his_atencion_meses.getTalla_actual());
			tbxPc_actual.setValue(his_atencion_meses.getPc_actual());
			tbxFc_actual.setValue(his_atencion_meses.getFc_actual());
			tbxFr_actual.setValue(his_atencion_meses.getFr_actual());
			tbxTemp_auxiliar.setValue(his_atencion_meses.getTemp_auxiliar());
			for (int i = 0; i < lbxPecho.getItemCount(); i++) {
				Listitem listitem = lbxPecho.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getPecho())) {
					listitem.setSelected(true);
					i = lbxPecho.getItemCount();
				}
			}
			for (int i = 0; i < lbxVomito.getItemCount(); i++) {
				Listitem listitem = lbxVomito.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getVomito())) {
					listitem.setSelected(true);
					i = lbxVomito.getItemCount();
				}
			}
			for (int i = 0; i < lbxVomita_todo.getItemCount(); i++) {
				Listitem listitem = lbxVomita_todo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getVomita_todo())) {
					listitem.setSelected(true);
					i = lbxVomita_todo.getItemCount();
				}
			}
			for (int i = 0; i < lbxRespiro.getItemCount(); i++) {
				Listitem listitem = lbxRespiro.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getRespiro())) {
					listitem.setSelected(true);
					i = lbxRespiro.getItemCount();
				}
			}
			tbxExplique.setValue(his_atencion_meses.getExplique());
			for (int i = 0; i < lbxFiebre.getItemCount(); i++) {
				Listitem listitem = lbxFiebre.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getFiebre())) {
					listitem.setSelected(true);
					i = lbxFiebre.getItemCount();
				}
			}
			for (int i = 0; i < lbxHipotermia.getItemCount(); i++) {
				Listitem listitem = lbxHipotermia.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getHipotermia())) {
					listitem.setSelected(true);
					i = lbxHipotermia.getItemCount();
				}
			}
			for (int i = 0; i < lbxConvulsiones.getItemCount(); i++) {
				Listitem listitem = lbxConvulsiones.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getConvulsiones())) {
					listitem.setSelected(true);
					i = lbxConvulsiones.getItemCount();
				}
			}
			tbxPanales.setValue(his_atencion_meses.getPanales());
			Radio radio1 = (Radio) rdbEstimulo.getFellow("Estimulo"
					+ his_atencion_meses.getEstimulo());
			radio1.setChecked(true);
			Radio radio2 = (Radio) rdbPalidez.getFellow("Palidez"
					+ his_atencion_meses.getPalidez());
			radio2.setChecked(true);
			Radio radio3 = (Radio) rdbFc_mayor.getFellow("Fc_mayor"
					+ his_atencion_meses.getFc_mayor());
			radio3.setChecked(true);
			Radio radio4 = (Radio) rdbFr_mayor.getFellow("Fr_mayor"
					+ his_atencion_meses.getFr_mayor());
			radio4.setChecked(true);
			Radio radio5 = (Radio) rdbApnea.getFellow("Apnea"
					+ his_atencion_meses.getApnea());
			radio5.setChecked(true);
			Radio radio6 = (Radio) rdbQuejido.getFellow("Quejido"
					+ his_atencion_meses.getQuejido());
			radio6.setChecked(true);
			Radio radio7 = (Radio) rdbSuperacion.getFellow("Superacion"
					+ his_atencion_meses.getSuperacion());
			radio7.setChecked(true);
			Radio radio8 = (Radio) rdbPustulas.getFellow("Pustulas"
					+ his_atencion_meses.getPustulas());
			radio8.setChecked(true);
			Radio radio9 = (Radio) rdbPlacas.getFellow("Placas"
					+ his_atencion_meses.getPlacas());
			radio9.setChecked(true);
			Radio radio10 = (Radio) rdbDistencion.getFellow("Distencion"
					+ his_atencion_meses.getDistencion());
			radio10.setChecked(true);
			Radio radio11 = (Radio) rdbVih.getFellow("Vih"
					+ his_atencion_meses.getVih());
			radio11.setChecked(true);
			Radio radio12 = (Radio) rdbNeonatal.getFellow("Neonatal"
					+ his_atencion_meses.getNeonatal());
			radio12.setChecked(true);
			Radio radio13 = (Radio) rdbClasificacion.getFellow("Clasificacion"
					+ his_atencion_meses.getClasificacion());
			radio13.setChecked(true);
			for (int i = 0; i < lbxDiarrea.getItemCount(); i++) {
				Listitem listitem = lbxDiarrea.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getDiarrea())) {
					listitem.setSelected(true);
					i = lbxDiarrea.getItemCount();
				}
			}
			tbxDuracion_diarrea.setValue(his_atencion_meses
					.getDuracion_diarrea());
			for (int i = 0; i < lbxSangre.getItemCount(); i++) {
				Listitem listitem = lbxSangre.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getSangre())) {
					listitem.setSelected(true);
					i = lbxSangre.getItemCount();
				}
			}
			Radio radio14 = (Radio) rdbEstado_general
					.getFellow("Estado_general"
							+ his_atencion_meses.getEstado_general());
			radio14.setChecked(true);
			Radio radio15 = (Radio) rdbEstado_comportamiento
					.getFellow("Estado_comportamiento"
							+ his_atencion_meses.getEstado_comportamiento());
			radio15.setChecked(true);
			Radio radio16 = (Radio) rdbOjos_hundidos.getFellow("Ojos_hundidos"
					+ his_atencion_meses.getOjos_hundidos());
			radio16.setChecked(true);
			Radio radio17 = (Radio) rdbClasificacion_diarrea
					.getFellow("Clasificacion_diarrea"
							+ his_atencion_meses.getClasificacion_diarrea());
			radio17.setChecked(true);
			for (int i = 0; i < lbxDificultad.getItemCount(); i++) {
				Listitem listitem = lbxDificultad.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getDificultad())) {
					listitem.setSelected(true);
					i = lbxDificultad.getItemCount();
				}
			}
			tbxCual.setValue(his_atencion_meses.getCual());
			for (int i = 0; i < lbxComer.getItemCount(); i++) {
				Listitem listitem = lbxComer.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getComer())) {
					listitem.setSelected(true);
					i = lbxComer.getItemCount();
				}
			}
			tbxDuracion_comer.setValue(his_atencion_meses.getDuracion_comer());
			for (int i = 0; i < lbxLeche_materna.getItemCount(); i++) {
				Listitem listitem = lbxLeche_materna.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getLeche_materna())) {
					listitem.setSelected(true);
					i = lbxLeche_materna.getItemCount();
				}
			}
			for (int i = 0; i < lbxForma.getItemCount(); i++) {
				Listitem listitem = lbxForma.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getForma())) {
					listitem.setSelected(true);
					i = lbxForma.getItemCount();
				}
			}
			for (int i = 0; i < lbxVeces.getItemCount(); i++) {
				Listitem listitem = lbxVeces.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getVeces())) {
					listitem.setSelected(true);
					i = lbxVeces.getItemCount();
				}
			}
			for (int i = 0; i < lbxOtra_leche.getItemCount(); i++) {
				Listitem listitem = lbxOtra_leche.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getOtra_leche())) {
					listitem.setSelected(true);
					i = lbxOtra_leche.getItemCount();
				}
			}
			tbxFrecuencia.setValue(his_atencion_meses.getFrecuencia());
			tbxPreparacion.setValue(his_atencion_meses.getPreparacion());
			tbxUtiliza.setValue(his_atencion_meses.getUtiliza());
			for (int i = 0; i < lbxChupo.getItemCount(); i++) {
				Listitem listitem = lbxChupo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getChupo())) {
					listitem.setSelected(true);
					i = lbxChupo.getItemCount();
				}
			}
			tbxPeso_edad.setValue(his_atencion_meses.getPeso_edad());
			tbxPeso_talla.setValue(his_atencion_meses.getPeso_talla());
			tbxPerdida_peso.setValue(his_atencion_meses.getPerdida_peso());
			Radio radio18 = (Radio) rdbTendencia_peso
					.getFellow("Tendencia_peso"
							+ his_atencion_meses.getTendencia_peso());
			radio18.setChecked(true);
			Radio radio19 = (Radio) rdbAgarre.getFellow("Agarre"
					+ his_atencion_meses.getAgarre());
			radio19.setChecked(true);
			Radio radio20 = (Radio) rdbPosicion.getFellow("Posicion"
					+ his_atencion_meses.getPosicion());
			radio20.setChecked(true);
			tbxSuccion.setValue(his_atencion_meses.getSuccion());
			Radio radio21 = (Radio) rdbClasificacion_alimentacion
					.getFellow("Clasificacion_alimentacion"
							+ his_atencion_meses
									.getClasificacion_alimentacion());
			radio21.setChecked(true);
			for (int i = 0; i < lbxParientes.getItemCount(); i++) {
				Listitem listitem = lbxParientes.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getParientes())) {
					listitem.setSelected(true);
					i = lbxParientes.getItemCount();
				}
			}
			for (int i = 0; i < lbxFamiliar_problemas.getItemCount(); i++) {
				Listitem listitem = lbxFamiliar_problemas.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getFamiliar_problemas())) {
					listitem.setSelected(true);
					i = lbxFamiliar_problemas.getItemCount();
				}
			}
			tbxCaida.setValue(his_atencion_meses.getCaida());
			tbxDesarrollo.setValue(his_atencion_meses.getDesarrollo());
			tbxParto_neonatal.setValue(his_atencion_meses.getParto_neonatal());
			tbxPc_dllo.setValue(his_atencion_meses.getPc_dllo());
			tbxPce_dllo.setValue(his_atencion_meses.getPce_dllo());
			chbMoro.setChecked(his_atencion_meses.getMoro());
			chbReflejo_succion.setChecked(his_atencion_meses
					.getReflejo_succion());
			chbManos_cerradas
					.setChecked(his_atencion_meses.getManos_cerradas());
			chbVacaliza.setChecked(his_atencion_meses.getVacaliza());
			chbSonrisa.setChecked(his_atencion_meses.getSonrisa());
			tbxFenitipica.setValue(his_atencion_meses.getFenitipica());
			Radio radio22 = (Radio) rdbClasificacion_desarrollo
					.getFellow("Clasificacion_desarrollo"
							+ his_atencion_meses.getClasificacion_desarrollo());
			radio22.setChecked(true);
			tbxTemperatura.setValue(his_atencion_meses.getTemperatura());
			tbxFc.setValue(his_atencion_meses.getFc());
			tbxFr.setValue(his_atencion_meses.getFr());
			tbxTalla.setValue(his_atencion_meses.getTalla());
			tbxPeso.setValue(his_atencion_meses.getPeso());
			tbxPc.setValue(his_atencion_meses.getPc());
			tbxImc.setValue(his_atencion_meses.getImc());
			chbVacunas_ant1.setChecked(his_atencion_meses.getVacunas_ant1());
			chbVacunas_ant2.setChecked(his_atencion_meses.getVacunas_ant2());
			tbxVacunas_otras.setValue(his_atencion_meses.getVacunas_otras());
			dtbxFecha_madre.setValue(his_atencion_meses.getFecha_madre());
			chbBcg.setChecked(his_atencion_meses.getBcg());
			chbHepb.setChecked(his_atencion_meses.getHepb());
			dtbxFecha_hijo.setValue(his_atencion_meses.getFecha_hijo());
			tbxAlarma.setValue(his_atencion_meses.getAlarma());
			tbxConsulta_control.setValue(his_atencion_meses
					.getConsulta_control());
			tbxConsulta_sano.setValue(his_atencion_meses.getConsulta_sano());
			tbxReferido_consulta.setValue(his_atencion_meses
					.getReferido_consulta());
			tbxMedidas_preventiva.setValue(his_atencion_meses
					.getMedidas_preventiva());
			tbxRecomendaciones
					.setValue(his_atencion_meses.getRecomendaciones());
			for (int i = 0; i < lbxAdecuada_lactancia.getItemCount(); i++) {
				Listitem listitem = lbxAdecuada_lactancia.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_atencion_meses.getAdecuada_lactancia())) {
					listitem.setSelected(true);
					i = lbxAdecuada_lactancia.getItemCount();
				}
			}
			tbxDiagnostico.setValue(his_atencion_meses.getDiagnostico());
			tbxCodigo_diagnostico.setValue(his_atencion_meses
					.getCodigo_diagnostico());
			tbxTratamiento.setValue(his_atencion_meses.getTratamiento());

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
		His_atencion_meses his_atencion_meses = (His_atencion_meses) obj;
		try {
			int result = getServiceLocator().getHis_atencion_mesesService()
					.eliminar(his_atencion_meses);
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

	public void buscarOcupaciones(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
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

	public void selectedOcupaciones(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_padre.setValue("");
			tbxOcupacion_padre.setValue("");

		} else {
			Ocupaciones dato = (Ocupaciones) listitem.getValue();
			tbxNombre_padre.setValue(dato.getNombre());
			tbxOcupacion_padre.setValue(dato.getId());

		}
		tbxOcupacion_padre.close();
	}

	public void selectedOcupacion_madre(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_madre.setValue("");
			tbxOcupacion_madre.setValue("");

		} else {
			Ocupaciones dato = (Ocupaciones) listitem.getValue();
			tbxNombre_madre.setValue(dato.getNombre());
			tbxOcupacion_madre.setValue(dato.getId());

		}
		tbxOcupacion_madre.close();
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
			tbxEdad.setValue("");
			tbxSexo.setValue("");
			dbxNacimiento.setValue(null);
			tbxDireccion.setValue("");

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
