/*
 * anexo4_entidadAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.controller;


import healthmanager.controller.SeleccionarPrestadorAction.IAccion;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Anexo4_entidad;
import healthmanager.modelo.bean.Anio_cuota_moderadora;
import healthmanager.modelo.bean.Anio_soat;
import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;
import healthmanager.modelo.bean.Detalle_anexo4;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Porcentaje_adicional_medicamento;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Anexo4_entidadService;
import healthmanager.modelo.service.Detalle_anexo4Service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IConstantesAutorizaciones;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.res.ResCalculadorDeRangoCuota;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Caja;
import contaweb.modelo.bean.Detalle_caja;
import contaweb.modelo.bean.Detalle_pagare;
import contaweb.modelo.bean.Pagare;
import contaweb.modelo.service.PagareService;

public class Anexo4_entidadAction extends ZKWindow implements WindowRegistrosIMG{

	private Anexo4_entidadService anexo4_entidadService;
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion; 
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Listbox lbxSearh;
	@View private Timer timer;
	
	@View private Datebox dtbxFecha_inicial;
	@View private Datebox dtbxFecha_final;
	
	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();
	
	private IAccion accion;
	
	public void listarCombos(){
		listarParameter();
		listarTipoBusqueda();
	}
	
	private void listarTipoBusqueda() {
        lbxSearh.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("Todos");
		lbxSearh.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("N");
		listitem.setLabel("Nuevo");
		lbxSearh.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("S");
		listitem.setLabel("Impreso");
		lbxSearh.appendChild(listitem);
		
		if (lbxSearh.getItemCount() > 0) {
			lbxSearh.setSelectedIndex(0);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("v_axo4.pac_nro_identificacion || ' ' || v_axo4.pac_apellido1 || ' ' || v_axo4.pac_apellido2 || ' ' || v_axo4.pac_nombre1 || ' ' || v_axo4.pac_nombre2");
		listitem.setLabel("Paciente");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	public void listarElementoListbox(Listbox listbox,boolean select){
		listbox.getChildren().clear();
		Listitem listitem;
		if(select){
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		
		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator().getElementoService().listar(tipo);
		
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
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
//		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			buscarDatos();
		}else{
			//buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
//		Collection<Component> collection = groupboxEditar.getFellows();
//		for (Component abstractComponent : collection) {
//			if (abstractComponent instanceof Textbox) {
//				Textbox textbox = (Textbox) abstractComponent;
//				if (!(textbox instanceof Combobox)) {
//					((Textbox) abstractComponent).setText(((Textbox) abstractComponent).getText().trim().toUpperCase());
//				} 
//			}
//		}
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
//		Collection<Component> collection = groupboxEditar.getFellows();
//		for (Component abstractComponent : collection){
//			if (abstractComponent instanceof Textbox) {
//				if (!((Textbox) abstractComponent).getId().equals("tbxValue")){
//					((Textbox) abstractComponent).setValue("");
//				}
//			}
//			if (abstractComponent instanceof Listbox) {
//				if (!((Listbox) abstractComponent).getId().equals("lbxParameter")){
//					if (((Listbox) abstractComponent).getItemCount() > 0) {
//						((Listbox) abstractComponent).setSelectedIndex(0);
//					}
//				}
//			}
//			if (abstractComponent instanceof Doublebox) {
//				((Doublebox) abstractComponent).setText("0.00");
//			}
//			if (abstractComponent instanceof Intbox) {
//				((Intbox) abstractComponent).setText("");
//			}
//			if (abstractComponent instanceof Datebox) {
//				((Datebox) abstractComponent).setValue(new Date());
//			}
//			if (abstractComponent instanceof Checkbox) {
//				((Checkbox) abstractComponent).setChecked(false);
//			}
//			if (abstractComponent instanceof Radiogroup) {
//				((Radio)((Radiogroup) abstractComponent).getFirstChild()).setChecked(true);
//			}
//		}
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		
		boolean valida = true;
		
		
		if(!valida){
			Messagebox.show("Los campos marcados con (*) son obligatorios", 
				usuarios.getNombres()+" recuerde que...", 
				Messagebox.OK, Messagebox.EXCLAMATION);
		}
		
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			String filtro = lbxSearh.getSelectedItem().getValue() + "";
			
			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			parameters.put("codigo_empresa", getEmpresa().getCodigo_empresa());
			parameters.put("codigo_sucursal", getSucursal().getCodigo_sucursal());
			parameters.put("fecha_inicio", dtbxFecha_inicial.getValue()); 
			parameters.put("fecha_final", dtbxFecha_final.getValue()); 
			
			if(!filtro.isEmpty()){
				parameters.put("leido", filtro);
			}
			
//			getServiceLocator().getAnexo4EntidadService().setLimit("limit 25 offset 0");
//			
//			List<Anexo4_entidad> lista_datos = getServiceLocator().getAnexo4EntidadService().listar(parameters);
			
			rowsResultado.getChildren().clear();
			resultadoPaginadoMacro.buscarDatos(parameters);
			
			
//			for (Anexo4_entidad anexo4_entidad : lista_datos) {
//				rowsResultado.appendChild(crearFilas(anexo4_entidad, this));
//			}
//			
//			gridResultado.setVisible(true);
//			gridResultado.setMold("paging");
//			gridResultado.setPageSize(20);
//			
//			gridResultado.applyProperties();
//			gridResultado.invalidate();
//			gridResultado.setVisible(true);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Anexo4_entidad anexo4_entidad = (Anexo4_entidad)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		String nombre = "";
		if(anexo4_entidad.getPaciente() != null){
			nombre = anexo4_entidad.getPaciente().getNombre1() + " "
					+ anexo4_entidad.getPaciente().getNombre2() + " "
					+ anexo4_entidad.getPaciente().getApellido1() + " "
					+ anexo4_entidad.getPaciente().getApellido2();
		}
		
		String servicio = " * SERVICIO NO ENCONTRADO * ";
		
		if(anexo4_entidad.getConfiguracion_servicios_autorizacion() != null){
			servicio = anexo4_entidad.getConfiguracion_servicios_autorizacion().getNombre();
		}
		
		String background = "";
		if(anexo4_entidad.getEstado().equals("02")){
			background = "background-color:#FACFC8;";
			fila.setTooltiptext("Autorizacion cancelada"); 
		}
		
		final boolean prestador_asignado = anexo4_entidad.getPrestador_asignado().equals("S");
		final boolean paga_copago = ((!anexo4_entidad.getEstado_cobro().equals(IConstantesAutorizaciones.ESTADO_COBRO_PYP))
				   && !anexo4_entidad.getConfiguracion_servicios_autorizacion().isMostrar_tipo_pcd());
		
		if(!prestador_asignado){
			background = "background-color:#DEF2A7";
			fila.setTooltiptext("Autorizacion necesita asignarle un prestador");
		}
		
		fila.setStyle("text-align: justify;nowrap:nowrap;");
		Cell cell = new Cell();
		cell.setStyle(background); 
		fila.appendChild(cell);
		fila.appendChild(new Label(anexo4_entidad.getCodigo()));
		fila.appendChild(new Label(anexo4_entidad.getCodigo_solicitud()));
		fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(anexo4_entidad.getFecha()))); 
		fila.appendChild(new Label(nombre));  
		fila.appendChild(new Label(servicio));  
		fila.appendChild(new Label(anexo4_entidad.getLeido().equalsIgnoreCase("S") ? "IMPRESO" : "NUEVO")); 
		 
		Caja caja = new Caja(false);
        caja.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
        caja.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
        caja.setCodigo_orden(anexo4_entidad.getCodigo());
        
        final Caja cajaEnd = getServiceLocator().getCajaService().consultar(caja);
		
		final Image imgCopago = new Image();
		imgCopago.setSrc("/images/caja.jpg");
		imgCopago.setTooltiptext("Generar copago");
		imgCopago.setStyle("cursor: pointer");
		imgCopago.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				generarCopago(anexo4_entidad, cajaEnd, anexo4_entidad
						.getConfiguracion_servicios_autorizacion());
			}
		});
		
		
		final Hbox hboxContenedorCopago = new Hbox();
		hboxContenedorCopago.appendChild(imgCopago);
		imgCopago.setVisible(paga_copago && prestador_asignado);
		fila.appendChild(hboxContenedorCopago);
		hbox.appendChild(space);
		
		final Image imgImprimir = new Image();
		imgImprimir.setSrc("/images/print_ico.gif");
		imgImprimir.setTooltiptext("Imprimir");
		imgImprimir.setStyle("cursor: pointer");
		if(!prestador_asignado){
			imgImprimir.setTooltiptext("Imprimir sin prestador");  
		}
		imgImprimir.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if(prestador_asignado){
					onImprimirAnexo4(anexo4_entidad, paga_copago);
				}else{
					imprimirAnexo4(anexo4_entidad, false); 
				}
			}
		});
		
		hbox.appendChild(imgImprimir);	
//		imgImprimir.setVisible(prestador_asignado);
		
		
		final Image imgCargarPrestador = new Image();
		imgCargarPrestador.setSrc("/images/consentimiento.png");
		imgCargarPrestador.setTooltiptext("Solicitar Prestador");
		imgCargarPrestador.setStyle("cursor: pointer");
		imgCargarPrestador.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				solicitarPrestador(anexo4_entidad, paga_copago, imgCopago,
						imgImprimir, imgCargarPrestador, anexo4_entidad.getConfiguracion_servicios_autorizacion());
			}
		}); 
		if(!prestador_asignado){
			hbox.appendChild(space);
		}
		imgCargarPrestador.setVisible(!prestador_asignado);
		hbox.appendChild(imgCargarPrestador);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	private void onImprimirAnexo4(final Anexo4_entidad anexo4_entidad, boolean paga_copago) throws Exception{
		Caja cajaEnd = getReciboCaja(anexo4_entidad);
		Pagare pagare = getPagares(anexo4_entidad);
        if(paga_copago && cajaEnd == null && pagare == null){
        	Messagebox.show(
					"Desea imprimir la orden sin cobrar el copago?",
					"Imprimir", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								imprimirAnexo4(anexo4_entidad, false); 
							}
						}
					});
        }else{
		     imprimirAnexo4(anexo4_entidad, true); 
        }
	}
	
	
	private Caja getReciboCaja(Anexo4_entidad anexo4_entidad){
		Caja caja = new Caja(false);
        caja.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
        caja.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
        caja.setCodigo_orden(anexo4_entidad.getCodigo());
        return getServiceLocator().getCajaService().consultar(caja);
	}
	
	private Pagare getPagares(Anexo4_entidad anexo4_entidad){
		Pagare pagare = new Pagare(false);
		pagare.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
		pagare.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
		pagare.setCodigo_orden(anexo4_entidad.getCodigo()); 
		return getServiceLocator().getServicio(PagareService.class).consultarAdmision(pagare); 
	}
	
	
	/**
	 * Este metodo me permite imprimir una orden siempre con prestador o sin prestador asignado
	 * @param anexo4_entidad
	 * @param mostrarPrestador - este parametro me permite visualizar el prestador
	 * */
	private void imprimirAnexo4(Anexo4_entidad anexo4_entidad, boolean mostrarPrestador) throws Exception{ 
		anexo4_entidad.setLeido("S");
		getServiceLocator().getAnexo4EntidadService().actualizar(anexo4_entidad);
		imprimir(anexo4_entidad.getCodigo(), mostrarPrestador);
		buscarDatos();
	}
	
	protected void solicitarPrestador(Anexo4_entidad anexo4_entidad,
			boolean paga_copago, Image imgCopago, Image imgImprimir,
			Image imgCargarPrestador, Configuracion_servicios_autorizacion config) {
		abrirWindowPrestadores(anexo4_entidad, paga_copago, imgCopago,
				imgImprimir, imgCargarPrestador, config);
	}


	public static String getNroAutorizacion(Anexo4_entidad anexo4_entidad){
		if(anexo4_entidad.getAnexo_constituye() != null){
			if(anexo4_entidad.getAnexo_constituye().trim().isEmpty()){
				return anexo4_entidad.getCodigo_solicitud();
			}else{
				return anexo4_entidad.getAnexo_constituye();
			}
		}else{
			return anexo4_entidad.getCodigo_solicitud();
		}
	}
	
	public void imprimir(String codigo, boolean mostrar_prestador) throws Exception{
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Autorizacion"); 
		paramRequest.put("codigo", codigo);
		paramRequest.put("mostrar_prestador", mostrar_prestador);
		paramRequest.put("reporte", mostrar_prestador ? "/report/autorizaciones_simple.jasper" : "/report/autorizacion_no_pagada.jasper" );
		 
		Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window)componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
	
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			setUpperCase();
			if(validarForm()){
				//Cargamos los componentes //
				
				
			}
			
		}catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
