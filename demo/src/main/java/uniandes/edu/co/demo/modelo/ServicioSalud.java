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

    public ServicioSalud() {}

    public ServicioSalud(String id, String nombre, String fechaDisponibilidad, String horaDisponibilidad, List<String> ipsIds, String medicoId) {
        this.id = id;
        this.nombre = nombre;
        this.fechaDisponibilidad = fechaDisponibilidad;
        this.horaDisponibilidad = horaDisponibilidad;
        this.ipsIds = ipsIds;
        this.medicoId = medicoId;
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

    public Object getTipo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTipo'");
    }
}
