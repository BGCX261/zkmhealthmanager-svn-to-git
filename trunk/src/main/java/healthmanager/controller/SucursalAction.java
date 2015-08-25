/*
 * sucursalAction.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Configuracion_servicios_autorizacion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.Configuracion_servicios_autorizacionService;
import healthmanager.modelo.service.ElementoService;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
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
import healthmanager.modelo.service.GeneralExtraService;

public class SucursalAction extends ZKWindow {

    public static final String EPS = "01";
    public static final String IPS = "02";

    private GeneralExtraService generalExtraService;
    private AdministradoraService administradoraService;
    private Configuracion_servicios_autorizacionService configuracion_servicios_autorizacionService;

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

    @View
    private Textbox tbxCodigo_sucursal;
    @View
    private Textbox tbxNombre_sucursal;
    @View
    private Textbox tbxDireccion_sucursal;
    @View
    private Textbox tbxTelefono_sucursal;
    @View
    private Listbox lbxTipo;
    @View
    private Textbox tbxEmail;

    @View
    private Row rowAseguradora;
    @View
    private Row rowConfiguracion;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_administradora;
    @View
    private Toolbarbutton btnLimpiarAseguradora;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxConfiguracionOdontologia;
    @View
    private Toolbarbutton btnLimpiarConfiguracion;

    private final String[] idsExcluyentes = {};

    @Override
    public void init() {
        listarCombos();
        verificarTipoSucursal();
        parametrizarBandbox();
    }

    private void parametrizarBandbox() {
        parametrizarBandboxConfiguracion();
        parametrizarBandboxAdministradora();
    }

    /**
     * Esto es para la caja de prevision
     *
     * @author Luis Miguel
     *
     */
    private void verificarTipoSucursal() {
        if (getSucursal().getTipo().equals(IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
            rowAseguradora.setVisible(true);
            rowConfiguracion.setVisible(true);
        }
    }

    public void listarCombos() {
        listarParameter();
        Utilidades.listarElementoListbox(lbxTipo, true, getServiceLocator());
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("codigo_sucursal");
        listitem.setLabel("código");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("nombre_sucursal");
        listitem.setLabel("Nombre");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("direccion_sucursal");
        listitem.setLabel("Direccion sucursal");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("tipo");
        listitem.setLabel("Tipo");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    private void parametrizarBandboxConfiguracion() {
        tbxConfiguracionOdontologia.inicializar(null, btnLimpiarConfiguracion);
        tbxConfiguracionOdontologia.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Configuracion_servicios_autorizacion>() {

            @Override
            public void renderListitem(Listitem listitem,
                    Configuracion_servicios_autorizacion registro) {
                listitem.appendChild(new Listcell(registro.getNombre()));
            }

            @Override
            public List<Configuracion_servicios_autorizacion> listarRegistros(
                    String valorBusqueda, Map<String, Object> parametros) {
                parametros.put("codigo_empresa",
                        getEmpresa().getCodigo_empresa());
                parametros.put("codigo_sucursal",
                        getSucursal().getCodigo_sucursal());
                parametros.put("paramTodo", "paramTodo");
                parametros.put("value", valorBusqueda);
                return getServiceLocator().getServicio(Configuracion_servicios_autorizacionService.class)
                        .listar(parametros);
            }

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion,
                    Configuracion_servicios_autorizacion registro) {
                bandbox.setValue(registro.getNombre());
                return true;
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {

            }
        });
    }

    private void parametrizarBandboxAdministradora() {
        tbxCodigo_administradora.inicializar(null,
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
                                        getEmpresa().getCodigo_empresa());
                                parametros.put("codigo_sucursal",
                                        getSucursal().getCodigo_sucursal());
                                parametros.put("paramTodo", "paramTodo");
                                parametros.put("value", valorBusqueda);
                                return getServiceLocator().getAdministradoraService()
                                .listar(parametros);
                            }

                            @Override
                            public boolean seleccionarRegistro(Bandbox bandbox,
                                    Textbox textboxInformacion, Administradora registro) {
                                bandbox.setValue(registro.getCodigo() + " " + registro.getNombre());
                                return true;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {

                            }
                });
    }

    //Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        groupboxConsulta.setVisible(!sw);
        groupboxEditar.setVisible(sw);

        if (!accion.equalsIgnoreCase("registrar")) {
            buscarDatos();
        } else {
            //buscarDatos();
            limpiarDatos();
        }
        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        tbxCodigo_sucursal.setDisabled(false);
    }

    //Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {
        try {
            FormularioUtil.validarCamposObligatorios(tbxNombre_sucursal, tbxCodigo_sucursal, lbxTipo);
        } catch (WrongValueException e) {
            return false;
        }
        boolean valida = true;
        String msj = "Los campos marcados con (*) son obligatorios";
        if (!tbxEmail.getText().trim().isEmpty() && !tbxEmail.getText().trim().matches("[a-zA-Z_.0-9]*[@][a-zA-Z0-9]*[.][a-zA-Z.0-9]*[a-zA-Z0-9]$")) {
            msj = "Email no valido por favor verifique";
            valida = false;
        }
        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres() + " recuerde que...", msj + "");
        }
        return valida;
    }

    //Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String parameter = lbxParameter.getSelectedItem().getValue().toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");

            List<Sucursal> lista_datos = generalExtraService.listar(Sucursal.class, parameters);
            rowsResultado.getChildren().clear();

            for (Sucursal sucursal : lista_datos) {
                rowsResultado.appendChild(crearFilas(sucursal, this));
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

        final Sucursal sucursal = (Sucursal) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        Elemento elemento = new Elemento();
        elemento.setTipo(lbxTipo.getName());
        elemento.setCodigo(sucursal.getTipo());
        elemento = getServiceLocator().getServicio(ElementoService.class).consultar(elemento);
        String tipo = elemento != null ? elemento.getDescripcion() : "";

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(sucursal.getCodigo_sucursal() + ""));
        fila.appendChild(new Label(sucursal.getNombre_sucursal() + ""));
        fila.appendChild(new Label(sucursal.getDireccion_sucursal() + ""));
        fila.appendChild(new Label(tipo + ""));
        fila.appendChild(new Label(sucursal.getEmail() + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(sucursal);
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
                Messagebox.show("Esta seguro que desea eliminar este registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener<Event>() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    eliminarDatos(sucursal);
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

    //Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
                //Cargamos los componentes //

                final Sucursal sucursal = new Sucursal();
                sucursal.setCodigo_empresa(empresa.getCodigo_empresa());
                sucursal.setCodigo_sucursal(tbxCodigo_sucursal.getValue());
                sucursal.setNombre_sucursal(tbxNombre_sucursal.getValue());
                sucursal.setDireccion_sucursal(tbxDireccion_sucursal.getValue());
                sucursal.setTelefono_sucursal(tbxTelefono_sucursal.getValue());
                sucursal.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
                sucursal.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
                sucursal.setCreacion_user(usuarios.getCodigo().toString());
                sucursal.setUltimo_user(usuarios.getCodigo().toString());
                sucursal.setTipo(lbxTipo.getSelectedItem().getValue().toString());
                sucursal.setEmail(tbxEmail.getValue());

                Administradora administradora = tbxCodigo_administradora.getRegistroSeleccionado();
                sucursal.setCodigo_administradora_relacion(administradora != null ? administradora.getCodigo() : "");

                Configuracion_servicios_autorizacion configuracion_servicios_autorizacion = tbxConfiguracionOdontologia.getRegistroSeleccionado();
                sucursal.setId_configuracion_orden_odontologia(configuracion_servicios_autorizacion != null ? configuracion_servicios_autorizacion
                        .getId() : null);

                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    generalExtraService.crear(sucursal);
                    accionForm(true, "registrar");
                } else {
                    int result = generalExtraService.actualizar(sucursal);
                    if (result == 0) {
                        throw new Exception("Lo sentimos pero los datos a modificar no se encuentran en base de datos");
                    }
                }
                final boolean recargarSucursal = this.codigo_sucursal.equals(sucursal.getCodigo_sucursal());
                Messagebox.show("Los datos se guardaron satisfactoriamente"
                        + (recargarSucursal ? ".\n se actualizara para que tome los cambios" : ""),
                        "Informacion ..", Messagebox.OK,
                        Messagebox.INFORMATION,
                        new EventListener<Event>() {
                            @Override
                            public void onEvent(Event event)
                            throws Exception {
                                if (recargarSucursal) {
                                    HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
                                    .getNativeRequest();
                                    request.getSession().setAttribute("sucursal", sucursal);
                                    _recargar();
                                }
                            }
                        });
            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    //Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Sucursal sucursal = (Sucursal) obj;
        try {
            tbxCodigo_sucursal.setValue(sucursal.getCodigo_sucursal());
            tbxNombre_sucursal.setValue(sucursal.getNombre_sucursal());
            tbxDireccion_sucursal.setValue(sucursal.getDireccion_sucursal());
            tbxTelefono_sucursal.setValue(sucursal.getTelefono_sucursal());
            Utilidades.setValueFrom(lbxTipo, sucursal.getTipo());
            tbxEmail.setValue(sucursal.getEmail());
            tbxCodigo_sucursal.setDisabled(true);

            Administradora administradora = new Administradora();
            administradora.setCodigo_empresa(sucursal.getCodigo_empresa());
            administradora.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            administradora.setCodigo(sucursal.getCodigo_administradora_relacion());
            administradora = administradoraService.consultar(administradora);
            if (administradora != null) {
                tbxCodigo_administradora.seleccionarRegistro(
                        administradora,
                        administradora.getCodigo() + " "
                        + administradora.getNombre(), "");
            }

            if (sucursal.getId_configuracion_orden_odontologia() != null) {
                Configuracion_servicios_autorizacion configuracion_servicios_autorizacion = new Configuracion_servicios_autorizacion();
                configuracion_servicios_autorizacion.setId(sucursal.getId_configuracion_orden_odontologia());
                configuracion_servicios_autorizacion = configuracion_servicios_autorizacionService
                        .consultar(configuracion_servicios_autorizacion);
                tbxConfiguracionOdontologia.seleccionarRegistro(
                        configuracion_servicios_autorizacion,
                        configuracion_servicios_autorizacion.getNombre(), "");
            }

            //Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Sucursal sucursal = (Sucursal) obj;
        try {
            int result = generalExtraService.eliminar(sucursal);
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
}
