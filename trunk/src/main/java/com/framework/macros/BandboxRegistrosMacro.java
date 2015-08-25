package com.framework.macros;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.BandboxRegistrosIMG.ISeleccionarItem;
import com.framework.macros.util.Parametros_busqueda;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

/**
 * Controlador del macrocomponente bandboxEntidad
 *
 * @author Dario Perez
 *
 */
public class BandboxRegistrosMacro extends Bandbox implements AfterCompose {

    public static final String PARAMETRO_CUALQUIERA = "paramTodo";
    public static final String ETIQUETA_CUALQUIERA = "Cualquier parametro";

    private static final long serialVersionUID = -7698822638205299258L;

    private static Logger log = Logger.getLogger(BandboxRegistrosMacro.class);

    private Object bandboxRegistrosIMG;

    private Textbox mcTextboxValor;
    private Listbox mcListboxResultados;
    private Textbox mcTextboxColumnas;

    private Textbox mcTextboxInformacion;
    private Toolbarbutton mcButtonLimpiar;

    private Object registroSeleccionado;

    private Object object;

    private boolean mostrarVacio = false;
    private String mensajeVacio;

    private ISeleccionarItem seleccionarItem;

    private Listbox mcLbxParametros;

    private boolean trabajarConParametros = false;

    @Override
    public void afterCompose() {
        try {
            cargarComponentes();
            inicializarEventos();
            agregarColumnas(mcTextboxColumnas.getValue());
            inicializarEventoSeguridad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inicializarEventoSeguridad() {
		// validarLimpieza(Events.ON_BLUR);
        // validarLimpieza(Events.ON_CHANGE);
        // validarLimpieza(Events.ON_CHANGING);
    }

    private void validarLimpieza(String metodo) {
        addEventListener(metodo, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                if (getRegistroSeleccionado() != null) {
                    limpiarSeleccion(true);
                }
            }
        });
    }

    public void inicializar(Textbox textboxInformacion,
            Toolbarbutton buttonLimpiar) {

        if (textboxInformacion != null) {
            setTextboxInformacion(textboxInformacion);
        }
        if (buttonLimpiar != null) {
            setButtonLimpiar(buttonLimpiar);
            buttonLimpiar.setVisible(false);
            mcButtonLimpiar.addEventListener(Events.ON_CLICK,
                    new EventListener<Event>() {

                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            limpiarSeleccion(true);
                            mcButtonLimpiar.setVisible(false);
                        }

                    });
        }

