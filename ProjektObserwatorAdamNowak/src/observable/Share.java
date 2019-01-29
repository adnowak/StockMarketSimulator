package observable;

import java.util.*;

import observers.Observer;
import projectObserver.*;

public class Share implements Subject
{
	private ArrayList<Observer> observersList = new ArrayList<Observer>();
	
	private ArrayList<Integer> historicalQuotationsList = new ArrayList<Integer>(); 
	private int priceInGrosze;
	private int amountOfSharesInTheMarket;
	private int companysPolicysFactor;
	private String companysShortName = "";
	int influenceOfManagement = 5;
	
	public ArrayList<Integer> getHistoricalQuotationsList() 
	{
		return historicalQuotationsList;
	}
	
	public boolean equals(Object share)
	{
		if(((Share)share).getCompanysShortName().equals(companysShortName))
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
		return this.companysShortName.hashCode();
	}

	public int getPriceInGrosze() 
	{
		return priceInGrosze;
	}

	public int getAmountOfSharesInTheMArket() 
	{
		return amountOfSharesInTheMarket;
	}

	public String getCompanysShortName() 
	{
		return companysShortName;
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
				if(shortName.equals(share.getCompanysShortName()))
				{
					shortName = setRandomName();
				}
			}
		}
		return shortName;
	}

	public Share()
	{
		Random rnd = new Random();
		//losowanie nazwy
		companysShortName = setRandomName();
		
		//losowanie wspolczynnika polityki spolki
		companysPolicysFactor = influenceOfManagement-rnd.nextInt(2*influenceOfManagement+1);
		
		//losowanie ceny poczatkowej
		priceInGrosze = rnd.nextInt(9900)+100;//aby zwiekszyc realnosc cena poczatkowa bedzie z przedzialu od 1zł do 100zł
		historicalQuotationsList.add(priceInGrosze);
		
		//losowanie ilosci akcji w obiegu
		amountOfSharesInTheMarket = 1000*(rnd.nextInt(19000)+1000);//aby zwiekszyc realnosc, ilosc akcji w obiegu bedzie podzielna przez 1000 i z przedzialu od 1.000.000 do 20.000.000
	}
	
	private void setRandomManagementChange()
	{
		Random rnd = new Random();
		if((rnd.nextDouble()*100)<((10.0-(double)this.companysPolicysFactor)/8.0))
		{
			this.companysPolicysFactor = influenceOfManagement-rnd.nextInt(2*influenceOfManagement+1);
		}
	}
	
	public void nextTurn()
	{
		setRandomManagementChange();
		Random rnd = new Random();
		priceInGrosze = (priceInGrosze*(rnd.nextInt(500)+9756+companysPolicysFactor));
		
		priceInGrosze = new Integer((int) Math.round(new Double(priceInGrosze)/10000));
		
		historicalQuotationsList.add(priceInGrosze);
		notifyObservers();
	}
	
	public String toString()
	{
		int zlotys = (priceInGrosze-(priceInGrosze%100))/100;
		int groszes = priceInGrosze-100*zlotys;
		String cena = new Integer(zlotys).toString() + "zł " + new Integer(groszes).toString()+"gr";
		return companysShortName+" Cena:"+cena+" Ilosc akcji w obiegu:"+amountOfSharesInTheMarket + " Wspolczynnik polityki spolki:"+companysPolicysFactor;
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
			observer.update(new SharesPublicData(this.getCompanysShortName(), this.getPriceInGrosze()));
		}
	}
}
