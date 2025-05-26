package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import uniandes.edu.co.demo.controller.ServicioSaludController.ServicioFrecuenciaDTO;
import uniandes.edu.co.demo.modelo.Orden;

import java.util.List;

public interface OrdenRepository extends MongoRepository<Orden, String> {
    List<Orden> findByAfiliadoId(String afiliadoId);
    List<Orden> findByMedicoId(String medicoId);
    List<Orden> findByEstado(String estado);
    List<Orden> findByFechaEmision(String fechaEmision);
    
    // Buscar órdenes que contengan un servicio por nombre
    @Query("{ 'detallesOrden.servicioSalud.nombre': ?0 }")
    List<Orden> findByNombreServicioSalud(String nombreServicio);

    // Buscar órdenes que contengan un servicio por código
    @Query("{ 'detallesOrden.servicioSalud.codigo': ?0 }")
    List<Orden> findByCodigoServicioSalud(String codigo);

    
    //RFC2
    @Aggregation(pipeline = {
        "{ $match: { fechaEmision: { $gte: ?0, $lte: ?1 } } }", // Filtrar por rango de fechas
        "{ $unwind: '$detallesOrden' }", // Descomponer la lista de detalles
        "{ $group: { " +
            "_id: '$detallesOrden.servicioSalud.codigo', " + // Agrupar por código del servicio
            "nombre: { $first: '$detallesOrden.servicioSalud.nombre' }, " +
            "descripcion: { $first: '$detallesOrden.servicioSalud.descripcion' }, " +
            "frecuencia: { $sum: 1 } " + // Contar la frecuencia
        "} }",
        "{ $sort: { frecuencia: -1 } }", // Ordenar por frecuencia descendente
        "{ $limit: 20 }", // Limitar a 20 resultados
        "{ $project: { " +
            "codigo: '$_id', " +
            "nombre: 1, " +
            "descripcion: 1, " +
            "frecuencia: 1, " +
            "_id: 0 " + // Excluir el campo _id original
        "} }"
    })
    List<ServicioFrecuenciaDTO> findTop20ServiciosByPeriodo(String fechaInicio, String fechaFin);
}
