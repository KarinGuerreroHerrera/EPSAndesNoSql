package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "serviciosSalud")
public class ServicioSalud {

    @Id
    private String id;
    private String nombre;
    private String fechaDisponibilidad;
    private String horaDisponibilidad;
    private List<String> ipsIds;
    private String medicoId;
    private List<Disponibilidad> disponibilidades;
    private String tipo;

    public ServicioSalud() {}

    public ServicioSalud(String id, String nombre, String fechaDisponibilidad, String horaDisponibilidad, List<String> ipsIds, String medicoId, List<Disponibilidad> disponibilidades, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.fechaDisponibilidad = fechaDisponibilidad;
        this.horaDisponibilidad = horaDisponibilidad;
        this.ipsIds = ipsIds;
        this.medicoId = medicoId;
        this.disponibilidades = disponibilidades;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<String> getIpsIds() {
        return ipsIds;
    }

    public void setIpsIds(List<String> ipsIds) {
        this.ipsIds = ipsIds;
    }

    public String getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(String medicoId) {
        this.medicoId = medicoId;
    }

    public List<Disponibilidad> getDisponibilidades() {
        return disponibilidades;
    }
    public void setDisponibilidades(List<Disponibilidad> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static class Disponibilidad {
        private String fecha; 
        private String hora;  
        private boolean disponible; 
        private String ipsId; 
        private String medicoId;

        public String getFecha() {
            return fecha;
        }
        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
        public String getHora() {
            return hora;
        }
        public void setHora(String hora) {
            this.hora = hora;
        }
        public boolean isDisponible() {
            return disponible;
        }
        public void setDisponible(boolean disponible) {
            this.disponible = disponible;
        }
        public String getIpsId() {
            return ipsId;
        }
        public void setIpsId(String ipsId) {
            this.ipsId = ipsId;
        }
        public String getMedicoId() {
            return medicoId;
        }
        public void setMedicoId(String medicoId) {
            this.medicoId = medicoId;
        }
    }

    public static class AgendamientoRequest {
    private String fecha; 
    private String hora;  
    private String ordenId; 

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public String getOrdenId() {
        return ordenId;
    }
    public void setOrdenId(String ordenId) {
        this.ordenId = ordenId;
    }
}
}
