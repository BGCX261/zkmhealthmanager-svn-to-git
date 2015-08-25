package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.Roles_usuarios_capsService;
import healthmanager.modelo.service.UsuariosService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.Utilidades;

public class AgendaCitasPrestadorAction extends ZKWindow {

	private static Logger log = Logger.getLogger(Cuota_moderadoraAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@View
	private Listbox lbxCitas;
	@View
	private Datebox dtbxFecha_cita;
	@View
	private Datebox dtbxFecha_cita_final;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_medico;
	@View
	private Textbox tbxNomPrestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_centro;
	@View
	private Textbox tbxNomCentro;
	@View
	private Toolbarbutton btnLimpiarCentro;

	private List<String> lista_centros = new ArrayList<String>();
	private String id_prestador = "";

	public void initAgendaCitas() throws Exception {
	}

	public void imprimir() throws Exception {
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "AgendaCitas");
		paramRequest.put("fecha_cita", dtbxFecha_cita.getValue());
		paramRequest.put("formato", "pdf");
		paramRequest.put("rol_usuario", rol_usuario);
		paramRequest.put("codigo_prestador", tbxCodigo_medico.getValue());

		usuarios.setCodigo(tbxCodigo_medico.getValue());

		getServiceLocator().getServicio(UsuariosService.class).consultar(
				usuarios);
		if (usuarios != null) {
			paramRequest.put("prestador",
					Utilidades.isMedico(rol_usuario) ? usuarios.getNombres()
							+ " " + usuarios.getApellidos() : "");
		}
		paramRequest.put(
				"codigo_centro",
				tbxCodigo_centro.getValue() != null ? tbxCodigo_centro
						.getValue() : "");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void listarCitas() throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

			parameters.put("codigo_prestador_unico",
					tbxCodigo_medico.getValue());
			parameters.put("codigo_centro", tbxCodigo_centro.getValue());

			if (dtbxFecha_cita.getValue() != null
					&& dtbxFecha_cita_final.getValue() != null) {
				if (dtbxFecha_cita.getValue().compareTo(
						dtbxFecha_cita_final.getValue()) > 0) {
					throw new ValidacionRunTimeException(
							"La fecha inicial en la busqueda no puede ser mayor a la fecha final");
				}
				parameters.put("fecha_inicio", dtbxFecha_cita.getValue());
				parameters.put("fecha_final", dtbxFecha_cita_final.getValue());
			} else if (dtbxFecha_cita.getValue() != null) {
				parameters.put("fecha_inicio", dtbxFecha_cita.getValue());
			} else if (dtbxFecha_cita_final.getValue() != null) {
				parameters.put("fecha_final", dtbxFecha_cita_final.getValue());
			}

			log.info("Parametros de Agenda citas: " + parameters);

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

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(codigo_empresa);
		paciente.setCodigo_sucursal(codigo_sucursal);
		paciente.setNro_identificacion(citas.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		Elemento elementoEstado = citas.getElementoEstado();

		Elemento elementoTipo_consulta = new Elemento();
		elementoTipo_consulta.setCodigo(citas.getTipo_consulta());
		elementoTipo_consulta.setTipo("via_ingreso");
		elementoTipo_consulta = getServiceLocator().getElementoService()
				.consultar(elementoTipo_consulta);

		Admision admision = new Admision();
		admision.setCodigo_empresa(citas.getCodigo_empresa());
		admision.setCodigo_sucursal(citas.getCodigo_sucursal());
		admision.setNro_identificacion(citas.getNro_identificacion());
		admision.setCodigo_cita(citas.getCodigo_cita());
		admision = getServiceLocator().getServicio(AdmisionService.class)
				.consultar(admision);

		String color = "none";
		String estado = citas.getEstado();
		String estado_admision = (admision != null ? admision.getEstado() : "");
		String label_estado_cita = (elementoEstado != null ? elementoEstado
				.getDescripcion() : "");

		if (estado_admision.equals("1")) {
			label_estado_cita = "Sala de espera";
		} else {
			if (estado.equals("1")) {
				color = "#FFFF00";
				label_estado_cita = "Por cumplir";
			}
		}

		if (estado_admision.equals("1")) {
			label_estado_cita = "Sala de espera";
			color = "#81F7F3";
		} else if (estado.equals("1")) {
			color = "#FFFF00";
			label_estado_cita = "Por cumplir";
		} else if (estado.equals("2")) {
			color = "#04B404";
			label_estado_cita = "Cumplida";
		} else if (estado.equals("3")) {
			color = "#FA5858";
			label_estado_cita = "Incumplida";
		} else if (estado.equals("4")) {
			color = "#FFFF00";
			label_estado_cita = "Reprogramada";
		} else if (estado.equals("5")) {
			color = "#819FF7";
			label_estado_cita = "Cancelada";
		}

		Listitem listitem = new Listitem();
		listitem.setStyle("background-color:" + color);

		Listcell listcell = new Listcell(label_estado_cita);
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

		return listitem;
	}

	private List<String> listarCentrosPrestador(String id) {
		List<String> centros = new ArrayList<String>();
		if (!id.isEmpty()) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codigo_empresa", codigo_empresa);
			param.put("codigo_sucursal", codigo_sucursal);
			param.put("codigo_usuario", id);
			List<Roles_usuarios_caps> roles = getServiceLocator().getServicio(
					Roles_usuarios_capsService.class).listar(param);
			if (roles != null) {
				for (Roles_usuarios_caps roles_usuarios_caps : roles) {
					centros.add(roles_usuarios_caps.getCodigo_centro());
				}
			}
		}

		return centros;

	}

