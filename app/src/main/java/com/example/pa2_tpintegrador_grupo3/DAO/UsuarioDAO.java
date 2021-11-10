package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoUsuario;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UsuarioDAO {
    //En esta interfaz enviamos la clase padre que llama al metodo, y recibe los datos en la implementacion del metodo operacionConBaseDeDatosFinalizada()
    InterfazDeComunicacion com;
    public UsuarioDAO(InterfazDeComunicacion ic){
        this.com = ic;
    }

    //CADA METODO DEBERA TENER SU HANDLER PARA SER USADO EN EL METODO QUE RECIBE LAS RESPUESTAS DE LA BASE DE DATOS
    //---------------------------------------------------------------------------------------------
    public void crearUsuario(Usuario us){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("CREARUSUARIO");
        String query = "INSERT INTO Usuario (Usuario, Contrasenia, Email, Id_tipo_usuario) VALUES (";
            query+= "\""+us.getUsuario()+"\",";
            query+= "\""+us.getContrasenia()+"\",";
            query+= "\""+us.getEmail()+"\",";
            query+= us.getTipoUsuario().getId();
            query+=")";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer crearUsuarioHandler(Object obj){
        if(obj != null){
            try {
                Integer rs = (Integer)obj;
                return rs;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    //---------------------------------------------------------------------------------------------

    //CADA METODO DEBERA TENER SU HANDLER PARA SER USADO EN EL METODO QUE RECIBE LAS RESPUESTAS DE LA BASE DE DATOS
    //el Identificador nos sirve para luego en el handler poder saber que metodo esta siendo recibido
    //---------------------------------------------------------------------------------------------
    public void obtenerUsuarioPorNombreUsuarioOEmail(String nombreUsuario,String email){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERUSUARIOPORNOMBREUSUARIOOEMAIL");
        String query = "SELECT * FROM Usuario WHERE Usuario = \""+nombreUsuario+"\" OR Email = \""+email+"\"AND Eliminado = 0";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Usuario obtenerUsuarioPorNombreUsuarioOEmailHandler(Object obj){
        if(obj != null){
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("Usuario"),
                        rs.getString("Contrasenia"),
                        rs.getString("Email"),
                        rs.getBoolean("Eliminado"),
                        new TipoUsuario(rs.getInt("Id_tipo_usuario"),"")
                    );
                }
                return null;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    //---------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------
    public void obtenerUsuarioPorNombreUsuario(String nombreUsuario){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("obtenerUsuarioPorNombreUsuario");
        String query = "SELECT * FROM Usuario WHERE Usuario = \""+nombreUsuario+"\" AND Eliminado = 0";
        System.out.println("@@ query " +query);
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Usuario obtenerUsuarioPorNombreUsuarioHandler(Object obj){
        if(obj != null){
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("Usuario"),
                        rs.getString("Contrasenia"),
                        rs.getString("Email"),
                        rs.getBoolean("Eliminado"),
                        new TipoUsuario(rs.getInt("Id_tipo_usuario"),"")
                    );
                }
                return null;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    //---------------------------------------------------------------------------------------------

    //CADA METODO DEBERA TENER SU HANDLER PARA SER USADO EN EL METODO QUE RECIBE LAS RESPUESTAS DE LA BASE DE DATOS
    //el Identificador nos sirve para luego en el handler poder saber que metodo esta siendo recibido
    //---------------------------------------------------------------------------------------------
    public void obtenerTodosLosUsuarios(){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERTODOSLOSUSUARIOS");
        manager.setQuery("SELECT * FROM Usuario WHERE Eliminaod = 0");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Usuario> obtenerTodosLosUsuariosHandler(Object obj){
        if(obj != null){
            ArrayList<Usuario> resultados = new ArrayList<Usuario>();
            try{
                ResultSet rs = (ResultSet)obj;
                //Recorremos los resultados
                while(rs.next()) {
                    resultados.add(
                        new Usuario(
                            rs.getInt("id"),
                            rs.getString("Usuario"),
                            rs.getString("Contrasenia"),
                            rs.getString("Email"),
                            rs.getBoolean("Eliminado"),
                            new TipoUsuario(rs.getInt("Id_tipo_usuario"),"")
                        )
                    );
                }
                return resultados;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    //---------------------------------------------------------------------------------------------

    public void eliminarUsuario(Integer idUsuario){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("ELIMINARUSUARIO");
        manager.setQuery("UPDATE Usuario SET Eliminado = 1 WHERE Id = "+idUsuario);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer eliminarUsuarioHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }
}
