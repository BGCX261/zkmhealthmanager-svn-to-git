package healthmanager.modelo.service;

import java.io.Serializable;

import com.framework.modelo.service.TransaccionalService;
import com.framework.modelo.util.ITransaccional;

public class TransaccionalExtraService implements TransaccionalService, Serializable {

    private GeneralExtraService generalExtraService;

    @Override
    public <T> T guardarProceso(ITransaccional iTransaccional) {
        return iTransaccional.onProceso(generalExtraService);
    }

    

}
