package healthmanager.controller.exception;

import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.res.Res;

public class ExceptionAction extends Window implements AfterCompose {

	private static Logger log = Logger.getLogger(ExceptionAction.class);

	private Label lbMensajeExcepcion;
	private Label lbCausaExcepcion;
	private Textbox tbxStacktrace;
	private Button btnEnviarError;

	@Override
	public void afterCompose() {

		lbMensajeExcepcion = (Label) this.getFellow("lbMensajeExcepcion");
		lbCausaExcepcion = (Label) this.getFellow("lbCausaExcepcion");
		tbxStacktrace = (Textbox) this.getFellow("tbxStacktrace");
		setBtnEnviarError((Button) this.getFellow("btnEnviarError"));

		Map<String, Object> parametros = (Map<String, Object>) Executions
				.getCurrent().getArg();

		String msgAdicinal = (String) parametros.get("msgAdicinal");
		if (msgAdicinal != null)
			msgAdicinal = msgAdicinal.trim();
		else
			msgAdicinal = "";

		mostrarError((Throwable) parametros.get("throwable"), msgAdicinal);

	}

	public String mostrarError(Throwable throwable, String msgAdicinal) {
		try {
			String mensaje = msgAdicinal.isEmpty() ? "" : (msgAdicinal + "\n")
					+ throwable.getMessage();
			String causa = throwable.getCause() != null ? throwable.getCause()
					.getMessage() : lbCausaExcepcion.getValue();

			if (causa == null)
				causa = "";

			lbMensajeExcepcion.setValue(mensaje.isEmpty() ? lbMensajeExcepcion
					.getValue() : mensaje);
			lbCausaExcepcion.setValue(causa.isEmpty() ? lbCausaExcepcion
					.getValue() : causa);
			tbxStacktrace.setValue(Res.getStackTrace(throwable));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return "Ha ocurriodo un error inesperado, se ha enviado un correo a soporte para darle solucion al problema, si el problema persiste favor comunicarse con el proveedor del servicio.";
	}

	public void setBtnEnviarError(Button btnEnviarError) {
		this.btnEnviarError = btnEnviarError;
	}

	public Button getBtnEnviarError() {
		return btnEnviarError;
	}

}
