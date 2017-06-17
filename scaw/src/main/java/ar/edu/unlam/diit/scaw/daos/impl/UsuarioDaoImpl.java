package ar.edu.unlam.diit.scaw.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.UpdatableSqlQuery;

import ar.edu.unlam.diit.scaw.daos.UsuarioDao;
import ar.edu.unlam.diit.scaw.daos.impl.TareaDaoImpl.TareaMapper;
import ar.edu.unlam.diit.scaw.entities.Tarea;
import ar.edu.unlam.diit.scaw.entities.Usuario;

public class UsuarioDaoImpl implements UsuarioDao{

	@Autowired
	 NamedParameterJdbcTemplate jdbcTemplate;
	
	
	
	 public UsuarioDaoImpl() {
		super();
	}
	
	 
	//obtengo los participantes de una tarea
		public List<Usuario> getListaDeParticipantesDeUnaTareaPorIdTarea(Integer idTarea) {
			Map<String, Object> params = new HashMap<String, Object>();
			String sql = "SELECT USUARIO.ID, USUARIO.NOMBRE ,USUARIO.APELLIDO,USUARIO.CONTRASENA ,USUARIO.TIPO,USUARIO.ESTAAPROBADO ,USUARIO.NICKNAME FROM (TAREA JOIN ACCEDETAREA  ON TAREA.ID = ACCEDETAREA .IDTAREA) JOIN USUARIO ON ACCEDETAREA.IDUSUARIO = USUARIO.ID  WHERE TAREA.ID = ' " +  " " + idTarea.toString() + " '";
			
			List<Usuario> lista = jdbcTemplate.query(sql, params, new UsuarioMapper());
			return lista;
			
			
			
			
		};
	
	 //guardar
	 
	 	public void guardarUsuarioBDD(Usuario usuario)  {
	 		
	 		String sql= "INSERT INTO USUARIO(NOMBRE,APELLIDO,CONTRASENA,TIPO,ESTAAPROBADO,NICKNAME) VALUES  (:nombre,:apellido,:contrasena,:tipo,:estaAprobado,:nickName)";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nombre",usuario.getNombre());
			params.put("apellido", usuario.getApellido());
			params.put("contrasena", usuario.getContrasena());
			usuario.setTipo("usuarioNormal");//por defecto
			usuario.setEstaAprobado(0);//no esta aprobado
			params.put("tipo", usuario.getTipo());
			params.put("estaAprobado", usuario.getEstaAprobado());
			params.put("nickName", usuario.getNickName());
			jdbcTemplate.update(sql, params);
			
			
	 	};
	 	//modificar
	 	public void modificarUsuario(Integer idUsuario, String nickName,String nombreU, String apellidoU, String contrasenaU,Integer estaParobado) {
	 		
	 		
	 		Map<String, Object> params = new HashMap<String, Object>();
	 		String SqlId = idUsuario.toString();
	
	 		
	 		String sqlNombre= "UPDATE USUARIO SET NOMBRE ='" +nombreU+ "' WHERE ID =" + SqlId;
	 		jdbcTemplate.update(sqlNombre, params );
	 	
	 		
	 		Map<String, Object> params2 = new HashMap<String, Object>();
	 		String sqlApellido= "UPDATE USUARIO SET APELLIDO ='" + apellidoU  + "' WHERE ID =" + SqlId;
	 		jdbcTemplate.update(sqlApellido, params2 );
	 		
	 		Map<String, Object> params3 = new HashMap<String, Object>();
	 		String sqlContrasena= "UPDATE USUARIO SET CONTRASENA ='" + contrasenaU  + "'  WHERE ID =" + SqlId;
	 		jdbcTemplate.update(sqlContrasena, params3 );
	 		
	 		Map<String, Object> params4 = new HashMap<String, Object>();
	 		String sqlEstaAprobado= "UPDATE USUARIO SET ESTAAPROBADO ='" + estaParobado.toString()   + "'  WHERE ID =" + SqlId;
	 		
	 		jdbcTemplate.update(sqlEstaAprobado, params4 );
	 		
	 		Map<String, Object> params5 = new HashMap<String, Object>();
	 		String sqlNick= "UPDATE USUARIO SET NICKNAME ='" + nickName.toString()   + "'  WHERE ID =" + SqlId;
	 		
	 		jdbcTemplate.update(sqlNick, params5 );

	 		
	 	};
	 	
