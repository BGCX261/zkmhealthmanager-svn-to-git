/*
 * ficha_epidemiologia_n43Action.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.bean.Ficha_epidemiologia_n43;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
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
import org.zkoss.zul.Listcell;
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
import healthmanager.modelo.service.GeneralExtraService;


public class Ficha_epidemiologia_n43Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n43>{

	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n43Action.class);
	
	
	
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
	@View private Radiogroup rdbCondicion;
	@View private Textbox tbxMotivo;
	@View private Radiogroup rdbAntecedentes_familiar;
	@View private Radiogroup rdbAtencion_medica;
	@View private Radiogroup rdbAtencion_especialista;
	@View private Datebox dtbxFecha_atencion_especialista;
	@View private Radiogroup rdbUso_psicofarmacos;
	@View private Textbox tbxCual_psicofarmacos;
	@View private Radiogroup rdbHospitalizacion;
	@View private Textbox tbxSitio_hospitalizacion;
	@View private Datebox dtbxFecha_hospitalizacion_inicio;
	@View private Datebox dtbxFecha_hospitalizacion_fin;
	@View private Bandbox bandboxAlcohol;
	@View private Bandbox bandboxApiaceos;
	@View private Bandbox bandboxCannabinoides;
	@View private Bandbox bandboxSedantes;
	@View private Bandbox bandboxCocaina;
	@View private Bandbox bandboxOtros_estimulantes;
	@View private Bandbox bandboxAlucinogenos;
	@View private Bandbox bandboxTabaco;
	@View private Bandbox bandboxDisolventes;
	@View private Bandbox bandboxMultiples_drogas;
	@View private Intbox ibxEdad_inicio;
	@View private Radiogroup rdbPoli_consumo;
	@View private Textbox tbxTipos_sustancias;
	@View private Radiogroup rdbConducta;
	@View private Textbox tbxOtra_conducta;
	@View private Textbox tbxObservaciones;
	@View private Textbox tbxCodigo_medico;
	private final String[] idsExcluyentes = {};

	@View private Toolbarbutton btGuardar;
	
	
	@View private Row rowHospitalizacion;
	@View private Row rowConducta;
	@View private Label lbFecha_atencion_especialista;
	@View private Label lbCual_psicofarmacos;
	@View private Label lbSitio_hospitalizacion;
	@View private Label lbFecha_hospitalizacion_inicio;
	@View private Label lbFecha_hospitalizacion_fin;
	@View private Label lbOtra_conducta;

	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception{
		listarCombos();
		obtenerAdmision(admision);
		FormularioUtil.inicializarRadiogroupsDefecto(this);

		//log.info("ficha-------> "+ficha_seleccionada);
		
		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n43 ficha = new Ficha_epidemiologia_n43();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n43) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 43-------> "+ficha);
			
			mostrarDatos(ficha);
		}
	}
	
	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
			//log.info("ficha_seleccionada"+ficha_seleccionada);
			
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
			//buscarDatos();
			btGuardar.setVisible(true);
			dtbxFecha_atencion_especialista.setValue(null);
			dtbxFecha_hospitalizacion_inicio.setValue(null);
			dtbxFecha_hospitalizacion_fin.setValue(null);
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
	public boolean validarFichaEpidemiologia(){
		
		
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
			
			List<Ficha_epidemiologia_n43> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n43.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n43 ficha_epidemiologia_n43 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n43, this));
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
		
		final Ficha_epidemiologia_n43 ficha_epidemiologia_n43 = (Ficha_epidemiologia_n43)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n43.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n43.getIdentificacion()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n43.getFecha_creacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n43);
			}
		});
		hbox.appendChild(img);
		
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n43 obtenerFichaEpidemiologia(){
		
				Ficha_epidemiologia_n43 ficha_epidemiologia_n43 = new Ficha_epidemiologia_n43();
				ficha_epidemiologia_n43.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n43.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n43.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n43.setIdentificacion(tbxIdentificacion.getValue());
				ficha_epidemiologia_n43.setFecha_creacion(new Timestamp(dtbxFecha_creacion.getValue().getTime()));
				ficha_epidemiologia_n43.setCodigo_diagnostico(tbxCodigo_diagnostico.getValue());
				ficha_epidemiologia_n43.setCondicion(rdbCondicion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n43.setMotivo(tbxMotivo.getValue());
				ficha_epidemiologia_n43.setAntecedentes_familiar(rdbAntecedentes_familiar.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n43.setAtencion_medica(rdbAtencion_medica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n43.setAtencion_especialista(rdbAtencion_especialista.getSelectedItem().getValue().toString());
				
				if (dtbxFecha_atencion_especialista.getValue() != null) {
					ficha_epidemiologia_n43.setFecha_atencion_especialista(new Timestamp(dtbxFecha_atencion_especialista.getValue().getTime()));
				}else{
					ficha_epidemiologia_n43.setFecha_atencion_especialista(null);
				}
				
				ficha_epidemiologia_n43.setUso_psicofarmacos(rdbUso_psicofarmacos.getSelectedItem().getValue().toString());
				
				ficha_epidemiologia_n43.setCual_psicofarmacos(tbxCual_psicofarmacos.getValue());
				ficha_epidemiologia_n43.setHospitalizacion(rdbHospitalizacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n43.setSitio_hospitalizacion(tbxSitio_hospitalizacion.getValue());
				
				if (dtbxFecha_hospitalizacion_inicio.getValue() != null) {
					ficha_epidemiologia_n43.setFecha_hospitalizacion_inicio(new Timestamp(dtbxFecha_hospitalizacion_inicio.getValue().getTime()));
				}else{
					ficha_epidemiologia_n43.setFecha_hospitalizacion_inicio(null);
				}
				
				
				if (dtbxFecha_hospitalizacion_fin.getValue() != null) {
					ficha_epidemiologia_n43.setFecha_hospitalizacion_fin(new Timestamp(dtbxFecha_hospitalizacion_fin.getValue().getTime()));
				}else{
					ficha_epidemiologia_n43.setFecha_hospitalizacion_fin(null);
				}
				
				ficha_epidemiologia_n43.setAlcohol(bandboxAlcohol.getValue());
				ficha_epidemiologia_n43.setApiaceos(bandboxApiaceos.getValue());
				ficha_epidemiologia_n43.setCannabinoides(bandboxCannabinoides.getValue());
				ficha_epidemiologia_n43.setSedantes(bandboxSedantes.getValue());
				ficha_epidemiologia_n43.setCocaina(bandboxCocaina.getValue());
				ficha_epidemiologia_n43.setOtros_estimulantes(bandboxOtros_estimulantes.getValue());
				ficha_epidemiologia_n43.setAlucinogenos(bandboxAlucinogenos.getValue());
				ficha_epidemiologia_n43.setTabaco(bandboxTabaco.getValue());
				ficha_epidemiologia_n43.setDisolventes(bandboxDisolventes.getValue());
				ficha_epidemiologia_n43.setMultiples_drogas(bandboxMultiples_drogas.getValue());
				ficha_epidemiologia_n43.setEdad_inicio((ibxEdad_inicio.getValue()!=null?ibxEdad_inicio.getValue()+"":""));
				ficha_epidemiologia_n43.setPoli_consumo(rdbPoli_consumo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n43.setTipos_sustancias(tbxTipos_sustancias.getValue());
				ficha_epidemiologia_n43.setConducta(rdbConducta.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n43.setOtra_conducta(tbxOtra_conducta.getValue());
				ficha_epidemiologia_n43.setObservaciones(tbxObservaciones.getValue());
				ficha_epidemiologia_n43.setCodigo_medico(tbxCodigo_medico.getValue());
				ficha_epidemiologia_n43.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n43.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n43.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n43.setDelete_date(null);
				ficha_epidemiologia_n43.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n43.setDelete_user(null);
				
			return ficha_epidemiologia_n43;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n43 obj)throws Exception{
		Ficha_epidemiologia_n43 ficha_epidemiologia_n43 = (Ficha_epidemiologia_n43)obj;
		try{
			//log.info("ficha_epidemiologia_n43.getCodigo_ficha()"+ficha_epidemiologia_n43.getCodigo_ficha());
			
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n43.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n43.getIdentificacion());
			
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			dtbxFecha_creacion.setValue(ficha_epidemiologia_n43.getFecha_creacion());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n43.getCodigo_diagnostico());
			
			Utilidades.seleccionarRadio(rdbCondicion, ficha_epidemiologia_n43.getCondicion());
			tbxMotivo.setValue(ficha_epidemiologia_n43.getMotivo());
			Utilidades.seleccionarRadio(rdbAntecedentes_familiar, ficha_epidemiologia_n43.getAntecedentes_familiar());
			Utilidades.seleccionarRadio(rdbAtencion_medica, ficha_epidemiologia_n43.getAtencion_medica());
			Utilidades.seleccionarRadio(rdbAtencion_especialista, ficha_epidemiologia_n43.getAtencion_especialista());
			if(ficha_epidemiologia_n43.getAtencion_especialista().equals("S")){
				lbFecha_atencion_especialista.setVisible(true);
				dtbxFecha_atencion_especialista.setVisible(true);
				
				dtbxFecha_atencion_especialista.setValue(ficha_epidemiologia_n43.getFecha_atencion_especialista());
			}else{
				lbFecha_atencion_especialista.setVisible(false);
				dtbxFecha_atencion_especialista.setVisible(false);
				
			}	
			Utilidades.seleccionarRadio(rdbUso_psicofarmacos, ficha_epidemiologia_n43.getUso_psicofarmacos());
			
			if(ficha_epidemiologia_n43.getUso_psicofarmacos().equals("S")){
				lbCual_psicofarmacos.setVisible(true);
				tbxCual_psicofarmacos.setVisible(true);
				tbxCual_psicofarmacos.setValue(ficha_epidemiologia_n43.getCual_psicofarmacos());
			}else{
				lbCual_psicofarmacos.setVisible(false);
				tbxCual_psicofarmacos.setVisible(false);
				
			}
			Utilidades.seleccionarRadio(rdbHospitalizacion, ficha_epidemiologia_n43.getHospitalizacion());
			
			if(ficha_epidemiologia_n43.getHospitalizacion().equals("S")){
				rowHospitalizacion.setVisible(true);
				lbSitio_hospitalizacion.setVisible(true);
				lbFecha_hospitalizacion_inicio.setVisible(true);
				lbFecha_hospitalizacion_fin.setVisible(true);
				tbxSitio_hospitalizacion.setVisible(true);
				tbxSitio_hospitalizacion.setValue(ficha_epidemiologia_n43.getSitio_hospitalizacion());
				dtbxFecha_hospitalizacion_inicio.setValue(ficha_epidemiologia_n43.getFecha_hospitalizacion_inicio());
				dtbxFecha_hospitalizacion_fin.setValue(ficha_epidemiologia_n43.getFecha_hospitalizacion_fin());
			}else{
				lbSitio_hospitalizacion.setVisible(false);
				lbFecha_hospitalizacion_inicio.setVisible(false);
				lbFecha_hospitalizacion_fin.setVisible(false);
				tbxSitio_hospitalizacion.setVisible(false);
				
			}
			bandboxAlcohol.setValue(ficha_epidemiologia_n43.getAlcohol());
			bandboxApiaceos.setValue(ficha_epidemiologia_n43.getApiaceos());
			bandboxCannabinoides.setValue(ficha_epidemiologia_n43.getCannabinoides());
			bandboxSedantes.setValue(ficha_epidemiologia_n43.getSedantes());
			bandboxCocaina.setValue(ficha_epidemiologia_n43.getCocaina());
			bandboxOtros_estimulantes.setValue(ficha_epidemiologia_n43.getOtros_estimulantes());
			bandboxAlucinogenos.setValue(ficha_epidemiologia_n43.getAlucinogenos());
			bandboxTabaco.setValue(ficha_epidemiologia_n43.getTabaco());
			bandboxDisolventes.setValue(ficha_epidemiologia_n43.getDisolventes());
			bandboxMultiples_drogas.setValue(ficha_epidemiologia_n43.getMultiples_drogas());
			ibxEdad_inicio.setValue((ficha_epidemiologia_n43.getEdad_inicio() != null && !ficha_epidemiologia_n43.getEdad_inicio().isEmpty()) ? Integer.parseInt(ficha_epidemiologia_n43.getEdad_inicio()) : null);
			Utilidades.seleccionarRadio(rdbPoli_consumo, ficha_epidemiologia_n43.getPoli_consumo());
			tbxTipos_sustancias.setValue(ficha_epidemiologia_n43.getTipos_sustancias());
			Utilidades.seleccionarRadio(rdbConducta, ficha_epidemiologia_n43.getConducta());
			
			if(ficha_epidemiologia_n43.getConducta().equals("S")){
				lbOtra_conducta.setVisible(true);
				tbxOtra_conducta.setVisible(true);
				tbxOtra_conducta.setValue(ficha_epidemiologia_n43.getOtra_conducta());
			}else{
				lbOtra_conducta.setVisible(false);
				tbxOtra_conducta.setVisible(false);
				
			}
			tbxObservaciones.setValue(ficha_epidemiologia_n43.getObservaciones());
			tbxCodigo_medico.setValue(ficha_epidemiologia_n43.getCodigo_medico());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n43 ficha_epidemiologia_n43 = (Ficha_epidemiologia_n43)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n43);
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
	
	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Cie> list = getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class, 
					parameters);

			lbx.getItems().clear();

			for (Cie dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getSexo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_inferior()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_superior()));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void selectedDx(Listitem listitem, Bandbox bandbox) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
		} else {
			Cie dato = (Cie) listitem.getValue();
			bandbox.setValue(dato.getCodigo());
		}
		bandbox.close();
	}
	
	public void mostrar_conRadio(ZKWindow form,Radiogroup radiogroup,AbstractComponent[] abstractComponents) {
		try {
			String valor = "S";
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
	public Ficha_epidemiologia_n43 consultarDatos(Map<String, Object> map,
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
		
		List<Ficha_epidemiologia_n43> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
				Ficha_epidemiologia_n43.class, parameters);
		
		if (!lista_datos.isEmpty()){
			Ficha_epidemiologia_n43 ficha_n43 = lista_datos.get(lista_datos.size() -1);
			
			return ficha_n43;
		}else{

			return null;
		}
		
	}

	
	
	
}