/*
 * UsuariosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.dao.PrestadoresDao;
import healthmanager.modelo.dao.Roles_usuariosDao;
import healthmanager.modelo.dao.Roles_usuarios_capsDao;
import healthmanager.modelo.dao.UsuariosDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IRoles;
import com.framework.util.Utilidades;

@Service("usuariosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UsuariosService implements Serializable{

	private String limit;

	@Autowired
	private UsuariosDao usuariosDao;
	@Autowired
	private Roles_usuariosDao roles_usuariosDao;
	@Autowired
	private PrestadoresDao prestadoresDao;
	@Autowired
	private Roles_usuarios_capsDao roles_usuarios_capsDao;

	public void crear(Usuarios usuarios) {
		try {
			if (consultar(usuarios) != null) {
				throw new HealthmanagerException(
						"Usuarios ya se encuentra en la base de datos");
			}
			usuariosDao.crear(usuarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Usuarios usuarios) {
		try {
			return usuariosDao.actualizar(usuarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Usuarios consultar(Usuarios usuarios) {
		try {
			return usuariosDao.consultar(usuarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Usuarios usuarios) {
		try {
			return usuariosDao.eliminar(usuarios);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Usuarios> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return usuariosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Map<String, Object>> getUsuarioByRol(Map<String, Object> param) {
		try {
			return usuariosDao.getUsuarioByRol(param);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void guardar(Map<String, Object> map) {
		try {
			String accion = (String) map.get("accion");
			Usuarios usuarios = (Usuarios) map.get("user");
			List<Roles_usuarios> listado_roles = (List<Roles_usuarios>) map
					.get("roles_usuarios");
			List<Roles_usuarios_caps> listado_roles_caps = (List<Roles_usuarios_caps>) map
					.get("roles_usuarios_caps");

			if (accion.equalsIgnoreCase("registrar")) {
				Usuarios usr = new Usuarios();
				usr.setCodigo_empresa(usuarios.getCodigo_empresa());
				usr.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				usr.setCodigo(usuarios.getCodigo());

				usr = consultar(usr);

				if (usr != null) {
					throw new HealthmanagerException(
							"Este código ya se encuentra en la base de datos!!");
				}
				Usuarios usr1 = new Usuarios();
				usr1.setCodigo_empresa(usuarios.getCodigo_empresa());
				usr1.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				usr1.setLogin(usuarios.getLogin());

				usr1 = consultar(usr1);

				if (usr1 != null) {
					throw new HealthmanagerException(
							"Este usuario no se encuentra disponible!!");
				}
			}

			if (accion.equalsIgnoreCase("registrar")) {
				usuariosDao.crear(usuarios);
			} else {
				usuariosDao.actualizar(usuarios);
				Roles_usuarios roles_usuariosDel = new Roles_usuarios();
				roles_usuariosDel.setCodigo_empresa(usuarios
						.getCodigo_empresa());
				roles_usuariosDel.setCodigo_sucursal(usuarios
						.getCodigo_sucursal());
				roles_usuariosDel.setCodigo_usuario(usuarios.getCodigo());
				roles_usuariosDao.eliminar(roles_usuariosDel);
			}

			boolean rol_prestador = false;
			String tipo_prestador = "";
			if (listado_roles != null) {
				for (Roles_usuarios roles : listado_roles) {
					String rol_s = roles.getRol();
					roles.setCodigo_empresa(usuarios.getCodigo_empresa());
					roles.setCodigo_sucursal(usuarios.getCodigo_sucursal());
					roles.setRol(rol_s);
					roles.setCodigo_usuario(usuarios.getCodigo());
					roles_usuariosDao.crear(roles);
					if (Utilidades.isMedico(roles.getRol())) {
						rol_prestador = true;
						tipo_prestador = "01";
					} else if ((Utilidades.isEnfermeraJefe(roles.getRol()) || Utilidades
							.isAuxiliarEnfermeria(roles.getRol()))
							&& !rol_prestador) {
						rol_prestador = true;
						tipo_prestador = "02";
					} else if (roles.getRol().equals(IRoles.VACUNACION)) {
						rol_prestador = true;
						tipo_prestador = "03";
						usuarios.setCodigo_especialidad("60");
					}else if(roles.getRol().equals(IRoles.BACTERIOLOGO)){
						rol_prestador = true;
						tipo_prestador = "04";
						usuarios.setCodigo_especialidad("60");
					}
				}
			}

			if (listado_roles_caps != null) {
				for (Roles_usuarios_caps roles_caps : listado_roles_caps) {
					roles_usuarios_capsDao.crear(roles_caps);
				}
			}

			if (rol_prestador) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(usuarios.getCodigo_empresa());
				prestadores.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				prestadores.setNro_identificacion(usuarios.getCodigo());
				prestadores.setTipo_identificacion("CC");
				prestadores.setApellidos(usuarios.getApellidos());
				prestadores.setNombres(usuarios.getNombres());
				prestadores.setDireccion(usuarios.getDireccion());
				prestadores.setTel_oficina(usuarios.getTel_oficina());
				prestadores.setTel_res(usuarios.getTel_res());
				prestadores.setCelular(usuarios.getCelular());
				prestadores.setRegistro_medico(usuarios.getRegistro_medico());
				prestadores.setTipo(usuarios.getTipo_med());
				prestadores.setCodigo_especialidad(usuarios
						.getCodigo_especialidad());
				prestadores.setCreacion_date(usuarios.getCreacion_date());
				prestadores.setUltimo_update(usuarios.getUltimo_update());
				prestadores.setCreacion_user(usuarios.getCreacion_user());
				prestadores.setUltimo_user(usuarios.getUltimo_user());
				prestadores.setLogin(usuarios.getLogin());
				prestadores.setPassword(usuarios.getPassword());
				prestadores.setTipo_medico(usuarios.getTipo_medico());
				prestadores.setTipo_prestador(tipo_prestador);

				if (prestadoresDao.consultar(prestadores) == null) {
					prestadoresDao.crear(prestadores);
				} else {
					prestadoresDao.actualizar(prestadores);
				}

			} else {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(usuarios.getCodigo_empresa());
				prestadores.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				prestadores.setNro_identificacion(usuarios.getCodigo());
				prestadoresDao.eliminar(prestadores);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer totalResultados(Map<String, Object> parameters) {
		try {
			return usuariosDao.totalResultados(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
