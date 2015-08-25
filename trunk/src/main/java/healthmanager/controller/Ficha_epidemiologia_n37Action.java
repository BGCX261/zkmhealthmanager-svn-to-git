/*
 * ficha_epidemiologia_n37Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n37;
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

public class Ficha_epidemiologia_n37Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n37> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n37Action.class);

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
	private Textbox tbxNombre_madre_o_padre;
	@View
	private Textbox tbxOcupacion_madre_padre;
	@View
	private Textbox tbxCodigo_ocupacion;
	@View
	private Textbox tbxDireccion_trabajo;
	@View
	private Radiogroup rdbCaso_detectado_por;
	@View
	private Radiogroup rdbVacuna_sarampion;
	@View
	private Intbox ibxDosis1;
	@View
	private Datebox dtbxFecha_ultima_dosis1;
	@View
	private Radiogroup rdbFuente1;
	@View
	private Radiogroup rdbTipo_vacuna1;
	@View
	private Radiogroup rdbVacuna_rubeola;
	@View
	private Intbox ibxDosis2;
	@View
	private Datebox dtbxFecha_ultima_dosis2;
	@View
	private Radiogroup rdbFuente2;
	@View
	private Radiogroup rdbTipo_vacuna2;
	@View
	private Datebox dtbxHora_visita_domi;
	@View
	private Textbox tbxDiagnostico_ini_cie;
	@View
	private Datebox dtbxFecha_ini_fiebre;
	@View
	private Radiogroup rdbTipo_erupcion;
	@View
	private Textbox tbxOtro_tipo_erupcion;
	@View
	private Datebox dtbxFecha_ini_erupcion;
	@View
	private Intbox ibxDuracion_erupcion;
	@View
	private Radiogroup rdbTos;
	@View
	private Radiogroup rdbCoriza;
	@View
	private Radiogroup rdbConjuntivitis;
	@View
	private Radiogroup rdbAdenopatia;
	@View
	private Radiogroup rdbArtralgia;
	@View
	private Radiogroup rdbEmbarazada;
	@View
	private Intbox ibxNumero_semana;
	@View
	private Textbox tbxLugar_probable_parto;
	@View
	private Textbox tbxCodigo;
	@View
	private Radiogroup rdbCaso_confirmado_sara_rube_erup;
	@View
	private Radiogroup rdbCaso_confirmado_sara_rube;
	@View
	private Radiogroup rdbViajo_dias_previos;
	@View
	private Textbox tbxDonde_viajo;
	@View
	private Listbox lbxDepartamento_lugar_parto;
	@View
	private Listbox lbxMunicipio_lugar_parto;
	@View
	private Listbox lbxDepartamento;
	@View
	private Listbox lbxMunicipio;
	@View
	private Radiogroup rdbContacto_mujer_embarazada;
	@View
	private Datebox dtbxFecha_de_toma1;
	@View
	private Datebox dtbxFecha_de_recepcion1;
	@View
	private Checkbox chbMuestra1;
	@View
	private Checkbox chbPrueba1;
	@View
	private Textbox tbxAgente1;
	@View
	private Textbox tbxResultado1;
	@View
	private Datebox dtbxFecha_de_recepcion1_2;
	@View
	private Textbox tbxValor_del_registro1;
	@View
	private Datebox dtbxFecha_de_toma2;
	@View
	private Datebox dtbxFecha_de_recepcion2;
	@View
	private Checkbox chbMuestra2;
	@View
	private Checkbox chbPrueba2;
	@View
	private Textbox tbxAgente2;
	@View
	private Textbox tbxResultado2;
	@View
	private Datebox dtbxFecha_de_recepcion2_2;
	@View
	private Textbox tbxValor_del_registro2;
	@View
	private Datebox dtbxFecha_de_toma3;
	@View
	private Datebox dtbxFecha_de_recepcion3;
	@View
	private Checkbox chbMuestra3;
	@View
	private Checkbox chbPrueba3;
	@View
	private Textbox tbxAgente3;
	@View
	private Textbox tbxResultado3;
	@View
	private Datebox dtbxFecha_de_recepcion3_2;
	@View
	private Textbox tbxValor_del_registro3;
	@View
	private Datebox dtbxFecha_de_toma4;
	@View
	private Datebox dtbxFecha_de_recepcion4;
	@View
	private Checkbox chbMuestra4;
	@View
	private Checkbox chbPrueba4;
	@View
	private Textbox tbxAgente4;
	@View
	private Textbox tbxResultado4;
	@View
	private Datebox dtbxFecha_de_recepcion4_2;
	@View
	private Textbox tbxValor_del_registro4;
	@View
	private Radiogroup rdbVacuna_bloqueo;
	@View
	private Radiogroup rdbMonitoreo_rapido_cobertura;
	@View
	private Radiogroup rdbSeguimientos_contacto;
	@View
	private Radiogroup rdbConfirmado_fuente_infeccion;
	@View
	private Textbox tbxCaso_importado_pais;
	@View
	private Radiogroup rdbCaso_descartado;
	@View
	private Textbox tbxOtro_diagnostico;
	private final String[] idsExcluyentes = {};
	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;

	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada.getCodigo_empresa()+" - "+ficha_seleccionada.getCodigo_sucursal()+" - "+ficha_seleccionada.getConsecutivo());
			Ficha_epidemiologia_n37 ficha = new Ficha_epidemiologia_n37();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());

			//log.info("consultar ficha 37-------> "+ficha);
			
			ficha = (Ficha_epidemiologia_n37) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 37-------> "+ficha);
			
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
		Utilidades.listarDepartamentos(lbxDepartamento, true,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
				getServiceLocator());

		Utilidades.listarDepartamentos(lbxDepartamento_lugar_parto, true,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxMunicipio_lugar_parto,
				lbxDepartamento_lugar_parto, getServiceLocator());
	}

	// manuel
	public void listarMunicipios(Listbox lbxMunicipio, Listbox lbxDepartamento) {
		Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
				getServiceLocator());
	}

	public void listarMunicipios2(Listbox lbxMunicipio_lugar_parto,
			Listbox lbxDepartamento_lugar_parto) {
		Utilidades.listarMunicipios(lbxMunicipio_lugar_parto,
				lbxDepartamento_lugar_parto, getServiceLocator());
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
			//btGuardar.setVisible(false);
		} else {
			// buscarDatos();
			limpiarDatos();
			FormularioUtil.cargarRadiogroupsDefecto(this);
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

		if (lbxDepartamento.getSelectedItem() != null) {
			if (lbxDepartamento_lugar_parto.getSelectedItem().getValue()
					.toString().equals("")) {
				lbxDepartamento_lugar_parto
						.setStyle("background-color:#F6BBBE");
				valida = false;

			}
		}

		if (lbxMunicipio_lugar_parto.getSelectedItem() != null) {
			if (lbxMunicipio_lugar_parto.getSelectedItem().getValue()
					.toString().equals("")) {
				lbxMunicipio_lugar_parto.setStyle("background-color:#F6BBBE");
				valida = false;
			}
		}

		if (lbxDepartamento.getSelectedItem().getValue().toString().equals("")) {
			lbxDepartamento.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxMunicipio.getSelectedItem() != null) {
			if (lbxMunicipio.getSelectedItem().getValue().toString().equals("")) {
				lbxMunicipio.setStyle("background-color:#F6BBBE");
				valida = false;
			}
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

			getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
					"limit 25 offset 0");

			List<Ficha_epidemiologia_n37> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(Ficha_epidemiologia_n37.class, parameters);
			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n37 ficha_epidemiologia_n37 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n37,
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

		final Ficha_epidemiologia_n37 ficha_epidemiologia_n37 = (Ficha_epidemiologia_n37) objeto;

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
				mostrarDatos(ficha_epidemiologia_n37);
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
									eliminarDatos(ficha_epidemiologia_n37);
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
	public Ficha_epidemiologia_n37 obtenerFichaEpidemiologia() {
		// Cargamos los componentes //

		Ficha_epidemiologia_n37 ficha_epidemiologia_n37 = new Ficha_epidemiologia_n37();
		ficha_epidemiologia_n37.setFecha_inicial(new Timestamp(
				dtbxFecha_inicial.getValue().getTime()));
		ficha_epidemiologia_n37.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n37.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		ficha_epidemiologia_n37.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n37.setIdentificacion(tbxIdentificacion_p.getValue());
		ficha_epidemiologia_n37.setCodigo_diagnostico("A031");
		ficha_epidemiologia_n37.setPrimer_nombre_paciente("");
		ficha_epidemiologia_n37.setSegundo_nombre_paciente("");
		ficha_epidemiologia_n37.setPrimer_apellido_paciente("");
		ficha_epidemiologia_n37.setSegundo_apellido_paciente("");
		ficha_epidemiologia_n37.setTipo_identidad("");
		ficha_epidemiologia_n37.setNumero_identidad("");
		ficha_epidemiologia_n37.setNombre_madre_o_padre(tbxNombre_madre_o_padre
				.getValue());
		ficha_epidemiologia_n37
				.setOcupacion_madre_padre(tbxOcupacion_madre_padre.getValue());
		ficha_epidemiologia_n37.setCodigo_ocupacion(tbxCodigo_ocupacion
				.getValue());
		ficha_epidemiologia_n37.setDireccion_trabajo(tbxDireccion_trabajo
				.getValue());
		ficha_epidemiologia_n37.setCaso_detectado_por(rdbCaso_detectado_por
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setVacuna_sarampion(rdbVacuna_sarampion
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37
				.setDosis1((ibxDosis1.getValue() != null ? ibxDosis1.getValue()
						.toString() : "0"));
		ficha_epidemiologia_n37.setFecha_ultima_dosis1(new Timestamp(
				dtbxFecha_ultima_dosis1.getValue().getTime()));
		ficha_epidemiologia_n37.setFuente1(rdbFuente1.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n37.setTipo_vacuna1(rdbTipo_vacuna1
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setVacuna_rubeola(rdbVacuna_rubeola
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37
				.setDosis2((ibxDosis2.getValue() != null ? ibxDosis2.getValue()
						.toString() : "0"));
		ficha_epidemiologia_n37.setFecha_ultima_dosis2(new Timestamp(
				dtbxFecha_ultima_dosis2.getValue().getTime()));
		ficha_epidemiologia_n37.setFuente2(rdbFuente2.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n37.setTipo_vacuna2(rdbTipo_vacuna2
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setHora_visita_domi(new Timestamp(
				dtbxHora_visita_domi.getValue().getTime()));
		ficha_epidemiologia_n37.setDiagnostico_ini_cie(tbxDiagnostico_ini_cie
				.getValue());
		ficha_epidemiologia_n37.setFecha_ini_fiebre(new Timestamp(
				dtbxFecha_ini_fiebre.getValue().getTime()));
		ficha_epidemiologia_n37.setTipo_erupcion(rdbTipo_erupcion
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setOtro_tipo_erupcion(tbxOtro_tipo_erupcion
				.getValue());
		ficha_epidemiologia_n37.setFecha_ini_erupcion(new Timestamp(
				dtbxFecha_ini_erupcion.getValue().getTime()));
		ficha_epidemiologia_n37.setDuracion_erupcion((ibxDuracion_erupcion
				.getValue() != null ? ibxDuracion_erupcion.getValue()
				.toString() : "0"));
		ficha_epidemiologia_n37.setTos(rdbTos.getSelectedItem().getValue()
				.toString());
		ficha_epidemiologia_n37.setCoriza(rdbCoriza.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n37.setConjuntivitis(rdbConjuntivitis
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setAdenopatia(rdbAdenopatia.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n37.setArtralgia(rdbArtralgia.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n37.setEmbarazada(rdbEmbarazada.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n37
				.setNumero_semana((ibxNumero_semana.getValue() != null ? ibxNumero_semana
						.getValue().toString() : "0"));
		ficha_epidemiologia_n37.setLugar_probable_parto(tbxLugar_probable_parto
				.getValue());
		ficha_epidemiologia_n37.setCodigo(tbxCodigo.getValue());
		ficha_epidemiologia_n37
				.setCaso_confirmado_sara_rube_erup(rdbCaso_confirmado_sara_rube_erup
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37
				.setCaso_confirmado_sara_rube(rdbCaso_confirmado_sara_rube
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setViajo_dias_previos(rdbViajo_dias_previos
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setDonde_viajo(tbxDonde_viajo.getValue());
		ficha_epidemiologia_n37.setDepartamento(lbxDepartamento
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setMunicipio(lbxMunicipio.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n37
				.setDepartamento_lugar_parto(lbxDepartamento_lugar_parto
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37
				.setMunicipio_lugar_parto(lbxMunicipio_lugar_parto
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37
				.setContacto_mujer_embarazada(rdbContacto_mujer_embarazada
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setFecha_de_toma1(new Timestamp(
				dtbxFecha_de_toma1.getValue().getTime()));
		ficha_epidemiologia_n37.setFecha_de_recepcion1(new Timestamp(
				dtbxFecha_de_recepcion1.getValue().getTime()));
		ficha_epidemiologia_n37
				.setMuestra1(chbMuestra1.isChecked() ? "S" : "N");
		ficha_epidemiologia_n37.setPrueba1(chbPrueba1.isChecked() ? "S" : "N");
		ficha_epidemiologia_n37.setAgente1(tbxAgente1.getValue());
		ficha_epidemiologia_n37.setResultado1(tbxResultado1.getValue());
		ficha_epidemiologia_n37.setFecha_de_recepcion1_2(new Timestamp(
				dtbxFecha_de_recepcion1_2.getValue().getTime()));
		ficha_epidemiologia_n37.setValor_del_registro1(tbxValor_del_registro1
				.getValue());
		ficha_epidemiologia_n37.setFecha_de_toma2(new Timestamp(
				dtbxFecha_de_toma2.getValue().getTime()));
		ficha_epidemiologia_n37.setFecha_de_recepcion2(new Timestamp(
				dtbxFecha_de_recepcion2.getValue().getTime()));
		ficha_epidemiologia_n37
				.setMuestra2(chbMuestra2.isChecked() ? "S" : "N");
		ficha_epidemiologia_n37.setPrueba2(chbPrueba2.isChecked() ? "S" : "N");
		ficha_epidemiologia_n37.setAgente2(tbxAgente2.getValue());
		ficha_epidemiologia_n37.setResultado2(tbxResultado2.getValue());
		ficha_epidemiologia_n37.setFecha_de_recepcion2_2(new Timestamp(
				dtbxFecha_de_recepcion2_2.getValue().getTime()));
		ficha_epidemiologia_n37.setValor_del_registro2(tbxValor_del_registro2
				.getValue());
		ficha_epidemiologia_n37.setFecha_de_toma3(new Timestamp(
				dtbxFecha_de_toma3.getValue().getTime()));
		ficha_epidemiologia_n37.setFecha_de_recepcion3(new Timestamp(
				dtbxFecha_de_recepcion3.getValue().getTime()));
		ficha_epidemiologia_n37
				.setMuestra3(chbMuestra3.isChecked() ? "S" : "N");
		ficha_epidemiologia_n37.setPrueba3(chbPrueba3.isChecked() ? "S" : "N");
		ficha_epidemiologia_n37.setAgente3(tbxAgente3.getValue());
		ficha_epidemiologia_n37.setResultado3(tbxResultado3.getValue());
		ficha_epidemiologia_n37.setFecha_de_recepcion3_2(new Timestamp(
				dtbxFecha_de_recepcion3_2.getValue().getTime()));
		ficha_epidemiologia_n37.setValor_del_registro3(tbxValor_del_registro3
				.getValue());
		ficha_epidemiologia_n37.setFecha_de_toma4(new Timestamp(
				dtbxFecha_de_toma4.getValue().getTime()));
		ficha_epidemiologia_n37.setFecha_de_recepcion4(new Timestamp(
				dtbxFecha_de_recepcion4.getValue().getTime()));
		ficha_epidemiologia_n37
				.setMuestra4(chbMuestra4.isChecked() ? "S" : "N");
		ficha_epidemiologia_n37.setPrueba4(chbPrueba4.isChecked() ? "S" : "N");
		ficha_epidemiologia_n37.setAgente4(tbxAgente4.getValue());
		ficha_epidemiologia_n37.setResultado4(tbxResultado4.getValue());
		ficha_epidemiologia_n37.setFecha_de_recepcion4_2(new Timestamp(
				dtbxFecha_de_recepcion4_2.getValue().getTime()));
		ficha_epidemiologia_n37.setValor_del_registro4(tbxValor_del_registro4
				.getValue());
		ficha_epidemiologia_n37.setVacuna_bloqueo(rdbVacuna_bloqueo
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37
				.setMonitoreo_rapido_cobertura(rdbMonitoreo_rapido_cobertura
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37
				.setSeguimientos_contacto(rdbSeguimientos_contacto
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37
				.setConfirmado_fuente_infeccion(rdbConfirmado_fuente_infeccion
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setCaso_importado_pais(tbxCaso_importado_pais
				.getValue());
		ficha_epidemiologia_n37.setCaso_descartado(rdbCaso_descartado
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n37.setOtro_diagnostico(tbxOtro_diagnostico
				.getValue());
		ficha_epidemiologia_n37.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n37.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n37.setCreacion_user(usuarios.getCodigo()
				.toString());
		ficha_epidemiologia_n37.setDelete_date(null);
		ficha_epidemiologia_n37.setUltimo_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n37.setDelete_user(null);

		return ficha_epidemiologia_n37;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n37 obj) throws Exception {
		Ficha_epidemiologia_n37 ficha_epidemiologia_n37 = (Ficha_epidemiologia_n37) obj;
		try {
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n37.getFecha_inicial());
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n37.getCodigo_ficha());
			tbxIdentificacion_p.setValue(ficha_epidemiologia_n37.getIdentificacion());

			
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			tbxNombre_madre_o_padre.setValue(ficha_epidemiologia_n37
					.getNombre_madre_o_padre());
			tbxOcupacion_madre_padre.setValue(ficha_epidemiologia_n37
					.getOcupacion_madre_padre());
			tbxCodigo_ocupacion.setValue(ficha_epidemiologia_n37
					.getCodigo_ocupacion());
			tbxDireccion_trabajo.setValue(ficha_epidemiologia_n37
					.getDireccion_trabajo());
			Utilidades.seleccionarRadio(rdbCaso_detectado_por,
					ficha_epidemiologia_n37.getCaso_detectado_por());
			Utilidades.seleccionarRadio(rdbVacuna_sarampion,
					ficha_epidemiologia_n37.getVacuna_sarampion());
			ibxDosis1.setValue(Integer.valueOf(ficha_epidemiologia_n37
					.getDosis1()));
			dtbxFecha_ultima_dosis1.setValue(ficha_epidemiologia_n37
					.getFecha_ultima_dosis1());
			Utilidades.seleccionarRadio(rdbFuente1,
					ficha_epidemiologia_n37.getFuente1());
			Utilidades.seleccionarRadio(rdbTipo_vacuna1,
					ficha_epidemiologia_n37.getTipo_vacuna1());
			Utilidades.seleccionarRadio(rdbVacuna_rubeola,
					ficha_epidemiologia_n37.getVacuna_rubeola());
			ibxDosis2.setValue(Integer.valueOf(ficha_epidemiologia_n37
					.getDosis2()));
			dtbxFecha_ultima_dosis2.setValue(ficha_epidemiologia_n37
					.getFecha_ultima_dosis2());
			Utilidades.seleccionarRadio(rdbFuente2,
					ficha_epidemiologia_n37.getFuente2());
			Utilidades.seleccionarRadio(rdbTipo_vacuna2,
					ficha_epidemiologia_n37.getTipo_vacuna2());
			dtbxHora_visita_domi.setValue(ficha_epidemiologia_n37
					.getHora_visita_domi());
			tbxDiagnostico_ini_cie.setValue(ficha_epidemiologia_n37
					.getDiagnostico_ini_cie());
			dtbxFecha_ini_fiebre.setValue(ficha_epidemiologia_n37
					.getFecha_ini_fiebre());
			Utilidades.seleccionarRadio(rdbTipo_erupcion,
					ficha_epidemiologia_n37.getTipo_erupcion());
			tbxOtro_tipo_erupcion.setValue(ficha_epidemiologia_n37
					.getOtro_tipo_erupcion());
			dtbxFecha_ini_erupcion.setValue(ficha_epidemiologia_n37
					.getFecha_ini_erupcion());
			ibxDuracion_erupcion.setValue(Integer
					.valueOf(ficha_epidemiologia_n37.getDuracion_erupcion()));
			Utilidades.seleccionarRadio(rdbTos,
					ficha_epidemiologia_n37.getTos());
			Utilidades.seleccionarRadio(rdbCoriza,
					ficha_epidemiologia_n37.getCoriza());
			Utilidades.seleccionarRadio(rdbConjuntivitis,
					ficha_epidemiologia_n37.getConjuntivitis());
			Utilidades.seleccionarRadio(rdbAdenopatia,
					ficha_epidemiologia_n37.getAdenopatia());
			Utilidades.seleccionarRadio(rdbArtralgia,
					ficha_epidemiologia_n37.getArtralgia());
			Utilidades.seleccionarRadio(rdbEmbarazada,
					ficha_epidemiologia_n37.getEmbarazada());
			ibxNumero_semana.setValue(Integer.valueOf(ficha_epidemiologia_n37
					.getNumero_semana()));
			tbxLugar_probable_parto.setValue(ficha_epidemiologia_n37
					.getLugar_probable_parto());
			tbxCodigo.setValue(ficha_epidemiologia_n37.getCodigo());
			Utilidades
					.seleccionarRadio(rdbCaso_confirmado_sara_rube_erup,
							ficha_epidemiologia_n37
									.getCaso_confirmado_sara_rube_erup());
			Utilidades.seleccionarRadio(rdbCaso_confirmado_sara_rube,
					ficha_epidemiologia_n37.getCaso_confirmado_sara_rube());
			Utilidades.seleccionarRadio(rdbViajo_dias_previos,
					ficha_epidemiologia_n37.getViajo_dias_previos());
			tbxDonde_viajo.setValue(ficha_epidemiologia_n37.getDonde_viajo());
			for (int i = 0; i < lbxDepartamento.getItemCount(); i++) {
				Listitem listitem = lbxDepartamento.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n37.getDepartamento())) {
					listitem.setSelected(true);
					i = lbxDepartamento.getItemCount();
				}
			}

			Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
					getServiceLocator());
			for (int i = 0; i < lbxMunicipio.getItemCount(); i++) {
				Listitem listitem = lbxMunicipio.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n37.getMunicipio())) {
					listitem.setSelected(true);
					i = lbxMunicipio.getItemCount();
				}
			}

			for (int i = 0; i < lbxDepartamento_lugar_parto.getItemCount(); i++) {
				Listitem listitem = lbxDepartamento_lugar_parto
						.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n37.getDepartamento())) {
					listitem.setSelected(true);
					i = lbxDepartamento_lugar_parto.getItemCount();
				}
			}

			Utilidades.listarMunicipios(lbxMunicipio_lugar_parto,
					lbxDepartamento_lugar_parto, getServiceLocator());
			for (int i = 0; i < lbxMunicipio_lugar_parto.getItemCount(); i++) {
				Listitem listitem = lbxMunicipio_lugar_parto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n37.getMunicipio())) {
					listitem.setSelected(true);
					i = lbxMunicipio_lugar_parto.getItemCount();
				}
			}

			Utilidades.seleccionarRadio(rdbContacto_mujer_embarazada,
					ficha_epidemiologia_n37.getContacto_mujer_embarazada());
			dtbxFecha_de_toma1.setValue(ficha_epidemiologia_n37
					.getFecha_de_toma1());
			dtbxFecha_de_recepcion1.setValue(ficha_epidemiologia_n37
					.getFecha_de_recepcion1());
			chbMuestra1.setChecked(ficha_epidemiologia_n37.getMuestra1()
					.equals("S") ? true : false);
			chbPrueba1.setChecked(ficha_epidemiologia_n37.getPrueba1().equals(
					"S") ? true : false);
			tbxAgente1.setValue(ficha_epidemiologia_n37.getAgente1());
			tbxResultado1.setValue(ficha_epidemiologia_n37.getResultado1());
			dtbxFecha_de_recepcion1_2.setValue(ficha_epidemiologia_n37
					.getFecha_de_recepcion1_2());
			tbxValor_del_registro1.setValue(ficha_epidemiologia_n37
					.getValor_del_registro1());
			dtbxFecha_de_toma2.setValue(ficha_epidemiologia_n37
					.getFecha_de_toma2());
			dtbxFecha_de_recepcion2.setValue(ficha_epidemiologia_n37
					.getFecha_de_recepcion2());
			chbMuestra2.setChecked(ficha_epidemiologia_n37.getMuestra2()
					.equals("S") ? true : false);
			chbPrueba2.setChecked(ficha_epidemiologia_n37.getPrueba2().equals(
					"S") ? true : false);
			tbxAgente2.setValue(ficha_epidemiologia_n37.getAgente2());
			tbxResultado2.setValue(ficha_epidemiologia_n37.getResultado2());
			dtbxFecha_de_recepcion2_2.setValue(ficha_epidemiologia_n37
					.getFecha_de_recepcion2_2());
			tbxValor_del_registro2.setValue(ficha_epidemiologia_n37
					.getValor_del_registro2());
			dtbxFecha_de_toma3.setValue(ficha_epidemiologia_n37
					.getFecha_de_toma3());
			dtbxFecha_de_recepcion3.setValue(ficha_epidemiologia_n37
					.getFecha_de_recepcion3());
			chbMuestra3.setChecked(ficha_epidemiologia_n37.getMuestra3()
					.equals("S") ? true : false);
			chbPrueba3.setChecked(ficha_epidemiologia_n37.getPrueba3().equals(
					"S") ? true : false);
			tbxAgente3.setValue(ficha_epidemiologia_n37.getAgente3());
			tbxResultado3.setValue(ficha_epidemiologia_n37.getResultado3());
			dtbxFecha_de_recepcion3_2.setValue(ficha_epidemiologia_n37
					.getFecha_de_recepcion3_2());
			tbxValor_del_registro3.setValue(ficha_epidemiologia_n37
					.getValor_del_registro3());
			dtbxFecha_de_toma4.setValue(ficha_epidemiologia_n37
					.getFecha_de_toma4());
			dtbxFecha_de_recepcion4.setValue(ficha_epidemiologia_n37
					.getFecha_de_recepcion4());
			chbMuestra4.setChecked(ficha_epidemiologia_n37.getMuestra4()
					.equals("S") ? true : false);
			chbPrueba4.setChecked(ficha_epidemiologia_n37.getPrueba4().equals(
					"S") ? true : false);
			tbxAgente4.setValue(ficha_epidemiologia_n37.getAgente4());
			tbxResultado4.setValue(ficha_epidemiologia_n37.getResultado4());
			dtbxFecha_de_recepcion4_2.setValue(ficha_epidemiologia_n37
					.getFecha_de_recepcion4_2());
			tbxValor_del_registro4.setValue(ficha_epidemiologia_n37
					.getValor_del_registro4());
			Utilidades.seleccionarRadio(rdbVacuna_bloqueo,
					ficha_epidemiologia_n37.getVacuna_bloqueo());
			Utilidades.seleccionarRadio(rdbMonitoreo_rapido_cobertura,
					ficha_epidemiologia_n37.getMonitoreo_rapido_cobertura());
			Utilidades.seleccionarRadio(rdbSeguimientos_contacto,
					ficha_epidemiologia_n37.getSeguimientos_contacto());
			Utilidades.seleccionarRadio(rdbConfirmado_fuente_infeccion,
					ficha_epidemiologia_n37.getConfirmado_fuente_infeccion());
			tbxCaso_importado_pais.setValue(ficha_epidemiologia_n37
					.getCaso_importado_pais());
			Utilidades.seleccionarRadio(rdbCaso_descartado,
					ficha_epidemiologia_n37.getCaso_descartado());
			tbxOtro_diagnostico.setValue(ficha_epidemiologia_n37
					.getOtro_diagnostico());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n37 ficha_epidemiologia_n37 = (Ficha_epidemiologia_n37) obj;
		try {
			int result = getServiceLocator()
					.getFicha_epidemiologia_nnService().eliminar(
							ficha_epidemiologia_n37);
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
	public Ficha_epidemiologia_n37 consultarDatos(Map<String, Object> map,
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
				
				//log.info("parameters"+parameters);
				
				List<Ficha_epidemiologia_n37> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n37.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n37 ficha_n37 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n37;
				}else{

					return null;
				}
	}

}