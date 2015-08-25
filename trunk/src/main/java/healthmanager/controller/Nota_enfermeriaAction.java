/**
 * 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_nota_enf;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Nota_enfermeria;
import healthmanager.modelo.bean.Prestadores;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
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
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.framework.constantes.INotas;

/**
 * @author ferney
 * 
 */
public class Nota_enfermeriaAction extends ZKWindow {
	private static Logger log = Logger.getLogger(Nota_enfermeriaAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// Componentes //
	@View private Listbox lbxFormato;

	@View private Textbox tbxAccion;
	@View private Textbox tbxCodigo_nota;
	@View private Textbox tbxNro_identificacion;
	@View private Textbox tbxCodigo_administradora;
	@View private Textbox tbxId_plan;
	@View private Textbox tbxNro_ingreso;
	@View private Textbox tbxTipo_hc;
	@View private Textbox tbxTipo;
	
	@View private Bandbox tbxCodigo_prestador;
	@View private Textbox tbxNomPrestador;

	@View private Grid gridNotas;
	@View private Rows rowsNotas;
	
	@View Button btEliminar;

	private List<Map> lista_datos;

	@View Button btAdicionar;
	private String tipo;


	@Override
	public void params(Map<String, Object> map) {
		tipo = (String) map.get("tipo"); 
		if(tipo.equals(INotas.NOTAS_ODONTOLOGIA)){
			btAdicionar.setLabel("Agregar nueva nota odontologica");
		}
	}

	public void initNota_enfermeria() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		try {
			String id_m = request.getParameter("id_menu");
			if (id_m != null) {
				// crearPermisos(new Integer(id_m));
			}
			Map parametros = Executions.getCurrent().getArg();
			String nro_identificacion = (String) parametros
					.get("nro_identificacion");
			String nro_ingreso = (String) parametros.get("nro_ingreso");
			String estado = (String) parametros.get("estado");
			String codigo_administradora = (String) parametros
					.get("codigo_administradora");
			String id_plan = (String) parametros.get("id_plan");
			String tipo_hc = (String) parametros.get("tipo_hc");
			String tipo = (String) parametros.get("tipo");

			tbxNro_identificacion.setValue(nro_identificacion);
			tbxNro_ingreso.setValue(nro_ingreso);
			tbxCodigo_administradora.setValue(codigo_administradora);
			tbxId_plan.setValue(id_plan);
			tbxTipo_hc.setValue(tipo_hc);
			tbxTipo.setValue(tipo);

			if (nro_identificacion.equals("") && nro_ingreso.equals("")) {
				accionForm(true, "registrar");
				deshabilitarCampos(true);
			} else {
				Nota_enfermeria nota_enfermeria = new Nota_enfermeria();
				nota_enfermeria.setCodigo_empresa(empresa.getCodigo_empresa());
				nota_enfermeria.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				nota_enfermeria.setNro_identificacion(nro_identificacion);
				nota_enfermeria.setNro_ingreso(nro_ingreso);
				nota_enfermeria.setTipo_hc(tipo_hc);
				nota_enfermeria.setTipo(tipo);
				nota_enfermeria = getServiceLocator()
						.getNota_enfermeriaService().consultar(nota_enfermeria);
				// //log.info("historia: "+historia);
				if (nota_enfermeria != null) {
					mostrarDatos(nota_enfermeria);

				} else {
					accionForm(true, "registrar");
				}

				if (estado.equals("2")) {
					deshabilitarCampos(true);
				} else {
					deshabilitarCampos(false);
				}
				
				if(tipo.equals(INotas.NOTAS_ODONTOLOGIA)){
					btEliminar.setVisible(false);
					deshabilitarDetalles(true);
				}
			}
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

	

	public void cargarPrestador() throws Exception {
		if (rol_usuario.equals("05")) {
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(empresa.getCodigo_empresa());
			prestadores.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			prestadores.setNro_identificacion(usuarios.getCodigo());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			if (prestadores == null) {
				throw new Exception(
						"Usuario no esta creado en el modulo de prestadore, actualize informacion de usuario");
			}
			tbxCodigo_prestador.setValue(prestadores.getNro_identificacion());
			tbxNomPrestador.setValue(prestadores.getNombres() + " "
					+ prestadores.getApellidos());

		} else {
			Admision admision = new Admision();
			admision.setCodigo_empresa(empresa.getCodigo_empresa());
			admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			admision.setNro_identificacion(tbxNro_identificacion.getValue());
			admision.setNro_ingreso(tbxNro_ingreso.getValue());
			admision = getServiceLocator().getAdmisionService().consultar(
					admision);

			if (admision != null) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(admision.getCodigo_empresa());
				prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
				prestadores.setNro_identificacion(admision.getCodigo_medico());
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);

				tbxCodigo_prestador.setValue((prestadores != null ? prestadores
						.getNro_identificacion() : "000000000"));
				tbxNomPrestador.setValue((prestadores != null ? prestadores
						.getNombres() + " " + prestadores.getApellidos()
						: "MEDICO POR DEFECTO"));

			} else {
				tbxCodigo_prestador.setValue("000000000");
				tbxNomPrestador.setValue("MEDICO POR DEFECTO");
			}
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		if (accion.equalsIgnoreCase("registrar")) {
			limpiarDatos();
		}

		tbxAccion.setValue(accion);
	}

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = this.getFellows();
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
		Collection<Component> collection = this.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxNro_identificacion")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxNro_ingreso")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxCodigo_administradora")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxId_plan")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxTipo_hc")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxTipo")) {
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
		 * tbxCodigo_prestador.setValue("000000000");
		 * tbxNomPrestador.setValue("MEDICO POR DEFECTO");
		 */
		cargarPrestador();
		lista_datos.clear();
		crearFilas();
		// adicionarBlanco();
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

	// Limpiamos los campos del formulario //
	public void deshabilitarCampos(boolean sw) throws Exception {
		Collection<Component> collection = this.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setDisabled(sw);
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals("lbxFormato")) {
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setDisabled(sw);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Radio) {
				((Radio) abstractComponent).setDisabled(sw);
			}

		}

		deshabilitarDetalles(sw);

		if (!sw) {
			((Button) this.getFellow("btGuardar")).setDisabled(false);
			((Button) this.getFellow("btGuardar"))
					.setImage("/images/Save16.gif");

			((Button) this.getFellow("btEliminar")).setDisabled(false);
			((Button) this.getFellow("btEliminar"))
					.setImage("/images/eliminar.gif");

			((Button) this.getFellow("btAdicionar")).setDisabled(false);
		} else {
			((Button) this.getFellow("btGuardar")).setDisabled(true);
			((Button) this.getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");

			((Button) this.getFellow("btEliminar")).setDisabled(true);
			((Button) this.getFellow("btEliminar"))
					.setImage("/images/eliminar_gris.gif");

			((Button) this.getFellow("btAdicionar")).setDisabled(true);
		}

		if (rol_usuario.equals("05")) {
			tbxCodigo_prestador.setDisabled(true);
		} else {
			tbxCodigo_prestador.setDisabled(sw);
		}
	}
	
	public void deshabilitarDetalles(boolean sw){
		for (int i = 0; i < lista_datos.size(); i++) {
			Row fila = (Row) rowsNotas.getFellow("fila_" + i);

			Datebox dtbxFecha = (Datebox) fila.getFellow("fecha_" + i);
			Textbox tbxNota = (Textbox) fila.getFellow("nota_" + i);
			Image img = (Image) fila.getFellow("img_" + i);
			Textbox tbxAsunto = (Textbox) fila.getFellow("asunto_" + i);
				dtbxFecha.setReadonly(sw);
				tbxNota.setReadonly(sw);
				tbxAsunto.setReadonly(sw);
				img.setVisible(!sw);
		}
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

	public void selectedPrestador(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxCodigo_prestador.setValue("");
			tbxNomPrestador.setValue("");
		} else {
			Prestadores dato = (Prestadores) listitem.getValue();
			tbxCodigo_prestador.setValue(dato.getNro_identificacion());
			tbxNomPrestador.setValue(dato.getNombres() + " "
					+ dato.getApellidos());
		}
		tbxCodigo_prestador.close();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxCodigo_prestador
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo_prestador.getText().trim().equals("")) {
			tbxCodigo_prestador
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida && lista_datos.size() <= 0) {
			mensaje = "Debe crear al menos un registro de evolucion";
			valida = false;
		} else {
			try { 
				actualizarPagina();
			} catch (Exception e) {
				mensaje = "Campos marcados como oblidatorios";
				valida = false;
			}
		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			// setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //
				Map datos = new HashMap();

				Nota_enfermeria nota_enfermeria = new Nota_enfermeria();
				nota_enfermeria.setCodigo_empresa(empresa.getCodigo_empresa());
				nota_enfermeria.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				nota_enfermeria.setCodigo_nota(tbxCodigo_nota.getValue());
				nota_enfermeria.setNro_identificacion(tbxNro_identificacion
						.getValue());
				nota_enfermeria.setNro_ingreso(tbxNro_ingreso.getValue());
				nota_enfermeria
						.setCodigo_administradora(tbxCodigo_administradora
								.getValue());
				nota_enfermeria.setId_plan(tbxId_plan.getValue());
				nota_enfermeria.setCodigo_prestador(tbxCodigo_prestador
						.getValue());
				nota_enfermeria.setTipo_hc(tbxTipo_hc.getValue());
				nota_enfermeria.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				nota_enfermeria.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				nota_enfermeria.setCreacion_user(usuarios.getCodigo());
				nota_enfermeria.setUltimo_user(usuarios.getCodigo());
				nota_enfermeria.setTipo(tbxTipo.getValue());

				datos.put("nota_enfermeria", nota_enfermeria);
				datos.put("lista_detalle", lista_datos);
				datos.put("accion", tbxAccion.getText());

				nota_enfermeria = getServiceLocator()
						.getNota_enfermeriaService().guardar(datos);
				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					tbxAccion.setText("modificar");
				}
				mostrarDatos(nota_enfermeria);

				final String codigo_nota = nota_enfermeria.getCodigo_nota();
				Messagebox
						.show("Los datos se guardaron satisfactoriamente. Desea imprimir el documento? ",
								"impresion", Messagebox.YES + Messagebox.NO,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											// do the thing
											imprimir(codigo_nota);
										}
									}
								});
			}
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Nota_enfermeria nota_enfermeria = (Nota_enfermeria) obj;
		try {
			tbxCodigo_nota.setValue(nota_enfermeria.getCodigo_nota());
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(nota_enfermeria.getCodigo_empresa());
			prestadores
					.setCodigo_sucursal(nota_enfermeria.getCodigo_sucursal());
			prestadores.setNro_identificacion(nota_enfermeria
					.getCodigo_prestador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);

			tbxCodigo_prestador.setValue(nota_enfermeria.getCodigo_prestador());
			tbxNomPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));

			lista_datos.clear();
			rowsNotas.getChildren().clear();
			List<Detalle_nota_enf> lista_detalle = nota_enfermeria
					.getLista_detalle();
			for (Detalle_nota_enf detalle : lista_detalle) {

				Map bean = llenarBeanDetalle(detalle);
				lista_datos.add(bean);
			}
			crearFilas();
			// adicionarBlanco();

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(String codigo_nota) throws Exception {
		final String cod_no = codigo_nota;
		if (codigo_nota.equals("")) {
			Messagebox.show("La nota de "
					+ (tbxTipo.getValue().equals("1") ? "enfermería"
							: "aclaratoria") + " no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		try {
			Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								Nota_enfermeria nota_enfermeria = new Nota_enfermeria();
								nota_enfermeria.setCodigo_empresa(empresa
										.getCodigo_empresa());
								nota_enfermeria.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								nota_enfermeria.setCodigo_nota(cod_no);
								int result = getServiceLocator()
										.getNota_enfermeriaService().eliminar(
												nota_enfermeria);
								if (result == 0) {
									throw new Exception(
											"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
								}

								accionForm(true, "registrar");

								Messagebox
										.show("Informacion se eliminó satisfactoriamente !!",
												"Information", Messagebox.OK,
												Messagebox.INFORMATION);
							}
						}
					});
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

	public void imprimir(String codigo_nota) throws Exception {
		if (codigo_nota.equals("")) {
			Messagebox.show("La nota de "
					+ (tbxTipo.getValue().equals(INotas.NOTAS_ENFERMERIA) ? "enfermería"
							: tbxTipo.getValue().equals(INotas.NOTAS_ACLARATORIAS) ? "aclaratoria" : "odontologia") + " no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Nota_enfermeria");
		paramRequest.put("codigo_nota", codigo_nota);
		paramRequest.put("tipo", tbxTipo.getValue());
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

	private Map llenarBeanDetalle(Detalle_nota_enf detalle) throws Exception {
		Map bean = new HashMap();
		bean.put("fecha", detalle.getFecha());
		bean.put("nota", detalle.getNota());
		bean.put("asunto", detalle.getAsunto());
		bean.put("codigo_nota", detalle.getCodigo_nota());
		return bean;
	}

	public void adicionarNuevo() throws Exception {
		actualizarPagina();
		Detalle_nota_enf detalle_nota_enf = new Detalle_nota_enf();
		detalle_nota_enf.setFecha(new Timestamp(new Date().getTime()));
		detalle_nota_enf.setNota("");
        detalle_nota_enf.setAsunto(""); 
		adicionarNota(detalle_nota_enf);
	}

	public void adicionarNota(Detalle_nota_enf detalle) throws Exception {
		try {
			Map bean = llenarBeanDetalle(detalle);
			lista_datos.add(bean);
			crearFilas();
			// repintarGridCuentas();
		} catch (Exception e) {
			
			Messagebox.show(e.getMessage(), "Error al adicionar cuenta",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void crearFilas() throws Exception {
		rowsNotas.getChildren().clear();
		for (int j = 0; j < lista_datos.size(); j++) {
			Map bean = lista_datos.get(j);
			crearFilaNota(bean, j);
		}
	}

	public void crearFilaNota(Map bean, int j) throws Exception {
		final int index = j;
		Timestamp fecha = (Timestamp) bean.get("fecha");
		String nota = (String) bean.get("nota");
		String asunto = (String) bean.get("asunto");
		String codigo_nota = (String) bean.get("codigo_nota"); 
		boolean disable = codigo_nota != null;

		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna fecha //
		Cell cell = new Cell();
		cell.setAlign("left");
		cell.setValign("top");
		final Datebox dtbxFecha = new Datebox();
		if (fecha != null) {
			dtbxFecha.setValue(new Date(fecha.getTime()));
		}
		dtbxFecha.setFormat("yyyy-MM-dd HH:mm");
		// dtbxFecha.setInplace(true);
		dtbxFecha.setId("fecha_" + j);
		dtbxFecha.setHflex("1");
		dtbxFecha.setReadonly(disable);
		dtbxFecha.setButtonVisible(!disable);
		cell.appendChild(dtbxFecha);
		fila.appendChild(cell);
		
		// Columna evoluciones //
		cell = new Cell();
		cell.setAlign("left");
		
		final Textbox tbxNota = new Textbox(nota);
		// tbxEvoluciones.setInplace(true);
		tbxNota.setId("nota_" + j);
		tbxNota.setHflex("1");
		tbxNota.setRows(7);
		tbxNota.setStyle("text-transform:uppercase");
		tbxNota.setReadonly(disable);
		
		if(tipo == INotas.NOTAS_ODONTOLOGIA){
			Vbox vbox = new Vbox();
			vbox.setWidth("100%"); 
			
        	final Textbox tbxAsunto = new Textbox(asunto != null ? asunto : "");
    		tbxAsunto.setId("asunto_" + j);
    		tbxAsunto.setWidth("97%");
    		tbxAsunto.setMaxlength(100);
    		tbxAsunto.setStyle("text-transform:uppercase"); 
    		vbox.appendChild(tbxAsunto);
    		tbxAsunto.setReadonly(disable);
    		
    		Space space = new  Space();
    		space.setOrient("vertical"); 
    		space.setHeight("2px");
    		vbox.appendChild(space);
    		
    		/* Agregamos separador */
    		Groupbox  groupbox = new Groupbox();
    		groupbox.setClosable(false);
    		groupbox.setMold("3d");
    		groupbox.setTitle("Notas"); 
    		groupbox.appendChild(tbxNota);
    		vbox.appendChild(groupbox);
    		cell.appendChild(vbox);
		}else{
		    cell.appendChild(tbxNota);
		}
		fila.appendChild(cell);

		// Columna borrar //
		cell = new Cell();
		cell.setAlign("left");
		cell.setValign("top");
		Image img = new Image("/images/borrar.gif");
		img.setId("img_" + j);
		img.setTooltiptext("Quitar registro");
		img.setStyle("cursor:pointer");
		
		Vbox vbox = new Vbox();
		vbox.setAlign("center");
		vbox.appendChild(img);
		cell.appendChild(vbox);
		fila.appendChild(cell);
		img.setVisible(!disable);
			img.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event event) throws Exception {
					
					Messagebox.show(
							"Esta seguro que desea eliminar este registro? ",
							"Eliminar Registro", Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event event) throws Exception {
									if ("onYes".equals(event.getName())) {
										// do the thing
//										actualizarPagina();
										lista_datos.remove(index);
										rowsNotas.removeChild(fila);
									}
								}
							});
				}
			});

		fila.setId("fila_" + j);

		rowsNotas.appendChild(fila);

		gridNotas.setVisible(true);
		gridNotas.setMold("paging");
		gridNotas.setPageSize(20);
		gridNotas.applyProperties();
		gridNotas.invalidate();
	}

	public void actualizarPagina() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);

			Row fila = (Row) rowsNotas.getFellow("fila_" + i);

			Datebox dtbxFecha = (Datebox) fila.getFellow("fecha_" + i);
			Textbox tbxNota = (Textbox) fila.getFellow("nota_" + i);
			Textbox tbxAsunto= (Textbox) fila.getFellowIfAny("asunto_" + i);
			
			/* validacion */
			if(tipo.equals(INotas.NOTAS_ODONTOLOGIA)){
				dtbxFecha.setStyle("text-transform:uppercase;background-color:white");
				tbxNota.setStyle("text-transform:uppercase;background-color:white");
			    if(tbxAsunto != null)tbxAsunto.setStyle("text-transform:uppercase;background-color:white");
			    boolean valid = true;
			    if(dtbxFecha.getValue() == null){
			    	valid = false;
			    	dtbxFecha.focus();
			    	dtbxFecha
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			    }
			    
			    if(tbxNota.getValue().trim().isEmpty()){
			    	valid = false;
			    	tbxNota.focus();
			    	tbxNota
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			    }
			    
			    if(tbxAsunto != null){
			    	if(tbxAsunto.getValue().trim().isEmpty()){
			    		valid = false;
			    		tbxAsunto.focus();
			    		tbxAsunto
						.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			    	}
			    }
			    
			    if(!valid){
			    	throw new HealthmanagerException("debe diligenciar la nota anterior"); 
			    }
			}

			bean.put("fecha", new Timestamp(dtbxFecha.getValue().getTime()));
			bean.put("nota", tbxNota.getValue().trim());
			bean.put("asunto", tbxAsunto != null ? tbxAsunto.getValue() : ""); 
			lista_datos.set(i, bean);
		}
	}


	@Override
	public void init() throws Exception {
		lista_datos = new LinkedList<Map>();
		cargarDatosSesion();
		initNota_enfermeria();
	}
}
