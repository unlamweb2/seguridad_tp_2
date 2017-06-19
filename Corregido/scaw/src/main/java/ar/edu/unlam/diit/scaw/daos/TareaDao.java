package ar.edu.unlam.diit.scaw.daos;

import java.util.List;

import ar.edu.unlam.diit.scaw.entities.AccedeTarea;
import ar.edu.unlam.diit.scaw.entities.Tarea;
import ar.edu.unlam.diit.scaw.entities.Usuario;

public interface TareaDao {

		public  void agregarTareaBdd (Tarea tarea,Integer idUsuario);
		
		public List<Tarea> getListaDeTareasBDD();
		
		public List<Tarea>  getListaDeTareasPorIdDeUsuario (Integer idUsuario);
		
		public List<Tarea> getListaDeTareasPendientesPorIdDeUsuario(Integer idUsuario);
		
		public List<Tarea> getListaDeTareasCompletasPorIdDeUsuario(Integer idUsuario);
		
		//magia
		public List<Tarea> getListaDeTareasCompletasPorIdDeUsuarioConQueryYEstado(Integer idUsuario,String estado);
		
		public void setearEstadoTareaBdd(Integer idTarea,String estado);
		
		//public AccedeTarea traerUnAccedeTareaPorIdDeTarea(Integer idTarea,Integer idUsuario);
		public List<AccedeTarea> traerTodosLosAccedeTareaPorIdTarea(Integer idTarea);
		
		
		public void modificarTarea(Integer idTarea,String nombreT,String descripcionT,String estadoT,String accesoT);
		
		public void eliminarTareaPorId(Integer idTarea);
		
		
		public List<Tarea> getTareaConIdDeUsuarioYIdDeTarea(Integer idUsuario,Integer idTarea);
		
		public List<AccedeTarea> getACCEDEConIdDeUsuarioYIdDeTarea(Integer idUsuario,Integer idTarea);
		
		public void guardarAccedeTarea (Integer idUsuario,Integer idTarea,String modo );
		
		public List<Tarea> listaDeTareasPorModoDeAccedo(String modo); 
		
		public List<Tarea> listaDeTareasPorAccesoDeAccedo(String acceso);
		
		public List<AccedeTarea> traerListaDeAccedeTareaPorIdDeTarea(Integer idTarea);

		public void eliminarParticipanteDeUnaTarea(Integer idTarea, Integer idParticipante);

}
