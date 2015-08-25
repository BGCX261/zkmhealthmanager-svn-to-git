/**
 * 
 */
package healthmanager.controller;

import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.CargardorDeDatos;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.softcomputo.composer.constantes.IParametrosSesion;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * @author ferney
 * 
 */
public class Orden_servicioAction extends Action implements AfterCompose {
	private static Logger log = Logger.getLogger(Orden_servicioAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	private Window form;

	// Componentes //
	private Listbox lbxFormato;

	private Textbox tbxAccion;
	private Textbox tbxCodigo_orden;
	private Textbox tbxCodigo_paciente;
	private Textbox tbxSexo_pct;
	private Textbox tbxFecha_nac;
	private Textbox tbxCodigo_administradora;
	private Textbox tbxId_plan;
	private Textbox tbxNro_ingreso;
	private Textbox tbxTipo_hc;

	private Datebox dtbxFecha_orden;
	private Bandbox tbxCodigo_ordenador;
	private Textbox tbxNomOrdenador;
	private Bandbox tbxCodigo_dx;
	private Textbox tbxNomDx;
	private Textbox tbxSexo_dx;
	private Textbox tbxLimite_inferior_dx;
	private Textbox tbxLimite_superior_dx;
	private Bandbox tbxId_prestador;
	private Textbox tbxNomPrestador;

	private Grid gridOrdenes;
	private Rows rowsOrdenes;
	@View
	Grid gridtabla;
	@View
	Div divcontenedorPametrizado;
	@View
	Toolbar toolbarGuardar;

	private List<Map> lista_datos;

	private String rol_usuario;
	private Admision admision;
	private String tipo_hc;

	private boolean trabajar_sin_admision_atendida;
	private Orden_servicio orden_servicio;

	@Override
	public void afterCompose() {
		CargardorDeDatos.initComponents(this);
		loadComponents();
		cargarDatosSesion();
		try {
			initOrden_servicio();
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
		}
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formOrden_servicio");

		lbxFormato = (Listbox) form.getFellow("lbxFormato");
		tbxCodigo_orden = (Textbox) form.getFellow("tbxCodigo_orden");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		tbxCodigo_paciente = (Textbox) form.getFellow("tbxCodigo_paciente");
		tbxSexo_pct = (Textbox) form.getFellow("tbxSexo_pct");
		tbxFecha_nac = (Textbox) form.getFellow("tbxFecha_nac");
		tbxCodigo_administradora = (Textbox) form
				.getFellow("tbxCodigo_administradora");
		tbxId_plan = (Textbox) form.getFellow("tbxId_plan");
		tbxNro_ingreso = (Textbox) form.getFellow("tbxNro_ingreso");
		tbxTipo_hc = (Textbox) form.getFellow("tbxTipo_hc");

		dtbxFecha_orden = (Datebox) form.getFellow("dtbxFecha_orden");
		tbxCodigo_ordenador = (Bandbox) form.getFellow("tbxCodigo_ordenador");
		tbxNomOrdenador = (Textbox) form.getFellow("tbxNomOrdenador");
		tbxCodigo_dx = (Bandbox) form.getFellow("tbxCodigo_dx");
		tbxNomDx = (Textbox) form.getFellow("tbxNomDx");
		tbxSexo_dx = (Textbox) form.getFellow("tbxSexo_dx");
		tbxLimite_inferior_dx = (Textbox) form
				.getFellow("tbxLimite_inferior_dx");
		tbxLimite_superior_dx = (Textbox) form
				.getFellow("tbxLimite_superior_dx");
		tbxId_prestador = (Bandbox) form.getFellow("tbxId_prestador");
		tbxNomPrestador = (Textbox) form.getFellow("tbxNomPrestador");

		gridOrdenes = (Grid) form.getFellow("gridOrdenes");
		rowsOrdenes = (Rows) form.getFellow("rowsOrdenes");
	}

	public void initOrden_servicio() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		try {
			String id_m = request.getParameter("id_menu");
			if (id_m != null) {
				// crearPermisos(new Integer(id_m));
			}
			Map parametros = Executions.getCurrent().getArg();

			admision = (Admision) parametros.get("admision");
			tipo_hc = (String) parametros.get("tipo_hc");

			String nro_identificacion = admision.getNro_identificacion();
			String nro_ingreso = admision.getNro_ingreso();
			String estado = admision.getEstado();
			String codigo_administradora = admision.getCodigo_administradora();
			String id_plan = admision.getId_plan();

			if (parametros.get("ocultarInformacion") != null) {
				gridtabla.setVisible(false);
				toolbarGuardar.setVisible(false);
				divcontenedorPametrizado.setVisible(true);
				trabajar_sin_admision_atendida = true;
			}

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(admision.getCodigo_empresa());
			paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());

			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);
			//log.info("Paciente: " + paciente);
			if (paciente == null) {
				paciente = new Paciente();
				//log.info("Paciente nulo");
			}

			String sexo_pct = paciente.getSexo();
			String fecha_nac = new SimpleDateFormat("dd-MM-yyyy")
					.format(paciente.getFecha_nacimiento());

