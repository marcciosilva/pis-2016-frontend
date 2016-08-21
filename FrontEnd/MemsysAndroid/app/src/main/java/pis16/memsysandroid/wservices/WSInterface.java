package pis16.memsysandroid.wservices;

import java.util.List;

import pis16.memsysandroid.domain.DtoUser;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * WSInterface contiene los llamados a los WS del BE
 * Created by jmsmuy on 21/08/16.
 */

public interface WSInterface {

    @GET("api/Usuario/{username}")
    Call<DtoUser> getUser(@Path("username") String username);

    @GET("api/Usuario")
    Call<List<DtoUser>> getUsuarios();

    @POST("users/new")
    Call<DtoUser> createUser(@Body DtoUser user);
}
