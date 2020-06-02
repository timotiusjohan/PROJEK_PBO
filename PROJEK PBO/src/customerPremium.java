import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
public class customerPremium extends customer implements Serializable{
	private BigInteger ktp;
	
	private static final long serialVersionUID = 1L;
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String url =  "jdbc:mysql://localhost/final_projek_pbo?serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";
    
    private static Film film=new Film();
    

	public customerPremium(String nama, String HP, String email, String password,BigInteger ktp) {
		super(nama, HP, email, password);
		this.ktp=ktp;
	}
	public void Menu() {
		try {
			Scanner sc=new Scanner(System.in);
			System.out.println("---Selamat Datang Di Menu Utama O-Tic---");
			System.out.println("1. Beli tiket");
			System.out.println("2. Logout");
			System.out.print("Pilihan anda: ");
			int inputanMenu=sc.nextInt();
			
			if(inputanMenu==1) {
				this.beliTiket();
			}else if(inputanMenu==2) {
				Main.menu();
			}else {
				System.out.println("Maaf, kami tidak dapat memproses input anda silahkan masukkan pilihan sesuai dengan menu yang tersedia");
				Menu();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void beliTiket() {
		System.out.println("Selamat! anda mendapatkan potongan sebesar 50% untuk pembelian tiket kedua. Tidak berlaku kelipatan");
		Scanner sc = new Scanner(System.in);
		Film filmDipilih=null;
		List<String> tanggal = new ArrayList<String>();
		List<String> jam = new ArrayList<String>();
		List<String> kursi = new ArrayList<String>(Arrays.asList("A1","A2","A3","A4","A5","A6","A7","A8","A9","A10","B1","B2","B3","B4","B5","B6","B7","B8","B9","B10","C1","C2","C3","C4","C5","C6","C7","C8","C9","C10","D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","E1","E2","E3","E4","E5","E6","E7","E8","E9","E10"));
		List<String> nomorKursi = new ArrayList<String>();
		try {
			//connect ke database
			Class.forName(JDBC_DRIVER);
			Connection con = null;
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			
			//untuk menampilkan film yang tersedia dengan select dari database
			sc = new Scanner(System.in);
			System.out.println("---Film Tersedia---");
			film.selectFilm();
			
			//untuk memilih film yang akan di tonton lalu menampikan detil dari film tersebut
			System.out.print("Anda ingin menonton film nomor: ");
			int pilihan = sc.nextInt();
			
			//select dari database
			ps=con.prepareStatement("SELECT FilmObject FROM Film WHERE idFilm=?");
			ps.setInt(1, pilihan);
			ResultSet rsFilm=ps.executeQuery();
			while(rsFilm.next()) {
				byte[] st = (byte[])rsFilm.getObject(1);
            	ByteArrayInputStream bais = new ByteArrayInputStream(st);
            	ObjectInputStream ois = new ObjectInputStream(bais);
            	filmDipilih = (Film) ois.readObject();
			}
			
			//untuk menampilkan detil dari film
			System.out.println("---Detail Film---");
			System.out.println("Judul: "+filmDipilih.getJudul());
			System.out.println("Durasi: "+filmDipilih.getDurasi());
			System.out.println("Genre: ");
			ps=con.prepareStatement("SELECT genre.genre FROM genre LEFT JOIN Film ON Film.idFilm=genre.idFilm WHERE genre.idFilm=? ");
			ps.setInt(1, pilihan);
			ResultSet rsGenre = ps.executeQuery();
			while(rsGenre.next()) {
				System.out.print("      ");
				System.out.println(rsGenre.getString(1));
			}
			
			System.out.println("Pemeran: ");
			ps=con.prepareStatement("SELECT pemeran.namaPemeran FROM pemeran LEFT JOIN Film ON Film.idFilm=pemeran.idFilm WHERE pemeran.idFilm=? ");
			ps.setInt(1, pilihan);
			ResultSet rsPemeran = ps.executeQuery();
			while(rsPemeran.next()) {
				System.out.print("      ");
				System.out.println(rsPemeran.getString(1)+" ");
			}
			
			int k=0;
			String sinopsis=filmDipilih.getSinopsis();
			String sinopsisBaru="";
			while(k<sinopsis.length()) {
				if(k%50==0) {
					sinopsisBaru+='\n';
					sinopsisBaru+=sinopsis.charAt(k);
					k++;
				}else {
					sinopsisBaru+=sinopsis.charAt(k);
					k++;
				}
			}
			System.out.println("Sinopsis: "+sinopsisBaru);
			
			DecimalFormat df=new DecimalFormat("#.##");
			String formatted=df.format(filmDipilih.getHarga());
			System.out.println("Harga: Rp."+formatted);
			
			System.out.println("---Jadwal Tanggal Tayang---");
			ps=con.prepareStatement("SELECT DISTINCT jadwal.Tanggal FROM jadwal LEFT JOIN Film ON Film.idFilm=jadwal.idFilm WHERE jadwal.idFilm=? ");
			ps.setInt(1, pilihan);
			ResultSet rsTanggal = ps.executeQuery();
			int i=1;
			while(rsTanggal.next()) {
			    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
			    String strDate = formatter.format(rsTanggal.getDate(1));
			    tanggal.add(strDate);
				System.out.println(i+". "+strDate);
				i++;
			}
			System.out.print("Silahkan pilih tanggal tayang yang tersedia: ");
			int tanggalPilihan=sc.nextInt();
			
			System.out.println("---Jadwal Jam Tayang---");
			ps=con.prepareStatement("SELECT DISTINCT jadwal.Jam FROM jadwal LEFT JOIN Film ON Film.idFilm=jadwal.idFilm WHERE jadwal.idFilm=? ");
			ps.setInt(1, pilihan);
			ResultSet rsJam = ps.executeQuery();
			int j=1;
			while(rsJam.next()) {
				jam.add(rsJam.getString(1));
				System.out.println(j+". "+rsJam.getString(1));
				j++;
			}
			System.out.print("Silahkan pilih jam tayang yang tersedia: ");
			int jamPilihan=sc.nextInt();
			
			System.out.print("Masukkan jumlah tiket yang ingin anda beli: ");
			int jumlahTiket=sc.nextInt();
			sc.nextLine();
			for(int z=0;z<jumlahTiket;z++) {
				for(int q=0;q<kursi.size();q++) {
					if(q%5==0) {
						System.out.println();
						System.out.print(kursi.get(q)+" ");
					}else {
						System.out.print(" "+kursi.get(q)+" ");
					}
				}
				System.out.println();
				
				System.out.print("Silahkan pilih tempat duduk untuk tiket ke-"+(z+1)+": ");
				String tempatduduk=sc.nextLine();
				nomorKursi.add(tempatduduk);
			}
			
			double totalbayar=0;
			if(jumlahTiket>=2) {
				int sisa=--jumlahTiket;
				totalbayar=(filmDipilih.getHarga()/2)+(sisa*filmDipilih.getHarga());
				System.out.println(totalbayar);
			}
			String formatTotal=df.format(totalbayar);
			System.out.println("Total: Rp."+formatTotal);
			System.out.print("Silahkan masukkan nominal sesuai dengan total bayar: Rp.");
			double bayar=sc.nextDouble();
			
			DateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date dateobj = new Date();
			String tanggalTransaksi=format.format(dateobj);
			
			int tgl=tanggalPilihan-1;
			int jm=jamPilihan-1;
			int jml=jumlahTiket;
			Transaksi baru = new Transaksi(jml,filmDipilih,this,totalbayar,tanggal.get(tgl),jam.get(jm),tanggalTransaksi);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(baos);
            oos.writeObject(baru);
            oos.flush();
            oos.close();
            baos.close();
            byte[] data = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
			
            
            ps=con.prepareStatement("INSERT INTO Transaksi(idCustomer,idFilm,tanggal,jam,jumlahTiket,totalBayar,WaktuTransaksi,TransObjek) VALUES(?,?,?,?,?,?,?,?)");
            ps.setInt(1, this.getIdCustomer());
            ps.setInt(2, pilihan);
            ps.setString(3, baru.getTanggal());
            ps.setString(4, baru.getJam());
            ps.setInt(5, baru.getJumlahTiket());
            ps.setDouble(6, baru.getTotalBayar());
            ps.setString(7, tanggalTransaksi);
            ps.setBinaryStream(8, bais, data.length);
            ps.executeUpdate();
            
            ps=con.prepareStatement("SELECT kodeTransaksi FROM Transaksi WHERE WaktuTransaksi=?");
			ps.setString(1, tanggalTransaksi);
			ResultSet rs = ps.executeQuery();
			int kodeTransaksi=0;
			while(rs.next()) {
				kodeTransaksi = rs.getInt(1);
			}
			int w=0;
            while(w<nomorKursi.size()){
            	ps=con.prepareStatement("INSERT INTO kursi(kodeTransaksi,nomorKursi) VALUES(?,?)");
                ps.setInt(1, kodeTransaksi);
                ps.setString(2, nomorKursi.get(w));
                ps.executeUpdate();
                w++;
            }
            
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public BigInteger getKtp() {
		return ktp;
	}

}
