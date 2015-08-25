/*
 * ficha_epidemiologia_n29Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n29;
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

public class Ficha_epidemiologia_n29Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n29>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n29Action.class);
	
	
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
	@View private Radiogroup rdbClasificacion_indice;
	@View private Radiogroup rdbPresencia_caries;
	@View private Radiogroup rdbTipo_caries;
	@View private Radiogroup rdbSitio_residencia_gestantes;
	@View private Radiogroup rdbSitio_residencia;
	@View private Radiogroup rdbFuente_consumo_agua;
	@View private Radiogroup rdbFuente_consumo_sal;
	@View private Radiogroup rdbTipo_sal;
	@View private Radiogroup rdbResponsable_cepillado;
	@View private Radiogroup rdbFrecuencia;
	@View private Radiogroup rdbCantidad_crema;
	@View private Radiogroup rdbIngesta_crema;
	@View private Radiogroup rdbIngesta_enjuague;
	@View private Radiogroup rdbAplicacion_topicas;
	@View private Radiogroup rdbLactancia_materna;
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
				Ficha_epidemiologia_n29 ficha = new Ficha_epidemiologia_n29();
				ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
				ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
				ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
				ficha = (Ficha_epidemiologia_n29) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
				
				//log.info("consultar ficha 29-------> "+ficha);
				
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
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}
			
			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
			
			List<Ficha_epidemiologia_n29> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n29.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n29 ficha_epidemiologia_n29 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n29, this));
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
		
		final Ficha_epidemiologia_n29 ficha_epidemiologia_n29 = (Ficha_epidemiologia_n29)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n29.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n29.getIdentificacion()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n29.getFecha_creacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n29);
			}
		});
		hbox.appendChild(img);
		
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n29 obtenerFichaEpidemiologia() {
					
				Ficha_epidemiologia_n29 ficha_epidemiologia_n29 = new Ficha_epidemiologia_n29();
				ficha_epidemiologia_n29.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n29.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n29.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n29.setIdentificacion(tbxIdentificacion.getValue());
				ficha_epidemiologia_n29.setFecha_creacion(new Timestamp(dtbxFecha_creacion.getValue().getTime()));
				ficha_epidemiologia_n29.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n29.setEstrato_socioeconomico(rdbEstrato_socioeconomico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setEscolaridad(rdbEscolaridad.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setIndice_46("");
				ficha_epidemiologia_n29.setIndice_16("");
				ficha_epidemiologia_n29.setIndice_15("");
				ficha_epidemiologia_n29.setIndice_13("");
				ficha_epidemiologia_n29.setIndice_12("");
				ficha_epidemiologia_n29.setIndice_11("");
				ficha_epidemiologia_n29.setIndice_21("");
				ficha_epidemiologia_n29.setIndice_22("");
				ficha_epidemiologia_n29.setIndice_23("");
				ficha_epidemiologia_n29.setIndice_25("");
				ficha_epidemiologia_n29.setIndice_26("");
				ficha_epidemiologia_n29.setIndice_36("");
				ficha_epidemiologia_n29.setClasificacion_indice(rdbClasificacion_indice.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setPresencia_caries(rdbPresencia_caries.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setTipo_caries(rdbTipo_caries.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setSitio_residencia_gestantes(rdbSitio_residencia_gestantes.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setSitio_residencia(rdbSitio_residencia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setFuente_consumo_agua(rdbFuente_consumo_agua.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setFuente_consumo_sal(rdbFuente_consumo_sal.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setTipo_sal(rdbTipo_sal.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setResponsable_cepillado(rdbResponsable_cepillado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setFrecuencia(rdbFrecuencia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setCantidad_crema(rdbCantidad_crema.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setIngesta_crema(rdbIngesta_crema.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setIngesta_enjuague(rdbIngesta_enjuague.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setAplicacion_topicas(rdbAplicacion_topicas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setLactancia_materna(rdbLactancia_materna.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n29.setCodigo_medico(tbxCodigo_medico.getValue());
				ficha_epidemiologia_n29.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n29.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n29.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n29.setDelete_date(null);
				ficha_epidemiologia_n29.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n29.setDelete_user(null);
				
				return ficha_epidemiologia_n29;
				 
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n29 obj)throws Exception{
		Ficha_epidemiologia_n29 ficha_epidemiologia_n29 = (Ficha_epidemiologia_n29)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n29.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n29.getIdentificacion());

			obtenerAdmision(admision);


			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			dtbxFecha_creacion.setValue(ficha_epidemiologia_n29.getFecha_creacion());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n29.getCodigo_diagnostico());
			Utilidades.seleccionarRadio(rdbEstrato_socioeconomico, ficha_epidemiologia_n29.getEstrato_socioeconomico());
			Utilidades.seleccionarRadio(rdbEscolaridad, ficha_epidemiologia_n29.getEscolaridad());
			Utilidades.seleccionarRadio(rdbClasificacion_indice, ficha_epidemiologia_n29.getClasificacion_indice());
			Utilidades.seleccionarRadio(rdbPresencia_caries, ficha_epidemiologia_n29.getPresencia_caries());
			Utilidades.seleccionarRadio(rdbTipo_caries, ficha_epidemiologia_n29.getTipo_caries());
			Utilidades.seleccionarRadio(rdbSitio_residencia_gestantes, ficha_epidemiologia_n29.getSitio_residencia_gestantes());
			Utilidades.seleccionarRadio(rdbSitio_residencia, ficha_epidemiologia_n29.getSitio_residencia());
			Utilidades.seleccionarRadio(rdbFuente_consumo_agua, ficha_epidemiologia_n29.getFuente_consumo_agua());
			Utilidades.seleccionarRadio(rdbFuente_consumo_sal, ficha_epidemiologia_n29.getFuente_consumo_sal());
			Utilidades.seleccionarRadio(rdbTipo_sal, ficha_epidemiologia_n29.getTipo_sal());
			Utilidades.seleccionarRadio(rdbResponsable_cepillado, ficha_epidemiologia_n29.getResponsable_cepillado());
			Utilidades.seleccionarRadio(rdbFrecuencia, ficha_epidemiologia_n29.getFrecuencia());
			Utilidades.seleccionarRadio(rdbCantidad_crema, ficha_epidemiologia_n29.getCantidad_crema());
			Utilidades.seleccionarRadio(rdbIngesta_crema, ficha_epidemiologia_n29.getIngesta_crema());
			Utilidades.seleccionarRadio(rdbIngesta_enjuague, ficha_epidemiologia_n29.getIngesta_enjuague());
			Utilidades.seleccionarRadio(rdbAplicacion_topicas, ficha_epidemiologia_n29.getAplicacion_topicas());
			Utilidades.seleccionarRadio(rdbLactancia_materna, ficha_epidemiologia_n29.getLactancia_materna());
			tbxCodigo_medico.setValue(ficha_epidemiologia_n29.getCodigo_medico());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n29 ficha_epidemiologia_n29 = (Ficha_epidemiologia_n29)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n29);
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
	public Ficha_epidemiologia_n29 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n29> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n29.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n29 ficha_n29 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n29;
				}else{

					return null;
				}
	}
	
}