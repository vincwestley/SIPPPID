package smkn4.bdg.sipppid.DAO;

/**
 * Created by vincwestley on 17/10/16.
 */
public class PermohonanDao {

    /**
     * Nomor : 10010000002
     * Judul : Anggaran 2014
     * Deskripsi : Informasi Keuangan selama periode 2015 /2014
     * Tujuan : tugas kuliah
     * Komponen : Ditjen Politik dan Pemerintahan Umum
     * Status : Menunggu
     * Tanggal : 2016-08-29 23:18:18
     */

    private String Nomor;
    private String Judul;
    private String Deskripsi;
    private String Tujuan;
    private String Komponen;
    private String Status;
    private String Tanggal;

    public PermohonanDao() {
        // TODO Auto-generated constructor stub
    }

    public PermohonanDao(String Nomor, String Judul, String Deskripsi, String Tujuan, String Komponen,
                         String Status, String Tanggal) {
        super();
        this.Nomor = Nomor;
        this.Judul = Judul;
        this.Deskripsi = Deskripsi;
        this.Tujuan = Tujuan;
        this.Komponen = Komponen;
        this.Status = Status;
        this.Tanggal = Tanggal;

    }

    public String getNomor() {
        return Nomor;
    }

    public void setNomor(String Nomor) {
        this.Nomor = Nomor;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String Judul) {
        this.Judul = Judul;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String Deskripsi) {
        this.Deskripsi = Deskripsi;
    }

    public String getTujuan() {
        return Tujuan;
    }

    public void setTujuan(String Tujuan) {
        this.Tujuan = Tujuan;
    }

    public String getKomponen() {
        return Komponen;
    }

    public void setKomponen(String Komponen) {
        this.Komponen = Komponen;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String Tanggal) {
        this.Tanggal = Tanggal;
    }
}
