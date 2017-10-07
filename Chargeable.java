
/*
 * Interface for payment methods that are charged after a cost is known, such as credit cards or paypal
 */
public interface Chargeable {
	
	/*
	 * Returns true if payment received successfully
	 */
	public Boolean charge (int price);
	/*
	 * Returns a string describing the payment type
	 */
	public String paymentDescription ();
}
