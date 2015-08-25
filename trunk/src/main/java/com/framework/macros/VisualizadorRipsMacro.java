package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Procedimientos;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.framework.res.LabelState;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * Este clase me permite visualizar el contenido de los rips
 *
 * @author Luis Miguel
 *
 */
public class VisualizadorRipsMacro extends ZKWindow {

    @View
    private Rows rowsDetalle;

    public static final String PARAM_RIPS = "PR";

    public static final String PARAM_TIPOS_RIPS = "PTR";

    public static final String PARAM_TIPOS_RIPS_CONSULTA = "PTRC";
    public static final String PARAM_TIPOS_RIPS_PROCEDIMIENTO = "PTRP";
    public static final String PARAM_TIPOS_RIPS_MEDICAMENTO = "PTRM";
    public static final String PARAM_TIPOS_RIPS_SERVICIOS = "PTRS";

    public static final String TIPO_ELEMENTO_VALOR = "|EL";
    public static final String TIPO_DIAGNOSTICO_VALOR = "|DX";
    public static final String TIPO_CODIGO_CUPS = "|CPS";

    private Object objeto_rips = null;
    private String tipo_objeto = "";

    @Override
    public void params(Map<String, Object> map) {
        tipo_objeto = (String) map.get(PARAM_TIPOS_RIPS);
        objeto_rips = (Object) map.get(PARAM_RIPS);
    }

    @Override
    public void init() throws Exception {
        visualizarObjeto();
    }

    /**
     * Este metodo visualiza el contenido de los rips mandado a visualizar
     *
     * @author Luis Miguel
     *
     */
    private void visualizarObjeto() {
        if (objeto_rips != null) {
            mostarDescriptor(getDescriptor());
        } else {
            MensajesUtil.mensajeAlerta("Advertencia", "El objeto de rips a visualizar no puede ser nulo");
        }
    }

    @Override
    public void onClose() {
        detach();// esto es para cuando cierre con el boton superior se desvincule de la vista
    }

    /**
     * Este metodo me devuel el descriptor dependiendo el tipo seleccionado
	 *
     */
    private String[][] getDescriptor() {
        if (tipo_objeto.equals(PARAM_TIPOS_RIPS_CONSULTA)) {
            setTitle("RIPS DE CONSULTA");
            return descriptor_consulta;
        } else if (tipo_objeto.equals(PARAM_TIPOS_RIPS_PROCEDIMIENTO)) {
            setTitle("RIPS DE PROCEDIMIENTO");
            return descriptor_procedimiento;
        } else if (tipo_objeto.equals(PARAM_TIPOS_RIPS_MEDICAMENTO)) {
            setTitle("RIPS DE MEDICAMENTOS");
            return descriptor_medicamento;
        } else if (tipo_objeto.equals(PARAM_TIPOS_RIPS_SERVICIOS)) {
            setTitle("RIPS DE SERVICIOS");
            return descriptor_servicio;
        }
        return null;
    }

    /**
     * Este metodo me permite mostrar el descriptor visualmente
	 *
     */
    private void mostarDescriptor(String[][] descriptor) {
        if (descriptor != null) {
            rowsDetalle.getChildren().clear();
            for (String[] descriptorTemp : descriptor) {
                rowsDetalle.appendChild(convertirDesciptorRow(descriptorTemp));
            }
        } else {
            MensajesUtil.mensajeAlerta("Advertencia", "El descriptor no es valido esta nulo");
        }
    }

    /**
     * Este metodo me convierte los descriptorres a vista
	 *
     */
    private Row convertirDesciptorRow(String[] descriptorTemp) {
        String descipcion = descriptorTemp[0];
        String valor = descriptorTemp[1];
        Row row = new Row();
        row.appendChild(new LabelState(descipcion, true));
        row.appendChild(new Label(validarValorDescriptor(valor).toUpperCase()));
        return row;
    }

