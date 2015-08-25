package healthmanager.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Registros_entradas;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Registros_entradasService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zul.A;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;

import com.framework.constantes.IVias_ingreso;
import com.softcomputo.composer.constantes.IParametrosSesion;

public class IndexPageAction extends ZKWindow implements DesktopCleanup,
		SessionCleanup {

	@View
	private A linkSesionPerdida;
	@View
	private Iframe iframeContenidoPpal;
	@View
	private Iframe iframeContenidoSouth;
	@View
	private Borderlayout borderLayoutContenedor;

	@View
	private Div divSesionPerdida;
	@View
	private Div divSesionPerdida2;

	@Override
	public void beforeInit() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Object object = request.getSession().getAttribute(
				IParametrosSesion.PARAM_USUARIO);
		if (object == null) {
			Executions.sendRedirect("../");
		}
	}

	@Override
	public void init() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		String pageInicioSesion = request.getParameter("pageInicioSesion");

		Object object = request.getSession().getAttribute(
				IParametrosSesion.PARAM_USUARIO);

		boolean user_session = false;
		Usuarios usuarios = null;

		if (object instanceof healthmanager.modelo.bean.Usuarios) {
			usuarios = (Usuarios) request.getSession().getAttribute(
					IParametrosSesion.PARAM_USUARIO);
		}

		if (usuarios != null) {
			user_session = true;
		} else {
			user_session = false;
			Executions.sendRedirect("../");
		}

		if (!user_session) {
			if (pageInicioSesion != null) {
				divSesionPerdida.setVisible(true);
				linkSesionPerdida.setHref("/pages/inicioSesion"
						+ pageInicioSesion + ".zul");
			} else {
				divSesionPerdida2.setVisible(true);
			}
		} else {
			iframeContenidoPpal.setVisible(true);
			iframeContenidoPpal
					.setSrc("/pages/mainAdministrador.zul?pageInicioSesion="
							+ pageInicioSesion);
			iframeContenidoSouth.setSrc("/pages/foot.zul");
			borderLayoutContenedor.setVisible(true);
		}

		// getDesktop().getWebApp().getConfiguration()
		// .addListener(this.getClass());
	}

	@Override
	public void cleanup(Desktop desktop) throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		HttpSession session = request.getSession();
		if (session.getAttribute(IVias_ingreso.ADMISION_PACIENTE) != null) {
			Admision admision_seleccionada = (Admision) session
					.getAttribute(IVias_ingreso.ADMISION_PACIENTE);
			if (admision_seleccionada != null) {
				admision_seleccionada.setAtendiendo(false);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("admision", admision_seleccionada);
				// map.put("parametros_empresa", parametros_empresa);
				getServiceLocator().getAdmisionService().actualizar(map);
				session.removeAttribute(IVias_ingreso.ADMISION_PACIENTE);
				session.removeAttribute(IVias_ingreso.SERVICE_LOCATOR);
			}
		}

		if (session.getAttribute(IParametrosSesion.PARAM_REG_ENTRADAS) != null) {
			Registros_entradas registros_entradas = (Registros_entradas) session
					.getAttribute(IParametrosSesion.PARAM_REG_ENTRADAS);
			if (!registros_entradas.getEstado().equals("2")) {
				registros_entradas.setFecha_egreso(new Timestamp(new Date()
						.getTime()));
				getServiceLocator()
						.getServicio(Registros_entradasService.class)
						.actualizar(registros_entradas);
			}
		}

	}

	@Override
	public void cleanup(Session arg0) throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		HttpSession session = request.getSession();
		if (session.getAttribute(IVias_ingreso.ADMISION_PACIENTE) != null) {
			// log.info("Ejecutando el metodo cleanup(Session desktop)");
			Admision admision_seleccionada = (Admision) session
					.getAttribute(IVias_ingreso.ADMISION_PACIENTE);
			admision_seleccionada.setAtendiendo(false);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("admision", admision_seleccionada);
			// map.put("parametros_empresa", parametros_empresa);
			getServiceLocator().getAdmisionService().actualizar(map);
			session.removeAttribute(IVias_ingreso.ADMISION_PACIENTE);
			session.removeAttribute(IVias_ingreso.SERVICE_LOCATOR);
		}

	}

}
