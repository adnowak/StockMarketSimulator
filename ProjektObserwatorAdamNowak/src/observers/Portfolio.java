package observers;

import java.util.*;

import javax.swing.*;

import GUI.*;
import myFunctions.MyFunctions;
import observable.*;
import projectObserver.ProjectObserver;

public class Portfolio implements Observer
{
	private static ArrayList<Portfolio> portfoliosList = new ArrayList<Portfolio>();
	private ArrayList<SharesPublicData> sharesInAPortfolioList = new ArrayList<SharesPublicData>();
	private HashSet<String> differentSharesInAPortfolioSet = new HashSet<String>();
	private ArrayList<Integer> historicalValueList= new ArrayList<Integer>();
	private int saldo, value;
	private String name;
	
	public Portfolio(String name)
	{
		saldo = 10000000;
		value = 10000000;
		historicalValueList.add(value);
		this.setName(name);
		ProjectObserver.getMarket().registerObserver(this);
		portfoliosList.add(this);
		JOptionPane.showMessageDialog(null, "Utworzono nowy portfel, nazwa: "+name+", saldo: "+MyFunctions.amountZlotowki(saldo), "Utworzono nowy portfel", 1);
	}
	
	public static void removePortfolio(Portfolio portfolio)
	{
		if(portfolio.equals(ProjectObserver.getDefaultPortfolio()))
		{
			JOptionPane.showMessageDialog(null, "Nie można usunąć portfela domyślnego", "Nie można usunąć portfelal", 1);
		}
		else
		{
			try 
			{
				if(portfolio.equals(ProjectObserver.getRecentPortfolio()))
				{
					ProjectObserver.setRecentPortfolio(ProjectObserver.getDefaultPortfolio());
				}
				JOptionPane.showMessageDialog(null, "Pomyślnie usunięto portfel: "+portfolio.getName(), "Usunięto portfel", 1);
				ProjectObserver.getMarket().removeObserver(portfolio);
				portfoliosList.remove(portfolio);
				portfolio.finalize();
			} 
			catch (Throwable e) 
			{
				e.printStackTrace();
			}
			portfolio=null;
		}
	}
	
	public boolean equals(Portfolio otherPortfolio)
	{
		if(otherPortfolio.getName().equals(this.getName()))
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
		return this.getName().hashCode();
	}
	
	public static ArrayList<Portfolio> getPortfoliosList() 
	{
		return portfoliosList;
	}

	public HashSet<String> getDifferentSharesInAPortfolioSet() 
	{
		return differentSharesInAPortfolioSet;
	}

	public void setValue(int value) 
	{
		this.value = value;
	}

	public ArrayList<SharesPublicData> getSharesInAPortfolioList() 
	{
		return sharesInAPortfolioList;
	}

	public ArrayList<Integer> getHistoricalValueList() 
	{
		return historicalValueList;
	}

	public int getSaldo() 
	{
		return saldo;
	}

	public void setSaldo(int saldo) 
	{
		this.saldo = saldo;
	}

	public int getValue() 
	{
		return value;
	}
	
	public void writeNewValue()
	{
		calculateValue();
		historicalValueList.add(value);
	}
	
	public void calculateValue()
	{
		int portfoliosValue = saldo;
		for(SharesPublicData share : sharesInAPortfolioList)
		{
			portfoliosValue += share.getPriceInGrosze();
		}
		value = portfoliosValue;
	}
	
	public String calculateTotalValueOfChosenShareInAPortfolio(String shortName)
	{
		Iterator<SharesPublicData> itr = sharesInAPortfolioList.iterator();
		SharesPublicData share = null;
		while(itr.hasNext())
		{
			share = itr.next();
			if(share.getShortName().equals(shortName))
			{
				return MyFunctions.amountZlotowki(share.getPriceInGrosze()*countChosenSharesInAPortfolio(shortName));
			}
		}
		return "ERROR";
	}
	
	public String findThePriceOfAChosenShare(String shortName)
	{
		Iterator<SharesPublicData> itr = sharesInAPortfolioList.iterator();
		SharesPublicData share = null;
		while(itr.hasNext())
		{
			share = itr.next();
			if(share.getShortName().equals(shortName))
			{
				return MyFunctions.amountZlotowki(share.getPriceInGrosze());
			}
		}
		return "BŁĄD";
	}
	
	public int countChosenSharesInAPortfolio(String shortName)
	{
		int amount=0;
		Iterator<SharesPublicData> itr = sharesInAPortfolioList.iterator();
		while(itr.hasNext())
		{
			if(itr.next().getShortName().equals(shortName))
			{
				amount++;
			}
		}
		return amount;
	}
	
	public void sellShares(Share shareBeingSold, int amountOfShares)
	{
		SharesPublicData beingSold = new SharesPublicData(shareBeingSold.getCompanysShortName(), shareBeingSold.getPriceInGrosze());
		if(amountOfShares<=countChosenSharesInAPortfolio(beingSold.getShortName()))
		{
			saldo+=amountOfShares*beingSold.getPriceInGrosze();
			for(int i=0; i<amountOfShares; i++)
			{
				sharesInAPortfolioList.remove(beingSold);
			}
			if(countChosenSharesInAPortfolio(beingSold.getShortName())==0)
			{
				differentSharesInAPortfolioSet.remove((beingSold.getShortName()));
				shareBeingSold.removeObserver(this);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Nie udało się sprzedać akcji", "Nie udało się sprzedać akcji", 1);
		}
		ViewSellShares.displaySellSharesView();
	}
	
	public void boyShares(Share shareBeingBought, int amountOfShares)
	{
		SharesPublicData beingBought = new SharesPublicData(shareBeingBought.getCompanysShortName(), shareBeingBought.getPriceInGrosze());
		if((amountOfShares*beingBought.getPriceInGrosze()<saldo)&&amountOfShares<shareBeingBought.getAmountOfSharesInTheMArket())
		{
			saldo-=amountOfShares*beingBought.getPriceInGrosze();
			for(int i=0; i<amountOfShares; i++)
			{
				sharesInAPortfolioList.add(beingBought);
			}
			differentSharesInAPortfolioSet.add(beingBought.getShortName());
			shareBeingBought.registerObserver(this);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Nie udało się kupić akcji", "Nie udało się kupić akcji", 1);
		}
		ViewBuyShares.displayViewBuyShares();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void update() 
	{
		this.writeNewValue();
	}

	@Override
	public void update(SharesPublicData sharesPublicData) 
	{
		for(SharesPublicData shareInAPortfolio : sharesInAPortfolioList)
		{
			if(sharesPublicData.getShortName().equals(shareInAPortfolio.getShortName()))
			{
				shareInAPortfolio.setPriceInGrosze(sharesPublicData.getPriceInGrosze());
			}
		}
	}
}
