package healthmanager.controller.admin;

import healthmanager.controller.ZKWindow.View;
import healthmanager.modelo.bean.Admin;
import healthmanager.modelo.bean.Usuarios;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

import com.framework.res.CargardorDeDatos;
import com.framework.res.Res;
import com.framework.res.TabsValidator;

public class MainAdministradoresAction extends Window implements AfterCompose {

//	private static Logger log = Logger
//			.getLogger(MainAdministradoresAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

//	private Bandbox bandboxInstitucion;
//	private Listbox lbxTipoUsuario;
//	private Listbox lbxCodigo_documento;
//	private Textbox tbxCodigo_documento;
//	private Textbox tbxNombres;
//	private Textbox tbxApellidos;
//	private Grid gridResultado;
//	private Rows rowsResultado;
//	private Label label_informacion;
	private Admin admin;
//	private Iframe iframe;
	
	@View private Listbox lbxAnios;
    @View private Listbox lbxPeriodo;
	
	@View Tabs tab_contenedora;
	@View Tabpanels tabpanelcontenedora;
	@View Label lbUsuario;

	@Override
	public void afterCompose() {
		CargardorDeDatos.initComponents(this);
		cargarDatosSesion();
		loadPeriodo();
		showName();
	}
	
	private void showName(){
		lbUsuario.setValue((admin.getNombre() + " " + admin.getApellido())
				.toUpperCase());
	}
	
	private void loadPeriodo() {
		/* cargamos anios */
		lbxAnios.getChildren().clear();
		for (String year : Res.getAnnos(5)) { 
			Listitem listitem = new Listitem();
			listitem.setValue(year);
			listitem.setLabel(year);
			lbxAnios.appendChild(listitem);
		}
		if (lbxAnios.getItemCount() > 0) {
			lbxAnios.setSelectedIndex(0);
		}
		
		/* cargamos periodos */
		lbxPeriodo.getChildren().clear();
		for (int i = 0; i < 12; i++) {
			Listitem listitem = new Listitem();
			listitem.setValue(i);
			listitem.setLabel((Res.getNombreMes(i)+"").toUpperCase()); 
			lbxPeriodo.appendChild(listitem);
		}
		if (lbxPeriodo.getItemCount() > 0) {
			lbxPeriodo.setSelectedIndex(0);
		}
		initPeriodo();
		loadEvent(); 
	}

	private void loadEvent() {
		EventListener<Event> eventListener = new EventListener<Event>() {
			@Override 
			public void onEvent(Event arg0) throws Exception {
				HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest(); 
				request.getSession().setAttribute("anio", Integer.parseInt(lbxAnios.getSelectedItem().getValue().toString()));
				request.getSession().setAttribute("mes", lbxPeriodo.getSelectedItem().getValue());
				Executions.getCurrent().sendRedirect("");  
			}
		};
		lbxPeriodo.addEventListener("onSelect", eventListener);
		lbxAnios.addEventListener("onSelect", eventListener);
	}

	private void initPeriodo() {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest(); 
		int anio = (Integer) request.getSession().getAttribute("anio");
		int mes  = (Integer) request.getSession().getAttribute("mes");
		lbxPeriodo.setSelectedIndex(mes);
		for(int i=0;i<lbxAnios.getItemCount();i++){
			Listitem listitem = lbxAnios.getItemAtIndex(i);
			if(listitem.getValue().toString().equals("" + anio)){
				listitem.setSelected(true);
				break;
			}
		}
	}

