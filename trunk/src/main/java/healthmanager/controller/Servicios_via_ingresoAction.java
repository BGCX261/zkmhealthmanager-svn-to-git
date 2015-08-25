/*
 * servicios_via_ingresoAction.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Servicios_disponibles;
import healthmanager.modelo.bean.Servicios_via_ingreso;
import healthmanager.modelo.service.Servicios_disponiblesService;
import healthmanager.modelo.service.Servicios_via_ingresoService;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.XulElement;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Servicios_via_ingresoAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Servicios_via_ingresoAction.class);

	private Servicios_via_ingresoService servicios_via_ingresoService;

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
	private Listbox listboxResultado;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxServicio;
	@View
	private Textbox tbxNombre_servicio;
	@View
	private Toolbarbutton btnLimpiarServicio;
	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxVias;
	@View
	private Textbox tbxNombre_via;
	@View
	private Toolbarbutton btnLimpiarVia;

	@View
	private Longbox lgxCodigo;

	private final String[] idsExcluyentes = { "lgxCodigo" };

	@Override
	public void init() {
		listarCombos();
		parametrizarBandbox();
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_servicio_like");
		listitem.setLabel("Codigo de servicio");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre_servicio");
		listitem.setLabel("Nombre de servicio");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("via_ingreso_like");
		listitem.setLabel("Via de ingreso");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre_via_ingreso");
		listitem.setLabel("Nombre de via de ingreso");
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
			lgxCodigo.setValue(null);
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;
		XulElement xulElement = null;
		if (bandboxServicio.getValue().trim().isEmpty()) {
			MensajesUtil.notificarError(
					"El campo Codigo servicio es obligatorio", bandboxServicio);
			valida = false;
			if (xulElement == null)
				xulElement = bandboxServicio;
		}
		if (bandboxVias.getValue().trim().isEmpty()) {
			MensajesUtil.notificarError("El campo Via ingreso es obligatorio",
					bandboxVias);
			valida = false;
			if (xulElement == null)
				xulElement = bandboxVias;
		}

		if (xulElement != null) {
			Clients.scrollIntoView(xulElement);
			xulElement.setFocus(true);
		}
		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			String value = tbxValue.getValue().trim().toUpperCase();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			if (!value.trim().isEmpty())
				parameters.put(parameter, value.trim().toUpperCase());

			List<Servicios_via_ingreso> lista_datos = servicios_via_ingresoService
					.listar(parameters);
			listboxResultado.getItems().clear();

			for (Servicios_via_ingreso servicios_via_ingreso : lista_datos) {
				listboxResultado.appendChild(crearFilas(servicios_via_ingreso,
						this));
			}

			listboxResultado.applyProperties();
			listboxResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente)
			throws Exception {
		Listitem fila = new Listitem();

		final Servicios_via_ingreso servicios_via_ingreso = (Servicios_via_ingreso) objeto;

		Servicios_disponibles servicios_disponibles = new Servicios_disponibles();
		servicios_disponibles.setCodigo(servicios_via_ingreso
				.getCodigo_servicio());
		servicios_disponibles = getServiceLocator().getServicio(
				Servicios_disponiblesService.class).consultar(
				servicios_disponibles);

		fila.appendChild(new Listcell(servicios_via_ingreso
				.getCodigo_servicio()));
		fila.appendChild(new Listcell(
				servicios_disponibles != null ? servicios_disponibles
						.getNombre() : ""));

		Elemento elemento = new Elemento();
		elemento.setCodigo(servicios_via_ingreso.getVia_ingreso());
		elemento.setTipo("via_ingreso");
		elemento = getServiceLocator().getElementoService().consultar(elemento);

		fila.appendChild(new Listcell(servicios_via_ingreso.getVia_ingreso()));

		fila.appendChild(new Listcell(elemento != null ? elemento
				.getDescripcion() : ""));

		Hlayout hlayout = new Hlayout();
		fila.setStyle("text-align: justify;nowrap:nowrap");
		Toolbarbutton toolbarbutton = new Toolbarbutton("Editar");
		toolbarbutton.setImage("/images/editar.gif");
		toolbarbutton.setTooltiptext("Editar registro");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						mostrarDatos(servicios_via_ingreso);
					}
				});
		hlayout.appendChild(toolbarbutton);
		toolbarbutton = new Toolbarbutton("Eliminar");
		toolbarbutton.setImage("/images/borrar.gif");
		toolbarbutton.setTooltiptext("Eliminar registro");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("Esta seguro que desea eliminar este registro? ",
										"Eliminar Registro",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													eliminarDatos(servicios_via_ingreso);
													buscarDatos();
												}
											}
										});
					}
				});
		hlayout.appendChild(new Space());
		hlayout.appendChild(toolbarbutton);

		Listcell listcell = new Listcell();
		listcell.appendChild(hlayout);
		fila.appendChild(listcell);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			//log.info("ejecutando metodo guardarDatos()");
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //

				Servicios_via_ingreso servicios_via_ingreso = new Servicios_via_ingreso();
				servicios_via_ingreso.setCodigo_registro(lgxCodigo.getValue());
				servicios_via_ingreso.setCodigo_empresa(codigo_empresa);
				servicios_via_ingreso.setCodigo_sucursal(codigo_sucursal);
				servicios_via_ingreso.setCodigo_servicio(bandboxServicio
						.getValue());
				servicios_via_ingreso.setVia_ingreso(bandboxVias.getValue());
				servicios_via_ingreso.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				servicios_via_ingreso.setCreacion_user(usuarios.getCodigo()
						.toString());
				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("servicios_via_ingreso", servicios_via_ingreso);
				mapa_datos.put("accion", tbxAccion.getValue());
				servicios_via_ingresoService.guardarDatos(mapa_datos);
				lgxCodigo.setValue(servicios_via_ingreso.getCodigo_registro());
				tbxAccion.setValue("modificar");
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	private void parametrizarBandbox() {
		parametrizarBandboxServicio();
		parametrizarBandboxVias();
	}

	private void parametrizarBandboxServicio() {
		bandboxServicio.inicializar(tbxNombre_servicio, btnLimpiarServicio);
		bandboxServicio
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Servicios_disponibles>() {

					@Override
					public void renderListitem(Listitem listitem,
							Servicios_disponibles registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro.getCodigo()
								+ ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre()
								+ ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getCodigo_procedimiento()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Servicios_disponibles> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", "");
						parametros.put("via_ingreso", true);
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado",
								"limit 25 offset 0");

						return getServiceLocator().getServicio(
								Servicios_disponiblesService.class).listar(
								parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion,
							Servicios_disponibles registro) {

						// Esta validacion aplica cuando trabaja como la Caja
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNombre()
								+ " "
								+ (registro.getCodigo_procedimiento() != null ? registro
										.getCodigo_procedimiento() : ""));

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	private void parametrizarBandboxVias() {
		bandboxVias.inicializar(tbxNombre_via, btnLimpiarVia);
		bandboxVias.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Elemento>() {

			@Override
			public void renderListitem(Listitem listitem, Elemento registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getDescripcion() + ""));
				listitem.appendChild(listcell);

			}

			@Override
			public List<Elemento> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("tipo", "via_ingreso");
				parametros.put("parametroTodo", valorBusqueda.toLowerCase());

				return getServiceLocator().getElementoService().listar(
						parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Elemento registro) {

				// Esta validacion aplica cuando trabaja como la Caja
				bandbox.setValue(registro.getCodigo());
				textboxInformacion.setValue(registro.getDescripcion());

				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}
		});
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Servicios_via_ingreso servicios_via_ingreso = (Servicios_via_ingreso) obj;
		try {
			lgxCodigo.setValue(servicios_via_ingreso.getCodigo_registro());
			Elemento elemento = new Elemento();
			elemento.setCodigo(servicios_via_ingreso.getVia_ingreso());
			elemento.setTipo("via_ingreso");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);
			bandboxVias.seleccionarRegistro(elemento, servicios_via_ingreso
					.getVia_ingreso(),
					elemento != null ? elemento.getDescripcion() : "");

			Servicios_disponibles servicios_disponibles = new Servicios_disponibles();
			servicios_disponibles.setCodigo(servicios_via_ingreso
					.getCodigo_servicio());
			servicios_disponibles = getServiceLocator().getServicio(
					Servicios_disponiblesService.class).consultar(
					servicios_disponibles);

			bandboxServicio
					.seleccionarRegistro(
							servicios_disponibles,
							servicios_via_ingreso.getCodigo_servicio(),
							servicios_disponibles != null ? ((servicios_disponibles
									.getCodigo_procedimiento() != null ? servicios_disponibles
									.getCodigo_procedimiento() : "")
									+ " " + servicios_disponibles.getNombre())
									: "");

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Servicios_via_ingreso servicios_via_ingreso = (Servicios_via_ingreso) obj;
		try {
			int result = servicios_via_ingresoService
					.eliminar(servicios_via_ingreso);
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
}