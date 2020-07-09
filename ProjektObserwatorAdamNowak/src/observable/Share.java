package observable;

import java.util.*;

import observers.Observer;
import projectObserver.*;

public class Share implements Subject
{
	private ArrayList<Observer> observersList = new ArrayList();
	
	private ArrayList<Integer> historicalQuotationsList = new ArrayList();
	private int priceInGrosze;
	private int amountOfSharesInTheMarket;
	private int companyPolicyFactor;
	private String companyShortName = "";
	int influenceOfManagement = 5;
	
	public ArrayList<Integer> getHistoricalQuotationsList() 
	{
		return historicalQuotationsList;
	}


	public Share()
	{
		companyShortName = setRandomName();
		companyPolicyFactor = influenceOfManagement-new Random().nextInt(2*influenceOfManagement+1);
		priceInGrosze = new Random().nextInt(9900)+100;
		historicalQuotationsList.add(priceInGrosze);
		amountOfSharesInTheMarket = 1000*(new Random().nextInt(19000)+1000);
	}
	
	public boolean equals(Object share)
	{
		if(((Share)share).getCompanyShortName().equals(companyShortName))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int hashCode()
	{
		return this.companyShortName.hashCode();
	}

	public int getPriceInGrosze() 
	{
		return priceInGrosze;
	}

	public int getAmountOfSharesInTheMArket() 
	{
		return amountOfSharesInTheMarket;
	}

	public String getCompanyShortName()
	{
		return companyShortName;
	}
	
	private static String setRandomName()
	{
		String shortName = "";
		Random rnd = new Random();
		for(int i=0; i<3; i++)
		{
			shortName += (char) (rnd.nextInt(26) + 'A');
		}
		ArrayList<Share> sharesList = ProjectObserver.getMarket().getSharesAtTheMarketList();
		if(sharesList.size()!=0)
		{
			for(Share share : ProjectObserver.getMarket().getSharesAtTheMarketList())
			{
				if(shortName.equals(share.getCompanyShortName()))
				{
					shortName = setRandomName();
				}
			}
		}
		return shortName;
	}
	
	private void setRandomManagementChange()
	{
		Random rnd = new Random();
		if((rnd.nextDouble()*100)<((10.0-(double)this.companyPolicyFactor)/8.0))
		{
			this.companyPolicyFactor = influenceOfManagement-rnd.nextInt(2*influenceOfManagement+1);
		}
	}
	
	public void nextTurn()
	{
		setRandomManagementChange();
		priceInGrosze = (priceInGrosze*(new Random().nextInt(500)+9756+ companyPolicyFactor));
		
		priceInGrosze = new Integer((int) Math.round(new Double(priceInGrosze)/10000));
		
		historicalQuotationsList.add(priceInGrosze);
		notifyObservers();
	}
	
	public String toString()
	{
		int zlotys = (priceInGrosze-(priceInGrosze%100))/100;
		int groszes = priceInGrosze-100*zlotys;
		String cena = new Integer(zlotys).toString() + "zł " + new Integer(groszes).toString()+"gr";
		return companyShortName +" Cena:"+cena+" Ilosc akcji w obiegu:"+amountOfSharesInTheMarket + " Wspolczynnik polityki spolki:"+ companyPolicyFactor;
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
			observer.update(new SharesPublicData(this.getCompanyShortName(), this.getPriceInGrosze()));
		}
	}
}
