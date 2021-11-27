package ejemplohiber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;




public class AccesoDatos {
//    private String bd_name, user, pwd, driver, host;
//    
//    public AccesoDatos(String host,String base_datos,String usuario,String password,String driver){
//        this.driver = driver;
//        this.host = host;
//        this.bd_name = base_datos;
//        this.user = usuario;
//        this.pwd = password;        
//    }
//    
//    public Connection con () throws ClassNotFoundException, SQLException{        
//        Class.forName(this.driver);
//        return DriverManager.getConnection("jdbc:mysql://"+this.host+"/"+this.bd_name,this.user,this.pwd);
//    }
    
    public Session ses(){
        return (Session) HibernateUtil.getSessionFactory().openSession();
    }
    
    public void CrearUsuario(String nombre,String pass,String tipo)
    {        
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        
        Transaction ts = sesion.beginTransaction();
        Usuarios nuevo = new Usuarios(nombre,pass,tipo);
        sesion.save(nuevo);
        
        ts.commit();
        sesion.close();          
    }
    
    public String mostrarAdmins()
    {
        String output = "";
//        Session ses = ses();
        Session ses = HibernateUtil.getSessionFactory().openSession();
        String sentencia = "from Usuarios";
        
        Query consulta = ses.createQuery(sentencia);
        List<Usuarios> lista = consulta.list();
        for(Usuarios u: lista) {
            if (u.getTipo().equals("admin")){
                output+=u.getLogin();
            }
        }
        
        ses.close();
        return output;
    }
    
    public void modificarAdmins()
    {
        Session ses = ses();
        String sentencia = "from Usuarios";
        
        Transaction ts = ses.beginTransaction();
        Query consulta = ses.createQuery(sentencia);
        
        List<Usuarios> list = consulta.list();
        for(Usuarios u: list){
            u.setPass("QUERTY");
        }
        
        ts.commit();
        ses.close();        
    }
    
    public String recuperarUsuario(String user)
    {
        String sentencia="from Usuarios u where u.login='"+user+"'";
        String output = "";
        
        Session ses = ses();
        Query consulta = ses.createQuery(sentencia);
        
        Usuarios buscado = (Usuarios) consulta.uniqueResult();
        output = (buscado != null) ? buscado.getPass()+"\n"+buscado.getTipo()+"\n" : "No existe el usuario";
                
        return output;
    }
    
    
    public String empleadosDepartamento(String dep){
        String output = "";
        String sentencia = "from Departamentos d where d.nombre ='"+dep+"'";
        
        Session ses =  ses();
        Query consulta = ses.createQuery(sentencia);
        
        Departamentos buscado = (Departamentos) consulta.uniqueResult();        
        output = (buscado == null)? "No existe el departamento" : getEmpleados((Set<Empleados>) buscado);
        
        return output;
    }
    private String getEmpleados(Set<Empleados> empleados){
        String output = "";
        for(Empleados e: empleados){
            output += e.getApellido()+"\n";
        }
        return output;
    }
    
    public String infoEmpleado(String apellido){
        String output="";
        String sentencia = "from Empleados u where apellido ='"+apellido+"'";
        
        Session ses = ses();
        Query consulta = ses.createQuery(sentencia);
        
        Empleados buscado = (Empleados)consulta.uniqueResult();
        if(buscado != null){
            output = buscado.getApellido()+"\n"+buscado.getSalario()+"\n";
            Departamentos dep = buscado.getDepartamentos();
            output += "Departamento de "+dep.getNombre()+"\n";
            Empleados jefe = buscado.getEmpleados();
            if(jefe == null){
                output += "Esta persona no tiene jefe\n";
            } else {
                output += "Su jefe es "+jefe.getApellido()+"\n";
            }
            
            Set<Empleados> subordinados = buscado.getEmpleadoses();
            for(Empleados e:subordinados){
                output += "Es jefe "+e.getApellido()+"\n";
            }
        } else {
            
        }
        
        
        ses.close();        
        return output;
    }
    
