package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Pertenencia_etnica;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;

import com.framework.constantes.INotas;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.impl.ModuloConsultaIMG;
import healthmanager.modelo.service.GeneralExtraService;

public class Modulo_hcuciAction extends ZKWindow {
	private static Logger log = Logger.getLogger(Modulo_hcuciAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@View
	private Textbox tbxEstado;
	@View
	private Textbox tbxCodigo_administradora;
	@View
	private Bandbox tbxNro_ingreso;
	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxTipo_doc;
	@View
	private Textbox tbxNomPaciente;
	@View
	private Textbox tbxAdministradora;
	@View
	private Textbox tbxContrato;
	@View
	private Textbox tbxNivelEducativo;
	@View
	private Textbox tbxPertenenciaEtnica;

	@View
	private Textbox tbxId_plan;
	@View
	private Textbox tbxSexo_pct;
	@View
	private Textbox tbxFecha_nac;
	@View
	private Textbox tbxTipo_hc;

	// private Tabbox tabboxHCUci;

	@View
	private Tabpanel tabpanelHistoria;
	@View
	private Tabpanel tabpanelAutorizaciones;
	@View
	private Tabpanel tabpanelRecord;
	@View
	private Tabpanel tabpanelremisiones;
	@View
	private Tabpanel tabpanelEvoluciones;
	@View
	private Tabpanel tabpanelOrdenes;
	@View
	private Tabpanel tabpanelNotaEnf;
	@View
	private Tabpanel tabpanelNotaAc;
	@View
	private Tabpanel tabpanelRecetas;
	@View
	private Tabpanel tabpanelConsultas;
	@View
	private Tabpanel tabpanelEgreso;
	@View
	private Tabpanel tabpanelEpicris;

	@View
	private Tab tabEvoluciones;
	@View
	private Tab tabNotaEnf;
	@View
	private Tab tabEgreso;
	@View
	private Tab tabConsultas;
	@View
	private Tab tabEpicris;
	@View
	private Tab tabAutorizaciones;
	@View
	private Tab tabOrdenes;

	@View
	private West westInformacion_paciente;

	@View
	private Toolbar toolbar;
	@View
	private Listbox lbxAtendida;
	private Parametros_empresa parametrosEmpresa;

	@View
	private Label lbFechaIngreso;
	@View
	private Groupbox group_paciente;
	private Admision admision;
	private Map<String, Object> parametros;

	@Override
	public void init() {
		listarCombos();
		try {
			loadEventClose();
			if (admision != null) {
				initModulo_hcuci();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		parametros = map;
		admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
		// log.info("admision ===> "+admision);
	}

	private void loadEventClose() {
		group_paciente.addEventListener("onOpen", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// log.info("" + group_paciente.isOpen());
			}
		});
	}

	public void initModulo_hcuci() throws Exception {
		try {
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
					.getNativeRequest();
			String id_m = request.getParameter("id_menu");
			if (id_m != null) {

			}

			if (parametros == null) {
				parametros = new HashMap();
			}

			if (parametros.containsKey("ocultar_west")) {
				westInformacion_paciente.setVisible(false);
			}

			String tipo_hc = request.getParameter("tipo_hc");
			if (tipo_hc == null) {
				tipo_hc = (String) parametros.get("tipo_hc");
			}

			// log.info("tipo_hc: " + tipo_hc);
			tbxTipo_hc.setValue(tipo_hc);

			if (!tbxTipo_hc.getValue().equals("1")
					&& !tbxTipo_hc.getValue().equals("3")) {
				tabEvoluciones.setVisible(false);
				tabNotaEnf.setVisible(false);
				tabEpicris.setVisible(false);
			}

			if (!tbxTipo_hc.getValue().equals("2")) {
				tabConsultas.setVisible(false);
			}

			if (!tbxTipo_hc.getValue().equals("4")) {
				tabEgreso.setVisible(false);
			}

			boolean bloqueaAdmision = false;
			if (parametros.get("bloqueaAdmision") != null) {
				bloqueaAdmision = (Boolean) parametros.get("bloqueaAdmision");
			}

			if (bloqueaAdmision) {
				tbxNro_ingreso.setDisabled(true);
				Listitem listitem = new Listitem();
				listitem.setValue(admision);
				selectedAdmision(listitem);
				toolbar.setVisible(false);
			} else {
				toolbar.setVisible(false);
				cargarHistoriaClinica();
				// cargarAutorizacion();
				cargarRemisiones();
				if (tbxTipo_hc.getValue().equals("1")
						|| tbxTipo_hc.getValue().equals("3")) {
					cargarEvolucion();
				}
				cargarOrden();
				if (tbxTipo_hc.getValue().equals("1")
						|| tbxTipo_hc.getValue().equals("3")) {
					cargarNota_enf();
				}
				cargarNota_ac();
				cargarReceta();
				if (tbxTipo_hc.getValue().equals("2")) {
					cargarConsultas();
				}
				if (tbxTipo_hc.getValue().equals("4")) {
					cargarEgreso();
				}
				if (tbxTipo_hc.getValue().equals("1")
						|| tbxTipo_hc.getValue().equals("3")) {
					cargarEpicrisis();
				}
			}

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		Parametros_empresa parametrosEmpresaWhere = new Parametros_empresa();
		parametrosEmpresaWhere.setCodigo_empresa(empresa.getCodigo_empresa());
		parametrosEmpresa = getServiceLocator().getServicio(GeneralExtraService.class)
				.consultar(parametrosEmpresaWhere);
	}

	public void listarCombos() {
		listarAtendida();
	}

	public void listarAtendida() {
		lbxAtendida.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue(false);
		listitem.setLabel("Pendiente");
		lbxAtendida.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue(true);
		listitem.setLabel("Atendida");
		lbxAtendida.appendChild(listitem);

		if (lbxAtendida.getItemCount() > 0) {
			lbxAtendida.setSelectedIndex(0);
		}
	}

	public void buscarAdmision(String value, String valor_estado, Listbox lbx)
			throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("codigo_sucursal", tbxTipo_hc.getValue());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			if (!valor_estado.equals("")) {
				parameters.put("estado", valor_estado);
			}

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Admision> list = getServiceLocator().getAdmisionService()
					.listarResultados(parameters);

			lbx.getItems().clear();

			for (Admision dato : list) {

				Paciente paciente = dato.getPaciente();

				String apellidos = (paciente != null ? paciente.getApellido1()
						+ " " + paciente.getApellido2() : "");
				String nombres = (paciente != null ? paciente.getNombre1()
						+ " " + paciente.getNombre2() : "");

				String estado = (dato.getEstado().equals("1") ? "Activo"
						: "Terminada");

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_ingreso() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(apellidos));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(nombres));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(new SimpleDateFormat(
						"yyyy-MM-dd HH:mm").format(dato.getFecha_ingreso())));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(estado));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void selectedAdmision(Listitem listitem) {
		// log.info("ejecutando metodo @selectedAdmision");
		group_paciente.setClosable(true);
		if (listitem.getValue() == null) {

			tbxEstado.setValue("");
			tbxCodigo_administradora.setValue("");
			tbxId_plan.setValue("");
			tbxNro_ingreso.setValue("");

			tbxNro_identificacion.setValue("");
			tbxNomPaciente.setValue("");
			tbxSexo_pct.setValue("");
			tbxFecha_nac.setValue("");
			tbxNivelEducativo.setValue("");
			tbxPertenenciaEtnica.setValue("");
			tbxTipo_doc.setValue("");
			tbxAdministradora.setValue("");
			tbxContrato.setValue("");
			lbFechaIngreso.setValue("");

			lbxAtendida.setSelectedIndex(0);
			group_paciente.setOpen(false);
		} else {
			Admision dato = (Admision) listitem.getValue();
			Paciente paciente = dato.getPaciente();

			Elemento tipo_doc = new Elemento();
			tipo_doc.setTipo("tipo_id");
			tipo_doc.setCodigo((paciente != null ? paciente
					.getTipo_identificacion() : ""));
			tipo_doc = getServiceLocator().getElementoService().consultar(
					tipo_doc);

			Nivel_educativo educacion = new Nivel_educativo();
			educacion.setId(paciente.getCodigo_educativo());
			educacion = getServiceLocator().getNivel_educativoService()
					.consultar(educacion);

			Pertenencia_etnica etnica = new Pertenencia_etnica();
			etnica.setId(paciente.getPertenencia_etnica());
			etnica = getServiceLocator().getPertenencia_etnicaService()
					.consultar(etnica);

			Administradora admin = new Administradora();
			admin.setCodigo(dato.getCodigo_administradora());
			admin = getServiceLocator().getAdministradoraService().consultar(
					admin);

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(dato.getCodigo_empresa());
			contratos.setCodigo_sucursal(dato.getCodigo_sucursal());
			contratos.setCodigo_administradora(dato.getCodigo_administradora());
			contratos.setId_plan(dato.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(
					contratos);

			lbFechaIngreso.setValue(new SimpleDateFormat("yyyy-MM-dd mm:ss")
					.format(dato.getFecha_ingreso()));
			group_paciente.setOpen(true);
			/*
			 * tbxCodigo_prestador.setValue("000000000");
			 * tbxNomPrestador.setValue("MEDICO POR DEFECTO");
			 */

			tbxEstado.setValue(dato.getEstado());
			tbxCodigo_administradora.setValue(dato.getCodigo_administradora());
			tbxId_plan.setValue(dato.getId_plan());
			tbxNro_ingreso.setValue(dato.getNro_ingreso());

			tbxNro_identificacion.setValue(dato.getNro_identificacion());
			tbxNomPaciente.setValue((paciente != null ? paciente.getApellido1()
					+ " " + paciente.getApellido2() + " "
					+ paciente.getNombre1() : ""));
			tbxNivelEducativo.setValue((educacion != null ? educacion
					.getNombre() : ""));
			tbxPertenenciaEtnica.setValue((etnica != null ? etnica.getNombre()
					: ""));

			tbxSexo_pct.setValue((paciente != null ? paciente.getSexo() : ""));
			tbxFecha_nac.setValue((paciente != null ? new SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()) : ""));
			tbxTipo_doc.setValue((tipo_doc != null ? tipo_doc.getDescripcion()
					: ""));
			tbxAdministradora.setValue(dato.getCodigo_administradora() + " - "
					+ (admin != null ? admin.getNombre() : ""));
			tbxContrato.setValue((contratos != null ? contratos.getNombre()
					: ""));

			if (dato.getAtendida()) {
				lbxAtendida.setSelectedIndex(1);
			} else {
				lbxAtendida.setSelectedIndex(0);
			}

			admision = dato;

		}

		cargarHistoriaClinica();
		// cargarAutorizacion();
		cargarRemisiones();
		if (tbxTipo_hc.getValue().equals("1")
				|| tbxTipo_hc.getValue().equals("3")) {
			cargarEvolucion();
		}
		cargarOrden();
		if (tbxTipo_hc.getValue().equals("1")
				|| tbxTipo_hc.getValue().equals("3")) {
			cargarNota_enf();
		}
		cargarNota_ac();
		cargarReceta();
		if (tbxTipo_hc.getValue().equals("2")) {
			cargarConsultas();
		}
		if (tbxTipo_hc.getValue().equals("4")) {
			cargarEgreso();
		}
		if (tbxTipo_hc.getValue().equals("1")
				|| tbxTipo_hc.getValue().equals("3")) {
			cargarEpicrisis();
		}

		tbxNro_ingreso.close();
	}

	public void cargarHistoriaClinica() {
		// log.info("ejecutando metodo @cargarHistoriaClinica()");
		String area_int = "";

		if (admision != null) {
			Citas citas = new Citas();
			citas.setCodigo_empresa(admision.getCodigo_empresa());
			citas.setCodigo_sucursal(admision.getCodigo_sucursal());
			citas.setCodigo_cita(admision.getCodigo_cita());
			citas = getServiceLocator().getCitasService().consultar(citas);
			area_int = (citas != null ? citas.getArea_intervencion() : "");
		}

		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());
		parametros.put("fecha_nac", tbxFecha_nac.getValue());
		parametros.put("area_int", area_int);
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision);
		parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
				Opciones_via_ingreso.REGISTRAR);

		tabpanelHistoria.getChildren().clear();
		Component componente = Executions.createComponents(
				getHistoriaClinica(), this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");

		if (ventana instanceof HistoriaClinicaModel) {
			((HistoriaClinicaModel) ventana)
					.setParentBean((ModuloConsultaIMG) this);
		}
		tabpanelHistoria.appendChild(ventana);
	}

	private String getHistoriaClinica() {
		String url_historia = "/pages/historia_clinica_uci.zul";
		if (parametrosEmpresa.getTipo_historia_clinica().equalsIgnoreCase("02")) {
			url_historia = "/pages/historia_clinica_uci_2.zul";
		} else if (parametrosEmpresa.getTipo_historia_clinica()
				.equalsIgnoreCase("03")) {
			url_historia = "/pages/historia_clinica_uci.zul";
		}
		// log.info("url_historia ===> "+url_historia);
		return url_historia;
	}

	public void cargarAutorizacion() {
		if (parametrosEmpresa != null) {
			if (parametrosEmpresa.getTrabaja_autorizacion()) {
				tabAutorizaciones.setVisible(true);
				Map parametros = new HashMap();
				parametros.put("nro_identificacion",
						tbxNro_identificacion.getValue());
				parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
				parametros.put("estado", tbxEstado.getValue());
				parametros.put("tipo_hc", tbxTipo_hc.getValue());
				parametros.put("admision", admision);

				tabpanelAutorizaciones.getChildren().clear();
				Component componente = Executions.createComponents(
						"/pages/anexo3_entidad.zul", this, parametros);
				final Window ventana = (Window) componente;
				ventana.setWidth("100%");
				ventana.setVflex("1");
				tabpanelAutorizaciones.appendChild(ventana);
			}
		}
	}

	/* cargamos los records */
	public void cargarRecords() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());

		tabpanelRecord.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/anexo3_entidad.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelRecord.appendChild(ventana);
	}

	public void cargarRemisiones() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());

		tabpanelremisiones.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/remisiones.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelremisiones.appendChild(ventana);
	}

	public void cargarEvolucion() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());

		tabpanelEvoluciones.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/evolucion_medica.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelEvoluciones.appendChild(ventana);
	}

	public void cargarOrden() {
		boolean cargar = true;
		if (parametrosEmpresa != null) {
			cargar = parametrosEmpresa.getTrabaja_ordenes();
		}
		if (cargar) {
			Map parametros = new HashMap();
			parametros.put("nro_identificacion",
					tbxNro_identificacion.getValue());
			parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
			parametros.put("estado", tbxEstado.getValue());
			parametros.put("codigo_administradora",
					tbxCodigo_administradora.getValue());
			parametros.put("id_plan", tbxId_plan.getValue());
			parametros.put("sexo_pct", tbxSexo_pct.getValue());
			parametros.put("fecha_nac", tbxFecha_nac.getValue());
			parametros.put("tipo_hc", tbxTipo_hc.getValue());

			tabpanelOrdenes.getChildren().clear();
			Component componente = Executions.createComponents(
					"/pages/orden_servicio.zul", this, parametros);
			final Window ventana = (Window) componente;
			ventana.setWidth("100%");
			ventana.setVflex("1");
			tabpanelOrdenes.appendChild(ventana);
		} else {
			tabOrdenes.setVisible(false);
		}
	}

	public void cargarNota_enf() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());
		parametros.put("tipo", INotas.NOTAS_ENFERMERIA);

		tabpanelNotaEnf.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/nota_enfermeria.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelNotaEnf.appendChild(ventana);
	}

	public void cargarNota_ac() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());
		parametros.put("tipo", INotas.NOTAS_ACLARATORIAS);

		tabpanelNotaAc.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/nota_aclaratoria.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelNotaAc.appendChild(ventana);
	}

	public void cargarReceta() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);

		tabpanelRecetas.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/receta_rips.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelRecetas.appendChild(ventana);
	}

	public void cargarConsultas() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());

		tabpanelConsultas.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/consulta_externa_hc.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelConsultas.appendChild(ventana);
	}

	public void cargarEgreso() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("sexo_pct", tbxSexo_pct.getValue());
		parametros.put("fecha_nac", tbxFecha_nac.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());

		tabpanelEgreso.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/egreso_urgencias.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelEgreso.appendChild(ventana);
	}

	public void cargarEpicrisis() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());

		tabpanelEpicris.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/epicrisis_hv.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelEpicris.appendChild(ventana);
	}

	public void guardarDatos() throws Exception {
		try {
			if (tbxNro_ingreso.getValue().equals("")) {
				throw new Exception("Seleccione un ingreso de paciente");
			}
			Admision admision = new Admision();
			admision.setCodigo_empresa(empresa.getCodigo_empresa());
			admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			admision.setNro_identificacion(tbxNro_identificacion.getValue());
			admision.setNro_ingreso(tbxNro_ingreso.getValue());
			admision = getServiceLocator().getAdmisionService().consultar(
					admision);
			if (admision == null) {
				throw new Exception("No hay admision que actualizar");
			}
			admision.setAtendida((Boolean) lbxAtendida.getSelectedItem()
					.getValue());


			Messagebox.show("La admision se actualizó satisfactoriamente",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void verRegistros() throws Exception {
		try {
			if (tbxNro_identificacion.getValue().equals("")) {
				throw new Exception("Seleccione una admision valida");
			}
			Map parametros = new HashMap();
			parametros.put("nro_identificacion",
					tbxNro_identificacion.getValue());
			parametros.put("nro_ingreso", tbxNro_ingreso.getValue());

			Component componente = Executions.createComponents(
					"verRegistros_admision.zul", this, parametros);
			final Window ventana = (Window) componente;
			ventana.setWidth("990px");
			ventana.setHeight("400px");
			ventana.setClosable(true);
			ventana.setMaximizable(true);
			ventana.setTitle("CONSULTAR REGISTROS " + tbxNro_ingreso.getValue()
					+ " PACIENTE  " + tbxNomPaciente.getValue());
			ventana.setMode("modal");
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}
}
