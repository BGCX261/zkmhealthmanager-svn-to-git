package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cuadros_aiepi_descripciones;
import healthmanager.modelo.bean.Cuadros_aiepi_estado;
import healthmanager.modelo.bean.Cuadros_aiepi_resultados;
import healthmanager.modelo.bean.Cuadros_aiepi_sugerencias;
import healthmanager.modelo.bean.Cuadros_aiepi_tutoriales;
import healthmanager.modelo.service.Cuadros_aiepi_descripcionesService;
import healthmanager.modelo.service.Cuadros_aiepi_estadoService;
import healthmanager.modelo.service.Cuadros_aiepi_sugerenciasService;
import healthmanager.modelo.service.Cuadros_aiepi_tutorialesService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.TreeList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.framework.macros.impl.CuadroAiepiIMG;
import com.framework.util.MensajesUtil;

public class CuadroAIEPIMacro extends Grid implements AfterCompose{


	private CuadroAiepiIMG cuadroAiepiIMG;
	
	private ZKWindow zkWindow;
	private Admision admision;
	private String via_ingreso;
	
	//Componentes //
	private Rows rowsResultado;
	private Grid gridResultado;
	private Grid gridTutorial;
	
	private Map<String, Checkbox> mapaChecks = new HashMap<String, Checkbox>();
	private Map<String, Cuadros_aiepi_estado> mapaEstados = new HashMap<String, Cuadros_aiepi_estado>();
	private List<Cuadros_aiepi_descripciones> lista_datos = new TreeList();
	private List<Cuadros_aiepi_resultados> lista_resultados = new TreeList();
	private String cuadro;
	
	@Override
	public void afterCompose() {
		cargarComponentes();
		lista_resultados.clear();
		lista_datos.clear();
	}
	
	private void cargarComponentes() {
		gridTutorial = (Grid) this.getFellow("gridTutorial");
		rowsResultado = (Rows) this.getFellow("rowsResultado");
		gridResultado = (Grid) this.getFellow("gridResultado");
	}
	
	public void inicializarMacro(ZKWindow zkWindow, Admision admision, String via_ingreso, String cuadro) throws Exception {
		this.zkWindow = zkWindow;
		this.admision = admision;
		this.via_ingreso=via_ingreso;
		this.cuadro = cuadro;
		crearCuadro(via_ingreso,cuadro);
	}

	public void cargarDatos(List<Cuadros_aiepi_resultados> lista){
		this.lista_resultados = lista;
		for (Cuadros_aiepi_resultados res : lista_resultados) {
			Checkbox ch = getCheckbox(res.getOpcion(), res.getEstado());
			if(ch!=null){
				ch.setChecked(Boolean.valueOf((res.getValor()==null?"false":res.getValor())));
				evaluarCambio(res.getOpcion(), res.getEstado());
				//log.info(">>>>>>>>>>>><>"+res.getOpcion()+"<>"+res.getEstado());
			}
		}
	}
	
