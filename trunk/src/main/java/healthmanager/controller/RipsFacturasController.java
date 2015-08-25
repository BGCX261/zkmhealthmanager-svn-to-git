package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.ContratosService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

import contaweb.modelo.service.FacturacionService;


public class RipsFacturasController extends ZKWindow {

	/* facturas*/
	private String ct;
	private String af;
	/* detalles */
	private String ac;
	private String ap;
	private String am;
	private String at;
	
	/* archivos adicionales */
	private String us;
	private String ad;
	private String ah;
	private String au;
	private String an;
	
	
    private AdministradoraService administradoraService;
    private ContratosService contratosService;
    private FacturacionService facturacionService;
	
	/* cargamos componentes */
	@View private Textbox nameAF;
	@View private Textbox nameAD;
	@View private Textbox nameAC;
	@View private Textbox nameAP;
	@View private Textbox nameAM;
	@View private Textbox nameAT;
	@View private Textbox nameCT;
	
	/* campo adicionales */
	@View private Textbox nameUS;
	@View private Textbox nameAH;
	@View private Textbox nameAU;
	@View private Textbox nameAN;
	
	
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_administradora;
	@View private Textbox tbxInfoAseguradora;
	@View private Toolbarbutton btnLimpiarAseguradora;
	
	
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_contrato;
	@View private Textbox tbxInfoContrato;
	@View private Toolbarbutton btnLimpiarContrato;
	
	@View private Datebox dtbxFecha_contabilizacion;
	
	
	public void generarFactura() throws Exception{
		try {
			if(validarForm()){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ct", ct); 
				params.put("af", af);
				params.put("ac", ac);
				params.put("ap", ap);
				params.put("am", am);
				params.put("at", at);
				
				/* campo adicionales */
				params.put("us", us);
				params.put("ad", ad);
				params.put("ah", ah);
				params.put("au", au);
				params.put("an", an);
				
				/* cargamos datos de usuario */
				params.put("usuario", usuarios);
				params.put("admin", tbxCodigo_administradora.getRegistroSeleccionado());
				params.put("contrato", tbxCodigo_contrato.getRegistroSeleccionado());
				params.put("fecha_contabilizacion", getFechaContabilizacion());  
				
				facturacionService.crearFacturasDesdeRips(params);
				MensajesUtil.mensajeInformacion("Informacion", "Facturas generadas exitosamente");
			}
		} catch (Exception e) {  
			MensajesUtil.mensajeError(e, null, this); 
		}
	}
	
	private Timestamp getFechaContabilizacion() {
		if(dtbxFecha_contabilizacion.getValue() != null){
			return new Timestamp(dtbxFecha_contabilizacion.getValue().getTime());
		}
		return null;
	}

	private void parametrizarBandbox(){
		parametrizarAdministradora();
		parametrizarContrato();
	}
	
