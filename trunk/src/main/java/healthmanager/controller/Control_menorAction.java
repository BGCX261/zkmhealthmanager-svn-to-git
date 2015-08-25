/*
 * control_menorAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Control_menor;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
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
import com.framework.util.Util;

public class Control_menorAction extends Borderlayout implements AfterCompose {

	private static Logger log = Logger.getLogger(Control_menorAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	private Borderlayout form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;

	private Textbox tbxCodigo_historia;
	private Datebox dtbxFecha_inicial;
	private Textbox tbxCodigo_eps;
	private Textbox tbxNombre_eps;
	private Listbox lbxCodigo_dpto;
	private Listbox lbxCodigo_municipio;
	private Textbox tbxContrato;
	private Textbox tbxCodigo_contrato;

	private Bandbox tbxIdentificacion;
	private Textbox tbxNomPaciente;
	private Textbox tbxTipoIdentificacion;
	private Datebox dbxNacimiento;
	private Textbox tbxEdad;
	private Textbox tbxSexo;
	private Textbox tbxDireccion;

	private Datebox dtbxFecha_inicial1;
	private Bandbox tbxIdentificacion1;
	private Textbox tbxEdad1;
	private Textbox tbxPeso1;
	private Textbox tbxTalla1;
	private Textbox tbxPerimietro1;
	private Datebox dtbxFecha_inicial2;
	private Bandbox tbxIdentificacion2;
	private Textbox tbxEdad2;
	private Textbox tbxPeso2;
	private Textbox tbxTalla2;
	private Textbox tbxPerimietro2;
	private Datebox dtbxFecha_inicial3;
	private Bandbox tbxIdentificacion3;
	private Textbox tbxEdad3;
	private Textbox tbxPeso3;
	private Textbox tbxTalla3;
	private Textbox tbxPerimietro3;
	private Datebox dtbxFecha_inicial4;
	private Bandbox tbxIdentificacion4;
	private Textbox tbxEdad4;
	private Textbox tbxPeso4;
	private Textbox tbxTalla4;
	private Textbox tbxPerimietro4;
	private Datebox dtbxFecha_inicial5;
	private Bandbox tbxIdentificacion5;
	private Textbox tbxEdad5;
	private Textbox tbxPeso5;
	private Textbox tbxTalla5;
	private Textbox tbxPerimietro5;
	private Datebox dtbxFecha_inicial6;
	private Bandbox tbxIdentificacion6;
	private Textbox tbxEdad6;
	private Textbox tbxPeso6;
	private Textbox tbxTalla6;
	private Textbox tbxPerimietro6;
	private Datebox dtbxFecha_inicial7;
	private Bandbox tbxIdentificacion7;
	private Textbox tbxEdad7;
	private Textbox tbxPeso7;
	private Textbox tbxTalla7;
	private Textbox tbxPerimietro7;
	private Datebox dtbxFecha_inicial8;
	private Bandbox tbxIdentificacion8;
	private Textbox tbxEdad8;
	private Textbox tbxPeso8;
	private Textbox tbxTalla8;
	private Textbox tbxPerimietro8;

	private Textbox tbxNomIdentificacion1;
	private Textbox tbxNomIdentificacion2;
	private Textbox tbxNomIdentificacion3;
	private Textbox tbxNomIdentificacion4;
	private Textbox tbxNomIdentificacion5;
	private Textbox tbxNomIdentificacion6;
	private Textbox tbxNomIdentificacion7;
	private Textbox tbxNomIdentificacion8;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();
	}

	public void loadComponents() {
		form = (Borderlayout) this.getFellow("formControl_menor");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

		tbxCodigo_historia = (Textbox) form.getFellow("tbxCodigo_historia");
		dtbxFecha_inicial = (Datebox) form.getFellow("dtbxFecha_inicial");
		tbxCodigo_eps = (Textbox) form.getFellow("tbxCodigo_eps");
		tbxNombre_eps = (Textbox) form.getFellow("tbxNombre_eps");
		lbxCodigo_dpto = (Listbox) form.getFellow("lbxCodigo_dpto");
		lbxCodigo_municipio = (Listbox) form.getFellow("lbxCodigo_municipio");
		tbxContrato = (Textbox) form.getFellow("tbxContrato");
		tbxCodigo_contrato = (Textbox) form.getFellow("tbxCodigo_contrato");

		tbxIdentificacion = (Bandbox) form.getFellow("tbxIdentificacion");
		tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
		tbxTipoIdentificacion = (Textbox) form
				.getFellow("tbxTipoIdentificacion");
		tbxEdad = (Textbox) form.getFellow("tbxEdad");
		tbxSexo = (Textbox) form.getFellow("tbxSexo");
		dbxNacimiento = (Datebox) form.getFellow("dbxNacimiento");
		tbxDireccion = (Textbox) form.getFellow("tbxDireccion");

		dtbxFecha_inicial1 = (Datebox) form.getFellow("dtbxFecha_inicial1");
		tbxIdentificacion1 = (Bandbox) form.getFellow("tbxIdentificacion1");
		tbxEdad1 = (Textbox) form.getFellow("tbxEdad1");
		tbxPeso1 = (Textbox) form.getFellow("tbxPeso1");
		tbxTalla1 = (Textbox) form.getFellow("tbxTalla1");
		tbxPerimietro1 = (Textbox) form.getFellow("tbxPerimietro1");
		dtbxFecha_inicial2 = (Datebox) form.getFellow("dtbxFecha_inicial2");
		tbxIdentificacion2 = (Bandbox) form.getFellow("tbxIdentificacion2");
		tbxEdad2 = (Textbox) form.getFellow("tbxEdad2");
		tbxPeso2 = (Textbox) form.getFellow("tbxPeso2");
		tbxTalla2 = (Textbox) form.getFellow("tbxTalla2");
		tbxPerimietro2 = (Textbox) form.getFellow("tbxPerimietro2");
		dtbxFecha_inicial3 = (Datebox) form.getFellow("dtbxFecha_inicial3");
		tbxIdentificacion3 = (Bandbox) form.getFellow("tbxIdentificacion3");
		tbxEdad3 = (Textbox) form.getFellow("tbxEdad3");
		tbxPeso3 = (Textbox) form.getFellow("tbxPeso3");
		tbxTalla3 = (Textbox) form.getFellow("tbxTalla3");
		tbxPerimietro3 = (Textbox) form.getFellow("tbxPerimietro3");
		dtbxFecha_inicial4 = (Datebox) form.getFellow("dtbxFecha_inicial4");
		tbxIdentificacion4 = (Bandbox) form.getFellow("tbxIdentificacion4");
		tbxEdad4 = (Textbox) form.getFellow("tbxEdad4");
		tbxPeso4 = (Textbox) form.getFellow("tbxPeso4");
		tbxTalla4 = (Textbox) form.getFellow("tbxTalla4");
		tbxPerimietro4 = (Textbox) form.getFellow("tbxPerimietro4");
		dtbxFecha_inicial5 = (Datebox) form.getFellow("dtbxFecha_inicial5");
		tbxIdentificacion5 = (Bandbox) form.getFellow("tbxIdentificacion5");
		tbxEdad5 = (Textbox) form.getFellow("tbxEdad5");
		tbxPeso5 = (Textbox) form.getFellow("tbxPeso5");
		tbxTalla5 = (Textbox) form.getFellow("tbxTalla5");
		tbxPerimietro5 = (Textbox) form.getFellow("tbxPerimietro5");
		dtbxFecha_inicial6 = (Datebox) form.getFellow("dtbxFecha_inicial6");
		tbxIdentificacion6 = (Bandbox) form.getFellow("tbxIdentificacion6");
		tbxEdad6 = (Textbox) form.getFellow("tbxEdad6");
		tbxPeso6 = (Textbox) form.getFellow("tbxPeso6");
		tbxTalla6 = (Textbox) form.getFellow("tbxTalla6");
		tbxPerimietro6 = (Textbox) form.getFellow("tbxPerimietro6");
		dtbxFecha_inicial7 = (Datebox) form.getFellow("dtbxFecha_inicial7");
		tbxIdentificacion7 = (Bandbox) form.getFellow("tbxIdentificacion7");
		tbxEdad7 = (Textbox) form.getFellow("tbxEdad7");
		tbxPeso7 = (Textbox) form.getFellow("tbxPeso7");
		tbxTalla7 = (Textbox) form.getFellow("tbxTalla7");
		tbxPerimietro7 = (Textbox) form.getFellow("tbxPerimietro7");
		dtbxFecha_inicial8 = (Datebox) form.getFellow("dtbxFecha_inicial8");
		tbxIdentificacion8 = (Bandbox) form.getFellow("tbxIdentificacion8");
		tbxEdad8 = (Textbox) form.getFellow("tbxEdad8");
		tbxPeso8 = (Textbox) form.getFellow("tbxPeso8");
		tbxTalla8 = (Textbox) form.getFellow("tbxTalla8");
		tbxPerimietro8 = (Textbox) form.getFellow("tbxPerimietro8");

		tbxNomIdentificacion1 = (Textbox) form
				.getFellow("tbxNomIdentificacion1");
		tbxNomIdentificacion2 = (Textbox) form
				.getFellow("tbxNomIdentificacion2");
		tbxNomIdentificacion3 = (Textbox) form
				.getFellow("tbxNomIdentificacion3");
		tbxNomIdentificacion4 = (Textbox) form
				.getFellow("tbxNomIdentificacion4");
		tbxNomIdentificacion5 = (Textbox) form
				.getFellow("tbxNomIdentificacion5");
		tbxNomIdentificacion6 = (Textbox) form
				.getFellow("tbxNomIdentificacion6");
		tbxNomIdentificacion7 = (Textbox) form
				.getFellow("tbxNomIdentificacion7");
		tbxNomIdentificacion8 = (Textbox) form
				.getFellow("tbxNomIdentificacion8");

	}

	public void initControl_menor() throws Exception {
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

	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}

	public void listarCombos() {
		listarParameter();
		listarDepartamentos(lbxCodigo_dpto, true);
		listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
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
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getControl_menorService().setLimit(
					"limit 25 offset 0");

			List<Control_menor> lista_datos = getServiceLocator()
					.getControl_menorService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Control_menor control_menor : lista_datos) {
				rowsResultado.appendChild(crearFilas(control_menor, this));
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

		final Control_menor control_menor = (Control_menor) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(control_menor.getCodigo_historia() + ""));
		fila.appendChild(new Label(control_menor.getIdentificacion() + ""));
		fila.appendChild(new Label(control_menor.getFecha_inicial() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {

				mostrarDatos(control_menor);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		// Simg.setVisible((permisos_sc.getPermiso_eliminar()?true:false));
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
									eliminarDatos(control_menor);
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

				Control_menor control_menor = new Control_menor();
				control_menor.setCodigo_empresa(empresa.getCodigo_empresa());
				control_menor.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				control_menor.setCodigo_historia(tbxCodigo_historia.getValue());
				control_menor.setIdentificacion(tbxIdentificacion.getValue());
				control_menor.setFecha_inicial(new Timestamp(dtbxFecha_inicial
						.getValue().getTime()));
				control_menor.setFecha_inicial1(new Timestamp(
						dtbxFecha_inicial1.getValue().getTime()));
				control_menor.setIdentificacion1(tbxIdentificacion1.getValue());
				control_menor.setEdad1(tbxEdad1.getValue());
				control_menor.setPeso1(tbxPeso1.getValue());
				control_menor.setTalla1(tbxTalla1.getValue());
				control_menor.setPerimietro1(tbxPerimietro1.getValue());
				control_menor.setFecha_inicial2(new Timestamp(
						dtbxFecha_inicial2.getValue().getTime()));
				control_menor.setIdentificacion2(tbxIdentificacion2.getValue());
				control_menor.setEdad2(tbxEdad2.getValue());
				control_menor.setPeso2(tbxPeso2.getValue());
				control_menor.setTalla2(tbxTalla2.getValue());
				control_menor.setPerimietro2(tbxPerimietro2.getValue());
				control_menor.setFecha_inicial3(new Timestamp(
						dtbxFecha_inicial3.getValue().getTime()));
				control_menor.setIdentificacion3(tbxIdentificacion3.getValue());
				control_menor.setEdad3(tbxEdad3.getValue());
				control_menor.setPeso3(tbxPeso3.getValue());
				control_menor.setTalla3(tbxTalla3.getValue());
				control_menor.setPerimietro3(tbxPerimietro3.getValue());
				control_menor.setFecha_inicial4(new Timestamp(
						dtbxFecha_inicial4.getValue().getTime()));
				control_menor.setIdentificacion4(tbxIdentificacion4.getValue());
				control_menor.setEdad4(tbxEdad4.getValue());
				control_menor.setPeso4(tbxPeso4.getValue());
				control_menor.setTalla4(tbxTalla4.getValue());
				control_menor.setPerimietro4(tbxPerimietro4.getValue());
				control_menor.setFecha_inicial5(new Timestamp(
						dtbxFecha_inicial5.getValue().getTime()));
				control_menor.setIdentificacion5(tbxIdentificacion5.getValue());
				control_menor.setEdad5(tbxEdad5.getValue());
				control_menor.setPeso5(tbxPeso5.getValue());
				control_menor.setTalla5(tbxTalla5.getValue());
				control_menor.setPerimietro5(tbxPerimietro5.getValue());
				control_menor.setFecha_inicial6(new Timestamp(
						dtbxFecha_inicial6.getValue().getTime()));
				control_menor.setIdentificacion6(tbxIdentificacion6.getValue());
				control_menor.setEdad6(tbxEdad6.getValue());
				control_menor.setPeso6(tbxPeso6.getValue());
				control_menor.setTalla6(tbxTalla6.getValue());
				control_menor.setPerimietro6(tbxPerimietro6.getValue());
				control_menor.setFecha_inicial7(new Timestamp(
						dtbxFecha_inicial7.getValue().getTime()));
				control_menor.setIdentificacion7(tbxIdentificacion7.getValue());
				control_menor.setEdad7(tbxEdad7.getValue());
				control_menor.setPeso7(tbxPeso7.getValue());
				control_menor.setTalla7(tbxTalla7.getValue());
				control_menor.setPerimietro7(tbxPerimietro7.getValue());
				control_menor.setFecha_inicial8(new Timestamp(
						dtbxFecha_inicial8.getValue().getTime()));
				control_menor.setIdentificacion8(tbxIdentificacion8.getValue());
				control_menor.setEdad8(tbxEdad8.getValue());
				control_menor.setPeso8(tbxPeso8.getValue());
				control_menor.setTalla8(tbxTalla8.getValue());
				control_menor.setPerimietro8(tbxPerimietro8.getValue());

				datos.put("codigo_historia", control_menor);
				datos.put("accion", tbxAccion.getText());

				control_menor = getServiceLocator().getControl_menorService()
						.guardar(datos);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					accionForm(true, "modificar");
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
		Control_menor control_menor = (Control_menor) obj;
		try {
			tbxCodigo_historia.setValue(control_menor.getCodigo_historia());
			dtbxFecha_inicial.setValue(control_menor.getFecha_inicial());

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(control_menor.getCodigo_empresa());
			paciente.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			paciente.setNro_identificacion(control_menor.getIdentificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			Elemento elemento = new Elemento();
			elemento.setCodigo(paciente.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxIdentificacion.setValue(control_menor.getIdentificacion());
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

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(control_menor.getCodigo_empresa());
			contratos.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			contratos.setCodigo_administradora(paciente.getCodigo_administradora());
//			contratos.setId_plan(paciente.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(contratos);

			tbxCodigo_contrato.setValue(contratos != null ? contratos.getId_plan()
					: "");
			tbxContrato.setValue(contratos != null ? contratos.getNombre() : "");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(paciente.getCodigo_dpto())) {
					listitem.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

			dtbxFecha_inicial1.setValue(control_menor.getFecha_inicial1());

			Prestadores prestador = new Prestadores();
			prestador.setCodigo_empresa(control_menor.getCodigo_empresa());
			prestador.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			prestador.setNro_identificacion(control_menor.getIdentificacion1());
			//log.info("antes: " + prestador);
			prestador = getServiceLocator().getPrestadoresService().consultar(
					prestador);
			//log.info("despues: " + prestador);

			tbxIdentificacion1.setValue(control_menor.getIdentificacion1());
			tbxNomIdentificacion1.setValue((prestador != null ? prestador
					.getNombres() : ""));

			tbxEdad1.setValue(control_menor.getEdad1());
			tbxPeso1.setValue(control_menor.getPeso1());
			tbxTalla1.setValue(control_menor.getTalla1());
			tbxPerimietro1.setValue(control_menor.getPerimietro1());
			dtbxFecha_inicial2.setValue(control_menor.getFecha_inicial2());

			prestador = new Prestadores();
			prestador.setCodigo_empresa(control_menor.getCodigo_empresa());
			prestador.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			prestador.setNro_identificacion(control_menor.getIdentificacion2());
			//log.info("antes: " + prestador);
			prestador = getServiceLocator().getPrestadoresService().consultar(
					prestador);
			//log.info("despues: " + prestador);

			tbxIdentificacion2.setValue(control_menor.getIdentificacion2());
			tbxNomIdentificacion2.setValue((prestador != null ? prestador
					.getNombres() : ""));

			tbxEdad2.setValue(control_menor.getEdad2());
			tbxPeso2.setValue(control_menor.getPeso2());
			tbxTalla2.setValue(control_menor.getTalla2());
			tbxPerimietro2.setValue(control_menor.getPerimietro2());
			dtbxFecha_inicial3.setValue(control_menor.getFecha_inicial3());

			prestador = new Prestadores();
			prestador.setCodigo_empresa(control_menor.getCodigo_empresa());
			prestador.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			prestador.setNro_identificacion(control_menor.getIdentificacion3());
			//log.info("antes: " + prestador);
			prestador = getServiceLocator().getPrestadoresService().consultar(
					prestador);
			//log.info("despues: " + prestador);

			tbxIdentificacion3.setValue(control_menor.getIdentificacion3());
			tbxNomIdentificacion3.setValue((prestador != null ? prestador
					.getNombres() : ""));

			tbxEdad3.setValue(control_menor.getEdad3());
			tbxPeso3.setValue(control_menor.getPeso3());
			tbxTalla3.setValue(control_menor.getTalla3());
			tbxPerimietro3.setValue(control_menor.getPerimietro3());
			dtbxFecha_inicial4.setValue(control_menor.getFecha_inicial4());

			prestador = new Prestadores();
			prestador.setCodigo_empresa(control_menor.getCodigo_empresa());
			prestador.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			prestador.setNro_identificacion(control_menor.getIdentificacion4());
			//log.info("antes: " + prestador);
			prestador = getServiceLocator().getPrestadoresService().consultar(
					prestador);
			//log.info("despues: " + prestador);

			tbxIdentificacion4.setValue(control_menor.getIdentificacion4());
			tbxNomIdentificacion4.setValue((prestador != null ? prestador
					.getNombres() : ""));

			tbxEdad4.setValue(control_menor.getEdad4());
			tbxPeso4.setValue(control_menor.getPeso4());
			tbxTalla4.setValue(control_menor.getTalla4());
			tbxPerimietro4.setValue(control_menor.getPerimietro4());
			dtbxFecha_inicial5.setValue(control_menor.getFecha_inicial5());

			prestador = new Prestadores();
			prestador.setCodigo_empresa(control_menor.getCodigo_empresa());
			prestador.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			prestador.setNro_identificacion(control_menor.getIdentificacion5());
			//log.info("antes: " + prestador);
			prestador = getServiceLocator().getPrestadoresService().consultar(
					prestador);
			//log.info("despues: " + prestador);

			tbxIdentificacion5.setValue(control_menor.getIdentificacion5());
			tbxNomIdentificacion5.setValue((prestador != null ? prestador
					.getNombres() : ""));

			tbxEdad5.setValue(control_menor.getEdad5());
			tbxPeso5.setValue(control_menor.getPeso5());
			tbxTalla5.setValue(control_menor.getTalla5());
			tbxPerimietro5.setValue(control_menor.getPerimietro5());
			dtbxFecha_inicial6.setValue(control_menor.getFecha_inicial6());

			prestador = new Prestadores();
			prestador.setCodigo_empresa(control_menor.getCodigo_empresa());
			prestador.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			prestador.setNro_identificacion(control_menor.getIdentificacion6());
			//log.info("antes: " + prestador);
			prestador = getServiceLocator().getPrestadoresService().consultar(
					prestador);
			//log.info("despues: " + prestador);

			tbxIdentificacion6.setValue(control_menor.getIdentificacion6());
			tbxNomIdentificacion6.setValue((prestador != null ? prestador
					.getNombres() : ""));

			tbxEdad6.setValue(control_menor.getEdad6());
			tbxPeso6.setValue(control_menor.getPeso6());
			tbxTalla6.setValue(control_menor.getTalla6());
			tbxPerimietro6.setValue(control_menor.getPerimietro6());
			dtbxFecha_inicial7.setValue(control_menor.getFecha_inicial7());

			prestador = new Prestadores();
			prestador.setCodigo_empresa(control_menor.getCodigo_empresa());
			prestador.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			prestador.setNro_identificacion(control_menor.getIdentificacion7());
			//log.info("antes: " + prestador);
			prestador = getServiceLocator().getPrestadoresService().consultar(
					prestador);
			//log.info("despues: " + prestador);

			tbxIdentificacion7.setValue(control_menor.getIdentificacion7());
			tbxNomIdentificacion7.setValue((prestador != null ? prestador
					.getNombres() : ""));

			tbxEdad7.setValue(control_menor.getEdad7());
			tbxPeso7.setValue(control_menor.getPeso7());
			tbxTalla7.setValue(control_menor.getTalla7());
			tbxPerimietro7.setValue(control_menor.getPerimietro7());
			dtbxFecha_inicial8.setValue(control_menor.getFecha_inicial8());

			prestador = new Prestadores();
			prestador.setCodigo_empresa(control_menor.getCodigo_empresa());
			prestador.setCodigo_sucursal(control_menor.getCodigo_sucursal());
			prestador.setNro_identificacion(control_menor.getIdentificacion8());
			//log.info("antes: " + prestador);
			prestador = getServiceLocator().getPrestadoresService().consultar(
					prestador);
			//log.info("despues: " + prestador);

			tbxIdentificacion8.setValue(control_menor.getIdentificacion8());
			tbxNomIdentificacion8.setValue((prestador != null ? prestador
					.getNombres() : ""));

			tbxEdad8.setValue(control_menor.getEdad8());
			tbxPeso8.setValue(control_menor.getPeso8());
			tbxTalla8.setValue(control_menor.getTalla8());
			tbxPerimietro8.setValue(control_menor.getPerimietro8());

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
		Control_menor control_menor = (Control_menor) obj;
		try {
			int result = getServiceLocator().getControl_menorService()
					.eliminar(control_menor);
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
				.getDepartamentosService().listar(new HashMap());

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
		Map<String, Object> parameters = new HashMap();
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
			tbxIdentificacion.setValue("");
			tbxNomPaciente.setValue("");
			tbxTipoIdentificacion.setValue("");
			tbxEdad.setValue("");
			tbxSexo.setValue("");
			dbxNacimiento.setValue(null);
			tbxCodigo_eps.setValue("");
			tbxNombre_eps.setValue("");
			tbxDireccion.setValue("");
			tbxCodigo_contrato.setValue("");
			tbxContrato.setValue("");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem1.getValue().toString().equals("")) {
					listitem1.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
				if (listitem1.getValue().toString().equals("")) {
					listitem1.setSelected(true);
					i = lbxCodigo_municipio.getItemCount();
				}
			}

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

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(dato.getCodigo_empresa());
			contratos.setCodigo_sucursal(dato.getCodigo_sucursal());
			contratos.setCodigo_administradora(dato.getCodigo_administradora());
//			contratos.setId_plan(dato.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(contratos);

			tbxCodigo_contrato.setValue(contratos != null ? contratos.getId_plan()
					: "");
			tbxContrato.setValue(contratos != null ? contratos.getNombre() : "");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem1.getValue().toString()
						.equals(dato.getCodigo_dpto())) {
					listitem1.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
				if (listitem1.getValue().toString()
						.equals(dato.getCodigo_municipio())) {
					listitem1.setSelected(true);
					i = lbxCodigo_municipio.getItemCount();
				}
			}
		}

		tbxIdentificacion.close();
	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Prestadores> list = getServiceLocator()
					.getPrestadoresService().listar(parameters);

			lbx.getItems().clear();

			for (Prestadores dato : list) {

				Especialidad especialidad = new Especialidad();
				especialidad.setCodigo(dato.getCodigo_especialidad());
				especialidad = getServiceLocator().getEspecialidadService()
						.consultar(especialidad);

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
				listcell.appendChild(new Label(dato.getNombres()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellidos()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(
						(especialidad != null ? especialidad.getNombre() : "")));
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

	/*
	 * public void selectedPrestador(Listitem listitem){
	 * if(listitem.getValue()==null){ tbxCodigo_prestador.setValue("");
	 * tbxNomPrestador.setValue(""); }else{ Prestadores dato = (Prestadores)
	 * listitem.getValue();
	 * tbxCodigo_prestador.setValue(dato.getNro_identificacion());
	 * tbxNomPrestador.setValue(dato.getNombres()+" "+dato.getApellidos()); }
	 * tbxCodigo_prestador.close(); }
	 */

	public void selectedPrestador(Listitem listitem, Bandbox bandbox,
			Textbox textbox) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
		} else {
			Prestadores dato = (Prestadores) listitem.getValue();
			bandbox.setValue(dato.getNro_identificacion());
			textbox.setValue(dato.getNombres());
		}
		bandbox.close();
	}
}
