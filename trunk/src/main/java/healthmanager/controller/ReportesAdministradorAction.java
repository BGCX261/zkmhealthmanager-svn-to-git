package healthmanager.controller;

import java.lang.reflect.Field;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Center;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IRoles;

public class ReportesAdministradorAction extends ZKWindow {
	
	@View private Tabbox tbbxMenu;
	@View private Tab tabFacturacion;
	@View private Tab tabInscripciones;
	@View private Tab tabFarmacia;
	@View private Tab tabMaestros;
	@View private Tab tabEstadisticas;
	@View private Tab tabOdontologia;
	@View private Tab tabAutorizaciones;
	@View private Listbox lbxFacturacion;
	@View private Listitem lbxGlosasIps;
	@View private Listitem lbxConsolidadoGlosasIps;
	@View private Listbox lbxInscripciones;
	@View private Listitem lbxCarnets;
	@View private Listitem lbxCertificado;
	@View private Listitem lbxAutorizacionTranslado;
	@View private Listitem lbxBeneficiario25;
	@View private Listitem lbxBeneficiario18;
	@View private Listitem lbxRetiro_beneficiario;
	@View private Listitem lbxTraslado;
	@View private Listitem lbxConbeneficiario;
	@View private Listitem lbxIbc;
	@View private Listbox lbxFarmacia;
	@View private Listitem lbxConsumo;
	@View private Listitem lbxOportunidad;
	@View private Listbox lbxMaestros;
	@View private Listitem lbxRedPrestadores;
	@View private Listitem lbxResolcuon1552;
	@View private Listbox lbxEstadisticas;
	@View private Listitem lbxConsolidado_caja;
	@View private Listitem lbxConsolidado_morbilidad;
	@View private Listitem lbxConsolidado_consolidado;
	@View private Listitem lbxConsolidado_valorfacturado;
	@View private Listitem lbxResultado_laboratorios_fuera_rango;
	@View private Listbox lbxOdontologia;
	@View private Listitem lbxPacienteActividades;
	@View private Listitem lbxProcedimientoOdontolog;
	@View private Listitem lbxPacientesAtendidosPorEdad;
	@View private Iframe iframeContenido;
//	@View private Caption capNombreReporte;
	@View private Listbox lbxAutorizaciones;
	@View private Listitem lbxAutorizacionesRealizadas;
//	@View private Listitem lbxExportarFacturas;
	
	@View private Center ctnCentro;
	
	@Override
	public void init() throws Exception {
		ocultarMenusNoDisponibles();
		validamosMenuRespecotoRol(); 
	}
 
	private void ocultarMenusNoDisponibles() {
		try {
			if(sucursal.getTipo().equals(
					IConstantes.TIPOS_SUCURSAL_CAJA_PREV)){
				if (rol_usuario.equals(IRoles.AUDITOR)
						|| rol_usuario.equals(IRoles.CONTRATACIONES)
						|| rol_usuario.equals(IRoles.AUTORIZACIONES_EXTERNAS)) { 
					ocultar();
				}
			}else{
				if (rol_usuario.equals(IRoles.ESTADISTICO)){
					ocultar();
					tabEstadisticas.setVisible(true);
					lbxEstadisticas.setVisible(true);
					habilitamosElUltimoTab();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void ocultar() throws Exception {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object object = field.get(this);
			if (object instanceof Listitem || object instanceof Listbox
					|| object instanceof Tab) {
				((Component) object).setVisible(false);
			}
		}
	}

	private void validamosMenuRespecotoRol() {
		if(sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)){
			if(rol_usuario.equals(IRoles.AUDITOR)){
	        	 lbxGlosasIps.setLabel("GLOSAS POR PRESTADOR");
	        	 lbxConsolidadoGlosasIps.setLabel("CONSOLIDADO GLOSAS POR PRESTADOR");
	        	 
	        	 lbxFacturacion.setVisible(true); 
	        	 lbxGlosasIps.setVisible(true);
	        	 lbxConsolidadoGlosasIps.setVisible(true);
	        	 tabFacturacion.setVisible(true);
	        }else if(rol_usuario.equals(IRoles.CONTRATACIONES)){
	        	 lbxRedPrestadores.setVisible(true); 
	        	 tabMaestros.setVisible(true); 
	        	 lbxMaestros.setVisible(true); 
	        }else if(rol_usuario.equals(IRoles.AUTORIZACIONES_EXTERNAS)){
	        	 tabAutorizaciones.setVisible(true); 
	        	 lbxAutorizaciones.setVisible(true);
	        	 lbxAutorizacionesRealizadas.setVisible(true); 
	        }
			habilitamosElUltimoTab();
		}
	}
	
	/**
	 * Este metodo me permite, seleccionar el ultimo primer tab visible 
	 * */ 
	private void habilitamosElUltimoTab() {
		List<Component> components = tbbxMenu.getTabs().getChildren();
		for (Component component : components) {
           if(component instanceof Tab){
        	   Tab tab = (Tab) component;
        	   if(tab.isVisible()){
        		   tab.setSelected(true); 
        		   break;
        	   }
           }
		}
	}

	public void seleccionar(Listbox listbox, Tab tab){
		Listitem listitem = listbox.getSelectedItem();
		if(listitem != null){
			iframeContenido.setSrc("/pages/" + listitem.getValue().toString()); 
			listitem.setSelected(false);
			ctnCentro.setTitle(tab.getLabel() + " / " + listitem.getLabel().toUpperCase()); 
		}
	}

}
