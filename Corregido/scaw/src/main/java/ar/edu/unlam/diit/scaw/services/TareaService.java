package ar.edu.unlam.diit.scaw.services;

import java.util.List;

import ar.edu.unlam.diit.scaw.entities.AccedeTarea;
import ar.edu.unlam.diit.scaw.entities.Tarea;

public interface TareaService {

	
		public void guardarTarea(Tarea tarea, Integer idUsuario);
	
		public List<Tarea> getListaDeTareasPorIdUsuario(Integer idUsuario);
		
		public List<Tarea> getListaDeTareas();
		
		public List<Tarea> getListaDeTareasPendientesPorIdDeUsuario(Integer idUsuario);
		
		public List<Tarea> getListaDeTareasCompletaPorIdDeUsuario(Integer idUsuario);
		
		public void setearEstadoTarea(Integer idTarea,String estado);
		
		public AccedeTarea traerUnAccedeTareaPorIdUsuario(Integer idUsuario,Integer idTarea);
		
		public Tarea traerUnaTareaPorId(Integer idTarea);
		
		public void modificarTarea(Integer idTarea, String nombreT, String descripcionT, String estadoT, String accesoT);
		
		//magia //devuelte 
		public List<Tarea> getListaDeTareasCompletasPorIdDeUsuarioConQueryYEstado(Integer idUsuario,String estado);
		
		public void eliminarTareaPorId(Integer idTarea);
		
		public Tarea getTareaConIdDeUsuarioYIdDeTarea(Integer idUsuario,Integer idTarea);
		
		public AccedeTarea getACCEDEConIdDeUsuarioYIdDeTarea(Integer idUsuario, Integer idTarea);
		
		public void crearAccedeTarea (Integer idUsuario,Integer idTarea,String modo );
		
		public List<Tarea> listaDeTareasPorModoDeAccedo(String modo);
		
		public List<Tarea> listaDeTareasPorPUBLICOOPRIVADO(String acceso); 

		public List<AccedeTarea> traerListaDeAccedeTareaPorIdDeTarea(Integer idTarea);

		public void eliminarParticipanteDeUnaTarea(Integer idTarea, Integer idParticipante);


}

