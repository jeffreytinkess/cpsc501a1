

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.vending.frontend4.Cents;
import org.lsmr.vending.frontend4.Coin;
import org.lsmr.vending.frontend4.Product;
import org.lsmr.vending.frontend4.ProductKind;
import org.lsmr.vending.frontend4.hardware.DisabledException;
import org.lsmr.vending.frontend4.hardware.HardwareFacade;



@SuppressWarnings("javadoc")
public class SmallSetupVendingMachineFactoryTest {
    private HardwareFacade hf;
    private VendingMachine vm;
    @Before
    public void setup() {
	vm = new VendingMachine(new Cents[] {new Cents(5), new Cents(10), new Cents(25), new Cents(100)}, 1, 10, 10, 10);
	hf = vm.getHardware();
    }

    /**
     * T08
     */
    @Test
    public void testApproximateChange() throws DisabledException {
	hf.configure(new ProductKind("stuff", new Cents(140)));
	hf.getCoinRack(0).load();
	hf.getCoinRack(1).load(new Coin(new Cents(10)), new Coin(new Cents(10)), new Coin(new Cents(10)), new Coin(new Cents(10)), new Coin(new Cents(10)));
	hf.getCoinRack(2).load(new Coin(new Cents(25)));
	hf.getCoinRack(3).load(new Coin(new Cents(100)));
	hf.getProductRack(0).load(new Product("stuff"));

	hf.getCoinSlot().addCoin(new Coin(new Cents(100)));
	hf.getCoinSlot().addCoin(new Coin(new Cents(100)));
	hf.getCoinSlot().addCoin(new Coin(new Cents(100)));

	hf.getSelectionButton(0).press();
	assertEquals(Arrays.asList(155, "stuff"), Utilities.extractAndSortFromDeliveryChute(hf));
	assertEquals(320, Utilities.extractAndSumFromCoinRacks(hf));
	assertEquals(0, Utilities.extractAndSumFromStorageBin(hf));
    }

    /**
     * T09
     */
    @Test
    public void testHardForChange() throws DisabledException {
	hf.configure(new ProductKind("stuff", new Cents(140)));
	hf.loadCoins(1, 6, 1, 1);
	hf.loadProducts(1);
	hf.getCoinSlot().addCoin(new Coin(new Cents(100)));
	hf.getCoinSlot().addCoin(new Coin(new Cents(100)));
	hf.getCoinSlot().addCoin(new Coin(new Cents(100)));
	hf.getSelectionButton(0).press();
	assertEquals(Arrays.asList(160, "stuff"), Utilities.extractAndSortFromDeliveryChute(hf));
	assertEquals(330, Utilities.extractAndSumFromCoinRacks(hf));
	assertEquals(0, Utilities.extractAndSumFromStorageBin(hf));
	assertEquals(Arrays.asList(), Utilities.extractAndSortFromProductRacks(hf));
    }

    /**
     * T10
     */
    @Test
    public void testInvalidCoins() throws DisabledException {
	hf.configure(new ProductKind("stuff", new Cents(140)));
	hf.loadCoins(1, 6, 1, 1);
	hf.loadProducts(1);
	hf.getCoinSlot().addCoin(new Coin(new Cents(1)));
	hf.getCoinSlot().addCoin(new Coin(new Cents(139)));
	hf.getSelectionButton(0).press();
	assertEquals(Arrays.asList(140), Utilities.extractAndSortFromDeliveryChute(hf));
	assertEquals(190, Utilities.extractAndSumFromCoinRacks(hf));
	assertEquals(0, Utilities.extractAndSumFromStorageBin(hf));
	assertEquals(Arrays.asList("stuff"), Utilities.extractAndSortFromProductRacks(hf));
    }
    /**
     * T13
     */
    @Test
    public void testNeedToStorePayment() throws DisabledException {
	hf.configure(new ProductKind("stuff", new Cents(135)));
	hf.loadCoins(10, 10, 10, 10);
	hf.loadProducts(1);

	hf.getCoinSlot().addCoin(new Coin(new Cents(25)));
	hf.getCoinSlot().addCoin(new Coin(new Cents(100)));
	hf.getCoinSlot().addCoin(new Coin(new Cents(10)));
	hf.getSelectionButton(0).press();
	assertEquals(Arrays.asList(0, "stuff"), Utilities.extractAndSortFromDeliveryChute(hf));
	assertEquals(1400, Utilities.extractAndSumFromCoinRacks(hf));
	assertEquals(135, Utilities.extractAndSumFromStorageBin(hf));
	assertEquals(Arrays.asList(), Utilities.extractAndSortFromProductRacks(hf));
    }

    /**
     * T14
     */
    @Test
    public void testWeirdName() throws DisabledException {
	hf.configure(new ProductKind("\"", new Cents(135)));
    }


	/**
 	* T501_1
 	* Testing add new / change method of payment
 	*/
	@Test
	public void testModifyPaymentMethod() throws DisabledException {
		vm.getMoneyHandler().addPaymentMethod (new CashHandler(hf));
		vm.getMoneyHandler().setMethodOfPayment (1);
		int test = vm.getMoneyHandler().getAllPaymentMethod().length;
		assertEquals(test, 2);
}

/**
 * T501_2
 * Testing a second concurrent vending machine
 */
@Test
public void testTwoConcurrentVM() throws DisabledException {
  VendingMachine vm2 = new VendingMachine(new Cents[] {new Cents(5), new Cents(10), new Cents(25), new Cents(100)}, 1, 10, 10, 10);
	HardwareFacade hf2 = vm2.getHardware();
hf.configure(new ProductKind("stuff", new Cents(140)));
hf.loadCoins(1, 6, 1, 1);
hf.loadProducts(1);
hf.getCoinSlot().addCoin(new Coin(new Cents(100)));
hf.getCoinSlot().addCoin(new Coin(new Cents(100)));
hf.getCoinSlot().addCoin(new Coin(new Cents(100)));

hf2.configure(new ProductKind("stuff", new Cents(140)));
hf2.loadCoins(1, 6, 1, 1);
hf2.loadProducts(1);
hf2.getCoinSlot().addCoin(new Coin(new Cents(100)));
hf2.getCoinSlot().addCoin(new Coin(new Cents(100)));
hf2.getCoinSlot().addCoin(new Coin(new Cents(100)));


hf.getSelectionButton(0).press();
assertEquals(Arrays.asList(160, "stuff"), Utilities.extractAndSortFromDeliveryChute(hf));
assertEquals(330, Utilities.extractAndSumFromCoinRacks(hf));
assertEquals(0, Utilities.extractAndSumFromStorageBin(hf));
assertEquals(Arrays.asList(), Utilities.extractAndSortFromProductRacks(hf));

hf2.getSelectionButton(0).press();
assertEquals(Arrays.asList(160, "stuff"), Utilities.extractAndSortFromDeliveryChute(hf2));
assertEquals(330, Utilities.extractAndSumFromCoinRacks(hf2));
assertEquals(0, Utilities.extractAndSumFromStorageBin(hf2));
assertEquals(Arrays.asList(), Utilities.extractAndSortFromProductRacks(hf2));



}

}
