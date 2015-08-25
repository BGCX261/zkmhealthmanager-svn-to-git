/*
 * ficha_epidemiologia_n7Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n7;
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
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
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

public class Ficha_epidemiologia_n7Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n7>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n7Action.class);
	
	
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
	@View private Radiogroup rdbEmbarazada;
	@View private Radiogroup rdbControl_prenatal_emb;
	@View private Intbox ibxEdad_gestacional_cnp;
	@View private Intbox ibxSemana_gestacional;
	@View private Radiogroup rdbDonante_sangre;
	@View private Checkbox chbHijo_de_madre_hbsag;
	@View private Checkbox chbCompaniero_sexual_inestable;
	@View private Checkbox chbHemofilico;
	@View private Checkbox chbMultitranfundido;
	@View private Checkbox chbHemodializado;
	@View private Checkbox chbTrabajador_de_salud;
	@View private Checkbox chbDrogadicto_parental;
	@View private Checkbox chbConviviente_portador_hbsag;
	@View private Checkbox chbContacto_sexual_paciente;
	@View private Checkbox chbPoblacion_cautiva;
	@View private Radiogroup rdbModo_de_transmisio;
	@View private Radiogroup rdbOtras_its;
	@View private Radiogroup rdbVacunacion_previa;
	@View private Intbox ibxNumero_de_dosis;
	@View private Datebox dtbxFecha_tercera_dosis;
	@View private Radiogroup rdbFuente_recibio_informacion;
	@View private Checkbox chbNausea;
	@View private Checkbox chbVomito;
	@View private Checkbox chbFiebre;
	@View private Checkbox chbHiporexia;
	@View private Checkbox chbAstenia;
	@View private Checkbox chbMialgia;
	@View private Checkbox chbArtralgia;
	@View private Checkbox chbColuria;
	@View private Checkbox chbIctericia;
	@View private Checkbox chbHepatoesplenomegalila;
	@View private Radiogroup rdbTratamiento;
	@View private Textbox tbxCual1;
	@View private Radiogroup rdbComplicacion;
	@View private Textbox tbxCual2;
	@View private Radiogroup rdbCondicion_final;
	@View private Radiogroup rdbDiagnostico_contactos;
	@View private Radiogroup rdbSangre;
	@View private Datebox dtbxFecha_de_toma1;
	@View private Checkbox chbHbsag;
	@View private Checkbox chbAnti_hbc_igm;
	@View private Checkbox chbAntivhd;
	@View private Checkbox chbOtro_anti_hbc_igm;
	@View private Radiogroup rdbResutado1;
	@View private Radiogroup rdbResultado2;
	@View private Radiogroup rdbResultado3;
	@View private Radiogroup rdbResultado4;
	@View private Datebox dtbxFecha_de_resultado;
	@View private Radiogroup rdbTejidos;
	@View private Datebox dtbxFecha_de_toma2;
	@View private Checkbox chbPatologia;
	@View private Datebox dtbxFecha_del_resultado;
	@View private Textbox tbxNombres_y_apellidos_madre;
	@View private Listbox lbxTipo_identidad_madre;
	@View private Textbox tbxIdentificacion_madre;
	@View private Radiogroup rdbAplicacion_vacuna_anti;
	@View private Radiogroup rdbAplicacion_de_gamaglobulina;
	@View private Radiogroup rdbRemision_pediatra;
	@View private Textbox tbxObservaciones_seguimientos;
	@View private Toolbarbutton btGuardar;
	private final String[] idsExcluyentes = {};
    private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;

	@View private Toolbarbutton btImprimir;
	@View private North north_ficha;
	
	@Override
	public void init() throws Exception{
		listarCombos();		
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n7 ficha = new Ficha_epidemiologia_n7();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n7) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 7-------> "+ficha);
			
			mostrarDatos(ficha);

			north_ficha.setVisible(true);
			btImprimir.setVisible(true);
			
		}else {

			north_ficha.setVisible(false);
			btImprimir.setVisible(false);
			
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
		Utilidades.listarElementoListbox(lbxTipo_identidad_madre,true,getServiceLocator());
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("codigo_diagnostico");
		listitem.setLabel("Codigo_diagnostico");
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
		
		dtbxFecha_inicial.setStyle("background-color:white");
		tbxIdentificacion.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(dtbxFecha_inicial.getText().trim().equals("")){
			dtbxFecha_inicial.setStyle("background-color:#F6BBBE");
			valida = false;
		}
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
			
			List<Ficha_epidemiologia_n7> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n7.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n7 ficha_epidemiologia_n7 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n7, this));
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
		
		final Ficha_epidemiologia_n7 ficha_epidemiologia_n7 = (Ficha_epidemiologia_n7)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n7.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n7.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n7.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n7);
			}
		});
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n7 obtenerFichaEpidemiologia(){
						
				Ficha_epidemiologia_n7 ficha_epidemiologia_n7 = new Ficha_epidemiologia_n7();
				ficha_epidemiologia_n7.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n7.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n7.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n7.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n7.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n7.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n7.setEmbarazada(rdbEmbarazada.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setControl_prenatal_emb(rdbControl_prenatal_emb.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setEdad_gestacional_cnp((ibxEdad_gestacional_cnp.getValue()!=null?ibxEdad_gestacional_cnp.getValue():0));
				ficha_epidemiologia_n7.setSemana_gestacional((ibxSemana_gestacional.getValue()!=null?ibxSemana_gestacional.getValue():0));
				ficha_epidemiologia_n7.setDonante_sangre(rdbDonante_sangre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setHijo_de_madre_hbsag(chbHijo_de_madre_hbsag.isChecked());
				ficha_epidemiologia_n7.setCompaniero_sexual_inestable(chbCompaniero_sexual_inestable.isChecked());
				ficha_epidemiologia_n7.setHemofilico(chbHemofilico.isChecked());
				ficha_epidemiologia_n7.setMultitranfundido(chbMultitranfundido.isChecked());
				ficha_epidemiologia_n7.setHemodializado(chbHemodializado.isChecked());
				ficha_epidemiologia_n7.setTrabajador_de_salud(chbTrabajador_de_salud.isChecked());
				ficha_epidemiologia_n7.setDrogadicto_parental(chbDrogadicto_parental.isChecked());
				ficha_epidemiologia_n7.setConviviente_portador_hbsag(chbConviviente_portador_hbsag.isChecked());
				ficha_epidemiologia_n7.setContacto_sexual_paciente(chbContacto_sexual_paciente.isChecked());
				ficha_epidemiologia_n7.setPoblacion_cautiva(chbPoblacion_cautiva.isChecked());
				ficha_epidemiologia_n7.setModo_de_transmisio(rdbModo_de_transmisio.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setOtras_its(rdbOtras_its.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setVacunacion_previa(rdbVacunacion_previa.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setNumero_de_dosis((ibxNumero_de_dosis.getValue()!=null?ibxNumero_de_dosis.getValue():0));
				ficha_epidemiologia_n7.setFecha_tercera_dosis(new Timestamp(dtbxFecha_tercera_dosis.getValue().getTime()));
				ficha_epidemiologia_n7.setFuente_recibio_informacion(rdbFuente_recibio_informacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setNausea(chbNausea.isChecked());
				ficha_epidemiologia_n7.setVomito(chbVomito.isChecked());
				ficha_epidemiologia_n7.setFiebre(chbFiebre.isChecked());
				ficha_epidemiologia_n7.setHiporexia(chbHiporexia.isChecked());
				ficha_epidemiologia_n7.setAstenia(chbAstenia.isChecked());
				ficha_epidemiologia_n7.setMialgia(chbMialgia.isChecked());
				ficha_epidemiologia_n7.setArtralgia(chbArtralgia.isChecked());
				ficha_epidemiologia_n7.setColuria(chbColuria.isChecked());
				ficha_epidemiologia_n7.setIctericia(chbIctericia.isChecked());
				ficha_epidemiologia_n7.setHepatoesplenomegalila(chbHepatoesplenomegalila.isChecked());
				ficha_epidemiologia_n7.setTratamiento(rdbTratamiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setCual1(tbxCual1.getValue());
				ficha_epidemiologia_n7.setComplicacion(rdbComplicacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setCual2(tbxCual2.getValue());
				ficha_epidemiologia_n7.setCondicion_final(rdbCondicion_final.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setDiagnostico_contactos(rdbDiagnostico_contactos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setSangre(rdbSangre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setFecha_de_toma1(new Timestamp(dtbxFecha_de_toma1.getValue().getTime()));
				ficha_epidemiologia_n7.setHbsag(chbHbsag.isChecked());
				ficha_epidemiologia_n7.setAnti_hbc_igm(chbAnti_hbc_igm.isChecked());
				ficha_epidemiologia_n7.setAntivhd(chbAntivhd.isChecked());
				ficha_epidemiologia_n7.setOtro_anti_hbc_igm(chbOtro_anti_hbc_igm.isChecked());
				ficha_epidemiologia_n7.setResutado1(rdbResutado1.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setResultado2(rdbResultado2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setResultado3(rdbResultado3.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setResultado4(rdbResultado4.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setFecha_de_resultado(new Timestamp(dtbxFecha_de_resultado.getValue().getTime()));
				ficha_epidemiologia_n7.setTejidos(rdbTejidos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setFecha_de_toma2(new Timestamp(dtbxFecha_de_toma2.getValue().getTime()));
				ficha_epidemiologia_n7.setPatologia(chbPatologia.isChecked());
				ficha_epidemiologia_n7.setFecha_del_resultado(new Timestamp(dtbxFecha_del_resultado.getValue().getTime()));
				ficha_epidemiologia_n7.setNombres_y_apellidos_madre(tbxNombres_y_apellidos_madre.getValue());
				ficha_epidemiologia_n7.setTipo_identidad_madre(lbxTipo_identidad_madre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setIdentificacion_madre(tbxIdentificacion_madre.getValue());
				ficha_epidemiologia_n7.setAplicacion_vacuna_anti(rdbAplicacion_vacuna_anti.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setAplicacion_de_gamaglobulina(rdbAplicacion_de_gamaglobulina.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setRemision_pediatra(rdbRemision_pediatra.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n7.setObservaciones_seguimientos(tbxObservaciones_seguimientos.getValue());
				ficha_epidemiologia_n7.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n7.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n7.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n7.setDelete_date(null);
				ficha_epidemiologia_n7.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n7.setDelete_user(null);
				
				return ficha_epidemiologia_n7;		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n7 obj)throws Exception{
		Ficha_epidemiologia_n7 ficha_epidemiologia_n7 = (Ficha_epidemiologia_n7)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n7.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n7.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n7.getFecha_inicial());
			tbxIdentificacion.setValue(ficha_epidemiologia_n7.getIdentificacion());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbEmbarazada, ficha_epidemiologia_n7.getEmbarazada());
			Utilidades.seleccionarRadio(rdbControl_prenatal_emb, ficha_epidemiologia_n7.getControl_prenatal_emb());
			ibxEdad_gestacional_cnp.setValue(ficha_epidemiologia_n7.getEdad_gestacional_cnp());
			ibxSemana_gestacional.setValue(ficha_epidemiologia_n7.getSemana_gestacional());
			Utilidades.seleccionarRadio(rdbDonante_sangre, ficha_epidemiologia_n7.getDonante_sangre());
			chbHijo_de_madre_hbsag.setChecked(ficha_epidemiologia_n7.getHijo_de_madre_hbsag());
			chbCompaniero_sexual_inestable.setChecked(ficha_epidemiologia_n7.getCompaniero_sexual_inestable());
			chbHemofilico.setChecked(ficha_epidemiologia_n7.getHemofilico());
			chbMultitranfundido.setChecked(ficha_epidemiologia_n7.getMultitranfundido());
			chbHemodializado.setChecked(ficha_epidemiologia_n7.getHemodializado());
			chbTrabajador_de_salud.setChecked(ficha_epidemiologia_n7.getTrabajador_de_salud());
			chbDrogadicto_parental.setChecked(ficha_epidemiologia_n7.getDrogadicto_parental());
			chbConviviente_portador_hbsag.setChecked(ficha_epidemiologia_n7.getConviviente_portador_hbsag());
			chbContacto_sexual_paciente.setChecked(ficha_epidemiologia_n7.getContacto_sexual_paciente());
			chbPoblacion_cautiva.setChecked(ficha_epidemiologia_n7.getPoblacion_cautiva());
			Utilidades.seleccionarRadio(rdbModo_de_transmisio, ficha_epidemiologia_n7.getModo_de_transmisio());
			Utilidades.seleccionarRadio(rdbOtras_its, ficha_epidemiologia_n7.getOtras_its());
			Utilidades.seleccionarRadio(rdbVacunacion_previa, ficha_epidemiologia_n7.getVacunacion_previa());
			ibxNumero_de_dosis.setValue(ficha_epidemiologia_n7.getNumero_de_dosis());
			dtbxFecha_tercera_dosis.setValue(ficha_epidemiologia_n7.getFecha_tercera_dosis());
			Utilidades.seleccionarRadio(rdbFuente_recibio_informacion, ficha_epidemiologia_n7.getFuente_recibio_informacion());
			chbNausea.setChecked(ficha_epidemiologia_n7.getNausea());
			chbVomito.setChecked(ficha_epidemiologia_n7.getVomito());
			chbFiebre.setChecked(ficha_epidemiologia_n7.getFiebre());
			chbHiporexia.setChecked(ficha_epidemiologia_n7.getHiporexia());
			chbAstenia.setChecked(ficha_epidemiologia_n7.getAstenia());
			chbMialgia.setChecked(ficha_epidemiologia_n7.getMialgia());
			chbArtralgia.setChecked(ficha_epidemiologia_n7.getArtralgia());
			chbColuria.setChecked(ficha_epidemiologia_n7.getColuria());
			chbIctericia.setChecked(ficha_epidemiologia_n7.getIctericia());
			chbHepatoesplenomegalila.setChecked(ficha_epidemiologia_n7.getHepatoesplenomegalila());
			Utilidades.seleccionarRadio(rdbTratamiento, ficha_epidemiologia_n7.getTratamiento());
			tbxCual1.setValue(ficha_epidemiologia_n7.getCual1());
			Utilidades.seleccionarRadio(rdbComplicacion, ficha_epidemiologia_n7.getComplicacion());
			tbxCual2.setValue(ficha_epidemiologia_n7.getCual2());
			Utilidades.seleccionarRadio(rdbCondicion_final, ficha_epidemiologia_n7.getCondicion_final());
			Utilidades.seleccionarRadio(rdbDiagnostico_contactos, ficha_epidemiologia_n7.getDiagnostico_contactos());
			Utilidades.seleccionarRadio(rdbSangre, ficha_epidemiologia_n7.getSangre());
			dtbxFecha_de_toma1.setValue(ficha_epidemiologia_n7.getFecha_de_toma1());
			chbHbsag.setChecked(ficha_epidemiologia_n7.getHbsag());
			chbAnti_hbc_igm.setChecked(ficha_epidemiologia_n7.getAnti_hbc_igm());
			chbAntivhd.setChecked(ficha_epidemiologia_n7.getAntivhd());
			chbOtro_anti_hbc_igm.setChecked(ficha_epidemiologia_n7.getOtro_anti_hbc_igm());
			Utilidades.seleccionarRadio(rdbResutado1, ficha_epidemiologia_n7.getResutado1());
			Utilidades.seleccionarRadio(rdbResultado2, ficha_epidemiologia_n7.getResultado2());
			Utilidades.seleccionarRadio(rdbResultado3, ficha_epidemiologia_n7.getResultado3());
			Utilidades.seleccionarRadio(rdbResultado4, ficha_epidemiologia_n7.getResultado4());
			dtbxFecha_de_resultado.setValue(ficha_epidemiologia_n7.getFecha_de_resultado());
			Utilidades.seleccionarRadio(rdbTejidos, ficha_epidemiologia_n7.getTejidos());
			dtbxFecha_de_toma2.setValue(ficha_epidemiologia_n7.getFecha_de_toma2());
			chbPatologia.setChecked(ficha_epidemiologia_n7.getPatologia());
			dtbxFecha_del_resultado.setValue(ficha_epidemiologia_n7.getFecha_del_resultado());
			tbxNombres_y_apellidos_madre.setValue(ficha_epidemiologia_n7.getNombres_y_apellidos_madre());
			for(int i=0;i<lbxTipo_identidad_madre.getItemCount();i++){
				Listitem listitem = lbxTipo_identidad_madre.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(ficha_epidemiologia_n7.getTipo_identidad_madre())){
					listitem.setSelected(true);
					i = lbxTipo_identidad_madre.getItemCount();
				}
			}
			tbxIdentificacion_madre.setValue(ficha_epidemiologia_n7.getIdentificacion_madre());
			Utilidades.seleccionarRadio(rdbAplicacion_vacuna_anti, ficha_epidemiologia_n7.getAplicacion_vacuna_anti());
			Utilidades.seleccionarRadio(rdbAplicacion_de_gamaglobulina, ficha_epidemiologia_n7.getAplicacion_de_gamaglobulina());
			Utilidades.seleccionarRadio(rdbRemision_pediatra, ficha_epidemiologia_n7.getRemision_pediatra());
			tbxObservaciones_seguimientos.setValue(ficha_epidemiologia_n7.getObservaciones_seguimientos());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n7 ficha_epidemiologia_n7 = (Ficha_epidemiologia_n7)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n7);
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
	public Ficha_epidemiologia_n7 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n7> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n7.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n7 ficha_n7 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n7;
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
		paramRequest.put("ficha_epidemiologia", IFichas_epidemiologicas.HEPATITIS_B);

		//log.info("codigo_ficha"+tbxCodigo_ficha.getValue());
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
	
}