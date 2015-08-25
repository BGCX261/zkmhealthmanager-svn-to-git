/*
 * riesgoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Registro_detalle;
import healthmanager.modelo.bean.Riesgo;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
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
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;

public class RiesgoAction extends Window implements AfterCompose {

	private static Logger log = Logger.getLogger(RiesgoAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

//	private ServiceLocator serviceLocator;

	// private Permisos_sc permisos_sc;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;
	private Registro_detalle registro;

	private Window form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;

	private Listbox lbxGestacion;
	private Listbox lbxSifilis_gestacional;
	private Listbox lbxHipertension;
	private Listbox lbxHipotiroidismo;
	private Listbox lbxSintomatico;
	private Listbox lbxTuberculosis;
	private Listbox lbxLepra;
	private Listbox lbxObesidad;
	private Listbox lbxMaltrato;
	private Listbox lbxViolencia;
	private Listbox lbxInfeccion;
	private Listbox lbxEnfermedad_mental;
	private Listbox lbxCancer_cervix;
	private Listbox lbxCancer_seno;
	private Listbox lbxFluorosis;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();

		try {
			initRiesgo();
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
		}
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formRiesgo");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

		lbxGestacion = (Listbox) form.getFellow("lbxGestacion");
		lbxSifilis_gestacional = (Listbox) form
				.getFellow("lbxSifilis_gestacional");
		lbxHipertension = (Listbox) form.getFellow("lbxHipertension");
		lbxHipotiroidismo = (Listbox) form.getFellow("lbxHipotiroidismo");
		lbxSintomatico = (Listbox) form.getFellow("lbxSintomatico");
		lbxTuberculosis = (Listbox) form.getFellow("lbxTuberculosis");
		lbxLepra = (Listbox) form.getFellow("lbxLepra");
		lbxObesidad = (Listbox) form.getFellow("lbxObesidad");
		lbxMaltrato = (Listbox) form.getFellow("lbxMaltrato");
		lbxViolencia = (Listbox) form.getFellow("lbxViolencia");
		lbxInfeccion = (Listbox) form.getFellow("lbxInfeccion");
		lbxEnfermedad_mental = (Listbox) form.getFellow("lbxEnfermedad_mental");
		lbxCancer_cervix = (Listbox) form.getFellow("lbxCancer_cervix");
		lbxCancer_seno = (Listbox) form.getFellow("lbxCancer_seno");
		lbxFluorosis = (Listbox) form.getFellow("lbxFluorosis");

	}

	public void initRiesgo() throws Exception {
//		HttpServletRequest request = (HttpServletRequest) Executions
//				.getCurrent().getNativeRequest();
		try {
			accionForm(true, "registrar");

			Map mapRegistro = Executions.getCurrent().getArg();
			if (mapRegistro != null) {
				if (mapRegistro.get("codigo_registro") != null) {
					String codigo_registro = (String) mapRegistro
							.get("codigo_registro");
					String codigo_detalle = (String) mapRegistro
							.get("codigo_detalle");
					registro = new Registro_detalle();
					registro.setCodigo_empresa(empresa.getCodigo_empresa());
					registro.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					registro.setCodigo_registro(codigo_registro);
					registro.setCodigo_detalle(codigo_detalle);
					//log.info("antes: " + registro);
					registro = getServiceLocator().getRegistro_detalleService()
							.consultar(registro);
					//log.info("despues: " + registro);
				}
			}

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}

	public void listarCombos() {
		listarParameter();
		listarElementoListbox(lbxGestacion, true);
		listarElementoListbox(lbxSifilis_gestacional, true);
		listarElementoListbox(lbxHipertension, true);
		listarElementoListbox(lbxHipotiroidismo, true);
		listarElementoListbox(lbxSintomatico, true);
		listarElementoListbox(lbxTuberculosis, true);
		listarElementoListbox(lbxLepra, true);
		listarElementoListbox(lbxObesidad, true);
		listarElementoListbox(lbxMaltrato, true);
		listarElementoListbox(lbxViolencia, true);
		listarElementoListbox(lbxInfeccion, true);
		listarElementoListbox(lbxEnfermedad_mental, true);
		listarElementoListbox(lbxCancer_cervix, true);
		listarElementoListbox(lbxCancer_seno, true);
		listarElementoListbox(lbxFluorosis, true);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_registro");
		listitem.setLabel("Codigo_registro");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_detalle");
		listitem.setLabel("Codigo_detalle");
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
		/*
		 * if(permisos_sc.getPermiso_crear()){
		 * ((Button)form.getFellow("btGuardar")).setDisabled(false);
		 * ((Button)form.getFellow("btGuardar")).setImage("/images/Save16.gif");
		 * }else{ ((Button)form.getFellow("btGuardar")).setDisabled(true);
		 * ((Button
		 * )form.getFellow("btGuardar")).setImage("/images/Save16_disabled.gif"
		 * ); }
		 */
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String codigo_empresa = empresa.getCodigo_empresa();
			String codigo_sucursal = sucursal.getCodigo_sucursal();
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getRiesgoService().setLimit(
					"limit 25 offset 0");

			List<Riesgo> lista_datos = getServiceLocator().getRiesgoService()
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Riesgo riesgo : lista_datos) {
				rowsResultado.appendChild(crearFilas(riesgo, this));
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

		final Riesgo riesgo = (Riesgo) objeto;

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
				/*
				 * if(!permisos_sc.getPermiso_modificar()){
				 * ((Button)form.getFellow("btGuardar")).setDisabled(true);
				 * ((Button)form.getFellow("btGuardar")).setImage(
				 * "/images/Save16_disabled.gif"); }
				 */
				mostrarDatos(riesgo);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		// img.setVisible((permisos_sc.getPermiso_eliminar()?true:false));
		img.setSrc("/images/eliminar.gif");
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
									eliminarDatos(riesgo);
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
				// Cargamos los componentes //

				Riesgo riesgo = new Riesgo();
				riesgo.setCodigo_empresa(empresa.getCodigo_empresa());
				riesgo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				riesgo.setCodigo_registro(registro.getCodigo_registro());
				riesgo.setCodigo_detalle(registro.getCodigo_detalle());
				riesgo.setGestacion(lbxGestacion.getSelectedItem().getValue()
						.toString());
				riesgo.setSifilis_gestacional(lbxSifilis_gestacional
						.getSelectedItem().getValue().toString());
				riesgo.setHipertension(lbxHipertension.getSelectedItem()
						.getValue().toString());
				riesgo.setHipotiroidismo(lbxHipotiroidismo.getSelectedItem()
						.getValue().toString());
				riesgo.setSintomatico(lbxSintomatico.getSelectedItem()
						.getValue().toString());
				riesgo.setTuberculosis(lbxTuberculosis.getSelectedItem()
						.getValue().toString());
				riesgo.setLepra(lbxLepra.getSelectedItem().getValue()
						.toString());
				riesgo.setObesidad(lbxObesidad.getSelectedItem().getValue()
						.toString());
				riesgo.setMaltrato(lbxMaltrato.getSelectedItem().getValue()
						.toString());
				riesgo.setViolencia(lbxViolencia.getSelectedItem().getValue()
						.toString());
				riesgo.setInfeccion(lbxInfeccion.getSelectedItem().getValue()
						.toString());
				riesgo.setEnfermedad_mental(lbxEnfermedad_mental
						.getSelectedItem().getValue().toString());
				riesgo.setCancer_cervix(lbxCancer_cervix.getSelectedItem()
						.getValue().toString());
				riesgo.setCancer_seno(lbxCancer_seno.getSelectedItem()
						.getValue().toString());
				riesgo.setFluorosis(lbxFluorosis.getSelectedItem().getValue()
						.toString());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getRiesgoService().crear(riesgo);
					accionForm(true, "registrar");
				} else {
					int result = getServiceLocator().getRiesgoService()
							.actualizar(riesgo);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				Messagebox.show("Los datos se guardaron satisfactoriamente",
						"Informacion ..", Messagebox.OK,
						Messagebox.INFORMATION);

			}

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Riesgo riesgo = (Riesgo) obj;
		try {
			for (int i = 0; i < lbxGestacion.getItemCount(); i++) {
				Listitem listitem = lbxGestacion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getGestacion())) {
					listitem.setSelected(true);
					i = lbxGestacion.getItemCount();
				}
			}
			for (int i = 0; i < lbxSifilis_gestacional.getItemCount(); i++) {
				Listitem listitem = lbxSifilis_gestacional.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getSifilis_gestacional())) {
					listitem.setSelected(true);
					i = lbxSifilis_gestacional.getItemCount();
				}
			}
			for (int i = 0; i < lbxHipertension.getItemCount(); i++) {
				Listitem listitem = lbxHipertension.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getHipertension())) {
					listitem.setSelected(true);
					i = lbxHipertension.getItemCount();
				}
			}
			for (int i = 0; i < lbxHipotiroidismo.getItemCount(); i++) {
				Listitem listitem = lbxHipotiroidismo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getHipotiroidismo())) {
					listitem.setSelected(true);
					i = lbxHipotiroidismo.getItemCount();
				}
			}
			for (int i = 0; i < lbxSintomatico.getItemCount(); i++) {
				Listitem listitem = lbxSintomatico.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getSintomatico())) {
					listitem.setSelected(true);
					i = lbxSintomatico.getItemCount();
				}
			}
			for (int i = 0; i < lbxTuberculosis.getItemCount(); i++) {
				Listitem listitem = lbxTuberculosis.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getTuberculosis())) {
					listitem.setSelected(true);
					i = lbxTuberculosis.getItemCount();
				}
			}
			for (int i = 0; i < lbxLepra.getItemCount(); i++) {
				Listitem listitem = lbxLepra.getItemAtIndex(i);
				if (listitem.getValue().toString().equals(riesgo.getLepra())) {
					listitem.setSelected(true);
					i = lbxLepra.getItemCount();
				}
			}
			for (int i = 0; i < lbxObesidad.getItemCount(); i++) {
				Listitem listitem = lbxObesidad.getItemAtIndex(i);
				if (listitem.getValue().toString().equals(riesgo.getObesidad())) {
					listitem.setSelected(true);
					i = lbxObesidad.getItemCount();
				}
			}
			for (int i = 0; i < lbxMaltrato.getItemCount(); i++) {
				Listitem listitem = lbxMaltrato.getItemAtIndex(i);
				if (listitem.getValue().toString().equals(riesgo.getMaltrato())) {
					listitem.setSelected(true);
					i = lbxMaltrato.getItemCount();
				}
			}
			for (int i = 0; i < lbxViolencia.getItemCount(); i++) {
				Listitem listitem = lbxViolencia.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getViolencia())) {
					listitem.setSelected(true);
					i = lbxViolencia.getItemCount();
				}
			}
			for (int i = 0; i < lbxInfeccion.getItemCount(); i++) {
				Listitem listitem = lbxInfeccion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getInfeccion())) {
					listitem.setSelected(true);
					i = lbxInfeccion.getItemCount();
				}
			}
			for (int i = 0; i < lbxEnfermedad_mental.getItemCount(); i++) {
				Listitem listitem = lbxEnfermedad_mental.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getEnfermedad_mental())) {
					listitem.setSelected(true);
					i = lbxEnfermedad_mental.getItemCount();
				}
			}
			for (int i = 0; i < lbxCancer_cervix.getItemCount(); i++) {
				Listitem listitem = lbxCancer_cervix.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getCancer_cervix())) {
					listitem.setSelected(true);
					i = lbxCancer_cervix.getItemCount();
				}
			}
			for (int i = 0; i < lbxCancer_seno.getItemCount(); i++) {
				Listitem listitem = lbxCancer_seno.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getCancer_seno())) {
					listitem.setSelected(true);
					i = lbxCancer_seno.getItemCount();
				}
			}
			for (int i = 0; i < lbxFluorosis.getItemCount(); i++) {
				Listitem listitem = lbxFluorosis.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(riesgo.getFluorosis())) {
					listitem.setSelected(true);
					i = lbxFluorosis.getItemCount();
				}
			}

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Riesgo riesgo = (Riesgo) obj;
		try {
			int result = getServiceLocator().getRiesgoService()
					.eliminar(riesgo);
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

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}
}
