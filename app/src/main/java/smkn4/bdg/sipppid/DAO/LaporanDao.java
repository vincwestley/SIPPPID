package smkn4.bdg.sipppid.DAO;

/**
 * Created by vincwestley on 17/10/16.
 */
public class LaporanDao {

    /**
     * Dokumen : 1000
     * Permohonan : 235
     * Pengunjung : 700
     * Download : 300
     */

    private String Dokumen;
    private String Permohonan;
    private String Pengunjung;
    private String Download;

    public String getDokumen() {
        return Dokumen;
    }

    public void setDokumen(String Dokumen) {
        this.Dokumen = Dokumen;
    }

    public String getPermohonan() {
        return Permohonan;
    }

    public void setPermohonan(String Permohonan) {
        this.Permohonan = Permohonan;
    }

    public String getPengunjung() {
        return Pengunjung;
    }

    public void setPengunjung(String Pengunjung) {
        this.Pengunjung = Pengunjung;
    }

    public String getDownload() {
        return Download;
    }

    public void setDownload(String Download) {
        this.Download = Download;
    }
}
