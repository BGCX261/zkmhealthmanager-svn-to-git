/*
 * empresaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.service.GeneralExtraService;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class EmpresaAction extends ZKWindow{

//	private static Logger log = Logger.getLogger(EmpresaAction.class);
	
	
	private GeneralExtraService generalExtraService;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Listbox lbxTipo_identificacion;
	@View private Textbox tbxNro_identificacion;
	@View private Textbox tbxNombre_empresa;
	@View private Textbox tbxIdentificador;
	@View private Textbox tbxDireccion;
	@View private Textbox tbxTelefonos;
	@View private Textbox tbxFax;
	@View private Listbox lbxCodigo_dpto;
	@View private Listbox lbxCodigo_municipio;
	@View private Checkbox chbTrabaja_inventario;
	@View private Checkbox chbSaldos_negativos;
	@View private Checkbox chbManeja_contabilidad;
	@View private Textbox tbxGerente;
	@View private Listbox lbxNivel;
	@View private Listbox lbxRegimen;
	@View private Textbox tbxCodigo_habilitacion;
	@View private Textbox tbxEmailFinanciero;
	
	@View private Textbox tbxCodigo;
	
	@View private Image imageUsuario;
	@View private Textbox tbxTelefonoFinanciero;
	@View private Textbox tbxAvisoFacturacion;
	
	
	private final String[] idsExcluyentes = {"lbxParameter", "tbxValue", "tbxAccion", "rowsResultado"};
	
	@Override
	public void init(){
		listarCombos();
	}
	
	public void onCreate(){
		try {
			mostrarDatos(empresa);
		} catch (Exception e) { 
			e.printStackTrace();
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void listarCombos(){
		listarParameter();
		Utilidades.listarNivelEmpresa(lbxNivel, false);
		listarDepartamentos(lbxCodigo_dpto,true);
		listarMunicipios(lbxCodigo_municipio,lbxCodigo_dpto);
		Utilidades.listarElementoListbox(lbxRegimen, true, getServiceLocator()); 
		listarTipoEmpresa();
	}
	 
	private void listarTipoEmpresa() {
		String[][] tipos = { { "NI", "Nit" }, { "CC", "Cédula de Ciudadanía" },
				{ "CE", "Cédula de Extranjería" }, { "PA", "Pasaporte" } };	
		for (String[] tipo : tipos) {
			String codigo = tipo[0];
			String descripcion = tipo[1];
			
			Listitem listitem = new Listitem();
			listitem.setValue("" + codigo);
			listitem.setLabel("" + descripcion);
			lbxTipo_identificacion.appendChild(listitem);
		}
		
		if (lbxTipo_identificacion.getItemCount() > 0) {
			lbxTipo_identificacion.setSelectedIndex(0);
		}
	}

	public void listarDepartamentos(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		List<Departamentos> departamentos = this.getServiceLocator()
				.getDepartamentosService()
				.listar(new HashMap<String, Object>());

		for (Departamentos dpto : departamentos) {
			listitem = new Listitem();
			listitem.setValue(dpto.getCodigo());
			listitem.setLabel(dpto.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		listboxMun.getChildren().clear();
		Listitem listitem;
		String coddep = listboxDpto.getSelectedItem().getValue().toString();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("coddep", coddep);
		List<Municipios> municipios = this.getServiceLocator()
				.getMunicipiosService().listar(parameters);

		for (Municipios mun : municipios) {
			listitem = new Listitem();
			listitem.setValue(mun.getCodigo());
			listitem.setLabel(mun.getNombre());
			listboxMun.appendChild(listitem);
		}
		if (listboxMun.getItemCount() > 0) {
			listboxMun.setSelectedIndex(0);
		}
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("tipo_identificacion");
		listitem.setLabel("Tipo_identificacion");
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
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		   boolean valida = true;
		try {
			FormularioUtil.validarCamposObligatorios(tbxNro_identificacion, tbxNombre_empresa, lbxCodigo_dpto, 
					lbxCodigo_municipio, lbxRegimen, tbxCodigo_habilitacion);
		} catch (WrongValueException e) { 
			valida = false; 
		} 
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			List<Empresa> lista_datos = generalExtraService.listar(Empresa.class,parameters);
			rowsResultado.getChildren().clear();
			
			for (Empresa empresa : lista_datos) {
				rowsResultado.appendChild(crearFilas(empresa, this));
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
		
		final Empresa empresa = (Empresa)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(empresa.getTrabaja_inventario()+""));
		fila.appendChild(new Label(empresa.getSaldos_negativos()+""));
		fila.appendChild(new Label(empresa.getManeja_contabilidad()+""));
		fila.appendChild(new Label(empresa.getDelete_date()+""));
		fila.appendChild(new Label(empresa.getDelete_user()+""));
		fila.appendChild(new Label(empresa.getNivel()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(empresa);
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
								eliminarDatos(empresa);
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
	public void guardarDatos()throws Exception{
		try {
			FormularioUtil.setUpperCase(groupboxEditar, "tbxEmailFinanciero", "tbxAvisoFacturacion");
			if(validarForm()){
				//Cargamos los componentes //
				getEmpresa().setTipo_identificacion(lbxTipo_identificacion.getSelectedItem().getValue().toString());
				getEmpresa().setNro_identificacion(tbxNro_identificacion.getValue());
				getEmpresa().setNombre_empresa(tbxNombre_empresa.getValue().toUpperCase());
				getEmpresa().setIdentificador(tbxIdentificador.getValue());
				getEmpresa().setDireccion(tbxDireccion.getValue());
				getEmpresa().setTelefonos(tbxTelefonos.getValue());
				getEmpresa().setFax(tbxFax.getValue());
				getEmpresa().setCodigo_dpto(lbxCodigo_dpto.getSelectedItem().getValue().toString());
				getEmpresa().setCodigo_municipio(lbxCodigo_municipio.getSelectedItem().getValue().toString());
				getEmpresa().setTrabaja_inventario(chbTrabaja_inventario.isChecked() ? "S" : "N");
				getEmpresa().setSaldos_negativos(chbSaldos_negativos.isChecked()  ? "S" : "N");
				getEmpresa().setManeja_contabilidad(chbManeja_contabilidad.isChecked()  ? "S" : "N");
				getEmpresa().setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				getEmpresa().setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				getEmpresa().setCreacion_user(getUsuarios().getCodigo().toString());
				getEmpresa().setUltimo_user(getUsuarios().getCodigo().toString());
				getEmpresa().setNivel(lbxNivel.getSelectedItem().getValue().toString());
                getEmpresa().setRegimen(lbxRegimen.getSelectedItem().getValue().toString());
				getEmpresa().setGerente(tbxGerente.getValue());
				getEmpresa().setCodigo_habilitacion(tbxCodigo_habilitacion.getValue());
				getEmpresa().setFirma(imageUsuario.getContent() != null ? imageUsuario.getContent().getByteData() : null);
				getEmpresa().setEmail_departamento_financiero(tbxEmailFinanciero.getValue());
				getEmpresa().setTelefono_dpto_financiero(tbxTelefonoFinanciero.getValue()); 
				getEmpresa().setAviso_facturacion_capitada(tbxAvisoFacturacion.getValue()); 
				
				
//				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
//					getServiceLocator().getEmpresaService().crear(empresa);
//					accionForm(true,"registrar");
//				}else{
					int result = generalExtraService.actualizar(getEmpresa());
					if(result==0){
						throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
//				}
				
					Messagebox.show("Los datos se guardaron satisfactoriamente, se actualizara para que tome los cambios", "Informacion ..", Messagebox.OK,
							Messagebox.INFORMATION, new EventListener<Event>() {
								@Override
								public void onEvent(Event event) throws Exception {
									HttpServletRequest request = (HttpServletRequest)Executions.getCurrent().getNativeRequest();
									request.getSession().setAttribute("empresa", empresa);
									_recargar(); 
								}
							});
		}		
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Empresa empresa = (Empresa)obj;
		try{
			for(int i=0;i<lbxTipo_identificacion.getItemCount();i++){
				Listitem listitem = lbxTipo_identificacion.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(empresa.getTipo_identificacion())){
					listitem.setSelected(true);
					break;
				}
			}
		    tbxCodigo.setValue(empresa.getCodigo_empresa());
			tbxNro_identificacion.setValue(empresa.getNro_identificacion());
			tbxNombre_empresa.setValue(empresa.getNombre_empresa());
			tbxIdentificador.setValue(empresa.getIdentificador());
			tbxDireccion.setValue(empresa.getDireccion());
			tbxTelefonos.setValue(empresa.getTelefonos());
			tbxFax.setValue(empresa.getFax());
			Utilidades.setValueFrom(lbxRegimen, empresa.getRegimen()); 
			for(int i=0;i<lbxCodigo_dpto.getItemCount();i++){
				Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(empresa.getCodigo_dpto())){
					listitem.setSelected(true);
					break;
				}
			}
			listarMunicipios(lbxCodigo_municipio,lbxCodigo_dpto);
			for(int i=0;i<lbxNivel.getItemCount();i++){
				Listitem listitem = lbxNivel.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(empresa.getNivel())){
					listitem.setSelected(true);
					break;
				}
			}
			
			for(int i=0;i<lbxCodigo_municipio.getItemCount();i++){
				Listitem listitem = lbxCodigo_municipio.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(empresa.getCodigo_municipio())){
					listitem.setSelected(true);
					break;
				}
			}
			
			chbTrabaja_inventario.setChecked(empresa.getTrabaja_inventario().equalsIgnoreCase("S"));
			chbSaldos_negativos.setChecked(empresa.getSaldos_negativos().equalsIgnoreCase("S"));
			chbManeja_contabilidad.setChecked(empresa.getManeja_contabilidad().equalsIgnoreCase("S"));
			tbxGerente.setValue(empresa.getGerente());
			tbxCodigo_habilitacion.setValue(empresa.getCodigo_habilitacion()); 
			tbxEmailFinanciero.setValue(empresa.getEmail_departamento_financiero());
			tbxTelefonoFinanciero.setValue(empresa.getTelefono_dpto_financiero()); 
			tbxAvisoFacturacion.setValue(empresa.getAviso_facturacion_capitada());
			 
			if(empresa.getFirma() != null){
				imageUsuario.setContent(new AImage("firma_gerente", empresa.getFirma()));
			}
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Empresa empresa = (Empresa)obj;
		try{
			int result = generalExtraService.eliminar(empresa);
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
	
	/**
	 * Metodo para cargar la imagenes
	 * 
	 * @author EvanSandryHB
	 * */

	public void guardarImagen(Media media) throws Exception {
		try {
			if (media instanceof org.zkoss.image.Image) {
				AImage aImage = new AImage("firma_usr", media.getByteData());
				imageUsuario.setContent(aImage);
			} else {
				Messagebox.show("No es una foto: " + media, "Error",
						Messagebox.OK, Messagebox.EXCLAMATION);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error cargando foto!!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * Permite eliminar la imagen de la firma cargada
	 * */
	public void borrarImagen() throws Exception {
		imageUsuario.setSrc("");
	}
}