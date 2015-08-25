/*
 * ecografiaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Ecografia;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.EcografiaService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

public class EcografiaAction extends ZKWindow {


	// Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;

	@View private Listbox lbxEdad_gestional;
	
	
	@View private Datebox dtbxFum;
	@View private Datebox dtbxFpp;
	@View private Textbox tbxUtero;
	@View private Textbox tbxActividad_cardiaca;
	@View private Textbox tbxMov_fetal;
	@View private Textbox tbxSituacion;
	@View private Textbox tbxPresentacion;
	@View private Textbox tbxDorso;
	@View private Textbox tbxPlacenta;
	@View private Textbox tbxGrosor;
	@View private Textbox tbxGrado;
	@View private Textbox tbxLiquido_amniotico;
	@View private Doublebox dbxCrl;
	@View private Listbox lbxSem_crl;
	@View private Doublebox dbxDbp;
	@View private Listbox lbxSem_pc;
	@View private Doublebox dbxPc;
	@View private Listbox lbxSem_dbp;	
	@View private Doublebox dbxCa;
	@View private Listbox lbxSem_ca;
	@View private Doublebox dbxFemur;
	@View private Listbox lbxSem_femur;
	@View private Listbox lbxSexo;
	@View private Doublebox dbxPeso;
	@View private Textbox tbxOvario_derecho;
	@View private Textbox tbxOvario_izquierdo;
	@View private Textbox tbxD_s_douglas;
	@View private Textbox tbxObservaciones;
	@View private Textbox tbxDiagnostico;
	@View private Textbox tbxNotas;
	
	@View private Doublebox dbxDms;
	@View private Listbox lbxSem_dms;
	
	@View private Datebox dtbxFPPPorEcografia;
	
	@View private Toolbarbutton btGuardar;
	
	@View private Label lbGinecologo;
	
	private final String[] idsExcluyentes = { "tbxAccion" };
	
	@View private Toolbarbutton btImprimir;
	@View private Listbox lbxFormato;
	private Admision admision;
	private Ecografia ecografia;

	@Override
	public void init() {
		listarCombos();
		cargarEventoFecha();
	}

	private void cargarEventoFecha() {
		dtbxFum.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				dtbxFpp.setValue(Utilidades.calcularFechaEsperadaParto(dtbxFum.getValue())); 
				String semanas = Utilidades.getSemanasEmbarazo(dtbxFum.getValue());
				Utilidades.setValueFrom(lbxEdad_gestional, semanas); 
			}
		});
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxSexo, true, getServiceLocator());
		incializarSemanas();
		inicializarEcografia();
	}
	
	private void inicializarEcografia() {
		if(admision != null){
			Ecografia ecografia = new Ecografia();
			ecografia.setCodigo_empresa(codigo_empresa);
			ecografia.setCodigo_sucursal(codigo_sucursal);
			ecografia.setNro_ingreso(admision.getNro_ingreso());
			ecografia.setIdentificacion(admision.getNro_identificacion()); 
			//log.info("Ecografia a consultar: " + ecografia); 
			ecografia = getServiceLocator().getServicio(EcografiaService.class).consultarPorFiltros(ecografia);
			if(ecografia != null){
				try {
					mostrarDatos(ecografia);
					btGuardar.setDisabled(true); 
				} catch (Exception e) {
					MensajesUtil.mensajeError(e, null, this); 
				} 
			}
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
	}
	
	public void habialitarImpresion(boolean habilitar){
		btImprimir.setDisabled(!habilitar);
		lbxFormato.setDisabled(!habilitar); 
		lbGinecologo.setVisible(habilitar);
		
		if(!btImprimir.isVisible())
		   btImprimir.setVisible(true);
	}
 
	private void incializarSemanas() {
		Listbox[] listitems = { lbxSem_ca, lbxSem_crl, lbxSem_dbp,
				lbxSem_femur, lbxSem_pc, lbxEdad_gestional, lbxSem_dms};

		for (Listbox listbox : listitems) {
			listbox.appendChild(new Listitem("-seleccione-", ""));
		}
		for (int i = 0; i <= 42; i++) {
			for (Listbox listbox : listitems) {
				listbox.appendChild(new Listitem(i + "", i + ""));
			}
		}
		for (Listbox listbox : listitems) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
        boolean valido = true;
		try {
			FormularioUtil.validarCamposObligatorios(tbxMov_fetal, tbxSituacion,
					tbxPresentacion, tbxDorso, tbxPlacenta, 
					tbxGrosor, lbxEdad_gestional, dtbxFum, dtbxFpp, 
					tbxUtero, tbxActividad_cardiaca,  tbxMov_fetal, tbxSituacion, tbxPresentacion, tbxDorso, tbxPlacenta,
					tbxGrosor, tbxGrado, tbxLiquido_amniotico, dbxCrl, lbxSem_crl, dbxDbp, lbxSem_dbp,
					dbxPc, lbxSem_pc, dbxCa, lbxSem_ca, dbxFemur, lbxSem_femur, dbxPeso, lbxSexo, tbxOvario_derecho, tbxOvario_izquierdo,
					tbxD_s_douglas, tbxObservaciones, tbxDiagnostico, tbxNotas, dtbxFPPPorEcografia);
		} catch (WrongValueException e) {
			valido = false;
		}
		
		if(valido){
			Date fecha_fum = dtbxFum.getValue();
			if(fecha_fum != null && fecha_fum.compareTo(Calendar.getInstance().getTime()) > 0){
				 valido = false;
				 MensajesUtil.mensajeAlerta("Advertencia", "La fecha de ultima mestrucion no puede ser igual a la fecha actual."); 
			}
		}
		return valido;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getEcografiaService().setLimit(
					"limit 25 offset 0");

			List<Ecografia> lista_datos = getServiceLocator()
					.getEcografiaService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Ecografia ecografia : lista_datos) {
				rowsResultado.appendChild(crearFilas(ecografia, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Ecografia ecografia = (Ecografia) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ecografia);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(ecografia);
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

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);
				// Cargamos los componentes //
				
				Paciente paciente = new Paciente();
				paciente.setCodigo_empresa(codigo_empresa);
				paciente.setCodigo_sucursal(codigo_sucursal);
				paciente.setNro_identificacion(admision.getNro_identificacion());
				paciente = getServiceLocator().getServicio(PacienteService.class).consultar(paciente);
				
				if(paciente == null)
					throw new ValidacionRunTimeException("El paciente con el nro identificacion " + admision.getNro_identificacion() + " no existe");
				
				String edad = Util.getEdadPrsentacion(paciente.getFecha_nacimiento());
				
				Ecografia ecografia = new Ecografia();
				ecografia.setCodigo_empresa(empresa.getCodigo_empresa());
				ecografia.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ecografia.setNro_ingreso(admision.getNro_ingreso());
				ecografia.setIdentificacion(admision.getNro_identificacion()); 
				ecografia.setFecha_inicial(new Timestamp(Calendar.getInstance().getTimeInMillis()));  
				ecografia.setEdad_gestional(lbxEdad_gestional.getSelectedItem().getValue().toString());
				ecografia.setFum(new Timestamp(dtbxFum.getValue().getTime()));
				ecografia.setFpp(new Timestamp(dtbxFpp.getValue().getTime()));
				ecografia.setUtero(tbxUtero.getValue());
				ecografia.setActividad_cardiaca(tbxActividad_cardiaca.getValue());
				ecografia.setMov_fetal(tbxMov_fetal.getValue());
				ecografia.setSituacion(tbxSituacion.getValue());
				ecografia.setPresentacion(tbxPresentacion.getValue());
				ecografia.setDorso(tbxDorso.getValue());
				ecografia.setPlacenta(tbxPlacenta.getValue());
				ecografia.setGrosor(tbxGrosor.getValue());
				ecografia.setGrado(tbxGrado.getValue());
				ecografia.setEdad(edad); 
				ecografia.setLiquido_amniotico(tbxLiquido_amniotico.getValue());
				ecografia.setCrl(ConvertidorDatosUtil.convertirDato(dbxCrl
						.getValue()));
				ecografia.setSem_crl(lbxSem_crl.getSelectedItem().getValue().toString());
				ecografia.setDbp(ConvertidorDatosUtil.convertirDato(dbxDbp
						.getValue()));
				ecografia.setPc(ConvertidorDatosUtil.convertirDato(dbxPc
						.getValue()));
				ecografia.setSem_dbp(lbxSem_dbp.getSelectedItem().getValue().toString());
				ecografia.setSem_pc(lbxSem_pc.getSelectedItem().getValue().toString());
				ecografia.setCa(ConvertidorDatosUtil.convertirDato(dbxCa
						.getValue()));
				ecografia.setSem_ca(lbxSem_ca.getSelectedItem().getValue().toString());
				ecografia.setFemur(ConvertidorDatosUtil.convertirDato(dbxFemur
						.getValue()));
				ecografia.setSem_femur(lbxSem_femur.getSelectedItem().getValue().toString());
				ecografia.setSexo(lbxSexo.getSelectedItem().getValue()
						.toString());
				ecografia.setPeso(ConvertidorDatosUtil.convertirDato(dbxPeso
						.getValue()));
				ecografia.setOvario_derecho(tbxOvario_derecho.getValue());
				ecografia.setOvario_izquierdo(tbxOvario_izquierdo.getValue());
				ecografia.setD_s_douglas(tbxD_s_douglas.getValue());
				ecografia.setObservaciones(tbxObservaciones.getValue());
				ecografia.setDiagnostico(tbxDiagnostico.getValue());
				ecografia.setNotas(tbxNotas.getValue());
				ecografia.setCodigo_ginecolo(usuarios.getCodigo());
				ecografia.setFpp_ecografia(new Timestamp(dtbxFPPPorEcografia.getValue().getTime())); 
				ecografia.setDms(ConvertidorDatosUtil.convertirDato(dbxDms.getValue()));
				ecografia.setSem_dms(lbxSem_dms.getSelectedItem().getValue().toString()); 
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("ecografia", ecografia); 
				params.put("accion",  this.ecografia ==  null ? "registrar" : "modificar");
				params.put("admision", admision);
				getServiceLocator().getEcografiaService().guardar(params);
				
				// Actualizamos la instancia para imprimir
			   this.ecografia = ecografia;
				
				habialitarImpresion(true); 
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		ecografia = (Ecografia) obj; 
		try {
//			tbxVia_ingreso.setValue(ecografia.getVia_ingreso());
			Utilidades.setValueFrom(lbxEdad_gestional, ecografia.getEdad_gestional()); 
			dtbxFum.setValue(ecografia.getFum());
			dtbxFpp.setValue(ecografia.getFpp());
			tbxUtero.setValue(ecografia.getUtero());
			tbxActividad_cardiaca.setValue(ecografia.getActividad_cardiaca());
			tbxMov_fetal.setValue(ecografia.getMov_fetal());
			tbxSituacion.setValue(ecografia.getSituacion());
			tbxPresentacion.setValue(ecografia.getPresentacion());
			tbxDorso.setValue(ecografia.getDorso());
			tbxPlacenta.setValue(ecografia.getPlacenta());
			tbxGrosor.setValue(ecografia.getGrosor());
			tbxGrado.setValue(ecografia.getGrado());
			tbxLiquido_amniotico.setValue(ecografia.getLiquido_amniotico());
			dbxCrl.setValue(ConvertidorDatosUtil.convertirDato(ecografia
					.getCrl()));
			
			Utilidades.setValueFrom(lbxSem_crl, ecografia.getSem_crl()); 
			Utilidades.setValueFrom(lbxSem_dbp, ecografia.getSem_dbp()); 
			Utilidades.setValueFrom(lbxSem_pc, ecografia.getSem_pc()); 
			Utilidades.setValueFrom(lbxSem_ca, ecografia.getSem_ca()); 
			Utilidades.setValueFrom(lbxSem_femur, ecografia.getSem_femur()); 
			Utilidades.setValueFrom(lbxSem_dms, ecografia.getSem_dms()); 
			
			dbxDbp.setValue(ConvertidorDatosUtil.convertirDato(ecografia
					.getDbp()));
			dbxPc.setValue(ConvertidorDatosUtil.convertirDato(ecografia
					.getDbp()));
			dbxCa.setValue(ConvertidorDatosUtil.convertirDato(ecografia.getCa()));
			dbxFemur.setValue(ConvertidorDatosUtil.convertirDato(ecografia
					.getFemur()));
			dbxDms.setValue(ConvertidorDatosUtil.convertirDato(ecografia
					.getDms()));
			Utilidades.seleccionarListitem(lbxSexo, ecografia.getSexo());
			dbxPeso.setValue(ConvertidorDatosUtil.convertirDato(ecografia
					.getPeso()));
			tbxOvario_derecho.setValue(ecografia.getOvario_derecho());
			tbxOvario_izquierdo.setValue(ecografia.getOvario_izquierdo());
			tbxD_s_douglas.setValue(ecografia.getD_s_douglas());
			tbxObservaciones.setValue(ecografia.getObservaciones());
			tbxDiagnostico.setValue(ecografia.getDiagnostico());
			tbxNotas.setValue(ecografia.getNotas());
			dtbxFPPPorEcografia.setValue(ecografia.getFpp_ecografia()); 
			
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(codigo_empresa);
			prestadores.setCodigo_sucursal(codigo_sucursal);
			prestadores.setNro_identificacion(ecografia.getCodigo_ginecolo()); 
			prestadores = getServiceLocator().getServicio(PrestadoresService.class).consultar(prestadores); 
			
			getFellow("rowGinecologo").setVisible(true); 
			lbGinecologo.setValue(prestadores != null ? prestadores.getNro_identificacion() + " " + prestadores.getNombres() + " " + prestadores.getApellidos() : "Ginecólogo no encontrado");  
			
			FormularioUtil.deshabilitarComponentes(this, true);
			// Mostramos la vista //
			tbxAccion.setText("modificar");
//			accionForm(true, tbxAccion.getText());
			habialitarImpresion(true);
			btImprimir.setVisible(true);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	
	public void imprimir()throws Exception{
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "EcografiaGinecostetrica");
		paramRequest.put("codigo_empresa", ecografia.getCodigo_empresa());
		paramRequest.put("codigo_sucursal", ecografia.getCodigo_sucursal());
		paramRequest.put("identificacion", ecografia.getIdentificacion());
		paramRequest.put("nro_ingreso", ecografia.getNro_ingreso()); 
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue().toString());
		
		Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window)componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}


	public void eliminarDatos(Object obj) throws Exception {
		Ecografia ecografia = (Ecografia) obj;
		try {
			int result = getServiceLocator().getEcografiaService().eliminar(
					ecografia);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminado satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}
}