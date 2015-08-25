/*
 * terceroAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import contaweb.modelo.bean.Elemento;
import contaweb.modelo.bean.Tercero;

public class TerceroAction extends ZKWindow {

	private static Logger log = Logger.getLogger(TerceroAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	public static final String TYPE = "R_U";

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Groupbox groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Textbox tbxNro_identificacion;
	@View
	private Listbox lbxTipo_identificacion;
	@View
	private Textbox tbxNombre1;
	@View
	private Textbox tbxDireccion;
	@View
	private Textbox tbxTel_oficina;
	@View
	private Textbox tbxObservacion;
	@View
	private Intbox tbxDv;

	public void listarCombos() {
		listarParameter();
		listarElementoListbox(lbxTipo_identificacion, true);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Nro identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre1");
		listitem.setLabel("Nombre");
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
		List<Elemento> elementos = getServiceLocator()
				.getElemento_contService().listar(tipo);

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
		tbxNro_identificacion.setReadonly(false);
		tbxDv.setReadonly(false);
		resetView();
	}

	public void resetView() {
		tbxNro_identificacion
				.setStyle("text-transform:uppercase;background-color:white");
		lbxTipo_identificacion.setStyle("background-color:white");
		tbxNombre1.setStyle("text-transform:uppercase;background-color:white");
		tbxDv.setStyle("text-transform:uppercase;background-color:white");
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		resetView();

		boolean valida = true;

		if (tbxNro_identificacion.getText().trim().equals("")) {
			tbxNro_identificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (lbxTipo_identificacion.getSelectedItem().getValue().toString()
				.trim().equals("")) {
			lbxTipo_identificacion.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (tbxNombre1.getText().trim().equals("")) {
			tbxNombre1
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxDv.getText().trim().equals("")
				&& lbxTipo_identificacion.getSelectedItem().getValue()
						.toString().equals("NI")) {
			tbxDv.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

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
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("codigo_empresa", usuarios.getCodigo_empresa());
			parameters.put("codigo_sucursal", usuarios.getCodigo_sucursal());
			parameters.put("tipo", TYPE);

			getServiceLocator().getTerceroService().setLimit(
					"limit 25 offset 0");

			List<Tercero> lista_datos = null;

			lista_datos = getServiceLocator().getTerceroService().listar(
					parameters);
			rowsResultado.getChildren().clear();

			for (Tercero tercero : lista_datos) {
				rowsResultado.appendChild(crearFilas(tercero, this));
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

		final Tercero tercero = (Tercero) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(tercero.getDv() + ""));
		fila.appendChild(new Label(tercero.getNro_identificacion() + ""));
		fila.appendChild(new Label(tercero.getNombre1() + ""));
		fila.appendChild(new Label(tercero.getDireccion() + ""));
		fila.appendChild(new Label(tercero.getTel_oficina() + ""));
		fila.appendChild(new Label(
				tercero.getTipo().equals(TYPE) ? "RED UNIVERSITARIA"
						: "TIPO DE TERCERO NO VALIDO PARA ESTA VISTA"));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(tercero);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(tercero);
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
				Tercero tercero = new Tercero();
				tercero.setCodigo_empresa(usuarios.getCodigo_empresa());
				tercero.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				tercero.setNro_identificacion(tbxNro_identificacion.getValue());
				tercero.setTipo_identificacion(lbxTipo_identificacion
						.getSelectedItem().getValue().toString());
				tercero.setNombre1(tbxNombre1.getValue());
				tercero.setNombre2("");
				tercero.setApellido1("");
				tercero.setApellido2("");
				tercero.setDireccion(tbxDireccion.getValue());
				tercero.setTel_oficina(tbxTel_oficina.getValue());
				tercero.setTel_res("");
				tercero.setFax("");
				tercero.setContacto("");
				tercero.setEmail("");
				tercero.setCodigo_dpto("");
				tercero.setCodigo_municipio("");
				tercero.setZona_venta("");
				tercero.setCodigo_vendedor("");
				tercero.setTipo_contribuyente("");
				tercero.setObservacion(tbxObservacion.getValue());
				tercero.setCreacion_date(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				tercero.setUltimo_update(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				tercero.setCreacion_user(usuarios.getCodigo().toString());
				tercero.setUltimo_user(usuarios.getCodigo().toString());
				tercero.setEmpresa("");
				tercero.setDireccion_empresa("");
				tercero.setTelefono_empresa("");
				tercero.setCargo("");
				tercero.setTiempo_servicio("");
				tercero.setIngresos(0);
				tercero.setPropietario("");
				tercero.setDireccion_propietario("");
				tercero.setValor_arrendamiento(0);
				tercero.setTiempo_habita("");
				tercero.setBanco("");
				tercero.setTarifa_ica(0);
				tercero.setTipo(TYPE);
				tercero.setCuenta_cobrar("");
				tercero.setCuenta_pagar("");
				tercero.setDv(tbxDv.getText());
				tercero.setTipo_aseguradora("01");
				tercero.setCuenta_retencion("");

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getTerceroService().crear(tercero);
					accionForm(true, "registrar");
				} else {
					int result = 0;
					result = getServiceLocator().getTerceroService()
							.actualizar(tercero);
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
		Tercero tercero = (Tercero) obj;
		try {
			limpiarDatos();
			tbxNro_identificacion.setReadonly(true);
			tbxDv.setReadonly(true);
			tbxNro_identificacion.setValue(tercero.getNro_identificacion());
			for (int i = 0; i < lbxTipo_identificacion.getItemCount(); i++) {
				Listitem listitem = lbxTipo_identificacion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(tercero.getTipo_identificacion())) {
					listitem.setSelected(true);
					i = lbxTipo_identificacion.getItemCount();
				}
			}
			tbxNombre1.setValue(tercero.getNombre1());
			tbxDireccion.setValue(tercero.getDireccion());
			tbxTel_oficina.setValue(tercero.getTel_oficina());
			tbxObservacion.setValue(tercero.getObservacion());
			tbxDv.setText(tercero.getDv());

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
		Tercero tercero = (Tercero) obj;
		try {
			int result = getServiceLocator().getTerceroService().eliminar(
					tercero);
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
	public void init() {
		cargarDatosSesion();
		listarCombos();
	}
}