	@Override
	public void init() {
		parametrizarBandboxPrestador();
		parametrizarBandboxCentro();
	}

	private void parametrizarBandboxPrestador() {
		tbxCodigo_medico.inicializar(tbxNomPrestador, btnLimpiarPrestador);
		tbxCodigo_medico
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {

					@Override
					public void renderListitem(Listitem listitem,
							Prestadores registro) {

						// Extraemos valores
						String nro_identificacion = (String) registro
								.getNro_identificacion();
						String nombres = (String) registro.getNombres();
						String apellidos = (String) registro.getApellidos();

						// Mostramos valores en vista
						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(nro_identificacion));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(nombres));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(apellidos));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Prestadores> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("codigo_empresa",
								sucursal.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());

						parametros.put("limite_paginado", "limit 25 offset 0");

						return getServiceLocator().getServicio(
								PrestadoresService.class).listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Prestadores registro) {

						String nro_identificacion = (String) registro
								.getNro_identificacion();
						String nombre = (String) registro.getNombres() + " "
								+ (String) registro.getApellidos();

						bandbox.setValue(nro_identificacion);
						textboxInformacion.setValue(nombre);
						id_prestador = nro_identificacion;
						// prestador = registro;
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						// prestador = null;
					}

				});
	}

	private void parametrizarBandboxCentro() {
		tbxCodigo_centro.inicializar(tbxNomCentro, btnLimpiarCentro);
		tbxCodigo_centro
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {

						// Extraemos valores
						String nro_identificacion = (String) registro
								.getCodigo_centro();
						String nombre = (String) registro.getNombre_centro();

						// Mostramos valores en vista
						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(nro_identificacion));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(nombre));
						listitem.appendChild(listcell);

						lista_centros = listarCentrosPrestador(id_prestador);
						// log.info("nro_identificacion>>>>>>" + id_prestador);

					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", valorBusqueda.toLowerCase()
								.trim());

						parametros.put("codigo_empresa",
								sucursal.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("limite_paginado", "limit 25 offset 0");
						// log.info("lista_centros>>>>>>" + lista_centros);
						if (!lista_centros.isEmpty()) {
							parametros.put("lista_centros", lista_centros);
						}

						return getServiceLocator().getServicio(
								Centro_atencionService.class)
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Centro_atencion registro) {

						String nro_identificacion = (String) registro
								.getCodigo_centro();
						String nombre = (String) registro.getNombre_centro();

						bandbox.setValue(nro_identificacion);
						textboxInformacion.setValue(nombre);

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}

				});
	}

}
