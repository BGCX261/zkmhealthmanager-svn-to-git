package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IParametrosRequest;
import com.framework.constantes.ITiposReportesIPS;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.res.L2HContraintDate;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class ReportePorEpsAction extends ZKWindow implements
           WindowRegistrosIMG{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipo_reporte = null;

	private static final String ADMINISTRADORA = "admins_";
	
	@View private Datebox dtxFechaInicial;
	@View private Datebox dtxFechaFinal;
	@View Listbox lbxFormato;
	
	@View private Auxheader auxTitulo;
	@View private Rows rowEps;
	@View private Checkbox chk_entidad_eps;
	
	@View private Groupbox grbAgrupado;
	
	private List<String> lista_seleccionados_eps = new ArrayList<String>();
	private List<Map<String, Object>> lista_datos_eps = new ArrayList<Map<String,Object>>();

	@Override
	public void params(Map<String, Object> map) {
		tipo_reporte = (String)map
				.get(IParametrosRequest.TIPO_REPORTE_EPS);
	}

	@Override
	public void init() throws Exception {
		inicializarFechas();
		inicializarListBoxTerceros();
	}


	public void imprimir() {
		if (tipo_reporte.equalsIgnoreCase(ITiposReportesIPS.GLOSAS_EPS)) {
			imprimirGlosasIps();
			auxTitulo.setLabel("GLOSAS POR IPS");
		} else if (tipo_reporte
				.equalsIgnoreCase(ITiposReportesIPS.CONSOLIDADO_GLOSAS_TOTAL_EPS)) {
			imprimirConsolidadoTotalIps();
			auxTitulo.setLabel("CONSOLIDADO DE GLOSAS TOTAL POR IPS");
		}
	}

	private void imprimirConsolidadoTotalIps() {
		imprimirEnConjunto("ConsolidadoGlosasTotalEps");
	}

	private void imprimirGlosasIps() {
//       imprimirEnConjunto("GlosarEps");
		imprimirEnConjunto("AuditoriaCuentasMedicas");
	}

	private void imprimirEnConjunto(String nombre_metodo_reporte) {
		if (validarForm()) {
			Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", nombre_metodo_reporte);
			paramRequest.put("fecha_inicio", dtxFechaInicial.getValue());
			paramRequest.put("fecha_final", dtxFechaFinal.getValue());
			paramRequest.put("impreso_por", usuarios.getNombres() + " " + usuarios.getApellidos()); 
			if(grbAgrupado.isOpen()){
				paramRequest.put("eps", getEps());
			}
			paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()); 
			Utilidades.mostarReporte(paramRequest, this);
		}
	}
	
	private List<Administradora> getEps() {
		List<Administradora> administradoras = new ArrayList<Administradora>();
	    for (Map<String, Object> map_ : lista_datos_eps) {
			  Administradora administradora = (Administradora) map_.get(ADMINISTRADORA);
			  administradoras.add(administradora);
		}
		return administradoras;
	}

	private boolean validarForm() {
		boolean validar = true;
		String msj = "";
		if(chk_entidad_eps.isChecked() && rowEps.getChildren().isEmpty()){
			validar = false;
			msj = "Para imprimir el reporte por lo menos debe seleccionar una EPS";
		}
		if(!validar)MensajesUtil.mensajeAlerta("Advertencia", msj);
		return validar;
	}

	/*
	 * Metodos inicializadores..
	 */
	private void inicializarListBoxTerceros() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private void inicializarFechas() {
		dtxFechaInicial.setValue(L2HContraintDate.getAsInitOfMonth(Calendar
				.getInstance().getTime()));
		dtxFechaFinal.setValue(L2HContraintDate.getAsEndOfMonth(Calendar
				.getInstance().getTime()));
	}
	
	
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

	@Override
	public void onSeleccionarRegistro(Object registro) {
		if (registro instanceof Administradora) {
			onSeleccionarEps((Administradora) registro);
		}
	}
	
	private void onSeleccionarEps(Administradora registro) {
	      adicionarDetalleListaAdministradora(cargarPrestador(registro)); 
	}
	
	private Map<String, Object> cargarPrestador(Administradora administradora) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(ADMINISTRADORA, administradora); 
		return bean;
	}
	
	private void adicionarDetalleListaAdministradora(Map<String, Object> bean) {
		try {
			crearFilasAdministradora(bean); 
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	
	private void crearFilasAdministradora(final Map<String, Object> bean) {
		final Row row = new Row();
		lista_datos_eps.add(bean);
		final Administradora administradora = (Administradora) bean.get(ADMINISTRADORA);
		
		
		// cargamos contratos
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_administradora", administradora.getCodigo());
		
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
		toolbarbutton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				 Messagebox.show("¿Estas seguro que deseas eliminar estos registros?", "Advertencia", Messagebox.YES + Messagebox.NO,
							Messagebox.QUESTION, new EventListener<Event>() {
								@Override
								public void onEvent(Event event) throws Exception {
									if("onYes".equals(event.getName())){
									     lista_datos_eps.remove(bean);
										 rowEps.removeChild(row);
									}
								}
							});
			}
		});
		rowEps.appendChild(row); 
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if(registro instanceof Administradora){
			return celdasListitemAdministradora((Administradora)registro);
		}
		return null; 
	}
	
	private Object[] celdasListitemAdministradora(Administradora registro) {
		return new Object[]{registro.getCodigo(), registro.getNombre()};
	}
}
