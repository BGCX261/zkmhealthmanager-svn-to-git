/*
 * agudeza_visualAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Sucursal;

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
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.InformacionPacientesMacro;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.Util;

public class Agudeza_visualAction extends Window implements AfterCompose {

	private static Logger log = Logger.getLogger(Agudeza_visualAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

	private Empresa empresa;
	private Sucursal sucursal;
//	private Usuarios usuarios;

	private Window form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;

	private Longbox tbxCodigo_historia;
	private Datebox dtbxFecha_inicial;
	// private Textbox tbxCodigo_eps;
	// private Textbox tbxNombre_eps;
	// private Listbox lbxCodigo_dpto;
	// private Listbox lbxCodigo_municipio;
	// private Textbox tbxContrato;
	// private Textbox tbxCodigo_contrato;

	private Bandbox tbxIdentificacion;
	private Textbox tbxNomPaciente;
	private Textbox tbxTipoIdentificacion;
	private Datebox dbxNacimiento;
	private Textbox tbxEdad;
	private Textbox tbxSexo;
	private Textbox tbxDireccion;

	@View
	private Toolbarbutton btGuardar;
	@View
	private Doublebox dbxCon_lentes_od;
	@View
	private Doublebox dbxCon_lentes_od2;
	@View
	private Doublebox dbxCon_lentes_oi;
	@View
	private Doublebox dbxCon_lentes_oi2;
	@View
	private Doublebox dbxCon_lentes_ao;
	@View
	private Doublebox dbxCon_lentes_ao2;
	@View
	private Doublebox dbxSin_lentes_od;
	@View
	private Doublebox dbxSin_lentes_od2;
	@View
	private Doublebox dbxSin_lentes_oi;
	@View
	private Doublebox dbxSin_lentes_oi2;
	@View
	private Doublebox dbxSin_lentes_ao;
	@View
	private Doublebox dbxSin_lentes_ao2;
	@View
	private Doublebox dbxVision_cercana_od;
	@View
	private Doublebox dbxVision_cercana_od2;
	@View
	private Doublebox dbxVision_cercana_oi;
	@View
	private Doublebox dbxVision_cercana_oi2;
	@View
	private Doublebox dbxVision_cercana_ao;
	@View
	private Doublebox dbxVision_cercana_ao2;
	@View
	private Radiogroup rdbConducta;
	@View
	private Radio rdRadio1;
	@View
	private Radio rdRadio2;
	@View
	private Textbox tbxObservacion;

	private Agudeza_visual agudeza_visual = new Agudeza_visual();
	private ZKWindow zkWindow;
	private Admision admision;

	public void inicializAgudeza(ZKWindow zkWindow, Admision admision) {
		setZkWindow(zkWindow);
		setAdmision(admision);
	}

	public void deshabilitarCampos() {
		dbxCon_lentes_od.setDisabled(true);
		dbxCon_lentes_od2.setDisabled(true);
		dbxCon_lentes_oi.setDisabled(true);
		dbxCon_lentes_oi2.setDisabled(true);
		dbxCon_lentes_ao.setDisabled(true);
		dbxCon_lentes_ao2.setDisabled(true);
		dbxSin_lentes_od.setDisabled(true);
		dbxSin_lentes_od2.setDisabled(true);
		dbxSin_lentes_oi.setDisabled(true);
		dbxSin_lentes_oi2.setDisabled(true);
		dbxSin_lentes_ao.setDisabled(true);
		dbxSin_lentes_ao2.setDisabled(true);
		dbxVision_cercana_od.setDisabled(true);
		dbxVision_cercana_od2.setDisabled(true);
		dbxVision_cercana_oi.setDisabled(true);
		dbxVision_cercana_oi2.setDisabled(true);
		dbxVision_cercana_ao.setDisabled(true);
		dbxVision_cercana_ao2.setDisabled(true);
		rdRadio1.setDisabled(true);
		rdRadio2.setDisabled(true);
		tbxObservacion.setReadonly(true);
	}

	private Listbox lbxFormato;

	@View(isMacro = true)
	private InformacionPacientesMacro infoPacientes;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		// listarCombos();
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formAgudeza_visual");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

		tbxCodigo_historia = (Longbox) form.getFellow("tbxCodigo_historia");
		dtbxFecha_inicial = (Datebox) form.getFellow("dtbxFecha_inicial");
		// tbxCodigo_eps = (Textbox) form.getFellow("tbxCodigo_eps");
		// tbxNombre_eps = (Textbox) form.getFellow("tbxNombre_eps");
		// lbxCodigo_dpto = (Listbox) form.getFellow("lbxCodigo_dpto");
		// lbxCodigo_municipio = (Listbox)
		// form.getFellow("lbxCodigo_municipio");
		// tbxContrato = (Textbox) form.getFellow("tbxContrato");
		// tbxCodigo_contrato = (Textbox) form.getFellow("tbxCodigo_contrato");

		tbxIdentificacion = (Bandbox) form.getFellow("tbxIdentificacion");
		tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
		tbxTipoIdentificacion = (Textbox) form
				.getFellow("tbxTipoIdentificacion");
		tbxEdad = (Textbox) form.getFellow("tbxEdad");
		tbxSexo = (Textbox) form.getFellow("tbxSexo");
		dbxNacimiento = (Datebox) form.getFellow("dbxNacimiento");
		tbxDireccion = (Textbox) form.getFellow("tbxDireccion");

		dbxCon_lentes_od = (Doublebox) form.getFellow("dbxCon_lentes_od");
		dbxCon_lentes_od2 = (Doublebox) form.getFellow("dbxCon_lentes_od2");
		dbxCon_lentes_oi = (Doublebox) form.getFellow("dbxCon_lentes_oi");
		dbxCon_lentes_oi2 = (Doublebox) form.getFellow("dbxCon_lentes_oi2");
		dbxCon_lentes_ao = (Doublebox) form.getFellow("dbxCon_lentes_ao");
		dbxCon_lentes_ao2 = (Doublebox) form.getFellow("dbxCon_lentes_ao2");
		dbxSin_lentes_od = (Doublebox) form.getFellow("dbxSin_lentes_od");
		dbxSin_lentes_od2 = (Doublebox) form.getFellow("dbxSin_lentes_od2");
		dbxSin_lentes_oi = (Doublebox) form.getFellow("dbxSin_lentes_oi");
		dbxSin_lentes_oi2 = (Doublebox) form.getFellow("dbxSin_lentes_oi2");
		dbxSin_lentes_ao = (Doublebox) form.getFellow("dbxSin_lentes_ao");
		dbxSin_lentes_ao2 = (Doublebox) form.getFellow("dbxSin_lentes_ao2");
		dbxVision_cercana_od = (Doublebox) form
				.getFellow("dbxVision_cercana_od");
		dbxVision_cercana_od2 = (Doublebox) form
				.getFellow("dbxVision_cercana_od2");
		dbxVision_cercana_oi = (Doublebox) form
				.getFellow("dbxVision_cercana_oi");
		dbxVision_cercana_oi2 = (Doublebox) form
				.getFellow("dbxVision_cercana_oi2");
		dbxVision_cercana_ao = (Doublebox) form
				.getFellow("dbxVision_cercana_ao");
		dbxVision_cercana_ao2 = (Doublebox) form
				.getFellow("dbxVision_cercana_ao2");

		rdbConducta = (Radiogroup) form.getFellow("rdbConducta");
		tbxObservacion = (Textbox) form.getFellow("tbxObservacion");

		lbxFormato = (Listbox) form.getFellow("lbxFormato");

	}

	public void initAgudeza_visual() throws Exception {

		try {
			accionForm(true, "registrar");

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
//		usuarios = ServiceLocator.getUsuarios(request);
	}

	// public void listarCombos() {
	// listarParameter();
	// listarDepartamentos(lbxCodigo_dpto, true);
	// listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
	// }

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		// listitem.setValue("codigo_historia");
		// listitem.setLabel("Codigo_historia");
		// lbxParameter.appendChild(listitem);
		//
		// listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
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
			/*
			 * if (abstractComponent instanceof Doublebox) { ((Doublebox)
			 * abstractComponent).setText("0.00"); }
			 */
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
			// Messagebox.show("Los campos marcados con (*) son obligatorios",
			// usuarios.getNombres() + " recuerde que...", Messagebox.OK,
			// Messagebox.EXCLAMATION);
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

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Agudeza_visual> lista_datos = getServiceLocator()
					.getAgudeza_visualService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Agudeza_visual agudeza_visual : lista_datos) {
				rowsResultado.appendChild(crearFilas(agudeza_visual, this));
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

		final Agudeza_visual agudeza_visual = (Agudeza_visual) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(agudeza_visual.getCodigo_historia() + ""));
		fila.appendChild(new Label(agudeza_visual.getIdentificacion() + ""));
		fila.appendChild(new Label(agudeza_visual.getFecha_inicial() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {

				mostrarDatosAgudeza(agudeza_visual, true);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		// img.setVisible((permisos_sc.getPermiso_eliminar()?true:false));
		img.setSrc("/images/eliminar.gif");
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
									eliminarDatos(agudeza_visual);
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

	public Agudeza_visual obtenerAgudeza() {
		if (agudeza_visual == null) {
			agudeza_visual = new Agudeza_visual();
		} else {
			agudeza_visual.setCodigo_empresa("");
			agudeza_visual.setCodigo_sucursal("");
			agudeza_visual.setCodigo_historia(null);
			agudeza_visual.setIdentificacion("");
			agudeza_visual.setFecha_inicial(null);
			agudeza_visual.setCon_lentes_od(ConvertidorDatosUtil
					.convertirDato((dbxCon_lentes_od.getValue())));
			agudeza_visual.setCon_lentes_od2(ConvertidorDatosUtil
					.convertirDato(dbxCon_lentes_od2.getValue()));
			agudeza_visual.setCon_lentes_oi(ConvertidorDatosUtil
					.convertirDato(dbxCon_lentes_oi.getValue()));
			agudeza_visual.setCon_lentes_oi2(ConvertidorDatosUtil
					.convertirDato(dbxCon_lentes_oi2.getValue()));
			agudeza_visual.setCon_lentes_ao(ConvertidorDatosUtil
					.convertirDato(dbxCon_lentes_ao.getValue()));
			agudeza_visual.setCon_lentes_ao2(ConvertidorDatosUtil
					.convertirDato(dbxCon_lentes_ao2.getValue()));
			agudeza_visual.setConducta(rdbConducta.getSelectedItem().getValue()
					.toString());
			agudeza_visual.setObservacion(tbxObservacion.getValue());

		}

		return agudeza_visual;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatosAgudeza(Agudeza_visual agudeza_visual,
			boolean readonly) throws Exception {
		this.agudeza_visual = agudeza_visual;
		if (this.agudeza_visual != null) {

			tbxCodigo_historia.setValue(agudeza_visual.getCodigo_historia());
			dtbxFecha_inicial.setValue(agudeza_visual.getFecha_inicial());

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(agudeza_visual.getCodigo_empresa());
			paciente.setCodigo_sucursal(agudeza_visual.getCodigo_sucursal());
			paciente.setNro_identificacion(agudeza_visual.getIdentificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			Elemento elemento = new Elemento();
			elemento.setCodigo(paciente.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxIdentificacion.setValue(agudeza_visual.getIdentificacion());
			tbxNomPaciente.setValue((paciente != null ? paciente.getNombre1()
					+ " " + paciente.getApellido1() : ""));
			tbxTipoIdentificacion.setValue((paciente != null ? paciente
					.getTipo_identificacion() : ""));
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
					paciente.getUnidad_medidad(), false));
			tbxSexo.setValue((elemento != null ? elemento.getDescripcion() : ""));
			dbxNacimiento.setValue(paciente.getFecha_nacimiento());
			tbxDireccion.setValue(paciente.getDireccion());

			Administradora administradora = new Administradora();
			administradora.setCodigo(paciente.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			// tbxCodigo_eps.setValue(administradora != null ? administradora
			// .getCodigo() : "");
			// tbxNombre_eps.setValue(administradora != null ? administradora
			// .getNombre() : "");

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(agudeza_visual.getCodigo_empresa());
			contratos.setCodigo_sucursal(agudeza_visual.getCodigo_sucursal());
			contratos.setCodigo_administradora(paciente
					.getCodigo_administradora());
			// contratos.setId_plan(paciente.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(
					contratos);

			// tbxcodigo_contrato.setvalue(contratos != null ? contratos
			// .getid_plan() : "");
			// tbxcontrato
			// .setvalue(contratos != null ? contratos.getnombre() : "");

			// for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
			// Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
			// if (listitem.getValue().toString()
			// .equals(paciente.getCodigo_dpto())) {
			// listitem.setSelected(true);
			// i = lbxCodigo_dpto.getItemCount();
			// }
			// }

			// listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

			dbxCon_lentes_od.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_od()));
			dbxCon_lentes_od2.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_od2()));
			dbxCon_lentes_oi.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_oi()));
			dbxCon_lentes_oi2.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_oi2()));
			dbxCon_lentes_ao.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_ao()));
			dbxCon_lentes_ao2.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_ao2()));
			Radio radio = (Radio) rdbConducta.getFellow("radio"
					+ agudeza_visual.getConducta());
			radio.setChecked(true);
			tbxObservacion.setValue(agudeza_visual.getObservacion());
		} else {
			// log.info("ya se encuaentra en la base de dato!!!");
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Agudeza_visual agudeza_visual = (Agudeza_visual) obj;
		try {
			int result = getServiceLocator().getAgudeza_visualService()
					.eliminar(agudeza_visual);
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

	public void buscarPaciente(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

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
			tbxIdentificacion.setValue("");
			tbxNomPaciente.setValue("");
			tbxTipoIdentificacion.setValue("");
			tbxEdad.setValue("");
			tbxSexo.setValue("");
			// dbxNacimiento.setValue(null);
			// tbxCodigo_eps.setValue("");
			// tbxNombre_eps.setValue("");
			// tbxDireccion.setValue("");
			// tbxCodigo_contrato.setValue("");
			// tbxContrato.setValue("");

			// for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
			// Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
			// if (listitem1.getValue().toString().equals("")) {
			// listitem1.setSelected(true);
			// i = lbxCodigo_dpto.getItemCount();
			// }
			// }

			// listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			// for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
			// Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
			// if (listitem1.getValue().toString().equals("")) {
			// listitem1.setSelected(true);
			// i = lbxCodigo_municipio.getItemCount();
			// }
			// }

		} else {
			Paciente dato = (Paciente) listitem.getValue();

			Elemento elemento = new Elemento();
			elemento.setCodigo(dato.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxIdentificacion.setValue(dato.getNro_identificacion());
			tbxNomPaciente.setValue(dato.getNombre1() + " " + dato.getNombre2()
					+ " " + dato.getApellido1() + " " + dato.getApellido2());
			tbxTipoIdentificacion.setValue(dato.getTipo_identificacion());
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(dato.getFecha_nacimiento()), dato
					.getUnidad_medidad(), false));
			tbxSexo.setValue(elemento != null ? elemento.getDescripcion() : "");
			dbxNacimiento.setValue(dato.getFecha_nacimiento());
			tbxDireccion.setValue(dato.getDireccion());

			Administradora administradora = new Administradora();
			administradora.setCodigo(dato.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			// tbxCodigo_eps.setValue(administradora != null ? administradora
			// .getCodigo() : "");
			// tbxNombre_eps.setValue(administradora != null ? administradora
			// .getNombre() : "");

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(dato.getCodigo_empresa());
			contratos.setCodigo_sucursal(dato.getCodigo_sucursal());
			contratos.setCodigo_administradora(dato.getCodigo_administradora());
			// contratos.setId_plan(dato.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(
					contratos);

			// tbxCodigo_contrato.setValue(contratos != null ? contratos
			// .getId_plan() : "");
			// tbxContrato
			// .setValue(contratos != null ? contratos.getNombre() : "");

			// for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
			// Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
			// if (listitem1.getValue().toString()
			// .equals(dato.getCodigo_dpto())) {
			// listitem1.setSelected(true);
			// i = lbxCodigo_dpto.getItemCount();
			// }
			// }

			// listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			// for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
			// Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
			// if (listitem1.getValue().toString()
			// .equals(dato.getCodigo_municipio())) {
			// listitem1.setSelected(true);
			// i = lbxCodigo_municipio.getItemCount();
			// }
			// }
		}

		tbxIdentificacion.close();
	}

	public void imprimir(Long codigo_historia) throws Exception {
		if (codigo_historia == null) {
			Messagebox.show("La evolucion no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Evolucion_medica");
		paramRequest.put("codigo_evolucion", codigo_historia);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", form, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

	public ZKWindow getZkWindow() {
		return zkWindow;
	}

	public void setZkWindow(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
	}

	public Admision getAdmision() {
		return admision;
	}

	public void setAdmision(Admision admision) {
		this.admision = admision;
	}
}
