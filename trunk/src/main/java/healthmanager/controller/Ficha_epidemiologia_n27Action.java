/*
 * ficha_epidemiologia_n27Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n27;
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

public class Ficha_epidemiologia_n27Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n27>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n27Action.class);
	
	
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
	@View private Radiogroup rdbEstrato_socioeconomico;
	@View private Radiogroup rdbEscolaridad;
	@View private Checkbox chbAmputacion;
	@View private Checkbox chbLaceracion;
	@View private Checkbox chbContusion;
	@View private Checkbox chbQuemadura;
	@View private Radiogroup rdbClasifique_grado;
	@View private Radiogroup rdbClasifique_extension;
	@View private Checkbox chbOjos;
	@View private Checkbox chbOrejas;
	@View private Checkbox chbCara;
	@View private Checkbox chbCabeza_cuello;
	@View private Checkbox chbVia_aerea;
	@View private Checkbox chbTronco;
	@View private Checkbox chbAbdomen;
	@View private Checkbox chbExt_superiores;
	@View private Checkbox chbExt_inferiores;
	@View private Checkbox chbManos;
	@View private Checkbox chbDedos;
	@View private Checkbox chbGenitales;
	@View private Checkbox chbSin_dato;
	@View private Checkbox chbOtro;
	@View private Textbox tbxCual_sitio;
	@View private Radiogroup rdbCircunstancia;
	@View private Textbox tbxCual_circunstancia;
	@View private Radiogroup rdbLugar_evento;
	@View private Textbox tbxCual_lugar_evento;
	@View private Radiogroup rdbLesionado;
	@View private Radiogroup rdbAdulto_acompanante;
	@View private Radiogroup rdbTipo_artefacto;
	@View private Textbox tbxCual_tipo_artefacto;
	@View private Textbox tbxCodigo_medico;
	private final String[] idsExcluyentes = {};

	@View private Toolbarbutton btGuardar;

	@View private Row rowCircunstancia;
	@View private Row rowLugar_evento;
	@View private Row rowTipo_artefacto;
	
	@View private Label lbCual_sitio;
	@View private Label lbCual_circunstancia;
	@View private Label lbCual_lugar_evento;
	@View private Label lbCual_tipo_artefacto;

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
				Ficha_epidemiologia_n27 ficha = new Ficha_epidemiologia_n27();
				ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
				ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
				ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
				ficha = (Ficha_epidemiologia_n27) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
				
				//log.info("consultar ficha 2-------> "+ficha);
				
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
			
			List<Ficha_epidemiologia_n27> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n27.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n27 ficha_epidemiologia_n27 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n27, this));
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
		
		final Ficha_epidemiologia_n27 ficha_epidemiologia_n27 = (Ficha_epidemiologia_n27)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n27.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n27.getIdentificacion()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n27.getFecha_creacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n27);
			}
		});
		hbox.appendChild(img);
		
		
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n27 obtenerFichaEpidemiologia() {
					
				Ficha_epidemiologia_n27 ficha_epidemiologia_n27 = new Ficha_epidemiologia_n27();
				ficha_epidemiologia_n27.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n27.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n27.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n27.setIdentificacion(tbxIdentificacion.getValue());
				ficha_epidemiologia_n27.setFecha_creacion(new Timestamp(dtbxFecha_creacion.getValue().getTime()));
				ficha_epidemiologia_n27.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n27.setEstrato_socioeconomico(rdbEstrato_socioeconomico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n27.setEscolaridad(rdbEscolaridad.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n27.setAmputacion(chbAmputacion.isChecked());
				ficha_epidemiologia_n27.setLaceracion(chbLaceracion.isChecked());
				ficha_epidemiologia_n27.setContusion(chbContusion.isChecked());
				ficha_epidemiologia_n27.setQuemadura(chbQuemadura.isChecked());
				ficha_epidemiologia_n27.setClasifique_grado(rdbClasifique_grado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n27.setClasifique_extension(rdbClasifique_extension.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n27.setOjos(chbOjos.isChecked());
				ficha_epidemiologia_n27.setOrejas(chbOrejas.isChecked());
				ficha_epidemiologia_n27.setCara(chbCara.isChecked());
				ficha_epidemiologia_n27.setCabeza_cuello(chbCabeza_cuello.isChecked());
				ficha_epidemiologia_n27.setVia_aerea(chbVia_aerea.isChecked());
				ficha_epidemiologia_n27.setTronco(chbTronco.isChecked());
				ficha_epidemiologia_n27.setAbdomen(chbAbdomen.isChecked());
				ficha_epidemiologia_n27.setExt_superiores(chbExt_superiores.isChecked());
				ficha_epidemiologia_n27.setExt_inferiores(chbExt_inferiores.isChecked());
				ficha_epidemiologia_n27.setManos(chbManos.isChecked());
				ficha_epidemiologia_n27.setDedos(chbDedos.isChecked());
				ficha_epidemiologia_n27.setGenitales(chbGenitales.isChecked());
				ficha_epidemiologia_n27.setSin_dato(chbSin_dato.isChecked());
				ficha_epidemiologia_n27.setOtro(chbOtro.isChecked());
				ficha_epidemiologia_n27.setCual_sitio(tbxCual_sitio.getValue());
				ficha_epidemiologia_n27.setCircunstancia(rdbCircunstancia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n27.setCual_circunstancia(tbxCual_circunstancia.getValue());
				ficha_epidemiologia_n27.setLugar_evento(rdbLugar_evento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n27.setCual_lugar_evento(tbxCual_lugar_evento.getValue());
				ficha_epidemiologia_n27.setLesionado(rdbLesionado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n27.setAdulto_acompanante(rdbAdulto_acompanante.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n27.setTipo_artefacto(rdbTipo_artefacto.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n27.setCual_tipo_artefacto(tbxCual_tipo_artefacto.getValue());
				ficha_epidemiologia_n27.setCodigo_medico(tbxCodigo_medico.getValue());
				ficha_epidemiologia_n27.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n27.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n27.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n27.setDelete_date(null);
				ficha_epidemiologia_n27.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n27.setDelete_user(null);
				
				return ficha_epidemiologia_n27;
				 
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n27 obj)throws Exception{
		Ficha_epidemiologia_n27 ficha_epidemiologia_n27 = (Ficha_epidemiologia_n27)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n27.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n27.getIdentificacion());
			
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			dtbxFecha_creacion.setValue(ficha_epidemiologia_n27.getFecha_creacion());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n27.getCodigo_diagnostico());
			Utilidades.seleccionarRadio(rdbEstrato_socioeconomico, ficha_epidemiologia_n27.getEstrato_socioeconomico());
			Utilidades.seleccionarRadio(rdbEscolaridad, ficha_epidemiologia_n27.getEscolaridad());
			chbAmputacion.setChecked(ficha_epidemiologia_n27.getAmputacion());
			chbLaceracion.setChecked(ficha_epidemiologia_n27.getLaceracion());
			chbContusion.setChecked(ficha_epidemiologia_n27.getContusion());
			chbQuemadura.setChecked(ficha_epidemiologia_n27.getQuemadura());
			Utilidades.seleccionarRadio(rdbClasifique_grado, ficha_epidemiologia_n27.getClasifique_grado());
			Utilidades.seleccionarRadio(rdbClasifique_extension, ficha_epidemiologia_n27.getClasifique_extension());
			chbOjos.setChecked(ficha_epidemiologia_n27.getOjos());
			chbOrejas.setChecked(ficha_epidemiologia_n27.getOrejas());
			chbCara.setChecked(ficha_epidemiologia_n27.getCara());
			chbCabeza_cuello.setChecked(ficha_epidemiologia_n27.getCabeza_cuello());
			chbVia_aerea.setChecked(ficha_epidemiologia_n27.getVia_aerea());
			chbTronco.setChecked(ficha_epidemiologia_n27.getTronco());
			chbAbdomen.setChecked(ficha_epidemiologia_n27.getAbdomen());
			chbExt_superiores.setChecked(ficha_epidemiologia_n27.getExt_superiores());
			chbExt_inferiores.setChecked(ficha_epidemiologia_n27.getExt_inferiores());
			chbManos.setChecked(ficha_epidemiologia_n27.getManos());
			chbDedos.setChecked(ficha_epidemiologia_n27.getDedos());
			chbGenitales.setChecked(ficha_epidemiologia_n27.getGenitales());
			chbSin_dato.setChecked(ficha_epidemiologia_n27.getSin_dato());
			chbOtro.setChecked(ficha_epidemiologia_n27.getOtro());
			
			if(ficha_epidemiologia_n27.getOtro()){
				lbCual_sitio.setVisible(true);
				tbxCual_sitio.setVisible(true);
				tbxCual_sitio.setValue(ficha_epidemiologia_n27.getCual_sitio());
				
			}else{
				lbCual_sitio.setVisible(false);
				tbxCual_sitio.setVisible(false);
				
			}
			
			Utilidades.seleccionarRadio(rdbCircunstancia, ficha_epidemiologia_n27.getCircunstancia());
			
			if(ficha_epidemiologia_n27.getCircunstancia().equals("O")){
				rowCircunstancia.setVisible(true);
				lbCual_circunstancia.setVisible(true);
				tbxCual_circunstancia.setVisible(true);
				tbxCual_circunstancia.setValue(ficha_epidemiologia_n27.getCual_circunstancia());
				
			}else{
				rowCircunstancia.setVisible(false);
				lbCual_circunstancia.setVisible(false);
				tbxCual_circunstancia.setVisible(false);
				
			}
			
			Utilidades.seleccionarRadio(rdbLugar_evento, ficha_epidemiologia_n27.getLugar_evento());
			
			if(ficha_epidemiologia_n27.getLugar_evento().equals("O")){
				rowLugar_evento.setVisible(true);
				lbCual_lugar_evento.setVisible(true);
				tbxCual_lugar_evento.setVisible(true);
				tbxCual_lugar_evento.setValue(ficha_epidemiologia_n27.getCual_lugar_evento());
				
			}else{
				rowLugar_evento.setVisible(false);
				lbCual_lugar_evento.setVisible(false);
				tbxCual_lugar_evento.setVisible(false);
				
			}
			
			Utilidades.seleccionarRadio(rdbLesionado, ficha_epidemiologia_n27.getLesionado());
			Utilidades.seleccionarRadio(rdbAdulto_acompanante, ficha_epidemiologia_n27.getAdulto_acompanante());
			Utilidades.seleccionarRadio(rdbTipo_artefacto, ficha_epidemiologia_n27.getTipo_artefacto());
			
			if(ficha_epidemiologia_n27.getTipo_artefacto().equals("O")){
				rowTipo_artefacto.setVisible(true);
				lbCual_tipo_artefacto.setVisible(true);
				tbxCual_tipo_artefacto.setVisible(true);
				tbxCual_tipo_artefacto.setValue(ficha_epidemiologia_n27.getCual_tipo_artefacto());
				
			}else{
				rowTipo_artefacto.setVisible(false);
				lbCual_tipo_artefacto.setVisible(false);
				tbxCual_tipo_artefacto.setVisible(false);
				
			}
			
			tbxCodigo_medico.setValue(ficha_epidemiologia_n27.getCodigo_medico());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n27 ficha_epidemiologia_n27 = (Ficha_epidemiologia_n27)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n27);
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
	public Ficha_epidemiologia_n27 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n27> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n27.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n27 ficha_n27 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n27;
				}else{

					return null;
				}
	}
	
	
}