package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Detalle_receta_rips;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Negacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Receta_rips;
import healthmanager.modelo.service.Receta_ripsService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.macros.macro_row.BandBoxRowMacro.IConfiguracionBandbox;
import com.framework.res.ContentCheck;
import com.framework.res.LabelAlign;
import com.framework.res.Res;
import com.framework.res.LabelAlign.AlignText;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Bodega_articulo;
import contaweb.modelo.bean.Caja;
import contaweb.modelo.bean.Tercero;
import contaweb.modelo.service.ArticuloService;

public class FarmaciaRecetasRipsMedicamentosAction extends ZKWindow {

	private Window form;
	// Componentes //
	private Listbox lbxParameter;
	// private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	// @View Listbox lbxSearh;
	// @View private Timer timer;
	@View
	private Checkbox check_global;
	@View
	private Column column_check;
	@View
	private Column column_entregado;
	@View
	private Column column_entregado_pendientes;
	@View
	private Column column_accion;
	@View
	private Column columnNombreComercial;

	@View
	private Toolbarbutton btn_entregar;
	@View
	private Toolbarbutton btn_autorizar;

	private Receta_rips receta_rips;
	private List<Detalle_receta_rips> list_detalleRecetaRipsAutorizados;
	private boolean auditor;

	private List<ContentCheck> listado_check;

	protected Anexo3_entidad anexo3Entidad;
	// private Boolean paga_copago;
	private FarmaciaRecetasRipsAction farmaciaRecetasRipsMedicamentosAction;

	private Ordenes_medicas_enfermeraAction ordenes_medicas_enfermeraAction;

