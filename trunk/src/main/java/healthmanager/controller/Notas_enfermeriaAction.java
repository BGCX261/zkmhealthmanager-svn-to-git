/*
 * notas_enfermeriaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Notas_enfermeria;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Notas_enfermeriaService;
import healthmanager.modelo.service.UsuariosService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Notas_enfermeriaAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Notas_enfermeriaAction.class);

	private Notas_enfermeriaService notas_enfermeriaService;

	private UsuariosService usuariosService;

	private String rol_medico;
	private Admision admision_seleccionada;

	private SimpleDateFormat formato_fecha = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm");

	// Componentes //
	@View
	private Listbox lbxFormatos;
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
	private Listbox listboxResultado;

	@View
	private Textbox tbxCodigo_nota;
	@View
	private Datebox dtbxFecha_nota;
	@View
	private Textbox tbxDescripcion_nota;
	@View
	private Textbox tbxCodigo_enfermera;
	@View
	private Textbox tbxInformacion_enfermera;

	@View
	private Listheader columnaAcciones;
	@View
	private Toolbarbutton toolbarbuttonNuevo;

	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		//log.info("rol_medico ===> " + rol_medico);
		if (rol_medico != null && rol_medico.equals("S")) {
			columnaAcciones.setVisible(false);
			toolbarbuttonNuevo.setVisible(false);
		} else {
			columnaAcciones.setVisible(true);
			toolbarbuttonNuevo.setVisible(true);
		}
		listarCombos();
	}

	public void onCreateNotas() throws Exception {
		if (rol_medico != null && rol_medico.equals("S")) {
			buscarDatos();
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey("rol_medico")) {
			rol_medico = (String) map.get("rol_medico");
		}
		admision_seleccionada = (Admision) map.get("admision_seleccionada");
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_nota");
		listitem.setLabel("Codigo de Nota");
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
			limpiarDatos();
			tbxCodigo_enfermera.setValue(usuarios.getCodigo());
			tbxInformacion_enfermera.setValue(usuarios.getNombres() + " "
					+ usuarios.getApellidos());
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;
		String mensaje = "";

		if (tbxDescripcion_nota.getValue().trim().isEmpty()) {
			mensaje = "El campo descripcion de la nota es obligatorio";
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa",
					admision_seleccionada.getCodigo_empresa());
			parameters.put("codigo_sucursal",
					admision_seleccionada.getCodigo_sucursal());
			parameters.put("nro_documento",
					admision_seleccionada.getNro_identificacion());
			parameters.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parameters.put("codigo_nota", value);
			parameters.put("codigo_centro",
					admision_seleccionada.getCodigo_centro());

			notas_enfermeriaService.setLimit("limit 25 offset 0");

			List<Notas_enfermeria> lista_datos = notas_enfermeriaService
					.listar(parameters);
			listboxResultado.getItems().clear();

			for (Notas_enfermeria notas_enfermeria : lista_datos) {
				listboxResultado
						.appendChild(crearFilas(notas_enfermeria, this));
			}

			listboxResultado.setVisible(true);
			listboxResultado.setMold("paging");
			listboxResultado.setPageSize(20);

			listboxResultado.applyProperties();
			listboxResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente)
			throws Exception {
		Listitem fila = new Listitem();
		final Notas_enfermeria notas_enfermeria = (Notas_enfermeria) objeto;
		fila.setValue(notas_enfermeria);
		fila.appendChild(new Listcell(notas_enfermeria.getCodigo_nota()));

		fila.appendChild(new Listcell(formato_fecha.format(notas_enfermeria
				.getFecha_nota())));

		Textbox textbox_descripcion = new Textbox(
				notas_enfermeria.getDescripcion_nota());
		textbox_descripcion.setReadonly(true);
		textbox_descripcion.setHflex("1");
		textbox_descripcion.setRows(4);

		Listcell listcell = new Listcell();
		listcell.appendChild(textbox_descripcion);
		fila.appendChild(listcell);

		Usuarios usuarios_enf = new Usuarios();
		usuarios_enf.setCodigo_empresa(notas_enfermeria.getCodigo_empresa());
		usuarios_enf.setCodigo_sucursal(notas_enfermeria.getCodigo_sucursal());
		usuarios_enf.setCodigo(notas_enfermeria.getCodigo_enfermera());

		usuarios_enf = usuariosService.consultar(usuarios_enf);

		fila.appendChild(Utilidades.obtenerListcell(
				usuarios_enf != null ? (usuarios_enf.getNombres() + " " + usuarios_enf
						.getApellidos()) : notas_enfermeria
						.getCodigo_enfermera(), Textbox.class, true, true));

		fila.setStyle("text-align: justify;nowrap:nowrap");

		Image img = new Image();
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
									eliminarDatos(notas_enfermeria);
									buscarDatos();
								}
							}
						});
			}
		});

		if (usuarios.getCodigo().equals(notas_enfermeria.getCodigo_enfermera())) {
			listcell = new Listcell();
			listcell.appendChild(img);
			fila.appendChild(listcell);
		} else {
			fila.appendChild(new Listcell("---"));
		}

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //
				Notas_enfermeria notas_enfermeria = new Notas_enfermeria();
				notas_enfermeria.setCodigo_empresa(empresa.getCodigo_empresa());
				notas_enfermeria.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				notas_enfermeria.setCodigo_nota(tbxCodigo_nota.getValue());
				notas_enfermeria.setNro_ingreso(admision_seleccionada
						.getNro_ingreso());
				notas_enfermeria.setVia_ingreso(admision_seleccionada
						.getVia_ingreso());
				notas_enfermeria.setNro_documento(admision_seleccionada
						.getNro_identificacion());
				notas_enfermeria.setFecha_nota(new Timestamp(dtbxFecha_nota
						.getValue().getTime()));
				notas_enfermeria.setDescripcion_nota(tbxDescripcion_nota
						.getValue());
				notas_enfermeria.setCodigo_enfermera(tbxCodigo_enfermera
						.getValue());
				notas_enfermeria.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				notas_enfermeria.setCreacion_user(usuarios.getCodigo()
						.toString());
				notas_enfermeria.setCodigo_centro(admision_seleccionada.getCodigo_centro());

				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("notas_enfermeria", notas_enfermeria);
				mapa_datos.put("accion", tbxAccion.getValue());

				notas_enfermeriaService.guardarDatos(mapa_datos);
				accionForm(true, "modificar");
				tbxCodigo_nota.setValue(notas_enfermeria.getCodigo_nota());
				dtbxFecha_nota.setValue(notas_enfermeria.getFecha_nota());

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Notas_enfermeria notas_enfermeria = (Notas_enfermeria) obj;
		try {
			tbxCodigo_nota.setValue(notas_enfermeria.getCodigo_nota());
			dtbxFecha_nota.setValue(notas_enfermeria.getFecha_nota());
			tbxDescripcion_nota
					.setValue(notas_enfermeria.getDescripcion_nota());
			tbxCodigo_enfermera
					.setValue(notas_enfermeria.getCodigo_enfermera());
			Usuarios usuarios_enf = new Usuarios();
			usuarios_enf
					.setCodigo_empresa(notas_enfermeria.getCodigo_empresa());
			usuarios_enf.setCodigo_sucursal(notas_enfermeria
					.getCodigo_sucursal());
			usuarios_enf.setCodigo(notas_enfermeria.getCodigo_enfermera());

			usuarios_enf = usuariosService.consultar(usuarios_enf);

			if (usuarios_enf != null) {
				tbxInformacion_enfermera.setValue(usuarios_enf.getNombres()
						+ " " + usuarios_enf.getApellidos());
			}
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Notas_enfermeria notas_enfermeria = (Notas_enfermeria) obj;
		try {
			int result = notas_enfermeriaService.eliminar(notas_enfermeria);
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

	public void imprimirNotas() {
		try {
			if(!listboxResultado.getSelectedItems().isEmpty()){
				Set<Listitem> listado_seleccionados = listboxResultado.getSelectedItems();
				String codigo_nota_in = "";
				int i = 0;
				for (Listitem listitem : listado_seleccionados) {
					Notas_enfermeria notas_enfermeria = (Notas_enfermeria)listitem.getValue();
					if(i==0){
						codigo_nota_in = "'"+notas_enfermeria.getCodigo_nota()+"'";
					}else{
						codigo_nota_in = codigo_nota_in+",'"+notas_enfermeria.getCodigo_nota()+"'";
					}
					i++;
				}
				
				Map<String, Object> parametros = new HashMap<String, Object>();
				
				parametros.put("name_report", "Notas_enfermeria");
				parametros.put("formato", lbxFormatos.getSelectedItem().getValue()
						.toString());
				parametros.put("codigo_nota_in", codigo_nota_in);
				parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
				parametros.put("nro_documento", admision_seleccionada.getNro_identificacion());
				parametros.put("codigo_centro", admision_seleccionada.getCodigo_centro());

				Component componente = Executions.createComponents(
						"/pages/printInformes.zul", this, parametros);
				final Window window = (Window) componente;
				window.setMode("modal");
				window.setMaximizable(true);
				window.setMaximized(true);
				
			}
		} catch (HealthmanagerException e) {
			MensajesUtil.mensajeError(e, e.getMessage(), this);
		}
	}

}