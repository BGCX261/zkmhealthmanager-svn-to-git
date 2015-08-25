package healthmanager.controller;

import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Anexo10_entidad;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
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
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.CargardorDeDatos;

public class ListadoContraremisionesAction extends Window implements
		AfterCompose {

	private static Logger log = Logger.getLogger(Anexo9_entidadAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

//	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	private Window form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;
	@View
	Listbox lbxSearh;
	@View
	private Timer timer;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();
		initTime();
		try {
			buscarDatos();
		} catch (Exception e) {
		}
	}

	private void initTime() {
		//System.Out.Println("timer accion");
		timer.addEventListener("onTimer", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				buscarDatos();
			}
		});
		timer.start();
		//System.Out.Println("timer stared");
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formListadoContraremisiones");
		CargardorDeDatos.initComponents(this);

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		// tbxAccion = (Textbox)form.getFellow("tbxAccion");
		// groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		// groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");
		listarTipoBusqueda();
	}

	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

//		empresa = ServiceLocator.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
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
		listitem.setValue("fecha");
		listitem.setLabel("Fecha");
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

	private void listarTipoBusqueda() {
		lbxSearh.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("Todos");
		lbxSearh.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("N");
		listitem.setLabel("No leidos");
		lbxSearh.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("S");
		listitem.setLabel("Leidos");
		lbxSearh.appendChild(listitem);

		if (lbxSearh.getItemCount() > 0) {
			lbxSearh.setSelectedIndex(0);
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
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			String filtro = lbxSearh.getSelectedItem().getValue() + "";

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			if (!filtro.isEmpty()) {
				parameters.put("leida", filtro);
			}

			getServiceLocator().getAnexo9EntidadService().setLimit(
					"limit 25 offset 0");

			List<Anexo10_entidad> lista_datos = getServiceLocator()
					.getAnexo10EntidadService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Anexo10_entidad anexo9_entidad : lista_datos) {
				rowsResultado.appendChild(crearFilas(anexo9_entidad, this));
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

		final Anexo10_entidad anexo9_entidad = (Anexo10_entidad) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		/* cargamos paciente */
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(sucursal.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(anexo9_entidad.getCodigo_paciente());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		String namePaciente = paciente != null ? (paciente.getApellido1() + " "
				+ paciente.getApellido2() + " " + paciente.getNombre1() + " " + paciente
				.getNombre2()) : "";

		Usuarios usuariosMedico = new Usuarios();
		usuariosMedico.setCodigo_empresa(sucursal.getCodigo_empresa());
		usuariosMedico.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		usuariosMedico.setCodigo(anexo9_entidad.getCodigo_medico());
		usuariosMedico = getServiceLocator().getUsuariosService().consultar(
				usuariosMedico);
		String nameMedico = usuariosMedico != null ? (usuariosMedico
				.getApellidos() + " " + usuariosMedico.getNombres()) : "";

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(anexo9_entidad.getCodigo() + ""));
		fila.appendChild(new Label(anexo9_entidad.getCodigo_paciente() + ""));
		fila.appendChild(new Label(namePaciente + ""));
		fila.appendChild(new Label(nameMedico + ""));
		fila.appendChild(new Label(anexo9_entidad.getServicio_contrarefiere()
				+ ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setTooltiptext("Imprimir remision");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea imprimir este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									anexo9_entidad.setLeida("S");
									getServiceLocator()
											.getAnexo10EntidadService()
											.actualizar(anexo9_entidad);
									imprimir(anexo9_entidad.getCodigo());
								}
							}
						});
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/uparrow_g.png");
		img.setTooltiptext("Agregar contra remision");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				addContraRemision(anexo9_entidad);
			}
		});
		// hbox.appendChild(space);
		// hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	protected void addContraRemision(Anexo10_entidad anexo9Entidad)
			throws Exception {
		
		/* agregamos contra remision */
		Map params = new HashMap();
		params.put("anexo9", anexo9Entidad);
		Component componente = Executions.createComponents(
				"/pages/contraremision.zul", this, params);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setHeight("90%");
		ventana.setVflex("1");
		ventana.doModal();
	}

	public void imprimir(Long codigo_remision) throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Anexo10Remision");
		paramRequest.put("codigo", codigo_remision);
		paramRequest.put("codigo_empresa", sucursal.getCodigo_empresa());
		paramRequest.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", form, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			// setUpperCase();
			// if(validarForm()){
			// //Cargamos los componentes //

			// Anexo9_entidad anexo9_entidad = new Anexo9_entidad();
			// anexo9_entidad.setCodigo_empresa(empresa.getCodigo_empresa());
			// anexo9_entidad.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			// anexo9_entidad.setCodigo();
			// anexo9_entidad.setFecha();
			// anexo9_entidad.setCodigo_prestador();
			// anexo9_entidad.setCodigo_paciente();
			// anexo9_entidad.setNombre_responsable();
			// anexo9_entidad.setApellido_responsable();
			// anexo9_entidad.setNro_id_responsable();
			// anexo9_entidad.setDir_responsable();
			// anexo9_entidad.setTer_responsable();
			// anexo9_entidad.setDep_responsable();
			// anexo9_entidad.setMun_responsable();
			// anexo9_entidad.setCodigo_medico();
			// anexo9_entidad.setInformacion_clinica();
			// anexo9_entidad.setCreacion_date(new Timestamp(new
			// GregorianCalendar().getTimeInMillis()));
			// anexo9_entidad.setUltimo_update(new Timestamp(new
			// GregorianCalendar().getTimeInMillis()));
			// anexo9_entidad.setDelete_date();
			// anexo9_entidad.setCreacion_user(usuarios.getId().toString());
			// anexo9_entidad.setUltimo_user(usuarios.getId().toString());
			// anexo9_entidad.setDelete_user();
			// anexo9_entidad.setNro_historia();
			// anexo9_entidad.setServicio_solicita();
			// anexo9_entidad.setServico_cual_solicita();
			// anexo9_entidad.setCel_responsable();
			// anexo9_entidad.setTipo_id_responsable();
			// anexo9_entidad.setNombre_solicita();
			// anexo9_entidad.setTelefono_solicita();
			// anexo9_entidad.setCel_solicita();
			//
			// if(tbxAccion.getText().equalsIgnoreCase("registrar")){
			// getServiceLocator().getAnexo10EntidadService().crear(anexo9_entidad);
			// accionForm(true,"registrar");
			// }else{
			// int result =
			// getServiceLocator().getAnexo10EntidadService().actualizar(anexo9_entidad);
			// if(result==0){
			// throw new
			// Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
			// }
			// }
			//
			// Messagebox.show("Los datos se guardaron satisfactoriamente",
			// "Informacion .." ,
			// Messagebox.OK, Messagebox.INFORMATION);
			//
			// }

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
//		Anexo10_entidad anexo9_entidad = (Anexo10_entidad) obj;
		try {

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
		Anexo10_entidad anexo9_entidad = (Anexo10_entidad) obj;
		try {
			int result = getServiceLocator().getAnexo10EntidadService()
					.eliminar(anexo9_entidad);
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
