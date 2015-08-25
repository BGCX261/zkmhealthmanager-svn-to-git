/*
 * ficha_epidemiologia_n4Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n4;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.Date;
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
import org.zkoss.zul.Radio;
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

public class Ficha_epidemiologia_n4Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n4>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n4Action.class);
	
	
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
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View private Datebox dtbxFecha_creacion;
	@View private Textbox tbxCodigo_diagnostico;
	
	@View private Radiogroup rdbDesplazamiento;
	@View private Datebox dtbxFecha_desplazamiento;
	@View private Textbox tbxMunicipio_desplazamiento;
	@View private Textbox tbxCodigo;
	@View private Radiogroup rdbAntecedente_dengue;
	@View private Radiogroup rdbAntecedente_familiar;
	@View private Textbox tbxDireccion;
	@View private Checkbox chbFiebre;
	@View private Checkbox chbMialgias;
	@View private Checkbox chbGingivorragia;
	@View private Checkbox chbVomito;
	@View private Checkbox chbIctericia;
	@View private Checkbox chbDolor_retrorbicular;
	@View private Checkbox chbHemoptisis;
	@View private Checkbox chbArtralgias;
	@View private Checkbox chbHiperemia_conjuntival;
	@View private Checkbox chbErupcion;
	@View private Checkbox chbHematemesis;
	@View private Checkbox chbOliguria;
	@View private Checkbox chbPetequias;
	@View private Checkbox chbMetrorragia;
	@View private Checkbox chbChoque;
	@View private Checkbox chbDiarrea;
	@View private Checkbox chbEquimosis;
	@View private Checkbox chbMelenas;
	@View private Checkbox chbDerrame_pleural;
	@View private Checkbox chbEpistaxis;
	@View private Checkbox chbHematuria;
	@View private Checkbox chbAscitis;
	@View private Checkbox chbDolor_abdominal;
	@View private Checkbox chbCefalea;
	@View private Checkbox chbTomiquete_postiva;
	@View private Checkbox chbTomiquete_negativa;
	@View private Checkbox chbAlteracion_conciencia;
	@View private Checkbox chbConvulciones;
	@View private Checkbox chbHipotension;
	@View private Checkbox chbTaquicardia;
	@View private Checkbox chbHepatomeglia;
	@View private Checkbox chbEsplenomegalia;
	@View private Checkbox chbEdema_pulmonar;
	@View private Doublebox dbxRecuento_leucocitos;
	@View private Doublebox dbxHematocrito_inicial;
	@View private Doublebox dbxHematocrito_control;
	@View private Doublebox dbxRecuento_plaquetas_inicial;
	@View private Doublebox dbxRecuento_plaquetas_control;
	@View private Radiogroup rdbElisa_igm;
	@View private Radiogroup rdbRt_pcr;
	@View private Radiogroup rdbAislamiento_viral;
	@View private Doublebox dbxAlbumina;
	@View private Radiogroup rdbGases_arteriales;
	@View private Radiogroup rdbAlteracion_electronica;
	@View private Doublebox dbxAlt_tgp;
	@View private Doublebox dbxAst_tgo;
	@View private Radiogroup rdbNitrogeno_ureico;
	@View private Radiogroup rdbCreatinina;
	@View private Radiogroup rdbTiempo_protrombina;
	@View private Radiogroup rdbTiempo_protrombina_parcial;
	@View private Radiogroup rdbRx_torax;
	@View private Radiogroup rdbElectrocardiograma;
	@View private Radiogroup rdbEcografia_abdominal;
	@View private Radiogroup rdbEcocardiograma;
	@View private Radiogroup rdbTac_craneo;
	@View private Radiogroup rdbMuestra_tejidos;
	@View private Radiogroup rdbHigado;
	@View private Radiogroup rdbBazo;
	@View private Radiogroup rdbPulmon;
	@View private Radiogroup rdbCerebro;
	@View private Radiogroup rdbMiocardio;
	@View private Radiogroup rdbMedula_osea;
	@View private Radiogroup rdbRinon;
	@View private Listbox lbxClasificacion_final;
	@View private Listbox lbxNivel_atencion;
	@View private Listbox lbxAmbulatoria;
	@View private Textbox tbxCodigo_medico;
	private final String[] idsExcluyentes = {};
	
	@View private Toolbarbutton btGuardar;
	
	@View private Row rowDesplazamiento;
	@View private Label lbFecha_desplazamiento;
	@View private Label lbMunicipio_desplazamiento;
	
	@View private Row rowMuestras;

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
				Ficha_epidemiologia_n4 ficha = new Ficha_epidemiologia_n4();
				ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
				ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
				ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
				ficha = (Ficha_epidemiologia_n4) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
				
				//log.info("consultar ficha 4-------> "+ficha);
				
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
		Utilidades.listarElementoListbox(lbxClasificacion_final, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxNivel_atencion, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxAmbulatoria, true, getServiceLocator());

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
			
		tbxIdentificacion.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo.setStyle("text-transform:uppercase;background-color:white");
		tbxDireccion.setStyle("text-transform:uppercase;background-color:white");
		dbxRecuento_leucocitos.setStyle("text-transform:uppercase;background-color:white");
		dbxHematocrito_inicial.setStyle("text-transform:uppercase;background-color:white");
		dbxHematocrito_control.setStyle("text-transform:uppercase;background-color:white");
		dbxRecuento_plaquetas_inicial.setStyle("text-transform:uppercase;background-color:white");
		dbxRecuento_plaquetas_control.setStyle("text-transform:uppercase;background-color:white");
		lbxClasificacion_final.setStyle("text-transform:uppercase;background-color:white");
		lbxNivel_atencion.setStyle("text-transform:uppercase;background-color:white");
		lbxAmbulatoria.setStyle("text-transform:uppercase;background-color:white");
		
		
		boolean valida = true;
		
		if(tbxIdentificacion.getText().trim().equals("")){
			tbxIdentificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(tbxCodigo.getText().trim().equals("")){
			tbxCodigo.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDireccion.getText().trim().equals("")){
			tbxDireccion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dbxRecuento_leucocitos.getText().trim().equals("")){
			dbxRecuento_leucocitos.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dbxHematocrito_inicial.getText().trim().equals("")){
			dbxHematocrito_inicial.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dbxHematocrito_control.getText().trim().equals("")){
			dbxHematocrito_control.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dbxRecuento_plaquetas_inicial.getText().trim().equals("")){
			dbxRecuento_plaquetas_inicial.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dbxRecuento_plaquetas_control.getText().trim().equals("")){
			dbxRecuento_plaquetas_control.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if (lbxClasificacion_final.getSelectedIndex() == 0) {
			lbxClasificacion_final.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if (lbxNivel_atencion.getSelectedIndex() == 0) {
			lbxNivel_atencion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if (lbxAmbulatoria.getSelectedIndex() == 0) {
			lbxAmbulatoria.setStyle("text-transform:uppercase;background-color:#F6BBBE");
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
			
			List<Ficha_epidemiologia_n4> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n4.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n4 ficha_epidemiologia_n4 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n4, this));
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
		
		final Ficha_epidemiologia_n4 ficha_epidemiologia_n4 = (Ficha_epidemiologia_n4)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n4.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n4.getIdentificacion()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n4.getFecha_creacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n4);
			}
		});
		hbox.appendChild(img);
		
		
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n4 obtenerFichaEpidemiologia() {
		
				Ficha_epidemiologia_n4 ficha_epidemiologia_n4 = new Ficha_epidemiologia_n4();
				ficha_epidemiologia_n4.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n4.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n4.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n4.setIdentificacion(tbxIdentificacion.getValue());
				ficha_epidemiologia_n4.setFecha_creacion(new Timestamp(dtbxFecha_creacion.getValue().getTime()));
				ficha_epidemiologia_n4.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n4.setDesplazamiento(rdbDesplazamiento.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_desplazamiento.getValue() != null) {
					ficha_epidemiologia_n4.setFecha_desplazamiento(new Timestamp(dtbxFecha_desplazamiento.getValue().getTime()));
					
				} else {
					ficha_epidemiologia_n4.setFecha_desplazamiento(null);
				}
				
				ficha_epidemiologia_n4.setMunicipio_desplazamiento(tbxMunicipio_desplazamiento.getValue());
				ficha_epidemiologia_n4.setCodigo(tbxCodigo.getValue());
				ficha_epidemiologia_n4.setAntecedente_dengue(rdbAntecedente_dengue.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setAntecedente_familiar(rdbAntecedente_familiar.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setDireccion(tbxDireccion.getValue());
				ficha_epidemiologia_n4.setFiebre(chbFiebre.isChecked());
				ficha_epidemiologia_n4.setMialgias(chbMialgias.isChecked());
				ficha_epidemiologia_n4.setGingivorragia(chbGingivorragia.isChecked());
				ficha_epidemiologia_n4.setVomito(chbVomito.isChecked());
				ficha_epidemiologia_n4.setIctericia(chbIctericia.isChecked());
				ficha_epidemiologia_n4.setDolor_retrorbicular(chbDolor_retrorbicular.isChecked());
				ficha_epidemiologia_n4.setHemoptisis(chbHemoptisis.isChecked());
				ficha_epidemiologia_n4.setArtralgias(chbArtralgias.isChecked());
				ficha_epidemiologia_n4.setHiperemia_conjuntival(chbHiperemia_conjuntival.isChecked());
				ficha_epidemiologia_n4.setErupcion(chbErupcion.isChecked());
				ficha_epidemiologia_n4.setHematemesis(chbHematemesis.isChecked());
				ficha_epidemiologia_n4.setOliguria(chbOliguria.isChecked());;
				ficha_epidemiologia_n4.setPetequias(chbPetequias.isChecked());
				ficha_epidemiologia_n4.setMetrorragia(chbMetrorragia.isChecked());
				ficha_epidemiologia_n4.setChoque(chbChoque.isChecked());
				ficha_epidemiologia_n4.setDiarrea(chbDiarrea.isChecked());
				ficha_epidemiologia_n4.setEquimosis(chbEquimosis.isChecked());
				ficha_epidemiologia_n4.setMelenas(chbMelenas.isChecked());
				ficha_epidemiologia_n4.setDerrame_pleural(chbDerrame_pleural.isChecked());
				ficha_epidemiologia_n4.setEpistaxis(chbEpistaxis.isChecked());
				ficha_epidemiologia_n4.setHematuria(chbHematuria.isChecked());
				ficha_epidemiologia_n4.setAscitis(chbAscitis.isChecked());
				ficha_epidemiologia_n4.setDolor_abdominal(chbDolor_abdominal.isChecked());
				ficha_epidemiologia_n4.setCefalea(chbCefalea.isChecked());
				ficha_epidemiologia_n4.setTomiquete_postiva(chbTomiquete_postiva.isChecked());
				ficha_epidemiologia_n4.setTomiquete_negativa(chbTomiquete_negativa.isChecked());
				ficha_epidemiologia_n4.setAlteracion_conciencia(chbAlteracion_conciencia.isChecked());
				ficha_epidemiologia_n4.setConvulciones(chbConvulciones.isChecked());
				ficha_epidemiologia_n4.setHipotension(chbHipotension.isChecked());
				ficha_epidemiologia_n4.setTaquicardia(chbTaquicardia.isChecked());
				ficha_epidemiologia_n4.setHepatomeglia(chbHepatomeglia.isChecked());
				ficha_epidemiologia_n4.setEsplenomegalia(chbEsplenomegalia.isChecked());
				ficha_epidemiologia_n4.setEdema_pulmonar(chbEdema_pulmonar.isChecked());
				ficha_epidemiologia_n4.setRecuento_leucocitos(dbxRecuento_leucocitos.getValue() != null ? dbxRecuento_leucocitos.getValue() + "" : "");
				ficha_epidemiologia_n4.setHematocrito_inicial(dbxHematocrito_inicial.getValue() != null ? dbxHematocrito_inicial.getValue() + "" : "");
				ficha_epidemiologia_n4.setHematocrito_control(dbxHematocrito_control.getValue() != null ? dbxHematocrito_control.getValue() + "" : "");
				ficha_epidemiologia_n4.setRecuento_plaquetas_inicial(dbxRecuento_plaquetas_inicial.getValue() != null ? dbxRecuento_plaquetas_inicial.getValue() + "" : "");
				ficha_epidemiologia_n4.setRecuento_plaquetas_control(dbxRecuento_plaquetas_control.getValue() != null ? dbxRecuento_plaquetas_control.getValue() + "" : "");
				ficha_epidemiologia_n4.setElisa_igm(rdbElisa_igm.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setRt_pcr(rdbRt_pcr.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setAislamiento_viral(rdbAislamiento_viral.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setAlbumina(dbxAlbumina.getValue() != null ? dbxAlbumina.getValue() + "" : "");
				ficha_epidemiologia_n4.setGases_arteriales(rdbGases_arteriales.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setAlteracion_electronica(rdbAlteracion_electronica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setAlt_tgp(dbxAlt_tgp.getValue() != null ? dbxAlt_tgp.getValue() + "" : "");
				ficha_epidemiologia_n4.setAst_tgo(dbxAst_tgo.getValue() != null ? dbxAst_tgo.getValue() + "" : "");
				ficha_epidemiologia_n4.setNitrogeno_ureico(rdbNitrogeno_ureico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setCreatinina(rdbCreatinina.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setTiempo_protrombina(rdbTiempo_protrombina.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setTiempo_protrombina_parcial(rdbTiempo_protrombina_parcial.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setRx_torax(rdbRx_torax.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setElectrocardiograma(rdbElectrocardiograma.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setEcografia_abdominal(rdbEcografia_abdominal.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setEcocardiograma(rdbEcocardiograma.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setTac_craneo(rdbTac_craneo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setMuestra_tejidos(rdbMuestra_tejidos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setHigado(rdbHigado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setBazo(rdbBazo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setPulmon(rdbPulmon.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setCerebro(rdbCerebro.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setMiocardio(rdbMiocardio.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setMedula_osea(rdbMedula_osea.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setRinon(rdbRinon.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setClasificacion_final(lbxClasificacion_final.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setNivel_atencion(lbxNivel_atencion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setAmbulatoria(lbxAmbulatoria.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n4.setCodigo_medico(tbxCodigo_medico.getValue());
				ficha_epidemiologia_n4.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n4.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n4.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n4.setDelete_date(null);
				ficha_epidemiologia_n4.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n4.setDelete_user(null);
				
				return ficha_epidemiologia_n4;
				 
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n4 obj)throws Exception{
		Ficha_epidemiologia_n4 ficha_epidemiologia_n4 = (Ficha_epidemiologia_n4)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n4.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n4.getIdentificacion());

			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			dtbxFecha_creacion.setValue(ficha_epidemiologia_n4.getFecha_creacion());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n4.getCodigo_diagnostico());
			Utilidades.seleccionarRadio(rdbDesplazamiento, ficha_epidemiologia_n4.getDesplazamiento());
			
			if (ficha_epidemiologia_n4.getDesplazamiento().equals("S")) {
				rowDesplazamiento.setVisible(true);
				lbFecha_desplazamiento.setVisible(true);
				lbMunicipio_desplazamiento.setVisible(true);
				dtbxFecha_desplazamiento.setVisible(true);
				tbxMunicipio_desplazamiento.setVisible(true);
				dtbxFecha_desplazamiento.setValue(ficha_epidemiologia_n4.getFecha_desplazamiento());
				tbxMunicipio_desplazamiento.setValue(ficha_epidemiologia_n4.getMunicipio_desplazamiento());
			
			} else {
				rowDesplazamiento.setVisible(false);
				lbFecha_desplazamiento.setVisible(false);
				lbMunicipio_desplazamiento.setVisible(false);
				dtbxFecha_desplazamiento.setVisible(false);
				tbxMunicipio_desplazamiento.setVisible(false);
			
			}
			
			tbxCodigo.setValue(ficha_epidemiologia_n4.getCodigo());
			Utilidades.seleccionarRadio(rdbAntecedente_dengue, ficha_epidemiologia_n4.getAntecedente_dengue());
			Utilidades.seleccionarRadio(rdbAntecedente_familiar, ficha_epidemiologia_n4.getAntecedente_familiar());
			tbxDireccion.setValue(ficha_epidemiologia_n4.getDireccion());
			chbFiebre.setChecked(ficha_epidemiologia_n4.getFiebre());
			chbMialgias.setChecked(ficha_epidemiologia_n4.getMialgias());
			chbGingivorragia.setChecked(ficha_epidemiologia_n4.getGingivorragia());
			chbVomito.setChecked(ficha_epidemiologia_n4.getVomito());
			chbIctericia.setChecked(ficha_epidemiologia_n4.getIctericia());
			chbDolor_retrorbicular.setChecked(ficha_epidemiologia_n4.getDolor_retrorbicular());
			chbHemoptisis.setChecked(ficha_epidemiologia_n4.getHemoptisis());
			chbArtralgias.setChecked(ficha_epidemiologia_n4.getArtralgias());
			chbHiperemia_conjuntival.setChecked(ficha_epidemiologia_n4.getHiperemia_conjuntival());
			chbErupcion.setChecked(ficha_epidemiologia_n4.getErupcion());
			chbHematemesis.setChecked(ficha_epidemiologia_n4.getHematemesis());
			chbOliguria.setChecked(ficha_epidemiologia_n4.getOliguria());
			chbPetequias.setChecked(ficha_epidemiologia_n4.getPetequias());
			chbMetrorragia.setChecked(ficha_epidemiologia_n4.getMetrorragia());
			chbChoque.setChecked(ficha_epidemiologia_n4.getChoque());
			chbDiarrea.setChecked(ficha_epidemiologia_n4.getDiarrea());
			chbEquimosis.setChecked(ficha_epidemiologia_n4.getEquimosis());
			chbMelenas.setChecked(ficha_epidemiologia_n4.getMelenas());
			chbDerrame_pleural.setChecked(ficha_epidemiologia_n4.getDerrame_pleural());
			chbEpistaxis.setChecked(ficha_epidemiologia_n4.getEpistaxis());
			chbHematuria.setChecked(ficha_epidemiologia_n4.getHematuria());
			chbAscitis.setChecked(ficha_epidemiologia_n4.getAscitis());
			chbDolor_abdominal.setChecked(ficha_epidemiologia_n4.getDolor_abdominal());
			chbCefalea.setChecked(ficha_epidemiologia_n4.getCefalea());
			chbTomiquete_postiva.setChecked(ficha_epidemiologia_n4.getTomiquete_postiva());
			chbTomiquete_negativa.setChecked(ficha_epidemiologia_n4.getTomiquete_negativa());
			chbAlteracion_conciencia.setChecked(ficha_epidemiologia_n4.getAlteracion_conciencia());
			chbConvulciones.setChecked(ficha_epidemiologia_n4.getConvulciones());
			chbHipotension.setChecked(ficha_epidemiologia_n4.getHipotension());
			chbTaquicardia.setChecked(ficha_epidemiologia_n4.getTaquicardia());
			chbHepatomeglia.setChecked(ficha_epidemiologia_n4.getHepatomeglia());
			chbEsplenomegalia.setChecked(ficha_epidemiologia_n4.getEsplenomegalia());
			chbEdema_pulmonar.setChecked(ficha_epidemiologia_n4.getEdema_pulmonar());
			dbxRecuento_leucocitos.setValue((ficha_epidemiologia_n4.getRecuento_leucocitos() != null && !ficha_epidemiologia_n4.getRecuento_leucocitos().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n4.getRecuento_leucocitos()) : null);
			dbxHematocrito_inicial.setValue((ficha_epidemiologia_n4.getHematocrito_inicial() != null && !ficha_epidemiologia_n4.getHematocrito_inicial().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n4.getHematocrito_inicial()) : null);
			dbxHematocrito_control.setValue((ficha_epidemiologia_n4.getHematocrito_control() != null && !ficha_epidemiologia_n4.getHematocrito_control().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n4.getHematocrito_control()) : null);
			dbxRecuento_plaquetas_inicial.setValue((ficha_epidemiologia_n4.getRecuento_plaquetas_inicial() != null && !ficha_epidemiologia_n4.getRecuento_plaquetas_inicial().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n4.getRecuento_plaquetas_inicial()) : null);
			dbxRecuento_plaquetas_control.setValue((ficha_epidemiologia_n4.getRecuento_plaquetas_control() != null && !ficha_epidemiologia_n4.getRecuento_plaquetas_control().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n4.getRecuento_plaquetas_control()) : null);
			Utilidades.seleccionarRadio(rdbElisa_igm, ficha_epidemiologia_n4.getElisa_igm());
			Utilidades.seleccionarRadio(rdbRt_pcr, ficha_epidemiologia_n4.getRt_pcr());
			Utilidades.seleccionarRadio(rdbAislamiento_viral, ficha_epidemiologia_n4.getAislamiento_viral());
			dbxAlbumina.setValue((ficha_epidemiologia_n4.getAlbumina() != null && !ficha_epidemiologia_n4.getAlbumina().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n4.getAlbumina()) : null);
			Utilidades.seleccionarRadio(rdbGases_arteriales, ficha_epidemiologia_n4.getGases_arteriales());
			Utilidades.seleccionarRadio(rdbAlteracion_electronica, ficha_epidemiologia_n4.getAlteracion_electronica());
			dbxAlt_tgp.setValue((ficha_epidemiologia_n4.getAlt_tgp() != null && !ficha_epidemiologia_n4.getAlt_tgp().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n4.getAlt_tgp()) : null);
			dbxAst_tgo.setValue((ficha_epidemiologia_n4.getAst_tgo() != null && !ficha_epidemiologia_n4.getAst_tgo().isEmpty()) ? Double.valueOf(ficha_epidemiologia_n4.getAst_tgo()) : null);
			Utilidades.seleccionarRadio(rdbNitrogeno_ureico, ficha_epidemiologia_n4.getNitrogeno_ureico());
			Utilidades.seleccionarRadio(rdbCreatinina, ficha_epidemiologia_n4.getCreatinina());
			Utilidades.seleccionarRadio(rdbTiempo_protrombina, ficha_epidemiologia_n4.getTiempo_protrombina());
			Utilidades.seleccionarRadio(rdbTiempo_protrombina_parcial, ficha_epidemiologia_n4.getTiempo_protrombina_parcial());
			Utilidades.seleccionarRadio(rdbRx_torax, ficha_epidemiologia_n4.getRx_torax());
			Utilidades.seleccionarRadio(rdbElectrocardiograma, ficha_epidemiologia_n4.getElectrocardiograma());
			Utilidades.seleccionarRadio(rdbEcografia_abdominal, ficha_epidemiologia_n4.getEcografia_abdominal());
			Utilidades.seleccionarRadio(rdbEcocardiograma, ficha_epidemiologia_n4.getEcocardiograma());
			Utilidades.seleccionarRadio(rdbTac_craneo, ficha_epidemiologia_n4.getTac_craneo());
			
			Utilidades.seleccionarRadio(rdbMuestra_tejidos, ficha_epidemiologia_n4.getMuestra_tejidos());
			
			if (ficha_epidemiologia_n4.getMuestra_tejidos().equals("S")) {
				rowMuestras.setVisible(true);
				Utilidades.seleccionarRadio(rdbHigado, ficha_epidemiologia_n4.getHigado());
				Utilidades.seleccionarRadio(rdbBazo, ficha_epidemiologia_n4.getBazo());
				Utilidades.seleccionarRadio(rdbPulmon, ficha_epidemiologia_n4.getPulmon());
				Utilidades.seleccionarRadio(rdbCerebro, ficha_epidemiologia_n4.getCerebro());
				Utilidades.seleccionarRadio(rdbMiocardio, ficha_epidemiologia_n4.getMiocardio());
				Utilidades.seleccionarRadio(rdbMedula_osea, ficha_epidemiologia_n4.getMedula_osea());
				Utilidades.seleccionarRadio(rdbRinon, ficha_epidemiologia_n4.getRinon());
				
			} else {
				rowMuestras.setVisible(false);
				
			}
			
			for (int i = 0; i < lbxClasificacion_final.getItemCount(); i++) {
				Listitem listitem = lbxClasificacion_final.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n4.getClasificacion_final())) {
					listitem.setSelected(true);
					i = lbxClasificacion_final.getItemCount();
				}
			}
			for (int i = 0; i < lbxNivel_atencion.getItemCount(); i++) {
				Listitem listitem = lbxNivel_atencion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n4.getNivel_atencion())) {
					listitem.setSelected(true);
					i = lbxNivel_atencion.getItemCount();
				}
			}
			
			for (int i = 0; i < lbxAmbulatoria.getItemCount(); i++) {
				Listitem listitem = lbxAmbulatoria.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n4.getAmbulatoria())) {
					listitem.setSelected(true);
					i = lbxAmbulatoria.getItemCount();
				}
			}
			tbxCodigo_medico.setValue(ficha_epidemiologia_n4.getCodigo_medico());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n4 ficha_epidemiologia_n4 = (Ficha_epidemiologia_n4)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n4);
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
	
	public void seleccion_radio(Radiogroup radiogroup,AbstractComponent[] abstractComponents) {
		try {
			//System.Out.Println("" + radiogroup.getSelectedItem().getValue());

			for (AbstractComponent abstractComponent : abstractComponents) {

				if (radiogroup.getSelectedItem().getValue().equals("S")) {
					abstractComponent.setVisible(true);
					
					if (abstractComponent instanceof Radiogroup) {
						((Radio) ((Radiogroup) abstractComponent).getLastChild())
								.setChecked(true);
					}
					
					if (abstractComponent instanceof Datebox) {
						((Datebox) abstractComponent).setValue(new Date());

					}

					if (abstractComponent instanceof Textbox) {
						((Textbox) abstractComponent).setValue("");

					}
					
				} else {
					
					if (abstractComponent instanceof Radiogroup) {
						((Radio) ((Radiogroup) abstractComponent).getLastChild())
								.setChecked(true);
					}
					
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
	public Ficha_epidemiologia_n4 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n4> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n4.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n4 ficha_n4 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n4;
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
		paramRequest.put("ficha_epidemiologia", IFichas_epidemiologicas.DENGUE);

		//log.info("codigo_ficha"+tbxCodigo_ficha.getValue());
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
	
	
}