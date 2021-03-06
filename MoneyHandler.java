
import java.util.ArrayList;
import java.util.List;

import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class MoneyHandler {
	
	//Used to select which method of payment will be used
	private int methodOfPaymentSelector;
	private List <Chargeable> availablePaymentMethods;
	private HardwareFacade hardware;
	
	public MoneyHandler(HardwareFacade hw){
		
			//Defaults to cash payment
			methodOfPaymentSelector = 0;
			availablePaymentMethods = new ArrayList<Chargeable>();
			CashHandler defaultPayment = new CashHandler(hw);
			availablePaymentMethods.add(defaultPayment);
			hardware = hw;
		
		
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
			methodOfPaymentSelector = i;
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
	
	public HardwareFacade getHardware(){
		return hardware;
	}
	
	/*
	 * Attempt to have the correct amount of money charged to the consumer for the purchase
	 * @param cost The cost of the payment required
	 * @return true if the payment was successful, false otherwise
	 */
	public boolean makePurchase (int cost){
		Chargeable c = availablePaymentMethods.get(methodOfPaymentSelector);
		return c.charge(cost);
	}
	
}
