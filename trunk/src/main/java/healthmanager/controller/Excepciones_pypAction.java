/*
 * excepciones_pypAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Excepciones_pyp;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Pyp;
import healthmanager.modelo.service.Excepciones_pypService;
import healthmanager.modelo.service.ProcedimientosService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
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

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import healthmanager.modelo.service.GeneralExtraService;

public class Excepciones_pypAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Excepciones_pypAction.class);

	private Excepciones_pypService excepciones_pypService;

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
	private Listbox lbxCodigo_pyp;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_cups;
	@View
	private Textbox tbxDescripcion;
	private final String[] idsExcluyentes = {};

	private ProcedimientosService procedimientosService;

	@Override
	public void init() {
		listarCombos();
		parametrizarBandbox();
	}

	private void parametrizarBandbox() {
		//log.info("band box: " + tbxCodigo_cups);
		tbxCodigo_cups.inicializar(tbxDescripcion,
				(Toolbarbutton) getFellow("btnLimpiarCodigoCups"));
		inicializarBandboxProcedimiento();
	}

	public void listarCombos() {
		listarParameter();
		listarAreasIntervencion();
	}

	private void listarAreasIntervencion() {
		List<Pyp> pyps = getServiceLocator().getServicio(GeneralExtraService.class)
				.listar(Pyp.class,new HashMap<String, Object>());
		lbxCodigo_pyp.appendChild(new Listitem(" -seleccione- ", ""));
		for (Pyp pyp : pyps) {
			lbxCodigo_pyp.appendChild(new Listitem(pyp.getNombre(), pyp
					.getCodigo()));
		}
		if (lbxCodigo_pyp.getItemCount() > 0) {
			lbxCodigo_pyp.setSelectedIndex(0);
		}
	}

	private void inicializarBandboxProcedimiento() {
		BandboxRegistrosIMG<Procedimientos> bandboxRegistrosIMG = new BandboxRegistrosIMG<Procedimientos>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Procedimientos registro) {
				bandbox.setValue(registro.getCodigo_cups());
				textboxInformacion.setValue(registro.getDescripcion());
				textboxInformacion.setTooltiptext(registro.getDescripcion());
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem,
					Procedimientos registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo_cups() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getDescripcion() + ""));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Procedimientos> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				procedimientosService.setLimit("limit 25 offset 0");
				return procedimientosService.listar(parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				tbxDescripcion.setTooltiptext("");
			}
		};
		/* inyectamos el mismo evento */
		tbxCodigo_cups.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("pyp.nombre");
		listitem.setLabel("Area intervencion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("ex_p.codigo_cups");
		listitem.setLabel("Codigo cups");
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
	public boolean validarForm() throws Exception {

		lbxCodigo_pyp.setStyle("background-color:white");
		tbxCodigo_cups
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		if (lbxCodigo_pyp.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxCodigo_pyp.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (tbxCodigo_cups.getText().trim().equals("")) {
			tbxCodigo_cups
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
			parameters.put("value", "%" + value + "%");

			excepciones_pypService.setLimit("limit 25 offset 0");

			List<Excepciones_pyp> lista_datos = excepciones_pypService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Excepciones_pyp excepciones_pyp : lista_datos) {
				rowsResultado.appendChild(crearFilas(excepciones_pyp, this));
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

		final Excepciones_pyp excepciones_pyp = (Excepciones_pyp) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		Procedimientos procedimientos = new Procedimientos();
		procedimientos.setId_procedimiento(new Long(excepciones_pyp
				.getCodigo_procedimiento()));
		procedimientos = (Procedimientos) procedimientosService
				.consultar(procedimientos);

		Pyp pyp = new Pyp();
		pyp.setCodigo(excepciones_pyp.getCodigo_pyp());
		pyp = getServiceLocator().getServicio(GeneralExtraService.class).consultar(pyp);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(pyp != null ? pyp.getNombre() : ""));
		fila.appendChild(new Label(excepciones_pyp.getCodigo_procedimiento() + ""));
		fila.appendChild(new Label(procedimientos != null ? procedimientos
				.getDescripcion() : ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(excepciones_pyp);
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
									eliminarDatos(excepciones_pyp);
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
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //

				Excepciones_pyp excepciones_pyp = new Excepciones_pyp();
				excepciones_pyp.setCodigo_pyp(lbxCodigo_pyp.getSelectedItem()
						.getValue().toString());
				excepciones_pyp.setCodigo_procedimiento(tbxCodigo_cups.getValue());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					excepciones_pypService.crear(excepciones_pyp);
					accionForm(true, "registrar");
				} else {
					int result = excepciones_pypService
							.actualizar(excepciones_pyp);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Excepciones_pyp excepciones_pyp = (Excepciones_pyp) obj;
		try {
			for (int i = 0; i < lbxCodigo_pyp.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_pyp.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(excepciones_pyp.getCodigo_pyp())) {
					listitem.setSelected(true);
					i = lbxCodigo_pyp.getItemCount();
				}
			}

			Procedimientos procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(excepciones_pyp
					.getCodigo_procedimiento()));
			procedimientos = (Procedimientos) procedimientosService
					.consultar(procedimientos);

			if (procedimientos != null)
				tbxCodigo_cups.seleccionarRegistro(procedimientos,
						procedimientos.getCodigo_cups(),
						procedimientos.getDescripcion());
			else {
				MensajesUtil.mensajeAlerta("Advertencia", "El codigo cups "
						+ excepciones_pyp.getCodigo_procedimiento() + " no existe.");
			}

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void cargarDatosSesion() {
	}

	public void eliminarDatos(Object obj) throws Exception {
		Excepciones_pyp excepciones_pyp = (Excepciones_pyp) obj;
		try {
			int result = excepciones_pypService.eliminar(excepciones_pyp);
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
}