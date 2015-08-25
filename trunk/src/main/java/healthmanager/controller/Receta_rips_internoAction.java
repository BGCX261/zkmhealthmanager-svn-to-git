package healthmanager.controller;

import healthmanager.controller.ZKWindow.OpcionesFormulario;
import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Detalle_solicitud_tecnico;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.bean.Solicitud_tecnico;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Roles_usuariosService;

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
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IAccionEvento;
import com.framework.interfaces.IRelacionSeleccion;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.CargardorDeDatos;
import com.framework.res.Res;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.service.ArticuloService;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * De los mismos creadores de flamenco.
 * 
 * @author ferney
 * @author Luis Miguel
 * @author Dario P.
 */

public class Receta_rips_internoAction extends Listbox implements AfterCompose,
		ISeleccionarComponente {

	private static Logger log = Logger
			.getLogger(Receta_rips_internoAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private Sucursal sucursal;
	private Usuarios usuarios;

	// Componentes //
	private Listbox lbxFormato;

	private Textbox tbxAccion;
	private Textbox tbxCodigo_receta;
	private Textbox tbxNro_identificacion;
	private Textbox tbxCodigo_administradora;
	private Textbox tbxId_plan;
	private Textbox tbxNro_ingreso;
	private Textbox tbxTipo_hc;

	private Datebox dtbxFecha;
	private Bandbox tbxCodigo_prestador;
	private Textbox tbxNomPrestador;

	private Listbox formReceta;

	private Listheader colValor_unitario;
	private Listheader colValor_total;
	private Listheader colEstado_cobro;
	@View
	private Listheader colDosis;
	@View
	private Listheader colPeriodo;
	@View
	private Listheader colCantidad;

	@View
	private Listheader colEstado_entregado;

	private List<Map> lista_datos;

	private Parametros_empresa parametros_empresa_global;
	private boolean noPrint;
	private Admision admision;

	@View
	private Button btEliminarReceta;

	@View
	private Button btGuardarReceta;

	@View
	private Textbox tbxObservaciones;
	private String tipo_hc;
	private Receta_rips receta_ripsEnUso;

	@View
	private Div dvContenedornoPametrizado;
	@View
	private Div divContenedorPametrizado;
	@View
	private Listheader listheaderEstado;

	@View
	private Listfooter listfooterEstado;

	@View
	private Listfoot footIndicacionRecomendaciones;
	private boolean trabajar_sin_admision_atendida;

	private String opcion_formulario;

	private ZKWindow zkWindow;

	private Map<String, Object> parametros;

	@View
	private Toolbarbutton btAdicionar2;

	@View
	private Toolbarbutton btImprimir2;
	@View
	private Toolbarbutton btSolicitud;

	private List<String> seleccionados = new ArrayList<String>();

	private Doublebox tbxCantidad_recetada_aux;
	private Textbox tbxPosologia_aux;
	private Intbox tiempotto_aux;
	private Listbox via_administracion_receta_aux;

	private Solicitud_tecnico solicitud_tecnico;
	private Double valor_inicial = 0.0;

	private Impresion_diagnostica impresion;

	@Override
	public void afterCompose() {
		try {
			cargarDatosSesion();
			parametros = (Map<String, Object>) Executions.getCurrent().getArg();
			CargardorDeDatos.initComponents(this);
			lista_datos = new LinkedList<Map>();
			incializarEventos();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void incializarEventos() {
		btSolicitud.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						actualizarPagina();
						final List<Detalle_receta_rips> lista_detalle = contieneMedicamentosNoAutorizados();
						if (!lista_detalle.isEmpty()
								&& solicitud_tecnico != null) {
							abrirSolicitud(lista_detalle, solicitud_tecnico,
									false);
						} else {
							MensajesUtil
									.mensajeAlerta(
											"Advertencia",
											"No se puede cargar la solicitud por no tiene medicamentos o no se a creado la solicitud");
						}
					}
				});
	}

	public void inicializar(ZKWindow zkWindow) throws Exception {
		setZkWindow(zkWindow);
		loadComponents();
		cargarDatosSesion();
		initReceta_rips();
		verificamosTipeImpresion();
		validarParaImpresion();
	}

	private void verificamosTipeImpresion() {
		Parametros_empresa parametrosEmpresa = new Parametros_empresa();
		parametrosEmpresa.setCodigo_empresa(admision.getCodigo_empresa());
		parametros_empresa_global = zkWindow.getParametros_empresa();
		if (parametros_empresa_global != null) {
			if (parametros_empresa_global.getPrint_receta_consulta_ext()
					.equals("N")) {
				((Button) getFellow("btImprimir")).setVisible(false);
				noPrint = true;
			}
		}
	}

	public void loadComponents() {
		formReceta = (Listbox) this.getFellow("formReceta");

		lbxFormato = (Listbox) this.getFellow("lbxFormatoReceta");
		tbxCodigo_receta = (Textbox) this.getFellow("tbxCodigo_receta");
		tbxAccion = (Textbox) this.getFellow("tbxAccionReceta");
		tbxNro_identificacion = (Textbox) this
				.getFellow("tbxNro_identificacionReceta");
		tbxCodigo_administradora = (Textbox) this
				.getFellow("tbxCodigo_administradora");
		tbxId_plan = (Textbox) this.getFellow("tbxId_plan");
		tbxNro_ingreso = (Textbox) this.getFellow("tbxNro_ingresoReceta");
		tbxTipo_hc = (Textbox) this.getFellow("tbxTipo_hcReceta");

		dtbxFecha = (Datebox) this.getFellow("dtbxFecha");
		tbxCodigo_prestador = (Bandbox) this.getFellow("tbxCodigo_prestador");
		tbxNomPrestador = (Textbox) this.getFellow("tbxNomPrestadorReceta");

		colValor_unitario = (Listheader) this.getFellow("colValor_unitario");
		colValor_total = (Listheader) this.getFellow("colValor_total");
		colEstado_cobro = (Listheader) this.getFellow("colEstado_cobro");
		footIndicacionRecomendaciones = (Listfoot) this
				.getFellow("footIndicacionRecomendaciones");
	}

	public void initReceta_rips() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		try {
			opcion_formulario = (String) parametros.get("opcion_formulario");
			admision = (Admision) parametros.get("admision");
			tipo_hc = (String) parametros.get("tipo_hc");
			String id_m = request.getParameter("id_menu");
			if (id_m != null) {
				// crearPermisos(new Integer(id_m));
			}

			if (parametros.get("ocultarInformacion") != null) {
				dvContenedornoPametrizado.setVisible(false);
				divContenedorPametrizado.setVisible(true);
				trabajar_sin_admision_atendida = true;
			}

			if (parametros.get("ocultarRecomendacion") != null) {
				footIndicacionRecomendaciones.setVisible(false);
			} else {
				footIndicacionRecomendaciones.setVisible(true);
			}

			// Map parametros = Executions.getCurrent().getArg();
			String codigo_receta = (String) parametros.get("codigo_receta");
			String nro_identificacion = admision.getNro_identificacion();
			String nro_ingreso = (String) parametros.get("nro_ingreso");
			String estado = admision.getEstado();
			// String codigo_administradora = admision.getCodigo_empresa();
			String id_plan = admision.getId_plan();

			tbxNro_identificacion.setValue(admision.getNro_identificacion());
			tbxNro_ingreso.setValue(admision.getNro_ingreso());
			tbxCodigo_administradora.setValue(admision
					.getCodigo_administradora());
			tbxId_plan.setValue(id_plan);
			tbxTipo_hc.setValue(tipo_hc);

			// admision = new Admision; parametros.get("admision");
			if (nro_identificacion.equals("") && nro_ingreso.equals("")) {
				accionForm(true, "registrar");
				deshabilitarCampos(true);
			} else {
				boolean estado_receta = false;
				receta_ripsEnUso = new Receta_rips();
				if (codigo_receta != null)
					receta_ripsEnUso.setCodigo_receta(codigo_receta);
				receta_ripsEnUso
						.setCodigo_empresa(admision.getCodigo_empresa());
				receta_ripsEnUso.setCodigo_sucursal(admision
						.getCodigo_sucursal());
				receta_ripsEnUso.setNro_identificacion(admision
						.getNro_identificacion());
				receta_ripsEnUso.setNro_ingreso(nro_ingreso);
				receta_ripsEnUso.setTipo_hc(tipo_hc);
				receta_ripsEnUso = zkWindow.getServiceLocator()
						.getReceta_ripsService().consultar(receta_ripsEnUso);
				// //log.info("historia: "+historia);
				boolean existReceta = false;
				if (receta_ripsEnUso != null) {
					estado_receta = receta_ripsEnUso.getEstado();
					mostrarDatos(receta_ripsEnUso, true);
					existReceta = true;
					btEliminarReceta.setVisible(false);
				} else {
					accionForm(true, "registrar");
				}

				if ((!admision.getVia_ingreso().equals(
						IVias_ingreso.ODONTOLOGIA2) && !admision
						.getVia_ingreso().equals(IVias_ingreso.SALUD_ORAL))
						&& (estado.equals("2") || estado_receta)) {
					deshabilitarCampos(true);
				} else {
					deshabilitarCampos(false);
				}

				if (existReceta)
					((Button) getFellow("btGuardarReceta")).setDisabled(true);
			}

			if (parametros.get("ocultaValor") != null) {
				colValor_unitario.setVisible(false);
				colValor_total.setVisible(false);
				if (sucursal.getTipo().equals(
						IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
					// listheaderEstado.setVisible(true);
					// colEstado_cobro.setVisible(true);
					listfooterEstado.setVisible(false);
				} else {
					listheaderEstado.setVisible(false);
					colEstado_cobro.setVisible(false);
				}
			}

			if (parametros.get("valorInicial") != null) {
				valor_inicial = (Double) parametros.get("valorInicial");
			}

			if (parametros.get("mostrarEstado") != null) {
				colEstado_entregado.setVisible(true);
			} else {
				colEstado_entregado.setVisible(false);
			}

			if (parametros.get("mostrarDosis") != null) {
				colDosis.setVisible(true);
			}
			if (parametros.get("mostrarDuracion") != null) {
				colPeriodo.setVisible(true);
			}
			if (parametros.get("mostrarCantidad") != null) {
				colCantidad.setVisible(true);
			}
			if (parametros.get("ocultarDosis") != null) {
				colDosis.setVisible(false);
			}
			if (parametros.get("ocultarDuracion") != null) {
				colPeriodo.setVisible(false);
			}
			if (parametros.get("ocultarCantidad") != null) {
				colCantidad.setVisible(false);
			}
			if (parametros.get("mensajeBotonImprimir") != null) {
				btImprimir2.setLabel((String) parametros
						.get("mensajeBotonImprimir"));
			}

		} catch (Exception e) {

			MensajesUtil.mensajeError(e, "", zkWindow);
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

	private void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}

	private boolean esPrestador() {
		Integer c_prestador = 0;
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", zkWindow.codigo_empresa);
		parametros.put("codigo_sucursal", zkWindow.codigo_sucursal);
		parametros.put("codigo_usuario", usuarios.getCodigo());

		List<Roles_usuarios> listado_roles = zkWindow.getServiceLocator()
				.getServicio(Roles_usuariosService.class).listar(parametros);

		for (Roles_usuarios roles_usuarios : listado_roles) {
			if (Utilidades.isMedico(roles_usuarios.getRol())
					|| Utilidades.isEnfermeraJefe(roles_usuarios.getRol())) {
				c_prestador++;
				break;
			}
		}

		return (c_prestador > 0);
	}

	private void cargarPrestador() throws Exception {
		if (esPrestador()) {
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(admision.getCodigo_empresa());
			prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
			prestadores.setNro_identificacion(admision.getCodigo_medico());
			prestadores = zkWindow.getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			if (prestadores == null) {
				throw new Exception(
						"Usuario no esta creado en el modulo de prestadore, actualize informacion de usuario");
			}
			tbxCodigo_prestador.setValue(prestadores.getNro_identificacion());
			tbxNomPrestador.setValue(prestadores.getNombres() + " "
					+ prestadores.getApellidos());

		} else {
			if (admision != null) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(admision.getCodigo_empresa());
				prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
				prestadores.setNro_identificacion(admision.getCodigo_medico());
				prestadores = zkWindow.getServiceLocator()
						.getPrestadoresService().consultar(prestadores);

				tbxCodigo_prestador.setValue((prestadores != null ? prestadores
						.getNro_identificacion()
						: IConstantes.CODIGO_MEDICO_DEFECTO));
				tbxNomPrestador.setValue((prestadores != null ? prestadores
						.getNombres() + " " + prestadores.getApellidos()
						: "MEDICO POR DEFECTO"));

			} else {
				tbxCodigo_prestador.setValue(IConstantes.CODIGO_MEDICO_DEFECTO);
				tbxNomPrestador.setValue("MEDICO POR DEFECTO");
			}

		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		if (accion.equalsIgnoreCase("registrar")) {
			limpiarDatos();
			// tbxObservaciones.setVisible(false);
			// gbxIndicaciones.setVisible(false);
		} else {
			// tbxObservaciones.setVisible(false);
			// gbxIndicaciones.setVisible(false);
		}

		tbxAccion.setValue(accion);
	}

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = formReceta.getFellows();
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
		Collection<Component> collection = formReceta.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxNro_identificacionReceta")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxNro_ingresoReceta")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxCodigo_administradora")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxId_plan")
						&& !((Textbox) abstractComponent).getId().equals(
								"tbxTipo_hcReceta")) {
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
		Collection<Component> collection = formReceta.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setReadonly(sw);
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
				((Doublebox) abstractComponent).setReadonly(sw);
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setReadonly(sw);
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setReadonly(sw);
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setDisabled(sw);
			}
			if (abstractComponent instanceof Radio) {
				((Radio) abstractComponent).setDisabled(sw);
			}

		}

		for (int i = 0; i < lista_datos.size(); i++) {
			Listitem fila = (Listitem) formReceta.getFellow("fila_receta_" + i);

			Textbox tbxCodigo_articulo = (Textbox) fila
					.getFellow("codigo_articulo_receta_" + i);
			Textbox tbxNombre_articulo = (Textbox) fila
					.getFellow("nombre_articulo_receta_" + i);
			Doublebox tbxCantidad_recetada = (Doublebox) fila
					.getFellow("cantidad_recetada_receta_" + i);
			Intbox intboxTiempo_tto = (Intbox) fila
					.getFellow("tiempo_tto_receta_" + i);
			Textbox tbxPosologia = (Textbox) fila.getFellow("posologia_receta_"
					+ i);
			Listbox via_administracion_receta = (Listbox) fila
					.getFellow("via_administracion_receta_" + i);
			Listbox LbxEstado_cobro = (Listbox) fila
					.getFellow("estado_cobro_receta_" + i);

			Image img = (Image) fila.getFellow("img_receta_" + i);

			if (!sw) {
				tbxCodigo_articulo.setReadonly(false);
				tbxNombre_articulo.setReadonly(false);
				tbxCantidad_recetada.setReadonly(false);
				tbxPosologia.setReadonly(false);
				img.setVisible(true);
				tbxObservaciones.setReadonly(false);
				via_administracion_receta.setDisabled(false);
			} else {
				tbxCodigo_articulo.setReadonly(true);
				tbxNombre_articulo.setReadonly(true);
				tbxCantidad_recetada.setReadonly(true);
				tbxPosologia.setReadonly(true);
				via_administracion_receta.setDisabled(true);
				img.setVisible(false);
				tbxObservaciones.setReadonly(true);
			}

			if (opcion_formulario != null
					&& opcion_formulario.equals(OpcionesFormulario.MOSTRAR
							.toString())) {
				// log.info("opcion_formulario ===> " + opcion_formulario);
				tbxCodigo_articulo.setReadonly(true);
				tbxNombre_articulo.setReadonly(true);
				tbxCantidad_recetada.setReadonly(true);
				tbxPosologia.setReadonly(true);
				LbxEstado_cobro.setDisabled(true);
				intboxTiempo_tto.setReadonly(true);
				img.setVisible(false);
				via_administracion_receta.setDisabled(true);
				tbxObservaciones.setReadonly(true);
			}
		}

		if (!sw) {
			((Button) formReceta.getFellow("btGuardarReceta"))
					.setDisabled(false);
			((Button) formReceta.getFellow("btGuardarReceta"))
					.setImage("/images/Save16.gif");

			((Button) formReceta.getFellow("btEliminarReceta"))
					.setDisabled(false);
			((Button) formReceta.getFellow("btEliminarReceta"))
					.setImage("/images/eliminar.gif");

			((Button) formReceta.getFellow("btAdicionar")).setDisabled(false);
		} else {
			((Button) formReceta.getFellow("btGuardarReceta"))
					.setDisabled(true);
			((Button) formReceta.getFellow("btGuardarReceta"))
					.setImage("/images/Save16_disabled.gif");

			((Button) formReceta.getFellow("btEliminarReceta"))
					.setDisabled(true);
			((Button) formReceta.getFellow("btEliminarReceta"))
					.setImage("/images/eliminar_gris.gif");

			((Button) formReceta.getFellow("btAdicionar")).setDisabled(true);
		}

	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", admision.getCodigo_empresa());
			parameters.put("codigo_sucursal", admision.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Prestadores> list = zkWindow.getServiceLocator()
					.getPrestadoresService().listar(parameters);

			lbx.getItems().clear();

			for (Prestadores dato : list) {

				Especialidad especialidad = new Especialidad();
				especialidad.setCodigo(dato.getCodigo_especialidad());
				especialidad = zkWindow.getServiceLocator()
						.getEspecialidadService().consultar(especialidad);

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

		tbxCodigo_prestador.setStyle("background-color:white");
		tbxNomPrestador.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (trabajar_sin_admision_atendida) {
			cargarPrestador();
		}

		if (tbxCodigo_prestador.getText().trim().equals("")) {
			tbxCodigo_prestador.setStyle("background-color:#F6BBBE");
			tbxNomPrestador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (valida && lista_datos.size() <= 0) {
			mensaje = "Debe crear al menos un registro de receta";
			valida = false;
		}

		if (!admision.getAtendida() && !trabajar_sin_admision_atendida) {
			valida = false;
			mensaje = "Para poder realizar esta accion primero debe realizar la historia";
		}
		if (sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)
				&& parametros_empresa_global.getTipo_solicitud_tecnica()
						.equals(IConstantes.SOLICITUD_TECNICA_CAJA_PREV)) {
			valida = validarSolicitud();
			mensaje = "Para poder realizar esta accion debe registrar una solitud para los medicamentos no autorizados";
		}

		if (valida) {
			actualizarPagina();

			for (int i = 0; i < lista_datos.size(); i++) {
				Map bean = lista_datos.get(i);

				String codigo_articulo = (String) bean.get("codigo_articulo");
				String nombre_articulo = (String) bean.get("nombre1");
				String posologia = (String) bean.get("posologia");

				if (((Double) bean.get("cantidad_recetada")) <= 0) {
					mensaje = "El valor de las cantidades recetadas en el medicamento "
							+ nombre_articulo
							+ " no puede ser menor ni igual a cero";
					valida = false;
					i = lista_datos.size();
				}

				if (valida && posologia.equals("")) {
					mensaje = "Debe digitar la posología en el medicamento "
							+ nombre_articulo + " ";
					valida = false;
					i = lista_datos.size();
				}

				if (valida) {
					for (int j = i + 1; j < lista_datos.size(); j++) {
						Map beanAux = lista_datos.get(j);
						if (codigo_articulo.equals((String) beanAux
								.get("codigo_articulo"))) {
							valida = false;
							mensaje = "El medicamento " + codigo_articulo
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

	private boolean validarSolicitud() throws Exception {
		final List<Detalle_receta_rips> lista_detalle = contieneMedicamentosNoAutorizados();
		if (!lista_detalle.isEmpty()) {
			if (solicitud_tecnico == null) {
				abrirSolicitud(lista_detalle, null, true);
				return false;
			} else if (Utilidades.verificarMedicamentoNoAutorizadosEnSolicitud(
					lista_detalle,
					solicitud_tecnico.getDetalleSolicitudTecnicos())) {
				abrirSolicitud(lista_detalle, solicitud_tecnico, true);
				return false;
			}
		}
		return true;
	}

	public boolean verificarMedicamentoNoAutorizadosEnSolicitud(
			List<Detalle_receta_rips> dtt_recetas,
			List<Detalle_solicitud_tecnico> dtt_solicituds) {
		for (Detalle_receta_rips dtt_receta : dtt_recetas) {
			boolean existe = false;
			for (Detalle_solicitud_tecnico dtt_solicitud : dtt_solicituds) {
				if (dtt_solicitud.getCodigo_medicamento().equals(
						dtt_receta.getCodigo_articulo())) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				return true;
			}
		}
		return false;
	}

	public boolean validarFormExterno() throws Exception {
		tbxCantidad_recetada_aux = null;
		tbxPosologia_aux = null;
		tiempotto_aux = null;
		via_administracion_receta_aux = null;

		tbxCodigo_prestador.setStyle("background-color:white");
		tbxNomPrestador.setStyle("background-color:white");

		boolean valida = true;
		boolean mostarMsj = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (trabajar_sin_admision_atendida) {
			cargarPrestador();
		}

		if (tbxCodigo_prestador.getText().trim().equals("")) {
			tbxCodigo_prestador.setStyle("background-color:#F6BBBE");
			tbxNomPrestador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		// if (valida && lista_datos.size() <= 0) {
		// mensaje = "Debe crear al menos un registro de receta";
		// valida = false;
		// }

		if (!admision.getAtendida() && !trabajar_sin_admision_atendida) {
			valida = false;
			mensaje = "Para poder realizar esta accion primero debe realizar la historia";
		}

		if (valida && !lista_datos.isEmpty()) {
			actualizarPagina();

			for (int i = 0; i < lista_datos.size(); i++) {
				Map bean = lista_datos.get(i);

				String codigo_articulo = (String) bean.get("codigo_articulo");
				String nombre_articulo = (String) bean.get("nombre1");
				String posologia = colDosis.isVisible() ? (String) bean
						.get("posologia") : "DOSIS UNICA";
				bean.put("posologia", posologia);

				double cantidad_recetada = bean.get("cantidad_recetada") != null ? (Double) bean
						.get("cantidad_recetada") : valor_inicial;
				bean.put("cantidad_recetada", cantidad_recetada);

				int tiempo_tto = colPeriodo.isVisible() ? (bean
						.get("tiempo_tto") != null ? (Integer) bean
						.get("tiempo_tto") : 0) : 1;
				bean.put("tiempo_tto", tiempo_tto);

				String via_administracion = (String) bean
						.get("via_administracion");

				if (cantidad_recetada <= 0) {
					mensaje = "El valor de las cantidades recetadas en el medicamento "
							+ nombre_articulo
							+ " no puede ser menor ni igual a cero";
					valida = false;
					tbxCantidad_recetada_aux = (Doublebox) this
							.getFellow("cantidad_recetada_receta_" + i);
					i = lista_datos.size();
				}

				if (valida && via_administracion != null
						&& via_administracion.isEmpty()) {
					mensaje = "Debe seleccionar una via de administracion de las recetadas en el medicamento "
							+ nombre_articulo;
					valida = false;
					via_administracion_receta_aux = (Listbox) this
							.getFellow("via_administracion_receta_" + i);
					i = lista_datos.size();
				}

				if (valida && tiempo_tto <= 0) {
					mensaje = "El valor del tiempo de tratamiento de las recetadas en el medicamento "
							+ nombre_articulo
							+ " no puede ser menor ni igual a cero";
					valida = false;
					tiempotto_aux = (Intbox) this
							.getFellow("tiempo_tto_receta_" + i);
					i = lista_datos.size();

				}

				if (valida && posologia.equals("")) {
					mensaje = "Debe digitar la posología en el medicamento "
							+ nombre_articulo + " ";
					valida = false;
					tbxPosologia_aux = (Textbox) this
							.getFellow("posologia_receta_" + i);
					i = lista_datos.size();

				}

				if (valida) {
					for (int j = i + 1; j < lista_datos.size(); j++) {
						Map beanAux = lista_datos.get(j);
						if (codigo_articulo.equals((String) beanAux
								.get("codigo_articulo"))) {
							valida = false;
							mensaje = "El medicamento " + codigo_articulo
									+ " se encuentra repetido";
							i = lista_datos.size();
							j = lista_datos.size();
						}
					}
				}
			}
		}

		// Esta validacion es para verificar si necesita una solicitud para el
		// medicamento
		if (valida
				&& sucursal.getTipo().equals(
						IConstantes.TIPOS_SUCURSAL_CAJA_PREV)
				&& parametros_empresa_global.getTipo_solicitud_tecnica()
						.equals(IConstantes.SOLICITUD_TECNICA_CAJA_PREV)
				&& !lista_datos.isEmpty()) {
			mostarMsj = valida = validarSolicitud();
			mensaje = "Para poder realizar esta accion debe registrar una solitud para los medicamentos no autorizados";
		}

		if (!valida && mostarMsj) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje, new EventListener<Event>() {

				@Override
				public void onEvent(Event arg0) throws Exception {
					if (tbxCantidad_recetada_aux != null)
						tbxCantidad_recetada_aux.setFocus(true);
					if (tiempotto_aux != null)
						tiempotto_aux.setFocus(true);
					if (tbxPosologia_aux != null)
						tbxPosologia_aux.setFocus(true);
					if (via_administracion_receta_aux != null)
						via_administracion_receta_aux.setFocus(true);
				}
			});
		}

		return valida;
	}

	@SuppressWarnings("unused")
	private boolean isThereNoAutorizados(List<Detalle_receta_rips> listaDetalle) {
		for (Detalle_receta_rips detalleRecetaRips : listaDetalle)
			if (!detalleRecetaRips.getAutorizado())
				return true;
		return false;
	}

	public Map<String, Object> obtenerDatos() {
		final Map datos = new HashMap();

		Receta_rips receta_rips = new Receta_rips();
		receta_rips.setCodigo_empresa(admision.getCodigo_empresa());
		receta_rips.setCodigo_sucursal(admision.getCodigo_sucursal());
		receta_rips.setCodigo_receta(tbxCodigo_receta.getValue());
		receta_rips.setNro_identificacion(admision.getNro_identificacion());
		receta_rips.setCodigo_administradora(admision
				.getCodigo_administradora());
		receta_rips.setId_plan(admision.getId_plan());
		receta_rips.setNro_ingreso(admision.getNro_ingreso());
		receta_rips.setFecha(new java.sql.Date(dtbxFecha.getValue().getTime()));
		receta_rips.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		receta_rips.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		receta_rips.setCreacion_user(usuarios.getCodigo());
		receta_rips.setUltimo_user(usuarios.getCodigo());
		receta_rips.setEstado(false);
		receta_rips.setTipo_hc(tipo_hc);
		receta_rips.setCodigo_prestador(tbxCodigo_prestador.getValue());
		receta_rips.setLeido(false);
		receta_rips.setLeido_auditor("N");
		receta_rips.setProcedencia("01");
		receta_rips.setCodigo_dx(impresion != null ? impresion
				.getCie_principal() : "Z000");
		receta_rips.setObservaciones("" + tbxObservaciones.getValue());
		receta_rips
				.setVigencia(parametros_empresa_global.getVigencia_recetas());

		receta_rips.setTipo_receta(IConstantes.TIPO_RECETA_AMBULATORIA);
		receta_rips.setAuditado_farmacia("N");

		boolean contiene_no_autorizados = false;
		final List<Detalle_receta_rips> lista_detalle = new LinkedList<Detalle_receta_rips>();

		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);

			String consecutivo = i + "";
			Detalle_receta_rips detalle = Utilidades.convertirMapDetalleReceta(
					bean, sucursal);
			detalle.setCodigo_empresa(receta_rips.getCodigo_empresa());
			detalle.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
			detalle.setConsecutivo(consecutivo + "");

			if (!detalle.getAutorizado()
					&& sucursal.getTipo().equals(
							IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
				contiene_no_autorizados = true;
			}

			lista_detalle.add(detalle);
		}

		receta_rips
				.setContiene_med_no_autorizados(contiene_no_autorizados ? "S"
						: "N");

		datos.put("receta_rips", receta_rips);
		datos.put("lista_detalle", lista_detalle);
		datos.put("lista_datos", lista_datos);
		datos.put("accion", tbxAccion.getText());
		datos.put("solicitud_tecnico", solicitud_tecnico);
		return datos;
	}

	/**
	 * Este metodo me permite saber si ahi procedimiento no autorizados.
	 * */
	private List<Detalle_receta_rips> contieneMedicamentosNoAutorizados() {
		List<Detalle_receta_rips> detalle_receta_rips = new ArrayList<Detalle_receta_rips>();
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);
			boolean autorizado = (Boolean) bean.get("autorizado");
			if (!autorizado) {
				detalle_receta_rips.add(Utilidades.convertirMapDetalleReceta(
						bean, sucursal));
			}
		}
		return detalle_receta_rips;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		Messagebox.show(
				"Esta seguro que desea guardar esta prescripcion medica? ",
				"Informacion", Messagebox.YES + Messagebox.NO,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event event) throws Exception {
						if ("onYes".equals(event.getName())) {
							try {
								// setUpperCase();
								if (validarForm()) {
									final Map datos = new HashMap();

									Receta_rips receta_rips = new Receta_rips();
									receta_rips.setCodigo_empresa(admision
											.getCodigo_empresa());
									receta_rips.setCodigo_sucursal(admision
											.getCodigo_sucursal());
									receta_rips
											.setCodigo_receta(tbxCodigo_receta
													.getValue());
									receta_rips
											.setNro_identificacion(tbxNro_identificacion
													.getValue());
									receta_rips
											.setCodigo_administradora(admision
													.getCodigo_empresa());
									receta_rips.setId_plan(admision
											.getId_plan());
									receta_rips.setNro_ingreso(admision
											.getNro_ingreso());
									receta_rips.setFecha(new java.sql.Date(
											dtbxFecha.getValue().getTime()));
									receta_rips.setCreacion_date(new Timestamp(
											new GregorianCalendar()
													.getTimeInMillis()));
									receta_rips.setUltimo_update(new Timestamp(
											new GregorianCalendar()
													.getTimeInMillis()));
									receta_rips.setCreacion_user(usuarios
											.getCodigo());
									receta_rips.setUltimo_user(usuarios
											.getCodigo());
									receta_rips.setEstado(false);
									receta_rips.setTipo_hc(tipo_hc);
									receta_rips
											.setCodigo_prestador(tbxCodigo_prestador
													.getValue());
									receta_rips.setLeido(false);
									receta_rips.setLeido_auditor("N");
									receta_rips.setProcedencia("01");
									receta_rips.setObservaciones(""
											+ tbxObservaciones.getValue());
									receta_rips
											.setVigencia(parametros_empresa_global
													.getVigencia_recetas());
									boolean haveNoAutorizados = false;
									final List<Detalle_receta_rips> lista_detalle = new LinkedList<Detalle_receta_rips>();
									for (int i = 0; i < lista_datos.size(); i++) {
										Map bean = lista_datos.get(i);

										String consecutivo = i + "";
										String codigo_articulo = (String) bean
												.get("codigo_articulo");
										double cantidad_recetada = (Double) bean
												.get("cantidad_recetada");
										double valor_unitario = (Double) bean
												.get("valor_unitario");
										double valor_total = (Double) bean
												.get("valor_total");
										String posologia = (String) bean
												.get("posologia");
										double descuento = (Double) bean
												.get("descuento");
										double incremento = (Double) bean
												.get("incremento");
										double valor_real = (Double) bean
												.get("valor_real");
										boolean autorizado = (Boolean) bean
												.get("autorizado");
										String estado_cobro = (String) bean
												.get("estado_cobro");
										int tiempo_tto = (Integer) bean
												.get("tiempo_tto");

										if (!autorizado
												&& sucursal.getTipo().equals(
														SucursalAction.IPS))
											haveNoAutorizados = true;

										Detalle_receta_rips detalle = new Detalle_receta_rips();
										detalle.setCodigo_empresa(receta_rips
												.getCodigo_empresa());
										detalle.setCodigo_sucursal(receta_rips
												.getCodigo_sucursal());
										detalle.setConsecutivo(consecutivo + "");
										detalle.setCodigo_articulo(codigo_articulo);
										detalle.setCantidad_recetada(cantidad_recetada);
										detalle.setCantidad_entregada(0.0);
										detalle.setValor_unitario(valor_unitario);
										detalle.setValor_total(valor_total);
										detalle.setValor_real(valor_real);
										detalle.setDescuento(descuento);
										detalle.setIncremento(incremento);
										detalle.setUnm(0);
										detalle.setPosologia(posologia);
										detalle.setEntregado("N");
										detalle.setAutorizado(parametros_empresa_global
												.getSolo_afiliados() ? autorizado
												: true);
										detalle.setEstado_cobro(estado_cobro);
										detalle.setTiempo_tto(tiempo_tto);

										lista_detalle.add(detalle);
									}

									receta_rips
											.setContiene_med_no_autorizados(haveNoAutorizados ? "S"
													: "N");

									datos.put("receta_rips", receta_rips);
									datos.put("lista_detalle", lista_detalle);
									datos.put("accion", tbxAccion.getText());

									if (haveNoAutorizados
											&& !haveSolicitud(receta_rips)
											&& parametros_empresa_global
													.getTipo_solicitud_tecnica()
													.equals("01")) {
										Messagebox
												.show("Hay medicamentos no autorizados. Debe generar una Solicitud",
														"Medicamentos no autorizados",
														Messagebox.YES
																+ Messagebox.NO,
														Messagebox.QUESTION,
														new org.zkoss.zk.ui.event.EventListener() {
															public void onEvent(
																	Event event)
																	throws Exception {
																if ("onYes"
																		.equals(event
																				.getName())) {
																	// abrirSolicitud(
																	// lista_detalle,
																	// datos);
																}
															}
														});
										return;
									} else {
										receta_rips = zkWindow
												.getServiceLocator()
												.getReceta_ripsService()
												.guardar(datos);

										if (tbxAccion.getText()
												.equalsIgnoreCase("registrar")) {
											tbxAccion.setText("modificar");
										}

										mostrarDatos(receta_rips, true);
										final String codigo_receta = receta_rips
												.getCodigo_receta();
										loadStateSave(codigo_receta);
									}
									/* tomamos la instancia de la ultima receta */
									receta_ripsEnUso = receta_rips;
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
								Messagebox.show(e.getMessage(),
										"Mensaje de Error !!", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	/**
	 * Este metodo actualiza el estado de las receta, para esta solicitud
	 * tecnica
	 * 
	 * @author Luis Miguel Hernandez
	 * */
	public void loadStateSave(final String codigo_receta) throws Exception {
		btGuardarReceta.setDisabled(true);
		btEliminarReceta.setDisabled(false);
		if (!noPrint)
			Messagebox
					.show("Los datos se guardaron satisfactoriamente. Desea imprimir el documento? ",
							"impresion", Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener() {
								public void onEvent(Event event)
										throws Exception {
									if ("onYes".equals(event.getName())) {
										imprimir(codigo_receta);
									}
								}
							});
	}

	private boolean haveSolicitud(Receta_rips recetaRips) {
		String codigo_receta = recetaRips.getCodigo_receta();
		if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
			codigo_receta = zkWindow
					.getServiceLocator()
					.getConsecutivoService()
					.crearConsecutivo(sucursal.getCodigo_empresa(),
							sucursal.getCodigo_sucursal(), "RECETA_RIPS");
		}
		Map param = new HashMap();
		param.put("codigo_empresa", admision.getCodigo_sucursal());
		param.put("codigo_sucursal", admision.getCodigo_empresa());
		param.put("codigo_receta", codigo_receta);
		return zkWindow.getServiceLocator().getSolicitudTecnicoService()
				.existe(param);
	}

	/**
	 * Este metodo me permite abrir una solicitud de medicamentos
	 * */
	private void abrirSolicitud(List<Detalle_receta_rips> detalleRecetaRips,
			Solicitud_tecnico solicitud_tecnico, boolean mostrar_msj)
			throws Exception {
		Map params = new HashMap();
		params.put(IConstantes.CONS_DETALLE_RECETA, detalleRecetaRips);
		params.put(IConstantes.CONS_SOLICITUD, solicitud_tecnico);
		params.put(IVias_ingreso.ADMISION_PACIENTE, admision);
		params.put(IConstantesAutorizaciones.CONS_SOLICITUD_TECNICA,
				solicitud_tecnico);
		if (mostrar_msj)
			params.put(
					IConstantes.CONS_MENSAJE,
					"Para realizar esta opcion debes realizar la solicitud para los medicamentos no autorizados");

		Solicitud_tecnicoAction componente = (Solicitud_tecnicoAction) Executions
				.createComponents("/pages/solicitud_tecnico.zul",
						this.getParent(), params);
		componente
				.setOnSeleccionar(new IRelacionSeleccion<Solicitud_tecnico>() {
					@Override
					public void onSeleccionar(
							Solicitud_tecnico solicitud_tecnico) {
						Receta_rips_internoAction.this.solicitud_tecnico = solicitud_tecnico;
						btSolicitud.setVisible(true);
						Clients.scrollIntoView(btSolicitud);
						Clients.showNotification("Para abrir solicitud",
								btSolicitud);
					}
				});
		final Window window = (Window) componente;
		window.setWidth("100%");
		window.setHeight("100%");
		window.doModal();
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj, boolean mostrar_observacion)
			throws Exception {
		Receta_rips receta_rips = (Receta_rips) obj;
		try {
			tbxCodigo_receta.setValue(receta_rips.getCodigo_receta());
			tbxNro_identificacion.setValue(receta_rips.getNro_identificacion());
			tbxCodigo_administradora.setValue(receta_rips
					.getCodigo_administradora());
			tbxId_plan.setValue(receta_rips.getId_plan());
			tbxNro_ingreso.setValue(receta_rips.getNro_ingreso());
			tbxTipo_hc.setValue(receta_rips.getTipo_hc());
			dtbxFecha.setValue(receta_rips.getFecha());

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(receta_rips.getCodigo_empresa());
			prestadores.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
			prestadores
					.setNro_identificacion(receta_rips.getCodigo_prestador());
			prestadores = zkWindow.getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			tbxCodigo_prestador.setValue(receta_rips.getCodigo_prestador());
			tbxNomPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));
			tbxObservaciones.setValue(Res.ifNull(
					receta_rips.getObservaciones(), String.class));
			lista_datos.clear();
			formReceta.getItems().clear();
			List<Detalle_receta_rips> lista_detalle = receta_rips
					.getLista_detalle();

			if (lista_detalle == null) {
				lista_detalle = new ArrayList<Detalle_receta_rips>();
			}

			for (Detalle_receta_rips detalle : lista_detalle) {
				Map bean = llenarBeanDetalle(detalle);
				lista_datos.add(bean);
			}

			if (mostrar_observacion) {
				if (!lista_detalle.isEmpty()) {
					footIndicacionRecomendaciones.setVisible(true);
				}
			}

			crearFilas();

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "Este dato no se puede editar",
					zkWindow);
		}
	}

	public void eliminarDatos(String codigo_receta) throws Exception {
		final String cod_rec = codigo_receta;
		if (codigo_receta.equals("")) {
			Messagebox.show("La receta medica no se ha guardado aún",
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
								Receta_rips receta_rips = new Receta_rips();
								receta_rips.setCodigo_empresa(admision
										.getCodigo_empresa());
								receta_rips.setCodigo_sucursal(admision
										.getCodigo_sucursal());
								receta_rips.setCodigo_receta(cod_rec);
								int result = zkWindow.getServiceLocator()
										.getReceta_ripsService()
										.eliminar(receta_rips);
								if (result == 0) {
									throw new Exception(
											"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
								}

								accionForm(true, "registrar");
								((Button) getFellow("btGuardarReceta"))
										.setDisabled(false);
								Messagebox
										.show("Informacion se eliminacion satisfactoriamente !!",
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

	public void imprimir(String codigo_receta) throws Exception {
		if (codigo_receta.equals("")) {
			Messagebox.show("La receta medica no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Receta_rips2");
		paramRequest.put("codigo_receta", codigo_receta);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());
		paramRequest.put("usuario_imprimio", usuarios.getNombres() + " "
				+ usuarios.getApellidos());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", zkWindow, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

	private Map llenarBeanDetalle(Detalle_receta_rips detalle) throws Exception {
		String codigo_articulo = detalle.getCodigo_articulo();
		String nombre_articulo = "";

		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(admision.getCodigo_empresa());
		articulo.setCodigo_sucursal(admision.getCodigo_sucursal());
		articulo.setCodigo_articulo(detalle.getCodigo_articulo());
		articulo = zkWindow.getServiceLocator().getArticuloService()
				.consultar(articulo);
		nombre_articulo = (articulo != null ? articulo.getNombre1() : "");

		double cantidad_recetada = detalle.getCantidad_recetada();
		double valor_unitario = detalle.getValor_unitario();
		double valor_total = detalle.getValor_total();
		double descuento = detalle.getDescuento();
		double incremento = detalle.getIncremento();
		double valor_real = detalle.getValor_real();
		String posologia = detalle.getPosologia();

		Map bean = new HashMap();
		bean.put("codigo_articulo", codigo_articulo);
		bean.put("nombre1", nombre_articulo);
		bean.put("cantidad_recetada", cantidad_recetada);
		bean.put("valor_unitario", valor_unitario);
		bean.put("valor_total", valor_total);
		bean.put("descuento", descuento);
		bean.put("incremento", incremento);
		bean.put("valor_real", valor_real);
		bean.put("posologia", posologia);
		bean.put("autorizado", detalle.getAutorizado());
		bean.put("estado_cobro", detalle.getEstado_cobro());
		bean.put("tiempo_tto", detalle.getTiempo_tto());
		bean.put("entregado", detalle.getEntregado());
		bean.put("cantidad_entregada", detalle.getCantidad_entregada());
		bean.put("via_administracion", detalle.getVia_administracion());
		bean.put("cantidad_maxima", articulo != null ? articulo.getCantidad_maxima() : null);
		return bean;
	}

	public void openArticulo() throws Exception {
		Map parametros = new HashMap();
		parametros.put("codigo_empresa", admision.getCodigo_empresa());
		parametros.put("codigo_sucursal", admision.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("grupo", "01");
		parametros.put("ocultaExist", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);
		parametros.put("seleccionados", seleccionados);

		Component componente = Executions.createComponents(
				"/pages/openArticulo.zul", null, parametros);
		final OpenArticuloAction ventana = (OpenArticuloAction) componente;
		ventana.setSeleccionar_componente(this);
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR MEDICAMENTOS ");
		ventana.setMode("modal");
	}

	public void adicionarReceta(Detalle_receta_rips detalle) throws Exception {
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
		formReceta.getItems().clear();
		List<Elemento> elementos = zkWindow.getServiceLocator()
				.getElementoService().listar("estado_pago");
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);
			crearFilaReceta(bean, i, elementos);
		}
//		formReceta.setVisible(true);
		formReceta.setMold("paging");
		formReceta.setPageSize(20);
		formReceta.applyProperties();
		formReceta.invalidate();
	}

	public void crearFilaReceta(final Map bean, int i, List<Elemento> elementos)
			throws Exception {
		// log.info("ejecutando metodo @crearFilaReceta() ==> Valor del indice = "
		// + i);
		final int index = i;

		final String codigo_articulo = (String) bean.get("codigo_articulo");
		final String nombre_articulo = (String) bean.get("nombre1");
		double cantidad_recetada = bean.get("cantidad_recetada") != null ? (Double) bean
				.get("cantidad_recetada") : 0.0;
		double valor_unitario = (Double) bean.get("valor_unitario");
		double valor_total = (Double) bean.get("valor_total");
		double descuento = (Double) bean.get("descuento");
		double incremento = (Double) bean.get("incremento");
		double valor_real = (Double) bean.get("valor_real");
		String posologia = (String) bean.get("posologia");
		String via_administracion = (String) bean.get("via_administracion");
		String estado_cobro = "01";
		int tiempo_tto = bean.get("tiempo_tto") != null ? (Integer) bean
				.get("tiempo_tto") : 0;
		Boolean autorizado = (Boolean) bean.get("autorizado");

		// String entregado = (String) bean.get("entregado");
		double cantidad_entregada = (Double) bean.get("cantidad_entregada");
		final Integer cantidad_maxima = (Integer) bean.get("cantidad_maxima");
		final String grupo1 = (String) bean.get("grupo1");

		String estado_cobro_temp = (String) bean.get("estado_cobro");
		if (estado_cobro_temp != null) {
			estado_cobro = (String) bean.get("estado_cobro");
		} else {
			bean.put("estado_cobro", estado_cobro);
		}

		final Listitem fila = new Listitem();
		fila.setStyle("text-align: center;nowrap:nowrap");
		Listcell cell = new Listcell();
		cell.setStyle(!autorizado ? IConstantesAutorizaciones.ESTILO_COLOR_NO_AUTORIZADO
				: null);
		fila.appendChild(cell);

		// Columna codigo //
		cell = new Listcell();
		final Textbox tbxCodigo_procedimiento = new Textbox(codigo_articulo);
		// tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("codigo_articulo_receta_" + i);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setReadonly(true);
		tbxCodigo_procedimiento.setInplace(true);
		cell.appendChild(tbxCodigo_procedimiento);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Listcell();

		final Textbox tbxNombre_procedimiento = new Textbox(
				nombre_articulo.trim());
		// tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_articulo_receta_" + i);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		tbxNombre_procedimiento.setInplace(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna via de administracion //
		cell = new Listcell();

		final Listbox lbxVia_administracion = new Listbox();
		// tbxUnidades.setInplace(true);
		lbxVia_administracion.setId("via_administracion_receta_" + i);
		lbxVia_administracion.setHflex("1");
		lbxVia_administracion.setMold("select");
		listarVia_administracion(lbxVia_administracion);
		Utilidades.seleccionarListitem(lbxVia_administracion,
				via_administracion);
		Res.cargarAutomatica(lbxVia_administracion, bean, "via_administracion",
				null);
		cell.appendChild(lbxVia_administracion);
		fila.appendChild(cell);

		// Columna posologia //
		cell = new Listcell();

		final Textbox tbxPosologia = new Textbox(posologia);
		// tbxCodigo_procedimiento.setInplace(true);
		tbxPosologia.setId("posologia_receta_" + i);
		Res.cargarAutomatica(tbxPosologia, bean, "posologia", null);
		tbxPosologia.setHflex("1");
		cell.appendChild(tbxPosologia);
		fila.appendChild(cell);

		// Columna Duracion //
		cell = new Listcell();

		final Intbox intboxTiempotto = new Intbox(tiempo_tto);
		if (tiempo_tto == 0)
			intboxTiempotto.setText("");
		intboxTiempotto.setId("tiempo_tto_receta_" + i);
		intboxTiempotto.setHflex("1");
		Res.cargarAutomatica(intboxTiempotto, bean, "tiempo_tto", null);
		cell.appendChild(intboxTiempotto);
		fila.appendChild(cell);

		// Columna cantidad recetada //
		cell = new Listcell();

		final Doublebox tbxCantidad_recetada = new Doublebox(cantidad_recetada);
		if (cantidad_recetada == 0.0)
			tbxCantidad_recetada.setText("");
		tbxCantidad_recetada.setId("cantidad_recetada_receta_" + i);
		tbxCantidad_recetada.setFormat("#0.##");
		tbxCantidad_recetada.setHflex("1");
		tbxCantidad_recetada.setReadonly(false);
		// tbxUnidades.setInplace(true);
		// para validar las cantidades
		if(cantidad_maxima != null && cantidad_maxima > 0){
			tbxCantidad_recetada.setMaxlength(cantidad_maxima.toString().length()); 
		}else{
			tbxCantidad_recetada.setMaxlength(3); 
		}

		// evento automatico
		Res.cargarAutomatica(tbxCantidad_recetada, bean, "cantidad_recetada",
				new IAccionEvento() {
					@Override
					public void accion() {
						if (cantidad_maxima != null
								&& cantidad_maxima > 0
								&& !FormularioUtil
										.onAccionValidarCantidadMaxima(
												cantidad_maxima,
												tbxCantidad_recetada,
												nombre_articulo, grupo1)) {
							bean.put("cantidad_recetada", tbxCantidad_recetada.getValue());
						}
					}
				});
		cell.appendChild(tbxCantidad_recetada);
		fila.appendChild(cell);

		// Columna valor unitario //
		cell = new Listcell();

		final Doublebox tbxValor_unitario = new Doublebox(valor_unitario);
		// tbxUnidades.setInplace(true);}
		Res.cargarAutomatica(tbxValor_unitario, bean, "valor_unitario", null);
		tbxValor_unitario.setId("valor_unitario_receta_" + i);
		tbxValor_unitario.setFormat("#,##0.00");
		tbxValor_unitario.setHflex("1");
		tbxValor_unitario.setReadonly(false);
		cell.appendChild(tbxValor_unitario);
		fila.appendChild(cell);

		// Columna valor total del detalle //
		cell = new Listcell();

		final Doublebox tbxValor_total = new Doublebox(valor_total);
		// tbxUnidades.setInplace(true);
		Res.cargarAutomatica(tbxValor_total, bean, "valor_total", null);
		tbxValor_total.setId("valor_total_receta_" + i);
		tbxValor_total.setFormat("#,##0.00");
		tbxValor_total.setHflex("1");
		tbxValor_total.setReadonly(false);
		cell.appendChild(tbxValor_total);
		fila.appendChild(cell);

		final Doublebox tbxDescuento = new Doublebox(descuento);
		Res.cargarAutomatica(tbxDescuento, bean, "descuento", null);
		tbxDescuento.setId("descuento_receta_" + i);
		tbxDescuento.setVisible(false);
		cell.appendChild(tbxDescuento);

		final Doublebox tbxIncremento = new Doublebox(incremento);
		Res.cargarAutomatica(tbxIncremento, bean, "incremento", null);
		tbxIncremento.setId("incremento_receta_" + i);
		tbxIncremento.setVisible(false);
		cell.appendChild(tbxIncremento);

		final Doublebox tbxValor_real = new Doublebox(valor_real);
		Res.cargarAutomatica(tbxValor_real, bean, "valor_real", null);
		tbxValor_real.setId("valor_real_receta_" + i);
		tbxValor_real.setVisible(false);
		cell.appendChild(tbxValor_real);

		fila.appendChild(cell);

		/* columna estado de cobro */
		cell = new Listcell();

		final Listbox lbxEstadoCobro = new Listbox();
		lbxEstadoCobro.setMold("select");
		lbxEstadoCobro.setClass("combobox");
		lbxEstadoCobro.setHflex("1");

		// tbxUnidades.setInplace(true);
		lbxEstadoCobro.setId("estado_cobro_receta_" + i);
		listarElementoListboxFromType(lbxEstadoCobro, false, elementos, false);
		lbxEstadoCobro.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				bean.put("estado_cobro", ""
						+ lbxEstadoCobro.getSelectedItem().getValue()
								.toString());
			}
		});

		// Actualizamos el estado de cobro
		if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)
				|| admision.getVia_ingreso().equals(
						IVias_ingreso.HOSPITALIZACIONES)) {
			estado_cobro = IConstantesAutorizaciones.ESTADO_COBRO_PYP;
		} else {
			if (sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_IPS)) {
				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(admision.getCodigo_empresa());
				contratos.setCodigo_sucursal(admision.getCodigo_sucursal());
				contratos.setCodigo_administradora(admision
						.getCodigo_administradora());
				contratos.setId_plan(admision.getId_plan());
				contratos = zkWindow.getServiceLocator()
						.getServicio(ContratosService.class)
						.consultar(contratos);
				if (contratos != null) {
					if (contratos.getCobrar_copago().equals("S")) {
						estado_cobro = IConstantesAutorizaciones.ESTADO_COBRO_MEDICINA_GENERAL;
					} else {
						estado_cobro = IConstantesAutorizaciones.ESTADO_COBRO_PYP;
					}
				} else {
					throw new ValidacionRunTimeException("El contrato nro "
							+ admision.getId_plan() + " no existe");
				}
			} else {
				estado_cobro = Utilidades.getEstadoCobro(admision
						.getVia_ingreso());
				if (estado_cobro
						.equals(IConstantesAutorizaciones.ESTADO_COBRO_PYP)) {
					Articulo articulo = new Articulo();
					articulo.setCodigo_empresa(admision.getCodigo_empresa());
					articulo.setCodigo_sucursal(admision.getCodigo_sucursal());
					articulo.setCodigo_articulo(codigo_articulo);
					articulo = zkWindow.getServiceLocator()
							.getServicio(ArticuloService.class)
							.consultar(articulo);
					if (articulo != null
							&& (articulo.getPyp() + "").equals("S")) {
						estado_cobro = IConstantesAutorizaciones.ESTADO_COBRO_PYP;
					} else {
						estado_cobro = IConstantesAutorizaciones.ESTADO_COBRO_MEDICINA_GENERAL;
					}
				}
			}
		}
		Utilidades.setValueFrom(lbxEstadoCobro, estado_cobro);
		bean.put("estado_cobro", estado_cobro);
		if (!estado_cobro.equals(IConstantesAutorizaciones.ESTADO_COBRO_PYP)) {
			lbxEstadoCobro.setDisabled(true);
		}

		cell.appendChild(lbxEstadoCobro);
		fila.appendChild(cell);

		// columna estado del medicamento
		cell = new Listcell();
		Textbox textbox_estado = new Textbox();
		textbox_estado.setReadonly(true);
		textbox_estado.setHflex("1");
		if (cantidad_entregada < cantidad_recetada) {
			if (cantidad_entregada == 0d) {
				textbox_estado
						.setStyle("text-align:center;background-color:red"
								+ ";font-weight:bold;border-style:solid;color:white;font-size:14px");
				textbox_estado.setValue("0");
				cell.setTooltiptext("Medicamento no entregado");
			} else {
				textbox_estado
						.setStyle("text-align:center;background-color:orange"
								+ ";font-weight:bold;border-style:solid;color:white;font-size:14px");
				textbox_estado.setValue("" + cantidad_entregada);
				cell.setTooltiptext("Medicamento entregado parcialmente");
			}
		} else {
			textbox_estado
					.setStyle("text-align:center;background-color:green"
							+ ";font-weight:bold;border-style:solid;color:white;font-size:14px");
			textbox_estado.setValue("" + cantidad_entregada);
			cell.setTooltiptext("Medicamento entregado");
		}

		cell.appendChild(textbox_estado);

		fila.appendChild(cell);

		// Columna borrar //
		cell = new Listcell();

		Image img = new Image("/images/borrar.gif");

		img.setId("img_receta_" + i);
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
									seleccionados.remove(codigo_articulo);
									actualizarPagina();
									lista_datos.remove(index);
									crearFilas();
									if (lista_datos.isEmpty()) {
										footIndicacionRecomendaciones
												.setVisible(false);
									}
									// esta parte es
									// validarExistenciaMedicamentosNoAutorizados();
								}
							}
						});
			}
		});

		tbxCantidad_recetada.addEventListener("onChange", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				double cant = (tbxCantidad_recetada.getValue() != null ? tbxCantidad_recetada
						.getValue() : 0.0);
				double valor_unit = (tbxValor_unitario.getValue() != null ? tbxValor_unitario
						.getValue() : 0.0);

				if (tbxCantidad_recetada.getValue() != null) {
					tbxValor_total.setValue((cant * valor_unit));
				}
			}
		});

		fila.setId("fila_receta_" + i);

		formReceta.appendChild(fila);
	}

	// private void validarExistenciaMedicamentosNoAutorizados(){
	// if(contieneMedicamentosNoAutorizados().isEmpty()){
	// solicitud_tecnico = null;
	// btSolicitud.setVisible(false);
	// }
	// }

	public void listarElementoListboxFromType(Listbox listbox, boolean select,
			List<Elemento> elementos, boolean selectEnd) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			if (!selectEnd) {
				listbox.setSelectedIndex(0);
			} else {
				listbox.setSelectedIndex(listbox.getChildren().size() - 1);
			}
		}
	}

	public void actualizarPagina() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);

			Listitem fila = (Listitem) formReceta.getFellow("fila_receta_" + i);
			Doublebox tbxCantidad_recetada = (Doublebox) fila
					.getFellow("cantidad_recetada_receta_" + i);
			Doublebox tbxValor_unitario = (Doublebox) fila
					.getFellow("valor_unitario_receta_" + i);
			Doublebox tbxValor_total = (Doublebox) fila
					.getFellow("valor_total_receta_" + i);
			Textbox tbxPosologia = (Textbox) fila.getFellow("posologia_receta_"
					+ i);
			Intbox tiempotto = (Intbox) fila
					.getFellow("tiempo_tto_receta_" + i);

			Listbox via_administracion_receta = (Listbox) fila
					.getFellow("via_administracion_receta_" + i);

			bean.put("cantidad_recetada", tbxCantidad_recetada.getValue());
			bean.put("valor_unitario", tbxValor_unitario.getValue());
			bean.put("valor_total", tbxValor_total.getValue());
			bean.put("posologia", tbxPosologia.getValue());
			bean.put("tiempo_tto", tiempotto.getValue());
			bean.put("via_administracion", via_administracion_receta
					.getSelectedItem().getValue().toString());
			lista_datos.set(i, bean);
		}
	}

	/**
	 * Este metodo te permite visualizar las recetas que se le enviaron ha este
	 * paciente anteriormente
	 * 
	 * @author Luis Miguel
	 * */
	public void mostrarHistoriaRecetas() {

	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Receta_rips receta_rips = (Receta_rips) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		Usuarios usuarios = new Usuarios();
		usuarios.setCodigo_empresa(this.usuarios.getCodigo_empresa());
		usuarios.setCodigo_sucursal(this.usuarios.getCodigo_sucursal());
		usuarios.setCodigo(receta_rips.getCodigo_prestador());
		usuarios = zkWindow.getServiceLocator().getUsuariosService()
				.consultar(usuarios);

		String nombre_medico = " -- ";
		if (usuarios != null) {
			nombre_medico = usuarios.getApellidos() + " "
					+ usuarios.getNombres();
		}

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.setTooltiptext(receta_rips.getObservaciones());
		fila.appendChild(new Label(receta_rips.getCodigo_prestador() + ""));
		fila.appendChild(new Label(nombre_medico + ""));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(receta_rips.getFecha())));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/vista_previa.png");
		img.setTooltiptext("visualizar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				visualizarRecetas(receta_rips);
			}
		});
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	public void crearNuevaReceta() throws Exception {
		limpiarDatos();
		deshabilitarCampos(false);
		btGuardarReceta.setDisabled(false);
		btEliminarReceta.setVisible(true);
		btEliminarReceta.setDisabled(true);
		tbxAccion.setValue("registrar");
	}

	private void visualizarRecetas(Receta_rips receta_rips) throws Exception {
		mostrarDatos(receta_rips, true);
		deshabilitarCampos(true);
	}

	public ZKWindow getZkWindow() {
		return zkWindow;
	}

	public void setZkWindow(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		actualizarPagina();
		// Detalle_receta_rips detalle_receta_rips = new Detalle_receta_rips();
		// detalle_receta_rips.setCodigo_empresa((String) pcd
		// .get("codigo_empresa"));
		// detalle_receta_rips.setCodigo_sucursal((String) pcd
		// .get("codigo_sucursal"));
		// detalle_receta_rips.setCodigo_articulo((String) pcd
		// .get("codigo_articulo"));
		// detalle_receta_rips.setValor_unitario((Double) pcd
		// .get("valor_unitario"));
		// detalle_receta_rips.setValor_total((Double) pcd.get("valor_total"));
		// detalle_receta_rips.setDescuento((Double) pcd.get("descuento"));
		// detalle_receta_rips.setIncremento((Double) pcd.get("incremento"));
		// detalle_receta_rips.setValor_real((Double) pcd.get("valor_real"));
		// detalle_receta_rips.setAutorizado((Boolean) pcd.get("autorizado"));
		// detalle_receta_rips.setCantidad_recetada(0);
		// detalle_receta_rips.setPosologia("");
		// detalle_receta_rips.setEntregado("N");
		// detalle_receta_rips.setTiempo_tto(0);

		/*
		 * Esta obcion me permite, validar la cantidad de medicamentos no
		 * autorizados por el medico maximo no autorizados 3
		 */
		// boolean aplica_adicion = true;
		// if (sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)
		// && !detalle_receta_rips.getAutorizado()) {
		// int cantidad_medicamentos_no_autorizados =
		// getCantidadMedicamentosNoAutorizados();
		// if (cantidad_medicamentos_no_autorizados >= 3) {
		// aplica_adicion = false;
		// MensajesUtil.mensajeAlerta("Advertencia",
		// "Solo puede ordenar 3 procedimientos no autorizados");
		// }
		// }
		// if (aplica_adicion) {
		pcd.put("cantidad_entregada", 0d);
		lista_datos.add(pcd);
		crearFilas();
		seleccionados.add((String) pcd.get("codigo_articulo"));
		// }
		// footIndicacionRecomendaciones.setVisible(true);
	}

