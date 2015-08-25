/*
 * certificado_incapacidad_cajaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Certificado_incapacidad_caja;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.Certificado_incapacidad_cajaService;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Certificado_incapacidad_cajaAction extends ZKWindow {

    private Certificado_incapacidad_cajaService certificado_incapacidad_cajaService;
    private Admision admision;

    //Componentes //
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
    private BandboxRegistrosMacro tbxNro_identificacion;
    @View
    private Datebox dtbxFecha;
    @View
    private Textbox tbxCodigo;
    @View
    private Datebox dtbxFecha_hasta;
    @View
    private Textbox tbxObservaciones;
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_dx;

    @View
    private Datebox dtbxFecha_inicial;
    @View
    private Datebox dtbxFecha_final;
    @View
    private Toolbarbutton btnLimpiarPaciente;
    @View
    private Textbox tbxNomPaciente;

    @View
    private Toolbarbutton btGuardar;
    @View
    private Toolbarbutton btImprimir;
    @View
    private Toolbarbutton btnLimpiarDx;
    @View
    private Textbox tbxNomDx;

    private final String[] idsExcluyentes = {"dtbxFecha_ingreso"};

    private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

    private Certificado_incapacidad_caja _certificado_incapacidad_caja;

    @Override
    public void init() {
        listarCombos();
        parametrizar();
    }

    @Override
    public void params(Map<String, Object> map) {
        setAdmision((Admision) map.get(IVias_ingreso.ADMISION_PACIENTE));
    }

    @Override
    public void _despuesIniciar() {
        validarEstadoCertificado();
    }

    private void validarEstadoCertificado() {
        try {
            if (getAdmision() != null) {
                Map<String, Object> map_consultarCerficidado = new HashMap<String, Object>();
                map_consultarCerficidado.put("codigo_empresa", getAdmision().getCodigo_empresa());
                map_consultarCerficidado.put("codigo_sucursal", getAdmision().getCodigo_sucursal());
                map_consultarCerficidado.put("nro_identificacion", getAdmision().getNro_identificacion());
                map_consultarCerficidado.put("nro_ingreso", getAdmision().getNro_ingreso());
                List<Certificado_incapacidad_caja> listado_sertificado = certificado_incapacidad_cajaService
                        .listar(map_consultarCerficidado);
                if (listado_sertificado.isEmpty()) {
                    accionForm(true, "registrar", true);
                    tbxNro_identificacion.seleccionarRegistro(getAdmision()
                            .getPaciente(), getAdmision().getPaciente()
                            .getDocumento(), getAdmision().getPaciente()
                            .getNombreCompleto());
                    btnLimpiarPaciente.setVisible(false);
                    if (getAdmision().getDiagnostico_ingreso() != null
                            && !getAdmision().getDiagnostico_ingreso().trim()
                            .isEmpty()) {
                        Cie cie = new Cie();
                        cie.setCodigo(getAdmision().getDiagnostico_ingreso());
                        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
                        if (cie != null) {
                            tbxCodigo_dx.seleccionarRegistro(cie, cie.getCodigo(), cie.getNombre());
                        }
                    }
                } else {
                    limpiarDatos();
                    FormularioUtil.deshabilitarComponentes(groupboxEditar, true);
                    btGuardar.setVisible(false);
                    mostrarDatos(listado_sertificado.get(0));
                    btnLimpiarPaciente.setVisible(false);
                    btnLimpiarDx.setVisible(false);
                }
            }
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, null, this);
        }
    }

    private void parametrizar() {
        parametrizarResultadoPaginado();
        parametrizarBandboxPaciente();
        parametrizarBanboxCie();
    }

    private void parametrizarBandboxPaciente() {
        tbxNro_identificacion.inicializar(tbxNomPaciente, btnLimpiarPaciente);
        tbxNro_identificacion.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {
            @Override
            public void renderListitem(Listitem listitem,
                    Paciente registro) {
                listitem.setValue(registro);

                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(registro
                        .getTipo_identificacion() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro
                        .getNro_identificacion() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNombre1()
                        + " " + registro.getNombre2()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getApellido1()
                        + " " + registro.getApellido2()));
                listitem.appendChild(listcell);
            }

            @Override
            public List<Paciente> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros) {

                parametros.put("codigo_empresa",
                        getEmpresa().getCodigo_empresa());
                parametros.put("codigo_sucursal",
                        getSucursal().getCodigo_sucursal());
                parametros.put("paramTodo", "");
                parametros.put("value", "%"
                        + valorBusqueda.toUpperCase().trim() + "%");

                parametros.put("limite_paginado", "limit 25 offset 0");

                return getServiceLocator().getPacienteService().listar(parametros);
            }

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Paciente registro) {
                bandbox.setValue(registro.getNro_identificacion());
                textboxInformacion.setValue(registro
                        .getNombreCompleto());
                return true;
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
            }
        });
    }

    private void parametrizarBanboxCie() {
        tbxCodigo_dx.inicializar(tbxNomDx, btnLimpiarDx);
        BandboxRegistrosIMG<Cie> bandboxRegistrosIMG = new BandboxRegistrosIMG<Cie>() {

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Cie registro) {
                bandbox.setValue("" + registro.getCodigo());
                textboxInformacion.setValue("" + registro.getNombre());
                return true;
            }

            @Override
            public void renderListitem(Listitem listitem, Cie registro) {
                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(registro.getCodigo() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNombre() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getSexo()));
                listitem.appendChild(listcell);
            }

            @Override
            public List<Cie> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros) {

                Paciente paciente = tbxNro_identificacion.getRegistroSeleccionado();
                parametros.put("paramTodo", "");
                parametros.put("sexo", paciente.getSexo().equals("M") ? "H" : "M");
                parametros.put("value", "%"
                        + valorBusqueda.toUpperCase().trim() + "%");

                parametros.put("limite_paginado", "limit 25 offset 0");

                return getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class, parametros);
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
            }
        };
        tbxCodigo_dx.setBandboxRegistrosIMG(bandboxRegistrosIMG);
    }

    private void parametrizarResultadoPaginado() {
        ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

            @Override
            public List<Certificado_incapacidad_caja> listarResultados(
                    Map<String, Object> parametros) {
                return certificado_incapacidad_cajaService.listar(parametros);
            }

            @Override
            public Integer totalResultados(Map<String, Object> parametros) {
                return certificado_incapacidad_cajaService.totalResultados(parametros);
            }

            @Override
            public XulElement renderizar(Object dato) throws Exception {
                return crearFilas(dato, gridResultado);
            }

        };
        resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
                gridResultado, 10);
    }

    public void listarCombos() {
        listarParameter();
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("vr_cic.nro_identificacion || ' ' || vr_cic.pac_apellido1 || ' ' || vr_cic.pac_apellido2 || ' ' || vr_cic.pac_nombre1 || ' ' || vr_cic.pac_nombre2");
        listitem.setLabel("Paciente");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("vr_cic.codigo");
        listitem.setLabel("Código");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("vr_cic.codigo_medico || ' ' || vr_cic.med_apellidos || ' ' || vr_cic.med_nombres");
        listitem.setLabel("Médico");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("vr_cic.codigo_dx || ' ' || vr_cic.cie_nombre");
        listitem.setLabel("Diagnostico");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("vr_cic.via_ingreso || ' ' || vr_cic.elm_descripcion_via_ingreso");
        listitem.setLabel("Via Ingreso");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    //Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion, boolean actualizar) throws Exception {
        groupboxConsulta.setVisible(!sw);
        groupboxEditar.setVisible(sw);

        if (actualizar) {
            if (!accion.equalsIgnoreCase("registrar")) {
                buscarDatos();
            } else {
                //buscarDatos();
                limpiarDatos();
            }
        }

        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        btImprimir.setVisible(false);
        dtbxFecha.setButtonVisible(false);
        dtbxFecha.setReadonly(true);
        dtbxFecha_hasta.setValue(null);
        Utilidades.validacionFechasPasadas(dtbxFecha_hasta);
        tbxNro_identificacion.setButtonVisible(false);
    }

    //Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {
        boolean valida = true;

        try {
            FormularioUtil.validarCamposObligatorios(tbxObservaciones, tbxCodigo_dx);
        } catch (Exception e) {
            return false;
        }

        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres() + " recuerde que...", "Los campos marcados con (*) son obligatorios");
        }

        return valida;
    }

    //Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String parameter = lbxParameter.getSelectedItem().getValue().toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", getEmpresa().getCodigo_empresa());
            parameters.put("codigo_sucursal", getSucursal().getCodigo_sucursal());
            parameters.put("parameter", parameter);
            parameters.put("value", value);
            parameters.put("fecha_in", dtbxFecha_inicial.getValue());
            parameters.put("fecha_fn", dtbxFecha_final.getValue());
            parameters.put("ordenar_por", "ORDER BY vr_cic.fecha DESC");

            resultadoPaginadoMacro.buscarDatos(parameters);
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public Row crearFilas(Object objeto, Component componente) throws Exception {
        Row fila = new Row();

        final Certificado_incapacidad_caja certificado_incapacidad_caja = (Certificado_incapacidad_caja) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(certificado_incapacidad_caja.getCodigo() + ""));
        fila.appendChild(new Label(formatFecha.format(certificado_incapacidad_caja.getFecha())));
        fila.appendChild(new Label("(" + certificado_incapacidad_caja.getPaciente()
                .getDocumento()
                + ") "
                + certificado_incapacidad_caja.getPaciente()
                .getNombreCompleto()));
        fila.appendChild(new Label(formatFecha.format(certificado_incapacidad_caja.getFecha_hasta())));
        fila.appendChild(new Label("(" + certificado_incapacidad_caja.getCie()
                .getCodigo()
                + ") "
                + certificado_incapacidad_caja.getCie().getNombre()));
        fila.appendChild(new Label(certificado_incapacidad_caja.getMedico()
                .getApellidos()
                + " "
                + certificado_incapacidad_caja.getMedico().getNombres()));
        fila.appendChild(new Label(certificado_incapacidad_caja
                .getElm_via_ingreso().getDescripcion()));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/print_ico.gif");
        img.setTooltiptext("Imprimir");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
//				mostrarDatos(certificado_incapacidad_caja);
                imprimir(certificado_incapacidad_caja);
            }
        });
        hbox.appendChild(img);

//		img = new Image();
//		img.setSrc("/images/borrar.gif");
//		img.setTooltiptext("Eliminar");
//		img.setStyle("cursor: pointer");
//		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				Messagebox.show("Esta seguro que desea eliminar este registro? ",
//					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
//					Messagebox.QUESTION,
//					new org.zkoss.zk.ui.event.EventListener<Event>() {
//						public void onEvent(Event event) throws Exception {
//							if ("onYes".equals(event.getName())) {
//								// do the thing
//								eliminarDatos(certificado_incapacidad_caja);
//								buscarDatos();
//							}
//						}
//					});
//			}
//		});
//		hbox.appendChild(space);
//		hbox.appendChild(img);
        fila.appendChild(hbox);

        return fila;
    }

    protected void imprimir(Certificado_incapacidad_caja cic) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pac_documento", cic.getPaciente().getDocumento());
        map.put("pac_apellido1", cic.getPaciente().getApellido1());
        map.put("pac_apellido2", cic.getPaciente().getApellido2());
        map.put("pac_nombre1", cic.getPaciente().getNombre1());
        map.put("pac_nombre2", cic.getPaciente().getNombre2());
        map.put("fecha", cic.getFecha());
        map.put("fecha_hasta", cic.getFecha_hasta());
        map.put("codigo", cic.getCodigo());
        map.put("observaciones", cic.getObservaciones());
        map.put("codigo_dx", cic.getCodigo_dx());
        map.put("cie_nombre", cic.getCie().getNombre());
        map.put("med_nombres", cic.getMedico().getNombres());
        map.put("med_apellidos", cic.getMedico().getApellidos());
        map.put("med_firma",
                cic.getMedico().getFirma() != null ? new ByteArrayInputStream(
                        cic.getMedico().getFirma()) : null);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map);
        Map paramRequest = new HashMap();
        paramRequest.put("name_report", "CertificadoIncapacidadCaja");
        paramRequest.put("list", list);

        Component componente = Executions.createComponents("/pages/printInformes.zul", this, paramRequest);
        final Window window = (Window) componente;
        window.setMode("modal");
        window.setMaximizable(true);
        window.setMaximized(true);
    }

    public void imprimir() {
        imprimir(get_certificado_incapacidad_caja());
    }

    //Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
				//Cargamos los componentes //

                Certificado_incapacidad_caja certificado_incapacidad_caja = get_certificado_incapacidad_caja();
                if (certificado_incapacidad_caja == null) {
                    certificado_incapacidad_caja = new Certificado_incapacidad_caja();
                }
                certificado_incapacidad_caja.setCodigo_empresa(getEmpresa().getCodigo_empresa());
                certificado_incapacidad_caja.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
                certificado_incapacidad_caja.setNro_identificacion(tbxNro_identificacion.getValue());
                certificado_incapacidad_caja.setFecha(new Timestamp(dtbxFecha.getValue().getTime()));
                certificado_incapacidad_caja.setCodigo(tbxCodigo.getValue());
                certificado_incapacidad_caja.setFecha_hasta(new Timestamp(dtbxFecha_hasta.getValue().getTime()));
                certificado_incapacidad_caja.setObservaciones(tbxObservaciones.getValue());
                certificado_incapacidad_caja.setCodigo_dx(tbxCodigo_dx.getValue());
                certificado_incapacidad_caja.setCodigo_medico(getUsuarios().getCodigo());
                certificado_incapacidad_caja.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
                certificado_incapacidad_caja.setCreacion_user(getUsuarios().getCodigo().toString());
                certificado_incapacidad_caja.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
                certificado_incapacidad_caja.setUltimo_user(getUsuarios().getCodigo().toString());
                certificado_incapacidad_caja.setVia_ingreso(getAdmision().getVia_ingreso());
                certificado_incapacidad_caja.setNro_ingreso(getAdmision().getNro_ingreso());

                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    certificado_incapacidad_cajaService.crear(certificado_incapacidad_caja);
                    tbxAccion.setValue("modificar");
                    tbxCodigo.setValue(certificado_incapacidad_caja.getCodigo());
                    btImprimir.setVisible(true);
                } else {
                    int result = certificado_incapacidad_cajaService.actualizar(certificado_incapacidad_caja);
                    if (result == 0) {
                        throw new ValidacionRunTimeException("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
                    }
                }
                set_certificado_incapacidad_caja(certificado_incapacidad_caja);
                MensajesUtil.mensajeInformacion("Informacion ..", "Los datos se guardaron satisfactoriamente");
            }
        } catch (WrongValueException e) {
            MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    //Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        set_certificado_incapacidad_caja((Certificado_incapacidad_caja) obj);
        try {
            if (get_certificado_incapacidad_caja().getPaciente() != null) {
                tbxNro_identificacion.seleccionarRegistro(
                        get_certificado_incapacidad_caja().getPaciente(),
                        get_certificado_incapacidad_caja().getPaciente()
                        .getDocumento(), get_certificado_incapacidad_caja()
                        .getPaciente().getNombreCompleto());
            } else {
                tbxNro_identificacion.setValue(get_certificado_incapacidad_caja().getNro_identificacion());
            }

            dtbxFecha.setValue(get_certificado_incapacidad_caja().getFecha());
            tbxCodigo.setValue(get_certificado_incapacidad_caja().getCodigo());
            dtbxFecha_hasta.setValue(get_certificado_incapacidad_caja().getFecha_hasta());
            tbxObservaciones.setValue(get_certificado_incapacidad_caja().getObservaciones());

            if (get_certificado_incapacidad_caja().getCie() != null) {
                tbxCodigo_dx.seleccionarRegistro(
                        get_certificado_incapacidad_caja().getCie(),
                        get_certificado_incapacidad_caja().getCie().getCodigo(),
                        get_certificado_incapacidad_caja().getCie().getNombre());
            } else {
                tbxCodigo_dx.setValue(get_certificado_incapacidad_caja().getCodigo_dx());
            }
            btImprimir.setVisible(true);
            //Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText(), false);
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Certificado_incapacidad_caja certificado_incapacidad_caja = (Certificado_incapacidad_caja) obj;
        try {
            int result = certificado_incapacidad_cajaService.eliminar(certificado_incapacidad_caja);
            if (result == 0) {
                throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
            }

            Messagebox.show("Informacion se eliminó satisfactoriamente !!",
                    "Information", Messagebox.OK, Messagebox.INFORMATION);
        } catch (HealthmanagerException e) {
            MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
        } catch (RuntimeException r) {
            MensajesUtil.mensajeError(r, "", this);
        }
    }

    public Admision getAdmision() {
        return admision;
    }

    public void setAdmision(Admision admision) {
        this.admision = admision;
    }

    public Certificado_incapacidad_caja get_certificado_incapacidad_caja() {
        return _certificado_incapacidad_caja;
    }

    public void set_certificado_incapacidad_caja(
            Certificado_incapacidad_caja _certificado_incapacidad_caja) {
        this._certificado_incapacidad_caja = _certificado_incapacidad_caja;
    }
}
