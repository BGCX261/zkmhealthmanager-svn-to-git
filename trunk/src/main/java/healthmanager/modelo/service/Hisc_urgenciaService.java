/*
 * Hisc_partoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo2;
import healthmanager.modelo.bean.Anexo9_entidad;
import healthmanager.modelo.bean.Antecedentes_personales;
import healthmanager.modelo.bean.Contratos_paquetes;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Detalles_paquetes_servicios;
import healthmanager.modelo.bean.Furips2;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Paquetes_servicios;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Remision_interna;
import healthmanager.modelo.bean.Revision_sistema;
import healthmanager.modelo.bean.Sigvitales;
import healthmanager.modelo.bean.Via_ingreso_consultas;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.Anexo9_entidadDao;
import healthmanager.modelo.dao.Antecedentes_personalesDao;
import healthmanager.modelo.dao.Contratos_paquetesDao;
import healthmanager.modelo.dao.Detalles_paquetes_serviciosDao;
import healthmanager.modelo.dao.Furips2Dao;
import healthmanager.modelo.dao.Hisc_urgenciaDao;
import healthmanager.modelo.dao.Pacientes_contratosDao;
import healthmanager.modelo.dao.ProcedimientosDao;
import healthmanager.modelo.dao.Revision_sistemaDao;
import healthmanager.modelo.dao.SigvitalesDao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IDatosProcedimientos;
import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.ServiciosDisponiblesUtils;

@Service("hisc_urgenciaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Hisc_urgenciaService implements Serializable{

	private String limit;

	@Autowired
	private Hisc_urgenciaDao hisc_urgenciaDao;

	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Receta_ripsService receta_ripsService;
	@Autowired
	private Orden_servicioService orden_servicioService;
	@Autowired
	private Revision_sistemaDao revision_sistemaDao;
	@Autowired
	private SigvitalesDao sigvitalesDao;
	@Autowired
	private Antecedentes_personalesDao antecedentes_personalesDao;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private Impresion_diagnosticaService impresion_diagnosticaService;
	@Autowired
	private Ficha_epidemiologia_nnService ficha_epidemiologia_nnService;
	@Autowired
	private Anexo9_entidadDao anexo9_entidadDao;
	@Autowired
	private Remision_internaService remision_internaService;
	@Autowired
	private Hoja_gastosService hoja_gastosService;
	@Autowired
	private Pacientes_contratosDao pacientes_contratosDao;
	@Autowired
	private Contratos_paquetesDao contratos_paquetesDao;
	@Autowired
	private Detalles_paquetes_serviciosDao detalles_paquetes_serviciosDao;
	@Autowired
	private Datos_procedimientoService datos_procedimientoService;
	@Autowired
	private Furips2Dao furips2Dao;
	@Autowired
	private GeneralExtraService generalExtraService;
	@Autowired
	private ProcedimientosDao procedimientosDao;

	public void guardarDatos(Map<String, Object> datos) {
		File file_url_partograma = null;
		try {
			String url_partograma_anterior = (String) datos
					.get("url_partograma_anterior");
			if (url_partograma_anterior != null
					&& !url_partograma_anterior.isEmpty()) {
				File file = new File(url_partograma_anterior);
				file.delete();
			}

			Hisc_urgencia hisc_urgencia = (Hisc_urgencia) datos
					.get("hisc_urgencia");
			Admision admision = (Admision) datos.get("admision");
			Sigvitales sigvitales = (Sigvitales) datos.get("sigvitales");
			List<Antecedentes_personales> listadoAntecedentes = (List<Antecedentes_personales>) datos
					.get("listadoAntecedentes");
			List<Revision_sistema> listadoRevisiones = (List<Revision_sistema>) datos
					.get("listadoRevisiones");
			String accion = (String) datos.get("accion");

			String via_ingreso = (String) datos.get("via_ingreso");

			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(hisc_urgencia
					.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_urgencia
					.getCodigo_sucursal());
			historia_clinica
					.setFecha_historia(hisc_urgencia.getFecha_ingreso());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(via_ingreso);
			historia_clinica.setPrestador(hisc_urgencia.getCreacion_user());

			Remision_interna remision_interna = (Remision_interna) datos
					.get("remision_interna");

			remision_interna.setCodigo_paciente(admision.getPaciente()
					.getNro_identificacion());
			remision_interna.setCodigo_historia(hisc_urgencia
					.getCodigo_historia());

			if (accion.equalsIgnoreCase("registrar")) {
				generalExtraService.crear(historia_clinica);
				hisc_urgencia.setCodigo_historia(historia_clinica
						.getCodigo_historia());
				hisc_urgenciaDao.crear(hisc_urgencia);

				remision_interna.setCodigo_historia(hisc_urgencia
						.getCodigo_historia());

			} else {
				if (generalExtraService.consultar(historia_clinica) != null) {
					if (consultar(hisc_urgencia) == null)
						throw new Exception(
								"El dato que desea actualizar no existe en la base de datos");
					hisc_urgenciaDao.actualizar(hisc_urgencia);
				}
			}
			if (remision_internaService.consultar(remision_interna) == null) {
				remision_internaService.crear(remision_interna);
			} else {
				remision_internaService.actualizar(remision_interna);
			}

			InputStream file_partograma = (InputStream) datos
					.get("file_partograma");
			if (file_partograma != null) {
				String url_partogramas = (String) datos.get("url_partogramas");
				String formato_partograma = (String) datos
						.get("formato_partograma");
				if (url_partogramas != null) {
					url_partogramas = url_partogramas + "/"
							+ hisc_urgencia.getCodigo_empresa() + "/"
							+ hisc_urgencia.getCodigo_sucursal() + "/"
							+ hisc_urgencia.getNro_identificacion();
					File archivo_partogramas = new File(url_partogramas);
					archivo_partogramas.mkdirs();

					BufferedInputStream bufferedInputStream = new BufferedInputStream(
							file_partograma);

					file_url_partograma = new File(url_partogramas + "/"
							+ historia_clinica.getCodigo_historia() + "."
							+ formato_partograma);

					FileOutputStream fileOutputStream = new FileOutputStream(
							file_url_partograma);
					BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
							fileOutputStream);

					byte[] array = new byte[100];
					int leidos = bufferedInputStream.read(array);

					while (leidos > 0) {
						bufferedOutputStream.write(array);
						leidos = bufferedInputStream.read(array);
					}

					bufferedInputStream.close();
					bufferedOutputStream.close();

				}
			}

			if (file_url_partograma != null) {
				hisc_urgencia.setUrl_partograma(file_url_partograma.getPath());
			}

			sigvitales.setCodigo_historia(hisc_urgencia.getCodigo_historia());
			sigvitales.setNro_identificacion(hisc_urgencia
					.getNro_identificacion());

			sigvitales.setCreacion_date(new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			if (sigvitalesDao.consultar(sigvitales) != null) {
				sigvitalesDao.actualizar(sigvitales);
			} else {
				sigvitalesDao.crear(sigvitales);
			}

			Antecedentes_personales antecedentesAux = new Antecedentes_personales();
			antecedentesAux
					.setCodigo_empresa(hisc_urgencia.getCodigo_empresa());
			antecedentesAux.setCodigo_sucursal(hisc_urgencia
					.getCodigo_sucursal());
			antecedentesAux.setCodigo_historia(hisc_urgencia
					.getCodigo_historia());
			antecedentes_personalesDao.eliminar(antecedentesAux);

			for (Antecedentes_personales antePersonales : listadoAntecedentes) {
				antePersonales.setCodigo_historia(hisc_urgencia
						.getCodigo_historia());
				antePersonales.setNro_identificacion(hisc_urgencia
						.getNro_identificacion());
				antecedentes_personalesDao.crear(antePersonales);
			}

			Revision_sistema revision_sistemaAux = new Revision_sistema();
			revision_sistemaAux.setCodigo_empresa(hisc_urgencia
					.getCodigo_empresa());
			revision_sistemaAux.setCodigo_sucursal(hisc_urgencia
					.getCodigo_sucursal());
			revision_sistemaAux.setCodigo_historia(hisc_urgencia
					.getCodigo_historia());
			revision_sistemaAux.setNro_identificacion(hisc_urgencia
					.getNro_identificacion());
			revision_sistemaDao.eliminar(revision_sistemaAux);

			for (Revision_sistema revision_sistema : listadoRevisiones) {
				revision_sistema.setCodigo_historia(hisc_urgencia
						.getCodigo_historia());
				revision_sistema.setNro_identificacion(hisc_urgencia
						.getNro_identificacion());
				revision_sistemaDao.crear(revision_sistema);
			}

			admision.setAtendida(true);
			admision.setCodigo_medico(hisc_urgencia.getCreacion_user());

			String hospitalizar = (String) datos.get("hospitalizar");

			if (hospitalizar != null) {
				admision.setHospitalizacion(hospitalizar);
			}

			Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) datos
					.get("impresion_diagnostica");
			admision.setDiagnostico_ingreso(impresion_diagnostica
					.getCie_principal());
			admisionDao.actualizar(admision);

			String tipo_urgencia = (String) datos.get("tipo_urgencia");

			if (!admision.getVia_ingreso().equals(
					IVias_ingreso.HOSPITALIZACIONES)) {
				crearConsulta(hisc_urgencia, admision, impresion_diagnostica,
						tipo_urgencia);
			}

			FichaEpidemiologiaModel.guardarDatosImpresionDiagnostica(
					hisc_urgencia.getCodigo_historia(), datos,
					ficha_epidemiologia_nnService,
					impresion_diagnosticaService,
					hisc_urgencia.getCreacion_user());
			// log.info("9 ------ ");

			Anexo9_entidad anexo9_entidad = (Anexo9_entidad) datos
					.get("anexo9");

			if (anexo9_entidad != null) {
				anexo9_entidad.setNro_historia(hisc_urgencia
						.getCodigo_historia());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				Anexo9_entidad anexo9_aux = anexo9_entidadDao
						.consultar(anexo9_entidad);
				if (anexo9_aux != null) {
					anexo9_entidad.setCodigo(anexo9_aux.getCodigo());
					anexo9_entidadDao.actualizar(anexo9_entidad);
				} else {
					String consecutivo = consecutivoService.crearConsecutivo(
							hisc_urgencia.getCodigo_empresa(),
							hisc_urgencia.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9);
					String codigo_anexo = consecutivoService.getZeroFill(
							consecutivo, 10);
					anexo9_entidad.setCodigo(codigo_anexo);
					anexo9_entidadDao.crear(anexo9_entidad);
					consecutivoService.actualizarConsecutivo(
							hisc_urgencia.getCodigo_empresa(),
							hisc_urgencia.getCodigo_sucursal(),
							IConstantes.CONS_ANEXO_9, codigo_anexo);
				}
			} else {
				anexo9_entidad = new Anexo9_entidad();
				anexo9_entidad.setCodigo_empresa(admision.getCodigo_empresa());
				anexo9_entidad
						.setCodigo_sucursal(admision.getCodigo_sucursal());
				anexo9_entidad.setNro_ingreso(admision.getNro_ingreso());
				anexo9_entidad.setCodigo_paciente(admision
						.getNro_identificacion());
				anexo9_entidadDao.eliminar(anexo9_entidad);
			}

			// diligenciamiento del anexo 2
			Anexo2 anexo2 = new Anexo2();
			anexo2.setCodigo_empresa(admision.getCodigo_empresa());
			anexo2.setCodigo_sucursal(admision.getCodigo_sucursal());
			anexo2.setNro_ingreso(admision.getNro_ingreso());
			anexo2.setNro_documento(admision.getNro_identificacion());

			anexo2 = generalExtraService.consultar(anexo2);

			if (anexo2 != null) {
				// colocas todos los datos que necesitas de la admision
				His_triage his_triage = new His_triage();
				his_triage.setCodigo_empresa(admision.getCodigo_empresa());
				his_triage.setCodigo_sucursal(admision.getCodigo_sucursal());
				his_triage.setNro_ingreso(admision.getNro_ingreso());
				his_triage.setIdentificacion(admision.getNro_identificacion());

				Paciente paciente = admision.getPaciente();

				Administradora administradora = new Administradora();
				administradora.setCodigo_empresa(paciente.getCodigo_empresa());
				administradora
						.setCodigo_sucursal(paciente.getCodigo_sucursal());
				administradora.setCodigo(paciente.getCodigo_administradora());

				anexo2.setCodigo_paciente(paciente.getNro_identificacion());
				anexo2.setOrigen_general(admision.getCausa_externa());
				anexo2.setCodigo_administradora(paciente
						.getCodigo_administradora());
				anexo2.setAseguradora(administradora != null ? administradora
						.getNombre() : "");
				anexo2.setCobertura(paciente.getTipo_usuario());
				anexo2.setRemitido(admision.getPaciente_remitido());
				anexo2.setCie_p(impresion_diagnostica.getCie_principal());
				anexo2.setCie_1(impresion_diagnostica.getCie_relacionado1());
				anexo2.setCie_2(impresion_diagnostica.getCie_relacionado2());
				anexo2.setCie_3(impresion_diagnostica.getCie_relacionado3());
				anexo2.setRemitido(admision.getPaciente_remitido());

				// colocar todos los datos que necesitas de la historia clinica
				hisc_urgencia.setCodigo_empresa(admision.getCodigo_empresa());
				hisc_urgencia.setCodigo_sucursal(admision.getCodigo_sucursal());
				hisc_urgencia.setNro_ingreso(admision.getNro_ingreso());
				hisc_urgencia.setNro_identificacion(admision
						.getNro_identificacion());

				String usuarios_nombre = (String) datos.get("usuarios_nombre");
				String usuarios_rol = (String) datos.get("usuarios_rol");
				String usuarios_cel = (String) datos.get("usuarios_cel");

				anexo2.setNumero_atencion(hisc_urgencia.getNro_ingreso());
				anexo2.setFecha(admision.getFecha_atencion());
				anexo2.setFecha_ingreso(admision.getFecha_ingreso());
				anexo2.setMotivo_consulta(hisc_urgencia.getMotivo_consulta());
				anexo2.setCreacion_date(hisc_urgencia.getCreacion_date());
				anexo2.setUltimo_update(hisc_urgencia.getUltimo_update());
				anexo2.setCreacion_user(hisc_urgencia.getCreacion_user());
				anexo2.setUltimo_user(hisc_urgencia.getUltimo_user());
				anexo2.setNombre_reporta(usuarios_nombre);
				anexo2.setCel_reporta(usuarios_cel);

				if (usuarios_rol != null
						&& usuarios_rol.equals(IRoles.MEDICO_URGENCIAS)) {
					anexo2.setCargo_reporta("MEDICO URGENCIA");

				}
				// else if (usuarios_rol != null
				// && usuarios_rol
				// .equals(IRoles.AUXILIAR_ENFERMERIA_URGENCIA)) {
				// anexo2.setCargo_reporta("AUX. DE ENFERMERIA URGENCIAS");
				// } else if (usuarios_rol != null
				// && usuarios_rol.equals(IRoles.ADMISIONISTA_URGENCIAS)) {
				// anexo2.setCargo_reporta("ADMISIONISTA DE URGENCIAS");
				// } else if (usuarios_rol != null
				// && usuarios_rol.equals(IRoles.ENFERMERA_JEFE_URGENCIAS)) {
				// anexo2.setCargo_reporta("ENFERMERIA URGENCIA");
				// } else if (usuarios_rol != null
				// && usuarios_rol.equals(IRoles.ADMINISTRADOR)) {
				// anexo2.setCargo_reporta("ADMINISTRADOR");
				// }

				generalExtraService.actualizar(anexo2);
			} else {
				His_triage his_triage = new His_triage();
				his_triage.setCodigo_empresa(admision.getCodigo_empresa());
				his_triage.setCodigo_sucursal(admision.getCodigo_sucursal());
				his_triage.setNro_ingreso(admision.getNro_ingreso());
				his_triage.setIdentificacion(admision.getNro_identificacion());

				anexo2 = new Anexo2();
				anexo2.setCodigo_empresa(admision.getCodigo_empresa());
				anexo2.setCodigo_sucursal(admision.getCodigo_sucursal());
				anexo2.setNro_ingreso(admision.getNro_ingreso());
				anexo2.setNro_documento(admision.getNro_identificacion());
				anexo2.setTriage("1");

				// colocas todos los datos que necesitas de la admision
				Paciente paciente = admision.getPaciente();

				Administradora administradora = new Administradora();
				administradora.setCodigo_empresa(paciente.getCodigo_empresa());
				administradora
						.setCodigo_sucursal(paciente.getCodigo_sucursal());
				administradora.setCodigo(paciente.getCodigo_administradora());

				anexo2.setCodigo_paciente(paciente.getNro_identificacion());
				anexo2.setOrigen_general(admision.getCausa_externa());
				anexo2.setCodigo_administradora(paciente
						.getCodigo_administradora());
				anexo2.setAseguradora(administradora != null ? administradora
						.getNombre() : "");
				anexo2.setCobertura(paciente.getTipo_usuario());
				anexo2.setRemitido(admision.getPaciente_remitido());
				anexo2.setCie_p(impresion_diagnostica.getCie_principal());
				anexo2.setCie_1(impresion_diagnostica.getCie_relacionado1());
				anexo2.setCie_2(impresion_diagnostica.getCie_relacionado2());
				anexo2.setCie_3(impresion_diagnostica.getCie_relacionado3());
				anexo2.setRemitido(admision.getPaciente_remitido());

				// colocar todos los datos que necesitas de la historia clinica
				hisc_urgencia.setCodigo_empresa(admision.getCodigo_empresa());
				hisc_urgencia.setCodigo_sucursal(admision.getCodigo_sucursal());
				hisc_urgencia.setNro_ingreso(admision.getNro_ingreso());
				hisc_urgencia.setNro_identificacion(admision
						.getNro_identificacion());

				String usuarios_nombre = (String) datos.get("usuarios_nombre");
				String usuarios_rol = (String) datos.get("usuarios_rol");
				String usuarios_cel = (String) datos.get("usuarios_cel");

				anexo2.setNumero_atencion(hisc_urgencia.getNro_ingreso());
				anexo2.setFecha(hisc_urgencia.getFecha_ingreso());
				anexo2.setFecha_ingreso(hisc_urgencia.getFecha_ingreso());
				anexo2.setMotivo_consulta(hisc_urgencia.getMotivo_consulta());
				anexo2.setNombre_reporta(usuarios_nombre);
				anexo2.setCel_reporta(usuarios_cel);

				if (usuarios_rol != null
						&& usuarios_rol.equals(IRoles.MEDICO_URGENCIAS)) {
					anexo2.setCargo_reporta("MEDICO URGENCIA");

				}
				// else if (usuarios_rol != null
				// && usuarios_rol
				// .equals(IRoles.AUXILIAR_ENFERMERIA_URGENCIA)) {
				// anexo2.setCargo_reporta("AUX. DE ENFERMERIA URGENCIAS");
				// } else if (usuarios_rol != null
				// && usuarios_rol.equals(IRoles.ADMISIONISTA_URGENCIAS)) {
				// anexo2.setCargo_reporta("ADMISIONISTA DE URGENCIAS");
				// } else if (usuarios_rol != null
				// && usuarios_rol.equals(IRoles.ENFERMERA_JEFE_URGENCIAS)) {
				// anexo2.setCargo_reporta("ENFERMERIA URGENCIA");
				// } else if (usuarios_rol != null
				// && usuarios_rol.equals(IRoles.ADMINISTRADOR)) {
				// anexo2.setCargo_reporta("ADMINISTRADOR");
				// }

				generalExtraService.crear(anexo2);
			}

			actualizarVerificacionFurips(admision, impresion_diagnostica);
		} catch (ValidacionRunTimeException e) {
			if (file_url_partograma != null)
				file_url_partograma.delete();
			throw new ValidacionRunTimeException(e.getMessage(), e);
		} catch (Exception e) {
			if (file_url_partograma != null)
				file_url_partograma.delete();
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite actualizar los datos del furips cuando se atienda
	 * por urgencia
	 * 
	 * @author Luis Miguel Hernandez
	 * */
	private void actualizarVerificacionFurips(Admision admision,
			Impresion_diagnostica impresion_diagnostica) {
		if (admision.getCausa_externa().equals(
				ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRANSITO)
				|| admision
						.getCausa_externa()
						.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_EVENTO_CATASTROFICO)) {
			Furips2 furips2 = new Furips2();
			furips2.setCodigo_empresa(admision.getCodigo_empresa());
			furips2.setCodigo_sucursal(admision.getCodigo_sucursal());
			furips2.setNro_ingreso(admision.getNro_ingreso());
			furips2.setNro_identificacion(admision.getNro_identificacion());
			furips2 = furips2Dao.consultarPorParametros(furips2);
			if (furips2 != null) {
				furips2.setIx_codigo_diagnostico_ingreso(impresion_diagnostica
						.getCie_principal());
				furips2.setIx_otro_codigo_diagnostico_ingreso(impresion_diagnostica
						.getCie_relacionado2());
				furips2.setIx_otro_codigo_diagnostico_ingreso2(impresion_diagnostica
						.getCie_relacionado2());

				furips2.setIx_otro_codigo_diagnostico_egreso(impresion_diagnostica
						.getCie_relacionado1());
				furips2.setIx_otro_codigo_diagnostico_egreso2(impresion_diagnostica
						.getCie_relacionado1());
				furips2.setIx_codigo_diagnostico_egreso(impresion_diagnostica
						.getCie_principal());
				furips2Dao.actualizar(furips2);
			}
		}
	}

	public void crear(Hisc_urgencia hisc_urgencia) {
		try {
			if (consultar(hisc_urgencia) != null) {
				throw new HealthmanagerException(
						"Hisc_parto ya se encuentra en la base de datos");
			}
			hisc_urgenciaDao.crear(hisc_urgencia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Hisc_urgencia hisc_urgencia) {
		try {
			return hisc_urgenciaDao.actualizar(hisc_urgencia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Hisc_urgencia consultar(Hisc_urgencia hisc_urgencia) {
		try {
			return hisc_urgenciaDao.consultar(hisc_urgencia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Hisc_urgencia hisc_urgencia) {
		try {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica
					.setCodigo_empresa(hisc_urgencia.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(hisc_urgencia
					.getCodigo_sucursal());
			historia_clinica.setCodigo_historia(hisc_urgencia
					.getCodigo_historia());
			return generalExtraService.eliminar(historia_clinica);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Hisc_urgencia> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return hisc_urgenciaDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.hisc_urgenciaDao.existe(parameters);
	}

	public void crearConsulta(Object historiasConsultas, Admision admision,
			Impresion_diagnostica impresion_diagnostica, String tipo_urgencia)
			throws Exception {

		boolean facturar_paquete = false;

		if (tipo_urgencia.equals(IVias_ingreso.TIPO_URGENCIA_OBSTETRICA)) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", admision.getCodigo_empresa());
			parametros.put("codigo_sucursal", admision.getCodigo_sucursal());
			parametros.put("nro_identificacion",
					admision.getNro_identificacion());
			parametros.put("codigo_administradora",
					admision.getCodigo_administradora());

			List<Pacientes_contratos> listado_contratos = pacientes_contratosDao
					.listar(parametros);

			Contratos_paquetes contratos_paquetes_aux = null;

			for (Pacientes_contratos pacientes_contratos : listado_contratos) {
				Map<String, Object> parametros_paquetes = new HashMap<String, Object>();
				parametros_paquetes.put("codigo_empresa",
						pacientes_contratos.getCodigo_empresa());
				parametros_paquetes.put("codigo_sucursal",
						pacientes_contratos.getCodigo_sucursal());
				parametros_paquetes.put("id_plan",
						pacientes_contratos.getId_codigo());
				parametros_paquetes.put("codigo_administradora",
						pacientes_contratos.getCodigo_administradora());

				List<Contratos_paquetes> listado_paquetes = contratos_paquetesDao
						.listar(parametros_paquetes);

				for (Contratos_paquetes contratos_paquetes : listado_paquetes) {
					if (contratos_paquetes.getPaquetes_servicios()
							.getVia_ingreso()
							.equals(admision.getVia_ingreso())) {
						contratos_paquetes_aux = contratos_paquetes;
						break;
					}
				}

				if (contratos_paquetes_aux != null)
					break;

			}

			if (contratos_paquetes_aux != null) {
				Via_ingreso_consultas via_ingreso_consultas = ServiciosDisponiblesUtils
						.getVia_ingreso_consultas(admision);
				if (via_ingreso_consultas != null) {
					Paquetes_servicios paquetes_servicios = contratos_paquetes_aux
							.getPaquetes_servicios();
					Procedimientos procedimientos = new Procedimientos();
					procedimientos
							.setId_procedimiento(new Long(paquetes_servicios
									.getId_procedimiento_principal()));
					procedimientos = procedimientosDao
							.consultar(procedimientos);
					if (contratos_paquetes_aux
							.getPaquetes_servicios()
							.getId_procedimiento_principal()
							.equals(via_ingreso_consultas
									.getPro_consulta_primera())) {
						facturar_paquete = true;
					} else {
						Map<String, Object> parametros_cups = new HashMap<String, Object>();
						parametros_cups.put("codigo_empresa",
								contratos_paquetes_aux.getCodigo_empresa());
						parametros_cups.put("codigo_sucursal",
								contratos_paquetes_aux.getCodigo_sucursal());
						parametros_cups.put("codigo_administradora",
								contratos_paquetes_aux
										.getCodigo_administradora());
						parametros_cups.put("id_paquete",
								contratos_paquetes_aux.getId_paquete());
						List<Detalles_paquetes_servicios> listado_detalles = detalles_paquetes_serviciosDao
								.listar(parametros_cups);
						for (Detalles_paquetes_servicios detalles_paquetes_servicios : listado_detalles) {
							if (detalles_paquetes_servicios.getCodigo_detalle()
									.equals(via_ingreso_consultas
											.getPro_consulta_primera())) {
								facturar_paquete = true;
								break;
							}
						}
					}
				}
			}

			if (facturar_paquete) {
				guardarDatos_procedimiento_paquete(
						contratos_paquetes_aux.getPaquetes_servicios(),
						admision, admision.getPaciente());
			}
		}

		Hisc_urgencia hisc_urgencia = (Hisc_urgencia) historiasConsultas;
		Map map = new HashMap();
		map.put("codigo_empresa", hisc_urgencia.getCodigo_empresa());
		map.put("codigo_sucursal", hisc_urgencia.getCodigo_sucursal());
		map.put("nro_identificacion", hisc_urgencia.getNro_identificacion());
		map.put("nro_ingreso", hisc_urgencia.getNro_ingreso());
		map.put("codigo_prestador", hisc_urgencia.getCreacion_user());
		map.put("codigo_dx", impresion_diagnostica.getCie_principal());
		map.put("creacion_date", hisc_urgencia.getCreacion_date());
		map.put("ultimo_update", hisc_urgencia.getUltimo_update());
		map.put("creacion_user", hisc_urgencia.getCreacion_user());
		map.put("ultimo_user", hisc_urgencia.getUltimo_user());
		map.put("tipo_hc", "");
		map.put("fecha_ingreso", admision.getFecha_ingreso());
		map.put("impresion_diagnostica", impresion_diagnostica);
		map.put("admision", admision);
		if (facturar_paquete)
			map.put("valor_consulta_paquete", 0.0);
		ServiciosDisponiblesUtils.generarConsulta(map);

	}

	private void guardarDatos_procedimiento_paquete(
			Paquetes_servicios paquetes_servicios, Admision admision,
			Paciente paciente) {
		// log.info("facturando Procedimiento ordenando ===> "
		// + paquetes_servicios.getId_procedimiento_principal());

		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(paquetes_servicios
				.getId_procedimiento_principal()));
		procedimientos = procedimientosDao.consultar(procedimientos);

		Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
		datos_procedimiento.setCodigo_empresa(paquetes_servicios
				.getCodigo_empresa());
		datos_procedimiento.setCodigo_sucursal(paquetes_servicios
				.getCodigo_sucursal());
		datos_procedimiento.setCodigo_registro(null);

		datos_procedimiento.setTipo_identificacion(paciente
				.getTipo_identificacion());
		datos_procedimiento.setNro_identificacion(paciente
				.getNro_identificacion());
		datos_procedimiento.setCodigo_administradora(admision
				.getCodigo_administradora());
		datos_procedimiento.setId_plan(admision.getId_plan());
		datos_procedimiento.setCodigo_prestador(admision.getCodigo_medico());
		datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
		datos_procedimiento.setCodigo_procedimiento(paquetes_servicios
				.getId_procedimiento_principal()+"");
		datos_procedimiento.setFecha_procedimiento(new Timestamp(Calendar
				.getInstance().getTimeInMillis()));
		datos_procedimiento.setNumero_autorizacion("");
		datos_procedimiento.setValor_procedimiento(paquetes_servicios
				.getValor());
		datos_procedimiento.setUnidades(1);
		datos_procedimiento.setCopago(0.0);
		datos_procedimiento.setValor_neto(paquetes_servicios.getValor());

		datos_procedimiento
				.setAmbito_procedimiento(IDatosProcedimientos.AMBITO_REALIZACION);
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
		datos_procedimiento.setCodigo_programa("");

		datos_procedimiento.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		datos_procedimiento.setCreacion_user(admision.getCreacion_user());
		datos_procedimiento.setUltimo_user(admision.getCreacion_user());
		datos_procedimiento.setValor_real(paquetes_servicios.getValor());
		datos_procedimiento.setDescuento(0);
		datos_procedimiento.setIncremento(0);
		datos_procedimiento.setRealizado_en("");
		datos_procedimiento.setCodigo_cups(procedimientos.getCodigo_cups());
		datos_procedimiento.setCodigo_orden(null);

		Datos_procedimiento datos_procedimiento2 = datos_procedimientoService
				.consultar(datos_procedimiento);

		if (datos_procedimiento2 != null) {
			datos_procedimiento.setCodigo_registro(datos_procedimiento2
					.getCodigo_registro());
			datos_procedimientoService.actualizarRegistro(datos_procedimiento);
		} else {
			datos_procedimiento.setCodigo_registro(null);
			datos_procedimientoService.crear(datos_procedimiento);
		}
	}

	// private void crearHojaGasto(Admision admision,
	// List<Map<String, Object>> list, Hisc_urgencia hisc_urgencia) {
	// /* creamos la hoja de gastos */
	// Hoja_gastos hoja_gastos = new Hoja_gastos();
	// hoja_gastos.setCodigo_empresa(admision.getCodigo_empresa());
	// hoja_gastos.setCodigo_sucursal(admision.getCodigo_sucursal());
	// hoja_gastos.setNro_identificacion(admision.getNro_identificacion());
	// hoja_gastos.setNro_ingreso(admision.getNro_ingreso());
	//
	// String accion = "registrar";
	// Hoja_gastos hoja_gastosTemp = hoja_gastosService.consultar(hoja_gastos);
	// if (hoja_gastosTemp != null) {
	// accion = "modificar";
	// hoja_gastos = hoja_gastosTemp;
	// } else {
	// hoja_gastos.setCreacion_date(new Timestamp(new GregorianCalendar()
	// .getTimeInMillis()));
	// hoja_gastos.setCreacion_user(hisc_urgencia.getCreacion_user());
	// }
	// hoja_gastos.setUltimo_update(new Timestamp(new GregorianCalendar()
	// .getTimeInMillis()));
	// hoja_gastos.setUltimo_user(hisc_urgencia.getUltimo_user());
	//
	// /* guardamos hoja de gastos */
	// Map<String, Object> mapDetalle = new HashMap<String, Object>();
	// mapDetalle.put("accion", accion);
	// mapDetalle.put("listado_detalle", list);
	// mapDetalle.put("hoja_gasto", hoja_gastos);
	// mapDetalle.put("admision", admision);
	// mapDetalle.put("tipo", "01"); // 01 es medicamento
	// hoja_gastosService.guardar(mapDetalle);
	// }

}