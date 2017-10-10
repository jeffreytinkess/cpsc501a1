
import java.util.ArrayList;
import java.util.List;

import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class MoneyHandler {

	//Used to select which method of payment will be used
	private List <Chargeable> availablePaymentMethods;
	private HardwareFacade hardware;
	private Chargeable currPayMethod;
 	private UIController ui;
	private ProductHandler productHandler;
	public MoneyHandler(HardwareFacade hw){

			//Defaults to cash payment
			availablePaymentMethods = new ArrayList<Chargeable>();
			CashHandler defaultPayment = new CashHandler(hw);
			availablePaymentMethods.add(defaultPayment);
			currPayMethod = defaultPayment;
			hardware = hw;
			ui = null;
			productHandler = null;


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

	/*
	 * Add a new method of payment to an existing money handler
	 */
	public void addPaymentMethod (Chargeable c){
		availablePaymentMethods.add(c);
	}

	/*
	 * Remove a method of payment from this money handler
	 *
	 */

	public void removePaymentMethod (int i){
		if (i < availablePaymentMethods.size() && i >= 0){
			availablePaymentMethods.remove(i);
		}
	}
	/*
	 * Used to change the method of payment at runtime.
	 * @param i The identifier of the payment method
	 * @return true if properly set, false otherwise.
	 */
	public boolean setMethodOfPayment (int i){
		if (i >= availablePaymentMethods.size() || i < 0){
			return false;
		} else {
			currPayMethod = availablePaymentMethods.get(i);
			return true;
		}
	}
	/*
	 * Get the in order list of payment methods that this vending machine is configured to use. Can be used to display methods to a customer
	 * @return The list of payment method descriptions. The index of the description is the identifier for that payment method
	 */
	public String[] getAllPaymentMethod(){
		String[] toReturn = new String[availablePaymentMethods.size()];
		for (int i = 0; i < availablePaymentMethods.size(); i++){
			toReturn[i] = availablePaymentMethods.get(i).paymentDescription();
		}
		return toReturn;
	}

	/*
	 * Attempt to have the correct amount of money charged to the consumer for the purchase
	 * @param cost The cost of the payment required
	 * @return true if the payment was successful, false otherwise
	 */
	public boolean makePurchase (int cost){
		return currPayMethod.charge(cost);
	}

}
