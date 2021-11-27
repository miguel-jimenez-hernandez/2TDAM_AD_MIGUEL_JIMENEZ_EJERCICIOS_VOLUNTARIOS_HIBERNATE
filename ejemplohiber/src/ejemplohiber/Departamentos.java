package ejemplohiber;
// Generated 03-nov-2021 15:37:40 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Departamentos generated by hbm2java
 */
public class Departamentos  implements java.io.Serializable {


     private int id;
     private String nombre;
     private String localizacion;
     private Set empleadoses = new HashSet(0);

    public Departamentos() {
    }

	
    public Departamentos(int id) {
        this.id = id;
    }
    public Departamentos(int id, String nombre, String localizacion, Set empleadoses) {
       this.id = id;
       this.nombre = nombre;
       this.localizacion = localizacion;
       this.empleadoses = empleadoses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getLocalizacion() {
        return this.localizacion;
    }
    
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
    public Set getEmpleadoses() {
        return this.empleadoses;
    }
    
    public void setEmpleadoses(Set empleadoses) {
        this.empleadoses = empleadoses;
    }




}


