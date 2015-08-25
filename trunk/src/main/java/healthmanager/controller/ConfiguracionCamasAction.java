/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Cama;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Habitacion;
import healthmanager.modelo.bean.Pabellon;
import healthmanager.modelo.service.CamaService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.HabitacionService;
import healthmanager.modelo.service.PabellonService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

/**
 * @author Usuario
 * 
 */
public class ConfiguracionCamasAction extends GeneralComposer {

	// private static Logger log = Logger
	// .getLogger(ConfiguracionCamasAction.class);
	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private PabellonService pabellonService;
	@WireVariable
	private HabitacionService habitacionService;
	@WireVariable
	private CamaService camaService;
	

	private @View Tree treeEstancias;
	private @View Auxheader auxHeaderTitulo;
	private @View Rows rowsMosaico;

	private @View Radiogroup radiogroupModulos;

	private @View Textbox tbxTipo;
	private @View Textbox tbxCodigo_atencion;
	private @View Textbox tbxCodigo_pabellon;
	private @View Textbox tbxCodigo_habitacion;
	private @View Textbox tbxCodigo_cama;

	private @View Window winMensaje;
	private Textbox tbxNombre;
	private Textbox tbxAccion;
	private Listbox lbxEstancia;

	private Hbox hboxEstancia;

	private String tipo_atencion = null;
	private boolean seleccionar = false;
	private boolean editar = true;

	private @View Button btNuevo;
	private @View Button btEditar;
	private @View Button btEliminar;
	private @View Button btAsignar;

	@Override
	public void init() throws Exception {

		tbxNombre = (Textbox) winMensaje.getFellow("tbxNombre");
		lbxEstancia = (Listbox) winMensaje.getFellow("lbxEstancia");
		hboxEstancia = (Hbox) winMensaje.getFellow("hboxEstancia");
		tbxAccion = (Textbox) winMensaje.getFellow("tbxAccion");

		listarCombos();

		Map parametrosAction = Executions.getCurrent().getArg();
		if (parametrosAction == null) {
			parametrosAction = new HashMap();
		}

		if (parametrosAction.containsKey("tipo_atencion")) {
			tipo_atencion = (String) parametrosAction.get("tipo_atencion");
		}

		if (parametrosAction.containsKey("seleccionar")) {
			seleccionar = (Boolean) parametrosAction.get("seleccionar");
		}

		if (parametrosAction.containsKey("editar")) {
			editar = (Boolean) parametrosAction.get("editar");
		}

		if (!editar) {
			btNuevo.setVisible(false);
			btEditar.setVisible(false);
			btEliminar.setVisible(false);
		}

		if (!seleccionar) {
			btAsignar.setVisible(false);
		}
	}

	public void listarCombos() {
		Utilidades
				.listarElementoListbox(lbxEstancia, true, elementoService);
	}

	// Este metodo nos sirve para crear el arbol estructural desde la estancia
	// hasta la cama y lo almacena en una Lista//
	public List<Map<String, Object>> listarEstancias() {
		Map<String, Object> paramEstancias = new HashMap<String, Object>();
		paramEstancias.put("tipo", "tipo_atencion");
		if (tipo_atencion != null) {
			if (!tipo_atencion.equals("")) {
				paramEstancias.put("codigo", tipo_atencion);
			}
		}
		List<Elemento> listaEstancias = elementoService.listar(paramEstancias);

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		for (Elemento elemento : listaEstancias) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo_atencion", elemento);

			Map<String, Object> paramPabellon = new HashMap<String, Object>();
			paramPabellon.put("codigo_empresa", empresa.getCodigo_empresa());
			paramPabellon.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			paramPabellon.put("tipo_atencion", elemento.getCodigo());
			paramPabellon.put("codigo_centro",
					centro_atencion_session.getCodigo_centro());
			List<Map<String, Object>> lista_pabellon = new ArrayList<Map<String, Object>>();
			List<Pabellon> lista_aux = pabellonService
					.listar(paramPabellon);
			for (Pabellon pabellon : lista_aux) {
				Map<String, Object> paramHabitacion = new HashMap<String, Object>();
				paramHabitacion.put("codigo_empresa",
						pabellon.getCodigo_empresa());
				paramHabitacion.put("codigo_sucursal",
						pabellon.getCodigo_sucursal());
				paramHabitacion.put("tipo_atencion",
						pabellon.getTipo_atencion());
				paramHabitacion.put("codigo_pabellon", pabellon.getCodigo());
				paramHabitacion.put("codigo_centro",
						pabellon.getCodigo_centro());
				List<Habitacion> lista_habitacion = habitacionService.listar(paramHabitacion);

				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("pabellon", pabellon);
				map2.put("lista_habitacion", lista_habitacion);
				lista_pabellon.add(map2);
			}
			map.put("lista_pabellon", lista_pabellon);
			data.add(map);

		}