        mcLbxParametros.setVisible(trabajarConParametros);

    }

    private void cargarComponentes() {
        mcTextboxValor = (Textbox) this.getFellow("mcTextboxValor");
        mcListboxResultados = (Listbox) this.getFellow("mcListboxResultados");
        mcTextboxColumnas = (Textbox) this.getFellow("mcTextboxColumnas");
        mcLbxParametros = (Listbox) this.getFellow("mcLbxParametros");
    }

    public void agregarColumnas(String columnas) {
        mcListboxResultados.getListhead().getChildren().clear();
        for (String valor : columnas.split("\\|")) {
            Listheader listheader = new Listheader();
            if (valor.contains("#")) {
                String vals[] = valor.split("\\#");
                listheader.setLabel(vals[0]);
                if (vals.length > 1) {
                    listheader.setWidth(vals[1]);
                }
            } else {
                listheader.setLabel(valor);
            }
            mcListboxResultados.getListhead().appendChild(listheader);
        }
    }

    @SuppressWarnings("unchecked")
    public void onConsultarRegistros(boolean enter_bandbox) {
        try {
            if (enter_bandbox) {
                mcTextboxValor.setValue(this.getValue());
            }
            String valorBusqueda = mcTextboxValor.getValue().toLowerCase();
            mcListboxResultados.getItems().clear();
            Map<String, Object> parametros = new HashMap<String, Object>();

            List<Object> listado = ((BandboxRegistrosIMG<Object>) bandboxRegistrosIMG)
                    .listarRegistros(valorBusqueda, parametros);

            if (listado.isEmpty()) {
                Notificaciones.mostrarNotificacionInformacion("informacion",
                        "No a devuelto ningun registro", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
            } else if (listado.size() == 1) {
                seleccionarRegistro(listado.get(0));
            } else {
                for (Object registro : listado) {
                    Listitem listitem = new Listitem();
                    ((BandboxRegistrosIMG<Object>) getBandboxRegistrosIMG())
                            .renderListitem(listitem, registro);
                    listitem.setValue(registro);
                    mcListboxResultados.appendChild(listitem);
                }
                setOpen(true);

                if (mostrarVacio && listado.isEmpty()) {
                    MensajesUtil.mensajeInformacion("Alerta", mensajeVacio);
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void seleccionarRegistro(Object registro) {
        if (registro != null) {
			// Esto se realiza por que se presentan veces que
            // se consulta el valor en el mismo metodo seleccion
            registroSeleccionado = registro;
            boolean select = ((BandboxRegistrosIMG<Object>) bandboxRegistrosIMG)
                    .seleccionarRegistro(
                            this,
                            getTextboxInformacion() != null ? getTextboxInformacion()
                            : new Textbox(), registro);
            if (select) {
                this.close();
                mcListboxResultados.getItems().clear();
                mcTextboxValor.setValue("");
                if (mcButtonLimpiar != null) {
                    mcButtonLimpiar.setVisible(true);
                }
                if (mcTextboxValor.getParent() != null) {
                    mcTextboxValor.getParent().invalidate();
                }
                if (seleccionarItem != null) {
                    seleccionarItem.accion(registro);
                }
            } else {
                registroSeleccionado = null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void onSeleccionarRegistro() {
        try {
            Listitem listitem = mcListboxResultados.getSelectedItem();
            Object registro = listitem != null ? listitem.getValue() : null;
            seleccionarRegistro(registro);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public void limpiarSeleccion(boolean limpiarIMG) {
        try {
            FormularioUtil.limpiarComponentesRestricciones(this);
            if (limpiarIMG) {
                ((BandboxRegistrosIMG<Object>) bandboxRegistrosIMG)
                        .limpiarSeleccion(this);
            }
            this.setValue("");
            registroSeleccionado = null;
            if (getTextboxInformacion() != null) {
                getTextboxInformacion().setValue("");
            }
            if (getButtonLimpiar() != null) {
                getButtonLimpiar().setVisible(false);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Metodo para seleccionar un registro en el bandbox de forma externa
     *
     * @param registro valor del registro seleccionado
     * @param codigo valor del bandbox
     * @param info valor del textbox de informacion
     */
    public void seleccionarRegistro(Object registro, String codigo, String info) {
        registroSeleccionado = registro;
        this.setValue(codigo);
        if (getTextboxInformacion() != null) {
            getTextboxInformacion().setValue(info);
        }
        if (getButtonLimpiar() != null) {
            getButtonLimpiar().setVisible(true);
        }
    }

    /**
     * Metodo que se ejecuta cuando se presiona la tecla Enter sobre el bandbox
     * o cuando se da click sobre el boton de busqueda
     */
    public void onConsultarEvent() {
        try {
            onConsultarRegistros(false);
        } catch (Exception e) {
        }
    }

    /**
     * Metodo que se ejecuta cuando se presiona la tecla Enter sobre el bandbox.
     * Se pone el foco en el textbox de criterio de busqueda y tambien se le
     * pasa el valor que se ingresó al bandbox. se parametriza el control de
     * paginacion con los nuevos parametros. y por ultimo se llama al metodo
     * {@link #onConsultar(String, boolean)}
     */
    public void onOkBandboxEvent() {
        try {
            onConsultarRegistros(true);
        } catch (Exception e) {

        }
    }

    public void inicializarEventos() {
        this.addEventListener(Events.ON_OK, new EventListener<Event>() {

            @Override
            public void onEvent(Event arg0) throws Exception {
                // if (isOnOK) {
                onOkBandboxEvent();
                // }

            }
        });

        this.addEventListener(Events.ON_OPEN, new EventListener<OpenEvent>() {

            @Override
            public void onEvent(OpenEvent arg0) throws Exception {

                if (arg0.isOpen()) {
                    onOpenBandboxEvent();
                }

            }
        });

    }

    /**
     * Metodo que se ejecuta cuando se abre el bandbox. Se selecciona el radio
     * visible que es el que indica el parametro o criterio de busqueda. Se
     * limpian los demas componentes y por ultimo se parametriza el control de
     * paginacion
     */
    public void onOpenBandboxEvent() {
        try {
            mcTextboxValor.setValue(this.getValue());
            mcTextboxValor.setFocus(true);
            mcListboxResultados.getItems().clear();
        } catch (Exception e) {

        }
    }

    public void setBandboxRegistrosIMG(Object bandboxRegistrosIMG) {
        this.bandboxRegistrosIMG = bandboxRegistrosIMG;
    }

    public Object getBandboxRegistrosIMG() {
        return bandboxRegistrosIMG;
    }

    public void setTextboxInformacion(Textbox textboxInformacion) {
        this.mcTextboxInformacion = textboxInformacion;
    }

    public Textbox getTextboxInformacion() {
        return mcTextboxInformacion;
    }

    public <T> T getRegistroSeleccionado() {
        return (T) registroSeleccionado;
    }

    public void setButtonLimpiar(Toolbarbutton buttonLimpiar) {
        this.mcButtonLimpiar = buttonLimpiar;
    }

    public Button getButtonLimpiar() {
        return mcButtonLimpiar;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public boolean isMostrarVacio() {
        return mostrarVacio;
    }

    public void setMostrarVacio(boolean mostrarVacio) {
        this.mostrarVacio = mostrarVacio;
    }

    public String getMensajeVacio() {
        return mensajeVacio;
    }

    public void setMensajeVacio(String mensajeVacio) {
        this.mensajeVacio = mensajeVacio;
    }

    public ISeleccionarItem getSeleccionarItem() {
        return seleccionarItem;
    }

    public void setSeleccionarItem(ISeleccionarItem seleccionarItem) {
        this.seleccionarItem = seleccionarItem;
    }

    public void accederAutomatico() {
        limpiarSeleccion(true);
        setFocus(true);
    }

    public boolean getTrabajarConParametros() {
        return trabajarConParametros;
    }

    public void setTrabajarConParametros(boolean trabajarConParametros) {
        this.trabajarConParametros = trabajarConParametros;
    }

    public Object getParametroSeleccionado() {
        if (mcLbxParametros.getSelectedItem() != null) {
            return mcLbxParametros.getSelectedItem().getValue();
        }
        return PARAMETRO_CUALQUIERA;
    }

    public void listarParametros(List<Parametros_busqueda> listado_parametros,
            boolean cualquier_parametro) {
        mcLbxParametros.getItems().clear();
        if (listado_parametros != null) {
            for (Parametros_busqueda parametros_busqueda : listado_parametros) {
                Listitem listitem = new Listitem(
                        parametros_busqueda.getEtiqueta(),
                        parametros_busqueda.getValor());
                mcLbxParametros.appendChild(listitem);
            }
        }
        if (cualquier_parametro) {
            mcLbxParametros.appendItem(ETIQUETA_CUALQUIERA,
                    PARAMETRO_CUALQUIERA);
        }
        if (mcLbxParametros.getItemCount() > 0) {
            mcLbxParametros.setSelectedIndex(0);
        }
    }

}
