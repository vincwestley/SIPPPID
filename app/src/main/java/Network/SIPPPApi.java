package Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vincwestley on 14/10/16.
 */
public class SIPPPApi {
    public static ApiBase service(String baseurl){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseurl)
                .build();
        return retrofit.create(ApiBase.class);
    }
}
