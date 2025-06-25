package entidad;

import java.sql.Date;

public class Cliente {
    private int id;
    private String dni;
    private String cuil;
    private String nombre;
    private String apellido;
    private String sexo; 
    private String nacionalidad;
    private Date fechaNacimiento;
    private String direccion;
    private String correo;
    private String telefono;
    private boolean estado;

    // Relaciones como objetos
    private Usuario usuario;
    private Localidad localidad;

    // Constructor vacío
    public Cliente() {
        this.usuario = new Usuario(); 
        this.localidad = new Localidad(); 
        this.localidad.setProvincia(new Provincia()); 
    }

    // Constructor completo
    public Cliente(int id, String dni, String cuil, String nombre, String apellido, String sexo, String nacionalidad,
                   Date fechaNacimiento, String direccion, String correo, String telefono, boolean estado,
                   Usuario usuario, Localidad localidad) {
        this.id = id;
        this.dni = dni;
        this.cuil = cuil;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.estado = estado;
        this.usuario = usuario;
        this.localidad = localidad;
    }

    // Metodos Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCuil() { return cuil; }
    public void setCuil(String cuil) { this.cuil = cuil; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getSexo() {
        return (sexo != null) ? sexo : ""; 
    }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Localidad getLocalidad() { return localidad; }
    public void setLocalidad(Localidad localidad) { this.localidad = localidad; }
}