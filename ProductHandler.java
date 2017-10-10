

import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class ProductHandler extends CentralComponent{
	private ProductSelector ps;
	public ProductHandler (HardwareFacade hw){
		//create a default product selector
		ps = new PopSelector(hw);
		ph = this;
	}


	/*
	*
	* Override this method to set ph to this
	*/
	public boolean registerProductHandler(ProductHandler phIn){
		ph = this;
		return false;
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
