package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.Controller;
import myFunctions.MyFunctions;

public class ViewSellShares extends View
{	
	public static void displaySellSharesView()
	{
		GUI.getMainPanel().removeAll();
		GUI.getMainPanelsHeader().removeAll();
		GUI.getScrollPane().removeAll();
		GUI.getSharesTable().removeAll();
		
		GUI.getMainPanelsHeader().setLayout(new GridLayout(3,2));
		
		JLabel symbolLabel  =new JLabel("Wpisz symbol akcji, którą chcesz sprzedać:");
		symbolLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel amountLabel  =new JLabel("Podaj ilość sprzedawanych akcji:");
		amountLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel nameLabel = new JLabel("Obecnie wybrany portfel: "+Controller.getRecentPortfoliosName()+"  ");
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel saldoLabel = new JLabel("Twoje saldo to: "+MyFunctions.amountZlotowki(Controller.getRecentPortfoliosBalance()));
		saldoLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel valueLabel = new JLabel("Łączna wartość twojego portfela to: "+MyFunctions.amountZlotowki(Controller.getRecentPortfoliosValue()));
		valueLabel.setHorizontalAlignment(JLabel.CENTER);
		valueLabel.setPreferredSize(new Dimension(333, valueLabel.getPreferredSize().height));
		JButton confirmSaleButton = new JButton("Potwierdz sprzedarz");
		
		confirmSaleButton.addActionListener(new Controller.ConfirmSaleButtonListener());
		
		String[] collumns = {"Symbol", "Cena", "Ilość w portfelu", "Łączna wartość"};
		String[][] data = Controller.fillDifferentSharesInAPortfolioTable();
		
		if(Controller.getIsTimerOn())
		{
			GUI.getSymbolTextField().setEditable(false);
			GUI.getAmountTextField().setEditable(false);
			confirmSaleButton.setEnabled(false);
			GUI.getScrollPane().setEnabled(false);
		}
		else
		{
			GUI.getSymbolTextField().setEditable(true);
			GUI.getAmountTextField().setEditable(true);
			confirmSaleButton.setEnabled(true);
			GUI.getScrollPane().setEnabled(true);
		}
		
		GUI.setSharesTable(new JTable(data, collumns));
		GUI.getSharesTable().setLayout(new FlowLayout());
		GUI.getSharesTable().setPreferredScrollableViewportSize(new Dimension(200, 50));
		GUI.getSharesTable().setFillsViewportHeight(true);
		
		GUI.setScrollPane(new JScrollPane(GUI.getSharesTable()));
		GUI.getScrollPane().setPreferredSize(new Dimension(1000, 325));
		
		GUI.getMainPanelsHeader().add(symbolLabel);
		GUI.getMainPanelsHeader().add(amountLabel);
		GUI.getMainPanelsHeader().add(new JLabel());
		GUI.getMainPanelsHeader().add(GUI.getSymbolTextField());
		GUI.getMainPanelsHeader().add(GUI.getAmountTextField());
		GUI.getMainPanelsHeader().add(confirmSaleButton);
		GUI.getMainPanelsHeader().add(nameLabel);
		GUI.getMainPanelsHeader().add(saldoLabel);
		GUI.getMainPanelsHeader().add(valueLabel);
		
		GUI.getMainPanel().add(GUI.getMainPanelsHeader(), BorderLayout.NORTH);
		GUI.getMainPanel().add(BorderLayout.CENTER,GUI.getScrollPane());
		GUI.getFrame().getContentPane().add(BorderLayout.CENTER, GUI.getMainPanel());
		GUI.getFrame().pack();
	}
	
	

	@Override
	public void refresh() 
	{
		super.refresh();
		displaySellSharesView();
	}
}
