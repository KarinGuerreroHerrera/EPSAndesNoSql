package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.Afiliado;

import java.util.List;

public interface AfiliadoRepository extends MongoRepository<Afiliado, String> {

    // Buscar por tipo y número de documento
    List<Afiliado> findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    // Buscar afiliados por nombre
    List<Afiliado> findByNombreContainingIgnoreCase(String nombre);

    // Buscar afiliados nacidos después de una fecha específica
    @Query("{ 'fechaNacimiento': { $gt: ?0 } }")
    List<Afiliado> findByFechaNacimientoAfter(String fecha);

    // Buscar por número de documento exacto
    Afiliado findByNumeroDocumento(String numeroDocumento);
}
