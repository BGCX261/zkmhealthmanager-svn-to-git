/**
 *
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anio_soat;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Copago_estrato;
import healthmanager.modelo.bean.Descuentos_laboratorios;
import healthmanager.modelo.bean.Maestro_manual;
import healthmanager.modelo.bean.Manuales_procedimientos;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Tarifas_procedimientos;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Contratos_procedimientos_exService;
import healthmanager.modelo.service.Copago_estratoService;
import healthmanager.modelo.service.Descuentos_laboratoriosService;
import healthmanager.modelo.service.Manuales_procedimientosService;
import healthmanager.modelo.service.Plantilla_pypService;
import healthmanager.modelo.service.ProcedimientosService;
import healthmanager.modelo.service.Tarifas_procedimientosService;
import healthmanager.modelo.service.VacunasService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * @author ferney
 *
 */
public class OpenProcedimientosAction extends GeneralComposer {

    private static Logger log = Logger
            .getLogger(OpenProcedimientosAction.class);
    private static final long serialVersionUID = -9145887024839938515L;

    @WireVariable
    private ContratosService contratosService;
    @WireVariable
    private Configuracion_admision_ingresoService configuracion_admision_ingresoService;
    @WireVariable
    private Plantilla_pypService plantilla_pypService;
    @WireVariable
    private VacunasService vacunasService;
    @WireVariable
    private Contratos_procedimientos_exService contratos_procedimientos_exService;
    @WireVariable
    private ProcedimientosService procedimientosService;
    @WireVariable
    private Manuales_procedimientosService manuales_procedimientosService;
    @WireVariable
    private Tarifas_procedimientosService tarifas_procedimientosService;
    @WireVariable
    private Descuentos_laboratoriosService descuentos_laboratoriosService;
    @WireVariable
    private Copago_estratoService copago_estratoService;
    @WireVariable
    private GeneralExtraService generalExtraService;

	// Componentes //
    // private Vbox vboxBusqueda;
    @View
    private Textbox tbxValue;
    @View
    private Listbox listboxResultado;
	// private Vbox vboxResultado;
    // private Listbox lbxDetalle;
    @View
    private Listbox lbxTipo_procedimiento;

    @View
    private Listheader columntipo;
    @View
    private Listheader columnporc;
    @View
    private Listheader columnparticular;
    @View
    private Listheader columnsexo;
    @View
    private Listheader columnlinf;
    @View
    private Listheader columnlins;

    @View
    private Auxheader auxheaderResultado;

    private List<Procedimientos> lista_pcd;
    private Map parametrosAction;

    private Window action;
    // private ZKWindow zkwindow;

    private ISeleccionarComponente seleccionar_componente;

    private Map<String, String> seleccionados = new HashMap<String, String>();
    private Manuales_tarifarios manual_tarifario;
    private String via_ingreso;
    private Contratos contratos;

    private Maestro_manual maestro_manual;
    private Admision admision;