	private void crearCuadro(String via, String cuadro)throws Exception{
		try {
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("via_ingreso", via);
			parameters.put("cuadro", cuadro);
			
			lista_datos = zkWindow.getServiceLocator().getServicio(Cuadros_aiepi_descripcionesService.class).listar(parameters);
			rowsResultado.getChildren().clear();
			mapaChecks.clear();
			
			int sw = 0;
			mapaEstados.clear();
			
			//Cargar Tutorial
			List<Cuadros_aiepi_tutoriales> tutoriales = zkWindow.getServiceLocator().getServicio(Cuadros_aiepi_tutorialesService.class).listar(parameters);
			Columns columnas = new Columns();
			Rows rows = new Rows();
			Row row = new Row();
			rows.appendChild(row);
			for (Cuadros_aiepi_tutoriales tutorial : tutoriales) {
				columnas.appendChild(new Column(tutorial.getTitulo()));
				Cell cell = new Cell();
				Label lbl = new Label(tutorial.getInfo());
				lbl.setStyle("font-weight:bold");
				lbl.setMultiline(true);
				cell.appendChild(lbl);
				cell.setValign("top");
				cell.setStyle("background:"+tutorial.getColor());
				row.appendChild(cell);
			}
			gridTutorial.appendChild(columnas);
			gridTutorial.appendChild(rows);
			gridTutorial.applyProperties();
			gridTutorial.invalidate();
			//Fin carga tutorial
			
			
			for (int i = 0; i < lista_datos.size(); i++) {
				Cuadros_aiepi_descripciones desc = lista_datos.get(i);
				Row r = crearFilas(desc, rowsResultado);
				
				Cuadros_aiepi_estado est = new Cuadros_aiepi_estado();
				est.setCodigo_empresa(desc.getCodigo_empresa());
				est.setCodigo_sucursal(desc.getCodigo_sucursal());
				est.setCuadro(desc.getCuadro());
				est.setEstado(desc.getEstado());
				est.setVia_ingreso(desc.getVia_ingreso());
				final Cuadros_aiepi_estado estado = zkWindow.getServiceLocator().getServicio(Cuadros_aiepi_estadoService.class).consultar(est);
				if(estado!=null){
					if(!mapaEstados.containsKey(estado.getEstado())){
						mapaEstados.put(estado.getEstado(), estado);
						if(estado.getTitulo_descripcion()!=null && !estado.getTitulo_descripcion().isEmpty()){
							Row rdesc = new Row();
							Cell col = new Cell();
							col.setColspan(2);
							Label lbl = new Label(estado.getTitulo_descripcion());
							lbl.setStyle("font-weight:bold;");
							col.appendChild(lbl);
							col.setStyle("background:"+estado.getColor_fondo()+";text-align:center;border: 1px solid #000000;");
							rdesc.appendChild(col);
							rowsResultado.appendChild(rdesc);
						}
					}
					r.setStyle("border: 1px solid #000000;background:"+estado.getColor_fondo());
				}
				
				int c = cantEstado(lista_datos, desc.getEstado());
				
				if(sw==0){
					Cell cell2 = new Cell();
					cell2.setId("cell_2"+desc.getCuadro()+"_"+desc.getEstado()+"_"+desc.getOpcion());
					if(estado!=null){
						final Button btn = new Button();
						btn.setId("btn"+desc.getCuadro()+"_"+desc.getEstado());
						btn.setDisabled(true);
						btn.setVisible(false);
						btn.setLabel(estado.getDescripcion());
						btn.setHflex("1");
						btn.setHeight("60px");
						btn.setStyle("font-weight:bold;background:"+estado.getColor_boton());
						cell2.setRowspan(c);
						cell2.appendChild(btn);
						cell2.setStyle("text-align:center");
						r.appendChild(cell2);
						
						//Cargar Sugerencias
						final Grid gridSugerencias = new Grid();
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("codigo_empresa", est.getCodigo_empresa());
						param.put("codigo_sucursal", est.getCodigo_sucursal());
						param.put("via_ingreso", via);
						param.put("cuadro", cuadro);
						param.put("estado", desc.getEstado());
						List<Cuadros_aiepi_sugerencias> sugerencias = zkWindow.getServiceLocator().getServicio(Cuadros_aiepi_sugerenciasService.class).listar(param);
						Columns cs = new Columns();
						Column c1 = new Column();
						c1.setWidth("30px");
						cs.appendChild(c1);
						Rows rs = new Rows();
						for (Cuadros_aiepi_sugerencias sugerencia : sugerencias) {
							Row r1 = new Row();
							Cell cell0 = new Cell();
							cell0.setStyle("background:"+estado.getColor_fondo());
							Radio rd = new Radio();
							rd.setSelected(true);
							rd.setDisabled(true);
							cell0.appendChild(rd);
							Cell cell1 = new Cell();
							final Label lbl = new Label(sugerencia.getSugerencia());
							lbl.setMultiline(true);
							lbl.setStyle("font-weight:bold");
							final Cuadros_aiepi_sugerencias sug = sugerencia;
							
							//Muestra como "link" o label dependiendo del enlace
							if(sug.getEnlace()!=null && !sug.getEnlace().isEmpty()){
								lbl.setStyle("font-weight:bold;color:blue");
								lbl.addEventListener(Events.ON_MOUSE_OVER, new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0) throws Exception {
										lbl.setStyle("font-weight:bold;color:blue;text-decoration:underline;cursor:pointer");
									}
								});
								lbl.addEventListener(Events.ON_MOUSE_OUT, new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0) throws Exception {
										lbl.setStyle("font-weight:bold;color:blue;cursor: default");
									}
								});
								lbl.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0) throws Exception {
										mostrarVentana(new Image(sug.getEnlace()), zkWindow.getPage(), " ", 0,0,0);

//										w.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//											@Override
//											public void onEvent(Event arg0) throws Exception {
//												w.detach();
//											}
//										});
									}
								});	
							}
							
							cell1.appendChild(lbl);
							cell1.setStyle("background:"+estado.getColor_fondo());
							r1.appendChild(cell0);
							r1.appendChild(cell1);
							rs.appendChild(r1);
						}
						gridSugerencias.appendChild(cs);
						gridSugerencias.appendChild(rs);
						gridSugerencias.setMold("paging");
						gridSugerencias.setPageSize(9);
						gridSugerencias.applyProperties();
						gridSugerencias.invalidate();
												
						//Fin carga Sugerencias
						
						btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								mostrarVentana(gridSugerencias, zkWindow.getPage(), "SUGERENCIAS: "+estado.getDescripcion(),400,0,0);
							}
						});	
					}
				}
				if(c-1>sw){
					sw++;
				}else{
					sw=0;
				}
				
				rowsResultado.appendChild(r);
			}
			rowsResultado.applyProperties();
			gridResultado.setVisible(true);
			gridResultado.invalidate();
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", zkWindow);
		}
	}
		
	public Checkbox getCheckbox(String opcion, String estado){
		return mapaChecks.get(opcion+"-"+estado);
	}

	public void setCheckbox(String opcion, String estado,Checkbox ch){
		mapaChecks.put(opcion+"-"+estado,ch);
	}
	
	private Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		Cell cell1 = new Cell();
		
		final Cuadros_aiepi_descripciones cuadros_aiepi_descripciones = (Cuadros_aiepi_descripciones)objeto;
		fila.setStyle("text-align: justify;nowrap:nowrap");
		final Checkbox ch = new Checkbox();
	
		ch.setId("cbxOpcion_"+cuadros_aiepi_descripciones.getCuadro()+"_"+cuadros_aiepi_descripciones.getEstado()+"_"+cuadros_aiepi_descripciones.getOpcion());
		ch.setLabel(" "+cuadros_aiepi_descripciones.getDescripcion());
		mapaChecks.put(cuadros_aiepi_descripciones.getOpcion()+"-"+cuadros_aiepi_descripciones.getEstado(), ch);
		
		Cuadros_aiepi_resultados cr = new Cuadros_aiepi_resultados();
		cr.setCuadro(cuadro);
		cr.setEstado(cuadros_aiepi_descripciones.getEstado());
		cr.setOpcion(cuadros_aiepi_descripciones.getOpcion());
		cr.setValor(""+false);
		lista_resultados.add(cr);
		
		cell1.appendChild(ch);
		ch.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				evaluarCambio(cuadros_aiepi_descripciones.getOpcion(),cuadros_aiepi_descripciones.getEstado());
				if(zkWindow instanceof CuadroAiepiIMG){
					cuadroAiepiIMG = (CuadroAiepiIMG) zkWindow;
					cuadroAiepiIMG.cambioCheck(cuadro, cuadros_aiepi_descripciones.getEstado(), cuadros_aiepi_descripciones.getOpcion());
				}
			}
		});	
		
		fila.appendChild(cell1);
		return fila;
	}
	
	public void cambiarResultado(String opcion, String estado){
		Checkbox chk = getCheckbox(opcion,estado);
		for(int i=0; i<lista_resultados.size();i++) {
			Cuadros_aiepi_resultados cuadros_aiepi_resultados = lista_resultados.get(i);
			if(cuadros_aiepi_resultados.getCuadro().equals(cuadro) && cuadros_aiepi_resultados.getOpcion().equals(opcion) && cuadros_aiepi_resultados.getEstado().equals(estado)){
				cuadros_aiepi_resultados.setValor(""+chk.isChecked());
				lista_resultados.set(i, cuadros_aiepi_resultados);
				break;
			}
		}
	}

	private void cambiarEstadoBoton(String opcion, String estado){
		int a = cantChecksEstado(lista_datos, estado);
		Button boton = getBoton(estado);
		Boolean r = (a<mapaEstados.get(estado).getMin_seleccion());
		boton.setDisabled(r);
		if(!boton.isDisabled()){
			boton.setVisible(true);
			boton.setHflex("1");
			boton.setWidgetListener("onBind", "jq(this).fadeIn(300).delay(50).fadeOut(300).fadeIn(300)");
		}else{
			boton.setHflex("1");
			boton.setWidgetListener("onBind", "jq(this).fadeOut(300).delay(50).fadeIn(300).fadeOut(300)");
			boton.setVisible(false);
		}
	}
	
	private Button getBoton(String estado){
		return (Button) this.getFellow("btn"+this.cuadro+"_"+estado);
	}
	
	private Integer cantChecksEstado(List<Cuadros_aiepi_descripciones> lista, String estado){
		Integer i = 0;
		for (Cuadros_aiepi_descripciones descripcion : lista) {
			if(descripcion.getEstado().equalsIgnoreCase(estado)){
				Checkbox chk = getCheckbox(descripcion.getOpcion(), estado);
				if(chk.isChecked()){
					i++;
				}
			}
		}
		return i;
	}
	
	private Integer cantEstado(List<Cuadros_aiepi_descripciones> lista, String estado){
		Integer i = 0;
		for (Cuadros_aiepi_descripciones descripcion : lista) {
			if(descripcion.getEstado().equalsIgnoreCase(estado)){
				i++;
			}
		}
		return i;
	}

	public String getCuadro() {
		return cuadro;
	}

	public ZKWindow getZkWindow() {
		return zkWindow;
	}

	public void setZkWindow(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
	}

	public Admision getAdmision() {
		return admision;
	}

	public void setAdmision(Admision admision) {
		this.admision = admision;
	}

	public String getVia_ingreso() {
		return via_ingreso;
	}

	public void setVia_ingreso(String via_ingreso) {
		this.via_ingreso = via_ingreso;
	}

	public List<Cuadros_aiepi_resultados> getLista_resultados() {
		return lista_resultados;
	}

	public void setLista_resultados(List<Cuadros_aiepi_resultados> lista_resultados) {
		this.lista_resultados = lista_resultados;
	}

	public void setCuadro(String cuadro) {
		this.cuadro = cuadro;
	}

	public void deshabilitarChecks(String[]...opciones) {
		for (String[] opc : opciones) {
			Checkbox chk = getCheckbox(opc[0], opc[1]);
			chk.setDisabled(true);
			chk.setStyle("color:gray");
		}
	}
	public void evaluarCambio(String opcion, String estado) {
		cambiarResultado(opcion,estado);
		cambiarEstadoBoton(opcion,estado);
	}
	
	public static Window mostrarVentana(Component component, Page page, String title, Integer width, Integer height,Integer tipo){
		
		final Window window = new Window(title,"normal",true); 
		window.appendChild(component); 
	
		if(component instanceof Image){
			Image img = (Image)component;
			img.setAttribute("org.zkoss.zul.image.preload", true);
		}
		
		if(width>0){
			window.setWidth(width+"px");
		}
		if(height>0){
			window.setHeight(height+"px");
		}
		window.setPage(page);
		window.setPosition("center");		
		switch (tipo) {
		case 0:
			window.doModal();	
			break;
		case 1:
			window.doPopup();	
			break;
		case 2:
			window.doHighlighted();
			break;
		case 3:
			window.doOverlapped();
			break;
		case 4:
			window.doEmbedded();
			break;
		default:
			window.doPopup();
			break;
		}
		window.applyProperties();
		return window;
	}
	
