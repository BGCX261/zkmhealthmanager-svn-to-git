/*
 * facturacion_medicamentoAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Facturacion_medicamento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.AdmisionService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IRoles;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Centro_costo;
import contaweb.modelo.service.ArticuloService;

public class Facturacion_medicamentoAction extends ZKWindow {

	private static final long serialVersionUID = -9145887024839938515L;
	
	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout borderlayoutEditar;

	@View
	private Groupbox groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Toolbarbutton btGuardar;
	@View
	private Toolbarbutton btNew;
	@View
	private Auxheader auxheaderInformacion;

	@View
	private Listbox listboxRegistros;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxPaciente;

	@View
	private Textbox tbxInfoPaciente;

	@View
	private Toolbarbutton btnLimpiarPaciente;

	@View
	private Listbox lbxNro_ingreso;

	@View
	private Textbox tbxAutorizacion;

	@View
	private Listbox lbxArea;

	@View
	private Doublebox dbxTotal;

	@View
	private Textbox tbxNro_factura;
	@View
	private Textbox tbxTipo_identificacion;

	@View
	private Textbox tbxCodigo_administradora;
	@View
	private Textbox tbxId_plan;

	@View
	private Datebox dtbxFecha_medicamento;

	@View
	private Textbox tbxObservacion;
	@View
	private Textbox tbxTipo;

	@View
	private Textbox tbxCodigo_solicitud;
	@View
	private Textbox tbxCodigo_receta;
	// 01 es medicamento
	// 02 es material de insumo
	private String tipo_medicamento = "01";

	private Admision admision;
	private List<String> medicamentosSeleccionados = new ArrayList<String>();

	private final String[] idsExcluyentes = new String[] { "bandboxPaciente",
			"btnLimpiarPaciente" };

	@Override
	public void init() throws Exception{

		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		String tmedicamento = request.getParameter("tipo_medicamento");
		
		if(tmedicamento != null && !tmedicamento.isEmpty()){
			setTipo_medicamento(tmedicamento);
		}
		
		parametrizarBandbox();
		listarCombos(); 
		deshabilitarComponentes();
		
		if (tipo_medicamento.equals("01")) {
			btGuardar.setLabel("Guardar Facturacion medicamento");
			btNew.setLabel("Nueva Facturacion medicamento");
			auxheaderInformacion
					.setLabel("REGISTRAR FACTURAcion DE MEDICAMENTOS");
		} else {
			btGuardar.setLabel("Guardar Facturacion Materiales de Insumos");
			btNew.setLabel("Nueva Facturacion Materiales de Insumos");
			auxheaderInformacion
					.setLabel("REGISTRAR FACTURAcion DE MATERIALES DE INSUMOS");
		}
		
		boolean ocultarConsulta = false;
		String nro_ingreso = "";
		String nro_identificacion = "";
		String nro_factura = "";
		Map parametros = Executions.getCurrent().getArg();
		if(parametros!=null){
			if(parametros.isEmpty()){
				parametros = null;
			}
		}
		if(parametros!=null){
			tipo_medicamento = (String) parametros.get("tipo");
			ocultarConsulta = (Boolean) parametros.get("ocultarConsulta");
			nro_ingreso = (String) parametros.get("nro_ingreso");
			nro_identificacion = (String) parametros.get("nro_identificacion");
			nro_factura = (String) parametros.get("nro_factura");
			
		}
		
		if(parametros!=null){
			cargarDatosModuloFactura(nro_ingreso,nro_identificacion,nro_factura,ocultarConsulta);
		}
		
		
	}
	
	public void deshabilitarComponentes(){
		if(rol_usuario.equals(IRoles.FACTURADOR_CAPS)){
			tbxAutorizacion.setReadonly(true);
			}else{
				tbxAutorizacion.setReadonly(false);
			}
	}
	
	private void cargarDatosModuloFactura(String nro_ingreso,
			String nro_identificacion, String nro_factura,
			boolean ocultarConsulta)throws Exception {
		accionForm(true, "Registrar");
		bandboxPaciente.setDisabled(true);
		btnLimpiarPaciente.setVisible(false);
    	lbxNro_ingreso.setDisabled(true);
    	ocultarBotoConsultar(ocultarConsulta);
    	
    	Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
    	facturacion_medicamento.setCodigo_empresa(empresa.getCodigo_empresa());
    	facturacion_medicamento.setCodigo_sucursal(sucursal.getCodigo_sucursal());
    	facturacion_medicamento.setNro_factura(nro_factura);
    	facturacion_medicamento = getServiceLocator().getFacturacion_medicamentoService().consultar(facturacion_medicamento);
    	if(facturacion_medicamento!=null){
    		mostrarDatos(facturacion_medicamento);
    	}else{
    		Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(empresa.getCodigo_empresa());
            paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            paciente.setNro_identificacion(nro_identificacion);
            paciente = getServiceLocator().getPacienteService().consultar(paciente);
            if(paciente!=null){
            	bandboxPaciente.setValue(paciente.getNro_identificacion());
            	tbxInfoPaciente.setValue(paciente.getNombreCompleto());
            	tbxTipo_identificacion.setValue(paciente.getTipo_identificacion());
            	
            	Admision aux2 = new Admision();
            	aux2.setCodigo_empresa(empresa.getCodigo_empresa());
            	aux2.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            	aux2.setNro_identificacion(nro_identificacion);
            	aux2.setNro_ingreso(nro_ingreso);
            	aux2 = getServiceLocator().getServicio(AdmisionService.class).consultar(aux2);
            	
            	if(aux2!=null){
            		////log.info("aux2: "+aux2);
            		
            		Administradora admin = new Administradora();
        			admin.setCodigo_empresa(aux2.getCodigo_empresa());
        			admin.setCodigo_sucursal(aux2.getCodigo_sucursal());
        			admin.setCodigo(aux2.getCodigo_administradora());
        			admin = getServiceLocator().getAdministradoraService().consultar(
        					admin);

        			lbxNro_ingreso.getItems().clear();
            		Listitem listitem = new Listitem();
            		listitem.setSelected(true);
            		listitem.setLabel(aux2.getNro_ingreso() + " -- "
        					+ (admin != null ? admin.getNombre() : ""));
            		listitem.setValue(aux2);
            		cargarAdmisiones(aux2);
            		lbxNro_ingreso.appendChild(listitem);
            		btnLimpiarPaciente.setVisible(false);
            		
            	}
            }
    	}
    }
	
	private void ocultarBotoConsultar(boolean sw) {
		
		if (!sw) {
			((Button) groupboxEditar.getFellow("btCancel")).setVisible(true);
			((Button) groupboxEditar.getFellow("btNew")).setVisible(true);
		} else {
			((Button) groupboxEditar.getFellow("btCancel")).setVisible(false);
			((Button) groupboxEditar.getFellow("btNew")).setVisible(false);
		}
	}

	private void parametrizarBandbox() {
		bandboxPaciente.inicializar(tbxInfoPaciente, btnLimpiarPaciente);
		bandboxPaciente
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("limite_paginado", 
						"limit 25 offset 0");
						
						return getServiceLocator().getPacienteService().listar(
								parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						List<Admision> listaAdmisiones = Utilidades
								.listarAdmisiones(
										registro.getNro_identificacion(), "",
										false,
										Facturacion_medicamentoAction.this);

						if (listaAdmisiones.isEmpty()) {
							Messagebox
									.show("No se ha registrado el Ingreso del Paciente",
											"Paciente no admisionado",
											Messagebox.OK,
											Messagebox.EXCLAMATION);
							limpiarDatos();
							// deshabilitarCampos(true);
							return false;
						}

						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro
								.getNombreCompleto());
						// imgConsultar_pcd.setVisible(true);

						deshabilitarCampos(false);

						Utilidades.listarIngresos(lbxNro_ingreso,
								listaAdmisiones, false,
								Facturacion_medicamentoAction.this);
						tbxTipo_identificacion.setValue(registro
								.getTipo_identificacion());
						Admision aux2 = (!listaAdmisiones.isEmpty() ? listaAdmisiones
								.get(0) : null);
						admision = aux2;
						cargarAdmisiones(admision);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						limpiarDatos();
						Utilidades.listarIngresos(lbxNro_ingreso,
								new LinkedList<Admision>(), true,
								Facturacion_medicamentoAction.this);
						deshabilitarCampos(true);
					}
				});
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarCentros_costo(lbxArea, true, this);
	}

	public void listarParameter() {

		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("t2.nro_identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("t2.nombre1||' '||t2.nombre2");
		listitem.setLabel("Nombres");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("t2.apellido1||' '||t2.apellido2");
		listitem.setLabel("Apellidos");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_factura");
		listitem.setLabel("Nro registro");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("fecha_medicamento");
		listitem.setLabel("Fecha(aaaa-mm-dd)");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		borderlayoutEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
			deshabilitarCampos(false);
		} else {
			// buscarDatos();
			limpiarDatos();
			bandboxPaciente.setDisabled(false);
			listboxRegistros.getItems().clear();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		bandboxPaciente.setValue("");
		bandboxPaciente.setDisabled(false);
		deshabilitarCampos(true);
	}
	
//	private String getEstado_admision(Facturacion_medicamento facturacion_medicamento)
//		throws Exception {
//		String estado = "1";
//		
//		Admision admision = new Admision();
//		admision.setCodigo_empresa(facturacion_medicamento.getCodigo_empresa());
//		admision.setCodigo_sucursal(facturacion_medicamento.getCodigo_sucursal());
//		admision.setNro_ingreso(facturacion_medicamento.getNro_ingreso());
//		admision.setNro_identificacion(facturacion_medicamento.getNro_identificacion());
//		admision = getServiceLocator().getServicio(AdmisionService.class).consultar(admision);
//		estado = (admision != null ? admision.getEstado() : "1");
//		
//		return estado;
//	}
	
	private void validarRegistroEditar(Facturacion_medicamento facturacion_medicamento)
		throws Exception {
		String estado_admision = Utilidades.getEstado_admision(
				facturacion_medicamento.getNro_ingreso(),
				facturacion_medicamento.getNro_identificacion(), this);
		if (this.getParent() instanceof Facturacion_ripsAction) {
			estado_admision = "1";
		}
		if (estado_admision.equals("2")) {
			bloqueoBotonGuardar(true);
		} else {
			bloqueoBotonGuardar(false);
		}
	}

	private void bloqueoBotonGuardar(boolean sw) {
		
		if (!sw) {
			((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(false);
			((Button) groupboxEditar.getFellow("btGuardar"))
					.setImage("/images/Save16.gif");
		} else {
			((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(true);
			((Button) groupboxEditar.getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");
		}
	}

	public void deshabilitarCampos(boolean sw) {
		FormularioUtil.deshabilitarComponentes(groupboxEditar, sw,
				idsExcluyentes);
		// bloqueoBotonGuardar(sw);
		if (sw) {
			Utilidades.listarIngresos(lbxNro_ingreso,
					new LinkedList<Admision>(), true, this);
		}
		// Grilla //
		// lista_datos.clear();
		// crearFilas();
	}

	public void cargarAdmisiones(Admision aux2) {

		if (aux2 != null) {
			deshabilitarCampos(false);
			dtbxFecha_medicamento.setValue(aux2.getFecha_ingreso());
			tbxId_plan.setValue(aux2.getId_plan());
			tbxCodigo_administradora.setValue(aux2.getCodigo_administradora());
			tbxAutorizacion.setValue(aux2.getNro_autorizacion() != null ? 
					aux2.getNro_autorizacion() : "");
		}
	}

	public void openArticulo() throws Exception {
		
		Map parametros = new HashMap();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("nro_ingreso", lbxNro_ingreso.getSelectedItem()
				.getValue().toString());
		parametros.put("nro_identificacion", bandboxPaciente.getValue());
		parametros.put("grupo", tipo_medicamento);
		parametros.put("ocultaExist", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision);
		parametros.put("seleccionados", medicamentosSeleccionados);

		Component componente = Executions.createComponents(
				"/pages/openArticulo.zul", this, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		if (tipo_medicamento.equals("01"))
			ventana.setTitle("CONSULTAR MEDICAMENTOS");
		else if (tipo_medicamento.equals("02"))
			ventana.setTitle("MATERIAL DE INSUMOS");
		
//		ventana.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {
//
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				if(listboxRegistros.getItemCount() == 5){
//					MensajesUtil.mensajeAlerta("Hay 5 unidades seleccionadas!", 
//							"está segur@ que desea agregar esta cantidad?");
//				}else if(listboxRegistros.getItemCount() > 5){
//					MensajesUtil.mensajeAlerta("Hay mas de 5 unidades seleccionadas!", 
//							"está segur@ que desea agregar esta cantidad?");
//				}
//			}
//		});

		ventana.setMode("modal");
	}

	@Override
	public void adicionarPcd(Map<String, Object> pcd) throws Exception {
		final String codigo_medicamento = pcd.get("codigo_articulo").toString();
		medicamentosSeleccionados.add(codigo_medicamento);
		final Listitem listitem = new Listitem();
		listitem.setValue(codigo_medicamento);

		Listcell listcell = new Listcell();
		Textbox textbox = new Textbox(codigo_medicamento);
		textbox.setInplace(true);
		textbox.setWidth("95%");
		listcell.appendChild(textbox);
		listitem.appendChild(listcell);

		listcell = new Listcell();
		textbox = new Textbox(pcd.get("nombre1").toString());
		textbox.setInplace(true);
		textbox.setWidth("95%");
		listcell.appendChild(textbox);
		listitem.appendChild(listcell);
		
		if(rol_usuario.equals(IRoles.FACTURADOR_CAPS)){
		final Listbox listbox_bodegas = new Listbox();
		listbox_bodegas.setWidth("95%");
		listbox_bodegas.setId("listbox_bodegas_" + codigo_medicamento);
		Utilidades.listarBodegasPorArticulo(codigo_medicamento,
				listbox_bodegas, this);
		listbox_bodegas.setVisible(false);
		listcell = new Listcell();
		listcell.appendChild(listbox_bodegas);
		listitem.appendChild(listcell);
		
		}else {
			final Listbox listbox_bodegas = new Listbox();
			listbox_bodegas.setWidth("95%");
			listbox_bodegas.setMold("select");
			listbox_bodegas.setId("listbox_bodegas_" + codigo_medicamento);
			Utilidades.listarBodegasPorArticulo(codigo_medicamento,
					listbox_bodegas, this);
			listcell = new Listcell();
			listcell.appendChild(listbox_bodegas);
			listitem.appendChild(listcell);
		}			

		final Doublebox doublebox_cantidad = new Doublebox();
		doublebox_cantidad.setId("doublebox_cantidad_" + codigo_medicamento);
		doublebox_cantidad.setWidth("95%");
		doublebox_cantidad.setInplace(true);
		listcell = new Listcell();
		listcell.appendChild(doublebox_cantidad);
		listitem.appendChild(listcell);

		Double valor_unitario = (Double) pcd.get("valor_unitario");
		final Doublebox doublebox_valor = new Doublebox(valor_unitario);
		doublebox_valor.setId("doublebox_valor_" + codigo_medicamento);
		doublebox_valor.setWidth("95%");
		doublebox_valor.setFormat("#,##0.00");
		doublebox_valor.setInplace(true);
		listcell = new Listcell();
		listcell.appendChild(doublebox_valor);
		listitem.appendChild(listcell);

		final Doublebox doublebox_total = new Doublebox();
		doublebox_total.setId("doublebox_total_" + codigo_medicamento);
		doublebox_total.setWidth("95%");
		doublebox_total.setFormat("#,##0.00");
		doublebox_total.setReadonly(true);
		doublebox_total.setInplace(true);
		listcell = new Listcell();
		listcell.appendChild(doublebox_total);
		listitem.appendChild(listcell);

		listitem.setAttribute("valor_total", pcd.get("valor_total"));
		listitem.setAttribute("descuento", pcd.get("descuento"));
		listitem.setAttribute("incremento", pcd.get("incremento"));
		listitem.setAttribute("valor_real", pcd.get("valor_real"));

//		final Image image = new Image("/images/borrar.gif");
//		image.setStyle("cursor:pointer");
//		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//
//			@Override
//			public void onEvent(Event event) throws Exception {
//				medicamentosSeleccionados.remove(codigo_medicamento);
//				listitem.detach();
//			}
//
//		});

		doublebox_cantidad.addEventListener(Events.ON_CHANGE,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						doublebox_total.setValue(calcularValorPorFila(
								doublebox_cantidad, doublebox_valor));
						calcularValorTotal();
					}
				});

		doublebox_cantidad.addEventListener(Events.ON_OK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						doublebox_total.setValue(calcularValorPorFila(
								doublebox_cantidad, doublebox_valor));
						calcularValorTotal();
					}

				});

		doublebox_valor.addEventListener(Events.ON_CHANGE,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						doublebox_total.setValue(calcularValorPorFila(
								doublebox_cantidad, doublebox_valor));
						calcularValorTotal();
					}
				});

		doublebox_valor.addEventListener(Events.ON_OK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						doublebox_total.setValue(calcularValorPorFila(
								doublebox_cantidad, doublebox_valor));
						calcularValorTotal();
					}

				});

		listcell = new Listcell();
//		listcell.appendChild(image);
		listitem.appendChild(listcell);

		listboxRegistros.appendChild(listitem);
	}

	private Double calcularValorPorFila(Doublebox doublebox_cantidad,
			Doublebox doublebox_valor) {
		Double cantidad = doublebox_cantidad.getValue();
		Double valor_unitario = doublebox_valor.getValue();

		if (cantidad != null) {
			if (valor_unitario != null) {
				return cantidad * valor_unitario;
			} else {
				return 0.0;
			}
		} else {
			return 0.0;
		}
	}

	public void calcularValorTotal() {
		Double total = 0.0;
		for (String codigo_articulo : medicamentosSeleccionados) {
			if (this.hasFellow("doublebox_total_" + codigo_articulo)) {
				Doublebox doublebox_total = (Doublebox) this
						.getFellow("doublebox_total_" + codigo_articulo);
				total += doublebox_total.getValue() != null ? doublebox_total
						.getValue() : 0.0;
			}
		}
		dbxTotal.setValue(total);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		tbxTipo_identificacion
				.setStyle("text-transform:uppercase;background-color:white");
		bandboxPaciente
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_administradora
				.setStyle("text-transform:uppercase;background-color:white");
		tbxId_plan.setStyle("text-transform:uppercase;background-color:white");
		lbxNro_ingreso
				.setStyle("text-transform:uppercase;background-color:white");
		dtbxFecha_medicamento.setStyle("background-color:white");

		boolean valida = true;
		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxTipo_identificacion.getText().trim().equals("")) {
			tbxTipo_identificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (bandboxPaciente.getText().trim().equals("")) {
			bandboxPaciente
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxCodigo_administradora.getText().trim().equals("")) {
			tbxCodigo_administradora
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxId_plan.getText().trim().equals("")) {
			tbxId_plan
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (lbxNro_ingreso.getSelectedItem().getValue() == null) {
			lbxNro_ingreso
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (dtbxFecha_medicamento.getText().trim().equals("")) {
			dtbxFecha_medicamento.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (valida) {
			if (listboxRegistros.getItemCount() == 0) {
				mensaje = "Debe agregar "
						+ (tipo_medicamento.equals("01") ? "Medicamentos"
								: "Materiales de Insumos")
						+ " para hacer la facturacion";
				valida = false;
			}
		}	

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public boolean validarCantidadesCero() {
		for (String codigo_articulo : medicamentosSeleccionados) {
			if (this.hasFellow("doublebox_cantidad_" + codigo_articulo)) {
				Doublebox doublebox_cantidad = (Doublebox) this
						.getFellow("doublebox_cantidad_" + codigo_articulo);
				if (doublebox_cantidad.getValue() == null) {
					return false;
				} else{
					if (doublebox_cantidad.getValue().intValue() == 0) {
						return false;
					}
				} 
			}
		}
		return true;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("tipo",tipo_medicamento);

			getServiceLocator().getFacturacion_medicamentoService().setLimit(
					"limit 25 offset 0");

			List<Facturacion_medicamento> lista_datos = getServiceLocator()
					.getFacturacion_medicamentoService().listar(parameters);
			rowsResultado.getChildren().clear();
			//log.info("======> medicamentosss" + lista_datos);

			for (Facturacion_medicamento facturacion_medicamento : lista_datos) {
				rowsResultado.appendChild(crearFilas(facturacion_medicamento,
						this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Facturacion_medicamento facturacion_medicamento = (Facturacion_medicamento) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(facturacion_medicamento.getNro_factura()
				+ ""));
		fila.appendChild(new Label(facturacion_medicamento
				.getTipo_identificacion() + ""));
		fila.appendChild(new Label(facturacion_medicamento
				.getNro_identificacion() + ""));

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(facturacion_medicamento.getCodigo_empresa());
		paciente.setCodigo_sucursal(facturacion_medicamento
				.getCodigo_sucursal());
		paciente.setNro_identificacion(facturacion_medicamento
				.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		String nombres_paciente = (paciente != null ? paciente.getNombre1()
				+ " " + paciente.getNombre2() : "");
		String apellidos_paciente = (paciente != null ? paciente.getApellido1()
				+ " " + paciente.getApellido2() : "");

		fila.appendChild(new Label(apellidos_paciente));
		fila.appendChild(new Label(nombres_paciente));

		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(facturacion_medicamento.getFecha_medicamento()) + ""));

		String centro_costo = "";
		if (facturacion_medicamento.getC_costo() != null
				&& !facturacion_medicamento.getC_costo().isEmpty()) {
			Centro_costo c_costo = new Centro_costo();
			c_costo.setCodigo_empresa(empresa.getCodigo_empresa());
			c_costo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			c_costo.setCodigo(facturacion_medicamento.getC_costo());
			c_costo = getServiceLocator().getCentro_costoService().consultar(
					c_costo);
			if (c_costo != null) {
				centro_costo = c_costo.getNombre();
			}
		}

		fila.appendChild(new Label(centro_costo));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(facturacion_medicamento);
			}
		});
		hbox.appendChild(img);

//		img = new Image();
//		img.setSrc("/images/borrar.gif");
//		img.setTooltiptext("Eliminar");
//		img.setStyle("cursor: pointer");
//		String estado_admision = getEstado_admision(facturacion_medicamento);
//		if (estado_admision.equals("2")) {
//			img.setVisible(false);
//		} else {
//			img.setVisible(true);
//		}
//		img.addEventListener("onClick", new EventListener() {
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				Messagebox.show(
//						"Esta seguro que desea eliminar este registro? ",
//						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
//						Messagebox.QUESTION,
//						new org.zkoss.zk.ui.event.EventListener() {
//							public void onEvent(Event event) throws Exception {
//								if ("onYes".equals(event.getName())) {
//									// do the thing
//									eliminarDatos(facturacion_medicamento);
//									buscarDatos();
//								}
//							}
//						});
//			}
//		});
//		hbox.appendChild(space);
//		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				if (validarCantidadesCero()) {

					Admision admision = (Admision) lbxNro_ingreso
							.getSelectedItem().getValue();

					// Cargamos los componentes //
					Facturacion_medicamento facturacion_medicamento = new Facturacion_medicamento();
					facturacion_medicamento.setCodigo_empresa(empresa
							.getCodigo_empresa());
					facturacion_medicamento.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					facturacion_medicamento.setNro_factura(tbxNro_factura
							.getValue());
					facturacion_medicamento
							.setTipo_identificacion(tbxTipo_identificacion
									.getValue());
					facturacion_medicamento
							.setNro_identificacion(bandboxPaciente.getValue());
					facturacion_medicamento
							.setCodigo_administradora(tbxCodigo_administradora
									.getValue());
					facturacion_medicamento.setId_plan(tbxId_plan.getValue());
					facturacion_medicamento.setNro_ingreso(admision
							.getNro_ingreso());
					facturacion_medicamento.setFecha_medicamento(new Timestamp(
							dtbxFecha_medicamento.getValue().getTime()));
					facturacion_medicamento
							.setNumero_autorizacion(tbxAutorizacion.getValue());
					facturacion_medicamento.setObservacion(tbxObservacion
							.getValue());
					facturacion_medicamento.setTipo(tipo_medicamento);
			
					facturacion_medicamento.setCreacion_date(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					facturacion_medicamento.setUltimo_update(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					facturacion_medicamento.setDelete_date(new Timestamp(
							(new Date()).getTime()));
					facturacion_medicamento.setCreacion_user(usuarios
							.getCodigo().toString());
					facturacion_medicamento.setUltimo_user(usuarios.getCodigo()
							.toString());
					facturacion_medicamento
							.setDelete_user(usuarios.getCodigo());
					facturacion_medicamento
							.setCodigo_solicitud(tbxCodigo_solicitud.getValue());
					facturacion_medicamento.setCodigo_receta(tbxCodigo_receta
							.getValue());
					facturacion_medicamento.setC_costo(lbxArea
							.getSelectedItem().getValue().toString());

					List<Datos_medicamentos> lista_medicamentos = new ArrayList<Datos_medicamentos>();

					for (Listitem listitem : listboxRegistros.getItems()) {
						String codigo_medicamento = listitem.getValue()
								.toString();
						Listbox listbox_bodegas = (Listbox) listboxRegistros
								.getFellow("listbox_bodegas_"
										+ codigo_medicamento);
						Doublebox doublebox_cantidad = (Doublebox) listboxRegistros
								.getFellow("doublebox_cantidad_"
										+ codigo_medicamento);
						Doublebox doublebox_valor = (Doublebox) listboxRegistros
								.getFellow("doublebox_valor_"
										+ codigo_medicamento);
						Doublebox doublebox_total = (Doublebox) listboxRegistros
								.getFellow("doublebox_total_"
										+ codigo_medicamento);

						Double descuento = (Double) listitem
								.getAttribute("descuento");
						Double incremento = (Double) listitem
								.getAttribute("incremento");
						Double valor_real = (Double) listitem
								.getAttribute("valor_real");
						
						Articulo articulo = new Articulo();
						articulo.setCodigo_empresa(codigo_empresa);
						articulo.setCodigo_sucursal(codigo_sucursal); 
						articulo.setCodigo_articulo(codigo_medicamento);
						articulo = getServiceLocator().getServicio(ArticuloService.class).consultar(articulo);

						Datos_medicamentos datos_medicamentos = new Datos_medicamentos();
						datos_medicamentos
								.setCodigo_medicamento(codigo_medicamento);
						datos_medicamentos.setCodigo_empresa(empresa
								.getCodigo_empresa());
						datos_medicamentos.setCodigo_sucursal(sucursal
								.getCodigo_sucursal());
						datos_medicamentos
								.setCantidad_entregada(doublebox_cantidad
										.getValue().intValue());
						if(doublebox_cantidad.getValue() == 5){					
							MensajesUtil.mensajeAlerta("Hay 5 unidades seleccionadas para el articulo "+articulo.getCodigo_articulo()+"! ", 
									"está segur@ que desea agregar esta cantidad?");
						}else if (doublebox_cantidad.getValue() > 5){
							MensajesUtil.mensajeAlerta("Hay  "+ doublebox_cantidad.getValue().intValue() +"  unidades seleccionadas "
									+ "para el articulo "+articulo.getCodigo_articulo()+" !", 
									"está segur@ que desea agregar esta cantidad?");
						}
						datos_medicamentos.setReferencia_pyp(articulo != null ? articulo.getReferencia() : "");  
						
						datos_medicamentos.setCodigo_bodega(listbox_bodegas
								.getSelectedItem().getValue().toString());
						datos_medicamentos.setCodigo_lote("");
						datos_medicamentos.setCancelo_copago("");
						datos_medicamentos.setConsecutivo("");
						datos_medicamentos.setCreacion_date(new Timestamp(
								(new Date()).getTime()));
						datos_medicamentos.setCreacion_user(usuarios
								.getCodigo());
						datos_medicamentos.setDescuento(descuento);
						datos_medicamentos.setIncremento(incremento);
						datos_medicamentos.setNro_factura("");
						datos_medicamentos.setUltimo_update(new Timestamp(
								(new Date()).getTime()));
						datos_medicamentos.setUltimo_user(usuarios.getCodigo());
						datos_medicamentos.setUnidades(doublebox_cantidad
								.getValue().intValue());
						datos_medicamentos.setValor_real(valor_real);
						datos_medicamentos.setValor_total(doublebox_total
								.getValue());
						datos_medicamentos.setValor_unitario(doublebox_valor
								.getValue());					
						

						lista_medicamentos.add(datos_medicamentos);
						//log.info("=====> medicamentos " + lista_medicamentos);
						
					}

					boolean cont = false;
					boolean afectar_kardex_fact = true;
					contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
					res.setCodigo_empresa(facturacion_medicamento
							.getCodigo_empresa());
					res = (contaweb.modelo.bean.Resolucion) getServiceLocator()
							.getResolucion_contService().consultar(res);
					if (res != null) {
						if (res.getContabiliza_aut()) {
							cont = true;
						}
					}

					healthmanager.modelo.bean.Resolucion res2 = new healthmanager.modelo.bean.Resolucion();
					res2.setCodigo_empresa(codigo_empresa);
					res2 = (healthmanager.modelo.bean.Resolucion) getServiceLocator()
							.getResolucionService().consultar(res2);
					if (res2 != null) {
						afectar_kardex_fact = res2.getAfectar_kardex_fact();
					}

					Map<String, Object> datos_guardar = new HashMap<String, Object>();
					datos_guardar.put("lista_medicamentos", lista_medicamentos);
					datos_guardar.put("facturacion_medicamento",
							facturacion_medicamento);
					datos_guardar.put("accion", tbxAccion.getValue());
					datos_guardar.put("cont", cont);
					datos_guardar.put("afectar_kardex_fact",
							afectar_kardex_fact);

					getServiceLocator().getFacturacion_medicamentoService()
							.guardarFacturacion(datos_guardar, true);

					tbxAccion.setValue("modificar");
					tbxNro_factura.setValue(facturacion_medicamento
							.getNro_factura());					

					Messagebox.show(
							"Los datos se guardaron satisfactoriamente",
							"Informacion ..", Messagebox.OK,
							Messagebox.INFORMATION);
					
					if(this.getParent() instanceof Facturacion_ripsAction){
						Facturacion_ripsAction action = (Facturacion_ripsAction) this
								.getParent();
						action.consultarServiciosFacturas(true);
						this.detach();
					}

				} else {
					MensajesUtil
							.mensajeAlerta("Hay cantidades 0",
									"NO pueden existir cantidades cero en los medicamentos/materiales de insumos");
				}
			}

		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage()); 
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Facturacion_medicamento facturacion_medicamento = (Facturacion_medicamento) obj;
		try {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(facturacion_medicamento
					.getCodigo_empresa());
			paciente.setCodigo_sucursal(facturacion_medicamento
					.getCodigo_sucursal());
			paciente.setNro_identificacion(facturacion_medicamento
					.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);
			String nombres_paciente = (paciente != null ? paciente.getNombre1()
					+ " " + paciente.getNombre2() : "");
			String apellidos_paciente = (paciente != null ? paciente
					.getApellido1() + " " + paciente.getApellido2() : "");

			bandboxPaciente.seleccionarRegistro(paciente,
					facturacion_medicamento.getNro_identificacion(),
					nombres_paciente + " " + apellidos_paciente);
			bandboxPaciente.setDisabled(true);
			btnLimpiarPaciente.setVisible(false);

			Utilidades.listarIngresos(lbxNro_ingreso, Utilidades
					.listarAdmisiones(
							facturacion_medicamento.getNro_identificacion(),
							facturacion_medicamento.getNro_ingreso(), true,
							this), false, this);

			Utilidades.seleccionarListitem(lbxNro_ingreso,
					facturacion_medicamento.getNro_ingreso());

			tbxNro_factura.setValue(facturacion_medicamento.getNro_factura());
			tbxTipo_identificacion.setValue(facturacion_medicamento
					.getTipo_identificacion());

			tbxCodigo_administradora.setValue(facturacion_medicamento
					.getCodigo_administradora());
			tbxId_plan.setValue(facturacion_medicamento.getId_plan());

			dtbxFecha_medicamento.setValue(facturacion_medicamento
					.getFecha_medicamento());
			tbxAutorizacion.setValue(facturacion_medicamento
					.getNumero_autorizacion());
			tbxObservacion.setValue(facturacion_medicamento.getObservacion());
			tbxTipo.setValue(facturacion_medicamento.getTipo());

			tbxCodigo_solicitud.setValue(facturacion_medicamento
					.getCodigo_solicitud());
			tbxCodigo_receta.setValue(facturacion_medicamento
					.getCodigo_receta());

			Utilidades.seleccionarListitem(lbxArea,
					facturacion_medicamento.getC_costo());

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa",
					facturacion_medicamento.getCodigo_empresa());
			parametros.put("codigo_sucursal",
					facturacion_medicamento.getCodigo_sucursal());
			parametros.put("nro_factura",
					facturacion_medicamento.getNro_factura());

			List<Datos_medicamentos> lista_medicamentos = getServiceLocator()
					.getDatos_medicamentosService().listar(parametros);

			listboxRegistros.getItems().clear();
			for (Datos_medicamentos datos_medicamentos : lista_medicamentos) {
				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(datos_medicamentos
						.getCodigo_empresa());
				articulo.setCodigo_sucursal(datos_medicamentos
						.getCodigo_sucursal());
				articulo.setCodigo_articulo(datos_medicamentos
						.getCodigo_medicamento());

				articulo = getServiceLocator().getArticuloService().consultar(
						articulo);

				final String codigo_medicamento = datos_medicamentos
						.getCodigo_medicamento();
				medicamentosSeleccionados.add(codigo_medicamento);
				final Listitem listitem = new Listitem();
				listitem.setValue(codigo_medicamento);

				Listcell listcell = new Listcell();
				Textbox textbox = new Textbox(codigo_medicamento);
				textbox.setInplace(true);
				textbox.setWidth("95%");
				listcell.appendChild(textbox);
				listitem.appendChild(listcell);

				listcell = new Listcell();
				textbox = new Textbox(articulo != null ? articulo.getNombre1()
						: "");
				textbox.setInplace(true);
				textbox.setWidth("95%");
				listcell.appendChild(textbox);
				listitem.appendChild(listcell);
				
				
				if(rol_usuario.equals(IRoles.FACTURADOR_CAPS)){
					final Listbox listbox_bodegas = new Listbox();
					listbox_bodegas.setWidth("95%");
					listbox_bodegas.setId("listbox_bodegas_" + codigo_medicamento);
					Utilidades.listarBodegasPorArticulo(codigo_medicamento,
							listbox_bodegas, this);
					Utilidades.seleccionarListitem(listbox_bodegas,
							datos_medicamentos.getCodigo_bodega());
					listbox_bodegas.setVisible(false);
					listcell = new Listcell();
					listcell.appendChild(listbox_bodegas);
					listitem.appendChild(listcell);
					
					}else {
						final Listbox listbox_bodegas = new Listbox();
						listbox_bodegas.setWidth("95%");
						listbox_bodegas.setMold("select");
						listbox_bodegas.setId("listbox_bodegas_" + codigo_medicamento);
						Utilidades.listarBodegasPorArticulo(codigo_medicamento,
								listbox_bodegas, this);
						Utilidades.seleccionarListitem(listbox_bodegas,
								datos_medicamentos.getCodigo_bodega());
						listcell = new Listcell();
						listcell.appendChild(listbox_bodegas);
						listitem.appendChild(listcell);
					}	

				final Doublebox doublebox_cantidad = new Doublebox(
						datos_medicamentos.getCantidad_entregada());
				doublebox_cantidad.setId("doublebox_cantidad_"
						+ codigo_medicamento);
				doublebox_cantidad.setWidth("95%");
				doublebox_cantidad.setInplace(true);
				listcell = new Listcell();
				listcell.appendChild(doublebox_cantidad);
				listitem.appendChild(listcell);

				Double valor_unitario = datos_medicamentos.getValor_unitario();
				final Doublebox doublebox_valor = new Doublebox(valor_unitario);
				doublebox_valor.setId("doublebox_valor_" + codigo_medicamento);
				doublebox_valor.setWidth("95%");
				doublebox_valor.setFormat("#,##0.00");
				doublebox_valor.setInplace(true);
				listcell = new Listcell();
				listcell.appendChild(doublebox_valor);
				listitem.appendChild(listcell);

				final Doublebox doublebox_total = new Doublebox(
						calcularValorPorFila(doublebox_cantidad,
								doublebox_valor));
				doublebox_total.setId("doublebox_total_" + codigo_medicamento);
				doublebox_total.setWidth("95%");
				doublebox_total.setFormat("#,##0.00");
				doublebox_total.setReadonly(true);
				doublebox_total.setInplace(true);
				listcell = new Listcell();
				listcell.appendChild(doublebox_total);
				listitem.appendChild(listcell);

				listitem.setAttribute("valor_total",
						datos_medicamentos.getValor_total());
				listitem.setAttribute("descuento",
						datos_medicamentos.getDescuento());
				listitem.setAttribute("incremento",
						datos_medicamentos.getIncremento());
				listitem.setAttribute("valor_real",
						datos_medicamentos.getValor_real());

				final Image image = new Image("/images/borrar.gif");
				image.setStyle("cursor:pointer");
				image.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								medicamentosSeleccionados
										.remove(codigo_medicamento);
								listitem.detach();
							}

						});

				doublebox_cantidad.addEventListener(Events.ON_CHANGE,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								doublebox_total.setValue(calcularValorPorFila(
										doublebox_cantidad, doublebox_valor));
								calcularValorTotal();
							}
						});

				doublebox_cantidad.addEventListener(Events.ON_OK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								doublebox_total.setValue(calcularValorPorFila(
										doublebox_cantidad, doublebox_valor));
								calcularValorTotal();
							}

						});

				doublebox_valor.addEventListener(Events.ON_CHANGE,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								doublebox_total.setValue(calcularValorPorFila(
										doublebox_cantidad, doublebox_valor));
								calcularValorTotal();
							}
						});

				doublebox_valor.addEventListener(Events.ON_OK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								doublebox_total.setValue(calcularValorPorFila(
										doublebox_cantidad, doublebox_valor));
								calcularValorTotal();
							}

						});

				listcell = new Listcell();
				listcell.appendChild(image);
				listitem.appendChild(listcell);

				listboxRegistros.appendChild(listitem);
			}
			calcularValorTotal();
			
			validarRegistroEditar(facturacion_medicamento);
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Facturacion_medicamento facturacion_medicamento = (Facturacion_medicamento) obj;
		try {
			boolean cont = false;
			boolean afectar_kardex_fact = true;
			contaweb.modelo.bean.Resolucion res = new contaweb.modelo.bean.Resolucion();
			res.setCodigo_empresa(facturacion_medicamento.getCodigo_empresa());
			res = (contaweb.modelo.bean.Resolucion) getServiceLocator()
					.getResolucion_contService().consultar(res);
			if (res != null) {
				if (res.getContabiliza_aut()) {
					cont = true;
				}
			}

			healthmanager.modelo.bean.Resolucion res2 = new healthmanager.modelo.bean.Resolucion();
			res2.setCodigo_empresa(codigo_empresa);
			res2 = (healthmanager.modelo.bean.Resolucion) getServiceLocator()
					.getResolucionService().consultar(res2);
			if (res2 != null) {
				afectar_kardex_fact = res2.getAfectar_kardex_fact();
			}

			Map<String, Object> datos = new HashMap<String, Object>();
			datos.put("facturacion_medicamento", facturacion_medicamento);
			datos.put("cont", cont);
			datos.put("afectar_kardex_fact", afectar_kardex_fact);

			int result = getServiceLocator()
					.getFacturacion_medicamentoService().eliminar(datos, true);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	public String getTipo_medicamento() {
		return tipo_medicamento;
	}

	public void setTipo_medicamento(String tipo_medicamento) {
		this.tipo_medicamento = tipo_medicamento;
	}
}