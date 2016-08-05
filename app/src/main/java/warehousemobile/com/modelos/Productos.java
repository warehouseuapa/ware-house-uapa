package warehousemobile.com.modelos;

/**
 * Created by Samuel on 04/08/2016.
 */
public class Productos {

    public Productos() {
    }

    public Productos(int id, int cantidad, int precio, String codigo, String descripcion, String localizacion) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.localizacion = localizacion;
    }

    public int id;
    public int cantidad;
    public int precio;
    public String codigo;
    public String descripcion;
    public String localizacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
}
