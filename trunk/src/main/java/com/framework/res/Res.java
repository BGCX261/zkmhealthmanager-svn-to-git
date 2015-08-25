package com.framework.res;

import healthmanager.controller.ChangePrestadorServicioAction;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Anexo4_entidad;
import healthmanager.modelo.bean.Detalle_anexo3;
import healthmanager.modelo.bean.Detalle_orden_autorizacion;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Usuarios;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;

import com.framework.interfaces.IAccionEvento;
import com.framework.interfaces.IOnEventoListCellAutomatica;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.util.Utilidades;

public class Res {

	public static final String EVENTO_ACTUALIZAR = "_EA";

	public static Logger log = Logger.getLogger(Res.class);

	public static void reOrder(List<Map<String, Object>> maps,
			Anexo3_entidad anexo_3) throws Exception {
		System.out.println("Procedimientos a agregar: " + maps.size()
				+ " - Prestador: " + anexo_3.getCodigo_ips());
		int c_autorizados = 1;
		for (Map<String, Object> map : maps) {
			String endNumber = c_autorizados + "";

			Field fieldValue = Anexo3_entidad.class.getDeclaredField("valor"
					+ endNumber);
			Field fieldTipo = Anexo3_entidad.class.getDeclaredField("tipo_pcd"
					+ endNumber);
			Field fieldCantidad = Anexo3_entidad.class
					.getDeclaredField("cantidad" + endNumber);
			Field fieldNombre = Anexo3_entidad.class.getDeclaredField("nombre"
					+ endNumber);
			Field fieldCodigo_cups = Anexo3_entidad.class
					.getDeclaredField("codigo_cups" + endNumber);
			Field fieldAuto = Anexo3_entidad.class
					.getDeclaredField("autorizado_" + endNumber);
			Field fieldestado_pago = Anexo3_entidad.class
					.getDeclaredField("estado_cobro" + endNumber);

			fieldValue.setAccessible(true);
			fieldTipo.setAccessible(true);
			fieldCantidad.setAccessible(true);
			fieldNombre.setAccessible(true);
			fieldCodigo_cups.setAccessible(true);
			fieldAuto.setAccessible(true);
			fieldestado_pago.setAccessible(true);

			fieldCantidad.set(anexo_3,
					map.get(ChangePrestadorServicioAction._CANTIDAD));
			fieldTipo.set(anexo_3,
					map.get(ChangePrestadorServicioAction._TIPO_PCD));
			fieldValue.set(anexo_3,
					map.get(ChangePrestadorServicioAction._VALOR));
			fieldNombre.set(anexo_3,
					map.get(ChangePrestadorServicioAction._NOMBRE));
			fieldCodigo_cups.set(anexo_3,
					map.get(ChangePrestadorServicioAction._CODIGO_CUPS));
			fieldAuto.set(anexo_3,
					map.get(ChangePrestadorServicioAction._AUTORIZADO));
			fieldestado_pago.set(anexo_3,
					map.get(ChangePrestadorServicioAction._ESTADO_COBRO));

			c_autorizados++;
		}

		for (int i = c_autorizados; i <= 20; i++) {
			String endNumber = "" + i;

			Field fieldValue = Anexo3_entidad.class.getDeclaredField("valor"
					+ endNumber);
			Field fieldTipo = Anexo3_entidad.class.getDeclaredField("tipo_pcd"
					+ endNumber);
			Field fieldCantidad = Anexo3_entidad.class
					.getDeclaredField("cantidad" + endNumber);
			Field fieldNombre = Anexo3_entidad.class.getDeclaredField("nombre"
					+ endNumber);
			Field fieldCodigo_cups = Anexo3_entidad.class
					.getDeclaredField("codigo_cups" + endNumber);
			Field fieldAuto = Anexo3_entidad.class
					.getDeclaredField("autorizado_" + endNumber);
			Field fieldestado_pago = Anexo3_entidad.class
					.getDeclaredField("estado_cobro" + endNumber);

			fieldValue.setAccessible(true);
			fieldTipo.setAccessible(true);
			fieldCantidad.setAccessible(true);
			fieldNombre.setAccessible(true);
			fieldCodigo_cups.setAccessible(true);
			fieldAuto.setAccessible(true);
			fieldestado_pago.setAccessible(true);

			fieldCantidad.set(anexo_3, 0);
			fieldTipo.set(anexo_3, "");
			fieldValue.set(anexo_3, 0d);
			fieldNombre.set(anexo_3, "");
			fieldCodigo_cups.set(anexo_3, "");
			fieldAuto.set(anexo_3, true);
			fieldestado_pago.set(anexo_3, "01");
		}
	}

	/* para creacion de anexos 4 */

