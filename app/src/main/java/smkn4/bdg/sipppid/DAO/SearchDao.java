package smkn4.bdg.sipppid.DAO;

/**
 * Created by vincwestley on 16/10/16.
 */
public class SearchDao {
    private String Id;
    private String Judul;
    private String Publisher;
    private String Jenis;
    private String Kategori;
    private String Kode;
    private String PublishDate;
    private String Requested;
    private String Deskripsi;


    public SearchDao() {
        // TODO Auto-generated constructor stub
    }

    //public Actors(String name, String description, String dob, String country,
    //String height, String spouse, String children, String image)
    public SearchDao(String  Id, String Judul, String Publisher, String Jenis, String Deskripsi,
                     String Kategori, String Kode, String PublishDate, String Requested) {
        super();
        this.Id = Id;
        this.Judul = Judul;
        this.Publisher = Publisher;
        this.Jenis = Jenis;
        this.Kategori = Kategori;
        this.Kode = Kode;
        this.PublishDate = PublishDate;
        this.Requested = Requested;
        this.Deskripsi = Deskripsi;

    }


    public String getPublishDate(){
        return PublishDate;
    }
    public void setPublishDate(String PublishDate){
        this.PublishDate = PublishDate;
    }

    public String getDeskripsi(){
        return Deskripsi;
    }
    public void setDeskripsi(String Deskripsi){
        this.Deskripsi = Deskripsi;
    }
    public String getRequested(){
        return Requested;
    }
    public void setRequested(String Requested){
        this.Requested = Requested;
    }


    public String getKode(){
        return Kode;
    }
    public void setKode(String Kode){
        this.Kode = Kode;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String Judul) {
        this.Judul= Judul;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String Publisher) {
        this.Publisher= Publisher;
    }

    public String getJenis() {
        return Jenis;
    }

    public void setJenis(String Jenis) {
        this.Jenis = Jenis;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String Kategori) {
        this.Kategori = Kategori;
    }
}
