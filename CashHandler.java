

import java.util.ArrayList;
import java.util.Collections;

import org.lsmr.vending.frontend4.Coin;
import org.lsmr.vending.frontend4.hardware.AbstractHardware;
import org.lsmr.vending.frontend4.hardware.AbstractHardwareListener;
import org.lsmr.vending.frontend4.hardware.CapacityExceededException;
import org.lsmr.vending.frontend4.hardware.CoinReceptacle;
import org.lsmr.vending.frontend4.hardware.CoinReceptacleListener;
import org.lsmr.vending.frontend4.hardware.DisabledException;
import org.lsmr.vending.frontend4.hardware.EmptyException;
import org.lsmr.vending.frontend4.hardware.HardwareFacade;

public class CashHandler implements Chargeable, CoinReceptacleListener{
	
	//The total amount of money that has been inserted so far
	private int credit;
	private HardwareFacade hardware;
	
	
	
	public CashHandler (HardwareFacade hw){
		credit = 0;
		hardware = hw;
		hw.getCoinReceptacle().register(this);
		
		
	}

	@Override
	public Boolean charge(int price) {
		if (price > credit){
			return false;
		}else {
			//move coins from receptacle to slots
			
			//calculate and return change
			returnChange(credit-price);
			//reset credit and return true
			try {
				hardware.getCoinReceptacle().storeCoins();
			} catch (CapacityExceededException | DisabledException e) {
				
			}
			return true;
		}
	}
	
	public String paymentDescription(){
		return "Cash (CAD coins)";
	}
	
	public void returnChange(int change){
		int changeRemaining = change;
		int numCoinKind = hardware.getNumberOfCoinRacks();
		int[][] sortedDenom = changeKinds();
		//Find the largest coin that hasn't been used yet and has at least one coin available
		for (int j = 0; j < numCoinKind;j++){
			if (changeRemaining == 0){
				break;
			}
			int i = sortedDenom[0][j];
			int value = sortedDenom[1][j];
			try{
				if (changeRemaining - value < 0 || hardware.getCoinRack(i).size() <= 0){
					continue;
				}	
				changeRemaining = changeRemaining-value;
				j--;
				try {
					hardware.getCoinRack(i).releaseCoin();
				} catch (CapacityExceededException | EmptyException | DisabledException e) {
					e.printStackTrace();
				}
			} catch (NullPointerException e){
				//Should never happen, means a coin kind was removed somehow.
				System.err.println("Coin rack for value " + i + " removed, null pointer thrown in returnChange");			
			}
			
		}
	}
	
	private int[][] changeKinds(){
		//returns an array with the first row being the index of the specified coin rack, the second row being the value of the coin, sorted in descending order
		int numRack = hardware.getNumberOfCoinRacks();
		int[][] toReturn = new int [2][numRack];
		for (int i = 0; i < numRack; i++){
			toReturn[0][i] = i;
			toReturn[1][i] = hardware.getCoinKindForCoinRack(i).getValue();
			for (int j = i; j > 0; j--){
				if (toReturn[1][j] > toReturn[1][j-1]){
					//swap them
					int tempIndex = toReturn [0][j];
					int tempVal = toReturn[1][j];
					toReturn[0][j] = toReturn[0][j-1];
					toReturn[1][j] = toReturn[1][j-1];
					toReturn[0][j-1] = tempIndex;
					toReturn[1][j-1] = tempVal;
				}
			}
		}
		return toReturn;
	}

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {}

	@Override
	public void coinAdded(CoinReceptacle receptacle, Coin coin) {
		credit += coin.getValue().getValue();		
	}

	@Override
	public void coinsRemoved(CoinReceptacle receptacle) {
		credit = 0;		
	}

	@Override
	public void coinsFull(CoinReceptacle receptacle) {}

	@Override
	public void coinsLoaded(CoinReceptacle receptacle, Coin... coins) {
		for (Coin coin:coins){
			credit += coin.getValue().getValue();
		}
	}

	@Override
	public void coinsUnloaded(CoinReceptacle receptacle, Coin... coins) {
		for (Coin coin:coins){
			credit -= coin.getValue().getValue();
		}
	}

}
