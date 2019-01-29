package GUI;

import java.awt.*;

import javax.swing.*;

import controller.Controller;

public class GUI 
{
	private static View recentView ;
	
	private static JFrame frame = new JFrame();
	private static JPanel navigationPanel = new JPanel();
	private static JPanel mainPanel = new JPanel();
	private static JPanel mainPanelsHeader = new JPanel();
	private static JPanel timePanel = new JPanel();
	
	private static JTextField quotationsHistoryTextField = new JTextField(3);
	private static JTextField symbolTextField = new JTextField(3);
	private static JTextField amountTextField = new JTextField(10);
	
	private static JTable sharesTable = new JTable();
	private static JScrollPane scrollPane = new JScrollPane();
	
	private static JLabel timeLabel = new JLabel();
	
	public static void setGUI()
	{
		Controller.setRecentView(new ViewPortfolios());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
		timePanel.setLayout(new GridLayout(4,3));
		
		MyButton myPortfolioButton = new MyButton("Portfele", 200, 80);
		MyButton buySharesButton = new MyButton("Kup Akcje", 200, 80);
		MyButton sellSharesButton = new MyButton("Sprzedaj Akcje", 200, 80);
		MyButton displaySharesQuotationsHistoryButton = new MyButton("<html> Wyswietl Historie<br>&ensp Notowan Akcji</html>", 200, 80);
		MyButton displayPortfoliosHistoricalValueButton = new MyButton("<html> Wyswietl Historie<br>Wartosci Portfela</html>", 200, 80);
		MyButton timerChangeButton = new MyButton("Czas Start/Stop", 240, 20);
		
		myPortfolioButton.addActionListener(new Controller.ViewPortfolioListener());
		buySharesButton.addActionListener(new Controller.BuySharesListener());
		sellSharesButton.addActionListener(new Controller.SellSharesListener());
		timerChangeButton.addActionListener(new Controller.NextTurnListener());
		displaySharesQuotationsHistoryButton.addActionListener(new Controller.DisplaySharesQuotationHistoryListener());
		displayPortfoliosHistoricalValueButton.addActionListener(new Controller.DisplayViewPortfoliosValueHistoryListener());
		
		navigationPanel.add(myPortfolioButton);
		navigationPanel.add(buySharesButton);
		navigationPanel.add(sellSharesButton);
		navigationPanel.add(displaySharesQuotationsHistoryButton);
		navigationPanel.add(displayPortfoliosHistoricalValueButton);
		
		frame.getContentPane().add(BorderLayout.NORTH, navigationPanel);
		
		timeLabel.setText(Controller.getTime());
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		
		timePanel.add(new JLabel());
		timePanel.add(new JLabel());
		timePanel.add(new JLabel());
		timePanel.add(new JLabel());
		timePanel.add(new JLabel());
		timePanel.add(timeLabel);
		timePanel.add(timerChangeButton);
		timePanel.add(new JLabel());
		timePanel.add(new JLabel());
		timePanel.add(new JLabel());
		timePanel.add(new JLabel());
		timePanel.add(new JLabel());
		timePanel.add(new JLabel());
		
		frame.getContentPane().add(BorderLayout.SOUTH, timePanel);
		
		frame.setResizable(false);
		frame.setSize(1006, 600);
		frame.setPreferredSize(new Dimension(1006, 600));
		frame.setVisible(true);
		
		ViewPortfolios.displayViewPortfolios();
	}
	
	public static View getRecentView() 
	{
		return recentView;
	}

	public static void setRecentView(View newView) 
	{
		recentView = newView;
	}
	
	public static JFrame getFrame() 
	{
		return frame;
	}

	public static JPanel getNavigationPanel() 
	{
		return navigationPanel;
	}

	public static JPanel getMainPanel() 
	{
		return mainPanel;
	}

	public static JPanel getMainPanelsHeader() 
	{
		return mainPanelsHeader;
	}

	public static JTextField getQuotationsHistoryTextField() 
	{
		return quotationsHistoryTextField;
	}

	public static JTextField getSymbolTextField() 
	{
		return symbolTextField;
	}

	public static JTextField getAmountTextField() 
	{
		return amountTextField;
	}

	public static JTable getSharesTable() 
	{
		return sharesTable;
	}

	public static void setSharesTable(JTable sharesTable) 
	{
		GUI.sharesTable = sharesTable;
	}

	public static JScrollPane getScrollPane() 
	{
		return scrollPane;
	}

	public static void setScrollPane(JScrollPane scrollPane) 
	{
		GUI.scrollPane = scrollPane;
	}

	public static JLabel getTimeLabel() 
	{
		return timeLabel;
	}		
}

class MyButton extends JButton
{
	private static final long serialVersionUID = 1L;

	public MyButton(String text, int sizeX, int sizeY)
	{
		super(text);
		setPreferredSize(new Dimension(sizeX, sizeY));
		setSize(new Dimension(sizeX, sizeY));
		setMinimumSize(new Dimension(sizeX, sizeY));
		setMaximumSize(new Dimension(sizeX, sizeY));
	}
}
