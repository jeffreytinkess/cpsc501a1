

public interface ProductSelector {
	/*
	 * Tells the hardware to dispense the product at index i.
	 * @returns true if the product is successfully returned, false if not
	 */
	public boolean dispenseProduct(int i);
	/*
	 * Gets the price of the product from the hardware and returns it as an int, must handle any currency conversions required
	 * @returns the price of the product at index i
	 */
	public int getPrice(int i);
	/*
	 * Checks if the product is in stock and available for purchase
	 * @returns true if it would be possible to dispense a product from this rack
	 */
	public boolean productAvailable(int i);
}
