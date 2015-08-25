package com.framework.modelo.service.impl;

import java.io.Serializable;

import com.framework.modelo.service.GeneralService;
import com.framework.modelo.service.TransaccionalService;
import com.framework.modelo.util.ITransaccional;

public class TransaccionalServiceImpl implements TransaccionalService, Serializable {

    private GeneralService generalService;

    @Override
    public <T> T guardarProceso(ITransaccional iTransaccional) {
        return (T) (new Object());
    }

    public GeneralService getGeneralService() {
        return generalService;
    }

    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

}
