package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import GUI.*;
import myFunctions.MyFunctions;
import observable.*;
import observers.*;
import projectObserver.ProjectObserver;

public class Controller {
	public static void refreshRecentView() {
		GUI.getRecentView().refresh();
	}
	
	public static void setGUI() {
		GUI.setGUI();
	}
	
	public static void setTimeLabelText(String text) {
		GUI.getTimeLabel().setText(text);
	}
	
	public static String getTime() {
		int hour = ProjectObserver.getMarket().getNow().getHour();
		int minute = ProjectObserver.getMarket().getNow().getMinute();
		if(hour<10) {
			if(minute==0)
				return ProjectObserver.getMarket().getNow().getYear()+" "+ProjectObserver.getMarket().getNow().getMonthValue()+" "+ProjectObserver.getMarket().getNow().getDayOfMonth()+" 0"+hour+":00";
			else
				return ProjectObserver.getMarket().getNow().getYear()+" "+ProjectObserver.getMarket().getNow().getMonthValue()+" "+ProjectObserver.getMarket().getNow().getDayOfMonth()+" 0"+hour+":"+ProjectObserver.getMarket().getNow().getMinute();
		}
		else {
			if(minute==0)
				return ProjectObserver.getMarket().getNow().getYear()+" "+ProjectObserver.getMarket().getNow().getMonthValue()+" "+ProjectObserver.getMarket().getNow().getDayOfMonth()+" "+hour+":00";
			else
				return ProjectObserver.getMarket().getNow().getYear()+" "+ProjectObserver.getMarket().getNow().getMonthValue()+" "+ProjectObserver.getMarket().getNow().getDayOfMonth()+" "+hour+":"+ProjectObserver.getMarket().getNow().getMinute();
		}
	}
	
	public static void setRecentView(View newView) {
		GUI.setRecentView(newView);
	}
	
