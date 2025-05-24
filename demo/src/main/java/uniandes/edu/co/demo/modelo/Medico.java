package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "medicos")
public class Medico {

    @Id
    private String id;
    private String nombre;
    private String tipoDocumento;
    private String numeroDocumento;
    private String especialidad;
    private String numeroRegistroMedico;
    private boolean adscritoIps;
    private List<String> ipsIds;

    public Medico() {}

    public Medico(String id, String nombre, String tipoDocumento, String numeroDocumento, String especialidad,
                  String numeroRegistroMedico, boolean adscritoIps, List<String> ipsIds) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.especialidad = especialidad;
        this.numeroRegistroMedico = numeroRegistroMedico;
        this.adscritoIps = adscritoIps;
        this.ipsIds = ipsIds;
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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumeroRegistroMedico() {
        return numeroRegistroMedico;
    }

    public void setNumeroRegistroMedico(String numeroRegistroMedico) {
        this.numeroRegistroMedico = numeroRegistroMedico;
    }

    public boolean isAdscritoIps() {
        return adscritoIps;
    }

    public void setAdscritoIps(boolean adscritoIps) {
        this.adscritoIps = adscritoIps;
    }

    public List<String> getIpsIds() {
        return ipsIds;
    }

    public void setIpsIds(List<String> ipsIds) {
        this.ipsIds = ipsIds;
    }
}
