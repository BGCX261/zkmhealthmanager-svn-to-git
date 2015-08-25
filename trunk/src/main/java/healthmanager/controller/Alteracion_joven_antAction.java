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
public class Alteracion_joven_antAction  extends ZKWindow{
	
//	private static Logger log = Logger.getLogger(Alteracion_joven_antAction.class);
	
	private Admision admision_seleccionada;
	private Map<Object,Object> bean;
	
	@View private Label lbIdMed;
	@View private Label lbNombreMed;
	@View private Label lbEsp;
	
	@View private Label lblTanner1;
	@View private Label lblTanner2;
	@View private Label lblTanner3;
	
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
		
		String tanner1 = "Estadio genital 1(G1) o prepuberal\n"
				+ "-Testiculos:Volumen menor de 1.5cc\n"
				+ "-Pene:Infantil\nEstadio genital 2(G2)\n"
				+ "-Testiculos:1.6cc a 6cc\n"
				+ "-Escroto:Enrojecido, mas delgado y grande\n"
				+ "-Pene:Sin cambios\n"
				+ "Estadio genital 3(G3)\n"
				+ "-Testiculos:6cc a 12cc\n"
				+ "-Escroto:Mayor aumento\n"
				+ "-Pene:Aumento en longitud\n"
				+ "Estadio genital 4(G4)\n"
				+ "-Testiculos:12cc a 20cc\n"
				+ "-Escroto:Mayor aumento oscurecimiento y adelgazamiento\n"
				+ "-Pene:Aumento en longitud y diametro\n"
				+ "Estadio genital 5(G5)\n"
				+ "-Testiculos:Mayor a 20cc\n"
				+ "-Escroto y pene:Adultos";
		lblTanner1.setValue(tanner1);

		String tanner2 = "Estadio mamario 1(S1)\n"+
				"-Seno:Prepuberal,sin tejido glandular\n"+
				"-Areola y pezon:_a aereola se conforma\n"+
				" a la General del torax\n"+
				"Estadio mamario 2(S2)\n"+
				"-Seno:Boton mamario,pequeña cantidad de\n"+
				" Tejido glandular\n"+
				"-Areola:Ampliacion de la areola\n"+
				"Estadio mamario 3(S3)\n"+
				"-Seno:Mayor y mas elevado,se extiende por\n"+
				" fuera del perimetro areolar\n"+
				"-Areola:Continua crecimiento pero hace\n"+
				" contor no con el seno\n"+
				"Estadio mamario 4(S4)\n"+
				"-Seno:Continua crecimiento y elevandose\n"+
				"-Areola:Forma un monticulo que sobresale\n"+
				" del contorno mamario\n"+
				"Estadio mamario 5(G5)\n"+
				"-Seno:Adulto\n"+
				"-Areola:La areola y el seno sobre el mismo\n"+
				" plano, con el pezon proyectandose sobre la areola";
		lblTanner2.setValue(tanner2);
		
		String tanner3 = "Estadio púbico 1(P1)\n"+
				"-No se observa vello\n"+
				"Estadio pubico 2(P2)\n"+
				"-Hombre:Pequeña cantidad de pelo escamo samente\n"+
				" pigmentado en la base del pene y escroto\n"+
				"-Mujer:Pequeña cantidad de pelo en labios mayores\n"+
				"Estadio publico 3(P3)\n"+
				"-Hombre:Cantidad de pelo moderadamente mas rizado,\n"+
				"pigmentado y grueso, mayor extension lateral\n"+
				"-Mujer:Caracteristicas similares en consistencia,\n"
				+ " un poco más extendido\n"+
				"Estadio púbico 4(P4)\n"+
				"-vello pubiano de tipo adulto, pero sin extension en\n"
				+ " la cara interna de los muslos\n"+
				"Estadio púbico 5(P5)\n"+
				"-vello de tipo adulto con extension a la cara interna\n"
				+ " de los muslos";
		lblTanner3.setValue(tanner3);
	
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
	            if(this.getFellowIfAny("label_"+i)!=null){
	            	Component component = this.getFellow("label_"+i);
	            	
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
