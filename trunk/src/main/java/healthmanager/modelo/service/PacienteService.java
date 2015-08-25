/*
 * PacienteServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */
package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.dao.AdministradoraDao;
import healthmanager.modelo.dao.DepartamentosDao;
import healthmanager.modelo.dao.MunicipiosDao;
import healthmanager.modelo.dao.PacienteDao;
import healthmanager.modelo.dao.Pacientes_contratosDao;

import java.io.Reader;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.csvreader.CsvReader;
import com.framework.constantes.IConstantes;

import contaweb.modelo.bean.Tercero;
import contaweb.modelo.bean.Tipo_tercero;
import contaweb.modelo.dao.TerceroDao;
import contaweb.modelo.dao.Tipo_terceroDao;

@Service("pacienteService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PacienteService implements Serializable {

	private static Logger log = Logger.getLogger(PacienteService.class);

	@Autowired
	private PacienteDao pacienteDao;
	@Autowired
	private TerceroDao terceroDao;
	@Autowired
	private Tipo_terceroDao tipo_terceroDao;

	/* contrataciones */
	@Autowired
	private AdministradoraDao administradoraDao;
	@Autowired
	private ElementoService elementoService;
	@Autowired
	private DepartamentosDao departamentosDao;
	@Autowired
	private MunicipiosDao municipiosDao;
	@Autowired
	private GeneralExtraService generalExtraService;
	@Autowired
	private AdmisionService admisionService;
	@Autowired
	private Pacientes_contratosDao pacientes_contratosDao;

	// private static final String CODIGO_DADIS_ANTERIOR = "13002";
	// private static final String CODIGO_DADIS_NUEVO = "DOA13";
	@SuppressWarnings("unchecked")
	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			Paciente paciente = (Paciente) mapa_datos.get("paciente");
			Admision admision_rn = (Admision) mapa_datos.get("admision_rn");
			String accion = (String) mapa_datos.get("accion");
			Adicional_paciente adicional_paciente = (Adicional_paciente) mapa_datos
					.get("adicional_paciente");
			Parametros_empresa parametros_empresa = (Parametros_empresa) mapa_datos
					.get("parametros_empresa");

			List<Contratos> listado_contratos = (List<Contratos>) mapa_datos
					.get("listado_contratos");

			if (accion.equalsIgnoreCase("registrar")) {
				log.info("paciente ===> " + paciente);
				paciente.setDocumento(paciente.getNro_identificacion());
				Paciente pacienteAux = consultar(paciente);
				if (pacienteAux != null) {
					if (pacienteAux.getTipo_identificacion().equals(
							paciente.getTipo_identificacion())
							&& pacienteAux.getDocumento().equals(
									paciente.getDocumento())) {
						throw new ValidacionRunTimeException("Paciente "
								+ paciente.getTipo_identificacion() + "-"
								+ paciente.getDocumento()
								+ " ya se encuentra en la base de datos");
					} else {
						paciente.setNro_identificacion(paciente
								.getTipo_identificacion()
								+ "_"
								+ paciente.getDocumento());
					}
				}

				crear(paciente, false);
			} else {
				// log.info("Ingreso paciente actulizar: " + paciente);
				Paciente pacienteAux = consultarPorDocumento(paciente);
				if (pacienteAux != null) {
					if (!pacienteAux.getNro_identificacion().equals(
							paciente.getNro_identificacion())) {
						throw new ValidacionRunTimeException("Paciente "
								+ paciente.getTipo_identificacion() + "-"
								+ paciente.getDocumento()
								+ " ya se encuentra en la base de datos");
					}

				}
				int i = actualizar(paciente, false);
				if (i == 0) {
					throw new ValidacionRunTimeException(
							"Lo sentimos pero los dato del paciente no se han podido actualizar.");
				}
			}

			Pacientes_contratos pacientes_contratos_aux = new Pacientes_contratos();
			pacientes_contratos_aux.setCodigo_empresa(paciente
					.getCodigo_empresa());
			pacientes_contratos_aux.setCodigo_sucursal(paciente
					.getCodigo_sucursal());
			pacientes_contratos_aux.setNro_identificacion(paciente
					.getNro_identificacion());

			pacientes_contratosDao.eliminar(pacientes_contratos_aux);

			for (Contratos contratos : listado_contratos) {
				Map<String, Object> parametros_servicios = new HashMap<String, Object>();
				parametros_servicios.put("codigo_empresa",
						contratos.getCodigo_empresa());
				parametros_servicios.put("codigo_sucursal",
						contratos.getCodigo_sucursal());
				parametros_servicios.put("id_contrato", contratos.getId_plan());
				parametros_servicios.put("codigo_administradora",
						contratos.getCodigo_administradora());

				Pacientes_contratos pacientes_contratos = new Pacientes_contratos();
				pacientes_contratos.setCodigo_empresa(paciente
						.getCodigo_empresa());
				pacientes_contratos.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				pacientes_contratos.setNro_identificacion(paciente
						.getNro_identificacion());
				pacientes_contratos.setCodigo_administradora(paciente
						.getCodigo_administradora());
				pacientes_contratos.setId_codigo(contratos.getId_plan());
				pacientes_contratos.setCreacion_date(new Timestamp((new Date())
						.getTime()));
				pacientes_contratos.setCreacion_user(paciente
						.getCreacion_user());
				pacientes_contratosDao.crear(pacientes_contratos);
			}

			if (admision_rn != null) {
				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("admision", admision_rn);
				datos.put("accion", accion);
				datos.put("parametros_empresa", parametros_empresa);
				admisionService.guardar(datos);
			}

			if (generalExtraService.consultar(adicional_paciente) != null) {
				generalExtraService.actualizar(adicional_paciente);
			} else {
				generalExtraService.crear(adicional_paciente);
			}

		} catch (ValidacionRunTimeException e) {
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Paciente paciente, boolean crear_tercero) {
		try {
			if (consultar(paciente) != null) {
				throw new HealthmanagerException("Paciente "
						+ paciente.getNro_identificacion()
						+ " ya se encuentra en la base de datos");
			}
			pacienteDao.crear(paciente);
			if (crear_tercero) {
				guardarTercero(paciente);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Paciente paciente, boolean actualizar_tercero) {
		try {
			int result = pacienteDao.actualizar(paciente);
			if (actualizar_tercero) {
				guardarTercero(paciente);
			}
			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardarTercero(Paciente paciente) {
		Tercero tercero = new Tercero();
		tercero.setCodigo_empresa(paciente.getCodigo_empresa());
		tercero.setCodigo_sucursal(paciente.getCodigo_sucursal());
		tercero.setNro_identificacion(paciente.getNro_identificacion());
		tercero.setTipo_identificacion(paciente.getTipo_identificacion());
		tercero.setNombre1(paciente.getNombre1());
		tercero.setNombre2(paciente.getNombre2());
		tercero.setApellido1(paciente.getApellido1());
		tercero.setApellido2(paciente.getApellido2());
		tercero.setDireccion(paciente.getDireccion());
		tercero.setTel_oficina(paciente.getTel_oficina());
		tercero.setTel_res(paciente.getTel_res());
		tercero.setFax("");
		tercero.setContacto("");
		tercero.setEmail("");
		tercero.setCodigo_dpto(paciente.getCodigo_dpto());
		tercero.setCodigo_municipio(paciente.getCodigo_municipio());
		tercero.setZona_venta("");
		tercero.setCodigo_vendedor("");
		tercero.setTipo_contribuyente("01");
		tercero.setObservacion("");
		tercero.setCreacion_date(paciente.getCreacion_date());
		tercero.setUltimo_update(paciente.getUltimo_update());
		tercero.setCreacion_user(paciente.getCreacion_user());
		tercero.setUltimo_user(paciente.getUltimo_user());
		tercero.setEmpresa("");
		tercero.setDireccion_empresa("");
		tercero.setTelefono_empresa("");
		tercero.setCargo("");
		tercero.setTiempo_servicio("");
		tercero.setIngresos(0);
		tercero.setPropietario("");
		tercero.setDireccion_propietario("");
		tercero.setValor_arrendamiento(0);
		tercero.setTiempo_habita("");
		tercero.setBanco("");
		tercero.setTarifa_ica(0.0);
		tercero.setTipo("PACIENTE");
		tercero.setCuenta_cobrar("");
		tercero.setCuenta_pagar("");
		tercero.setDv("");
		tercero.setCuenta_reteica("");

		Tercero tercero_temp = terceroDao.consultar(tercero);
		if (tercero_temp != null) {
			tercero.setCreacion_date(tercero_temp.getCreacion_date());
			tercero.setCreacion_user(tercero_temp.getCreacion_user());

			if (tercero.getUltimo_update() == null) {
				tercero.setUltimo_update(tercero_temp.getUltimo_update());
			}

			if (tercero.getUltimo_user() == null) {
				tercero.setUltimo_user(tercero_temp.getUltimo_user());
			}

			terceroDao.actualizar(tercero);
		} else {
			terceroDao.crear(tercero);
			Tipo_tercero tipo = new Tipo_tercero();
			tipo.setCodigo_empresa(tercero.getCodigo_empresa());
			tipo.setCodigo_sucursal(tercero.getCodigo_sucursal());
			tipo.setNro_identificacion(tercero.getNro_identificacion());
			tipo.setTipo_tercero("02");
			tipo.setCreacion_date(tercero.getCreacion_date());
			tipo.setUltimo_update(tercero.getUltimo_update());
			tipo.setCreacion_user(tercero.getCreacion_user());
			tipo.setUltimo_user(tercero.getUltimo_user());
			tipo_terceroDao.crear(tipo);
		}
	}

	public Paciente consultar(Paciente paciente) {
		try {
			return pacienteDao.consultar(paciente);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Paciente paciente) {
		try {
			int result = pacienteDao.eliminar(paciente);

			Tercero tercero = new Tercero();
			tercero.setCodigo_empresa(paciente.getCodigo_empresa());
			tercero.setCodigo_sucursal(paciente.getCodigo_sucursal());
			tercero.setNro_identificacion(paciente.getNro_identificacion());
			terceroDao.eliminar(tercero);

			return result;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Paciente> listar(Map<String, Object> parameter) {
		try {
			return pacienteDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Paciente> listarPacienteSaludMental(
			Map<String, Object> parametros) {
		try {
			return pacienteDao.listarPacienteSaludMental(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Paciente> listar_conTuberculosis_lepra(
			Map<String, Object> parametros) {
		try {
			return pacienteDao.listar_conTuberculosis_lepra(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Paciente> listarGraficos(Map<String, Object> parameters) {

		try {
			return pacienteDao.listarGraficos(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * @author Modificado por Luis Miguel <b>Aciualizacion de contratos</b>
	 *
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> guardarImportacionExcel(
			Map<String, Object> mapa_parametros) {

		// String entidad = (String) mapa_parametros.get("entidad");
		List<Contratos> listado_contratos = (List<Contratos>) mapa_parametros
				.get("listado_contratos");
		String codigo_empresa = (String) mapa_parametros.get("codigo_empresa");
		String codigo_sucursal = (String) mapa_parametros
				.get("codigo_sucursal");
		String codigo_administradora = (String) mapa_parametros
				.get("codigo_administradora");
		String codigo_usuario = (String) mapa_parametros.get("codigo_usuario");
		String tipo_importacion = (String) mapa_parametros
				.get("tipo_importacion");
		String delimitador = (String) mapa_parametros.get("delimitador");

		try {
			String mensaje = "";
			Reader reader = (Reader) mapa_parametros.get("file_contenido");
			CsvReader csvReader = new CsvReader(reader, delimitador.charAt(0));

			if (tipo_importacion.equals("01")) {
				Map<String, Object> mapa_contratos = new HashMap<String, Object>();
				mapa_contratos.put("codigo_empresa", codigo_empresa);
				mapa_contratos.put("codigo_sucursal", codigo_sucursal);
				mapa_contratos.put("codigo_administradora",
						codigo_administradora);
				List<String> contratos_listado = new ArrayList<String>();
				for (Contratos contratos : listado_contratos) {
					contratos_listado.add(contratos.getId_plan());
				}
				mapa_contratos.put("contratos_listado", contratos_listado);

				pacientes_contratosDao
						.eliminar_contratos_varios(mapa_contratos);
				// log.info("Cantidad contratos eliminados ===> " +
				// eliminados+" ===> "+mapa_contratos);
				return guardarImportacionCsvEseCartagena2(csvReader,
						codigo_empresa, codigo_sucursal, codigo_usuario,
						codigo_administradora, listado_contratos);
			} else if (tipo_importacion.equals("02")) {
				return guardarImportacionCsvEseCartagena2(csvReader,
						codigo_empresa, codigo_sucursal, codigo_usuario,
						codigo_administradora, listado_contratos);
			} else if (tipo_importacion.equals("03")) {
				return actualizarDesactivarCsvEseCartagena2(csvReader,
						codigo_empresa, codigo_sucursal, codigo_administradora,
						codigo_usuario);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(IConstantes.IMPORTADOR_MSJ, mensaje);
				return map;
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	private Map<String, Object> actualizarDesactivarCsvEseCartagena2(
			CsvReader csvReader, String codigo_empresa, String codigo_sucursal,
			String codigo_adm_aux, String codigo_usuario) throws Exception {

		int actualizados = 0;

		String[] cabecera = null;
		if (csvReader.readHeaders()) {
			cabecera = csvReader.getHeaders();
		}
		int cols[] = new int[IConstantes.COLUMNAS_ARCHIVO.length];
		// Validamos que exista la columna //
		for (int i = 0; i < IConstantes.COLUMNAS_ARCHIVO.length; i++) {
			String name_col = IConstantes.COLUMNAS_ARCHIVO[i];
			String col_enc = null;
			for (int j = 0; j < cabecera.length; j++) {
				if (name_col.equalsIgnoreCase(cabecera[j])) {
					cols[i] = j;
					col_enc = cabecera[j];
					break;
				}
			}
			if (col_enc == null) {
				throw new Exception("Columna \"" + name_col
						+ "\" no se encuentra");
			}
		}

		// Recorremos el archivo //
		int nroLinea = 0;

		while (csvReader.readRecord()) {
			// if (nroLinea > 0) {
			String codigo_administradora = csvReader.get(cols[1]).toUpperCase();
			String tipo_identificacion = csvReader.get(cols[2]).toUpperCase();
			;
			String nro_identificacion = csvReader.get(cols[3]).toUpperCase();
			String apellido1 = csvReader.get(cols[4]).toUpperCase();
			String apellido2 = csvReader.get(cols[5]).toUpperCase();
			String nombre1 = csvReader.get(cols[6]).toUpperCase();
			String nombre2 = csvReader.get(cols[7]).toUpperCase();
			String sexo = csvReader.get(cols[9]).toUpperCase();
			String codigo_dpto = csvReader.get(cols[10]).toUpperCase();
			String codigo_mun = csvReader.get(cols[11]).toUpperCase();
			String zona = csvReader.get(cols[12]).toUpperCase();

			String nivel_sisben = csvReader.get(cols[15]).toUpperCase();

			String direccion = csvReader.get(cols[19]).toUpperCase();

			String tipo_afiliado = "1";
			String estrato = "1";
			String estado_civil = "3";
			String tipo_usuario = "2";

			Timestamp fecha_nacimiento = null;

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(codigo_empresa);
			administradora.setCodigo_sucursal(codigo_sucursal);
			administradora.setCodigo(codigo_administradora);
			if (administradoraDao.consultar(administradora) == null) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el codigo de eps no se encuentra registrado en el sistema ");
			}

			Elemento elemento = new Elemento();
			elemento.setTipo("tipo_id");
			elemento.setCodigo(tipo_identificacion);
			if (elementoService.consultar(elemento) == null) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el tipo de identificacion no se encuentra registrado en el sistema ");
			}

			if (nro_identificacion.length() > 20) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el nro de identificacion no puede tener mas de 20 dígitos ");
			}

			elemento = new Elemento();
			elemento.setTipo("tipo_usuario");
			elemento.setCodigo(tipo_usuario);
			if (elementoService.consultar(elemento) == null) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el tipo de usuario no se encuentra registrado en el sistema");
			}

			if (!codigo_adm_aux.equals(codigo_administradora)) {
				throw new Exception(
						"Error al importar la base de datos ya que en la fila "
								+ (nroLinea)
								+ " el codigo de la administradora no corresponte con el codigo de la administradora del contrato seleccionado "
								+ "(" + codigo_adm_aux + " != "
								+ codigo_administradora + ")");
			}

			/**
			 * ************ Creamos los pacientes ***************
			 */
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setCodigo_administradora(codigo_administradora);
			paciente.setTipo_identificacion(tipo_identificacion);
			paciente.setNro_identificacion(nro_identificacion);
			paciente.setApellido1(apellido1);
			paciente.setApellido2(apellido2);
			paciente.setNombre1(nombre1);
			paciente.setNombre2(nombre2);
			paciente.setFecha_nacimiento(fecha_nacimiento);
			paciente.setSexo(sexo);
			paciente.setTipo_afiliado(tipo_afiliado);
			paciente.setEstrato(estrato);
			paciente.setCodigo_dpto(codigo_dpto);
			paciente.setCodigo_municipio(codigo_mun);
			paciente.setZona(zona);
			paciente.setDireccion(direccion);
			paciente.setTipo_usuario(tipo_usuario);
			// paciente.setId_plan(contratos.getNro_contrato());
			paciente.setEdad("0");
			paciente.setUnidad_medidad("1");
			paciente.setLugar_exp("");
			paciente.setProfesion("");
			paciente.setTel_oficina("");
			paciente.setEstado_civil(estado_civil);
			paciente.setPaciente_particula("N");

			paciente.setNivel_sisben(nivel_sisben);
			paciente.setDocumento(nro_identificacion);
			Paciente pctAux = consultar(paciente);
			if (pctAux == null) {
				paciente.setNro_identificacion(paciente
						.getTipo_identificacion()
						+ "_"
						+ paciente.getNro_identificacion());
			}

			Pacientes_contratos pacientes_contratos = new Pacientes_contratos();
			pacientes_contratos.setCodigo_empresa(codigo_empresa);
			pacientes_contratos.setCodigo_sucursal(codigo_sucursal);
			pacientes_contratos.setCodigo_administradora(codigo_administradora);
			pacientes_contratos.setNro_identificacion(paciente
					.getNro_identificacion());

			int eliminado = pacientes_contratosDao
					.eliminar_contratos(pacientes_contratos);
			if (eliminado != 0) {
				actualizados++;
			}
			// }
			nroLinea++;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IConstantes.IMPORTADOR_MSJ, "Se han desactivado "
				+ actualizados + " pacientes. Anlizado ===> " + nroLinea);
		return map;
	}

	private Map<String, Object> guardarImportacionCsvEseCartagena2(
			CsvReader csvReader, String codigo_empresa, String codigo_sucursal,
			String codigo_usuario, String codigo_administradora_aux,
			List<Contratos> listado_contratos) throws Exception {

		// log.info("Ejecutando metodo ===> importarExcelEseCartagena");
		// esta validacion no hay que hacerla porque el sistema cuando se admite
		// o se generan las facturas
		// el pide con cual contrato se va a trabajar en el caso de que el
		// servicio se encuentre en diferentes contratos
		int procesados = 0;
		int creados = 0;
		int actualizados = 0;
		int no_actualizados_otra_eps = 0;

		String[] cabecera = null;
		if (csvReader.readHeaders()) {
			cabecera = csvReader.getHeaders();
		}
		int cols[] = new int[IConstantes.COLUMNAS_ARCHIVO.length];
		// Validamos que exista la columna //
		for (int i = 0; i < IConstantes.COLUMNAS_ARCHIVO.length; i++) {
			String name_col = IConstantes.COLUMNAS_ARCHIVO[i];
			String col_enc = null;
			for (int j = 0; j < cabecera.length; j++) {
				if (name_col.equalsIgnoreCase(cabecera[j])) {
					cols[i] = j;
					col_enc = cabecera[j];
					break;
				}
			}
			if (col_enc == null) {
				throw new Exception("Columna \"" + name_col
						+ "\" no se encuentra");
			}
		}

		Map<String, String> mapa_tipo_id = elementoService
				.listarMapElementos("tipo_id");

		Map<String, String> mapa_tipo_afiliado = elementoService
				.listarMapElementos("tipo_afiliado");

		Map<String, String> mapa_tipo_usuario = elementoService
				.listarMapElementos("tipo_usuario");

		Map<String, Departamentos> mapa_departamentos = new HashMap<String, Departamentos>();

		Map<String, Municipios> mapa_municipios = new HashMap<String, Municipios>();

		// Recorremos el archivo //
		int nroLinea = 0;
		Map<String, String> map_administradora_cache = new HashMap<String, String>();
		List<Map<String, Object>> listado_homologados = new ArrayList<Map<String, Object>>();
		while (csvReader.readRecord()) {
			// if (nroLinea > 0) { // no estaba tomando el primer paciente
			String carnet = csvReader.get(cols[0]).trim().toUpperCase();
			String codigo_administradora = csvReader.get(cols[1]).trim()
					.toUpperCase();
			String tipo_identificacion = csvReader.get(cols[2]).trim()
					.toUpperCase();
			String nro_identificacion = csvReader.get(cols[3]).trim()
					.toUpperCase();
			String apellido1 = csvReader.get(cols[4]).trim().toUpperCase();
			String apellido2 = csvReader.get(cols[5]).trim().toUpperCase();
			String nombre1 = csvReader.get(cols[6]).trim().toUpperCase();
			String nombre2 = csvReader.get(cols[7]).trim().toUpperCase();
			String fecha_nac = csvReader.get(cols[8]).trim().toUpperCase();
			String sexo = csvReader.get(cols[9]).trim().toUpperCase();
			String codigo_dpto = csvReader.get(cols[10]).trim().toUpperCase();
			String codigo_mun = csvReader.get(cols[11]).trim().toUpperCase();
			String zona = csvReader.get(cols[12]).trim().toUpperCase();
			String fecha_afiliacion = csvReader.get(cols[13]).trim()
					.toUpperCase();
			String tipo_poblacion = csvReader.get(cols[14]).trim()
					.toUpperCase();
			String nivel_sisben = csvReader.get(cols[15]).trim().toUpperCase();
			String ficha_sisben = csvReader.get(cols[16]).trim().toUpperCase();
			String modalidad_subsidio = csvReader.get(cols[17]).trim()
					.toUpperCase();
			String codigo_barrio = csvReader.get(cols[18]).trim().toUpperCase();
			String direccion = csvReader.get(cols[19]).trim().toUpperCase();

			String tipo_afiliado = "1";
			String estrato = "1";
			String estado_civil = "3";
			String tipo_usuario = "2";

			Timestamp fecha_nacimiento = null;

			if (!mapa_tipo_id.containsKey(tipo_identificacion)) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el tipo de identificacion no se encuentra registrado en el sistema ");
			}

			if (nro_identificacion.length() > 20) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el nro de identificacion no puede tener mas de 20 dígitos ");
			}

			if (apellido1.length() > 150) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el primer apellido no puede tener mas de 150 dígitos ");
			}

			if (nombre1.length() > 150) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el primer nombre no puede tener mas de 150 dígitos ");
			}

			if (!mapa_tipo_afiliado.containsKey(tipo_afiliado)) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el tipo de afiliado '"
								+ tipo_afiliado
								+ "' especificado no se encuentra registrado en el sistema debe digitar 1=Cotizante,2=Beneficiario,3=Adicional");
			}

			Departamentos departamentos = null;
			if (mapa_departamentos.containsKey(codigo_dpto)) {
				departamentos = mapa_departamentos.get(codigo_dpto);
			} else {
				departamentos = new Departamentos();
				departamentos.setCodigo(codigo_dpto);
				departamentos = departamentosDao.consultar(departamentos);
				mapa_departamentos.put(codigo_dpto, departamentos);
			}

			if (departamentos == null) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el codigo de dpto especificado no se encuentra registrado en el sistema");
			}

			Municipios municipios = null;

			if (mapa_municipios.containsKey(codigo_dpto + "_" + codigo_mun)) {
				municipios = mapa_municipios
						.get(codigo_dpto + "_" + codigo_mun);
			} else {
				municipios = new Municipios();
				municipios.setCoddep(codigo_dpto);
				municipios.setCodigo(codigo_mun);
				municipios = municipiosDao.consultar(municipios);
				mapa_municipios.put(codigo_dpto + "_" + codigo_mun, municipios);
			}

			if (municipios == null) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el codigo de municipio especificado no se encuentra registrado en el sistema");
			}

			if (!mapa_tipo_usuario.containsKey(tipo_usuario)) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el tipo de usuario no se encuentra registrado en el sistema");
			}

			Timestamp creacion_date = new Timestamp(
					new GregorianCalendar().getTimeInMillis());
			String creacion_user = codigo_usuario;
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				fecha_nacimiento = new Timestamp(
						(formatter.parse(fecha_nac)).getTime());
			} catch (Exception e) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el valor especificado en la fecha de nacimiento es invalido");
			}

			if (!codigo_administradora_aux.equals(codigo_administradora)) {
				throw new Exception(
						"Error al importar la base de datos ya que en la fila "
								+ (nroLinea)
								+ " el codigo de la administradora no corresponte con el codigo de la administradora del contrato seleccionado "
								+ "("
								+ listado_contratos.get(0)
										.getCodigo_administradora() + " != "
								+ codigo_administradora + ")");
			}

			/**
			 * ************ Creamos los pacientes ***************
			 */
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setCodigo_administradora(codigo_administradora);
			paciente.setTipo_identificacion(tipo_identificacion);
			paciente.setNro_identificacion(nro_identificacion);
			paciente.setApellido1(apellido1);
			paciente.setApellido2(apellido2);
			paciente.setNombre1(nombre1);
			paciente.setNombre2(nombre2);
			paciente.setFecha_nacimiento(fecha_nacimiento);
			paciente.setSexo(sexo);
			paciente.setTipo_afiliado(tipo_afiliado);
			paciente.setEstrato(estrato);
			paciente.setCodigo_dpto(codigo_dpto);
			paciente.setCodigo_municipio(codigo_mun);
			paciente.setZona(zona);
			paciente.setDireccion(direccion);
			paciente.setTipo_usuario(tipo_usuario);
			// paciente.setId_plan(contratos.getNro_contrato());
			paciente.setEdad("0");
			paciente.setUnidad_medidad("1");
			paciente.setLugar_exp("");
			paciente.setProfesion("");
			paciente.setTel_oficina("");
			paciente.setEstado_civil(estado_civil);
			paciente.setPaciente_particula("N");
			paciente.setCreacion_date(creacion_date);
			paciente.setUltimo_update(creacion_date);
			paciente.setCreacion_user(creacion_user);
			paciente.setUltimo_user(creacion_user);
			paciente.setNivel_sisben(nivel_sisben);
			paciente.setDocumento(nro_identificacion);

			boolean ignorar = false;
			Paciente pctAux = consultarPorDocumento(paciente);
			if (pctAux != null) {// actualizamos lso registros
				// Verificamos si pertenece a la misma aseguradora
				// Esto es por el momento si es dadis
				// :TODO Esta pendiente marcar las admisnitradoras
				// para a la hora de importar me las incluya en esta parte

				String tipo_aseguradora = map_administradora_cache.get(pctAux
						.getCodigo_administradora());
				if (tipo_aseguradora == null) {
					Administradora administradora = new Administradora();
					administradora.setCodigo_empresa(codigo_empresa);
					administradora.setCodigo_sucursal(codigo_sucursal);
					administradora.setCodigo(pctAux.getCodigo_administradora());
					administradora = administradoraDao
							.consultar(administradora);
					if (administradora != null) {
						tipo_aseguradora = administradora.getTipo_aseguradora();
					} else {
						throw new Exception("La aseguradora "
								+ pctAux.getCodigo_administradora()
								+ " no existe en la base de datos");
					}
				}

				/**
				 * Los pacientes que tengan una aseguradora tipo vinculado,
				 * podran ser separados por la nueva que se importe
				 *
				 */
				if (pctAux.getCodigo_administradora().equals(
						codigo_administradora)
						|| tipo_aseguradora
								.equals(IConstantes.TIPO_ASEGURADORA_VINCULADA)) {
					pctAux.setFecha_nacimiento(fecha_nacimiento);
					pctAux.setCodigo_administradora(codigo_administradora);
					pctAux.setSexo(sexo);
					pctAux.setCodigo_dpto(codigo_dpto);
					pctAux.setCodigo_municipio(codigo_mun);
					pctAux.setZona(zona);
					pctAux.setDireccion(direccion);
					pctAux.setNivel_sisben(nivel_sisben);
					pctAux.setNombre1(nombre1);
					pctAux.setNombre2(nombre2);
					pctAux.setApellido1(apellido1);
					pctAux.setApellido2(apellido2);
					nro_identificacion = pctAux.getNro_identificacion();
					actualizar(pctAux, false);
					actualizados++;
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("codigo_empresa", pctAux.getCodigo_empresa());
					map.put("codigo_sucursal", pctAux.getCodigo_sucursal());
					map.put("nro_identificacion",
							pctAux.getNro_identificacion());
					Timestamp fecha_afiliacion_anterior = pacienteDao
							.getFechaAfiliacion(map);

					// Esta validacion corresponde a la fecha de afiliacion del
					// paciente
					// cuando aparesca con otra EPS, y la que tenga la fecha
					// mayor de afiliacion
					if (fecha_afiliacion_anterior == null
							|| fecha_afiliacion_anterior
									.compareTo(new Timestamp((formatter
											.parse(fecha_afiliacion)).getTime())) < 0) {
						pctAux.setFecha_nacimiento(fecha_nacimiento);
						pctAux.setCodigo_administradora(codigo_administradora);
						pctAux.setSexo(sexo);
						pctAux.setCodigo_dpto(codigo_dpto);
						pctAux.setCodigo_municipio(codigo_mun);
						pctAux.setZona(zona);
						pctAux.setDireccion(direccion);
						pctAux.setNivel_sisben(nivel_sisben);
						pctAux.setNombre1(nombre1);
						pctAux.setNombre2(nombre2);
						pctAux.setApellido1(apellido1);
						pctAux.setApellido2(apellido2);
						nro_identificacion = pctAux.getNro_identificacion();
						actualizar(pctAux, false);
						actualizados++;
					} else { // sino no lo va a actualizar, lo va a dejar con la
						// EPS anterior
						ignorar = true;
						no_actualizados_otra_eps++;
					}
				}

			} else { // no existe comparamos
				List<Paciente> listado_pacientes = (List<Paciente>) consultarPorInformacion(paciente);
				if (listado_pacientes.isEmpty()) { // sino existe proceguimos a
					// registrar
					pctAux = consultar(paciente);
					if (pctAux == null) {
						crear(paciente, false);
						creados++;
					} else {
						paciente.setNro_identificacion(paciente
								.getTipo_identificacion()
								+ "_"
								+ paciente.getNro_identificacion());
						nro_identificacion = paciente.getNro_identificacion();
						if (consultar(paciente) == null) {
							crear(paciente, false);
							creados++;
						} else {
							actualizar(paciente, false);
							actualizados++;
						}
					}
				} else {
					// Verificamos si el paciente tiene el mismo numero de
					// identificacion
					boolean agregarListado = true;
					for (Paciente paciente_homologado : listado_pacientes) {
						if (paciente_homologado.getDocumento().equals(
								paciente.getDocumento())) {
							paciente_homologado.setFecha_nacimiento(paciente
									.getFecha_nacimiento());
							paciente_homologado.setSexo(paciente.getSexo());
							paciente_homologado.setCodigo_dpto(paciente
									.getCodigo_dpto());
							paciente_homologado.setCodigo_municipio(paciente
									.getCodigo_municipio());
							paciente_homologado.setZona(paciente.getZona());
							paciente_homologado.setDireccion(paciente
									.getDireccion());
							paciente_homologado.setNivel_sisben(paciente
									.getNivel_sisben());
							paciente_homologado.setTipo_identificacion(paciente
									.getTipo_identificacion());
							paciente_homologado.setDocumento(paciente
									.getDocumento());
							paciente_homologado
									.setCodigo_administradora(paciente
											.getCodigo_administradora());
							paciente_homologado.setNombre1(paciente
									.getNombre1());
							paciente_homologado.setNombre2(paciente
									.getNombre2());
							paciente_homologado.setApellido1(paciente
									.getApellido1());
							paciente_homologado.setApellido2(paciente
									.getApellido2());

							Adicional_paciente adicional_paciente = new Adicional_paciente();
							adicional_paciente.setCodigo_empresa(paciente
									.getCodigo_empresa());
							adicional_paciente.setCodigo_sucursal(paciente
									.getCodigo_sucursal());
							adicional_paciente.setNro_identificacion(paciente
									.getNro_identificacion());
							adicional_paciente.setCarnet(carnet);
							adicional_paciente.setCodigo_barrio(codigo_barrio);
							adicional_paciente
									.setFecha_afiliacion(new Timestamp(
											(formatter.parse(fecha_afiliacion))
													.getTime()));
							adicional_paciente.setFicha_sisben(ficha_sisben);
							adicional_paciente
									.setModalidad_subsidio(modalidad_subsidio);
							adicional_paciente
									.setTipo_poblacion(tipo_poblacion);
							adicional_paciente
									.setNro_identificacion(paciente_homologado
											.getNro_identificacion());

							Map<String, Object> mapa_datos = new HashMap<String, Object>();
							mapa_datos.put("paciente", paciente_homologado);
							mapa_datos.put("accion", "modificar");
							mapa_datos.put("listado_contratos",
									listado_contratos);
							mapa_datos.put("adicional_paciente",
									adicional_paciente);

							guardarDatos(mapa_datos);
							agregarListado = false;
							break;
						}
					}

					// Agregamos al listado siempre el paciente homologado
					// no tenga el mismo numero de identificacion
					if (agregarListado) {
						Map<String, Object> mapHomologado = new HashMap<String, Object>();
						mapHomologado.put(
								IConstantes.IMPORTADOR_INTERNO_PACIENTE,
								paciente);
						mapHomologado
								.put(IConstantes.IMPORTADOR_INTERNO_LISTADO_PACIENTES,
										listado_pacientes);

						// adicional paciente
						Adicional_paciente adicional_paciente = new Adicional_paciente();
						adicional_paciente.setCodigo_empresa(paciente
								.getCodigo_empresa());
						adicional_paciente.setCodigo_sucursal(paciente
								.getCodigo_sucursal());
						adicional_paciente.setNro_identificacion(paciente
								.getNro_identificacion());
						adicional_paciente.setCarnet(carnet);
						adicional_paciente.setCodigo_barrio(codigo_barrio);
						adicional_paciente.setFecha_afiliacion(new Timestamp(
								(formatter.parse(fecha_afiliacion)).getTime()));
						adicional_paciente.setFicha_sisben(ficha_sisben);
						adicional_paciente
								.setModalidad_subsidio(modalidad_subsidio);

						// log.info("fecha afiliacion: " +
						// adicional_paciente.getFecha_afiliacion());
						adicional_paciente.setTipo_poblacion(tipo_poblacion);
						mapHomologado
								.put(IConstantes.IMPORTADOR_INTERNO_ADICIONAL_PACIENTE,
										adicional_paciente);

						listado_homologados.add(mapHomologado);
					}
					ignorar = true;
				}
			}

			procesados++;

			if (!ignorar) {
				for (Contratos contratos : listado_contratos) {
					Pacientes_contratos pacientes_contratos = new Pacientes_contratos();
					pacientes_contratos.setCodigo_empresa(paciente
							.getCodigo_empresa());
					pacientes_contratos.setCodigo_sucursal(paciente
							.getCodigo_sucursal());
					pacientes_contratos.setCodigo_administradora(paciente
							.getCodigo_administradora());
					pacientes_contratos.setCreacion_date(paciente
							.getCreacion_date());
					pacientes_contratos.setCreacion_user(paciente
							.getCreacion_user());
					pacientes_contratos.setId_codigo(contratos.getId_plan());
					pacientes_contratos
							.setNro_identificacion(nro_identificacion);

					/* creamos relacion de contrato paciente si no existe */
					if (pacientes_contratosDao.consultar(pacientes_contratos) == null) {
						pacientes_contratosDao.crear(pacientes_contratos);
					} else {
						pacientes_contratosDao.actualizar(pacientes_contratos);
					}
				}

				Adicional_paciente adicional_paciente = new Adicional_paciente();
				adicional_paciente.setCodigo_empresa(paciente
						.getCodigo_empresa());
				adicional_paciente.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				adicional_paciente.setNro_identificacion(nro_identificacion);
				adicional_paciente.setCarnet(carnet);
				adicional_paciente.setCodigo_barrio(codigo_barrio);
				adicional_paciente.setFecha_afiliacion(new Timestamp((formatter
						.parse(fecha_afiliacion)).getTime()));
				adicional_paciente.setFicha_sisben(ficha_sisben);
				adicional_paciente.setModalidad_subsidio(modalidad_subsidio);

				adicional_paciente.setTipo_poblacion(tipo_poblacion);

				if (generalExtraService.consultar(adicional_paciente) == null) {
					generalExtraService.crear(adicional_paciente);
				} else {
					generalExtraService.actualizar(adicional_paciente);
				}
			}
			// }
			nroLinea++;
		}

		Integer cantidad_actualizados = verificarNacidosVivos(codigo_empresa,
				codigo_sucursal, codigo_administradora_aux, codigo_usuario);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IConstantes.IMPORTADOR_MSJ, "Se han procesado " + procesados
				+ " pacientes. Se crearon " + creados + ", se actualizaron "
				+ actualizados + " y se encontraron "
				+ no_actualizados_otra_eps
				+ " pacientes con una fecha de afiliacion mayor de otra EPS");
		map.put(IConstantes.IMPORTADOR_POSIBLES_PACIENTES_REPETIDOS,
				listado_homologados);
		map.put(IConstantes.IMPORTADOR_NACIDOS_VIVOS, cantidad_actualizados);
		return map;
	}

	public Map<String, Object> guardarActualizacionCsvEseCartagena2(
			Map<String, Object> mapa_parametros) throws Exception {

		String codigo_empresa = (String) mapa_parametros.get("codigo_empresa");
		String codigo_sucursal = (String) mapa_parametros
				.get("codigo_sucursal");
		String codigo_usuario = (String) mapa_parametros.get("codigo_usuario");
		String delimitador = (String) mapa_parametros.get("delimitador");

		Reader reader = (Reader) mapa_parametros.get("file_contenido");
		CsvReader csvReader = new CsvReader(reader, delimitador.charAt(0));

		String[] cabecera = null;
		if (csvReader.readHeaders()) {
			cabecera = csvReader.getHeaders();
		}
		int cols[] = new int[IConstantes.COLUMNAS_ARCHIVO.length];
		// Validamos que exista la columna //
		for (int i = 0; i < IConstantes.COLUMNAS_ARCHIVO.length; i++) {
			String name_col = IConstantes.COLUMNAS_ARCHIVO[i];
			String col_enc = null;
			for (int j = 0; j < cabecera.length; j++) {
				if (name_col.equalsIgnoreCase(cabecera[j])) {
					cols[i] = j;
					col_enc = cabecera[j];
					break;
				}
			}
			if (col_enc == null) {
				throw new Exception("Columna \"" + name_col
						+ "\" no se encuentra");
			}
		}

		Map<String, String> mapa_tipo_usuario = elementoService
				.listarMapElementos("tipo_usuario");

		// Recorremos el archivo //
		int nroLinea = 0;
		StringBuilder stringBuilder = new StringBuilder(
				"Listado de pacientes actualizados\n");
		while (csvReader.readRecord()) {
			// if (nroLinea > 0) { // no estaba tomando el primer paciente
			String codigo_administradora = csvReader.get(cols[1]).trim()
					.toUpperCase();
			String tipo_identificacion = csvReader.get(cols[2]).trim()
					.toUpperCase();
			String nro_identificacion = csvReader.get(cols[3]).trim()
					.toUpperCase();
			String apellido1 = csvReader.get(cols[4]).trim().toUpperCase();
			String apellido2 = csvReader.get(cols[5]).trim().toUpperCase();
			String nombre1 = csvReader.get(cols[6]).trim().toUpperCase();
			String nombre2 = csvReader.get(cols[7]).trim().toUpperCase();
			String fecha_nac = csvReader.get(cols[8]).trim().toUpperCase();
			String sexo = csvReader.get(cols[9]).trim().toUpperCase();
			String codigo_dpto = csvReader.get(cols[10]).trim().toUpperCase();
			String codigo_mun = csvReader.get(cols[11]).trim().toUpperCase();
			String zona = csvReader.get(cols[12]).trim().toUpperCase();

			String nivel_sisben = csvReader.get(cols[15]).trim().toUpperCase();
			String direccion = csvReader.get(cols[19]).trim().toUpperCase();

			String tipo_afiliado = "1";
			String estrato = "1";
			String estado_civil = "3";
			String tipo_usuario = "2";

			Timestamp fecha_nacimiento = null;

			if (!mapa_tipo_usuario.containsKey(tipo_usuario)) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el tipo de usuario no se encuentra registrado en el sistema");
			}

			Timestamp creacion_date = new Timestamp(
					new GregorianCalendar().getTimeInMillis());
			String creacion_user = codigo_usuario;
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				fecha_nacimiento = new Timestamp(
						(formatter.parse(fecha_nac)).getTime());
			} catch (Exception e) {
				throw new Exception(
						"Error en la fila "
								+ (nroLinea)
								+ " el valor especificado en la fecha de nacimiento es invalido");
			}

			/**
			 * ************ Creamos los pacientes ***************
			 */
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setCodigo_administradora(codigo_administradora);
			paciente.setTipo_identificacion(tipo_identificacion);
			paciente.setNro_identificacion(nro_identificacion);
			paciente.setApellido1(apellido1);
			paciente.setApellido2(apellido2);
			paciente.setNombre1(nombre1);
			paciente.setNombre2(nombre2);
			paciente.setFecha_nacimiento(fecha_nacimiento);
			paciente.setSexo(sexo);
			paciente.setTipo_afiliado(tipo_afiliado);
			paciente.setEstrato(estrato);
			paciente.setCodigo_dpto(codigo_dpto);
			paciente.setCodigo_municipio(codigo_mun);
			paciente.setZona(zona);
			paciente.setDireccion(direccion);
			paciente.setTipo_usuario(tipo_usuario);
			// paciente.setId_plan(contratos.getNro_contrato());
			paciente.setEdad("0");
			paciente.setUnidad_medidad("1");
			paciente.setLugar_exp("");
			paciente.setProfesion("");
			paciente.setTel_oficina("");
			paciente.setEstado_civil(estado_civil);
			paciente.setPaciente_particula("N");
			paciente.setCreacion_date(creacion_date);
			paciente.setUltimo_update(creacion_date);
			paciente.setCreacion_user(creacion_user);
			paciente.setUltimo_user(creacion_user);
			paciente.setNivel_sisben(nivel_sisben);
			paciente.setDocumento(nro_identificacion);

			Paciente pctAux = consultarPorDocumento(paciente);
			if (pctAux != null) {// actualizamos lso registros
				pctAux.setFecha_nacimiento(fecha_nacimiento);
				int resultado = pacienteDao.actualizarFechaNacimiento(pctAux);
				stringBuilder.append(resultado).append(",")
						.append(pctAux.getNro_identificacion()).append(",")
						.append(pctAux.getFecha_nacimiento()).append(",").append(fecha_nac).append("\n");
			} else { // no existe comparamos
				List<Paciente> listado_pacientes = (List<Paciente>) consultarPorInformacion(paciente);
				if (!listado_pacientes.isEmpty()) { // sino existe proceguimos a
					for (Paciente paciente_homologado : listado_pacientes) {
						if (paciente_homologado.getDocumento().equals(
								paciente.getDocumento())) {
							paciente_homologado.setFecha_nacimiento(paciente
									.getFecha_nacimiento());
							paciente_homologado.setSexo(paciente.getSexo());
							paciente_homologado.setCodigo_dpto(paciente
									.getCodigo_dpto());
							paciente_homologado.setCodigo_municipio(paciente
									.getCodigo_municipio());
							paciente_homologado.setZona(paciente.getZona());
							paciente_homologado.setDireccion(paciente
									.getDireccion());
							paciente_homologado.setNivel_sisben(paciente
									.getNivel_sisben());
							paciente_homologado.setTipo_identificacion(paciente
									.getTipo_identificacion());
							paciente_homologado.setDocumento(paciente
									.getDocumento());
							paciente_homologado
									.setCodigo_administradora(paciente
											.getCodigo_administradora());
							paciente_homologado.setNombre1(paciente
									.getNombre1());
							paciente_homologado.setNombre2(paciente
									.getNombre2());
							paciente_homologado.setApellido1(paciente
									.getApellido1());
							paciente_homologado.setApellido2(paciente
									.getApellido2());
							int resultado = pacienteDao
									.actualizarFechaNacimiento(paciente_homologado);
							stringBuilder
									.append(resultado)
									.append(",")
									.append(paciente_homologado
											.getNro_identificacion())
									.append(",")
									.append(paciente_homologado
											.getFecha_nacimiento())
									.append(",").append(fecha_nac).append("\n");
							break;
						}
					}
				}
			}
			nroLinea++;
			log.info("nroLinea ===> "+nroLinea);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("LISTADO_ACTUALIZACIONES", stringBuilder.toString());
		map.put("PROCESADOS", nroLinea);
		return map;
	}

	public Integer verificarNacidosVivos(String codigo_empresa,
			String codigo_sucursal, String codigo_administradora,
			String codigo_usuario) {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_administradora", codigo_administradora);
		parametros.put("nacidos_vivos", "_nacidos_vivos");

		List<Paciente> listado = listar(parametros);

		Integer cantidad_actualizados = 0;

		for (Paciente paciente : listado) {
			StringTokenizer stIdentificacion = new StringTokenizer(
					paciente.getNro_identificacion(), "-");
			String nro_madre = stIdentificacion.nextToken();
			parametros.put("nro_identificacion", nro_madre);
			List<Pacientes_contratos> listado_contratos = pacientes_contratosDao
					.listar(parametros);
			for (Pacientes_contratos pacientes_contratos : listado_contratos) {
				pacientes_contratos.setCreacion_date(new Timestamp(new Date()
						.getTime()));
				pacientes_contratos.setCreacion_user(codigo_usuario);
				pacientes_contratos.setNro_identificacion(paciente
						.getNro_identificacion());
				if (pacientes_contratosDao.consultar(pacientes_contratos) != null) {
					pacientes_contratosDao.actualizar(pacientes_contratos);
				} else {
					pacientes_contratosDao.crear(pacientes_contratos);
				}
			}
			cantidad_actualizados++;
		}

		return cantidad_actualizados;
	}

	public List<Paciente> listar_por_centro(Map<String, Object> parameters) {
		// log.info("limit: " + limit);
		return pacienteDao.listar_por_centro(parameters);
	}

	public Paciente consultarPorLoginPassword(Paciente paciente) {
		try {
			return pacienteDao.consultarPorLoginPassword(paciente);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Paciente consultarPorDocumento(Paciente paciente) {
		try {
			return pacienteDao.consultarPorDocumento(paciente);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Paciente> consultarPorInformacion(Paciente paciente) {
		try {
			return pacienteDao.consultarPorInformacion(paciente);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer totalResultados(Map<String, Object> parametros) {
		try {
			return pacienteDao.totalResultados(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
