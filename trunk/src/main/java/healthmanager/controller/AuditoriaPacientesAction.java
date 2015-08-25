package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Paciente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;

public class AuditoriaPacientesAction extends ZKWindow {
	
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_administradora;
	@View private Textbox tbxInfoAseguradora;
	@View private Toolbarbutton btnLimpiarAseguradora;
	
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	
	@View private Listbox lbxSearh;
	@View private Popup popup;
	@View private Listbox lbxFormato;
	
	@View private Listbox lbxLimite;

	@Override
	public void init() throws Exception {
	   listarCombos();
       parametrizarBandbox();
	} 
	
	private void listarCombos() {
		listarParameter();
	}

	private void parametrizarBandbox(){
		parametrizarBandboxAseguradora();
	}
	
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre1||' '||nombre2");
		listitem.setLabel("Nombres");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("apellido1||' '||apellido2");
		listitem.setLabel("Apellidos");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	private void parametrizarBandboxAseguradora(){
		tbxCodigo_administradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		tbxCodigo_administradora
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
						parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
						parametros.put("tercerizada", "N"); 
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getCodigo());
						bandbox.setAttribute("admin", registro);
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}
	
	
	public void buscarDatos() throws Exception {
		try {
			Set<Listitem> listitems = lbxSearh.getSelectedItems();
			if(listitems.isEmpty()){
				Clients.showNotification("Presione aqui para configurar filtros", getFellow("btnConfiguracionBusqeuda"));   
				throw new ValidacionRunTimeException("Para esta opcion debe seleccionar por lo menos un filtro de busqueda"); 
			}
			
			String codigo_empresa = empresa.getCodigo_empresa();
			String codigo_sucursal = sucursal.getCodigo_sucursal();
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("order", "Ordenar"); 
			parameters.put("value", "%" + value + "%");
			
			if(!tbxCodigo_administradora.getValue().trim().isEmpty()){
				parameters.put("codigo_administradora", tbxCodigo_administradora.getValue()); 
			}
			
			agregarFiltros(parameters, listitems); 
			
            //log.info("@Parametros: " + parameters); 
			parameters.put("limite_paginado", 
					"limit " + lbxLimite.getSelectedItem().getValue() +  " offset 0"); 

			List<Paciente> lista_datos = getServiceLocator()
					.getPacienteService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Paciente paciente : lista_datos) {
				rowsResultado.appendChild(crearFilas(paciente, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this); 
		}
	}
	
	/**
	 * Este metodo me permite agregar filtros
	 * que me permitiran saber cuales son los pacientes que tienen las edades erroneas
	 * */
	private void agregarFiltros(Map<String, Object> parameters, Set<Listitem> listitems) {
		for (Listitem listitem : listitems) {
			parameters.put(listitem.getValue().toString(), listitem.getValue());
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();
		

		final Paciente paciente = (Paciente) objeto;

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(paciente.getCodigo_empresa());
		administradora.setCodigo_sucursal(paciente.getCodigo_sucursal());
		administradora.setCodigo(paciente.getCodigo_administradora());
		administradora = getServiceLocator().getAdministradoraService()
				.consultar(administradora);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(paciente.getTipo_identificacion() + ""));
		fila.appendChild(new Label(paciente.getDocumento() + ""));
		fila.appendChild(new Label(paciente.getCodigo_administradora() + " - "
				+ (administradora != null ? administradora.getNombre() : "")));
		fila.appendChild(new Label(paciente.getApellido1() + " "
				+ paciente.getApellido2()));
		fila.appendChild(new Label(paciente.getNombre1() + " "
				+ paciente.getNombre2()));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(paciente.getFecha_nacimiento())));
		
		
		String edad_paciente = " * ";
		if(paciente.getFecha_nacimiento().compareTo(Calendar.getInstance().getTime()) <= 0){
			edad_paciente = Util.getEdadPrsentacion(paciente.getFecha_nacimiento());
		}else{
			fila.setTooltiptext("La edad no puede ser calculada fecha de nacimiento no es correcta"); 
		}
		fila.appendChild(new Label(edad_paciente + ""));
		
		fila.setAttribute("paciente", paciente);
		fila.setAttribute("administradora", administradora);
		
		return fila;
	} 
	
	
	public void imprimir(){
		try {
			if(rowsResultado.getChildren().isEmpty())
				throw new ValidacionRunTimeException("Para realizar esta opcion por lo menos debe tener un dato"); 
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("listado", getListado()); 
			map.put("formato", lbxFormato.getSelectedItem().getValue()); 
			map.put("name_report", "AuditoriaPacientes");
			Component componente = Executions.createComponents("/pages/printInformes.zul", this, map);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this); 
		}
	}
	
	/**
	 * Este metodo me permite mapear, el listado de pacientes consultados
	 * @author Luis 
	 * */
	private List<Map<String, Object>> getListado() {
		List<Map<String, Object>> resultado = new ArrayList<Map<String,Object>>();
		List<Component> rows = rowsResultado.getChildren();
		for (Component component : rows) {
			 if(component instanceof Row){
				 Paciente paciente = (Paciente) component.getAttribute("paciente"); 
				 Administradora administradora = (Administradora) component.getAttribute("administradora"); 
				 
				 if(paciente != null){
					 Map<String, Object> map = new HashMap<String, Object>();
					 map.put("codigo_administradora", "" + paciente.getCodigo_administradora());
					 map.put("nombre_administradora", (administradora != null ? administradora.getNombre() : "* NO ENCONTRADA"));
					 map.put("informacion_paciente", "" + paciente.getNombreCompleto());
					 map.put("fecha_nacimiento", "" + new SimpleDateFormat("dd/MM/yyyy")
						.format(paciente.getFecha_nacimiento()));
					 
					 String edad_paciente = " * ";
						if(paciente.getFecha_nacimiento().compareTo(Calendar.getInstance().getTime()) <= 0){
							edad_paciente = Util.getEdadPrsentacion(paciente.getFecha_nacimiento());
						}
					 
					 map.put("edad_paciente", "" + edad_paciente);
					 map.put("numero_id", paciente.getTipo_identificacion() + " - " + paciente.getDocumento());
					 resultado.add(map);
				 }
			 }
		}
		return resultado;  
	}

}
