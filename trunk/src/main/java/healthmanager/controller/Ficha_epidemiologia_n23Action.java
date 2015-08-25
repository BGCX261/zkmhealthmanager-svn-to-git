/*
 * ficha_epidemiologia_n23Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n23;
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

public class Ficha_epidemiologia_n23Action extends FichaEpidemiologiaModel< Ficha_epidemiologia_n23>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n23Action.class);
	
	
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
	@View private Radiogroup rdbTipo_de_agresion;
	@View private Textbox tbxCual_otro;
	@View private Radiogroup rdbTipo_de_lesion;
	@View private Radiogroup rdbProfundidad;
	@View private Checkbox chbCabeza_cara_cuello;
	@View private Checkbox chbMano_dedo;
	@View private Checkbox chbTronco;
	@View private Checkbox chbMiembro_superior;
	@View private Checkbox chbMiembro_inferior;
	@View private Checkbox chbDeconocido;
	@View private Datebox dtbxFecha_de_agresion;
	@View private Radiogroup rdbLugar_de_exposicion;
	@View private Radiogroup rdbTipo_de_agresor;
	@View private Radiogroup rdbAnimal_con_rabia;
	@View private Radiogroup rdbPrueba_diagnostica;
	@View private Textbox tbxCual_otra;
	@View private Checkbox chbFiebre;
	@View private Checkbox chbDisfagia;
	@View private Checkbox chbAerofobia;
	@View private Checkbox chbHiporexia;
	@View private Checkbox chbOdinofagia;
	@View private Checkbox chbHidrofobia;
	@View private Checkbox chbCefalea;
	@View private Checkbox chbArreflexia_hiporreflexi;
	@View private Checkbox chbDisnea;
	@View private Checkbox chbVomito;
	@View private Checkbox chbPsicosis_alucinacion;
	@View private Checkbox chbDificultad_respiratoria;
	@View private Checkbox chbParesias;
	@View private Checkbox chbExpresion_de_terror;
	@View private Checkbox chbParestesias;
	@View private Checkbox chbCricis_de_llanto;
	@View private Checkbox chbDolor_neuropatic;
	@View private Checkbox chbSialorrea;
	@View private Checkbox chbOtro;
	@View private Textbox tbxCual_otro2;
	@View private Radiogroup rdbTipo_de_muestra;
	@View private Textbox tbxCual_otro3;
	@View private Radiogroup rdbDestino_de_muestra;
	@View private Radiogroup rdbPrueba_diagnostica_confirm;
	@View private Radiogroup rdbResultado;
	@View private Radiogroup rdbIdentificacion_variante;
	@View private Radiogroup rdbVariante_identificada;
	@View private Textbox tbxCual_otro4;
	@View private Radiogroup rdbAplicacion_de_suero;
	@View private Datebox dtbxFecha_de_aplicacion;
	@View private Radiogroup rdbAplicacion_de_vacuna;
	@View private Intbox ibxNumero_de_dosis;
	@View private Datebox dtbxFecha_de_primera_dosis;
	@View private Datebox dtbxFecha_ultima_dosis;
	@View private Textbox tbxPersonal_salud;
	@View private Textbox tbxAmigos;
	@View private Textbox tbxFamiliares;
	@View private Textbox tbxOtros;
	@View private Checkbox chbExposiciones_leves;
	@View private Checkbox chbExposiciones_graves;
	@View private Intbox ibxNum_exposiciones;
	@View private Intbox ibxSuero;
	@View private Intbox ibxVacuna;
	@View private Textbox tbxDiligenciado_por;
	@View private Textbox tbxTelefono_de_contacto;
	
	@View private Textbox tbxCual_otro_10;
	@View private Textbox tbxCual_otro_11;
	
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
			Ficha_epidemiologia_n23 ficha = new Ficha_epidemiologia_n23();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n23) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 23-------> "+ficha);
			
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
				dtbxFecha_de_aplicacion.setValue(null);
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
			
			List<Ficha_epidemiologia_n23> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
					Ficha_epidemiologia_n23.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n23 ficha_epidemiologia_n23 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n23, this));
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
		
		final Ficha_epidemiologia_n23 ficha_epidemiologia_n23 = (Ficha_epidemiologia_n23)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n23.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n23.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n23.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n23);
			}
		});
		hbox.appendChild(img);
	
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n23 obtenerFichaEpidemiologia(){
				
				Ficha_epidemiologia_n23 ficha_epidemiologia_n23 = new Ficha_epidemiologia_n23();
				ficha_epidemiologia_n23.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n23.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n23.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n23.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n23.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n23.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n23.setTipo_de_agresion(rdbTipo_de_agresion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setCual_otro(tbxCual_otro.getValue());
				ficha_epidemiologia_n23.setTipo_de_lesion(rdbTipo_de_lesion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setProfundidad(rdbProfundidad.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setCabeza_cara_cuello(chbCabeza_cara_cuello.isChecked());
				ficha_epidemiologia_n23.setMano_dedo(chbMano_dedo.isChecked());
				ficha_epidemiologia_n23.setTronco(chbTronco.isChecked());
				ficha_epidemiologia_n23.setMiembro_superior(chbMiembro_superior.isChecked());
				ficha_epidemiologia_n23.setMiembro_inferior(chbMiembro_inferior.isChecked());
				ficha_epidemiologia_n23.setDeconocido(chbDeconocido.isChecked());
				ficha_epidemiologia_n23.setFecha_de_agresion(new Timestamp(dtbxFecha_de_agresion.getValue().getTime()));
				ficha_epidemiologia_n23.setLugar_de_exposicion(rdbLugar_de_exposicion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setTipo_de_agresor(rdbTipo_de_agresor.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setAnimal_con_rabia(rdbAnimal_con_rabia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setPrueba_diagnostica(rdbPrueba_diagnostica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setCual_otra(tbxCual_otra.getValue());
				ficha_epidemiologia_n23.setFiebre(chbFiebre.isChecked());
				ficha_epidemiologia_n23.setDisfagia(chbDisfagia.isChecked());
				ficha_epidemiologia_n23.setAerofobia(chbAerofobia.isChecked());
				ficha_epidemiologia_n23.setHiporexia(chbHiporexia.isChecked());
				ficha_epidemiologia_n23.setOdinofagia(chbOdinofagia.isChecked());
				ficha_epidemiologia_n23.setHidrofobia(chbHidrofobia.isChecked());
				ficha_epidemiologia_n23.setCefalea(chbCefalea.isChecked());
				ficha_epidemiologia_n23.setArreflexia_hiporreflexi(chbArreflexia_hiporreflexi.isChecked());
				ficha_epidemiologia_n23.setDisnea(chbDisnea.isChecked());
				ficha_epidemiologia_n23.setVomito(chbVomito.isChecked());
				ficha_epidemiologia_n23.setPsicosis_alucinacion(chbPsicosis_alucinacion.isChecked());
				ficha_epidemiologia_n23.setDificultad_respiratoria(chbDificultad_respiratoria.isChecked());
				ficha_epidemiologia_n23.setParesias(chbParesias.isChecked());
				ficha_epidemiologia_n23.setExpresion_de_terror(chbExpresion_de_terror.isChecked());
				ficha_epidemiologia_n23.setParestesias(chbParestesias.isChecked());
				ficha_epidemiologia_n23.setCricis_de_llanto(chbCricis_de_llanto.isChecked());
				ficha_epidemiologia_n23.setDolor_neuropatic(chbDolor_neuropatic.isChecked());
				ficha_epidemiologia_n23.setSialorrea(chbSialorrea.isChecked());
				ficha_epidemiologia_n23.setOtro(chbOtro.isChecked());
				ficha_epidemiologia_n23.setCual_otro2(tbxCual_otro2.getValue());
				ficha_epidemiologia_n23.setTipo_de_muestra(rdbTipo_de_muestra.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setCual_otro3(tbxCual_otro3.getValue());
				ficha_epidemiologia_n23.setDestino_de_muestra(rdbDestino_de_muestra.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setPrueba_diagnostica_confirm(rdbPrueba_diagnostica_confirm.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setResultado(rdbResultado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setIdentificacion_variante(rdbIdentificacion_variante.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setVariante_identificada(rdbVariante_identificada.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n23.setCual_otro4(tbxCual_otro4.getValue());
				ficha_epidemiologia_n23.setAplicacion_de_suero(rdbAplicacion_de_suero.getSelectedItem().getValue().toString());
				if(dtbxFecha_de_aplicacion.getValue() != null){
					ficha_epidemiologia_n23.setFecha_de_aplicacion(new Timestamp(dtbxFecha_de_aplicacion.getValue().getTime()));					
				}
				
				if(dtbxFecha_de_primera_dosis.getValue() != null){
					ficha_epidemiologia_n23.setFecha_de_primera_dosis(new Timestamp(dtbxFecha_de_primera_dosis.getValue().getTime()));					
				}
				
				ficha_epidemiologia_n23.setNumero_de_dosis((ibxNumero_de_dosis.getValue()!=null?ibxNumero_de_dosis.getValue():0));
				if(dtbxFecha_ultima_dosis.getValue() != null){
					ficha_epidemiologia_n23.setFecha_ultima_dosis(new Timestamp(dtbxFecha_ultima_dosis.getValue().getTime()));					
				}
				ficha_epidemiologia_n23.setPersonal_salud(tbxPersonal_salud.getValue());
				ficha_epidemiologia_n23.setAmigos(tbxAmigos.getValue());
				ficha_epidemiologia_n23.setFamiliares(tbxFamiliares.getValue());
				ficha_epidemiologia_n23.setOtros(tbxOtros.getValue());
				ficha_epidemiologia_n23.setExposiciones_leves(chbExposiciones_leves.isChecked());
				ficha_epidemiologia_n23.setExposiciones_graves(chbExposiciones_graves.isChecked());
				ficha_epidemiologia_n23.setNum_exposiciones((ibxNum_exposiciones.getValue()!=null?ibxNum_exposiciones.getValue():0));
				ficha_epidemiologia_n23.setSuero((ibxSuero.getValue()!=null?ibxSuero.getValue():0));
				ficha_epidemiologia_n23.setVacuna((ibxVacuna.getValue()!=null?ibxVacuna.getValue():0));
				ficha_epidemiologia_n23.setDiligenciado_por(tbxDiligenciado_por.getValue());
				ficha_epidemiologia_n23.setTelefono_de_contacto(tbxTelefono_de_contacto.getValue());
				ficha_epidemiologia_n23.setCual_otro_11(tbxCual_otro_11.getValue());
				ficha_epidemiologia_n23.setCual_otro_10(tbxCual_otro_10.getValue());
				ficha_epidemiologia_n23.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n23.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n23.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n23.setDelete_date(null);
				ficha_epidemiologia_n23.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n23.setDelete_user(null);
				
				
				return ficha_epidemiologia_n23;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n23 obj)throws Exception{
		Ficha_epidemiologia_n23 ficha_epidemiologia_n23 = (Ficha_epidemiologia_n23)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n23.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n23.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n23.getFecha_inicial());
			tbxIdentificacion.setValue(ficha_epidemiologia_n23.getIdentificacion());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbTipo_de_agresion, ficha_epidemiologia_n23.getTipo_de_agresion());
			tbxCual_otro.setValue(ficha_epidemiologia_n23.getCual_otro());
			Utilidades.seleccionarRadio(rdbTipo_de_lesion, ficha_epidemiologia_n23.getTipo_de_lesion());
			Utilidades.seleccionarRadio(rdbProfundidad, ficha_epidemiologia_n23.getProfundidad());
			chbCabeza_cara_cuello.setChecked(ficha_epidemiologia_n23.getCabeza_cara_cuello());
			chbMano_dedo.setChecked(ficha_epidemiologia_n23.getMano_dedo());
			chbTronco.setChecked(ficha_epidemiologia_n23.getTronco());
			chbMiembro_superior.setChecked(ficha_epidemiologia_n23.getMiembro_superior());
			chbMiembro_inferior.setChecked(ficha_epidemiologia_n23.getMiembro_inferior());
			chbDeconocido.setChecked(ficha_epidemiologia_n23.getDeconocido());
			dtbxFecha_de_agresion.setValue(ficha_epidemiologia_n23.getFecha_de_agresion());
			Utilidades.seleccionarRadio(rdbLugar_de_exposicion, ficha_epidemiologia_n23.getLugar_de_exposicion());
			Utilidades.seleccionarRadio(rdbTipo_de_agresor, ficha_epidemiologia_n23.getTipo_de_agresor());
			Utilidades.seleccionarRadio(rdbAnimal_con_rabia, ficha_epidemiologia_n23.getAnimal_con_rabia());
			Utilidades.seleccionarRadio(rdbPrueba_diagnostica, ficha_epidemiologia_n23.getPrueba_diagnostica());
			tbxCual_otra.setValue(ficha_epidemiologia_n23.getCual_otra());
			chbFiebre.setChecked(ficha_epidemiologia_n23.getFiebre());
			chbDisfagia.setChecked(ficha_epidemiologia_n23.getDisfagia());
			chbAerofobia.setChecked(ficha_epidemiologia_n23.getAerofobia());
			chbHiporexia.setChecked(ficha_epidemiologia_n23.getHiporexia());
			chbOdinofagia.setChecked(ficha_epidemiologia_n23.getOdinofagia());
			chbHidrofobia.setChecked(ficha_epidemiologia_n23.getHidrofobia());
			chbCefalea.setChecked(ficha_epidemiologia_n23.getCefalea());
			chbArreflexia_hiporreflexi.setChecked(ficha_epidemiologia_n23.getArreflexia_hiporreflexi());
			chbDisnea.setChecked(ficha_epidemiologia_n23.getDisnea());
			chbVomito.setChecked(ficha_epidemiologia_n23.getVomito());
			chbPsicosis_alucinacion.setChecked(ficha_epidemiologia_n23.getPsicosis_alucinacion());
			chbDificultad_respiratoria.setChecked(ficha_epidemiologia_n23.getDificultad_respiratoria());
			chbParesias.setChecked(ficha_epidemiologia_n23.getParesias());
			chbExpresion_de_terror.setChecked(ficha_epidemiologia_n23.getExpresion_de_terror());
			chbParestesias.setChecked(ficha_epidemiologia_n23.getParestesias());
			chbCricis_de_llanto.setChecked(ficha_epidemiologia_n23.getCricis_de_llanto());
			chbDolor_neuropatic.setChecked(ficha_epidemiologia_n23.getDolor_neuropatic());
			chbSialorrea.setChecked(ficha_epidemiologia_n23.getSialorrea());
			chbOtro.setChecked(ficha_epidemiologia_n23.getOtro());
			tbxCual_otro2.setValue(ficha_epidemiologia_n23.getCual_otro2());
			Utilidades.seleccionarRadio(rdbTipo_de_muestra, ficha_epidemiologia_n23.getTipo_de_muestra());
			tbxCual_otro3.setValue(ficha_epidemiologia_n23.getCual_otro3());
			Utilidades.seleccionarRadio(rdbDestino_de_muestra, ficha_epidemiologia_n23.getDestino_de_muestra());
			Utilidades.seleccionarRadio(rdbPrueba_diagnostica_confirm, ficha_epidemiologia_n23.getPrueba_diagnostica_confirm());
			Utilidades.seleccionarRadio(rdbResultado, ficha_epidemiologia_n23.getResultado());
			Utilidades.seleccionarRadio(rdbIdentificacion_variante, ficha_epidemiologia_n23.getIdentificacion_variante());
			Utilidades.seleccionarRadio(rdbVariante_identificada, ficha_epidemiologia_n23.getVariante_identificada());
			tbxCual_otro4.setValue(ficha_epidemiologia_n23.getCual_otro4());
			Utilidades.seleccionarRadio(rdbAplicacion_de_suero, ficha_epidemiologia_n23.getAplicacion_de_suero());
			dtbxFecha_de_aplicacion.setValue(ficha_epidemiologia_n23.getFecha_de_aplicacion());
			Utilidades.seleccionarRadio(rdbAplicacion_de_vacuna, ficha_epidemiologia_n23.getAplicacion_de_vacuna());
			ibxNumero_de_dosis.setValue(ficha_epidemiologia_n23.getNumero_de_dosis());
			dtbxFecha_de_primera_dosis.setValue(ficha_epidemiologia_n23.getFecha_de_primera_dosis());
			dtbxFecha_ultima_dosis.setValue(ficha_epidemiologia_n23.getFecha_ultima_dosis());
			tbxPersonal_salud.setValue(ficha_epidemiologia_n23.getPersonal_salud());
			tbxAmigos.setValue(ficha_epidemiologia_n23.getAmigos());
			tbxFamiliares.setValue(ficha_epidemiologia_n23.getFamiliares());
			tbxOtros.setValue(ficha_epidemiologia_n23.getOtros());
			chbExposiciones_leves.setChecked(ficha_epidemiologia_n23.getExposiciones_leves());
			chbExposiciones_graves.setChecked(ficha_epidemiologia_n23.getExposiciones_graves());
			ibxNum_exposiciones.setValue(ficha_epidemiologia_n23.getNum_exposiciones());
			ibxSuero.setValue(ficha_epidemiologia_n23.getSuero());
			ibxVacuna.setValue(ficha_epidemiologia_n23.getVacuna());
			tbxDiligenciado_por.setValue(ficha_epidemiologia_n23.getDiligenciado_por());
			tbxTelefono_de_contacto.setValue(ficha_epidemiologia_n23.getTelefono_de_contacto());
			tbxCual_otro_10.setValue(ficha_epidemiologia_n23.getCual_otro_10());
			tbxCual_otro_11.setValue(ficha_epidemiologia_n23.getCual_otro_11());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n23 ficha_epidemiologia_n23 = (Ficha_epidemiologia_n23)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n23);
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
		//log.info("admision"+admision);
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
	public Ficha_epidemiologia_n23 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n23> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n23.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					//log.info("..............");
					Ficha_epidemiologia_n23 ficha_n23 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n23;
				}else{

					return null;
				}
	}
	
}