package healthmanager.controller;

import healthmanager.modelo.bean.Admision;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sios.modelo.service.Hisc_historialSiosService;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Historias_version_antAction extends ZKWindow{
	
//	private static Logger log = Logger.getLogger(Historias_version_antAction.class);
	
	private Hisc_historialSiosService hisc_historialSiosService;
	
	private Admision admision_seleccionada;
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	@View private Listbox lbxTipo_consulta;
	
	//@View private Window winHistoria_ant;
	@View private Center centerHistoria;
	
	private final String[] idsExcluyentes = {};
	
	private Map<String, Object> lista_tipos_historias;

	@Override
	public void init() throws Exception {
		listarCombos();
		Map parametros = Executions.getCurrent().getArg();
		if(parametros!=null){
			if(parametros.containsKey(IVias_ingreso.ADMISION_PACIENTE)){
				admision_seleccionada = (Admision) parametros.get(IVias_ingreso.ADMISION_PACIENTE);
			}
		}
		
		if(admision_seleccionada==null){
			admision_seleccionada = new Admision();
		}
		
		generarTipos_historias();
		crearListboxTipo_consulta();
	}
	
	private void generarTipos_historias(){
		lista_tipos_historias = new TreeMap<String, Object>();
		
		lista_tipos_historias.put("01", "Consulta  Medicina General");
		lista_tipos_historias.put("02", "Nota");
		lista_tipos_historias.put("07", "Consulta de Urgencias");
		lista_tipos_historias.put("08", "Consulta Odontologica y salud oral");
		lista_tipos_historias.put("35", "PyP Adulto Mayor");
		lista_tipos_historias.put("36", "PyP Control Prenatal");
		lista_tipos_historias.put("38", "PyP Alteracion al menor");
		lista_tipos_historias.put("40", "PyP Planificacion Familiar");
		lista_tipos_historias.put("41", "PyP Desarrollo del Joven");
		
		
		/*lista_tipos_historias.put("03", "PyP Hipertension y Diabetis");
		lista_tipos_historias.put("42", "PyP Alteracion al embarazo");
		lista_tipos_historias.put("06", "PyP Crecimiento y Desarrollo");
		lista_tipos_historias.put("10", "Agudeza Visual");
		lista_tipos_historias.put("11", "Examen de Mama");
		lista_tipos_historias.put("12", "Servicios Amigables para Jovenes y Adolecentes");
		lista_tipos_historias.put("13", "Control de TBC");
		lista_tipos_historias.put("14", "Control de Lepra");
		lista_tipos_historias.put("15", "Control de Cervix");
		lista_tipos_historias.put("16", "Consulta de Psiquiatria");
		lista_tipos_historias.put("17", "Consulta de Nutricion y Dietetica");
		lista_tipos_historias.put("20", "Fisioterapia");
		lista_tipos_historias.put("21", "Consulta psicología");*/
	}
	
	public void crearListboxTipo_consulta(){
		lbxTipo_consulta.getItems().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("Todas");
		lbxTipo_consulta.appendChild(listitem);
		
		Iterator it = lista_tipos_historias.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			listitem = new Listitem();
			listitem.setValue(e.getKey());
			listitem.setLabel(e.getValue()+"");
			lbxTipo_consulta.appendChild(listitem);
		}
		
		if(lbxTipo_consulta.getItemCount()>0){
			lbxTipo_consulta.setSelectedIndex(0);
		}
		
		/*if(lbxTipo_consulta.getItemCount()>1){
			lbxTipo_consulta.setSelectedIndex(1);
		}*/
	}
	
	public void listarCombos(){
		listarParameter();
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		
		Listitem listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("rh.FechaAsignacionRegistro");
		listitem.setLabel("Fecha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			//buscarDatos();
		}else{
			//buscarDatos();
			//limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
	}
	
	//Metodo para buscar //
		public void buscarDatos()throws Exception{
			try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			if (!value.isEmpty()) {

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("parameter", parameter);
				parameters.put("value", "%" + value + "%");

				if (admision_seleccionada.getNro_identificacion() != null) {
					parameters.put("identificacion",
							admision_seleccionada.getNro_identificacion());
				} else if (parameter.equalsIgnoreCase("identificacion")) {
					parameters.put("identificacion", value);
				}

				if (!lbxTipo_consulta.getSelectedItem().getValue().toString()
						.equals("")) {
					parameters.put("tipo_historia", lbxTipo_consulta
							.getSelectedItem().getValue().toString());
				}

				List<Map<Object, Object>> lista_datos = hisc_historialSiosService
						.listar(parameters);
				rowsResultado.getChildren().clear();

				for (Map<Object, Object> bean : lista_datos) {

					Map<String, Object> parametersEvoluciones = new HashMap<String, Object>();
					parametersEvoluciones.put("parameter", parameter);
					parametersEvoluciones.put("value", "%" + value + "%");
					parametersEvoluciones.put("identificacion",
							admision_seleccionada.getNro_identificacion());
					parametersEvoluciones.put("padre", bean.get("Id") + "");

					List<Map<Object, Object>> lista_evoluciones = hisc_historialSiosService
							.listar(parametersEvoluciones);
					bean.put("evoluciones", lista_evoluciones);
					rowsResultado.appendChild(crearFilas(bean, this));
					gridResultado.setVisible(true);
					gridResultado.setMold("paging");
					gridResultado.setPageSize(20);

					gridResultado.applyProperties();
					gridResultado.invalidate();

				}

			} else {
				
				Messagebox
				.show("Debe digitar un valor para el criterio de busqueda",
						"Criterio de busqueda",
						Messagebox.OK,
						Messagebox.EXCLAMATION);
			}
				
			} catch (Exception e) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}
		
		public Row crearFilas(Object objeto, Component componente)throws Exception{
			Row fila = new Row();
			
			final Map<Object,Object> bean = (Map<Object, Object>) objeto;
			List<Map<Object,Object>> lista_evoluciones = (List<Map<Object, Object>>) bean.get("evoluciones");
			
			Hbox hbox = new Hbox();
			Space space = new Space();
			
			String fecha = new SimpleDateFormat("dd/MM/yyyy").format(bean.get("FechaAsignacionRegistro"));
			////log.info("Padre: "+bean.get("Padre"));
			
			fila.setStyle("text-align: justify;nowrap:nowrap");
			if(lista_evoluciones!=null){
				if(!lista_evoluciones.isEmpty()){
					generarDetailEvoluciones(fila, lista_evoluciones);
				}else if(bean.get("Padre").toString().equalsIgnoreCase("Null")){
					fila.appendChild(new Label(""));
				}
			}else if(bean.get("Padre").toString().equalsIgnoreCase("Null")){
				fila.appendChild(new Label(""));
			}
			
			String codigo = (bean.get("Padre").toString().equalsIgnoreCase("Null")?
					bean.get("CodigoRegistro")+"":bean.get("Raiz")+"");
			////log.info("codigo: "+codigo);
			////log.info("CodigoRegistro: "+bean.get("CodigoRegistro")+"");
			////log.info("CodigoRegistro: "+bean.get("Raiz")+"");
			
			fila.appendChild(new Label(lista_tipos_historias.get(codigo)+""));
			fila.appendChild(new Label(bean.get("Id")+""));
			fila.appendChild(new Label(fecha+""));
			fila.appendChild(new Label(bean.get("Identificacion")+""));
			fila.appendChild(new Label(bean.get("Caso")+""));
			fila.appendChild(new Label(bean.get("Asunto")+""));
			
			hbox.appendChild(space);
			
			Image img = new Image();
			img.setSrc("/images/editar.gif");
			img.setTooltiptext("Ver");
			img.setStyle("cursor: pointer");
			img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					//log.info(">>>>>>>>>>"+bean.get("CodigoRegistro"));
					if(bean.get("CodigoRegistro").equals("01") && bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"consulta_externa_ant");
					}else if(bean.get("Raiz").equals("01") && !bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"evolucion_consulta_externa_ant");
					}else if(bean.get("CodigoRegistro").equals("02") && bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"nota_ant");
					}else if(bean.get("CodigoRegistro").equals("08") && bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"odontologia_ant");
					}else if(bean.get("CodigoRegistro").equals("40") && bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"planificacion_familiar_ant");
					}else if(bean.get("CodigoRegistro").equals("41") && bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"alteracion_joven_ant");
					}else if(bean.get("CodigoRegistro").equals("07") && bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"urgencias_ant");
					}else if(bean.get("CodigoRegistro").equals("35") && bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"adulto_mayor_ant");
					}else if(bean.get("CodigoRegistro").equals("36") && bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"recien_nacido_ant");
					}else if(bean.get("CodigoRegistro").equals("38") && bean.get("Padre").toString().equalsIgnoreCase("Null")){
						mostrarHistoria(bean,"alteracion_menor_ant");
					}else{
						MensajesUtil.mensajeAlerta("Alerta!!", 
								"Este tipo de historia no esta disponible");
					}
					
				}
			});
			hbox.appendChild(img);
			
			hbox.appendChild(space);
			hbox.appendChild(img);
			
			fila.appendChild(hbox);
			
			
			
			
			
			return fila;
			
		}
		
		private void generarDetailEvoluciones(Row fila,
				List<Map<Object,Object>> lista_evoluciones)throws Exception{
			if(!lista_evoluciones.isEmpty()){
				////log.info("gener¿Detail");
				
				Grid grid = new Grid();
				Columns columns = new Columns();
				columns.setSizable(true);
				grid.appendChild(columns);
				
				columns.appendChild(new Column("Tipo historia", "", "220px"));
				columns.appendChild(new Column("Id. Historia", "", "100px"));
				columns.appendChild(new Column("Fecha", "", "150px"));
				columns.appendChild(new Column("Id. Paciente", "", "100px"));
				columns.appendChild(new Column("Caso", "", "80px"));
				columns.appendChild(new Column("Asunto historia", "", ""));
				columns.appendChild(new Column("Acciones", "", "80px"));
				
				Rows rows = new Rows();
				grid.appendChild(rows);
				for (Map<Object, Object> map : lista_evoluciones) {
					Row filaDetail = crearFilas(map, this);
					rows.appendChild(filaDetail);
					
				}
				
				Detail detail = new Detail();
				detail.appendChild(grid);
				fila.appendChild(detail);
			}
			
		}
		
		private void mostrarHistoria(Map<Object,Object> bean,String pages)throws Exception{
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
			
			centerHistoria.getChildren().clear();
			Window windowAux = new  Window();
			windowAux.setWidth("100%");
			windowAux.setHeight("100%");
			centerHistoria.appendChild(windowAux);
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision_seleccionada);
			parametros.put("HISTORIA_SIOS",bean);
			
			Window winHistoria_ant = (Window)Executions.createComponents("/pages/sios/"+pages+".zul",
					Historias_version_antAction.this, parametros);
			winHistoria_ant.setWidth("100%");
			winHistoria_ant.setHeight("100%");
			
			
			/*if(centerHistoria.getFirstChild()!=null){
				Window aux = (Window) centerHistoria.getFirstChild();
				aux.setId(null);
				aux.detach();
			}*/

			windowAux.appendChild(winHistoria_ant);
			
			
			
		}

}
