/*
 * ficha_epidemiologia_n33Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n33;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n33Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n33>{

	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n33Action.class);
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_ficha;
	@View private Textbox tbxCodigo_diagnostico;
	@View private Datebox dtbxFecha_inicial;
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad;
	@View private Textbox tbxIdentificacion;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View private Checkbox chbViolecia_fisica;
	@View private Checkbox chbPrivacion_negligencia;
	@View private Checkbox chbAcoso_sexual;
	@View private Checkbox chbEplotacion_sexual;
	@View private Checkbox chbPornografia_nna;
	@View private Checkbox chbViolencia_psicologia;
	@View private Checkbox chbAbuso_sexual;
	@View private Checkbox chbAsalto_sexual;
	@View private Checkbox chbTurismo_sexual;
	@View private Checkbox chbTrata_de_persona;
	@View private Listbox lbxEscolaridad_de_victima;
	@View private Radiogroup rdbFactor_de_vulnerabilidad;
	@View private Textbox tbxCual_factor;
	@View private Radiogroup rdbAntecedentes_hecho;
	@View private Radiogroup rdbPresencia_de_alcohol;
	@View private Textbox tbxEdad_aparente;
	@View private Radiogroup rdbSexo_agresor;
	@View private Bandbox tbxOcupacion_del_agresor;
	@View private Listbox lbxEscolaridad_del_agresor;
	@View private Radiogroup rdbFamiliaridad_o_parent;
	@View private Textbox tbxCual_otro_fami;
	@View private Radiogroup rdbConvive_con_el_agresor;
	@View private Radiogroup rdbNo_familiar;
	@View private Textbox tbxConvive_con_el_agresor2;
	@View private Radiogroup rdbGrupos;
	@View private Textbox tbxCual_otro_grupo;
	@View private Radiogroup rdbPresencia_de_alcohol2;
	@View private Checkbox chbAhorcamiento;
	@View private Checkbox chbCortante;
	@View private Checkbox chbInmersion;
	@View private Checkbox chbMordedura;
	@View private Checkbox chbCadidas;
	@View private Checkbox chbElectrocucion;
	@View private Checkbox chbIntoxicacion;
	@View private Checkbox chbProyectil_arma;
	@View private Checkbox chbContundente;
	@View private Checkbox chbExplosivo;
	@View private Checkbox chbMinas_antipersona;
	@View private Checkbox chbQuemadura;
	@View private Checkbox chbOtra;
	@View private Textbox tbxNombre_sustancia;
	@View private Textbox tbxCual_otra_arma;
	@View private Radiogroup rdbOtros_mecanismos;
	@View private Textbox tbxCual_otro_meca;
	@View private Datebox dtbxFecha_del_hecho;
	@View private Timebox tbHora_del_hecho;
	@View private Radiogroup rdbEscenario;
//	@View private Textbox tbxOtro;
	@View private Textbox tbxCual_otro_esc;
	@View private Radiogroup rdbZona_de_conflicto;
	@View private Radiogroup rdbSector_que_notifica;
	@View private Textbox tbxCual_sector;
	@View private Checkbox chbAtencion_psicosocial;
	@View private Checkbox chbProfilaxis;
	@View private Checkbox chbAnticoncepcion_de_emerg;
	@View private Checkbox chbOrientacion_ive;
	@View private Checkbox chbAtencion_en_salud;
	@View private Checkbox chbInforme_autoridad;
	@View private Checkbox chbOtro2;
	@View private Textbox tbxCual_otra_accion;
	@View private Textbox tbxCual_autoridad;
	@View private Radiogroup rdbRecomendacion;
	@View private Radiogroup rdbAmerita_trabajo;
	//@View private Textbox tbxDiligenciado_por;
	//@View private Textbox tbxTelefono_de_contacto;
	@View private Toolbarbutton btGuardar;
	private final String[] idsExcluyentes = {};
	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;

	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n33 ficha = new Ficha_epidemiologia_n33();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n33) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 33-------> "+ficha);
			
			mostrarDatos(ficha);
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
		Utilidades.listarNivel_educativo(lbxEscolaridad_del_agresor,true,getServiceLocator());
		Utilidades.listarNivel_educativo(lbxEscolaridad_de_victima,true,getServiceLocator());
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
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
	public boolean validarFichaEpidemiologia(){
		
		tbxIdentificacion.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(tbxIdentificacion.getText().trim().equals("")){
			tbxIdentificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
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
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}
			
			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
			
			List<Ficha_epidemiologia_n33> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
					Ficha_epidemiologia_n33.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n33 ficha_epidemiologia_n33 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n33, this));
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
		
		final Ficha_epidemiologia_n33 ficha_epidemiologia_n33 = (Ficha_epidemiologia_n33)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n33.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n33.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n33.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n33);
			}
		});
		hbox.appendChild(img);

		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n33 obtenerFichaEpidemiologia(){
				
				Ficha_epidemiologia_n33 ficha_epidemiologia_n33 = new Ficha_epidemiologia_n33();
				ficha_epidemiologia_n33.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n33.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n33.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n33.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n33.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n33.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n33.setViolecia_fisica(chbViolecia_fisica.isChecked());
				ficha_epidemiologia_n33.setPrivacion_negligencia(chbPrivacion_negligencia.isChecked());
				ficha_epidemiologia_n33.setAcoso_sexual(chbAcoso_sexual.isChecked());
				ficha_epidemiologia_n33.setEplotacion_sexual(chbEplotacion_sexual.isChecked());
				ficha_epidemiologia_n33.setPornografia_nna(chbPornografia_nna.isChecked());
				ficha_epidemiologia_n33.setViolencia_psicologia(chbViolencia_psicologia.isChecked());
				ficha_epidemiologia_n33.setAbuso_sexual(chbAbuso_sexual.isChecked());
				ficha_epidemiologia_n33.setAsalto_sexual(chbAsalto_sexual.isChecked());
				ficha_epidemiologia_n33.setTurismo_sexual(chbTurismo_sexual.isChecked());
				ficha_epidemiologia_n33.setTrata_de_persona(chbTrata_de_persona.isChecked());
				ficha_epidemiologia_n33.setEscolaridad_de_victima(lbxEscolaridad_de_victima.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setFactor_de_vulnerabilidad(rdbFactor_de_vulnerabilidad.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setCual_factor(tbxCual_factor.getValue());
				ficha_epidemiologia_n33.setAntecedentes_hecho(rdbAntecedentes_hecho.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setPresencia_de_alcohol(rdbPresencia_de_alcohol.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setEdad_aparente(tbxEdad_aparente.getValue());
				ficha_epidemiologia_n33.setSexo_agresor(rdbSexo_agresor.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setOcupacion_del_agresor(tbxOcupacion_del_agresor.getValue());
				ficha_epidemiologia_n33.setEscolaridad_del_agresor(lbxEscolaridad_del_agresor.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setFamiliaridad_o_parent(rdbFamiliaridad_o_parent.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setCual_otro_fami(tbxCual_otro_fami.getValue());
				ficha_epidemiologia_n33.setConvive_con_el_agresor(rdbConvive_con_el_agresor.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setNo_familiar(rdbNo_familiar.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setConvive_con_el_agresor2(tbxConvive_con_el_agresor2.getValue());
				ficha_epidemiologia_n33.setGrupos(rdbGrupos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setCual_otro_grupo(tbxCual_otro_grupo.getValue());
				ficha_epidemiologia_n33.setPresencia_de_alcohol2(rdbPresencia_de_alcohol2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setAhorcamiento(chbAhorcamiento.isChecked());
				ficha_epidemiologia_n33.setCortante(chbCortante.isChecked());
				ficha_epidemiologia_n33.setInmersion(chbInmersion.isChecked());
				ficha_epidemiologia_n33.setMordedura(chbMordedura.isChecked());
				ficha_epidemiologia_n33.setCadidas(chbCadidas.isChecked());
				ficha_epidemiologia_n33.setElectrocucion(chbElectrocucion.isChecked());
				ficha_epidemiologia_n33.setIntoxicacion(chbIntoxicacion.isChecked());
				ficha_epidemiologia_n33.setProyectil_arma(chbProyectil_arma.isChecked());
				ficha_epidemiologia_n33.setContundente(chbContundente.isChecked());
				ficha_epidemiologia_n33.setExplosivo(chbExplosivo.isChecked());
				ficha_epidemiologia_n33.setMinas_antipersona(chbMinas_antipersona.isChecked());
				ficha_epidemiologia_n33.setQuemadura(chbQuemadura.isChecked());
				ficha_epidemiologia_n33.setOtra(chbOtra.isChecked());
				ficha_epidemiologia_n33.setNombre_sustancia(tbxNombre_sustancia.getValue());
				ficha_epidemiologia_n33.setCual_otra_arma(tbxCual_otra_arma.getValue());
				ficha_epidemiologia_n33.setOtros_mecanismos(rdbOtros_mecanismos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setCual_otro_meca(tbxCual_otro_meca.getValue());
				ficha_epidemiologia_n33.setFecha_del_hecho(new Timestamp(dtbxFecha_del_hecho.getValue().getTime()));
				ficha_epidemiologia_n33.setHora_del_hecho(new Timestamp(tbHora_del_hecho.getValue().getTime()));
				ficha_epidemiologia_n33.setEscenario(rdbEscenario.getSelectedItem().getValue().toString());
//				ficha_epidemiologia_n33.setOtro(tbxOtro.getValue());
				ficha_epidemiologia_n33.setCual_otro_esc(tbxCual_otro_esc.getValue());
				ficha_epidemiologia_n33.setZona_de_conflicto(rdbZona_de_conflicto.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setSector_que_notifica(rdbSector_que_notifica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setCual_sector(tbxCual_sector.getValue());
				ficha_epidemiologia_n33.setAtencion_psicosocial(chbAtencion_psicosocial.isChecked());
				ficha_epidemiologia_n33.setProfilaxis(chbProfilaxis.isChecked());
				ficha_epidemiologia_n33.setAnticoncepcion_de_emerg(chbAnticoncepcion_de_emerg.isChecked());
				ficha_epidemiologia_n33.setOrientacion_ive(chbOrientacion_ive.isChecked());
				ficha_epidemiologia_n33.setAtencion_en_salud(chbAtencion_en_salud.isChecked());
				ficha_epidemiologia_n33.setInforme_autoridad(chbInforme_autoridad.isChecked());
				ficha_epidemiologia_n33.setOtro2(chbOtro2.isChecked());
				ficha_epidemiologia_n33.setCual_otra_accion(tbxCual_otra_accion.getValue());
				ficha_epidemiologia_n33.setCual_autoridad(tbxCual_autoridad.getValue());
				ficha_epidemiologia_n33.setRecomendacion(rdbRecomendacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setAmerita_trabajo(rdbAmerita_trabajo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n33.setDiligenciado_por("");
				ficha_epidemiologia_n33.setTelefono_de_contacto("");
				ficha_epidemiologia_n33.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n33.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n33.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n33.setDelete_date(null);
				ficha_epidemiologia_n33.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n33.setDelete_user(null);
				
				return ficha_epidemiologia_n33;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n33 obj)throws Exception{
		Ficha_epidemiologia_n33 ficha_epidemiologia_n33 = (Ficha_epidemiologia_n33)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n33.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n33.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n33.getFecha_inicial());
			obtenerAdmision(admision);
			
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			tbxIdentificacion.setValue(ficha_epidemiologia_n33.getIdentificacion());
			chbViolecia_fisica.setChecked(ficha_epidemiologia_n33.getViolecia_fisica());
			chbPrivacion_negligencia.setChecked(ficha_epidemiologia_n33.getPrivacion_negligencia());
			chbAcoso_sexual.setChecked(ficha_epidemiologia_n33.getAcoso_sexual());
			chbEplotacion_sexual.setChecked(ficha_epidemiologia_n33.getEplotacion_sexual());
			chbPornografia_nna.setChecked(ficha_epidemiologia_n33.getPornografia_nna());
			chbViolencia_psicologia.setChecked(ficha_epidemiologia_n33.getViolencia_psicologia());
			chbAbuso_sexual.setChecked(ficha_epidemiologia_n33.getAbuso_sexual());
			chbAsalto_sexual.setChecked(ficha_epidemiologia_n33.getAsalto_sexual());
			chbTurismo_sexual.setChecked(ficha_epidemiologia_n33.getTurismo_sexual());
			chbTrata_de_persona.setChecked(ficha_epidemiologia_n33.getTrata_de_persona());
			
			for(int i=0;i<lbxEscolaridad_de_victima.getItemCount();i++){
				Listitem listitem = lbxEscolaridad_de_victima.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(ficha_epidemiologia_n33.getEscolaridad_de_victima())){
					listitem.setSelected(true);
					i = lbxEscolaridad_de_victima.getItemCount();
				}
			}
			
			Utilidades.seleccionarRadio(rdbFactor_de_vulnerabilidad, ficha_epidemiologia_n33.getFactor_de_vulnerabilidad());
			tbxCual_factor.setValue(ficha_epidemiologia_n33.getCual_factor());
			Utilidades.seleccionarRadio(rdbAntecedentes_hecho, ficha_epidemiologia_n33.getAntecedentes_hecho());
			Utilidades.seleccionarRadio(rdbPresencia_de_alcohol, ficha_epidemiologia_n33.getPresencia_de_alcohol());
			tbxEdad_aparente.setValue(ficha_epidemiologia_n33.getEdad_aparente());
			Utilidades.seleccionarRadio(rdbSexo_agresor, ficha_epidemiologia_n33.getSexo_agresor());
			tbxOcupacion_del_agresor.setValue(ficha_epidemiologia_n33.getOcupacion_del_agresor());

			for(int i=0;i<lbxEscolaridad_del_agresor.getItemCount();i++){
				Listitem listitem = lbxEscolaridad_del_agresor.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(ficha_epidemiologia_n33.getEscolaridad_del_agresor())){
					listitem.setSelected(true);
					i = lbxEscolaridad_del_agresor.getItemCount();
				}
			}
			Utilidades.seleccionarRadio(rdbFamiliaridad_o_parent, ficha_epidemiologia_n33.getFamiliaridad_o_parent());
			tbxCual_otro_fami.setValue(ficha_epidemiologia_n33.getCual_otro_fami());
			Utilidades.seleccionarRadio(rdbConvive_con_el_agresor, ficha_epidemiologia_n33.getConvive_con_el_agresor());
			Utilidades.seleccionarRadio(rdbNo_familiar, ficha_epidemiologia_n33.getNo_familiar());
			tbxConvive_con_el_agresor2.setValue(ficha_epidemiologia_n33.getConvive_con_el_agresor2());
			Utilidades.seleccionarRadio(rdbGrupos, ficha_epidemiologia_n33.getGrupos());
			tbxCual_otro_grupo.setValue(ficha_epidemiologia_n33.getCual_otro_grupo());
			Utilidades.seleccionarRadio(rdbPresencia_de_alcohol2, ficha_epidemiologia_n33.getPresencia_de_alcohol2());
			chbAhorcamiento.setChecked(ficha_epidemiologia_n33.getAhorcamiento());
			chbCortante.setChecked(ficha_epidemiologia_n33.getCortante());
			chbInmersion.setChecked(ficha_epidemiologia_n33.getInmersion());
			chbMordedura.setChecked(ficha_epidemiologia_n33.getMordedura());
			chbCadidas.setChecked(ficha_epidemiologia_n33.getCadidas());
			chbElectrocucion.setChecked(ficha_epidemiologia_n33.getElectrocucion());
			chbIntoxicacion.setChecked(ficha_epidemiologia_n33.getIntoxicacion());
			chbProyectil_arma.setChecked(ficha_epidemiologia_n33.getProyectil_arma());
			chbContundente.setChecked(ficha_epidemiologia_n33.getContundente());
			chbExplosivo.setChecked(ficha_epidemiologia_n33.getExplosivo());
			chbMinas_antipersona.setChecked(ficha_epidemiologia_n33.getMinas_antipersona());
			chbQuemadura.setChecked(ficha_epidemiologia_n33.getQuemadura());
			chbOtra.setChecked(ficha_epidemiologia_n33.getOtra());
			tbxNombre_sustancia.setValue(ficha_epidemiologia_n33.getNombre_sustancia());
			tbxCual_otra_arma.setValue(ficha_epidemiologia_n33.getCual_otra_arma());
			Utilidades.seleccionarRadio(rdbOtros_mecanismos, ficha_epidemiologia_n33.getOtros_mecanismos());
			tbxCual_otro_meca.setValue(ficha_epidemiologia_n33.getCual_otro_meca());
			dtbxFecha_del_hecho.setValue(ficha_epidemiologia_n33.getFecha_del_hecho());
			tbHora_del_hecho.setValue(ficha_epidemiologia_n33.getHora_del_hecho());
			Utilidades.seleccionarRadio(rdbEscenario, ficha_epidemiologia_n33.getEscenario());
//			tbxOtro.setValue(ficha_epidemiologia_n33.getOtro());
			tbxCual_otro_esc.setValue(ficha_epidemiologia_n33.getCual_otro_esc());
			Utilidades.seleccionarRadio(rdbZona_de_conflicto, ficha_epidemiologia_n33.getZona_de_conflicto());
			Utilidades.seleccionarRadio(rdbSector_que_notifica, ficha_epidemiologia_n33.getSector_que_notifica());
			tbxCual_sector.setValue(ficha_epidemiologia_n33.getCual_sector());
			chbAtencion_psicosocial.setChecked(ficha_epidemiologia_n33.getAtencion_psicosocial());
			chbProfilaxis.setChecked(ficha_epidemiologia_n33.getProfilaxis());
			chbAnticoncepcion_de_emerg.setChecked(ficha_epidemiologia_n33.getAnticoncepcion_de_emerg());
			chbOrientacion_ive.setChecked(ficha_epidemiologia_n33.getOrientacion_ive());
			chbAtencion_en_salud.setChecked(ficha_epidemiologia_n33.getAtencion_en_salud());
			chbInforme_autoridad.setChecked(ficha_epidemiologia_n33.getInforme_autoridad());
			chbOtro2.setChecked(ficha_epidemiologia_n33.getOtro2());
			tbxCual_otra_accion.setValue(ficha_epidemiologia_n33.getCual_otra_accion());
			tbxCual_autoridad.setValue(ficha_epidemiologia_n33.getCual_autoridad());
			Utilidades.seleccionarRadio(rdbRecomendacion, ficha_epidemiologia_n33.getRecomendacion());
			Utilidades.seleccionarRadio(rdbAmerita_trabajo, ficha_epidemiologia_n33.getAmerita_trabajo());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n33 ficha_epidemiologia_n33 = (Ficha_epidemiologia_n33)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n33);
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
	
	public void buscarOcupacion(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getOcupacionesService().setLimit(
					"limit 25 offset 0");

			List<Ocupaciones> list = getServiceLocator().getOcupacionesService().listar(parameters);

			lbx.getItems().clear();

			for (Ocupaciones dato : list) {

				Ocupaciones ocupaciones = new Ocupaciones();
				ocupaciones.setId(dato.getId());
				ocupaciones = getServiceLocator().getOcupacionesService()
						.consultar(ocupaciones);

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getId()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()
						+ ""));
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
			tbxOcupacion_del_agresor.setValue("");
		} else {
			Ocupaciones dato = (Ocupaciones) listitem.getValue();
			tbxOcupacion_del_agresor.setValue(dato.getId());
		}
		tbxOcupacion_del_agresor.close();
	}
	
	public void obtenerAdmision(Admision admision){
		Paciente paciente = admision.getPaciente();
		tbxIdentificacion.setValue(admision.getNro_identificacion());
		tbxNombres_y_apellidos.setValue(paciente.getNombreCompleto());
		tbxTipo_identidad.setValue(paciente.getTipo_identificacion());
		
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
	public Ficha_epidemiologia_n33 consultarDatos(Map<String, Object> map,
			Ficha_epidemiologia ficha_epidemiologia) throws Exception {
//		Ficha_epidemiologia ficha = (Ficha_epidemiologia)ficha_epidemiologia;
		
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
		
		List<Ficha_epidemiologia_n33> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
				Ficha_epidemiologia_n33.class, parameters);
		
		//log.info("lista_datos"+lista_datos);
		
		if (!lista_datos.isEmpty()){
			Ficha_epidemiologia_n33 ficha_n33 = lista_datos.get(lista_datos.size() -1);
			
			return ficha_n33;
		}else{

			return null;
		}
		
	}
	
	

}