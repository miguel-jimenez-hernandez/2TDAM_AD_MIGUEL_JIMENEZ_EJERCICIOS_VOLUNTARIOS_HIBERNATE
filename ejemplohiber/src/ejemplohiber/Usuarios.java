package ejemplohiber;
// Generated 03-nov-2021 15:37:40 by Hibernate Tools 4.3.1



/**
 * Usuarios generated by hbm2java
 */
public class Usuarios  implements java.io.Serializable {


     private Integer id;
     private String login;
     private String pass;
     private String tipo;

    public Usuarios() {
    }

    public Usuarios(String login, String pass, String tipo) {
       this.login = login;
       this.pass = pass;
       this.tipo = tipo;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPass() {
        return this.pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }




}


