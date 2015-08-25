/*
 * ficha_epidemiologia_n22Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n22;
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
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
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

public class Ficha_epidemiologia_n22Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n22>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n22Action.class);
	
	
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
	@View private Radiogroup rdbHaemophilus_infliuencae_b;
	@View private Textbox tbxDosis;
	@View private Datebox dtbxFecha_ultima_dosis;
	@View private Radiogroup rdbPresento_carne_vacuna;
	@View private Radiogroup rdbStreptococcus_pneumoniae;
	@View private Textbox tbxDosis2;
	@View private Datebox dtbxFecha_ultima_dosis2;
	@View private Radiogroup rdbPresento_carne_vacuna2;
	@View private Radiogroup rdbInfluenza_estacional;
	@View private Textbox tbxDosis3;
	@View private Datebox dtbxFecha_ultima_dosis3;
	@View private Radiogroup rdbPresento_carne_vacuna3;
	@View private Radiogroup rdbFuente_de_notificacion;
	@View private Radiogroup rdbViajo_durante_14dias;
	@View private Listbox lbxDpto_procedencia;
	@View private Listbox lbxMunicipio_procedencia;
	@View private Textbox tbxProcedencia_internacional;
	@View private Radiogroup rdbContacto_con_aves;
	@View private Radiogroup rdbContacto_estrecho;
	@View private Checkbox chbAsma;
	@View private Checkbox chbEpoc;
	@View private Checkbox chbDiabetes;
	@View private Checkbox chbVih_otras_inmu;
	@View private Checkbox chbEnfermedad_cardiaca;
	@View private Checkbox chbCancer;
	@View private Checkbox chbMalnutricion;
	@View private Checkbox chbEmbarazo;
	@View private Textbox tbxSemana_de_gestacion;
	@View private Checkbox chbObesidad;
	@View private Checkbox chbInsuficiencia_renal;
	@View private Checkbox chbToma_medicamentos;
	@View private Checkbox chbFumador;
	@View private Checkbox chbOtros;
	@View private Textbox tbxCual;
	@View private Radiogroup rdbTos;
	@View private Radiogroup rdbPostracion;
	@View private Radiogroup rdbFiebre;
	@View private Radiogroup rdbTiraje_toraxico;
	@View private Radiogroup rdbDolor_garganta;
	@View private Radiogroup rdbDolor_muscular;
	@View private Radiogroup rdbRinorrea;
	@View private Radiogroup rdbNo_tolera_via_oral;
	@View private Radiogroup rdbConjuntivitis;
	@View private Radiogroup rdbDiarrea;
	@View private Radiogroup rdbCefalea;
	@View private Radiogroup rdbDolor_abdominal;
	@View private Radiogroup rdbDifucultad_respirat;
	@View private Radiogroup rdbOtro;
	@View private Textbox tbxCual_otro;
	@View private Radiogroup rdbSe_tomo_radiografia;
	@View private Datebox dtbxFecha_de_toma;
	@View private Radiogroup rdbHallazgo_radiografia;
	@View private Radiogroup rdbUso_antibiotico;
	@View private Datebox dtbxFecha_inicio_antibioticos;
	@View private Radiogroup rdbUso_antiviral;
	@View private Datebox dtbxFecha_inicio_antiviral;
	@View private Radiogroup rdbHubo_complicaciones;
	@View private Radiogroup rdbCuales_complicaciones;
	@View private Textbox tbxCuales3;
	@View private Radiogroup rdbServicio_hopitalizo;
	@View private Listbox lbxDiganostico_inicial;
	@View private Listbox lbxDiagnostico_al_egreso;
	@View private Datebox dtbxFecha_de_toma1;
	@View private Datebox dtbxFecha_de_recepcion;
	@View private Checkbox chbMuestra;
	@View private Checkbox chbPrueba;
	@View private Textbox tbxAgente;
	@View private Textbox tbxResultado;
	@View private Datebox dtbxFecha_de_recepcion1;
	@View private Textbox tbxValor_registrado;
	@View private Datebox dtbxFecha_de_toma2;
	@View private Datebox dtbxFecha_de_recepcion2;
	@View private Checkbox chbMuestra2;
	@View private Checkbox chbPrueba2;
	@View private Textbox tbxAgente2;
	@View private Textbox tbxResultado2;
	@View private Datebox dtbxFecha_de_recepcion22;
	@View private Textbox tbxValor_registrado2;
	@View private Textbox tbxDiligenciado_por;
	@View private Textbox tbxTelefono_de_contacto;
	@View private Toolbarbutton btGuardar;
	
	@View private Row rowCual_otro;
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n22 ficha = new Ficha_epidemiologia_n22();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n22) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 22-------> "+ficha);
			
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
		Utilidades.listarDepartamentos(lbxDpto_procedencia,true,getServiceLocator());
		Utilidades.listarMunicipios(lbxMunicipio_procedencia,lbxDpto_procedencia,getServiceLocator());
		Utilidades.listarElementoListbox(lbxDiganostico_inicial,true,getServiceLocator());
		Utilidades.listarElementoListbox(lbxDiagnostico_al_egreso,true,getServiceLocator());
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
		lbxDpto_procedencia.setStyle("background-color:white;");
		lbxMunicipio_procedencia.setStyle("background-color:white;");
		
		boolean valida = true;
		
		if(tbxIdentificacion.getText().trim().equals("")){
			tbxIdentificacion.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (lbxDpto_procedencia.getSelectedItem().getValue().toString().equals("")) {
			lbxDpto_procedencia.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (lbxMunicipio_procedencia.getSelectedItem() != null) {
			if (lbxMunicipio_procedencia.getSelectedItem().getValue().toString()
					.equals("")) {
				lbxMunicipio_procedencia.setStyle("background-color:#F6BBBE");
				valida = false;
			}
		}

		if (lbxMunicipio_procedencia.getSelectedItem() == null) {
			lbxMunicipio_procedencia.setStyle("background-color:#F6BBBE");
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
			
			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
			
			List<Ficha_epidemiologia_n22> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
					Ficha_epidemiologia_n22.class, parameters);
			rowsResultado.getChildren().clear();
			
			for (Ficha_epidemiologia_n22 ficha_epidemiologia_n22 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n22, this));
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
		
		final Ficha_epidemiologia_n22 ficha_epidemiologia_n22 = (Ficha_epidemiologia_n22)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n22.getCodigo_ficha()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n22.getFecha_inicial()+""));
		fila.appendChild(new Label(ficha_epidemiologia_n22.getIdentificacion()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n22);
			}
		});
		hbox.appendChild(img);
		
		hbox.appendChild(space);
		hbox.appendChild(img);
		
		fila.appendChild(hbox);
		
		return fila;
	}
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n22 obtenerFichaEpidemiologia(){
				
				Ficha_epidemiologia_n22 ficha_epidemiologia_n22 = new Ficha_epidemiologia_n22();
				ficha_epidemiologia_n22.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n22.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n22.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n22.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n22.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n22.setIdentificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n22.setHaemophilus_infliuencae_b(rdbHaemophilus_infliuencae_b.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDosis(tbxDosis.getValue());
				ficha_epidemiologia_n22.setFecha_ultima_dosis(new Timestamp(dtbxFecha_ultima_dosis.getValue().getTime()));
				ficha_epidemiologia_n22.setPresento_carne_vacuna(rdbPresento_carne_vacuna.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setStreptococcus_pneumoniae(rdbStreptococcus_pneumoniae.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDosis2(tbxDosis2.getValue());
				ficha_epidemiologia_n22.setFecha_ultima_dosis2(new Timestamp(dtbxFecha_ultima_dosis2.getValue().getTime()));
				ficha_epidemiologia_n22.setPresento_carne_vacuna2(rdbPresento_carne_vacuna2.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setInfluenza_estacional(rdbInfluenza_estacional.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDosis3(tbxDosis3.getValue());
				ficha_epidemiologia_n22.setFecha_ultima_dosis3(new Timestamp(dtbxFecha_ultima_dosis3.getValue().getTime()));
				ficha_epidemiologia_n22.setPresento_carne_vacuna3(rdbPresento_carne_vacuna3.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setFuente_de_notificacion(rdbFuente_de_notificacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setViajo_durante_14dias(rdbViajo_durante_14dias.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDpto_procedencia(lbxDpto_procedencia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setMunicipio_procedencia(lbxMunicipio_procedencia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setProcedencia_internacional(tbxProcedencia_internacional.getValue());
				ficha_epidemiologia_n22.setContacto_con_aves(rdbContacto_con_aves.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setContacto_estrecho(rdbContacto_estrecho.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setAsma(chbAsma.isChecked());
				ficha_epidemiologia_n22.setEpoc(chbEpoc.isChecked());
				ficha_epidemiologia_n22.setDiabetes(chbDiabetes.isChecked());
				ficha_epidemiologia_n22.setVih_otras_inmu(chbVih_otras_inmu.isChecked());
				ficha_epidemiologia_n22.setEnfermedad_cardiaca(chbEnfermedad_cardiaca.isChecked());
				ficha_epidemiologia_n22.setCancer(chbCancer.isChecked());
				ficha_epidemiologia_n22.setMalnutricion(chbMalnutricion.isChecked());
				ficha_epidemiologia_n22.setEmbarazo(chbEmbarazo.isChecked());
				ficha_epidemiologia_n22.setSemana_de_gestacion(tbxSemana_de_gestacion.getValue());
				ficha_epidemiologia_n22.setObesidad(chbObesidad.isChecked());
				ficha_epidemiologia_n22.setInsuficiencia_renal(chbInsuficiencia_renal.isChecked());
				ficha_epidemiologia_n22.setToma_medicamentos(chbToma_medicamentos.isChecked());
				ficha_epidemiologia_n22.setFumador(chbFumador.isChecked());
				ficha_epidemiologia_n22.setOtros(chbOtros.isChecked());
				ficha_epidemiologia_n22.setCual(tbxCual.getValue());
				ficha_epidemiologia_n22.setTos(rdbTos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setPostracion(rdbPostracion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setFiebre(rdbFiebre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setTiraje_toraxico(rdbTiraje_toraxico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDolor_garganta(rdbDolor_garganta.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDolor_muscular(rdbDolor_muscular.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setRinorrea(rdbRinorrea.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setNo_tolera_via_oral(rdbNo_tolera_via_oral.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setConjuntivitis(rdbConjuntivitis.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDiarrea(rdbDiarrea.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setCefalea(rdbCefalea.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDolor_abdominal(rdbDolor_abdominal.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDifucultad_respirat(rdbDifucultad_respirat.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setOtro(rdbOtro.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setCual_otro(tbxCual_otro.getValue());
				ficha_epidemiologia_n22.setSe_tomo_radiografia(rdbSe_tomo_radiografia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setFecha_de_toma(new Timestamp(dtbxFecha_de_toma.getValue().getTime()));
				ficha_epidemiologia_n22.setHallazgo_radiografia(rdbHallazgo_radiografia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setUso_antibiotico(rdbUso_antibiotico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setFecha_inicio_antibioticos(new Timestamp(dtbxFecha_inicio_antibioticos.getValue().getTime()));
				ficha_epidemiologia_n22.setUso_antiviral(rdbUso_antiviral.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setFecha_inicio_antiviral(new Timestamp(dtbxFecha_inicio_antiviral.getValue().getTime()));
				ficha_epidemiologia_n22.setHubo_complicaciones(rdbHubo_complicaciones.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setCuales_complicaciones(rdbCuales_complicaciones.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setCuales3(tbxCuales3.getValue());
				ficha_epidemiologia_n22.setServicio_hopitalizo(rdbServicio_hopitalizo.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDiganostico_inicial(lbxDiganostico_inicial.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setDiagnostico_al_egreso(lbxDiagnostico_al_egreso.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n22.setFecha_de_toma1(new Timestamp(dtbxFecha_de_toma1.getValue().getTime()));
				ficha_epidemiologia_n22.setFecha_de_recepcion(new Timestamp(dtbxFecha_de_recepcion.getValue().getTime()));
				ficha_epidemiologia_n22.setMuestra(chbMuestra.isChecked());
				ficha_epidemiologia_n22.setPrueba(chbPrueba.isChecked());
				ficha_epidemiologia_n22.setAgente(tbxAgente.getValue());
				ficha_epidemiologia_n22.setResultado(tbxResultado.getValue());
				ficha_epidemiologia_n22.setFecha_de_recepcion1(new Timestamp(dtbxFecha_de_recepcion1.getValue().getTime()));
				ficha_epidemiologia_n22.setValor_registrado(tbxValor_registrado.getValue());
				ficha_epidemiologia_n22.setFecha_de_toma2(new Timestamp(dtbxFecha_de_toma2.getValue().getTime()));
				ficha_epidemiologia_n22.setFecha_de_recepcion2(new Timestamp(dtbxFecha_de_recepcion2.getValue().getTime()));
				ficha_epidemiologia_n22.setMuestra2(chbMuestra2.isChecked());
				ficha_epidemiologia_n22.setPrueba2(chbPrueba2.isChecked());
				ficha_epidemiologia_n22.setAgente2(tbxAgente2.getValue());
				ficha_epidemiologia_n22.setResultado2(tbxResultado2.getValue());
				ficha_epidemiologia_n22.setFecha_de_recepcion22(new Timestamp(dtbxFecha_de_recepcion22.getValue().getTime()));
				ficha_epidemiologia_n22.setValor_registrado2(tbxValor_registrado2.getValue());
				ficha_epidemiologia_n22.setDiligenciado_por(tbxDiligenciado_por.getValue());
				ficha_epidemiologia_n22.setTelefono_de_contacto(tbxTelefono_de_contacto.getValue());
				ficha_epidemiologia_n22.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n22.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n22.setCreacion_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n22.setDelete_date(null);
				ficha_epidemiologia_n22.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n22.setDelete_user(null);
				
				return ficha_epidemiologia_n22;
		
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n22 obj)throws Exception{
		Ficha_epidemiologia_n22 ficha_epidemiologia_n22 = (Ficha_epidemiologia_n22)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n22.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n22.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n22.getFecha_inicial());
			tbxIdentificacion.setValue(ficha_epidemiologia_n22.getIdentificacion());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbHaemophilus_infliuencae_b, ficha_epidemiologia_n22.getHaemophilus_infliuencae_b());
			tbxDosis.setValue(ficha_epidemiologia_n22.getDosis());
			dtbxFecha_ultima_dosis.setValue(ficha_epidemiologia_n22.getFecha_ultima_dosis());
			Utilidades.seleccionarRadio(rdbPresento_carne_vacuna, ficha_epidemiologia_n22.getPresento_carne_vacuna());
			Utilidades.seleccionarRadio(rdbStreptococcus_pneumoniae, ficha_epidemiologia_n22.getStreptococcus_pneumoniae());
			tbxDosis2.setValue(ficha_epidemiologia_n22.getDosis2());
			dtbxFecha_ultima_dosis2.setValue(ficha_epidemiologia_n22.getFecha_ultima_dosis2());
			Utilidades.seleccionarRadio(rdbPresento_carne_vacuna2, ficha_epidemiologia_n22.getPresento_carne_vacuna2());
			Utilidades.seleccionarRadio(rdbInfluenza_estacional, ficha_epidemiologia_n22.getInfluenza_estacional());
			tbxDosis3.setValue(ficha_epidemiologia_n22.getDosis3());
			dtbxFecha_ultima_dosis3.setValue(ficha_epidemiologia_n22.getFecha_ultima_dosis3());
			Utilidades.seleccionarRadio(rdbPresento_carne_vacuna3, ficha_epidemiologia_n22.getPresento_carne_vacuna3());
			Utilidades.seleccionarRadio(rdbFuente_de_notificacion, ficha_epidemiologia_n22.getFuente_de_notificacion());
			Utilidades.seleccionarRadio(rdbViajo_durante_14dias, ficha_epidemiologia_n22.getViajo_durante_14dias());
			for(int i=0;i<lbxDpto_procedencia.getItemCount();i++){
				Listitem listitem = lbxDpto_procedencia.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(ficha_epidemiologia_n22.getDpto_procedencia())){
					listitem.setSelected(true);
					i = lbxDpto_procedencia.getItemCount();
				}
			}
			for(int i=0;i<lbxMunicipio_procedencia.getItemCount();i++){
				Listitem listitem = lbxMunicipio_procedencia.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(ficha_epidemiologia_n22.getMunicipio_procedencia())){
					listitem.setSelected(true);
					i = lbxMunicipio_procedencia.getItemCount();
				}
			}
			tbxProcedencia_internacional.setValue(ficha_epidemiologia_n22.getProcedencia_internacional());			
			Utilidades.seleccionarRadio(rdbContacto_con_aves, ficha_epidemiologia_n22.getContacto_con_aves());
			Utilidades.seleccionarRadio(rdbContacto_estrecho, ficha_epidemiologia_n22.getContacto_estrecho());
			chbAsma.setChecked(ficha_epidemiologia_n22.getAsma());
			chbEpoc.setChecked(ficha_epidemiologia_n22.getEpoc());
			chbDiabetes.setChecked(ficha_epidemiologia_n22.getDiabetes());
			chbVih_otras_inmu.setChecked(ficha_epidemiologia_n22.getVih_otras_inmu());
			chbEnfermedad_cardiaca.setChecked(ficha_epidemiologia_n22.getEnfermedad_cardiaca());
			chbCancer.setChecked(ficha_epidemiologia_n22.getCancer());
			chbMalnutricion.setChecked(ficha_epidemiologia_n22.getMalnutricion());
			chbEmbarazo.setChecked(ficha_epidemiologia_n22.getEmbarazo());
			tbxSemana_de_gestacion.setValue(ficha_epidemiologia_n22.getSemana_de_gestacion());
			chbObesidad.setChecked(ficha_epidemiologia_n22.getObesidad());
			chbInsuficiencia_renal.setChecked(ficha_epidemiologia_n22.getInsuficiencia_renal());
			chbToma_medicamentos.setChecked(ficha_epidemiologia_n22.getToma_medicamentos());
			chbFumador.setChecked(ficha_epidemiologia_n22.getFumador());
			chbOtros.setChecked(ficha_epidemiologia_n22.getOtros());
			tbxCual.setValue(ficha_epidemiologia_n22.getCual());
			Utilidades.seleccionarRadio(rdbTos, ficha_epidemiologia_n22.getTos());
			Utilidades.seleccionarRadio(rdbPostracion, ficha_epidemiologia_n22.getPostracion());
			Utilidades.seleccionarRadio(rdbFiebre, ficha_epidemiologia_n22.getFiebre());
			Utilidades.seleccionarRadio(rdbTiraje_toraxico, ficha_epidemiologia_n22.getTiraje_toraxico());
			Utilidades.seleccionarRadio(rdbDolor_garganta, ficha_epidemiologia_n22.getDolor_garganta());
			Utilidades.seleccionarRadio(rdbDolor_muscular, ficha_epidemiologia_n22.getDolor_muscular());
			Utilidades.seleccionarRadio(rdbRinorrea, ficha_epidemiologia_n22.getRinorrea());
			Utilidades.seleccionarRadio(rdbNo_tolera_via_oral, ficha_epidemiologia_n22.getNo_tolera_via_oral());
			Utilidades.seleccionarRadio(rdbConjuntivitis, ficha_epidemiologia_n22.getConjuntivitis());
			Utilidades.seleccionarRadio(rdbDiarrea, ficha_epidemiologia_n22.getDiarrea());
			Utilidades.seleccionarRadio(rdbCefalea, ficha_epidemiologia_n22.getCefalea());
			Utilidades.seleccionarRadio(rdbDolor_abdominal, ficha_epidemiologia_n22.getDolor_abdominal());
			Utilidades.seleccionarRadio(rdbDifucultad_respirat, ficha_epidemiologia_n22.getDifucultad_respirat());
			Utilidades.seleccionarRadio(rdbOtro, ficha_epidemiologia_n22.getOtro());
			tbxCual_otro.setValue(ficha_epidemiologia_n22.getCual_otro());
			Utilidades.seleccionarRadio(rdbSe_tomo_radiografia, ficha_epidemiologia_n22.getSe_tomo_radiografia());
			dtbxFecha_de_toma.setValue(ficha_epidemiologia_n22.getFecha_de_toma());
			Utilidades.seleccionarRadio(rdbHallazgo_radiografia, ficha_epidemiologia_n22.getHallazgo_radiografia());
			Utilidades.seleccionarRadio(rdbUso_antibiotico, ficha_epidemiologia_n22.getUso_antibiotico());
			dtbxFecha_inicio_antibioticos.setValue(ficha_epidemiologia_n22.getFecha_inicio_antibioticos());
			Utilidades.seleccionarRadio(rdbUso_antiviral, ficha_epidemiologia_n22.getUso_antiviral());
			dtbxFecha_inicio_antiviral.setValue(ficha_epidemiologia_n22.getFecha_inicio_antiviral());
			Utilidades.seleccionarRadio(rdbHubo_complicaciones, ficha_epidemiologia_n22.getHubo_complicaciones());
			Utilidades.seleccionarRadio(rdbCuales_complicaciones, ficha_epidemiologia_n22.getCuales_complicaciones());
			tbxCuales3.setValue(ficha_epidemiologia_n22.getCuales3());
			Utilidades.seleccionarRadio(rdbServicio_hopitalizo, ficha_epidemiologia_n22.getServicio_hopitalizo());
			for(int i=0;i<lbxDiganostico_inicial.getItemCount();i++){
				Listitem listitem = lbxDiganostico_inicial.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(ficha_epidemiologia_n22.getDiganostico_inicial())){
					listitem.setSelected(true);
					i = lbxDiganostico_inicial.getItemCount();
				}
			}
			for(int i=0;i<lbxDiagnostico_al_egreso.getItemCount();i++){
				Listitem listitem = lbxDiagnostico_al_egreso.getItemAtIndex(i);
				if(listitem.getValue().toString().equals(ficha_epidemiologia_n22.getDiagnostico_al_egreso())){
					listitem.setSelected(true);
					i = lbxDiagnostico_al_egreso.getItemCount();
				}
			}
			dtbxFecha_de_toma1.setValue(ficha_epidemiologia_n22.getFecha_de_toma1());
			dtbxFecha_de_recepcion.setValue(ficha_epidemiologia_n22.getFecha_de_recepcion());
			chbMuestra.setChecked(ficha_epidemiologia_n22.getMuestra());
			chbPrueba.setChecked(ficha_epidemiologia_n22.getPrueba());
			tbxAgente.setValue(ficha_epidemiologia_n22.getAgente());
			tbxResultado.setValue(ficha_epidemiologia_n22.getResultado());
			dtbxFecha_de_recepcion1.setValue(ficha_epidemiologia_n22.getFecha_de_recepcion1());
			tbxValor_registrado.setValue(ficha_epidemiologia_n22.getValor_registrado());
			dtbxFecha_de_toma2.setValue(ficha_epidemiologia_n22.getFecha_de_toma2());
			dtbxFecha_de_recepcion2.setValue(ficha_epidemiologia_n22.getFecha_de_recepcion2());
			chbMuestra2.setChecked(ficha_epidemiologia_n22.getMuestra2());
			chbPrueba2.setChecked(ficha_epidemiologia_n22.getPrueba2());
			tbxAgente2.setValue(ficha_epidemiologia_n22.getAgente2());
			tbxResultado2.setValue(ficha_epidemiologia_n22.getResultado2());
			dtbxFecha_de_recepcion22.setValue(ficha_epidemiologia_n22.getFecha_de_recepcion22());
			tbxValor_registrado2.setValue(ficha_epidemiologia_n22.getValor_registrado2());
			tbxDiligenciado_por.setValue(ficha_epidemiologia_n22.getDiligenciado_por());
			tbxTelefono_de_contacto.setValue(ficha_epidemiologia_n22.getTelefono_de_contacto());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n22 ficha_epidemiologia_n22 = (Ficha_epidemiologia_n22)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n22);
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
	

	
	public void visible_conRadio(String value){
		if(value.equals("N")){
			rowCual_otro.setVisible(false);
			tbxCual_otro.setReadonly(true);
			tbxCual_otro.setText("");
		}
		if(value.equals("S")){
			rowCual_otro.setVisible(true);
			tbxCual_otro.setReadonly(false);
			tbxCual_otro.setText("");
			tbxCual_otro.focus();
		}
	}
	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		Utilidades.listarMunicipios(listboxMun, listboxDpto,
				getServiceLocator());
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
	public Ficha_epidemiologia_n22 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n22> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n22.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n22 ficha_n22 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n22;
				}else{

					return null;
				}
	}
	
}