			tbxCodigo_paciente.setValue(nro_identificacion);
			tbxSexo_pct.setValue(sexo_pct);
			tbxFecha_nac.setValue(fecha_nac);
			tbxNro_ingreso.setValue(nro_ingreso);
			tbxCodigo_administradora.setValue(codigo_administradora);
			tbxId_plan.setValue(id_plan);
			tbxTipo_hc.setValue(tipo_hc);

			if (nro_identificacion.equals("") && nro_ingreso.equals("")) {
				accionForm(true, "registrar");
				deshabilitarCampos(true);
			} else {
				Orden_servicio orden_servicio = new Orden_servicio();
				orden_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
				orden_servicio
						.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				orden_servicio.setCodigo_paciente(nro_identificacion);
				orden_servicio.setNro_ingreso(nro_ingreso);
				orden_servicio.setTipo_hc(tipo_hc);
				orden_servicio = getServiceLocator().getOrden_servicioService()
						.consultar(orden_servicio);
				// //log.info("historia: "+historia);
				if (orden_servicio != null) {
					mostrarDatos(orden_servicio);

				} else {
					accionForm(true, "registrar");
				}

				if (estado.equals("2")) {
					deshabilitarCampos(true);
				} else {
					deshabilitarCampos(false);
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

	public void cargarDatosSesion() {
		lista_datos = new LinkedList<Map>();
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);

		rol_usuario = (String) request.getSession().getAttribute(IParametrosSesion.PARAM_ROL_USUARIO);
	}

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
			tbxCodigo_ordenador.setValue(prestadores.getNro_identificacion());
			tbxNomOrdenador.setValue(prestadores.getNombres() + " "
					+ prestadores.getApellidos());

		} else {
			if (admision != null) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(admision.getCodigo_empresa());
				prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
				prestadores.setNro_identificacion(admision.getCodigo_medico());
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);