	private void parametrizarContrato(){
		tbxCodigo_contrato.inicializar(tbxInfoContrato,
				btnLimpiarContrato);
		tbxCodigo_contrato
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Contratos>() {
					@Override
					public void renderListitem(Listitem listitem,
							Contratos registro) {
						listitem.appendChild(new Listcell(registro.getNro_contrato() != null ? registro.getNro_contrato() : registro.getId_plan() + ""));
						listitem.appendChild(new Listcell(registro.getNombre()));
					}

					@Override
					public List<Contratos> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("codigo_administradora", tbxCodigo_administradora.getValue());
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return contratosService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Contratos registro) {
						bandbox.setValue((registro.getNro_contrato() != null && !registro
								.getNro_contrato().trim().isEmpty()) ? registro
								.getNro_contrato() : registro.getId_plan());
						textboxInformacion.setValue(registro.getNombre() + "");
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
					}
				});
	}
	
	
	private void parametrizarAdministradora(){
		tbxCodigo_administradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		tbxCodigo_administradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNit()));
						listitem.appendChild(new Listcell(registro.getNombre()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("tercerizada", "S");
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return administradoraService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						deshabilitarSeleccionContrato(false); 
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbxCodigo_contrato.limpiarSeleccion(true); 
						deshabilitarSeleccionContrato(true); 
					}
				});
	}
	 
	public void limpiar(){
		nameCT.setStyle("text-transform:uppercase;background-color:white");
		nameAF.setStyle("text-transform:uppercase;background-color:white");
		nameAC.setStyle("text-transform:uppercase;background-color:white");
		nameAP.setStyle("text-transform:uppercase;background-color:white");
		nameAM.setStyle("text-transform:uppercase;background-color:white");
		nameAT.setStyle("text-transform:uppercase;background-color:white"); 
		
		
		// limpiamos los campos
		nameCT.setValue(""); 
		nameAF.setValue("");
		nameAC.setValue("");
		nameAP.setValue("");
		nameAM.setValue("");
		nameAT.setValue("");
		nameUS.setValue("");
		nameAH.setValue("");
		nameAU.setValue("");
		nameAN.setValue("");
		nameAD.setValue(""); 
		
		ct = af =  ac =  ap =
		am = at = us = ad =
		ah = au = an = "";
		
	}
	
	private void deshabilitarSeleccionContrato(boolean estado){
		tbxCodigo_contrato.setDisabled(estado);
		tbxInfoContrato.setDisabled(estado); 
	}
	
	private boolean validarForm() throws Exception{
		
		nameCT.setStyle("text-transform:uppercase;background-color:white");
		nameAF.setStyle("text-transform:uppercase;background-color:white");
		nameAC.setStyle("text-transform:uppercase;background-color:white");
		nameAP.setStyle("text-transform:uppercase;background-color:white");
		nameAM.setStyle("text-transform:uppercase;background-color:white");
		nameAT.setStyle("text-transform:uppercase;background-color:white"); 
		
		try {
			FormularioUtil.validarCamposObligatorios(tbxCodigo_administradora,
					tbxCodigo_contrato, dtbxFecha_contabilizacion);  
		} catch (WrongValueException e) {
			return false;
		}
		
		if(ct == null){
			nameCT.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			throw new ValidacionRunTimeException("CT es requerido");
		}
		
		if(af == null){
			nameAF.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			throw new ValidacionRunTimeException("AF es requerido");
		}
		
		/* si todos son nulos */
		if (ac == null && am == null && ap == null && at == null) {
			nameAC.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			nameAP.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			nameAM.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			nameAT.setStyle("text-transform:uppercase;background-color:#F6BBBE"); 
			/* campos adicionales */
//			nameUS.setStyle("text-transform:uppercase;background-color:#F6BBBE"); 
//			nameAH.setStyle("text-transform:uppercase;background-color:#F6BBBE"); 
//			nameAU.setStyle("text-transform:uppercase;background-color:#F6BBBE"); 
//			nameAN.setStyle("text-transform:uppercase;background-color:#F6BBBE"); 
			throw new ValidacionRunTimeException("Se debe cargar al menos uno de estos archivos");
		}
		return true;
	}

	/* getter and setter*/
	
	public String getAf() {
		return af;
	}


	public void setAf(String af) {
		this.af = af;
	}


	public String getAc() {
		return ac;
	}


	public void setAc(String ac) {
		this.ac = ac;
	}


	public String getAp() {
		return ap;
	}


	public void setAp(String ap) {
		this.ap = ap;
	}


	public String getAm() {
		return am;
	}


	public void setAm(String am) {
		this.am = am;
	}


	public String getAt() {
		return at;
	}


	public void setAt(String at) {
		this.at = at;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}
	
	/* getter and setter de adionales */
	

	@Override
	public void init() throws Exception {
		parametrizarBandbox();
	}

	public String getUs() {
		return us;
	}

	public void setUs(String us) {
		this.us = us;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getAh() {
		return ah;
	}

	public void setAh(String ah) {
		this.ah = ah;
	}

	public String getAu() {
		return au;
	}

	public void setAu(String au) {
		this.au = au;
	}

	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}
}
