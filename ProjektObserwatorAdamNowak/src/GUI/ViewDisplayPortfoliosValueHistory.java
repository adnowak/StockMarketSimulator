package GUI;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JLabel;

import controller.Controller;

public class ViewDisplayPortfoliosValueHistory extends View
{
	private static GraphPanel graphPanel = new GraphPanel(1000, 375, new ArrayList<Integer>());	
	
	public static void displayPortfoliosValueHistory()
	{
		ArrayList<Integer> historicalValue = Controller.getRecentPortfoliosHistoricValuesList();
		getGraphPanel().setValues(historicalValue);
		getGraphPanel().repaint();
	}
	
	public static GraphPanel getGraphPanel() 
	{
		return graphPanel;
	}

	public static void displayViewPortfoliosValueHistory()
	{
		GUI.getMainPanel().removeAll();
		GUI.getMainPanelsHeader().removeAll();
		
		JLabel nameLabel = new JLabel("Obecnie wybrany portfel: "+Controller.getRecentPortfoliosName());
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		
		GUI.getMainPanelsHeader().add(nameLabel);
		
		GUI.getMainPanel().add(BorderLayout.NORTH, GUI.getMainPanelsHeader());
		GUI.getMainPanel().add(graphPanel, BorderLayout.CENTER);
		GUI.getFrame().getContentPane().add(BorderLayout.CENTER, GUI.getMainPanel());
		GUI.getFrame().pack();
		displayPortfoliosValueHistory();
	}

	@Override
	public void refresh() 
	{
		super.refresh();
		displayViewPortfoliosValueHistory();
	}
}
