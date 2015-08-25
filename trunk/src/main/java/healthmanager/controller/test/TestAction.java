package healthmanager.controller.test;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Diente;
import healthmanager.modelo.service.DienteService;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Image;

import com.framework.macros.odontograma.util.DienteDibujadorUtil;

public class TestAction extends ZKWindow{

	@Override
	public void init() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nro_historia", "0000000108"); 
		List<Diente> dientes = getServiceLocator().getServicio(DienteService.class).listar(map);
		for (Diente diente2 : dientes) {
			BufferedImage inputStream = DienteDibujadorUtil.convertirBufferedImage(diente2); 
			if(inputStream != null){
				Image image = new Image();
				image.setContent(inputStream); 
				appendChild(image); 
			}
		}
	}

}