    public void initOpenProcedimiento() throws Exception {
		// HttpServletRequest request = (HttpServletRequest)
        // Executions.getCurrent().getNativeRequest();
        try {
            listboxResultado.setAttribute("org.zkoss.zul.listbox.rod", true);
            // lbxDetalle.getItems().clear();

            if (getParent() instanceof Action) {
                action = (Action) getParent();
            } else if (getParent() instanceof ZKWindow) {
                action = (ZKWindow) getParent();
            }

            parametrosAction = Executions.getCurrent().getArg();
            if (parametrosAction == null) {
                parametrosAction = new HashMap();
            }

            /* desavilitamos campo */
            if (parametrosAction.get("contratos") != null) {
                columntipo.setVisible(false);
                auxheaderResultado
                        .setColspan(auxheaderResultado.getColspan() - 1);
                columnporc.setVisible(false);
                auxheaderResultado
                        .setColspan(auxheaderResultado.getColspan() - 1);
                columnparticular.setVisible(false);
                auxheaderResultado
                        .setColspan(auxheaderResultado.getColspan() - 1);
                columnsexo.setVisible(false);
                auxheaderResultado
                        .setColspan(auxheaderResultado.getColspan() - 1);
                columnlinf.setVisible(false);
                auxheaderResultado
                        .setColspan(auxheaderResultado.getColspan() - 1);
                columnlins.setVisible(false);
                auxheaderResultado
                        .setColspan(auxheaderResultado.getColspan() - 1);
            }

            contratos = new Contratos();
            contratos.setCodigo_empresa(empresa.getCodigo_empresa());
            contratos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            contratos.setCodigo_administradora((String) parametrosAction
                    .get("codigo_administradora"));
            contratos.setId_plan((String) parametrosAction.get("id_plan"));
            contratos = contratosService.consultar(contratos);

            manual_tarifario = (Manuales_tarifarios) parametrosAction
                    .get("manual_tarifario");

            maestro_manual = (Maestro_manual) parametrosAction
                    .get("maestro_manual");

            admision = (Admision) parametrosAction.get("admision");

            via_ingreso = (String) parametrosAction.get("via_ingreso");

            if (via_ingreso != null
                    && (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
                    || via_ingreso.equals(IVias_ingreso.URGENCIA)
                    || via_ingreso
                    .equals(IVias_ingreso.HOSPITALIZACIONES)
                    || via_ingreso.equals(IVias_ingreso.SALUD_ORAL) || via_ingreso
                    .equals(IVias_ingreso.URGENCIA_ODONTOLOGICO))) {
                lbxTipo_procedimiento.setVisible(false);
                getFellow("lbTipo").setVisible(false);
                lbxTipo_procedimiento.setSelectedIndex(0);
            }

            String ocultar_filtro_procedimiento = (String) parametrosAction
                    .get("ocultar_filtro_procedimiento");

            if (ocultar_filtro_procedimiento != null) {
                lbxTipo_procedimiento.setVisible(false);
                getFellow("lbTipo").setVisible(false);
                lbxTipo_procedimiento.setSelectedIndex(0);
            }

            seleccionados.clear();
            if (parametrosAction.get("seleccionados") != null) {
                List<String> lista_seleccionado = (List<String>) parametrosAction
                        .get("seleccionados");
                for (String seleccion : lista_seleccionado) {
                    seleccionados.put(seleccion, seleccion);
                }
            }

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
                    Messagebox.ERROR);
        }
    }

    // Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String value = tbxValue.getValue().toUpperCase().trim();
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("paramTodo", "");
            parameters.put("value", value);

            boolean isVacunacion = (via_ingreso != null && via_ingreso
                    .equals(IVias_ingreso.VIA_VACUNACION));

            if (parametrosAction.get("clasificacion") != null) {
                parameters.put("clasificacion",
                        parametrosAction.get("clasificacion"));
            }

            if (lbxTipo_procedimiento.isVisible()) {
                parameters.put("codigo_tipo_procedimiento",
                        lbxTipo_procedimiento.getSelectedItem().getValue()
                        .toString().trim());
            }
            /**
             * Este parametro me permite filtrar los procedimeintos
			 *
             */
            if (parametrosAction.containsKey("codigo_tipo_procedimiento")) {
                parameters.put("codigo_tipo_procedimiento",
                        (String) parametrosAction
                        .get("codigo_tipo_procedimiento"));
            }

            if (parametrosAction.containsKey("codigo_tipo_procedimiento_in")) {
                parameters.put("codigo_tipo_procedimiento_in",
                        parametrosAction.get("codigo_tipo_procedimiento_in"));
            }

            if (parametrosAction.containsKey("consulta")) {
                parameters.put("consulta",
                        (String) parametrosAction.get("consulta"));
            }
            if (parametrosAction.containsKey("quirurgico")) {
                parameters.put("quirurgico",
                        (String) parametrosAction.get("quirurgico"));
            }

            if (parametrosAction.containsKey("excluir_cups")) {
                parameters.put("excluir_cups",
                        parametrosAction.get("excluir_cups"));
            }

            if (via_ingreso != null
                    && (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)
                    || via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
                    || via_ingreso.equals(IVias_ingreso.SALUD_ORAL) || via_ingreso
                    .equals(IVias_ingreso.URGENCIA_ODONTOLOGICO))) {

                Admision admision_busqueda = admision;
                if (via_ingreso.equals(IVias_ingreso.URGENCIA_ODONTOLOGICO)
                        && admision != null) {
                    admision_busqueda = Res.clonar(admision);
                    admision_busqueda
                            .setVia_ingreso(IVias_ingreso.ODONTOLOGIA2);
                }

				// parameters.put("tipo_procedimiento",
                // IClasificacionProcedimiento.ODONTOLOGIACOS);
                parameters.put("consulta", "N");
                if (admision_busqueda != null) {
                    List<String> servicios_contratados = ServiciosDisponiblesUtils
                            .getFiltroProcedimientos(admision_busqueda, true);
                    if (!servicios_contratados.isEmpty()) {
                        parametrosAction.put("listado_cups_contratados",
                                servicios_contratados);
                    }
                }
            } else {
                Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
                configuracion_admision_ingreso
                        .setCodigo_empresa(codigo_empresa);
                configuracion_admision_ingreso
                        .setCodigo_sucursal(codigo_sucursal);
                configuracion_admision_ingreso.setVia_ingreso(via_ingreso);

                if (via_ingreso == null) {
                    configuracion_admision_ingreso = Configuracion_admision_ingreso
                            .getConfiguracionVacia();
                } else {
                    configuracion_admision_ingreso = configuracion_admision_ingresoService
                            .consultar(configuracion_admision_ingreso);

                    if (configuracion_admision_ingreso == null) {
                        throw new ValidacionRunTimeException(
                                "La via de ingreso "
                                + via_ingreso
                                + " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
                    }
                }

                if (parametros_empresa != null) {
                    if (via_ingreso != null
                            && parametros_empresa
                            .getFiltrar_actividades_ordenamiento_programas()
                            .equals("S")
                            && configuracion_admision_ingreso.getEs_pyp()) {
                        String servicios[] = ServiciosDisponiblesUtils
                                .getServiciosViaIngresoAux(sucursal,
                                        via_ingreso);
                        if (servicios != null) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("area_intervencion_in", servicios);
                            List<Plantilla_pyp> plantilla_pyps = plantilla_pypService
                                    .listar(map);
                            List<String> codigos_actividades = new ArrayList<String>();
                            for (Plantilla_pyp plantilla_pyp : plantilla_pyps) {
                                List<String> codigos = Utilidades
                                        .validarCodigo(plantilla_pyp
                                                .getCodigo_pdc());
                                for (String codig : codigos) {
                                    codigos_actividades.add(codig);
                                }
                            }
                            if (!codigos_actividades.isEmpty()) {
                                parameters.put("actividades_cups",
                                        codigos_actividades);
                            }
                        }
                    }
                }
            }

            if (parametrosAction.containsKey("pyp") && !isVacunacion) {
                parameters.put("pyp", (String) parametrosAction.get("pyp"));
            } else if (isVacunacion) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("codigo_empresa", codigo_empresa);
                map.put("codigo_sucursal", codigo_sucursal);
                List<String> codigos_vacunas = vacunasService
                        .getCodigoProcedimientosVacunas(map);
                if (!codigos_vacunas.isEmpty()) {
                    parameters.put("actividades", codigos_vacunas);
                }
            }

            if (parametrosAction.containsKey("restringido")) {
                String restringido = "N";
                Contratos contratos = new Contratos();
                contratos.setCodigo_empresa(empresa.getCodigo_empresa());
                contratos.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                contratos.setCodigo_administradora((String) parametrosAction
                        .get("codigo_administradora"));
                contratos.setId_plan((String) parametrosAction.get("id_plan"));
                // //System.Out.Println("Aqui pcd antes: "+contratos);
                contratos = contratosService.consultar(contratos);
                if (contratos != null && manual_tarifario != null) {
                    if (manual_tarifario.getRestriccion()) {
                        restringido = "S";
                    }
                }
                if (restringido.equals("S")) {
                    parameters.put("codigo_empresa",
                            (String) parametrosAction.get("codigo_empresa"));
                    parameters.put("codigo_sucursal",
                            (String) parametrosAction.get("codigo_sucursal"));
                    parameters.put("codigo_administradora",
                            (String) parametrosAction
                            .get("codigo_administradora"));
                    parameters.put("id_plan",
                            (String) parametrosAction.get("id_plan"));
                    parameters.put("restringido",
                            (String) parametrosAction.get("restringido"));
                }
            }

            if (parametrosAction.containsKey("listado_cups_contratados")) {
                List<String> codigos_actividades = (List<String>) parameters
                        .get("actividades_cups");
                List<String> listado_cups_contratados = (List<String>) parametrosAction
                        .get("listado_cups_contratados");
                if (codigos_actividades != null) {
                    listado_cups_contratados.addAll(codigos_actividades);
                }
                parameters.put("listado_cups_contratados",
                        listado_cups_contratados);
                parameters.remove("actividades_cups");
            }

            if (parametrosAction.containsKey("tipo_procedimiento")) {
                parameters.put("tipo_procedimiento",
                        parametrosAction.get("tipo_procedimiento"));
            }

            if (parametrosAction.containsKey("tipo_procedimiento_bus")) {
                parameters.put("tipo_procedimiento_bus",
                        parametrosAction.get("tipo_procedimiento_bus"));
            }

            List<String> listado_cups_excluyentes = contratos_procedimientos_exService
                    .listar_cups_ex(parameters);

            if (!listado_cups_excluyentes.isEmpty()) {
                parameters.put("listado_cups_excluyentes",
                        listado_cups_excluyentes);
            }

            procedimientosService.setLimit("limit 25 offset 0");

            lista_pcd = procedimientosService.listar(parameters);

            log.info("Parametros de busqueda de los procedimientos ");
            for (String key_mapa : parameters.keySet()) {
                log.info(key_mapa + " ===> " + parameters.get(key_mapa));
            }

            log.info("lista_pcd ==> " + lista_pcd.size());

            listboxResultado.getItems().clear();

            int i = 0;

            Maestro_manual maestro_manual_aux = null;

            if (manual_tarifario != null) {
                maestro_manual_aux = manual_tarifario.getMaestro_manual();
            } else if (maestro_manual != null) {
                maestro_manual_aux = maestro_manual;
            }

            for (Procedimientos pcd : lista_pcd) {
                if (maestro_manual_aux != null) {
                    Manuales_procedimientos manuales_procedimientos = new Manuales_procedimientos();
                    manuales_procedimientos.setId_manual(maestro_manual_aux
                            .getId_manual());
                    manuales_procedimientos.setId_procedimiento(pcd
                            .getId_procedimiento());
                    manuales_procedimientos = manuales_procedimientosService
                            .consultar(manuales_procedimientos);
                    if (manuales_procedimientos != null) {
                        listboxResultado.appendChild(crearFilas(
                                manuales_procedimientos, pcd, this, i));
                        i++;
                    }
                } else {
                    listboxResultado
                            .appendChild(crearFilas(null, pcd, this, i));
                    i++;
                }
            }

            listboxResultado.setVisible(true);
            listboxResultado.setMold("paging");
            listboxResultado.setPageSize(20);

            listboxResultado.applyProperties();
            listboxResultado.invalidate();
            listboxResultado.setVisible(true);

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public Listitem crearFilas(Manuales_procedimientos manuales_procedimientos,
            Object objeto, Component componente, int index) throws Exception {
        final Listitem fila = new Listitem();

        final Procedimientos pro = (Procedimientos) objeto;

        String tipo = "";
        if (pro.getUrgencias().equals("S")) {
            tipo = "Urgencias";
        } else if (pro.getHospitalizacion().equals("S")) {
            tipo = "Hospitalizacion";
        } else if (pro.getRecien_nacido().equals("S")) {
            tipo = "Recien nacido";
        }

        double valor = 0.0;
        boolean mostrar_porcentaje = false;
        String nombre_procedimiento = pro.getDescripcion();
        if (manual_tarifario != null) {
            if (manual_tarifario.getMaestro_manual().getTipo_manual()
                    .equals(IConstantes.TIPO_MANUAL_SOAT)) {
                Anio_soat anio_soat = new Anio_soat();
                anio_soat.setAnio(manual_tarifario != null ? manual_tarifario
                        .getAnio() : (String) parametrosAction.get("anio"));
                anio_soat = generalExtraService.consultar(anio_soat);
                valor = ((anio_soat != null && manuales_procedimientos != null) ? (int) (anio_soat
                        .getValor_anio() * manuales_procedimientos.getValor())
                        : 0);
                mostrar_porcentaje = true;
            } else if (manual_tarifario.getMaestro_manual().getTipo_manual()
                    .equals(IConstantes.TIPO_MANUAL_ISS01)
                    || manual_tarifario.getMaestro_manual().getTipo_manual()
                    .equals(IConstantes.TIPO_MANUAL_ISSEXT)) {
                valor = manuales_procedimientos != null ? manuales_procedimientos
                        .getValor() : 0;
            } else if (manual_tarifario.getMaestro_manual().getTipo_manual()
                    .equals(IConstantes.TIPO_MANUAL_ISS04)) {
                valor = manuales_procedimientos != null ? manuales_procedimientos
                        .getValor() * 100 : 0;
            }
        }

        double valor_real = valor;
        double copago = 0;
        double valor_particular = 0;
        boolean tarifa_especial = false, encontro_especial = false;
        // boolean autorizado = true;
        if (manual_tarifario != null) {
            if (manual_tarifario.getTarifa_especial().equals("S")
                    && pro.getQuirurgico().equals("N")) {
                // tarifa_especial = true;
                Tarifas_procedimientos tarifas = new Tarifas_procedimientos();
                tarifas.setCodigo_empresa(manual_tarifario.getCodigo_empresa());
                tarifas.setCodigo_sucursal(manual_tarifario
                        .getCodigo_sucursal());
                tarifas.setCodigo_administradora(manual_tarifario
                        .getCodigo_administradora());
                tarifas.setId_plan(manual_tarifario.getId_contrato());
                tarifas.setId_procedimiento(pro.getId_procedimiento());
                tarifas.setConsecutivo_manual(manual_tarifario.getConsecutivo());
                tarifas = tarifas_procedimientosService.consultar(tarifas);
                if (tarifas != null) {
                    if (tarifas.getDescripcion() != null
                            && !tarifas.getDescripcion().trim().isEmpty()) {
                        nombre_procedimiento = tarifas.getDescripcion();
                    }
                    valor = valor_particular = tarifas.getValor();
                    encontro_especial = true;
                    tarifa_especial = true;
                }
            }

            if (manual_tarifario.getAplica_descuentos().equals("S")) {
                // Incluimos los descuentos
                Descuentos_laboratorios descuentos_laboratorios = new Descuentos_laboratorios();
                descuentos_laboratorios.setCodigo_empresa(manual_tarifario
                        .getCodigo_empresa());
                descuentos_laboratorios.setCodigo_sucursal(manual_tarifario
                        .getCodigo_sucursal());
                descuentos_laboratorios
                        .setCodigo_administradora(manual_tarifario
                                .getCodigo_administradora());
                descuentos_laboratorios.setId_contrato(manual_tarifario
                        .getId_contrato());
                descuentos_laboratorios.setConsecutivo_manual(manual_tarifario
                        .getConsecutivo());
                descuentos_laboratorios.setCodigo_procedimiento(pro
                        .getId_procedimiento() + "");
                descuentos_laboratorios = descuentos_laboratoriosService
                        .consultar(descuentos_laboratorios);
                if (descuentos_laboratorios != null) {
                    double descuento = valor
                            * descuentos_laboratorios.getPorcentaje_descuento()
                            / 100d;
                    valor = valor - descuento;
                }
            }
        }

        if ((String) parametrosAction.get("estrato") != null) {
            Copago_estrato cop = new Copago_estrato();
            cop.setCodigo_empresa(empresa.getCodigo_empresa());
            cop.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            cop.setEstrato((String) parametrosAction.get("estrato"));
            cop = copago_estratoService.consultar(cop);
            copago = (cop != null ? (int) (valor) : 0);
        }
        double descuento = 0, incremento = 0;
        if (manual_tarifario != null
                && !manual_tarifario.getTarifa_especial().equals("S")) {
            if (pro.getConsulta().equals("S")) {
                if (manual_tarifario.getTipo_consulta().equals("01")) {
                    descuento = (int) (valor * (manual_tarifario.getConsulta() / 100));
                    valor -= descuento;
                } else if (manual_tarifario.getTipo_consulta().equals("02")) {
                    incremento = (int) (valor * (manual_tarifario.getConsulta() / 100));
                    valor += incremento;
                }
            } else {
                if (manual_tarifario.getTipo_general().equals("01")) {
                    descuento = (int) (valor * (manual_tarifario.getGeneral() / 100));
                    valor -= descuento;
                } else if (manual_tarifario.getTipo_general().equals("02")) {
                    incremento = (int) (valor * (manual_tarifario.getGeneral() / 100));
                    valor += incremento;
                }
            }
        }

        final Map<String, Object> pcd = new HashMap<String, Object>();
        pcd.put("codigo_empresa", empresa.getCodigo_empresa());
        pcd.put("codigo_sucursal", sucursal.getCodigo_sucursal());
        pcd.put("id_procedimiento", pro.getId_procedimiento());
        pcd.put("codigo_cups",
                (pro.getCodigo_cups() != null ? pro.getCodigo_cups() : ""));
        pcd.put("nombre_procedimiento", nombre_procedimiento);
        pcd.put("valor_procedimiento", valor);
        pcd.put("descuento", descuento);
        pcd.put("incremento", incremento);
        pcd.put("valor_real", valor_real);
        pcd.put("copago", copago);
        pcd.put("tarifa_especial", tarifa_especial);
        pcd.put("encontro_especial", encontro_especial);
        pcd.put("sexo_pcd", pro.getSexo());
        pcd.put("limite_inferior_pcd", pro.getLimite_inferior());
        pcd.put("limite_superior_pcd", pro.getLimite_superior());
        pcd.put("es_pyp", pro.getPyp());
        pcd.put("grupo_uvr",
                manuales_procedimientos != null ? manuales_procedimientos
                .getGrupo_uvr() : "");
        pcd.put("frecuencia", pro.getFrecuencia_orden());
        pcd.put("auto_medico_general", pro.getAut_medi_general());
        pcd.put("auto_medico_especialista", pro.getAut_medi_especialista());
        pcd.put("tipo_procedimiento", pro.getTipo_procedimiento());

        pcd.put("porcentaje_defecto", pro.getPorcentaje_defecto());
        pcd.put("valoriss01_defecto", pro.getValoriss01_defecto());
        pcd.put("valoriss04_defecto", pro.getValoriss04_defecto());
        pcd.put("cantidad_maxima", pro.getCantidad_maxima());

        if (parametrosAction.containsKey("tipo")) {
            pcd.put("tipo", parametrosAction.get("tipo"));
        }

		// final boolean aut = autorizado;
        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Listcell(pro.getId_procedimiento() + ""));
        fila.appendChild(new Listcell(pro.getCodigo_cups() != null ? pro
                .getCodigo_cups() : ""));
        fila.appendChild(new Listcell(pro.getDescripcion()));
        fila.appendChild(new Listcell(tipo));
        fila.appendChild(new Listcell(
                manuales_procedimientos != null ? (manuales_procedimientos
                .getValor() + (mostrar_porcentaje ? " %" : "")) : ""));
        fila.appendChild(new Listcell(valor_particular + ""));
        fila.appendChild(new Listcell(pro.getSexo()));
        fila.appendChild(new Listcell(pro.getLimite_inferior()));
        fila.appendChild(new Listcell(pro.getLimite_superior()));

        fila.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                try {
                    if (!fila.isDisabled()) {
                        if (seleccionar_componente != null) {
                            seleccionar_componente.onSeleccionar(pcd);
                            seleccionados.put(pro.getId_procedimiento() + "",
                                    pro.getId_procedimiento() + "");
                        } else if (action != null) {
                            if (action instanceof Action) {
                                ((Action) action).adicionarPcd(pcd);
                                seleccionados.put(pro.getId_procedimiento()
                                        + "", pro.getId_procedimiento() + "");
                            } else if (action instanceof ZKWindow) {
                                ((ZKWindow) action).adicionarPcd(pcd);
                                seleccionados.put(pro.getId_procedimiento()
                                        + "", pro.getId_procedimiento() + "");
                            } else {
                                ((GeneralComposer) action).adicionarPcd(pcd);
                                seleccionados.put(pro.getId_procedimiento()
                                        + "", pro.getId_procedimiento() + "");
                            }
                        }

                        fila.setDisabled(true);
                        fila.setTooltiptext("Este procedimiento ya se encuentra seleccionado");
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new Exception(e);
                }
            }
        });

        if (seleccionados.containsKey(pro.getId_procedimiento() + "")) {
            fila.setDisabled(true);
            fila.setSelected(true);
            fila.setTooltiptext("Este procedimiento ya se encuentra seleccionado");
        }

        return fila;
    }

    @Override
    public void init() throws Exception {
        listboxResultado.setAttribute("org.zkoss.zul.listbox.rod", true);
        try {
            initOpenProcedimiento();
        } catch (Exception e) {

            log.error(e.getMessage(), e);
        }
    }

    public ISeleccionarComponente getSeleccionar_componente() {
        return seleccionar_componente;
    }

    /*
     * ADVERTENCIA: Este metodo se esta utilizando en reflexion asi que a la
     * hora de cambiar el nombre, cambiar en donde se esta llamando
     * 
     * Metodo *
     * com.framework.macros.manuales_tarifarios.ManualesTarifariosMacro.openPcd
     */
    public void setSeleccionar_componente(
            ISeleccionarComponente seleccionar_componente) {
        this.seleccionar_componente = seleccionar_componente;
    }

    public static Map<String, Object> getProcedimientoMap(
            String codigo_empresa, String codigo_sucursal, Procedimientos pro) {
        Map<String, Object> pcd = new HashMap<String, Object>();
        pcd.put("codigo_empresa", codigo_empresa);
        pcd.put("codigo_sucursal", codigo_sucursal);
        pcd.put("id_procedimiento", pro.getId_procedimiento());
        pcd.put("codigo_cups",
                (pro.getCodigo_cups() != null ? pro.getCodigo_cups() : ""));
        pcd.put("nombre_procedimiento", pro.getDescripcion());
        pcd.put("sexo_pcd", pro.getSexo());
        pcd.put("limite_inferior_pcd", pro.getLimite_inferior());
        pcd.put("limite_superior_pcd", pro.getLimite_superior());
        pcd.put("es_pyp", pro.getPyp());
        pcd.put("grupo_uvr", "");
        pcd.put("frecuencia", pro.getFrecuencia_orden());
        pcd.put("auto_medico_general", pro.getAut_medi_general());
        pcd.put("auto_medico_especialista", pro.getAut_medi_especialista());
        pcd.put("tipo_procedimiento", pro.getTipo_procedimiento());

        pcd.put("porcentaje_defecto", pro.getPorcentaje_defecto());
        pcd.put("valoriss01_defecto", pro.getValoriss01_defecto());
        pcd.put("valoriss04_defecto", pro.getValoriss04_defecto());
        return pcd;
    }

}
