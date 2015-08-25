package com.framework.modelo.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.framework.modelo.dao.GeneralDao;

public class GeneralDaoImpl extends SqlSessionDaoSupport implements GeneralDao,
		Serializable {

	@Override
	public void crear(String queryId, Object dato) {
		getSqlSession().insert(queryId, dato);
	}

	@Override
	public int actualizar(String queryId, Object dato) {
		return getSqlSession().update(queryId, dato);
	}

	@Override
	public int eliminar(String queryId, Object dato) {
		return getSqlSession().delete(queryId, dato);
	}

	@Override
	public <T> T consultar(String queryId, T dato) {
		return getSqlSession().selectOne(queryId, dato);
	}

	@Override
	public <T> List<T> listar(String queryId, Map<String, Object> parametros) {
		return getSqlSession().selectList(queryId, parametros);
	}

	@Override
	public Long totalizar(String queryId, Map<String, Object> parametros) {
		return getSqlSession().selectOne(queryId, parametros);
	}

	@Override
	public Boolean existe(String queryId, Map<String, Object> parametros) {
		return getSqlSession().selectOne(queryId, parametros);
	}

}
