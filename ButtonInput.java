

import org.lsmr.vending.frontend4.hardware.AbstractHardware;
import org.lsmr.vending.frontend4.hardware.AbstractHardwareListener;
import org.lsmr.vending.frontend4.hardware.HardwareFacade;
import org.lsmr.vending.frontend4.hardware.SelectionButton;
import org.lsmr.vending.frontend4.hardware.SelectionButtonListener;

public class ButtonInput implements SelectionButtonListener{
	HardwareFacade hardware;
	UIController ui;
	public ButtonInput(HardwareFacade h, UIController uiIn){
		hardware = h;
		ui = uiIn;
		//Register this class as a listener on every selection button
		int i = hardware.getNumberOfSelectionButtons();
		for (int j = 0; j < i; j++){
			hardware.getSelectionButton(j).register(this);
		}
	}
	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
	}

	@Override
	public void pressed(SelectionButton button) {
		ui.userInput(hardware.indexOf(button));

	}

}
