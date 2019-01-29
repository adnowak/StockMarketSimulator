package observable;

import java.time.*;
import java.util.ArrayList;

import observers.*;

public class Market implements Subject
{
	private LocalDateTime now = LocalDateTime.of(LocalDateTime.now().getYear()+20, LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 8, 0);
	private int hourOfNow=8, minuteOfNow=0;
	private ArrayList<Share> sharesAtTheMarketList ;
	private ArrayList<Observer> observersList = new ArrayList<Observer>();
	
	public ArrayList<Share> getSharesAtTheMarketList() 
	{
		return sharesAtTheMarketList;
	}

	public Market()
	{
		sharesAtTheMarketList = new ArrayList<Share>();
	}
	
	public LocalDateTime getNow() 
	{
		return now;
	}

	public void setNow(LocalDateTime now) 
	{
		this.now = now;
	}
	
	public void nextTurn()
	{
		for(Share share : sharesAtTheMarketList)
		{
			share.nextTurn();
		}
		
		setNewTime();
		notifyObservers();
	}
	
	private void setNewTime()
	{
		if(minuteOfNow==0)
		{
			minuteOfNow=30;
			now = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hourOfNow, 30);
		}
		else
		{
			minuteOfNow=0;
			if(hourOfNow!=18) 
			{
				hourOfNow++;
				now = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hourOfNow, 0);
			}
			else
			{
				hourOfNow=8;
				now = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 8, 0).plusDays(1);
			}
		}
	}
	
	public void createShares()
	{
		for(int i=0; i<50; i++)
		{
			sharesAtTheMarketList.add(new Share());
		}
	}

	@Override
	public void registerObserver(Observer observer) 
	{
		observersList.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) 
	{
		observersList.remove(observer);
	}

	@Override
	public void notifyObservers() 
	{
		for(Observer observer : observersList)
		{
			observer.update();
		}
	}
}
