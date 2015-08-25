package healthmanager.controller.reportes;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Grupos_etareos;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Grupos_etareosService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Articulo;

public class ReporteInformeProvisionalServicioAction extends ZKWindow implements
		WindowRegistrosIMG {

	@View
	private Datebox dtbxFechaIncio;
	@View
	private Datebox dtbxFechaFinal;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxGrupoEtario;

	@View
	private Checkbox chkEstadoActivo;
	@View
	private Checkbox chkEstadoFacturado;
	@View
	private Checkbox chkEstadoCancelado;

	// filtros de servicios
	@View
	private Checkbox chkConsultas;
	@View
	private Checkbox chkProcedimiento;
	@View
	private Checkbox chkMedicamentosInsumos;
	@View
	private Checkbox chkEstancias;
	@View
	private Checkbox chkUrgencia;

	// contenedor de triage
	@View
	private Foot fotSeccionTriage;

	// seleccionar de niveles de triage
	@View
	private Checkbox chkTriageI;
	@View
	private Checkbox chkTriageII;
	@View
	private Checkbox chkTriageIII;
	@View
	private Checkbox chkTriageIV;
	@View
	private Checkbox chkUrgSinTriage;

	// check box de filtros de busqueda
	@View
	private Checkbox chk_centro_salud;
	@View
	private Checkbox chk_servicios;
	@View
	private Checkbox chk_entidad_eps;
	@View
	private Checkbox chk_medicos;
	@View
	private Checkbox chk_diagnosticos;
	@View
	private Checkbox chk_via_ingreso;
	@View
	private Checkbox chk_facturador;
	@View
	private Checkbox chk_paciente;

	@View
	private Checkbox chkConfigFacturasEventos;
	@View
	private Checkbox chkConfigFacturasCapitadas;
	@View
	private Checkbox chkConfigFacturasAgrupada;

	@View
	private Radiogroup rgFiltros;

	// rows contenedora de los detalles
	@View
	private Rows rowCentroSalud;
	@View
	private Rows rowVia_ingreso;
	@View
	private Rows rowEps;
	@View
	private Rows rowMedicos;
	@View
	private Rows rowDiagnosticos;
	@View
	private Rows rowServicios;
	@View
	private Rows rowFacturador;
	@View
	private Rows rowPacientes;

	@View
	private Groupbox grbCentro;
	@View
	private Groupbox grbViaIngreso;
	@View
	private Groupbox grbEntidades;
	@View
	private Groupbox grdMedicos;
	@View
	private Groupbox grbConsultas;
	@View
	private Groupbox grbServicios;
	@View
	private Groupbox grbFacturador;
	@View
	private Groupbox grbPaciente;

	// listado de servicios seleccionados
	private List<String> lista_seleccionados_pcd = new ArrayList<String>();
	private List<String> lista_seleccionados_art = new ArrayList<String>();
	private List<String> lista_seleccionados_via_ingreso = new ArrayList<String>();
	private List<String> lista_seleccionados_centro = new ArrayList<String>();
	private List<String> lista_seleccionados_diagnosticos = new ArrayList<String>();
	private List<String> lista_seleccionados_medicos = new ArrayList<String>();
	private List<String> lista_seleccionados_aseguradora = new ArrayList<String>();
	private List<String> lista_seleccionados_usuario = new ArrayList<String>();
	private List<String> lista_seleccionados_paciente = new ArrayList<String>();

	@Override
	public void init() throws Exception {
		parametrizarBandbox();
	}

	private void parametrizarBandbox() {
		tbxGrupoEtario.inicializar(null,
				(Toolbarbutton) getFellow("btnLimpiarGrupoEtario"));
		inicializarBandboxGruposEtarios();
	}

	private void inicializarBandboxGruposEtarios() {
		BandboxRegistrosIMG<Grupos_etareos> bandboxRegistrosIMG = new BandboxRegistrosIMG<Grupos_etareos>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Grupos_etareos registro) {
				bandbox.setValue(registro.getId() + " "
						+ registro.getDescripcion());
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
				ReporteInformeProvisionalServicioAction.this
						.getServiceLocator()
						.getServicio(Grupos_etareosService.class)
						.setLimit("limit 25 offset 0");
				return getServiceLocator().getServicio(
						Grupos_etareosService.class).listar(parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}
		};
		/* inyectamos el mismo evento */
		tbxGrupoEtario.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	public void onLimpiar() {
		dtbxFechaFinal.setValue(null);
		dtbxFechaIncio.setValue(null);

		rgFiltros.getItems().get(0).setSelected(true);

		chkConsultas.setChecked(false);
		chkProcedimiento.setChecked(false);
		chkMedicamentosInsumos.setChecked(false);
		chkEstancias.setChecked(false);
		chkUrgencia.setChecked(false);

		chkTriageI.setChecked(false);
		chkTriageII.setChecked(false);
		chkTriageIII.setChecked(false);
		chkTriageIV.setChecked(false);
		chkUrgSinTriage.setChecked(false);

		fotSeccionTriage.setVisible(false);

		// tipo de factura y admision
		chkConfigFacturasAgrupada.setChecked(true);
		chkConfigFacturasCapitadas.setChecked(true);
		chkConfigFacturasEventos.setChecked(true);

		chkEstadoActivo.setChecked(true);
		chkEstadoFacturado.setChecked(true);
		chkEstadoCancelado.setChecked(false);

		// limpiamos listas de seleccion
		lista_seleccionados_pcd.clear();
		lista_seleccionados_art.clear();
		lista_seleccionados_via_ingreso.clear();
		lista_seleccionados_centro.clear();
		lista_seleccionados_diagnosticos.clear();
		lista_seleccionados_medicos.clear();
		lista_seleccionados_aseguradora.clear();
		lista_seleccionados_usuario.clear();
		lista_seleccionados_paciente.clear();

		// limpiamos rows
		rowCentroSalud.getChildren().clear();
		rowDiagnosticos.getChildren().clear();
		rowEps.getChildren().clear();
		rowFacturador.getChildren().clear();
		rowMedicos.getChildren().clear();
		rowPacientes.getChildren().clear();
		rowServicios.getChildren().clear();
		rowVia_ingreso.getChildren().clear();

		// check de filtros
		chk_centro_salud.setChecked(false);
		chk_diagnosticos.setChecked(false);
		chk_entidad_eps.setChecked(false);
		chk_facturador.setChecked(false);
		chk_medicos.setChecked(false);
		chk_paciente.setChecked(false);
		chk_servicios.setChecked(false);
		chk_via_ingreso.setChecked(false);

		// cambio de estilo
		chk_centro_salud.setStyle("");
		chk_diagnosticos.setStyle("");
		chk_entidad_eps.setStyle("");
		chk_facturador.setStyle("");
		chk_medicos.setStyle("");
		chk_paciente.setStyle("");
		chk_servicios.setStyle("");
		chk_via_ingreso.setStyle("");

		// cerramos los grupos
		grbCentro.setOpen(false);
		grbConsultas.setOpen(false);
		grbEntidades.setOpen(false);
		grbFacturador.setOpen(false);
		grbPaciente.setOpen(false);
		grbServicios.setOpen(false);
		grbViaIngreso.setOpen(false);
		grdMedicos.setOpen(false);
	}

	/**
	 * Metodo para generar reporte
	 * 
	 * @author Luis Miguel
	 * */
	public void onGenerarReporte() {
		try {
			if (onValidar()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", sucursal.getCodigo_empresa());
				map.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				map.put("param_centro_salud",
						getListado(chk_centro_salud.isChecked(), rowCentroSalud));
				map.put("param_diagnosticos",
						getListado(chk_diagnosticos.isChecked(),
								rowDiagnosticos));
				map.put("param_pacientes",
						getListado(chk_paciente.isChecked(), rowPacientes));

				mapearContratos(map);

				map.put("param_facturador",
						getListado(chk_facturador.isChecked(), rowFacturador));
				map.put("param_medicos",
						getListado(chk_medicos.isChecked(), rowMedicos));
				mapearServicios(map, chk_servicios.isChecked(), rowServicios);

				map.put("param_via_ingreso",
						getListado(chk_via_ingreso.isChecked(), rowVia_ingreso));
				// parametros pestaña
				map.put("param_chk_consulta", chkConsultas.isChecked());
				map.put("param_chk_procedimiento", chkProcedimiento.isChecked());
				map.put("param_chk_medicamento_insumo",
						chkMedicamentosInsumos.isChecked());
				map.put("param_chk_estancias", chkEstancias.isChecked());
				map.put("param_chk_triage",
						chkUrgencia.isChecked()
								&& (chkTriageI.isChecked()
										|| chkTriageII.isChecked()
										|| chkTriageIII.isChecked() || chkTriageIV
											.isChecked()));
				map.put("param_chk_sin_triage", chkUrgSinTriage.isChecked());

				map.put("fecha_inicio", dtbxFechaIncio.getValue());
				map.put("fecha_final", dtbxFechaFinal.getValue());

				map.put("param_tipo_facturacion", getTipoFacturacion());
				map.put("param_estado_admision", getEstadoAdmision());

				map.put("param_titulo_ayuda", getTituloAyuda());

				mapearNivelTriage(map);

				// colocamos por que va a filtrar el reporte
				map.put(rgFiltros.getSelectedItem().getValue().toString()
						.equals("01") ? "fecha_realizacion" : "fecha_creacion",
						"filtro");

				generar(map);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private String getTituloAyuda() {
		StringBuilder bdl_titulo = new StringBuilder();
		if ((chkEstadoActivo.isChecked() && chkEstadoCancelado.isChecked() && chkEstadoFacturado
				.isChecked())
				|| (!chkEstadoActivo.isChecked()
						&& !chkEstadoCancelado.isChecked() && !chkEstadoFacturado
							.isChecked())) {
			bdl_titulo.append("Estado admision: TODOS");
		} else {
			bdl_titulo.append("Estado admision: ");
			boolean aplica_coma = false;
			if (chkEstadoActivo.isChecked()) {
				bdl_titulo.append("ACTIVA");
				aplica_coma = true;
			}
			if (chkEstadoCancelado.isChecked()) {
				bdl_titulo.append((aplica_coma ? " Y " : "") + "CANCELADA");
				aplica_coma = true;
			}
			if (chkEstadoFacturado.isChecked()) {
				bdl_titulo.append((aplica_coma ? " Y " : "") + "FACTURADA");
			}
		}

		if ((chkConfigFacturasEventos.isChecked()
				&& chkConfigFacturasCapitadas.isChecked() && chkConfigFacturasAgrupada
					.isChecked())
				|| (!chkConfigFacturasEventos.isChecked()
						&& !chkConfigFacturasCapitadas.isChecked() && !chkConfigFacturasAgrupada
							.isChecked())) {
			bdl_titulo.append(" - Tipo Fact: TODOS");
		} else {
			bdl_titulo.append(" - Tipo Fact: ");
			boolean aplica_coma = false;
			if (chkConfigFacturasEventos.isChecked()) {
				bdl_titulo.append("EVENTO");
				aplica_coma = true;
			}
			if (chkConfigFacturasCapitadas.isChecked()) {
				bdl_titulo.append((aplica_coma ? " Y " : "") + "CAPITADO");
				aplica_coma = true;
			}
			if (chkConfigFacturasAgrupada.isChecked()) {
				bdl_titulo.append((aplica_coma ? " Y " : "") + "AGRUPADO");
			}
		}

		return bdl_titulo.toString();
	}

	private List<String> getEstadoAdmision() {
		List<String> estado_admision = new ArrayList<String>();
		agregarValorCheck(chkEstadoActivo, estado_admision);
		agregarValorCheck(chkEstadoCancelado, estado_admision);
		agregarValorCheck(chkEstadoFacturado, estado_admision);
		return estado_admision.isEmpty() ? null : estado_admision;
	}

	private void agregarValorCheck(Checkbox checkbox, List<String> listado) {
		if (checkbox.isChecked()) {
			listado.add(checkbox.getValue().toString());
		}
	}

	private void mapearContratos(Map<String, Object> map) {
		if (chk_entidad_eps.isChecked()) {
			List<Object> list = new ArrayList<Object>();
			for (Component row_item : rowEps.getChildren()) {
				if (row_item instanceof Row) {
					Row row = (Row) row_item;
					alimentarDetalle(row, (Detail) row.getFirstChild());
					Listbox lbxContrato = (Listbox) row
							.getAttribute("lbxContrato");
					List<Listitem> listitems = lbxContrato.getItems();
					for (Listitem listitem : listitems) {
						if (listitem.isSelected()) {
							list.add(listitem.getValue());
						}
					}
				}
			}
			if (!list.isEmpty()) {
				map.put("param_eps", list);
			}
		}
	}

	private List<String> getTipoFacturacion() {
		List<String> tipo_fact = new ArrayList<String>();
		agregarValorCheck(chkConfigFacturasEventos, tipo_fact);
		agregarValorCheck(chkConfigFacturasCapitadas, tipo_fact);
		agregarValorCheck(chkConfigFacturasAgrupada, tipo_fact);
		return tipo_fact.isEmpty() ? null : tipo_fact;
	}

	private void mapearNivelTriage(Map<String, Object> map) {
		List<String> nivel_triage = new ArrayList<String>();
		agregarValorCheck(chkTriageI, nivel_triage);
		agregarValorCheck(chkTriageII, nivel_triage);
		agregarValorCheck(chkTriageIII, nivel_triage);
		agregarValorCheck(chkTriageIV, nivel_triage);
		if (!nivel_triage.isEmpty()) {
			map.put("nivel_triage", nivel_triage);
		}
	}

	private void generar(Map<String, Object> map) {
		Window window = (Window) Executions.createComponents(
				"visualizardor_informe_provisionar_servicios.zul", null, map);
		window.setWidth("100%");
		window.setHeight("100%");
		window.doModal();
	}

	private void mapearServicios(Map<String, Object> map, boolean checked,
			Rows row) {
		if (checked) {
			List<Object> list_medicamentos = new ArrayList<Object>();
			List<Object> list_servicios = new ArrayList<Object>();
			for (Component row_item : row.getChildren()) {
				if (row_item instanceof Row) {
					Object objeto = (Object) ((Row) row_item).getValue();
					if (objeto instanceof Procedimientos) {
						list_servicios.add(objeto);
					} else if (objeto instanceof Articulo) {
						list_medicamentos.add(objeto);
					}
				}
			}
			if (!list_servicios.isEmpty()) {
				map.put("param_procedimiento", list_servicios);
			}
			if (!list_medicamentos.isEmpty()) {
				map.put("param_medicamento", list_medicamentos);
			}
		}
	}

	private List<?> getListado(boolean checked, Rows row) {
		if (checked) {
			List<Object> list = new ArrayList<Object>();
			for (Component row_item : row.getChildren()) {
				if (row_item instanceof Row) {
					list.add((Object) ((Row) row_item).getValue());
				}
			}
			return list;
		} else {
			return null;
		}
	}

	private boolean onValidar() {
		boolean valido = true;
		StringBuilder msj = new StringBuilder();

		// validamos las fechas
		try {
			FormularioUtil.validarCamposObligatorios(dtbxFechaIncio,
					dtbxFechaFinal);
		} catch (Exception e) {
			return false;
		}

		if (dtbxFechaIncio.getValue().compareTo(dtbxFechaFinal.getValue()) > 0) {
			valido = false;
			msj.append("La fecha final no puede ser menor que la fecha incial");
		}

		if (!chkConsultas.isChecked() && !chkProcedimiento.isChecked()
				&& !chkMedicamentosInsumos.isChecked()
				&& !chkEstancias.isChecked() && !chkUrgencia.isChecked()) {
			msj.append((valido ? "" : "\n")
					+ "Debe seleccionar al menos un tipo de servicios");
			valido = false;
		}

		if (chk_centro_salud.isChecked()
				&& rowCentroSalud.getChildren().isEmpty()) {
			msj.append((valido ? "" : "\n")
					+ "Debé seleccionar un centro de salud");
			valido = false;
		}

		if (chk_diagnosticos.isChecked()
				&& rowDiagnosticos.getChildren().isEmpty()) {
			msj.append((valido ? "" : "\n")
					+ "Debé seleccionar un diagnostisco");
			valido = false;
		}

		if (chk_entidad_eps.isChecked() && rowEps.getChildren().isEmpty()) {
			msj.append((valido ? "" : "\n") + "Debé seleccionar una empresa");
			valido = false;
		}

		if (chk_medicos.isChecked() && rowMedicos.getChildren().isEmpty()) {
			msj.append((valido ? "" : "\n") + "Debé seleccionar un prestador");
			valido = false;
		}

		if (chk_via_ingreso.isChecked()
				&& rowVia_ingreso.getChildren().isEmpty()) {
			msj.append((valido ? "" : "\n")
					+ "Debé seleccionar una via de ingreso");
			valido = false;
		}

		if (chk_paciente.isChecked() && rowPacientes.getChildren().isEmpty()) {
			msj.append((valido ? "" : "\n") + "Debé seleccionar un paciente");
			valido = false;
		}

		if (!valido) {
			MensajesUtil.mensajeAlerta("Advertencia", msj.toString());
		}

		return valido;
	}

	/**
	 * Este metodo se ejecuta cuando se seleccione el check de Urgencias
	 * 
	 * @author Luis Miguel
	 * */
	public void onSeleccionarUrgencia() {
		fotSeccionTriage.setVisible(chkUrgencia.isChecked());
	}

	// inicializamos los filtros
	/*********************************************************
	 * ABRIR VENTANAS DE FILTRO
	 **********************************************************/

	// metodos para cargar los datos a seleccionar
	// cargar sevicios
	public void abrirWindowServiciosPcd() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", "via_ingreso");
		String columnas = "Código cups#100px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccionar Procedimientos", Paquetes.HEALTHMANAGER,
				"ProcedimientosDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_pcd);
	}

	public void abrirWindowUsuario() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		String columnas = "Código#100px|Nombre|Apellido";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccionar usuario", Paquetes.HEALTHMANAGER,
				"UsuariosDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_usuario);
	}

	public void abrirWindowPaciente() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		String columnas = "Tipo Doc#100px|Documento#100px|Apellidos|Nombres";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccionar pacientes", Paquetes.HEALTHMANAGER,
				"PacienteDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_paciente);
	}

	public void abrirWindowServiciosArt() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", "via_ingreso");
		String columnas = "Código#100px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccionar Articulos", Paquetes.CONTAWEB,
				"ArticuloDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_art);
	}

	public void abrirWindowViaIngreso() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", "via_ingreso");
		String columnas = "Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccionar Vias de Ingreso", Paquetes.HEALTHMANAGER,
				"ElementoDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_via_ingreso);
	}

	public void abrirWindowCentrosSalud() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		String columnas = "código#65px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccionar Centros de Salud", Paquetes.HEALTHMANAGER,
				"Centro_atencionDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_centro);
	}

	// cargar eps
	public void abrirWindowEps() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("cerrado", false);
		String columnas = "Código#65px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccionar Aseguradora", Paquetes.HEALTHMANAGER,
				"AdministradoraDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_aseguradora);
	}

	// cargar medicos
	public void abrirWindowMedicos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		String columnas = "Código#65px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Seleccionar Médicos", Paquetes.HEALTHMANAGER,
				"PrestadoresDao.listarParaReporte", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_medicos);
	}

	// cargar diagnosticos o consultas
	public void abrirWindowDiagnosticos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		String columnas = "Código#65px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Diagnosticos", Paquetes.HEALTHMANAGER, "CieDao.listar", this,
				columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_diagnosticos);
	}

	/*********************************************************
	 * CREACION DE SELDA DE SERVICIOS CONSULTADOS
	 **********************************************************/

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
		} else if (registro instanceof Procedimientos) {
			return celdasListitemProcedimientos((Procedimientos) registro);
		} else if (registro instanceof Articulo) {
			return celdasListitemArticulo((Articulo) registro);
		} else if (registro instanceof Usuarios) {
			return celdasListitemUsuario((Usuarios) registro);
		} else if (registro instanceof Paciente) {
			return celdasListitemUsuario((Paciente) registro);
		}
		return null;
	}

	private Object[] celdasListitemUsuario(Paciente registro) {
		return new Object[] { registro.getTipo_identificacion(),
				registro.getDocumento(),
				registro.getApellido1() + " " + registro.getApellido2(),
				registro.getNombre1() + " " + registro.getNombre2() };
	}

	private Object[] celdasListitemUsuario(Usuarios registro) {
		return new Object[] { registro.getCodigo(), registro.getNombres(),
				registro.getApellidos() };
	}

	private Object[] celdasListitemArticulo(Articulo registro) {
		return new Object[] { registro.getCodigo_articulo(),
				registro.getNombre1() };
	}

	private Object[] celdasListitemProcedimientos(Procedimientos registro) {
		return new Object[] { registro.getCodigo_cups(),
				registro.getDescripcion() };
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

	/*********************************************************
	 * SELECCION DE SERVICIOS
	 **********************************************************/

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			// log.info("Registro seleccionado: " + registro);
			if (registro instanceof Elemento) {
				crearFilasViaIngreso((Elemento) registro);
			} else if (registro instanceof Administradora) {
				crearFilasAdministradora((Administradora) registro);
			} else if (registro instanceof Procedimientos) {
				crearFilasProcedimiento((Procedimientos) registro);
			} else if (registro instanceof Cie) {
				crearFilasCie((Cie) registro);
			} else if (registro instanceof Prestadores) {
				crearFilasPretador((Prestadores) registro);
			} else if (registro instanceof Centro_atencion) {
				crearFilasCentroSalud((Centro_atencion) registro);
			} else if (registro instanceof Articulo) {
				crearFilasArticulo((Articulo) registro);
			} else if (registro instanceof Usuarios) {
				crearFilasUsuario((Usuarios) registro);
			} else if (registro instanceof Paciente) {
				crearFilasPaciente((Paciente) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFilasPaciente(Paciente registro) {
		final Row row = new Row();

		row.setValue(registro);

		Cell cell = new Cell();
		Label label = new Label("" + registro.getTipo_identificacion());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + registro.getDocumento());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + registro.getApellido1() + " "
				+ registro.getApellido2());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + registro.getNombre1() + " "
				+ registro.getNombre2());
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
													rowPacientes
															.removeChild(row);
												}
											}
										});
					}
				});
		rowPacientes.appendChild(row);
	}

	private void crearFilasUsuario(Usuarios registro) {
		final Row row = new Row();

		row.setValue(registro);

		Cell cell = new Cell();
		Label label = new Label("" + registro.getCodigo());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + registro.getNombres());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + registro.getApellidos());
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
													rowFacturador
															.removeChild(row);
												}
											}
										});
					}
				});
		rowFacturador.appendChild(row);
	}

	private void crearFilasCie(Cie cie) {
		final Row row = new Row();

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
													rowDiagnosticos
															.removeChild(row);
												}
											}
										});
					}
				});
		rowDiagnosticos.appendChild(row);
	}

	private void crearFilasArticulo(final Articulo articulo) {
		final Row row = new Row();
		row.setValue(articulo);

		Cell cell = new Cell();
		Label label = new Label("" + articulo.getCodigo_articulo());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + articulo.getNombre1());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		cell = new Cell();
		cell.appendChild(new Label("MEDICAMENTOS"));
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
													rowServicios
															.removeChild(row);
												}
											}
										});
					}
				});
		rowServicios.appendChild(row);
	}

	private void crearFilasProcedimiento(final Procedimientos procedimientos) {
		final Row row = new Row();
		row.setValue(procedimientos);

		Cell cell = new Cell();
		Label label = new Label("" + procedimientos.getCodigo_cups());
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label("" + procedimientos.getDescripcion());
		cell = new Cell();
		cell.appendChild(label);
		row.appendChild(cell);

		label = new Label(procedimientos.getConsulta().equals("S") ? "CONSULTA"
				: "PROCEDIMIENTO");
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
													rowServicios
															.removeChild(row);
												}
											}
										});
					}
				});
		rowServicios.appendChild(row);
	}

	private void crearFilasPretador(final Prestadores prestadores) {
		final Row row = new Row();
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
													rowMedicos.removeChild(row);
												}
											}
										});
					}
				});
		rowMedicos.appendChild(row);
	}

	private void crearFilasCentroSalud(final Centro_atencion centro_atencion) {
		final Row row = new Row();
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
													rowCentroSalud
															.removeChild(row);
												}
											}
										});
					}
				});
		rowCentroSalud.appendChild(row);
	}

	private void alimentarDetalle(Row row, Detail detail) {
		if (detail.getChildren().isEmpty()) {
			Administradora administradora = row.getValue();

			// cargamos contratos
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("codigo_administradora", administradora.getCodigo());

			// listado de contratos
			List<Contratos> lista_contratos = getServiceLocator()
					.getContratosService().listar(parametros);
			Listbox listboxContratos = getListBox(lista_contratos);
			row.setAttribute("lbxContrato", listboxContratos);
			row.setAttribute("ls_cont", lista_contratos);
			detail.appendChild(listboxContratos);
		}
	}

	private void crearFilasAdministradora(final Administradora administradora) {
		final Row row = new Row();

		final Detail detail = new Detail();
		detail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				alimentarDetalle(row, detail);
			}
		});
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

		// if (listbox.getSelectedCount() > 0) {
		// listbox.setSelectedIndex(0);
		// }
		return listbox;
	}

	private void crearFilasViaIngreso(final Elemento via_ingreso) {
		final Row row = new Row();

		row.setValue(via_ingreso);

		Cell cell = new Cell();
		Label label = new Label("" + via_ingreso.getDescripcion().toUpperCase());
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
													lista_seleccionados_via_ingreso.remove(via_ingreso
															.toString());
													rowVia_ingreso
															.removeChild(row);
												}
											}
										});
					}
				});
		rowVia_ingreso.appendChild(row);
	}

}
