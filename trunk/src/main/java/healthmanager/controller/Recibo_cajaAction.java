package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Detalle_recibo_caja;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Recibo_caja;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Recibo_cajaService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IEstados;
import com.framework.constantes.ITipos;
import com.framework.interfaces.OnGuardar;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Articulo;

public class Recibo_cajaAction extends GeneralComposer {

	private static final long serialVersionUID = -2784444126583828663L;

	@WireVariable
	private Recibo_cajaService recibo_cajaService;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private GeneralExtraService generalExtraService;

	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxNomPaciente;
	@View
	private Textbox tbxCodigo_recibo;
	@View
	private Textbox tbxAdministradora;
	@View
	private Datebox dtbxFecha;
	@View
	private Doublebox dbxTotal_dct;

	@View
	private Listbox lbxFormato;

	// componentes internos de winPago
	@View
	private Doublebox dbxSubtotal;
	@View
	private Doublebox dbxEfectivo;
	@View
	private Doublebox dbxCambio;

	@View
	private Groupbox gbxPago;

	@View
	private Toolbarbutton btGuardar;
	@View
	private Toolbarbutton btEliminar;

	@View
	private Listbox listboxDetalles;
	@View
	private Checkbox chkAplica_descuento;
	@View
	private Textbox tbxQuienAutoriza;
	@View
	private Doublebox dbxDescuento;
	@View
	private Doublebox dbxNeto_pagar;

	public interface IOnGuardarCaja {
		boolean onGuardar();
	}

	private OnGuardar onGuardar;

	private Paciente paciente;
	private Administradora administradora;
	private List<Map<String, Object>> listado_datos;
	private Recibo_caja recibo_caja;
	private String accion_form;
	private String nro_ingreso;
	private Boolean actualizacion;

	private Map<String, Object> parametros;

	private Long id_current;

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {

	}

	@Override
	public void params(Map<String, Object> map) {
		parametros = map;
	}

	@SuppressWarnings("unchecked")
	public void initReciboCaja() throws Exception {
		onGuardar = (OnGuardar) parametros.get("onGuardar");
		paciente = (Paciente) parametros.get("paciente");
		administradora = (Administradora) parametros.get("administradora");
		listado_datos = (List<Map<String, Object>>) parametros
				.get("listado_datos");
		tbxNro_identificacion.setValue(paciente.getDocumento());
		tbxNomPaciente.setValue(paciente.getNombreCompleto());
		tbxAdministradora.setValue(administradora.getCodigo() + " "
				+ administradora.getNombre());
		dtbxFecha.setValue(new Date());
		recibo_caja = (Recibo_caja) parametros.get("recibo_caja");
		accion_form = (String) parametros.get("accion_form");
		actualizacion = (Boolean) parametros.get("actualizacion");
		nro_ingreso = (String) parametros.get("nro_ingreso");
		crearFilas();
	}

	public void listarCombos() {
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		StringBuilder stringBuilder = new StringBuilder();
		if (listboxDetalles.getItems().isEmpty()) {
			stringBuilder
					.append("Se debe agregar por lo menos un detalle para generar el recibo de caja.\n");
			valida = false;
		}
		if (valida && dbxEfectivo.getValue() < dbxNeto_pagar.getValue()) {
			stringBuilder
					.append("El valor efectivo pagado por el cliente debe cubrir el neto a pagar.\n");
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta("Recuerde que...",
					stringBuilder.toString());
		}

		return valida;
	}

	public void inicializarComponentesPagos() {
		dbxSubtotal.setValue(0.0);
		dbxEfectivo.setValue(0);
		dbxCambio.setValue(0);
	}

