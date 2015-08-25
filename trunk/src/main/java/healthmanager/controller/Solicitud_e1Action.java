/*
 * solicitud_e1Action.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.controller.Beneficiarios_meAction.ACCION_BENEFICIARIOS;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Aportantes_ma;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Solicitud_e1;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.Afiliaciones_meService;
import healthmanager.modelo.service.Aportantes_maService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.LabelState;
import com.framework.util.FormularioUtil;
import com.framework.util.Utilidades;

public class Solicitud_e1Action extends ZKWindow {

	// Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;


	@View private Label lbTipo_identificacion_bdua;
	@View private Listbox lbxTipo_identificacion_bdua;
	@View private Label lbNro_identificacion_bdua;
	@View private Textbox tbxNro_identificacion_bdua;
	@View private Label lbApellido1_bdua;
	@View private Textbox tbxApellido1_bdua;
	@View private Label lbApellido2_bdua;
	@View private Textbox tbxApellido2_bdua;
	@View private Label lbNombre1_bdua;
	@View private Textbox tbxNombre1_bdua;
	@View private Label lbNombre2_bdua;
	@View private Textbox tbxNombre2_bdua;
	@View private Label lbFecha_nacimiento_bdua;
	@View private Datebox dtbxFecha_nacimiento_bdua;
	@View private Label lbSexo_bdua;
	@View private Listbox lbxSexo_bdua;
	@View private Label lbTipo_identificacion;
	@View private Listbox lbxTipo_identificacion;
	@View private Label lbNro_identificacion;
	@View private Textbox tbxNro_identificacion;
	@View private Label lbApellido1;
	@View private Textbox tbxApellido1;
	@View private Label lbApellido2;
	@View private Textbox tbxApellido2;
	@View private Label lbNombre1;
	@View private Textbox tbxNombre1;
	@View private Label lbNombre2;
	@View private Textbox tbxNombre2;
	@View private Label lbFecha_nacimiento;
	@View private Datebox dtbxFecha_nacimiento;
	@View private Label lbSexo;
	@View private Listbox lbxSexo;
	@View private Label lbCodigo_dpto;
	@View private Listbox lbxCodigo_dpto;
	@View private Label lbCodigo_municipio;
	@View private Listbox lbxCodigo_municipio;
	@View private Label lbZona;
	@View private Listbox lbxZona;
	@View private Label lbFecha_afiliacion;
	@View private Datebox dtbxFecha_afiliacion;
	@View(isMacro = true) private BandboxRegistrosMacro tbxNro_identificacion_aportante;
	
	@View private Toolbarbutton tbxAportanteBorrar;
	@View private Textbox tbxNombreAportante;
	
	@View private Listbox lbxTipo_id_cotizante;
	@View(isMacro = true) private BandboxRegistrosMacro tbxNro_id_cotizante;
	
	@View private Intbox ibxId;
	
	@View private Toolbarbutton btGuardar;
	@View private Toolbarbutton btNew;
	@View private Toolbarbutton btCancel;
	
	@View private Textbox tbxNombreCotizante;
	@View private Textbox tbxApellidosCotizante;
	@View private Toolbarbutton btnlimpiarCotizante;
	
	@View(isMacro = true) private BandboxRegistrosMacro tbxCodigo_administradora;
	@View private Textbox tbxNomAdministradora;
	
	private final String[] ids = { "btGuardar", "btNew", "btCancel",
			"tbxNro_id_cotizante", "lbxTipo_id_cotizante",
			"tbxNombreCotizante", "tbxApellidos", "tbxCodigo_administradora",
			"tbxNomAdministradora" };

	private void verificamosParametros() {
		try {
			try {
				getParametrosEmpresa();
			} catch (Exception e) {
				Messagebox.show("" + e.getMessage(), "Informacion ..",
						Messagebox.OK, Messagebox.EXCLAMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listarCombos() {
		listarParameter();
		listarElementoListbox(lbxZona, false);
		listarDepartamentos(lbxCodigo_dpto, true);
		listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
		listarElementoListbox(lbxSexo, true);
		listarElementoListbox(lbxSexo_bdua, true);
		listarElementoListboxTipoId(lbxTipo_identificacion, true, "permitidos2");
		listarElementoListboxTipoId(lbxTipo_identificacion_bdua, true,
				"permitidos2");
		listarElementoListboxTipoId(lbxTipo_id_cotizante, true, "permitidos4");
	}

	public void listarElementoListboxTipoId(Listbox listbox, boolean select,
			String permitodo) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tipo", "tipo_id");
		map.put(permitodo, "_");

		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(map);

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

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("creacion_date::varchar");
		listitem.setLabel("Fecha de Solicitud(YYYY-MM-DD)");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacion_bdua ||' '|| nombre1_bdua||' '||nombre2_bdua ||' '|| apellido1_bdua||' '||apellido2_bdua ");
		listitem.setLabel("Informacion BDUA");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacion ||' '|| nombre1||' '||nombre2 ||' '|| apellido1||' '||apellido2 ");
		listitem.setLabel("Informacion Actualizada");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("fecha_afiliacion::varchar");
		listitem.setLabel("Fecha Afiliacion(YYYY-MM-DD)");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacion_aportante");
		listitem.setLabel("Nro identificacion Aportante");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_id_cotizante");
		listitem.setLabel("Nro identificacion Cotizante");
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

	public void listarDepartamentos(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		List<Departamentos> departamentos = this.getServiceLocator()
				.getDepartamentosService().listar(new HashMap<String,Object>());

		for (Departamentos dpto : departamentos) {
			listitem = new Listitem();
			listitem.setValue(dpto.getCodigo());
			listitem.setLabel(dpto.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		listboxMun.getChildren().clear();
		Listitem listitem;
		String coddep = listboxDpto.getSelectedItem().getValue().toString();
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("coddep", coddep);
		List<Municipios> municipios = this.getServiceLocator()
				.getMunicipiosService().listar(parameters);

		if (municipios.size() > 0) {
			for (Municipios mun : municipios) {
				listitem = new Listitem();
				listitem.setValue(mun.getCodigo());
				listitem.setLabel(mun.getNombre());
				listboxMun.appendChild(listitem);
			}
		} else {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel(" -seleccione- ");
			listboxMun.appendChild(listitem);
		}

		if (listboxMun.getItemCount() > 0) {
			listboxMun.setSelectedIndex(0);
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

	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(groupboxEditar, sw, ids); 
		tbxNro_id_cotizante.setButtonVisible(!sw); 
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar); 
		listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
		deshabilitarCampos(false);
		dtbxFecha_afiliacion.setValue(null);
		dtbxFecha_nacimiento.setValue(null);
		dtbxFecha_nacimiento_bdua.setValue(null); 
		btGuardar.setDisabled(false); 
		tbxNro_id_cotizante.setReadonly(true); 
		btnlimpiarCotizante.setVisible(false); 
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		try {
			FormularioUtil.validarCamposObligatorios(
					lbxTipo_identificacion_bdua, tbxNro_identificacion_bdua,
					tbxApellido1_bdua, tbxApellido2_bdua, tbxNombre1_bdua,
					tbxNombre2_bdua, dtbxFecha_nacimiento_bdua, lbxSexo_bdua,
					lbxTipo_identificacion, tbxNro_identificacion,
					tbxApellido1, tbxApellido2, tbxNombre1, tbxNombre2,
					dtbxFecha_nacimiento, lbxSexo, lbxCodigo_dpto,
					lbxCodigo_municipio, lbxZona, dtbxFecha_afiliacion,
					tbxNro_identificacion_aportante, tbxCodigo_administradora);
		} catch (WrongValueException e) {
			valida = false;
		}
		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getSolicitudE1Service().setLimit(
					"limit 25 offset 0");

			List<Solicitud_e1> lista_datos = getServiceLocator()
					.getSolicitudE1Service().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Solicitud_e1 solicitud_e1 : lista_datos) {
				rowsResultado.appendChild(crearFilas(solicitud_e1, this));
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

		final Solicitud_e1 solicitud_e1 = (Solicitud_e1) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

//		Departamentos departamentos = new Departamentos();
//		departamentos.setCodigo(solicitud_e1.getCodigo_dpto());
//		departamentos = getServiceLocator().getDepartamentosService()
//				.consultar(departamentos);
//
//		Municipios municipios = new Municipios();
//		municipios.setCoddep(solicitud_e1.getCodigo_dpto());
//		municipios.setCodigo(solicitud_e1.getCodigo_municipio());
//		municipios = getServiceLocator().getMunicipiosService().consultar(
//				municipios);

		Afiliaciones_me afiliacionesMe = new Afiliaciones_me();
		afiliacionesMe.setCodigo_empresa(usuarios.getCodigo_empresa());
		afiliacionesMe.setCodigo_sucursal(usuarios.getCodigo_sucursal());
		afiliacionesMe.setNro_identificacion_afiliado(solicitud_e1
				.getNro_identificacion());
		afiliacionesMe = getServiceLocator().getAfiliacionesMeService()
				.consultar(afiliacionesMe);

		boolean aceptada = false;
		Cell cell = new Cell();
		if (solicitud_e1.getEstado_respuesta() != null && !solicitud_e1.getEstado_respuesta().isEmpty()) {
			aceptada = solicitud_e1.getEstado_respuesta().equals("1");
			String color = solicitud_e1.getEstado_respuesta().equals("1") ? "b6e69c"
					: "e8dd87";
			cell.setStyle("background-color: #?1;text-align: justify;nowrap:nowrap"
					.replace("?1", color));
			
			if(solicitud_e1.getEstado_respuesta().equals("1")){
				Image image = new Image();
				image.setSrc("/images/activo.gif");
				cell.appendChild(image);
			}
			
			fila.setTooltiptext(solicitud_e1.getEstado_respuesta().equals("1") ? "Aceptada"
					+ (afiliacionesMe != null ? " y ya se encuentra Afiliado"
							: "")
					: "Negada");
		} else {
			fila.setStyle("text-align: justify;nowrap:nowrap");
		}
		fila.appendChild(cell);
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(solicitud_e1.getCreacion_date()) + ""));
		fila.appendChild(new Label(solicitud_e1.getNro_identificacion() + ""));
		fila.appendChild(new Label(solicitud_e1.getApellido1() + ""));
		fila.appendChild(new Label(solicitud_e1.getApellido2() + ""));
		fila.appendChild(new Label(solicitud_e1.getNombre1() + ""));
		fila.appendChild(new Label(solicitud_e1.getNombre2() + ""));
//		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
//				.format(solicitud_e1.getFecha_nacimiento()) + ""));
//		fila.appendChild(new Label(departamentos != null ? departamentos
//				.getNombre() : ""));
//		fila.appendChild(new Label(municipios != null ? municipios.getNombre()
//				: ""));
//		fila.appendChild(new Label(solicitud_e1
//				.getNro_identificacion_aportante() + ""));
//		fila.appendChild(new Label(solicitud_e1.getNro_id_cotizante() + ""));
		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(solicitud_e1);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/add_perfil.png");
		img.setTooltiptext("Afiliar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				afiliar(solicitud_e1);
			}
		});
		if (aceptada && afiliacionesMe == null) {
			hbox.appendChild(space);
			hbox.appendChild(img);
		}

		fila.appendChild(hbox);
		return fila;
	}

	protected void afiliar(Solicitud_e1 solicitudE1) throws Exception {
		try {
			if (solicitudE1.getNro_identificacion().equalsIgnoreCase(
					solicitudE1.getNro_id_cotizante())) {
				// si es cotizante
				afiliarCotizante(solicitudE1);
			} else {
				// si es beneficiario
				Afiliaciones_me afiliacionesMe = new Afiliaciones_me();
				afiliacionesMe.setCodigo_empresa(usuarios.getCodigo_empresa());
				afiliacionesMe
						.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				afiliacionesMe.setNro_identificacion_afiliado(solicitudE1
						.getNro_id_cotizante());

				if (getServiceLocator().getAfiliacionesMeService().consultar(
						afiliacionesMe) != null) {
					afiliarBeneficiario(solicitudE1);
				} else
					throw new Exception(
							"El cotizante no existe para afiliar esta persona");
			}
		} catch (Exception e) {
			//  block
			e.printStackTrace();
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void afiliarCotizante(Solicitud_e1 solicitudE1) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("SE1", solicitudE1); // accion modificar beneficiarios
		Component componente = Executions.createComponents(
				"/pages/afiliaciones_me.zul", this, map);
		final Window ventana = (Window) componente;
		ventana.setWidth("95%");
		ventana.setHeight("95%");
		ventana.setVflex("1");
		ventana.doModal();
	}

	public void afiliarBeneficiario(Solicitud_e1 solicitudE1) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accion", ACCION_BENEFICIARIOS.AFILIAR_NUEVO); // accion agregar
																// beneficiarios
		map.put("SE1", solicitudE1); // accion modificar beneficiarios
		Component componente = Executions.createComponents(
				"/pages/beneficiarios.zul", this, map);
		final Window ventana = (Window) componente;
		ventana.setWidth("95%");
		ventana.setHeight("95%");
		ventana.setVflex("1");
		ventana.doModal();
	}

	private Parametros_empresa getParametrosEmpresa() {
		if (parametros_empresa == null) {
			throw new HealthmanagerException(
					"Para Esta opcion agregar un parametro de la empresa");
		}
		return parametros_empresa;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //
				Parametros_empresa parametrosEmpresa = getParametrosEmpresa();

				Solicitud_e1 solicitud_e1 = new Solicitud_e1();
				solicitud_e1.setCodigo_empresa(empresa.getCodigo_empresa());
				solicitud_e1.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				solicitud_e1
						.setTipo_identificacion_bdua(lbxTipo_identificacion_bdua
								.getSelectedItem().getValue().toString());
				solicitud_e1
						.setNro_identificacion_bdua(tbxNro_identificacion_bdua
								.getValue());
				solicitud_e1.setApellido1_bdua(tbxApellido1_bdua.getValue());
				solicitud_e1.setApellido2_bdua(tbxApellido2_bdua.getValue());
				solicitud_e1.setNombre1_bdua(tbxNombre1_bdua.getValue());
				solicitud_e1.setNombre2_bdua(tbxNombre2_bdua.getValue());
				solicitud_e1.setFecha_nacimiento_bdua(new Timestamp(
						dtbxFecha_nacimiento_bdua.getValue().getTime()));
				solicitud_e1.setSexo_bdua(lbxSexo_bdua.getSelectedItem()
						.getValue().toString());
				solicitud_e1.setTipo_identificacion(lbxTipo_identificacion
						.getSelectedItem().getValue().toString());
				solicitud_e1.setNro_identificacion(tbxNro_identificacion
						.getValue());
				solicitud_e1.setApellido1(tbxApellido1.getValue());
				solicitud_e1.setApellido2(tbxApellido2.getValue());
				solicitud_e1.setNombre1(tbxNombre1.getValue());
				solicitud_e1.setNombre2(tbxNombre2.getValue());
				solicitud_e1.setFecha_nacimiento(new Timestamp(
						dtbxFecha_nacimiento.getValue().getTime()));
				solicitud_e1.setSexo(lbxSexo.getSelectedItem().getValue()
						.toString());
				solicitud_e1.setCodigo_dpto(lbxCodigo_dpto.getSelectedItem()
						.getValue().toString());
				solicitud_e1.setCodigo_municipio(lbxCodigo_municipio
						.getSelectedItem().getValue().toString());
				solicitud_e1.setZona(lbxZona.getSelectedItem().getValue()
						.toString());
				solicitud_e1.setFecha_afiliacion(new Timestamp(
						dtbxFecha_afiliacion.getValue().getTime()));
				solicitud_e1.setTipo_id_aportante("");
				Aportantes_ma aportantes_ma = tbxNro_identificacion_aportante.getRegistroSeleccionado();
				solicitud_e1
						.setNro_identificacion_aportante(aportantes_ma != null ? aportantes_ma.getCodigo() : "");
				solicitud_e1.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				solicitud_e1.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				solicitud_e1.setCreacion_user(usuarios.getCodigo().toString());
				solicitud_e1.setUltimo_user(usuarios.getCodigo().toString());
				solicitud_e1.setTipo_id_cotizante(lbxTipo_id_cotizante
						.getSelectedItem().getValue().toString());
				// si tiene un cotizante seleccionado es por que un beneficiario
				solicitud_e1
						.setNro_id_cotizante(tbxNro_id_cotizante.getValue());
				solicitud_e1.setId((ibxId.getValue() != null ? ibxId.getValue()
						: 0));
				solicitud_e1.setCodigo_entidad(parametrosEmpresa
						.getCodigo_ministerio());

				solicitud_e1.setCodigo_administradora(tbxCodigo_administradora.getValue()); 
				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getSolicitudE1Service().crear(
							solicitud_e1);
					accionForm(true, "registrar");
				} else {
					int result = getServiceLocator().getSolicitudE1Service()
							.actualizar(solicitud_e1);
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
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	private void setSelect(Listbox listbox, String value) {
		for (int i = 0; i < listbox.getItemCount(); i++) {
			Listitem listitem = listbox.getItemAtIndex(i);
			if (listitem.getValue().toString().equals(value)) {
				listitem.setSelected(true);
				break;
			}
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Solicitud_e1 solicitud_e1 = (Solicitud_e1) obj;
		try {
			setSelect(lbxTipo_id_cotizante, solicitud_e1.getTipo_id_cotizante());
			setSelect(lbxTipo_identificacion_bdua,
					solicitud_e1.getTipo_identificacion_bdua());
			setSelect(lbxSexo_bdua, solicitud_e1.getSexo_bdua());
			setSelect(lbxTipo_identificacion,
					solicitud_e1.getTipo_identificacion());
			setSelect(lbxSexo, solicitud_e1.getSexo());
			setSelect(lbxCodigo_dpto, solicitud_e1.getCodigo_dpto());
			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			setSelect(lbxCodigo_municipio, solicitud_e1.getCodigo_municipio());
			setSelect(lbxZona, solicitud_e1.getZona());
//			setSelect(lbxTipo_id_aportante, solicitud_e1.getTipo_id_aportante());

//			tbxNro_id_cotizante.setValue(solicitud_e1.getNro_id_cotizante());
			
			Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
			afiliaciones_me.setCodigo_empresa(solicitud_e1.getCodigo_empresa());
			afiliaciones_me.setCodigo_sucursal(solicitud_e1.getCodigo_sucursal());
			afiliaciones_me.setNro_identificacion_afiliado(solicitud_e1.getNro_id_cotizante());
			afiliaciones_me = getServiceLocator().getServicio(Afiliaciones_meService.class).consultar(afiliaciones_me);
			
			if(afiliaciones_me != null){
				tbxNro_id_cotizante.seleccionarRegistro(afiliaciones_me, afiliaciones_me.getNro_identificacion_afiliado(), ""); 
				seleccionarCotizante(afiliaciones_me); 
			}
			
			
			tbxNro_identificacion_bdua.setValue(solicitud_e1
					.getNro_identificacion_bdua());
			tbxApellido1_bdua.setValue(solicitud_e1.getApellido1_bdua());
			tbxApellido2_bdua.setValue(solicitud_e1.getApellido2_bdua());
			tbxNombre1_bdua.setValue(solicitud_e1.getNombre1_bdua());
			tbxNombre2_bdua.setValue(solicitud_e1.getNombre2_bdua());
			dtbxFecha_nacimiento_bdua.setValue(solicitud_e1
					.getFecha_nacimiento_bdua());
			tbxNro_identificacion
					.setValue(solicitud_e1.getNro_identificacion());
			tbxApellido1.setValue(solicitud_e1.getApellido1());
			tbxApellido2.setValue(solicitud_e1.getApellido2());
			tbxNombre1.setValue(solicitud_e1.getNombre1());
			tbxNombre2.setValue(solicitud_e1.getNombre2());
			dtbxFecha_nacimiento.setValue(solicitud_e1.getFecha_nacimiento());
			dtbxFecha_afiliacion.setValue(solicitud_e1.getFecha_afiliacion());
			
			
			ibxId.setValue(solicitud_e1.getId());
			
			 // consultamios administradora
			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(solicitud_e1.getCodigo_empresa());
			administradora.setCodigo_sucursal(solicitud_e1.getCodigo_sucursal());
			administradora.setCodigo(solicitud_e1.getCodigo_administradora()); 
			administradora = getServiceLocator().getServicio(AdministradoraService.class).consultar(administradora);
			if(administradora != null){
				tbxCodigo_administradora.seleccionarRegistro(administradora, administradora.getCodigo(), administradora.getNombre()); 
			}
			
			Aportantes_ma aportantes_ma = new Aportantes_ma();
			aportantes_ma.setCodigo_empresa(solicitud_e1.getCodigo_empresa());
			aportantes_ma.setCodigo_sucursal(solicitud_e1.getCodigo_sucursal());
			aportantes_ma.setCodigo(solicitud_e1.getNro_identificacion_aportante());
			aportantes_ma = getServiceLocator().getServicio(Aportantes_maService.class).consultar(aportantes_ma); 
			if(aportantes_ma != null){
				tbxNro_identificacion_aportante.seleccionarRegistro(aportantes_ma, aportantes_ma.getNro_identificacon(), aportantes_ma.getNombre()); 
			}else{
				tbxNro_identificacion_aportante.limpiarSeleccion(false); 
			}

			if (solicitud_e1.getEstado_respuesta() != null)
				deshabilitarCampos(true);
			
			
			btGuardar.setDisabled(true); 
			
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
		Solicitud_e1 solicitud_e1 = (Solicitud_e1) obj;
		try {
			int result = getServiceLocator().getSolicitudE1Service().eliminar(
					solicitud_e1);
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
		listarCombos();
		verificamosParametros();
		parametrizarBandbox(); 
	}

	private void parametrizarBandbox( ) {
		parametrizarCotizante();
		parametrizarAdministradora();
		parametrizarAportante();
	}

	private void parametrizarCotizante() {
		tbxNro_id_cotizante.inicializar(null, btnlimpiarCotizante); 
		tbxNro_id_cotizante.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Afiliaciones_me>(){
			@Override
			public void renderListitem(Listitem listitem,
					Afiliaciones_me registro) {
				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getPaciente()
						.getTipo_identificacion() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro
						.getNro_identificacion_afiliado()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getPaciente()
						.getNombreCompleto()));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Afiliaciones_me> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("codigo_empresa", empresa.getCodigo_empresa());
				parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				parameters.put("paramTodo", "");
				parameters.put("value", "%" + valorBusqueda.toUpperCase().trim() + "%");
				parameters.put("tipo_afiliado", "C");
				parameters.put("diferent_me", tbxNro_identificacion.getValue());

				getServiceLocator().getAfiliacionesMeService().setLimit(
						"limit 25 offset 0");

				return getServiceLocator()
						.getAfiliacionesMeService().listar(parameters);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Afiliaciones_me registro) {
				bandbox.setValue("" + registro.getNro_identificacion_afiliado());
				textboxInformacion.setValue("" + registro.getPaciente().getNombreCompleto());
				seleccionarCotizante(registro); 
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				tbxNombreCotizante.setValue("");
				tbxApellidosCotizante.setValue("");
				lbxTipo_id_cotizante.setSelectedIndex(0); 
			}
			
		});
	}
	
	
	private void parametrizarAdministradora() {
		tbxCodigo_administradora.inicializar(tbxNomAdministradora, null);
		tbxCodigo_administradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNit()));
						listitem.appendChild(new Listcell(registro.getNombre()));

						String tipo_aseguradora = "";
						Listcell listcell = new Listcell();
						if (registro
								.getTipo_aseguradora()
								.equals(IConstantes.TIPO_ASEGURADORA_PARTICULARES_PERSONAS_NATURALES)) {
							tipo_aseguradora = "PARTICULAR";
							listcell.setStyle("background-color:#96D9FA;");
						}
						listcell.appendChild(new LabelState(tipo_aseguradora,
								true));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								codigo_empresa);
						parametros.put("codigo_sucursal",
								codigo_sucursal);
						parametros
								.put("tipos_aseguradora",
										new String[] {
												IConstantes.TIPO_ASEGURADORA_EPS_REGIMEN_CONTRIBUTIVO,
												IConstantes.TIPO_ASEGURADORA_EPS_REGIMEN_SUBSIDIADO });
						// parametros.put("tercerizada",
						// chkSubcontratacion.isChecked() ? "S" : "N");
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getServicio(AdministradoraService.class).listar(parametros); 
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
							bandbox.setValue(registro.getCodigo());
							textboxInformacion.setValue(registro.getNit() + " "
									+ registro.getNombre());
							return  true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}
	
	private void parametrizarAportante() {
		tbxNro_identificacion_aportante.inicializar(tbxNombreAportante, tbxAportanteBorrar);
		
		BandboxRegistrosIMG<Aportantes_ma> bandboxRegistrosIMG = new BandboxRegistrosIMG<Aportantes_ma>() {
			
			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Aportantes_ma registro) {
				bandbox.setValue(registro.getNro_identificacon());
				textboxInformacion.setValue(registro.getNombre()); 
				return true;
			}
			
			@Override
			public void renderListitem(Listitem listitem, Aportantes_ma registro) {
				Listcell listcell = new Listcell();
				String addDV = registro.getDv() != null ? (!registro.getDv().isEmpty() ? "-"
						+ registro.getDv()
						: "")
						: "";
				
				listcell.appendChild(new Label(registro.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNro_identificacon()
						+ addDV));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre() + ""));
				listitem.appendChild(listcell);
			}
			
			@Override
			public List<Aportantes_ma> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("codigo_empresa", empresa.getCodigo_empresa());
				parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				parameters.put("paramTodo", "");
				parameters.put("value", valorBusqueda.toUpperCase().trim());

				parameters.put("limite_paginado", "limit 25 offset 0");

				return getServiceLocator()
						.getAportantesMaService().listar(parameters);
			}
			
			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				
			}
		};
		tbxNro_identificacion_aportante.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}
	

	protected void seleccionarCotizante(Afiliaciones_me afiliaciones_me) {
		Paciente paciente = afiliaciones_me.getPaciente();
		tbxNombreCotizante.setValue("" + paciente.getNombre1() + " "  + paciente.getNombre2());
		tbxApellidosCotizante.setValue("" + paciente.getApellido1()  + " " + paciente.getApellido2());
		Utilidades.setValueFrom(lbxTipo_id_cotizante, paciente.getTipo_identificacion()); 
	}
}
