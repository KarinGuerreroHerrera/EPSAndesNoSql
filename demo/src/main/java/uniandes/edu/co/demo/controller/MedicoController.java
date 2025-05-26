package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Medico;
import uniandes.edu.co.demo.repository.MedicoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    // RF4
    @PostMapping("/new/save")
    public ResponseEntity<String> crearMedico(@RequestBody Medico medico) {
        try {
            medicoRepository.save(medico);
            return new ResponseEntity<>("Médico creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el médico: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un médico existente
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarMedico(@PathVariable("id") String id, @RequestBody Medico medico) {
        try {
            Optional<Medico> existente = medicoRepository.findById(id);
            if (existente.isPresent()) {
                medico.setId(id);
                medicoRepository.save(medico);
                return new ResponseEntity<>("Médico actualizado exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Médico no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el médico: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los médicos
    @GetMapping("")
    public ResponseEntity<List<Medico>> obtenerTodosLosMedicos() {
        try {
            List<Medico> medicos = medicoRepository.findAll();
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener médico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerMedicoPorId(@PathVariable("id") String id) {
        try {
            Optional<Medico> medico = medicoRepository.findById(id);
            return medico.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar médico por ID
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarMedico(@PathVariable("id") String id) {
        try {
            if (medicoRepository.existsById(id)) {
                medicoRepository.deleteById(id);
                return new ResponseEntity<>("Médico eliminado exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Médico no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el médico: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar médico por número de documento
    @GetMapping("/por-documento")
    public ResponseEntity<Medico> buscarPorNumeroDocumento(@RequestParam String numeroDocumento) {
        try {
            Medico medico = medicoRepository.findByNumeroDocumento(numeroDocumento);
            if (medico != null) {
                return ResponseEntity.ok(medico);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar médicos por especialidad exacta
    @GetMapping("/por-especialidad")
    public ResponseEntity<List<Medico>> buscarPorEspecialidad(@RequestParam String especialidad) {
        try {
            List<Medico> medicos = medicoRepository.findByEspecialidad(especialidad);
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar médicos por nombre parcial (ignore case)
    @GetMapping("/por-nombre")
    public ResponseEntity<List<Medico>> buscarPorNombre(@RequestParam String nombre) {
        try {
            List<Medico> medicos = medicoRepository.findByNombreContainingIgnoreCase(nombre);
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar médicos adscritos a IPS
    @GetMapping("/adscritos")
    public ResponseEntity<List<Medico>> buscarAdscritos() {
        try {
            List<Medico> medicos = medicoRepository.findByAdscritoIpsTrue();
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar médicos no adscritos a IPS
    @GetMapping("/no-adscritos")
    public ResponseEntity<List<Medico>> buscarNoAdscritos() {
        try {
            List<Medico> medicos = medicoRepository.findByAdscritoIpsFalse();
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar médicos por ID de IPS
    @GetMapping("/por-ips")
    public ResponseEntity<List<Medico>> buscarPorIps(@RequestParam String ipsId) {
        try {
            List<Medico> medicos = medicoRepository.findByIpsId(ipsId);
            return ResponseEntity.ok(medicos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
