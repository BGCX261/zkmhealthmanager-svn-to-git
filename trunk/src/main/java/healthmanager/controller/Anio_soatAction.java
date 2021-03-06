/*
 * anio_soatAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Anio_soat;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Doublebox;
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

public class Anio_soatAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Anio_soatAction.class);
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
    private Listbox lbxAnio;
    @View
    private Doublebox dbxValor_anio;
    private final String[] idsExcluyentes = {};

    @Override
    public void init() {
        listarCombos();
    }

    public void listarCombos() {
        listarParameter();
        Utilidades.listarAnios(lbxAnio, 5);
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("anio");
        listitem.setLabel("año");
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
        } else {
            // buscarDatos();
            limpiarDatos();
        }
        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        Utilidades.seleccionarAnio(lbxAnio);
        lbxAnio.setDisabled(false);
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        lbxAnio.setStyle("background-color:white");
        dbxValor_anio.setStyle("background-color:white");

        boolean valida = true;

        String mensaje = "Los campos marcados con (*) son obligatorios";

        if (lbxAnio.getSelectedItem().getValue().toString().trim().equals("")) {
            lbxAnio.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (dbxValor_anio.getText().trim().equals("")) {
            dbxValor_anio.setStyle("background-color:#F6BBBE");
            valida = false;
        }

        if (dbxValor_anio.getValue() < 0) {
            dbxValor_anio.setStyle("background-color:#F6BBBE");
            valida = false;
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
            parameters.put("limit", "limit 25 offset 0");

            List<Anio_soat> lista_datos = getServiceLocator()
                    .getServicio(GeneralExtraService.class).listar(Anio_soat.class, parameters);
            rowsResultado.getChildren().clear();

            for (Anio_soat anio_soat : lista_datos) {
                rowsResultado.appendChild(crearFilas(anio_soat, this));
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

        final Anio_soat anio_soat = (Anio_soat) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(anio_soat.getAnio() + ""));
        fila.appendChild(new Label(Utilidades.formatDecimal(
                anio_soat.getValor_anio(), "#,##0.00")
                + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(anio_soat);
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
                                    eliminarDatos(anio_soat);
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

                Anio_soat anio_soat = new Anio_soat();
                anio_soat.setAnio(lbxAnio.getSelectedItem().getValue()
                        .toString());
                anio_soat
                        .setValor_anio((dbxValor_anio.getValue() != null ? dbxValor_anio
                                .getValue() : 0.00));
                anio_soat.setCreacion_date(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                anio_soat.setUltimo_update(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                anio_soat.setCreacion_user(usuarios.getCodigo().toString());
                anio_soat.setUltimo_user(usuarios.getCodigo().toString());

                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    getServiceLocator().getServicio(GeneralExtraService.class).crear(anio_soat);
                    accionForm(true, "registrar");
                } else {
                    int result = getServiceLocator().getServicio(GeneralExtraService.class)
                            .actualizar(anio_soat);
                    if (result == 0) {
                        throw new Exception(
                                "Lo sentimos pero los datos a modificar no se encuentran en base de datos");
                    }
                }

                MensajesUtil.mensajeInformacion("Informacion ..",
                        "Los datos se guardaron satisfactoriamente");
            }

        } catch (Exception e) {
            Messagebox.show(e.getMessage(), "information", Messagebox.OK,
                    Messagebox.ERROR);
        }

    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Anio_soat anio_soat = (Anio_soat) obj;
        try {
            for (int i = 0; i < lbxAnio.getItemCount(); i++) {
                Listitem listitem = lbxAnio.getItemAtIndex(i);
                if (listitem.getValue().toString().equals(anio_soat.getAnio())) {
                    listitem.setSelected(true);
                    i = lbxAnio.getItemCount();
                }
            }
            dbxValor_anio.setValue(anio_soat.getValor_anio());

            lbxAnio.setDisabled(true);

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Anio_soat anio_soat = (Anio_soat) obj;
        try {
            int result = getServiceLocator().getServicio(GeneralExtraService.class).eliminar(
                    anio_soat);
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