//		Anexo4_entidad anexo4_entidad = (Anexo4_entidad)obj;
		try{
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar",
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Anexo4_entidad anexo4_entidad = (Anexo4_entidad)obj;
		try{
			int result = getServiceLocator().getAnexo4EntidadService().eliminar(anexo4_entidad);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}catch(RuntimeException r){
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	/* nueva actualizacion de generar copago*/
	public void generarCopago(Anexo4_entidad anexo4_entidad, Caja caja, Configuracion_servicios_autorizacion config)throws Exception {
		try { 
			
			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
			paciente.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
			paciente.setNro_identificacion(anexo4_entidad.getNro_identificacion_paciente());
			paciente = getServiceLocator().getPacienteService().consultar(paciente);
//			String grupo = (paciente != null ? paciente.getGrupo_cuota() : "");
			
			String anioo = new SimpleDateFormat("yyyy").format(new Date());
			
			Anio_cuota_moderadora anio_cuota_moderadora = ResCalculadorDeRangoCuota.getGrupo(paciente,
					getServiceLocator(), anioo, getSucursal());
			
			double porcentaje = (anio_cuota_moderadora != null ? anio_cuota_moderadora.getPorcentaje_copago() : 0);
            double cuota_moderadora = (anio_cuota_moderadora != null ? anio_cuota_moderadora.getValor_cuota() : 0);
			
			Anio_soat anio_soat = new Anio_soat();
			anio_soat.setAnio(anioo);
			
			Map parametros = new HashMap();
			parametros.put("caja", caja);
			parametros.put("paciente", paciente);
			parametros.put("codigo_orden", anexo4_entidad.getCodigo());
			
			if(config.getTipo_servicio().equals(IConstantes.TIPO_SERVICIO_AUTO_MEDICAMENTOS)
					|| config.getModo_cobro().equals(IConstantes.MODO_COBRO_RECIBOS_CAJA_MEDICAMENTOS)){   
				parametros.put("copago_autorizaciones", "N");
				parametros.put("copago_medicamentos", "S");
				parametros.put("cuota_moderadora", cuota_moderadora);
				
			}else{
				parametros.put("copago_autorizaciones", "S");
				parametros.put("copago_medicamentos", "N");
				
			}
			
			if(caja==null){
				List<Detalle_caja> lista_detalle = new LinkedList<Detalle_caja>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", anexo4_entidad.getCodigo_empresa());
				map.put("codigo_sucursal", anexo4_entidad.getCodigo_sucursal());
				map.put("codigo_orden", anexo4_entidad.getCodigo());
				List<Detalle_anexo4> detalle_anexo4s = getServiceLocator().getServicio(Detalle_anexo4Service.class).listar(map);
				for (Detalle_anexo4 detalle_anexo4 : detalle_anexo4s) {
					Detalle_caja detalle = new Detalle_caja();
	                detalle.setCodigo_empresa(detalle_anexo4.getCodigo_empresa());
	                detalle.setCodigo_sucursal(detalle_anexo4.getCodigo_sucursal());
	                detalle.setCodigo_articulo(detalle_anexo4.getCodigo_cups());
	                detalle.setDetalle(detalle_anexo4.getNombre_pcd());
	                detalle.setCantidad(detalle_anexo4.getUnidades());
	                detalle.setValor_unitario(detalle_anexo4.getValor_procedimiento());
	                detalle.setValor_total((detalle_anexo4.getValor_procedimiento() * detalle_anexo4.getUnidades().intValue()));
	                detalle.setCopago((detalle_anexo4.getValor_procedimiento() *(porcentaje/100)) * detalle_anexo4.getUnidades().intValue());
	                
	                // validamos si es medicamento y agregamos un porcentaje adicional
	                if(anexo4_entidad.getTipo_servicio().equals(AutorizacionesAction.MEDICAMENTOS)){
	                	Porcentaje_adicional_medicamento porcentaje_adicional_medicamento = new Porcentaje_adicional_medicamento();
						porcentaje_adicional_medicamento
								.setCodigo_empresa(codigo_empresa);
						porcentaje_adicional_medicamento
								.setCodigo_sucursal(codigo_sucursal);
						porcentaje_adicional_medicamento
								.setCodigo_med(detalle_anexo4.getCodigo_procedimiento()); 
						porcentaje_adicional_medicamento
								.setNro_identificacion(anexo4_entidad.getNro_identificacion_paciente());
						porcentaje_adicional_medicamento = getServiceLocator()
								.getPorcentaje_adicional_medicamentoService()
								.consultar(porcentaje_adicional_medicamento);

						double porcentaje_adicional = 0d;
						if (porcentaje_adicional_medicamento != null) {
							porcentaje_adicional = porcentaje_adicional_medicamento
									.getPorcentaje();
						}
						detalle.setPorcentaje_adicionar(porcentaje_adicional);  
	                }
	                lista_detalle.add(detalle);
				}
				parametros.put("lista_detalle", lista_detalle);
			}
			Map<String,Object> mapPagare =getPagare(anexo4_entidad);
			parametros.put("mapPagare", mapPagare);
			try {
			Component componente = Executions.createComponents("/pages/caja_cuota_moderadora.zul", 
					this, parametros);
		    Window win = (Window) componente;
		    win.setWidth("100%");
		    win.setHeight("100%");
		    win.setMode("modal");
			} catch (Exception e) { }
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public Map<String,Object> getPagare(Anexo4_entidad anexo4_entidad)throws Exception{
		
		Map<String,Object> mapPagare = new HashMap<String,Object>();
		
		Pagare pagare = new Pagare(false);
        pagare.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
        pagare.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
        pagare.setCodigo_orden(anexo4_entidad.getCodigo());
        
        pagare = getServiceLocator().getPagareService().consultarAdmision(pagare);
		
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
		paciente.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
		paciente.setNro_identificacion(anexo4_entidad.getNro_identificacion_paciente());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		
		String anioo = new SimpleDateFormat("yyyy").format(new Date());
		
		Anio_cuota_moderadora anio_cuota_moderadora = ResCalculadorDeRangoCuota.getGrupo(paciente,
				getServiceLocator(), anioo, getSucursal());
		
		double porcentaje = (anio_cuota_moderadora != null ? anio_cuota_moderadora.getPorcentaje_copago() : 0);
		
		Anio_soat anio_soat = new Anio_soat();
		anio_soat.setAnio(anioo);
		
		if(pagare==null){
			List<Detalle_pagare> lista_detalle = new LinkedList<Detalle_pagare>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", anexo4_entidad.getCodigo_empresa());
			map.put("codigo_sucursal", anexo4_entidad.getCodigo_sucursal());
			map.put("codigo_orden", anexo4_entidad.getCodigo());
			List<Detalle_anexo4> detalle_anexo4s = getServiceLocator().getServicio(Detalle_anexo4Service.class).listar(map);
			for (Detalle_anexo4 detalle_anexo4 : detalle_anexo4s) {
				Detalle_pagare detalle = new Detalle_pagare();
                detalle.setCodigo_empresa(detalle_anexo4.getCodigo_empresa());
                detalle.setCodigo_sucursal(detalle_anexo4.getCodigo_sucursal());
                detalle.setCodigo_servicio(detalle_anexo4.getCodigo_cups());
                detalle.setConcepto(detalle_anexo4.getNombre_pcd());
                detalle.setCantidad(detalle_anexo4.getUnidades());
                detalle.setValor_unitario(detalle_anexo4.getValor_procedimiento());
                detalle.setValor_total((detalle_anexo4.getValor_procedimiento() * detalle_anexo4.getUnidades().intValue()));
                detalle.setCopago((detalle_anexo4.getValor_procedimiento() *(porcentaje/100)) * detalle_anexo4.getUnidades().intValue());
                lista_detalle.add(detalle);
			}
			mapPagare.put("lista_detalle_pagare", lista_detalle);
		}

		mapPagare.put("pagare", pagare);
		
		return mapPagare;
	}
	
	
	public void generarCopagoOld(Anexo4_entidad anexo4_entidad)throws Exception {
		
		try {
			Procedimientos procediemiento_caja = new Procedimientos();
			boolean cobra_copago = false;
			for (int i = 1; i <= 20; i++) {
				Method method = anexo4_entidad.getClass().getMethod("getCodigo_cups"+i, new Class[]{});
				String codigo_cups = (String)method.invoke(anexo4_entidad, new Object[]{});
				procediemiento_caja.setCodigo_cups(codigo_cups);
				Procedimientos auxProcediemiento_caja = getServiceLocator().getProcedimientosService().consultar(procediemiento_caja);
				if(!codigo_cups.equals("")){
					if(auxProcediemiento_caja==null){
						throw new Exception("Codigo "+codigo_cups+" no se encuentra registrado");
					}
//					if(auxProcediemiento_caja.getGenera_copago().equals("S")){
//						cobra_copago = true;
//						i = 20;
//					}
				}
			}
			
			if(!cobra_copago){
				throw new Exception("Ninguno de los procedimientos del anexo generan copagos");
			}
			
			Caja caja = new Caja();
            caja.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
            caja.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
            caja.setCodigo_anexo4(anexo4_entidad.getCodigo_solicitud());
            caja.setCopago_autorizaciones("S");
            caja = getServiceLocator().getCajaService().consultar(caja);
            
            Anexo3_entidad anexo3_entidad = new Anexo3_entidad();
            anexo3_entidad.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
            anexo3_entidad.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
            anexo3_entidad.setNumero_solicitud(anexo4_entidad.getCodigo_solicitud());
            anexo3_entidad = getServiceLocator().getAnexo3EntidadService().consultar(anexo3_entidad);
            if(anexo3_entidad==null){
            	throw new Exception("Autorizacion no tiene solicitud asignada");
            }
            
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
            paciente.setCodigo_sucursal(anexo4_entidad.getCodigo_sucursal());
            paciente.setNro_identificacion(anexo3_entidad.getCodigo_paciente());
            paciente = getServiceLocator().getPacienteService().consultar(paciente);
            String grupo = (paciente != null ? paciente.getGrupo_cuota() : "");
            
            String anioo = new SimpleDateFormat("yyyy").format(new Date());
			
            Anio_cuota_moderadora anio_cuota_moderadora = new Anio_cuota_moderadora();
            anio_cuota_moderadora.setCodigo_empresa(anexo4_entidad.getCodigo_empresa());
            anio_cuota_moderadora.setGrupo(grupo);
            anio_cuota_moderadora.setAnio(anioo);
            anio_cuota_moderadora = getServiceLocator().getAnio_cuota_moderadoraService().consultar(anio_cuota_moderadora);
            double porcentaje = (anio_cuota_moderadora != null ? anio_cuota_moderadora.getPorcentaje_copago() : 0);
            
            Map parametros = new HashMap();
    		parametros.put("caja", caja);
    		parametros.put("paciente", paciente);
    		parametros.put("nro_autorizacion", anexo4_entidad.getCodigo_solicitud());
    		parametros.put("copago_autorizaciones", "S");
    		parametros.put("copago_medicamentos", "N");
            
            if(caja==null){
            	List<Detalle_caja> lista_detalle = new LinkedList<Detalle_caja>();
            	for (int i = 1; i <= 20; i++) {
    				Method methodCodigo_procedimiento = anexo4_entidad.getClass().getMethod("getCodigo_procedimiento"+i, new Class[]{});
    				String codigo_cups = (String)methodCodigo_procedimiento.invoke(anexo4_entidad, new Object[]{});
    				
    				Method methodCant = anexo4_entidad.getClass().getMethod("getCantidad"+i, new Class[]{});
    				double cantidad = (Integer)methodCant.invoke(anexo4_entidad, new Object[]{});
    				
    				Method methodValor = anexo4_entidad.getClass().getMethod("getValor"+i, new Class[]{});
    				double valor = (Integer)methodValor.invoke(anexo4_entidad, new Object[]{});
    				
    				procediemiento_caja.setCodigo_cups(codigo_cups);
    				Procedimientos auxProcediemiento_caja = getServiceLocator().getProcedimientosService().consultar(procediemiento_caja);
    				
    				if(!codigo_cups.equals("")){
    					if(true){
//    						double valor_pcd = getServiceLocator().getProcediemientoCajaService().consultarValor_pcd(auxProcediemiento_caja, anioo);
    						double valor_pcd = valor;
    						//System.Out.Println("Valor procedimeinto: " + valor);
    						
        					Detalle_caja detalle = new Detalle_caja();
                            detalle.setCodigo_empresa(empresa.getCodigo_empresa());
                            detalle.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                            detalle.setCodigo_articulo(codigo_cups);
                            detalle.setDetalle(auxProcediemiento_caja.getDescripcion());
                            detalle.setCantidad(cantidad);
                            detalle.setValor_unitario(valor_pcd);
                            detalle.setValor_total((valor_pcd*cantidad));
                            detalle.setCopago((valor_pcd*(porcentaje/100))*cantidad);
                            lista_detalle.add(detalle);
        				}
    				}
    				
    			}
            	parametros.put("lista_detalle", lista_detalle);
            }
            Component componente = Executions.createComponents("/pages/caja_cuota_moderadora.zul", 
    				this, parametros);
            Window win = (Window) componente;
            win.setWidth("860px");
            win.setHeight("420px");
            win.setClosable(true);
            win.setTitle("Recibo de caja por copago");
            win.setMode("modal");
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Override
	public void init() {
		listarCombos();
		parametrizarResultadoPaginado();
	}

		private void parametrizarResultadoPaginado() {
			ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

				@Override
				public List<Anexo4_entidad> listarResultados(
						Map<String, Object> parametros) {
					return anexo4_entidadService.listarResultados(parametros);
				}

				@Override
				public Integer totalResultados(Map<String, Object> parametros) {
					return anexo4_entidadService.totalResultados(parametros);
				}

				@Override
				public XulElement renderizar(Object dato) throws Exception {
					return crearFilas(dato, gridResultado);
				}

			};
			resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
					gridResultado, 10);
		}
	
	
	
	public void abrirWindowPrestadores(Anexo4_entidad anexo4_entidad,
			boolean paga_copago, Image imgCopago, Image imgImprimir,
			Image imgCargarPrestador, final Configuracion_servicios_autorizacion config) {
		
		if(config != null){
			Map<String, Object> map_params = new HashMap<String, Object>();
			map_params.put("anexo4_entidad", anexo4_entidad);
			map_params.put("config", config);
			map_params.put("paga_copago", paga_copago); 
			map_params.put("imgCopago", imgCopago); 
			map_params.put("imgImprimir", imgImprimir); 
			map_params.put("imgCargarPrestador", imgCargarPrestador);
			SeleccionarPrestadorAction window = (SeleccionarPrestadorAction) Executions
					.createComponents("/pages/seleccionar_prestador_caja.zul",
							this, map_params); 
			if(accion == null){
				accion = new IAccion() {  
					@Override
					public void onCambiarPrestador(boolean paga_copago,
							Anexo4_entidad anexo4_entidad) {
						try {
							if(paga_copago){
								generarCopago(anexo4_entidad, null, config);  
							}else{
								anexo4_entidad.setLeido("S");
								anexo4_entidadService.actualizar(anexo4_entidad);
								imprimir(anexo4_entidad.getCodigo(), true);
								buscarDatos();
							}
						} catch (Exception e) { 
							MensajesUtil.mensajeError(e, null, Anexo4_entidadAction.this); 
						}
					}

					@Override
					public void onGenerarCopago(Anexo4_entidad anexo4_entidad,
							Configuracion_servicios_autorizacion config) {
						try {
							generarCopago(anexo4_entidad, null, config);
						} catch (Exception e) { 
							MensajesUtil.mensajeError(e, null, Anexo4_entidadAction.this);
						}
					}

					@Override
					public void onImprimir(Anexo4_entidad anexo4_entidad, boolean paga_copago) {
						try {
							onImprimirAnexo4(anexo4_entidad, paga_copago);
						} catch (Exception e) { 
							MensajesUtil.mensajeError(e, null, Anexo4_entidadAction.this);
						}
					}
				};
			}
			window.setAccion(accion); 
			window.setWidth("800px");
			window.setHeight("400px"); 
			window.doModal();
		}else{
			MensajesUtil
					.mensajeAlerta(
							"Advertencia",
							"El servicio no existe, por lo tanto no se puede autoriza.\n código de verificacion: "
									+ anexo4_entidad.getTipo_servicio());  
		}
	}


	@Override
	public void onSeleccionarRegistro(Object registro) {
		if(registro instanceof Administradora){
//			cambiarPrestador((Administradora)registro);
//			windowRegistros.detach();
		}
	}

//	private void cambiarPrestador(Administradora registro) {
//		try {
//			if(windowRegistros != null){
//				final Anexo4_entidad anexo4_entidad = (Anexo4_entidad) windowRegistros.getAttribute("anexo4");
//				anexo4_entidad.setCodigo_prestador(registro.getCodigo());
////				Unidad_funcional unidad_funcional = (Unidad_funcional) windowRegistros.getAttribute("servicio");
//				final boolean paga_copago = (Boolean) windowRegistros.getAttribute("paga_copago"); 
//				Image imgCopago = (Image) windowRegistros.getAttribute("imgCopago"); 
//				Image imgImprimir = (Image) windowRegistros.getAttribute("imgImprimir"); 
//				Image imgCargarPrestador = (Image) windowRegistros.getAttribute("imgCargarPrestador"); 
//				
//				// Realizamos el cambio de prestador
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("anexo4", anexo4_entidad);
////				map.put("unidad_funcional", unidad_funcional);
//				map.put("administradora", registro);
//				map.put("servicelocator", getServiceLocator());
//				getServiceLocator().getServicio(Anexo4_entidadService.class).guardarCambioPrestador(map);
//				imgCargarPrestador.setVisible(false);
//				imgImprimir.setVisible(true);
//				String msj = "¿Cambio de prestador realizado exitosamente, desea imprimir esta autorizacion?";
//				if(paga_copago){
//					imgCopago.setVisible(true); 
//					msj = "¿Cambio de prestador realizado exitosamente, desea realizar el cobro del copago?";
//				}
//				Messagebox.show(
//						"" + msj,
//						"Informacion", Messagebox.YES + Messagebox.NO,
//						Messagebox.QUESTION,
//						new org.zkoss.zk.ui.event.EventListener<Event>() {
//							public void onEvent(Event event) throws Exception {
//								if ("onYes".equals(event.getName())) {
//									if(paga_copago){
//										generarCopago(anexo4_entidad, null, );
//									}else{
//										anexo4_entidad.setLeido("S");
//										getServiceLocator().getAnexo4EntidadService().actualizar(anexo4_entidad);
//										imprimir(anexo4_entidad.getCodigo(), true);
//										buscarDatos();
//									}
//								}
//							}
//						});
//			}	
//		} catch (Exception e) { 
//			MensajesUtil.mensajeError(e, "Se presento un error al realizar el cambio de prestador, comuníquese con el administrador del sistema", this); 
//		}
//	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if(registro instanceof Administradora){
		  	return celdasListitemPrestador((Administradora)registro);
		}
		return null;
	}
	
	private Object[] celdasListitemPrestador(Administradora registro) {
		return new Object[]{registro.getNit(), registro.getNombre(), registro.getDireccion()}; 
	}
}