	 	@Override
	 	public List<Usuario> getListaDeUsuariosNoAprobadosBDD() {
	 		Map<String, Object> params = new HashMap<String, Object>();
			String sql = "SELECT * FROM USUARIO WHERE ESTAAPROBADO = 0";
			List<Usuario> lista = jdbcTemplate.query(sql, params, new UsuarioMapper());
			return lista;
	 	} 
	 	
	 	@Override
	 	public void aprobarUsuarioBdd(Usuario usuario) {
	 		Map<String, Object> params = new HashMap<String, Object>();
	 		
	 		
	 		String sql2 = usuario.getId().toString();
	 		String sql= "UPDATE USUARIO SET ESTAAPROBADO = 1 WHERE ID =" +" "+ sql2;
	 		

	 		jdbcTemplate.update(sql, params );
	 		
	 	}
	 	
	 	@Override
	 	public void eliminarUnUsuarioBdd(Integer idUsuario) {
	 			Map<String, Object> params = new HashMap<String, Object>();
	 			String sql2 = idUsuario.toString();
	 			String sql= "DELETE FROM USUARIO WHERE ID =" + " " + sql2;
	 			jdbcTemplate.update(sql, params);
	 		
	 		
	 	}
	 //--
	 	@Override
	 	public List<Usuario> buscaUsuariosEnLaBDD(String nombreUsuario,Integer idUsuarioActual) {
	 		Map<String, Object> params = new HashMap<String, Object>();
			String sql = "SELECT * FROM USUARIO WHERE NOMBRE LIKE '" + nombreUsuario + "%' AND TIPO = 'usuarioNormal' AND ID != " +" "+ idUsuarioActual.toString() ;
			System.out.println(sql);
	 		List<Usuario> lista = jdbcTemplate.query(sql, params, new UsuarioMapper());
			return lista;
	 	}
	 	
	 	@Override
		public List<Usuario> getUsuarioPorNickNameBdd(String nickName) {
	 		
	 		Map<String, Object> params = new HashMap<String, Object>();
			
	 		String sql = "SELECT * FROM USUARIO WHERE NICKNAME= '" + nickName + "'" ;
	
	 		List<Usuario> lista = jdbcTemplate.query(sql, params, new UsuarioMapper());
			return lista;
			
	 	}
	 
	@Override
	public List<Usuario> getListaUsuariosBDD() {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM USUARIO";
		List<Usuario> lista = jdbcTemplate.query(sql, params, new UsuarioMapper());
		
		/*for(Usuario e :lista)
		{
			System.out.println( "" +e.getNombre() +""+ e.getContrasena()+""+e.getTipo()+""+e.getEstaAprobado());
		}*/
		
		return lista;
	}
	
	//traer por id
	@Override
	public Usuario getUnUsuarioPorIdBdd(Integer idUsuario) {
		return null;
	}
	

	//geters
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}



	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private static final class UsuarioMapper implements RowMapper<Usuario>{
		
		//@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			Usuario usuario= new Usuario();
			usuario.setId(rs.getInt("id"));
			usuario.setNombre(rs.getString("nombre"));
			usuario.setApellido(rs.getString("apellido"));
			usuario.setContrasena(rs.getString("contrasena"));
			usuario.setTipo(rs.getString("tipo"));
			usuario.setEstaAprobado(rs.getInt("estaAprobado"));
			usuario.setNickName(rs.getString("nickName"));
			return usuario;
		}
		
		
	}

	
	
	
	
	
	
	
}
