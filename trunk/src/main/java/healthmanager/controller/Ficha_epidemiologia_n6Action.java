/*
 * ficha_epidemiologia_n6Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.bean.Ficha_epidemiologia_n6;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IFichas_epidemiologicas;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n6Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n6>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n6Action.class);
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_ficha;
	@View private Textbox tbxIdentificacion;
	@View private Textbox tbxNombrePaciente;
	@View private Textbox tbxTipo_identificacion;
	@View private Datebox dtbxFecha_creacion;
	@View private Textbox tbxCodigo_diagnostico;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	
	@View private Radiogroup rdbVacuna_fiebre_amarilla;
	@View private Datebox dtbxFecha_fiebre_amarilla;
	@View private Radiogroup rdbVacuna_hepatitis_b1;
	@View private Datebox dtbxFecha_hepatitis_b1;
	@View private Radiogroup rdbVacuna_hepatitis_b2;
	@View private Datebox dtbxFecha_hepatitis_b2;
	@View private Radiogroup rdbVacuna_hepatitis_b3;
	@View private Datebox dtbxFecha_hepatitis_b3;
	@View private Radiogroup rdbVacuna_hepatitis_a;
	@View private Datebox dtbxFecha_hepatitis_a;
	@View private Textbox tbxObservaciones_vacunas;
	@View private Checkbox chbFiebre;
	@View private Checkbox chbMialgias;
	@View private Checkbox chbArtralgias;
	@View private Checkbox chbCefalea;
	@View private Checkbox chbVomito;
	@View private Checkbox chbIctericia;
	@View private Checkbox chbHemoptisis;
	@View private Checkbox chbS_faget;
	@View private Checkbox chbHiperemia_conjuntival;
	@View private Checkbox chbHematemesis;
	@View private Checkbox chbOliguria;
	@View private Checkbox chbPetequias;
	@View private Checkbox chbMetrorragia;
	@View private Checkbox chbChoque;
	@View private Checkbox chbBrandicardia;
	@View private Checkbox chbMelenas;
	@View private Checkbox chbEquimosis;
	@View private Checkbox chbEpistaxis;
	@View private Checkbox chbHematuria;
	@View private Checkbox chbDolor_abdominal;
	@View private Checkbox chbFalla_renal;
	@View private Checkbox chbFalla_hepatica;
	@View private Doublebox dbxRecuento_leucocitos;
	@View private Doublebox dbxRecuento_plaquetas_inicial;
	@View private Doublebox dbxAst_tgo;
	@View private Doublebox dbxAlt_tgp;
	@View private Doublebox dbxBilirrubina_total;
	@View private Doublebox dbxBilirrubina_directa;
	@View private Doublebox dbxBilirrubina_indirecta;
	@View private Doublebox dbxBun;
	@View private Doublebox dbxCreatinina;
	@View private Radiogroup rdbIgm_fiebre_amarilla;
	@View private Radiogroup rdbRt_pcr;
	@View private Radiogroup rdbAislamiento_viral;
	@View private Radiogroup rdbViscerotomia;
	@View private Radiogroup rdbTejido_fresco;
	@View private Radiogroup rdbTejido_normal;
	@View private Radiogroup rdbDesplazamiento;
	@View private Datebox dtbxFecha_desplazamiento;
	@View private Textbox tbxMunicipio_desplazamiento;
	@View private Radiogroup rdbCaso_fiebre;
	@View private Radiogroup rdbOcurrencia;
	@View private Textbox tbxDireccion;
	@View private Radiogroup rdbPresencia_aedes;
	@View private Textbox tbxCodigo_medico;
	private final String[] idsExcluyentes = {};

	@View private Toolbarbutton btGuardar;
	
	@View private Row rowDesplazamiento;
	@View private Label lbFecha_desplazamiento;
	@View private Label lbMunicipio_desplazamiento;

	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@View private Toolbarbutton btImprimir;
	@View private North north_ficha;
	
	@Override
	public void init(){
		try {
			listarCombos();
			obtenerAdmision(admision);
			FormularioUtil.inicializarRadiogroupsDefecto(this);

			if(ficha_seleccionada != null){
				//log.info("consultar ficha-------> "+ficha_seleccionada);
				Ficha_epidemiologia_n6 ficha = new Ficha_epidemiologia_n6();
				ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
				ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
				ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
				ficha = (Ficha_epidemiologia_n6) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
				
				//log.info("consultar ficha 6-------> "+ficha);
				
				mostrarDatos(ficha);
				north_ficha.setVisible(true);
				btImprimir.setVisible(true);
				
			}else {

				north_ficha.setVisible(false);
				btImprimir.setVisible(false);
				
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
		}
	}
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo Ficha");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
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
			btGuardar.setVisible(false);
		}else{
			btGuardar.setVisible(true);
			limpiarDatos();
			dtbxFecha_fiebre_amarilla.setValue(null);
			dtbxFecha_hepatitis_b1.setValue(null);
			dtbxFecha_hepatitis_b2.setValue(null); 
			dtbxFecha_hepatitis_b3.setValue(null); 
			dtbxFecha_hepatitis_a.setValue(null);
			FormularioUtil.cargarRadiogroupsDefecto(this);
			
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarFichaEpidemiologia() {
			
		dbxRecuento_leucocitos.setStyle("background-color:white");
		dbxRecuento_plaquetas_inicial.setStyle("background-color:white");
		dbxAst_tgo.setStyle("background-color:white");
		dbxAlt_tgp.setStyle("background-color:white");
		dbxBilirrubina_total.setStyle("background-color:white");
		dbxBilirrubina_directa.setStyle("background-color:white");
		dbxBilirrubina_indirecta.setStyle("background-color:white");
		dbxBun.setStyle("background-color:white");
		dbxCreatinina.setStyle("background-color:white");
		tbxObservaciones_vacunas.setStyle("background-color:white");
		tbxDireccion.setStyle("background-color:white");
		
		
		boolean valida = true;
		
		if(dbxRecuento_leucocitos.getText().trim().equals("")){
			dbxRecuento_leucocitos.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(dbxRecuento_plaquetas_inicial.getText().trim().equals("")){
			dbxRecuento_plaquetas_inicial.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(dbxAst_tgo.getText().trim().equals("")){
			dbxAst_tgo.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(dbxAlt_tgp.getText().trim().equals("")){
			dbxAlt_tgp.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(dbxBilirrubina_total.getText().trim().equals("")){
			dbxBilirrubina_total.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(dbxBilirrubina_directa.getText().trim().equals("")){
			dbxBilirrubina_directa.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(dbxBilirrubina_indirecta.getText().trim().equals("")){
			dbxBilirrubina_indirecta.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(dbxBun.getText().trim().equals("")){
			dbxBun.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(dbxCreatinina.getText().trim().equals("")){
			dbxCreatinina.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		
		if(tbxObservaciones_vacunas.getText().trim().equals("")){
			tbxObservaciones_vacunas.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(tbxDireccion.getText().trim().equals("")){
			tbxDireccion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...","Los campos marcados con (*) son obligatorios");
		}
		
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}
			
			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
			
			List<Ficha_epidemiologia_n6> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n6.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n6 ficha_epidemiologia_n6 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n6, this));
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
		
		final Ficha_epidemiologia_n6 ficha_epidemiologia_n6 = (Ficha_epidemiologia_n6)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n6.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n6.getIdentificacion()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n6.getFecha_creacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n6);
			}
		});
		hbox.appendChild(img);
		
			
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n6 obtenerFichaEpidemiologia() {
					
				Ficha_epidemiologia_n6 ficha_epidemiologia_n6 = new Ficha_epidemiologia_n6();
				ficha_epidemiologia_n6.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n6.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n6.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n6.setIdentificacion(tbxIdentificacion.getValue());
				ficha_epidemiologia_n6.setFecha_creacion(new Timestamp(dtbxFecha_creacion.getValue().getTime()));
				ficha_epidemiologia_n6.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n6.setVacuna_fiebre_amarilla(rdbVacuna_fiebre_amarilla.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_fiebre_amarilla.getValue() != null) {
					ficha_epidemiologia_n6.setFecha_fiebre_amarilla(new Timestamp(dtbxFecha_fiebre_amarilla.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n6.setFecha_fiebre_amarilla(null);
				}
				
				ficha_epidemiologia_n6.setVacuna_hepatitis_b1(rdbVacuna_hepatitis_b1.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_hepatitis_b1.getValue() != null) {
					ficha_epidemiologia_n6.setFecha_hepatitis_b1(new Timestamp(dtbxFecha_hepatitis_b1.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n6.setFecha_hepatitis_b1(null);
				}
				
				ficha_epidemiologia_n6.setVacuna_hepatitis_b2(rdbVacuna_hepatitis_b2.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_hepatitis_b2.getValue() != null) {
					ficha_epidemiologia_n6.setFecha_hepatitis_b2(new Timestamp(dtbxFecha_hepatitis_b2.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n6.setFecha_hepatitis_b2(null);
				}
				
				ficha_epidemiologia_n6.setVacuna_hepatitis_b3(rdbVacuna_hepatitis_b3.getSelectedItem().getValue().toString());
				
				
				if (dtbxFecha_hepatitis_b3.getValue() != null) {
					ficha_epidemiologia_n6.setFecha_hepatitis_b3(new Timestamp(dtbxFecha_hepatitis_b3.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n6.setFecha_hepatitis_b3(null);
				}
				
				ficha_epidemiologia_n6.setVacuna_hepatitis_a(rdbVacuna_hepatitis_a.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_hepatitis_a.getValue() != null) {
					ficha_epidemiologia_n6.setFecha_hepatitis_a(new Timestamp(dtbxFecha_hepatitis_a.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n6.setFecha_hepatitis_a(null);
				}
				
				ficha_epidemiologia_n6.setObservaciones_vacunas(tbxObservaciones_vacunas.getValue());
				ficha_epidemiologia_n6.setFiebre(chbFiebre.isChecked());
				ficha_epidemiologia_n6.setMialgias(chbMialgias.isChecked());
				ficha_epidemiologia_n6.setArtralgias(chbArtralgias.isChecked());
				ficha_epidemiologia_n6.setCefalea(chbCefalea.isChecked());
				ficha_epidemiologia_n6.setVomito(chbVomito.isChecked());
				ficha_epidemiologia_n6.setIctericia(chbIctericia.isChecked());
				ficha_epidemiologia_n6.setHemoptisis(chbHemoptisis.isChecked());
				ficha_epidemiologia_n6.setS_faget(chbS_faget.isChecked());
				ficha_epidemiologia_n6.setHiperemia_conjuntival(chbHiperemia_conjuntival.isChecked());
				ficha_epidemiologia_n6.setHematemesis(chbHematemesis.isChecked());
				ficha_epidemiologia_n6.setOliguria(chbOliguria.isChecked());
				ficha_epidemiologia_n6.setPetequias(chbPetequias.isChecked());
				ficha_epidemiologia_n6.setMetrorragia(chbMetrorragia.isChecked());
				ficha_epidemiologia_n6.setChoque(chbChoque.isChecked());
				ficha_epidemiologia_n6.setBrandicardia(chbBrandicardia.isChecked());
				ficha_epidemiologia_n6.setMelenas(chbMelenas.isChecked());
				ficha_epidemiologia_n6.setEquimosis(chbEquimosis.isChecked());
				ficha_epidemiologia_n6.setEpistaxis(chbEpistaxis.isChecked());
				ficha_epidemiologia_n6.setHematuria(chbHematuria.isChecked());
				ficha_epidemiologia_n6.setDolor_abdominal(chbDolor_abdominal.isChecked());
				ficha_epidemiologia_n6.setFalla_renal(chbFalla_renal.isChecked());
				ficha_epidemiologia_n6.setFalla_hepatica(chbFalla_hepatica.isChecked());
				ficha_epidemiologia_n6.setRecuento_leucocitos(dbxRecuento_leucocitos.getValue() != null ? dbxRecuento_leucocitos.getValue() + "" : "");
				ficha_epidemiologia_n6.setRecuento_plaquetas_inicial(dbxRecuento_plaquetas_inicial.getValue() != null ? dbxRecuento_plaquetas_inicial.getValue() + "" : "");
				ficha_epidemiologia_n6.setAst_tgo(dbxAst_tgo.getValue() != null ? dbxAst_tgo.getValue() + "" : "");
				ficha_epidemiologia_n6.setAlt_tgp(dbxAlt_tgp.getValue() != null ? dbxAlt_tgp.getValue() + "" : "");
				ficha_epidemiologia_n6.setBilirrubina_total(dbxBilirrubina_total.getValue()!=null ? dbxBilirrubina_total.getValue()+ "" : "");
				ficha_epidemiologia_n6.setBilirrubina_directa(dbxBilirrubina_directa.getValue()!=null ? dbxBilirrubina_directa.getValue()+ "" : "");
				ficha_epidemiologia_n6.setBilirrubina_indirecta(dbxBilirrubina_indirecta.getValue()!=null ? dbxBilirrubina_indirecta.getValue()+ "" : "");
				ficha_epidemiologia_n6.setBun(dbxBun.getValue()!=null ? dbxBun.getValue()+ "" : "");
				ficha_epidemiologia_n6.setCreatinina(dbxCreatinina.getValue()!=null ? dbxCreatinina.getValue()+ "" : "");
				ficha_epidemiologia_n6.setIgm_fiebre_amarilla(rdbIgm_fiebre_amarilla.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n6.setRt_pcr(rdbRt_pcr.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n6.setAislamiento_viral(rdbAislamiento_viral.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n6.setViscerotomia(rdbViscerotomia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n6.setTejido_fresco(rdbTejido_fresco.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n6.setTejido_normal(rdbTejido_normal.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n6.setDesplazamiento(rdbDesplazamiento.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_desplazamiento.getValue() != null) {
					ficha_epidemiologia_n6.setFecha_desplazamiento(new Timestamp(dtbxFecha_desplazamiento.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n6.setFecha_desplazamiento(null);
				}
				
				ficha_epidemiologia_n6.setMunicipio_desplazamiento(tbxMunicipio_desplazamiento.getValue());
				ficha_epidemiologia_n6.setCaso_fiebre(rdbCaso_fiebre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n6.setOcurrencia(rdbOcurrencia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n6.setDireccion(tbxDireccion.getValue());
				ficha_epidemiologia_n6.setPresencia_aedes(rdbPresencia_aedes.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n6.setCodigo_medico(tbxCodigo_medico.getValue());
				ficha_epidemiologia_n6.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n6.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n6.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n6.setDelete_date(null);
				ficha_epidemiologia_n6.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n6.setDelete_user(null);
				
				return ficha_epidemiologia_n6;
				 
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n6 obj)throws Exception{
		Ficha_epidemiologia_n6 ficha_epidemiologia_n6 = (Ficha_epidemiologia_n6)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n6.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n6.getIdentificacion());

			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			dtbxFecha_creacion.setValue(ficha_epidemiologia_n6.getFecha_creacion());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n6.getCodigo_diagnostico());
			Utilidades.seleccionarRadio(rdbVacuna_fiebre_amarilla, ficha_epidemiologia_n6.getVacuna_fiebre_amarilla());
			dtbxFecha_fiebre_amarilla.setValue(ficha_epidemiologia_n6.getFecha_fiebre_amarilla());
			Utilidades.seleccionarRadio(rdbVacuna_hepatitis_b1, ficha_epidemiologia_n6.getVacuna_hepatitis_b1());
			dtbxFecha_hepatitis_b1.setValue(ficha_epidemiologia_n6.getFecha_hepatitis_b1());
			Utilidades.seleccionarRadio(rdbVacuna_hepatitis_b2, ficha_epidemiologia_n6.getVacuna_hepatitis_b2());
			dtbxFecha_hepatitis_b2.setValue(ficha_epidemiologia_n6.getFecha_hepatitis_b2());
			Utilidades.seleccionarRadio(rdbVacuna_hepatitis_b3, ficha_epidemiologia_n6.getVacuna_hepatitis_b3());
			dtbxFecha_hepatitis_b3.setValue(ficha_epidemiologia_n6.getFecha_hepatitis_b3());
			Utilidades.seleccionarRadio(rdbVacuna_hepatitis_a, ficha_epidemiologia_n6.getVacuna_hepatitis_a());
			dtbxFecha_hepatitis_a.setValue(ficha_epidemiologia_n6.getFecha_hepatitis_a());
			tbxObservaciones_vacunas.setValue(ficha_epidemiologia_n6.getObservaciones_vacunas());
			chbFiebre.setChecked(ficha_epidemiologia_n6.getFiebre());
			chbMialgias.setChecked(ficha_epidemiologia_n6.getMialgias());
			chbArtralgias.setChecked(ficha_epidemiologia_n6.getArtralgias());
			chbCefalea.setChecked(ficha_epidemiologia_n6.getCefalea());
			chbVomito.setChecked(ficha_epidemiologia_n6.getVomito());
			chbIctericia.setChecked(ficha_epidemiologia_n6.getIctericia());
			chbHemoptisis.setChecked(ficha_epidemiologia_n6.getHemoptisis());
			chbS_faget.setChecked(ficha_epidemiologia_n6.getS_faget());
			chbHiperemia_conjuntival.setChecked(ficha_epidemiologia_n6.getHiperemia_conjuntival());
			chbHematemesis.setChecked(ficha_epidemiologia_n6.getHematemesis());
			chbOliguria.setChecked(ficha_epidemiologia_n6.getOliguria());
			chbPetequias.setChecked(ficha_epidemiologia_n6.getPetequias());
			chbMetrorragia.setChecked(ficha_epidemiologia_n6.getMetrorragia());
			chbChoque.setChecked(ficha_epidemiologia_n6.getChoque());
			chbBrandicardia.setChecked(ficha_epidemiologia_n6.getBrandicardia());
			chbMelenas.setChecked(ficha_epidemiologia_n6.getMelenas());
			chbEquimosis.setChecked(ficha_epidemiologia_n6.getEquimosis());
			chbEpistaxis.setChecked(ficha_epidemiologia_n6.getEpistaxis());
			chbHematuria.setChecked(ficha_epidemiologia_n6.getHematuria());
			chbDolor_abdominal.setChecked(ficha_epidemiologia_n6.getDolor_abdominal());
			chbFalla_renal.setChecked(ficha_epidemiologia_n6.getFalla_renal());
			chbFalla_hepatica.setChecked(ficha_epidemiologia_n6.getFalla_hepatica());
			dbxRecuento_leucocitos.setValue((ficha_epidemiologia_n6.getRecuento_leucocitos() != null && !ficha_epidemiologia_n6.getRecuento_leucocitos().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n6.getRecuento_leucocitos()) : null);
			dbxRecuento_plaquetas_inicial.setValue((ficha_epidemiologia_n6.getRecuento_plaquetas_inicial() != null && !ficha_epidemiologia_n6.getRecuento_plaquetas_inicial().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n6.getRecuento_plaquetas_inicial()) : null);
			dbxAst_tgo.setValue((ficha_epidemiologia_n6.getAst_tgo() != null && !ficha_epidemiologia_n6.getAst_tgo().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n6.getAst_tgo()) : null);
			dbxAlt_tgp.setValue((ficha_epidemiologia_n6.getAlt_tgp() != null && !ficha_epidemiologia_n6.getAlt_tgp().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n6.getAlt_tgp()) : null);
			dbxBilirrubina_total.setValue((ficha_epidemiologia_n6.getBilirrubina_total() != null && !ficha_epidemiologia_n6.getBilirrubina_total().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n6.getBilirrubina_total()) : null);
			dbxBilirrubina_directa.setValue((ficha_epidemiologia_n6.getBilirrubina_directa() != null && !ficha_epidemiologia_n6.getBilirrubina_directa().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n6.getBilirrubina_directa()) : null);
			dbxBilirrubina_indirecta.setValue((ficha_epidemiologia_n6.getBilirrubina_indirecta() != null && !ficha_epidemiologia_n6.getBilirrubina_indirecta().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n6.getBilirrubina_indirecta()) : null);
			dbxBun.setValue((ficha_epidemiologia_n6.getBun() != null && !ficha_epidemiologia_n6.getBun().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n6.getBun()) : null);
			dbxCreatinina.setValue((ficha_epidemiologia_n6.getCreatinina() != null && !ficha_epidemiologia_n6.getCreatinina().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n6.getCreatinina()) : null);
			Utilidades.seleccionarRadio(rdbIgm_fiebre_amarilla, ficha_epidemiologia_n6.getIgm_fiebre_amarilla());
			Utilidades.seleccionarRadio(rdbRt_pcr, ficha_epidemiologia_n6.getRt_pcr());
			Utilidades.seleccionarRadio(rdbAislamiento_viral, ficha_epidemiologia_n6.getAislamiento_viral());
			Utilidades.seleccionarRadio(rdbViscerotomia, ficha_epidemiologia_n6.getViscerotomia());
			Utilidades.seleccionarRadio(rdbTejido_fresco, ficha_epidemiologia_n6.getTejido_fresco());
			Utilidades.seleccionarRadio(rdbTejido_normal, ficha_epidemiologia_n6.getTejido_normal());
					
			Utilidades.seleccionarRadio(rdbDesplazamiento, ficha_epidemiologia_n6.getDesplazamiento());
			
			if (ficha_epidemiologia_n6.getDesplazamiento().equals("S")) {
				rowDesplazamiento.setVisible(true);
				lbFecha_desplazamiento.setVisible(true);
				lbMunicipio_desplazamiento.setVisible(true);
				dtbxFecha_desplazamiento.setVisible(true);
				tbxMunicipio_desplazamiento.setVisible(true);
				dtbxFecha_desplazamiento.setValue(ficha_epidemiologia_n6.getFecha_desplazamiento());
				tbxMunicipio_desplazamiento.setValue(ficha_epidemiologia_n6.getMunicipio_desplazamiento());
			
			} else {
				rowDesplazamiento.setVisible(false);
				lbFecha_desplazamiento.setVisible(false);
				lbMunicipio_desplazamiento.setVisible(false);
				dtbxFecha_desplazamiento.setVisible(false);
				tbxMunicipio_desplazamiento.setVisible(false);
			
			}
			
			Utilidades.seleccionarRadio(rdbCaso_fiebre, ficha_epidemiologia_n6.getCaso_fiebre());
			Utilidades.seleccionarRadio(rdbOcurrencia, ficha_epidemiologia_n6.getOcurrencia());
			tbxDireccion.setValue(ficha_epidemiologia_n6.getDireccion());
			Utilidades.seleccionarRadio(rdbPresencia_aedes, ficha_epidemiologia_n6.getPresencia_aedes());
			tbxCodigo_medico.setValue(ficha_epidemiologia_n6.getCodigo_medico());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n6 ficha_epidemiologia_n6 = (Ficha_epidemiologia_n6)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n6);
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
	
	public void mostrar_conRadio(ZKWindow form,Radiogroup radiogroup,AbstractComponent[] abstractComponents) {
		try {
			String valor = "S";
			FormularioUtil.mostrarComponentes_conRadiogroup(form, radiogroup, valor, abstractComponents);
			
		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}
	
	
	public void deshabilitar_conRadio(Radiogroup radiogroup,AbstractComponent[] abstractComponents) {
		try {
			String valor="S";
			FormularioUtil.deshabilitarComponentes_conRadiogroup(radiogroup, valor, abstractComponents);
			
		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}
	
	public void obtenerAdmision(Admision admision){
		Paciente paciente = admision.getPaciente();
		tbxIdentificacion.setValue(admision.getNro_identificacion());
		tbxNombrePaciente.setValue(paciente.getNombreCompleto());
		tbxTipo_identificacion.setValue(paciente.getTipo_identificacion());
		
		tbxAseguradora.setValue(admision.getCodigo_administradora());
		//log.info("PACIENTE"+paciente);
		//log.info("ADMINISTRADORA"+admision.getCodigo_empresa()+" "+admision.getCodigo_sucursal()+" "+paciente.getCodigo_administradora());
		
		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(admision.getCodigo_empresa());
		administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
		administradora.setCodigo(admision.getCodigo_administradora());
		administradora = getServiceLocator().getAdministradoraService().consultar(administradora);
		//log.info("administradora"+administradora);
		
		tbxNombre_aseguradora.setValue(administradora.getNombre());
	}

	@Override
	public Ficha_epidemiologia_n6 consultarDatos(Map<String, Object> map,
			Ficha_epidemiologia ficha_epidemiologia) throws Exception {
//				Ficha_epidemiologia ficha = (Ficha_epidemiologia)ficha_epidemiologia;
				
				//log.info("-----------------");
				
				//log.info("map"+map);
				//log.info("ficha"+ficha);
				
				Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) map.get("impresion_diagnostica");
				Cie_epidemiologia cie_epidemiologia = (Cie_epidemiologia) map.get("cie_epidemiologia");
				Admision admision = (Admision) map.get("admision");
				
				Map<String,Object> parameters = new HashMap<String,Object>();
				parameters.put("codigo_empresa", admision.getCodigo_empresa());
				parameters.put("codigo_sucursal", admision.getCodigo_sucursal());
				parameters.put("identificacion", admision.getNro_identificacion());
				
				if(impresion_diagnostica != null){
				parameters.put("codigo_historia", impresion_diagnostica.getCodigo_historia());
				}else{
					return null;
				}
				
				if(cie_epidemiologia != null){
					parameters.put("codigo_diagnostico", cie_epidemiologia.getCodigo_cie());			
				}else{
					return null;
				}
				
				getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
				
				List<Ficha_epidemiologia_n6> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n6.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n6 ficha_n6 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n6;
				}else{

					return null;
				}
	}
	

	public void imprimir() throws Exception {
		
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Ficha_epidemiologia");
		paramRequest.put("empresa", empresa.getCodigo_empresa());
		paramRequest.put("sucursal", sucursal.getCodigo_sucursal());
		paramRequest.put("codigo_ficha", tbxCodigo_ficha.getValue());
		paramRequest.put("ficha_epidemiologia", IFichas_epidemiologicas.FIEBRE_AMARILLA);

		//log.info("codigo_ficha"+tbxCodigo_ficha.getValue());
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
	
}