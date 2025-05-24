package uniandes.edu.co.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uniandes.edu.co.demo.modelo.Terapia;

import java.util.List;

public interface TerapiaRepository extends MongoRepository<Terapia, String> {

    // Buscar terapias por afiliado
    List<Terapia> findByAfiliadoId(String afiliadoId);

    // Buscar terapias por tipo
    List<Terapia> findByTipoTerapia(String tipoTerapia);

    // Buscar terapias por tipo y afiliado
    List<Terapia> findByTipoTerapiaAndAfiliadoId(String tipoTerapia, String afiliadoId);

    // Buscar terapias con número mínimo de sesiones
    @Query("{ 'numeroSesiones': { $gte: ?0 } }")
    List<Terapia> findByNumeroSesionesMayorOIgualA(int minimoSesiones);
}
