package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.bean.Solicitud_tecnico;
import healthmanager.modelo.service.AdministradoraService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.interfaces.IRelacionSeleccion;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.CampoObservacionesPopupMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;
import com.softcomputo.composer.constantes.IParametrosSesion;

import contaweb.modelo.bean.Articulo;

public class IngresoRecetasExternasAction extends ZKWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_identificacion;
	@View
	private Textbox tbxNombPaciente;
	// @View
	// private Textbox tbxTipoId;
	// @View
	// private Textbox tbxNro;
	@View
	private Textbox tbxNombre_prestador;
	@View
	private Label lbFecha_orden;
	@View
	private Datebox dtbxFecha;

	private List<Map> lista_datos;

	@View
	private Grid gridReceta;
	@View
	private Rows rowsReceta;

	@View
	private Column colValor_unitario;
	@View
	private Column colValor_total;

	@View
	private Borderlayout borderLayoutRegistrar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	private Receta_rips receta_ripsModificar;

	@View
	private Textbox tbxObservaciones;

	private boolean auditor;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_prestador;

	@View
	private Row row_administradora;
	@View
	private Label lbadministradora;
	@View private Toolbarbutton btSolicitud;
	
	
	private Solicitud_tecnico solicitud_tecnico;

	private String[] IDS = {"btGuardar", "btEliminar"}; 
	
	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() {
		lista_datos = new ArrayList<Map>();
		listarParameter();
		parametrizarBandboxPaciente();
		parametrizarBanboxAseguradora();
		inicializarEventos();
		parametrizarResultadoPaginado();
	}
	
	private void inicializarEventos() {
		btSolicitud.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				actualizarPagina();
				final List<Detalle_receta_rips> lista_detalle = contieneMedicamentosNoAutorizados();
				if(!lista_detalle.isEmpty() && solicitud_tecnico != null){
					abrirSolicitud(lista_detalle, solicitud_tecnico, false); 
				}else{
					MensajesUtil.mensajeAlerta("Advertencia", "No se puede cargar la solicitud por no tiene medicamentos o no se a creado la solicitud");
				}
			}
		});
	}


	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		auditor = (request.getSession().getAttribute(IParametrosSesion.PARAM_ROL_USUARIO) + "")
				.equalsIgnoreCase("03");
		if (sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
			lbadministradora.setValue("Prestador: ");
		}
		if (auditor) {
			row_administradora.setVisible(false);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_receta");
		listitem.setLabel("Codigo receta");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Nro identificacion Paciente");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_prestador");
		listitem.setLabel("Nro identificacion Medico");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre_medico");
		listitem.setLabel("Nombre del Medico");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public boolean validarForm() throws Exception {

		boolean valida = true;
        boolean mostrar_msj = true;
		String mensaje = "Los campos marcados con (*) son obligatorios";
		try {
			FormularioUtil.validarCamposObligatorios(tbxCodigo_prestador, tbxNro_identificacion, dtbxFecha);
		} catch (WrongValueException e) { 
			valida = false;
		} 

		if (valida && lista_datos.size() <= 0) {
			mensaje = "Debe crear al menos un registro de receta";
			valida = false;
		}
		
		if (valida) {
			actualizarPagina();

			for (int i = 0; i < lista_datos.size(); i++) {
				Map bean = lista_datos.get(i);

				String codigo_articulo = (String) bean.get("codigo_articulo");
				String nombre_articulo = (String) bean.get("nombre_articulo");
				String posologia = (String) bean.get("posologia");
				String via_administracion = (String) bean.get("via_administracion"); 

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
				
				if (valida && via_administracion.equals("")) {
					mensaje = "Debe digitar la via de administracion en el medicamento "
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
		
		if (valida && sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)
				&& parametros_empresa.getTipo_solicitud_tecnica()
						.equals(IConstantes.SOLICITUD_TECNICA_CAJA_PREV)) {
			valida = validarSolicitud();
//			mensaje = "Para poder realizar esta accion debe registrar una solitud para los medicamentos no autorizados";
			mostrar_msj = false;
		}

		if (!valida && mostrar_msj) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
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
			tbxNombPaciente.setValue("");
			tbxNro_identificacion.setObject(null);
		} else {
			Paciente dato = (Paciente) listitem.getValue();
			tbxNro_identificacion.setValue(dato.getNro_identificacion());
			tbxNombPaciente.setValue(dato.getNombreCompleto());
			tbxNro_identificacion.setObject(dato);
		}
		tbxNro_identificacion.close();
	}

	public void openArticulo() throws Exception {

		Paciente paciente = (Paciente) tbxNro_identificacion.getObject();
		
	    Manuales_tarifarios manuales_tarifarios = (Manuales_tarifarios)	tbxNro_identificacion.getAttribute("manuales_tarifarios"); 

		if (paciente != null) {
			Map parametros = new HashMap();
			parametros.put("codigo_empresa", empresa.getCodigo_empresa());
			parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parametros.put("codigo_administradora",
					paciente.getCodigo_administradora());
			// parametros.put("id_plan", paciente.getId_plan());
			parametros.put("nro_ingreso", "0");
			parametros.put("nro_identificacion",
					tbxNro_identificacion.getValue());
			parametros.put("grupo", "01");
			parametros.put("ocultaExist", "");
			parametros.put("ocultaValor", "");
			parametros.put("manuales_tarifarios", manuales_tarifarios);

			Component componente = Executions.createComponents(
					"/pages/openArticulo.zul", this, parametros);
			final Window ventana = (Window) componente;
			ventana.setWidth("990px");
			ventana.setHeight("400px");
			ventana.setClosable(true);
			ventana.setMaximizable(true);
			ventana.setTitle("CONSULTAR MEDICAMENTOS ");
			ventana.setMode("modal");
		} else {
			MensajesUtil.mensajeAlerta("Alerta !!!",
					"Debe seleccionar un paciente");
		}
	}

	public void limpiar() throws Exception {
		FormularioUtil.limpiarComponentes(borderLayoutRegistrar, IDS);
		receta_ripsModificar = null;
		((Button) getFellow("btGuardar")).setDisabled(false);
		tbxNro_identificacion.removeAttribute("manuales_tarifarios"); 
		rowsReceta.getChildren().clear();
		btSolicitud.setVisible(false);
		lista_datos.clear();
	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

			getServiceLocator().getAdministradoraService().setLimit(
					"limit 25 offset 0");

			List<Map> list = getServiceLocator().getAdministradoraService()
					.listarDesdeContratos(parameters);

			lbx.getItems().clear();

			for (Map dato : list) {

				String codigo = (String) dato.get("codigo");
				String nit = (String) dato.get("nit");
				String nombre = (String) dato.get("nombre");

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(codigo + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(nit + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(nombre + ""));
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

	/* datos del prestador */
	public void selectedPrestador(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxCodigo_prestador.setValue("");
			tbxNombre_prestador.setValue("");

		} else {
			Map dato = (HashMap) listitem.getValue();

			String codigo = (String) dato.get("codigo");
			String nombre = (String) dato.get("nombre");
			tbxCodigo_prestador.setObject(dato);

			tbxCodigo_prestador.setValue(codigo);
			tbxNombre_prestador.setValue(nombre);

		}
		tbxCodigo_prestador.close();
	}

	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("procedencia", "02");


			List<Receta_rips> lista_datos = getServiceLocator()
					.getReceta_ripsService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Receta_rips receta_rips : lista_datos) {
				rowsResultado.appendChild(crearFilas(receta_rips, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
			
			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Receta_rips receta_rips = (Receta_rips) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(receta_rips.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(receta_rips.getNro_identificacion() + ""));
		fila.appendChild(new Label(paciente != null ? paciente
				.getNombreCompleto() : "Paciente no encontrado"));
		fila.appendChild(new Label(paciente != null ? paciente.getTel_res()
				: ""));
		fila.appendChild(new Label(receta_rips.getNombre_medico().toUpperCase()
				+ ""));

		Image img = new Image();
		img.setSrc("/images/vista_previa.png");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				showObservacion(receta_rips.getObservaciones());
			}
		});
		if (receta_rips.getObservaciones() != null) {
			if (!receta_rips.getObservaciones().trim().isEmpty()) {
				fila.appendChild(img);
			} else {
				fila.appendChild(new Label());
			}
		} else {
			fila.appendChild(new Label());
		}
		fila.appendChild(new Label(
				receta_rips.getLista_detalle() != null ? receta_rips
						.getLista_detalle().size() + "" : "0"));

		hbox.appendChild(space);

		img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(receta_rips);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
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
									eliminarDatos(receta_rips);
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

	protected void showObservacion(String observaciones) {
		Window window = new Window();
		window.appendChild(new Label(observaciones));
		window.setWidth("300px");
		window.setHeight("300px");
		window.setTitle("Observaciones");
		window.setClosable(true);
		window.setPage(getPage());
		window.doModal();
	}

	public void mostrarDatos(Object obj) throws Exception {
		Receta_rips receta_rips = (Receta_rips) obj;
		limpiar();
		try {

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(receta_rips.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			receta_ripsModificar = receta_rips;
			tbxNro_identificacion.setValue(receta_rips.getNro_identificacion());
			tbxNombPaciente.setValue(paciente != null ? paciente
					.getNombreCompleto() : "::No existe::");
			// tbxNro.setValue(receta_rips.getCodigo_administradora());
			dtbxFecha.setValue(receta_rips.getFecha_externa() != null ?  receta_rips.getFecha_externa() : receta_rips.getFecha());
			tbxObservaciones.setValue(receta_rips.getObservaciones());
			
			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(receta_rips.getCodigo_empresa());
			administradora.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
			administradora.setCodigo(receta_rips.getCodigo_prestador());
			administradora = getServiceLocator().getServicio(AdministradoraService.class).consultar(administradora);
			if(administradora != null){
				tbxCodigo_prestador.seleccionarRegistro(administradora, administradora.getCodigo(), administradora.getNombre()); 
			}
			// tbxNro.setValue(receta_rips.getCodigo_prestador());
			// tbxTipoId.setValue(receta_rips.getTipo_id_prestador_externo());

			lista_datos.clear();
			rowsReceta.getChildren().clear();
			List<Detalle_receta_rips> lista_detalle = receta_rips
					.getLista_detalle();
			for (Detalle_receta_rips detalle : lista_detalle) {
				Map bean = llenarBeanDetalle(detalle);
				lista_datos.add(bean);
			}

			crearFilas();
			
			// verificamos si tiene una solcitud
//			Solicitud_tecnico solicitud_tecnico = new Solicitud_tecnico();
//			solicitud_tecnico.setCodigo_empresa(receta_rips.getCodigo_empresa());
//			solicitud_tecnico.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
//			solicitud_tecnico.setCodigo_receta(receta_rips.getCodigo_receta()); 
//			this.solicitud_tecnico = getServiceLocator().getServicio(Solicitud_tecnicoService.class).consultarP(solicitud_tecnico);
//			if(this.solicitud_tecnico != null){
//				btSolicitud.setVisible(true);
//			}
			// Mostramos la vista //
			borderLayoutRegistrar.setVisible(true);
			groupboxConsulta.setVisible(false);
			((Button) getFellow("btGuardar")).setDisabled(true);
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
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
		String estado_cobro = IConstantesAutorizaciones.ESTADO_COBRO_MEDICINA_GENERAL;
		String nombre_articulo_comercial = (String) bean.get("nombre_articulo_comercial");
		String via_administracion = (String) bean.get("via_administracion"); 

		//log.info("Estado de Cobro: "
				//+ bean.containsKey("estado_cobro"));
		String estado_cobro_temp = (String) bean.get("estado_cobro");
		if (estado_cobro_temp != null) {
			estado_cobro = (String) bean.get("estado_cobro");
		} else {
			bean.put("estado_cobro", estado_cobro);
		}

		final Row fila = new Row();
		fila.setStyle("nowrap:nowrap");

		// Columna codigo //
		Cell cell = new Cell();
		cell.setAlign("left");
		Textbox tbxCodigo_procedimiento = new Textbox(codigo_articulo);
		// tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setId("codigo_articulo_" + j);
		tbxCodigo_procedimiento.setHflex("1");
		tbxCodigo_procedimiento.setInplace(true);
		tbxCodigo_procedimiento.setReadonly(true);
		cell.appendChild(tbxCodigo_procedimiento);
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Cell();
		cell.setAlign("left");
		Textbox tbxNombre_procedimiento = new Textbox(nombre_articulo);
		// tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_articulo_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setReadonly(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);
		
		
		// Columna nombre comercial //
	    cell = new Cell();
	    cell.setAlign("left");
	    tbxNombre_procedimiento = new Textbox(nombre_articulo_comercial);
	    // tbxNombre_procedimiento.setInplace(true);
	    tbxNombre_procedimiento.setId("nombre_articulo_" + j);
	    tbxNombre_procedimiento.setHflex("1");
	    tbxNombre_procedimiento.setInplace(true);
	    tbxNombre_procedimiento.setReadonly(true);
	    cell.appendChild(tbxNombre_procedimiento);
	    fila.appendChild(cell);
	    
	    cell = new Cell();
	    final Listbox lbxVia_administracion = new Listbox();
		// tbxUnidades.setInplace(true);
		lbxVia_administracion.setId("via_administracion_receta_" + j);
		lbxVia_administracion.setHflex("1");
		lbxVia_administracion.setMold("select");
		listarVia_administracion(lbxVia_administracion);
		Utilidades.seleccionarListitem(lbxVia_administracion,
				via_administracion);
		cell.appendChild(lbxVia_administracion);
		fila.appendChild(cell);
		
		lbxVia_administracion.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				String via_administracion_nueva =   ((Listbox)arg0.getTarget()).getSelectedItem().getValue();
				bean.put("via_administracion", via_administracion_nueva); 
			}
		});

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
		CampoObservacionesPopupMacro tbxPosologia = new CampoObservacionesPopupMacro(this);
		tbxPosologia.setValue(posologia); 
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
		lbxEstadoCobro.setDisabled(true);
		updateList(lbxEstadoCobro, estado_cobro);

		cell.appendChild(lbxEstadoCobro);
		// fila.appendChild(cell);

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
	
	private void listarVia_administracion(Listbox listbox) {
		listbox.setName("via_administracion_receta");
		Utilidades.listarElementoListbox(listbox, true, getServiceLocator());
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

	private void updateList(Listbox listbox, String estado_cobro) {
		for (int i = 0; i < listbox.getItemCount(); i++) {
			Listitem listitem = listbox.getItemAtIndex(i);
			if (listitem.getValue().toString().equals(estado_cobro)) {
				listitem.setSelected(true);
				break;
			}
		}
	}

	private Map llenarBeanDetalle(Detalle_receta_rips detalle) throws Exception {
		String codigo_articulo = detalle.getCodigo_articulo();
		String nombre_articulo = "";

		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(detalle.getCodigo_empresa());
		articulo.setCodigo_sucursal(detalle.getCodigo_sucursal());
		articulo.setCodigo_articulo(detalle.getCodigo_articulo());
		articulo = getServiceLocator().getArticuloService().consultar(articulo);
		nombre_articulo = (articulo != null ? articulo.getNombre1() : "*");
		String nombre_articulo_comercial = (articulo != null ? articulo.getNombre2() : "*");

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
		bean.put("nombre_articulo_comercial", nombre_articulo_comercial);
		bean.put("cantidad_recetada", cantidad_recetada);
		bean.put("valor_unitario", valor_unitario);
		bean.put("valor_total", valor_total);
		bean.put("descuento", descuento);
		bean.put("incremento", incremento);
		bean.put("valor_real", valor_real);
		bean.put("posologia", posologia);
		bean.put("autorizado", detalle.getAutorizado());
		bean.put("estado_cobro", detalle.getEstado_cobro());
		bean.put("via_administracion", detalle.getVia_administracion());
		return bean;
	}

	public void eliminar() throws Exception {
		if (receta_ripsModificar != null) {
			eliminarDatos(receta_ripsModificar);
		} else {
			MensajesUtil.mensajeAlerta("Alerta!!!",
					"No se ha guardado la Receta");
		}
	}

	public void accionBuscar() {
		borderLayoutRegistrar.setVisible(false);
		groupboxConsulta.setVisible(true);
	}

	public void accionNuevo() throws Exception {
		borderLayoutRegistrar.setVisible(true);
		groupboxConsulta.setVisible(false);
		limpiar();
		dtbxFecha.setValue(null); 
	}

	public void eliminarDatos(Object obj) throws Exception {
		Receta_rips receta_rips = (Receta_rips) obj;
		try {
			int result = getServiceLocator().getReceta_ripsService().eliminar(
					receta_rips);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			} else {
				((Button) getFellow("btGuardar")).setDisabled(false);
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

		adicionarReceta(detalle_receta_rips);
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			// setUpperCase();
			if (validarForm()) {
				final Map datos = new HashMap();

				Receta_rips receta_rips = new Receta_rips();
				if (receta_ripsModificar != null) {
					receta_rips = receta_ripsModificar;
				} else {
					receta_rips.setCodigo_receta("");
				}

				Paciente paciente = (Paciente) tbxNro_identificacion
						.getObject();
				
				Manuales_tarifarios manuales_tarifarios = (Manuales_tarifarios) tbxNro_identificacion.getAttribute("manuales_tarifarios"); 

				receta_rips.setCodigo_empresa(codigo_empresa);
				receta_rips.setCodigo_sucursal(codigo_sucursal);
				receta_rips.setNro_identificacion(tbxNro_identificacion
						.getValue());
				receta_rips.setCodigo_administradora(paciente
						.getCodigo_administradora());
				 receta_rips.setId_plan(manuales_tarifarios.getId_contrato());
				receta_rips.setNro_ingreso("0");
				receta_rips.setFecha(new Date(Calendar.getInstance().getTimeInMillis())); 
				receta_rips.setFecha_externa(new Timestamp(dtbxFecha.getValue().getTime())); 
				receta_rips.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				receta_rips.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				receta_rips.setCreacion_user(usuarios.getCodigo());
				receta_rips.setUltimo_user(usuarios.getCodigo());
				receta_rips.setEstado(false);
				receta_rips.setTipo_hc("0");
				receta_rips.setCodigo_prestador(auditor ? usuarios.getCodigo()
						: tbxCodigo_prestador.getValue());
				receta_rips.setLeido(false);
				receta_rips.setLeido_auditor("N");
				receta_rips.setProcedencia("02");
				
				receta_rips.setVigencia(parametros_empresa.getVigencia_recetas());

				Administradora administradora = (Administradora) tbxCodigo_prestador.getRegistroSeleccionado();
				
				receta_rips.setTipo_receta(IConstantes.TIPO_RECETA_AMBULATORIA);
				receta_rips.setAuditado_farmacia("N"); 
				
				String nombre = administradora.getNombre();
				receta_rips.setTipo_id_prestador_externo("");
				receta_rips.setNombre_medico(auditor ? usuarios.getApellidos()
						+ " " + usuarios.getNombres() : nombre);
				receta_rips.setObservaciones(tbxObservaciones.getValue());
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
					String via_administracion = (String) bean.get("via_administracion"); 

					if (estado_cobro != null) {
						if (!estado_cobro.trim().isEmpty()) {
							estado_cobro = "01";
						}
					}

					//log.info(":: medicamento autorizado: "
							//+ autorizado);
					//log.info(":: Estado de Cobro: " + estado_cobro);
					if (!autorizado
							&& sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV))
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
					detalle.setAutorizado(parametros_empresa
							.getSolo_afiliados() ? autorizado : true);
					detalle.setEstado_cobro(estado_cobro);
					detalle.setTiempo_tto(0); 
					detalle.setVia_administracion(via_administracion); 

					lista_detalle.add(detalle);
				}

				receta_rips
						.setContiene_med_no_autorizados(haveNoAutorizados ? "S"
								: "N");

				datos.put("receta_rips", receta_rips);
				datos.put("lista_detalle", lista_detalle);
				datos.put("accion", receta_ripsModificar != null ? "modificar"
						: "registrar");
				datos.put("solicitud_tecnico", solicitud_tecnico);
				guardarDatos(datos);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this); 
		}
	}

	public void guardarDatos(Map datos) throws Exception {
		getServiceLocator().getReceta_ripsService().guardar(datos);
		deshabilitarBoton();
		loadStateSave();
	}
//
//	private void buscarMedicamentosSolicitados(
//			List<Detalle_receta_rips> detalleRecetaRips, Map datos)
//			throws Exception {
//		Map params = new HashMap();
//		params.put("detalles", detalleRecetaRips);
//		params.put("nro_id_paciente", tbxNro_identificacion.getValue());
//		params.put("datos", datos);
//
//		Component componente = Executions.createComponents(
//				"/pages/solicitud_tecnico.zul", this, params);
//		final Window window = (Window) componente;
//		window.setWidth("100%");
//		window.setHeight("100%");
//		try {
//			window.doModal();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// window.setMaximizable(true);
//	}
	
	
	private boolean validarSolicitud() throws Exception{
		final List<Detalle_receta_rips> lista_detalle = contieneMedicamentosNoAutorizados();
		if(!lista_detalle.isEmpty()){
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
	
	private List<Detalle_receta_rips> contieneMedicamentosNoAutorizados(){
		List<Detalle_receta_rips> detalle_receta_rips = new ArrayList<Detalle_receta_rips>();
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);
			boolean autorizado = (Boolean) bean.get("autorizado");
			if(!autorizado){
				detalle_receta_rips.add(Utilidades.convertirMapDetalleReceta(bean, getSucursal()));
			}
		}
		return detalle_receta_rips;
	}
	
	private void abrirSolicitud(
			List<Detalle_receta_rips> detalleRecetaRips, Solicitud_tecnico solicitud_tecnico, boolean mostrar_msj) throws Exception {
		Map params = new HashMap();
		params.put(IConstantes.CONS_DETALLE_RECETA, detalleRecetaRips);
		params.put(IConstantes.CONS_SOLICITUD, solicitud_tecnico);
		params.put(IConstantesAutorizaciones.CONS_SOLICITUD_TECNICA, solicitud_tecnico);
		params.put(IConstantes.CONS_RECETA_EXTERNA, "_ok"); 
		params.put(IConstantes.CONS_NRO_IDENTIFICACION_PACIENTE, tbxNro_identificacion.getValue());
		if(mostrar_msj)	params.put(IConstantes.CONS_MENSAJE, "Para realizar esta opcion debes realizar la solicitud para los medicamentos no autorizados");

		Solicitud_tecnicoAction componente = (Solicitud_tecnicoAction) Executions.createComponents(
				"/pages/solicitud_tecnico.zul", this.getParent(), params);
		componente.setOnSeleccionar(new IRelacionSeleccion<Solicitud_tecnico>() {
			@Override
			public void onSeleccionar(Solicitud_tecnico solicitud_tecnico) {
				IngresoRecetasExternasAction.this.solicitud_tecnico = solicitud_tecnico;
				btSolicitud.setVisible(true);
				Clients.scrollIntoView(btSolicitud); 
				Clients.showNotification("Para abrir solicitud", btSolicitud); 
				try {
					guardarDatos(); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		final Window window = (Window) componente;
		window.setWidth("100%");
		window.setHeight("100%");
		window.doModal();
	}

	public void deshabilitarBoton() {
		((Button) getFellow("btGuardar")).setDisabled(true);
	}

	public void loadStateSave() throws Exception {
		Messagebox.show("Los datos se guardaron satisfactoriamente.",
				"Informacion", Messagebox.OK, Messagebox.INFORMATION);
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
			bean.put("cantidad_recetada", tbxCantidad_recetada.getValue());
			bean.put("valor_unitario", tbxValor_unitario.getValue());
			bean.put("valor_total", tbxValor_total.getValue());
			bean.put("posologia", tbxPosologia.getValue());
			lista_datos.set(i, bean);
		}
	}
	
	
	/* PARAMETRIZACION DE BANDBOX REGISTRO */
	
	private void parametrizarBandboxPaciente() {
		tbxNro_identificacion.inicializar(tbxNombPaciente, (Toolbarbutton)getFellow("btnLimpiarPaciente"));  
		tbxNro_identificacion
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado", 
								"limit 25 offset 0");

						return getServiceLocator().getPacienteService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {
						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro
								.getNombreCompleto());
						((BandboxRegistrosMacro)bandbox).setObject(registro);
						
						Servicios_contratados servicios_contratados = ServiciosDisponiblesUtils
								.getServiciosEspecificos(registro,
										getServiceLocator(),
										ServiciosDisponiblesUtils.CODSER_FARMACIA_MEDICAMENTOS);
						
						boolean valida = true;
						if(servicios_contratados != null){
							Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils.getManuales_tarifarios(servicios_contratados);
							if(manuales_tarifarios != null){
								bandbox.setAttribute("manuales_tarifarios", manuales_tarifarios); 
							}else{
								valida = false;
							}
						}else{
							valida = false;
						}
						if(!valida){
							MensajesUtil.mensajeAlerta("Advertencia", "El paciente con el nro de identificacion " + registro.getNro_identificacion() + " no tiene el servicio de farmacia activo, verificar en los contratos");
						}
						return valida;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						((BandboxRegistrosMacro)bandbox).setObject(null);
					}
				});
	}
	
	
	private void parametrizarBanboxAseguradora() {
		tbxCodigo_prestador.inicializar(tbxNombre_prestador, (Toolbarbutton)getFellow("btnLimpiarAseguradora")); 
		BandboxRegistrosIMG<Administradora> bandboxRegistrosIMG = new BandboxRegistrosIMG<Administradora>() {
			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Administradora registro) {
				bandbox.setValue(registro.getCodigo());
			      textboxInformacion.setValue(registro.getNombre());
			      ((BandboxRegistrosMacro)bandbox).setObject(registro);
				return true;
			}
			
			@Override
			public void renderListitem(Listitem listitem, Administradora registro) {
				listitem.appendChild(new Listcell(registro.getNit()));
				listitem.appendChild(new Listcell(registro.getCodigo()));
				listitem.appendChild(new Listcell(registro.getNombre()));
               
				
				String direccionPres = "";
				if (registro != null) {
					direccionPres += registro.getDireccion();

					Departamentos departamentos = new Departamentos();
					departamentos.setCodigo(registro.getCodigo_dpto());
					departamentos = getServiceLocator().getDepartamentosService()
							.consultar(departamentos);

					Municipios municipios = new Municipios();
					municipios.setCoddep(registro.getCodigo_dpto());
					municipios.setCodigo(registro.getCodigo_municipio());
					municipios = getServiceLocator().getMunicipiosService().consultar(
							municipios);

					if (municipios != null)
						direccionPres += " - " + municipios.getNombre();
					if (departamentos != null)
						direccionPres += " - " + departamentos.getNombre();
				}
				listitem.appendChild(new Listcell(direccionPres.toUpperCase()));
			}
			
			@Override
			public List<Administradora> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("codigo_empresa",
						empresa.getCodigo_empresa());
				parametros.put("codigo_sucursal",
						sucursal.getCodigo_sucursal());
				parametros.put("tercerizada", "S");
				parametros.put("paramTodo", "paramTodo");
				parametros.put("value", valorBusqueda);
				return getServiceLocator().getAdministradoraService()
						.listar(parametros);
			}
			
			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				((BandboxRegistrosMacro)bandbox).setObject(null);
			}
		};
		tbxCodigo_prestador.setBandboxRegistrosIMG(bandboxRegistrosIMG); 
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
				gridResultado,7);
	}

}
