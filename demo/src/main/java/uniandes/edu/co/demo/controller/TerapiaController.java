package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Terapia;
import uniandes.edu.co.demo.repository.TerapiaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/terapias")
public class TerapiaController {

    @Autowired
    private TerapiaRepository terapiaRepository;

    // Crear nueva terapia
    @PostMapping("/new/save")
    public ResponseEntity<String> crearTerapia(@RequestBody Terapia terapia) {
        try {
            terapiaRepository.save(terapia);
            return new ResponseEntity<>("Terapia creada correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear terapia: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar terapia existente
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarTerapia(@PathVariable("id") String id, @RequestBody Terapia terapia) {
        Optional<Terapia> existente = terapiaRepository.findById(id);
        if (existente.isPresent()) {
            terapia.setId(id);
            terapiaRepository.save(terapia);
            return ResponseEntity.ok("Terapia actualizada correctamente");
        } else {
            return new ResponseEntity<>("Terapia no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una terapia
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarTerapia(@PathVariable("id") String id) {
        if (terapiaRepository.existsById(id)) {
            terapiaRepository.deleteById(id);
            return ResponseEntity.ok("Terapia eliminada correctamente");
        } else {
            return new ResponseEntity<>("Terapia no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todas las terapias
    @GetMapping("")
    public ResponseEntity<List<Terapia>> obtenerTodas() {
        return ResponseEntity.ok(terapiaRepository.findAll());
    }

    // Obtener terapia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Terapia> obtenerPorId(@PathVariable("id") String id) {
        Optional<Terapia> terapia = terapiaRepository.findById(id);
        return terapia.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Buscar terapias por afiliado
    @GetMapping("/por-afiliado")
    public ResponseEntity<List<Terapia>> buscarPorAfiliado(@RequestParam String afiliadoId) {
        return ResponseEntity.ok(terapiaRepository.findByAfiliadoId(afiliadoId));
    }

    // Buscar terapias por tipo
    @GetMapping("/por-tipo")
    public ResponseEntity<List<Terapia>> buscarPorTipo(@RequestParam String tipoTerapia) {
        return ResponseEntity.ok(terapiaRepository.findByTipoTerapia(tipoTerapia));
    }

    // Buscar terapias por tipo y afiliado
    @GetMapping("/por-tipo-y-afiliado")
    public ResponseEntity<List<Terapia>> buscarPorTipoYAfiliado(@RequestParam String tipoTerapia, @RequestParam String afiliadoId) {
        return ResponseEntity.ok(terapiaRepository.findByTipoTerapiaAndAfiliadoId(tipoTerapia, afiliadoId));
    }

    // Buscar terapias con un número mínimo de sesiones
    @GetMapping("/por-min-sesiones")
    public ResponseEntity<List<Terapia>> buscarPorMinimoSesiones(@RequestParam int minimoSesiones) {
        return ResponseEntity.ok(terapiaRepository.findByNumeroSesionesMayorOIgualA(minimoSesiones));
    }
}
