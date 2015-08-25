/*
 * ficha_epidemiologia_n20Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n20;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
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

public class Ficha_epidemiologia_n20Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n20>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n20Action.class);
	
	
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
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View private Datebox dtbxFecha_inicial;
	@View private Textbox tbxIdentificacion;
	@View private Radiogroup rdbVacuna_contra_rota;
	@View private Datebox dtbxFecha_aplicacion_1dosis;
	@View private Datebox dtbxFecha_aplicacion_2dosis;
	@View private Radiogroup rdbTiene_carnet;
	@View private Doublebox dbxPeso_al_nacer;
	@View private Radiogroup rdbRecibio_lecha_materna;
	@View private Intbox ibxCuanto_tiempo;
	@View private Radiogroup rdbAlimentacion_actual;
	@View private Radiogroup rdbFiebre;
	@View private Radiogroup rdbVomito;
	@View private Intbox ibxNumero_vomitos;
	@View private Datebox dtbxFecha_inicio_diarrea;
	@View private Intbox ibxNumero_de_deposiciones;
	@View private Datebox dtbxFecha_terminacion_diarrea;
	@View private Radiogroup rdbCaracteristica_heces;
	@View private Textbox tbxCual1;
	@View private Radiogroup rdbEstado_al_ingreso;
	@View private Radiogroup rdbGrado_de_deshidratacion;
	@View private Doublebox dbxPeso;
	@View private Doublebox dbxTalla;
	@View private Radiogroup rdbRecibio_antibiotico1;
	@View private Textbox tbxCual2;
	@View private Radiogroup rdbRecibio_antibiotico2;
	@View private Radiogroup rdbPresento_alguna_comp;
	@View private Radiogroup rdbRecibio_antib_durante;
	@View private Textbox tbxCual3;
	@View private Radiogroup rdbHospitalizacion_tratamiento;
	@View private Textbox tbxCual4;
	@View private Intbox ibxDuracion_hospitalizacion;
	@View private Intbox ibxDias_urgencia;
	@View private Intbox ibxDias_pediatra;
	@View private Intbox ibxCuidados_intensivos;
	@View private Datebox dtbxFecha_de_ingreso;
	@View private Radiogroup rdbMotivo_de_ingreso;
	@View private Radiogroup rdbCuadro_de_diarrea;
	@View private Textbox tbxDiagnostico_de_egreso;
	@View private Datebox dtbxFecha_de_recoleccion;
	@View private Datebox dtbxFecha_del_recepcion;
	@View private Datebox dtbxFecha_del_resultado;
	@View private Radiogroup rdbIdentificacion_de_rota;
	@View private Textbox tbxSerotipo_g;
	@View private Textbox tbxSerotipo_p;
	@View private Radiogroup rdbIdentificacion_de_bact;
	@View private Textbox tbxCuales;
	@View private Radiogroup rdbIdentificacion_de_parasitos;
	@View private Textbox tbxCuales2;
	@View private Radiogroup rdbAsiste_a_guarderia;
	@View private Textbox tbxCual6;
	@View private Radiogroup rdbPersonas_con_diarrea;
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
			Ficha_epidemiologia_n20 ficha = new Ficha_epidemiologia_n20();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n20) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 20-------> "+ficha);
			
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
			
			List<Ficha_epidemiologia_n20> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService()
					.listar(
							Ficha_epidemiologia_n20.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n20 ficha_epidemiologia_n20 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n20, this));
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
		
		final Ficha_epidemiologia_n20 ficha_epidemiologia_n20 = (Ficha_epidemiologia_n20)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n20.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n20.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n20.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n20);
			}
		});
		hbox.appendChild(img);
		
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n20 obtenerFichaEpidemiologia(){
				
				Ficha_epidemiologia_n20 ficha_epidemiologia_n20 = new Ficha_epidemiologia_n20();
				ficha_epidemiologia_n20.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n20.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n20.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n20.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n20.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n20.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n20.setVacuna_contra_rota(rdbVacuna_contra_rota.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setFecha_aplicacion_1dosis(new Timestamp(dtbxFecha_aplicacion_1dosis.getValue().getTime()));
				ficha_epidemiologia_n20.setFecha_aplicacion_2dosis(new Timestamp(dtbxFecha_aplicacion_2dosis.getValue().getTime()));
				ficha_epidemiologia_n20.setTiene_carnet(rdbTiene_carnet.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setPeso_al_nacer((dbxPeso_al_nacer.getValue()!=null?dbxPeso_al_nacer.getValue():0.00));
				ficha_epidemiologia_n20.setRecibio_lecha_materna(rdbRecibio_lecha_materna.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setCuanto_tiempo((ibxCuanto_tiempo.getValue()!=null?ibxCuanto_tiempo.getValue():0));
				ficha_epidemiologia_n20.setAlimentacion_actual(rdbAlimentacion_actual.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setFiebre(rdbFiebre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setVomito(rdbVomito.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setNumero_vomitos((ibxNumero_vomitos.getValue()!=null?ibxNumero_vomitos.getValue():0));
				ficha_epidemiologia_n20.setFecha_inicio_diarrea(new Timestamp(dtbxFecha_inicio_diarrea.getValue().getTime()));
				ficha_epidemiologia_n20.setNumero_de_deposiciones((ibxNumero_de_deposiciones.getValue()!=null?ibxNumero_de_deposiciones.getValue():0));
				ficha_epidemiologia_n20.setFecha_terminacion_diarrea(new Timestamp(dtbxFecha_terminacion_diarrea.getValue().getTime()));
				ficha_epidemiologia_n20.setCaracteristica_heces(rdbCaracteristica_heces.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setCual1(tbxCual1.getValue());
				ficha_epidemiologia_n20.setEstado_al_ingreso(rdbEstado_al_ingreso.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setGrado_de_deshidratacion(rdbGrado_de_deshidratacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setPeso((dbxPeso.getValue()!=null?dbxPeso.getValue():0.00));
				ficha_epidemiologia_n20.setTalla((dbxTalla.getValue()!=null?dbxTalla.getValue():0.00));
				ficha_epidemiologia_n20.setRecibio_antibiotico1(rdbRecibio_antibiotico1.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setCual2(tbxCual2.getValue());
				ficha_epidemiologia_n20.setRecibio_antibiotico2(rdbRecibio_antibiotico2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setPresento_alguna_comp(rdbPresento_alguna_comp.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setCual3(tbxCual3.getValue());
				ficha_epidemiologia_n20.setRecibio_antib_durante(rdbRecibio_antib_durante.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setCual4(tbxCual4.getValue());
				ficha_epidemiologia_n20.setHospitalizacion_tratamiento(rdbHospitalizacion_tratamiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setDuracion_hospitalizacion((ibxDuracion_hospitalizacion.getValue()!=null?ibxDuracion_hospitalizacion.getValue():0));
				ficha_epidemiologia_n20.setDias_urgencia((ibxDias_urgencia.getValue()!=null?ibxDias_urgencia.getValue():0));
				ficha_epidemiologia_n20.setDias_pediatra((ibxDias_pediatra.getValue()!=null?ibxDias_pediatra.getValue():0));
				ficha_epidemiologia_n20.setCuidados_intensivos((ibxCuidados_intensivos.getValue()!=null?ibxCuidados_intensivos.getValue():0));
				ficha_epidemiologia_n20.setFecha_de_ingreso(new Timestamp(dtbxFecha_de_ingreso.getValue().getTime()));
				ficha_epidemiologia_n20.setMotivo_de_ingreso(rdbMotivo_de_ingreso.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setCuadro_de_diarrea(rdbCuadro_de_diarrea.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setDiagnostico_de_egreso(tbxDiagnostico_de_egreso.getValue());
				ficha_epidemiologia_n20.setFecha_de_recoleccion(new Timestamp(dtbxFecha_de_recoleccion.getValue().getTime()));
				ficha_epidemiologia_n20.setFecha_del_recepcion(new Timestamp(dtbxFecha_del_recepcion.getValue().getTime()));
				ficha_epidemiologia_n20.setFecha_del_resultado(new Timestamp(dtbxFecha_del_resultado.getValue().getTime()));
				ficha_epidemiologia_n20.setIdentificacion_de_rota(rdbIdentificacion_de_rota.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setSerotipo_g(tbxSerotipo_g.getValue());
				ficha_epidemiologia_n20.setSerotipo_p(tbxSerotipo_p.getValue());
				ficha_epidemiologia_n20.setIdentificacion_de_bact(rdbIdentificacion_de_bact.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setCuales(tbxCuales.getValue());
				ficha_epidemiologia_n20.setIdentificacion_de_parasitos(rdbIdentificacion_de_parasitos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setCuales2(tbxCuales2.getValue());
				ficha_epidemiologia_n20.setAsiste_a_guarderia(rdbAsiste_a_guarderia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setCual6(tbxCual6.getValue());
				ficha_epidemiologia_n20.setPersonas_con_diarrea(rdbPersonas_con_diarrea.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n20.setDiligenciado_por(tbxDiligenciado_por.getValue());
				ficha_epidemiologia_n20.setTelefono_de_contacto(tbxTelefono_de_contacto.getValue());
				ficha_epidemiologia_n20.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n20.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n20.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n20.setDelete_date(null);
				ficha_epidemiologia_n20.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n20.setDelete_user(null);
				
				return ficha_epidemiologia_n20;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n20 obj)throws Exception{
		Ficha_epidemiologia_n20 ficha_epidemiologia_n20 = (Ficha_epidemiologia_n20)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n20.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n20.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n20.getFecha_inicial());
			obtenerAdmision(admision);
			tbxIdentificacion.setValue(ficha_epidemiologia_n20.getIdentificacion());

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbVacuna_contra_rota, ficha_epidemiologia_n20.getVacuna_contra_rota());
			dtbxFecha_aplicacion_1dosis.setValue(ficha_epidemiologia_n20.getFecha_aplicacion_1dosis());
			dtbxFecha_aplicacion_2dosis.setValue(ficha_epidemiologia_n20.getFecha_aplicacion_2dosis());
			Utilidades.seleccionarRadio(rdbTiene_carnet, ficha_epidemiologia_n20.getTiene_carnet());
			dbxPeso_al_nacer.setValue(ficha_epidemiologia_n20.getPeso_al_nacer());
			Utilidades.seleccionarRadio(rdbRecibio_lecha_materna, ficha_epidemiologia_n20.getRecibio_lecha_materna());
			ibxCuanto_tiempo.setValue(ficha_epidemiologia_n20.getCuanto_tiempo());
			Utilidades.seleccionarRadio(rdbAlimentacion_actual, ficha_epidemiologia_n20.getAlimentacion_actual());
			Utilidades.seleccionarRadio(rdbFiebre, ficha_epidemiologia_n20.getFiebre());
			Utilidades.seleccionarRadio(rdbVomito, ficha_epidemiologia_n20.getVomito());
			ibxNumero_vomitos.setValue(ficha_epidemiologia_n20.getNumero_vomitos());
			dtbxFecha_inicio_diarrea.setValue(ficha_epidemiologia_n20.getFecha_inicio_diarrea());
			ibxNumero_de_deposiciones.setValue(ficha_epidemiologia_n20.getNumero_de_deposiciones());
			dtbxFecha_terminacion_diarrea.setValue(ficha_epidemiologia_n20.getFecha_terminacion_diarrea());
			Utilidades.seleccionarRadio(rdbCaracteristica_heces, ficha_epidemiologia_n20.getCaracteristica_heces());
			tbxCual1.setValue(ficha_epidemiologia_n20.getCual1());
			Utilidades.seleccionarRadio(rdbEstado_al_ingreso, ficha_epidemiologia_n20.getEstado_al_ingreso());
			Utilidades.seleccionarRadio(rdbGrado_de_deshidratacion, ficha_epidemiologia_n20.getGrado_de_deshidratacion());
			dbxPeso.setValue(ficha_epidemiologia_n20.getPeso());
			dbxTalla.setValue(ficha_epidemiologia_n20.getTalla());
			Utilidades.seleccionarRadio(rdbRecibio_antibiotico1, ficha_epidemiologia_n20.getRecibio_antibiotico1());
			tbxCual2.setValue(ficha_epidemiologia_n20.getCual2());
			Utilidades.seleccionarRadio(rdbRecibio_antibiotico2, ficha_epidemiologia_n20.getRecibio_antibiotico2());
			Utilidades.seleccionarRadio(rdbPresento_alguna_comp, ficha_epidemiologia_n20.getPresento_alguna_comp());
			tbxCual3.setValue(ficha_epidemiologia_n20.getCual3());
			Utilidades.seleccionarRadio(rdbRecibio_antib_durante, ficha_epidemiologia_n20.getRecibio_antib_durante());
			tbxCual4.setValue(ficha_epidemiologia_n20.getCual4());
			ibxDuracion_hospitalizacion.setValue(ficha_epidemiologia_n20.getDuracion_hospitalizacion());
			Utilidades.seleccionarRadio(rdbHospitalizacion_tratamiento, ficha_epidemiologia_n20.getHospitalizacion_tratamiento());
			ibxDias_urgencia.setValue(ficha_epidemiologia_n20.getDias_urgencia());
			ibxDias_pediatra.setValue(ficha_epidemiologia_n20.getDias_pediatra());
			ibxCuidados_intensivos.setValue(ficha_epidemiologia_n20.getCuidados_intensivos());
			dtbxFecha_de_ingreso.setValue(ficha_epidemiologia_n20.getFecha_de_ingreso());
			Utilidades.seleccionarRadio(rdbMotivo_de_ingreso, ficha_epidemiologia_n20.getMotivo_de_ingreso());
			Utilidades.seleccionarRadio(rdbCuadro_de_diarrea, ficha_epidemiologia_n20.getCuadro_de_diarrea());
			tbxDiagnostico_de_egreso.setValue(ficha_epidemiologia_n20.getDiagnostico_de_egreso());
			dtbxFecha_de_recoleccion.setValue(ficha_epidemiologia_n20.getFecha_de_recoleccion());
			dtbxFecha_del_recepcion.setValue(ficha_epidemiologia_n20.getFecha_del_recepcion());
			dtbxFecha_del_resultado.setValue(ficha_epidemiologia_n20.getFecha_del_resultado());
			Utilidades.seleccionarRadio(rdbIdentificacion_de_rota, ficha_epidemiologia_n20.getIdentificacion_de_rota());
			tbxSerotipo_g.setValue(ficha_epidemiologia_n20.getSerotipo_g());
			tbxSerotipo_p.setValue(ficha_epidemiologia_n20.getSerotipo_p());
			Utilidades.seleccionarRadio(rdbIdentificacion_de_bact, ficha_epidemiologia_n20.getIdentificacion_de_bact());
			tbxCuales.setValue(ficha_epidemiologia_n20.getCuales());
			Utilidades.seleccionarRadio(rdbIdentificacion_de_parasitos, ficha_epidemiologia_n20.getIdentificacion_de_parasitos());
			tbxCuales2.setValue(ficha_epidemiologia_n20.getCuales2());
			Utilidades.seleccionarRadio(rdbAsiste_a_guarderia, ficha_epidemiologia_n20.getAsiste_a_guarderia());
			tbxCual6.setValue(ficha_epidemiologia_n20.getCual6());
			Utilidades.seleccionarRadio(rdbPersonas_con_diarrea, ficha_epidemiologia_n20.getPersonas_con_diarrea());
			tbxDiligenciado_por.setValue(ficha_epidemiologia_n20.getDiligenciado_por());
			tbxTelefono_de_contacto.setValue(ficha_epidemiologia_n20.getTelefono_de_contacto());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n20 ficha_epidemiologia_n20 = (Ficha_epidemiologia_n20)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n20);
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
	
	public void deshabilitar_conRadio(String value) {
		if (value.equals("1")) {
			ibxDias_urgencia.setReadonly(false);
			ibxDias_pediatra.setText("");
			ibxCuidados_intensivos.setText("");	
			ibxDias_pediatra.setReadonly(true);
			ibxCuidados_intensivos.setReadonly(true);
		}if (value.equals("2")) {
			ibxDias_pediatra.setReadonly(false);
			ibxCuidados_intensivos.setText("");	
			ibxDias_urgencia.setText("");
			ibxDias_urgencia.setReadonly(true);
			ibxCuidados_intensivos.setReadonly(true);
		}if (value.equals("3")) {
			ibxCuidados_intensivos.setReadonly(false);
			ibxDias_pediatra.setText("");
			ibxDias_urgencia.setText("");
			ibxDias_urgencia.setReadonly(true);
			ibxDias_pediatra.setReadonly(true);
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
	public Ficha_epidemiologia_n20 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n20> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n20.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n20 ficha_n20 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n20;
				}else{

					return null;
				}
	}
}