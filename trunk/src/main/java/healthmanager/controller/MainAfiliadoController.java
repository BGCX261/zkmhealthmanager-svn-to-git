package healthmanager.controller;

import java.sql.Timestamp;
import java.util.Date;

import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Registros_entradas;
import healthmanager.modelo.service.Registros_entradasService;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkex.zul.Fisheyebar;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import com.framework.res.TabsValidator;

public class MainAfiliadoController extends ZKWindow {

	@View
	Fisheyebar fisheyebarMenu;
	@View
	private Label cerrar_sesion;

	@View
	private Label lbUsuario;
	@View
	private Label lbEmpresa;
	@View
	private Label lbSucursal;
	@View
	private Listbox lbxRoles;

	@View
	Tabs tab_contenedora;
	@View
	Tabpanels tabpanelcontenedora;

	private Paciente paciente;

	public void verificamosSession() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Object userEnSession = request.getSession().getAttribute("usuario");
		if (userEnSession != null) {
			if (userEnSession instanceof Paciente) {
				paciente = (Paciente) userEnSession;
			} else {
				Executions.sendRedirect("../");
				return;
			}
		} else {
			Executions.sendRedirect("../");
			return;
		}
	}

	@Override
	public void beforeInit() {
		verificamosSession();
	}

	@Override
	public void init() {
		loadDatosSession();
		inicializarMenu();
	}

	private void loadDatosSession() {
		cerrar_sesion.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				HttpServletRequest request = (HttpServletRequest) Executions
						.getCurrent().getNativeRequest();
				if (request.getSession().getAttribute("registros_entradas") != null) {
					Registros_entradas registros_entradas = (Registros_entradas) request
							.getSession().getAttribute("registros_entradas");
					registros_entradas.setFecha_egreso(new Timestamp(new Date()
							.getTime()));
					registros_entradas.setEstado("2");
					getServiceLocator().getServicio(
							Registros_entradasService.class).actualizar(
							registros_entradas);
					request.getSession().setAttribute("registros_entradas",
							registros_entradas);
				}
				request.getSession().invalidate();
				Executions.sendRedirect("inicioSesionPaciente.zul");
			}
		});

		lbUsuario.setValue(paciente.getNombreCompleto());
		lbEmpresa.setValue(empresa.getNombre_empresa());
		lbSucursal.setValue(sucursal.getNombre_sucursal());
	}

	public void addTab(String name, String url) {
		TabsValidator.addTabParam(name, url, tab_contenedora,
				tabpanelcontenedora, this);
	}

	public void inicializarMenu() {
		// Asignamos las imagenes //
		String img = Labels.getLabel("app.images");

		fisheyebarMenu.getChildren().clear();
		if (!sucursal.getTipo().equals(SucursalAction.IPS)) {
			Fisheye fsh_cita_medica = new Fisheye();
			fsh_cita_medica.setStyle("cursor:pointer");
			fsh_cita_medica.setId("inicio");
			fsh_cita_medica.setTooltiptext("Cita médica");
			fisheyebarMenu.appendChild(fsh_cita_medica);

			// Asignamos los eventos //
			fsh_cita_medica.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					addTab("Cita médica", "citas_web.zul?type=01");
				}
			});
			fsh_cita_medica.setImage("/images/" + img
					+ "/iconos_menu/imagesCitaMedica.png");

			Fisheye fsh_cancelar_cita = new Fisheye();
			fsh_cancelar_cita.setStyle("cursor:pointer");
			fsh_cancelar_cita.setId("Cancelacion");
			fsh_cancelar_cita.setTooltiptext("Cancelar Cita médica");
			fisheyebarMenu.appendChild(fsh_cancelar_cita);

			fsh_cancelar_cita.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					addTab("Cancelar Cita médica",
							"control_citas_pacientes_web.zul?type=01");

				}
			});
			fsh_cancelar_cita.setImage("/images/" + img
					+ "/iconos_menu/CancelarCita.jpg");

			Fisheye fsh_laboratorio = new Fisheye();
			fsh_laboratorio.setStyle("cursor:pointer");
			fsh_laboratorio.setId("laboratorio");
			fsh_laboratorio.setTooltiptext("Laboratorio");
			fisheyebarMenu.appendChild(fsh_laboratorio);

			fsh_laboratorio.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					addTab("Laboratorios", "laboratorios_web.zul");
				}
			});
			fsh_laboratorio.setImage("/images/" + img
					+ "/iconos_menu/laboratorio.jpg");

		} else {
			// Creamos los submenus //
			Fisheye fsh_cita_medica = new Fisheye();
			fsh_cita_medica.setStyle("cursor:pointer");
			fsh_cita_medica.setId("inicio");
			fsh_cita_medica.setTooltiptext("Cita médica");
			fisheyebarMenu.appendChild(fsh_cita_medica);

			// Asignamos los eventos //
			fsh_cita_medica.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					addTab("Cita médica", "citas_web.zul?type=01");
				}
			});
			fsh_cita_medica.setImage("/images/" + img
					+ "/iconos_menu/imagesCitaMedica.png");

			Fisheye fsh_cancelar_cita = new Fisheye();
			fsh_cancelar_cita.setStyle("cursor:pointer");
			fsh_cancelar_cita.setId("Cancelacion");
			fsh_cancelar_cita.setTooltiptext("Cancelar Cita médica");
			fisheyebarMenu.appendChild(fsh_cancelar_cita);

			fsh_cancelar_cita.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					addTab("Cancelar Cita médica",
							"control_citas_pacientes_web.zul?type=01");

				}
			});
			fsh_cancelar_cita.setImage("/images/" + img
					+ "/iconos_menu/CancelarCita.jpg");

			Fisheye fsh_laboratorio = new Fisheye();
			fsh_laboratorio.setStyle("cursor:pointer");
			fsh_laboratorio.setId("laboratorio");
			fsh_laboratorio.setTooltiptext("Laboratorio");
			fisheyebarMenu.appendChild(fsh_laboratorio);

			fsh_laboratorio.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					addTab("Laboratorios", "laboratorios_web.zul");
				}
			});
			fsh_laboratorio.setImage("/images/" + img
					+ "/iconos_menu/laboratorio.jpg");

			Fisheye fsh_carnet = new Fisheye();
			fsh_carnet.setStyle("cursor:pointer");
			fsh_carnet.setId("informe");
			fsh_carnet.setTooltiptext("Carnet");
			fisheyebarMenu.appendChild(fsh_carnet);

			fsh_carnet.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					
				}
			});
			fsh_carnet.setImage("/images/" + img + "/iconos_menu/carnet.png");

			Fisheye fsh_benefiarios = new Fisheye();
			fsh_benefiarios.setStyle("cursor:pointer");
			fsh_benefiarios.setId("beneficario");
			fsh_benefiarios.setTooltiptext("Benefiarios");
			fisheyebarMenu.appendChild(fsh_benefiarios);

			fsh_benefiarios.addEventListener("onClick", new EventListener() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					
				}
			});
			fsh_benefiarios.setImage("/images/" + img
					+ "/iconos_menu/beneficiarios.jpg");

		}
	}

}