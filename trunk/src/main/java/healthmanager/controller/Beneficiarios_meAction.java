package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Edad_vencimiento;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Historial_observaciones;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.bean.Solicitud_e1;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Afiliaciones_meService;
import healthmanager.modelo.service.Historial_observacionesService;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.macros.HistorialObservacionMacro;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.Utilidades;

public class Beneficiarios_meAction extends ZKWindow {

	/* componentes de la vista */ 
	@View
	private Groupbox groupboxConsulta;
	@View
	private Grid gridResultado;
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Rows rowsResultado;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Listbox lbxTipo_identificacion;
	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxApellido1;
	@View
	private Textbox tbxApellido2;
	@View
	private Textbox tbxNombre1;
	@View
	private Textbox tbxNombre2;
	@View
	private Datebox dtbxFecha_nacimiento;
	@View
	private Listbox lbxSexo;
	@View
	private Listbox lbxCodigo_dpto;
	@View
	private Listbox lbxCodigo_municipio;
	@View
	private Listbox lbxTipo_afiliado;
	@View
	private Listbox lbxZona;
	@View
	private Datebox dtbxFecha_afiliacion;
	@View
	private Listbox lbxParentesco;
	@View
	private Textbox tbxAccion;
	
	@View private Image imgState;
	
	@View private Row rowDiscapacidad;
	
	
	@View private Textbox tbxLogin;
	@View private Textbox tbxPassword;
	
	
	@View private Listbox lbxEstadoAfiliacion;
	
	/* botones */
	@View
	private Button btn_guardar;
	@View
	private Button btn_nuevo;
	@View
	private Button btn_cancel;
	
	@View private Listbox lbxRh;
	
	@View private Checkbox chbDiscapacidad;
	@View private Textbox tbxDescripcion_discapacidad;
	@View private Label lbDescripcion_discapacidad;
	@View private Intbox ibxId;
	
	@View private Textbox tbxDireccion;
	@View private Textbox tbxTel_oficina;
	
    @View private Listbox lbxPertenencia_etnica;
	
	@View private Listbox lbxCodigo_ocupacion;
	@View private Listbox lbxCodigo_educativo;
	
	@View private Datebox dtbxFecha_vencimiento;
	@View private Listbox lbxTipo_vencimiento;
	
    @View private Image imageUsuario;
	
	@View private Button btn_pasar_cotizante;
	
	@View(isMacro = true) private HistorialObservacionMacro historialObservacionMacro;

	
	private byte[] bytefoto = null;
	public Afiliaciones_me afiliado_cotizante;

	public enum ACCION_BENEFICIARIOS {
		ADD_BENEFICIARIO, LIST_BENEFICIARIOS, AFILIAR_NUEVO
	};

	private ACCION_BENEFICIARIOS accion;
	
	private Afiliaciones_meAction afiliacionesMeAction;

	private Afiliaciones_me afiliacionesMeBeneficiario;
	private Afiliaciones_me afiliacionesMeCotizante;

	private Boolean update;

	private Solicitud_e1 solicitud_e1;
	private Paciente paciente;

