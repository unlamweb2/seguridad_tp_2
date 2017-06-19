package ar.edu.unlam.diit.scaw.services.impl;

import java.util.List;

import javax.enterprise.inject.New;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlam.diit.scaw.daos.PersonDao;
import ar.edu.unlam.diit.scaw.daos.UsuarioDao;
import ar.edu.unlam.diit.scaw.daos.impl.UsuarioDaoImpl;
import ar.edu.unlam.diit.scaw.entities.Usuario;
import ar.edu.unlam.diit.scaw.services.UsuarioService;
import junit.framework.Assert;

public class UsuarioServiceImpl implements UsuarioService {

	
	@Autowired
	UsuarioDao usuarioDao;
	
	//UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl();
	
	@Override
	public List<Usuario> getListaDeUsuarios() {
		
		
		
		
	
		return usuarioDao.getListaUsuariosBDD();
	}
	
	@Override
	public Usuario getUsuarioPorNickName(String nickName) {
		
	
		List<Usuario> listaUsuarios = usuarioDao.getUsuarioPorNickNameBdd(nickName);
		
		for(Usuario e:listaUsuarios)
		{

			if(e.getNickName().equals(nickName))
			{
				
				return e;
				
			}
			
		}
		
		return null;
	}
	
	//MODIFICAR USUARIO
	 public void modificarUsuario(Integer idUsuario, String nickname,String nombreU, String apellidoU, String contrasenaU, Integer estaParobado) {
		 
		 usuarioDao.modificarUsuario(idUsuario,nickname, nombreU, apellidoU, contrasenaU, estaParobado);
		 
	 };
	//registro
	@Override
	public void crearUsuario(Usuario usuario) throws Exception {
		
		if(getUsuarioPorNickName(usuario.getNickName())==null)
		{
			
			usuarioDao.guardarUsuarioBDD(usuario);
		}
		else
		{
			throw new Exception("Usuario existente");
		}
		
	}
	//logueeo
	@Override
	public Integer getLogueo(String nickName, String contrasena) throws Exception {//si retorna uno accede
		
	
		if(getUsuarioPorNickName(nickName).getId()!=null)
		{
			Usuario usuario=getUsuarioPorNickName(nickName);
			if(usuario.getEstaAprobado() == 1)
			{
				if(usuario.getContrasena().equals(contrasena))
				{
					return 1;
				}
				else
				{
					throw new Exception("Acesso denegadoPorqueNoEstaAprobao");
				}
				
			}
			else
			{
				throw new Exception("Acesso denegado2");
			}
			
		}
		else
		{
			
			throw new Exception("Acesso denegado3");
			
			
		}
	}
	//apribar uauarios
	
	@Override
	public List<Usuario> getListaDeUsuariosNoAprobados() {
		return usuarioDao.getListaDeUsuariosNoAprobadosBDD();
	}
	
	@Override
	public Usuario buscarUnUsuarioPorId(Integer id) {
		
		System.out.println("id buscado " + id);
		for(Usuario e:usuarioDao.getListaUsuariosBDD())
		{
			System.out.println("id probables : : " + e.getId());
			if(e.getId().equals(id))
			{
				System.out.println("id final : : " + e.getId());
				return e;
			}
			
			
		}
		return null;
	
	}
	
	@Override
	public List<Usuario> buscaUsuariosEnLaBDDConLike(String nombreUsuario,Integer usuarioActual) {
		return  usuarioDao.buscaUsuariosEnLaBDD(nombreUsuario,usuarioActual);
	}
	
	@Override
	public List<Usuario> getListaDeParticipantesDeUnaTareaPorIdTarea(Integer idTarea) {
	
		return usuarioDao.getListaDeParticipantesDeUnaTareaPorIdTarea(idTarea);
	}
	
	
	@Override
	public void aprobarUsuario(Usuario usuario) {
		
		usuarioDao.aprobarUsuarioBdd(usuario);
		
		
	}
	@Override
	public void eliminarUnUsuario(Integer idUsuario) {
		usuarioDao.eliminarUnUsuarioBdd(idUsuario);
		
	}
	
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDaoImpl usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	



	

	
}
