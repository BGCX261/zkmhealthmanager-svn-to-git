/*
 * ficha_epidemiologia_n18Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n18;
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

public class Ficha_epidemiologia_n18Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n18>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n18Action.class);
	
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
	@View private Textbox tbxNombre_padre_o_madre;
	@View private Datebox dtbxFecha_inicio_invest;
	@View private Radiogroup rdbCaso_identificado_por;
	@View private Radiogroup rdbContacto_caso_confirm;
	@View private Radiogroup rdbTiene_carnet_vacun;
	@View private Radiogroup rdbDosis_aplicadas;
	@View private Radiogroup rdbTipo_de_vacuna;
	@View private Textbox tbxCual;
	@View private Datebox dtbxFecha_ultima_dosis;
	@View private Radiogroup rdbEtapa_enfermedad;
	@View private Radiogroup rdbTos;
	@View private Intbox ibxDuracion_tos;
	@View private Radiogroup rdbTos_paroxistica;
	@View private Radiogroup rdbEstridor;
	@View private Radiogroup rdbApnea;
	@View private Radiogroup rdbFiebre;
	@View private Radiogroup rdbVomito_postusivo;
	@View private Radiogroup rdbComplicaciones;
	@View private Radiogroup rdbTipo_de_complicacion;
	@View private Textbox tbxCual_otro;
	@View private Radiogroup rdbTratamiento_antibitico;
	@View private Textbox tbxTipo_antibitico;
	@View private Intbox ibxDuracion_del_tratamiento;
	@View private Datebox dtbxFecha_del_toma;
	@View private Datebox dtbxFecha_del_recepcion;
	@View private Checkbox chbMuestra;
	@View private Checkbox chbPrueba;
	@View private Textbox tbxAgente;
	@View private Textbox tbxResultado;
	@View private Datebox dtbxFecha_del_resultado;
	@View private Datebox dtbxFecha_toma2;
	@View private Datebox dtbxFecha_del_recepcion2;
	@View private Checkbox chbMuestra2;
	@View private Checkbox chbPrueba2;
	@View private Textbox tbxAgente2;
	@View private Textbox tbxResultado2;
	@View private Datebox dtbxFecha_del_resultado2;
	@View private Datebox dtbxFecha_toma3;
	@View private Datebox dtbxFecha_del_recepcion3;
	@View private Checkbox chbMuestra3;
	@View private Checkbox chbPrueba3;
	@View private Textbox tbxAgente3;
	@View private Textbox tbxResultado3;
	@View private Datebox dtbxFecha_del_resultado3;
	@View private Radiogroup rdbInvestigacion_de_campo;
	@View private Datebox dtbxFecha_operacion_barrido;
	@View private Intbox ibxNumero_de_contactos;
	@View private Radiogroup rdbQuimioprofilaxis;
	@View private Intbox ibxTotal_poblacion1;
	@View private Intbox ibxDpt_1;
	@View private Intbox ibxDpt_2;
	@View private Intbox ibxDpt_3;
	@View private Intbox ibxRef_1;
	@View private Intbox ibxRef_2;
	@View private Intbox ibxTotal;
	@View private Intbox ibxTotal_poblacion2;
	@View private Intbox ibxDpt_1_2;
	@View private Intbox ibxDpt_2_2;
	@View private Intbox ibxDpt_3_2;
	@View private Intbox ibxRef_1_2;
	@View private Intbox ibxRef_2_2;
	@View private Intbox ibxTotal_2;
	@View private Intbox ibxTotal_poblacion3;
	@View private Intbox ibxDpt_1_3;
	@View private Intbox ibxDpt_2_3;
	@View private Intbox ibxDpt_3_3;
	@View private Intbox ibxRef_1_3;
	@View private Intbox ibxRef_2_3;
	@View private Intbox ibxTotal_3;
	@View private Intbox ibxTotal_poblacion4;
	@View private Intbox ibxDpt_1_4;
	@View private Intbox ibxDpt_2_4;
	@View private Intbox ibxDpt_3_4;
	@View private Intbox ibxRef_1_4;
	@View private Intbox ibxRef_2_4;
	@View private Intbox ibxTotal_4;
	@View private Textbox tbxMunicipios_veredad_vacun;
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
			Ficha_epidemiologia_n18 ficha = new Ficha_epidemiologia_n18();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n18) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 18-------> "+ficha);
			
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
			
			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
			
			List<Ficha_epidemiologia_n18> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService()
					.listar(Ficha_epidemiologia_n18.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n18 ficha_epidemiologia_n18 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n18, this));
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
		
		final Ficha_epidemiologia_n18 ficha_epidemiologia_n18 = (Ficha_epidemiologia_n18)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n18.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n18.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n18.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n18);
			}
		});
		hbox.appendChild(img);
		
		
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n18 obtenerFichaEpidemiologia(){
				
				Ficha_epidemiologia_n18 ficha_epidemiologia_n18 = new Ficha_epidemiologia_n18();
				ficha_epidemiologia_n18.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n18.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n18.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n18.setCodigo_diagnostico("A001");
				ficha_epidemiologia_n18.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n18.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n18.setNombre_padre_o_madre(tbxNombre_padre_o_madre.getValue());
				ficha_epidemiologia_n18.setFecha_inicio_invest(new Timestamp(dtbxFecha_inicio_invest.getValue().getTime()));
				ficha_epidemiologia_n18.setCaso_identificado_por(rdbCaso_identificado_por.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setContacto_caso_confirm(rdbContacto_caso_confirm.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setTiene_carnet_vacun(rdbTiene_carnet_vacun.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setDosis_aplicadas(rdbDosis_aplicadas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setTipo_de_vacuna(rdbTipo_de_vacuna.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setCual(tbxCual.getValue());
				ficha_epidemiologia_n18.setFecha_ultima_dosis(new Timestamp(dtbxFecha_ultima_dosis.getValue().getTime()));
				ficha_epidemiologia_n18.setEtapa_enfermedad(rdbEtapa_enfermedad.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setTos(rdbTos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setDuracion_tos((ibxDuracion_tos.getValue()!=null?ibxDuracion_tos.getValue():0));
				ficha_epidemiologia_n18.setTos_paroxistica(rdbTos_paroxistica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setEstridor(rdbEstridor.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setApnea(rdbApnea.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setFiebre(rdbFiebre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setVomito_postusivo(rdbVomito_postusivo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setComplicaciones(rdbComplicaciones.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setTipo_de_complicacion(rdbTipo_de_complicacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setCual_otro(tbxCual_otro.getValue());
				ficha_epidemiologia_n18.setTratamiento_antibitico(rdbTratamiento_antibitico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setTipo_antibitico(tbxTipo_antibitico.getValue());
				ficha_epidemiologia_n18.setDuracion_del_tratamiento((ibxDuracion_del_tratamiento.getValue()!=null?ibxDuracion_del_tratamiento.getValue():0));
				ficha_epidemiologia_n18.setFecha_del_toma(new Timestamp(dtbxFecha_del_toma.getValue().getTime()));
				ficha_epidemiologia_n18.setFecha_del_recepcion(new Timestamp(dtbxFecha_del_recepcion.getValue().getTime()));
				ficha_epidemiologia_n18.setMuestra(chbMuestra.isChecked());
				ficha_epidemiologia_n18.setPrueba(chbPrueba.isChecked());
				ficha_epidemiologia_n18.setAgente(tbxAgente.getValue());
				ficha_epidemiologia_n18.setResultado(tbxResultado.getValue());
				ficha_epidemiologia_n18.setFecha_del_resultado(new Timestamp(dtbxFecha_del_resultado.getValue().getTime()));
				ficha_epidemiologia_n18.setFecha_toma2(new Timestamp(dtbxFecha_toma2.getValue().getTime()));
				ficha_epidemiologia_n18.setFecha_del_recepcion2(new Timestamp(dtbxFecha_del_recepcion2.getValue().getTime()));
				ficha_epidemiologia_n18.setMuestra2(chbMuestra2.isChecked());
				ficha_epidemiologia_n18.setPrueba2(chbPrueba2.isChecked());
				ficha_epidemiologia_n18.setAgente2(tbxAgente2.getValue());
				ficha_epidemiologia_n18.setResultado2(tbxResultado2.getValue());
				ficha_epidemiologia_n18.setFecha_del_resultado2(new Timestamp(dtbxFecha_del_resultado2.getValue().getTime()));
				ficha_epidemiologia_n18.setFecha_toma3(new Timestamp(dtbxFecha_toma3.getValue().getTime()));
				ficha_epidemiologia_n18.setFecha_del_recepcion3(new Timestamp(dtbxFecha_del_recepcion3.getValue().getTime()));
				ficha_epidemiologia_n18.setMuestra3(chbMuestra3.isChecked());
				ficha_epidemiologia_n18.setPrueba3(chbPrueba3.isChecked());
				ficha_epidemiologia_n18.setAgente3(tbxAgente3.getValue());
				ficha_epidemiologia_n18.setResultado3(tbxResultado3.getValue());
				ficha_epidemiologia_n18.setFecha_del_resultado3(new Timestamp(dtbxFecha_del_resultado3.getValue().getTime()));
				ficha_epidemiologia_n18.setInvestigacion_de_campo(rdbInvestigacion_de_campo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setFecha_operacion_barrido(new Timestamp(dtbxFecha_operacion_barrido.getValue().getTime()));
				ficha_epidemiologia_n18.setNumero_de_contactos((ibxNumero_de_contactos.getValue()!=null?ibxNumero_de_contactos.getValue():0));
				ficha_epidemiologia_n18.setQuimioprofilaxis(rdbQuimioprofilaxis.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n18.setTotal_poblacion1((ibxTotal_poblacion1.getValue()!=null?ibxTotal_poblacion1.getValue():0));
				ficha_epidemiologia_n18.setDpt_1((ibxDpt_1.getValue()!=null?ibxDpt_1.getValue():0));
				ficha_epidemiologia_n18.setDpt_2((ibxDpt_2.getValue()!=null?ibxDpt_2.getValue():0));
				ficha_epidemiologia_n18.setDpt_3((ibxDpt_3.getValue()!=null?ibxDpt_3.getValue():0));
				ficha_epidemiologia_n18.setRef_1((ibxRef_1.getValue()!=null?ibxRef_1.getValue():0));
				ficha_epidemiologia_n18.setRef_2((ibxRef_2.getValue()!=null?ibxRef_2.getValue():0));
				ficha_epidemiologia_n18.setTotal((ibxTotal.getValue()!=null?ibxTotal.getValue():0));
				ficha_epidemiologia_n18.setTotal_poblacion2((ibxTotal_poblacion2.getValue()!=null?ibxTotal_poblacion2.getValue():0));
				ficha_epidemiologia_n18.setDpt_1_2((ibxDpt_1_2.getValue()!=null?ibxDpt_1_2.getValue():0));
				ficha_epidemiologia_n18.setDpt_2_2((ibxDpt_2_2.getValue()!=null?ibxDpt_2_2.getValue():0));
				ficha_epidemiologia_n18.setDpt_3_2((ibxDpt_3_2.getValue()!=null?ibxDpt_3_2.getValue():0));
				ficha_epidemiologia_n18.setRef_1_2((ibxRef_1_2.getValue()!=null?ibxRef_1_2.getValue():0));
				ficha_epidemiologia_n18.setRef_2_2((ibxRef_2_2.getValue()!=null?ibxRef_2_2.getValue():0));
				ficha_epidemiologia_n18.setTotal_2((ibxTotal_2.getValue()!=null?ibxTotal_2.getValue():0));
				ficha_epidemiologia_n18.setTotal_poblacion3((ibxTotal_poblacion3.getValue()!=null?ibxTotal_poblacion3.getValue():0));
				ficha_epidemiologia_n18.setDpt_1_3((ibxDpt_1_3.getValue()!=null?ibxDpt_1_3.getValue():0));
				ficha_epidemiologia_n18.setDpt_2_3((ibxDpt_2_3.getValue()!=null?ibxDpt_2_3.getValue():0));
				ficha_epidemiologia_n18.setDpt_3_3((ibxDpt_3_3.getValue()!=null?ibxDpt_3_3.getValue():0));
				ficha_epidemiologia_n18.setRef_1_3((ibxRef_1_3.getValue()!=null?ibxRef_1_3.getValue():0));
				ficha_epidemiologia_n18.setRef_2_3((ibxRef_2_3.getValue()!=null?ibxRef_2_3.getValue():0));
				ficha_epidemiologia_n18.setTotal_3((ibxTotal_3.getValue()!=null?ibxTotal_3.getValue():0));
				ficha_epidemiologia_n18.setTotal_poblacion4((ibxTotal_poblacion4.getValue()!=null?ibxTotal_poblacion4.getValue():0));
				ficha_epidemiologia_n18.setDpt_1_4((ibxDpt_1_4.getValue()!=null?ibxDpt_1_4.getValue():0));
				ficha_epidemiologia_n18.setDpt_2_4((ibxDpt_2_4.getValue()!=null?ibxDpt_2_4.getValue():0));
				ficha_epidemiologia_n18.setDpt_3_4((ibxDpt_3_4.getValue()!=null?ibxDpt_3_4.getValue():0));
				ficha_epidemiologia_n18.setRef_1_4((ibxRef_1_4.getValue()!=null?ibxRef_1_4.getValue():0));
				ficha_epidemiologia_n18.setRef_2_4((ibxRef_2_4.getValue()!=null?ibxRef_2_4.getValue():0));
				ficha_epidemiologia_n18.setTotal_4((ibxTotal_4.getValue()!=null?ibxTotal_4.getValue():0));
				ficha_epidemiologia_n18.setMunicipios_veredad_vacun(tbxMunicipios_veredad_vacun.getValue());
				ficha_epidemiologia_n18.setDiligenciado_por(tbxDiligenciado_por.getValue());
				ficha_epidemiologia_n18.setTelefono_de_contacto(tbxTelefono_de_contacto.getValue());
				ficha_epidemiologia_n18.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n18.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n18.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n18.setDelete_date(null);
				ficha_epidemiologia_n18.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n18.setDelete_user(null);
				
				return ficha_epidemiologia_n18;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n18 obj)throws Exception{
		Ficha_epidemiologia_n18 ficha_epidemiologia_n18 = (Ficha_epidemiologia_n18)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n18.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n18.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n18.getFecha_inicial());
			tbxIdentificacion.setValue(ficha_epidemiologia_n18.getIdentificacion());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			tbxNombre_padre_o_madre.setValue(ficha_epidemiologia_n18.getNombre_padre_o_madre());
			dtbxFecha_inicio_invest.setValue(ficha_epidemiologia_n18.getFecha_inicio_invest());
			Utilidades.seleccionarRadio(rdbCaso_identificado_por, ficha_epidemiologia_n18.getCaso_identificado_por());
			Utilidades.seleccionarRadio(rdbContacto_caso_confirm, ficha_epidemiologia_n18.getContacto_caso_confirm());
			Utilidades.seleccionarRadio(rdbTiene_carnet_vacun, ficha_epidemiologia_n18.getTiene_carnet_vacun());
			Utilidades.seleccionarRadio(rdbDosis_aplicadas, ficha_epidemiologia_n18.getDosis_aplicadas());
			Utilidades.seleccionarRadio(rdbTipo_de_vacuna, ficha_epidemiologia_n18.getTipo_de_vacuna());
			tbxCual.setValue(ficha_epidemiologia_n18.getCual());
			dtbxFecha_ultima_dosis.setValue(ficha_epidemiologia_n18.getFecha_ultima_dosis());
			Utilidades.seleccionarRadio(rdbEtapa_enfermedad, ficha_epidemiologia_n18.getEtapa_enfermedad());
			Utilidades.seleccionarRadio(rdbTos, ficha_epidemiologia_n18.getTos());
			ibxDuracion_tos.setValue(ficha_epidemiologia_n18.getDuracion_tos());
			Utilidades.seleccionarRadio(rdbTos_paroxistica, ficha_epidemiologia_n18.getTos_paroxistica());
			Utilidades.seleccionarRadio(rdbEstridor, ficha_epidemiologia_n18.getEstridor());
			Utilidades.seleccionarRadio(rdbApnea, ficha_epidemiologia_n18.getApnea());
			Utilidades.seleccionarRadio(rdbFiebre, ficha_epidemiologia_n18.getFiebre());
			Utilidades.seleccionarRadio(rdbVomito_postusivo, ficha_epidemiologia_n18.getVomito_postusivo());
			Utilidades.seleccionarRadio(rdbComplicaciones, ficha_epidemiologia_n18.getComplicaciones());
			Utilidades.seleccionarRadio(rdbTipo_de_complicacion, ficha_epidemiologia_n18.getTipo_de_complicacion());
			tbxCual_otro.setValue(ficha_epidemiologia_n18.getCual_otro());
			Utilidades.seleccionarRadio(rdbTratamiento_antibitico, ficha_epidemiologia_n18.getTratamiento_antibitico());
			tbxTipo_antibitico.setValue(ficha_epidemiologia_n18.getTipo_antibitico());
			ibxDuracion_del_tratamiento.setValue(ficha_epidemiologia_n18.getDuracion_del_tratamiento());
			dtbxFecha_del_toma.setValue(ficha_epidemiologia_n18.getFecha_del_toma());
			dtbxFecha_del_recepcion.setValue(ficha_epidemiologia_n18.getFecha_del_recepcion());
			chbMuestra.setChecked(ficha_epidemiologia_n18.getMuestra());
			chbPrueba.setChecked(ficha_epidemiologia_n18.getPrueba());
			tbxAgente.setValue(ficha_epidemiologia_n18.getAgente());
			tbxResultado.setValue(ficha_epidemiologia_n18.getResultado());
			dtbxFecha_del_resultado.setValue(ficha_epidemiologia_n18.getFecha_del_resultado());
			dtbxFecha_toma2.setValue(ficha_epidemiologia_n18.getFecha_toma2());
			dtbxFecha_del_recepcion2.setValue(ficha_epidemiologia_n18.getFecha_del_recepcion2());
			chbMuestra2.setChecked(ficha_epidemiologia_n18.getMuestra2());
			chbPrueba2.setChecked(ficha_epidemiologia_n18.getPrueba2());
			tbxAgente2.setValue(ficha_epidemiologia_n18.getAgente2());
			tbxResultado2.setValue(ficha_epidemiologia_n18.getResultado2());
			dtbxFecha_del_resultado2.setValue(ficha_epidemiologia_n18.getFecha_del_resultado2());
			dtbxFecha_toma3.setValue(ficha_epidemiologia_n18.getFecha_toma3());
			dtbxFecha_del_recepcion3.setValue(ficha_epidemiologia_n18.getFecha_del_recepcion3());
			chbMuestra3.setChecked(ficha_epidemiologia_n18.getMuestra3());
			chbPrueba3.setChecked(ficha_epidemiologia_n18.getPrueba3());
			tbxAgente3.setValue(ficha_epidemiologia_n18.getAgente3());
			tbxResultado3.setValue(ficha_epidemiologia_n18.getResultado3());
			dtbxFecha_del_resultado3.setValue(ficha_epidemiologia_n18.getFecha_del_resultado3());
			Utilidades.seleccionarRadio(rdbInvestigacion_de_campo, ficha_epidemiologia_n18.getInvestigacion_de_campo());
			dtbxFecha_operacion_barrido.setValue(ficha_epidemiologia_n18.getFecha_operacion_barrido());
			ibxNumero_de_contactos.setValue(ficha_epidemiologia_n18.getNumero_de_contactos());
			Utilidades.seleccionarRadio(rdbQuimioprofilaxis, ficha_epidemiologia_n18.getQuimioprofilaxis());
			ibxTotal_poblacion1.setValue(ficha_epidemiologia_n18.getTotal_poblacion1());
			ibxDpt_1.setValue(ficha_epidemiologia_n18.getDpt_1());
			ibxDpt_2.setValue(ficha_epidemiologia_n18.getDpt_2());
			ibxDpt_3.setValue(ficha_epidemiologia_n18.getDpt_3());
			ibxRef_1.setValue(ficha_epidemiologia_n18.getRef_1());
			ibxRef_2.setValue(ficha_epidemiologia_n18.getRef_2());
			ibxTotal.setValue(ficha_epidemiologia_n18.getTotal());
			ibxTotal_poblacion2.setValue(ficha_epidemiologia_n18.getTotal_poblacion2());
			ibxDpt_1_2.setValue(ficha_epidemiologia_n18.getDpt_1_2());
			ibxDpt_2_2.setValue(ficha_epidemiologia_n18.getDpt_2_2());
			ibxDpt_3_2.setValue(ficha_epidemiologia_n18.getDpt_3_2());
			ibxRef_1_2.setValue(ficha_epidemiologia_n18.getRef_1_2());
			ibxRef_2_2.setValue(ficha_epidemiologia_n18.getRef_2_2());
			ibxTotal_2.setValue(ficha_epidemiologia_n18.getTotal_2());
			ibxTotal_poblacion3.setValue(ficha_epidemiologia_n18.getTotal_poblacion3());
			ibxDpt_1_3.setValue(ficha_epidemiologia_n18.getDpt_1_3());
			ibxDpt_2_3.setValue(ficha_epidemiologia_n18.getDpt_2_3());
			ibxDpt_3_3.setValue(ficha_epidemiologia_n18.getDpt_3_3());
			ibxRef_1_3.setValue(ficha_epidemiologia_n18.getRef_1_3());
			ibxRef_2_3.setValue(ficha_epidemiologia_n18.getRef_2_3());
			ibxTotal_3.setValue(ficha_epidemiologia_n18.getTotal_3());
			ibxTotal_poblacion4.setValue(ficha_epidemiologia_n18.getTotal_poblacion4());
			ibxDpt_1_4.setValue(ficha_epidemiologia_n18.getDpt_1_4());
			ibxDpt_2_4.setValue(ficha_epidemiologia_n18.getDpt_2_4());
			ibxDpt_3_4.setValue(ficha_epidemiologia_n18.getDpt_3_4());
			ibxRef_1_4.setValue(ficha_epidemiologia_n18.getRef_1_4());
			ibxRef_2_4.setValue(ficha_epidemiologia_n18.getRef_2_4());
			ibxTotal_4.setValue(ficha_epidemiologia_n18.getTotal_4());
			tbxMunicipios_veredad_vacun.setValue(ficha_epidemiologia_n18.getMunicipios_veredad_vacun());
			tbxDiligenciado_por.setValue(ficha_epidemiologia_n18.getDiligenciado_por());
			tbxTelefono_de_contacto.setValue(ficha_epidemiologia_n18.getTelefono_de_contacto());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n18 ficha_epidemiologia_n18 = (Ficha_epidemiologia_n18)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n18);
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
	public Ficha_epidemiologia_n18 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n18> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n18.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n18 ficha_n18 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n18;
				}else{

					return null;
				}
	}
}