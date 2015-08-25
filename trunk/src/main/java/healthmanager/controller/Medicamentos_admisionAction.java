/*
 * usuariosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.service.ArticuloService;

public class Medicamentos_admisionAction extends GeneralComposer implements
		ISeleccionarComponente {

	// private static Logger log = Logger
	// .getLogger(Medicamentos_admisionAction.class);

	// Componentes //
	@WireVariable
	private ArticuloService articuloService;

	@View
	private Listbox lbxMedicamentosAfacturar;
	@View
	private Toolbarbutton btnAgregar_medicamento;
	@View
	private Toolbarbutton btnQuitar_medicamento;

	private List<String> seleccionados_medicamentos = new ArrayList<String>();

	private static final String NO_ELIMINAR = "no_e";
	private String via_ingreso;

	private Manuales_tarifarios manuales_tarifarios;

	private boolean atencion_particular;

	@Override
	public void init() {

	}

	@Override
	public void onClose() {
		setVisible(false);
	}

	/**
	 * Este metodo me carga los servicios que tenga pendientes por realizar este
	 * paciente
	 * 
	 * @param string
	 * */
	public void mostrarServicios(String via_ingreso, Paciente paciente,
			boolean atencion_particular, Contratos contratos) {
		// Cargamos via ingreso
		this.via_ingreso = via_ingreso;
		this.atencion_particular = atencion_particular;

		// Consultamos el manual tariafario donde tenga contratado
		// el servicio el paciente
		Admision admision = new Admision();
		admision.setCodigo_empresa(paciente.getCodigo_empresa());
		admision.setCodigo_sucursal(paciente.getCodigo_sucursal());
		admision.setNro_identificacion(paciente.getNro_identificacion());
		admision.setCodigo_administradora(contratos != null ? contratos
				.getCodigo_administradora() : paciente
				.getCodigo_administradora());
		admision.setVia_ingreso(via_ingreso);
		admision.setId_plan(contratos != null ? contratos.getId_plan() : "");
		admision.setParticular(atencion_particular ? "S" : "N");
		// log.info("Admision: " + admision);
		manuales_tarifarios = ServiciosDisponiblesUtils
				.getManuales_tarifarios(admision);
		// log.info("Manuales: " + manuales_tarifarios);
		if (manuales_tarifarios != null) {
			// Obtenemos la lista de los servicios correspondientes
			String servicios[] = ServiciosDisponiblesUtils
					.getServiciosViaIngresoAux(sucursal, via_ingreso);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codigo_empresa", paciente.getCodigo_empresa());
			params.put("codigo_sucursal", paciente.getCodigo_sucursal());
			params.put("codigo_paciente", paciente.getNro_identificacion());

			if (habilitarFiltro()) {
				params.put("tipo_procedimiento_in", servicios);
			}

			btnAgregar_medicamento.setVisible(true);
			btnQuitar_medicamento.setVisible(true);
			lbxMedicamentosAfacturar.setMultiple(true);
			lbxMedicamentosAfacturar.setCheckmark(true);

			lbxMedicamentosAfacturar.invalidate();
		} else {
			onClose();
		}
	}

	public void limpiar() {
		lbxMedicamentosAfacturar.getChildren().clear();
		seleccionados_medicamentos.clear();
	}

	private boolean habilitarFiltro() {
		return (ServiciosDisponiblesUtils.isApoyoDiagnostico(via_ingreso)
				|| via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
				|| via_ingreso.equals(IVias_ingreso.VIA_VACUNACION) || via_ingreso
					.equals(IVias_ingreso.SERVICIOS_AMBULATORIOS));
	}

	public void openMedicamentos() throws Exception {
		String pages = "/pages/openArticulo.zul";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		parametros.put("seleccionados", seleccionados_medicamentos);

		parametros.put("pyp", "S");
		OpenArticuloAction componente = (OpenArticuloAction) Executions
				.createComponents(pages, null, parametros);

		componente.setSeleccionar_componente(this);

		componente.setWidth("990px");
		componente.setHeight("400px");
		componente.setClosable(true);
		componente.setMaximizable(true);
		componente.setTitle("CONSULTAR MEDICAMENTOS E INSUMOS DE PYP");
		componente.setMode("modal");

	}

	public void quitarListitemSeleccionados() {
		Set<Listitem> listado_items = lbxMedicamentosAfacturar
				.getSelectedItems();
		List<Listitem> listitems_eliminar = new ArrayList<Listitem>();
		if (listado_items.isEmpty()) {
			MensajesUtil.mensajeAlerta("Advertencia",
					"Para esta accion debe seleccionar por lo menos un item");
		} else {
			boolean servicions_ordenados_por_medico = false;
			for (Listitem listitem : listado_items) {
				if (listitem.getAttribute(NO_ELIMINAR) == null) {
					Articulo detalle_medicamento = (Articulo) listitem
							.getValue();
					seleccionados_medicamentos.remove(detalle_medicamento
							.getCodigo_articulo());
					listitems_eliminar.add(listitem);
				} else {
					servicions_ordenados_por_medico = true;
				}
			}
			lbxMedicamentosAfacturar.getItems().removeAll(listitems_eliminar);

			if (servicions_ordenados_por_medico) {
				MensajesUtil.mensajeAlerta("Advertencia",
						"Solo puede eliminar los servicios que adicione");
			}
		}
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		String codigo_articulo = (String) pcd.get("codigo_articulo");
		Double valor_total = (Double) pcd.get("valor_total");
		seleccionados_medicamentos.add(codigo_articulo);

		String nombre_articulo = "";

		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(codigo_empresa);
		articulo.setCodigo_sucursal(codigo_sucursal);
		articulo.setCodigo_articulo(codigo_articulo);
		articulo = articuloService.consultar(articulo);
		nombre_articulo = (articulo != null ? articulo.getNombre1() : "");

		final Listitem listitem = new Listitem();
		listitem.setAttribute("VALOR_TOTAL", valor_total);
		listitem.setValue(articulo);
		listitem.appendChild(new Listcell(codigo_articulo));
		listitem.appendChild(Utilidades.obtenerListcell(nombre_articulo,
				Textbox.class, true, true));
		Listcell listcell = Utilidades.obtenerListcell(1.0, Doublebox.class,
				false, true);
		Doublebox doublebox = (Doublebox) listcell.getFirstChild();
		doublebox.setId("doublebox_codigo_articulo_" + codigo_articulo);
		doublebox.setFormat("#####0");
		doublebox.setValue(1);
		listitem.appendChild(listcell);
		listitem.setSelected(true);
		lbxMedicamentosAfacturar.appendChild(listitem);
	}

	/**
	 * Este metodo me permite calcular el valor total de los procedimientos
	 * */

	public List<Map<String, Object>> getListadoMedicamentos() {
		List<Map<String, Object>> listado_ordenes = new ArrayList<Map<String, Object>>();
		Collection<Listitem> seleccionados = atencion_particular ? lbxMedicamentosAfacturar
				.getItems() : lbxMedicamentosAfacturar.getSelectedItems();
		for (Listitem listitem : seleccionados) {
			Double valor_total = (Double) listitem.getAttribute("VALOR_TOTAL");
			Map<String, Object> dato_medicamento = new HashMap<String, Object>();
			Articulo medicamento = (Articulo) listitem.getValue();
			dato_medicamento.put("medicamento", medicamento);
			dato_medicamento.put("valor_total", valor_total);
			Doublebox doublebox = (Doublebox) listitem
					.getFellowIfAny("doublebox_codigo_articulo_"
							+ medicamento.getCodigo_articulo());
			if (doublebox != null && doublebox.getValue() != null
					&& doublebox.getValue().intValue() != 0) {
				dato_medicamento.put("cantidad_medicamento", doublebox
						.getValue().intValue());
				listado_ordenes.add(dato_medicamento);
			}

		}
		return listado_ordenes;
	}

	public void limpiarDatos() {
		lbxMedicamentosAfacturar.getItems().clear();
		seleccionados_medicamentos.clear();
	}

	public String getCodigoOrden(List<Detalle_orden> detalle_ordens) {
		String codigo_orden_final = detalle_ordens.get(0).getCodigo_orden()
				+ "_";
		for (Detalle_orden detalle_orden : detalle_ordens) {
			codigo_orden_final += "_" + detalle_orden.getCodigo_procedimiento();
		}
		return codigo_orden_final;
	}

	public String getVia_ingreso() {
		return via_ingreso;
	}

	public void setVia_ingreso(String via_ingreso) {
		this.via_ingreso = via_ingreso;
	}

}