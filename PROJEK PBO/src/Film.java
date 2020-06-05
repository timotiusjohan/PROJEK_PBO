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
	
	public int selectFilm() {
		int i=0;
		Film select=null;
		try {
			Class.forName(JDBC_DRIVER);
			Connection con = null;
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			
			ps=con.prepareStatement("SELECT FilmObject FROM `Film` ");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				byte[] st = (byte[])rs.getObject(1);
            	ByteArrayInputStream bais = new ByteArrayInputStream(st);
            	ObjectInputStream ois = new ObjectInputStream(bais);
            	select = (Film) ois.readObject();
            	i++;
            	System.out.println(i+". "+select.getJudul());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return i;
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
