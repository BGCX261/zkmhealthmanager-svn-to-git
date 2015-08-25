package healthmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;

public class PerfilAction extends ZKWindow{
	
	@View
	private Textbox tbxNombres;
	@View
	private Textbox tbxApellidos;
	@View
	private Textbox tbxNivel;
	@View
	private Textbox tbxEmail;
	@View
	private Textbox tbxTelefono;
	@View
	private Textbox tbxLogin;
	@View
	private Textbox tbxPassword;
	@View
	private Textbox tbxNewPassword;
	@View
	private Textbox tbxConfirmNewPassword;
	@View
	private Label lbPassword;
	@View
	private Label lbNewPassword;
	@View
	private Label lbConfirmNewPassword;
	@View
	private Checkbox chxCambiar;
	
	@View private Groupbox grdCambioContrasenia;

	@Override
	public void init() throws Exception {
		
		mostrarDatos();
	}
	
	public void mostrarDatos() throws Exception {
		//log.info("cargar datos");
		tbxNombres.setValue(usuarios.getNombres());
		tbxApellidos.setValue(usuarios.getApellidos());
		tbxNivel.setValue(usuarios.getNivel().equals("1") ? "ADMINISTRADOR"
				: "INVITADO");
		tbxEmail.setValue(usuarios.getEmail());
		tbxTelefono.setValue(usuarios.getTel_res() != null ? usuarios.getTel_res().replaceAll("[^0-9]", "") : "");  
		tbxLogin.setValue(usuarios.getLogin());

		checkedCambioPassword(false);
	}
	
	public void checkedCambioPassword(boolean sw) {

		// //log.info("sw: "+sw);
		lbPassword.setVisible(sw);
		lbNewPassword.setVisible(sw);
		lbConfirmNewPassword.setVisible(sw);

		tbxPassword.setVisible(sw);
		tbxNewPassword.setVisible(sw);
		tbxConfirmNewPassword.setVisible(sw);
		grdCambioContrasenia.setOpen(sw);
		

		if (!sw) {
			tbxPassword.setValue("");
			tbxNewPassword.setValue("");
			tbxConfirmNewPassword.setValue("");
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		tbxNombres.setStyle("text-transform:uppercase;background-color:white");
		tbxApellidos
				.setStyle("text-transform:uppercase;background-color:white");
		tbxLogin.setStyle("text-transform:uppercase;background-color:white");

		tbxPassword.setStyle("text-transform:uppercase;background-color:white");
		tbxNewPassword
				.setStyle("text-transform:uppercase;background-color:white");
		tbxConfirmNewPassword
				.setStyle("text-transform:uppercase;background-color:white");

		String mensaje = "Los campos marcados con (*) son obligatorios";

		boolean valida = true;

		if (tbxNombres.getText().trim().equals("")) {
			tbxNombres
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxApellidos.getText().trim().equals("")) {
			tbxApellidos
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxLogin.getText().trim().equals("")) {
			tbxLogin.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(!tbxTelefono.getValue().matches("[0-9]*$") && valida){
			tbxTelefono.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			mensaje = "Nro. de teléfono no valido";
			valida = false;
		}
		
		
		//log.info("usuarios: "+usuarios);

		if (chxCambiar.isChecked()) {
			if (tbxNewPassword.getValue().trim().equals("")) {
				tbxNewPassword
						.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				valida = false;
				mensaje = "La Nueva Clave del usuario no puede ir en blanco";
			} else if (!usuarios.getPassword().equals(tbxPassword.getValue())) {
				tbxPassword
						.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				valida = false;
				mensaje = "Clave actual del usuario es incorrecta";
			} else if (!tbxNewPassword.getValue().equals(
					tbxConfirmNewPassword.getValue())) {
				tbxNewPassword
						.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				tbxConfirmNewPassword
						.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				valida = false;
				mensaje = "La Nueva Clave del usuario no coincide con la que esta confirmando";
			}
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

		return valida;
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		try {
			if(validarForm()){
				//Cargamos los componentes //
				usuarios.setNombres(tbxNombres.getValue().trim().toUpperCase());
				usuarios.setApellidos(tbxApellidos.getValue().trim().toUpperCase());
				usuarios.setEmail(tbxEmail.getValue().trim());
				usuarios.setTel_res(tbxTelefono.getValue().trim().toUpperCase());
				usuarios.setLogin(tbxLogin.getValue().trim().toUpperCase());
				if(chxCambiar.isChecked()){
					usuarios.setPassword(tbxNewPassword.getValue().trim());
				}
				getServiceLocator().getUsuariosService().actualizar(usuarios);
				request.getSession().setAttribute("usuario",
						usuarios);
				cargarDatosSesion();
				mostrarDatos();
				tbxPassword.setValue("");
				tbxNewPassword.setValue("");
				tbxConfirmNewPassword.setValue("");
				
				mostrarDatos();
				
				/*MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se actualizaron satisfactoriamente");*/
				Messagebox.show("Los datos se guardaron satisfactoriamente, se actualizara para que tome los cambios", "Informacion ..", Messagebox.OK,
						Messagebox.INFORMATION, new EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								_recargar(); 
							}
						});
				
			}
		} catch (Exception e) {
			
			MensajesUtil.mensajeError(e, "", this);
		}
	}

}
