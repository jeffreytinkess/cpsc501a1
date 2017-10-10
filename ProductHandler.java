

import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class ProductHandler {
	private ProductSelector ps;
	private MoneyHandler mh;
	private UIController ui;
	public ProductHandler (HardwareFacade hw){
		//create a default product selector
		ps = new PopSelector(hw);
		mh = null;
		ui = null;
	}

	/*
	*
	* @return True if setting UI for first time, false if replacing an existing UI
	*/

	public boolean registerUI(UIController uiIn){
		if (ui == null){
			ui = uiIn;
			return true;
		} else {
			ui = uiIn;
			return false;
		}
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
	 * Called by the UI to indicate a selection has been made by the user
	 */
	public void purchaseItem (int i){
		if (!ps.productAvailable(i)){
			ui.displayGeneralError("Product cannot be dispensed. Please check dispenser or try another selection");
			return;
		}
		int cost = ps.getPrice(i);
		boolean success = mh.makePurchase(cost);
		if (success){
			boolean b = ps.dispenseProduct(i);
			if (!b){
				//display error message here, problem during delivery
				ui.displayHardwareError();
			}
		} else {
			//Display payment failure here
			ui.displayPaymentError();
		}
	}
	/*
	 * Change the concrete product selector being used
	 * @returns true if selector updated properly
	 */
	public boolean setProductSelector(ProductSelector p){
		if (p == null){
			return false;
		} else {
			ps = p;
			return true;
		}
	}
}
