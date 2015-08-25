/*
 * bancoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.controller;

import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import contaweb.modelo.bean.*;

public class VerContabilizacionAction extends ZKWindow{

	private static Logger log = Logger.getLogger(VerContabilizacionAction.class);
	
	//Componentes //
	@View private Listbox lbxDetalle;
	@View private Doublebox dbxDebitos;
	@View private Doublebox dbxCreditos;
	@View private Doublebox dbxSaldos;
	
	
	public void init(){
		cargarDatosSesion();
		try {
			initVer_contabilizacion();
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
		}
	}
	
	
	public void initVer_contabilizacion()throws Exception{
		//HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		try {
			lbxDetalle.getItems().clear();
			
			Map parametros = Executions.getCurrent().getArg();
			////log.info("parametros: "+parametros);
			
			Nota_contable nota_contable = (Nota_contable) parametros.get("nota_contable");
			double sum_debito=0,sum_credito=0;
			////log.info("parameters: "+parameters);
			List<Detalle_nota> lista_notas = nota_contable.getLista_detalle();
			for (Detalle_nota detalle : lista_notas) {
				
				Puc puc = new Puc();
				puc.setCodigo_empresa(detalle.getCodigo_empresa());
				puc.setCodigo_sucursal(detalle.getCodigo_sucursal());
				puc.setCodigo_cuenta(detalle.getCodigo_cuenta());
				puc = getServiceLocator().getPucService().consultar(puc);
				
				Centro_costo centro_costo = new Centro_costo();
				centro_costo.setCodigo_empresa(detalle.getCodigo_empresa());
				centro_costo.setCodigo_sucursal(detalle.getCodigo_sucursal());
				centro_costo.setCodigo(detalle.getC_costo());
				centro_costo = getServiceLocator().getCentro_costoService().consultar(centro_costo);
				
				Listitem listitem = new Listitem();
				
				Listcell listcell = new Listcell(detalle.getCodigo_cuenta());
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				listitem.appendChild(listcell);
				
				listcell = new Listcell((puc!=null?puc.getNombre():""));
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				final Doublebox textbox_debito = new Doublebox(detalle.getDebito());
				textbox_debito.setHflex("1");
				textbox_debito.setFormat("#,##0.00");
				textbox_debito.setInplace(true);
				textbox_debito.setStyle("text-align:right;");
				textbox_debito.setReadonly(true);
				listcell.appendChild(textbox_debito);
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				final Doublebox textbox_credito = new Doublebox(detalle.getCredito());
				textbox_credito.setHflex("1");
				textbox_credito.setFormat("#,##0.00");
				textbox_credito.setInplace(true);
				textbox_credito.setStyle("text-align:right;");
				textbox_credito.setReadonly(true);
				listcell.appendChild(textbox_credito);
				listitem.appendChild(listcell);
				
				listcell = new Listcell(detalle.getAbona());
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				listitem.appendChild(listcell);
				
				listcell = new Listcell((centro_costo!=null?centro_costo.getNombre():""));
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				listitem.appendChild(listcell);
				
				sum_debito+=(textbox_debito.getValue()!=null?textbox_debito.getValue():0);
				sum_credito+=(textbox_credito.getValue()!=null?textbox_credito.getValue():0);
				
				lbxDetalle.appendChild(listitem);
			}
			dbxDebitos.setValue(sum_debito);
			dbxCreditos.setValue(sum_credito);
			dbxSaldos.setValue(sum_debito-sum_credito);
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
}