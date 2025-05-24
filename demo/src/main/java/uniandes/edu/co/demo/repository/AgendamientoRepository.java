package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.Agendamiento;

import java.util.List;

public interface AgendamientoRepository extends MongoRepository<Agendamiento, String> {

    // Buscar agendamientos por afiliado
    List<Agendamiento> findByAfiliadoId(String afiliadoId);

    // Buscar agendamientos por médico
    List<Agendamiento> findByMedicoId(String medicoId);

    // Buscar agendamientos por estado (ej: "pendiente", "completado")
    List<Agendamiento> findByEstado(String estado);

    // Buscar agendamientos por fecha exacta
    List<Agendamiento> findByFechaAgendamiento(String fechaAgendamiento);

    // Buscar agendamientos por servicio específico (por nombre del servicio embebido)
    @Query("{ 'servicioSalud.nombre': ?0 }")
    List<Agendamiento> findByNombreServicio(String nombreServicio);

    // Buscar todos los agendamientos de un afiliado en una fecha determinada
    @Query("{ 'afiliadoId': ?0, 'fechaAgendamiento': ?1 }")
    List<Agendamiento> findByAfiliadoIdAndFecha(String afiliadoId, String fechaAgendamiento);
}
