/*
 * ficha_epidemiologia_n14Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n14;
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

public class Ficha_epidemiologia_n14Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n14> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n14Action.class);

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
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View
	private Textbox tbxCodigo_dpto;
	@View
	private Textbox tbxCodigo_municipio;
	@View
	private Textbox tbxCodigo;
	@View
	private Listbox lbxSubindice;
	@View
	private Listbox lbxCodigo_evento;
	@View
	private Datebox dtbxFecha_de_notificacion;
	@View
	private Textbox tbxOrden;
	@View
	private Textbox tbxPais_procedencia_del_caso;
	@View
	private Listbox lbxCodigo_dpto_caso;
	@View
	private Listbox lbxCodigo_municipio_caso;
	@View
	private Listbox lbxArea_origen_caso;
	@View
	private Textbox tbxCodigo_barrio_vereda;
	@View
	private Listbox lbxGrupo_poblacional;
	@View
	private Datebox dtbxFecha_de_consulta;
	@View
	private Datebox dtbxInicio_de_sintomas;
	@View
	private Radiogroup rdbClasificacion_del_caso;
	@View
	private Checkbox chbCoartem;
	@View
	private Checkbox chbCloroquina_primaquina;
	@View
	private Checkbox chbCloroquina;
	@View
	private Checkbox chbOtro;
	@View
	private Textbox tbxOtro_tratamiento;
	@View
	private Radiogroup rdbVigilancia_activa;
	@View
	private Radiogroup rdbSintomatico;
	@View
	private Radiogroup rdbClasificacion_caso_origen;
	@View
	private Radiogroup rdbNuevo;
	@View
	private Radiogroup rdbRecurdescencia;
	@View
	private Radiogroup rdbEmbarazo;
	@View
	private Radiogroup rdbTrimestre_de_gestacion;
	@View
	private Radiogroup rdbTipo_de_examen;
	@View
	private Textbox tbxRecuento;
	@View
	private Radiogroup rdbGametocitos;
	@View
	private Radiogroup rdbResultado_del_examen;
	@View
	private Datebox dtbxFecha_del_resultado;
	@View
	private Radiogroup rdbTipo_de_examen2;
	@View
	private Radiogroup rdbResultado_del_examen2;
	@View
	private Textbox tbxEspecie;
	@View
	private Textbox tbxRecuento_parasitario;
	@View
	private Datebox dtbxFecha_del_resultado2;
	@View
	private Toolbarbutton btGuardar;

	@View
	private Textbox tbxEdad;
	@View
	private Datebox dtbxFecha_nacimiento;
	@View
	private Textbox tbxUnidad_medidad;
	@View
	private Textbox tbxSexo;
	@View
	private Textbox tbxCodigo_dpto2;
	@View
	private Textbox tbxCodigo_municipio2;
	@View
	private Textbox tbxDireccion;
	@View
	private Textbox tbxOcupacion;
	@View
	private Textbox tbxRegimen;
	@View
	private Textbox tbxAdministradora;
	@View
	private Textbox tbxPertenencia_etnica;

	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception {
		listarCombos();
		asignacionValoresPorDefecto();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n14 ficha = new Ficha_epidemiologia_n14();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n14) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 14-------> "+ficha);
			
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
		Utilidades.listarDepartamentos(lbxCodigo_dpto_caso, true,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxCodigo_municipio_caso,
				lbxCodigo_dpto_caso, getServiceLocator());

		// Utilidades.listarElementoListbox(lbxCodigo_dpto,true,getServiceLocator());
		// Utilidades.listarElementoListbox(lbxCodigo_municipio,true,getServiceLocator());
		// Utilidades.listarElementoListbox(lbxCodigo,true,getServiceLocator());
		Utilidades.listarElementoListbox(lbxSubindice, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxCodigo_evento, true,
				getServiceLocator());
		// Utilidades.listarElementoListbox(lbxCodigo_dpto_caso,true,getServiceLocator());
		// Utilidades.listarElementoListbox(lbxCodigo_municipio_caso,true,getServiceLocator());
		Utilidades.listarElementoListbox(lbxArea_origen_caso, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxGrupo_poblacional, true,
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
		Utilidades.listarMunicipios(lbxCodigo_municipio_caso,
				lbxCodigo_dpto_caso, getServiceLocator());
	}

	// Metodo para validar campos del formulario //
	public boolean validarFichaEpidemiologia() {

		tbxIdentificacion
				.setStyle("text-transform:uppercase;background-color:white");
		lbxCodigo_dpto_caso.setStyle("background-color:white;");
		lbxCodigo_municipio_caso.setStyle("background-color:white;");

		boolean valida = true;

		if (tbxIdentificacion.getText().trim().equals("")) {
			tbxIdentificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (lbxCodigo_dpto_caso.getSelectedItem().getValue().toString()
				.equals("")) {
			lbxCodigo_dpto_caso.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (lbxCodigo_municipio_caso.getSelectedItem() != null) {
			if (lbxCodigo_municipio_caso.getSelectedItem().getValue()
					.toString().equals("")) {
				lbxCodigo_municipio_caso.setStyle("background-color:#F6BBBE");
				valida = false;
			}
		}

		if (lbxCodigo_municipio_caso.getSelectedItem() == null) {
			lbxCodigo_municipio_caso.setStyle("background-color:#F6BBBE");
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

			List<Ficha_epidemiologia_n14> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n14.class, parameters);
			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n14 ficha_epidemiologia_n14 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n14,
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

		final Ficha_epidemiologia_n14 ficha_epidemiologia_n14 = (Ficha_epidemiologia_n14) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n14.getCodigo_ficha()
				+ ""));
		fila.appendChild(new Label(ficha_epidemiologia_n14.getFecha_inicial()
				+ ""));
		fila.appendChild(new Label(ficha_epidemiologia_n14.getIdentificacion()
				+ ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n14);
			}
		});
		hbox.appendChild(img);

		hbox.appendChild(space);
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public Ficha_epidemiologia_n14 obtenerFichaEpidemiologia() {

		Ficha_epidemiologia_n14 ficha_epidemiologia_n14 = new Ficha_epidemiologia_n14();
		ficha_epidemiologia_n14.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n14.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		ficha_epidemiologia_n14.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n14.setCodigo_diagnostico("Z000");
		ficha_epidemiologia_n14.setFecha_inicial(new Timestamp(
				dtbxFecha_inicial.getValue().getTime()));
		ficha_epidemiologia_n14.setIdentificacion(admision != null ? admision
				.getNro_identificacion() : null);

		ficha_epidemiologia_n14.setCodigo_dpto(tbxCodigo_dpto.getValue());
		ficha_epidemiologia_n14.setCodigo_municipio(tbxCodigo_municipio
				.getValue());

		ficha_epidemiologia_n14.setCodigo(tbxCodigo.getValue());
		ficha_epidemiologia_n14.setSubindice(lbxSubindice.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n14.setCodigo_evento(lbxCodigo_evento
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setFecha_de_notificacion(new Timestamp(
				dtbxFecha_de_notificacion.getValue().getTime()));
		ficha_epidemiologia_n14.setOrden(tbxOrden.getValue());
		ficha_epidemiologia_n14
				.setPais_procedencia_del_caso(tbxPais_procedencia_del_caso
						.getValue());
		ficha_epidemiologia_n14.setCodigo_dpto_caso(lbxCodigo_dpto_caso
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14
				.setCodigo_municipio_caso(lbxCodigo_municipio_caso
						.getSelectedItem().getValue().toString());

		ficha_epidemiologia_n14.setArea_origen_caso(lbxArea_origen_caso
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setCodigo_barrio_vereda(tbxCodigo_barrio_vereda
				.getValue());
		ficha_epidemiologia_n14.setGrupo_poblacional(lbxGrupo_poblacional
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setFecha_de_consulta(new Timestamp(
				dtbxFecha_de_consulta.getValue().getTime()));
		ficha_epidemiologia_n14.setInicio_de_sintomas(new Timestamp(
				dtbxInicio_de_sintomas.getValue().getTime()));
		ficha_epidemiologia_n14
				.setClasificacion_del_caso(rdbClasificacion_del_caso
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setCoartem(chbCoartem.isChecked());
		ficha_epidemiologia_n14
				.setCloroquina_primaquina(chbCloroquina_primaquina.isChecked());
		ficha_epidemiologia_n14.setCloroquina(chbCloroquina.isChecked());
		ficha_epidemiologia_n14.setOtro(chbOtro.isChecked());
		ficha_epidemiologia_n14.setOtro_tratamiento(tbxOtro_tratamiento
				.getValue());
		ficha_epidemiologia_n14.setVigilancia_activa(rdbVigilancia_activa
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setSintomatico(rdbSintomatico.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n14
				.setClasificacion_caso_origen(rdbClasificacion_caso_origen
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setNuevo(rdbNuevo.getSelectedItem().getValue()
				.toString());
		ficha_epidemiologia_n14.setRecurdescencia(rdbRecurdescencia
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setEmbarazo(rdbEmbarazo.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n14
				.setTrimestre_de_gestacion(rdbTrimestre_de_gestacion
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setTipo_de_examen(rdbTipo_de_examen
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setRecuento(tbxRecuento.getValue());
		ficha_epidemiologia_n14.setGametocitos(rdbGametocitos.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n14.setResultado_del_examen(rdbResultado_del_examen
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setFecha_del_resultado(new Timestamp(
				dtbxFecha_del_resultado.getValue().getTime()));
		ficha_epidemiologia_n14.setTipo_de_examen2(rdbTipo_de_examen2
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14
				.setResultado_del_examen2(rdbResultado_del_examen2
						.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n14.setEspecie(tbxEspecie.getValue());
		ficha_epidemiologia_n14.setRecuento_parasitario(tbxRecuento_parasitario
				.getValue());
		ficha_epidemiologia_n14.setFecha_del_resultado2(new Timestamp(
				dtbxFecha_del_resultado2.getValue().getTime()));
		ficha_epidemiologia_n14.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n14.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n14.setCreacion_user(usuarios.getCodigo()
				.toString());
		ficha_epidemiologia_n14.setDelete_date(null);
		ficha_epidemiologia_n14.setUltimo_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n14.setDelete_user(null);

		return ficha_epidemiologia_n14;
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n14 obj) throws Exception {
		Ficha_epidemiologia_n14 ficha_epidemiologia_n14 = (Ficha_epidemiologia_n14) obj;
		try {
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n14.getCodigo_ficha());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n14
					.getCodigo_diagnostico());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n14
					.getFecha_inicial());
			obtenerAdmision(admision);
			asignacionValoresPorDefecto();

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			tbxIdentificacion.setValue(ficha_epidemiologia_n14
					.getIdentificacion());
			tbxCodigo_dpto.setValue(ficha_epidemiologia_n14.getCodigo_dpto());
			tbxCodigo_municipio.setValue(ficha_epidemiologia_n14
					.getCodigo_municipio());
			tbxCodigo.setValue(ficha_epidemiologia_n14.getCodigo());

			for (int i = 0; i < lbxSubindice.getItemCount(); i++) {
				Listitem listitem = lbxSubindice.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n14.getSubindice())) {
					listitem.setSelected(true);
					i = lbxSubindice.getItemCount();
				}
			}
			for (int i = 0; i < lbxCodigo_evento.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_evento.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n14.getCodigo_evento())) {
					listitem.setSelected(true);
					i = lbxCodigo_evento.getItemCount();
				}
			}
			dtbxFecha_de_notificacion.setValue(ficha_epidemiologia_n14
					.getFecha_de_notificacion());
			tbxOrden.setValue(ficha_epidemiologia_n14.getOrden());
			tbxPais_procedencia_del_caso.setValue(ficha_epidemiologia_n14
					.getPais_procedencia_del_caso());
			for (int i = 0; i < lbxCodigo_dpto_caso.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_dpto_caso.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n14.getCodigo_dpto_caso())) {
					listitem.setSelected(true);
					i = lbxCodigo_dpto_caso.getItemCount();
				}
			}
			Utilidades.listarMunicipios(lbxCodigo_municipio_caso,
					lbxCodigo_dpto_caso, getServiceLocator());
			for (int i = 0; i < lbxCodigo_municipio_caso.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_municipio_caso.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(ficha_epidemiologia_n14
								.getCodigo_municipio_caso())) {
					listitem.setSelected(true);
					i = lbxCodigo_municipio_caso.getItemCount();
				}
			}
			for (int i = 0; i < lbxArea_origen_caso.getItemCount(); i++) {
				Listitem listitem = lbxArea_origen_caso.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n14.getArea_origen_caso())) {
					listitem.setSelected(true);
					i = lbxArea_origen_caso.getItemCount();
				}
			}
			tbxCodigo_barrio_vereda.setValue(ficha_epidemiologia_n14
					.getCodigo_barrio_vereda());
			for (int i = 0; i < lbxGrupo_poblacional.getItemCount(); i++) {
				Listitem listitem = lbxGrupo_poblacional.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n14.getGrupo_poblacional())) {
					listitem.setSelected(true);
					i = lbxGrupo_poblacional.getItemCount();
				}
			}
			dtbxFecha_de_consulta.setValue(ficha_epidemiologia_n14
					.getFecha_de_consulta());
			dtbxInicio_de_sintomas.setValue(ficha_epidemiologia_n14
					.getInicio_de_sintomas());
			Utilidades.seleccionarRadio(rdbClasificacion_del_caso,
					ficha_epidemiologia_n14.getClasificacion_del_caso());
			chbCoartem.setChecked(ficha_epidemiologia_n14.getCoartem());
			chbCloroquina_primaquina.setChecked(ficha_epidemiologia_n14
					.getCloroquina_primaquina());
			chbCloroquina.setChecked(ficha_epidemiologia_n14.getCloroquina());
			chbOtro.setChecked(ficha_epidemiologia_n14.getOtro());
			tbxOtro_tratamiento.setValue(ficha_epidemiologia_n14
					.getOtro_tratamiento());
			Utilidades.seleccionarRadio(rdbVigilancia_activa,
					ficha_epidemiologia_n14.getVigilancia_activa());
			Utilidades.seleccionarRadio(rdbSintomatico,
					ficha_epidemiologia_n14.getSintomatico());
			Utilidades.seleccionarRadio(rdbClasificacion_caso_origen,
					ficha_epidemiologia_n14.getClasificacion_caso_origen());
			Utilidades.seleccionarRadio(rdbNuevo,
					ficha_epidemiologia_n14.getNuevo());
			Utilidades.seleccionarRadio(rdbRecurdescencia,
					ficha_epidemiologia_n14.getRecurdescencia());
			Utilidades.seleccionarRadio(rdbEmbarazo,
					ficha_epidemiologia_n14.getEmbarazo());
			Utilidades.seleccionarRadio(rdbTrimestre_de_gestacion,
					ficha_epidemiologia_n14.getTrimestre_de_gestacion());
			Utilidades.seleccionarRadio(rdbTipo_de_examen,
					ficha_epidemiologia_n14.getTipo_de_examen());
			tbxRecuento.setValue(ficha_epidemiologia_n14.getRecuento());
			Utilidades.seleccionarRadio(rdbGametocitos,
					ficha_epidemiologia_n14.getGametocitos());
			Utilidades.seleccionarRadio(rdbResultado_del_examen,
					ficha_epidemiologia_n14.getResultado_del_examen());
			dtbxFecha_del_resultado.setValue(ficha_epidemiologia_n14
					.getFecha_del_resultado());
			Utilidades.seleccionarRadio(rdbTipo_de_examen2,
					ficha_epidemiologia_n14.getTipo_de_examen2());
			Utilidades.seleccionarRadio(rdbResultado_del_examen2,
					ficha_epidemiologia_n14.getResultado_del_examen2());
			tbxEspecie.setValue(ficha_epidemiologia_n14.getEspecie());
			tbxRecuento_parasitario.setValue(ficha_epidemiologia_n14
					.getRecuento_parasitario());
			dtbxFecha_del_resultado2.setValue(ficha_epidemiologia_n14
					.getFecha_del_resultado2());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n14 ficha_epidemiologia_n14 = (Ficha_epidemiologia_n14) obj;
		try {
			int result = getServiceLocator().getFicha_epidemiologia_nnService()
					.eliminar(ficha_epidemiologia_n14);
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

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		Utilidades.listarMunicipios(listboxMun, listboxDpto,
				getServiceLocator());
	}

	public void obtenerAdmision(Admision admision) {
		Paciente paciente = admision.getPaciente();
		tbxIdentificacion.setValue(admision.getNro_identificacion());
		tbxNombres_y_apellidos.setValue(paciente.getNombreCompleto());
		tbxTipo_identidad.setValue(paciente.getTipo_identificacion());
		tbxEdad.setValue(paciente.getEdad());
		dtbxFecha_nacimiento.setValue(paciente.getFecha_nacimiento());
		tbxUnidad_medidad.setValue(paciente.getUnidad_medidad());
		tbxSexo.setValue(paciente.getSexo());
		tbxCodigo_dpto2.setValue(paciente.getCodigo_dpto());
		tbxCodigo_municipio2.setValue(paciente.getCodigo_municipio());
		tbxDireccion.setValue(paciente.getDireccion());
		tbxPertenencia_etnica.setValue(paciente.getPertenencia_etnica());	
		tbxOcupacion.setValue(paciente.getCodigo_ocupacion());
		tbxRegimen.setValue(paciente.getTipo_usuario());
		tbxAdministradora.setValue(paciente.getCodigo_administradora());	
		
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

	public void asignacionValoresPorDefecto() {
		tbxCodigo_dpto.setValue("13");
		tbxCodigo_municipio.setValue("001");
		tbxCodigo.setValue("12345");
	}

	@Override
	public Ficha_epidemiologia_n14 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n14> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n14.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n14 ficha_n14 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n14;
				}else{

					return null;
				}
	}

}