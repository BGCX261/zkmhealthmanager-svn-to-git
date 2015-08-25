/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.service.ContratosService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.bean.Nota_contable;


/**
 * @author Usuario
 *
 */
public class RadicacionFacturasAction extends ZKWindow {
	
//	private static Logger log = Logger.getLogger(RadicacionFacturasAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
	private ContratosService contratosService;
	
	@View private Borderlayout groupboxEditar;
	
	@View(isMacro = true)private BandboxRegistrosMacro bandboxAseguradora;
	@View private Textbox tbxInfoAseguradora;
	@View private Toolbarbutton btnLimpiarAseguradora;
	
	@View private Listbox lbxPlan;
	
	@View private Datebox dtbxFecha_inicial;
	@View private Datebox dtbxFecha_fin;
	
	@View private Listbox listboxRegistros;
	
	private List<Facturacion> lista_datos;

	@Override
	public void init() throws Exception {
		lista_datos = new ArrayList<Facturacion>();
		parametrizarBandbox();
		
		GregorianCalendar fecha = new GregorianCalendar();
		fecha.set(Calendar.DAY_OF_MONTH, 1);
		dtbxFecha_inicial.setValue(fecha.getTime());
		
		fecha = new GregorianCalendar();
		fecha.set(Calendar.DAY_OF_MONTH, fecha.getActualMaximum(Calendar.DAY_OF_MONTH));
		dtbxFecha_fin.setValue(fecha.getTime());
		
	}
	
