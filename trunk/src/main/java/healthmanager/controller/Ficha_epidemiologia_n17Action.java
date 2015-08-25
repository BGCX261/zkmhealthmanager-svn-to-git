/*
 * ficha_epidemiologia_n17Action.java
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
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.bean.Ficha_epidemiologia_n17;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
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
import healthmanager.modelo.service.GeneralExtraService;

public class Ficha_epidemiologia_n17Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n17>{

	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n17Action.class);
	
	
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
	@View private Textbox tbxNombre_madre;
	@View private Textbox tbxNombre_padre;
	@View private Datebox dtbxFecha_investigacion;
	@View private Intbox ibxDosis_recibida;
	@View private Datebox dtbxFecha_ultima_dosis;
	@View private Radiogroup rdbCarne;
	@View private Radiogroup rdbFiebre;
	@View private Radiogroup rdbRespiratorios;
	@View private Radiogroup rdbDigestivos;
	@View private Radiogroup rdbDolor_muscular;
	@View private Radiogroup rdbSignos_meningeos;
	@View private Radiogroup rdbFiebre_inicio_paralisis;
	@View private Intbox ibxInstalacion;
	@View private Radiogroup rdbProgresion;
	@View private Datebox dtbxFecha_inicio_paralisis;
	@View private Radiogroup rdbParesia_sup_der;
	@View private Radiogroup rdbParalisis_sup_der;
	@View private Radiogroup rdbFlacida_sup_der;
	@View private Radiogroup rdbLocalizacion_sup_der;
	@View private Radiogroup rdbSencibilidad_sup_der;
	@View private Radiogroup rdbRot_sup_der;
	@View private Radiogroup rdbParesia_sup_izq;
	@View private Radiogroup rdbParalisis_sup_izq;
	@View private Radiogroup rdbFlacida_sup_izq;
	@View private Radiogroup rdbLocalizacion_sup_izq;
	@View private Radiogroup rdbSencibilidad_sup_izq;
	@View private Radiogroup rdbRot_sup_izq;
	@View private Radiogroup rdbParesia_inf_der;
	@View private Radiogroup rdbParalisis_inf_der;
	@View private Radiogroup rdbFlacida_inf_der;
	@View private Radiogroup rdbLocalizacion_inf_der;
	@View private Radiogroup rdbSencibilidad_inf_der;
	@View private Radiogroup rdbRot_inf_der;
	@View private Radiogroup rdbParesia_inf_izq;
	@View private Radiogroup rdbParalisis_inf_izq;
	@View private Radiogroup rdbFlacida_inf_izq;
	@View private Radiogroup rdbLocalizacion_inf_izq;
	@View private Radiogroup rdbSencibilidad_inf_izq;
	@View private Radiogroup rdbRot_inf_izq;
	@View private Radiogroup rdbMusculos_respiratorios;
	@View private Radiogroup rdbOtros_signos_meningeos;
	@View private Radiogroup rdbBabinsky;
	@View private Radiogroup rdbBrudzinsky;
	@View private Radiogroup rdbPares_craneanos;
	@View private Radiogroup rdbLiquido_cefalorraquideo;
	@View private Radiogroup rdbElectromiogragia;
	@View private Radiogroup rdbVelocidad_conduccion;
	@View private Textbox tbxImpresion_diagnostica;
	@View private Bandbox tbxImpresion_inicial;
	@View private Radiogroup rdbToma_muestra;
	@View private Datebox dtbxFecha_toma;
	@View private Datebox dtbxFecha_envio;
	@View private Datebox dtbxFecha_recepcion;
	@View private Datebox dtbxFecha_resultado;
	@View private Radiogroup rdbResultado;
	@View private Intbox ibxTotal_poblacion_menor;
	@View private Intbox ibxTotal_poblacion_1_4;
	@View private Intbox ibxTotal_poblacion_5_9;
	@View private Intbox ibxTotal_poblacion_10_14;
	@View private Intbox ibxTotal_poblacion;
	@View private Intbox ibxBac_menor;
	@View private Intbox ibxBac_1_4;
	@View private Intbox ibxBac_5_9;
	@View private Intbox ibxBac_10_14;
	@View private Intbox ibxTotal_bac;
	@View private Intbox ibxRecien;
	@View private Intbox ibxVop1_menor;
	@View private Intbox ibxVop1_1_4;
	@View private Intbox ibxVop1_5_9;
	@View private Intbox ibxTotal_vop1;
	@View private Intbox ibxVop2_menor;
	@View private Intbox ibxVop2_1_4;
	@View private Intbox ibxVop2_5_9;
	@View private Intbox ibxTotal_vop2;
	@View private Intbox ibxVop3_menor;
	@View private Intbox ibxVop3_1_4;
	@View private Intbox ibxVop3_5_9;
	@View private Intbox ibxTotal_vop3;
	@View private Intbox ibxAdicional_menor;
	@View private Intbox ibxAdicional_1_4;
	@View private Intbox ibxAdicional_5_9;
	@View private Intbox ibxTotal_adicional;
	@View private Datebox dtbxFecha_vacunacion_bloqueo;
	@View private Datebox dtbxFecha_culminacion_bloqueo;
	@View private Intbox ibxViviendas_zona;
	@View private Intbox ibxViviendas_visitadas;
	@View private Radiogroup rdbCaso_detectado;
	@View private Datebox dtbxFecha_seguimiento;
	@View private Radiogroup rdbParalisis_residual;
	@View private Radiogroup rdbAtrofia;
	@View private Radiogroup rdbClasificacion_final;
	@View private Datebox dtbxFecha_clasificacion;
	@View private Radiogroup rdbCriterios_clasificacion;
	@View private Textbox tbxImpresion_diagnostica2;
	@View private Bandbox tbxImpresion_final;
	@View private Textbox tbxCodigo_medico;
	private final String[] idsExcluyentes = {};

	@View private Toolbarbutton btGuardar;

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
				Ficha_epidemiologia_n17 ficha = new Ficha_epidemiologia_n17();
				ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
				ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
				ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
				ficha = (Ficha_epidemiologia_n17) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
				
				//log.info("consultar ficha 17-------> "+ficha);
				
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
				dtbxFecha_clasificacion.setValue(null);
				dtbxFecha_culminacion_bloqueo.setValue(null);
				dtbxFecha_envio.setValue(null);
				dtbxFecha_inicio_paralisis.setValue(null);
				dtbxFecha_investigacion.setValue(null);
				dtbxFecha_recepcion.setValue(null);
				dtbxFecha_resultado.setValue(null);
				dtbxFecha_seguimiento.setValue(null);
				dtbxFecha_toma.setValue(null);
				dtbxFecha_ultima_dosis.setValue(null);
				dtbxFecha_vacunacion_bloqueo.setValue(null);
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
			
			List<Ficha_epidemiologia_n17> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n17.class, parameters);
			
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n17 ficha_epidemiologia_n17 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n17, this));
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
		
		final Ficha_epidemiologia_n17 ficha_epidemiologia_n17 = (Ficha_epidemiologia_n17)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n17.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n17.getIdentificacion()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n17.getFecha_creacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n17);
			}
		});
		hbox.appendChild(img);
		
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n17 obtenerFichaEpidemiologia() {
		
				Ficha_epidemiologia_n17 ficha_epidemiologia_n17 = new Ficha_epidemiologia_n17();
				ficha_epidemiologia_n17.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n17.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n17.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n17.setIdentificacion(tbxIdentificacion.getValue());
				ficha_epidemiologia_n17.setFecha_creacion(new Timestamp(dtbxFecha_creacion.getValue().getTime()));
				ficha_epidemiologia_n17.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n17.setNombre_madre(tbxNombre_madre.getValue());
				ficha_epidemiologia_n17.setNombre_padre(tbxNombre_padre.getValue());
				
				if (dtbxFecha_investigacion.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_investigacion(new Timestamp(dtbxFecha_investigacion.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_investigacion(null);
				}
				
				ficha_epidemiologia_n17.setDosis_recibida(ibxDosis_recibida.getValue()!=null?ibxDosis_recibida.getValue() + "" : "");
				
				if (dtbxFecha_ultima_dosis.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_ultima_dosis(new Timestamp(dtbxFecha_ultima_dosis.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_ultima_dosis(null);
				}
				
				ficha_epidemiologia_n17.setCarne(rdbCarne.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setFiebre(rdbFiebre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setRespiratorios(rdbRespiratorios.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setDigestivos(rdbDigestivos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setDolor_muscular(rdbDolor_muscular.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setSignos_meningeos(rdbSignos_meningeos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setFiebre_inicio_paralisis(rdbFiebre_inicio_paralisis.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setInstalacion((ibxInstalacion.getValue()!=null?ibxInstalacion.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setProgresion(rdbProgresion.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_inicio_paralisis.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_inicio_paralisis(new Timestamp(dtbxFecha_inicio_paralisis.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_inicio_paralisis(null);
				}
				
				ficha_epidemiologia_n17.setParesia_sup_der(rdbParesia_sup_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setParalisis_sup_der(rdbParalisis_sup_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setFlacida_sup_der(rdbFlacida_sup_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setLocalizacion_sup_der(rdbLocalizacion_sup_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setSencibilidad_sup_der(rdbSencibilidad_sup_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setRot_sup_der(rdbRot_sup_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setParesia_sup_izq(rdbParesia_sup_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setParalisis_sup_izq(rdbParalisis_sup_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setFlacida_sup_izq(rdbFlacida_sup_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setLocalizacion_sup_izq(rdbLocalizacion_sup_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setSencibilidad_sup_izq(rdbSencibilidad_sup_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setRot_sup_izq(rdbRot_sup_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setParesia_inf_der(rdbParesia_inf_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setParalisis_inf_der(rdbParalisis_inf_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setFlacida_inf_der(rdbFlacida_inf_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setLocalizacion_inf_der(rdbLocalizacion_inf_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setSencibilidad_inf_der(rdbSencibilidad_inf_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setRot_inf_der(rdbRot_inf_der.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setParesia_inf_izq(rdbParesia_inf_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setParalisis_inf_izq(rdbParalisis_inf_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setFlacida_inf_izq(rdbFlacida_inf_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setLocalizacion_inf_izq(rdbLocalizacion_inf_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setSencibilidad_inf_izq(rdbSencibilidad_inf_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setRot_inf_izq(rdbRot_inf_izq.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setMusculos_respiratorios(rdbMusculos_respiratorios.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setOtros_signos_meningeos(rdbOtros_signos_meningeos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setBabinsky(rdbBabinsky.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setBrudzinsky(rdbBrudzinsky.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setPares_craneanos(rdbPares_craneanos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setLiquido_cefalorraquideo(rdbLiquido_cefalorraquideo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setElectromiogragia(rdbElectromiogragia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setVelocidad_conduccion(rdbVelocidad_conduccion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setImpresion_diagnostica(tbxImpresion_diagnostica.getValue());
				ficha_epidemiologia_n17.setImpresion_inicial(tbxImpresion_inicial.getValue());
				ficha_epidemiologia_n17.setToma_muestra(rdbToma_muestra.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_toma.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_toma(new Timestamp(dtbxFecha_toma.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_toma(null);
				}
				
				if (dtbxFecha_envio.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_envio(new Timestamp(dtbxFecha_envio.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_envio(null);
				}
				
				if (dtbxFecha_recepcion.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_recepcion(new Timestamp(dtbxFecha_recepcion.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_recepcion(null);
				}
				
				if (dtbxFecha_resultado.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_resultado(new Timestamp(dtbxFecha_resultado.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_resultado(null);
				}
				
				ficha_epidemiologia_n17.setResultado(rdbResultado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setTotal_poblacion_menor((ibxTotal_poblacion_menor.getValue()!=null?ibxTotal_poblacion_menor.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setTotal_poblacion_1_4((ibxTotal_poblacion_1_4.getValue()!=null?ibxTotal_poblacion_1_4.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setTotal_poblacion_5_9((ibxTotal_poblacion_5_9.getValue()!=null?ibxTotal_poblacion_5_9.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setTotal_poblacion_10_14((ibxTotal_poblacion_10_14.getValue()!=null?ibxTotal_poblacion_10_14.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setTotal_poblacion((ibxTotal_poblacion.getValue()!=null?ibxTotal_poblacion.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setBac_menor((ibxBac_menor.getValue()!=null?ibxBac_menor.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setBac_1_4((ibxBac_1_4.getValue()!=null?ibxBac_1_4.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setBac_5_9((ibxBac_5_9.getValue()!=null?ibxBac_5_9.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setBac_10_14((ibxBac_10_14.getValue()!=null?ibxBac_10_14.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setTotal_bac((ibxTotal_bac.getValue()!=null?ibxTotal_bac.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setRecien((ibxRecien.getValue()!=null?ibxRecien.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setVop1_menor((ibxVop1_menor.getValue()!=null?ibxVop1_menor.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setVop1_1_4((ibxVop1_1_4.getValue()!=null?ibxVop1_1_4.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setVop1_5_9((ibxVop1_5_9.getValue()!=null?ibxVop1_5_9.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setTotal_vop1((ibxTotal_vop1.getValue()!=null?ibxTotal_vop1.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setVop2_menor((ibxVop2_menor.getValue()!=null?ibxVop2_menor.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setVop2_1_4((ibxVop2_1_4.getValue()!=null?ibxVop2_1_4.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setVop2_5_9((ibxVop2_5_9.getValue()!=null?ibxVop2_5_9.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setTotal_vop2((ibxTotal_vop2.getValue()!=null?ibxTotal_vop2.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setVop3_menor((ibxVop3_menor.getValue()!=null?ibxVop3_menor.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setVop3_1_4((ibxVop3_1_4.getValue()!=null?ibxVop3_1_4.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setVop3_5_9((ibxVop3_5_9.getValue()!=null?ibxVop3_5_9.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setTotal_vop3((ibxTotal_vop3.getValue()!=null?ibxTotal_vop3.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setAdicional_menor((ibxAdicional_menor.getValue()!=null?ibxAdicional_menor.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setAdicional_1_4((ibxAdicional_1_4.getValue()!=null?ibxAdicional_1_4.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setAdicional_5_9((ibxAdicional_5_9.getValue()!=null?ibxAdicional_5_9.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setTotal_adicional((ibxTotal_adicional.getValue()!=null?ibxTotal_adicional.getValue()+ "" : ""));
				
				if (dtbxFecha_vacunacion_bloqueo.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_vacunacion_bloqueo(new Timestamp(dtbxFecha_vacunacion_bloqueo.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_vacunacion_bloqueo(null);
				}
				
				
				if (dtbxFecha_culminacion_bloqueo.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_culminacion_bloqueo(new Timestamp(dtbxFecha_culminacion_bloqueo.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_culminacion_bloqueo(null);
				}
				
				ficha_epidemiologia_n17.setViviendas_zona((ibxViviendas_zona.getValue()!=null?ibxViviendas_zona.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setViviendas_visitadas((ibxViviendas_visitadas.getValue()!=null?ibxViviendas_visitadas.getValue()+ "" : ""));
				ficha_epidemiologia_n17.setCaso_detectado(rdbCaso_detectado.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_seguimiento.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_seguimiento(new Timestamp(dtbxFecha_seguimiento.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_seguimiento(null);
				}
				
				ficha_epidemiologia_n17.setParalisis_residual(rdbParalisis_residual.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setAtrofia(rdbAtrofia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setClasificacion_final(rdbClasificacion_final.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_clasificacion.getValue() != null) {
					ficha_epidemiologia_n17.setFecha_clasificacion(new Timestamp(dtbxFecha_clasificacion.getValue().getTime()));
						
				} else {
					ficha_epidemiologia_n17.setFecha_clasificacion(null);
				}
				
				ficha_epidemiologia_n17.setCriterios_clasificacion(rdbCriterios_clasificacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n17.setImpresion_diagnostica2(tbxImpresion_diagnostica2.getValue());
				ficha_epidemiologia_n17.setImpresion_final(tbxImpresion_final.getValue());
				ficha_epidemiologia_n17.setCodigo_medico(tbxCodigo_medico.getValue());
				ficha_epidemiologia_n17.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n17.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n17.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n17.setDelete_date(null);
				ficha_epidemiologia_n17.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n17.setDelete_user(null);
				
				return ficha_epidemiologia_n17;
			
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n17 obj)throws Exception{
		Ficha_epidemiologia_n17 ficha_epidemiologia_n17 = (Ficha_epidemiologia_n17)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n17.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n17.getIdentificacion());

			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			dtbxFecha_creacion.setValue(ficha_epidemiologia_n17.getFecha_creacion());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n17.getCodigo_diagnostico());
			tbxNombre_madre.setValue(ficha_epidemiologia_n17.getNombre_madre());
			tbxNombre_padre.setValue(ficha_epidemiologia_n17.getNombre_padre());
			dtbxFecha_investigacion.setValue(ficha_epidemiologia_n17.getFecha_investigacion());
			ibxDosis_recibida.setValue((ficha_epidemiologia_n17.getDosis_recibida() != null && !ficha_epidemiologia_n17.getDosis_recibida().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getDosis_recibida()) : null);
			dtbxFecha_ultima_dosis.setValue(ficha_epidemiologia_n17.getFecha_ultima_dosis());
			Utilidades.seleccionarRadio(rdbCarne, ficha_epidemiologia_n17.getCarne());
			Utilidades.seleccionarRadio(rdbFiebre, ficha_epidemiologia_n17.getFiebre());
			Utilidades.seleccionarRadio(rdbRespiratorios, ficha_epidemiologia_n17.getRespiratorios());
			Utilidades.seleccionarRadio(rdbDigestivos, ficha_epidemiologia_n17.getDigestivos());
			Utilidades.seleccionarRadio(rdbDolor_muscular, ficha_epidemiologia_n17.getDolor_muscular());
			Utilidades.seleccionarRadio(rdbSignos_meningeos, ficha_epidemiologia_n17.getSignos_meningeos());
			Utilidades.seleccionarRadio(rdbFiebre_inicio_paralisis, ficha_epidemiologia_n17.getFiebre_inicio_paralisis());
			ibxInstalacion.setValue((ficha_epidemiologia_n17.getInstalacion() != null && !ficha_epidemiologia_n17.getInstalacion().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getInstalacion()) : null);
			Utilidades.seleccionarRadio(rdbProgresion, ficha_epidemiologia_n17.getProgresion());
			dtbxFecha_inicio_paralisis.setValue(ficha_epidemiologia_n17.getFecha_inicio_paralisis());
			Utilidades.seleccionarRadio(rdbParesia_sup_der, ficha_epidemiologia_n17.getParesia_sup_der());
			Utilidades.seleccionarRadio(rdbParalisis_sup_der, ficha_epidemiologia_n17.getParalisis_sup_der());
			Utilidades.seleccionarRadio(rdbFlacida_sup_der, ficha_epidemiologia_n17.getFlacida_sup_der());
			Utilidades.seleccionarRadio(rdbLocalizacion_sup_der, ficha_epidemiologia_n17.getLocalizacion_sup_der());
			Utilidades.seleccionarRadio(rdbSencibilidad_sup_der, ficha_epidemiologia_n17.getSencibilidad_sup_der());
			Utilidades.seleccionarRadio(rdbRot_sup_der, ficha_epidemiologia_n17.getRot_sup_der());
			Utilidades.seleccionarRadio(rdbParesia_sup_izq, ficha_epidemiologia_n17.getParesia_sup_izq());
			Utilidades.seleccionarRadio(rdbParalisis_sup_izq, ficha_epidemiologia_n17.getParalisis_sup_izq());
			Utilidades.seleccionarRadio(rdbFlacida_sup_izq, ficha_epidemiologia_n17.getFlacida_sup_izq());
			Utilidades.seleccionarRadio(rdbLocalizacion_sup_izq, ficha_epidemiologia_n17.getLocalizacion_sup_izq());
			Utilidades.seleccionarRadio(rdbSencibilidad_sup_izq, ficha_epidemiologia_n17.getSencibilidad_sup_izq());
			Utilidades.seleccionarRadio(rdbRot_sup_izq, ficha_epidemiologia_n17.getRot_sup_izq());
			Utilidades.seleccionarRadio(rdbParesia_inf_der, ficha_epidemiologia_n17.getParesia_inf_der());
			Utilidades.seleccionarRadio(rdbParalisis_inf_der, ficha_epidemiologia_n17.getParalisis_inf_der());
			Utilidades.seleccionarRadio(rdbFlacida_inf_der, ficha_epidemiologia_n17.getFlacida_inf_der());
			Utilidades.seleccionarRadio(rdbLocalizacion_inf_der, ficha_epidemiologia_n17.getLocalizacion_inf_der());
			Utilidades.seleccionarRadio(rdbSencibilidad_inf_der, ficha_epidemiologia_n17.getSencibilidad_inf_der());
			Utilidades.seleccionarRadio(rdbRot_inf_der, ficha_epidemiologia_n17.getRot_inf_der());
			Utilidades.seleccionarRadio(rdbParesia_inf_izq, ficha_epidemiologia_n17.getParesia_inf_izq());
			Utilidades.seleccionarRadio(rdbParalisis_inf_izq, ficha_epidemiologia_n17.getParalisis_inf_izq());
			Utilidades.seleccionarRadio(rdbFlacida_inf_izq, ficha_epidemiologia_n17.getFlacida_inf_izq());
			Utilidades.seleccionarRadio(rdbLocalizacion_inf_izq, ficha_epidemiologia_n17.getLocalizacion_inf_izq());
			Utilidades.seleccionarRadio(rdbSencibilidad_inf_izq, ficha_epidemiologia_n17.getSencibilidad_inf_izq());
			Utilidades.seleccionarRadio(rdbRot_inf_izq, ficha_epidemiologia_n17.getRot_inf_izq());
			Utilidades.seleccionarRadio(rdbMusculos_respiratorios, ficha_epidemiologia_n17.getMusculos_respiratorios());
			Utilidades.seleccionarRadio(rdbOtros_signos_meningeos, ficha_epidemiologia_n17.getOtros_signos_meningeos());
			Utilidades.seleccionarRadio(rdbBabinsky, ficha_epidemiologia_n17.getBabinsky());
			Utilidades.seleccionarRadio(rdbBrudzinsky, ficha_epidemiologia_n17.getBrudzinsky());
			Utilidades.seleccionarRadio(rdbPares_craneanos, ficha_epidemiologia_n17.getPares_craneanos());
			Utilidades.seleccionarRadio(rdbLiquido_cefalorraquideo, ficha_epidemiologia_n17.getLiquido_cefalorraquideo());
			Utilidades.seleccionarRadio(rdbElectromiogragia, ficha_epidemiologia_n17.getElectromiogragia());
			Utilidades.seleccionarRadio(rdbVelocidad_conduccion, ficha_epidemiologia_n17.getVelocidad_conduccion());
			tbxImpresion_diagnostica.setValue(ficha_epidemiologia_n17.getImpresion_diagnostica());
			tbxImpresion_inicial.setValue(ficha_epidemiologia_n17.getImpresion_inicial());
			Utilidades.seleccionarRadio(rdbToma_muestra, ficha_epidemiologia_n17.getToma_muestra());
			dtbxFecha_toma.setValue(ficha_epidemiologia_n17.getFecha_toma());
			dtbxFecha_envio.setValue(ficha_epidemiologia_n17.getFecha_envio());
			dtbxFecha_recepcion.setValue(ficha_epidemiologia_n17.getFecha_recepcion());
			dtbxFecha_resultado.setValue(ficha_epidemiologia_n17.getFecha_resultado());
			Utilidades.seleccionarRadio(rdbResultado, ficha_epidemiologia_n17.getResultado());
			ibxTotal_poblacion_menor.setValue((ficha_epidemiologia_n17.getTotal_poblacion_menor() != null && !ficha_epidemiologia_n17.getTotal_poblacion_menor().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_poblacion_menor()) : null);
			ibxTotal_poblacion_1_4.setValue((ficha_epidemiologia_n17.getTotal_poblacion_1_4() != null && !ficha_epidemiologia_n17.getTotal_poblacion_1_4().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_poblacion_1_4()) : null);
			ibxTotal_poblacion_5_9.setValue((ficha_epidemiologia_n17.getTotal_poblacion_5_9() != null && !ficha_epidemiologia_n17.getTotal_poblacion_5_9().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_poblacion_5_9()) : null);
			ibxTotal_poblacion_10_14.setValue((ficha_epidemiologia_n17.getTotal_poblacion_10_14() != null && !ficha_epidemiologia_n17.getTotal_poblacion_10_14().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_poblacion_10_14()) : null);
			ibxTotal_poblacion.setValue((ficha_epidemiologia_n17.getTotal_poblacion() != null && !ficha_epidemiologia_n17.getTotal_poblacion().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_poblacion()) : null);
			
			ibxBac_menor.setValue((ficha_epidemiologia_n17.getBac_menor() != null && !ficha_epidemiologia_n17.getBac_menor().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getBac_menor()) : null);
			ibxBac_1_4.setValue((ficha_epidemiologia_n17.getBac_1_4() != null && !ficha_epidemiologia_n17.getBac_1_4().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getBac_1_4()) : null);
			ibxBac_5_9.setValue((ficha_epidemiologia_n17.getBac_5_9() != null && !ficha_epidemiologia_n17.getBac_5_9().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getBac_5_9()) : null);
			ibxBac_10_14.setValue((ficha_epidemiologia_n17.getBac_10_14() != null && !ficha_epidemiologia_n17.getBac_10_14().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getBac_10_14()) : null);
			ibxTotal_bac.setValue((ficha_epidemiologia_n17.getTotal_bac() != null && !ficha_epidemiologia_n17.getTotal_bac().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_bac()) : null);
			
			ibxRecien.setValue((ficha_epidemiologia_n17.getRecien() != null && !ficha_epidemiologia_n17.getRecien().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getRecien()) : null);
			
			ibxVop1_menor.setValue((ficha_epidemiologia_n17.getVop1_menor() != null && !ficha_epidemiologia_n17.getVop1_menor().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getVop1_menor()) : null);
			ibxVop1_1_4.setValue((ficha_epidemiologia_n17.getVop1_1_4() != null && !ficha_epidemiologia_n17.getVop1_1_4().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getVop1_1_4()) : null);
			ibxVop1_5_9.setValue((ficha_epidemiologia_n17.getVop1_5_9() != null && !ficha_epidemiologia_n17.getVop1_5_9().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getVop1_5_9()) : null);
			ibxTotal_vop1.setValue((ficha_epidemiologia_n17.getTotal_vop1() != null && !ficha_epidemiologia_n17.getTotal_vop1().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_vop1()) : null);
			ibxVop2_menor.setValue((ficha_epidemiologia_n17.getVop2_menor() != null && !ficha_epidemiologia_n17.getVop2_menor().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getVop2_menor()) : null);
			ibxVop2_1_4.setValue((ficha_epidemiologia_n17.getVop2_1_4() != null && !ficha_epidemiologia_n17.getVop2_1_4().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getVop2_1_4()) : null);
			ibxVop2_5_9.setValue((ficha_epidemiologia_n17.getVop2_5_9() != null && !ficha_epidemiologia_n17.getVop2_5_9().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getVop2_5_9()) : null);
			ibxTotal_vop2.setValue((ficha_epidemiologia_n17.getTotal_vop2() != null && !ficha_epidemiologia_n17.getTotal_vop2().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_vop2()) : null);
			ibxVop3_menor.setValue((ficha_epidemiologia_n17.getVop3_menor() != null && !ficha_epidemiologia_n17.getVop3_menor().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getVop3_menor()) : null);
			ibxVop3_1_4.setValue((ficha_epidemiologia_n17.getVop3_1_4() != null && !ficha_epidemiologia_n17.getVop3_1_4().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getVop3_1_4()) : null);
			ibxVop3_5_9.setValue((ficha_epidemiologia_n17.getVop3_5_9() != null && !ficha_epidemiologia_n17.getVop3_5_9().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getVop3_5_9()) : null);
			ibxTotal_vop3.setValue((ficha_epidemiologia_n17.getTotal_vop3() != null && !ficha_epidemiologia_n17.getTotal_vop3().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_vop3()) : null);
			ibxAdicional_menor.setValue((ficha_epidemiologia_n17.getAdicional_menor() != null && !ficha_epidemiologia_n17.getAdicional_menor().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getAdicional_menor()) : null);
			ibxAdicional_1_4.setValue((ficha_epidemiologia_n17.getAdicional_1_4() != null && !ficha_epidemiologia_n17.getAdicional_1_4().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getAdicional_1_4()) : null);
			ibxAdicional_5_9.setValue((ficha_epidemiologia_n17.getAdicional_5_9() != null && !ficha_epidemiologia_n17.getAdicional_5_9().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getAdicional_5_9()) : null);
			ibxTotal_adicional.setValue((ficha_epidemiologia_n17.getTotal_adicional() != null && !ficha_epidemiologia_n17.getTotal_adicional().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getTotal_adicional()) : null);
			dtbxFecha_vacunacion_bloqueo.setValue(ficha_epidemiologia_n17.getFecha_vacunacion_bloqueo());
			dtbxFecha_culminacion_bloqueo.setValue(ficha_epidemiologia_n17.getFecha_culminacion_bloqueo());
			ibxViviendas_zona.setValue((ficha_epidemiologia_n17.getViviendas_zona() != null && !ficha_epidemiologia_n17.getViviendas_zona().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getViviendas_zona()) : null);
			ibxViviendas_visitadas.setValue((ficha_epidemiologia_n17.getViviendas_visitadas() != null && !ficha_epidemiologia_n17.getViviendas_visitadas().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n17.getViviendas_visitadas()) : null);
			Utilidades.seleccionarRadio(rdbCaso_detectado, ficha_epidemiologia_n17.getCaso_detectado());
			dtbxFecha_seguimiento.setValue(ficha_epidemiologia_n17.getFecha_seguimiento());
			Utilidades.seleccionarRadio(rdbParalisis_residual, ficha_epidemiologia_n17.getParalisis_residual());
			Utilidades.seleccionarRadio(rdbAtrofia, ficha_epidemiologia_n17.getAtrofia());
			Utilidades.seleccionarRadio(rdbClasificacion_final, ficha_epidemiologia_n17.getClasificacion_final());
			dtbxFecha_clasificacion.setValue(ficha_epidemiologia_n17.getFecha_clasificacion());
			Utilidades.seleccionarRadio(rdbCriterios_clasificacion, ficha_epidemiologia_n17.getCriterios_clasificacion());
			tbxImpresion_diagnostica2.setValue(ficha_epidemiologia_n17.getImpresion_diagnostica2());
			tbxImpresion_final.setValue(ficha_epidemiologia_n17.getImpresion_final());
			tbxCodigo_medico.setValue(ficha_epidemiologia_n17.getCodigo_medico());
			
			

			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n17 ficha_epidemiologia_n17 = (Ficha_epidemiologia_n17)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n17);
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
	
	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
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
	
	public void sumarPoblacion(Intbox ibx1, Intbox ibx2, Intbox ibx3, Intbox ibx4, Intbox ibxTotal) {
		try {
			Integer menor = ibx1.getValue();
			Integer menor1_4 = ibx2.getValue();
			Integer menor5_9 = ibx3.getValue();
			Integer menor10_14 = ibx4.getValue();
			
			if(menor == null){
				menor = 0;
			} 
			if(menor1_4 == null){
				menor1_4 = 0;
			} 
			if(menor5_9 == null){
				menor5_9 = 0;
			}
			if(menor10_14 == null){
				menor10_14 = 0;
			}
			
			Integer total = menor + menor1_4 + menor5_9 + menor10_14;
			ibxTotal.setValue(total);
			

			
		} catch (Exception e) {
			//  block

			MensajesUtil.mensajeError(e, "", this);
			e.printStackTrace();

		}
	}
	
	public void sumarDosis(Intbox ibx1, Intbox ibx2, Intbox ibx3, Intbox ibxTotal) {
		try {
			Integer menor = ibx1.getValue();
			Integer menor1_4 = ibx2.getValue();
			Integer menor5_9 = ibx3.getValue();
			
			if(menor == null){
				menor = 0;
			} 
			if(menor1_4 == null){
				menor1_4 = 0;
			} 
			if(menor5_9 == null){
				menor5_9 = 0;
			}
			
			Integer total = menor + menor1_4 + menor5_9;
			ibxTotal.setValue(total);
			

			
		} catch (Exception e) {
			//  block

			MensajesUtil.mensajeError(e, "", this);
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
	public Ficha_epidemiologia_n17 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n17> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n17.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n17 ficha_n17 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n17;
				}else{

					return null;
				}
	}
	

}