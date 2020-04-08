package Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private Retrofit retrofit;
    private RetrofitInterface retrofitUtils;

    public Retrofit getRetrofit() {
        this.retrofit  = new Retrofit.Builder()
                .baseUrl( "http://api.tianapi.com/txapi/" )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        return retrofit;
    }

    public RetrofitInterface getRetrofitIntreface() {
        this.retrofitUtils = getRetrofit().create( RetrofitInterface.class );
        return retrofitUtils;
    }
}
