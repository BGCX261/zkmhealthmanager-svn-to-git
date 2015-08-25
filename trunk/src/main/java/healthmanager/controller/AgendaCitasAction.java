package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.AdmisionService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.framework.util.Utilidades;

public class AgendaCitasAction extends ZKWindow {

	private static Logger log = Logger.getLogger(Cuota_moderadoraAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@View
	private Listbox lbxCitas;
	@View
	private Datebox dtbxFecha_cita;

	public void initAgendaCitas() throws Exception {

	}

	public void imprimir() throws Exception {
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "AgendaCitas");
		paramRequest.put("fecha_cita", dtbxFecha_cita.getValue());
		paramRequest.put("formato", "pdf");
		paramRequest.put("rol_usuario", rol_usuario);
		paramRequest.put("codigo_prestador", usuarios.getCodigo());
		paramRequest.put("prestador",
				Utilidades.isMedico(rol_usuario) ? usuarios.getNombres() + " "
						+ usuarios.getApellidos() : "");
		paramRequest.put(
				"codigo_centro",
				centro_atencion_session != null ? centro_atencion_session
						.getCodigo_centro() : "");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

	public void listarCitas() throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("fecha_cita", dtbxFecha_cita.getValue());
			parameters.put("codigo_prestador_unico", usuarios.getCodigo());
			parameters.put(
					"codigo_centro",
					centro_atencion_session != null ? centro_atencion_session
							.getCodigo_centro() : "");

			// log.info("Parametros de Agenda citas: " + parameters);

			List<Citas> lista_datos = getServiceLocator().getCitasService()
					.listar(parameters);
			lbxCitas.getItems().clear();

			for (Citas citas : lista_datos) {
				lbxCitas.appendChild(crearFilas(citas));
			}

			lbxCitas.setMold("paging");
			lbxCitas.setPageSize(20);

			lbxCitas.applyProperties();
			lbxCitas.invalidate();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Listitem crearFilas(Object objeto) throws Exception {
		final Citas citas = (Citas) objeto;
		Admision admision = new Admision();
		admision.setCodigo_empresa(citas.getCodigo_empresa());
		admision.setCodigo_sucursal(citas.getCodigo_sucursal());
		admision.setNro_identificacion(citas.getNro_identificacion());
		admision.setCodigo_cita(citas.getCodigo_cita());
		admision = getServiceLocator().getServicio(AdmisionService.class)
				.consultar(admision);
		Paciente paciente = null;
		if (admision == null) {
			paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(citas.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);
		} else {
			paciente = admision.getPaciente();
		}

		Elemento elementoEstado = citas.getElementoEstado();
		Elemento elementoTipo_consulta = new Elemento();
		elementoTipo_consulta.setCodigo(citas.getTipo_consulta());
		elementoTipo_consulta.setTipo("via_ingreso");
		elementoTipo_consulta = getServiceLocator().getElementoService()
				.consultar(elementoTipo_consulta);

		String color = "none";
		String estado = citas.getEstado();
		String estado_admision = (admision != null ? admision.getEstado() : "");
		String estado_cita = (elementoEstado != null ? elementoEstado
				.getDescripcion() : "");
		if (estado_admision.equals("1")) {
			estado_cita = "Sala de espera";
			estado = "6";
		}
		if (estado.equals("1")) {
			color = "#FFFF00";
			estado_cita = "Por cumplir";
		} else if (estado.equals("2")) {
			color = "#04B404";
		} else if (estado.equals("3")) {
			color = "#FA5858";
		} else if (estado.equals("4")) {
			color = "#FFFF00";
			estado_cita = "Por cumplir";
		} else if (estado.equals("5")) {
			color = "#819FF7";
			estado_cita = "Cancelada";
		} else if (estado.equals("6")) {
			color = "#81F7F3";
		}

		Listitem listitem = new Listitem();
		listitem.setStyle("background-color:" + color);

		Listcell listcell = new Listcell(estado_cita);
		listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
		listitem.appendChild(listcell);

		listcell = new Listcell(citas.getNro_identificacion());
		listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
		listitem.appendChild(listcell);

		listcell = new Listcell((paciente != null ? paciente.getApellido1()
				+ " " + paciente.getApellido2() : ""));
		listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
		listitem.appendChild(listcell);

		listcell = new Listcell((paciente != null ? paciente.getNombre1() + " "
				+ paciente.getNombre2() : ""));
		listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
		listitem.appendChild(listcell);

		listcell = new Listcell(
				(elementoTipo_consulta != null ? elementoTipo_consulta
						.getDescripcion() : ""));
		listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
		listitem.appendChild(listcell);

		listcell = new Listcell(new SimpleDateFormat("dd/MM/yyyy").format(citas
				.getFecha_cita()) + " " + citas.getHora());
		listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
		listitem.appendChild(listcell);

		/*
		 * listcell = new
		 * Listcell((admision!=null?(admision.getEstado().equals("2"
		 * )?"Atendido":"En espera"):"NO TIENE"));
		 * listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
		 * listitem.appendChild(listcell);
		 */

		return listitem;
	}

	@Override
	public void init() {
		// log.info("ROL USUARIO ===> " + rol_usuario);
	}
	
	public void listarCitasOnTimer() throws Exception {
		log.info("Ejecutando metodo @listarCitasOnTimer()");
		listarCitas();
	}

}
