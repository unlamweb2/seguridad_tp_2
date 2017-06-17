package ar.edu.unlam.diit.scaw.daos;

import java.util.List;

import ar.edu.unlam.diit.scaw.entities.Usuario;

public interface UsuarioDao {
	
	public void guardarUsuarioBDD(Usuario usuario) ;
	
	public List<Usuario> getListaUsuariosBDD();
	
	public Usuario getUnUsuarioPorIdBdd(Integer idUsuario);
	
	public List<Usuario> getListaDeUsuariosNoAprobadosBDD();
	
	public void aprobarUsuarioBdd(Usuario usuario);
	
	public void eliminarUnUsuarioBdd(Integer idUsuario);
	
	public void modificarUsuario(Integer idUsuario,String nickName,String nombreU,String apellidoU,String contrasenaU,Integer estaParobado);
	
	public List<Usuario> getUsuarioPorNickNameBdd(String nickName);
	
	
	public List<Usuario>buscaUsuariosEnLaBDD(String nombreUsuario,Integer idUsuarioActual);
	
	public List<Usuario> getListaDeParticipantesDeUnaTareaPorIdTarea(Integer idTarea);
	
	
	
}
