import java.util.Scanner;
public class Main {
	private static customer custBaru=new customer();
	
	public static void main(String[] args) {
		new Main();
		Main.menu();
	}
	public static void menu() {
		boolean stop=false;
		Scanner sc = new Scanner(System.in);
		while(stop==false) {
			try {
	            System.out.println("---Selamat datang di O-Tic---");
	            System.out.print("Sudah punya akun?(Yes/No): ");
	            String yesno=sc.nextLine();
	            if(yesno.equals("Yes")) {
	            	custBaru=custBaru.Login();
	            	custBaru.Menu();
	            }else if(yesno.equals("No")){
	            	custBaru.Registrasi();
	            }else {
	            	System.out.println("Maaf, input anda tidak valid. Harap masukkan sesuai dengan ketentuan");
	            	menu();
	            }
	        } catch (NullPointerException e) {
	            System.out.println("Login gagal. Harap periksa kembali email dan password anda dan lakukan login ulang");
	            custBaru=new customer();
	        }
		}
		
	}

}
