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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
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

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Contabilizacion;
import contaweb.modelo.bean.Elemento;
import contaweb.modelo.bean.Grupo1;
import contaweb.modelo.bean.Grupo2;
import contaweb.modelo.bean.Medicamentos_pos;
import contaweb.modelo.bean.Presentacion_articulo;
import contaweb.modelo.bean.Tercero;
import contaweb.modelo.bean.Unidad_medida;
import contaweb.modelo.service.ElementoService;
import contaweb.modelo.service.Medicamentos_posService;
import contaweb.modelo.service.Presentacion_articuloService;
import contaweb.modelo.service.Unidad_medidaService;

public class ArticuloAction extends ZKWindow {

	private static Logger log = Logger.getLogger(ArticuloAction.class);
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
	private Textbox tbxCodigo_articulo;
	@View
	private Textbox tbxCodigo_barra;
	@View
	private Textbox tbxNombre1;
	@View
	private Textbox tbxNombre3;
	@View
	private Bandbox tbxReferencia;
	@View
	private Listbox lbxGrupo1;
	@View
	private Listbox lbxGrupo2;
	@View
	private Listbox lbxUnidad_medida;
	@View
	private Listbox lbxPresentacion;
	@View
	private Textbox tbxConcentracion;
	@View
	private Textbox tbxUnidad_concentracion;
	@View
	private Checkbox chbSin_existencia;
	@View
	private Checkbox chbProducto;
	@View
	private Checkbox chbVenta;
	@View
	private Checkbox chbPos;
	@View
	private Doublebox dbxCosto;
	@View
	private Doublebox dbxIva;
	@View
	private Doublebox dbxPrecio1;
	@View
	private Intbox ibxMaximo_permitido;
	@View
	private Intbox ibxMinimo_permitido;
	@View
	private Bandbox tbxFabricante;
	@View
	private Textbox tbxNomFabricante;
	@View
	private Textbox tbxObservaciones;
	@View
	private Textbox tbxCum;
	@View
	private Textbox tbxRegistro_sanitario;
	@View
	private Listbox lbxVencimiento;
	@View
	private Datebox dtbxEstado_registro;
	@View
	private Textbox tbxModalidad;
	@View
	private Textbox tbxCantidad;
	@View
	private Bandbox tbxTitular;
	@View
	private Textbox tbxNomTitular;
	@View
	private Label lbCodigo_contabilidad;
	@View
	private Listbox lbxCodigo_contabilidad;
	@View
	private Listbox lbxCodigo_unidad_funcional;
	@View
	private Intbox ibxUnm;
	@View
	private Listbox lbxUnd_unm;
	@View
	private Checkbox chbFacturable;
	@View
	private Checkbox chbServicio_gravado;
	@View
	private Checkbox chbServicio_intangible;
	@View
	private Textbox tbxReferencia_articulo;

	@View
	private Checkbox chbPyp;
	@View
	private Checkbox chbAlto_costo;
	@View
	private Listbox lbxVia;
	@View
	private Doublebox dbxValor_adicional;

	// private boolean importar;
	// private String id_menu;
	private String producto;

	@View
	private Checkbox chbActivo_fijo;
	@View
	private Datebox dtbxFecha_compra_activo;
	@View
	private Doublebox dbxValor_activo;
	@View
	private Intbox ibxVida_util;
	@View
	private Textbox tbxColor_activo;
	@View
	private Textbox tbxMarca_activo;
	@View
	private Textbox tbxModelo_activo;
	@View
	private Textbox tbxSerial_activo;
	@View
	private Listbox lbxEstado_activo;
	@View
	private Textbox tbxEmpleado;
	@View
	private Checkbox chbDepreciable;
	@View
	private Textbox tbxCargo_empleado;
	@View
	private Textbox tbxPuesto_empleado;
	@View
	private Checkbox chbVademecumInstitucional;
	@View
	private Row rowMedicamento;
	@View
	private Row rowActivos_fijos;

	/* componentes agregados utilizados en la Caja */
	@View
	private Checkbox chbComercial;
	@View
	private Textbox tbxNombreComercialMedicamento;

	@View
	private Checkbox chbDiferido;

	private final String[] idsExcluyentes = {};
	private Articulo articulo_modificado;

