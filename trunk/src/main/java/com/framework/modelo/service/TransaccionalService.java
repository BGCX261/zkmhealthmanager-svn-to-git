package com.framework.modelo.service;

import com.framework.modelo.util.ITransaccional;

public interface TransaccionalService {
	<T> T guardarProceso(ITransaccional iTransaccional);
}
