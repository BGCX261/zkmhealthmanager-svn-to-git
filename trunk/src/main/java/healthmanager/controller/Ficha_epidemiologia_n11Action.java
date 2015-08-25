/*
 * ficha_epidemiologia_n11Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n11;
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

public class Ficha_epidemiologia_n11Action  extends FichaEpidemiologiaModel<Ficha_epidemiologia_n11>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n11Action.class);
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Textbox tbxNro_identificacion;
	@View private Textbox tbx_tipo_identificacion;
	@View private Textbox tbx_nombres;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	@View private Textbox codigo_ficha;
	@View private Datebox dtbxFecha_ficha;
	@View private Intbox ibxNumero_lesiones;
	@View private Checkbox chbCara;
	@View private Checkbox chbTronco;
	@View private Checkbox chbMiembros_superiores;
	@View private Checkbox chbMiembros_inferiores;
	@View private Intbox ibxTamano_lesion_1;
	@View private Intbox ibxTamano_lesion_2;
	@View private Intbox ibxTamano_lesion_3;
	@View private Intbox ibxTamano_lesion_4;
	@View private Intbox ibxTamano_lesion_5;
	@View private Intbox ibxTamano_lesion_6;
	@View private Radiogroup rdbHay_cicatrices_compatibles_con_forma_cutanea;
	@View private Intbox tbxTiempo;
	@View private Radiogroup rdbCuanto_tiempo_que_crerro_lesion_en_piel;
	@View private Radiogroup rdbAnteceedentes_de_trauma_cicatriz;
	@View private Radiogroup rdbMucosa_afectada;
	@View private Radiogroup rdbSignos_y_sintomas_mucosa;
	@View private Radiogroup rdbSignos_y_sintomas_visceral;
	@View private Radiogroup rdbPaciente_presenta_coinfeccion_vih;
	@View private Radiogroup rdbRecibio_tratamiento_anterior;
	@View private Intbox ibxNumero_tratamintos_anteriores;
	@View private Intbox ibxNumero_tratamientos_en_episodio_actual;
	@View private Radiogroup rdbEmbarzo_actual;
	@View private Intbox ibxPeso_actual_paciente;
	@View private Radiogroup rdbMedicamento_recibido_anteriormente;
	@View private Textbox tbxOtro_medicamento_anterior;
	@View private Radiogroup rdbMedicamento_formulado_actualmente;
	@View private Textbox tbxOtro_medicamente_actual;
	@View private Intbox ibxNumero_de_capsulas_o_volumen_diario_a_aplicar;
	@View private Intbox ibxDias_de_tratamiento;
	@View private Intbox ibxTotal_capsulas_o_ampollas;
	@View private Checkbox chbFrotis;
	@View private Checkbox chbTejido1;
	@View private Checkbox chbTejido2;
	@View private Checkbox chbSangre;
	@View private Checkbox chbAspirado1;
	@View private Checkbox chbAspirado2;
	@View private Checkbox chbTejido3;
	@View private Checkbox chbDirecto;
	@View private Checkbox chbBiopsia_piel;
	@View private Checkbox chbBiopsia_mucosa;
	@View private Checkbox chbTitulo_ifi;
	@View private Checkbox chbAspirado_brazo;
	@View private Checkbox chbAspirado_medula;
	@View private Checkbox chbPrueba_montenegro;
	@View private Checkbox chbPositivo_1;
	@View private Checkbox chbPositivo_2;
	@View private Checkbox chbPositivo_3;
	@View private Checkbox chbPositivo_4;
	@View private Checkbox chbPositivo_5;
	@View private Checkbox chbPositivo_6;
	@View private Checkbox chbNegativo_1;
	@View private Checkbox chbNegativo_2;
	@View private Checkbox chbNegativo_3;
	@View private Checkbox chbNegativo_4;
	@View private Checkbox chbNegativo_5;
	@View private Checkbox chbNegativo_6;
	@View private Checkbox chbCompatible_1;
	@View private Checkbox chbCompatible_2;
	@View private Row row1;
	@View private Row row2;
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
			Ficha_epidemiologia_n11 ficha = new Ficha_epidemiologia_n11();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n11) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 11-------> "+ficha);
			
			mostrarDatos(ficha);

			north_ficha.setVisible(true);
			btImprimir.setVisible(true);
			
		}else {

			north_ficha.setVisible(false);
			btImprimir.setVisible(false);
			
		}

	}
	
	public void listarCombos(){
		listarParameter();
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
		}
	}
	

	
	public void mostrarFila1(Radiogroup radio1){
		if (radio1.getSelectedItem().getValue().equals("5")){
			row1.setVisible(true);
		}else{
			row1.setVisible(false);
			tbxOtro_medicamento_anterior.setText("");
		}		
	}
	public void mostrarFila2(Radiogroup radio1){
		if (radio1.getSelectedItem().getValue().equals("5")){
			row2.setVisible(true);
		}else{
			row2.setVisible(false);
			tbxOtro_medicamente_actual.setText("");
		}
		
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("codgo_empresa");
		listitem.setLabel("Codgo_empresa");
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

			
			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
			
			List<Ficha_epidemiologia_n11> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n11.class, parameters);

			for (Ficha_epidemiologia_n11 ficha_epidemiologia_n11 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n11, this));
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
		
		final Ficha_epidemiologia_n11 ficha_epidemiologia_n11 = (Ficha_epidemiologia_n11)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n11.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n11.getCodigo_empresa()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n11);
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
								eliminarDatos(ficha_epidemiologia_n11);
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
	public Ficha_epidemiologia_n11 obtenerFichaEpidemiologia() {
			//Cargamos los componentes //
				
				Ficha_epidemiologia_n11 ficha_epidemiologia_n11 = new Ficha_epidemiologia_n11();
				ficha_epidemiologia_n11.setCodigo_ficha(codigo_ficha.getValue());
				ficha_epidemiologia_n11.setFecha_ficha(new Timestamp(dtbxFecha_ficha.getValue().getTime()));
				ficha_epidemiologia_n11.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n11.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				//ficha_epidemiologia_n11.setNro_identificacion();
				ficha_epidemiologia_n11.setCodigo("Z000");
				ficha_epidemiologia_n11.setNro_identificacion(tbxNro_identificacion.getValue());
			
				ficha_epidemiologia_n11.setNumero_lesiones((ibxNumero_lesiones.getValue()!=null?ibxNumero_lesiones.getValue():0));
				ficha_epidemiologia_n11.setCara(chbCara.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setTronco(chbTronco.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setMiembros_superiores(chbMiembros_superiores.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setMiembros_inferiores(chbMiembros_inferiores.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setTamano_lesion_1((ibxTamano_lesion_1.getValue()!=null?ibxTamano_lesion_1.getValue():0));
				ficha_epidemiologia_n11.setTamano_lesion_2((ibxTamano_lesion_2.getValue()!=null?ibxTamano_lesion_2.getValue():0));
				ficha_epidemiologia_n11.setTamano_lesion_3((ibxTamano_lesion_3.getValue()!=null?ibxTamano_lesion_3.getValue():0));
				ficha_epidemiologia_n11.setTamano_lesion_4((ibxTamano_lesion_4.getValue()!=null?ibxTamano_lesion_4.getValue():0));
				ficha_epidemiologia_n11.setTamano_lesion_5((ibxTamano_lesion_5.getValue()!=null?ibxTamano_lesion_5.getValue():0));
				ficha_epidemiologia_n11.setTamano_lesion_6((ibxTamano_lesion_6.getValue()!=null?ibxTamano_lesion_6.getValue():0));			
				ficha_epidemiologia_n11.setHay_cicatrices_compatibles_con_forma_cutanea(rdbHay_cicatrices_compatibles_con_forma_cutanea.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setTiempo((tbxTiempo.getValue()!=null?tbxTiempo.getValue():0));
				ficha_epidemiologia_n11.setCuanto_tiempo_que_crerro_lesion_en_piel(rdbCuanto_tiempo_que_crerro_lesion_en_piel.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setAnteceedentes_de_trauma_cicatriz(rdbAnteceedentes_de_trauma_cicatriz.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setMucosa_afectada(rdbMucosa_afectada.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setSignos_y_sintomas_mucosa(rdbSignos_y_sintomas_mucosa.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setSignos_y_sintomas_visceral(rdbSignos_y_sintomas_visceral.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setPaciente_presenta_coinfeccion_vih(rdbPaciente_presenta_coinfeccion_vih.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setRecibio_tratamiento_anterior(rdbRecibio_tratamiento_anterior.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setNumero_tratamintos_anteriores((ibxNumero_tratamintos_anteriores.getValue()!=null?ibxNumero_tratamintos_anteriores.getValue():0));
				ficha_epidemiologia_n11.setNumero_tratamientos_en_episodio_actual((ibxNumero_tratamientos_en_episodio_actual.getValue()!=null?ibxNumero_tratamientos_en_episodio_actual.getValue():0));
				ficha_epidemiologia_n11.setEmbarzo_actual(rdbEmbarzo_actual.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setPeso_actual_paciente((ibxPeso_actual_paciente.getValue()!=null?ibxPeso_actual_paciente.getValue():0));
				ficha_epidemiologia_n11.setMedicamento_recibido_anteriormente(rdbMedicamento_recibido_anteriormente.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setOtro_medicamento_anterior(tbxOtro_medicamento_anterior.getValue());
				ficha_epidemiologia_n11.setMedicamento_formulado_actualmente(rdbMedicamento_formulado_actualmente.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n11.setOtro_medicamente_actual(tbxOtro_medicamente_actual.getValue());
				ficha_epidemiologia_n11.setNumero_de_capsulas_o_volumen_diario_a_aplicar((ibxNumero_de_capsulas_o_volumen_diario_a_aplicar.getValue()!=null?ibxNumero_de_capsulas_o_volumen_diario_a_aplicar.getValue():0));
				ficha_epidemiologia_n11.setDias_de_tratamiento((ibxDias_de_tratamiento.getValue()!=null?ibxDias_de_tratamiento.getValue():0));
				ficha_epidemiologia_n11.setTotal_capsulas_o_ampollas((ibxTotal_capsulas_o_ampollas.getValue()!=null?ibxTotal_capsulas_o_ampollas.getValue():0));
				ficha_epidemiologia_n11.setFrotis(chbFrotis.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setTejido1(chbTejido1.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setTejido2(chbTejido2.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setSangre(chbSangre.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setAspirado1(chbAspirado1.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setAspirado2(chbAspirado2.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setTejido3(chbTejido3.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setDirecto(chbDirecto.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setBiopsia_piel(chbBiopsia_piel.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setBiopsia_mucosa(chbBiopsia_mucosa.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setTitulo_ifi(chbTitulo_ifi.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setAspirado_brazo(chbAspirado_brazo.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setAspirado_medula(chbAspirado_medula.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setPrueba_montenegro(chbPrueba_montenegro.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setPositivo_1(chbPositivo_1.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setPositivo_2(chbPositivo_2.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setPositivo_3(chbPositivo_3.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setPositivo_4(chbPositivo_4.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setPositivo_5(chbPositivo_5.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setPositivo_6(chbPositivo_6.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setNegativo_1(chbNegativo_1.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setNegativo_2(chbNegativo_2.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setNegativo_3(chbNegativo_3.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setNegativo_4(chbNegativo_4.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setNegativo_5(chbNegativo_5.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setNegativo_6(chbNegativo_6.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setCompatible_1(chbCompatible_1.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setCompatible_2(chbCompatible_2.isChecked()?"S":"N");
				ficha_epidemiologia_n11.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n11.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n11.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n11.setDelete_date(null);
				ficha_epidemiologia_n11.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n11.setDelete_use(null);
				 
				return ficha_epidemiologia_n11;
				
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n11 obj)throws Exception{
		Ficha_epidemiologia_n11 ficha_epidemiologia_n11 = (Ficha_epidemiologia_n11)obj;
		try{
			codigo_ficha.setValue(ficha_epidemiologia_n11.getCodigo_ficha());
			dtbxFecha_ficha.setValue(ficha_epidemiologia_n11.getFecha_ficha());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			ibxNumero_lesiones.setValue(ficha_epidemiologia_n11.getNumero_lesiones());
			chbCara.setChecked(ficha_epidemiologia_n11.getCara().equals("S")?true:false);
			chbTronco.setChecked(ficha_epidemiologia_n11.getTronco().equals("S")?true:false);
			chbMiembros_superiores.setChecked(ficha_epidemiologia_n11.getMiembros_superiores().equals("S")?true:false);
			chbMiembros_inferiores.setChecked(ficha_epidemiologia_n11.getMiembros_inferiores().equals("S")?true:false);
			ibxTamano_lesion_1.setValue(ficha_epidemiologia_n11.getTamano_lesion_1());
			ibxTamano_lesion_2.setValue(ficha_epidemiologia_n11.getTamano_lesion_2());
			ibxTamano_lesion_3.setValue(ficha_epidemiologia_n11.getTamano_lesion_3());
			ibxTamano_lesion_4.setValue(ficha_epidemiologia_n11.getTamano_lesion_4());
			ibxTamano_lesion_5.setValue(ficha_epidemiologia_n11.getTamano_lesion_5());
			ibxTamano_lesion_6.setValue(ficha_epidemiologia_n11.getTamano_lesion_6());
			Utilidades.seleccionarRadio(rdbHay_cicatrices_compatibles_con_forma_cutanea, ficha_epidemiologia_n11.getHay_cicatrices_compatibles_con_forma_cutanea());
			tbxTiempo.setValue(ficha_epidemiologia_n11.getTiempo());
			Utilidades.seleccionarRadio(rdbCuanto_tiempo_que_crerro_lesion_en_piel, ficha_epidemiologia_n11.getCuanto_tiempo_que_crerro_lesion_en_piel());
			Utilidades.seleccionarRadio(rdbAnteceedentes_de_trauma_cicatriz, ficha_epidemiologia_n11.getAnteceedentes_de_trauma_cicatriz());
			Utilidades.seleccionarRadio(rdbMucosa_afectada, ficha_epidemiologia_n11.getMucosa_afectada());
			Utilidades.seleccionarRadio(rdbSignos_y_sintomas_mucosa, ficha_epidemiologia_n11.getSignos_y_sintomas_mucosa());
			Utilidades.seleccionarRadio(rdbSignos_y_sintomas_visceral, ficha_epidemiologia_n11.getSignos_y_sintomas_visceral());
			Utilidades.seleccionarRadio(rdbPaciente_presenta_coinfeccion_vih, ficha_epidemiologia_n11.getPaciente_presenta_coinfeccion_vih());
			Utilidades.seleccionarRadio(rdbRecibio_tratamiento_anterior, ficha_epidemiologia_n11.getRecibio_tratamiento_anterior());
			ibxNumero_tratamintos_anteriores.setValue(ficha_epidemiologia_n11.getNumero_tratamintos_anteriores());
			ibxNumero_tratamientos_en_episodio_actual.setValue(ficha_epidemiologia_n11.getNumero_tratamientos_en_episodio_actual());
			Utilidades.seleccionarRadio(rdbEmbarzo_actual, ficha_epidemiologia_n11.getEmbarzo_actual());
			ibxPeso_actual_paciente.setValue(ficha_epidemiologia_n11.getPeso_actual_paciente());
			Utilidades.seleccionarRadio(rdbMedicamento_recibido_anteriormente, ficha_epidemiologia_n11.getMedicamento_recibido_anteriormente());
			tbxOtro_medicamento_anterior.setValue(ficha_epidemiologia_n11.getOtro_medicamento_anterior());
			Utilidades.seleccionarRadio(rdbMedicamento_formulado_actualmente, ficha_epidemiologia_n11.getMedicamento_formulado_actualmente());
			tbxOtro_medicamente_actual.setValue(ficha_epidemiologia_n11.getOtro_medicamente_actual());
			ibxNumero_de_capsulas_o_volumen_diario_a_aplicar.setValue(ficha_epidemiologia_n11.getNumero_de_capsulas_o_volumen_diario_a_aplicar());
			ibxDias_de_tratamiento.setValue(ficha_epidemiologia_n11.getDias_de_tratamiento());
			ibxTotal_capsulas_o_ampollas.setValue(ficha_epidemiologia_n11.getTotal_capsulas_o_ampollas());
			chbFrotis.setChecked(ficha_epidemiologia_n11.getFrotis().equals("S")?true:false);
			chbTejido1.setChecked(ficha_epidemiologia_n11.getTejido1().equals("S")?true:false);
			chbTejido2.setChecked(ficha_epidemiologia_n11.getTejido2().equals("S")?true:false);
			chbSangre.setChecked(ficha_epidemiologia_n11.getSangre().equals("S")?true:false);
			chbAspirado1.setChecked(ficha_epidemiologia_n11.getAspirado1().equals("S")?true:false);
			chbAspirado2.setChecked(ficha_epidemiologia_n11.getAspirado2().equals("S")?true:false);
			chbTejido3.setChecked(ficha_epidemiologia_n11.getTejido3().equals("S")?true:false);
			chbDirecto.setChecked(ficha_epidemiologia_n11.getDirecto().equals("S")?true:false);
			chbBiopsia_piel.setChecked(ficha_epidemiologia_n11.getBiopsia_piel().equals("S")?true:false);
			chbBiopsia_mucosa.setChecked(ficha_epidemiologia_n11.getBiopsia_mucosa().equals("S")?true:false);
			chbTitulo_ifi.setChecked(ficha_epidemiologia_n11.getTitulo_ifi().equals("S")?true:false);
			chbAspirado_brazo.setChecked(ficha_epidemiologia_n11.getAspirado_brazo().equals("S")?true:false);
			chbAspirado_medula.setChecked(ficha_epidemiologia_n11.getAspirado_medula().equals("S")?true:false);
			chbPrueba_montenegro.setChecked(ficha_epidemiologia_n11.getPrueba_montenegro().equals("S")?true:false);
			chbPositivo_1.setChecked(ficha_epidemiologia_n11.getPositivo_1().equals("S")?true:false);
			chbPositivo_2.setChecked(ficha_epidemiologia_n11.getPositivo_2().equals("S")?true:false);
			chbPositivo_3.setChecked(ficha_epidemiologia_n11.getPositivo_3().equals("S")?true:false);
			chbPositivo_4.setChecked(ficha_epidemiologia_n11.getPositivo_4().equals("S")?true:false);
			chbPositivo_5.setChecked(ficha_epidemiologia_n11.getPositivo_5().equals("S")?true:false);
			chbPositivo_6.setChecked(ficha_epidemiologia_n11.getPositivo_6().equals("S")?true:false);
			chbNegativo_1.setChecked(ficha_epidemiologia_n11.getNegativo_1().equals("S")?true:false);
			chbNegativo_2.setChecked(ficha_epidemiologia_n11.getNegativo_2().equals("S")?true:false);
			chbNegativo_3.setChecked(ficha_epidemiologia_n11.getNegativo_3().equals("S")?true:false);
			chbNegativo_4.setChecked(ficha_epidemiologia_n11.getNegativo_4().equals("S")?true:false);
			chbNegativo_5.setChecked(ficha_epidemiologia_n11.getNegativo_5().equals("S")?true:false);
			chbNegativo_6.setChecked(ficha_epidemiologia_n11.getNegativo_6().equals("S")?true:false);
			chbCompatible_1.setChecked(ficha_epidemiologia_n11.getCompatible_1().equals("S")?true:false);
			chbCompatible_2.setChecked(ficha_epidemiologia_n11.getCompatible_2().equals("S")?true:false);
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n11 ficha_epidemiologia_n11 = (Ficha_epidemiologia_n11)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n11);
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
	public Ficha_epidemiologia_n11 consultarDatos(Map<String, Object> map,
			Ficha_epidemiologia ficha_epidemiologia) throws Exception {
		
//		Ficha_epidemiologia ficha = (Ficha_epidemiologia)ficha_epidemiologia;
		
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
		
		List<Ficha_epidemiologia_n11> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
				Ficha_epidemiologia_n11.class, parameters);
		
		//log.info("lista_datos"+lista_datos);
		
		if (!lista_datos.isEmpty()){
			Ficha_epidemiologia_n11 ficha_n11 = lista_datos.get(lista_datos.size() -1);
			
			return ficha_n11;
		}else{

			return null;
		}
	}



	public void imprimir() throws Exception {
		
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Ficha_epidemiologia");
		paramRequest.put("empresa", empresa.getCodigo_empresa());
		paramRequest.put("sucursal", sucursal.getCodigo_sucursal());
		paramRequest.put("codigo_ficha", codigo_ficha.getValue());
		paramRequest.put("ficha_epidemiologia", IFichas_epidemiologicas.LEISHMANIASIS_CUTANEA);

		//log.info("codigo_ficha"+codigo_ficha.getValue());
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
	

}