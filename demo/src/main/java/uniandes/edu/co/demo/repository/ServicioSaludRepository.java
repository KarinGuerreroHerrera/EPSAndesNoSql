package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.ServicioSalud;

import java.util.List;

public interface ServicioSaludRepository extends MongoRepository<ServicioSalud, String> {

    // Buscar servicios por nombre
    List<ServicioSalud> findByNombre(String nombre);

    // Buscar servicios por ID del médico
    List<ServicioSalud> findByMedicoId(String medicoId);

    // Buscar servicios disponibles en una fecha específica
    List<ServicioSalud> findByFechaDisponibilidad(String fechaDisponibilidad);

    // Buscar servicios por hora específica
    List<ServicioSalud> findByHoraDisponibilidad(String horaDisponibilidad);

    // Buscar servicios asociados a una IPS específica
    @Query("{ 'ipsIds': ?0 }")
    List<ServicioSalud> findByIpsId(String ipsId);

    // Buscar servicios por nombre e IPS
    @Query("{ 'nombre': ?0, 'ipsIds': ?1 }")
    List<ServicioSalud> findByNombreAndIpsId(String nombre, String ipsId);
}
