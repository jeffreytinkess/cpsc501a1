

import org.lsmr.vending.frontend4.Cents;
import org.lsmr.vending.frontend4.hardware.CoinRack;
import org.lsmr.vending.frontend4.hardware.HardwareFacade;

/**
 * Represents vending machines, fully configured and with all software
 * installed.
 *
 * @author Robert J. Walker
 */
public class VendingMachine {
    private HardwareFacade hf;
    private MoneyHandler mh;
    private ProductHandler ph;
    private UIController ui;
    /* YOU CAN ADD OTHER COMPONENTS HERE */

    /**
     * Creates a standard arrangement for the vending machine. All the
     * components are created and interconnected. The hardware is initially
     * empty. The product kind names and costs are initialized to &quot; &quot;
     * and 1 respectively.
     *
     * @param coinKinds
     *            The values (in cents) of each kind of coin. The order of the
     *            kinds is maintained. One coin rack is produced for each kind.
     *            Each kind must have a unique, positive value.
     * @param selectionButtonCount
     *            The number of selection buttons on the machine. Must be
     *            positive.
     * @param coinRackCapacity
     *            The maximum capacity of each coin rack in the machine. Must be
     *            positive.
     * @param productRackCapacity
     *            The maximum capacity of each product rack in the machine. Must
     *            be positive.
     * @param receptacleCapacity
     *            The maximum capacity of the coin receptacle, storage bin, and
     *            delivery chute. Must be positive.
     * @throws IllegalArgumentException
     *             If any of the arguments is null, or the size of productCosts
     *             and productNames differ.
     */
    public VendingMachine(Cents[] coinKinds, int selectionButtonCount, int coinRackCapacity, int productRackCapacity, int receptacleCapacity) {
	     hf = new HardwareFacade(coinKinds, selectionButtonCount, coinRackCapacity, productRackCapacity, receptacleCapacity);
       createSoftwareInstance(hf);
    }

    private void createSoftwareInstance(HardwareFacade hf){
      //Create the three main classes representing operating software
      mh = new MoneyHandler(hf);
      ph = new ProductHandler(hf);
      ui = new UIController(hf);
    	ButtonInput in = new ButtonInput(hf, ui);
      //Register each element with the other two
      //Register MoneyHandler with ProductHandler and UI
      ph.registerMoneyHandler(mh);
      ui.registerMoneyHandler(mh);
      //Register Producthandler with MoneyHandler and UI
      mh.registerProductHandler(ph);
      ui.registerProductHandler(ph);
      //Register UI with MoneyHandler and ProductHandler
      mh.registerUI(ui);
      ph.registerUI(ui);
    }
    public HardwareFacade getHardware(){
      return hf;
    }
    /*
    * Convenience method for tests
    */
    public MoneyHandler getMoneyHandler(){
      return mh;
    }
}
