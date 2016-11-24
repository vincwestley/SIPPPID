package smkn4.bdg.sipppid.DAO;

/**
 * Created by vincwestley on 14/10/16.
 */
public class LoginDao {

    /**
     * Nama : Rizky Alfa Uji G
     * MemberId : 20160000006
     * Priviledges : Anggota
     * PrivId : 8
     */

    private String Nama;
    private String MemberId;
    private String Priviledges;
    private int PrivId;

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String MemberId) {
        this.MemberId = MemberId;
    }

    public String getPriviledges() {
        return Priviledges;
    }

    public void setPriviledges(String Priviledges) {
        this.Priviledges = Priviledges;
    }

    public int getPrivId() {
        return PrivId;
    }

    public void setPrivId(int PrivId) {
        this.PrivId = PrivId;
    }
}
