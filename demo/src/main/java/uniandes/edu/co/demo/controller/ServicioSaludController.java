package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.ServicioSalud;
import uniandes.edu.co.demo.repository.ServicioSaludRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
public class ServicioSaludController {

    @Autowired
    private ServicioSaludRepository servicioSaludRepository;

    // Crear nuevo servicio
    @PostMapping("/new/save")
    public ResponseEntity<String> crearServicio(@RequestBody ServicioSalud servicio) {
        try {
            servicioSaludRepository.save(servicio);
            return new ResponseEntity<>("Servicio creado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el servicio: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un servicio existente
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarServicio(@PathVariable("id") String id, @RequestBody ServicioSalud servicio) {
        Optional<ServicioSalud> existente = servicioSaludRepository.findById(id);
        if (existente.isPresent()) {
            servicio.setId(id);
            servicioSaludRepository.save(servicio);
            return ResponseEntity.ok("Servicio actualizado correctamente");
        } else {
            return new ResponseEntity<>("Servicio no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un servicio
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarServicio(@PathVariable("id") String id) {
        if (servicioSaludRepository.existsById(id)) {
            servicioSaludRepository.deleteById(id);
            return ResponseEntity.ok("Servicio eliminado correctamente");
        } else {
            return new ResponseEntity<>("Servicio no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Obtener todos los servicios
    @GetMapping("")
    public ResponseEntity<List<ServicioSalud>> obtenerTodosLosServicios() {
        List<ServicioSalud> servicios = servicioSaludRepository.findAll();
        return ResponseEntity.ok(servicios);
    }

    // Obtener servicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicioSalud> obtenerPorId(@PathVariable("id") String id) {
        Optional<ServicioSalud> servicio = servicioSaludRepository.findById(id);
        return servicio.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Buscar por nombre
    @GetMapping("/por-nombre")
    public ResponseEntity<List<ServicioSalud>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(servicioSaludRepository.findByNombre(nombre));
    }

    // Buscar por ID de médico
    @GetMapping("/por-medico")
    public ResponseEntity<List<ServicioSalud>> buscarPorMedicoId(@RequestParam String medicoId) {
        return ResponseEntity.ok(servicioSaludRepository.findByMedicoId(medicoId));
    }

    // Buscar por fecha
    @GetMapping("/por-fecha")
    public ResponseEntity<List<ServicioSalud>> buscarPorFecha(@RequestParam String fecha) {
        return ResponseEntity.ok(servicioSaludRepository.findByFechaDisponibilidad(fecha));
    }

    // Buscar por hora
    @GetMapping("/por-hora")
    public ResponseEntity<List<ServicioSalud>> buscarPorHora(@RequestParam String hora) {
        return ResponseEntity.ok(servicioSaludRepository.findByHoraDisponibilidad(hora));
    }

    // Buscar por ID de IPS
    @GetMapping("/por-ips")
    public ResponseEntity<List<ServicioSalud>> buscarPorIpsId(@RequestParam String ipsId) {
        return ResponseEntity.ok(servicioSaludRepository.findByIpsId(ipsId));
    }

    // Buscar por nombre e IPS
    @GetMapping("/por-nombre-e-ips")
    public ResponseEntity<List<ServicioSalud>> buscarPorNombreYIps(@RequestParam String nombre, @RequestParam String ipsId) {
        return ResponseEntity.ok(servicioSaludRepository.findByNombreAndIpsId(nombre, ipsId));
    }
    
}
