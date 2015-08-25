/*
 * ficha_epidemiologia_n30Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n30;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n30Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n30> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n30Action.class);

	// Componentes //
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad_p;
	@View
	private Textbox tbxIdentificacion_p;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
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
	private Datebox dtbxFecha_inicial;
	@View
	private Textbox tbxCodigo_ficha;
	@View
	private Radiogroup rdbPaciente_remitida;
	@View
	private Textbox tbxInstitucion_referencia1;
	@View
	private Textbox tbxInstitucion_referencia2;
	@View
	private Intbox ibxNumero_gestacion;
	@View
	private Intbox ibxPartos_vaginales;
	@View
	private Intbox ibxCesareas;
	@View
	private Intbox ibxAbortos;
	@View
	private Intbox ibxMolas;
	@View
	private Intbox ibxEctopicos;
	@View
	private Intbox ibxMuertos;
	@View
	private Intbox ibxVivios;
	@View
	private Intbox ibxPeriodo_intergenetico;
	@View
	private Radiogroup rdbControl_prenatal;
	@View
	private Intbox ibxSem_inicio_cpn;
	@View
	private Radiogroup rdbTerminacion_gestacion;
	@View
	private Intbox ibxEdad_gestacion;
	@View
	private Radiogroup rdbMomento_terminacion_gestacion;
	@View
	private Radiogroup rdbEstado_recien_nacido;
	@View
	private Doublebox dbxPeso_recien_nacido;
	@View
	private Radiogroup rdbEclampsia;
	@View
	private Radiogroup rdbChoque_eseptico;
	@View
	private Radiogroup rdbChoque_hipovolemico;
	@View
	private Radiogroup rdbCardiaca;
	@View
	private Radiogroup rdbVascular;
	@View
	private Radiogroup rdbRenal;
	@View
	private Radiogroup rdbHepatica;
	@View
	private Radiogroup rdbMetabolica;
	@View
	private Radiogroup rdbCerebral;
	@View
	private Radiogroup rdbRespiratoria;
	@View
	private Radiogroup rdbCoagulacion;
	@View
	private Radiogroup rdbIngreso_uci;
	@View
	private Radiogroup rdbCirugia_adicional;
	@View
	private Radiogroup rdbTrasfusion;
	@View
	private Textbox tbxTotal_criterio;
	@View
	private Intbox ibxDias_estancia_hosp;
	@View
	private Intbox ibxDias_estancia_cui_intensiv;
	@View
	private Intbox ibxUnidades_transfundidas;
	@View
	private Radiogroup rdbCirugia_adicional1;
	@View
	private Textbox tbxCual1;
	@View
	private Radiogroup rdbCirugia_adicional2;
	@View
	private Textbox tbxCual2;
	@View
	private Textbox tbxCausas_principal_cie;
	@View
	private Textbox tbxCodigo_cie;
	@View
	private Checkbox chbTrastorno_hiper;
	@View
	private Checkbox chbComplicaciones_hemorragia;
	@View
	private Checkbox chbComplicaciones_aborto;
	@View
	private Checkbox chbSepsi_origen_obs;
	@View
	private Checkbox chbSepsi_origen_no_obs;
	@View
	private Checkbox chbSepsi_origen_pulm;
	@View
	private Checkbox chbEnfer_preexis_compli;
	@View
	private Checkbox chbOtras_caus;
	@View
	private Textbox tbxOtra_caus_tex;
	@View
	private Textbox tbxCausas_asociadas_cie1;
	@View
	private Textbox tbxCod_caus_aso1;
	@View
	private Textbox tbxCausas_asociadas_cie2;
	@View
	private Textbox tbxCod_caus_aso2;
	@View
	private Textbox tbxCausas_asociadas_cie3;
	@View
	private Textbox tbxCod_caus_aso3;
	@View
	private Radiogroup rdbRetraso_tipo1;
	@View
	private Textbox tbxDescripcion_retraso1;
	@View
	private Radiogroup rdbRetraso_tipo2;
	@View
	private Textbox tbxDescripcion_retraso2;
	@View
	private Radiogroup rdbRetraso_tipo3;
	@View
	private Textbox tbxDescripcion_retraso3;
	@View
	private Radiogroup rdbRetraso_tipo4;
	@View
	private Textbox tbxDescripcion_retraso4;

//	private Datebox dtbxDelete_date;

//	private Textbox tbxDelete_user;
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n30 ficha = new Ficha_epidemiologia_n30();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n30) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 30-------> "+ficha);
			
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

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha_inicial");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	//
	public void seleccionCheck(Checkbox che, Textbox tex) {
		if (che.isChecked()) {
			tex.setReadonly(false);
		} else {
			tex.setReadonly(true);
			tex.setText(" ");
		}
	}

	public void seleccionRadio(Radiogroup radio, Textbox textb) {
		if (radio.getSelectedItem().getValue().equals("S")) {
			textb.setReadonly(false);
		} else {
			textb.setReadonly(true);
			textb.setText("");
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
	public boolean validarFichaEpidemiologia() {

		boolean valida = true;

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

			getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
					"limit 25 offset 0");

			List<Ficha_epidemiologia_n30> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(Ficha_epidemiologia_n30.class , parameters);
			rowsResultado.getChildren().clear();
			

			for (Ficha_epidemiologia_n30 ficha_epidemiologia_n30 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n30,
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

		final Ficha_epidemiologia_n30 ficha_epidemiologia_n30 = (Ficha_epidemiologia_n30) objeto;

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
				mostrarDatos(ficha_epidemiologia_n30);
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
									eliminarDatos(ficha_epidemiologia_n30);
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
	public Ficha_epidemiologia_n30 obtenerFichaEpidemiologia() {

		// Cargamos los componentes //

		Ficha_epidemiologia_n30 ficha_epidemiologia_n30 = new Ficha_epidemiologia_n30();
		ficha_epidemiologia_n30.setFecha_inicial(new Timestamp(
				dtbxFecha_inicial.getValue().getTime()));
		ficha_epidemiologia_n30.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n30.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		ficha_epidemiologia_n30.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n30.setIdentificacion("123");
		ficha_epidemiologia_n30.setCodigo_diagnostico("A046");
		ficha_epidemiologia_n30.setPrimer_nombre_paciente("");
		ficha_epidemiologia_n30.setSegundo_nombre_paciente("");
		ficha_epidemiologia_n30.setPrimer_apellido_paciente("");
		ficha_epidemiologia_n30.setSegundo_apellido_paciente("");
		ficha_epidemiologia_n30.setTipo_identidad("");
		ficha_epidemiologia_n30.setNumero_identidad("");
		ficha_epidemiologia_n30.setPaciente_remitida(rdbPaciente_remitida
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30
				.setInstitucion_referencia1(tbxInstitucion_referencia1
						.getValue());
		ficha_epidemiologia_n30
				.setInstitucion_referencia2(tbxInstitucion_referencia2
						.getValue());
		ficha_epidemiologia_n30.setNumero_gestacion((ibxNumero_gestacion
				.getValue() != null ? ibxNumero_gestacion.getValue().toString()
				: "0"));
		ficha_epidemiologia_n30.setPartos_vaginales((ibxPartos_vaginales
				.getValue() != null ? ibxPartos_vaginales.getValue().toString()
				: "0"));
		ficha_epidemiologia_n30
				.setCesareas((ibxCesareas.getValue() != null ? ibxCesareas
						.getValue().toString() : "0"));
		ficha_epidemiologia_n30
				.setAbortos((ibxAbortos.getValue() != null ? ibxAbortos
						.getValue().toString() : "0"));
		ficha_epidemiologia_n30
				.setMolas((ibxMolas.getValue() != null ? ibxMolas.getValue()
						.toString() : "0"));
		ficha_epidemiologia_n30
				.setEctopicos((ibxEctopicos.getValue() != null ? ibxEctopicos
						.getValue().toString() : "0"));
		ficha_epidemiologia_n30
				.setMuertos((ibxMuertos.getValue() != null ? ibxMuertos
						.getValue().toString() : "0"));
		ficha_epidemiologia_n30
				.setVivios((ibxVivios.getValue() != null ? ibxVivios.getValue()
						.toString() : "0"));
		ficha_epidemiologia_n30
				.setPeriodo_intergenetico((ibxPeriodo_intergenetico.getValue() != null ? ibxPeriodo_intergenetico
						.getValue().toString() : "0"));
		ficha_epidemiologia_n30.setControl_prenatal(rdbControl_prenatal
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30
				.setSem_inicio_cpn((ibxSem_inicio_cpn.getValue() != null ? ibxSem_inicio_cpn
						.getValue().toString() : "0"));
		ficha_epidemiologia_n30
				.setTerminacion_gestacion(rdbTerminacion_gestacion
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30
				.setEdad_gestacion((ibxEdad_gestacion.getValue() != null ? ibxEdad_gestacion
						.getValue().toString() : "0"));
		ficha_epidemiologia_n30
				.setMomento_terminacion_gestacion(rdbMomento_terminacion_gestacion
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setEstado_recien_nacido(rdbEstado_recien_nacido
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setPeso_recien_nacido((dbxPeso_recien_nacido
				.getValue() != null ? dbxPeso_recien_nacido.getValue()
				.toString() : "0.00"));
		ficha_epidemiologia_n30.setEclampsia(rdbEclampsia.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n30.setChoque_eseptico(rdbChoque_eseptico
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setChoque_hipovolemico(rdbChoque_hipovolemico
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setCardiaca(rdbCardiaca.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n30.setVascular(rdbVascular.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n30.setRenal(rdbRenal.getSelectedItem().getValue()
				.toString());
		ficha_epidemiologia_n30.setHepatica(rdbHepatica.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n30.setMetabolica(rdbMetabolica.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n30.setCerebral(rdbCerebral.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n30.setRespiratoria(rdbRespiratoria
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setCoagulacion(rdbCoagulacion.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n30.setIngreso_uci(rdbIngreso_uci.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n30.setCirugia_adicional(rdbCirugia_adicional
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setTrasfusion(rdbTrasfusion.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n30.setTotal_criterio(tbxTotal_criterio.getValue());
		ficha_epidemiologia_n30.setDias_estancia_hosp((ibxDias_estancia_hosp
				.getValue() != null ? ibxDias_estancia_hosp.getValue()
				.toString() : "0"));
		ficha_epidemiologia_n30
				.setDias_estancia_cui_intensiv((ibxDias_estancia_cui_intensiv
						.getValue() != null ? ibxDias_estancia_cui_intensiv
						.getValue().toString() : "0"));
		ficha_epidemiologia_n30
				.setUnidades_transfundidas((ibxUnidades_transfundidas
						.getValue() != null ? ibxUnidades_transfundidas
						.getValue().toString() : "0"));
		ficha_epidemiologia_n30.setCirugia_adicional1(rdbCirugia_adicional1
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setCual1(tbxCual1.getValue());
		ficha_epidemiologia_n30.setCirugia_adicional2(rdbCirugia_adicional2
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setCual2(tbxCual2.getValue());
		ficha_epidemiologia_n30.setCausas_principal_cie(tbxCausas_principal_cie
				.getValue());
		ficha_epidemiologia_n30.setCodigo_cie(tbxCodigo_cie.getValue());
		ficha_epidemiologia_n30.setTrastorno_hiper(chbTrastorno_hiper
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n30
				.setComplicaciones_hemorragia(chbComplicaciones_hemorragia
						.isChecked() ? "S" : "N");
		ficha_epidemiologia_n30
				.setComplicaciones_aborto(chbComplicaciones_aborto.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n30.setSepsi_origen_obs(chbSepsi_origen_obs
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n30.setSepsi_origen_no_obs(chbSepsi_origen_no_obs
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n30.setSepsi_origen_pulm(chbSepsi_origen_pulm
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n30.setEnfer_preexis_compli(chbEnfer_preexis_compli
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n30.setOtras_caus(chbOtras_caus.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n30.setOtra_caus_tex(tbxOtra_caus_tex.getValue());
		ficha_epidemiologia_n30
				.setCausas_asociadas_cie1(tbxCausas_asociadas_cie1.getValue());
		ficha_epidemiologia_n30.setCod_caus_aso1(tbxCod_caus_aso1.getValue());
		ficha_epidemiologia_n30
				.setCausas_asociadas_cie2(tbxCausas_asociadas_cie2.getValue());
		ficha_epidemiologia_n30.setCod_caus_aso2(tbxCod_caus_aso2.getValue());
		ficha_epidemiologia_n30
				.setCausas_asociadas_cie3(tbxCausas_asociadas_cie3.getValue());
		ficha_epidemiologia_n30.setCod_caus_aso3(tbxCod_caus_aso3.getValue());
		ficha_epidemiologia_n30.setRetraso_tipo1(rdbRetraso_tipo1
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setDescripcion_retraso1(tbxDescripcion_retraso1
				.getValue());
		ficha_epidemiologia_n30.setRetraso_tipo2(rdbRetraso_tipo2
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setDescripcion_retraso2(tbxDescripcion_retraso2
				.getValue());
		ficha_epidemiologia_n30.setRetraso_tipo3(rdbRetraso_tipo3
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setDescripcion_retraso3(tbxDescripcion_retraso3
				.getValue());
		ficha_epidemiologia_n30.setRetraso_tipo4(rdbRetraso_tipo4
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n30.setDescripcion_retraso4(tbxDescripcion_retraso4
				.getValue());
		ficha_epidemiologia_n30.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n30.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n30.setCreacion_user(usuarios.getCodigo()
				.toString());
		ficha_epidemiologia_n30.setUltimo_user(usuarios.getCodigo().toString());

		return ficha_epidemiologia_n30;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n30 obj) throws Exception {
		Ficha_epidemiologia_n30 ficha_epidemiologia_n30 = (Ficha_epidemiologia_n30) obj;
		try {
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n30
					.getFecha_inicial());
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n30.getCodigo_ficha());
			

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbPaciente_remitida,
					ficha_epidemiologia_n30.getPaciente_remitida());
			tbxInstitucion_referencia1.setValue(ficha_epidemiologia_n30
					.getInstitucion_referencia1());
			tbxInstitucion_referencia2.setValue(ficha_epidemiologia_n30
					.getInstitucion_referencia2());
			ibxNumero_gestacion.setValue(Integer
					.valueOf(ficha_epidemiologia_n30.getNumero_gestacion()));
			ibxPartos_vaginales.setValue(Integer
					.valueOf(ficha_epidemiologia_n30.getPartos_vaginales()));
			ibxCesareas.setValue(Integer.valueOf(ficha_epidemiologia_n30
					.getCesareas()));
			ibxAbortos.setValue(Integer.valueOf(ficha_epidemiologia_n30
					.getAbortos()));
			ibxMolas.setValue(Integer.valueOf(ficha_epidemiologia_n30
					.getMolas()));
			ibxEctopicos.setValue(Integer.valueOf(ficha_epidemiologia_n30
					.getEctopicos()));
			ibxMuertos.setValue(Integer.valueOf(ficha_epidemiologia_n30
					.getMuertos()));
			ibxVivios.setValue(Integer.valueOf(ficha_epidemiologia_n30
					.getVivios()));
			ibxPeriodo_intergenetico
					.setValue(Integer.valueOf(ficha_epidemiologia_n30
							.getPeriodo_intergenetico()));
			Utilidades.seleccionarRadio(rdbControl_prenatal,
					ficha_epidemiologia_n30.getControl_prenatal());
			ibxSem_inicio_cpn.setValue(Integer.valueOf(ficha_epidemiologia_n30
					.getSem_inicio_cpn()));
			Utilidades.seleccionarRadio(rdbTerminacion_gestacion,
					ficha_epidemiologia_n30.getTerminacion_gestacion());
			ibxEdad_gestacion.setValue(Integer.valueOf(ficha_epidemiologia_n30
					.getEdad_gestacion()));
			Utilidades.seleccionarRadio(rdbMomento_terminacion_gestacion,
					ficha_epidemiologia_n30.getMomento_terminacion_gestacion());
			Utilidades.seleccionarRadio(rdbEstado_recien_nacido,
					ficha_epidemiologia_n30.getEstado_recien_nacido());
			dbxPeso_recien_nacido.setValue(Double
					.valueOf(ficha_epidemiologia_n30.getPeso_recien_nacido()));
			Utilidades.seleccionarRadio(rdbEclampsia,
					ficha_epidemiologia_n30.getEclampsia());
			Utilidades.seleccionarRadio(rdbChoque_eseptico,
					ficha_epidemiologia_n30.getChoque_eseptico());
			Utilidades.seleccionarRadio(rdbChoque_hipovolemico,
					ficha_epidemiologia_n30.getChoque_hipovolemico());
			Utilidades.seleccionarRadio(rdbCardiaca,
					ficha_epidemiologia_n30.getCardiaca());
			Utilidades.seleccionarRadio(rdbVascular,
					ficha_epidemiologia_n30.getVascular());
			Utilidades.seleccionarRadio(rdbRenal,
					ficha_epidemiologia_n30.getRenal());
			Utilidades.seleccionarRadio(rdbHepatica,
					ficha_epidemiologia_n30.getHepatica());
			Utilidades.seleccionarRadio(rdbMetabolica,
					ficha_epidemiologia_n30.getMetabolica());
			Utilidades.seleccionarRadio(rdbCerebral,
					ficha_epidemiologia_n30.getCerebral());
			Utilidades.seleccionarRadio(rdbRespiratoria,
					ficha_epidemiologia_n30.getRespiratoria());
			Utilidades.seleccionarRadio(rdbCoagulacion,
					ficha_epidemiologia_n30.getCoagulacion());
			Utilidades.seleccionarRadio(rdbIngreso_uci,
					ficha_epidemiologia_n30.getIngreso_uci());
			Utilidades.seleccionarRadio(rdbCirugia_adicional,
					ficha_epidemiologia_n30.getCirugia_adicional());
			Utilidades.seleccionarRadio(rdbTrasfusion,
					ficha_epidemiologia_n30.getTrasfusion());
			tbxTotal_criterio.setValue(ficha_epidemiologia_n30
					.getTotal_criterio());
			ibxDias_estancia_hosp.setValue(Integer
					.valueOf(ficha_epidemiologia_n30.getDias_estancia_hosp()));
			ibxDias_estancia_cui_intensiv.setValue(Integer
					.valueOf(ficha_epidemiologia_n30
							.getDias_estancia_cui_intensiv()));
			ibxUnidades_transfundidas.setValue(Integer
					.valueOf(ficha_epidemiologia_n30
							.getUnidades_transfundidas()));
			Utilidades.seleccionarRadio(rdbCirugia_adicional1,
					ficha_epidemiologia_n30.getCirugia_adicional1());
			tbxCual1.setValue(ficha_epidemiologia_n30.getCual1());
			Utilidades.seleccionarRadio(rdbCirugia_adicional2,
					ficha_epidemiologia_n30.getCirugia_adicional2());
			tbxCual2.setValue(ficha_epidemiologia_n30.getCual2());
			tbxCausas_principal_cie.setValue(ficha_epidemiologia_n30
					.getCausas_principal_cie());
			tbxCodigo_cie.setValue(ficha_epidemiologia_n30.getCodigo_cie());
			chbTrastorno_hiper.setChecked(ficha_epidemiologia_n30
					.getTrastorno_hiper().equals("S") ? true : false);
			chbComplicaciones_hemorragia.setChecked(ficha_epidemiologia_n30
					.getComplicaciones_hemorragia().equals("S") ? true : false);
			chbComplicaciones_aborto.setChecked(ficha_epidemiologia_n30
					.getComplicaciones_aborto().equals("S") ? true : false);
			chbSepsi_origen_obs.setChecked(ficha_epidemiologia_n30
					.getSepsi_origen_obs().equals("S") ? true : false);
			chbSepsi_origen_no_obs.setChecked(ficha_epidemiologia_n30
					.getSepsi_origen_no_obs().equals("S") ? true : false);
			chbSepsi_origen_pulm.setChecked(ficha_epidemiologia_n30
					.getSepsi_origen_pulm().equals("S") ? true : false);
			chbEnfer_preexis_compli.setChecked(ficha_epidemiologia_n30
					.getEnfer_preexis_compli().equals("S") ? true : false);
			chbOtras_caus.setChecked(ficha_epidemiologia_n30.getOtras_caus()
					.equals("S") ? true : false);
			tbxOtra_caus_tex.setValue(ficha_epidemiologia_n30
					.getOtra_caus_tex());
			tbxCausas_asociadas_cie1.setValue(ficha_epidemiologia_n30
					.getCausas_asociadas_cie1());
			tbxCod_caus_aso1.setValue(ficha_epidemiologia_n30
					.getCod_caus_aso1());
			tbxCausas_asociadas_cie2.setValue(ficha_epidemiologia_n30
					.getCausas_asociadas_cie2());
			tbxCod_caus_aso2.setValue(ficha_epidemiologia_n30
					.getCod_caus_aso2());
			tbxCausas_asociadas_cie3.setValue(ficha_epidemiologia_n30
					.getCausas_asociadas_cie3());
			tbxCod_caus_aso3.setValue(ficha_epidemiologia_n30
					.getCod_caus_aso3());
			Utilidades.seleccionarRadio(rdbRetraso_tipo1,
					ficha_epidemiologia_n30.getRetraso_tipo1());
			tbxDescripcion_retraso1.setValue(ficha_epidemiologia_n30
					.getDescripcion_retraso1());
			Utilidades.seleccionarRadio(rdbRetraso_tipo2,
					ficha_epidemiologia_n30.getRetraso_tipo2());
			tbxDescripcion_retraso2.setValue(ficha_epidemiologia_n30
					.getDescripcion_retraso2());
			Utilidades.seleccionarRadio(rdbRetraso_tipo3,
					ficha_epidemiologia_n30.getRetraso_tipo3());
			tbxDescripcion_retraso3.setValue(ficha_epidemiologia_n30
					.getDescripcion_retraso3());
			Utilidades.seleccionarRadio(rdbRetraso_tipo4,
					ficha_epidemiologia_n30.getRetraso_tipo4());
			tbxDescripcion_retraso4.setValue(ficha_epidemiologia_n30
					.getDescripcion_retraso4());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n30 ficha_epidemiologia_n30 = (Ficha_epidemiologia_n30) obj;
		try {
			int result = getServiceLocator().getFicha_epidemiologia_nnService()
					.eliminar(ficha_epidemiologia_n30);
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

	public void obtenerAdmision(Admision admision) {
		Paciente paciente = admision.getPaciente();
		tbxIdentificacion_p.setValue(admision.getNro_identificacion());
		tbxNombres_y_apellidos.setValue(paciente.getNombreCompleto());
		tbxTipo_identidad_p.setValue(paciente.getTipo_identificacion());
		
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
	public Ficha_epidemiologia_n30 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n30> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n30.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n30 ficha_n30 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n30;
				}else{

					return null;
				}
	}

}