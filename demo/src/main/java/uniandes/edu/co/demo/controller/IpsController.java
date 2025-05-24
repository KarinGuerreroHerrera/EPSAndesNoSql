package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Ips;
import uniandes.edu.co.demo.repository.IpsRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ips")
public class IpsController {

    @Autowired
    private IpsRepository ipsRepository;

    // Crear una nueva IPS
    @PostMapping("/new/save")
    public ResponseEntity<String> crearIps(@RequestBody Ips ips) {
        try {
            ipsRepository.save(ips);
            return new ResponseEntity<>("IPS creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la IPS: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar una IPS existente
    @PostMapping("/{id}/edit/save")
    public ResponseEntity<String> actualizarIps(@PathVariable("id") String id, @RequestBody Ips ips) {
        try {
            Optional<Ips> existente = ipsRepository.findById(id);
            if (existente.isPresent()) {
                ips.setId(id);
                ipsRepository.save(ips);
                return new ResponseEntity<>("IPS actualizada exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("IPS no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la IPS: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todas las IPS
    @GetMapping("")
    public ResponseEntity<List<Ips>> obtenerTodasLasIps() {
        try {
            List<Ips> ipsList = ipsRepository.findAll();
            return ResponseEntity.ok(ipsList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una IPS por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ips> obtenerIpsPorId(@PathVariable("id") String id) {
        try {
            Optional<Ips> ips = ipsRepository.findById(id);
            return ips.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar una IPS por ID
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarIps(@PathVariable("id") String id) {
        try {
            if (ipsRepository.existsById(id)) {
                ipsRepository.deleteById(id);
                return new ResponseEntity<>("IPS eliminada exitosamente", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("IPS no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la IPS: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar IPS por NIT exacto
    @GetMapping("/por-nit")
    public ResponseEntity<Ips> buscarPorNit(@RequestParam String nit) {
        try {
            Ips resultado = ipsRepository.findByNit(nit);
            if (resultado != null) {
                return ResponseEntity.ok(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar IPS por nombre (parcial, sin distinción de mayúsculas)
    @GetMapping("/por-nombre")
    public ResponseEntity<List<Ips>> buscarPorNombre(@RequestParam String nombre) {
        try {
            List<Ips> resultados = ipsRepository.findByNombreContainingIgnoreCase(nombre);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar IPS por dirección (ciudad o parte de la dirección)
    @GetMapping("/por-direccion")
    public ResponseEntity<List<Ips>> buscarPorDireccion(@RequestParam String direccion) {
        try {
            List<Ips> resultados = ipsRepository.findByDireccionContainingIgnoreCase(direccion);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar IPS por teléfono exacto
    @GetMapping("/por-telefono")
    public ResponseEntity<Ips> buscarPorTelefono(@RequestParam String telefono) {
        try {
            Ips resultado = ipsRepository.findByTelefono(telefono);
            if (resultado != null) {
                return ResponseEntity.ok(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Buscar IPS cuyo NIT empiece por un prefijo
    @GetMapping("/por-nit-prefijo")
    public ResponseEntity<List<Ips>> buscarPorNitPrefijo(@RequestParam String prefijo) {
        try {
            List<Ips> resultados = ipsRepository.findByNitPrefix(prefijo);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
