/*
 * ficha_epidemiologia_n28Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package healthmanager.controller;


import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.bean.Ficha_epidemiologia_n28;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n28Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n28>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n28Action.class);
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_ficha;
	@View private Textbox tbxCodigo_diagnostico;
	@View private Datebox dtbxFecha_inicial;
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad;
	@View private Textbox tbxIdentificacion;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View private Textbox tbxNombre_ape_madre;
	@View private Listbox lbxTipo_id_madre;
	@View private Textbox tbxIdentificacion_madre;
	@View private Intbox ibxNumero_de_embarazo;
	@View private Intbox ibxNumero_de_perdidas;
	@View private Intbox ibxNumero_de_cesareas;
	@View private Radiogroup rdbToma_de_serologia;
	@View private Datebox dtbxFecha_de_serologia;
	@View private Radiogroup rdbResultado_de_serologia;
	@View private Radiogroup rdbTratamiento_terminado;
	@View private Radiogroup rdbToma_igm;
	@View private Datebox dtbxFecha_toma_igm;
	@View private Radiogroup rdbResultado_igm;
	@View private Radiogroup rdbTratamiento_terminado2;
	@View private Radiogroup rdbRecibio_vacuna;
	@View private Datebox dtbxMes_anio;
	@View private Radiogroup rdbRecibio_vacuna2;
	@View private Datebox dtbxMes_anio2;
	@View private Radiogroup rdbSintomatologia_confirmacion;
	@View private Radiogroup rdbTrimestre;
	@View private Radiogroup rdbBiologica;
	@View private Radiogroup rdbTrimestre2;
	@View private Radiogroup rdbAmbiental;
	@View private Radiogroup rdbTrimestre3;
	@View private Radiogroup rdbNunguna;
	@View private Radiogroup rdbTrimestre4;
	@View private Radiogroup rdbMedicamento;
	@View private Radiogroup rdbTrimestre5;
	@View private Radiogroup rdbOtra;
	@View private Radiogroup rdbTrimestre6;
	@View private Radiogroup rdbGemelo;
	@View private Radiogroup rdbNativivo;
	@View private Radiogroup rdbNatimorto;
	@View private Textbox tbxSemana_gestacion;
	@View private Intbox ibxEdad_gestacional;
	@View private Intbox ibxApgar;
	@View private Doublebox dbxPeso;
	@View private Radiogroup rdbToma_de_muestra;
	@View private Datebox dtbxFecha_muestra;
	@View private Radiogroup rdbAnencefalia;
	@View private Radiogroup rdbCriptorquidia;
	@View private Radiogroup rdbMicrocefalila;
	@View private Radiogroup rdbAno_imperforado;
	@View private Radiogroup rdbDefectos_de_pared_abd;
	@View private Radiogroup rdbPolidactilia;
	@View private Radiogroup rdbAnomalias_auditivas;
	@View private Radiogroup rdbDisplasia_esqueletica;
	@View private Radiogroup rdbPolimalformado;
	@View private Radiogroup rdbAnomalias_funcionales2;
	@View private Radiogroup rdbEspina_bifida;
	@View private Radiogroup rdbPurpura;
	@View private Radiogroup rdbAnomalias_oculares;
	@View private Radiogroup rdbFisura_oral;
	@View private Radiogroup rdbReduccion_de_miembros;
	@View private Radiogroup rdbAnotia_o_microtia;
	@View private Radiogroup rdbGemelos_acoplados;
	@View private Radiogroup rdbSindactilia;
	@View private Radiogroup rdbAtresia_esofagia;
	@View private Radiogroup rdbGenitales_ambiguos;
	@View private Radiogroup rdbSindrome_down;
	@View private Radiogroup rdbAtresia_intestinal;
	@View private Radiogroup rdbHemangioma;
	@View private Radiogroup rdbTalipes;
	@View private Radiogroup rdbCardiopatia;
	@View private Radiogroup rdbHepatoesplenomegalia;
	@View private Radiogroup rdbBajo_peso_nacimiento;
	@View private Radiogroup rdbCatarata_congenita;
	@View private Radiogroup rdbHidrocefalia;
	@View private Radiogroup rdbAnomalias_funcionales;
	@View private Radiogroup rdbCefalocele;
	@View private Radiogroup rdbHipoacusia;
	@View private Radiogroup rdbHipospadias;
	@View private Radiogroup rdbInfecciones_congenitas;
	@View private Radiogroup rdbOtras;
	@View private Textbox tbxCual_otra;
	@View private Textbox tbxBreve_descripcion;
	@View private Textbox tbxDiligenciado_por;
	@View private Textbox tbxTelefono_de_contacto;
	@View private Toolbarbutton btGuardar;
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n28 ficha = new Ficha_epidemiologia_n28();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n28) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 28-------> "+ficha);
			
			mostrarDatos(ficha);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
		}
	}
	
	
	public void listarCombos(){
		listarParameter();
		Utilidades.listarElementoListbox(lbxTipo_id_madre,true,getServiceLocator());
	}
	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			buscarDatos();
			btGuardar.setVisible(false);
		}else{
			btGuardar.setVisible(true);
			//buscarDatos();
			limpiarDatos();
			dtbxMes_anio.setValue(null);
			dtbxMes_anio2.setValue(null);
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
	}
	
	//Metodo para validar campos del formulario //
	public boolean validarFichaEpidemiologia(){
		
		tbxIdentificacion.setStyle("text-transform:uppercase;background-color:white");
		
		boolean valida = true;
		
		if(tbxIdentificacion.getText().trim().equals("")){
			tbxIdentificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(!valida){
			MensajesUtil.mensajeAlerta(usuarios.getNombres()+" recuerde que...","Los campos marcados con (*) son obligatorios");
		}
		
		return valida;
	}
	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}
			
			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
			
			List<Ficha_epidemiologia_n28> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
					Ficha_epidemiologia_n28.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n28 ficha_epidemiologia_n28 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n28, this));
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
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Ficha_epidemiologia_n28 ficha_epidemiologia_n28 = (Ficha_epidemiologia_n28)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n28.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n28.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n28.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n28);
			}
		});
		hbox.appendChild(img);

		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n28 obtenerFichaEpidemiologia(){
				
				Ficha_epidemiologia_n28 ficha_epidemiologia_n28 = new Ficha_epidemiologia_n28();
				ficha_epidemiologia_n28.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n28.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n28.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n28.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n28.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n28.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n28.setNombre_ape_madre(tbxNombre_ape_madre.getValue());
				ficha_epidemiologia_n28.setTipo_id_madre(lbxTipo_id_madre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setIdentificacion_madre(tbxIdentificacion_madre.getValue());
				ficha_epidemiologia_n28.setNumero_de_embarazo((ibxNumero_de_embarazo.getValue()!=null?ibxNumero_de_embarazo.getValue():0));
				ficha_epidemiologia_n28.setNumero_de_perdidas((ibxNumero_de_perdidas.getValue()!=null?ibxNumero_de_perdidas.getValue():0));
				ficha_epidemiologia_n28.setNumero_de_cesareas((ibxNumero_de_cesareas.getValue()!=null?ibxNumero_de_cesareas.getValue():0));
				ficha_epidemiologia_n28.setToma_de_serologia(rdbToma_de_serologia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setFecha_de_serologia(new Timestamp(dtbxFecha_de_serologia.getValue().getTime()));
				ficha_epidemiologia_n28.setResultado_de_serologia(rdbResultado_de_serologia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setTratamiento_terminado(rdbTratamiento_terminado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setToma_igm(rdbToma_igm.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setFecha_toma_igm(new Timestamp(dtbxFecha_toma_igm.getValue().getTime()));
				ficha_epidemiologia_n28.setResultado_igm(rdbResultado_igm.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setTratamiento_terminado2(rdbTratamiento_terminado2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setRecibio_vacuna(rdbRecibio_vacuna.getSelectedItem().getValue().toString());
				
				if(dtbxMes_anio.getValue() != null){
					ficha_epidemiologia_n28.setMes_anio(new Timestamp(dtbxMes_anio.getValue().getTime()));
				}
				
				if(dtbxMes_anio2.getValue() != null){
					ficha_epidemiologia_n28.setMes_anio2(new Timestamp(dtbxMes_anio2.getValue().getTime()));
				}		
				
				
				ficha_epidemiologia_n28.setRecibio_vacuna2(rdbRecibio_vacuna2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setSintomatologia_confirmacion(rdbSintomatologia_confirmacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setTrimestre(rdbTrimestre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setBiologica(rdbBiologica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setTrimestre2(rdbTrimestre2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setAmbiental(rdbAmbiental.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setTrimestre3(rdbTrimestre3.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setNunguna(rdbNunguna.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setTrimestre4(rdbTrimestre4.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setMedicamento(rdbMedicamento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setTrimestre5(rdbTrimestre5.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setOtra(rdbOtra.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setTrimestre6(rdbTrimestre6.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setGemelo(rdbGemelo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setNativivo(rdbNativivo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setNatimorto(rdbNatimorto.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setSemana_gestacion(tbxSemana_gestacion.getValue());
				ficha_epidemiologia_n28.setEdad_gestacional((ibxEdad_gestacional.getValue()!=null?ibxEdad_gestacional.getValue():0));
				ficha_epidemiologia_n28.setApgar((ibxApgar.getValue()!=null?ibxApgar.getValue():0));
				ficha_epidemiologia_n28.setPeso((dbxPeso.getValue()!=null?dbxPeso.getValue():0.00));
				ficha_epidemiologia_n28.setToma_de_muestra(rdbToma_de_muestra.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setFecha_muestra(new Timestamp(dtbxFecha_muestra.getValue().getTime()));
				ficha_epidemiologia_n28.setAnencefalia(rdbAnencefalia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setCriptorquidia(rdbCriptorquidia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setMicrocefalila(rdbMicrocefalila.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setAno_imperforado(rdbAno_imperforado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setDefectos_de_pared_abd(rdbDefectos_de_pared_abd.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setPolidactilia(rdbPolidactilia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setAnomalias_auditivas(rdbAnomalias_auditivas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setDisplasia_esqueletica(rdbDisplasia_esqueletica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setPolimalformado(rdbPolimalformado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setAnomalias_funcionales2(rdbAnomalias_funcionales2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setEspina_bifida(rdbEspina_bifida.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setPurpura(rdbPurpura.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setAnomalias_oculares(rdbAnomalias_oculares.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setFisura_oral(rdbFisura_oral.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setReduccion_de_miembros(rdbReduccion_de_miembros.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setAnotia_o_microtia(rdbAnotia_o_microtia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setGemelos_acoplados(rdbGemelos_acoplados.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setSindactilia(rdbSindactilia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setAtresia_esofagia(rdbAtresia_esofagia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setGenitales_ambiguos(rdbGenitales_ambiguos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setSindrome_down(rdbSindrome_down.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setAtresia_intestinal(rdbAtresia_intestinal.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setHemangioma(rdbHemangioma.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setTalipes(rdbTalipes.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setCardiopatia(rdbCardiopatia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setHepatoesplenomegalia(rdbHepatoesplenomegalia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setBajo_peso_nacimiento(rdbBajo_peso_nacimiento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setCatarata_congenita(rdbCatarata_congenita.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setHidrocefalia(rdbHidrocefalia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setAnomalias_funcionales(rdbAnomalias_funcionales.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setCefalocele(rdbCefalocele.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setHipoacusia(rdbHipoacusia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setHipospadias(rdbHipospadias.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setInfecciones_congenitas(rdbInfecciones_congenitas.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setOtras(rdbOtras.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n28.setCual_otra(tbxCual_otra.getValue());
				ficha_epidemiologia_n28.setBreve_descripcion(tbxBreve_descripcion.getValue());
				ficha_epidemiologia_n28.setDiligenciado_por(tbxDiligenciado_por.getValue());
				ficha_epidemiologia_n28.setTelefono_de_contacto(tbxTelefono_de_contacto.getValue());
				ficha_epidemiologia_n28.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n28.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n28.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n28.setDelete_date(null);
				ficha_epidemiologia_n28.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n28.setDelete_user(null);
				
				return ficha_epidemiologia_n28;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n28 obj)throws Exception{
		Ficha_epidemiologia_n28 ficha_epidemiologia_n28 = (Ficha_epidemiologia_n28)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n28.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n28.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n28.getFecha_inicial());
			tbxIdentificacion.setValue(ficha_epidemiologia_n28.getIdentificacion());

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			tbxNombre_ape_madre.setValue(ficha_epidemiologia_n28.getNombre_ape_madre());
			for(int i=0;i<lbxTipo_id_madre.getItemCount();i++){
				Listitem listitem = lbxTipo_id_madre.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(ficha_epidemiologia_n28.getTipo_id_madre())){
					listitem.setSelected(true);
					i = lbxTipo_id_madre.getItemCount();
				}
			}
			tbxIdentificacion_madre.setValue(ficha_epidemiologia_n28.getIdentificacion_madre());
			obtenerAdmision(admision);
			ibxNumero_de_embarazo.setValue(ficha_epidemiologia_n28.getNumero_de_embarazo());
			ibxNumero_de_perdidas.setValue(ficha_epidemiologia_n28.getNumero_de_perdidas());
			ibxNumero_de_cesareas.setValue(ficha_epidemiologia_n28.getNumero_de_cesareas());
			Utilidades.seleccionarRadio(rdbToma_de_serologia, ficha_epidemiologia_n28.getToma_de_serologia());
			dtbxFecha_de_serologia.setValue(ficha_epidemiologia_n28.getFecha_de_serologia());
			Utilidades.seleccionarRadio(rdbResultado_de_serologia, ficha_epidemiologia_n28.getResultado_de_serologia());
			Utilidades.seleccionarRadio(rdbTratamiento_terminado, ficha_epidemiologia_n28.getTratamiento_terminado());
			Utilidades.seleccionarRadio(rdbToma_igm, ficha_epidemiologia_n28.getToma_igm());
			dtbxFecha_toma_igm.setValue(ficha_epidemiologia_n28.getFecha_toma_igm());
			Utilidades.seleccionarRadio(rdbResultado_igm, ficha_epidemiologia_n28.getResultado_igm());
			Utilidades.seleccionarRadio(rdbTratamiento_terminado2, ficha_epidemiologia_n28.getTratamiento_terminado2());
			Utilidades.seleccionarRadio(rdbRecibio_vacuna, ficha_epidemiologia_n28.getRecibio_vacuna());
			dtbxMes_anio.setValue(ficha_epidemiologia_n28.getMes_anio());
			Utilidades.seleccionarRadio(rdbRecibio_vacuna2, ficha_epidemiologia_n28.getRecibio_vacuna2());
			dtbxMes_anio2.setValue(ficha_epidemiologia_n28.getMes_anio2());
			Utilidades.seleccionarRadio(rdbSintomatologia_confirmacion, ficha_epidemiologia_n28.getSintomatologia_confirmacion());
			Utilidades.seleccionarRadio(rdbTrimestre, ficha_epidemiologia_n28.getTrimestre());
			Utilidades.seleccionarRadio(rdbBiologica, ficha_epidemiologia_n28.getBiologica());
			Utilidades.seleccionarRadio(rdbTrimestre2, ficha_epidemiologia_n28.getTrimestre2());
			Utilidades.seleccionarRadio(rdbAmbiental, ficha_epidemiologia_n28.getAmbiental());
			Utilidades.seleccionarRadio(rdbTrimestre3, ficha_epidemiologia_n28.getTrimestre3());
			Utilidades.seleccionarRadio(rdbNunguna, ficha_epidemiologia_n28.getNunguna());
			Utilidades.seleccionarRadio(rdbTrimestre4, ficha_epidemiologia_n28.getTrimestre4());
			Utilidades.seleccionarRadio(rdbMedicamento, ficha_epidemiologia_n28.getMedicamento());
			Utilidades.seleccionarRadio(rdbTrimestre5, ficha_epidemiologia_n28.getTrimestre5());
			Utilidades.seleccionarRadio(rdbOtra, ficha_epidemiologia_n28.getOtra());
			Utilidades.seleccionarRadio(rdbTrimestre6, ficha_epidemiologia_n28.getTrimestre6());
			Utilidades.seleccionarRadio(rdbGemelo, ficha_epidemiologia_n28.getGemelo());
			Utilidades.seleccionarRadio(rdbNativivo, ficha_epidemiologia_n28.getNativivo());
			Utilidades.seleccionarRadio(rdbNatimorto, ficha_epidemiologia_n28.getNatimorto());
			tbxSemana_gestacion.setValue(ficha_epidemiologia_n28.getSemana_gestacion());
			ibxEdad_gestacional.setValue(ficha_epidemiologia_n28.getEdad_gestacional());
			ibxApgar.setValue(ficha_epidemiologia_n28.getApgar());
			dbxPeso.setValue(ficha_epidemiologia_n28.getPeso());
			Utilidades.seleccionarRadio(rdbToma_de_muestra, ficha_epidemiologia_n28.getToma_de_muestra());
			dtbxFecha_muestra.setValue(ficha_epidemiologia_n28.getFecha_muestra());
			Utilidades.seleccionarRadio(rdbAnencefalia, ficha_epidemiologia_n28.getAnencefalia());
			Utilidades.seleccionarRadio(rdbCriptorquidia, ficha_epidemiologia_n28.getCriptorquidia());
			Utilidades.seleccionarRadio(rdbMicrocefalila, ficha_epidemiologia_n28.getMicrocefalila());
			Utilidades.seleccionarRadio(rdbAno_imperforado, ficha_epidemiologia_n28.getAno_imperforado());
			Utilidades.seleccionarRadio(rdbDefectos_de_pared_abd, ficha_epidemiologia_n28.getDefectos_de_pared_abd());
			Utilidades.seleccionarRadio(rdbPolidactilia, ficha_epidemiologia_n28.getPolidactilia());
			Utilidades.seleccionarRadio(rdbAnomalias_auditivas, ficha_epidemiologia_n28.getAnomalias_auditivas());
			Utilidades.seleccionarRadio(rdbDisplasia_esqueletica, ficha_epidemiologia_n28.getDisplasia_esqueletica());
			Utilidades.seleccionarRadio(rdbPolimalformado, ficha_epidemiologia_n28.getPolimalformado());
			Utilidades.seleccionarRadio(rdbAnomalias_funcionales2, ficha_epidemiologia_n28.getAnomalias_funcionales2());
			Utilidades.seleccionarRadio(rdbEspina_bifida, ficha_epidemiologia_n28.getEspina_bifida());
			Utilidades.seleccionarRadio(rdbPurpura, ficha_epidemiologia_n28.getPurpura());
			Utilidades.seleccionarRadio(rdbAnomalias_oculares, ficha_epidemiologia_n28.getAnomalias_oculares());
			Utilidades.seleccionarRadio(rdbFisura_oral, ficha_epidemiologia_n28.getFisura_oral());
			Utilidades.seleccionarRadio(rdbReduccion_de_miembros, ficha_epidemiologia_n28.getReduccion_de_miembros());
			Utilidades.seleccionarRadio(rdbAnotia_o_microtia, ficha_epidemiologia_n28.getAnotia_o_microtia());
			Utilidades.seleccionarRadio(rdbGemelos_acoplados, ficha_epidemiologia_n28.getGemelos_acoplados());
			Utilidades.seleccionarRadio(rdbSindactilia, ficha_epidemiologia_n28.getSindactilia());
			Utilidades.seleccionarRadio(rdbAtresia_esofagia, ficha_epidemiologia_n28.getAtresia_esofagia());
			Utilidades.seleccionarRadio(rdbGenitales_ambiguos, ficha_epidemiologia_n28.getGenitales_ambiguos());
			Utilidades.seleccionarRadio(rdbSindrome_down, ficha_epidemiologia_n28.getSindrome_down());
			Utilidades.seleccionarRadio(rdbAtresia_intestinal, ficha_epidemiologia_n28.getAtresia_intestinal());
			Utilidades.seleccionarRadio(rdbHemangioma, ficha_epidemiologia_n28.getHemangioma());
			Utilidades.seleccionarRadio(rdbTalipes, ficha_epidemiologia_n28.getTalipes());
			Utilidades.seleccionarRadio(rdbCardiopatia, ficha_epidemiologia_n28.getCardiopatia());
			Utilidades.seleccionarRadio(rdbHepatoesplenomegalia, ficha_epidemiologia_n28.getHepatoesplenomegalia());
			Utilidades.seleccionarRadio(rdbBajo_peso_nacimiento, ficha_epidemiologia_n28.getBajo_peso_nacimiento());
			Utilidades.seleccionarRadio(rdbCatarata_congenita, ficha_epidemiologia_n28.getCatarata_congenita());
			Utilidades.seleccionarRadio(rdbHidrocefalia, ficha_epidemiologia_n28.getHidrocefalia());
			Utilidades.seleccionarRadio(rdbAnomalias_funcionales, ficha_epidemiologia_n28.getAnomalias_funcionales());
			Utilidades.seleccionarRadio(rdbCefalocele, ficha_epidemiologia_n28.getCefalocele());
			Utilidades.seleccionarRadio(rdbHipoacusia, ficha_epidemiologia_n28.getHipoacusia());
			Utilidades.seleccionarRadio(rdbHipospadias, ficha_epidemiologia_n28.getHipospadias());
			Utilidades.seleccionarRadio(rdbInfecciones_congenitas, ficha_epidemiologia_n28.getInfecciones_congenitas());
			Utilidades.seleccionarRadio(rdbOtras, ficha_epidemiologia_n28.getOtras());
			tbxCual_otra.setValue(ficha_epidemiologia_n28.getCual_otra());
			tbxBreve_descripcion.setValue(ficha_epidemiologia_n28.getBreve_descripcion());
			tbxDiligenciado_por.setValue(ficha_epidemiologia_n28.getDiligenciado_por());
			tbxTelefono_de_contacto.setValue(ficha_epidemiologia_n28.getTelefono_de_contacto());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n28 ficha_epidemiologia_n28 = (Ficha_epidemiologia_n28)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n28);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (HealthmanagerException e) {
			MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
		}catch(RuntimeException r){
			MensajesUtil.mensajeError(r, "", this);
		}
	}
	
	public void deshabilitar_conRadio(Radiogroup radiogroup,AbstractComponent[] abstractComponents) {
		try {
			String valor="S";
			FormularioUtil.deshabilitarComponentes_conRadiogroup(radiogroup, valor, abstractComponents);
			
		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}
	
	public void deshabilitar_conCheck(Checkbox checkbox,AbstractComponent[] abstractComponents) {
		try {
			
			FormularioUtil.deshabilitarComponentes_conCheckbox(checkbox, abstractComponents);

		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}
	
	public void obtenerAdmision(Admision admision){
		Paciente paciente = admision.getPaciente();
		tbxIdentificacion.setValue(admision.getNro_identificacion());
		tbxNombres_y_apellidos.setValue(paciente.getNombreCompleto());
		tbxTipo_identidad.setValue(paciente.getTipo_identificacion());
		
		tbxAseguradora.setValue(admision.getCodigo_administradora());
		//log.info("PACIENTE"+paciente);
		//log.info("ADMINISTRADORA"+admision.getCodigo_empresa()+" "+admision.getCodigo_sucursal()+" "+paciente.getCodigo_administradora());
		
		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(admision.getCodigo_empresa());
		administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
		administradora.setCodigo(admision.getCodigo_administradora());
		administradora = getServiceLocator().getAdministradoraService().consultar(administradora);
		//log.info("administradora"+administradora);
		
		tbxNombre_aseguradora.setValue(administradora.getNombre());
	}

	@Override
	public Ficha_epidemiologia_n28 consultarDatos(Map<String, Object> map,
			Ficha_epidemiologia ficha_epidemiologia) throws Exception {
//				Ficha_epidemiologia ficha = (Ficha_epidemiologia)ficha_epidemiologia;
				
				//log.info("-----------------");
				
				//log.info("map"+map);
				//log.info("ficha"+ficha);
				
				Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) map.get("impresion_diagnostica");
				Cie_epidemiologia cie_epidemiologia = (Cie_epidemiologia) map.get("cie_epidemiologia");
				Admision admision = (Admision) map.get("admision");
				
				Map<String,Object> parameters = new HashMap<String,Object>();
				parameters.put("codigo_empresa", admision.getCodigo_empresa());
				parameters.put("codigo_sucursal", admision.getCodigo_sucursal());
				parameters.put("identificacion", admision.getNro_identificacion());
				
				if(impresion_diagnostica != null){
				parameters.put("codigo_historia", impresion_diagnostica.getCodigo_historia());
				}else{
					return null;
				}
				
				if(cie_epidemiologia != null){
					parameters.put("codigo_diagnostico", cie_epidemiologia.getCodigo_cie());			
				}else{
					return null;
				}
				
				getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
				
				List<Ficha_epidemiologia_n28> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n28.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n28 ficha_n28 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n28;
				}else{

					return null;
				}
	}
}