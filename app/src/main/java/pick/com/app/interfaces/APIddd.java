package pick.com.app.interfaces;

import com.google.gson.JsonObject;
import okhttp3.MultipartBody;
import pick.com.app.varient.user.pojo.LoginModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import java.util.ArrayList;

interface APIddd {




    @Multipart
    @POST("api/comments/add_photo_comment")
    Call<LoginModel> add_photo_comment(@Body JsonObject jsonObject, @Part ArrayList<MultipartBody.Part> surveyImage);

}
