/*
 * registro_controlAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Registro_control;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
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
import org.zkoss.zul.Bandbox;
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
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;

public class Registro_controlAction extends Window implements AfterCompose {

	private static Logger log = Logger.getLogger(Registro_controlAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;
	// private Registro_control registro;

	private Window form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;

	private Textbox tbxCodigo_registro;
	private Bandbox tbxCodigo_eps;
	private Datebox dtbxFecha_inicial;
	private Datebox dtbxFecha_final;
	private Doublebox dbxNum_registros;
	private String codigo_registro;
	private Textbox tbxNombre_eps;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formRegistro_control");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

		tbxCodigo_registro = (Textbox) form.getFellow("tbxCodigo_registro");
		tbxCodigo_eps = (Bandbox) form.getFellow("tbxCodigo_eps");
		dtbxFecha_inicial = (Datebox) form.getFellow("dtbxFecha_inicial");
		dtbxFecha_final = (Datebox) form.getFellow("dtbxFecha_final");
		dbxNum_registros = (Doublebox) form.getFellow("dbxNum_registros");
		tbxNombre_eps = (Textbox) form.getFellow("tbxNombre_eps");

	}

	public void initRegistro_control() throws Exception {
//		HttpServletRequest request = (HttpServletRequest) Executions
//				.getCurrent().getNativeRequest();
		try {

			accionForm(true, "registrar");

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	/*
	 * public void crearPermisos(Integer id_menu)throws Exception{
	 * if(usuarios.getNivel().equals("01")){ permisos_sc = new Permisos_sc();
	 * permisos_sc.setId_menu(id_menu);
	 * permisos_sc.setId_usuario(usuarios.getId());
	 * permisos_sc.setPermiso_crear(true);
	 * permisos_sc.setPermiso_modificar(true);
	 * permisos_sc.setPermiso_consultar(true);
	 * permisos_sc.setPermiso_eliminar(true); }else{ permisos_sc = new
	 * Permisos_sc(); permisos_sc.setId_menu(id_menu);
	 * permisos_sc.setId_usuario(usuarios.getId()); permisos_sc =
	 * getServiceLocator().getPermisos_scService().consultar(permisos_sc); }
	 * if(!permisos_sc.getPermiso_consultar()){ accionForm(true,"registrar");
	 * ((Button)form.getFellow("btCancelar")).setDisabled(true);
	 * ((Button)form.getFellow
	 * ("btCancelar")).setImage("/images/cancel_disabled.jpg"); }
	 * if(!permisos_sc.getPermiso_crear()){
	 * ((Button)form.getFellow("btNuevo")).setDisabled(true);
	 * ((Button)form.getFellow
	 * ("btNuevo")).setImage("/images/New16_disabled.gif");
	 * ((Button)form.getFellow("btNew")).setDisabled(true);
	 * ((Button)form.getFellow("btNew")).setImage("/images/New16_disabled.gif");
	 * } }
	 */
	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
		// registro = getServiceLocator().getRegistro_control(request);

	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_registro");
		listitem.setLabel("Codigo_registro");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_eps");
		listitem.setLabel("Codigo_eps");
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

			getServiceLocator().getRegistro_controlService().setLimit(
					"limit 25 offset 0");

			List<Registro_control> lista_datos = getServiceLocator()
					.getRegistro_controlService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Registro_control registro_control : lista_datos) {
				rowsResultado.appendChild(crearFilas(registro_control, this));
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

		final Registro_control registro_control = (Registro_control) objeto;

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
				mostrarDatos(registro_control);
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
									eliminarDatos(registro_control);
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
				Map datos = new HashMap();

				Registro_control registro_control = new Registro_control();
				registro_control.setCodigo_empresa(empresa.getCodigo_empresa());
				registro_control.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				registro_control.setCodigo_registro(tbxCodigo_registro
						.getValue());
				registro_control.setCodigo_eps(tbxCodigo_eps.getValue());
				registro_control.setFecha_inicial(new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				registro_control.setFecha_final(new Timestamp(dtbxFecha_final
						.getValue().getTime()));
				registro_control
						.setNum_registros((dbxNum_registros.getValue() != null ? dbxNum_registros
								.getValue() : 0.00));

				datos.put("codigo_registro", registro_control);
				datos.put("accion", tbxAccion.getText());

				registro_control = getServiceLocator()
						.getRegistro_controlService().guardar(datos);

				// registro= registro_control;
				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {

					tbxAccion.setText("modificar");
				}

				codigo_registro = registro_control.getCodigo_registro();

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
		Registro_control registro_control = (Registro_control) obj;
		try {
			tbxCodigo_registro.setValue(registro_control.getCodigo_registro());
			// tbxCodigo_eps.setValue(registro_control.getCodigo_eps());
			dtbxFecha_inicial.setValue(registro_control.getFecha_inicial());
			dtbxFecha_final.setValue(registro_control.getFecha_final());
			dbxNum_registros.setValue(registro_control.getNum_registros());

			Administradora administradora = new Administradora();
			administradora.setCodigo(registro_control.getCodigo_eps());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

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
		Registro_control registro_control = (Registro_control) obj;
		try {
			int result = getServiceLocator().getRegistro_controlService()
					.eliminar(registro_control);
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

	public void verRegistros() throws Exception {
		try {

			guardarDatos();
			Map registro = new HashMap();
			registro.put("codigo_registro", codigo_registro);

			Component componente = Executions.createComponents(
					"registro_detalle.zul", form, registro);
			final Window ventana = (Window) componente;
			ventana.setWidth("990px");
			ventana.setHeight("400px");
			ventana.setClosable(true);
			ventana.setMaximizable(true);
			ventana.setTitle("CONSULTAR REGISTRO " + codigo_registro);
			ventana.setMode("modal");
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	public void buscarEps(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getOcupacionesService().setLimit(
					"limit 25 offset 0");

			List<Administradora> list = getServiceLocator()
					.getAdministradoraService().listar(parameters);

			lbx.getItems().clear();

			for (Administradora dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNit() + ""));
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

	public void selectedEps(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_eps.setValue("");
			tbxCodigo_eps.setValue("");

		} else {
			Administradora dato = (Administradora) listitem.getValue();
			tbxNombre_eps.setValue(dato.getNombre());
			tbxCodigo_eps.setValue(dato.getCodigo());

		}
		tbxCodigo_eps.close();
	}
}
