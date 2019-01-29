package GUI;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import controller.Controller;
import observable.Share;

public class ViewDisplaySharesQuotationHistory extends View
{
	private static GraphPanel graphPanel = new GraphPanel(1000, 375, new ArrayList<Integer>());
	private static Share recentlyDisplayedShare = null;
	
	public static Share getRecentlyDisplayedShare() 
	{
		return recentlyDisplayedShare;
	}

	public static void setRecentlyDisplayedShare(Share recentlyDisplayedShare) 
	{
		ViewDisplaySharesQuotationHistory.recentlyDisplayedShare = recentlyDisplayedShare;
	}

	public static GraphPanel getGraphPanel() 
	{
		return graphPanel;
	}

	public static void setGraphPanel(GraphPanel graphP) 
	{
		graphPanel = graphP;
	}

	
	
	public static void displayViewSharesQuotationHistory()
	{
		GUI.getMainPanel().removeAll();
		GUI.getMainPanelsHeader().removeAll();
		GUI.getScrollPane().removeAll();
		GUI.getSharesTable().removeAll();
		GUI.getMainPanelsHeader().setLayout(new BoxLayout(GUI.getMainPanelsHeader(), BoxLayout.X_AXIS));
		
		
		JLabel label = new JLabel("Wpisz symbol akcji, której historię notowań chcesz sprawdzić   ");
		JButton chooseButton =new JButton("Wybierz");
		chooseButton.addActionListener(new Controller.ChooseButtonListener());
		
		
		if(Controller.getIsTimerOn())
		{
			GUI.getQuotationsHistoryTextField().setEditable(false);
			chooseButton.setEnabled(false);
		}
		else
		{
			GUI.getQuotationsHistoryTextField().setEditable(true);
			chooseButton.setEnabled(true);
		}
		
		GUI.getMainPanelsHeader().add(label);
		GUI.getMainPanelsHeader().add(GUI.getQuotationsHistoryTextField());
		GUI.getMainPanelsHeader().add(chooseButton);
		if(recentlyDisplayedShare!=null)
		{
			GUI.getQuotationsHistoryTextField().setText(recentlyDisplayedShare.getCompanysShortName());
		}
		GUI.getMainPanel().add(GUI.getMainPanelsHeader(), BorderLayout.NORTH);
		GUI.getMainPanel().add(graphPanel, BorderLayout.CENTER);
		GUI.getFrame().getContentPane().add(BorderLayout.CENTER, GUI.getMainPanel());
		
		GUI.getFrame().pack();
		displaySharesQoutationsHistory(recentlyDisplayedShare);
	}
	
	public static void displaySharesQoutationsHistory(Share shareToDisplay)
	{
		if(shareToDisplay==null){}
		else
		{
			ArrayList<Integer> historicalQoutations = shareToDisplay.getHistoricalQuotationsList();
			getGraphPanel().setValues(historicalQoutations);
		}
		getGraphPanel().repaint();
	}

	@Override
	public void refresh() 
	{
		super.refresh();
		displayViewSharesQuotationHistory();
	}
}
