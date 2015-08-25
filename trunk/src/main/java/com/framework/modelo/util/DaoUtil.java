package com.framework.modelo.util;

import healthmanager.modelo.util.Paquete;

public class DaoUtil {

    public static final String CREAR = ".crear";
    public static final String ACTUALIZAR = ".actualizar";
    public static final String CONSULTAR = ".consultar";
    public static final String ELIMINAR = ".eliminar";
    public static final String LISTAR = ".listar";
    public static final String TOTALIZAR = ".totalizar";
    public static final String EXISTE = ".existe";

    public static <T> String getQueryId(T dato) {
        Paquete paquete = dato.getClass().getAnnotation(Paquete.class);
        return paquete != null ? paquete.value() : "#";
    }

    public static String getQueryId(Class<?> clase) {
        Paquete paquete = clase != null ? clase.getAnnotation(Paquete.class)
                : null;
        return paquete != null ? paquete.value() : "#";
    }

}
