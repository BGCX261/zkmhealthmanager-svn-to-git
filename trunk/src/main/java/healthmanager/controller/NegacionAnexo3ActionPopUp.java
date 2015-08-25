package healthmanager.controller;

import healthmanager.controller.ZKWindow.View;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Anexo4_entidad;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Negacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.CargardorDeDatos;
import com.framework.res.Main;

public class NegacionAnexo3ActionPopUp extends Window implements AfterCompose {

	@View(classField = Anexo3_entidad.class, field = "codigo_paciente")
	private Textbox tbxIdentificacionPaciente;
	@View
	private Textbox tbxTipoId;
	@View
	private Textbox tbxapellido1Paciente;
	@View
	private Textbox tbxapellido2paciente;
	@View
	private Textbox tbxnombre1Paciente;
	@View
	private Textbox tbxnombre2paciente;
	@View
	private Textbox tbxdirPaciente;
	@View
	private Textbox tbxtelpaciente;
	@View
	private Datebox tbxFechNacpaciente;

	@View
	private Rows row_procediemiento;

	private Anexo3_entidad anexo3Entidad;
	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	private List<ProcedimientosAdded> procediemientoCajas;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Procedimeintos {
	}

	@Override
	public void afterCompose() {
		cargarDatosSesion();
		CargardorDeDatos.initComponents(this);
		cargarDatosPacientes();
		cargamosProcediemientos();
	}

	private class ProcedimientosAdded {
		private String codigo_cups;
		private String nombre;
		private String tipo;
		private int cantidad;
		private Field fieldAutorizado;
		private Double value;
		private String esatdo_cobro;
	}

