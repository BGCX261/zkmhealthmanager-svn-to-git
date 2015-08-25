/*
 * ficha_epidemiologia_n21Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n21;
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

public class Ficha_epidemiologia_n21Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n21>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n21Action.class);
	
	
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
	@View private Radiogroup rdbTipo_agresion;
	@View private Textbox tbxOtra_agresion;
	@View private Radiogroup rdbAgresion_provocada;
	@View private Radiogroup rdbTipo_lesion;
	@View private Radiogroup rdbProfundidad;
	@View private Checkbox chbCabeza;
	@View private Checkbox chbMano;
	@View private Checkbox chbTronco;
	@View private Checkbox chbMiembro_superior;
	@View private Checkbox chbMiembro_inferior;
	@View private Datebox dtbxFecha_agresion;
	@View private Radiogroup rdbTipo_agresor;
	@View private Radiogroup rdbVacunado;
	@View private Datebox dtbxFacha_vacunacion;
	@View private Radiogroup rdbPresento_carne;
	@View private Textbox tbxNombre_propietario;
	@View private Textbox tbxDireccion_propietario;
	@View private Textbox tbxTelefono_propietario;
	@View private Radiogroup rdbEstado_animal;
	@View private Radiogroup rdbUbicacion;
	@View private Radiogroup rdbTipo_exposicion;
	@View private Radiogroup rdbSuero_antirrabico;
	@View private Datebox dtbxFacha_aplicacion;
	@View private Radiogroup rdbVacuna_antirrabica;
	@View private Intbox ibxDosis;
	@View private Datebox dtbxFacha_ultima_dosis;
	@View private Radiogroup rdbLavado_herida;
	@View private Radiogroup rdbSutura_herida;
	@View private Radiogroup rdbAplicacion_suero;
	@View private Radiogroup rdbAplicacion_vacuna;
	@View private Datebox dtbxFecha_aplicacion_suero;
	@View private Intbox ibxNumero_frascos;
	@View private Radiogroup rdbReaccion_suero;
	@View private Datebox dtbxAplicacion_dosis1;
	@View private Datebox dtbxAplicacion_dosis2;
	@View private Datebox dtbxAplicacion_dosis3;
	@View private Datebox dtbxAplicacion_dosis4;
	@View private Datebox dtbxAplicacion_dosis5;
	@View private Radiogroup rdbReaccion_vacuna;
	@View private Radiogroup rdbSuspension_tratamiento;
	@View private Intbox ibxDias_observacion;
	@View private Radiogroup rdbLugar_observacion;
	@View private Radiogroup rdbEstado_animal_observacion;
	@View private Datebox dtbxFecha_muerte;
	@View private Textbox tbxCodigo_medico;
	private final String[] idsExcluyentes = {};

	@View private Toolbarbutton btGuardar;
	
	@View private Row rowOtro_agresor;
	@View private Label lbOtra_agresion;

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
				Ficha_epidemiologia_n21 ficha = new Ficha_epidemiologia_n21();
				ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
				ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
				ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
				ficha = (Ficha_epidemiologia_n21) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
				
				//log.info("consultar ficha 21-------> "+ficha);
				
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
			dtbxAplicacion_dosis1.setValue(null);
			dtbxAplicacion_dosis2.setValue(null);
			dtbxAplicacion_dosis3.setValue(null);
			dtbxAplicacion_dosis4.setValue(null);
			dtbxAplicacion_dosis5.setValue(null);
			dtbxFacha_aplicacion.setValue(null);
			dtbxFacha_ultima_dosis.setValue(null);
			dtbxFacha_vacunacion.setValue(null);
			dtbxFecha_agresion.setValue(null);
			dtbxFecha_aplicacion_suero.setValue(null);
			dtbxFecha_muerte.setValue(null);
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
			
		dtbxFecha_agresion.setStyle("background-color:white");
		tbxNombre_propietario.setStyle("text-transform:uppercase;background-color:white");
		tbxDireccion_propietario.setStyle("text-transform:uppercase;background-color:white");
		tbxTelefono_propietario.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(dtbxFecha_agresion.getText().trim().equals("")){
			dtbxFecha_agresion.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if(tbxNombre_propietario.getText().trim().equals("")){
			tbxNombre_propietario.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxDireccion_propietario.getText().trim().equals("")){
			tbxDireccion_propietario.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(tbxTelefono_propietario.getText().trim().equals("")){
			tbxTelefono_propietario.setStyle("text-transform:uppercase;background-color:#F6BBBE");
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

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}
			
			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
			
			List<Ficha_epidemiologia_n21> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n21.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n21 ficha_epidemiologia_n21 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n21, this));
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
		
		final Ficha_epidemiologia_n21 ficha_epidemiologia_n21 = (Ficha_epidemiologia_n21)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n21.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n21.getIdentificacion()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n21.getFecha_creacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n21);
			}
		});
		hbox.appendChild(img);
		
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n21 obtenerFichaEpidemiologia() {
		
				Ficha_epidemiologia_n21 ficha_epidemiologia_n21 = new Ficha_epidemiologia_n21();
				ficha_epidemiologia_n21.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n21.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n21.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n21.setIdentificacion(tbxIdentificacion.getValue());
				ficha_epidemiologia_n21.setFecha_creacion(new Timestamp(dtbxFecha_creacion.getValue().getTime()));
				ficha_epidemiologia_n21.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n21.setTipo_agresion(rdbTipo_agresion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setOtra_agresion(tbxOtra_agresion.getValue());
				ficha_epidemiologia_n21.setAgresion_provocada(rdbAgresion_provocada.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setTipo_lesion(rdbTipo_lesion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setProfundidad(rdbProfundidad.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setCabeza(chbCabeza.isChecked());
				ficha_epidemiologia_n21.setMano(chbMano.isChecked());
				ficha_epidemiologia_n21.setTronco(chbTronco.isChecked());
				ficha_epidemiologia_n21.setMiembro_superior(chbMiembro_superior.isChecked());
				ficha_epidemiologia_n21.setMiembro_inferior(chbMiembro_inferior.isChecked());
				
				if (dtbxFecha_agresion.getValue() != null) {
					ficha_epidemiologia_n21.setFecha_agresion(new Timestamp(dtbxFecha_agresion.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setFecha_agresion(null);
				}
				
				ficha_epidemiologia_n21.setTipo_agresor(rdbTipo_agresor.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setVacunado(rdbVacunado.getSelectedItem().getValue().toString());
				
				if (dtbxFacha_vacunacion.getValue() != null) {
					ficha_epidemiologia_n21.setFacha_vacunacion(new Timestamp(dtbxFacha_vacunacion.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setFacha_vacunacion(null);
				}
				
				ficha_epidemiologia_n21.setPresento_carne(rdbPresento_carne.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setNombre_propietario(tbxNombre_propietario.getValue());
				ficha_epidemiologia_n21.setDireccion_propietario(tbxDireccion_propietario.getValue());
				ficha_epidemiologia_n21.setTelefono_propietario(tbxTelefono_propietario.getValue());
				ficha_epidemiologia_n21.setEstado_animal(rdbEstado_animal.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setUbicacion(rdbUbicacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setTipo_exposicion(rdbTipo_exposicion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setSuero_antirrabico(rdbSuero_antirrabico.getSelectedItem().getValue().toString());
				
				if (dtbxFacha_aplicacion.getValue() != null) {
					ficha_epidemiologia_n21.setFacha_aplicacion(new Timestamp(dtbxFacha_aplicacion.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setFacha_aplicacion(null);
				}
				
				ficha_epidemiologia_n21.setVacuna_antirrabica(rdbVacuna_antirrabica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setDosis(ibxDosis.getValue() != null ? ibxDosis.getValue() + "" : "");
				if (dtbxFacha_ultima_dosis.getValue() != null) {
					ficha_epidemiologia_n21.setFacha_ultima_dosis(new Timestamp(dtbxFacha_ultima_dosis.getValue().getTime()));
					
				} else {
					ficha_epidemiologia_n21.setFacha_ultima_dosis(null);
					
				}
				ficha_epidemiologia_n21.setLavado_herida(rdbLavado_herida.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setSutura_herida(rdbSutura_herida.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setAplicacion_suero(rdbAplicacion_suero.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setAplicacion_vacuna(rdbAplicacion_vacuna.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_aplicacion_suero.getValue() != null) {
					ficha_epidemiologia_n21.setFecha_aplicacion_suero(new Timestamp(dtbxFecha_aplicacion_suero.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setFecha_aplicacion_suero(null);
				}
				
				ficha_epidemiologia_n21.setNumero_frascos(ibxNumero_frascos.getValue() != null ? ibxNumero_frascos.getValue() + "" : "");
				ficha_epidemiologia_n21.setReaccion_suero(rdbReaccion_suero.getSelectedItem().getValue().toString());
				
				if (dtbxAplicacion_dosis1.getValue() != null) {
					ficha_epidemiologia_n21.setAplicacion_dosis1(new Timestamp(dtbxAplicacion_dosis1.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setAplicacion_dosis1(null);
				}
				
				
				if (dtbxAplicacion_dosis2.getValue() != null) {
					ficha_epidemiologia_n21.setAplicacion_dosis2(new Timestamp(dtbxAplicacion_dosis2.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setAplicacion_dosis2(null);
				}
				
				
				if (dtbxAplicacion_dosis3.getValue() != null) {
					ficha_epidemiologia_n21.setAplicacion_dosis3(new Timestamp(dtbxAplicacion_dosis3.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setAplicacion_dosis3(null);
				}
				
				
				if (dtbxAplicacion_dosis4.getValue() != null) {
					ficha_epidemiologia_n21.setAplicacion_dosis4(new Timestamp(dtbxAplicacion_dosis4.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setAplicacion_dosis4(null);
				}
				
				
				if (dtbxAplicacion_dosis5.getValue() != null) {
					ficha_epidemiologia_n21.setAplicacion_dosis5(new Timestamp(dtbxAplicacion_dosis5.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setAplicacion_dosis5(null);
				}
				
				ficha_epidemiologia_n21.setReaccion_vacuna(rdbReaccion_vacuna.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setSuspension_tratamiento(rdbSuspension_tratamiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setDias_observacion(ibxDias_observacion.getValue() != null ? ibxDias_observacion.getValue() + "" : "");
				ficha_epidemiologia_n21.setLugar_observacion(rdbLugar_observacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n21.setEstado_animal_observacion(rdbEstado_animal_observacion.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_muerte.getValue() != null) {
					ficha_epidemiologia_n21.setFecha_muerte(new Timestamp(dtbxFecha_muerte.getValue().getTime()));
	
				} else {
					ficha_epidemiologia_n21.setFecha_muerte(null);
				}
				
				ficha_epidemiologia_n21.setCodigo_medico(tbxCodigo_medico.getValue());
				ficha_epidemiologia_n21.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n21.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n21.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n21.setDelete_date(null);
				ficha_epidemiologia_n21.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n21.setDelete_user(null);
				
				return ficha_epidemiologia_n21;
				 
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n21 obj)throws Exception{
		Ficha_epidemiologia_n21 ficha_epidemiologia_n21 = (Ficha_epidemiologia_n21)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n21.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n21.getIdentificacion());

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(empresa.getCodigo_empresa());
			paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			paciente.setNro_identificacion(ficha_epidemiologia_n21.getIdentificacion());
			paciente = getServiceLocator().getPacienteService().consultar(paciente);

			tbxNombrePaciente.setValue((paciente != null ? paciente.getNombre1()+ " " + paciente.getApellido1() : ""));
			tbxTipo_identificacion.setValue((paciente != null ? paciente.getTipo_identificacion()+ " " + paciente.getTipo_identificacion() : ""));
			
			dtbxFecha_creacion.setValue(ficha_epidemiologia_n21.getFecha_creacion());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n21.getCodigo_diagnostico());
			Utilidades.seleccionarRadio(rdbTipo_agresion, ficha_epidemiologia_n21.getTipo_agresion());
			
			if(ficha_epidemiologia_n21.getTipo_agresion().equals("O")){
				lbOtra_agresion.setVisible(true);
				tbxOtra_agresion.setVisible(true);
				tbxOtra_agresion.setValue(ficha_epidemiologia_n21.getOtra_agresion());
				
			}else{
				lbOtra_agresion.setVisible(false);
				tbxOtra_agresion.setVisible(false);
				
			}
			
			Utilidades.seleccionarRadio(rdbAgresion_provocada, ficha_epidemiologia_n21.getAgresion_provocada());
			Utilidades.seleccionarRadio(rdbTipo_lesion, ficha_epidemiologia_n21.getTipo_lesion());
			Utilidades.seleccionarRadio(rdbProfundidad, ficha_epidemiologia_n21.getProfundidad());
			chbCabeza.setChecked(ficha_epidemiologia_n21.getCabeza());
			chbMano.setChecked(ficha_epidemiologia_n21.getMano());
			chbTronco.setChecked(ficha_epidemiologia_n21.getTronco());
			chbMiembro_superior.setChecked(ficha_epidemiologia_n21.getMiembro_superior());
			chbMiembro_inferior.setChecked(ficha_epidemiologia_n21.getMiembro_inferior());
			dtbxFecha_agresion.setValue(ficha_epidemiologia_n21.getFecha_agresion());
			Utilidades.seleccionarRadio(rdbTipo_agresor, ficha_epidemiologia_n21.getTipo_agresor());
			Utilidades.seleccionarRadio(rdbVacunado, ficha_epidemiologia_n21.getVacunado());
			dtbxFacha_vacunacion.setValue(ficha_epidemiologia_n21.getFacha_vacunacion());
			Utilidades.seleccionarRadio(rdbPresento_carne, ficha_epidemiologia_n21.getPresento_carne());
			tbxNombre_propietario.setValue(ficha_epidemiologia_n21.getNombre_propietario());
			tbxDireccion_propietario.setValue(ficha_epidemiologia_n21.getDireccion_propietario());
			tbxTelefono_propietario.setValue(ficha_epidemiologia_n21.getTelefono_propietario());
			Utilidades.seleccionarRadio(rdbEstado_animal, ficha_epidemiologia_n21.getEstado_animal());
			Utilidades.seleccionarRadio(rdbUbicacion, ficha_epidemiologia_n21.getUbicacion());
			Utilidades.seleccionarRadio(rdbTipo_exposicion, ficha_epidemiologia_n21.getTipo_exposicion());
			Utilidades.seleccionarRadio(rdbSuero_antirrabico, ficha_epidemiologia_n21.getSuero_antirrabico());
			dtbxFacha_aplicacion.setValue(ficha_epidemiologia_n21.getFacha_aplicacion());
			Utilidades.seleccionarRadio(rdbVacuna_antirrabica, ficha_epidemiologia_n21.getVacuna_antirrabica());
			ibxDosis.setValue((ficha_epidemiologia_n21.getDosis() != null && !ficha_epidemiologia_n21.getDosis().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n21.getDosis()) : null);
			dtbxFacha_ultima_dosis.setValue(ficha_epidemiologia_n21.getFacha_ultima_dosis());
			Utilidades.seleccionarRadio(rdbLavado_herida, ficha_epidemiologia_n21.getLavado_herida());
			Utilidades.seleccionarRadio(rdbSutura_herida, ficha_epidemiologia_n21.getSutura_herida());
			Utilidades.seleccionarRadio(rdbAplicacion_suero, ficha_epidemiologia_n21.getAplicacion_suero());
			Utilidades.seleccionarRadio(rdbAplicacion_vacuna, ficha_epidemiologia_n21.getAplicacion_vacuna());
			dtbxFecha_aplicacion_suero.setValue(ficha_epidemiologia_n21.getFecha_aplicacion_suero());
			ibxNumero_frascos.setValue((ficha_epidemiologia_n21.getNumero_frascos() != null && !ficha_epidemiologia_n21.getNumero_frascos().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n21.getNumero_frascos()) : null);
			Utilidades.seleccionarRadio(rdbReaccion_suero, ficha_epidemiologia_n21.getReaccion_suero());
			dtbxAplicacion_dosis1.setValue(ficha_epidemiologia_n21.getAplicacion_dosis1());
			dtbxAplicacion_dosis2.setValue(ficha_epidemiologia_n21.getAplicacion_dosis2());
			dtbxAplicacion_dosis3.setValue(ficha_epidemiologia_n21.getAplicacion_dosis3());
			dtbxAplicacion_dosis4.setValue(ficha_epidemiologia_n21.getAplicacion_dosis4());
			dtbxAplicacion_dosis5.setValue(ficha_epidemiologia_n21.getAplicacion_dosis5());
			Utilidades.seleccionarRadio(rdbReaccion_vacuna, ficha_epidemiologia_n21.getReaccion_vacuna());
			Utilidades.seleccionarRadio(rdbSuspension_tratamiento, ficha_epidemiologia_n21.getSuspension_tratamiento());
			ibxDias_observacion.setValue((ficha_epidemiologia_n21.getDias_observacion() != null && !ficha_epidemiologia_n21.getDias_observacion().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n21.getDias_observacion()) : null);
			Utilidades.seleccionarRadio(rdbLugar_observacion, ficha_epidemiologia_n21.getLugar_observacion());
			Utilidades.seleccionarRadio(rdbEstado_animal_observacion, ficha_epidemiologia_n21.getEstado_animal_observacion());
			dtbxFecha_muerte.setValue(ficha_epidemiologia_n21.getFecha_muerte());
			tbxCodigo_medico.setValue(ficha_epidemiologia_n21.getCodigo_medico());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n21 ficha_epidemiologia_n21 = (Ficha_epidemiologia_n21)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n21);
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
	public Ficha_epidemiologia_n21 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n21> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n21.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n21 ficha_n21 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n21;
				}else{

					return null;
				}
	}
	
	
}