

import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class UIController {
	public UIOutput out;
	private boolean useDefaultDisplay;
	private MoneyHandler mh;
	private ProductHandler productHandler;
	public UIController (HardwareFacade hw){
		useDefaultDisplay = true;
		mh = null;
		productHandler = null;

	}
	/*
	*
	* @return True if setting mh for first time, false if replacing an existing mh
	*/
	public boolean registerMoneyHandler(MoneyHandler mhIn){
		if (mh == null){
			mh = mhIn;
			return true;
		} else {
			mh = mhIn;
			return false;
		}

	}
	/*
	*
	* @return True if setting product handler for first time, false if replacing an existing one
	*/
	public boolean registerProductHandler(ProductHandler phIn){
		if (productHandler == null){
			productHandler = phIn;
			return true;
		} else {
			productHandler = phIn;
			return false;
		}

	}
	public void userInput(int i){
		productHandler.purchaseItem(i);
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
