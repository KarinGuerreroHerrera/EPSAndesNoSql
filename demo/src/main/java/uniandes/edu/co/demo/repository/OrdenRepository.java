package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.Orden;

import java.util.List;

public interface OrdenRepository extends MongoRepository<Orden, String> {

    // Buscar órdenes por ID del afiliado
    List<Orden> findByAfiliadoId(String afiliadoId);

    // Buscar órdenes por ID del médico
    List<Orden> findByMedicoId(String medicoId);

    // Buscar órdenes por estado (ej: PENDIENTE, APROBADA, CANCELADA)
    List<Orden> findByEstado(String estado);

    // Buscar órdenes por fecha exacta de emisión
    List<Orden> findByFechaEmision(String fechaEmision);

    // Buscar órdenes que tengan un servicio de salud específico (por nombre)
    @Query("{ 'detalleOrden.servicioSalud.nombre': ?0 }")
    List<Orden> findByNombreServicioSalud(String nombreServicio);

    // Buscar órdenes por código del servicio de salud
    @Query("{ 'detalleOrden.servicioSalud.codigo': ?0 }")
    List<Orden> findByCodigoServicioSalud(String codigo);
}