	public void eliminarSession() {
		try {
			HttpServletRequest request = (HttpServletRequest) Executions
					.getCurrent().getNativeRequest();
			request.getSession().invalidate();
			Executions.getCurrent().sendRedirect("../");
		} catch (Exception e) {e.printStackTrace();}
	}

	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		Object userInSession = request.getSession().getAttribute("usuario");
		if (userInSession != null) {
			if (userInSession instanceof Usuarios) {
				Executions.sendRedirect("../pages/inicioSesion.zul");
			} else {
				admin = (Admin) userInSession;
			}
		} else {
			Executions.sendRedirect("index.zul");
			return;
		}
	}

	// public void llenarInstitucion(String codigo_institucion) {
	// // if (matriculacodigoAlumno.gets)
	// Listbox listbox = (Listbox) this.getFellow("listboxInstituciones");
	// listbox.getItems().clear();
	// ////log.info("busqueda: "+codigoalumno.trim().toUpperCase());
	// Map<String, Object> parameters = new HashMap();
	// parameters.put("busqueda_avanzada", "");
	// parameters.put("value", "%"+codigo_institucion.trim().toUpperCase()+"%");
	//
	// List<Institucion> lista_institucion =
	// getServiceLocator().getInstitucionService().listar(parameters);
	//
	// for (int i = 0; i < lista_institucion.size(); i++) {
	// Institucion institucion = lista_institucion.get(i);
	// //if (identificacion_alumno.getApellido1().contains(codigoalumno)) {
	// Listitem listitem = new Listitem();
	// listitem.setValue(institucion.getCodigo_dane());
	// listitem.setLabel(institucion.getCodigo_dane()+" "+institucion.getNombre_ie());
	// listbox.appendChild(listitem);
	// //}
	// }
	// listbox.setMold("paging");
	// listbox.setPageSize(20);
	// listbox.applyProperties();
	// listbox.invalidate();
	// }
	//
	// public void buscar_usuarios()throws Exception{
	// if(bandboxInstitucion.getValue().trim().isEmpty()){
	// Messagebox.show("Los campos marcados con (*) son obligatorios",
	// " recuerde que", Messagebox.OK, Messagebox.EXCLAMATION);
	// }else{
	// String codigo_dane = bandboxInstitucion.getValue();
	// String tipo_usuario =
	// lbxTipoUsuario.getSelectedItem().getValue().toString();
	// String codigo_documento =
	// lbxCodigo_documento.getSelectedItem().getValue().toString();
	// String value_codigo_documento = tbxCodigo_documento.getText().trim();
	// String nombres = tbxNombres.getValue().trim();
	// String apellidos = tbxApellidos.getValue().trim();
	// Map<String, Object> parameters = new HashMap();
	// parameters.put("codigo_dane", codigo_dane);
	// if(tipo_usuario.equals("usuarios")){
	// if(!value_codigo_documento.isEmpty()){
	// if(codigo_documento.equals("cod_usr")){
	// parameters.put("cod_usr", value_codigo_documento);
	// }else{
	// parameters.put("nro_documento", value_codigo_documento);
	// }
	// }
	// if(!nombres.isEmpty()){
	// parameters.put("nom_usr", nombres.toLowerCase());
	// }
	// if(!apellidos.isEmpty()){
	// parameters.put("ape_usr", apellidos.toLowerCase());
	// }
	//
	// //log.info(parameters);
	//
	// List<Usuarios> listado_usuarios =
	// getServiceLocator().getUsuariosService().listar_usuarios(parameters);
	// rowsResultado.getChildren().clear();
	//
	// for(Usuarios usuarios : listado_usuarios){
	// rowsResultado.appendChild(crearFilas(usuarios));
	// }
	// gridResultado.setVisible(true);
	// gridResultado.setMold("paging");
	// gridResultado.setPageSize(20);
	//
	// gridResultado.applyProperties();
	// gridResultado.invalidate();
	// gridResultado.setVisible(true);
	// }else{
	// Messagebox.show("Hasta el momento solo esta disponible buscar las contraseñas de los Recusrsos Humanos",
	// "En construccion", Messagebox.OK, Messagebox.EXCLAMATION);
	// }
	// }
	// }
	//
	// public Row crearFilas(Object objeto){
	// Row fila = new Row();
	// fila.setStyle("text-align: justify;nowrap:nowrap");
	// if(objeto instanceof Usuarios){
	// Usuarios usuarios = (Usuarios)objeto;
	// String roles = "";
	// List<Map> lista_roles =
	// getServiceLocator().getRoles_usuariosService().roles_institucion(usuarios.getCod_usr());
	// for(Map datos : lista_roles){
	// String rol = (String)datos.get("rol_elemento");
	// roles = roles+rol.substring(0,5).toUpperCase()+"; ";
	// }
	//
	// fila.appendChild(new Label(""+usuarios.getCod_usr()));
	// fila.appendChild(new Label(""+usuarios.getNom_usr()));
	// fila.appendChild(new Label(""+usuarios.getApe_usr()));
	// fila.appendChild(new Label(""+usuarios.getNro_documento()));
	// fila.appendChild(new Label(roles));
	// fila.appendChild(new Label(""+usuarios.getLog_usr()));
	// fila.appendChild(new Label(""+usuarios.getPass_usr()));
	// }
	// return fila;
	// }
	//
	// public ServiceLocator getServiceLocator() {
	// if (serviceLocator == null) {
	// serviceLocator = new ServiceLocatorImpl((ServletContext)
	// (Executions.getCurrent().getDesktop().getWebApp().getNativeContext()));
	// }
	// return serviceLocator;
	// }

	public void goPestania(String page) {
	}

	public void addTab(String name, String url) {
		TabsValidator.addTabParam(name, url, tab_contenedora,
				tabpanelcontenedora, this);
	}

	public void ejecutarOperacionPeligrosa() throws Exception {
		/*
		 * Matricula mat = new Matricula(); mat.setGrado("26"); List<Matricula>
		 * lista_matriculas =
		 * getServiceLocator().getMatriculaService().listar(mat);
		 * //log.info("lista_matriculas : "+lista_matriculas.size());
		 * for(Matricula matricula : lista_matriculas){ Sedes sedes = new
		 * Sedes(); sedes.setCodigo_dane_ie(matricula.getCodigo_dane());
		 * sedes.setCons_sede(matricula.getCons_sede()); sedes =
		 * getServiceLocator().getSedesService().consultar(sedes);
		 * 
		 * Sedes sedes_aux = new Sedes();
		 * sedes_aux.setCodigo_dane_ie(matricula.getCodigo_dane());
		 * sedes_aux.setSede_ciclo(sedes.getCons_sede()); sedes_aux =
		 * getServiceLocator().getSedesService().consultarSedeCiclo6(sedes_aux);
		 * if(sedes_aux == null){ sedes_aux = (Sedes)sedes.clone(); String
		 * cons_sede =
		 * getServiceLocator().getSedesService().obtenerUltimoConsecutivo
		 * (sedes); sedes_aux.setNombre_sede(sedes_aux.getNombre_sede()+"_c");
		 * sedes_aux.setCons_sede(cons_sede);
		 * sedes_aux.setSede_ciclo(sedes.getCons_sede());
		 * getServiceLocator().getSedesService().crear(sedes_aux); }
		 * 
		 * Grupos grupos = new Grupos();
		 * grupos.setCodigo_dane(matricula.getCodigo_dane());
		 * grupos.setCons_sede(matricula.getCons_sede());
		 * grupos.setGrupo_anio(matricula.getAnio_inf());
		 * grupos.setGrado_codigo(matricula.getGrado());
		 * grupos.setGrupo_codigo(matricula.getGrupo());
		 * 
		 * grupos = getServiceLocator().getGruposService().consultar(grupos);
		 * Grupos grupos_aux = (Grupos)grupos.clone();
		 * grupos_aux.setCons_sede(sedes_aux.getCons_sede());
		 * 
		 * Grado grado = grupos.getGrado();
		 * 
		 * 
		 * Identificacion_alumno identificacion_alumno =
		 * matricula.getIdentificacion_alumno();
		 * 
		 * }
		 */
	}

}
