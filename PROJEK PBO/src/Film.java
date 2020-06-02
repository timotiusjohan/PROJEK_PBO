import java.io.*;
import java.sql.*;
import java.util.Scanner;
public class Film implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String url =  "jdbc:mysql://localhost/final_projek_pbo?serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";
	
	private String Judul;
	private String Genre;
	private String Durasi;
	private String Pemeran;
	private String Sinopsis;
	private String Jadwal; 
	private double Harga;
	private Scanner sc;
	
	public Film() {
		
	}
	
	public Film(String judul,String durasi, String sinopsis,double harga) {
		this.Judul=judul;
		this.Durasi=durasi;
		this.Sinopsis=sinopsis;
		this.Harga=harga;
	}
	
	public void selectFilm() {
		Film select=null;
		try {
			Class.forName(JDBC_DRIVER);
			Connection con = null;
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			
			ps=con.prepareStatement("SELECT FilmObject FROM `Film` ");
			ResultSet rs = ps.executeQuery();
			int i=1;
			while(rs.next()) {
				byte[] st = (byte[])rs.getObject(1);
            	ByteArrayInputStream bais = new ByteArrayInputStream(st);
            	ObjectInputStream ois = new ObjectInputStream(bais);
            	select = (Film) ois.readObject();
            	System.out.println(i+". "+select.getJudul());
            	i++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tambahFilm() {
		try {
			Class.forName(JDBC_DRIVER);
			Connection con = null;
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			sc = new Scanner(System.in);
			
			System.out.print("Judul Film: ");
            String judul = sc.nextLine();
            System.out.print("Durasi: ");
            String durasi = sc.nextLine();
            System.out.print("Sinopsis: ");
            String sinopsis = sc.nextLine();
            System.out.print("Harga: ");
            double harga = sc.nextDouble();
			
			Film baru = new Film(judul,durasi,sinopsis,harga);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(baos);
            oos.writeObject(baru);
            oos.flush();
            oos.close();
            baos.close();
            byte[] data = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            
            ps = con.prepareStatement("INSERT INTO Film(Judul,Durasi,Sinopsis,Harga,FilmObject) Values(?,?,?,?,?)");
            
            ps.setString(1, judul);
            ps.setString(2, durasi);
            ps.setString(3, sinopsis);
            ps.setDouble(4, harga);
            ps.setBinaryStream(5, bais, data.length);
            ps.executeUpdate();
            System.out.print("Masukkan jumlah genre: ");
            int jumlahGenre=sc.nextInt();
            sc.nextLine();
            String genre;
            for(int i=0;i<jumlahGenre;i++) {
            	System.out.print("Masukkan genre ke-"+(i+1)+": ");
            	genre = sc.nextLine();
            	baru.tambahGenre(genre);
            }
            System.out.print("Masukkan jumlah jadwal: ");
            int jumlahJadwal=sc.nextInt();
            sc.nextLine();
            String jadwal;
            for(int i=0;i<jumlahJadwal;i++) {
            	System.out.print("Masukkan jadwal ke-"+(i+1)+": ");
            	jadwal = sc.nextLine();
            	baru.tambahJadwal(jadwal);
            }
            System.out.print("Masukkan jumlah pemeran: ");
            int jumlahPemeran=sc.nextInt();
            sc.nextLine();
            String pemeran;
            for(int i=0;i<jumlahPemeran;i++) {
            	System.out.print("Masukkan pemeran ke-"+(i+1)+": ");
            	pemeran = sc.nextLine();
            	baru.tambahPemeran(pemeran);
            }
            System.out.println("Berhasil Insert");
            con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tambahGenre(String genre) {
		try {
			Class.forName(JDBC_DRIVER);
			Connection con = null;
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			ps=con.prepareStatement("SELECT idFilm FROM Film WHERE Judul=?");
			ps.setString(1, Judul);
			ResultSet rs = ps.executeQuery();
			int idFilm=0;
			while(rs.next()) {
				idFilm = rs.getInt(1);
			}
			
			PreparedStatement a=null;
			a = con.prepareStatement("INSERT INTO genre(idFilm,genre) VALUES(?,?)");
			a.setInt(1, idFilm);
			a.setString(2, genre);
			a.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void tambahJadwal(String jadwal) {
		try {
			Class.forName(JDBC_DRIVER);
			Connection con = null;
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			ps=con.prepareStatement("SELECT idFilm FROM Film WHERE Judul=?");
			ps.setString(1, Judul);
			ResultSet rs = ps.executeQuery();
			int idFilm=0;
			while(rs.next()) {
				idFilm = rs.getInt(1);
			}
			
			PreparedStatement a=null;
			a = con.prepareStatement("INSERT INTO jadwal(idFilm,Jam) VALUES(?,?)");
			a.setInt(1, idFilm);
			a.setString(2, jadwal);
			a.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void tambahPemeran(String pemeran) {
		try {
			Class.forName(JDBC_DRIVER);
			Connection con = null;
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			ps=con.prepareStatement("SELECT idFilm FROM Film WHERE Judul=?");
			ps.setString(1, Judul);
			ResultSet rs = ps.executeQuery();
			int idFilm=0;
			while(rs.next()) {
				idFilm = rs.getInt(1);
			}
			
			PreparedStatement a=null;
			a = con.prepareStatement("INSERT INTO pemeran(idFilm,namaPemeran) VALUES(?,?)");
			a.setInt(1, idFilm);
			a.setString(2, pemeran);
			a.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String getJudul() {
		return Judul;
	}

	public String getGenre() {
		return Genre;
	}

	public String getDurasi() {
		return Durasi;
	}

	public String getPemeran() {
		return Pemeran;
	}

	public String getSinopsis() {
		return Sinopsis;
	}

	public String getJadwal() {
		return Jadwal;
	}

	public double getHarga() {
		return Harga;
	}
}
