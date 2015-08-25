package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Test_figura_humana;
import healthmanager.modelo.bean.Test_puntuacion_figura_humana;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Test_figura_humanaService;
import healthmanager.modelo.service.Test_puntuacion_figura_humanaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;


public class FiguraHumanaMacro extends Grid implements AfterCompose {
	
	private Intbox ibx_total_puntuacion;
	private Textbox tbx_edad_mental;
	private Textbox tbxMensajeEdadMental;
	private Rows rowsRes;
	private Rows rowsResultadosAnteriores;
	private Row rowAnteriores;
	
	private ZKWindow zkWindow;
	private List<Test_puntuacion_figura_humana> puntos = new ArrayList<Test_puntuacion_figura_humana>();
	private List<Test_figura_humana> datos =  new ArrayList<Test_figura_humana>();
	private Map<String,Checkbox> checks = new HashMap<String,Checkbox>();
	
	private String prefijoComponente = "chkResultado"; 
	private Integer edadMeses;
		
	@Override
	public void afterCompose() {
		cargarComponentes();
		tbx_edad_mental.setText("Edad no disponible");
		ibx_total_puntuacion.setValue(0);
	}
	
	private void cargarComponentes(){
		ibx_total_puntuacion = (Intbox)this.getFellow("ibx_total_puntuacion");
		tbx_edad_mental = (Textbox)this.getFellow("tbx_edad_mental");
		tbxMensajeEdadMental = (Textbox)this.getFellow("tbxMensajeEdadMental");
		rowsRes = (Rows) this.getFellow("rowsRes");
		rowsResultadosAnteriores = (Rows) this.getFellow("rowsResultadosAnteriores");
		rowAnteriores = (Row) this.getFellow("rowAnteriores");
	}
	
	public void iniciarMacroFiguraHumana(ZKWindow zkWindow, Paciente paciente, Integer edadMeses){
		this.zkWindow = zkWindow;
		this.edadMeses = edadMeses;
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("codigo_empresa", zkWindow.codigo_empresa);
		parameters.put("codigo_sucursal", zkWindow.codigo_sucursal);
		this.datos = zkWindow.getServiceLocator().getServicio(Test_figura_humanaService.class).listar(parameters);
		for (Test_figura_humana dato : datos) {
			Test_puntuacion_figura_humana punto = new Test_puntuacion_figura_humana();
			punto.setCodigo_empresa(dato.getCodigo_empresa());
			punto.setCodigo_sucursal(dato.getCodigo_sucursal());
			punto.setCodigo_criterio(dato.getCodigo_criterio());
			punto.setPuntuacion("false");
			puntos.add(punto);
		};
		cargarDatos(datos);
		cargarHistorial(paciente);
	}
	
	private void cargarDatos(List<Test_figura_humana> datos){
		for (Test_figura_humana test_figura_humana : datos) {
			rowsRes.appendChild(crearFilas(test_figura_humana));
		}
	}
	
	private void cargarHistorial(Paciente paciente){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("codigo_empresa",zkWindow.codigo_empresa);
		param.put("codigo_sucursal",zkWindow.codigo_sucursal);
		param.put("nro_identificacion",paciente.getNro_identificacion());
		List<Test_puntuacion_figura_humana> puntos = zkWindow.getServiceLocator().getServicio(Test_puntuacion_figura_humanaService.class).listarHistorial(param);
		
		if(puntos.size()>0){
			rowAnteriores.setVisible(true);
		}
		
		for (Test_puntuacion_figura_humana punto : puntos) {
			Elemento elemento = new Elemento();
			elemento.setTipo("via_ingreso");
			elemento.setCodigo(punto.getVia_ingreso());
			elemento = zkWindow.getServiceLocator().getServicio(ElementoService.class).consultar(elemento);
			String fecha = new java.text.SimpleDateFormat("dd/MM/yyyy").format(punto.getFecha());
			rowsResultadosAnteriores.appendChild(crearFilasPuntos(elemento.getDescripcion(),punto.getHistoria(),fecha,punto.getPuntuacion(), edadMental(Integer.valueOf(punto.getPuntuacion()))));
		}
	}
	
	private Row crearFilasPuntos(String programa, Long historia, String fecha, String puntuacion, String edad){
		Row fila = new Row();
		Cell cell = new Cell();
		cell.appendChild(new Label(programa));
		fila.appendChild(cell);
		cell = new Cell();
		cell.appendChild(new Label(historia.toString()));
		fila.appendChild(cell);
		cell = new Cell();
		cell.appendChild(new Label(fecha));
		fila.appendChild(cell);
		cell = new Cell();
		cell.appendChild(new Label(puntuacion));
		fila.appendChild(cell);
		cell = new Cell();
		cell.appendChild(new Label(edad));
		fila.appendChild(cell);
		return fila;
	}
	
