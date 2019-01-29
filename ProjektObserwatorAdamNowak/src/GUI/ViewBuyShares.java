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

public class ViewBuyShares extends View
{
	public static void displayViewBuyShares()
	{
		GUI.getMainPanel().removeAll();
		GUI.getMainPanelsHeader().removeAll();
		GUI.getScrollPane().removeAll();
		GUI.getSharesTable().removeAll();
		
		GUI.getMainPanelsHeader().setLayout(new GridLayout(3,2));
		
		JLabel symbolLabel  = new JLabel("Wpisz symbol akcji, którą chcesz kupić:");
		symbolLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel amountLabel  = new JLabel("Podaj ilość zakupowanych akcji:");
		amountLabel.setHorizontalAlignment(JLabel.CENTER);
		JButton confirmPurchaseButton = new JButton("Potwierdz zakup");
		JLabel nameLabel = new JLabel("Obecnie wybrany portfel: "+Controller.getRecentPortfoliosName()+"  ");
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel saldoLabel = new JLabel("Twoje saldo to: "+MyFunctions.amountZlotowki(Controller.getRecentPortfoliosBalance()));
		saldoLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel valueLabel = new JLabel("Łączna wartość twojego portfela to: "+MyFunctions.amountZlotowki(Controller.getRecentPortfoliosValue()));
		valueLabel.setHorizontalAlignment(JLabel.CENTER);
		valueLabel.setPreferredSize(new Dimension(333, valueLabel.getPreferredSize().height));
		
		confirmPurchaseButton.addActionListener(new Controller.ConfirmPurchaseButtonListener());
		
		
		GUI.getMainPanelsHeader().add(symbolLabel);
		GUI.getMainPanelsHeader().add(amountLabel);
		GUI.getMainPanelsHeader().add(new JLabel());
		GUI.getMainPanelsHeader().add(GUI.getSymbolTextField());
		GUI.getMainPanelsHeader().add(GUI.getAmountTextField());
		GUI.getMainPanelsHeader().add(confirmPurchaseButton);
		GUI.getMainPanelsHeader().add(nameLabel);
		GUI.getMainPanelsHeader().add(saldoLabel);
		GUI.getMainPanelsHeader().add(valueLabel);
		
		String[] collumns = {"Symbol", "Cena", "Ilość w obiegu", "Łączna wartość"};
		String[][] data = Controller.fillSharesAtTheMarketTable();
		
		if(Controller.getIsTimerOn())
		{
			GUI.getSymbolTextField().setEditable(false);
			GUI.getAmountTextField().setEditable(false);
			confirmPurchaseButton.setEnabled(false);
			GUI.getScrollPane().setEnabled(false);
		}
		else
		{
			GUI.getSymbolTextField().setEditable(true);
			GUI.getAmountTextField().setEditable(true);
			confirmPurchaseButton.setEnabled(true);
			GUI.getScrollPane().setEnabled(true);
		}

		GUI.setSharesTable(new JTable(data, collumns));
		GUI.getSharesTable().setLayout(new FlowLayout());
		GUI.getSharesTable().setPreferredScrollableViewportSize(new Dimension(200, 50));
		GUI.getSharesTable().setFillsViewportHeight(true);
		
		GUI.setScrollPane(new JScrollPane(GUI.getSharesTable()));
		GUI.getScrollPane().setPreferredSize(new Dimension(1000, 325));
		
		GUI.getMainPanel().add(BorderLayout.NORTH, GUI.getMainPanelsHeader());
		GUI.getMainPanel().add(BorderLayout.CENTER,GUI.getScrollPane());
		GUI.getFrame().getContentPane().add(BorderLayout.CENTER, GUI.getMainPanel());
		GUI.getFrame().pack();
	}

	@Override
	public void refresh() 
	{
		super.refresh();
		displayViewBuyShares();
	}
}
