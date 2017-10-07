
/*
 * Default system for returning a message to the user, prints messages to console
 */
public class DefaultOut implements UIOutput{

	@Override
	public void displayPaymentError(String out) {
		System.out.println(out);
		
	}

	@Override
	public void displayHardwareError(String out) {
		System.out.println(out);
		
	}

	@Override
	public void displayGeneralError(String out) {
		System.out.println(out);
		
	}

}