				tbxCodigo_ordenador.setValue((prestadores != null ? prestadores
						.getNro_identificacion() : "000000000"));
				tbxNomOrdenador.setValue((prestadores != null ? prestadores
						.getNombres() + " " + prestadores.getApellidos()
						: "MEDICO POR DEFECTO"));

			} else {
				tbxCodigo_ordenador.setValue("000000000");
				tbxNomOrdenador.setValue("MEDICO POR DEFECTO");
			}

		}
	}

	public void cargarDx() throws Exception {
		Cie cie = new Cie();
		cie.setCodigo((admision != null ? admision.getDiagnostico_ingreso()
				: "Z000"));
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		tbxCodigo_dx.setValue((cie != null ? cie.getCodigo() : "Z000"));
		tbxNomDx.setValue((cie != null ? cie.getNombre()
				: "EXAMEN MEDICO GENERAL"));

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
		Collection<Component> collection = form.getFellows();
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
		Collection<Component> collection = form.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxCodigo_paciente")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxSexo_pct")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxFecha_nac")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxNro_ingreso")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxCodigo_administradora")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxId_plan")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxTipo_hc")) {
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
		 * tbxCodigo_ordenador.setValue("000000000");
		 * tbxNomOrdenador.setValue("MEDICO POR DEFECTO");
		 * 
		 * tbxCodigo_dx.setValue("Z000");
		 * tbxNomDx.setValue("EXAMEN MEDICO GENERAL");
		 */
		cargarPrestador();
		cargarDx();
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
		Collection<Component> collection = form.getFellows();
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

		for (int i = 0; i < lista_datos.size(); i++) {
			Row fila = (Row) rowsOrdenes.getFellow("fila_" + i);

			Textbox tbxCodigo_procedimiento = (Textbox) fila
					.getFellow("codigo_procedimiento_" + i);
			Textbox tbxNombre_procedimiento = (Textbox) fila
					.getFellow("nombre_procedimiento_" + i);
			Doublebox tbxUnidades = (Doublebox) fila.getFellow("unidades_" + i);
			Doublebox tbxValor_procedimiento = (Doublebox) fila
					.getFellow("valor_procedimiento_" + i);
			/*
			 * Doublebox tbxDescuento = (Doublebox)
			 * fila.getFellow("descuento_"+i); Doublebox tbxIncremento =
			 * (Doublebox) fila.getFellow("incremento_"+i); Doublebox
			 * tbxValor_real = (Doublebox) fila.getFellow("valor_real_"+i);
			 * Textbox tbxSexo_pcd = (Textbox) fila.getFellow("sexo_pcd_"+i);
			 * Textbox tbxLimite_inferior_pcd = (Textbox)
			 * fila.getFellow("limite_inferior_pcd_"+i); Textbox
			 * tbxLimite_superior_pcd = (Textbox)
			 * fila.getFellow("limite_superior_pcd_"+i);
			 */
			Image img = (Image) fila.getFellow("img_" + i);

			if (!sw) {
				tbxCodigo_procedimiento.setDisabled(false);
				tbxNombre_procedimiento.setDisabled(false);
				tbxUnidades.setDisabled(false);
				tbxValor_procedimiento.setDisabled(false);
				img.setVisible(true);
			} else {
				tbxCodigo_procedimiento.setDisabled(true);
				tbxNombre_procedimiento.setDisabled(true);
				tbxUnidades.setDisabled(true);
				tbxValor_procedimiento.setDisabled(true);
				img.setVisible(false);
			}
		}

		if (!sw) {
			((Button) form.getFellow("btGuardar")).setDisabled(false);
			((Button) form.getFellow("btGuardar"))
					.setImage("/images/Save16.gif");

			((Button) form.getFellow("btEliminar")).setDisabled(false);
			((Button) form.getFellow("btEliminar"))
					.setImage("/images/eliminar.gif");

			((Button) form.getFellow("btAdicionar")).setDisabled(false);
		} else {
			((Button) form.getFellow("btGuardar")).setDisabled(true);
			((Button) form.getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");

			((Button) form.getFellow("btEliminar")).setDisabled(true);
			((Button) form.getFellow("btEliminar"))
					.setImage("/images/eliminar_gris.gif");

			((Button) form.getFellow("btAdicionar")).setDisabled(true);
		}

		if (rol_usuario.equals("05")) {
			tbxCodigo_ordenador.setDisabled(true);
		} else {
			tbxCodigo_ordenador.setDisabled(sw);
		}
	}

	public void buscarOrdenador(String value, Listbox lbx) throws Exception {
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

	public void selectedOrdenador(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxCodigo_ordenador.setValue("");
			tbxNomOrdenador.setValue("");
		} else {
			Prestadores dato = (Prestadores) listitem.getValue();
			tbxCodigo_ordenador.setValue(dato.getNro_identificacion());
			tbxNomOrdenador.setValue(dato.getNombres() + " "
					+ dato.getApellidos());
		}
		tbxCodigo_ordenador.close();
	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getAdministradoraService().setLimit(
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
				listcell.appendChild(new Label(dato.getNombre() + ""));
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
			tbxId_prestador.setValue("");
			tbxNomPrestador.setValue("");
		} else {
			Administradora dato = (Administradora) listitem.getValue();
			tbxId_prestador.setValue(dato.getCodigo());
			tbxNomPrestador.setValue(dato.getNombre());
		}
		tbxId_prestador.close();
	}

	public void buscarDx(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Cie> list = getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class, 
					parameters);

			lbx.getItems().clear();

			for (Cie dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getSexo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_inferior()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getLimite_superior()));
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

	public void selectedDx(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxCodigo_dx.setValue("");
			tbxNomDx.setValue("");
			tbxSexo_dx.setValue("A");
			tbxLimite_inferior_dx.setValue("0");
			tbxLimite_superior_dx.setValue("599");
		} else {
			Cie dato = (Cie) listitem.getValue();
			tbxCodigo_dx.setValue(dato.getCodigo());
			tbxNomDx.setValue(dato.getNombre());
			tbxSexo_dx.setValue(dato.getSexo());
			tbxLimite_inferior_dx.setValue(dato.getLimite_inferior());
			tbxLimite_superior_dx.setValue(dato.getLimite_superior());
		}
		tbxCodigo_dx.close();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxCodigo_ordenador.setStyle("background-color:white");
		tbxNomOrdenador.setStyle("background-color:white");

		tbxId_prestador.setStyle("background-color:white");
		tbxNomPrestador.setStyle("background-color:white");

		tbxCodigo_dx.setStyle("background-color:white");
		tbxNomDx.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo_ordenador.getText().trim().equals("")) {
			tbxCodigo_ordenador.setStyle("background-color:#F6BBBE");
			tbxNomOrdenador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (tbxId_prestador.getText().trim().equals("")) {
			tbxId_prestador.setStyle("background-color:#F6BBBE");
			tbxNomPrestador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (tbxCodigo_dx.getText().trim().equals("")) {
			tbxCodigo_dx.setStyle("background-color:#F6BBBE");
			tbxNomDx.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (valida && lista_datos.size() <= 0) {
			mensaje = "Debe crear al menos un registro de ordenes";
			valida = false;
		}

		if (valida) {
			Object result[] = validarDx("");
			valida = (Boolean) result[0];
			mensaje = (String) result[1];
		}

		if (valida) {
			actualizarPagina();
			for (int i = 0; i < lista_datos.size(); i++) {
				Map bean = lista_datos.get(i);
				String codigo_procedimiento = (String) bean
						.get("codigo_procedimiento");
				String nombre_procedimiento = (String) bean
						.get("nombre_procedimiento");
				//log.info("Nomgre procedimeinto: " + nombre_procedimiento);

				if (((Double) bean.get("unidades")) <= 0) {
					mensaje = "El valor de las unidades en el procedimiento "
							+ nombre_procedimiento
							+ " no puede ser menor ni igual a cero";
					valida = false;
					i = lista_datos.size();
				}

				if (valida) {
					Object result[] = validarPcd(bean, "El procedimiento");
					valida = (Boolean) result[0];
					mensaje = (String) result[1];
				}

				if (valida) {
					Object result[] = validarDx("");
					valida = (Boolean) result[0];
					mensaje = (String) result[1];
				}

				if (valida) {
					for (int j = i + 1; j < lista_datos.size(); j++) {
						Map beanAux = lista_datos.get(j);
						if (codigo_procedimiento.equals((String) beanAux
								.get("codigo_procedimiento"))) {
							valida = false;
							mensaje = "El procedimiento "
									+ codigo_procedimiento
									+ " se encuentra repetido";
							i = lista_datos.size();
							j = lista_datos.size();
						}
					}
				}
			}
		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public boolean validarFormExterno() throws Exception {

		tbxCodigo_ordenador.setStyle("background-color:white");
		tbxNomOrdenador.setStyle("background-color:white");

		tbxId_prestador.setStyle("background-color:white");
		tbxNomPrestador.setStyle("background-color:white");

		tbxCodigo_dx.setStyle("background-color:white");
		tbxNomDx.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (trabajar_sin_admision_atendida) {
			cargarPrestador();
			cargarDx();
			tbxId_prestador.setValue(admision.getCodigo_administradora());
		}

		if (tbxCodigo_ordenador.getText().trim().equals("")) {
			tbxCodigo_ordenador.setStyle("background-color:#F6BBBE");
			tbxNomOrdenador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (tbxId_prestador.getText().trim().equals("")) {
			tbxId_prestador.setStyle("background-color:#F6BBBE");
			tbxNomPrestador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (tbxCodigo_dx.getText().trim().equals("")) {
			tbxCodigo_dx.setStyle("background-color:#F6BBBE");
			tbxNomDx.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		// if (valida && lista_datos.size() <= 0) {
		// mensaje = "Debe crear al menos un registro de ordenes";
		// valida = false;
		// }

		if (valida) {
			Object result[] = validarDx("");
			valida = (Boolean) result[0];
			mensaje = (String) result[1];
		}

		if (valida && !lista_datos.isEmpty()) {
			actualizarPagina();
			for (int i = 0; i < lista_datos.size(); i++) {
				Map bean = lista_datos.get(i);
				String codigo_procedimiento = (String) bean
						.get("codigo_procedimiento");
				String nombre_procedimiento = (String) bean
						.get("nombre_procedimiento");

				if (((Double) bean.get("unidades")) <= 0) {
					mensaje = "El valor de las unidades en el procedimiento "
							+ nombre_procedimiento
							+ " no puede ser menor ni igual a cero";
					valida = false;
					i = lista_datos.size();
				}

				if (valida) {
					Object result[] = validarPcd(bean, "El procedimiento");
					valida = (Boolean) result[0];
					mensaje = (String) result[1];
				}

				if (valida) {
					Object result[] = validarDx("");
					valida = (Boolean) result[0];
					mensaje = (String) result[1];
				}

				if (valida) {
					for (int j = i + 1; j < lista_datos.size(); j++) {
						Map beanAux = lista_datos.get(j);
						if (codigo_procedimiento.equals((String) beanAux
								.get("codigo_procedimiento"))) {
							valida = false;
							mensaje = "El procedimiento "
									+ codigo_procedimiento
									+ " se encuentra repetido";
							i = lista_datos.size();
							j = lista_datos.size();
						}
					}
				}
			}
		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public Object[] validarPcd(Map bean, String msg) {

		String nombre_procedimiento = (String) bean.get("nombre_procedimiento");
		String limite_inferior = (String) bean.get("limite_inferior_pcd");
		String limite_superior = (String) bean.get("limite_superior_pcd");
		String sexo_pcd = (String) bean.get("sexo_pcd");

		//log.info("@validarPcd Nombre_procedimeinto cargado: "
				//+ nombre_procedimiento);

		if (!nombre_procedimiento.equals("")) {
			if (!limite_inferior.equals("0") && !limite_inferior.equals("000")
					&& !limite_inferior.equals("")) {

				String unidad_medida = limite_inferior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Util.getEdad(tbxFecha_nac.getValue(),
						unidad_medida, false);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) < Integer
							.parseInt(limite_inferior.substring(1))) {
						return new Object[] {
								false,
								msg
										+ " "
										+ nombre_procedimiento
										+ " no corresponde con la edad del paciente" };
					}
				}
			}

			if (!limite_superior.equals("599") && !limite_superior.equals("0")
					&& !limite_superior.equals("")) {

				String unidad_medida = limite_superior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Util.getEdad(tbxFecha_nac.getValue(),
						unidad_medida, false);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) < Integer
							.parseInt(limite_superior.substring(1))) {
						return new Object[] {
								false,
								msg
										+ " "
										+ nombre_procedimiento
										+ " no corresponde con la edad del paciente" };
					}
				}
			}
			if (!sexo_pcd.equals("A") && !sexo_pcd.equals("")) {

				String sex = sexo_pcd;
				if (sex.equals("H")) {
					sex = "M";
				} else if (sex.equals("M")) {
					sex = "F";
				}
				if (!sex.equals(tbxSexo_pct.getValue())) {
					return new Object[] {
							false,
							msg
									+ " "
									+ nombre_procedimiento
									+ " no corresponde con el sexo del paciente" };
				}
			}
		}

		return new Object[] { true, "" };

	}

	public Object[] validarDx(String msg) {

		String nombre_dx = tbxNomDx.getValue();
		String limite_inferior = tbxLimite_inferior_dx.getValue();
		String limite_superior = tbxLimite_superior_dx.getValue();
		String sexo_dx = tbxSexo_dx.getValue();

		/*
		 * //log.info("limite_inferior_dx: "+limite_inferior);
		 * //log.info("limite_superior_dx: "+limite_superior);
		 * //log.info("sexo_dx: "+sexo_dx);
		 */

		if (!nombre_dx.equals("")) {
			if (!limite_inferior.equals("0") && !limite_inferior.equals("000")
					&& !limite_inferior.equals("")) {

				String unidad_medida = limite_inferior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Util.getEdad(tbxFecha_nac.getValue(),
						unidad_medida, false);
				// //log.info("edad: "+edad);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) < Integer
							.parseInt(limite_inferior.substring(1))) {
						return new Object[] {
								false,
								"El diagnostico "
										+ msg
										+ " "
										+ nombre_dx
										+ " no corresponde con la edad del paciente" };
					}
				}
			}

			if (!limite_superior.equals("599") && !limite_superior.equals("0")
					&& !limite_superior.equals("")) {

				String unidad_medida = limite_superior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Util.getEdad(tbxFecha_nac.getValue(),
						unidad_medida, false);
				// //log.info("edad: "+edad);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) < Integer
							.parseInt(limite_superior.substring(1))) {
						return new Object[] {
								false,
								"El diagnostico "
										+ msg
										+ " "
										+ nombre_dx
										+ " no corresponde con la edad del paciente" };
					}
				}
			}
			if (!sexo_dx.equals("A") && !sexo_dx.equals("")) {

				String sex = sexo_dx;
				if (sex.equals("H")) {
					sex = "M";
				} else if (sex.equals("M")) {
					sex = "F";
				}
				if (!sex.equals(tbxSexo_pct.getValue())) {
					return new Object[] {
							false,
							"El diagnostico "
									+ msg
									+ " "
									+ nombre_dx
									+ " no corresponde con el sexo del paciente" };
				}
			}
		}

		return new Object[] { true, "" };

	}

	public Map<String, Object> obtenerDatos() {
		Map datos = new HashMap();

		Orden_servicio orden_servicio = new Orden_servicio();
		orden_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
		orden_servicio.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		orden_servicio.setCodigo_orden(tbxCodigo_orden.getValue());
		orden_servicio.setFecha_orden(new Timestamp(dtbxFecha_orden.getValue()
				.getTime()));
		orden_servicio.setNro_ingreso(tbxNro_ingreso.getValue());
		orden_servicio.setCodigo_paciente(admision.getNro_identificacion());
		orden_servicio.setCodigo_administradora(admision
				.getCodigo_administradora());
		orden_servicio.setId_plan(admision.getId_plan());
		orden_servicio.setCodigo_ordenador(tbxCodigo_ordenador.getValue());
		orden_servicio.setCodigo_dx(tbxCodigo_dx.getValue());
		orden_servicio.setId_prestador(usuarios.getCodigo());
		orden_servicio.setEstado("01");
		orden_servicio.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		orden_servicio.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		orden_servicio.setCreacion_user(usuarios.getCodigo());
		orden_servicio.setUltimo_user(usuarios.getCodigo());
		orden_servicio.setActualizado(true);
		orden_servicio.setTipo_hc(tbxTipo_hc.getValue());

		List<Detalle_orden> lista_detalle = new LinkedList<Detalle_orden>();
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);

			String consecutivo = i + "";
			String codigo_procedimiento = (String) bean
					.get("codigo_procedimiento");
			double valor_procedimiento = (Double) bean
					.get("valor_procedimiento");
			double unidades = (Double) bean.get("unidades");
			double descuento = (Double) bean.get("descuento");
			double incremento = (Double) bean.get("incremento");
			double valor_real = (Double) bean.get("valor_real");

			Detalle_orden detalle = new Detalle_orden();
			detalle.setCodigo_empresa(orden_servicio.getCodigo_empresa());
			detalle.setCodigo_sucursal(orden_servicio.getCodigo_sucursal());
			detalle.setCodigo_orden(orden_servicio.getId()); 
			detalle.setConsecutivo(consecutivo);
			detalle.setCodigo_procedimiento(codigo_procedimiento);
			detalle.setValor_procedimiento(valor_procedimiento);
			detalle.setUnidades((int) unidades);
			detalle.setDescuento(descuento);
			detalle.setIncremento(incremento);
			detalle.setValor_real(valor_real);
			lista_detalle.add(detalle);
		}

		datos.put("orden_servicio", orden_servicio);
		datos.put("lista_detalle", lista_detalle);
		datos.put("accion", tbxAccion.getText());
		return datos;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			// setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //

				Map datos = new HashMap();

				Orden_servicio orden_servicio = new Orden_servicio();
				orden_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
				orden_servicio
						.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				orden_servicio.setCodigo_orden(tbxCodigo_orden.getValue());
				orden_servicio.setFecha_orden(new Timestamp(dtbxFecha_orden
						.getValue().getTime()));
				orden_servicio.setNro_ingreso(tbxNro_ingreso.getValue());
				orden_servicio
						.setCodigo_paciente(tbxCodigo_paciente.getValue());
				orden_servicio
						.setCodigo_administradora(tbxCodigo_administradora
								.getValue());
				orden_servicio.setId_plan(tbxId_plan.getValue());
				orden_servicio.setCodigo_ordenador(tbxCodigo_ordenador
						.getValue());
				orden_servicio.setCodigo_dx(tbxCodigo_dx.getValue());
				orden_servicio.setId_prestador(tbxId_prestador.getValue());
				orden_servicio.setEstado("01");
				orden_servicio.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				orden_servicio.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				orden_servicio.setCreacion_user(usuarios.getCodigo());
				orden_servicio.setUltimo_user(usuarios.getCodigo());
				orden_servicio.setActualizado(true);
				orden_servicio.setTipo_hc(tbxTipo_hc.getValue());

				List<Detalle_orden> lista_detalle = new LinkedList<Detalle_orden>();
				for (int i = 0; i < lista_datos.size(); i++) {
					Map bean = lista_datos.get(i);

					String consecutivo = i + "";
					String codigo_procedimiento = (String) bean
							.get("codigo_procedimiento");
					double valor_procedimiento = (Double) bean
							.get("valor_procedimiento");
					double unidades = (Double) bean.get("unidades");
					double descuento = (Double) bean.get("descuento");
					double incremento = (Double) bean.get("incremento");
					double valor_real = (Double) bean.get("valor_real");

					Detalle_orden detalle = new Detalle_orden();
					detalle.setCodigo_empresa(orden_servicio
							.getCodigo_empresa());
					detalle.setCodigo_sucursal(orden_servicio
							.getCodigo_sucursal());
					detalle.setCodigo_orden(orden_servicio.getId()); 
					detalle.setConsecutivo(consecutivo);
					detalle.setCodigo_procedimiento(codigo_procedimiento);
					detalle.setValor_procedimiento(valor_procedimiento);
					detalle.setUnidades((int) unidades);
					detalle.setDescuento(descuento);
					detalle.setIncremento(incremento);
					detalle.setValor_real(valor_real);
					lista_detalle.add(detalle);
				}

				datos.put("orden_servicio", orden_servicio);
				datos.put("lista_detalle", lista_detalle);
				datos.put("accion", tbxAccion.getText());

				this.orden_servicio = getServiceLocator().getOrden_servicioService()
						.guardar(datos);
				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					tbxAccion.setText("modificar");
				}
				mostrarDatos(orden_servicio);

				Messagebox
						.show("Los datos se guardaron satisfactoriamente. Desea imprimir el documento? ",
								"impresion", Messagebox.YES + Messagebox.NO,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											imprimir();
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
		orden_servicio = (Orden_servicio) obj; 
		try {
			tbxCodigo_orden.setValue(orden_servicio.getCodigo_orden());
			tbxCodigo_paciente.setValue(orden_servicio.getCodigo_paciente());
			tbxCodigo_administradora.setValue(orden_servicio
					.getCodigo_administradora());
			tbxId_plan.setValue(orden_servicio.getId_plan());
			tbxNro_ingreso.setValue(orden_servicio.getNro_ingreso());

			dtbxFecha_orden.setValue(orden_servicio.getFecha_orden());

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(orden_servicio.getCodigo_empresa());
			prestadores.setCodigo_sucursal(orden_servicio.getCodigo_sucursal());
			prestadores.setNro_identificacion(orden_servicio
					.getCodigo_ordenador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			tbxCodigo_ordenador.setValue(orden_servicio.getCodigo_ordenador());
			tbxNomOrdenador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));

			Cie cie = new Cie();
			cie.setCodigo(orden_servicio.getCodigo_dx());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
			tbxCodigo_dx.setValue(orden_servicio.getCodigo_dx());
			tbxNomDx.setValue((cie != null ? cie.getNombre() : ""));
			tbxSexo_dx.setValue((cie != null ? cie.getSexo() : "A"));
			tbxLimite_inferior_dx.setValue((cie != null ? cie
					.getLimite_inferior() : "0"));
			tbxLimite_superior_dx.setValue((cie != null ? cie
					.getLimite_superior() : "599"));

			Administradora admin = new Administradora();
			admin.setCodigo(orden_servicio.getId_prestador());
			admin = getServiceLocator().getAdministradoraService().consultar(
					admin);
			tbxId_prestador.setValue(orden_servicio.getId_prestador());
			tbxNomPrestador.setValue((admin != null ? admin.getNombre() : ""));

			lista_datos.clear();
			rowsOrdenes.getChildren().clear();
			List<Detalle_orden> lista_detalle = orden_servicio
					.getLista_detalle();
			for (Detalle_orden detalle : lista_detalle) {
				Map bean = llenarBeanDetalle(detalle);
				lista_datos.add(bean);
			}

			crearFilas();

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(String codigo_orden) throws Exception {
		final String cod_ord = codigo_orden;
		if (codigo_orden.equals("")) {
			Messagebox.show("La orden de servicio no se ha guardado aún",
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
								Orden_servicio orden_servicio = new Orden_servicio();
								orden_servicio.setCodigo_empresa(empresa
										.getCodigo_empresa());
								orden_servicio.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								orden_servicio.setCodigo_orden(cod_ord);
								int result = getServiceLocator()
										.getOrden_servicioService().eliminar(
												orden_servicio);
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

	public void imprimir() throws Exception {
		if (orden_servicio == null) {
			Messagebox.show("La orden no se ha guardado aún", "Informacion ..",
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Orden_servicio");
		paramRequest.put("id", orden_servicio.getId());
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

	private Map llenarBeanDetalle(Detalle_orden detalle) throws Exception {
		String codigo_procedimiento = detalle.getCodigo_procedimiento();
		String nombre_procedimiento = "";
		String sexo_pcd = "A";
		String limite_inferior_pcd = "0";
		String limite_superior_pcd = "0";
		double valor_procedimiento = detalle.getValor_procedimiento();
		int unidades = detalle.getUnidades();
		double descuento = detalle.getDescuento();
		double incremento = detalle.getIncremento();
		double valor_real = detalle.getValor_real();
		String codigo_cups = detalle.getCodigo_cups();

		Procedimientos proc = new Procedimientos();
		proc.setId_procedimiento(new Long(detalle.getCodigo_procedimiento()));
		proc.setCodigo_cups(detalle.getCodigo_procedimiento());
		proc = getServiceLocator().getProcedimientosService().consultar(proc);
		nombre_procedimiento = (proc != null ? proc.getDescripcion() : "");
		sexo_pcd = (proc != null ? proc.getSexo() : "");
		limite_inferior_pcd = (proc != null ? proc.getLimite_inferior() : "");
		limite_superior_pcd = (proc != null ? proc.getLimite_superior() : "");

		Map bean = new HashMap();
		bean.put("codigo_procedimiento", codigo_procedimiento);
		bean.put("nombre_procedimiento", nombre_procedimiento);
		bean.put("unidades", (double) unidades);
		bean.put("valor_procedimiento", valor_procedimiento);
		bean.put("descuento", descuento);
		bean.put("incremento", incremento);
		bean.put("valor_real", valor_real);
		bean.put("sexo_pcd", sexo_pcd);
		bean.put("limite_inferior_pcd", limite_inferior_pcd);
		bean.put("limite_superior_pcd", limite_superior_pcd);
		bean.put("codigo_cups", codigo_cups);
		return bean;
	}

	public void openPcd() throws Exception {

		Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
				.getManuales_tarifarios(admision);

		String pages = "";
		Contratos contratos = new Contratos();
		contratos.setCodigo_empresa(empresa.getCodigo_empresa());
		contratos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		contratos.setCodigo_administradora(admision.getCodigo_administradora());
		contratos.setId_plan(admision.getId_plan());
		contratos = getServiceLocator().getContratosService().consultar(
				contratos);
	
		String anio = (manuales_tarifarios != null ? (manuales_tarifarios
				.getAnio() != null ? manuales_tarifarios.getAnio() : "") : "");

		
			pages = "/pages/openProcedimientos.zul";

		Map parametros = new HashMap();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("restringido", "");
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("nro_identificacion", tbxCodigo_paciente.getValue());
		// parametros.put("estrato", "");
		// parametros.put("consulta", "");
		// parametros.put("quirurgico", "");
		parametros.put("anio", anio);

		//log.info("Paginas: " + pages);

		Component componente = Executions.createComponents(pages, form,
				parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
		ventana.setMode("modal");
	}

	public void adicionarPcd(Map pcd) throws Exception {
		//log.info("@adicionarPcd Ejecutando evento");
		actualizarPagina();
		Detalle_orden detalle_orden = new Detalle_orden();
		detalle_orden.setCodigo_empresa((String) pcd.get("codigo_empresa"));
		detalle_orden.setCodigo_sucursal((String) pcd.get("codigo_sucursal"));
		detalle_orden.setCodigo_procedimiento((String) pcd
				.get("codigo_procedimiento"));
		detalle_orden.setValor_procedimiento((Double) pcd
				.get("valor_procedimiento"));
		detalle_orden.setDescuento((Double) pcd.get("descuento"));
		detalle_orden.setIncremento((Double) pcd.get("incremento"));
		detalle_orden.setValor_real((Double) pcd.get("valor_real"));
		detalle_orden.setUnidades(1);
		detalle_orden.setCodigo_cups((String) pcd.get("codigo_cups"));
		detalle_orden.setRealizado("N");

		adicionarOrden(detalle_orden, (String) pcd.get("nombre_procedimiento"));
	}

	public void adicionarOrden(Detalle_orden detalle,
			String nombre_procedimiento) throws Exception {
		try {
			Map bean = llenarBeanDetalle(detalle);
			bean.put("nombre_procedimiento", nombre_procedimiento);
			lista_datos.add(bean);
			crearFilas();
			// repintarGridCuentas();
		} catch (Exception e) {
			
			Messagebox.show(e.getMessage(), "Error al adicionar cuenta",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void crearFilas() throws Exception {
		rowsOrdenes.getChildren().clear();
		for (int j = 0; j < lista_datos.size(); j++) {
			Map bean = lista_datos.get(j);
			crearFilaOrden(bean, j);
		}
	}

	public void crearFilaOrden(Map bean, int j) throws Exception {
		final int index = j;

		String codigo_procedimiento = (String) bean.get("codigo_procedimiento");
		String nombre_procedimiento = (String) bean.get("nombre_procedimiento");
		double valor_procedimiento = (Double) bean.get("valor_procedimiento");
		double unidades = (Double) bean.get("unidades");
		double descuento = (Double) bean.get("descuento");
		double incremento = (Double) bean.get("incremento");
		double valor_real = (Double) bean.get("valor_real");
		String sexo_pcd = (String) bean.get("sexo_pcd");
		String limite_inferior_pcd = (String) bean.get("limite_inferior_pcd");
		String limite_superior_pcd = (String) bean.get("limite_superior_pcd");

		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo //
		Cell cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_procedimiento = new Textbox(
				codigo_procedimiento);
		// tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("codigo_procedimiento_" + j);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setReadonly(true);
		cell.appendChild(tbxCodigo_procedimiento);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_procedimiento = new Textbox(
				nombre_procedimiento);
		// tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_procedimiento_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna unidades //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxUnidades = new Doublebox(unidades);
		// tbxUnidades.setInplace(true);
		tbxUnidades.setId("unidades_" + j);
		tbxUnidades.setFormat("#,##0.00");
		tbxUnidades.setHflex("1");
		tbxUnidades.setReadonly(false);
		cell.appendChild(tbxUnidades);
		fila.appendChild(cell);

		// Columna valor pcd //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValor_procedimiento = new Doublebox(
				valor_procedimiento);
		// tbxValor_procedimiento.setInplace(true);
		tbxValor_procedimiento.setId("valor_procedimiento_" + j);
		tbxValor_procedimiento.setFormat("#,##0.00");
		tbxValor_procedimiento.setHflex("1");
		tbxValor_procedimiento.setReadonly(true);
		cell.appendChild(tbxValor_procedimiento);

		final Doublebox tbxDescuento = new Doublebox(descuento);
		tbxDescuento.setId("descuento_" + j);
		tbxDescuento.setVisible(false);
		cell.appendChild(tbxDescuento);

		final Doublebox tbxIncremento = new Doublebox(incremento);
		tbxIncremento.setId("incremento_" + j);
		tbxIncremento.setVisible(false);
		cell.appendChild(tbxIncremento);

		final Doublebox tbxValor_real = new Doublebox(valor_real);
		tbxValor_real.setId("valor_real_" + j);
		tbxValor_real.setVisible(false);
		cell.appendChild(tbxValor_real);

		final Textbox tbxSexo_pcd = new Textbox(sexo_pcd);
		tbxSexo_pcd.setId("sexo_pcd_" + j);
		tbxSexo_pcd.setVisible(false);
		cell.appendChild(tbxSexo_pcd);

		final Textbox tbxLimite_inferior_pcd = new Textbox(limite_inferior_pcd);
		tbxLimite_inferior_pcd.setId("limite_inferior_pcd_" + j);
		tbxLimite_inferior_pcd.setVisible(false);
		cell.appendChild(tbxLimite_inferior_pcd);

		final Textbox tbxLimite_superior_pcd = new Textbox(limite_superior_pcd);
		tbxLimite_superior_pcd.setId("limite_superior_pcd_" + j);
		tbxLimite_superior_pcd.setVisible(false);
		cell.appendChild(tbxLimite_superior_pcd);

		fila.appendChild(cell);

		// Columna borrar //
		cell = new Cell();
		cell.setAlign("left");
		cell.setValign("top");
		Image img = new Image("/images/borrar.gif");
		img.setId("img_" + j);
		img.setTooltiptext("Quitar registro");
		img.setStyle("cursor:pointer");
		cell.appendChild(img);
		fila.appendChild(cell);

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
									actualizarPagina();
									lista_datos.remove(index);
									crearFilas();
								}
							}
						});
			}
		});

		fila.setId("fila_" + j);

		rowsOrdenes.appendChild(fila);

		gridOrdenes.setVisible(true);
		gridOrdenes.setMold("paging");
		gridOrdenes.setPageSize(20);
		gridOrdenes.applyProperties();
		gridOrdenes.invalidate();

	}

	public void actualizarPagina() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);

			Row fila = (Row) rowsOrdenes.getFellow("fila_" + i);
			Doublebox tbxUnidades = (Doublebox) fila.getFellow("unidades_" + i);
			bean.put("unidades", tbxUnidades.getValue());
			lista_datos.set(i, bean);
		}
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

}
