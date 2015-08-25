package com.framework.macros;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Footer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.impl.XulElement;

import com.framework.macros.impl.ResultadoPaginadoMacroIMG;

public class ResultadoPaginadoMacro {

    public static Logger log = Logger.getLogger(ResultadoPaginadoMacro.class);

    private static final Integer TAMANIO_PAGINACION = 20;

    private Component componente_referencia;

    private Hlayout hlayoutFooter;
    private PagingControl pagingControl;

    private ResultadoPaginadoMacroIMG parametrizacion;

    private Map<String, Object> parametros_busqueda;

    private Label labelPaginas;
    private Spinner spinnerPaginas;

    public void incicializar(ResultadoPaginadoMacroIMG parametrizacion,
            Component componente_ref, int columnas) {
        incicializar(parametrizacion, componente_ref, columnas, false);
    }

    public void incicializar(ResultadoPaginadoMacroIMG parametrizacion,
            Component componente_ref, int columnas, boolean visible) {
        this.parametrizacion = parametrizacion;
        this.componente_referencia = componente_ref;
        this.pagingControl = new PagingControl();
        this.hlayoutFooter = new Hlayout();
        this.hlayoutFooter.setValign("middle");
        this.labelPaginas = new Label("Pag. disponibles");
        this.spinnerPaginas = new Spinner(10);
        this.spinnerPaginas.addEventListener(Events.ON_CHANGE,
                new EventListener<Event>() {

                    @Override
                    public void onEvent(Event arg0) throws Exception {
                        pagingControl.setTotalSize(TAMANIO_PAGINACION
                                * (spinnerPaginas.getValue() != null ? spinnerPaginas
                                .getValue() : 1));
                    }
                });

        if (componente_referencia instanceof Grid) {
            Foot foot = ((Grid) componente_referencia).getFoot();
            Footer footer = null;
            if (foot != null) {
                if (!foot.getChildren().isEmpty()) {
                    footer = (Footer) foot.getChildren().get(0);
                } else {
                    footer = new Footer();
                    footer.setSpan(columnas);
                    foot.appendChild(footer);
                }
            } else {
                foot = new Foot();
                ((Grid) componente_referencia).appendChild(foot);
                footer = new Footer();
                footer.setSpan(columnas);
                foot.appendChild(footer);
            }

            hlayoutFooter.appendChild(pagingControl);
            hlayoutFooter.appendChild(labelPaginas);
            hlayoutFooter.appendChild(spinnerPaginas);
            footer.appendChild(hlayoutFooter);
        } else if (componente_referencia instanceof Listbox) {
            Listfoot foot = ((Listbox) componente_referencia).getListfoot();
            if (foot != null) {
                foot.getChildren().clear();
            } else {
                foot = new Listfoot();
                ((Listbox) componente_referencia).appendChild(foot);
            }
            Listfooter footer = new Listfooter();
            footer.setSpan(columnas);
            hlayoutFooter.appendChild(pagingControl);
            hlayoutFooter.appendChild(labelPaginas);
            hlayoutFooter.appendChild(spinnerPaginas);
            footer.appendChild(hlayoutFooter);
            foot.appendChild(footer);
        }
        hlayoutFooter.setVisible(visible);
    }

    public void buscarDatos(Map<String, Object> parametros) throws Exception {
        this.parametros_busqueda = parametros;
        pagingControl.setActivePage(0);
        pagingControl.ejecutarProceso(pagingControl.getActivePage(), true);
        hlayoutFooter.invalidate();
    }

    public Integer totalRegistros() {
        return pagingControl != null ? pagingControl.getTotalSize() : 0;
    }

    public Map<String, Object> getParametrosBusqueda() {
        Map<String, Object> parametros = new HashMap<String, Object>(
                parametros_busqueda);
        parametros.remove("limite_paginado");
        return parametros;
    }

    public void buscarDatosPaginas(Map<String, Object> parametros,
            Integer paginas) throws Exception {
        try {
            if (pagingControl != null) {
                if (paginas != null && paginas.intValue() > 0) {
                    this.parametros_busqueda = parametros;
                    pagingControl.setTotalSize(paginas
                            * pagingControl.getPageSize());
                    pagingControl.setDetailed(false);
                    if (parametros_busqueda != null) {
                        pagingControl.setActivePage(0);
                        pagingControl.ejecutarProceso(
                                pagingControl.getActivePage(), true);
                    }
                } else {
                    pagingControl.setDetailed(true);
                    buscarDatos(parametros);
                }
            } else {
                buscarDatos(parametros);
            }
        } catch (Exception e) {
            log.error("Error en Paginas", e);
        }
    }

    private class PagingControl extends Paging implements
            EventListener<PagingEvent> {

        public PagingControl() {
            setVisible(true);
            setDetailed(false);
            this.addEventListener("onPaging", this);
            this.setPageSize(TAMANIO_PAGINACION);
        }

        @Override
        public void onEvent(PagingEvent event) throws Exception {
            try {
                ejecutarProceso(event.getActivePage(), false);
            } catch (Exception e) {
                log.error("Evento de Paginación", e);
            }
        }

        public void ejecutarProceso(int pagina, boolean cons_total)
                throws Exception {
            // verificamos si por detalle
            if (cons_total) {
                setTotalSize(TAMANIO_PAGINACION
                        * (spinnerPaginas.getValue() != null ? spinnerPaginas
                        .getValue() : 1));
            }
            List<?> listado = ejecutarConsultaPaginada(pagina);
            // verificamos total de pagina para habilitar
            if (listado.size() < TAMANIO_PAGINACION && pagina == 0) {
                hlayoutFooter.setVisible(false);
            } else {
                hlayoutFooter.setVisible(true);
            }
            mostrarPaginaComponente(listado);
        }

        private void mostrarPaginaComponente(List<?> listado) throws Exception {
            if (componente_referencia instanceof Grid) {
                Rows rows = ((Grid) componente_referencia).getRows();
                if (rows != null) {
                    rows.getChildren().clear();
                } else {
                    rows = new Rows();
                    ((Grid) componente_referencia).appendChild(rows);
                }
                for (Object dato : listado) {
                    XulElement fila = parametrizacion.renderizar(dato);
                    if (fila != null) {
                        rows.appendChild(fila);
                    }
                }
                ((Grid) componente_referencia).getFoot().invalidate();
            } else if (componente_referencia instanceof Listbox) {
                ((Listbox) componente_referencia).getItems().clear();
                for (Object dato : listado) {
                    XulElement fila = parametrizacion.renderizar(dato);
                    if (fila != null) {
                        ((Listbox) componente_referencia).appendChild(fila);
                    }
                }
            }
        }

        private List<?> ejecutarConsultaPaginada(int pagina) {
            Integer inicio = pagina * getPageSize();
            String limite = "limit " + getPageSize() + " offset " + inicio;
            parametros_busqueda.put("limite_paginado", limite);
            return parametrizacion.listarResultados(parametros_busqueda);
        }

    }

}
