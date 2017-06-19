package ar.edu.unlam.diit.scaw.beans;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes.Name;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hsqldb.lib.HashSet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.edu.unlam.diit.scaw.entities.AccedeTarea;
import ar.edu.unlam.diit.scaw.entities.Tarea;
import ar.edu.unlam.diit.scaw.entities.TareaYAccede;
import ar.edu.unlam.diit.scaw.entities.Usuario;
import ar.edu.unlam.diit.scaw.services.TareaService;
import ar.edu.unlam.diit.scaw.services.UsuarioService;


@ManagedBean(name="tareaBean",eager=true)
@SessionScoped
public class TareaBean extends UsuarioBean implements Serializable  {
	
	private static final long serialVersionUID = 10L;
	

	//participante 
	private String participanteNombre;
	private List<Usuario> listaDeParticipantes;
	java.util.HashSet<Usuario> listaDeParticipantesBuscados;
	private String modo;

	
	//logueo
	private Integer idUsuario;
	private String nickNameUsuario;
	private String contrasenaUsuario;
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private String acceso;
	private String estado;
	
	private Map listaAcceso ;// no lo use sacar si no sirve
	//Spring Inject
	
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"beans.xml"});
		TareaService tareaService = (TareaService) context.getBean("tareaService");
		UsuarioService usuarioService = (UsuarioService) context.getBean("usuarioService");
		
		
		public String prueba(){
			return "prueba axel";
		}
	
	
		
	public TareaBean() {
		super();
		
		this.nombre = null;
		this.descripcion = null;
		this.acceso =  null;
		this.estado = null;
		
	}
	
	public Tarea crearTareaEnBean()
	{
		Tarea t= new Tarea();
		t.setAcceso(this.acceso);
		t.setDescripcion(this.descripcion);
		t.setNombre(this.nombre);
		return t;
	}
	
	private Integer setearIdUsuario()
	{
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id2 = params.get("idUsuarioParam");
		Integer id3 = Integer.parseInt(id2);
		return id3;
		
	}
	//anonimos
	 
		
	
	//para ver si se soluciona eso del id
	 //Login -- lo puse en este bean pq no podia consegui el usuario id
	 public String loginUsuarioEnTarea(){
		 
		 try {
			 
				
			
			if(usuarioService.getLogueo(this.nickNameUsuario,this.contrasenaUsuario)==1)
			 {	
				
				Usuario usuario = usuarioService.getUsuarioPorNickName(this.nickNameUsuario);
				
				this.idUsuario = usuarioService.getUsuarioPorNickName(this.nickNameUsuario).getId();
				
				
				if(usuario.getTipo().equals("usuarioAdministrador"))
				{
					return "usuarioHomeAdministrador";
				}
				else
				{
					/*
					this.idUsuario = null;
					this.nombreUsuario = null;
					this.contrasenaUsuario = null;
					this.nombre = null;
					this.nombreUsuario=null;
					this.contrasenaUsuario =null;*/
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
	
	
	public String agregarTarea()
	{	
		
		
		Tarea t= crearTareaEnBean();
			
		
		tareaService.guardarTarea(t, usuarioService.getUsuarioPorNickName(nickNameUsuario).getId()); //ARREGLAR EL TEMA DEL ID
		//tareaService.guardarTarea(t, 1);
		this.nombre = null;
		this.descripcion = null;
		this.acceso = null;
		
		return "usuarioNormalListaDeTareas";
	}
	
	
	public String agregarParticipanteAUnaTarea()
	{
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id2 = params.get("idAgregarParticipante");
		Integer id3 = Integer.parseInt(id2);
		
		tareaService.crearAccedeTarea(id3, this.id, this.modo);
		
		return verTarea2();
	}
	
	public String eliminarParticipanteAUnaTarea()
	{
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id2 = params.get("nickNameUsuarioAEliminar");
		
		System.out.println("LA PUTA MADRE " + id2);
		tareaService.eliminarParticipanteDeUnaTarea(this.id,usuarioService.getUsuarioPorNickName(id2).getId());
		return verTarea2();
	}
	
	//salir
	public String Salir()
	{
		this.idUsuario = null;
		this.contrasenaUsuario = null;
		this.nombre = null;
		this.nickNameUsuario=null;
		this.contrasenaUsuario =null;
		
		
		return "index";
	}
	
	public List<Tarea> getListaDeTareasPorId()
	{
		List<Tarea> lista = tareaService.getListaDeTareasPorIdUsuario(usuarioService.getUsuarioPorNickName(this.nickNameUsuario).getId());
		return lista;
		
	}
	
	
	
	
	public List<Tarea> getTareaPorId()
	{
		
		Tarea t= tareaService.traerUnaTareaPorId(this.id);
		List<Tarea> lista = new ArrayList<>();
		lista.add(t);
		return lista;
		
	}
	
	public java.util.HashSet<Usuario> getListaDeParticipantesDeUnaTarea()
	{
//		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//		String id2 = params.get("idTareaAVer");
//		Integer id3 = Integer.parseInt(id2);
		
		
		List<Usuario > lista= usuarioService.getListaDeParticipantesDeUnaTareaPorIdTarea(this.id);
		java.util.HashSet<Usuario> listaEnvidad = new java.util.HashSet<Usuario>();
		
	
		
		for(Usuario e:lista)
		{
			if(e.getId().equals(this.idUsuario)==false)
			{
				listaEnvidad.add(e);
			}
		}
		
		
		
		if(lista.size()>0)
		{
			return listaEnvidad ;
			
		}
		else{
			 lista.clear();
			return listaEnvidad;
			
		}
		
	}
	
	public String verTarea()
	{
		
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id2 = params.get("idTareaAVer");
		
		Integer id3 = Integer.parseInt(id2);
		this.id=id3; //seteo el id de la tarea
		
		
		System.out.println(tareaService.getACCEDEConIdDeUsuarioYIdDeTarea(this.idUsuario, id3).getModo() + "miraaaaaaaaaaaaaaaaa");
		if(tareaService.getACCEDEConIdDeUsuarioYIdDeTarea(this.idUsuario, id3).getModo().equals("escritura"))
		{
			
			return "usuarioNomalVerTareaEscritura";
			
		}
		
		else
		{
			return "usuarioNomalVerTareaLectura";
		}
	}

	public String verTarea2()//para el buscar
	{
		

		
		if(tareaService.getACCEDEConIdDeUsuarioYIdDeTarea(this.idUsuario, this.id).getModo().equals("escritura"))
		{
			
			return "usuarioNomalVerTareaEscritura";
			
		}
		else
		{
			return "usuarioNomalVerTareaLectura";
		}
		
		
		
	}
	
	public List<Tarea> getlistaDeTareasAnonimas()
	{
		
		return tareaService.listaDeTareasPorPUBLICOOPRIVADO("publica");
	}
	
	
	public String editar()
	{
		Tarea t = tareaService.traerUnaTareaPorId(this.id);
		this.nombre = t.getNombre();
		this.descripcion= t.getDescripcion();
		this.acceso = t.getAcceso();
		this.estado = t.getEstado();
		return "usuarioNomalVerTareaEditar";
	}
	
	public String editarTarea2()
	{
		
		tareaService.modificarTarea(this.id,this.nombre, this.descripcion, this.estado, this.acceso);
		this.nombre =null;
		
		this.descripcion =null;
		this.estado=null;
		this.acceso=null;
		
		return verTarea();
			
	}
	
	//buscar participante
	public java.util.HashSet<Usuario> getListaDeUsuariosBuscador()
	{
		
		
		
		return this.listaDeParticipantesBuscados;
		
	}
	
	public String buscarButton()
	{
		setlistaDeParticipantes(new ArrayList<Usuario>());
		setListaDeParticipantesBuscados(new java.util.HashSet<Usuario>());

		return	"usuarioNormalBuscarParticipante";
	}
	public void buscarUsuarios()
	{
		List<Usuario> lista = usuarioService.buscaUsuariosEnLaBDDConLike(this.participanteNombre ,usuarioService.getUsuarioPorNickName(this.nickNameUsuario).getId());
		java.util.HashSet<Usuario> lista2 = new java.util.HashSet<Usuario>();
		
		for(Usuario e: lista)
		{
			if(usuarioService.getListaDeParticipantesDeUnaTareaPorIdTarea(this.id).contains(e)==false)
			{
				lista2.add(e);
			}
		}
			
	
		setListaDeParticipantesBuscados(lista2);
		
	}
	
	
	public List<Tarea> getListaDeTareasPENDIENTESPorId()
	{
		
		
		return tareaService.getListaDeTareasCompletasPorIdDeUsuarioConQueryYEstado(usuarioService.getUsuarioPorNickName(this.nickNameUsuario).getId(),"pendiente");

		
	}
	
	public List<Tarea> getListaDeTareasCOMPLETASPorId()
	{
	
		return tareaService.getListaDeTareasCompletasPorIdDeUsuarioConQueryYEstado(usuarioService.getUsuarioPorNickName(this.nickNameUsuario).getId(),"completa");
		
		
	}
	
	/*
	 * magia
	 * */
	
	
	
	public Map<String,String> getListaAcceso()
	{
		this.listaAcceso = new HashMap<String,String>();		
		listaAcceso.put("privada","privada");
		listaAcceso.put("publica","publica");
		return listaAcceso;
		
	}
	
	
	
	
	public List<Tarea> getListaDeTodasLasTareas()
	{
		List<Tarea> lista = tareaService.getListaDeTareas();
		return lista;
		
	}
	//setear estado
	public String setearEstadoDeTarea()
	{
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id2 = params.get("idTareaACompletar");
		Integer id3 = Integer.parseInt(id2);
		
		tareaService.setearEstadoTarea(id3, "completa");
		
		return "usuarioNormalListaDeTareas";
		
	}
	public String setearEstadoDeTarea2()
	{
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id2 = params.get("idTareaAPendiente");
		Integer id3 = Integer.parseInt(id2);
		
		tareaService.setearEstadoTarea(id3, "pendiente");
		
		return "usuarioNormalListaDeTareas";
		
	}
	
	public String eliminarTarea()
	{
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id2 = params.get("idTareaAEliminar");
		Integer id3 = Integer.parseInt(id2);
		
		tareaService.eliminarTareaPorId(id3);
		
		return "usuarioNormalListaDeTareas";
	}
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public TareaService getTareaService() {
		return tareaService;
	}

	public void setTareaService(TareaService tareaService) {
		this.tareaService = tareaService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setListaAcceso(Map listaAcceso) {
		this.listaAcceso = listaAcceso;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}



	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}


	public String getContrasenaUsuario() {
		return contrasenaUsuario;
	}

	public void setContrasenaUsuario(String contrasenaUsuario) {
		this.contrasenaUsuario = contrasenaUsuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getParticipanteNombre() {
		return participanteNombre;
	}

	public void setParticipanteNombre(String participanteNombre) {
		this.participanteNombre = participanteNombre;
	}
	
	public List<Usuario> getlistaDeParticipantes() {
		return listaDeParticipantes;
	}

	public void setlistaDeParticipantes(List<Usuario> listaDeParticipantes) {
		this.listaDeParticipantes = listaDeParticipantes;
	}
	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public List<Usuario> getListaDeParticipantes() {
		return listaDeParticipantes;
	}

	public void setListaDeParticipantes(List<Usuario> listaDeParticipantes) {
		this.listaDeParticipantes = listaDeParticipantes;
	}

	public String getNickNameUsuario() {
		return nickNameUsuario;
	}

	public void setNickNameUsuario(String nickNameUsuario) {
		this.nickNameUsuario = nickNameUsuario;
	}

	public java.util.HashSet<Usuario> getListaDeParticipantesBuscados() {
		return listaDeParticipantesBuscados;
	}

	public void setListaDeParticipantesBuscados(java.util.HashSet<Usuario> listaDeParticipantesBuscados) {
		this.listaDeParticipantesBuscados = listaDeParticipantesBuscados;
	}


	
	
	
	
	
}
