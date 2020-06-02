import java.io.*;
import java.sql.*;
import java.util.Scanner;
public class customer implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String url =  "jdbc:mysql://localhost/final_projek_pbo?serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";
	
	private String Nama;
	private String NomorHandphone;
	private String Email;
	private String Password;
	private Scanner sc;
	
	public customer() {
		
	}
	
	public customer(String nama, String HP, String email, String password) {
		this.Nama=nama;
		this.NomorHandphone=HP;
		this.Email=email;
		this.Password=password;
	}
	
	public void beliTiket() {
		
	}

	public String getNama() {
		return Nama;
	}

	public String getNomorHandphone() {
		return NomorHandphone;
	}

	public String getEmail() {
		return Email;
	}

	public String getPassword() {
		return Password;
	}
	
	public customer Login() {
		customer baru=null;
		try {
			Class.forName(JDBC_DRIVER);
			Connection con = null;
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			
			sc = new Scanner(System.in);
			System.out.println("---Login---");
        	System.out.print("Email: ");
        	String Email = sc.nextLine();
        	System.out.print("Password: ");
        	String Password = sc.nextLine();
        	ps=con.prepareStatement("SELECT CustObject FROM Customer WHERE Email=? AND Password=?");
        	ps.setString(1, Email);
        	ps.setString(2, Password);
        	ResultSet rs = ps.executeQuery();
        	int rowCount = 0;
        	while(rs.next()) {
        		rowCount++;
        		byte[] st = (byte[])rs.getObject(1);
            	ByteArrayInputStream bais = new ByteArrayInputStream(st);
            	ObjectInputStream ois = new ObjectInputStream(bais);
            	baru = (customer) ois.readObject(); 
        	}
        	if(rowCount==1) {
        		System.out.println("Login Sukses");
        		con.close();
        	}else {
        		System.out.println("Login Gagal");
        	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return baru;
	}
	
	public void Registrasi() {
		try {
			//input user
			sc = new Scanner(System.in);
			System.out.println("---Registrasi---");
            System.out.print("Nama: ");
            String Nama = sc.nextLine();
            System.out.print("No. Handphone: ");
            String NoHandphone = sc.nextLine();
            System.out.print("Email: ");
            String Email = sc.nextLine();
            System.out.print("Password: ");
            String Password = sc.nextLine();
            
            //buat objek baru
            customer baru = new customerBiasa(Nama,NoHandphone,Email,Password);
            
            //koneksi database
			Class.forName(JDBC_DRIVER);
			Connection con = null;
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			
			//serialisasi
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(baos);
            oos.writeObject(baru);
            oos.flush();
            oos.close();
            baos.close();
            byte[] data = baos.toByteArray();
            
            //insert ke database
            ps = con.prepareStatement("INSERT INTO Customer(Nama,NoHandphone,Email,Password,CustObject) Values(?,?,?,?,?)");
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ps.setString(1, Nama);
            ps.setString(2, NoHandphone);
            ps.setString(3, Email);
            ps.setString(4, Password);
            ps.setBinaryStream(5, bais, data.length);
            ps.executeUpdate();
            System.out.println("Berhasil Insert");
            con.close();
            
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
