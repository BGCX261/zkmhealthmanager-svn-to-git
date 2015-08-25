package com.framework.macros.manuales_tarifarios;

import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Descuentos_laboratorios;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Servicios_disponibles;
import healthmanager.modelo.bean.Tarifas_procedimientos;
import healthmanager.modelo.service.Descuentos_laboratoriosService;
import healthmanager.modelo.service.Servicios_contratadosService;
import healthmanager.modelo.service.Tarifas_procedimientosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.manuales_tarifarios.interfaces.IAccionEliminar;
import com.framework.macros.manuales_tarifarios.interfaces.IManualesAction;
import com.framework.macros.manuales_tarifarios.validaciones.Validacion;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.Utilidades;

/**
 * ManualTarifarioAdministradorMacro <br>
 * Con esta clase controlamos todas las acciones para crear los manuales
 * tarifarios creados
 * 
 * @author Luis Miguel
 * 
 * */
public class ManualTarifarioAdministradorMacro extends Groupbox {

	public static Logger log = Logger
			.getLogger(ManualTarifarioAdministradorMacro.class);

	private Rows rowsContenedor;

	/**
	 * Esta lista contiene los manuales tarifarios creados
	 * 
	 * @author Luis Miguel
	 * */
	private List<Manuales_tarifarios> manuales_tarifarios;
	private IBrindaServiciosPyp brindaServiciosPyp;

	private IManualesAction iManuelesAction;
	private List<ManualesTarifariosMacro> listmanualesTarifariosMacros;
	private IAccionEliminar accionEliminar;

	private Toolbarbutton toolbarbuttonAgregarNuevo;

	/**
	 * Este componete es de refecia.. Ejemplo, para que el sepa cuando esta
	 * visible y cuando no.
	 * */
	private Component componentReferencia = null;

	public ManualTarifarioAdministradorMacro() {
	}

	public void _inicializar(IManualesAction iManuelesAction,
			IBrindaServiciosPyp brindaServiciosPyp) {
		this.iManuelesAction = iManuelesAction;
		this.manuales_tarifarios = new ArrayList<Manuales_tarifarios>();
		this.brindaServiciosPyp = brindaServiciosPyp;
		brindaServiciosPyp.tieneServiciosPyp(true);
		inicializarComponente();
		cargarEventoAccionEliminar();
		cargarManualesTarifarios();
	}

	private void cargarEventoAccionEliminar() {
		accionEliminar = new IAccionEliminar() {
			@Override
			public void onEliminar(ManualesTarifariosMacro component) {
				removerMacro(component);
			}
		};
	}

	/**
	 * Este metodo me permite adicionar los manuates tarifarios del componente
	 * Ejemplo:<br/>
	 * * Manual<br/>
	 * &nbsp;&nbsp; * servicios<br/>
	 * * Manual<br/>
	 * &nbsp;&nbsp; * Servicios<br/>
	 * * adicionar nuevo
	 * 
	 * @author Luis Miguel
	 * */
	private void cargarManualesTarifarios() {
		if (manuales_tarifarios.isEmpty()) {
			adicionarNuevoManualTarifario().setOpen(true);
			adicionarAccionAgregarNuevoManual();
		} else {
			for (Manuales_tarifarios manuales_tarifarios : this.manuales_tarifarios) {
				adicionarManualTarifario(manuales_tarifarios);
			}
			adicionarAccionAgregarNuevoManual();
		}
	}

	/**
	 * Este metodo me permite, inicializar el contenido de este componente.
	 * 
	 * @author Luis Miguel hernandez Perez
	 * */
	private void inicializarComponente() {
		setWidth("100%");
		setContentStyle("border:0");
		Grid gridManualGlobal = new Grid();
		appendChild(gridManualGlobal);

		Columns columns = new Columns();
		columns.setVisible(false);
		gridManualGlobal.appendChild(columns);

		Column column = new Column();
		// column.setAlign("left");
		columns.appendChild(column);

		rowsContenedor = new Rows();
		gridManualGlobal.appendChild(rowsContenedor);
	}

