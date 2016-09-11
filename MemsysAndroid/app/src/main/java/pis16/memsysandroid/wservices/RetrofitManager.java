package pis16.memsysandroid.wservices;

import android.util.Log;

import java.io.IOException;

import pis16.memsysandroid.domain.DtoUser;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * RetrofitManager se encarga de manejar los llamados a WebServices, debe ser llamado desde un thread != UI
 *
 * TODO: Estaría bueno pasar a inyección esta clase! Usando tag Singleton sobre esta clase
 *
 * Created by jmsmuy on 21/08/2016.
 */
public class RetrofitManager {
    public static final String BASE_URL = "http://pis-prototipo1-api.azurewebsites.net/";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static RetrofitManager instance;
    private final WSInterface wsInterface;

    public RetrofitManager() {
        wsInterface = retrofit.create(WSInterface.class);
    }

    public static RetrofitManager getInstance(){
        if(instance == null){
            instance = new RetrofitManager();
        }
        return instance;
    }

    public void getUser(String username) {

        Call<DtoUser> call = wsInterface.getUser(username);

        try {
            Response<DtoUser> response = call.execute();
            DtoUser user = response.body();
            Log.i("INFO", "Usuario: " + user.getNombreUsuario() + " - Clave : " + user.getContraseña());
        } catch (IOException e) {
            e.printStackTrace(); // TODO Loguear el error correctamente
        }
    }
}

