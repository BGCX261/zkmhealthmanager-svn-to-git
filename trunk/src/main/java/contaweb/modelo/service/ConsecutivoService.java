/*
 * ConsecutivoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */
package contaweb.modelo.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Comprobante;
import contaweb.modelo.bean.Consecutivo;
import contaweb.modelo.dao.ComprobanteDao;
import contaweb.modelo.dao.ConsecutivoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("consecutivoContawebService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConsecutivoService implements Serializable {

    private String limit;

    @Autowired
    private ConsecutivoDao consecutivoDao;
    @Autowired
    private ComprobanteDao comprobanteDao;

    public void crear(Consecutivo consecutivo) {
        try {
            if (consultar(consecutivo) != null) {
                throw new HealthmanagerException(
                        "Consecutivo ya se encuentra en la base de datos");
            }
            consecutivoDao.crear(consecutivo);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int actualizar(Consecutivo consecutivo) {
        try {
            return consecutivoDao.actualizar(consecutivo);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public Consecutivo consultar(Consecutivo consecutivo) {
        try {
            return consecutivoDao.consultar(consecutivo);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public int eliminar(Consecutivo consecutivo) {
        try {
            return consecutivoDao.eliminar(consecutivo);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public List<Consecutivo> listar(Map<String, Object> parameter) {
        try {
            parameter.put("limit", limit);
            return consecutivoDao.listar(parameter);
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void guardarDatos(List<Consecutivo> lista_consecutivos) {
        try {
            for (Consecutivo consecutivo : lista_consecutivos) {
                if (consecutivoDao.consultar(consecutivo) == null) {
                    consecutivoDao.crear(consecutivo);
                } else {
                    consecutivoDao.actualizar(consecutivo);
                }
            }
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }

    }

    public String crearConsecutivo(String codigo_empresa,
            String codigo_sucursal, String tabla) {
        try {
            Consecutivo consecutivo = new Consecutivo();
            consecutivo.setCodigo_empresa(codigo_empresa);
            consecutivo.setCodigo_sucursal(codigo_sucursal);
            consecutivo.setTipo(tabla);

            if (consecutivoDao.consultar(consecutivo) == null) {
                consecutivo.setConsecutivo("1");
                consecutivo.setCreacion_date(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                consecutivo.setUltimo_update(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                consecutivoDao.crear(consecutivo);
            } else {
                consecutivo = consecutivoDao.consultar(consecutivo);
            }

            return consecutivo.getConsecutivo();
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public String crearConsecutivoNota(String codigo_empresa,
            String codigo_sucursal, String codigo_comprobante) {
        try {
            Comprobante c = new Comprobante();
            c.setCodigo_empresa(codigo_empresa);
            c.setCodigo_sucursal(codigo_sucursal);
            c.setCodigo(codigo_comprobante);
            c = (Comprobante) comprobanteDao.consultar(c);

            String tabla = "";
            if (c.getTipo_dct().equals("01")) {
                //tabla = "NOTA_CONTABLE";
                tabla = c.getConsecutivo_tabla();
            } else if (c.getTipo_dct().equals("02")) {
                tabla = "NOTA_CLIENTE";
            } else if (c.getTipo_dct().equals("03")) {
                tabla = "NOTA_PROVEEDORES";
            } else if (c.getTipo_dct().equals("04")) {
                tabla = "COMPRA";
            } else if (c.getTipo_dct().equals("05")) {
                tabla = "EGRESO";
            } else if (c.getTipo_dct().equals("06")) {
                tabla = "VENTA";
            } else if (c.getTipo_dct().equals("07")) {
                tabla = "CAJA";
            } else if (c.getTipo_dct().equals("08")) {
                tabla = "TRASLADO_BODEGA";
            } else if (c.getTipo_dct().equals("09")) {
                tabla = "ENTRADA_COMPRA";
            } else if (c.getTipo_dct().equals("10")) {
                tabla = "DEVOLUCION_VENTA";
            } else if (c.getTipo_dct().equals("11")) {
                tabla = "DEVOLUCION_CONSUMO";
            } else if (c.getTipo_dct().equals("12")) {
                tabla = "DEVOLUCION_COMPRA";
            } else if (c.getTipo_dct().equals("13")) {
                tabla = "SALIDA_CONSUMO";
            } else if (c.getTipo_dct().equals("14")) {
                tabla = "ASIENTOS_CIERRE";
            } else if (c.getTipo_dct().equals("15")) {
                tabla = "TRANSFORMACION_ARTICULO";
            } else if (c.getTipo_dct().equals("16")) {
                tabla = "AJUSTE_INVENTARIO";
            } else if (c.getTipo_dct().equals("17")) {
                tabla = "CONSIGNACIONES";
            } else if (c.getTipo_dct().equals("18")) {
                tabla = "NOTA_BANCARIA";
            } else if (c.getTipo_dct().equals("19")) {
                tabla = "SALIDA_GARANTIA";
            } else if (c.getTipo_dct().equals("20")) {
                tabla = "SALIDA_CONSIGNACION";
            } else if (c.getTipo_dct().equals("21")) {
                tabla = "DEVOLUCION_CONSIGNACION";
            } else if (c.getTipo_dct().equals("22")) {
                tabla = "RMA";
            } else if (c.getTipo_dct().equals("23")) {
                tabla = "CHEQUES";
            } else if (c.getTipo_dct().equals("24")) {
                tabla = "REMISION";
            } else if (c.getTipo_dct().equals("25")) {
                tabla = "RECIBOS_CONSIGNACION";
            } else if (c.getTipo_dct().equals("26")) {
                tabla = "DEVOLUCION_MERCANCIA";
            } else if (c.getTipo_dct().equals("27")) {
                tabla = "ENTRADA";
            } else if (c.getTipo_dct().equals("28")) {
                tabla = "SALIDA";
            } else if (c.getTipo_dct().equals("29")) {
                tabla = "ORDEN_COMPRA";
            } else if (c.getTipo_dct().equals("30")) {
                tabla = "SALDO_INICIAL";
            } else if (c.getTipo_dct().equals("40")) {
                tabla = "AMORTIZACION";
            } else if (c.getTipo_dct().equals("41")) {
                tabla = "PAGARE";
            } else {
                tabla = "DEFAULT";
            }

            Consecutivo consecutivo = new Consecutivo();
            consecutivo.setCodigo_empresa(codigo_empresa);
            consecutivo.setCodigo_sucursal(codigo_sucursal);
            consecutivo.setTipo(tabla);
            if (consecutivoDao.consultar(consecutivo) == null) {
                consecutivo.setConsecutivo("1");
                consecutivo.setCreacion_date(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                consecutivo.setUltimo_update(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                consecutivoDao.crear(consecutivo);
            } else {
                consecutivo = (Consecutivo) consecutivoDao
                        .consultar(consecutivo);
            }

            return consecutivo.getConsecutivo();
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

    public void actualizarConsecutivo(String codigo_empresa,
            String codigo_sucursal, String tabla, String codigo) {
        try {
            Consecutivo consecutivo = new Consecutivo();
            consecutivo.setCodigo_empresa(codigo_empresa);
            consecutivo.setCodigo_sucursal(codigo_sucursal);
            consecutivo.setTipo(tabla);
            consecutivo = consecutivoDao.consultar(consecutivo);
            if (consecutivo != null) {
                consecutivo.setConsecutivo((Integer.parseInt(codigo) + 1) + "");
                consecutivoDao.actualizar(consecutivo);
            }
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }

    }

    public void actualizarConsecutivoNota(String codigo_empresa,
            String codigo_sucursal, String codigo_comprobante, String codigo) {
        try {
            Comprobante c = new Comprobante();
            c.setCodigo_empresa(codigo_empresa);
            c.setCodigo_sucursal(codigo_sucursal);
            c.setCodigo(codigo_comprobante);
            c = (Comprobante) comprobanteDao.consultar(c);
            String tabla = "";
            if (c.getTipo_dct().equals("01")) {
                //tabla = "NOTA_CONTABLE";
                tabla = c.getConsecutivo_tabla();
            } else if (c.getTipo_dct().equals("02")) {
                tabla = "NOTA_CLIENTE";
            } else if (c.getTipo_dct().equals("03")) {
                tabla = "NOTA_PROVEEDORES";
            } else if (c.getTipo_dct().equals("04")) {
                tabla = "COMPRA";
            } else if (c.getTipo_dct().equals("05")) {
                tabla = "EGRESO";
            } else if (c.getTipo_dct().equals("06")) {
                tabla = "VENTA";
            } else if (c.getTipo_dct().equals("07")) {
                tabla = "CAJA";
            } else if (c.getTipo_dct().equals("08")) {
                tabla = "TRASLADO_BODEGA";
            } else if (c.getTipo_dct().equals("09")) {
                tabla = "ENTRADA_COMPRA";
            } else if (c.getTipo_dct().equals("10")) {
                tabla = "DEVOLUCION_VENTA";
            } else if (c.getTipo_dct().equals("11")) {
                tabla = "DEVOLUCION_CONSUMO";
            } else if (c.getTipo_dct().equals("12")) {
                tabla = "DEVOLUCION_COMPRA";
            } else if (c.getTipo_dct().equals("13")) {
                tabla = "SALIDA_CONSUMO";
            } else if (c.getTipo_dct().equals("14")) {
                tabla = "ASIENTOS_CIERRE";
            } else if (c.getTipo_dct().equals("15")) {
                tabla = "TRANSFORMACION_ARTICULO";
            } else if (c.getTipo_dct().equals("16")) {
                tabla = "AJUSTE_INVENTARIO";
            } else if (c.getTipo_dct().equals("17")) {
                tabla = "CONSIGNACIONES";
            } else if (c.getTipo_dct().equals("18")) {
                tabla = "NOTA_BANCARIA";
            } else if (c.getTipo_dct().equals("19")) {
                tabla = "SALIDA_GARANTIA";
            } else if (c.getTipo_dct().equals("20")) {
                tabla = "SALIDA_CONSIGNACION";
            } else if (c.getTipo_dct().equals("21")) {
                tabla = "DEVOLUCION_CONSIGNACION";
            } else if (c.getTipo_dct().equals("22")) {
                tabla = "RMA";
            } else if (c.getTipo_dct().equals("23")) {
                tabla = "CHEQUES";
            } else if (c.getTipo_dct().equals("24")) {
                tabla = "REMISION";
            } else if (c.getTipo_dct().equals("25")) {
                tabla = "RECIBOS_CONSIGNACION";
            } else if (c.getTipo_dct().equals("26")) {
                tabla = "DEVOLUCION_MERCANCIA";
            } else if (c.getTipo_dct().equals("27")) {
                tabla = "ENTRADA";
            } else if (c.getTipo_dct().equals("28")) {
                tabla = "SALIDA";
            } else if (c.getTipo_dct().equals("29")) {
                tabla = "ORDEN_COMPRA";
            } else if (c.getTipo_dct().equals("30")) {
                tabla = "SALDO_INICIAL";
            } else if (c.getTipo_dct().equals("40")) {
                tabla = "AMORTIZACION";
            } else if (c.getTipo_dct().equals("41")) {
                tabla = "PAGARE";
            } else {
                tabla = "DEFAULT";
            }

            Consecutivo consecutivo = new Consecutivo();
            consecutivo.setCodigo_empresa(codigo_empresa);
            consecutivo.setCodigo_sucursal(codigo_sucursal);
            consecutivo.setTipo(tabla);
            consecutivo = (Consecutivo) consecutivoDao.consultar(consecutivo);
            if (consecutivo != null) {
                consecutivo.setConsecutivo((Integer.parseInt(codigo) + 1) + "");
                consecutivoDao.actualizar(consecutivo);
            }
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }

    }

    public String getZeroFill(String valor, int zeroFill) {
        String st_zeroFill = "000000000000000000000000000000";
        return st_zeroFill.substring(0, zeroFill - valor.length()) + valor;
    }

    public String obtenerPrefijo(String codigo_empresa, String codigo_sucursal,
            String codigo_comprobante) {
        try {
            Comprobante c = new Comprobante();
            c.setCodigo_empresa(codigo_empresa);
            c.setCodigo_sucursal(codigo_sucursal);
            c.setCodigo(codigo_comprobante);
            c = (Comprobante) comprobanteDao.consultar(c);
            String prefijo = "";
            if (c.getTipo_dct().equals("01")) {
                prefijo = "NC";
            } else if (c.getTipo_dct().equals("02")) {
                prefijo = "NCL";
            } else if (c.getTipo_dct().equals("03")) {
                prefijo = "NP";
            } else if (c.getTipo_dct().equals("04")) {
                prefijo = "FC";
            } else if (c.getTipo_dct().equals("05")) {
                prefijo = "CE";
            } else if (c.getTipo_dct().equals("06")) {
                prefijo = "FV";
            } else if (c.getTipo_dct().equals("07")) {
                prefijo = "RC";
            } else if (c.getTipo_dct().equals("08")) {
                prefijo = "TB";
            } else if (c.getTipo_dct().equals("09")) {
                prefijo = "EC";
            } else if (c.getTipo_dct().equals("10")) {
                prefijo = "DV";
            } else if (c.getTipo_dct().equals("11")) {
                prefijo = "DC";
            } else if (c.getTipo_dct().equals("12")) {
                prefijo = "DFC";
            } else if (c.getTipo_dct().equals("13")) {
                prefijo = "SC";
            } else if (c.getTipo_dct().equals("14")) {
                prefijo = "AC";
            } else if (c.getTipo_dct().equals("15")) {
                prefijo = "TA";
            } else if (c.getTipo_dct().equals("16")) {
                prefijo = "AI";
            } else if (c.getTipo_dct().equals("17")) {
                prefijo = "CNG";
            } else if (c.getTipo_dct().equals("18")) {
                prefijo = "NB";
            } else if (c.getTipo_dct().equals("19")) {
                prefijo = "SG";
            } else if (c.getTipo_dct().equals("20")) {
                prefijo = "SCNG";
            } else if (c.getTipo_dct().equals("21")) {
                prefijo = "DCNG";
            } else if (c.getTipo_dct().equals("22")) {
                prefijo = "RMA";
            } else if (c.getTipo_dct().equals("23")) {
                prefijo = "CH";
            } else if (c.getTipo_dct().equals("24")) {
                prefijo = "RM";
            } else if (c.getTipo_dct().equals("25")) {
                prefijo = "RCNG";
            } else if (c.getTipo_dct().equals("26")) {
                prefijo = "DM";
            } else if (c.getTipo_dct().equals("27")) {
                prefijo = "EM";
            } else if (c.getTipo_dct().equals("28")) {
                prefijo = "ES";
            } else if (c.getTipo_dct().equals("29")) {
                prefijo = "OC";
            } else if (c.getTipo_dct().equals("30")) {
                prefijo = "SI";
            } else if (c.getTipo_dct().equals("32")) {
                prefijo = "RMV";
            } else if (c.getTipo_dct().equals("33")) {
                prefijo = "DRC";
            } else if (c.getTipo_dct().equals("34")) {
                prefijo = "DRV";
            } else if (c.getTipo_dct().equals("35")) {
                prefijo = "PPG";
            } else if (c.getTipo_dct().equals("39")) {
                prefijo = "GD";
            } else if (c.getTipo_dct().equals("40")) {
                prefijo = "AM";
            } else if (c.getTipo_dct().equals("41")) {
                prefijo = "PG";
            } else {
                prefijo = "DF";
            }
            return prefijo;
        } catch (Exception e) {
            throw new HealthmanagerException(e.getMessage(), e);
        }
    }

}
