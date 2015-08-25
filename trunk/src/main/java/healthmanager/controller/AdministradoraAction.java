/*
 * administradoraAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;

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
import org.zkoss.zul.Checkbox;
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

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesComponentes;

import contaweb.modelo.bean.Grupo_cuenta;
import contaweb.modelo.bean.Puc;
import contaweb.modelo.bean.Tipo_cuenta;

public class AdministradoraAction extends ZKWindow {
	
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
	private Textbox tbxNit;
	@View
	private Textbox tbxNombre;
	@View
	private Textbox tbxRepresentante;
	@View
	private Listbox lbxCodigo_dpto;
	@View
	private Listbox lbxCodigo_municipio;
	@View
	private Textbox tbxDireccion;
	@View
	private Textbox tbxTelefono;
	@View
	private Textbox tbxObservaciones;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCuenta_cobrar;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCuenta_cobrar2;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCuenta_pagar;
	@View
	private Listbox lbxTipo_aseguradora;
	@View
	private Listbox lbxTipo_contribuyente;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCuenta_retencion;
	@View
	private Listbox lbxFormato_rips;
	@View
	private Listbox lbxFormato_fecha_rips;
	@View
	private Listbox lbxTipo;
	
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCuenta_autorete1;
	@View Textbox tbxNomCuenta_autorete1;
	@View Toolbarbutton btnLimpiarCuenta_autorete1;
	
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCuenta_autorete2;
	@View Textbox tbxNomCuenta_autorete2;
	@View Toolbarbutton btnLimpiarCuenta_autorete2;
	
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCuenta_reteica;
	@View Textbox tbxNomCuenta_reteica;
	@View Toolbarbutton btnLimpiarCuenta_reteica;
	
	@View
	private Checkbox chbAutoretencion;
	
	
	private final String[] idsExcluyentes = { "lbxParameter", "tbxValue",
			"tbxAccion", "rowsResultado" };

	/* Campos bandbox */
	@View
	Textbox tbxNomCuentaCobrar;
	@View
	Textbox tbxNomCuentaCobrar2;
	@View
	Textbox tbxNomCuentaPagar;
	@View
	Textbox tbxNomCuentaRetencion;

	@View
	Toolbarbutton btnLimpiarCuentaCobrar;
	@View
	Toolbarbutton btnLimpiarCuentaCobrar2;
	@View
	Toolbarbutton btnLimpiarCuentaPagar;
	@View
	Toolbarbutton btnLimpiarCuentaRetencion;

	@View
	Toolbarbutton btGuardar;
	
	@View
	private Checkbox chkTercerizada;

	@Override
	public void init() {
		listarCombos();
		tbxCuenta_cobrar
				.inicializar(tbxNomCuentaCobrar, btnLimpiarCuentaCobrar);
		tbxCuenta_cobrar2
		.inicializar(tbxNomCuentaCobrar2, btnLimpiarCuentaCobrar2);
		tbxCuenta_pagar.inicializar(tbxNomCuentaPagar, btnLimpiarCuentaPagar);
		tbxCuenta_retencion.inicializar(tbxNomCuentaRetencion,
				btnLimpiarCuentaRetencion);
		
		tbxCuenta_autorete1.inicializar(tbxNomCuenta_autorete1, btnLimpiarCuenta_autorete1);
		tbxCuenta_autorete2.inicializar(tbxNomCuenta_autorete2, btnLimpiarCuenta_autorete2);
		tbxCuenta_reteica.inicializar(tbxNomCuenta_reteica, btnLimpiarCuenta_reteica);
		parametrizarBandbox();
	}

	private void parametrizarBandbox() {
		inicializarBandboxPuc();
	}

	private void inicializarBandboxPuc() {
		BandboxRegistrosIMG<Puc> bandboxRegistrosIMG = new BandboxRegistrosIMG<Puc>() {

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Puc registro) {
				bandbox.setValue(registro.getCodigo_cuenta());
				textboxInformacion.setValue(registro.getNombre());
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem, Puc registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo_cuenta() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCuenta_padre() + ""));
				listitem.appendChild(listcell);

				Tipo_cuenta tp = new Tipo_cuenta();
				tp.setCodigo_empresa(registro.getCodigo_empresa());
				tp.setCodigo_sucursal(registro.getCodigo_sucursal());
				tp.setCodigo(registro.getTipo_cuenta());
				tp = getServiceLocator().getTipo_cuentaService().consultar(tp);

				listcell = new Listcell();
				listcell.appendChild(new Label(tp != null ? tp.getNombre() : ""));
				listitem.appendChild(listcell);

				Grupo_cuenta gp = new Grupo_cuenta();
				gp.setCodigo_empresa(registro.getCodigo_empresa());
				gp.setCodigo_sucursal(registro.getCodigo_sucursal());
				gp.setCodigo(registro.getGrupo_cuenta());
				gp = getServiceLocator().getGrupo_cuentaService().consultar(gp);
				listcell = new Listcell();
				listcell.appendChild(new Label(gp != null ? gp.getNombre() : ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(
						registro.getOculto().equals("S") ? "SI" : "NO"));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getInactivo().equals(
						"S") ? "SI" : "NO"));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getManeja_terceros()
						.equals("S") ? "SI" : "NO"));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Puc> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("codigo_empresa", empresa.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				AdministradoraAction.this.getServiceLocator().getPucService()
						.setLimit("limit 25 offset 0");
				return AdministradoraAction.this.getServiceLocator()
						.getPucService().listar(parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}
		};
		/* inyectamos el mismo evento */
		tbxCuenta_cobrar.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxCuenta_cobrar2.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxCuenta_pagar.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxCuenta_retencion.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		
		tbxCuenta_autorete1.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxCuenta_autorete2.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxCuenta_reteica.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarDepartamentos(lbxCodigo_dpto, true,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
				getServiceLocator());
		UtilidadesComponentes.listarElementosListbox(false,false,
				getServiceLocator(), lbxTipo_aseguradora, lbxTipo_contribuyente);
		listarTipo();
		cargarFormato();
		listarFormatoRips();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nit");
		listitem.setLabel("Nit");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	private void cargarFormato() {
		String[] formato = { "dd/MM/yyyy", "yyyy/MM/dd" };
		for (String formto : formato) {
			String codigo = formto;
			String descripcion = formto;

			Listitem listitem = new Listitem();
			listitem.setValue("" + codigo);
			listitem.setLabel("" + descripcion);
			lbxFormato_fecha_rips.appendChild(listitem);
		}
		if (lbxFormato_fecha_rips.getItemCount() > 0) {
			lbxFormato_fecha_rips.setSelectedIndex(0);
		}
	}

	private void listarFormatoRips() {
		String[][] tipos = { { "zip", "Zip" }, { "rar", "Rar" } };
		for (String[] tipo : tipos) {
			String codigo = tipo[0];
			String descripcion = tipo[1];

			Listitem listitem = new Listitem();
			listitem.setValue("" + codigo);
			listitem.setLabel("" + descripcion);
			lbxFormato_rips.appendChild(listitem);
		}
		if (lbxFormato_rips.getItemCount() > 0) {
			lbxFormato_rips.setSelectedIndex(0);
		}
	}

	private void listarTipo() {
		String[][] tipos = { { "01", "EPS" }, { "02", "IPS" } };
		for (String[] tipo : tipos) {
			String codigo = tipo[0];
			String descripcion = tipo[1];

			Listitem listitem = new Listitem();
			listitem.setValue("" + codigo);
			listitem.setLabel("" + descripcion);
			lbxTipo.appendChild(listitem);
		}
		if (lbxTipo.getItemCount() > 0) {
			lbxTipo.setSelectedIndex(0);
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

	public void cancelar() throws Exception {
		groupboxConsulta.setVisible(true);
		groupboxEditar.setVisible(false);
		buscarDatos();
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		tbxCodigo.setReadonly(false);
		btGuardar
		.setLabel("Guardar "
				+ (sucursal.getTipo().equals(
						IConstantes.TIPOS_SUCURSAL_IPS) ? "Administradora"
						: "Prestador"));
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
				getServiceLocator());
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxCodigo.setStyle("text-transform:uppercase;background-color:white");
		tbxNit.setStyle("text-transform:uppercase;background-color:white");
		tbxNombre.setStyle("text-transform:uppercase;background-color:white");
		tbxRepresentante
				.setStyle("text-transform:uppercase;background-color:white");
		lbxCodigo_dpto.setStyle("background-color:white");
		lbxCodigo_municipio.setStyle("background-color:white");
		tbxDireccion
				.setStyle("text-transform:uppercase;background-color:white");
		tbxTelefono.setStyle("text-transform:uppercase;background-color:white");
		tbxObservaciones
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCuenta_cobrar
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCuenta_pagar
				.setStyle("text-transform:uppercase;background-color:white");
		lbxTipo_aseguradora
				.setStyle("text-transform:uppercase;background-color:white");
		lbxTipo_contribuyente
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCuenta_retencion
				.setStyle("text-transform:uppercase;background-color:white");
		lbxFormato_rips.setStyle("background-color:white");
		lbxFormato_fecha_rips.setStyle("background-color:white");
		lbxTipo.setStyle("background-color:white");

		boolean valida = true;

		if (tbxCodigo.getText().trim().equals("")) {
			tbxCodigo
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxNit.getText().trim().equals("")) {
			tbxNit.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxNombre.getText().trim().equals("")) {
			tbxNombre
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (lbxCodigo_dpto.getSelectedItem().getValue().toString().trim()
				.equals("")) {
			lbxCodigo_dpto.setStyle("background-color:#F6BBBE");
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getAdministradoraService().setLimit(
					"limit 25 offset 0");

			List<Administradora> lista_datos = getServiceLocator()
					.getAdministradoraService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Administradora administradora : lista_datos) {
				rowsResultado.appendChild(crearFilas(administradora, this));
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

		final Administradora administradora = (Administradora) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(administradora.getCodigo() + ""));
		fila.appendChild(new Label(administradora.getNit() + ""));
		fila.appendChild(new Label(administradora.getNombre() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(administradora);
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
									eliminarDatos(administradora);
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

				Administradora administradora = new Administradora();
				administradora.setCodigo(tbxCodigo.getValue());
				administradora.setNit(tbxNit.getValue());
				administradora.setNombre(tbxNombre.getValue().toUpperCase());
				administradora.setRepresentante(tbxRepresentante.getValue());
				administradora.setCodigo_dpto(lbxCodigo_dpto.getSelectedItem()
						.getValue().toString());
				administradora.setCodigo_municipio(lbxCodigo_municipio
						.getSelectedItem().getValue().toString());
				administradora.setDireccion(tbxDireccion.getValue());
				administradora.setTelefono(tbxTelefono.getValue());
				administradora.setObservaciones(tbxObservaciones.getValue());
				administradora.setTrabaja_articulos("");
				administradora.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				administradora.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				administradora
						.setCreacion_user(usuarios.getCodigo().toString());
				administradora.setUltimo_user(usuarios.getCodigo().toString());
				administradora.setCuenta_cobrar(tbxCuenta_cobrar.getValue());
				administradora.setCuenta_cobrar2(tbxCuenta_cobrar2.getValue());
				administradora.setCuenta_cobrar3("");
				administradora.setCuenta_pagar(tbxCuenta_pagar.getValue());
				administradora.setTipo_aseguradora(lbxTipo_aseguradora
						.getSelectedItem().getValue().toString());
				administradora.setTipo_contribuyente(lbxTipo_contribuyente
						.getSelectedItem().getValue().toString());
				administradora.setCuenta_retencion(tbxCuenta_retencion
						.getValue());
				administradora.setFormato_rips(lbxFormato_rips
						.getSelectedItem().getValue().toString());
				administradora.setFormato_fecha_rips(lbxFormato_fecha_rips
						.getSelectedItem().getValue().toString());
				administradora.setTipo(lbxTipo.getSelectedItem().getValue()
						.toString());
				administradora.setCodigo_empresa(empresa.getCodigo_empresa());
				administradora
						.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				administradora.setTercerizada(chkTercerizada.isChecked() ? "S" : "N");
				
				administradora.setCuenta_autorete1(tbxCuenta_autorete1.getValue());
				administradora.setCuenta_autorete2(tbxCuenta_autorete2.getValue());
				administradora.setAutoretencion(chbAutoretencion.isChecked());
				administradora.setCuenta_reteica(tbxCuenta_reteica.getValue());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getAdministradoraService().crear(
							administradora);
					accionForm(true, "registrar");
				} else {
					int result = getServiceLocator().getAdministradoraService()
							.actualizar(administradora);
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
		Administradora administradora = (Administradora) obj;
		try {
			btGuardar.setLabel("Modificar "
					+ (sucursal.getTipo().equals("1") ? "Administradora"
							: "IPS"));
			tbxCodigo.setReadonly(true);
			tbxCodigo.setValue(administradora.getCodigo());
			tbxNit.setValue(administradora.getNit());
			tbxNombre.setValue(administradora.getNombre());
			tbxRepresentante.setValue(administradora.getRepresentante());
			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(administradora.getCodigo_dpto())) {
					listitem.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}
			Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
					getServiceLocator());
			for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_municipio.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(administradora.getCodigo_municipio())) {
					listitem.setSelected(true);
					i = lbxCodigo_municipio.getItemCount();
				}
			}
			tbxDireccion.setValue(administradora.getDireccion());
			tbxTelefono.setValue(administradora.getTelefono());
			tbxObservaciones.setValue(administradora.getObservaciones());

			Utilidades.setValueFrom(lbxTipo_aseguradora,
					administradora.getTipo_aseguradora());
			Utilidades.setValueFrom(lbxTipo_contribuyente,
					administradora.getTipo_contribuyente());

			/* Cargamos cuentas */
			//log.info("administradora.getCuenta_cobrar(): "+administradora.getCuenta_cobrar());
			if (!administradora.getCuenta_cobrar().isEmpty()) {
				Puc puc = new Puc();
				puc.setCodigo_empresa(codigo_empresa);
				puc.setCodigo_sucursal(codigo_sucursal);
				puc.setCodigo_cuenta(administradora.getCuenta_cobrar());
				puc = getServiceLocator().getPucService().consultar(puc);
				if (puc != null) {
					tbxCuenta_cobrar.seleccionarRegistro(puc,
							puc.getCodigo_cuenta(), puc.getNombre());
				}
			}
			
			if (!administradora.getCuenta_cobrar2().isEmpty()) {
				Puc puc = new Puc();
				puc.setCodigo_empresa(codigo_empresa);
				puc.setCodigo_sucursal(codigo_sucursal);
				puc.setCodigo_cuenta(administradora.getCuenta_cobrar2());
				puc = getServiceLocator().getPucService().consultar(puc);
				if (puc != null) {
					tbxCuenta_cobrar2.seleccionarRegistro(puc,
							puc.getCodigo_cuenta(), puc.getNombre());
				}
			}

			if (!administradora.getCuenta_pagar().isEmpty()) {
				Puc puc = new Puc();
				puc.setCodigo_empresa(codigo_empresa);
				puc.setCodigo_sucursal(codigo_sucursal);
				puc.setCodigo_cuenta(administradora.getCuenta_pagar());
				puc = getServiceLocator().getPucService().consultar(puc);
				if (puc != null) {
					tbxCuenta_pagar.seleccionarRegistro(puc,
							puc.getCodigo_cuenta(), puc.getNombre());
				}
			}

			if (!administradora.getCuenta_retencion().isEmpty()) {
				Puc puc = new Puc();
				puc.setCodigo_empresa(codigo_empresa);
				puc.setCodigo_sucursal(codigo_sucursal);
				puc.setCodigo_cuenta(administradora.getCuenta_retencion());
				puc = getServiceLocator().getPucService().consultar(puc);
				if (puc != null) {
					tbxCuenta_retencion.seleccionarRegistro(puc,
							puc.getCodigo_cuenta(), puc.getNombre());
				}
			}
			chbAutoretencion.setChecked(administradora.getAutoretencion());
			/* fin cargar cuentas */

			Utilidades.setValueFrom(lbxTipo_aseguradora,
					administradora.getTipo_aseguradora());
			Utilidades.setValueFrom(lbxTipo_contribuyente,
					administradora.getTipo_contribuyente());

			for (int i = 0; i < lbxFormato_rips.getItemCount(); i++) {
				Listitem listitem = lbxFormato_rips.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(administradora.getFormato_rips())) {
					listitem.setSelected(true);
					i = lbxFormato_rips.getItemCount();
				}
			}
			for (int i = 0; i < lbxFormato_fecha_rips.getItemCount(); i++) {
				Listitem listitem = lbxFormato_fecha_rips.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(administradora.getFormato_fecha_rips())) {
					listitem.setSelected(true);
					i = lbxFormato_fecha_rips.getItemCount();
				}
			}
			for (int i = 0; i < lbxTipo.getItemCount(); i++) {
				Listitem listitem = lbxTipo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(administradora.getTipo())) {
					listitem.setSelected(true);
					i = lbxTipo.getItemCount();
				}
			}
			
			chkTercerizada.setChecked(administradora.getTercerizada().equals("S"));
			
			if (!administradora.getCuenta_autorete1().isEmpty()) {
				Puc puc = new Puc();
				puc.setCodigo_empresa(codigo_empresa);
				puc.setCodigo_sucursal(codigo_sucursal);
				puc.setCodigo_cuenta(administradora.getCuenta_autorete1());
				puc = getServiceLocator().getPucService().consultar(puc);
				if (puc != null) {
					tbxCuenta_autorete1.seleccionarRegistro(puc,
							puc.getCodigo_cuenta(), puc.getNombre());
				}
			}
			
			if (!administradora.getCuenta_autorete2().isEmpty()) {
				Puc puc = new Puc();
				puc.setCodigo_empresa(codigo_empresa);
				puc.setCodigo_sucursal(codigo_sucursal);
				puc.setCodigo_cuenta(administradora.getCuenta_autorete2());
				puc = getServiceLocator().getPucService().consultar(puc);
				if (puc != null) {
					tbxCuenta_autorete2.seleccionarRegistro(puc,
							puc.getCodigo_cuenta(), puc.getNombre());
				}
			}
			
			if (!administradora.getCuenta_reteica().isEmpty()) {
				Puc puc = new Puc();
				puc.setCodigo_empresa(codigo_empresa);
				puc.setCodigo_sucursal(codigo_sucursal);
				puc.setCodigo_cuenta(administradora.getCuenta_reteica());
				puc = getServiceLocator().getPucService().consultar(puc);
				if (puc != null) {
					tbxCuenta_reteica.seleccionarRegistro(puc,
							administradora.getCuenta_reteica(), puc.getNombre());
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
		Administradora administradora = (Administradora) obj;
		try {
			int result = getServiceLocator().getAdministradoraService()
					.eliminar(administradora);
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