	private void cargamosProcediemientos() {
		
		/* cargamos lso procedientos */
		try {
			row_procediemiento.getChildren().clear();
			procediemientoCajas = new ArrayList<ProcedimientosAdded>();
			Field[] fields = Anexo3_entidad.class.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Procedimeintos view = field.getAnnotation(Procedimeintos.class);
				if (view != null) {
					final String codigo_cups = "" + field.get(anexo3Entidad);
					if (!codigo_cups.isEmpty()) {
						String endNumber = field.getName().replaceAll("[^0-9]",
								"");
						//System.Out.Println("process number: " + endNumber);

						/* cargamos parametros primero */
						final Field fieldAuto = Anexo3_entidad.class
								.getDeclaredField("autorizado_" + endNumber);
						fieldAuto.setAccessible(true);
						boolean isAutorizado = (Boolean) fieldAuto
								.get(anexo3Entidad);

						if (!isAutorizado) {
							final Field fieldAutoCantidad = Anexo3_entidad.class
									.getDeclaredField("cantidad" + endNumber);
							final Field fieldAutoTipo = Anexo3_entidad.class
									.getDeclaredField("tipo_pcd" + endNumber);
							final Field fieldNombre = Anexo3_entidad.class
									.getDeclaredField("nombre" + endNumber);
							Field fieldValue = Anexo3_entidad.class
									.getDeclaredField("valor" + endNumber);
							Field fieldEstadoCobro = Anexo3_entidad.class
									.getDeclaredField("estado_cobro"
											+ endNumber);

							fieldAutoCantidad.setAccessible(true);
							fieldAutoTipo.setAccessible(true);
							fieldNombre.setAccessible(true);
							fieldValue.setAccessible(true);
							fieldEstadoCobro.setAccessible(true);

							final int cantidad = (Integer) fieldAutoCantidad
									.get(anexo3Entidad);
							final String tipo = (String) fieldAutoTipo
									.get(anexo3Entidad);
							final String nombre = (String) fieldNombre
									.get(anexo3Entidad);
							final double value = (Double) fieldValue
									.get(anexo3Entidad);
							final String estado_cobro = (String) fieldEstadoCobro
									.get(anexo3Entidad);

							/* vistas de process */
							Row row = new Row();

							Label label = new Label();
							label.setValue(""
									+ codigo_cups
									+ " - "
									+ nombre
									+ " - Cantidad (?)".replace("?", ""
											+ cantidad));
							Div div = new Div();
							div.appendChild(label);
//							div.setAlign("center");

							if (!isAutorizado) {
								/*
								 * verificamos si ya se hizo una negacion de
								 * sercio
								 */

								Negacion negacion = new Negacion();
								negacion.setCodigo_empresa(sucursal
										.getCodigo_empresa());
								negacion.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								negacion.setNro_solicitud(anexo3Entidad
										.getNumero_solicitud());
								negacion.setCodigo_servicio(codigo_cups);
								final Negacion negacionTemp = getServiceLocator()
										.getNegacionService().consultar(
												negacion);

								final Hbox hbox = new Hbox();
								if (negacionTemp != null) {
									hbox.getChildren().clear();
									label = new Label();
									label.setValue("no autorizado");
									label.setStyle("color: #ABB7DD;");
									hbox.appendChild(label);

									Image img = new Image();
									img.setSrc("/images/print_ico.gif");
									img.setTooltiptext("Imprimir Negacion");
									img.setStyle("cursor: pointer");
									img.addEventListener("onClick",
											new EventListener() {
												@Override
												public void onEvent(Event arg0)
														throws Exception {
//													imprimir(negacionTemp
//															.getCodigo());
												}
											});
									hbox.appendChild(img);
									div.appendChild(hbox);
								} else {
									/* botones add */
									final Button buttonAuto = new Button(
											"Autorizar");
									final Button buttonNoAuto = new Button(
											"Negar servicio");
									final Button buttonCancel = new Button(
											"Cancelar");

									hbox.appendChild(buttonAuto);
									hbox.appendChild(buttonNoAuto);
									div.appendChild(hbox);

									buttonAuto.addEventListener("onClick",
											new EventListener() {
												@Override
												public void onEvent(Event arg0)
														throws Exception {

													Messagebox
															.show("Estas seguro que deseas autorizar este procedimiento? ",
																	"Informacion",
																	Messagebox.YES
																			+ Messagebox.NO,
																	Messagebox.QUESTION,
																	new org.zkoss.zk.ui.event.EventListener() {
																		public void onEvent(
																				Event event)
																				throws Exception {
																			if ("onYes"
																					.equals(event
																							.getName())) {
																				hbox.getChildren()
																						.clear();
																				Label label = new Label();
																				label.setValue("Marcado como Autorizado");
																				label.setStyle("color: #ABB7DB;");
																				hbox.appendChild(label);
																				hbox.appendChild(buttonCancel);
																				// fieldAuto.set(anexo3Entidad,
																				// true);
																				// addProcediemitoAutorizado(procediemientoCajaSend,
																				// tipo,
																				// cantidad,
																				// fieldAuto);
																				addProcediemitoAutorizado(
																						codigo_cups,
																						nombre,
																						value,
																						tipo,
																						cantidad,
																						fieldAuto,
																						estado_cobro);
																			}
																		}
																	});
												}
											});

									buttonCancel.addEventListener("onClick",
											new EventListener() {
												@Override
												public void onEvent(Event arg0)
														throws Exception {

													Messagebox
															.show("Estas seguro que deseas cancelar la autorizacion de este procedimiento? ",
																	"Informacion",
																	Messagebox.YES
																			+ Messagebox.NO,
																	Messagebox.QUESTION,
																	new org.zkoss.zk.ui.event.EventListener() {
																		public void onEvent(
																				Event event)
																				throws Exception {
																			if ("onYes"
																					.equals(event
																							.getName())) {
																				hbox.getChildren()
																						.clear();
																				hbox.appendChild(buttonAuto);
																				hbox.appendChild(buttonNoAuto);
																				// fieldAuto.set(anexo3Entidad,
																				// true);
																				cancelProcessAdded(codigo_cups);
																			}
																		}
																	});
												}
											});

									buttonNoAuto.addEventListener("onClick",
											new EventListener() {
												@Override
												public void onEvent(Event arg0)
														throws Exception {
													Map params = new HashMap();
													params.put("process",
															codigo_cups);
													params.put("name", nombre);
													params.put("anexo3",
															anexo3Entidad);
													params.put("negacion", hbox);

													Component componente = Executions
															.createComponents(
																	"/pages/negacion.zul",
																	NegacionAnexo3ActionPopUp.this,
																	params);
													final Window ventana = (Window) componente;
													ventana.setWidth("90%");
													ventana.setHeight("90%");
													ventana.doModal();
												}
											});
								}
								/* agregamos */
							} else {
								label = new Label();
								label.setValue("Autorizado");
								label.setStyle("color: #ABB7DB;");
								Hbox hbox = new Hbox();
								hbox.appendChild(label);
								div.appendChild(hbox);
							}
							Cell cell = new Cell();
							cell.appendChild(div);
							row.appendChild(cell);
							row_procediemiento.appendChild(row);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void guardarProcedimientosAutorizados() throws Exception {
		if (procediemientoCajas.isEmpty()) {
			Messagebox.show(
					"Para esta accion debe primero autorizar un procedimiento",
					"Informacion", Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			autorizarPriocediento("01");
			autorizarPriocediento("02");
			autorizarPriocediento("03");
			cargamosProcediemientos();
			Messagebox.show("Procedimientos autorizados exitosamente.",
					"Informacion", Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	private void addProcediemitoAutorizado(String codigo_cups, String nombre,
			double value, String tipo, int cantidad, Field field,
			String estadoCobro) {
		ProcedimientosAdded procedimientosAdded = new ProcedimientosAdded();
		procedimientosAdded.nombre = nombre;
		procedimientosAdded.value = value;
		procedimientosAdded.codigo_cups = codigo_cups;
		procedimientosAdded.cantidad = cantidad;
		procedimientosAdded.tipo = tipo;
		procedimientosAdded.fieldAutorizado = field;
		procedimientosAdded.esatdo_cobro = estadoCobro;
		procediemientoCajas.add(procedimientosAdded);
	}

	private void cancelProcessAdded(String codigo_cups) {
		List<ProcedimientosAdded> list_temp = getNewList();
		for (ProcedimientosAdded procedimientosAdded : list_temp) {
			if (procedimientosAdded.codigo_cups.equals(codigo_cups))
				procediemientoCajas.remove(procedimientosAdded);
		}
	}

	private List<ProcedimientosAdded> getNewList() {
		List<ProcedimientosAdded> lsiList = new ArrayList<ProcedimientosAdded>();
		for (ProcedimientosAdded procedimientosAdded : procediemientoCajas) {
			lsiList.add(procedimientosAdded);
		}
		return lsiList;
	}

	protected void autorizarPriocediento(String marcado_como) throws Exception {
		

		Anexo4_entidad anexo4Entidad = new Anexo4_entidad();

		String codigo = Main.obtenerConsecutivo(getServiceLocator(),
				sucursal.getCodigo_empresa(), sucursal.getCodigo_sucursal(),
				"ANEXO_4");
		String numero_solicitud = Main.obtenerNro_solicitud(
				getServiceLocator(), sucursal.getCodigo_empresa(),
				sucursal.getCodigo_sucursal(), "NRO_ANEXO_4");

		/* hacemos aclopamientos de clases */

		anexo4Entidad.setCodigo_empresa(empresa.getCodigo_empresa());
		anexo4Entidad.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		anexo4Entidad.setCodigo(codigo);
//		anexo4Entidad.setNumero_autorizacion(numero_solicitud);
		anexo4Entidad.setFecha(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
//		anexo4Entidad.setCodigo_administradora(anexo3Entidad
//				.getCodigo_administradora());
		anexo4Entidad.setCodigo_solicitud(anexo3Entidad.getCodigo());
//		anexo4Entidad.setNumero_solicitud(anexo3Entidad.getNumero_solicitud());
		anexo4Entidad.setUbicacion(anexo3Entidad.getUbicacion());
		anexo4Entidad.setServicio(anexo3Entidad.getServicio());
		anexo4Entidad.setCama(anexo3Entidad.getCama());
		anexo4Entidad.setGuia_atencion(anexo3Entidad.getGuia_atencion());
		anexo4Entidad.setEstado_cobro(marcado_como);

		// Field[] fields = Anexo3_entidad.class.getDeclaredFields();
		// for (Field field : fields) {
		// field.setAccessible(true);
		// noAdded added = field.getAnnotation(noAdded.class);
		// if(added == null){
		// Object value = field.get(anexo3Entidad);
		// Field fieldAnexo4 =
		// Anexo4_entidad.class.getDeclaredField(field.getName());
		// fieldAnexo4.setAccessible(true);
		// fieldAnexo4.set(anexo4Entidad, value);
		// }
		// }
		/**/

		DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		numero_solicitud = decimalFormat.format(Double
				.parseDouble(numero_solicitud));
		anexo4Entidad.setCodigo_empresa(empresa.getCodigo_empresa());
		anexo4Entidad.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		anexo4Entidad.setCodigo(codigo);
//		anexo4Entidad.setNumero_autorizacion(numero_solicitud);
		anexo4Entidad.setFecha(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		anexo4Entidad.setPorcentaje_valor(0);
		anexo4Entidad.setSemanas_afiliacion(0);
		anexo4Entidad.setReclamo_bono("N");
		anexo4Entidad.setConcepto_moderadora("N");
		anexo4Entidad.setValor_moderadora(0);
		anexo4Entidad.setPorcentaje_moderadora(0);
		anexo4Entidad.setValor_max_moderadora(0);
		anexo4Entidad.setConcepto_copago("N");
		anexo4Entidad.setValor_copago(0);
		anexo4Entidad.setPorcentaje_copago(0);
		anexo4Entidad.setValor_max_copago(0);
		anexo4Entidad.setConcepto_recuperacion("N");
		anexo4Entidad.setValor_recuperacion(0);
		anexo4Entidad.setPorcentaje_recuperacion(0);
		anexo4Entidad.setValor_max_recuperacion(0);
		anexo4Entidad.setConcepto_otro("N");
		anexo4Entidad.setValor_otro(0);
		anexo4Entidad.setPorcentaje_otro(0);
		anexo4Entidad.setValor_max_otro(0);
		anexo4Entidad.setNombre_reporta(usuarios.getNombres() + " "
				+ usuarios.getApellidos());
		anexo4Entidad.setCargo_reporta("");
		anexo4Entidad.setTel_reporta("");
		anexo4Entidad.setCel_reporta("");
		anexo4Entidad.setCreacion_date(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		anexo4Entidad.setUltimo_update(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		anexo4Entidad.setCreacion_user(usuarios.getCodigo());
		anexo4Entidad.setUltimo_user(usuarios.getCodigo());
//		anexo4Entidad.setCodigo_ips(anexo3Entidad.getCodigo_ips());
//		anexo4Entidad.setCons_ips(anexo3Entidad.getCons_ips());
		anexo4Entidad.setEntidad(anexo3Entidad.getEntidad());
		anexo4Entidad.setLeido("N");

		boolean look_estado_marcado = false;
		clearFieldsProcedimiento(anexo4Entidad); // limpiamos los campos
		int z_count_procedimiento = 1;
		for (ProcedimientosAdded procedimientosAdded : procediemientoCajas) {
			if (procedimientosAdded.esatdo_cobro.equals(marcado_como)) {
				look_estado_marcado = true;
				Field fieldCantidad = Anexo4_entidad.class
						.getDeclaredField("cantidad" + z_count_procedimiento);
				Field fieldTipo = Anexo4_entidad.class
						.getDeclaredField("tipo_pcd" + z_count_procedimiento);
				Field fieldCodigoCups = Anexo4_entidad.class
						.getDeclaredField("codigo_cups" + z_count_procedimiento);
				Field fieldNombre = Anexo4_entidad.class
						.getDeclaredField("nombre" + z_count_procedimiento);
				Field fieldValue = Anexo4_entidad.class
						.getDeclaredField("valor" + z_count_procedimiento);

				/* colocamos los campos de modo accesible */
				fieldCantidad.setAccessible(true);
				fieldTipo.setAccessible(true);
				fieldCodigoCups.setAccessible(true);
				fieldNombre.setAccessible(true);
				fieldValue.setAccessible(true);

				/* agregamos valores */
				fieldCantidad.set(anexo4Entidad, procedimientosAdded.cantidad);
				fieldTipo.set(anexo4Entidad, procedimientosAdded.tipo);
				fieldCodigoCups.set(anexo4Entidad,
						procedimientosAdded.codigo_cups);
				fieldNombre.set(anexo4Entidad, procedimientosAdded.nombre);
				fieldValue.set(anexo4Entidad, procedimientosAdded.value);
				// cambiamos el estado dep campo autorizado el bean anexo3 para
				// dicho pprocedimeito
				procedimientosAdded.fieldAutorizado.set(anexo3Entidad, true);
				z_count_procedimiento++;
			}
		}

		/* rectificamos que no se vallan valores nulos */
		for (int i = z_count_procedimiento; i <= 20; i++) {
			String endNumber = "" + i;

			Field fieldValue = Anexo4_entidad.class.getDeclaredField("valor"
					+ endNumber);
			Field fieldTipo = Anexo4_entidad.class.getDeclaredField("tipo_pcd"
					+ endNumber);
			Field fieldCantidad = Anexo4_entidad.class
					.getDeclaredField("cantidad" + endNumber);
			Field fieldNombre = Anexo4_entidad.class.getDeclaredField("nombre"
					+ endNumber);
			Field fieldCodigo_cups = Anexo4_entidad.class
					.getDeclaredField("codigo_cups" + endNumber);

			/* colocamos de forma accecible */
			fieldValue.setAccessible(true);
			fieldTipo.setAccessible(true);
			fieldCantidad.setAccessible(true);
			fieldNombre.setAccessible(true);
			fieldCodigo_cups.setAccessible(true);

			/* cargamos los siuientes valores */
			fieldCantidad.set(anexo4Entidad, 0);
			fieldTipo.set(anexo4Entidad, "");
			fieldValue.set(anexo4Entidad, 0d);
			fieldNombre.set(anexo4Entidad, "");
			fieldCodigo_cups.set(anexo4Entidad, "");
		}

		if (look_estado_marcado) {
			getServiceLocator().getAnexo4EntidadService().crear(anexo4Entidad);
			getServiceLocator().getAnexo3EntidadService().actualizar(
					anexo3Entidad);
			Main.actualizarConsecutivo(getServiceLocator(),
					sucursal.getCodigo_empresa(),
					sucursal.getCodigo_sucursal(), "ANEXO_4", codigo);

			Main.actualizarConsecutivo(getServiceLocator(),
					sucursal.getCodigo_empresa(),
					sucursal.getCodigo_sucursal(), "NRO_ANEXO_4",
					numero_solicitud);
		}
	}

	private void clearFieldsProcedimiento(Anexo4_entidad anexo4Entidad) {
//		anexo4Entidad.setCantidad1(0);
//		anexo4Entidad.setTipo_pcd1("");
//		anexo4Entidad.setCodigo_cups1("");
//
//		anexo4Entidad.setCodigo_cups2("");
//		anexo4Entidad.setTipo_pcd2("");
//		anexo4Entidad.setCantidad2(0);
//
//		anexo4Entidad.setCodigo_cups3("");
//		anexo4Entidad.setTipo_pcd3("");
//		anexo4Entidad.setCantidad3(0);
//
//		anexo4Entidad.setCodigo_cups4("");
//		anexo4Entidad.setTipo_pcd4("");
//		anexo4Entidad.setCantidad4(0);
//
//		anexo4Entidad.setCodigo_cups5("");
//		anexo4Entidad.setTipo_pcd5("");
//		anexo4Entidad.setCantidad5(0);
//
//		anexo4Entidad.setCodigo_cups6("");
//		anexo4Entidad.setTipo_pcd6("");
//		anexo4Entidad.setCantidad6(0);
//
//		anexo4Entidad.setCodigo_cups7("");
//		anexo4Entidad.setTipo_pcd7("");
//		anexo4Entidad.setCantidad7(0);
//
//		anexo4Entidad.setCodigo_cups8("");
//		anexo4Entidad.setTipo_pcd8("");
//		anexo4Entidad.setCantidad8(0);
//
//		anexo4Entidad.setCodigo_cups9("");
//		anexo4Entidad.setTipo_pcd9("");
//		anexo4Entidad.setCantidad9(0);
//
//		anexo4Entidad.setCodigo_cups10("");
//		anexo4Entidad.setTipo_pcd10("");
//		anexo4Entidad.setCantidad10(0);
//
//		anexo4Entidad.setCodigo_cups11("");
//		anexo4Entidad.setTipo_pcd11("");
//		anexo4Entidad.setCantidad11(0);
//
//		anexo4Entidad.setCodigo_cups12("");
//		anexo4Entidad.setTipo_pcd12("");
//		anexo4Entidad.setCantidad12(0);
//
//		anexo4Entidad.setCodigo_cups13("");
//		anexo4Entidad.setTipo_pcd13("");
//		anexo4Entidad.setCantidad13(0);
//
//		anexo4Entidad.setCodigo_cups14("");
//		anexo4Entidad.setTipo_pcd14("");
//		anexo4Entidad.setCantidad14(0);
//
//		anexo4Entidad.setCodigo_cups15("");
//		anexo4Entidad.setTipo_pcd15("");
//		anexo4Entidad.setCantidad15(0);
//
//		anexo4Entidad.setCodigo_cups16("");
//		anexo4Entidad.setTipo_pcd16("");
//		anexo4Entidad.setCantidad16(0);
//
//		anexo4Entidad.setCodigo_cups17("");
//		anexo4Entidad.setTipo_pcd17("");
//		anexo4Entidad.setCantidad17(0);
//
//		anexo4Entidad.setCodigo_cups18("");
//		anexo4Entidad.setTipo_pcd18("");
//		anexo4Entidad.setCantidad18(0);
//
//		anexo4Entidad.setCodigo_cups19("");
//		anexo4Entidad.setTipo_pcd19("");
//		anexo4Entidad.setCantidad19(0);
//
//		anexo4Entidad.setCodigo_cups20("");
//		anexo4Entidad.setTipo_pcd20("");
//		anexo4Entidad.setCantidad20(0);
	}

	public void imprimir(String codigo) throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Negacion");
		paramRequest.put("codigo", codigo);

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	private void cargarDatosPacientes() {
		
		Map parametros = Executions.getCurrent().getArg();
		anexo3Entidad = (Anexo3_entidad) parametros.get("anexo3");
		cargamosDatosDelPaciente(anexo3Entidad.getCodigo_paciente());
	}

	private void cargamosDatosDelPaciente(String nroIdentificacion) {
		
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(empresa.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(nroIdentificacion);
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null) {
			tbxIdentificacionPaciente.setText(""
					+ paciente.getNro_identificacion());
			tbxTipoId.setText("" + paciente.getTipo_identificacion());
			tbxapellido1Paciente.setText("" + paciente.getApellido1());
			tbxapellido2paciente.setText("" + paciente.getApellido2());
			tbxnombre1Paciente.setText("" + paciente.getNombre1());
			tbxnombre2paciente.setText("" + paciente.getNombre2());
			tbxdirPaciente.setText("" + paciente.getDireccion());
			tbxtelpaciente.setText("" + paciente.getTel_res());
			tbxFechNacpaciente.setValue(paciente.getFecha_nacimiento());
		}
	}

	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

}
