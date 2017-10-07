

import org.lsmr.vending.frontend4.hardware.CapacityExceededException;
import org.lsmr.vending.frontend4.hardware.DisabledException;
import org.lsmr.vending.frontend4.hardware.EmptyException;
import org.lsmr.vending.frontend4.hardware.HardwareFacade;

/*
 * Concrete selector class that converts prices from cents to int and dispenses pop, checking a physical dispense chute for room
 */
public class PopSelector implements ProductSelector{
	HardwareFacade hardware;
	
	public PopSelector(HardwareFacade h){
		hardware = h;
	}
	
	
	@Override
	public boolean dispenseProduct(int i) {
		
			try {
				hardware.getProductRack(i).dispenseProduct();
			} catch (DisabledException | EmptyException | CapacityExceededException e) {
				return false;
			}
			return true;
		
	}

	@Override
	public int getPrice(int i) {
		return hardware.getProductKind(i).getCost().getValue();
	}


	@Override
	public boolean productAvailable(int i) {
		if (hardware.getProductRack(i).size() > 0 && hardware.getDeliveryChute().hasSpace()){
			return true;
		} else {
			return false;
		}
	}

}
