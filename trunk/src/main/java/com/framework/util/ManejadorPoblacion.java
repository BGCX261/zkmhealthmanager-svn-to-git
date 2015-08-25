package com.framework.util;

import healthmanager.controller.ZKWindow;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_plantilla_pyp;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.bean.Vacunas_pacientes;
import healthmanager.modelo.service.Datos_procedimientoService;
import healthmanager.modelo.service.Detalle_plantilla_pypService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Esquema_vacunacionService;
import healthmanager.modelo.service.Metas_pypService;
import healthmanager.modelo.service.Plantilla_pypService;
import healthmanager.modelo.service.Servicios_contratadosService;
import healthmanager.modelo.service.VacunasService;
import healthmanager.modelo.service.Vacunas_pacientesService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.constantes.IVias_ingreso;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.Res;

public class ManejadorPoblacion {

	// private static Logger log =
	// Logger.getLogger(Centro_serviciosAction.class);

	public static final String DETENCION_CANCER_SENO = "DETENCION_CANCER_SENO";
	public static final String SALUD_ORAL = "SALUD_ORAL";
	public static final String ALTERACION_MENOR_10_ANIOS = "ALTERACION_MENOR_10_ANIOS";
	public static final String AGUDEZA_VISUAL = "AGUDEZA_VISUAL";
	public static final String RECIEN_NACIDO = "RECIEN_NACIDO";
	public static final String DESARROLLO_JOVEN = "DESARROLLO_JOVEN";
	public static final String CANCER_CUELLO_UTERINO = "CANCER_CUELLO_UTERINO";
	public static final String PLANIFICACION_FAMILIAR = "PLANIFICACION_FAMILIAR";
	public static final String ADULTO_MAYOR = "ADULTO_MAYOR";

	/* constantes vacunacion */
	public static final String VACUNA_CONTRA_TUBERCULOSIS = "993102";
	public static final String VACUNA_CONTRA_HEPATITIS_B = "993503";
	public static final String VACUNA_CONTRA_POLIOMIELITIS = "993501";
	public static final String VACUNA_COMBINADA_CONTRA_Y_DIFTERIA = "993120";
	public static final String VACUNA_CONTRA_ROTAVIRUS = "993512";
	public static final String VACUNA_CONTRA_NEUMOCOCO = "993106";
	public static final String VACUNA_CONTRA_HAEMOPHILUS_INFLUENZAE_TIPO_B = "993104";
	public static final String VACUNA_COMBINADA_CONTRA_SARAMPION_PAROTIDITIS_RUBEOLA_TRIPLE_VIRAL = "993522";
	public static final String VACUNA_CONTRA_FIEBRE_AMARILLA = "993504";
	public static final String VACUNA_CONTRA_HEPATITIS_A = "993502";
	public static final String VACUNA_COMBINADA_CONTRA_SARAMPION_RUBEOLA_DOBLE_VIRAL = "993520";
	public static final String VACUNA_COMBINADA_CONTRA_DIFTERIA_TETANOS_FERINA = "993122";

	public static final String ACTIVIDADESA_PENDIENTES = "act_pen";
	public static final String ACTIVIDADESA_REALIZADAS = "act_res";

