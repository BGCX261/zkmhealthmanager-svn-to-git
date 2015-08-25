/*
 * ficha_epidemiologia_n8Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n8;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IFichas_epidemiologicas;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n8Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n8> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n8Action.class);

	// Componentes //
	// tbxCodigo_ficha
	@View
	private Textbox tbxCual_agente;
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad_p;
	@View
	private Textbox tbxIdentificacion_p;
	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Textbox tbxCodigo_ficha;
	
//	private Textbox tbxIdentificacion;
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
	private Checkbox chbNauseas;
	@View
	private Checkbox chbVomito;
	@View
	private Checkbox chbDiarrea;
	@View
	private Checkbox chbFiebre;
	@View
	private Checkbox chbCalambres_abdominales;
	@View
	private Checkbox chbCefalea;
	@View
	private Checkbox chbDeshidratacion;
	@View
	private Checkbox chbCianosis;
	@View
	private Checkbox chbMialgias;
	@View
	private Checkbox chbArtralgias;
	@View
	private Checkbox chbMareo;
	@View
	private Checkbox chbLesiones_maculopapulares;
	@View
	private Checkbox chbEscalofrio;
	@View
	private Checkbox chbTos;
	@View
	private Checkbox chbParestesias;
	@View
	private Checkbox chbSialorrea;
	@View
	private Checkbox chbMiosis;
	@View
	private Checkbox chbOtros;
	@View
	private Timebox dtbxHora_inicio_sintomas;
	@View
	private Textbox tbxCual;
	@View
	private Textbox tbxNombre1_alimento_dia;
	@View
	private Textbox tbxLugar1_consumo_dia;
	@View
	private Timebox tbHora1_dia;
	@View
	private Textbox tbxNombre2_alimento_dia;
	@View
	private Textbox tbxLugar2_consumo_dia;
	@View
	private Timebox tbHora2_dia;
	@View
	private Textbox tbxNombre3_alimento_dia;
	@View
	private Textbox tbxLugar3_consumo_dia;
	@View
	private Timebox tbHora3_dia;
	@View
	private Textbox tbxNombre1_alimento_dia_ant;
	@View
	private Textbox tbxLugar1_consumo_dia_ant;
	@View
	private Timebox tbHora1_dia_ant;
	@View
	private Textbox tbxNombre2_alimento_dia_ant;
	@View
	private Textbox tbxLugar2_consumo_dia_ant;
	@View
	private Timebox tbHora2_dia_ant;
	@View
	private Textbox tbxNombre3_alimento_dia_ant;
	@View
	private Textbox tbxLugar3_consumo_dia_ant;
	@View
	private Timebox tbHora3_dia_ant;
	@View
	private Textbox tbxNombre1_alimento_2dia_ant;
	@View
	private Textbox tbxLugar1_consumo_2dia_ant;
	@View
	private Timebox tbHora1_2dia_ant;
	@View
	private Textbox tbxNombre2_alimento_2dia_ant;
	@View
	private Textbox tbxLugar2_consumo_2dia_ant;
	@View
	private Timebox tbHora2_2dia_ant;
	@View
	private Textbox tbxNombre3_alimento_2dia_ant;
	@View
	private Textbox tbxLugar3_consumo_2dia_ant;
	@View
	private Timebox tbHora3_2dia_ant;
	@View
	private Textbox tbxNombre_lugar_implicado;
	@View
	private Textbox tbxDireccion_lugar_implicado;
	@View
	private Radiogroup rdbCaso_asociado_brote;
	@View
	private Radiogroup rdbCaso_captado_por;
	@View
	private Radiogroup rdbRelacion_exposicion;
	@View
	private Radiogroup rdbRecolecto_muestra_biologica;
	@View
	private Radiogroup rdbTipo_muestra;
	@View
	private Textbox tbxCual_tipo_muestra;
	@View
	private Textbox tbxAgente_identificaco1;
	@View
	private Textbox tbxAgente_identificaco2;
	@View
	private Textbox tbxAgente_identificaco3;
	@View
	private Textbox tbxAgente_identificaco4;
	@View
	private Radiogroup rdbAgentes_pendiente;
	@View
	private Radiogroup rdbAgentes_no_detectaddo;
	@View
	private Radiogroup rdbAgente_otro;
	@View
	private Radiogroup rdbAgente_seleccionado;
	private final String[] idsExcluyentes = {};
	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;
	

	@View private Toolbarbutton btImprimir;
	@View private North north_ficha;
	
	
	public void ocultarCampo(Radiogroup rbx,Textbox tx) {
		if (rbx.getSelectedItem().getValue().equals("S")) {
			tx.setReadonly(false);
		}else {
			tx.setReadonly(true);
			tx.setText("");
		}

	}

	public void listarCombos() {
		listarParameter();
	}

	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n8 ficha = new Ficha_epidemiologia_n8();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n8) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 8-------> "+ficha);
			
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
		}
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

		// tbxCodigo_ficha.setStyle("text-transform:uppercase;background-color:white");
		// tbxIdentificacion.setStyle("text-transform:uppercase;background-color:white");

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

			List<Ficha_epidemiologia_n8> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n8.class, parameters);
			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n8 ficha_epidemiologia_n8 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n8,
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

		final Ficha_epidemiologia_n8 ficha_epidemiologia_n8 = (Ficha_epidemiologia_n8) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n8.getCodigo_ficha()
				+ ""));
		fila.appendChild(new Label(ficha_epidemiologia_n8.getIdentificacion()
				+ ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n8);
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
									eliminarDatos(ficha_epidemiologia_n8);
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
	public Ficha_epidemiologia_n8 obtenerFichaEpidemiologia() {
		// Cargamos los componentes //

		Ficha_epidemiologia_n8 ficha_epidemiologia_n8 = new Ficha_epidemiologia_n8();
		ficha_epidemiologia_n8.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n8.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n8.setFecha_inicial(new Timestamp(dtbxFecha_inicial.getValue().getTime()));
		ficha_epidemiologia_n8
				.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		ficha_epidemiologia_n8.setCodigo_diagnostico("A000");
		ficha_epidemiologia_n8.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n8.setIdentificacion(tbxIdentificacion_p.getValue());
		ficha_epidemiologia_n8.setCual_agente(tbxCual_agente.getValue());
		ficha_epidemiologia_n8.setNauseas(chbNauseas.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setVomito(chbVomito.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setDiarrea(chbDiarrea.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setFiebre(chbFiebre.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8
				.setCalambres_abdominales(chbCalambres_abdominales.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n8.setCefalea(chbCefalea.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8
				.setDeshidratacion(chbDeshidratacion.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setCianosis(chbCianosis.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setMialgias(chbMialgias.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setArtralgias(chbArtralgias.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n8.setMareo(chbMareo.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8
				.setLesiones_maculopapulares(chbLesiones_maculopapulares
						.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setEscalofrio(chbEscalofrio.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n8.setTos(chbTos.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setParestesias(chbParestesias.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n8.setSialorrea(chbSialorrea.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n8.setMiosis(chbMiosis.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setOtros(chbOtros.isChecked() ? "S" : "N");
		ficha_epidemiologia_n8.setHora_inicio_sintomas(new Timestamp(
				dtbxHora_inicio_sintomas.getValue().getTime()));
		ficha_epidemiologia_n8.setCual(tbxCual.getValue());
		ficha_epidemiologia_n8.setNombre1_alimento_dia(tbxNombre1_alimento_dia
				.getValue());
		ficha_epidemiologia_n8.setLugar1_consumo_dia(tbxLugar1_consumo_dia
				.getValue());
		ficha_epidemiologia_n8.setHora1_dia(new Timestamp(tbHora1_dia
				.getValue().getTime()));
		ficha_epidemiologia_n8.setNombre2_alimento_dia(tbxNombre2_alimento_dia
				.getValue());
		ficha_epidemiologia_n8.setLugar2_consumo_dia(tbxLugar2_consumo_dia
				.getValue());
		ficha_epidemiologia_n8.setHora2_dia(new Timestamp(tbHora2_dia
				.getValue().getTime()));
		ficha_epidemiologia_n8.setNombre3_alimento_dia(tbxNombre3_alimento_dia
				.getValue());
		ficha_epidemiologia_n8.setLugar3_consumo_dia(tbxLugar3_consumo_dia
				.getValue());
		ficha_epidemiologia_n8.setHora3_dia(new Timestamp(tbHora3_dia
				.getValue().getTime()));
		ficha_epidemiologia_n8
				.setNombre1_alimento_dia_ant(tbxNombre1_alimento_dia_ant
						.getValue());
		ficha_epidemiologia_n8
				.setLugar1_consumo_dia_ant(tbxLugar1_consumo_dia_ant.getValue());
		ficha_epidemiologia_n8.setHora1_dia_ant(new Timestamp(tbHora1_dia_ant
				.getValue().getTime()));
		ficha_epidemiologia_n8
				.setNombre2_alimento_dia_ant(tbxNombre2_alimento_dia_ant
						.getValue());
		ficha_epidemiologia_n8
				.setLugar2_consumo_dia_ant(tbxLugar2_consumo_dia_ant.getValue());
		ficha_epidemiologia_n8.setHora2_dia_ant(new Timestamp(tbHora2_dia_ant
				.getValue().getTime()));
		ficha_epidemiologia_n8
				.setNombre3_alimento_dia_ant(tbxNombre3_alimento_dia_ant
						.getValue());
		ficha_epidemiologia_n8
				.setLugar3_consumo_dia_ant(tbxLugar3_consumo_dia_ant.getValue());
		ficha_epidemiologia_n8.setHora3_dia_ant(new Timestamp(tbHora3_dia_ant
				.getValue().getTime()));
		ficha_epidemiologia_n8
				.setNombre1_alimento_2dia_ant(tbxNombre1_alimento_2dia_ant
						.getValue());
		ficha_epidemiologia_n8
				.setLugar1_consumo_2dia_ant(tbxLugar1_consumo_2dia_ant
						.getValue());
		ficha_epidemiologia_n8.setHora1_2dia_ant(new Timestamp(tbHora1_2dia_ant
				.getValue().getTime()));
		ficha_epidemiologia_n8
				.setNombre2_alimento_2dia_ant(tbxNombre2_alimento_2dia_ant
						.getValue());
		ficha_epidemiologia_n8
				.setLugar2_consumo_2dia_ant(tbxLugar2_consumo_2dia_ant
						.getValue());
		ficha_epidemiologia_n8.setHora2_2dia_ant(new Timestamp(tbHora2_2dia_ant
				.getValue().getTime()));
		ficha_epidemiologia_n8
				.setNombre3_alimento_2dia_ant(tbxNombre3_alimento_2dia_ant
						.getValue());
		ficha_epidemiologia_n8
				.setLugar3_consumo_2dia_ant(tbxLugar3_consumo_2dia_ant
						.getValue());
		ficha_epidemiologia_n8.setHora3_2dia_ant(new Timestamp(tbHora3_2dia_ant
				.getValue().getTime()));
		ficha_epidemiologia_n8
				.setNombre_lugar_implicado(tbxNombre_lugar_implicado.getValue());
		ficha_epidemiologia_n8
				.setDireccion_lugar_implicado(tbxDireccion_lugar_implicado
						.getValue());
		ficha_epidemiologia_n8.setCaso_asociado_brote(rdbCaso_asociado_brote
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n8.setCaso_captado_por(rdbCaso_captado_por
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n8.setRelacion_exposicion(rdbRelacion_exposicion
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n8
				.setRecolecto_muestra_biologica(rdbRecolecto_muestra_biologica
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n8.setTipo_muestra(rdbTipo_muestra
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n8.setCual_tipo_muestra(tbxCual_tipo_muestra
				.getValue());
		ficha_epidemiologia_n8.setAgente_identificaco1(tbxAgente_identificaco1
				.getValue());
		ficha_epidemiologia_n8.setAgente_identificaco2(tbxAgente_identificaco2
				.getValue());
		ficha_epidemiologia_n8.setAgente_identificaco3(tbxAgente_identificaco3
				.getValue());
		ficha_epidemiologia_n8.setAgente_identificaco4(tbxAgente_identificaco4
				.getValue());
		ficha_epidemiologia_n8.setAgentes_pendiente(rdbAgentes_pendiente
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n8
				.setAgentes_no_detectaddo(rdbAgentes_no_detectaddo
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n8.setAgente_otro(rdbAgente_otro.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n8.setAgente_seleccionado(rdbAgente_seleccionado
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n8.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n8.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n8
				.setCreacion_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n8.setDelete_date(null);
		ficha_epidemiologia_n8.setUltimo_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n8.setDelete_user(null);
		
		//log.info("ficha_epidemiologia_n8"+ficha_epidemiologia_n8);

		return ficha_epidemiologia_n8;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n8 obj) throws Exception {
		Ficha_epidemiologia_n8 ficha_epidemiologia_n8 = (Ficha_epidemiologia_n8) obj;
		try {

			tbxCodigo_ficha.setValue(ficha_epidemiologia_n8.getCodigo_ficha());
			
			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			chbNauseas.setChecked(ficha_epidemiologia_n8.getNauseas().equals(
					"S") ? true : false);
			chbVomito
					.setChecked(ficha_epidemiologia_n8.getVomito().equals("S") ? true
							: false);
			chbDiarrea.setChecked(ficha_epidemiologia_n8.getDiarrea().equals(
					"S") ? true : false);
			chbFiebre
					.setChecked(ficha_epidemiologia_n8.getFiebre().equals("S") ? true
							: false);
			chbCalambres_abdominales.setChecked(ficha_epidemiologia_n8
					.getCalambres_abdominales().equals("S") ? true : false);
			chbCefalea.setChecked(ficha_epidemiologia_n8.getCefalea().equals(
					"S") ? true : false);
			chbDeshidratacion.setChecked(ficha_epidemiologia_n8
					.getDeshidratacion().equals("S") ? true : false);
			chbCianosis.setChecked(ficha_epidemiologia_n8.getCianosis().equals(
					"S") ? true : false);
			chbMialgias.setChecked(ficha_epidemiologia_n8.getMialgias().equals(
					"S") ? true : false);
			chbArtralgias.setChecked(ficha_epidemiologia_n8.getArtralgias()
					.equals("S") ? true : false);
			chbMareo.setChecked(ficha_epidemiologia_n8.getMareo().equals("S") ? true
					: false);
			chbLesiones_maculopapulares.setChecked(ficha_epidemiologia_n8
					.getLesiones_maculopapulares().equals("S") ? true : false);
			chbEscalofrio.setChecked(ficha_epidemiologia_n8.getEscalofrio()
					.equals("S") ? true : false);
			chbTos.setChecked(ficha_epidemiologia_n8.getTos().equals("S") ? true
					: false);
			chbParestesias.setChecked(ficha_epidemiologia_n8.getParestesias()
					.equals("S") ? true : false);
			chbSialorrea.setChecked(ficha_epidemiologia_n8.getSialorrea()
					.equals("S") ? true : false);
			chbMiosis
					.setChecked(ficha_epidemiologia_n8.getMiosis().equals("S") ? true
							: false);
			chbOtros.setChecked(ficha_epidemiologia_n8.getOtros().equals("S") ? true
					: false);
			dtbxHora_inicio_sintomas.setValue(ficha_epidemiologia_n8
					.getHora_inicio_sintomas());
			tbxCual.setValue(ficha_epidemiologia_n8.getCual());
			tbxNombre1_alimento_dia.setValue(ficha_epidemiologia_n8
					.getNombre1_alimento_dia());
			tbxLugar1_consumo_dia.setValue(ficha_epidemiologia_n8
					.getLugar1_consumo_dia());
			tbHora1_dia.setValue(ficha_epidemiologia_n8.getHora1_dia());
			tbxNombre2_alimento_dia.setValue(ficha_epidemiologia_n8
					.getNombre2_alimento_dia());
			tbxLugar2_consumo_dia.setValue(ficha_epidemiologia_n8
					.getLugar2_consumo_dia());
			tbHora2_dia.setValue(ficha_epidemiologia_n8.getHora2_dia());
			tbxNombre3_alimento_dia.setValue(ficha_epidemiologia_n8
					.getNombre3_alimento_dia());
			tbxLugar3_consumo_dia.setValue(ficha_epidemiologia_n8
					.getLugar3_consumo_dia());
			tbHora3_dia.setValue(ficha_epidemiologia_n8.getHora3_dia());
			tbxNombre1_alimento_dia_ant.setValue(ficha_epidemiologia_n8
					.getNombre1_alimento_dia_ant());
			tbxLugar1_consumo_dia_ant.setValue(ficha_epidemiologia_n8
					.getLugar1_consumo_dia_ant());
			tbHora1_dia_ant.setValue(ficha_epidemiologia_n8.getHora1_dia_ant());
			tbxNombre2_alimento_dia_ant.setValue(ficha_epidemiologia_n8
					.getNombre2_alimento_dia_ant());
			tbxLugar2_consumo_dia_ant.setValue(ficha_epidemiologia_n8
					.getLugar2_consumo_dia_ant());
			tbHora2_dia_ant.setValue(ficha_epidemiologia_n8.getHora2_dia_ant());
			tbxNombre3_alimento_dia_ant.setValue(ficha_epidemiologia_n8
					.getNombre3_alimento_dia_ant());
			tbxLugar3_consumo_dia_ant.setValue(ficha_epidemiologia_n8
					.getLugar3_consumo_dia_ant());
			tbHora3_dia_ant.setValue(ficha_epidemiologia_n8.getHora3_dia_ant());
			tbxNombre1_alimento_2dia_ant.setValue(ficha_epidemiologia_n8
					.getNombre1_alimento_2dia_ant());
			tbxLugar1_consumo_2dia_ant.setValue(ficha_epidemiologia_n8
					.getLugar1_consumo_2dia_ant());
			tbHora1_2dia_ant.setValue(ficha_epidemiologia_n8
					.getHora1_2dia_ant());
			tbxNombre2_alimento_2dia_ant.setValue(ficha_epidemiologia_n8
					.getNombre2_alimento_2dia_ant());
			tbxLugar2_consumo_2dia_ant.setValue(ficha_epidemiologia_n8
					.getLugar2_consumo_2dia_ant());
			tbHora2_2dia_ant.setValue(ficha_epidemiologia_n8
					.getHora2_2dia_ant());
			tbxNombre3_alimento_2dia_ant.setValue(ficha_epidemiologia_n8
					.getNombre3_alimento_2dia_ant());
			tbxLugar3_consumo_2dia_ant.setValue(ficha_epidemiologia_n8
					.getLugar3_consumo_2dia_ant());
			tbHora3_2dia_ant.setValue(ficha_epidemiologia_n8
					.getHora3_2dia_ant());
			tbxNombre_lugar_implicado.setValue(ficha_epidemiologia_n8
					.getNombre_lugar_implicado());
			tbxDireccion_lugar_implicado.setValue(ficha_epidemiologia_n8
					.getDireccion_lugar_implicado());
			Utilidades.seleccionarRadio(rdbCaso_asociado_brote,
					ficha_epidemiologia_n8.getCaso_asociado_brote());
			Utilidades.seleccionarRadio(rdbCaso_captado_por,
					ficha_epidemiologia_n8.getCaso_captado_por());
			Utilidades.seleccionarRadio(rdbRelacion_exposicion,
					ficha_epidemiologia_n8.getRelacion_exposicion());
			Utilidades.seleccionarRadio(rdbRecolecto_muestra_biologica,
					ficha_epidemiologia_n8.getRecolecto_muestra_biologica());
			Utilidades.seleccionarRadio(rdbTipo_muestra,
					ficha_epidemiologia_n8.getTipo_muestra());
			tbxCual_tipo_muestra.setValue(ficha_epidemiologia_n8
					.getCual_tipo_muestra());
			tbxAgente_identificaco1.setValue(ficha_epidemiologia_n8
					.getAgente_identificaco1());
			tbxAgente_identificaco2.setValue(ficha_epidemiologia_n8
					.getAgente_identificaco2());
			tbxAgente_identificaco3.setValue(ficha_epidemiologia_n8
					.getAgente_identificaco3());
			tbxAgente_identificaco4.setValue(ficha_epidemiologia_n8
					.getAgente_identificaco4());
			Utilidades.seleccionarRadio(rdbAgentes_pendiente,
					ficha_epidemiologia_n8.getAgentes_pendiente());
			Utilidades.seleccionarRadio(rdbAgentes_no_detectaddo,
					ficha_epidemiologia_n8.getAgentes_no_detectaddo());
			Utilidades.seleccionarRadio(rdbAgente_otro,
					ficha_epidemiologia_n8.getAgente_otro());
			Utilidades.seleccionarRadio(rdbAgente_seleccionado,
					ficha_epidemiologia_n8.getAgente_seleccionado());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n8 ficha_epidemiologia_n8 = (Ficha_epidemiologia_n8) obj;
		try {
			int result = getServiceLocator().getFicha_epidemiologia_nnService()
					.eliminar(ficha_epidemiologia_n8);
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
	public Ficha_epidemiologia_n8 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n8> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n8.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n8 ficha_n8 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n8;
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
		paramRequest.put("ficha_epidemiologia", IFichas_epidemiologicas.ENFERMEDAD_POR_ALIMENTOS);

		//log.info("codigo_ficha"+tbxCodigo_ficha.getValue());
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
	
}