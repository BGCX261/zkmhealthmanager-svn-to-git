package com.framework.util;

import healthmanager.controller.FrecuenciaOrdenamientoAction;
import healthmanager.controller.ZKWindow;
import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anio_soat;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Copago_estrato;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Descuentos_laboratorios;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Detalle_solicitud_tecnico;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Frecuencia_ordenamiento;
import healthmanager.modelo.bean.Grupos_iss01;
import healthmanager.modelo.bean.Grupos_quirurgicos;
import healthmanager.modelo.bean.Localidad;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_procedimientos;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.bean.Plan_tratamiento;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Pyp;
import healthmanager.modelo.bean.Reg_historias_manuales;
import healthmanager.modelo.bean.Registro_admision;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Tarifas_procedimientos;
import healthmanager.modelo.bean.Tipo_procedimiento;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Copago_estratoService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.Descuentos_laboratoriosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.EspecialidadService;
import healthmanager.modelo.service.Grupos_iss01Service;
import healthmanager.modelo.service.Grupos_quirurgicosService;
import healthmanager.modelo.service.Maestro_manualService;
import healthmanager.modelo.service.Manuales_procedimientosService;
import healthmanager.modelo.service.Manuales_tarifariosService;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.Nivel_educativoService;
import healthmanager.modelo.service.Pertenencia_etnicaService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Registro_admisionService;
import healthmanager.modelo.service.Tarifas_procedimientosService;
import healthmanager.modelo.service.Tipo_procedimientoService;
import healthmanager.modelo.service.Vacunas_pacientesService;
import imagen_diagnostica.modelo.bean.Laboratorios_resultados;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.zkoss.image.Images;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IClasificacionProcedimiento;
import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IOnEventoListCellAutomatica;
import com.framework.interfaces.ISeleccionarMedico;
import com.framework.interfaces.OnProcedimientoEvent;
import com.framework.interfaces.WindowStandar;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.imagen_diagnostica.LaboratoriosResultadosVisualizador;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.res.Res;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Bodega;
import contaweb.modelo.bean.Bodega_articulo;
import contaweb.modelo.bean.Centro_costo;
import contaweb.modelo.bean.Contabilizacion;
import contaweb.modelo.bean.Tarifa_medicamento;
import contaweb.modelo.service.ArticuloService;
import contaweb.modelo.service.Grupo1Service;
import contaweb.modelo.service.Tarifa_medicamentoService;
import healthmanager.modelo.service.GeneralExtraService;

/**
 *
 * @author Usuario
 *
 */
public class Utilidades {

	public static final String CODIGO_USUARIO = "codigo_usuario";
	public static final String NOMBRES = "nombres";
	public static final String APELLIDOS = "apellidos";
	public static final String TIPO_MEDICO = "tipo_medico";

	private static Logger log = Logger.getLogger(Utilidades.class);

	public static void setValueFrom(Listbox listbox, String valor) {
		if (valor != null) {
			for (int i = 0; i < listbox.getItemCount(); i++) {
				Listitem listitem = listbox.getItemAtIndex(i);
				if (listitem.getValue().toString().equals(valor)) {
					listitem.setSelected(true);
					break;
				}
			}
		}
	}

	public static void setValueFrom(Radiogroup radiogroup, String value) {
		if (value != null) {
			if (radiogroup.getItemCount() > 0) {
				for (int i = 0; i < radiogroup.getItemCount(); i++) {
					Radio radio = radiogroup.getItemAtIndex(i);
					if (radio.getValue().toString().equals(value)) {
						radiogroup.setSelectedItem(radio);
						i = radiogroup.getItemCount();
					}
				}
			}
		}
	}

	public static void setValueFrom(Listbox listbox, Double valor) {
		if (valor != null) {
			for (int i = 0; i < listbox.getItemCount(); i++) {
				Listitem listitem = listbox.getItemAtIndex(i);
				if (((Double) listitem.getValue()).doubleValue() == valor
						.doubleValue()) {
					listitem.setSelected(true);
					break;
				}
			}
		} else {
			// log.info("Ahi un valor nulo, ha seleccionar un listbox " +
			// listbox);
		}
	}

