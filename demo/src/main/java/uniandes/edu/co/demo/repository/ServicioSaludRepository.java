package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.ServicioSalud;
import uniandes.edu.co.demo.controller.ServicioSaludController.DisponibilidadDTO;

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

    // RFC1
    @Aggregation(pipeline = {
        "{ $match: { 'codigo': ?0 } }", 
        "{ $match: { 'fechaDisponibilidad': { $gte: ?1, $lte: ?2 } } }", 
        "{ $lookup: { from: 'ips', localField: 'ipsIds', foreignField: '_id', as: 'ips' } }", 
        "{ $lookup: { from: 'medicos', localField: 'medicoId', foreignField: '_id', as: 'medico' } }", 
        "{ $unwind: '$ips' }", 
        "{ $unwind: '$medico' }", 
        "{ $project: { " +
            "nombreServicio: '$nombre', " +
            "fechaDisponibilidad: 1, " +
            "horaDisponibilidad: 1, " +
            "nombreIps: '$ips.nombre', " +
            "nombreMedico: '$medico.nombre', " +
            "_id: 0 " +
        "} }"
    })
    List<DisponibilidadDTO> findAgendaDisponibilidad(String codigoServicio, String fechaInicio, String fechaFin);
}
