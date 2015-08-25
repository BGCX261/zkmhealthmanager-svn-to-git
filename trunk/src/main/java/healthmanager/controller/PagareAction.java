package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Anio_cuota_moderadora;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.Afiliaciones_meService;
import healthmanager.modelo.service.Anio_cuota_moderadoraService;
import healthmanager.modelo.service.PacienteService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Detalle_pagare;
import contaweb.modelo.bean.Empresa;
import contaweb.modelo.bean.Pagare;
import contaweb.modelo.service.EmpresaService;
import contaweb.modelo.service.PagareService;

public class PagareAction extends GeneralComposer {

	@WireVariable
	private AdministradoraService administradoraService;
	@WireVariable
	private PagareService pagareService;
	@WireVariable
	private Afiliaciones_meService afiliaciones_meService;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private Anio_cuota_moderadoraService anio_cuota_moderadoraService;
	@WireVariable("empresaContawebService")
	private EmpresaService empresa_contService;

	@View
	private Grid gridDetalle;
	@View
	private Rows rowsDetalle;

	@View
	private Textbox tbxCodigo_tercero;
	@View
	private Textbox tbxNomTercero;
	@View
	private Textbox tbxCodigo_documento;
	@View
	private Textbox tbxAdministradora;
	@View
	private Datebox dtbxFecha;
	@View
	private Doublebox dbxTotal_dct;
	@View
	private Doublebox dbxTotal_copago;
	@View
	private Label lbCodigo_anexo4;
	@View
	private Textbox tbxCodigo_anexo4;
	@View
	private Textbox tbxCopago_medicamentos;
	@View
	private Textbox tbxCopago_autorizaciones;
	@View
	private Listbox lbxFormato;

	@View
	private Column colCopago;
	@View
	private Column colValor_unitario;
	@View
	private Column colValor_total;
	@View
	private Column colPorcentaje;
	@View
	private Column colValorAdicional;
	@View
	private Label lbTotal;

	@View
	private Intbox ibxNro_cuota;
	@View
	private Datebox dtbxPrimer_vencimiento;

	private Map<String, Object> mapPagare;

	public final String TIPO_CAJA_COPAGO = "04";
	public final String TIPO_CAJA_CUOTA_MODERADORA = "05";
	public final String TIPO_CAJA_PARTICULAR = "06";

	private Double cuota_moderadora;
	private String copago_autorizaciones;
	private Paciente paciente;
	private String codigo_cita;
	private String codigo_orden;
	private List<Map> lista_datos;

	private static final String IDS[] = { "btGuardar" };

	private Map<String, Object> parametros;
	private String nro_ingreso;
	private String codigo_administradora;

