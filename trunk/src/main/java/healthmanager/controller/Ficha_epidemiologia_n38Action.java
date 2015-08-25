/*
 * ficha_epidemiologia_n38Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n38;
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
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
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

public class Ficha_epidemiologia_n38Action  extends FichaEpidemiologiaModel<Ficha_epidemiologia_n38>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n38Action.class);
	
	
	//Componentes //
	@View private Textbox tbx_nro_identificacion;
	@View private Textbox tbx_tipo_identificacion;
	@View private Textbox tbx_nombres;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;

	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;  
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_ficha;
	@View private Datebox dtbxFecha_ficha;
	//@View private Radiogroup rdbDatos_recien_nacido;
	@View private Doublebox dbx_peso;
	@View private Doublebox tbxTalla;
	@View private Radiogroup rbdSexo;
	@View private Radiogroup rdbAborto;
	@View private Radiogroup rdbMortinato;
	@View private Radiogroup rdbPrematuridad;
	@View private Radiogroup rdbAlteraciones_del_lcr;
	@View private Radiogroup rdbLesiones_cutaneas_o_mucocutaneas;
	@View private Radiogroup rdbRinitis_serosanguinolenta;
	@View private Radiogroup rdbHepatoesplenomegalia;
	@View private Radiogroup rdbHidropesia;
	@View private Radiogroup rdbLesiones_oseas;
	@View private Radiogroup rdbAlteraciones_renales;
	@View private Radiogroup rdbAlteraciones_hematologicas;
	@View private Radiogroup rdbAlteraciones_de_la_funcion_hepatica;
	@View private Radiogroup rdbSordera;
	@View private Radiogroup rdbAlteraciones_oculares;
	@View private Radiogroup rdbAsintomatico;
	@View private Radiogroup rdbSe_tomo_serologia_al_nacimiento;
	@View private Checkbox chbDils_0;
	@View private Checkbox chbDils_1;
	@View private Checkbox chbDils_2;
	@View private Checkbox chbDils_4;
	@View private Checkbox chbDils_8;
	@View private Checkbox chbDils_16;
	@View private Checkbox chbDils_32;
	@View private Checkbox chbDils_64;
	@View private Checkbox chbDils_128;
	@View private Checkbox chbDils_256;
	@View private Checkbox chbDils_512;
	@View private Checkbox chbDils_1024;
	@View private Checkbox chbDils_2048;
	@View private Checkbox chbSin_dato;
	@View private Checkbox chbNo_reactivo;
	@View private Radiogroup rdbResultado_serologia_lcr;
	@View private Radiogroup rdbTratamiento;
	@View private Radiogroup rdbModalidad_tratamiento;
	@View private Radiogroup rdbMedicamento_administrado;
	@View private Textbox tbxOtro_medicamento_administrado;
	@View private Radiogroup rdbMotivo_de_no_tratamiento_del_nino;
	@View private Textbox tbxOtro_cual;
	@View private Row row_otro;
	@View private Row row_medicamento;
	private final String[] idsExcluyentes = {};
	private Admision admision;
	

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception{
		listarCombos();obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n38 ficha = new Ficha_epidemiologia_n38();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n38) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 38-------> "+ficha);
			
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
	public void mostrar_fila_medicamento(Radiogroup rg){
		if (rg.getSelectedItem().getValue().equals("3")){
			row_medicamento.setVisible(true);
		}else{
			row_medicamento.setVisible(false);
			tbxOtro_medicamento_administrado.setText("");
		}
	}
	public void mostrar_fila_tratameinto(Radiogroup rg_tratamiento){
		if(rg_tratamiento.getSelectedItem().getValue().equals("4")){
			row_otro.setVisible(true);
			
		}else{
			row_otro.setVisible(false);
			tbxOtro_cual.setText("");
		}
		
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
	boolean valida = true;
	
	// metodo para validar campos
	public boolean validarFichaEpidemiologia() {

		dtbxFecha_ficha.setStyle("background-color:white");
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:white");
		tbx_nro_identificacion.setStyle("background-color:white");

		boolean valida = true;

		if (dtbxFecha_ficha.getText().trim().equals("")) {
			dtbxFecha_ficha.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		// if(tbxNombres_y_apellidos.getText().trim().equals("")){
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		// valida = false;
		// }
		if (tbx_nro_identificacion.getText().trim().equals("")) {
			tbx_nro_identificacion.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
	}	//Metodo para buscar //
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

			List<Ficha_epidemiologia_n38> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n38.class, parameters);

			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n38 ficha_epidemiologia_n38 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n38,
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
		
		final Ficha_epidemiologia_n38 ficha_epidemiologia_n38 = (Ficha_epidemiologia_n38)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n38);
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
								eliminarDatos(ficha_epidemiologia_n38);
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
	
	public Ficha_epidemiologia_n38 obtenerFichaEpidemiologia() {
	
				Ficha_epidemiologia_n38 ficha_epidemiologia_n38 = new Ficha_epidemiologia_n38();
				ficha_epidemiologia_n38.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n38.setFecha_ficha(new Timestamp(dtbxFecha_ficha.getValue().getTime()));
				ficha_epidemiologia_n38.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n38.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n38.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n38.setNro_identificacion(admision != null ? admision
						.getNro_identificacion() : null);
				
				//ficha_epidemiologia_n38.setNro_identificacion();
				//ficha_epidemiologia_n38.setCodigo();
			//	ficha_epidemiologia_n38.setDatos_recien_nacido(rdbDatos_recien_nacido.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setPeso(dbx_peso.doubleValue());
				ficha_epidemiologia_n38.setTalla(tbxTalla.doubleValue());
				ficha_epidemiologia_n38.setSexo(rbdSexo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setAborto(rdbAborto.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setMortinato(rdbMortinato.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setPrematuridad(rdbPrematuridad.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setAlteraciones_del_lcr(rdbAlteraciones_del_lcr.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setLesiones_cutaneas_o_mucocutaneas(rdbLesiones_cutaneas_o_mucocutaneas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setRinitis_serosanguinolenta(rdbRinitis_serosanguinolenta.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setHepatoesplenomegalia(rdbHepatoesplenomegalia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setHidropesia(rdbHidropesia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setLesiones_oseas(rdbLesiones_oseas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setAlteraciones_renales(rdbAlteraciones_renales.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setAlteraciones_hematologicas(rdbAlteraciones_hematologicas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setAlteraciones_de_la_funcion_hepatica(rdbAlteraciones_de_la_funcion_hepatica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setSordera(rdbSordera.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setAlteraciones_oculares(rdbAlteraciones_oculares.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setAsintomatico(rdbAsintomatico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setSe_tomo_serologia_al_nacimiento(rdbSe_tomo_serologia_al_nacimiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setDils_0(chbDils_0.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_1(chbDils_1.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_2(chbDils_2.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_4(chbDils_4.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_8(chbDils_8.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_16(chbDils_16.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_32(chbDils_32.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_64(chbDils_64.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_128(chbDils_128.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_256(chbDils_256.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_512(chbDils_512.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_1024(chbDils_1024.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setDils_2048(chbDils_2048.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setSin_dato(chbSin_dato.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setNo_reactivo(chbNo_reactivo.isChecked()?"S":"N");
				ficha_epidemiologia_n38.setResultado_serologia_lcr(rdbResultado_serologia_lcr.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setTratamiento(rdbTratamiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setModalidad_tratamiento(rdbModalidad_tratamiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setMedicamento_administrado(rdbMedicamento_administrado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setOtro_medicamento_administrado(tbxOtro_medicamento_administrado.getValue());
				ficha_epidemiologia_n38.setMotivo_de_no_tratamiento_del_nino(rdbMotivo_de_no_tratamiento_del_nino.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n38.setOtro_cual(tbxOtro_cual.getValue());
				ficha_epidemiologia_n38.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n38.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				//ficha_epidemiologia_n38.setDelete_date();
				ficha_epidemiologia_n38.setUltimo_user(usuarios.getCodigo().toString());
				//ficha_epidemiologia_n38.setDelete_use();
				ficha_epidemiologia_n38.setCreacion_user(usuarios.getCodigo().toString());
				
				return ficha_epidemiologia_n38;	
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n38 obj)throws Exception{
		Ficha_epidemiologia_n38 ficha_epidemiologia_n38 = (Ficha_epidemiologia_n38)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n38.getCodigo_ficha());
			dtbxFecha_ficha.setValue(ficha_epidemiologia_n38.getFecha_ficha());

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			//Utilidades.seleccionarRadio(rdbDatos_recien_nacido, ficha_epidemiologia_n38.getDatos_recien_nacido());
			dbx_peso.setValue(ficha_epidemiologia_n38.getPeso());
			tbxTalla.setValue(ficha_epidemiologia_n38.getTalla());
			
			Utilidades.seleccionarRadio(rbdSexo, ficha_epidemiologia_n38.getSexo());
			Utilidades.seleccionarRadio(rdbAborto, ficha_epidemiologia_n38.getAborto());
			Utilidades.seleccionarRadio(rdbMortinato, ficha_epidemiologia_n38.getMortinato());
			Utilidades.seleccionarRadio(rdbPrematuridad, ficha_epidemiologia_n38.getPrematuridad());
			Utilidades.seleccionarRadio(rdbAlteraciones_del_lcr, ficha_epidemiologia_n38.getAlteraciones_del_lcr());
			Utilidades.seleccionarRadio(rdbLesiones_cutaneas_o_mucocutaneas, ficha_epidemiologia_n38.getLesiones_cutaneas_o_mucocutaneas());
			Utilidades.seleccionarRadio(rdbRinitis_serosanguinolenta, ficha_epidemiologia_n38.getRinitis_serosanguinolenta());
			Utilidades.seleccionarRadio(rdbHepatoesplenomegalia, ficha_epidemiologia_n38.getHepatoesplenomegalia());
			Utilidades.seleccionarRadio(rdbHidropesia, ficha_epidemiologia_n38.getHidropesia());
			Utilidades.seleccionarRadio(rdbLesiones_oseas, ficha_epidemiologia_n38.getLesiones_oseas());
			Utilidades.seleccionarRadio(rdbAlteraciones_renales, ficha_epidemiologia_n38.getAlteraciones_renales());
			Utilidades.seleccionarRadio(rdbAlteraciones_hematologicas, ficha_epidemiologia_n38.getAlteraciones_hematologicas());
			Utilidades.seleccionarRadio(rdbAlteraciones_de_la_funcion_hepatica, ficha_epidemiologia_n38.getAlteraciones_de_la_funcion_hepatica());
			Utilidades.seleccionarRadio(rdbSordera, ficha_epidemiologia_n38.getSordera());
			Utilidades.seleccionarRadio(rdbAlteraciones_oculares, ficha_epidemiologia_n38.getAlteraciones_oculares());
			Utilidades.seleccionarRadio(rdbAsintomatico, ficha_epidemiologia_n38.getAsintomatico());
			Utilidades.seleccionarRadio(rdbSe_tomo_serologia_al_nacimiento, ficha_epidemiologia_n38.getSe_tomo_serologia_al_nacimiento());
			chbDils_0.setChecked(ficha_epidemiologia_n38.getDils_0().equals("S")?true:false);
			chbDils_1.setChecked(ficha_epidemiologia_n38.getDils_1().equals("S")?true:false);
			chbDils_2.setChecked(ficha_epidemiologia_n38.getDils_2().equals("S")?true:false);
			chbDils_4.setChecked(ficha_epidemiologia_n38.getDils_4().equals("S")?true:false);
			chbDils_8.setChecked(ficha_epidemiologia_n38.getDils_8().equals("S")?true:false);
			chbDils_16.setChecked(ficha_epidemiologia_n38.getDils_16().equals("S")?true:false);
			chbDils_32.setChecked(ficha_epidemiologia_n38.getDils_32().equals("S")?true:false);
			chbDils_64.setChecked(ficha_epidemiologia_n38.getDils_64().equals("S")?true:false);
			chbDils_128.setChecked(ficha_epidemiologia_n38.getDils_128().equals("S")?true:false);
			chbDils_256.setChecked(ficha_epidemiologia_n38.getDils_256().equals("S")?true:false);
			chbDils_512.setChecked(ficha_epidemiologia_n38.getDils_512().equals("S")?true:false);
			chbDils_1024.setChecked(ficha_epidemiologia_n38.getDils_1024().equals("S")?true:false);
			chbDils_2048.setChecked(ficha_epidemiologia_n38.getDils_2048().equals("S")?true:false);
			chbSin_dato.setChecked(ficha_epidemiologia_n38.getSin_dato().equals("S")?true:false);
			chbNo_reactivo.setChecked(ficha_epidemiologia_n38.getNo_reactivo().equals("S")?true:false);
			Utilidades.seleccionarRadio(rdbResultado_serologia_lcr, ficha_epidemiologia_n38.getResultado_serologia_lcr());
			Utilidades.seleccionarRadio(rdbTratamiento, ficha_epidemiologia_n38.getTratamiento());
			Utilidades.seleccionarRadio(rdbModalidad_tratamiento, ficha_epidemiologia_n38.getModalidad_tratamiento());
			Utilidades.seleccionarRadio(rdbMedicamento_administrado, ficha_epidemiologia_n38.getMedicamento_administrado());
			tbxOtro_medicamento_administrado.setValue(ficha_epidemiologia_n38.getOtro_medicamento_administrado());
			Utilidades.seleccionarRadio(rdbMotivo_de_no_tratamiento_del_nino, ficha_epidemiologia_n38.getMotivo_de_no_tratamiento_del_nino());
			tbxOtro_cual.setValue(ficha_epidemiologia_n38.getOtro_cual());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n38 ficha_epidemiologia_n38 = (Ficha_epidemiologia_n38)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n38);
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
		tbx_nro_identificacion.setValue(admision.getNro_identificacion());
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
	public Ficha_epidemiologia_n38 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n38> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n38.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n38 ficha_n38 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n38;
				}else{

					return null;
				}
	}
}