	private Row crearFilas(Object objeto){
		Row fila = new Row();
		String colorCell = "#7A7A7A";
		final Test_figura_humana test_figura_humana = (Test_figura_humana)objeto;
		Cell cell = new Cell();
		cell.setStyle("border: 1px solid "+colorCell+";");
		cell.appendChild(new Label(test_figura_humana.getCodigo_criterio()));
		cell.setStyle("border: 1px solid "+colorCell+";");
		fila.appendChild(cell);
		cell = new Cell();
		cell.appendChild(new Label(test_figura_humana.getDatos()));
		cell.setStyle("border: 1px solid "+colorCell+";");
		fila.appendChild(cell);
		cell = new Cell();
		final Checkbox chk = new Checkbox();
		chk.setId(prefijoComponente+test_figura_humana.getCodigo_criterio());
		chk.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				cambiarResultado(test_figura_humana.getCodigo_criterio(), chk);
			}
		});
		checks.put(test_figura_humana.getCodigo_criterio(), chk);
		cell.appendChild(chk);
		cell.setStyle("border: 1px solid "+colorCell+";");
		fila.appendChild(cell);
		return fila;
	}
	
	private void cambiarResultado(String codigo, Checkbox chk){
		for (Test_puntuacion_figura_humana punto : puntos) {
			if(codigo.equals(punto.getCodigo_criterio())){
				punto.setPuntuacion(""+chk.isChecked());
				recalcular();
				break;
			}
		}
	}

	private void recalcular(){
		Integer p = sumatoriaPuntos();
		ibx_total_puntuacion.setText(""+p);
		tbx_edad_mental.setText(edadMental(p));
		Integer[] res = getArregloEdad(p);
		if(res!=null){
			if(edadMeses<res[1]){
				//Mayor
				tbxMensajeEdadMental.setValue("Edad mental superior a la edad biológica");
				tbxMensajeEdadMental.setStyle("background:#FFED9E;font-weight: bold");
			}else if(edadMeses==res[1]){
				//Igual
				tbxMensajeEdadMental.setValue("Edad mental igual a la edad biológica");
				tbxMensajeEdadMental.setStyle("background:#E0FF9E;font-weight: bold");
			}else{
				//Menor
				tbxMensajeEdadMental.setValue("Edad mental inferior a la edad biológica");
				tbxMensajeEdadMental.setStyle("background:#FFBEA8;font-weight: bold");
			}
		}else{
			tbxMensajeEdadMental.setValue("");
			tbxMensajeEdadMental.setStyle(null);
		}
	}
	
	public void cargarPuntos(List<Test_puntuacion_figura_humana> puntos){
		this.puntos = puntos;
		for (Test_puntuacion_figura_humana punto : puntos) {
			Checkbox chk = checks.get(punto.getCodigo_criterio());
			chk.setChecked(punto.getPuntuacion().equals("true"));
		}
		recalcular();
	}
	
	private Integer sumatoriaPuntos(){
		Integer c = 0;
		for (Test_puntuacion_figura_humana punto : puntos) {
			if(punto.getPuntuacion().equals("true")){
				c++;
			}
		}
		return c;
	}

	public List<Test_puntuacion_figura_humana> getPuntos() {
		return puntos;
	}

	public void setPuntos(List<Test_puntuacion_figura_humana> puntos) {
		this.puntos = puntos;
	}
	
	private static Integer[] getArregloEdad(Integer punto){
		Integer[][] arregloEdad = {{2,42},{3,45},{4,48},{5,51},{6,54},
				{7,57},{8,60},{9,63},{10,66},{11,69},{12,72},{13,75},
				{14,78},{15,81},{16,84},{17,87},{18,90},{19,93},{20,96},
				{21,99},{22,102},{23,105},{24,108},{25,111},{26,114},
				{27,117},{28,120},{29,123},{30,126},{31,129},{32,132},
				{33,135},{34,138},{35,141},{36,144},{37,147},{38,150},
				{39,153},{40,156},{41,159},{42,162},{43,165}};
				
		for (int i=0; i<arregloEdad.length; i++){
			if(arregloEdad[i][0] == punto){
				return arregloEdad[i];
			}
		}
		return null;
	}
	
	public static String edadMental(Integer puntos){
		String res;
		Integer anos = -1;
		Integer meses = -1;
		
		Integer[] opc = getArregloEdad(puntos);
		if(opc!=null){
			anos = opc[1]/12;
			meses = opc[1]%12;
			res = anos + " Años "+(meses>0?" y ":"")+(meses>1?meses+" meses":"");
		}else{
			res = "Edad no disponible";
		}
		return res;
	}
}
