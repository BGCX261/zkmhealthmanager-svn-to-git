package com.framework.modelo.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.framework.modelo.dao.GeneralDao;
import com.framework.modelo.service.GeneralService;
import com.framework.modelo.util.DaoUtil;

public class GeneralServiceImpl implements GeneralService, Serializable{

	private GeneralDao generalDao;

	@Override
	public <T> void crear(T dato) {
		String queryId = DaoUtil.getQueryId(dato) + DaoUtil.CREAR;
		generalDao.crear(queryId, dato);
	}

	@Override
	public void crear(String queryId, Object dato) {
		generalDao.crear(queryId, dato);
	}

	@Override
	public <T> int actualizar(T dato) {
		String queryId = DaoUtil.getQueryId(dato) + DaoUtil.ACTUALIZAR;
		return generalDao.actualizar(queryId, dato);
	}

	@Override
	public int actualizar(String queryId, Object dato) {
		return generalDao.actualizar(queryId, dato);
	}

	@Override
	public <T> T consultar(T dato) {
		String queryId = DaoUtil.getQueryId(dato) + DaoUtil.CONSULTAR;
		return generalDao.consultar(queryId, dato);
	}

	@Override
	public Object consultar(String queryId, Object dato) {
		return generalDao.consultar(queryId, dato);
	}

	@Override
	public <T> int eliminar(T dato) {
		String queryId = DaoUtil.getQueryId(dato) + DaoUtil.ELIMINAR;
		return generalDao.eliminar(queryId, dato);
	}

	@Override
	public int eliminar(String queryId, Object dato) {
		return generalDao.eliminar(queryId, dato);
	}

	@Override
	public <T> List<T> listar(Class<?> clase, Map<String, Object> parametros) {
		String queryId = DaoUtil.getQueryId(clase) + DaoUtil.LISTAR;
		return generalDao.listar(queryId, parametros);
	}

	@Override
	public <T> List<T> listar(String queryId, Map<String, Object> parametros) {
		return generalDao.listar(queryId, parametros);
	}

	@Override
	public Long totalizar(Class<?> clase, Map<String, Object> parametros) {
		String queryId = DaoUtil.getQueryId(clase) + DaoUtil.TOTALIZAR;
		return generalDao.totalizar(queryId, parametros);
	}

	@Override
	public Long totalizar(String queryId, Map<String, Object> parametros) {
		return generalDao.totalizar(queryId, parametros);
	}

	@Override
	public Boolean existe(Class<?> clase, Map<String, Object> parametros) {
		String queryId = DaoUtil.getQueryId(clase) + DaoUtil.EXISTE;
		return generalDao.existe(queryId, parametros);
	}

	@Override
	public Boolean existe(String queryId, Map<String, Object> parametros) {
		return generalDao.existe(queryId, parametros);
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

}
