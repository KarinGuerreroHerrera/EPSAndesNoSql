package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.Medico;

import java.util.List;

public interface MedicoRepository extends MongoRepository<Medico, String> {

    // Buscar por número de documento
    Medico findByNumeroDocumento(String numeroDocumento);

    // Buscar por especialidad exacta
    List<Medico> findByEspecialidad(String especialidad);

    // Buscar por nombre (coincidencia parcial, sin distinguir mayúsculas)
    List<Medico> findByNombreContainingIgnoreCase(String nombre);

    // Buscar médicos adscritos a IPS
    List<Medico> findByAdscritoIpsTrue();

    // Buscar médicos no adscritos a IPS
    List<Medico> findByAdscritoIpsFalse();

    // Buscar médicos por ID de una IPS específica
    @Query("{ 'ipsIds': ?0 }")
    List<Medico> findByIpsId(String ipsId);

    //RF6

    
}
