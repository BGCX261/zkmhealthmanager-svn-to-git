/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.service.Enfermera_signosService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IVias_ingreso;
import com.framework.macros.ContenedorPaginasMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

/**
 * @author ferney
 * 
 */
public class Asistencial_enfermeraAction extends ZKWindow {
	@SuppressWarnings("unused")
	private static Logger log = Logger
			.getLogger(Asistencial_enfermeraAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private String modulo;

	@View
	private Auxheader auxheaderAsistencial;

	@View
	private Listbox listboxAdmisiones;
	@View
	private Datebox dtxFecha;
	@View
	private Listbox lbxAtendidas;

	@View
	private Textbox tbxValueAdmision;

	@View
	private Textbox tbxVia_ingreso;

	@View
	private Groupbox groupboxConsultar;
	@View
	private Borderlayout borderlayoutEditar;

	@View
	private Caption captionInformacion_paciente;
	@View
	private Textbox tbxIngreso;
	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxTipo_identificacion;
	@View
	private Textbox tbxFecha_ingreso;
	@View
	private Textbox tbxAseguradora;
	@View
	private Textbox tbxContrato;
	@View
	private Textbox tbxNivel_educativo;
	@View
	private Textbox tbxPertencia_etnica;
	@View
	private Textbox tbxEdad;

	@View
	private Textbox tbxSexo;

	@View
	private ContenedorPaginasMacro tabboxContendor;

	private Admision admision_seleccionada;

	private Enfermera_signos enfermera_signos_seleccionada;

	@View
	private Div divAsistencial;

	@View
	private Caption captionConsultar;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() throws Exception {
		dtxFecha.setValue(new Date());
		parametrizarResultadoPaginado();
		buscarDatos();
		if (modulo.equals("02")) {
			captionConsultar
					.setLabel("Consultar Pacientes admitidos para las notas de enfermeria");
		} else if (modulo.equals("03")) {
			captionConsultar
					.setLabel("Consultar Pacientes admitidos para el registro de medicamentos");
		} else if (modulo.equals("04")) {
			captionConsultar
					.setLabel("Consultar Pacientes admitidos para el control de signos vitales");
		} else if (modulo.equals("05")) {
			captionConsultar
					.setLabel("Consultar Pacientes admitidos para servicios ambulatorios");
		}
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		tbxVia_ingreso
				.setValue(request.getParameter(IVias_ingreso.VIA_INGRESO));
		modulo = request.getParameter("modulo");
	}

	public void accionForm(String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
			groupboxConsultar.setVisible(true);
			borderlayoutEditar.setVisible(false);
		} else {
			groupboxConsultar.setVisible(false);
			borderlayoutEditar.setVisible(true);
		}
	}

