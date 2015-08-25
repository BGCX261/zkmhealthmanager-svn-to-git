/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.framework.res;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Anio_cuota_moderadora;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Salario_anual;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.service.Afiliaciones_meService;
import healthmanager.modelo.service.Anio_cuota_moderadoraService;
import healthmanager.modelo.service.Aportes_cotizacionesService;
import healthmanager.modelo.service.Salario_anualService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.framework.constantes.IConstantes;
import com.framework.locator.ServiceLocatorWeb;

/**
 *
 * @author Idadyou
 */
public class ResCalculadorDeRangoCuota {

	public static Anio_cuota_moderadora getGrupo(Paciente paciente,
			ServiceLocatorWeb daoFactory, String anio, Integer mes_i,
			Sucursal sucursal) throws Exception {
		/* verificamos que tipo de usuario es? */

		Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
		afiliaciones_me.setCodigo_empresa(paciente.getCodigo_empresa());
		afiliaciones_me.setCodigo_sucursal(paciente.getCodigo_sucursal());
		afiliaciones_me.setNro_identificacion_afiliado(paciente
				.getNro_identificacion());
		Afiliaciones_meService afiliaciones_meDao = daoFactory
				.getAfiliacionesMeService();
		afiliaciones_me = (Afiliaciones_me) afiliaciones_meDao
				.consultar(afiliaciones_me);

		/* verificamos este salario anual */
		Salario_anual salario_anual = new Salario_anual();
		salario_anual.setCodigo_empresa(paciente.getCodigo_empresa());
		salario_anual.setCodigo_sucursal(paciente.getCodigo_sucursal());
		salario_anual.setAnio(anio);
		Salario_anualService salario_anualDao = daoFactory
				.getSalarioAnualService();
		salario_anual = (Salario_anual) salario_anualDao
				.consultar(salario_anual);

		boolean utiliza_afiliaciones = sucursal.getTipo().equals(
				IConstantes.TIPOS_SUCURSAL_CAJA_PREV);

		if (afiliaciones_me != null && utiliza_afiliaciones) {
			if (!afiliaciones_me.getTipo_afiliado().equals("C")) {
				afiliaciones_me.setNro_identificacion_afiliado(afiliaciones_me
						.getNro_identificacion_cotizante());
				Afiliaciones_me afiliaciones_meCotizante = (Afiliaciones_me) afiliaciones_meDao
						.consultar(afiliaciones_me);
				if (afiliaciones_meCotizante != null) {
					afiliaciones_me = afiliaciones_meCotizante;
				}
			}

			if (mes_i == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, Integer.parseInt(anio));
				// Tomamos el mes anterior
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
				mes_i = (calendar.get(Calendar.MONTH) + 1);
			}
			String mes = "" + (mes_i <= 9 ? ("0" + mes_i) : mes_i);
			// aportes_cotizaciones.setMes(mes);

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", paciente.getCodigo_empresa());
			param.put("codigo_sucursal", paciente.getCodigo_sucursal());
			param.put("nro_identificacion",
					afiliaciones_me.getNro_identificacion_afiliado());
			param.put("mes", mes);
			param.put("anio", anio);
			param.put("look", "ibc"); // este es el que devuelve el valor
			param.put("estado", "S");

			Double aporte_mensual = daoFactory.getAportesCotizacionesService()
					.getIbcOrCotizacion(param);
			if (aporte_mensual == null) {
				aporte_mensual = 0d;
			}

			/* relacion ibc */
			if (afiliaciones_me.getIdentificacion_ibc() != null) {
				if (!afiliaciones_me.getIdentificacion_ibc().isEmpty()) {
					param.put("nro_identificacion",
							afiliaciones_me.getIdentificacion_ibc());
					Double valor_realcion_ibc = daoFactory
							.getAportesCotizacionesService()
							.getIbcOrCotizacion(param);
					if (valor_realcion_ibc == null) {
						valor_realcion_ibc = 0d;
					}
					if (aporte_mensual.doubleValue() > valor_realcion_ibc
							.doubleValue()) {
						aporte_mensual = valor_realcion_ibc;
					}
				}
			}

			if (salario_anual == null) {
				throw new ValidacionRunTimeException(
						"Para esta opcion Debe configurar los salirios del año "
								+ anio);
			}

			double valor_anual = salario_anual.getValor_activos();
			if (isActivo(afiliaciones_me))
				valor_anual = salario_anual.getValor_pensionado();

			double grupo = aporte_mensual / valor_anual;
			System.out.println("::- Grupo valor: " + grupo);
			return getRange(grupo, daoFactory, paciente.getCodigo_empresa(),
					anio);
		} else {
			// if(sucursal.getTipo().equals(SucursalAction.EPS)){
			// throw new
			// ValidacionRunTimeException("Para esta opcion el Paciente debe estar Afiliado");
			// }else{
			if (salario_anual == null) {
				throw new ValidacionRunTimeException(
						"Para esta opcion Debe configurar los salirios del año "
								+ anio);
			}
			double valor_anual = salario_anual.getValor_activos();
			double grupo = paciente.getIngresos() / valor_anual;
			return getRange(grupo, daoFactory, paciente.getCodigo_empresa(),
					anio);
			// }
		}

	}

	public static Anio_cuota_moderadora getGrupo(Paciente paciente,
			Afiliaciones_meService afiliaciones_meService,
			Salario_anualService salario_anualService,
			Aportes_cotizacionesService aportes_cotizacionesService,
			Anio_cuota_moderadoraService anio_cuota_moderadoraService,
			String anio, Integer mes_i, Sucursal sucursal) throws Exception {
		/* verificamos que tipo de usuario es? */

		Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
		afiliaciones_me.setCodigo_empresa(paciente.getCodigo_empresa());
		afiliaciones_me.setCodigo_sucursal(paciente.getCodigo_sucursal());
		afiliaciones_me.setNro_identificacion_afiliado(paciente
				.getNro_identificacion());
		afiliaciones_me = afiliaciones_meService.consultar(afiliaciones_me);

		/* verificamos este salario anual */
		Salario_anual salario_anual = new Salario_anual();
		salario_anual.setCodigo_empresa(paciente.getCodigo_empresa());
		salario_anual.setCodigo_sucursal(paciente.getCodigo_sucursal());
		salario_anual.setAnio(anio);

		salario_anual = salario_anualService.consultar(salario_anual);

		boolean utiliza_afiliaciones = sucursal.getTipo().equals(
				IConstantes.TIPOS_SUCURSAL_CAJA_PREV);

		if (afiliaciones_me != null && utiliza_afiliaciones) {
			if (!afiliaciones_me.getTipo_afiliado().equals("C")) {
				afiliaciones_me.setNro_identificacion_afiliado(afiliaciones_me
						.getNro_identificacion_cotizante());
				Afiliaciones_me afiliaciones_meCotizante = afiliaciones_meService
						.consultar(afiliaciones_me);
				if (afiliaciones_meCotizante != null) {
					afiliaciones_me = afiliaciones_meCotizante;
				}
			}

			if (mes_i == null) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, Integer.parseInt(anio));
				// Tomamos el mes anterior
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
				mes_i = (calendar.get(Calendar.MONTH) + 1);
			}
			String mes = "" + (mes_i <= 9 ? ("0" + mes_i) : mes_i);
			// aportes_cotizaciones.setMes(mes);

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", paciente.getCodigo_empresa());
			param.put("codigo_sucursal", paciente.getCodigo_sucursal());
			param.put("nro_identificacion",
					afiliaciones_me.getNro_identificacion_afiliado());
			param.put("mes", mes);
			param.put("anio", anio);
			param.put("look", "ibc"); // este es el que devuelve el valor
			param.put("estado", "S");

			Double aporte_mensual = aportes_cotizacionesService
					.getIbcOrCotizacion(param);
			if (aporte_mensual == null) {
				aporte_mensual = 0d;
			}

			/* relacion ibc */
			if (afiliaciones_me.getIdentificacion_ibc() != null) {
				if (!afiliaciones_me.getIdentificacion_ibc().isEmpty()) {
					param.put("nro_identificacion",
							afiliaciones_me.getIdentificacion_ibc());
					Double valor_realcion_ibc = aportes_cotizacionesService
							.getIbcOrCotizacion(param);
					if (valor_realcion_ibc == null) {
						valor_realcion_ibc = 0d;
					}
					if (aporte_mensual.doubleValue() > valor_realcion_ibc
							.doubleValue()) {
						aporte_mensual = valor_realcion_ibc;
					}
				}
			}

			if (salario_anual == null) {
				throw new ValidacionRunTimeException(
						"Para esta opcion Debe configurar los salirios del año "
								+ anio);
			}

			double valor_anual = salario_anual.getValor_activos();
			if (isActivo(afiliaciones_me))
				valor_anual = salario_anual.getValor_pensionado();

			double grupo = aporte_mensual / valor_anual;
			System.out.println("::- Grupo valor: " + grupo);
			return getRange(grupo, anio_cuota_moderadoraService, paciente.getCodigo_empresa(), anio);
		} else {
			// if(sucursal.getTipo().equals(SucursalAction.EPS)){
			// throw new
			// ValidacionRunTimeException("Para esta opcion el Paciente debe estar Afiliado");
			// }else{
			if (salario_anual == null) {
				throw new ValidacionRunTimeException(
						"Para esta opcion Debe configurar los salirios del año "
								+ anio);
			}
			double valor_anual = salario_anual.getValor_activos();
			double grupo = paciente.getIngresos() / valor_anual;
			return getRange(grupo, anio_cuota_moderadoraService, paciente.getCodigo_empresa(), anio);
			// }
		}

	}

	public static Anio_cuota_moderadora getGrupo(Paciente paciente,
			ServiceLocatorWeb daoFactory, String anio, Sucursal sucursal)
			throws Exception {
		if (sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
			Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
			afiliaciones_me.setCodigo_empresa(paciente.getCodigo_empresa());
			afiliaciones_me.setCodigo_sucursal(paciente.getCodigo_sucursal());
			afiliaciones_me.setNro_identificacion_afiliado(paciente
					.getNro_identificacion());
			Afiliaciones_meService afiliaciones_meDao = daoFactory
					.getAfiliacionesMeService();
			afiliaciones_me = (Afiliaciones_me) afiliaciones_meDao
					.consultar(afiliaciones_me);

			double cuota_moderadora = 0d;
			double porcentaje_copago = 0d;
			if (afiliaciones_me != null) {
				if (!afiliaciones_me.getTipo_afiliado().equals("C")) {
					afiliaciones_me
							.setNro_identificacion_afiliado(afiliaciones_me
									.getNro_identificacion_cotizante());
					Afiliaciones_me afiliaciones_meCotizante = (Afiliaciones_me) afiliaciones_meDao
							.consultar(afiliaciones_me);
					if (afiliaciones_meCotizante != null) {
						afiliaciones_me = afiliaciones_meCotizante;
					}
				}
				cuota_moderadora = afiliaciones_me.getCuota_moderadora();
				porcentaje_copago = afiliaciones_me.getPorcentaje_copago();
			}

			// Consulta en la informacion del afil
			Anio_cuota_moderadora anio_cuota_moderadora = new Anio_cuota_moderadora();
			anio_cuota_moderadora.setPorcentaje_copago(porcentaje_copago);
			anio_cuota_moderadora.setValor_cuota(cuota_moderadora);
			return anio_cuota_moderadora;
		} else {
			return getGrupo(paciente, daoFactory, anio, null, sucursal);
		}
	}

	public static Anio_cuota_moderadora getGrupo(Paciente paciente,
			Afiliaciones_meService afiliaciones_meService,
			Salario_anualService salario_anualService,
			Aportes_cotizacionesService aportes_cotizacionesService,
			Anio_cuota_moderadoraService anio_cuota_moderadoraService,
			String anio, Sucursal sucursal) throws Exception {
		if (sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
			Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
			afiliaciones_me.setCodigo_empresa(paciente.getCodigo_empresa());
			afiliaciones_me.setCodigo_sucursal(paciente.getCodigo_sucursal());
			afiliaciones_me.setNro_identificacion_afiliado(paciente
					.getNro_identificacion());

			afiliaciones_me = afiliaciones_meService.consultar(afiliaciones_me);

			double cuota_moderadora = 0d;
			double porcentaje_copago = 0d;
			if (afiliaciones_me != null) {
				if (!afiliaciones_me.getTipo_afiliado().equals("C")) {
					afiliaciones_me
							.setNro_identificacion_afiliado(afiliaciones_me
									.getNro_identificacion_cotizante());
					Afiliaciones_me afiliaciones_meCotizante = afiliaciones_meService
							.consultar(afiliaciones_me);
					if (afiliaciones_meCotizante != null) {
						afiliaciones_me = afiliaciones_meCotizante;
					}
				}
				cuota_moderadora = afiliaciones_me.getCuota_moderadora();
				porcentaje_copago = afiliaciones_me.getPorcentaje_copago();
			}

			// Consulta en la informacion del afil
			Anio_cuota_moderadora anio_cuota_moderadora = new Anio_cuota_moderadora();
			anio_cuota_moderadora.setPorcentaje_copago(porcentaje_copago);
			anio_cuota_moderadora.setValor_cuota(cuota_moderadora);
			return anio_cuota_moderadora;
		} else {
			return getGrupo(paciente, afiliaciones_meService, salario_anualService,
					aportes_cotizacionesService,anio_cuota_moderadoraService, anio, null, sucursal);
		}
	}

	private static Anio_cuota_moderadora getRange(double rango,
			ServiceLocatorWeb daoFactory, String codigo_empresa, String anio)
			throws Exception {
		Anio_cuota_moderadoraService anio_cuota_moderadoraService = daoFactory
				.getAnio_cuota_moderadoraService();
		Anio_cuota_moderadora cuota_moderadora = new Anio_cuota_moderadora();
		cuota_moderadora.setCodigo_empresa(codigo_empresa);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", "" + codigo_empresa);
		map.put("anio", anio);

		List<Anio_cuota_moderadora> list = anio_cuota_moderadoraService
				.listar(map);

		System.out.println("::- size cuota: " + list.size());

		for (Anio_cuota_moderadora cuota_moderadora_temp : (List<Anio_cuota_moderadora>) list) {
			double n1 = cuota_moderadora_temp.getNivel1();
			double n2 = cuota_moderadora_temp.getNivel2();

			System.out.println("::- Nivel1 : " + n1);
			System.out.println("::- Nivel2 : " + n2);
			System.out.println("::- rango : " + rango);

			boolean acepted = false;
			if (cuota_moderadora_temp.getTipo_nivel().equals("1")) {
				if (rango < n1) {
					acepted = true;
				}
			} else if (cuota_moderadora_temp.getTipo_nivel().equals("2")) {
				if (n1 <= rango && rango <= n2) {
					acepted = true;
				}
			} else if (cuota_moderadora_temp.getTipo_nivel().equals("3")) {
				if (rango > n1) {
					acepted = true;
				}
			}
			if (acepted)
				return cuota_moderadora_temp;
		}
		throw new ValidacionRunTimeException("No esta en un rango especifico.");
	}

	private static Anio_cuota_moderadora getRange(double rango,
			Anio_cuota_moderadoraService anio_cuota_moderadoraService,
			String codigo_empresa, String anio) throws Exception {
		Anio_cuota_moderadora cuota_moderadora = new Anio_cuota_moderadora();
		cuota_moderadora.setCodigo_empresa(codigo_empresa);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", "" + codigo_empresa);
		map.put("anio", anio);

		List<Anio_cuota_moderadora> list = anio_cuota_moderadoraService
				.listar(map);

		System.out.println("::- size cuota: " + list.size());

		for (Anio_cuota_moderadora cuota_moderadora_temp : (List<Anio_cuota_moderadora>) list) {
			double n1 = cuota_moderadora_temp.getNivel1();
			double n2 = cuota_moderadora_temp.getNivel2();

			System.out.println("::- Nivel1 : " + n1);
			System.out.println("::- Nivel2 : " + n2);
			System.out.println("::- rango : " + rango);

			boolean acepted = false;
			if (cuota_moderadora_temp.getTipo_nivel().equals("1")) {
				if (rango < n1) {
					acepted = true;
				}
			} else if (cuota_moderadora_temp.getTipo_nivel().equals("2")) {
				if (n1 <= rango && rango <= n2) {
					acepted = true;
				}
			} else if (cuota_moderadora_temp.getTipo_nivel().equals("3")) {
				if (rango > n1) {
					acepted = true;
				}
			}
			if (acepted)
				return cuota_moderadora_temp;
		}
		throw new ValidacionRunTimeException("No esta en un rango especifico.");
	}

	private static boolean isActivo(Afiliaciones_me afiliaciones_me)
			throws Exception {
		if (afiliaciones_me.getTipo_empleado() != null) {
			return !(afiliaciones_me.getTipo_empleado().equalsIgnoreCase("J") || afiliaciones_me
					.getTipo_empleado().equalsIgnoreCase("S"));
		}
		throw new ValidacionRunTimeException(
				"Este afiliado debe de tener un tipo de afiliado");
	}
}
