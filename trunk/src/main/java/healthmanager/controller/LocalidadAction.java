/*
 * localidadAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Localidad;
import healthmanager.modelo.bean.Municipios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class LocalidadAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(LocalidadAction.class);
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
    private Textbox tbxCodigo_localidad;
    @View
    private Textbox tbxLocalidad;
    @View
    private Listbox lbxCodigo;
    @View
    private Listbox lbxCoddep;
    private final String[] idsExcluyentes = {"btCancel", "btGuardar",
        "tbxAccion", "btNew"};

    @Override
    public void init() {
        listarCombos();
    }

    public void listarCombos() {
        listarParameter();
        Utilidades.listarDepartamentos(lbxCoddep, true, getServiceLocator());
        Utilidades.listarMunicipios(lbxCodigo, lbxCoddep, getServiceLocator());
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("codigo_localidad");
        listitem.setLabel("Codigo");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("localidad");
        listitem.setLabel("Nombre");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    public void listarMunicipios() {
        Utilidades.listarMunicipios(lbxCodigo, lbxCoddep, getServiceLocator());
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
        tbxCodigo_localidad.setDisabled(false);
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        tbxCodigo_localidad
                .setStyle("text-transform:uppercase;background-color:white");
        tbxLocalidad
                .setStyle("text-transform:uppercase;background-color:white");
        lbxCoddep.setStyle("background-color:white");
        lbxCodigo.setStyle("background-color:white");

        boolean valida = true;

        if (tbxCodigo_localidad.getText().trim().equals("")) {
            tbxCodigo_localidad
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (tbxLocalidad.getText().trim().equals("")) {
            tbxLocalidad
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (lbxCoddep.getSelectedItem().getValue().equals("")) {
            lbxCoddep.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (lbxCodigo.getSelectedItem() == null) {
            lbxCodigo.setStyle("background-color:#F6BBBE");
            valida = false;
        } else if (lbxCodigo.getSelectedItem().getValue().equals("")) {
            lbxCodigo.setStyle("background-color:#F6BBBE");
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
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");

            List<Localidad> lista_datos = getServiceLocator()
                    .getServicio(GeneralExtraService.class).listar(Localidad.class, parameters);
            rowsResultado.getChildren().clear();

            for (Localidad localidad : lista_datos) {
                rowsResultado.appendChild(crearFilas(localidad, this));
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

        final Localidad localidad = (Localidad) objeto;

        Municipios municipios = new Municipios();
        municipios.setCodigo((localidad != null ? localidad.getCodigo() : ""));
        municipios.setCoddep((localidad != null ? localidad.getCoddep() : ""));
        municipios = getServiceLocator().getMunicipiosService().consultar(
                municipios);

        Departamentos departamentos = new Departamentos();
        departamentos
                .setCodigo((localidad != null ? localidad.getCoddep() : ""));
        departamentos = getServiceLocator().getDepartamentosService()
                .consultar(departamentos);

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(localidad.getCodigo_localidad() + ""));
        fila.appendChild(new Label(localidad.getLocalidad() + ""));
        fila.appendChild(new Label(municipios != null ? municipios.getNombre()
                : ""));
        fila.appendChild(new Label(departamentos != null ? departamentos
                .getNombre() : ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(localidad);
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
                                    eliminarDatos(localidad);
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

                Localidad localidad = new Localidad();
                localidad.setCodigo_localidad(tbxCodigo_localidad.getValue());
                localidad.setLocalidad(tbxLocalidad.getValue());
                localidad.setCodigo(lbxCodigo.getSelectedItem().getValue()
                        .toString());
                localidad.setCoddep(lbxCoddep.getSelectedItem().getValue()
                        .toString());

                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    getServiceLocator().getServicio(GeneralExtraService.class).crear(localidad);
                    accionForm(true, "registrar");
                } else {
                    int result = getServiceLocator().getServicio(GeneralExtraService.class)
                            .actualizar(localidad);
                    if (result == 0) {
                        throw new Exception(
                                "Lo sentimos pero los datos a modificar no se encuentran en base de datos");
                    }
                }

                MensajesUtil.mensajeInformacion("Informacion ..",
                        "Los datos se guardaron satisfactoriamente");
            }

        } catch (Exception e) {
//			MensajesUtil.mensajeError(e, "", this);
            Messagebox.show(e.getMessage(), "Informacion", Messagebox.OK,
                    Messagebox.ERROR);
        }

    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Localidad localidad = (Localidad) obj;
        try {
            tbxCodigo_localidad.setValue(localidad.getCodigo_localidad());
            tbxLocalidad.setValue(localidad.getLocalidad());

            for (int i = 0; i < lbxCoddep.getItemCount(); i++) {
                Listitem listitem = lbxCoddep.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(localidad.getCoddep())) {
                    listitem.setSelected(true);
                    i = lbxCoddep.getItemCount();
                }
            }
            Utilidades.listarMunicipios(lbxCodigo, lbxCoddep,
                    getServiceLocator());

            for (int i = 0; i < lbxCodigo.getItemCount(); i++) {
                Listitem listitem = lbxCodigo.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(localidad.getCodigo())) {
                    listitem.setSelected(true);
                    i = lbxCodigo.getItemCount();
                }
            }

            tbxCodigo_localidad.setDisabled(true);
            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Localidad localidad = (Localidad) obj;
        try {
            int result = getServiceLocator().getServicio(GeneralExtraService.class).eliminar(
                    localidad);
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
        }
    }
}
