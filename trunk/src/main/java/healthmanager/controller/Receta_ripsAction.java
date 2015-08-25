package healthmanager.controller;

import healthmanager.controller.ZKWindow.OpcionesFormulario;
import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Receta_rips;
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
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Foot;
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
import org.zkoss.zul.impl.XulElement;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.res.CargardorDeDatos;
import com.framework.res.Res;
import com.framework.res.VerificacionOnlyPyp;
import com.framework.util.MensajesUtil;
import com.softcomputo.composer.constantes.IParametrosSesion;

import contaweb.modelo.bean.Articulo;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * De los mismos creadores de flamenco.
 * @author ferney
 * @author Luis Miguel
 * @author Dario P.
 */

public class Receta_ripsAction extends Action implements AfterCompose {

	private static Logger log = Logger.getLogger(Receta_ripsAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

//	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	private Window form;

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

	private Grid gridReceta;
	private Rows rowsReceta;

	private Column colValor_unitario;
	private Column colValor_total;

	private List<Map> lista_datos;

	private String rol_usuario;
	private Parametros_empresa parametrosEmpresaGlobal;
	private boolean noPrint;
	private Admision admision;
//	private Paciente paciente;
	
	@View private Button btEliminar;
	
	// contenedor
	@View private Borderlayout brlContenedorReceta;
//	@View private Toolbarbutton btnHistorialRecetas;
	@View private Groupbox groupboxConsulta;
	@View private Button btGuardar;
	
	@View Listbox lbxParameter;
	@View Textbox tbxValue;
	@View Rows rowsResultado;
	@View Grid gridResultado;
	@View Button btnNuevaReceta;

	@View
	private Textbox tbxObservaciones;
	private String tipo_hc;
	private Receta_rips receta_ripsEnUso;
	
	
	@View Div dvContenedornoPametrizado;
	@View Div divContenedorPametrizado;
	@View Foot footIndicacionRecomendaciones;
	private boolean trabajar_sin_admision_atendida;
	
	private String opcion_formulario;
	
	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void afterCompose() {
		CargardorDeDatos.initComponents(this);
		listarParameter();
		loadComponents();
		parametrizarResultadoPaginado();
		cargarDatosSesion();
		try {
			initReceta_rips();
			verificamosTipeImpresion();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void verificamosTipeImpresion() {
		Parametros_empresa parametrosEmpresa = new Parametros_empresa();
		parametrosEmpresa.setCodigo_empresa(admision.getCodigo_empresa());
		parametrosEmpresaGlobal = getServiceLocator()
				.getServicio(GeneralExtraService.class).consultar(parametrosEmpresa);
		if (parametrosEmpresaGlobal != null) {
			if (parametrosEmpresaGlobal.getPrint_receta_consulta_ext().equals(
					"N")) {
				((Button) getFellow("btImprimir")).setVisible(false);
				noPrint = true;
			}
		}
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formReceta_rips");

		lbxFormato = (Listbox) form.getFellow("lbxFormato");
		tbxCodigo_receta = (Textbox) form.getFellow("tbxCodigo_receta");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		tbxNro_identificacion = (Textbox) form
				.getFellow("tbxNro_identificacion");
		tbxCodigo_administradora = (Textbox) form
				.getFellow("tbxCodigo_administradora");
		tbxId_plan = (Textbox) form.getFellow("tbxId_plan");
		tbxNro_ingreso = (Textbox) form.getFellow("tbxNro_ingreso");
		tbxTipo_hc = (Textbox) form.getFellow("tbxTipo_hc");

		dtbxFecha = (Datebox) form.getFellow("dtbxFecha");
		tbxCodigo_prestador = (Bandbox) form.getFellow("tbxCodigo_prestador");
		tbxNomPrestador = (Textbox) form.getFellow("tbxNomPrestador");

		gridReceta = (Grid) form.getFellow("gridReceta");
		rowsReceta = (Rows) form.getFellow("rowsReceta");

		colValor_unitario = (Column) form.getFellow("colValor_unitario");
		colValor_total = (Column) form.getFellow("colValor_total");
	}
	
	

	public void initReceta_rips() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		try {
			Map<String, Object> parametros = (Map<String, Object>) Executions
					.getCurrent().getArg();
			opcion_formulario = (String)parametros.get("opcion_formulario");
			admision = (Admision) parametros.get("admision");
			tipo_hc = (String) parametros.get("tipo_hc"); 
			String id_m = request.getParameter("id_menu");
			if (id_m != null) {
				// crearPermisos(new Integer(id_m));
			}
			
			if(parametros.get("ocultarInformacion") != null){
				dvContenedornoPametrizado.setVisible(false);
				divContenedorPametrizado.setVisible(true);
				footIndicacionRecomendaciones.setVisible(false);
				trabajar_sin_admision_atendida = true; 
			}
			// Map parametros = Executions.getCurrent().getArg();
			String nro_identificacion = admision.getNro_identificacion();
			String nro_ingreso = admision.getNro_ingreso();
			String estado = admision.getEstado();
//			String codigo_administradora = admision.getCodigo_empresa();
			String id_plan = admision.getId_plan();

			tbxNro_identificacion.setValue(admision.getNro_identificacion());
			tbxNro_ingreso.setValue(admision.getNro_ingreso());
			tbxCodigo_administradora.setValue(admision.getCodigo_empresa());
			tbxId_plan.setValue(id_plan);
			tbxTipo_hc.setValue(tipo_hc);

			// admision = new Admision; parametros.get("admision");
			if (nro_identificacion.equals("") && nro_ingreso.equals("")) {
				accionForm(true, "registrar");
				deshabilitarCampos(true);
			} else {
				boolean estado_receta = false;
				receta_ripsEnUso = new Receta_rips(); 
				receta_ripsEnUso.setCodigo_empresa(admision.getCodigo_empresa());
				receta_ripsEnUso.setCodigo_sucursal(admision.getCodigo_sucursal());
				receta_ripsEnUso.setNro_identificacion(admision
						.getNro_identificacion());
				receta_ripsEnUso.setNro_ingreso(admision.getNro_ingreso());
				receta_ripsEnUso.setTipo_hc(tipo_hc);
				receta_ripsEnUso = getServiceLocator().getReceta_ripsService()
						.consultar(receta_ripsEnUso);
				// //log.info("historia: "+historia);
				boolean existReceta = false;
				if (receta_ripsEnUso != null) {
					estado_receta = receta_ripsEnUso.getEstado();
					mostrarDatos(receta_ripsEnUso);
					existReceta = true;
					btEliminar.setVisible(false);
				} else {
					accionForm(true, "registrar");
				}

				if (estado.equals("2") || estado_receta) {
					deshabilitarCampos(true);
				} else {
					deshabilitarCampos(false);
				}

				if (existReceta)
					((Button) getFellow("btGuardar")).setDisabled(true);
			}

			if (parametros.get("ocultaValor") != null) {
				colValor_unitario.setVisible(false);
				colValor_total.setVisible(false);
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

		 sucursal = ServiceLocatorWeb.getSucursal(request);
		 usuarios = ServiceLocatorWeb.getUsuarios(request);

		rol_usuario = (String) request.getSession().getAttribute(IParametrosSesion.PARAM_ROL_USUARIO);
	}

	public void cargarPrestador() throws Exception {
		if (rol_usuario.equals("05")) {
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(admision.getCodigo_empresa());
			prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
			prestadores.setNro_identificacion(admision.getCodigo_medico());
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
								"tbxNro_identificacion")
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
		Collection<Component> collection = form.getFellows();
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
			Row fila = (Row) rowsReceta.getFellow("fila_" + i);

			Textbox tbxCodigo_articulo = (Textbox) fila
					.getFellow("codigo_articulo_" + i);
			Textbox tbxNombre_articulo = (Textbox) fila
					.getFellow("nombre_articulo_" + i);
			Doublebox tbxCantidad_recetada = (Doublebox) fila
					.getFellow("cantidad_recetada_" + i);
			Textbox tbxPosologia = (Textbox) fila.getFellow("posologia_" + i);
			
			Image img = (Image) fila.getFellow("img_" + i);

			if (!sw) {
				tbxCodigo_articulo.setReadonly(false);
				tbxNombre_articulo.setReadonly(false);
				tbxCantidad_recetada.setReadonly(false);
				tbxPosologia.setReadonly(false);
				img.setVisible(true);
			} else {
				tbxCodigo_articulo.setReadonly(true);
				tbxNombre_articulo.setReadonly(true);
				tbxCantidad_recetada.setReadonly(true);
				tbxPosologia.setReadonly(true);
				img.setVisible(false);
			}
			
			if(opcion_formulario != null && opcion_formulario.equals(OpcionesFormulario.MOSTRAR.toString())){
				//log.info("opcion_formulario ===> "+opcion_formulario);
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
			tbxCodigo_prestador.setDisabled(true);
		} else {
			tbxCodigo_prestador.setDisabled(sw);
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

		tbxCodigo_prestador.setStyle("background-color:white");
		tbxNomPrestador.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";
		
		if(trabajar_sin_admision_atendida){
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

		if (valida) {
			actualizarPagina();

			for (int i = 0; i < lista_datos.size(); i++) {
				Map bean = lista_datos.get(i);

				String codigo_articulo = (String) bean.get("codigo_articulo");
				String nombre_articulo = (String) bean.get("nombre_articulo");
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
	
	
	public boolean validarFormExterno() throws Exception {

		tbxCodigo_prestador.setStyle("background-color:white");
		tbxNomPrestador.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";
		
		if(trabajar_sin_admision_atendida){
			cargarPrestador();
		}

		if (tbxCodigo_prestador.getText().trim().equals("")) {
			tbxCodigo_prestador.setStyle("background-color:#F6BBBE");
			tbxNomPrestador.setStyle("background-color:#F6BBBE");
			valida = false;
		}

//		if (valida && lista_datos.size() <= 0) {
//			mensaje = "Debe crear al menos un registro de receta";
//			valida = false;
//		}
 
		if (!admision.getAtendida() && !trabajar_sin_admision_atendida) { 
			valida = false;
			mensaje = "Para poder realizar esta accion primero debe realizar la historia";
		}

		if (valida && !lista_datos.isEmpty()) {
			actualizarPagina();

			for (int i = 0; i < lista_datos.size(); i++) {
				Map bean = lista_datos.get(i);

				String codigo_articulo = (String) bean.get("codigo_articulo");
				String nombre_articulo = (String) bean.get("nombre_articulo");
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
	

	@SuppressWarnings("unused")
	private boolean isThereNoAutorizados(List<Detalle_receta_rips> listaDetalle) {
		for (Detalle_receta_rips detalleRecetaRips : listaDetalle)
			if (!detalleRecetaRips.getAutorizado())
				return true;
		return false;
	}
	
	public Map<String, Object> obtenerDatos(){
		final Map datos = new HashMap();

		Receta_rips receta_rips = new Receta_rips();
		receta_rips.setCodigo_empresa(admision.getCodigo_empresa());
		receta_rips.setCodigo_sucursal(admision.getCodigo_sucursal());
		receta_rips.setCodigo_receta(tbxCodigo_receta.getValue());
		receta_rips.setNro_identificacion(tbxNro_identificacion
				.getValue());
		receta_rips.setCodigo_administradora(admision
				.getCodigo_administradora());
		receta_rips.setId_plan(admision.getId_plan());
		receta_rips.setNro_ingreso(admision.getNro_ingreso());
		receta_rips.setFecha(new java.sql.Date(dtbxFecha.getValue()
				.getTime()));
		receta_rips.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		receta_rips.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		receta_rips.setCreacion_user(usuarios.getCodigo());
		receta_rips.setUltimo_user(usuarios.getCodigo());
		receta_rips.setEstado(false);
		receta_rips.setTipo_hc(tipo_hc);
		receta_rips.setCodigo_prestador(tbxCodigo_prestador.getValue());
		receta_rips.setLeido(false);
		receta_rips.setLeido_auditor("N");
		receta_rips.setProcedencia("01");
		receta_rips.setObservaciones("" + tbxObservaciones.getValue());
		receta_rips.setVigencia(parametrosEmpresaGlobal
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
			double valor_unitario = (Double) bean.get("valor_unitario");
			double valor_total = (Double) bean.get("valor_total");
			String posologia = (String) bean.get("posologia");
			double descuento = (Double) bean.get("descuento");
			double incremento = (Double) bean.get("incremento");
			double valor_real = (Double) bean.get("valor_real");
			boolean autorizado = (Boolean) bean.get("autorizado");
			String estado_cobro = (String) bean.get("estado_cobro");
			int tiempo_tto = (Integer) bean.get("tiempo_tto");

			//log.info(":: medicamento autorizado: "
			//+ autorizado);
			//log.info(":: Estado de Cobro: " + estado_cobro);
			if (!autorizado
					&& sucursal.getTipo().equals(SucursalAction.IPS))
				haveNoAutorizados = true;

			Detalle_receta_rips detalle = new Detalle_receta_rips();
			detalle.setCodigo_empresa(receta_rips.getCodigo_empresa());
			detalle.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
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
			detalle.setAutorizado(parametrosEmpresaGlobal
					.getSolo_afiliados() ? autorizado : true);
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
		return datos;
	}
	

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		Messagebox
		.show("Esta seguro que desea guardar esta prescripcion medica? ",
				"Informacion", Messagebox.YES + Messagebox.NO,
				Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event event)
							throws Exception {
						if ("onYes".equals(event.getName())) {
							try {
								// setUpperCase();
								if (validarForm()) {
									final Map datos = new HashMap();

									Receta_rips receta_rips = new Receta_rips();
									receta_rips.setCodigo_empresa(admision.getCodigo_empresa());
									receta_rips.setCodigo_sucursal(admision.getCodigo_sucursal());
									receta_rips.setCodigo_receta(tbxCodigo_receta.getValue());
									receta_rips.setNro_identificacion(tbxNro_identificacion
											.getValue());
									receta_rips.setCodigo_administradora(admision
											.getCodigo_empresa());
									receta_rips.setId_plan(admision.getId_plan());
									receta_rips.setNro_ingreso(admision.getNro_ingreso());
									receta_rips.setFecha(new java.sql.Date(dtbxFecha.getValue()
											.getTime()));
									receta_rips.setCreacion_date(new Timestamp(
											new GregorianCalendar().getTimeInMillis()));
									receta_rips.setUltimo_update(new Timestamp(
											new GregorianCalendar().getTimeInMillis()));
									receta_rips.setCreacion_user(usuarios.getCodigo());
									receta_rips.setUltimo_user(usuarios.getCodigo());
									receta_rips.setEstado(false);
									receta_rips.setTipo_hc(tipo_hc);
									receta_rips.setCodigo_prestador(tbxCodigo_prestador.getValue());
									receta_rips.setLeido(false);
									receta_rips.setLeido_auditor("N");
									receta_rips.setProcedencia("01");
									receta_rips.setObservaciones("" + tbxObservaciones.getValue());
									receta_rips.setVigencia(parametrosEmpresaGlobal
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
										double valor_unitario = (Double) bean.get("valor_unitario");
										double valor_total = (Double) bean.get("valor_total");
										String posologia = (String) bean.get("posologia");
										double descuento = (Double) bean.get("descuento");
										double incremento = (Double) bean.get("incremento");
										double valor_real = (Double) bean.get("valor_real");
										boolean autorizado = (Boolean) bean.get("autorizado");
										String estado_cobro = (String) bean.get("estado_cobro");
										int tiempo_tto = (Integer) bean.get("tiempo_tto");

										System.out.println(":: medicamento autorizado: "
												+ autorizado);
										System.out.println(":: Estado de Cobro: " + estado_cobro);
										if (!autorizado
												&& sucursal.getTipo().equals(SucursalAction.IPS))
											haveNoAutorizados = true;

										Detalle_receta_rips detalle = new Detalle_receta_rips();
										detalle.setCodigo_empresa(receta_rips.getCodigo_empresa());
										detalle.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
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
										detalle.setAutorizado(parametrosEmpresaGlobal
												.getSolo_afiliados() ? autorizado : true);
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

									if (haveNoAutorizados && !haveSolicitud(receta_rips) && parametrosEmpresaGlobal.getTipo_solicitud_tecnica().equals("01")) {
										Messagebox
												.show("Hay medicamentos no autorizados. Debe generar una Solicitud",
														"Medicamentos no autorizados",
														Messagebox.YES + Messagebox.NO,
														Messagebox.QUESTION,
														new org.zkoss.zk.ui.event.EventListener() {
															public void onEvent(Event event)
																	throws Exception {
																if ("onYes".equals(event.getName())) {
																	lookUpMedicamentosSolicitados(
																			lista_detalle, datos);
																}
															}
														});
										return;
									}else{
										receta_rips = getServiceLocator().getReceta_ripsService()
										.guardar(datos);
										
										if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
											tbxAccion.setText("modificar");
										}
										
										mostrarDatos(receta_rips);
										final String codigo_receta = receta_rips.getCodigo_receta();
										loadStateSave(codigo_receta);
									}
									/* tomamos la instancia de la ultima receta*/
									receta_ripsEnUso = receta_rips;
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
								Messagebox.show(e.getMessage(), "Mensaje de Error !!",
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});
	}

	/**
	 * Este metodo actualiza el estado de las receta, para esta solicitud tecnica
	 * @author Luis Miguel Hernandez
	 * */
	public void loadStateSave(final String codigo_receta) throws Exception {
		btGuardar.setDisabled(true);
		btEliminar.setDisabled(false);
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
			codigo_receta = getServiceLocator().getConsecutivoService()
					.crearConsecutivo(sucursal.getCodigo_empresa(),
							sucursal.getCodigo_sucursal(), "RECETA_RIPS");
		}
		Map param = new HashMap();
		param.put("codigo_empresa", admision.getCodigo_sucursal());
		param.put("codigo_sucursal", admision.getCodigo_empresa());
		param.put("codigo_receta", codigo_receta);
		return getServiceLocator().getSolicitudTecnicoService().existe(param);
	}
	
	private void lookUpMedicamentosSolicitados(
			List<Detalle_receta_rips> detalleRecetaRips, Map datos)
			throws Exception {
		Map params = new HashMap();
		params.put("detalles", detalleRecetaRips);
		params.put("admision", admision);
		params.put("datos", datos);

		Component componente = Executions.createComponents(
				"/pages/solicitud_tecnico.zul", this, params);
		final Window window = (Window) componente;
		window.setWidth("100%");
		window.setHeight("100%");
		try {
			window.doModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// window.setMaximizable(true);
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
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
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			tbxCodigo_prestador.setValue(receta_rips.getCodigo_prestador());
			tbxNomPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));
			tbxObservaciones.setValue(Res.ifNull(
					receta_rips.getObservaciones(), String.class));
			lista_datos.clear();
			rowsReceta.getChildren().clear();
			List<Detalle_receta_rips> lista_detalle = receta_rips
					.getLista_detalle();
			for (Detalle_receta_rips detalle : lista_detalle) {
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
								int result = getServiceLocator()
										.getReceta_ripsService().eliminar(
												receta_rips);
								if (result == 0) {
									throw new Exception(
											"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
								}

								accionForm(true, "registrar");
								((Button) getFellow("btGuardar"))
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
			Messagebox.show("La receta medica no se ha guardado aun",
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
				"/pages/printInformes.zul", form, paramRequest);
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
		articulo = getServiceLocator().getArticuloService().consultar(articulo);
		nombre_articulo = (articulo != null ? articulo.getNombre2() : "");

		double cantidad_recetada = detalle.getCantidad_recetada();
		double valor_unitario = detalle.getValor_unitario();
		double valor_total = detalle.getValor_total();
		double descuento = detalle.getDescuento();
		double incremento = detalle.getIncremento();
		double valor_real = detalle.getValor_real();
		String posologia = detalle.getPosologia();

		Map bean = new HashMap();
		bean.put("codigo_articulo", codigo_articulo);
		bean.put("nombre_articulo", nombre_articulo);
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

		Component componente = Executions.createComponents(
				"/pages/openArticulo.zul", form, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR MEDICAMENTOS ");
		ventana.setMode("modal");
	}

	public void adicionarPcd(Map pcd) throws Exception {
		actualizarPagina();
		Detalle_receta_rips detalle_receta_rips = new Detalle_receta_rips();
		detalle_receta_rips.setCodigo_empresa((String) pcd
				.get("codigo_empresa"));
		detalle_receta_rips.setCodigo_sucursal((String) pcd
				.get("codigo_sucursal"));
		detalle_receta_rips.setCodigo_articulo((String) pcd
				.get("codigo_articulo"));
		detalle_receta_rips.setValor_unitario((Double) pcd
				.get("valor_unitario"));
		detalle_receta_rips.setValor_total((Double) pcd.get("valor_total"));
		detalle_receta_rips.setDescuento((Double) pcd.get("descuento"));
		detalle_receta_rips.setIncremento((Double) pcd.get("incremento"));
		detalle_receta_rips.setValor_real((Double) pcd.get("valor_real"));
		detalle_receta_rips.setAutorizado((Boolean) pcd.get("autorizado"));
		detalle_receta_rips.setCantidad_recetada(1);
		detalle_receta_rips.setPosologia("");
		detalle_receta_rips.setEntregado("N");
		detalle_receta_rips.setTiempo_tto(1);

		adicionarReceta(detalle_receta_rips);
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
		rowsReceta.getChildren().clear();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar("estado_pago");
		for (int j = 0; j < lista_datos.size(); j++) {
			Map bean = lista_datos.get(j);
			crearFilaReceta(bean, j, elementos);
		}
	}

	public void crearFilaReceta(final Map bean, int j, List<Elemento> elementos)
			throws Exception {
		final int index = j;

		String codigo_articulo = (String) bean.get("codigo_articulo");
		String nombre_articulo = (String) bean.get("nombre_articulo");
		double cantidad_recetada = (Double) bean.get("cantidad_recetada");
		double valor_unitario = (Double) bean.get("valor_unitario");
		double valor_total = (Double) bean.get("valor_total");
		double descuento = (Double) bean.get("descuento");
		double incremento = (Double) bean.get("incremento");
		double valor_real = (Double) bean.get("valor_real");
		String posologia = (String) bean.get("posologia");
		String estado_cobro = "01";
		int tiempo_tto = (Integer) bean.get("tiempo_tto"); 
		

		System.out.println("Estado de Cobro: "
				+ bean.containsKey("estado_cobro"));
		String estado_cobro_temp = (String) bean.get("estado_cobro");
		if (estado_cobro_temp != null) {
			estado_cobro = (String) bean.get("estado_cobro");
		} else {
			bean.put("estado_cobro", estado_cobro);
		}

		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo //
		Cell cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_procedimiento = new Textbox(codigo_articulo);
		// tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("codigo_articulo_" + j);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setReadonly(true);
		cell.appendChild(tbxCodigo_procedimiento);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_procedimiento = new Textbox(nombre_articulo);
		// tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_articulo_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna cantidad recetada //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxCantidad_recetada = new Doublebox(cantidad_recetada);
		// tbxUnidades.setInplace(true);
		tbxCantidad_recetada.setId("cantidad_recetada_" + j);
		tbxCantidad_recetada.setFormat("#,##0");
		tbxCantidad_recetada.setHflex("1");
		tbxCantidad_recetada.setReadonly(false);
		cell.appendChild(tbxCantidad_recetada);
		fila.appendChild(cell);

		// Columna cantidad recetada //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValor_unitario = new Doublebox(valor_unitario);
		// tbxUnidades.setInplace(true);
		tbxValor_unitario.setId("valor_unitario_" + j);
		tbxValor_unitario.setFormat("#,##0.00");
		tbxValor_unitario.setHflex("1");
		tbxValor_unitario.setReadonly(false);
		cell.appendChild(tbxValor_unitario);
		fila.appendChild(cell);

		// Columna cantidad recetada //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValor_total = new Doublebox(valor_total);
		// tbxUnidades.setInplace(true);
		tbxValor_total.setId("valor_total_" + j);
		tbxValor_total.setFormat("#,##0.00");
		tbxValor_total.setHflex("1");
		tbxValor_total.setReadonly(false);
		cell.appendChild(tbxValor_total);
		fila.appendChild(cell);
		
		
		cell = new Cell();
		cell.setAlign("left");
		final Intbox intboxTiempotto = new Intbox(tiempo_tto);
		intboxTiempotto.setId("tiempo_tto_" + j);
		intboxTiempotto.setHflex("1");
		cell.appendChild(intboxTiempotto);
		fila.appendChild(cell);
		

		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxPosologia = new Textbox(posologia);
		// tbxCodigo_procedimiento.setInplace(true);
		tbxPosologia.setId("posologia_" + j);
		tbxPosologia.setHflex("1");
		cell.appendChild(tbxPosologia);

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

		fila.appendChild(cell);

		/* columna estado de cobro */
		cell = new Cell();
		cell.setAlign("left");
		final Listbox lbxEstadoCobro = new Listbox();
		lbxEstadoCobro.setMold("select");
		lbxEstadoCobro.setClass("combobox");

		// tbxUnidades.setInplace(true);
		lbxEstadoCobro.setId("estado_cobro_" + j);
		listarElementoListboxFromType(lbxEstadoCobro, false, elementos, false);
		lbxEstadoCobro.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				
				bean.put("estado_cobro", ""
						+ lbxEstadoCobro.getSelectedItem().getValue()
								.toString());
			}
		});
		if (!VerificacionOnlyPyp.onlyPyp(admision, getServiceLocator())){
			lbxEstadoCobro.setDisabled(true);
		} else {
			// este estado de cobro es PYP
			updateList(lbxEstadoCobro, "02");
		}
		updateList(lbxEstadoCobro, estado_cobro);

		cell.appendChild(lbxEstadoCobro);
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

		fila.setId("fila_" + j);

		rowsReceta.appendChild(fila);

		gridReceta.setVisible(true);
		gridReceta.setMold("paging");
		gridReceta.setPageSize(20);
		gridReceta.applyProperties();
		gridReceta.invalidate();
	}

	// 02
	private void updateList(Listbox listbox, String estado_cobro) {
		for (int i = 0; i < listbox.getItemCount(); i++) {
			Listitem listitem = listbox.getItemAtIndex(i);
			if (listitem.getValue().toString().equals(estado_cobro)) {
				listitem.setSelected(true);
				break;
			}
		}
	}

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

			Row fila = (Row) rowsReceta.getFellow("fila_" + i);
			Doublebox tbxCantidad_recetada = (Doublebox) fila
					.getFellow("cantidad_recetada_" + i);
			Doublebox tbxValor_unitario = (Doublebox) fila
					.getFellow("valor_unitario_" + i);
			Doublebox tbxValor_total = (Doublebox) fila
					.getFellow("valor_total_" + i);
			Textbox tbxPosologia = (Textbox) fila.getFellow("posologia_" + i);
			Intbox tiempotto = (Intbox) fila.getFellow("tiempo_tto_" + i);
			bean.put("cantidad_recetada", tbxCantidad_recetada.getValue());
			bean.put("valor_unitario", tbxValor_unitario.getValue());
			bean.put("valor_total", tbxValor_total.getValue());
			bean.put("posologia", tbxPosologia.getValue());
			bean.put("tiempo_tto", tiempotto.getValue());
			lista_datos.set(i, bean);
		}
	}
	
	/**
	 * Este metodo te permite visualizar las recetas que se le enviaron ha este paciente anteriormente
	 * @author Luis Miguel
	 * */
	public void mostrarHistoriaRecetas(){
		// ocultamos el boton crear nueva, para no permitir crear una nueva receta
		if(receta_ripsEnUso != null){
			btnNuevaReceta.setVisible(false);
		}
		brlContenedorReceta.setVisible(false);
		groupboxConsulta.setVisible(true);
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_receta");
		listitem.setLabel("Codigo Medico");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("fecha::varchar");
		listitem.setLabel("Fecha Receta");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("codigo_empresa", usuarios.getCodigo_empresa());
			parameters.put("codigo_sucursal", usuarios.getCodigo_sucursal());
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			
			List<Receta_rips> lista_datos = getServiceLocator().getReceta_ripsService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Receta_rips receta_rips : lista_datos) {
				rowsResultado.appendChild(crearFilas(receta_rips, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			
			resultadoPaginadoMacro.buscarDatos(parameters);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			MensajesUtil.mensajeAlerta("Advertencia", "Ha ocurrido un error interno, comuniquese con su provedor de servicios..!!");
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Receta_rips receta_rips = (Receta_rips)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		Usuarios usuarios = new Usuarios();
		usuarios.setCodigo_empresa(this.usuarios.getCodigo_empresa());
		usuarios.setCodigo_sucursal(this.usuarios.getCodigo_sucursal());
		usuarios.setCodigo(receta_rips.getCodigo_prestador());
        usuarios = getServiceLocator().getUsuariosService().consultar(usuarios);
        
        String nombre_medico  = " -- ";
        if(usuarios != null){
        	nombre_medico = usuarios.getApellidos() + " " + usuarios.getNombres();
        }
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.setTooltiptext(receta_rips.getObservaciones()); 
		fila.appendChild(new Label(receta_rips.getCodigo_prestador()+""));
		fila.appendChild(new Label(nombre_medico+"")); 
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy").format(receta_rips.getFecha()))); 
	
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
	
	public void crearNuevaReceta() throws Exception{
		limpiarDatos();
		deshabilitarCampos(false);
		btGuardar.setDisabled(false);
		btEliminar.setVisible(true);
		btEliminar.setDisabled(true);
		groupboxConsulta.setVisible(false);
		brlContenedorReceta.setVisible(true);
		tbxAccion.setValue("registrar");
	}
	
	private void visualizarRecetas(Receta_rips receta_rips) throws Exception{
		mostrarDatos(receta_rips);
		groupboxConsulta.setVisible(false);
		brlContenedorReceta.setVisible(true);
		deshabilitarCampos(true);
	}
	
	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Receta_rips> listarResultados(
					Map<String, Object> parametros) {
				List<Receta_rips> listado = getServiceLocator().getReceta_ripsService().listar(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getReceta_ripsService().totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilas(dato, gridResultado);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado,5);
	}
}
