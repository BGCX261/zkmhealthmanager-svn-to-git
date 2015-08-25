/*
 * vacunasAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.service.Esquema_vacunacionService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.VacunasService;

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
import org.zkoss.zul.Checkbox;
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

import healthmanager.modelo.service.GeneralExtraService;

public class VacunasAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(VacunasAction.class);

	private VacunasService vacunasService;
	private ProcedimientosService procedimientosService;
	private GeneralExtraService generalExtraService;

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
	private BandboxRegistrosMacro bandboxProcedimientos;
	@View
	private Textbox tbxDescripcion;
	@View
	private Rows rowEsquemaVacunacion;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_cie;
	@View
	private Textbox tbxCieDescripcion;

	private List<Esquema_vacunacion> list_Esquema_vacunacions = new ArrayList<Esquema_vacunacion>();

	private final String[] idsExcluyentes = {};

	private Vacunas vacunasTemp = null;

	private Toolbarbutton toolbarbutton;
	
	private Procedimientos procedimiento_actual;

	@Override
	public void init() {
		listarCombos();
		bandboxProcedimientos.inicializar(tbxDescripcion,
				(Toolbarbutton) getFellow("btnLimpiarCuentaCobrar"));
		tbxCodigo_cie.inicializar(tbxCieDescripcion,
				(Toolbarbutton) getFellow("btnLimpiarCie"));
		parametrizarBandbox();
		inicializarEsquemaVacunacion();
	}

	private void inicializarEsquemaVacunacion() {
		rowEsquemaVacunacion.getChildren().clear();
		adicionarRowAdicion();
	}

	private void adicionarRowAdicion() {
		final Row row = new Row();

		Cell cell = new Cell();
		cell.setColspan(12);
		cell.setAlign(AlignText.RIGHT.toString().toLowerCase());
		row.appendChild(cell);

		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/add.png");
		cell.appendChild(toolbarbutton);

		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						agregarNuevaFilaEsquemaVacunacion(row,
								new Esquema_vacunacion());
						adicionarRowAdicion();
						invalidarTabla();
						// if(rowEsquemaVacunacion.getChildren().size() <= 2){
						// Clients.showNotification("Presiones aqui para eliminar un item al esquema del vacunacion",
						// toolbarbutton);
						// }
					}
				});
		rowEsquemaVacunacion.appendChild(row);
	}

	protected Toolbarbutton agregarNuevaFilaEsquemaVacunacion(Row row,
			Esquema_vacunacion esquema_vacunacion) {
		if (row != null)
			row.getChildren().clear();
		else {
			row = new Row();
			rowEsquemaVacunacion.appendChild(row);
		}

		/* inicalizamos carga de datos esquema */
		/* descripcion */
		Textbox textbox = new Textbox();
		textbox.setHflex("1");
		row.appendChild(textbox);
		cargarAutomatica(textbox, esquema_vacunacion, "descripcion");

		/* dosis */
		Intbox intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		cargarAutomatica(intbox, esquema_vacunacion, "dosis");

		/* via administracion */
		Listbox listbox = getListBoxViaAdministracion();
		listbox.setHflex("1");
		row.appendChild(listbox);
		cargarAutomatica(listbox, esquema_vacunacion, "via_administracion");

		/* diferenciador */
		Listbox listboxDiferec = getListboxComparador();
		listboxDiferec.setHflex("1");
		row.appendChild(listboxDiferec);
		cargarAutomatica(listboxDiferec, esquema_vacunacion, "diferenciador");

		/* Edad Inicial */
		intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		cargarAutomatica(intbox, esquema_vacunacion, "edad_inicial");

		/* unidad medida */
		listbox = getListBoxUnidadMedida();
		listbox.setHflex("1");
		row.appendChild(listbox);
		cargarAutomatica(listbox, esquema_vacunacion, "unidad_med_edad_inicial");

		/* Edad Final */
		intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		cargarAutomatica(intbox, esquema_vacunacion, "edad_final");

		/* unidad medida */
		listbox = getListBoxUnidadMedida();
		listbox.setHflex("1");
		row.appendChild(listbox);
		cargarAutomatica(listbox, esquema_vacunacion, "unidad_med_edad_final");

		/* evento para accion */
		cargarEventoAutomaticoDiferenciacion(listboxDiferec, intbox, listbox);
		cargarEventoDiferencia(listboxDiferec, intbox, listbox);

		/* mujeres embarazas */
		Checkbox checkbox = new Checkbox();
		checkbox.setHflex("1");
		row.appendChild(checkbox);
		cargarAutomatica(checkbox, esquema_vacunacion, "embarazadas");

		/* mujeres lactantes */
		checkbox = new Checkbox();
		checkbox.setHflex("1");
		row.appendChild(checkbox);
		cargarAutomatica(checkbox, esquema_vacunacion, "mujeres_lactantes");

		/* respuesta 4505 */
		intbox = new Intbox(1);
		intbox.setHflex("1");
		row.appendChild(intbox);
		cargarAutomatica(intbox, esquema_vacunacion, "respuesta_4505");

		list_Esquema_vacunacions.add(esquema_vacunacion);
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setAttribute("row", row);
		toolbarbutton.setAttribute("esquMa", esquema_vacunacion);
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
													rowEsquemaVacunacion
															.removeChild((Row) arg0
																	.getTarget()
																	.getAttribute(
																			"row"));
													list_Esquema_vacunacions
															.remove((Esquema_vacunacion) arg0
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

	private void cargarEventoAutomaticoDiferenciacion(
			final Listbox listboxDiferec, final Intbox intbox,
			final Listbox listbox) {
		listboxDiferec.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						cargarEventoDiferencia(listboxDiferec, intbox, listbox);
					}
				});
	}

	private void cargarEventoDiferencia(Listbox listboxDiferec,
			final Intbox intbox, final Listbox listbox) {
		String valor = listboxDiferec.getSelectedItem().getValue();
		boolean visible = valor.equals("><") || valor.equals("=><=");
		intbox.setVisible(visible);
		listbox.setVisible(visible);
	}

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
						field.set(bean, "");
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

	private Listbox getListBoxViaAdministracion() {
		String[][] via_administracion = { { "01", "INTRAMUSCULAR" },
				{ "02", "ORAL" } };
		Listbox listbox = new Listbox();
		listbox.setZclass("combobox");
		listbox.setMold("select");
		for (String[] unidad_medida : via_administracion) {
			listbox.appendChild(new Listitem(unidad_medida[1], unidad_medida[0]));
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
		return listbox;
	}

	private Listbox getListBoxUnidadMedida() {
		String[][] unidades_medida = { { "01", "Dia(s)" }, { "02", "Mes(es)" },
				{ "03", "año(s)" } };
		Listbox listbox = new Listbox();
		listbox.setZclass("combobox");
		listbox.setMold("select");
		for (String[] unidad_medida : unidades_medida) {
			listbox.appendChild(new Listitem(unidad_medida[1], unidad_medida[0]));
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
		return listbox;
	}

	private Listbox getListboxComparador() {
		String[][] unidades_medida = { { "<", "Menor de" },
				{ "<=", "Menor o Igual" }, { "==", "Igual a" },
				{ "><", "Entre" }, { "=><=", "Entre o Igual" },
				{ ">=", "Mayor o Igual" }, { ">", "Mayor de" } };
		Listbox listbox = new Listbox();
		listbox.setZclass("combobox");
		listbox.setMold("select");
		for (String[] unidad_medida : unidades_medida) {
			listbox.appendChild(new Listitem(unidad_medida[1], unidad_medida[0]));
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
		return listbox;
	}

	private void parametrizarBandbox() {
		inicializarBandboxProcedimiento();
		inicializarBandboxCie();
	}

	private void inicializarBandboxCie() {
		BandboxRegistrosIMG<Cie> bandboxRegistrosIMG = new BandboxRegistrosIMG<Cie>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Cie registro) {
				bandbox.setValue(registro.getCodigo());
				textboxInformacion.setValue(registro.getNombre());
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem, Cie registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre() + ""));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Cie> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				parametros.put("limite_paginado", "limit 25 offset 0");
				
				return generalExtraService.listar(Cie.class,parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
			}
		};
		/* inyectamos el mismo evento */
		tbxCodigo_cie.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	private void inicializarBandboxProcedimiento() {
		BandboxRegistrosIMG<Procedimientos> bandboxRegistrosIMG = new BandboxRegistrosIMG<Procedimientos>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Procedimientos registro) {
				bandbox.setValue(registro.getCodigo_cups());
				textboxInformacion.setValue(registro.getDescripcion());

				Vacunas vacunas = new Vacunas();
				vacunas.setCodigo_empresa(codigo_empresa);
				vacunas.setCodigo_sucursal(codigo_sucursal);
				procedimiento_actual = registro;
				vacunas.setCodigo_procedimiento(registro.getId_procedimiento()+"");
				final Vacunas vacunasTemp = vacunasService.consultar(vacunas);
				//log.info("vacuna: " + vacunasTemp);
				if (vacunasTemp != null) {
					Messagebox
							.show("Ya existe un esquema para esta vacuna, desea cargar el esquema? ",
									"Informacion",
									Messagebox.YES + Messagebox.NO,
									Messagebox.QUESTION,
									new org.zkoss.zk.ui.event.EventListener<Event>() {
										public void onEvent(Event event)
												throws Exception {
											if ("onYes".equals(event.getName())) {
												mostrarDatos(vacunasTemp);
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
				inicializarEsquemaVacunacion();
				vacunasTemp = null;
			}
		};
		/* inyectamos el mismo evento */
		bandboxProcedimientos.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_procedimiento");
		listitem.setLabel("Codigo procedimiento");
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
			if(groupboxEditar.isVisible()){
				Clients.showNotification(
						"Presiones aqui para adicionar un item al esquema de vacunacion",
						toolbarbutton);
			}
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		list_Esquema_vacunacions.clear();
		capVacunas.setLabel("Registro de Vacuna");
		inicializarEsquemaVacunacion();
		vacunasTemp = null;
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		bandboxProcedimientos
				.setStyle("text-transform:uppercase;background-color:white");
		tbxDescripcion
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		String msj = "Los campos marcados con (*) son obligatorios";

		if (bandboxProcedimientos.getText().trim().equals("")) {
			bandboxProcedimientos
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxDescripcion.getText().trim().equals("")) {
			tbxDescripcion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida && list_Esquema_vacunacions.isEmpty()) {
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

			parameters.put("limite_paginado","limit 25 offset 0");

			List<Vacunas> lista_datos = vacunasService.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Vacunas vacunas : lista_datos) {
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

		final Vacunas vacunas = (Vacunas) objeto;

		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(vacunas
				.getCodigo_procedimiento()));
		procedimientos = getServiceLocator().getProcedimientosService()
				.consultar(procedimientos);

		Hbox hbox = new Hbox();
		Space space = new Space();

		Cie cie = new Cie();
		cie.setCodigo(vacunas.getCodigo_cie());
		cie = generalExtraService.consultar(cie);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(procedimientos.getCodigo_cups() + ""));
		fila.appendChild(new Label(vacunas.getDescripcion() + ""));
		fila.appendChild(new Label(cie != null ? cie.getNombre() : ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(vacunas);
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
									eliminarDatos(vacunas);
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
				Vacunas vacunas = new Vacunas();
				vacunas.setCodigo_procedimiento(procedimiento_actual.getId_procedimiento()+"");
				vacunas.setDescripcion(tbxDescripcion.getValue());
				vacunas.setCodigo_empresa(empresa.getCodigo_empresa());
				vacunas.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				vacunas.setUltimo_update(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				vacunas.setUltimo_user(usuarios.getCodigo().toString());
				vacunas.setCodigo_cie(tbxCodigo_cie.getValue());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					vacunas.setCreacion_date(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					vacunas.setCreacion_user(usuarios.getCodigo().toString());
				} else {
					vacunas.setCreacion_date(vacunasTemp.getCreacion_date());
					vacunas.setCreacion_user(vacunasTemp.getCreacion_user());
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("vacunas", vacunas);
				map.put("esquema_vacunacion", list_Esquema_vacunacions);
				vacunasService.guardarEsquema(map);

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
		limpiarDatos();
		Vacunas vacunas = (Vacunas) obj;
		rowEsquemaVacunacion.getChildren().clear();
		list_Esquema_vacunacions.clear();
		this.vacunasTemp = vacunas;
		try {
			Procedimientos procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(vacunas
					.getCodigo_procedimiento()));
			procedimientos = getServiceLocator().getProcedimientosService()
					.consultar(procedimientos);

			capVacunas.setLabel("Modificar de Vacuna");
			procedimiento_actual = procedimientos;
			bandboxProcedimientos.setValue(procedimientos.getCodigo_cups());
			tbxDescripcion.setValue(vacunas.getDescripcion());

			Procedimientos procedimientos2 = new Procedimientos();
			procedimientos2.setId_procedimiento(new Long(vacunas
					.getCodigo_procedimiento()));
			procedimientos2 = (Procedimientos) procedimientosService
					.consultar(procedimientos2);

			if (procedimientos2 != null)
				bandboxProcedimientos.seleccionarRegistro(procedimientos2,
						procedimientos2.getId_procedimiento()+"",
						procedimientos2.getDescripcion());

			Cie cie = new Cie();
			cie.setCodigo(vacunas.getCodigo_cie());
			cie = generalExtraService.consultar(cie);

			if (cie != null)
				tbxCodigo_cie.seleccionarRegistro(cie, cie.getCodigo(),
						cie.getNombre());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", vacunas.getCodigo_empresa());
			map.put("codigo_sucursal", vacunas.getCodigo_sucursal());
			map.put("codigo_vacuna", vacunas.getCodigo_procedimiento());
			List<Esquema_vacunacion> list_Esquema_vacunacions = getServiceLocator()
					.getServicio(Esquema_vacunacionService.class).listar(map);
			for (Esquema_vacunacion esquema_vacunacion : list_Esquema_vacunacions) {
				agregarNuevaFilaEsquemaVacunacion(null, esquema_vacunacion);
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
		Vacunas vacunas = (Vacunas) obj;
		try {
			int result = vacunasService.eliminar(vacunas);
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