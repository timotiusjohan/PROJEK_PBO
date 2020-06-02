
public class Transaksi {
	private static int NoTransaksi;
	private int JumlahTiket;
	private double TotalBayar;
	private Film FilmDipilih;
	private customer Customer;
	
	public Transaksi(int jmlTiket,Film film,customer Customer) {
		this.JumlahTiket=jmlTiket;
		this.FilmDipilih=film;
		this.Customer=Customer;
	}
}
