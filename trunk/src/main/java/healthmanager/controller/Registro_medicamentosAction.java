/*
 * notas_enfermeriaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Notas_enfermeria;
import healthmanager.modelo.bean.Ordenes_medicas;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Registro_medicamentos;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Ordenes_medicasService;
import healthmanager.modelo.service.Registro_medicamentosService;
import healthmanager.modelo.service.UsuariosService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.service.ArticuloService;

public class Registro_medicamentosAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Registro_medicamentosAction.class);

	private Ordenes_medicasService ordenes_medicasService;
	private UsuariosService usuariosService;
	private Registro_medicamentosService registro_medicamentosService;
	private ArticuloService articuloService;
	private ElementoService elementoService;

	private String rol_medico;
	private Admision admision_seleccionada;

	private SimpleDateFormat formato_fecha = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	// Componentes //
	@View
	private Datebox dtbxFecha_orden_b;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Grid gridResultado;
	@View
	private Rows rowsResultado;

	@View
	private Textbox tbxCodigo_registro;
	@View
	private Datebox dtbxFecha_registro;
	@View
	private Checkbox chbAplico_medicamento;
	@View
	private Datebox dtbxFecha_aplicacion;
	@View
	private Textbox tbxCodigo_enfermera;
	@View
	private Textbox tbxInformacion_enfermera;

	@View
	private Textbox tbxMedicamento;

	@View
	private Textbox tbxVia_administracion;

	@View
	private Textbox tbxInformacion_medicamento;

	@View
	private Column columnaOpciones;

	private Detalle_receta_rips detalle_receta_rips_seleccionado = null;
	private Articulo articulo_seleccionado = null;

	private final String[] idsExcluyentes = {};
	
	protected Ordenes_medicas ordenes_medicas_seleccionada;

	@Override
	public void init() {
		//log.info("rol_medico ===> " + rol_medico);
		if (rol_medico != null && rol_medico.equals("S")) {
			columnaOpciones.setVisible(false);
		} else {
			columnaOpciones.setVisible(true);
		}
		listarCombos();
		dtbxFecha_aplicacion.setValue(Calendar.getInstance().getTime()); 
	}

	public void onCreateNotas() throws Exception {
		if (rol_medico != null && rol_medico.equals("S")) {
			buscarDatos();
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey("rol_medico")) {
			rol_medico = (String) map.get("rol_medico");
		}
		admision_seleccionada = (Admision) map.get("admision_seleccionada");
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			limpiarDatos();
			tbxCodigo_enfermera.setValue(usuarios.getCodigo());
			tbxInformacion_enfermera.setValue(usuarios.getNombres() + " "
					+ usuarios.getApellidos());
			tbxMedicamento.setValue(articulo_seleccionado.getNombre1().trim());
			Elemento elemento_via = new Elemento();
			elemento_via.setCodigo(detalle_receta_rips_seleccionado
					.getVia_administracion());
			elemento_via.setTipo("via_administracion_receta");
			elemento_via = elementoService.consultar(elemento_via);

			if (elemento_via != null)
				tbxVia_administracion.setValue(elemento_via.getDescripcion());

			tbxInformacion_medicamento.setValue("Cantidad: "
					+ detalle_receta_rips_seleccionado.getCantidad_recetada()
					+ ", DF: "
					+ detalle_receta_rips_seleccionado.getPosologia());

		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		chbAplico_medicamento.setChecked(false);
		dtbxFecha_aplicacion.setValue(new Date());
		dtbxFecha_aplicacion.setDisabled(true);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;
		String mensaje = "";

		if (!chbAplico_medicamento.isChecked()) {
			mensaje = "La aplicacion del medicamento es obligatorio para guardar el registro";
			valida = false;
		} else if (ordenes_medicas_seleccionada != null
				&& dtbxFecha_aplicacion.getValue().compareTo(
						ordenes_medicas_seleccionada.getFecha_orden()) < 0) {
			mensaje = "La fecha de aplicacion del medicamento debe ser mayor o igual que la fecha de ordenamiento del medico: "
					+ formato_fecha.format(ordenes_medicas_seleccionada.getFecha_orden()); 
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa",
					admision_seleccionada.getCodigo_empresa());
			parameters.put("codigo_sucursal",
					admision_seleccionada.getCodigo_sucursal());
			parameters.put("nro_documento",
					admision_seleccionada.getNro_identificacion());
			parameters.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parameters.put("codigo_centro",
					admision_seleccionada.getCodigo_centro());
			parameters.put("tipo",
					"2");
			if (dtbxFecha_orden_b.getValue() != null)
				parameters.put("fecha_orden", new Timestamp(dtbxFecha_orden_b
						.getValue().getTime()));

			ordenes_medicasService.setLimit("limit 25 offset 0");

			List<Ordenes_medicas> lista_datos = ordenes_medicasService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Ordenes_medicas ordenes_medicas : lista_datos) {
				if (ordenes_medicas.getCodigo_receta() != null
						&& !ordenes_medicas.getCodigo_receta().isEmpty()) {
					Receta_rips receta_rips = new Receta_rips();
					receta_rips.setCodigo_empresa(codigo_empresa);
					receta_rips.setCodigo_sucursal(codigo_sucursal);
					receta_rips.setCodigo_receta(ordenes_medicas
							.getCodigo_receta());
					receta_rips = getServiceLocator().getReceta_ripsService()
							.consultar(receta_rips);

					List<Detalle_receta_rips> listado_detalles = receta_rips
							.getLista_detalle();

					for (Detalle_receta_rips detalle_receta_rips : listado_detalles) {
						rowsResultado.appendChild(crearFilas(ordenes_medicas,
								detalle_receta_rips, this));
					}

				}
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

	public Row crearFilas(final Ordenes_medicas ordenes_medicas,
			final Detalle_receta_rips detalle_receta_rips, Component componente)
			throws Exception {
		Row fila = new Row();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_receta", detalle_receta_rips.getCodigo_receta());
		parametros.put("consecutivo", detalle_receta_rips.getConsecutivo());
		parametros.put("nro_documento", ordenes_medicas.getNro_documento());
		parametros.put("nro_ingreso", ordenes_medicas.getNro_ingreso());

		List<Registro_medicamentos> listado_registros = registro_medicamentosService
				.listar(parametros);

		if (!listado_registros.isEmpty()) {
			Detail detalles_registros = new Detail();
			Listbox listbox_registros = new Listbox();
			listbox_registros.setStyle("margin:5px");

			Listhead listhead = new Listhead();

			Listheader listheader = new Listheader("Descripcion del registro");
			listhead.appendChild(listheader);

			listheader = new Listheader("Acciones");
			listheader.setWidth("70px");
			listhead.appendChild(listheader);

			if (rol_medico != null && rol_medico.equals("S")) {
				listheader.setVisible(false);
			} else {
				listheader.setVisible(true);
			}

			listbox_registros.appendChild(listhead);

			for (final Registro_medicamentos registro_medicamentos : listado_registros) {
				Listitem listitem = new Listitem();

				Usuarios usuarios_enf = new Usuarios();
				usuarios_enf.setCodigo_empresa(codigo_empresa);
				usuarios_enf.setCodigo_sucursal(codigo_sucursal);
				usuarios_enf.setCodigo(registro_medicamentos
						.getCodigo_enfermera());

				usuarios_enf = usuariosService.consultar(usuarios_enf);
				
				String descripcion = (
						usuarios_enf != null ? usuarios_enf.getNombres() + " "
								+ usuarios_enf.getApellidos()
								: registro_medicamentos.getCodigo_enfermera())+
								" aplicó a las "+
								formato_fecha
								.format(registro_medicamentos.getFecha_aplicacion());

				listitem.appendChild(new Listcell(descripcion));

				Image img = new Image();
				img.setSrc("/images/borrar.gif");
				img.setTooltiptext("Eliminar");
				img.setStyle("cursor: pointer");
				img.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Messagebox
										.show("Esta seguro que desea eliminar este registro? ",
												"Eliminar Registro",
												Messagebox.YES + Messagebox.NO,
												Messagebox.QUESTION,
												new org.zkoss.zk.ui.event.EventListener<Event>() {
													public void onEvent(
															Event event)
															throws Exception {
														if ("onYes".equals(event
																.getName())) {
															// do the thing
															eliminarDatos(registro_medicamentos);
															buscarDatos();
														}
													}
												});
							}
						});

				if (usuarios.getCodigo().equals(registro_medicamentos.getCodigo_enfermera())) {
					Listcell listcell = new Listcell();
					listcell.appendChild(img);
					listitem.appendChild(listcell);
				} else {
					listitem.appendChild(new Listcell("---"));
				}

				listbox_registros.appendChild(listitem);

			}

			detalles_registros.appendChild(listbox_registros);

			fila.appendChild(detalles_registros);
		} else {
			fila.appendChild(new Label());
		}

		fila.appendChild(new Label(ordenes_medicas.getCodigo_orden()));
		fila.appendChild(new Label("Urgencias"));
		fila.appendChild(new Label(formato_fecha.format(ordenes_medicas
				.getFecha_orden())));

		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(codigo_empresa);
		articulo.setCodigo_sucursal(codigo_sucursal);
		articulo.setCodigo_articulo(detalle_receta_rips.getCodigo_articulo());
		articulo = articuloService.consultar(articulo);

		if (articulo != null) {
			fila.appendChild(new Label(articulo.getNombre1().trim()));
		} else {
			fila.appendChild(new Label(detalle_receta_rips.getCodigo_articulo()));
		}

		fila.appendChild(new Label(detalle_receta_rips.getCantidad_recetada()
				+ ""));

		Elemento elemento_via = new Elemento();
		elemento_via.setCodigo(detalle_receta_rips.getVia_administracion());
		elemento_via.setTipo("via_administracion_receta");
		elemento_via = elementoService.consultar(elemento_via);

		if (elemento_via != null) {
			fila.appendChild(new Label(elemento_via.getDescripcion()));
		} else {
			fila.appendChild(new Label(detalle_receta_rips
					.getVia_administracion()));
		}

		Toolbarbutton toolbarbutton = new Toolbarbutton("Nuevo",
				"/images/New16.gif");
		toolbarbutton.setTooltiptext("Nuevo registro de medicamento");
		fila.appendChild(toolbarbutton);

		final Articulo articulo2 = articulo;

		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						detalle_receta_rips_seleccionado = detalle_receta_rips;
						articulo_seleccionado = articulo2;
						ordenes_medicas_seleccionada = ordenes_medicas; 
						accionForm(true, "registrar");
					}
				});

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //
				Registro_medicamentos registro_medicamentos = new Registro_medicamentos();
				registro_medicamentos.setCodigo_empresa(empresa
						.getCodigo_empresa());
				registro_medicamentos.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				registro_medicamentos.setCodigo_registro(tbxCodigo_registro
						.getValue());
				registro_medicamentos
						.setCodigo_receta(detalle_receta_rips_seleccionado
								.getCodigo_receta());
				registro_medicamentos
						.setConsecutivo(detalle_receta_rips_seleccionado
								.getConsecutivo());

				registro_medicamentos.setNro_ingreso(admision_seleccionada
						.getNro_ingreso());
				registro_medicamentos.setNro_documento(admision_seleccionada
						.getNro_identificacion());

				registro_medicamentos.setFecha_registro(new Timestamp(
						dtbxFecha_registro.getValue().getTime()));
				registro_medicamentos.setFecha_aplicacion(new Timestamp(
						dtbxFecha_aplicacion.getValue().getTime()));
				registro_medicamentos.setCodigo_enfermera(tbxCodigo_enfermera
						.getValue());
				registro_medicamentos.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				registro_medicamentos.setCreacion_user(usuarios.getCodigo()
						.toString());

				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("registro_medicamentos", registro_medicamentos);
				mapa_datos.put("accion", tbxAccion.getValue());

				registro_medicamentosService.guardarDatos(mapa_datos);
				accionForm(true, "modificar");
				tbxCodigo_registro.setValue(registro_medicamentos
						.getCodigo_registro());
				dtbxFecha_registro.setValue(registro_medicamentos
						.getFecha_registro());

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Notas_enfermeria notas_enfermeria = (Notas_enfermeria) obj;
		try {
			tbxCodigo_registro.setValue(notas_enfermeria.getCodigo_nota());
			dtbxFecha_registro.setValue(notas_enfermeria.getFecha_nota());
			tbxCodigo_enfermera
					.setValue(notas_enfermeria.getCodigo_enfermera());
			Usuarios usuarios_enf = new Usuarios();
			usuarios_enf
					.setCodigo_empresa(notas_enfermeria.getCodigo_empresa());
			usuarios_enf.setCodigo_sucursal(notas_enfermeria
					.getCodigo_sucursal());
			usuarios_enf.setCodigo(notas_enfermeria.getCodigo_enfermera());

			usuarios_enf = usuariosService.consultar(usuarios_enf);

			if (usuarios_enf != null) {
				tbxInformacion_enfermera.setValue(usuarios_enf.getNombres()
						+ " " + usuarios_enf.getApellidos());
			}
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Registro_medicamentos registro_medicamentos = (Registro_medicamentos) obj;
		try {
			int result = registro_medicamentosService
					.eliminar(registro_medicamentos);
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

	public void imprimirRegistros() {
		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("name_report", "Registro_medicamentos");
			parametros.put("formato", "pdf");
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("nro_documento",
					admision_seleccionada.getNro_identificacion());

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, parametros);
			final Window window = (Window) componente;

			if (!window.hasAttribute("LISTADO_REGISTROS_VACIO")) {
				window.setMode("modal");
				window.setMaximizable(true);
				window.setMaximized(true);
			} else {
				window.detach();
			}

		} catch (HealthmanagerException e) {
			MensajesUtil.mensajeError(e, e.getMessage(), this);
		}
	}

}