/*
 * ficha_epidemiologia_n32Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n32;
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

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n32Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n32>{

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n32Action.class);

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
	private Datebox dtbxFecha_ficha;
	@View
	private Radiogroup rdbTipo_de_tuberculosis;
	@View
	private Radiogroup rdbLocalizacion_de_la_tuberculosis;
	@View
	private Textbox tbxCual_otra_localizacion;
	@View
	private Radiogroup rdbSegun_antecedente_de_tratamiento;
	@View
	private Radiogroup rdbSegun_tipo_de_medicamento_recibido;
	@View
	private Radiogroup rdbSegun_condicion_de_ingreso;
	@View
	private Radiogroup rdbBacteriologico;
	@View
	private Radiogroup rdbClinico;
	@View
	private Radiogroup rdbNexo_epidemiologico;
	@View
	private Radiogroup rdbRadiologico;
	@View
	private Datebox dtbxFecha_de_confirmacion_del_caso;
	@View
	private Radiogroup rdbRealiza_bacilosopia;
	@View
	private Radiogroup rdbResultado_bk;
	@View
	private Datebox dtbxFecha_de_resultado;
	@View
	private Textbox tbxLaboratorio_que_realiza_baciloscopia;
	@View
	private Radiogroup rdbRealiza_cultivo;
	@View
	private Datebox dtbxFecha_de_siembra;
	@View
	private Datebox dtbxFecha_de_resultado_1;
	@View
	private Radiogroup rdbResultado_de_cultivo;
	@View
	private Radiogroup rdbEspecie_identificada;
	@View
	private Textbox tbxLaboratorio_que_realiza_cultivo;
	@View
	private Textbox tbxLaboratorio_que_realiza_identificacion;
	@View
	private Radiogroup rdbRealizo_prueba_de_sensibilidad_a_farmacos;
	@View
	private Textbox tbxLaboratorio_que_realiza_la_psf;
	@View
	private Datebox dtbxFecha_resultado_psf;
	@View
	private Radiogroup rdbResultado_prueba_de_sensibilidad_a_farmacos_psf;
	@View
	private Radiogroup rdbRealiza_psfde_primera;
	@View
	private Checkbox chbEstreptomicina;
	@View
	private Checkbox chbIsoniazida;
	@View
	private Checkbox chbRifampicina;
	@View
	private Checkbox chbEtambutol;
	@View
	private Checkbox chbPirazinamida;
	@View
	private Radiogroup rdbRealiza_psf_de_segunda_linea;
	@View
	private Checkbox chbEtionamida;
	@View
	private Checkbox chbLevofloxacina;
	@View
	private Checkbox chbAmikacina;
	@View
	private Checkbox chbOtro_medicamento_segunda_linea;
	@View
	private Checkbox chbCicloserina;
	@View
	private Checkbox chbMoxifloxacina;
	@View
	private Checkbox chbKanamicina;
	@View
	private Textbox tbxOtro_cual_resultado_psf_primera_linea;
	@View
	private Checkbox chbAcido_paramino_salicilico;
	@View
	private Checkbox chbOfloxacina;
	@View
	private Checkbox chbCapreomicina;
	@View
	private Radiogroup rdbClasificacion_de_caso_segun_tipo_de_resistencia;
	@View
	private Checkbox chbNitrato_reductasa;
	@View
	private Checkbox chbProporciones_lj;
	@View
	private Checkbox chbBactec_mgit_960;
	@View
	private Checkbox chbProporciones_en_agar;
	@View
	private Checkbox chbPrueba_molecular;
	@View
	private Textbox tbxNombre_de_la_prueba_molecular;
	@View
	private Radiogroup rdbFactores_de_riesgo;
	@View
	private Checkbox chbContacto_con_paciente_farmacorresistente;
	@View
	private Checkbox chbFarmacodependencia;
	@View
	private Checkbox chbHabitante_de_calle;
	@View
	private Checkbox chbOtros_factores_inmunosupresores;
	@View
	private Checkbox chbTratamiento_irregular_por_mas_de_un_mes;
	@View
	private Checkbox chbHa_vivido_en_areas_de_alta_carga_de_tb_farmacorresistente;
	@View
	private Checkbox chbTratamiento_con_menos_de_tres_medicamentos;
	@View
	private Checkbox chbOtro_factor_risgo;
	@View
	private Textbox tbxCual_otro_factor_riesgo;
	@View
	private Radiogroup rdbCoomorbilidades;
	@View
	private Checkbox chbDiabetes;
	@View
	private Checkbox chbSilicosis;
	@View
	private Checkbox chbEfermedad_renal;
	@View
	private Checkbox chbEpoc;
	@View
	private Checkbox chbEnfermedad_hepatica;
	@View
	private Checkbox chbCancer;
	@View
	private Checkbox chbArtritis_rematoide;
	@View
	private Checkbox chbDesnutricion;
	@View
	private Checkbox chbVhi_sida;
	@View
	private Checkbox chbOtra_coomorbilidades;
	@View
	private Textbox tbxCual_otra_coomorbilidades;
	@View
	private Row row_1;
	@View
	private Row row_2;
	@View
	private Row row_3;
	@View
	private Groupbox groupbox_1;
	@View
	private Groupbox groupbox_2;
	@View
	private Groupbox groupbox_3;
	@View
	private Groupbox groupbox_4;
	
	@View private Textbox tbx_nombres;
	@View private Textbox tbx_tipo_identificacion;
	@View private Textbox tbx_nro_identificacion;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;

	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n32 ficha = new Ficha_epidemiologia_n32();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n32) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 32-------> "+ficha);
			
			mostrarDatos(ficha);
		}
	}

	public void listarCombos() {
		listarParameter();
	}
	
	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
		}
	}
	
	public void mostrar_groupbox_al_mostrar_datos(Radiogroup rg){
		if (rg.getSelectedItem().getValue().equals("1")){
			groupbox_1.setVisible(true);
			groupbox_2.setVisible(true);
			
		}else{
			groupbox_1.setVisible(false);
			groupbox_2.setVisible(false);
		
		}
		
		}
	
	public void mostrarFila_1(Radiogroup rg) {
		if (rg.getSelectedItem().getValue().equals("12")) {
			row_1.setVisible(true);
		} else {
			row_1.setVisible(false);
			tbxCual_otra_localizacion.setText("");
		}
	}

	public void mostrar_texto_otro_psf_2da_linea() {
		if (chbOtro_medicamento_segunda_linea.isChecked()) {
			tbxOtro_cual_resultado_psf_primera_linea.setDisabled(false);
		} else {
			tbxOtro_cual_resultado_psf_primera_linea.setDisabled(true);
			tbxOtro_cual_resultado_psf_primera_linea.setText("");
		}
	}

	public void mostrar_texto_prueba_molecular() {
		if (chbPrueba_molecular.isChecked()) {
			tbxNombre_de_la_prueba_molecular.setDisabled(false);
		} else {
			tbxNombre_de_la_prueba_molecular.setDisabled(true);
			tbxNombre_de_la_prueba_molecular.setText("");
		}
	}

	public void mostrar_fila_otro_riesgo() {
		if (chbOtro_factor_risgo.isChecked()) {
			row_2.setVisible(true);

		} else {
			row_2.setVisible(false);
			tbxCual_otro_factor_riesgo.setText("");
		}
	}

	public void mostrar_fila_otras_coomorbilidades() {
		if (chbOtra_coomorbilidades.isChecked()) {
			row_3.setVisible(true);

		} else {
			row_3.setVisible(false);
			tbxCual_otra_coomorbilidades.setText("");
		}
	}

	public void mostrar_groupbox(Radiogroup rg) {
		if (rg.getSelectedItem().getValue().equals("1")) {
			groupbox_1.setVisible(true);
		} else {
			groupbox_1.setVisible(false);
		}
	}
	public void mostrar_groupbox_1(Radiogroup rg) {
		if (rg.getSelectedItem().getValue().equals("1")) {
			groupbox_2.setVisible(true);
		} else {
			groupbox_2.setVisible(false);
			chbOtro_medicamento_segunda_linea.setChecked(false);
			tbxOtro_cual_resultado_psf_primera_linea.setDisabled(true);
			tbxOtro_cual_resultado_psf_primera_linea.setText("");
		}
	}
	public void mostrar_groupbox_2(Radiogroup rg) {
		if (rg.getSelectedItem().getValue().equals("1")) {
			groupbox_3.setVisible(true);
		} else {
			groupbox_3.setVisible(false);
			row_2.setVisible(false);
			chbOtros_factores_inmunosupresores.setChecked(false);

		}
	}

	public void mostrar_groupbox_3(Radiogroup rg) {
		if (rg.getSelectedItem().getValue().equals("1")) {
			groupbox_4.setVisible(true);
		} else {
			groupbox_4.setVisible(false);
			row_3.setVisible(false);
			chbOtra_coomorbilidades.setChecked(false);
		}
	}
	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("fecha_ficha");
		listitem.setLabel("Fecha_ficha");
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

		dtbxFecha_ficha.setStyle("background-color:white");
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:white");
		tbx_nro_identificacion.setStyle("background-color:white");

		boolean valida = true;

		if (dtbxFecha_ficha.getText().trim().equals("")) {
			dtbxFecha_ficha.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		// if(tbxNombres_y_apellidos.getText().trim().equals("")){
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		// valida = false;
		// }
		if (tbx_nro_identificacion.getText().trim().equals("")) {
			tbx_nro_identificacion.setStyle("background-color:#F6BBBE");
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

			List<Ficha_epidemiologia_n32> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n32.class, parameters);

			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n32 ficha_epidemiologia_n32 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n32,
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

		final Ficha_epidemiologia_n32 ficha_epidemiologia_n32 = (Ficha_epidemiologia_n32) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n32.getCodigo_ficha()
				+ ""));
		fila.appendChild(new Label(ficha_epidemiologia_n32.getFecha_ficha()
				+ ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n32);
						
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
									eliminarDatos(ficha_epidemiologia_n32);
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
	public Ficha_epidemiologia_n32 obtenerFichaEpidemiologia() {

				// Cargamos los componentes //

				Ficha_epidemiologia_n32 ficha_epidemiologia_n32 = new Ficha_epidemiologia_n32();
				ficha_epidemiologia_n32.setCodigo_ficha(tbxCodigo_ficha
						.getValue());
				ficha_epidemiologia_n32.setFecha_ficha(new Timestamp(
						dtbxFecha_ficha.getValue().getTime()));
				ficha_epidemiologia_n32.setCodigo_empresa(empresa
						.getCodigo_empresa());
				ficha_epidemiologia_n32.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n32.setCodigo_diagnostico("Z000");
				
				
				ficha_epidemiologia_n32.setNro_identificacion(admision != null ? admision
						.getNro_identificacion() : null);
			
				// ficha_epidemiologia_n32.setNro_identificacion();
				// ficha_epidemiologia_n32.setCodigo();
				ficha_epidemiologia_n32
						.setTipo_de_tuberculosis(rdbTipo_de_tuberculosis
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setLocalizacion_de_la_tuberculosis(rdbLocalizacion_de_la_tuberculosis
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setCual_otra_localizacion(tbxCual_otra_localizacion
								.getValue());
				ficha_epidemiologia_n32
						.setSegun_antecedente_de_tratamiento(rdbSegun_antecedente_de_tratamiento
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setSegun_tipo_de_medicamento_recibido(rdbSegun_tipo_de_medicamento_recibido
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setSegun_condicion_de_ingreso(rdbSegun_condicion_de_ingreso
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32.setBacteriologico(rdbBacteriologico
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32.setClinico(rdbClinico.getSelectedItem()
						.getValue().toString());
				ficha_epidemiologia_n32
						.setNexo_epidemiologico(rdbNexo_epidemiologico
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32.setRadiologico(rdbRadiologico
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setFecha_de_confirmacion_del_caso(new Timestamp(
								dtbxFecha_de_confirmacion_del_caso.getValue()
										.getTime()));
				ficha_epidemiologia_n32
						.setRealiza_bacilosopia(rdbRealiza_bacilosopia
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32.setResultado_bk(rdbResultado_bk
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32.setFecha_de_resultado(new Timestamp(
						dtbxFecha_de_resultado.getValue().getTime()));
				ficha_epidemiologia_n32
						.setLaboratorio_que_realiza_baciloscopia(tbxLaboratorio_que_realiza_baciloscopia
								.getValue());
				ficha_epidemiologia_n32.setRealiza_cultivo(rdbRealiza_cultivo
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32.setFecha_de_siembra(new Timestamp(
						dtbxFecha_de_siembra.getValue().getTime()));
				ficha_epidemiologia_n32.setFecha_de_resultado_1(new Timestamp(
						dtbxFecha_de_resultado_1.getValue().getTime()));
				ficha_epidemiologia_n32
						.setResultado_de_cultivo(rdbResultado_de_cultivo
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setEspecie_identificada(rdbEspecie_identificada
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setLaboratorio_que_realiza_cultivo(tbxLaboratorio_que_realiza_cultivo
								.getValue());
				ficha_epidemiologia_n32
						.setLaboratorio_que_realiza_identificacion(tbxLaboratorio_que_realiza_identificacion
								.getValue());
				ficha_epidemiologia_n32
						.setRealizo_prueba_de_sensibilidad_a_farmacos(rdbRealizo_prueba_de_sensibilidad_a_farmacos
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setLaboratorio_que_realiza_la_psf(tbxLaboratorio_que_realiza_la_psf
								.getValue());
				ficha_epidemiologia_n32.setFecha_resultado_psf(new Timestamp(
						dtbxFecha_resultado_psf.getValue().getTime()));
				ficha_epidemiologia_n32
						.setResultado_prueba_de_sensibilidad_a_farmacos_psf(rdbResultado_prueba_de_sensibilidad_a_farmacos_psf
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setRealiza_psfde_primera(rdbRealiza_psfde_primera
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32.setEstreptomicina(chbEstreptomicina
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setIsoniazida(chbIsoniazida.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setRifampicina(chbRifampicina
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setEtambutol(chbEtambutol.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setPirazinamida(chbPirazinamida
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setRealiza_psf_de_segunda_linea(rdbRealiza_psf_de_segunda_linea
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setEtionamida(chbEtionamida.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setLevofloxacina(chbLevofloxacina
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setAmikacina(chbAmikacina.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setOtro_medicamento_segunda_linea(chbOtro_medicamento_segunda_linea
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setCicloserina(chbCicloserina
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setMoxifloxacina(chbMoxifloxacina
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setKanamicina(chbKanamicina.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setOtro_cual_resultado_psf_primera_linea(tbxOtro_cual_resultado_psf_primera_linea
								.getValue());
				ficha_epidemiologia_n32
						.setAcido_paramino_salicilico(chbAcido_paramino_salicilico
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setOfloxacina(chbOfloxacina.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setCapreomicina(chbCapreomicina
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setClasificacion_de_caso_segun_tipo_de_resistencia(rdbClasificacion_de_caso_segun_tipo_de_resistencia
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setNitrato_reductasa(chbNitrato_reductasa.isChecked() ? "S"
								: "N");
				ficha_epidemiologia_n32.setProporciones_lj(chbProporciones_lj
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setBactec_mgit_960(chbBactec_mgit_960
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setProporciones_en_agar(chbProporciones_en_agar
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setPrueba_molecular(chbPrueba_molecular
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setNombre_de_la_prueba_molecular(tbxNombre_de_la_prueba_molecular
								.getValue());
				ficha_epidemiologia_n32
						.setFactores_de_riesgo(rdbFactores_de_riesgo
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setContacto_con_paciente_farmacorresistente(chbContacto_con_paciente_farmacorresistente
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setFarmacodependencia(chbFarmacodependencia
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setHabitante_de_calle(chbHabitante_de_calle
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setOtros_factores_inmunosupresores(chbOtros_factores_inmunosupresores
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setTratamiento_irregular_por_mas_de_un_mes(chbTratamiento_irregular_por_mas_de_un_mes
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setHa_vivido_en_areas_de_alta_carga_de_tb_farmacorresistente(chbHa_vivido_en_areas_de_alta_carga_de_tb_farmacorresistente
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setTratamiento_con_menos_de_tres_medicamentos(chbTratamiento_con_menos_de_tres_medicamentos
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setOtro_factor_risgo(chbOtro_factor_risgo.isChecked() ? "S"
								: "N");
				ficha_epidemiologia_n32
						.setCual_otro_factor_riesgo(tbxCual_otro_factor_riesgo
								.getValue());
				ficha_epidemiologia_n32.setCoomorbilidades(rdbCoomorbilidades
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n32
						.setDiabetes(chbDiabetes.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setSilicosis(chbSilicosis.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setEfermedad_renal(chbEfermedad_renal
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setEpoc(chbEpoc.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setEnfermedad_hepatica(chbEnfermedad_hepatica
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setCancer(chbCancer.isChecked() ? "S"
						: "N");
				ficha_epidemiologia_n32
						.setArtritis_rematoide(chbArtritis_rematoide
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32.setDesnutricion(chbDesnutricion
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setVhi_sida(chbVhi_sida.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setOtra_coomorbilidades(chbOtra_coomorbilidades
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n32
						.setCual_otra_coomorbilidades(tbxCual_otra_coomorbilidades
								.getValue());
				ficha_epidemiologia_n32.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n32.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				// ficha_epidemiologia_n32.setDelete_date();
				ficha_epidemiologia_n32.setUltimo_user(usuarios.getCodigo()
						.toString());
				// ficha_epidemiologia_n32.setDelete_use();
				ficha_epidemiologia_n32.setCreacion_user(usuarios.getCodigo()
				
						.toString());
				
				return ficha_epidemiologia_n32;

					}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n32 obj) throws Exception {
		Ficha_epidemiologia_n32 ficha_epidemiologia_n32 = (Ficha_epidemiologia_n32) obj;
		try {
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n32.getCodigo_ficha());
			dtbxFecha_ficha.setValue(ficha_epidemiologia_n32.getFecha_ficha());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbTipo_de_tuberculosis,
					ficha_epidemiologia_n32.getTipo_de_tuberculosis());
			Utilidades.seleccionarRadio(rdbLocalizacion_de_la_tuberculosis,
					ficha_epidemiologia_n32
							.getLocalizacion_de_la_tuberculosis());
			tbxCual_otra_localizacion.setValue(ficha_epidemiologia_n32
					.getCual_otra_localizacion());
			Utilidades.seleccionarRadio(rdbSegun_antecedente_de_tratamiento,
					ficha_epidemiologia_n32
							.getSegun_antecedente_de_tratamiento());
			Utilidades.seleccionarRadio(rdbSegun_tipo_de_medicamento_recibido,
					ficha_epidemiologia_n32
							.getSegun_tipo_de_medicamento_recibido());
			Utilidades.seleccionarRadio(rdbSegun_condicion_de_ingreso,
					ficha_epidemiologia_n32.getSegun_condicion_de_ingreso());
			Utilidades.seleccionarRadio(rdbBacteriologico,
					ficha_epidemiologia_n32.getBacteriologico());
			Utilidades.seleccionarRadio(rdbClinico,
					ficha_epidemiologia_n32.getClinico());
			Utilidades.seleccionarRadio(rdbNexo_epidemiologico,
					ficha_epidemiologia_n32.getNexo_epidemiologico());
			Utilidades.seleccionarRadio(rdbRadiologico,
					ficha_epidemiologia_n32.getRadiologico());
			dtbxFecha_de_confirmacion_del_caso.setValue(ficha_epidemiologia_n32
					.getFecha_de_confirmacion_del_caso());
			Utilidades.seleccionarRadio(rdbRealiza_bacilosopia,
					ficha_epidemiologia_n32.getRealiza_bacilosopia());
			Utilidades.seleccionarRadio(rdbResultado_bk,
					ficha_epidemiologia_n32.getResultado_bk());
			dtbxFecha_de_resultado.setValue(ficha_epidemiologia_n32
					.getFecha_de_resultado());
			tbxLaboratorio_que_realiza_baciloscopia
					.setValue(ficha_epidemiologia_n32
							.getLaboratorio_que_realiza_baciloscopia());
			Utilidades.seleccionarRadio(rdbRealiza_cultivo,
					ficha_epidemiologia_n32.getRealiza_cultivo());
			dtbxFecha_de_siembra.setValue(ficha_epidemiologia_n32
					.getFecha_de_siembra());
			dtbxFecha_de_resultado_1.setValue(ficha_epidemiologia_n32
					.getFecha_de_resultado_1());
			Utilidades.seleccionarRadio(rdbResultado_de_cultivo,
					ficha_epidemiologia_n32.getResultado_de_cultivo());
			Utilidades.seleccionarRadio(rdbEspecie_identificada,
					ficha_epidemiologia_n32.getEspecie_identificada());
			tbxLaboratorio_que_realiza_cultivo.setValue(ficha_epidemiologia_n32
					.getLaboratorio_que_realiza_cultivo());
			tbxLaboratorio_que_realiza_identificacion
					.setValue(ficha_epidemiologia_n32
							.getLaboratorio_que_realiza_identificacion());
			Utilidades.seleccionarRadio(
					rdbRealizo_prueba_de_sensibilidad_a_farmacos,
					ficha_epidemiologia_n32
							.getRealizo_prueba_de_sensibilidad_a_farmacos());
			tbxLaboratorio_que_realiza_la_psf.setValue(ficha_epidemiologia_n32
					.getLaboratorio_que_realiza_la_psf());
			dtbxFecha_resultado_psf.setValue(ficha_epidemiologia_n32
					.getFecha_resultado_psf());
			Utilidades
					.seleccionarRadio(
							rdbResultado_prueba_de_sensibilidad_a_farmacos_psf,
							ficha_epidemiologia_n32
									.getResultado_prueba_de_sensibilidad_a_farmacos_psf());
			Utilidades.seleccionarRadio(rdbRealiza_psfde_primera,
					ficha_epidemiologia_n32.getRealiza_psfde_primera());
			chbEstreptomicina.setChecked(ficha_epidemiologia_n32
					.getEstreptomicina().equals("S") ? true : false);
			chbIsoniazida.setChecked(ficha_epidemiologia_n32.getIsoniazida()
					.equals("S") ? true : false);
			chbRifampicina.setChecked(ficha_epidemiologia_n32.getRifampicina()
					.equals("S") ? true : false);
			chbEtambutol.setChecked(ficha_epidemiologia_n32.getEtambutol()
					.equals("S") ? true : false);
			chbPirazinamida.setChecked(ficha_epidemiologia_n32
					.getPirazinamida().equals("S") ? true : false);
			Utilidades.seleccionarRadio(rdbRealiza_psf_de_segunda_linea,
					ficha_epidemiologia_n32.getRealiza_psf_de_segunda_linea());
			chbEtionamida.setChecked(ficha_epidemiologia_n32.getEtionamida()
					.equals("S") ? true : false);
			chbLevofloxacina.setChecked(ficha_epidemiologia_n32
					.getLevofloxacina().equals("S") ? true : false);
			chbAmikacina.setChecked(ficha_epidemiologia_n32.getAmikacina()
					.equals("S") ? true : false);
			chbOtro_medicamento_segunda_linea
					.setChecked(ficha_epidemiologia_n32
							.getOtro_medicamento_segunda_linea().equals("S") ? true
							: false);
			chbCicloserina.setChecked(ficha_epidemiologia_n32.getCicloserina()
					.equals("S") ? true : false);
			chbMoxifloxacina.setChecked(ficha_epidemiologia_n32
					.getMoxifloxacina().equals("S") ? true : false);
			chbKanamicina.setChecked(ficha_epidemiologia_n32.getKanamicina()
					.equals("S") ? true : false);
			tbxOtro_cual_resultado_psf_primera_linea
					.setValue(ficha_epidemiologia_n32
							.getOtro_cual_resultado_psf_primera_linea());
			chbAcido_paramino_salicilico.setChecked(ficha_epidemiologia_n32
					.getAcido_paramino_salicilico().equals("S") ? true : false);
			chbOfloxacina.setChecked(ficha_epidemiologia_n32.getOfloxacina()
					.equals("S") ? true : false);
			chbCapreomicina.setChecked(ficha_epidemiologia_n32
					.getCapreomicina().equals("S") ? true : false);
			Utilidades
					.seleccionarRadio(
							rdbClasificacion_de_caso_segun_tipo_de_resistencia,
							ficha_epidemiologia_n32
									.getClasificacion_de_caso_segun_tipo_de_resistencia());
			chbNitrato_reductasa.setChecked(ficha_epidemiologia_n32
					.getNitrato_reductasa().equals("S") ? true : false);
			chbProporciones_lj.setChecked(ficha_epidemiologia_n32
					.getProporciones_lj().equals("S") ? true : false);
			chbBactec_mgit_960.setChecked(ficha_epidemiologia_n32
					.getBactec_mgit_960().equals("S") ? true : false);
			chbProporciones_en_agar.setChecked(ficha_epidemiologia_n32
					.getProporciones_en_agar().equals("S") ? true : false);
			chbPrueba_molecular.setChecked(ficha_epidemiologia_n32
					.getPrueba_molecular().equals("S") ? true : false);
			tbxNombre_de_la_prueba_molecular.setValue(ficha_epidemiologia_n32
					.getNombre_de_la_prueba_molecular());
			Utilidades.seleccionarRadio(rdbFactores_de_riesgo,
					ficha_epidemiologia_n32.getFactores_de_riesgo());
			chbContacto_con_paciente_farmacorresistente
					.setChecked(ficha_epidemiologia_n32
							.getContacto_con_paciente_farmacorresistente()
							.equals("S") ? true : false);
			chbFarmacodependencia.setChecked(ficha_epidemiologia_n32
					.getFarmacodependencia().equals("S") ? true : false);
			chbHabitante_de_calle.setChecked(ficha_epidemiologia_n32
					.getHabitante_de_calle().equals("S") ? true : false);
			chbOtros_factores_inmunosupresores
					.setChecked(ficha_epidemiologia_n32
							.getOtros_factores_inmunosupresores().equals("S") ? true
							: false);
			chbTratamiento_irregular_por_mas_de_un_mes
					.setChecked(ficha_epidemiologia_n32
							.getTratamiento_irregular_por_mas_de_un_mes()
							.equals("S") ? true : false);
			chbHa_vivido_en_areas_de_alta_carga_de_tb_farmacorresistente
					.setChecked(ficha_epidemiologia_n32
							.getHa_vivido_en_areas_de_alta_carga_de_tb_farmacorresistente()
							.equals("S") ? true : false);
			chbTratamiento_con_menos_de_tres_medicamentos
					.setChecked(ficha_epidemiologia_n32
							.getTratamiento_con_menos_de_tres_medicamentos()
							.equals("S") ? true : false);
			chbOtro_factor_risgo.setChecked(ficha_epidemiologia_n32
					.getOtro_factor_risgo().equals("S") ? true : false);
			tbxCual_otro_factor_riesgo.setValue(ficha_epidemiologia_n32
					.getCual_otro_factor_riesgo());
			Utilidades.seleccionarRadio(rdbCoomorbilidades,
					ficha_epidemiologia_n32.getCoomorbilidades());
			chbDiabetes.setChecked(ficha_epidemiologia_n32.getDiabetes()
					.equals("S") ? true : false);
			chbSilicosis.setChecked(ficha_epidemiologia_n32.getSilicosis()
					.equals("S") ? true : false);
			chbEfermedad_renal.setChecked(ficha_epidemiologia_n32
					.getEfermedad_renal().equals("S") ? true : false);
			chbEpoc.setChecked(ficha_epidemiologia_n32.getEpoc().equals("S") ? true
					: false);
			chbEnfermedad_hepatica.setChecked(ficha_epidemiologia_n32
					.getEnfermedad_hepatica().equals("S") ? true : false);
			chbCancer.setChecked(ficha_epidemiologia_n32.getCancer()
					.equals("S") ? true : false);
			chbArtritis_rematoide.setChecked(ficha_epidemiologia_n32
					.getArtritis_rematoide().equals("S") ? true : false);
			chbDesnutricion.setChecked(ficha_epidemiologia_n32
					.getDesnutricion().equals("S") ? true : false);
			chbVhi_sida.setChecked(ficha_epidemiologia_n32.getVhi_sida()
					.equals("S") ? true : false);
			chbOtra_coomorbilidades.setChecked(ficha_epidemiologia_n32
					.getOtra_coomorbilidades().equals("S") ? true : false);
			tbxCual_otra_coomorbilidades.setValue(ficha_epidemiologia_n32
					.getCual_otra_coomorbilidades());
			
			if(rdbRealiza_psfde_primera.getSelectedItem().getValue().equals("1")){
				groupbox_1.setVisible(true);
				
			}else{
				groupbox_1.setVisible(false);
			}
			if(rdbRealiza_psf_de_segunda_linea.getSelectedItem().getValue().equals("1")){
				groupbox_2.setVisible(true);
			}else{
				groupbox_2.setVisible(false);
			}
			if(rdbFactores_de_riesgo.getSelectedItem().getValue().equals("1")){
				groupbox_3.setVisible(true);
			}else{
				groupbox_3.setVisible(false);
			}
			if(rdbCoomorbilidades.getSelectedItem().getValue().equals("1")){
				groupbox_4.setVisible(true);
			}else{
				groupbox_4.setVisible(false);
			}
			

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n32 ficha_epidemiologia_n32 = (Ficha_epidemiologia_n32) obj;
		try {
			int result = getServiceLocator()
					.getFicha_epidemiologia_nnService().eliminar(
							ficha_epidemiologia_n32);
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
	
	public void obtenerAdmision(Admision admision){
		Paciente paciente = admision.getPaciente();
		tbx_nro_identificacion.setValue(admision.getNro_identificacion());
		tbx_nombres.setValue(paciente.getNombreCompleto());
		tbx_tipo_identificacion.setValue(paciente.getTipo_identificacion());
		
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
	public Ficha_epidemiologia_n32 consultarDatos(Map<String, Object> map,
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
				parameters.put("nro_identificacion", admision.getNro_identificacion());
				
				if(impresion_diagnostica != null){
				parameters.put("codigo_historia", impresion_diagnostica.getCodigo_historia());
				}else{
					return null;
				}
				
				if(cie_epidemiologia != null){
					parameters.put("codigo", cie_epidemiologia.getCodigo_cie());			
				}else{
					return null;
				}
				
				getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
				
				List<Ficha_epidemiologia_n32> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n32.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n32 ficha_n32 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n32;
				}else{

					return null;
				}
	}
}