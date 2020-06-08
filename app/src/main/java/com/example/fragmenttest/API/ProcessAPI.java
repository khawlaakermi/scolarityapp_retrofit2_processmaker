package com.example.fragmenttest.API;

import com.example.fragmenttest.Model.Case;
import com.example.fragmenttest.Model.ItemFormulaire;
import com.example.fragmenttest.Model.PostObject;
import com.example.fragmenttest.Model.Process;
import com.example.fragmenttest.Model.User;

import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ProcessAPI {

    @GET("api/1.0/isi/cases/start-cases")
    Call<List<Case>> cases(@Header("Authorization") String token);
    @GET("api/1.0/isi/extrarest/dynaform/{id_dyn}")
    Call<List<ItemFormulaire>> champs(@Header("Authorization") String token,@Path("id_dyn") String id_dyn);
    @GET("api/1.0/isi/extrarest/login-user")
    Call<User> getuser(@Header("Authorization") String token);
    @POST("api/1.0/isi/cases")
    Call<ResponseBody> postRequest(@Header("Authorization") String token,@Body PostObject fieldsMap);
    @GET("api/1.0/isi/project/{pro_uid}/activity/{tas_uid}/steps")
    Call<ResponseBody> getifdynaform(@Header("Authorization") String token, @Path("pro_uid") String pro_uid , @Path("tas_uid") String tas_uid);
    @GET("api/1.0/isi/cases/participated")
    Call<List<Process>> particpated(@Header("Authorization") String token);
    @POST("api/1.0/isi/project/{pro_uid}/process-variable/{txt_field}/execute-query")
    Call<List<ItemFormulaire.option>> getenseignant(@Header("Authorization") String token, @Path("pro_uid") String pro_uid , @Path("pro_uid") String txt_field ,@Body RequestBody params);

}
