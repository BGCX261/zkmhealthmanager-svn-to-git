/*
 * hisc_fisioterapiaAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Antecedentes_personales;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Hisc_fisioterapia;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.service.Hisc_fisioterapiaService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Remision_internaService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.interfaces.IHistoria_generica;
import com.framework.macros.ImpresionDiagnosticaMacro;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.macros.Remision_internaMacro;
import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.ConvertidorXmlToMap;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Hisc_fisioterapiaAction extends HistoriaClinicaModel implements
        IHistoria_generica{

	
	
	private Hisc_fisioterapiaService hisc_fisioterapiaService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Toolbarbutton btGuardar;
	@View private Toolbarbutton btImprimir;
	@View private Toolbarbutton btnCancelar;
	@View private Toolbarbutton toolbarbuttonPaciente_admisionado1;
	@View private Toolbarbutton toolbarbuttonPaciente_admisionado2;
	
	
	@View private Groupbox gbxIdentificacion;
	@View(isMacro = true) private InformacionPacientesMacro infoPacientes;
	@View private Bandbox bandboxPrestador;
	@View private Textbox tbxNomPrestador;
	@View private Toolbarbutton btnLimpiar_prestador;
	@View private Textbox tbxAcompaniante;
	@View private Textbox tbxCedula_acomp;
	@View private Doublebox dbxTel_acompaniante;
	@View private Listbox lbxRelacion;
	@View private Listbox lbxLateralidad_motora;
	@View private Hlayout hlayoutAntecedentes_personales;
	@View private Grid gridAntecedentes_personales;
	@View private Bandbox bandboxAnte_fam_hipertension;
	@View private Bandbox bandboxAnte_fam_obesos;
	@View private Bandbox bandboxAnte_fam_ecv;
	@View private Bandbox bandboxAnte_fam_diabetes;
	@View private Bandbox bandboxAnte_fam_enf_coronaria;
	@View private Bandbox bandboxAnte_fam_enf_mental;
	@View private Bandbox bandboxAnte_fam_muerte_im_acv;
	@View private Bandbox bandboxAnte_fam_cancer;
	@View private Bandbox bandboxAnte_fam_dislipidemia;
	@View private Bandbox bandboxAnte_fam_hematologia;
	@View private Bandbox bandboxAnte_fam_alergicos;
	@View private Bandbox bandboxAnte_fam_infecciosa_vih;
	@View private Bandbox bandboxAnte_fam_infecciosa_sifilis;
	@View private Bandbox bandboxAnte_fam_infecciosa_hepatitisb;
	@View private Bandbox bandboxAnte_fam_infecciosa_tuberculosis;
	@View private Bandbox bandboxAnte_fam_infecciosa_lepra;
	@View private Bandbox bandboxAnte_fam_nefropatias;
	@View private Bandbox bandboxAnte_fam_asma;
	@View private Textbox bandboxAnte_fam_otros;
	@View private Textbox tbxAnte_fam_estecifique;
	@View private Textbox tbxAnte_fam_observaciones;
	@View private Textbox tbxQuirurgicos_toxicos;
	@View private Textbox tbxFarmacologicos;
	@View private Textbox tbxSicologicos;
	@View private Textbox tbxMotivo_consulta;
	@View private Doublebox dbxEval_pa;
	@View private Doublebox dbxEval_fc;
	@View private Doublebox dbxEval_fr;
	@View private Doublebox dbxEval_temp;
	@View private Listbox lbxFis_torax;
	@View private Textbox tbxFis_torax;
	@View private Listbox lbxFis_pulmones;
	@View private Textbox tbxFis_pulmones;
	@View private Listbox lbxFis_abdomen;
	@View private Textbox tbxFs_abdomen;
	@View private Doublebox dbxEval_peso;
	@View private Doublebox dbxEval_talla;
	@View private Doublebox mcDbxIMC;
	@View private Listbox lbxFis_postura;
	@View private Textbox tbxFis_postura;
	@View private Listbox lbxFis_rdm;
	@View private Textbox tbxFis_rdm;
	@View private Listbox lbxFis_tono_muscular;
	@View private Textbox tbxFis_tono_muscular;
	@View private Listbox lbxFis_fuerza_muscular;
	@View private Textbox tbxFis_fuerza_muscular;
	@View private Listbox lbxFis_limitacion_discapacidad;
	@View private Textbox tbxFis_limitacion_discapacidad;
	@View private Listbox lbxFis_coloracion;
	@View private Textbox tbxFis_coloracion;
	@View private Listbox lbxFis_hidratacion;
	@View private Textbox tbxFis_hidratacion;
	@View private Listbox lbxFis_temperatura;
	@View private Textbox tbxFis_temperatura;
	@View private Listbox lbxFis_sudoracion;
	@View private Textbox tbxFis_sudoracion;
	@View private Listbox lbxFis_heridas;
	@View private Textbox tbxFis_heridas;
	@View private Listbox lbxFis_cicatrices;
	@View private Textbox tbxFis_cicatrices;
	@View private Radiogroup rgFis_dolor;
	@View(isMacro = true) private ImpresionDiagnosticaMacro macroImpresion_diagnostica;
	@View private Textbox tbxIntervencion_objetivo;
	@View private Textbox tbxIntervencion_tratamiento;
	@View private Div divModuloFarmacologico;
	@View(isMacro = true) private Remision_internaMacro macroRemision_interna;
	@View private Groupbox gbPLanManejo;

	
	private final String[] idsExcluyentes = { "celdaPrestador",
			"macroImpresion_diagnostica", "dtbxAnt_gin_fecha_ultima_regla",
			"dtbxAnt_gin_fecha_espectante_parto", "tbxValue",
			"tbxNro_identificacion", "tbxNro_ingreso", "tbxTipo_hc", "tbxEdad",
			"tbxArea_int", "lbxParameter", "lbxTipo_disnostico", "northEditar",
			"btnLimpiar_prestador", "lbxSintomatico_respiratorio",
			"lbxSintomatico_piel", "toolbarbuttonPaciente_admisionado1",
			"toolbarbuttonPaciente_admisionado2", "northEditar", "formReceta",
			"gridOrdenes_servicio", "dtbxPpd", "dtbxTorax", "tbxAccion",
			"toolbarbuttonCargar_signos", "dtbxAnt_gin_menarca",
			"divModuloRemisiones_externas", "macroRemision_interna", "btNew",
			"btnCancelar", "btGuardar" };
	
	private List<Elemento> elementosAntecentesPersonales;
	private Admision admision_seleccionada;
	private Citas cita_seleccionada;
	private Opciones_via_ingreso opciones_via_ingreso;
	private String nro_ingreso_admision;
//	private String tipo_historia;
	
	private Receta_rips_internoAction receta_ripsAction;
	
	private String via_ingreso = "1";

	
	@Override
	public void init(){
		listarCombos();
		loadComponents();
	}
	
	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision_seleccionada = (Admision) map
					.get(IVias_ingreso.ADMISION_PACIENTE);
			
			if (admision_seleccionada.getVia_ingreso().equals(
					IVias_ingreso.TERAPIA_RESPIRATORIA)) {
				gbPLanManejo.setVisible(true);
			}
			macroRemision_interna.deshabilitarCheck(admision_seleccionada,
					admision_seleccionada.getVia_ingreso());

			cita_seleccionada = (Citas) map.get(IVias_ingreso.CITA_PACIENTE);
			if (cita_seleccionada == null) {
				cita_seleccionada = new Citas();
				cita_seleccionada.setAcompaniante("");
				cita_seleccionada.setApellidos_acomp("");
			}
			opciones_via_ingreso = (Opciones_via_ingreso) map
					.get(IVias_ingreso.OPCION_VIA_INGRESO);
			if (map.containsKey("via_ingreso")) {
				via_ingreso = (String) map.get("via_ingreso");
			}
		}
	}
	 
	private void loadComponents() {
		cargarAntecedentesPersonales(new HashMap<String, Antecedentes_personales>());
		cargarEventos();
	}
 
	private void cargarEventos() {
		EventListener<Event> eventListener = new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Utilidades.onCalcularIMC(mcDbxIMC, dbxEval_peso, dbxEval_talla, "C");  
			}
		};
		dbxEval_peso.addEventListener(Events.ON_BLUR, eventListener);
		dbxEval_talla.addEventListener(Events.ON_BLUR, eventListener);
	}

	public void listarCombos(){
		listarParameter();
		Utilidades.listarElementoListbox(lbxLateralidad_motora,true,getServiceLocator());
//		Utilidades.listarElementoListbox(lbxFis_dolor,true,getServiceLocator());
		Utilidades
		.listarElementoListbox(lbxRelacion, true, getServiceLocator());
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			buscarDatos();
		}else{
			//buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		infoPacientes.validarInformacionPaciente();
		boolean valido = receta_ripsAction.validarFormExterno();
		try {
			if (valido) {
				FormularioUtil.validarCamposObligatorios(
						tbxMotivo_consulta);
			} 
		} catch (Exception e) {
			valido = false;
		}
		return valido;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
			parameters.put("via_ingreso",via_ingreso);
			
			if (parameter.equalsIgnoreCase("fecha_inicial")) {
				parameters.put("fecha_string", value);
			} else {
				parameters.put("parameter", parameter);
				parameters.put("value", "%" + value + "%");
			}
			
			parameters.put("limite_paginado","limit 25 offset 0");
			
			List<Hisc_fisioterapia> lista_datos = hisc_fisioterapiaService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Hisc_fisioterapia hisc_fisioterapia : lista_datos) {
				rowsResultado.appendChild(crearFilas(hisc_fisioterapia, this));
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
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();

		final Hisc_fisioterapia hisc_fisioterapia = (Hisc_fisioterapia) objeto;
		

		//log.info("hisc_consulta_externa Nro ingreso ===> "
				//+ hisc_fisioterapia.getNro_ingreso());

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(hisc_fisioterapia.getCodigo_historia()
				+ ""));
		fila.appendChild(new Label(hisc_fisioterapia
				.getNro_identificacion() + ""));

		fila.appendChild(new Label(
				admision_seleccionada.getPaciente().getNombre1()
						+ (admision_seleccionada.getPaciente().getNombre2()
								.isEmpty() ? "" : " "
								+ admision_seleccionada.getPaciente()
										.getNombre2()) + " "
						+ admision_seleccionada.getPaciente().getApellido1()
						+ " "
						+ admision_seleccionada.getPaciente().getApellido2()
						+ ""));
        	Datebox datebox = new Datebox(hisc_fisioterapia.getFecha_ingreso());
    		datebox.setButtonVisible(false);
    		datebox.setFormat("yyyy-MM-dd hh-mm-ss");
    		datebox.setWidth("98%");
    		datebox.setReadonly(true);
    		datebox.setInplace(true);
    		fila.appendChild(datebox);

//		fila.appendChild(new Label(hisc_fisioterapia.getTipo_historia()
//				.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ) ? "PRIMERA VEZ"
//				: "CONTROL"));

		Hlayout hlayout = new Hlayout();

		Toolbarbutton btn_mostrar = new Toolbarbutton();
		btn_mostrar.setImage("/images/mostrar_info.png");
		btn_mostrar.setTooltiptext("Mostrar historia Clinica");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.setLabel("Mostrar");
		btn_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						groupboxConsulta.setVisible(false);
						groupboxEditar.setVisible(true);
						cargarInformacion_paciente();
						mostrarDatos(hisc_fisioterapia);

					}
				});
		hlayout.appendChild(btn_mostrar);

		btn_mostrar = new Toolbarbutton();
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			btn_mostrar.setVisible(false);
		}
		btn_mostrar.setImage("/images/borrar.gif");
		btn_mostrar.setTooltiptext("Eliminar");
		btn_mostrar.setStyle("cursor: pointer");
		btn_mostrar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("Esta seguro que desea eliminar este registro? ",
										"Eliminar Registro",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													// do the thing
													eliminarDatos(hisc_fisioterapia);
													buscarDatos();
												}
											}
										});
					}
				});
		hlayout.appendChild(new Space());
		hlayout.appendChild(btn_mostrar);

		fila.appendChild(hlayout);

		return fila;
	}
	
	
	public Hisc_fisioterapia getBean(){
		Hisc_fisioterapia hisc_fisioterapia = new Hisc_fisioterapia();
		hisc_fisioterapia.setCodigo_empresa(empresa.getCodigo_empresa());
		hisc_fisioterapia.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		hisc_fisioterapia.setCodigo_historia(infoPacientes.getCodigo_historia());
		hisc_fisioterapia.setCodigo_prestador(bandboxPrestador.getValue());
		hisc_fisioterapia.setNro_identificacion(admision_seleccionada
		.getNro_identificacion());
		hisc_fisioterapia.setNro_ingreso(admision_seleccionada.getNro_ingreso());
		hisc_fisioterapia.setVia_ingreso(admision_seleccionada.getVia_ingreso());
		hisc_fisioterapia.setRelacion_acompaniante(tbxAcompaniante.getValue());
		hisc_fisioterapia.setTelefono_acompaniante(lbxRelacion.getSelectedItem().getValue().toString());
		
		hisc_fisioterapia.setAcompaniante(tbxAcompaniante.getValue());
		hisc_fisioterapia.setLateralidad_motora(lbxLateralidad_motora.getSelectedItem().getValue().toString());
		hisc_fisioterapia.setQuirurgicos_toxicos(tbxQuirurgicos_toxicos.getValue());
		hisc_fisioterapia.setFarmacologicos(tbxFarmacologicos.getValue());
		hisc_fisioterapia.setSicologicos(tbxSicologicos.getValue());
		hisc_fisioterapia.setMotivo_consulta(tbxMotivo_consulta.getValue());
		hisc_fisioterapia.setEval_pa((dbxEval_pa.getValue()!=null?dbxEval_pa.getValue():0.00));
		hisc_fisioterapia.setEval_fc((dbxEval_fc.getValue()!=null?dbxEval_fc.getValue():0.00));
		hisc_fisioterapia.setEval_fr(dbxEval_fr.getValue()!=null?dbxEval_fr.getValue():0.00);
		hisc_fisioterapia.setEval_peso((dbxEval_peso.getValue()!=null?dbxEval_peso.getValue():0.00));
		hisc_fisioterapia.setEval_temp((dbxEval_temp.getValue()!=null?dbxEval_temp.getValue():0.00));
		hisc_fisioterapia.setEval_talla((dbxEval_talla.getValue()!=null?dbxEval_talla.getValue():0.00));
		hisc_fisioterapia.setFis_pulmones(tbxFis_pulmones.getValue());
		hisc_fisioterapia.setFs_abdomen(tbxFs_abdomen.getValue());
		hisc_fisioterapia.setFis_totax(tbxFis_torax.getValue()); 
		hisc_fisioterapia.setFis_postura(tbxFis_postura.getValue());
		hisc_fisioterapia.setFis_rdm(tbxFis_rdm.getValue());
		hisc_fisioterapia.setFis_tono_muscular(tbxFis_tono_muscular.getValue());
		hisc_fisioterapia.setFis_fuerza_muscular(tbxFis_fuerza_muscular.getValue());
		hisc_fisioterapia.setFis_limitacion_discapacidad(tbxFis_limitacion_discapacidad.getValue());
		hisc_fisioterapia.setFis_coloracion(tbxFis_coloracion.getValue());
		hisc_fisioterapia.setFis_hidratacion(tbxFis_hidratacion.getValue());
		hisc_fisioterapia.setFis_temperatura(tbxFis_temperatura.getValue());
		hisc_fisioterapia.setFis_sudoracion(tbxFis_sudoracion.getValue());
		hisc_fisioterapia.setFis_heridas(tbxFis_heridas.getValue());
		hisc_fisioterapia.setFis_cicatrices(tbxFis_cicatrices.getValue());
		hisc_fisioterapia.setFis_dolor(rgFis_dolor.getSelectedItem() != null ? rgFis_dolor.getSelectedItem().getValue().toString() : "");
		hisc_fisioterapia.setIntervencion_objetivo(tbxIntervencion_objetivo.getValue());
		hisc_fisioterapia.setIntervencion_tratamiento(tbxIntervencion_tratamiento.getValue());
		hisc_fisioterapia.setAnte_fam_hipertension(bandboxAnte_fam_hipertension.getValue());
		hisc_fisioterapia.setAnte_fam_ecv(bandboxAnte_fam_ecv.getValue());
		hisc_fisioterapia.setAnte_fam_enf_coronaria(bandboxAnte_fam_enf_coronaria.getValue());
		hisc_fisioterapia.setAnte_fam_muerte_im_acv(bandboxAnte_fam_muerte_im_acv.getValue());
		hisc_fisioterapia.setAnte_fam_dislipidemia(bandboxAnte_fam_dislipidemia.getValue());
		hisc_fisioterapia.setAnte_fam_nefropatias(bandboxAnte_fam_nefropatias.getValue());
		hisc_fisioterapia.setAnte_fam_obesos(bandboxAnte_fam_obesos.getValue());
		hisc_fisioterapia.setAnte_fam_diabetes(bandboxAnte_fam_diabetes.getValue());
		hisc_fisioterapia.setAnte_fam_enf_mental(bandboxAnte_fam_enf_mental.getValue());
		hisc_fisioterapia.setAnte_fam_cancer(bandboxAnte_fam_cancer.getValue());
		hisc_fisioterapia.setAnte_fam_hematologia(bandboxAnte_fam_hematologia.getValue());
		hisc_fisioterapia.setAnte_fam_otros(bandboxAnte_fam_otros.getValue());
		hisc_fisioterapia.setAnte_fam_estecifique(tbxAnte_fam_estecifique.getValue());
		hisc_fisioterapia.setAnte_fam_observaciones(tbxAnte_fam_observaciones.getValue());
		hisc_fisioterapia.setAnte_fam_asma(bandboxAnte_fam_asma.getValue());
		hisc_fisioterapia.setAnte_fam_alergicos(bandboxAnte_fam_alergicos.getValue());
		hisc_fisioterapia.setAnte_fam_infecciosa_vih(bandboxAnte_fam_infecciosa_vih.getValue());
		hisc_fisioterapia.setAnte_fam_infecciosa_sifilis(bandboxAnte_fam_infecciosa_sifilis.getValue());
		hisc_fisioterapia.setAnte_fam_infecciosa_tuberculosis(bandboxAnte_fam_infecciosa_tuberculosis.getValue());
		hisc_fisioterapia.setAnte_fam_infecciosa_hepatitisb(bandboxAnte_fam_infecciosa_hepatitisb.getValue());
		hisc_fisioterapia.setAnte_fam_infecciosa_lepra(bandboxAnte_fam_infecciosa_lepra.getValue());
		
		hisc_fisioterapia.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
		hisc_fisioterapia.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
		hisc_fisioterapia.setCreacion_user(usuarios.getCodigo().toString());
		hisc_fisioterapia.setUltimo_user(usuarios.getCodigo().toString());
		hisc_fisioterapia.setFecha_ingreso(admision_seleccionada.getFecha_ingreso());
		hisc_fisioterapia.setFecha_inicial(new Timestamp(
				infoPacientes.getFecha_inicial().getTime())); 
		return hisc_fisioterapia;
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if(validarForm()){
				//Cargamos los componentes //
				//Obtenemos datos
				Hisc_fisioterapia hisc_fisioterapia = getBean();
				Map<String, Object> mapReceta = receta_ripsAction
						.obtenerDatos();
				Impresion_diagnostica impresion_diagnostica = macroImpresion_diagnostica
						.obtenerImpresionDiagnostica();

				receta_ripsAction.actualizarDiagnosticos(impresion_diagnostica);
				List<Antecedentes_personales> listadoAntecedentes = obtenerAntecedentesPersonales();
				Remision_interna remision_interna = macroRemision_interna
						.obtenerRemisionInterna();
				
				// mapeamos los datos que vamos a guardar
				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("histotia_fisioterapia", hisc_fisioterapia);
				datos.put("receta_medica", mapReceta);
				datos.put("admision", admision_seleccionada);
				datos.put("accion", tbxAccion.getValue()); 
				datos.put("impresion_diagnostica", impresion_diagnostica);
				datos.put("listadoAntecedentes", listadoAntecedentes);
				datos.put("remision_interna", remision_interna);
				
				hisc_fisioterapiaService.guardar(datos);
				
		        MensajesUtil.mensajeInformacion("Informacion ..","Los datos se guardaron satisfactoriamente");
		     // Despues de guardar la historia
				tbxAccion.setValue("modificar");
				infoPacientes.setCodigo_historia(hisc_fisioterapia
						.getCodigo_historia());
				Receta_rips receta_rips = (Receta_rips) mapReceta
						.get("receta_rips");
				if (receta_rips != null)
					receta_ripsAction.mostrarDatos(receta_rips, false);
				
//		        deshabilitarCampos(true);
			}
		} catch (ImpresionDiagnosticaException e) {
			log.error(e.getMessage(), e);
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage()); 
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			MensajesUtil.mensajeError(e, "", this);
		}
		
	}
	
	private void cargarImpresionDiagnostica(
			Hisc_fisioterapia hisc_fisioterapia) throws Exception {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_empresa(codigo_empresa);
		impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
		impresion_diagnostica.setCodigo_historia(hisc_fisioterapia
				.getCodigo_historia());
		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);
		macroImpresion_diagnostica.mostrarImpresionDiagnostica(
				impresion_diagnostica, true);
	}
	
	public List<Antecedentes_personales> obtenerAntecedentesPersonales() {
		List<Antecedentes_personales> listadoAntecedentes = new ArrayList<Antecedentes_personales>();
		for (Elemento antecedente : elementosAntecentesPersonales) {
			if (gridAntecedentes_personales
					.hasFellow("radiogroup_ant_personales_"
							+ antecedente.getCodigo())) {
				Radiogroup radiogroup = (Radiogroup) gridAntecedentes_personales
						.getFellow("radiogroup_ant_personales_"
								+ antecedente.getCodigo());
				Textbox textbox = (Textbox) gridAntecedentes_personales
						.getFellow("textbox_ant_personales_"
								+ antecedente.getCodigo());

				String respuesta = radiogroup.getSelectedIndex() == -1 ? "N"
						: radiogroup.getSelectedItem().getValue().toString();

				Antecedentes_personales antecedentes_personales = new Antecedentes_personales();
				antecedentes_personales.setCodigo_antecente(antecedente
						.getCodigo());
				antecedentes_personales.setCodigo_empresa(codigo_empresa);
				antecedentes_personales.setCodigo_sucursal(codigo_sucursal);
				antecedentes_personales.setRespuesta(respuesta);
				if (antecedente.getCodigo().equals("32")) {
					antecedentes_personales
							.setObservacion(Utilidades.obtenerXmlTextbox(textbox));
				} else {
					antecedentes_personales.setObservacion(textbox.getValue());
				}
				listadoAntecedentes.add(antecedentes_personales);
			}
		}
		return listadoAntecedentes;
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Hisc_fisioterapia hisc_fisioterapia = (Hisc_fisioterapia)obj;
		try{
//			tbxCodigo_historia.setValue(hisc_fisioterapia.getCodigo_historia());
//			tbxCodigo_prestador.setValue(hisc_fisioterapia.getCodigo_prestador());
//			tbxNro_identificacion.setValue(hisc_fisioterapia.getNro_identificacion());
			tbxAcompaniante.setValue(hisc_fisioterapia.getAcompaniante());
//			tbxRelacion_acompaniante.setValue(hisc_fisioterapia.getRelacion_acompaniante());
//			tbxTelefono_acompaniante.setValue(hisc_fisioterapia.getTelefono_acompaniante());
			
			cargarPrestador(hisc_fisioterapia.getCodigo_prestador());
			
			Utilidades.setValueFrom(lbxLateralidad_motora,  hisc_fisioterapia.getLateralidad_motora());
			tbxQuirurgicos_toxicos.setValue(hisc_fisioterapia.getQuirurgicos_toxicos());
			tbxFarmacologicos.setValue(hisc_fisioterapia.getFarmacologicos());
			tbxSicologicos.setValue(hisc_fisioterapia.getSicologicos());
			tbxMotivo_consulta.setValue(hisc_fisioterapia.getMotivo_consulta());
			dbxEval_pa.setValue(hisc_fisioterapia.getEval_pa());
			dbxEval_fc.setValue(hisc_fisioterapia.getEval_fc());
			dbxEval_fr.setValue(hisc_fisioterapia.getEval_fr());
			dbxEval_peso.setValue(hisc_fisioterapia.getEval_peso());
			dbxEval_temp.setValue(hisc_fisioterapia.getEval_temp());
			dbxEval_talla.setValue(hisc_fisioterapia.getEval_talla());
			
			tbxFis_torax.setValue(hisc_fisioterapia.getFis_totax()); 
			tbxFis_pulmones.setValue(hisc_fisioterapia.getFis_pulmones());
			tbxFs_abdomen.setValue(hisc_fisioterapia.getFs_abdomen());
			tbxFis_postura.setValue(hisc_fisioterapia.getFis_postura());
			tbxFis_rdm.setValue(hisc_fisioterapia.getFis_rdm());
			tbxFis_tono_muscular.setValue(hisc_fisioterapia.getFis_tono_muscular());
			tbxFis_fuerza_muscular.setValue(hisc_fisioterapia.getFis_fuerza_muscular());
			tbxFis_limitacion_discapacidad.setValue(hisc_fisioterapia.getFis_limitacion_discapacidad());
			tbxFis_coloracion.setValue(hisc_fisioterapia.getFis_coloracion());
			tbxFis_hidratacion.setValue(hisc_fisioterapia.getFis_hidratacion());
			tbxFis_temperatura.setValue(hisc_fisioterapia.getFis_temperatura());
			tbxFis_sudoracion.setValue(hisc_fisioterapia.getFis_sudoracion());
			tbxFis_heridas.setValue(hisc_fisioterapia.getFis_heridas());
			tbxFis_cicatrices.setValue(hisc_fisioterapia.getFis_cicatrices());
			
			Utilidades.setValueFrom(rgFis_dolor, hisc_fisioterapia.getFis_dolor()); 
			
			Utilidades.seleccionarListitem(lbxRelacion,
					hisc_fisioterapia.getRelacion_acompaniante());
			
			tbxIntervencion_objetivo.setValue(hisc_fisioterapia.getIntervencion_objetivo());
			tbxIntervencion_tratamiento.setValue(hisc_fisioterapia.getIntervencion_tratamiento());
//			tbxNro_ingreso.setValue(hisc_fisioterapia.getNro_ingreso());
//			tbxVia_ingreso.setValue(hisc_fisioterapia.getVia_ingreso());
			
			
			bandboxAnte_fam_hipertension.setValue(hisc_fisioterapia.getAnte_fam_hipertension());
			bandboxAnte_fam_ecv.setValue(hisc_fisioterapia.getAnte_fam_ecv());
			bandboxAnte_fam_enf_coronaria.setValue(hisc_fisioterapia.getAnte_fam_enf_coronaria());
			bandboxAnte_fam_muerte_im_acv.setValue(hisc_fisioterapia.getAnte_fam_muerte_im_acv());
			bandboxAnte_fam_dislipidemia.setValue(hisc_fisioterapia.getAnte_fam_dislipidemia());
			bandboxAnte_fam_nefropatias.setValue(hisc_fisioterapia.getAnte_fam_nefropatias());
			bandboxAnte_fam_obesos.setValue(hisc_fisioterapia.getAnte_fam_obesos());
			bandboxAnte_fam_diabetes.setValue(hisc_fisioterapia.getAnte_fam_diabetes());
			bandboxAnte_fam_enf_mental.setValue(hisc_fisioterapia.getAnte_fam_enf_mental());
			bandboxAnte_fam_cancer.setValue(hisc_fisioterapia.getAnte_fam_cancer());
			bandboxAnte_fam_hematologia.setValue(hisc_fisioterapia.getAnte_fam_hematologia());
			bandboxAnte_fam_otros.setValue(hisc_fisioterapia.getAnte_fam_otros());
			tbxAnte_fam_estecifique.setValue(hisc_fisioterapia.getAnte_fam_estecifique());
			tbxAnte_fam_observaciones.setValue(hisc_fisioterapia.getAnte_fam_observaciones());
			bandboxAnte_fam_asma.setValue(hisc_fisioterapia.getAnte_fam_asma());
			bandboxAnte_fam_alergicos.setValue(hisc_fisioterapia.getAnte_fam_alergicos());
			bandboxAnte_fam_infecciosa_vih.setValue(hisc_fisioterapia.getAnte_fam_infecciosa_vih());
			bandboxAnte_fam_infecciosa_sifilis.setValue(hisc_fisioterapia.getAnte_fam_infecciosa_sifilis());
			bandboxAnte_fam_infecciosa_tuberculosis.setValue(hisc_fisioterapia.getAnte_fam_infecciosa_tuberculosis());
			bandboxAnte_fam_infecciosa_hepatitisb.setValue(hisc_fisioterapia.getAnte_fam_infecciosa_hepatitisb());
			bandboxAnte_fam_infecciosa_lepra.setValue(hisc_fisioterapia.getAnte_fam_infecciosa_lepra());
			
			cargarAntecedentesPersonales(obtenerMapaAntecedentesPersonales(hisc_fisioterapia));
			
			cargarImpresionDiagnostica(hisc_fisioterapia); 
			
			cargarRemisionInterna(hisc_fisioterapia);
			 
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
			
			if (tbxAccion.getValue().equals(
					OpcionesFormulario.REGISTRAR.toString())) {
				deshabilitarCampos(false);
			}else{
				deshabilitarCampos(true);
			}
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(this, sw, idsExcluyentes);
		if (!sw) {
			((Button) getFellow("btGuardar")).setDisabled(false);
			((Button) getFellow("btGuardar")).setImage("/images/Save16.gif");
		} else {
			((Button) getFellow("btGuardar")).setDisabled(true);
			((Button) getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");
		}

		if (rol_usuario.equals(IRoles.FISIOTERAPEUTA)
				|| (admision_seleccionada.getVia_ingreso().equals(
						IVias_ingreso.TERAPIA_RESPIRATORIA) && rol_usuario
						.equals(IRoles.FISIOTERAPEUTA_FISICA))) {
			bandboxPrestador.setDisabled(true);
		} else {
			bandboxPrestador.setDisabled(sw);
		}
	}
	
	private void cargarRemisionInterna(Hisc_fisioterapia hisc_fisioterapia) throws Exception {
		Remision_interna remision_interna = new Remision_interna();
		remision_interna.setCodigo_historia(hisc_fisioterapia
				.getCodigo_historia());
		remision_interna.setCodigo_empresa(hisc_fisioterapia
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(hisc_fisioterapia
				.getCodigo_sucursal());

		remision_interna = getServiceLocator().getServicio(
				Remision_internaService.class).consultar(remision_interna);

		macroRemision_interna.mostrarRemisionInterna(remision_interna, true);
	}

	private Map<String, Antecedentes_personales> obtenerMapaAntecedentesPersonales(
			Hisc_fisioterapia hisc_fisioterapia) {
		Map<String, Antecedentes_personales> mapaAntecedentesPersonales = new HashMap<String, Antecedentes_personales>();
		Map map = new HashMap();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("nro_historia", hisc_fisioterapia.getCodigo_historia());

		List<Antecedentes_personales> listaAntecedentes_personales = getServiceLocator()
				.getAntecedentesPersonalesService().listar(map);
		for (Antecedentes_personales antecedentes_personales : listaAntecedentes_personales) {
			mapaAntecedentesPersonales.put(
					antecedentes_personales.getCodigo_antecente(),
					antecedentes_personales);
		}
		return mapaAntecedentesPersonales;
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Hisc_fisioterapia hisc_fisioterapia = (Hisc_fisioterapia)obj;
		try{
			int result = hisc_fisioterapiaService.eliminar(hisc_fisioterapia);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (HealthmanagerException e) {
			MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
		}catch(RuntimeException r){
			MensajesUtil.mensajeError(r, "", this);
		}
	}
	
	/* ANTECEDENTES FAMILIARES */
	
	public void generarContendioAntecedentesFamiliares(
			Bandbox bandbox_antecedente) {
		Utilidades.generarContendioAntecedentesFamiliares(bandbox_antecedente); 
	}
	
	
	/* ANTECEDENTES PERSONALES */
	
	private void cargarAntecedentesPersonales(
			Map<String, Antecedentes_personales> mapaAntecedentesPersonales) {
		elementosAntecentesPersonales = getServiceLocator()
				.getElementoService().listar("ante_personales");
		if (gridAntecedentes_personales.getRows() != null) {
			gridAntecedentes_personales.getRows().getChildren().clear();
		}
		hlayoutAntecedentes_personales.getChildren().clear();
		if (elementosAntecentesPersonales.isEmpty()) {
			gridAntecedentes_personales.setVisible(false);
		} else {
			Rows rowsFilas = gridAntecedentes_personales.getRows();
			if (rowsFilas == null) {
				rowsFilas = new Rows();
				gridAntecedentes_personales.appendChild(rowsFilas);
			}

			for (int i = 0; i < elementosAntecentesPersonales.size(); i++) {
				Row fila = new Row();
				Elemento antecedente = elementosAntecentesPersonales.get(i);
				crearCeldaAntecedentesPersonales(antecedente, fila,
						mapaAntecedentesPersonales.get(antecedente.getCodigo()));
				i++;
				if (i < elementosAntecentesPersonales.size()) {
					antecedente = elementosAntecentesPersonales.get(i);
					crearCeldaAntecedentesPersonales(antecedente, fila,
							mapaAntecedentesPersonales.get(antecedente
									.getCodigo()));
				}
				rowsFilas.appendChild(fila);
			}

		}

		if (opciones_via_ingreso != null &&  opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(gridAntecedentes_personales,
					true, new String[] { "northEditar" });
		}
	}
	
	
	private void crearCeldaAntecedentesPersonales(final Elemento antecedente,
			Row fila, Antecedentes_personales antecedentes_personales) {
		Cell celda = new Cell();
		celda.appendChild(new Label(antecedente.getDescripcion()));
		fila.appendChild(celda);

		celda = new Cell();
		celda.setColspan(2);
		final Radiogroup radiogroup = new Radiogroup();
		radiogroup
				.setId("radiogroup_ant_personales_" + antecedente.getCodigo());
		Radio radio = new Radio();
		radio.setValue("S");
		radiogroup.appendChild(radio);

		Space space = new Space();
		space.setWidth("5px");
		radiogroup.appendChild(space);

		radio = new Radio();
		radio.setValue("N");
		radiogroup.appendChild(radio);

		celda.appendChild(radiogroup);
		radiogroup.setSelectedIndex(1);
		fila.appendChild(celda);

		celda = Utilidades.obtenerCell("", Textbox.class, true, false);
		final Textbox tbxObservaciones = (Textbox) celda.getFirstChild();
		tbxObservaciones.setId("textbox_ant_personales_"
				+ antecedente.getCodigo());

		tbxObservaciones.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						hlayoutAntecedentes_personales.getChildren().clear();
						final Popup popupObservaciones = generarPopupObservaciones(
								antecedente, tbxObservaciones,
								hlayoutAntecedentes_personales);
						popupObservaciones.open(tbxObservaciones, "after_end");
						if (popupObservaciones
								.hasFellow("textbox_observaciones_ant_per_"
										+ antecedente.getCodigo())) {
							((Textbox) popupObservaciones
									.getFellow("textbox_observaciones_ant_per_"
											+ antecedente.getCodigo()))
									.setFocus(true);
						}

					}

				});

		tbxObservaciones.setVisible(false);

		if (antecedentes_personales != null) {
			Utilidades.seleccionarRadio(radiogroup,
					antecedentes_personales.getRespuesta());
			if (!antecedentes_personales.getCodigo_antecente().equals("32"))
				tbxObservaciones.setValue(antecedentes_personales
						.getObservacion());
			else
				Utilidades.mostrarXmlTextbox(tbxObservaciones,
						ConvertidorXmlToMap
								.convertirToMap(antecedentes_personales
										.getObservacion()));
			if (antecedentes_personales.getRespuesta().equals("S")) {
				tbxObservaciones.setVisible(true);
			} else {
				tbxObservaciones.setVisible(false);
				tbxObservaciones.setValue("");
			}
		}

		radiogroup.addEventListener(Events.ON_CHECK,
				new EventListener<CheckEvent>() {

					@Override
					public void onEvent(CheckEvent event) throws Exception {
						Checkbox checkbox = (Checkbox) event.getTarget();
						String valor = checkbox.getValue();
						if (valor.equals("S")) {
							final Popup popupObservaciones = generarPopupObservaciones(
									antecedente, tbxObservaciones,
									hlayoutAntecedentes_personales);
							tbxObservaciones.setVisible(true);
							popupObservaciones.open(tbxObservaciones,
									"after_end");
							if (popupObservaciones
									.hasFellow("textbox_observaciones_ant_per_"
											+ antecedente.getCodigo())) {
								((Textbox) popupObservaciones
										.getFellow("textbox_observaciones_ant_per_"
												+ antecedente.getCodigo()))
										.setFocus(true);
							}

							checkbox.addEventListener(Events.ON_CLICK,
									new EventListener<Event>() {

										@Override
										public void onEvent(Event event)
												throws Exception {
											hlayoutAntecedentes_personales
													.getChildren().clear();
											Popup popupObservaciones_aux = generarPopupObservaciones(
													antecedente,
													tbxObservaciones,
													hlayoutAntecedentes_personales);
											tbxObservaciones.setVisible(true);
											popupObservaciones_aux.open(
													tbxObservaciones,
													"after_end");
											if (popupObservaciones_aux.hasFellow("textbox_observaciones_ant_per_"
													+ antecedente.getCodigo())) {
												((Textbox) popupObservaciones_aux.getFellow("textbox_observaciones_ant_per_"
														+ antecedente
																.getCodigo()))
														.setFocus(true);
											}
										}

									});

						} else {
							tbxObservaciones.setVisible(false);
							tbxObservaciones.setText("");
							hlayoutAntecedentes_personales.getChildren()
									.clear();
							Iterator<EventListener<? extends Event>> eventos = radiogroup
									.getItemAtIndex(0)
									.getEventListeners(Events.ON_CLICK)
									.iterator();
							while (eventos.hasNext()) {
								radiogroup.getItemAtIndex(0)
										.removeEventListener(Events.ON_CLICK,
												eventos.next());
							}
						}
					}

				});

		fila.appendChild(celda);

	}
	
	
	private Popup generarPopupObservaciones(Elemento elemento,
			final Textbox textboxObservacion, Component contenedor) {

		final Popup popup = new Popup();
		final Textbox textbox = new Textbox();

		popup.addEventListener(Events.ON_OPEN, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				textbox.setValue(textboxObservacion.getValue());
				// textbox.setFocus(true);
			}
		});

		Window window = new Window();
		window.setBorder(true);
		window.setClosable(true);
		window.setTitle("Observaciones " + elemento.getDescripcion());

		window.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				popup.close();
			}

		});

		Vlayout vlayout = new Vlayout();
		vlayout.setStyle("margin:5px");

		if (!elemento.getCodigo().equals("32")) {
			textbox.setId("textbox_observaciones_ant_per_"
					+ elemento.getCodigo());
			textbox.setWidth("350px");
			textbox.setRows(8);

			vlayout.appendChild(textbox);
			textbox.setValue(textboxObservacion.getValue());
			textbox.addEventListener("onChanging", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					InputEvent inputevent = (InputEvent) event;
					textboxObservacion.setValue(inputevent.getValue());
				}
			});

		} else {
			Hbox hbox = new Hbox();

			final Checkbox checkbox1 = new Checkbox("VIH");
			final Checkbox checkbox2 = new Checkbox("Hepatitis B");
			final Checkbox checkbox3 = new Checkbox("Sífilis");
			final Checkbox checkbox4 = new Checkbox("Tuberculosis");
			final Checkbox checkbox5 = new Checkbox("Lepra");

			checkbox1.setValue("VIH");
			checkbox2.setValue("HEPATITISB");
			checkbox3.setValue("SIFILIS");
			checkbox4.setValue("TUBERCULOSIS");
			checkbox5.setValue("LEPRA");

			hbox.appendChild(checkbox1);
			hbox.appendChild(new Space());
			hbox.appendChild(checkbox2);
			hbox.appendChild(new Space());
			hbox.appendChild(checkbox3);
			hbox.appendChild(new Space());
			hbox.appendChild(checkbox4);
			hbox.appendChild(new Space());
			hbox.appendChild(checkbox5);

			Map<String, Object> mapa_aux = null;
			try {
				mapa_aux = ConvertidorXmlToMap
						.convertirToMap(Utilidades.obtenerXmlTextbox(textboxObservacion));
			} catch (Exception e) {
				mapa_aux = new HashMap<String, Object>();
			}

			checkbox1.setChecked(mapa_aux.containsKey(checkbox1.getValue()));

			checkbox2.setChecked(mapa_aux.containsKey(checkbox2.getValue()));

			checkbox3.setChecked(mapa_aux.containsKey(checkbox3.getValue()));

			checkbox4.setChecked(mapa_aux.containsKey(checkbox4.getValue()));

			checkbox5.setChecked(mapa_aux.containsKey(checkbox5.getValue()));

			final Textbox textbox_observacion = new Textbox();
			textbox_observacion.setHflex("1");
			textbox_observacion.setRows(2);

			if (mapa_aux.containsKey("OBSERVACION")) {
				textbox_observacion.setValue(mapa_aux.get("OBSERVACION")
						.toString());
			} else {
				textbox_observacion.setValue("");
				mapa_aux.put("OBSERVACION", textbox_observacion.getValue());
			}

			final Map<String, Object> mapa_contenido = mapa_aux;

			EventListener<CheckEvent> eventListener = new EventListener<CheckEvent>() {
				@Override
				public void onEvent(CheckEvent event) throws Exception {
					if (event.getTarget() != null) {
						Checkbox checkbox = (Checkbox) event.getTarget();
						if (checkbox.isChecked()) {
							mapa_contenido.put(checkbox.getValue().toString(),
									checkbox.getValue());
						} else {
							mapa_contenido.remove(checkbox.getValue());
						}

						Utilidades.mostrarXmlTextbox(textboxObservacion, mapa_contenido);

					}

				}
			};

			textbox_observacion.addEventListener(Events.ON_CHANGE,
					new EventListener<InputEvent>() {

						@Override
						public void onEvent(InputEvent event) throws Exception {
							mapa_contenido.put("OBSERVACION", event.getValue());
							Utilidades.mostrarXmlTextbox(textboxObservacion,
									mapa_contenido);

						}

					});

			checkbox1.addEventListener("onCheck", eventListener);
			checkbox2.addEventListener("onCheck", eventListener);
			checkbox3.addEventListener("onCheck", eventListener);
			checkbox4.addEventListener("onCheck", eventListener);
			checkbox5.addEventListener("onCheck", eventListener);

			vlayout.appendChild(hbox);
			Label labelObservacion = new Label("observacion");
			labelObservacion.setStyle("font-weight:bold");
			vlayout.appendChild(labelObservacion);
			vlayout.appendChild(textbox_observacion);

		}
		window.appendChild(vlayout);
		popup.appendChild(window);
		contenedor.appendChild(popup);
		if (opciones_via_ingreso != null && opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil.deshabilitarComponentes(
					hlayoutAntecedentes_personales, true,
					new String[] { "northEditar" });
		}
		return popup;
	}
	
	/* MODULO DE RECETA MEDICA */
	
	public void onMostrarModuloFarmacologico() throws Exception {
		if (receta_ripsAction != null)
			receta_ripsAction.detach();
		//log.info("creando la ventana receta_ripsAction");
		Map parametros = new HashMap();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", nro_ingreso_admision);
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision_seleccionada);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");

		parametros.put("ocultarRecomendaciones", "_Ocultar");

		receta_ripsAction = (Receta_rips_internoAction) Executions
				.createComponents("/pages/receta_rips_interno.zul", null,
						parametros);
		receta_ripsAction.inicializar(this);
		divModuloFarmacologico.appendChild(receta_ripsAction);

	}
	
	
	public void accionForm(OpcionesFormulario opciones_formulario, Object dato)
			throws Exception {
		tbxAccion.setValue(opciones_formulario.toString());
		if (opciones_formulario.equals(OpcionesFormulario.REGISTRAR)) {
			onOpcionFormularioRegistrar();
		} else if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)
				|| opciones_formulario.equals(OpcionesFormulario.MOSTRAR)) {
			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
			mostrarDatos(dato);
			if (opciones_formulario.equals(OpcionesFormulario.MODIFICAR)) {
				FormularioUtil.deshabilitarComponentes(groupboxEditar, false,
						idsExcluyentes);
			}
		} else if (opciones_formulario.equals(OpcionesFormulario.CONSULTAR)) {
			onOpcionFormularioConsultar();
		}

	}
	
	private void onOpcionFormularioRegistrar() throws Exception {
		groupboxConsulta.setVisible(false);
		groupboxEditar.setVisible(true);
		limpiarDatos();
		if (admision_seleccionada != null) {
			this.nro_ingreso_admision = admision_seleccionada.getNro_ingreso();
			infoPacientes.setFecha_inicio(new Date());
			cargarInformacion_paciente();
			onMostrarModuloFarmacologico();
			receta_ripsAction.obtenerBotonAgregar().setVisible(true);
			cargarPrestador(admision_seleccionada.getCodigo_medico());
		}
		btImprimir.setVisible(false);
	}
	
	public void cargarPrestador(String codigo_medico) throws Exception {
		//log.info("ejecutando metodo @cargarPrestador()");
		if (rol_usuario.equals(IConstantes.ROL_MEDICO_CONSULTA_EXTERNA)) {
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(codigo_empresa);
			prestadores.setCodigo_sucursal(codigo_sucursal);
			prestadores.setNro_identificacion(codigo_medico);
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
			if (admision_seleccionada != null) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(codigo_empresa);
				prestadores.setCodigo_sucursal(codigo_sucursal);
				prestadores.setNro_identificacion(codigo_medico);
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);

				if (prestadores != null) {
					bandboxPrestador.setValue(prestadores
							.getNro_identificacion());
					tbxNomPrestador.setValue(prestadores.getNombres() + " "
							+ prestadores.getApellidos());
				} else {
					bandboxPrestador
							.setValue((prestadores != null ? prestadores
									.getNro_identificacion() : "000000000"));
					tbxNomPrestador.setValue((prestadores != null ? prestadores
							.getNombres() + " " + prestadores.getApellidos()
							: "MEDICO POR DEFECTO"));
				}
			} else {
				bandboxPrestador.setValue("000000000");
				tbxNomPrestador.setValue("MEDICO POR DEFECTO");
			}
		}
	}

	private void onOpcionFormularioConsultar() throws Exception {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}
	
	
	/* METODOS DE LA HISTORIA */

	@Override
	public void initHistoria() throws Exception {
		macroImpresion_diagnostica.inicializarMacro(this,
				admision_seleccionada, admision_seleccionada.getVia_ingreso());
		macroRemision_interna.inicializarMacro(this, admision_seleccionada,
				admision_seleccionada.getVia_ingreso());
//		if (parametros_empresa != null
//				&& parametros_empresa.getSignos_enfermera().equals("S")) {
//			toolbarbuttonCargar_signos.setVisible(true);
//		} else {
//			toolbarbuttonCargar_signos.setVisible(false);
//		}

		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
//			via_ingreso = admision_seleccionada.getVia_ingreso();
			accionForm(OpcionesFormulario.CONSULTAR, null);
			btnCancelar.setVisible(true);
		} else {
			if (admision_seleccionada != null) {
				toolbarbuttonPaciente_admisionado1
						.setLabel(admision_seleccionada.getPaciente()
								.getNombreCompleto());
				toolbarbuttonPaciente_admisionado2
						.setLabel(admision_seleccionada.getPaciente()
								.getNombreCompleto());

				if (admision_seleccionada.getAtendida()) {
					opciones_via_ingreso = Opciones_via_ingreso.MOSTRAR;

					Hisc_fisioterapia hisc_fisioterapia = new Hisc_fisioterapia();
					hisc_fisioterapia.setCodigo_empresa(empresa
							.getCodigo_empresa());
					hisc_fisioterapia.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					hisc_fisioterapia
							.setNro_identificacion(admision_seleccionada
									.getNro_identificacion());
					hisc_fisioterapia.setVia_ingreso(admision_seleccionada
							.getVia_ingreso());
					hisc_fisioterapia.setNro_ingreso(admision_seleccionada
							.getNro_ingreso());
					hisc_fisioterapia.setVia_ingreso(admision_seleccionada.getVia_ingreso());

					hisc_fisioterapia = getServiceLocator()
							.getServicio(Hisc_fisioterapiaService.class).consultar(
									hisc_fisioterapia); 

					if (hisc_fisioterapia != null) {
						accionForm(OpcionesFormulario.MOSTRAR,
								hisc_fisioterapia);
						infoPacientes.cargarInformacion(admision_seleccionada, this, null);
						infoPacientes.setCodigo_historia(hisc_fisioterapia
								.getCodigo_historia());
						//log.info("Codigo de la historia ===> "+infoPacientes.getCodigo_historia());
					} else {
						groupboxEditar.setVisible(false);
						throw new ValidacionRunTimeException(
								"NO hay una historia clinica relacionada a este paciente admitido");
					}
				} else {
					if (opciones_via_ingreso
							.equals(Opciones_via_ingreso.REGISTRAR)) {
						accionForm(OpcionesFormulario.REGISTRAR, null);
						btnCancelar.setVisible(false);
					} else {
						accionForm(OpcionesFormulario.CONSULTAR, null);
						btnCancelar.setVisible(true);
					}
				}

			}

		}
	}

	@Override
	public void inicializarVista(String tipo) {
//		if (tipo.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
//			tipo_historia = IConstantes.TIPO_HISTORIA_PRIMERA_VEZ;
//		} else {
//			tipo_historia = IConstantes.TIPO_HISTORIA_CONTROL;
//		}
	}

	@Override
	public void cargarInformacion_paciente() {
		tbxAcompaniante.setValue(cita_seleccionada.getAcompaniante() + " "
				+ cita_seleccionada.getApellidos_acomp());
		dbxTel_acompaniante.setValue(ConvertidorDatosUtil
				.convertirDato(cita_seleccionada.getTel_acompaniante()));
		tbxCedula_acomp.setValue(cita_seleccionada.getCedula_acomp());
		Utilidades.seleccionarListitem(lbxRelacion,
				cita_seleccionada.getRelacion());
//		onSeleccionarRelacionAcompaniante();
//		tbxOtro_acompaniante.setValue(cita_seleccionada.getOtro_acompaniante());
		infoPacientes.cargarInformacion(admision_seleccionada, this,
				new InformacionPacienteIMG() {
					@Override
					public void ejecutarProceso() throws Exception {
						if (tbxAccion.getValue().equalsIgnoreCase(
								OpcionesFormulario.REGISTRAR.toString())) {
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("nro_identificacion",
									admision_seleccionada
											.getNro_identificacion());
							parametros.put("via_ingreso", via_ingreso);
							parametros.put("order_desc", "order_desc");

							List<Hisc_fisioterapia> listado_historias = getServiceLocator()
									.getServicio(Hisc_fisioterapiaService.class).listar(
											parametros); 

							if (!listado_historias.isEmpty()) {
								final Hisc_fisioterapia hisc_fisioterapia = listado_historias
										.get(0);
								inicializarVista(IConstantes.TIPO_HISTORIA_CONTROL);
								Messagebox.show(
										"Existe una historia clinica creada para la fecha "
												+ formatFecha
														.format(hisc_fisioterapia
																.getFecha_ingreso())
												+ ". ¿Desea cargar la informacion?",
										"Cargar informacion anterior",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,

										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if (event.getName().equals(
														"onYes")) {
													cargarInformacion_historia_anterior(hisc_fisioterapia);
													btnCancelar.setVisible(false);
												}
											}
										});

//								toolbarbuttonTipo_historia
//										.setLabel("Creando historia clinica");
								admision_seleccionada.setPrimera_vez("N");
							} else {
//								toolbarbuttonTipo_historia
//										.setLabel("Creando historia clinica por primera vez");
								inicializarVista(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ);
								admision_seleccionada.setPrimera_vez("S");
							}
						} else {
//							if (tipo_historia
//									.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
//								Hisc_consulta_externa hisc_hd = new Hisc_consulta_externa();
//								hisc_hd.setCodigo_empresa(empresa
//										.getCodigo_empresa());
//								hisc_hd.setCodigo_sucursal(sucursal
//										.getCodigo_sucursal());
//								hisc_hd.setCodigo_historia(codigo_historia_anterior);
//								hisc_hd.setVia_ingreso(via_ingreso);
//
//								hisc_hd = getServiceLocator()
//										.getHisc_consulta_externaService()
//										.consultar(hisc_hd);
//
//								if (hisc_hd != null) {
//									cargarInformacion_historia_anterior(hisc_hd);
//								}
//
//							}
						}

					}
				});
	}

	@Override
	public void cargarInformacion_historia_anterior(Object historia_anterior)
			throws Exception {
		groupboxConsulta.setVisible(false);
		groupboxEditar.setVisible(true);
		mostrarDatos(historia_anterior);
		tbxAccion.setValue(OpcionesFormulario.REGISTRAR.toString());
		deshabilitarCampos(false);
	}

	@Override
	public void initMostrar_datos(Object historia_clinica) {
//		Hisc_fisioterapia hisc_consulta_externa = (Hisc_fisioterapia) historia_clinica;
		if (opciones_via_ingreso.equals(Opciones_via_ingreso.MOSTRAR)) {
			FormularioUtil
					.deshabilitarComponentes(groupboxEditar, true, idsExcluyentes);
//			if (hisc_consulta_externa.getTipo_historia().equals(
//					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
//				toolbarbuttonTipo_historia
//						.setLabel("Mostrando Historia Clinica por Primera Vez");
//			} else {
//				toolbarbuttonTipo_historia
//						.setLabel("Mostrando Historia Clinica de control/evolucion");
//			}
			((Button) getFellow("btGuardar")).setVisible(false);

		} else {
//			if (hisc_consulta_externa.getTipo_historia().equals(
//					IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
//				toolbarbuttonTipo_historia
//						.setLabel("Modificando Historia Clinica por Primera Vez");
//			} else {
//				toolbarbuttonTipo_historia
//						.setLabel("Modificando Historia Clinica de control/evolucion");
//			}

			((Button) getFellow("btGuardar")).setVisible(true);
		}

//		codigo_historia_anterior = hisc_consulta_externa
//				.getCodigo_historia_anterior();
//		tipo_historia = hisc_consulta_externa.getTipo_historia();
		
	}
}