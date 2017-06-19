package ar.edu.unlam.diit.scaw.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.jsf.FacesContextUtils;

import ar.edu.unlam.diit.scaw.entities.TareaYAccede;
import ar.edu.unlam.diit.scaw.entities.Usuario;
import ar.edu.unlam.diit.scaw.services.PersonService;
import ar.edu.unlam.diit.scaw.services.UsuarioService;
import ar.edu.unlam.diit.scaw.services.impl.UsuarioServiceImpl;

@ManagedBean(name="usuarioBean",eager = true)
@SessionScoped
public class UsuarioBean implements Serializable{
	
	//BAJA Y MODI
		private Integer idUsuarioABM;
		
	
	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private String nombre = null;
	private String apellido = null;
	private String contrasena = null;
	private String tipo = null;
	private Integer estaAprobado;
	private String nickName = null;
	
	//Spring Inject
	
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"beans.xml"});
	UsuarioService usuarioService = (UsuarioService) context.getBean("usuarioService");
	
	
	
	//UsuarioServiceImpl usuarioService= new UsuarioServiceImpl();
	
	 public UsuarioBean() {
		 super();
		 this.nombre=null;
		 this.apellido=null;
		 this.contrasena=null;
		 this.tipo=null;
		 this.estaAprobado=null;
		 this.nickName = null;
	}

	 
	 
	 
	 public List<Usuario> getListaDeUsuarios()
	 {
	
		 List<Usuario> list = usuarioService.getListaDeUsuarios();		 
		return list;
	 }
	
	
	 //registro
	 public String registro()
	 {
		 Usuario nuevoUsuario = crearUsuarioConLosDatosDelBean();
		 try {
			usuarioService.crearUsuario(nuevoUsuario);
			//para qye n me lo muestr en el form login el nombre y la contra
			this.nombre = null;
			this.contrasena = null; 
			 this.nickName = null;
			return "index";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 //return "index";
		 this.nombre=null;
		 this.nickName=null;
		 this.contrasena=null;
		 this.apellido=null;
		 
		 return "index";//no tira exepcion este metodo ver
	 }
	 
	 public Usuario crearUsuarioConLosDatosDelBean()
	 {
		 	Usuario usuario= new Usuario();
		 	usuario.setNombre(this.nombre);
		 	usuario.setApellido(this.apellido);
		 	usuario.setContrasena(this.contrasena);
		 	usuario.setEstaAprobado(this.estaAprobado);
		 	usuario.setTipo(this.tipo);
		 	usuario.setNickName(this.nickName);
		 	return usuario;
	 }
	 
	 //Login
	 public String login(){
		

		 try {
			if(usuarioService.getLogueo(this.nickName,this.contrasena)==1)
			 {
				Usuario usuario = usuarioService.getUsuarioPorNickName(this.nombre);
				
				if(usuario.getTipo().equals("usuarioAdministrador"))
				{
					return "usuarioHomeAdministrador";
				}
				else
				{
					return "usuarioHomeNormal";
				}
				 
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "index";
	 }
	
	 //admin
		public List<Usuario> getListaDeUsuariosNoApronados()
		{
			List<Usuario> list = usuarioService.getListaDeUsuariosNoAprobados();
		
			return list;
		}
		
		//aprobar un useer
		public String aprobarUnUsuario()
		{
			//me trae el id
			Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String id2 = params.get("idParam");
			Integer id3 = Integer.parseInt(id2);

			Usuario usuarioAAprobar = usuarioService.buscarUnUsuarioPorId(id3); //me da null el id

			usuarioService.aprobarUsuario(usuarioAAprobar);
			return "usuarioListaDeUsuariosNoAprobados";
		}
		
		//rehaszar user
		public String rechazarUnUsuario()
		{
			
			Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String id2 = params.get("idParam2");
			Integer id3 = Integer.parseInt(id2);

			usuarioService.eliminarUnUsuario(id3);
			return "usuarioListaDeUsuariosNoAprobados";
		}
		
		//eliminar
				public String eliminarUnUsuario()
				{
					
					Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
					String id2 = params.get("idParamEliminar");
					Integer id3 = Integer.parseInt(id2);

					usuarioService.eliminarUnUsuario(id3);
					return "usuarioAdministradorListaDeTodosLosUsuarios";
				}
				
	//modificar
				public String modificarUsuario()
				{
					
					Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
					String id2 = params.get("idParamEditar");
					Integer idUsuarioModif = Integer.parseInt(id2);

					this.idUsuarioABM = idUsuarioModif;
					
					//segun el idDeUsario a modif cambio los datos
				
					Usuario usuarioAModif = usuarioService.buscarUnUsuarioPorId(idUsuarioABM);
					
					this.nombre = usuarioAModif.getNombre();
					this.nickName = usuarioAModif.getNickName();
					this.apellido = usuarioAModif.getApellido();
					this.estaAprobado = usuarioAModif.getEstaAprobado();
					this.contrasena = usuarioAModif.getContrasena();
					
					return "usuarioAdministradorModificarUsuario";
				}
				public String modificarUsuario2()
				{
					
					usuarioService.modificarUsuario(this.idUsuarioABM,this.nickName, this.nombre,this.apellido, this.contrasena, this.estaAprobado);
					
					//vuelvo a setear los valores
					Usuario usuarioAModif = usuarioService.buscarUnUsuarioPorId(idUsuarioABM);//reveer no anda como quiero
					
					this.nombre = usuarioAModif.getNombre();
					this.apellido = usuarioAModif.getApellido();
					this.estaAprobado = usuarioAModif.getEstaAprobado();
					this.nickName = usuarioAModif.getNickName();
					
					
					return "usuarioAdministradorListaDeTodosLosUsuarios";
				}
	 
	 //buscar por nombre
		public Integer getIdDeUnUsuario()// no anda reveer
		{
			if(this.nombre!=null)
			{
				return usuarioService.getUsuarioPorNickName(this.nombre).getId();
				
			}
			return 1;
			
		}
		
		//salir
		public String Salir()
		{
			this.idUsuarioABM = null;
			this.id = null;
			this.nombre = null;
			this.contrasena = null;
			
			return "index";
		}
	 // --
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}




	public void setTipo(String tipo) {
		this.tipo = tipo;
	}




	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}



	public void setUsuarioService(UsuarioServiceImpl usuarioService) {
		this.usuarioService = usuarioService;
	}




	public UsuarioService getUsuarioService() {
		return usuarioService;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	public ApplicationContext getContext() {
		return context;
	}




	public void setContext(ApplicationContext context) {
		this.context = context;
	}




	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}




	public Integer getEstaAprobado() {
		return estaAprobado;
	}




	public void setEstaAprobado(Integer estaAprobado) {
		this.estaAprobado = estaAprobado;
	}




	public Integer getIdUsuarioABM() {
		return idUsuarioABM;
	}




	public void setIdUsuarioABM(Integer idUsuarioABM) {
		this.idUsuarioABM = idUsuarioABM;
	}




	public String getNickName() {
		return nickName;
	}




	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	 
}
