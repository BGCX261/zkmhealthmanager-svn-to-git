/*
 * copago_estratoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.controller.CargarMultipleAdministradoraAction.ICargarMultipleAdministradoraEvent;
import healthmanager.controller.MetasPypAction.EventoMetasPyp;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Contratos_paquetes;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paquetes_servicios;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Contratos_paquetesService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Via_ingreso_contratadasService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.macros.manuales_tarifarios.ManualTarifarioAdministradorMacro;
import com.framework.macros.manuales_tarifarios.ManualTarifarioAdministradorMacro.IBrindaServiciosPyp;
import com.framework.macros.manuales_tarifarios.interfaces.IManualesAction;
import com.framework.macros.manuales_tarifarios.validaciones.Validacion;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.Res;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Grupo_cuenta;
import contaweb.modelo.bean.Puc;
import contaweb.modelo.bean.Tipo_cuenta;

public class ContratosAction extends ZKWindow implements IManualesAction,
		ISeleccionarComponente {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5165632202654564123L;

	private static Logger log = Logger.getLogger(ContratosAction.class);

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
	private Textbox tbxId_plan;
	@View
	private Checkbox chbCerrado;
	@View
	private Textbox tbxNombre;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_administradora;
	@View
	private Textbox tbxInfoAseguradora;
	@View
	private Toolbarbutton btnLimpiarAseguradora;
	@View
	private Listbox lbxTipo_usuario;
	// @View private Checkbox chbPyp;
	@View
	private Textbox tbxNro_contrato;
	// @View private Checkbox chbNocopago;
	@View
	private Listbox lbxTipo_facturacion;
	@View
	private Textbox tbxObservacion;
	@View
	private Datebox dtbxFecha_inicio;
	@View
	private Datebox dtbxFecha_fin;

	@View
	private Toolbarbutton btCopiaContrato;
	@View
	private Toolbarbutton btGuardarMultiple;

	@View
	Intbox ibxCantidadUsuarios;
	@View
	Doublebox dbxValorMensual;
	@View
	Doublebox dbxValorTotal;

	@View
	Listbox lbxModoFacturacion;

	// UPC
	@View
	Doublebox dbxUpc_mes;
	@View
	Doublebox dbxUpc_dia;

	@View
	private Textbox tbxCups_oxigeno;

	@View
	private Doublebox dbxValor_oxigeno;

	@View
	private Row rowValor_oxigeno;

	private healthmanager.modelo.service.ElementoService elementoService;

	/* Macro de manuales tarifarios */
	@View
	private ManualTarifarioAdministradorMacro manualTarifarioAdministradorMacro;

	private final String[] idsExcluyentes = { "dbxValorMensual",
			"dtbxFecha_fin", "dtbxFecha_inicio", "tbxCups_oxigeno",
			"btAlimentar_otros" };
	protected List<Map<String, Object>> lista_metas_pyp;
	// private Plantilla_pypService plantilla_pypService;
	private EventoMetasPyp eventoMetasPyp;

	private List<String> seleccionados_paquetes = new ArrayList<String>();

	@View
	private Listbox listboxPaquetes_servicios;

	@View
	private Checkbox chkIncluir_paquetes;

	@View
	private Checkbox chkCobrar_copago;

	@View
	private Checkbox chkSubcontratacion;
	@View
	private Listbox lbxNivel;

	@View
	private Label lbCodigo_administradora;

	private Contratos_seleccionadosAction contratos_seleccionadosAction;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCuenta_ingreso;
	@View
	private Textbox tbxNomCuenta_ingreso;
	@View
	private Toolbarbutton btnLimpiarCuenta_ingreso;
	@View
	private Checkbox chkAutorizacion_obligatoria;
	@View
	private Toolbarbutton btnInfo_vias;
	@View
	private Listbox lbxVias_ingreso;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() throws Exception {
		listarCombos();
		FormularioUtil.inicializarRadiogroupsDefecto(groupboxEditar);
		manualTarifarioAdministradorMacro
				.setComponentReferencia(groupboxEditar);
		manualTarifarioAdministradorMacro._inicializar(this,
				new IBrindaServiciosPyp() {
					@Override
					public void tieneServiciosPyp(boolean b) {
						// log.info("Contiene servicios de PYP." + b);
					}
				});
		cargarEventosFechas();
		validarCampoCapitados();
		// accionForm(true,"registrar");
	}

	/**
	 * Este metodo me permite cargar el evento cuando cambie la accion
	 * 
	 * @author Luis Miguel
	 * */
	private void cargarEventosFechas() {

	}

	public void listarCombos() {
		cargarVias(lbxVias_ingreso);
		listarParameter();
		Utilidades.listarElementoListbox(lbxTipo_usuario, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_facturacion, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxModoFacturacion, true,
				getServiceLocator());
		parametrizarBandbox();
		parametrizarResultadoPaginado();
		parametrizarCuenta();
		if (sucursal.getTipo().equals(IConstantes.TIPOS_SUCURSAL_IPS)) {
			String nivel = empresa.getNivel();
			int nivel_complejidad = 1;
			if (nivel != null && !nivel.isEmpty() && nivel.matches("[0-9]*$")) {
				nivel_complejidad = Integer.parseInt(nivel);
			}
			// log.info("Nivel complejidad: " + nivel_complejidad);
			Utilidades.listarNivelEmpresa(lbxNivel, true, nivel_complejidad);
			autoseleccionarNiveles();
		} else {
			Utilidades.listarNivelEmpresa(lbxNivel, true);
		}
	}

	private void parametrizarCuenta() {
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
				ContratosAction.this.getServiceLocator().getPucService()
						.setLimit("limit 25 offset 0");
				return ContratosAction.this.getServiceLocator().getPucService()
						.listar(parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}
		};
		/* inyectamos el mismo evento */
		tbxCuenta_ingreso.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxCuenta_ingreso.inicializar(tbxNomCuenta_ingreso,
				btnLimpiarCuenta_ingreso);
	}

	private void autoseleccionarNiveles() {
		if (lbxNivel.getItemCount() == 2) {
			lbxNivel.setSelectedIndex(1);
		}
	}

	private void parametrizarBandbox() {
		tbxCodigo_administradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		tbxCodigo_administradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("tercerizada",
								chkSubcontratacion.isChecked() ? "S" : "N");
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getCodigo());
						bandbox.setAttribute("admin", registro);
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Contratos> listarResultados(
					Map<String, Object> parametros) {
				List<Contratos> listado = getServiceLocator()
						.getContratosService().listar(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getContratosService()
						.totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilas(dato, gridResultado);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado, 9);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("contratos.nombre");
		listitem.setLabel("Nombre del contrato");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("contratos.nro_contrato");
		listitem.setLabel("Nro contrato");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("contratos.codigo_administradora");
		listitem.setLabel("Codigo Administradora");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("ad.nombre");
		listitem.setLabel("Nombre Administradora");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("contratos.id_plan");
		listitem.setLabel("ID Contrato");
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
			tbxCodigo_administradora.setDisabled(true);
			chkSubcontratacion.setDisabled(true);
		} else {
			// buscarDatos();
			limpiarDatos();
			tbxCodigo_administradora.setDisabled(false);
			chkSubcontratacion.setDisabled(false);
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		FormularioUtil.cargarRadiogroupsDefecto(groupboxEditar);
		// chbNocopago.setChecked(true);
		dtbxFecha_inicio.setValue(null);
		dtbxFecha_fin.setValue(null);
		chbCerrado.setDisabled(true);
		// lbxAnio.setDisabled(true);
		manualTarifarioAdministradorMacro.resetear();
		// manualTarifarioAdministradorMacro.setPyp(chbPyp.isChecked());
		lista_metas_pyp = new ArrayList<Map<String, Object>>();
		dbxValorMensual.setValue(0);
		dbxValorTotal.setValue(0);
		ibxCantidadUsuarios.setValue(0);
		validarCampoCapitados();
		setVisibleAcciones(false);
		tbxCodigo_administradora.removeAttribute("admin");
		listboxPaquetes_servicios.getItems().clear();
		chkSubcontratacion.setChecked(sucursal.getTipo().equals(
				IConstantes.TIPOS_SUCURSAL_CAJA_PREV));
		cambiarNombreAseguradora();
		autoseleccionarNiveles();
		if (contratos_seleccionadosAction != null)
			contratos_seleccionadosAction.limpiarDatos();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm(boolean multiple) throws Exception {
		tbxCodigo_administradora
				.setStyle("text-transform:uppercase;background-color:white");
		tbxId_plan.setStyle("text-transform:uppercase;background-color:white");
		tbxNombre.setStyle("text-transform:uppercase;background-color:white");
		dtbxFecha_inicio
				.setStyle("text-transform:uppercase;background-color:white");
		dtbxFecha_fin
				.setStyle("text-transform:uppercase;background-color:white");
		dbxValorTotal
				.setStyle("text-transform:uppercase;background-color:white;text-align:right");
		ibxCantidadUsuarios
				.setStyle("text-transform:uppercase;background-color:white");
		lbxModoFacturacion
				.setStyle("text-transform:uppercase;background-color:white");
		lbxNivel.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";
		boolean isCapitado = lbxTipo_facturacion.getSelectedItem().getValue()
				.toString().equals("01");

		if (tbxCodigo_administradora.getValue().equals("") && !multiple) {
			tbxCodigo_administradora
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			Clients.scrollIntoView(tbxCodigo_administradora);
		}

		if (dtbxFecha_fin.getValue() == null && valida) {
			valida = false;
			dtbxFecha_fin
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_fin);
		}

		if (lbxNivel.getSelectedIndex() == 0) {
			if (valida)
				Clients.scrollIntoView(lbxNivel);
			valida = false;
			lbxNivel.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		}

		if (dbxValorTotal.getValue() == null && isCapitado) {
			valida = false;
			dbxValorTotal
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_fin);
		} else if (dbxValorTotal.getValue() <= 0 && isCapitado) {
			valida = false;
			mensaje = "El valor total del contrato no puede ser igual a cero";
			dbxValorTotal
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			Clients.scrollIntoView(dbxValorTotal);
		}

		if (ibxCantidadUsuarios.getValue() == null && isCapitado) {
			valida = false;
			ibxCantidadUsuarios
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_fin);
		} else if (ibxCantidadUsuarios.getValue() != null
				&& ibxCantidadUsuarios.getValue() <= 0 && isCapitado) {
			valida = false;
			ibxCantidadUsuarios
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			Clients.scrollIntoView(ibxCantidadUsuarios);
		}

		if (dtbxFecha_inicio.getValue() == null) {
			valida = false;
			dtbxFecha_inicio
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_inicio);
		}

		if (valida
				&& dtbxFecha_inicio.getValue().compareTo(
						dtbxFecha_fin.getValue()) > 0) {
			mensaje = "Rangos de fechas no validos";
			valida = false;
			dtbxFecha_inicio
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			Clients.scrollIntoView(dtbxFecha_inicio);
		}

		if (tbxNombre.getText().trim().equals("")) {
			tbxNombre
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida
				&& lbxTipo_facturacion.getSelectedItem().getValue()
						.equals("01")
				&& lbxModoFacturacion.getSelectedIndex() == 0) {
			mensaje = "Para realizar esta opcion debe seleccionar un modo para calcular la factura capitada.";
			valida = false;
			lbxModoFacturacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			Clients.scrollIntoView(lbxModoFacturacion);
		}

		if (valida) {
			if (dbxValor_oxigeno.getValue() != null) {
				if (dbxValor_oxigeno.getValue() <= 0) {
					mensaje = "El valor del oxigeno por litros tiene que ser mayor que cero";
					valida = false;
					dtbxFecha_inicio
							.setStyle("text-transform:uppercase;background-color:#F6BBBE");
					Clients.scrollIntoView(dbxValor_oxigeno);
				}
			} else {
				mensaje = "El valor del oxigeno por litros tiene que ser mayor que cero";
				valida = false;
				dtbxFecha_inicio
						.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				Clients.scrollIntoView(dbxValor_oxigeno);
			}
		}

		if (manualTarifarioAdministradorMacro.getManuales_tarifarios()
				.isEmpty() && valida) {
			valida = false;
			mensaje = "Para realizar esta accion debe agregar por lo menos un manual tarifario";
		}

		if (manualTarifarioAdministradorMacro.getManuales_tarifarios().size() > 1
				&& lbxTipo_facturacion.getSelectedItem().getValue().toString()
						.equals("01") && valida) {
			valida = false;
			mensaje = "Para registrar este contrato como capitado, solo debe tener una manual tarifario asignado.";
		}

		/* con esta accion valida la clasificacion de manuales tarifarios */
		if (valida) {
			Validacion validacion = manualTarifarioAdministradorMacro
					.validarForm();
			if (!validacion.isValida()) {
				valida = false;
				mensaje = validacion.getMsj();
			}
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
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

			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Contratos contratos = (Contratos) objeto;

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(contratos.getCodigo_empresa());
		administradora.setCodigo_sucursal(contratos.getCodigo_sucursal());
		administradora.setCodigo(contratos.getCodigo_administradora());
		administradora = getServiceLocator().getAdministradoraService()
				.consultar(administradora);

		Elemento elemento = new Elemento();
		elemento.setTipo("tipo_contrato");
		elemento.setCodigo(contratos.getTipo_facturacion());

		elemento = elementoService.consultar(elemento);

		StringBuilder manuales = new StringBuilder("");

		int c = 0;
		for (Manuales_tarifarios manuales_tarifarios : contratos
				.getManuales_tarifarios()) {
			manuales.append(""
					+ manuales_tarifarios.getMaestro_manual().getManual()
					+ ""
					+ (manuales_tarifarios.getMaestro_manual().getTipo_manual()
							.equals(IConstantes.TIPO_MANUAL_SOAT) ? manuales_tarifarios
							.getAnio() : ""));
			manuales.append(" - Tarifa especial: "
					+ (manuales_tarifarios.getTarifa_especial().equals("S") ? "SI"
							: "NO"));
			manuales.append(((++c) < contratos.getManuales_tarifarios().size()) ? ", "
					: "");
		}

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setTooltiptext(manuales.toString());

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(contratos.getId_plan()));
		fila.appendChild(new Label(contratos.getCodigo_administradora()));
		fila.appendChild(new Label((administradora != null ? administradora
				.getNombre() : "")));
		fila.appendChild(new Label(contratos.getNro_contrato()));
		fila.appendChild(new Label(contratos.getNombre()));
		fila.appendChild(new Label(Res.recortarCadena(manuales.toString(), 10)));
		fila.appendChild(new Label(elemento != null ? elemento.getDescripcion()
				: ""));
		fila.appendChild(new Label(contratos.getCerrado() ? "SI" : "NO"));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(contratos);
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
							@Override
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									eliminarDatos(contratos);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(new Space());
		hbox.appendChild(img);

		// cerrar contrato
		img = new Image();
		img.setSrc("/images/"
				+ (contratos.getCerrado() ? "open.png" : "lock.gif"));
		img.setTooltiptext((!contratos.getCerrado() ? "Cerrar" : "Abrir ")
				+ " contrato");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show("Esta seguro que desea cerrar este contrato? ",
						"Cerrar contrato", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									cerrarContrato(contratos,
											!contratos.getCerrado());
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

	public void calcularValorMes() {
		Double valor_total = dbxValorTotal.getValue();
		Date fecha_inicial = dtbxFecha_inicio.getValue();
		Date fecha_final = dtbxFecha_fin.getValue();

		if (valor_total != null && fecha_inicial != null && fecha_final != null) {

			int diferencia = Util.getDiferenciaEntreMeses(fecha_inicial,
					fecha_final);

			double valor_mensual = valor_total / diferencia;

			dbxValorMensual.setValue(valor_mensual);
			dbxValorMensual.invalidate();
		} else {
			dbxValorMensual.setValue(0);
			dbxValorMensual.invalidate();
		}
		calcularValorUPC();
	}

	public void calcularValorUPC() {
		Double valor_mes = dbxValorMensual.getValue();
		Integer cantidad_pacientes = ibxCantidadUsuarios.getValue();

		if (valor_mes != null && cantidad_pacientes != null) {
			double valor_ups = valor_mes / cantidad_pacientes;
			double valor_dia = valor_ups / 30;
			dbxUpc_dia.setValue(valor_dia);
			dbxUpc_mes.setValue(valor_ups);
		} else {
			dbxUpc_dia.setValue(0);
			dbxUpc_mes.setValue(0);
		}
	}

	/**
	 * Este metodo sirve para cerrar los contratos
	 * 
	 * @author Luis Miguel
	 * */
	private void cerrarContrato(Contratos contratos, boolean cerrar)
			throws Exception {
		contratos.setCerrado(cerrar);
		getServiceLocator().getContratosService().actualizar(contratos);
		buscarDatos();
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm(false)) {
				// Cargamos los componentes //
				Messagebox.show(
						"Esta seguro que desea guardar este contrato? ",
						"Guardar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									Contratos contratos = new Contratos();
									contratos.setCodigo_empresa(empresa
											.getCodigo_empresa());
									contratos.setCodigo_sucursal(sucursal
											.getCodigo_sucursal());
									contratos
											.setCodigo_administradora(tbxCodigo_administradora
													.getValue());
									contratos.setId_plan(tbxId_plan.getValue());
									contratos.setNombre(tbxNombre.getValue());
									contratos.setCodificacion("CUPS");
									contratos.setNro_contrato(tbxNro_contrato
											.getValue());
									contratos.setMonto_contrato(0.00);
									// contratos.setNocopago(chbNocopago.isChecked()
									// ? "S" : "N");
									contratos
											.setTipo_facturacion(lbxTipo_facturacion
													.getSelectedItem()
													.getValue().toString());
									contratos.setValor_total(0.00);
									contratos.setObservacion(tbxObservacion
											.getValue());
									contratos.setCreacion_date(new Timestamp(
											new GregorianCalendar()
													.getTimeInMillis()));
									contratos.setUltimo_update(new Timestamp(
											new GregorianCalendar()
													.getTimeInMillis()));
									contratos.setCreacion_user(usuarios
											.getCodigo().toString());
									contratos.setUltimo_user(usuarios
											.getCodigo().toString());
									contratos.setCerrado(chbCerrado.isChecked());
									contratos.setTipo_usuario(lbxTipo_usuario
											.getSelectedItem().getValue()
											.toString());
									contratos.setActualizar_metas_mensaul("S");
									contratos
											.setCantidad_usuarios(ibxCantidadUsuarios
													.getValue() != null ? ibxCantidadUsuarios
													.getValue() : 0);
									contratos.setValor_mes(dbxValorMensual
											.getValue() != null ? dbxValorMensual
											.getValue() : 0);
									contratos.setValor_total(dbxValorTotal
											.getValue() != null ? dbxValorTotal
											.getValue() : 0);
									contratos
											.setModo_facturacion(lbxModoFacturacion
													.getSelectedItem()
													.getValue().toString());
									// contratos.setPyp(chbPyp.isChecked() ? "S"
									// : "N");
									contratos
											.setUpc_mes((dbxUpc_mes.getValue() != null ? dbxUpc_mes
													.getValue() : 0.00));
									contratos
											.setUpc_dia((dbxUpc_dia.getValue() != null ? dbxUpc_dia
													.getValue() : 0.00));
									contratos.setNivel(lbxNivel
											.getSelectedItem().getValue()
											.toString());
									if (dtbxFecha_inicio.getValue() != null) {
										contratos
												.setFecha_inicio(new Timestamp(
														dtbxFecha_inicio
																.getValue()
																.getTime()));
									}
									if (dtbxFecha_fin.getValue() != null) {
										contratos.setFecha_fin(new Timestamp(
												dtbxFecha_fin.getValue()
														.getTime()));
									}

									contratos.setCups_oxigeno(tbxCups_oxigeno
											.getValue());
									contratos.setValor_oxigeno(dbxValor_oxigeno
											.getValue());
									contratos
											.setIncluir_paquetes(chkIncluir_paquetes
													.isChecked() ? "S" : "N");
									contratos.setCobrar_copago(chkCobrar_copago
											.isChecked() ? "S" : "N");
									contratos
											.setSubcontratacion(chkSubcontratacion
													.isChecked() ? "S" : "N");
									contratos
											.setAutorizacion_obligatoria(chkAutorizacion_obligatoria
													.isChecked());
									if (contratos.getAutorizacion_obligatoria()) {
										List<Listitem> listado = lbxVias_ingreso
												.getItems();
										StringBuilder stringBuilder = new StringBuilder();
										for (Listitem listitem : listado) {

											stringBuilder
													.append("(")
													.append(listitem.getValue())
													.append(")");
										}
										contratos
												.setVias_ingreso_obligatorias(stringBuilder
														.toString());
									} else {
										contratos
												.setVias_ingreso_obligatorias("");
									}

									Puc puc = tbxCuenta_ingreso
											.getRegistroSeleccionado();
									if (puc != null) {
										contratos.setCuenta_ingreso(puc
												.getCodigo_cuenta());
									} else {
										contratos.setCuenta_ingreso("");
									}

									Map<String, Object> map = new HashMap<String, Object>();
									map.put("contratos", contratos);
									map.put("manuales_tarifarios",
											manualTarifarioAdministradorMacro
													.getManuales_tarifarios());
									// map.put("metas_pyp", lista_metas_pyp);
									map.put("accion", tbxAccion.getText());
									//

									List<Paquetes_servicios> listado_paquetes = new ArrayList<Paquetes_servicios>();

									for (int i = 0; i < listboxPaquetes_servicios
											.getItemCount(); i++) {
										Listitem listitem = listboxPaquetes_servicios
												.getItemAtIndex(i);
										listado_paquetes
												.add((Paquetes_servicios) listitem
														.getValue());
									}

									map.put("listado_paquetes",
											listado_paquetes);

									getServiceLocator().getContratosService()
											.guardarContrato(map);
									if (tbxAccion.getText().equalsIgnoreCase(
											"registrar")) {
										accionForm(true, "registrar");
									}

									MensajesUtil
											.mensajeInformacion(
													"Informacion ..",
													"Los datos se guardaron satisfactoriamente");
									setVisibleAcciones(true);
								}
							}
						});
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// Metodo para guardar la informacion //
	public void guardarDatosServicios() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm(false)) {
				// Cargamos los componentes //

				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(empresa.getCodigo_empresa());
				contratos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				contratos.setCodigo_administradora(tbxCodigo_administradora
						.getValue());
				contratos.setId_plan(tbxId_plan.getValue());
				contratos.setNombre(tbxNombre.getValue());
				contratos.setCodificacion("CUPS");
				contratos.setNro_contrato(tbxNro_contrato.getValue());
				contratos.setMonto_contrato(0.00);
				// contratos.setNocopago(chbNocopago.isChecked()
				// ? "S" : "N");
				contratos.setTipo_facturacion(lbxTipo_facturacion
						.getSelectedItem().getValue().toString());
				contratos.setValor_total(0.00);
				contratos.setObservacion(tbxObservacion.getValue());
				contratos.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				contratos.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				contratos.setCreacion_user(usuarios.getCodigo().toString());
				contratos.setUltimo_user(usuarios.getCodigo().toString());
				contratos.setCerrado(chbCerrado.isChecked());
				contratos.setTipo_usuario(lbxTipo_usuario.getSelectedItem()
						.getValue().toString());
				contratos.setActualizar_metas_mensaul("S");
				contratos
						.setCantidad_usuarios(ibxCantidadUsuarios.getValue() != null ? ibxCantidadUsuarios
								.getValue() : 0);
				contratos
						.setValor_mes(dbxValorMensual.getValue() != null ? dbxValorMensual
								.getValue() : 0);
				contratos
						.setValor_total(dbxValorTotal.getValue() != null ? dbxValorTotal
								.getValue() : 0);
				contratos.setModo_facturacion(lbxModoFacturacion
						.getSelectedItem().getValue().toString());
				// contratos.setPyp(chbPyp.isChecked() ? "S"
				// : "N");
				contratos
						.setUpc_mes((dbxUpc_mes.getValue() != null ? dbxUpc_mes
								.getValue() : 0.00));
				contratos
						.setUpc_dia((dbxUpc_dia.getValue() != null ? dbxUpc_dia
								.getValue() : 0.00));
				contratos.setNivel(lbxNivel.getSelectedItem().getValue()
						.toString());
				if (dtbxFecha_inicio.getValue() != null) {
					contratos.setFecha_inicio(new Timestamp(dtbxFecha_inicio
							.getValue().getTime()));
				}
				if (dtbxFecha_fin.getValue() != null) {
					contratos.setFecha_fin(new Timestamp(dtbxFecha_fin
							.getValue().getTime()));
				}

				contratos.setCups_oxigeno(tbxCups_oxigeno.getValue());
				contratos.setValor_oxigeno(dbxValor_oxigeno.getValue());
				contratos
						.setIncluir_paquetes(chkIncluir_paquetes.isChecked() ? "S"
								: "N");
				contratos.setCobrar_copago(chkCobrar_copago.isChecked() ? "S"
						: "N");
				contratos
						.setSubcontratacion(chkSubcontratacion.isChecked() ? "S"
								: "N");

				Puc puc = tbxCuenta_ingreso.getRegistroSeleccionado();
				if (puc != null) {
					contratos.setCuenta_ingreso(puc.getCodigo_cuenta());
				} else {
					contratos.setCuenta_ingreso("");
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("contratos", contratos);
				map.put("manuales_tarifarios",
						manualTarifarioAdministradorMacro
								.getManuales_tarifarios());
				// map.put("metas_pyp", lista_metas_pyp);
				map.put("accion", tbxAccion.getText());
				//

				List<Paquetes_servicios> listado_paquetes = new ArrayList<Paquetes_servicios>();

				for (int i = 0; i < listboxPaquetes_servicios.getItemCount(); i++) {
					Listitem listitem = listboxPaquetes_servicios
							.getItemAtIndex(i);
					listado_paquetes.add((Paquetes_servicios) listitem
							.getValue());
				}

				map.put("listado_paquetes", listado_paquetes);

				getServiceLocator().getContratosService().guardarContrato(map);
				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					accionForm(true, "registrar");
				}

				setVisibleAcciones(true);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void guardarMultiple() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm(true)) {
				// Cargamos los componentes //
				Contratos contratos = new Contratos();
				contratos.setCodigo_empresa(empresa.getCodigo_empresa());
				contratos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				contratos.setCodigo_administradora(tbxCodigo_administradora
						.getValue());
				contratos.setId_plan(tbxId_plan.getValue());
				contratos.setNombre(tbxNombre.getValue());
				contratos.setCodificacion("CUPS");
				contratos.setNro_contrato(tbxNro_contrato.getValue());
				contratos.setMonto_contrato(0.00);
				// contratos.setNocopago(chbNocopago.isChecked() ? "S" : "N");
				contratos.setTipo_facturacion(lbxTipo_facturacion
						.getSelectedItem().getValue().toString());
				contratos.setValor_total(0.00);
				contratos.setObservacion(tbxObservacion.getValue());
				contratos.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				contratos.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				contratos.setCreacion_user(usuarios.getCodigo().toString());
				contratos.setUltimo_user(usuarios.getCodigo().toString());
				contratos.setCerrado(chbCerrado.isChecked());
				contratos.setTipo_usuario(lbxTipo_usuario.getSelectedItem()
						.getValue().toString());
				contratos.setActualizar_metas_mensaul("S");
				contratos.setCantidad_usuarios(ibxCantidadUsuarios.getValue());
				contratos.setValor_mes(dbxValorMensual.getValue());
				contratos.setValor_total(dbxValorTotal.getValue());
				contratos.setModo_facturacion(lbxModoFacturacion
						.getSelectedItem().getValue().toString());
				// contratos.setPyp(chbPyp.isChecked() ? "S" : "N");
				contratos
						.setUpc_mes((dbxUpc_mes.getValue() != null ? dbxUpc_mes
								.getValue() : 0.00));
				contratos
						.setUpc_dia((dbxUpc_dia.getValue() != null ? dbxUpc_dia
								.getValue() : 0.00));

				contratos.setCups_oxigeno(tbxCups_oxigeno.getValue());
				contratos.setValor_oxigeno(dbxValor_oxigeno.getValue());

				contratos
						.setIncluir_paquetes(chkIncluir_paquetes.isChecked() ? "S"
								: "N");
				contratos.setCobrar_copago(chkCobrar_copago.isChecked() ? "S"
						: "N");
				contratos
						.setSubcontratacion(chkSubcontratacion.isChecked() ? "S"
								: "N");
				contratos.setNivel(lbxNivel.getSelectedItem().getValue()
						.toString());

				if (dtbxFecha_inicio.getValue() != null) {
					contratos.setFecha_inicio(new Timestamp(dtbxFecha_inicio
							.getValue().getTime()));
				}
				if (dtbxFecha_fin.getValue() != null) {
					contratos.setFecha_fin(new Timestamp(dtbxFecha_fin
							.getValue().getTime()));
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put(CargarMultipleAdministradoraAction.CONTRATO, contratos);
				map.put(CargarMultipleAdministradoraAction.ADMINISTRADORA,
						tbxCodigo_administradora.getAttribute("admin"));
				map.put(CargarMultipleAdministradoraAction.MANUALES_TARIFARIOS,
						manualTarifarioAdministradorMacro
								.getManuales_tarifarios());

				final List<Paquetes_servicios> listado_paquetes = new ArrayList<Paquetes_servicios>();

				for (int i = 0; i < listboxPaquetes_servicios.getItemCount(); i++) {
					Listitem listitem = listboxPaquetes_servicios
							.getItemAtIndex(i);
					listado_paquetes.add((Paquetes_servicios) listitem
							.getValue());
				}

				map.put("listado_paquetes", listado_paquetes);

				// map.put("metas_pyp", lista_metas_pyp);
				// map.put("accion", tbxAccion.getText());
				//
				// setVisibleAcciones(true);

				final CargarMultipleAdministradoraAction cargarMultipleAdministradoraAction = (CargarMultipleAdministradoraAction) Executions
						.createComponents(
								"/pages/cargar_multiple_administradora.zul",
								ContratosAction.this, map);
				cargarMultipleAdministradoraAction.setWidth("100%");
				cargarMultipleAdministradoraAction.setHeight("100%");
				// este es el evento y las administradoras seleccionadas
				cargarMultipleAdministradoraAction
						.setCargarMultipleAdministradoraEvent(new ICargarMultipleAdministradoraEvent() {
							@Override
							public void guardarAdministradoras(
									List<Map<String, Object>> administradoras,
									Contratos contratos,
									List<Manuales_tarifarios> manuales_tarifarios) {
								try {
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("administradoras", administradoras);
									map.put("contratos", contratos);
									map.put("manuales_tarifarios",
											manuales_tarifarios);
									map.put("accion", "registrar");
									map.put("listado_paquetes",
											listado_paquetes);
									getServiceLocator().getServicio(
											ContratosService.class)
											.guardarContratoMultiple(map);

									if (tbxAccion.getText().equalsIgnoreCase(
											"registrar")) {
										accionForm(true, "registrar");
									}

									MensajesUtil
											.mensajeInformacion(
													"Informacion ..",
													"Los datos se guardaron satisfactoriamente");
									setVisibleAcciones(true);
									cargarMultipleAdministradoraAction.detach();
								} catch (Exception e) {
									MensajesUtil.mensajeError(e, "",
											ContratosAction.this);
								}
							}
						});
				cargarMultipleAdministradoraAction.doModal();
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void setVisibleAcciones(boolean visible) {
		btGuardarMultiple.setVisible(!visible);
		btCopiaContrato.setVisible(visible);
	}

	/**
	 * Este metodo me permite crear un copia del contrato.
	 * 
	 * @author Luis Miguel Hernández Pérez
	 * */
	public void crearCopiaDelContrato() {
		dtbxFecha_inicio.setValue(null);
		dtbxFecha_fin.setValue(null);
		tbxNro_contrato.setValue(null);
		tbxId_plan.setValue(null);
		tbxNombre.setValue(null);

		// cambiamos el estado para que la aplicacion sepa
		// un nuevo registro...
		tbxAccion.setValue("registrar");
		btCopiaContrato.setVisible(false);
		tbxCodigo_administradora.setDisabled(false);
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Contratos contratos = (Contratos) obj;
		try {
			setVisibleAcciones(true);
			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(contratos.getCodigo_empresa());
			administradora.setCodigo_sucursal(contratos.getCodigo_sucursal());
			administradora.setCodigo(contratos.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			tbxId_plan.setValue(contratos.getId_plan());
			tbxNombre.setValue(contratos.getNombre());

			tbxCodigo_administradora.setValue(contratos
					.getCodigo_administradora());
			tbxInfoAseguradora.setValue(administradora != null ? administradora
					.getNombre() : "");

			Utilidades.setValueFrom(lbxTipo_facturacion,
					contratos.getTipo_facturacion());
			Utilidades.setValueFrom(lbxTipo_usuario,
					contratos.getTipo_usuario());

			Utilidades.setValueFrom(lbxModoFacturacion,
					contratos.getModo_facturacion());
			Utilidades.setValueFrom(lbxNivel, contratos.getNivel());

			tbxNro_contrato.setValue(contratos.getNro_contrato());
			// chbNocopago.setChecked(contratos.getNocopago().equals("S"));

			tbxObservacion.setValue(contratos.getObservacion());
			chbCerrado.setChecked(contratos.getCerrado());
			// chbPyp.setChecked(contratos.getPyp().equals("S"));
			dtbxFecha_inicio.setValue(contratos.getFecha_inicio());
			dtbxFecha_fin.setValue(contratos.getFecha_fin());
			dbxValorMensual.setValue(contratos.getValor_mes());
			dbxValorTotal.setValue(contratos.getValor_total());
			ibxCantidadUsuarios.setValue(contratos.getCantidad_usuarios());
			dbxUpc_mes.setValue(contratos.getUpc_mes());
			dbxUpc_dia.setValue(contratos.getUpc_dia());

			tbxCups_oxigeno.setValue(contratos.getCups_oxigeno());
			dbxValor_oxigeno.setValue(contratos.getValor_oxigeno());

			chbCerrado.setDisabled(false);
			chkIncluir_paquetes.setChecked(contratos.getIncluir_paquetes()
					.equals("S"));
			chkCobrar_copago.setChecked(contratos.getCobrar_copago()
					.equals("S"));
			chkSubcontratacion.setChecked(contratos.getSubcontratacion()
					.equals("S"));
			chkAutorizacion_obligatoria.setChecked(contratos
					.getAutorizacion_obligatoria());
			onCheckAutorizaciones(false);
			if (!contratos.getVias_ingreso_obligatorias().isEmpty()) {
				StringTokenizer stringTokenizer = new StringTokenizer(
						contratos.getVias_ingreso_obligatorias(), "|");
				while (stringTokenizer.hasMoreTokens()) {
					String via = stringTokenizer.nextToken();
					Utilidades.seleccionarListitem(lbxVias_ingreso, via);
				}
			}

			manualTarifarioAdministradorMacro._cargarContrato(contratos);
			validarCampoCapitados();

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", contratos.getCodigo_empresa());
			parametros.put("codigo_sucursal", contratos.getCodigo_sucursal());
			parametros.put("id_plan", contratos.getId_plan());
			parametros.put("codigo_administradora",
					contratos.getCodigo_administradora());

			List<Contratos_paquetes> listado_paquetes = getServiceLocator()
					.getServicio(Contratos_paquetesService.class).listar(
							parametros);

			listboxPaquetes_servicios.getItems().clear();

			for (Contratos_paquetes contratos_paquetes : listado_paquetes) {
				final Paquetes_servicios paquetes_servicios = contratos_paquetes
						.getPaquetes_servicios();

				Procedimientos procedimientos = new Procedimientos();
				procedimientos.setId_procedimiento(new Long(paquetes_servicios
						.getId_procedimiento_principal()));
				procedimientos = getServiceLocator().getProcedimientosService()
						.consultar(procedimientos);

				Elemento elemento = new Elemento();
				elemento.setCodigo(paquetes_servicios.getVia_ingreso());
				elemento.setTipo("via_ingreso");
				elemento = getServiceLocator().getServicio(
						ElementoService.class).consultar(elemento);

				final Listitem listitem = new Listitem();

				listitem.setValue(paquetes_servicios);

				listitem.appendChild(Utilidades.obtenerListcell(
						paquetes_servicios.getId() + "", Textbox.class, true,
						true));
				listitem.appendChild(Utilidades.obtenerListcell(
						procedimientos != null ? procedimientos
								.getCodigo_cups() : "", Textbox.class, true,
						true));

				String nombre_paquete = "";

				Procedimientos procedimiento = new Procedimientos();
				procedimiento.setId_procedimiento(new Long(paquetes_servicios
						.getId_procedimiento_principal()));
				procedimiento = getServiceLocator().getProcedimientosService()
						.consultar(procedimiento);
				if (procedimiento != null) {
					nombre_paquete = procedimiento.getDescripcion();
				}

				listitem.appendChild(Utilidades.obtenerListcell(nombre_paquete,
						Textbox.class, true, true));

				listitem.appendChild(Utilidades.obtenerListcell(
						elemento != null ? (elemento.getDescripcion())
								: paquetes_servicios.getVia_ingreso(),
						Textbox.class, true, true));

				listitem.appendChild(Utilidades.obtenerListcell(
						paquetes_servicios.getValor(), Doublebox.class, true,
						true));

				final Image image = new Image("/images/borrar.gif");
				image.setStyle("cursor:pointer");
				image.setTooltiptext("eliminar este item");
				image.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {
								Messagebox
										.show("Esta seguro que desea eliminar este registro? ",
												"Eliminar Registro",
												Messagebox.YES + Messagebox.NO,
												Messagebox.QUESTION,
												new org.zkoss.zk.ui.event.EventListener<Event>() {
													@Override
													public void onEvent(
															Event event)
															throws Exception {
														if ("onYes".equals(event
																.getName())) {
															seleccionados_paquetes.remove(paquetes_servicios
																	.getId()
																	+ "");
															listitem.detach();
														}
													}
												});
							}

						});

				Listcell celda = new Listcell();
				celda.appendChild(image);
				listitem.appendChild(celda);

				seleccionados_paquetes.add(paquetes_servicios.getId() + "");

				listboxPaquetes_servicios.appendChild(listitem);

			}

			if (contratos.getCuenta_ingreso() != null
					&& !contratos.getCuenta_ingreso().isEmpty()) {
				Puc puc = new Puc();
				puc.setCodigo_empresa(codigo_empresa);
				puc.setCodigo_sucursal(codigo_sucursal);
				puc.setCodigo_cuenta(contratos.getCuenta_ingreso());
				puc = getServiceLocator().getPucService().consultar(puc);
				tbxCuenta_ingreso.seleccionarRegistro(puc,
						puc != null ? puc.getCodigo_cuenta() : "",
						puc != null ? puc.getNombre() : "");
			} else {
				tbxCuenta_ingreso.seleccionarRegistro(null, "", "");
			}

			// cargarMetasPyp(contratos);

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
			// log.info("ultimo");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Contratos contratos = (Contratos) obj;
		try {
			int result = getServiceLocator().getContratosService().eliminar(
					contratos);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeAlerta(
							"ADVERNTENCIA",
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos");
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		} catch (Exception r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	/**
	 * Este metodo te permite cargar la vista a la cual ingresas las metas de
	 * PYP.
	 * 
	 * @author Luis Miguel
	 * */
	public void cargarVistaMetasPyp() {
		// if(chbPyp.isChecked()){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(MetasPypAction.MODO, MetasPypAction.MODO_REGISTRO);
		param.put(MetasPypAction.REGIMEN_CONTRATO, lbxTipo_usuario
				.getSelectedItem().getValue().toString());
		param.put(MetasPypAction.METAS_PYP, lista_metas_pyp);
		param.put(MetasPypAction.ID_CONTRATO, tbxId_plan.getValue());
		param.put(MetasPypAction.CODIGO_ADMINISTRADORA,
				tbxCodigo_administradora.getValue());
		MetasPypAction metasPypAction = (MetasPypAction) Executions
				.createComponents(MetasPypAction.PAGINA_METAS, this, param);

		if (eventoMetasPyp == null)
			eventoMetasPyp = new EventoMetasPyp() {
				@Override
				public void onRegistar(List<Map<String, Object>> list_metas) {
					lista_metas_pyp = list_metas;
				}

				@Override
				public void onCerrar() {
					// log.info("Se cerro ventana PYP");
				}

				@Override
				public void onCancelar() {
					// chbPyp.setChecked(false);
				}

			};
		/* inyectamos evento */
		metasPypAction.setEventoMetasPyp(eventoMetasPyp);
		// }
	}

	@Override
	public String getCodigoAdministradora() {
		return tbxCodigo_administradora.getValue().toString();
	}

	@Override
	public String getIdContrato() {
		return tbxId_plan.getValue();
	}

	@Override
	public String getTipoFactura() {
		return lbxTipo_facturacion.getSelectedItem().getValue().toString();
	}

	public void validarPortabilidad(Listbox listbox) {
		if (listbox.getSelectedItem().getValue().toString().equals("04")) {
			Notificaciones
					.mostrarNotificacionAlerta(
							"Advertencia",
							"Este contrato que ha seleccionado es de tipo PORTABILIDAD",
							IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		}

	}

	public void validarCampoCapitados() {
		boolean isCapitado = lbxTipo_facturacion.getSelectedItem().getValue()
				.toString().equals("01");
		dbxValorTotal.setVisible(isCapitado);
		getFellow("lbValorTotal").setVisible(isCapitado);
		getFellow("rowCantidades").setVisible(isCapitado);
		getFellow("footAccion").setVisible(isCapitado);
		getFellow("rowUpc").setVisible(isCapitado);
		if (!isCapitado) {
			lbxModoFacturacion.setSelectedIndex(0);
		} else {
			dbxValorTotal.focus();
		}
	}

	public void agregarDetallePaquetes() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("seleccionados", seleccionados_paquetes);

		Component componente = Executions.createComponents(
				"/pages/openPaquetesServicios.zul", null, parametros);
		final OpenPaquetesServiciosAction ventana = (OpenPaquetesServiciosAction) componente;
		ventana.setSeleccionar_componente(this);
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR PAQUETES DE SERVICIOS");
		ventana.setMode("modal");
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		final Paquetes_servicios paquetes_servicios = (Paquetes_servicios) pcd
				.get("paquetes_servicios");

		Elemento elemento = new Elemento();
		elemento.setTipo("via_ingreso");
		elemento.setCodigo(paquetes_servicios.getVia_ingreso());

		elemento = elementoService.consultar(elemento);

		final Listitem listitem = new Listitem();

		listitem.setValue(paquetes_servicios);

		listitem.appendChild(Utilidades.obtenerListcell(
				paquetes_servicios.getId() + "", Textbox.class, true, true));
		listitem.appendChild(Utilidades.obtenerListcell(
				paquetes_servicios.getCodigo_cups(), Textbox.class, true, true));

		listitem.appendChild(Utilidades.obtenerListcell(
				paquetes_servicios.getNombre_paquete(), Textbox.class, true,
				true));

		listitem.appendChild(Utilidades.obtenerListcell(
				elemento != null ? elemento.getDescripcion()
						: paquetes_servicios.getVia_ingreso(), Textbox.class,
				true, true));

		listitem.appendChild(Utilidades.obtenerListcell(
				paquetes_servicios.getValor(), Doublebox.class, true, true));

		final Image image = new Image("/images/borrar.gif");
		image.setStyle("cursor:pointer");
		image.setTooltiptext("eliminar este item");
		image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							@Override
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									seleccionados_paquetes
											.remove(paquetes_servicios.getId()
													+ "");
									listitem.detach();
								}
							}
						});
			}

		});

		Listcell celda = new Listcell();
		celda.appendChild(image);
		listitem.appendChild(celda);

		seleccionados_paquetes.add(paquetes_servicios.getId() + "");

		listboxPaquetes_servicios.appendChild(listitem);
	}

	public void onCheckSubcontratacion() {
		tbxCodigo_administradora.limpiarSeleccion(true);
		cambiarNombreAseguradora();
	}

	public void cambiarNombreAseguradora() {
		lbCodigo_administradora
				.setValue(chkSubcontratacion.isChecked() ? "Prestador: "
						: "Aseguradora:");
	}

	@Override
	public String getNivel() {
		return lbxNivel.getSelectedItem().getValue().toString();
	}

	@Override
	public ZKWindow getZkWindow() {
		return this;
	}

	public void onAlimentarOtrosContratos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		if (contratos_seleccionadosAction == null) {
			contratos_seleccionadosAction = (Contratos_seleccionadosAction) Executions
					.createComponents("/pages/contratos_seleccionados.zul",
							this, parametros);
			contratos_seleccionadosAction
					.setEventListener(new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							guardarAlimentacionContratos();
						}
					});
		}
		contratos_seleccionadosAction.setPage(getPage());
		contratos_seleccionadosAction.setVisible(true);
		contratos_seleccionadosAction.setWidth("80%");
		contratos_seleccionadosAction.setHeight("80%");
		contratos_seleccionadosAction.setClosable(true);
		contratos_seleccionadosAction
				.setTitle("Seleccionar contratos para alimentar");
		contratos_seleccionadosAction.doModal();
	}

	public void guardarAlimentacionContratos() throws Exception {
		if (validarForm(false)) {
			if (contratos_seleccionadosAction != null) {
				List<Contratos> listado_contratos = contratos_seleccionadosAction
						.obtenerListadoContratos();
				if (!listado_contratos.isEmpty()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("listado_contratos", listado_contratos);
					map.put("manuales_tarifarios",
							manualTarifarioAdministradorMacro
									.getManuales_tarifarios());

					List<Paquetes_servicios> listado_paquetes = new ArrayList<Paquetes_servicios>();

					for (int i = 0; i < listboxPaquetes_servicios
							.getItemCount(); i++) {
						Listitem listitem = listboxPaquetes_servicios
								.getItemAtIndex(i);
						listado_paquetes.add((Paquetes_servicios) listitem
								.getValue());
					}

					map.put("listado_paquetes", listado_paquetes);

					getServiceLocator().getContratosService()
							.guardarAlimentarContrato(map);

					MensajesUtil
							.mensajeInformacion("Informacion",
									"Alimentacion de contratos hecha satisfactiamente...");

				} else {
					MensajesUtil
							.mensajeAlerta("Seleccionar contratos",
									"No se puede han seleccionados los contratos que se van a alimentar");
				}
			} else {
				MensajesUtil
						.mensajeAlerta("Seleccionar contratos",
								"No se puede han seleccionados los contratos que se van a alimentar");
			}
		}
	}

	public void actualizarServicios() throws Exception {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_dane", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		List<Contratos> listado = getServiceLocator().getContratosService()
				.listar(parametros);

		List<Via_ingreso_contratadas> listado_vias = getServiceLocator()
				.getServicio(Via_ingreso_contratadasService.class).listar(
						parametros);

		for (int i = 0; i < listado.size(); i++) {
			Contratos contratos = listado.get(i);
			parametros.put("codigo_administradora",
					contratos.getCodigo_administradora());
			parametros.put("id_plan", contratos.getId_plan());

			if (!getServiceLocator().getServicio(
					Via_ingreso_contratadasService.class).existe(parametros)) {
				for (Via_ingreso_contratadas via_ingreso_contratadas : listado_vias) {
					via_ingreso_contratadas.setId_plan(contratos.getId_plan());
					via_ingreso_contratadas.setCodigo_administradora(contratos
							.getCodigo_administradora());
					Via_ingreso_contratadas via_aux = getServiceLocator()
							.getServicio(Via_ingreso_contratadasService.class)
							.consultar_informacion(via_ingreso_contratadas);
					if (via_aux != null) {
						via_ingreso_contratadas.setId_via(via_aux.getId_via());
						getServiceLocator().getServicio(
								Via_ingreso_contratadasService.class)
								.actualizar(via_ingreso_contratadas);
					} else {
						getServiceLocator().getServicio(
								Via_ingreso_contratadasService.class).crear(
								via_ingreso_contratadas);
					}

				}
			}
			log.info("Procesado " + (i + 1) + " de " + listado.size());
		}
	}

	public void verificarRepetidos() {
		List<Map<String, Object>> listado = getServiceLocator().getServicio(
				Via_ingreso_contratadasService.class).verificarDuplicados();
		for (Map<String, Object> datos : listado) {
			Long total = (Long) datos.get("total");
			if (total > 1) {
				List<Via_ingreso_contratadas> listado_aux = getServiceLocator()
						.getServicio(Via_ingreso_contratadasService.class)
						.listar(datos);
				for (int i = 1; i < listado_aux.size(); i++) {
					getServiceLocator().getServicio(
							Via_ingreso_contratadasService.class).eliminar(
							listado_aux.get(i));
				}
			}
		}
		log.info("terminado de verificar informacion");
	}

	public void onCheckAutorizaciones(boolean todos) {
		if (chkAutorizacion_obligatoria.isChecked()) {
			btnInfo_vias.setVisible(true);
			for (Listitem listitem : lbxVias_ingreso.getItems()) {
				listitem.setSelected(todos);
			}
		} else {
			btnInfo_vias.setVisible(false);
		}
	}

	private void cargarVias(Listbox listbox) {
		Listitem listitem;
		String tipo = listbox.getName();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo);
		parametros.put("orden_descripcion", "orden_descripcion");

		List<Elemento> elementos = elementoService.listar(parametros);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
			listitem.setSelected(true);
		}
	}

}