package smkn4.bdg.sipppid.DAO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rizky Alfa Uji G on 13/10/2016.
 */

public class DaoKomponen  {

    @SerializedName("Kode")
    private
    String Kode;

    @SerializedName("Institusi")
    private
    String Institusi;

    public String getKode() {
        return Kode;
    }

    public void setKode(String kode) {
        Kode = kode;
    }

    public String getInstitusi() {
        return Institusi;
    }

    public void setInstitusi(String institusi) {
        Institusi = institusi;
    }
}

