/*
 * negacionAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Negacion;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.interfaces.IEventoNegacionAutorizaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class NegacionAction extends ZKWindow {


	// Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;

	@View private Textbox tbxJustificacionClinica;
	@View private Textbox tbxFundamento_legal;
	@View private Textbox tbxAlternativa1;
	@View private Textbox tbxAlternativa2;
	@View private Textbox tbxAlternativa3;
	@View private Textbox tbxAlternativa4;
	
	@View private Toolbarbutton tbnAnularNegacion;
	
	// Los parametros enviados
	private  Map<String, Object> params;
	
	private IEventoNegacionAutorizaciones evento;

	
	@Override
	public void params(Map<String, Object> map) {
		// Cargamos parametros
		params = map;
		// Verificamos si trae una negacion para mostrarla
		Negacion negacion = (Negacion) map.get(IConstantesAutorizaciones.PARAM_NEGACION); 
		Boolean activo = (Boolean) map.get(IConstantesAutorizaciones.PARAM_EDICION_NEGACION);
		if(negacion != null){
			try {
				mostrarDatos(negacion);
			} catch (Exception e) { 
				MensajesUtil.mensajeError(e, null, this); 
			} 
		}
		if(activo != null && !activo){
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true, new String[]{"btnAtras"});
		}
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_solicitud");
		listitem.setLabel("Nro_solicitud");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	
	public void cancelar(){
		if(evento != null){
			evento.OnCancelarNegacion(this, params); 
		}
	}

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent)
							.setText(((Textbox) abstractComponent).getText()
									.trim().toUpperCase());
				}
			}
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals(
						"lbxParameter")) {
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setSelectedIndex(0);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setText("0.00");
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setText("");
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setValue(new Date());
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setChecked(false);
			}
			if (abstractComponent instanceof Radiogroup) {
				((Radio) ((Radiogroup) abstractComponent).getFirstChild())
						.setChecked(true);
			}
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		try {
			FormularioUtil.validarCamposObligatorios(tbxJustificacionClinica);
		} catch (WrongValueException e) { 
			valida = false;
		} 
		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getNegacionService().setLimit(
					"limit 25 offset 0");

			List<Negacion> lista_datos = getServiceLocator()
					.getNegacionService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Negacion negacion : lista_datos) {
				rowsResultado.appendChild(crearFilas(negacion, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Negacion negacion = (Negacion) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(negacion);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(negacion);
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

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				Negacion negacion = new Negacion();
				negacion.setCodigo_empresa(empresa.getCodigo_empresa());
				negacion.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				negacion.setFecha(new Timestamp(Calendar.getInstance()
						.getTimeInMillis()));
				negacion.setJustificacion(tbxJustificacionClinica.getValue());
				negacion.setFundamento_legal(tbxFundamento_legal.getValue());
				negacion.setAlternativa1(tbxAlternativa1.getValue());
				negacion.setAlternativa2(tbxAlternativa2.getValue());
				negacion.setAlternativa3(tbxAlternativa3.getValue());
				negacion.setAlternativa4(tbxAlternativa4.getValue());
				negacion.setCreacion_date(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				negacion.setUltimo_update(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				negacion.setCreacion_user(usuarios.getCodigo().toString());
				negacion.setUltimo_user(usuarios.getCodigo().toString());
				negacion.setLeido("N");
				negacion.setFecha_diligenciamiento(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));

				if(evento != null){
					evento.OnAgregarNegacion(this, negacion, params); 
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}
	
	@Override
	public void onClose() {
		if(evento != null){
			evento.OnCerrarNegacion(this, params); 
		}
		super.onClose();
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Negacion negacion = (Negacion) obj;
		try {
			tbxJustificacionClinica.setValue(negacion.getJustificacion());  
			tbxFundamento_legal.setValue(negacion.getFundamento_legal());
			tbxAlternativa1.setValue(negacion.getAlternativa1());
			tbxAlternativa2.setValue(negacion.getAlternativa2());
			tbxAlternativa3.setValue(negacion.getAlternativa3());
			tbxAlternativa4.setValue(negacion.getAlternativa4());

			tbnAnularNegacion.setVisible(true); 
			// Mostramos la vista //
//			tbxAccion.setText("modificar");
//			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Negacion negacion = (Negacion) obj;
		try {
			int result = getServiceLocator().getNegacionService().eliminar(
					negacion);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	
	@Override
	public void beforeInit() {
		if(getParent() instanceof IEventoNegacionAutorizaciones){
			evento = (IEventoNegacionAutorizaciones) getParent(); 
		}
	}

	@Override
	public void init() throws Exception {
		listarCombos();
	}
}