	public static Anexo4_entidad generarAnexo4Para(
			Anexo3_entidad anexo3_entidad, String marcado_como,
			ServiceLocatorWeb serviceLocator, Usuarios usuarios) throws Exception {
		Anexo4_entidad anexo4EntidadMedicinaGeneral = getServiciosAutorizados(
				anexo3_entidad, marcado_como, serviceLocator, usuarios);
		if (anexo4EntidadMedicinaGeneral != null) {
			serviceLocator.getAnexo4EntidadService().crear(
					anexo4EntidadMedicinaGeneral);

			// Main.actualizarConsecutivo(serviceLocator,
			// anexo3_entidad.getCodigo_empresa(),
			// anexo3_entidad.getCodigo_sucursal(), "ANEXO_4",
			// anexo4EntidadMedicinaGeneral.getCodigo());
			// Main.actualizarConsecutivo(serviceLocator,
			// anexo3_entidad.getCodigo_empresa(),
			// anexo3_entidad.getCodigo_sucursal(), "NRO_ANEXO_4",
			// anexo4EntidadMedicinaGeneral.getNumero_autorizacion());
		}
		return anexo4EntidadMedicinaGeneral;
	}

	public static String getAnexoTipo(List<Anexo4_entidad> anexo4_entidads,
			String tipo) {
		// for (Anexo4_entidad anexo4_entidad : anexo4_entidads) {
		// if(anexo4_entidad.getEstado_cobro().equals(tipo)){
		// return anexo4_entidad.getNumero_autorizacion();
		// }
		// }
		return "";
	}

