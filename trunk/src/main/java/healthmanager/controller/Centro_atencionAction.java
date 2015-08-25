/*
 * centro_atencionAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_barrio;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Localidad;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
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
import org.zkoss.zul.impl.XulElement;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Grupo_cuenta;
import contaweb.modelo.bean.Puc;
import contaweb.modelo.bean.Tipo_cuenta;
import healthmanager.modelo.service.GeneralExtraService;

public class Centro_atencionAction extends ZKWindow implements
		WindowRegistrosIMG {

	// private static Logger log =
	// Logger.getLogger(Centro_atencionAction.class);

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
	private Textbox tbxCodigo_centro;
	@View
	private Textbox tbxNombre_centro;
	@View
	private Textbox tbxDireccion;
	@View
	private Textbox tbxTelefonos;
	@View
	private Textbox tbxId_coordinador;
	@View
	private Textbox tbxNombre_coordinador;
	@View
	private Textbox tbxObservaciones;
	private final String[] idsExcluyentes = { "btCancel", "btGuardar",
			"tbxAccion", "btNew" };

	private List<Map<String, Object>> lista_datos_barrios;
	@View
	private Grid gridBarrios;
	@View
	private Rows rowsBarrios;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxCentro_atencion;

	@View
	private Textbox tbxInfo_centro;

	@View
	private Toolbarbutton btnLimpiarCentro;

	@View
	private Checkbox chkLaboratorios;

	@View
	private Checkbox chkTriage_enfermera;

	// private List<Map<String, Object>> lista_datos_prestadores;
	// @View private Grid gridPrestadores;
	// @View private Rows rowsPrestadores;

	// private List<Map<String, Object>> lista_datos_usuarios;
	// @View private Grid gridUsuarios;
	// @View private Rows rowsUsuarios;

	private List<String> lista_seleccionados = new ArrayList<String>();
	private List<String> lista_seleccionados_prestadores = new ArrayList<String>();
	private List<String> lista_seleccionados_usuarios = new ArrayList<String>();

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCuenta_ingreso;
	@View
	private Textbox tbxNomCuenta_ingreso;
	@View
	private Toolbarbutton btnLimpiarCuenta_ingreso;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() throws Exception {
		lista_datos_barrios = new ArrayList<Map<String, Object>>();
		parametrizarBandboxCentro();
		listarCombos();
		accionForm(false, tbxAccion.getText());
		parametrizarResultadoPaginado();
		parametrizarCuenta();
	}

	public void listarCombos() {
		listarParameter();
	}

	private void parametrizarCuenta() {
		BandboxRegistrosIMG<Puc> bandboxRegistrosIMG = new BandboxRegistrosIMG<Puc>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Puc registro) {
				bandbox.setValue(registro.getCodigo_cuenta());
				textboxInformacion.setValue(registro.getNombre());
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem, Puc registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo_cuenta() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCuenta_padre() + ""));
				listitem.appendChild(listcell);

				Tipo_cuenta tp = new Tipo_cuenta();
				tp.setCodigo_empresa(registro.getCodigo_empresa());
				tp.setCodigo_sucursal(registro.getCodigo_sucursal());
				tp.setCodigo(registro.getTipo_cuenta());
				tp = getServiceLocator().getTipo_cuentaService().consultar(tp);

				listcell = new Listcell();
				listcell.appendChild(new Label(tp != null ? tp.getNombre() : ""));
				listitem.appendChild(listcell);

				Grupo_cuenta gp = new Grupo_cuenta();
				gp.setCodigo_empresa(registro.getCodigo_empresa());
				gp.setCodigo_sucursal(registro.getCodigo_sucursal());
				gp.setCodigo(registro.getGrupo_cuenta());
				gp = getServiceLocator().getGrupo_cuentaService().consultar(gp);
				listcell = new Listcell();
				listcell.appendChild(new Label(gp != null ? gp.getNombre() : ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(
						registro.getOculto().equals("S") ? "SI" : "NO"));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getInactivo().equals(
						"S") ? "SI" : "NO"));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getManeja_terceros()
						.equals("S") ? "SI" : "NO"));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Puc> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("codigo_empresa", empresa.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				Centro_atencionAction.this.getServiceLocator().getPucService()
						.setLimit("limit 25 offset 0");
				return Centro_atencionAction.this.getServiceLocator()
						.getPucService().listar(parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}
		};
		/* inyectamos el mismo evento */
		tbxCuenta_ingreso.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxCuenta_ingreso.inicializar(tbxNomCuenta_ingreso,
				btnLimpiarCuenta_ingreso);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_centro");
		listitem.setLabel("Codigo centro");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre_centro");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Centro_atencion> listarResultados(
					Map<String, Object> parametros) {
				List<Centro_atencion> listado = getServiceLocator()
						.getCentro_atencionService().listar(parametros);
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getCentro_atencionService()
						.totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilas(dato, gridResultado);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado, 6);
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
		lista_seleccionados.clear();
		lista_seleccionados_prestadores.clear();
		lista_seleccionados_usuarios.clear();

		lista_datos_barrios.clear();
		// lista_datos_prestadores.clear();
		// lista_datos_usuarios.clear();
		crearFilasBarrios();
		// crearFilasPrestadores();
		// crearFilasUsuarios();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		// tbxCodigo_centro.setStyle("text-transform:uppercase;background-color:white");
		tbxNombre_centro
				.setStyle("text-transform:uppercase;background-color:white");
		bandboxCentro_atencion
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		if (tbxNombre_centro.getText().trim().equals("")) {
			tbxNombre_centro
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

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("value", "%" + value + "%");

			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Centro_atencion centro_atencion = (Centro_atencion) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(centro_atencion.getCodigo_centro() + ""));
		fila.appendChild(new Label(centro_atencion.getNombre_centro() + ""));
		fila.appendChild(new Label(centro_atencion.getDireccion() + ""));
		fila.appendChild(new Label(centro_atencion.getLaboratorios()
				.equals("S") ? "Si" : "No"));
		fila.appendChild(new Label(centro_atencion.getTelefonos() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(centro_atencion);
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
									eliminarDatos(centro_atencion);
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
	public void guardarDatos() throws Exception {
		try {
			// log.info("ejecutando metodo @guardarDatos()");
			if (validarForm()) {
				FormularioUtil.setUpperCase(groupboxEditar);

				Centro_atencion centro_atencion = new Centro_atencion();
				centro_atencion.setCodigo_empresa(empresa.getCodigo_empresa());
				centro_atencion.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				centro_atencion.setCodigo_centro(tbxCodigo_centro.getValue());
				centro_atencion.setNombre_centro(tbxNombre_centro.getValue());
				centro_atencion.setDireccion(tbxDireccion.getValue());
				centro_atencion.setTelefonos(tbxTelefonos.getValue());
				centro_atencion.setId_coordinador(tbxId_coordinador.getValue());
				centro_atencion.setNombre_coordinador(tbxNombre_coordinador
						.getValue());
				centro_atencion.setObservaciones(tbxObservaciones.getValue());
				centro_atencion.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				centro_atencion.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				centro_atencion.setCreacion_user(usuarios.getCodigo()
						.toString());
				centro_atencion.setUltimo_user(usuarios.getCodigo().toString());

				centro_atencion
						.setLaboratorios(chkLaboratorios.isChecked() ? "S"
								: "N");

				Centro_atencion centro_atencion_geo = bandboxCentro_atencion
						.getRegistroSeleccionado();

				if (centro_atencion_geo != null) {
					centro_atencion.setCentro_georeferencia(centro_atencion_geo
							.getCodigo_centro());
				} else {
					centro_atencion.setCentro_georeferencia("");
				}

				centro_atencion.setTriage_enfermera(chkTriage_enfermera
						.isChecked() ? "S" : "N");

				Puc puc = tbxCuenta_ingreso.getRegistroSeleccionado();
				if (puc != null) {
					centro_atencion.setCuenta_ingreso(puc.getCodigo_cuenta());
				} else {
					centro_atencion.setCuenta_ingreso("");
				}

				final Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("centro_atencion", centro_atencion);
				datos.put("lista_detalle_barrios", lista_datos_barrios);
				// datos.put("lista_datos_prestadores",
				// lista_datos_prestadores);
				// datos.put("lista_datos_usuarios", lista_datos_usuarios);
				datos.put("accion", tbxAccion.getText());
				centro_atencion = getServiceLocator()
						.getCentro_atencionService().guardar(datos);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					accionForm(true, "registrar");
				} else {
					mostrarDatos(centro_atencion);
				}

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			Messagebox.show(e.getMessage(), "information", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Centro_atencion centro_atencion = (Centro_atencion) obj;
		try {
			tbxCuenta_ingreso.limpiarSeleccion(true);
			tbxCodigo_centro.setValue(centro_atencion.getCodigo_centro());
			tbxNombre_centro.setValue(centro_atencion.getNombre_centro());
			tbxDireccion.setValue(centro_atencion.getDireccion());
			tbxTelefonos.setValue(centro_atencion.getTelefonos());
			tbxId_coordinador.setValue(centro_atencion.getId_coordinador());
			tbxNombre_coordinador.setValue(centro_atencion
					.getNombre_coordinador());
			tbxObservaciones.setValue(centro_atencion.getObservaciones());

			chkLaboratorios.setChecked(centro_atencion.getLaboratorios()
					.equals("S"));

			Centro_atencion centro_atencion_geo = new Centro_atencion();
			centro_atencion_geo.setCodigo_empresa(codigo_empresa);
			centro_atencion_geo.setCodigo_sucursal(codigo_sucursal);
			centro_atencion_geo.setCodigo_centro(centro_atencion
					.getCentro_georeferencia());
			centro_atencion_geo = getServiceLocator()
					.getCentro_atencionService().consultar(centro_atencion_geo);

			chkTriage_enfermera.setChecked(centro_atencion
					.getTriage_enfermera().equals("S"));

			if (centro_atencion_geo != null) {
				bandboxCentro_atencion.seleccionarRegistro(centro_atencion_geo,
						centro_atencion_geo.getCodigo_centro(),
						centro_atencion_geo.getNombre_centro());
			}

			lista_datos_barrios.clear();
			lista_seleccionados.clear();

			// Barrios //
			Map<String, Object> paramCentro_barrio = new HashMap<String, Object>();
			paramCentro_barrio.put("codigo_empresa",
					centro_atencion.getCodigo_empresa());
			paramCentro_barrio.put("codigo_sucursal",
					centro_atencion.getCodigo_sucursal());
			paramCentro_barrio.put("codigo_centro",
					centro_atencion.getCodigo_centro());

			List<Centro_barrio> listaCentro_barrios = getServiceLocator()
					.getServicio(GeneralExtraService.class).listar(
							Centro_barrio.class, paramCentro_barrio);
			for (Centro_barrio centro_barrio : listaCentro_barrios) {
				Barrio barrio = new Barrio();
				barrio.setCodigo_barrio(centro_barrio.getCodigo_barrio());
				barrio = getServiceLocator().getBarrioService().consultar(
						barrio);
				Map<String, Object> bean = cargarBarrio(barrio);
				lista_datos_barrios.add(bean);

				lista_seleccionados.add(barrio.toString());
			}
			crearFilasBarrios();

			if (centro_atencion.getCuenta_ingreso() != null
					&& !centro_atencion.getCuenta_ingreso().isEmpty()) {
				Puc puc = new Puc();
				puc.setCodigo_empresa(codigo_empresa);
				puc.setCodigo_sucursal(codigo_sucursal);
				puc.setCodigo_cuenta(centro_atencion.getCuenta_ingreso());
				puc = getServiceLocator().getPucService().consultar(puc);
				tbxCuenta_ingreso.seleccionarRegistro(puc,
						puc != null ? puc.getCodigo_cuenta() : "",
						puc != null ? puc.getNombre() : "");
			}

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Centro_atencion centro_atencion = (Centro_atencion) obj;
		try {
			int result = getServiceLocator().getCentro_atencionService()
					.eliminar(centro_atencion);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeAlerta(
							"Advertencia",
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos");
		}
	}

	public void abrirWindowBarrios() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String columnas = "Codigo#100px|Barrio|Localidad#180px|Dpto  Mun#210px";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"CONSULTAR BARRIOS", Paquetes.HEALTHMANAGER,
				"BarrioDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados);
	}

	public void abrirWindowPrestadores() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String columnas = "Codigo#100px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"CONSULTAR PRESTADORES", Paquetes.HEALTHMANAGER,
				"PrestadoresDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_prestadores);
	}

	public void abrirWindowUsuarios() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("no_rol_medico", "");
		String columnas = "Codigo#100px|Nombre";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"CONSULTAR USUARIOS", Paquetes.HEALTHMANAGER,
				"UsuariosDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_usuarios);
	}

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			if (registro instanceof Barrio) {
				onSeleccionarRegistroBarrio((Barrio) registro);
			} else if (registro instanceof Prestadores) {
				onSeleccionarRegistroPrestadores((Prestadores) registro);
			} else if (registro instanceof Usuarios) {
				onSeleccionarRegistroUsuarios((Usuarios) registro);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void onSeleccionarRegistroBarrio(Barrio barrio) {
		Map<String, Object> bean = cargarBarrio(barrio);
		adicionarDetalleListaBarrio(bean);
	}

	public void onSeleccionarRegistroPrestadores(Prestadores prestadores) {

	}

	public void onSeleccionarRegistroUsuarios(Usuarios usuarios) {

	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if (registro instanceof Barrio) {
			return celdasListitemBarrio((Barrio) registro);
		} else if (registro instanceof Prestadores) {
			return celdasListitemPrestadores((Prestadores) registro);
		} else if (registro instanceof Usuarios) {
			return celdasListitemUsuarios((Usuarios) registro);
		}

		return null;
	}

	public Object[] celdasListitemBarrio(Barrio barrio) {
		Map<String, Object> bean = cargarBarrio(barrio);

		Localidad localidad = (Localidad) bean.get("localidad");
		Municipios municipios = (Municipios) bean.get("municipios");
		Departamentos departamentos = (Departamentos) bean.get("departamentos");

		return new Object[] {
				barrio.getCodigo_barrio(),
				barrio.getBarrio(),
				(localidad != null ? localidad.getLocalidad() : ""),
				(municipios != null ? municipios.getNombre() : "")
						+ " "
						+ (departamentos != null ? departamentos.getNombre()
								: "") };

	}

	public Object[] celdasListitemPrestadores(Prestadores prestadores) {

		return new Object[] { prestadores.getNro_identificacion(),
				prestadores.getNombres() + " " + prestadores.getApellidos() };

	}

	public Object[] celdasListitemUsuarios(Usuarios user) {
		return new Object[] { user.getCodigo(),
				user.getNombres() + " " + user.getApellidos() };

	}

	public void adicionarDetalleListaBarrio(Map<String, Object> bean) {
		try {
			lista_datos_barrios.add(bean);
			crearFilasBarrios();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// public void adicionarDetalleListaPrestadores(Map<String, Object> bean){
	// try {
	// lista_datos_prestadores.add(bean);
	// crearFilasPrestadores();
	// } catch (Exception e) {
	// MensajesUtil.mensajeError(e, "", this);
	// }
	// }
	//
	// public void adicionarDetalleListaUsuarios(Map<String, Object> bean){
	// try {
	// lista_datos_usuarios.add(bean);
	// crearFilasUsuarios();
	// } catch (Exception e) {
	// MensajesUtil.mensajeError(e, "", this);
	// }
	// }

	// Este metodo genera las filas nuevamente //
	public void crearFilasBarrios() {
		rowsBarrios.getChildren().clear();
		for (int j = 0; j < lista_datos_barrios.size(); j++) {
			Map<String, Object> bean = lista_datos_barrios.get(j);
			crearFilaDetalleBarrio(bean, j);
		}
	}

	// Metodo para crear la grilla cuando se consulte los servicios cargados //
	public void crearFilaDetalleBarrio(Map<String, Object> bean, int j) {

		final int index = j;
		Barrio barrio = (Barrio) bean.get("barrio");
		Localidad localidad = (Localidad) bean.get("localidad");
		Municipios municipios = (Municipios) bean.get("municipios");
		Departamentos departamentos = (Departamentos) bean.get("departamentos");

		String codigo_barrio = (barrio != null ? barrio.getCodigo_barrio() : "");
		String nombre_barrio = (barrio != null ? barrio.getBarrio() : "");
		String nombre_localidad = (localidad != null ? localidad.getLocalidad()
				: "");
		String nombre_mun = (municipios != null ? municipios.getNombre() : "");
		String nombre_dpto = (departamentos != null ? departamentos.getNombre()
				: "");

		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// Columna codigo barrio //
		Cell cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxCodigo_barrio = new Textbox(codigo_barrio);
		tbxCodigo_barrio.setInplace(true);
		tbxCodigo_barrio.setId("codigo_barrio_" + j);
		tbxCodigo_barrio.setHflex("1");
		tbxCodigo_barrio.setReadonly(true);
		cell.appendChild(tbxCodigo_barrio);
		fila.appendChild(cell);

		// Columna nombre barrio //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_barrio = new Textbox(nombre_barrio);
		tbxNombre_barrio.setInplace(true);
		tbxNombre_barrio.setId("nombre_barrio_" + j);
		tbxNombre_barrio.setHflex("1");
		tbxNombre_barrio.setReadonly(true);
		cell.appendChild(tbxNombre_barrio);
		fila.appendChild(cell);

		// Columna localidad //
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_localidad = new Textbox(nombre_localidad);
		tbxNombre_localidad.setInplace(true);
		tbxNombre_localidad.setId("nombre_localidad_" + j);
		tbxNombre_localidad.setHflex("1");
		tbxNombre_localidad.setReadonly(true);
		cell.appendChild(tbxNombre_localidad);
		fila.appendChild(cell);

		// Columna mun dpto//
		cell = new Cell();
		cell.setAlign("left");
		final Textbox tbxNombre_procedimiento = new Textbox(nombre_mun + " - "
				+ nombre_dpto);
		tbxNombre_procedimiento.setInplace(true);
		tbxNombre_procedimiento.setId("nombre_mun_" + j);
		tbxNombre_procedimiento.setHflex("1");
		tbxNombre_procedimiento.setReadonly(true);
		cell.appendChild(tbxNombre_procedimiento);
		fila.appendChild(cell);

		// Columna borrar //
		cell = new Cell();
		cell.setAlign("left");
		cell.setValign("top");
		Image img = new Image("/images/borrar.gif");
		img.setId("img_" + j);
		img.setTooltiptext("Quitar registro");
		img.setStyle("cursor:pointer");
		cell.appendChild(img);
		fila.appendChild(cell);

		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {

				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									lista_datos_barrios.remove(index);
									crearFilasBarrios();
								}
							}
						});
			}
		});

		fila.setId("fila_" + j);

		rowsBarrios.appendChild(fila);

		gridBarrios.setVisible(true);
		gridBarrios.setMold("paging");
		gridBarrios.setPageSize(20);
		gridBarrios.applyProperties();
		gridBarrios.invalidate();
	}

	// Este metodo genera las filas nuevamente //
	// public void crearFilasPrestadores() {
	// rowsPrestadores.getChildren().clear();
	// for (int j = 0; j < lista_datos_prestadores.size(); j++) {
	// Map<String, Object> bean = lista_datos_prestadores.get(j);
	// crearFilaDetallePrestadores(bean, j);
	// }
	// }

	// Metodo para crear la grilla cuando se consulte los servicios cargados //
	// public void crearFilaDetallePrestadores(Map<String, Object> bean, int j){
	//
	// final int index = j;
	// Prestadores prestadores = (Prestadores) bean.get("prestadores");
	//
	// String codigo_user =
	// (prestadores!=null?prestadores.getNro_identificacion():"");
	// String nombre_user =
	// (prestadores!=null?prestadores.getNombres()+" "+prestadores.getApellidos():"");
	//
	// final Row fila = new Row();
	// fila.setStyle("text-align: center;nowrap:nowrap");
	//
	// // Columna codigo barrio //
	// Cell cell = new Cell();
	// cell.setAlign("left");
	// final Textbox tbxCodigo_barrio = new Textbox(codigo_user);
	// tbxCodigo_barrio.setInplace(true);
	// tbxCodigo_barrio.setId("codigo_prestador_" + j);
	// tbxCodigo_barrio.setHflex("1");
	// tbxCodigo_barrio.setReadonly(true);
	// cell.appendChild(tbxCodigo_barrio);
	// fila.appendChild(cell);
	//
	// // Columna nombre barrio //
	// cell = new Cell();
	// cell.setAlign("left");
	// final Textbox tbxNombre_barrio = new Textbox(nombre_user);
	// tbxNombre_barrio.setInplace(true);
	// tbxNombre_barrio.setId("nombre_prestador_" + j);
	// tbxNombre_barrio.setHflex("1");
	// tbxNombre_barrio.setReadonly(true);
	// cell.appendChild(tbxNombre_barrio);
	// fila.appendChild(cell);
	//
	// // Columna borrar //
	// cell = new Cell();
	// cell.setAlign("left");
	// cell.setValign("top");
	// Image img = new Image("/images/borrar.gif");
	// img.setId("img_prestador_" + j);
	// img.setTooltiptext("Quitar registro");
	// img.setStyle("cursor:pointer");
	// cell.appendChild(img);
	// fila.appendChild(cell);
	//
	// img.addEventListener("onClick", new EventListener() {
	// @Override
	// public void onEvent(Event event) throws Exception {
	//
	// Messagebox.show(
	// "Esta seguro que desea eliminar este registro? ",
	// "Eliminar Registro", Messagebox.YES + Messagebox.NO,
	// Messagebox.QUESTION,
	// new org.zkoss.zk.ui.event.EventListener() {
	// public void onEvent(Event event) throws Exception {
	// if ("onYes".equals(event.getName())) {
	// // do the thing
	// lista_datos_prestadores.remove(index);
	// crearFilasPrestadores();
	// }
	// }
	// });
	// }
	// });
	//
	// fila.setId("fila_prestadores_" + j);
	//
	// rowsPrestadores.appendChild(fila);
	//
	// gridPrestadores.setVisible(true);
	// gridPrestadores.setMold("paging");
	// gridPrestadores.setPageSize(20);
	// gridPrestadores.applyProperties();
	// gridPrestadores.invalidate();
	// }

	// Este metodo genera las filas nuevamente //
	// public void crearFilasUsuarios() {
	// rowsUsuarios.getChildren().clear();
	// for (int j = 0; j < lista_datos_usuarios.size(); j++) {
	// Map<String, Object> bean = lista_datos_usuarios.get(j);
	// crearFilaDetalleUsuarios(bean, j);
	// }
	// }

	// Metodo para crear la grilla cuando se consulte los servicios cargados //
	// public void crearFilaDetalleUsuarios(Map<String, Object> bean, int j){
	//
	// final int index = j;
	// Usuarios user = (Usuarios) bean.get("usuarios");
	//
	// String codigo_user = (user!=null?user.getCodigo():"");
	// String nombre_user =
	// (user!=null?user.getNombres()+" "+user.getApellidos():"");
	//
	// final Row fila = new Row();
	// fila.setStyle("text-align: center;nowrap:nowrap");
	//
	// // Columna codigo barrio //
	// Cell cell = new Cell();
	// cell.setAlign("left");
	// final Textbox tbxCodigo_barrio = new Textbox(codigo_user);
	// tbxCodigo_barrio.setInplace(true);
	// tbxCodigo_barrio.setId("codigo_usuario_" + j);
	// tbxCodigo_barrio.setHflex("1");
	// tbxCodigo_barrio.setReadonly(true);
	// cell.appendChild(tbxCodigo_barrio);
	// fila.appendChild(cell);
	//
	// // Columna nombre barrio //
	// cell = new Cell();
	// cell.setAlign("left");
	// final Textbox tbxNombre_barrio = new Textbox(nombre_user);
	// tbxNombre_barrio.setInplace(true);
	// tbxNombre_barrio.setId("nombre_usuario_" + j);
	// tbxNombre_barrio.setHflex("1");
	// tbxNombre_barrio.setReadonly(true);
	// cell.appendChild(tbxNombre_barrio);
	// fila.appendChild(cell);
	//
	// // Columna borrar //
	// cell = new Cell();
	// cell.setAlign("left");
	// cell.setValign("top");
	// Image img = new Image("/images/borrar.gif");
	// img.setId("img_usuario_" + j);
	// img.setTooltiptext("Quitar registro");
	// img.setStyle("cursor:pointer");
	// cell.appendChild(img);
	// fila.appendChild(cell);
	//
	// img.addEventListener("onClick", new EventListener() {
	// @Override
	// public void onEvent(Event event) throws Exception {
	//
	// Messagebox.show(
	// "Esta seguro que desea eliminar este registro? ",
	// "Eliminar Registro", Messagebox.YES + Messagebox.NO,
	// Messagebox.QUESTION,
	// new org.zkoss.zk.ui.event.EventListener() {
	// public void onEvent(Event event) throws Exception {
	// if ("onYes".equals(event.getName())) {
	// // do the thing
	// lista_datos_usuarios.remove(index);
	// crearFilasUsuarios();
	// }
	// }
	// });
	// }
	// });
	//
	// fila.setId("fila_usuario_" + j);
	//
	// rowsUsuarios.appendChild(fila);
	//
	// gridUsuarios.setVisible(true);
	// gridUsuarios.setMold("paging");
	// gridUsuarios.setPageSize(20);
	// gridUsuarios.applyProperties();
	// gridUsuarios.invalidate();
	// }

	private Map<String, Object> cargarBarrio(Barrio barrio) {
		Map<String, Object> bean = new HashMap<String, Object>();

		Localidad localidad = new Localidad();
		localidad.setCodigo_localidad(barrio != null ? barrio
				.getCodigo_localidad() : "");
		localidad = getServiceLocator().getServicio(GeneralExtraService.class)
				.consultar(localidad);

		Centro_barrio centro_barrio = new Centro_barrio();
		centro_barrio.setCodigo_barrio(barrio != null ? barrio
				.getCodigo_barrio() : "");

		Municipios municipios = new Municipios();
		municipios.setCodigo((localidad != null ? localidad.getCodigo() : ""));
		municipios.setCoddep((localidad != null ? localidad.getCoddep() : ""));
		municipios = getServiceLocator().getMunicipiosService().consultar(
				municipios);

		Departamentos departamentos = new Departamentos();
		departamentos
				.setCodigo((localidad != null ? localidad.getCoddep() : ""));
		departamentos = getServiceLocator().getDepartamentosService()
				.consultar(departamentos);

		bean.put("barrio", barrio);
		bean.put("localidad", localidad);
		bean.put("centro_barrio", centro_barrio);
		bean.put("municipios", municipios);
		bean.put("departamentos", departamentos);

		return bean;

	}

	private void parametrizarBandboxCentro() {
		bandboxCentro_atencion.inicializar(tbxInfo_centro, btnLimpiarCentro);
		bandboxCentro_atencion
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {
						listitem.appendChild(new Listcell(registro
								.getCodigo_centro()));
						listitem.appendChild(new Listcell(registro
								.getNombre_centro()));
					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						return getServiceLocator().getCentro_atencionService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Centro_atencion registro) {
						bandbox.setValue(registro.getCodigo_centro());
						textboxInformacion.setValue(registro.getNombre_centro());
						// textboxInformacion.setValue(registro.getNombre_centro());
						((BandboxRegistrosMacro) bandbox).setObject(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						((BandboxRegistrosMacro) bandbox).setObject(null);
					}
				});
	}

}