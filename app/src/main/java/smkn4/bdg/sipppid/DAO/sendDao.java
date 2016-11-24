package smkn4.bdg.sipppid.DAO;

/**
 * Created by vincwestley on 15/10/16.
 */
public class sendDao {
    /**
     * Status : 1
     * ReqId : 10010000012
     * Keterangan : Permohonan Berhasil Dikirim
     */

    private int Status;
    private String ReqId;
    private String Keterangan;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getReqId() {
        return ReqId;
    }

    public void setReqId(String ReqId) {
        this.ReqId = ReqId;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String Keterangan) {
        this.Keterangan = Keterangan;
    }

}