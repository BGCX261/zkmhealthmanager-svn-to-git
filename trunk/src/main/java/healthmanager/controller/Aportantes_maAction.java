/*
 * aportantes_maAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Aportantes_ma;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Revision_ciiu;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.locator.ServiceLocatorWeb;

public class Aportantes_maAction extends ZKWindow {

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Listbox lbxTipo_identificacion;
	@View
	private Textbox tbxNro_identificacon;
	@View
	private Textbox tbxDv;
	@View
	private Textbox tbxNombre;
	@View
	private Listbox lbxTipo_aportante;
	@View
	private Listbox lbxSector_aportante;
	@View
	private Bandbox tbxCodigo_CIIU;
	@View
	private Textbox tbxNomCIIU;
	private Aportantes_ma aportantes_ma;

	public void listarCombos() {
		listarParameter();
		listarElementoListbox(lbxTipo_aportante, true);
		listarElementoListbox(lbxSector_aportante, true);
		listarElementoListboxTipoId(lbxTipo_identificacion, true);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nombre");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("tipo_identificacion");
		listitem.setLabel("Tipo identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacon");
		listitem.setLabel("Nro identificacon");
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

	public void listarElementoListboxTipoId(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();

		Map map = new HashMap();
		map.put("tipo", tipo);
		map.put("permitidos1", "_");

		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(map);

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

	public void buscarCIIU(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Revision_ciiu> list = getServiceLocator()
					.getRevisionCiiuService().listar(parameters);

			lbx.getItems().clear();

			for (Revision_ciiu dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getDescripcion() + ""));
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

	public void selectedCIIU(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxCodigo_CIIU.setValue("");
			tbxNomCIIU.setValue("");
		} else {
			Revision_ciiu dato = (Revision_ciiu) listitem.getValue();
			tbxCodigo_CIIU.setValue(dato.getCodigo());
			tbxNomCIIU.setValue(dato.getDescripcion());
		}
		tbxNomCIIU.setTooltiptext(tbxNomCIIU.getValue());
		tbxCodigo_CIIU.close();
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
		tbxNomCIIU.setTooltiptext(tbxNomCIIU.getValue());
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		lbxTipo_identificacion
				.setStyle("text-transform:uppercase;background-color:white");
		tbxNro_identificacon
				.setStyle("text-transform:uppercase;background-color:white");
		tbxNombre.setStyle("text-transform:uppercase;background-color:white");
		lbxTipo_aportante.setStyle("background-color:white");
		lbxSector_aportante.setStyle("background-color:white");
		// tbxCodigo_CIIU.setStyle("text-transform:uppercase;background-color:white");
		tbxDv.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		if (lbxTipo_identificacion.getSelectedItem().getValue().toString()
				.trim().equals("")) {
			lbxTipo_identificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		} else if (lbxTipo_identificacion.getSelectedItem().getValue()
				.toString().equals("NI")) {
			if (tbxDv.getValue().trim().isEmpty()) {
				tbxDv.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				valida = false;
			}
		}

		if (tbxNro_identificacon.getText().trim().equals("")) {
			tbxNro_identificacon
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxNombre.getText().trim().equals("")) {
			tbxNombre
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (lbxTipo_aportante.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxTipo_aportante.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (lbxSector_aportante.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxSector_aportante.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		// if(tbxCodigo_CIIU.getText().trim().equals("")){
		// tbxCodigo_CIIU.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		// valida = false;
		// }

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

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", value);
			parameters.put("codigo_empresa", usuarios.getCodigo_empresa());
			parameters.put("codigo_sucursal", usuarios.getCodigo_sucursal());

			getServiceLocator().getAportantesMaService().setLimit(
					"limit 25 offset 0");

			List<Aportantes_ma> lista_datos = getServiceLocator()
					.getAportantesMaService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Aportantes_ma aportantes_ma : lista_datos) {
				rowsResultado.appendChild(crearFilas(aportantes_ma, this));
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

		final Aportantes_ma aportantes_ma = (Aportantes_ma) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(aportantes_ma.getTipo_identificacion() + ""));
		fila.appendChild(new Label(aportantes_ma.getNro_identificacon() + ""));
		fila.appendChild(new Label(aportantes_ma.getNombre() + ""));
		fila.appendChild(new Label(getDescripcionElement(
				aportantes_ma.getTipo_aportante(), "tipo_aportante")
				+ ""));
		fila.appendChild(new Label(getDescripcionElement(
				aportantes_ma.getSector_aportante(), "sector_aportante")
				+ ""));
		fila.appendChild(new Label(getDescripcionCIIU(aportantes_ma
				.getRevision_ciiu()) + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(aportantes_ma);
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
									eliminarDatos(aportantes_ma);
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

	private String getDescripcionCIIU(String ciiu_codigo) {

		Revision_ciiu revisionCiiu = new Revision_ciiu();
		revisionCiiu.setCodigo_empresa(sucursal.getCodigo_empresa());
		revisionCiiu.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		revisionCiiu.setCodigo(ciiu_codigo);
		revisionCiiu = getServiceLocator().getRevisionCiiuService().consultar(
				revisionCiiu);
		return (revisionCiiu != null ? revisionCiiu.getDescripcion() : "");
	}

	private String getDescripcionElement(String codigo, String tipo) {
		Elemento elemento = new Elemento();
		elemento.setTipo(tipo);
		elemento.setCodigo(codigo);
		elemento = getServiceLocator().getElementoService().consultar(elemento);
		return elemento != null ? elemento.getDescripcion() : "";
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //

				Aportantes_ma aportantes_ma = new Aportantes_ma();
				aportantes_ma.setTipo_identificacion(lbxTipo_identificacion
						.getSelectedItem().getValue().toString());
				aportantes_ma.setNro_identificacon(tbxNro_identificacon
						.getValue());
				aportantes_ma.setCodigo_empresa(empresa.getCodigo_empresa());
				aportantes_ma.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				aportantes_ma.setDv(tbxDv.getValue());
				aportantes_ma.setNombre(tbxNombre.getValue());
				aportantes_ma.setTipo_aportante(lbxTipo_aportante
						.getSelectedItem().getValue().toString());
				aportantes_ma.setSector_aportante(lbxSector_aportante
						.getSelectedItem().getValue().toString());
				aportantes_ma.setRevision_ciiu(tbxCodigo_CIIU.getValue());
				aportantes_ma.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				aportantes_ma.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				aportantes_ma.setCreacion_user(usuarios.getCodigo().toString());
				aportantes_ma.setUltimo_user(usuarios.getCodigo().toString());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getAportantesMaService().crear(
							aportantes_ma);
					accionForm(true, "registrar");
				} else {
					// Cargamos el codigo del aporte cuando se valla a guardar
					aportantes_ma.setCodigo(this.aportantes_ma.getCodigo());
					int result = getServiceLocator().getAportantesMaService()
							.actualizar(aportantes_ma);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				Messagebox
						.show("Los datos se guardaron satisfactoriamente",
								"Informacion ..", Messagebox.OK,
								Messagebox.INFORMATION);

			}

		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	public void mostrarMsj(String msj, String title) throws Exception {
		Messagebox.show("" + msj, "" + title, Messagebox.OK,
				Messagebox.INFORMATION);
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		aportantes_ma = (Aportantes_ma) obj;
		try {
			tbxNro_identificacon.setValue(aportantes_ma.getNro_identificacon());
			tbxDv.setValue(aportantes_ma.getDv());
			tbxNombre.setValue(aportantes_ma.getNombre());

			for (int i = 0; i < lbxTipo_identificacion.getItemCount(); i++) {
				Listitem listitem = lbxTipo_identificacion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(aportantes_ma.getTipo_identificacion())) {
					listitem.setSelected(true);
					break;
				}
			}

			for (int i = 0; i < lbxTipo_aportante.getItemCount(); i++) {
				Listitem listitem = lbxTipo_aportante.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(aportantes_ma.getTipo_aportante())) {
					listitem.setSelected(true);
					break;
				}
			}
			for (int i = 0; i < lbxSector_aportante.getItemCount(); i++) {
				Listitem listitem = lbxSector_aportante.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(aportantes_ma.getSector_aportante())) {
					listitem.setSelected(true);
					break;
				}
			}
			tbxCodigo_CIIU.setValue(aportantes_ma.getRevision_ciiu());
			cargarCIIU(aportantes_ma);
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void cargarCIIU(Aportantes_ma aportantesMa) {
		Revision_ciiu revisionCiiu = new Revision_ciiu();
		revisionCiiu.setCodigo_empresa(sucursal.getCodigo_empresa());
		revisionCiiu.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		revisionCiiu.setCodigo(aportantesMa.getRevision_ciiu());
		revisionCiiu = getServiceLocator().getRevisionCiiuService().consultar(
				revisionCiiu);
		tbxNomCIIU.setValue(revisionCiiu != null ? revisionCiiu
				.getDescripcion() : "");
		tbxNomCIIU.setTooltiptext(tbxNomCIIU.getValue());
	}

	public void eliminarDatos(Object obj) throws Exception {
		Aportantes_ma aportantes_ma = (Aportantes_ma) obj;
		try {
			int result = getServiceLocator().getAportantesMaService().eliminar(
					aportantes_ma);
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

	@Override
	public void init() throws Exception {
		listarCombos();
	}
}
