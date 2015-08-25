package healthmanager.controller;

import healthmanager.modelo.bean.Elemento;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Resolucion1552 extends ZKWindow{

	@View Checkbox chRip;
	@View Checkbox chReportes;
	@View Listbox lbxAnios;
	@View Listbox lbxMeses;
	@View Listbox lbxMotivoConsulta;
	
	@Override
	public void init() {
		initYear();
		initMotivoConsulta();
	}
	
	
	public void generar() throws Exception{
			try {
				Map paramRequest = new HashMap();
				paramRequest.put("name_report", "Resolucion1552");
				paramRequest.put("rip", chRip.isChecked());
				paramRequest.put("report", chReportes.isChecked());
				paramRequest.put("contentin", getInFromList()); 
				paramRequest.put("mes_anio", getMesYAnio()); 
				
				Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
				final Window window = (Window)componente;
				window.setWidth("100%");
				window.setHeight("100%"); 
				window.doModal();
			} catch (Exception e) {
               MensajesUtil.mensajeError(e, null, this);
			}
	}

	private Set<Listitem> getInFromList() throws Exception{
		Set<Listitem> set =  lbxMotivoConsulta.getSelectedItems();
		if(set.isEmpty())
			throw new Exception("Dede seleccionar un tipo de consulta");
		return set;
	}

	
	private String getMesYAnio(){
		int mes = lbxMeses.getSelectedIndex() + 1;
		String m = mes < 10 ? "0" + mes : mes + "";
		return m + "-" + lbxAnios.getSelectedItem().getValue().toString();
	}

	private void initMotivoConsulta() {
		List<Elemento> elementos = this.getServiceLocator()
		.getElementoService().listar("motivo_consulta_hc");
        Utilidades.listarElementoListboxFromType(lbxMotivoConsulta, false, elementos, false, false);
	}

	private void initYear() {
		Utilidades.listarAnios(lbxAnios, false, 7, true, anio);
		lbxMeses.setSelectedIndex(mes.intValue()); 
	}

}
