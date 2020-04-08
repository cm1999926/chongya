package Utils;

import androidx.annotation.RequiresPermission;

import java.util.Map;

import cn.njcit.girl.Morning;
import cn.njcit.girl.Night;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("wanan/index")
    Call< Night > getNight(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("zaoan/index")
    Call< Morning > getMorning(@FieldMap Map<String,String> map);
}
