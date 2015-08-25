/*
 * ficha_epidemiologia_n2Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n2;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
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

public class Ficha_epidemiologia_n2Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n2> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n2Action.class);

	// Componentes //
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad_p;
	@View
	private Textbox tbxIdentificacion_p;
	@View
	private Datebox dtbxFecha_inicial;
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
	private Textbox tbxIdentificacion;
	@View
	private Textbox tbxCodigo_diagnostico;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View
	private Textbox tbxPrimer_nombre_madre;
	@View
	private Textbox tbxSegundo_nombre_madre;
	@View
	private Textbox tbxPrimer_apellido_madre;
	@View
	private Textbox tbxSegundo_apellido_madre;
	@View
	private Listbox lbxTipo_identidad;
	@View
	private Textbox tbxId_madre;
	@View
	private Textbox tbxPrimer_nombre_rnacido;
	@View
	private Textbox tbxSegundo_nombre_rnacido;
	@View
	private Textbox tbxPrimer_apellido_rnacido;
	@View
	private Textbox tbxSegundo_apellido_rnacido;
	@View
	private Listbox lbxTipo_documento_rnacido;
	@View
	private Intbox ibxNumero_identificacion_rnacido;
	@View
	private Datebox dtbxFecha_rnacimiento;
	@View
	private Intbox ibxEdad_rnacido;
	@View
	private Radiogroup rdbSexo_rnacido;
	@View
	private Intbox ibxPeso_al_nacer;
	@View
	private Intbox ibxTalla_al_nacer;
	@View
	private Doublebox dbxPerimetro_cefalico_rn;
	@View
	private Intbox ibxSemanas_gestacion_parto;
	@View
	private Radiogroup rdbClasificacion_peso;
	@View
	private Radiogroup rdbSitio_atencion_parto;
	@View
	private Radiogroup rdbMultiplicidad_embarazo;
	@View
	private Intbox ibxEdad_gestacion_primer_control;
	@View
	private Intbox ibxNumero_control_prenatal;
	@View
	private Radiogroup rdbCalcio;
	@View
	private Radiogroup rdbHierro;
	@View
	private Radiogroup rdbAc_folico;
	@View
	private Radiogroup rdbSindrome_anemico_embarazo;
	@View
	private Doublebox dbxUltimo_valor_emoglobina_madre;
	@View
	private Radiogroup rdbTrimestre_gestacion_hemoglobina;
	@View
	private Radiogroup rdbIntervalo_intergenesico_mn2anios;
	@View
	private Intbox ibxNumero_embarazos_previos;
	@View
	private Intbox ibxNumero_hijos_vivos;
	@View
	private Radiogroup rdbHijo_bajo_peso_nacer;
	@View
	private Radiogroup rdbPresento_hipertension_embarazo;
	@View
	private Radiogroup rdbAmenaza_parto_pretermino;
	@View
	private Radiogroup rdbConsumio_bebidas_alcoholicas;
	@View
	private Radiogroup rdbEsfuerzo_fisico;
	@View
	private Radiogroup rdbInfeccion_vias_urinarias;
	@View
	private Radiogroup rdbSobrepeso_obesidad;
	@View
	private Radiogroup rdbDesnutricion;
	@View
	private Doublebox dbxPeso_pregestacional;
	@View
	private Doublebox dbxTalla_madre;
	@View
	private Doublebox dbxPeso_ganado_embarazo;
	@View
	private Radiogroup rdbNivel_educativo_madre;
	@View
	private Radiogroup rdbEstado_socioeconomico;
	@View
	private Datebox dtbxFecha_investigacion;
	@View
	private Radiogroup rdbEntidad_realiza_investigacion;
	@View
	private Textbox tbxNombre_realiza_investigacion;
	@View
	private Textbox tbxPerfil_realiza_investigacion;
	@View
	private Textbox tbxPrimer_nombre_realiza_investigacion;
	@View
	private Textbox tbxSegundo_nombre_realiza_investigacion;
	@View
	private Textbox tbxPrimer_apellido_realiza_investigacion;
	@View
	private Textbox tbxSegundo_apellido_realiza_investigacion;
	@View
	private Textbox tbxParentesco_recien_nacido;
	@View
	private Radiogroup rdbMenor_asegurado;
	@View
	private Radiogroup rdbEstado_civil_madre;
	@View
	private Radiogroup rdbIngreso_mensual_hogar;
	@View
	private Radiogroup rdbHacinamiento;
	@View
	private Radiogroup rdbAgua_potable;
	@View
	private Radiogroup rdbLavado_mano_cuidador;
	@View
	private Radiogroup rdbRecipientes_cocina_tapa_lavables;
	@View
	private Radiogroup rdbSistema_eliminacion_excreta;
	@View
	private Radiogroup rdbAlmacenamiento_alimentos;
	@View
	private Radiogroup rdbConcervacion_alimentos;
	@View
	private Radiogroup rdbEvaluacion_condicion_higiene;
	@View
	private Textbox tbxInstitucion_realizaron_controles;
	@View
	private Radiogroup rdbDiabetes;
	@View
	private Radiogroup rdbAmenaza_aborto;
	@View
	private Radiogroup rdbOtra_complicacion;
	@View
	private Textbox tbxCual;
	@View
	private Radiogroup rdbRecibio_realizo_tratamiento;
	@View
	private Radiogroup rdbDificultad_respirar;
	@View
	private Radiogroup rdbDificultad_alimentarse;
	@View
	private Radiogroup rdbReflujo_gastroesofagico;
	@View
	private Radiogroup rdbVomito;
	@View
	private Radiogroup rdbDiarrea;
	@View
	private Radiogroup rdbRecibio_realizo_tratamiento_recien_nacido;
	@View
	private Radiogroup rdbRecibio_vacunacion_bcg;
	@View
	private Intbox ibxPeso_gramo;
	@View
	private Doublebox dbxTalla_centimetro;
	@View
	private Doublebox dbxPerimetro_cefalico;
	@View
	private Radiogroup rdbEstado_actual;
	@View
	private Radiogroup rdbRecibe_lactancia_materna;
	@View
	private Radiogroup rdbLibre_demanda;
	@View
	private Radiogroup rdbEn_la_noche;
	@View
	private Radiogroup rdbAlimento_diferente_lactancia;
	@View
	private Radiogroup rdbRecibe_leche_formula;
	@View
	private Intbox ibxNumero_tomas;
	@View
	private Radiogroup rdbDificultades_lactancia_materna;
	@View
	private Radiogroup rdbPezon_fisurado;
	@View
	private Radiogroup rdbBaja_produccion_libre;
	@View
	private Radiogroup rdbCongestion_mamaria;
	@View
	private Radiogroup rdbMastitis;
	@View
	private Radiogroup rdbOtra_complicacion_alimentacion_rn;
	@View
	private Textbox tbxCual_alimentacion_recibido;
	@View
	private Radiogroup rdbRecibio_capacitacion_lactancia_materna;
	@View
	private Radiogroup rdbQuedaron_sin_alimentos_falta_dinero;
	@View
	private Radiogroup rdbQuedaron_sin_comer_falta_dinero;
	@View
	private Radiogroup rdbRecibio_alimentos_institucion;
	@View
	private Radiogroup rdbRealiza_educacion_alimentaria;
	@View
	private Radiogroup rdbRemite_servicio_salud;
	@View
	private Radiogroup rdbCanaliza_programas_apoyo_alimentario;
	@View
	private Datebox dtbxFecha_visita_control;
	@View
	private Intbox ibxPeso_grmos_visita_seguimiento;
	@View
	private Doublebox dbxTalla_visita_seguimiento;
	@View
	private Doublebox dbxPerimetro_cefalico_visita_seguimiento;
	@View
	private Radiogroup rdbEstado_actual_visita_seguimiento;
	@View
	private Textbox tbxNombre_profesional;
	@View
	private Textbox tbxCargo;
	@View
	private Textbox tbxInformacion_complementaria;
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n2 ficha = new Ficha_epidemiologia_n2();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n2) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 2-------> "+ficha);
			
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
		Utilidades.listarElementoListbox(lbxTipo_identidad, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_documento_rnacido, true,
				getServiceLocator());
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

			List<Ficha_epidemiologia_n2> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n2.class, parameters);

			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n2 ficha_epidemiologia_n2 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n2,
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

		final Ficha_epidemiologia_n2 ficha_epidemiologia_n2 = (Ficha_epidemiologia_n2) objeto;

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
				mostrarDatos(ficha_epidemiologia_n2);
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
									eliminarDatos(ficha_epidemiologia_n2);
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
	public Ficha_epidemiologia_n2 obtenerFichaEpidemiologia() {

		Ficha_epidemiologia_n2 ficha_epidemiologia_n2 = new Ficha_epidemiologia_n2();
		ficha_epidemiologia_n2.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n2
				.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		ficha_epidemiologia_n2.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n2.setIdentificacion(tbxIdentificacion.getValue());
		ficha_epidemiologia_n2.setCodigo_diagnostico(tbxCodigo_diagnostico
				.getValue());
		ficha_epidemiologia_n2.setPrimer_nombre_madre(tbxPrimer_nombre_madre
				.getValue());
		ficha_epidemiologia_n2.setSegundo_nombre_madre(tbxSegundo_nombre_madre
				.getValue());
		ficha_epidemiologia_n2
				.setPrimer_apellido_madre(tbxPrimer_apellido_madre.getValue());
		ficha_epidemiologia_n2
				.setSegundo_apellido_madre(tbxSegundo_apellido_madre.getValue());
		ficha_epidemiologia_n2.setTipo_identidad(lbxTipo_identidad
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setId_madre(tbxId_madre.getValue());
		ficha_epidemiologia_n2
				.setPrimer_nombre_rnacido(tbxPrimer_nombre_rnacido.getValue());
		ficha_epidemiologia_n2
				.setSegundo_nombre_rnacido(tbxSegundo_nombre_rnacido.getValue());
		ficha_epidemiologia_n2
				.setPrimer_apellido_rnacido(tbxPrimer_apellido_rnacido
						.getValue());
		ficha_epidemiologia_n2
				.setSegundo_apellido_rnacido(tbxSegundo_apellido_rnacido
						.getValue());
		ficha_epidemiologia_n2
				.setTipo_documento_rnacido(lbxTipo_documento_rnacido
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setNumero_identificacion_rnacido((ibxNumero_identificacion_rnacido
						.getValue() != null ? ibxNumero_identificacion_rnacido
						.getValue().toString() : "0"));
		ficha_epidemiologia_n2.setFecha_rnacimiento(new Timestamp(
				dtbxFecha_rnacimiento.getValue().getTime()));
		ficha_epidemiologia_n2
				.setEdad_rnacido((ibxEdad_rnacido.getValue() != null ? ibxEdad_rnacido
						.getValue().toString() : "0"));
		ficha_epidemiologia_n2.setSexo_rnacido(rdbSexo_rnacido
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setPeso_al_nacer((ibxPeso_al_nacer.getValue() != null ? ibxPeso_al_nacer
						.getValue().toString() : "0"));
		ficha_epidemiologia_n2
				.setTalla_al_nacer((ibxTalla_al_nacer.getValue() != null ? ibxTalla_al_nacer
						.getValue().toString() : "0"));
		ficha_epidemiologia_n2
				.setPerimetro_cefalico_rn((dbxPerimetro_cefalico_rn.getValue() != null ? dbxPerimetro_cefalico_rn
						.getValue().toString() : "0.00"));
		ficha_epidemiologia_n2
				.setSemanas_gestacion_parto((ibxSemanas_gestacion_parto
						.getValue() != null ? ibxSemanas_gestacion_parto
						.getValue().toString() : "0.00"));
		ficha_epidemiologia_n2.setClasificacion_peso(rdbClasificacion_peso
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setSitio_atencion_parto(rdbSitio_atencion_parto
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setMultiplicidad_embarazo(rdbMultiplicidad_embarazo
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setEdad_gestacion_primer_control((ibxEdad_gestacion_primer_control
						.getValue() != null ? ibxEdad_gestacion_primer_control
						.getValue().toString() : "0"));
		ficha_epidemiologia_n2
				.setNumero_control_prenatal((ibxNumero_control_prenatal
						.getValue() != null ? ibxNumero_control_prenatal
						.getValue().toString() : "0"));
		ficha_epidemiologia_n2.setCalcio(rdbCalcio.getSelectedItem().getValue()
				.toString());
		ficha_epidemiologia_n2.setHierro(rdbHierro.getSelectedItem().getValue()
				.toString());
		ficha_epidemiologia_n2.setAc_folico(rdbAc_folico.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n2
				.setSindrome_anemico_embarazo(rdbSindrome_anemico_embarazo
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setUltimo_valor_emoglobina_madre((dbxUltimo_valor_emoglobina_madre
						.getValue() != null ? dbxUltimo_valor_emoglobina_madre
						.getValue().toString() : "0.0"));
		ficha_epidemiologia_n2
				.setTrimestre_gestacion_hemoglobina(rdbTrimestre_gestacion_hemoglobina
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setIntervalo_intergenesico_mn2anios(rdbIntervalo_intergenesico_mn2anios
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setNumero_embarazos_previos((ibxNumero_embarazos_previos
						.getValue() != null ? ibxNumero_embarazos_previos
						.getValue().toString() : "0"));
		ficha_epidemiologia_n2.setNumero_hijos_vivos((ibxNumero_hijos_vivos
				.getValue() != null ? ibxNumero_hijos_vivos.getValue()
				.toString() : "0"));
		ficha_epidemiologia_n2.setHijo_bajo_peso_nacer(rdbHijo_bajo_peso_nacer
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setPresento_hipertension_embarazo(rdbPresento_hipertension_embarazo
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setAmenaza_parto_pretermino(rdbAmenaza_parto_pretermino
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setConsumio_bebidas_alcoholicas(rdbConsumio_bebidas_alcoholicas
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setEsfuerzo_fisico(rdbEsfuerzo_fisico
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setInfeccion_vias_urinarias(rdbInfeccion_vias_urinarias
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setSobrepeso_obesidad(rdbSobrepeso_obesidad
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setDesnutricion(rdbDesnutricion
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setPeso_pregestacional((dbxPeso_pregestacional
				.getValue() != null ? dbxPeso_pregestacional.getValue()
				.toString() : "0.0"));
		ficha_epidemiologia_n2
				.setTalla_madre((dbxTalla_madre.getValue() != null ? dbxTalla_madre
						.getValue().toString() : "0.0"));
		ficha_epidemiologia_n2.setPeso_ganado_embarazo((dbxPeso_ganado_embarazo
				.getValue() != null ? dbxPeso_ganado_embarazo.getValue()
				.toString() : "0.0"));
		ficha_epidemiologia_n2
				.setNivel_educativo_madre(rdbNivel_educativo_madre
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setEstado_socioeconomico(rdbEstado_socioeconomico
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setFecha_investigacion(new Timestamp(
				dtbxFecha_investigacion.getValue().getTime()));
		ficha_epidemiologia_n2
				.setEntidad_realiza_investigacion(rdbEntidad_realiza_investigacion
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setNombre_realiza_investigacion(tbxNombre_realiza_investigacion
						.getValue());
		ficha_epidemiologia_n2
				.setPerfil_realiza_investigacion(tbxPerfil_realiza_investigacion
						.getValue());
		ficha_epidemiologia_n2
				.setPrimer_nombre_realiza_investigacion(tbxPrimer_nombre_realiza_investigacion
						.getValue());
		ficha_epidemiologia_n2
				.setSegundo_nombre_realiza_investigacion(tbxSegundo_nombre_realiza_investigacion
						.getValue());
		ficha_epidemiologia_n2
				.setPrimer_apellido_realiza_investigacion(tbxPrimer_apellido_realiza_investigacion
						.getValue());
		ficha_epidemiologia_n2
				.setSegundo_apellido_realiza_investigacion(tbxSegundo_apellido_realiza_investigacion
						.getValue());
		ficha_epidemiologia_n2
				.setParentesco_recien_nacido(tbxParentesco_recien_nacido
						.getValue());
		ficha_epidemiologia_n2.setMenor_asegurado(rdbMenor_asegurado
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setEstado_civil_madre(rdbEstado_civil_madre
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setIngreso_mensual_hogar(rdbIngreso_mensual_hogar
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setHacinamiento(rdbHacinamiento
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setAgua_potable(rdbAgua_potable
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setLavado_mano_cuidador(rdbLavado_mano_cuidador
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setRecipientes_cocina_tapa_lavables(rdbRecipientes_cocina_tapa_lavables
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setSistema_eliminacion_excreta(rdbSistema_eliminacion_excreta
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setAlmacenamiento_alimentos(rdbAlmacenamiento_alimentos
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setConcervacion_alimentos(rdbConcervacion_alimentos
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setEvaluacion_condicion_higiene(rdbEvaluacion_condicion_higiene
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setInstitucion_realizaron_controles(tbxInstitucion_realizaron_controles
						.getValue());
		ficha_epidemiologia_n2.setDiabetes(rdbDiabetes.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n2.setAmenaza_aborto(rdbAmenaza_aborto
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setOtra_complicacion(rdbOtra_complicacion
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setCual(tbxCual.getValue());
		ficha_epidemiologia_n2
				.setRecibio_realizo_tratamiento(rdbRecibio_realizo_tratamiento
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setDificultad_respirar(rdbDificultad_respirar
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setDificultad_alimentarse(rdbDificultad_alimentarse
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setReflujo_gastroesofagico(rdbReflujo_gastroesofagico
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setVomito(rdbVomito.getSelectedItem().getValue()
				.toString());
		ficha_epidemiologia_n2.setDiarrea(rdbDiarrea.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n2
				.setRecibio_realizo_tratamiento_recien_nacido(rdbRecibio_realizo_tratamiento_recien_nacido
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setRecibio_vacunacion_bcg(rdbRecibio_vacunacion_bcg
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setPeso_gramo((ibxPeso_gramo.getValue() != null ? ibxPeso_gramo
						.getValue().toString() : "0"));
		ficha_epidemiologia_n2.setTalla_centimetro((dbxTalla_centimetro
				.getValue() != null ? dbxTalla_centimetro.getValue().toString()
				: "0.0"));
		ficha_epidemiologia_n2.setPerimetro_cefalico((dbxPerimetro_cefalico
				.getValue() != null ? dbxPerimetro_cefalico.getValue()
				.toString() : "0.0"));
		ficha_epidemiologia_n2.setEstado_actual(rdbEstado_actual
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setRecibe_lactancia_materna(rdbRecibe_lactancia_materna
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setLibre_demanda(rdbLibre_demanda
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setEn_la_noche(rdbEn_la_noche.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n2
				.setAlimento_diferente_lactancia(rdbAlimento_diferente_lactancia
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setRecibe_leche_formula(rdbRecibe_leche_formula
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setNumero_tomas((ibxNumero_tomas.getValue() != null ? ibxNumero_tomas
						.getValue() : 0));
		ficha_epidemiologia_n2
				.setDificultades_lactancia_materna(rdbDificultades_lactancia_materna
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setPezon_fisurado(rdbPezon_fisurado
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setBaja_produccion_libre(rdbBaja_produccion_libre
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setCongestion_mamaria(rdbCongestion_mamaria
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setMastitis(rdbMastitis.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n2
				.setOtra_complicacion_alimentacion_rn(rdbOtra_complicacion_alimentacion_rn
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setCual_alimentacion_recibido(tbxCual_alimentacion_recibido
						.getValue());
		ficha_epidemiologia_n2
				.setRecibio_capacitacion_lactancia_materna(rdbRecibio_capacitacion_lactancia_materna
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setQuedaron_sin_alimentos_falta_dinero(rdbQuedaron_sin_alimentos_falta_dinero
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setQuedaron_sin_comer_falta_dinero(rdbQuedaron_sin_comer_falta_dinero
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setRecibio_alimentos_institucion(rdbRecibio_alimentos_institucion
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setRealiza_educacion_alimentaria(rdbRealiza_educacion_alimentaria
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setRemite_servicio_salud(rdbRemite_servicio_salud
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2
				.setCanaliza_programas_apoyo_alimentario(rdbCanaliza_programas_apoyo_alimentario
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setFecha_visita_control(new Timestamp(
				dtbxFecha_visita_control.getValue().getTime()));
		ficha_epidemiologia_n2
				.setPeso_grmos_visita_seguimiento((ibxPeso_grmos_visita_seguimiento
						.getValue() != null ? ibxPeso_grmos_visita_seguimiento
						.getValue() : 0));
		ficha_epidemiologia_n2
				.setTalla_visita_seguimiento((dbxTalla_visita_seguimiento
						.getValue() != null ? dbxTalla_visita_seguimiento
						.getValue().toString() : "0.00"));
		ficha_epidemiologia_n2
				.setPerimetro_cefalico_visita_seguimiento((dbxPerimetro_cefalico_visita_seguimiento
						.getValue() != null ? dbxPerimetro_cefalico_visita_seguimiento
						.getValue().toString() : "0.0"));
		ficha_epidemiologia_n2
				.setEstado_actual_visita_seguimiento(rdbEstado_actual_visita_seguimiento
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n2.setNombre_profesional(tbxNombre_profesional
				.getValue());
		ficha_epidemiologia_n2.setCargo(tbxCargo.getValue());
		ficha_epidemiologia_n2
				.setInformacion_complementaria(tbxInformacion_complementaria
						.getValue());

		return ficha_epidemiologia_n2;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n2 obj) throws Exception {
		Ficha_epidemiologia_n2 ficha_epidemiologia_n2 = (Ficha_epidemiologia_n2) obj;
		try {
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n2.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n2
					.getIdentificacion());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n2
					.getCodigo_diagnostico());

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			tbxPrimer_nombre_madre.setValue(ficha_epidemiologia_n2
					.getPrimer_nombre_madre());
			tbxSegundo_nombre_madre.setValue(ficha_epidemiologia_n2
					.getSegundo_nombre_madre());
			tbxPrimer_apellido_madre.setValue(ficha_epidemiologia_n2
					.getPrimer_apellido_madre());
			tbxSegundo_apellido_madre.setValue(ficha_epidemiologia_n2
					.getSegundo_apellido_madre());
			for (int i = 0; i < lbxTipo_identidad.getItemCount(); i++) {
				Listitem listitem = lbxTipo_identidad.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n2.getTipo_identidad())) {
					listitem.setSelected(true);
					i = lbxTipo_identidad.getItemCount();
				}
			}
			tbxId_madre.setValue(ficha_epidemiologia_n2.getId_madre());
			tbxPrimer_nombre_rnacido.setValue(ficha_epidemiologia_n2
					.getPrimer_nombre_rnacido());
			tbxSegundo_nombre_rnacido.setValue(ficha_epidemiologia_n2
					.getSegundo_nombre_rnacido());
			tbxPrimer_apellido_rnacido.setValue(ficha_epidemiologia_n2
					.getPrimer_apellido_rnacido());
			tbxSegundo_apellido_rnacido.setValue(ficha_epidemiologia_n2
					.getSegundo_apellido_rnacido());
			for (int i = 0; i < lbxTipo_documento_rnacido.getItemCount(); i++) {
				Listitem listitem = lbxTipo_documento_rnacido.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(ficha_epidemiologia_n2
								.getTipo_documento_rnacido())) {
					listitem.setSelected(true);
					i = lbxTipo_documento_rnacido.getItemCount();
				}
			}
			ibxNumero_identificacion_rnacido.setValue(Integer
					.valueOf(ficha_epidemiologia_n2
							.getNumero_identificacion_rnacido()));
			dtbxFecha_rnacimiento.setValue(ficha_epidemiologia_n2
					.getFecha_rnacimiento());
			ibxEdad_rnacido.setValue(Integer.valueOf(ficha_epidemiologia_n2
					.getEdad_rnacido()));
			Utilidades.seleccionarRadio(rdbSexo_rnacido,
					ficha_epidemiologia_n2.getSexo_rnacido());
			ibxPeso_al_nacer.setValue(Integer.valueOf(ficha_epidemiologia_n2
					.getPeso_al_nacer()));
			ibxTalla_al_nacer.setValue(Integer.valueOf(ficha_epidemiologia_n2
					.getTalla_al_nacer()));
			dbxPerimetro_cefalico_rn
					.setValue(Double.valueOf(ficha_epidemiologia_n2
							.getPerimetro_cefalico_rn()));
			ibxSemanas_gestacion_parto.setValue(Integer
					.valueOf(ficha_epidemiologia_n2
							.getSemanas_gestacion_parto()));
			Utilidades.seleccionarRadio(rdbClasificacion_peso,
					ficha_epidemiologia_n2.getClasificacion_peso());
			Utilidades.seleccionarRadio(rdbSitio_atencion_parto,
					ficha_epidemiologia_n2.getSitio_atencion_parto());
			Utilidades.seleccionarRadio(rdbMultiplicidad_embarazo,
					ficha_epidemiologia_n2.getMultiplicidad_embarazo());
			ibxEdad_gestacion_primer_control.setValue(Integer
					.valueOf(ficha_epidemiologia_n2
							.getEdad_gestacion_primer_control()));
			ibxNumero_control_prenatal.setValue(Integer
					.valueOf(ficha_epidemiologia_n2
							.getNumero_control_prenatal()));
			Utilidades.seleccionarRadio(rdbCalcio,
					ficha_epidemiologia_n2.getCalcio());
			Utilidades.seleccionarRadio(rdbHierro,
					ficha_epidemiologia_n2.getHierro());
			Utilidades.seleccionarRadio(rdbAc_folico,
					ficha_epidemiologia_n2.getAc_folico());
			Utilidades.seleccionarRadio(rdbSindrome_anemico_embarazo,
					ficha_epidemiologia_n2.getSindrome_anemico_embarazo());
			dbxUltimo_valor_emoglobina_madre.setValue(Double
					.valueOf(ficha_epidemiologia_n2
							.getUltimo_valor_emoglobina_madre()));
			Utilidades
					.seleccionarRadio(rdbTrimestre_gestacion_hemoglobina,
							ficha_epidemiologia_n2
									.getTrimestre_gestacion_hemoglobina());
			Utilidades.seleccionarRadio(rdbIntervalo_intergenesico_mn2anios,
					ficha_epidemiologia_n2
							.getIntervalo_intergenesico_mn2anios());
			ibxNumero_embarazos_previos.setValue(Integer
					.valueOf(ficha_epidemiologia_n2
							.getNumero_embarazos_previos()));
			ibxNumero_hijos_vivos.setValue(Integer
					.valueOf(ficha_epidemiologia_n2.getNumero_hijos_vivos()));
			Utilidades.seleccionarRadio(rdbHijo_bajo_peso_nacer,
					ficha_epidemiologia_n2.getHijo_bajo_peso_nacer());
			Utilidades.seleccionarRadio(rdbPresento_hipertension_embarazo,
					ficha_epidemiologia_n2.getPresento_hipertension_embarazo());
			Utilidades.seleccionarRadio(rdbAmenaza_parto_pretermino,
					ficha_epidemiologia_n2.getAmenaza_parto_pretermino());
			Utilidades.seleccionarRadio(rdbConsumio_bebidas_alcoholicas,
					ficha_epidemiologia_n2.getConsumio_bebidas_alcoholicas());
			Utilidades.seleccionarRadio(rdbEsfuerzo_fisico,
					ficha_epidemiologia_n2.getEsfuerzo_fisico());
			Utilidades.seleccionarRadio(rdbInfeccion_vias_urinarias,
					ficha_epidemiologia_n2.getInfeccion_vias_urinarias());
			Utilidades.seleccionarRadio(rdbSobrepeso_obesidad,
					ficha_epidemiologia_n2.getSobrepeso_obesidad());
			Utilidades.seleccionarRadio(rdbDesnutricion,
					ficha_epidemiologia_n2.getDesnutricion());
			dbxPeso_pregestacional.setValue(Double
					.valueOf(ficha_epidemiologia_n2.getPeso_pregestacional()));
			dbxTalla_madre.setValue(Double.valueOf(ficha_epidemiologia_n2
					.getTalla_madre()));
			dbxPeso_ganado_embarazo.setValue(Double
					.valueOf(ficha_epidemiologia_n2.getPeso_ganado_embarazo()));
			Utilidades.seleccionarRadio(rdbNivel_educativo_madre,
					ficha_epidemiologia_n2.getNivel_educativo_madre());
			Utilidades.seleccionarRadio(rdbEstado_socioeconomico,
					ficha_epidemiologia_n2.getEstado_socioeconomico());
			dtbxFecha_investigacion.setValue(ficha_epidemiologia_n2
					.getFecha_investigacion());
			Utilidades.seleccionarRadio(rdbEntidad_realiza_investigacion,
					ficha_epidemiologia_n2.getEntidad_realiza_investigacion());
			tbxNombre_realiza_investigacion.setValue(ficha_epidemiologia_n2
					.getNombre_realiza_investigacion());
			tbxPerfil_realiza_investigacion.setValue(ficha_epidemiologia_n2
					.getPerfil_realiza_investigacion());
			tbxPrimer_nombre_realiza_investigacion
					.setValue(ficha_epidemiologia_n2
							.getPrimer_nombre_realiza_investigacion());
			tbxSegundo_nombre_realiza_investigacion
					.setValue(ficha_epidemiologia_n2
							.getSegundo_nombre_realiza_investigacion());
			tbxPrimer_apellido_realiza_investigacion
					.setValue(ficha_epidemiologia_n2
							.getPrimer_apellido_realiza_investigacion());
			tbxSegundo_apellido_realiza_investigacion
					.setValue(ficha_epidemiologia_n2
							.getSegundo_apellido_realiza_investigacion());
			tbxParentesco_recien_nacido.setValue(ficha_epidemiologia_n2
					.getParentesco_recien_nacido());
			Utilidades.seleccionarRadio(rdbMenor_asegurado,
					ficha_epidemiologia_n2.getMenor_asegurado());
			Utilidades.seleccionarRadio(rdbEstado_civil_madre,
					ficha_epidemiologia_n2.getEstado_civil_madre());
			Utilidades.seleccionarRadio(rdbIngreso_mensual_hogar,
					ficha_epidemiologia_n2.getIngreso_mensual_hogar());
			Utilidades.seleccionarRadio(rdbHacinamiento,
					ficha_epidemiologia_n2.getHacinamiento());
			Utilidades.seleccionarRadio(rdbAgua_potable,
					ficha_epidemiologia_n2.getAgua_potable());
			Utilidades.seleccionarRadio(rdbLavado_mano_cuidador,
					ficha_epidemiologia_n2.getLavado_mano_cuidador());
			Utilidades.seleccionarRadio(rdbRecipientes_cocina_tapa_lavables,
					ficha_epidemiologia_n2
							.getRecipientes_cocina_tapa_lavables());
			Utilidades.seleccionarRadio(rdbSistema_eliminacion_excreta,
					ficha_epidemiologia_n2.getSistema_eliminacion_excreta());
			Utilidades.seleccionarRadio(rdbAlmacenamiento_alimentos,
					ficha_epidemiologia_n2.getAlmacenamiento_alimentos());
			Utilidades.seleccionarRadio(rdbConcervacion_alimentos,
					ficha_epidemiologia_n2.getConcervacion_alimentos());
			Utilidades.seleccionarRadio(rdbEvaluacion_condicion_higiene,
					ficha_epidemiologia_n2.getEvaluacion_condicion_higiene());
			tbxInstitucion_realizaron_controles.setValue(ficha_epidemiologia_n2
					.getInstitucion_realizaron_controles());
			Utilidades.seleccionarRadio(rdbDiabetes,
					ficha_epidemiologia_n2.getDiabetes());
			Utilidades.seleccionarRadio(rdbAmenaza_aborto,
					ficha_epidemiologia_n2.getAmenaza_aborto());
			Utilidades.seleccionarRadio(rdbOtra_complicacion,
					ficha_epidemiologia_n2.getOtra_complicacion());
			tbxCual.setValue(ficha_epidemiologia_n2.getCual());
			Utilidades.seleccionarRadio(rdbRecibio_realizo_tratamiento,
					ficha_epidemiologia_n2.getRecibio_realizo_tratamiento());
			Utilidades.seleccionarRadio(rdbDificultad_respirar,
					ficha_epidemiologia_n2.getDificultad_respirar());
			Utilidades.seleccionarRadio(rdbDificultad_alimentarse,
					ficha_epidemiologia_n2.getDificultad_alimentarse());
			Utilidades.seleccionarRadio(rdbReflujo_gastroesofagico,
					ficha_epidemiologia_n2.getReflujo_gastroesofagico());
			Utilidades.seleccionarRadio(rdbVomito,
					ficha_epidemiologia_n2.getVomito());
			Utilidades.seleccionarRadio(rdbDiarrea,
					ficha_epidemiologia_n2.getDiarrea());
			Utilidades.seleccionarRadio(
					rdbRecibio_realizo_tratamiento_recien_nacido,
					ficha_epidemiologia_n2
							.getRecibio_realizo_tratamiento_recien_nacido());
			Utilidades.seleccionarRadio(rdbRecibio_vacunacion_bcg,
					ficha_epidemiologia_n2.getRecibio_vacunacion_bcg());
			ibxPeso_gramo.setValue(Integer.valueOf(ficha_epidemiologia_n2
					.getPeso_gramo()));
			dbxTalla_centimetro.setValue(Integer.valueOf(ficha_epidemiologia_n2
					.getTalla_centimetro()));
			dbxPerimetro_cefalico.setValue(Integer
					.valueOf(ficha_epidemiologia_n2.getPerimetro_cefalico()));
			Utilidades.seleccionarRadio(rdbEstado_actual,
					ficha_epidemiologia_n2.getEstado_actual());
			Utilidades.seleccionarRadio(rdbRecibe_lactancia_materna,
					ficha_epidemiologia_n2.getRecibe_lactancia_materna());
			Utilidades.seleccionarRadio(rdbLibre_demanda,
					ficha_epidemiologia_n2.getLibre_demanda());
			Utilidades.seleccionarRadio(rdbEn_la_noche,
					ficha_epidemiologia_n2.getEn_la_noche());
			Utilidades.seleccionarRadio(rdbAlimento_diferente_lactancia,
					ficha_epidemiologia_n2.getAlimento_diferente_lactancia());
			Utilidades.seleccionarRadio(rdbRecibe_leche_formula,
					ficha_epidemiologia_n2.getRecibe_leche_formula());
			ibxNumero_tomas.setValue(ficha_epidemiologia_n2.getNumero_tomas());
			Utilidades.seleccionarRadio(rdbDificultades_lactancia_materna,
					ficha_epidemiologia_n2.getDificultades_lactancia_materna());
			Utilidades.seleccionarRadio(rdbPezon_fisurado,
					ficha_epidemiologia_n2.getPezon_fisurado());
			Utilidades.seleccionarRadio(rdbBaja_produccion_libre,
					ficha_epidemiologia_n2.getBaja_produccion_libre());
			Utilidades.seleccionarRadio(rdbCongestion_mamaria,
					ficha_epidemiologia_n2.getCongestion_mamaria());
			Utilidades.seleccionarRadio(rdbMastitis,
					ficha_epidemiologia_n2.getMastitis());
			Utilidades.seleccionarRadio(rdbOtra_complicacion_alimentacion_rn,
					ficha_epidemiologia_n2
							.getOtra_complicacion_alimentacion_rn());
			tbxCual_alimentacion_recibido.setValue(ficha_epidemiologia_n2
					.getCual_alimentacion_recibido());
			Utilidades.seleccionarRadio(
					rdbRecibio_capacitacion_lactancia_materna,
					ficha_epidemiologia_n2
							.getRecibio_capacitacion_lactancia_materna());
			Utilidades.seleccionarRadio(rdbQuedaron_sin_alimentos_falta_dinero,
					ficha_epidemiologia_n2
							.getQuedaron_sin_alimentos_falta_dinero());
			Utilidades
					.seleccionarRadio(rdbQuedaron_sin_comer_falta_dinero,
							ficha_epidemiologia_n2
									.getQuedaron_sin_comer_falta_dinero());
			Utilidades.seleccionarRadio(rdbRecibio_alimentos_institucion,
					ficha_epidemiologia_n2.getRecibio_alimentos_institucion());
			Utilidades.seleccionarRadio(rdbRealiza_educacion_alimentaria,
					ficha_epidemiologia_n2.getRealiza_educacion_alimentaria());
			Utilidades.seleccionarRadio(rdbRemite_servicio_salud,
					ficha_epidemiologia_n2.getRemite_servicio_salud());
			Utilidades.seleccionarRadio(
					rdbCanaliza_programas_apoyo_alimentario,
					ficha_epidemiologia_n2
							.getCanaliza_programas_apoyo_alimentario());
			dtbxFecha_visita_control.setValue(ficha_epidemiologia_n2
					.getFecha_visita_control());
			ibxPeso_grmos_visita_seguimiento.setValue(ficha_epidemiologia_n2
					.getPeso_grmos_visita_seguimiento());
			dbxTalla_visita_seguimiento.setValue(Double
					.valueOf(ficha_epidemiologia_n2
							.getTalla_visita_seguimiento()));
			dbxPerimetro_cefalico_visita_seguimiento.setValue(Double
					.valueOf(ficha_epidemiologia_n2
							.getPerimetro_cefalico_visita_seguimiento()));
			Utilidades.seleccionarRadio(rdbEstado_actual_visita_seguimiento,
					ficha_epidemiologia_n2
							.getEstado_actual_visita_seguimiento());
			tbxNombre_profesional.setValue(ficha_epidemiologia_n2
					.getNombre_profesional());
			tbxCargo.setValue(ficha_epidemiologia_n2.getCargo());
			tbxInformacion_complementaria.setValue(ficha_epidemiologia_n2
					.getInformacion_complementaria());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n2 ficha_epidemiologia_n2 = (Ficha_epidemiologia_n2) obj;
		try {
			int result = getServiceLocator().getFicha_epidemiologia_nnService()
					.eliminar(ficha_epidemiologia_n2);
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
	public Ficha_epidemiologia_n2 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n2> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n2.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n2 ficha_n2 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n2;
				}else{

					return null;
				}
	}

}