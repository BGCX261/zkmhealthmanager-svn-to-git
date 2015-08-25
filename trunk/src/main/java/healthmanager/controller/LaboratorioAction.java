package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Laboratorios;
import healthmanager.modelo.bean.Laboratorios_respuestas;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.LaboratoriosService;
import healthmanager.modelo.service.Laboratorios_respuestasService;
import healthmanager.modelo.service.ProcedimientosService;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.zkoss.zul.Caption;
import org.zkoss.zul.Cell;
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

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.LabelAlign.AlignText;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class LaboratorioAction extends ZKWindow {
//	private static Logger log = Logger.getLogger(VacunasAction.class);

	private LaboratoriosService laboratoriosServiceService;
	private ProcedimientosService procedimientosService;

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

	@View
	private Caption capVacunas;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_cups;
	@View
	private Textbox tbxDescripcion;
	@View
	private Rows rowLaboratorio;

	private List<Laboratorios_respuestas> list_laboratorios_respuesta = new ArrayList<Laboratorios_respuestas>();

	private final String[] idsExcluyentes = {};

	private Laboratorios laboratoriosTemp = null;

	private Toolbarbutton toolbarbutton;

	@Override
	public void init() {
		listarCombos();
		tbxCodigo_cups.inicializar(tbxDescripcion,
				(Toolbarbutton) getFellow("btnLimpiarCuentaCobrar"));
		parametrizarBandbox();
		inicializarRespuestaLaboratorio();
	}

	private void inicializarRespuestaLaboratorio() {
		rowLaboratorio.getChildren().clear();
		adicionarRowAdicion();
	}

	private void adicionarRowAdicion() {
		final Row row = new Row();

		Cell cell = new Cell();
		cell.setColspan(3);
		cell.setAlign(AlignText.RIGHT.toString().toLowerCase());
		row.appendChild(cell);

		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/add.png");
		cell.appendChild(toolbarbutton);

		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						agregarNuevaFilaRespuestaLaboratorio(row,
								new Laboratorios_respuestas());
						adicionarRowAdicion();
						invalidarTabla();
						// if(rowEsquemaVacunacion.getChildren().size() <= 2){
						// Clients.showNotification("Presiones aqui para eliminar un item al esquema del vacunacion",
						// toolbarbutton);
						// }
					}
				});
		rowLaboratorio.appendChild(row);
	}

	protected Toolbarbutton agregarNuevaFilaRespuestaLaboratorio(Row row,
			Laboratorios_respuestas laboratorios_respuestas) {
		if (row != null)
			row.getChildren().clear();
		else {
			row = new Row();
			rowLaboratorio.appendChild(row);
		}

		/* dosis */
		Intbox intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		cargarAutomatica(intbox, laboratorios_respuestas, "codigo_respuesta");

		/* inicalizamos carga de datos esquema */
		/* descripcion */
		Textbox textbox = new Textbox();
		textbox.setHflex("1");
		row.appendChild(textbox);
		cargarAutomatica(textbox, laboratorios_respuestas, "descripcion");

		list_laboratorios_respuesta.add(laboratorios_respuestas);
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setAttribute("row", row);
		toolbarbutton.setAttribute("esquMa", laboratorios_respuestas);
		toolbarbutton.setImage("/images/borrar.gif");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(final Event arg0) throws Exception {
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
													rowLaboratorio
															.removeChild((Row) arg0
																	.getTarget()
																	.getAttribute(
																			"row"));
													list_laboratorios_respuesta
															.remove((Laboratorios_respuestas) arg0
																	.getTarget()
																	.getAttribute(
																			"esquMa"));
													invalidarTabla();
												}
											}
										});
					}
				});
		row.appendChild(toolbarbutton);

		/* fin carga de datos esquema */
		return toolbarbutton;
	}

	// private void cargarEventoAutomaticoDiferenciacion(final Listbox
	// listboxDiferec,
	// final Intbox intbox, final Listbox listbox) {
	// listboxDiferec.addEventListener(Events.ON_SELECT, new
	// EventListener<Event>() {
	// @Override
	// public void onEvent(Event arg0) throws Exception {
	// cargarEventoDiferencia(listboxDiferec, intbox, listbox);
	// }
	// });
	// }

	// private void cargarEventoDiferencia(Listbox listboxDiferec,
	// final Intbox intbox, final Listbox listbox){
	// String valor = listboxDiferec.getSelectedItem().getValue();
	// boolean visible = valor.equals("><") || valor.equals("=><=");
	// intbox.setVisible(visible);
	// listbox.setVisible(visible);
	// }

	private void invalidarTabla() {
		getFellow("gridRegistros").invalidate();
	}

	/**
	 * Este metodo me permite cargar por demanda los valores
	 * 
	 * @author Luis Miguel
	 * */
	private void cargarAutomatica(Component component, Object bean, String campo) {
		try {
			/* obtenemos el valor */
			Field field = bean.getClass().getDeclaredField(campo);
			field.setAccessible(true);
			Object valor = field.get(bean);
			component.setAttribute("bean", bean);
			component.setAttribute("field", field);
			/* inyectamos valor en compoenete */
			if (component instanceof Intbox) {
				Intbox intbox = (Intbox) component;
				intbox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor = ((Intbox) arg0.getTarget())
										.getValue();
								Field field = (Field) ((Intbox) arg0
										.getTarget()).getAttribute("field");
								Object bean = (Object) ((Intbox) arg0
										.getTarget()).getAttribute("bean");

								if (valor == null) {
									valor = 1;
									((Intbox) arg0.getTarget()).setValue(1);
								}

								if (valor != null) {
									if (field.getType() == String.class) {
										field.set(bean, valor.toString());
									} else if (field.getType() == valor
											.getClass()) {
										field.set(bean, valor);
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Intbox) arg0.getTarget()));
									}
								}
							}
						});
				if (valor != null) {
					if (valor instanceof String) {
						String v_ = (String) valor;
						if (!v_.trim().isEmpty() && v_.matches("[0-9]*$")) {
							intbox.setValue(Integer.parseInt(v_));
						}
					} else
						intbox.setValue((Integer) valor);
				} else {
					if (field.getType() == String.class) {
						field.set(bean, "1");
					} else
						field.set(bean, 1);
				}
			} else if (component instanceof Textbox) {
				Textbox intbox = (Textbox) component;
				intbox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor = ((Textbox) arg0.getTarget())
										.getValue();
								Field field = (Field) ((Textbox) arg0
										.getTarget()).getAttribute("field");
								Object bean = (Object) ((Textbox) arg0
										.getTarget()).getAttribute("bean");
								if (valor != null) {
									if (field.getType() == valor.getClass()) {
										field.set(bean, valor);
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Textbox) arg0.getTarget()));
									}
								}
							}
						});
				if (valor != null) {
					intbox.setValue((String) valor);
				} else {
					field.set(bean, "");
				}
			} else if (component instanceof Listbox) {
				Listbox listbox = (Listbox) component;
				listbox.addEventListener(Events.ON_SELECT,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor = ((Listbox) arg0.getTarget())
										.getSelectedItem().getValue();
								Field field = (Field) ((Listbox) arg0
										.getTarget()).getAttribute("field");
								Object bean = (Object) ((Listbox) arg0
										.getTarget()).getAttribute("bean");
								if (valor != null) {
									if (field.getType() == valor.getClass()) {
										field.set(bean, valor);
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Listbox) arg0.getTarget()));
									}
								}
							}
						});
				if (valor != null) {
					Utilidades.setValueFrom(listbox, (String) valor);
				} else {
					field.set(bean, listbox.getSelectedItem().getValue());
				}
			}
		} catch (Exception e) {
			Clients.showNotification(
					"Ocurrio un error reportelo a administrador del sistema, en este compoenete.",
					component);
			e.printStackTrace();
		}
	}

	private void parametrizarBandbox() {
		inicializarBandboxProcedimiento();
	}

	private void inicializarBandboxProcedimiento() {
		BandboxRegistrosIMG<Procedimientos> bandboxRegistrosIMG = new BandboxRegistrosIMG<Procedimientos>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Procedimientos registro) {
				bandbox.setValue(registro.getCodigo_cups());
				textboxInformacion.setValue(registro.getDescripcion());

				Laboratorios laboratorios = new Laboratorios();
				laboratorios.setCodigo_empresa(codigo_empresa);
				laboratorios.setCodigo_sucursal(codigo_sucursal);
				laboratorios.setCodigo_procedimiento(bandbox.getValue());
				final Laboratorios laboratorioTemp = laboratoriosServiceService
						.consultar(laboratorios);
				//log.info("vacuna: " + laboratorioTemp);
				if (laboratorioTemp != null) {
					Messagebox.show(
							"Ya existe un este laboratorio, desea cargarlo? ",
							"Informacion", Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION,
							new org.zkoss.zk.ui.event.EventListener<Event>() {
								public void onEvent(Event event)
										throws Exception {
									if ("onYes".equals(event.getName())) {
										mostrarDatos(laboratorioTemp);
									}
								}
							});
				}
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem,
					Procedimientos registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo_cups() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getDescripcion() + ""));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Procedimientos> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				procedimientosService.setLimit("limit 25 offset 0");
				return procedimientosService.listar(parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				inicializarRespuestaLaboratorio();
				laboratoriosTemp = null;
			}
		};
		/* inyectamos el mismo evento */
		tbxCodigo_cups.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_cups");
		listitem.setLabel("Codigo cups");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("descripcion");
		listitem.setLabel("Descripcion");
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
			Clients.showNotification(
					"Presiones aqui para adicionar un item al esquema de vacunacion",
					toolbarbutton);
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		list_laboratorios_respuesta.clear();
		capVacunas.setLabel("Registro de Laboratorio");
		inicializarRespuestaLaboratorio();
		laboratoriosTemp = null;
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		tbxCodigo_cups
				.setStyle("text-transform:uppercase;background-color:white");
		tbxDescripcion
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		String msj = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo_cups.getText().trim().equals("")) {
			tbxCodigo_cups
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxDescripcion.getText().trim().equals("")) {
			tbxDescripcion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida && list_laboratorios_respuesta.isEmpty()) {
			msj = "Para realizar esta accion debe agregar por lo menos un esquema de vacunacion";
			valida = false;
			Clients.showNotification(
					"Presiones aqui para adicionar un item al esquema de vacunacion",
					toolbarbutton);
		}

		if (!valida) {
			Notificaciones.mostrarNotificacionAlerta(usuarios.getNombres()
					+ " recuerde que...", msj, IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
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
			parameters.put("value", "%" + value + "%");

			laboratoriosServiceService.setLimit("limit 25 offset 0");

			List<Laboratorios> lista_datos = laboratoriosServiceService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Laboratorios vacunas : lista_datos) {
				rowsResultado.appendChild(crearFilas(vacunas, this));
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

		final Laboratorios laboratorios = (Laboratorios) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(laboratorios
				.getCodigo_procedimiento()));
		procedimientos = procedimientosService
				.consultar(procedimientos);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(laboratorios.getCodigo_procedimiento() + ""));
		fila.appendChild(new Label(
				procedimientos != null ? procedimientos
						.getDescripcion() : ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(laboratorios);
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
									eliminarDatos(laboratorios);
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

				Laboratorios laboratorios = new Laboratorios();
				laboratorios.setCodigo_empresa(empresa.getCodigo_empresa());
				laboratorios.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				laboratorios.setCodigo_procedimiento(tbxCodigo_cups.getValue());
				laboratorios.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				laboratorios.setUltimo_user(usuarios.getCodigo().toString());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					laboratorios.setCreacion_date(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					laboratorios.setCreacion_user(usuarios.getCodigo()
							.toString());
				} else {
					laboratorios.setCreacion_date(laboratoriosTemp
							.getCreacion_date());
					laboratorios.setCreacion_user(laboratoriosTemp
							.getCreacion_user());
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lab", laboratorios);
				map.put("labres", list_laboratorios_respuesta);
				laboratoriosServiceService.guardar(map);

				Notificaciones.mostrarNotificacionInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
			}
		} catch (ValidacionRunTimeException e) {
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					e.getMessage(), IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Laboratorios laboratorios = (Laboratorios) obj;
		rowLaboratorio.getChildren().clear();
		list_laboratorios_respuesta.clear();
		this.laboratoriosTemp = laboratorios;
		try {

			Procedimientos procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(laboratorios.getCodigo_procedimiento()));
			procedimientos = procedimientosService
					.consultar(procedimientos);

			capVacunas.setLabel("Modificar de Laboratorio");
			tbxCodigo_cups.setValue(laboratorios.getCodigo_procedimiento());
			tbxDescripcion
					.setValue(procedimientos != null ? procedimientos
							.getDescripcion() : "");

			if (procedimientos != null)
				tbxCodigo_cups.seleccionarRegistro(procedimientos,
						procedimientos.getCodigo_cups(),
						procedimientos.getDescripcion());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", laboratorios.getCodigo_empresa());
			map.put("codigo_sucursal", laboratorios.getCodigo_sucursal());
			map.put("codigo_cups", laboratorios.getCodigo_procedimiento());
			List<Laboratorios_respuestas> list_laboratorio_respuesta = getServiceLocator()
					.getServicio(Laboratorios_respuestasService.class).listar(
							map);
			for (Laboratorios_respuestas laboratorios_respuestas : list_laboratorio_respuesta) {
				agregarNuevaFilaRespuestaLaboratorio(null,
						laboratorios_respuestas);
			}

			/* adicionamos boton adicion */
			adicionarRowAdicion();
			invalidarTabla();

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
			Clients.scrollIntoView(toolbarbutton);
			Clients.showNotification(
					"Presiones aqui para adicionar un item al esquema de vacunacion",
					toolbarbutton);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Laboratorios vacunas = (Laboratorios) obj;
		try {
			int result = laboratoriosServiceService.eliminar(vacunas);
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
