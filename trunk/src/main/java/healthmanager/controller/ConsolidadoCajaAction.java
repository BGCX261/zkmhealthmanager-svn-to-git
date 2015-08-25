package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.service.ContratosService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.Utilidades;

public class ConsolidadoCajaAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(ConsolidadoCajaAction.class);
	
	private ContratosService contratosService;

	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_final;

	@View
	private Timebox timeHora_inicial;
	@View
	private Timebox timeHora_final;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxAseguradora;

	@View
	private Textbox tbxInfoAseguradora;

	@View
	private Toolbarbutton btnLimpiarAseguradora;

	@View
	private Listbox lbxPlan;

	@View
	private Listbox lbxCajero;

	@View
	private Checkbox chbAgrupar_turnos;
	
	@View
	private Listbox lbxFormato;
	
	private final String[] idsExcluyentes = new String[] {
			"btImprimir", "lbxFormato" };

	@Override
	public void init() throws Exception {
		
		listarCombos();
		parametrizarBandbox();
		FormularioUtil.limpiarComponentes(this, idsExcluyentes);
		
		Date fecha_inicial = dtbxFecha_inicial.getValue();
		Date fecha_final = dtbxFecha_final.getValue();
		
		GregorianCalendar auxFecha = new GregorianCalendar();
		auxFecha.setTimeInMillis(fecha_inicial.getTime());
		auxFecha.set(Calendar.HOUR_OF_DAY, 0);
		auxFecha.set(Calendar.MINUTE, 0);
		fecha_inicial.setTime(auxFecha.getTimeInMillis());
		
		auxFecha = new GregorianCalendar();
		auxFecha.setTimeInMillis(fecha_final.getTime());
		auxFecha.set(Calendar.HOUR_OF_DAY,23);
		auxFecha.set(Calendar.MINUTE, 59);
		fecha_final.setTime(auxFecha.getTimeInMillis());
		
		timeHora_inicial.setValue(fecha_inicial);
		timeHora_final.setValue(fecha_final);
		
		dtbxFecha_inicial.setValue(fecha_inicial);
		dtbxFecha_final.setValue(fecha_final);
	}

	public void listarCombos() {
		Utilidades.listarUsuarios(lbxCajero, codigo_empresa, codigo_sucursal,
				true, getServiceLocator());
		Utilidades.listarContratos(lbxPlan, "", true, false, codigo_empresa,
				codigo_sucursal, getServiceLocator());
		
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
								false, registro.getCodigo(),
								ConsolidadoCajaAction.this, contratosService);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						lbxPlan.setDisabled(true);
					}
				});
	}
	
	public void imprimir() throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Consolidado_caja");
		paramRequest.put("codigo_empresa", codigo_empresa);
		paramRequest.put("codigo_sucursal", codigo_sucursal);
//		paramRequest.put("tipo", "03");
		paramRequest.put("tipos", new String[]{"03", "05"});  
		paramRequest.put("fecha_inicial", new Timestamp(dtbxFecha_inicial.getValue().getTime()));
		paramRequest.put("fecha_final", new Timestamp(dtbxFecha_final.getValue().getTime()));
		paramRequest.put("codigo_administradora",bandboxAseguradora.getValue());
		paramRequest.put("id_plan", (lbxPlan.getSelectedItem().getValue() instanceof Contratos) ? ((Contratos)lbxPlan.getSelectedItem().getValue()).getId_plan() : 
			lbxPlan.getSelectedItem().getValue().toString());
		paramRequest.put("codigo_usuario", lbxCajero.getSelectedItem().getValue()
				.toString());
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());
		if(chbAgrupar_turnos.isChecked()){
			paramRequest.put("agrupar_turnos","");
		}
		
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

}
