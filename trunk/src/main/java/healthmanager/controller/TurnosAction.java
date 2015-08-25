/*
 * turnosAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Detalle_turno;
import healthmanager.modelo.bean.Turnos;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.Detalle_turnoService;
import healthmanager.modelo.service.TurnosService;

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
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.DiasSemanaMacro;
import com.framework.macros.DiasSemanaMacro.OnNuevoPatronDiasSemanales;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.Res;
import com.framework.res.LabelAlign.AlignText;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class TurnosAction extends ZKWindow {

	private TurnosService turnosService;

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
	private BandboxRegistrosMacro tbxCodigo_centro_atencion;
	@View
	private Textbox tbxDescripcionCentroAtencion;

	@View
	private Listbox lbxCapMultiple;

	@View
	private Rows rowTopeTurnos;

	@View
	private Groupbox groupCreacionMultiple;
	@View
	private Groupbox groupApoyo;
	
	@View private Checkbox chDomingo;
	@View private Checkbox chLunes;
	@View private Checkbox chMartes;
	@View private Checkbox chMiercoles;
	@View private Checkbox chJueves;
	@View private Checkbox chViernes;
	@View private Checkbox chSabado;

	private final String[] idsExcluyentes = {};

	@View
	private Toolbarbutton btnLimpiarCentro;

	private Toolbarbutton toolbarbutton;

	private List<Detalle_turno> list_detalle_turno;

	@Override
	public void init() {
		list_detalle_turno = new ArrayList<Detalle_turno>();
		listarCombos();
		parametrizarBandBox();
		inicializarTurnos();
		cargarEventoMultiple();
		reiniciarCheckbox();
	}
	
	private void reiniciarCheckbox(){
		chDomingo.setChecked(false);  
		chLunes.setChecked(true);    
		chMartes.setChecked(true);  
		chMiercoles.setChecked(true);
		chJueves.setChecked(true);  
		chViernes.setChecked(true); 
		chSabado.setChecked(true); 
	}

	private void cargarEventoMultiple() {
		groupCreacionMultiple.addEventListener(Events.ON_OPEN,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						groupApoyo.setVisible(!groupCreacionMultiple.isOpen());
						if (groupCreacionMultiple.isOpen()
								&& lbxCapMultiple.getItems().isEmpty()) {
							// Listamos los centros de salud
							cargarCentrosDeSalud();
						}
					}
				});
	}

	protected void cargarCentrosDeSalud() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		List<Centro_atencion> centro_atencions = getServiceLocator()
				.getCentro_atencionService().listar(parametros);
		for (Centro_atencion centro_atencion : centro_atencions) {
			Listitem listitem = new Listitem();
			listitem.setValue(centro_atencion);

			Listcell listcell = new Listcell();
			listcell.setLabel(centro_atencion.getCodigo_centro());
			listitem.appendChild(listcell);

			listcell = new Listcell();
			listcell.setLabel(centro_atencion.getNombre_centro().toUpperCase());
			listitem.appendChild(listcell);

			lbxCapMultiple.appendChild(listitem);
		}
	}

	private void parametrizarBandBox() {
		parametrizarBandboxCentro();
	}

	private void parametrizarBandboxCentro() {
		tbxCodigo_centro_atencion.inicializar(tbxDescripcionCentroAtencion,
				btnLimpiarCentro);
		tbxCodigo_centro_atencion
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {
						listitem.appendChild(new Listcell(registro
								.getCodigo_centro()));
						listitem.appendChild(new Listcell(registro
								.getNombre_centro()));
						// listitem.appendChild(new
						// Listcell(registro.getDireccion()));
					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						parametros.put("limite_paginado", "limit 25 offset 0");
						return getServiceLocator().getCentro_atencionService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Centro_atencion registro) {
						try {
							Turnos turnos = new Turnos();
							turnos.setCodigo_empresa(codigo_empresa);
							turnos.setCodigo_sucursal(codigo_sucursal);
							turnos.setCodigo_centro_atencion(registro
									.getCodigo_centro());

							turnos = getServiceLocator().getServicio(
									TurnosService.class).consultar(turnos);

							if (turnos != null) {
								mostrarDatos(turnos);
							}

							bandbox.setValue(registro.getCodigo_centro());
							textboxInformacion.setValue(registro
									.getNombre_centro());
							((BandboxRegistrosMacro) bandbox)
									.setObject(registro);
						} catch (Exception e) {
							return false;
						}
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						((BandboxRegistrosMacro) bandbox).setObject(null);
					}
				});
	}

	private void inicializarTurnos() {
		rowTopeTurnos.getChildren().clear();
		adicionarRowAdicion();
	}

	private void adicionarRowAdicion() {
		final Row row = new Row();

		Cell cell = new Cell();
		cell.setColspan(6);
		cell.setAlign(AlignText.RIGHT.toString().toLowerCase());
		row.appendChild(cell);

		toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/add.png");
		cell.appendChild(toolbarbutton);

		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Toolbarbutton toolbarbuttonButtonEliminar = agregarNuevaFilaTopeTurnos(
								row, new Detalle_turno());
						adicionarRowAdicion();
						invalidarTabla();
						if (rowTopeTurnos.getChildren().size() <= 2) {
							Clients.showNotification(
									"Presiones aqui para eliminar un item",
									toolbarbuttonButtonEliminar);
						}
					}
				});
		rowTopeTurnos.appendChild(row);
	}

	protected Toolbarbutton agregarNuevaFilaTopeTurnos(Row row,
			final Detalle_turno detalle_turno) {
		if (row != null)
			row.getChildren().clear();
		else {
			row = new Row();
			rowTopeTurnos.appendChild(row);
		}

		/* Edad Inicial */
		Intbox intbox = new Intbox();
		intbox.setAttribute("NoComplementar", "_No");
		intbox.setHflex("1");
		row.appendChild(intbox);
		Res.cargarAutomatica(intbox, detalle_turno, "tope_maximo_citas");

		Listbox listboxTurnos = getListBoxApoyoDiagnostico();
		row.appendChild(listboxTurnos);
		Res.cargarAutomatica(listboxTurnos, detalle_turno, "via_ingreso");

		listboxTurnos = getListBoxTurnos();
		row.appendChild(listboxTurnos);
		Res.cargarAutomatica(listboxTurnos, detalle_turno, "codigo_turno");
		
		DiasSemanaMacro diasSemanaMacro = new DiasSemanaMacro(this,
				chDomingo.isChecked(), chLunes.isChecked(),
				chMartes.isChecked(), chMiercoles.isChecked(),
				chJueves.isChecked(), chViernes.isChecked(),
				chSabado.isChecked());
		row.appendChild(diasSemanaMacro);
		diasSemanaMacro.setOnNuevoPatronDiasSemanales(new OnNuevoPatronDiasSemanales() {
			@Override
			public void patronDiasSemana(String patron_dias) {
				detalle_turno.setPatron_dias(patron_dias); 
			}
		});
		if(detalle_turno.getPatron_dias() != null && !detalle_turno.getPatron_dias().trim().isEmpty()){
			diasSemanaMacro.setPatronDias(detalle_turno.getPatron_dias()); 
		}else{
			detalle_turno.setPatron_dias(diasSemanaMacro.getPatronDias()); 
		}

		/* inicalizamos carga de datos esquema */
		/* descripcion */
		Timebox timebox = new Timebox();
		timebox.setFormat("hh:mm a");
		intbox.setAttribute("NoComplementar", "_No");
		timebox.setHflex("1");
		row.appendChild(timebox);
		Res.cargarAutomatica(timebox, detalle_turno, "hora_inicio");

		timebox = new Timebox();
		timebox.setHflex("1");
		timebox.setFormat("hh:mm a");
		row.appendChild(timebox);
		Res.cargarAutomatica(timebox, detalle_turno, "hora_fin");

		list_detalle_turno.add(detalle_turno);
		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setAttribute("row", row);
		toolbarbutton.setAttribute("esquMa", detalle_turno);
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
													rowTopeTurnos
															.removeChild((Row) arg0
																	.getTarget()
																	.getAttribute(
																			"row"));
													list_detalle_turno
															.remove((Detalle_turno) arg0
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

	private Listbox getListBoxApoyoDiagnostico() {
		Listbox listbox = new Listbox();
		listbox.setName("apoyo_diagnostico");
		listbox.setHflex("1");
		listbox.setZclass("combobox");
		listbox.setMold("select");
		Utilidades.listarElementoListbox(listbox, true, getServiceLocator());
		return listbox;
	}

	private Listbox getListBoxTurnos() {
		Listbox listbox = new Listbox();
		listbox.setName("descripcion_turno");
		listbox.setHflex("1");
		listbox.setZclass("combobox");
		listbox.setMold("select");
		Utilidades.listarElementoListbox(listbox, true, getServiceLocator());
		return listbox;
	}

	private void invalidarTabla() {
		getFellow("gridRegistros").invalidate();
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("turno.codigo_centro_atencion");
		listitem.setLabel("Codigo centro atencion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("centro.nombre_centro");
		listitem.setLabel("Nombre centro atencion");
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
					"Presiones aqui para registar de manera multiple",
					groupCreacionMultiple);
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		rowTopeTurnos.getChildren().clear();
		list_detalle_turno.clear();
		adicionarRowAdicion();
		groupApoyo.setTitle("REGISTAR TURNOS APOYO DIAGNOSTICO");
		reiniciarCheckbox();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		tbxCodigo_centro_atencion
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo_centro_atencion.getText().trim().equals("")
				&& !groupCreacionMultiple.isOpen()) {
			tbxCodigo_centro_atencion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (lbxCapMultiple.getSelectedItems().isEmpty()
				&& groupCreacionMultiple.isOpen()) {
			valida = false;
			msj = "Para realizar esta accion debe seleccionar por lo menos un centro de salud";
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", msj);
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

			turnosService.setLimit("limit 25 offset 0");

			List<Turnos> lista_datos = turnosService.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Turnos turnos : lista_datos) {
				rowsResultado.appendChild(crearFilas(turnos, this));
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

		final Turnos turnos = (Turnos) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		Centro_atencion centro_atencion = new Centro_atencion();
		centro_atencion.setCodigo_empresa(codigo_empresa);
		centro_atencion.setCodigo_sucursal(codigo_sucursal);
		centro_atencion.setCodigo_centro(turnos.getCodigo_centro_atencion());
		centro_atencion = getServiceLocator().getServicio(
				Centro_atencionService.class).consultar(centro_atencion);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(centro_atencion != null ? centro_atencion
				.getNombre_centro() : "CENTRO NO ENCONTRADO"));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(turnos);
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
									eliminarDatos(turnos);
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
				Messagebox.show(
						"Esta seguro que deseas guardar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									try {
										Turnos turnos = new Turnos();
										turnos.setCodigo_empresa(empresa
												.getCodigo_empresa());
										turnos.setCodigo_sucursal(sucursal
												.getCodigo_sucursal());
										turnos.setCodigo_centro_atencion(tbxCodigo_centro_atencion
												.getValue());
										turnos.setCreacion_date(new Timestamp(
												new GregorianCalendar()
														.getTimeInMillis()));
										turnos.setUltimo_update(new Timestamp(
												new GregorianCalendar()
														.getTimeInMillis()));
										turnos.setCreacion_user(usuarios
												.getCodigo().toString());
										turnos.setUltimo_user(usuarios.getCodigo()
												.toString());

										Map<String, Object> map = new HashMap<String, Object>();
										map.put("turnos", turnos);
										map.put("detalle_turno", list_detalle_turno);
										map.put("accion", tbxAccion.getValue());

										//log.info("list_detalle_turno ===> "
										//+ list_detalle_turno.size());

										if (groupCreacionMultiple.isOpen()) {
											map.put("lisCentros",
													getCentro_atencions());
											turnosService.guardarMultiple(map);
										} else {
											turnosService.guardar(map);
										}

										MensajesUtil
												.mensajeInformacion(
														"Informacion ..",
														"Los datos se guardaron satisfactoriamente");
									} catch (Exception e) { 
										MensajesUtil.mensajeError(e, "", TurnosAction.this);
									}
								}
							}
						});
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private List<Centro_atencion> getCentro_atencions() {
		List<Centro_atencion> centro_atencions = new ArrayList<Centro_atencion>();
		for (Listitem listitem : lbxCapMultiple.getSelectedItems()) {
			Centro_atencion centro_atencion = listitem.getValue();
			centro_atencions.add(centro_atencion);
		}
		return centro_atencions;
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Turnos turnos = (Turnos) obj; 
		try {
			tbxCodigo_centro_atencion.setValue(turnos
					.getCodigo_centro_atencion());

			groupApoyo.setVisible(true);
			groupCreacionMultiple.setOpen(false);

			groupApoyo.setTitle("MODIFICAR TURNOS APOYO DIAGNOSTICO");

			Centro_atencion centro_atencion = new Centro_atencion();
			centro_atencion.setCodigo_empresa(codigo_empresa);
			centro_atencion.setCodigo_sucursal(codigo_sucursal);
			centro_atencion
					.setCodigo_centro(turnos.getCodigo_centro_atencion());
			centro_atencion = getServiceLocator().getServicio(
					Centro_atencionService.class).consultar(centro_atencion);

			if (centro_atencion != null)
				tbxCodigo_centro_atencion.seleccionarRegistro(centro_atencion,
						centro_atencion.getCodigo_centro(),
						centro_atencion.getNombre_centro());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", turnos.getCodigo_empresa());
			map.put("codigo_sucursal", turnos.getCodigo_sucursal());
			map.put("codigo_centro_salud", turnos.getCodigo_centro_atencion());
			List<Detalle_turno> detalle_turnos = getServiceLocator()
					.getServicio(Detalle_turnoService.class).listar(map);

			rowTopeTurnos.getChildren().clear();
			list_detalle_turno.clear();
			for (Detalle_turno detalle_turno : detalle_turnos) {
				agregarNuevaFilaTopeTurnos(null, detalle_turno);
			}

			/* adicionamos boton adicion */
			adicionarRowAdicion();
			invalidarTabla();

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Turnos turnos = (Turnos) obj;
		try {
			int result = turnosService.eliminar(turnos);
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