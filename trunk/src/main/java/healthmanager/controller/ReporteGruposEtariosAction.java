package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Grupos_etareos;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Grupos_etareosService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.res.L2HContraintDate;
import com.framework.res.L2HContraintDate.TypeInit;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class ReporteGruposEtariosAction extends GeneralComposer implements
		WindowRegistrosIMG {
	
	@WireVariable 
	private Grupos_etareosService grupos_etareosService;
	@WireVariable 
	private ContratosService contratosService;

	private static final String CIE = "cie";
	private static final String PROCEDIMIENTO_CONSULTA = "pro_con";
	private static final String PRESTADOR_SERVICIO = "pres_ser";
	private static final String SERVICIO = "serv";
	private static final String ADMINISTRADORA = "admins_";
	private static final String CENTRO_SALUD = "centro_salud";

	public static final String DIAGNOSTICO = "DIAG";
	public static final String CONSULTAS = "CONS";
	public static final String PROCEDIMIENTOS = "PROC";
	public static final String FACTURACION = "FACT";

	// cargamos componentes
	@View
	private Rows rowServicios;
	@View
	private Rows rowEps;
	@View
	private Rows rowMedicos;
	@View
	private Rows rowDiagnosticosOConsulta;
	@View
	private Rows rowCentroSalud;

	@View
	private Groupbox groupInicial;

	// listado de items seleccionado
	private List<String> lista_seleccionados_servicios = new ArrayList<String>();
	private List<String> lista_seleccionados_eps = new ArrayList<String>();
	private List<String> lista_seleccionados_medicos = new ArrayList<String>();
	private List<String> lista_seleccionados_diagnosticos_consultas = new ArrayList<String>();
	private List<String> lista_seleccionados_centro = new ArrayList<String>();

	// listado de objetos
	private List<Map<String, Object>> lista_datos_servicios = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> lista_datos_eps = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> lista_datos_medicos = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> lista_datos_diagnosticos_consultas = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> lista_datos_centros_salud = new ArrayList<Map<String, Object>>();

	// componentes
	@View
	private Datebox dtbxFechaIncio;
	@View
	private Datebox dtbxFechaFinal;
	@View
	private Listbox lbxListadoAgrupacion;

	@View
	private Row rowGruposEtarios;
	@View
	private Groupbox grbConsultas;

	@View
	private Groupbox grbEntidades;

	@View
	private Space space_entidad;

	@View
	private Groupbox grdMedicos;

	@View
	private Borderlayout groupboxEditar;

	@View
	private Listbox lbxFormato;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxGrupoEtario;

	@View
	Checkbox chkAgruparPorContrato;

	// check de verificacion de filtros
	@View
	private Checkbox chk_servicios;
	@View
	private Checkbox chk_entidad_eps;
	@View
	private Checkbox chk_medicos;
	@View
	private Checkbox chk_diagnosticos_consultas;
	@View
	private Checkbox chk_centro_salud;

	private final String[] idsExcluyentes = {};

	private List<String> listado_descipcion_ordenado = new ArrayList<String>();
	private List<String> listado_descipcion_TOTAL_ordenado = new ArrayList<String>();

	private TipoReporteGruposEtarios tipoReporteGruposEtarios;
	private String tipo_string;

	public enum TipoReporteGruposEtarios {
		DIAGNOSTICOS, CONSULTAS, FACTURADO, PROCEDIMIENTOS, NULL;
	}

	private void inicializarAgrupacionServicio(String[][] grupos) {
		int i = 0;
		for (String[] grupo : grupos) {
			Listitem listitem = new Listitem();
			listitem.setValue(grupo[0]);
			// listitem.setLabel(grupo[1]);
			listitem.setAttribute("index", i++);

			Checkbox checkbox = new Checkbox();
			checkbox.setChecked(true);
			Listcell listcell = new Listcell();
			listcell.appendChild(checkbox);
			listitem.appendChild(new Listcell(grupo[1]));
			listitem.appendChild(listcell);
			chkAgruparPorContrato.setVisible(true);

			checkbox.setAttribute("tipo", grupo[0]);
			checkbox.addEventListener(Events.ON_CHECK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							// log.info("se selecciono u checkbox: "
							// + arg0.getTarget().getAttribute("tipo"));
							if ("01".equals(arg0.getTarget().getAttribute(
									"tipo"))) {
								chkAgruparPorContrato
										.setVisible(((Checkbox) arg0
												.getTarget()).isChecked());
							}
						}
					});

			listitem.setAttribute("chk", checkbox);

			listitem.setDraggable("true");
			listitem.setDroppable("true");
			listitem.addEventListener(Events.ON_DROP,
					new EventListener<DropEvent>() {
						@Override
						public void onEvent(DropEvent event) throws Exception {
							// log.info("Evento: " + event);
							Component selt = event.getTarget();
							Component component = event.getDragged();

							int posicion_incial = (Integer) selt
									.getAttribute("index");
							int posicion_cambiar = (Integer) component
									.getAttribute("index");

							// log.info("Seleccionado: " + posicion_cambiar
							// + " - cambiado por: " + posicion_incial);

							selt.getParent().insertBefore(component, selt);

							selt.setAttribute("index", posicion_cambiar);
							component.setAttribute("index", posicion_incial);
						}
					});
			lbxListadoAgrupacion.appendChild(listitem);
		}
	}

	private List<String> getListitemsOrdenados() {
		Object[] listitems = getItemSeleccionados();
		// reodenamos
		for (int i = 0; i < listitems.length; i++) {
			for (int j = 0; j < listitems.length; j++) {
				Listitem listitem_i = (Listitem) listitems[i];
				Listitem listitem_j = (Listitem) listitems[j];

				int pos_i = (Integer) listitem_i.getAttribute("index");
				int pos_j = (Integer) listitem_j.getAttribute("index");

				if (pos_i < pos_j) {
					Object field_temp = listitems[i];
					listitems[i] = listitems[j];
					listitems[j] = field_temp;
				}
			}
		}

		List<String> listadoAgrupado = new ArrayList<String>();
		int i = tipo_string.equals(FACTURACION) ? 0 : 3;
		boolean ciclo = true;
		for (; ciclo;) {

			if ((listitems.length - 1) >= i) {
				Listitem listitem = (Listitem) listitems[i];

				// Lo agregamos para morbilidar antes del item del contrato
				if (!tipo_string.equals(FACTURACION)) {
					agregarFiltroContrato(listadoAgrupado, listitem);
				}

				listadoAgrupado.add(getScriptPorAgrupacion(listitem.getValue()
						.toString()));
				listado_descipcion_ordenado
						.add(getScriptPorAgrupacionDescripcion(listitem
								.getValue().toString()));
				listado_descipcion_TOTAL_ordenado
						.add(getDescripcionTotal(listitem.getValue().toString()));

				// Agregamos de ultimo cuando es facturacion
				if (tipo_string.equals(FACTURACION)) {
					agregarFiltroContrato(listadoAgrupado, listitem);
				}
			}

			if (tipo_string.equals(FACTURACION)) {
				i++;
				ciclo = i < listitems.length;
			} else {
				i--;
				ciclo = i >= 0;
			}
		}

		// este es para agregar un complemento
		complementar(listadoAgrupado);

		/* listamos los grupos como quedarion */
		// for (String g : listadoAgrupado) {
		// //log.info("" + g);
		// }

		return listadoAgrupado;
	}

	private void agregarFiltroContrato(List<String> listadoAgrupado,
			Listitem listitem) {
		if (listitem.getValue().toString().equals("01")
				&& chkAgruparPorContrato.isChecked()) {
			// 04 significa contrato
			listadoAgrupado.add(getScriptPorAgrupacion("04"));
			listado_descipcion_ordenado
					.add(getScriptPorAgrupacionDescripcion("04"));
			listado_descipcion_TOTAL_ordenado.add(getDescripcionTotal("04"));
		}
	}

	private String getScriptPorAgrupacionDescripcion(String tipo_agrupacion) {
		if (tipo_agrupacion.equals("01")) {
			return "ads.codigo_administradora || ' ' || eps.nombre";
		} else if (tipo_agrupacion.equals("02")) {
			return "elm.descripcion";
		} else if (tipo_agrupacion.equals("03")) {
			return "med.nro_identificacion  || ' ' || med.apellidos || ' ' || med.nombres";
		} else if (tipo_agrupacion.equals("04")) {
			return "ads.id_plan || ' ' ||  contrato.nombre";
		} else if (tipo_agrupacion.equals("00")) {
			return "centro.codigo_centro || ' ' ||  centro.nombre_centro";
		}
		return "";
	}

	private String getDescripcionTotal(String tipo_agrupacion) {
		if (tipo_agrupacion.equals("01")) {
			return "TOTAL ADMINISTRADORA: ";
		} else if (tipo_agrupacion.equals("02")) {
			return "TOTAL SERVICIO: ";
		} else if (tipo_agrupacion.equals("03")) {
			return "med.nro_identificacion  || ' ' || med.apellidos || ' ' || med.nombres";
		} else if (tipo_agrupacion.equals("04")) {
			return "TOTAL CONTRATO: ";
		} else if (tipo_agrupacion.equals("00")) {
			return "TOTAL CENTRO: ";
		}
		return "";
	}

	private void complementar(List<String> listadoagrupado) {
		if (listadoagrupado.size() < 5 && !tipo_string.equals(FACTURACION)) {
			listadoagrupado.add("''");
			complementar(listadoagrupado);
		}
	}

	// Este metodo de forma ordenada me devuelve los grupos
	private String getScriptPorAgrupacion(String tipo_agrupacion) {
		if (!tipo_string.equals(FACTURACION)) {
			if (tipo_agrupacion.equals("01")) {
				return "ads.codigo_administradora || ' ' || eps.nombre";
			} else if (tipo_agrupacion.equals("02")) {
				return "elm.descripcion";
			} else if (tipo_agrupacion.equals("03")) {
				return "med.nro_identificacion  || ' ' || med.apellidos || ' ' || med.nombres";
			} else if (tipo_agrupacion.equals("04")) {
				return "ads.id_plan || ' ' ||  contrato.nombre";
			} else if (tipo_agrupacion.equals("00")) {
				return "centro.codigo_centro || ' ' ||  centro.nombre_centro";
			}
		} else {
			if (tipo_agrupacion.equals("01")) {
				return "ads.codigo_administradora";
			} else if (tipo_agrupacion.equals("02")) {
				return "elm.codigo";
			} else if (tipo_agrupacion.equals("04")) {
				return "ads.id_plan";
			} else if (tipo_agrupacion.equals("00")) {
				return "ads.codigo_centro";
			}
		}
		return "";
	}

	private Object[] getItemSeleccionados() {
		List<Listitem> listitems = lbxListadoAgrupacion.getItems();
		List<Listitem> listitemsOut = new ArrayList<Listitem>();
		for (Listitem listitem : listitems) {
			if (isSeleccionado(listitem)) {
				listitemsOut.add(listitem);
			}
		}
		return listitemsOut.toArray();
	}

	private boolean isSeleccionado(Listitem listitem) {
		Checkbox checkbox = (Checkbox) listitem.getAttribute("chk");
		if (checkbox != null) {
			return checkbox.isChecked();
		} else {
			return false;
		}
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		tipo_string = (String) request.getParameter("tipo");
		if (tipo_string != null && !tipo_string.trim().isEmpty()) {
			String[][] grupos = { { "00", "CENTRO DE SALUD" }, { "01", "EPS" },
					{ "02", "SERVICIOS" }, { "03", "MEDICOS" } };
			if (tipo_string.equals(CONSULTAS)) {
				tipoReporteGruposEtarios = TipoReporteGruposEtarios.CONSULTAS;
				groupInicial.setTitle("REPORTE POR CONSULTAS");
			} else if (tipo_string.equals(PROCEDIMIENTOS)) {
				tipoReporteGruposEtarios = TipoReporteGruposEtarios.PROCEDIMIENTOS;
				groupInicial.setTitle("REPORTE DE PROCEDIMIENTOS");
			} else if (tipo_string.equals(DIAGNOSTICO)) {
				tipoReporteGruposEtarios = TipoReporteGruposEtarios.DIAGNOSTICOS;
				groupInicial.setTitle("REPORTE DE MORBILIDAD");
			} else {
				tipoReporteGruposEtarios = TipoReporteGruposEtarios.FACTURADO;
				groupInicial.setTitle("REPORTE VALOR FACTURADO");
				String[][] grupos_temp = { { "00", "CENTRO DE SALUD" },
						{ "01", "EPS" }, { "02", "SERVICIOS" } };
				grupos = grupos_temp;
			}
			inicializarAgrupacionServicio(grupos);
			setTituloDesdeTipo(); 
		}else{
			tipoReporteGruposEtarios = TipoReporteGruposEtarios.NULL;
		}
	}
	
	private void setTituloDesdeTipo() {
		if (tipoReporteGruposEtarios == TipoReporteGruposEtarios.CONSULTAS) {
			grbConsultas.setTitle("CONSULTAS");
		} else if (tipoReporteGruposEtarios == TipoReporteGruposEtarios.PROCEDIMIENTOS) {
			grbConsultas.setTitle("PROCEDIMIENTOS");
		} else if (tipoReporteGruposEtarios == TipoReporteGruposEtarios.DIAGNOSTICOS) {
			grbConsultas.setTitle("DIAGNOSTICOS");
		}
	}

	@Override
	public void init() throws Exception {
		parametrizarBandbox();
	}

	private void parametrizarBandbox() {
		tbxGrupoEtario.inicializar(null,
				(Toolbarbutton) getFellow("btnLimpiarPaciente"));
		inicializarBandboxGruposEtarios();
	}

	private void inicializarBandboxGruposEtarios() {
		BandboxRegistrosIMG<Grupos_etareos> bandboxRegistrosIMG = new BandboxRegistrosIMG<Grupos_etareos>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Grupos_etareos registro) {
				bandbox.setValue(registro.getId() + " "
						+ registro.getDescripcion());
				bandbox.setAttribute("valor", registro.getId());
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem,
					Grupos_etareos registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getId() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getDescripcion() + ""));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Grupos_etareos> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("codigo_empresa", empresa.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				grupos_etareosService.setLimit("limit 25 offset 0");
				return grupos_etareosService.listar(parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}
		};
		/* inyectamos el mismo evento */
		tbxGrupoEtario.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	public void generarReporte() {
		try {
			listado_descipcion_ordenado.clear();
			listado_descipcion_TOTAL_ordenado.clear();
			List<String> listitems = getListitemsOrdenados();

			// for (String string : listitems) {
			// //log.info("Ordenamiento: " + string);
			// }

			if (validarForm()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("fecha_inicio", L2HContraintDate.initFechaInHHMMSS(
						dtbxFechaIncio.getValue(), TypeInit.Init00_00_00));
				map.put("fecha_final", L2HContraintDate.initFechaInHHMMSS(
						dtbxFechaFinal.getValue(), TypeInit.end23_59_59));
				map.put("grupos_etarios", tbxGrupoEtario.getAttribute("valor"));

				if (chkAgruparPorContrato.isChecked()
						&& chk_entidad_eps.isChecked()) {
					map.put("contratos", getContratos());
				} else if (chk_entidad_eps.isChecked()) {
					map.put("eps", getEps());
				}

				if (chk_diagnosticos_consultas.isChecked()) {
					// consultas
					if (tipo_string.equals(CONSULTAS)) {
						map.put("consultas", getConsultas());
					} else if (tipo_string.equals(PROCEDIMIENTOS)) {
						// Procedimientos
						map.put("procedimientos", getProcedimientos());
					} else {
						// Diagnosticos
						map.put("diagnostico", getDiagnostico());
					}
				}

				// Adicionamos el filtro de centro de salud
				if (chk_centro_salud.isChecked()) {
					map.put("centros_salud", getCentrosSalud());
				}

				if (chk_medicos.isChecked()) {
					map.put("medicos", getMedicos());
				}

				if (chk_servicios.isChecked()) {
					map.put("servicios", getServicios());
				}

				/* grupos */
				if (!tipo_string.equals(FACTURACION)) {
					int i = 5;
					for (String grupo : listitems) {
						map.put("grupo" + i--, grupo);
					}
				} else {
					int i = 4;
					int c_lista = 0;
					for (String grupo : listitems) {
						int indice_desciptor = i--;
						String descripcion_total = listado_descipcion_TOTAL_ordenado
								.get(c_lista);
						String descipcion = listado_descipcion_ordenado
								.get(c_lista++);
						map.put("grupo_" + indice_desciptor, grupo);
						map.put("grupo" + indice_desciptor, descripcion_total);
						map.put("descipcion_grupo" + indice_desciptor,
								descipcion);
					}

					while (i > 0) {
						// map.put("grupo" + i, "''");
						map.put("descipcion_grupo" + i--, "''");
					}

				}
				map.put("nivel", listitems.size());

				map.put("impreso_por",
						usuarios.getCodigo() + " - " + usuarios.getNombres()
								+ " " + usuarios.getApellidos());
				map.put("tipo_item", tipo_string.equals(CONSULTAS)
						|| tipo_string.equals(PROCEDIMIENTOS) ? 2 : 1);
				map.put("titulo",
						tipo_string.equals(CONSULTAS) ? "Consolidado de consultas del"
								: (tipo_string.equals(PROCEDIMIENTOS) ? "Consolidado de procedimientos del"
										: (tipo_string.equals(DIAGNOSTICO) ? "Reporte de morbilidad del"
												: "Reporte de valor facturado")));
				map.put("name_report",
						tipo_string.equals(FACTURACION) ? "ValorFacturado"
								: (tipo_string.equals(PROCEDIMIENTOS) ? "ConsolidadoYProcedimientos"
										: "ConsolidadoYMorbilidad"));
				map.put("formato", lbxFormato.getSelectedItem().getValue()
						.toString());

				// log.info("Mapeo: " + map);

				Component componente = Executions.createComponents(
						"/pages/printInformes.zul", this, map);
				final Window window = (Window) componente;
				window.setWidth("100%");
				window.setHeight("100%");
				window.setMode("modal");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private List<Centro_atencion> getCentrosSalud() {
		List<Centro_atencion> centro_atencions = new ArrayList<Centro_atencion>();
		for (Map<String, Object> map_ : lista_datos_centros_salud) {
			Centro_atencion atencion = (Centro_atencion) map_.get(CENTRO_SALUD);
			centro_atencions.add(atencion);
		}
		return centro_atencions;
	}

	private List<Elemento> getServicios() {
		List<Elemento> elementos = new ArrayList<Elemento>();
		for (Map<String, Object> map_ : lista_datos_servicios) {
			Elemento elm = (Elemento) map_.get(SERVICIO);
			elementos.add(elm);
		}
		return elementos;
	}

	private List<Prestadores> getMedicos() {
		List<Prestadores> prestadores = new ArrayList<Prestadores>();
		for (Map<String, Object> map_ : lista_datos_medicos) {
			Prestadores prestador = (Prestadores) map_.get(PRESTADOR_SERVICIO);
			prestadores.add(prestador);
		}
		return prestadores;
	}

	private List<Cie> getDiagnostico() {
		List<Cie> cies = new ArrayList<Cie>();
		for (Map<String, Object> map_ : lista_datos_diagnosticos_consultas) {
			Cie cie = (Cie) map_.get(CIE);
			cies.add(cie);
		}
		return cies;
	}

	private List<Procedimientos> getConsultas() {
		List<Procedimientos> procedimiento_iss01s = new ArrayList<Procedimientos>();
		for (Map<String, Object> map_ : lista_datos_diagnosticos_consultas) {
			Procedimientos procedimientos = (Procedimientos) map_
					.get(PROCEDIMIENTO_CONSULTA);
			procedimiento_iss01s.add(procedimientos);
		}
		return procedimiento_iss01s;
	}

	private List<Procedimientos> getProcedimientos() {
		List<Procedimientos> procedimiento_iss01s = new ArrayList<Procedimientos>();
		for (Map<String, Object> map_ : lista_datos_diagnosticos_consultas) {
			Procedimientos procedimientos = (Procedimientos) map_
					.get(PROCEDIMIENTO_CONSULTA);
			procedimiento_iss01s.add(procedimientos);
		}
		return procedimiento_iss01s;
	}

	private List<Administradora> getEps() {
		List<Administradora> administradoras = new ArrayList<Administradora>();
		for (Map<String, Object> map_ : lista_datos_eps) {
			Administradora administradora = (Administradora) map_
					.get(ADMINISTRADORA);
			administradoras.add(administradora);
		}
		return administradoras;
	}

	private List<Contratos> getContratos() {
		List<Contratos> contratos = new ArrayList<Contratos>();
		List<Component> components = rowEps.getChildren();
		for (Component component : components) {
			Listbox listbox = (Listbox) component.getAttribute("lbxContrato");
			Set<Listitem> listitems = listbox.getSelectedItems();
			for (Listitem listitem : listitems) {
				contratos.add((Contratos) listitem.getValue());
			}
		}
		return contratos;
	}

	public boolean validarForm() throws Exception {
		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";
		dtbxFechaIncio
				.setStyle("text-transform:uppercase;background-color:white");
		dtbxFechaFinal
				.setStyle("text-transform:uppercase;background-color:white");
		tbxGrupoEtario
				.setStyle("text-transform:uppercase;background-color:white");

		if (dtbxFechaIncio.getValue() == null) {
			valida = false;
			dtbxFechaIncio
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		}

		if (dtbxFechaFinal.getValue() == null && valida) {
			valida = false;
			dtbxFechaFinal
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		}

		if (tbxGrupoEtario.getValue().toString().isEmpty() && valida
				&& !tipo_string.equals(FACTURACION)) {
			valida = false;
			tbxGrupoEtario
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		}

		// if(getItemSeleccionados().length == 0 && valida){
		// valida = false;
		// msj =
		// "Para esta opcion debe por lo menos seleccionar un item para ordenar la informacion!";
		// }

		if (valida && chk_entidad_eps.isChecked()
				&& !chkAgruparPorContrato.isChecked() && getEps().isEmpty()) {
			valida = false;
			msj = "Para filtar las eps debes por lo menos seleccionar una!";
		} else if (valida && chk_entidad_eps.isChecked()
				&& chkAgruparPorContrato.isChecked()
				&& getContratos().isEmpty()) {
			valida = false;
			msj = "Para filtar por contrato debe por lo menos seleccionar uno!";
		}

		if (valida && chk_medicos.isChecked() && getMedicos().isEmpty()) {
			valida = false;
			msj = "Para filtar por los medicos debes por lo menos seleccionar uno!";
		}

		if (valida && chk_diagnosticos_consultas.isChecked()
				&& lista_datos_diagnosticos_consultas.isEmpty()) {
			valida = false;
			msj = "Para filtar por "
					+ (tipo_string.equals("CONS") ? "las consultas"
							: "los diagnosticos")
					+ " debes por lo menos seleccionar un registro!";
		}

		if (valida && chk_centro_salud.isChecked()
				&& getCentrosSalud().isEmpty()) {
			valida = false;
			msj = "Para filtar los centros de salud debes por lo menos seleccionar uno!";
		}

		if (valida && chk_servicios.isChecked() && getServicios().isEmpty()) {
			valida = false;
			msj = "Para filtar los servicios debes por lo menos seleccionar uno!";
		}

		if (tipoReporteGruposEtarios != TipoReporteGruposEtarios.FACTURADO
				&& valida && chkAgruparPorContrato.isChecked()
				&& chk_entidad_eps.isChecked()) {
			List<Contratos> contratos = getContratos();
			if (contratos.isEmpty() && !rowEps.getChildren().isEmpty()) {
				valida = false;
				msj = "Para realizar esta accion debe seleccionar un contrato";
			}
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", msj);
		}

		return valida;
	}

	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		lista_seleccionados_diagnosticos_consultas.clear();
		lista_seleccionados_centro.clear();
		lista_seleccionados_eps.clear();
		lista_seleccionados_medicos.clear();
		lista_seleccionados_servicios.clear();

		lista_datos_centros_salud.clear();
		lista_datos_diagnosticos_consultas.clear();
		lista_datos_eps.clear();
		lista_datos_medicos.clear();
		lista_datos_servicios.clear();

		rowDiagnosticosOConsulta.getChildren().clear();
		rowEps.getChildren().clear();
		rowMedicos.getChildren().clear();
		rowServicios.getChildren().clear();
		rowCentroSalud.getChildren().clear();
		dtbxFechaFinal.setValue(null);
		dtbxFechaIncio.setValue(null);
	}

	// metodos para cargar los datos a seleccionar
	// cargar sevicios
	public void abrirWindowServicios() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", "via_ingreso");
		String columnas = "Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Servicios", Paquetes.HEALTHMANAGER, "ElementoDao.listar",
				this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_servicios);
	}

	public void abrirWindowCentrosSalud() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		String columnas = "código#65px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Centros de Salud", Paquetes.HEALTHMANAGER,
				"Centro_atencionDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_centro);
	}

	// cargar eps
	public void abrirWindowEps() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("cerrado", false);
		String columnas = "código#65px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro("Eps",
				Paquetes.HEALTHMANAGER, "AdministradoraDao.listar", this,
				columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_eps);
	}

	// cargar medicos
	public void abrirWindowMedicos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		String columnas = "código#65px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Prestadores", Paquetes.HEALTHMANAGER, "PrestadoresDao.listar",
				this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_medicos);
	}

	// cargar diagnosticos o consultas
	public void abrirWindowDiagnosticosConsultas() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		String columnas = "código#65px|Nombre";
		// log.info("" + tipoReporteGruposEtarios);
		if (tipoReporteGruposEtarios == TipoReporteGruposEtarios.CONSULTAS
				|| tipoReporteGruposEtarios == TipoReporteGruposEtarios.PROCEDIMIENTOS) {
			parametros
					.put("consulta",
							tipoReporteGruposEtarios == TipoReporteGruposEtarios.CONSULTAS ? "S"
									: "N");  
			columnas = "código cups#150px|Nombre";
			WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
					"Consultas", Paquetes.HEALTHMANAGER,
					"ProcedimientosDao.listar", this, columnas, parametros);
			windowRegistros
					.mostrarWindow(lista_seleccionados_diagnosticos_consultas);
		} else {
			WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
					"Diagnosticos", Paquetes.HEALTHMANAGER, "CieDao.listar",
					this, columnas, parametros);
			windowRegistros
					.mostrarWindow(lista_seleccionados_diagnosticos_consultas);
		}
	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			// log.info("Registro seleccionado: " + registro);
			if (registro instanceof Elemento) {
				onSeleccionarServicio((Elemento) registro);
			} else if (registro instanceof Administradora) {
				onSeleccionarEps((Administradora) registro);
			} else if (registro instanceof Procedimientos) {
				onSeleccionarProcedimientos((Procedimientos) registro);
			} else if (registro instanceof Cie) {
				onSeleccionarCie((Cie) registro);
			} else if (registro instanceof Prestadores) {
				onSeleccionaPrestadores((Prestadores) registro);
			} else if (registro instanceof Centro_atencion) {
				onSeleccionaCentro((Centro_atencion) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void onSeleccionaCentro(Centro_atencion registro) {
		adicionarDetalleListaCentroSalud(cargarCentroSalud(registro));
	}

	private void onSeleccionaPrestadores(Prestadores registro) {
		adicionarDetalleListaPretador(cargarPrestador(registro));
	}

	private void onSeleccionarCie(Cie registro) {
		adicionarDetalleListaCie(cargarCie(registro));
	}

	private void onSeleccionarProcedimientos(Procedimientos registro) {
		adicionarDetalleListaProcedimiento(cargarProcedimientoIss01(registro));
	}

	private void onSeleccionarEps(Administradora registro) {
		adicionarDetalleListaAdministradora(cargarPrestador(registro));
	}

	private void onSeleccionarServicio(Elemento registro) {
		adicionarDetalleListaUnidadFuncional(cargarServicio(registro));
	}

	// mapeamos los bean a ingresar
	private Map<String, Object> cargarCie(Cie cie) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(CIE, cie);
		return bean;
	}

	private Map<String, Object> cargarProcedimientoIss01(
			Procedimientos procedimientos) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(PROCEDIMIENTO_CONSULTA, procedimientos);
		return bean;
	}

	private Map<String, Object> cargarPrestador(Prestadores prestadores) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(PRESTADOR_SERVICIO, prestadores);
		return bean;
	}

	private Map<String, Object> cargarPrestador(Administradora administradora) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(ADMINISTRADORA, administradora);
		return bean;
	}

	private Map<String, Object> cargarServicio(Elemento unidad_funcional) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(SERVICIO, unidad_funcional);
		return bean;
	}

	private Map<String, Object> cargarCentroSalud(Centro_atencion registro) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(CENTRO_SALUD, registro);
		return bean;
	}

	// adicionamos registros
	private void adicionarDetalleListaCie(Map<String, Object> bean) {
		try {
			crearFilasCie(bean);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasCie(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_diagnosticos_consultas.add(bean);
		final Cie cie = (Cie) bean.get(CIE);

		row.setValue(cie);

		Cell cell = new Cell();
		Label label = new Label("" + cie.getCodigo());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + cie.getNombre());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_diagnosticos_consultas
															.remove(bean);
													rowDiagnosticosOConsulta
															.removeChild(row);
												}
											}
										});
					}
				});
		rowDiagnosticosOConsulta.appendChild(row);
	}

	private void crearFilasProcedimiento(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_diagnosticos_consultas.add(bean);
		final Procedimientos procedimientos = (Procedimientos) bean
				.get(PROCEDIMIENTO_CONSULTA);

		row.setValue(procedimientos);

		Cell cell = new Cell();
		Label label = new Label("" + procedimientos.getCodigo_cups());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + procedimientos.getDescripcion());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_diagnosticos_consultas
															.remove(bean);
													rowDiagnosticosOConsulta
															.removeChild(row);
												}
											}
										});
					}
				});
		rowDiagnosticosOConsulta.appendChild(row);
	}

	private void adicionarDetalleListaPretador(Map<String, Object> bean) {
		try {
			crearFilasPretador(bean);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasPretador(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_medicos.add(bean);
		final Prestadores prestadores = (Prestadores) bean
				.get(PRESTADOR_SERVICIO);

		row.setValue(prestadores);

		Cell cell = new Cell();
		Label label = new Label("" + prestadores.getNro_identificacion());
		cell.appendChild(label);
		row.appendChild(cell);

		cell = new Cell();
		label = new Label("" + prestadores.getNombres() + " "
				+ prestadores.getApellidos());
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_medicos
															.remove(bean);
													rowMedicos.removeChild(row);
												}
											}
										});
					}
				});
		rowMedicos.appendChild(row);
	}

	private void adicionarDetalleListaAdministradora(Map<String, Object> bean) {
		try {
			crearFilasAdministradora(bean);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void adicionarDetalleListaCentroSalud(
			Map<String, Object> cargarCentroSalud) {
		try {
			crearFilasCentroSalud(cargarCentroSalud);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasCentroSalud(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_centros_salud.add(bean);

		final Centro_atencion centro_atencion = (Centro_atencion) bean
				.get(CENTRO_SALUD);
		row.setValue(centro_atencion);

		Cell cell = new Cell();
		Label label = new Label("" + centro_atencion.getCodigo_centro());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + centro_atencion.getNombre_centro());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_centros_salud
															.remove(bean);
													rowCentroSalud
															.removeChild(row);
												}
											}
										});
					}
				});
		rowCentroSalud.appendChild(row);
	}

	private void crearFilasAdministradora(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_eps.add(bean);
		final Administradora administradora = (Administradora) bean
				.get(ADMINISTRADORA);

		// cargamos contratos
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_administradora", administradora.getCodigo());

		List<Contratos> lista_contratos = contratosService.listar(parametros);
		Listbox listboxContratos = getListBox(lista_contratos);
		row.setAttribute("lbxContrato", listboxContratos);

		Detail detail = new Detail();
		detail.appendChild(listboxContratos);
		row.appendChild(detail);

		row.setValue(administradora);

		Cell cell = new Cell();
		Label label = new Label("" + administradora.getCodigo());
		cell.appendChild(label);
		row.appendChild(cell);

		cell = new Cell();
		label = new Label("" + administradora.getNombre());
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_eps
															.remove(bean);
													rowEps.removeChild(row);
												}
											}
										});
					}
				});
		rowEps.appendChild(row);
	}

	private Listbox getListBox(List<Contratos> lista_contratos) {
		Listbox listbox = new Listbox();
		listbox.setCheckmark(true);
		listbox.setMultiple(true);
		listbox.setHflex("1");

		Listhead listhead = new Listhead();
		listbox.appendChild(listhead);

		Listheader listheader = new Listheader("ID contrato");
		listheader.setWidth("150px");
		listhead.appendChild(listheader);

		listheader = new Listheader("código contrato");
		listhead.appendChild(listheader);

		listheader = new Listheader("Nombre contrato");
		listhead.appendChild(listheader);

		for (Contratos contratos : lista_contratos) {
			Listitem listitem = new Listitem();
			listitem.setValue(contratos);

			listitem.setSelected(!contratos.getCerrado());
			listitem.appendChild(new Listcell(contratos.getId_plan()));
			listitem.appendChild(new Listcell(contratos.getNro_contrato()));
			listitem.appendChild(new Listcell(contratos.getNombre()));

			listbox.appendChild(listitem);
		}

		if (listbox.getSelectedCount() > 0) {
			listbox.setSelectedIndex(0);
		}
		return listbox;
	}

	private void adicionarDetalleListaProcedimiento(Map<String, Object> bean) {
		try {
			crearFilasProcedimiento(bean);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void adicionarDetalleListaUnidadFuncional(Map<String, Object> bean) {
		try {
			crearFilasUnidadFuncional(bean);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasUnidadFuncional(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_servicios.add(bean);
		final Elemento centro_de_costo_via_ingreso = (Elemento) bean
				.get(SERVICIO);

		row.setValue(centro_de_costo_via_ingreso);

		Cell cell = new Cell();
		Label label = new Label(""
				+ centro_de_costo_via_ingreso.getDescripcion().toUpperCase());
		cell.appendChild(label);
		row.appendChild(cell);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/borrar.gif");
		row.appendChild(toolbarbutton);
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Messagebox
								.show("¿Estas seguro que deseas eliminar estos registros?",
										"Advertencia", Messagebox.YES
												+ Messagebox.NO,
										Messagebox.QUESTION,
										new EventListener<Event>() {
											@Override
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													lista_datos_servicios
															.remove(bean);
													rowServicios
															.removeChild(row);
												}
											}
										});
					}
				});
		rowServicios.appendChild(row);
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if (registro instanceof Prestadores) {
			return celdasListitemPrestadores((Prestadores) registro);
		} else if (registro instanceof Elemento) {
			return celdasListitemUnidadFuncional((Elemento) registro);
		} else if (registro instanceof Procedimientos) {
			return celdasListitemConsulta((Procedimientos) registro);
		} else if (registro instanceof Administradora) {
			return celdasListitemAdministradora((Administradora) registro);
		} else if (registro instanceof Cie) {
			return celdasListitemCie((Cie) registro);
		} else if (registro instanceof Centro_atencion) {
			return celdasListitemCentroAtencion((Centro_atencion) registro);
		}
		return null;
	}

	private Object[] celdasListitemCentroAtencion(Centro_atencion registro) {
		return new Object[] { registro.getCodigo_centro(),
				registro.getNombre_centro() };
	}

	private Object[] celdasListitemAdministradora(Administradora registro) {
		return new Object[] { registro.getCodigo(), registro.getNombre() };
	}

	private Object[] celdasListitemCie(Cie registro) {
		return new Object[] { registro.getCodigo(), registro.getNombre() };
	}

	private Object[] celdasListitemConsulta(Procedimientos registro) {
		return new Object[] { registro.getCodigo_cups(),
				registro.getDescripcion() };
	}

	private Object[] celdasListitemUnidadFuncional(Elemento registro) {
		return new Object[] { registro.getDescripcion().toUpperCase() };
	}

	private Object[] celdasListitemPrestadores(Prestadores registro) {
		return new Object[] { registro.getNro_identificacion(),
				registro.getNombres() + " " + registro.getApellidos() };
	}

}
