package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.state.impl.LineaBean;

@Service("resolucion4505Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Resolucion4505Service implements Serializable{
	
	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;
	
	private static Connection connection;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	
	public interface OnResultSet{
		LineaBean onNuevo(ResultSet resultado, long contador, long total);
		void onFinalizar();
		void onCalcularTotalVerificar(long total); 
		void onComplementar(List<LineaBean> listado); 
	}
	
	public void listar(Map<String, Object> map, OnResultSet onResultSet)
			throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			String sql_where = getWhereSQL(map);
			long total = 0;
			// consultamos la cantidad
			rs = getConnection(sqlSession).prepareStatement(getSQLCount(sql_where))
					.executeQuery();
			if(rs.next()){
				total = rs.getLong(1);
			}
			onResultSet.onCalcularTotalVerificar(total);
			// montamos el total
			rs = getConnection(sqlSession).prepareStatement(getSQL(sql_where))
					.executeQuery();
			long contador = 0;
			List<LineaBean> lineaBeans = new ArrayList<LineaBean>();
			while (rs.next()) {
			   lineaBeans.add(onResultSet.onNuevo(rs, ++contador, total));
			}
			onResultSet.onComplementar(lineaBeans); 
			onResultSet.onFinalizar(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new HealthmanagerException(e.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstm != null) {
				pstm.close();
			}
			// cerrar conexion
//			close();
		}
	}
	
	private String getSQLCount(String where){
		StringBuilder builder = new StringBuilder();
//		builder.append("SELECT count(*) FROM (");
//		builder.append("SELECT 1  FROM resolucion4505.get4505 ");
//		builder.append(where);
//		builder.append(" GROUP BY id_paciente ");
//		builder.append(") AS res;");
		
		builder.append("SELECT count(*) FROM public.paciente AS pac ");
		builder.append(where);
		builder.append(";");
		return builder.toString();
	}
	 
	private String getSQL(String where) {
		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT ");
//		sql.append("DISTINCT ON (id_paciente) id_paciente,");
//		sql.append("variable_3,");
//		sql.append("variable_4,");
//		sql.append("variable_5,");
//		sql.append("variable_6,");
//		sql.append("variable_7,");
//		sql.append("variable_8,");
//		sql.append("variable_9,");
//		sql.append("variable_10,");
//		sql.append("variable_11,");
//		sql.append("variable_12,");
//		sql.append("variable_13 ");
//		sql.append("FROM resolucion4505.get4505 ");
//		sql.append(where);
////		sql.append("LIMIT 100;");
//		sql.append(";");
		
		
		 sql.append("SELECT ");
		 sql.append("pac.nro_identificacion AS id_paciente, ");
		 sql.append("pac.tipo_identificacion AS variable_3, ");
		 sql.append("pac.documento AS variable_4,  ");
		 sql.append("pac.apellido1 AS variable_5, ");
		 sql.append("CASE "); 
		 sql.append("WHEN ( ");
		 sql.append("(pac.apellido2)::text = ''::text ");
		 sql.append(") THEN 'NONE'::character varying "); 
		 sql.append("ELSE pac.apellido2 "); 
		 sql.append("END AS variable_6, "); 
		 sql.append("pac.nombre1 AS variable_7, "); 
		 sql.append("CASE "); 
		 sql.append("WHEN ( ");
		 sql.append("(pac.nombre2)::text = ''::text ");
		 sql.append(") THEN 'NONE'::character varying "); 
		 sql.append("ELSE pac.nombre2 "); 
		 sql.append("END AS variable_8, "); 
		 sql.append("to_char(pac.fecha_nacimiento, 'yyyy-MM-dd'::text) AS variable_9, "); 
		 sql.append("pac.sexo AS variable_10, "); 
		 sql.append("CASE "); 
		 sql.append("WHEN ( ");
		 sql.append("(pac.pertenencia_etnica)::text = ''::text ");
		 sql.append(") THEN '6'::character varying "); 
		 sql.append("ELSE pac.pertenencia_etnica "); 
		 sql.append("END AS variable_11, "); 
		 sql.append("CASE "); 
		 sql.append("WHEN ( ");
		 sql.append("(pac.codigo_ocupacion)::text <> ''::text ");
		 sql.append(") THEN pac.codigo_ocupacion "); 
		 sql.append("ELSE '9998'::character varying "); 
		 sql.append("END AS variable_12, "); 
		 sql.append("CASE "); 
		 sql.append("WHEN ( ");
		 sql.append("(pac.codigo_educativo)::text <> ''::text ");
		 sql.append(") THEN pac.codigo_educativo "); 
		 sql.append("ELSE '13'::character varying "); 
		 sql.append("END AS variable_13, "); 
		 sql.append("pac.codigo_empresa, "); 
		 sql.append("pac.codigo_sucursal, "); 
		 sql.append("pac.codigo_administradora "); 
		 sql.append("FROM public.paciente AS pac "); 
		 sql.append(where);
		 sql.append(";"); 
		
		return sql.toString();
	}
	
	private String getWhereSQL(Map<String, Object> map) {
		StringBuilder sqlWhere = new StringBuilder();
		sqlWhere.append("WHERE (EXISTS (SELECT 1 FROM public.admision AS ads WHERE ads.codigo_empresa = pac.codigo_empresa ");
		sqlWhere.append("AND ads.codigo_sucursal = pac.codigo_sucursal AND ads.nro_identificacion = pac.nro_identificacion ");
		sqlWhere.append("AND CAST(ads.fecha_ingreso AS DATE) BETWEEN '"
				+ dateFormat.format(map.get("fecha_inicio")) + "' AND '"
				+ dateFormat.format(map.get("fecha_final")) + "') OR ");
		sqlWhere.append("EXISTS(SELECT 1 FROM public.resultado_laboratorios AS rls WHERE rls.codigo_empresa = pac.codigo_empresa ");
		sqlWhere.append("AND rls.codigo_sucursal = pac.codigo_sucursal AND rls.nro_identificacion = pac.nro_identificacion ");
		sqlWhere.append("AND CAST(rls.fecha_resultado AS DATE) BETWEEN '"
				+ dateFormat.format(map.get("fecha_inicio")) + "' AND '"
				+ dateFormat.format(map.get("fecha_final")) + "')) ");
		sqlWhere.append("AND codigo_administradora = '"
				+ map.get("codigo_administradora") + "'");
		sqlWhere.append("AND codigo_empresa = '" + map.get("codigo_empresa")
				+ "'");
		sqlWhere.append("AND codigo_sucursal = '" + map.get("codigo_sucursal")
				+ "'");
		
//		sqlWhere.append("WHERE ");
//		sqlWhere.append(" codigo_empresa = '" + map.get("codigo_empresa") + "'");
//		sqlWhere.append(" AND codigo_sucursal = '" + map.get("codigo_sucursal")
//				+ "'");
//		sqlWhere.append(" AND codigo_administradora = '"
//				+ map.get("codigo_administradora") + "'");
//		sqlWhere.append(" AND CAST(fecha_ingreso AS DATE) BETWEEN '"
//				+ dateFormat.format(map.get("fecha_inicio")) + "' AND '"
//				+ dateFormat.format(map.get("fecha_final")) + "'");
//		sqlWhere.append(" AND id_paciente IN ('1043670755', '1047398050', '1052987121', '1065995987')"); 
		return sqlWhere.toString();
	}


	private Connection getConnection(SqlSessionTemplate sqlSession)
			throws Exception {
		if (connection == null)
			connection = sqlSession.getConfiguration().getEnvironment()
					.getDataSource().getConnection();
		return connection;
	}
	
	
//	private void close() throws Exception{
//		if(connection != null){
//			connection.close();
//			connection = null;
//		}
//	}

	public String consultar(String sql) throws Exception{ 
		ResultSet rs = null;
		try {
			// consultamos la cantidad
			rs = getConnection(sqlSession).prepareStatement(sql)
					.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HealthmanagerException(e.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		throw new HealthmanagerException("sin resultado"); 
	}
	
	
	public boolean consultarBoolean(String sql) throws Exception{ 
		ResultSet rs = null;
		try {
			// consultamos la cantidad
			rs = getConnection(sqlSession).prepareStatement(sql)
					.executeQuery();
			if(rs.next()){
				return rs.getBoolean(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HealthmanagerException(e.getMessage());
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		throw new HealthmanagerException("sin resultado"); 
	}
	
}
