/*
 * riesgo_intervencionAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Registro_detalle;
import healthmanager.modelo.bean.Riesgo_intervencion;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
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
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;

public class Riesgo_intervencionAction extends Window implements AfterCompose{

	private static Logger log = Logger.getLogger(Riesgo_intervencionAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
	//private Permisos_sc permisos_sc;
	
	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;
	private Registro_detalle registro;
	
	private Window form;
	
	//Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;
	
	private Textbox tbxPeso;
	private Datebox dtbxFecha_talla;
	private Datebox dtbxFecha_peso;
	private Textbox tbxTalla;
	private Datebox dtbxFecha_parto;
	private Textbox tbxEdad_gestacional;
	private Listbox lbxBcg;
	private Listbox lbxHepatitis_b;
	private Listbox lbxPentavalente;
	private Listbox lbxPollo;
	private Listbox lbxDpt;
	private Listbox lbxRotavirus;
	private Listbox lbxNeumococo;
	private Listbox lbxInfluenza;
	private Listbox lbxFiebre_amarilla;
	private Listbox lbxHepatitis_a;
	private Listbox lbxTriple_viral;
	private Listbox lbxVph;
	private Listbox lbxTd_tt;
	private Listbox lbxPlaca_bacteriana;
	private Datebox dtbxFecha_atencion_parto;
	private Datebox dtbxFecha_salida_atencion;
	private Datebox dtbxFecha_consejeria_lactancia;
	private Datebox dtbxFecha_recien_nacido;
	private Datebox dtbxFecha_planificacion_familiar;
	private Listbox lbxSuministro_anticonceptivo;
	private Datebox dtbxFecha_suministro_anticonceptivo;
	private Datebox dtbxFecha_control_prenatal;
	private Textbox tbxControl_prenatal;
	private Datebox dtbxUltimo_control_prenatal;
	private Listbox lbxSuministro_acido_folico;
	private Listbox lbxSuministro_sulfato;
	private Listbox lbxSuministro_carbonato;
	private Datebox dtbxFecha_agudeza_visual;
	private Datebox dtbxFecha_oftalmologia;
	private Datebox dtbxFecha_diagnistico_desnutricion;
	private Datebox dtbxFecha_consulta_maltrato;
	private Datebox dtbxFecha_consulta_violacion;
	private Datebox dtbxFecha_nutricion;
	private Datebox dtbxFecha_psicologia;
	private Datebox dtbxFecha_crecimiento;
	private Listbox lbxSuministro_sulfato_ultima;
	private Listbox lbxSuministro_vitamina_a;
	private Datebox dtbxFecha_consulta_joven;
	private Datebox dtbxFecha_consulta_adulto;
	private Textbox tbxPreservativos;
	private Datebox dtbxFecha_asesoria_vih;
	private Datebox dtbxFecha_asesoria_vih_pos;
	private Listbox lbxDiagnostico;
	private Datebox dtbxFecha_antigeno_hepatitis_b;
	private Listbox lbxResultado_antigeno_hepatitis_b;
	private Datebox dtbxFecha_serologia;
	private Listbox lbxResultado_serologia;
	private Datebox dtbxFecha_toma_vih;
	private Listbox lbxResultado_vih;
	private Datebox dtbxFecha_tsh;
	private Listbox lbxResultado_tsh;
	private Listbox lbxTamizaje;
	private Datebox dtbxFecha_citologia_cervico;
	private Listbox lbxResultado_citologia_cervico;
	private Listbox lbxCalidad_citologia_cervico;
	private Textbox tbxCodigo_ips_citologia;
	private Datebox dtbxFecha_colposcopia;
	private Textbox tbxCodigo_ips_colposcopia;
	private Datebox dtbxFecha_biopsia;
	private Listbox lbxResultado_biopsia;
	private Textbox tbxCodigo_ips_biopsia;
	private Datebox dtbxFecha_mamografia;
	private Listbox lbxResultado_mamografia;
	private Textbox tbxCodigo_ips_mamografia;
	private Datebox dtbxFecha_biopsia_seno;
	private Datebox dtbxFecha_resultado_biopsia_seno;
	private Listbox lbxBipsia_seno;
	private Textbox tbxCodigo_ips_biopsia_seno;
	private Datebox dtbxFecha_hemoglobina;
	private Doublebox dbxHemoglibina;
	private Datebox dtbxFecha_glisemia;
	private Datebox dtbxFecha_creatinina;
	private Doublebox dbxCreatinina;
	private Datebox dtbxFecha_hemoglobina_glicosilada;
	private Doublebox dbxHemoglobina_glicosilada;
	private Datebox dtbxFecha_microalbuminuria;
	private Datebox dtbxFecha_hdl;
	private Datebox dtbxFecha_basiloscopia;
	private Listbox lbxBasiloscopia;
	private Listbox lbxTratamiento_hipotiroidismo;
	private Listbox lbxTratamiento_sifilis_gestional;
	private Listbox lbxTratamiento_sifilis_congenita;
	private Listbox lbxTratamiento_lepra;
	private Datebox dtbxFecha_leismaniasis;
	
	@Override
	public void afterCompose(){
		loadComponents();
		cargarDatosSesion();
		listarCombos();
		
		try {
			initRiesgo_intervencion();
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
		}
	}
	
	public void loadComponents(){
		form = (Window) this.getFellow("formRiesgo_intervencion");
		
		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox)form.getFellow("tbxValue");
		tbxAccion = (Textbox)form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");
		
		tbxPeso = (Textbox)form.getFellow("tbxPeso");
		dtbxFecha_talla = (Datebox)form.getFellow("dtbxFecha_talla");
		dtbxFecha_peso = (Datebox)form.getFellow("dtbxFecha_peso");
		tbxTalla = (Textbox)form.getFellow("tbxTalla");
		dtbxFecha_parto = (Datebox)form.getFellow("dtbxFecha_parto");
		tbxEdad_gestacional = (Textbox)form.getFellow("tbxEdad_gestacional");
		lbxBcg = (Listbox)form.getFellow("lbxBcg");
		lbxHepatitis_b = (Listbox)form.getFellow("lbxHepatitis_b");
		lbxPentavalente = (Listbox)form.getFellow("lbxPentavalente");
		lbxPollo = (Listbox)form.getFellow("lbxPollo");
		lbxDpt = (Listbox)form.getFellow("lbxDpt");
		lbxRotavirus = (Listbox)form.getFellow("lbxRotavirus");
		lbxNeumococo = (Listbox)form.getFellow("lbxNeumococo");
		lbxInfluenza = (Listbox)form.getFellow("lbxInfluenza");
		lbxFiebre_amarilla = (Listbox)form.getFellow("lbxFiebre_amarilla");
		lbxHepatitis_a = (Listbox)form.getFellow("lbxHepatitis_a");
		lbxTriple_viral = (Listbox)form.getFellow("lbxTriple_viral");
		lbxVph = (Listbox)form.getFellow("lbxVph");
		lbxTd_tt = (Listbox)form.getFellow("lbxTd_tt");
		lbxPlaca_bacteriana = (Listbox)form.getFellow("lbxPlaca_bacteriana");
		dtbxFecha_atencion_parto = (Datebox)form.getFellow("dtbxFecha_atencion_parto");
		dtbxFecha_salida_atencion = (Datebox)form.getFellow("dtbxFecha_salida_atencion");
		dtbxFecha_consejeria_lactancia = (Datebox)form.getFellow("dtbxFecha_consejeria_lactancia");
		dtbxFecha_recien_nacido = (Datebox)form.getFellow("dtbxFecha_recien_nacido");
		dtbxFecha_planificacion_familiar = (Datebox)form.getFellow("dtbxFecha_planificacion_familiar");
		lbxSuministro_anticonceptivo = (Listbox)form.getFellow("lbxSuministro_anticonceptivo");
		dtbxFecha_suministro_anticonceptivo = (Datebox)form.getFellow("dtbxFecha_suministro_anticonceptivo");
		dtbxFecha_control_prenatal = (Datebox)form.getFellow("dtbxFecha_control_prenatal");
		tbxControl_prenatal = (Textbox)form.getFellow("tbxControl_prenatal");
		dtbxUltimo_control_prenatal = (Datebox)form.getFellow("dtbxUltimo_control_prenatal");
		lbxSuministro_acido_folico = (Listbox)form.getFellow("lbxSuministro_acido_folico");
		lbxSuministro_sulfato = (Listbox)form.getFellow("lbxSuministro_sulfato");
		lbxSuministro_carbonato = (Listbox)form.getFellow("lbxSuministro_carbonato");
		dtbxFecha_agudeza_visual = (Datebox)form.getFellow("dtbxFecha_agudeza_visual");
		dtbxFecha_oftalmologia = (Datebox)form.getFellow("dtbxFecha_oftalmologia");
		dtbxFecha_diagnistico_desnutricion = (Datebox)form.getFellow("dtbxFecha_diagnistico_desnutricion");
		dtbxFecha_consulta_maltrato = (Datebox)form.getFellow("dtbxFecha_consulta_maltrato");
		dtbxFecha_consulta_violacion = (Datebox)form.getFellow("dtbxFecha_consulta_violacion");
		dtbxFecha_nutricion = (Datebox)form.getFellow("dtbxFecha_nutricion");
		dtbxFecha_psicologia = (Datebox)form.getFellow("dtbxFecha_psicologia");
		dtbxFecha_crecimiento = (Datebox)form.getFellow("dtbxFecha_crecimiento");
		lbxSuministro_sulfato_ultima = (Listbox)form.getFellow("lbxSuministro_sulfato_ultima");
		lbxSuministro_vitamina_a = (Listbox)form.getFellow("lbxSuministro_vitamina_a");
		dtbxFecha_consulta_joven = (Datebox)form.getFellow("dtbxFecha_consulta_joven");
		dtbxFecha_consulta_adulto = (Datebox)form.getFellow("dtbxFecha_consulta_adulto");
		tbxPreservativos = (Textbox)form.getFellow("tbxPreservativos");
		dtbxFecha_asesoria_vih = (Datebox)form.getFellow("dtbxFecha_asesoria_vih");
		dtbxFecha_asesoria_vih_pos = (Datebox)form.getFellow("dtbxFecha_asesoria_vih_pos");
		lbxDiagnostico = (Listbox)form.getFellow("lbxDiagnostico");
		dtbxFecha_antigeno_hepatitis_b = (Datebox)form.getFellow("dtbxFecha_antigeno_hepatitis_b");
		lbxResultado_antigeno_hepatitis_b = (Listbox)form.getFellow("lbxResultado_antigeno_hepatitis_b");
		dtbxFecha_serologia = (Datebox)form.getFellow("dtbxFecha_serologia");
		lbxResultado_serologia = (Listbox)form.getFellow("lbxResultado_serologia");
		dtbxFecha_toma_vih = (Datebox)form.getFellow("dtbxFecha_toma_vih");
		lbxResultado_vih = (Listbox)form.getFellow("lbxResultado_vih");
		dtbxFecha_tsh = (Datebox)form.getFellow("dtbxFecha_tsh");
		lbxResultado_tsh = (Listbox)form.getFellow("lbxResultado_tsh");
		lbxTamizaje = (Listbox)form.getFellow("lbxTamizaje");
		dtbxFecha_citologia_cervico = (Datebox)form.getFellow("dtbxFecha_citologia_cervico");
		lbxResultado_citologia_cervico = (Listbox)form.getFellow("lbxResultado_citologia_cervico");
		lbxCalidad_citologia_cervico = (Listbox)form.getFellow("lbxCalidad_citologia_cervico");
		tbxCodigo_ips_citologia = (Textbox)form.getFellow("tbxCodigo_ips_citologia");
		dtbxFecha_colposcopia = (Datebox)form.getFellow("dtbxFecha_colposcopia");
		tbxCodigo_ips_colposcopia = (Textbox)form.getFellow("tbxCodigo_ips_colposcopia");
		dtbxFecha_biopsia = (Datebox)form.getFellow("dtbxFecha_biopsia");
		lbxResultado_biopsia = (Listbox)form.getFellow("lbxResultado_biopsia");
		tbxCodigo_ips_biopsia = (Textbox)form.getFellow("tbxCodigo_ips_biopsia");
		dtbxFecha_mamografia = (Datebox)form.getFellow("dtbxFecha_mamografia");
		lbxResultado_mamografia = (Listbox)form.getFellow("lbxResultado_mamografia");
		tbxCodigo_ips_mamografia = (Textbox)form.getFellow("tbxCodigo_ips_mamografia");
		dtbxFecha_biopsia_seno = (Datebox)form.getFellow("dtbxFecha_biopsia_seno");
		dtbxFecha_resultado_biopsia_seno = (Datebox)form.getFellow("dtbxFecha_resultado_biopsia_seno");
		lbxBipsia_seno = (Listbox)form.getFellow("lbxBipsia_seno");
		tbxCodigo_ips_biopsia_seno = (Textbox)form.getFellow("tbxCodigo_ips_biopsia_seno");
		dtbxFecha_hemoglobina = (Datebox)form.getFellow("dtbxFecha_hemoglobina");
		dbxHemoglibina = (Doublebox)form.getFellow("dbxHemoglibina");
		dtbxFecha_glisemia = (Datebox)form.getFellow("dtbxFecha_glisemia");
		dtbxFecha_creatinina = (Datebox)form.getFellow("dtbxFecha_creatinina");
		dbxCreatinina = (Doublebox)form.getFellow("dbxCreatinina");
		dtbxFecha_hemoglobina_glicosilada = (Datebox)form.getFellow("dtbxFecha_hemoglobina_glicosilada");
		dbxHemoglobina_glicosilada = (Doublebox)form.getFellow("dbxHemoglobina_glicosilada");
		dtbxFecha_microalbuminuria = (Datebox)form.getFellow("dtbxFecha_microalbuminuria");
		dtbxFecha_hdl = (Datebox)form.getFellow("dtbxFecha_hdl");
		dtbxFecha_basiloscopia = (Datebox)form.getFellow("dtbxFecha_basiloscopia");
		lbxBasiloscopia = (Listbox)form.getFellow("lbxBasiloscopia");
		lbxTratamiento_hipotiroidismo = (Listbox)form.getFellow("lbxTratamiento_hipotiroidismo");
		lbxTratamiento_sifilis_gestional = (Listbox)form.getFellow("lbxTratamiento_sifilis_gestional");
		lbxTratamiento_sifilis_congenita = (Listbox)form.getFellow("lbxTratamiento_sifilis_congenita");
		lbxTratamiento_lepra = (Listbox)form.getFellow("lbxTratamiento_lepra");
		dtbxFecha_leismaniasis = (Datebox)form.getFellow("dtbxFecha_leismaniasis");
		
	}
	
	public void initRiesgo_intervencion()throws Exception{
//		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		try{
			
			accionForm(true, "registrar");
			
			Map mapRegistro = Executions.getCurrent().getArg();
			if(mapRegistro!=null){
				if(mapRegistro.get("codigo_registro")!=null){
					String codigo_registro = (String) mapRegistro.get("codigo_registro");
					String codigo_detalle = (String) mapRegistro.get("codigo_detalle");
					registro = new Registro_detalle();
					registro.setCodigo_empresa(empresa.getCodigo_empresa());
					registro.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					registro.setCodigo_registro(codigo_registro);
					registro.setCodigo_detalle(codigo_detalle);
					//log.info("antes: "+registro);
					registro = getServiceLocator().getRegistro_detalleService().consultar(registro);
					//log.info("despues: "+registro);
				}
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	public void cargarDatosSesion(){
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		
		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}
	
	public void listarCombos(){
		listarParameter();
		listarElementoListbox(lbxBcg,true);
		listarElementoListbox(lbxHepatitis_b,true);
		listarElementoListbox(lbxPentavalente,true);
		listarElementoListbox(lbxPollo,true);
		listarElementoListbox(lbxDpt,true);
		listarElementoListbox(lbxRotavirus,true);
		listarElementoListbox(lbxNeumococo,true);
		listarElementoListbox(lbxInfluenza,true);
		listarElementoListbox(lbxFiebre_amarilla,true);
		listarElementoListbox(lbxHepatitis_a,true);
		listarElementoListbox(lbxTriple_viral,true);
		listarElementoListbox(lbxVph,true);
		listarElementoListbox(lbxTd_tt,true);
		listarElementoListbox(lbxPlaca_bacteriana,true);
		listarElementoListbox(lbxSuministro_anticonceptivo,true);
		listarElementoListbox(lbxSuministro_acido_folico,true);
		listarElementoListbox(lbxSuministro_sulfato,true);
		listarElementoListbox(lbxSuministro_carbonato,true);
		listarElementoListbox(lbxSuministro_sulfato_ultima,true);
		listarElementoListbox(lbxSuministro_vitamina_a,true);
		listarElementoListbox(lbxDiagnostico,true);
		listarElementoListbox(lbxResultado_antigeno_hepatitis_b,true);
		listarElementoListbox(lbxResultado_serologia,true);
		listarElementoListbox(lbxResultado_vih,true);
		listarElementoListbox(lbxResultado_tsh,true);
		listarElementoListbox(lbxTamizaje,true);
		listarElementoListbox(lbxResultado_citologia_cervico,true);
		listarElementoListbox(lbxCalidad_citologia_cervico,true);
		listarElementoListbox(lbxResultado_biopsia,true);
		listarElementoListbox(lbxResultado_mamografia,true);
		listarElementoListbox(lbxBipsia_seno,true);
		listarElementoListbox(lbxBasiloscopia,true);
		listarElementoListbox(lbxTratamiento_hipotiroidismo,true);
		listarElementoListbox(lbxTratamiento_sifilis_gestional,true);
		listarElementoListbox(lbxTratamiento_sifilis_congenita,true);
		listarElementoListbox(lbxTratamiento_lepra,true);
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_registro");
		listitem.setLabel("Codigo_registro");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("codigo_detalle");
		listitem.setLabel("Codigo_detalle");
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
		groupboxEditar.setVisible(sw);
		
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
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent).setText(((Textbox) abstractComponent).getText().trim().toUpperCase());
				} 
			}
		}
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection){
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")){
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals("lbxParameter")){
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
				((Radio)((Radiogroup) abstractComponent).getFirstChild()).setChecked(true);
			}
		}
	/*	if(permisos_sc.getPermiso_crear()){
			((Button)form.getFellow("btGuardar")).setDisabled(false);
			((Button)form.getFellow("btGuardar")).setImage("/images/Save16.gif");
		}else{
			((Button)form.getFellow("btGuardar")).setDisabled(true);
			((Button)form.getFellow("btGuardar")).setImage("/images/Save16_disabled.gif");
		}*/
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
			String codigo_empresa = empresa.getCodigo_empresa();
			String codigo_sucursal = sucursal.getCodigo_sucursal();
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			getServiceLocator().getRiesgo_intervencionService().setLimit("limit 25 offset 0");
			
			List<Riesgo_intervencion> lista_datos = getServiceLocator().getRiesgo_intervencionService().listar(parameters);
			rowsResultado.getChildren().clear();
			
			for (Riesgo_intervencion riesgo_intervencion : lista_datos) {
				rowsResultado.appendChild(crearFilas(riesgo_intervencion, this));
			}
			
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			
			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Riesgo_intervencion riesgo_intervencion = (Riesgo_intervencion)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
			/*	if(!permisos_sc.getPermiso_modificar()){
					((Button)form.getFellow("btGuardar")).setDisabled(true);
					((Button)form.getFellow("btGuardar")).setImage("/images/Save16_disabled.gif");
				}*/
				mostrarDatos(riesgo_intervencion);
			}
		});
		hbox.appendChild(img);
		
		img = new Image();
	//	img.setVisible((permisos_sc.getPermiso_eliminar()?true:false));
		img.setSrc("/images/eliminar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(riesgo_intervencion);
								buscarDatos();
							}
						}
					});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public void guardarDatos()throws Exception{
		try {
			setUpperCase();
			if(validarForm()){
				//Cargamos los componentes //
				
				Riesgo_intervencion riesgo_intervencion = new Riesgo_intervencion();
				riesgo_intervencion.setCodigo_empresa(empresa.getCodigo_empresa());
				riesgo_intervencion.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				riesgo_intervencion.setCodigo_registro(registro.getCodigo_registro());
				riesgo_intervencion.setCodigo_detalle(registro.getCodigo_detalle());
				riesgo_intervencion.setPeso(tbxPeso.getValue());
				riesgo_intervencion.setFecha_talla(new Timestamp(dtbxFecha_talla.getValue().getTime()));
				riesgo_intervencion.setFecha_peso(new Timestamp(dtbxFecha_peso.getValue().getTime()));
				riesgo_intervencion.setTalla(tbxTalla.getValue());
				riesgo_intervencion.setFecha_parto(new Timestamp(dtbxFecha_parto.getValue().getTime()));
				riesgo_intervencion.setEdad_gestacional((tbxEdad_gestacional.getValue()));
				riesgo_intervencion.setBcg(lbxBcg.getSelectedItem().getValue().toString());
				riesgo_intervencion.setHepatitis_b(lbxHepatitis_b.getSelectedItem().getValue().toString());
				riesgo_intervencion.setPentavalente(lbxPentavalente.getSelectedItem().getValue().toString());
				riesgo_intervencion.setPollo(lbxPollo.getSelectedItem().getValue().toString());
				riesgo_intervencion.setDpt(lbxDpt.getSelectedItem().getValue().toString());
				riesgo_intervencion.setRotavirus(lbxRotavirus.getSelectedItem().getValue().toString());
				riesgo_intervencion.setNeumococo(lbxNeumococo.getSelectedItem().getValue().toString());
				riesgo_intervencion.setInfluenza(lbxInfluenza.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFiebre_amarilla(lbxFiebre_amarilla.getSelectedItem().getValue().toString());
				riesgo_intervencion.setHepatitis_a(lbxHepatitis_a.getSelectedItem().getValue().toString());
				riesgo_intervencion.setTriple_viral(lbxTriple_viral.getSelectedItem().getValue().toString());
				riesgo_intervencion.setVph(lbxVph.getSelectedItem().getValue().toString());
				riesgo_intervencion.setTd_tt(lbxTd_tt.getSelectedItem().getValue().toString());
				riesgo_intervencion.setPlaca_bacteriana(lbxPlaca_bacteriana.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_atencion_parto(new Timestamp(dtbxFecha_atencion_parto.getValue().getTime()));
				riesgo_intervencion.setFecha_salida_atencion(new Timestamp(dtbxFecha_salida_atencion.getValue().getTime()));
				riesgo_intervencion.setFecha_consejeria_lactancia(new Timestamp(dtbxFecha_consejeria_lactancia.getValue().getTime()));
				riesgo_intervencion.setFecha_recien_nacido(new Timestamp(dtbxFecha_recien_nacido.getValue().getTime()));
				riesgo_intervencion.setFecha_planificacion_familiar(new Timestamp(dtbxFecha_planificacion_familiar.getValue().getTime()));
				riesgo_intervencion.setSuministro_anticonceptivo(lbxSuministro_anticonceptivo.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_suministro_anticonceptivo(new Timestamp(dtbxFecha_suministro_anticonceptivo.getValue().getTime()));
				riesgo_intervencion.setFecha_control_prenatal(new Timestamp(dtbxFecha_control_prenatal.getValue().getTime()));
				riesgo_intervencion.setControl_prenatal((tbxControl_prenatal.getValue()));
				riesgo_intervencion.setUltimo_control_prenatal(new Timestamp(dtbxUltimo_control_prenatal.getValue().getTime()));
				riesgo_intervencion.setSuministro_acido_folico(lbxSuministro_acido_folico.getSelectedItem().getValue().toString());
				riesgo_intervencion.setSuministro_sulfato(lbxSuministro_sulfato.getSelectedItem().getValue().toString());
				riesgo_intervencion.setSuministro_carbonato(lbxSuministro_carbonato.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_agudeza_visual(new Timestamp(dtbxFecha_agudeza_visual.getValue().getTime()));
				riesgo_intervencion.setFecha_oftalmologia(new Timestamp(dtbxFecha_oftalmologia.getValue().getTime()));
				riesgo_intervencion.setFecha_diagnistico_desnutricion(new Timestamp(dtbxFecha_diagnistico_desnutricion.getValue().getTime()));
				riesgo_intervencion.setFecha_consulta_maltrato(new Timestamp(dtbxFecha_consulta_maltrato.getValue().getTime()));
				riesgo_intervencion.setFecha_consulta_violacion(new Timestamp(dtbxFecha_consulta_violacion.getValue().getTime()));
				riesgo_intervencion.setFecha_nutricion(new Timestamp(dtbxFecha_nutricion.getValue().getTime()));
				riesgo_intervencion.setFecha_psicologia(new Timestamp(dtbxFecha_psicologia.getValue().getTime()));
				riesgo_intervencion.setFecha_crecimiento(new Timestamp(dtbxFecha_crecimiento.getValue().getTime()));
				riesgo_intervencion.setSuministro_sulfato_ultima(lbxSuministro_sulfato_ultima.getSelectedItem().getValue().toString());
				riesgo_intervencion.setSuministro_vitamina_a(lbxSuministro_vitamina_a.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_consulta_joven(new Timestamp(dtbxFecha_consulta_joven.getValue().getTime()));
				riesgo_intervencion.setFecha_consulta_adulto(new Timestamp(dtbxFecha_consulta_adulto.getValue().getTime()));
				riesgo_intervencion.setPreservativos((tbxPreservativos.getValue()));
				riesgo_intervencion.setFecha_asesoria_vih(new Timestamp(dtbxFecha_asesoria_vih.getValue().getTime()));
				riesgo_intervencion.setFecha_asesoria_vih_pos(new Timestamp(dtbxFecha_asesoria_vih_pos.getValue().getTime()));
				riesgo_intervencion.setDiagnostico(lbxDiagnostico.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_antigeno_hepatitis_b(new Timestamp(dtbxFecha_antigeno_hepatitis_b.getValue().getTime()));
				riesgo_intervencion.setResultado_antigeno_hepatitis_b(lbxResultado_antigeno_hepatitis_b.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_serologia(new Timestamp(dtbxFecha_serologia.getValue().getTime()));
				riesgo_intervencion.setResultado_serologia(lbxResultado_serologia.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_toma_vih(new Timestamp(dtbxFecha_toma_vih.getValue().getTime()));
				riesgo_intervencion.setResultado_vih(lbxResultado_vih.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_tsh(new Timestamp(dtbxFecha_tsh.getValue().getTime()));
				riesgo_intervencion.setResultado_tsh(lbxResultado_tsh.getSelectedItem().getValue().toString());
				riesgo_intervencion.setTamizaje(lbxTamizaje.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_citologia_cervico(new Timestamp(dtbxFecha_citologia_cervico.getValue().getTime()));
				riesgo_intervencion.setResultado_citologia_cervico(lbxResultado_citologia_cervico.getSelectedItem().getValue().toString());
				riesgo_intervencion.setCalidad_citologia_cervico(lbxCalidad_citologia_cervico.getSelectedItem().getValue().toString());
				riesgo_intervencion.setCodigo_ips_citologia(tbxCodigo_ips_citologia.getValue());
				riesgo_intervencion.setFecha_colposcopia(new Timestamp(dtbxFecha_colposcopia.getValue().getTime()));
				riesgo_intervencion.setCodigo_ips_colposcopia(tbxCodigo_ips_colposcopia.getValue());
				riesgo_intervencion.setFecha_biopsia(new Timestamp(dtbxFecha_biopsia.getValue().getTime()));
				riesgo_intervencion.setResultado_biopsia(lbxResultado_biopsia.getSelectedItem().getValue().toString());
				riesgo_intervencion.setCodigo_ips_biopsia(tbxCodigo_ips_biopsia.getValue());
				riesgo_intervencion.setFecha_mamografia(new Timestamp(dtbxFecha_mamografia.getValue().getTime()));
				riesgo_intervencion.setResultado_mamografia(lbxResultado_mamografia.getSelectedItem().getValue().toString());
				riesgo_intervencion.setCodigo_ips_mamografia(tbxCodigo_ips_mamografia.getValue());
				riesgo_intervencion.setFecha_biopsia_seno(new Timestamp(dtbxFecha_biopsia_seno.getValue().getTime()));
				riesgo_intervencion.setFecha_resultado_biopsia_seno(new Timestamp(dtbxFecha_resultado_biopsia_seno.getValue().getTime()));
				riesgo_intervencion.setBipsia_seno(lbxBipsia_seno.getSelectedItem().getValue().toString());
				riesgo_intervencion.setCodigo_ips_biopsia_seno(tbxCodigo_ips_biopsia_seno.getValue());
				riesgo_intervencion.setFecha_hemoglobina(new Timestamp(dtbxFecha_hemoglobina.getValue().getTime()));
				riesgo_intervencion.setHemoglibina((dbxHemoglibina.getValue()!=null?dbxHemoglibina.getValue():0.00));
				riesgo_intervencion.setFecha_glisemia(new Timestamp(dtbxFecha_glisemia.getValue().getTime()));
				riesgo_intervencion.setFecha_creatinina(new Timestamp(dtbxFecha_creatinina.getValue().getTime()));
				riesgo_intervencion.setCreatinina((dbxCreatinina.getValue()!=null?dbxCreatinina.getValue():0.00));
				riesgo_intervencion.setFecha_hemoglobina_glicosilada(new Timestamp(dtbxFecha_hemoglobina_glicosilada.getValue().getTime()));
				riesgo_intervencion.setHemoglobina_glicosilada((dbxHemoglobina_glicosilada.getValue()!=null?dbxHemoglobina_glicosilada.getValue():0.00));
				riesgo_intervencion.setFecha_microalbuminuria(new Timestamp(dtbxFecha_microalbuminuria.getValue().getTime()));
				riesgo_intervencion.setFecha_hdl(new Timestamp(dtbxFecha_hdl.getValue().getTime()));
				riesgo_intervencion.setFecha_basiloscopia(new Timestamp(dtbxFecha_basiloscopia.getValue().getTime()));
				riesgo_intervencion.setBasiloscopia(lbxBasiloscopia.getSelectedItem().getValue().toString());
				riesgo_intervencion.setTratamiento_hipotiroidismo(lbxTratamiento_hipotiroidismo.getSelectedItem().getValue().toString());
				riesgo_intervencion.setTratamiento_sifilis_gestional(lbxTratamiento_sifilis_gestional.getSelectedItem().getValue().toString());
				riesgo_intervencion.setTratamiento_sifilis_congenita(lbxTratamiento_sifilis_congenita.getSelectedItem().getValue().toString());
				riesgo_intervencion.setTratamiento_lepra(lbxTratamiento_lepra.getSelectedItem().getValue().toString());
				riesgo_intervencion.setFecha_leismaniasis(new Timestamp(dtbxFecha_leismaniasis.getValue().getTime()));
				
				if(tbxAccion.getText().equalsIgnoreCase("registrar")){
					getServiceLocator().getRiesgo_intervencionService().crear(riesgo_intervencion);
					accionForm(true,"registrar");
				}else{
					int result = getServiceLocator().getRiesgo_intervencionService().actualizar(riesgo_intervencion);
					if(result==0){
						throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				
				Messagebox.show("Los datos se guardaron satisfactoriamente", 
					"Informacion .." ,
					Messagebox.OK, Messagebox.INFORMATION);
				
			}
			
		}catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj)throws Exception{
		Riesgo_intervencion riesgo_intervencion = (Riesgo_intervencion)obj;
		try{
			tbxPeso.setValue(riesgo_intervencion.getPeso());
			dtbxFecha_talla.setValue(riesgo_intervencion.getFecha_talla());
			dtbxFecha_peso.setValue(riesgo_intervencion.getFecha_peso());
			tbxTalla.setValue(riesgo_intervencion.getTalla());
			dtbxFecha_parto.setValue(riesgo_intervencion.getFecha_parto());
			tbxEdad_gestacional.setValue(riesgo_intervencion.getEdad_gestacional());
			for(int i=0;i<lbxBcg.getItemCount();i++){
				Listitem listitem = lbxBcg.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getBcg())){
					listitem.setSelected(true);
					i = lbxBcg.getItemCount();
				}
			}
			for(int i=0;i<lbxHepatitis_b.getItemCount();i++){
				Listitem listitem = lbxHepatitis_b.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getHepatitis_b())){
					listitem.setSelected(true);
					i = lbxHepatitis_b.getItemCount();
				}
			}
			for(int i=0;i<lbxPentavalente.getItemCount();i++){
				Listitem listitem = lbxPentavalente.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getPentavalente())){
					listitem.setSelected(true);
					i = lbxPentavalente.getItemCount();
				}
			}
			for(int i=0;i<lbxPollo.getItemCount();i++){
				Listitem listitem = lbxPollo.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getPollo())){
					listitem.setSelected(true);
					i = lbxPollo.getItemCount();
				}
			}
			for(int i=0;i<lbxDpt.getItemCount();i++){
				Listitem listitem = lbxDpt.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getDpt())){
					listitem.setSelected(true);
					i = lbxDpt.getItemCount();
				}
			}
			for(int i=0;i<lbxRotavirus.getItemCount();i++){
				Listitem listitem = lbxRotavirus.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getRotavirus())){
					listitem.setSelected(true);
					i = lbxRotavirus.getItemCount();
				}
			}
			for(int i=0;i<lbxNeumococo.getItemCount();i++){
				Listitem listitem = lbxNeumococo.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getNeumococo())){
					listitem.setSelected(true);
					i = lbxNeumococo.getItemCount();
				}
			}
			for(int i=0;i<lbxInfluenza.getItemCount();i++){
				Listitem listitem = lbxInfluenza.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getInfluenza())){
					listitem.setSelected(true);
					i = lbxInfluenza.getItemCount();
				}
			}
			for(int i=0;i<lbxFiebre_amarilla.getItemCount();i++){
				Listitem listitem = lbxFiebre_amarilla.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getFiebre_amarilla())){
					listitem.setSelected(true);
					i = lbxFiebre_amarilla.getItemCount();
				}
			}
			for(int i=0;i<lbxHepatitis_a.getItemCount();i++){
				Listitem listitem = lbxHepatitis_a.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getHepatitis_a())){
					listitem.setSelected(true);
					i = lbxHepatitis_a.getItemCount();
				}
			}
			for(int i=0;i<lbxTriple_viral.getItemCount();i++){
				Listitem listitem = lbxTriple_viral.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getTriple_viral())){
					listitem.setSelected(true);
					i = lbxTriple_viral.getItemCount();
				}
			}
			for(int i=0;i<lbxVph.getItemCount();i++){
				Listitem listitem = lbxVph.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getVph())){
					listitem.setSelected(true);
					i = lbxVph.getItemCount();
				}
			}
			for(int i=0;i<lbxTd_tt.getItemCount();i++){
				Listitem listitem = lbxTd_tt.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getTd_tt())){
					listitem.setSelected(true);
					i = lbxTd_tt.getItemCount();
				}
			}
			for(int i=0;i<lbxPlaca_bacteriana.getItemCount();i++){
				Listitem listitem = lbxPlaca_bacteriana.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getPlaca_bacteriana())){
					listitem.setSelected(true);
					i = lbxPlaca_bacteriana.getItemCount();
				}
			}
			dtbxFecha_atencion_parto.setValue(riesgo_intervencion.getFecha_atencion_parto());
			dtbxFecha_salida_atencion.setValue(riesgo_intervencion.getFecha_salida_atencion());
			dtbxFecha_consejeria_lactancia.setValue(riesgo_intervencion.getFecha_consejeria_lactancia());
			dtbxFecha_recien_nacido.setValue(riesgo_intervencion.getFecha_recien_nacido());
			dtbxFecha_planificacion_familiar.setValue(riesgo_intervencion.getFecha_planificacion_familiar());
			for(int i=0;i<lbxSuministro_anticonceptivo.getItemCount();i++){
				Listitem listitem = lbxSuministro_anticonceptivo.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getSuministro_anticonceptivo())){
					listitem.setSelected(true);
					i = lbxSuministro_anticonceptivo.getItemCount();
				}
			}
			dtbxFecha_suministro_anticonceptivo.setValue(riesgo_intervencion.getFecha_suministro_anticonceptivo());
			dtbxFecha_control_prenatal.setValue(riesgo_intervencion.getFecha_control_prenatal());
			tbxControl_prenatal.setValue(riesgo_intervencion.getControl_prenatal());
			dtbxUltimo_control_prenatal.setValue(riesgo_intervencion.getUltimo_control_prenatal());
			for(int i=0;i<lbxSuministro_acido_folico.getItemCount();i++){
				Listitem listitem = lbxSuministro_acido_folico.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getSuministro_acido_folico())){
					listitem.setSelected(true);
					i = lbxSuministro_acido_folico.getItemCount();
				}
			}
			for(int i=0;i<lbxSuministro_sulfato.getItemCount();i++){
				Listitem listitem = lbxSuministro_sulfato.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getSuministro_sulfato())){
					listitem.setSelected(true);
					i = lbxSuministro_sulfato.getItemCount();
				}
			}
			for(int i=0;i<lbxSuministro_carbonato.getItemCount();i++){
				Listitem listitem = lbxSuministro_carbonato.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getSuministro_carbonato())){
					listitem.setSelected(true);
					i = lbxSuministro_carbonato.getItemCount();
				}
			}
			dtbxFecha_agudeza_visual.setValue(riesgo_intervencion.getFecha_agudeza_visual());
			dtbxFecha_oftalmologia.setValue(riesgo_intervencion.getFecha_oftalmologia());
			dtbxFecha_diagnistico_desnutricion.setValue(riesgo_intervencion.getFecha_diagnistico_desnutricion());
			dtbxFecha_consulta_maltrato.setValue(riesgo_intervencion.getFecha_consulta_maltrato());
			dtbxFecha_consulta_violacion.setValue(riesgo_intervencion.getFecha_consulta_violacion());
			dtbxFecha_nutricion.setValue(riesgo_intervencion.getFecha_nutricion());
			dtbxFecha_psicologia.setValue(riesgo_intervencion.getFecha_psicologia());
			dtbxFecha_crecimiento.setValue(riesgo_intervencion.getFecha_crecimiento());
			for(int i=0;i<lbxSuministro_sulfato_ultima.getItemCount();i++){
				Listitem listitem = lbxSuministro_sulfato_ultima.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getSuministro_sulfato_ultima())){
					listitem.setSelected(true);
					i = lbxSuministro_sulfato_ultima.getItemCount();
				}
			}
			for(int i=0;i<lbxSuministro_vitamina_a.getItemCount();i++){
				Listitem listitem = lbxSuministro_vitamina_a.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getSuministro_vitamina_a())){
					listitem.setSelected(true);
					i = lbxSuministro_vitamina_a.getItemCount();
				}
			}
			dtbxFecha_consulta_joven.setValue(riesgo_intervencion.getFecha_consulta_joven());
			dtbxFecha_consulta_adulto.setValue(riesgo_intervencion.getFecha_consulta_adulto());
			tbxPreservativos.setValue(riesgo_intervencion.getPreservativos());
			dtbxFecha_asesoria_vih.setValue(riesgo_intervencion.getFecha_asesoria_vih());
			dtbxFecha_asesoria_vih_pos.setValue(riesgo_intervencion.getFecha_asesoria_vih_pos());
			for(int i=0;i<lbxDiagnostico.getItemCount();i++){
				Listitem listitem = lbxDiagnostico.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getDiagnostico())){
					listitem.setSelected(true);
					i = lbxDiagnostico.getItemCount();
				}
			}
			dtbxFecha_antigeno_hepatitis_b.setValue(riesgo_intervencion.getFecha_antigeno_hepatitis_b());
			for(int i=0;i<lbxResultado_antigeno_hepatitis_b.getItemCount();i++){
				Listitem listitem = lbxResultado_antigeno_hepatitis_b.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getResultado_antigeno_hepatitis_b())){
					listitem.setSelected(true);
					i = lbxResultado_antigeno_hepatitis_b.getItemCount();
				}
			}
			dtbxFecha_serologia.setValue(riesgo_intervencion.getFecha_serologia());
			for(int i=0;i<lbxResultado_serologia.getItemCount();i++){
				Listitem listitem = lbxResultado_serologia.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getResultado_serologia())){
					listitem.setSelected(true);
					i = lbxResultado_serologia.getItemCount();
				}
			}
			dtbxFecha_toma_vih.setValue(riesgo_intervencion.getFecha_toma_vih());
			for(int i=0;i<lbxResultado_vih.getItemCount();i++){
				Listitem listitem = lbxResultado_vih.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getResultado_vih())){
					listitem.setSelected(true);
					i = lbxResultado_vih.getItemCount();
				}
			}
			dtbxFecha_tsh.setValue(riesgo_intervencion.getFecha_tsh());
			for(int i=0;i<lbxResultado_tsh.getItemCount();i++){
				Listitem listitem = lbxResultado_tsh.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getResultado_tsh())){
					listitem.setSelected(true);
					i = lbxResultado_tsh.getItemCount();
				}
			}
			for(int i=0;i<lbxTamizaje.getItemCount();i++){
				Listitem listitem = lbxTamizaje.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getTamizaje())){
					listitem.setSelected(true);
					i = lbxTamizaje.getItemCount();
				}
			}
			dtbxFecha_citologia_cervico.setValue(riesgo_intervencion.getFecha_citologia_cervico());
			for(int i=0;i<lbxResultado_citologia_cervico.getItemCount();i++){
				Listitem listitem = lbxResultado_citologia_cervico.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getResultado_citologia_cervico())){
					listitem.setSelected(true);
					i = lbxResultado_citologia_cervico.getItemCount();
				}
			}
			for(int i=0;i<lbxCalidad_citologia_cervico.getItemCount();i++){
				Listitem listitem = lbxCalidad_citologia_cervico.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getCalidad_citologia_cervico())){
					listitem.setSelected(true);
					i = lbxCalidad_citologia_cervico.getItemCount();
				}
			}
			tbxCodigo_ips_citologia.setValue(riesgo_intervencion.getCodigo_ips_citologia());
			dtbxFecha_colposcopia.setValue(riesgo_intervencion.getFecha_colposcopia());
			tbxCodigo_ips_colposcopia.setValue(riesgo_intervencion.getCodigo_ips_colposcopia());
			dtbxFecha_biopsia.setValue(riesgo_intervencion.getFecha_biopsia());
			for(int i=0;i<lbxResultado_biopsia.getItemCount();i++){
				Listitem listitem = lbxResultado_biopsia.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getResultado_biopsia())){
					listitem.setSelected(true);
					i = lbxResultado_biopsia.getItemCount();
				}
			}
			tbxCodigo_ips_biopsia.setValue(riesgo_intervencion.getCodigo_ips_biopsia());
			dtbxFecha_mamografia.setValue(riesgo_intervencion.getFecha_mamografia());
			for(int i=0;i<lbxResultado_mamografia.getItemCount();i++){
				Listitem listitem = lbxResultado_mamografia.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getResultado_mamografia())){
					listitem.setSelected(true);
					i = lbxResultado_mamografia.getItemCount();
				}
			}
			tbxCodigo_ips_mamografia.setValue(riesgo_intervencion.getCodigo_ips_mamografia());
			dtbxFecha_biopsia_seno.setValue(riesgo_intervencion.getFecha_biopsia_seno());
			dtbxFecha_resultado_biopsia_seno.setValue(riesgo_intervencion.getFecha_resultado_biopsia_seno());
			for(int i=0;i<lbxBipsia_seno.getItemCount();i++){
				Listitem listitem = lbxBipsia_seno.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getBipsia_seno())){
					listitem.setSelected(true);
					i = lbxBipsia_seno.getItemCount();
				}
			}
			tbxCodigo_ips_biopsia_seno.setValue(riesgo_intervencion.getCodigo_ips_biopsia_seno());
			dtbxFecha_hemoglobina.setValue(riesgo_intervencion.getFecha_hemoglobina());
			dbxHemoglibina.setValue(riesgo_intervencion.getHemoglibina());
			dtbxFecha_glisemia.setValue(riesgo_intervencion.getFecha_glisemia());
			dtbxFecha_creatinina.setValue(riesgo_intervencion.getFecha_creatinina());
			dbxCreatinina.setValue(riesgo_intervencion.getCreatinina());
			dtbxFecha_hemoglobina_glicosilada.setValue(riesgo_intervencion.getFecha_hemoglobina_glicosilada());
			dbxHemoglobina_glicosilada.setValue(riesgo_intervencion.getHemoglobina_glicosilada());
			dtbxFecha_microalbuminuria.setValue(riesgo_intervencion.getFecha_microalbuminuria());
			dtbxFecha_hdl.setValue(riesgo_intervencion.getFecha_hdl());
			dtbxFecha_basiloscopia.setValue(riesgo_intervencion.getFecha_basiloscopia());
			for(int i=0;i<lbxBasiloscopia.getItemCount();i++){
				Listitem listitem = lbxBasiloscopia.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getBasiloscopia())){
					listitem.setSelected(true);
					i = lbxBasiloscopia.getItemCount();
				}
			}
			for(int i=0;i<lbxTratamiento_hipotiroidismo.getItemCount();i++){
				Listitem listitem = lbxTratamiento_hipotiroidismo.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getTratamiento_hipotiroidismo())){
					listitem.setSelected(true);
					i = lbxTratamiento_hipotiroidismo.getItemCount();
				}
			}
			for(int i=0;i<lbxTratamiento_sifilis_gestional.getItemCount();i++){
				Listitem listitem = lbxTratamiento_sifilis_gestional.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getTratamiento_sifilis_gestional())){
					listitem.setSelected(true);
					i = lbxTratamiento_sifilis_gestional.getItemCount();
				}
			}
			for(int i=0;i<lbxTratamiento_sifilis_congenita.getItemCount();i++){
				Listitem listitem = lbxTratamiento_sifilis_congenita.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getTratamiento_sifilis_congenita())){
					listitem.setSelected(true);
					i = lbxTratamiento_sifilis_congenita.getItemCount();
				}
			}
			for(int i=0;i<lbxTratamiento_lepra.getItemCount();i++){
				Listitem listitem = lbxTratamiento_lepra.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(riesgo_intervencion.getTratamiento_lepra())){
					listitem.setSelected(true);
					i = lbxTratamiento_lepra.getItemCount();
				}
			}
			dtbxFecha_leismaniasis.setValue(riesgo_intervencion.getFecha_leismaniasis());
			
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
		Riesgo_intervencion riesgo_intervencion = (Riesgo_intervencion)obj;
		try{
			int result = getServiceLocator().getRiesgo_intervencionService().eliminar(riesgo_intervencion);
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
	
	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}
}
