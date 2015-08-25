/*
 * ficha_epidemiologia_n9Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n9;
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
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IFichas_epidemiologicas;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n9Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n9> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n9Action.class);

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
	private Textbox tbxIdentificacion;

	@View
	private Textbox tbxNombrePaciente;
	@View
	private Textbox tbxTipo_identificacion;

	@View
	private Datebox dtbxFecha_creacion;
	@View
	private Textbox tbxCodigo_diagnostico;
	@View
	private Textbox tbxAseguradora;
	@View
	private Textbox tbxNombre_aseguradora;

	@View
	private Radiogroup rdbIntoxicacion;
	@View
	private Textbox tbxNombre_producto;
	@View
	private Radiogroup rdbTipo_exposicion;
	@View
	private Checkbox chbElaboracion;
	@View
	private Checkbox chbUso_domiciliario;
	@View
	private Checkbox chbTratamiento_humano;
	@View
	private Checkbox chbDesconocido;
	@View
	private Checkbox chbAlmacenamiento;
	@View
	private Checkbox chbUso_salud;
	@View
	private Checkbox chbTratamiento_veterinario;
	@View
	private Checkbox chbOtra;
	@View
	private Checkbox chbAplicacion_agricola;
	@View
	private Checkbox chbUso_industrial;
	@View
	private Checkbox chbActividad_social;
	@View
	private Textbox tbxOtra_actividad;
	@View
	private Datebox dtbxFecha_exposicion;
	@View
	private Timebox tbHora;
	@View
	private Radiogroup rdbVia_respiratoria;
	@View
	private Textbox tbxOtra_via_exposicion;
	@View
	private Radiogroup rdbEscolaridad;
	@View
	private Radiogroup rdbEmbarazo_actual;
	@View
	private Radiogroup rdbAfiliado_arp;
	@View
	private Textbox tbxNombre_arp;
	@View
	private Textbox tbxCodigo_arp;
	@View
	private Radiogroup rdbEstado_civil;
	@View
	private Radiogroup rdbBrote;
	@View
	private Intbox ibxNumero_brote;
	@View
	private Radiogroup rdbInvestigacion;
	@View
	private Datebox dtbxFecha_investigacion;
	@View
	private Timebox tbHora_investigacion;
	@View
	private Radiogroup rdbAlerta;
	@View
	private Datebox dtbxFecha_alerta;
	// @View private Intbox ibxTelefono_fijo;
	// @View private Textbox tbxNombre_responsable;
	@View
	private Textbox tbxCodigo_medico;
	private final String[] idsExcluyentes = {};

	@View
	private Toolbarbutton btGuardar;

	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;

	@View
	private Toolbarbutton btImprimir;
	@View
	private North north_ficha;

	@Override
	public void init() {
		try {
			listarCombos();
			obtenerAdmision(admision);
			FormularioUtil.inicializarRadiogroupsDefecto(this);

			if (ficha_seleccionada != null) {
				//log.info("consultar ficha-------> " + ficha_seleccionada);
				Ficha_epidemiologia_n9 ficha = new Ficha_epidemiologia_n9();
				ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
				ficha.setCodigo_sucursal(ficha_seleccionada
						.getCodigo_sucursal());
				ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
				ficha = (Ficha_epidemiologia_n9) getServiceLocator()
						.getFicha_epidemiologia_nnService().consultar(ficha);

				//log.info("consultar ficha 9-------> " + ficha);

				mostrarDatos(ficha);

				north_ficha.setVisible(true);
				btImprimir.setVisible(true);

			} else {

				north_ficha.setVisible(false);
				btImprimir.setVisible(false);

			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map
					.get("ficha_seleccionada");
		}
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("código Ficha");
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
			limpiarDatos();
			dtbxFecha_alerta.setValue(null);
			dtbxFecha_investigacion.setValue(null);
			tbHora_investigacion.setValue(null);
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

		tbxNombre_producto
				.setStyle("text-transform:uppercase;background-color:white");
		dtbxFecha_exposicion.setStyle("background-color:white");
		tbHora.setStyle("background-color:white");
		// ibxTelefono_fijo.setStyle("text-transform:uppercase;background-color:white");
		// tbxNombre_responsable.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		if (tbxNombre_producto.getText().trim().equals("")) {
			tbxNombre_producto
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (dtbxFecha_exposicion.getText().trim().equals("")) {
			dtbxFecha_exposicion.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (tbHora.getText().trim().equals("")) {
			tbHora.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		/*
		 * if(ibxTelefono_fijo.getText().trim().equals("")){
		 * ibxTelefono_fijo.setStyle
		 * ("text-transform:uppercase;background-color:#F6BBBE"); valida =
		 * false; } if(tbxNombre_responsable.getText().trim().equals("")){
		 * tbxNombre_responsable
		 * .setStyle("text-transform:uppercase;background-color:#F6BBBE");
		 * valida = false; }
		 */
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

			List<Ficha_epidemiologia_n9> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n9.class, parameters);
			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n9 ficha_epidemiologia_n9 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n9,
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

		final Ficha_epidemiologia_n9 ficha_epidemiologia_n9 = (Ficha_epidemiologia_n9) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n9.getCodigo_ficha()
				+ ""));
		fila.appendChild(new Label(ficha_epidemiologia_n9.getIdentificacion()
				+ ""));
		fila.appendChild(new Label(ficha_epidemiologia_n9.getFecha_creacion()
				+ ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n9);
			}
		});
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public Ficha_epidemiologia_n9 obtenerFichaEpidemiologia() {

		Ficha_epidemiologia_n9 ficha_epidemiologia_n9 = new Ficha_epidemiologia_n9();
		ficha_epidemiologia_n9.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n9
				.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		ficha_epidemiologia_n9.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n9.setIdentificacion(tbxIdentificacion.getValue());
		ficha_epidemiologia_n9.setFecha_creacion(new Timestamp(
				dtbxFecha_creacion.getValue().getTime()));
		ficha_epidemiologia_n9.setCodigo_diagnostico("Z000");
		ficha_epidemiologia_n9.setIntoxicacion(rdbIntoxicacion
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n9
				.setNombre_producto(tbxNombre_producto.getValue());
		ficha_epidemiologia_n9.setTipo_exposicion(rdbTipo_exposicion
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n9.setElaboracion(chbElaboracion.isChecked());
		ficha_epidemiologia_n9.setUso_domiciliario(chbUso_domiciliario
				.isChecked());
		ficha_epidemiologia_n9.setTratamiento_humano(chbTratamiento_humano
				.isChecked());
		ficha_epidemiologia_n9.setDesconocido(chbDesconocido.isChecked());
		ficha_epidemiologia_n9.setAlmacenamiento(chbAlmacenamiento.isChecked());
		ficha_epidemiologia_n9.setUso_salud(chbUso_salud.isChecked());
		ficha_epidemiologia_n9
				.setTratamiento_veterinario(chbTratamiento_veterinario
						.isChecked());
		ficha_epidemiologia_n9.setOtra(chbOtra.isChecked());
		ficha_epidemiologia_n9.setAplicacion_agricola(chbAplicacion_agricola
				.isChecked());
		ficha_epidemiologia_n9.setUso_industrial(chbUso_industrial.isChecked());
		ficha_epidemiologia_n9.setActividad_social(chbActividad_social
				.isChecked());
		ficha_epidemiologia_n9.setOtra_actividad(tbxOtra_actividad.getValue());
		ficha_epidemiologia_n9.setFecha_exposicion(new Timestamp(
				dtbxFecha_exposicion.getValue().getTime()));
		ficha_epidemiologia_n9.setHora(new Timestamp(tbHora.getValue()
				.getTime()));
		ficha_epidemiologia_n9.setVia_respiratoria(rdbVia_respiratoria
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n9.setOtra_via_exposicion(tbxOtra_via_exposicion
				.getValue());
		ficha_epidemiologia_n9.setEscolaridad(rdbEscolaridad.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n9.setEmbarazo_actual(rdbEmbarazo_actual
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n9.setAfiliado_arp(rdbAfiliado_arp
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n9.setNombre_arp(tbxNombre_arp.getValue());
		ficha_epidemiologia_n9.setCodigo_arp(tbxCodigo_arp.getValue());
		ficha_epidemiologia_n9.setEstado_civil(rdbEstado_civil
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n9.setBrote(rdbBrote.getSelectedItem().getValue()
				.toString());
		ficha_epidemiologia_n9
				.setNumero_brote(ibxNumero_brote.getValue() != null ? ibxNumero_brote
						.getValue() + ""
						: "");
		ficha_epidemiologia_n9.setInvestigacion(rdbInvestigacion
				.getSelectedItem().getValue().toString());

		if (dtbxFecha_investigacion.getValue() != null) {
			ficha_epidemiologia_n9.setFecha_investigacion(new Timestamp(
					dtbxFecha_investigacion.getValue().getTime()));

		} else {
			ficha_epidemiologia_n9.setFecha_investigacion(null);
		}

		if (tbHora_investigacion.getValue() != null) {
			ficha_epidemiologia_n9.setHora_investigacion(new Timestamp(
					tbHora_investigacion.getValue().getTime()));

		} else {
			ficha_epidemiologia_n9.setHora_investigacion(null);
		}
		ficha_epidemiologia_n9.setAlerta(rdbAlerta.getSelectedItem().getValue()
				.toString());

		if (dtbxFecha_alerta.getValue() != null) {
			ficha_epidemiologia_n9.setFecha_alerta(new Timestamp(
					dtbxFecha_alerta.getValue().getTime()));

		} else {
			ficha_epidemiologia_n9.setFecha_alerta(null);
		}
		ficha_epidemiologia_n9.setTelefono_fijo("");
		ficha_epidemiologia_n9.setNombre_responsable("");
		ficha_epidemiologia_n9.setCodigo_medico(tbxCodigo_medico.getValue());
		ficha_epidemiologia_n9.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n9.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n9
				.setCreacion_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n9.setDelete_date(null);
		ficha_epidemiologia_n9.setUltimo_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n9.setDelete_user(null);

		return ficha_epidemiologia_n9;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n9 obj) throws Exception {
		Ficha_epidemiologia_n9 ficha_epidemiologia_n9 = (Ficha_epidemiologia_n9) obj;
		try {
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n9.getCodigo_ficha());
			tbxIdentificacion.setValue(ficha_epidemiologia_n9
					.getIdentificacion());

			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
					new String[] {});

			dtbxFecha_creacion.setValue(ficha_epidemiologia_n9
					.getFecha_creacion());
			tbxCodigo_diagnostico.setValue(ficha_epidemiologia_n9
					.getCodigo_diagnostico());
			Utilidades.seleccionarRadio(rdbIntoxicacion,
					ficha_epidemiologia_n9.getIntoxicacion());
			tbxNombre_producto.setValue(ficha_epidemiologia_n9
					.getNombre_producto());
			Utilidades.seleccionarRadio(rdbTipo_exposicion,
					ficha_epidemiologia_n9.getTipo_exposicion());
			chbElaboracion.setChecked(ficha_epidemiologia_n9.getElaboracion());
			chbUso_domiciliario.setChecked(ficha_epidemiologia_n9
					.getUso_domiciliario());
			chbTratamiento_humano.setChecked(ficha_epidemiologia_n9
					.getTratamiento_humano());
			chbDesconocido.setChecked(ficha_epidemiologia_n9.getDesconocido());
			chbAlmacenamiento.setChecked(ficha_epidemiologia_n9
					.getAlmacenamiento());
			chbUso_salud.setChecked(ficha_epidemiologia_n9.getUso_salud());
			chbTratamiento_veterinario.setChecked(ficha_epidemiologia_n9
					.getTratamiento_veterinario());
			chbOtra.setChecked(ficha_epidemiologia_n9.getOtra());
			chbAplicacion_agricola.setChecked(ficha_epidemiologia_n9
					.getAplicacion_agricola());
			chbUso_industrial.setChecked(ficha_epidemiologia_n9
					.getUso_industrial());
			chbActividad_social.setChecked(ficha_epidemiologia_n9
					.getActividad_social());
			tbxOtra_actividad.setValue(ficha_epidemiologia_n9
					.getOtra_actividad());
			dtbxFecha_exposicion.setValue(ficha_epidemiologia_n9
					.getFecha_exposicion());
			tbHora.setValue(ficha_epidemiologia_n9.getHora());
			Utilidades.seleccionarRadio(rdbVia_respiratoria,
					ficha_epidemiologia_n9.getVia_respiratoria());
			tbxOtra_via_exposicion.setValue(ficha_epidemiologia_n9
					.getOtra_via_exposicion());
			Utilidades.seleccionarRadio(rdbEscolaridad,
					ficha_epidemiologia_n9.getEscolaridad());
			Utilidades.seleccionarRadio(rdbEmbarazo_actual,
					ficha_epidemiologia_n9.getEmbarazo_actual());
			Utilidades.seleccionarRadio(rdbAfiliado_arp,
					ficha_epidemiologia_n9.getAfiliado_arp());
			tbxNombre_arp.setValue(ficha_epidemiologia_n9.getNombre_arp());
			tbxCodigo_arp.setValue(ficha_epidemiologia_n9.getCodigo_arp());
			Utilidades.seleccionarRadio(rdbEstado_civil,
					ficha_epidemiologia_n9.getEstado_civil());
			Utilidades.seleccionarRadio(rdbBrote,
					ficha_epidemiologia_n9.getBrote());
			ibxNumero_brote
					.setValue((ficha_epidemiologia_n9.getNumero_brote() != null && !ficha_epidemiologia_n9
							.getNumero_brote().isEmpty()) ? Integer
							.parseInt(ficha_epidemiologia_n9.getNumero_brote())
							: null);
			Utilidades.seleccionarRadio(rdbInvestigacion,
					ficha_epidemiologia_n9.getInvestigacion());
			dtbxFecha_investigacion.setValue(ficha_epidemiologia_n9
					.getFecha_investigacion());
			tbHora_investigacion.setValue(ficha_epidemiologia_n9
					.getHora_investigacion());
			Utilidades.seleccionarRadio(rdbAlerta,
					ficha_epidemiologia_n9.getAlerta());
			dtbxFecha_alerta.setValue(ficha_epidemiologia_n9.getFecha_alerta());
			tbxCodigo_medico
					.setValue(ficha_epidemiologia_n9.getCodigo_medico());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n9 ficha_epidemiologia_n9 = (Ficha_epidemiologia_n9) obj;
		try {
			int result = getServiceLocator().getFicha_epidemiologia_nnService()
					.eliminar(ficha_epidemiologia_n9);
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

	public void mostrar_conRadio(ZKWindow form, Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			String valor = "OT";
			FormularioUtil.mostrarComponentes_conRadiogroup(form, radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
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
		tbxNombrePaciente.setValue(paciente.getNombreCompleto());
		tbxTipo_identificacion.setValue(paciente.getTipo_identificacion());

		tbxAseguradora.setValue(admision.getCodigo_administradora());
		//log.info("PACIENTE" + paciente);
		//log.info("ADMINISTRADORA" + admision.getCodigo_empresa() + " "
				//+ admision.getCodigo_sucursal() + " "
				//+ paciente.getCodigo_administradora());

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(admision.getCodigo_empresa());
		administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
		administradora.setCodigo(admision.getCodigo_administradora());
		administradora = getServiceLocator().getAdministradoraService()
				.consultar(administradora);
		//log.info("administradora" + administradora);

		tbxNombre_aseguradora.setValue(administradora.getNombre());
	}

	@Override
	public Ficha_epidemiologia_n9 consultarDatos(Map<String, Object> map,
			Ficha_epidemiologia ficha_epidemiologia) throws Exception {

//		Ficha_epidemiologia ficha = (Ficha_epidemiologia) ficha_epidemiologia;

		//log.info("-----------------");

		//log.info("map" + map);
		//log.info("ficha" + ficha);

		Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) map
				.get("impresion_diagnostica");
		Cie_epidemiologia cie_epidemiologia = (Cie_epidemiologia) map
				.get("cie_epidemiologia");
		Admision admision = (Admision) map.get("admision");

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", admision.getCodigo_empresa());
		parameters.put("codigo_sucursal", admision.getCodigo_sucursal());
		parameters.put("identificacion", admision.getNro_identificacion());

		if (impresion_diagnostica != null) {
			parameters.put("codigo_historia",
					impresion_diagnostica.getCodigo_historia());
		} else {
			return null;
		}

		if (cie_epidemiologia != null) {
			parameters.put("codigo_diagnostico",
					cie_epidemiologia.getCodigo_cie());
		} else {
			return null;
		}

		getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
				"limit 25 offset 0");

		List<Ficha_epidemiologia_n9> lista_datos = getServiceLocator()
				.getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n9.class, parameters);

		//log.info("lista_datos" + lista_datos);

		if (!lista_datos.isEmpty()) {
			Ficha_epidemiologia_n9 ficha_n9 = lista_datos.get(lista_datos
					.size() - 1);

			return ficha_n9;
		} else {

			return null;
		}
	}

	public void imprimir() throws Exception {

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Ficha_epidemiologia");
		paramRequest.put("empresa", empresa.getCodigo_empresa());
		paramRequest.put("sucursal", sucursal.getCodigo_sucursal());
		paramRequest.put("codigo_ficha", tbxCodigo_ficha.getValue());
		paramRequest.put("ficha_epidemiologia",
				IFichas_epidemiologicas.INTOXICACION);

		//log.info("codigo_ficha" + tbxCodigo_ficha.getValue());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

}