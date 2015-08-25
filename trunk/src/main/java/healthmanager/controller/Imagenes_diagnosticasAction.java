/*
 * imagenes_diagnosticasAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Imagenes_diagnosticas;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Imagenes_diagnosticasService;
import healthmanager.modelo.service.ProcedimientosService;

import java.sql.Timestamp;
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

public class Imagenes_diagnosticasAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Imagenes_diagnosticasAction.class);

	private Imagenes_diagnosticasService imagenes_diagnosticasService;
	private ProcedimientosService procedimientosService;

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

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_cups;
	@View
	private Textbox tbxDescripcion;
	@View
	private Listbox lbxTipo_imagen;
	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		listarCombos();
		tbxCodigo_cups.inicializar(tbxDescripcion,
				(Toolbarbutton) getFellow("btnLimpiarCuentaCobrar"));
		parametrizarBandbox();
	}

	private void parametrizarBandbox() {
		inicializarBandboxProcedimiento();
	}

	private void inicializarBandboxProcedimiento() {
		BandboxRegistrosIMG<Procedimientos> bandboxRegistrosIMG = new BandboxRegistrosIMG<Procedimientos>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Procedimientos registro) {
				bandbox.setValue(registro.getCodigo_cups());
				textboxInformacion.setValue(registro.getDescripcion());

				Imagenes_diagnosticas laboratorios = new Imagenes_diagnosticas();
				laboratorios.setCodigo_empresa(codigo_empresa);
				laboratorios.setCodigo_sucursal(codigo_sucursal);
				laboratorios.setCodigo_cups(bandbox.getValue());
				final Imagenes_diagnosticas laboratorioTemp = imagenes_diagnosticasService
						.consultar(laboratorios);
				//log.info("vacuna: " + laboratorioTemp);
				if (laboratorioTemp != null) {
					Messagebox
							.show("Ya existe un esta imagen diagnostica, desea cargarla? ",
									"Informacion",
									Messagebox.YES + Messagebox.NO,
									Messagebox.QUESTION,
									new org.zkoss.zk.ui.event.EventListener<Event>() {
										public void onEvent(Event event)
												throws Exception {
											if ("onYes".equals(event.getName())) {
												mostrarDatos(laboratorioTemp);
											}
										}
									});
				}
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

			}
		};
		/* inyectamos el mismo evento */
		tbxCodigo_cups.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	public void listarCombos() {
		listarParameter();
		listarTiposImagenDiagnostica();
	}

	private void listarTiposImagenDiagnostica() {
//		Map<String, Object> map = new HashMap<String, Object>();
		lbxTipo_imagen.appendChild(new Listitem("-seleccione-", ""));
		
		if (lbxTipo_imagen.getItemCount() > 0) {
			lbxTipo_imagen.setSelectedIndex(0);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_cups");
		listitem.setLabel("Codigo cups");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("tipo_imagen");
		listitem.setLabel("Tipo imagen diagnostica");
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

		lbxTipo_imagen.setStyle("background-color:white");

		boolean valida = true;

		if (lbxTipo_imagen.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxTipo_imagen.setStyle("background-color:#F6BBBE");
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

			imagenes_diagnosticasService.setLimit("limit 25 offset 0");

			List<Imagenes_diagnosticas> lista_datos = imagenes_diagnosticasService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Imagenes_diagnosticas imagenes_diagnosticas : lista_datos) {
				rowsResultado.appendChild(crearFilas(imagenes_diagnosticas,
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

		final Imagenes_diagnosticas imagenes_diagnosticas = (Imagenes_diagnosticas) objeto;

		Procedimientos procedimiento_iss01 = new Procedimientos();
		procedimiento_iss01.setId_procedimiento(new Long(imagenes_diagnosticas
				.getCodigo_cups()));
		procedimiento_iss01 = procedimientosService
				.consultar(procedimiento_iss01);

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(imagenes_diagnosticas.getCodigo_cups() + ""));
		fila.appendChild(new Label(
				procedimiento_iss01 != null ? procedimiento_iss01
						.getDescripcion() : ""));
		fila.appendChild(new Label( ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(imagenes_diagnosticas);
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
									eliminarDatos(imagenes_diagnosticas);
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
				Imagenes_diagnosticas imagenes_diagnosticas = new Imagenes_diagnosticas();
				imagenes_diagnosticas.setCodigo_empresa(empresa
						.getCodigo_empresa());
				imagenes_diagnosticas.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				imagenes_diagnosticas.setCodigo_cups(tbxCodigo_cups.getValue());
//				imagenes_diagnosticas.setTipo_imagen(unidad_funcional
//						.getCodigo());
				imagenes_diagnosticas.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				imagenes_diagnosticas.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				imagenes_diagnosticas.setCreacion_user(usuarios.getCodigo()
						.toString());
				imagenes_diagnosticas.setUltimo_user(usuarios.getCodigo()
						.toString());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					imagenes_diagnosticasService.crear(imagenes_diagnosticas);
					accionForm(true, "registrar");
				} else {
					int result = imagenes_diagnosticasService
							.actualizar(imagenes_diagnosticas);
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
		Imagenes_diagnosticas imagenes_diagnosticas = (Imagenes_diagnosticas) obj;
		try {
			tbxCodigo_cups.setValue(imagenes_diagnosticas.getCodigo_cups());
			for (int i = 0; i < lbxTipo_imagen.getItemCount(); i++) {
				Listitem listitem = lbxTipo_imagen.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(imagenes_diagnosticas.getTipo_imagen())) {
					listitem.setSelected(true);
					i = lbxTipo_imagen.getItemCount();
				}
			}

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Imagenes_diagnosticas imagenes_diagnosticas = (Imagenes_diagnosticas) obj;
		try {
			int result = imagenes_diagnosticasService
					.eliminar(imagenes_diagnosticas);
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