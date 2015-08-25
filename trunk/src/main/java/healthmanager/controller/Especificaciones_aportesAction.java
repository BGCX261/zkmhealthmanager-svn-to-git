/*
 * especificaciones_aportesAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Especificaciones_aportes;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
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
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.CargardorDeDatos;
import com.framework.util.MensajesUtil;

public class Especificaciones_aportesAction extends Window implements
		AfterCompose {

	private static Logger log = Logger
			.getLogger(Especificaciones_aportesAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	private Window form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Borderlayout groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;

//	private Textbox tbxDescripcion;

	private Intbox ibxPos_anio;
	private Intbox ibxPos_mes;
	private Intbox ibxPos_cedula;
	private Intbox ibxPos_dias;
	private Intbox ibxPos_ibc;
	private Intbox ibxPos_cotizacion;
	private Checkbox chbCargar_dias_desde_mmyyyy;
	private Listbox lbxSeparado_por;

	@View
	private Row row_dias;
	@View
	private Textbox tbxNombre;

	@View
	private Intbox ibxId;

	private Aportes_cotizacionesAction aportesCotizacionesAction;

	@Override
	public void afterCompose() {
		CargardorDeDatos.initComponents(this);
		loadComponents();
		cargarDatosSesion();
		listarCombos();
		aportesCotizacionesAction = (Aportes_cotizacionesAction) getParent();
	}

	public void loadComponents() {
		form = (Window) this.getFellow("formEspecificaciones_aportes");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		groupboxEditar = (Borderlayout) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

		lbxSeparado_por = (Listbox) form.getFellow("lbxSeparado_por");
		ibxPos_anio = (Intbox) form.getFellow("ibxPos_anio");
		ibxPos_mes = (Intbox) form.getFellow("ibxPos_mes");
		ibxPos_cedula = (Intbox) form.getFellow("ibxPos_cedula");
		ibxPos_dias = (Intbox) form.getFellow("ibxPos_dias");
		ibxPos_ibc = (Intbox) form.getFellow("ibxPos_ibc");
		ibxPos_cotizacion = (Intbox) form.getFellow("ibxPos_cotizacion");
		chbCargar_dias_desde_mmyyyy = (Checkbox) form
				.getFellow("chbCargar_dias_desde_mmyyyy");

		inicializarEventos();
	}

	private void inicializarEventos() {
		chbCargar_dias_desde_mmyyyy.addEventListener("onCheck",
				new EventListener() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						verificamosSeleccion();
					}
				});
	}

	protected void verificamosSeleccion() {
		row_dias.setVisible(!chbCargar_dias_desde_mmyyyy.isChecked());
	}

	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}

	public void listarCombos() {
		listarParameter();
		listarElementoListbox(lbxSeparado_por, true);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("id::varchar");
		listitem.setLabel("Id");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("descripcion");
		listitem.setLabel("Descripcion");
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
		lbxSeparado_por.setStyle("background-color:white");
		ibxPos_anio.setStyle("background-color:white");
		ibxPos_mes.setStyle("background-color:white");
		ibxPos_cedula.setStyle("background-color:white");
		ibxPos_dias.setStyle("background-color:white");
		ibxPos_ibc.setStyle("background-color:white");
		ibxPos_cotizacion.setStyle("background-color:white");
		tbxNombre.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		if (tbxNombre.getText().trim().equals("")) {
			tbxNombre
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (ibxPos_anio.getText().trim().equals("")) {
			ibxPos_anio.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (ibxPos_mes.getText().trim().equals("")) {
			ibxPos_mes.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (ibxPos_cedula.getText().trim().equals("")) {
			ibxPos_cedula.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxSeparado_por.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxSeparado_por.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		// if(ibxPos_dias.getText().trim().equals("") &&
		// !chbCargar_dias_desde_mmyyyy.isChecked()){
		// ibxPos_dias.setStyle("background-color:#F6BBBE");
		// valida = false;
		// }
		if (ibxPos_ibc.getText().trim().equals("")) {
			ibxPos_ibc.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (ibxPos_cotizacion.getText().trim().equals("")) {
			ibxPos_cotizacion.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getEspecificacionesAportesService().setLimit(
					"limit 25 offset 0");

			List<Especificaciones_aportes> lista_datos = getServiceLocator()
					.getEspecificacionesAportesService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Especificaciones_aportes especificaciones_aportes : lista_datos) {
				rowsResultado.appendChild(crearFilas(especificaciones_aportes,
						this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Especificaciones_aportes especificaciones_aportes = (Especificaciones_aportes) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(especificaciones_aportes.getId() + ""));
		fila.appendChild(new Label(especificaciones_aportes.getNombre() + ""));
		fila.appendChild(new Label(especificaciones_aportes.getDescripcion()
				.replace("\n", " ")));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(especificaciones_aportes);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
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
									eliminarDatos(especificaciones_aportes);
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

	@Override
	public void onClose() {
		aportesCotizacionesAction.listarEspecificaciones();
		super.onClose();
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //
				Especificaciones_aportes especificaciones_aportes = new Especificaciones_aportes();
				especificaciones_aportes
						.setId((ibxId.getValue() != null ? ibxId.getValue() : 0));
				especificaciones_aportes.setCodigo_empresa(empresa
						.getCodigo_empresa());
				especificaciones_aportes.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				especificaciones_aportes.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				especificaciones_aportes.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				especificaciones_aportes.setCreacion_user(usuarios.getCodigo()
						.toString());
				especificaciones_aportes.setUltimo_user(usuarios.getCodigo()
						.toString());
				especificaciones_aportes.setSeparado_por(lbxSeparado_por
						.getSelectedItem().getValue().toString());
				especificaciones_aportes
						.setPos_anio((ibxPos_anio.getValue() != null ? ibxPos_anio
								.getValue() : 0));
				especificaciones_aportes
						.setPos_mes((ibxPos_mes.getValue() != null ? ibxPos_mes
								.getValue() : 0));
				especificaciones_aportes.setPos_cedula((ibxPos_cedula
						.getValue() != null ? ibxPos_cedula.getValue() : 0));
				especificaciones_aportes
						.setPos_dias((ibxPos_dias.getValue() != null ? ibxPos_dias
								.getValue() : 0));
				especificaciones_aportes
						.setPos_ibc((ibxPos_ibc.getValue() != null ? ibxPos_ibc
								.getValue() : 0));
				especificaciones_aportes
						.setPos_cotizacion((ibxPos_cotizacion.getValue() != null ? ibxPos_cotizacion
								.getValue() : 0));
				especificaciones_aportes
						.setCargar_dias_desde_mmyyyy(chbCargar_dias_desde_mmyyyy
								.isChecked() ? "S" : "N");
				especificaciones_aportes.setNombre(tbxNombre.getValue());
				
				validarPosiciones(especificaciones_aportes); 

				String descripcion = getDescripcion(especificaciones_aportes);
				especificaciones_aportes.setDescripcion(descripcion);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getEspecificacionesAportesService()
							.crear(especificaciones_aportes);
					accionForm(true, "registrar");
				} else {
					int result = getServiceLocator()
							.getEspecificacionesAportesService().actualizar(
									especificaciones_aportes);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				Messagebox.show("Los datos se guardaron satisfactoriamente",
						"Informacion ..", Messagebox.OK,
						Messagebox.INFORMATION);

			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this); 
		}

	}

	/**
	 * Este metodo me permite validar si las posiciones son correctas
	 * */
	public static void validarPosiciones(
			Especificaciones_aportes especificaciones) {
		Object[][] posiciones =  { {especificaciones.getPos_anio(), "año"},
				{especificaciones.getPos_cedula(), "Cédula"},
				{especificaciones.getPos_ibc(), "IBC"}, {especificaciones.getPos_mes(), "Mes"},
				{especificaciones.getPos_cotizacion(), "Cotizaciones" }};
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (Object[] posicion : posiciones) {
			 Integer pos = (Integer) posicion[0];
			 String descipcion = (String) posicion[1];
			 
			 if(map.containsKey(pos)){
				 String descipcion_campo_repetido = map.get(pos); 
				 throw new ValidacionRunTimeException("La posicion del campo " + descipcion + " se encuantra repetida con  la posicion del campo " + descipcion_campo_repetido); 
			 }else{
				 map.put(pos, descipcion); 
			 }
			 if(pos.intValue() <= 0){
				 throw new ValidacionRunTimeException("La posicion del campo " + descipcion  + " no puede ser menor o igual a cero"); 
			 }else if(pos.intValue() > 5){
				 throw new ValidacionRunTimeException("La posision del campo " + descipcion  + " no puede ser mayor que 5"); 
			 }
		}
	}

	private String getDescripcion(
			Especificaciones_aportes especificacionesAportes) {
		StringBuilder builder = new StringBuilder();
		builder.append("Separado por: "
				+ lbxSeparado_por.getSelectedItem().getLabel() + "\n");
		builder.append("año: " + especificacionesAportes.getPos_anio() + "\n");
		builder.append("Mes: " + especificacionesAportes.getPos_mes() + "\n");
		builder.append("Cédula: " + especificacionesAportes.getPos_cedula()
				+ "\n");
		// builder.append("Dias: " + especificacionesAportes.getPos_dias()+
		// "\n");
		// builder.append("Ibc: " +
		// (especificacionesAportes.getCargar_dias_desde_mmyyyy().equals("S") ?
		// especificacionesAportes.getPos_dias() :
		// "Cargado dependiendo el mes y año")+ "\n");
		builder.append("Ibc: " + especificacionesAportes.getPos_ibc() + "\n");
		builder.append("Cotizacion: " + especificacionesAportes.getPos_cotizacion()
				+ "\n");
		return builder.toString();
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Especificaciones_aportes especificaciones_aportes = (Especificaciones_aportes) obj;
		try {
			for (int i = 0; i < lbxSeparado_por.getItemCount(); i++) {
				Listitem listitem = lbxSeparado_por.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(especificaciones_aportes.getSeparado_por())) {
					listitem.setSelected(true);
					i = lbxSeparado_por.getItemCount();
				}
			}
			ibxId.setValue(especificaciones_aportes.getId());
			ibxPos_anio.setValue(especificaciones_aportes.getPos_anio());
			ibxPos_mes.setValue(especificaciones_aportes.getPos_mes());
			ibxPos_cedula.setValue(especificaciones_aportes.getPos_cedula());
			ibxPos_dias.setValue(especificaciones_aportes.getPos_dias());
			ibxPos_ibc.setValue(especificaciones_aportes.getPos_ibc());
			ibxPos_cotizacion.setValue(especificaciones_aportes
					.getPos_cotizacion());
			chbCargar_dias_desde_mmyyyy.setChecked(especificaciones_aportes
					.getCargar_dias_desde_mmyyyy().equals("S"));
			tbxNombre.setValue(especificaciones_aportes.getNombre());
//			verificamosSeleccion();

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Especificaciones_aportes especificaciones_aportes = (Especificaciones_aportes) obj;
		try {
			int result = getServiceLocator()
					.getEspecificacionesAportesService().eliminar(
							especificaciones_aportes);
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

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}
}