	public static class NextTurnListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			ProjectObserver.resumeTimer();
		}
	}
	
	public static String getRecentPortfoliosName() {
		return ProjectObserver.getRecentPortfolio().getName();
	}
	
	public static int getRecentPortfoliosBalance() {
		return ProjectObserver.getRecentPortfolio().getSaldo();
	}
	
	public static int getRecentPortfoliosValue() {
		return ProjectObserver.getRecentPortfolio().getValue();
	}
	
	public static ArrayList<Integer> getRecentPortfoliosHistoricValuesList() {
		return ProjectObserver.getRecentPortfolio().getHistoricalValueList();
	}
	
	public static class RemovePortfolioButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean removed = false;
			for(Portfolio portfolio : Portfolio.getPortfoliosList()) {
				if(ViewPortfolios.getPortfoliosNameTextField().getText().equals(portfolio.getName())) {
					Portfolio.removePortfolio(portfolio);
					removed = true;
					break;
				}
			}
			
			if(!removed) {
				JOptionPane.showMessageDialog(null, "Nie można usunąć podanego portfela, portfel o podanej nazwie: "+ViewPortfolios.getPortfoliosNameTextField().getText()+", nie istnieje", "Nie można usunąć podanego portfela", 2);
			}
			GUI.getRecentView().refresh();
		}
	}
	
	public static class ViewPortfolioListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GUI.setRecentView(new ViewPortfolios());
			GUI.getRecentView().refresh();
		}
	}
	
	public static class ChoosePortfolioButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			boolean isChosen = false;
			if(MyFunctions.isAlphanumerical(ViewPortfolios.getPortfoliosNameTextField().getText())) {
				for(Portfolio portfolio : Portfolio.getPortfoliosList()) {
					if(portfolio.getName().equals(ViewPortfolios.getPortfoliosNameTextField().getText())) {
						ProjectObserver.setRecentPortfolio(portfolio);
						isChosen=true;
						break;
					}
				}
			}
			if(isChosen)
				GUI.getRecentView().refresh();
			else
				JOptionPane.showMessageDialog(null, "Nie można wczytać podanego portfela, portfel o podanej nazwie: "+ViewPortfolios.getPortfoliosNameTextField().getText()+", nie istnieje", "Nie można wczytać podanego portfela", 2);
		}
	}
	
	public static class CreatePortfolioButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			if(MyFunctions.isAlphanumerical(ViewPortfolios.getPortfoliosNameTextField().getText())) {
				boolean isPossible = true;
				for(Portfolio portfolio : Portfolio.getPortfoliosList())
					if(portfolio.getName().equals(ViewPortfolios.getPortfoliosNameTextField().getText()))
						isPossible = false;

				if(isPossible)
					new Portfolio(ViewPortfolios.getPortfoliosNameTextField().getText());
				else
					JOptionPane.showMessageDialog(null, "Portfel o nazwie: "+ViewPortfolios.getPortfoliosNameTextField().getText()+", już istnieje", "Nie można utworzyć portfela o podanej nazwie", 0);
			}
			else
				JOptionPane.showMessageDialog(null,"Nazwa: "+ViewPortfolios.getPortfoliosNameTextField().getText()+ ", jest nieprawidłowa. Proszę podać nazwę alfanumeryczną, o niezerowej długości i bez spacji", "Nie można utworzyć portfela o podanej nazwie", 0);
			GUI.getRecentView().refresh();
		}
	}
	
	public static class DisplayViewPortfoliosValueHistoryListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GUI.setRecentView(new ViewDisplayPortfoliosValueHistory());
			GUI.getRecentView().refresh();
		}
	}
	
	public static class ConfirmSaleButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean isFound = false;
			for(Share share : ProjectObserver.getMarket().getSharesAtTheMarketList()) {
				if(GUI.getSymbolTextField().getText().equals(share.getCompanyShortName())) {
					isFound = true;
					if(MyFunctions.isPositiveInteger(GUI.getAmountTextField().getText())) {
						ProjectObserver.getRecentPortfolio().sellShares(share, new Integer(GUI.getAmountTextField().getText()));
					}
					else {
						JOptionPane.showMessageDialog(null, "Nie udało się sprzedać akcji:"+GUI.getSymbolTextField().getText()+", w ilości: "+GUI.getAmountTextField().getText(), "Nie udało się sprzedać podanej ilości akcji", 2);
					}
				}
			}
			
			if(!isFound) {
				JOptionPane.showMessageDialog(null, "Nie udało się odnaleźć w portfelu akcji: "+GUI.getSymbolTextField().getText(), "Nie udało się odnaleźć w portfelu akcji", 2);
			}
		}
	}
	
	public static class SellSharesListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			GUI.setRecentView(new ViewSellShares());
			GUI.getRecentView().refresh();
		}
	}
	
	public static class DisplaySharesQuotationHistoryListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			GUI.setRecentView(new ViewDisplaySharesQuotationHistory());
			GUI.getRecentView().refresh();
		}
	}
	
	public static class ChooseButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			boolean isFound = false;
			for(Share share : ProjectObserver.getMarket().getSharesAtTheMarketList()) {
				if(GUI.getQuotationsHistoryTextField().getText().equals(share.getCompanyShortName())) {
					isFound = true;
					ViewDisplaySharesQuotationHistory.setRecentlyDisplayedShare(share);
					ViewDisplaySharesQuotationHistory.displaySharesQoutationsHistory(share);
				}
			}
			
			if(!isFound)
				JOptionPane.showMessageDialog(null, "Nie udało się odnaleźć akcji: "+GUI.getSymbolTextField().getText(), "Nie udało się odnaleźć akcji", 2);

			GUI.getRecentView().refresh();
		}
	}
	
	public static String[][] fillPortfoliosTable()
	{
		String[][] data = new String[Portfolio.getPortfoliosList().size()][3];
		
		Iterator<Portfolio> itr = Portfolio.getPortfoliosList().iterator();
		int i=0;
		Portfolio portfolio = null;
		
		while(itr.hasNext()) {
			portfolio = itr.next();
			data[i][0] = portfolio.getName();
			data[i][1] = (MyFunctions.amountZlotowki(new Integer(portfolio.getSaldo()))).toString();
			data[i][2] = (MyFunctions.amountZlotowki(new Integer(portfolio.getValue()))).toString();
			i++;
		}
		
		return data;
	}
	
	public static String[][] fillSharesAtTheMarketTable() {
		String[][] data = new String[ProjectObserver.getMarket().getSharesAtTheMarketList().size()][4];
		
		Iterator<Share> itr = ProjectObserver.getMarket().getSharesAtTheMarketList().iterator();
		int i=0;
		Share share = null;
		
		while(itr.hasNext()) {
			share = itr.next();
			data[i][0] = share.getCompanyShortName();
			data[i][1] = MyFunctions.amountZlotowki(share.getPriceInGrosze());
			data[i][2] = new Integer(share.getAmountOfSharesInTheMArket()).toString();
			data[i][3] = MyFunctions.amountZlotowki(new Long(share.getAmountOfSharesInTheMArket())*new Long(share.getPriceInGrosze()));
			i++;
		}
		
		return data;
	}
	
	public static String[][] fillDifferentSharesInAPortfolioTable() {
		String[][] data = new String[ProjectObserver.getRecentPortfolio().getDifferentSharesInAPortfolioSet().size()][4];
		
		Iterator<String> itr = ProjectObserver.getRecentPortfolio().getDifferentSharesInAPortfolioSet().iterator();
		int i=0;
		String share = null;
		
		while(itr.hasNext()) {
			share = itr.next();
			data[i][0] = share;
			data[i][1] = ProjectObserver.getRecentPortfolio().findThePriceOfAChosenShare(share);
			data[i][2] = new Integer(ProjectObserver.getRecentPortfolio().countChosenSharesInAPortfolio(share)).toString();
			data[i][3] = ProjectObserver.getRecentPortfolio().calculateTotalValueOfChosenShareInAPortfolio(share);
			i++;
		}
		
		return data;
	}
	
	public static class ConfirmPurchaseButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			boolean wouldBeBought = false;
			for(Share share : ProjectObserver.getMarket().getSharesAtTheMarketList()) {
				if(GUI.getSymbolTextField().getText().equals(share.getCompanyShortName())) {
					wouldBeBought = true;
					if(MyFunctions.isPositiveInteger(GUI.getAmountTextField().getText())) {
						ProjectObserver.getRecentPortfolio().boyShares(share, new Integer(GUI.getAmountTextField().getText()));
						break;
					}
					else
						JOptionPane.showMessageDialog(null, "Nie udało się kupić akcji:"+GUI.getSymbolTextField().getText()+", w ilości: "+GUI.getAmountTextField().getText(), "Nie udało się kupić podanej ilości akcji", 2);
				}
			}
			
			if(!wouldBeBought)
				JOptionPane.showMessageDialog(null, "Nie udało się kupić akcji: "+GUI.getSymbolTextField().getText(), "Nie udało się kupić akcji", 1);
		}
	}
	
	public static class BuySharesListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			GUI.setRecentView(new ViewBuyShares());
			GUI.getRecentView().refresh();
		}
	}
	
	public static boolean getIsTimerOn() {
		return ProjectObserver.getIsTimerOn();
	}
}
