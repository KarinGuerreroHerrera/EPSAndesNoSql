package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "terapias")
public class Terapia {

    @Id
    private String id;
    private String tipoTerapia;
    private int numeroSesiones;
    private String afiliadoId;

    public Terapia() {}

    public Terapia(String id, String tipoTerapia, int numeroSesiones, String afiliadoId) {
        this.id = id;
        this.tipoTerapia = tipoTerapia;
        this.numeroSesiones = numeroSesiones;
        this.afiliadoId = afiliadoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoTerapia() {
        return tipoTerapia;
    }

    public void setTipoTerapia(String tipoTerapia) {
        this.tipoTerapia = tipoTerapia;
    }

    public int getNumeroSesiones() {
        return numeroSesiones;
    }

    public void setNumeroSesiones(int numeroSesiones) {
        this.numeroSesiones = numeroSesiones;
    }

    public String getAfiliadoId() {
        return afiliadoId;
    }

    public void setAfiliadoId(String afiliadoId) {
        this.afiliadoId = afiliadoId;
    }
}