	private void cargarReceta() {
		// TODO Aut o-generated method stub
		Map map = Executions.getCurrent().getArg();
		receta_rips = (Receta_rips) map.get("receta");
		auditor = (Boolean) map.get("auditor");
		anexo3Entidad = (Anexo3_entidad) map.get("anexo");
		// paga_copago = (Boolean) map.get("paga_copago");
		if (auditor) {
			column_check.setVisible(true);
			column_entregado_pendientes.setVisible(false);
			column_accion.setVisible(true);
			((Toolbarbutton) getFellow("btn_entregar")).setVisible(false);
			((Button) getFellow("btn_autorizar")).setVisible(true);
		}

		if (receta_rips != null
				&& sucursal.getTipo().equals(
						IConstantes.TIPOS_SUCURSAL_CAJA_PREV)
				&& (receta_rips.getAuditado_farmacia() + "").equals("N")) {
			btn_entregar.setDisabled(true);
		}
		if (!sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
			btn_autorizar.setVisible(false);
		} else {
			columnNombreComercial.setVisible(true);
		}
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_receta");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Nro id paciente");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_prestador");
		listitem.setLabel("Nro codigo Medico");
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
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

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

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent)
							.setText(((Textbox) abstractComponent).getText()
									.trim().toUpperCase());
				}
			}
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals(
						"lbxParameter")) {
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setSelectedIndex(0);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setText("0.00");
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setText("");
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setValue(new Date());
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setChecked(false);
			}
			if (abstractComponent instanceof Radiogroup) {
				((Radio) ((Radiogroup) abstractComponent).getFirstChild())
						.setChecked(true);
			}
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		String msj = "";

		if (auditor) {
			boolean isCheckSomeOne = false;
			for (ContentCheck contentCheck : listado_check) {
				if (contentCheck.getCheckbox().isChecked()) {
					isCheckSomeOne = true;
					break;
				}
			}
			if (!isCheckSomeOne) {
				valida = false;
				msj = "Para esta accion debes seleccionar almenos un medicamento que sea no autorizado";
			}
		} else {
			if (list_detalleRecetaRipsAutorizados.isEmpty()) {
				valida = false;
				msj = "para esta accion por lo menos debe haber un medicamento autorizado o con medicamentos pendientes";
			} else {
				/* validamos cantidades */
				for (Detalle_receta_rips detalleRecetaRips : list_detalleRecetaRipsAutorizados) {

					Articulo articulo = new Articulo();
					articulo.setCodigo_empresa(sucursal.getCodigo_empresa());
					articulo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
					articulo.setCodigo_articulo(detalleRecetaRips
							.getCodigo_articulo());
					articulo = getServiceLocator().getArticuloService()
							.consultar(articulo);

					Bodega_articulo bodega_articulo = new Bodega_articulo();
					bodega_articulo.setCodigo_empresa(articulo
							.getCodigo_empresa());
					bodega_articulo.setCodigo_sucursal(articulo
							.getCodigo_sucursal());
					bodega_articulo.setCodigo_bodega("01");
					bodega_articulo.setCodigo_articulo(articulo
							.getCodigo_articulo());
					bodega_articulo = getServiceLocator()
							.getBodega_articuloService().consultar(
									bodega_articulo);

					String exist = (bodega_articulo != null ? bodega_articulo
							.getCantidad() + "" : "0.0");
					if (exist.matches("[0-9.]*")) {
						Double existencias = Double.parseDouble(exist);
						//log.info(":: valido existencias: " + exist);
						detalleRecetaRips.setExistencia(existencias);
					} else {
						detalleRecetaRips.setExistencia(0d);
					}

					if (detalleRecetaRips.getCantidad_entregada() == 0) {
						valida = false;
						msj = "La cantidad a entragar no puede ser igual a cero para el medicamento "
								+ detalleRecetaRips.getCodigo_articulo();
						break;
					} else if (detalleRecetaRips.getCantidad_entregada() > detalleRecetaRips
							.getExistencia()
							&& detalleRecetaRips.getEntregado().equals("N")) {
						valida = false;
						msj = "La cantidad a entragar no puede ser mayor que la existencia para el medicamento "
								+ detalleRecetaRips.getCodigo_articulo();
						break;
					} else if (detalleRecetaRips.getCantidad_entregada() > detalleRecetaRips
							.getCantidad_recetada()
							&& detalleRecetaRips.getEntregado().equals("N")) {
						valida = false;
						msj = "La cantidad a entragar no puede ser mayor que la cantidad recetada para el medicamento "
								+ detalleRecetaRips.getCodigo_articulo();
						break;
					}

					if (detalleRecetaRips.getCantidad_entregada() != detalleRecetaRips
							.getCantidad_recetada()) {
						double faltante = detalleRecetaRips
								.getCantidad_pendiente() != null ? detalleRecetaRips
								.getCantidad_pendiente() : 0d;
						double entregar_Faltante = detalleRecetaRips
								.getEntregarFaltantes() != null ? detalleRecetaRips
								.getEntregarFaltantes() : 0d;

						if (entregar_Faltante > faltante) {
							valida = false;
							msj = "La cantidad a entregar no puede ser mayor que la cantidad faltante para el medicamento "
									+ detalleRecetaRips.getCodigo_articulo();
							break;
						}
					}

				}
			}

		}

		if (!valida) {
			Messagebox.show(msj, usuarios.getNombres() + " recuerde que...",
					Messagebox.OK, Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			list_detalleRecetaRipsAutorizados.clear();
			rowsResultado.getChildren().clear();
			for (Detalle_receta_rips detalleRecetaRips : receta_rips
					.getLista_detalle()) {
				rowsResultado.appendChild(crearFilas(detalleRecetaRips, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		final Row fila = new Row();

		final Detalle_receta_rips detalleRecetaRips = (Detalle_receta_rips) objeto;

		final Hbox hbox = new Hbox();
		Space space = new Space();

		String codigo_articulo = detalleRecetaRips.getCodigo_articulo();
		// if(detalleRecetaRips.getCodigo_articulo_cambio() != null){
		// codigo_articulo = detalleRecetaRips.getCodigo_articulo_cambio();
		// }

		/* cargamos articulo */
		Articulo articulo = new Articulo();
		articulo.setCodigo_empresa(sucursal.getCodigo_empresa());
		articulo.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		articulo.setCodigo_articulo(codigo_articulo);
		articulo = getServiceLocator().getArticuloService().consultar(articulo);

		/* cargamos existencias */
		Bodega_articulo bodega_articulo = new Bodega_articulo();
		bodega_articulo.setCodigo_empresa(articulo.getCodigo_empresa());
		bodega_articulo.setCodigo_sucursal(articulo.getCodigo_sucursal());
		bodega_articulo.setCodigo_bodega("01");
		bodega_articulo.setCodigo_articulo(codigo_articulo);
		bodega_articulo = getServiceLocator().getBodega_articuloService()
				.consultar(bodega_articulo);

		Tercero ter = new Tercero();
		ter.setCodigo_empresa(articulo.getCodigo_empresa());
		ter.setCodigo_sucursal(articulo.getCodigo_sucursal());
		ter.setNro_identificacion(articulo.getFabricante());
		ter = getServiceLocator().getTerceroService().consultar(ter);
		String nombre_fabricante = "*";
		if (ter != null) {
			nombre_fabricante = ter.getNombre1() + " " + ter.getApellido1()
					+ " " + ter.getApellido2();
		}

		double exist = (bodega_articulo != null ? bodega_articulo.getCantidad() + 0
				: 0);
		boolean verificamosExistencia = false;
		verificamosExistencia = (exist > 0);
		//log.info(":: valido existencias: " + exist);
		detalleRecetaRips.setExistencia(exist);

		String nombreArt = "";
		String nombreArtComercial = "";
		if (articulo != null) {
			nombreArt = articulo.getNombre1();
			nombreArtComercial = articulo.getNombre2();
		}
		final String name = nombreArt;

		final Doublebox textboxEntregar = new Doublebox();
		textboxEntregar.setHflex("1"); 
		textboxEntregar.setFormat("#0.##"); 
		String color_row = "";
		//log.info("Entregado:" + detalleRecetaRips.getEntregado());
		//log.info("Existencia:" + verificamosExistencia);

		final Doublebox textboxFaltanteEntregar = new Doublebox();
		textboxFaltanteEntregar.setHflex("1"); 
		textboxFaltanteEntregar.setFormat("#0.##"); 

		double cantidad_pendiente = detalleRecetaRips.getCantidad_pendiente() != null ? detalleRecetaRips
				.getCantidad_pendiente() : 0d;

		textboxFaltanteEntregar.setReadonly(true);

		boolean entregado = true;
		boolean autorizado = false;
		boolean noAutorizado = false;
		fila.setTooltiptext("ENTREGADO");
		if ((detalleRecetaRips.getEntregado() + "").equals("N")) {
			entregado = false;
			if (!verificamosExistencia) {
				color_row = "background-color: #e3e0a6;";
				fila.setTooltiptext("SIN EXISTENCIAS");
			} else if (detalleRecetaRips.getAutorizado()) {
				color_row = "background-color: #88e3a3;";
				autorizado = true;
				fila.setTooltiptext("AUTORIZADO");
				list_detalleRecetaRipsAutorizados.add(detalleRecetaRips);

				if (detalleRecetaRips.getCantidad_recetada() > detalleRecetaRips
						.getExistencia()) {
					textboxEntregar.setValue(detalleRecetaRips.getExistencia());
				} else {
					textboxEntregar.setValue(detalleRecetaRips
							.getCantidad_recetada());
				}
			} else {
				color_row = "background-color: #e37f93;";
				fila.setTooltiptext("NO AUTORIZADO");
				noAutorizado = true;
			}
		} else if (cantidad_pendiente > 0) {
			//log.info("Detalle pendiente ::::: "
					//+ detalleRecetaRips.getCodigo_articulo() + " :::::");
			list_detalleRecetaRipsAutorizados.add(detalleRecetaRips);
			textboxFaltanteEntregar.setReadonly(false);
		}

		textboxEntregar.setReadonly(entregado || !autorizado);
		if (entregado)
			textboxEntregar.setValue(detalleRecetaRips.getCantidad_entregada());
		/* cargamos evento */

		textboxEntregar.addEventListener("onBlur", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				detalleRecetaRips.setCantidad_entregada(textboxEntregar
						.getValue() != null ? textboxEntregar.getValue() : 0d);
				if (textboxEntregar.getValue() == null) {
					textboxEntregar.setValue(0d);
				}
			}
		});
		detalleRecetaRips
				.setCantidad_entregada(textboxEntregar.getValue() != null ? textboxEntregar
						.getValue() : 0d);

		textboxFaltanteEntregar.addEventListener("onBlur", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				detalleRecetaRips.setEntregarFaltantes(textboxFaltanteEntregar
						.getValue() != null ? textboxFaltanteEntregar
						.getValue() : 0d);
				if (textboxFaltanteEntregar.getValue() == null) {
					textboxFaltanteEntregar.setValue(0d);
				}
			}
		});
		Negacion negacion = null;
		if (anexo3Entidad != null) {
			negacion = new Negacion();
			negacion.setCodigo_empresa(anexo3Entidad.getCodigo_empresa());
			negacion.setCodigo_sucursal(anexo3Entidad.getCodigo_sucursal());
			negacion.setNro_solicitud(anexo3Entidad.getNumero_solicitud());
			negacion.setCodigo_servicio(detalleRecetaRips.getCodigo_articulo());
			negacion = getServiceLocator().getNegacionService().consultar(
					negacion);
		}
		final Negacion negacionLook = negacion;
		final Checkbox checkbox = new Checkbox();
		ContentCheck contentCheck = new ContentCheck();
		contentCheck.setCheckbox(checkbox);
		contentCheck.setObject(detalleRecetaRips);
		if (noAutorizado && negacionLook == null) {
			listado_check.add(contentCheck);
		} else {
			if (negacionLook != null) {
				color_row = "";
				fila.setTooltiptext("MEDICAMENTO NEGADO");
			}
			checkbox.setVisible(false);
		}

		Textbox textboxNombreMedicamento = new Textbox(nombreArt);
		textboxNombreMedicamento.setReadonly(true);
		textboxNombreMedicamento.setInplace(true);
		textboxNombreMedicamento.setHflex("1");

		Textbox textboxNombreMedicamentoComercial = new Textbox(nombreArtComercial);
		textboxNombreMedicamentoComercial.setReadonly(true);
		textboxNombreMedicamentoComercial.setInplace(true);
		textboxNombreMedicamentoComercial.setHflex("1");

		Label labelFabricante = new Label(nombre_fabricante);

		/* agregamos las existencias y los valores */
		LabelAlign alignExistencias = new LabelAlign(
				new DecimalFormat("##0.#").format(exist), AlignText.RIGHT);
		BandBoxRowMacro bandBoxRowMacro = getBanbBoxRow(detalleRecetaRips,
				articulo, textboxNombreMedicamento, autorizado,
				alignExistencias, labelFabricante,
				textboxNombreMedicamentoComercial);
		bandBoxRowMacro.setValue(codigo_articulo);

		// si no trabaja como la caja, vuelve a su estado normal
		if (!sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
			bandBoxRowMacro.setInplace(true);
			bandBoxRowMacro.setButtonVisible(false);
		}

		//log.info("row color :" + color_row);
		fila.setStyle("text-align: justify;nowrap:nowrap;" + color_row);
		fila.appendChild(checkbox);
		fila.appendChild(bandBoxRowMacro);
		fila.appendChild(textboxNombreMedicamento);
		fila.appendChild(textboxNombreMedicamentoComercial);
		fila.appendChild(labelFabricante);
		fila.appendChild(alignExistencias);
		fila.appendChild(new LabelAlign(detalleRecetaRips
				.getCantidad_recetada() + "", AlignText.RIGHT));
		fila.appendChild(auditor ? new LabelAlign(detalleRecetaRips
				.getCantidad_entregada() + "", AlignText.RIGHT)
				: textboxEntregar);
		fila.appendChild(new LabelAlign((detalleRecetaRips
				.getCantidad_pendiente() != null ? detalleRecetaRips
				.getCantidad_pendiente() : 0)
				+ "", AlignText.RIGHT));
		fila.appendChild(textboxFaltanteEntregar);
		// fila.appendChild(new
		// LabelState(anexo3Entidad.getLeido().equalsIgnoreCase("s") ? "Impreso"
		// : "Nuevo", leido));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/lock.gif");
		img.setTooltiptext("Negacion");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Map params = new HashMap();
				params.put("process", detalleRecetaRips.getCodigo_articulo());
				params.put("name", name);
				params.put("anexo3", anexo3Entidad);
				params.put("negacion", hbox);
				params.put("check", checkbox);

				Component componente = Executions.createComponents(
						"/pages/negacion.zul",
						FarmaciaRecetasRipsMedicamentosAction.this, params);
				final Window ventana = (Window) componente;
				ventana.setWidth("90%");
				ventana.setHeight("90%");
				ventana.doModal();
			}
		});

		//log.info(":::::::: Estado: " + (anexo3Entidad != null && noAutorizado));
		//log.info(":::::::: Estado: ");

		/* esto es si se utiliza una negacion */
		// if(noAutorizado && negacionLook == null)
		// hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setTooltiptext("Imprimir Negacion");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				// imprimirNegacion(negacionLook.getCodigo());
			}
		});

		if (negacionLook != null)
			hbox.appendChild(img);

		fila.appendChild(hbox);
		checkbox.addEventListener("onCheck", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				boolean isCheckNoAll = true;
				for (ContentCheck cbox : listado_check) {
					if (!cbox.getCheckbox().isChecked()) {
						isCheckNoAll = false;
					}
				}
				check_global.setChecked(isCheckNoAll);
			}
		});
		return fila;
	}

	private BandBoxRowMacro getBanbBoxRow(
			final Detalle_receta_rips detalleRecetaRips,
			final Articulo articulo, final Textbox textbox, boolean autorizado,
			final LabelAlign alignExistencias,
			final Label labelNombreFabricante,
			final Textbox textboxNombreMedicamentoComercial) {
		BandBoxRowMacro bandBoxRowMacro = new BandBoxRowMacro();
		bandBoxRowMacro.setHflex("1");
		if (articulo == null || !autorizado) {
			bandBoxRowMacro.setInplace(true);
			bandBoxRowMacro.setButtonVisible(false);
		} else {
			IConfiguracionBandbox<Articulo> configuracionBandbox = new IConfiguracionBandbox<Articulo>() {
				@Override
				public boolean seleccionarRegistro(Bandbox bandbox,
						Articulo registro) {
					// si es nulo, se esta haciendo un cambio
					if ((detalleRecetaRips.getCodigo_articulo_cambio() == null || detalleRecetaRips
							.getCodigo_articulo_cambio().trim().isEmpty())
							&& !detalleRecetaRips.getCodigo_articulo().equals(
									registro.getCodigo_articulo())) {
						detalleRecetaRips
								.setCodigo_articulo_cambio(detalleRecetaRips
										.getCodigo_articulo());
						;
						detalleRecetaRips.setCodigo_articulo(registro
								.getCodigo_articulo());
					} else if (detalleRecetaRips.getCodigo_articulo_cambio() != null
							&& !detalleRecetaRips.getCodigo_articulo_cambio()
									.trim().isEmpty()
							&& detalleRecetaRips.getCodigo_articulo_cambio()
									.equals(registro.getCodigo_articulo())) {
						detalleRecetaRips.setCodigo_articulo(registro
								.getCodigo_articulo());
						detalleRecetaRips.setCodigo_articulo_cambio(null);
						;
					}

					bandbox.setValue("" + registro.getCodigo_articulo());
					textbox.setValue("" + registro.getNombre1());
					textboxNombreMedicamentoComercial.setValue(""
							+ registro.getNombre2());

					Bodega_articulo bodega_articulo = new Bodega_articulo();
					bodega_articulo.setCodigo_empresa(registro
							.getCodigo_empresa());
					bodega_articulo.setCodigo_sucursal(registro
							.getCodigo_sucursal());
					bodega_articulo.setCodigo_bodega("01");
					bodega_articulo.setCodigo_articulo(registro
							.getCodigo_articulo());
					bodega_articulo = getServiceLocator()
							.getBodega_articuloService().consultar(
									bodega_articulo);

					Tercero ter = new Tercero();
					ter.setCodigo_empresa(articulo.getCodigo_empresa());
					ter.setCodigo_sucursal(articulo.getCodigo_sucursal());
					ter.setNro_identificacion(articulo.getFabricante());
					ter = getServiceLocator().getTerceroService()
							.consultar(ter);
					String nombre_fabricante = "*";
					if (ter != null) {
						nombre_fabricante = ter.getNombre1() + " "
								+ ter.getApellido1() + " " + ter.getApellido2();
					}
					labelNombreFabricante.setValue(nombre_fabricante);

					double exist = (bodega_articulo != null ? bodega_articulo
							.getCantidad() : 0);
					alignExistencias.setValue(new DecimalFormat("##0.#")
							.format(exist));
					boolean contiene_existencia = exist > 0;
					if (!contiene_existencia) {
						MensajesUtil.mensajeAlerta("Advertencia",
								"Este medicamento no tiene existencias");
					}
					return contiene_existencia;
				}

				@Override
				public void renderListitem(Listitem listitem, Articulo registro, Bandbox bandbox) {
					Bodega_articulo bodega_articulo = new Bodega_articulo();
					bodega_articulo.setCodigo_empresa(registro
							.getCodigo_empresa());
					bodega_articulo.setCodigo_sucursal(registro
							.getCodigo_sucursal());
					bodega_articulo.setCodigo_bodega("01");
					bodega_articulo.setCodigo_articulo(registro
							.getCodigo_articulo());
					bodega_articulo = getServiceLocator()
							.getBodega_articuloService().consultar(
									bodega_articulo);

					double exist = (bodega_articulo != null ? bodega_articulo
							.getCantidad() : 0);

					listitem.appendChild(new Listcell(""
							+ registro.getCodigo_articulo()));
					listitem.appendChild(new Listcell(""
							+ registro.getNombre1()));
					listitem.appendChild(new Listcell(""
							+ registro.getNombre2()));
					listitem.appendChild(new Listcell(
							new DecimalFormat("##0.#").format(exist)));
				}

				@Override
				public void onSeleccionar(Articulo t, Bandbox bandbox) {

				}

				@Override
				public List<Articulo> listarRegistros(String valorBusqueda,
						Map<String, Object> parametros, Bandbox bandbox) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("codigo_empresa", codigo_empresa);
					map.put("codigo_sucursal", codigo_sucursal);
					map.put("referencia", articulo.getReferencia());
					return getServiceLocator().getServicio(
							ArticuloService.class).listar(map);
				}

				@Override
				public void limpiarSeleccion(Bandbox bandbox) {

				}

				@Override
				public String getWidthListBox() {
					return "500px";
				}

				@Override
				public String getHeightListBox() {
					return "200px";
				}

				@Override
				public String getCabecera(Bandbox bandbox) {
					return "Codigo#100px|Nombre|Nombre comercial|Existencias#70px";
				}
				
			};
			// cargamos eventos para el cambio de medicamentos
			bandBoxRowMacro.configurar(configuracionBandbox);
		}
		return bandBoxRowMacro;
	}

	public void crearRevisionPorComite() throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("lista_check", listado_check);
		paramRequest.put("receta", receta_rips);

		Component componente = Executions.createComponents(
				"/pages/revision_comite.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.doModal();
	}

	public void imprimirNegacion(String codigo) throws Exception {
		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Negacion");
		paramRequest.put("codigo", codigo);

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void setCheck(boolean check) {
		for (ContentCheck contentCheck : listado_check) {
			contentCheck.getCheckbox().setChecked(check);
		}
	}

	public void imprimir(){
		try {
			imprimir(receta_rips != null ? receta_rips.getCodigo_receta() : "");
		} catch (Exception e) { 
			MensajesUtil.mensajeError(e, null, this); 
		} 
	}
	
	public void imprimir_receta() throws Exception {
		if (receta_rips.getCodigo_receta().equals("")) {
			Messagebox.show("La receta medica no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Receta_rips2");
		paramRequest.put("codigo_receta", receta_rips.getCodigo_receta());
		paramRequest.put("formato", "pdf");
		paramRequest.put("usuario_imprimio", usuarios.getNombres() + " "
				+ usuarios.getApellidos());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", form, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}
	
	public void imprimir(String codigo_receta) throws Exception {
		farmaciaRecetasRipsMedicamentosAction.imprimir(codigo_receta); 
	}

	protected void cargarMedicamentos(Receta_rips recetaRips) throws Exception {
		
		Map params = new HashMap();
		params.put("receta", recetaRips);

		Component componente = Executions.createComponents(
				"/pages/farmacia_recetas_rips_medicamentos.zul", this, params);
		final Window ventana = (Window) componente;
		ventana.setWidth("90%");
		ventana.setHeight("90%");
		ventana.setVflex("1");
		ventana.doModal();
	}

	private boolean pagaCopago() {
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(receta_rips.getCodigo_empresa());
		paciente.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
		paciente.setNro_identificacion(receta_rips.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null) {
			boolean pagacuotamoderadora = ServiciosDisponiblesUtils
					.pagaCuotaModeradora(paciente);
			if (pagacuotamoderadora) {
				Caja caja = new Caja();
				caja.setCodigo_empresa(receta_rips.getCodigo_empresa());
				caja.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
				caja.setCodigo_receta(receta_rips.getCodigo_receta());
				caja.setCopago_autorizaciones("N");
				caja.setCopago_medicamentos("S");
				caja.setMedio_pago(null);
				caja.setCodigo_cita(null);
				caja.setCodigo_anexo4(null);
				caja.setCodigo_anexo9(null);
				caja.setCodigo_orden(null);
				final Caja cajaEnd = getServiceLocator().getCajaService()
						.consultar(caja);
				//log.info("Recibo de caja: " + cajaEnd);
				final boolean pagaCopago = pagaCopago(receta_rips
						.getLista_detalle());
				return (cajaEnd == null && pagaCopago);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean pagaCopago(List<Detalle_receta_rips> listaDetalle) {
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(receta_rips.getCodigo_empresa());
		paciente.setCodigo_sucursal(receta_rips.getCodigo_sucursal());
		paciente.setNro_identificacion(receta_rips.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null)
			for (Detalle_receta_rips detalleRecetaRips : listaDetalle) {
				if (detalleRecetaRips.getEstado_cobro().equals("01")
						&& !paciente.getTipo_usuario().equals(
								IConstantes.REGIMEN_SUBCIDIADO)) {
					return true;
				}
			}
		return false;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarForm()) {
				// Cargamos los componentes //
				if (pagaCopago()) {
					Messagebox
							.show("Para entregar estos medicamentos debe cancelar la cuota moderadora",
									"Informacion ..", Messagebox.YES
											+ Messagebox.CANCEL,
									Messagebox.QUESTION, new EventListener() {
										@Override
										public void onEvent(Event arg0)
												throws Exception {
											if ("onYes".equals(arg0.getName())) {
												farmaciaRecetasRipsMedicamentosAction
														.generarCopago(
																receta_rips,
																null);
											}
										}
									});
				} else {
					getServiceLocator()
							.getDetalle_receta_ripsService()
							.guardarEntregaMedicamentos(receta_rips,
									list_detalleRecetaRipsAutorizados, usuarios);
					receta_rips = getServiceLocator().getReceta_ripsService()
							.consultar(receta_rips);
					buscarDatos();
					Messagebox
							.show("Registro de entrega realizado exitosamente",
									"Informcion", Messagebox.OK,
									Messagebox.INFORMATION);
					if (ordenes_medicas_enfermeraAction != null) {
						ordenes_medicas_enfermeraAction
								.mostrarInformacionRecetaRips(receta_rips, true);
					}
					
					
					Messagebox.show("Registro de entrega realizado exitosamente, ¿Deseas imprimir receta?",
							"Informacion ..", Messagebox.YES
									+ Messagebox.CANCEL,
							Messagebox.QUESTION, new EventListener() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									if ("onYes".equals(arg0.getName())) {		
												imprimir_receta(); 
										
									}
								}
							});
				}
			}
		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	

	public void autorizarMedicamentos() throws Exception {
		try {
			Messagebox.show("Esta seguro que desea realizar esta accion?",
					"Informacion", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								try {
									Receta_rips receta_rips = Res
											.clonar(FarmaciaRecetasRipsMedicamentosAction.this.receta_rips);
									receta_rips.setAuditado_farmacia("S");

									Map<String, Object> map = new HashMap<String, Object>();
									map.put("receta", receta_rips);
									map.put("dtt_receta",
											list_detalleRecetaRipsAutorizados);
									map.put("serviceLocator",
											getServiceLocator());
									getServiceLocator().getServicio(
											Receta_ripsService.class)
											.actualizar(map);
									FarmaciaRecetasRipsMedicamentosAction.this.receta_rips
											.setAuditado_farmacia("S");
									btn_entregar.setDisabled(false);
									MensajesUtil
											.mensajeInformacion("Informacion",
													"Datos guardado satisfactoriamente");
								} catch (Exception e) {
									MensajesUtil
											.mensajeError(
													e,
													null,
													FarmaciaRecetasRipsMedicamentosAction.this);
								}
							}
						}
					});
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	// public void mostrarDatos(Object obj) throws Exception {
	// Anexo3_entidad anexo4_entidad = (Anexo3_entidad) obj;
	// try {
	//
	// // Mostramos la vista //
	// tbxAccion.setText("modificar");
	// accionForm(true, tbxAccion.getText());
	// } catch (Exception e) {
	// 
	// log.error(e.getMessage(), e);
	// Messagebox.show("Este dato no se puede editar", "Error !!",
	// Messagebox.OK, Messagebox.ERROR);
	// }
	// }

	public void eliminarDatos(Object obj) throws Exception {
		Anexo3_entidad anexo4_entidad = (Anexo3_entidad) obj;
		try {
			int result = getServiceLocator().getAnexo3EntidadService()
					.eliminar(anexo4_entidad);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			
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

	@Override
	public void init() throws Exception {
		list_detalleRecetaRipsAutorizados = new ArrayList<Detalle_receta_rips>();
		listado_check = new ArrayList<ContentCheck>();
		cargarReceta();
		buscarDatos();
		if (getParent() instanceof FarmaciaRecetasRipsAction) {
			farmaciaRecetasRipsMedicamentosAction = (FarmaciaRecetasRipsAction) getParent();
		} else if (getParent() instanceof Ordenes_medicas_enfermeraAction) {
			ordenes_medicas_enfermeraAction = (Ordenes_medicas_enfermeraAction) getParent();
		}
	}

}