	public static List<String> obtenerListadoProgrmas(Paciente paciente) {
		List<String> listado_programas = new ArrayList<String>();

		int edad_meses = Integer.parseInt(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente
						.getFecha_nacimiento()), "2", false));

		int edad = Integer.parseInt(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente
						.getFecha_nacimiento()), "1", false));

		// validacion de salud oral
		if (edad_meses >= 6) {
			listado_programas.add(SALUD_ORAL);
		}

		// validacion de alteracion menor 10 años
		if (edad <= 10) {
			// log.info("Edad: " + edad);
			listado_programas.add(ALTERACION_MENOR_10_ANIOS);
		}

		// Poblacion: Adultos mayores de 45 años
		// Frecuencia: cada 5 años hasta los 100
		if (edad >= 45 && ((edad / 5d) + "").endsWith(".0") && edad <= 100) {
			listado_programas.add(ADULTO_MAYOR);
		}

		// validacion de agudeza visual
		if (edad == 4 || edad == 11 || edad == 16 || edad >= 45
				&& ((edad / 5d) + "").endsWith(".0")) {
			listado_programas.add(AGUDEZA_VISUAL);
		}

		// validacion de desarrollo joven
		if (edad >= 10 && edad <= 29) {
			listado_programas.add(DESARROLLO_JOVEN);
		}

		// validacion de recien nacido
		if (edad == 0) {
			int edad_dias = Integer
					.parseInt(Util.getEdad(
							new java.text.SimpleDateFormat("dd/MM/yyyy")
									.format(paciente.getFecha_nacimiento()),
							"3", false));
			if (edad_dias < 30) {
				listado_programas.add(RECIEN_NACIDO);
			}
		}

		if (paciente.getSexo().equals("F")) {
			// validacion de detencion de cancer de seno
			if (edad > 50) {
				listado_programas.add(DETENCION_CANCER_SENO);
			}
			// validacion cancer de cuello uterino
			if (edad >= 25 && edad <= 69) {
				listado_programas.add(CANCER_CUELLO_UTERINO);
			}
			// validacion planificacion familiar
			if (edad >= 10) {
				listado_programas.add(PLANIFICACION_FAMILIAR);
			}

		} else {
			// validacion planificacion familiar
			if (edad >= 12) {
				listado_programas.add(PLANIFICACION_FAMILIAR);
			}
		}

		return listado_programas;
	}

	public static boolean isAplicaPlanificacion(String sexo, int anio) {
		if (sexo.equals("F")) {
			if (anio >= 10 && anio <= 49) {
				return true;
			}
		} else {
			if (anio >= 12 && anio <= 49) {
				return true;
			}
		}
		return false;
	}

	public static List<Elemento> obtenerListadoProgramasPaciente(
			Paciente paciente, ZKWindow zkWindow) {
		List<Elemento> lista_programas = zkWindow.getServiceLocator()
				.getElementoService().listar("via_ingreso");
		List<Elemento> lista_progVerificados = new ArrayList<Elemento>();

		String sexo = paciente.getSexo();

		int edad = Integer.parseInt(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente
						.getFecha_nacimiento()), "1", false));

		int edad_meses = Integer.parseInt(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente
						.getFecha_nacimiento()), "2", false));

		for (Elemento programa : lista_programas) {
			if (programa.getCodigo().equals("1")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("2")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("3")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("4")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("6")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("7")) {
				if (edad_meses <= 2) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("8")) {
				if (edad_meses >= 2 && edad <= 2) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("9")) {
				if (edad >= 2 && edad <= 5) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("10")) {
				if (edad >= 5 && edad <= 10) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("11")) {
				if (edad >= 10 && sexo.equals("F")) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("12")) {
				if (edad >= 45) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("13")) {
				if (edad >= 10 && edad <= 29) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("14")) {
				if (edad >= 10) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("15")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("16")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("17")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("18")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("22")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("23")) {
				if (edad_meses <= 2) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("24")) {
				if (edad_meses >= 2 && edad <= 5) {
					lista_progVerificados.add(programa);
				}

			}
		}

		return lista_progVerificados;
	}

	public static List<Elemento> obtenerListadoProgramasPaciente(
			Paciente paciente, ElementoService elementoService) {
		List<Elemento> lista_programas = elementoService.listar("via_ingreso");
		List<Elemento> lista_progVerificados = new ArrayList<Elemento>();

		String sexo = paciente.getSexo();

		int edad = Integer.parseInt(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente
						.getFecha_nacimiento()), "1", false));

		int edad_meses = Integer.parseInt(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(paciente
						.getFecha_nacimiento()), "2", false));

		for (Elemento programa : lista_programas) {
			if (programa.getCodigo().equals("1")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("2")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("3")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("4")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("6")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("7")) {
				if (edad_meses <= 2) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("8")) {
				if (edad_meses >= 2 && edad <= 2) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("9")) {
				if (edad >= 2 && edad <= 5) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("10")) {
				if (edad >= 5 && edad <= 10) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("11")) {
				if (edad >= 10 && sexo.equals("F")) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("12")) {
				if (edad >= 45) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("13")) {
				if (edad >= 10 && edad <= 29) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("14")) {
				if (edad >= 10) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("15")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("16")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("17")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("18")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("22")) {
				lista_progVerificados.add(programa);
			} else if (programa.getCodigo().equals("23")) {
				if (edad_meses <= 2) {
					lista_progVerificados.add(programa);
				}
			} else if (programa.getCodigo().equals("24")) {
				if (edad_meses >= 2 && edad <= 5) {
					lista_progVerificados.add(programa);
				}

			}
		}

		return lista_progVerificados;
	}

	/**
	 * Este metodo me permite saber que vacunas, se puede colocar dicho paciente
	 * 
	 * @author Luis Miguel Hernandez
	 * @param paciente
	 * @param serviceLocator
	 * @param inCluirRealizadas
	 *            - True - mjuestra las que estan por hacer, verificando las
	 *            realizadas, False- devuelve todas sin importar las realizadas
	 * @return List<String> - lista de vacunas disponibles devueve los codigos
	 *         cups.
	 * */
	public static List<Esquema_vacunacion> getVacunasDisponibles(
			Paciente paciente, ServiceLocatorWeb serviceLocator) {
		List<Esquema_vacunacion> vacunas_disponibles = new ArrayList<Esquema_vacunacion>();

		Map<String, Integer> mapa_edades = Util.getEdadYYYYMMDD(paciente
				.getFecha_nacimiento());

		int anios = mapa_edades.get("anios");
		int meses = mapa_edades.get("meses");
		int dias = mapa_edades.get("dias");

		// if(meses > 12){
		// meses = meses / 12;
		// }

		// int dias = mapa_edades.get("dias");

		// log.info("años: " + anios + " meses: " + meses);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", paciente.getCodigo_empresa());
		map.put("codigo_sucursal", paciente.getCodigo_sucursal());
		List<Vacunas> vacunas = serviceLocator
				.getServicio(VacunasService.class).listar(map);
		// log.info("Vacunas: " + vacunas.size());
		for (Vacunas vacunasTemp : vacunas) {
			Map<String, Object> mapEquema = new HashMap<String, Object>();
			mapEquema.put("codigo_empresa", vacunasTemp.getCodigo_empresa());
			mapEquema.put("codigo_sucursal", vacunasTemp.getCodigo_sucursal());
			mapEquema.put("codigo_vacuna",
					vacunasTemp.getCodigo_procedimiento());
			List<Esquema_vacunacion> list_Esquema_vacunacions = serviceLocator
					.getServicio(Esquema_vacunacionService.class).listar(
							mapEquema);
			// //log.info("Vacunas: " + vacunasTemp.getCodigo_cups() + " " +
			// list_Esquema_vacunacions.size());
			for (Esquema_vacunacion esquema_vacunacion : list_Esquema_vacunacions) {
				int comparadorInicial = getComparador(dias, meses, anios,
						esquema_vacunacion.getUnidad_med_edad_inicial());
				int dia_inicial = getDias(esquema_vacunacion.getEdad_inicial(),
						esquema_vacunacion.getUnidad_med_edad_inicial());
				int dia_final = getDias(esquema_vacunacion.getEdad_final(),
						esquema_vacunacion.getUnidad_med_edad_final());
				boolean aplica = false;
				if (esquema_vacunacion.getDiferenciador().equals("<")) {
					if (comparadorInicial < esquema_vacunacion
							.getEdad_inicial().intValue()) {
						aplica = true;
					}
				} else if (esquema_vacunacion.getDiferenciador().equals("<=")) {
					if (comparadorInicial <= esquema_vacunacion
							.getEdad_inicial().intValue()) {
						aplica = true;
					}
				} else if (esquema_vacunacion.getDiferenciador().equals("==")) {
					if (comparadorInicial == esquema_vacunacion
							.getEdad_inicial().intValue()) {
						aplica = true;
					}
				} else if (esquema_vacunacion.getDiferenciador().equals("><")) {
					if (dia_inicial > dias && dias < dia_final) {
						aplica = true;
					}
				} else if (esquema_vacunacion.getDiferenciador().equals("=><=")) {
					if (dia_inicial >= dias && dias <= dia_final) {
						aplica = true;
					}
				} else if (esquema_vacunacion.getDiferenciador().equals(">=")) {
					if (comparadorInicial >= esquema_vacunacion
							.getEdad_inicial().intValue()) {
						aplica = true;
					}
				} else if (esquema_vacunacion.getDiferenciador().equals(">")) {
					if (comparadorInicial > esquema_vacunacion
							.getEdad_inicial().intValue()) {
						aplica = true;
					}
				}

				if (aplica) {
					// log.info("Aplica");
					esquema_vacunacion.setVacunas(vacunasTemp);
					vacunas_disponibles.add(esquema_vacunacion);
					break;
				}
			}

		}
		return vacunas_disponibles;
	}

	public static List<Esquema_vacunacion> getVacunasPendientesNoRealizadas(
			Paciente paciente, ServiceLocatorWeb serviceLocator) {
		List<Esquema_vacunacion> vacunasPermitidas = ManejadorPoblacion
				.getVacunasDisponibles(paciente, serviceLocator);
		List<Esquema_vacunacion> vacunasPendientes = new ArrayList<Esquema_vacunacion>();
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
					vacunasPendientes.add(vacunas);
				}
			}
		}
		return vacunasPendientes;
	}

	public static List<Esquema_vacunacion> getListEsquema_vacunacions(
			Sucursal sucursal, ServiceLocatorWeb serviceLocator) {
		List<Esquema_vacunacion> vacunas_disponibles = new ArrayList<Esquema_vacunacion>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", sucursal.getCodigo_empresa());
		map.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		List<Vacunas> vacunas = serviceLocator
				.getServicio(VacunasService.class).listar(map);
		// log.info("Vacunas: " + vacunas.size());
		for (Vacunas vacunasTemp : vacunas) {
			Map<String, Object> mapEquema = new HashMap<String, Object>();
			mapEquema.put("codigo_empresa", vacunasTemp.getCodigo_empresa());
			mapEquema.put("codigo_sucursal", vacunasTemp.getCodigo_sucursal());
			mapEquema.put("codigo_vacuna",
					vacunasTemp.getCodigo_procedimiento());
			List<Esquema_vacunacion> list_Esquema_vacunacions = serviceLocator
					.getServicio(Esquema_vacunacionService.class).listar(
							mapEquema);
			// log.info("Vacunas: " + vacunasTemp.getCodigo_procedimiento() +
			// " "
			// + list_Esquema_vacunacions.size());
			for (Esquema_vacunacion esquema_vacunacion : list_Esquema_vacunacions) {
				esquema_vacunacion.setVacunas(vacunasTemp);
				vacunas_disponibles.add(esquema_vacunacion);
			}
		}
		return vacunas_disponibles;
	}

	public static List<Esquema_vacunacion> getVacunasDisponibles(
			Paciente paciente, List<Esquema_vacunacion> listEsquema_vacunacions) {
		Map<String, Integer> mapa_edades = Util.getEdadYYYYMMDD(paciente
				.getFecha_nacimiento());

		int anios = mapa_edades.get("anios");
		int meses = mapa_edades.get("meses");
		int dias = mapa_edades.get("dias");

		List<Esquema_vacunacion> vacunas_disponibles = new ArrayList<Esquema_vacunacion>();
		for (Esquema_vacunacion esquema_vacunacion : listEsquema_vacunacions) {
			int comparadorInicial = getComparador(dias, meses, anios,
					esquema_vacunacion.getUnidad_med_edad_inicial());
			int dia_inicial = getDias(esquema_vacunacion.getEdad_inicial(),
					esquema_vacunacion.getUnidad_med_edad_inicial());
			int dia_final = getDias(esquema_vacunacion.getEdad_final(),
					esquema_vacunacion.getUnidad_med_edad_final());
			boolean aplica = false;
			if (esquema_vacunacion.getDiferenciador().equals("<")) {
				if (comparadorInicial < esquema_vacunacion.getEdad_inicial()
						.intValue()) {
					aplica = true;
				}
			} else if (esquema_vacunacion.getDiferenciador().equals("<=")) {
				if (comparadorInicial <= esquema_vacunacion.getEdad_inicial()
						.intValue()) {
					aplica = true;
				}
			} else if (esquema_vacunacion.getDiferenciador().equals("==")) {
				if (comparadorInicial == esquema_vacunacion.getEdad_inicial()
						.intValue()) {
					aplica = true;
				}
			} else if (esquema_vacunacion.getDiferenciador().equals("><")) {
				if (dia_inicial > dias && dias < dia_final) {
					aplica = true;
				}
			} else if (esquema_vacunacion.getDiferenciador().equals("=><=")) {
				if (dia_inicial >= dias && dias <= dia_final) {
					aplica = true;
				}
			} else if (esquema_vacunacion.getDiferenciador().equals(">=")) {
				if (comparadorInicial >= esquema_vacunacion.getEdad_inicial()
						.intValue()) {
					aplica = true;
				}
			} else if (esquema_vacunacion.getDiferenciador().equals(">")) {
				if (comparadorInicial > esquema_vacunacion.getEdad_inicial()
						.intValue()) {
					aplica = true;
				}
			}

			if (aplica) {
				// log.info("Aplica");
				vacunas_disponibles.add(esquema_vacunacion);
				break;
			}
		}
		return vacunas_disponibles;
	}

	private static int getComparador(int dias, int meses, int anios,
			String unidad_med_edad_inicial) {
		if (unidad_med_edad_inicial.equals("01")) {
			return dias;
		} else if (unidad_med_edad_inicial.equals("02")) {
			return meses;
		} else {
			return anios;
		}
	}

	public static int getDias(Integer edad_inicial,
			String unidad_med_edad_inicial) {
		if (unidad_med_edad_inicial.equals("01")) {
			return edad_inicial;
		} else if (unidad_med_edad_inicial.equals("02")) {
			return edad_inicial * 30;
		} else {
			return edad_inicial * 365;
		}
	}

	public static String getDiasDescripcion(String unidad_med_edad_inicial,
			Integer cantidad) {
		if (cantidad == null)
			cantidad = 0;
		if (unidad_med_edad_inicial.equals("01")) {
			return cantidad == 1 ? "dia" : "días";
		} else if (unidad_med_edad_inicial.equals("02")) {
			return cantidad == 1 ? "mes" : "meses";
		} else {
			return cantidad == 1 ? "año" : "años";
		}
	}

	/**
	 * Este metodo me permite saber si se puede volver a colocar esa vacuna
	 * 
	 * @author Luis Miguel Hernandez Perez
	 * @param vacunas
	 * @param vacunas_pacientes
	 * @param paciente
	 * */
	public static boolean sePuedeColocarLaVacuna(Esquema_vacunacion vacunas,
			List<Vacunas_pacientes> vacunas_pacientes, Paciente paciente) {
		Map<String, Integer> mapa_edades = Util.getEdadYYYYMMDD(paciente
				.getFecha_nacimiento());

		int anios = mapa_edades.get("anios");
		int meses = mapa_edades.get("meses");
		if (vacunas.getCodigo_vacuna().equals(VACUNA_CONTRA_TUBERCULOSIS)
				|| vacunas.getCodigo_vacuna().equals(VACUNA_CONTRA_HEPATITIS_B)) {
			if (yaSecolocaLaVacuna(null, null, vacunas_pacientes, vacunas))
				return false;
		} else if (vacunas.getCodigo_vacuna().equals(
				VACUNA_CONTRA_POLIOMIELITIS)
				|| vacunas.getCodigo_vacuna().equals(
						VACUNA_COMBINADA_CONTRA_DIFTERIA_TETANOS_FERINA)
				|| vacunas.getCodigo_vacuna().equals(
						VACUNA_CONTRA_HAEMOPHILUS_INFLUENZAE_TIPO_B)) {

			if (yaSecolocaLaVacuna(anios, meses, vacunas_pacientes, vacunas)
					|| yaSecolocaLaVacuna(anios, null, vacunas_pacientes,
							vacunas))
				return false;
		} else if (vacunas.getCodigo_vacuna().equals(VACUNA_CONTRA_ROTAVIRUS)
				|| vacunas.getCodigo_vacuna().equals(VACUNA_CONTRA_NEUMOCOCO)) {
			if (yaSecolocaLaVacuna(anios, meses, vacunas_pacientes, vacunas))
				return false;
		} else if (vacunas
				.getCodigo_vacuna()
				.equals(VACUNA_COMBINADA_CONTRA_SARAMPION_PAROTIDITIS_RUBEOLA_TRIPLE_VIRAL)
				|| vacunas.getCodigo_vacuna().equals(
						VACUNA_CONTRA_FIEBRE_AMARILLA)
				|| vacunas.getCodigo_vacuna().equals(VACUNA_CONTRA_HEPATITIS_A)) {
			if (yaSecolocaLaVacuna(anios, null, vacunas_pacientes, vacunas))
				return false;
		}
		return true;
	}

	/**
	 * Este metodo me permite saber si ya se coloco esa vacuna
	 * 
	 * @author Luis Miguel
	 * @param anio
	 * @param mes
	 * @param vacunas_pacientes
	 * @param vacunas
	 * */
	public static boolean yaSecolocaLaVacuna(Integer anio, Integer mes,
			List<Vacunas_pacientes> vacunas_pacientes,
			Esquema_vacunacion vacunas) {
		for (Vacunas_pacientes vacunas_pacientesTemp : vacunas_pacientes) {
			if (vacunas_pacientesTemp.getCodigo_procedimiento_vacuna().equals(
					vacunas.getCodigo_vacuna())) {
				boolean b_anio = true;
				if (anio != null
						&& anio == vacunas_pacientesTemp.getAnio().intValue()) {
					b_anio = false;
				}

				boolean b_mes = true;
				if (mes != null
						&& mes == vacunas_pacientesTemp.getMeses().intValue()) {
					b_mes = false;
				}
				return b_anio && b_mes;
			}
		}
		return false;
	}

	public static boolean isPacienteTieneContratadoPyP(Paciente paciente) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", paciente.getCodigo_empresa());
		map.put("codigo_sucursal", paciente.getCodigo_sucursal());
		map.put("nro_identificacion", paciente.getNro_identificacion());
		map.put("servicio", ServiciosDisponiblesUtils.CODSER_PYP);
		return ServiciosDisponiblesUtils.getServiceLocator()
				.getServicio(Servicios_contratadosService.class)
				.getTieneContratoServicio(map);
	}

	/**
	 * Este metodo me permite saber cuales son las actividades pendientes que
	 * tiene este paciente
	 * 
	 * @author Luis Miguel
	 * */
	public static List<Plantilla_pyp> getActividadesPendientesPorRealizar(
			Paciente paciente) {
		List<Plantilla_pyp> plantilla_pyps = new ArrayList<Plantilla_pyp>();
		if (isPacienteTieneContratadoPyP(paciente)) {
			List<String> programas = obtenerListadoProgrmas(paciente);
			for (String programa : programas) {
				// Verificamos si ademas de que aplica ese sprograma lo tenga
				// contratado
				String codigo_servicio = getIdServicio(programa);
				if (!codigo_servicio.trim().isEmpty()
						&& ServiciosDisponiblesUtils.getServicioContratado(
								codigo_servicio, paciente)) {
					Map<String, List<Plantilla_pyp>> map = getActividadesPendientes(
							paciente, codigo_servicio);
					List<Plantilla_pyp> plantilla_pypsActividadesPendientes = map
							.get(ACTIVIDADESA_PENDIENTES);
					// //log.info("Actividades pendientes: " +
					// plantilla_pypsActividadesPendientes.size());
					// //log.info("Actividades realizadas: " +
					// map.get(ACTIVIDADESA_REALIZADAS).size());
					if (!plantilla_pypsActividadesPendientes.isEmpty()) {
						plantilla_pyps
								.addAll(plantilla_pypsActividadesPendientes);
					}
				}
			}
		}
		return plantilla_pyps;
	}

	/**
	 * Este metodo me permite, obtener las actividades pendientes de un paciente
	 * relacionado con un servicio
	 * 
	 * @author Luis Miguel
	 * */
	public static Map<String, List<Plantilla_pyp>> getActividadesPendientes(
			Paciente paciente, String id_servicio) {
		ServiceLocatorWeb serviceLocator = ServiciosDisponiblesUtils
				.getServiceLocator();
		List<Plantilla_pyp> plantilla_pypsPendientes = new ArrayList<Plantilla_pyp>();
		List<Plantilla_pyp> plantilla_pypsRealizadas = new ArrayList<Plantilla_pyp>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area_intervencion", id_servicio);
		map.put("sexo", paciente.getSexo());
		// map.put("fecha_nacimiento_paciente", paciente.getFecha_nacimiento());
		// Consultamos las validaciones de la actividades de PyP
		List<Detalle_plantilla_pyp> list_Plantilla_pyps = serviceLocator
				.getServicio(Detalle_plantilla_pypService.class).listar(map);
		for (Detalle_plantilla_pyp detalle_plantilla_pyp : list_Plantilla_pyps) {
			// Obtenemos la actividad de PyP
			Plantilla_pyp plantilla_pyp = new Plantilla_pyp();
			plantilla_pyp.setId(detalle_plantilla_pyp.getId_actividad());
			plantilla_pyp = serviceLocator.getServicio(
					Plantilla_pypService.class).consultar(plantilla_pyp);
			if (plantilla_pyp != null) {
				// La fecha
				// Relacionar si la edad del paciente cumple.
				// con las validaciones de PyP
				Map<String, Integer> mapFecha = Util.getEdadYYYYMMDD(paciente
						.getFecha_nacimiento());
				int anios = mapFecha.get("anios");
				int dias = anios > 0 ? anios * 365 : mapFecha.get("dias");
				int dias_inicial = getDias(
						detalle_plantilla_pyp.getEdad_inicial(),
						detalle_plantilla_pyp.getUnidad_med_edad_inicial());
				int dias_final = getDias(detalle_plantilla_pyp.getEdad_final(),
						detalle_plantilla_pyp.getUnidad_med_edad_final());
				if (dias_inicial <= dias && dias <= dias_final) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("codigo_empresa", paciente.getCodigo_empresa());
					params.put("codigo_sucursal", paciente.getCodigo_sucursal());
					params.put("codigo_activiad", plantilla_pyp.getCodigo_pdc());
					params.put("nro_identificacion",
							paciente.getNro_identificacion());
					// Consultamos la ultima fecha cuando se realizo ese
					// procedimiento
					Timestamp timestamp = serviceLocator.getServicio(
							Metas_pypService.class)
							.getUltimaFechaRealizacionActividad(params);
					if (timestamp != null) {
						int dias_frecuencia = getDias(
								detalle_plantilla_pyp.getTiempo(),
								detalle_plantilla_pyp.getUnidad_tiempo());
						Map<String, Integer> mapFechaRealizacio = Util
								.getEdadYYYYMMDD(timestamp);
						int diasFechaRealizacion = mapFechaRealizacio
								.get("dias");
						int respuesta_realizacion = diasFechaRealizacion
								- dias_frecuencia;

						// Fecha para cuando se puede volver a realizar
						Calendar calendar = Calendar.getInstance();
						calendar.set(Calendar.DAY_OF_MONTH,
								calendar.get(Calendar.DAY_OF_MONTH)
										+ dias_frecuencia);
						Date fecha_puede_realizar = calendar.getTime();

						if (respuesta_realizacion > 0) {
							if (!existe(plantilla_pyp, plantilla_pypsPendientes)) {
								plantilla_pyp.setPendiente(true);
								plantilla_pyp
										.setFecha_volver_realizar(fecha_puede_realizar);
								plantilla_pypsPendientes.add(plantilla_pyp);
							}
						} else {// actividad realizada
							if (!existe(plantilla_pyp, plantilla_pypsRealizadas)) {
								plantilla_pyp.setPendiente(false);
								plantilla_pyp
										.setFecha_volver_realizar(fecha_puede_realizar);
								plantilla_pypsRealizadas.add(plantilla_pyp);
							}
						}
					} else {
						if (!existe(plantilla_pyp, plantilla_pypsPendientes)) {
							plantilla_pyp.setPendiente(true);
							plantilla_pypsPendientes.add(plantilla_pyp);
						}
					}
				}
			}
		}
		Map<String, List<Plantilla_pyp>> mapPlantillasPyP = new HashMap<String, List<Plantilla_pyp>>();
		mapPlantillasPyP.put(ACTIVIDADESA_PENDIENTES, plantilla_pypsPendientes);
		mapPlantillasPyP.put(ACTIVIDADESA_REALIZADAS, plantilla_pypsRealizadas);
		return mapPlantillasPyP;
	}

	public static boolean existe(Plantilla_pyp plantilla_pyp,
			List<Plantilla_pyp> plantilla_pyps) {
		for (Plantilla_pyp plantilla_pyp2 : plantilla_pyps) {
			if (plantilla_pyp2.getId().intValue() == plantilla_pyp.getId()
					.intValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Este metodo me permite, cual es el codigo del programa de PyP.
	 * */
	private static String getIdServicio(String programa) {
		if (programa.equals(DETENCION_CANCER_SENO)) {
			return ServiciosDisponiblesUtils.CODSER_PYP_CANCER_SENO;
		} else if (programa.equals(SALUD_ORAL)) {
			return ServiciosDisponiblesUtils.CODSER_PYP_SALUD_ORAL;
		} else if (programa.equals(ALTERACION_MENOR_10_ANIOS)) {
			return ServiciosDisponiblesUtils.CODSER_PYP_CRECIMIENTO_DESARROLLO;
		} else if (programa.equals(AGUDEZA_VISUAL)) {
			return ServiciosDisponiblesUtils.CODSER_PYP_AGUDEZA_VISUAL;
		} else if (programa.equals(RECIEN_NACIDO)) {
			return ServiciosDisponiblesUtils.CODSER_PYP_RECIEN_NACIDO;
		} else if (programa.equals(DESARROLLO_JOVEN)) {
			return ServiciosDisponiblesUtils.CODSER_PYP_DESARROLLO_JOVEN;
		} else if (programa.equals(CANCER_CUELLO_UTERINO)) {
			return ServiciosDisponiblesUtils.CODSER_PYP_CUELLO_UTERINO;
		} else if (programa.equals(PLANIFICACION_FAMILIAR)) {
			return ServiciosDisponiblesUtils.CODSER_PYP_PLANIFICACION_MUJERES;
		} else if (programa.equals(ADULTO_MAYOR)) {
			return ServiciosDisponiblesUtils.CODSER_PYP_ADULTO_MAYOR;
		}
		return "";
	}

	public static boolean validarFrecuenciaOrdenamiento(String codigo_cups,
			int frecuencia_orden, String nombre_procedimiento,
			Admision admision_original, ServiceLocatorWeb locator) {

		Admision admision = Res.clonar(admision_original);
		if (admision.getVia_ingreso().equals(IVias_ingreso.ODONTOLOGIA2)
				|| admision.getVia_ingreso().equals(IVias_ingreso.ODONTOLOGIA)) {
			admision.setVia_ingreso(IVias_ingreso.SALUD_ORAL);
		}
		/*
		 * Verificamos la frecuencia cuando sea PyP
		 */
		String servicio = "";
		try {
			String servicios[] = ServiciosDisponiblesUtils
					.getServicios(admision);
			if (servicios != null) {
				servicio = servicios[0];
			}
		} catch (ValidacionRunTimeException e) {
		} catch (Exception e) {
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("area_intervencion", servicio);
		map.put("codigo_pdc", codigo_cups);
		boolean es_una_actividad_pyp = locator.getServicio(
				Plantilla_pypService.class).existe(map);
		if (es_una_actividad_pyp) {
			map = new HashMap<String, Object>();
			map.put("area_intervencion", servicio);
			map.put("codigo_pdc", codigo_cups);

			// log.info("Parametros: " + map);
			int frecuencia_pyp = 0;
			List<Detalle_plantilla_pyp> list_Plantilla_pyps = locator
					.getServicio(Detalle_plantilla_pypService.class)
					.listar(map);

			// log.info("Listado de plantilla: " + list_Plantilla_pyps.size());
			// Caluculamos edad del paciente
			Map<String, Integer> mapFecha = Util.getEdadYYYYMMDD(admision
					.getPaciente().getFecha_nacimiento());
			int anios = mapFecha.get("anios");
			int dias = mapFecha.get("dias");
			int meses = mapFecha.get("meses");
			if (anios > 0) {
				dias = anios * 365;
			} else if (meses > 0) {
				dias = meses * 30;
			}

			boolean esta_en_rango = false;
			StringBuilder stringBuilderInformaicionDetalle = new StringBuilder(
					"Este procedimiento es permitido para pacientes con edades:\n");
			for (Detalle_plantilla_pyp detalle_plantilla_pyp : list_Plantilla_pyps) {
				int dias_inicial = ManejadorPoblacion.getDias(
						detalle_plantilla_pyp.getEdad_inicial(),
						detalle_plantilla_pyp.getUnidad_med_edad_inicial());
				int dias_final = ManejadorPoblacion.getDias(
						detalle_plantilla_pyp.getEdad_final(),
						detalle_plantilla_pyp.getUnidad_med_edad_final());
				if (dias_inicial <= dias && dias <= dias_final) {
					frecuencia_pyp = ManejadorPoblacion.getDias(
							detalle_plantilla_pyp.getTiempo(),
							detalle_plantilla_pyp.getUnidad_tiempo());
					esta_en_rango = true;
				}
				// Adicionar para informacion
				String descipcion_edad_inicial = "";
				String descipcion_edad_final = "";
				if (detalle_plantilla_pyp.getUnidad_med_edad_inicial().equals(
						detalle_plantilla_pyp.getUnidad_med_edad_inicial())) {
					descipcion_edad_final = getDiasDescripcion(
							detalle_plantilla_pyp.getUnidad_med_edad_inicial(),
							detalle_plantilla_pyp.getEdad_final());
				} else {
					descipcion_edad_inicial = getDiasDescripcion(
							detalle_plantilla_pyp.getUnidad_med_edad_inicial(),
							detalle_plantilla_pyp.getEdad_inicial());
					descipcion_edad_final = getDiasDescripcion(
							detalle_plantilla_pyp.getUnidad_med_edad_inicial(),
							detalle_plantilla_pyp.getEdad_final());
				}
				stringBuilderInformaicionDetalle.append("De "
						+ detalle_plantilla_pyp.getEdad_inicial() + " "
						+ descipcion_edad_inicial + " a "
						+ detalle_plantilla_pyp.getEdad_final() + " "
						+ descipcion_edad_final + "\n");
			}

			if (!list_Plantilla_pyps.isEmpty()) {
				if (!esta_en_rango) {
					MensajesUtil.mensajeAlerta("Advertencia", ""
							+ stringBuilderInformaicionDetalle.toString());
				}
				if (frecuencia_pyp > 0) {
					// log.info("Frecuencia procedimiento: " + codigo_cups +
					// " : "
					// + frecuencia_pyp);
					frecuencia_orden = frecuencia_pyp;
				}
			}
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codigo_empresa", admision.getCodigo_empresa());
		param.put("codigo_sucursal", admision.getCodigo_sucursal());
		param.put("nro_identificacion", admision.getNro_identificacion());
		param.put("codigo_cups", codigo_cups);

		Timestamp fecha_ultimo_procedimiento = locator.getServicio(
				Datos_procedimientoService.class).getUltimoProcedimiento(param);

		if (fecha_ultimo_procedimiento != null) {
			// log.info("Fecha de ultimo procedimiento: "
			// + fecha_ultimo_procedimiento);
			// En esta parte verificamos
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) - frecuencia_orden);

			Timestamp fecha_fecuencia = new Timestamp(calendar.getTime()
					.getTime());
			if (fecha_fecuencia.compareTo(fecha_ultimo_procedimiento) < 0) {
				// String tienpo_faltante =
				// Util.getEdadPrsentacion(fecha_fecuencia,
				// fecha_ultimo_procedimiento);
				int tienpo_faltante = Util.getDiasDiferencia(fecha_fecuencia,
						fecha_ultimo_procedimiento);

				Calendar calendarProsimo = Calendar.getInstance();
				calendarProsimo.set(Calendar.DAY_OF_MONTH,
						calendarProsimo.get(Calendar.DAY_OF_MONTH)
								+ tienpo_faltante);
				String fecha = new SimpleDateFormat("yyyy-MM-dd")
						.format(calendarProsimo.getTime());

				String informacion_procedimiento = "Para ordenar el procedimiento \n"
						+ codigo_cups
						+ " - "
						+ nombre_procedimiento
						+ "\nDebe tener en cuenta:"
						+ "\n * Fecha ultima realizacion ("
						+ new SimpleDateFormat("yyyy-MM-dd")
								.format(fecha_ultimo_procedimiento)
						+ ")"
						+ "\n * Puede ordenarse apartir del ("
						+ fecha
						+ ")"
						+ "\n * Frecuencia de orden "
						+ frecuencia_orden
						+ " días";
				// if (viaIngresoUtilizafrecuencia()) {
				MensajesUtil.mensajeAlerta("Advertencia",
						informacion_procedimiento);
				// return false;
				// } else {
				// Notificaciones.mostrarNotificacionAlerta("Advertencia",
				// informacion_procedimiento, 2000);
				// }
			}
		}
		return true;
	}
}
