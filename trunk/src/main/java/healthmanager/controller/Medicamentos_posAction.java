/*
 * articuloAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Tipo_procedimiento;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Contabilizacion;
import contaweb.modelo.bean.Elemento;
import contaweb.modelo.bean.Grupo1;
import contaweb.modelo.bean.Grupo2;
import contaweb.modelo.bean.Medicamentos_pos;
import contaweb.modelo.bean.Presentacion_articulo;
import contaweb.modelo.bean.Unidad_medida;
import contaweb.modelo.service.ElementoService;
import contaweb.modelo.service.Medicamentos_posService;
import contaweb.modelo.service.Presentacion_articuloService;
import contaweb.modelo.service.Unidad_medidaService;

public class Medicamentos_posAction extends ZKWindow {

	private static Logger log = Logger.getLogger(Medicamentos_posAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

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
	private Textbox tbxCodigo;
	@View
	private Textbox tbxNombre;
	@View
	private Textbox tbxConcentracion;
	@View
	private Textbox tbxObservacion;

	private Medicamentos_pos medicamentos_pos_modificado;

	private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		listarCombos();
	}

	public void initArticulo() throws Exception {
		// HttpServletRequest request = (HttpServletRequest) Executions
		// .getCurrent().getNativeRequest();
		try {

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nombre");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = getServiceLocator().getServicio(
				ElementoService.class).listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarGrupo1(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map param = new HashMap();
		param.put("codigo_empresa", empresa.getCodigo_empresa());
		param.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		List<Grupo1> lista_grupo1 = getServiceLocator().getGrupo1Service()
				.listar(param);
		for (Grupo1 grupo1 : lista_grupo1) {
			listitem = new Listitem();
			listitem.setValue(grupo1.getCodigo());
			listitem.setLabel(grupo1.getNombre());
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarGrupo2(Listbox listbox1, Listbox listbox2, boolean select) {
		listbox2.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox2.appendChild(listitem);
		}

		Map param = new HashMap();
		param.put("codigo_empresa", empresa.getCodigo_empresa());
		param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		param.put("codigo_grupo1", listbox1.getSelectedItem().getValue());

		List<Grupo2> lista_grupo2 = getServiceLocator().getGrupo2Service()
				.listar(param);
		for (Grupo2 grupo2 : lista_grupo2) {
			listitem = new Listitem();
			listitem.setValue(grupo2.getCodigo());
			listitem.setLabel(grupo2.getNombre());
			listbox2.appendChild(listitem);
		}

		if (listbox2.getItemCount() > 0) {
			listbox2.setSelectedIndex(0);
		}
	}

	public void listarUnidad_medida(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map param = new HashMap();
		param.put("codigo_empresa", empresa.getCodigo_empresa());
		param.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		List<Unidad_medida> lista_unidad_medida = getServiceLocator()
				.getServicio(Unidad_medidaService.class).listar(param);
		for (Unidad_medida unidad_medida : lista_unidad_medida) {
			listitem = new Listitem();
			listitem.setValue(unidad_medida.getCodigo());
			listitem.setLabel(unidad_medida.getNombre());
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarPresentacion(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map param = new HashMap();
		param.put("codigo_empresa", empresa.getCodigo_empresa());
		param.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		List<Presentacion_articulo> lista_presentacion_articulos = getServiceLocator()
				.getServicio(Presentacion_articuloService.class).listar(param);
		for (Presentacion_articulo presentacion_articulo : lista_presentacion_articulos) {
			listitem = new Listitem();
			listitem.setValue(presentacion_articulo.getCodigo());
			listitem.setLabel(presentacion_articulo.getNombre());
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarVencimiento(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		listitem = new Listitem();
		listitem.setValue("Vigente");
		listitem.setLabel("Vigente");
		listbox.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("En tramite renov");
		listitem.setLabel("En tramite renov");
		listbox.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("Temp. no comerc.");
		listitem.setLabel("Temp. no comerc.");
		listbox.appendChild(listitem);

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarContabilizacion(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map param = new HashMap();
		param.put("codigo_empresa", empresa.getCodigo_empresa());
		param.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		List<Contabilizacion> lista_contabilizacion = getServiceLocator()
				.getContabilizacionService().listar(param);
		for (Contabilizacion contabilizacion : lista_contabilizacion) {
			listitem = new Listitem();
			listitem.setValue(contabilizacion.getCodigo());
			listitem.setLabel(contabilizacion.getNombre());
			listbox.appendChild(listitem);
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarUnd_unm(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		listitem = new Listitem();
		listitem.setValue("Und");
		listitem.setLabel("Unidad");
		listbox.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("Gr");
		listitem.setLabel("Gramos");
		listbox.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("Mg");
		listitem.setLabel("Miligramos");
		listbox.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("Ml");
		listitem.setLabel("Mililitros");
		listbox.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("Mcg");
		listitem.setLabel("Microgramos");
		listbox.appendChild(listitem);

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarTipo_procedimiento(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map param = new HashMap();
		param.put("codigo_empresa", empresa.getCodigo_empresa());
		param.put("codigo_sucursal", sucursal.getCodigo_sucursal());

		List<Tipo_procedimiento> lista_tipo_procedimiento = getServiceLocator()
				.getTipo_procedimientoService().listar(param);
		for (Tipo_procedimiento tipo_procedimiento : lista_tipo_procedimiento) {
			listitem = new Listitem();
			listitem.setValue(tipo_procedimiento.getCodigo());
			listitem.setLabel(tipo_procedimiento.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
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
		tbxCodigo.setStyle("text-transform:uppercase;background-color:white");
		tbxNombre.setStyle("text-transform:uppercase;background-color:white");
		tbxConcentracion
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;
		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo.getText().trim().equals("")) {
			tbxCodigo
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxNombre.getText().trim().equals("")) {
			tbxNombre
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String codigo_empresa = empresa.getCodigo_empresa();
			String codigo_sucursal = sucursal.getCodigo_sucursal();
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getServicio(Medicamentos_posService.class)
					.setLimit("limit 300 offset 0");

			List<Medicamentos_pos> lista_datos = getServiceLocator()
					.getServicio(Medicamentos_posService.class).listar(
							parameters);
			rowsResultado.getChildren().clear();

			for (Medicamentos_pos medicamentos_pos : lista_datos) {
				rowsResultado.appendChild(crearFilas(medicamentos_pos, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Medicamentos_pos medicamentos_pos = (Medicamentos_pos) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(medicamentos_pos.getCodigo() + ""));
		// fila.appendChild(new Label(articulo.getReferencia_articulo()));
		fila.appendChild(new Label(medicamentos_pos.getNombre() + ""));
		fila.appendChild(new Label(medicamentos_pos.getConcentracion() + ""));
		fila.appendChild(new Label(medicamentos_pos.getObservaciones() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(medicamentos_pos);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/eliminar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(medicamentos_pos);
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
				Medicamentos_pos medicamentos_pos = new Medicamentos_pos();
				medicamentos_pos.setCodigo(tbxCodigo.getValue());
				medicamentos_pos.setNombre(tbxNombre.getValue().trim());
				medicamentos_pos.setConcentracion(tbxConcentracion.getValue());
				medicamentos_pos.setObservaciones(tbxObservacion.getValue());
				medicamentos_pos.setUltimo_update(new Timestamp(new Date()
						.getTime()));
				medicamentos_pos.setUltimo_user(usuarios.getCodigo());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					medicamentos_pos.setCreacion_user(usuarios.getCodigo());
					medicamentos_pos.setCreacion_date(new Timestamp(new Date()
							.getTime()));
					getServiceLocator().getServicio(
							Medicamentos_posService.class).crear(
							medicamentos_pos);
					accionForm(true, "registrar");
				} else {
					medicamentos_pos
							.setCreacion_date(medicamentos_pos_modificado
									.getCreacion_date());
					medicamentos_pos
							.setCreacion_user(medicamentos_pos_modificado
									.getCreacion_user());
					int result = getServiceLocator().getServicio(
							Medicamentos_posService.class).actualizar(
							medicamentos_pos);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");

			}

		} catch (Exception e) {
			// TODO: handle exception
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		medicamentos_pos_modificado = (Medicamentos_pos) obj;
		try {
			tbxCodigo.setValue(medicamentos_pos_modificado.getCodigo());
			tbxNombre.setValue(medicamentos_pos_modificado.getNombre());
			tbxConcentracion.setValue(medicamentos_pos_modificado
					.getConcentracion());
			tbxObservacion.setValue(medicamentos_pos_modificado
					.getObservaciones());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Medicamentos_pos medicamentos_pos = (Medicamentos_pos) obj;
		try {
			int result = getServiceLocator().getServicio(
					Medicamentos_posService.class).eliminar(medicamentos_pos);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se elimino satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

}