	public void guardarDatos() throws Exception {
		if (validarForm()) {
			if (onGuardar != null) {
				List<Detalle_recibo_caja> listado_detalles = new ArrayList<Detalle_recibo_caja>();
				if (!actualizacion) {
					Admision admision = (Admision) onGuardar.onGuardar(this);
					this.nro_ingreso = admision.getNro_ingreso();
				}
				Recibo_caja recibo_caja = new Recibo_caja();
				recibo_caja.setCodigo_empresa(codigo_empresa);
				recibo_caja.setCodigo_sucursal(codigo_sucursal);
				recibo_caja.setNro_ingreso(nro_ingreso);
				recibo_caja
						.setAplica_descuento(chkAplica_descuento.isChecked());
				recibo_caja.setEstado(IEstados.RECIBO_CAJA_APROBADO);
				recibo_caja.setFecha(new Timestamp(dtbxFecha.getValue()
						.getTime()));
				recibo_caja.setId(id_current);
				recibo_caja.setNro_identificacion(paciente
						.getNro_identificacion());
				recibo_caja.setObservaciones("");
				recibo_caja.setQuien_autoriza(tbxQuienAutoriza.getValue());
				recibo_caja.setTipo(ITipos.TIPO_RECIBO_CAJA);
				recibo_caja
						.setCreacion_date(new Timestamp(new Date().getTime()));
				recibo_caja.setCreacion_user(usuarios.getCodigo());
				recibo_caja
						.setUltimo_update(new Timestamp(new Date().getTime()));
				recibo_caja.setUltimo_user(usuarios.getCodigo());
				recibo_caja.setValor_descuento(dbxDescuento.getValue());
				recibo_caja.setValor_neto(dbxNeto_pagar.getValue());
				recibo_caja.setValor_total(dbxSubtotal.getValue());
				recibo_caja.setEstado(IEstados.RECIBO_CAJA_APROBADO);
				recibo_caja.setEfectivo(dbxEfectivo.getValue());
				recibo_caja.setCambio(dbxCambio.getValue());
				recibo_caja.setCodigo("RC" + recibo_caja.getNro_ingreso()
						+ recibo_caja.getNro_identificacion());
				recibo_caja.setCodigo_centro(centro_atencion_session
						.getCodigo_centro());
				for (Listitem listitem : listboxDetalles.getItems()) {
					Detalle_recibo_caja detalle_recibo_caja = (Detalle_recibo_caja) listitem
							.getValue();
					listado_detalles.add(detalle_recibo_caja);
				}
				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("recibo_caja", recibo_caja);
				datos.put("listado_detalles", listado_detalles);
				datos.put("accion", "registrar");
				recibo_cajaService.guardarDatos(datos);
				tbxCodigo_recibo.setValue(recibo_caja.getCodigo());
				if (actualizacion) {
					MensajesUtil
							.mensajeInformacion("Recibo generado",
									"El recibo de caja ha sido generado satisfactoriamente");
				}
				btGuardar.setDisabled(true);
				this.id_current = recibo_caja.getId();
			}
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		try {

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void imprimir() throws Exception {
		if (id_current == null) {
			Messagebox.show("El documento no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Recibo_caja_particular");
		paramRequest.put("id_recibo", id_current);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void crearFilas() throws Exception {
		listboxDetalles.getItems().clear();
		if (accion_form.equalsIgnoreCase("registrar")) {
			Double valor_total = 0.0;
			for (Map<String, Object> mapa : listado_datos) {
				String tipo_registro = (String) mapa.get("tipo_registro");
				String codigo_detalle = "";
				String descripcion = "";
				String tipo = "";
				Integer cantidad = 0;
				Double valor_aux = 0D;
				if (tipo_registro.equals("MEDICAMENTO")
						|| tipo_registro.equals("MATERIALES/INSUMOS")
						|| tipo_registro.equals("SERVICIO")) {
					Articulo medicamento = (Articulo) mapa.get("medicamento");
					codigo_detalle = medicamento.getCodigo_articulo();
					descripcion = medicamento.getNombre1();
					tipo = tipo_registro;
					cantidad = (Integer) mapa.get("cantidad_medicamento");
					valor_aux = (Double) mapa.get("valor_total");
				} else {
					Detalle_orden detalle_orden = (Detalle_orden) mapa
							.get("dtt_orden");
					cantidad = detalle_orden.getUnidades();
					codigo_detalle = detalle_orden.getCodigo_cups();
					descripcion = detalle_orden.getNombre_procedimiento();
					valor_aux = detalle_orden.getValor_procedimiento();
					tipo = tipo_registro;
				}
				double valor = cantidad * valor_aux;

				Listitem listitem = new Listitem();
				listitem.appendChild(new Listcell(codigo_detalle));
				listitem.appendChild(new Listcell(descripcion));
				listitem.appendChild(new Listcell(cantidad + ""));
				listitem.appendChild(new Listcell(valor_aux + ""));
				listitem.appendChild(new Listcell(valor + ""));
				Detalle_recibo_caja detalle_recibo_caja = new Detalle_recibo_caja();
				detalle_recibo_caja.setCantidad(cantidad);
				detalle_recibo_caja.setCodigo_detalle(codigo_detalle);
				detalle_recibo_caja.setCreacion_date(new Timestamp(new Date()
						.getTime()));
				detalle_recibo_caja.setCreacion_user(usuarios.getCodigo());
				detalle_recibo_caja.setDescripcion(descripcion);
				detalle_recibo_caja.setTipo(tipo);
				detalle_recibo_caja.setUltimo_update(new Timestamp(new Date()
						.getTime()));
				detalle_recibo_caja.setUltimo_user(usuarios.getUltimo_user());
				detalle_recibo_caja.setValor_total(valor);
				detalle_recibo_caja.setValor_unitario(valor_aux);
				listitem.setValue(detalle_recibo_caja);
				listboxDetalles.appendChild(listitem);
				valor_total += valor;
			}

			dbxTotal_dct.setValue(valor_total);
			dbxSubtotal.setValue(valor_total);
			calcularNetoPagar();
		} else {
			id_current = recibo_caja.getId();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("id_recibo", recibo_caja.getId());
			List<Detalle_recibo_caja> listado_detalles = generalExtraService
					.listar(Detalle_recibo_caja.class, parameters);
			for (Detalle_recibo_caja detalle_recibo_caja : listado_detalles) {
				Integer cantidad = detalle_recibo_caja.getCantidad();
				Listitem listitem = new Listitem();
				listitem.appendChild(new Listcell(detalle_recibo_caja
						.getCodigo_detalle()));
				listitem.appendChild(new Listcell(detalle_recibo_caja
						.getDescripcion()));
				listitem.appendChild(new Listcell(cantidad + ""));
				listitem.appendChild(new Listcell(detalle_recibo_caja
						.getValor_unitario() + ""));
				double valor = cantidad
						* detalle_recibo_caja.getValor_unitario();
				listitem.appendChild(new Listcell(valor + ""));
				listitem.setValue(detalle_recibo_caja);
				listboxDetalles.appendChild(listitem);
			}
			dbxTotal_dct.setValue(recibo_caja.getValor_total());
			dbxSubtotal.setValue(recibo_caja.getValor_total());
			chkAplica_descuento.setChecked(recibo_caja.getAplica_descuento());
			dbxEfectivo.setValue(recibo_caja.getEfectivo());
			dbxCambio.setValue(recibo_caja.getCambio());
			dbxNeto_pagar.setValue(recibo_caja.getValor_neto());
			tbxQuienAutoriza.setValue(recibo_caja.getQuien_autoriza());
			tbxCodigo_recibo.setValue(recibo_caja.getCodigo());
			onAplicaDescuento();
			calcularNetoPagar();
			btGuardar.setDisabled(true);
		}
	}

	@Override
	public void init() throws Exception {
		listarCombos();
		initReciboCaja();
	}

	public void onAplicaDescuento() {
		if (chkAplica_descuento.isChecked()) {
			tbxQuienAutoriza.setValue("");
			tbxQuienAutoriza.setReadonly(false);
			dbxDescuento.setReadonly(false);
			dbxDescuento.setValue(0.0);
		} else {
			tbxQuienAutoriza.setValue("");
			tbxQuienAutoriza.setReadonly(true);
			dbxDescuento.setReadonly(true);
			dbxDescuento.setValue(0.0);
		}
		calcularNetoPagar();
	}

	public void calcularNetoPagar() {
		Double valor_descuento = 0.0;
		if (chkAplica_descuento.isChecked()) {
			valor_descuento = (dbxDescuento.getValue() != null ? dbxDescuento
					.getValue() : 0.0);
		}
		dbxNeto_pagar.setValue(dbxSubtotal.getValue() - valor_descuento);
	}

	public void calcularCambio() {
		Double efectivo = dbxEfectivo.getValue() != null ? dbxEfectivo
				.getValue() : 0.0;
		Double neto_pagar = dbxNeto_pagar.getValue() != null ? dbxNeto_pagar
				.getValue() : 0.0;
		dbxCambio.setValue(efectivo - neto_pagar);
	}
}
