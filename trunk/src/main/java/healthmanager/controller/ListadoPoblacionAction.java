package healthmanager.controller;

import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Excepciones_pyp;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.VacunasService;
import com.framework.util.Util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Footer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;

import com.framework.constantes.IConstantes;
import com.framework.util.MensajesUtil;

public class ListadoPoblacionAction extends ZKWindow {

	public static final String PACIENTES = IConstantes.IMPORTADOR_INTERNO_PACIENTE;
	public static final String LISTADO_CONTRATOS = IConstantes.IMPORTADOR_INTERNO_LISTADO_CONTRATOS;
	public static final String TITULO = "til";
	public static final String LISTAS = "list";

	@View
	private Rows rowsResultado;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Column columnAccion;

	@View
	private Foot footInformacion;
	@View
	private Footer footerInformacion;

	private List<Map<String, Object>> pacientesTemp;
	private List<Contratos> listado_contratos;

	private String informacion;

	@Override
	public void params(Map<String, Object> map) {
		pacientesTemp = (List<Map<String, Object>>) map.get(PACIENTES);
		listado_contratos = (List<Contratos>) map.get(LISTADO_CONTRATOS);
		groupboxConsulta.setTitle(map.get(TITULO) + "");
		informacion = (String) map
				.get(IConstantes.IMPORTADOR_INTERNO_INFORMACION);
		if (informacion != null && !informacion.trim().isEmpty()) {
			footInformacion.setVisible(true);
			footerInformacion.setLabel(informacion);
		}
	}

	@Override
	public void init() throws Exception {
		cargarPacientes();
	}

