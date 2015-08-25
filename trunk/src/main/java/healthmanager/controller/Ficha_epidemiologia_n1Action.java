/*
 * ficha_epidemiologia_n1Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n1;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IFichas_epidemiologicas;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n1Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n1> {


	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Textbox tbxCodigo_ficha;
	@View
	private Textbox tbxCodigo_diagnostico;
	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad;
	@View
	private Textbox tbxIdentificacion;
	@View 
	private Textbox tbxAseguradora;
	@View 
	private Textbox tbxNombre_aseguradora;
	@View
	private Datebox dtbxFecha_accidente;
	@View
	private Textbox tbxDireccion_accidente;
	@View
	private Radiogroup rdbActividad_reliz_acc;
	@View
	private Textbox tbxCual_otro1;
	@View
	private Radiogroup rdbAtencion_inicial;
	@View
	private Textbox tbxCual_otro2;
	@View
	private Radiogroup rdbPersona_sometida;
	@View
	private Textbox tbxCual_otro3;
	@View
	private Radiogroup rdbLocalizacion_mordedura;
	@View
	private Radiogroup rdbHay_evidencia_huellas;
	@View
	private Radiogroup rdbPersona_vio_serpiente;
	@View
	private Radiogroup rdbSe_capturo_serpiente;
	@View
	private Radiogroup rdbAgente_agresor_ident;
	@View
	private Textbox tbxCual_otro4;
	@View
	private Radiogroup rdbAgente_agresor_nomb;
	@View
	private Textbox tbxCual_otro5;
	@View
	private Checkbox chbEdema;
	@View
	private Checkbox chbDolor;
	@View
	private Checkbox chbEritmeta;
	@View
	private Checkbox chbFlictenas;
	@View
	private Checkbox chbParestesias_hipo;
	@View
	private Checkbox chbEquimosis;
	@View
	private Checkbox chbHematomas;
	@View
	private Checkbox chbOtro1;
	@View
	private Textbox tbxCual_otro6;
	@View
	private Checkbox chbNausea;
	@View
	private Checkbox chbVomito;
	@View
	private Checkbox chbSialorrea;
	@View
	private Checkbox chbDiarrea;
	@View
	private Checkbox chbBradicardia;
	@View
	private Checkbox chbHipotension;
	@View
	private Checkbox chbDolor_abdom;
	@View
	private Checkbox chbFascies_neurot;
	@View
	private Checkbox chbAlteraciones_vision;
	@View
	private Checkbox chbAlteraciones_sensorial;
	@View
	private Checkbox chbDebilidad_muscular;
	@View
	private Checkbox chbOliguria;
	@View
	private Checkbox chbCianosis;
	@View
	private Checkbox chbHemotaquexia;
	@View
	private Checkbox chbEpistaxis;
	@View
	private Checkbox chbGingivorragia;
	@View
	private Checkbox chbHematemesis;
	@View
	private Checkbox chbHematuria;
	@View
	private Checkbox chbOtro2;
	@View
	private Checkbox chbVertigo;
	@View
	private Checkbox chbPtosis_palpebral;
	@View
	private Checkbox chbDificultad_hablar;
	@View
	private Checkbox chbDisfagia;
	@View
	private Textbox tbxCual_otro7;
	@View
	private Checkbox chbCelulitis;
	@View
	private Checkbox chbAbsceso;
	@View
	private Checkbox chbNecrosis;
	@View
	private Checkbox chbMionecrosis;
	@View
	private Checkbox chbFasceitis;
	@View
	private Checkbox chbAlteraciones_circul_perf;
	@View
	private Checkbox chbOtro3;
	@View
	private Textbox tbxCual_otro8;
	@View
	private Checkbox chbAnemia_aguda_severa;
	@View
	private Checkbox chbShock_hipovolemico;
	@View
	private Checkbox chbShock_septico;
	@View
	private Checkbox chbIra;
	@View
	private Checkbox chbCid;
	@View
	private Checkbox chbHsa;
	@View
	private Checkbox chbEdema_cerebral;
	@View
	private Checkbox chbFalla_ventilatoria;
	@View
	private Checkbox chbComa;
	@View
	private Checkbox chbOtro4;
	@View
	private Textbox tbxCual_otro9;
	@View
	private Radiogroup rdbGravedad_accidentes;
	@View
	private Radiogroup rdbEmpleo_suero;
	@View
	private Intbox ibxDias;
	@View
	private Intbox ibxHoras;
	@View
	private Radiogroup rdbTipo_suero_antiofidico;
	@View
	private Radiogroup rdbReacciones_apli_suero;
	@View
	private Intbox ibxDosis_de_suero;
	@View
	private Intbox ibxHoras2;
	// @View private Doublebox dbxMinutos;
	@View
	private Radiogroup rdbRemitido_otra_instit;
	@View
	private Radiogroup rdbTratamiento_quirurgico;
	@View
	private Checkbox chbDrenaje_absceso;
	@View
	private Checkbox chbLimpieza_quirurgica;
	@View
	private Checkbox chbDesbridamiento;
	@View
	private Checkbox chbFasciotomia;
	@View
	private Checkbox chbInjerto_de_piel;
	@View
	private Checkbox chbAmputacion;
	@View
	private Toolbarbutton btGuardar;
	private final String[] idsExcluyentes = {};
	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;

	@View private Toolbarbutton btImprimir;
	@View private North north_ficha;
	
	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);
		
		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n1 ficha = new Ficha_epidemiologia_n1();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n1) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 1-------> "+ficha);
			
			mostrarDatos(ficha);

			north_ficha.setVisible(true);
			btImprimir.setVisible(true);
			
		}else {

			north_ficha.setVisible(false);
			btImprimir.setVisible(false);
			
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
			//log.info("admision"+admision);
			//log.info("ficha_seleccionada"+ficha_seleccionada);
			
		}
	}

	public void listarCombos() {
		listarParameter();
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

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
			btGuardar.setVisible(false);
		} else {
			btGuardar.setVisible(true);
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
	public boolean validarFichaEpidemiologia() {

		dtbxFecha_inicial.setStyle("background-color:white");
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:white");
		tbxIdentificacion.setStyle("background-color:white");

		boolean valida = true;

		if (dtbxFecha_inicial.getText().trim().equals("")) {
			dtbxFecha_inicial.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		// if(tbxNombres_y_apellidos.getText().trim().equals("")){
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		// valida = false;
		// }
		if (tbxIdentificacion.getText().trim().equals("")) {
			tbxIdentificacion.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
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

			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}

			getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
					"limit 25 offset 0");

			List<Ficha_epidemiologia_n1> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n1.class, parameters);

			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n1 ficha_epidemiologia_n1 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n1,
						this));
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

		final Ficha_epidemiologia_n1 ficha_epidemiologia_n1 = (Ficha_epidemiologia_n1) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n1.getCodigo_ficha()
				+ ""));
		fila.appendChild(new Label(ficha_epidemiologia_n1.getIdentificacion()
				+ ""));
		fila.appendChild(new Label(ficha_epidemiologia_n1.getFecha_inicial()
				+ ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n1);
			}
		});
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public Ficha_epidemiologia_n1 obtenerFichaEpidemiologia() {

		Ficha_epidemiologia_n1 ficha_epidemiologia_n1 = new Ficha_epidemiologia_n1();
		ficha_epidemiologia_n1.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n1
				.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		ficha_epidemiologia_n1.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n1.setCodigo_diagnostico("Z000");
		ficha_epidemiologia_n1.setFecha_inicial(new Timestamp(dtbxFecha_inicial
				.getValue().getTime()));
		// ficha_epidemiologia_n1.setNombres_y_apellidos(tbxNombres_y_apellidos.getValue());
		// ficha_epidemiologia_n1.setTipo_identidad(tbxTipo_identidad.getValue());
		ficha_epidemiologia_n1.setIdentificacion(admision != null ? admision
				.getNro_identificacion() : null);
		ficha_epidemiologia_n1.setFecha_accidente(new Timestamp(
				dtbxFecha_accidente.getValue().getTime()));
		ficha_epidemiologia_n1.setDireccion_accidente(tbxDireccion_accidente
				.getValue());
		ficha_epidemiologia_n1.setActividad_reliz_acc(rdbActividad_reliz_acc
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setCual_otro1(tbxCual_otro1.getValue());
		ficha_epidemiologia_n1.setAtencion_inicial(rdbAtencion_inicial
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setCual_otro2(tbxCual_otro2.getValue());
		ficha_epidemiologia_n1.setPersona_sometida(rdbPersona_sometida
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setCual_otro3(tbxCual_otro3.getValue());
		ficha_epidemiologia_n1
				.setLocalizacion_mordedura(rdbLocalizacion_mordedura
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1
				.setHay_evidencia_huellas(rdbHay_evidencia_huellas
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1
				.setPersona_vio_serpiente(rdbPersona_vio_serpiente
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setSe_capturo_serpiente(rdbSe_capturo_serpiente
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setAgente_agresor_ident(rdbAgente_agresor_ident
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setCual_otro4(tbxCual_otro4.getValue());
		ficha_epidemiologia_n1.setAgente_agresor_nomb(rdbAgente_agresor_nomb
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setCual_otro5(tbxCual_otro5.getValue());
		ficha_epidemiologia_n1.setEdema(chbEdema.isChecked());
		ficha_epidemiologia_n1.setDolor(chbDolor.isChecked());
		ficha_epidemiologia_n1.setEritmeta(chbEritmeta.isChecked());
		ficha_epidemiologia_n1.setFlictenas(chbFlictenas.isChecked());
		ficha_epidemiologia_n1.setParestesias_hipo(chbParestesias_hipo
				.isChecked());
		ficha_epidemiologia_n1.setEquimosis(chbEquimosis.isChecked());
		ficha_epidemiologia_n1.setHematomas(chbHematomas.isChecked());
		ficha_epidemiologia_n1.setOtro1(chbOtro1.isChecked());
		ficha_epidemiologia_n1.setCual_otro6(tbxCual_otro6.getValue());
		ficha_epidemiologia_n1.setNausea(chbNausea.isChecked());
		ficha_epidemiologia_n1.setVomito(chbVomito.isChecked());
		ficha_epidemiologia_n1.setSialorrea(chbSialorrea.isChecked());
		ficha_epidemiologia_n1.setDiarrea(chbDiarrea.isChecked());
		ficha_epidemiologia_n1.setBradicardia(chbBradicardia.isChecked());
		ficha_epidemiologia_n1.setHipotension(chbHipotension.isChecked());
		ficha_epidemiologia_n1.setDolor_abdom(chbDolor_abdom.isChecked());
		ficha_epidemiologia_n1.setFascies_neurot(chbFascies_neurot.isChecked());
		ficha_epidemiologia_n1.setAlteraciones_vision(chbAlteraciones_vision
				.isChecked());
		ficha_epidemiologia_n1
				.setAlteraciones_sensorial(chbAlteraciones_sensorial
						.isChecked());
		ficha_epidemiologia_n1.setDebilidad_muscular(chbDebilidad_muscular
				.isChecked());
		ficha_epidemiologia_n1.setOliguria(chbOliguria.isChecked());
		ficha_epidemiologia_n1.setCianosis(chbCianosis.isChecked());
		ficha_epidemiologia_n1.setHemotaquexia(chbHemotaquexia.isChecked());
		ficha_epidemiologia_n1.setEpistaxis(chbEpistaxis.isChecked());
		ficha_epidemiologia_n1.setGingivorragia(chbGingivorragia.isChecked());
		ficha_epidemiologia_n1.setHematemesis(chbHematemesis.isChecked());
		ficha_epidemiologia_n1.setHematuria(chbHematuria.isChecked());
		ficha_epidemiologia_n1.setOtro2(chbOtro2.isChecked());
		ficha_epidemiologia_n1.setVertigo(chbVertigo.isChecked());
		ficha_epidemiologia_n1.setPtosis_palpebral(chbPtosis_palpebral
				.isChecked());
		ficha_epidemiologia_n1.setDificultad_hablar(chbDificultad_hablar
				.isChecked());
		ficha_epidemiologia_n1.setDisfagia(chbDisfagia.isChecked());
		ficha_epidemiologia_n1.setCual_otro7(tbxCual_otro7.getValue());
		ficha_epidemiologia_n1.setCelulitis(chbCelulitis.isChecked());
		ficha_epidemiologia_n1.setAbsceso(chbAbsceso.isChecked());
		ficha_epidemiologia_n1.setNecrosis(chbNecrosis.isChecked());
		ficha_epidemiologia_n1.setMionecrosis(chbMionecrosis.isChecked());
		ficha_epidemiologia_n1.setFasceitis(chbFasceitis.isChecked());
		ficha_epidemiologia_n1
				.setAlteraciones_circul_perf(chbAlteraciones_circul_perf
						.isChecked());
		ficha_epidemiologia_n1.setOtro3(chbOtro3.isChecked());
		ficha_epidemiologia_n1.setCual_otro8(tbxCual_otro8.getValue());
		ficha_epidemiologia_n1.setAnemia_aguda_severa(chbAnemia_aguda_severa
				.isChecked());
		ficha_epidemiologia_n1.setShock_hipovolemico(chbShock_hipovolemico
				.isChecked());
		ficha_epidemiologia_n1.setShock_septico(chbShock_septico.isChecked());
		ficha_epidemiologia_n1.setIra(chbIra.isChecked());
		ficha_epidemiologia_n1.setCid(chbCid.isChecked());
		ficha_epidemiologia_n1.setHsa(chbHsa.isChecked());
		ficha_epidemiologia_n1.setEdema_cerebral(chbEdema_cerebral.isChecked());
		ficha_epidemiologia_n1.setFalla_ventilatoria(chbFalla_ventilatoria
				.isChecked());
		ficha_epidemiologia_n1.setComa(chbComa.isChecked());
		ficha_epidemiologia_n1.setOtro4(chbOtro4.isChecked());
		ficha_epidemiologia_n1.setCual_otro9(tbxCual_otro9.getValue());
		ficha_epidemiologia_n1.setGravedad_accidentes(rdbGravedad_accidentes
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setEmpleo_suero(rdbEmpleo_suero
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setDias((ibxDias.getValue() != null ? ibxDias
				.getValue() : 0));
		ficha_epidemiologia_n1.setHoras(ibxHoras.getValue() != null ? ibxHoras.getValue() : 0);
		
		if (rdbTipo_suero_antiofidico.getSelectedItem().getValue() != null) {
			ficha_epidemiologia_n1
					.setTipo_suero_antiofidico(rdbTipo_suero_antiofidico
							.getSelectedItem().getValue().toString());
		}
		ficha_epidemiologia_n1
				.setTipo_suero_antiofidico(rdbTipo_suero_antiofidico
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1
				.setReacciones_apli_suero(rdbReacciones_apli_suero
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1
				.setDosis_de_suero((ibxDosis_de_suero.getValue() != null ? ibxDosis_de_suero
						.getValue() : 0));
		ficha_epidemiologia_n1.setHoras2(ibxHoras2.getValue() != null ? ibxHoras2.getValue() : 0);
		

		// ficha_epidemiologia_n1.setMinutos((dbxMinutos.getValue()!=null?dbxMinutos.getValue():0.00));
		ficha_epidemiologia_n1.setRemitido_otra_instit(rdbRemitido_otra_instit
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1
				.setTratamiento_quirurgico(rdbTratamiento_quirurgico
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n1.setDrenaje_absceso(chbDrenaje_absceso
				.isChecked());
		ficha_epidemiologia_n1.setLimpieza_quirurgica(chbLimpieza_quirurgica
				.isChecked());
		ficha_epidemiologia_n1.setDesbridamiento(chbDesbridamiento.isChecked());
		ficha_epidemiologia_n1.setFasciotomia(chbFasciotomia.isChecked());
		ficha_epidemiologia_n1.setInjerto_de_piel(chbInjerto_de_piel
				.isChecked());
		ficha_epidemiologia_n1.setAmputacion(chbAmputacion.isChecked());
		ficha_epidemiologia_n1.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n1.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n1
				.setCreacion_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n1.setDelete_date(null);
		ficha_epidemiologia_n1.setUltimo_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n1.setDelete_user(null);

		return ficha_epidemiologia_n1;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n1 obj) throws Exception {
		Ficha_epidemiologia_n1 ficha_epidemiologia_n1 = (Ficha_epidemiologia_n1) obj;
		try {
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n1.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n1
					.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n1
					.getFecha_inicial());
			// tbxNombres_y_apellidos.setValue(ficha_epidemiologia_n1.getNombres_y_apellidos());
			// tbxTipo_identidad.setValue(ficha_epidemiologia_n1.getTipo_identidad());
			obtenerAdmision(admision);

			north_ficha.setVisible(true);
			btImprimir.setVisible(true);
			//log.info("-----------------"+1);
			
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			tbxIdentificacion.setValue(ficha_epidemiologia_n1
					.getIdentificacion());
			dtbxFecha_accidente.setValue(ficha_epidemiologia_n1
					.getFecha_accidente());
			tbxDireccion_accidente.setValue(ficha_epidemiologia_n1
					.getDireccion_accidente());
			Utilidades.seleccionarRadio(rdbActividad_reliz_acc,
					ficha_epidemiologia_n1.getActividad_reliz_acc());
			tbxCual_otro1.setValue(ficha_epidemiologia_n1.getCual_otro1());
			Utilidades.seleccionarRadio(rdbAtencion_inicial,
					ficha_epidemiologia_n1.getAtencion_inicial());
			tbxCual_otro2.setValue(ficha_epidemiologia_n1.getCual_otro2());
			Utilidades.seleccionarRadio(rdbPersona_sometida,
					ficha_epidemiologia_n1.getPersona_sometida());
			tbxCual_otro3.setValue(ficha_epidemiologia_n1.getCual_otro3());
			Utilidades.seleccionarRadio(rdbLocalizacion_mordedura,
					ficha_epidemiologia_n1.getLocalizacion_mordedura());
			Utilidades.seleccionarRadio(rdbHay_evidencia_huellas,
					ficha_epidemiologia_n1.getHay_evidencia_huellas());
			Utilidades.seleccionarRadio(rdbPersona_vio_serpiente,
					ficha_epidemiologia_n1.getPersona_vio_serpiente());
			Utilidades.seleccionarRadio(rdbSe_capturo_serpiente,
					ficha_epidemiologia_n1.getSe_capturo_serpiente());
			Utilidades.seleccionarRadio(rdbAgente_agresor_ident,
					ficha_epidemiologia_n1.getAgente_agresor_ident());
			tbxCual_otro4.setValue(ficha_epidemiologia_n1.getCual_otro4());
			Utilidades.seleccionarRadio(rdbAgente_agresor_nomb,
					ficha_epidemiologia_n1.getAgente_agresor_nomb());
			tbxCual_otro5.setValue(ficha_epidemiologia_n1.getCual_otro5());
			chbEdema.setChecked(ficha_epidemiologia_n1.getEdema());
			chbDolor.setChecked(ficha_epidemiologia_n1.getDolor());
			chbEritmeta.setChecked(ficha_epidemiologia_n1.getEritmeta());
			chbFlictenas.setChecked(ficha_epidemiologia_n1.getFlictenas());
			chbParestesias_hipo.setChecked(ficha_epidemiologia_n1
					.getParestesias_hipo());
			chbEquimosis.setChecked(ficha_epidemiologia_n1.getEquimosis());
			chbHematomas.setChecked(ficha_epidemiologia_n1.getHematomas());
			chbOtro1.setChecked(ficha_epidemiologia_n1.getOtro1());
			tbxCual_otro6.setValue(ficha_epidemiologia_n1.getCual_otro6());
			chbNausea.setChecked(ficha_epidemiologia_n1.getNausea());
			chbVomito.setChecked(ficha_epidemiologia_n1.getVomito());
			chbSialorrea.setChecked(ficha_epidemiologia_n1.getSialorrea());
			chbDiarrea.setChecked(ficha_epidemiologia_n1.getDiarrea());
			chbBradicardia.setChecked(ficha_epidemiologia_n1.getBradicardia());
			chbHipotension.setChecked(ficha_epidemiologia_n1.getHipotension());
			chbDolor_abdom.setChecked(ficha_epidemiologia_n1.getDolor_abdom());
			chbFascies_neurot.setChecked(ficha_epidemiologia_n1
					.getFascies_neurot());
			chbAlteraciones_vision.setChecked(ficha_epidemiologia_n1
					.getAlteraciones_vision());
			chbAlteraciones_sensorial.setChecked(ficha_epidemiologia_n1
					.getAlteraciones_sensorial());
			chbDebilidad_muscular.setChecked(ficha_epidemiologia_n1
					.getDebilidad_muscular());
			chbOliguria.setChecked(ficha_epidemiologia_n1.getOliguria());
			chbCianosis.setChecked(ficha_epidemiologia_n1.getCianosis());
			chbHemotaquexia
					.setChecked(ficha_epidemiologia_n1.getHemotaquexia());
			chbEpistaxis.setChecked(ficha_epidemiologia_n1.getEpistaxis());
			chbGingivorragia.setChecked(ficha_epidemiologia_n1
					.getGingivorragia());
			chbHematemesis.setChecked(ficha_epidemiologia_n1.getHematemesis());
			chbHematuria.setChecked(ficha_epidemiologia_n1.getHematuria());
			chbOtro2.setChecked(ficha_epidemiologia_n1.getOtro2());
			chbVertigo.setChecked(ficha_epidemiologia_n1.getVertigo());
			chbPtosis_palpebral.setChecked(ficha_epidemiologia_n1
					.getPtosis_palpebral());
			chbDificultad_hablar.setChecked(ficha_epidemiologia_n1
					.getDificultad_hablar());
			chbDisfagia.setChecked(ficha_epidemiologia_n1.getDisfagia());
			tbxCual_otro7.setValue(ficha_epidemiologia_n1.getCual_otro7());
			chbCelulitis.setChecked(ficha_epidemiologia_n1.getCelulitis());
			chbAbsceso.setChecked(ficha_epidemiologia_n1.getAbsceso());
			chbNecrosis.setChecked(ficha_epidemiologia_n1.getNecrosis());
			chbMionecrosis.setChecked(ficha_epidemiologia_n1.getMionecrosis());
			chbFasceitis.setChecked(ficha_epidemiologia_n1.getFasceitis());
			chbAlteraciones_circul_perf.setChecked(ficha_epidemiologia_n1
					.getAlteraciones_circul_perf());
			chbOtro3.setChecked(ficha_epidemiologia_n1.getOtro3());
			tbxCual_otro8.setValue(ficha_epidemiologia_n1.getCual_otro8());
			chbAnemia_aguda_severa.setChecked(ficha_epidemiologia_n1
					.getAnemia_aguda_severa());
			chbShock_hipovolemico.setChecked(ficha_epidemiologia_n1
					.getShock_hipovolemico());
			chbShock_septico.setChecked(ficha_epidemiologia_n1
					.getShock_septico());
			chbIra.setChecked(ficha_epidemiologia_n1.getIra());
			chbCid.setChecked(ficha_epidemiologia_n1.getCid());
			chbHsa.setChecked(ficha_epidemiologia_n1.getHsa());
			chbEdema_cerebral.setChecked(ficha_epidemiologia_n1
					.getEdema_cerebral());
			chbFalla_ventilatoria.setChecked(ficha_epidemiologia_n1
					.getFalla_ventilatoria());
			chbComa.setChecked(ficha_epidemiologia_n1.getComa());
			chbOtro4.setChecked(ficha_epidemiologia_n1.getOtro4());
			tbxCual_otro9.setValue(ficha_epidemiologia_n1.getCual_otro9());
			Utilidades.seleccionarRadio(rdbGravedad_accidentes,
					ficha_epidemiologia_n1.getGravedad_accidentes());
			Utilidades.seleccionarRadio(rdbEmpleo_suero,
					ficha_epidemiologia_n1.getEmpleo_suero());
			ibxDias.setValue(ficha_epidemiologia_n1.getDias());
			ibxHoras.setValue(ficha_epidemiologia_n1.getHoras());
			Utilidades.seleccionarRadio(rdbTipo_suero_antiofidico,
					ficha_epidemiologia_n1.getTipo_suero_antiofidico());
			Utilidades.seleccionarRadio(rdbReacciones_apli_suero,
					ficha_epidemiologia_n1.getReacciones_apli_suero());
			ibxDosis_de_suero.setValue(ficha_epidemiologia_n1
					.getDosis_de_suero());
			ibxHoras2.setValue(ficha_epidemiologia_n1.getHoras2());
			// dbxMinutos.setValue(ficha_epidemiologia_n1.getMinutos());
			Utilidades.seleccionarRadio(rdbRemitido_otra_instit,
					ficha_epidemiologia_n1.getRemitido_otra_instit());
			Utilidades.seleccionarRadio(rdbTratamiento_quirurgico,
					ficha_epidemiologia_n1.getTratamiento_quirurgico());
			chbDrenaje_absceso.setChecked(ficha_epidemiologia_n1
					.getDrenaje_absceso());
			chbLimpieza_quirurgica.setChecked(ficha_epidemiologia_n1
					.getLimpieza_quirurgica());
			chbDesbridamiento.setChecked(ficha_epidemiologia_n1
					.getDesbridamiento());
			chbFasciotomia.setChecked(ficha_epidemiologia_n1.getFasciotomia());
			chbInjerto_de_piel.setChecked(ficha_epidemiologia_n1
					.getInjerto_de_piel());
			chbAmputacion.setChecked(ficha_epidemiologia_n1.getAmputacion());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n1 ficha_epidemiologia_n1 = (Ficha_epidemiologia_n1) obj;
		try {
			int result = getServiceLocator().getFicha_epidemiologia_nnService()
					.eliminar(ficha_epidemiologia_n1);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
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

	public void deshabilitar_conRadio(Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			String valor = "S";
			FormularioUtil.deshabilitarComponentes_conRadiogroup(radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void deshabilitar_conCheck(Checkbox checkbox,
			AbstractComponent[] abstractComponents) {
		try {

			FormularioUtil.deshabilitarComponentes_conCheckbox(checkbox,
					abstractComponents);

		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void obtenerAdmision(Admision admision) {
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
	public Ficha_epidemiologia_n1 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n1> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n1.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n1 ficha_n1 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n1;
				}else{

					return null;
				}
	}
	

	public void imprimir() throws Exception {
		
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Ficha_epidemiologia");
		paramRequest.put("empresa", empresa.getCodigo_empresa());
		paramRequest.put("sucursal", sucursal.getCodigo_sucursal());
		paramRequest.put("codigo_ficha", tbxCodigo_ficha.getValue());
		paramRequest.put("ficha_epidemiologia", IFichas_epidemiologicas.ACCIDENTE_OFIDICO);

		//log.info("codigo_ficha"+tbxCodigo_ficha.getValue());
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

}