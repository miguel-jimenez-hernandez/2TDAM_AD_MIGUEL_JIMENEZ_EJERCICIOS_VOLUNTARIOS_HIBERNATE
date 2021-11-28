package ejemplohiber;

import javax.swing.JOptionPane;

public class Ejemplohiber {

    public static void main(String[] args) {
       String[] options = { "Nuevo usuario", "Ver admins","Modificar contra admins","Ver usuario","Empleados depar","Info empleado","EJERCIICO 1","EJERCICIO 2","EJERCICIO 3","EJERCICIO 4","EJERCICIO 5","EJERCICIO 6","EJERCICIO 7", "EJERCICIO 8", "EJERCICIO 9","EJERCICIO 10"};
       int opcion;
       String login,pass,tipo,dep,empleado;
//       AccesoDatos ad= new AccesoDatos("localhost", "tema3", "root", "", "com.mysql.jdbc.Driver");
       AccesoDatos ad= new AccesoDatos();
       
       
       try{    
        
            do
            {
                opcion=JOptionPane.showOptionDialog(null, "Elija una de las opciones", "Hibernate",
                                               JOptionPane.DEFAULT_OPTION, 
                                               JOptionPane.INFORMATION_MESSAGE,null, 
                                               options, options[0]);
                switch(opcion)
                {
                    case 0:
                        login=JOptionPane.showInputDialog(null, "Dime el login");
                        pass=JOptionPane.showInputDialog(null, "Dime el password");
                        tipo=JOptionPane.showInputDialog(null, "Dime el tipo");
                        ad.CrearUsuario(login, pass, tipo);
                        JOptionPane.showMessageDialog(null,"Usuario creado con exito");
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null,ad.mostrarAdmins());
                        break;
                    case 2:
                        ad.modificarAdmins();
                        JOptionPane.showMessageDialog(null,"Contraseñas cambiadas con exito");
                        break;
                    case 3:
                        login=JOptionPane.showInputDialog(null, "Dime el nombre de usuario");
                        JOptionPane.showMessageDialog(null,"Datos de usuario \n"+ad.recuperarUsuario(login));
                        break;
                    case 4:
                        dep=JOptionPane.showInputDialog(null, "Dime el nombre del departamento");
                        JOptionPane.showMessageDialog(null,"Empleados departamento \n"+ad.empleadosDepartamento(dep));
                        break;
                    case 5:
                        empleado=JOptionPane.showInputDialog(null, "Dime el nombre del empleado");
                        JOptionPane.showMessageDialog(null,"Datos del empleado \n"+ad.infoEmpleado(empleado));
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(null,ad.ejercicio1());
                        break;
                    case 7:
                        JOptionPane.showMessageDialog(null,ad.ejercicio2());
                        break;
                    case 8:
                        JOptionPane.showMessageDialog(null,ad.ejercicio3());
                        break;
                    case 9:
                        JOptionPane.showMessageDialog(null,ad.ejercicio4());
                        break;
                    case 10:
                        JOptionPane.showMessageDialog(null,ad.ejercicio5());
                        break;
                    case 11:
                        JOptionPane.showMessageDialog(null,ad.ejercicio6());
                        break;
                    case 12:
                        JOptionPane.showMessageDialog(null,ad.ejercicio7());
                        break;
                    case 13:
                        JOptionPane.showMessageDialog(null,ad.ejercicio8());
                        break;
                    case 14:
                        JOptionPane.showMessageDialog(null,ad.ejercicio9());
                        break;
                    case 15:
                        JOptionPane.showMessageDialog(null,ad.ejercicio10());
                        break;
                    
                    
                }    

            }while(opcion!=-1); 
            JOptionPane.showMessageDialog(null,"Adios");
            System.exit(0);
        }catch(Exception e){
          JOptionPane.showMessageDialog(null,e.toString());  
          System.exit(0);
        }
    }
    
}
