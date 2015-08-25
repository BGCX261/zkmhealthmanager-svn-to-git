/*
 * ficha_epidemiologia_n39Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n39;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class Ficha_epidemiologia_n39Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n39>{

	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbx_nro_identificacion;
	@View private Textbox tbx_nombres;
	@View private Textbox tbx_tipo_identificacion;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View private Textbox tbxCodigo_ficha;
	@View private Datebox dtbxFecha_ficha;
	@View private Radiogroup rdbHistoria_previa_de_sifilis;
	@View private Intbox ibxN_gestaciones;
	@View private Intbox ibxVivos;
	@View private Intbox ibxAbortos;
	@View private Intbox ibxMortinatos;
	@View private Radiogroup rdbOtras_its;
	@View private Textbox tbxCual_its;
	@View private Radiogroup rdbAlergia_a_la_penicilina;
	@View private Radiogroup rdbCondicion_al_momento_del_diagnostico;
	@View private Radiogroup rdbLugar_de_atencion_del_parto;
	@View private Textbox tbxOtro_lugar_de_parto;
	@View private Radiogroup rdbControl_prenatal_en_embarazo_actual;
	@View private Intbox ibxNumero_de_controles_prenatales;
	@View private Intbox ibxEdad_gestacional_al_inicio_del_control_prental;
	@View private Intbox ibxEdad_gestacional_a_la_toma_de_serologia;
	@View private Radiogroup rdbResultado_serologia;
	@View private Checkbox chbDils_0;
	@View private Checkbox chbDils_1;
	@View private Checkbox chbDils_2;
	@View private Checkbox chbDils_4;
	@View private Checkbox chbDils_8;
	@View private Checkbox chbDils_16;
	@View private Checkbox chbDils_32;
	@View private Checkbox chbDils_64;
	@View private Checkbox chbDils_128;
	@View private Checkbox chbDils_256;
	@View private Checkbox chbDils_512;
	@View private Checkbox chbDils_1024;
	@View private Checkbox chbDils_2048;
	@View private Checkbox chbSin_dato;
	@View private Radiogroup rdbPrueba_treponemica_confirmatoria;
	@View private Radiogroup rdbResultado_de_prueba_treponemica;
	@View private Radiogroup rdbSe_administro_tratamiento_a_la_madre;
	@View private Radiogroup rdbModalidad_tratamiento;
	@View private Intbox ibxSemana_gestacional_al_inicio_del_tratamiento;
	@View private Radiogroup rdbMotivo_de_no_tratamiento;
	@View private Textbox tbxOtro_motivo;
	@View private Radiogroup rdbMedicamento_administrado;
	@View private Textbox tbxCual_otro_suministrado;
	@View private Radiogroup rdbNivel_de_complegidad_de_la_institucion_tratante;
	@View private Radiogroup rdbClasificacion_final_de_sifilis;
	@View private Radiogroup rdbDiagnostico_de_contactos;
	@View private Radiogroup rdbTratamiento_de_contactos;
	@View private Row row_1;
	@View private Row row_2;
	@View private Row row_3;
	@View private Row row_4;
	
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
			Ficha_epidemiologia_n39 ficha = new Ficha_epidemiologia_n39();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n39) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 39-------> "+ficha);
			
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
	}
	
	public void mostrarFila(Radiogroup rg){
		if (rg.getSelectedItem().getValue().equals("3")){
			row_1.setVisible(true);
			
		}else{
			
			row_1.setVisible(false);
			tbxCual_its.setText("");
		}
		
	}
public void mostrarFila_2(Radiogroup rg_1){
	if(rg_1.getSelectedItem().getValue().equals("3")){
		row_2.setVisible(true);
	}else{
		row_2.setVisible(false);
		tbxOtro_lugar_de_parto.setText("");
		
	}
	
}
public void mostrarFila_3(Radiogroup rg_1){
	if(rg_1.getSelectedItem().getValue().equals("3")){
		row_3.setVisible(true);
	}else{
		row_3.setVisible(false);
		tbxOtro_motivo.setText("");
	}
}
public void mostrarFila_4(Radiogroup rg_1){
	if(rg_1.getSelectedItem().getValue().equals("3")){
		row_4.setVisible(true);
	}else{
		row_4.setVisible(false);
		tbxCual_otro_suministrado.setText("");
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

			List<Ficha_epidemiologia_n39> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n39.class, parameters);

			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n39 ficha_epidemiologia_n39 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n39,
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
		
		final Ficha_epidemiologia_n39 ficha_epidemiologia_n39 = (Ficha_epidemiologia_n39)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n39.getCodigo_ficha()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n39);
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
								eliminarDatos(ficha_epidemiologia_n39);
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
	public Ficha_epidemiologia_n39 obtenerFichaEpidemiologia() {
	
				Ficha_epidemiologia_n39 ficha_epidemiologia_n39 = new Ficha_epidemiologia_n39();
				ficha_epidemiologia_n39.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n39.setFecha_ficha(new Timestamp(dtbxFecha_ficha.getValue().getTime()));
				ficha_epidemiologia_n39.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n39.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n39.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n39.setNro_identificacion(admision != null ? admision
						.getNro_identificacion() : null);
				//ficha_epidemiologia_n39.setNro_identificacion();
				//ficha_epidemiologia_n39.setCodigo();
				ficha_epidemiologia_n39.setHistoria_previa_de_sifilis(rdbHistoria_previa_de_sifilis.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setN_gestaciones((ibxN_gestaciones.getValue()!=null?ibxN_gestaciones.getValue():0));
				ficha_epidemiologia_n39.setVivos((ibxVivos.getValue()!=null?ibxVivos.getValue():0));
				ficha_epidemiologia_n39.setAbortos((ibxAbortos.getValue()!=null?ibxAbortos.getValue():0));
				ficha_epidemiologia_n39.setMortinatos((ibxMortinatos.getValue()!=null?ibxMortinatos.getValue():0));
				ficha_epidemiologia_n39.setOtras_its(rdbOtras_its.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setCual_its(tbxCual_its.getValue());
				ficha_epidemiologia_n39.setAlergia_a_la_penicilina(rdbAlergia_a_la_penicilina.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setCondicion_al_momento_del_diagnostico(rdbCondicion_al_momento_del_diagnostico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setLugar_de_atencion_del_parto(rdbLugar_de_atencion_del_parto.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setOtro_lugar_de_parto(tbxOtro_lugar_de_parto.getValue());
				ficha_epidemiologia_n39.setControl_prenatal_en_embarazo_actual(rdbControl_prenatal_en_embarazo_actual.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setNumero_de_controles_prenatales((ibxNumero_de_controles_prenatales.getValue()!=null?ibxNumero_de_controles_prenatales.getValue():0));
				ficha_epidemiologia_n39.setEdad_gestacional_al_inicio_del_control_prental((ibxEdad_gestacional_al_inicio_del_control_prental.getValue()!=null?ibxEdad_gestacional_al_inicio_del_control_prental.getValue():0));
				ficha_epidemiologia_n39.setEdad_gestacional_a_la_toma_de_serologia((ibxEdad_gestacional_a_la_toma_de_serologia.getValue()!=null?ibxEdad_gestacional_a_la_toma_de_serologia.getValue():0));
				ficha_epidemiologia_n39.setResultado_serologia(rdbResultado_serologia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setDils_0(chbDils_0.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_1(chbDils_1.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_2(chbDils_2.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_4(chbDils_4.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_8(chbDils_8.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_16(chbDils_16.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_32(chbDils_32.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_64(chbDils_64.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_128(chbDils_128.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_256(chbDils_256.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_512(chbDils_512.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_1024(chbDils_1024.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setDils_2048(chbDils_2048.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setSin_dato(chbSin_dato.isChecked()?"S":"N");
				ficha_epidemiologia_n39.setPrueba_treponemica_confirmatoria(rdbPrueba_treponemica_confirmatoria.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setResultado_de_prueba_treponemica(rdbResultado_de_prueba_treponemica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setSe_administro_tratamiento_a_la_madre(rdbSe_administro_tratamiento_a_la_madre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setModalidad_tratamiento(rdbModalidad_tratamiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setSemana_gestacional_al_inicio_del_tratamiento((ibxSemana_gestacional_al_inicio_del_tratamiento.getValue()!=null?ibxSemana_gestacional_al_inicio_del_tratamiento.getValue():0));
				ficha_epidemiologia_n39.setMotivo_de_no_tratamiento(rdbMotivo_de_no_tratamiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setOtro_motivo(tbxOtro_motivo.getValue());
				ficha_epidemiologia_n39.setMedicamento_administrado(rdbMedicamento_administrado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setCual_otro_suministrado(tbxCual_otro_suministrado.getValue());
				ficha_epidemiologia_n39.setNivel_de_complegidad_de_la_institucion_tratante(rdbNivel_de_complegidad_de_la_institucion_tratante.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setClasificacion_final_de_sifilis(rdbClasificacion_final_de_sifilis.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setDiagnostico_de_contactos(rdbDiagnostico_de_contactos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setTratamiento_de_contactos(rdbTratamiento_de_contactos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n39.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				//ficha_epidemiologia_n39.setDelete_date();
				ficha_epidemiologia_n39.setUltimo_user(usuarios.getCodigo().toString());
			//	ficha_epidemiologia_n39.setDelete_use();
				ficha_epidemiologia_n39.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n39.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				
				return ficha_epidemiologia_n39;
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n39 obj)throws Exception{
		Ficha_epidemiologia_n39 ficha_epidemiologia_n39 = (Ficha_epidemiologia_n39)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n39.getCodigo_ficha());
			dtbxFecha_ficha.setValue(ficha_epidemiologia_n39.getFecha_ficha());
			obtenerAdmision(admision);

			north_ficha.setVisible(true);
			btImprimir.setVisible(true);
			//log.info("-----------------"+1);
			
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbHistoria_previa_de_sifilis, ficha_epidemiologia_n39.getHistoria_previa_de_sifilis());
			ibxN_gestaciones.setValue(ficha_epidemiologia_n39.getN_gestaciones());
			ibxVivos.setValue(ficha_epidemiologia_n39.getVivos());
			ibxAbortos.setValue(ficha_epidemiologia_n39.getAbortos());
			ibxMortinatos.setValue(ficha_epidemiologia_n39.getMortinatos());
			Utilidades.seleccionarRadio(rdbOtras_its, ficha_epidemiologia_n39.getOtras_its());
			tbxCual_its.setValue(ficha_epidemiologia_n39.getCual_its());
			Utilidades.seleccionarRadio(rdbAlergia_a_la_penicilina, ficha_epidemiologia_n39.getAlergia_a_la_penicilina());
			Utilidades.seleccionarRadio(rdbCondicion_al_momento_del_diagnostico, ficha_epidemiologia_n39.getCondicion_al_momento_del_diagnostico());
			Utilidades.seleccionarRadio(rdbLugar_de_atencion_del_parto, ficha_epidemiologia_n39.getLugar_de_atencion_del_parto());
			tbxOtro_lugar_de_parto.setValue(ficha_epidemiologia_n39.getOtro_lugar_de_parto());
			Utilidades.seleccionarRadio(rdbControl_prenatal_en_embarazo_actual, ficha_epidemiologia_n39.getControl_prenatal_en_embarazo_actual());
			ibxNumero_de_controles_prenatales.setValue(ficha_epidemiologia_n39.getNumero_de_controles_prenatales());
			ibxEdad_gestacional_al_inicio_del_control_prental.setValue(ficha_epidemiologia_n39.getEdad_gestacional_al_inicio_del_control_prental());
			ibxEdad_gestacional_a_la_toma_de_serologia.setValue(ficha_epidemiologia_n39.getEdad_gestacional_a_la_toma_de_serologia());
			Utilidades.seleccionarRadio(rdbResultado_serologia, ficha_epidemiologia_n39.getResultado_serologia());
			chbDils_0.setChecked(ficha_epidemiologia_n39.getDils_0().equals("S")?true:false);
			chbDils_1.setChecked(ficha_epidemiologia_n39.getDils_1().equals("S")?true:false);
			chbDils_2.setChecked(ficha_epidemiologia_n39.getDils_2().equals("S")?true:false);
			chbDils_4.setChecked(ficha_epidemiologia_n39.getDils_4().equals("S")?true:false);
			chbDils_8.setChecked(ficha_epidemiologia_n39.getDils_8().equals("S")?true:false);
			chbDils_16.setChecked(ficha_epidemiologia_n39.getDils_16().equals("S")?true:false);
			chbDils_32.setChecked(ficha_epidemiologia_n39.getDils_32().equals("S")?true:false);
			chbDils_64.setChecked(ficha_epidemiologia_n39.getDils_64().equals("S")?true:false);
			chbDils_128.setChecked(ficha_epidemiologia_n39.getDils_128().equals("S")?true:false);
			chbDils_256.setChecked(ficha_epidemiologia_n39.getDils_256().equals("S")?true:false);
			chbDils_512.setChecked(ficha_epidemiologia_n39.getDils_512().equals("S")?true:false);
			chbDils_1024.setChecked(ficha_epidemiologia_n39.getDils_1024().equals("S")?true:false);
			chbDils_2048.setChecked(ficha_epidemiologia_n39.getDils_2048().equals("S")?true:false);
			chbSin_dato.setChecked(ficha_epidemiologia_n39.getSin_dato().equals("S")?true:false);
			Utilidades.seleccionarRadio(rdbPrueba_treponemica_confirmatoria, ficha_epidemiologia_n39.getPrueba_treponemica_confirmatoria());
			Utilidades.seleccionarRadio(rdbResultado_de_prueba_treponemica, ficha_epidemiologia_n39.getResultado_de_prueba_treponemica());
			Utilidades.seleccionarRadio(rdbSe_administro_tratamiento_a_la_madre, ficha_epidemiologia_n39.getSe_administro_tratamiento_a_la_madre());
			Utilidades.seleccionarRadio(rdbModalidad_tratamiento, ficha_epidemiologia_n39.getModalidad_tratamiento());
			ibxSemana_gestacional_al_inicio_del_tratamiento.setValue(ficha_epidemiologia_n39.getSemana_gestacional_al_inicio_del_tratamiento());
			Utilidades.seleccionarRadio(rdbMotivo_de_no_tratamiento, ficha_epidemiologia_n39.getMotivo_de_no_tratamiento());
			tbxOtro_motivo.setValue(ficha_epidemiologia_n39.getOtro_motivo());
			Utilidades.seleccionarRadio(rdbMedicamento_administrado, ficha_epidemiologia_n39.getMedicamento_administrado());
			tbxCual_otro_suministrado.setValue(ficha_epidemiologia_n39.getCual_otro_suministrado());
			Utilidades.seleccionarRadio(rdbNivel_de_complegidad_de_la_institucion_tratante, ficha_epidemiologia_n39.getNivel_de_complegidad_de_la_institucion_tratante());
			Utilidades.seleccionarRadio(rdbClasificacion_final_de_sifilis, ficha_epidemiologia_n39.getClasificacion_final_de_sifilis());
			Utilidades.seleccionarRadio(rdbDiagnostico_de_contactos, ficha_epidemiologia_n39.getDiagnostico_de_contactos());
			Utilidades.seleccionarRadio(rdbTratamiento_de_contactos, ficha_epidemiologia_n39.getTratamiento_de_contactos());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n39 ficha_epidemiologia_n39 = (Ficha_epidemiologia_n39)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n39);
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
	public Ficha_epidemiologia_n39 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n39> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n39.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n39 ficha_n39 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n39;
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
		paramRequest.put("ficha_epidemiologia", IFichas_epidemiologicas.SIFILIS_GESTIONAL);

		//log.info("codigo_ficha"+tbxCodigo_ficha.getValue());
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
	
}