	private void cargamosDatosAfiliados() throws Exception{
		Map map = Executions.getCurrent().getArg();
		if (map != null) {
			afiliado_cotizante = (Afiliaciones_me) map.get("AC");
			accion = (ACCION_BENEFICIARIOS) map.get("accion");
		}

		if (accion != null) {
			switch (accion) {
			case ADD_BENEFICIARIO:
				 afiliacionesMeAction = (Afiliaciones_meAction) getParent();
				 groupboxConsulta.setVisible(false);
				 groupboxEditar.setVisible(true); 
				 afiliacionesMeBeneficiario = (Afiliaciones_me) map.get("AB"); 
				 List<Historial_observaciones> historial_observaciones = (List<Historial_observaciones>) map.get("hist_ob");
				 if(historial_observaciones != null){
					 historialObservacionMacro.setHistorialObservaciones(historial_observaciones); 
				 }
				 update = (Boolean) map.get("UPdate");  
					if(afiliacionesMeBeneficiario != null) mostrarDatos(afiliacionesMeBeneficiario);
                 btn_guardar.setLabel((afiliacionesMeBeneficiario != null ? "Modificar" :  "Agregar") + " Beneficiario");
                 btn_nuevo.setVisible(afiliacionesMeBeneficiario == null);
                 btn_cancel.addEventListener("onClick", new EventListener() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Beneficiarios_meAction.this.onClose();
					}
				}); 
				break;
			case LIST_BENEFICIARIOS:
				afiliacionesMeAction = (Afiliaciones_meAction) getParent();
				afiliacionesMeCotizante = (Afiliaciones_me) map.get("AB"); 
				btn_guardar.setVisible(false);
                btn_nuevo.setVisible(false);
                buscarDatos();
				break;
			case AFILIAR_NUEVO:
				 groupboxConsulta.setVisible(false);
				 groupboxEditar.setVisible(true); 
				 solicitud_e1 = (Solicitud_e1) map.get("SE1");  
					if(solicitud_e1 != null) mostrarDatos(solicitud_e1);
//                 btn_guardar.setLabel((afiliacionesMeBeneficiario != null ? "Modificar" :  "Agregar") + " Beneficiario");
                 btn_nuevo.setVisible(solicitud_e1 == null);
                 btn_cancel.addEventListener("onClick", new EventListener() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Beneficiarios_meAction.this.onClose();
					}
				}); 
					break;
			}
		}
	}

	private void listarCombos() {
		listarParameter();
		listarElementoListbox(lbxParentesco, true);
		listarElementoListbox(lbxZona, false);
		listarDepartamentos(lbxCodigo_dpto, true);
		listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
		listarElementoListboxTipoId(lbxTipo_identificacion, true);
		listarElementoListbox(lbxSexo, true);
		listarElementoListbox(lbxRh,true);
		listarPertenenciaEtnica();
		listarOcupaciones();
		listarNivelEducativo();
		listarEdadVencimiento(); 
		Utilidades.listarElementoListbox(lbxEstadoAfiliacion, true, getServiceLocator()); 
	}
	
	private void listarEdadVencimiento() {
		//log.info("Tipo ven: " + lbxTipo_vencimiento);
		lbxTipo_vencimiento.getChildren().clear();
		//log.info("paso 1 -");

		Listitem listitem = new Listitem();
		listitem.setValue(0);
		listitem.setLabel("seleccione");
		lbxTipo_vencimiento.appendChild(listitem);
		//log.info("paso 1 --");
		
		Map<String, Object> parameters = new HashMap();
		parameters.put("codigo_empresa", usuarios.getCodigo_empresa()); 
		parameters.put("codigo_sucursal", usuarios.getCodigo_sucursal());
		//log.info("paso 1 ");
		
		//log.info("paso 2 antes ");
		List<Edad_vencimiento> edad_vencimientos = getServiceLocator().getEdad_vencimientoService().listar(parameters );
		//log.info("paso 2 ");
		for (Edad_vencimiento edad_vencimiento : edad_vencimientos) {
        	listitem = new Listitem();
    		listitem.setValue(edad_vencimiento.getEdad_vencimiento());
    		listitem.setLabel("" + edad_vencimiento.getEdad_vencimiento().toString());
    		lbxTipo_vencimiento.appendChild(listitem);
		}
		//log.info("paso 3 ");

		if (lbxTipo_vencimiento.getItemCount() > 0) {
			lbxTipo_vencimiento.setSelectedIndex(0);
		}
	}

	private void listarNivelEducativo() {
		List<Nivel_educativo> pertenenciaEtnicas = getServiceLocator().getNivel_educativoService().listar(new HashMap());
        lbxCodigo_educativo.getChildren().clear();
        
        for (Nivel_educativo pertenenciaEtnica : pertenenciaEtnicas) {
        	Listitem listitem = new Listitem();
    		listitem.setValue(pertenenciaEtnica.getId());
    		listitem.setLabel("" + pertenenciaEtnica.getNombre());
    		lbxCodigo_educativo.appendChild(listitem);
		}
		
		if (lbxCodigo_educativo.getItemCount() > 0) {
			lbxCodigo_educativo.setSelectedIndex(0);
		}
	}

	private void listarOcupaciones() {
		
		List<Ocupaciones> pertenenciaEtnicas = getServiceLocator().getOcupacionesService().listar(new HashMap());
        lbxCodigo_ocupacion.getChildren().clear();
        
        for (Ocupaciones pertenenciaEtnica : pertenenciaEtnicas) {
        	Listitem listitem = new Listitem();
    		listitem.setValue(pertenenciaEtnica.getId());
    		listitem.setLabel("" + pertenenciaEtnica.getNombre());
    		lbxCodigo_ocupacion.appendChild(listitem);
		}
		
		if (lbxCodigo_ocupacion.getItemCount() > 0) {
			lbxCodigo_ocupacion.setSelectedIndex(0);
		}
	}

	private void listarPertenenciaEtnica() {
		List<Pertenencia_etnica> pertenenciaEtnicas = getServiceLocator().getPertenencia_etnicaService().listar(new HashMap());
        lbxPertenencia_etnica.getChildren().clear();
        
        for (Pertenencia_etnica pertenenciaEtnica : pertenenciaEtnicas) {
        	Listitem listitem = new Listitem();
    		listitem.setValue(pertenenciaEtnica.getId());
    		listitem.setLabel("" + pertenenciaEtnica.getNombre());
    		lbxPertenencia_etnica.appendChild(listitem);
		}
		
		if (lbxPertenencia_etnica.getItemCount() > 0) {
			lbxPertenencia_etnica.setSelectedIndex(0);
		}
	}

	public void listarElementoListboxTipoId(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();

		Map map = new HashMap();
		map.put("tipo", tipo);
		map.put("permitidos2", "_");

		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(map);

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

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nro_identificacion_cotizante");
		listitem.setLabel("Nro_identificacion_cotizante");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacion_afiliado");
		listitem.setLabel("Nro_identificacion_afiliado");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void guardarImagen(Media media) throws Exception {
		try {
			if (media instanceof org.zkoss.image.Image){
				AImage aImage = new AImage("foto_usr",media.getByteData());
	            imageUsuario.setContent(aImage);
	            bytefoto = media.getByteData();
			}else {
	            Messagebox.show("No es una foto: "+media, 
	            		"Error", Messagebox.OK, Messagebox.EXCLAMATION);
	        }
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error cargando foto!!", 
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void borrarImagen()throws Exception{
		ServletContext servletContext = (ServletContext) Executions
				.getCurrent().getDesktop().getWebApp().getServletContext();
		AImage aImage = new AImage(new File(servletContext.getRealPath("/images/perfil.gif")));
		imageUsuario.setContent(aImage);
        bytefoto = null;
	}
	
	public void accionForm(boolean sw, String accion) throws Exception {
//		groupboxConsulta.setVisible(!sw);
//		groupboxEditar.setVisible(sw);
//
//		if (!accion.equalsIgnoreCase("registrar")) {
//			buscarDatos();
//		} else {
//			// buscarDatos();
//			limpiarDatos();
//		}
//		tbxAccion.setValue(accion);
	}

	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent)
							.setText(((Textbox) abstractComponent).getText()
									.trim().toUpperCase());
				}
			}
		}
	}

	public void buscarDatos() throws Exception {
		try {
			rowsResultado.getChildren().clear();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", afiliacionesMeCotizante.getCodigo_empresa());
			map.put("codigo_sucursal", afiliacionesMeCotizante.getCodigo_sucursal());
			map.put("nro_identificacion_cotizante", afiliacionesMeCotizante.getNro_identificacion_afiliado());
			List<Afiliaciones_me> list_afiliacionesMesBeneficiarios = getServiceLocator().getServicio(Afiliaciones_meService.class).listar(map);
 
			for (Afiliaciones_me afiliaciones_me : list_afiliacionesMesBeneficiarios) {
				rowsResultado.appendChild(crearFilas(afiliaciones_me, this));
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

	public Row crearFilas(Object objeto, Component componente)
			throws Exception {
		Row fila = new Row();

		final Afiliaciones_me afiliaciones_me = (Afiliaciones_me) objeto;

		
		Departamentos  departamentos = new Departamentos();
		departamentos.setCodigo(afiliaciones_me.getDepartamento_afiliacion());
		departamentos =   getServiceLocator().getDepartamentosService().consultar(departamentos);
		
		Municipios municipios = new Municipios();
		municipios.setCoddep(afiliaciones_me.getDepartamento_afiliacion());
		municipios.setCodigo(afiliaciones_me.getMunicipio_afiliacion());
		municipios = getServiceLocator().getMunicipiosService().consultar(municipios);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(afiliaciones_me.getPaciente()
				.getTipo_identificacion()
				+ ""));
		fila.appendChild(new Label(afiliaciones_me.getPaciente().getDocumento()
				+ ""));
		fila.appendChild(new Label(afiliaciones_me
				.getPaciente().getNombre1()
				+ " " +afiliaciones_me
				.getPaciente().getNombre2()));
		fila.appendChild(new Label(afiliaciones_me
				.getPaciente().getApellido1()
				+ " " +afiliaciones_me
				.getPaciente().getApellido2()));
		fila.appendChild(new Label(afiliaciones_me.getTipo_afiliado().equalsIgnoreCase("b") ? "Beneficiario" : "Adicional"));
		fila.appendChild(new Label(afiliaciones_me.getPaciente().getSexo().equals("M") ? "Masculino" : "Femenino"));
		fila.appendChild(new Label(afiliaciones_me.getParenteco_cotizante()));
		fila.appendChild(new Label(departamentos != null ? departamentos.getNombre() : ""));
		fila.appendChild(new Label(municipios != null ? municipios.getNombre() : ""));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy").format(afiliaciones_me.getFecha_afiliacion())+""));

//		hbox.appendChild(space);
//
//		Image img = new Image();
//		img.setSrc("/images/editar.gif");
//		img.setTooltiptext("Editar");
//		img.setStyle("cursor: pointer");
//		img.addEventListener("onClick", new EventListener() {
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				mostrarDatos(afiliaciones_me);
//			}
//		});
//		hbox.appendChild(img);
//
//		img = new Image();
//		img.setSrc("/images/add_perfil.png");
//		img.setTooltiptext("Agregar Veneficiarios");
//		img.setStyle("cursor: pointer");
//		img.addEventListener("onClick", new EventListener() {
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				mostrarDatos(afiliaciones_me);
//			}
//		});
//		hbox.appendChild(space);
//		hbox.appendChild(img);
//
//		img = new Image();
//		img.setSrc("/images/borrar.gif");
//		img.setTooltiptext("Eliminar");
//		img.setStyle("cursor: pointer");
//		img.addEventListener("onClick", new EventListener() {
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				Messagebox.show(
//						"Esta seguro que desea eliminar este registro? ",
//						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
//						Messagebox.QUESTION,
//						new org.zkoss.zk.ui.event.EventListener() {
//							public void onEvent(Event event) throws Exception {
//								if ("onYes".equals(event.getName())) {
//									// do the thing
//									eliminarDatos(afiliaciones_me);
//									buscarDatos();
//								}
//							}
//						});
//			}
//		});
//		hbox.appendChild(space);
//		hbox.appendChild(img);
//
//		fila.appendChild(hbox);

		return fila;
	}

	public void eliminarDatos(Object obj) throws Exception {
		Afiliaciones_me afiliaciones_me = (Afiliaciones_me) obj;
		try {
			int result = getServiceLocator().getAfiliacionesMeService()
					.eliminar(afiliaciones_me);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox
					.show(
							"Este objeto no se puede eliminar por que esta requerido para otro proceso",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void pasarHaCotizante() throws Exception{
		Messagebox.show(
				"Esta seguro que desea pasar este afiliado a Cotizante? ",
				"Eliminar Registro", Messagebox.YES + Messagebox.NO,
				Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event event) throws Exception {
						if ("onYes".equals(event.getName())) {
							// do the thing
							if(afiliacionesMeBeneficiario != null){
								if(getServiceLocator().getAfiliacionesMeService().consultar(afiliacionesMeBeneficiario) != null){
									afiliacionesMeBeneficiario.setTipo_afiliado("C");
									afiliacionesMeBeneficiario.setNro_id_antes_cotizante(afiliacionesMeBeneficiario.getNro_identificacion_cotizante());
									afiliacionesMeBeneficiario.setNro_identificacion_cotizante(""); 
									getServiceLocator().getAfiliacionesMeService().actualizar(afiliacionesMeBeneficiario);
									
									Paciente paciente = afiliacionesMeBeneficiario.getPaciente();
									paciente.setTipo_afiliado("1");
									getServiceLocator().getPacienteService().actualizar(paciente, true);
									Messagebox.show("Cambio de estado de afiliacion realizado exitosamente", "Informacion !!", Messagebox.OK,
											Messagebox.INFORMATION);
								}else{
									Messagebox.show("Este afiliado no se ha guardado aun", "Advertencia !!", Messagebox.OK,
											Messagebox.EXCLAMATION);
								}
							}
						}
					}
				});
	}
	
	
	private void mostrarDatos(Solicitud_e1 solicitudE1) throws Exception{
		try {
			Utilidades.setValueFrom(lbxTipo_identificacion, solicitudE1.getTipo_identificacion());  
			Utilidades.setValueFrom(lbxSexo, solicitudE1.getSexo());
			Utilidades.setValueFrom(lbxZona, solicitudE1.getZona());
			Utilidades.setValueFrom(lbxCodigo_dpto, solicitudE1.getCodigo_dpto());
			
			listarMunicipios(lbxCodigo_municipio,lbxCodigo_dpto);
			Utilidades.setValueFrom(lbxCodigo_municipio, solicitudE1.getCodigo_municipio());
			
			tbxApellido1.setValue(solicitudE1.getApellido1());
			tbxApellido2.setValue(solicitudE1.getApellido2());
			tbxNombre1.setValue(solicitudE1.getNombre1());
			tbxNombre2.setValue(solicitudE1.getNombre2());
			tbxNro_identificacion.setValue(solicitudE1.getNro_identificacion());
			dtbxFecha_nacimiento.setValue(solicitudE1.getFecha_nacimiento());
			dtbxFecha_afiliacion.setValue(solicitudE1.getFecha_afiliacion());
			
		} catch (WrongValueException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar",
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	protected void mostrarDatos(Afiliaciones_me afiliacionesMe) throws Exception{
		paciente = afiliacionesMe.getPaciente(); 
		try {
			btn_pasar_cotizante.setVisible(true);
//			lbxTipo_identificacion.setDisabled(true);
//			tbxNro_identificacion.setReadonly(true);
			ibxId.setText(afiliacionesMe.getId());
			
			Utilidades.setValueFrom(lbxTipo_identificacion, paciente.getTipo_identificacion()); 
			Utilidades.setValueFrom(lbxSexo, paciente.getSexo()); 
			Utilidades.setValueFrom(lbxTipo_afiliado, paciente.getTipo_afiliado()); 
			Utilidades.setValueFrom(lbxZona, paciente.getZona()); 
			Utilidades.setValueFrom(lbxParentesco, afiliacionesMe.getParenteco_cotizante()); 
			
			dtbxFecha_vencimiento.setValue(afiliacionesMe.getFecha_vencimiento());
			
			
			if(afiliacionesMe.getTipo_vencimiento() != null)
			for(int i=0;i<lbxTipo_vencimiento.getItemCount();i++){
				Listitem listitem = lbxTipo_vencimiento.getItemAtIndex(i);
				if(((Integer)listitem.getValue()).intValue() == afiliacionesMe.getTipo_vencimiento()){
					listitem.setSelected(true);
					break;
				}
			}
			
			for(int i=0;i<lbxRh.getItemCount();i++){
				Listitem listitem = lbxRh.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(afiliacionesMe.getRh())){
					listitem.setSelected(true);
					break;
				}
			}
			
			
			for(int i=0;i<lbxCodigo_dpto.getItemCount();i++){
				Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(paciente.getCodigo_dpto())){
					listitem.setSelected(true);
					break;
				}
			}
			
			listarMunicipios(lbxCodigo_municipio,lbxCodigo_dpto);
			for(int i=0;i<lbxCodigo_municipio.getItemCount();i++){
				Listitem listitem = lbxCodigo_municipio.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(paciente.getCodigo_municipio())){
					listitem.setSelected(true);
					break;
				}
			}
			
			tbxApellido1.setValue(paciente.getApellido1());
			tbxApellido2.setValue(paciente.getApellido2());
			tbxNombre1.setValue(paciente.getNombre1());
			tbxNombre2.setValue(paciente.getNombre2());
			tbxNro_identificacion.setValue(paciente.getDocumento());
			dtbxFecha_nacimiento.setValue(paciente.getFecha_nacimiento());
			dtbxFecha_afiliacion.setValue(afiliacionesMe.getFecha_afiliacion());
			tbxDireccion.setValue(paciente.getDireccion());
			tbxTel_oficina.setValue(paciente.getTel_res()); 
			
		chbDiscapacidad.setChecked(afiliacionesMe.getDiscapacidad().equalsIgnoreCase("s"));  
		tbxDescripcion_discapacidad.setValue(afiliacionesMe.getDescripcion_discapacidad());
		setVisibleDiscapacidad(chbDiscapacidad.isChecked());
		Utilidades.setValueFrom(lbxEstadoAfiliacion, paciente.getEstado_afiliacion());
		
		// Cargamos las observaciones
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("codigo_empresa", afiliacionesMe.getCodigo_empresa());
		map.put("codigo_sucursal", afiliacionesMe.getCodigo_sucursal());
		map.put("id_afiliado", afiliacionesMe.getId());
		List<Historial_observaciones> historial_observaciones = getServiceLocator().getServicio(Historial_observacionesService.class).listar(map);
		historialObservacionMacro.setHistorialObservaciones(historial_observaciones); 
		
		bytefoto = afiliacionesMe.getFoto_afiliados();
		if(bytefoto != null){
			AImage aImage = new AImage("foto_usr", bytefoto);
            imageUsuario.setContent(aImage);
		}
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar",
				"Error !!", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar);  
		listarMunicipios(lbxCodigo_municipio,lbxCodigo_dpto);
		setVisibleDiscapacidad(false);
		lbxTipo_identificacion.setDisabled(false);
		tbxNro_identificacion.setReadonly(false);
		btn_pasar_cotizante.setVisible(false);
		borrarImagen();
		historialObservacionMacro.limpiar();
	}

	public void listarDepartamentos(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		List<Departamentos> departamentos = this.getServiceLocator()
				.getDepartamentosService().listar(new HashMap());

		for (Departamentos dpto : departamentos) {
			listitem = new Listitem();
			listitem.setValue(dpto.getCodigo());
			listitem.setLabel(dpto.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		listboxMun.getChildren().clear();
		Listitem listitem;
		String coddep = listboxDpto.getSelectedItem().getValue().toString();
		Map<String, Object> parameters = new HashMap();
		parameters.put("coddep", coddep);
		List<Municipios> municipios = this.getServiceLocator()
				.getMunicipiosService().listar(parameters);

		if (municipios.size() > 0) {
			for (Municipios mun : municipios) {
				listitem = new Listitem();
				listitem.setValue(mun.getCodigo());
				listitem.setLabel(mun.getNombre());
				listboxMun.appendChild(listitem);
			}
		} else {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel(" -seleccione- ");
			listboxMun.appendChild(listitem);
		}

		if (listboxMun.getItemCount() > 0) {
			listboxMun.setSelectedIndex(0);
		}
	}

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

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
	
	public boolean validarForm()throws Exception{
		lbxTipo_afiliado.setStyle("background-color:white");
		lbxParentesco.setStyle("background-color:white");
		dtbxFecha_afiliacion.setStyle("background-color:white");
		tbxApellido1.setStyle("text-transform:uppercase;background-color:white");
		tbxNombre1.setStyle("text-transform:uppercase;background-color:white");
		lbxCodigo_dpto.setStyle("background-color:white");
		lbxTipo_identificacion.setStyle("background-color:white");
		tbxNro_identificacion.setStyle("text-transform:uppercase;background-color:white");
		lbxRh.setStyle("background-color:white");
		lbxTipo_vencimiento.setStyle("background-color:white");
		
		String msj = "Los campos marcados con (*) son obligatorios";
		boolean valida = true;
		
		try {
			FormularioUtil.validarCamposObligatorios(lbxTipo_afiliado,
					lbxParentesco, dtbxFecha_afiliacion, tbxApellido1,
					tbxNombre1, lbxCodigo_dpto, lbxTipo_identificacion,
					tbxNro_identificacion, lbxRh, lbxTipo_vencimiento,
					lbxEstadoAfiliacion, dtbxFecha_nacimiento);
			
            Date fecha_actual = Calendar.getInstance().getTime();
			
			if(dtbxFecha_nacimiento.getValue().compareTo(fecha_actual) > 0){
				valida = false;
				msj = "La fecha de nacimiento no puede ser mayor que la fecha actual";
			}
			
			if(valida && dtbxFecha_afiliacion.getValue().compareTo(fecha_actual) > 0){
				valida = false;
				msj = "La fecha de afiliacion no puede ser mayor que la fecha actual";
			}

			if (valida
					&& dtbxFecha_vencimiento.getValue() != null
					&& dtbxFecha_nacimiento.getValue().compareTo(
							dtbxFecha_vencimiento.getValue()) > 0) {
				valida = false;
				msj = "La fecha de vencimiento no puede ser menor o igual que la fecha de nacimiento";
			}
			
//			if(valida && dtbxFecha_nacimiento.getValue().compareTo(dtbxFecha_afiliacion.getValue()) > 0){
//				valida = false;
//				msj = "La fecha de afiliacion no puede ser menor o igual que la fecha de nacimiento";
//			}
			
			if(!valida){
				Messagebox.show(msj, 
					usuarios.getNombres()+" recuerde que...", 
					Messagebox.OK, Messagebox.EXCLAMATION);
			}else{
				FormularioUtil.limpiarComponentesRestricciones(tbxApellido1);
				FormularioUtil.limpiarComponentesRestricciones(tbxApellido2);
				FormularioUtil.limpiarComponentesRestricciones(tbxNombre1);
				FormularioUtil.limpiarComponentesRestricciones(tbxNombre2);
			}
		} catch (Exception e) {
			valida = false;
		}
		
		return valida;
	}
	
	
	private Paciente getBeanPaciente(){
		    Paciente paciente = null;
		    if(afiliacionesMeBeneficiario != null){
		    	paciente = afiliacionesMeBeneficiario.getPaciente();
				paciente.setCodigo_administradora(afiliacionesMeBeneficiario
						.getPaciente().getCodigo_administradora());
		    }else{
		    	paciente = new Paciente();
		    	paciente.setCodigo_empresa(getEmpresa().getCodigo_empresa());
				paciente.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
				paciente.setNro_identificacion(tbxNro_identificacion.getValue());
		    }
		    	
		    paciente.setTipo_identificacion(lbxTipo_identificacion.getSelectedItem().getValue().toString());
			paciente.setApellido1(tbxApellido1.getValue());
			paciente.setApellido2(tbxApellido2.getValue());
			paciente.setNombre1(tbxNombre1.getValue());
			paciente.setNombre2(tbxNombre2.getValue());
			paciente.setFecha_nacimiento(new Timestamp(dtbxFecha_nacimiento.getValue().getTime()));
			paciente.setEdad("");
			paciente.setSexo(lbxSexo.getSelectedItem().getValue().toString());
			paciente.setCodigo_dpto(lbxCodigo_dpto.getSelectedItem().getValue().toString());
			paciente.setCodigo_municipio(lbxCodigo_municipio.getSelectedItem().getValue().toString());
			paciente.setZona(lbxZona.getSelectedItem().getValue().toString());
			paciente.setLugar_exp("");
			paciente.setProfesion("");
			paciente.setTel_oficina("");
			paciente.setTel_res(tbxTel_oficina.getValue());
			paciente.setDireccion(tbxDireccion.getValue());
			paciente.setEstado_civil(""); 
			paciente.setPaciente_particula("N"); 
			paciente.setTipo_afiliado((lbxTipo_afiliado.getSelectedIndex() + 2) + "");
			paciente.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
			paciente.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
			paciente.setCreacion_user(usuarios.getCodigo().toString());
			paciente.setUltimo_user(usuarios.getCodigo().toString());
			paciente.setEstado_afiliacion("01");
			paciente.setIngresos(0);
			paciente.setGrupo_cuota("");
			paciente.setLogin(tbxLogin.getValue());
			paciente.setPassword(tbxPassword.getValue());
			paciente.setPertenencia_etnica(lbxPertenencia_etnica.getSelectedItem().getValue().toString());
			paciente.setCodigo_ocupacion(lbxCodigo_ocupacion.getSelectedItem().getValue().toString());
			paciente.setCodigo_educativo(lbxCodigo_educativo.getSelectedItem().getValue().toString());
			paciente.setEstado_afiliacion(lbxEstadoAfiliacion.getSelectedItem().getValue().toString());
			paciente.setNivel_sisben("");
			paciente.setDocumento(tbxNro_identificacion.getValue()); 
			return paciente;
	}
	
	
	public void guardarDatos()throws Exception{
		try {
			setUpperCase();
			if(validarForm()){
				//Cargamos los componentes //
				Paciente paciente = getBeanPaciente();
				Afiliaciones_me afiliaciones_me = getBeanAfiliaciones();
				afiliaciones_me.setPaciente(paciente); 
			   
			    Adicional_paciente adicional_paciente = new Adicional_paciente();
				adicional_paciente.setCodigo_empresa(paciente
						.getCodigo_empresa());
				adicional_paciente.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				adicional_paciente.setNro_identificacion(paciente
						.getNro_identificacion());
				adicional_paciente.setCarnet("");
				if (dtbxFecha_afiliacion.getValue() != null)
					adicional_paciente.setFecha_afiliacion(new Timestamp(
							dtbxFecha_afiliacion.getValue().getTime()));
				adicional_paciente.setTipo_poblacion("5");
				adicional_paciente.setFicha_sisben("0");
				adicional_paciente.setModalidad_subsidio("ST");
//				adicional_paciente.setCodigo_barrio();
			   
				afiliaciones_me.setHistorial_observaciones(historialObservacionMacro.getHistorial_observaciones()); 
			   
			    Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put(Afiliaciones_meService.AFILIACIONES, afiliaciones_me);
				mapa_datos.put(Afiliaciones_meService.PACIENTES, paciente);
				mapa_datos.put(Afiliaciones_meService.ADICIONAL_PACIENTES, adicional_paciente);
				mapa_datos.put(Afiliaciones_meService.NIVEL, Afiliaciones_meService.BENEFICIARIOS);
				mapa_datos.put(Afiliaciones_meService.HISTORIAL_OBSERVACIONES, historialObservacionMacro.getHistorial_observaciones());
				
				if(solicitud_e1 == null){
					if(update != null){
						getServiceLocator().getAfiliacionesMeService().actualizar(mapa_datos);
						afiliacionesMeAction.buscarDatos();
						Notificaciones.mostrarNotificacionInformacion("Informacion", "Datos modificados exitosamente", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES); 
						onClose();
					}else{
						if(afiliacionesMeBeneficiario == null){
							afiliaciones_me.setPaciente(paciente);
							afiliaciones_me.setNuevoBeneficiario(true);
							afiliaciones_me.setHistorial_observaciones(historialObservacionMacro.getHistorial_observaciones()); 
							afiliacionesMeAction.agregarBeneficiarios(afiliaciones_me);
							limpiarDatos();
						}else{
							afiliacionesMeAction.buscarBeneficiarios();
							onClose();
						}
						
					}
				}else{
					 Afiliaciones_me afiliacionesMeCotizante = new Afiliaciones_me();
					 afiliacionesMeCotizante.setCodigo_empresa(usuarios.getCodigo_empresa());
					 afiliacionesMeCotizante.setCodigo_sucursal(usuarios.getCodigo_sucursal());
					 afiliacionesMeCotizante.setNro_identificacion_afiliado(solicitud_e1.getNro_id_cotizante());
					 afiliacionesMeCotizante = getServiceLocator().getAfiliacionesMeService().consultar(afiliacionesMeCotizante);
					 
					 afiliaciones_me.setPaciente(paciente);
				     afiliaciones_me.setNuevoBeneficiario(true);
				     afiliaciones_me.setHistorial_observaciones(historialObservacionMacro.getHistorial_observaciones());
					 
					 afiliacionesMeCotizante.getList_beneficiarios().add(afiliaciones_me);
					 getServiceLocator().getAfiliacionesMeService().actualizar(mapa_datos);
					Messagebox.show("Afiliacion realizada satisfactoriamente", "Mensaje de Error !!", 
							Messagebox.OK, Messagebox.ERROR, new EventListener() {
								@Override
								public void onEvent(Event arg0) throws Exception {
									onClose();
								}
				   });
				}
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
		
	}
	
	private Afiliaciones_me getBeanAfiliaciones() {
		Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
		if(afiliacionesMeBeneficiario != null){
			afiliaciones_me = afiliacionesMeBeneficiario;
		}else{
			afiliaciones_me.setCodigo_empresa(getEmpresa().getCodigo_empresa());
			afiliaciones_me.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			afiliaciones_me.setNro_identificacion_afiliado(tbxNro_identificacion.getValue());
		}
		
		afiliaciones_me.setTipo_afiliado(lbxTipo_afiliado.getSelectedItem().getValue().toString());
		afiliaciones_me.setParenteco_cotizante(lbxParentesco.getSelectedItem().getValue().toString());
		afiliaciones_me.setDepartamento_afiliacion(lbxCodigo_dpto.getSelectedItem().getValue().toString());
		afiliaciones_me.setMunicipio_afiliacion(lbxCodigo_municipio.getSelectedItem().getValue().toString());
		afiliaciones_me.setZona_afiliacion(lbxZona.getSelectedItem().getValue().toString());
		afiliaciones_me.setFecha_afiliacion(new Timestamp(dtbxFecha_afiliacion.getValue().getTime()));
		afiliaciones_me.setEscolaridad("");
		afiliaciones_me.setDiscapacidad(chbDiscapacidad.isChecked() ? "S" : "N");
		afiliaciones_me.setDescripcion_discapacidad(tbxDescripcion_discapacidad.getValue());
		afiliaciones_me.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
		afiliaciones_me.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
		afiliaciones_me.setCreacion_user(usuarios.getCodigo().toString());
		afiliaciones_me.setUltimo_user(usuarios.getCodigo().toString());
		afiliaciones_me.setRh(lbxRh.getSelectedItem().getValue().toString());
		afiliaciones_me.setId(ibxId.getText());
		afiliaciones_me.setFoto_afiliados(bytefoto);
		afiliaciones_me.setObservaciones("");
		afiliaciones_me
		.setFecha_vencimiento(dtbxFecha_vencimiento.getValue() != null ? new Timestamp(
				dtbxFecha_vencimiento.getValue().getTime()) : null);

		afiliaciones_me.setTipo_vencimiento((Integer)lbxTipo_vencimiento.getSelectedItem().getValue());
		
		return afiliaciones_me;
	}

	private void loadEvents() {
		chbDiscapacidad.addEventListener("onCheck", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				setVisibleDiscapacidad(chbDiscapacidad.isChecked());
			}
		});
		
		tbxLogin.addEventListener("onChanging", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				validateLogin(arg0);
			}
		});
		
		tbxLogin.addEventListener("onBlur", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				validateLogin(arg0);
			}
		});
		
		dtbxFecha_nacimiento.addEventListener("onBlur", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				verificarFechaVencimiento();			
			}
		});
		
		lbxTipo_vencimiento.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
                verificarFechaVencimiento();			
			}
		});
	}
	
	private void verificarFechaVencimiento() {
		if(dtbxFecha_nacimiento.getValue() != null && lbxTipo_vencimiento.getSelectedItem().getValue() != null){
			 Date fechNac = dtbxFecha_nacimiento.getValue();
		        int edadVen = (Integer)lbxTipo_vencimiento.getSelectedItem().getValue();
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTimeInMillis(fechNac.getTime());
		        calendar.set(Calendar.DAY_OF_MONTH, 1);
		        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + edadVen);
		        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		        dtbxFecha_vencimiento.setValue(calendar.getTime());
		}
	}
	
	private void validateLogin(Event event){
		// activo.gif  -  estado_error.gif
		String login = tbxLogin.getValue();
		if(event instanceof InputEvent){
			login = ((InputEvent)event).getValue();
		}
		if(login != null){
			  if(!login.trim().isEmpty()){
				  login = login.toUpperCase();
					Paciente paciente = new Paciente();
					paciente.setLogin(login);
					paciente = getServiceLocator().getPacienteService().consultar(paciente);
					 //log.info("::-" + paciente);
					boolean existPacinet = paciente != null;
					
					Usuarios usuarios = new Usuarios();
					usuarios.setLogin(login);
					boolean existUsuario = getServiceLocator().getUsuariosService().consultar(usuarios)!= null;
					
				    //log.info("::-" + login);
					//log.info("Exist Paciente: " + existPacinet + " - " + existUsuario);
					
					if(existPacinet){
						//log.info("Exist Paciente Nro ID: " + paciente.getNro_identificacion() + " - " + tbxNro_identificacion.getValue());
						if(paciente.getNro_identificacion().equals(tbxNro_identificacion.getValue())){
							existPacinet = false;
						}
					}
					
					if(existPacinet || existUsuario){
						imgState.setSrc(Afiliaciones_meAction.IMAGE_CANCEL);
						imgState.setTooltiptext("Este login ya existe"); 
					}else if(!login.trim().isEmpty()){
						imgState.setSrc(Afiliaciones_meAction.IMAGE_OK);
						imgState.setTooltiptext("Login Correcto"); 
					}else{
						noImage(); 
					} 
			  }else{
				  noImage();
			  }
		}else{
			noImage();
		}
		imgState.applyProperties();
	}
	
	private void noImage(){
		imgState.setSrc(null);
		imgState.setTooltiptext(""); 
	}
	
	private void setVisibleDiscapacidad(boolean visible){
		rowDiscapacidad.setVisible(visible); 
	}
	
	@Override
	public void init() throws Exception {
		loadEvents();
		listarCombos();
		cargamosDatosAfiliados();
	}

}