	private void parametrizarBandbox() {
		bandboxAseguradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		bandboxAseguradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						lbxPlan.setDisabled(false);
						Utilidades.listarcontratosPorAdministradora(lbxPlan,
								true, registro.getCodigo(),
								RadicacionFacturasAction.this, contratosService);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						Utilidades.listarcontratosPorAdministradora(lbxPlan,
								true, "",
								RadicacionFacturasAction.this, contratosService);
						lbxPlan.setDisabled(true);
						
					}
				});
	}
	
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("codigo_administradora", bandboxAseguradora.getValue());
			if(lbxPlan.getSelectedItem()!=null){
				if(!lbxPlan.getSelectedItem().getValue().equals("")){
					parameters.put("id_plan", ((Contratos)lbxPlan.getSelectedItem().getValue()).getId_plan());
				}
			}
			parameters.put("consulta_para_radicar", "");
			parameters.put("rango_fecha", "");
			parameters.put("fecha_inicio", dtbxFecha_inicial.getValue());
			parameters.put("fecha_final", dtbxFecha_fin.getValue());
			
			//log.info("parameters: "+parameters);
			
			lista_datos = getServiceLocator().getFacturacionService().listarRegistros(parameters);
			////log.info("lista_datos: "+lista_datos);
			crearFilas();
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	// Este metodo genera las filas nuevamente //
	public void crearFilas() {
		listboxRegistros.getItems().clear();
		for (int j = 0; j < lista_datos.size(); j++) {
			Facturacion facturacion = lista_datos.get(j);
			crearFilaDetalle(facturacion,j);
		}
	}
	
	// Metodo para crear la grilla cuando se consulte los servicios cargados //
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void crearFilaDetalle(Facturacion facturacion,int j){
		
		//final int index = j;
		final Listitem fila = new Listitem();
		fila.setId("fila_"+j);
		fila.setValue(j+"");
		
		//Columna //
		Listcell cell = new Listcell();
		final Checkbox checkbox = new Checkbox();
		checkbox.setId("radicado_"+j);
		checkbox.setChecked(facturacion.getRadicado().equals("S")?true:false);
		cell.appendChild(checkbox);
		fila.appendChild(cell);
		
		//Columna //
		cell = new Listcell();
		final Datebox dtbxFecha_radicado = new Datebox();
		dtbxFecha_radicado.setId("fecha_radicado_"+j);
		dtbxFecha_radicado.setFormat("yyyy-MM-dd");
		dtbxFecha_radicado.setHflex("1");
		dtbxFecha_radicado.setStyle("font-size:9px");
		dtbxFecha_radicado.setInplace(true);
		dtbxFecha_radicado.setValue(facturacion.getRadicado().equals("S")?
				(facturacion.getFecha_erradicacion()!=null?
						new Date(facturacion.getFecha_erradicacion().getTime()):null):null);
		dtbxFecha_radicado.setDisabled(facturacion.getRadicado().equals("S")?false:true);
		cell.appendChild(dtbxFecha_radicado);
		fila.appendChild(cell);
		
		//Columna codigo
		cell = new Listcell();
		Label label = new Label(facturacion.getCodigo_documento_res());
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);
		
		cell = new Listcell();
		label = new Label(facturacion.getDescripcion());
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);
		
		cell = new Listcell();
		label = new Label(new SimpleDateFormat("yyyy-MM-dd").format(facturacion.getFecha()));
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);
		
		cell = new Listcell();
		label = new Label(Utilidades.formatDecimal(
				facturacion.getValor_total()-facturacion.getValor_copago(), "#,##0.00"));
		label.setStyle("font-size:9px");
		cell.appendChild(label);
		fila.appendChild(cell);
		
		cell = new Listcell();
		Image img = new Image("/images/print_ico.gif");
		img.setId("img_" + j);
		img.setTooltiptext("Imprimir radicado");
		img.setStyle("cursor:pointer");
		cell.appendChild(img);
		fila.appendChild(cell);
		
		Nota_contable nota_contable = new Nota_contable();
		nota_contable.setCodigo_empresa(facturacion.getCodigo_empresa());
		nota_contable.setCodigo_sucursal(facturacion.getCodigo_sucursal());
		nota_contable.setCodigo_comprobante(facturacion.getFuenta_radicado());
		nota_contable.setCodigo_documento(facturacion.getDocumento_radicado());
		nota_contable = getServiceLocator().getNota_contableService().consultar(nota_contable);
		if(nota_contable==null){
			img.setVisible(false);
		}
		
		final Nota_contable auxNota_contable = nota_contable;
		
		listboxRegistros.appendChild(fila);
		listboxRegistros.setMold("paging");
		listboxRegistros.setPageSize(20);
		
		checkbox.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				if(checkbox.isChecked()){
					dtbxFecha_radicado.setDisabled(false);
				}else{
					dtbxFecha_radicado.setDisabled(true);
					dtbxFecha_radicado.setValue(null);
				}
			}
		});
		
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(auxNota_contable!=null){
					imprimirNota_contable(auxNota_contable.getCodigo_documento(), 
							auxNota_contable.getCodigo_comprobante());
				}
			}
		});
		
	}
	
	public void actualizarPagina() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Facturacion facturacion = lista_datos.get(i);
						
			Checkbox checkbox = (Checkbox) listboxRegistros
					.getFellow("radicado_" + i);
			
			Datebox dtbxFecha_radicado = (Datebox) listboxRegistros
					.getFellow("fecha_radicado_" + i);
			
			facturacion.setRadicado(checkbox.isChecked()?"S":"N");
			if(!dtbxFecha_radicado.getText().equals("")){
				facturacion.setFecha_erradicacion(new java.sql.Date(dtbxFecha_radicado.getValue().getTime()));
			}
			
			lista_datos.set(i, facturacion);
		}
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarForm()throws Exception{
		
		boolean valida = true;
		
		String mensaje = "Los campos marcados con (*) son obligatorios";
		
		if (valida && lista_datos.size() <= 0) {
			mensaje = "Debe crear al menos un registro de en la grilla";
			valida = false;
		}
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...",
					mensaje);
		}
		
		if(valida){
			actualizarPagina();
			
			for (int i = 0; i < lista_datos.size(); i++) {
				Facturacion facturacion = lista_datos.get(i);
				if(facturacion.getRadicado().equals("S") && facturacion.getFecha_erradicacion()==null){
					mensaje = "Debe colocar la fecha de radicacion en la fila "
							+ (i) + "";
					valida = false;
					i = lista_datos.size();
				}
			}
		}
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...",mensaje);
		}
		
		return valida;
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			if(validarForm()){
				
				getServiceLocator().getFacturacionService().
					guardarFacturasRadicadas(lista_datos, usuarios.getCodigo());
				
				buscarDatos();
				MensajesUtil.mensajeInformacion("Informacion ..","Los datos se guardaron satisfactoriamente");
				
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void imprimirNota_contable(String codigo_documento,String codigo_comprobante) throws Exception {
		if (codigo_documento.equals("")) {
			Messagebox.show("El documento no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Nota_contable");
		paramRequest
				.put("codigo_comprobante", codigo_comprobante);
		paramRequest.put("codigo_documento", codigo_documento);
		paramRequest.put("formato", "pdf");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}


}