    //Buscar datos del departamento localizado en Dallas
    public String ejercicio1(){
        String res = "";
        String departamento = "Dallas";
        String sentencia = "from Departamentos d where d.localizacion='"+departamento+"'";
        Session ses = ses();
        Query consulta = ses.createQuery(sentencia);
        Departamentos buscado =(Departamentos)consulta.uniqueResult();
        if(buscado != null)
        {
            res += "ID: "+buscado.getId()+"\n"+
                    "NOMBRE: "+buscado.getNombre()+"\n"+
                    "LOCALIZACIÓN: "+buscado.getLocalizacion()+"\n";
        } else 
        {
            res = "Departamento no encontrado";
        }
        ses.close();
        return res;
    }
    
    // Mostrar datos de los empleados que trabajan en el departamento localizado en Dallas
    public String ejercicio2(){
        String res = "";
        int id_departamento;
        String sentencia = "from Departamentos d where d.localizacion = 'Dallas'";
        Session ses = ses();
        Query consulta = ses.createQuery(sentencia);
        Departamentos buscado = (Departamentos) consulta.uniqueResult();
        if(buscado != null)
        {
            id_departamento = buscado.getId();
            sentencia = "from Empleados e where e.departamento = "+id_departamento;
            Query consulta2 = ses.createQuery(sentencia);
            List<Empleados> list = consulta2.list();
            for (Empleados u: list)
            {
                String nombre_jefe = "";
                sentencia = "from Empleados e where id = "+u.getEmpleados();
                Query consulta3 = ses.createQuery(sentencia);
                Empleados jefe =(Empleados) consulta3.uniqueResult();                
                nombre_jefe = (jefe != null)? jefe.getApellido() : "Este empleado no tiene jefe";
                res += "\n***********"
                        + "ID: "+u.getId()+"\n"
                        + "APELLIDO: "+u.getApellido()+"\n"
                        + "CARGO: "+u.getCargo()+"\n"
                        + "JEFE: "+nombre_jefe+"\n"
                        + "FECHA DE ALTA: "+u.getFechaAlta()+"\n"
                        + "SALARIO: "+u.getSalario()+"\n"
                        + "COMISION: "+u.getComision()+"\n"
                        + "***********\n";                        
            }
        }
        ses.close();
        return res;
    }
    
    //  Buscar empleado con mayor sueldo de toda la empresa
    public String ejercicio3(){
        String res = "";
        
        Session ses = ses();
        String sentencia = "FROM empleados e ORDER BY e.salario DESC";
        Query consulta = ses.createQuery(sentencia);
        Empleados buscado = (Empleados)consulta.uniqueResult();
        if( buscado != null)
        {
            res = "El empleado con mayor sueldo es "+buscado.getSalario();
        } else 
        {
            res = "No existen empleados";
        }
        ses.close();        
        return res;
    }
    //  Mostrar en qué departamento trabaja el empleado del ejercicio anterior
    public String ejercicio4(){
        String res = "";
        
        Session ses = ses();
        int id_departamento;
        String sentencia = "FROM empleados e ORDER BY e.salario";
        Query consulta = ses.createQuery(sentencia);
        Empleados buscado = (Empleados)consulta.uniqueResult();
        if(buscado != null)
        {
            id_departamento =((Departamentos)buscado.getDepartamentos()).getId();
            sentencia = "FROM departamentos d WHERE id = "+id_departamento;
            Query consulta2 = ses.createQuery(sentencia);
            Departamentos departamento = (Departamentos)consulta2.uniqueResult();
            if(departamento != null)
            {
                res = departamento.getNombre();
            } else 
            {
                res = "No existe el departamento";
            }
        } else
        {
            res = "No existen empleados";
            
        }
        ses.close();
        return res;
    }
    