	@View
	private Doublebox dbxInteres;
	@View
	private Doublebox dbxValor_neto;

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		lista_datos = new LinkedList<Map>();
	}

	@Override
	public void params(Map<String, Object> map) {
		parametros = map;
	}

	public void initPagare() throws Exception {
		paciente = (Paciente) parametros.get("paciente");
		String nro_autorizacion = (String) parametros.get("nro_autorizacion");
		copago_autorizaciones = (String) parametros
				.get("copago_autorizaciones");
		String copago_medicamentos = (String) parametros
				.get("copago_medicamentos");
		cuota_moderadora = (Double) parametros.get("cuota_moderadora");

		codigo_cita = (String) parametros.get("codigo_cita");
		codigo_orden = (String) parametros.get("codigo_orden");
		codigo_administradora = (String) parametros
				.get("codigo_administradora");
		nro_ingreso = (String) parametros.get("nro_ingreso");
		// log.info("Type Caja: " + tipo_recibo_caja);
		// log.info("Orden de servicio: " + codigo_orden);

		mapPagare = (Map<String, Object>) parametros.get("mapPagare");
		Pagare pagare = (Pagare) mapPagare.get("pagare");
		List<Detalle_pagare> lista_detalle = (List<Detalle_pagare>) mapPagare
				.get("lista_detalle_pagare");


	

		if (copago_medicamentos.equals("S")) {
			lbCodigo_anexo4.setVisible(false);
			tbxCodigo_anexo4.setVisible(false);
		}

		limpiarDatos();
		// log.info("Pagaré: " + pagare);
		if (pagare != null) {
			mostrarDatos(pagare);
		} else {

			tbxCodigo_tercero.setValue((paciente != null ? paciente
					.getNro_identificacion() : ""));
			tbxNomTercero.setValue((paciente != null ? paciente.getNombre1()
					+ " " + paciente.getApellido1() + " "
					+ paciente.getApellido2() : ""));

			Administradora admin = new Administradora();
			admin.setCodigo(codigo_administradora != null ? codigo_administradora
					: (paciente != null ? paciente.getCodigo_administradora()
							: ""));
			admin.setCodigo_empresa(codigo_empresa);
			admin.setCodigo_sucursal(codigo_sucursal);
			admin = administradoraService.consultar(admin);
			tbxAdministradora
					.setValue((admin != null ? admin.getNombre() : ""));

			tbxCodigo_anexo4.setValue(nro_autorizacion);
			tbxCopago_autorizaciones.setValue(copago_autorizaciones);
			tbxCopago_medicamentos.setValue(copago_medicamentos);

			lista_datos.clear();
			rowsDetalle.getChildren().clear();
			if (lista_detalle != null) {
				for (Detalle_pagare detalle : lista_detalle) {
					detalle.setValor_unitario((Math.rint(detalle
							.getValor_unitario() * 1) / 1));
					detalle.setValor_total(detalle.getValor_unitario()
							* detalle.getCantidad());
					detalle.setCopago((Math.rint(detalle.getCopago() * 1) / 1));
					Map bean = llenarBeanDetalle(detalle, false);
					lista_datos.add(bean);
				}
			}
			cambiarFechaVencimiento();
			crearFilas(true);
			calcularTotal();
			calcularTotalInteres();
		}
	}

	public void listarCombos() {

	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(this, IDS);

		lista_datos.clear();
		crearFilas(true);

		calcularTotal();

		ibxNro_cuota.setValue(1);

		((Button) getFellow("btGuardar")).setDisabled(false);
		((Button) getFellow("btGuardar")).setImage("/images/Save16.gif");
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxCodigo_tercero
				.setStyle("text-transform:uppercase;background-color:white");
		tbxNomTercero
				.setStyle("text-transform:uppercase;background-color:white");

		tbxCodigo_anexo4
				.setStyle("text-transform:uppercase;background-color:white");
		dbxTotal_dct.setStyle("background-color:white");
		dbxTotal_copago.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo_tercero.getText().trim().equals("")) {
			tbxCodigo_tercero
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			tbxNomTercero
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (valida && tbxCodigo_anexo4.getValue().trim().equals("")
				&& tbxCopago_autorizaciones.getValue().equals("S")) {
			tbxCodigo_anexo4
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		
			valida = false;
		}

		if (valida && dbxTotal_dct.getValue() <= 0) {
			dbxTotal_dct
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			mensaje = "El valor total no puede ser menor ni igual a cero (0)";
			valida = false;
		}
		if (valida && dbxTotal_copago.getValue() <= 0) {
			dbxTotal_copago
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			mensaje = "El valor del copago no puede ser menor ni igual a cero (0)";
			valida = false;
		}

		if (valida) {
			for (int i = 0; i < (lista_datos.size()); i++) {

				Map bean = lista_datos.get(i);

				String codigo_articulo = (String) bean.get("codigo_articulo");
				double valor_total = (Double) bean.get("valor_total");
				double copago = (Double) bean.get("copago");

				Row fila = (Row) rowsDetalle.getFellow("fila_" + i);
				Doublebox tbxValor_total = (Doublebox) fila
						.getFellow("valor_total_" + i);
				Doublebox tbxCopago = (Doublebox) fila.getFellow("copago_" + i);

				tbxValor_total.setStyle("background-color:white");
				tbxCopago.setStyle("background-color:white");

				if (tbxCopago_autorizaciones.getValue().equals("S")) {
					if (valor_total <= 0) {
						tbxValor_total
								.setStyle("text-transform:uppercase;background-color:#F6BBBE");
						mensaje = "El valor total del codigo  "
								+ codigo_articulo
								+ " no puede ser menor ni igual a cero (0)";
						valida = false;
						i = lista_datos.size();
						// El copago no se cobra en particular
					} else if (copago <= 0
							) {
						tbxCopago
								.setStyle("text-transform:uppercase;background-color:#F6BBBE");
						mensaje = "El valor del copago del codigo  "
								+ codigo_articulo
								+ " no puede ser menor ni igual a cero (0)";
						valida = false;
						i = lista_datos.size();
					}
				}
			}
		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;

	}

	public void guardarDatos() throws Exception {
		try {
			Timestamp fecha_pagare = new Timestamp(dtbxFecha.getValue()
					.getTime());
			Timestamp primer_vencimiento = new Timestamp(dtbxPrimer_vencimiento
					.getValue().getTime());

			Map<String, Object> datos = new HashMap<String, Object>();
			datos.put("codigo_empresa", empresa.getCodigo_empresa());
			datos.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			datos.put("tipo_doc", "VENTA");
			datos.put("codigo_tercero", tbxCodigo_tercero.getValue());
			datos.put("fecha_pagare", fecha_pagare);
			datos.put("primer_vencimiento", primer_vencimiento);
			datos.put("nro_cuota", ibxNro_cuota.getValue());
			datos.put("id_usuario", usuarios.getCodigo() + "");
			datos.put("codigo_cita", "");
			datos.put("codigo_orden", codigo_orden);
			datos.put("nro_ingreso", nro_ingreso);
			
			datos.put("lista_detalle", lista_datos);
			datos.put("pago_factura", false);

			datos.put("afiliado", getAfiliacion());
			datos.put("usuario", usuarios);

			Pagare pagare = pagareService.guardarPagare(datos);

			mostrarDatos(pagare);

			final String codigo_doc = pagare.getCodigo_documento();
			final String cod_comp = pagare.getCodigo_comprobante();

			GeneralComposer action = (GeneralComposer) this.getParent();
			if (action instanceof Recibo_cajaAction) {
				Recibo_cajaAction caja_cuota_moderadoraAction = (Recibo_cajaAction) action;
				
			}

			Messagebox.show("Desea imprimir el documento? ", "impresion",
					Messagebox.YES + Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								imprimir(cod_comp, codigo_doc);
							}
						}
					});
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private Afiliaciones_me getAfiliacion() {

		Afiliaciones_me afiliacionesMe = new Afiliaciones_me();
		afiliacionesMe.setCodigo_empresa(sucursal.getCodigo_empresa());
		afiliacionesMe.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		afiliacionesMe.setNro_identificacion_afiliado(paciente
				.getNro_identificacion());
		return afiliaciones_meService.consultar(afiliacionesMe);
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Pagare pagare = (Pagare) obj;
		try {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(pagare.getCodigo_empresa());
			paciente.setCodigo_sucursal(pagare.getCodigo_sucursal());
			paciente.setNro_identificacion(pagare.getCodigo_tercero());
			paciente = pacienteService.consultar(paciente);

			tbxCodigo_documento.setValue(pagare.getCodigo_documento());
			dtbxFecha.setValue(new Date(pagare.getFecha().getTime()));
			dtbxPrimer_vencimiento.setValue(new Date(pagare
					.getPrimer_vencimiento().getTime()));
			ibxNro_cuota.setValue(pagare.getNro_cuota());
			tbxCodigo_tercero.setValue((paciente != null ? paciente
					.getNro_identificacion() : ""));
			tbxNomTercero.setValue((paciente != null ? paciente.getNombre1()
					+ " " + paciente.getApellido1() + " "
					+ paciente.getApellido2() : ""));

			Administradora admin = new Administradora();
			admin.setCodigo((paciente != null ? paciente
					.getCodigo_administradora() : ""));
			admin.setCodigo_empresa(codigo_empresa);
			admin.setCodigo_sucursal(codigo_sucursal);
			admin = administradoraService.consultar(admin);
			tbxAdministradora
					.setValue((admin != null ? admin.getNombre() : ""));

			tbxCodigo_anexo4.setValue(pagare.getCodigo_orden());
			tbxCopago_autorizaciones
					.setValue(pagare.getCopago_autorizaciones());
			tbxCopago_medicamentos.setValue(pagare.getCopago_medicamentos());

			lista_datos.clear();
			rowsDetalle.getChildren().clear();
			List<Detalle_pagare> lista_detalle = pagare.getLista_detalle();
			if (lista_detalle != null) {
				for (Detalle_pagare detalle : lista_detalle) {
					Map bean = llenarBeanDetalle(detalle, false);
					lista_datos.add(bean);
				}
			}
			dbxInteres.setValue(pagare.getInteres());
			crearFilas(true);
			calcularTotal();

			((Button) this.getFellow("btGuardar")).setDisabled(true);
			((Button) this.getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void imprimir(String codigo_comprobante, String codigo_documento)
			throws Exception {
		if (codigo_documento.equals("")) {
			Messagebox.show("El documento no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Pagare");
		paramRequest.put("codigo_comprobante", codigo_comprobante);
		paramRequest.put("codigo_documento", codigo_documento);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void imprimirNota_contable(String codigo_comprobante,
			String codigo_documento) throws Exception {
		if (codigo_documento.equals("")) {
			Messagebox.show("El documento no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Nota_contable");
		paramRequest.put("codigo_comprobante", codigo_comprobante);
		paramRequest.put("codigo_documento", codigo_documento);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void contabilizar(String codigo_comprobante, String codigo_documento)
			throws Exception {
		try {
			if (codigo_documento.equals("")) {
				Messagebox
						.show("El documento no se ha guardado aún",
								"Informacion ..", Messagebox.OK,
								Messagebox.EXCLAMATION);
				return;
			}

			Map<String, Object> param_cont = new HashMap<String, Object>();
			param_cont.put("codigo_empresa", empresa.getCodigo_empresa());
			param_cont.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param_cont.put("codigo_documento", codigo_documento);
			param_cont.put("codigo_comprobante", codigo_comprobante);
			pagareService.guardarContabilizacionPagare(param_cont);

			Messagebox.show("El documento se contabilizó satisfactoriamente",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	private Map llenarBeanDetalle(Detalle_pagare detalle, boolean sw)
			throws Exception {
		Map bean = new HashMap();
		bean.put("codigo_articulo", detalle.getCodigo_servicio());
		bean.put("detalle", detalle.getConcepto());
		bean.put("cant", detalle.getCantidad());
		bean.put("valor_unit", detalle.getValor_unitario());
		bean.put("valor_total", detalle.getValor_total());
		bean.put("copago", detalle.getCopago());
		bean.put("porcentaje_add", 0.0);
		return bean;
	}

	public void adicionarArticulo(Detalle_pagare detalle) throws Exception {
		try {
			Map bean = llenarBeanDetalle(detalle, true);
			lista_datos.add(bean);
			crearFilas(true);
			// repintarGridCuentas();
		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error al adicionar cuenta",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void crearFilas(boolean sw) throws Exception {
		rowsDetalle.getChildren().clear();
		// //log.info("lista_datos crearFilascrearFilas: "+lista_datos);
		for (int j = 0; j < lista_datos.size(); j++) {
			Map bean = lista_datos.get(j);
			crearFilaArticulo(bean, j, sw);
		}
	}

	public void crearFilaArticulo(Map bean, int j, boolean bloquea)
			throws Exception {
		String codigo_articulo = (String) bean.get("codigo_articulo");
		String detalle = (String) bean.get("detalle");
		double cantidad = (Double) bean.get("cant");
		double valor_unitario = (Double) bean.get("valor_unit");
		double valor_total = (Double) bean.get("valor_total");
		double copago = (Double) bean.get("copago");
		double porcentaje = (Double) bean.get("porcentaje_add");

		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		boolean showValuesCadicioanl = true;

		if (copago_autorizaciones.equals("S")) {
			showValuesCadicioanl = false;
			// En el valor unitario va el valor adicional
			// cuota_moderadora += valor_unitario;
		}

		// Columna codigo articulo //
		Cell cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_articulo = new Textbox(codigo_articulo);
		tbxCodigo_articulo.setInplace(true);
		tbxCodigo_articulo.setId("codigo_articulo_" + j);
		tbxCodigo_articulo.setHflex("1");
		tbxCodigo_articulo.setReadonly(true);
		cell.appendChild(tbxCodigo_articulo);
		cell.setStyle("padding:1px;border: 1px solid #D8D8D8;");
		fila.appendChild(cell);

		// Columna nombre //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_articulo = new Textbox(detalle);
		tbxNombre_articulo.setInplace(true);
		tbxNombre_articulo.setId("nombre_articulo_" + j);
		tbxNombre_articulo.setHflex("1");
		tbxNombre_articulo.setReadonly(true);
		cell.setStyle("padding:1px;border: 1px solid #D8D8D8;");
		cell.appendChild(tbxNombre_articulo);
		fila.appendChild(cell);

		// Columna cantidad //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxCant = new Doublebox(cantidad);
		tbxCant.setInplace(true);
		tbxCant.setId("cant_" + j);
		tbxCant.setFormat("#,##0");
		tbxCant.setHflex("1");
		tbxCant.setReadonly(true);
		cell.setStyle("padding:1px;border: 1px solid #D8D8D8;");
		cell.appendChild(tbxCant);
		fila.appendChild(cell);

		// Columna valor unit //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValor_unit = new Doublebox(valor_unitario);
		tbxValor_unit.setInplace(true);
		tbxValor_unit.setId("valor_unit_" + j);
		tbxValor_unit.setFormat("#,##0.00");
		tbxValor_unit.setHflex("1");
		tbxValor_unit.setReadonly(true);
		cell.setStyle("padding:1px;border: 1px solid #D8D8D8;");
		cell.appendChild(tbxValor_unit);
		fila.appendChild(cell);

		// Columna valor total //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValor_total = new Doublebox(valor_total);
		tbxValor_total.setInplace(true);
		tbxValor_total.setId("valor_total_" + j);
		tbxValor_total.setFormat("#,##0.00");
		tbxValor_total.setHflex("1");
		tbxValor_total.setReadonly(true);
		cell.setStyle("padding:1px;border: 1px solid #D8D8D8;");
		cell.appendChild(tbxValor_total);
		fila.appendChild(cell);

		// Columna porcentaje //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxPorcentaje = new Doublebox(porcentaje);
		tbxPorcentaje.setInplace(true);
		tbxPorcentaje.setId("porcentaje_" + j);
		tbxPorcentaje.setFormat("#,##0.##");
		tbxPorcentaje.setHflex("1");
		tbxPorcentaje.setReadonly(true);
		cell.setStyle("padding:1px;border: 1px solid #D8D8D8;");
		cell.appendChild(tbxPorcentaje);
		bean.put("valor_adicional", tbxPorcentaje.getValue().doubleValue());
		if (showValuesCadicioanl) {
			fila.appendChild(cell);
		}
		fila.appendChild(cell);

		// Columna valoradional //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxValorAdiconal = new Doublebox((valor_total
				* porcentaje / 100));
		tbxValorAdiconal.setInplace(true);
		tbxValorAdiconal.setId("valor_adicional_" + j);
		tbxValorAdiconal.setFormat("#,##0.00");
		tbxValorAdiconal.setHflex("1");
		tbxValorAdiconal.setReadonly(true);
		cell.setStyle("padding:1px;border: 1px solid #D8D8D8;");
		cell.appendChild(tbxValorAdiconal);
		if (showValuesCadicioanl)
			fila.appendChild(cell);
		fila.appendChild(cell);
		// Columna copago //
		cell = new Cell();
		cell.setAlign("left");
		final Doublebox tbxCopago = new Doublebox(copago);
		tbxCopago.setInplace(true);
		tbxCopago.setId("copago_" + j);
		tbxCopago.setFormat("#,##0.00");
		tbxCopago.setHflex("1");
		tbxCopago.setReadonly(true);
		cell.setStyle("padding:1px;border: 1px solid #D8D8D8;");
		cell.appendChild(tbxCopago);
		fila.appendChild(cell);

		fila.setId("fila_" + j);

		rowsDetalle.appendChild(fila);

		gridDetalle.setVisible(true);
		gridDetalle.setMold("paging");
		gridDetalle.setPageSize(20);
		gridDetalle.applyProperties();
		gridDetalle.invalidate();
	}

	public void calcularTotal() throws Exception {
		double sum_total = 0;
		double sum_copago = 0;
		double sum_valor_adicionarl = 0;

		for (int i = 0; i < lista_datos.size(); i++) {

			Map datos = lista_datos.get(i);
			double porcentaje = (Double) datos.get("porcentaje_add");

			Row fila = (Row) rowsDetalle.getFellow("fila_" + i);

			Doublebox tbxValor_total = (Doublebox) fila
					.getFellow("valor_total_" + i);

			/* si es medicamento */
			double valor_total = (tbxValor_total.getValue() != null ? tbxValor_total
					.getValue() : 0);
			if (!copago_autorizaciones.equals("S")) {
				sum_valor_adicionarl += (valor_total * porcentaje / 100);
			}
			Doublebox tbxCopago = (Doublebox) fila.getFellow("copago_" + i);
			sum_total += valor_total;
			sum_copago += (tbxCopago.getValue() != null ? tbxCopago.getValue()
					: 0);
		}
		dbxTotal_dct.setValue(sum_total);
		// log.info("Es un copago: " + copago_autorizaciones);
		

		double valor_interes = dbxInteres.getValue();
		double valor_total = 0.0;
	
		dbxValor_neto.setValue(valor_total + valor_interes);

		// log.info("Total copago: " + dbxTotal_copago.getValue());
	}

	public void consultarValor_copago() throws Exception {
		try {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(empresa.getCodigo_empresa());
			paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			paciente.setNro_identificacion(tbxCodigo_tercero.getValue());
			paciente = pacienteService.consultar(paciente);
			String grupo = (paciente != null ? paciente.getGrupo_cuota() : "");

			String anioo = new SimpleDateFormat("yyyy").format(dtbxFecha
					.getValue());

			Anio_cuota_moderadora anio_cuota_moderadora = new Anio_cuota_moderadora();
			anio_cuota_moderadora
					.setCodigo_empresa(empresa.getCodigo_empresa());
			anio_cuota_moderadora.setGrupo(grupo);
			anio_cuota_moderadora.setAnio(anioo);
			anio_cuota_moderadora = anio_cuota_moderadoraService
					.consultar(anio_cuota_moderadora);
			double porcentaje = (anio_cuota_moderadora != null ? anio_cuota_moderadora
					.getPorcentaje_copago() : 0);

			for (int i = 0; i < lista_datos.size(); i++) {
				Map bean = lista_datos.get(i);
				Row fila = (Row) rowsDetalle.getFellow("fila_" + i);

				Doublebox tbxCant = (Doublebox) fila.getFellow("cant_" + i);
				Doublebox tbxValor_unit = (Doublebox) fila
						.getFellow("valor_total_" + i);
				Doublebox tbxCopago = (Doublebox) fila.getFellow("copago_" + i);

				double cantidad = (tbxCant.getValue() != null ? tbxCant
						.getValue() : 0);
				double valor_pcd = (tbxValor_unit.getValue() != null ? tbxValor_unit
						.getValue() : 0);
				double copago = (valor_pcd * (porcentaje / 100)) * cantidad;

				tbxCopago.setValue(copago);
				bean.put("copago", copago);
				lista_datos.set(i, bean);
			}

			calcularTotal();

		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void cambiarFechaVencimiento() {
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.setTimeInMillis(dtbxFecha.getValue().getTime());
		fecha.set(Calendar.DAY_OF_MONTH,
				(fecha.get(Calendar.DAY_OF_MONTH) + 30));
		dtbxPrimer_vencimiento.setValue(fecha.getTime());
	}

	public void calcularTotalInteres() {
		Empresa empresaCont = new Empresa();
		empresaCont.setCodigo_empresa(codigo_empresa);
		empresaCont = empresa_contService.consultar(empresaCont);
		double porcentaje_interes = ((empresaCont != null ? empresaCont
				.getInteres_pagares() : 0.0) / 100);

		double valor = 0.0;
		
		double auxValor = valor;
		int nro_cuotas = (ibxNro_cuota.getValue() != null ? ibxNro_cuota
				.getValue() : 1);
		double valores_intereses[] = new double[nro_cuotas + 1];
		double valor_cuota = valor / nro_cuotas;
		valor_cuota = (Math.rint(valor_cuota * 1) / 1);
		for (int i = 0; i <= nro_cuotas; i++) {
			if (valor >= 0) {
				valores_intereses[i] = (Math
						.rint((auxValor * porcentaje_interes) * 1) / 1);
			} else {
				valores_intereses[i] = 0.0;
			}
			auxValor -= valor_cuota;
		}
		double total_intereses = 0.0;
		for (int i = 0; i <= nro_cuotas; i++) {
			total_intereses += valores_intereses[i];
		}

		dbxInteres.setValue(total_intereses);
		dbxValor_neto.setValue((valor + total_intereses));

	}

	@Override
	public void init() throws Exception {
		listarCombos();
		initPagare();
	}
}
