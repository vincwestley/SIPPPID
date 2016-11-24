package smkn4.bdg.sipppid.DAO;

/**
 * Created by vincwestley on 17/10/16.
 */
public class ProfileDao {

    /**
     * Nama : Administrator
     * MemberId : 168
     * Priviledges : Admin
     * PrivId : 2
     * Avatar : 1.png
     * Alamat : Sekelimus No 22
     * Kota : Bandung
     * Provinsi : Jawa Barat
     * Pos :
     * Telepon :
     * HP :
     * Email : benny.ariadikusuma@gmail.com
     */

    private String Nama;
    private String MemberId;
    private String Priviledges;
    private int PrivId;
    private String Avatar;
    private String Alamat;
    private String Kota;
    private String Provinsi;
    private String Pos;
    private String Telepon;
    private String HP;
    private String Email;

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

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String Alamat) {
        this.Alamat = Alamat;
    }

    public String getKota() {
        return Kota;
    }

    public void setKota(String Kota) {
        this.Kota = Kota;
    }

    public String getProvinsi() {
        return Provinsi;
    }

    public void setProvinsi(String Provinsi) {
        this.Provinsi = Provinsi;
    }

    public String getPos() {
        return Pos;
    }

    public void setPos(String Pos) {
        this.Pos = Pos;
    }

    public String getTelepon() {
        return Telepon;
    }

    public void setTelepon(String Telepon) {
        this.Telepon = Telepon;
    }

    public String getHP() {
        return HP;
    }

    public void setHP(String HP) {
        this.HP = HP;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
}
