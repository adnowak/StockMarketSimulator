package projectObserver;

import java.util.Timer;
import java.util.TimerTask;

import controller.Controller;
import observable.*;
import observers.*;

public class ProjectObserver 
{
	private static Portfolio recentPortfolio;
	private static Portfolio defaultPortfolio;
	private static Market market;
	private static Timer timer;
	private static boolean isTimerOn = false;
		
	public static boolean getIsTimerOn() 
	{
		return isTimerOn;
	}

	public static Market getMarket() 
	{
		return market;
	}

	public static void setMarket(Market market) 
	{
		ProjectObserver.market = market;
	}

	public static void main(String[] args) 
	{
		market = new Market();
		defaultPortfolio = new Portfolio("DomyslnyPortfel");
		recentPortfolio = defaultPortfolio;
		Controller.setGUI();
		market.createShares();
	}
	
	public static Portfolio getDefaultPortfolio() 
	{
		return defaultPortfolio;
	}

	public static void resumeTimer()
	{
		if(isTimerOn)
		{
			isTimerOn=false;
			timer.cancel();
			Controller.refreshRecentView();
		}
		else
		{
			isTimerOn=true;
			timer= new Timer();
			timer.schedule(new TimerTick(), 1000);
		}
	}
	
	public static class TimerTick extends TimerTask
	{
		@Override
		public void run() 
		{
			market.nextTurn();
			Controller.refreshRecentView();
			timer= new Timer();
			timer.schedule(new TimerTick(), 1000);
		}
	}

	public static Portfolio getRecentPortfolio() 
	{
		return recentPortfolio;
	}

	public static void setRecentPortfolio(Portfolio recentPortfolio) 
	{
		ProjectObserver.recentPortfolio = recentPortfolio;
	}	
}


