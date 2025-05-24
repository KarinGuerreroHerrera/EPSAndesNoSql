package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "agendamientos")
public class Agendamiento {

    @Id
    private String id;
    private String afiliadoId;
    private String medicoId;
    private String fechaAgendamiento;
    private String horaAgendamiento;
    private String estado;
    private ServicioSalud servicioSalud;

    // Constructor vacío
    public Agendamiento() {}

    // Constructor completo
    public Agendamiento(String id, String afiliadoId, String medicoId, String fechaAgendamiento, String horaAgendamiento, String estado, ServicioSalud servicioSalud) {
        this.id = id;
        this.afiliadoId = afiliadoId;
        this.medicoId = medicoId;
        this.fechaAgendamiento = fechaAgendamiento;
        this.horaAgendamiento = horaAgendamiento;
        this.estado = estado;
        this.servicioSalud = servicioSalud;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAfiliadoId() {
        return afiliadoId;
    }

    public void setAfiliadoId(String afiliadoId) {
        this.afiliadoId = afiliadoId;
    }

    public String getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(String medicoId) {
        this.medicoId = medicoId;
    }

    public String getFechaAgendamiento() {
        return fechaAgendamiento;
    }

    public void setFechaAgendamiento(String fechaAgendamiento) {
        this.fechaAgendamiento = fechaAgendamiento;
    }

    public String getHoraAgendamiento() {
        return horaAgendamiento;
    }

    public void setHoraAgendamiento(String horaAgendamiento) {
        this.horaAgendamiento = horaAgendamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ServicioSalud getServicioSalud() {
        return servicioSalud;
    }

    public void setServicioSalud(ServicioSalud servicioSalud) {
        this.servicioSalud = servicioSalud;
    }

    // Clase embebida
    public static class ServicioSalud {
        private String codigo;
        private String nombre;
        private String descripcion;

        public ServicioSalud() {}

        public ServicioSalud(String codigo, String nombre, String descripcion) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.descripcion = descripcion;
        }

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
    }
}
