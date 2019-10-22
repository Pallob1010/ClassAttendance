package Networking;

import com.google.gson.JsonArray;

import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import Model.AllData;
import Model.LoginResult;
import Model.RegistrationResult;
import Model.Restore;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<List<LoginResult>>postdata(@Field("Number") String Number);

    @FormUrlEncoded
    @POST("registration.php")
    Call<RegistrationResult>postdata(@Field("Name") String Name, @Field("Number") String Number, @Field("Password") String Password,@Field("Designation") String Designation );

    @FormUrlEncoded
    @POST("getRegisterdCourse.php")
    Call<ArrayList<Restore>>restoreData(@Field("Number") String Number);


    @POST("recieveData.php")
    Call<ArrayList<AllData>>recieve(@Field("Id")String Id);


    @POST("sendData.php")
    Call<String>sendToweb(@Body JsonArray object);

    @POST("insert_update.php")
    Call<String>InformationSend(@Body JsonArray object);



}
