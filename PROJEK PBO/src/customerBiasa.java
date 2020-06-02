import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;


public class customerBiasa extends customer implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String url =  "jdbc:mysql://localhost/final_projek_pbo?serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";
    
    private static Film film=new Film();
    
    private Scanner sc;
	public customerBiasa(String nama, String HP, String email, String password) {
		super(nama, HP, email, password);
		// TODO Auto-generated constructor stub
	}
	
	
	//fitur untuk beli tiket
	public void beliTiket() {
		Film filmDipilih=null;
		List<String> tanggal = new ArrayList<String>();
		List<String> jam = new ArrayList<String>();
		List<String> kursi = new ArrayList<String>(Arrays.asList("A1","A2","A3","A4","A5","A6","A7","A8","A9","A10","B1","B2","B3","B4","B5","B6","B7","B8","B9","B10","C1","C2","C3","C4","C5","C6","C7","C8","C9","C10","D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","E1","E2","E3","E4","E5","E6","E7","E8","E9","E10"));
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
			
			System.out.println("Jadwal:");
			ps=con.prepareStatement("SELECT DISTINCT jadwal.Tanggal FROM jadwal LEFT JOIN Film ON Film.idFilm=jadwal.idFilm WHERE jadwal.idFilm=? ");
			ps.setInt(1, pilihan);
			ResultSet rsTanggal = ps.executeQuery();
			while(rsTanggal.next()) {
			    SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
			    String strDate = formatter.format(rsTanggal.getDate(1));   
				System.out.println(strDate);
				ps=con.prepareStatement("SELECT DISTINCT jadwal.Jam FROM jadwal LEFT JOIN Film ON Film.idFilm=jadwal.idFilm WHERE jadwal.idFilm=? ");
				ps.setInt(1, pilihan);
				ResultSet rsJam = ps.executeQuery();
				while(rsJam.next()) {
					System.out.print("       ");
					System.out.print(rsJam.getString(1)+" ");
				}
				System.out.println();
			}
			
			DecimalFormat df=new DecimalFormat("#.##");
			String formatted=df.format(filmDipilih.getHarga());
			System.out.println("Harga: Rp."+formatted);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