	public static Anexo4_entidad getServiciosAutorizados(Anexo3_entidad anexo3,
			String marcado_como, ServiceLocatorWeb serviceLocator,
			Usuarios usuarios) throws Exception {
		String codigoAnexo4 = Main.obtenerConsecutivo(serviceLocator,
				anexo3.getCodigo_empresa(), anexo3.getCodigo_sucursal(),
				"ANEXO_4");
		String numero_solicitudAnexo4 = Main.obtenerNro_solicitud(
				serviceLocator, anexo3.getCodigo_empresa(),
				anexo3.getCodigo_sucursal(), "NRO_ANEXO_4");

		DecimalFormat decimalFormat = new DecimalFormat("0000000000");
		numero_solicitudAnexo4 = decimalFormat.format(Double
				.parseDouble(numero_solicitudAnexo4));
		Anexo4_entidad anexo4 = new Anexo4_entidad();
		anexo4.setCodigo_empresa(anexo3.getCodigo_empresa());
		anexo4.setCodigo_sucursal(anexo3.getCodigo_sucursal());
		anexo4.setCodigo(codigoAnexo4);
		// anexo4.setNumero_autorizacion(numero_solicitudAnexo4);
		// anexo4.setFecha(new
		// Timestamp(Calendar.getInstance().getTimeInMillis()));
		// anexo4.setCodigo_administradora(anexo3.getCodigo_empresa());
		// anexo4.setCodigo_solicitud(anexo3.getCodigo());
		// anexo4.setNumero_solicitud(anexo3.getNumero_solicitud());
		anexo4.setUbicacion(anexo3.getUbicacion());
		anexo4.setServicio(anexo3.getServicio());
		anexo4.setCama(anexo3.getCama());
		anexo4.setGuia_atencion(anexo3.getGuia_atencion());
		anexo4.setEstado_cobro(marcado_como);
		anexo4.setEstado("01");
		/* cargamos los procedimientos autorizados */
		boolean hayAutorizados = false;
		int c_autorizados = 1;
		for (int i = 1; i <= 20; i++) {
			String endNumber = "" + i;

			Field fieldValue = Anexo3_entidad.class.getDeclaredField("valor"
					+ endNumber);
			Field fieldTipo = Anexo3_entidad.class.getDeclaredField("tipo_pcd"
					+ endNumber);
			Field fieldCantidad = Anexo3_entidad.class
					.getDeclaredField("cantidad" + endNumber);
			Field fieldNombre = Anexo3_entidad.class.getDeclaredField("nombre"
					+ endNumber);
			Field fieldCodigo_cups = Anexo3_entidad.class
					.getDeclaredField("codigo_cups" + endNumber);
			Field fieldAuto = Anexo3_entidad.class
					.getDeclaredField("autorizado_" + endNumber);
			Field fieldestado_pago = Anexo3_entidad.class
					.getDeclaredField("estado_cobro" + endNumber);

			/* colocamos de forma accecible */
			fieldValue.setAccessible(true);
			fieldTipo.setAccessible(true);
			fieldCantidad.setAccessible(true);
			fieldNombre.setAccessible(true);
			fieldCodigo_cups.setAccessible(true);
			fieldAuto.setAccessible(true);
			fieldestado_pago.setAccessible(true);

			String estado_pago = (String) fieldestado_pago.get(anexo3);
			String codigo_cups = (String) fieldCodigo_cups.get(anexo3);
			boolean autorizado = (Boolean) fieldAuto.get(anexo3);

			boolean marca_aceptada = false;
			if (marcado_como.equals(estado_pago)) {
				marca_aceptada = true;
			}
			if (autorizado && !codigo_cups.isEmpty() && marca_aceptada) {
				String endNumberOrden = "" + c_autorizados;
				hayAutorizados = true;
				/* obtenemos valores */
				Field fieldValue4 = Anexo4_entidad.class
						.getDeclaredField("valor" + endNumberOrden);
				Field fieldTipo4 = Anexo4_entidad.class
						.getDeclaredField("tipo_pcd" + endNumberOrden);
				Field fieldCantidad4 = Anexo4_entidad.class
						.getDeclaredField("cantidad" + endNumberOrden);
				Field fieldNombre4 = Anexo4_entidad.class
						.getDeclaredField("nombre" + endNumberOrden);
				Field fieldCodigo_cups4 = Anexo4_entidad.class
						.getDeclaredField("codigo_cups" + endNumberOrden);

				/* colocamos de forma accecible */
				fieldValue4.setAccessible(true);
				fieldTipo4.setAccessible(true);
				fieldCantidad4.setAccessible(true);
				fieldNombre4.setAccessible(true);
				fieldCodigo_cups4.setAccessible(true);

				/* obtenemos valores */

				Double valor = (Double) fieldValue.get(anexo3);
				String tipo = (String) fieldTipo.get(anexo3);
				String nombre = (String) fieldNombre.get(anexo3);
				Integer cantidad = (Integer) fieldCantidad.get(anexo3);

				fieldCantidad4.set(anexo4, cantidad);
				fieldTipo4.set(anexo4, tipo);
				fieldValue4.set(anexo4, valor);
				fieldNombre4.set(anexo4, nombre);
				fieldCodigo_cups4.set(anexo4, codigo_cups);
				c_autorizados++;
			}
		}

		/* rectificamos que no se vallan valores nulos */
		for (int i = c_autorizados; i <= 20; i++) {
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
			fieldCantidad.set(anexo4, 0);
			fieldTipo.set(anexo4, "");
			fieldValue.set(anexo4, 0d);
			fieldNombre.set(anexo4, "");
			fieldCodigo_cups.set(anexo4, "");
		}
		/* fin proceso de carga de procedimeintos */

		anexo4.setPorcentaje_valor(0);
		anexo4.setSemanas_afiliacion(0);
		anexo4.setReclamo_bono("N");
		anexo4.setConcepto_moderadora("N");
		anexo4.setValor_moderadora(0);
		anexo4.setPorcentaje_moderadora(0);
		anexo4.setValor_max_moderadora(0);
		anexo4.setConcepto_copago("N");
		anexo4.setValor_copago(0);
		anexo4.setPorcentaje_copago(0);
		anexo4.setValor_max_copago(0);
		anexo4.setConcepto_recuperacion("N");
		anexo4.setValor_recuperacion(0);
		anexo4.setPorcentaje_recuperacion(0);
		anexo4.setValor_max_recuperacion(0);
		anexo4.setConcepto_otro("N");
		anexo4.setValor_otro(0);
		anexo4.setPorcentaje_otro(0);
		anexo4.setValor_max_otro(0);
		anexo4.setNombre_reporta(usuarios.getNombres() + " "
				+ usuarios.getApellidos());
		anexo4.setCargo_reporta("");
		anexo4.setTel_reporta("");
		anexo4.setCel_reporta("");
		anexo4.setCreacion_date(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		anexo4.setUltimo_update(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		anexo4.setCreacion_user(usuarios.getCodigo());
		anexo4.setUltimo_user(usuarios.getCodigo());
		// anexo4.setCodigo_ips(anexo3.getCodigo_ips());
		// anexo4.setCons_ips(anexo3.getCons_ips());
		anexo4.setEntidad(anexo3.getEntidad());
		anexo4.setLeido("N");
		return hayAutorizados ? anexo4 : null;
	}

	@SuppressWarnings("unused")
	private static boolean hayAutorizados(boolean b, String marcadoComo,
			Anexo3_entidad anexo3) {
		return false;
	}

	public static String[] getAnnos(int length) {
		String[] salida = new String[length];
		int anno = getYear();
		for (int i = 0; i < salida.length; i++) {
			salida[i] = String.valueOf(anno--);
		}
		return salida;
	}

	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static Detalles_horarios converTo(ZKSimpleCalendarEvent event) {
		Detalles_horarios detalles_horarios = new Detalles_horarios();
		detalles_horarios.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		detalles_horarios.setFecha_inicial(new Timestamp(event.getBeginDate()
				.getTime()));
		detalles_horarios.setFecha_final(new Timestamp(event.getEndDate()
				.getTime()));
		detalles_horarios.setDia_semana(0);
		detalles_horarios.setCita(event.isCita() ? "S" : "N");
		detalles_horarios.setDescripcion(event.getContent());
		detalles_horarios.setConsecutivo(event.getConsecutivo());
		detalles_horarios.setCodigo_rol(event.getCodigo_rol());
		detalles_horarios.setCodigo_programa_excepcion(event
				.getCodigo_programa_excepcion());
		detalles_horarios.setCodigo_consultorio(event.getCodigo_consulorio());
		detalles_horarios.setCodigo_centro(event.getCodigo_centro_salud());
		return detalles_horarios;
	}

	public static ZKSimpleCalendarEvent converTo(
			Detalles_horarios detallesHorarios) {
		ZKSimpleCalendarEvent calendarEvent = new ZKSimpleCalendarEvent();
		calendarEvent.setBeginDate(detallesHorarios.getFecha_inicial());
		calendarEvent.setEndDate(detallesHorarios.getFecha_final());
		calendarEvent.setCita(detallesHorarios.getCita().equals("S"));
		calendarEvent.setContent(new SimpleDateFormat("HH:mm")
				.format(detallesHorarios.getFecha_final())
				+ " "
				+ detallesHorarios.getDescripcion());
		calendarEvent.setConsecutivo(detallesHorarios.getConsecutivo());
		calendarEvent.setCodigo_consulorio(detallesHorarios
				.getCodigo_consultorio());
		calendarEvent.setCodigo_centro_salud(detallesHorarios
				.getCodigo_centro());
		calendarEvent.setCodigo_rol(detallesHorarios.getCodigo_rol());
		calendarEvent.setCodigo_programa_excepcion(detallesHorarios
				.getCodigo_programa_excepcion());
		return calendarEvent;
	}

	/**
	 * Este metodo te permite validar que el objeto no se valla ningun campo
	 * nulo
	 * 
	 * @author Luis Miguel
	 * */
	public static void insertNull(Object object) throws Exception {
		Field field[] = object.getClass().getDeclaredFields();
		for (Field fieldTemp : field) {
			fieldTemp.setAccessible(true);
			Object value = fieldTemp.get(object);
			if (value == null) {
				if (fieldTemp.getType() == String.class) {
					fieldTemp.set(object, "");
				} else if (fieldTemp.getType() == Boolean.class) {
					fieldTemp.set(object, false);
				}
			}
		}
	}

	public static <T> T ifNull(T t, Class<T> class1) {
		try {
			if (t == null) {
				return (T) class1.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public static boolean vaidarIgualdad(String... in) {
		Map<String,Object> map = new HashMap<String,Object>();
		for (String inO : in) {
			if (!inO.trim().isEmpty()) {
				if (map.containsKey(inO)) {
					return true;
				}
				map.put(inO, inO);
			}
		}
		return false;
	}

	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

	public static String getNombreMes(int mes) {
		switch (mes) {
		case 0:
			return "Enero";
		case 1:
			return "Febrero";
		case 2:
			return "Marzo";
		case 3:
			return "Abril";
		case 4:
			return "Mayo";
		case 5:
			return "Junio";
		case 6:
			return "Julio";
		case 7:
			return "Agosto";
		case 8:
			return "Septiembre";
		case 9:
			return "Octubre";
		case 10:
			return "Noviembre";
		case 11:
			return "Diciembre";
		}
		return null;
	}

	public static void sendMsjError(Throwable throwable, String nombreEmpresa,
			String modulo, String usuarioEnSesion) {
		String sender = Labels.getLabel("app.sender");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<html>"
				+ "  <head>"
				+ "    <style>"
				+ "       div{"
				+ "         border-width: 2px;"
				+ "         border-style: dashed;"
				+ "       }"
				+ "    </style>"
				+ "  </head>"
				+ "  <body>"
				+ "  <div align=\"center\">"
				+ "    <h1>HA OCURRIDO UN ERROR EN EL MODULO "
				+ modulo
				+ "</h1>"
				+ "    <h2>"
				+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(Calendar
						.getInstance().getTime()) + "</h2>"
				+ "    <h2>USUARIO " + usuarioEnSesion + "</h2>" + "    </div>"
				+ "<br/>" + "<div>" + "<br/><br/>" + "    <h7> "
				+ getStackTrace(throwable) + "</h7>" + "  </div>"
				+ "  </body> " + "</html>");

		EnviarMail.send(stringBuilder.toString(), sender, nombreEmpresa,
				"HealthManager - " + nombreEmpresa.toUpperCase(),
				"idadyou.err@gmail.com", "idadyou.err1-9");
	}

	public static Date utilDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);

		int maxday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (day > maxday) {
			calendar.set(Calendar.DAY_OF_MONTH, maxday);
		} else {
			calendar.set(Calendar.DAY_OF_MONTH, day);
		}
		return calendar.getTime();
	}

	/**
	 * Este metodo te permite clonar un objeto. Para esta instanciacion el
	 * obejto debe terner un constructor vacio.
	 * 
	 * @author Luis Miguel
	 * @param t
	 *            Generico
	 * */
	public static <T> T clonar(T t) {
		try {
			if (t != null) {
				T t2 = (T) t.getClass().newInstance();
				Field[] fields = t.getClass().getDeclaredFields();
				for (Field field : fields) {
					Field fieldClonado = t2.getClass().getDeclaredField(
							field.getName());
					fieldClonado.setAccessible(true);
					field.setAccessible(true);
					fieldClonado.set(t2, field.get(t));
				}
				return t2;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Este metodo te permite recortar una cadena de caracteres si es mayor de
	 * valor estipulado
	 * 
	 * @author Luis Miguel
	 * */
	public static String recortarCadena(String cadena, int maximo) {
		if (cadena.length() > maximo) {
			return cadena.substring(0, maximo - 1) + "...";
		} else
			return cadena;
	}

	/**
	 * Este metodo te permite inyectar los valores dependiendo del valor del
	 * campo
	 * 
	 * @author Luis Miguel
	 * */
	public static void inyectarValoresDesdeEventos(Component componente,
			final String nombreCampo, final Object objeto) {
		if (objeto != null) {
			if (componente instanceof Listbox) {
				componente.addEventListener(Events.ON_SELECT,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								inyectarValor(objeto, nombreCampo,
										getValorDesdeComponente(event
												.getTarget()));
							}
						});
			} else if (componente instanceof Doublebox) {
				componente.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								inyectarValor(objeto, nombreCampo,
										getValorDesdeComponente(event
												.getTarget()));
							}
						});
			} else if (componente instanceof Checkbox
					|| componente instanceof Radiogroup) {
				componente.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								inyectarValor(objeto, nombreCampo,
										getValorDesdeComponente(event
												.getTarget()));
							}
						});
			}
			inyectarValor(objeto, nombreCampo,
					getValorDesdeComponente(componente));
		} else {
			log.error("@inyectarValoresDesdeEventos el objeto a realizar la inyecion es nulo.");
		}
	}

	/**
	 * Este metodo me permite inyectar un valor a un objeto
	 * 
	 * @author Luis Miguel
	 * */
	private static void inyectarValor(Object objeto, String nombreCampo,
			Object valor) {
		if (valor != null) {
			try {
				Field field = objeto.getClass().getDeclaredField(nombreCampo);
				field.setAccessible(true);
				if ((valor.getClass() == boolean.class || valor.getClass() == Boolean.class)
						&& field.getType() == String.class) {
					Boolean valorBooleano = (Boolean) valor;
					if (valorBooleano != null) {
						valor = valorBooleano ? "S" : "N";
					}
				}
				field.set(objeto, valor);
			} catch (Exception e) {
				log.error("@inyectarValoresDesdeEventos campo " + nombreCampo
						+ " no encontrado para clase: " + objeto.getClass());
			}
		}
	}

	/**
	 * Este metodo me permite extraer el valor de un compoenete
	 * 
	 * @author Luis Miguel
	 * */
	private static Object getValorDesdeComponente(Component component) {
		if (component instanceof Listbox) {
			Listbox listbox = (Listbox) component;
			Listitem listitem = listbox.getSelectedItem();
			if (listitem != null) {
				Object objectValue = listitem.getValue();
				return objectValue;
			}
			return null;
		} else if (component instanceof Radiogroup) {
			Radiogroup radiogroup = (Radiogroup) component;
			Radio radio = radiogroup.getSelectedItem();
			if (radio != null) {
				if (radio.getValue() == null) {
					return radio.isChecked();
				} else {
					return radio.getValue();
				}
			}
			return null;
		} else if (component instanceof Radio) {
			Radio radio = (Radio) component;
			if (radio != null) {
				if (radio.getValue() == null) {
					return radio.isChecked();
				} else {
					return radio.getValue();
				}
			}
		} else if (component instanceof Doublebox) {
			return ((Doublebox) component).getValue();
		} else if (component instanceof Checkbox) {
			return ((Checkbox) component).isChecked();
		}
		return null;
	}

	private static void validarEstadoValor(Event event, Field field) {
		// verificamos inyeccion de evento
		IOnEventoListCellAutomatica eventoListCellAutomatica = (IOnEventoListCellAutomatica) event
				.getTarget().getAttribute(EVENTO_ACTUALIZAR);
		if (eventoListCellAutomatica != null) {
			// Este metodo permite se cualquier parte de
			// donde este el evento pueda
			// obtener el datos que se cargo
			eventoListCellAutomatica.onActualizarValor(event.getTarget(),
					field.getName(), event.getTarget().getAttribute("bean"));
		}
	}
	
	/**
	 * Este metodo me permite cargar por demanda los valores
	 * sin parametro de evento
	 * @author Luis Miguel
	 * */
	public static void cargarAutomatica(Component component, Object bean,
			String campo) {
		cargarAutomatica(component, bean, campo, null); 
	}

	/**
	 * Este metodo me permite cargar por demanda los valores
	 * con parametro de evento
	 * @author Luis Miguel
	 * */
	public static void cargarAutomatica(Component component, Object bean,
			String campo, final IAccionEvento iAccionEvento) {
		try {
			/* obtenemos el valor */
			Field field = bean.getClass().getDeclaredField(campo);
			field.setAccessible(true);
			Object valor = field.get(bean);
			component.setAttribute("bean", bean);
			component.setAttribute("field", field);
			/* inyectamos valor en compoenete */
			if (component instanceof BandBoxRowMacro) {
				// no hacer nada por el momento
			} else if (component instanceof Intbox) {
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
									if (arg0.getTarget().getAttribute(
											"NoComplementar") == null) {
										valor = 1;
										((Intbox) arg0.getTarget()).setValue(1);
									}
								}
								if (valor != null) {
									if (field.getType() == String.class) {
										field.set(bean, valor.toString());
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else if (field.getType() == valor
											.getClass()) {
										field.set(bean, valor);
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Intbox) arg0.getTarget()));
									}
								}
								validarEstadoValor(arg0, field);
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
					} else {
						if (intbox.getAttribute("NoComplementar") == null) {
							field.set(bean, 1);
						}
					}
				}
			} else if (component instanceof Doublebox) {
				Doublebox intbox = (Doublebox) component;
				intbox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor = ((Doublebox) arg0.getTarget())
										.getValue();
								Field field = (Field) ((Doublebox) arg0
										.getTarget()).getAttribute("field");
								Object bean = (Object) ((Doublebox) arg0
										.getTarget()).getAttribute("bean");
								if (valor != null) {
									if (field.getType() == valor.getClass()
											|| valor.getClass() == Double.class) {
										field.set(bean, valor);
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Doublebox) arg0.getTarget()));
									}
								}
								validarEstadoValor(arg0, field);
							}
						});
				if (valor != null) {
					if (valor instanceof Integer) {
						intbox.setValue((Integer) valor);
					} else {
						intbox.setValue((Double) valor);
					}
				} else {
					field.set(bean, 0d);
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
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Textbox) arg0.getTarget()));
									}
								}
								validarEstadoValor(arg0, field);
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
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Listbox) arg0.getTarget()));
									}
								}
								validarEstadoValor(arg0, field);
							}
						});
				if (valor != null) {
					Utilidades.setValueFrom(listbox, (String) valor);
				} else {
					if (listbox.getSelectedItem() != null) {
						field.set(bean, listbox.getSelectedItem().getValue());
					} else {
						field.set(bean, "");
					}
				}
			} else if (component instanceof Checkbox) {
				Checkbox checkbox = (Checkbox) component;
				checkbox.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor = ((Checkbox) arg0.getTarget())
										.isChecked();
								Field field = (Field) ((Checkbox) arg0
										.getTarget()).getAttribute("field");
								Object bean = (Object) ((Checkbox) arg0
										.getTarget()).getAttribute("bean");
								if (valor != null) {
									if (field.getType() == valor.getClass()) {
										field.set(bean, valor);
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else if (field.getType() == String.class) {
										field.set(bean, ((Boolean) valor ? "S"
												: "N"));
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Checkbox) arg0.getTarget()));
									}
								}
								validarEstadoValor(arg0, field);
							}
						});
				if (valor != null) {
					if (valor instanceof String) {
						checkbox.setChecked(valor.equals("S"));
					} else {
						checkbox.setChecked((Boolean) valor);
					}
				} else {
					if (field.getType() == String.class) {
						field.set(bean, checkbox.isChecked() ? "S" : "N");
					} else {
						field.set(bean, checkbox.isChecked());
					}
				}
			} else if (component instanceof Timebox) {
				final Timebox timebox = (Timebox) component;
				timebox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor = ((Timebox) arg0.getTarget())
										.getValue();
								Field field = (Field) ((Timebox) arg0
										.getTarget()).getAttribute("field");
								Object bean = (Object) ((Timebox) arg0
										.getTarget()).getAttribute("bean");
								if (valor != null) {
									if (field.getType() == valor.getClass()) {
										field.set(bean, valor);
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else if (field.getType() == String.class) {
										field.set(bean, timebox.getText());
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Timebox) arg0.getTarget()));
									}
								}
								validarEstadoValor(arg0, field);
							}
						});
				if (valor != null) {
					if (valor instanceof String) {
						timebox.setText(valor.toString());
					} else {
						timebox.setValue((Date) valor);
					}
				} else {
					if (field.getType() == String.class) {
						field.set(bean, timebox.getText());
					} else {
						field.set(bean, timebox.getValue());
					}
				}
			} else if (component instanceof Datebox) {
				final Datebox datebox = (Datebox) component;
				datebox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor = ((Datebox) arg0.getTarget())
										.getValue();
								Field field = (Field) ((Datebox) arg0
										.getTarget()).getAttribute("field");
								Object bean = (Object) ((Datebox) arg0
										.getTarget()).getAttribute("bean");
								if (valor != null) {
									if (field.getType() == valor.getClass()) {
										field.set(bean, valor);
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else if (field.getType() == Timestamp.class) {
										field.set(bean, new Timestamp(datebox
												.getValue().getTime()));
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else if (field.getType() == String.class) {
										field.set(bean, datebox.getText());
										if (iAccionEvento != null) {
											iAccionEvento.accion();
										}
									} else {
										Clients.showNotification(
												"El tipo no compatibles "
														+ field.getType()
														+ " valor: "
														+ valor.getClass(),
												((Datebox) arg0.getTarget()));
									}
								} else {
									field.set(bean, null);
								}
								validarEstadoValor(arg0, field);
							}

						});
				if (valor != null) {
					if (valor instanceof String) {
						datebox.setText(valor.toString());
					} else {
						datebox.setValue((Date) valor);
					}
				} else {
					if (field.getType() == String.class) {
						field.set(bean, datebox.getText());
					} else if (field.getType() == Timestamp.class) {
						if (datebox.getValue() != null) {
							field.set(bean, new Timestamp(datebox.getValue()
									.getTime()));
						} else {
							field.set(bean, null);
						}
					} else {
						field.set(bean, datebox.getValue());
					}
				}
			}
		} catch (Exception e) {
			Clients.showNotification(
					"Ocurrio un error reportelo a administrador del sistema, en este compoenete.",
					component);
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo me permite cargar por demanda los valores
	 * 
	 * @author Dario Perez Campillo
	 * */
	public static void cargarAutomatica(Component component,
			final Map<String, Object> mapa_datos, final String campo,
			final IAccionEvento iAccionEvento) {
		try {
			/* inyectamos valor en compoenete */
			if (component instanceof BandBoxRowMacro) {
				// no hacer nada por el momento
			} else if (component instanceof Intbox) {
				Intbox intbox = (Intbox) component;
				Iterable<EventListener<? extends Event>> iterable_eventos = intbox
						.getEventListeners(Events.ON_BLUR);
				for (EventListener<? extends Event> eventListener : iterable_eventos) {
					intbox.removeEventListener(Events.ON_BLUR, eventListener);
				}
				intbox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor_aux = ((Intbox) arg0.getTarget())
										.getValue();
								if (valor_aux != null) {
									mapa_datos.put(campo, valor_aux);
									if (iAccionEvento != null) {
										iAccionEvento.accion();
									}
								}
							}
						});
			} else if (component instanceof Spinner) {
				Spinner intbox = (Spinner) component;
				Iterable<EventListener<? extends Event>> iterable_eventos = intbox
						.getEventListeners(Events.ON_BLUR);
				for (EventListener<? extends Event> eventListener : iterable_eventos) {
					intbox.removeEventListener(Events.ON_BLUR, eventListener);
				}
				intbox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor_aux = ((Spinner) arg0.getTarget())
										.getValue();
								if (valor_aux != null) {
									mapa_datos.put(campo, valor_aux);
									if (iAccionEvento != null) {
										iAccionEvento.accion();
									}
								}
							}
						});
			} else if (component instanceof Doublebox) {
				Doublebox intbox = (Doublebox) component;
				Iterable<EventListener<? extends Event>> iterable_eventos = intbox
						.getEventListeners(Events.ON_BLUR);
				for (EventListener<? extends Event> eventListener : iterable_eventos) {
					intbox.removeEventListener(Events.ON_BLUR, eventListener);
				}
				intbox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor_aux = ((Doublebox) arg0
										.getTarget()).getValue();
								if (valor_aux != null) {
									mapa_datos.put(campo, valor_aux);
									if (iAccionEvento != null) {
										iAccionEvento.accion();
									}
								}
							}
						});
			} else if (component instanceof Textbox) {
				Textbox intbox = (Textbox) component;
				Iterable<EventListener<? extends Event>> iterable_eventos = intbox
						.getEventListeners(Events.ON_BLUR);
				for (EventListener<? extends Event> eventListener : iterable_eventos) {
					intbox.removeEventListener(Events.ON_BLUR, eventListener);
				}
				intbox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor_aux = ((Textbox) arg0.getTarget())
										.getValue();
								if (valor_aux != null) {
									mapa_datos.put(campo, valor_aux);
									if (iAccionEvento != null) {
										iAccionEvento.accion();
									}
								}
							}
						});
			} else if (component instanceof Listbox) {
				Listbox listbox = (Listbox) component;
				Iterable<EventListener<? extends Event>> iterable_eventos = listbox
						.getEventListeners(Events.ON_SELECT);
				for (EventListener<? extends Event> eventListener : iterable_eventos) {
					listbox.removeEventListener(Events.ON_SELECT, eventListener);
				}
				listbox.addEventListener(Events.ON_SELECT,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor_aux = ((Listbox) arg0.getTarget())
										.getSelectedItem().getValue();
								if (valor_aux != null) {
									mapa_datos.put(campo, valor_aux);
									if (iAccionEvento != null) {
										iAccionEvento.accion();
									}
								}
							}
						});
			} else if (component instanceof Checkbox) {
				Checkbox listbox = (Checkbox) component;
				Iterable<EventListener<? extends Event>> iterable_eventos = listbox
						.getEventListeners(Events.ON_CHECK);
				for (EventListener<? extends Event> eventListener : iterable_eventos) {
					listbox.removeEventListener(Events.ON_CHECK, eventListener);
				}
				listbox.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor_aux = ((Checkbox) arg0.getTarget())
										.isChecked();
								if (valor_aux != null) {
									mapa_datos.put(campo, valor_aux);
									if (iAccionEvento != null) {
										iAccionEvento.accion();
									}
								}
							}
						});
			} else if (component instanceof Timebox) {
				final Timebox timebox = (Timebox) component;
				Iterable<EventListener<? extends Event>> iterable_eventos = timebox
						.getEventListeners(Events.ON_BLUR);
				for (EventListener<? extends Event> eventListener : iterable_eventos) {
					timebox.removeEventListener(Events.ON_BLUR, eventListener);
				}
				timebox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor_aux = ((Timebox) arg0.getTarget())
										.getValue();
								if (valor_aux != null) {
									mapa_datos.put(campo, valor_aux);
									if (iAccionEvento != null) {
										iAccionEvento.accion();
									}
								}
							}
						});
			} else if (component instanceof Datebox) {
				final Datebox datebox = (Datebox) component;
				Iterable<EventListener<? extends Event>> iterable_eventos = datebox
						.getEventListeners(Events.ON_BLUR);
				for (EventListener<? extends Event> eventListener : iterable_eventos) {
					datebox.removeEventListener(Events.ON_BLUR, eventListener);
				}
				datebox.addEventListener(Events.ON_BLUR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								Object valor_aux = ((Datebox) arg0.getTarget())
										.getValue();
								if (valor_aux != null) {
									mapa_datos.put(campo, valor_aux);
									if (iAccionEvento != null) {
										iAccionEvento.accion();
									}
								}
							}

						});
			}
		} catch (Exception e) {
			Clients.showNotification(
					"Ocurrio un error reportelo a administrador del sistema, en este compoenete.",
					component);
			e.printStackTrace();
		}
	}

	public static void setValor(Object object, String campo, Object valor) {
		try {
			Field field = object.getClass().getDeclaredField(campo);
			field.setAccessible(true);
			field.set(object, valor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static <T> T getValor(Object object, String campo) {
		try {
			Field field = object.getClass().getDeclaredField(campo);
			field.setAccessible(true);
			return (T)field.get(object);
		} catch (Exception e) {
			log.error("Bean: " + object, e);  
			return null;
		}
	}

	/**
	 * Este metodo me permite, convertir una lista de Detalle_anexo3 a una lista
	 * de Detalle_orden_autorizacion
	 * 
	 * @author Luis Miguel
	 * */
	public static List<Detalle_orden_autorizacion> convertirListaDetalleAnexo3ADetalleOrden(
			List<Detalle_anexo3> detalle_anexo3, Anexo3_entidad anexo3_entidad) {
		List<Detalle_orden_autorizacion> autorizacions = new ArrayList<Detalle_orden_autorizacion>();
		for (Detalle_anexo3 dtt_anexo3 : detalle_anexo3) {
			Detalle_orden_autorizacion detalle_orden_autorizacion = new Detalle_orden_autorizacion();
			detalle_orden_autorizacion.setCodigo_procedimiento(dtt_anexo3
					.getCodigo_procedimiento());
			detalle_orden_autorizacion.setValor_procedimiento(dtt_anexo3
					.getValor_procedimiento());
			detalle_orden_autorizacion.setUnidades(dtt_anexo3.getUnidades());
			detalle_orden_autorizacion
					.setValor_real(dtt_anexo3.getValor_real());
			detalle_orden_autorizacion.setDescuento(dtt_anexo3.getDescuento());
			detalle_orden_autorizacion
					.setIncremento(dtt_anexo3.getIncremento());
			detalle_orden_autorizacion.setCodigo_cups(dtt_anexo3
					.getCodigo_cups());
			detalle_orden_autorizacion.setNombre_procedimiento(dtt_anexo3
					.getNombre_pcd());
			detalle_orden_autorizacion.setManual_tarifario(dtt_anexo3
					.getManual_tarifario());
			detalle_orden_autorizacion.setTipo_quirurgico(anexo3_entidad.getTipo_cirugia());
			detalle_orden_autorizacion.setObservaciones(anexo3_entidad.getObservaciones());  
			autorizacions.add(detalle_orden_autorizacion);
		}
		return autorizacions;
	}

	// public static Detalle_orden
	// convertirDttGrupoDttOrden(Detalle_grupos_procedimientos dtt_grupo,
	// Detalle_orden detalle_orden, Map<String, Object> servicios,
	// Manuales_tarifarios manuales_tarifarios) {
	// try {
	// Detalle_orden detalle = Res.clonar(detalle_orden);
	// Map<String, Object> mapProcedimieto =
	// Utilidades.getProcedimiento(manuales_tarifarios,
	// dtt_grupo.getCodigo_cups(), servicios);
	//
	// Double valor_procedimiento = (Double) mapProcedimieto.get("valor_pcd");
	// Double descuento = (Double) mapProcedimieto.get("descuento");
	// Double incremento = (Double) mapProcedimieto.get("incremento");
	// Double valor_real = (Double) mapProcedimieto.get("valor_real");
	//
	// detalle.setCodigo_procedimiento(dtt_grupo.getCodigo_cups());
	// detalle.setValor_procedimiento(valor_procedimiento);
	// detalle.setDescuento(descuento);
	// detalle.setIncremento(incremento);
	// detalle.setValor_real(valor_real);
	// detalle.setCodigo_cups(dtt_grupo.getCodigo_cups());
	// detalle.setRealizado("N");
	// detalle.setUnidades_realizadas(0);
	// return detalle;
	// } catch (Exception e) {
	// throw new HealthmanagerException(e.getMessage(), e);
	// }
	// }
	

}
