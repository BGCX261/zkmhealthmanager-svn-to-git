package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Paciente;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;

import com.framework.constantes.INotas;
import com.framework.res.CargardorDeDatos;

public class Modulo_odontoAction  extends ZKWindow{
	private static Logger log = Logger.getLogger(Modulo_odontoAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
	//private Permisos_sc permisos_sc;
	
	
	private Window form;
	
	//Componentes //
	/*private Bandbox tbxCodigo_prestador;
	private Textbox tbxNomPrestador;*/
	private Textbox tbxEstado;
	private Textbox tbxCodigo_administradora;
	private Bandbox tbxNro_ingreso;
	private Textbox tbxNro_identificacion;
	private Textbox tbxTipo_doc;
	private Textbox tbxNomPaciente;
	private Textbox tbxAdministradora;
	private Textbox tbxContrato;
	
	private Textbox tbxId_plan;
	private Textbox tbxSexo_pct;
    private Textbox tbxFecha_nac;
    private Textbox tbxTipo_hc;
	
	
	//private Tabbox tabboxHCUci;
    @View private Tab tabEvolucionOdontologia;
    @View private Tab tabRegClinicoHigiene;
    
	private Tabpanel tabpanelHistoria;
	@View private Tabpanel tabpanelEvolucionOdonotlogia;
	@View private Tabpanel tabpanelNotasOdonotlogia;
	@View private Tabpanel tabpanelRegClinicoHigiene;
	
	private Toolbar toolbar;
	private Listbox lbxAtendida;
//	private Parametros_empresa parametrosEmpresa;
	/*private Label lbEstado;
	private Button btActualizar;*/
	
	@View private Label lbFechaIngreso;
	@View private Groupbox group_paciente;
	private Admision admision;
	

	private void loadEventClose(){
		group_paciente.addEventListener("onOpen", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				//System.Out.Println("" + group_paciente.isOpen());
			}
		});
	}
	
	public void loadComponents(){
		form = (Window) this.getFellow("formModulo_odonto");
		
		tbxTipo_hc = (Textbox)form.getFellow("tbxTipo_hc");
		/*tbxCodigo_prestador = (Bandbox)form.getFellow("tbxCodigo_prestador");
		tbxNomPrestador = (Textbox)form.getFellow("tbxNomPrestador");*/
		tbxCodigo_administradora = (Textbox)form.getFellow("tbxCodigo_administradora");
		tbxEstado = (Textbox)form.getFellow("tbxEstado");
		tbxNro_ingreso = (Bandbox)form.getFellow("tbxNro_ingreso");
		tbxNro_identificacion = (Textbox)form.getFellow("tbxNro_identificacion");
		tbxTipo_doc = (Textbox)form.getFellow("tbxTipo_doc");
		tbxNomPaciente = (Textbox)form.getFellow("tbxNomPaciente");
		tbxAdministradora = (Textbox)form.getFellow("tbxAdministradora");
		tbxContrato = (Textbox)form.getFellow("tbxContrato");
		
		tbxId_plan = (Textbox)form.getFellow("tbxId_plan");
		tbxSexo_pct = (Textbox)form.getFellow("tbxSexo_pct");
		tbxFecha_nac = (Textbox)form.getFellow("tbxFecha_nac");
		
		
		//tabboxHCUci = (Tabbox) form.getFellow("tabboxHCUci");
		
		tabpanelHistoria = (Tabpanel) form.getFellow("tabpanelHistoria");
		
		toolbar = (Toolbar) form.getFellow("toolbar");
		lbxAtendida = (Listbox) form.getFellow("lbxAtendida");
		/*lbEstado = (Label) form.getFellow("lbEstado");
		btActualizar = (Button) form.getFellow("btActualizar");*/
	}
	
	public void initModulo_odonto()throws Exception{
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		try{
			String id_m = request.getParameter("id_menu");
			if(id_m!=null){
				//crearPermisos(new Integer(id_m));
			}
			
			Map parametros = Executions.getCurrent().getArg();
			if(parametros==null){
				parametros = new HashMap();
			}
			String tipo_hc = request.getParameter("tipo_hc");
			if(tipo_hc==null){
				tipo_hc = (String)parametros.get("tipo_hc");
			}
			//log.info("tipo_hc: "+tipo_hc);
			tbxTipo_hc.setValue(tipo_hc);
			
			
			admision = (Admision) parametros.get("admision");
			
						
			Admision admision = (Admision)parametros.get("admision");
			boolean bloqueaAdmision = false;
			if(parametros.get("bloqueaAdmision")!=null){
				bloqueaAdmision =  (Boolean) parametros.get("bloqueaAdmision");
			}
			
			if(bloqueaAdmision){
				tbxNro_ingreso.setDisabled(true);
				Listitem listitem = new Listitem();
				listitem.setValue(admision);
				selectedAdmision(listitem);
				//toolbar.setVisible(true);
				toolbar.setVisible(false);
				/*lbEstado.setVisible(true);
				lbxAtendida.setVisible(true);
				btActualizar.setVisible(true);*/
			}else{
				toolbar.setVisible(false);
				/*lbEstado.setVisible(false);
				lbxAtendida.setVisible(false);
				btActualizar.setVisible(false);*/
				cargarHistoriaClinica();
				cargarEvolucionOdonotologia();
				cargarNota_odontologia();
//				cargarRegClinicoHigiene();
			}
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(),
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	
	public void listarCombos(){
		listarAtendida();
	}
	
	public void listarAtendida() {
		lbxAtendida.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue(false);
		listitem.setLabel("Pendiente");
		lbxAtendida.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue(true);
		listitem.setLabel("Atendida");
		lbxAtendida.appendChild(listitem);
		
		if (lbxAtendida.getItemCount() > 0) {
			lbxAtendida.setSelectedIndex(0);
		}
	}
	
	public void buscarAdmision(String value,String valor_estado,Listbox lbx)throws Exception{
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("codigo_sucursal", tbxTipo_hc.getValue());
			parameters.put("paramTodo", "");
			parameters.put("value", "%"+value.toUpperCase().trim()+"%");
			if(!valor_estado.equals("")){
				parameters.put("estado",valor_estado);
			}
			
			parameters.put("limite_paginado","limit 25 offset 0");
			
			List<Admision> list = getServiceLocator().getAdmisionService().listarResultados(parameters);
			
			lbx.getItems().clear();

			for (Admision dato : list) {
				
				Paciente paciente = admision.getPaciente();
				
				String apellidos = (paciente!=null?paciente.getApellido1()+" "+paciente.getApellido2():"");
				String nombres = (paciente!=null?paciente.getNombre1()+" "+paciente.getNombre2():"");
				
				String estado = (dato.getEstado().equals("1")?"Activo":"Terminada");
				
				Listitem listitem = new Listitem();
				listitem.setValue(dato);
				
				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_ingreso()+""));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()+""));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(apellidos));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(nombres));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dato.getFecha_ingreso())));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(estado));
				listitem.appendChild(listcell);
				
				lbx.appendChild(listitem);
			}
			
			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();
			
			
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void selectedAdmision(Listitem listitem){
		group_paciente.setClosable(true);
		if(listitem.getValue()==null){
			/*tbxCodigo_prestador.setValue("000000000");
			tbxNomPrestador.setValue("MEDICO POR DEFECTO");*/
			
			tbxEstado.setValue("");
			tbxCodigo_administradora.setValue("");
			tbxId_plan.setValue("");
			tbxNro_ingreso.setValue("");
			
			tbxNro_identificacion.setValue("");
			tbxNomPaciente.setValue("");
			tbxSexo_pct.setValue("");
			tbxFecha_nac.setValue("");
			tbxTipo_doc.setValue("");
			tbxAdministradora.setValue("");
			tbxContrato.setValue("");
			lbFechaIngreso.setValue(""); 
			
			lbxAtendida.setSelectedIndex(0);
			group_paciente.setOpen(false);
		}else{
			Admision dato = (Admision)listitem.getValue();
			Paciente paciente = admision.getPaciente();
			
			Elemento tipo_doc = new Elemento();
			tipo_doc.setTipo("tipo_id");
			tipo_doc.setCodigo((paciente!=null?paciente.getTipo_identificacion():""));
			tipo_doc = getServiceLocator().getElementoService().consultar(tipo_doc);
			
			Administradora admin = new Administradora();
			admin.setCodigo(dato.getCodigo_administradora());
			admin = getServiceLocator().getAdministradoraService().consultar(admin);
			
			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(dato.getCodigo_empresa());
			contratos.setCodigo_sucursal(dato.getCodigo_sucursal());
			contratos.setCodigo_administradora(dato.getCodigo_administradora());
			contratos.setId_plan(dato.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(contratos);
			 
			lbFechaIngreso.setValue(new SimpleDateFormat("yyyy-MM-dd mm:ss").format(dato.getFecha_ingreso())); 
			group_paciente.setOpen(true);
			/*tbxCodigo_prestador.setValue("000000000");
			tbxNomPrestador.setValue("MEDICO POR DEFECTO");*/
			
			tbxEstado.setValue(dato.getEstado());
			tbxCodigo_administradora.setValue(dato.getCodigo_administradora());
			tbxId_plan.setValue(dato.getId_plan());
            tbxNro_ingreso.setValue(dato.getNro_ingreso());
			
			tbxNro_identificacion.setValue(dato.getNro_identificacion());
			tbxNomPaciente.setValue((paciente!=null?paciente.getApellido1()+" "+paciente.getApellido2()+" "+paciente.getNombre1():""));
			tbxSexo_pct.setValue((paciente!=null?paciente.getSexo():""));
			tbxFecha_nac.setValue((paciente!=null?new SimpleDateFormat("dd/MM/yyyy").format(paciente.getFecha_nacimiento()):""));
			tbxTipo_doc.setValue((tipo_doc!=null?tipo_doc.getDescripcion():""));
			tbxAdministradora.setValue(dato.getCodigo_administradora()+" - "+(admin!=null?admin.getNombre():""));
			tbxContrato.setValue((contratos!=null?contratos.getNombre():""));
			
			if(dato.getAtendida()){
				lbxAtendida.setSelectedIndex(1);
			}else{
				lbxAtendida.setSelectedIndex(0);
			}
			
		}
		
		cargarHistoriaClinica();
		cargarEvolucionOdonotologia();
		cargarNota_odontologia();
//		cargarRegClinicoHigiene();
		tbxNro_ingreso.close();
	}
	
	public void cargarHistoriaClinica(){
		Admision admision = new Admision();
		admision.setCodigo_empresa(empresa.getCodigo_empresa());
		admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		admision.setNro_identificacion(tbxNro_identificacion.getValue());
		admision.setNro_ingreso(tbxNro_ingreso.getValue());
		admision = getServiceLocator().getAdmisionService().consultar(admision);
		
		String area_int = "";
		
		if(admision!=null){
			Citas citas = new Citas();
			citas.setCodigo_empresa(admision.getCodigo_empresa());
			citas.setCodigo_sucursal(admision.getCodigo_sucursal());
			citas.setCodigo_cita(admision.getCodigo_cita());
			citas = getServiceLocator().getCitasService().consultar(citas);
			area_int = (citas!=null?citas.getArea_intervencion():"");
		}
		
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());
		parametros.put("fecha_nac", tbxFecha_nac.getValue());
		parametros.put("area_int", area_int);
		
		tabpanelHistoria.getChildren().clear();
		
		Component componente = Executions.createComponents("/pages/odontologia.zul", form,
				parametros);  
		final Window ventana = (Window)componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		/*
		 if(parametrosEmpresa.getTipo_historia_clinica().equalsIgnoreCase("01")){
		 
			((Historia_clinica_uciAction)ventana).setParentBean(this);
		}else{
			((Historia_clinica_uciAction_2)ventana).setParentBean(this);
		}
		*/
		tabpanelHistoria.appendChild(ventana);
	}
	
	public void cargarNota_odontologia() {
		Map parametros = new HashMap();
		parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
		parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
		parametros.put("estado", tbxEstado.getValue());
		parametros.put("codigo_administradora",
				tbxCodigo_administradora.getValue());
		parametros.put("id_plan", tbxId_plan.getValue());
		parametros.put("tipo_hc", tbxTipo_hc.getValue());
		parametros.put("tipo", INotas.NOTAS_ODONTOLOGIA); 

		tabpanelNotasOdonotlogia.getChildren().clear();
		Component componente = Executions.createComponents(
				"/pages/nota_aclaratoria.zul", form, parametros);
		final Window ventana = (Window) componente;
		ventana.setWidth("100%");
		ventana.setVflex("1");
		tabpanelNotasOdonotlogia.appendChild(ventana);
	}
	
	private void cargarEvolucionOdonotologia(){
		/* verificamos si existe una historia odonotologica */
		Odontologia odontologia = new Odontologia(); 
		odontologia.setCodigo_empresa(codigo_empresa);
		odontologia.setCodigo_sucursal(codigo_sucursal);
		odontologia.setIdentificacion(tbxNro_identificacion.getValue());
		odontologia.setNro_ingreso(tbxNro_ingreso.getValue()); 
		odontologia = getServiceLocator().getOdontologiaService().consultar(odontologia);
		
		if(odontologia != null){
			tabEvolucionOdontologia.setVisible(true);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("odontologia", odontologia);
			parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
			Component componente = Executions.createComponents("/pages/evolucion_odontologia.zul", form,
					parametros);  
			final Window ventana = (Window)componente;
			ventana.setWidth("100%");
			ventana.setVflex("1");
			tabpanelEvolucionOdonotlogia.appendChild(ventana);
		}
	}
	