	public static void listarQuirurgicos(Listbox listbox, boolean select) {
		Listitem listitem = new Listitem();
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		listitem = new Listitem();
		listitem.setValue("01");
		listitem.setLabel("Incruentos");
		listbox.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("02");
		listitem.setLabel("Cruentos");
		listbox.appendChild(listitem);

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}

	}

	public static void listarRegimen(Listbox listbox, boolean select) {
		Listitem listitem = new Listitem();
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		listitem = new Listitem();
		listitem.setValue("2");
		listitem.setLabel("Subsidiado");
		listbox.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("1");
		listitem.setLabel("Contributivo");
		listbox.appendChild(listitem);

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}

	}

	public static void listarAnios(Integer anio, Listbox listbox,
			boolean select, int size, boolean selectPeriodo) {
		listbox.getChildren().clear();
		Listitem listitem = new Listitem();
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		for (String year : Res.getAnnos(size)) {
			listitem = new Listitem();
			listitem.setValue(year);
			listitem.setLabel(year);
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
		if (selectPeriodo) {
			for (int i = 0; i < listbox.getItemCount(); i++) {
				listitem = listbox.getItemAtIndex(i);
				if (listitem.getValue().toString().equals("" + anio)) {
					listitem.setSelected(true);
					break;
				}
			}
		}
	}

	public static Map<String, Object> getMedicamentos(
			Map<String, Object> params, ServiceLocatorWeb serviceLocator) {
		Map<String, Object> map_servicios = new HashMap<String, Object>();
		map_servicios.put("articuloService",
				serviceLocator.getServicio(ArticuloService.class));
		map_servicios.put("grupo1Service",
				serviceLocator.getServicio(Grupo1Service.class));
		map_servicios.put("copago_estratoService",
				serviceLocator.getServicio(Copago_estratoService.class));
		map_servicios.put("tarifa_medicamentoService",
				serviceLocator.getServicio(Tarifa_medicamentoService.class));
		return getMedicamentos(params, map_servicios);
	}

	public static Map<String, Object> getMedicamentos(
			Map<String, Object> params, Map<String, Object> servicios) {
		// parametros
		Via_ingreso_contratadas via_ingreso_contratadas = (Via_ingreso_contratadas) params
				.get("via_ingreso_contratadas");
		String codigo_articulo = (String) params.get("codigo_articulo");
		String estrato = (String) params.get("estrato"); // no es obligatorio
		// enviarlo sino se
		// utiliza va null
		String grupo = (String) params.get("grupo");

		Manuales_tarifarios manuales_tarifarios = new Manuales_tarifarios();
		manuales_tarifarios.setConsecutivo(via_ingreso_contratadas
				.getConsecutivo_manual());
		manuales_tarifarios.setCodigo_empresa(via_ingreso_contratadas
				.getCodigo_empresa());
		manuales_tarifarios.setCodigo_sucursal(via_ingreso_contratadas
				.getCodigo_sucursal());
		manuales_tarifarios.setCodigo_administradora(via_ingreso_contratadas
				.getCodigo_administradora());
		manuales_tarifarios
				.setId_contrato(via_ingreso_contratadas.getId_plan());
		manuales_tarifarios = ServiciosDisponiblesUtils.getServiceLocator()
				.getServicio(Manuales_tarifariosService.class)
				.consultar(manuales_tarifarios);

		// servicios
		ArticuloService articuloService = (ArticuloService) servicios
				.get("articuloService");
		// Grupo1Service grupo1Service = (Grupo1Service) servicios
		// .get("grupo1Service");
		Copago_estratoService copago_estratoService = (Copago_estratoService) servicios
				.get("copago_estratoService");
		Tarifa_medicamentoService tarifa_medicamentoService = (Tarifa_medicamentoService) servicios
				.get("tarifa_medicamentoService");

		Articulo art = new Articulo();
		art.setCodigo_empresa(manuales_tarifarios.getCodigo_empresa());
		art.setCodigo_sucursal(manuales_tarifarios.getCodigo_sucursal());
		art.setCodigo_articulo(codigo_articulo);
		art = articuloService.consultar(art);

		if (art != null) {
			// Grupo1 grupo1 = new Grupo1();
			// grupo1.setCodigo_empresa(art.getCodigo_empresa());
			// grupo1.setCodigo_sucursal(art.getCodigo_sucursal());
			// grupo1.setCodigo(art.getGrupo1());
			// grupo1 = grupo1Service.consultar(grupo1);

			if (grupo == null) {
				grupo = art.getGrupo1();
			}

			double valor = (int) art.getPrecio1();
			double valor_real = valor;
			double copago = 0;

			if (estrato != null) {
				Copago_estrato cop = new Copago_estrato();
				cop.setCodigo_empresa(manuales_tarifarios.getCodigo_empresa());
				cop.setCodigo_sucursal(manuales_tarifarios.getCodigo_sucursal());
				cop.setEstrato(estrato);
				cop = copago_estratoService.consultar(cop);
				copago = (cop != null ? (int) (valor) : 0);
			}

			double descuento = 0, incremento = 0;
			if (manuales_tarifarios != null) {
				if (grupo.equalsIgnoreCase("01")
						|| art.getGrupo1().equalsIgnoreCase("01")) {
					if (manuales_tarifarios.getTipo_medicamento()
							.equalsIgnoreCase("01")) {
						descuento = (int) (valor * (manuales_tarifarios
								.getMedicamentos() / 100));
						valor -= descuento;
					} else {
						incremento = (int) (valor * (manuales_tarifarios
								.getMedicamentos() / 100));
						valor += incremento;
					}
				} else if (grupo.equalsIgnoreCase("02")
						|| art.getGrupo1().equalsIgnoreCase("02")) {
					if (manuales_tarifarios.getTipo_servicio()
							.equalsIgnoreCase("01")) {
						descuento = (int) (valor * (manuales_tarifarios
								.getServicios() / 100));
						valor -= descuento;
					} else {
						incremento = (int) (valor * (manuales_tarifarios
								.getServicios() / 100));
						valor += incremento;
					}
				}

				if (manuales_tarifarios.getTarifa_especial().equals("S")) {
					Tarifa_medicamento tarifa = new Tarifa_medicamento();
					tarifa.setCodigo_empresa(manuales_tarifarios
							.getCodigo_empresa());
					tarifa.setCodigo_sucursal(manuales_tarifarios
							.getCodigo_sucursal());
					tarifa.setCodigo_administradora(manuales_tarifarios
							.getCodigo_administradora());
					tarifa.setId_plan(manuales_tarifarios.getId_contrato());
					tarifa.setCodigo_pcd(art.getCodigo_articulo());
					tarifa = tarifa_medicamentoService.consultar(tarifa);
					if (tarifa != null) {
						valor = tarifa.getValor();
						valor_real = valor;
					}
				}
			}

			/* verificamos autorizacion de medicamento */
			boolean autorizado = (((art.getPos() + "").equalsIgnoreCase("S") || (art
					.getPyp() + "").equalsIgnoreCase("S")) && (art
					.getAlto_costo() + "").equalsIgnoreCase("N"));

			Map<String, Object> medicamento = new HashMap<String, Object>();
			medicamento.put("codigo_empresa",
					manuales_tarifarios.getCodigo_empresa());
			medicamento.put("codigo_sucursal",
					manuales_tarifarios.getCodigo_sucursal());
			medicamento.put("codigo_articulo", art.getCodigo_articulo());
			medicamento.put("valor_unitario", valor);
			medicamento.put("valor_total", valor);
			medicamento.put("descuento", descuento);
			medicamento.put("incremento", incremento);
			medicamento.put("valor_real", valor_real);
			medicamento.put("copago", copago);
			medicamento.put("autorizado", autorizado);
			medicamento.put("tipo", art.getGrupo1());
			medicamento.put("bodega", "01");
			medicamento.put("pyp", art.getPyp() + "");
			medicamento.put("nombre1", art.getNombre1());
			medicamento.put("referencia", art.getReferencia());
			medicamento.put("unidad_medida", art.getUnidad_medida());
			medicamento.put("concentracion", art.getUnidad_concentracion());
			medicamento.put("facturable", art.getFacturable());
			medicamento.put("pos", art.getPos());
			medicamento.put("cantidad_maxima", art.getCantidad_maxima());
			return medicamento;
		} else {
			return null;
		}
	}

	/**
	 * Metodo para obtener el nombre del procedimiento dependiendo del manual
	 * tarifario que se este trabajando (Este metodo se debe llamar cuando se
	 * este trabajando desde el servicio)
	 *
	 * @author ?
	 * @author <b> Modificado por </b> Luis Miguel hernandez
	 *
	 */
	public static Map<String, Object> getProcedimiento(
			Via_ingreso_contratadas via_ingreso_contratadas,
			Long id_procedimiento, ServiceLocatorWeb serviceLocator)
			throws Exception {

		Manuales_tarifarios manuales_tarifarios = new Manuales_tarifarios();
		manuales_tarifarios.setConsecutivo(via_ingreso_contratadas
				.getConsecutivo_manual());
		manuales_tarifarios.setCodigo_empresa(via_ingreso_contratadas
				.getCodigo_empresa());
		manuales_tarifarios.setCodigo_sucursal(via_ingreso_contratadas
				.getCodigo_sucursal());
		manuales_tarifarios.setCodigo_administradora(via_ingreso_contratadas
				.getCodigo_administradora());
		manuales_tarifarios
				.setId_contrato(via_ingreso_contratadas.getId_plan());
		manuales_tarifarios = serviceLocator.getServicio(
				Manuales_tarifariosService.class)
				.consultar(manuales_tarifarios);
		return getProcedimiento(manuales_tarifarios, id_procedimiento,
				serviceLocator);

	}

	/**
	 * Metodo para obtener el nombre del procedimiento dependiendo del manual
	 * tarifario que se este trabajando (Este metodo se debe llamar cuando se
	 * este trabajando desde el servicio)
	 *
	 * @author ?
	 * @author <b> Modificado por </b> Luis Miguel hernandez
	 *
	 */
	public static Map<String, Object> getProcedimiento(
			Manuales_tarifarios manual, Long id_procedimiento,
			ServiceLocatorWeb serviceLocator) throws Exception {

		ProcedimientosService procedimientoService = serviceLocator
				.getServicio(ProcedimientosService.class);
		Tarifas_procedimientosService tarifas_procedimientosService = serviceLocator
				.getServicio(Tarifas_procedimientosService.class);
		Map<String, Object> bean = new HashMap<String, Object>();

		String nombre_procedimiento = "";
		String sexo_pcd = "A";
		String limite_inferior_pcd = "0";
		String limite_superior_pcd = "0";
		String es_pyp = "N";
		String grupo_uvr = "";
		String codigo_plantilla = "";
		String tipo_procedimiento = "";
		Double valor_porcentaje = 0D;
		double valor_pcd = 0;
		String codigo_cups = "";
		String consulta = "";
		String quirurgico = "";

		Manuales_procedimientos manuales_procedimientos = new Manuales_procedimientos();
		manuales_procedimientos.setId_manual(manual.getId_maestro_manual());
		manuales_procedimientos.setId_procedimiento(id_procedimiento);

		manuales_procedimientos = serviceLocator.getServicio(
				Manuales_procedimientosService.class).consultar(
				manuales_procedimientos);

		if (manuales_procedimientos != null) {
			Maestro_manual maestro_manual = new Maestro_manual();
			maestro_manual.setId_manual(manual.getId_maestro_manual());
			maestro_manual = serviceLocator.getServicio(
					Maestro_manualService.class).consultar(maestro_manual);

			Procedimientos proc = new Procedimientos();
			proc.setId_procedimiento(id_procedimiento);
			proc = procedimientoService.consultar(proc);
			nombre_procedimiento = (proc != null ? proc.getDescripcion() : "");
			sexo_pcd = (proc != null ? proc.getSexo() : "");
			limite_inferior_pcd = proc.getLimite_inferior();
			limite_superior_pcd = proc.getLimite_superior();
			es_pyp = (proc != null ? proc.getPyp() : "");
			codigo_plantilla = (proc != null ? proc.getCodigo_contabilidad()
					: "");
			tipo_procedimiento = (proc != null ? proc.getTipo_procedimiento()
					: "");
			valor_porcentaje = manuales_procedimientos.getValor();
			codigo_cups = proc != null ? proc.getCodigo_cups() : "";
			consulta = proc != null ? proc.getConsulta() : "";
			quirurgico = proc != null ? proc.getQuirurgico() : "";

			if (maestro_manual.getTipo_manual().equals(
					IConstantes.TIPO_MANUAL_SOAT)) {
				Anio_soat anio_soat = new Anio_soat();
				anio_soat.setAnio(manual.getAnio());
				anio_soat = serviceLocator.getServicio(
						GeneralExtraService.class).consultar(anio_soat);
				valor_pcd = (anio_soat != null ? (int) (anio_soat
						.getValor_anio() * valor_porcentaje) : 0);
			} else if (maestro_manual.getTipo_manual().equals(
					IConstantes.TIPO_MANUAL_ISS01)
					|| maestro_manual.getTipo_manual().equals(
							IConstantes.TIPO_MANUAL_ISSEXT)) {
				valor_pcd = manuales_procedimientos.getValor();
			} else if (maestro_manual.getTipo_manual().equals(
					IConstantes.TIPO_MANUAL_ISS04)) {
				valor_pcd = manuales_procedimientos != null ? manuales_procedimientos
						.getValor() * 100 : 0;
			}

			double valor_real = valor_pcd;
			double descuento = 0, incremento = 0;
			if (manual != null) {
				if (proc.getConsulta().equalsIgnoreCase("S")) {// Cuando es una
					// consulta
					if (manual.getTipo_consulta().equalsIgnoreCase("01")) {
						descuento = (int) (valor_pcd * (manual.getConsulta() / 100));
						valor_pcd -= descuento;
					} else if (manual.getTipo_consulta().equalsIgnoreCase("02")) {
						incremento = (int) (valor_pcd * (manual.getConsulta() / 100));
						valor_pcd += incremento;
					}
				} else {// Procedimiento
					if (manual.getTipo_general().equalsIgnoreCase("01")) {
						descuento = (int) (valor_pcd * (manual.getGeneral() / 100));
						valor_pcd -= descuento;
					} else if (manual.getTipo_general().equalsIgnoreCase("02")) {
						incremento = (int) (valor_pcd * (manual.getGeneral() / 100));
						valor_pcd += incremento;
					}
				}

				// si se manejan tarifas especiales
				// va ha tomar dicho valor
				if (manual.getTarifa_especial().equalsIgnoreCase("S")
						&& proc.getQuirurgico().equalsIgnoreCase("N")) {
					Tarifas_procedimientos tarifas_procedimientos = new Tarifas_procedimientos();
					tarifas_procedimientos.setCodigo_empresa(manual
							.getCodigo_empresa());
					tarifas_procedimientos.setCodigo_sucursal(manual
							.getCodigo_sucursal());
					tarifas_procedimientos.setCodigo_administradora(manual
							.getCodigo_administradora());
					tarifas_procedimientos.setId_plan(manual.getId_contrato());
					tarifas_procedimientos
							.setId_procedimiento(id_procedimiento);
					tarifas_procedimientos.setConsecutivo_manual(manual
							.getConsecutivo());
					tarifas_procedimientos = tarifas_procedimientosService
							.consultar(tarifas_procedimientos);

					if (tarifas_procedimientos != null) {
						if (!tarifas_procedimientos.getDescripcion().isEmpty()) {
							nombre_procedimiento = tarifas_procedimientos
									.getDescripcion();
						}
						valor_pcd = tarifas_procedimientos.getValor();
					}
				}

				if (manual.getAplica_descuentos().equalsIgnoreCase("S")) {
					// Incluimos los descuentos
					Descuentos_laboratorios descuentos_laboratorios = new Descuentos_laboratorios();
					descuentos_laboratorios.setCodigo_empresa(manual
							.getCodigo_empresa());
					descuentos_laboratorios.setCodigo_sucursal(manual
							.getCodigo_sucursal());
					descuentos_laboratorios.setCodigo_administradora(manual
							.getCodigo_administradora());
					descuentos_laboratorios.setId_contrato(manual
							.getId_contrato());
					descuentos_laboratorios.setConsecutivo_manual(manual
							.getConsecutivo());
					descuentos_laboratorios.setCodigo_procedimiento(proc
							.getId_procedimiento() + "");
					descuentos_laboratorios = serviceLocator.getServicio(
							Descuentos_laboratoriosService.class).consultar(
							descuentos_laboratorios);
					if (descuentos_laboratorios != null) {
						descuento = valor_pcd
								* descuentos_laboratorios
										.getPorcentaje_descuento() / 100d;
						valor_pcd = valor_pcd - descuento;
					}
				}
			}

			grupo_uvr = manuales_procedimientos.getGrupo_uvr();

			bean.put("nombre_procedimiento", nombre_procedimiento);
			bean.put("sexo_pcd", sexo_pcd);
			bean.put("limite_inferior_pcd", limite_inferior_pcd);
			bean.put("limite_superior_pcd", limite_superior_pcd);
			bean.put("es_pyp", es_pyp);
			bean.put("grupo_uvr", grupo_uvr);
			bean.put("codigo_plantilla", codigo_plantilla);
			bean.put("tipo_procedimiento", tipo_procedimiento);
			bean.put("valor_porcentaje", valor_porcentaje);
			bean.put("valor_pcd", valor_pcd);
			bean.put("descuento", descuento);
			bean.put("incremento", incremento);
			bean.put("valor_real", valor_real);
			bean.put("codigo_cups", codigo_cups);
			bean.put("codigo_manual",
					manuales_procedimientos.getCodigo_manual());
			bean.put("id_procedimiento", id_procedimiento);
			bean.put("consulta", consulta);
			bean.put("quirurgico", quirurgico);
			bean.put("cantidad_maxima",
					proc != null ? proc.getCantidad_maxima() : 0);
		} else {
			Procedimientos procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(id_procedimiento);
			procedimientos = procedimientoService.consultar(procedimientos);

			throw new ValidacionRunTimeException(
					"El procedimiento "
							+ (procedimientos != null ? procedimientos
									.getCodigo_cups()
									+ " "
									+ procedimientos.getDescripcion()
									: id_procedimiento)
							+ " que intenta consultar no se encuentra relacionado con el manual tarifario en cuestion");
		}

		return bean;

	}

	public static void getBeanFromView(Window window, Class<?> bean,
			Object objectBean) {
		try {
			Field[] fields = window.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				View view = field.getAnnotation(View.class);
				if (view != null) {
					if (view.classField() == bean
							&& !view.field().equalsIgnoreCase("$")) {
						Object object = field.get(window);
						if (object != null) {
							if (object instanceof Textbox) {
								Textbox textbox = (Textbox) object;
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								String value = "" + field2.get(objectBean);
								textbox.setValue(value);
							} else if (object instanceof Bandbox) {
								Bandbox textbox = (Bandbox) object;
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								String value = "" + field2.get(objectBean);
								textbox.setValue(value + "");

							} else if (object instanceof Label) {
								Label textbox = (Label) object;
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								String value = "" + field2.get(objectBean);
								textbox.setValue(value + "");
							} else if (object instanceof Intbox) {
								Intbox textbox = (Intbox) object;
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								String value = "" + field2.get(objectBean);
								textbox.setText(value + "");
							} else if (object instanceof Datebox) {
								Datebox datebox = (Datebox) object;
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								if (field2.get(objectBean) != null) {
									Timestamp fecha = (Timestamp) field2
											.get(objectBean);
									datebox.setValue(fecha);
								}
							} else if (object instanceof Listbox) {
								Listbox listbox = (Listbox) object;
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								if (field2.get(objectBean) != null) {
									String value = "" + field2.get(objectBean);
									if (listbox.getItemCount() > 0) {
										for (int i = 0; i < listbox
												.getItemCount(); i++) {
											Listitem listitem = listbox
													.getItemAtIndex(i);
											if (listitem.getValue().toString()
													.equals(value)) {
												listitem.setSelected(true);
												i = listbox.getItemCount();
											}
										}
									}

								}
							} else if (object instanceof Radiogroup) {
								Radiogroup radiogroup = (Radiogroup) object;
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								if (field2.getType().toString()
										.equals("class java.lang.Boolean")) {
									if (field2.get(objectBean) != null) {
										String value = (((Boolean) field2
												.get(objectBean)) ? "S" : "N");
										if (radiogroup.getItemCount() > 0) {
											for (int i = 0; i < radiogroup
													.getItemCount(); i++) {
												Radio radio = radiogroup
														.getItemAtIndex(i);
												if (radio.getValue().toString()
														.equals(value)) {
													radiogroup
															.setSelectedItem(radio);
													i = radiogroup
															.getItemCount();
												}
											}
										}
									}
								}

							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void showBeanToView(Window window, Class<?> bean,
			Object objectBean) {
		try {
			Field[] fields = window.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				View view = field.getAnnotation(View.class);
				if (view != null) {
					if (view.classField() == bean
							&& !view.field().equalsIgnoreCase("$")) {
						Object object = field.get(window);
						if (object != null) {
							if (object instanceof Textbox) {
								Textbox textbox = (Textbox) object;
								String value = textbox.getValue();
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								field2.set(objectBean, value);
							} else if (object instanceof Datebox) {
								Datebox datebox = (Datebox) object;
								if (datebox.getValue() != null) {
									Timestamp fecha = new Timestamp(datebox
											.getValue().getTime());
									Field field2 = bean.getDeclaredField(view
											.field());
									field2.setAccessible(true);
									field2.set(objectBean, fecha);
								}

							} else if (object instanceof Listbox) {
								Listbox listbox = (Listbox) object;
								if (listbox.getSelectedItem().getValue() != null) {
									String value = listbox.getSelectedItem()
											.getValue().toString();
									Field field2 = bean.getDeclaredField(view
											.field());
									field2.setAccessible(true);
									field2.set(objectBean, value);
								}

							} else if (object instanceof Radiogroup) {
								Radiogroup radio = (Radiogroup) object;
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);

								if (field2.getType().toString()
										.equals("class java.lang.Boolean")) {
									if (radio.getSelectedItem().getValue() != null) {
										String value = radio.getSelectedItem()
												.getValue().toString();
										field2.set(objectBean, (value
												.equals("S") ? true : false));
									} else {
										field2.set(objectBean, false);
									}
								}
							} else if (object instanceof Checkbox) {
								Checkbox textbox = (Checkbox) object;
								boolean value = textbox.isChecked();
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								if (view.isStringCheck()) {
									field2.set(objectBean, value ? "S" : "N");
								} else {
									field2.set(objectBean, value);
								}
							} else if (object instanceof Bandbox) {
								Bandbox textbox = (Bandbox) object;
								String value = textbox.getValue();
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								field2.set(objectBean, value);
							} else if (object instanceof Label) {
								Label textbox = (Label) object;
								String value = textbox.getValue();
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								field2.set(objectBean, value);
							} else if (object instanceof Intbox) {
								Intbox textbox = (Intbox) object;
								Integer value = textbox.getValue();
								if (value == null) {
									value = 0;
								}
								Field field2 = bean.getDeclaredField(view
										.field());
								field2.setAccessible(true);
								field2.set(objectBean, value.intValue());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Map<String, Object> validarInformacionLimiteSexo(String tipo,
			String codigo, String limite_inferior, String limite_superior,
			String sexo, String fecha_nac, String sexo_pct) {
		Map<String, Object> resultado = new HashMap<String, Object>();
		boolean success = true;
		String mensaje = "";
		if (!codigo.trim().equals("")) {
			// Validamos si la edad corresponde al dx con limite inferior//
			if (!limite_inferior.equals("0") && !limite_inferior.equals("000")
					&& !limite_inferior.equals("")) {
				String unidad_medida = limite_inferior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Utilidades.getEdad2(fecha_nac, unidad_medida);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) < Integer
							.parseInt(limite_inferior.substring(1))) {
						mensaje = tipo + " " + codigo + " "
								+ " no corresponde con la edad del paciente ("
								+ edad + ").\n";
						success = false;
					}
				}
			}

			// Validamos si la edad corresponde al dx con limite superior//
			if (!limite_superior.equals("599") && !limite_superior.equals("0")
					&& !limite_superior.equals("")) {
				String unidad_medida = limite_superior.substring(0, 1);
				if (unidad_medida.equals("2") || unidad_medida.equals("1")) {
					unidad_medida = "3";
				} else if (unidad_medida.equals("3")) {
					unidad_medida = "2";
				} else if (unidad_medida.equals("4")) {
					unidad_medida = "1";
				}
				String edad = Utilidades.getEdad2(fecha_nac, unidad_medida);
				if (!edad.equals("")) {
					if (Integer.parseInt(edad) > Integer
							.parseInt(limite_superior.substring(1))) {
						mensaje += tipo + " " + codigo + " "
								+ " no corresponde con la edad del paciente ("
								+ edad + ").\n";
						success = false;
					}
				}
			}
			// Validamos si el sexo corresponde al dx //
			if (!sexo.equals("") && !sexo.equals("A")) {
				if (tipo.equalsIgnoreCase("diagnostico")) {
					if (sexo.equalsIgnoreCase("H")) {
						sexo = "M";
					} else {
						sexo = "F";
					}
				}
				if (!sexo.equals(sexo_pct)) {
					mensaje += tipo + " " + codigo + " "
							+ " no corresponde con el sexo del paciente ("
							+ sexo_pct + ").";
					success = false;
				}
			}

		}
		// log.info("mensaje validar limite sexo ===> " + mensaje);
		resultado.put("result", mensaje);
		resultado.put("msg", mensaje);
		resultado.put("success", success);

		return resultado;
	}

	public static void ordenarProcedimientosQuirurgicos(
			List<Datos_procedimiento> lista_pro, Manuales_tarifarios manual,
			ServiceLocatorWeb serviceLocator) throws Exception {
		Maestro_manual maestro_manual = null;
		Datos_procedimiento tmp;
		int i, j;
		int N = lista_pro.size();
		for (i = 1; i < N; i++) {
			for (j = N - 1; j >= 1; j--) {
				if (maestro_manual == null) {
					maestro_manual = new Maestro_manual();
					maestro_manual.setId_manual(manual.getId_maestro_manual());
					maestro_manual = serviceLocator.getServicio(
							Maestro_manualService.class).consultar(
							maestro_manual);
				}

				Datos_procedimiento aux1 = (Datos_procedimiento) lista_pro
						.get(j - 1);
				Datos_procedimiento aux2 = (Datos_procedimiento) lista_pro
						.get(j);
				String dato1 = (String) aux1.getGrupo();
				String dato2 = (String) aux2.getGrupo();
				if (maestro_manual.getTipo_manual().equals(
						IConstantes.TIPO_MANUAL_SOAT)) {
					if (dato1.compareTo(dato2) < 0) {
						tmp = lista_pro.get(j);
						lista_pro.set(j, lista_pro.get(j - 1));
						lista_pro.set(j - 1, tmp);
					}
				} else if (maestro_manual.getTipo_manual().equals(
						IConstantes.TIPO_MANUAL_ISS01)
						|| maestro_manual.getTipo_manual().equals(
								IConstantes.TIPO_MANUAL_ISS04)) {
					Map<String, Object> pro1 = getProcedimiento(manual,
							Long.parseLong(aux1.getCodigo_procedimiento()),
							serviceLocator);
					Map<String, Object> pro2 = getProcedimiento(manual,
							Long.parseLong(aux2.getCodigo_procedimiento()),
							serviceLocator);

					if (pro1 != null && pro2 != null) {
						Double uvr1 = new Double(pro1.get("grupo_uvr")
								.toString());
						Double uvr2 = new Double(pro2.get("grupo_uvr")
								.toString());
						if (uvr1.compareTo(uvr2) < 0) {
							tmp = lista_pro.get(j);
							lista_pro.set(j, lista_pro.get(j - 1));
							lista_pro.set(j - 1, tmp);
						}
					}

				}
			}
		}
	}

	public static void listarAdministradoras(Listbox listbox, boolean select,
			ZKWindow zkWindow) {
		listbox.getChildren().clear();
		Listitem listitem = new Listitem();
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", zkWindow.getEmpresa().getCodigo_empresa());
		map.put("codigo_sucursal", zkWindow.getSucursal().getCodigo_sucursal());
		List<Administradora> list = zkWindow.getServiceLocator()
				.getAdministradoraService().listar(map);
		for (Administradora administradora : list) {
			listitem = new Listitem();
			listitem.setValue(administradora.getCodigo());
			listitem.setLabel(administradora.getNombre());
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarContabiliazaciones(Listbox listbox,
			boolean select, ZKWindow zkWindow) {
		listbox.getChildren().clear();
		Listitem listitem = new Listitem();
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", zkWindow.getEmpresa().getCodigo_empresa());
		List<Contabilizacion> list = zkWindow.getServiceLocator()
				.getContabilizacionService().listar(map);
		for (Contabilizacion contabilizacion : list) {
			listitem = new Listitem();
			listitem.setValue(contabilizacion.getCodigo());
			listitem.setLabel(contabilizacion.getNombre());
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarTipoProcedimiento(Listbox listbox, boolean select,
			ZKWindow zkWindow) {
		listbox.getChildren().clear();
		Listitem listitem = new Listitem();
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", zkWindow.getEmpresa().getCodigo_empresa());
		List<Tipo_procedimiento> list = zkWindow.getServiceLocator()
				.getTipo_procedimientoService().listar(map);
		for (Tipo_procedimiento tipo_procedimiento : list) {
			listitem = new Listitem();
			listitem.setValue(tipo_procedimiento.getCodigo());
			listitem.setLabel(tipo_procedimiento.getNombre());
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static boolean seleccionarListitem(Listbox listbox, String valor) {
		boolean selected = false;
		if (valor != null) {
			for (int i = 0; i < listbox.getItemCount(); i++) {
				Listitem listitem = listbox.getItemAtIndex(i);
				if (valor.equals(listitem.getValue().toString())) {
					listitem.setSelected(true);
					selected = true;
					break;
				}
			}
		}
		return selected;
	}

	public static boolean seleccionarRadio(Radiogroup radiogroup, String valor) {
		boolean selected = false;
		for (int i = 0; i < radiogroup.getItemCount(); i++) {
			Radio radio = radiogroup.getItemAtIndex(i);
			if (valor != null && valor.equals(radio.getValue().toString())) {
				radio.setChecked(true);
				selected = true;
				break;
			}
		}
		return selected;
	}

	public static void listarProgramasPYP(Listbox listboxPrograma,
			boolean select, ZKWindow zkWindow) {

		listboxPrograma.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listboxPrograma.appendChild(listitem);
		}

		Map<String, Object> parameters = new HashMap();
		parameters.put("codigo_empresa", zkWindow.getEmpresa()
				.getCodigo_empresa());
		parameters.put("codigo_sucursal", zkWindow.getSucursal()
				.getCodigo_sucursal());

		List<Pyp> lista_datos = zkWindow.getServiceLocator()
				.getServicio(GeneralExtraService.class)
				.listar(Pyp.class, parameters);
		for (Pyp pyp : lista_datos) {
			listitem = new Listitem();
			listitem.setValue(pyp.getCodigo());
			listitem.setLabel(pyp.getNombre());
			listboxPrograma.appendChild(listitem);
		}
		if (listboxPrograma.getItemCount() > 0) {
			listboxPrograma.setSelectedIndex(0);
		}
	}

	// Metodo para saber el codigo del grupo quirurgico que va a consultar//
	public static String getTipo_grupo_cirugia(String tipo, String manual) {
		String tipo_grupo = "";

		if (tipo.equals("1")) {
			if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
				tipo_grupo = "GR01";
			} else {
				tipo_grupo = "39756";
			}
		} else if (tipo.equals("2")) {
			if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
				tipo_grupo = "GR02";
			} else {
				tipo_grupo = "38564";
			}
		} else if (tipo.equals("3")) {
			if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
				tipo_grupo = "GR03";
			} else {
				tipo_grupo = "39456";
			}
		} else if (tipo.equals("4")) {
			if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
				tipo_grupo = "GR04";
			} else {
				tipo_grupo = "SALA";
			}
		} else if (tipo.equals("5")) {
			if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
				tipo_grupo = "GR05";
			} else {
				tipo_grupo = "MATERIALES";
			}
		} else if (tipo.equals("6")) {
			if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
				tipo_grupo = "GR06";
			}
		}

		return tipo_grupo;
	}

	// Metodo para saber el codigo de la sala para cobrar//
	public static String getCodigo_sala(String tipo_sala, double valor_uvr)
			throws Exception {
		String codigo_sala = "";

		if (tipo_sala.equals("01")) {
			if (valor_uvr <= 20) {
				codigo_sala = "S23101";
			} else if (valor_uvr >= 21 && valor_uvr <= 30) {
				codigo_sala = "S23102";
			} else if (valor_uvr >= 31 && valor_uvr <= 40) {
				codigo_sala = "S23201";
			} else if (valor_uvr >= 41 && valor_uvr <= 50) {
				codigo_sala = "S23202";
			} else if (valor_uvr >= 51 && valor_uvr <= 60) {
				codigo_sala = "S23203";
			} else if (valor_uvr >= 61 && valor_uvr <= 70) {
				codigo_sala = "S23204";
			} else if (valor_uvr >= 71 && valor_uvr <= 80) {
				codigo_sala = "S23205";
			} else if (valor_uvr >= 81 && valor_uvr <= 90) {
				codigo_sala = "S23301";
			} else if (valor_uvr >= 91 && valor_uvr <= 100) {
				codigo_sala = "S23302";
			} else if (valor_uvr >= 101 && valor_uvr <= 110) {
				codigo_sala = "S23303";
			} else if (valor_uvr >= 111 && valor_uvr <= 130) {
				codigo_sala = "S23304";
			} else if (valor_uvr >= 131 && valor_uvr <= 150) {
				codigo_sala = "S23305";
			} else if (valor_uvr >= 151 && valor_uvr <= 170) {
				codigo_sala = "S23306";
			} else if (valor_uvr >= 171 && valor_uvr <= 200) {
				codigo_sala = "S23307";
			} else if (valor_uvr >= 201 && valor_uvr <= 230) {
				codigo_sala = "S23308";
			} else if (valor_uvr >= 231 && valor_uvr <= 260) {
				codigo_sala = "S23309";
			} else if (valor_uvr >= 261 && valor_uvr <= 290) {
				codigo_sala = "S23310";
			} else if (valor_uvr >= 291 && valor_uvr <= 320) {
				codigo_sala = "S23311";
			} else if (valor_uvr >= 321 && valor_uvr <= 350) {
				codigo_sala = "S23312";
			} else if (valor_uvr >= 351 && valor_uvr <= 380) {
				codigo_sala = "S23313";
			} else if (valor_uvr >= 381 && valor_uvr <= 410) {
				codigo_sala = "S23314";
			} else if (valor_uvr >= 411 && valor_uvr <= 450) {
				codigo_sala = "S23315";
			}
		} else {
			if (valor_uvr <= 20) {
				codigo_sala = "S22201";
			} else if (valor_uvr >= 21 && valor_uvr <= 30) {
				codigo_sala = "S22202";
			} else if (valor_uvr >= 31 && valor_uvr <= 40) {
				codigo_sala = "S22203";
			} else if (valor_uvr >= 41 && valor_uvr <= 50) {
				codigo_sala = "S22204";
			} else if (valor_uvr >= 51 && valor_uvr <= 60) {
				codigo_sala = "S22205";
			} else if (valor_uvr >= 61 && valor_uvr <= 70) {
				codigo_sala = "S22206";
			} else if (valor_uvr >= 71 && valor_uvr <= 80) {
				codigo_sala = "S22207";
			} else if (valor_uvr >= 81 && valor_uvr <= 90) {
				codigo_sala = "S22208";
			} else if (valor_uvr >= 91 && valor_uvr <= 100) {
				codigo_sala = "S22209";
			} else if (valor_uvr >= 101 && valor_uvr <= 110) {
				codigo_sala = "S22210";
			} else if (valor_uvr >= 111 && valor_uvr <= 130) {
				codigo_sala = "S22211";
			} else if (valor_uvr >= 131 && valor_uvr <= 150) {
				codigo_sala = "S22212";
			} else if (valor_uvr >= 151 && valor_uvr <= 170) {
				codigo_sala = "S22213";
			} else if (valor_uvr >= 171 && valor_uvr <= 200) {
				codigo_sala = "S22214";
			} else {
				throw new Exception(
						"Procedimientos Iss con uvr mayor a 200 no se pueden cobrar");
			}
		}

		return codigo_sala;
	}

	// Metodo para saber el codigo del material para cobrar//
	public static String getCodigo_material(double valor_uvr) throws Exception {
		String codigo = "";

		if (valor_uvr <= 20) {
			codigo = "S55101";
		} else if (valor_uvr >= 21 && valor_uvr <= 30) {
			codigo = "S55102";
		} else if (valor_uvr >= 31 && valor_uvr <= 40) {
			codigo = "S55103";
		} else if (valor_uvr >= 41 && valor_uvr <= 50) {
			codigo = "S55104";
		} else if (valor_uvr >= 51 && valor_uvr <= 60) {
			codigo = "S55105";
		} else if (valor_uvr >= 61 && valor_uvr <= 70) {
			codigo = "S55106";
		} else if (valor_uvr >= 71 && valor_uvr <= 80) {
			codigo = "S55107";
		} else if (valor_uvr >= 81 && valor_uvr <= 90) {
			codigo = "S55108";
		} else if (valor_uvr >= 91 && valor_uvr <= 100) {
			codigo = "S55109";
		} else if (valor_uvr >= 101 && valor_uvr <= 110) {
			codigo = "S55110";
		} else if (valor_uvr >= 111 && valor_uvr <= 130) {
			codigo = "S55111";
		} else if (valor_uvr >= 131 && valor_uvr <= 150) {
			codigo = "S55112";
		} else if (valor_uvr >= 151 && valor_uvr <= 170) {
			codigo = "S55113";
		}

		return codigo;
	}

	// Metodo para traer nombre de los elementos //
	public static String getNombreElemento(String codigo, String tipo,
			ZKWindow zkWindow) {
		return getNombreElemento(codigo, tipo, zkWindow.getServiceLocator());
	}

	// Metodo para traer nombre de los elementos //
	public static String getNombreElemento(String codigo, String tipo,
			ServiceLocatorWeb servicelocator) {
		String nombre_elemento = "";

		Elemento elemento = new Elemento();
		elemento.setCodigo(codigo);
		elemento.setTipo(tipo);
		elemento = servicelocator.getElementoService().consultar(elemento);
		nombre_elemento = (elemento != null ? elemento.getDescripcion() : "");
		return nombre_elemento;
	}

	// Metodo para traer nombre de los elementos //
	public static String getNombreElemento(String codigo, String tipo,
			ElementoService elementoService) {
		String nombre_elemento = "";

		Elemento elemento = new Elemento();
		elemento.setCodigo(codigo);
		elemento.setTipo(tipo);
		elemento = elementoService.consultar(elemento);
		nombre_elemento = (elemento != null ? elemento.getDescripcion() : "");
		return nombre_elemento;
	}

	// Metodo para traer nombre de los elementos //
	public static String getNombreMunicipio(String codigo_dpto,
			String codigo_municipio, ZKWindow zkWindow) {
		String nombre_municipio = "";

		Municipios municipios = new Municipios();
		municipios.setCoddep(codigo_dpto);
		municipios.setCodigo(codigo_municipio);
		municipios = zkWindow.getServiceLocator().getMunicipiosService()
				.consultar(municipios);

		nombre_municipio = (municipios != null ? municipios.getNombre() : "");
		return nombre_municipio;
	}

	// Metodo para traer nombre de los elementos //
	public static String getNombreMunicipio(String codigo_dpto,
			String codigo_municipio, MunicipiosService municipiosService) {
		String nombre_municipio = "";

		Municipios municipios = new Municipios();
		municipios.setCoddep(codigo_dpto);
		municipios.setCodigo(codigo_municipio);
		municipios = municipiosService.consultar(municipios);

		nombre_municipio = (municipios != null ? municipios.getNombre() : "");
		return nombre_municipio;
	}

	// Metodo para listar las admisiones
	public static List<Admision> listarAdmisiones(String nro_documento,
			String nro_ingreso, boolean sw, ZKWindow zkWindow) {
		Map<String, Object> paramAdmision = new HashMap<String, Object>();
		paramAdmision.put("codigo_empresa", zkWindow.getEmpresa()
				.getCodigo_empresa());
		paramAdmision.put("codigo_sucursal", zkWindow.getSucursal()
				.getCodigo_sucursal());
		paramAdmision.put("nro_identificacion", nro_documento);
		paramAdmision.put("order", "fecha_ingreso desc");
		if (sw) {
			paramAdmision.put("nro_ingreso", nro_ingreso);
		} else {
			paramAdmision.put("estado", "1");
		}

		List<Admision> listaAdmisiones = zkWindow.getServiceLocator()
				.getServicio(AdmisionService.class).listarTabla(paramAdmision);

		return listaAdmisiones;
	}

	// Metodo para obtener los datos del grupo quirurgico esto para cuando se
	// llame del servicio //
	public static Map getNomGrupoCirugia(String manual, String grupo,
			String tipo_grupo_soat, String tipo_grupo_iss,
			Map<String, Object> servicios, Map param) {

		Grupos_quirurgicosService grupos_quirurgicosService = (Grupos_quirurgicosService) servicios
				.get("grupos_quirurgicosService");

		Grupos_iss01Service grupos_iss01Service = (Grupos_iss01Service) servicios
				.get("grupos_iss01Service");

		Map<String, Object> mapBean = new HashMap<String, Object>();
		String nom_pcd = "";
		if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
			Grupos_quirurgicos grupos = new Grupos_quirurgicos();
			grupos.setCodigo_grupo(grupo);
			grupos.setTipo_grupo(tipo_grupo_soat);
			grupos = grupos_quirurgicosService.consultar(grupos);
			nom_pcd = (grupos != null ? grupos.getCodigo_pcd() + "-"
					+ grupos.getNombre_pcd() : "");
		} else if (manual.equals(IConstantes.TIPO_MANUAL_ISS01)
				|| manual.equals(IConstantes.TIPO_MANUAL_ISS04)) {
			Grupos_iss01 grupos = new Grupos_iss01();
			grupos.setCodigo(tipo_grupo_iss);
			grupos = grupos_iss01Service.consultar(grupos);
			nom_pcd = (grupos != null ? grupos.getCodigo() + "-"
					+ grupos.getDescripcion() : "");
		}

		mapBean.put("nro_factura", (String) param.get("nro_factura"));
		mapBean.put("nro_identificacion",
				(String) param.get("nro_identificacion"));
		mapBean.put("nombre1", (String) param.get("nombre1"));
		mapBean.put("nombre2", (String) param.get("nombre2"));
		mapBean.put("apellido1", (String) param.get("apellido1"));
		mapBean.put("apellido2", (String) param.get("apellido2"));
		mapBean.put("codigo_procedimiento", "");
		mapBean.put("procedimiento", nom_pcd);
		mapBean.put("unidades", new Integer(0));
		mapBean.put("valor_neto", new BigDecimal(0));

		return mapBean;

	}

	// Metodo para obtener los datos del grupo quirurgico esto para cuando se
	// llame de la vista //
	public static Map getNomGrupoCirugia(String manual, String grupo,
			String tipo_grupo_soat, String tipo_grupo_iss,
			ServiceLocatorWeb serviceLocator, Map param) {

		Map<String, Object> servicios = new HashMap<String, Object>();
		servicios.put("grupos_quirurgicosService",
				serviceLocator.getGrupos_quirurgicosService());
		servicios.put("grupos_iss01Service",
				serviceLocator.getGrupos_iss01Service());

		return getNomGrupoCirugia(manual, grupo, tipo_grupo_soat,
				tipo_grupo_iss, servicios, param);

	}

	// metodo para listarIngresos
	public static void listarIngresos(Listbox listbox,
			List<Admision> listaAdmisiones, boolean select, ZKWindow zkWindow) {
		listbox.getItems().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("-- --");
			listbox.appendChild(listitem);
		}

		for (Admision a : listaAdmisiones) {
			Administradora admin = new Administradora();
			admin.setCodigo_empresa(a.getCodigo_empresa());
			admin.setCodigo_sucursal(a.getCodigo_sucursal());
			admin.setCodigo(a.getCodigo_administradora());
			admin = zkWindow.getServiceLocator().getAdministradoraService()
					.consultar(admin);

			listitem = new Listitem();
			listitem.setValue(a);
			listitem.setLabel(a.getNro_ingreso() + " -- "
					+ (admin != null ? admin.getNombre() : ""));
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static String getEstado_admision(String nro_ingreso,
			String nro_documento, ZKWindow zkWindow) throws Exception {
		String estado = "1";

		Admision admision = new Admision();
		admision.setCodigo_empresa(zkWindow.getEmpresa().getCodigo_empresa());
		admision.setCodigo_sucursal(zkWindow.getSucursal().getCodigo_sucursal());
		admision.setNro_ingreso(nro_ingreso);
		admision.setNro_identificacion(nro_documento);
		admision = zkWindow.getServiceLocator()
				.getServicio(AdmisionService.class).consultar(admision);
		estado = (admision != null ? admision.getEstado() : "1");

		return estado;
	}

	public static void listarCentros_costo(Listbox listbox, boolean select,
			ZKWindow zkWindow) {
		listbox.getItems().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", zkWindow.getEmpresa()
				.getCodigo_empresa());
		parametros.put("codigo_sucursal", zkWindow.getSucursal()
				.getCodigo_sucursal());

		List<Centro_costo> listaCentros = zkWindow.getServiceLocator()
				.getCentro_costoService().listar(parametros);

		for (Centro_costo centro_costo : listaCentros) {
			listitem = new Listitem();
			listitem.setValue(centro_costo.getCodigo());
			listitem.setLabel(centro_costo.getNombre());
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}

	}

	public static void listarBodegasPorArticulo(String codigo_articulo,
			Listbox listbox, ZKWindow zkWindow) {
		listbox.getItems().clear();

		Bodega bodega = new Bodega();
		bodega.setCodigo_empresa(zkWindow.getEmpresa().getCodigo_empresa());
		bodega.setCodigo_sucursal(zkWindow.getEmpresa().getCodigo_empresa());
		bodega.setCodigo("01");
		bodega = zkWindow.getServiceLocator().getBodegaService()
				.consultar(bodega);

		Listitem listitem = new Listitem();
		listitem.setValue("01");
		listitem.setLabel(bodega != null ? bodega.getNombre() : "BODEGA 01");
		listbox.appendChild(listitem);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", zkWindow.getEmpresa()
				.getCodigo_empresa());
		parametros.put("codigo_sucursal", zkWindow.getSucursal()
				.getCodigo_sucursal());
		parametros.put("codigo_articulo", codigo_articulo);

		List<Bodega_articulo> lista_bodegas = zkWindow.getServiceLocator()
				.getBodega_articuloService().listar(parametros);

		for (Bodega_articulo bodega_articulo : lista_bodegas) {
			if (!bodega_articulo.getCodigo_bodega().equals("01")) {
				bodega = new Bodega();
				bodega.setCodigo_empresa(bodega_articulo.getCodigo_empresa());
				bodega.setCodigo_sucursal(bodega_articulo.getCodigo_sucursal());
				bodega.setCodigo(bodega_articulo.getCodigo_bodega());
				bodega = zkWindow.getServiceLocator().getBodegaService()
						.consultar(bodega);

				listitem = new Listitem();
				listitem.setValue(bodega_articulo.getCodigo_bodega());
				listitem.setLabel(bodega != null ? bodega.getNombre()
						: "bodega " + bodega_articulo.getCodigo_bodega());
				listbox.appendChild(listitem);
			}

		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}

	}

	public static void listarcontratosPorAdministradora(Listbox listbox,
			boolean select, String codigo_administradora,
			WindowStandar zkWindow, ContratosService contratosService) {
		listbox.getItems().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", zkWindow.getEmpresa()
				.getCodigo_empresa());
		parametros.put("codigo_sucursal", zkWindow.getSucursal()
				.getCodigo_sucursal());
		parametros.put("codigo_administradora", codigo_administradora);

		List<Contratos> lista_contratos = contratosService.listar(parametros);

		for (Contratos contratos : lista_contratos) {
			String nro_contrato = (contratos.getNro_contrato() != null && !contratos
					.getNro_contrato().trim().isEmpty()) ? contratos
					.getNro_contrato() : contratos.getId_plan();

			listitem = new Listitem();
			listitem.setValue(contratos);
			listitem.setLabel(nro_contrato + " " + contratos.getNombre());
			listbox.appendChild(listitem);
		}

		if (lista_contratos.isEmpty()) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}

	}

	public static String getZeroFill(String valor, int zeroFill) {
		String st_zeroFill = "000000000000000000000000000000";
		return st_zeroFill.substring(0, zeroFill - valor.length()) + valor;
	}

	public static String formatDecimal(double valor, String format) {
		Locale.setDefault(Locale.ENGLISH);
		return new DecimalFormat(format).format(valor);
	}

	public static String formato_fecha(Administradora administradora)
			throws Exception {
		String formato_fecha = "dd/MM/yyyy";
		formato_fecha = (administradora != null ? administradora
				.getFormato_fecha_rips() : "dd/MM/yyyy");

		return formato_fecha;
	}

	public static String formatearNit(String nit) throws Exception {
		// String nit_formateado = "";
		StringTokenizer st = new StringTokenizer(nit, "-");
		if (st.hasMoreTokens()) {
			return st.nextToken();
		} else {
			return nit;
		}
	}

	public static String formatDate(java.util.Date date, String format) {
		if (format == null) {
			format = "dd/MM/yyyy";
		}
		return new java.text.SimpleDateFormat(format).format(date);
	}

	public static String formatDate(java.sql.Date date, String format) {
		return formatDate((java.util.Date) date, format);
	}

	public static String formatDate(java.sql.Timestamp date, String format) {
		return formatDate((java.util.Date) date, format);
	}

	public static String formatText(String text) {
		String format = "";
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch != '.' && ch != ',' && ch != '/' && ch != '|' && ch != '-'
					&& ch != '_' && ch != '\\') {
				format += ch + "";
			}
		}
		return format.trim();
	}

	public static String getEdad2(String fecha, String unidad_medidad) {
		String edad = "";

		Date fecha_actual = new Date();
		Date fecha_nacimiento = new Date();

		int dia = 0, mes = 0, anio = 0;

		StringTokenizer st = new StringTokenizer(fecha, "/");
		int cont = 0;
		while (st.hasMoreTokens()) {
			String tokens = st.nextToken();
			if (cont == 0) {
				dia = Integer.parseInt(tokens, 10);
			} else if (cont == 1) {
				mes = Integer.parseInt(tokens, 10);
			} else if (cont == 2) {
				anio = Integer.parseInt(tokens, 10);
			}
			cont++;
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, anio);
		calendar.set(Calendar.MONTH, (mes - 1));
		calendar.set(Calendar.DAY_OF_MONTH, dia);
		fecha_nacimiento.setTime(calendar.getTimeInMillis());

		double mSecDiff = Math.abs(fecha_actual.getTime()
				- fecha_nacimiento.getTime()) / 1000;
		double AgeDays = mSecDiff / 86400;
		double AgeMonth = AgeDays / 30.4375;
		double AgeYears = AgeDays / 365.24;
		AgeYears = Math.floor(AgeYears);

		AgeDays = Math.round(AgeDays * 10) / 10;
		AgeMonth = Math.round(AgeMonth * 10) / 10;

		if (unidad_medidad.equals("1")) {
			edad = (int) AgeYears + "";
		} else if (unidad_medidad.equals("2")) {
			edad = (int) AgeMonth + "";
		} else {
			edad = (int) AgeDays + "";
		}

		return edad;
	}

	public static int getAnios(Date fecha_nacimiento) {
		double mSecDiff = Math.abs(Calendar.getInstance().getTimeInMillis()
				- fecha_nacimiento.getTime()) / 1000;
		double AgeDays = mSecDiff / 86400;
		double AgeYears = AgeDays / 365.24;
		AgeYears = Math.floor(AgeYears);
		return (int) AgeYears;
	}

	public static String getEdad2(String fecha, String unidad_medidad,
			Date fecha_atencion) {
		String edad = "";

		Date fecha_actual = fecha_atencion;
		Date fecha_nacimiento = new Date();

		int dia = 0, mes = 0, anio = 0;

		StringTokenizer st = new StringTokenizer(fecha, "/");
		int cont = 0;
		while (st.hasMoreTokens()) {
			String tokens = st.nextToken();
			if (cont == 0) {
				dia = Integer.parseInt(tokens, 10);
			} else if (cont == 1) {
				mes = Integer.parseInt(tokens, 10);
			} else if (cont == 2) {
				anio = Integer.parseInt(tokens, 10);
			}
			cont++;
		}
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, anio);
		calendar.set(Calendar.MONTH, (mes - 1));
		calendar.set(Calendar.DAY_OF_MONTH, dia);
		fecha_nacimiento.setTime(calendar.getTimeInMillis());

		double mSecDiff = Math.abs(fecha_actual.getTime()
				- fecha_nacimiento.getTime()) / 1000;
		double AgeDays = mSecDiff / 86400;
		double AgeMonth = AgeDays / 30.4375;
		double AgeYears = AgeDays / 365.24;
		AgeYears = Math.floor(AgeYears);

		AgeDays = Math.round(AgeDays * 10) / 10;
		AgeMonth = Math.round(AgeMonth * 10) / 10;

		if (unidad_medidad.equals("1")) {
			edad = (int) AgeYears + "";
		} else if (unidad_medidad.equals("2")) {
			edad = (int) AgeMonth + "";
		} else {
			edad = (int) AgeDays + "";
		}

		return edad;
	}

	public static void listarAnios(Listbox listbox, boolean select, int size,
			boolean selectPeriodo, Integer anio_periodo) {
		listbox.getChildren().clear();
		Listitem listitem = new Listitem();
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		for (String year : Res.getAnnos(size)) {
			listitem = new Listitem();
			listitem.setValue(year);
			listitem.setLabel(year);
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
		if (selectPeriodo) {
			for (int i = 0; i < listbox.getItemCount(); i++) {
				listitem = listbox.getItemAtIndex(i);
				if (listitem.getValue().toString().equals("" + anio_periodo)) {
					listitem.setSelected(true);
					break;
				}
			}
		}
	}

	// Estos metodos son para el reporte de factura clinica //
	public static void dividirListas(List<Map> datos, List lista_med,
			List lista_otros) throws Exception {
		for (int i = 0; i < datos.size(); i++) {
			Map map = (Map) datos.get(i);
			String tipo_servicio = (String) map.get("tipo_servicio");
			if (tipo_servicio.equals("MEDICAMENTO")
					|| tipo_servicio.equals("MATERIALES/INSUMOS")) {
				lista_med.add(map);
			} else {
				lista_otros.add(map);
			}
		}
		datos.clear();
	}

	public static void unirListas(List<Map> datos, List lista_med,
			List lista_otros) throws Exception {
		for (int i = 0; i < lista_med.size(); i++) {
			Map map = (Map) lista_med.get(i);
			datos.add(map);
		}
		for (int i = 0; i < lista_otros.size(); i++) {
			Map map = (Map) lista_otros.get(i);
			datos.add(map);
		}

		lista_med.clear();
		lista_otros.clear();
	}

	// Aqui agrupamos la lista de los servicios con el mismo codigo o nombre
	// cuando sea medicamento/materiales//
	public static void agrupar(List<Map> datos) throws Exception {
		Map<String, Object> general = new HashMap<String, Object>();
		for (int i = 0; i < datos.size(); i++) {
			Map map = (Map) datos.get(i);
			String tipo_servicio = (String) map.get("tipo_servicio");
			String codigo_articulo = "";
			String detalle = "";
			detalle = ((String) map.get("detalle")).trim();
			if (tipo_servicio.equals("MEDICAMENTO")
					|| tipo_servicio.equals("MATERIALES/INSUMOS")) {
				tipo_servicio = "MEDICAMENTO";
				codigo_articulo = "";
				detalle = (detalle.length() <= 30 ? Utilidades
						.formatText(detalle) : Utilidades.formatText(detalle
						.substring(0, 30)));
			} else if (tipo_servicio.equals("PROCEDIMIENTO MULT")) {
				codigo_articulo = (String) map.get("consecutivo") + "_";
			} else {
				codigo_articulo = (String) map.get("codigo_articulo") + "_";
			}
			int cantidad = ((Integer) map.get("cantidad")).intValue();
			double total = ((java.math.BigDecimal) map.get("total"))
					.doubleValue();

			if (general.get(codigo_articulo + tipo_servicio + "_" + detalle) == null) {
				general.put(codigo_articulo + tipo_servicio + "_" + detalle,
						map);
			} else {
				Map aux = (Map) general.get(codigo_articulo + tipo_servicio
						+ "_" + detalle);
				int can_aux = ((Integer) aux.get("cantidad")).intValue();
				int vu_aux = (int) ((java.math.BigDecimal) aux
						.get("valor_unitario")).doubleValue();
				int total_aux = (int) ((java.math.BigDecimal) aux.get("total"))
						.doubleValue();

				can_aux += cantidad;
				total_aux += total;
				if (can_aux > 0) {
					vu_aux = (total_aux / can_aux);
					total_aux = (vu_aux * can_aux);
				}

				aux.put("cantidad", new Integer(can_aux));
				aux.put("valor_unitario", new java.math.BigDecimal(vu_aux));
				aux.put("total", new java.math.BigDecimal(total_aux));
				general.put(codigo_articulo + tipo_servicio + "_" + detalle,
						aux);
			}
		}
		datos.clear();
		Iterator it = general.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			Map map = (Map) e.getValue();
			datos.add(map);
		}
	}

	// Esta lista ordena los datos de la factura que sean procedimientos //
	public static void ordenarPcd(List<Map> datos, final String codigo_empresa,
			List lista_serv, ZKWindow zkWindow) {
		final ServiceLocatorWeb serviceLocator = zkWindow.getServiceLocator();
		Collections.sort(lista_serv, new Comparator() {

			public int compare(Object o1, Object o2) {
				Map aux1 = (Map) o1;
				Map aux2 = (Map) o2;

				String tipo1 = (String) aux1.get("tipo_procedimiento");
				String tipo2 = (String) aux2.get("tipo_procedimiento");
				String codigo1 = (String) aux1.get("codigo_articulo");
				String codigo2 = (String) aux2.get("codigo_articulo");
				String factura_concepto1 = (String) aux1
						.get("factura_concepto");
				String factura_concepto2 = (String) aux2
						.get("factura_concepto");
				String total1 = ((java.math.BigDecimal) aux1.get("total"))
						.toString();
				String total2 = ((java.math.BigDecimal) aux2.get("total"))
						.toString();
				Tipo_procedimiento tipo_procedimiento1 = new Tipo_procedimiento();
				tipo_procedimiento1.setCodigo_empresa(codigo_empresa);
				tipo_procedimiento1.setCodigo(tipo1);
				Tipo_procedimiento tipo_procedimiento2 = new Tipo_procedimiento();
				tipo_procedimiento2.setCodigo_empresa(codigo_empresa);
				tipo_procedimiento2.setCodigo(tipo2);
				Timestamp fecha1 = null;
				Timestamp fecha2 = null;
				try {

					tipo_procedimiento1 = serviceLocator
							.getTipo_procedimientoService().consultar(
									tipo_procedimiento1);
					tipo_procedimiento2 = serviceLocator
							.getTipo_procedimientoService().consultar(
									tipo_procedimiento2);
					fecha1 = (tipo_procedimiento1 != null ? tipo_procedimiento1
							.getUltimo_update() : new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					fecha2 = (tipo_procedimiento2 != null ? tipo_procedimiento2
							.getUltimo_update() : new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					/*
					 * System.out.println("tipo1: "+tipo_procedimiento1);
					 * System.out.println("tipo2: "+tipo_procedimiento2+"\n");
					 */

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (fecha1.compareTo(fecha2) == 0) {
					if (factura_concepto1 != null && factura_concepto2 != null) {
						if (factura_concepto1
								.compareToIgnoreCase(factura_concepto2) == 0) {
							if (codigo1.compareToIgnoreCase(codigo2) == 0) {
								return total1.compareToIgnoreCase(total2);
							} else {
								return codigo1.compareToIgnoreCase(codigo2);
							}
						} else {
							return factura_concepto1
									.compareToIgnoreCase(factura_concepto2);
						}
					} else {
						return 1;// cuando el rips no existe
					}
				} else {
					return fecha1.compareTo(fecha2);
				}
			}
		});
	}

	// Esta lista ordena los datos de la factura que sean procedimientos //
	public static void ordenarPcd(List<Map> datos, final String codigo_empresa,
			List lista_serv,
			final Tipo_procedimientoService tipo_procedimientoService) {

		Collections.sort(lista_serv, new Comparator() {

			public int compare(Object o1, Object o2) {
				Map aux1 = (Map) o1;
				Map aux2 = (Map) o2;

				String tipo1 = (String) aux1.get("tipo_procedimiento");
				String tipo2 = (String) aux2.get("tipo_procedimiento");
				String codigo1 = (String) aux1.get("codigo_articulo");
				String codigo2 = (String) aux2.get("codigo_articulo");
				String factura_concepto1 = (String) aux1
						.get("factura_concepto");
				String factura_concepto2 = (String) aux2
						.get("factura_concepto");
				String total1 = ((java.math.BigDecimal) aux1.get("total"))
						.toString();
				String total2 = ((java.math.BigDecimal) aux2.get("total"))
						.toString();
				Tipo_procedimiento tipo_procedimiento1 = new Tipo_procedimiento();
				tipo_procedimiento1.setCodigo_empresa(codigo_empresa);
				tipo_procedimiento1.setCodigo(tipo1);
				Tipo_procedimiento tipo_procedimiento2 = new Tipo_procedimiento();
				tipo_procedimiento2.setCodigo_empresa(codigo_empresa);
				tipo_procedimiento2.setCodigo(tipo2);
				Timestamp fecha1 = null;
				Timestamp fecha2 = null;
				try {

					tipo_procedimiento1 = tipo_procedimientoService
							.consultar(tipo_procedimiento1);
					tipo_procedimiento2 = tipo_procedimientoService
							.consultar(tipo_procedimiento2);
					fecha1 = (tipo_procedimiento1 != null ? tipo_procedimiento1
							.getUltimo_update() : new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					fecha2 = (tipo_procedimiento2 != null ? tipo_procedimiento2
							.getUltimo_update() : new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					/*
					 * System.out.println("tipo1: "+tipo_procedimiento1);
					 * System.out.println("tipo2: "+tipo_procedimiento2+"\n");
					 */

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (fecha1.compareTo(fecha2) == 0) {
					if (factura_concepto1 != null && factura_concepto2 != null) {
						if (factura_concepto1
								.compareToIgnoreCase(factura_concepto2) == 0) {
							if (codigo1.compareToIgnoreCase(codigo2) == 0) {
								return total1.compareToIgnoreCase(total2);
							} else {
								return codigo1.compareToIgnoreCase(codigo2);
							}
						} else {
							return factura_concepto1
									.compareToIgnoreCase(factura_concepto2);
						}
					} else {
						return 1;// cuando el rips no existe
					}
				} else {
					return fecha1.compareTo(fecha2);
				}
			}
		});
	}

	// Esta lista ordena los datos de la factura que sean
	// medicamentos/materiales //
	public static void ordenarMedicamento_materiales(List<Map> datos,
			final String codigo_empresa, List lista_serv, ZKWindow zkWindow) {
		final ServiceLocatorWeb serviceLocator = zkWindow.getServiceLocator();
		Collections.sort(lista_serv, new Comparator() {

			public int compare(Object o1, Object o2) {
				Map aux1 = (Map) o1;
				Map aux2 = (Map) o2;

				String tipo1 = (String) aux1.get("tipo_procedimiento");
				String tipo2 = (String) aux2.get("tipo_procedimiento");
				// String tipo_servicio1 = (String) aux1.get("tipo_servicio");
				// String tipo_servicio2 = (String) aux2.get("tipo_servicio");
				String detalle1 = (String) aux1.get("detalle");
				String detalle2 = (String) aux2.get("detalle");
				// String codigo1 = (String) aux1.get("codigo_articulo");
				// String codigo2 = (String) aux2.get("codigo_articulo");
				// String factura_concepto1 = (String)
				// aux1.get("factura_concepto");
				// String factura_concepto2 = (String)
				// aux2.get("factura_concepto");
				String total1 = ((java.math.BigDecimal) aux1.get("total"))
						.toString();
				String total2 = ((java.math.BigDecimal) aux2.get("total"))
						.toString();

				Tipo_procedimiento tipo_procedimiento1 = new Tipo_procedimiento();
				tipo_procedimiento1.setCodigo_empresa(codigo_empresa);
				tipo_procedimiento1.setCodigo(tipo1);
				Tipo_procedimiento tipo_procedimiento2 = new Tipo_procedimiento();
				tipo_procedimiento2.setCodigo_empresa(codigo_empresa);
				tipo_procedimiento2.setCodigo(tipo2);
				/*
				 * Timestamp fecha1 = null; Timestamp fecha2 = null;
				 */
				try {
					tipo_procedimiento1 = serviceLocator
							.getTipo_procedimientoService().consultar(
									tipo_procedimiento1);
					tipo_procedimiento2 = serviceLocator
							.getTipo_procedimientoService().consultar(
									tipo_procedimiento2);
					// fecha1 = (tipo_procedimiento1 != null ?
					// tipo_procedimiento1.getUltimo_update() : new
					// Timestamp(new GregorianCalendar().getTimeInMillis()));
					// fecha2 = (tipo_procedimiento2 != null ?
					// tipo_procedimiento2.getUltimo_update() : new
					// Timestamp(new GregorianCalendar().getTimeInMillis()));

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (detalle1.compareToIgnoreCase(detalle2) == 0) {
					return total1.compareToIgnoreCase(total2);
				} else {
					return detalle1.compareToIgnoreCase(detalle2);
				}
			}
		});
	}

	// Esta lista ordena los datos de la factura que sean
	// medicamentos/materiales //
	public static void ordenarMedicamento_materiales(List<Map> datos,
			final String codigo_empresa, List lista_serv,
			final Tipo_procedimientoService tipo_procedimientoService) {

		Collections.sort(lista_serv, new Comparator() {

			public int compare(Object o1, Object o2) {
				Map aux1 = (Map) o1;
				Map aux2 = (Map) o2;

				String tipo1 = (String) aux1.get("tipo_procedimiento");
				String tipo2 = (String) aux2.get("tipo_procedimiento");
				// String tipo_servicio1 = (String) aux1.get("tipo_servicio");
				// String tipo_servicio2 = (String) aux2.get("tipo_servicio");
				String detalle1 = (String) aux1.get("detalle");
				String detalle2 = (String) aux2.get("detalle");
				// String codigo1 = (String) aux1.get("codigo_articulo");
				// String codigo2 = (String) aux2.get("codigo_articulo");
				// String factura_concepto1 = (String)
				// aux1.get("factura_concepto");
				// String factura_concepto2 = (String)
				// aux2.get("factura_concepto");
				String total1 = ((java.math.BigDecimal) aux1.get("total"))
						.toString();
				String total2 = ((java.math.BigDecimal) aux2.get("total"))
						.toString();

				Tipo_procedimiento tipo_procedimiento1 = new Tipo_procedimiento();
				tipo_procedimiento1.setCodigo_empresa(codigo_empresa);
				tipo_procedimiento1.setCodigo(tipo1);
				Tipo_procedimiento tipo_procedimiento2 = new Tipo_procedimiento();
				tipo_procedimiento2.setCodigo_empresa(codigo_empresa);
				tipo_procedimiento2.setCodigo(tipo2);
				/*
				 * Timestamp fecha1 = null; Timestamp fecha2 = null;
				 */
				try {
					tipo_procedimiento1 = tipo_procedimientoService
							.consultar(tipo_procedimiento1);
					tipo_procedimiento2 = tipo_procedimientoService
							.consultar(tipo_procedimiento2);
					// fecha1 = (tipo_procedimiento1 != null ?
					// tipo_procedimiento1.getUltimo_update() : new
					// Timestamp(new GregorianCalendar().getTimeInMillis()));
					// fecha2 = (tipo_procedimiento2 != null ?
					// tipo_procedimiento2.getUltimo_update() : new
					// Timestamp(new GregorianCalendar().getTimeInMillis()));

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (detalle1.compareToIgnoreCase(detalle2) == 0) {
					return total1.compareToIgnoreCase(total2);
				} else {
					return detalle1.compareToIgnoreCase(detalle2);
				}
			}
		});
	}

	public static void listarNivelEmpresa(Listbox lbxNivel, boolean b) {
		if (b) {
			lbxNivel.appendChild(new Listitem("-seleccione-", ""));
		}
		for (int i = 0; i < 4; i++) {
			Listitem listitem = new Listitem();
			String nivel = (i + 1) + "";
			listitem.setValue("" + nivel);
			listitem.setLabel("Nivel " + nivel);
			lbxNivel.appendChild(listitem);
		}
	}

	public static void listarNivelEmpresa(Listbox lbxNivel, boolean b,
			int por_nivel) {
		if (b) {
			lbxNivel.appendChild(new Listitem("-seleccione-", ""));
		}
		for (int i = 0; i < 4 && i < por_nivel; i++) {
			Listitem listitem = new Listitem();
			String nivel = (i + 1) + "";
			listitem.setValue("" + nivel);
			listitem.setLabel("Nivel " + nivel);
			lbxNivel.appendChild(listitem);
		}
	}

	public static Listcell obtenerListcell(Object valor,
			Class<? extends XulElement> classs, boolean readonly,
			boolean inplace) {
		Listcell listcell = new Listcell();
		XulElement component = null;
		if (classs.getName().equals(Textbox.class.getName())) {
			component = new Textbox(valor != null ? valor.toString() : "");
		} else if (classs.getName().equals(Doublebox.class.getName())) {
			component = new Doublebox((Double) valor);
			((Doublebox) component).setFormat("#,##0.00");
		} else if (classs.getName().equals(Label.class.getName())) {
			component = new Label(valor.toString());
		} else if (classs.getName().equals(Intbox.class.getName())) {
			component = new Intbox(valor != null ? Integer.parseInt(valor
					.toString()) : 0);
		} else if (classs.getName().equals(Longbox.class.getName())) {
			component = new Longbox(valor != null ? Long.parseLong(valor
					.toString()) : 0L);
		} else if (classs.getName().equals(Datebox.class.getName())) {
			if (valor instanceof Date) {
				component = new Datebox((Date) valor);
				((Datebox) component).setFormat("yyyy-MM-dd");
			} else {
				component = new Datebox();
				((Datebox) component).setFormat("yyyy-MM-dd");
				((Datebox) component).setText((String) valor);
			}
			((Datebox) component).setButtonVisible(!readonly);
		}
		component.setWidth("98%");
		if (component instanceof InputElement) {
			((InputElement) component).setReadonly(readonly);
			((InputElement) component).setInplace(inplace);

		}
		listcell.appendChild(component);
		return listcell;
	}

	/**
	 * Este metodo me permite adicionar una celda con un componente de cargado
	 * automatico con respecto al campo del objeto.
	 *
	 * @author Luis Miguel
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 *
	 */
	public static Listcell obtenerListcellAutomatica(Object object,
			String campo, Class<? extends XulElement> classs, boolean readonly,
			boolean inplace, IOnEventoListCellAutomatica listCellAutomatica,
			String nivel) throws Exception {
		Listcell listcell = new Listcell();
		// Creamo instancia del componente
		XulElement component = classs.newInstance();

		if (component instanceof Listbox) {
			((Listbox) component).setMold("select");
		} else if (component instanceof BandBoxRowMacro) {
			((BandBoxRowMacro) component).setAttribute("object", object);
			((BandBoxRowMacro) component).setAttribute("campo", campo);
		}

		// agregamos al evento el componente
		if (listCellAutomatica != null) {
			listCellAutomatica.onCargarComponente(component, campo, nivel,
					object);
		}
		// alimentamos informacion de comportamiento a los
		// componentes
		component.setHflex("1");
		if (component instanceof InputElement) {
			((InputElement) component).setReadonly(readonly);
			((InputElement) component).setInplace(inplace);
		}
		// Registramos como carga automatica
		Res.cargarAutomatica(component, object, campo);
		// adicionamos a la celda
		listcell.appendChild(component);
		return listcell;
	}

	public static Cell obtenerCell(Object valor,
			Class<? extends XulElement> classs, boolean readonly,
			boolean inplace) {
		Cell celda = new Cell();
		XulElement component = null;
		if (classs.getName().equals(Textbox.class.getName())) {
			component = new Textbox(valor != null ? valor.toString() : "");
		} else if (classs.getName().equals(Doublebox.class.getName())) {
			component = new Doublebox((Double) valor);
			((Doublebox) component).setFormat("#,##0.00");
		} else if (classs.getName().equals(Label.class.getName())) {
			component = new Label(valor.toString());
		} else if (classs.getName().equals(Intbox.class.getName())) {
			component = new Intbox(valor != null ? Integer.parseInt(valor
					.toString()) : 0);
		} else if (classs.getName().equals(Datebox.class.getName())) {
			if (valor instanceof Date) {
				component = new Datebox((Date) valor);
				((Datebox) component).setFormat("yyyy-MM-dd");
			} else {
				component = new Datebox();
				((Datebox) component).setFormat("yyyy-MM-dd");
				((Datebox) component).setText((String) valor);
			}
			((Datebox) component).setButtonVisible(!readonly);
		}
		component.setWidth("98%");
		if (component instanceof InputElement) {
			((InputElement) component).setReadonly(readonly);
			((InputElement) component).setInplace(inplace);

		}
		celda.appendChild(component);
		return celda;
	}

	public static String ifNull(String param) {
		if (param == null) {
			return "";
		} else {
			return param;
		}
	}

	public static void listarAnios(Listbox listbox, int rango) {
		listbox.getChildren().clear();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		boolean selected = false;
		for (int i = (year - rango); i < (year + rango); i++) {
			Listitem listitem = new Listitem();
			listitem.setValue(i + "");
			listitem.setLabel(i + "");
			if (i == year) {
				listitem.setSelected(true);
				selected = true;
			}
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0 && !selected) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void seleccionarAnio(Listbox listbox) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		boolean selected = false;
		for (int i = 0; i < listbox.getItemCount(); i++) {
			Listitem listitem = listbox.getItemAtIndex(i);
			int anio = Integer.parseInt(listitem.getValue().toString());
			if (anio == year) {
				listitem.setSelected(true);
				selected = true;
			}
		}
		if (listbox.getItemCount() > 0 && !selected) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarDepartamentos(Listbox listbox, boolean select,
			ServiceLocatorWeb serviceLocator) {
		listarDepartamentos(listbox, select,
				serviceLocator.getServicio(DepartamentosService.class));
	}

	public static void listarDepartamentos(Listbox listbox, boolean select,
			DepartamentosService departamentosService) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		List<Departamentos> departamentos = departamentosService
				.listar(new HashMap<String, Object>());

		for (Departamentos dpto : departamentos) {
			listitem = new Listitem();
			listitem.setValue(dpto.getCodigo());
			listitem.setLabel(dpto.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarMunicipios(Listbox listboxMun,
			Listbox listboxDpto, ServiceLocatorWeb serviceLocator) {
		listarMunicipios(listboxMun, listboxDpto,
				serviceLocator.getServicio(MunicipiosService.class));
	}

	public static void listarMunicipios(Listbox listboxMun,
			Listbox listboxDpto, MunicipiosService municipiosService) {
		listboxMun.getChildren().clear();
		Listitem listitem;
		String coddep = listboxDpto.getSelectedItem().getValue().toString();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("coddep", coddep);
		List<Municipios> municipios = municipiosService.listar(parameters);

		for (Municipios mun : municipios) {
			listitem = new Listitem();
			listitem.setValue(mun.getCodigo());
			listitem.setLabel(mun.getNombre());
			listboxMun.appendChild(listitem);
		}
		if (listboxMun.getItemCount() > 0) {
			listboxMun.setSelectedIndex(0);
		}
	}

	public static void listarTipo_afiliado(Listbox lbxTipo_afiliado,
			Listbox lbxTipo_usuario, ServiceLocatorWeb serviceLocator) {
		listarTipo_afiliado(lbxTipo_afiliado, lbxTipo_usuario,
				serviceLocator.getServicio(ElementoService.class));
	}

	public static void listarTipo_afiliado(Listbox lbxTipo_afiliado,
			Listbox lbxTipo_usuario, ElementoService elementoService) {
		lbxTipo_afiliado.getChildren().clear();
		Listitem listitem;
		String tipo = lbxTipo_usuario.getSelectedItem().getValue().toString();
		String tipo_afiliado = "";
		// log.info("tipo" + tipo);

		if (tipo.equals("2") || tipo.equals("6")) {
			tipo_afiliado = "tipo_afiliado_subsidiado";
		} else if (tipo.equals("1") || tipo.equals("4") || tipo.equals("10")) {
			tipo_afiliado = "tipo_afiliado";
		} else {
			tipo_afiliado = "tipo_afiliado_vinculado";
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tipo", tipo_afiliado);
		List<Elemento> elemento = elementoService.listar(parameters);

		for (Elemento mun : elemento) {
			listitem = new Listitem();
			listitem.setValue(mun.getCodigo());
			listitem.setLabel(mun.getDescripcion());
			lbxTipo_afiliado.appendChild(listitem);
		}
		if (lbxTipo_afiliado.getItemCount() > 0) {
			lbxTipo_afiliado.setSelectedIndex(0);
		}
	}

	public static void listarContratos(Listbox listbox, String codigo_admin,
			boolean select, boolean solo_abiertos, String codigo_empresa,
			String codigo_sucursal, ServiceLocatorWeb serviceLocator) {

		listbox.getChildren().clear();
		Listitem listitem;

		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- Seleccione --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("codigo_administradora", codigo_admin);
		if (solo_abiertos) {
			parameters.put("cerrado", false);
		}
		List<Contratos> lista_contratos = serviceLocator.getContratosService()
				.listar(parameters);

		for (Contratos contratos : lista_contratos) {
			listitem = new Listitem();
			listitem.setValue(contratos.getId_plan());
			listitem.setLabel(contratos.getId_plan() + " - "
					+ contratos.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarContratos(Listbox listbox, String codigo_admin,
			boolean select, boolean solo_abiertos, String codigo_empresa,
			String codigo_sucursal, ContratosService contratosService) {

		listbox.getChildren().clear();
		Listitem listitem;

		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- Seleccione --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("codigo_administradora", codigo_admin);
		if (solo_abiertos) {
			parameters.put("cerrado", false);
		}
		List<Contratos> lista_contratos = contratosService.listar(parameters);

		for (Contratos contratos : lista_contratos) {
			listitem = new Listitem();
			listitem.setValue(contratos.getId_plan());
			listitem.setLabel(contratos.getId_plan() + " - "
					+ contratos.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarContratosConValorObjeto(Listbox listbox,
			String codigo_admin, boolean select, boolean solo_abiertos,
			String codigo_empresa, String codigo_sucursal,
			ServiceLocatorWeb serviceLocator) {

		listbox.getChildren().clear();
		Listitem listitem;

		if (select) {
			listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("-- Seleccione --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("codigo_administradora", codigo_admin);
		if (solo_abiertos) {
			parameters.put("cerrado", false);
		}
		List<Contratos> lista_contratos = serviceLocator.getContratosService()
				.listar(parameters);

		for (Contratos contratos : lista_contratos) {
			listitem = new Listitem();
			listitem.setValue(contratos);
			listitem.setLabel(contratos.getId_plan() + " - "
					+ contratos.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarUsuarios(Listbox listbox, String codigo_empresa,
			String codigo_sucursal, boolean select,
			ServiceLocatorWeb serviceLocator) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		List<Usuarios> lista_usuarios = serviceLocator.getUsuariosService()
				.listar(parameters);

		for (Usuarios user : lista_usuarios) {
			listitem = new Listitem();
			listitem.setValue(user.getCodigo());
			listitem.setLabel(user.getNombres() + " " + user.getApellidos());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	/**
	 * <b>Este metodo verifica si se puede trabajar con prestador en:</b><br/>
	 * (01) autorizaciones <br/>
	 * (02) remisiones
	 *
	 */
	public static boolean noUsarPrestador(
			Parametros_empresa parametros_empresa, String tipo) {
		if (parametros_empresa != null) {
			if (tipo.equals("01")
					&& !parametros_empresa
							.getSelect_prestador_medico_adutorizacion()
							.equalsIgnoreCase("S")) {
				return true;
			} else if (tipo.equals("02")
					&& !parametros_empresa
							.getSelect_prestador_medico_remisiones()
							.equalsIgnoreCase("S")) {
				return true;
			}
			return false;
		} else {
			MensajesUtil.mensajeAlerta("Advertencia",
					"Para este modulo necesita tener parametros de empresa.");
			return false;
		}
	}

	/**
	 * Fade para Imagenes
	 *
	 * @throws IOException
	 *
	 *
	 */
	public static byte[] getImagenAplicandoFade(InputStream imagen, float fade)
			throws IOException {
		/* esta es la imagen incial */
		BufferedImage img = ImageIO.read(imagen);

		/* Esta es la nueva imagen */
		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics2d = (Graphics2D) bi.getGraphics();
		/* Aplicamos el efecto fade en la imagen */
		graphics2d.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, fade));
		/* Renderizamos la imagen */
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		/* Dibujamos la imagen */
		graphics2d.drawImage(img, null, 0, 0);

		/*
		 * Obtenemos la matriz de bytes, y sacamos el buffer para obtener el los
		 * bytes array
		 */
		return Images.encode("logo_fade.png", bi).getByteData();
	}

	public static void listarAnios_soat(Listbox listbox, String codigo_empresa,
			String codigo_sucursal, boolean select,
			ServiceLocatorWeb serviceLocator) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		List<Anio_soat> lista_anio_soat = serviceLocator.getServicio(
				GeneralExtraService.class).listar(Anio_soat.class, parameters);

		for (Anio_soat anio_soat : lista_anio_soat) {
			listitem = new Listitem();
			listitem.setValue(anio_soat.getAnio());
			listitem.setLabel(anio_soat.getAnio());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	// Este metodo es para descomponer la url cuando venga con parametros estilo
	// html ejemplo si la url vienen con /pages/adicion.zul?param1= el metodo
	// saca la url y los parametros los almacena en un mapa//
	public static Map<String, Object> descomponerUrl(String url) {
		Map<String, Object> mapUrl = new HashMap<String, Object>();

		StringTokenizer st = new StringTokenizer(url, "?");
		url = st.nextToken();
		String parametros = "";
		// //log.info("st.countTokens(): "+st.countTokens());
		if (st.hasMoreTokens()) {
			parametros = st.nextToken();
		}
		mapUrl.put("url", url);
		List<Map<String, String>> lista_parametros = new ArrayList<Map<String, String>>();
		st = new StringTokenizer(parametros, "&");
		while (st.hasMoreTokens()) {
			String tokens = st.nextToken();
			Map<String, String> paramBean = new HashMap<String, String>();
			StringTokenizer stValor = new StringTokenizer(tokens, "=");
			String key = stValor.nextToken();
			String value = "";
			if (stValor.hasMoreTokens()) {
				value = stValor.nextToken();
			}
			paramBean.put(key, value);
			lista_parametros.add(paramBean);
		}
		mapUrl.put("lista_parametros", lista_parametros);

		return mapUrl;
	}

	public static void listarNivel_educativo(Listbox listbox, boolean select,
			ServiceLocatorWeb serviceLocator) {
		listarNivel_educativo(listbox, select,
				serviceLocator.getServicio(Nivel_educativoService.class));
	}

	public static void listarNivel_educativo(Listbox listbox, boolean select,
			Nivel_educativoService nivel_educativoService) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();

		List<Nivel_educativo> lista_nivel_educativo = nivel_educativoService
				.listar(parameters);

		for (Nivel_educativo nivel_educativo : lista_nivel_educativo) {
			listitem = new Listitem();
			listitem.setValue(nivel_educativo.getId());
			listitem.setLabel(nivel_educativo.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarPertenencia_etnica(Listbox listbox,
			boolean select, ServiceLocatorWeb serviceLocator) {
		listarPertenencia_etnica(listbox, select,
				serviceLocator.getServicio(Pertenencia_etnicaService.class));
	}

	public static void listarPertenencia_etnica(Listbox listbox,
			boolean select, Pertenencia_etnicaService pertenencia_etnicaService) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();

		List<Pertenencia_etnica> lista_pertencia_etnica = pertenencia_etnicaService
				.listar(parameters);

		for (Pertenencia_etnica pertenencia_etnica : lista_pertencia_etnica) {
			listitem = new Listitem();
			listitem.setValue(pertenencia_etnica.getId());
			listitem.setLabel(pertenencia_etnica.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarOcupaciones(Listbox listbox, boolean select,
			ServiceLocatorWeb serviceLocator) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orden_nombre", "orden_nombre");
		List<Ocupaciones> listado_ocupaciones = serviceLocator
				.getOcupacionesService().listar(parameters);

		for (Ocupaciones ocupaciones : listado_ocupaciones) {
			listitem = new Listitem();
			listitem.setValue(ocupaciones.getId());
			listitem.setLabel(ocupaciones.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarLocalidades(Listbox listbox, boolean select,
			ServiceLocatorWeb serviceLocator) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();

		List<Localidad> lista_localidades = serviceLocator.getServicio(
				GeneralExtraService.class).listar(Localidad.class, parameters);

		for (Localidad localidad : lista_localidades) {
			listitem = new Listitem();
			listitem.setValue(localidad.getCodigo_localidad());
			listitem.setLabel(localidad.getLocalidad());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarEspecialidades(Listbox listbox, boolean select,
			ServiceLocatorWeb serviceLocator) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();

		List<Especialidad> lista_especialidades = serviceLocator
				.getEspecialidadService().listar(parameters);

		for (Especialidad especialidad : lista_especialidades) {
			listitem = new Listitem();
			listitem.setValue(especialidad.getCodigo());
			listitem.setLabel(especialidad.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarEspecialidades(Listbox listbox, boolean select,
			EspecialidadService especialidadService) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();

		List<Especialidad> lista_especialidades = especialidadService
				.listar(parameters);

		for (Especialidad especialidad : lista_especialidades) {
			listitem = new Listitem();
			listitem.setValue(especialidad.getCodigo());
			listitem.setLabel(especialidad.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	/**
	 * Este metodo calcula el resultado del calculo de las curvas.
	 *
	 * @author Luis Miguel
	 * @param medida
	 *            (Esta es la medida Ejemplo: Peso, Talla, Imc, etc)
	 * @param mediana
	 *            (Esta la mediana de la medida)
	 * @param menos_1_sd
	 *            (menos 1 desviacion estandar)
	 * @param mas_1_sd
	 *            (mas 1 desviacion estandar)
	 *
	 */
	public static double calcularMedianaCurvas(double medida, double mediana,
			double menos_1_sd, double mas_1_sd) {
		double medida_mediana = medida - mediana;
		if (medida_mediana < 0) {
			mediana = mediana - (menos_1_sd);
		} else {
			mediana = mas_1_sd - mediana;
		}
		Double r = medida_mediana / mediana;

		if (r > 3) {
			r = 3d;
		} else if (r < -3) {
			r = -3d;
		}
		return r;
	}

	/**
	 * Este metodo devuelve un BandboxRegistrosIMG con metodos ya predefinidos
	 * para llamar los procedimientos de los siguientes manuales SOAT, ISS01,
	 * ISS04
	 *
	 * @author Luis Miguel
	 * @param clasificacion
	 *            - Clasificacion de procedimientos
	 * @param serviceLocator
	 *            - Este es service locator
	 *
	 */
	public static BandboxRegistrosIMG<Map<String, Object>> inicializarBanboxRegistrosProcedimiento(
			final Map<String, Object> map, final ZKWindow zkWindow,
			final Paciente paciente) {
		return new BandboxRegistrosIMG<Map<String, Object>>() {

			@Override
			public void renderListitem(Listitem listitem,
					Map<String, Object> registro) {
				listitem.setValue(registro);
				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro
						.get(IClasificacionProcedimiento.CODIGO) + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro
						.get(IClasificacionProcedimiento.CODIGO_CUPS) + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro
						.get(IClasificacionProcedimiento.DESCRIPCION) + ""));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Map<String, Object>> listarRegistros(
					String valorBusqueda, Map<String, Object> parametros) {
				parametros.putAll(map);
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				parametros.put("limite_paginado", "limit 25 offset 0");
				ServiceLocatorWeb serviceLocator = zkWindow.getServiceLocator();
				return serviceLocator.getContratosService()
						.listarProcedimientos(parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Map<String, Object> registro) {
				textboxInformacion.setText(registro
						.get(IClasificacionProcedimiento.DESCRIPCION) + "");
				bandbox.setText(registro
						.get(IClasificacionProcedimiento.CODIGO_CUPS) + "");
				((BandboxRegistrosMacro) bandbox).setObject(registro);
				((Intbox) zkWindow.getFellow("ibx_cantidad"
						+ bandbox.getParent().getId().replaceAll("[^0-9]", "")))
						.setValue(1);
				if (paciente == null) {
					return true;
				} else {
					return verificarFrecuenciaOrdenamientoProcedimiento(
							paciente, registro, zkWindow,
							(BandboxRegistrosMacro) bandbox);
				}
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				((BandboxRegistrosMacro) bandbox).setObject(null);
				((Intbox) zkWindow.getFellow("ibx_cantidad"
						+ bandbox.getParent().getId().replaceAll("[^0-9]", "")))
						.setValue(0);
			}
		};
	}

	public static boolean verificarFrecuenciaOrdenamientoProcedimiento(
			Paciente paciente, final Map<String, Object> map_procedimiento,
			final ZKWindow zkWindow,
			final BandboxRegistrosMacro bandboxRegistros) {
		Frecuencia_ordenamiento frecuencia_ordenamiento = new Frecuencia_ordenamiento();
		frecuencia_ordenamiento.setCodigo_empresa(paciente.getCodigo_empresa());
		frecuencia_ordenamiento.setCodigo_sucursal(paciente
				.getCodigo_sucursal());
		frecuencia_ordenamiento.setCodigo(map_procedimiento
				.get(IClasificacionProcedimiento.CODIGO) + "");
		frecuencia_ordenamiento.setNro_identificacion(paciente
				.getNro_identificacion());
		frecuencia_ordenamiento = zkWindow.getServiceLocator()
				.getFrecuencia_ordenamientoService()
				.consultarActual(frecuencia_ordenamiento);

		Integer frecuencia = (Integer) map_procedimiento
				.get("frecuencia_orden");
		frecuencia = frecuencia != null ? frecuencia : 0;

		if (frecuencia_ordenamiento != null) {
			/* verificamos la frecuencia */
			if (frecuencia_ordenamiento.getFecha_realizacion() != null) {
				Timestamp today = new Timestamp(Calendar.getInstance()
						.getTimeInMillis());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(frecuencia_ordenamiento.getFecha_realizacion());
				calendar.set(
						Calendar.DAY_OF_MONTH,
						calendar.get(Calendar.DAY_OF_MONTH)
								+ frecuencia.intValue());
				Timestamp fecha_para_volver_hacer = new Timestamp(
						calendar.getTimeInMillis());

				if (today.compareTo(fecha_para_volver_hacer) > 0) {
					return true;
				} else {
					Messagebox
							.show("Este procedimiento ya ha sido enviado ha este paciente, y para poder enviar este procedimiento tiene que pasar de esta fecha ? para la realizacion de este procedimiento"
									.replace("?", new SimpleDateFormat(
											"yyyy/MM/dd hh:mm a")
											.format(fecha_para_volver_hacer)));
					return false;
				}
			} else {
				final Frecuencia_ordenamiento frecuencia_ordenamientoToSend = frecuencia_ordenamiento;
				final int fr = frecuencia;
				Messagebox
						.show("Este procedimiento ya ha sido enviado ha este paciente, pero no tiene registro de cuando se realizo",
								"Informacion", Messagebox.OK,
								Messagebox.INFORMATION, new EventListener() {

									@Override
									public void onEvent(Event arg0)
											throws Exception {
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("codigo",
												""
														+ map_procedimiento
																.get(IClasificacionProcedimiento.CODIGO));
										map.put("codigo_cups",
												""
														+ map_procedimiento
																.get(IClasificacionProcedimiento.CODIGO_CUPS));
										map.put("nombreve",
												""
														+ map_procedimiento
																.get(IClasificacionProcedimiento.DESCRIPCION));
										map.put("frec_orden",
												frecuencia_ordenamientoToSend);
										map.put("fr", fr);
										Component componente = Executions
												.createComponents(
														"/pages/frecuencia_ordenamiento.zul",
														zkWindow, map);
										final FrecuenciaOrdenamientoAction frecuenciaOrdenamientoAction = (FrecuenciaOrdenamientoAction) componente;
										frecuenciaOrdenamientoAction
												.setOnProcedimientoEvent(new OnProcedimientoEvent() {
													@Override
													public void onCancel() {
														bandboxRegistros
																.limpiarSeleccion(true);
													}

													@Override
													public void onAcept() {
														// este era si aceptaba
													}
												});
										frecuenciaOrdenamientoAction.doModal();
									}
								});
			}

		}
		return true;
	}

	/**
	 * 01=Medico general<br/>
	 * 02=Medico especialista interno<br/>
	 * 03=Medico especialista externo
	 *
	 */
	public static String getTipoMedico(String type) {
		if (type.equals("01")) {
			return "MEDICO GENERAL";
		} else if (type.equals("02")) {
			return "MEDICO ESPECIALISTA INTERNO";
		} else if (type.equals("03")) {
			return "MEDICO ESPECIALISTA EXTERNO";
		}
		return "MEDICO";
	}

	/* metodos repetidos */
	public static void buscarMedicos(String value, Listbox lbx,
			ServiceLocatorWeb serviceLocator, Sucursal sucursal)
			throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("rol_medico", "_todos");

			serviceLocator.getUsuariosService().setLimit("limit 25 offset 0");

			List<Map<String, Object>> list = serviceLocator
					.getUsuariosService().getUsuarioByRol(parameters);
			lbx.getItems().clear();
			for (Map<String, Object> dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label((String) dato
						.get(CODIGO_USUARIO)));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label((String) dato.get(NOMBRES)));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label((String) dato.get(APELLIDOS)));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(getTipoMedico((String) dato
						.get(TIPO_MEDICO))));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	public static void selectedMedicos(Listitem listitem, Bandbox bandbox,
			Textbox textbox, ZKWindow zkWindow) {
		boolean select = false;
		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
		} else {
			Map<String, Object> dato = (Map<String, Object>) listitem
					.getValue();
			bandbox.setValue((String) dato.get(CODIGO_USUARIO));
			textbox.setValue((String) dato.get(NOMBRES) + " "
					+ (String) dato.get(APELLIDOS));
			select = true;
		}

		if (zkWindow instanceof ISeleccionarMedico) {
			((ISeleccionarMedico) zkWindow).eventoSeleccionarMedico(select,
					(Map<String, Object>) listitem.getValue());
		}
		bandbox.close();
	}

	/**
	 * Este metodo me permite, mostrar el pdf del laboratorio
	 *
	 * @author Dario(Rochi)
	 * @author Luis Miguel
	 *
	 */
	public void mostrarPDF(Laboratorios_resultados laboratorios_resultados,
			Paciente paciente, Page page) {
		String directorio = laboratorios_resultados.getDireccion_archivo();
		File file = new File(directorio);
		if (file.exists()) {
			LaboratoriosResultadosVisualizador resultadosVisualizador = new LaboratoriosResultadosVisualizador();
			resultadosVisualizador.setPage(page);
			resultadosVisualizador.setClosable(true);
			resultadosVisualizador.setMaximizable(true);
			resultadosVisualizador.mostrarPDF(file, paciente);
			resultadosVisualizador.setHeight("75%");
			resultadosVisualizador.setWidth("70%");
			resultadosVisualizador.doModal();
		} else {
			MensajesUtil.mensajeAlerta("Advertencia",
					"El resultado realizado no ha sido encontrado..");
		}
	}

	/**
	 * Consulta elemento
	 *
	 * @author Luis Miguel
	 *
	 */
	public static String getDescripcionElemento(String codigo, String tipo,
			ServiceLocatorWeb locator) {
		Elemento elemento = new Elemento();
		elemento.setCodigo(codigo);
		elemento.setTipo(tipo);
		elemento = locator.getServicio(ElementoService.class).consultar(
				elemento);
		return elemento != null ? elemento.getDescripcion() : "";
	}

	/**
	 * Consulta elemento
	 *
	 * @author Luis Miguel
	 *
	 */
	public static String getDescripcionElemento(String codigo, String tipo,
			ElementoService elementoService) {
		Elemento elemento = new Elemento();
		elemento.setCodigo(codigo);
		elemento.setTipo(tipo);
		elemento = elementoService.consultar(elemento);
		return elemento != null ? elemento.getDescripcion() : "";
	}

	public static String getDescripcionElementoContaweb(String codigo,
			String tipo, ServiceLocatorWeb locator) {
		contaweb.modelo.bean.Elemento elemento = new contaweb.modelo.bean.Elemento();
		elemento.setCodigo(codigo);
		elemento.setTipo(tipo);
		elemento = locator.getServicio(
				contaweb.modelo.service.ElementoService.class).consultar(
				elemento);
		return elemento != null ? elemento.getDescripcion() : "";
	}

	public static void mostarReporte(Map<String, Object> param,
			Component component) {
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", component, param);
		final Window window = (Window) componente;
		window.setWidth("100%");
		window.setHeight("100%");
		window.doModal();
	}

	/**
	 *
	 * @param valor
	 *            Valor a comparar
	 * @param datos
	 *            vector de string con los que se va a comparar el valor iniciak
	 * @return retorna true si el valor inicial es igual a algun valor de la
	 *         lista
	 */
	public static boolean igualConjuncion(Object valor, String... datos) {
		if (datos != null) {
			for (String dato : datos) {
				if (valor.toString().equals(dato)) {
					return true;
				}
			}
		}
		return false;

	}

	public static boolean isMedico(String codigo_rol) {
		return (IRoles.MEDICO_CONS_EXTERNA.equals(codigo_rol)
				|| IRoles.MEDICO_URGENCIAS.equals(codigo_rol)
				|| IRoles.ODONTOLOGO.equals(codigo_rol)
				|| IRoles.PROFESIONAL_SALUD_MENTAL.equals(codigo_rol)
				|| isMedicoPyP(codigo_rol)
				|| IRoles.GINECOLOGO.equals(codigo_rol)
				|| IRoles.MEDICO_ESPECIALISTA.equals(codigo_rol)
				|| IRoles.FISIOTERAPEUTA.equals(codigo_rol)
				|| IRoles.FISIOTERAPEUTA_FISICA.equals(codigo_rol) || IRoles.MEDICO_DE_CUALQUIER_SERVICIO
					.equals(codigo_rol));
	}

	public static boolean isMedicoPyP(String codigo_rol) {
		return (IRoles.MEDICO_PYP.equals(codigo_rol)
				|| IRoles.MEDICO_DE_ADULTO_MAYOR.equals(codigo_rol)
				|| IRoles.MEDICO_DE_ALTERACION_EMBARAZO.equals(codigo_rol)
				|| IRoles.MEDICO_DE_ALTERACION_JOVEN.equals(codigo_rol)
				|| IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO.equals(codigo_rol)
				|| IRoles.MEDICO_DE_HIPERTENSO_DIABETICO.equals(codigo_rol)
				|| IRoles.MEDICO_DE_PLANIFICACION.equals(codigo_rol) || IRoles.MEDICO_DE_CUALQUIER_SERVICIO
					.equals(codigo_rol));
	}

	public static boolean isAuxiliarOEnfermeraPyP(String codigo_rol) {
		return (IRoles.AUXILIAR_ENFERMERIA_URGENCIA.equals(codigo_rol)
				|| IRoles.ENFERMERA_JEFE.equals(codigo_rol) || IRoles.ENFERMERA_JEFE_URGENCIAS
					.equals(codigo_rol));
	}

	public static boolean isAuxiliarEnfermeria(String codigo_rol) {
		return (IRoles.AUXILIAR_ENFERMERIA_URGENCIA.equals(codigo_rol)
				|| IRoles.AUXILIAR_ENFERMERIA.equals(codigo_rol) || IRoles.AUXILIAR_HOSPITALIZACION
					.equals(codigo_rol));
	}

	public static boolean isAdministrador(String codigo_rol) {
		return (IRoles.ADMINISTRADOR.equals(codigo_rol));
	}

	public static boolean isEnfermeraJefe(String codigo_rol) {
		return (IRoles.ENFERMERA_JEFE.equals(codigo_rol)
				|| IRoles.ENFERMERA_JEFE_URGENCIAS.equals(codigo_rol) || IRoles.ENFERMERA_JEFE_HOSPITALIZACION
					.equals(codigo_rol));
	}

	public static List<String> validarCodigo(String codigo_meta) {
		StringTokenizer stringTokenizer = new StringTokenizer(codigo_meta, "-");
		List<String> codigos = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {
			codigos.add(stringTokenizer.nextToken().trim());
		}
		return codigos;
	}

	public static void setdisable(Component component, boolean disabled) {
		if (component instanceof Radiogroup) {
			List<Radio> components = ((Radiogroup) component).getItems();
			for (Component component2 : components) {
				((Radio) component2).setDisabled(disabled);
			}
		}
	}

	public static void setDeseleccionar(Component component) {
		if (component instanceof Radiogroup) {
			List<Radio> components = ((Radiogroup) component).getItems();
			for (Component component2 : components) {
				((Radio) component2).setSelected(false);
			}
		}
	}

	/**
	 * Este metodo me permite separar los apellidos que esten unidos en una
	 * misma cadena de caracteres
	 *
	 * @author Luis Miguel
	 * @param nombre
	 * @return [0] Primer Apellido - [1] Segundo Apellido
	 *
	 */
	public static String[] separarApellido(String apellido) {
		String[] apllidos = new String[2];
		StringTokenizer st = new StringTokenizer(apellido.toUpperCase(), " ");
		apllidos[0] = "";
		apllidos[1] = "";
		int index = 0;
		while (st.hasMoreTokens()) {
			if (index == 0) {
				apllidos[0] += st.nextToken() + " ";
				index++;
			} else if (index == 1 && apellido.trim().equals("DE")) {
				apllidos[0] += st.nextToken();
			} else if (index == 1 && !apellido.trim().equals("DE")) {
				apllidos[1] += st.nextToken() + " ";
				index++;
			} else {
				apllidos[1] += " " + st.nextToken();
			}
		}
		return apllidos;
	}

	/**
	 * Este metodo me permite separar los nombres que esten unidos en una misma
	 * cadena de caracteres
	 *
	 * @author Luis Miguel
	 * @param nombre
	 * @return [0] Primer Nombre - [1] Segundo Nombre
	 *
	 */
	public static String[] separarNombre(String nombre) {
		String[] nombres = new String[2];
		StringTokenizer st = new StringTokenizer(nombre, " ");
		int index = 0;
		nombres[0] = "";
		nombres[1] = "";
		while (st.hasMoreTokens()) {
			if (index == 0) {
				nombres[0] = st.nextToken();
			} else if (index == 1) {
				nombres[1] = st.nextToken();
			} else {
				nombres[1] += " " + st.nextToken();
			}
			index++;
		}
		return nombres;
	}

	public static double obtenerValorPrimitivo(Double valor) {
		if (valor != null) {
			return valor.doubleValue();
		} else {
			return 0.0;
		}
	}

	public static void generarDatosProcedimientos(Admision admision,
			Long id_proc, Map<String, Object> procedimiento,
			Manuales_tarifarios manual, Integer anio_periodo, ZKWindow zkWindow) {
		final ServiceLocatorWeb serviceLocator = zkWindow.getServiceLocator();

		double valor_pcd = 0;
		double valor_real = 0;
		double descuento = 0, incremento = 0;
		String codigo_cups = "";

		Manuales_procedimientos manuales_procedimientos = new Manuales_procedimientos();
		manuales_procedimientos.setId_procedimiento(id_proc);
		manuales_procedimientos.setId_manual(manual.getId_maestro_manual());

		manuales_procedimientos = serviceLocator.getServicio(
				Manuales_procedimientosService.class).consultar(
				manuales_procedimientos);

		if (manuales_procedimientos != null) {
			Maestro_manual maestro_manual = new Maestro_manual();
			maestro_manual.setId_manual(manuales_procedimientos.getId_manual());
			maestro_manual = serviceLocator.getServicio(
					Maestro_manualService.class).consultar(maestro_manual);
			if (!maestro_manual.getTipo_manual().equals(
					IConstantes.TIPO_MANUAL_SOAT)) {
				valor_pcd = (Double) procedimiento.get("valor_pcd");
			} else {
				double porcentaje = (Double) procedimiento
						.get("valor_porcentaje");

				Anio_soat anio_soat = new Anio_soat();
				anio_soat.setAnio(anio_periodo + "");
				// log.info("anio_periodo>>>>>>>>>>" + anio_periodo);
				anio_soat = serviceLocator.getServicio(
						GeneralExtraService.class).consultar(anio_soat);

				valor_pcd = (anio_soat != null ? (int) (anio_soat
						.getValor_anio() * porcentaje) : 0);

				if (manual != null) {
					if (manual.getTipo_general().equals("01")) {
						descuento = (int) (valor_pcd * (manual.getGeneral() / 100));
						valor_pcd -= descuento;
					} else if (manual.getTipo_general().equals("02")) {
						incremento = (int) (valor_pcd * (manual.getGeneral() / 100));
						valor_pcd += incremento;
					}
				}
			}

			valor_real = (Double) procedimiento.get("valor_real");
			descuento = (Double) procedimiento.get("descuento");
			incremento = (Double) procedimiento.get("incremento");
			codigo_cups = (String) procedimiento.get("codigo_cups");

			Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
			datos_procedimiento.setCodigo_empresa(admision.getCodigo_empresa());
			datos_procedimiento.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			datos_procedimiento.setCodigo_registro(null);
			;
			datos_procedimiento.setTipo_identificacion(admision.getPaciente()
					.getTipo_identificacion());
			datos_procedimiento.setNro_identificacion(admision
					.getNro_identificacion());
			datos_procedimiento.setCodigo_cups(codigo_cups);
			datos_procedimiento.setCodigo_administradora(admision
					.getCodigo_administradora());
			datos_procedimiento.setId_plan(admision.getId_plan());
			datos_procedimiento
					.setCodigo_prestador(admision.getCodigo_medico());
			datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
			datos_procedimiento.setCodigo_procedimiento(id_proc + "");
			datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
					.getInstance().getTimeInMillis()));
			datos_procedimiento.setNumero_autorizacion("");
			datos_procedimiento.setValor_procedimiento(valor_pcd);
			datos_procedimiento.setUnidades(1);
			datos_procedimiento.setCopago(0.0);
			datos_procedimiento.setValor_neto(valor_pcd);
			datos_procedimiento
					.setAmbito_procedimiento(ServiciosDisponiblesUtils
							.getAmbitoRealizacion(admision));
			datos_procedimiento
					.setFinalidad_procedimiento(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
			datos_procedimiento
					.setPersonal_atiende(IDatosProcedimientos.PERSONAL_ATIENDE);
			datos_procedimiento
					.setForma_realizacion(IDatosProcedimientos.FORMA_REALIZACION);
			datos_procedimiento.setCodigo_diagnostico_principal(admision
					.getDiagnostico_ingreso());
			datos_procedimiento.setCodigo_diagnostico_relacionado("");
			datos_procedimiento.setComplicacion("");
			datos_procedimiento.setCancelo_copago("N");

			datos_procedimiento.setCreacion_date(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			datos_procedimiento.setUltimo_update(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			datos_procedimiento.setCreacion_user(admision.getCreacion_user());
			datos_procedimiento.setUltimo_user(admision.getCreacion_user());
			datos_procedimiento.setValor_real(valor_real);
			datos_procedimiento.setDescuento(descuento);
			datos_procedimiento.setIncremento(incremento);
			datos_procedimiento
					.setRealizado_en(IDatosProcedimientos.FINALIDAD_PROCEDIMIENTO);
			serviceLocator.getDatos_procedimientoService().crear(
					datos_procedimiento);
		} else {
			throw new ValidacionRunTimeException(
					"El procedimiento "
							+ id_proc
							+ " que intenta consultar no se encuentra relacionado con el manual tarifario en cuestion");
		}

	}

	public static String getEstadoCobro(String via_ingreso) {
		if (via_ingreso.equals(IVias_ingreso.ADULTO_MAYOR)
				|| via_ingreso.equals(IVias_ingreso.ALTERACION_JOVEN)
				|| via_ingreso.equals(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)
				|| via_ingreso.equals(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)
				|| via_ingreso.equals(IVias_ingreso.HC_MENOR_2_MESES)
				|| via_ingreso.equals(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)
				|| via_ingreso
						.equals(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)
				|| via_ingreso
						.equals(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO)
				|| via_ingreso.equals(IVias_ingreso.PLANIFICACION_FAMILIAR)
				|| via_ingreso.equals(IVias_ingreso.SALUD_ORAL)
				|| via_ingreso.equals(IVias_ingreso.HIPERTENSO_DIABETICO)) {
			return IConstantesAutorizaciones.ESTADO_COBRO_PYP;
		} else {
			return IConstantesAutorizaciones.ESTADO_COBRO_MEDICINA_GENERAL;
		}
	}

	/**
	 * Este metodo me permite habilitar los filtros correspondientes a la
	 * enfermera
	 *
	 * @author Dario Perez
	 * @author Luis Miguel
	 * @return IConstantes FILTRO
	 *
	 */
	public static String habilitarFiltroEnfermera(String via_ingreso,
			String tipo_cita, Parametros_empresa parametros_empresa) {
		String habilitar = IConstantes.FILTRO_MEDICO;
		if (parametros_empresa.getAtender_enfermeras_gefe_primeravez().equals(
				"S")) {
			habilitar = IConstantes.FILTRO_MOSTRAR_TODOS;
		} else {
			if (via_ingreso.equals(IVias_ingreso.PLANIFICACION_FAMILIAR)) {
				habilitar = IConstantes.FILTRO_MOSTRAR_TODOS;
			} else if ((via_ingreso
					.equals(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)
					|| via_ingreso.equals(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS) || via_ingreso
						.equals(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS))
					&& tipo_cita.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				habilitar = IConstantes.FILTRO_MEDICO;
			} else if ((via_ingreso
					.equals(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)
					|| via_ingreso.equals(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS) || via_ingreso
						.equals(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS))
					&& tipo_cita.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
				habilitar = IConstantes.FILTRO_ENFERMERA;
			} else if (via_ingreso
					.equals(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)
					&& tipo_cita.equals(IConstantes.TIPO_HISTORIA_PRIMERA_VEZ)) {
				habilitar = IConstantes.FILTRO_MEDICO;
			} else if (via_ingreso
					.equals(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)
					&& tipo_cita.equals(IConstantes.TIPO_HISTORIA_CONTROL)) {
				habilitar = IConstantes.FILTRO_MOSTRAR_TODOS;
			}
		}
		return habilitar;
	}

	public static Plan_tratamiento convertirDetalle_ordenAPlanTratamiento(
			Detalle_orden detalle_orden) {
		Plan_tratamiento plan_tratamiento = new Plan_tratamiento();
		plan_tratamiento.setCodigo_empresa(detalle_orden.getCodigo_empresa());
		plan_tratamiento.setCodigo_sucursal(detalle_orden.getCodigo_sucursal());
		plan_tratamiento.setCodigo_procedimiento(detalle_orden
				.getCodigo_procedimiento());
		plan_tratamiento.setValor_procedimiento(detalle_orden
				.getValor_procedimiento());
		plan_tratamiento
				.setUnidades(detalle_orden.getUnidades() != null ? detalle_orden
						.getUnidades() : 0);
		plan_tratamiento.setValor_real(detalle_orden.getValor_real());
		plan_tratamiento.setDescuento(detalle_orden.getDescuento());
		plan_tratamiento.setIncremento(detalle_orden.getIncremento());
		plan_tratamiento.setCodigo_cups(detalle_orden.getCodigo_cups());
		plan_tratamiento.setRealizado(detalle_orden.getRealizado());
		plan_tratamiento.setUnidades_realizadas(detalle_orden
				.getUnidades_realizadas());
		plan_tratamiento.setTipo_procedimiento(detalle_orden
				.getTipo_procedimiento());
		plan_tratamiento.setCreacion_date(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		plan_tratamiento.setUltimo_update(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		return plan_tratamiento;
	}

	public static String generarCodigoProximoAdmision(Paciente paciente,
			ServiceLocatorWeb serviceLocator) {
		Registro_admision ra = new Registro_admision();
		Registro_admisionService raDao = serviceLocator
				.getServicio(Registro_admisionService.class);
		ra.setCodigo_empresa(paciente.getCodigo_empresa());
		ra.setCodigo_sucursal(paciente.getCodigo_sucursal());
		ra.setCodigo_paciente(paciente.getNro_identificacion());
		if (raDao.consultar(ra) != null) {
			ra = (Registro_admision) raDao.consultar(ra);
		} else {
			ra.setNro_ingreso("1");
		}
		int nro_ing = Integer.parseInt(ra.getNro_ingreso());
		return new DecimalFormat("0000").format(nro_ing);
	}

	public static String generarCodigoProximoAdmision(Paciente paciente,
			Registro_admisionService registro_admisionService) {
		Registro_admision ra = new Registro_admision();
		ra.setCodigo_empresa(paciente.getCodigo_empresa());
		ra.setCodigo_sucursal(paciente.getCodigo_sucursal());
		ra.setCodigo_paciente(paciente.getNro_identificacion());
		if (registro_admisionService.consultar(ra) != null) {
			ra = (Registro_admision) registro_admisionService.consultar(ra);
		} else {
			ra.setNro_ingreso("1");
		}
		int nro_ing = Integer.parseInt(ra.getNro_ingreso());
		return new DecimalFormat("0000").format(nro_ing);
	}

	public static String getCodigoReciboCaja(Paciente paciente,
			ServiceLocatorWeb locator) {
		String nro_ingreso_proximo = generarCodigoProximoAdmision(paciente,
				locator);
		return paciente.getTipo_identificacion() + nro_ingreso_proximo
				+ paciente.getNro_identificacion();
	}

	public static String getCodigoReciboCaja(Paciente paciente,
			Registro_admisionService registro_admisionService) {
		String nro_ingreso_proximo = generarCodigoProximoAdmision(paciente,
				registro_admisionService);
		return paciente.getTipo_identificacion() + nro_ingreso_proximo
				+ paciente.getNro_identificacion();
	}

	public static Map<String, Object> getAdmisionFantasma(
			Map<String, Object> parametro) {
		Empresa empresa = (Empresa) parametro.get("empresa");
		Sucursal sucursal = (Sucursal) parametro.get("sucursal");
		Paciente paciente = (Paciente) parametro.get("paciente");
		ServiceLocatorWeb serviceLocator = (ServiceLocatorWeb) parametro
				.get("serviceLocator");
		Usuarios usuarios = (Usuarios) parametro.get("usuario");
		Usuarios vacunador = (Usuarios) parametro.get("vacunador");
		if (vacunador == null) {
			vacunador = usuarios;
		}
		Centro_atencion centro_atencion_session = (Centro_atencion) parametro
				.get("centro_atencion_session");
		List<Detalle_orden> detalle_ordens = (List<Detalle_orden>) parametro
				.get("dtt_orden");

		String nro_ingreso = generarCodigoProximoAdmision(paciente,
				serviceLocator);
		String codigo_cie = "Z000";

		Admision admision = new Admision();
		admision.setCodigo_empresa(empresa.getCodigo_empresa());
		admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		admision.setNro_ingreso(nro_ingreso);
		admision.setNro_identificacion(paciente.getNro_identificacion());
		admision = serviceLocator.getServicio(AdmisionService.class).consultar(
				admision);

		if (admision == null) {
			admision = new Admision();
			admision.setCodigo_empresa(empresa.getCodigo_empresa());
			admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			admision.setNro_ingreso(nro_ingreso);
			admision.setNro_identificacion(paciente.getNro_identificacion());
			admision.setEstado("1");
			admision.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			admision.setAtendida(false);
		} else {
			codigo_cie = admision.getDiagnostico_ingreso();
		}
		admision.setCodigo_administradora(paciente.getCodigo_administradora());

		// En esta parte agregamos el vacunador
		admision.setCodigo_medico("" + vacunador.getCodigo());

		admision.setFecha_ingreso(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		admision.setNro_autorizacion("");
		admision.setVia_ingreso(IVias_ingreso.VIA_VACUNACION);
		admision.setTipo_atencion("01");
		admision.setProcedencia("");
		admision.setCodigo_especialidad("");
		admision.setCama("");
		admision.setIngreso_programa("N");
		admision.setPrimera_vez("N");
		admision.setCondicion_usuaria("");
		admision.setCausa_externa(IConstantes.CAUSA_EXTERNA_OTRA);
		admision.setTipo_diagnostico("1");
		admision.setDiagnostico_ingreso(codigo_cie);
		admision.setTipo_discapacidad("");
		admision.setGrado_discapacidad("");
		admision.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		admision.setCreacion_user(usuarios.getCodigo().toString());
		admision.setUltimo_user(usuarios.getCodigo().toString());
		admision.setUrgencias("N");
		admision.setHospitalizacion("N");
		admision.setRecien_nacido("N");

		admision.setCodigo_cita("");

		admision.setAplica_triage(false);
		admision.setAplica_tuberculosis(false);
		admision.setRealizo_triage(false);
		admision.setAplica_lepra(false);
		admision.setPaciente_remitido("N");
		admision.setTipo_consulta("");
		admision.setTipo_adicional_via_ingreso("");
		admision.setAdmision_parto("N");

		admision.setCodigo_centro(centro_atencion_session.getCodigo_centro());

		// verificamos si es para un particular
		admision.setParticular("N");
		admision.setTipo_psicologia("");

		parametro.clear();
		parametro.put("admision", admision);
		parametro.put("accion", "registrar");
		parametro.put("codigo_atencion", "");
		parametro.put("codigo_pabellon", "");
		parametro.put("codigo_habitacion", "");

		parametro.put("empresa", empresa);
		parametro.put("dtt_servicios", detalle_ordens);
		return parametro;
	}

	public static Detalle_orden convertirVacunasPacienteADetalleOrden(
			Vacunas vacunas_pacientes, ServiceLocatorWeb serviceLocator) {
		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(vacunas_pacientes
				.getCodigo_procedimiento()));
		procedimientos = serviceLocator.getProcedimientosService().consultar(
				procedimientos);
		Detalle_orden detalle_orden = new Detalle_orden();
		detalle_orden.setCodigo_empresa(vacunas_pacientes.getCodigo_empresa());
		detalle_orden
				.setCodigo_sucursal(vacunas_pacientes.getCodigo_sucursal());
		detalle_orden.setCodigo_cups(procedimientos.getCodigo_cups());
		detalle_orden.setCodigo_procedimiento(vacunas_pacientes
				.getCodigo_procedimiento());
		detalle_orden.setUnidades(1);
		detalle_orden.setRealizado("N");
		detalle_orden.setUnidades_realizadas(0);
		detalle_orden.setCodigo_orden(null);

		/*
		 * los valores van en cero por que a la hora de facturarlos el los
		 * recalcula
		 */
		detalle_orden.setValor_procedimiento(0);
		detalle_orden.setDescuento(0);
		detalle_orden.setIncremento(0);
		detalle_orden.setValor_real(0);
		return detalle_orden;
	}

	public static List<Esquema_vacunacion> getVacunasPendientes(
			Paciente paciente, ServiceLocatorWeb serviceLocator) {
		List<Esquema_vacunacion> vacunasPermitidas = ManejadorPoblacion
				.getVacunasDisponibles(paciente, serviceLocator);
		List<Esquema_vacunacion> vacunasPermitidasSalida = new ArrayList<Esquema_vacunacion>();
		if (!vacunasPermitidas.isEmpty()) {
			for (Esquema_vacunacion vacunas : vacunasPermitidas) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", paciente.getCodigo_empresa());
				map.put("codigo_sucursal", paciente.getCodigo_sucursal());
				map.put("nro_identificacion", paciente.getNro_identificacion());
				map.put("codigo_cups_vacuna", vacunas.getCodigo_vacuna());
				map.put("id_esquema_vacunacion", vacunas.getConsecutivo());
				if (!serviceLocator.getServicio(Vacunas_pacientesService.class)
						.existe(map)) {
					vacunasPermitidasSalida.add(vacunas);
				}
			}
		}
		return vacunasPermitidasSalida;
	}

	public static Detalle_receta_rips convertirMapDetalleReceta(
			Map<String, Object> bean, Sucursal sucursal) {
		Double cantidad = (Double) bean.get("cantidad_recetada");

		String codigo_articulo = (String) bean.get("codigo_articulo");
		double cantidad_recetada = cantidad != null ? cantidad : 0;
		double valor_unitario = (Double) bean.get("valor_unitario");
		double valor_total = (Double) bean.get("valor_total");
		String posologia = (String) bean.get("posologia");
		double descuento = (Double) bean.get("descuento");
		double incremento = (Double) bean.get("incremento");
		double valor_real = (Double) bean.get("valor_real");
		boolean autorizado = (Boolean) bean.get("autorizado");
		String estado_cobro = (String) bean.get("estado_cobro");
		Integer tiempo_tto = (Integer) bean.get("tiempo_tto");

		if (tiempo_tto == null) {
			tiempo_tto = 0;
		}

		String via_administracion = (String) bean.get("via_administracion");

		// log.info(":: medicamento autorizado: " + autorizado);
		// log.info(":: Estado de Cobro: " + estado_cobro);
		Detalle_receta_rips detalle = new Detalle_receta_rips();
		detalle.setCodigo_articulo(codigo_articulo);
		detalle.setCantidad_recetada(cantidad_recetada);
		detalle.setCantidad_entregada(0.0);
		detalle.setValor_unitario(valor_unitario);
		detalle.setValor_total(valor_total);
		detalle.setValor_real(valor_real);
		detalle.setDescuento(descuento);
		detalle.setIncremento(incremento);
		detalle.setUnm(0);
		detalle.setPosologia(posologia);
		detalle.setEntregado("N");
		detalle.setAutorizado(sucursal.getTipo().equals(
				IConstantes.TIPOS_SUCURSAL_CAJA_PREV) ? autorizado : true);
		detalle.setEstado_cobro(estado_cobro);
		detalle.setTiempo_tto(tiempo_tto);
		detalle.setVia_administracion(via_administracion);
		return detalle;
	}

	/**
	 * Este metodo me permite verificar medicamentos no autorizados
	 *
	 */
	public static boolean verificarMedicamentoNoAutorizadosEnSolicitud(
			List<Detalle_receta_rips> dtt_recetas,
			List<Detalle_solicitud_tecnico> dtt_solicituds) {
		for (Detalle_receta_rips dtt_receta : dtt_recetas) {
			boolean existe = false;
			for (Detalle_solicitud_tecnico dtt_solicitud : dtt_solicituds) {
				if (dtt_solicitud.getCodigo_medicamento().equals(
						dtt_receta.getCodigo_articulo())) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				return true;
			}
		}
		return false;
	}

	public static void generarContendioAntecedentesFamiliares(
			Bandbox bandbox_antecedente) {

		String contenido = obtenerXmlTextbox(bandbox_antecedente);

		Bandpopup bandpopup = null;
		Grid grid = null;
		Rows filas = null;

		if (bandbox_antecedente.getFirstChild() != null
				&& (bandbox_antecedente.getFirstChild() instanceof Bandpopup)) {
			bandpopup = (Bandpopup) bandbox_antecedente.getFirstChild();
		} else {
			bandpopup = new Bandpopup();
		}

		bandpopup.getChildren().clear();

		bandpopup.setWidth("300px");
		grid = new Grid();
		filas = new Rows();
		grid.appendChild(filas);
		bandpopup.appendChild(grid);

		Map<String, Object> mapa_aux = null;

		if (!contenido.isEmpty()) {
			try {
				mapa_aux = ConvertidorXmlToMap.convertirToMap(contenido);
			} catch (Exception e) {
				mapa_aux = new HashMap<String, Object>();
			}
		}

		final Map<String, Object> mapa_contenido = mapa_aux != null ? mapa_aux
				: new HashMap<String, Object>();

		Row fila = new Row();
		Hlayout hlayout = new Hlayout();
		final Checkbox checkbox_padre = new Checkbox("Padre");
		checkbox_padre.setChecked(mapa_contenido.containsKey("padre"));

		final Checkbox checkbox_madre = new Checkbox("Madre");
		checkbox_madre.setChecked(mapa_contenido.containsKey("madre"));

		final Checkbox checkbox_hermano = new Checkbox("Hermano");
		checkbox_hermano.setChecked(mapa_contenido.containsKey("hermano"));

		final Checkbox checkbox_abuelo_paterno = new Checkbox(
				"Abuelo (Paterno)");
		checkbox_abuelo_paterno.setChecked(mapa_contenido
				.containsKey("abuelo_paterno"));

		final Checkbox checkbox_abuelo_materno = new Checkbox(
				"Abuelo (Materno)");
		checkbox_abuelo_materno.setChecked(mapa_contenido
				.containsKey("abuelo_materno"));

		final Checkbox checkbox_abuela_paterna = new Checkbox(
				"Abuela (Paterna)");
		checkbox_abuela_paterna.setChecked(mapa_contenido
				.containsKey("abuela_paterna"));

		final Checkbox checkbox_abuela_materna = new Checkbox(
				"Abuela (Materna)");
		checkbox_abuela_materna.setChecked(mapa_contenido
				.containsKey("abuela_materna"));

		final Checkbox checkbox_otros = new Checkbox("Otros Familiares");
		checkbox_otros.setChecked(mapa_contenido.containsKey("otros"));

		final Textbox textbox_otros = new Textbox();
		textbox_otros.setVisible(mapa_contenido.containsKey("otros"));
		textbox_otros.setHflex("1");

		if (mapa_contenido.containsKey("otros_familiares")) {
			textbox_otros.setValue(mapa_contenido.get("otros_familiares")
					.toString());
		} else {
			textbox_otros.setValue("");
		}

		hlayout.appendChild(checkbox_padre);
		hlayout.appendChild(checkbox_madre);
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		hlayout.appendChild(checkbox_hermano);
		hlayout.appendChild(new Space());
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		hlayout.appendChild(checkbox_abuelo_paterno);
		hlayout.appendChild(checkbox_abuelo_materno);
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		hlayout.appendChild(checkbox_abuela_paterna);
		hlayout.appendChild(checkbox_abuela_materna);
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		fila = new Row();
		hlayout = new Hlayout();
		hlayout.appendChild(checkbox_otros);
		hlayout.appendChild(textbox_otros);
		fila.appendChild(hlayout);
		filas.appendChild(fila);

		final Bandbox bandbox = bandbox_antecedente;

		EventListener<Event> eventListener = new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getTarget() != null) {
					if (checkbox_padre.equals(event.getTarget())) {
						if (checkbox_padre.isChecked()) {
							mapa_contenido.put("padre", "padre");
						} else {
							mapa_contenido.remove("padre");
						}
					} else if (checkbox_madre.equals(event.getTarget())) {
						if (checkbox_madre.isChecked()) {
							mapa_contenido.put("madre", "madre");
						} else {
							mapa_contenido.remove("madre");
						}
					} else if (checkbox_hermano.equals(event.getTarget())) {
						if (checkbox_hermano.isChecked()) {
							mapa_contenido.put("hermano", "hermano");
						} else {
							mapa_contenido.remove("hermano");
						}
					} else if (checkbox_abuelo_paterno
							.equals(event.getTarget())) {
						if (checkbox_abuelo_paterno.isChecked()) {
							mapa_contenido.put("abuelo_paterno",
									"abuelo_paterno");
						} else {
							mapa_contenido.remove("abuelo_paterno");
						}
					} else if (checkbox_abuelo_materno
							.equals(event.getTarget())) {
						if (checkbox_abuelo_materno.isChecked()) {
							mapa_contenido.put("abuelo_materno",
									"abuelo_materno");
						} else {
							mapa_contenido.remove("abuelo_materno");
						}
					} else if (checkbox_abuela_paterna
							.equals(event.getTarget())) {
						if (checkbox_abuela_paterna.isChecked()) {
							mapa_contenido.put("abuela_paterna",
									"abuela_paterna");
						} else {
							mapa_contenido.remove("abuela_paterna");
						}
					} else if (checkbox_abuela_materna
							.equals(event.getTarget())) {
						if (checkbox_abuela_materna.isChecked()) {
							mapa_contenido.put("abuela_materna",
									"abuela_materna");
						} else {
							mapa_contenido.remove("abuela_materna");
						}
					}

					mostrarXmlTextbox(bandbox, mapa_contenido);

				}
			}
		};

		checkbox_otros.addEventListener(Events.ON_CHECK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						if (checkbox_otros.isChecked()) {
							textbox_otros.setVisible(true);
							mapa_contenido.put("otros", "otros");
							mostrarXmlTextbox(bandbox, mapa_contenido);
						} else {
							textbox_otros.setVisible(false);
							mapa_contenido.remove("otros");
							mapa_contenido.remove("otros_familiares");

							mostrarXmlTextbox(bandbox, mapa_contenido);
						}
					}

				});

		textbox_otros.addEventListener(Events.ON_CHANGE,
				new EventListener<InputEvent>() {

					@Override
					public void onEvent(InputEvent event) throws Exception {
						mapa_contenido.put("otros_familiares", event.getValue());
						mostrarXmlTextbox(bandbox, mapa_contenido);
					}

				});

		checkbox_abuela_materna
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_abuela_paterna
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_abuelo_materno
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_abuelo_paterno
				.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_hermano.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_madre.addEventListener(Events.ON_CHECK, eventListener);
		checkbox_padre.addEventListener(Events.ON_CHECK, eventListener);

	}

	public static void mostrarXmlTextbox(Textbox textbox,
			Map<String, Object> mapa_contenido) {
		try {
			textbox.setAttribute("XML_CONTENIDO",
					ConvertidorXmlToMap.convertirToXML(mapa_contenido));
			StringBuffer stringBuffer = new StringBuffer();
			for (String key : mapa_contenido.keySet()) {
				Object valor = mapa_contenido.get(key);
				if (key.equals(valor)) {
					stringBuffer.append(key).append(";");
				} else {
					if (!valor.toString().isEmpty()) {
						stringBuffer.append(key).append("=").append(valor)
								.append(";");
					}
				}
			}
			textbox.setText(stringBuffer.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static String obtenerXmlTextbox(Textbox textbox) {
		if (textbox.hasAttribute("XML_CONTENIDO")) {
			return (String) textbox.getAttribute("XML_CONTENIDO");
		} else {
			return "";
		}
	}

	/**
	 * CONFIG_TALLA (C - centimetros, M - metros)
	 *
	 */
	public static void onCalcularIMC(Doublebox mcDbxIMC, Doublebox mcDbxPeso,
			Doublebox mcDbxTalla, String CONFIG_TALLA) {
		try {
			mcDbxIMC.setConstraint("");
			mcDbxIMC.setValue(null);
			Double talla = mcDbxTalla.getValue();
			Double peso = mcDbxPeso.getValue();

			if (talla != null && peso != null) {
				if (talla.compareTo(0.0) != 0) {
					if (CONFIG_TALLA.equals("C")) {
						talla = talla / 100;
					}
					double imc = (peso.doubleValue() / (Math.pow(
							talla.doubleValue(), 2)));
					// log.info("imc: " + imc);
					mcDbxIMC.setValue(imc);
					String alerta = "";
					String color_alerta = "none";
					if (0 <= imc && imc <= 18.4) {
						alerta = "Desnutricion - Riesgo(Aumentado)";
						color_alerta = "red";
					} else if (18.5 <= imc && imc <= 24.9) {
						alerta = "Rango Normal";
						color_alerta = "white";
					} else if (25 <= imc && imc <= 29.9) {
						alerta = "Sobrepeso - Riesgo(Aumentado)";
						color_alerta = "blue";
					} else if (30 <= imc && imc <= 34.9) {
						alerta = "Obesidad grado I - Riesgo(Moderado)";
						color_alerta = "pink";
					} else if (35 <= imc && imc <= 39.9) {
						alerta = "Obesidad grado II - Riesgo(Severo)";
						color_alerta = "#e37676";
					} else if (imc >= 40) {
						alerta = "Obesidad grado III - Riesgo(Muy severo)";
						color_alerta = "#823434";
					}
					if (!alerta.isEmpty()) {
						mcDbxIMC.setTooltiptext(alerta);
						mcDbxIMC.setStyle("background-color:" + color_alerta);
						if (color_alerta.equals("white")) {
							MensajesUtil.notificarInformacion(alerta, mcDbxIMC);
						} else {
							MensajesUtil.notificarAlerta(alerta, mcDbxIMC);
						}
					} else {
						mcDbxIMC.setTooltiptext(null);
						mcDbxIMC.setStyle("background-color:" + color_alerta);

					}
				} else {
					mcDbxIMC.setTooltiptext(null);
					mcDbxIMC.setStyle("background-color:white");
				}
			} else {
				mcDbxIMC.setTooltiptext(null);
				mcDbxIMC.setStyle("background-color:white");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite, calcular la fecha espertante de parto
	 *
	 */
	public static Date calcularFechaEsperadaParto(Date date) {
		if (date != null) {
			Calendar calendar_fur = Calendar.getInstance();
			calendar_fur.setTime(date);
			int mes = calendar_fur.get(Calendar.MONTH);
			int sumador_mes = 7;
			if (mes < 2) {
				sumador_mes = 10;
			}
			calendar_fur.set(Calendar.MONTH,
					calendar_fur.get(Calendar.MONTH) + 9);
			calendar_fur.set(Calendar.DAY_OF_MONTH,
					calendar_fur.get(Calendar.DAY_OF_MONTH) + sumador_mes);
			return calendar_fur.getTime();
		}
		return null;
	}

	public static String getSemanasEmbarazo(Date fecha_ultima_regla) {
		if (fecha_ultima_regla != null) {
			int dias = Util.getDiasDiferencia(fecha_ultima_regla, Calendar
					.getInstance().getTime());
			int semanas = dias / 7;
			return semanas + "";
		}
		return "";
	}

	/**
     *
     *
     */
	public static String mostrarXml(Map<String, Object> mapa_contenido,
			String separador) {
		try {
			ConvertidorXmlToMap.convertirToXML(mapa_contenido);
			StringBuffer stringBuffer = new StringBuffer();
			for (Object key : mapa_contenido.keySet()) {
				Object valor = mapa_contenido.get(key);
				if (key.equals(valor)) {
					stringBuffer.append(key).append(separador);
				} else {
					if (!valor.toString().isEmpty()) {
						stringBuffer.append(key).append("=").append(valor)
								.append(separador);
					}
				}
			}
			return stringBuffer.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public static String setValoresMedicamentosAnteriores(String valores,
			int fila) {
		StringTokenizer stringTokenizer = new StringTokenizer(valores, "\n");
		List<String> listado_valores = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {
			listado_valores.add(stringTokenizer.nextToken());
		}
		if (fila < listado_valores.size()) {
			return listado_valores.get(fila);
		}
		return "";
	}

	public static void ajustarCuadrosTexto(Textbox textbox) {

		StringTokenizer stringTokenizer = new StringTokenizer(textbox
				.getValue().trim(), "\n");
		int i = stringTokenizer.countTokens();

		// int i = Integer.parseInt(s);
		textbox.setRows(i);
		// log.info("--" + i);
	}

	// Opciones de niveles
	public static void quitarSeleccion(Listbox listbox) {
		List<Listitem> listitems = listbox.getItems();
		for (Listitem listitem : listitems) {
			listitem.setSelected(false);
		}
	}

	public static void setNivelesExcepcion(String excepcion_nivel,
			Listbox listbox) {
		quitarSeleccion(listbox);
		if (excepcion_nivel != null) {
			List<Listitem> listitems = listbox.getItems();
			for (Listitem listitem : listitems) {
				if (excepcion_nivel.contains("["
						+ listitem.getValue().toString() + "]")) {
					listitem.setSelected(true);
				}
			}
		}
	}

	public static String getNivelesExcepcion(Listbox listbox) {
		Set<Listitem> listitems = listbox.getSelectedItems();
		String excepcion_nivel = "";
		if (!listitems.isEmpty()) {
			for (Listitem listitem : listitems) {
				excepcion_nivel += "[" + listitem.getValue() + "]";
			}
		}
		return excepcion_nivel;
	}

	public static void aplicarOnOk(HtmlBasedComponent component,
			final HtmlBasedComponent component2) {
		component.addEventListener(Events.ON_OK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				component2.setFocus(true);
			}
		});
	}

	public static void aplicarIndicador(Checkbox... checkbox) {
		for (Checkbox checkboxTemp : checkbox) {
			if (checkboxTemp.isChecked()) {
				checkboxTemp.setStyle("color:red;");
			} else {
				checkboxTemp.setStyle("color:black;");
			}
		}
	}

	public static void validacionFechasFuturas(Datebox... dtbxFechas) {
		validacionFechas(true, "before", dtbxFechas);
	}

	public static void validacionFechasFuturas(boolean actualizar_fecha,
			Datebox... dtbxFechas) {
		validacionFechas(actualizar_fecha, "before", dtbxFechas);
	}

	public static void validacionFechasPasadas(Datebox... dtbxFechas) {
		validacionFechas(true, "after", dtbxFechas);
	}

	public static void validacionFechasPasadas(boolean actualizar_fecha,
			Datebox... dtbxFechas) {
		validacionFechas(actualizar_fecha, "after", dtbxFechas);
	}

	private static void validacionFechas(boolean actualizar_fecha,
			String comando, Datebox... dtbxFechas) {
		if (dtbxFechas != null) {
			java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat(
					"yyyyMMdd");
			Date fecha = new Date();
			java.lang.String ff = formato.format(fecha);
			for (Datebox datebox : dtbxFechas) {
				datebox.setConstraint(comando + " " + ff);
				// en algunos partes no es necesario cambiar la fecha
				if (actualizar_fecha) {
					datebox.setValue(fecha);
				}
			}
		}
	}

	/**
	 * Este metodo nos permite obtener los valores de la listbox en forma de
	 * patron
	 *
	 * @author Luis Miguel
	 *
	 */
	public static String getPatronString(Set<Listitem> selectedItems) {
		StringBuilder patron = new StringBuilder("");
		for (Listitem listitem : selectedItems) {
			patron.append("[" + listitem.getValue() + "]");
		}
		return patron.toString();
	}

	/**
	 * Este metodo me permite visualizar el patrin en el listbox
	 *
	 * @author Luis Miguel
	 *
	 */
	public static void setValueDesdePatronString(Listbox listbox, String patron) {
		if (patron != null && !patron.trim().isEmpty()
				&& patron.matches("[\\[][\\[0-9\\]]*[\\]]$")) {
			quitarSeleccion(listbox);
			for (Listitem listitem : listbox.getItems()) {
				listitem.setSelected(patron.contains("[" + listitem.getValue()
						+ "]"));
			}
		} else {
			// log.info("patron no valido: " + patron);
		}
	}

	public static <T> void setValueDesde(Listbox listbox, List<T> list,
			String campo_comparacion) {
		quitarSeleccion(listbox);
		List<Listitem> listitems = listbox.getItems();
		for (T bean : list) {
			try {
				Field field = bean.getClass().getDeclaredField(
						campo_comparacion);
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				Object objectValue = field.get(bean);
				if (objectValue != null) {
					for (Listitem listitem : listitems) {
						if (listitem.getValue().equals(objectValue)) {
							listitem.setSelected(true);
						}
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public static void inicializarAnio(Listbox lbxAnios) {
		Calendar fecha_sistema = Calendar.getInstance();
		int anio_sistema = fecha_sistema.get(Calendar.YEAR);

		for (int i = (anio_sistema - 5); i < (anio_sistema + 5); i++) {
			Listitem listitem = new Listitem();
			listitem.setValue(i);
			listitem.setLabel("" + i);
			if (i == anio_sistema) {
				listitem.setSelected(true);
			}
			lbxAnios.appendChild(listitem);
		}
	}

	public static void listarMeses(Listbox listboxMeses) {
		listboxMeses.getItems().clear();
		Listitem listitem = new Listitem();
		listitem.setValue(0);
		listitem.setLabel("Enero");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(1);
		listitem.setLabel("Febrero");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(2);
		listitem.setLabel("Marzo");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(3);
		listitem.setLabel("Abril");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(4);
		listitem.setLabel("Mayo");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(5);
		listitem.setLabel("Junio");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(6);
		listitem.setLabel("Julio");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(7);
		listitem.setLabel("Agosto");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(8);
		listitem.setLabel("Septiembre");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(9);
		listitem.setLabel("Octubre");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(10);
		listitem.setLabel("Noviembre");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(11);
		listitem.setLabel("Diciembre");
		listboxMeses.appendChild(listitem);
		listboxMeses.setSelectedIndex(Calendar.getInstance()
				.get(Calendar.MONTH));
	}

	public static List<String> convertirALista(String... valores) {
		List<String> listado = new ArrayList<String>();
		if (valores != null) {
			for (String valor : valores) {
				listado.add(valor);
			}
		}
		return listado;
	}

	public static void listarElementoListbox(Listbox listbox, boolean select,
			ServiceLocatorWeb serviceLocator) {
		listarElementoListbox(listbox, select,
				serviceLocator.getElementoService(), true);
	}

	public static void listarElementoListbox(Listbox listbox, boolean select,
			ElementoService elementoService) {
		listarElementoListbox(listbox, select, elementoService, true);
	}

	public static void listarElementoListbox(Listbox listbox, boolean select,
			ElementoService elementoService, boolean seleccionarIndice) {
		listbox.getItems().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = elementoService.listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (seleccionarIndice && listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarElementoListbox(Listbox listbox, boolean select,
			ServiceLocatorWeb serviceLocatorWeb, boolean seleccionarIndice) {
		listbox.getItems().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = serviceLocatorWeb.getElementoService()
				.listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (seleccionarIndice && listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarElementoListboxOrdenado(Listbox listbox,
			boolean select, ServiceLocatorWeb serviceLocator) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo);
		parametros.put("orden_descripcion", "orden_descripcion");

		List<Elemento> elementos = serviceLocator.getElementoService().listar(
				parametros);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarElementoListboxOrdenado(Listbox listbox,
			boolean select, ElementoService elementoService) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo);
		parametros.put("orden_descripcion", "orden_descripcion");

		List<Elemento> elementos = elementoService.listar(parametros);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public static void listarElementoListboxFromType(Listbox listbox,
			boolean select, List<Elemento> elementos, boolean selectEnd) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			if (!selectEnd) {
				listbox.setSelectedIndex(0);
			} else {
				listbox.setSelectedIndex(listbox.getChildren().size() - 1);
			}
		}
	}

	public static void listarElementoListboxFromType(Listbox listbox,
			boolean select, List<Elemento> elementos, boolean selectEnd,
			boolean selectItem) {
		if (selectItem) {
			listbox.getChildren().clear();
		}
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			if (!selectEnd) {
				listbox.setSelectedIndex(0);
			} else {
				listbox.setSelectedIndex(listbox.getChildren().size() - 1);
			}
		}
	}

	public static String getValorListBox(Listbox listbox) {
		if (listbox.getSelectedItem() != null
				&& listbox.getSelectedItem().getValue() != null) {
			return listbox.getSelectedItem().getValue().toString();
		} else {
			return "";
		}
	}

	public static String getNumeroFactura(String codigo_documento, int longitud) {
		if (codigo_documento != null) {
			if (codigo_documento.matches("[0-9]*$")) {
				int numero_enterio = Integer.valueOf(codigo_documento);
				if ((numero_enterio + "").length() >= longitud) {
					return numero_enterio + "";
				} else {
					return Utilidades
							.getZeroFill(numero_enterio + "", longitud);
				}
			} else {
				return codigo_documento;
			}
		}
		return "";
	}

	public static Long getValorLong(String valor) {
		if (valor != null) {
			if (valor.isEmpty()) {
				return null;
			} else {
				return new Long(valor);
			}
		} else {
			return null;
		}
	}

	public static String getTipoFacturacion(String tipo) {
		if (tipo.equalsIgnoreCase(IConstantes.TIPO_FACTURA_CAP_INTERNA)) {
			return "Capitada";
		} else if (tipo.equalsIgnoreCase(IConstantes.TIPO_FACTURA_EVENTO_IND)) {
			return "Evento";
		} else if (tipo.equalsIgnoreCase(IConstantes.TIPO_FACTURA_PORTABILIDAD)) {
			return "Portabilidad";
		} else {
			return tipo;
		}
	}

	public static void onBuscarCentros(Bandbox bandboxBuscar_centros,
			Listbox lbxCentros_atencion) {
		String valor = bandboxBuscar_centros.getValue().trim().toUpperCase();
		if (!valor.isEmpty()) {
			List<Listitem> listado = lbxCentros_atencion.getItems();
			for (Listitem listitem : listado) {
				Centro_atencion centro_atencion = (Centro_atencion) listitem
						.getValue();
				if (centro_atencion.getNombre_centro().toUpperCase()
						.contains(valor)
						|| valor.equals(centro_atencion.getCodigo_centro())) {
					Clients.scrollIntoView(listitem);
					MensajesUtil.notificarInformacion("Resultado encontrado",
							listitem);
					break;
				}
			}
		}
	}

	public static boolean tieneHistoriasManuales(Map<String, Object> parametros,
			GeneralExtraService generalExtraService) {
		return generalExtraService.existe(Reg_historias_manuales.class,
				parametros);
	}

}
