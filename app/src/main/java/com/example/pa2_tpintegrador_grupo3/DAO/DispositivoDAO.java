package com.example.pa2_tpintegrador_grupo3.DAO;
import com.example.pa2_tpintegrador_grupo3.conexion.DBQueryManager;
import com.example.pa2_tpintegrador_grupo3.conexion.SelectManager;
import com.example.pa2_tpintegrador_grupo3.conexion.UpsertManager;
import com.example.pa2_tpintegrador_grupo3.entidades.TipoDispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Dispositivo;
import com.example.pa2_tpintegrador_grupo3.entidades.Usuario;
import com.example.pa2_tpintegrador_grupo3.interfaces.InterfazDeComunicacion;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DispositivoDAO {

    InterfazDeComunicacion com;
    public DispositivoDAO(InterfazDeComunicacion ic){
        this.com = ic;
    }

    public void crearDispositivo(Dispositivo dispo){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("crearDispositivo");
        String query = "INSERT INTO Dispositivo (id_Tipo_Dispositivo, Imei, Marca, Modelo, Nombre) VALUES (";
        query+= dispo.getTipo_Dispositivo().getId()+" ,";
        query+= "\""+dispo.getImei()+"\",";
        query+= "\""+dispo.getMarca()+"\",";
        query+= "\""+dispo.getModelo()+"\",";
        query+= "\""+dispo.getNombre()+"\"";
        query+=")";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer crearDispositivoHandler(Object obj){
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

    public void relacionarDispositivoConUsuario(Integer idDispositivo, Integer idUsuario){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("relacionarDispositivoConUsuario");
        String query = "INSERT INTO Dispositivo_x_usuario (Id_Dispositivo, Id_Usuario) VALUES (";
        query+= idDispositivo+" ,";
        query+= idUsuario;
        query+=")";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer relacionarDispositivoConUsuarioHandler(Object obj){
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

    public void eliminarSubordinado(Integer id){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("eliminarSubordinado");
        manager.setQuery("DELETE FROM Dispositivo WHERE Id = "+id);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer eliminarSubordinadoHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }

    public void sumarTiempoDeUsoDeDispositivo(Dispositivo d, Long tiempo){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("actualizarConfiguracionBloqueoDispositivo");
        String query = "UPDATE Dispositivo SET Tiempo_Asignado = Tiempo_Asignado + "+tiempo+" WHERE Id = "+d.getId();
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public void actualizarConfiguracionBloqueoDispositivo(Dispositivo d){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("actualizarConfiguracionBloqueoDispositivo");
        manager.setQuery("UPDATE Dispositivo SET Tiempo_Asignado = "+d.getTiempoAsignado()+",Hora_Inicio = "+d.getHoraInicio()+",Hora_Fin = "+d.getHoraFin()+",Bloqueo_Activo = "+d.isBloqueoActivo()+" WHERE Id = "+d.getId());
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer actualizarConfiguracionBloqueoDispositivoHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }

    public void obtenerDispositivoPorId(Integer dispoId){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("obtenerDispositivoPorId");
        String query = "SELECT * FROM Dispositivo WHERE id = \""+dispoId+"\" AND Eliminado = 0";
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Dispositivo obtenerDispositivoPorIdHandler(Object obj){
        if(obj != null){
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    return new Dispositivo(
                        rs.getInt("Id"),
                        new TipoDispositivo(rs.getInt("Id_Tipo_Dispositivo"),""),
                        rs.getString("Imei"),
                        rs.getString("Marca"),
                        rs.getString("Modelo"),
                        rs.getString("Nombre"),
                        rs.getLong("Tiempo_Uso"),
                        rs.getLong("Tiempo_Asignado"),
                        rs.getLong("Hora_Inicio"),
                        rs.getLong("Hora_Fin"),
                        rs.getBoolean("Bloqueo_Activo"),
                        false
                    );
                }
                return null;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }


    public void obtenerDispositivoPorYUsuarioMaestroRelacionadoPorIdDeDispositivo(Integer dispoId){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("obtenerDispositivoPorYUsuarioMaestroRelacionadoPorIdDeDispositivo");
        String query = "SELECT d.Id as Id, dxu.Id_Usuario as idUsuario, u.Email as emailMaestro FROM Dispositivo as d " +
                "INNER JOIN Dispositivo_x_usuario as dxu ON d.Id = dxu.Id_Dispositivo " +
                "inner JOIN Usuario as u ON dxu.Id_Usuario = u.Id " +
                "WHERE d.Eliminado = 0 AND dxu.Eliminado = 0 AND u.Eliminado = 0 AND d.Id = "+dispoId;
        manager.setQuery(query);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Dispositivo obtenerDispositivoPorYUsuarioMaestroRelacionadoPorIdDeDispositivoHandler(Object obj){
        if(obj != null){
            try {
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    Dispositivo res = new Dispositivo();
                    res.setId(rs.getInt("Id"));
                    Usuario us = new Usuario();
                    us.setId(rs.getInt("idUsuario"));
                    us.setEmail(rs.getString("emailMaestro"));
                    res.setUsuarioMaestro(us);
                    return  res;
                }
                return null;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    //----------------------------------------------------------------------------------------------
    public void obtenerTodosLosDispositivosPorUsuario(Usuario u){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("obtenerTodosLosDispositivosPorUsuario");
        manager.setQuery(
            "SELECT d.Id as Id, d.Imei as Imei, d.Marca as marca, d.Modelo as modelo, d.Nombre as nombre, d.Tiempo_Uso as tiempoUso, d.Tiempo_Asignado as tiempoAsignado, d.Hora_Inicio as horaInicio, d.Hora_Fin as horaFin, d.Bloqueo_Activo as bloqueoActivo "+
                "FROM Dispositivo_x_usuario as dxu "+
                "INNER JOIN Dispositivo as d ON dxu.Id_Dispositivo = d.Id "+
                "WHERE d.Id_tipo_dispositivo = 2 AND d.Eliminado = 0 AND dxu.Eliminado = 0 and dxu.Id_Usuario = "+u.getId());
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static ArrayList<Dispositivo> obtenerTodosLosDispositivosPorUsuarioHandler(Object obj){

        if(obj != null){
            ArrayList<Dispositivo> resultados = new ArrayList<Dispositivo>();
            try{
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    resultados.add(
                        new Dispositivo(
                            rs.getInt("Id"),
                            new TipoDispositivo(2,""),
                            rs.getString("Imei"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getString("nombre"),
                            rs.getLong("tiempoUso"),
                            rs.getLong("tiempoAsignado"),
                            rs.getLong("horaInicio"),
                            rs.getLong("horaFin"),
                            rs.getBoolean("bloqueoActivo"),
                    false
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
    //----------------------------------------------------------------------------------------------

    public void obtenerTodosLosDispositivos(){
        SelectManager manager = new SelectManager();
        manager.setIdentificador("OBTENERTODOSLOSDISPOSITIVOS");
        manager.setQuery("SELECT * FROM Dispositivo WHERE Eliminado = 0");
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }
    public static ArrayList<Dispositivo> obtenerTodosLosDispositivosHandler(Object obj){
        if(obj != null){
            ArrayList<Dispositivo> resultados = new ArrayList<Dispositivo>();
            try{
                ResultSet rs = (ResultSet)obj;
                while(rs.next()) {
                    resultados.add(
                            new Dispositivo(
                                    rs.getInt("id"),
                                    new TipoDispositivo(rs.getInt("Id_tipo_dispositivo"),""),
                                    rs.getString("Imei"),
                                    rs.getBoolean("eliminado")
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

    public void actualizarTiempoDeUso(Integer dispoId, Long tiempo){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("actualizarTiempoDeUso");
        manager.setQuery("UPDATE Dispositivo SET Tiempo_Uso = "+tiempo+" WHERE Id = "+dispoId);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer actualizarTiempoDeUsoHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }

    public void eliminarDispositivo(Integer dispoId){
        UpsertManager manager = new UpsertManager();
        manager.setIdentificador("ELIMINARDISPOSITIVO");
        manager.setQuery("UPDATE Dispositivo SET Eliminado = 1 WHERE Id = "+dispoId);
        DBQueryManager mg = new DBQueryManager(this.com, manager);
        mg.execute();
    }

    public static Integer eliminarDispositivoHandler(Object obj){
        if(obj != null){
            return (Integer)obj;
        }
        return null;
    }
}