package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.Ips;

import java.util.List;

public interface IpsRepository extends MongoRepository<Ips, String> {

    // Buscar IPS por NIT
    Ips findByNit(String nit);

    // Buscar IPS por nombre (coincidencia parcial, sin distinción de mayúsculas)
    List<Ips> findByNombreContainingIgnoreCase(String nombre);

    // Buscar IPS por ciudad o parte de la dirección
    List<Ips> findByDireccionContainingIgnoreCase(String direccion);

    // Buscar IPS por teléfono exacto
    Ips findByTelefono(String telefono);

    // Consulta personalizada: buscar IPS con NIT que empiece por un valor específico
    @Query("{ 'nit': { $regex: '^?0' } }")
    List<Ips> findByNitPrefix(String nitPrefix);
}
