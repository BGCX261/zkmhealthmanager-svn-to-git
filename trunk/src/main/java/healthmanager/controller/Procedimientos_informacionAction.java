/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.ElementoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.interfaces.IOnEventoListCellAutomatica;
import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Contabilizacion;

/**
 * @author Dario Perez Campillo
 * 
 */
public class Procedimientos_informacionAction extends ZKWindow implements
		AfterCompose {

//	private static Logger log = Logger
//			.getLogger(Procedimientos_informacionAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@View
	private Listbox lbxNivel_procedimiento;

	@View
	private Textbox tbxValue;
	@View
	private Listbox listboxResultado;

	private IOnEventoListCellAutomatica iOnEventoListCellAutomatica;
	private List<Elemento> elementos_clasificacion;
	// private List<Elemento> elementos_sexo;
	private List<Elemento> elementos_si_no;
	private List<Elemento> elementos_si_no_todo;
	private List<Contabilizacion> list_contabilizacion;
	private List<Elemento> elementos_grupo;

	public void seleccionarManual() {
		listboxResultado.getItems().clear();
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String value = tbxValue.getValue().toUpperCase().trim();
			Map<String, Object> parameters = new HashMap();
			parameters.put("nivel_orden", lbxNivel_procedimiento
					.getSelectedItem().getValue().toString());
			parameters.put("paramTodo", "");
			parameters.put("value", value);
			//log.info("parametros de busqueda ===> " + parameters);

			listboxResultado.getItems().clear();

			List<Procedimientos> lista_procedimientos = getServiceLocator()
					.getProcedimientosService().listar(parameters);

			for (Procedimientos procedimientos : lista_procedimientos) {
				listboxResultado.appendChild(crearFilas(procedimientos));
			}

			listboxResultado.setVisible(true);
			listboxResultado.setMold("paging");
			listboxResultado.setPageSize(20);

			listboxResultado.applyProperties();
			listboxResultado.invalidate();
			listboxResultado.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void registrarNuevo() {

		String pagina_procedimiento = "";

		pagina_procedimiento = "procedimientos.zul";

		if (!pagina_procedimiento.trim().isEmpty()) {
			Window window = (Window) Executions.createComponents(
					pagina_procedimiento, this, null);
			window.setBorder("normal");
			window.setTitle("Registrar procedimiento ");
			window.setWidth("800px");
			window.setHeight("90%");
			window.setClosable(true);
			window.doModal();
		} else {
			MensajesUtil
					.mensajeAlerta("Advertencia",
							"Tipo de procedimiento no valido, verifique que sea SOAT o ISS");
		}
	}

	private Listcell getCellListbox(Procedimientos objeto, String campo,
			boolean select, String nivel, Class<? extends XulElement> classs)
			throws Exception {
		Listcell listcell = Utilidades.obtenerListcellAutomatica(objeto, campo,
				classs, false, false, iOnEventoListCellAutomatica, nivel);
		return listcell;
	}

	public Listitem crearFilas(Object objeto) throws Exception {
		final Listitem fila = new Listitem();

		Procedimientos procedimientos = (Procedimientos) objeto;
		//log.info("procedimientos ===> " + procedimientos);
		fila.setValue(procedimientos);

		Listcell listcell = Utilidades
				.obtenerListcell(procedimientos.getId_procedimiento(),
						Longbox.class, true, true);
		listcell.getFirstChild().setId(
				"id_procedimiento_" + procedimientos.getId_procedimiento());
		fila.appendChild(listcell);

		listcell = Utilidades.obtenerListcell(procedimientos.getCodigo_cups(),
				Textbox.class, false, true);
		listcell.getFirstChild().setId(
				"codigo_cups_" + procedimientos.getId_procedimiento());
		Res.cargarAutomatica(listcell.getFirstChild(), objeto, "codigo_cups");
		fila.appendChild(listcell);

		listcell = Utilidades.obtenerListcell(procedimientos.getDescripcion(),
				Textbox.class, false, true);
		listcell.getFirstChild().setId(
				"descripcion_" + procedimientos.getId_procedimiento());
		Res.cargarAutomatica(listcell.getFirstChild(), objeto, "descripcion");
		fila.appendChild(listcell);

		// clasificacion
		fila.appendChild(getCellListbox(procedimientos, "clasificacion", true,
				"1", Listbox.class));

		listcell = Utilidades.obtenerListcell(procedimientos.getCodigo_cups(),
				Textbox.class, false, true);
		listcell.getFirstChild().setId(
				"tipo_procedimiento_" + procedimientos.getId_procedimiento());
		Res.cargarAutomatica(listcell.getFirstChild(), objeto,
				"tipo_procedimiento");
		fila.appendChild(listcell);

		fila.appendChild(getCellListbox(procedimientos, "consulta", false, "2",
				Listbox.class));

		fila.appendChild(getCellListbox(procedimientos, "quirurgico", false,
				"2", Listbox.class));

		fila.appendChild(getCellListbox(procedimientos, "tipo_quirurgico",
				false, "7", Listbox.class));

		fila.appendChild(getCellListbox(procedimientos, "urgencias", false,
				"2", Listbox.class));

		fila.appendChild(getCellListbox(procedimientos, "hospitalizacion",
				false, "2", Listbox.class));

		fila.appendChild(getCellListbox(procedimientos, "recien_nacido", false,
				"2", Listbox.class));

		fila.appendChild(getCellListbox(procedimientos, "codigo_contabilidad",
				false, "6", Listbox.class));

		fila.appendChild(getCellListbox(procedimientos, "sexo", false, "3",
				Listbox.class));

		listcell = Utilidades
				.obtenerListcell(procedimientos.getLimite_inferior(),
						Textbox.class, false, true);
		listcell.getFirstChild().setId(
				"limite_inferior_" + procedimientos.getId_procedimiento());
		Res.cargarAutomatica(listcell.getFirstChild(), objeto,
				"limite_inferior");
		fila.appendChild(listcell);

		listcell = Utilidades
				.obtenerListcell(procedimientos.getLimite_superior(),
						Textbox.class, false, true);
		listcell.getFirstChild().setId(
				"limite_superior_" + procedimientos.getId_procedimiento());
		Res.cargarAutomatica(listcell.getFirstChild(), objeto,
				"limite_superior");
		fila.appendChild(listcell);

		fila.appendChild(getCellListbox(procedimientos, "pyp", false, "5",
				Listbox.class));
		fila.appendChild(getCellListbox(procedimientos, "cobra_copago", false,
				"2", Listbox.class));

		listcell = Utilidades
				.obtenerListcell(procedimientos.getFrecuencia_orden(),
						Intbox.class, false, true);
		listcell.getFirstChild().setId(
				"frecuencia_orden_" + procedimientos.getId_procedimiento());
		Res.cargarAutomatica(listcell.getFirstChild(), objeto,
				"frecuencia_orden");
		fila.appendChild(listcell);

		fila.appendChild(getCellListbox(procedimientos, "nivel", false, "4",
				Listbox.class));

		listcell = Utilidades
				.obtenerListcell(procedimientos.getExcepcion_nivel(),
						Textbox.class, false, true);
		listcell.getFirstChild().setId(
				"excepcion_nivel_" + procedimientos.getId_procedimiento());
		Res.cargarAutomatica(listcell.getFirstChild(), objeto,
				"excepcion_nivel");
		fila.appendChild(listcell);

		Listbox listbox_finalidad = new Listbox();
		listbox_finalidad.setHflex("1");
		listbox_finalidad.setName("finalidad_proc");
		listbox_finalidad.setMold("select");
		Utilidades.listarElementoListbox(listbox_finalidad, true,
				getServiceLocator());
		Utilidades.seleccionarListitem(listbox_finalidad,
				procedimientos.getFinalidad_procedimiento());
		Res.cargarAutomatica(listbox_finalidad, procedimientos,
				"finalidad_procedimiento");
		listcell = new Listcell();
		listcell.appendChild(listbox_finalidad);
		fila.appendChild(listcell);
		
		if (procedimientos.getEditable() != null) {
			Object dato = procedimientos.getEditable();
			if (dato instanceof Boolean)
				fila.setDisabled(!(Boolean) dato);
		}

		return fila;
	}

	public void guardarDatos() {
		try {
			Set<Listitem> listado_seleccionados = listboxResultado
					.getSelectedItems();

			List<Object> listado_procedimientos = new ArrayList<Object>();
			if (!listado_seleccionados.isEmpty()) {
				for (Listitem listitem : listado_seleccionados) {
					Procedimientos procedimientos = listitem.getValue();
					listado_procedimientos.add(procedimientos);
				}
				getServiceLocator().getProcedimientosService()
						.actualizarProcedimientosInformacion(
								listado_procedimientos);
				MensajesUtil.mensajeInformacion("Informacion",
						"Los datos se guardaron satisfactoriamente");
				buscarDatos();
			} else {
				MensajesUtil.mensajeAlerta("No hay items seleccionados",
						"Debe seleccionar procedimientos que desea actualizar");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void init() throws Exception {
		inicializamosEvento();
		inicializarListado();
		seleccionarManual();
	}

	private void inicializarListado() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tipo", "clasificacion");
		elementos_clasificacion = getServiceLocator().getServicio(
				ElementoService.class).listar(map);

		map.clear();
		map.put("tipo", "codigo_grupo");
		elementos_grupo = getServiceLocator()
				.getServicio(ElementoService.class).listar(map);

		elementos_si_no = new ArrayList<Elemento>();
		elementos_si_no.add(new Elemento("N", "", "NO"));
		elementos_si_no.add(new Elemento("S", "", "SI"));

		elementos_si_no_todo = new ArrayList<Elemento>();
		elementos_si_no_todo.add(new Elemento("SN", "", "TODOS"));
		elementos_si_no_todo.add(new Elemento("S", "", "SI"));
		elementos_si_no_todo.add(new Elemento("N", "", "NO"));

		map.clear();
		map.put("codigo_empresa", getEmpresa().getCodigo_empresa());
		list_contabilizacion = getServiceLocator().getContabilizacionService()
				.listar(map);

	}

	private void inicializamosEvento() {
		iOnEventoListCellAutomatica = new IOnEventoListCellAutomatica() {
			@Override
			public void onCargarComponente(Component component, String campo,
					String nivel, Object object) {
				if (component instanceof Listbox && nivel.equals("1")) {
					Utilidades.listarElementoListboxFromType(
							(Listbox) component, true, elementos_clasificacion,
							false);
				} else if (component instanceof Listbox && nivel.equals("2")) {
					Utilidades.listarElementoListboxFromType(
							(Listbox) component, false, elementos_si_no, false);
				} else if (component instanceof Listbox && nivel.equals("3")) {
					((Listbox) component)
							.appendChild(new Listitem("Ambos", "A"));
					((Listbox) component).appendChild(new Listitem("Masculino",
							"M"));
					((Listbox) component).appendChild(new Listitem("Femenino",
							"F"));
				} else if (component instanceof Listbox && nivel.equals("4")) {
					Utilidades.listarNivelEmpresa((Listbox) component, false);
					Listbox listbox = (Listbox) component;
					if (listbox.getItemCount() > 0) {
						listbox.setSelectedIndex(0);
					}
				} else if (component instanceof Listbox && nivel.equals("5")) {
					Utilidades.listarElementoListboxFromType(
							(Listbox) component, false, elementos_si_no_todo,
							false);
				} else if (component instanceof Listbox && nivel.equals("6")) {
					Listbox listbox = (Listbox) component;
					listbox.appendChild(new Listitem("-- seleccione --", ""));
					for (Contabilizacion contabilizacion : list_contabilizacion) {
						Listitem listitem = new Listitem();
						listitem.setValue(contabilizacion.getCodigo());
						listitem.setLabel(contabilizacion.getNombre());
						listbox.appendChild(listitem);
					}
					if (listbox.getItemCount() > 0) {
						listbox.setSelectedIndex(0);
					}
				} else if (component instanceof BandBoxRowMacro
						&& nivel.equals("6")) {

				} else if (component instanceof Listbox && nivel.equals("7")) {
					Utilidades.listarQuirurgicos((Listbox) component, true);
				} else if (component instanceof Listbox && nivel.equals("8")) {
					Utilidades.listarElementoListboxFromType(
							(Listbox) component, true, elementos_grupo, false);
				}
			}

			@Override
			public void onActualizarValor(Component component, String campo,
					Object valor) {

			}
		};
	}
}
