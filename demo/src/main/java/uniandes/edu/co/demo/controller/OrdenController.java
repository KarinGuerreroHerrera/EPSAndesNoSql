package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Orden;
import uniandes.edu.co.demo.repository.OrdenRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenRepository ordenRepository;

    // Crear nueva orden
    @PostMapping("/new/save")
    public ResponseEntity<String> crearOrden(@RequestBody Orden orden) {
        try {
            ordenRepository.save(orden);
            return new ResponseEntity<>("Orden creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la orden: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar una orden existente
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarOrden(@PathVariable("id") String id, @RequestBody Orden orden) {
        Optional<Orden> existente = ordenRepository.findById(id);
        if (existente.isPresent()) {
            orden.setId(id);
            ordenRepository.save(orden);
            return ResponseEntity.ok("Orden actualizada correctamente");
        } else {
            return new ResponseEntity<>("Orden no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar orden por ID
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarOrden(@PathVariable("id") String id) {
        if (ordenRepository.existsById(id)) {
            ordenRepository.deleteById(id);
            return ResponseEntity.ok("Orden eliminada correctamente");
        } else {
            return new ResponseEntity<>("Orden no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todas las órdenes
    @GetMapping("")
    public ResponseEntity<List<Orden>> obtenerTodasLasOrdenes() {
        List<Orden> ordenes = ordenRepository.findAll();
        return ResponseEntity.ok(ordenes);
    }

    // Obtener orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerOrdenPorId(@PathVariable("id") String id) {
        Optional<Orden> orden = ordenRepository.findById(id);
        return orden.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Buscar por afiliado
    @GetMapping("/por-afiliado")
    public ResponseEntity<List<Orden>> buscarPorAfiliado(@RequestParam String afiliadoId) {
        List<Orden> ordenes = ordenRepository.findByAfiliadoId(afiliadoId);
        return ResponseEntity.ok(ordenes);
    }

    // Buscar por médico
    @GetMapping("/por-medico")
    public ResponseEntity<List<Orden>> buscarPorMedico(@RequestParam String medicoId) {
        List<Orden> ordenes = ordenRepository.findByMedicoId(medicoId);
        return ResponseEntity.ok(ordenes);
    }

    // Buscar por estado
    @GetMapping("/por-estado")
    public ResponseEntity<List<Orden>> buscarPorEstado(@RequestParam String estado) {
        List<Orden> ordenes = ordenRepository.findByEstado(estado);
        return ResponseEntity.ok(ordenes);
    }

    // Buscar por fecha de emisión
    @GetMapping("/por-fecha")
    public ResponseEntity<List<Orden>> buscarPorFechaEmision(@RequestParam String fecha) {
        List<Orden> ordenes = ordenRepository.findByFechaEmision(fecha);
        return ResponseEntity.ok(ordenes);
    }

    // Buscar por nombre del servicio de salud
    @GetMapping("/por-servicio-nombre")
    public ResponseEntity<List<Orden>> buscarPorNombreServicio(@RequestParam String nombre) {
        List<Orden> ordenes = ordenRepository.findByNombreServicioSalud(nombre);
        return ResponseEntity.ok(ordenes);
    }

    // Buscar por código del servicio de salud
    @GetMapping("/por-servicio-codigo")
    public ResponseEntity<List<Orden>> buscarPorCodigoServicio(@RequestParam String codigo) {
        List<Orden> ordenes = ordenRepository.findByCodigoServicioSalud(codigo);
        return ResponseEntity.ok(ordenes);
    }
}
