package uniandes.edu.co.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "ordenes")
public class Orden {

    @Id
    private String id;
    private String afiliadoId;
    private String medicoId;
    private String fechaEmision;
    private String estado;
    private List<DetalleOrden> detallesOrden;

    public Orden() {}

    public Orden(String id, String afiliadoId, String medicoId, String fechaEmision, String estado, List<DetalleOrden> detallesOrden) {
        this.id = id;
        this.afiliadoId = afiliadoId;
        this.medicoId = medicoId;
        this.fechaEmision = fechaEmision;
        this.estado = estado;
        this.detallesOrden = detallesOrden;
    }

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

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleOrden> getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(List<DetalleOrden> detallesOrden) {
        this.detallesOrden = detallesOrden;
    }

    public static class DetalleOrden {
        private ServicioSalud servicioSalud;

        public DetalleOrden() {}

        public DetalleOrden(ServicioSalud servicioSalud) {
            this.servicioSalud = servicioSalud;
        }

        public ServicioSalud getServicioSalud() {
            return servicioSalud;
        }

        public void setServicioSalud(ServicioSalud servicioSalud) {
            this.servicioSalud = servicioSalud;
        }
    }

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