    //  Sumar todos los sueldos del departamento anterior
    public int ejercicio5() throws Exception
    {
        int res = 0;
        Session ses = ses();
        String sentencia = "FROM empleados e ORDER BY e.salario";
        Query consulta = ses.createQuery(sentencia);
        Empleados empleado = (Empleados)consulta.uniqueResult();
        if(empleado != null)
        {
            Departamentos dpto = empleado.getDepartamentos();
            if (dpto != null)
            {
                sentencia = "FROM empleados e WHERE e.departamento="+dpto.getId();
                Query consulta2 = ses.createQuery(sentencia);
                List<Empleados> list = consulta2.list();
                for (Empleados e:list)
                {
                    res += e.getSalario();
                }
            } else
            {
                throw new Exception("El departamento no existe");
            }
        } else 
        {
            throw new Exception("El empleado no existe");
        }
        ses.close();
        return res;
    }
    
    //  Encontrar el departamento cuya suma de sueldos es la mayor de la empresa
    public String ejercicio6()
    {
        String res = "";
        Session ses = ses();
        List<Integer> dpto_salarios = Arrays.asList();
        List<String> dpto_nombres = Arrays.asList();
        String sentencia = "FROM departamentos d";
        Query consulta = ses.createQuery(sentencia);
        List<Departamentos> departamentos_list = consulta.list();
        for(Departamentos d: departamentos_list)
        {            
            sentencia = "FROM empleados e WHERE e.departamento="+d.getId();
            Query consulta2 = ses.createQuery(sentencia);
            List<Empleados> empleados_list = consulta2.list();
            int salario_acc = 0;
            for(Empleados e: empleados_list)
            {
                salario_acc += e.getSalario();
            }
            dpto_nombres.add(d.getNombre());
            dpto_salarios.add(salario_acc);            
        }
        if(dpto_salarios.size() > 0)
        {            
            int mayor_salario = Collections.max(dpto_salarios);
            int index = dpto_salarios.indexOf(mayor_salario);
            res = dpto_nombres.get(index);
        } else
        {
            res = "No existen departamentos";
        }
        
        
        ses.close();
        return res;
    }
    
    //  Quien es el jefe de MILLER
    public String ejercicio7(){
        String res = "";
        Session ses = ses();
        
        String sentencia = "FROM empleados e WHERE apellido = 'MILLER'";
        Query consulta = ses.createQuery(sentencia);
        Empleados miller = (Empleados)consulta.uniqueResult();
        if (miller != null)
        {
            sentencia = "FROM empleados e WHERE id="+miller.getEmpleados();            
            Query consulta2 = ses.createQuery(sentencia);
            Empleados jefe_miller = (Empleados)consulta2.uniqueResult();
            if(jefe_miller != null)
            {
                res = jefe_miller.getApellido();
            } else 
            {
                res = "Miller no tiene jefe";
            }
        } else
        {
            res = "Miller no existe";
        }
                
        ses.close();
        return res;
    }
    
    //  Qué empleados tienen como jefe a KING
    public List<Empleados> ejercicio8() throws Exception
    {
        List<Empleados> res = Arrays.asList();
        Session ses = ses();
        String sentencia = "FROM empleados e WHERE apellido ='KING'";
        Query consulta = ses.createQuery(sentencia);
        Empleados king = (Empleados)consulta.uniqueResult();
        if(king != null)
        {
            res = (List<Empleados>) king.getEmpleadoses();            
        } else 
        {
            throw new Exception("No existe KING");
        }
        //  Si queremos un string con los nombres de los subordinados no hay mas que iterar la lista de subordinados y obtener su apellido
        return res;
    }
    
    //  Que empleados son jefes de algún empleado y cuales no
    public Map<String,List<Empleados>> ejercicio10()
    {
        Map<String,List<Empleados>> res = new HashMap<String,List<Empleados>>();
        Session ses = ses();
        
        String sentencia = "FROM empleados e";
        Query consulta = ses.createQuery(sentencia);
        List<Empleados> lista = (List<Empleados>)consulta.list();
        for(Empleados e: lista)
        {
            int e_jefes = ((List<Empleados>)e.getEmpleados()).size();
            int e_subordinados = ((List<Empleados>)e.getEmpleadoses()).size();
            if(e_subordinados > 0)
            {
                //  Si es jefe de algun empleado
            } else
            {
                // no es jefe de ningun empleado
            }
        }
        
        
        ses.close();
        return res;
    }
    
}
