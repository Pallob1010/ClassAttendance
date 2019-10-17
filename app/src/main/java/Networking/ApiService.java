package Networking;

import java.util.List;

import Model.LoginResult;
import Model.RegistrationResult;
import Model.Restore;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<List<LoginResult>>postdata(@Field("Number") String Number);

    @FormUrlEncoded
    @POST("registration.php")
    Call<RegistrationResult>postdata(@Field("Name") String Name, @Field("Number") String Number, @Field("Password") String Password);

    @FormUrlEncoded
    @POST("getRegisterdCourse.php")
    Call<List<Restore>>restoreData(@Field("Number") String Number);



}
