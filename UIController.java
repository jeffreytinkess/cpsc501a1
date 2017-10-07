

import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class UIController {
	public UIOutput out;
	public UIController (HardwareFacade hw){
		out = new DefaultOut();
		
	}
	
	public void userInput(int i){
		VendingMachine.getSingleton().getProductHandler().purchaseItem(i);
	}
	
	public void displayPaymentError(){
		out.displayPaymentError("Error: Insufficient Payment available for purchase.");
	}
	
	public void displayHardwareError(){
		out.displayHardwareError("Error: Hardware malfunction. Please contact a service technician.");
	}
	
	public void displayGeneralError(String s){
		out.displayGeneralError(s);
	}
}