		return data;
	}

	public void crearArbol() throws Exception {
		try {
			List<Map<String, Object>> listaArbol = listarEstancias();

			treeEstancias.getTreechildren().getChildren().clear();

			Treechildren treechildren = treeEstancias.getTreechildren();

			for (Map<String, Object> map : listaArbol) {

				final Elemento tipo_atencion = (Elemento) map
						.get("tipo_atencion");
				final List<Map<String, Object>> lista_pabellon = (List<Map<String, Object>>) map
						.get("lista_pabellon");

				Treeitem treeitemEstancia = new Treeitem();
				treeitemEstancia.setOpen(false);

				// Creamos el encabezado de la estancia //
				Treerow treerowTituloEstancia = new Treerow();
				treerowTituloEstancia.setLabel(tipo_atencion.getDescripcion());
				treerowTituloEstancia.setSclass("Estilo7");
				treerowTituloEstancia.setStyle("font-weight:bold");
				treerowTituloEstancia.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								tbxCodigo_atencion.setValue(tipo_atencion
										.getCodigo());
								mostrarPabellones(
										tipo_atencion.getDescripcion(),
										tipo_atencion.getCodigo());

							}
						});

				// Agregamos el treerowTituloEstancia al treeitemEstancia //
				treeitemEstancia.appendChild(treerowTituloEstancia);

				// Generamos los pabellones: Pabellones es como si fueran los
				// pisos //
				Treechildren treechildrenPabellon = null;
				if (!lista_pabellon.isEmpty()) {
					treechildrenPabellon = new Treechildren();
					treeitemEstancia.appendChild(treechildrenPabellon);
				}/*
				 * else{ treeitemEstancia.setDisabled(true); }
				 */
				for (Map<String, Object> map2 : lista_pabellon) {
					final Pabellon pabellon = (Pabellon) map2.get("pabellon");
					List<Habitacion> lista_habitacion = (List<Habitacion>) map2
							.get("lista_habitacion");

					Treeitem treeitemPabellon = new Treeitem();
					treeitemPabellon.setOpen(false);

					// Creamos el encabezado del pabellon //
					Treerow treerowTituloPabellon = new Treerow();
					treerowTituloPabellon.setLabel(pabellon.getNombre());
					treerowTituloPabellon.setSclass("Estilo7");
					treerowTituloPabellon.setStyle("font-weight:bold");
					treerowTituloPabellon.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									tbxCodigo_atencion.setValue(pabellon
											.getTipo_atencion());
									tbxCodigo_pabellon.setValue(pabellon
											.getCodigo() + "");
									mostrarHabitaciones(pabellon.getNombre(),
											pabellon.getCodigo() + "",
											pabellon.getCodigo_centro());

								}
							});

					// Agregamos el treerowTituloPabellon al treeitemPabellon //
					treeitemPabellon.appendChild(treerowTituloPabellon);

					// Agregamos el treeitemPabellon al treechildrenPabellon //
					treechildrenPabellon.appendChild(treeitemPabellon);

					// Generamos las habitaciones //
					Treechildren treechildrenHabitacion = null;
					treechildrenHabitacion = new Treechildren();
					treeitemPabellon.appendChild(treechildrenHabitacion);
					/*
					 * if(lista_habitacion.isEmpty()){
					 * treeitemPabellon.setDisabled(true); }
					 */

					for (final Habitacion habitacion : lista_habitacion) {
						Map<String, Object> paramCama = new HashMap<String, Object>();
						paramCama.put("codigo_empresa",
								empresa.getCodigo_empresa());
						paramCama.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						paramCama.put("tipo_atencion",
								habitacion.getTipo_atencion());
						paramCama.put("codigo_pabellon",
								habitacion.getCodigo_pabellon());
						paramCama.put("codigo_habitacion",
								habitacion.getCodigo());
						paramCama.put("codigo_centro",
								habitacion.getCodigo_centro());
						List<Cama> lista_aux = camaService.listar(paramCama);

						Treeitem treeitemHabitacion = new Treeitem();

						// Creamos el encabezado del pabellon //
						Treerow treerowTituloHabitacion = new Treerow();
						treerowTituloHabitacion.setLabel(habitacion.getNombre()
								+ " (Camas: " + lista_aux.size() + ")");
						treerowTituloHabitacion.setSclass("Estilo7");
						treerowTituloHabitacion.setStyle("font-weight:bold");

						treerowTituloHabitacion.addEventListener(
								Events.ON_CLICK, new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {
										tbxCodigo_atencion.setValue(habitacion
												.getTipo_atencion());
										tbxCodigo_pabellon.setValue(habitacion
												.getCodigo_pabellon() + "");
										tbxCodigo_habitacion
												.setValue(habitacion
														.getCodigo() + "");
										mostrarCamas(habitacion.getNombre(),
												habitacion.getCodigo() + "",
												habitacion.getCodigo_centro());

									}
								});

						// Agregamos el treerowTituloHabitacion al
						// treeitemHabitacion //
						treeitemHabitacion.appendChild(treerowTituloHabitacion);

						// Agregamos el treeitemHabitacion al
						// treechildrenHabitacion //
						treechildrenHabitacion.appendChild(treeitemHabitacion);
					}

				}

				// Agregamos el treeitem de estancia al treechildrem principal
				// //
				treechildren.appendChild(treeitemEstancia);
			}

			// Agregamos el treechildren principal al treeEstancias //
			treeEstancias.appendChild(treechildren);
		} catch (Exception e) {
			// log.info(e.getMessage(), e);
			MensajesUtil.mensajeAlerta("Alerta!!", e.getMessage());
		}
	}

	// Este metodo es para generar las celdas de la grilla dependiendo del
	// mosaico //
	public void mostrarPabellones(String titulo, String tipo_estancia)
			throws Exception {

		Map<String, Object> paramPabellon = new HashMap<String, Object>();
		paramPabellon.put("codigo_empresa", empresa.getCodigo_empresa());
		paramPabellon.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		paramPabellon.put("tipo_atencion", tipo_estancia);
		paramPabellon.put("codigo_centro",
				centro_atencion_session.getCodigo_centro());
		List<Pabellon> lista_aux = pabellonService
				.listar(paramPabellon);

		Vector<Map<String, Pabellon>> lista_pabellon = generarMosaicoPabellones(
				titulo, lista_aux);
		if (!titulo.isEmpty()) {

		}
		auxHeaderTitulo.setLabel(titulo);
		tbxTipo.setValue("pabellon");
		tbxCodigo_pabellon.setValue("");

		rowsMosaico.getChildren().clear();

		for (Map<String, Pabellon> map : lista_pabellon) {
			Row row = new Row();
			int count = 1;
			for (Entry<String, Pabellon> pabellon : map.entrySet()) {

				final Entry<String, Pabellon> auxPabellon = pabellon;
				Cell cell = new Cell();

				Vlayout vlayout = new Vlayout();

				Image image = new Image();
				image.setSrc("/images/arbol_camas/pabellones.jpg");
				image.setWidth("70px");
				image.setHeight("50px");
				vlayout.appendChild(image);

				Radio radio = new Radio();
				radio.setLabel(pabellon.getValue().getNombre());
				radio.setStyle("font-size:8px");
				radio.setRadiogroup(radiogroupModulos);
				radio.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								tbxCodigo_pabellon.setValue(auxPabellon
										.getValue().getCodigo() + "");
							}
						});
				vlayout.appendChild(radio);

				cell.appendChild(vlayout);

				row.appendChild(cell);
				if (count == 1) {
					radio.setChecked(true);
					tbxCodigo_pabellon.setValue(pabellon.getValue().getCodigo()
							+ "");
				}

				count++;
			}

			rowsMosaico.appendChild(row);
		}

	}

	// Este metodo es para generar un mosaico con la informacion de los
	// pabellones //
	private Vector<Map<String, Pabellon>> generarMosaicoPabellones(
			String titulo, List<Pabellon> lista_aux) {

		Map<String, Map<String, Pabellon>> map_pabellones = new HashMap<String, Map<String, Pabellon>>();
		Map<String, Pabellon> map = new HashMap<String, Pabellon>();

		Vector<Map<String, Pabellon>> lista_pabellon = new Vector<Map<String, Pabellon>>();
		int cont = 0, index = 0;
		for (int i = 0; i < lista_aux.size(); i++) {
			Pabellon pabellon = (Pabellon) lista_aux.get(i);
			pabellon.setAuxNombreEstancia(titulo);

			if (cont == 4) {
				cont = 0;
				map = new HashMap<String, Pabellon>();
				index++;
			}

			map.put("pabellon_" + (cont + 1), pabellon);
			map_pabellones.put(index + "", map);
			cont++;
		}

		int k = 0;
		while (k < map_pabellones.size()) {
			Map<String, Pabellon> aux = map_pabellones.get(k + "");
			if (aux != null) {
				lista_pabellon.add(aux);
			}
			k++;
		}

		return lista_pabellon;
	}

	// Para habitaciones//

	// Este metodo es para generar las celdas de la grilla dependiendo del
	// mosaico //
	public void mostrarHabitaciones(String titulo, String codigo_pabellon,
			String codigo_centro) throws Exception {

		Map<String, Object> paramHabitacion = new HashMap<String, Object>();
		paramHabitacion.put("codigo_empresa", empresa.getCodigo_empresa());
		paramHabitacion.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		paramHabitacion.put("tipo_atencion", tbxCodigo_atencion.getValue());
		paramHabitacion.put("codigo_pabellon",
				Integer.parseInt(codigo_pabellon));
		paramHabitacion.put("codigo_centro", codigo_centro);
		List<Habitacion> lista_aux = habitacionService
				.listar(paramHabitacion);
		Vector<Map<String, Habitacion>> lista_habitacion = generarMosaicoHabitacion(
				titulo, lista_aux);
		if (!titulo.isEmpty()) {

		}
		auxHeaderTitulo.setLabel(titulo);
		tbxTipo.setValue("habitacion");
		tbxCodigo_habitacion.setValue("");

		rowsMosaico.getChildren().clear();

		for (Map<String, Habitacion> map : lista_habitacion) {
			Row row = new Row();
			int count = 1;
			for (Entry<String, Habitacion> habitacion : map.entrySet()) {

				final Entry<String, Habitacion> auxHabitacion = habitacion;
				Cell cell = new Cell();

				Vlayout vlayout = new Vlayout();

				Image image = new Image();
				image.setSrc("/images/arbol_camas/habitaciones.jpg");
				image.setWidth("70px");
				image.setHeight("50px");
				vlayout.appendChild(image);

				Radio radio = new Radio();
				radio.setLabel(habitacion.getValue().getNombre());
				radio.setStyle("font-size:8px");
				radio.setRadiogroup(radiogroupModulos);
				radio.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								tbxCodigo_habitacion.setValue(auxHabitacion
										.getValue().getCodigo() + "");
							}
						});
				vlayout.appendChild(radio);

				cell.appendChild(vlayout);

				row.appendChild(cell);
				if (count == 1) {
					radio.setChecked(true);
					tbxCodigo_habitacion.setValue(habitacion.getValue()
							.getCodigo() + "");
				}

				count++;
			}

			rowsMosaico.appendChild(row);
		}

	}

	// Este metodo es para generar un mosaico con la informacion de los
	// pabellones //
	private Vector<Map<String, Habitacion>> generarMosaicoHabitacion(
			String titulo, List<Habitacion> lista_aux) {

		Map<String, Map<String, Habitacion>> map_habitaciones = new HashMap<String, Map<String, Habitacion>>();
		Map<String, Habitacion> map = new HashMap<String, Habitacion>();

		Vector<Map<String, Habitacion>> lista_habitacion = new Vector<Map<String, Habitacion>>();
		int cont = 0, index = 0;
		for (int i = 0; i < lista_aux.size(); i++) {
			Habitacion habitacion = (Habitacion) lista_aux.get(i);
			habitacion.setAuxNombrePabellon(titulo);

			if (cont == 4) {
				cont = 0;
				map = new HashMap<String, Habitacion>();
				index++;
			}

			map.put("pabellon_" + (cont + 1), habitacion);
			map_habitaciones.put(index + "", map);
			cont++;
		}

		int k = 0;
		while (k < map_habitaciones.size()) {
			Map<String, Habitacion> aux = map_habitaciones.get(k + "");
			if (aux != null) {
				lista_habitacion.add(aux);
			}
			k++;
		}

		return lista_habitacion;
	}

	// Para camas//

	// Este metodo es para generar las celdas de la grilla dependiendo del
	// mosaico //
	public void mostrarCamas(String titulo, String codigo_habitacion,
			String codigo_centro) throws Exception {

		Map<String, Object> paramCama = new HashMap<String, Object>();
		paramCama.put("codigo_empresa", empresa.getCodigo_empresa());
		paramCama.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		paramCama.put("tipo_atencion", tbxCodigo_atencion.getValue());
		paramCama.put("codigo_pabellon",
				Integer.parseInt(tbxCodigo_pabellon.getValue()));
		paramCama.put("codigo_habitacion", Integer.parseInt(codigo_habitacion));
		paramCama.put("codigo_centro", codigo_centro);
		List<Cama> lista_aux = camaService.listar(
				paramCama);

		Vector<Map<String, Cama>> lista_cama = generarMosaicoCama(titulo,
				lista_aux);
		if (!titulo.isEmpty()) {

		}
		auxHeaderTitulo.setLabel(titulo);
		tbxTipo.setValue("cama");
		tbxCodigo_cama.setValue("");

		rowsMosaico.getChildren().clear();

		for (Map<String, Cama> map : lista_cama) {
			Row row = new Row();
			int count = 1;
			for (Entry<String, Cama> cama : map.entrySet()) {

				final Entry<String, Cama> auxCama = cama;
				Cell cell = new Cell();

				Vlayout vlayout = new Vlayout();

				if (seleccionar) {
					Label label = new Label((cama.getValue().getEstado()
							.equals("01") ? "Libre" : "Asignado"));
					vlayout.appendChild(label);
				}

				Image image = new Image();
				image.setSrc("/images/arbol_camas/camas.jpg");
				image.setWidth("70px");
				image.setHeight("50px");
				vlayout.appendChild(image);

				Radio radio = new Radio();
				radio.setLabel(cama.getValue().getNombre());
				radio.setStyle("font-size:8px");
				radio.setRadiogroup(radiogroupModulos);
				radio.setDisabled((cama.getValue().getEstado().equals("02") ? true
						: false));
				radio.addEventListener(Events.ON_CHECK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								tbxCodigo_cama.setValue(auxCama.getValue()
										.getCodigo() + "");
							}
						});
				vlayout.appendChild(radio);

				cell.appendChild(vlayout);
				if (cama.getValue().getEstado().equals("02")) {
					cell.setStyle("background-color:yellow");
				} else {
					cell.setStyle("background-color:white");
				}

				row.appendChild(cell);
				if (count == 1 && !seleccionar) {
					radio.setChecked(true);
					tbxCodigo_cama.setValue(cama.getValue().getCodigo() + "");
				}

				count++;
			}

			rowsMosaico.appendChild(row);
		}

	}

	// Este metodo es para generar un mosaico con la informacion de los
	// pabellones //
	private Vector<Map<String, Cama>> generarMosaicoCama(String titulo,
			List<Cama> lista_aux) {

		Map<String, Map<String, Cama>> map_cama = new HashMap<String, Map<String, Cama>>();
		Map<String, Cama> map = new HashMap<String, Cama>();

		Vector<Map<String, Cama>> lista_cama = new Vector<Map<String, Cama>>();
		int cont = 0, index = 0;
		for (int i = 0; i < lista_aux.size(); i++) {
			Cama cama = (Cama) lista_aux.get(i);
			cama.setAuxNombreHabitacion(titulo);

			if (cont == 4) {
				cont = 0;
				map = new HashMap<String, Cama>();
				index++;
			}

			map.put("pabellon_" + (cont + 1), cama);
			map_cama.put(index + "", map);
			cont++;
		}

		int k = 0;
		while (k < map_cama.size()) {
			Map<String, Cama> aux = map_cama.get(k + "");
			if (aux != null) {
				lista_cama.add(aux);
			}
			k++;
		}

		return lista_cama;
	}

	public void abrirModulo(String accion) {
		if (tbxTipo.getValue().equals("")) {
			return;
		}

		if (accion.equals("modificar") && tbxTipo.getValue().equals("pabellon")
				&& tbxCodigo_pabellon.getValue().equals("")) {
			MensajesUtil.mensajeAlerta("Alerta !!",
					"Debe seleccionar elemento a editar");
			return;
		} else if (accion.equals("modificar")
				&& tbxTipo.getValue().equals("habitacion")
				&& tbxCodigo_habitacion.getValue().equals("")) {
			MensajesUtil.mensajeAlerta("Alerta !!",
					"Debe seleccionar elemento a editar");
			return;
		} else if (accion.equals("modificar")
				&& tbxTipo.getValue().equals("cama")
				&& tbxCodigo_cama.getValue().equals("")) {
			MensajesUtil.mensajeAlerta("Alerta !!",
					"Debe seleccionar elemento a editar");
			return;
		}

		if (!tbxTipo.getValue().equals("pabellon")) {
			hboxEstancia.setVisible(false);
		} else {
			hboxEstancia.setVisible(true);
		}

		tbxAccion.setValue(accion);

		if (accion.equals("modificar") && tbxTipo.getValue().equals("pabellon")) {
			mostrarModuloPabellon();
		}

		if (accion.equals("modificar")
				&& tbxTipo.getValue().equals("habitacion")) {
			mostrarModuloHabitacion();
		}

		if (accion.equals("modificar") && tbxTipo.getValue().equals("cama")) {
			mostrarModuloCama();
		}

		winMensaje.setMode("modal");
		winMensaje.setVisible(true);
	}

	public void guardarModulo() throws Exception {
		if (tbxTipo.getValue().equals("pabellon")) {
			guardarPabellon();
		} else if (tbxTipo.getValue().equals("habitacion")) {
			guardarHabitacion();
		} else if (tbxTipo.getValue().equals("cama")) {
			guardarCama();
		}
	}

	public void eliminarModulo() throws Exception {
		if (tbxTipo.getValue().equals("pabellon")) {
			eliminarPabellon();
		} else if (tbxTipo.getValue().equals("habitacion")) {
			eliminarHabitacion();
		} else if (tbxTipo.getValue().equals("cama")) {
			eliminarCama();
		}
	}

	// Pabellones maestros//
	public void guardarPabellon() throws Exception {
		try {

			if (tbxNombre.getValue().trim().equals("")) {
				MensajesUtil.mensajeAlerta("Alerta !!",
						"Debe digitar el nombre");
				return;
			}

			if (lbxEstancia.getSelectedItem().getValue().equals("")) {
				MensajesUtil.mensajeAlerta("Alerta !!",
						"Debe digitar la estancia");
				return;
			}

			Pabellon pabellon = new Pabellon();
			pabellon.setCodigo_empresa(empresa.getCodigo_empresa());
			pabellon.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			pabellon.setTipo_atencion(tbxCodigo_atencion.getValue());
			pabellon.setCodigo_centro(centro_atencion_session
					.getCodigo_centro());
			if (!tbxAccion.getValue().equals("registrar")) {
				pabellon.setCodigo(Integer.parseInt(tbxCodigo_pabellon
						.getValue()));
			}

			pabellon.setNombre(tbxNombre.getValue().trim().toUpperCase());
			pabellon.setEstancias(lbxEstancia.getSelectedItem().getValue()
					.toString());
			pabellon.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			pabellon.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			pabellon.setCreacion_user(usuarios.getCodigo());
			pabellon.setUltimo_user(usuarios.getCodigo());

			if (tbxAccion.getValue().equals("registrar")) {
				pabellonService.crear(pabellon);
			} else {
				pabellonService.actualizar(pabellon);
			}
			mostrarPabellones("", tbxCodigo_atencion.getValue());
			crearArbol();
			winMensaje.setVisible(false);
		} catch (Exception e) {
			// log.info(e.getMessage(), e);
			MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
		}
	}

	public void mostrarModuloPabellon() {
		Pabellon pabellon = getPabellon(tbxCodigo_atencion.getValue(),
				Integer.parseInt(tbxCodigo_pabellon.getValue()));
		if (pabellon != null) {
			tbxNombre.setValue(pabellon.getNombre());
			Utilidades
					.seleccionarListitem(lbxEstancia, pabellon.getEstancias());
		}
	}

	public void eliminarPabellon() throws Exception {
		try {

			if (tbxCodigo_pabellon.getValue().trim().equals("")) {
				MensajesUtil.mensajeAlerta("Alerta !!",
						"Debe seleccionar el pabellon");
				return;
			}

			Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								Pabellon pabellon = getPabellon(
										tbxCodigo_atencion.getValue(), Integer
												.parseInt(tbxCodigo_pabellon
														.getValue()));
								if (pabellon != null) {
									pabellonService
											.eliminar(pabellon);
									mostrarPabellones("",
											tbxCodigo_atencion.getValue());
									crearArbol();
								}
							}
						}
					});
		} catch (Exception e) {
			// log.info(e.getMessage(), e);
			MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
		}
	}

	// Habitaciones maestros //
	public void guardarHabitacion() throws Exception {
		try {

			if (tbxNombre.getValue().trim().equals("")) {
				MensajesUtil.mensajeAlerta("Alerta !!",
						"Debe digitar el nombre");
				return;
			}

			Pabellon pabellon = getPabellon(tbxCodigo_atencion.getValue(),
					Integer.parseInt(tbxCodigo_pabellon.getValue()));
			if (pabellon == null) {
				throw new Exception("Pabellón no válido");
			}

			Habitacion habitacion = new Habitacion();
			habitacion.setCodigo_empresa(empresa.getCodigo_empresa());
			habitacion.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			habitacion.setTipo_atencion(tbxCodigo_atencion.getValue());
			habitacion.setCodigo_pabellon(pabellon.getCodigo());
			if (!tbxAccion.getValue().equals("registrar")) {
				habitacion.setCodigo(Integer.parseInt(tbxCodigo_habitacion
						.getValue()));
			}
			habitacion.setNombre(tbxNombre.getValue().trim().toUpperCase());
			habitacion.setNro_camas(0);
			habitacion.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			habitacion.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			habitacion.setCreacion_user(usuarios.getCodigo());
			habitacion.setUltimo_user(usuarios.getCodigo());
			habitacion.setCodigo_centro(pabellon.getCodigo_centro());

			if (tbxAccion.getValue().equals("registrar")) {
				habitacionService.crear(habitacion);
			} else {
				habitacionService.actualizar(
						habitacion);
			}
			mostrarHabitaciones("", tbxCodigo_pabellon.getValue(),
					pabellon.getCodigo_centro());
			crearArbol();
			winMensaje.setVisible(false);
		} catch (Exception e) {
			// log.info(e.getMessage(), e);
			MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
		}
	}

	public void mostrarModuloHabitacion() {

		Pabellon pabellon = getPabellon(tbxCodigo_atencion.getValue(),
				Integer.parseInt(tbxCodigo_pabellon.getValue()));
		Habitacion habitacion = getHabitacion(pabellon,
				Integer.parseInt(tbxCodigo_habitacion.getValue()));

		if (habitacion != null) {
			tbxNombre.setValue(habitacion.getNombre());
		}
	}

	public void eliminarHabitacion() throws Exception {
		try {

			if (tbxCodigo_habitacion.getValue().trim().equals("")) {
				MensajesUtil.mensajeAlerta("Alerta !!",
						"Debe seleccionar la habitacion");
				return;
			}

			Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								Pabellon pabellon = getPabellon(
										tbxCodigo_atencion.getValue(), Integer
												.parseInt(tbxCodigo_pabellon
														.getValue()));
								if (pabellon != null) {
									Habitacion habitacion = getHabitacion(
											pabellon,
											Integer.parseInt(tbxCodigo_habitacion
													.getValue()));
									if (habitacion != null) {
										habitacionService
												.eliminar(habitacion);
										mostrarHabitaciones("",
												tbxCodigo_pabellon.getValue(),
												pabellon.getCodigo_centro());
										crearArbol();
									}
								}

							}
						}
					});
		} catch (Exception e) {
			// log.info(e.getMessage(), e);
			MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
		}
	}

	// Habitaciones cama //
	public void guardarCama() throws Exception {
		try {

			if (tbxNombre.getValue().trim().equals("")) {
				MensajesUtil.mensajeAlerta("Alerta !!",
						"Debe digitar el nombre");
				return;
			}

			Pabellon pabellon = getPabellon(tbxCodigo_atencion.getValue(),
					Integer.parseInt(tbxCodigo_pabellon.getValue()));
			Habitacion habitacion = getHabitacion(pabellon,
					Integer.parseInt(tbxCodigo_habitacion.getValue()));

			Cama cama = new Cama();
			cama.setCodigo_empresa(empresa.getCodigo_empresa());
			cama.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			cama.setTipo_atencion(tbxCodigo_atencion.getValue());
			cama.setCodigo_pabellon(pabellon.getCodigo());
			cama.setCodigo_habitacion(habitacion.getCodigo());
			if (!tbxAccion.getValue().equals("registrar")) {
				cama.setCodigo(Integer.parseInt(tbxCodigo_cama.getValue()));
			}
			cama.setNombre(tbxNombre.getValue().trim().toUpperCase());
			cama.setEstado("01");
			cama.setCodigo_paciente("");
			cama.setCreacion_date(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cama.setUltimo_update(new Timestamp(new GregorianCalendar()
					.getTimeInMillis()));
			cama.setCreacion_user(usuarios.getCodigo());
			cama.setUltimo_user(usuarios.getCodigo());
			cama.setCodigo_centro(pabellon.getCodigo_centro());

			if (tbxAccion.getValue().equals("registrar")) {
				camaService.crear(cama);
			} else {
				camaService.actualizar(cama);
			}
			mostrarCamas("", tbxCodigo_habitacion.getValue(),
					pabellon.getCodigo_centro());
			crearArbol();
			winMensaje.setVisible(false);
		} catch (Exception e) {
			// log.info(e.getMessage(), e);
			MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
		}
	}

	public void mostrarModuloCama() {
		Pabellon pabellon = getPabellon(tbxCodigo_atencion.getValue(),
				Integer.parseInt(tbxCodigo_pabellon.getValue()));
		Habitacion habitacion = getHabitacion(pabellon,
				Integer.parseInt(tbxCodigo_habitacion.getValue()));
		Cama cama = getCama(pabellon, habitacion,
				Integer.parseInt(tbxCodigo_cama.getValue()));

		if (cama != null) {
			tbxNombre.setValue(cama.getNombre());
		}
	}

	public void eliminarCama() throws Exception {
		try {

			if (tbxCodigo_cama.getValue().trim().equals("")) {
				MensajesUtil.mensajeAlerta("Alerta !!",
						"Debe seleccionar la cama");
				return;
			}

			Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								Pabellon pabellon = getPabellon(
										tbxCodigo_atencion.getValue(), Integer
												.parseInt(tbxCodigo_pabellon
														.getValue()));
								Habitacion habitacion = getHabitacion(pabellon,
										Integer.parseInt(tbxCodigo_habitacion
												.getValue()));
								Cama cama = getCama(pabellon, habitacion,
										Integer.parseInt(tbxCodigo_cama
												.getValue()));
								camaService.eliminar(
										cama);
								mostrarCamas("", tbxCodigo_habitacion
										.getValue(), centro_atencion_session
										.getCodigo_centro());
								crearArbol();
							}
						}
					});
		} catch (Exception e) {
			// log.info(e.getMessage(), e);
			MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
		}
	}

	public void asignarCama() {
		if (tbxCodigo_cama.getValue().equals("")) {
			MensajesUtil.mensajeInformacion("Alerta !!",
					"Debe seleccionar la cama");
			return;
		}

		if (this.getParent() instanceof Admision_nuevaAction) {
			Admision_nuevaAction action = (Admision_nuevaAction) this
					.getParent();
			action.cargarCama(tbxCodigo_atencion.getValue(),
					tbxCodigo_pabellon.getValue(),
					tbxCodigo_habitacion.getValue(), tbxCodigo_cama.getValue(),
					centro_atencion_session.getCodigo_centro());
			this.detach();
		}
	}

	public Pabellon getPabellon(String tipo_atencion, Integer codigo) {
		Pabellon pabellon = null;
		if (codigo != null) {
			pabellon = new Pabellon();
			pabellon.setCodigo_empresa(empresa.getCodigo_empresa());
			pabellon.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			pabellon.setTipo_atencion(tipo_atencion);
			pabellon.setCodigo(codigo);
			pabellon.setCodigo_centro(centro_atencion_session
					.getCodigo_centro());
			pabellon = pabellonService.consultar(
					pabellon);
		}
		return pabellon;
	}

	public Habitacion getHabitacion(Pabellon pabellon, Integer codigo) {
		Habitacion habitacion = null;
		if (pabellon != null && codigo != null) {
			habitacion = new Habitacion();
			habitacion.setCodigo_empresa(pabellon.getCodigo_empresa());
			habitacion.setCodigo_sucursal(pabellon.getCodigo_sucursal());
			habitacion.setTipo_atencion(pabellon.getTipo_atencion());
			habitacion.setCodigo_pabellon(pabellon.getCodigo());
			habitacion.setCodigo_centro(pabellon.getCodigo_centro());
			habitacion.setCodigo(codigo);
			habitacion = habitacionService.consultar(
					habitacion);
		}
		return habitacion;
	}

	public Cama getCama(Pabellon pabellon, Habitacion habitacion, Integer codigo) {
		Cama cama = null;
		if (pabellon != null && habitacion != null && codigo != null) {
			cama = new Cama();
			cama.setCodigo_empresa(pabellon.getCodigo_empresa());
			cama.setCodigo_sucursal(pabellon.getCodigo_sucursal());
			cama.setTipo_atencion(pabellon.getTipo_atencion());
			cama.setCodigo_pabellon(pabellon.getCodigo());
			cama.setCodigo_habitacion(habitacion.getCodigo());
			cama.setCodigo_centro(pabellon.getCodigo_centro());
			cama.setCodigo(codigo);
			habitacion = habitacionService.consultar(
					habitacion);
		}
		return cama;
	}

}