	/* metodos de adicion */

	/**
	 * Este metodo me permite adicionar un manual tarifario a la vista.
	 * 
	 * @author Luis Miguel
	 * */
	private ManualesTarifariosMacro adicionarManualTarifario(
			Manuales_tarifarios manuales_tarifarios) {
		//log.info(":: Adicionar registros ");
		this.manuales_tarifarios.add(manuales_tarifarios);
		ManualesTarifariosMacro manualesTarifariosMacro = new ManualesTarifariosMacro(
				iManuelesAction, manuales_tarifarios);
		Row row = new Row();
		Cell cell = new Cell();
		cell.appendChild(manualesTarifariosMacro);
		registrarMacro(manualesTarifariosMacro);
		manualesTarifariosMacro.setOpen(true);
		manualesTarifariosMacro.setClosable(false);
		manualesTarifariosMacro.set_rowPadreContenedora(row);
		row.appendChild(cell);
		rowsContenedor.appendChild(row);
		return manualesTarifariosMacro;
	}

	/**
	 * Este metodo me permite validar si presta servicios de pyp
	 * 
	 * @author Luis Miguel
	 * */
	public void validarContieneServiciosPyp() {
		if (brindaServiciosPyp != null)
			brindaServiciosPyp.tieneServiciosPyp(contiene_servicios_pyp());
	}

	public boolean contiene_servicios_pyp() {
		boolean presta_servicios_pyp = false;
		if (listmanualesTarifariosMacros != null)
			for (ManualesTarifariosMacro manualesTarifariosMacro : listmanualesTarifariosMacros) {
				if (manualesTarifariosMacro.contieneServiciosPyp()) {
					//log.info("Manuales tarifarios Macro contiene servicios de PYP");
					presta_servicios_pyp = true;
					break;
				}
			}
		return presta_servicios_pyp;
	}

	/**
	 * Con este metodo registrado cada manual tarifario que se cree para tener
	 * la referencia de cada uno
	 * 
	 * @author Luis Miguel Hernandez
	 * */
	private void registrarMacro(ManualesTarifariosMacro manualesTarifariosMacro) {
		if (listmanualesTarifariosMacros == null)
			listmanualesTarifariosMacros = new ArrayList<ManualesTarifariosMacro>();
		listmanualesTarifariosMacros.add(manualesTarifariosMacro);
		manualesTarifariosMacro.setAccionEliminar(accionEliminar);
		// //log.info(":: Antes de incializar compoenete  ");
		manualesTarifariosMacro._inicializarPara(this);
		// //log.info("Nuevo manual tarifario registrado");
	}

	public Long cualEsMiPosicionEnLista(
			ManualesTarifariosMacro manualesTarifariosMacro2) {
		return new Long(listmanualesTarifariosMacros.indexOf(manualesTarifariosMacro2) + 1);
	}

	/**
	 * Con este metodo removemos el registro al manual tarifario seleccionado y
	 * de la vista
	 * 
	 * @author Luis Miguel
	 * */
	private void removerMacro(ManualesTarifariosMacro manualesTarifariosMacro) {
		if (listmanualesTarifariosMacros != null) {
			listmanualesTarifariosMacros.remove(manualesTarifariosMacro);
			rowsContenedor.removeChild(manualesTarifariosMacro
					.get_rowPadreContenedora());
		}
	}

	private ManualesTarifariosMacro adicionarNuevoManualTarifario() {
		return adicionarManualTarifario(new Manuales_tarifarios());
	}

