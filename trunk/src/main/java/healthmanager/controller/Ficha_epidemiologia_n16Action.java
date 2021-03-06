/*
 * ficha_epidemiologia_n16Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n16;
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

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n16Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n16> {

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n16Action.class);
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	
	@View private Textbox tbx_nombres;
	@View private Textbox tbx_tipo_identificacion;
	
	@View private Textbox tbxCodigo_ficha;
	@View private Datebox dtbxFecha_ficha;
 	@View private Textbox tbxNro_identificacion;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
//    private Textbox tbxCodigo;
	@View private Radiogroup rdbVacuna_anti_hib;
	@View private Radiogroup rdbDosis_1;
	@View private Datebox dtbxFecha_primera_dosis_1;
	@View private Datebox dtbxFecha_ultima_dosis_1;
	@View private Radiogroup rdbTiene_carnet_1;
	@View private Radiogroup rdbVacuna_anti_meningococo;
	@View private Radiogroup rdbDosis2;
	@View private Datebox dtbxFecha_primera_dosis_2;
	@View private Datebox dtbxFecha_ultima_dosis_2;
	@View private Radiogroup rdbTiene_carnet_2;
	@View private Radiogroup rdbVacuna_anti_neumococo;
	@View private Radiogroup rdbDosis_3;
	@View private Datebox dtbxFecha_primera_dosis_3;
	@View private Datebox dtbxFecha_ultima_dosis_3;
	@View private Radiogroup rdbTiene_carnet_3;
	@View private Checkbox chbFiebre;
	@View private Checkbox chbRigidez_de_nuca;
	@View private Checkbox chbSignos_de_irritacion_meningea;
	@View private Checkbox chbRash_purpurico_o_petequial;
	@View private Checkbox chbAbombamiento_de_fontanelas;
	@View private Checkbox chbAlteracion_de_la_conciencia;
	@View private Radiogroup rdbUso_antibioticos_en_la_ultima_semana;
	@View private Textbox tbxCual_antibioticos_ultima_semana;
	@View private Datebox dtbxFecha_ultima_dosis_4;
	@View private Datebox dtbxFecha_de_la_toma_1;
	@View private Datebox dtbxFecha_de_la_toma_2;
	@View private Radiogroup rdbGram;
	@View private Radiogroup rdbCultivo_1;
	@View private Radiogroup rdbAntigenemia_1;
	@View private Radiogroup rdbCultivo_2;
	@View private Radiogroup rdbAntigenemia_2;
	@View private Checkbox chbPositivo_para_fi;
	@View private Checkbox chbPositivo_para_nm;
	@View private Checkbox chbPositivo_para_sp;
	@View private Checkbox chbNegativo;
	@View private Textbox tbxResultado_diferencial;
	@View private Datebox dtbxFecha_resultado;
	@View private Textbox tbxObservaciones_seguimientos;
	private final String[] idsExcluyentes = {};

	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception{
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n16 ficha = new Ficha_epidemiologia_n16();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n16) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 16-------> "+ficha);
			
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
			tbxNro_identificacion.setStyle("background-color:white");

			boolean valida = true;

			if (dtbxFecha_ficha.getText().trim().equals("")) {
				dtbxFecha_ficha.setStyle("background-color:#F6BBBE");
				valida = false;
			}
			// if(tbxNombres_y_apellidos.getText().trim().equals("")){
			// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			// valida = false;
			// }
			if (tbxNro_identificacion.getText().trim().equals("")) {
				tbxNro_identificacion.setStyle("background-color:#F6BBBE");
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

			List<Ficha_epidemiologia_n16> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n16.class, parameters);

			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n16 ficha_epidemiologia_n16 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n16,
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
		
		final Ficha_epidemiologia_n16 ficha_epidemiologia_n16 = (Ficha_epidemiologia_n16)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n16.getCodigo_ficha()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n16);
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
								eliminarDatos(ficha_epidemiologia_n16);
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
	// Metodo para guardar la informacion //
		public Ficha_epidemiologia_n16 obtenerFichaEpidemiologia() {
	
				Ficha_epidemiologia_n16 ficha_epidemiologia_n16 = new Ficha_epidemiologia_n16();
				ficha_epidemiologia_n16.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n16.setFecha_ficha(new Timestamp(dtbxFecha_ficha.getValue().getTime()));
				ficha_epidemiologia_n16.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n16.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				//ficha_epidemiologia_n16.setNro_identificacion(tbxNro_identificacion.getValue());
				ficha_epidemiologia_n16.setCodigo("Z000");
				ficha_epidemiologia_n16.setNro_identificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n16.setVacuna_anti_hib(rdbVacuna_anti_hib.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setDosis_1(rdbDosis_1.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setFecha_primera_dosis_1(new Timestamp(dtbxFecha_primera_dosis_1.getValue().getTime()));
				ficha_epidemiologia_n16.setFecha_ultima_dosis_1(new Timestamp(dtbxFecha_ultima_dosis_1.getValue().getTime()));
				ficha_epidemiologia_n16.setTiene_carnet_1(rdbTiene_carnet_1.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setVacuna_anti_meningococo(rdbVacuna_anti_meningococo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setDosis2(rdbDosis2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setFecha_primera_dosis_2(new Timestamp(dtbxFecha_primera_dosis_2.getValue().getTime()));
				ficha_epidemiologia_n16.setFecha_ultima_dosis_2(new Timestamp(dtbxFecha_ultima_dosis_2.getValue().getTime()));
				ficha_epidemiologia_n16.setTiene_carnet_2(rdbTiene_carnet_2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setVacuna_anti_neumococo(rdbVacuna_anti_neumococo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setDosis_3(rdbDosis_3.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setFecha_primera_dosis_3(new Timestamp(dtbxFecha_primera_dosis_3.getValue().getTime()));
				ficha_epidemiologia_n16.setFecha_ultima_dosis_3(new Timestamp(dtbxFecha_ultima_dosis_3.getValue().getTime()));
				ficha_epidemiologia_n16.setTiene_carnet_3(rdbTiene_carnet_3.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setFiebre(chbFiebre.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setRigidez_de_nuca(chbRigidez_de_nuca.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setSignos_de_irritacion_meningea(chbSignos_de_irritacion_meningea.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setRash_purpurico_o_petequial(chbRash_purpurico_o_petequial.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setAbombamiento_de_fontanelas(chbAbombamiento_de_fontanelas.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setAlteracion_de_la_conciencia(chbAlteracion_de_la_conciencia.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setUso_antibioticos_en_la_ultima_semana(rdbUso_antibioticos_en_la_ultima_semana.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setCual_antibioticos_ultima_semana(tbxCual_antibioticos_ultima_semana.getValue());
				ficha_epidemiologia_n16.setFecha_ultima_dosis_4(new Timestamp(dtbxFecha_ultima_dosis_4.getValue().getTime()));
				ficha_epidemiologia_n16.setFecha_de_la_toma_1(new Timestamp(dtbxFecha_de_la_toma_1.getValue().getTime()));
				ficha_epidemiologia_n16.setFecha_de_la_toma_2(new Timestamp(dtbxFecha_de_la_toma_2.getValue().getTime()));
				ficha_epidemiologia_n16.setGram(rdbGram.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setCultivo_1(rdbCultivo_1.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setAntigenemia_1(rdbAntigenemia_1.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setCultivo_2(rdbCultivo_2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setAntigenemia_2(rdbAntigenemia_2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n16.setPositivo_para_fi(chbPositivo_para_fi.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setPositivo_para_nm(chbPositivo_para_nm.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setPositivo_para_sp(chbPositivo_para_sp.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setNegativo(chbNegativo.isChecked()?"S":"N");
				ficha_epidemiologia_n16.setResultado_diferencial(tbxResultado_diferencial.getValue());
				ficha_epidemiologia_n16.setFecha_resultado(new Timestamp(dtbxFecha_resultado.getValue().getTime()));
				ficha_epidemiologia_n16.setObservaciones_seguimientos(tbxObservaciones_seguimientos.getValue());
				ficha_epidemiologia_n16.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n16.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n16.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n16.setDelete_date(null);
				ficha_epidemiologia_n16.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n16.setDelete_use(null);
				
				return ficha_epidemiologia_n16;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n16 obj)throws Exception{
		Ficha_epidemiologia_n16 ficha_epidemiologia_n16 = (Ficha_epidemiologia_n16)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n16.getCodigo_ficha());
			dtbxFecha_ficha.setValue(ficha_epidemiologia_n16.getFecha_ficha());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			//tbxNro_identificacion.setValue(ficha_epidemiologia_n16.getNro_identificacion());
			//tbxCodigo.setValue(ficha_epidemiologia_n16.getCodigo());
			Utilidades.seleccionarRadio(rdbVacuna_anti_hib, ficha_epidemiologia_n16.getVacuna_anti_hib());
			Utilidades.seleccionarRadio(rdbDosis_1, ficha_epidemiologia_n16.getDosis_1());
			dtbxFecha_primera_dosis_1.setValue(ficha_epidemiologia_n16.getFecha_primera_dosis_1());
			dtbxFecha_ultima_dosis_1.setValue(ficha_epidemiologia_n16.getFecha_ultima_dosis_1());
			Utilidades.seleccionarRadio(rdbTiene_carnet_1, ficha_epidemiologia_n16.getTiene_carnet_1());
			Utilidades.seleccionarRadio(rdbVacuna_anti_meningococo, ficha_epidemiologia_n16.getVacuna_anti_meningococo());
			Utilidades.seleccionarRadio(rdbDosis2, ficha_epidemiologia_n16.getDosis2());
			dtbxFecha_primera_dosis_2.setValue(ficha_epidemiologia_n16.getFecha_primera_dosis_2());
			dtbxFecha_ultima_dosis_2.setValue(ficha_epidemiologia_n16.getFecha_ultima_dosis_2());
			Utilidades.seleccionarRadio(rdbTiene_carnet_2, ficha_epidemiologia_n16.getTiene_carnet_2());
			Utilidades.seleccionarRadio(rdbVacuna_anti_neumococo, ficha_epidemiologia_n16.getVacuna_anti_neumococo());
			Utilidades.seleccionarRadio(rdbDosis_3, ficha_epidemiologia_n16.getDosis_3());
			dtbxFecha_primera_dosis_3.setValue(ficha_epidemiologia_n16.getFecha_primera_dosis_3());
			dtbxFecha_ultima_dosis_3.setValue(ficha_epidemiologia_n16.getFecha_ultima_dosis_3());
			Utilidades.seleccionarRadio(rdbTiene_carnet_3, ficha_epidemiologia_n16.getTiene_carnet_3());
			chbFiebre.setChecked(ficha_epidemiologia_n16.getFiebre().equals("S")?true:false);
			chbRigidez_de_nuca.setChecked(ficha_epidemiologia_n16.getRigidez_de_nuca().equals("S")?true:false);
			chbSignos_de_irritacion_meningea.setChecked(ficha_epidemiologia_n16.getSignos_de_irritacion_meningea().equals("S")?true:false);
			chbRash_purpurico_o_petequial.setChecked(ficha_epidemiologia_n16.getRash_purpurico_o_petequial().equals("S")?true:false);
			chbAbombamiento_de_fontanelas.setChecked(ficha_epidemiologia_n16.getAbombamiento_de_fontanelas().equals("S")?true:false);
			chbAlteracion_de_la_conciencia.setChecked(ficha_epidemiologia_n16.getAlteracion_de_la_conciencia().equals("S")?true:false);
			Utilidades.seleccionarRadio(rdbUso_antibioticos_en_la_ultima_semana, ficha_epidemiologia_n16.getUso_antibioticos_en_la_ultima_semana());
			tbxCual_antibioticos_ultima_semana.setValue(ficha_epidemiologia_n16.getCual_antibioticos_ultima_semana());
			dtbxFecha_ultima_dosis_4.setValue(ficha_epidemiologia_n16.getFecha_ultima_dosis_4());
			dtbxFecha_de_la_toma_1.setValue(ficha_epidemiologia_n16.getFecha_de_la_toma_1());
			dtbxFecha_de_la_toma_2.setValue(ficha_epidemiologia_n16.getFecha_de_la_toma_2());
			Utilidades.seleccionarRadio(rdbGram, ficha_epidemiologia_n16.getGram());
			Utilidades.seleccionarRadio(rdbCultivo_1, ficha_epidemiologia_n16.getCultivo_1());
			Utilidades.seleccionarRadio(rdbAntigenemia_1, ficha_epidemiologia_n16.getAntigenemia_1());
			Utilidades.seleccionarRadio(rdbCultivo_2, ficha_epidemiologia_n16.getCultivo_2());
			Utilidades.seleccionarRadio(rdbAntigenemia_2, ficha_epidemiologia_n16.getAntigenemia_2());
			chbPositivo_para_fi.setChecked(ficha_epidemiologia_n16.getPositivo_para_fi().equals("S")?true:false);
			chbPositivo_para_nm.setChecked(ficha_epidemiologia_n16.getPositivo_para_nm().equals("S")?true:false);
			chbPositivo_para_sp.setChecked(ficha_epidemiologia_n16.getPositivo_para_sp().equals("S")?true:false);
			chbNegativo.setChecked(ficha_epidemiologia_n16.getNegativo().equals("S")?true:false);
			tbxResultado_diferencial.setValue(ficha_epidemiologia_n16.getResultado_diferencial());
			dtbxFecha_resultado.setValue(ficha_epidemiologia_n16.getFecha_resultado());
			tbxObservaciones_seguimientos.setValue(ficha_epidemiologia_n16.getObservaciones_seguimientos());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n16 ficha_epidemiologia_n16 = (Ficha_epidemiologia_n16)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n16);
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
		tbxNro_identificacion.setValue(admision.getNro_identificacion());
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
	public Ficha_epidemiologia_n16 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n16> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n16.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n16 ficha_n16 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n16;
				}else{

					return null;
				}
	}
}