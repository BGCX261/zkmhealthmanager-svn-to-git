package healthmanager.controller;

import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Paciente;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

public class InicioAfiliadoController extends ZKWindow {

	@View private Image imgUsuario;
	@View private Label lbUsuario;
	
	private Paciente paciente; 
	
	@Override
	public void init() {
        cargarDatosAfiliado();
	} 

	private void cargarDatosAfiliado() {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		paciente = (Paciente) request.getSession().getAttribute("usuario");
		loadDatos();
	}

	private void loadDatos() {
		 try {
			 lbUsuario.setValue(paciente.getNombreCompleto());
			 Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
			 afiliaciones_me.setCodigo_empresa(sucursal.getCodigo_empresa());
			 afiliaciones_me.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			 afiliaciones_me.setNro_identificacion_afiliado(paciente.getNro_identificacion());
			 afiliaciones_me = getServiceLocator().getAfiliacionesMeService().consultar(afiliaciones_me);
			 if(afiliaciones_me != null){
				 if(afiliaciones_me.getFoto_afiliados() != null){
					 AImage aImage = new AImage("foto_usr", afiliaciones_me.getFoto_afiliados());
					 imgUsuario.setContent(aImage); 
				 }
			 }
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

}
