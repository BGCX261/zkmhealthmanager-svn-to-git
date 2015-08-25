package healthmanager.controller.reportes;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.framework.res.Res;
import com.framework.util.MensajesUtil;

public class ReportePacienteActividadesOdontologia extends ZKWindow {
	
	@View private Listbox lbxAnios;
	@View private Listbox lbxMeses;
	@View private Listbox lbxMedicodosOdontologos;
	
	private final String ESPECIALIDAD_ODONTOLOGIA = "51";
	
	private String meses_y_anio_abrebiado = "";

	@Override
	public void init() throws Exception {
		inicializarListBoxAnio();
//		inicializarListBoxMese();
		inicializarListBoxMedicosOdontologos();
	} 
	
	
	public void imprimirActividadesPacientesOdontologo(int modo_orden){
		if(validarImpimirReporte()){ 
			Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", "PacienteActividadesOdontologia");
			paramRequest.put("anio", lbxAnios.getSelectedItem().getValue().toString() + "");
			paramRequest.put("meses", getMesesDelAnio());
			paramRequest.put("medicos", lbxMedicodosOdontologos.getSelectedItems());
			paramRequest.put("meses_anio", meses_y_anio_abrebiado);
			paramRequest.put("modo_orden", modo_orden);
			mostarReporte(paramRequest);
		}
	}
	
    public void imprimir(){
		imprimirActividadesPacientesOdontologo(1);
	}
    
    public void imprimirSimple(){
    	imprimirActividadesPacientesOdontologo(3);
    }
    
    public void imprimirProcedimientoOrdenados(){
    	if(validarImpimirReporte()){
    		Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", "TotalProcedimientoOrdenadosPorOdontolog");
			paramRequest.put("anio", lbxAnios.getSelectedItem().getValue().toString() + "");
			paramRequest.put("meses", getMesesDelAnio());
			paramRequest.put("medicos", lbxMedicodosOdontologos.getSelectedItems());
			mostarReporte(paramRequest);
    	}
    }
    
