/*
 * via_ingreso_consultasAction.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Via_ingreso_especialidad;
import healthmanager.modelo.bean.Via_ingreso_rol;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.ElementoService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IRoles;
import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.macros.macro_row.BandBoxRowMacro.IConfiguracionBandbox;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Configuracion_admision_ingresoAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Configuracion_admision_ingresoAction.class);
    private Configuracion_admision_ingresoService configuracion_admision_ingresoService;

    private IConfiguracionBandbox<Elemento> iconfiguracionbandbox_via_ingreso;

    @View
    private Listbox listboxResultado;

    @View
    private Bandbox bandboxBusqueda;

    @Override
    public void init() {
        listarCombos();
    }

    public void listarCombos() {
        listarParameter();
    }

    public void listarParameter() {

    }

    // Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {

    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {

    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {
        boolean valida = true;
        List<Listitem> listado_listitems = listboxResultado.getItems();
        Map<String, String> mapa_vias = new HashMap<String, String>();
        if (!listado_listitems.isEmpty()) {
            for (Listitem listitem : listado_listitems) {
                final BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) listitem
                        .getChildren().get(0).getFirstChild();
                if (bandBoxRowMacro.getRegistroSeleccionado() == null) {
                    valida = false;
                    bandBoxRowMacro.setFocus(true);
                    MensajesUtil.notificarError(
                            "El campo Via ingreso es obligatorio",
                            bandBoxRowMacro);
                    break;
                }

                if (mapa_vias.containsKey(((Elemento) bandBoxRowMacro
                        .getRegistroSeleccionado()).getCodigo())) {
                    valida = false;
                    bandBoxRowMacro.setFocus(true);
                    MensajesUtil.notificarError(
                            "El campo Via ingreso esta repetido",
                            bandBoxRowMacro);
                    break;
                } else {
                    mapa_vias.put(((Elemento) bandBoxRowMacro
                            .getRegistroSeleccionado()).getCodigo(),
                            bandBoxRowMacro.getValue());
                }

            }
        }
        return valida;
    }

    // Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("codigo_empresa", codigo_empresa);
            parametros.put("codigo_sucursal", codigo_sucursal);

            List<Configuracion_admision_ingreso> listado_datos = configuracion_admision_ingresoService
                    .listar(parametros);

            listboxResultado.getItems().clear();

            for (Configuracion_admision_ingreso configuracion_admision_ingreso : listado_datos) {
                crearFilas(configuracion_admision_ingreso);
            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void inicializamosEvento() {
        iconfiguracionbandbox_via_ingreso = new IConfiguracionBandbox<Elemento>() {

            @Override
            public void onSeleccionar(Elemento t, Bandbox bandbox) {
                Listbox listbox_roles = (Listbox) bandbox
                        .getAttribute("LISTBOX_ROLES");
                seleccionarRolesViaIngreso(listbox_roles,
                        ((BandBoxRowMacro) bandbox).getRegistroSeleccionado());
                Listbox listbox_especialidades = (Listbox) bandbox
                        .getAttribute("LISTBOX_ESPECIALIDADES");
                seleccionarEspecialidadesViaIngreso(listbox_especialidades,
                        ((BandBoxRowMacro) bandbox).getRegistroSeleccionado());
            }

            @Override
            public String getCabecera(Bandbox bandbox) {
                return "Codigo#70px|Via de ingreso";
            }

            @Override
            public String getWidthListBox() {
                return "350px";
            }

            @Override
            public String getHeightListBox() {
                return "200px";
            }

            @Override
            public void renderListitem(Listitem listitem, Elemento registro,
                    Bandbox bandbox) {
                listitem.appendChild(new Listcell(registro.getCodigo()));
                listitem.appendChild(new Listcell(""
                        + registro.getDescripcion()));
            }

            @Override
            public List<Elemento> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros, Bandbox bandbox) {
                parametros.put("parametroTodo", valorBusqueda);
                parametros.put("tipo", "via_ingreso");
                return getServiceLocator().getServicio(ElementoService.class)
                        .listar(parametros);
            }

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Elemento registro) {
                bandbox.setValue(registro.getDescripcion());
                return true;
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {

            }
        };

    }

    public void crearFilas(
            final Configuracion_admision_ingreso configuracion_admision_ingreso)
            throws Exception {
        final Listitem listitem_fila = new Listitem();

        final Popup popup = new Popup();
        final Listbox listbox_roles = new Listbox();
        listbox_roles.setCheckmark(true);
        listbox_roles.setMultiple(true);
        listbox_roles.setWidth("300px");
        listbox_roles.setHeight("300px");

        Listhead listhead = new Listhead();
        Listheader listheader = new Listheader();
        listheader.setWidth("35px");
        listhead.appendChild(listheader);

        listheader = new Listheader();
        listheader.setLabel("Rol de usuario");
        listhead.appendChild(listheader);

        listbox_roles.appendChild(listhead);
        popup.appendChild(listbox_roles);

        Listcell listcell = new Listcell();
        final BandBoxRowMacro bandBoxRowMacro = new BandBoxRowMacro();

        bandBoxRowMacro.setHflex("1");

        bandBoxRowMacro.setAttribute("LISTBOX_ROLES", listbox_roles);
        bandBoxRowMacro.configurar(iconfiguracionbandbox_via_ingreso);

        listcell.appendChild(bandBoxRowMacro);

        listitem_fila.appendChild(listcell);

        Listbox listbox_informacion = new Listbox();
        listbox_informacion.setMold("select");
        listbox_informacion.setName("solicitar_informacion");
        Utilidades.listarElementoListbox(listbox_informacion, true,
                getServiceLocator());

        if (configuracion_admision_ingreso != null) {
            Utilidades.seleccionarListitem(listbox_informacion,
                    configuracion_admision_ingreso.getSolicitar_informacion());
        }

        listbox_informacion.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(listbox_informacion);
        listitem_fila.appendChild(listcell);

        Checkbox checkbox_prestador = new Checkbox();
        checkbox_prestador
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .getSolicitar_prestador() : false);
        checkbox_prestador.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_prestador);
        listitem_fila.appendChild(listcell);

        Checkbox checkbox_atencion = new Checkbox();
        checkbox_atencion
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .getSolicitar_marca() : false);
        checkbox_atencion.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_atencion);
        listitem_fila.appendChild(listcell);

        Checkbox checkbox_fac_aut = new Checkbox();
        checkbox_fac_aut
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .getFac_automatica_particular() : false);
        checkbox_fac_aut.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_fac_aut);
        listitem_fila.appendChild(listcell);

        Checkbox checkbox_mostrar_cita = new Checkbox();
        checkbox_mostrar_cita
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .getMostrar_cita() : false);
        checkbox_mostrar_cita.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_mostrar_cita);
        listitem_fila.appendChild(listcell);

        Checkbox checkbox_obligatorio_cita = new Checkbox();
        checkbox_obligatorio_cita
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .getObligatorio_cita() : false);
        checkbox_obligatorio_cita.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_obligatorio_cita);
        listitem_fila.appendChild(listcell);

        final Checkbox checkbox_es_pyp = new Checkbox();
        checkbox_es_pyp
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .getEs_pyp() : false);
        checkbox_es_pyp.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_es_pyp);
        listitem_fila.appendChild(listcell);

        final Checkbox checkbox_lab_pyp = new Checkbox();
        checkbox_lab_pyp
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .getLaboratorio_pyp() : false);
        checkbox_lab_pyp.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_lab_pyp);
        listitem_fila.appendChild(listcell);

        final Checkbox checkbox_programa_pyp = new Checkbox();
        checkbox_programa_pyp
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .getPrograma_lab_pyp() : false);
        checkbox_programa_pyp.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_programa_pyp);
        listitem_fila.appendChild(listcell);

        final Checkbox checkbox_habilitar_certificado = new Checkbox();
        checkbox_habilitar_certificado
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .getHabilitar_certificado() : false);
        checkbox_habilitar_certificado.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_habilitar_certificado);
        listitem_fila.appendChild(listcell);

        final Checkbox checkbox_aplica_cualquier_servicio = new Checkbox();
        checkbox_aplica_cualquier_servicio
                .setChecked(configuracion_admision_ingreso != null ? configuracion_admision_ingreso
                        .isAplica_cualquier_servicio() : false);
        checkbox_aplica_cualquier_servicio.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(checkbox_aplica_cualquier_servicio);
        listitem_fila.appendChild(listcell);

        checkbox_lab_pyp.setDisabled(!checkbox_es_pyp.isChecked());
        checkbox_programa_pyp.setDisabled(!checkbox_es_pyp.isChecked());

        checkbox_es_pyp.addEventListener(Events.ON_CHECK,
                new EventListener<Event>() {

                    @Override
                    public void onEvent(Event arg0) throws Exception {
                        checkbox_lab_pyp.setDisabled(!checkbox_es_pyp
                                .isChecked());
                        checkbox_programa_pyp.setDisabled(!checkbox_es_pyp
                                .isChecked());
                    }
                });

        final Toolbarbutton toolbarbutton_aux = new Toolbarbutton("",
                "/images/configuracion_mini.png");

        toolbarbutton_aux
                .setTooltiptext("Configurar los roles de medico que estan asociados a esta via de ingreso");

        List<Elemento> listado_roles = getServiceLocator().getElementoService()
                .listar("rol");

        for (Elemento elemento : listado_roles) {
            if (Utilidades.isMedico(elemento.getCodigo())
                    || Utilidades.isAuxiliarOEnfermeraPyP(elemento.getCodigo())
                    || Utilidades.isEnfermeraJefe(elemento.getCodigo())
                    || elemento.getCodigo().equals(IRoles.VACUNACION)
                    || elemento.getCodigo().equals(IRoles.BACTERIOLOGO)) {
                Listitem listitem = new Listitem();
                listitem.setValue(elemento.getCodigo());
                listitem.appendChild(new Listcell());
                listitem.appendChild(new Listcell(elemento.getDescripcion()));
                listbox_roles.appendChild(listitem);
            }
        }

        toolbarbutton_aux.addEventListener(Events.ON_CLICK,
                new EventListener<Event>() {

                    @Override
                    public void onEvent(Event arg0) throws Exception {
                        popup.open(toolbarbutton_aux);
                    }

                });

        seleccionarRolesViaIngreso(listbox_roles,
                bandBoxRowMacro.getRegistroSeleccionado());

        listcell = new Listcell();
        listcell.appendChild(popup);
        listcell.appendChild(toolbarbutton_aux);
        listitem_fila.appendChild(listcell);

        final Popup popup_especialidad = new Popup();
        final Listbox listbox_especialidades = new Listbox();
        listbox_especialidades.setCheckmark(true);
        listbox_especialidades.setMultiple(true);
        listbox_especialidades.setWidth("300px");
        listbox_especialidades.setHeight("300px");

        Listhead listhead_especialidad = new Listhead();
        Listheader listheader_especialidad = new Listheader();
        listheader_especialidad.setWidth("35px");
        listhead_especialidad.appendChild(listheader_especialidad);

        listheader_especialidad = new Listheader();
        listheader_especialidad.setLabel("Especialidades");
        listhead_especialidad.appendChild(listheader_especialidad);

        listbox_especialidades.appendChild(listhead_especialidad);
        popup_especialidad.appendChild(listbox_especialidades);

        final Toolbarbutton toolbarbutton_especialidad = new Toolbarbutton("",
                "/images/configuracion_mini.png");

        toolbarbutton_especialidad
                .setTooltiptext("Configurar las especialidades que estan asociados a esta via de ingreso");

        List<Especialidad> listado_especialidades = getServiceLocator()
                .getEspecialidadService().listar(new HashMap<String, Object>());

        for (Especialidad especialidad : listado_especialidades) {
            Listitem listitem = new Listitem();
            listitem.setValue(especialidad.getCodigo());
            listitem.appendChild(new Listcell());
            listitem.appendChild(new Listcell(especialidad.getNombre()));
            listbox_especialidades.appendChild(listitem);
        }

        bandBoxRowMacro.setAttribute("LISTBOX_ESPECIALIDADES",
                listbox_especialidades);

        toolbarbutton_especialidad.addEventListener(Events.ON_CLICK,
                new EventListener<Event>() {

                    @Override
                    public void onEvent(Event arg0) throws Exception {
                        popup_especialidad.open(toolbarbutton_especialidad);
                    }

                });

        seleccionarEspecialidadesViaIngreso(listbox_especialidades,
                bandBoxRowMacro.getRegistroSeleccionado());

        listcell = new Listcell();
        listcell.appendChild(popup_especialidad);
        listcell.appendChild(toolbarbutton_especialidad);
        listitem_fila.appendChild(listcell);

        Listbox listbox_ambito = new Listbox();
        listbox_ambito.setMold("select");
        listbox_ambito.setName("ambito_procedimiento");
        Utilidades.listarElementoListbox(listbox_ambito, true,
                getServiceLocator());

        if (configuracion_admision_ingreso != null) {
            Utilidades.seleccionarListitem(listbox_ambito,
                    configuracion_admision_ingreso.getAmbito_realizacion());
        }

        listbox_ambito.setHflex("1");
        listcell = new Listcell();
        listcell.appendChild(listbox_ambito);
        listitem_fila.appendChild(listcell);

        Hlayout hlayout = new Hlayout();
        listitem_fila.setStyle("text-align: justify;nowrap:nowrap");
        Toolbarbutton toolbarbutton = new Toolbarbutton("Eliminar");
        toolbarbutton.setImage("/images/borrar.gif");
        toolbarbutton.setTooltiptext("Eliminar registro");
        toolbarbutton.addEventListener(Events.ON_CLICK,
                new EventListener<Event>() {
                    @Override
                    public void onEvent(Event arg0) throws Exception {
                        Messagebox
                        .show("Esta seguro que desea eliminar este registro? ",
                                "Eliminar Registro",
                                Messagebox.YES + Messagebox.NO,
                                Messagebox.QUESTION,
                                new org.zkoss.zk.ui.event.EventListener<Event>() {
                                    public void onEvent(Event event)
                                    throws Exception {
                                        if ("onYes".equals(event
                                                .getName())) {
                                            eliminarDatos(listitem_fila);
                                        }
                                    }
                                });
                    }
                });
        hlayout.appendChild(toolbarbutton);
        listcell = new Listcell();
        listcell.appendChild(hlayout);
        listitem_fila.appendChild(listcell);

        if (configuracion_admision_ingreso != null) {
            Elemento elemento = new Elemento();
            elemento.setCodigo(configuracion_admision_ingreso.getVia_ingreso());
            elemento.setTipo("via_ingreso");
            elemento = getServiceLocator().getElementoService().consultar(
                    elemento);
            bandBoxRowMacro.seleccionarRegistro(elemento,
                    elemento.getDescripcion());
        }

        Clients.scrollIntoView(bandBoxRowMacro);
        bandBoxRowMacro.setFocus(true);

        listboxResultado.appendChild(listitem_fila);

    }

    public void seleccionarRolesViaIngreso(Listbox listbox_roles,
            Object registro) {
        for (Listitem listitem : listbox_roles.getItems()) {
            listitem.setSelected(false);
        }

        if (registro != null) {
            Elemento elemento = (Elemento) registro;
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("codigo_via_ingreso", elemento.getCodigo());
            parametros.put("codigo_empresa", codigo_empresa);
            // //log.info("Map de vias ingreso: " + param);
            List<Via_ingreso_rol> listado_vias = getServiceLocator()
                    .getServicio(GeneralExtraService.class).listar(Via_ingreso_rol.class,
                            parametros);

            for (Via_ingreso_rol via_ingreso_rol : listado_vias) {
                for (Listitem listitem : listbox_roles.getItems()) {
                    if (via_ingreso_rol.getCodigo_rol().equals(
                            listitem.getValue().toString())) {
                        listitem.setSelected(true);
                        break;
                    }
                }
            }
        }
    }

    public void seleccionarEspecialidadesViaIngreso(
            Listbox listbox_especialidades, Object registro) {
        for (Listitem listitem : listbox_especialidades.getItems()) {
            listitem.setSelected(false);
        }

        if (registro != null) {
            Elemento elemento = (Elemento) registro;
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("codigo_via_ingreso", elemento.getCodigo());
            parametros.put("codigo_empresa", codigo_empresa);
            // //log.info("Map de vias ingreso: " + param);
            List<Via_ingreso_especialidad> listado_especialidades = getServiceLocator()
                    .getServicio(GeneralExtraService.class).listar(Via_ingreso_especialidad.class,
                            parametros);

            for (Via_ingreso_especialidad via_ingreso_especialidad : listado_especialidades) {
                for (Listitem listitem : listbox_especialidades.getItems()) {
                    if (via_ingreso_especialidad.getCodigo_especialidad()
                            .equals(listitem.getValue().toString())) {
                        listitem.setSelected(true);
                        break;
                    }
                }
            }
        }
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            //log.info("ejecutando metodo @guardarDatos()");
            if (validarForm()) {
                List<Listitem> listado_items = listboxResultado.getItems();
                if (!listado_items.isEmpty()) {
                    List<Map<String, Object>> listado_datos = new ArrayList<Map<String, Object>>();
                    for (Listitem listitem : listado_items) {
                        Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
                        configuracion_admision_ingreso
                                .setCodigo_empresa(codigo_empresa);
                        configuracion_admision_ingreso
                                .setCodigo_sucursal(codigo_sucursal);
                        configuracion_admision_ingreso
                                .setCreacion_date(new Timestamp(new Date()
                                                .getTime()));
                        configuracion_admision_ingreso
                                .setCreacion_user(usuarios.getCodigo());
                        configuracion_admision_ingreso
                                .setSolicitar_informacion(((Listbox) listitem
                                        .getChildren().get(1).getFirstChild())
                                        .getSelectedItem().getValue()
                                        .toString());
                        configuracion_admision_ingreso
                                .setSolicitar_prestador(((Checkbox) listitem
                                        .getChildren().get(2).getFirstChild())
                                        .isChecked());
                        configuracion_admision_ingreso
                                .setSolicitar_marca(((Checkbox) listitem
                                        .getChildren().get(3).getFirstChild())
                                        .isChecked());
                        configuracion_admision_ingreso
                                .setFac_automatica_particular(((Checkbox) listitem
                                        .getChildren().get(4).getFirstChild())
                                        .isChecked());
                        configuracion_admision_ingreso
                                .setMostrar_cita(((Checkbox) listitem
                                        .getChildren().get(5).getFirstChild())
                                        .isChecked());
                        configuracion_admision_ingreso
                                .setObligatorio_cita(((Checkbox) listitem
                                        .getChildren().get(6).getFirstChild())
                                        .isChecked());
                        configuracion_admision_ingreso
                                .setEs_pyp(((Checkbox) listitem.getChildren()
                                        .get(7).getFirstChild()).isChecked());

                        configuracion_admision_ingreso
                                .setLaboratorio_pyp(((Checkbox) listitem
                                        .getChildren().get(8).getFirstChild())
                                        .isChecked());

                        configuracion_admision_ingreso
                                .setPrograma_lab_pyp(((Checkbox) listitem
                                        .getChildren().get(9).getFirstChild())
                                        .isChecked());

                        BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) listitem
                                .getChildren().get(0).getFirstChild();

                        configuracion_admision_ingreso
                                .setVia_ingreso(((Elemento) bandBoxRowMacro
                                        .getRegistroSeleccionado()).getCodigo());

                        configuracion_admision_ingreso
                                .setHabilitar_certificado(((Checkbox) listitem
                                        .getChildren().get(10).getFirstChild())
                                        .isChecked());

                        configuracion_admision_ingreso.setAplica_cualquier_servicio(((Checkbox) listitem
                                .getChildren().get(11).getFirstChild())
                                .isChecked());

                        Listbox listbox_roles = (Listbox) listitem
                                .getChildren().get(12).getFirstChild()
                                .getFirstChild();
                        Set<Listitem> listado_seleccionados = listbox_roles
                                .getSelectedItems();

                        List<Via_ingreso_rol> listado_via_roles = new ArrayList<Via_ingreso_rol>();

                        for (Listitem listitem_aux : listado_seleccionados) {
                            Via_ingreso_rol via_ingreso_rol = new Via_ingreso_rol();
                            via_ingreso_rol.setCodigo_empresa(codigo_empresa);
                            via_ingreso_rol.setCodigo_rol(listitem_aux
                                    .getValue().toString());
                            via_ingreso_rol
                                    .setCodigo_via_ingreso(configuracion_admision_ingreso
                                            .getVia_ingreso());
                            listado_via_roles.add(via_ingreso_rol);
                        }

                        Listbox listbox_especialidades = (Listbox) listitem
                                .getChildren().get(13).getFirstChild()
                                .getFirstChild();
                        Set<Listitem> listado_seleccionados_aux = listbox_especialidades
                                .getSelectedItems();

                        List<Via_ingreso_especialidad> listado_via_especialidades = new ArrayList<Via_ingreso_especialidad>();

                        for (Listitem listitem_aux : listado_seleccionados_aux) {
                            Via_ingreso_especialidad via_ingreso_especialidad = new Via_ingreso_especialidad();
                            via_ingreso_especialidad
                                    .setCodigo_empresa(codigo_empresa);
                            via_ingreso_especialidad
                                    .setCodigo_especialidad(listitem_aux
                                            .getValue().toString());
                            via_ingreso_especialidad
                                    .setCodigo_via_ingreso(configuracion_admision_ingreso
                                            .getVia_ingreso());
                            listado_via_especialidades
                                    .add(via_ingreso_especialidad);
                        }

                        configuracion_admision_ingreso
                                .setAmbito_realizacion(((Listbox) listitem
                                        .getChildren().get(14).getFirstChild())
                                        .getSelectedItem().getValue()
                                        .toString());

                        Map<String, Object> mapa_bean = new HashMap<String, Object>();
                        mapa_bean.put("configuracion_admision_ingreso",
                                configuracion_admision_ingreso);
                        mapa_bean.put("listado_via_roles", listado_via_roles);
                        mapa_bean.put("listado_via_especialidades",
                                listado_via_especialidades);

                        listado_datos.add(mapa_bean);
                    }

                    Map<String, Object> mapa_datos = new HashMap<String, Object>();
                    mapa_datos.put("codigo_empresa", codigo_empresa);
                    mapa_datos.put("codigo_sucursal", codigo_sucursal);
                    mapa_datos.put("listado_datos", listado_datos);

                    configuracion_admision_ingresoService
                            .guardarDatos(mapa_datos);
                    MensajesUtil.mensajeInformacion("Inforamcion",
                            "Se han guardado los datos satisfactoriamente");
                } else {
                    MensajesUtil.mensajeInformacion("Informacion",
                            "NO hay registros seleccionados para guardar");
                }
            }
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    public void buscarRegistros() {
        String valor = bandboxBusqueda.getValue().trim().toUpperCase();
        if (!valor.isEmpty()) {
            List<Listitem> listado_items = listboxResultado.getItems();
            for (Listitem listitem : listado_items) {
                BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) listitem
                        .getChildren().get(0).getFirstChild();
                if (bandBoxRowMacro.getValue().toUpperCase().contains(valor)) {
                    Clients.scrollIntoView(listitem);
                    MensajesUtil.notificarInformacion("Resultado encontrado",
                            listitem);
                    break;
                }
            }
        }
    }

    public void eliminarDatos(Listitem listitem) throws Exception {
        try {
            listitem.detach();
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
