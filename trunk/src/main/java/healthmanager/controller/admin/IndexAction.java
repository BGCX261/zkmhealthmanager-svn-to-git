package healthmanager.controller.admin;

import healthmanager.controller.ZKWindow;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admin;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Registros_entradas;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdminService;
import healthmanager.modelo.service.Registros_entradasService;
import healthmanager.modelo.service.Roles_usuarios_capsService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Area;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Chart;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class IndexAction extends ZKWindow {

	private static final long serialVersionUID = -9145887024839938515L;

	private static final long MILLSEG_POR_MINUTO = 60 * 1000;

	private static Logger log = Logger.getLogger(IndexAction.class);

	public static final SimpleDateFormat formatFechaHora = new SimpleDateFormat(
			"yyyy/MM/dd hh:mm:ss a");

	private boolean iniciar = false;

	private double punto_final;

	private double punto_concurrente;

	private Integer iteraciones = 0;

	private Map<String, Double> mapa_acumulados = new TreeMap<String, Double>();

	private com.framework.helper.SimpleCategoryModelHelper simpleCategoryModelHelper;

	public static final String USUARIO_ADMIN = "administrador";

	private double punto_final2;

	private double punto_concurrente2;

	private boolean iniciar2 = false;

	private Integer iteraciones2 = 0;

	private StringBuilder sb_consultas = new StringBuilder();

	@View
	private Groupbox groupboxInicio;
	@View
	private Label labelUsuario;
	@View
	private Textbox tbxLogin;
	@View
	private Textbox tbxPassword;
	@View
	private Borderlayout borderlayoutContenido;

	@View
	private Datebox dtbxFecha_inicio;

	@View
	private Datebox dtbxFecha_final;

	@View
	private Intbox ibxCantidad_usuarios;

	@View
	private Doublebox dbxPromedio_estancia;

	@View
	private Listbox lbxRangos;

	@View
	private Listbox listboxConcurrencia;

	@View
	private Spinner spinnerMinutos;

	@View
	private Listbox lbxMinutos;

	@View
	private Timer timerVerificacion;

	@View
	private Listbox listboxConexiones;

	@View
	private Textbox tbxPromedios;

	@View
	private Checkbox chkPostgres;

	@View
	private Toolbarbutton btnIniciarAux;

	@View
	private Doublebox dbxPromedio_concurrencia;

	@View
	private Toolbarbutton btnFrenar;

	@View
	private Toolbarbutton btnIniciar;

	@View
	private Spinner spinnerMinutos2;

	@View
	private Listbox lbxMinutos2;

	@View
	private Checkbox chkPostgres2;

	@View
	private Toolbarbutton btnIniciar2;

	@View
	private Toolbarbutton btnFrenar2;

	@View
	private Listbox listboxConsultas;

	@View
	private Timer timerVerificacion2;

	@View
	private Listbox listboxTotal_registros;

	@View
	private Listbox lbxMeses;

	@View
	private Listbox lbxRangos_dia;

	@View
	private Listbox listboxConcurrencia_dias;

	private StringBuilder sb_concurrencia_dias = new StringBuilder();

	@Override
	public void init() {
		verificamosSession();
		tbxLogin.setFocus(true);
		timerVerificacion.stop();
		timerVerificacion2.stop();
	}

	private void verificamosSession() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Object administrador = request.getSession().getAttribute(USUARIO_ADMIN);
		if (administrador != null) {
			if (administrador instanceof Admin) {
				Admin admin = (Admin) administrador;
				labelUsuario.setValue(admin.getNombre() + " - "
						+ admin.getApellido());
				borderlayoutContenido.setVisible(true);
				groupboxInicio.setVisible(false);
			}
		}
	}

	public void ingresar() throws Exception {
		try {
			if (tbxLogin.getValue().trim().isEmpty()
					|| tbxPassword.getValue().trim().isEmpty()) {
				throw new ValidacionRunTimeException(
						"Los campos login y password son obligatorios !!");
			}
			String login = tbxLogin.getText().trim().toUpperCase();
			String password = tbxPassword.getText().trim().toUpperCase();

			AdminService adminServiceService = getServiceLocator().getServicio(
					AdminService.class);

			Admin admin = new Admin();

			admin.setLogin(login);
			admin.setPassword(password);

			admin = adminServiceService.consultar(admin);

			if (admin == null) {
				throw new ValidacionRunTimeException(
						"Clave de usuario incorrecta !!");
			} else {
				labelUsuario.setValue(admin.getNombre() + " - "
						+ admin.getApellido());
				HttpServletRequest request = (HttpServletRequest) Executions
						.getCurrent().getNativeRequest();
				request.getSession().setAttribute(USUARIO_ADMIN, admin);
				borderlayoutContenido.setVisible(true);
				groupboxInicio.setVisible(false);
			}
		} catch (Exception r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	public void cerrarSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		request.getSession().removeAttribute(USUARIO_ADMIN);
		tbxLogin.setValue("");
		tbxPassword.setValue("");
		borderlayoutContenido.setVisible(false);
		groupboxInicio.setVisible(true);
		tbxLogin.focus();
	}

	public void consultarInformacion() {
		try {
			if (dtbxFecha_inicio.getValue() != null
					&& dtbxFecha_final.getValue() != null) {
				if (dtbxFecha_inicio.getValue().compareTo(
						dtbxFecha_final.getValue()) <= 0) {

					Timestamp fecha_inicio = new Timestamp(dtbxFecha_inicio
							.getValue().getTime());
					Timestamp fecha_final = new Timestamp(dtbxFecha_final
							.getValue().getTime());

					Map<String, Object> parametros = new HashMap<String, Object>();
					parametros.put("fecha_inicio", fecha_inicio);
					parametros.put("fecha_final", fecha_final);

					List<Registros_entradas> listado_entradas = getServiceLocator()
							.getServicio(Registros_entradasService.class)
							.listar(parametros);

					// log.info("listado_entradas ==> " +
					// listado_entradas.size());

					if (!listado_entradas.isEmpty()) {
						Date fecha_sistema = new Date();
						long sumatoria = 0L;
						for (Registros_entradas registros_entradas : listado_entradas) {
							long tiempo = 0L;
							if (registros_entradas.getFecha_egreso() != null) {
								tiempo = registros_entradas.getFecha_egreso()
										.getTime()
										- registros_entradas.getFecha_ingreso()
												.getTime();
							} else {
								tiempo = fecha_sistema.getTime()
										- registros_entradas.getFecha_ingreso()
												.getTime();
							}
							sumatoria = sumatoria + tiempo;
						}

						long resultado = sumatoria / listado_entradas.size();

						dbxPromedio_estancia.setValue(resultado
								/ MILLSEG_POR_MINUTO);

					}
					ibxCantidad_usuarios.setValue(listado_entradas.size());
				} else {
					MensajesUtil
							.mensajeAlerta("Datos errados",
									"La fecha-hora de inicio debe ser menor o igual a la fecha-hora final");
				}
			} else {
				MensajesUtil.mensajeAlerta("Datos obligatorios",
						"Debe seleccionar la fecha-hora de inicio y final");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void iniciarVerificacion() {
		MensajesUtil.notificarInformacion("Iniciando verificacion", btnIniciar);
		listboxConcurrencia.getItems().clear();
		simpleCategoryModelHelper = new com.framework.helper.SimpleCategoryModelHelper();
		long frecuencia = Integer.parseInt(lbxRangos.getSelectedItem()
				.getValue().toString())
				* MILLSEG_POR_MINUTO;
		Calendar fecha_inicio = Calendar.getInstance();
		fecha_inicio.setTime(dtbxFecha_inicio.getValue());
		Calendar fecha_final = Calendar.getInstance();
		fecha_final.setTime(dtbxFecha_final.getValue());
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		int contador = 0;
		double acumulador = 0.0;
		do {
			contador++;
			Timestamp fecha_concurrencia = new Timestamp(
					fecha_inicio.getTimeInMillis());
			parametros.put("fecha_concurrencia", fecha_concurrencia);
			// log.info("fecha_concurrencia ==> " + fecha_concurrencia);
			List<Long> listado = getServiceLocator().getServicio(
					Registros_entradasService.class).listarIds(parametros);
			crearFilaResultados(contador, fecha_inicio, listado);
			fecha_inicio.setTimeInMillis(fecha_inicio.getTimeInMillis()
					+ frecuencia);
			acumulador += listado.size();
		} while (fecha_inicio.getTimeInMillis() <= fecha_final
				.getTimeInMillis());
		dbxPromedio_concurrencia.setValue(acumulador / contador);

	}

	public void crearFilaResultados(int contador, Calendar fecha_concurrencia,
			List<Long> listado) {
		String hora = formatHora.format(fecha_concurrencia.getTime());
		Listitem listitem = new Listitem();
		listitem.appendChild(Utilidades.obtenerListcell(contador + "",
				Label.class, true, true));
		listitem.appendChild(Utilidades.obtenerListcell(
				fecha_concurrencia.getTime(), Datebox.class, true, true));
		listitem.appendChild(Utilidades.obtenerListcell(hora, Label.class,
				true, true));
		listitem.appendChild(Utilidades.obtenerListcell(listado.size() + "",
				Label.class, true, true));
		listboxConcurrencia.appendChild(listitem);
		simpleCategoryModelHelper.setValue(hora, hora, listado);
	}

	public void iniciarVerificacionPostgres() {
		MensajesUtil.notificarInformacion("Iniciando verificacion",
				btnIniciarAux);
		Integer limite = spinnerMinutos.getValue();
		punto_final = limite * 60000;
		punto_concurrente = 0;
		double rangos = Double.parseDouble(lbxMinutos.getSelectedItem()
				.getValue().toString());
		timerVerificacion.stop();
		timerVerificacion.setDelay((int) (rangos * 60000));
		timerVerificacion.start();
		iniciar = true;
		iteraciones = 0;
		listboxConexiones.getItems().clear();
		tbxPromedios.setValue("");
		mapa_acumulados.clear();
	}

	public void frenarVerificacion() {
		timerVerificacion.stop();
		MensajesUtil.notificarInformacion("Frenando verificacion", btnFrenar);
	}

	public void onTimer() {
		log.debug("ejecutando metodo onTimer()");
		if (iniciar) {
			try {
				punto_concurrente = punto_concurrente
						+ timerVerificacion.getDelay();
				iteraciones++;

				List<Map<String, Object>> listado_datos = getServiceLocator()
						.getServicio(AdminService.class)
						.getInformacionConexiones(chkPostgres.isChecked());

				log.debug("listado_datos ===> " + listado_datos.size());

				Listgroup listgroup = new Listgroup("Iteracion N° "
						+ iteraciones);
				listgroup.setStyle("font-weight:bold;color:red");
				listgroup.setOpen(true);
				listboxConexiones.appendChild(listgroup);

				for (Map<String, Object> map : listado_datos) {
					Long total = (Long) map.get("total");
					String datname = (String) map.get("datname");
					String state = (String) map.get("state");
					if (mapa_acumulados.containsKey(datname + "||" + state)) {
						Double acum = (Double) mapa_acumulados.get(datname
								+ "||" + state);
						acum = acum + total;
						mapa_acumulados.put(datname + "||" + state, acum);
					} else {
						mapa_acumulados.put(datname + "||" + state,
								total.doubleValue());
					}
					crearFilaConexiones(new Date(), datname, state, total);
				}

				tbxPromedios.setValue("");
				StringBuilder stringBuilder = new StringBuilder();

				for (String key_map : mapa_acumulados.keySet()) {
					Double acumulado = mapa_acumulados.get(key_map);
					stringBuilder.append("( ");
					stringBuilder.append(key_map.substring(0,
							key_map.lastIndexOf("||")));
					stringBuilder.append(" )");
					stringBuilder.append("\t");
					stringBuilder.append("( ");
					stringBuilder.append(key_map.substring(
							key_map.lastIndexOf("||") + 2, key_map.length()));
					stringBuilder.append(" )");
					stringBuilder.append("\t");
					stringBuilder.append("( ");
					stringBuilder.append(acumulado / iteraciones);
					stringBuilder.append(" conexiones");
					stringBuilder.append(" )").append("\n");
				}

				tbxPromedios.setText(stringBuilder.toString());

				if (punto_concurrente == punto_final) {
					timerVerificacion.stop();
					iniciar = false;
					mapa_acumulados.clear();
					iteraciones = 0;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public void crearFilaConexiones(Date fecha, String datname, String state,
			Long total) {
		Listitem listitem = new Listitem();
		listitem.appendChild(new Listcell(formatFechaHora.format(fecha)));
		listitem.appendChild(new Listcell(datname));
		listitem.appendChild(new Listcell(state));
		listitem.appendChild(new Listcell(total.toString()));
		listboxConexiones.appendChild(listitem);
		Clients.scrollIntoView(listitem);
	}

	public void iniciarVerificacionConsultas() {
		Integer limite = spinnerMinutos2.getValue();
		punto_final2 = limite * 60000;
		punto_concurrente2 = 0;
		double rangos = Double.parseDouble(lbxMinutos2.getSelectedItem()
				.getValue().toString());
		timerVerificacion2.stop();
		timerVerificacion2.setDelay((int) (rangos * 60000));
		timerVerificacion2.start();
		iniciar2 = true;
		iteraciones2 = 0;
		listboxConsultas.getItems().clear();
		sb_consultas.setLength(0);
	}

	public void frenarVerificacionConsultas() {
		timerVerificacion2.stop();
		MensajesUtil.notificarInformacion("Frenando verificacion", btnFrenar2);
	}

	public void onTimer2() {
		log.debug("ejecutando metodo onTimer2()");
		if (iniciar2) {
			try {
				punto_concurrente2 = punto_concurrente2
						+ timerVerificacion2.getDelay();
				iteraciones2++;

				List<Map<String, Object>> listado_datos = getServiceLocator()
						.getServicio(AdminService.class).getInformacionQuerys(
								chkPostgres2.isChecked());

				log.debug("listado_datos ===> " + listado_datos.size());

				Listgroup listgroup = new Listgroup("Iteracion N° "
						+ iteraciones2);
				listgroup.setStyle("font-weight:bold;color:red");
				listgroup.setOpen(true);
				listboxConsultas.appendChild(listgroup);

				crearFilaConsultas(new Date(), listado_datos.size());

				for (Map<String, Object> map : listado_datos) {
					sb_consultas.append("\n");
					String datid = (String) map.get("pid");
					String state = (String) map.get("state");
					String query = (String) map.get("query");
					sb_consultas.append(datid).append(" | ").append(state)
							.append(" | ").append(query.replace("\n", " "));
					sb_consultas.append("\n");
				}

				if (punto_concurrente2 == punto_final2) {
					timerVerificacion2.stop();
					iniciar2 = false;
					iteraciones2 = 0;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public void crearFilaConsultas(Date fecha, Integer total) {
		Listitem listitem = new Listitem();
		listitem.appendChild(new Listcell(formatFechaHora.format(fecha)));
		listitem.appendChild(new Listcell(total.toString()));
		listboxConsultas.appendChild(listitem);
		Clients.scrollIntoView(listitem);
	}

	public void exportarConsultas() {
		Filedownload.save(sb_consultas.toString(), "text/plain",
				"consultas_proceso.txt");
	}

	public void exportarPromediosSentencias() {

	}

	public void consultarCantidadRegistros() {
		try {
			listboxTotal_registros.getItems().size();
			List<Map<String, Object>> listado_datos = getServiceLocator()
					.getServicio(AdminService.class).getInformacionRegistros();
			int numero = 1;
			for (Map<String, Object> map : listado_datos) {
				String schemaname = (String) map.get("schemaname");
				String relname = (String) map.get("relname");
				Long total = (Long) map.get("total");
				Listitem listitem = new Listitem();
				listitem.appendChild(new Listcell(numero + ""));
				listitem.appendChild(new Listcell(schemaname));
				listitem.appendChild(new Listcell(relname));
				listitem.appendChild(new Listcell(total + ""));
				listitem.setValue(schemaname + " | " + relname + " | " + total);
				listboxTotal_registros.appendChild(listitem);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void exportarCantidadesRegistros() {
		StringBuilder sb_cantidades = new StringBuilder();
		for (int i = 0; i < listboxTotal_registros.getItemCount(); i++) {
			Listitem listitem = listboxTotal_registros.getItemAtIndex(i);
			String datos = listitem.getValue();
			sb_cantidades.append(datos).append("\n");
		}
		Filedownload.save(sb_cantidades.toString(), "text/plain",
				"cantidades_registros.txt");
	}

	public void iniciarVerificacionMes() {
		try {
			listboxConcurrencia_dias.getItems().clear();
			sb_concurrencia_dias.setLength(0);
			sb_concurrencia_dias
					.append("FECHA,CANTIDAD_INGRESOS,PROMEDIO DE ESTANCIA,HORA MAS CONCURRENTE,CANTIDAD DE USUARIOS CONCURRENTES,PROMEDIO DE USUARIOS CONCURRENTES\n");
			Calendar calendar = Calendar.getInstance();
			int mes = lbxMeses.getSelectedIndex();
			calendar.set(Calendar.MONTH, mes);
			int dia_maximo = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			for (int i = 1; i <= dia_maximo; i++) {
				calendar.set(Calendar.DAY_OF_MONTH, i);
				calendar.set(Calendar.HOUR, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 1);

				String columna1 = formatFecha.format(calendar.getTime());

				Timestamp fecha_inicio = new Timestamp(
						calendar.getTimeInMillis());
				calendar.set(Calendar.HOUR, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				Timestamp fecha_final = new Timestamp(
						calendar.getTimeInMillis());

				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("fecha_inicio", fecha_inicio);
				parametros.put("fecha_final", fecha_final);

				List<Registros_entradas> listado_entradas = getServiceLocator()
						.getServicio(Registros_entradasService.class).listar(
								parametros);

				String columna2 = listado_entradas.size() + "";
				String columna3 = "";
				if (!listado_entradas.isEmpty()) {
					Date fecha_sistema = new Date();
					long sumatoria = 0L;
					for (Registros_entradas registros_entradas : listado_entradas) {
						long tiempo = 0L;
						if (registros_entradas.getFecha_egreso() != null) {
							tiempo = registros_entradas.getFecha_egreso()
									.getTime()
									- registros_entradas.getFecha_ingreso()
											.getTime();
						} else {
							tiempo = fecha_sistema.getTime()
									- registros_entradas.getFecha_ingreso()
											.getTime();
						}
						sumatoria = sumatoria + tiempo;
					}

					long resultado = sumatoria / listado_entradas.size();
					columna3 = (resultado / MILLSEG_POR_MINUTO) + "";
				}

				long frecuencia = Integer.parseInt(lbxRangos_dia
						.getSelectedItem().getValue().toString())
						* MILLSEG_POR_MINUTO;

				long cantidad_mayor = -1L;
				String hora_mayor = "";

				Calendar fecha_inicio_c = Calendar.getInstance();
				fecha_inicio_c.setTime(fecha_inicio);
				Calendar fecha_final_c = Calendar.getInstance();
				fecha_final_c.setTime(fecha_final);
				parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", codigo_empresa);
				parametros.put("codigo_sucursal", codigo_sucursal);
				int contador = 0;
				double acumulador = 0.0;
				do {
					contador++;
					Timestamp fecha_concurrencia = new Timestamp(
							fecha_inicio_c.getTimeInMillis());
					parametros.put("fecha_concurrencia", fecha_concurrencia);
					// log.info("fecha_concurrencia ==> " + fecha_concurrencia);
					long cantidad = getServiceLocator().getServicio(
							Registros_entradasService.class).cantidad_entradas(
							parametros);
					if (cantidad_mayor < cantidad) {
						cantidad_mayor = cantidad;
						hora_mayor = formatHora.format(fecha_concurrencia);
					}
					fecha_inicio_c.setTimeInMillis(fecha_inicio_c
							.getTimeInMillis() + frecuencia);
					acumulador += cantidad;
				} while (fecha_inicio_c.getTimeInMillis() <= fecha_final_c
						.getTimeInMillis());

				String columna4 = hora_mayor;
				String columna5 = cantidad_mayor + "";
				String columna6 = (acumulador / contador) + "";

				crearFilaMes(columna1, columna2, columna3, columna4, columna5,
						columna6);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void crearFilaMes(String... columnas) {
		Listitem listitem = new Listitem();
		int inicio = 0;
		for (String columna : columnas) {
			listitem.appendChild(new Listcell(columna));
			if (inicio != 0)
				sb_concurrencia_dias.append(",");
			sb_concurrencia_dias.append(columna);
			inicio++;
		}
		sb_concurrencia_dias.append("\n");
		listboxConcurrencia_dias.appendChild(listitem);
	}

	public void exportarInformacionMes() {
		String mes_label = lbxMeses.getSelectedItem().getLabel();
		Filedownload.save(sb_concurrencia_dias.toString(), "text/plain",
				"concurrencia_usuarios_" + mes_label + ".csv");
	}

	public void mostrarGrafico() {
		Window window = null;
		try {
			window = (Window) Executions.createComponents(
					"/admin/index_grafico.zul", this,
					new HashMap<String, Object>());
			Chart barcharGrafico = (Chart) window.getFellow("barcharGrafico");
			barcharGrafico.setModel(simpleCategoryModelHelper);
			barcharGrafico.addEventListener(Events.ON_CLICK,
					new EventListener<MouseEvent>() {

						@Override
						public void onEvent(MouseEvent evento) throws Exception {
							Area area = (Area) evento.getAreaComponent();
							if (simpleCategoryModelHelper != null) {
								final String series_id = area.getAttribute(
										"series").toString();
								String category_id = area.getAttribute(
										"category").toString();
								final List<Long> listado_entradas = simpleCategoryModelHelper
										.getObjects(series_id + category_id);

								if (listado_entradas != null
										&& !listado_entradas.isEmpty()) {
									Messagebox.show(
											"("
													+ series_id
													+ ") cantidad de usuarios "
													+ listado_entradas.size()
													+ ". ¿Desea descargar los datos de los usuarios?",
											"Informacion area seleccionada",
											Messagebox.YES + Messagebox.NO,
											Messagebox.QUESTION,
											new org.zkoss.zk.ui.event.EventListener<Event>() {
												public void onEvent(Event event)
														throws Exception {
													if ("onYes".equals(event
															.getName())) {
														Map<String, Object> parametros = new HashMap<String, Object>();
														Map<String, Centro_atencion> mapa_centros = new HashMap<String, Centro_atencion>();

														Workbook libro_usuarios = new HSSFWorkbook();
														Sheet hoja1 = libro_usuarios
																.createSheet("usuarios");

														// cargamos formato del
														// documento
														HSSFFont fontBold = (HSSFFont) libro_usuarios
																.createFont();
														fontBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
														fontBold.setFontName(HSSFFont.FONT_ARIAL);
														fontBold.setFontHeightInPoints((short) 9);

														HSSFFont headerFont = (HSSFFont) libro_usuarios
																.createFont();
														headerFont
																.setBoldweight(Font.BOLDWEIGHT_BOLD);
														// headerFont.setColor(IndexedColors.WHITE.index);
														headerFont
																.setFontHeightInPoints((short) 10);
														headerFont
																.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
														headerFont
																.setFontName(HSSFFont.FONT_ARIAL);

														HSSFFont fontNormal = (HSSFFont) libro_usuarios
																.createFont();
														fontNormal
																.setFontName(HSSFFont.FONT_ARIAL);
														fontNormal
																.setFontHeightInPoints((short) 9);

														DataFormat format = libro_usuarios
																.createDataFormat();

														CellStyle styleInforme = libro_usuarios
																.createCellStyle();
														styleInforme
																.setFont(fontNormal);
														styleInforme
																.setAlignment(CellStyle.ALIGN_CENTER);

														HSSFCellStyle style = (HSSFCellStyle) libro_usuarios
																.createCellStyle();
														style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
														style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
														style.setBorderTop(CellStyle.BORDER_THIN);
														style.setBorderBottom(CellStyle.BORDER_THIN);
														style.setBorderLeft(CellStyle.BORDER_THIN);
														style.setBorderRight(CellStyle.BORDER_THIN);

														CellStyle styleFecha = libro_usuarios
																.createCellStyle();
														styleFecha
																.setFont(fontNormal);
														styleFecha
																.setDataFormat(format
																		.getFormat("yyyy/MM/dd hh:mm:ss a"));
														styleFecha
																.setBorderTop(CellStyle.BORDER_THIN);
														styleFecha
																.setBorderBottom(CellStyle.BORDER_THIN);
														styleFecha
																.setBorderLeft(CellStyle.BORDER_THIN);
														styleFecha
																.setBorderRight(CellStyle.BORDER_THIN);

														CellStyle styleHeader = libro_usuarios
																.createCellStyle();
														styleHeader
																.setFont(headerFont);
														styleHeader
																.setBorderTop(CellStyle.BORDER_THIN);
														styleHeader
																.setBorderBottom(CellStyle.BORDER_THIN);
														styleHeader
																.setBorderLeft(CellStyle.BORDER_THIN);
														styleHeader
																.setBorderRight(CellStyle.BORDER_THIN);

														CellStyle styleTexto = libro_usuarios
																.createCellStyle();
														styleTexto
																.setFont(fontNormal);
														styleTexto
																.setDataFormat((short) BuiltinFormats
																		.getBuiltinFormat("text"));
														styleTexto
																.setBorderTop(CellStyle.BORDER_THIN);
														styleTexto
																.setBorderBottom(CellStyle.BORDER_THIN);
														styleTexto
																.setBorderLeft(CellStyle.BORDER_THIN);
														styleTexto
																.setBorderRight(CellStyle.BORDER_THIN);

														int row = 0;
														org.apache.poi.ss.usermodel.Row filaCol = hoja1
																.createRow(row);
														row++;
														int initCol = 0;

														org.apache.poi.ss.usermodel.Cell celda = filaCol
																.createCell(
																		initCol,
																		org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
														celda.setCellValue("CODIGO USUARIO");
														celda.setCellStyle(style);
														initCol++;

														celda = filaCol
																.createCell(
																		initCol,
																		org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
														celda.setCellValue("APELLIDOS");
														celda.setCellStyle(style);
														initCol++;

														celda = filaCol
																.createCell(
																		initCol,
																		org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
														celda.setCellValue("NOMBRES");
														celda.setCellStyle(style);
														initCol++;

														celda = filaCol
																.createCell(
																		initCol,
																		org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
														celda.setCellValue("FECHA DE INICIO");
														celda.setCellStyle(style);
														initCol++;

														celda = filaCol
																.createCell(
																		initCol,
																		org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
														celda.setCellValue("FECHA FINAL");
														celda.setCellStyle(style);
														initCol++;

														celda = filaCol
																.createCell(
																		initCol,
																		org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
														celda.setCellValue("CENTROS DE SALUD");
														celda.setCellStyle(style);
														initCol++;

														for (Long id_registro : listado_entradas) {
															Registros_entradas registros_entradas = new Registros_entradas();
															registros_entradas
																	.setId_registro(id_registro);
															registros_entradas = getServiceLocator()
																	.getServicio(
																			Registros_entradasService.class)
																	.consultar(
																			registros_entradas);
															if (registros_entradas != null) {
																Usuarios usuarios_aux = new Usuarios();
																usuarios_aux
																		.setCodigo_empresa(registros_entradas
																				.getCodigo_empresa());
																usuarios_aux
																		.setCodigo_sucursal(registros_entradas
																				.getCodigo_sucursal());
																usuarios_aux
																		.setCodigo(registros_entradas
																				.getCodigo_usuario());
																usuarios_aux = getServiceLocator()
																		.getUsuariosService()
																		.consultar(
																				usuarios_aux);
																if (usuarios_aux != null) {
																	org.apache.poi.ss.usermodel.Row filaCart = hoja1
																			.createRow(row);
																	row++;
																	initCol = 0;
																	celda = filaCart
																			.createCell(
																					initCol,
																					org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
																	celda.setCellValue(usuarios_aux
																			.getCodigo());
																	celda.setCellStyle(styleTexto);
																	initCol++;
																	celda = filaCart
																			.createCell(
																					initCol,
																					org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
																	celda.setCellValue(usuarios_aux
																			.getApellidos());
																	celda.setCellStyle(styleTexto);
																	initCol++;
																	celda = filaCart
																			.createCell(
																					initCol,
																					org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
																	celda.setCellValue(usuarios_aux
																			.getNombres());
																	celda.setCellStyle(styleTexto);
																	initCol++;
																	celda = filaCart
																			.createCell(
																					initCol,
																					org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
																	celda.setCellValue(registros_entradas
																			.getFecha_ingreso());
																	celda.setCellStyle(styleFecha);
																	initCol++;
																	celda = filaCart
																			.createCell(
																					initCol,
																					org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
																	celda.setCellValue(registros_entradas
																			.getFecha_egreso());
																	celda.setCellStyle(styleFecha);
																	initCol++;

																	parametros
																			.put("codigo_empresa",
																					usuarios_aux
																							.getCodigo_empresa());
																	parametros
																			.put("codigo_sucursal",
																					usuarios_aux
																							.getCodigo_sucursal());
																	parametros
																			.put("codigo_usuario",
																					usuarios_aux
																							.getCodigo());
																	parametros
																			.put("limite_paginado",
																					"limit 5 offset 0");
																	List<Roles_usuarios_caps> listado_caps = getServiceLocator()
																			.getServicio(
																					Roles_usuarios_capsService.class)
																			.listar(parametros);
																	String centros = "";
																	for (Roles_usuarios_caps roles_usuarios_caps : listado_caps) {
																		Centro_atencion centro_atencion = null;
																		if (mapa_centros
																				.containsKey(roles_usuarios_caps
																						.getCodigo_centro())) {
																			centro_atencion = mapa_centros
																					.get(roles_usuarios_caps
																							.getCodigo_centro());
																		} else {
																			centro_atencion = new Centro_atencion();
																			centro_atencion
																					.setCodigo_centro(roles_usuarios_caps
																							.getCodigo_centro());
																			centro_atencion
																					.setCodigo_empresa(roles_usuarios_caps
																							.getCodigo_empresa());
																			centro_atencion
																					.setCodigo_sucursal(roles_usuarios_caps
																							.getCodigo_sucursal());
																			centro_atencion = getServiceLocator()
																					.getCentro_atencionService()
																					.consultar(
																							centro_atencion);
																			mapa_centros
																					.put(roles_usuarios_caps
																							.getCodigo_centro(),
																							centro_atencion);
																		}

																		if (centro_atencion != null) {
																			centros = centros
																					+ centro_atencion
																							.getNombre_centro()
																					+ "\t";
																		}
																	}

																	celda = filaCart
																			.createCell(
																					initCol,
																					org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
																	celda.setCellValue(centros);
																	celda.setCellStyle(styleTexto);
																	initCol++;

																}
															}
														}

														for (int i = 0; i < 6; i++) {
															hoja1.autoSizeColumn((short) i);
														}

														File fileDirectorio = getDirectorio();

														File archivo_excel = crearArchivo(
																fileDirectorio,
																series_id
																		+ "usuarios.xls",
																libro_usuarios);

														FileOutputStream fos = null;
														try {
															fos = new FileOutputStream(
																	archivo_excel);
															libro_usuarios
																	.write(fos);
														} catch (IOException e) {
															e.printStackTrace();
														} finally {
															if (fos != null) {
																try {
																	fos.flush();
																	fos.close();
																} catch (IOException e) {
																	e.printStackTrace();
																}
															}
														}

														byte[] fileContent = null;
														ByteArrayOutputStream theBAOS = new ByteArrayOutputStream();
														FileInputStream theFIS = null;
														try {
															theBAOS.reset();
															theFIS = new FileInputStream(
																	archivo_excel);
															@SuppressWarnings("resource")
															BufferedInputStream theBIS = new BufferedInputStream(
																	theFIS);
															byte[] buffer = new byte[4096];
															int bytesRead;
															while ((bytesRead = theBIS
																	.read(buffer)) >= 0) {
																theBAOS.write(
																		buffer,
																		0,
																		bytesRead);
															}
															theBAOS.flush();
															fileContent = theBAOS
																	.toByteArray();
														} catch (IOException e) {
															e.printStackTrace();
														} finally {
															if (theBAOS != null) {
																theBAOS.reset();
															}
															if (theFIS != null) {
																try {
																	theFIS.close();
																} catch (IOException e) {
																	e.printStackTrace();
																}
															}
														}
														Filedownload
																.save(fileContent,
																		"xlsx",
																		"listado_usuarios.xls");
														archivo_excel.delete();

														try {
															fileDirectorio
																	.deleteOnExit();
														} catch (Exception e) {
															e.printStackTrace();

														}

													}
												}
											});
								}
							}
						}

					});
			window.doModal();
		} catch (Exception e) {
			if (window != null) {
				window.detach();
			}
			MensajesUtil.mensajeError(e, "", this);

		}
	}

	private File getDirectorio() {
		File file = new File(Labels.getLabel("app.file_temp"));
		if (!file.exists()) {
			file.mkdir();
		}

		return file;
	}

	private File crearArchivo(File file, String title, Workbook libro)
			throws Exception {
		File archivo_excel = new File(file.getAbsolutePath() + File.separator
				+ title);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(archivo_excel);
			libro.write(fos);
			log.info("Generado archivo ===> " + archivo_excel.getAbsolutePath());
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return archivo_excel;
	}

}