/*
 * ficha_epidemiologia_n15Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n15;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n15Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n15>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n15Action.class);
	
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
	@View private Radiogroup rdbViajo_durante_ult_dias;
	@View private Listbox lbxDepartamento_municipio_viajo;
//	@View private Intbox ibxCodigo_dpto;
	@View private Listbox lbxCodigo_municipio;
	@View private Radiogroup rdbAntecedente_malaria;
	@View private Datebox dtbxFecha_notificacion;
	@View private Radiogroup rdbMedicacion_antimalarica;
	@View private Radiogroup rdbAtecedente_transfuncional;
	@View private Datebox dtbxFecha_aproximada;
	@View private Radiogroup rdbTipo_complicacion;
	@View private Checkbox chbFiebre;
	@View private Checkbox chbAdinamia;
	@View private Checkbox chbHipotension;
	@View private Checkbox chbChoque;
	@View private Checkbox chbCefalea;
	@View private Checkbox chbHemoglobina;
	@View private Checkbox chbInsuficiencia_renal;
	@View private Checkbox chbEscalofrio;
	@View private Checkbox chbPlaquetas;
	@View private Checkbox chbInsuficiencia_respi;
	@View private Checkbox chbSudoracion;
	@View private Checkbox chbHemorragias;
	@View private Checkbox chbInsuficiencia_hepatica;
	@View private Checkbox chbMialgias;
	@View private Checkbox chbCid;
	@View private Checkbox chbConfusion;
	@View private Checkbox chbHiperemesis;
	@View private Checkbox chbHepatomegalia;
	@View private Checkbox chbSomnolencia;
	@View private Checkbox chbNauseas;
	@View private Checkbox chbEsplenomegalia;
	@View private Checkbox chbConvulsion;
	@View private Checkbox chbAstenia;
	@View private Checkbox chbEdema_pulmonar;
	@View private Checkbox chbComa;
	@View private Radiogroup rdbEspecie_de_plasmodium;
	@View private Radiogroup rdbPaciente_embarazo;
	@View private Checkbox chbSangre1;
	@View private Checkbox chbSangre2;
	@View private Checkbox chbSangre3;
	@View private Checkbox chbSangre4;
	@View private Checkbox chbSangre5;
	@View private Checkbox chbSangre6;
	@View private Checkbox chbSangre7;
	@View private Checkbox chbRecuento_parasitario;
	@View private Checkbox chbCreatininia;
	@View private Checkbox chbTgo;
	@View private Checkbox chbTgp;
	@View private Checkbox chbBilirrubina_total;
	@View private Checkbox chbBilirrubina_directa;
	@View private Checkbox chbGlucosa;
	@View private Textbox tbxValor1;
	@View private Textbox tbxValor2;
	@View private Textbox tbxValor3;
	@View private Textbox tbxValor4;
	@View private Textbox tbxValor5;
	@View private Textbox tbxValor6;
	@View private Textbox tbxValor7;
	@View private Radiogroup rdbTratamiento_antimalarico;
	@View private Textbox tbxCual;
	@View private Toolbarbutton btGuardar;
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception{
		listarCombos();
	Utilidades.listarDepartamentos(lbxDepartamento_municipio_viajo,true,getServiceLocator());
	Utilidades.listarMunicipios(lbxCodigo_municipio,lbxDepartamento_municipio_viajo,getServiceLocator());
	obtenerAdmision(admision);

	if(ficha_seleccionada != null){
		//log.info("consultar ficha-------> "+ficha_seleccionada);
		Ficha_epidemiologia_n15 ficha = new Ficha_epidemiologia_n15();
		ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
		ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
		ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
		ficha = (Ficha_epidemiologia_n15) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
		
		//log.info("consultar ficha 15-------> "+ficha);
		
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
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxDepartamento_municipio_viajo,
				getServiceLocator());
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarFichaEpidemiologia(){
		
		tbxIdentificacion.setStyle("text-transform:uppercase;background-color:white");
		lbxDepartamento_municipio_viajo.setStyle("background-color:white;");
		lbxCodigo_municipio.setStyle("background-color:white;");
		
		boolean valida = true;
		
		if(tbxIdentificacion.getText().trim().equals("")){
			tbxIdentificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if (lbxDepartamento_municipio_viajo.getSelectedItem().getValue().toString().equals("")) {
			lbxDepartamento_municipio_viajo.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (lbxCodigo_municipio.getSelectedItem() != null) {
			if (lbxCodigo_municipio.getSelectedItem().getValue().toString()
					.equals("")) {
				lbxCodigo_municipio.setStyle("background-color:#F6BBBE");
				valida = false;
			}
		}

		if (lbxCodigo_municipio.getSelectedItem() == null) {
			lbxCodigo_municipio.setStyle("background-color:#F6BBBE");
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
			
			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
			
			List<Ficha_epidemiologia_n15> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService()
					.listar(Ficha_epidemiologia_n15.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n15 ficha_epidemiologia_n15 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n15, this));
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
		
		final Ficha_epidemiologia_n15 ficha_epidemiologia_n15 = (Ficha_epidemiologia_n15)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n15.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n15.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n15.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n15);
			}
		});
		hbox.appendChild(img);
		
		
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public  Ficha_epidemiologia_n15 obtenerFichaEpidemiologia(){
				
				Ficha_epidemiologia_n15 ficha_epidemiologia_n15 = new Ficha_epidemiologia_n15();
				ficha_epidemiologia_n15.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n15.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n15.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n15.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n15.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n15.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n15.setViajo_durante_ult_dias(rdbViajo_durante_ult_dias.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n15.setDepartamento_municipio_viajo(lbxDepartamento_municipio_viajo.getSelectedItem().getValue().toString());
//				ficha_epidemiologia_n15.setCodigo_dpto((ibxCodigo_dpto.getValue()!=null?ibxCodigo_dpto.getValue():0));
				ficha_epidemiologia_n15.setCodigo_municipio((lbxCodigo_municipio.getSelectedItem().getValue().toString()));
				ficha_epidemiologia_n15.setAntecedente_malaria(rdbAntecedente_malaria.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n15.setFecha_notificacion(new Timestamp(dtbxFecha_notificacion.getValue().getTime()));
				ficha_epidemiologia_n15.setMedicacion_antimalarica(rdbMedicacion_antimalarica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n15.setAtecedente_transfuncional(rdbAtecedente_transfuncional.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n15.setFecha_aproximada(new Timestamp(dtbxFecha_aproximada.getValue().getTime()));
				ficha_epidemiologia_n15.setTipo_complicacion(rdbTipo_complicacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n15.setFiebre(chbFiebre.isChecked());
				ficha_epidemiologia_n15.setAdinamia(chbAdinamia.isChecked());
				ficha_epidemiologia_n15.setHipotension(chbHipotension.isChecked());
				ficha_epidemiologia_n15.setChoque(chbChoque.isChecked());
				ficha_epidemiologia_n15.setCefalea(chbCefalea.isChecked());
				ficha_epidemiologia_n15.setHemoglobina(chbHemoglobina.isChecked());
				ficha_epidemiologia_n15.setInsuficiencia_renal(chbInsuficiencia_renal.isChecked());
				ficha_epidemiologia_n15.setEscalofrio(chbEscalofrio.isChecked());
				ficha_epidemiologia_n15.setPlaquetas(chbPlaquetas.isChecked());
				ficha_epidemiologia_n15.setInsuficiencia_respi(chbInsuficiencia_respi.isChecked());
				ficha_epidemiologia_n15.setSudoracion(chbSudoracion.isChecked());
				ficha_epidemiologia_n15.setHemorragias(chbHemorragias.isChecked());
				ficha_epidemiologia_n15.setInsuficiencia_hepatica(chbInsuficiencia_hepatica.isChecked());
				ficha_epidemiologia_n15.setMialgias(chbMialgias.isChecked());
				ficha_epidemiologia_n15.setCid(chbCid.isChecked());
				ficha_epidemiologia_n15.setConfusion(chbConfusion.isChecked());
				ficha_epidemiologia_n15.setHiperemesis(chbHiperemesis.isChecked());
				ficha_epidemiologia_n15.setHepatomegalia(chbHepatomegalia.isChecked());
				ficha_epidemiologia_n15.setSomnolencia(chbSomnolencia.isChecked());
				ficha_epidemiologia_n15.setNauseas(chbNauseas.isChecked());
				ficha_epidemiologia_n15.setEsplenomegalia(chbEsplenomegalia.isChecked());
				ficha_epidemiologia_n15.setConvulsion(chbConvulsion.isChecked());
				ficha_epidemiologia_n15.setAstenia(chbAstenia.isChecked());
				ficha_epidemiologia_n15.setEdema_pulmonar(chbEdema_pulmonar.isChecked());
				ficha_epidemiologia_n15.setComa(chbComa.isChecked());
				ficha_epidemiologia_n15.setEspecie_de_plasmodium(rdbEspecie_de_plasmodium.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n15.setPaciente_embarazo(rdbPaciente_embarazo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n15.setSangre1(chbSangre1.isChecked());
				ficha_epidemiologia_n15.setSangre2(chbSangre2.isChecked());
				ficha_epidemiologia_n15.setSangre3(chbSangre3.isChecked());
				ficha_epidemiologia_n15.setSangre4(chbSangre4.isChecked());
				ficha_epidemiologia_n15.setSangre5(chbSangre5.isChecked());
				ficha_epidemiologia_n15.setSangre6(chbSangre6.isChecked());
				ficha_epidemiologia_n15.setSangre7(chbSangre7.isChecked());
				ficha_epidemiologia_n15.setRecuento_parasitario(chbRecuento_parasitario.isChecked());
				ficha_epidemiologia_n15.setCreatininia(chbCreatininia.isChecked());
				ficha_epidemiologia_n15.setTgo(chbTgo.isChecked());
				ficha_epidemiologia_n15.setTgp(chbTgp.isChecked());
				ficha_epidemiologia_n15.setBilirrubina_total(chbBilirrubina_total.isChecked());
				ficha_epidemiologia_n15.setBilirrubina_directa(chbBilirrubina_directa.isChecked());
				ficha_epidemiologia_n15.setGlucosa(chbGlucosa.isChecked());
				ficha_epidemiologia_n15.setValor1(tbxValor1.getValue());
				ficha_epidemiologia_n15.setValor2(tbxValor2.getValue());
				ficha_epidemiologia_n15.setValor3(tbxValor3.getValue());
				ficha_epidemiologia_n15.setValor4(tbxValor4.getValue());
				ficha_epidemiologia_n15.setValor5(tbxValor5.getValue());
				ficha_epidemiologia_n15.setValor6(tbxValor6.getValue());
				ficha_epidemiologia_n15.setValor7(tbxValor7.getValue());
				ficha_epidemiologia_n15.setTratamiento_antimalarico(rdbTratamiento_antimalarico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n15.setCual(tbxCual.getValue());
				ficha_epidemiologia_n15.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n15.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n15.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n15.setDelete_date(null);
				ficha_epidemiologia_n15.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n15.setDelete_user(null);
				
				return ficha_epidemiologia_n15;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n15 obj)throws Exception{
		Ficha_epidemiologia_n15 ficha_epidemiologia_n15 = (Ficha_epidemiologia_n15)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n15.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n15.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n15.getFecha_inicial());
			tbxIdentificacion.setValue(ficha_epidemiologia_n15.getIdentificacion());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbViajo_durante_ult_dias, ficha_epidemiologia_n15.getViajo_durante_ult_dias());
			
			for (int i = 0; i < lbxDepartamento_municipio_viajo.getItemCount(); i++) {
				Listitem listitem = lbxDepartamento_municipio_viajo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n15.getCodigo_dpto())) {
					listitem.setSelected(true);
					i = lbxDepartamento_municipio_viajo.getItemCount();
				}
			}

			Utilidades.listarMunicipios(lbxCodigo_municipio, lbxDepartamento_municipio_viajo,
					getServiceLocator());
			for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_municipio.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n15.getCodigo_municipio())) {
					listitem.setSelected(true);
					i = lbxCodigo_municipio.getItemCount();
				}
			}
			
			
			
//			ibxCodigo_dpto.setValue(ficha_epidemiologia_n15.getCodigo_dpto());
		
			Utilidades.seleccionarRadio(rdbAntecedente_malaria, ficha_epidemiologia_n15.getAntecedente_malaria());
			dtbxFecha_notificacion.setValue(ficha_epidemiologia_n15.getFecha_notificacion());
			Utilidades.seleccionarRadio(rdbMedicacion_antimalarica, ficha_epidemiologia_n15.getMedicacion_antimalarica());
			Utilidades.seleccionarRadio(rdbAtecedente_transfuncional, ficha_epidemiologia_n15.getAtecedente_transfuncional());
			dtbxFecha_aproximada.setValue(ficha_epidemiologia_n15.getFecha_aproximada());
			Utilidades.seleccionarRadio(rdbTipo_complicacion, ficha_epidemiologia_n15.getTipo_complicacion());
			chbFiebre.setChecked(ficha_epidemiologia_n15.getFiebre());
			chbAdinamia.setChecked(ficha_epidemiologia_n15.getAdinamia());
			chbHipotension.setChecked(ficha_epidemiologia_n15.getHipotension());
			chbChoque.setChecked(ficha_epidemiologia_n15.getChoque());
			chbCefalea.setChecked(ficha_epidemiologia_n15.getCefalea());
			chbHemoglobina.setChecked(ficha_epidemiologia_n15.getHemoglobina());
			chbInsuficiencia_renal.setChecked(ficha_epidemiologia_n15.getInsuficiencia_renal());
			chbEscalofrio.setChecked(ficha_epidemiologia_n15.getEscalofrio());
			chbPlaquetas.setChecked(ficha_epidemiologia_n15.getPlaquetas());
			chbInsuficiencia_respi.setChecked(ficha_epidemiologia_n15.getInsuficiencia_respi());
			chbSudoracion.setChecked(ficha_epidemiologia_n15.getSudoracion());
			chbHemorragias.setChecked(ficha_epidemiologia_n15.getHemorragias());
			chbInsuficiencia_hepatica.setChecked(ficha_epidemiologia_n15.getInsuficiencia_hepatica());
			chbMialgias.setChecked(ficha_epidemiologia_n15.getMialgias());
			chbCid.setChecked(ficha_epidemiologia_n15.getCid());
			chbConfusion.setChecked(ficha_epidemiologia_n15.getConfusion());
			chbHiperemesis.setChecked(ficha_epidemiologia_n15.getHiperemesis());
			chbHepatomegalia.setChecked(ficha_epidemiologia_n15.getHepatomegalia());
			chbSomnolencia.setChecked(ficha_epidemiologia_n15.getSomnolencia());
			chbNauseas.setChecked(ficha_epidemiologia_n15.getNauseas());
			chbEsplenomegalia.setChecked(ficha_epidemiologia_n15.getEsplenomegalia());
			chbConvulsion.setChecked(ficha_epidemiologia_n15.getConvulsion());
			chbAstenia.setChecked(ficha_epidemiologia_n15.getAstenia());
			chbEdema_pulmonar.setChecked(ficha_epidemiologia_n15.getEdema_pulmonar());
			chbComa.setChecked(ficha_epidemiologia_n15.getComa());
			Utilidades.seleccionarRadio(rdbEspecie_de_plasmodium, ficha_epidemiologia_n15.getEspecie_de_plasmodium());
			Utilidades.seleccionarRadio(rdbPaciente_embarazo, ficha_epidemiologia_n15.getPaciente_embarazo());
			chbSangre1.setChecked(ficha_epidemiologia_n15.getSangre1());
			chbSangre2.setChecked(ficha_epidemiologia_n15.getSangre2());
			chbSangre3.setChecked(ficha_epidemiologia_n15.getSangre3());
			chbSangre4.setChecked(ficha_epidemiologia_n15.getSangre4());
			chbSangre5.setChecked(ficha_epidemiologia_n15.getSangre5());
			chbSangre6.setChecked(ficha_epidemiologia_n15.getSangre6());
			chbSangre7.setChecked(ficha_epidemiologia_n15.getSangre7());
			chbRecuento_parasitario.setChecked(ficha_epidemiologia_n15.getRecuento_parasitario());
			chbCreatininia.setChecked(ficha_epidemiologia_n15.getCreatininia());
			chbTgo.setChecked(ficha_epidemiologia_n15.getTgo());
			chbTgp.setChecked(ficha_epidemiologia_n15.getTgp());
			chbBilirrubina_total.setChecked(ficha_epidemiologia_n15.getBilirrubina_total());
			chbBilirrubina_directa.setChecked(ficha_epidemiologia_n15.getBilirrubina_directa());
			chbGlucosa.setChecked(ficha_epidemiologia_n15.getGlucosa());
			tbxValor1.setValue(ficha_epidemiologia_n15.getValor1());
			tbxValor2.setValue(ficha_epidemiologia_n15.getValor2());
			tbxValor3.setValue(ficha_epidemiologia_n15.getValor3());
			tbxValor4.setValue(ficha_epidemiologia_n15.getValor4());
			tbxValor5.setValue(ficha_epidemiologia_n15.getValor5());
			tbxValor6.setValue(ficha_epidemiologia_n15.getValor6());
			tbxValor7.setValue(ficha_epidemiologia_n15.getValor7());
			Utilidades.seleccionarRadio(rdbTratamiento_antimalarico, ficha_epidemiologia_n15.getTratamiento_antimalarico());
			tbxCual.setValue(ficha_epidemiologia_n15.getCual());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n15 ficha_epidemiologia_n15 = (Ficha_epidemiologia_n15)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n15);
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
	
	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		Utilidades.listarMunicipios(listboxMun, listboxDpto,getServiceLocator());
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
	public Ficha_epidemiologia_n15 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n15> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n15.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n15 ficha_n15 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n15;
				}else{

					return null;
				}
	}
	
}