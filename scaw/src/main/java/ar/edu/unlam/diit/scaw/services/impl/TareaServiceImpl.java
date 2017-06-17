package ar.edu.unlam.diit.scaw.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlam.diit.scaw.daos.TareaDao;
import ar.edu.unlam.diit.scaw.entities.AccedeTarea;
import ar.edu.unlam.diit.scaw.entities.Tarea;
import ar.edu.unlam.diit.scaw.services.TareaService;

public class TareaServiceImpl implements TareaService{

	@Autowired
	TareaDao tareaDao;
	
	@Override
	public List<Tarea> getListaDeTareas() {
		return tareaDao.getListaDeTareasBDD();
	}
	
	@Override
	public void guardarTarea(Tarea tarea, Integer idUsuario) {
		tareaDao.agregarTareaBdd(tarea, idUsuario);
		
	}
	
	@Override
	public List<Tarea> getListaDeTareasPorIdUsuario(Integer idUsuario) {
		return tareaDao.getListaDeTareasPorIdDeUsuario(idUsuario);
	}
	
	@Override
	public List<Tarea> getListaDeTareasPendientesPorIdDeUsuario(Integer idUsuario) {
		return tareaDao.getListaDeTareasPendientesPorIdDeUsuario(idUsuario);
	}
	
	@Override
	public List<Tarea> getListaDeTareasCompletaPorIdDeUsuario(Integer idUsuario) {
		return tareaDao.getListaDeTareasCompletasPorIdDeUsuario(idUsuario);
	}
	@Override
	public Tarea traerUnaTareaPorId(Integer idTarea) {
		List<Tarea> listaDeTareas = getListaDeTareas();
		
		for(Tarea e:listaDeTareas)
		{
			if(e.getId().equals(idTarea))
			{
				//Tarea t= new Tarea();
				return e;
			}
		}
		return null;
	}
	
	@Override
	public List<Tarea> listaDeTareasPorModoDeAccedo(String modo) {
		return tareaDao.listaDeTareasPorModoDeAccedo(modo);
	}
	
	@Override
	public List<Tarea> listaDeTareasPorPUBLICOOPRIVADO(String acceso) {
		return tareaDao.listaDeTareasPorAccesoDeAccedo(acceso);
	}
	
	@Override
	public AccedeTarea traerUnAccedeTareaPorIdUsuario(Integer idUsuario, Integer idTarea) {
		 
		List<AccedeTarea>listaDeAccedes = tareaDao.traerTodosLosAccedeTareaPorIdTarea(idTarea);
		 
		 for(AccedeTarea acc:listaDeAccedes)
		 {
			 if(acc.getIdTarea().equals(idTarea))
			 {
				 Tarea tarea2= traerUnaTareaPorId(idTarea);
				 return acc;
				 
			 }
		 }
		return null;
	}
	
	public void modificarTarea(Integer idTarea, String nombreT, String descripcionT, String estadoT, String accesoT)
	{
		tareaDao.modificarTarea(idTarea, nombreT, descripcionT, estadoT, accesoT);
		
	}
	
	
	@Override
	public void setearEstadoTarea(Integer idTarea, String estado) {
		tareaDao.setearEstadoTareaBdd(idTarea, estado);
		
	}
	
	/**
	 * 
	 * 
	 * magia
	 * 
	 */
	
	@Override
	public List<Tarea> getListaDeTareasCompletasPorIdDeUsuarioConQueryYEstado(Integer idUsuario, String estado) {
		return tareaDao.getListaDeTareasCompletasPorIdDeUsuarioConQueryYEstado(idUsuario, estado);
		

	}
	
@Override
	public Tarea getTareaConIdDeUsuarioYIdDeTarea(Integer idUsuario, Integer idTarea) {
		List<Tarea> list = tareaDao.getTareaConIdDeUsuarioYIdDeTarea(idUsuario, idTarea);
		for(Tarea e:list)
		{
			if(e.getId().equals(idTarea))
			{
				return e;
			}
		}
		return null;
	}
@Override
public AccedeTarea getACCEDEConIdDeUsuarioYIdDeTarea(Integer idUsuario, Integer idTarea) {
	List<AccedeTarea> list = tareaDao.getACCEDEConIdDeUsuarioYIdDeTarea(idUsuario, idTarea);
	for(AccedeTarea acc : list)
	{
		if(acc.getIdTarea().equals(idTarea))
		{
			return acc;
		}
	}
	return null;
}
	
	@Override
	public void eliminarTareaPorId(Integer idTarea) {
		tareaDao.eliminarTareaPorId(idTarea);		
	}
	
	@Override
	public void crearAccedeTarea(Integer idUsuario, Integer idTarea, String modo) {
		tareaDao.guardarAccedeTarea(idUsuario, idTarea, modo);
		
	}
	
	@Override
	public List<AccedeTarea> traerListaDeAccedeTareaPorIdDeTarea(Integer idTarea) {
		return traerListaDeAccedeTareaPorIdDeTarea(idTarea);
	}
	
	@Override
	public void eliminarParticipanteDeUnaTarea(Integer idTarea, Integer idParticipante) {
		tareaDao.eliminarParticipanteDeUnaTarea( idTarea,  idParticipante);
		
	}
	
}