	public void accionForm2(final String accion) throws Exception {

		Messagebox.show(usuarios.getNombres()
				+ " estas seguro de realizar esta accion ? "
				+ "Si no ha guardado la informacion se perderán los cambios!",
				"Informacion ..", Messagebox.YES + Messagebox.NO,
				Messagebox.INFORMATION, new EventListener() {

					@Override
					public void onEvent(Event event) throws Exception {

						if (event.getName().equals("onYes")) {

							if (!accion.equalsIgnoreCase("registrar")) {
								buscarDatos();
								groupboxConsultar.setVisible(true);
								borderlayoutEditar.setVisible(false);
							} else {
								groupboxConsultar.setVisible(false);
								borderlayoutEditar.setVisible(true);
							}
						}
					}
				});
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Admision> listarResultados(
					Map<String, Object> parametros) {
				List<Admision> listado = getServiceLocator()
						.getAdmisionService().listarResultados(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getAdmisionService()
						.totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				String atendida = (String) lbxAtendidas.getSelectedItem()
						.getValue();
				Admision admision = (Admision) dato;
				if (modulo.equals("01")) {
					Enfermera_signos enfermera_signos = new Enfermera_signos();
					enfermera_signos.setCodigo_empresa(admision
							.getCodigo_empresa());
					enfermera_signos.setCodigo_sucursal(admision
							.getCodigo_sucursal());
					enfermera_signos.setNro_ingreso(admision.getNro_ingreso());
					enfermera_signos.setNro_identificacion(admision
							.getNro_identificacion());
					enfermera_signos = getServiceLocator().getServicio(
							Enfermera_signosService.class).consultar(
							enfermera_signos);
					if (atendida.equals("0")) {
						return crearFila(admision, enfermera_signos);
					} else if (atendida.equals("1")) {
						if (enfermera_signos != null) {
							return crearFila(admision, enfermera_signos);
						}
					} else if (atendida.equals("2")) {
						if (enfermera_signos == null) {
							return crearFila(admision, enfermera_signos);
						}
					}
				} else if (modulo.equals("02") || modulo.equals("03")
						|| modulo.equals("04") || modulo.equals("05")) {
					return crearFila(admision);
				}
				return null;
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				listboxAdmisiones, 8);
	}

	public void buscarDatos() throws Exception {
		try {
			String atendida = (String) lbxAtendidas.getSelectedItem()
					.getValue();

			boolean atendida_boolean = (atendida.equals("1") ? true : false);

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

			if (modulo.equals("01")) {
				parameters.put("atendida", false);
			} else if (modulo.equals("02")) {
				List<String> listado_vias = new ArrayList<String>();
				listado_vias.add(IVias_ingreso.URGENCIA);
				listado_vias.add(IVias_ingreso.HOSPITALIZACIONES);
				parameters.put("vias_ingreso", listado_vias);
				if (!atendida.equals("0"))
					parameters.put("atendida", atendida_boolean);
			} else if (modulo.equals("04")) {
				List<String> listado_vias = new ArrayList<String>();
				listado_vias.add(IVias_ingreso.URGENCIA);
				listado_vias.add(IVias_ingreso.HOSPITALIZACIONES);
				parameters.put("vias_ingreso", listado_vias);
				parameters.put("atendida", true);
			} else if (modulo.equals("05")) {
				List<String> listado_vias = new ArrayList<String>();
				listado_vias.add(IVias_ingreso.SERVICIOS_AMBULATORIOS);
				parameters.put("vias_ingreso", listado_vias);
			}

			if (parametros_empresa.getCargar_signos_vitales_triage()
					.equals("N")) {
				parameters.put("filtro_urgencias", "filtro_urgencias");
			}

			parameters.put("fecha", new SimpleDateFormat("yyyy-MM-dd")
					.format(dtxFecha.getValue()));

			parameters.put("paramTodo", "");
			parameters.put("value", "%"
					+ tbxValueAdmision.getValue().toUpperCase().trim() + "%");

			parameters.put("codigo_centro",
					centro_atencion_session.getCodigo_centro());

			parameters
					.put("order",
							"fecha_ingreso asc,creacion_date,apellido1,apellido2,nombre1,nombre2");

			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFila(final Admision admision,
			final Enfermera_signos enfermera_signos) {

		Paciente paciente = admision.getPaciente();

		Elemento descripcion = admision.getElemento_via_ingreso();

		String apellidos = (paciente != null ? paciente.getApellido1() + " "
				+ paciente.getApellido2() : "");
		String nombres = (paciente != null ? paciente.getNombre1() + " "
				+ paciente.getNombre2() : "");

		String estado = (enfermera_signos != null ? "Atendido" : "Pendiente");

		final Listitem listitem = new Listitem();
		listitem.setStyle("cursor:pointer");
		listitem.setValue(admision);

		listitem.appendChild(new Listcell());

		listitem.appendChild(Utilidades.obtenerListcell(
				admision != null ? admision.getNro_ingreso() : "", Label.class,
				true, true));
		listitem.appendChild(Utilidades.obtenerListcell(
				admision.getNro_identificacion(), Label.class, true, true));

		listitem.appendChild(Utilidades.obtenerListcell(apellidos, Label.class,
				true, true));
		listitem.appendChild(Utilidades.obtenerListcell(nombres, Label.class,
				true, true));
		listitem.appendChild(Utilidades.obtenerListcell(
				descripcion != null ? descripcion.getDescripcion() : "",
				Textbox.class, true, true));
		listitem.appendChild(Utilidades.obtenerListcell(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm").format(admision.getFecha_ingreso()),
				Label.class, true, true));
		listitem.appendChild(Utilidades.obtenerListcell(estado, Label.class,
				true, true));

		listitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				listitem.setSelected(true);
				onSeleccionarAdmision(listitem, enfermera_signos);

			}
		});

		return listitem;
	}

