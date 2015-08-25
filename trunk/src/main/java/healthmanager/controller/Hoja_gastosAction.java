/*
 * hoja_gastosAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Detalle_hoja_gasto;
import healthmanager.modelo.bean.Hoja_gastos;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.Detalle_hoja_gastoService;
import healthmanager.modelo.service.Hoja_gastosService;
import healthmanager.modelo.service.MunicipiosService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;

public class Hoja_gastosAction extends ZKWindow {

	private Hoja_gastosService hoja_gastosService;

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxAdmision;
	@View
	private Textbox tbxdirPaciente;
	@View
	private Textbox tbxtelpaciente;
	@View
	private Datebox tbxFechNacpaciente;

	@View
	private Listbox listboxRegistros;
	@View
	private Textbox tbxInfoPaciente;

	@View
	private Textbox tbxNroIngreso;

	@View
	private Toolbarbutton btnLimpiarPaciente;

	@View
	private Intbox ibxTotal;

	@View
	private Toolbarbutton btGuardar;

	@View
	private Toolbarbutton btCancel;

	@View
	private Groupbox groupboxPaciente;

	/* parametros */
	private final String ADMISION = "adson";
	private final String ADMINITRADORA = "admin";

	private Admision admision_seleccionada;

	private final String[] idsExcluyentes = {};
	private List<String> medicamentosSeleccionados = new ArrayList<String>();

	private Hoja_gastos hoja_gastos;

	// 02 es material de insumo
	private String tipo_medicamento = "02";
	// 01 para que me liste solo medicamentos
	private String tipo_medicamento2 = "01";

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		listarCombos();
		parametrizarBandBox();
		if (admision_seleccionada != null) {
			btCancel.setDisabled(true);
			((BandboxRegistrosIMG<Admision>) bandboxAdmision
					.getBandboxRegistrosIMG()).seleccionarRegistro(
					bandboxAdmision, tbxInfoPaciente, admision_seleccionada);
			bandboxAdmision.setDisabled(true);
			btnLimpiarPaciente.setVisible(false);
			groupboxPaciente.setVisible(false);
		}
	}

	private void parametrizarBandBox() {
		parametrizarBandboxAdmision();
	}

	public void listarCombos() {
		listarParameter();
	}

	@Override
	public void params(Map<String, Object> map) {
		admision_seleccionada = (Admision) map.get("admision_seleccionada");
	}

	private void parametrizarBandboxAdmision() {
		bandboxAdmision.inicializar(tbxInfoPaciente, btnLimpiarPaciente);
		bandboxAdmision
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Admision>() {

					@Override
					public void renderListitem(Listitem listitem,
							Admision registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_ingreso() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getPaciente()
								.getNombre1()
								+ " "
								+ registro.getPaciente().getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getPaciente()
								.getApellido1()
								+ " "
								+ registro.getPaciente().getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Admision> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("limite_paginado", "limit 25 offset 0");

						return getServiceLocator().getAdmisionService()
								.listarResultados(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Admision registro) {
						registro = getServiceLocator().getAdmisionService()
								.consultar(registro);
						bandbox.setValue(registro.getPaciente()
								.getTipo_identificacion()
								+ " - "
								+ registro.getNro_identificacion());
						textboxInformacion.setValue(registro.getPaciente()
								.getNombreCompleto());

						cargarHojaGasto(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						limpiarDatos();
					}
				});
	}

	private void cargarDatosPaciente(Admision admision) {
		Paciente paciente = admision.getPaciente();
		if (paciente == null) {
			paciente = new Paciente();
			paciente.setCodigo_empresa(admision.getCodigo_empresa());
			paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			if (paciente == null)
				throw new ValidacionRunTimeException(
						"Paciente no exites en la base de datos "
								+ admision.getNro_identificacion());
		}

		Departamentos departamentos = new Departamentos();
		departamentos.setCodigo(paciente.getCodigo_dpto());
		departamentos = getServiceLocator().getServicio(
				DepartamentosService.class).consultar(departamentos);

		Municipios municipios = new Municipios();
		municipios.setCoddep(paciente.getCodigo_dpto());
		municipios.setCodigo(paciente.getCodigo_municipio());
		municipios = getServiceLocator().getServicio(MunicipiosService.class)
				.consultar(municipios);

		Administradora admin = new Administradora();
		admin.setCodigo_empresa(admision.getCodigo_empresa());
		admin.setCodigo_sucursal(admision.getCodigo_sucursal());
		admin.setCodigo(admision.getCodigo_administradora());
		admin = getServiceLocator().getAdministradoraService().consultar(admin);

		String direccion = paciente.getDireccion();

		if (municipios != null) {
			direccion += ", " + municipios.getNombre();
		}

		if (departamentos != null) {
			direccion += ", " + departamentos.getNombre();
		}

		tbxdirPaciente.setText("" + direccion);
		tbxtelpaciente.setText("" + paciente.getTel_res());
		tbxFechNacpaciente.setValue(paciente.getFecha_nacimiento());

		tbxNroIngreso.setText(admision.getNro_ingreso() + " -- "
				+ (admin != null ? admin.getNombre() : ""));
		setVisibleAccionAdicionar(true);

		bandboxAdmision.setAttribute(ADMISION, admision);
		bandboxAdmision.setAttribute(ADMINITRADORA, admin);
		bandboxAdmision.seleccionarRegistro(
				admision,
				paciente.getTipo_identificacion() + " - "
						+ paciente.getNro_identificacion(),
				paciente.getNombreCompleto());

		btGuardar.setDisabled(false);
	}

	protected void cargarHojaGasto(Admision admision) {
		Hoja_gastos hoja_gastos = new Hoja_gastos();
		hoja_gastos.setCodigo_empresa(admision.getCodigo_empresa());
		hoja_gastos.setCodigo_sucursal(admision.getCodigo_sucursal());
		hoja_gastos.setNro_ingreso(admision.getNro_ingreso());
		hoja_gastos.setNro_identificacion(admision.getNro_identificacion());
		hoja_gastos = hoja_gastosService.consultar(hoja_gastos);

		if (hoja_gastos != null) {
			mostrarDatos(hoja_gastos, admision);
		} else {
			cargarDatosPaciente(admision);
		}
	}

	public void limpiarDatos() {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		bandboxAdmision.setValue("");
		bandboxAdmision.setDisabled(false);
		setVisibleAccionAdicionar(false);
		bandboxAdmision.removeAttribute(ADMISION);
		bandboxAdmision.removeAttribute(ADMINITRADORA);
		btGuardar.setDisabled(true);
		listboxRegistros.getItems().clear();
	}

	public void setVisibleAccionAdicionar(boolean visible) {
		getFellow("auxHeadAccion").setVisible(visible);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("pac.nro_identificacion ||' '|| pac.nombre1||' '||pac.nombre2 ||' '|| pac.apellido1||' '||pac.apellido2");
		listitem.setLabel("Paciente");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("hgasto.nro_ingreso");
		listitem.setLabel("Nro ingreso");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("hgasto.creacion_date::varchar");
		listitem.setLabel("Fecha(yyyy-MM-dd)");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
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

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("value", "%" + value + "%");

			hoja_gastosService.setLimit("limit 25 offset 0");

			List<Hoja_gastos> lista_datos = hoja_gastosService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Hoja_gastos hoja_gastos : lista_datos) {
				rowsResultado.appendChild(crearFilas(hoja_gastos, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Hoja_gastos hoja_gastos = (Hoja_gastos) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		Admision admision = new Admision();
		admision.setCodigo_empresa(hoja_gastos.getCodigo_empresa());
		admision.setCodigo_sucursal(hoja_gastos.getCodigo_sucursal());
		admision.setNro_identificacion(hoja_gastos.getNro_identificacion());
		admision.setNro_ingreso(hoja_gastos.getNro_ingreso());
		final Admision admisionFinal = getServiceLocator().getAdmisionService()
				.consultar(admision);

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(hoja_gastos.getCodigo_empresa());
		paciente.setCodigo_sucursal(hoja_gastos.getCodigo_sucursal());
		paciente.setNro_identificacion(hoja_gastos.getNro_identificacion());

		final Paciente pacienteFinal = getServiceLocator().getPacienteService()
				.consultar(paciente);
		String nombres_paciente = (pacienteFinal != null ? pacienteFinal
				.getNombre1() + " " + pacienteFinal.getNombre2() : "");
		String apellidos_paciente = (pacienteFinal != null ? pacienteFinal
				.getApellido1() + " " + pacienteFinal.getApellido2() : "");
		String tipo_identificacion = pacienteFinal != null ? pacienteFinal
				.getTipo_identificacion() : "";

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(admisionFinal != null ? admisionFinal
				.getNro_ingreso() : ""));
		fila.appendChild(new Label(tipo_identificacion));
		fila.appendChild(new Label(hoja_gastos.getNro_identificacion() + ""));
		fila.appendChild(new Label(apellidos_paciente));
		fila.appendChild(new Label(nombres_paciente));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(hoja_gastos.getCreacion_date()) + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(hoja_gastos, admisionFinal);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
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
									eliminarDatos(hoja_gastos);
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
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //
				Messagebox.show(
						"Esta seguro que desea guardar esta hoja de gasto?",
						"Guardar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							@SuppressWarnings("unchecked")
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									if (hoja_gastos == null) {
										hoja_gastos = getHoja_gastos();
									}

									Admision admision = bandboxAdmision
											.getRegistroSeleccionado();

									List<Map<String, Object>> listado_materiales = new ArrayList<Map<String, Object>>();

									for (Listitem listitem : listboxRegistros
											.getItems()) {
										Map<String, Object> mapa_detalle = (Map<String, Object>) listitem
												.getValue();
										Listbox listbox_bodega = (Listbox) listitem
												.getChildren().get(2)
												.getFirstChild();
										Intbox intbox_cantidad = (Intbox) listitem
												.getChildren().get(3)
												.getFirstChild();
										mapa_detalle.put("bodega",
												listbox_bodega
														.getSelectedItem()
														.getValue().toString());
										mapa_detalle
												.put("cantidad_recetada",
														intbox_cantidad
																.getValue() != null ? intbox_cantidad
																.getValue() : 0);
										listitem.setValue(mapa_detalle);
										listado_materiales.add(mapa_detalle);
									}

									Map<String, Object> map = new HashMap<String, Object>();
									map.put("accion", tbxAccion.getText());
									map.put("listado_detalle",
											listado_materiales);
									map.put("hoja_gasto", hoja_gastos);
									map.put("admision", admision);
									hoja_gastosService.guardar(map);
									tbxAccion.setValue("modificar");

									Notificaciones
											.mostrarNotificacionInformacion(
													"Informacion ..",
													"Los datos se guardaron satisfactoriamente",
													3000);
									mostrarDatos(hoja_gastos, admision);
								}
							}
						});
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	private Hoja_gastos getHoja_gastos() {
		Admision admision = bandboxAdmision.getRegistroSeleccionado();
		Hoja_gastos hoja_gastos = new Hoja_gastos();
		hoja_gastos.setCodigo_empresa(empresa.getCodigo_empresa());
		hoja_gastos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		hoja_gastos.setNro_identificacion(admision.getNro_identificacion());
		hoja_gastos.setNro_ingreso(admision.getNro_ingreso());
		hoja_gastos.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		hoja_gastos.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		hoja_gastos.setCreacion_user(usuarios.getCodigo().toString());
		hoja_gastos.setUltimo_user(usuarios.getCodigo().toString());
		return hoja_gastos;
	}

	public void imprimir() throws Exception {
		Administradora administradora = (Administradora) bandboxAdmision
				.getAttribute(ADMINITRADORA);
		Map<String, Object> paramRequest = new HashMap<String, Object>();

		Hoja_gastos hoja_gastos = new Hoja_gastos();
		hoja_gastos
				.setCodigo_empresa(admision_seleccionada.getCodigo_empresa());
		hoja_gastos.setCodigo_sucursal(admision_seleccionada
				.getCodigo_sucursal());
		hoja_gastos.setNro_ingreso(admision_seleccionada.getNro_ingreso());
		hoja_gastos.setNro_identificacion(admision_seleccionada
				.getNro_identificacion());
		hoja_gastos = hoja_gastosService.consultar(hoja_gastos);

		if (hoja_gastos != null) {
			paramRequest.put("name_report", "Hoja_gastos");
			paramRequest.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			paramRequest.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			paramRequest.put("codigo_administradora",
					administradora.getCodigo());
			paramRequest.put("codigo_empresa", codigo_empresa);
			paramRequest.put("codigo_sucursal", codigo_sucursal);

			// log.info("===> parametros" + paramRequest);

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, paramRequest);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			MensajesUtil
					.mensajeAlerta("Hoja de gasto",
							"No se ha generado la hoja de gasto por que no se ha guardado");
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Hoja_gastos hoja_gastos, Admision admision) {
		this.hoja_gastos = hoja_gastos;
		limpiarDatos();
		try {
			if (admision != null)
				cargarDatosPaciente(admision);

			Map<String, Object> paramsDetalleHoja = new HashMap<String, Object>();
			paramsDetalleHoja.put("codigo_empresa",
					hoja_gastos.getCodigo_empresa());
			paramsDetalleHoja.put("nro_ingreso", hoja_gastos.getNro_ingreso());
			paramsDetalleHoja.put("codigo_sucursal",
					hoja_gastos.getCodigo_sucursal());
			paramsDetalleHoja.put("nro_identificacion",
					hoja_gastos.getNro_identificacion());
			List<Detalle_hoja_gasto> detalle_hoja_gastos = getServiceLocator()
					.getServicio(Detalle_hoja_gastoService.class).listar(
							paramsDetalleHoja);

			/* adicionamos articuls y servicios a la hoja de gasto */
			for (Detalle_hoja_gasto detalle_hoja_gasto : detalle_hoja_gastos) {
				Map<String, Object> mapa_bean = convertirDetalleHojaAMap(detalle_hoja_gasto);
				adicionarDetalle(mapa_bean, false);
			}

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Hoja_gastos hoja_gastos = (Hoja_gastos) obj;
		try {
			int result = hoja_gastosService.eliminar(hoja_gastos);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	public void openArticulo() throws Exception {
		Administradora administradora = (Administradora) bandboxAdmision
				.getAttribute(ADMINITRADORA);
		Admision admision = bandboxAdmision.getRegistroSeleccionado();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("codigo_administradora", administradora.getCodigo());
		parametros.put("id_plan", admision.getId_plan());
		parametros.put("nro_ingreso", admision.getNro_ingreso());
		parametros.put("nro_identificacion", admision.getNro_identificacion());
		parametros.put("grupo", tipo_medicamento);
		parametros.put("grupo2", tipo_medicamento2);
		parametros.put("ocultaExist", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);
		parametros.put("seleccionados", medicamentosSeleccionados);

		Component componente = Executions.createComponents(
				"/pages/openArticulo.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR MEDICAMENTOS Y/O MATERIAL DE INSUMOS");

		ventana.setMode("modal");
	}

	@Override
	public void adicionarPcd(Map<String, Object> pcd) throws Exception {
		adicionarDetalle(pcd, true);
	}

	private void adicionarDetalle(Map<String, Object> pcd, boolean eliminar) {
		final String codigo_medicamento = pcd.get("codigo_articulo").toString();
		boolean facturable = (Boolean) pcd.get("facturable");
		medicamentosSeleccionados.add(codigo_medicamento);

		Integer cantidad_recetada = (Integer) pcd.get("cantidad_recetada");

		final Listitem listitem = new Listitem();
		listitem.setValue(pcd);

		Listcell listcell = new Listcell();
		Textbox textbox = new Textbox(codigo_medicamento);
		textbox.setInplace(true);
		textbox.setWidth("95%");
		listcell.appendChild(textbox);
		listitem.appendChild(listcell);

		listcell = new Listcell();
		textbox = new Textbox(pcd.get("nombre1").toString());
		textbox.setInplace(true);
		textbox.setWidth("95%");
		listcell.appendChild(textbox);
		listitem.appendChild(listcell);

		final Listbox listbox_bodegas = new Listbox();
		listbox_bodegas.setWidth("95%");
		listbox_bodegas.setMold("select");
		Utilidades.listarBodegasPorArticulo(codigo_medicamento,
				listbox_bodegas, this);
		listcell = new Listcell();
		listcell.appendChild(listbox_bodegas);
		listitem.appendChild(listcell);
		// Res.cargarAutomatica(listbox_bodegas, pcd, "bodega");

		final Intbox intbox_cantidad = new Intbox(
				cantidad_recetada != null ? cantidad_recetada : 1);
		intbox_cantidad.setWidth("95%");
		intbox_cantidad.setInplace(true);
		listcell = new Listcell();
		listcell.appendChild(intbox_cantidad);
		listitem.appendChild(listcell);

		listcell = new Listcell();
		textbox = new Textbox(pcd.get("tipo").equals("01") ? "MEDICAMENTOS"
				: "MATERIAL DE INSUMOS");
		textbox.setInplace(true);
		textbox.setTooltiptext(textbox.getValue());
		textbox.setWidth("95%");
		textbox.setReadonly(true);
		listcell.appendChild(textbox);
		listitem.appendChild(listcell);

		listitem.appendChild(new Listcell(facturable ? "SI" : "NO"));

		final Image image = new Image("/images/borrar.gif");
		image.setStyle("cursor:pointer");
		image.setVisible(eliminar);
		if (!eliminar) {
			pcd.put("noafectabodega", "_Bodega");
		}

		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				medicamentosSeleccionados.remove(codigo_medicamento);
				listitem.detach();
				ibxTotal.setValue(listboxRegistros.getItems().size());
			}
		});

		listcell = new Listcell();
		listcell.appendChild(image);
		listitem.appendChild(listcell);

		listboxRegistros.appendChild(listitem);
		ibxTotal.setValue(listboxRegistros.getItems().size());
	}

	private Map<String, Object> convertirDetalleHojaAMap(
			Detalle_hoja_gasto detalle_hoja_gasto) {
		Map<String, Object> map = new HashMap<String, Object>();

		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(detalle_hoja_gasto.getCodigo_empresa());
		articulo.setCodigo_sucursal(detalle_hoja_gasto.getCodigo_sucursal());
		articulo.setCodigo_articulo(detalle_hoja_gasto.getCodigo_articulo());
		articulo = getServiceLocator().getArticuloService().consultar(articulo);

		map.put("codigo_empresa", empresa.getCodigo_empresa());
		map.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		map.put("codigo_articulo", detalle_hoja_gasto.getCodigo_articulo());
		map.put("valor_unitario", detalle_hoja_gasto.getValor_unitario());
		map.put("valor_total", detalle_hoja_gasto.getValor_unitario()
				* detalle_hoja_gasto.getCantidad());
		map.put("descuento", detalle_hoja_gasto.getDescuento());
		map.put("incremento", detalle_hoja_gasto.getIncremento());
		map.put("valor_real", detalle_hoja_gasto.getValor_real());
		map.put("copago", "N");
		map.put("cantidad_recetada", detalle_hoja_gasto.getCantidad());
		map.put("autorizado", "S");
		map.put("tipo", articulo != null ? articulo.getGrupo1() : "");
		map.put("nombre1", articulo != null ? articulo.getNombre1() : "");
		map.put("facturable", articulo != null ? articulo.getFacturable()
				: false);
		map.put("consecutivo", detalle_hoja_gasto.getConsecutivo());
		return map;
	}

}