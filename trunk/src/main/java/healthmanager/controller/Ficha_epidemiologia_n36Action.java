/*
 * ficha_epidemiologia_n36Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n36;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n36Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n36>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n36Action.class);
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_ficha;
	@View private Datebox dtbxFecha_ficha;
	@View private Textbox tbx_nro_identificacion;
	@View private Textbox tbx_nombres;
	@View private Textbox tbx_tipo_identificacion;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	
	@View private Radiogroup rdbClasificacion_inicial;
	@View private Textbox tbxNombre_del_tutor;
	@View private Radiogroup rdbFuente_notificacion;
	@View private Textbox tbxNombre_de_la_madre;
	@View private Intbox ibxEdad_de_la_madre;
	@View private Intbox ibxEmbarazos;
	@View private Radiogroup rdbCarne_de_vacunacion;
	@View private Radiogroup rdbVacuna_rubeola;
	@View private Intbox ibxNumero_de_dosis;
	@View private Datebox dtbxUltima_dosis;
	@View private Radiogroup rdbRubeola_confirmada;
	@View private Intbox ibxSemanas_de_embarazo_de_la_confimacion_de_rubeola;
	@View private Radiogroup rdbSimilar_a_la_rubeola;
	@View private Textbox tbxSemanas_de_embarazo_contacto_similar_a_rubeola;
	@View private Radiogroup rdbExpuesta_rubeola;
	@View private Intbox ibxSemanas_de_embarazo_de_la_expocsicion;
	@View private Textbox tbxDepartamento_municipio_donde_fue_expesta;
	@View private Textbox tbxCodigo_1;
	@View private Radiogroup rdbViajes;
	@View private Intbox ibxSemanas_de_embarazo_del_viaje;
	@View private Textbox tbxDepartamento_municipio_donde_viajo;
	@View private Textbox tbxCodigo_2;
	@View private Intbox ibxApgar;
	@View private Radiogroup rdbBajo_de_peso_al_nacer;
	@View private Doublebox dbxPeso;
	@View private Radiogroup rdbPequeno_para_edad_gestacional;
	@View private Intbox ibxSemanas_nacimiento;
	@View private Radiogroup rdbCataratas;
	@View private Radiogroup rdbGlaucoma;
	@View private Radiogroup rdbRetinopatia_pigmentaria;
	@View private Textbox tbxOtros_1;
	@View private Radiogroup rdbMicrosefalia;
	@View private Radiogroup rdbRetraso_en_el_desarrollo_psicomotor;
	@View private Radiogroup rdbPurpura;
	@View private Radiogroup rdbHigado_agrandado;
	@View private Radiogroup rdbPersistensia_del_conducto_arterioso;
	@View private Radiogroup rdbEstenosis_de_la_arteria_pulmonar;
	@View private Textbox tbxOtros_2;
	@View private Radiogroup rdbIctericia_al_nacer;
	@View private Radiogroup rdbBazo_agrandado;
	@View private Radiogroup rdbOsteopatia_radio_lucida;
	@View private Radiogroup rdbMeningoencefalitis;
	@View private Textbox tbxOtros_3;
	@View private Radiogroup rdbSoldera;
	@View private Textbox tbxOtros_4;
	@View private Datebox dtbxFecha_de_la_toma_1;
	@View private Datebox dtbxFecha_recepcion_1;
	@View private Checkbox chbMuestra_1;
	@View private Checkbox chbPrueba_1;
	@View private Textbox tbxAgente_1;
	@View private Textbox tbxResultado_1;
	@View private Datebox dtbxFecha_resultado_1;
	@View private Textbox tbxValor_1;
	@View private Datebox dtbxFecha_de_la_toma_2;
	@View private Datebox dtbxFecha_recepcion_2;
	@View private Checkbox chbMuestra_2;
	@View private Checkbox chbPrueba_2;
	@View private Textbox tbxAgente_2;
	@View private Textbox tbxReusltado_2;
	@View private Datebox dtbxFecha_resultado_2;
	@View private Textbox tbxValor_2;
	@View private Datebox dtbxFecha_de_la_toma_3;
	@View private Datebox dtbxFecha_recepcion_3;
	@View private Checkbox chbMuestra_3;
	@View private Checkbox chbPrueba_3;
	@View private Textbox tbxAgente_3;
	@View private Textbox tbxResultado_3;
	@View private Datebox dtbxFecha_resultado_3;
	@View private Textbox tbxValor_3;
	@View private Datebox dtbxFecha_de_la_toma_4;
	@View private Datebox dtbxFecha_recepcion_4;
	@View private Checkbox chbMuestra_4;
	@View private Checkbox chbPrueba_4;
	@View private Textbox tbxAgente_4;
	@View private Textbox tbxResultado_4;
	@View private Datebox dtbxFecha_resultado_4;
	@View private Textbox tbxValor_4;
	@View private Datebox dtbxFecha_inicio_investtigacion;
	@View private Textbox tbxDiagnostico_final;
	//@View private Textbox tbxInvestigado_por;
	//@View private Intbox ibxTelefono;
	@View private Row row1;
	@View private Textbox tbx_otras_cual;
	private final String[] idsExcluyentes = {};
	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;

	
	@Override
	public void init() throws Exception{
		listarCombos();obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n36 ficha = new Ficha_epidemiologia_n36();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n36) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 36-------> "+ficha);
			
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
	
	
	//metodo para mostra columna oculta 
	public void mostrarColumna(Radiogroup rg){
		if (rg.getSelectedItem().getValue().equals("6")){
			row1.setVisible(true);
		}else{			
			row1.setVisible(false);
			tbx_otras_cual.setText("");
		}		
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Nro_identificacion");
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
	
	// Metodo para validar campos del formulario //
	public boolean validarFichaEpidemiologia() {

		dtbxFecha_ficha.setStyle("background-color:white");
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:white");
		tbx_nro_identificacion.setStyle("background-color:white");

		boolean valida = true;

		if (dtbxFecha_ficha.getText().trim().equals("")) {
			dtbxFecha_ficha.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		// if(tbxNombres_y_apellidos.getText().trim().equals("")){
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		// valida = false;
		// }
		if (tbx_nro_identificacion.getText().trim().equals("")) {
			tbx_nro_identificacion.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
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

			getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
					"limit 25 offset 0");

			List<Ficha_epidemiologia_n36> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n36.class, parameters);

			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n36 ficha_epidemiologia_n36 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n36,
						this));
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
		
		final Ficha_epidemiologia_n36 ficha_epidemiologia_n36 = (Ficha_epidemiologia_n36)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n36);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(ficha_epidemiologia_n36);
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
	
	//Metodo para guardar la informacion //

	public Ficha_epidemiologia_n36 obtenerFichaEpidemiologia() {

		Ficha_epidemiologia_n36 ficha_epidemiologia_n36 = new Ficha_epidemiologia_n36();
				ficha_epidemiologia_n36.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n36.setFecha_ficha(new Timestamp(dtbxFecha_ficha.getValue().getTime()));
				ficha_epidemiologia_n36.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n36.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n36.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n36.setNro_identificacion(admision != null ? admision
						.getNro_identificacion() : null);
			
				//ficha_epidemiologia_n36.setNro_identificacion();
				//ficha_epidemiologia_n36.setCodigo();
				ficha_epidemiologia_n36.setClasificacion_inicial(rdbClasificacion_inicial.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setNombre_del_tutor(tbxNombre_del_tutor.getValue());
				ficha_epidemiologia_n36.setFuente_notificacion(rdbFuente_notificacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setNombre_de_la_madre(tbxNombre_de_la_madre.getValue());
				ficha_epidemiologia_n36.setEdad_de_la_madre((ibxEdad_de_la_madre.getValue()!=null?ibxEdad_de_la_madre.getValue():0));
				ficha_epidemiologia_n36.setEmbarazos((ibxEmbarazos.getValue()!=null?ibxEmbarazos.getValue():0));
				ficha_epidemiologia_n36.setCarne_de_vacunacion(rdbCarne_de_vacunacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setVacuna_rubeola(rdbVacuna_rubeola.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setNumero_de_dosis((ibxNumero_de_dosis.getValue()!=null?ibxNumero_de_dosis.getValue():0));
				ficha_epidemiologia_n36.setUltima_dosis(new Timestamp(dtbxUltima_dosis.getValue().getTime()));
				ficha_epidemiologia_n36.setRubeola_confirmada(rdbRubeola_confirmada.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setSemanas_de_embarazo_de_la_confimacion_de_rubeola((ibxSemanas_de_embarazo_de_la_confimacion_de_rubeola.getValue()!=null?ibxSemanas_de_embarazo_de_la_confimacion_de_rubeola.getValue():0));
				ficha_epidemiologia_n36.setSimilar_a_la_rubeola(rdbSimilar_a_la_rubeola.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setSemanas_de_embarazo_contacto_similar_a_rubeola(tbxSemanas_de_embarazo_contacto_similar_a_rubeola.getValue());
				ficha_epidemiologia_n36.setExpuesta_rubeola(rdbExpuesta_rubeola.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setSemanas_de_embarazo_de_la_expocsicion((ibxSemanas_de_embarazo_de_la_expocsicion.getValue()!=null?ibxSemanas_de_embarazo_de_la_expocsicion.getValue():0));
				ficha_epidemiologia_n36.setDepartamento_municipio_donde_fue_expesta(tbxDepartamento_municipio_donde_fue_expesta.getValue());
				ficha_epidemiologia_n36.setCodigo_1(tbxCodigo_1.getValue());
				ficha_epidemiologia_n36.setViajes(rdbViajes.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setSemanas_de_embarazo_del_viaje((ibxSemanas_de_embarazo_del_viaje.getValue()!=null?ibxSemanas_de_embarazo_del_viaje.getValue():0));
				ficha_epidemiologia_n36.setDepartamento_municipio_donde_viajo(tbxDepartamento_municipio_donde_viajo.getValue());
				ficha_epidemiologia_n36.setCodigo_2(tbxCodigo_2.getValue());
				ficha_epidemiologia_n36.setApgar((ibxApgar.getValue()!=null?ibxApgar.getValue():0));
				ficha_epidemiologia_n36.setBajo_de_peso_al_nacer(rdbBajo_de_peso_al_nacer.getSelectedItem().getValue().toString());		
				ficha_epidemiologia_n36.setPeso(dbxPeso.getValue()!=null?dbxPeso.getValue():0.00);
				ficha_epidemiologia_n36.setPequeno_para_edad_gestacional(rdbPequeno_para_edad_gestacional.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setSemanas_nacimiento((ibxSemanas_nacimiento.getValue()!=null?ibxSemanas_nacimiento.getValue():0));
				ficha_epidemiologia_n36.setCataratas(rdbCataratas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setGlaucoma(rdbGlaucoma.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setRetinopatia_pigmentaria(rdbRetinopatia_pigmentaria.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setOtros_1(tbxOtros_1.getValue());
				ficha_epidemiologia_n36.setMicrosefalia(rdbMicrosefalia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setRetraso_en_el_desarrollo_psicomotor(rdbRetraso_en_el_desarrollo_psicomotor.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setPurpura(rdbPurpura.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setHigado_agrandado(rdbHigado_agrandado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setPersistensia_del_conducto_arterioso(rdbPersistensia_del_conducto_arterioso.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setEstenosis_de_la_arteria_pulmonar(rdbEstenosis_de_la_arteria_pulmonar.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setOtros_2(tbxOtros_2.getValue());
				ficha_epidemiologia_n36.setIctericia_al_nacer(rdbIctericia_al_nacer.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setBazo_agrandado(rdbBazo_agrandado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setOsteopatia_radio_lucida(rdbOsteopatia_radio_lucida.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setMeningoencefalitis(rdbMeningoencefalitis.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setOtros_3(tbxOtros_3.getValue());
				ficha_epidemiologia_n36.setSoldera(rdbSoldera.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n36.setOtros_4(tbxOtros_4.getValue());
				ficha_epidemiologia_n36.setFecha_de_la_toma_1(new Timestamp(dtbxFecha_de_la_toma_1.getValue().getTime()));
				ficha_epidemiologia_n36.setFecha_recepcion_1(new Timestamp(dtbxFecha_recepcion_1.getValue().getTime()));
				ficha_epidemiologia_n36.setMuestra_1(chbMuestra_1.isChecked()?"S":"N");
				ficha_epidemiologia_n36.setPrueba_1(chbPrueba_1.isChecked()?"S":"N");
				ficha_epidemiologia_n36.setAgente_1(tbxAgente_1.getValue());
				ficha_epidemiologia_n36.setResultado_1(tbxResultado_1.getValue());
				ficha_epidemiologia_n36.setFecha_resultado_1(new Timestamp(dtbxFecha_resultado_1.getValue().getTime()));
				ficha_epidemiologia_n36.setValor_1(tbxValor_1.getValue());
				ficha_epidemiologia_n36.setFecha_de_la_toma_2(new Timestamp(dtbxFecha_de_la_toma_2.getValue().getTime()));
				ficha_epidemiologia_n36.setFecha_recepcion_2(new Timestamp(dtbxFecha_recepcion_2.getValue().getTime()));
				ficha_epidemiologia_n36.setMuestra_2(chbMuestra_2.isChecked()?"S":"N");
				ficha_epidemiologia_n36.setPrueba_2(chbPrueba_2.isChecked()?"S":"N");
				ficha_epidemiologia_n36.setAgente_2(tbxAgente_2.getValue());
				ficha_epidemiologia_n36.setReusltado_2(tbxReusltado_2.getValue());
				ficha_epidemiologia_n36.setFecha_resultado_2(new Timestamp(dtbxFecha_resultado_2.getValue().getTime()));
				ficha_epidemiologia_n36.setValor_2(tbxValor_2.getValue());
				ficha_epidemiologia_n36.setFecha_de_la_toma_3(new Timestamp(dtbxFecha_de_la_toma_3.getValue().getTime()));
				ficha_epidemiologia_n36.setFecha_recepcion_3(new Timestamp(dtbxFecha_recepcion_3.getValue().getTime()));
				ficha_epidemiologia_n36.setMuestra_3(chbMuestra_3.isChecked()?"S":"N");
				ficha_epidemiologia_n36.setPrueba_3(chbPrueba_3.isChecked()?"S":"N");
				ficha_epidemiologia_n36.setAgente_3(tbxAgente_3.getValue());
				ficha_epidemiologia_n36.setResultado_3(tbxResultado_3.getValue());
				ficha_epidemiologia_n36.setFecha_resultado_3(new Timestamp(dtbxFecha_resultado_3.getValue().getTime()));
				ficha_epidemiologia_n36.setValor_3(tbxValor_3.getValue());
				ficha_epidemiologia_n36.setFecha_de_la_toma_4(new Timestamp(dtbxFecha_de_la_toma_4.getValue().getTime()));
				ficha_epidemiologia_n36.setFecha_recepcion_4(new Timestamp(dtbxFecha_recepcion_4.getValue().getTime()));
				ficha_epidemiologia_n36.setMuestra_4(chbMuestra_4.isChecked()?"S":"N");
				ficha_epidemiologia_n36.setPrueba_4(chbPrueba_4.isChecked()?"S":"N");
				ficha_epidemiologia_n36.setAgente_4(tbxAgente_4.getValue());
				ficha_epidemiologia_n36.setResultado_4(tbxResultado_4.getValue());
				ficha_epidemiologia_n36.setFecha_resultado_4(new Timestamp(dtbxFecha_resultado_4.getValue().getTime()));
				ficha_epidemiologia_n36.setValor_4(tbxValor_4.getValue());
				ficha_epidemiologia_n36.setFecha_inicio_investtigacion(new Timestamp(dtbxFecha_inicio_investtigacion.getValue().getTime()));
				ficha_epidemiologia_n36.setDiagnostico_final(tbxDiagnostico_final.getValue());
				ficha_epidemiologia_n36.setInvestigado_por("");
				ficha_epidemiologia_n36.setTelefono(0);
				ficha_epidemiologia_n36.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n36.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				//ficha_epidemiologia_n36.setDelete_date();
				ficha_epidemiologia_n36.setUltimo_user(usuarios.getCodigo().toString());
				//ficha_epidemiologia_n36.setDelete_use();
				ficha_epidemiologia_n36.setCreacion_user(usuarios.getCodigo().toString());
				
				return ficha_epidemiologia_n36;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n36 obj)throws Exception{
		Ficha_epidemiologia_n36 ficha_epidemiologia_n36 = (Ficha_epidemiologia_n36)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n36.getCodigo_ficha());
			dtbxFecha_ficha.setValue(ficha_epidemiologia_n36.getFecha_ficha());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			Utilidades.seleccionarRadio(rdbClasificacion_inicial, ficha_epidemiologia_n36.getClasificacion_inicial());
			tbxNombre_del_tutor.setValue(ficha_epidemiologia_n36.getNombre_del_tutor());
			Utilidades.seleccionarRadio(rdbFuente_notificacion, ficha_epidemiologia_n36.getFuente_notificacion());
			tbxNombre_de_la_madre.setValue(ficha_epidemiologia_n36.getNombre_de_la_madre());
			ibxEdad_de_la_madre.setValue(ficha_epidemiologia_n36.getEdad_de_la_madre());
			ibxEmbarazos.setValue(ficha_epidemiologia_n36.getEmbarazos());
			Utilidades.seleccionarRadio(rdbCarne_de_vacunacion, ficha_epidemiologia_n36.getCarne_de_vacunacion());
			Utilidades.seleccionarRadio(rdbVacuna_rubeola, ficha_epidemiologia_n36.getVacuna_rubeola());
			ibxNumero_de_dosis.setValue(ficha_epidemiologia_n36.getNumero_de_dosis());
			dtbxUltima_dosis.setValue(ficha_epidemiologia_n36.getUltima_dosis());
			Utilidades.seleccionarRadio(rdbRubeola_confirmada, ficha_epidemiologia_n36.getRubeola_confirmada());
			ibxSemanas_de_embarazo_de_la_confimacion_de_rubeola.setValue(ficha_epidemiologia_n36.getSemanas_de_embarazo_de_la_confimacion_de_rubeola());
			Utilidades.seleccionarRadio(rdbSimilar_a_la_rubeola, ficha_epidemiologia_n36.getSimilar_a_la_rubeola());
			tbxSemanas_de_embarazo_contacto_similar_a_rubeola.setValue(ficha_epidemiologia_n36.getSemanas_de_embarazo_contacto_similar_a_rubeola());
			Utilidades.seleccionarRadio(rdbExpuesta_rubeola, ficha_epidemiologia_n36.getExpuesta_rubeola());
			ibxSemanas_de_embarazo_de_la_expocsicion.setValue(ficha_epidemiologia_n36.getSemanas_de_embarazo_de_la_expocsicion());
			tbxDepartamento_municipio_donde_fue_expesta.setValue(ficha_epidemiologia_n36.getDepartamento_municipio_donde_fue_expesta());
			tbxCodigo_1.setValue(ficha_epidemiologia_n36.getCodigo_1());
			Utilidades.seleccionarRadio(rdbViajes, ficha_epidemiologia_n36.getViajes());
			ibxSemanas_de_embarazo_del_viaje.setValue(ficha_epidemiologia_n36.getSemanas_de_embarazo_del_viaje());
			tbxDepartamento_municipio_donde_viajo.setValue(ficha_epidemiologia_n36.getDepartamento_municipio_donde_viajo());
			tbxCodigo_2.setValue(ficha_epidemiologia_n36.getCodigo_2());
			ibxApgar.setValue(ficha_epidemiologia_n36.getApgar());
			Utilidades.seleccionarRadio(rdbBajo_de_peso_al_nacer, ficha_epidemiologia_n36.getBajo_de_peso_al_nacer());
			dbxPeso.setValue(ficha_epidemiologia_n36.getPeso());
			Utilidades.seleccionarRadio(rdbPequeno_para_edad_gestacional, ficha_epidemiologia_n36.getPequeno_para_edad_gestacional());
			ibxSemanas_nacimiento.setValue(ficha_epidemiologia_n36.getSemanas_nacimiento());
			Utilidades.seleccionarRadio(rdbCataratas, ficha_epidemiologia_n36.getCataratas());
			Utilidades.seleccionarRadio(rdbGlaucoma, ficha_epidemiologia_n36.getGlaucoma());
			Utilidades.seleccionarRadio(rdbRetinopatia_pigmentaria, ficha_epidemiologia_n36.getRetinopatia_pigmentaria());
			tbxOtros_1.setValue(ficha_epidemiologia_n36.getOtros_1());
			Utilidades.seleccionarRadio(rdbMicrosefalia, ficha_epidemiologia_n36.getMicrosefalia());
			Utilidades.seleccionarRadio(rdbRetraso_en_el_desarrollo_psicomotor, ficha_epidemiologia_n36.getRetraso_en_el_desarrollo_psicomotor());
			Utilidades.seleccionarRadio(rdbPurpura, ficha_epidemiologia_n36.getPurpura());
			Utilidades.seleccionarRadio(rdbHigado_agrandado, ficha_epidemiologia_n36.getHigado_agrandado());
			Utilidades.seleccionarRadio(rdbPersistensia_del_conducto_arterioso, ficha_epidemiologia_n36.getPersistensia_del_conducto_arterioso());
			Utilidades.seleccionarRadio(rdbEstenosis_de_la_arteria_pulmonar, ficha_epidemiologia_n36.getEstenosis_de_la_arteria_pulmonar());
			tbxOtros_2.setValue(ficha_epidemiologia_n36.getOtros_2());
			Utilidades.seleccionarRadio(rdbIctericia_al_nacer, ficha_epidemiologia_n36.getIctericia_al_nacer());
			Utilidades.seleccionarRadio(rdbBazo_agrandado, ficha_epidemiologia_n36.getBazo_agrandado());
			Utilidades.seleccionarRadio(rdbOsteopatia_radio_lucida, ficha_epidemiologia_n36.getOsteopatia_radio_lucida());
			Utilidades.seleccionarRadio(rdbMeningoencefalitis, ficha_epidemiologia_n36.getMeningoencefalitis());
			tbxOtros_3.setValue(ficha_epidemiologia_n36.getOtros_3());
			Utilidades.seleccionarRadio(rdbSoldera, ficha_epidemiologia_n36.getSoldera());
			tbxOtros_4.setValue(ficha_epidemiologia_n36.getOtros_4());
			dtbxFecha_de_la_toma_1.setValue(ficha_epidemiologia_n36.getFecha_de_la_toma_1());
			dtbxFecha_recepcion_1.setValue(ficha_epidemiologia_n36.getFecha_recepcion_1());
			chbMuestra_1.setChecked(ficha_epidemiologia_n36.getMuestra_1().equals("S")?true:false);
			chbPrueba_1.setChecked(ficha_epidemiologia_n36.getPrueba_1().equals("S")?true:false);
			tbxAgente_1.setValue(ficha_epidemiologia_n36.getAgente_1());
			tbxResultado_1.setValue(ficha_epidemiologia_n36.getResultado_1());
			dtbxFecha_resultado_1.setValue(ficha_epidemiologia_n36.getFecha_resultado_1());
			tbxValor_1.setValue(ficha_epidemiologia_n36.getValor_1());
			dtbxFecha_de_la_toma_2.setValue(ficha_epidemiologia_n36.getFecha_de_la_toma_2());
			dtbxFecha_recepcion_2.setValue(ficha_epidemiologia_n36.getFecha_recepcion_2());
			chbMuestra_2.setChecked(ficha_epidemiologia_n36.getMuestra_2().equals("S")?true:false);
			chbPrueba_2.setChecked(ficha_epidemiologia_n36.getPrueba_2().equals("S")?true:false);
			tbxAgente_2.setValue(ficha_epidemiologia_n36.getAgente_2());
			tbxReusltado_2.setValue(ficha_epidemiologia_n36.getReusltado_2());
			dtbxFecha_resultado_2.setValue(ficha_epidemiologia_n36.getFecha_resultado_2());
			tbxValor_2.setValue(ficha_epidemiologia_n36.getValor_2());
			dtbxFecha_de_la_toma_3.setValue(ficha_epidemiologia_n36.getFecha_de_la_toma_3());
			dtbxFecha_recepcion_3.setValue(ficha_epidemiologia_n36.getFecha_recepcion_3());
			chbMuestra_3.setChecked(ficha_epidemiologia_n36.getMuestra_3().equals("S")?true:false);
			chbPrueba_3.setChecked(ficha_epidemiologia_n36.getPrueba_3().equals("S")?true:false);
			tbxAgente_3.setValue(ficha_epidemiologia_n36.getAgente_3());
			tbxResultado_3.setValue(ficha_epidemiologia_n36.getResultado_3());
			dtbxFecha_resultado_3.setValue(ficha_epidemiologia_n36.getFecha_resultado_3());
			tbxValor_3.setValue(ficha_epidemiologia_n36.getValor_3());
			dtbxFecha_de_la_toma_4.setValue(ficha_epidemiologia_n36.getFecha_de_la_toma_4());
			dtbxFecha_recepcion_4.setValue(ficha_epidemiologia_n36.getFecha_recepcion_4());
			chbMuestra_4.setChecked(ficha_epidemiologia_n36.getMuestra_4().equals("S")?true:false);
			chbPrueba_4.setChecked(ficha_epidemiologia_n36.getPrueba_4().equals("S")?true:false);
			tbxAgente_4.setValue(ficha_epidemiologia_n36.getAgente_4());
			tbxResultado_4.setValue(ficha_epidemiologia_n36.getResultado_4());
			dtbxFecha_resultado_4.setValue(ficha_epidemiologia_n36.getFecha_resultado_4());
			tbxValor_4.setValue(ficha_epidemiologia_n36.getValor_4());
			dtbxFecha_inicio_investtigacion.setValue(ficha_epidemiologia_n36.getFecha_inicio_investtigacion());
			tbxDiagnostico_final.setValue(ficha_epidemiologia_n36.getDiagnostico_final());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n36 ficha_epidemiologia_n36 = (Ficha_epidemiologia_n36)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n36);
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
	public void obtenerAdmision(Admision admision){
		Paciente paciente = admision.getPaciente();
		tbx_nro_identificacion.setValue(admision.getNro_identificacion());
		tbx_nombres.setValue(paciente.getNombreCompleto());
		tbx_tipo_identificacion.setValue(paciente.getTipo_identificacion());
		
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
	public Ficha_epidemiologia_n36 consultarDatos(Map<String, Object> map,
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
				parameters.put("nro_identificacion", admision.getNro_identificacion());
				
				if(impresion_diagnostica != null){
				parameters.put("codigo_historia", impresion_diagnostica.getCodigo_historia());
				}else{
					return null;
				}
				
				if(cie_epidemiologia != null){
					parameters.put("codigo", cie_epidemiologia.getCodigo_cie());			
				}else{
					return null;
				}
				
				getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
				
				List<Ficha_epidemiologia_n36> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n36.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n36 ficha_n36 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n36;
				}else{

					return null;
				}
	}
}