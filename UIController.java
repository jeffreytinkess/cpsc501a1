

import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class UIController extends CentralComponent{
	public UIOutput out;
	private boolean useDefaultDisplay;
	public UIController (HardwareFacade hw){
		useDefaultDisplay = true;
		ui = this;
	}
	/*
	*
	* Override to set UI to equal this object
	*/

	public boolean registerUI(UIController uiIn){
		ui = this;
		return false;
	}
	public void userInput(int i){
		ph.purchaseItem(i);
	}

	public void displayPaymentError(){
		String toPrint = "Error: Insufficient Payment available for purchase.";
		if (useDefaultDisplay){
			//Send to command line
			System.out.println(toPrint);
		} else {
			//Send to specific display code
			out.displayPaymentError(toPrint);
		}
	}

	public void displayHardwareError(){
		String toPrint = "Error: Hardware malfunction. Please contact a service technician.";
		if (useDefaultDisplay){
			//Send to command line
			System.out.println(toPrint);
		} else {
			//Send to specific display code
			out.displayHardwareError(toPrint);}
	}

	public void displayGeneralError(String s){
		if (useDefaultDisplay){
			//Send to command line
			System.out.println(s);
		} else {
			//Send to specific display code
			out.displayGeneralError(s);
		}
	}
	public void setDisplayObject(UIOutput newOut){
		useDefaultDisplay = false;
		out = newOut;
	}
	public void removeDisplayObject(){
		useDefaultDisplay = true;
		out = null;
	}
}
