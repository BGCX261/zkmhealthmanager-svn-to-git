package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo2;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.His_triageService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IVias_ingreso;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.MensajesUtil;

public class Anexo2Action extends ZKWindow {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(Anexo2Action.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	private GeneralExtraService generalExtraService;

	@View
	private Listbox lbxFormato;
	@View
	private Auxheader auxheaderAsistencial;

	@View
	private Grid gridResultado;
	@View
	private Datebox dtxFecha;
	@View
	private Textbox tbxValueAdmision;
	@View
	private Textbox tbxVia_ingreso;
	@View
	private Groupbox groupboxConsultar;

	private Admision admision_seleccionada;

	@View
	private Div divAsistencial;

	@View
	private Caption captionConsultar;

	private Map<String, String> vias;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() throws Exception {
		dtxFecha.setValue(new Date());
		List<Elemento> listado_vias = getServiceLocator().getServicio(
				ElementoService.class).listar("via_ingreso");
		vias = new HashMap<String, String>();
		for (Elemento elemento : listado_vias) {
			vias.put(elemento.getCodigo(), elemento.getDescripcion());
		}
		parametrizarResultadoPaginado();
		buscarDatos();
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		tbxVia_ingreso.setValue((String) request
				.getParameter(IVias_ingreso.VIA_INGRESO));
	}

	public void accionForm(String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
			groupboxConsultar.setVisible(true);
		} else {
			groupboxConsultar.setVisible(false);
		}
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Admision> listarResultados(
					Map<String, Object> parametros) {
				List<Admision> listado = getServiceLocator()
						.getAdmisionService().listarResultados(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getAdmisionService()
						.totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				Admision admision = (Admision) dato;
				if (admision.getRealizo_triage()) {
					His_triage historia = new His_triage();
					historia.setCodigo_empresa(admision.getCodigo_empresa());
					historia.setCodigo_sucursal(admision.getCodigo_sucursal());
					historia.setNro_ingreso(admision.getNro_ingreso());
					historia.setIdentificacion(admision.getNro_identificacion());
					historia = getServiceLocator().getServicio(
							His_triageService.class).consultar(historia);
					if (historia != null
							&& historia.getAdmitido().equalsIgnoreCase("S")) {
						Row f = crearFila(admision, historia.getNivel_triage());
						return f;
					}
				} else {
					Row f = crearFila(admision, "N/D");
					return f;
				}
				return null;
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado, 8);
	}

	public void buscarDatos() throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("fecha", new SimpleDateFormat("yyyy-MM-dd")
					.format(dtxFecha.getValue()));
			parameters.put("via_ingreso", IVias_ingreso.URGENCIA);
			parameters.put("paramTodo", "");
			parameters.put("value", "%"
					+ tbxValueAdmision.getValue().toUpperCase().trim() + "%");
			parameters.put("codigo_centro",
					centro_atencion_session.getCodigo_centro());
			parameters
					.put("order",
							"fecha_ingreso asc, creacion_date,apellido1,apellido2,nombre1,nombre2");

			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFila(final Admision admision, final String triage) {
		final Row fila = new Row();

		Paciente paciente = admision.getPaciente();

		String apellidos = (paciente != null ? paciente.getApellido1() + " "
				+ paciente.getApellido2() : "");
		String nombres = (paciente != null ? paciente.getNombre1() + " "
				+ paciente.getNombre2() : "");

		// fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(admision.getNro_ingreso() + ""));
		fila.appendChild(new Label(admision.getNro_identificacion() + ""));
		fila.appendChild(new Label(apellidos));
		fila.appendChild(new Label(nombres));
		fila.appendChild(new Label(vias.get(admision.getVia_ingreso())));
		Label label = new Label(
				admision.getFecha_atencion() != null ? simpleDateFormat
						.format(admision.getFecha_atencion())
						: "SIN FECHA DE ATENCIÓN");
		if (admision.getFecha_atencion() == null)
			label.setStyle("color:red;font-weight:bold");
		String val_triage = "N/D";
		if (triage.equalsIgnoreCase("1")) {
			val_triage = "I";
		} else if (triage.equalsIgnoreCase("2")) {
			val_triage = "II";
		} else if (triage.equalsIgnoreCase("3")) {
			val_triage = "III";
		} else if (triage.equalsIgnoreCase("4")) {
			val_triage = "IV";
		}
		Label lbl = new Label(val_triage);
		fila.appendChild(lbl);
		fila.appendChild(label);
		Image img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setTooltiptext("Imprimir");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				imprimir(triage, admision);
			}
		});
		fila.appendChild(img);
		return fila;
	}

	public void imprimir(String triage, Admision admision) throws Exception {
		Anexo2 anexo2 = new Anexo2();
		anexo2.setCodigo_empresa(admision.getCodigo_empresa());
		anexo2.setCodigo_sucursal(admision.getCodigo_sucursal());
		anexo2.setNro_ingreso(admision.getNro_ingreso());
		anexo2.setNro_documento(admision.getNro_identificacion());
		anexo2 = generalExtraService.consultar(anexo2);

		if (anexo2 != null) {
			anexo2.setTriage(triage);
			generalExtraService.actualizar(anexo2);
			Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", "Anexo2");
			paramRequest.put("codigo_empresa", empresa.getCodigo_empresa());
			paramRequest.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			paramRequest.put("nro_ingreso", admision.getNro_ingreso());
			paramRequest.put("nro_documento", admision.getNro_identificacion());
			paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
					.toString());

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, paramRequest);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			MensajesUtil
					.mensajeAlerta(
							"NO existe anexo 2",
							"No se ha generado el anexo tecnico 2 porque aún el paciente no se ha atendido");
		}
	}

	public void onSeleccionarAdmision(Listitem listitem,
			Enfermera_signos enfermera_signos) throws Exception {
		try {
			// log.info("Item de la admision: " + listitem);
			Admision admision = (Admision) listitem.getValue();
			admision_seleccionada = admision;
			accionForm("registrar");
			mostrarDatos();
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil
					.mensajeError(e, "Admision no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil.mensajeError(e, "Admision no valida Exception", this);
		}
	}

	public void onSeleccionarAdmision(Listitem listitem) throws Exception {
		try {
			// log.info("Item de la admision: " + listitem);
			Admision admision = (Admision) listitem.getValue();
			admision_seleccionada = admision;
			accionForm("registrar");
			mostrarDatos();
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil
					.mensajeError(e, "Admision no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil.mensajeError(e, "Admision no valida Exception", this);
		}
	}

	public void mostrarDatos() throws Exception {
		if (admision_seleccionada != null) {

		} else {
			throw new Exception("No hay una admision seleccionada");
		}

	}

}
