/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Admision;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.ConvertidorXmlToMap;

/**
 * @author Usuario
 *
 */
public class Nota_antAction  extends ZKWindow{
	
//	private static Logger log = Logger.getLogger(Nota_antAction.class);
	
	private Admision admision_seleccionada;
	private Map<Object,Object> bean;
	
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
		
		mostrarPrestador();
		mostrarDatos_xml();
	}
	
	private void mostrarPrestador(){
		
		lbIdMed.setValue(bean.get("Prestador")+"");
		lbNombreMed.setValue(bean.get("NomMed")+"");
		lbEsp.setValue(bean.get("Especialidad")+"");
		
	}
	
	private void mostrarDatos_xml()throws Exception{
		String xml = (String)bean.get("RegistroXML");
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
	            if(this.getFellowIfAny("lbl_"+namedNodeMap.getNamedItem("NombreCampo").getNodeValue())!=null){
	            	Component component = this.getFellow("lbl_"+namedNodeMap.getNamedItem("NombreCampo").getNodeValue());
	            	
	            	if(component instanceof Label){
	            		String valor = namedNodeMap.getNamedItem("ValorCampo").getNodeValue();
	            		if(valor.equalsIgnoreCase("false")){
	            			valor = "NO";
	            		}else if(valor.equalsIgnoreCase("true")){
	            			valor = "SI";
	            		}
	            		((Label)component).setValue(valor);
	            	}else if(component instanceof Textbox){
	            		String valor = namedNodeMap.getNamedItem("ValorCampo").getNodeValue();
	            		if(valor.equalsIgnoreCase("false")){
	            			valor = "NO";
	            		}else if(valor.equalsIgnoreCase("true")){
	            			valor = "SI";
	            		}
	            		((Textbox)component).setValue(valor);
	            	}else if(component instanceof Checkbox && namedNodeMap.getNamedItem("NombreTabla").getNodeValue().equals("CamposBoolean")){
	            		Checkbox checkbox = (Checkbox)component;
	            		checkbox.setChecked(new Boolean(namedNodeMap.getNamedItem("ValorCampo").getNodeValue()));
	            	}else if(component instanceof Radiogroup && namedNodeMap.getNamedItem("NombreTabla").getNodeValue().equals("CamposBoolean")){
	            		Radiogroup radiogroup = (Radiogroup)component;
	            		Radio radio = (Radio) this.getFellow(radiogroup.getId()+"_"+namedNodeMap.getNamedItem("ValorCampo").getNodeValue());
	            		radiogroup.setSelectedItem(radio);
	            	}
	            }
	            
	        }
		}
		
		
	}
	
	
}
