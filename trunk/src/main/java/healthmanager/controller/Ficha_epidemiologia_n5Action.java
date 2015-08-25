/*
 * ficha_epidemiologia_n5Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n5;
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

public class Ficha_epidemiologia_n5Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n5> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n5Action.class);

	// Componentes //
	
	@View
	private Textbox tbxCodigo_ficha;
	@View
	private Datebox dtbxFecha_inicial;
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
	private Listbox lbxVacuna_1;
	@View
	private Listbox lbxDosis_1;
	@View
	private Listbox lbxVia_1;
	@View
	private Listbox lbxSitio_1;
	@View
	private Datebox dtbxFecha_administracion_1;
	@View
	private Textbox tbxFabricante_1;
	@View
	private Textbox tbxLote_1;
	@View
	private Listbox lbxVacuna_2;
	@View
	private Listbox lbxDosis_2;
	@View
	private Listbox lbxVia_2;
	@View
	private Listbox lbxSitio_2;
	@View
	private Datebox dtbxFecha_administracion_2;
	@View
	private Textbox tbxFabricante_2;
	@View
	private Textbox tbxLote_2;
	@View
	private Listbox lbxVacuna_3;
	@View
	private Listbox lbxDosis_3;
	@View
	private Listbox lbxVia_3;
	@View
	private Listbox lbxSitio_3;
	@View
	private Datebox dtbxFecha_administracion_3;
	@View
	private Textbox tbxFabricante_3;
	@View
	private Textbox tbxLote_3;
	@View
	private Listbox lbxVacuna_4;
	@View
	private Listbox lbxDosis_4;
	@View
	private Listbox lbxVia_4;
	@View
	private Listbox lbxSitio_4;
	@View
	private Datebox dtbxFecha_administracion_4;
	@View
	private Textbox tbxFabricante_4;
	@View
	private Textbox tbxLote_4;
	@View
	private Checkbox chbBecegeitis;
	@View
	private Checkbox chbAbsceso;
	@View
	private Checkbox chbLinfadenitis;
	@View
	private Checkbox chbFiebre;
	@View
	private Checkbox chbConvulsion_febril;
	@View
	private Checkbox chbConvulsion_sin_fiebre;
	@View
	private Checkbox chbEpisodio_hipotonico;
	@View
	private Checkbox chbParestesia;
	@View
	private Checkbox chbParalisis;
	@View
	private Checkbox chbEncefalopatia;
	@View
	private Checkbox chbMeningitis;
	@View
	private Checkbox chbUrticaria;
	@View
	private Checkbox chbEczema;
	@View
	private Checkbox chbChoque_anafilactico;
	@View
	private Checkbox chbGuillain_barre;
	@View
	private Checkbox chbCelulitis;
	@View
	private Checkbox chbInduracion;
	@View
	private Checkbox chbDolor_local;
	@View
	private Checkbox chbLlanto_persistente;
	@View
	private Checkbox chbIrritabilidad;
	@View
	private Checkbox chbEritema;
	@View
	private Checkbox chbOtro_datos_clinicos;
	@View
	private Textbox tbxOtro_datos_clinicos_cual;
	@View
	private Intbox ibxTiempo;
	@View
	private Listbox lbxUnidad_medida;
	@View
	private Textbox tbxLugar_de_vacunacion;
	@View
	private Listbox lbxDepartamento;
	@View
	private Listbox lbxMunicipio;
	@View
	private Radiogroup rdbEstado_salud_previo_vacunacion;
	@View
	private Radiogroup rdbMedicamento_durante_semana_anterior;
	@View
	private Textbox tbxMedicamento_durante_semana_anterior_cual;
	@View
	private Radiogroup rdbTiene_antecedentes_patologicos;
	@View
	private Textbox tbxTiene_antecedentes_patologicos_cual;
	@View
	private Radiogroup rdbTiene_antecedentes_alergicos;
	@View
	private Textbox tbxTiene_antecedentes_alergicos_cual;
	@View
	private Radiogroup rdbTiene_antecedentes_adversos_seguidos_vacunacion;
	@View
	private Textbox tbxTiene_antecedentes_adversos_seguidos_vacunacion_cual;
	@View
	private Radiogroup rdbEstado_paciente;
	@View
	private Radiogroup rdbExceso_en_dosis_recomendada;
	@View
	private Textbox tbxExceso_en_dosis_recomendada_describa;
	@View
	private Radiogroup rdbUso_equivocado_diluyenyte;
	@View
	private Textbox tbxUso_equivocado_diluyenyte_describa;
	@View
	private Radiogroup rdbClasificacion_final_caso;
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n5 ficha = new Ficha_epidemiologia_n5();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n5) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 5-------> "+ficha);
			
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
		Utilidades
				.listarElementoListbox(lbxVacuna_1, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxDosis_1, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxVia_1, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxSitio_1, true, getServiceLocator());
		Utilidades
				.listarElementoListbox(lbxVacuna_2, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxDosis_2, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxVia_2, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxSitio_2, true, getServiceLocator());
		Utilidades
				.listarElementoListbox(lbxVacuna_3, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxDosis_3, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxVia_3, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxSitio_3, true, getServiceLocator());
		Utilidades
				.listarElementoListbox(lbxVacuna_4, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxDosis_4, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxVia_4, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxSitio_4, true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxUnidad_medida, true,
				getServiceLocator());
		Utilidades.listarDepartamentos(lbxDepartamento, true,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
				getServiceLocator());
	}

	// manuel
	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		Utilidades.listarMunicipios(listboxMun, listboxDpto,
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
		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (lbxVacuna_1.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxVacuna_1.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxVacuna_2.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxVacuna_2.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxVacuna_3.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxVacuna_3.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxVacuna_4.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxVacuna_4.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxDosis_1.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxDosis_1.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxDosis_2.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxDosis_2.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxDosis_3.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxDosis_3.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxDosis_4.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxDosis_4.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxVia_1.getSelectedItem().getValue().toString().trim().equals("")) {
			lbxVia_1.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxVia_2.getSelectedItem().getValue().toString().trim().equals("")) {
			lbxVia_2.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxVia_3.getSelectedItem().getValue().toString().trim().equals("")) {
			lbxVia_3.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxVia_4.getSelectedItem().getValue().toString().trim().equals("")) {
			lbxVia_4.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxSitio_1.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxSitio_1.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxSitio_2.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxSitio_2.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxSitio_3.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxSitio_3.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxSitio_4.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxSitio_4.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxUnidad_medida.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxUnidad_medida.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxDepartamento.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxDepartamento.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxMunicipio.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxMunicipio.setStyle("background-color:#F6BBBE");
			valida = false;

		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					mensaje);
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

			// getServiceLocator().getFicha_epidemiologia_n5Service().setLimit("limit 25 offset 0");

			// List<Ficha_epidemiologia_n5> lista_datos =
			// getServiceLocator().getFicha_epidemiologia_n5Service().listar(parameters);
			rowsResultado.getChildren().clear();

			// for (Ficha_epidemiologia_n5 ficha_epidemiologia_n5 : lista_datos)
			// {
			// rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n5,
			// this));
			// }

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

		final Ficha_epidemiologia_n5 ficha_epidemiologia_n5 = (Ficha_epidemiologia_n5) objeto;

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
				mostrarDatos(ficha_epidemiologia_n5);
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
									eliminarDatos(ficha_epidemiologia_n5);
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
	public Ficha_epidemiologia_n5 obtenerFichaEpidemiologia() {

		// Cargamos los componentes //

		Ficha_epidemiologia_n5 ficha_epidemiologia_n5 = new Ficha_epidemiologia_n5();
		ficha_epidemiologia_n5.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n5
				.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		ficha_epidemiologia_n5.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n5.setIdentificacion("123");
		ficha_epidemiologia_n5.setFecha_inicial(new Timestamp(dtbxFecha_inicial
				.getValue().getTime()));
		ficha_epidemiologia_n5.setCodigo_diagnostico("A001");
		ficha_epidemiologia_n5.setVacuna_1(Integer.valueOf(lbxVacuna_1
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setDosis_1(Integer.valueOf(lbxDosis_1
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setVia_1(Integer.valueOf(lbxVia_1
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setSitio_1(Integer.valueOf(lbxSitio_1
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setFecha_administracion_1(new Timestamp(
				dtbxFecha_administracion_1.getValue().getTime()));
		ficha_epidemiologia_n5.setFabricante_1(tbxFabricante_1.getValue());
		ficha_epidemiologia_n5.setLote_1(tbxLote_1.getValue());
		ficha_epidemiologia_n5.setVacuna_2(Integer.valueOf(lbxVacuna_2
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setDosis_2(Integer.valueOf(lbxDosis_2
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setVia_2(Integer.valueOf(lbxVia_2
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setSitio_2(Integer.valueOf(lbxSitio_2
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setFecha_administracion_2(new Timestamp(
				dtbxFecha_administracion_2.getValue().getTime()));
		ficha_epidemiologia_n5.setFabricante_2(tbxFabricante_2.getValue());
		ficha_epidemiologia_n5.setLote_2(tbxLote_2.getValue());
		ficha_epidemiologia_n5.setVacuna_3(Integer.valueOf(lbxVacuna_3
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setDosis_3(Integer.valueOf(lbxDosis_3
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setVia_3(Integer.valueOf(lbxVia_3
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setSitio_3(Integer.valueOf(lbxSitio_3
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setFecha_administracion_3(new Timestamp(
				dtbxFecha_administracion_3.getValue().getTime()));
		ficha_epidemiologia_n5.setFabricante_3(tbxFabricante_3.getValue());
		ficha_epidemiologia_n5.setLote_3(tbxLote_3.getValue());
		ficha_epidemiologia_n5.setVacuna_4(Integer.valueOf(lbxVacuna_4
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setDosis_4(Integer.valueOf(lbxDosis_4
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setVia_4(Integer.valueOf(lbxVia_4
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setSitio_4(Integer.valueOf(lbxSitio_4
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setFecha_administracion_4(new Timestamp(
				dtbxFecha_administracion_4.getValue().getTime()));
		ficha_epidemiologia_n5.setFabricante_4(tbxFabricante_4.getValue());
		ficha_epidemiologia_n5.setLote_4(tbxLote_4.getValue());
		ficha_epidemiologia_n5.setBecegeitis(chbBecegeitis.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n5.setAbsceso(chbAbsceso.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5
				.setLinfadenitis(chbLinfadenitis.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5.setFiebre(chbFiebre.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5.setConvulsion_febril(chbConvulsion_febril
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5
				.setConvulsion_sin_fiebre(chbConvulsion_sin_fiebre.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n5.setEpisodio_hipotonico(chbEpisodio_hipotonico
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5.setParestesia(chbParestesia.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n5.setParalisis(chbParalisis.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n5
				.setEncefalopatia(chbEncefalopatia.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5.setMeningitis(chbMeningitis.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n5.setUrticaria(chbUrticaria.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n5.setEczema(chbEczema.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5.setChoque_anafilactico(chbChoque_anafilactico
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5
				.setGuillain_barre(chbGuillain_barre.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5.setCelulitis(chbCelulitis.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n5.setInduracion(chbInduracion.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n5.setDolor_local(chbDolor_local.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n5.setLlanto_persistente(chbLlanto_persistente
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5
				.setIrritabilidad(chbIrritabilidad.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5.setEritema(chbEritema.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5.setOtro_datos_clinicos(chbOtro_datos_clinicos
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n5
				.setOtro_datos_clinicos_cual(tbxOtro_datos_clinicos_cual
						.getValue());
		ficha_epidemiologia_n5
				.setTiempo((ibxTiempo.getValue() != null ? ibxTiempo.getValue()
						: 0));
		ficha_epidemiologia_n5.setUnidad_medida(Integer
				.valueOf(lbxUnidad_medida.getSelectedItem().getValue()
						.toString()));
		ficha_epidemiologia_n5.setLugar_de_vacunacion(tbxLugar_de_vacunacion
				.getValue());
		ficha_epidemiologia_n5.setDepartamento(Integer.valueOf(lbxDepartamento
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5.setMunicipio(Integer.valueOf(lbxMunicipio
				.getSelectedItem().getValue().toString()));
		ficha_epidemiologia_n5
				.setEstado_salud_previo_vacunacion(rdbEstado_salud_previo_vacunacion
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n5
				.setMedicamento_durante_semana_anterior(rdbMedicamento_durante_semana_anterior
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n5
				.setMedicamento_durante_semana_anterior_cual(tbxMedicamento_durante_semana_anterior_cual
						.getValue());
		ficha_epidemiologia_n5
				.setTiene_antecedentes_patologicos(rdbTiene_antecedentes_patologicos
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n5
				.setTiene_antecedentes_patologicos_cual(tbxTiene_antecedentes_patologicos_cual
						.getValue());
		ficha_epidemiologia_n5
				.setTiene_antecedentes_alergicos(rdbTiene_antecedentes_alergicos
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n5
				.setTiene_antecedentes_alergicos_cual(tbxTiene_antecedentes_alergicos_cual
						.getValue());
		ficha_epidemiologia_n5
				.setTiene_antecedentes_adversos_seguidos_vacunacion(rdbTiene_antecedentes_adversos_seguidos_vacunacion
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n5
				.setTiene_antecedentes_adversos_seguidos_vacunacion_cual(tbxTiene_antecedentes_adversos_seguidos_vacunacion_cual
						.getValue());
		ficha_epidemiologia_n5.setEstado_paciente(rdbEstado_paciente
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n5
				.setExceso_en_dosis_recomendada(rdbExceso_en_dosis_recomendada
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n5
				.setExceso_en_dosis_recomendada_describa(tbxExceso_en_dosis_recomendada_describa
						.getValue());
		ficha_epidemiologia_n5
				.setUso_equivocado_diluyenyte(rdbUso_equivocado_diluyenyte
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n5
				.setUso_equivocado_diluyenyte_describa(tbxUso_equivocado_diluyenyte_describa
						.getValue());
		ficha_epidemiologia_n5
				.setClasificacion_final_caso(rdbClasificacion_final_caso
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n5.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n5.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n5
				.setCreacion_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n5.setDelete_date(null);
		ficha_epidemiologia_n5.setUltimo_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n5.setDelete_user(null);

		return ficha_epidemiologia_n5;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n5 obj) throws Exception {
		Ficha_epidemiologia_n5 ficha_epidemiologia_n5 = (Ficha_epidemiologia_n5) obj;
		try {

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			for (int i = 0; i < lbxVacuna_1.getItemCount(); i++) {
				Listitem listitem = lbxVacuna_1.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getVacuna_1())) {
					listitem.setSelected(true);
					i = lbxVacuna_1.getItemCount();
				}
			}
			for (int i = 0; i < lbxDosis_1.getItemCount(); i++) {
				Listitem listitem = lbxDosis_1.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getDosis_1())) {
					listitem.setSelected(true);
					i = lbxDosis_1.getItemCount();
				}
			}
			for (int i = 0; i < lbxVia_1.getItemCount(); i++) {
				Listitem listitem = lbxVia_1.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getVia_1())) {
					listitem.setSelected(true);
					i = lbxVia_1.getItemCount();
				}
			}
			for (int i = 0; i < lbxSitio_1.getItemCount(); i++) {
				Listitem listitem = lbxSitio_1.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getSitio_1())) {
					listitem.setSelected(true);
					i = lbxSitio_1.getItemCount();
				}
			}
			dtbxFecha_administracion_1.setValue(ficha_epidemiologia_n5
					.getFecha_administracion_1());
			tbxFabricante_1.setValue(ficha_epidemiologia_n5.getFabricante_1());
			tbxLote_1.setValue(ficha_epidemiologia_n5.getLote_1());
			for (int i = 0; i < lbxVacuna_2.getItemCount(); i++) {
				Listitem listitem = lbxVacuna_2.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getVacuna_2())) {
					listitem.setSelected(true);
					i = lbxVacuna_2.getItemCount();
				}
			}
			for (int i = 0; i < lbxDosis_2.getItemCount(); i++) {
				Listitem listitem = lbxDosis_2.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getDosis_2())) {
					listitem.setSelected(true);
					i = lbxDosis_2.getItemCount();
				}
			}
			for (int i = 0; i < lbxVia_2.getItemCount(); i++) {
				Listitem listitem = lbxVia_2.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getVia_2())) {
					listitem.setSelected(true);
					i = lbxVia_2.getItemCount();
				}
			}
			for (int i = 0; i < lbxSitio_2.getItemCount(); i++) {
				Listitem listitem = lbxSitio_2.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getSitio_2())) {
					listitem.setSelected(true);
					i = lbxSitio_2.getItemCount();
				}
			}
			dtbxFecha_administracion_2.setValue(ficha_epidemiologia_n5
					.getFecha_administracion_2());
			tbxFabricante_2.setValue(ficha_epidemiologia_n5.getFabricante_2());
			tbxLote_2.setValue(ficha_epidemiologia_n5.getLote_2());
			for (int i = 0; i < lbxVacuna_3.getItemCount(); i++) {
				Listitem listitem = lbxVacuna_3.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getVacuna_3())) {
					listitem.setSelected(true);
					i = lbxVacuna_3.getItemCount();
				}
			}
			for (int i = 0; i < lbxDosis_3.getItemCount(); i++) {
				Listitem listitem = lbxDosis_3.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getDosis_3())) {
					listitem.setSelected(true);
					i = lbxDosis_3.getItemCount();
				}
			}
			for (int i = 0; i < lbxVia_3.getItemCount(); i++) {
				Listitem listitem = lbxVia_3.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getVia_3())) {
					listitem.setSelected(true);
					i = lbxVia_3.getItemCount();
				}
			}
			for (int i = 0; i < lbxSitio_3.getItemCount(); i++) {
				Listitem listitem = lbxSitio_3.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getSitio_3())) {
					listitem.setSelected(true);
					i = lbxSitio_3.getItemCount();
				}
			}
			dtbxFecha_administracion_3.setValue(ficha_epidemiologia_n5
					.getFecha_administracion_3());
			tbxFabricante_3.setValue(ficha_epidemiologia_n5.getFabricante_3());
			tbxLote_3.setValue(ficha_epidemiologia_n5.getLote_3());
			for (int i = 0; i < lbxVacuna_4.getItemCount(); i++) {
				Listitem listitem = lbxVacuna_4.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getVacuna_4())) {
					listitem.setSelected(true);
					i = lbxVacuna_4.getItemCount();
				}
			}
			for (int i = 0; i < lbxDosis_4.getItemCount(); i++) {
				Listitem listitem = lbxDosis_4.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getDosis_4())) {
					listitem.setSelected(true);
					i = lbxDosis_4.getItemCount();
				}
			}
			for (int i = 0; i < lbxVia_4.getItemCount(); i++) {
				Listitem listitem = lbxVia_4.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getVia_4())) {
					listitem.setSelected(true);
					i = lbxVia_4.getItemCount();
				}
			}
			for (int i = 0; i < lbxSitio_4.getItemCount(); i++) {
				Listitem listitem = lbxSitio_4.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getSitio_4())) {
					listitem.setSelected(true);
					i = lbxSitio_4.getItemCount();
				}
			}
			dtbxFecha_administracion_4.setValue(ficha_epidemiologia_n5
					.getFecha_administracion_4());
			tbxFabricante_4.setValue(ficha_epidemiologia_n5.getFabricante_4());
			tbxLote_4.setValue(ficha_epidemiologia_n5.getLote_4());
			chbBecegeitis.setChecked(ficha_epidemiologia_n5.getBecegeitis()
					.equals("S") ? true : false);
			chbAbsceso.setChecked(ficha_epidemiologia_n5.getAbsceso().equals(
					"S") ? true : false);
			chbLinfadenitis.setChecked(ficha_epidemiologia_n5.getLinfadenitis()
					.equals("S") ? true : false);
			chbFiebre
					.setChecked(ficha_epidemiologia_n5.getFiebre().equals("S") ? true
							: false);
			chbConvulsion_febril.setChecked(ficha_epidemiologia_n5
					.getConvulsion_febril().equals("S") ? true : false);
			chbConvulsion_sin_fiebre.setChecked(ficha_epidemiologia_n5
					.getConvulsion_sin_fiebre().equals("S") ? true : false);
			chbEpisodio_hipotonico.setChecked(ficha_epidemiologia_n5
					.getEpisodio_hipotonico().equals("S") ? true : false);
			chbParestesia.setChecked(ficha_epidemiologia_n5.getParestesia()
					.equals("S") ? true : false);
			chbParalisis.setChecked(ficha_epidemiologia_n5.getParalisis()
					.equals("S") ? true : false);
			chbEncefalopatia.setChecked(ficha_epidemiologia_n5
					.getEncefalopatia().equals("S") ? true : false);
			chbMeningitis.setChecked(ficha_epidemiologia_n5.getMeningitis()
					.equals("S") ? true : false);
			chbUrticaria.setChecked(ficha_epidemiologia_n5.getUrticaria()
					.equals("S") ? true : false);
			chbEczema
					.setChecked(ficha_epidemiologia_n5.getEczema().equals("S") ? true
							: false);
			chbChoque_anafilactico.setChecked(ficha_epidemiologia_n5
					.getChoque_anafilactico().equals("S") ? true : false);
			chbGuillain_barre.setChecked(ficha_epidemiologia_n5
					.getGuillain_barre().equals("S") ? true : false);
			chbCelulitis.setChecked(ficha_epidemiologia_n5.getCelulitis()
					.equals("S") ? true : false);
			chbInduracion.setChecked(ficha_epidemiologia_n5.getInduracion()
					.equals("S") ? true : false);
			chbDolor_local.setChecked(ficha_epidemiologia_n5.getDolor_local()
					.equals("S") ? true : false);
			chbLlanto_persistente.setChecked(ficha_epidemiologia_n5
					.getLlanto_persistente().equals("S") ? true : false);
			chbIrritabilidad.setChecked(ficha_epidemiologia_n5
					.getIrritabilidad().equals("S") ? true : false);
			chbEritema.setChecked(ficha_epidemiologia_n5.getEritema().equals(
					"S") ? true : false);
			chbOtro_datos_clinicos.setChecked(ficha_epidemiologia_n5
					.getOtro_datos_clinicos().equals("S") ? true : false);
			tbxOtro_datos_clinicos_cual.setValue(ficha_epidemiologia_n5
					.getOtro_datos_clinicos_cual());
			ibxTiempo.setValue(ficha_epidemiologia_n5.getTiempo());
			for (int i = 0; i < lbxUnidad_medida.getItemCount(); i++) {
				Listitem listitem = lbxUnidad_medida.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getUnidad_medida())) {
					listitem.setSelected(true);
					i = lbxUnidad_medida.getItemCount();
				}
			}
			tbxLugar_de_vacunacion.setValue(ficha_epidemiologia_n5
					.getLugar_de_vacunacion());
			for (int i = 0; i < lbxDepartamento.getItemCount(); i++) {
				Listitem listitem = lbxDepartamento.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getDepartamento())) {
					listitem.setSelected(true);
					i = lbxDepartamento.getItemCount();
				}
			}
			for (int i = 0; i < lbxMunicipio.getItemCount(); i++) {
				Listitem listitem = lbxMunicipio.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n5.getMunicipio())) {
					listitem.setSelected(true);
					i = lbxMunicipio.getItemCount();
				}
			}
			Utilidades.seleccionarRadio(rdbEstado_salud_previo_vacunacion,
					ficha_epidemiologia_n5.getEstado_salud_previo_vacunacion());
			Utilidades.seleccionarRadio(rdbMedicamento_durante_semana_anterior,
					ficha_epidemiologia_n5
							.getMedicamento_durante_semana_anterior());
			tbxMedicamento_durante_semana_anterior_cual
					.setValue(ficha_epidemiologia_n5
							.getMedicamento_durante_semana_anterior_cual());
			Utilidades.seleccionarRadio(rdbTiene_antecedentes_patologicos,
					ficha_epidemiologia_n5.getTiene_antecedentes_patologicos());
			tbxTiene_antecedentes_patologicos_cual
					.setValue(ficha_epidemiologia_n5
							.getTiene_antecedentes_patologicos_cual());
			Utilidades.seleccionarRadio(rdbTiene_antecedentes_alergicos,
					ficha_epidemiologia_n5.getTiene_antecedentes_alergicos());
			tbxTiene_antecedentes_alergicos_cual
					.setValue(ficha_epidemiologia_n5
							.getTiene_antecedentes_alergicos_cual());
			Utilidades
					.seleccionarRadio(
							rdbTiene_antecedentes_adversos_seguidos_vacunacion,
							ficha_epidemiologia_n5
									.getTiene_antecedentes_adversos_seguidos_vacunacion());
			tbxTiene_antecedentes_adversos_seguidos_vacunacion_cual
					.setValue(ficha_epidemiologia_n5
							.getTiene_antecedentes_adversos_seguidos_vacunacion_cual());
			Utilidades.seleccionarRadio(rdbEstado_paciente,
					ficha_epidemiologia_n5.getEstado_paciente());
			Utilidades.seleccionarRadio(rdbExceso_en_dosis_recomendada,
					ficha_epidemiologia_n5.getExceso_en_dosis_recomendada());
			tbxExceso_en_dosis_recomendada_describa
					.setValue(ficha_epidemiologia_n5
							.getExceso_en_dosis_recomendada_describa());
			Utilidades.seleccionarRadio(rdbUso_equivocado_diluyenyte,
					ficha_epidemiologia_n5.getUso_equivocado_diluyenyte());
			tbxUso_equivocado_diluyenyte_describa
					.setValue(ficha_epidemiologia_n5
							.getUso_equivocado_diluyenyte_describa());
			Utilidades.seleccionarRadio(rdbClasificacion_final_caso,
					ficha_epidemiologia_n5.getClasificacion_final_caso());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n5 ficha_epidemiologia_n5 = (Ficha_epidemiologia_n5) obj;
		try {
			int result = getServiceLocator().getFicha_epidemiologia_nnService()
					.eliminar(ficha_epidemiologia_n5);
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
		
		tbxNombre_aseguradora.setValue(administradora.getNombre());	}

	
	@Override
	public Ficha_epidemiologia_n5 consultarDatos(Map<String, Object> map,
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
					parameters.put("codigo_cie", cie_epidemiologia.getCodigo_cie());			
				}else{
					return null;
				}
				
				getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
				
				List<Ficha_epidemiologia_n5> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n5.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n5 ficha_n5 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n5;
				}else{

					return null;
				}
	}

}