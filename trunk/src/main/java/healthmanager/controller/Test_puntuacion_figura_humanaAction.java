/*
 * test_puntuacion_figura_humanaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package healthmanager.controller;



import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Test_figura_humana;
import healthmanager.modelo.bean.Test_puntuacion_figura_humana;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.Test_figura_humanaService;
import healthmanager.modelo.service.Test_puntuacion_figura_humanaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Test_puntuacion_figura_humanaAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Test_puntuacion_figura_humanaAction.class);
	
	//Componentes //
	@View private Intbox ibx_total_puntuacion;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
    private Textbox tbxCodigo_criterio;
	private Textbox tbxPuntuacion;
	private final String[] idsExcluyentes = {};
	private Test_figura_humana test_figura_humana = null;
	private List<Test_puntuacion_figura_humana> puntos= new ArrayList<Test_puntuacion_figura_humana>();
//	private Admision admision;
	private List<Test_figura_humana> lista_datos =  new ArrayList<Test_figura_humana>();
	
	Test_puntuacion_figura_humanaService test_puntuacion_figura_humanaService;
	Test_figura_humanaService test_figura_humanaService;
	Test_puntuacion_figura_humana test_puntuacion_figura_humana;
	String via_ingreso;
	
	@Override
	public void init() {
		
		buscarDatos_figura_humana();
		
	}
	
	public static void guardarDatosPuntuacion(Long codigo_historia,
			Map<String, Object> mapa_datos,
			Impresion_diagnosticaService impresion_diagnosticaService)
			throws Exception {
		
		Test_puntuacion_figura_humana test_puntuacion_figura_humana = (Test_puntuacion_figura_humana) mapa_datos
				.get("impresion_diagnostica");

		if (test_puntuacion_figura_humana != null) {
			test_puntuacion_figura_humana.setHistoria(codigo_historia);
			//Test_puntuacion_figura_humanaService.guardarDatos(test_puntuacion_figura_humana);

		}
		}
	
	//obtener test figuara humana
		public Test_puntuacion_figura_humana obtenerTest_puntuacion_figura_humana()
				throws Exception {

			if (test_puntuacion_figura_humana == null)
				test_puntuacion_figura_humana = new Test_puntuacion_figura_humana();
			
			test_puntuacion_figura_humana.setCodigo_criterio(tbxCodigo_criterio.getValue());
			test_puntuacion_figura_humana.setPuntuacion(tbxPuntuacion.getValue());
			test_puntuacion_figura_humana.setCodigo_empresa(codigo_empresa);
			test_puntuacion_figura_humana.setCodigo_sucursal(codigo_sucursal);
			test_puntuacion_figura_humana.setVia_ingreso(via_ingreso);

//				impresion_diagnostica.setListado_fichas(obtenerFichasEpidemiologia());
//				impresion_diagnostica.setCodigo_empresa(zkWindow.codigo_empresa);
//				impresion_diagnostica.setCodigo_sucursal(zkWindow.codigo_sucursal);
//				impresion_diagnostica.setCausas_externas(lbxCausas_externas
//						.getSelectedItem().getValue().toString());
//				impresion_diagnostica.setCie_principal(bandboxPrincipal_cie
//						.getValue());
//				impresion_diagnostica.setCie_relacionado1(bandboxRelacionado1_cie
//						.getValue());
//				impresion_diagnostica.setCie_relacionado2(bandboxRelacionado2_cie
//						.getValue());
//				impresion_diagnostica.setCie_relacionado3(bandboxRelacionado3_cie
//						.getValue());
//				impresion_diagnostica.setCodigo_consulta_pyp(lbxCodigo_consulta_pyp
//						.getSelectedItem().getValue().toString());
//				
//				impresion_diagnostica.setFinalidad_consulta(lbxFinalidad_consulta
//						.getSelectedItem().getValue().toString());
//				impresion_diagnostica.setTipo_principal(lbxTipo_principal
//						.getSelectedItem().getValue().toString());
//				impresion_diagnostica.setTipo_relacionado1(lbxTipo_relacionado1
//						.getSelectedItem().getValue().toString());
//				impresion_diagnostica.setTipo_relacionado2(lbxTipo_relacionado2
//						.getSelectedItem().getValue().toString());
//				impresion_diagnostica.setTipo_relacionado3(lbxTipo_relacionado3
//						.getSelectedItem().getValue().toString());
//				impresion_diagnostica.setVia_ingreso(via_ingreso);
			
			return test_puntuacion_figura_humana;
			}	
		
		
		
		
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			buscarDatos_figura_humana();
		}else{
			//buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		
		boolean valida = true;
		
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...","Los campos marcados con (*) son obligatorios");
		}
		
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
					
			Map<String,Object> parameters = new HashMap<String,Object>();
			
			test_puntuacion_figura_humanaService.setLimit("limit 25 offset 0");
			
			List<Test_puntuacion_figura_humana> lista_datos = test_puntuacion_figura_humanaService.listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Test_puntuacion_figura_humana test_puntuacion_figura_humana : lista_datos) {
				rowsResultado.appendChild(crearFilas(test_puntuacion_figura_humana, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(5);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Test_puntuacion_figura_humana test_puntuacion_figura_humana = (Test_puntuacion_figura_humana)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(test_figura_humana.getCodigo_criterio()+""));
		//fila.appendChild(new Label(test_puntuacion_figura_humana.getCodigo_criterio()+""));
		fila.appendChild(new Label(test_puntuacion_figura_humana.getPuntuacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(test_puntuacion_figura_humana);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(test_puntuacion_figura_humana);
								buscarDatos_figura_humana();
							}
						}
					});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//metodo para buscar figuara humana 
	
	public void buscarDatos_figura_humana(){
		try {
			
			Map<String,Object> parameters = new HashMap<String,Object>();
		
			test_puntuacion_figura_humanaService.setLimit("limit 25 offset 0");
			
			lista_datos = test_figura_humanaService.listar(parameters);
			rowsResultado.getChildren().clear();
			//Inicio
			for (Test_figura_humana test_figura_humana : lista_datos) {
				Test_puntuacion_figura_humana tpfh = new Test_puntuacion_figura_humana();
				tpfh.setCodigo_criterio(test_figura_humana.getCodigo_criterio());
				tpfh.setCodigo_empresa(test_figura_humana.getCodigo_empresa());
				tpfh.setCodigo_sucursal(test_figura_humana.getCodigo_sucursal());
				tpfh.setPuntuacion("0");
				puntos.add(tpfh);
				rowsResultado.appendChild(crearFilas_figura_humana(test_figura_humana, this));
			}
			
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(8);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	
	
	public Row crearFilas_figura_humana(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
	
		
		final Test_figura_humana test_figura_humana = (Test_figura_humana)objeto;
		Test_figura_humana obj = (Test_figura_humana)objeto; 
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");

	    fila.appendChild(new Label(test_figura_humana.getCodigo_criterio()+""));
		fila.appendChild(new Label(test_figura_humana.getDatos()+""));
		
		final Intbox text = new Intbox() ;
		text.setValue(0);
		text.setId("txtPuntuacion_"+obj.getCodigo_criterio());
		text.setText("0");
		text.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if (text.getText().equals("")||text.getValue()<0 || text.getValue()>1){
					text.setValue(0);
					Messagebox.show("error digita cero o uno ");
				}else{
					
//				if(text.getValue() < 0 || text.getValue()>1){
//					Messagebox.show("Solo cero(0) y unoo(1)");
//						text.setValue(0);
//						
//						text.setValue(0);
//					}else{
						cambiarPunto(text);
						
				}
					
					
				
				
		}
		});
		fila.appendChild(text);
		
		
		hbox.appendChild(space);
		
		Image img = new Image();
			img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(test_figura_humana);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(test_figura_humana);
								buscarDatos_figura_humana();
							}
						}
					});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
//	//Metodo para guardar la informacion //
//	public void guardarDatos()throws Exception{
//		try {
//			FormularioUtil.setUpperCase(groupboxEditar);
//			if(validarForm()){
//				//Cargamos los componentes //
//				
//				//Integer c=0;
//				//Al guardar
//				for (Test_puntuacion_figura_humana punto : puntos) {
//					Intbox ixtPuntuacion = (Intbox) this.getFellow("txtPuntuacion_"+punto.getCodigo_criterio());
//					punto.setPuntuacion(ixtPuntuacion.getValue().toString());
//					punto.setNro_identificacion(admision != null ? admision.getNro_identificacion() : null);
//					
//					punto.setHistoria("1223");
//					
//					if(tbxAccion.getText().equalsIgnoreCase("registrar")){
//						
//						test_puntuacion_figura_humanaService.crear(punto);
//					}else{
//						int result =test_puntuacion_figura_humanaService.actualizar(punto);
//						if(result==0){
//							throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
//						}
//					}
//				}
//				MensajesUtil.mensajeInformacion("Informacion ..","Guardo con exito");
//			}
//			
//		}catch (Exception e) {
//			MensajesUtil.mensajeError(e, "", this);
//		}
//		
//	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Test_puntuacion_figura_humana test_puntuacion_figura_humana = (Test_puntuacion_figura_humana)obj;
		try{
			tbxCodigo_criterio.setValue(test_puntuacion_figura_humana.getCodigo_criterio());
			tbxPuntuacion.setValue(test_puntuacion_figura_humana.getPuntuacion());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Test_puntuacion_figura_humana test_puntuacion_figura_humana = (Test_puntuacion_figura_humana)obj;
		try{
			int result =test_puntuacion_figura_humanaService.eliminar(test_puntuacion_figura_humana);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (HealthmanagerException e) {
			MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
		}catch(RuntimeException r){
			MensajesUtil.mensajeError(r, "", this);
		}
	}
	
	public void cambiarPunto(Intbox intbox){
		
		//log.info(">>>>>"+intbox.getId().substring("txtPuntuacion_".length()));
		
		
		for (Test_puntuacion_figura_humana test_puntuacion_figura_humana : puntos) {
			String codigo = intbox.getId().substring("txtPuntuacion_".length());
			if(test_puntuacion_figura_humana.getCodigo_criterio().equalsIgnoreCase(codigo)){
				test_puntuacion_figura_humana.setPuntuacion(intbox.getValue().toString());
				ibx_total_puntuacion.setText(""+sumatoriaPuntos());
				System.out.println(">>>>>>>>>>"+sumatoriaPuntos());
				break;
			}
		}
	}
	
	public Integer sumatoriaPuntos(){
		Integer c = 0;
		for (Test_puntuacion_figura_humana punto : puntos) {
			c +=Integer.valueOf(punto.getPuntuacion());
		}
		return c;
	}
}