	@Override
	public void init() {
		listarCombos();

		seleccionarActivoFijo();
		seleccionarGrupo();

		// importar = false;
		// Map parametros = Executions.getCurrent().getArg();
		// if (parametros != null) {
		// if (parametros.get("importar") != null) {
		// importar = true;
		// }
		// if (parametros.get("id_menu") != null) {
		// id_menu = (String) parametros.get("id_menu");
		// }
		// if (parametros.get("producto") != null) {
		// producto = (String) parametros.get("producto");
		// }
		// }
	}

	public void initArticulo() throws Exception {
		// HttpServletRequest request = (HttpServletRequest) Executions
		// .getCurrent().getNativeRequest();
		try {
			// String id_m = request.getParameter("id_menu");

			if (empresa.getManeja_contabilidad().equals("N")) {
				lbCodigo_contabilidad.setVisible(false);
				lbxCodigo_contabilidad.setVisible(false);
			}

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public void listarCombos() {
		listarParameter();
		listarGrupo1(lbxGrupo1, true);
		listarGrupo2(lbxGrupo1, lbxGrupo2, true);
		listarUnidad_medida(lbxUnidad_medida, true);
		listarPresentacion(lbxPresentacion, true);
		listarVencimiento(lbxVencimiento, true);
		listarContabilizacion(lbxCodigo_contabilidad, true);
		listarElementoListbox(lbxCodigo_unidad_funcional, true);
		listarUnd_unm(lbxUnd_unm, true);
		listarElementoListbox(lbxEstado_activo, true);
		listarElementoListbox(lbxVia, true);
		listarTipo_procedimiento(lbxCodigo_unidad_funcional, true);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nombre1");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_articulo");
		listitem.setLabel("Codigo articulo");
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

		((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(false);
		((Button) groupboxEditar.getFellow("btGuardar"))
				.setImage("/images/Save16.gif");

		seleccionarActivoFijo();
		seleccionarDiferido();
		seleccionarGrupo();

		chbProducto.setChecked(true);
		chbVenta.setChecked(true);
		chbFacturable.setChecked(true);
		listarGrupo2(lbxGrupo1, lbxGrupo2, true);
		tbxCodigo_articulo.setReadonly(false);

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		dbxIva.setStyle("background-color:white");
		ibxUnm.setStyle("background-color:white");
		tbxCodigo_articulo
				.setStyle("text-transform:uppercase;background-color:white");
		tbxNombre1.setStyle("text-transform:uppercase;background-color:white");
		lbxCodigo_contabilidad.setStyle("background-color:white");
		tbxReferencia
				.setStyle("text-transform:uppercase;background-color:white");
		lbxCodigo_unidad_funcional.setStyle("background-color:white");
		lbxUnidad_medida.setStyle("background-color:white");
		tbxUnidad_concentracion
				.setStyle("text-transform:uppercase;background-color:white");
		tbxConcentracion
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;
		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo_articulo.getText().trim().equals("")) {
			tbxCodigo_articulo
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxNombre1.getText().trim().equals("")) {
			tbxNombre1
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (lbxCodigo_contabilidad.isVisible()
				&& lbxCodigo_contabilidad.getSelectedItem().getValue()
						.equals("")) {
			lbxCodigo_contabilidad
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (valida && dbxIva.getValue() != null) {
			if (dbxIva.getValue().doubleValue() > 100
					|| dbxIva.getValue().doubleValue() < 0) {
				dbxIva.setStyle("background-color:#F6BBBE");
				valida = false;
				mensaje = "El porcentaje de iva no puede ser menor ni mayor que cero";
			}
		}

		if (valida && ibxUnm.getValue() != null) {
			if (ibxUnm.getValue().doubleValue() < 0) {
				ibxUnm.setStyle("background-color:#F6BBBE");
				valida = false;
				mensaje = "El valor de La unidad minima no puede ser menor que cero (0)";
			}
		}

		if (valida
				&& (lbxGrupo1.getSelectedItem().getValue().toString()
						.equals("01")
						&& tbxReferencia.getValue().equals("") && chbPos
							.isChecked())) {
			tbxReferencia
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			mensaje = "Para articulos de linea medicamentos que sean POS debe colocar el codigo del SGSSS en el campo codigo POS";
		}

		if (valida
				&& ((lbxGrupo1.getSelectedItem().getValue().toString()
						.equals("01")
						|| lbxGrupo1.getSelectedItem().getValue().toString()
								.equals("02") || lbxGrupo1.getSelectedItem()
						.getValue().toString().equals("03")) && lbxCodigo_unidad_funcional
						.getSelectedItem().getValue().toString().equals(""))) {
			lbxCodigo_unidad_funcional.setStyle("background-color:#F6BBBE");
			valida = false;
			mensaje = "Para articulos de linea medicamentos,materiales o servicios debe colocar el tipo de servicio";
		}

		if (valida
				&& (lbxGrupo1.getSelectedItem().getValue().toString()
						.equals("01")
						&& lbxUnidad_medida.getSelectedItem().getValue()
								.toString().equals("") && !chbPos.isChecked())) {
			lbxUnidad_medida.setStyle("background-color:#F6BBBE");
			valida = false;
			mensaje = "Para articulos de Grupo medicamentos que sean no pos debe colocar la unidad de medida";
		}

		if (valida
				&& (lbxGrupo1.getSelectedItem().getValue().toString()
						.equals("01")
						&& tbxUnidad_concentracion.getValue().equals("") && !chbPos
							.isChecked())) {
			tbxUnidad_concentracion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			mensaje = "Para articulos de Grupo medicamentos que sean no pos debe digitar la forma farmaceutica";
		}

		if (valida
				&& (lbxGrupo1.getSelectedItem().getValue().toString()
						.equals("01")
						&& tbxConcentracion.getValue().equals("") && !chbPos
							.isChecked())) {
			tbxConcentracion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			mensaje = "Para articulos de Grupo medicamentos que sean no pos debe digitar la concentracion";
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
			if (producto != null) {
				parameters.put("producto", producto);
			}

			getServiceLocator().getArticuloService().setLimit(
					"limit 300 offset 0");

			List<Articulo> lista_datos = getServiceLocator()
					.getArticuloService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Articulo articulo : lista_datos) {
				rowsResultado.appendChild(crearFilas(articulo, this));
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

		final Articulo articulo = (Articulo) objeto;

		Grupo1 grupo1 = new Grupo1();
		grupo1.setCodigo_empresa(articulo.getCodigo_empresa());
		grupo1.setCodigo_sucursal(articulo.getCodigo_sucursal());
		grupo1.setCodigo(articulo.getGrupo1());
		grupo1 = getServiceLocator().getGrupo1Service().consultar(grupo1);

		Grupo2 grupo2 = new Grupo2();
		grupo2.setCodigo_empresa(articulo.getCodigo_empresa());
		grupo2.setCodigo_sucursal(articulo.getCodigo_sucursal());
		grupo2.setCodigo_grupo1(articulo.getGrupo1());
		grupo2.setCodigo(articulo.getGrupo2());
		grupo2 = getServiceLocator().getGrupo2Service().consultar(grupo2);

		Doublebox dbxCosto = new Doublebox(articulo.getCosto());
		dbxCosto.setReadonly(true);
		dbxCosto.setInplace(true);
		dbxCosto.setFormat("$#,##0.00");

		Doublebox dbxPrecio1 = new Doublebox(articulo.getPrecio1());
		dbxPrecio1.setReadonly(true);
		dbxPrecio1.setInplace(true);
		dbxPrecio1.setFormat("$#,##0.00");

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(articulo.getCodigo_articulo() + ""));
		// fila.appendChild(new Label(articulo.getReferencia_articulo()));
		fila.appendChild(new Label(articulo.getNombre1() + ""));
		fila.appendChild(new Label(grupo1 != null ? grupo1.getNombre() : ""));
		fila.appendChild(new Label(grupo2 != null ? grupo2.getNombre() : ""));
		fila.appendChild(dbxCosto);
		fila.appendChild(dbxPrecio1);

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(articulo);
			}
		});
		hbox.appendChild(img);

		// img = new Image();
		// img.setVisible((permisos_sc.getPermiso_eliminar() ? true : false));
		// img.setSrc("/images/eliminar.gif");
		// img.setTooltiptext("Eliminar");
		// img.setStyle("cursor: pointer");
		// img.addEventListener("onClick", new EventListener() {
		// @Override
		// public void onEvent(Event arg0) throws Exception {
		// Messagebox.show(
		// "Esta seguro que desea eliminar este registro? ",
		// "Eliminar Registro", Messagebox.YES + Messagebox.NO,
		// Messagebox.QUESTION,
		// new org.zkoss.zk.ui.event.EventListener() {
		// public void onEvent(Event event) throws Exception {
		// if ("onYes".equals(event.getName())) {
		// // do the thing
		// eliminarDatos(articulo);
		// buscarDatos();
		// }
		// }
		// });
		// }
		// });
		// hbox.appendChild(space);
		// hbox.appendChild(img);

		// img = new Image();
		// img.setSrc("/images/ok.jpg");
		// img.setVisible(importar ? true : false);
		// img.setTooltiptext("Seleccionar");
		// img.setStyle("cursor: pointer");
		// img.addEventListener("onClick", new EventListener() {
		// @Override
		// public void onEvent(Event arg0) throws Exception {
		// Map art = new HashMap();
		// art.put("codigo_empresa", articulo.getCodigo_empresa());
		// art.put("codigo_sucursal", articulo.getCodigo_sucursal());
		// art.put("codigo_articulo", articulo.getCodigo_articulo());
		// art.put("nombre_articulo", articulo.getNombre1());
		// art.put("costo", articulo.getCosto());
		//
		// if (ArticuloAction.this.getParent() instanceof GastosAction) {
		// GastosAction action = (GastosAction) ArticuloAction.this
		// .getParent();
		// action.adicionarArticulo(art);
		// ArticuloAction.this.detach();
		// }
		// }
		// });
		// hbox.appendChild(space);
		// hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //

				Articulo articulo = new Articulo();
				articulo.setCodigo_empresa(empresa.getCodigo_empresa());
				articulo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				articulo.setCodigo_articulo(tbxCodigo_articulo.getValue());
				articulo.setCodigo_barra(tbxCodigo_barra.getValue().trim()
						.equals("") ? null : tbxCodigo_barra.getValue());
				articulo.setNombre1(tbxNombre1.getValue());
				articulo.setNombre2(tbxNombreComercialMedicamento.getValue());
				articulo.setNombre3(tbxNombre3.getValue());
				articulo.setReferencia(tbxReferencia.getValue());
				articulo.setGrupo1(lbxGrupo1.getSelectedItem().getValue()
						.toString());
				articulo.setGrupo2(lbxGrupo2.getSelectedItem().getValue()
						.toString());
				articulo.setUnidad_medida(lbxUnidad_medida.getSelectedItem()
						.getValue().toString());
				articulo.setPresentacion(lbxPresentacion.getSelectedItem()
						.getValue().toString());
				articulo.setConcentracion(tbxConcentracion.getValue());
				articulo.setUnidad_concentracion(tbxUnidad_concentracion
						.getValue());
				articulo.setSin_existencia(chbSin_existencia.isChecked() ? "S"
						: "N");
				articulo.setProducto(chbProducto.isChecked() ? "S" : "N");
				articulo.setNo_inv(chbProducto.isChecked() ? "N" : "S");
				articulo.setGasto("N");
				articulo.setVenta(chbVenta.isChecked() ? "S" : "N");
				articulo.setManeja_costo("S");
				articulo.setPos(chbPos.isChecked() ? "S" : "N");
				articulo.setActivo_fijo("");
				articulo.setCosto((dbxCosto.getValue() != null ? dbxCosto
						.getValue() : 0.00));
				articulo.setGrava_iva("");
				articulo.setIva((dbxIva.getValue() != null ? (dbxIva.getValue() / 100)
						: 0.00));
				articulo.setUtilidad(0.0);
				articulo.setPrecio1((dbxPrecio1.getValue() != null ? dbxPrecio1
						.getValue() : 0.00));
				articulo.setMaximo_permitido((ibxMaximo_permitido.getValue() != null ? ibxMaximo_permitido
						.getValue() : 0));
				articulo.setMinimo_permitido((ibxMinimo_permitido.getValue() != null ? ibxMinimo_permitido
						.getValue() : 0));
				articulo.setPunto_reorden(0);
				articulo.setFabricante(tbxFabricante.getValue());
				articulo.setProveedor("");
				articulo.setObservaciones(tbxObservaciones.getValue());
				articulo.setCreacion_date(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				articulo.setUltimo_update(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				articulo.setCreacion_user(usuarios.getCodigo());
				articulo.setUltimo_user(usuarios.getCodigo());
				articulo.setCum(tbxCum.getValue());
				articulo.setRegistro_sanitario(tbxRegistro_sanitario.getValue());
				articulo.setVencimiento(lbxVencimiento.getSelectedItem()
						.getValue().toString());
				articulo.setEstado_registro(new Timestamp(dtbxEstado_registro
						.getValue().getTime()));
				articulo.setModalidad(tbxModalidad.getValue());
				articulo.setCantidad(tbxCantidad.getValue());
				articulo.setTitular(tbxTitular.getValue());
				articulo.setCodigo_contabilidad(lbxCodigo_contabilidad
						.getSelectedItem().getValue().toString());
				articulo.setCodigo_unidad_funcional(lbxCodigo_unidad_funcional
						.getSelectedItem().getValue().toString());
				articulo.setUnm((ibxUnm.getValue() != null ? ibxUnm.getValue()
						: 0));
				articulo.setUnd_unm(lbxUnd_unm.getSelectedItem().getValue()
						.toString());
				articulo.setFacturable(chbFacturable.isChecked());
				articulo.setServicio_gravado(chbServicio_gravado.isChecked());
				articulo.setServicio_intangible(chbServicio_intangible
						.isChecked());
				articulo.setReferencia_articulo(tbxReferencia_articulo
						.getValue());
				articulo.setPyp(chbPyp.isChecked() ? "S" : "N");
				articulo.setAlto_costo(chbAlto_costo.isChecked() ? "S" : "N");
				articulo.setVia(lbxVia.getSelectedItem().getValue().toString());
				articulo.setValor_adicional((dbxValor_adicional.getValue() != null ? dbxValor_adicional
						.getValue() : 0.00));

				articulo.setFecha_compra_activo(new Timestamp(
						dtbxFecha_compra_activo.getValue().getTime()));
				articulo.setValor_activo((dbxValor_activo.getValue() != null ? dbxValor_activo
						.getValue() : 0.00));
				articulo.setVida_util((ibxVida_util.getValue() != null ? ibxVida_util
						.getValue() : 0));
				articulo.setColor_activo(tbxColor_activo.getValue());
				articulo.setMarca_activo(tbxMarca_activo.getValue());
				articulo.setModelo_activo(tbxModelo_activo.getValue());
				articulo.setSerial_activo(tbxSerial_activo.getValue());
				articulo.setEstado_activo(lbxEstado_activo.getSelectedItem()
						.getValue().toString());
				articulo.setEmpleado(tbxEmpleado.getValue());
				articulo.setDepreciable(chbDepreciable.isChecked());
				articulo.setCargo_empleado(tbxCargo_empleado.getValue());
				articulo.setPuesto_empleado(tbxPuesto_empleado.getValue());
				articulo.setActivo_fijo(chbActivo_fijo.isChecked() ? "S" : "N");
				articulo.setComercial(chbComercial.isChecked() ? "S" : "N");

				articulo.setDiferido(chbDiferido.isChecked() ? "S" : "N");
				articulo.setVademecum_institucional(chbVademecumInstitucional
						.isChecked() ? "S" : "N");

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getArticuloService().crearExistencias(
							articulo, resolucion.getAfectar_kardex_fact());
					accionForm(true, "registrar");
				} else {
					int result = getServiceLocator().getArticuloService()
							.actualizarExistencias(articulo,
									resolucion.getAfectar_kardex_fact());
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
		articulo_modificado = (Articulo) obj;
		try {
			tbxCodigo_articulo.setValue(articulo_modificado
					.getCodigo_articulo());
			tbxCodigo_barra
					.setValue(articulo_modificado.getCodigo_barra() != null ? articulo_modificado
							.getCodigo_barra() : "");
			tbxNombre1.setValue(articulo_modificado.getNombre1());
			tbxNombre3.setValue(articulo_modificado.getNombre3());
			tbxReferencia.setValue(articulo_modificado.getReferencia());
			tbxNombreComercialMedicamento.setValue(articulo_modificado
					.getNombre2());
			for (int i = 0; i < lbxGrupo1.getItemCount(); i++) {
				Listitem listitem = lbxGrupo1.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(articulo_modificado.getGrupo1())) {
					listitem.setSelected(true);
					i = lbxGrupo1.getItemCount();
				}
			}

			listarGrupo2(lbxGrupo1, lbxGrupo2, true);
			for (int i = 0; i < lbxGrupo2.getItemCount(); i++) {
				Listitem listitem = lbxGrupo2.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(articulo_modificado.getGrupo2())) {
					listitem.setSelected(true);
					i = lbxGrupo2.getItemCount();
				}
			}
			for (int i = 0; i < lbxUnidad_medida.getItemCount(); i++) {
				Listitem listitem = lbxUnidad_medida.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(articulo_modificado.getUnidad_medida())) {
					listitem.setSelected(true);
					i = lbxUnidad_medida.getItemCount();
				}
			}
			for (int i = 0; i < lbxPresentacion.getItemCount(); i++) {
				Listitem listitem = lbxPresentacion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(articulo_modificado.getPresentacion())) {
					listitem.setSelected(true);
					i = lbxPresentacion.getItemCount();
				}
			}
			chbComercial
					.setChecked(articulo_modificado.getComercial() != null ? articulo_modificado
							.getComercial().equals("S") : false);
			tbxConcentracion.setValue(articulo_modificado.getConcentracion());
			tbxUnidad_concentracion.setValue(articulo_modificado
					.getUnidad_concentracion());
			chbSin_existencia.setChecked(articulo_modificado
					.getSin_existencia().equals("S") ? true : false);
			chbProducto.setChecked(articulo_modificado.getProducto()
					.equals("S") ? true : false);
			chbVenta.setChecked(articulo_modificado.getVenta().equals("S") ? true
					: false);
			chbPos.setChecked(articulo_modificado.getPos().equals("S") ? true
					: false);
			dbxCosto.setValue(articulo_modificado.getCosto());
			dbxIva.setValue(articulo_modificado.getIva() * 100);
			dbxPrecio1.setValue(articulo_modificado.getPrecio1());
			ibxMaximo_permitido.setValue(articulo_modificado
					.getMaximo_permitido());
			ibxMinimo_permitido.setValue(articulo_modificado
					.getMinimo_permitido());
			tbxObservaciones.setValue(articulo_modificado.getObservaciones());
			tbxCum.setValue(articulo_modificado.getCum());
			tbxRegistro_sanitario.setValue(articulo_modificado
					.getRegistro_sanitario());
			for (int i = 0; i < lbxVencimiento.getItemCount(); i++) {
				Listitem listitem = lbxVencimiento.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(articulo_modificado.getVencimiento())) {
					listitem.setSelected(true);
					i = lbxVencimiento.getItemCount();
				}
			}
			dtbxEstado_registro.setValue(articulo_modificado
					.getEstado_registro());
			tbxModalidad.setValue(articulo_modificado.getModalidad());
			tbxCantidad.setValue(articulo_modificado.getCantidad());

