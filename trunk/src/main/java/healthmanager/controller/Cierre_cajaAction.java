/*
 * ficha_epidemiologia_n11Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Cierre_caja;
import healthmanager.modelo.bean.Detalle_cierre_caja;
import healthmanager.modelo.bean.Recibo_caja;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.UsuariosService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.runtime.Log;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IRoles;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.util.Parametros_busqueda;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Cierre_cajaAction extends ZKWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1290109334090356435L;

	public static Logger log = Logger.getLogger(Cierre_cajaAction.class);

	private Toolbarbutton btnLimpiarPaciente;
	@View
	private Textbox tbxNomUsuario;
	@View
	private Listbox listboxRecibos;
	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxUsuarios;
	@View
	private Textbox tbxCodigo_usuario;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Grid gridResultado;
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Rows rowsResultado;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Textbox tbxAccion;
	@View
	private Toolbarbutton btGuardar;
	@View
	private Datebox dtbxFecha_fin;
	@View
	private Datebox dtbxFecha_inicio;
	@WireVariable
	private UsuariosService usuarioService;
	@WireVariable
	private ElementoService elementoService;

	private final String[] idsExcluyentes = { "btCancel", "btGuardar",
			"tbxAccion", "btNew", "dtbxFecha_ingreso",
			"lbxVia_ingreso_defecto", };

	private Long id_current;
	private Long id_current1;

	@Override
	public void init() throws Exception {
		listarCombos();
		parametrizarBandbox();
		accionForm(true, "registrar");
	}

	public void cargar_usuario() {
		if (rol_usuario.equals(IRoles.ADMINISTRADOR)) {
			bandboxUsuarios.setVisible(true);
		} else {
			bandboxUsuarios.setVisible(false);
			tbxCodigo_usuario.setValue(usuarios.getCodigo());
			tbxNomUsuario.setValue(usuarios.getNombres() + " "
					+ usuarios.getApellidos());

		}
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();
		Listitem listitem = new Listitem();
		listitem.setValue("codigo");
		listitem.setLabel("codigo");
		lbxParameter.appendChild(listitem);
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	private void parametrizarBandbox() {
		parametrizarBandboxUsuario();
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			limpiarDatos();
			cargar_usuario();
			buscarDatos();

		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		// btFurips.setVisible(false);
		btGuardar.setDisabled(false);
		bandboxUsuarios.setDisabled(false);
		bandboxUsuarios.setReadonly(false);
		bandboxUsuarios.setFocus(true);
		bandboxUsuarios.limpiarSeleccion(true);
		listboxRecibos.getItems().clear();
		id_current = null;
		id_current1 = null;
	}

	/**
	 * En este metodo se parametriza todos los bandbox
	 *
	 */

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			List<Cierre_caja> lista_datos = getServiceLocator().getServicio(
					GeneralExtraService.class).listar(Cierre_caja.class,
					parameters);
			rowsResultado.getChildren().clear();
			for (Cierre_caja cierre_caja : lista_datos) {
				rowsResultado.appendChild(crearFilas(cierre_caja, this));
			}
			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);
			gridResultado.applyProperties();
			gridResultado.invalidate();

			// resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();
		final Cierre_caja cierre_caja = (Cierre_caja) objeto;
		Hbox hbox = new Hbox();
		Space space = new Space();
		Usuarios usuario = new Usuarios();
		usuario.setCodigo(cierre_caja.getCodigo_usuario());
		usuario.setCodigo_empresa(empresa.getCodigo_empresa());
		usuario.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		usuario = getServiceLocator().getUsuariosService().consultar(usuario);
		fila.appendChild(new Label(usuario.getNombres() + " "
				+ usuario.getApellidos()));
		fila.appendChild(new Label(cierre_caja.getFecha_inicio() + ""));
		fila.appendChild(new Label(cierre_caja.getFecha_fin() + ""));

		Centro_atencion centro_atencion = new Centro_atencion();
		centro_atencion.setCodigo_centro(cierre_caja.getCodigo_centro());
		centro_atencion.setCodigo_empresa(empresa.getCodigo_empresa());
		centro_atencion.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		centro_atencion = getServiceLocator().getCentro_atencionService()
				.consultar(centro_atencion);
		fila.appendChild(new Label(centro_atencion.getNombre_centro()));
		fila.appendChild(new Label(cierre_caja.getValor_total() + ""));
		// fila.appendChild(new Label(tipo));
		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(cierre_caja);
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
									eliminarDatos(cierre_caja);
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

	public void eliminarDatos(Object obj) throws Exception {
		Cierre_caja cierre_caja = (Cierre_caja) obj;
		try {
			int result = getServiceLocator().getServicio(
					GeneralExtraService.class).eliminar(cierre_caja);
			if (result == 0) {
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		}
	}

	// Metodo para validar campos del formulario //

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {

				// Cargamos los componentes //

				Cierre_caja cierre_caja = new Cierre_caja();
				cierre_caja.setId(id_current);
				cierre_caja.setCodigo_centro(centro_atencion_session
						.getCodigo_centro());
				cierre_caja.setCodigo_empresa(empresa.getCodigo_empresa());
				cierre_caja.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				cierre_caja.setCodigo_usuario(tbxCodigo_usuario.getValue());
				cierre_caja.setFecha_fin(new Timestamp(dtbxFecha_fin.getValue()
						.getTime()));
				cierre_caja.setFecha_inicio(new Timestamp(dtbxFecha_inicio
						.getValue().getTime()));
				cierre_caja.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				cierre_caja.setUltimo_user(usuarios.getCodigo().toString());
				// cierre_caja.setCodigo_usuario(usuarios.getCodigo());
				cierre_caja.setCreacion_user(usuarios.getCodigo().toString());
				cierre_caja.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				Detalle_cierre_caja detalle_cierre_caja = new Detalle_cierre_caja();
				double valor_total = 0.0;
				Usuarios usuario = new Usuarios();

				if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
					for (Listitem listitem : listboxRecibos.getSelectedItems()) {
						Recibo_caja recibo_caja = (Recibo_caja) listitem
								.getValue();
						valor_total = valor_total
								+ recibo_caja.getValor_total();

					}
					cierre_caja.setValor_total(valor_total);
					getServiceLocator().getServicio(GeneralExtraService.class)
							.crear(cierre_caja);
					cierre_caja = getServiceLocator().getServicio(
							GeneralExtraService.class).consultar(cierre_caja);
					for (Listitem listitem : listboxRecibos.getSelectedItems()) {
						Recibo_caja recibo_caja = (Recibo_caja) listitem
								.getValue();
						usuario.setCodigo(cierre_caja.getCodigo_usuario());
						detalle_cierre_caja.setId(id_current1);
						detalle_cierre_caja.setCreacion_date(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						usuario.setCodigo_empresa(empresa.getCodigo_empresa());
						usuario.setCodigo_sucursal(sucursal
								.getCodigo_sucursal());
						usuario = getServiceLocator().getUsuariosService()
								.consultar(usuario);
						detalle_cierre_caja.setCreacion_user(usuario
								.getCodigo().toString());
						detalle_cierre_caja.setUltimo_update(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						detalle_cierre_caja.setUltimo_user(usuarios.getCodigo()
								.toString());

						// actualiza recibo caaja
						recibo_caja.setId_cierre_caja(cierre_caja.getId());
						getServiceLocator().getServicio(
								GeneralExtraService.class).actualizar(
								recibo_caja);
						getServiceLocator().getServicio(
								GeneralExtraService.class).crear(
								detalle_cierre_caja);
					}
				} else {
					int result = getServiceLocator().getServicio(
							GeneralExtraService.class).actualizar(cierre_caja);
					result = getServiceLocator().getServicio(
							GeneralExtraService.class).actualizar(
							detalle_cierre_caja);

					if (result == 0) {
						throw new ValidacionRunTimeException(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				tbxAccion.setValue("modificar");
				id_current = cierre_caja.getId();
				id_current1 = detalle_cierre_caja.getId();

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");

				listarRecibos_caja();
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public boolean validarForm() throws Exception {
		tbxCodigo_usuario.setStyle("background-color:white;text-transform:uppercase");
		boolean valida = true;
		for (Listitem listitem : listboxRecibos.getSelectedItems()) {
			Recibo_caja recibo_caja = (Recibo_caja) listitem.getValue();
					
			if (!recibo_caja.getEstado().equals("aprovado")) {
				MensajesUtil.mensajeAlerta(usuarios.getNombres()
						+ " recuerde que...",
						"Debe seleccionar recibo de caja aprovados");
				valida = false;
			}
		
		}

		if (listboxRecibos.getSelectedItem()== null) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"No ha seleccionado ningun recibo de caja");
			valida = false;
		}

		if (tbxCodigo_usuario.getText().trim().equals("")) {
			tbxCodigo_usuario
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (dtbxFecha_fin.getText().trim().equals("")) {
			dtbxFecha_fin
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (dtbxFecha_inicio.getText().trim().equals("")) {
			dtbxFecha_inicio
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}
		return valida;
	}

	public void mostrarDatos(Object obj) throws Exception {
		bandboxUsuarios.setDisabled(true);
		Cierre_caja cierre_caja = (Cierre_caja) obj;
		try {
			Long id = cierre_caja.getId();
			// Long id1 = detalle_cierre_caja.getId();
			this.id_current = id;
			// this.id_current1 = id1;
			tbxCodigo_usuario.setValue(cierre_caja.getCodigo_usuario());
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo(cierre_caja.getCodigo_usuario());
			usuarios.setCodigo_empresa(empresa.getCodigo_empresa());
			usuarios.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			usuarios = getServiceLocator().getUsuariosService().consultar(
					usuarios);
			tbxNomUsuario.setValue(usuarios.getNombres() + " "
					+ usuarios.getApellidos());
			dtbxFecha_inicio.setValue(cierre_caja.getFecha_inicio());
			dtbxFecha_fin.setValue(cierre_caja.getFecha_fin());
			// lbxVias_ingreso.setValue(reg_historias_manuales.getFecha_historia());
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void listarRecibos_caja() {
		Listbox listbox = listboxRecibos;
		listbox.getItems().clear();
		Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters.put("codigo_empresa", codigo_empresa);
		// parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("creacion_user", tbxCodigo_usuario.getValue());
		parameters.put("recibos_abiertos", "");

		List<Recibo_caja> lista_datos = getServiceLocator().getServicio(
				GeneralExtraService.class)
				.listar(Recibo_caja.class, parameters);

		if (lista_datos.isEmpty()) {

			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"No existen recibos de caja para este usuario");

		} else {

			Listitem listitem;
			for (Recibo_caja recibo_caja : lista_datos) {

				listitem = new Listitem();
				listitem.setValue(recibo_caja);
				listitem.appendChild(new Listcell());
				listitem.appendChild(new Listcell(recibo_caja.getId() + ""));
				listitem.appendChild(new Listcell(recibo_caja.getValor_total()
						+ ""));
				listitem.appendChild(new Listcell(recibo_caja.getEstado() + ""));
				listbox.appendChild(listitem);
				
				// Desabilitar recibos no aprovados
				String estado = recibo_caja.getEstado();
				if (!estado.equals("aprovado")) {
					listitem.setDisabled(true);
					listitem.setSelected(false);
					
				}
				listboxRecibos.setVisible(true);
				listboxRecibos.setMold("paging");
				listboxRecibos.setPageSize(20);
				listboxRecibos.applyProperties();
				listboxRecibos.invalidate();

				
			}
		}
	}


	// mostrar usuario banbox
	public void listarUsuario(Listbox listbox) {

		listbox.getItems().clear();

		Listitem listitem;
		

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);

		List<Recibo_caja> lista_datos = getServiceLocator().getServicio(
				GeneralExtraService.class)
				.listar(Recibo_caja.class, parameters);

		for (Recibo_caja recibo_caja : lista_datos) {
			listitem = new Listitem();
			/*
			 * Este parametro me permite habilitar la carga de conratos
			 * capitados por medio de un facturador
			 */

			listitem.setValue(recibo_caja);
			listitem.appendChild(new Listcell());
			listitem.appendChild(new Listcell(recibo_caja.getCodigo() + ""));
			listitem.appendChild(new Listcell(recibo_caja.getValor_total() + ""));
			listitem.appendChild(new Listcell(recibo_caja.getEstado() + ""));

			listbox.appendChild(listitem);
		}
	}

	private void parametrizarBandboxUsuario() {
		bandboxUsuarios.setTrabajarConParametros(true);
		bandboxUsuarios.inicializar(tbxNomUsuario, btnLimpiarPaciente);
		bandboxUsuarios.setMostrarVacio(true);
		bandboxUsuarios
				.setMensajeVacio("Este usuario no se encuentra en la base de datos..!!");
		bandboxUsuarios
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Usuarios>() {

					@Override
					public void renderListitem(Listitem listitem,
							Usuarios registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro.getCodigo()
								+ ""));
						listitem.appendChild(listcell);
						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombres()
								+ ""));
						listitem.appendChild(listcell);
						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellidos()
								+ ""));
						listitem.appendChild(listcell);
						listitem.appendChild(listcell);
					}

					@Override
					public List<Usuarios> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {
						String parameter = (String) bandboxUsuarios
								.getParametroSeleccionado();
						if (!parameter
								.equals(BandboxRegistrosMacro.PARAMETRO_CUALQUIERA)) {
							parametros.put("parameter", parameter);
							parametros.put("value", "%" + valorBusqueda + "%");
						} else {
							parametros.put("parametroTodo", valorBusqueda);
						}
						return usuarioService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Usuarios registro) {

						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNombres());
						tbxCodigo_usuario.setValue(registro.getCodigo());

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});

		bandboxUsuarios
				.setSeleccionarItem(new BandboxRegistrosIMG.ISeleccionarItem() {
					public void accion(Object registro) {
					}
				});
		List<Parametros_busqueda> listado_parametros = new ArrayList<Parametros_busqueda>();
		listado_parametros.add(new Parametros_busqueda("codigo", "codigo"));
		bandboxUsuarios.listarParametros(listado_parametros, true);
	}
}
