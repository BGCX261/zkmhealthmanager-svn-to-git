package healthmanager.controller;

import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.ProcedimientosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IKeyCode;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.service.ArticuloService;

public class ConfigCantidadesServicioAction extends ZKWindow {

	@View
	private Rows rowsResultado;
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Grid gridResultado;

	@View
	private Checkbox chkConfigConsultas;
	@View
	private Checkbox chkConfigProcedimientos;
	@View
	private Checkbox chkConfigMedicamentos;
	@View
	private Checkbox chkConfigInsumos;
	@View
	private Checkbox chkConfigServicios;

	@View
	private Intbox ibxAccesoRapido;
	@View
	private Checkbox chkTodos;

	@View
	private Toolbarbutton btnConfiguracion;
	
	
	private final String TIPO_PROCEDIMIENTO = "01";
	private final String TIPO_ARTICULO = "02";

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	private List<Row> listado_seleccionado = new ArrayList<Row>();

	@Override
	public void init() throws Exception {
		listarParameter();
		parametrizarResultadoPaginado();
		inicializarEvento();
	}

	private void inicializarEvento() {
		addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				ejecutarEvento(((KeyEvent) event).getKeyCode());
			}
		});
	}

	public void seleccionarTodos() {
		List<Component> listado = rowsResultado.getChildren();
		for (Component component : listado) {
			if (component instanceof Row) {
				Checkbox checkbox = (Checkbox) ((Row) component)
						.getFirstChild();
				if (chkTodos.isChecked()) {
					listado_seleccionado.add((Row) component);
					checkbox.setChecked(true);
				} else {
					listado_seleccionado.remove((Row) component);
					checkbox.setChecked(false);
				}
			}
		}
	}

	private void ejecutarEvento(int keycode) {
		try {
//			log.info("Key: " + keycode);  
			switch (keycode) {
			case IKeyCode.ALT_A: // colocar el foco
				ibxAccesoRapido.setFocus(true);
				break;
			case IKeyCode.ALT_R: // para alplicar
				aplicarResultadoAccesoRapido();
				break;
			case IKeyCode.ALT_G: 
				aplicarGuardadoRapido();
				break;
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_presentacion");
		listitem.setLabel("Código");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("upper(nombre)");
		listitem.setLabel("Nombres");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void buscarDatos() throws Exception {
		try {
			listado_seleccionado.clear();
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("tipo_in", getFiltroTipo());
			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private List<String> getFiltroTipo() {
		List<String> listado_filtro = new ArrayList<String>();

		adicionarListado(listado_filtro, chkConfigConsultas, TIPO_PROCEDIMIENTO, "S");
		adicionarListado(listado_filtro, chkConfigProcedimientos, TIPO_PROCEDIMIENTO, "N");
		adicionarListado(listado_filtro, chkConfigMedicamentos, TIPO_ARTICULO, "01");
		adicionarListado(listado_filtro, chkConfigInsumos, TIPO_ARTICULO, "02");
		adicionarListado(listado_filtro, chkConfigServicios, TIPO_ARTICULO, "03");

		if (!listado_filtro.isEmpty()) {
			btnConfiguracion.setImage("/images/filtro1.png");
			return listado_filtro;
		} else {
			btnConfiguracion.setImage("/images/filtro.png");
			return null;
		}
	}

	private void adicionarListado(List<String> listado_filtro, Checkbox chk,
			String grupo, String tipo) {
		if (chk.isChecked()) {
			listado_filtro.add(grupo.concat(tipo));
		}
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Map<String, Object>> listarResultados(
					Map<String, Object> parametros) {
				listado_seleccionado.clear();
				chkTodos.setChecked(false);
				return getServiceLocator().getFacturacionService()
						.listarServicios(parametros);
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				return getServiceLocator().getFacturacionService()
						.getTotalServicios(parametros).intValue();
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilas(dato);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado, gridResultado.getColumns().getChildren().size());
	}

	public Row crearFilas(Object objeto) throws Exception {
		final Map<String, Object> map_resultado = (Map<String, Object>) objeto;
		String codigo_presentacion = (String) map_resultado
				.get("codigo_presentacion");
		String nombre = (String) map_resultado.get("nombre");
		String tipo_descripcion = (String) map_resultado
				.get("tipo_descripcion");
		int cantidad_maxima = (Integer) map_resultado.get("cantidad_maxima");

		map_resultado.put("cantidad_inicial", cantidad_maxima);

		final Row fila = new Row();
		fila.setValue(map_resultado);
		fila.setStyle("text-align: justify;nowrap:nowrap");

		Checkbox checkbox = new Checkbox();
		checkbox.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Checkbox checkbox = (Checkbox) arg0.getTarget();
				if (checkbox.isChecked()) {
					listado_seleccionado.add(fila);
				} else {
					listado_seleccionado.remove(fila);
				}
				// para verificar el total seleccionado
				chkTodos.setChecked(listado_seleccionado.size() == rowsResultado
						.getChildren().size());
			}
		});

		fila.appendChild(checkbox);
		fila.appendChild(FormularioUtil.getLabelSize(codigo_presentacion,
				"11px"));
		fila.appendChild(FormularioUtil.getLabelSize(nombre, "11px"));
		fila.appendChild(FormularioUtil.getLabelSize(tipo_descripcion, "11px"));

		Intbox intbox = new Intbox();
		intbox.setValue(cantidad_maxima == 0 ? null : cantidad_maxima);
		intbox.setMaxlength(2);
		intbox.setHflex("1");
		fila.setAttribute("intbox", intbox);
		intbox.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@Override
			public void onEvent(Event evt) throws Exception {
				onAccionCambioCantidad((Intbox) evt.getTarget(), map_resultado);
			}

		});
		fila.appendChild(intbox);

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setVisible(false);
		toolbarbutton.setImage("/images/editar.gif");
		toolbarbutton.setTooltiptext("Editar");
		toolbarbutton.setStyle("cursor: pointer");
		intbox.setAttribute("btn", toolbarbutton);
		toolbarbutton.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				String tabla = (String) map_resultado.get("tabla");
				actualizarRegistro(
						map_resultado,
						tabla.equals(TIPO_PROCEDIMIENTO) ? getServiceLocator().getServicio(
								ProcedimientosService.class)
								: getServiceLocator().getServicio(
										ArticuloService.class),
						(Toolbarbutton) arg0.getTarget());
			}
		});
		fila.appendChild(toolbarbutton);
		return fila;
	}

	public void aplicarGuardadoRapido() {
		try {
			ProcedimientosService procedimientosService = getServiceLocator().getServicio(ProcedimientosService.class);
			ArticuloService articuloService = getServiceLocator().getServicio(ArticuloService.class);
			for (Component cmp : rowsResultado.getChildren()) {
				if (cmp instanceof Row) {
					Row row = (Row) cmp;
					Toolbarbutton toolbarbutton = (Toolbarbutton) row
							.getLastChild();
					if (toolbarbutton.isVisible()) {
						Map<String, Object> map_resultado = row.getValue();
						String tabla = (String) map_resultado.get("tabla");
						actualizarRegistro(map_resultado,
								tabla.equals(TIPO_PROCEDIMIENTO) ? procedimientosService
										: articuloService, toolbarbutton);
					}
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	public void aplicarResultadoAccesoRapido() {
		try {
			Integer valor_acceso_rapido = ibxAccesoRapido.getValue();
			for (Row row : listado_seleccionado) {
				Map<String, Object> map = row.getValue();
				Intbox intbox = (Intbox) row.getAttribute("intbox");
				intbox.setValue(valor_acceso_rapido);
				onAccionCambioCantidad(intbox, map);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	protected void onAccionCambioCantidad(Intbox intbox,
			Map<String, Object> map_resultado) {
		Integer valor = intbox.getValue();
		if (valor != null) {
			map_resultado.put("cantidad_maxima", valor);
			if (valor.intValue() == 0) {
				intbox.setValue(null);
			}
		} else {
			map_resultado.put("cantidad_maxima", 0);
		}

		Integer cantidad_inicial = (Integer) map_resultado
				.get("cantidad_inicial");
		Integer cantidad_maxima = (Integer) map_resultado
				.get("cantidad_maxima");

		Toolbarbutton toolbarbutton = (Toolbarbutton) intbox
				.getAttribute("btn");
		toolbarbutton.setVisible((cantidad_inicial != cantidad_maxima));
	}

	/**
	 * Este metodo me permite actualizar el registro
	 * */
	protected void actualizarRegistro(Map<String, Object> map_resultado,
			Object servicio, Toolbarbutton toolbarbutton) {
		String tabla = (String) map_resultado.get("tabla");
		String id = (String) map_resultado.get("id");
		int cantidad_maxima = (Integer) map_resultado.get("cantidad_maxima");
		if (tabla.equals(TIPO_PROCEDIMIENTO)) {
			Procedimientos procedimientos = new Procedimientos();
			procedimientos.setId_procedimiento(Long.parseLong(id));
			procedimientos.setCantidad_maxima(cantidad_maxima);
			((ProcedimientosService) servicio)
					.actualizarPorDmanda(procedimientos);
		} else {
			Articulo articulo = new Articulo();
			articulo.setCodigo_empresa(codigo_empresa);
			articulo.setCodigo_sucursal(codigo_sucursal); 
			articulo.setCodigo_articulo(id);
			articulo.setCantidad_maxima(cantidad_maxima);
			((ArticuloService) servicio).actualizarPorDmanda(articulo);
		}
		toolbarbutton.setVisible(false);
		map_resultado.put("cantidad_inicial", cantidad_maxima);
	}
}
