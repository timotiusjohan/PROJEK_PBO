import java.util.Scanner;
public class Main {
	private static customer custBaru=new customer();
	//private static Film filmBaru=new Film();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	        try {
	            System.out.println("---Selamat datang di O-Tic---");
	            System.out.print("Sudah punya akun?(Yes/No): ");
	            String yesno=sc.nextLine();
	            if(yesno.equals("Yes")) {
	            	custBaru=custBaru.Login();
	            	custBaru.beliTiket();
	            }else {
	            	custBaru.Registrasi();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
