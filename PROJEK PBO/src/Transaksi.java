import java.io.Serializable;

public class Transaksi implements Serializable{
	private static final long serialVersionUID = 1L;
	private int JumlahTiket;
	private double TotalBayar;
	private String Tanggal;
	private String jam;
	private Film FilmDipilih;
	private customer Customer;
	private String tanggalTransaksi;
	
	public String getTanggalTransaksi() {
		return tanggalTransaksi;
	}

	public int getJumlahTiket() {
		return JumlahTiket;
	}

	public double getTotalBayar() {
		return TotalBayar;
	}

	public String getTanggal() {
		return Tanggal;
	}

	public String getJam() {
		return jam;
	}

	public Film getFilmDipilih() {
		return FilmDipilih;
	}

	public customer getCustomer() {
		return Customer;
	}
	public Transaksi(int jmlTiket,Film film,customer Customer,double totalbayar,String Tanggal,String jam,String tanggaltransaksi) {
		this.JumlahTiket=jmlTiket;
		this.FilmDipilih=film;
		this.Customer=Customer;
		this.TotalBayar=totalbayar;
		this.Tanggal=Tanggal;
		this.jam=jam;
		this.tanggalTransaksi=tanggaltransaksi;
	}
}
