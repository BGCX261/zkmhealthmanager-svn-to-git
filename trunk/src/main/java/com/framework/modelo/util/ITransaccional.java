package com.framework.modelo.util;

import healthmanager.modelo.service.GeneralExtraService;

public interface ITransaccional {

    <T> T onProceso(GeneralExtraService generalExtraService);
}
