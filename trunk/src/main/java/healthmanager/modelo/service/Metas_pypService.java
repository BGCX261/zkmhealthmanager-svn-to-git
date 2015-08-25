/*
 * Metas_pypServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Metas_pyp;
import healthmanager.modelo.dao.Metas_pypDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.dao.ArticuloDao;

@Service("metas_pypService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Metas_pypService implements Serializable{

	public static Logger log = Logger.getLogger(Metas_pypService.class);

	@Autowired
	private Metas_pypDao metas_pypDao;

	@Autowired
	private ArticuloDao articuloDao;
	private String limit;

	public void crear(Metas_pyp metas_pyp) {
		try {
			if (consultar(metas_pyp) != null) {
				throw new HealthmanagerException(
						"Metas_pyp ya se encuentra en la base de datos");
			}
			metas_pypDao.crear(metas_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Metas_pyp metas_pyp) {
		try {
			return metas_pypDao.actualizar(metas_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Metas_pyp consultar(Metas_pyp metas_pyp) {
		try {
			return metas_pypDao.consultar(metas_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Metas_pyp metas_pyp) {
		try {
			return metas_pypDao.eliminar(metas_pyp);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Metas_pyp> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return metas_pypDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite saber que metas se realizaron del tipo de
	 * contrato, y en que mes y que anio
	 * 
	 * @author Luis Miguel
	 * @param metas_pyp
	 * @param codigo_meta
	 * @param finalidad
	 * @return metas realizadas
	 * */
	public int getMetasRealizadas(Metas_pyp metas_pyp, String codigo_meta,
			Timestamp fecha_inicio, Timestamp fecha_final, String finalidad) {
		List<String> codigos_metas = Utilidades.validarCodigo(codigo_meta);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fecha_inicio", fecha_inicio);
		map.put("fecha_final", fecha_final);
		map.put("codigo_empresa", metas_pyp.getCodigo_empresa());
		map.put("codigo_sucursal", metas_pyp.getCodigo_sucursal());
		map.put("codigo_administradora", metas_pyp.getCodigo_administradora());
		map.put("id_contrato", metas_pyp.getId_plan());
//		String mts = "";
//		for (String string : codigos_metas) {
//			mts += string + " ";
//		}
		// log.info("Codigo meta: " + codigo_meta + " " + mts);
		int meta_realizada = 0;
		for (String codigo_meTemp : codigos_metas) {
			if (isMedicamento(codigo_meTemp)) {
				try {
					Articulo articulo = new Articulo();
					articulo.setCodigo_empresa(metas_pyp.getCodigo_empresa());
					articulo.setCodigo_sucursal(metas_pyp.getCodigo_sucursal());
					articulo.setReferencia(codigo_meTemp);
					if (articuloDao.consultarPorReferencia(articulo)) {
						String codigo_medicamento = articulo
								.getCodigo_articulo();
						map.put("codigo_datos", "medicamento");
						map.put("nombre_tabla", "medicamentos");
						map.put("referencia_pyp", codigo_medicamento.trim());
						meta_realizada += metas_pypDao
								.getCantidadMetasArticulos(map);

						/* datos servicios */
						map.put("codigo_datos", "servicio");
						map.put("nombre_tabla", "servicio");
						meta_realizada += metas_pypDao
								.getCantidadMetasArticulos(map);
					}
				} catch (Exception e) {
				}
			} else {

				// log.info("is Procedimiento");
				map.put("codigo_datos", "consulta");
				map.put("codigo_procedimiento", codigo_meTemp.trim());
				map.put("finalidad", finalidad);
//				if (codigo_meTemp.equals("997103")) {
//					// log.info(map);
//				}

				meta_realizada += metas_pypDao
						.getCantidadMetasProcedimiento(map);
				// log.info("valor encontrado " + meta_realizada);
				/* procedimiento */
				map.put("codigo_datos", "procedimiento");
				map.put("finalidad", null);
				meta_realizada += metas_pypDao
						.getCantidadMetasProcedimiento(map);
				// log.info("valor encontrado: " + meta_realizada);
			}
		}
		return meta_realizada;
	}

	private boolean isMedicamento(String codigo_meta) {
		return !codigo_meta.matches("[0-9]*$");
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return metas_pypDao.existe(parameters);
	}

	public void guardarMetas(List<Metas_pyp> metas_pyps, Contratos contratos) {
		boolean metas_encontradas = false;
		try {
			Metas_pyp metas_pyp = new Metas_pyp();
			metas_pyp.setCodigo_empresa(contratos.getCodigo_empresa());
			metas_pyp.setCodigo_sucursal(contratos.getCodigo_sucursal());
			metas_pyp.setCodigo_administradora(contratos
					.getCodigo_administradora());
			metas_pyp.setId_plan(contratos.getId_plan());
			metas_pypDao.eliminarDesdeContrato(metas_pyp);
			// log.info("Metas eliminadas: " + eliminados);

			for (Metas_pyp metas_pypTemp : metas_pyps) {
				if (metas_pypTemp.getMetas() != null
						&& !metas_pypTemp.getMetas().trim().isEmpty()) {
					metas_encontradas = true;
					if (metas_pypDao.consultar(metas_pypTemp) != null) {
						metas_pypDao.actualizar(metas_pypTemp);
					} else {
						metas_pypDao.crear(metas_pypTemp);
					}
				} else {
					metas_pypDao.eliminar(metas_pypTemp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HealthmanagerException(e.getMessage(), e);
		}
		if (!metas_encontradas) {
			throw new ValidacionRunTimeException(
					"Para realizar esta accion por lo menos debe ingresar una meta de pyp");
		}
	}

	public Timestamp getUltimaFechaRealizacionActividad(
			Map<String, Object> params) {
		String codigo_activiad = (String) params.get("codigo_activiad");
		String codigo_empresa = (String) params.get("codigo_empresa");
		String codigo_sucursal = (String) params.get("codigo_sucursal");
		String nro_identificacion = (String) params.get("nro_identificacion");

		List<String> codigos_actividades = Utilidades
				.validarCodigo(codigo_activiad);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		map.put("nro_identificacion", nro_identificacion);
		// String mts = "";
		// for (String string : codigos_metas) {
		// mts += string + " ";
		// }
		List<Timestamp> timestampsFecha = new ArrayList<Timestamp>();
//		int meta_realizada = 0;
		for (String codigo_meTemp : codigos_actividades) {
			if (isMedicamento(codigo_meTemp)) {
				try {
					Articulo articulo = new Articulo();
					articulo.setCodigo_empresa(codigo_empresa);
					articulo.setCodigo_sucursal(codigo_sucursal);
					articulo.setReferencia(codigo_meTemp);
					if (articuloDao.consultarPorReferencia(articulo)) {
						String codigo_medicamento = articulo
								.getCodigo_articulo();
						map.put("codigo_datos", "medicamento");
						map.put("nombre_tabla", "medicamentos");
						map.put("referencia_pyp", codigo_medicamento.trim());
						Timestamp timestamp = metas_pypDao
								.getUltimaFechaArticulos(map);
						if (timestamp != null) {
							timestampsFecha.add(timestamp);
						}

						/* datos servicios */
						map.put("codigo_datos", "servicio");
						map.put("nombre_tabla", "servicio");
						timestamp = metas_pypDao.getUltimaFechaArticulos(map);
						if (timestamp != null) {
							timestampsFecha.add(timestamp);
						}
					}
				} catch (Exception e) {
				}
			} else {

				// log.info("is Procedimiento");
				map.put("codigo_datos", "consulta");
				map.put("codigo_procedimiento", codigo_meTemp.trim());

				Timestamp timestamp = metas_pypDao
						.getUltimaFechaProcedimiento(map);
				if (timestamp != null) {
					timestampsFecha.add(timestamp);
				}
				// log.info("valor encontrado " + meta_realizada);
				/* procedimiento */
				map.put("codigo_datos", "procedimiento");
				map.put("finalidad", null);
				timestamp = metas_pypDao.getUltimaFechaProcedimiento(map);
				// log.info("valor encontrado: " + meta_realizada);
				if (timestamp != null) {
					timestampsFecha.add(timestamp);
				}
			}
		}
		return getMayor(timestampsFecha);
	}

	private Timestamp getMayor(List<Timestamp> timestampsFecha) {
		for (int i = 0; i < timestampsFecha.size(); i++) {
			for (int j = 0; j < timestampsFecha.size(); j++) {
				if (timestampsFecha.get(i).after(timestampsFecha.get(j))) {
					Timestamp aux = timestampsFecha.get(i);
					timestampsFecha.set(i, timestampsFecha.get(j));
					timestampsFecha.set(j, aux);
				}
			}
		}
		if (timestampsFecha.size() > 0) {
			return timestampsFecha.get(0);
		} else
			return null;
	}

}