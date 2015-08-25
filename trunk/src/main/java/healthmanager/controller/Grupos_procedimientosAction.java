/*
 * grupos_procedimientosAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_grupos_procedimientos;
import healthmanager.modelo.bean.Grupos_procedimientos;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Detalle_grupos_procedimientosService;
import healthmanager.modelo.service.Grupos_procedimientosService;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;
import healthmanager.modelo.service.ProcedimientosService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Grupos_procedimientosAction extends ZKWindow implements
		WindowRegistrosIMG {

	private static final String PROCEDIMIENTO = "pro_";

	private Grupos_procedimientosService grupos_procedimientosService;

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
	private Textbox tbxCodigo_cups_grupo;
	@View
	private Textbox tbxDescipcion;

	@View
	private Listbox lbxProcedimientos;

	@View
	private Listbox lbxSexo;
	@View
	private Intbox ibxFrecuencia;
	@View
	private Listbox lbxTipoProcedimiento;

	private final String[] idsExcluyentes = {};

	private List<String> lista_seleccionados_procedimientos = new ArrayList<String>();

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {
		listarParameter();
		listarTiposProcedimiento();
		listarSexos();
	}

	private void listarSexos() {
		String[][] sexos = { { "A", "AMBOS" }, { "M", "MASCULINO" },
				{ "F", "FEMENINO" } };
		lbxSexo.setZclass("combobox");
		lbxSexo.setMold("select");
		for (String[] unidad_medida : sexos) {
			lbxSexo.appendChild(new Listitem(unidad_medida[1], unidad_medida[0]));
		}
		if (lbxSexo.getItemCount() > 0) {
			lbxSexo.setSelectedIndex(0);
		}
	}

	private void listarTiposProcedimiento() {
		String[][] tipos_procedimientos = {
				{ ServiciosDisponiblesUtils.TIPO_LABORATORIO_CLINICO,
						"LABORATORIOS" },
				{ ServiciosDisponiblesUtils.TIPO_IMAGENEOLOGIA,
						"IMAGENES DIAGNOSTICAS" } };
		lbxTipoProcedimiento.setZclass("combobox");
		lbxTipoProcedimiento.setMold("select");
		for (String[] unidad_medida : tipos_procedimientos) {
			lbxTipoProcedimiento.appendChild(new Listitem(unidad_medida[1],
					unidad_medida[0]));
		}
		if (lbxTipoProcedimiento.getItemCount() > 0) {
			lbxTipoProcedimiento.setSelectedIndex(0);
		}

		lbxTipoProcedimiento.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event evt) throws Exception {

					}
				});
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("id_codigo_grupo");
		listitem.setLabel("Código grupo");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("descipcion");
		listitem.setLabel("Descipcion");
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

	public void abrirWindowProcedimientos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nivel_orden", getEmpresa().getNivel());
		parametros.put("es_grupo", "N");
		// parametros.put("codigo_tipo_procedimiento",
		// lbxTipoProcedimiento.getSelectedItem().getValue()
		// .toString());
		String columnas = "Código cups#100px|Descripcion";
		WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
				"Procedimientos", Paquetes.HEALTHMANAGER,
				"ProcedimientosDao.listar", this, columnas, parametros);
		windowRegistros.mostrarWindow(lista_seleccionados_procedimientos);
	}

	public void eliminarProcedimientos() {
		Set<Listitem> listitems = lbxProcedimientos.getSelectedItems();
		if (listitems.isEmpty()) {
			MensajesUtil
					.mensajeAlerta("Advertencia",
							"Para realizar esta accion debe seleccionar por lo menos un item");
		} else {
			List<Listitem> listitemsTemp = new ArrayList<Listitem>(listitems);
			for (Listitem listitem : listitemsTemp) {
				lbxProcedimientos.getItems().remove(listitem);
			}
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		lista_seleccionados_procedimientos.clear();
		lbxProcedimientos.getItems().clear();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		tbxDescipcion
				.setStyle("text-transform:uppercase;background-color:white");
		ibxFrecuencia
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;
		String msj = "Los campos marcados con (*) son obligatorios";

		if (tbxDescipcion.getText().trim().equals("")) {
			tbxDescipcion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (ibxFrecuencia.getValue() == null && valida) {
			ibxFrecuencia
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			msj = "Valor de frecuencia de ordenamiento es requerido";
			valida = false;
		}

		if (lbxProcedimientos.getItems().size() < 2 && valida) {
			msj = "Para realizar esta accion por menos debe agregar 2 procedimientos";
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", msj);
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

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Grupos_procedimientos> lista_datos = grupos_procedimientosService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Grupos_procedimientos grupos_procedimientos : lista_datos) {
				rowsResultado.appendChild(crearFilas(grupos_procedimientos,
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

		final Grupos_procedimientos grupos_procedimientos = (Grupos_procedimientos) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(grupos_procedimientos.getId_codigo_grupo()
				+ ""));
		fila.appendChild(new Label(grupos_procedimientos.getDescipcion() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(grupos_procedimientos);
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
									eliminarDatos(grupos_procedimientos);
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
				Grupos_procedimientos grupos_procedimientos = new Grupos_procedimientos();
				grupos_procedimientos.setCodigo_empresa(empresa
						.getCodigo_empresa());
				grupos_procedimientos.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				grupos_procedimientos.setId_codigo_grupo(tbxCodigo_cups_grupo
						.getValue());
				grupos_procedimientos.setDescipcion(tbxDescipcion.getValue());
				grupos_procedimientos.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				grupos_procedimientos.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				grupos_procedimientos.setCreacion_user(usuarios.getCodigo()
						.toString());
				grupos_procedimientos.setUltimo_user(usuarios.getCodigo()
						.toString());

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("grupos_pro", grupos_procedimientos);
				map.put("accion", tbxAccion.getText());
				map.put("dtt_grupos", getDetalleGrupos());
				map.put("tipo_procedimiento", lbxTipoProcedimiento
						.getSelectedItem().getValue());
				map.put("sexo", lbxSexo.getSelectedItem().getValue());
				map.put("frecuencia_ordenamiento", ibxFrecuencia.getValue());
				map.put("nivel", empresa.getNivel());
				grupos_procedimientosService.guardar(map);

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private List<Detalle_grupos_procedimientos> getDetalleGrupos() {
		List<Detalle_grupos_procedimientos> detalle_grupos_procedimientos = new ArrayList<Detalle_grupos_procedimientos>();
		List<Listitem> listitems = lbxProcedimientos.getItems();
		for (Listitem listitem : listitems) {
			detalle_grupos_procedimientos
					.add(convertir((Procedimientos) listitem.getValue()));
		}
		return detalle_grupos_procedimientos;
	}

	public Detalle_grupos_procedimientos convertir(Procedimientos procedimientos) {
		Detalle_grupos_procedimientos detalle_grupos_procedimientos = new Detalle_grupos_procedimientos();
		detalle_grupos_procedimientos.setCodigo_empresa(empresa
				.getCodigo_empresa());
		detalle_grupos_procedimientos.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		detalle_grupos_procedimientos.setId_procedimiento(procedimientos
				.getId_procedimiento() + "");
		detalle_grupos_procedimientos.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		detalle_grupos_procedimientos.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		detalle_grupos_procedimientos.setCreacion_user(usuarios.getCodigo()
				.toString());
		detalle_grupos_procedimientos.setUltimo_user(usuarios.getCodigo()
				.toString());
		return detalle_grupos_procedimientos;
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Grupos_procedimientos grupos_procedimientos = (Grupos_procedimientos) obj;
		try {
			tbxCodigo_cups_grupo.setValue(grupos_procedimientos
					.getId_codigo_grupo());
			tbxDescipcion.setValue(grupos_procedimientos.getDescipcion());

			Procedimientos procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(new Long(grupos_procedimientos
					.getId_codigo_grupo()));
			procedimientos = getServiceLocator().getServicio(
					ProcedimientosService.class).consultar(procedimientos);

			if (procedimientos != null) {
				ibxFrecuencia.setValue(procedimientos.getFrecuencia_orden());
				Utilidades.setValueFrom(lbxSexo, procedimientos.getSexo());
				Utilidades.setValueFrom(lbxTipoProcedimiento,
						procedimientos.getTipo_procedimiento());
			}

			// Listamos detalles
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", grupos_procedimientos.getCodigo_empresa());
			map.put("codigo_sucursal",
					grupos_procedimientos.getCodigo_sucursal());
			map.put("codigo_grupo", grupos_procedimientos.getId_codigo_grupo());
			List<Detalle_grupos_procedimientos> dtt_grupos = getServiceLocator()
					.getServicio(Detalle_grupos_procedimientosService.class)
					.listar(map);
			for (Detalle_grupos_procedimientos detalle_grupos_procedimientos : dtt_grupos) {
				Procedimientos procedimiento_Temp = new Procedimientos();
				procedimiento_Temp.setId_procedimiento(new Long(
						detalle_grupos_procedimientos.getId_procedimiento()));
				procedimiento_Temp = getServiceLocator().getServicio(
						ProcedimientosService.class).consultar(
						procedimiento_Temp);
				if (procedimiento_Temp != null) {
					onSeleccionarProcedimiento(procedimiento_Temp);
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
		Grupos_procedimientos grupos_procedimientos = (Grupos_procedimientos) obj;
		try {
			int result = grupos_procedimientosService
					.eliminar(grupos_procedimientos);
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

	@Override
	public void onSeleccionarRegistro(Object registro) {
		try {
			//log.info("Registro seleccionado: " + registro);
			if (registro instanceof Procedimientos) {
				onSeleccionarProcedimiento((Procedimientos) registro);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void onSeleccionarProcedimiento(Procedimientos registro) {
		crearFilasProcedimientos(cargarProcedimiento(registro));
	}

	private void crearFilasProcedimientos(Map<String, Object> bean) {
		Procedimientos procedimiento = (Procedimientos) bean.get(PROCEDIMIENTO);
		Listitem listitem = new Listitem();
		listitem.setValue(procedimiento);
		listitem.appendChild(new Listcell(procedimiento.getCodigo_cups()));
		listitem.appendChild(new Listcell(procedimiento.getDescripcion()));
		lbxProcedimientos.appendChild(listitem);
	}

	private Map<String, Object> cargarProcedimiento(Procedimientos registro) {
		Map<String, Object> bean = new HashMap<String, Object>();
		bean.put(PROCEDIMIENTO, registro);
		return bean;
	}

	@Override
	public Object[] celdasListitem(Object registro) {
		if (registro instanceof Procedimientos) {
			return celdasListitemProcedimiento((Procedimientos) registro);
		}
		return null;
	}

	private Object[] celdasListitemProcedimiento(Procedimientos registro) {
		return new Object[] { registro.getCodigo_cups(),
				registro.getDescripcion() };
	}
}