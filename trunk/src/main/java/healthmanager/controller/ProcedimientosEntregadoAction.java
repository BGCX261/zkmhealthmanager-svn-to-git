/**
 * 
 */
package healthmanager.controller;

import healthmanager.controller.ZKWindow.OpcionesFormulario;
import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Datos_procedimientoService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IDatosProcedimientos;
import com.framework.interfaces.ISeleccionarComponente;
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
public class ProcedimientosEntregadoAction extends Listbox implements
		AfterCompose, ISeleccionarComponente {
	private static Logger log = Logger
			.getLogger(Orden_servicio_internoAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	// Componentes //
	private Listbox _lbxFormatoOrden;

	private Textbox _tbxAccion;
	private Textbox _tbxCodigo_orden;
	private Textbox _tbxCodigo_paciente;
	private Textbox _tbxSexo_pct;
	private Textbox _tbxFecha_nac;
	private Textbox _tbxCodigo_administradora;
	private Textbox _tbxId_plan;
	private Textbox _tbxNro_ingreso;
	private Textbox _tbxTipo_hc;

	private Datebox _dtbxFecha_orden;
	private Bandbox _tbxCodigo_ordenador;
	private Textbox _tbxNomOrdenador;
	private Bandbox _tbxCodigo_dx;
	private Textbox _tbxNomOrdenDx;
	private Textbox _tbxSexo_dx;
	private Textbox _tbxLimite_inferior_dx;
	private Textbox _tbxLimite_superior_dx;
	private Bandbox _tbxId_prestador;
	private Textbox _tbxNomPrestadorOrden;

	private Listbox _listboxOrdenes_servicio;
	@View
	Grid _gridtabla;
	@View
	Div _divcontenedorPametrizado;
	@View
	Toolbar _toolbarGuardar;

	private List<Map> lista_datos;

	private String rol_usuario;
	private Admision admision;
	private String tipo_hc;

	private boolean trabajar_sin_admision_atendida;

	private ZKWindow zkWindow;

	private Map<String, Object> parametros;

	private String opcion_formulario;

	private Orden_servicio orden_servicio;

	@View
	private Toolbarbutton _btAdicionar2Orden;

	private List<String> seleccionados = new ArrayList<String>();

	@Override
	public void afterCompose() {

		try {
			parametros = (Map<String, Object>) Executions.getCurrent().getArg();
			CargardorDeDatos.initComponents(this);

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
		}
	}

	public void inicializar(ZKWindow zkWindow) throws Exception {
		setZkWindow(zkWindow);
		loadComponents();
		cargarDatosSesion();
		initOrden_servicio();
	}

	public void loadComponents() {
		_listboxOrdenes_servicio = (Listbox) this
				.getFellow("_listboxOrdenes_servicio");

		_lbxFormatoOrden = (Listbox) this.getFellow("_lbxFormatoOrden");
		_tbxCodigo_orden = (Textbox) this.getFellow("_tbxCodigo_orden");
		_tbxAccion = (Textbox) this.getFellow("_tbxAccionOrden");
		_tbxCodigo_paciente = (Textbox) this.getFellow("_tbxCodigo_paciente");
		_tbxSexo_pct = (Textbox) this.getFellow("_tbxSexo_pctOrden");
		_tbxFecha_nac = (Textbox) this.getFellow("_tbxFecha_nacOrden");
		_tbxCodigo_administradora = (Textbox) this
				.getFellow("_tbxCodigo_administradoraOrden");
		_tbxId_plan = (Textbox) this.getFellow("_tbxId_planOrden");
		_tbxNro_ingreso = (Textbox) this.getFellow("_tbxNro_ingresoOrden");
		_tbxTipo_hc = (Textbox) this.getFellow("_tbxTipo_hcOrden");

		_dtbxFecha_orden = (Datebox) this.getFellow("_dtbxFecha_orden");
		_tbxCodigo_ordenador = (Bandbox) this.getFellow("_tbxCodigo_ordenador");
		_tbxNomOrdenador = (Textbox) this.getFellow("_tbxNomOrdenador");
		_tbxCodigo_dx = (Bandbox) this.getFellow("_tbxCodigo_dx");
		_tbxNomOrdenDx = (Textbox) this.getFellow("_tbxNomOrdenDx");
		_tbxSexo_dx = (Textbox) this.getFellow("_tbxSexo_dx");
		_tbxLimite_inferior_dx = (Textbox) this
				.getFellow("_tbxLimite_inferior_dx");
		_tbxLimite_superior_dx = (Textbox) this
				.getFellow("_tbxLimite_superior_dx");
		_tbxId_prestador = (Bandbox) this.getFellow("_tbxId_prestador");
		_tbxNomPrestadorOrden = (Textbox) this
				.getFellow("_tbxNomPrestadorOrden");

	}

	public void initOrden_servicio() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		try {
			opcion_formulario = (String) parametros.get("opcion_formulario");
			String id_m = request.getParameter("id_menu");
			if (id_m != null) {
				// crearPermisos(new Integer(id_m));
			}

			admision = (Admision) parametros.get("admision");
			tipo_hc = (String) parametros.get("tipo_hc");

			String nro_identificacion = admision.getNro_identificacion();
			String nro_ingreso = admision.getNro_ingreso();
			String estado = admision.getEstado();
			String codigo_administradora = admision.getCodigo_administradora();
			String id_plan = admision.getId_plan();

			if (parametros.get("ocultarInformacion") != null) {
				_gridtabla.setVisible(false);
				_toolbarGuardar.setVisible(false);
				_divcontenedorPametrizado.setVisible(true);
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

			_tbxCodigo_paciente.setValue(nro_identificacion);
			_tbxSexo_pct.setValue(sexo_pct);
			_tbxFecha_nac.setValue(fecha_nac);
			_tbxNro_ingreso.setValue(nro_ingreso);
			_tbxCodigo_administradora.setValue(codigo_administradora);
			_tbxId_plan.setValue(id_plan);
			_tbxTipo_hc.setValue(tipo_hc);

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
	 * ((Button)form.getFellow("_btCancelar")).setDisabled(true);
	 * ((Button)form.getFellow
	 * ("btCancelar")).setImage("/images/cancel_disabled.jpg"); }
	 * if(!permisos_sc.getPermiso_crear()){
	 * ((Button)form.getFellow("_btNuevo")).setDisabled(true);
	 * ((Button)form.getFellow
	 * ("btNuevo")).setImage("/images/New16_disabled.gif");
	 * ((Button)form.getFellow("_btNew")).setDisabled(true);
	 * ((Button)form.getFellow
	 * ("_btNew")).setImage("/images/New16_disabled.gif"); } }
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
			_tbxCodigo_ordenador.setValue(prestadores.getNro_identificacion());
			_tbxNomOrdenador.setValue(prestadores.getNombres() + " "
					+ prestadores.getApellidos());

		} else {
			if (admision != null) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(admision.getCodigo_empresa());
				prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
				prestadores.setNro_identificacion(admision.getCodigo_medico());
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);

				_tbxCodigo_ordenador
						.setValue((prestadores != null ? prestadores
								.getNro_identificacion() : "000000000"));
				_tbxNomOrdenador.setValue((prestadores != null ? prestadores
						.getNombres() + " " + prestadores.getApellidos()
						: "MEDICO POR DEFECTO"));

			} else {
				_tbxCodigo_ordenador.setValue("000000000");
				_tbxNomOrdenador.setValue("MEDICO POR DEFECTO");
			}

		}
	}

	public void cargarDx() throws Exception {
		Cie cie = new Cie();
		cie.setCodigo((admision != null ? admision.getDiagnostico_ingreso()
				: "Z000"));
		cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		_tbxCodigo_dx.setValue((cie != null ? cie.getCodigo() : "Z000"));
		_tbxNomOrdenDx.setValue((cie != null ? cie.getNombre()
				: "EXAMEN MEDICO GENERAL"));

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		if (accion.equalsIgnoreCase("registrar")) {
			limpiarDatos();
		}

		_tbxAccion.setValue(accion);
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
								"tbxCodigo_paciente")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxSexo_pctOrden")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxFecha_nacOrden")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxNro_ingresoOrden")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxCodigo_administradoraOrden")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxId_planOrden")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxTipo_hcOrden")) {
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
		 * ((Button)form.getFellow("_btGuardar")).setDisabled(false);
		 * ((Button)form
		 * .getFellow("_btGuardar")).setImage("/images/Save16.gif"); }else{
		 * ((Button)form.getFellow("_btGuardar")).setDisabled(true); ((Button
		 * )form.getFellow("_btGuardar")).setImage("/images/Save16_disabled.gif"
		 * ); }
		 */
	}

	// Limpiamos los campos del formulario //
	public void deshabilitarCampos(boolean sw) throws Exception {
		Collection<Component> collection = this.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals(
						"tbxValueOrden")) {
					((Textbox) abstractComponent).setDisabled(sw);
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals(
						"lbxFormatoOrden")) {
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
			Listitem fila = (Listitem) _listboxOrdenes_servicio
					.getFellow("_fila_" + i);

			Textbox tbxCodigo_procedimiento = (Textbox) fila
					.getFellow("_codigo_procedimiento_" + i);
			Textbox tbxNombre_procedimiento = (Textbox) fila
					.getFellow("_nombre_procedimiento_" + i);
			Doublebox tbxUnidades = (Doublebox) fila
					.getFellow("_unidades_" + i);
			Doublebox tbxValor_procedimiento = (Doublebox) fila
					.getFellow("_valor_procedimiento_" + i);
			/*
			 * Doublebox tbxDescuento = (Doublebox)
			 * fila.getFellow("_descuento_"+i); Doublebox tbxIncremento =
			 * (Doublebox) fila.getFellow("_incremento_"+i); Doublebox
			 * tbxValor_real = (Doublebox) fila.getFellow("_valor_real_"+i);
			 * Textbox tbxSexo_pcd = (Textbox) fila.getFellow("_sexo_pcd_"+i);
			 * Textbox tbxLimite_inferior_pcd = (Textbox)
			 * fila.getFellow("_limite_inferior_pcd_"+i); Textbox
			 * tbxLimite_superior_pcd = (Textbox)
			 * fila.getFellow("_limite_superior_pcd_"+i);
			 */
			Image img = (Image) fila.getFellow("_img_" + i);

			if (!sw) {
				tbxCodigo_procedimiento.setReadonly(false);
				tbxNombre_procedimiento.setReadonly(false);
				tbxUnidades.setReadonly(false);
				tbxValor_procedimiento.setReadonly(false);
				img.setVisible(true);
			} else {
				tbxCodigo_procedimiento.setReadonly(true);
				tbxNombre_procedimiento.setReadonly(true);
				tbxUnidades.setReadonly(true);
				tbxValor_procedimiento.setReadonly(true);
				img.setVisible(false);
			}

			if (opcion_formulario != null
					&& opcion_formulario.equals(OpcionesFormulario.MOSTRAR
							.toString())) {
				//log.info("opcion_formulario ===> " + opcion_formulario);
				tbxCodigo_procedimiento.setReadonly(true);
				tbxNombre_procedimiento.setReadonly(true);
				tbxUnidades.setReadonly(true);
				tbxValor_procedimiento.setReadonly(true);
				img.setVisible(false);
			}

		}

		if (!sw) {
			((Button) this.getFellow("_btGuardarOrden")).setDisabled(false);
			((Button) this.getFellow("_btGuardarOrden"))
					.setImage("/images/Save16.gif");

			((Button) this.getFellow("_btEliminarOrden")).setDisabled(false);
			((Button) this.getFellow("_btEliminarOrden"))
					.setImage("/images/eliminar.gif");

			((Button) this.getFellow("_btAdicionarOrden")).setDisabled(false);
		} else {
			((Button) this.getFellow("_btGuardarOrden")).setDisabled(true);
			((Button) this.getFellow("_btGuardarOrden"))
					.setImage("/images/Save16_disabled.gif");

			((Button) this.getFellow("_btEliminarOrden")).setDisabled(true);
			((Button) this.getFellow("_btEliminarOrden"))
					.setImage("/images/eliminar_gris.gif");

			((Button) this.getFellow("_btAdicionarOrden")).setDisabled(true);
		}

		if (rol_usuario.equals("05")) {
			_tbxCodigo_ordenador.setDisabled(true);
		} else {
			_tbxCodigo_ordenador.setDisabled(sw);
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
			_tbxCodigo_ordenador.setValue("");
			_tbxNomOrdenador.setValue("");
		} else {
			Prestadores dato = (Prestadores) listitem.getValue();
			_tbxCodigo_ordenador.setValue(dato.getNro_identificacion());
			_tbxNomOrdenador.setValue(dato.getNombres() + " "
					+ dato.getApellidos());
		}
		_tbxCodigo_ordenador.close();
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
			_tbxId_prestador.setValue("");
			_tbxNomPrestadorOrden.setValue("");
		} else {
			Administradora dato = (Administradora) listitem.getValue();
			_tbxId_prestador.setValue(dato.getCodigo());
			_tbxNomPrestadorOrden.setValue(dato.getNombre());
		}
		_tbxId_prestador.close();
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
			_tbxCodigo_dx.setValue("");
			_tbxNomOrdenDx.setValue("");
			_tbxSexo_dx.setValue("A");
			_tbxLimite_inferior_dx.setValue("0");
			_tbxLimite_superior_dx.setValue("599");
		} else {
			Cie dato = (Cie) listitem.getValue();
			_tbxCodigo_dx.setValue(dato.getCodigo());
			_tbxNomOrdenDx.setValue(dato.getNombre());
			_tbxSexo_dx.setValue(dato.getSexo());
			_tbxLimite_inferior_dx.setValue(dato.getLimite_inferior());
			_tbxLimite_superior_dx.setValue(dato.getLimite_superior());
		}
		_tbxCodigo_dx.close();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		_tbxCodigo_ordenador.setStyle("background-color:white");
		_tbxNomOrdenador.setStyle("background-color:white");

		_tbxId_prestador.setStyle("background-color:white");
		_tbxNomPrestadorOrden.setStyle("background-color:white");

		_tbxCodigo_dx.setStyle("background-color:white");
		_tbxNomOrdenDx.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (_tbxCodigo_ordenador.getText().trim().equals("")) {
			_tbxCodigo_ordenador.setStyle("background-color:#F6BBBE");
			_tbxNomOrdenador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (_tbxId_prestador.getText().trim().equals("")) {
			_tbxId_prestador.setStyle("background-color:#F6BBBE");
			_tbxNomPrestadorOrden.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (_tbxCodigo_dx.getText().trim().equals("")) {
			_tbxCodigo_dx.setStyle("background-color:#F6BBBE");
			_tbxNomOrdenDx.setStyle("background-color:#F6BBBE");
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

		_tbxCodigo_ordenador.setStyle("background-color:white");
		_tbxNomOrdenador.setStyle("background-color:white");

		_tbxId_prestador.setStyle("background-color:white");
		_tbxNomPrestadorOrden.setStyle("background-color:white");

		_tbxCodigo_dx.setStyle("background-color:white");
		_tbxNomOrdenDx.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (trabajar_sin_admision_atendida) {
			cargarPrestador();
			cargarDx();
			_tbxId_prestador.setValue(admision.getCodigo_administradora());
		}

		if (_tbxCodigo_ordenador.getText().trim().equals("")) {
			_tbxCodigo_ordenador.setStyle("background-color:#F6BBBE");
			_tbxNomOrdenador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (_tbxId_prestador.getText().trim().equals("")) {
			_tbxId_prestador.setStyle("background-color:#F6BBBE");
			_tbxNomPrestadorOrden.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (_tbxCodigo_dx.getText().trim().equals("")) {
			_tbxCodigo_dx.setStyle("background-color:#F6BBBE");
			_tbxNomOrdenDx.setStyle("background-color:#F6BBBE");
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
				String edad = Util.getEdad(_tbxFecha_nac.getValue(),
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
				String edad = Util.getEdad(_tbxFecha_nac.getValue(),
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
				if (!sex.equals(_tbxSexo_pct.getValue())) {
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

		String nombre_dx = _tbxNomOrdenDx.getValue();
		String limite_inferior = _tbxLimite_inferior_dx.getValue();
		String limite_superior = _tbxLimite_superior_dx.getValue();
		String sexo_dx = _tbxSexo_dx.getValue();

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
				String edad = Util.getEdad(_tbxFecha_nac.getValue(),
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
				String edad = Util.getEdad(_tbxFecha_nac.getValue(),
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
				if (!sex.equals(_tbxSexo_pct.getValue())) {
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
		if (orden_servicio == null)
			orden_servicio = new Orden_servicio();

		orden_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
		orden_servicio.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		orden_servicio
				.setCodigo_orden(orden_servicio.getCodigo_orden() != null ? orden_servicio
						.getCodigo_orden() : _tbxCodigo_orden.getValue());
		orden_servicio.setFecha_orden(new Timestamp(_dtbxFecha_orden.getValue()
				.getTime()));
		orden_servicio.setNro_ingreso(_tbxNro_ingreso.getValue());
		orden_servicio.setCodigo_paciente(admision.getNro_identificacion());
		orden_servicio.setCodigo_administradora(admision
				.getCodigo_administradora());
		orden_servicio.setId_plan(admision.getId_plan());
		orden_servicio.setCodigo_ordenador(_tbxCodigo_ordenador.getValue());
		orden_servicio.setCodigo_dx(_tbxCodigo_dx.getValue());
		orden_servicio.setId_prestador(usuarios.getCodigo());
		orden_servicio.setEstado("01");
		orden_servicio.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		orden_servicio.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		orden_servicio.setCreacion_user(usuarios.getCodigo());
		orden_servicio.setUltimo_user(usuarios.getCodigo());
		orden_servicio.setActualizado(true);
		orden_servicio.setTipo_hc(_tbxTipo_hc.getValue());

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
			String codigo_cups = (String) bean.get("codigo_cups");

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
			detalle.setCodigo_cups(codigo_cups);
			detalle.setRealizado("N");
			lista_detalle.add(detalle);
		}

		datos.put("orden_servicio", orden_servicio);
		datos.put("lista_detalle", lista_detalle);
		datos.put("accion", _tbxAccion.getText());
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
				orden_servicio.setCodigo_orden(_tbxCodigo_orden.getValue());
				orden_servicio.setFecha_orden(new Timestamp(_dtbxFecha_orden
						.getValue().getTime()));
				orden_servicio.setNro_ingreso(_tbxNro_ingreso.getValue());
				orden_servicio.setCodigo_paciente(_tbxCodigo_paciente
						.getValue());
				orden_servicio
						.setCodigo_administradora(_tbxCodigo_administradora
								.getValue());
				orden_servicio.setId_plan(_tbxId_plan.getValue());
				orden_servicio.setCodigo_ordenador(_tbxCodigo_ordenador
						.getValue());
				orden_servicio.setCodigo_dx(_tbxCodigo_dx.getValue());
				orden_servicio.setId_prestador(_tbxId_prestador.getValue());
				orden_servicio.setEstado("01");
				orden_servicio.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				orden_servicio.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				orden_servicio.setCreacion_user(usuarios.getCodigo());
				orden_servicio.setUltimo_user(usuarios.getCodigo());
				orden_servicio.setActualizado(true);
				orden_servicio.setTipo_hc(_tbxTipo_hc.getValue());

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
					// String nombre_procedimiento =
					// (String)bean.get("nombre_procedimiento");

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
				datos.put("accion", _tbxAccion.getText());

				orden_servicio = getServiceLocator().getOrden_servicioService()
						.guardar(datos);
				if (_tbxAccion.getText().equalsIgnoreCase("registrar")) {
					_tbxAccion.setText("modificar");
				}
				mostrarDatos(orden_servicio);

				final Long codigo_orden_id = orden_servicio.getId(); 

				Messagebox
						.show("Los datos se guardaron satisfactoriamente. Desea imprimir el documento? ",
								"impresion", Messagebox.YES + Messagebox.NO,
								Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											// do the thing
											imprimir(codigo_orden_id);
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
		Orden_servicio orden_servicio = (Orden_servicio) obj;
		try {
			_tbxCodigo_orden.setValue(orden_servicio.getCodigo_orden());
			_tbxCodigo_paciente.setValue(orden_servicio.getCodigo_paciente());
			_tbxCodigo_administradora.setValue(orden_servicio
					.getCodigo_administradora());
			_tbxId_plan.setValue(orden_servicio.getId_plan());
			_tbxNro_ingreso.setValue(orden_servicio.getNro_ingreso());

			_dtbxFecha_orden.setValue(orden_servicio.getFecha_orden());

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(orden_servicio.getCodigo_empresa());
			prestadores.setCodigo_sucursal(orden_servicio.getCodigo_sucursal());
			prestadores.setNro_identificacion(orden_servicio
					.getCodigo_ordenador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			_tbxCodigo_ordenador.setValue(orden_servicio.getCodigo_ordenador());
			_tbxNomOrdenador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));

			Cie cie = new Cie();
			cie.setCodigo(orden_servicio.getCodigo_dx());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
			_tbxCodigo_dx.setValue(orden_servicio.getCodigo_dx());
			_tbxNomOrdenDx.setValue((cie != null ? cie.getNombre() : ""));
			_tbxSexo_dx.setValue((cie != null ? cie.getSexo() : "A"));
			_tbxLimite_inferior_dx.setValue((cie != null ? cie
					.getLimite_inferior() : "0"));
			_tbxLimite_superior_dx.setValue((cie != null ? cie
					.getLimite_superior() : "599"));

			Administradora admin = new Administradora();
			admin.setCodigo(orden_servicio.getId_prestador());
			admin = getServiceLocator().getAdministradoraService().consultar(
					admin);
			_tbxId_prestador.setValue(orden_servicio.getId_prestador());
			_tbxNomPrestadorOrden.setValue((admin != null ? admin.getNombre()
					: ""));

			lista_datos.clear();
			_listboxOrdenes_servicio.getItems().clear();
			List<Detalle_orden> lista_detalle = orden_servicio
					.getLista_detalle();
			for (Detalle_orden detalle : lista_detalle) {
				Map bean = llenarBeanDetalle(detalle);
				//log.info("bean-nombre_procedimiento ===> "
						//+ bean.get("nombre_procedimiento"));
				// bean.put("nombre_procedimiento", nombre_procedimiento);
				lista_datos.add(bean);
			}

			crearFilas();

			// Mostramos la vista //
			_tbxAccion.setText("modificar");
			accionForm(true, _tbxAccion.getText());
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void cargarProcedimientoRealizados(Odontologia odontologia) {
		try {
			//log.info("Cargar Datos odonotologia");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", odontologia.getCodigo_empresa());
			map.put("codigo_sucursal", odontologia.getCodigo_sucursal());
			map.put("nro_identificacion", odontologia.getIdentificacion());
			map.put("nro_ingreso", odontologia.getNro_ingreso());
			map.put("realizado_en",
					IDatosProcedimientos.REALIZADO_EN_ODONOTOLOGIA);
			//log.info("Consultar Odontologia con: " + map);
			List<Datos_procedimiento> datos_procedimientos = getServiceLocator()
					.getServicio(Datos_procedimientoService.class).listarTabla(map);
			//log.info("Consultar Odontologia con: "
					///+ datos_procedimientos.size());
			for (Datos_procedimiento datos_procedimiento : datos_procedimientos) {
				lista_datos
						.add(llenarBeanDetalle(convertirHaDetalle(datos_procedimiento)));
			}
			crearFilas();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("No se pueden cargar los serivicios ordenados",
					"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}

	private Detalle_orden convertirHaDetalle(
			Datos_procedimiento datos_procedimiento) {
		Detalle_orden detalle_orden = new Detalle_orden();
		detalle_orden.setCodigo_procedimiento(datos_procedimiento
				.getCodigo_procedimiento());
		detalle_orden.setValor_procedimiento(datos_procedimiento
				.getValor_procedimiento());
		detalle_orden
				.setUnidades(datos_procedimiento.getUnidades() != null ? datos_procedimiento
						.getUnidades() : 1);
		detalle_orden.setDescuento(datos_procedimiento.getDescuento());
		detalle_orden.setIncremento(datos_procedimiento.getIncremento());
		detalle_orden.setValor_real(datos_procedimiento.getValor_real());
		return detalle_orden;
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

	public void imprimir(Long id) throws Exception {
		if (id == null) {
			Messagebox.show("La orden no se ha guardado aún", "Informacion ..",
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Orden_servicio");
		paramRequest.put("id", id);
		paramRequest.put("formato", _lbxFormatoOrden.getSelectedItem()
				.getValue().toString());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", null, paramRequest);
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

//		Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
//				.getManuales_tarifarios(admision);

		// Contratos contratos = new Contratos();
		// contratos.setCodigo_empresa(detalle.getCodigo_empresa());
		// contratos.setCodigo_sucursal(detalle.getCodigo_sucursal());
		// contratos.setCodigo_administradora(admision.getCodigo_administradora());
		// contratos.setId_plan(admision.getId_plan());
		// contratos =

		Procedimientos proc = new Procedimientos();
		proc.setId_procedimiento(new Long(detalle.getCodigo_procedimiento()));
		// proc.setCodigo_cups(detalle.getCodigo_procedimiento());
		proc = getServiceLocator().getProcedimientosService().consultar(proc);
		nombre_procedimiento = (proc != null ? proc.getDescripcion() : "");
		sexo_pcd = (proc != null ? proc.getSexo() : "");
		limite_inferior_pcd = (proc != null ? proc.getLimite_inferior() : "");
		limite_superior_pcd = (proc != null ? proc.getLimite_superior() : "");

		//log.info("nombre_procedimiento ===> " + nombre_procedimiento);

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

		return bean;
	}

	public void openPcd() throws Exception {

		Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
				.getManuales_tarifarios(admision);

		String pages = "";
		// Contratos contratos = new Contratos();
		// contratos.setCodigo_empresa(empresa.getCodigo_empresa());
		// contratos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		// contratos.setCodigo_administradora(admision.getCodigo_administradora());
		// contratos.setId_plan(admision.getId_plan());
		// contratos =
		// getServiceLocator().getContratosService().consultar(contratos);

		String anio = (manuales_tarifarios != null ? (manuales_tarifarios
				.getAnio() != null ? manuales_tarifarios.getAnio() : "") : "");

		pages = "/pages/openProcedimientos.zul";

		Map parametros = new HashMap();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				_tbxCodigo_administradora.getValue());
		parametros.put("id_plan", _tbxId_plan.getValue());
		parametros.put("restringido", "");
		parametros.put("nro_ingreso", _tbxNro_ingreso.getValue());
		parametros.put("nro_identificacion", _tbxCodigo_paciente.getValue());
		// parametros.put("estrato", "");
		// parametros.put("consulta", "");
		// parametros.put("quirurgico", "");
		parametros.put("anio", anio);
		parametros.put("seleccionados", seleccionados);

		//log.info("Paginas: " + pages);

		Component componente = Executions.createComponents(pages, null,
				parametros);
		final Window ventana = (Window) componente;
		if (ventana instanceof OpenProcedimientosAction) {
			((OpenProcedimientosAction) ventana)
					.setSeleccionar_componente(this);
		}
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
		ventana.setMode("modal");
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
		_listboxOrdenes_servicio.getItems().clear();
		for (int j = 0; j < lista_datos.size(); j++) {
			Map bean = lista_datos.get(j);
			crearFilaOrden(bean, j);
		}
	}

	public void crearFilaOrden(Map bean, int j) throws Exception {
		final int index = j;

		final String codigo_procedimiento = (String) bean
				.get("codigo_procedimiento");
		String nombre_procedimiento = (String) bean.get("nombre_procedimiento");
		double valor_procedimiento = (Double) bean.get("valor_procedimiento");
		double unidades = (Double) bean.get("unidades");
		double descuento = (Double) bean.get("descuento");
		double incremento = (Double) bean.get("incremento");
		double valor_real = (Double) bean.get("valor_real");
		String sexo_pcd = (String) bean.get("sexo_pcd");
		String limite_inferior_pcd = (String) bean.get("limite_inferior_pcd");
		String limite_superior_pcd = (String) bean.get("limite_superior_pcd");

		final Listitem fila = new Listitem();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo //
		Listcell cell = new Listcell();

		final Textbox tbxCodigo_procedimiento = new Textbox(
				codigo_procedimiento);
		// tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("_codigo_procedimiento_" + j);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setReadonly(true);
		cell.appendChild(tbxCodigo_procedimiento);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Listcell();

		final Textbox tbxNombre_procedimiento = new Textbox(
				nombre_procedimiento);
		// tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("_nombre_procedimiento_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna unidades //
		cell = new Listcell();

		final Doublebox tbxUnidades = new Doublebox(unidades);
		// tbxUnidades.setInplace(true);
		tbxUnidades.setId("_unidades_" + j);
		tbxUnidades.setFormat("#,##0.00");
		tbxUnidades.setHflex("1");
		tbxUnidades.setReadonly(false);
		cell.appendChild(tbxUnidades);
		fila.appendChild(cell);

		// Columna valor pcd //
		cell = new Listcell();

		final Doublebox tbxValor_procedimiento = new Doublebox(
				valor_procedimiento);
		// tbxValor_procedimiento.setInplace(true);
		tbxValor_procedimiento.setId("_valor_procedimiento_" + j);
		tbxValor_procedimiento.setFormat("#,##0.00");
		tbxValor_procedimiento.setHflex("1");
		tbxValor_procedimiento.setReadonly(true);
		cell.appendChild(tbxValor_procedimiento);

		final Doublebox tbxDescuento = new Doublebox(descuento);
		tbxDescuento.setId("_descuento_" + j);
		tbxDescuento.setVisible(false);
		cell.appendChild(tbxDescuento);

		final Doublebox tbxIncremento = new Doublebox(incremento);
		tbxIncremento.setId("_incremento_" + j);
		tbxIncremento.setVisible(false);
		cell.appendChild(tbxIncremento);

		final Doublebox tbxValor_real = new Doublebox(valor_real);
		tbxValor_real.setId("_valor_real_" + j);
		tbxValor_real.setVisible(false);
		cell.appendChild(tbxValor_real);

		final Textbox tbxSexo_pcd = new Textbox(sexo_pcd);
		tbxSexo_pcd.setId("_sexo_pcd_" + j);
		tbxSexo_pcd.setVisible(false);
		cell.appendChild(tbxSexo_pcd);

		final Textbox tbxLimite_inferior_pcd = new Textbox(limite_inferior_pcd);
		tbxLimite_inferior_pcd.setId("_limite_inferior_pcd_" + j);
		tbxLimite_inferior_pcd.setVisible(false);
		cell.appendChild(tbxLimite_inferior_pcd);

		final Textbox tbxLimite_superior_pcd = new Textbox(limite_superior_pcd);
		tbxLimite_superior_pcd.setId("_limite_superior_pcd_" + j);
		tbxLimite_superior_pcd.setVisible(false);
		cell.appendChild(tbxLimite_superior_pcd);

		fila.appendChild(cell);

		// Columna borrar //
		cell = new Listcell();

		Image img = new Image("/images/borrar.gif");
		img.setId("_img_" + j);
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
									seleccionados.remove(codigo_procedimiento);
									actualizarPagina();
									lista_datos.remove(index);
									crearFilas();
								}
							}
						});
			}
		});

		fila.setId("_fila_" + j);

		_listboxOrdenes_servicio.appendChild(fila);

		_listboxOrdenes_servicio.setVisible(true);
		_listboxOrdenes_servicio.setMold("paging");
		_listboxOrdenes_servicio.setPageSize(20);
		_listboxOrdenes_servicio.applyProperties();
		_listboxOrdenes_servicio.invalidate();

	}

	public void actualizarPagina() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);

			Listitem fila = (Listitem) _listboxOrdenes_servicio
					.getFellow("_fila_" + i);
			Doublebox tbxUnidades = (Doublebox) fila
					.getFellow("_unidades_" + i);
			bean.put("unidades", tbxUnidades.getValue());
			lista_datos.set(i, bean);
		}
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

	public ZKWindow getZkWindow() {
		return zkWindow;
	}

	public void setZkWindow(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
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
		detalle_orden.setRealizado("N");
		detalle_orden.setCodigo_cups("" + (pcd.get("codigo_cups")));

		adicionarOrden(detalle_orden, (String) pcd.get("nombre_procedimiento"));
		seleccionados.add(detalle_orden.getCodigo_procedimiento());
	}

	public Toolbarbutton obtenerBotonAgregar() {
		return _btAdicionar2Orden;
	}

}