    /**
     * Este metodo me valida el valor que va a mostar
	 *
     */
    private String validarValorDescriptor(String valor) {
        try {
            String campo = valor;
            String descipcion_complemento = "";
            Object objectValor = null;

            if (valor.contains("|")) {
                campo = valor.replaceAll("[|].+$", "");
            }
            //log.info("@validarValorDescriptorCargar Campo: " + campo); 
            if (objeto_rips instanceof Map) {
                objectValor = ((Map) objeto_rips).get(campo);
            } else {
                Field field = objeto_rips.getClass().getDeclaredField(campo);
                field.setAccessible(true);
                objectValor = field.get(objeto_rips);
            }
            if (objectValor != null) {
                if (valor.contains(TIPO_CODIGO_CUPS)) {
                    return getCodigoCups(objectValor + "");
                } else {
                    // cargar los diagnosticos
                    if (valor.contains(TIPO_DIAGNOSTICO_VALOR)) {
                        descipcion_complemento += getDiagnostico(objectValor + "");
                    }
                    // cargar el valor de los elementos
                    if (valor.contains(TIPO_ELEMENTO_VALOR)) {
                        descipcion_complemento += getElemento(valor, campo, objectValor);
                    }
                    return validarValorFormato(objectValor) + " " + descipcion_complemento;
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * Este metodo me devuelve el con un formato especifico
	 *
     */
    private String validarValorFormato(Object object) {
        if (object instanceof Double) {
            return new DecimalFormat("#,##0.00").format(object);
        } else if (object instanceof Timestamp
                || object instanceof java.sql.Date || object instanceof Date) {
            return new SimpleDateFormat("dd-MM-yyyy").format(object);
        }
        return object + "";
    }

    /**
     * Este metodo me consulta el elemento
     *
     */
    private String getElemento(String valorDescriptor, String campo, Object valor) {
        String codigo = "" + valor;
        String tipo = campo;
        if (valorDescriptor.matches(".+|EL.+[(].+[)]")) {
            tipo = valorDescriptor.replaceAll(".+[EL(]", "").replaceAll("[)]", "");
        }
        return Utilidades.getDescripcionElemento(codigo, tipo, getServiceLocator());
    }

    /**
     * Este metodo me consulta el laboratorio
     *
     */
    private String getDiagnostico(String codigo) {
        Cie cie = new Cie();
        cie.setCodigo(codigo);
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
        return cie != null ? cie.getNombre() : "";
    }

    private String getCodigoCups(String codigo_articulo) {
        Procedimientos procedimiento = new Procedimientos();
        procedimiento.setId_procedimiento(new Long(codigo_articulo));
        procedimiento = getServiceLocator().getProcedimientosService()
                .consultar(procedimiento);
        if (procedimiento != null) {
            return procedimiento.getCodigo_cups();
        } else {
            return codigo_articulo;
        }
    }


    /*  DESCRIPTORES DE RIPS */
    /* DESCIPTOR DE CONSULTA */
    /**
     * Descriptor de consulta esta variable me pertime especificarle al software
     * como debe mostrar el rips indicado en el campo de los rips puedes
     * utilizar 1. la descipcion del campo 2. el campo que puede llevar al lado
     * separado por | Ejemplo: para cargar diagnostico escribir |DX Elemento
     * |EL(tipo) - sino se coloca el tipo tima el nombre del campo
     *
     * @author Luis Miguel
	 *
     */
    private final String[][] descriptor_consulta = {
        {"Tipo de identificacion", "tipo_identificacion|EL(tipo_id)"},
        {"Número de identificacion", "nro_identificacion"},
        {"Fecha de la consulta", "fecha_consulta"},
        {"Número de autorizacion, cuando se requiera",
            "numero_autorizacion"},
        {"código de consulta", "codigo_consulta|CPS"},
        {"Finalidad de la consulta", "finalidad_consulta|EL(finalidad_consulta)"},
        {"Causa externa que originó la consulta", "causa_externa|EL(causa_externa)"},
        {"diagnóstico principal", "codigo_diagnostico_principal|DX"},
        {"diagnóstico relacionado No. 1", "codigo_diagnostico1|DX"},
        {"diagnóstico relacionado No. 2", "codigo_diagnostico2|DX"},
        {"diagnóstico relacionado No. 3", "codigo_diagnostico3|DX"},
        {"Tipo de diagnóstico principal", "tipo_diagnostico|EL(tipo_diagnostico)"},
        {"Valor de la consulta", "valor_consulta"},
        {"Valor de la cuota moderadora", "valor_cuota"},
        {"Valor neto a pagar por la entidad administradora del plan de beneficios", "valor_neto"}};

    /* DESCRIPTOR DE PROCEDIMIENTO */
    private final String[][] descriptor_procedimiento = {
        {"Fecha del procedimiento", "fecha_procedimiento"},
        {"Número de autorizacion, cuando se requiera", "numero_autorizacion"},
        {"código del procedimiento", "codigo_procedimiento|CPS"},
        {"Ambito de realizacion del procedimiento", "ambito_procedimiento|EL(ambito_procedimiento)"},
        {"Finalidad del procedimiento", "finalidad_procedimiento|EL(finalidad_proc)"},
        {"Personal que atiende", "personal_atiende|EL(personal_atiende)"},
        {"diagnóstico principal", "codigo_diagnostico_principal|DX"},
        {"diagnóstico relacionad", "codigo_diagnostico_relacionado|DX"},
        {"Complicacion (cuando ocurra dentro de un procedimiento)", "complicacion|DX"},
        {"Forma de realizacion", "forma_realizacion|EL(forma_realizacion)"},
        {"Valor del procedimiento", "valor_procedimiento"}
    };

    /* DESCRIPTOR DE PROCEDIMIENTO */
    private final String[][] descriptor_medicamento = {
        {"Número de la factura", "nro_factura"},
        {"Tipo de identificacion del usuario", "tipo_identificacion"},
        {"Número de identificacion del usuario en el Sistema", "nro_identificacion"},
        {"Número de autorizacion", "numero_autorizacion"},
        {"código del medicamento", "codigo_medicamento"},
        {"Tipo de medicamento", "tipo_medicamento|EL(tipo_medicamento)"},
        {"Nombre genérico del medicamento", "nombre_generico"},
        {"Forma farmacéutica", "forma_farmaceutica"},
        {"Concentracion del medicamento", "concentracion_medicamento"},
        {"Unidad de medida del medicamento", "unidad_medida"},
        {"Número de unidades", "unidades"},
        {"Valor unitario de medicamentos", "valor_unitario"},
        {"Valor total de medicamento", "valor_total"}
    };

    /* DESCRIPTOR DE PROCEDIMIENTO */
    private final String[][] descriptor_servicio = {
        {"Número de la factura", "nro_factura"},
        {"Tipo de identificacion", "tipo_identificacion"},
        {"Número de identificacion", "nro_identificacion"},
        {"Número de autorizacion", "numero_autorizacion"},
        {"Tipo de servicio", "tipo_servicio|EL(tipo_servicio)"},
        {"código del servicio", "codigo_servicio"},
        {"Nombre del servicio", "nombre_servicio"},
        {"Cantidad", "unidades"},
        {"Valor unitario del material e insumo", "valor_unitario"},
        {"Valor total del material e insumo", "valor_total"}
    };
}
