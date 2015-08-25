/*
 * usuariosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Roles_usuarios_capsService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IRoles;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class UsuariosAction extends ZKWindow {

    private static Logger log = Logger.getLogger(UsuariosAction.class);

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
    private Textbox tbxLogin;
    @View
    private Textbox tbxPassword;
    @View
    private Textbox tbxRepetirPassword;
    @View
    private Textbox tbxNombres;
    @View
    private Textbox tbxApellidos;
    @View
    private Listbox lbxNivel;
    @View
    private Checkbox chbActivo;
    // @View private Textbox tbxRol;
    @View
    private Textbox tbxDireccion;
    @View
    private Doublebox dbxTel_oficina;
    @View
    private Doublebox dbxTel_res;
    @View
    private Doublebox dbxCelular;
    @View
    private Textbox tbxRegistro_medico;
    @View
    private Listbox lbxTipo_med;
    @View
    private Listbox lbxCodigo_especialidad;
    @View
    private Listbox lbxTipo_prestador;
    @View
    private Textbox tbxEmail;
    @View
    private Listbox lbxMunicipio;
    @View
    private Listbox lbxDepartamento;

    @View
    private Image imageUsuario;

    @View
    private Listbox listboxRol;
    @View
    private Listbox listboxSucursal;

    @View
    private Button btn_addRol;

    @View
    private Groupbox groupMedico;

    @View
    private Button btGuardar;

    private final String[] idsExcluyentes = {"lbxParameter", "tbxAccion"};

    private boolean existe_rol_medico;

    @View
    private Rows rowsRoles;

    @View
    private Checkbox chkTodos_centros;

    private List<Centro_atencion> listado_centros_atencion;

    private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

    @Override
    public void init() {
        listarCombos();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("codigo_empresa", codigo_empresa);
        parametros.put("codigo_sucursal", codigo_sucursal);
        listado_centros_atencion = getServiceLocator()
                .getCentro_atencionService().listar(parametros);
        parametrizarResultadoPaginado();
    }

    private void parametrizarResultadoPaginado() {
        ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

            @Override
            public List<Usuarios> listarResultados(
                    Map<String, Object> parametros) {
                List<Usuarios> listado = getServiceLocator()
                        .getUsuariosService().listar(parametros);
                // log.info("listado ====> " + listado.size());
                return listado;
            }

            @Override
            public Integer totalResultados(Map<String, Object> parametros) {
                Integer total = getServiceLocator().getUsuariosService()
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
                gridResultado, 8);
    }

    // manuel
    public void listarMunicipios(Listbox lbxMunicipio, Listbox lbxDepartamento) {
        Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
                getServiceLocator());
    }

    public void listarMunicipios2(Listbox lbxMunicipio_lugar_parto,
            Listbox lbxDepartamento_lugar_parto) {
        Utilidades.listarMunicipios(lbxMunicipio_lugar_parto,
                lbxDepartamento_lugar_parto, getServiceLocator());
    }

    public void listarCombos() {
        listarParameter();
        Utilidades.listarDepartamentos(lbxDepartamento, true,
                getServiceLocator());
        Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
                getServiceLocator());
        Utilidades.listarDepartamentos(lbxDepartamento, true,
                getServiceLocator());
        Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
                getServiceLocator());
        listarSucursales();
        Utilidades.listarElementoListboxOrdenado(listboxRol, true,
                getServiceLocator());
        loadEvents();
        validamosRoles();
        listarEspecialidades();
        listarTipos();
        Utilidades.listarElementoListbox(lbxTipo_prestador, true,
                getServiceLocator());
    }

    /**
     * Para cambiar el nombre del usuario
	 *
     */
    public void cambiarLabelUsuario(String nombres, String apellidos) {
        // labelUsuaurio.setValue(nombres.toUpperCase());
    }

    /**
     * Listamos las especialidades cuando es medico
	 *
     */
    private void listarEspecialidades() {
        List<Especialidad> especialidads = getServiceLocator()
                .getEspecialidadService().listar(new HashMap<String, Object>());
        Listitem listitem = new Listitem();
        listitem.setValue("");
        listitem.setLabel("-- seleccione --");
        lbxCodigo_especialidad.appendChild(listitem);
        for (Especialidad especialidad : especialidads) {
            lbxCodigo_especialidad.appendChild(new Listitem(especialidad
                    .getNombre(), especialidad.getCodigo()));
        }
        if (lbxCodigo_especialidad.getItemCount() > 0) {
            lbxCodigo_especialidad.setSelectedIndex(0);
        }
    }

    private void listarTipos() {
        String[][] items = {{"01", "Interno"}, {"02", "Externo"}};
        Listitem listitem = new Listitem();
        listitem.setValue("");
        listitem.setLabel("-- seleccione --");
        lbxTipo_med.appendChild(listitem);
        for (String[] item : items) {
            lbxTipo_med.appendChild(new Listitem(item[1], item[0]));
        }
        if (lbxTipo_med.getItemCount() > 0) {
            lbxTipo_med.setSelectedIndex(0);
        }
    }

    private void validamosRoles() {
        List<Listitem> listitems = listboxRol.getItems();
        Iterator<Listitem> it = listitems.iterator();
        while (it.hasNext()) {
            Listitem itempRol = it.next();
            if (itempRol.getValue().equals(IRoles.AFILIACIONES)
                    && !sucursal.getTipo().equals(SucursalAction.IPS)) {
                // listboxRol.removeChild(itempRol);
                it.remove();
            }
        }
    }

    private void loadEvents() {
        listboxRol.addEventListener("onSelect", new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                Listbox listbox = (Listbox) event.getTarget();
                btn_addRol.setDisabled(listbox.getSelectedIndex() == 0);
            }
        });
    }

    public void listarSucursales() {
        listboxSucursal.getChildren().clear();
        try {
            Map parameter = new HashMap();
            parameter.put("codigo_empresa", usuarios.getCodigo_empresa());

            List<Sucursal> lista_sucursales = getServiceLocator()
                    .getServicio(GeneralExtraService.class).listar(Sucursal.class, parameter);
            // log.info("sucursales: " + lista_sucursales.size());
            for (Sucursal sucursal : lista_sucursales) {
                Listitem listitem = new Listitem();
                listitem.setValue(sucursal.getCodigo_sucursal());
                listitem.setLabel(sucursal.getNombre_sucursal());
                listboxSucursal.appendChild(listitem);
            }

            Listitem listitem = new Listitem();
            listitem.setValue("");
            listitem.setLabel("Todas las sucursales");
            listboxSucursal.appendChild(listitem);
            if (listboxSucursal.getItemCount() > 0) {
                listboxSucursal.setSelectedIndex(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("usuarios.codigo");
        listitem.setLabel("Codigo");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("usuarios.nombres");
        listitem.setLabel("Nombres");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("usuarios.apellidos");
        listitem.setLabel("Apellidos");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("usuarios.login");
        listitem.setLabel("Login");
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
            tbxCodigo.setDisabled(true);
            // tbxLogin.setDisabled(true);
        } else {
            // buscarDatos();
            limpiarDatos();
            tbxCodigo.setDisabled(false);
        }
        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        chbActivo.setChecked(true);
        limpiarRoles();
        cambiarLabelUsuario("", "");
        btGuardar.setLabel("Guardar Usuarios");
        imageUsuario.setSrc("");
        dbxTel_oficina.setValue(null);
        dbxTel_res.setValue(null);
        dbxCelular.setValue(null);
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        Clients.clearWrongValue(lbxTipo_med);
        Clients.clearWrongValue(lbxCodigo_especialidad);

        FormularioUtil.validarCamposObligatorios(tbxCodigo, tbxLogin,
                tbxPassword, tbxRepetirPassword, tbxNombres, tbxApellidos,
                tbxDireccion, dbxCelular, dbxTel_oficina, dbxTel_res,
                dbxCelular, tbxRegistro_medico, lbxTipo_med, lbxDepartamento,
                lbxMunicipio);

        boolean valida = true;
        String mensaje = "Los campos marcados con (*) son obligatorios";

        if (existe_rol_medico && valida) {
            if (lbxTipo_med.getSelectedIndex() == 0) {
                Clients.wrongValue(lbxTipo_med, mensaje);
                valida = false;
            }
            if (lbxCodigo_especialidad.getSelectedIndex() == 0) {
                Clients.wrongValue(lbxCodigo_especialidad, mensaje);
                valida = false;
            }
        }

        if (valida) {
            if (!tbxPassword.getValue().toString()
                    .equals(tbxRepetirPassword.getValue().toString())) {
                mensaje = "La contraseña no coincide, vuelva a digitarla";
                valida = false;
            }
        }

        if (rowsRoles.getChildren().size() == 0 && valida) {
            mensaje = "Para registrar este usuario, debe agregar el rol, "
                    + "(Para agregar un rol, seleccione el rol correspondiente y presiones adicional rol)";
            valida = false;
        } else {
            for (Component component : rowsRoles.getChildren()) {
                Row row_fila = (Row) component;
                Intbox intbox_horas = (Intbox) row_fila.getChildren().get(3)
                        .getFirstChild();
                Roles_usuarios roles_usuarios = (Roles_usuarios) row_fila
                        .getValue();
                roles_usuarios.setHoras_medico(intbox_horas.getValue());
                Listbox listbox_cups = (Listbox) this.getFellow("listbox_cups_"
                        + codigo_empresa + "_" + codigo_sucursal + "_"
                        + roles_usuarios.getRol());
                if (listbox_cups.getSelectedItems().isEmpty()) {
                    Elemento elemento = new Elemento();
                    elemento.setTipo("rol");
                    elemento.setCodigo(roles_usuarios.getRol());
                    elemento = getServiceLocator().getServicio(
                            ElementoService.class).consultar(elemento);
                    mensaje = "Para registrar este usuario, debe agregar al menos un centro de atencion al rol "
                            + (elemento != null ? elemento.getDescripcion()
                            : "ROL NO ENCONTRADO");
                    valida = false;
                }
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
            parameters.put("codigo_empresa", codigo_empresa);
            parameters.put("codigo_sucursal", codigo_sucursal);
            if (chkTodos_centros.isChecked()) {
                parameters.put("codigo_centro",
                        centro_atencion_session.getCodigo_centro());
            }

            resultadoPaginadoMacro.buscarDatos(parameters);

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public Row crearFilas(Object objeto, Component componente) throws Exception {
        Row fila = new Row();

        final Usuarios usuarios = (Usuarios) objeto;

        Map<String, Object> parameters = new HashMap();
        parameters.put("codigo_empresa", usuarios.getCodigo_empresa());
        parameters.put("codigo_usuario", usuarios.getCodigo());
        final List<Roles_usuarios> lista_roles_usuarios = getServiceLocator()
                .getRoles_usuariosService().listar(parameters);

        String roles = "";
        for (int i = 0; i < lista_roles_usuarios.size(); i++) {
            Roles_usuarios roles_usuarios = lista_roles_usuarios.get(i);

            Elemento elemento = new Elemento();
            elemento.setCodigo("" + roles_usuarios.getRol());
            elemento.setTipo("rol");
            elemento = getServiceLocator().getElementoService().consultar(
                    elemento);

            roles += elemento != null ? elemento.getDescripcion() : "";
            if (i != lista_roles_usuarios.size() - 1) {
                roles += ",";
            }
        }

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(usuarios.getCodigo() + ""));
        fila.appendChild(new Label(usuarios.getNombres() + " "
                + usuarios.getApellidos()));
        fila.appendChild(new Label(usuarios.getLogin() + ""));
        fila.appendChild(new Label(usuarios.getPassword() + ""));
        fila.appendChild(new Label(usuarios.getDireccion() + ""));
        Doublebox doublebox_telefono = new Doublebox();
        doublebox_telefono.setReadonly(true);
        doublebox_telefono.setInplace(true);
        doublebox_telefono.setFormat("#");
        doublebox_telefono.setHflex("1");
        doublebox_telefono.setValue(ConvertidorDatosUtil.convertirDato(usuarios
                .getTel_res()));
        fila.appendChild(doublebox_telefono);
        Label label = new Label(roles + "");
        label.setTooltiptext(roles);
        fila.appendChild(label);

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(usuarios, lista_roles_usuarios);
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
                                    eliminarDatos(usuarios);
                                    buscarDatos();
                                }
                            }
                        });
            }
        });
        hbox.appendChild(space);
        /* Esto es para el mismo usuario no puede eliminarse */
        if (!esElMismoUsuario(usuarios)) {
            hbox.appendChild(img);
        }

        fila.appendChild(hbox);

        return fila;
    }

    private boolean esElMismoUsuario(Usuarios usuarios) {
        return usuarios.getCodigo().equalsIgnoreCase(this.usuarios.getCodigo());
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {

            if (validarForm()) {
                FormularioUtil.setUpperCase(groupboxEditar, "tbxPassword",
                        "tbxRepetirPassword");
                // Cargamos los componentes //

                Usuarios usuarios = getInstanciaCorrecta();
                usuarios.setCodigo_empresa(empresa.getCodigo_empresa());
                usuarios.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                usuarios.setCodigo(tbxCodigo.getValue());
                usuarios.setLogin(tbxLogin.getValue());
                usuarios.setPassword(tbxPassword.getValue());
                usuarios.setNombres(tbxNombres.getValue());
                usuarios.setApellidos(tbxApellidos.getValue());
                usuarios.setNivel(lbxNivel.getSelectedItem().getValue()
                        .toString());
                usuarios.setCreacion_date(new Timestamp(new GregorianCalendar()
                        .getTimeInMillis()));
                usuarios.setUltimo_update(new Timestamp(new GregorianCalendar()
                        .getTimeInMillis()));
                usuarios.setCreacion_user(usuarios.getCodigo().toString());
                usuarios.setUltimo_user(usuarios.getCodigo().toString());
                usuarios.setTipo("01");
                usuarios.setEntidad("");
                usuarios.setCodigo_ente("");
                usuarios.setCons_ente("");
                usuarios.setActivo(chbActivo.isChecked());
                usuarios.setRol("01");
                usuarios.setDireccion(tbxDireccion.getValue());
                usuarios.setTel_oficina(dbxTel_oficina.getText());
                usuarios.setTel_res(dbxTel_res.getText());
                usuarios.setCelular(dbxCelular.getText());
                usuarios.setRegistro_medico(tbxRegistro_medico.getValue());

                if (lbxTipo_med.getSelectedItem().getValue().toString() != null) {
                    usuarios.setTipo_med(lbxTipo_med.getSelectedItem()
                            .getValue().toString());
                }

                usuarios.setCodigo_especialidad(lbxCodigo_especialidad
                        .getSelectedItem().getValue().toString());
                usuarios.setDepartamento(lbxDepartamento.getSelectedItem()
                        .getValue().toString());
                usuarios.setMunicipio(lbxMunicipio.getSelectedItem().getValue()
                        .toString());
                usuarios.setTipo_medico(lbxTipo_prestador.getSelectedItem()
                        .getValue().toString());
                usuarios.setEmail(tbxEmail.getValue());
                usuarios.setFirma(imageUsuario.getContent() != null ? imageUsuario
                        .getContent().getByteData() : null);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user", usuarios);

                List<Roles_usuarios> listado_roles = new ArrayList<Roles_usuarios>();
                List<Roles_usuarios_caps> listado_roles_caps = new ArrayList<Roles_usuarios_caps>();

                for (Component component : rowsRoles.getChildren()) {
                    Row row_fila = (Row) component;
                    Intbox intbox_horas = (Intbox) row_fila.getChildren()
                            .get(3).getFirstChild();
                    Roles_usuarios roles_usuarios = (Roles_usuarios) row_fila
                            .getValue();
                    roles_usuarios.setHoras_medico(intbox_horas.getValue());
                    listado_roles.add(roles_usuarios);
                    Listbox listbox_cups = (Listbox) this
                            .getFellow("listbox_cups_" + codigo_empresa + "_"
                                    + codigo_sucursal + "_"
                                    + roles_usuarios.getRol());
                    for (Listitem listitem : listbox_cups.getSelectedItems()) {
                        Centro_atencion centro_atencion = (Centro_atencion) listitem
                                .getValue();
                        Roles_usuarios_caps roles_usuarios_caps = new Roles_usuarios_caps();
                        roles_usuarios_caps.setCodigo_empresa(codigo_empresa);
                        roles_usuarios_caps.setCodigo_sucursal(codigo_sucursal);
                        roles_usuarios_caps.setCodigo_usuario(usuarios
                                .getCodigo());
                        roles_usuarios_caps.setRol(roles_usuarios.getRol());
                        roles_usuarios_caps.setCodigo_centro(centro_atencion
                                .getCodigo_centro());
                        listado_roles_caps.add(roles_usuarios_caps);
                    }
                }

                map.put("roles_usuarios", listado_roles);
                map.put("roles_usuarios_caps", listado_roles_caps);
                map.put("accion", tbxAccion.getValue());

                getServiceLocator().getUsuariosService().guardar(map);
                final boolean es_el_mismo = esElMismoUsuario(usuarios);
                Messagebox
                        .show("Los datos se guardaron satisfactoriamente"
                                + (es_el_mismo ? ", se actualizara para que tome los cambios"
                                : ""), "Informacion ..", Messagebox.OK,
                                Messagebox.INFORMATION,
                                new EventListener<Event>() {
                                    @Override
                                    public void onEvent(Event event)
                                    throws Exception {
                                        if (es_el_mismo) {
                                            _recargar();
                                        }
                                    }
                                });

                tbxAccion.setValue("modificar");
                tbxCodigo.setDisabled(true);

            }
        } catch (Exception e) {
            if (!(e instanceof WrongValueException)) {
                Messagebox.show(e.getMessage(), "information", Messagebox.OK,
                        Messagebox.ERROR);
            } else {
                log.error("Error en el componente "
                        + ((WrongValueException) e).getComponent().getId(), e);
            }
        }

    }

    private Usuarios getInstanciaCorrecta() {
        if (tbxCodigo.getValue().trim().equalsIgnoreCase(usuarios.getCodigo())) {
            return usuarios;
        }
        return new Usuarios();
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj,
            List<Roles_usuarios> lista_roles_usuarios) throws Exception {
        Usuarios usuarios = (Usuarios) obj;
		// Esto se hace para que consulte la firma de los usuarios ya que en
        // listado no viene dicha firma
        usuarios = getServiceLocator().getUsuariosService().consultar(usuarios);
        try {
            btGuardar.setLabel("Modificar Usuarios");
            tbxCodigo.setValue(usuarios.getCodigo());
            tbxLogin.setValue(usuarios.getLogin());
            tbxPassword.setValue(usuarios.getPassword().isEmpty() ? "."
                    : usuarios.getPassword());
            tbxRepetirPassword.setValue(usuarios.getPassword().isEmpty() ? "."
                    : usuarios.getPassword());
            tbxNombres.setValue(usuarios.getNombres().isEmpty() ? "."
                    : usuarios.getNombres());
            tbxApellidos.setValue(usuarios.getApellidos().isEmpty() ? "."
                    : usuarios.getApellidos());
            Utilidades.setValueFrom(lbxNivel,
                    usuarios.getNivel().isEmpty() ? "." : usuarios.getNivel());

            chbActivo.setChecked(usuarios.getActivo());
            tbxDireccion.setValue(usuarios.getDireccion().isEmpty() ? "."
                    : usuarios.getDireccion());
            dbxTel_oficina.setValue(ConvertidorDatosUtil.convertirDato(usuarios
                    .getTel_oficina()));
            dbxTel_res.setValue(ConvertidorDatosUtil.convertirDato(usuarios
                    .getTel_res()));
            dbxCelular.setValue(ConvertidorDatosUtil.convertirDato(usuarios
                    .getCelular()));
            tbxRegistro_medico
                    .setValue(usuarios.getRegistro_medico().isEmpty() ? "."
                            : usuarios.getRegistro_medico());

            if (usuarios.getFirma() != null) {
                try {
                    AImage aImage = new AImage("firma_usr", usuarios.getFirma());
                    imageUsuario.setContent(aImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                imageUsuario.setSrc("");
            }
            for (int i = 0; i < lbxDepartamento.getItemCount(); i++) {
                Listitem listitem = lbxDepartamento.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(usuarios.getDepartamento())) {
                    listitem.setSelected(true);
                    i = lbxDepartamento.getItemCount();
                }
            }

            Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
                    getServiceLocator());
            for (int i = 0; i < lbxMunicipio.getItemCount(); i++) {
                Listitem listitem = lbxMunicipio.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(usuarios.getMunicipio())) {
                    listitem.setSelected(true);
                    i = lbxMunicipio.getItemCount();
                }
            }

            for (int i = 0; i < lbxDepartamento.getItemCount(); i++) {
                Listitem listitem = lbxDepartamento.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(usuarios.getDepartamento())) {
                    listitem.setSelected(true);
                    i = lbxDepartamento.getItemCount();
                }
            }

            Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
                    getServiceLocator());
            for (int i = 0; i < lbxMunicipio.getItemCount(); i++) {
                Listitem listitem = lbxMunicipio.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(usuarios.getMunicipio())) {
                    listitem.setSelected(true);
                    i = lbxMunicipio.getItemCount();
                }
            }

            Utilidades.setValueFrom(lbxTipo_med, usuarios.getTipo_med());
            Utilidades.setValueFrom(lbxCodigo_especialidad,
                    usuarios.getCodigo_especialidad());
            Utilidades.setValueFrom(lbxTipo_prestador,
                    usuarios.getTipo_medico());

            tbxEmail.setValue(usuarios.getEmail());
            cambiarLabelUsuario(usuarios.getNombres(), usuarios.getApellidos());

            limpiarRoles();
            for (Roles_usuarios roles_usuarios : lista_roles_usuarios) {
                adicionarRol(roles_usuarios, null);
                Listbox listbox_cups = (Listbox) this.getFellow("listbox_cups_"
                        + codigo_empresa + "_" + codigo_sucursal + "_"
                        + roles_usuarios.getRol());
                for (Listitem listitem : listbox_cups.getItems()) {
                    Centro_atencion centro_atencion = (Centro_atencion) listitem
                            .getValue();
                    Roles_usuarios_caps roles_usuarios_caps = new Roles_usuarios_caps();
                    roles_usuarios_caps.setCodigo_empresa(roles_usuarios
                            .getCodigo_empresa());
                    roles_usuarios_caps.setCodigo_sucursal(roles_usuarios
                            .getCodigo_sucursal());
                    roles_usuarios_caps.setCodigo_usuario(roles_usuarios
                            .getCodigo_usuario());
                    roles_usuarios_caps.setRol(roles_usuarios.getRol());
                    roles_usuarios_caps.setCodigo_centro(centro_atencion
                            .getCodigo_centro());
                    roles_usuarios_caps = getServiceLocator().getServicio(
                            Roles_usuarios_capsService.class).consultar(
                                    roles_usuarios_caps);
                    if (roles_usuarios_caps != null) {
                        listitem.setSelected(true);
                    }

                }
            }

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            if (e instanceof WrongValueException) {
				// log.info("Id de componente con el error : "
                // + ((WrongValueException) e).getComponent().getId());
            }
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void limpiarRoles() {
        rowsRoles.getChildren().clear();
    }

    public void eliminarDatos(Object obj) throws Exception {
        Usuarios usuarios = (Usuarios) obj;
        try {
            int result = getServiceLocator().getUsuariosService().eliminar(
                    usuarios);
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

    /**
     * con este metodo listamos los roles..
	 *
     */
    public void adicionarRoles() throws Exception {
        try {
            String rol = listboxRol.getSelectedItem().getValue().toString();
            String codigo_sucursal = listboxSucursal.getSelectedItem()
                    .getValue().toString();
            String provedorServ = null;

            Roles_usuarios roles_usuarios = new Roles_usuarios();
            roles_usuarios.setCodigo_empresa(codigo_empresa);
            roles_usuarios.setCodigo_sucursal(codigo_sucursal);
            roles_usuarios.setCodigo_usuario(tbxCodigo.getValue());
            roles_usuarios.setHoras_medico(0);
            roles_usuarios.setRol(rol);

            /* para que vuelva a deseleccionarse */
            if (!codigo_sucursal.equals("")) {
                adicionarRol(roles_usuarios, provedorServ);
            } else {
                for (int i = 0; i < listboxSucursal.getItemCount(); i++) {
                    Listitem listitem = listboxSucursal.getItemAtIndex(i);
                    if (!listitem.getValue().toString().equals("")) {
                        adicionarRol(roles_usuarios, provedorServ);
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
            log.error(e.getMessage(), e);
            MensajesUtil.mensajeError(e, null, this);
        }
    }

    public void adicionarRol(Roles_usuarios roles_usuarios, String provedorServ)
            throws Exception {

        try {
            boolean existe = false;

            // Validamos si el rol ya se encuentra en el listbox //
            List<Component> listado_filas = rowsRoles.getChildren();

            for (int i = 0; i < listado_filas.size(); i++) {
                Row row_fila = (Row) listado_filas.get(i);
                Roles_usuarios roles_aux = (Roles_usuarios) row_fila.getValue();
                if (roles_usuarios.getCodigo_empresa().equals(
                        roles_aux.getCodigo_empresa())
                        && roles_usuarios.getCodigo_sucursal().equals(
                                roles_aux.getCodigo_sucursal())
                        && roles_usuarios.getRol().equals(roles_aux.getRol())) {
                    existe = true;
                }
            }

            // Si no existe en la tabla lo ingresamos en el listbox //
            if (!existe) {
                Elemento elemento = new Elemento();
                elemento.setTipo("rol");
                elemento.setCodigo(roles_usuarios.getRol());

                elemento = getServiceLocator().getElementoService().consultar(
                        elemento);
                Sucursal suc = new Sucursal();
                suc.setCodigo_sucursal(roles_usuarios.getCodigo_sucursal());
                suc.setCodigo_empresa(roles_usuarios.getCodigo_empresa());
                suc = getServiceLocator().getServicio(GeneralExtraService.class).consultar(suc);

                String nameRol = (elemento != null ? elemento.getDescripcion()
                        : "*");

                Row row_fila = new Row();
                row_fila.setValue(roles_usuarios);

                Detail detalle_cups = new Detail();

                Listbox listbox_cups = new Listbox();
                listbox_cups.setId("listbox_cups_" + codigo_empresa + "_"
                        + codigo_sucursal + "_" + roles_usuarios.getRol());

                Auxhead auxhead = new Auxhead();
                Auxheader auxheader = new Auxheader(
                        "Listado de caps donde ejerce el rol");
                auxheader.setColspan(2);
                auxhead.appendChild(auxheader);

                listbox_cups.appendChild(auxhead);

                listbox_cups.setCheckmark(true);
                listbox_cups.setMultiple(true);
                Listhead listhead = new Listhead();

                Listheader listheader = new Listheader("Codigo");
                listheader.setWidth("80px");
                listhead.appendChild(listheader);

                listheader = new Listheader("Nombre");
                listhead.appendChild(listheader);

                listbox_cups.appendChild(listhead);

                if (listado_centros_atencion != null) {
                    for (Centro_atencion centro_atencion : listado_centros_atencion) {
                        if (!centro_atencion.getCodigo_centro().equals(
                                IConstantes.CENTRO_ATENCION_CUALQUIERA)) {
                            Listitem listitem = new Listitem();
                            listitem.setValue(centro_atencion);
                            Listcell listcell = new Listcell();
                            listcell.setLabel(centro_atencion
                                    .getCodigo_centro());
                            listitem.appendChild(listcell);
                            listcell = new Listcell();
                            listcell.setLabel(centro_atencion
                                    .getNombre_centro());
                            listitem.appendChild(listcell);
                            listbox_cups.appendChild(listitem);
                        }
                    }
                } else {
                    // log.info("listado_centros_atencion es NULL");
                }

                listbox_cups.setRows(5);

                detalle_cups.appendChild(listbox_cups);

                row_fila.appendChild(detalle_cups);

                row_fila.appendChild(Utilidades.obtenerCell(
                        suc != null ? suc.getNombre_sucursal() : "*",
                        Label.class, true, true));

                row_fila.appendChild(Utilidades.obtenerCell(nameRol,
                        Label.class, true, true));

                Cell listcell_horas = Utilidades.obtenerCell(
                        roles_usuarios.getHoras_medico(), Intbox.class, false,
                        false);
                row_fila.appendChild(listcell_horas);

                Checkbox checkbox_seleccionar = new Checkbox();
                checkbox_seleccionar.setId("checkbox_seleccionar_"
                        + roles_usuarios.getCodigo_sucursal() + "_"
                        + roles_usuarios.getRol());
                Cell celda = new Cell();
                celda.appendChild(checkbox_seleccionar);
                row_fila.appendChild(celda);

                rowsRoles.appendChild(row_fila);

            }
            verificamosRolMedico();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            MensajesUtil.mensajeError(e, null, this);
        }
    }

    private void verificamosRolMedico() {
        List<Component> listitems = rowsRoles.getChildren();
        existe_rol_medico = false;
        for (Component componente : listitems) {
            Row row_fila = (Row) componente;
            Roles_usuarios roles_usuarios = (Roles_usuarios) row_fila
                    .getValue();

            if (Utilidades.isMedico(roles_usuarios.getRol())) {
                existe_rol_medico = true;
            } else if (Utilidades.isEnfermeraJefe(roles_usuarios.getRol())) {
                existe_rol_medico = true;
            } else if (roles_usuarios.getRol().equals(IRoles.BACTERIOLOGO)) {
                // esto es para especificar que es un prestador
                existe_rol_medico = true;
            }
        }

        //
        groupMedico.setVisible(existe_rol_medico);
    }

    /**
     * Eliminar los roles seleccionados.
	 *
     */
    public List<Row> getFilasSeleccionadas(int columna) {
        List<Row> listado_seleccionadas = new ArrayList<Row>();
        for (int i = 0; i < rowsRoles.getChildren().size(); i++) {
            Component fila_componente = rowsRoles.getChildren().get(i);
            Row row_fila = (Row) fila_componente;
            Checkbox checkbox_seleccionar = (Checkbox) row_fila.getChildren()
                    .get(columna).getFirstChild();
            if (checkbox_seleccionar.isChecked()) {
                listado_seleccionadas.add(row_fila);
            }

        }
        return listado_seleccionadas;
    }

    public void eliminarRoles() throws Exception {
        try {
            List<Row> listado_seleccion = getFilasSeleccionadas(4);
            if (listado_seleccion.size() == 0) {
                throw new Exception(
                        "Lo sentimos pero no existe rol seleccionado para quitar");
            }
            Messagebox.show("Esta seguro que desea quitar esta informacion? ",
                    "Eliminar Descriptor", Messagebox.YES + Messagebox.NO,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event event) throws Exception {
                            if ("onYes".equals(event.getName())) {
                                List<Row> listado_seleccion_aux = getFilasSeleccionadas(4);

                                Object array[] = listado_seleccion_aux
                                .toArray();
                                int selected = (array.length - 1);
                                while (selected >= 0) {
                                    Row row_fila = (Row) array[selected];
                                    rowsRoles.removeChild(row_fila);
                                    selected--;
                                }
                                verificamosRolMedico();
                            }
                        }
                    });

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Error al Eliminar Rol",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    /**
     * Metodo para cargar la imagenes
     *
     * @author EvanSandryHB
	 *
     */
    public void guardarImagen(Media media) throws Exception {
        try {
            if (media instanceof org.zkoss.image.Image) {
                AImage aImage = new AImage("firma_usr", media.getByteData());
                imageUsuario.setContent(aImage);
            } else {
                Messagebox.show("No es una foto: " + media, "Error",
                        Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Error cargando foto!!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    /**
     * Permite eliminar la imagen de la firma cargada
	 *
     */
    public void borrarImagen() throws Exception {
        imageUsuario.setSrc("");
    }
}
