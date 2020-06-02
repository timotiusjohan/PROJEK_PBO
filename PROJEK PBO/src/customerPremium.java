
public class customerPremium extends customer {
	private double ktp;
	public customerPremium(String nama, String HP, String email, String password,double ktp) {
		super(nama, HP, email, password);
		this.ktp=ktp;
	}

	public void info() {
		// TODO Auto-generated method stub
	}

}