//	private int getCantidadMedicamentosNoAutorizados() {
//		int c = 0;
//		for (Map<String, Object> bean : lista_datos) {
//			if (!(Boolean) bean.get("autorizado")) {
//				++c;
//			}
//		}
//		return c;
//	}

	private void listarVia_administracion(Listbox listbox) {
		listbox.setName("via_administracion_receta");
		Utilidades.listarElementoListbox(listbox, true,
				zkWindow.getServiceLocator());
	}

	public void actualizarDiagnosticos(
			Impresion_diagnostica impresion_diagnostica) {
		StringBuilder stringBuilder = new StringBuilder();
		Cie cie = new Cie();
		cie.setCodigo(impresion_diagnostica.getCie_principal());
		cie = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

		stringBuilder
				.append("\tDiagnostico principal: ")
				.append(impresion_diagnostica.getCie_principal())
				.append(" ")
				.append(cie != null ? cie.getNombre() : "")
				.append("\t")
				.append("\tTipo: ")
				.append(Utilidades.getNombreElemento(
						impresion_diagnostica.getTipo_principal(),
						"tipo_diagnostico", zkWindow)).append("\n");

		/* relacionado 1 */
		if (!impresion_diagnostica.getCie_relacionado1().isEmpty()) {
			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado1());
			cie = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			stringBuilder
					.append("\tRelacionado 1: ")
					.append(impresion_diagnostica.getCie_relacionado1())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado1(),
							"tipo_diagnostico", zkWindow)).append("\n");
		}
		if (!impresion_diagnostica.getCie_relacionado2().isEmpty()) {

			/* relacionado 2 */
			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado2());
			cie = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			stringBuilder
					.append("\tRelacionado 2: ")
					.append(impresion_diagnostica.getCie_relacionado2())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado2(),
							"tipo_diagnostico", zkWindow)).append("\n");

		}
		if (!impresion_diagnostica.getCie_relacionado3().isEmpty()) {

			cie = new Cie();
			cie.setCodigo(impresion_diagnostica.getCie_relacionado3());
			cie = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

			stringBuilder
					.append("\tRelacionado 3: ")
					.append(impresion_diagnostica.getCie_relacionado3())
					.append(" ")
					.append(cie != null ? cie.getNombre() : "")
					.append("\t")
					.append("\tTipo: ")
					.append(Utilidades.getNombreElemento(
							impresion_diagnostica.getTipo_relacionado3(),
							"tipo_diagnostico", zkWindow));
		}
		// tbxObservaciones.setValue(stringBuilder.toString());
		impresion = impresion_diagnostica;
	}

	public Toolbarbutton obtenerBotonAgregar() {
		return btAdicionar2;
	}

	public void setCodigo_orden(String codigo_orden) {
		this.tbxCodigo_receta.setValue(codigo_orden);
	}

	public boolean validarParaImpresion() {
		if (!lista_datos.isEmpty()) {
			btImprimir2.setDisabled(false);
			btImprimir2.setVisible(true);
			return true;
		} else {
			btImprimir2.setDisabled(true);
			return false;
		}
	}

	public int getTotalDetalles() {
		return lista_datos.size();
	}

	public Solicitud_tecnico getSolicitud_tecnico() {
		return solicitud_tecnico;
	}

	public void setSolicitud_tecnico(Solicitud_tecnico solicitud_tecnico) {
		this.solicitud_tecnico = solicitud_tecnico;
	}

}
