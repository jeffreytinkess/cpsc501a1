/*
*
* Superclass containing register methods for the main components that must communicate with each other
*
*/
public class CentralComponent {
	protected MoneyHandler mh;
  protected ProductHandler ph;
  protected UIController ui;

  public CentralComponent(){
    mh = null;
    ph = null;
    ui = null;
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
		if (ph == null){
			ph = phIn;
			return true;
		} else {
			ph = phIn;
			return false;
		}

	}

  /*
	*
	* @return True if setting mh for first time, false if replacing an existing mh
	*/
	public boolean registerMoneyHandler(MoneyHandler mhIn){
		if (mh == null){
			mh = mhIn;
			return true;
		} else {
			mh = mhIn;
			return false;
		}

	}

}
