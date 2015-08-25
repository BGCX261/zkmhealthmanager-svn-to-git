package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Anio_cuota_moderadora;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Porcentaje_adicional_medicamento;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdmisionService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.res.LabelAlign;
import com.framework.res.LabelAlign.AlignText;
import com.framework.res.LabelState;
import com.framework.res.ResCalculadorDeRangoCuota;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Caja;
import contaweb.modelo.bean.Detalle_caja;
import contaweb.modelo.bean.Detalle_pagare;
import contaweb.modelo.bean.Pagare;

public class FarmaciaRecetasRipsAction extends ZKWindow {

	private static Logger log = Logger
			.getLogger(FarmaciaRecetasRipsAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private Window form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;

	@View
	Listbox lbxSearh;
	@View
	private Timer timer;
	private boolean auditor;
	private boolean print;
	private boolean pagoCuota;

	@View
	private Datebox dtxFecha;
	
	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	private void verificamosTipeImpresion() {
		if (parametros_empresa != null) {
			if (parametros_empresa.getPrint_receta_caja().equals("S")) {
				print = true;
			}
		}
	}

	public void initTime() {
		//log.info("timer accion");
		timer.addEventListener("onTimer", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				buscarDatos();
			}
		});
		timer.start();
		//log.info("timer stared");
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formAnexo3_negacion");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		// groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		auditor = ((request.getParameter("auditor") != null ? request.getParameter("auditor") : "") + "").equals("s");
		pagoCuota = ((request.getParameter("pagoCuota") != null ? request.getParameter("pagoCuota") : "") + "").equals("s");
		//log.info("pagoCuota: " + request.getParameter("pagoCuota"));
		//log.info("pagoCuota: " + pagoCuota);
	}	

	public void listarCombos() {
		listarParameter();
		listarTipoBusqueda();
		parametrizarResultadoPaginado();
	}

	private void listarTipoBusqueda() {
		lbxSearh.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue(null);
		listitem.setLabel("Todos");
		lbxSearh.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue(auditor ? "N" : false);
		listitem.setLabel("No leidos");
		lbxSearh.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue(auditor ? "S" : true);
		listitem.setLabel("Leidos");
		lbxSearh.appendChild(listitem);

		if (lbxSearh.getItemCount() > 0) {
			lbxSearh.setSelectedIndex(0);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_receta");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Nro id paciente");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_prestador");
		listitem.setLabel("Nro codigo Medico");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent)
							.setText(((Textbox) abstractComponent).getText()
									.trim().toUpperCase());
				}
			}
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals(
						"lbxParameter")) {
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setSelectedIndex(0);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setText("0.00");
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setText("");
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setValue(new Date());
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setChecked(false);
			}
			if (abstractComponent instanceof Radiogroup) {
				((Radio) ((Radiogroup) abstractComponent).getFirstChild())
						.setChecked(true);
			}
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("fecha", new SimpleDateFormat("yyyy-MM-dd")
					.format(dtxFecha.getValue().getTime()));

			if (lbxSearh.getSelectedItem().getValue() != null) {
				parameters.put(auditor ? "leido_auditor" : "leido", lbxSearh
						.getSelectedItem().getValue());
			}

			if (auditor) {
				parameters.put("contiene_med_no_autorizados", "S");
			}

			List<Receta_rips> lista_datos = getServiceLocator()
					.getReceta_ripsService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Receta_rips recetaRips : lista_datos) {
				rowsResultado.appendChild(crearFilas(recetaRips, this));
			}
			// esto es para que esta actualizando la grilla.
			 gridResultado.setVisible(true);
			 gridResultado.setMold("paging");
			 gridResultado.setPageSize(20);
			
			 gridResultado.applyProperties();
			 gridResultado.invalidate();
			 gridResultado.setVisible(true);
			
			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		final Row fila = new Row();

		final Receta_rips recetaRips = (Receta_rips) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		String codigo_paciente = recetaRips != null ? recetaRips
				.getNro_identificacion() : "";

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(sucursal.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(codigo_paciente);
		paciente = getServiceLocator().getPacienteService().consultar(paciente);

		String nombre = "";
		if (paciente != null) {
			nombre = paciente.getNombre1() + " " + paciente.getNombre2() + " "
					+ paciente.getApellido1() + " " + paciente.getApellido2();
		}

		Usuarios usuariosMed = new Usuarios();
		usuariosMed.setCodigo_empresa(sucursal.getCodigo_empresa());
		usuariosMed.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		usuariosMed.setCodigo(recetaRips.getCodigo_prestador());
		usuariosMed = getServiceLocator().getUsuariosService().consultar(
				usuariosMed);

		String nombreMed = "";
		if (usuariosMed != null) {
			nombreMed = usuariosMed.getNombres() + " "
					+ usuariosMed.getApellidos();
		} else {
			nombreMed = recetaRips.getNombre_medico();
		}

		boolean leido = auditor ? (recetaRips.getLeido_auditor() + "")
				.equals("N") : !recetaRips.getLeido();

		fila.setStyle("text-align: justify;nowrap:nowrap;");
		fila.appendChild(new LabelState(recetaRips.getCodigo_receta(), leido));
		fila.appendChild(new LabelState(new SimpleDateFormat("yyyy-MM-dd")
				.format(recetaRips.getFecha()), leido));
		fila.appendChild(new LabelState(recetaRips.getNro_identificacion(),
				leido));
		fila.appendChild(new LabelState(nombre, leido));
		fila.appendChild(new LabelState(recetaRips.getCodigo_prestador(), leido));
		fila.appendChild(new LabelState(nombreMed, leido));
		fila.appendChild(new LabelState(recetaRips.getTipo_receta().equals(
				IConstantes.TIPO_RECETA_AMBULATORIA) ? "AMBULATORIA"
				: "URGENCIAS", leido));
		fila.appendChild(new LabelAlign(
				(recetaRips.getLista_detalle() != null ? recetaRips
						.getLista_detalle().size() : 0) + "", AlignText.RIGHT,
				leido));
		// fila.appendChild(new
		// LabelState(anexo3Entidad.getLeido().equalsIgnoreCase("s") ? "Impreso"
		// : "Nuevo", leido));

		hbox.appendChild(space);

		Caja caja = new Caja(false);
		caja.setCodigo_empresa(recetaRips.getCodigo_empresa());
		caja.setCodigo_sucursal(recetaRips.getCodigo_sucursal());
		caja.setCodigo_orden(recetaRips.getCodigo_receta());
		caja.setCopago_autorizaciones("N");
		caja.setCopago_medicamentos("S");
		final Caja cajaEnd = getServiceLocator().getCajaService().consultar(
				caja);

		final boolean pagaCopago = pagaCopago(recetaRips.getLista_detalle(),
				paciente);

		Image imgCopago = new Image();
		imgCopago.setSrc("/images/caja.jpg");
		imgCopago.setTooltiptext("Generar copago");
		imgCopago.setStyle("cursor: pointer");
		imgCopago.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if((recetaRips.getAuditado_farmacia()+"").equals("S")){
					generarCopago(recetaRips, cajaEnd);
					cambiarEstado(recetaRips);
				}else{
					MensajesUtil.mensajeAlerta("Advertencia", "Para realizar esta accion farmacia debe aprobar la entrega de los medicamentos, por favor dirigirse a farmacia");  
				}
			}
		});

		boolean permitido_cobrar_copago = false;
		if (parametros_empresa != null) {
			if (parametros_empresa.getEntrega_r_caja().equals("01")) {
				permitido_cobrar_copago = true;
			}
		}
		if (pagaCopago && !auditor && permitido_cobrar_copago)
			hbox.appendChild(imgCopago);

		Image img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setTooltiptext("Imprimir");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				imprimir(recetaRips.getCodigo_receta());
				cambiarEstado(recetaRips);
			}
		});
		if (print && !auditor && !pagoCuota)
			hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/boton_restaurar.gif");
		img.setTooltiptext("Medicamentos");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if (!auditor) {
					cargarMedicamentos(recetaRips,
							(pagaCopago && cajaEnd == null));
				} else {
					loadSolicitudes(recetaRips);
				}
				cambiarEstado(recetaRips);
			}
		});
		hbox.appendChild(space);
		if (!pagoCuota)
			hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	protected void loadSolicitudes(Receta_rips recetaRips) throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("receta_rips", recetaRips);
		paramRequest.put("AutoZs", true);

		Component componente = Executions.createComponents(
				"/pages/solicitud_tecnico.zul", form, paramRequest);
		final Window window = (Window) componente;
		window.setWidth("100%");
		window.setHeight("100%");
		window.doModal();
	}

	private boolean pagaCopago(List<Detalle_receta_rips> listaDetalle,
			Paciente paciente) {
		for (Detalle_receta_rips detalleRecetaRips : listaDetalle) {
			if (detalleRecetaRips.getEstado_cobro().equals("01")
					&& !paciente.getTipo_usuario().equals(
							IConstantes.REGIMEN_SUBCIDIADO)) {
				return true;
			}
		}
		return false;
	}

	/* nueva actualizacion de generar copago */
	public void generarCopago(Receta_rips recetaRips, Caja caja)
			throws Exception {
		try {
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(recetaRips.getCodigo_empresa());
			paciente.setCodigo_sucursal(recetaRips.getCodigo_sucursal());
			paciente.setNro_identificacion(recetaRips.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);
			// String grupo = (paciente != null ? paciente.getGrupo_cuota() :
			// "");

			Admision admision = new Admision();
			admision.setCodigo_empresa(recetaRips.getCodigo_empresa());
			admision.setCodigo_sucursal(recetaRips.getCodigo_sucursal());
			admision.setNro_identificacion(recetaRips.getNro_identificacion());
			admision.setNro_ingreso(recetaRips.getNro_ingreso());
			admision = getServiceLocator().getServicio(AdmisionService.class).consultar(admision);
			
			String anioo = new SimpleDateFormat("yyyy").format(new Date());

			Anio_cuota_moderadora anio_cuota_moderadora = ResCalculadorDeRangoCuota
					.getGrupo(paciente, getServiceLocator(), anioo, getSucursal());
			// double copago = (anio_cuota_moderadora != null ?
			// anio_cuota_moderadora.getPorcentaje_copago() : 0);
			double cuota_moderadora = (anio_cuota_moderadora != null ? anio_cuota_moderadora
					.getValor_cuota() : 0);

			Map parametros = new HashMap();
			parametros.put("caja", caja);
			parametros.put("paciente", paciente);
			parametros.put("codigo_orden", recetaRips.getCodigo_receta());
			parametros.put("copago_autorizaciones", "N");
			parametros.put("copago_medicamentos", "S");
			parametros.put("cuota_moderadora", cuota_moderadora);
			parametros.put("codigo_orden", recetaRips.getCodigo_receta());
//			parametros.put("tipo", TIPO_RECIBO_CAJA.CUOTA_MODERADORA);
			// para que cobre con respecto al tipo

			if (caja == null) {
				List<Detalle_caja> lista_detalle = new LinkedList<Detalle_caja>();
				for (Detalle_receta_rips detalleRecetaRips : recetaRips
						.getLista_detalle()) {

					Articulo articulo = new Articulo();
					articulo.setCodigo_empresa(sucursal.getCodigo_empresa());
					articulo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					articulo.setCodigo_articulo(detalleRecetaRips
							.getCodigo_articulo());
					articulo = getServiceLocator().getArticuloService()
							.consultar(articulo);

					/* cargamos porcentaje adicional por paciente */
					Porcentaje_adicional_medicamento porcentaje_adicional_medicamento = new Porcentaje_adicional_medicamento();
					porcentaje_adicional_medicamento
							.setCodigo_empresa(codigo_empresa);
					porcentaje_adicional_medicamento
							.setCodigo_sucursal(codigo_sucursal);
					porcentaje_adicional_medicamento
							.setCodigo_med(detalleRecetaRips
									.getCodigo_articulo());
					porcentaje_adicional_medicamento
							.setNro_identificacion(recetaRips
									.getNro_identificacion());
					porcentaje_adicional_medicamento = getServiceLocator()
							.getPorcentaje_adicional_medicamentoService()
							.consultar(porcentaje_adicional_medicamento);

					double porcentaje_adicional = 0d;
					if (porcentaje_adicional_medicamento != null) {
						porcentaje_adicional = porcentaje_adicional_medicamento
								.getPorcentaje();
					}
					/* fin cargar valor adicional por paciente */

					Detalle_caja detalle = new Detalle_caja();
					detalle.setCodigo_empresa(empresa.getCodigo_empresa());
					detalle.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					detalle.setCodigo_articulo(detalleRecetaRips
							.getCodigo_articulo());
					detalle.setDetalle(articulo != null ? articulo.getNombre1()
							: "");
					detalle.setCantidad(detalleRecetaRips.getCantidad_recetada());
					detalle.setValor_unitario(articulo != null ? articulo
							.getPrecio1() : 0d);
					detalle.setValor_total((detalle.getValor_unitario() * detalle
							.getCantidad()));
					detalle.setValor_adicional(porcentaje_adicional); 

					//log.info("::::::::::::::::::::::::::");
					//log.info("Cantidad Recetada: "
					//+ detalle.getCantidad());
					//log.info("Valor adicional: "
					//+ detalle.getValor_unitario());
					//log.info("Valor total: "
					//+ detalle.getValor_total());
					//log.info("::::::::::::::::::::::::::");

					detalle.setCopago(cuota_moderadora);
					lista_detalle.add(detalle);
				}

				parametros.put("lista_detalle", lista_detalle);
			}
			Map<String,Object> mapPagare =getPagare(recetaRips);
			parametros.put("mapPagare", mapPagare);

			Component componente = Executions.createComponents(
					"/pages/caja_cuota_moderadora.zul", form, parametros);
			Window win = (Window) componente;
			win.setWidth("100%");
			win.setHeight("100%");
			win.setMode("modal");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}
	
	public Map<String,Object> getPagare(Receta_rips recetaRips)throws Exception{
		
		Map<String,Object> mapPagare = new HashMap<String,Object>();
		
		Pagare pagare = new Pagare(false);
		pagare.setCodigo_empresa(recetaRips.getCodigo_empresa());
		pagare.setCodigo_sucursal(recetaRips.getCodigo_sucursal());
		pagare.setCodigo_orden(recetaRips.getCodigo_receta());
		pagare.setCopago_autorizaciones("N");
		pagare.setCopago_medicamentos("S");
		pagare = getServiceLocator().getPagareService().consultarAdmision(
				pagare);
		
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(recetaRips.getCodigo_empresa());
		paciente.setCodigo_sucursal(recetaRips.getCodigo_sucursal());
		paciente.setNro_identificacion(recetaRips.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(
				paciente);
		// String grupo = (paciente != null ? paciente.getGrupo_cuota() :
		// "");

		Admision admision = new Admision();
		admision.setCodigo_empresa(recetaRips.getCodigo_empresa());
		admision.setCodigo_sucursal(recetaRips.getCodigo_sucursal());
		admision.setNro_identificacion(recetaRips.getNro_identificacion());
		admision.setNro_ingreso(recetaRips.getNro_ingreso());
		admision = getServiceLocator().getServicio(AdmisionService.class).consultar(admision);
		
		String anioo = new SimpleDateFormat("yyyy").format(new Date());

		Anio_cuota_moderadora anio_cuota_moderadora = ResCalculadorDeRangoCuota
				.getGrupo(paciente, getServiceLocator(), anioo, getSucursal());
		// double copago = (anio_cuota_moderadora != null ?
		// anio_cuota_moderadora.getPorcentaje_copago() : 0);
		double cuota_moderadora = (anio_cuota_moderadora != null ? anio_cuota_moderadora
				.getValor_cuota() : 0);

		if (pagare == null) {
			List<Detalle_pagare> lista_detalle = new LinkedList<Detalle_pagare>();
			for (Detalle_receta_rips detalleRecetaRips : recetaRips
					.getLista_detalle()) {

				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(sucursal.getCodigo_empresa());
				articulo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				articulo.setCodigo_articulo(detalleRecetaRips
						.getCodigo_articulo());
				articulo = getServiceLocator().getArticuloService()
						.consultar(articulo);

//				/* cargamos porcentaje adicional por paciente */
//				Porcentaje_adicional_medicamento porcentaje_adicional_medicamento = new Porcentaje_adicional_medicamento();
//				porcentaje_adicional_medicamento
//						.setCodigo_empresa(codigo_empresa);
//				porcentaje_adicional_medicamento
//						.setCodigo_sucursal(codigo_sucursal);
//				porcentaje_adicional_medicamento
//						.setCodigo_med(detalleRecetaRips
//								.getCodigo_articulo());
//				porcentaje_adicional_medicamento
//						.setNro_identificacion(recetaRips
//								.getNro_identificacion());
//				porcentaje_adicional_medicamento = getServiceLocator()
//						.getPorcentaje_adicional_medicamentoService()
//						.consultar(porcentaje_adicional_medicamento);
//
//				double porcentaje_adicional = 0d;
//				if (porcentaje_adicional_medicamento != null) {
//					porcentaje_adicional = porcentaje_adicional_medicamento
//							.getPorcentaje();
//				}
//				/* fin cargar valor adicional por paciente */

				Detalle_pagare detalle = new Detalle_pagare();
				detalle.setCodigo_empresa(getEmpresa().getCodigo_empresa());
				detalle.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
				detalle.setCodigo_servicio(detalleRecetaRips
						.getCodigo_articulo());
				detalle.setConcepto(articulo != null ? articulo.getNombre1()
						: "");
				detalle.setCantidad(detalleRecetaRips.getCantidad_recetada());
				detalle.setValor_unitario(articulo != null ? articulo
						.getPrecio1() : 0d);
				detalle.setValor_total((detalle.getValor_unitario() * detalle
						.getCantidad()));

				detalle.setCopago(cuota_moderadora);
				lista_detalle.add(detalle);
			}
			mapPagare.put("lista_detalle_pagare", lista_detalle);

		}
		mapPagare.put("pagare", pagare);
		
		return mapPagare;
		
	}

	public void imprimir(String codigo_receta) throws Exception {
		if (codigo_receta.equals("")) {
			Messagebox.show("La receta medica no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Receta_rips2");
		paramRequest.put("codigo_receta", codigo_receta);
		paramRequest.put("formato", "pdf");
		paramRequest.put("usuario_imprimio", usuarios.getNombres() + " "
				+ usuarios.getApellidos());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", form, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

	private void cambiarEstado(Receta_rips recetaRips) {
		if (auditor)
			recetaRips.setLeido_auditor("S");
		else
			recetaRips.setLeido(true);
		getServiceLocator().getReceta_ripsService().actualizar(recetaRips);
	}

	protected void cargarMedicamentos(Receta_rips recetaRips, boolean pagaCopago)
			throws Exception {
		
		Map params = new HashMap();
		params.put("receta", recetaRips);
		params.put("auditor", auditor);
		params.put("paga_copago", pagaCopago);

		Component componente = Executions.createComponents(
				"/pages/farmacia_recetas_rips_medicamentos.zul", this, params);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setHeight("100%");
		ventana.setVflex("1");
		ventana.doModal();
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //

			}

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
//		Anexo3_entidad anexo4_entidad = (Anexo3_entidad) obj;
//		try {
//
//			// Mostramos la vista //
//			tbxAccion.setText("modificar");
//			accionForm(true, tbxAccion.getText());
//		} catch (Exception e) {
//			
//			log.error(e.getMessage(), e);
//			Messagebox.show("Este dato no se puede editar", "Error !!",
//					Messagebox.OK, Messagebox.ERROR);
//		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Anexo3_entidad anexo4_entidad = (Anexo3_entidad) obj;
		try {
			int result = getServiceLocator().getAnexo3EntidadService()
					.eliminar(anexo4_entidad);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	@Override
	public void init() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();
		// initTime();
		verificamosTipeImpresion();
	}
	
	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Receta_rips> listarResultados(
					Map<String, Object> parametros) {
				List<Receta_rips> listado = getServiceLocator().getReceta_ripsService().listar(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getReceta_ripsService().totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilas(dato, gridResultado);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado,9);
	}

}
