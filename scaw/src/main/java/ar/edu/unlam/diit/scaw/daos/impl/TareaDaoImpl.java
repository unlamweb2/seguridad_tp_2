package ar.edu.unlam.diit.scaw.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ar.edu.unlam.diit.scaw.daos.TareaDao;

import ar.edu.unlam.diit.scaw.entities.AccedeTarea;
import ar.edu.unlam.diit.scaw.entities.Tarea;
import ar.edu.unlam.diit.scaw.entities.TareaYAccede;
import ar.edu.unlam.diit.scaw.entities.Usuario;


public class TareaDaoImpl  implements TareaDao{
	
	@Autowired
	 NamedParameterJdbcTemplate jdbcTemplate;
	
	public TareaDaoImpl() {
		super();
	}
	
	@Override
	public void agregarTareaBdd(Tarea tarea,Integer idUsuario) {
 		String sql= "INSERT INTO TAREA(NOMBRE,DESCRIPCION,ACCESO,ESTADO) VALUES  (:nombre,:descripcion,:acceso,:estado)";
 		Map<String, Object> params = new HashMap<String, Object>();
 		params.put("nombre", tarea.getNombre());
 		params.put("descripcion", tarea.getDescripcion());
 		params.put("acceso", tarea.getAcceso());
 		params.put("estado", "pendiente"); //por defecto
 		
 		jdbcTemplate.update(sql, params);
 		
 		//obtengo el ultimo de las tareas o sea el id actual para este caso
 		Map<String, Object> params3 = new HashMap<String, Object>();
		String sql3 = "SELECT COUNT(ID) FROM TAREA ";
		int idTarea = getJdbcTemplate().queryForInt(sql3, params3);
 		
 		//por defecto modo lectura y agrego la tarea al usuario
 		AccedeTarea accedeTarea= new AccedeTarea();
 		accedeTarea.setIdUsuario(idUsuario);
 		//accedeTarea.setIdTarea(idTarea) LA ID LA CONSIGO DE OTRA FORMA;
 		accedeTarea.setModo("escritura");
 		String sql2= "INSERT INTO ACCEDETAREA(MODO,IDTAREA,IDUSUARIO) VALUES (:modo,:idTarea,:idUsuario)";
 		Map<String, Object> params2 = new HashMap<String, Object>();	
 		params2.put("modo",accedeTarea.getModo() );
 		params2.put("idTarea", idTarea);
 		params2.put("idUsuario", accedeTarea.getIdUsuario());
 		
 		jdbcTemplate.update(sql2, params2);
		
		
	}
	
	@Override
	public void guardarAccedeTarea(Integer idUsuario, Integer idTarea, String modo) {

 
 		String sql= "INSERT INTO ACCEDETAREA(MODO,IDTAREA,IDUSUARIO) VALUES (:modo,:idTarea,:idUsuario)";
 		Map<String, Object> params = new HashMap<String, Object>();	
 		params.put("idUsuario",idUsuario );
 		params.put("modo",modo );
 		params.put("idTarea", idTarea);
 		
 		jdbcTemplate.update(sql, params);
		
		
	}
	
	@Override
	public void eliminarParticipanteDeUnaTarea(Integer idTarea, Integer idParticipante) {
		Map<String, Object> params = new HashMap<String, Object>();	
 		String sql ="DELETE FROM ACCEDETAREA WHERE IDUSUARIO = '" +idParticipante.toString()+"'" +" AND IDTAREA = '" +idTarea.toString()+"'";
 		
 		jdbcTemplate.update(sql, params);
		
	}

	@Override
	public void eliminarTareaPorId(Integer idTarea) {
		Map<String, Object> params = new HashMap<String, Object>();
			String sql2 = idTarea.toString();
			String sql= "DELETE FROM TAREA WHERE ID =" + " " + sql2;
			jdbcTemplate.update(sql, params);
		
		
	}
	
