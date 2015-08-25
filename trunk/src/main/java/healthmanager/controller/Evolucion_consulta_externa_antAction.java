/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Admision;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import sios.modelo.service.Hisc_historialSiosService;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.ConvertidorXmlToMap;

/**
 * @author Usuario
 *
 */
public class Evolucion_consulta_externa_antAction  extends ZKWindow{
	
//	private static Logger log = Logger.getLogger(Evolucion_consulta_externa_antAction.class);
	
	private Hisc_historialSiosService hisc_historialSiosService;
	
	private Admision admision_seleccionada;
	private Map<Object,Object> bean;
	Map<Object, Object> beanHistoriaPadre;
	
	
	@View private Label lbIdHistoria;
	@View private Label label2_0;
	
	@View private Label lbIdMed;
	@View private Label lbNombreMed;
	@View private Label lbEsp;
	
	@Override
	public void init() throws Exception {
		Map parametros = Executions.getCurrent().getArg();
		if(parametros!=null){
			if(parametros.containsKey(IVias_ingreso.ADMISION_PACIENTE)){
				admision_seleccionada = (Admision) parametros.get(IVias_ingreso.ADMISION_PACIENTE);
			}
			if(parametros.containsKey("HISTORIA_SIOS")){
				bean =  (Map<Object, Object>) parametros.get("HISTORIA_SIOS");
			}
		}
		
		if(admision_seleccionada==null){
			admision_seleccionada = new Admision();
		}
		
		if(bean==null){
			bean = new HashMap<Object, Object>();
		}
		
		beanHistoriaPadre = hisc_historialSiosService.consultarHistoria(bean.get("Padre")+"");
		
		mostrarHistoria();
		mostrarPrestador();
		mostrarDatos_xml(true);
		mostrarDatos_xml(false);
		String fecha = new SimpleDateFormat("MMM dd yyyy hh:mm aa",Locale.ENGLISH).
				format(beanHistoriaPadre.get("FechaAsignacionRegistro"));
		label2_0.setValue(fecha+" "+label2_0.getValue());
	}
	
	private void mostrarHistoria(){
		
		lbIdHistoria.setValue(beanHistoriaPadre.get("Id")+"");
		
	}
	
	private void mostrarPrestador(){
		
		lbIdMed.setValue(beanHistoriaPadre.get("Prestador")+"");
		lbNombreMed.setValue(beanHistoriaPadre.get("NomMed")+"");
		lbEsp.setValue(beanHistoriaPadre.get("Especialidad")+"");
		
	}
	
	private void mostrarDatos_xml(boolean sw)throws Exception{
		String xml = (sw?(String)bean.get("RegistroXML"):(String)beanHistoriaPadre.get("RegistroXML"));
		if(xml!=null){
			//Elemento raiz //
			Document document = ConvertidorXmlToMap.stringToDom(xml);
	        Element nodoC = document.getDocumentElement();
	        NodeList listaNodo = nodoC.getChildNodes();
	        int totalRegistros = listaNodo.getLength();
	        //log.info("totalRegistros: "+totalRegistros);
	        for (int i = 0; i < totalRegistros; i ++){
	        	Node node = listaNodo.item(i);
	            NamedNodeMap namedNodeMap = node.getAttributes();
	            if(this.getFellowIfAny("label"+(!sw?"2":"")+"_"+i)!=null){
	            	Component component = this.getFellow("label"+(!sw?"2":"")+"_"+i);
	            	if(component instanceof Label){
	            		((Label)component).setValue(namedNodeMap.getNamedItem("ValorCampo").getNodeValue());
	            		
	            	}else if(component instanceof Textbox){
	            		((Textbox)component).setValue(namedNodeMap.getNamedItem("ValorCampo").getNodeValue());
	            		
	            	}
	            }
	            
	        }
		}
		
		
	}
	
	
}