	/**
	 * Este metodo contiene la accion de agregar, un nuevo manual tarifario
	 * 
	 * @author Luis Migue
	 * */
	private void adicionarAccionAgregarNuevoManual() {
		final Row row = new Row();
		Cell cell = new Cell();
		cell.setAlign("right");
		toolbarbuttonAgregarNuevo = new Toolbarbutton();
		toolbarbuttonAgregarNuevo.setImage("/images/add.png");
		toolbarbuttonAgregarNuevo.setTooltiptext("Agregar manual tarifario!");
		toolbarbuttonAgregarNuevo.setVisible(true);
		toolbarbuttonAgregarNuevo.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						adicionarNuevoManualTarifario();
						adicionarAccionAgregarNuevoManual();
						rowsContenedor.removeChild(row);
						validarContieneServiciosPyp();
					}
				});
		cell.appendChild(toolbarbuttonAgregarNuevo);
		row.appendChild(cell);
		rowsContenedor.appendChild(row);
	}

	/**
	 * Este metodo me permite, marcar si es PYP
	 * 
	 * @author Luis Miguel
	 * */
	public void setPyp(boolean pyp) {

	}

	/**
	 * Este metodo me permite cargar los manuales tarifarios del contrato
	 * 
	 * @author Luis Miguel
	 * */
	public void _cargarContrato(Contratos contratos) throws Exception {
		desecharContenido();
		if (contratos.getManuales_tarifarios() == null
				|| contratos.getManuales_tarifarios().isEmpty())
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					"Este contrato no tiene manuales tarifarios", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		for (Manuales_tarifarios manuales_tarifarios : contratos
				.getManuales_tarifarios()) {
			/* verificamos si tiene restricciones */
			if (manuales_tarifarios.getLista_procedimiento_especificos() == null)
				manuales_tarifarios
						.setLista_procedimiento_especificos(new ArrayList<Map<String, Object>>());

			if (manuales_tarifarios.getLista_descuentos() == null)
				manuales_tarifarios
						.setLista_descuentos(new ArrayList<Map<String, Object>>());

			if (manuales_tarifarios.getListado_servicios_disponibles() == null) {
				manuales_tarifarios
						.setListado_servicios_disponibles(new ArrayList<Servicios_disponibles>());
			}

			ManualesTarifariosMacro manualesTarifariosMacro = adicionarManualTarifario(manuales_tarifarios);

			/* esta validacion es para que solo abra el primero */

			// parametro de busqueda
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("codigo_empresa", contratos.getCodigo_empresa());
			map.put("codigo_sucursal", contratos.getCodigo_sucursal());
			map.put("id_contrato", contratos.getId_plan());
			map.put("id_plan", contratos.getId_plan());
			map.put("codigo_administradora",
					contratos.getCodigo_administradora());
			map.put("consecutivo_manual", manuales_tarifarios.getConsecutivo());

			List<Servicios_contratados> listado_servicios_contratados = getServiceLocator()
					.getServicio(Servicios_contratadosService.class)
					.listar(map);
			manualesTarifariosMacro
					.setServiciosContratados(listado_servicios_contratados);


			// Descuentos
			manualesTarifariosMacro.borrarTablaDescuento();
			List<Descuentos_laboratorios> descuentos_laboratorios = getServiceLocator()
					.getServicio(Descuentos_laboratoriosService.class).listar(
							map);
			for (Descuentos_laboratorios descuentos_laboratoriosTemp : descuentos_laboratorios) {
				Map<String, Object> beanPcd = Utilidades.getProcedimiento(
						manuales_tarifarios,
						new Long(descuentos_laboratoriosTemp
								.getCodigo_procedimiento()),
						getServiceLocator());
				Map<String, Object> pcd = new HashMap<String, Object>();
				pcd.put("codigo_procedimiento",
						descuentos_laboratoriosTemp.getCodigo_procedimiento());
				pcd.put("nombre_procedimiento",
						(String) beanPcd.get("nombre_procedimiento"));
				pcd.put("valor_procedimiento",
						(Double) beanPcd.get("valor_pcd"));
				pcd.put("descuento",
						descuentos_laboratoriosTemp.getPorcentaje_descuento());
				//log.info("Proc: -> " + pcd);
				manuales_tarifarios.getLista_descuentos().add(pcd);
			}
			manualesTarifariosMacro
					.setLista_datos_descuentos(manuales_tarifarios
							.getLista_descuentos());
			manualesTarifariosMacro.crearFilasDescuento();

			manualesTarifariosMacro.borrarTablaTarifas();
			/* Verificamos tarifas especiales */
			if (manuales_tarifarios.getTarifa_especial().equalsIgnoreCase("S")) {
//				//log.info("mapa busqueda tarifas ===> " + map);
				List<Tarifas_procedimientos> lista_pcd_especiales = getServiceLocator()
						.getServicio(Tarifas_procedimientosService.class)
						.listar(map);
				manualesTarifariosMacro.loadTarifasProcedimientos(
						lista_pcd_especiales, manuales_tarifarios);
			}

			manualesTarifariosMacro.crearFilasEspecial();
		}
		validarContieneServiciosPyp();
		// setPyp(contratos.getPyp().equals("S"));
	}

	/**
	 * Este metodo me permite validar, todos los manuales tarifarios agregados.
	 * 
	 * @author Luis Miguel Hernandez Perez
	 * */
	public Validacion validarForm() throws Exception {
		if (listmanualesTarifariosMacros != null) {
			for (ManualesTarifariosMacro manualesTarifariosMacro : listmanualesTarifariosMacros) {
				Validacion validacion = manualesTarifariosMacro.validarForm();
				if (!validacion.isValida()) {
					return validacion;
				}
			}
			return new Validacion(true, "todo bien, todo bien..");
		} else {
			return new Validacion(false, "Debe agregar un manual tarifario!");
		}
	}

	/* fin metodos de adicion */
	public List<Manuales_tarifarios> getManuales_tarifarios() {
		List<Manuales_tarifarios> manuales_tarifarios = new ArrayList<Manuales_tarifarios>();
		for (ManualesTarifariosMacro manualesTarifariosMacro : listmanualesTarifariosMacros) {
			manuales_tarifarios.add(manualesTarifariosMacro
					.getManuales_tarifarios());
		}
		return manuales_tarifarios;
	}

	public void setManuales_tarifarios(
			List<Manuales_tarifarios> manuales_tarifarios) {
		if (manuales_tarifarios == null)
			manuales_tarifarios = new ArrayList<Manuales_tarifarios>();
		this.manuales_tarifarios = manuales_tarifarios;
		cargarManualesTarifarios();
	}

	/**
	 * Este metodo sirve para limpiar todos el componente
	 * 
	 * @author Luis Miguel
	 * */
	public void limpiarDatos() throws Exception {
		if (listmanualesTarifariosMacros != null) {
			for (ManualesTarifariosMacro manualesTarifariosMacro : listmanualesTarifariosMacros) {
				manualesTarifariosMacro.limpiarDatos();
			}
		}
	}

	/**
	 * Este metodo t permite resetear el componente.
	 * 
	 * @author Luis Miguel
	 * */
	public void resetear() {
		desecharContenido();
		cargarManualesTarifarios();
		if (brindaServiciosPyp != null)
			brindaServiciosPyp.tieneServiciosPyp(true);
	}

	/**
	 * Este metodo me permite limpiar todo el contenido hasta el punto de
	 * dejarlo sin ningun item de manual tarifario
	 * 
	 * @author Luis Miguel
	 * */
	public void desecharContenido() {
		if (this.manuales_tarifarios != null)
			this.manuales_tarifarios.clear();
		if (this.listmanualesTarifariosMacros != null)
			this.listmanualesTarifariosMacros.clear();
		rowsContenedor.getChildren().clear();

	}

	/**
	 * Este metodo te permite limpiar todos los datos y del manual tarifario, y
	 * sobre seleccionar la factura.
	 * */
	public void _accionLimpiarDatosYTipofactura(String tipo) throws Exception {
		if (listmanualesTarifariosMacros != null) {
			for (ManualesTarifariosMacro manualesTarifariosMacro : listmanualesTarifariosMacros) {
				manualesTarifariosMacro.limpiarDatos();
			}
		}
	}

	public interface IBrindaServiciosPyp {
		public void tieneServiciosPyp(boolean b);
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

	public Component getComponentReferencia() {
		return componentReferencia;
	}

	public void setComponentReferencia(Component componentReferencia) {
		this.componentReferencia = componentReferencia;
	}
}
