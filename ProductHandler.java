

import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class ProductHandler {
	private ProductSelector ps;
	public ProductHandler (HardwareFacade hw){
		//create a default product selector
		ps = new PopSelector(hw);
	}
	/*
	 * Called by the UI to indicate a selection has been made by the user
	 */
	public void purchaseItem (int i){
		if (!ps.productAvailable(i)){
			VendingMachine.getUI().displayGeneralError("Product cannot be dispensed. Please check dispenser or try another selection");
			return;
		}
		int cost = ps.getPrice(i);
		boolean success = VendingMachine.getMoneyHandler().makePurchase(cost);
		if (success){
			boolean b = ps.dispenseProduct(i);
			if (!b){
				//display error message here, problem during delivery
				VendingMachine.getUI().displayHardwareError();
			}
		} else {
			//Display payment failure here
			VendingMachine.getUI().displayPaymentError();
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