	private void cargarPacientes() {
		try {
			for (Map<String, Object> paciente : pacientesTemp) {
				rowsResultado.appendChild(crearFilas(paciente));
			}
			getFellow("gridResultado").invalidate();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	public Row crearFilas(Object objeto) throws Exception {
		final Row fila = new Row();

		final Map<String, Object> map = (Map<String, Object>) objeto;
		final Paciente paciente = (Paciente) map.get(PACIENTES);

		Departamentos departamentos = new Departamentos();
		departamentos.setCodigo(paciente.getCodigo_dpto());
		departamentos = getServiceLocator().getServicio(
				DepartamentosService.class).consultar(departamentos);

		Municipios municipios = new Municipios();
		municipios.setCoddep(paciente.getCodigo_dpto());
		municipios.setCodigo(paciente.getCodigo_municipio());
		municipios = getServiceLocator().getServicio(MunicipiosService.class)
				.consultar(municipios);

		String complementoDireccion = "";

		if (municipios != null) {
			complementoDireccion += (complementoDireccion.isEmpty() ? "" : ", ")
					+ municipios.getNombre();
		}

		if (departamentos != null) {
			complementoDireccion += (complementoDireccion.isEmpty() ? "" : ", ")
					+ departamentos.getNombre();
		}

		fila.setStyle("text-align: justify;nowrap:nowrap;border: 1px solid #949293;");
		fila.appendChild(getDetail(map, fila, paciente));
		fila.appendChild(new Label(paciente.getTipo_identificacion() + ""));
		fila.appendChild(new Label(paciente.getDocumento() + ""));
		fila.appendChild(new Label(paciente.getNombreCompleto()));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(paciente.getFecha_nacimiento())));

		String edad = Util.getEdadPrsentacion(paciente.getFecha_nacimiento());
		fila.appendChild(new Label(paciente.getSexo().equals("M") ? "MASCULINO"
				: "FEMENINO"));
		fila.appendChild(new Label(edad.toUpperCase()));

		fila.appendChild(new Label(paciente.getTel_res()));
		fila.appendChild(new Label(
				((paciente.getDireccion() + "") + " " + complementoDireccion)
						.toUpperCase()));
		fila.appendChild(new Label(
				(paciente.getZona() + "").equals("U") ? "URBANA" : "RURAL"));

		Toolbarbutton toolbarbuttonCrearNuevo = new Toolbarbutton();
		toolbarbuttonCrearNuevo.setImage("/images/New16.gif");
		toolbarbuttonCrearNuevo.setTooltiptext("Realizar nuevo registro");
		toolbarbuttonCrearNuevo.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("Esta seguro que desea realizar un nuevo registro? ",
										"Nuevo Registro Registro",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													PacienteService pacienteService = getServiceLocator()
															.getServicio(
																	PacienteService.class);
													Adicional_paciente adicional_paciente = (Adicional_paciente) map
															.get(IConstantes.IMPORTADOR_INTERNO_ADICIONAL_PACIENTE);
													if (pacienteService
															.consultar(paciente) == null) {
														adicional_paciente
																.setNro_identificacion(paciente
																		.getNro_identificacion());
														Map<String, Object> mapa_datos = new HashMap<String, Object>();
														mapa_datos.put(
																"paciente",
																paciente);
														mapa_datos.put(
																"accion",
																"registrar");
														mapa_datos
																.put("listado_contratos",
																		listado_contratos);
														mapa_datos
																.put("adicional_paciente",
																		adicional_paciente);
														mapa_datos
																.put("parametros_empresa",
																		parametros_empresa);
														pacienteService
																.guardarDatos(mapa_datos);
													} else {
														paciente.setNro_identificacion(paciente
																.getTipo_identificacion()
																+ "_"
																+ paciente
																		.getNro_identificacion());
														Map<String, Object> mapa_datos = new HashMap<String, Object>();
														if (pacienteService
																.consultar(paciente) == null) {
															mapa_datos
																	.put("accion",
																			"registrar");
														} else {
															mapa_datos
																	.put("accion",
																			"modificar");
														}
														adicional_paciente
																.setNro_identificacion(paciente
																		.getNro_identificacion());
														mapa_datos.put(
																"paciente",
																paciente);
														mapa_datos
																.put("listado_contratos",
																		listado_contratos);
														mapa_datos
																.put("adicional_paciente",
																		adicional_paciente);
														mapa_datos
																.put("parametros_empresa",
																		parametros_empresa);
														pacienteService
																.guardarDatos(mapa_datos);
													}
													desacoclarVista(fila);
												}
											}
										});
					}
				});
		Hbox hbox = new Hbox();
		hbox.appendChild(toolbarbuttonCrearNuevo);
		Cell cell = new Cell();
		cell.appendChild(hbox);
		fila.appendChild(cell);
		return fila;
	}

	private Detail getDetail(Map<String, Object> map, Row fila,
			Paciente paciente) {
		Detail detail = new Detail();
		detail.setVisible(false);
		Vlayout vlayoutExcepciones = null;
		Vlayout vlayoutVacunas = null;
		Vlayout vlayoutActividadePendiente = null;
		Vlayout vlayoutActividadeRealizadas = null;
		Vlayout vlayoutPacientes = null;

		List<Object>[] lists = (List<Object>[]) map.get(LISTAS);
		if (lists != null) {
			for (List<Object> list : lists) {
				// listamos las sublistas
				detail.setVisible(true);
				for (Object object : list) {
					if (object instanceof Esquema_vacunacion) {
						if (vlayoutVacunas == null) {
							vlayoutVacunas = new Vlayout();
							detail.appendChild(getGroupboxContenedor("VACUNAS",
									vlayoutVacunas));
						}
						// ADICIONAMOS INFORMACION
						vlayoutVacunas
								.appendChild(getInformacion((Esquema_vacunacion) object));
					} else if (object instanceof Excepciones_pyp) {
						if (vlayoutExcepciones == null) {
							vlayoutExcepciones = new Vlayout();
							detail.appendChild(getGroupboxContenedor(
									"EXCEPCIONES", vlayoutExcepciones));
						}
						// ADICIONAMOS INFORMACION
						vlayoutExcepciones
								.appendChild(getInformacion((Excepciones_pyp) object));
					} else if (object instanceof Plantilla_pyp
							&& ((Plantilla_pyp) object).isPendiente()) {
						if (vlayoutActividadePendiente == null) {
							vlayoutActividadePendiente = new Vlayout();
							detail.appendChild(getGroupboxContenedor(
									"ACTIVIDADES PENDIENTES",
									vlayoutActividadePendiente));
						}
						// ADICIONAMOS INFORMACION
						vlayoutActividadePendiente
								.appendChild(getActividad((Plantilla_pyp) object));
					} else if (object instanceof Plantilla_pyp
							&& !((Plantilla_pyp) object).isPendiente()) {
						if (vlayoutActividadeRealizadas == null) {
							vlayoutActividadeRealizadas = new Vlayout();
							detail.appendChild(getGroupboxContenedor(
									"ACTIVIDADES REALIZADAS",
									vlayoutActividadeRealizadas));
						}
						// ADICIONAMOS INFORMACION
						vlayoutActividadeRealizadas
								.appendChild(getActividad((Plantilla_pyp) object));
					}
				}
			}
		} else { // listado desde el importador
			columnAccion.setVisible(true);// Habilitamos la columnaw
			List<Paciente> list = (List<Paciente>) map
					.get(IConstantes.IMPORTADOR_INTERNO_LISTADO_PACIENTES);
			if (list != null) {
				detail.setVisible(!list.isEmpty());

				Grid grid = new Grid();

				Auxhead auxhead = new Auxhead();
				grid.appendChild(auxhead);

				Auxheader auxheader = new Auxheader();
				auxheader.setColspan(8);
				auxheader
						.setLabel("LISTADO DE PACIENTES YA EXISTENTES EN LA BASE DE DATOS QUE TIENEN RELAcion");
				auxhead.appendChild(auxheader);

				Columns columns = new Columns();
				grid.appendChild(columns);

				Column column = new Column("Tipo Id");
				column.setWidth("58px");
				columns.appendChild(column);

				column = new Column("Identificacion");
				column.setWidth("90px");
				columns.appendChild(column);

				columns.appendChild(new Column("Nombres"));
				columns.appendChild(new Column("Aseguradora"));

				column = new Column("Fecha nac");
				column.setWidth("90px");
				columns.appendChild(column);

				column = new Column("Sexo");
				column.setWidth("90px");
				columns.appendChild(column);

				columns.appendChild(new Column("Direccion"));

				column = new Column("Zona");
				column.setWidth("90px");
				columns.appendChild(column);

				column = new Column("Accion");
				column.setWidth("40px");
				columns.appendChild(column);

				Rows rows = new Rows();
				grid.appendChild(rows);

				for (Paciente paciente_homologado : list) {
					if (vlayoutPacientes == null) {
						vlayoutPacientes = new Vlayout();
						detail.appendChild(vlayoutPacientes);
					}
					// ADICIONAMOS INFORMACION
					rows.appendChild(getInformacion(paciente_homologado, fila,
							paciente, map));
					vlayoutPacientes.appendChild(grid);
				}
			}
		}

		return detail;
	}

	private Component getInformacion(final Paciente paciente_homologado,
			final Row fila, final Paciente paciente,
			final Map<String, Object> map) {
		Row row = new Row();

		// Informacion del Paciente

		row.appendChild(new Label(paciente_homologado.getTipo_identificacion()
				+ ""));
		row.appendChild(new Label(paciente_homologado.getDocumento() + ""));
		row.appendChild(new Label(paciente_homologado.getNombreCompleto()));

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(paciente_homologado
				.getCodigo_empresa());
		administradora.setCodigo_sucursal(paciente_homologado
				.getCodigo_sucursal());
		administradora
				.setCodigo(paciente_homologado.getCodigo_administradora());
		administradora = getServiceLocator().getServicio(
				AdministradoraService.class).consultar(administradora);

		row.appendChild(new Label(administradora != null ? administradora
				.getNombre() : ""));

		row.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(paciente_homologado.getFecha_nacimiento())));

		row.appendChild(new Label(
				paciente_homologado.getSexo().equals("M") ? "MASCULINO"
						: "FEMENINO"));

		row.appendChild(new Label(((paciente_homologado.getDireccion() + ""))
				.toUpperCase()));
		row.appendChild(new Label((paciente_homologado.getZona() + "")
				.equals("U") ? "URBANA" : "RURAL"));

		// Acciones
		Cell cell = new Cell();
		Hbox hbox = new Hbox();
		// Reemplazar
		Toolbarbutton toolbarbuttonUnificarRegistro = new Toolbarbutton();
		toolbarbuttonUnificarRegistro.setImage("/images/reemplazar.png");
		toolbarbuttonUnificarRegistro.setTooltiptext("Actualizar Paciente");
		toolbarbuttonUnificarRegistro.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("Esta seguro que deseas actualizar este registro?",
										"Actualizacion Registro",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													// Actualizamos los datos
													// relebantes
													Adicional_paciente adicional_paciente = (Adicional_paciente) map
															.get(IConstantes.IMPORTADOR_INTERNO_ADICIONAL_PACIENTE);

													// El homologado es el
													// paciente ya existente en
													// la base de datos
													paciente_homologado
															.setFecha_nacimiento(paciente
																	.getFecha_nacimiento());
													paciente_homologado
															.setSexo(paciente
																	.getSexo());
													paciente_homologado
															.setCodigo_dpto(paciente
																	.getCodigo_dpto());
													paciente_homologado
															.setCodigo_municipio(paciente
																	.getCodigo_municipio());
													paciente_homologado
															.setZona(paciente
																	.getZona());
													paciente_homologado
															.setDireccion(paciente
																	.getDireccion());
													paciente_homologado
															.setNivel_sisben(paciente
																	.getNivel_sisben());
													paciente_homologado
															.setTipo_identificacion(paciente
																	.getTipo_identificacion());
													paciente_homologado
															.setDocumento(paciente
																	.getDocumento());
													paciente_homologado
															.setCodigo_administradora(paciente
																	.getCodigo_administradora());
													paciente_homologado
															.setUltimo_user(usuarios
																	.getCodigo());
													paciente_homologado
															.setUltimo_update(new Timestamp(
																	Calendar.getInstance()
																			.getTimeInMillis()));
													paciente_homologado
															.setNombre1(paciente
																	.getNombre1());
													paciente_homologado
															.setNombre2(paciente
																	.getNombre2());
													paciente_homologado
															.setApellido1(paciente
																	.getApellido1());
													paciente_homologado
															.setApellido2(paciente
																	.getApellido2());

													adicional_paciente
															.setNro_identificacion(paciente_homologado
																	.getNro_identificacion());

													Map<String, Object> mapa_datos = new HashMap<String, Object>();
													mapa_datos
															.put("paciente",
																	paciente_homologado);
													mapa_datos.put("accion",
															"modificar");
													mapa_datos
															.put("listado_contratos",
																	listado_contratos);
													mapa_datos
															.put("adicional_paciente",
																	adicional_paciente);
													mapa_datos
															.put("parametros_empresa",
																	parametros_empresa);

													getServiceLocator()
															.getServicio(
																	PacienteService.class)
															.guardarDatos(
																	mapa_datos);
													desacoclarVista(fila);
												}
											}
										});
					}
				});

		// Crear nuevo registro
		hbox.appendChild(toolbarbuttonUnificarRegistro);
		cell.appendChild(hbox);
		row.appendChild(cell);

		return row;
	}

	private void desacoclarVista(Row fila_paciente) {
		rowsResultado.removeChild(fila_paciente);
	}

	private Component getActividad(Plantilla_pyp ddt_pnatilla) {
		return new Label(ddt_pnatilla != null ? (ddt_pnatilla.getCodigo_pdc()
				+ " - " + ddt_pnatilla.getNombre_pcd())
				: "ACTIVIDAD NO ENCONTRADA");
	}

	private Groupbox getGroupboxContenedor(String titulo, Vlayout vlayout) {
		Groupbox groupbox = new Groupbox();
		Caption caption = new Caption(titulo);
		caption.setStyle("color:#7b7fed");
		groupbox.appendChild(caption);
		groupbox.appendChild(vlayout);
		return groupbox;
	}

	private Label getInformacion(Esquema_vacunacion esquema_vacunacion) {
		Vacunas vacunas = new Vacunas();
		vacunas.setCodigo_empresa(esquema_vacunacion.getCodigo_empresa());
		vacunas.setCodigo_sucursal(esquema_vacunacion.getCodigo_sucursal());
		vacunas.setCodigo_procedimiento(esquema_vacunacion.getCodigo_vacuna());
		vacunas = getServiceLocator().getServicio(VacunasService.class)
				.consultar(vacunas);
		return new Label(esquema_vacunacion.getCodigo_vacuna() + " "
				+ (vacunas != null ? vacunas.getDescripcion() : ""));
	}

	private Label getInformacion(Excepciones_pyp excepciones_pyp) {
		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(excepciones_pyp
				.getCodigo_procedimiento()));
		procedimientos = getServiceLocator().getProcedimientosService()
				.consultar(procedimientos);
		return new Label(excepciones_pyp.getCodigo_procedimiento()
				+ " "
				+ (procedimientos != null ? procedimientos.getDescripcion()
						: ""));
	}

}
