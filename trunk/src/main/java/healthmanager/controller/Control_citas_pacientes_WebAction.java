package healthmanager.controller;

import healthmanager.modelo.bean.Citas_web;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.Citas_webService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;

public class Control_citas_pacientes_WebAction  extends ZKWindow{
	
//	private static Logger log = Logger.getLogger(Control_citas_pacientes_WebAction.class);
	
	@View private Textbox tbxNro_identificacion;
	@View private Intbox tbxAnio;
	@View private Grid gridResultado;
	@View private Rows rowsResultado;

	@Override
	public void init() throws Exception {
		
	}
	

	public void listarCitas()throws Exception{
		try {			
			Map map = new HashMap();
			map.put("codigo_empresa", codigo_empresa);
			map.put("codigo_sucursal", codigo_sucursal);
			map.put("nro_identificacion", paciente_session.getNro_identificacion());
			map.put("anio",tbxAnio.getValue());
			map.put("estado","1");
			
			List<Citas_web> listaCitas = getServiceLocator().getServicio(Citas_webService.class).listar(map);
			rowsResultado.getChildren().clear();
			for (Citas_web citas : listaCitas) {
				rowsResultado.appendChild(crearFilas(citas, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		final Citas_web citas = (Citas_web) objeto;
		
		String fecha_cita = new SimpleDateFormat("yyyy-MM-dd").
		format(citas.getFecha_cita());
		
		Elemento elementoEstado = citas.getElementoEstado();
	
		Paciente paciente = citas.getPaciente();
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(citas.getCodigo_cita()));
		fila.appendChild(new Label(citas.getNro_identificacion()));
		fila.appendChild(new Label((paciente!=null?paciente.getNombreCompleto():"")));
		fila.appendChild(new Label(fecha_cita));
		fila.appendChild(new Label(citas.getHora()));
		fila.appendChild(new Label((elementoEstado!=null?elementoEstado.getDescripcion():"")));
		
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				crearDetalle_cita(citas);
			}
		});
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		
		return fila;
		
	}

	protected void crearDetalle_cita(Citas_web citas) {
		Map<String, Object> parameters = new HashMap();
		Component componente = Executions.createComponents(
				"/pages/detalleCitaWeb.zul", this, parameters);
		final Detalle_cita_webAction window = (Detalle_cita_webAction) componente;
		window.setMode("modal");
		window.cargarCita(citas.getCodigo_cita());
		
	}
	
	public void cerrarWindow(){
		if(this.getParent() instanceof Citas_corregidoAction){
			Citas_corregidoAction action = (Citas_corregidoAction) this
			.getParent();
			action.cargarCalendarioCitas();
			this.detach();
		}
	}
	
}
