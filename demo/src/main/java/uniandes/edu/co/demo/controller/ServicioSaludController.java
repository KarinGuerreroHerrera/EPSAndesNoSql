package uniandes.edu.co.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import uniandes.edu.co.demo.modelo.Medico;
import uniandes.edu.co.demo.modelo.ServicioSalud;
import uniandes.edu.co.demo.modelo.ServicioSalud.AgendamientoRequest;
import uniandes.edu.co.demo.modelo.Ips;
import uniandes.edu.co.demo.repository.MedicoRepository;
import uniandes.edu.co.demo.repository.OrdenRepository;
import uniandes.edu.co.demo.repository.ServicioSaludRepository;
import uniandes.edu.co.demo.repository.IpsRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/servicios")
public class ServicioSaludController {

    @Autowired
    private ServicioSaludRepository servicioSaludRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private IpsRepository ipsRepository;
     
    @Autowired
    private MedicoRepository medicoRepository;

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

    // RF7
    @GetMapping("/{id}/disponibilidad")
    public ResponseEntity<List<ServicioSalud.Disponibilidad>> consultarDisponibilidad(@PathVariable("id") String id) {
        Optional<ServicioSalud> servicioOpt = servicioSaludRepository.findById(id);
        if (servicioOpt.isPresent()) {
            ServicioSalud servicio = servicioOpt.get();
            List<ServicioSalud.Disponibilidad> disponibilidades = servicio.getDisponibilidades();

            // Filtrar disponibilidades para las próximas 4 semanas
            LocalDate hoy = LocalDate.now();
            LocalDate limite = hoy.plusWeeks(4);
            List<ServicioSalud.Disponibilidad> proximasDisponibilidades = disponibilidades.stream()
                .filter(d -> {
                    LocalDate fecha = LocalDate.parse(d.getFecha());
                    return fecha.isAfter(hoy) && fecha.isBefore(limite) && d.isDisponible();
                })
                .collect(Collectors.toList());

            return ResponseEntity.ok(proximasDisponibilidades);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // RF7
    @PostMapping("/{id}/agendar")
    public ResponseEntity<String> agendarServicio(@PathVariable("id") String id, @RequestBody AgendamientoRequest request) {
        Optional<ServicioSalud> servicioOpt = servicioSaludRepository.findById(id);
        if (servicioOpt.isPresent()) {
            ServicioSalud servicio = servicioOpt.get();

            // Verificar si el servicio requiere orden
            boolean requiereOrden = !servicio.getTipo().equals("general") && !servicio.getTipo().equals("urgencia");
            if (requiereOrden && (request.getOrdenId() == null || !ordenRepository.existsById(request.getOrdenId()))) {
                return new ResponseEntity<>("Se requiere una orden de servicio válida", HttpStatus.BAD_REQUEST);
            }

            // Verificar disponibilidad
            boolean disponible = servicio.getDisponibilidades().stream()
                .anyMatch(d -> d.getFecha().equals(request.getFecha()) && 
                              d.getHora().equals(request.getHora()) && 
                              d.isDisponible());
            if (!disponible) {
                return new ResponseEntity<>("La fecha y hora seleccionadas no están disponibles", HttpStatus.BAD_REQUEST);
            }

            // Marcar como no disponible
            servicio.getDisponibilidades().stream()
                .filter(d -> d.getFecha().equals(request.getFecha()) && d.getHora().equals(request.getHora()))
                .forEach(d -> d.setDisponible(false));
            servicioSaludRepository.save(servicio);

            // Aquí podrías registrar la cita agendada en otra colección, si el sistema lo requiere
            return new ResponseEntity<>("Servicio agendado exitosamente", HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //RFC 1
    @GetMapping("/agenda")
    public ResponseEntity<List<DisponibilidadDTO>> obtenerAgendaDisponibilidad(@RequestParam String codigoServicio) {
        try {
            LocalDate hoy = LocalDate.now();
            String fechaInicio = hoy.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate fin = hoy.plusWeeks(4);
            String fechaFin = fin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<DisponibilidadDTO> agenda = servicioSaludRepository.findAgendaDisponibilidad(codigoServicio, fechaInicio, fechaFin);
            if (agenda.isEmpty()) {
                return ResponseEntity.noContent().build(); 
            }
            return ResponseEntity.ok(agenda); 
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // RFC 2

    @GetMapping("/top20")
    public ResponseEntity<List<ServicioFrecuenciaDTO>> obtenerTop20Servicios(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        try {
            List<ServicioFrecuenciaDTO> top20 = ordenRepository.findTop20ServiciosByPeriodo(fechaInicio, fechaFin);
            if (top20.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 si no hay resultados
            }
            return ResponseEntity.ok(top20); // 200 con la lista de DTOs
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 500 en caso de error
        }
    }

    public static class ServicioFrecuenciaDTO {
    private String codigo;
    private String nombre;
    private String descripcion;
    private int frecuencia;

    public ServicioFrecuenciaDTO() {}

    public ServicioFrecuenciaDTO(String codigo, String nombre, String descripcion, int frecuencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.frecuencia = frecuencia;
    }

    // Getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}

    // Clase DTO para la respuesta
    public static class DisponibilidadDTO {
        private String nombreServicio;
        private String fechaDisponibilidad;
        private String horaDisponibilidad;
        private String nombreIps;
        private String nombreMedico;

        public DisponibilidadDTO(String nombreServicio, String fechaDisponibilidad, String horaDisponibilidad, String nombreIps, String nombreMedico) {
            this.nombreServicio = nombreServicio;
            this.fechaDisponibilidad = fechaDisponibilidad;
            this.horaDisponibilidad = horaDisponibilidad;
            this.nombreIps = nombreIps;
            this.nombreMedico = nombreMedico;
        }
        public String getNombreServicio() {
            return nombreServicio;
        }
        public void setNombreServicio(String nombreServicio) {
            this.nombreServicio = nombreServicio;
        }
        public String getFechaDisponibilidad() {
            return fechaDisponibilidad;
        }
        public void setFechaDisponibilidad(String fechaDisponibilidad) {
            this.fechaDisponibilidad = fechaDisponibilidad;
        }
        public String getHoraDisponibilidad() {
            return horaDisponibilidad;
        }
        public void setHoraDisponibilidad(String horaDisponibilidad) {
            this.horaDisponibilidad = horaDisponibilidad;
        }
        public String getNombreIps() {
            return nombreIps;
        }
        public void setNombreIps(String nombreIps) {
            this.nombreIps = nombreIps;
        }
        public String getNombreMedico() {
            return nombreMedico;
        }
        public void setNombreMedico(String nombreMedico) {
            this.nombreMedico = nombreMedico;
        }
    }   
}