    public void imprimirPacietesAtendidosPorEdad(){
    	Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "TotalPacientesPorEdadMesYAnio");
		String dia_anio = getFecha();
		paramRequest.put("fecha_aniomes", dia_anio);
		paramRequest.put("fecha", Date.valueOf(dia_anio + "-01"));  
		mostarReporte(paramRequest);
    }
    
    public void mostarReporte(Map<String, Object> param){
    	Component componente = Executions.createComponents("/pages/printInformes.zul", this, param);
		final Window window = (Window)componente;
		window.setWidth("100%");
		window.setHeight("100%"); 
		window.doModal();
    }
    
    public String getFecha(){
    	String anio = lbxAnios.getSelectedItem().getValue();
    	int index = lbxMeses.getSelectedIndex() + 1;
    	return anio + "-" + ((index <= 9) ? "0" + index : ("" + index));
    }
    
    private List<String> getMesesDelAnio(){
    	// Enero, Febrero, Marzo y Abril del año 2013 
    	meses_y_anio_abrebiado = "";
    	List<String> list = new ArrayList<String>();
    	Set<Listitem> items = lbxMeses.getSelectedItems();
    	String anio = lbxAnios.getSelectedItem().getValue();
    	int count = 0;
    	String[] mesesParaOrdenar = new String[items.size()];
    	for (Listitem listitem : items) {
			 int index = (lbxMeses.getIndexOfItem(listitem) + 1);
			 String itemAnioMes = anio + "-" + ((index <= 9) ? "0" + index : ("" + index));
			 //log.info("Item mes y anio: " + itemAnioMes); 
			 list.add(itemAnioMes);
			 String nombre_mes =  Res.getNombreMes(index - 1);
			 mesesParaOrdenar[count++] = nombre_mes;
		}
    	
    	count = 0;
    	for (int i = mesesParaOrdenar.length - 1; i >= 0 ; i--) {
    		String nombre_mes = mesesParaOrdenar[i];
    		meses_y_anio_abrebiado += nombre_mes + (count < items.size() ? (count < (items.size() - 2) ? ", " : " y ") : "");
    		count++;
		}
    	
    	meses_y_anio_abrebiado = meses_y_anio_abrebiado.substring(0, meses_y_anio_abrebiado.length() - 2);
    	meses_y_anio_abrebiado += " del año " + anio;
    	//log.info("Meses del anio: " + meses_y_anio_abrebiado); 
    	return list;
    }
	
	private boolean validarImpimirReporte() {
		boolean validar = true;
		String msj = "";
		if(lbxMeses.getSelectedItems().isEmpty()){
			validar = false;
			msj = "Para imprimir el reporte por lo menos debe seleccionar un mes";
		}
		
		if(lbxMedicodosOdontologos.getSelectedItems().isEmpty()){
			validar = false;
			msj = "Para imprimir el reporte por lo menos debe seleccionar un odontológo";
		}
		if(!validar)MensajesUtil.mensajeAlerta("Advertencia", msj);
		return validar;
	}


	/*
	 * Metodos inicializadores..
	 * */
	private void inicializarListBoxMedicosOdontologos() {
      try {
		Map<String, Object>  map = new  HashMap<String, Object>();
		  map.put("codigo_especialidad", ESPECIALIDAD_ODONTOLOGIA); 
		  map.put("codigo_empresa", codigo_empresa);
		  map.put("codigo_sucursal", codigo_sucursal);
		  List<Prestadores> prestadores = getServiceLocator().getPrestadoresService().listar(map);
		  for (Prestadores prestadoresTemp : prestadores) {
			 Usuarios usuarios = new Usuarios();
			 usuarios.setCodigo_empresa(prestadoresTemp.getCodigo_empresa());
			 usuarios.setCodigo_sucursal(prestadoresTemp.getCodigo_sucursal());
			 usuarios.setCodigo(prestadoresTemp.getNro_identificacion());
			 usuarios = getServiceLocator().getUsuariosService().consultar(usuarios);
			 if(usuarios != null){
				 Listitem listitem = new Listitem();
				 listitem.setValue(usuarios);
				 Listcell listcell = new Listcell();
				 listcell.setLabel(usuarios.getCodigo());
				 listitem.appendChild(listcell);
				 
				 
				 listcell = new Listcell();
				 listcell.setLabel(usuarios.getNombres() + " " + usuarios.getApellidos());
				 listitem.appendChild(listcell);
				 
				 lbxMedicodosOdontologos.appendChild(listitem); 
			 }
		  }
		} catch (Exception e) { 
			e.printStackTrace();
			MensajesUtil.mensajeError(e, null, this);
		}		
	}

	private void inicializarListBoxAnio(){
		String[] anios = Res.getAnnos(10);
		for (String anio : anios) {
			Listitem listitem = new Listitem();
			listitem.setValue(anio);
			listitem.setLabel("" + anio);
			lbxAnios.appendChild(listitem);
		}
		if(lbxAnios.getItemCount() > 0){
			lbxAnios.setSelectedIndex(0);
		}
	}
	
	public void seleccionarMesActual(){
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		Integer mes  = (Integer) request.getSession().getAttribute("mes");
		if(mes != null){
			lbxMeses.setSelectedIndex(mes);
		}else{
			Calendar calendar = Calendar.getInstance();
			lbxMeses.setSelectedIndex(calendar.get(Calendar.MONTH)); 
		}
	}
	
	@SuppressWarnings("unused") 
	private void inicializarListBoxMese(){
		lbxMeses.getChildren().clear();
		for (int i = 0; i < 12; i++) {
			Listitem listitem = new Listitem();
			listitem.setValue(i);
			listitem.setLabel(Res.getNombreMes(i)); 
			lbxMeses.appendChild(listitem);
		}
		if(lbxMeses.getItemCount() > 0){
			lbxMeses.setSelectedIndex(0);
		}
	}

}