	@Override
	public void modificarTarea(Integer idTarea, String nombreT, String descripcionT, String estadoT, String accesoT) {
		Map<String, Object> params = new HashMap<String, Object>();
 		String SqlId = idTarea.toString();

 		
 		String sqlNombre= "UPDATE TAREA SET NOMBRE = '" + nombreT + " "  + "' WHERE ID =" + SqlId;
 		jdbcTemplate.update(sqlNombre, params );
 	
 		
 		Map<String, Object> params2 = new HashMap<String, Object>();
 		String sqlDescripcion= "UPDATE TAREA SET DESCRIPCION = '" + descripcionT + " "  + "' WHERE ID =" + SqlId;
 		jdbcTemplate.update(sqlDescripcion, params2 );
 		
 		Map<String, Object> params3 = new HashMap<String, Object>();
 		String sqlEstado= "UPDATE TAREA SET ESTADO = ' " + estadoT + " " + "'  WHERE ID =" + SqlId;
 		jdbcTemplate.update(sqlEstado, params3 );
 		
 		Map<String, Object> params4 = new HashMap<String, Object>();
 		String sqlAcceso= "UPDATE TAREA SET ACCESO =  '" + accesoT.toString() + " "  + "'  WHERE ID =" + SqlId;
 		
 		jdbcTemplate.update(sqlAcceso, params4 );
		
	}
	
	
	//estado
	public void setearEstadoTareaBdd(Integer idTarea, String estado) {
		
		Map<String, Object> params = new HashMap<String, Object>();
 		String SqlId = idTarea.toString();

 		String sql= "UPDATE TAREA SET ESTADO = '" + estado + " "  + "' WHERE ID =" + SqlId;
 		jdbcTemplate.update(sql, params );
			
	};
	
	@Override
	public List<Tarea> getListaDeTareasCompletasPorIdDeUsuario(Integer idUsuario) {
		List<Tarea> listaDeTareas = getListaDeTareasPorIdDeUsuario(idUsuario);
	
		List<Tarea> listaAMostrar = new ArrayList<Tarea>();
		
		for(Tarea t:listaDeTareas)
		{
			
			
			
			if(t.getEstado().equals("completa"))//agrego las pendientes
			{
				
				listaAMostrar.add(t);
			}
			
		}
		
		return listaAMostrar;
	}
	
	
	@Override
	public List<Tarea> getListaDeTareasPendientesPorIdDeUsuario(Integer idUsuario) {
		List<Tarea> listaDeTareas = getListaDeTareasPorIdDeUsuario(idUsuario);
 
		List<Tarea> listaAMostrar = new ArrayList<Tarea>();
		
		for(Tarea t:listaDeTareas)
		{
			if(t.getEstado().equals("pendiente"))//agrego las pendientes
			{
				
				listaAMostrar.add(t);
			}
			
		}
		
		return listaAMostrar;
	}
	
	@Override
	public List<AccedeTarea> traerTodosLosAccedeTareaPorIdTarea(Integer idTarea) {
		String sql2= idTarea.toString();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM ACCEDETAREA WHERE IDTAREA = " + " " +idTarea ;
		
		List<AccedeTarea> listaDeACCEDETareas = jdbcTemplate.query(sql, params, new AccesoMapper());
		
	
		return listaDeACCEDETareas;
	}
	
	@Override
	public List<Tarea> getListaDeTareasPorIdDeUsuario(Integer idUsuario) {
		String sql2= idUsuario.toString();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM ACCEDETAREA WHERE IDUSUARIO = " + " " +sql2 ;
		
		List<AccedeTarea> listaDeACCEDETareas = jdbcTemplate.query(sql, params, new AccesoMapper());
		
		List<Tarea> listaDeTareas = new ArrayList<Tarea>();
		for(Tarea t :  getListaDeTareasBDD())
		{
			for(AccedeTarea acc : listaDeACCEDETareas )
			{
				if(acc.getIdTarea().equals(t.getId()))
				{
					listaDeTareas.add(t);
				}
				
			}
		}
		return listaDeTareas;
	}

	
	
