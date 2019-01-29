package GUI;

import java.awt.BorderLayout;
import java.awt.*;

import javax.swing.*;

import controller.Controller;
import myFunctions.MyFunctions;

public class ViewPortfolios extends View
{
	private static JTextField portfoliosNameTextField = new JTextField(30);
	
	public static JTextField getPortfoliosNameTextField() 
	{
		return portfoliosNameTextField;
	}

	public static void displayViewPortfolios()
	{
		GUI.getMainPanel().removeAll();
		GUI.getMainPanelsHeader().removeAll();
		GUI.getSharesTable().removeAll();
		GUI.getScrollPane().removeAll();
		JLabel enterNameLabel = new JLabel("Wpisz nazwę portfela: ");
		
		JButton choosePortfolioButton = new JButton("Wybierz podany portfel");
		enterNameLabel.setHorizontalAlignment(JLabel.CENTER);
		JButton createPortfolioButton = new JButton("Utwórz podany portfel");
		JButton removePortfolioButton = new JButton("Usuń podany portfel");
		
		createPortfolioButton.addActionListener(new Controller.CreatePortfolioButtonListener());
		choosePortfolioButton.addActionListener(new Controller.ChoosePortfolioButtonListener());
		removePortfolioButton.addActionListener(new Controller.RemovePortfolioButtonListener());
		
		GUI.getMainPanelsHeader().setLayout(new GridLayout(3,2));
		
		GUI.getMainPanelsHeader().add(enterNameLabel);
		GUI.getMainPanelsHeader().add(new Label());
		GUI.getMainPanelsHeader().add(portfoliosNameTextField);
		GUI.getMainPanelsHeader().add(choosePortfolioButton);
		GUI.getMainPanelsHeader().add(createPortfolioButton);
		GUI.getMainPanelsHeader().add(removePortfolioButton);
		
		JLabel nameLabel = new JLabel("Obecnie wybrany portfel: "+Controller.getRecentPortfoliosName()+"  ");
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel saldoLabel = new JLabel("Twoje saldo to: "+MyFunctions.amountZlotowki(Controller.getRecentPortfoliosBalance()));
		saldoLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel valueLabel = new JLabel("Łączna wartość twojego portfela to: "+MyFunctions.amountZlotowki(Controller.getRecentPortfoliosValue()));
		valueLabel.setHorizontalAlignment(JLabel.CENTER);
		
		GUI.getMainPanelsHeader().add(nameLabel);
		GUI.getMainPanelsHeader().add(saldoLabel);
		GUI.getMainPanelsHeader().add(valueLabel);
		
		if(Controller.getIsTimerOn())
		{
			portfoliosNameTextField.setEditable(false);
			choosePortfolioButton.setEnabled(false);
			createPortfolioButton.setEnabled(false);
			removePortfolioButton.setEnabled(false);
			GUI.getScrollPane().setEnabled(false);
		}
		else
		{
			portfoliosNameTextField.setEditable(true);
			choosePortfolioButton.setEnabled(true);
			createPortfolioButton.setEnabled(true);
			removePortfolioButton.setEnabled(true);
			GUI.getScrollPane().setEnabled(true);
		}
		
		String[] collumns = {"Nazwa portfela", "Saldo", "Wartość"};
		String[][] data = Controller.fillPortfoliosTable();
		
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
		displayViewPortfolios();
	}
}
