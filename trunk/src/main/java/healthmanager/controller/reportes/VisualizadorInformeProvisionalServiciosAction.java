package healthmanager.controller.reportes;

import healthmanager.controller.ZKWindow;

import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import com.framework.macros.informe_provisional.TabInformeProvisionalMacro;
import com.framework.macros.informe_provisional.TabInformeProvisionalMacro.ITipoServicio;
import com.framework.util.MensajesUtil;

public class VisualizadorInformeProvisionalServiciosAction extends ZKWindow {

	@View
	private Tabs tab_contenedora;
	@View
	private Tabpanels tabpanelcontenedora;
	@View
	private Div dvContenedor;
	
	@View private Label lbTitulo;
	
	public static final String PARAM_PARAMETROS = "PPS";
	private Map<String, Object> map_param;

	@Override
	public void params(Map<String, Object> map) {
		map_param = map;
	}

	@Override
	public void init() throws Exception {
		inicializarPestaniasVisualizadoras();
	}

	/**
	 * Este metodo me permite especificar que quiere consultar el usuario
	 * 
	 * @author Luis Miguel
	 * */
	private void inicializarPestaniasVisualizadoras() {
		boolean param_consulta = (Boolean) map_param.get("param_chk_consulta");
		boolean param_procedimiento = (Boolean) map_param
				.get("param_chk_procedimiento");
		boolean param_medicamento_insumo = (Boolean) map_param
				.get("param_chk_medicamento_insumo");
		boolean param_estancias = (Boolean) map_param
				.get("param_chk_estancias");
		boolean param_triage = (Boolean) map_param.get("param_chk_triage");
		boolean param_chk_sin_triage = (Boolean) map_param
				.get("param_chk_sin_triage");
		
		String titulo = (String) map_param.get("param_titulo_ayuda");
		
		lbTitulo.setValue(" > " + titulo);
		
		boolean inicializada = false;

		if (param_consulta
				&& montar(!inicializada, ITipoServicio.DATOS_CONSULTA)) {
			inicializada = true;
		}

		if (param_procedimiento
				&& montar(!inicializada, ITipoServicio.DATOS_PROCEDIMIENTO)) {
			inicializada = true;
		}

		if (param_medicamento_insumo
				&& montar(!inicializada, ITipoServicio.MEDICAMENTOS_INSUMOS)) {
			inicializada = true;
		}

		if (param_estancias && montar(!inicializada, ITipoServicio.ESTANCIAS)) {
			inicializada = true;
		}

		if (param_triage && montar(!inicializada, ITipoServicio.TRIAGE)) {
			inicializada = true;
		}

		if (param_chk_sin_triage
				&& montar(!inicializada, ITipoServicio.SIN_TRIAGE)) {
            inicializada = true;
		}

		if (!inicializada) {
			MensajesUtil
					.mensajeAlerta("Advertencia",
							"El sistema no encontró ninguna informacion con respecto a los filtros");
			addEventListener(Events.ON_CREATE, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					detach();
				}
			});
		}
	}

	/**
	 * Este metodo me permite montar la informacion en la pagina
	 **/
	private boolean montar(boolean inicializada, ITipoServicio tipo) {
		TabInformeProvisionalMacro informeProvisionalMacro = new TabInformeProvisionalMacro(
				inicializada, tipo, map_param, tabpanelcontenedora,
				dvContenedor);
		if (inicializada) {
			if (informeProvisionalMacro.contieneInformacion()) {
				tab_contenedora.appendChild(informeProvisionalMacro);
				return true;
			} else {
				return false;
			}
		} else {
			tab_contenedora.appendChild(informeProvisionalMacro);
			return true;
		}
	}
}
