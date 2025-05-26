package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Afiliado;
import uniandes.edu.co.demo.repository.AfiliadoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/afiliados")
public class AfiliadoController {

    @Autowired
    private AfiliadoRepository afiliadoRepository;

    // RF5
    @PostMapping("/new/save")
    public ResponseEntity<String> crearAfiliado(@RequestBody Afiliado afiliado) {
        try {
            afiliadoRepository.save(afiliado);
            return new ResponseEntity<>("Afiliado creado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el afiliado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un afiliado existente
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarAfiliado(@PathVariable("id") String id, @RequestBody Afiliado afiliado) {
        try {
            Optional<Afiliado> existente = afiliadoRepository.findById(id);
            if (existente.isPresent()) {
                afiliado.setId(id);
                afiliadoRepository.save(afiliado);
                return new ResponseEntity<>("Afiliado actualizado exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Afiliado no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el afiliado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los afiliados
    @GetMapping("")
    public ResponseEntity<List<Afiliado>> obtenerTodosLosAfiliados() {
        try {
            List<Afiliado> afiliados = afiliadoRepository.findAll();
            return ResponseEntity.ok(afiliados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener un afiliado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Afiliado> obtenerAfiliadoPorId(@PathVariable("id") String id) {
        try {
            Optional<Afiliado> afiliado = afiliadoRepository.findById(id);
            return afiliado.map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar un afiliado por su ID
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarAfiliado(@PathVariable("id") String id) {
        try {
            if (afiliadoRepository.existsById(id)) {
                afiliadoRepository.deleteById(id);
                return new ResponseEntity<>("Afiliado eliminado exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Afiliado no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el afiliado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar por tipo y número de documento
    @GetMapping("/buscar-por-documento")
    public ResponseEntity<List<Afiliado>> buscarPorDocumento(
            @RequestParam String tipoDocumento,
            @RequestParam String numeroDocumento) {
        try {
            List<Afiliado> afiliados = afiliadoRepository.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
            return ResponseEntity.ok(afiliados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar afiliados por nombre (parcial, sin importar mayúsculas)
    @GetMapping("/buscar-por-nombre")
    public ResponseEntity<List<Afiliado>> buscarPorNombre(@RequestParam String nombre) {
        try {
            List<Afiliado> afiliados = afiliadoRepository.findByNombreContainingIgnoreCase(nombre);
            return ResponseEntity.ok(afiliados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar afiliados nacidos después de una fecha
    @GetMapping("/nacidos-despues")
    public ResponseEntity<List<Afiliado>> nacidosDespuesDe(@RequestParam String fecha) {
        try {
            List<Afiliado> afiliados = afiliadoRepository.findByFechaNacimientoAfter(fecha);
            return ResponseEntity.ok(afiliados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