//	private void cargarRegClinicoHigiene(){
//		/* verificamos si existe una historia odonotologica */
//		Odontologia odontologia = new Odontologia(); 
//		odontologia.setCodigo_empresa(codigo_empresa);
//		odontologia.setCodigo_sucursal(codigo_sucursal);
//		odontologia.setIdentificacion(tbxNro_identificacion.getValue());
//		odontologia.setNro_ingreso(tbxNro_ingreso.getValue()); 
//		odontologia = getServiceLocator().getOdontologiaService().consultar(odontologia);
//		
//		if(odontologia != null){
//			tabRegClinicoHigiene.setVisible(true);
//			Map<String, Object> parametros = new HashMap<String, Object>();
//			parametros.put("odontologia", odontologia);
//			parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
//			Component componente = Executions.createComponents("/pages/reg_clinico_higiene.zul", form,
//					parametros);  
//			final Window ventana = (Window)componente;
//			ventana.setWidth("100%");
//			ventana.setVflex("1");
//			tabpanelRegClinicoHigiene.appendChild(ventana);
//		}
//	}
	
	
	public void guardarDatos()throws Exception{
		try {
			if(tbxNro_ingreso.getValue().equals("")){
				throw new Exception("Seleccione un ingreso de paciente");
			}
			Admision admision = new Admision();
			admision.setCodigo_empresa(empresa.getCodigo_empresa());
			admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			admision.setNro_identificacion(tbxNro_identificacion.getValue());
			admision.setNro_ingreso(tbxNro_ingreso.getValue());
			admision = getServiceLocator().getAdmisionService().consultar(admision);
			if(admision==null){
				throw new Exception("No hay admision que actualizar");
			}
			admision.setAtendida((Boolean) lbxAtendida.getSelectedItem().getValue());

			
			Messagebox.show("La admision se actualizó satisfactoriamente", 
					"Informacion .." ,
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void verRegistros()throws Exception{
		
		/*
		 try {
			if(tbxNro_identificacion.getValue().equals("")){
				throw new Exception("Seleccione una admision valida");
			}
			Map parametros = new HashMap();
			parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
			parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
			
			Component componente = Executions.createComponents("verRegistros_admision.zul", form, parametros);
			final Window ventana = (Window)componente;
			ventana.setWidth("990px");
			ventana.setHeight("400px");
			ventana.setClosable(true);
			ventana.setMaximizable(true);
			ventana.setTitle("CONSULTAR REGISTROS "+tbxNro_ingreso.getValue()+" PACIENTE  "+tbxNomPaciente.getValue());
			ventana.setMode("modal");
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
		*/
	}


	@Override
	public void init() throws Exception {
		
		CargardorDeDatos.initComponents(this);
		loadComponents();
		cargarDatosSesion();
		listarCombos();
		loadEventClose();
		initModulo_odonto();
	}
}
