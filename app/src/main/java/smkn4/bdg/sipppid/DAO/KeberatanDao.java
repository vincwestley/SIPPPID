package smkn4.bdg.sipppid.DAO;

/**
 * Created by vincwestley on 17/10/16.
 */
public class KeberatanDao {

    /**
     * Nomor :
     * Refferensi : 10010000001
     * Permohonan : Permohonan Testing Kemendagri
     * Reason : Permintaan Informasi ditanggapi tidak sebagaimana yang diminta
     * Status : Menunggu
     * Tanggal : 2016-08-28
     */

    private String Nomor;
    private String Refferensi;
    private String Permohonan;
    private String Reason;
    private String Status;
    private String Tanggal;

    public String getNomor() {
        return Nomor;
    }

    public void setNomor(String Nomor) {
        this.Nomor = Nomor;
    }

    public String getRefferensi() {
        return Refferensi;
    }

    public void setRefferensi(String Refferensi) {
        this.Refferensi = Refferensi;
    }

    public String getPermohonan() {
        return Permohonan;
    }

    public void setPermohonan(String Permohonan) {
        this.Permohonan = Permohonan;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
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
