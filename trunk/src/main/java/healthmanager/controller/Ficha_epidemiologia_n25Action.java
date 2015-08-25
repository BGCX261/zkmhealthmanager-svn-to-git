/*
 * ficha_epidemiologia_n25Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n25;
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

public class Ficha_epidemiologia_n25Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n25>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n25Action.class);
	
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
	
	@View private Textbox tbxNombres_y_apellidos;
	@View private Textbox tbxTipo_identidad;
	@View private Textbox tbxIdentificacion;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	
	@View private Radiogroup rdbTipo_de_tuberculosis;
	@View private Radiogroup rdbLocalizacion_tuberculosis;
	@View private Radiogroup rdbSegun_ingreso;
	@View private Radiogroup rdbSegun_tipo_residencia;
	@View private Radiogroup rdbTiene_cicatriz;
	@View private Radiogroup rdbTiene_vacuna;
	@View private Radiogroup rdbEmbarazo;
	@View private Textbox tbxSemana_embarazo;
	@View private Radiogroup rdbRealizo_consejeria;
	@View private Radiogroup rdbRealizo_prueba;
	@View private Radiogroup rdbCoinfeccion;
	@View private Radiogroup rdbBaciloscopia;
	@View private Datebox dtbxFecha_de_baciloscopia;
	@View private Checkbox chbPrimera;
	@View private Checkbox chbSegunda;
	@View private Checkbox chbTercera;
	@View private Checkbox chbCuarta;
	@View private Datebox dtbxFecha_de_resultado;
	@View private Radiogroup rdbCultivo;
	@View private Datebox dtbxFecha_de_cultivo;
	@View private Checkbox chbPositivo;
	@View private Checkbox chbNegativo;
	@View private Datebox dtbxFecha_de_resultado2;
	@View private Radiogroup rdbHispatologia;
	@View private Datebox dtbxFecha_de_hispatologia;
	@View private Checkbox chbPositivo2;
	@View private Checkbox chbNegativo2;
	@View private Datebox dtbxFecha_de_resultado3;
	@View private Radiogroup rdbCuadro_clinico;
	@View private Radiogroup rdbNexo_epidemiologico;
	@View private Radiogroup rdbRadiologico;
	@View private Radiogroup rdbAdenosina_deaminasa;
	@View private Radiogroup rdbTuberculina;
	@View private Radiogroup rdbMetodo_de_captacion;
	@View private Radiogroup rdbPosible_funete_contagio;
	@View private Radiogroup rdbInvestigacion_de_campo;
	@View private Datebox dtbxFecha_de_realizacion;
//	@View private Textbox tbxAplicacion_de_vacuna;
//	@View private Textbox tbxNumero_de_dosis;
//	@View private Datebox dtbxFecha_de_primera_dosis;
//	@View private Datebox dtbxFecha_ultima_dosis;
	@View private Intbox ibxNumero_de_contactos;
	@View private Intbox ibxNumero_de_sintomaticos_id;
	@View private Intbox ibxNumero_de_sintomaticos_res;
	@View private Textbox tbxObservaciones;
	@View private Textbox tbxDiligenciado_por;
	@View private Textbox tbxTelefono_de_contacto;
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
			Ficha_epidemiologia_n25 ficha = new Ficha_epidemiologia_n25();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n25) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 25-------> "+ficha);
			
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
			dtbxFecha_de_realizacion.setValue(null);
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
			
			List<Ficha_epidemiologia_n25> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
					Ficha_epidemiologia_n25.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n25 ficha_epidemiologia_n25 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n25, this));
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
		
		final Ficha_epidemiologia_n25 ficha_epidemiologia_n25 = (Ficha_epidemiologia_n25)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n25.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n25.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n25.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n25);
			}
		});
		hbox.appendChild(img);
		
	
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n25 obtenerFichaEpidemiologia(){
				
				Ficha_epidemiologia_n25 ficha_epidemiologia_n25 = new Ficha_epidemiologia_n25();
				ficha_epidemiologia_n25.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n25.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n25.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n25.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n25.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n25.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n25.setTipo_de_tuberculosis(rdbTipo_de_tuberculosis.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setLocalizacion_tuberculosis(rdbLocalizacion_tuberculosis.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setSegun_ingreso(rdbSegun_ingreso.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setSegun_tipo_residencia(rdbSegun_tipo_residencia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setTiene_cicatriz(rdbTiene_cicatriz.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setTiene_vacuna(rdbTiene_vacuna.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setEmbarazo(rdbEmbarazo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setSemana_embarazo(tbxSemana_embarazo.getValue());
				ficha_epidemiologia_n25.setRealizo_consejeria(rdbRealizo_consejeria.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setRealizo_prueba(rdbRealizo_prueba.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setCoinfeccion(rdbCoinfeccion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setBaciloscopia(rdbBaciloscopia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setFecha_de_baciloscopia(new Timestamp(dtbxFecha_de_baciloscopia.getValue().getTime()));
				ficha_epidemiologia_n25.setPrimera(chbPrimera.isChecked());
				ficha_epidemiologia_n25.setSegunda(chbSegunda.isChecked());
				ficha_epidemiologia_n25.setTercera(chbTercera.isChecked());
				ficha_epidemiologia_n25.setCuarta(chbCuarta.isChecked());
				ficha_epidemiologia_n25.setFecha_de_resultado(new Timestamp(dtbxFecha_de_resultado.getValue().getTime()));
				ficha_epidemiologia_n25.setCultivo(rdbCultivo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setFecha_de_cultivo(new Timestamp(dtbxFecha_de_cultivo.getValue().getTime()));
				ficha_epidemiologia_n25.setPositivo(chbPositivo.isChecked());
				ficha_epidemiologia_n25.setNegativo(chbNegativo.isChecked());
				ficha_epidemiologia_n25.setFecha_de_resultado2(new Timestamp(dtbxFecha_de_resultado2.getValue().getTime()));
				ficha_epidemiologia_n25.setHispatologia(rdbHispatologia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setFecha_de_hispatologia(new Timestamp(dtbxFecha_de_hispatologia.getValue().getTime()));
				ficha_epidemiologia_n25.setPositivo2(chbPositivo2.isChecked());
				ficha_epidemiologia_n25.setNegativo2(chbNegativo2.isChecked());
				ficha_epidemiologia_n25.setFecha_de_resultado3(new Timestamp(dtbxFecha_de_resultado3.getValue().getTime()));
				ficha_epidemiologia_n25.setCuadro_clinico(rdbCuadro_clinico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setNexo_epidemiologico(rdbNexo_epidemiologico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setRadiologico(rdbRadiologico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setAdenosina_deaminasa(rdbAdenosina_deaminasa.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setTuberculina(rdbTuberculina.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setMetodo_de_captacion(rdbMetodo_de_captacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setPosible_funete_contagio(rdbPosible_funete_contagio.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n25.setInvestigacion_de_campo(rdbInvestigacion_de_campo.getSelectedItem().getValue().toString());
				if(dtbxFecha_de_realizacion.getValue() != null){
					ficha_epidemiologia_n25.setFecha_de_realizacion(new Timestamp(dtbxFecha_de_realizacion.getValue().getTime()));
				}				
				
				ficha_epidemiologia_n25.setNumero_de_contactos((ibxNumero_de_contactos.getValue()!=null?ibxNumero_de_contactos.getValue():0));
				ficha_epidemiologia_n25.setNumero_de_sintomaticos_id((ibxNumero_de_sintomaticos_id.getValue()!=null?ibxNumero_de_sintomaticos_id.getValue():0));
				ficha_epidemiologia_n25.setNumero_de_sintomaticos_res((ibxNumero_de_sintomaticos_res.getValue()!=null?ibxNumero_de_sintomaticos_res.getValue():0));
				ficha_epidemiologia_n25.setObservaciones(tbxObservaciones.getValue());
				ficha_epidemiologia_n25.setDiligenciado_por(usuarios.getCodigo().toString());
				ficha_epidemiologia_n25.setTelefono_de_contacto(tbxTelefono_de_contacto.getValue());
				ficha_epidemiologia_n25.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n25.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n25.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n25.setDelete_date(null);
				ficha_epidemiologia_n25.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n25.setDelete_user(null);
				
				return ficha_epidemiologia_n25;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n25 obj)throws Exception{
		Ficha_epidemiologia_n25 ficha_epidemiologia_n25 = (Ficha_epidemiologia_n25)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n25.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n25.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n25.getFecha_inicial());
			tbxIdentificacion.setValue(ficha_epidemiologia_n25.getIdentificacion());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbTipo_de_tuberculosis, ficha_epidemiologia_n25.getTipo_de_tuberculosis());
			Utilidades.seleccionarRadio(rdbLocalizacion_tuberculosis, ficha_epidemiologia_n25.getLocalizacion_tuberculosis());
			Utilidades.seleccionarRadio(rdbSegun_ingreso, ficha_epidemiologia_n25.getSegun_ingreso());
			Utilidades.seleccionarRadio(rdbSegun_tipo_residencia, ficha_epidemiologia_n25.getSegun_tipo_residencia());
			Utilidades.seleccionarRadio(rdbTiene_cicatriz, ficha_epidemiologia_n25.getTiene_cicatriz());
			Utilidades.seleccionarRadio(rdbTiene_vacuna, ficha_epidemiologia_n25.getTiene_vacuna());
			Utilidades.seleccionarRadio(rdbEmbarazo, ficha_epidemiologia_n25.getEmbarazo());
			tbxSemana_embarazo.setValue(ficha_epidemiologia_n25.getSemana_embarazo());
			Utilidades.seleccionarRadio(rdbRealizo_consejeria, ficha_epidemiologia_n25.getRealizo_consejeria());
			Utilidades.seleccionarRadio(rdbRealizo_prueba, ficha_epidemiologia_n25.getRealizo_prueba());
			Utilidades.seleccionarRadio(rdbCoinfeccion, ficha_epidemiologia_n25.getCoinfeccion());
			Utilidades.seleccionarRadio(rdbBaciloscopia, ficha_epidemiologia_n25.getBaciloscopia());
			dtbxFecha_de_baciloscopia.setValue(ficha_epidemiologia_n25.getFecha_de_baciloscopia());
			chbPrimera.setChecked(ficha_epidemiologia_n25.getPrimera());
			chbSegunda.setChecked(ficha_epidemiologia_n25.getSegunda());
			chbTercera.setChecked(ficha_epidemiologia_n25.getTercera());
			chbCuarta.setChecked(ficha_epidemiologia_n25.getCuarta());
			dtbxFecha_de_resultado.setValue(ficha_epidemiologia_n25.getFecha_de_resultado());
			Utilidades.seleccionarRadio(rdbCultivo, ficha_epidemiologia_n25.getCultivo());
			dtbxFecha_de_cultivo.setValue(ficha_epidemiologia_n25.getFecha_de_cultivo());
			chbPositivo.setChecked(ficha_epidemiologia_n25.getPositivo());
			chbNegativo.setChecked(ficha_epidemiologia_n25.getNegativo());
			dtbxFecha_de_resultado2.setValue(ficha_epidemiologia_n25.getFecha_de_resultado2());
			Utilidades.seleccionarRadio(rdbHispatologia, ficha_epidemiologia_n25.getHispatologia());
			dtbxFecha_de_hispatologia.setValue(ficha_epidemiologia_n25.getFecha_de_hispatologia());
			chbPositivo2.setChecked(ficha_epidemiologia_n25.getPositivo2());
			chbNegativo2.setChecked(ficha_epidemiologia_n25.getNegativo2());
			dtbxFecha_de_resultado3.setValue(ficha_epidemiologia_n25.getFecha_de_resultado3());
			Utilidades.seleccionarRadio(rdbCuadro_clinico, ficha_epidemiologia_n25.getCuadro_clinico());
			Utilidades.seleccionarRadio(rdbNexo_epidemiologico, ficha_epidemiologia_n25.getNexo_epidemiologico());
			Utilidades.seleccionarRadio(rdbRadiologico, ficha_epidemiologia_n25.getRadiologico());
			Utilidades.seleccionarRadio(rdbAdenosina_deaminasa, ficha_epidemiologia_n25.getAdenosina_deaminasa());
			Utilidades.seleccionarRadio(rdbTuberculina, ficha_epidemiologia_n25.getTuberculina());
			Utilidades.seleccionarRadio(rdbMetodo_de_captacion, ficha_epidemiologia_n25.getMetodo_de_captacion());
			Utilidades.seleccionarRadio(rdbPosible_funete_contagio, ficha_epidemiologia_n25.getPosible_funete_contagio());
			Utilidades.seleccionarRadio(rdbInvestigacion_de_campo, ficha_epidemiologia_n25.getInvestigacion_de_campo());
			dtbxFecha_de_realizacion.setValue(ficha_epidemiologia_n25.getFecha_de_realizacion());
			ibxNumero_de_contactos.setValue(ficha_epidemiologia_n25.getNumero_de_contactos());
			ibxNumero_de_sintomaticos_id.setValue(ficha_epidemiologia_n25.getNumero_de_sintomaticos_id());
			ibxNumero_de_sintomaticos_res.setValue(ficha_epidemiologia_n25.getNumero_de_sintomaticos_res());
			tbxObservaciones.setValue(ficha_epidemiologia_n25.getObservaciones());
//			tbxDiligenciado_por.setValue(ficha_epidemiologia_n25.getDiligenciado_por());
//			tbxTelefono_de_contacto.setValue(ficha_epidemiologia_n25.getTelefono_de_contacto());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n25 ficha_epidemiologia_n25 = (Ficha_epidemiologia_n25)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n25);
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
	public Ficha_epidemiologia_n25 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n25> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n25.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n25 ficha_n25 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n25;
				}else{

					return null;
				}
	}
}