	public Listitem crearFila(final Admision admision) {
		String atendido = (admision.getAtendida() ? "Atendida" : "Pendiente");
		Paciente paciente = admision.getPaciente();
		Elemento descripcion = new Elemento();
		descripcion.setCodigo(admision.getVia_ingreso());
		descripcion.setTipo("via_ingreso");
		descripcion = getServiceLocator().getElementoService().consultar(
				descripcion);

		String apellidos = (paciente != null ? paciente.getApellido1() + " "
				+ paciente.getApellido2() : "");
		String nombres = (paciente != null ? paciente.getNombre1() + " "
				+ paciente.getNombre2() : "");

		final Listitem listitem = new Listitem();
		listitem.setStyle("cursor:pointer");
		listitem.setValue(admision);

		listitem.appendChild(new Listcell());

		listitem.appendChild(Utilidades.obtenerListcell(
				admision != null ? admision.getNro_ingreso() : "", Label.class,
				true, true));
		listitem.appendChild(Utilidades.obtenerListcell(
				admision.getNro_identificacion(), Label.class, true, true));

		listitem.appendChild(Utilidades.obtenerListcell(apellidos, Label.class,
				true, true));
		listitem.appendChild(Utilidades.obtenerListcell(nombres, Label.class,
				true, true));
		listitem.appendChild(Utilidades.obtenerListcell(
				descripcion != null ? descripcion.getDescripcion() : "",
				Textbox.class, true, true));
		listitem.appendChild(Utilidades.obtenerListcell(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm").format(admision.getFecha_ingreso()),
				Label.class, true, true));

		listitem.appendChild(Utilidades.obtenerListcell(atendido,
				Textbox.class, true, true));

		listitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				listitem.setSelected(true);
				onSeleccionarAdmision(listitem);
			}
		});

		return listitem;
	}

	public void onSeleccionarAdmision(Listitem listitem,
			Enfermera_signos enfermera_signos) throws Exception {
		try {
			Admision admision = (Admision) listitem.getValue();
			admision = getServiceLocator().getAdmisionService().consultar(
					admision);
			admision_seleccionada = admision;
			enfermera_signos_seleccionada = enfermera_signos;
			// log.info("admision_seleccionada ===> " + admision_seleccionada);
			accionForm("registrar");
			mostrarDatos();
			inicializarContenedorPaginas();
			borderlayoutEditar.invalidate();
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil
					.mensajeError(e, "Admision no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil.mensajeError(e, "Admision no valida Exception", this);
		}
	}

	public void onSeleccionarAdmision(Listitem listitem) throws Exception {
		try {
			Admision admision = (Admision) listitem.getValue();
			admision = getServiceLocator().getAdmisionService().consultar(
					admision);
			admision_seleccionada = admision;
			// log.info("admision_seleccionada ===> " + admision_seleccionada);
			accionForm("registrar");
			mostrarDatos();
			inicializarContenedorPaginas2();
			borderlayoutEditar.invalidate();
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil
					.mensajeError(e, "Admision no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil.mensajeError(e, "Admision no valida Exception", this);
		}
	}

	public void mostrarDatos() throws Exception {
		if (admision_seleccionada != null) {
			captionInformacion_paciente.setLabel(admision_seleccionada
					.getPaciente().getNombreCompleto());
			tbxIngreso.setValue(admision_seleccionada.getNro_ingreso());
			tbxNro_identificacion.setValue(admision_seleccionada
					.getNro_identificacion());

			// Agregado por jhonny
			Map<String, Integer> mapa_edades = Util
					.getEdadYYYYMMDD(admision_seleccionada.getPaciente()
							.getFecha_nacimiento());

			Integer anios = mapa_edades.get("anios");
			Integer meses = mapa_edades.get("meses");
			Integer dias = mapa_edades.get("dias");

			if (anios.intValue() == 0 && meses.intValue() == 0) {
				tbxEdad.setValue(dias + (dias == 1 ? " dia" : " dias"));
			} else if (anios.intValue() == 0) {
				tbxEdad.setValue(meses + (meses == 1 ? " mes (" : " meses (")
						+ (dias + (dias == 1 ? " dia" : " dias")) + ")");
			} else {
				int current_meses = meses.intValue() - (anios.intValue() * 12);
				tbxEdad.setValue(anios
						+ (anios == 1 ? " anio" : " Anios")
						+ (current_meses != 0 ? (" y " + current_meses + (current_meses == 1 ? " mes "
								: " meses"))
								: ""));
			}

			if (admision_seleccionada.getPaciente().getSexo() != null) {
				if (admision_seleccionada.getPaciente().getSexo().equals("M")) {
					tbxSexo.setValue("MASCULINO");
				} else if (admision_seleccionada.getPaciente().getSexo()
						.equals("F")) {
					tbxSexo.setValue("FEMENINO");
				}
			}

			// Fin Jhonny
			tbxTipo_identificacion.setValue(Utilidades.getNombreElemento(
					admision_seleccionada.getPaciente()
							.getTipo_identificacion(), "tipo_id", this));
			tbxFecha_ingreso.setValue(new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss").format(admision_seleccionada
					.getFecha_ingreso()));
			tbxAseguradora
					.setValue(admision_seleccionada.getAdministradora() != null ? admision_seleccionada
							.getAdministradora().getCodigo()
							+ " "
							+ admision_seleccionada.getAdministradora()
									.getNombre() : admision_seleccionada
							.getCodigo_administradora());
			tbxContrato
					.setValue(admision_seleccionada.getContratos() != null ? admision_seleccionada
							.getContratos().getNombre() : admision_seleccionada
							.getId_plan());

			Nivel_educativo educacion = new Nivel_educativo();
			educacion.setId(admision_seleccionada.getPaciente()
					.getCodigo_educativo());
			educacion = getServiceLocator().getNivel_educativoService()
					.consultar(educacion);

			Pertenencia_etnica etnica = new Pertenencia_etnica();
			etnica.setId(admision_seleccionada.getPaciente()
					.getPertenencia_etnica());
			etnica = getServiceLocator().getPertenencia_etnicaService()
					.consultar(etnica);

			tbxNivel_educativo.setValue(educacion != null ? educacion
					.getNombre() : "");
			tbxPertencia_etnica.setValue("("
					+ admision_seleccionada.getPaciente()
							.getPertenencia_etnica() + ") "
					+ (etnica != null ? etnica.getNombre() : ""));
		} else {
			throw new Exception("No hay una admision seleccionada");
		}

	}

	public void inicializarContenedorPaginas() throws Exception {
		tabboxContendor.cerrarTabs();
		if (admision_seleccionada != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.ENFERMERA_SIGNOS_PACIENTE,
					enfermera_signos_seleccionada);
			parametros.put(IVias_ingreso.PADRE, this);

			tabboxContendor.getTabs().setStyle(
					"background-image:url(../images/bar01.gif)");
			tabboxContendor
					.setUrlPaginaInicio("/pages/signos_enfermera_datos.zul");
			tabboxContendor.setLabelTabInicio("SIGNOS VITALES");
			tabboxContendor.inicializarInicio(false, parametros);
		}
	}

	public void inicializarContenedorPaginas2() throws Exception {
		tabboxContendor.cerrarTabs();
		if (admision_seleccionada != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("admision_seleccionada", admision_seleccionada);
			parametros.put("rol_medico", "N");
			parametros.put(IVias_ingreso.PADRE, this);

			tabboxContendor.getTabs().setStyle(
					"background-image:url(../images/bar01.gif)");
			if (modulo.equals("02")) {
				tabboxContendor
						.setUrlPaginaInicio("/pages/notas_enfermeria.zul");
				tabboxContendor.setLabelTabInicio("NOTAS DE ENFERMERIA");
			} else if (modulo.equals("03")) {
				tabboxContendor
						.setUrlPaginaInicio("/pages/registro_medicamentos.zul");
				tabboxContendor.setLabelTabInicio("REGISTROS DE MEDICAMENTOS");
			} else if (modulo.equals("04")) {
				tabboxContendor
						.setUrlPaginaInicio("/pages/control_signos_vitales.zul");
				tabboxContendor.setLabelTabInicio("CONTROL DE SIGNOS VITALES");
			} else if (modulo.equals("05")) {
				parametros.put("todas_admisiones", "todas_admisiones");
				tabboxContendor.setUrlPaginaInicio("/pages/hoja_gastos.zul");
				tabboxContendor.setLabelTabInicio("SERVICIOS AMBULATORIOS");
			}
			tabboxContendor.inicializarInicio(false, parametros);
		}
	}

	/**
	 * Este metodo me devuelve el mapa que contiene todos los componetes, que
	 * estan caragados en la vista Ejemplo: Autorizaciones, Remisiones, etc
	 * 
	 * @author Luis Miguel
	 * */
	public Map<String, Component> getMapa_componentes() {
		return tabboxContendor.getMapa_componentes();
	}
}
