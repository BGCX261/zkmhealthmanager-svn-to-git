/*
 * ficha_epidemiologia_n12Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n12;
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

public class Ficha_epidemiologia_n12Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n12>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n12Action.class);
	
	
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
	@View private Radiogroup rdbTipo_de_ingreso;
	@View private Radiogroup rdbClasificacion_bacteriologica;
	@View private Radiogroup rdbClasificacion_final;
	@View private Radiogroup rdbBaciloscopia;
	@View private Datebox dtbxFecha_toma_baciloscopia;
	@View private Textbox tbxResultado_indice;
	@View private Datebox dtbxFecha_resultado1;
	@View private Radiogroup rdbBiopsia;
	@View private Datebox dtbxFecha_toma_biopsia;
	@View private Radiogroup rdbResultado_histopatologia;
	@View private Datebox dtbxFecha_resultado2;
	@View private Radiogroup rdbMaximo_grado_discap;
	@View private Radiogroup rdbPresenta_reaccion;
	@View private Datebox dtbxFecha_de_inicio_trata;
	@View private Radiogroup rdbMetodo_de_captacion;
	@View private Radiogroup rdbPosible_fuente_contagio;
	@View private Radiogroup rdbInvestigacion_campo;
	@View private Datebox dtbxFecha_de_investigacion;
	@View private Radiogroup rdbTiene_convivientes;
	@View private Intbox ibxNum_total_convivientes;
	@View private Intbox ibxNum_total_conv_examin;
	@View private Intbox ibxNum_total_conv_sintoma;
	@View private Intbox ibxNum_conv_vacunados;
	@View private Textbox tbxObservaciones_y_seguimientos;
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
			Ficha_epidemiologia_n12 ficha = new Ficha_epidemiologia_n12();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n12) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 12-------> "+ficha);
			
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
	// Metodo para buscar //
		public void buscarDatos() throws Exception {
			try {
				String parameter = lbxParameter.getSelectedItem().getValue()
						.toString();
				String value = tbxValue.getValue().toUpperCase().trim();

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("parameter", parameter);
				parameters.put("value", "%" + value + "%");

				if (admision != null) {
					parameters.put("identificacion",
							admision.getNro_identificacion());
				}

				getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
						"limit 25 offset 0");

				List<Ficha_epidemiologia_n12> lista_datos = getServiceLocator()
						.getFicha_epidemiologia_nnService().listar(
								Ficha_epidemiologia_n12.class, parameters);
				rowsResultado.getChildren().clear();

				for (Ficha_epidemiologia_n12 ficha_epidemiologia_n12 : lista_datos) {
					rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n12,
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
		
		final Ficha_epidemiologia_n12 ficha_epidemiologia_n12 = (Ficha_epidemiologia_n12)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n12.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n12.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n12.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n12);
			}
		});
		hbox.appendChild(img);
		
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n12 obtenerFichaEpidemiologia(){
						
				Ficha_epidemiologia_n12 ficha_epidemiologia_n12 = new Ficha_epidemiologia_n12();
				ficha_epidemiologia_n12.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n12.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n12.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n12.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n12.setIdentificacion(tbxIdentificacion.getValue());
				ficha_epidemiologia_n12.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n12.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n12.setTipo_de_ingreso(rdbTipo_de_ingreso.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setClasificacion_bacteriologica(rdbClasificacion_bacteriologica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setClasificacion_final(rdbClasificacion_final.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setBaciloscopia(rdbBaciloscopia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setFecha_toma_baciloscopia(new Timestamp(dtbxFecha_toma_baciloscopia.getValue().getTime()));
				ficha_epidemiologia_n12.setResultado_indice(tbxResultado_indice.getValue());
				ficha_epidemiologia_n12.setFecha_resultado1(new Timestamp(dtbxFecha_resultado1.getValue().getTime()));
				ficha_epidemiologia_n12.setBiopsia(rdbBiopsia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setFecha_toma_biopsia(new Timestamp(dtbxFecha_toma_biopsia.getValue().getTime()));
				ficha_epidemiologia_n12.setResultado_histopatologia(rdbResultado_histopatologia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setFecha_resultado2(new Timestamp(dtbxFecha_resultado2.getValue().getTime()));
				ficha_epidemiologia_n12.setMaximo_grado_discap(rdbMaximo_grado_discap.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setPresenta_reaccion(rdbPresenta_reaccion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setFecha_de_inicio_trata(new Timestamp(dtbxFecha_de_inicio_trata.getValue().getTime()));
				ficha_epidemiologia_n12.setMetodo_de_captacion(rdbMetodo_de_captacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setPosible_fuente_contagio(rdbPosible_fuente_contagio.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setInvestigacion_campo(rdbInvestigacion_campo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setFecha_de_investigacion(new Timestamp(dtbxFecha_de_investigacion.getValue().getTime()));
				ficha_epidemiologia_n12.setTiene_convivientes(rdbTiene_convivientes.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n12.setNum_total_convivientes((ibxNum_total_convivientes.getValue()!=null?ibxNum_total_convivientes.getValue():0));
				ficha_epidemiologia_n12.setNum_total_conv_examin((ibxNum_total_conv_examin.getValue()!=null?ibxNum_total_conv_examin.getValue():0));
				ficha_epidemiologia_n12.setNum_total_conv_sintoma((ibxNum_total_conv_sintoma.getValue()!=null?ibxNum_total_conv_sintoma.getValue():0));
				ficha_epidemiologia_n12.setNum_conv_vacunados((ibxNum_conv_vacunados.getValue()!=null?ibxNum_conv_vacunados.getValue():0));
				ficha_epidemiologia_n12.setObservaciones_y_seguimientos(tbxObservaciones_y_seguimientos.getValue());
				ficha_epidemiologia_n12.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n12.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n12.setCreacion_user(usuarios.getCreacion_user());
				ficha_epidemiologia_n12.setDelete_date(null);
				ficha_epidemiologia_n12.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n12.setDelete_user(null);
				
				return ficha_epidemiologia_n12;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n12 obj)throws Exception{
		Ficha_epidemiologia_n12 ficha_epidemiologia_n12 = (Ficha_epidemiologia_n12)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n12.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n12.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n12.getFecha_inicial());
			tbxIdentificacion.setValue(ficha_epidemiologia_n12.getIdentificacion());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbTipo_de_ingreso, ficha_epidemiologia_n12.getTipo_de_ingreso());
			Utilidades.seleccionarRadio(rdbClasificacion_bacteriologica, ficha_epidemiologia_n12.getClasificacion_bacteriologica());
			Utilidades.seleccionarRadio(rdbClasificacion_final, ficha_epidemiologia_n12.getClasificacion_final());
			Utilidades.seleccionarRadio(rdbBaciloscopia, ficha_epidemiologia_n12.getBaciloscopia());
			dtbxFecha_toma_baciloscopia.setValue(ficha_epidemiologia_n12.getFecha_toma_baciloscopia());
			tbxResultado_indice.setValue(ficha_epidemiologia_n12.getResultado_indice());
			dtbxFecha_resultado1.setValue(ficha_epidemiologia_n12.getFecha_resultado1());
			Utilidades.seleccionarRadio(rdbBiopsia, ficha_epidemiologia_n12.getBiopsia());
			dtbxFecha_toma_biopsia.setValue(ficha_epidemiologia_n12.getFecha_toma_biopsia());
			Utilidades.seleccionarRadio(rdbResultado_histopatologia, ficha_epidemiologia_n12.getResultado_histopatologia());
			dtbxFecha_resultado2.setValue(ficha_epidemiologia_n12.getFecha_resultado2());
			Utilidades.seleccionarRadio(rdbMaximo_grado_discap, ficha_epidemiologia_n12.getMaximo_grado_discap());
			Utilidades.seleccionarRadio(rdbPresenta_reaccion, ficha_epidemiologia_n12.getPresenta_reaccion());
			dtbxFecha_de_inicio_trata.setValue(ficha_epidemiologia_n12.getFecha_de_inicio_trata());
			Utilidades.seleccionarRadio(rdbMetodo_de_captacion, ficha_epidemiologia_n12.getMetodo_de_captacion());
			Utilidades.seleccionarRadio(rdbPosible_fuente_contagio, ficha_epidemiologia_n12.getPosible_fuente_contagio());
			Utilidades.seleccionarRadio(rdbInvestigacion_campo, ficha_epidemiologia_n12.getInvestigacion_campo());
			dtbxFecha_de_investigacion.setValue(ficha_epidemiologia_n12.getFecha_de_investigacion());
			Utilidades.seleccionarRadio(rdbTiene_convivientes, ficha_epidemiologia_n12.getTiene_convivientes());
			ibxNum_total_convivientes.setValue(ficha_epidemiologia_n12.getNum_total_convivientes());
			ibxNum_total_conv_examin.setValue(ficha_epidemiologia_n12.getNum_total_conv_examin());
			ibxNum_total_conv_sintoma.setValue(ficha_epidemiologia_n12.getNum_total_conv_sintoma());
			ibxNum_conv_vacunados.setValue(ficha_epidemiologia_n12.getNum_conv_vacunados());
			tbxObservaciones_y_seguimientos.setValue(ficha_epidemiologia_n12.getObservaciones_y_seguimientos());
				
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n12 ficha_epidemiologia_n12 = (Ficha_epidemiologia_n12)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n12);
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
	public Ficha_epidemiologia_n12 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n12> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n12.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n12 ficha_n12 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n12;
				}else{

					return null;
				}
	}
	
}