public static Window mostrarImagen(Component component, Page page, String title, Integer width, Integer height,Integer tipo){
		
		final Window window = new Window(); 
		window.appendChild(component); 
	
		if(component instanceof Image){
			Image img = (Image)component;
			img.setAttribute("org.zkoss.zul.image.preload", true);
		}
		
		if(width>0){
			window.setWidth(width+"px");
		}
		if(height>0){
			window.setHeight(height+"px");
		}
		window.setPage(page);
		window.setPosition("center");		
		switch (tipo) {
		case 0:
			window.doModal();	
			break;
		case 1:
			window.doPopup();	
			break;
		case 2:
			window.doHighlighted();
			break;
		case 3:
			window.doOverlapped();
			break;
		case 4:
			window.doEmbedded();
			break;
		default:
			window.doPopup();
			break;
		}
		window.applyProperties();
		return window;
	}
	
	
	public static void deshabilitarCheck(Checkbox ch, Boolean r){
		if(r){
			ch.setChecked(true);
		}else{
			ch.setChecked(false);
		}
	}
	
	public List<Checkbox> getListaChecksEstado(String estado){
		List<Checkbox> lista = new ArrayList<Checkbox>();
		for (Cuadros_aiepi_descripciones dato : lista_datos) {
			if(dato.getEstado().equalsIgnoreCase(estado)){
				Checkbox chk = mapaChecks.get(dato.getOpcion()+"-"+estado);
				lista.add(chk);
			}
		}
		return lista;
	}
	
	public Integer cantCheckeadosEstado(String estado){
		Integer cant = 0;
		for (Checkbox chk : getListaChecksEstado(estado)) {
			if(chk.isChecked()){
				cant ++;
			}
		}		
		return cant;
	}
}