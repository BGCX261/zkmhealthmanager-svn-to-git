package healthmanager.controller;

import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.service.ElementoService;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;

public class InicioAction extends GeneralComposer {

	private static final long serialVersionUID = -9145887024839938515L;

	@WireVariable
	private ElementoService elementoService;

	@View
	private Image imgUsuario;
	@View
	private Label lbUsuario;
	@View
	private Label lbEmpresa;
	@View
	private Label lbSucursal;
	@View
	private Label lbRol;
	@View
	private Label lbCentroAtencion;

	@View
	private Image imgLogo;

	@Override
	public void init() throws Exception {
		mostrarDatos();

	}

	public void mostrarDatos() throws Exception {
		lbUsuario.setValue(usuarios.getNombres() + " "
				+ usuarios.getApellidos());
		lbEmpresa.setValue(empresa.getNombre_empresa());
		lbSucursal.setValue(sucursal.getNombre_sucursal());

		Elemento elemento = new Elemento();
		elemento.setTipo("rol");
		elemento.setCodigo(rol_usuario);
		elemento = elementoService.consultar(elemento);

		lbRol.setValue(elemento != null ? elemento.getDescripcion()
				.toUpperCase() : "ROL NO ENCONTRADO");

		lbCentroAtencion
				.setValue(centro_atencion_session != null ? centro_atencion_session
						.getNombre_centro()
						: "CENTRO DE ATENcion NO ENCONTRATDO");

		// imgUsuario.setSrc("/images/perfil.gif");
		imgUsuario.setSrc("/images/perfil_2.png");
		cargarLogo();
	}

	/**
	 * Este metodo es para mostrar el logo de la empresa
	 * 
	 * @author Luis Miguel Hernández
	 * */
	private void cargarLogo() throws Exception {
		if (resolucion != null) {
			if (resolucion.getLogo() != null) {
				imgLogo.setContent(new AImage("logo", resolucion.getLogo()));
			}
		}
	}

	public void irPerfil() throws Exception {
		try {
			Map parametros = new HashMap();
			parametros.put("mostrar_boton_salir", "");

			Component component = Executions.createComponents(
					"/pages/perfil.zul", this, parametros);
			Window windowPerfil = (Window) component;
			windowPerfil.setWidth("340px");
			windowPerfil.setHeight("440px");
			windowPerfil.setClosable(true);
			windowPerfil.setMaximizable(true);
			windowPerfil.setTitle("Modificar Perfil");
			windowPerfil.setMode("modal");

			// Executions.getCurrent().sendRedirect("/pages/perfil.zul");
		} catch (Exception e) {

			MensajesUtil.mensajeError(e, "", this);
		}
	}
}
