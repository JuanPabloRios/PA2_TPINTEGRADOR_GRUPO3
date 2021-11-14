package com.example.pa2_tpintegrador_grupo3;

import android.app.PendingIntent;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pa2_tpintegrador_grupo3.entidades.Configuracion;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Utilidad extends AppCompatActivity {
    private static final String NOMBRE_ARCHIVO = "parentalWatcher.txt";
    private String tipoUsuario;

    public boolean validateString(String cadena){

        for (int i = 0; i < cadena.length(); i++)
        {
            char caracter = cadena.toUpperCase().charAt(i);
            int valorASCII = (int)caracter;
            if (valorASCII != 165 && valorASCII != 32  && ( valorASCII < 65 || valorASCII > 90)) {
                return true; //Se encontro un caracter distinto a Letra
            }
        }
        return false;
    }

    public boolean validateDate(String fecha){
        String[] arrsplit = fecha.split("/");
        Integer dia = Integer.parseInt(arrsplit[0]);
        Integer mes = Integer.parseInt(arrsplit[1]);
        if(dia > 31 || mes > 12){
            return  true;
        }
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fechaInicioDate = date.parse(fecha);
            Date fechaactual = new Date(System.currentTimeMillis());
            if(fechaInicioDate.before(fechaactual)){
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            //mal formato de fecha
            return true;
        }
        return true;
    }
    //validar si es un email
    public boolean emailInvalido(String cadena){
        if (!Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            return true;
        }return false;
    }

    public boolean validateEmpty(String campo){
        if(TextUtils.isEmpty(campo)){
            return true;
        }
        return false;
    }


    public Integer validarTipoDispositivo(Context context) {
        Configuracion con = obtenerConfiguracion(context);
        if(con != null && con.getDispositivo() != null && con.getDispositivo().getTipo_Dispositivo() != null){
            return con.getDispositivo().getTipo_Dispositivo().getId();
        }
        return null;
    }


    public Configuracion obtenerConfiguracion(Context context) {
        FileInputStream file = null;
        try {
            file = context.openFileInput("parentalWatcher.txt");
            InputStreamReader inputReader = new InputStreamReader(file);
            BufferedReader buffer = new BufferedReader(inputReader);
            String toJson;
            Gson gson = new Gson();
            while((toJson = buffer.readLine()) != null){
                return gson.fromJson(toJson, Configuracion.class);
            }
            return null;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return  null;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        } finally {
            if(file != null){
                try {
                    file.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public Boolean guardarArchivoDeConfiguracion(Context context,Configuracion con){
        FileOutputStream file = null;
        try {
            Gson gson = new Gson();
            String objConf = gson.toJson(con);
            file = new FileOutputStream(new File(context.getFilesDir(),NOMBRE_ARCHIVO));
            OutputStreamWriter writer = new OutputStreamWriter(file);
            writer.write(objConf);
            writer.close();
            return true;
        } catch (IOException e){
            Toast.makeText(this,"Error guardando archivo de configuracion!",Toast.LENGTH_SHORT).show();
            return false;
        } finally {
            if(file != null){
                try {
                    file.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    public String encodeIcon(Drawable icon){
        String res = "";
        if(icon !=null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Bitmap bmp = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            icon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            icon.draw(canvas);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapByte = stream.toByteArray();
            res = Base64.encodeToString(bitmapByte,Base64.DEFAULT);
        }
        return res;
    }


    public ArrayList<InfoObject> getInstalledApps(Context context) {
        ArrayList<InfoObject> listObj = new ArrayList<InfoObject>();
        final PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : packages) {
            InfoObject newInfo = new InfoObject();
            ArrayList<String> appsToAdd = new ArrayList<String>();
            appsToAdd.add("com.google.android.youtube");
            appsToAdd.add("com.google.android.googlequicksearchbox");
            appsToAdd.add("com.google.android.gm");
            appsToAdd.add("com.google.android.music");
            appsToAdd.add("com.google.android.apps.docs");

            if( (applicationInfo.packageName.startsWith("com.android") && !applicationInfo.packageName.equals("com.android.vending")) ||
                (applicationInfo.packageName.startsWith("com.google.android") && !appsToAdd.contains(applicationInfo.packageName)) ||
                applicationInfo.packageName.equals("android") ||
                applicationInfo.packageName.equals("com.example.pa2_tpintegrador_grupo3")
            ){
                continue;
            }

            newInfo.appname = applicationInfo.loadLabel(packageManager).toString();
            newInfo.packagename = applicationInfo.packageName;
            Drawable icon = packageManager.getApplicationIcon(applicationInfo);
            newInfo.icon = encodeIcon(icon);
            Gson gson = new Gson();
            listObj.add(newInfo);

        }
        return listObj;
    }

    public class InfoObject implements Serializable {
        public String appname = "";
        public String packagename = "";
        public String icon;
    }

    public List<AppUsageInfo> obtenerStadisticasDeUso(Context context) {
        UsageEvents.Event currentEvent;
        List<UsageEvents.Event> allEvents = new ArrayList<>();
        HashMap<String, AppUsageInfo> map = new HashMap <String, AppUsageInfo> ();
        Long phoneUsageToday = 0L;

        Calendar now = Calendar.getInstance();
        long currTime = now.getTimeInMillis();
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        long startTime = now.getTimeInMillis();
        UsageStatsManager mUsageStatsManager =  (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        assert mUsageStatsManager != null;
        UsageEvents usageEvents = mUsageStatsManager.queryEvents(startTime, currTime);

        while (usageEvents.hasNextEvent()) {
            currentEvent = new UsageEvents.Event();
            usageEvents.getNextEvent(currentEvent);
            if (currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND ||
                    currentEvent.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                allEvents.add(currentEvent);
                String key = currentEvent.getPackageName();
                if (map.get(key)==null)
                    map.put(key,new AppUsageInfo(key));
            }
        }

        for (int i=0;i<allEvents.size()-1;i++){
            UsageEvents.Event E0=allEvents.get(i);
            UsageEvents.Event E1=allEvents.get(i+1);
            if (!E0.getPackageName().equals(E1.getPackageName()) && E1.getEventType()==1){
                map.get(E1.getPackageName()).launchCount++;
            }
            if (E0.getEventType()==1 && E1.getEventType()==2
                    && E0.getClassName().equals(E1.getClassName())){
                long diff = E1.getTimeStamp()-E0.getTimeStamp();
                phoneUsageToday+=diff; //gloabl Long var for total usagetime in the timerange
                map.get(E0.getPackageName()).timeInForeground+= diff;
            }
        }
        List<AppUsageInfo> smallInfoList = new ArrayList<>(map.values());
        for(AppUsageInfo us : smallInfoList){
            us.tiempoTotal = phoneUsageToday;
        }
        return smallInfoList;
    }

    public class AppUsageInfo {
        public Drawable appIcon;
        public String appName, packageName;
        public long timeInForeground;
        public int launchCount;
        public long tiempoTotal;

        AppUsageInfo(String pName) {
            this.packageName=pName;
        }
    }

    public void enviarNotificacionCustom(Context context, String titulo, String mensaje , String channelID, PendingIntent intent, Integer id){

        NotificationCompat.Builder  d = new NotificationCompat.Builder(context, channelID)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(intent);
        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(id,d.build());

    }

}