	@Override
	public List<Tarea> getListaDeTareasBDD() {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM TAREA";
		List<Tarea> lista = jdbcTemplate.query(sql, params, new TareaMapper());
		return lista;
	}
	/*
	 * tirando magia
	 * 
	 * */
	public List<Tarea> getListaDeTareasCompletasPorIdDeUsuarioConQueryYEstado(Integer idUsuario, String estado) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM TAREA JOIN ACCEDETAREA  ON TAREA.ID = ACCEDETAREA .IDTAREA WHERE TAREA.ESTADO = " + "  '" + estado + " '" + "and ACCEDETAREA.IDUSUARIO = " + " " + idUsuario.toString();
		List<Tarea> lista = jdbcTemplate.query(sql, params, new TareaMapper());
		return lista;
		
		
		
	};
	
	@Override
	public List<AccedeTarea> traerListaDeAccedeTareaPorIdDeTarea(Integer idTarea) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM TAREA JOIN ACCEDETAREA  ON TAREA.ID = ACCEDETAREA .IDTAREA WHERE TAREA.ID =" +idTarea.toString();
		List<AccedeTarea> lista = jdbcTemplate.query(sql, params, new AccesoMapper());
		return lista;
	}
	
	@Override
	public List<Tarea> listaDeTareasPorModoDeAccedo(String modo) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM TAREA JOIN ACCEDETAREA  ON TAREA.ID = ACCEDETAREA .ID TAREA WHERE ACCEDETAREA.MODO = '"  + modo + "'" ;
		List<Tarea> lista = jdbcTemplate.query(sql, params, new TareaMapper());
		return lista;
	}
	
	@Override
	public List<Tarea> listaDeTareasPorAccesoDeAccedo(String acceso) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM TAREA WHERE ACCESO ='"  + acceso + "'" ;
		List<Tarea> lista = jdbcTemplate.query(sql, params, new TareaMapper());
		return lista;
	}
	
	
	@Override
	public List<Tarea> getTareaConIdDeUsuarioYIdDeTarea(Integer idUsuario, Integer idTarea) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT * FROM TAREA JOIN ACCEDETAREA  ON TAREA.ID = ACCEDETAREA .IDTAREA WHERE TAREA.ID = " + "  '" + idTarea.toString() + " '" + "and ACCEDETAREA.IDUSUARIO = " + " " + idUsuario.toString();
		List<Tarea> lista = jdbcTemplate.query(sql, params, new TareaMapper());
		return lista;
		
	}
	
	
@Override
	public List<AccedeTarea> getACCEDEConIdDeUsuarioYIdDeTarea(Integer idUsuario, Integer idTarea) {
	Map<String, Object> params = new HashMap<String, Object>();
	String sql = "SELECT * FROM TAREA JOIN ACCEDETAREA  ON TAREA.ID = ACCEDETAREA .IDTAREA WHERE TAREA.ID = " + "  '" + idTarea.toString() + " '" + "and ACCEDETAREA.IDUSUARIO = " + " " + idUsuario.toString();
	List<AccedeTarea> lista = jdbcTemplate.query(sql, params, new AccesoMapper());
	return lista;
	}
	
	public static final class TareaYAccedeMapper implements RowMapper<TareaYAccede>
	{

		@Override
		public TareaYAccede mapRow(ResultSet rs, int rowNum) throws SQLException {
			TareaYAccede x = new TareaYAccede();
			x.getAccedeTareaUsuaroEn().setId(rs.getInt("id"));
			x.getAccedeTareaUsuaroEn().setIdTarea(rs.getInt("idTarea"));
			x.getAccedeTareaUsuaroEn().setIdUsuario(rs.getInt("idUsuario"));
			x.getAccedeTareaUsuaroEn().setModo(rs.getString("modo"));
			x.setId(rs.getInt("id"));
			x.setAcceso(rs.getString("acceso"));
			x.setDescripcion(rs.getString("descripcion"));
			x.setNombre(rs.getString("nombre"));
			x.setEstado(rs.getString("estado"));
			return x;
		}
		
		
	}
	
	

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	//TAREA ROW
	public static final class TareaMapper implements RowMapper<Tarea>
	{

		@Override
		public Tarea mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tarea tarea= new Tarea();
			tarea.setId(rs.getInt("id"));
			tarea.setAcceso(rs.getString("acceso"));
			tarea.setDescripcion(rs.getString("descripcion"));
			tarea.setNombre(rs.getString("nombre"));
			tarea.setEstado(rs.getString("estado"));
			return tarea;
		}
		
	}
	
	//acceso tarea row
	public static final class AccesoMapper implements RowMapper<AccedeTarea>
	{

		@Override
		public AccedeTarea mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccedeTarea accedeTarea = new AccedeTarea();
			accedeTarea.setId(rs.getInt("id"));
			accedeTarea.setIdTarea(rs.getInt("idTarea"));
			accedeTarea.setIdUsuario(rs.getInt("idUsuario"));
			accedeTarea.setModo(rs.getString("modo"));
			
			return accedeTarea;
		}
		
		
	}
}
