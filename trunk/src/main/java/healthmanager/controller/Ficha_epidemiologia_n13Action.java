/*
 * ficha_epidemiologia_n13Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n13;
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
import org.zkoss.zul.Intbox;
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

public class Ficha_epidemiologia_n13Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n13>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n13Action.class);
	
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
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	
	@View private Checkbox chbFiebre;
	@View private Checkbox chbMialgias;
	@View private Checkbox chbCefalea;
	@View private Checkbox chbArtralgias;
	@View private Checkbox chbVomito;
	@View private Checkbox chbNausea;
	@View private Checkbox chbDolor_retrocular;
	@View private Checkbox chbHiperemia_conjuntival;
	@View private Checkbox chbSecrecion_conjuntival;
	@View private Checkbox chbDolor_pantorrillas;
	@View private Checkbox chbDiarrea;
	@View private Checkbox chbDolor_abdominal;
	@View private Checkbox chbHemoptisis;
	@View private Checkbox chbMelenas;
	@View private Checkbox chbEpistaxis;
	@View private Checkbox chbErupcion;
	@View private Checkbox chbHematuria;
	@View private Checkbox chbTomiquete_postiva;
	@View private Checkbox chbEsplenomegalia;
	@View private Checkbox chbSignos_meningeos;
	@View private Checkbox chbDisnea;
	@View private Checkbox chbTos;
	@View private Checkbox chbInsuficiencia_respiratoria;
	@View private Checkbox chbHepatomeglia;
	@View private Checkbox chbIctericia;
	@View private Checkbox chbInsuficiencia_hepatica;
	@View private Checkbox chbInsuficiencia_renal;
	@View private Radiogroup rdbVacuna_fiebre_amarilla;
	@View private Intbox ibxDosis_fiebre_amarilla;
	@View private Radiogroup rdbVacuna_hepatitis_a;
	@View private Intbox ibxDosis_hepatitis_a;
	@View private Radiogroup rdbVacuna_hepatitis_b;
	@View private Intbox ibxDosis_hepatitis_b;
	@View private Radiogroup rdbVacuna_leptospirosis;
	@View private Intbox ibxDosis_leptospirosis;
	@View private Checkbox chbPerros;
	@View private Checkbox chbGatos;
	@View private Checkbox chbBovinos;
	@View private Checkbox chbEquinos;
	@View private Checkbox chbPorcions;
	@View private Checkbox chbNinguno;
	@View private Checkbox chbOtros_animal;
	@View private Textbox tbxCual_otro;
	@View private Radiogroup rdbContacto_animales;
	@View private Radiogroup rdbRatas_domicilio;
	@View private Radiogroup rdbRatas_trabajo;
	@View private Checkbox chbAcueducto;
	@View private Checkbox chbPozo;
	@View private Checkbox chbRio;
	@View private Checkbox chbTanque_almacenamiento;
	@View private Radiogroup rdbAlcantarillas;
	@View private Radiogroup rdbInundaciones;
	@View private Radiogroup rdbContacto_aguas_estancadas;
	@View private Radiogroup rdbAntecedentes_deportivos;
	@View private Radiogroup rdbDisposicion_residuos;
	@View private Radiogroup rdbTiempo_almacenamiento;
	@View private Radiogroup rdbPersonas_sintomatologia;
	@View private Checkbox chbLeucocitosis;
	@View private Checkbox chbLeucopenia;
	@View private Checkbox chbNeutrofilia;
	@View private Checkbox chbNeutropenia;
	@View private Checkbox chbLinfocitocis;
	@View private Checkbox chbTrombocitosis;
	@View private Checkbox chbTrombocitopenia;
	@View private Checkbox chbHemoconcentracion;
	@View private Checkbox chbAlteracion_trasaminasas;
	@View private Checkbox chbAlteracion_bilirrubinas;
	@View private Checkbox chbAlteracion_bun;
	@View private Checkbox chbAlteracion_creatinina;
	@View private Checkbox chbCpk_elevada;
	@View private Radiogroup rdbDengue;
	@View private Radiogroup rdbMalaria;
	@View private Radiogroup rdbHepatitis_a;
	@View private Radiogroup rdbHepatitis_b;
	@View private Radiogroup rdbHepatitis_c;
	@View private Radiogroup rdbFiebre_amarilla;
	@View private Radiogroup rdbTipo_muestra;
	@View private Radiogroup rdbDestino_muestra;
	@View private Textbox tbxOtra_muestra;
	@View private Radiogroup rdbCultivo;
	@View private Radiogroup rdbHistoquimica;
	@View private Radiogroup rdbPcr;
	@View private Radiogroup rdbElisa;
	@View private Radiogroup rdbMicroaglutinacion;
	@View private Radiogroup rdbPareadas;
	@View private Datebox dtbxFecha_muestra1;
	@View private Datebox dtbxFecha_muestra2;
	@View private Radiogroup rdbIdentificacion_serogrupos;
	@View private Textbox tbxTitulo_muestra1;
	@View private Textbox tbxTitulo_muestra2;
	@View private Radiogroup rdbTratamiento;
	@View private Textbox tbxCual_tratamiento;
	@View private Textbox tbxTratamiento_antibiotico;
	@View private Intbox ibxDosis;
	@View private Intbox ibxTiempo;
	@View private Textbox tbxCodigo_medico;
	private final String[] idsExcluyentes = {};
	@View private Textbox tbxOtro_serogrupo;

	@View private Toolbarbutton btGuardar;
	
	@View private Row rowSerogrupo;
	@View private Row rowMuestra;
	@View private Label lbCual_otro;
	@View private Label lbOtra_muestra;
	@View private Label lOtro_serogrupo;
	
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init(){
		try {
			listarCombos();
			obtenerAdmision(admision);
			FormularioUtil.inicializarRadiogroupsDefecto(this);
			
			if(ficha_seleccionada != null){
				//log.info("consultar ficha-------> "+ficha_seleccionada);
				Ficha_epidemiologia_n13 ficha = new Ficha_epidemiologia_n13();
				ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
				ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
				ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
				ficha = (Ficha_epidemiologia_n13) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
				
				//log.info("consultar ficha 13-------> "+ficha);
				
				mostrarDatos(ficha);
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
		listitem.setLabel("código Ficha");
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
			dtbxFecha_muestra1.setValue(null);
			dtbxFecha_muestra2.setValue(null);
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
		
		boolean valida = true;
		
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
			
			List<Ficha_epidemiologia_n13> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n13.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n13 ficha_epidemiologia_n13 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n13, this));
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
		
		final Ficha_epidemiologia_n13 ficha_epidemiologia_n13 = (Ficha_epidemiologia_n13)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n13.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n13.getIdentificacion()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n13.getFecha_creacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n13);
			}
		});
		hbox.appendChild(img);
		
			
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n13 obtenerFichaEpidemiologia() {
				
				Ficha_epidemiologia_n13 ficha_epidemiologia_n13 = new Ficha_epidemiologia_n13();
				ficha_epidemiologia_n13.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n13.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n13.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n13.setIdentificacion(tbxIdentificacion.getValue());
				ficha_epidemiologia_n13.setFecha_creacion(new Timestamp(dtbxFecha_creacion.getValue().getTime()));
				ficha_epidemiologia_n13.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n13.setFiebre(chbFiebre.isChecked());
				ficha_epidemiologia_n13.setMialgias(chbMialgias.isChecked());
				ficha_epidemiologia_n13.setCefalea(chbCefalea.isChecked());
				ficha_epidemiologia_n13.setArtralgias(chbArtralgias.isChecked());
				ficha_epidemiologia_n13.setVomito(chbVomito.isChecked());
				ficha_epidemiologia_n13.setNausea(chbNausea.isChecked());
				ficha_epidemiologia_n13.setDolor_retrocular(chbDolor_retrocular.isChecked());
				ficha_epidemiologia_n13.setHiperemia_conjuntival(chbHiperemia_conjuntival.isChecked());
				ficha_epidemiologia_n13.setSecrecion_conjuntival(chbSecrecion_conjuntival.isChecked());
				ficha_epidemiologia_n13.setDolor_pantorrillas(chbDolor_pantorrillas.isChecked());
				ficha_epidemiologia_n13.setDiarrea(chbDiarrea.isChecked());
				ficha_epidemiologia_n13.setDolor_abdominal(chbDolor_abdominal.isChecked());
				ficha_epidemiologia_n13.setHemoptisis(chbHemoptisis.isChecked());
				ficha_epidemiologia_n13.setMelenas(chbMelenas.isChecked());
				ficha_epidemiologia_n13.setEpistaxis(chbEpistaxis.isChecked());
				ficha_epidemiologia_n13.setErupcion(chbErupcion.isChecked());
				ficha_epidemiologia_n13.setHematuria(chbHematuria.isChecked());
				ficha_epidemiologia_n13.setTomiquete_postiva(chbTomiquete_postiva.isChecked());
				ficha_epidemiologia_n13.setEsplenomegalia(chbEsplenomegalia.isChecked());
				ficha_epidemiologia_n13.setSignos_meningeos(chbSignos_meningeos.isChecked());
				ficha_epidemiologia_n13.setDisnea(chbDisnea.isChecked());
				ficha_epidemiologia_n13.setTos(chbTos.isChecked());
				ficha_epidemiologia_n13.setInsuficiencia_respiratoria(chbInsuficiencia_respiratoria.isChecked());
				ficha_epidemiologia_n13.setHepatomeglia(chbHepatomeglia.isChecked());
				ficha_epidemiologia_n13.setIctericia(chbIctericia.isChecked());
				ficha_epidemiologia_n13.setInsuficiencia_hepatica(chbInsuficiencia_hepatica.isChecked());
				ficha_epidemiologia_n13.setInsuficiencia_renal(chbInsuficiencia_renal.isChecked());
				ficha_epidemiologia_n13.setVacuna_fiebre_amarilla(rdbVacuna_fiebre_amarilla.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setDosis_fiebre_amarilla(ibxDosis_fiebre_amarilla.getValue()!=null ? ibxDosis_fiebre_amarilla.getValue() + "" : "");
				ficha_epidemiologia_n13.setVacuna_hepatitis_a(rdbVacuna_hepatitis_a.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setDosis_hepatitis_a(ibxDosis_hepatitis_a.getValue()!=null?ibxDosis_hepatitis_a.getValue() + "" : "");
				ficha_epidemiologia_n13.setVacuna_hepatitis_b(rdbVacuna_hepatitis_b.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setDosis_hepatitis_b(ibxDosis_hepatitis_b.getValue()!=null?ibxDosis_hepatitis_b.getValue() + "" : "");
				ficha_epidemiologia_n13.setVacuna_leptospirosis(rdbVacuna_leptospirosis.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setDosis_leptospirosis(ibxDosis_leptospirosis.getValue()!=null?ibxDosis_leptospirosis.getValue() + "" : "");
				ficha_epidemiologia_n13.setPerros(chbPerros.isChecked());
				ficha_epidemiologia_n13.setGatos(chbGatos.isChecked());
				ficha_epidemiologia_n13.setBovinos(chbBovinos.isChecked());
				ficha_epidemiologia_n13.setEquinos(chbEquinos.isChecked());
				ficha_epidemiologia_n13.setPorcions(chbPorcions.isChecked());
				ficha_epidemiologia_n13.setNinguno(chbNinguno.isChecked());
				ficha_epidemiologia_n13.setOtros_animal(chbOtros_animal.isChecked());
				ficha_epidemiologia_n13.setCual_otro(tbxCual_otro.getValue());
				ficha_epidemiologia_n13.setContacto_animales(rdbContacto_animales.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setRatas_domicilio(rdbRatas_domicilio.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setRatas_trabajo(rdbRatas_trabajo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setAcueducto(chbAcueducto.isChecked());
				ficha_epidemiologia_n13.setPozo(chbPozo.isChecked());
				ficha_epidemiologia_n13.setRio(chbRio.isChecked());
				ficha_epidemiologia_n13.setTanque_almacenamiento(chbTanque_almacenamiento.isChecked());
				ficha_epidemiologia_n13.setAlcantarillas(rdbAlcantarillas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setInundaciones(rdbInundaciones.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setContacto_aguas_estancadas(rdbContacto_aguas_estancadas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setAntecedentes_deportivos(rdbAntecedentes_deportivos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setDisposicion_residuos(rdbDisposicion_residuos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setTiempo_almacenamiento(rdbTiempo_almacenamiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setPersonas_sintomatologia(rdbPersonas_sintomatologia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setLeucocitosis(chbLeucocitosis.isChecked());
				ficha_epidemiologia_n13.setLeucopenia(chbLeucopenia.isChecked());
				ficha_epidemiologia_n13.setNeutrofilia(chbNeutrofilia.isChecked());
				ficha_epidemiologia_n13.setNeutropenia(chbNeutropenia.isChecked());
				ficha_epidemiologia_n13.setLinfocitocis(chbLinfocitocis.isChecked());
				ficha_epidemiologia_n13.setTrombocitosis(chbTrombocitosis.isChecked());
				ficha_epidemiologia_n13.setTrombocitopenia(chbTrombocitopenia.isChecked());
				ficha_epidemiologia_n13.setHemoconcentracion(chbHemoconcentracion.isChecked());
				ficha_epidemiologia_n13.setAlteracion_trasaminasas(chbAlteracion_trasaminasas.isChecked());
				ficha_epidemiologia_n13.setAlteracion_bilirrubinas(chbAlteracion_bilirrubinas.isChecked());
				ficha_epidemiologia_n13.setAlteracion_bun(chbAlteracion_bun.isChecked());
				ficha_epidemiologia_n13.setAlteracion_creatinina(chbAlteracion_creatinina.isChecked());
				ficha_epidemiologia_n13.setCpk_elevada(chbCpk_elevada.isChecked());
				ficha_epidemiologia_n13.setDengue(rdbDengue.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setMalaria(rdbMalaria.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setHepatitis_a(rdbHepatitis_a.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setHepatitis_b(rdbHepatitis_b.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setHepatitis_c(rdbHepatitis_c.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setFiebre_amarilla(rdbFiebre_amarilla.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setTipo_muestra(rdbTipo_muestra.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setDestino_muestra(rdbDestino_muestra.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setOtra_muestra(tbxOtra_muestra.getValue());
				ficha_epidemiologia_n13.setCultivo(rdbCultivo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setHistoquimica(rdbHistoquimica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setPcr(rdbPcr.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setElisa(rdbElisa.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setMicroaglutinacion(rdbMicroaglutinacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setPareadas(rdbPareadas.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_muestra1.getValue() != null) {
					ficha_epidemiologia_n13.setFecha_muestra1(new Timestamp(dtbxFecha_muestra1.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n13.setFecha_muestra1(null);
				}
				

				
				if (dtbxFecha_muestra2.getValue() != null) {
					ficha_epidemiologia_n13.setFecha_muestra2(new Timestamp(dtbxFecha_muestra2.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n13.setFecha_muestra2(null);
				}
				
				ficha_epidemiologia_n13.setIdentificacion_serogrupos(rdbIdentificacion_serogrupos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setTitulo_muestra1(tbxTitulo_muestra1.getValue());
				ficha_epidemiologia_n13.setTitulo_muestra2(tbxTitulo_muestra2.getValue());
				ficha_epidemiologia_n13.setTratamiento(rdbTratamiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n13.setCual_tratamiento(tbxCual_tratamiento.getValue());
				ficha_epidemiologia_n13.setTratamiento_antibiotico(tbxTratamiento_antibiotico.getValue());
				ficha_epidemiologia_n13.setDosis(ibxDosis.getValue()!=null?ibxDosis.getValue() + "" : "");
				ficha_epidemiologia_n13.setTiempo(ibxTiempo.getValue()!=null?ibxTiempo.getValue() + "" : "");
				ficha_epidemiologia_n13.setCodigo_medico(tbxCodigo_medico.getValue());
				ficha_epidemiologia_n13.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n13.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n13.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n13.setDelete_date(null);
				ficha_epidemiologia_n13.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n13.setDelete_user(null);
				ficha_epidemiologia_n13.setOtro_serogrupo(tbxOtro_serogrupo.getValue());

				return ficha_epidemiologia_n13;
			 
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n13 obj)throws Exception{
		Ficha_epidemiologia_n13 ficha_epidemiologia_n13 = (Ficha_epidemiologia_n13)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n13.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n13.getIdentificacion());
			
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			dtbxFecha_creacion.setValue(ficha_epidemiologia_n13.getFecha_creacion());
			chbFiebre.setChecked(ficha_epidemiologia_n13.getFiebre());
			chbMialgias.setChecked(ficha_epidemiologia_n13.getMialgias());
			chbCefalea.setChecked(ficha_epidemiologia_n13.getCefalea());
			chbArtralgias.setChecked(ficha_epidemiologia_n13.getArtralgias());
			chbVomito.setChecked(ficha_epidemiologia_n13.getVomito());
			chbNausea.setChecked(ficha_epidemiologia_n13.getNausea());
			chbDolor_retrocular.setChecked(ficha_epidemiologia_n13.getDolor_retrocular());
			chbHiperemia_conjuntival.setChecked(ficha_epidemiologia_n13.getHiperemia_conjuntival());
			chbSecrecion_conjuntival.setChecked(ficha_epidemiologia_n13.getSecrecion_conjuntival());
			chbDolor_pantorrillas.setChecked(ficha_epidemiologia_n13.getDolor_pantorrillas());
			chbDiarrea.setChecked(ficha_epidemiologia_n13.getDiarrea());
			chbDolor_abdominal.setChecked(ficha_epidemiologia_n13.getDolor_abdominal());
			chbHemoptisis.setChecked(ficha_epidemiologia_n13.getHemoptisis());
			chbMelenas.setChecked(ficha_epidemiologia_n13.getMelenas());
			chbEpistaxis.setChecked(ficha_epidemiologia_n13.getEpistaxis());
			chbErupcion.setChecked(ficha_epidemiologia_n13.getErupcion());
			chbHematuria.setChecked(ficha_epidemiologia_n13.getHematuria());
			chbTomiquete_postiva.setChecked(ficha_epidemiologia_n13.getTomiquete_postiva());
			chbEsplenomegalia.setChecked(ficha_epidemiologia_n13.getEsplenomegalia());
			chbSignos_meningeos.setChecked(ficha_epidemiologia_n13.getSignos_meningeos());
			chbDisnea.setChecked(ficha_epidemiologia_n13.getDisnea());
			chbTos.setChecked(ficha_epidemiologia_n13.getTos());
			chbInsuficiencia_respiratoria.setChecked(ficha_epidemiologia_n13.getInsuficiencia_respiratoria());
			chbHepatomeglia.setChecked(ficha_epidemiologia_n13.getHepatomeglia());
			chbIctericia.setChecked(ficha_epidemiologia_n13.getIctericia());
			chbInsuficiencia_hepatica.setChecked(ficha_epidemiologia_n13.getInsuficiencia_hepatica());
			chbInsuficiencia_renal.setChecked(ficha_epidemiologia_n13.getInsuficiencia_renal());
			Utilidades.seleccionarRadio(rdbVacuna_fiebre_amarilla, ficha_epidemiologia_n13.getVacuna_fiebre_amarilla());
			ibxDosis_fiebre_amarilla.setValue((ficha_epidemiologia_n13.getDosis_fiebre_amarilla() != null && !ficha_epidemiologia_n13.getDosis_fiebre_amarilla().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n13.getDosis_fiebre_amarilla()) : null);
			Utilidades.seleccionarRadio(rdbVacuna_hepatitis_a, ficha_epidemiologia_n13.getVacuna_hepatitis_a());
			ibxDosis_hepatitis_a.setValue((ficha_epidemiologia_n13.getDosis_hepatitis_a() != null && !ficha_epidemiologia_n13.getDosis_hepatitis_a().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n13.getDosis_hepatitis_a()) : null);
			Utilidades.seleccionarRadio(rdbVacuna_hepatitis_b, ficha_epidemiologia_n13.getVacuna_hepatitis_b());
			ibxDosis_hepatitis_b.setValue((ficha_epidemiologia_n13.getDosis_hepatitis_b() != null && !ficha_epidemiologia_n13.getDosis_hepatitis_b().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n13.getDosis_hepatitis_b()) : null);
			Utilidades.seleccionarRadio(rdbVacuna_leptospirosis, ficha_epidemiologia_n13.getVacuna_leptospirosis());
			ibxDosis_leptospirosis.setValue((ficha_epidemiologia_n13.getDosis_leptospirosis() != null && !ficha_epidemiologia_n13.getDosis_leptospirosis().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n13.getDosis_leptospirosis()) : null);
			chbPerros.setChecked(ficha_epidemiologia_n13.getPerros());
			chbGatos.setChecked(ficha_epidemiologia_n13.getGatos());
			chbBovinos.setChecked(ficha_epidemiologia_n13.getBovinos());
			chbEquinos.setChecked(ficha_epidemiologia_n13.getEquinos());
			chbPorcions.setChecked(ficha_epidemiologia_n13.getPorcions());
			chbNinguno.setChecked(ficha_epidemiologia_n13.getNinguno());
			chbOtros_animal.setChecked(ficha_epidemiologia_n13.getOtros_animal());
			
			if(ficha_epidemiologia_n13.getOtros_animal()){
				lbCual_otro.setVisible(true);
				tbxCual_otro.setVisible(true);
				tbxCual_otro.setValue(ficha_epidemiologia_n13.getCual_otro());
				
			}else{
				lbCual_otro.setVisible(false);
				tbxCual_otro.setVisible(false);
				
			}
			
			Utilidades.seleccionarRadio(rdbContacto_animales, ficha_epidemiologia_n13.getContacto_animales());
			Utilidades.seleccionarRadio(rdbRatas_domicilio, ficha_epidemiologia_n13.getRatas_domicilio());
			Utilidades.seleccionarRadio(rdbRatas_trabajo, ficha_epidemiologia_n13.getRatas_trabajo());
			chbAcueducto.setChecked(ficha_epidemiologia_n13.getAcueducto());
			chbPozo.setChecked(ficha_epidemiologia_n13.getPozo());
			chbRio.setChecked(ficha_epidemiologia_n13.getRio());
			chbTanque_almacenamiento.setChecked(ficha_epidemiologia_n13.getTanque_almacenamiento());
			Utilidades.seleccionarRadio(rdbAlcantarillas, ficha_epidemiologia_n13.getAlcantarillas());
			Utilidades.seleccionarRadio(rdbInundaciones, ficha_epidemiologia_n13.getInundaciones());
			Utilidades.seleccionarRadio(rdbContacto_aguas_estancadas, ficha_epidemiologia_n13.getContacto_aguas_estancadas());
			Utilidades.seleccionarRadio(rdbAntecedentes_deportivos, ficha_epidemiologia_n13.getAntecedentes_deportivos());
			Utilidades.seleccionarRadio(rdbDisposicion_residuos, ficha_epidemiologia_n13.getDisposicion_residuos());
			Utilidades.seleccionarRadio(rdbTiempo_almacenamiento, ficha_epidemiologia_n13.getTiempo_almacenamiento());
			Utilidades.seleccionarRadio(rdbPersonas_sintomatologia, ficha_epidemiologia_n13.getPersonas_sintomatologia());
			chbLeucocitosis.setChecked(ficha_epidemiologia_n13.getLeucocitosis());
			chbLeucopenia.setChecked(ficha_epidemiologia_n13.getLeucopenia());
			chbNeutrofilia.setChecked(ficha_epidemiologia_n13.getNeutrofilia());
			chbNeutropenia.setChecked(ficha_epidemiologia_n13.getNeutropenia());
			chbLinfocitocis.setChecked(ficha_epidemiologia_n13.getLinfocitocis());
			chbTrombocitosis.setChecked(ficha_epidemiologia_n13.getTrombocitosis());
			chbTrombocitopenia.setChecked(ficha_epidemiologia_n13.getTrombocitopenia());
			chbHemoconcentracion.setChecked(ficha_epidemiologia_n13.getHemoconcentracion());
			chbAlteracion_trasaminasas.setChecked(ficha_epidemiologia_n13.getAlteracion_trasaminasas());
			chbAlteracion_bilirrubinas.setChecked(ficha_epidemiologia_n13.getAlteracion_bilirrubinas());
			chbAlteracion_bun.setChecked(ficha_epidemiologia_n13.getAlteracion_bun());
			chbAlteracion_creatinina.setChecked(ficha_epidemiologia_n13.getAlteracion_creatinina());
			chbCpk_elevada.setChecked(ficha_epidemiologia_n13.getCpk_elevada());
			Utilidades.seleccionarRadio(rdbDengue, ficha_epidemiologia_n13.getDengue());
			Utilidades.seleccionarRadio(rdbMalaria, ficha_epidemiologia_n13.getMalaria());
			Utilidades.seleccionarRadio(rdbHepatitis_a, ficha_epidemiologia_n13.getHepatitis_a());
			Utilidades.seleccionarRadio(rdbHepatitis_b, ficha_epidemiologia_n13.getHepatitis_b());
			Utilidades.seleccionarRadio(rdbHepatitis_c, ficha_epidemiologia_n13.getHepatitis_c());
			Utilidades.seleccionarRadio(rdbFiebre_amarilla, ficha_epidemiologia_n13.getFiebre_amarilla());
			Utilidades.seleccionarRadio(rdbTipo_muestra, ficha_epidemiologia_n13.getTipo_muestra());
			Utilidades.seleccionarRadio(rdbDestino_muestra, ficha_epidemiologia_n13.getDestino_muestra());

			if( ficha_epidemiologia_n13.getDestino_muestra().equals("O")){
				rowMuestra.setVisible(true);
				lbOtra_muestra.setVisible(true);
				tbxOtra_muestra.setVisible(true);
				tbxOtra_muestra.setValue(ficha_epidemiologia_n13.getOtra_muestra());
				
			}else{
				rowMuestra.setVisible(false);
				lbOtra_muestra.setVisible(false);
				tbxOtra_muestra.setVisible(false);
				
			}
			Utilidades.seleccionarRadio(rdbCultivo, ficha_epidemiologia_n13.getCultivo());
			Utilidades.seleccionarRadio(rdbHistoquimica, ficha_epidemiologia_n13.getHistoquimica());
			Utilidades.seleccionarRadio(rdbPcr, ficha_epidemiologia_n13.getPcr());
			Utilidades.seleccionarRadio(rdbElisa, ficha_epidemiologia_n13.getElisa());
			Utilidades.seleccionarRadio(rdbMicroaglutinacion, ficha_epidemiologia_n13.getMicroaglutinacion());
			Utilidades.seleccionarRadio(rdbPareadas, ficha_epidemiologia_n13.getPareadas());
			dtbxFecha_muestra1.setValue(ficha_epidemiologia_n13.getFecha_muestra1());
			dtbxFecha_muestra2.setValue(ficha_epidemiologia_n13.getFecha_muestra2());
			Utilidades.seleccionarRadio(rdbIdentificacion_serogrupos, ficha_epidemiologia_n13.getIdentificacion_serogrupos());
			
			if( ficha_epidemiologia_n13.getIdentificacion_serogrupos().equals("O")){
				rowSerogrupo.setVisible(true);
				lOtro_serogrupo.setVisible(true);
				tbxOtro_serogrupo.setVisible(true);
				tbxOtro_serogrupo.setValue(ficha_epidemiologia_n13.getOtro_serogrupo());
				
			}else{
				rowSerogrupo.setVisible(false);
				lOtro_serogrupo.setVisible(false);
				tbxOtro_serogrupo.setVisible(false);
				
			}
			
			tbxTitulo_muestra1.setValue(ficha_epidemiologia_n13.getTitulo_muestra1());
			tbxTitulo_muestra2.setValue(ficha_epidemiologia_n13.getTitulo_muestra2());
			Utilidades.seleccionarRadio(rdbTratamiento, ficha_epidemiologia_n13.getTratamiento());
			tbxCual_tratamiento.setValue(ficha_epidemiologia_n13.getCual_tratamiento());
			tbxTratamiento_antibiotico.setValue(ficha_epidemiologia_n13.getTratamiento_antibiotico());
			ibxDosis.setValue((ficha_epidemiologia_n13.getDosis() != null && !ficha_epidemiologia_n13.getDosis().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n13.getDosis()) : null);
			
			ibxTiempo.setValue((ficha_epidemiologia_n13.getTiempo() != null && !ficha_epidemiologia_n13.getTiempo().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n13.getTiempo()) : null);
			tbxCodigo_medico.setValue(ficha_epidemiologia_n13.getCodigo_medico());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n13 ficha_epidemiologia_n13 = (Ficha_epidemiologia_n13)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n13);
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
			String valor = "O";
			FormularioUtil.mostrarComponentes_conRadiogroup(form, radiogroup, valor, abstractComponents);
			
		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}
	
	public void mostrar_conCheck(ZKWindow form,Checkbox checkbox,AbstractComponent[] abstractComponents) {
		try {
			
			FormularioUtil.mostrarComponentes_conCheckbox(form, checkbox, abstractComponents);

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
	
	public void deshabilitar_conCheck(Checkbox checkbox,AbstractComponent[] abstractComponents) {
		try {
			
			FormularioUtil.deshabilitarComponentes_conCheckbox(checkbox, abstractComponents);

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
	public Ficha_epidemiologia_n13 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n13> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n13.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n13 ficha_n13 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n13;
				}else{

					return null;
				}
	}
	
}