			for (int i = 0; i < lbxCodigo_contabilidad.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_contabilidad.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(articulo_modificado.getCodigo_contabilidad())) {
					listitem.setSelected(true);
					i = lbxCodigo_contabilidad.getItemCount();
				}
			}
			for (int i = 0; i < lbxCodigo_unidad_funcional.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_unidad_funcional
						.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(articulo_modificado
								.getCodigo_unidad_funcional())) {
					listitem.setSelected(true);
					i = lbxCodigo_unidad_funcional.getItemCount();
				}
			}
			ibxUnm.setValue(articulo_modificado.getUnm());
			for (int i = 0; i < lbxUnd_unm.getItemCount(); i++) {
				Listitem listitem = lbxUnd_unm.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(articulo_modificado.getUnd_unm())) {
					listitem.setSelected(true);
					i = lbxUnd_unm.getItemCount();
				}
			}
			chbFacturable.setChecked(articulo_modificado.getFacturable());
			chbServicio_gravado.setChecked(articulo_modificado
					.getServicio_gravado());
			chbServicio_intangible.setChecked(articulo_modificado
					.getServicio_intangible());
			tbxReferencia_articulo.setValue(articulo_modificado
					.getReferencia_articulo());

			if (articulo_modificado.getFabricante() != null
					&& !articulo_modificado.getFabricante().isEmpty()) {
				Tercero tercero = new Tercero();
				tercero.setCodigo_empresa(articulo_modificado
						.getCodigo_empresa());
				tercero.setCodigo_sucursal(articulo_modificado
						.getCodigo_sucursal());
				tercero.setNro_identificacion(articulo_modificado
						.getFabricante());
				tercero = getServiceLocator().getTerceroService().consultar(
						tercero);
				tbxFabricante.setValue(articulo_modificado.getFabricante());
				tbxNomFabricante.setValue(tercero != null ? tercero
						.getNombre1()
						+ " "
						+ tercero.getApellido1()
						+ " "
						+ tercero.getApellido2() : "");
			} else {
				tbxFabricante.setValue("");
				tbxNomFabricante.setValue("");
			}

			if (articulo_modificado.getTitular() != null
					&& !articulo_modificado.getTitular().isEmpty()) {
				Tercero tercero = new Tercero();
				tercero.setCodigo_empresa(articulo_modificado
						.getCodigo_empresa());
				tercero.setCodigo_sucursal(articulo_modificado
						.getCodigo_sucursal());
				tercero.setNro_identificacion(articulo_modificado.getTitular());
				tercero = getServiceLocator().getTerceroService().consultar(
						tercero);
				tbxTitular.setValue(articulo_modificado.getTitular());
				tbxNomTitular.setValue(tercero != null ? tercero.getNombre1()
						+ " " + tercero.getApellido1() + " "
						+ tercero.getApellido2() : "");
			} else {
				tbxTitular.setValue("");
				tbxNomTitular.setValue("");
			}

			chbPyp.setChecked(articulo_modificado.getPyp().equals("S") ? true
					: false);
			chbAlto_costo.setChecked(articulo_modificado.getAlto_costo()
					.equals("S") ? true : false);
			for (int i = 0; i < lbxVia.getItemCount(); i++) {
				Listitem listitem = lbxVia.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(articulo_modificado.getVia())) {
					listitem.setSelected(true);
					i = lbxVia.getItemCount();
				}
			}
			dbxValor_adicional.setValue(articulo_modificado
					.getValor_adicional());

			dtbxFecha_compra_activo.setValue(articulo_modificado
					.getFecha_compra_activo());
			dbxValor_activo.setValue(articulo_modificado.getValor_activo());
			ibxVida_util.setValue(articulo_modificado.getVida_util());
			tbxColor_activo.setValue(articulo_modificado.getColor_activo());
			tbxMarca_activo.setValue(articulo_modificado.getMarca_activo());
			tbxModelo_activo.setValue(articulo_modificado.getModelo_activo());
			tbxSerial_activo.setValue(articulo_modificado.getSerial_activo());
			Utilidades.seleccionarListitem(lbxEstado_activo,
					articulo_modificado.getEstado_activo());
			tbxEmpleado.setValue(articulo_modificado.getEmpleado());
			chbDepreciable.setChecked(articulo_modificado.getDepreciable());
			tbxCargo_empleado.setValue(articulo_modificado.getCargo_empleado());
			tbxPuesto_empleado.setValue(articulo_modificado
					.getPuesto_empleado());
			chbActivo_fijo.setChecked(articulo_modificado.getActivo_fijo()
					.equals("S") ? true : false);

			chbDiferido.setChecked(articulo_modificado.getDiferido()
					.equals("S") ? true : false);
			chbVademecumInstitucional.setChecked(articulo_modificado
					.getVademecum_institucional().equals("S"));

			tbxCodigo_articulo.setReadonly(true);

			seleccionarActivoFijo();
			seleccionarDiferido();
			seleccionarGrupo();

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
		Articulo articulo = (Articulo) obj;
		try {
			int result = getServiceLocator().getArticuloService().eliminar(
					articulo);
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

	public void buscarTercero(String value, Listbox lbx, String tipo_tercero)
			throws Exception {
		try {
			// //log.info("parent: "+((Bandpopup)lbxCta.getParent().getParent().getParent()).);
			Map parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");
			if (!tipo_tercero.equals("")) {
				parameters.put("tipo_tercero", tipo_tercero);
				// parameters.put("tipo_cuenta","'"+tipo_cuenta+"','07'");
			}

			getServiceLocator().getTerceroService().setLimit(
					"limit 100 offset 0");

			// //log.info("parameters: "+parameters);
			List<Tercero> list = getServiceLocator().getTerceroService()
					.listar(parameters);

			lbx.getItems().clear();

			Listitem listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("NINGUNO");
			listitem.setStyle("font-family: Verdana,Arial;font-size: 12px;background-color:#F78181;");
			// lbx.appendChild(listitem);

			for (Tercero dato : list) {
				// List<Tipo_tercero> listaTipos_tercero = dato
				// .getListaTipos_tercero();
				// String nombre_tipo_tercero = "";
				// for (int i = 0; i < listaTipos_tercero.size(); i++) {
				// Tipo_tercero tipo_terceroAux = listaTipos_tercero.get(i);
				// nombre_tipo_tercero += tipo_terceroAux
				// .getElemento_tipo_tercero().getDescripcion();
				// if (i != listaTipos_tercero.size() - 1) {
				// nombre_tipo_tercero += ",";
				// }
				// }
				listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getDv() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre1() + " "
						+ dato.getNombre2()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellido1() + " "
						+ dato.getApellido2()));
				listitem.appendChild(listcell);

				// listcell = new Listcell();
				// listcell.appendChild(new Label(nombre_tipo_tercero + ""));
				// listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}
			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void selectedTercero(Bandbox bandbox, Textbox textbox,
			Listitem listitem, String sw, String muestra_dir) {
		if (listitem.getValue() == null) {
			// log.info("borrar tercero");
			bandbox.setValue("");
			textbox.setValue("");

		} else {
			Tercero dato = (Tercero) listitem.getValue();
			if (sw.equals("N")) {
				bandbox.setValue(dato.getNombre1() + " " + dato.getApellido1()
						+ " " + dato.getApellido2());
				textbox.setValue(dato.getNro_identificacion());
			} else {
				textbox.setValue(dato.getNombre1() + " " + dato.getApellido1()
						+ " " + dato.getApellido2());
				bandbox.setValue(dato.getNro_identificacion());

			}
		}
		bandbox.close();
	}

	public void buscarPos(String value, Listbox lbx) throws Exception {
		try {
			// //log.info("parent: "+((Bandpopup)lbxCta.getParent().getParent().getParent()).);
			Map parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getServicio(Medicamentos_posService.class)
					.setLimit("limit 100");

			// //log.info("parameters: "+parameters);
			List<Medicamentos_pos> list = getServiceLocator().getServicio(
					Medicamentos_posService.class).listar(parameters);

			lbx.getItems().clear();

			Listitem listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("NINGUNO");
			listitem.setStyle("font-family: Verdana,Arial;font-size: 12px;background-color:#F78181;");
			// lbx.appendChild(listitem);
			for (Medicamentos_pos dato : list) {

				listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getConcentracion()));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}
			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void selectedPos(Bandbox bandbox, Listitem listitem) {
		if (listitem.getValue() == null) {
			bandbox.setValue("");
		} else {
			Medicamentos_pos dato = (Medicamentos_pos) listitem.getValue();
			bandbox.setValue(dato.getCodigo());
		}
		bandbox.close();
	}

	public void seleccionarActivoFijo() {
		rowActivos_fijos.setVisible(chbActivo_fijo.isChecked());
		if (chbActivo_fijo.isChecked()) {
			chbDiferido.setChecked(false);
		}
	}

	public void seleccionarDiferido() {
		if (chbDiferido.isChecked()) {
			chbActivo_fijo.setChecked(false);
			rowActivos_fijos.setVisible(false);
		}
	}

	public void seleccionarGrupo() {
		if (lbxGrupo1.getSelectedItem().getValue().equals("01")) {
			rowMedicamento.setVisible(true);
		} else {
			rowMedicamento.setVisible(false);
		}
	}
}
