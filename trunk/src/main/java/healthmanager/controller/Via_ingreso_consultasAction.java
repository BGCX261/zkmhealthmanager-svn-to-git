/*
 * via_ingreso_consultasAction.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Via_ingreso_consultas;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Via_ingreso_consultasService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.macros.macro_row.BandBoxRowMacro.IConfiguracionBandbox;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Via_ingreso_consultasAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Via_ingreso_consultasAction.class);

	private Via_ingreso_consultasService via_ingreso_consultasService;

	private IConfiguracionBandbox<Elemento> iconfiguracionbandbox_via_ingreso;

	private IConfiguracionBandbox<Procedimientos> iconfiguracionbandbox_procedimientos;

	@View
	private Listbox listboxResultado;

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {

	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		List<Listitem> listado_listitems = listboxResultado.getItems();
		if (!listado_listitems.isEmpty()) {
			for (Listitem listitem : listado_listitems) {
				BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) listitem
						.getChildren().get(0).getFirstChild();

				if (bandBoxRowMacro.getRegistroSeleccionado() == null) {
					valida = false;
					bandBoxRowMacro.setFocus(true);
					MensajesUtil.notificarError(
							"El campo Via ingreso es obligatorio",
							bandBoxRowMacro);
					break;
				}

			}
		}
		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);

			List<Via_ingreso_consultas> listado_datos = via_ingreso_consultasService
					.listar(parametros);

			listboxResultado.getItems().clear();

			for (Via_ingreso_consultas via_ingreso_consultas : listado_datos) {
				crearFilas(via_ingreso_consultas);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void crearFilas(final Via_ingreso_consultas via_ingreso_consultas)
			throws Exception {
		final Listitem listitem_fila = new Listitem();

		Listcell listcell = new Listcell();

		final BandBoxRowMacro bandBoxRowMacro = new BandBoxRowMacro();

		bandBoxRowMacro.setHflex("1");

		bandBoxRowMacro.configurar(iconfiguracionbandbox_via_ingreso);

		if (via_ingreso_consultas != null) {
			Elemento elemento = new Elemento();
			elemento.setCodigo(via_ingreso_consultas.getVia_ingreso());
			elemento.setTipo("via_ingreso");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);
			bandBoxRowMacro.seleccionarRegistro(elemento,
					elemento.getDescripcion());
		}

		listcell.appendChild(bandBoxRowMacro);

		listitem_fila.appendChild(listcell);

		BandBoxRowMacro bandBoxRowMacroPro = new BandBoxRowMacro();
		bandBoxRowMacroPro.setHflex("1");
		bandBoxRowMacroPro.configurar(iconfiguracionbandbox_procedimientos);

		Procedimientos procedimientos;
		if (via_ingreso_consultas != null
				&& via_ingreso_consultas.getPro_consulta_primera() != null
				&& !via_ingreso_consultas.getPro_consulta_primera().isEmpty()) {
			procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(via_ingreso_consultas
					.getPro_consulta_primera()));
			procedimientos = getServiceLocator().getProcedimientosService()
					.consultar(procedimientos);
			bandBoxRowMacroPro.seleccionarRegistro(procedimientos,
					procedimientos != null ? procedimientos.getCodigo_cups()
							+ " " + procedimientos.getDescripcion() : "");
		}
		listcell = new Listcell();
		listcell.appendChild(bandBoxRowMacroPro);
		listitem_fila.appendChild(listcell);

		bandBoxRowMacroPro = new BandBoxRowMacro();
		bandBoxRowMacroPro.setHflex("1");
		bandBoxRowMacroPro.configurar(iconfiguracionbandbox_procedimientos);

		if (via_ingreso_consultas != null
				&& via_ingreso_consultas.getPro_consulta_control() != null
				&& !via_ingreso_consultas.getPro_consulta_control().isEmpty()) {
			procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(via_ingreso_consultas
					.getPro_consulta_control()));
			procedimientos = getServiceLocator().getProcedimientosService()
					.consultar(procedimientos);
			bandBoxRowMacroPro.seleccionarRegistro(procedimientos,
					procedimientos != null ? procedimientos.getCodigo_cups()
							+ " " + procedimientos.getDescripcion() : "");
		}
		listcell = new Listcell();
		listcell.appendChild(bandBoxRowMacroPro);
		listitem_fila.appendChild(listcell);

		bandBoxRowMacroPro = new BandBoxRowMacro();
		bandBoxRowMacroPro.setHflex("1");
		bandBoxRowMacroPro.configurar(iconfiguracionbandbox_procedimientos);

		if (via_ingreso_consultas != null
				&& via_ingreso_consultas.getPro_enfermeria_primera() != null
				&& !via_ingreso_consultas.getPro_enfermeria_primera().isEmpty()) {
			procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(via_ingreso_consultas
					.getPro_enfermeria_primera()));
			procedimientos = getServiceLocator().getProcedimientosService()
					.consultar(procedimientos);
			bandBoxRowMacroPro.seleccionarRegistro(procedimientos,
					procedimientos != null ? procedimientos.getCodigo_cups()
							+ " " + procedimientos.getDescripcion() : "");
		}
		listcell = new Listcell();
		listcell.appendChild(bandBoxRowMacroPro);
		listitem_fila.appendChild(listcell);

		bandBoxRowMacroPro = new BandBoxRowMacro();
		bandBoxRowMacroPro.setHflex("1");
		bandBoxRowMacroPro.configurar(iconfiguracionbandbox_procedimientos);

		if (via_ingreso_consultas != null
				&& via_ingreso_consultas.getPro_enfermeria_control() != null
				&& !via_ingreso_consultas.getPro_enfermeria_control().isEmpty()) {
			procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(via_ingreso_consultas
					.getPro_enfermeria_control()));
			procedimientos = getServiceLocator().getProcedimientosService()
					.consultar(procedimientos);
			bandBoxRowMacroPro.seleccionarRegistro(procedimientos,
					procedimientos != null ? procedimientos.getCodigo_cups()
							+ " " + procedimientos.getDescripcion() : "");
		}
		listcell = new Listcell();
		listcell.appendChild(bandBoxRowMacroPro);
		listitem_fila.appendChild(listcell);

		Listbox listbox = new Listbox();
		listbox.setMold("select");
		listbox.setName("finalidad_consulta");

		Utilidades.listarElementoListbox(listbox, true, getServiceLocator());

		if (via_ingreso_consultas != null
				&& !via_ingreso_consultas.getFinalidad().isEmpty()) {
			Utilidades.seleccionarListitem(listbox,
					via_ingreso_consultas.getFinalidad());
		}
		listbox.setHflex("1");
		listcell = new Listcell();
		listcell.appendChild(listbox);
		listitem_fila.appendChild(listcell);

		Hlayout hlayout = new Hlayout();
		listitem_fila.setStyle("text-align: justify;nowrap:nowrap");
		Toolbarbutton toolbarbutton = new Toolbarbutton("Eliminar");
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
													eliminarDatos(listitem_fila);
												}
											}
										});
					}
				});
		hlayout.appendChild(toolbarbutton);
		listcell = new Listcell();
		listcell.appendChild(hlayout);
		listitem_fila.appendChild(listcell);

		listboxResultado.appendChild(listitem_fila);

	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			//log.info("ejecutando metodo @guardarDatos()");
			if (validarForm()) {
				List<Listitem> listado_items = listboxResultado.getItems();
				if (!listado_items.isEmpty()) {
					List<Via_ingreso_consultas> listado_datos = new ArrayList<Via_ingreso_consultas>();
					for (Listitem listitem : listado_items) {
						Via_ingreso_consultas via_ingreso_consultas = new Via_ingreso_consultas();
						via_ingreso_consultas.setCodigo_empresa(codigo_empresa);
						via_ingreso_consultas
								.setCodigo_sucursal(codigo_sucursal);
						via_ingreso_consultas.setCreacion_date(new Timestamp(
								new Date().getTime()));
						via_ingreso_consultas.setCreacion_user(usuarios
								.getCodigo());

						BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) listitem
								.getChildren().get(1).getFirstChild();

						via_ingreso_consultas
								.setPro_consulta_primera(bandBoxRowMacro
										.getRegistroSeleccionado() != null ? ((Procedimientos) bandBoxRowMacro
										.getRegistroSeleccionado())
										.getId_procedimiento()
										+ "" : "");

						bandBoxRowMacro = (BandBoxRowMacro) listitem
								.getChildren().get(2).getFirstChild();

						via_ingreso_consultas
								.setPro_consulta_control(bandBoxRowMacro
										.getRegistroSeleccionado() != null ? ((Procedimientos) bandBoxRowMacro
										.getRegistroSeleccionado())
										.getId_procedimiento()
										+ "" : "");

						bandBoxRowMacro = (BandBoxRowMacro) listitem
								.getChildren().get(3).getFirstChild();

						via_ingreso_consultas
								.setPro_enfermeria_primera(bandBoxRowMacro
										.getRegistroSeleccionado() != null ? ((Procedimientos) bandBoxRowMacro
										.getRegistroSeleccionado())
										.getId_procedimiento()
										+ "" : "");

						bandBoxRowMacro = (BandBoxRowMacro) listitem
								.getChildren().get(4).getFirstChild();

						via_ingreso_consultas
								.setPro_enfermeria_control(bandBoxRowMacro
										.getRegistroSeleccionado() != null ? ((Procedimientos) bandBoxRowMacro
										.getRegistroSeleccionado())
										.getId_procedimiento()
										+ "" : "");

						via_ingreso_consultas.setFinalidad(((Listbox) listitem
								.getChildren().get(5).getFirstChild())
								.getSelectedItem().getValue().toString());

						bandBoxRowMacro = (BandBoxRowMacro) listitem
								.getChildren().get(0).getFirstChild();

						via_ingreso_consultas
								.setVia_ingreso(((Elemento) bandBoxRowMacro
										.getRegistroSeleccionado()).getCodigo());

						listado_datos.add(via_ingreso_consultas);
					}

					Map<String, Object> mapa_datos = new HashMap<String, Object>();
					mapa_datos.put("codigo_empresa", codigo_empresa);
					mapa_datos.put("codigo_sucursal", codigo_sucursal);
					mapa_datos.put("listado_datos", listado_datos);

					via_ingreso_consultasService.guardarDatos(mapa_datos);
					MensajesUtil.mensajeInformacion("Inforamcion",
							"Se han guardado los datos satisfactoriamente");
				} else {
					MensajesUtil.mensajeInformacion("Informacion",
							"NO hay registros seleccionados para guardar");
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	public void inicializamosEvento() {
		iconfiguracionbandbox_via_ingreso = new IConfiguracionBandbox<Elemento>() {

			@Override
			public void onSeleccionar(Elemento t, Bandbox bandbox) {

			}

			@Override
			public String getCabecera(Bandbox bandbox) {
				return "Codigo#70px|Via de ingreso";
			}

			@Override
			public String getWidthListBox() {
				return "350px";
			}

			@Override
			public String getHeightListBox() {
				return "200px";
			}

			@Override
			public void renderListitem(Listitem listitem, Elemento registro,
					Bandbox bandbox) {
				listitem.appendChild(new Listcell(registro.getCodigo()));
				listitem.appendChild(new Listcell(""
						+ registro.getDescripcion()));
			}

			@Override
			public List<Elemento> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros, Bandbox bandbox) {
				parametros.put("parametroTodo", valorBusqueda);
				parametros.put("tipo", "via_ingreso");
				return getServiceLocator().getServicio(ElementoService.class)
						.listar(parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Elemento registro) {
				bandbox.setValue(registro.getDescripcion());
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}
		};

		iconfiguracionbandbox_procedimientos = new IConfiguracionBandbox<Procedimientos>() {

			@Override
			public void onSeleccionar(Procedimientos t, Bandbox bandbox) {

			}

			@Override
			public String getCabecera(Bandbox bandbox) {
				return "Codigo cups#70px|Nombre";
			}

			@Override
			public String getWidthListBox() {
				return "350px";
			}

			@Override
			public String getHeightListBox() {
				return "200px";
			}

			@Override
			public void renderListitem(Listitem listitem,
					Procedimientos registro, Bandbox bandbox) {
				listitem.appendChild(new Listcell(registro.getCodigo_cups()));
				listitem.appendChild(new Listcell(""
						+ registro.getDescripcion()));
			}

			@Override
			public List<?> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros, Bandbox bandbox) {
				parametros.put("paramTodo", "paramTodo");
				parametros.put("value", valorBusqueda.toUpperCase());
				return getServiceLocator().getServicio(
						ProcedimientosService.class).listar(parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Procedimientos registro) {
				bandbox.setValue(registro.getCodigo_cups() + " "
						+ registro.getDescripcion());
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}

		};

	}

	public void eliminarDatos(Listitem listitem) throws Exception {
		try {
			listitem.detach();
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