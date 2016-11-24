package Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import smkn4.bdg.sipppid.DAO.Documents;
import smkn4.bdg.sipppid.DAO.KeberatanDao;
import smkn4.bdg.sipppid.DAO.LaporanDao;
import smkn4.bdg.sipppid.DAO.LoginDao;
import smkn4.bdg.sipppid.DAO.PermohonanDao;
import smkn4.bdg.sipppid.DAO.ProfileDao;
import smkn4.bdg.sipppid.DAO.ProvinsiDao;
import smkn4.bdg.sipppid.DAO.SearchDao;
import smkn4.bdg.sipppid.DAO.sendDao;

/**
 * Created by vincwestley on 14/10/16.
 */
public interface ApiBase {
    @GET("login")
    Call<List<LoginDao>> getLogin(@Query("username") String username,
                                  @Query("password") String password);

    @GET("dip")
    Call<List<Documents>> getDIP();

    @GET("sendrequest")
    Call<List<sendDao>> getRespon(@Query("memberid") String memberid,
                            @Query("judul") String judul,
                            @Query("idskpd") String idskpd,
                            @Query("deskripsi") String deskripsi,
                            @Query("tujuan") String tujuan,
                            @Query("idlev") String idlev,
                            @Query("idprov") String idprov,
                            @Query("idkab") String idkab);
    //idlvl 1,2,3
    //idprov
    //idkab

    @GET("find-doc")
    Call<List<Documents>>getSearch(@Query("key") String key);

    @GET("myrequest")
    Call<List<PermohonanDao>>getRequest(@Query("memberid") String memberid);

    @GET("keberatan")
    Call<List<KeberatanDao>>getKeberatan(@Query("memberid") String memberid);

    @GET("laporan")
    Call<List<LaporanDao>>getLaporan();

    @GET("profil")
    Call<List<ProfileDao>>getProfile(@Query("memberid") String memberid);

    @GET("get-provinsi")
    Call<List<ProvinsiDao>>getProvinsi();
}
