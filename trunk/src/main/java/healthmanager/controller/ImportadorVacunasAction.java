package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.ImportadorVacunaItem;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.bean.Vacunas_pacientes;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.Esquema_vacunacionService;
import healthmanager.modelo.service.Manuales_tarifariosService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.UsuariosService;
import healthmanager.modelo.service.VacunasService;
import healthmanager.modelo.service.Vacunas_pacientesService;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class ImportadorVacunasAction extends ZKWindow {

	private Media file;

	@View
	private Button buttonEliminar;
	@View
	private Button buttonAdjuntar;
	@View
	private Label labelArchivo_adjunto;
	@View
	private Button buttonImportar;
	@View
	private Vlayout vlayoutResultados;
	@View
	private org.zkoss.zul.Row rowResultado;
	@View
	private org.zkoss.zul.Row rowResultadoBotones;
	@View
	private Listbox lbxOpcionesReporte;

	public static final String PACIENTE_VACUNADO = "cent_s";
	public static final String MANUAL_TARIFARIO = "manu_taf";

	public static final String[] BCG_DOSIS = { "993102", "48" };
	public static final String[] HEPATITISB_DOSIS = { "993503", "51" };
	public static final String[] VPO_DOSIS = { "993501", "54" };
	public static final String[] VPI_DOSIS = { "993501", "56" };
	public static final String[] PENTAVALENTE_DOSIS = { "993131", "59" };
	public static final String[] DPT_DOSIS = { "993122", "62" };
	public static final String[] ROTAVIRUS_DOSIS = { "993512", "65" };
	public static final String[] NEUMOCOCO_DOSIS = { "993106", "67" };
	public static final String[] TRIPLE_VIRAL_DOSIS = { "993522", "70" };
	public static final String[] HEPATITISA_DOSIS = { "993502", "73" };
	public static final String[] ANTIAMARILICA_DOSIS = { "993504", "76" };
	public static final String[] INFLUENZA_DOSIS = { "993104", "82" };
	public static final String[] SARAMPION_RUBEOLA_DOSIS = { "993520", "88" };
	public static final String[] VPH_DOSIS = { "C00011", "91" };
	public static final String[] ANTIRABICA_DOSIS = { "993505", "94" };
	public static final String[] TOXOIDE_TETANICO_DIFTERICO_DOSIS = { "993120",
			"79" };
	public static final String[] TD_DOSIS = { "993120", "85" };

	public static final Integer ID_CELL = 4;
	public static final Integer TIPO_ID_CELL = 3;
	public static final Integer FECHA_CELL = 2;
	public static final Integer ADMINISTRADORA_CELL = 25;
	public static final Integer INDICE_CELL = 1;
	public static final Integer VACUNADOR_CELL = 125;
	public static final Integer NOMBRE1_CELL = 19;
	public static final Integer NOMBRE2_CELL = 20;
	public static final Integer APELLIDO1_CELL = 22;
	public static final Integer APELLIDO2_CELL = 23;

	private String[][] vacunas = { BCG_DOSIS, HEPATITISB_DOSIS, VPO_DOSIS,
			VPI_DOSIS, PENTAVALENTE_DOSIS, DPT_DOSIS, ROTAVIRUS_DOSIS,
			NEUMOCOCO_DOSIS, TRIPLE_VIRAL_DOSIS, HEPATITISA_DOSIS,
			ANTIAMARILICA_DOSIS, INFLUENZA_DOSIS, SARAMPION_RUBEOLA_DOSIS,
			VPH_DOSIS, ANTIRABICA_DOSIS, TOXOIDE_TETANICO_DIFTERICO_DOSIS,
			TD_DOSIS };

	int importados = 0;
	ArrayList<Paciente> no_existen = new ArrayList<Paciente>();
	ArrayList<String> administradora_no_existe = new ArrayList<String>();
	ArrayList<Paciente> sin_contratos = new ArrayList<Paciente>();
	ArrayList<Paciente> validos = new ArrayList<Paciente>();
	ArrayList<Paciente> administradoras_diferentes = new ArrayList<Paciente>();
	ArrayList<Paciente> pacientes_dosis_invalida = new ArrayList<Paciente>();

	@Override
	public void init() throws Exception {
		rowResultadoBotones.setVisible(false);
		Utilidades.listarElementoListbox(lbxOpcionesReporte, true,
				getServiceLocator());
	}

	public void adjuntarArchivo(Media media) throws Exception {
		try {

			if (!(media.getFormat().endsWith("xls") || media.getFormat()
					.endsWith("xlsx"))) {
				throw new Exception(
						"El importador de pacientes solo soporta formatos xls o xlsx");
			}

			if (file != null) {
				throw new Exception("El archivo ya fue cargado");
			}

			buttonAdjuntar.setVisible(false);
			buttonEliminar.setVisible(true);
			buttonImportar.setDisabled(false);

			labelArchivo_adjunto.setValue(media.getName());

			file = media;

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarArchivo() {
		labelArchivo_adjunto.setValue("");
		buttonAdjuntar.setVisible(true);
		buttonEliminar.setVisible(false);
		buttonImportar.setDisabled(true);
		rowResultado.setVisible(false);
		rowResultadoBotones.setVisible(false);
		file = null;
	}

	public void importarDatos() throws Exception {
		try {

			vlayoutResultados.getChildren().clear();
			if (file == null) {
				throw new Exception(
						"Debe cargar un archivo de excel para la importacion");
			}

			String formato = file.getFormat();

			Workbook workbook;
			if (formato.endsWith("xls")) {
				workbook = new HSSFWorkbook(file.getStreamData());
			} else {
				workbook = new XSSFWorkbook(file.getStreamData());
			}

			Integer iniciof = 16;
			Integer inicioc = 1;

			Sheet sheet = workbook.getSheetAt(0);
			Row fila = sheet.getRow(inicioc);

			no_existen = new ArrayList<Paciente>();
			administradora_no_existe = new ArrayList<String>();
			sin_contratos = new ArrayList<Paciente>();
			validos = new ArrayList<Paciente>();
			administradoras_diferentes = new ArrayList<Paciente>();
			pacientes_dosis_invalida = new ArrayList<Paciente>();

			Date tiempo_inicio = new Date();
			Date tiempo_fin;
			importados = 0;

			for (int i = iniciof; i < sheet.getLastRowNum() + 1; i++) {
				// //log.info(">>>>>>Procesando la linea "+getValorCeldaTexto(fila.getCell(INDICE_CELL)));

				fila = sheet.getRow(i);
				if (fila.getCell(ID_CELL) != null) {
					String id = getValorCeldaTexto(fila.getCell(ID_CELL));
					String tipo_id = getValorCeldaTexto(fila
							.getCell(TIPO_ID_CELL));
					String nombre1 = getValorCeldaTexto(fila
							.getCell(NOMBRE1_CELL));
					String nombre2 = getValorCeldaTexto(fila
							.getCell(NOMBRE2_CELL));
					String apellido1 = getValorCeldaTexto(fila
							.getCell(APELLIDO1_CELL));
					String apellido2 = getValorCeldaTexto(fila
							.getCell(APELLIDO2_CELL));
					String codigo_administradora = getValorCeldaTexto(fila
							.getCell(ADMINISTRADORA_CELL));
					String codigo_vacunador = getValorCeldaTexto(fila
							.getCell(VACUNADOR_CELL));

					tipo_id = parseTipoId(tipo_id);

					if (id != null && !id.isEmpty() && tipo_id != null
							&& !tipo_id.isEmpty()
							&& codigo_administradora != null
							&& !codigo_administradora.isEmpty()) {
						Date fecha = new SimpleDateFormat("dd/MM/yyyy")
								.parse(getValorCeldaTexto(fila
										.getCell(FECHA_CELL)));
						Paciente paciente = getPaciente(id, tipo_id);

						if (paciente == null) {
							Paciente p = new Paciente();
							p.setNombre1(nombre1);
							p.setNombre2(nombre2);
							p.setApellido1(apellido1);
							p.setApellido2(apellido2);
							p.setNro_identificacion(id);
							p.setTipo_identificacion(tipo_id);
							no_existen.add(p);
							// //log.info(">>>>>>>Paciente no existe>: "+id+ "["+
							// tipo_id +"]");
						} else {
							// Administradora administradora =
							// cargarAdministradora(codigo_administradora);
							Administradora administradora = cargarAdministradora(paciente
									.getCodigo_administradora());
							if (administradora == null) {
								administradora_no_existe
										.add(codigo_administradora);
								// //log.info(">>>>>>>>Administrdadora no existe>: "+codigo_administradora);
							} else {
								if (!paciente.getCodigo_administradora()
										.equalsIgnoreCase(
												administradora.getCodigo())) {
									administradoras_diferentes.add(paciente);
									// //log.info(">>>>>>>>Administradoras diferentes>: "+paciente.getNro_identificacion()+
									// "["+ paciente.getTipo_identificacion()
									// +"]");
								} else {
									Manuales_tarifarios manuales_tarifarios = getManuales_tarifarios(paciente);
									if (manuales_tarifarios == null) {
										sin_contratos.add(paciente);
										// //log.info(">>>>>>>>Sin contrato vacunacion>: "+paciente.getNro_identificacion()+
										// "["+
										// paciente.getTipo_identificacion()
										// +"]");
									} else {
										if (!dosisValidas(fila, vacunas)) {
											pacientes_dosis_invalida
													.add(paciente);
											//log.info(">>>>>>>>Dosis inválida (Error en esquema)>: "
													//+ getValorCeldaTexto(fila
															//.getCell(INDICE_CELL)));
										} else {
											List<Esquema_vacunacion> esquemas = getListaEsquema(fila);
											esquemas = validarPentavalente(
													esquemas, fila);
											if (esquemas.size() == 0) {
												pacientes_dosis_invalida
														.add(paciente);
												//log.info(">>>>>>>>Dosis inválida (Esquema vacío)>: "
														//+ getValorCeldaTexto(fila
																//.getCell(INDICE_CELL)));
											} else {
												Usuarios vacunador = cargarUsuario(codigo_vacunador);
												ImportadorVacunaItem item = new ImportadorVacunaItem(
														fecha, paciente,
														esquemas, vacunador,
														administradora);
												guardarItemImportador(item);
												validos.add(paciente);
												// //log.info(">>>>>>>Paciente válido>: "+paciente.getNro_identificacion()+
												// "["+
												// paciente.getTipo_identificacion()
												// +"] => ("+esquemas.size()+")");
											}
										}
									}

								}
							}
						}
					}
				}
			}
			tiempo_fin = new Date();

			Long res = tiempo_fin.getTime() - tiempo_inicio.getTime();
			Long diffs = res / 1000 % 60;
			Long diffm = res / (60 * 1000) % 60;
			Long diffh = res / (60 * 60 * 1000) % 24;
			DecimalFormat df = new DecimalFormat("00");
			String tiempo = String.format("%s:%s:%s", df.format(diffh),
					df.format(diffm), df.format(diffs));

			//log.info(">>><No existe paciente> " + no_existen.size());
			//log.info(">>><No existe administradora> "
					//+ administradora_no_existe.size());
			//log.info(">>><Existen sin contrato> " + sin_contratos.size());
			//log.info(">>><Paciente válido> " + validos.size());
			//log.info(">>><Pacientes con dosis inválida> "
					//+ pacientes_dosis_invalida.size());
			//log.info(">>><Total> "
					/*+ (no_existen.size() + administradora_no_existe.size()
							+ sin_contratos.size() + validos.size() + pacientes_dosis_invalida
								.size()));*/

			rowResultadoBotones.setVisible(true);

			if (importados > 0) {
				mensaje("Importacion correcta, se importaron " + importados
						+ " registros en " + tiempo, 1);
			} else {
				mensaje("ADVERTENCIA: No se importaron registros, posiblemente ya se encuentran registrados ("
						+ tiempo + ")", 3);
			}

		} catch (Exception e) {
			mensaje(e.getMessage(), 2);
			e.printStackTrace();
		}
	}

	private Administradora cargarAdministradora(String codigo) {
		Administradora administradora = new Administradora();
		administradora.setCodigo(codigo);
		administradora.setCodigo_empresa(codigo_empresa);
		administradora.setCodigo_sucursal(codigo_sucursal);
		administradora = getServiceLocator().getServicio(
				AdministradoraService.class).consultar(administradora);
		return administradora;
	}

	private Usuarios cargarUsuario(String codigo) {
		Usuarios usuario = new Usuarios();
		usuario.setCodigo(codigo);
		usuario.setCodigo_empresa(empresa.getCodigo_empresa());
		usuario.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		usuario = getServiceLocator().getServicio(UsuariosService.class)
				.consultar(usuario);
		return usuario;
	}

	private void guardarItemImportador(ImportadorVacunaItem item)
			throws Exception {
		if (item != null && item.getEsquemas() != null
				&& !item.getEsquemas().isEmpty()) {

			guardarVacunas(new Timestamp(item.getFecha().getTime()),
					item.getPaciente(), usuarios, item.getVacunador(),
					item.getEsquemas(), item.getAdministradora());

			/*
			 * for (Esquema_vacunacion esquema : item.getEsquemas()) { Vacunas v
			 * = getVacuna(esquema.getCodigo_vacuna()); guardarVacuna(new
			 * Timestamp(item.getFecha().getTime()),item.getPaciente(),
			 * usuarios,item.getVacunador(),
			 * v,esquema,item.getAdministradora()); }
			 */
		}
	}

	private String parseTipoId(String tipo_id) {
		if (tipo_id != null) {
			tipo_id = tipo_id.trim();
			if (tipo_id.equalsIgnoreCase("Cédula de ciudadanía")) {
				return "CC";
			} else if (tipo_id.contains("Adulto sin identificacion")) {
				return "AS";
			} else if (tipo_id.contains("Cédula de extranjería")) {
				return "CE";
			} else if (tipo_id.contains("Certificado de Nacido Vivo")) {
				return null;
			} else if (tipo_id.contains("Menor sin identificacion")) {
				return "MS";
			} else if (tipo_id.contains("Pasaporte")) {
				return "PA";
			} else if (tipo_id.contains("Registro Civil")) {
				return "RC";
			} else if (tipo_id.contains("Tarjeta de identidad")) {
				return "TI";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private Paciente getPaciente(String id, String tipo_id) {
		Paciente paciente = new Paciente();
		paciente.setNro_identificacion(id.toUpperCase());
		if (tipo_id != null) {
			paciente.setTipo_identificacion(tipo_id.trim());
		}
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente = getServiceLocator().getServicio(PacienteService.class)
				.consultar(paciente);
		return paciente;
	}

	private Manuales_tarifarios getManuales_tarifarios(Paciente paciente) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", paciente.getCodigo_empresa());
		map.put("codigo_sucursal", paciente.getCodigo_sucursal());
		map.put("codigo_administradora", paciente.getCodigo_administradora());
		map.put("nro_identificacion", paciente.getNro_identificacion());
		map.put("codigo_servicio",
				ServiciosDisponiblesUtils.CODSER_PYP_VACUNACION_PAI);
		return getServiceLocator()
				.getServicio(Manuales_tarifariosService.class)
				.consultar_parametrizado(map);
	}

	private Map<String, Object> cargarPaciente(Paciente paciente,
			Manuales_tarifarios manuales_tarifarios) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(PACIENTE_VACUNADO, paciente);
		bean.put(MANUAL_TARIFARIO, manuales_tarifarios);
		return bean;
	}

	private List<Esquema_vacunacion> validarPentavalente(
			List<Esquema_vacunacion> esquemas, Row fila) {

		Map<String, Esquema_vacunacion> map = new HashMap<String, Esquema_vacunacion>();
		for (Esquema_vacunacion esquema : esquemas) {
			map.put(esquema.getCodigo_vacuna(), esquema);
		}

		if (!map.isEmpty() && map.get(PENTAVALENTE_DOSIS[0]) != null) {
			map.remove(HEPATITISB_DOSIS[0]);
			map.remove(DPT_DOSIS[0]);
			map.remove(INFLUENZA_DOSIS[0]);
		}

		esquemas = new ArrayList<Esquema_vacunacion>(map.values());
		return esquemas;
	}

	private Esquema_vacunacion getEsquemaPorFila(String[] vacuna, Row fila) {
		Integer indice = Integer.valueOf(vacuna[1]);
		String d = getValorCeldaTexto(fila.getCell(indice)).toString();
		return getEsquema(vacuna[0], d);
	}

	private Boolean dosisValidas(Row fila, String[][] vacunas) {
		for (int i = 0; i < vacunas.length; i++) {
			String dosis = getValorCeldaTexto(fila.getCell(Integer
					.valueOf(vacunas[i][1])));
			if (!dosisValida(dosis)) {
				return false;
			}
		}
		return true;
	}

	private Boolean dosisValida(String dosis) {
		return (dosis.matches("R?\\d*"));
	}

	private Integer homologarDosis(String dosis, String cup) {
		if (cup.equalsIgnoreCase(TRIPLE_VIRAL_DOSIS[0])
				|| cup.equalsIgnoreCase(ANTIAMARILICA_DOSIS[0])
				|| cup.equalsIgnoreCase(HEPATITISA_DOSIS[0])) {
			// correccion de dosis unica
			return 1;
		} else if (cup.equalsIgnoreCase(DPT_DOSIS[0])) {
			if (dosis.equalsIgnoreCase("R1")) {
				return 1;
			} else if (dosis.equalsIgnoreCase("R2")) {
				return 2;
			}
		} else if (cup.equalsIgnoreCase(TOXOIDE_TETANICO_DIFTERICO_DOSIS[0])
				|| cup.equalsIgnoreCase(TD_DOSIS[0])) {
			if (dosis.equalsIgnoreCase("R1")) {
				return 6;
			}
		}

		if (dosis.equalsIgnoreCase("R1")) {
			return 4;
		} else if (dosis.equalsIgnoreCase("R2")) {
			return 5;
		} else {

			Integer d = Integer.valueOf(dosis);
			// si viene un valor errado se toma como si fuera la primera dosis
			if (d < 1 || d > 3) {
				d = 1;
			}
			return d;
		}
	}

	public List<Esquema_vacunacion> getListaEsquema(Row fila) {
		List<Esquema_vacunacion> esquemas = new ArrayList<Esquema_vacunacion>();
		for (String[] dosis : vacunas) {
			Esquema_vacunacion esquema = getEsquemaPorFila(dosis, fila);
			if (esquema != null) {
				esquemas.add(esquema);
			}
		}
		return esquemas;
	}

	public Esquema_vacunacion getEsquema(String cup, String dosis) {
		Esquema_vacunacion esquema = null;
		if (cup != null && dosis != null && !cup.isEmpty() && !dosis.isEmpty()) {
			esquema = new Esquema_vacunacion();
			esquema.setCodigo_empresa(codigo_empresa);
			esquema.setCodigo_sucursal(codigo_sucursal);
			esquema.setCodigo_vacuna(cup);

			if (cup == NEUMOCOCO_DOSIS[0]) {
				//log.info(">>>>>>>>>>>>" + dosis);
			}

			if (cup != INFLUENZA_DOSIS[0]) {
				esquema.setConsecutivo(homologarDosis(dosis, cup));
			} else {
				esquema.setConsecutivo(1);
			}
			esquema = getServiceLocator().getServicio(
					Esquema_vacunacionService.class).consultar(esquema);
		}
		return esquema;

	}

	public Vacunas getVacuna(String cup) {
		Vacunas v = new Vacunas();
		v.setCodigo_empresa(empresa.getCodigo_empresa());
		v.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		v.setCodigo_procedimiento(cup);
		v = getServiceLocator().getServicio(VacunasService.class).consultar(v);
		return v;
	}

	public Vacunas_pacientes getVacunaPaciente(Esquema_vacunacion esquema,
			Paciente paciente) {
		Vacunas_pacientes vp = new Vacunas_pacientes();
		vp.setCodigo_empresa(codigo_empresa);
		vp.setCodigo_sucursal(codigo_sucursal);
		vp.setCodigo_procedimiento_vacuna(esquema.getCodigo_vacuna());
		vp.setNro_identificacion(paciente.getNro_identificacion());
		vp.setId_esquema_vacunacion(esquema.getConsecutivo());
		return getServiceLocator().getServicio(Vacunas_pacientesService.class)
				.consultar(vp);
	}

	public void guardarVacunas(Timestamp fecha, Paciente paciente,
			Usuarios usuario, Usuarios vacunador,
			List<Esquema_vacunacion> esquemas, Administradora administradora)
			throws Exception {

		try {

			List<Map<String, Object>> listado_pacientes = new ArrayList<Map<String, Object>>();
			Manuales_tarifarios manual = getManuales_tarifarios(paciente);
			listado_pacientes.add(cargarPaciente(paciente, manual));
			List<Map<String, Object>> listado_vacunas = new ArrayList<Map<String, Object>>();

			for (Esquema_vacunacion esquema : esquemas) {
				Map<String, Object> mapa_vacunas = new HashMap<String, Object>();
				mapa_vacunas.put(VacunasPaiAction.VACUNAS,
						getVacuna(esquema.getCodigo_vacuna()));
				mapa_vacunas.put(VacunasPaiAction.ESQUEMA_VACUNACION, esquema);
				listado_vacunas.add(mapa_vacunas);
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pacientes", listado_pacientes);
			params.put("vacunas", listado_vacunas);
			params.put("usuario", usuario);
			params.put("vacunador", vacunador);
			params.put("fecha_vacunacion", fecha);
			params.put("sucursal", sucursal);
			params.put("empresa", empresa);
			params.put("administradora", administradora);
			params.put("serviceLocator", getServiceLocator());
			params.put("centro_atencion_session", centro_atencion_session);

			getServiceLocator().getServicio(VacunasService.class).guardar(
					params);
			importados++;
			// Notificaciones.mostrarNotificacionInformacion("Informacion ..","Los datos se guardaron satisfactoriamente",
			// 3000);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void guardarVacuna(Timestamp fecha, Paciente paciente,
			Usuarios usuario, Usuarios vacunador, Vacunas vacuna,
			Esquema_vacunacion esquema, Administradora administradora)
			throws Exception {

		try {

			if (getVacunaPaciente(esquema, paciente) == null) {

				List<Map<String, Object>> listado_pacientes = new ArrayList<Map<String, Object>>();
				Manuales_tarifarios manual = getManuales_tarifarios(paciente);
				listado_pacientes.add(cargarPaciente(paciente, manual));
				List<Map<String, Object>> listado_vacunas = new ArrayList<Map<String, Object>>();
				Map<String, Object> mapa_vacunas = new HashMap<String, Object>();
				mapa_vacunas.put(VacunasPaiAction.VACUNAS, vacuna);
				mapa_vacunas.put(VacunasPaiAction.ESQUEMA_VACUNACION, esquema);
				listado_vacunas.add(mapa_vacunas);

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("pacientes", listado_pacientes);
				params.put("vacunas", listado_vacunas);
				params.put("usuario", usuario);
				params.put("vacunador", vacunador);
				params.put("fecha_vacunacion", fecha);
				params.put("sucursal", sucursal);
				params.put("empresa", empresa);
				params.put("administradora", administradora);
				params.put("serviceLocator", getServiceLocator());
				params.put("centro_atencion_session", centro_atencion_session);

				getServiceLocator().getServicio(VacunasService.class).guardar(
						params);
				importados++;
				// Notificaciones.mostrarNotificacionInformacion("Informacion ..","Los datos se guardaron satisfactoriamente",
				// 3000);
			} else {
				//log.info("La vacuna para este paciente ya se encontraba registrada!!!");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private String getValorCeldaTexto(Cell celda) {
		String valor = "";
		switch (celda.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			valor = celda.getStringCellValue();
			break;

		case Cell.CELL_TYPE_FORMULA:
			valor = celda.getCellFormula();
			break;

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(celda)) {
				valor = celda.getDateCellValue().toString();
			} else {
				double valor_double = celda.getNumericCellValue();
				DecimalFormat formato = new DecimalFormat("#");
				valor = formato.format(valor_double);
			}
			break;

		case Cell.CELL_TYPE_BLANK:
			valor = "";
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			valor = Boolean.toString(celda.getBooleanCellValue());
			break;

		}
		return valor;
	}

	public void mensaje(String mensaje, int tipo) {
		String color = "";
		if (tipo == 1) {
			color = "blue";
		} else if (tipo == 2) {
			color = "red";
		} else if (tipo == 3) {
			color = "orange";
		}
		Hlayout hlayout = new Hlayout();
		Textbox textbox = new Textbox(mensaje);
		textbox.setWidth("700px");
		textbox.setRows(6);
		textbox.setReadonly(true);
		textbox.setStyle("background-color:transparent;color:red;border-color:transparent");
		hlayout.setStyle("color:" + color);

		// Label label = new Label(mensaje);
		if (tipo == 1 || tipo == 3) {
			Html html = new Html("<p>" + mensaje + "</p>");
			hlayout.appendChild(html);
		} else {
			hlayout.appendChild(textbox);
		}
		rowResultado.setVisible(true);
		vlayoutResultados.appendChild(hlayout);

	}

	public void imprimir() {
		String opcion = lbxOpcionesReporte.getSelectedItem().getValue();
		if (opcion.equals("0")) {
			imprimirPacientes(no_existen,
					"PACIENTES QUE NO APARECEN EN LA BASE DE DATOS");
		} else if (opcion.equals("1")) {
			// imprimirPacientes(no_existen,
			// "PACIENTES QUE NO TIENEN UNA ADMINISTRADORA válidA");
		} else if (opcion.equals("2")) {
			imprimirPacientes(sin_contratos,
					"PACIENTES QUE NO TIENEN CONTRATO DE VACUNAcion");
		} else if (opcion.equals("3")) {
			imprimirPacientes(pacientes_dosis_invalida,
					"PACIENTES CON DOSIS INválidA");
		} else if (opcion.equals("4")) {
			imprimirPacientes(administradoras_diferentes,
					"LAS ADMINISTRADORAS NO COINCIDEN (XLS-BD)");
		} else if (opcion.equals("5")) {
			imprimirPacientes(validos, "PACIENTES IMPORTADOS");
		}
	}

	public void imprimirPacientes(List<Paciente> pacientes,
			String nombre_reporte) {
		if (pacientes != null && !pacientes.isEmpty()) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nombre_reporte", nombre_reporte.toUpperCase());
			parametros.put("formato", "pdf");
			parametros.put("listado_pacientes", pacientes);
			parametros.put("name_report", "Listado_pacientes");

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, parametros);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			Messagebox.show("No existen datos para imprimir este reporte",
					"Sin datos", Messagebox.OK, Messagebox.INFORMATION);
		}
	}
}
