/*
 * registro_detalleAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.bean.Registro_control;
import healthmanager.modelo.bean.Registro_detalle;
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
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.util.Util;

public class Registro_detalleAction extends Window implements AfterCompose {

	private static Logger log = Logger.getLogger(Registro_detalleAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;
	private Registro_control registro;

	private Window form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;

	private Bandbox tbxNro_identificacion;
	private Textbox tbxNomPaciente;
	private Textbox tbxCodigo_detalle;
	private Textbox tbxTipoIdentificacion;
	private Datebox dbxNacimiento;
	private Textbox tbxEdad;
	private Textbox tbxSexo;

	private Textbox tbxPerEtnica;
	private Textbox tbxOcupacion;
	private Textbox tbxEducacion;

	private Textbox tbxDetalle;
	private Tabpanel tabpanelRiesgo;
	private Tabpanel tabpanelIntervenciones;
	private Tabpanel tabpanelNovedades;
	private Tab tabNovedades;
//	private Tab tabRiesgo;
	private Tab tabIntervenciones;
	private String codigo_detalle;
//	private String codigo_registro;

	// private Toolbar toolbar;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();

		try {
			initRegistro_detalle();
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
		}
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formRegistro_detalle");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

		tbxNro_identificacion = (Bandbox) form
				.getFellow("tbxNro_identificacion");
		tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
		tbxCodigo_detalle = (Textbox) form.getFellow("tbxCodigo_detalle");
		tbxTipoIdentificacion = (Textbox) form
				.getFellow("tbxTipoIdentificacion");
		tbxEdad = (Textbox) form.getFellow("tbxEdad");
		tbxSexo = (Textbox) form.getFellow("tbxSexo");
		dbxNacimiento = (Datebox) form.getFellow("dbxNacimiento");
		tbxPerEtnica = (Textbox) form.getFellow("tbxPerEtnica");
		tbxOcupacion = (Textbox) form.getFellow("tbxOcupacion");
		tbxEducacion = (Textbox) form.getFellow("tbxEducacion");

		tbxDetalle = (Textbox) form.getFellow("tbxDetalle");
		tabpanelRiesgo = (Tabpanel) form.getFellow("tabpanelRiesgo");
		tabpanelIntervenciones = (Tabpanel) form
				.getFellow("tabpanelIntervenciones");
		tabpanelNovedades = (Tabpanel) form.getFellow("tabpanelNovedades");
//		tabRiesgo = (Tab) form.getFellow("tabRiesgo");
		tabNovedades = (Tab) form.getFellow("tabNovedades");
		tabIntervenciones = (Tab) form.getFellow("tabIntervenciones");
	}

	public void initRegistro_detalle() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		try {
			String id_m = request.getParameter("id_menu");
			if (id_m != null) {
				// crearPermisos(new Integer(id_m));
			}

			Map mapRegistro = Executions.getCurrent().getArg();
			if (mapRegistro != null) {
				if (mapRegistro.get("codigo_registro") != null) {
					String codigo_registro = (String) mapRegistro
							.get("codigo_registro");
					registro = new Registro_control();
					registro.setCodigo_empresa(empresa.getCodigo_empresa());
					registro.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					registro.setCodigo_registro(codigo_registro);
					//log.info("antes: " + registro);
					registro = getServiceLocator().getRegistro_controlService()
							.consultar(registro);
					//log.info("despues: " + registro);
				}
			}

			Map parametros = Executions.getCurrent().getArg();
			if (parametros == null) {
				parametros = new HashMap();
			}
			String detalle = "1";

			//log.info("detalle: " + detalle);
			tbxDetalle.setValue(detalle);

			if (!tbxDetalle.getValue().equals("1")) {
				tabIntervenciones.setVisible(false);
				tabNovedades.setVisible(false);

			}

			/*
			 * cargarRiesgo();
			 * 
			 * if(tbxDetalle.getValue().equals("1")){ cargarIntervenciones(); }
			 */

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
			String codigo_registro = registro.getCodigo_registro();
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("codigo_registro", codigo_registro);

			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getRegistro_detalleService().setLimit(
					"limit 25 offset 0");

			List<Registro_detalle> lista_datos = getServiceLocator()
					.getRegistro_detalleService().listar(parameters);

			rowsResultado.getChildren().clear();

			for (Registro_detalle registro_detalle : lista_datos) {
				rowsResultado.appendChild(crearFilas(registro_detalle, this));
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

		final Registro_detalle registro_detalle = (Registro_detalle) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(registro_detalle.getCodigo_empresa());
		paciente.setCodigo_sucursal(registro_detalle.getCodigo_sucursal());
		paciente.setNro_identificacion(registro_detalle.getNro_identificacion());
		//log.info("antes: " + paciente);
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		//log.info("despues: " + paciente);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		// fila.appendChild(new
		// Label(registro_detalle.getCodigo_registro()+""));
		fila.appendChild(new Label(registro_detalle.getCodigo_detalle() + ""));
		fila.appendChild(new Label(registro_detalle.getNro_identificacion()
				+ ""));
		fila.appendChild(new Label(paciente != null ? paciente.getNombre1()
				+ " " + paciente.getApellido1() : ""));

		// tbxNomPaciente.setValue((paciente!=null?paciente.getNombre1()+" "+paciente.getApellido1():""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(registro_detalle);
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
									eliminarDatos(registro_detalle);
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

				Registro_detalle registro_detalle = new Registro_detalle();
				registro_detalle.setCodigo_empresa(empresa.getCodigo_empresa());
				registro_detalle.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				registro_detalle.setCodigo_registro(registro.getCodigo_registro());
				registro_detalle.setCodigo_detalle(tbxCodigo_detalle.getValue());
				registro_detalle.setNro_identificacion(tbxNro_identificacion.getValue());

				datos.put("codigo_detalle", registro_detalle);
				datos.put("accion", tbxAccion.getText());

				registro_detalle = getServiceLocator()
						.getRegistro_detalleService().guardar(datos);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					tbxAccion.setText("modificar");
				}
				/*
				 * else{ int result =
				 * getServiceLocator().getRegistro_detalleService
				 * ().actualizar(registro_detalle); if(result==0){ throw new
				 * Exception(
				 * "Lo sentimos pero los datos a modificar no se encuentran en base de datos"
				 * ); } }
				 */

				codigo_detalle = registro_detalle.getCodigo_detalle();

				Map detalle = new HashMap();
				detalle.put("codigo_registro", registro.getCodigo_registro());
				detalle.put("codigo_detalle", codigo_detalle);

				cargarRiesgo(detalle, registro_detalle);
				cargarIntervenciones(detalle);
				cargarNovedades(detalle);

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
		Registro_detalle registro_detalle = (Registro_detalle) obj;
		try {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(registro_detalle.getCodigo_empresa());
			paciente.setCodigo_sucursal(registro_detalle.getCodigo_sucursal());
			paciente.setNro_identificacion(registro_detalle
					.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			Elemento elemento = new Elemento();
			elemento.setCodigo(paciente.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			Pertenencia_etnica pertenencia_etnica = new Pertenencia_etnica();
			pertenencia_etnica.setId(paciente.getPertenencia_etnica());
			pertenencia_etnica = getServiceLocator()
					.getPertenencia_etnicaService().consultar(
							pertenencia_etnica);

			Ocupaciones ocupaciones = new Ocupaciones();
			ocupaciones.setId(paciente.getCodigo_ocupacion());
			ocupaciones = getServiceLocator().getOcupacionesService()
					.consultar(ocupaciones);

			Nivel_educativo nivel_educativo = new Nivel_educativo();
			nivel_educativo.setId(paciente.getCodigo_educativo());
			nivel_educativo = getServiceLocator().getNivel_educativoService()
					.consultar(nivel_educativo);

			tbxCodigo_detalle.setValue(registro_detalle.getCodigo_detalle());
			tbxNro_identificacion.setValue(registro_detalle
					.getNro_identificacion());
			tbxNomPaciente.setValue((paciente != null ? paciente.getNombre1()
					+ " " + paciente.getApellido1() : ""));
			tbxTipoIdentificacion.setValue((paciente != null ? paciente
					.getTipo_identificacion() : ""));
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
					paciente.getUnidad_medidad(), false));
			tbxSexo.setValue((elemento != null ? elemento.getDescripcion() : ""));
			dbxNacimiento.setValue(paciente.getFecha_nacimiento());
			tbxPerEtnica
					.setValue((pertenencia_etnica != null ? pertenencia_etnica
							.getNombre() : ""));
			tbxOcupacion.setValue((ocupaciones != null ? ocupaciones
					.getNombre() : ""));
			tbxEducacion.setValue((nivel_educativo != null ? nivel_educativo
					.getNombre() : ""));

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
		Registro_detalle registro_detalle = (Registro_detalle) obj;
		try {
			int result = getServiceLocator().getRegistro_detalleService()
					.eliminar(registro_detalle);
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

	public void buscarPaciente(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", 
					"limit 25 offset 0");

			List<Paciente> list = getServiceLocator().getPacienteService()
					.listar(parameters);

			lbx.getItems().clear();

			for (Paciente dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre1() + " "
						+ dato.getNombre2()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellido1() + " "
						+ dato.getApellido2()));
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

	public void selectedPaciente(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNro_identificacion.setValue("");
			tbxNomPaciente.setValue("");
			tbxTipoIdentificacion.setValue("");
			tbxEdad.setValue("");
			tbxSexo.setValue("");
			dbxNacimiento.setValue(null);
			tbxPerEtnica.setValue("");
			tbxOcupacion.setValue("");
			tbxEducacion.setValue("");

		} else {
			Paciente dato = (Paciente) listitem.getValue();

			Elemento elemento = new Elemento();
			elemento.setCodigo(dato.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);
			Pertenencia_etnica pertenencia_etnica = new Pertenencia_etnica();
			pertenencia_etnica.setId(dato.getPertenencia_etnica());
			pertenencia_etnica = getServiceLocator()
					.getPertenencia_etnicaService().consultar(
							pertenencia_etnica);

			Ocupaciones ocupaciones = new Ocupaciones();
			ocupaciones.setId(dato.getCodigo_ocupacion());
			ocupaciones = getServiceLocator().getOcupacionesService()
					.consultar(ocupaciones);

			Nivel_educativo nivel_educativo = new Nivel_educativo();
			nivel_educativo.setId(dato.getCodigo_educativo());
			nivel_educativo = getServiceLocator().getNivel_educativoService()
					.consultar(nivel_educativo);

			tbxNro_identificacion.setValue(dato.getNro_identificacion());
			tbxNomPaciente.setValue(dato.getNombre1() + " " + dato.getNombre2()
					+ " " + dato.getApellido1() + " " + dato.getApellido2());
			tbxTipoIdentificacion.setValue(dato.getTipo_identificacion());
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(dato.getFecha_nacimiento()), dato
					.getUnidad_medidad(), false));
			tbxSexo.setValue(elemento != null ? elemento.getDescripcion() : "");
			dbxNacimiento.setValue(dato.getFecha_nacimiento());
			tbxPerEtnica
					.setValue((pertenencia_etnica != null ? pertenencia_etnica
							.getNombre() : ""));
			tbxOcupacion.setValue((ocupaciones != null ? ocupaciones
					.getNombre() : ""));
			tbxEducacion.setValue((nivel_educativo != null ? nivel_educativo
					.getNombre() : ""));

		}

		tbxNro_identificacion.close();
	}

	public void cargarRiesgo(Map detalle, Object obj) {

		tabpanelRiesgo.getChildren().clear();
		Component componente = Executions.createComponents("/pages/riesgo.zul",
				form, detalle);
		final RiesgoAction ventana = (RiesgoAction) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelRiesgo.appendChild(ventana);

		/*
		 * Registro_detalle registro_detalle = (Registro_detalle)obj; Riesgo
		 * riesgo = new Riesgo();
		 * 
		 * riesgo.setCodigo_empresa(registro_detalle.getCodigo_empresa());
		 * riesgo.setCodigo_sucursal(registro_detalle.getCodigo_sucursal());
		 * riesgo.setCodigo_registro(registro_detalle.getCodigo_registro());
		 * riesgo.setCodigo_detalle(registro_detalle.getCodigo_sucursal());
		 * 
		 * riesgo = getServiceLocator().getRiesgoService().consultar(riesgo);
		 * 
		 * ventana.mostrarDatos(riesgo);
		 */

	}

	public void cargarIntervenciones(Map detalle) {

		tabpanelIntervenciones.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/riesgo_intervencion.zul", form, detalle);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelIntervenciones.appendChild(ventana);
	}

	public void cargarNovedades(Map detalle) {

		tabpanelNovedades.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/novedad.zul", form, detalle);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelNovedades.appendChild(ventana);
	}
}
