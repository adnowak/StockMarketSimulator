package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.*;
import javax.swing.*;

public class GraphPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
    private ArrayList<Integer> values;
    private Color lineColor = new Color(44, 102, 230, 180);
    
	public GraphPanel(int width,int height, ArrayList<Integer> values)
	{
		this.setPreferredSize(new Dimension(width, height));
		this.setValues(values);
		this.add(new JLabel("Brak danych do wyświetlenia"), BorderLayout.NORTH);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(!values.isEmpty())
		{
			this.removeAll();
			Graphics2D g2 = (Graphics2D) g;
	        
	        double scaleX = ((double) getWidth()) / (values.size() - 1);
	        double scaleY = ((double) getHeight()) / getMaxValue();
	        
	        ArrayList<Point> graphsPoints = new ArrayList<Point>();
	        
	        if(values.size()==1)
	        {
	        	for (int i = 0; i < values.size()+1; i++) 
	        	{
	        		int x1 = (int) (i * getWidth());
	        		int y1 = (int) (scaleY);
	        		graphsPoints.add(new Point(x1, y1));
	        	}
	        }
	        else
	        {
	        	for (int i = 0; i < values.size(); i++) 
	        	{
	        		int x1 = (int) (i * scaleX);
	        		int y1 = (int) ((getMaxValue() - values.get(i)) * scaleY);
	        		graphsPoints.add(new Point(x1, y1));
	        	}
	        }
	        
	        g2.setColor(lineColor);
	        
	        for (int i = 0; i < graphsPoints.size() - 1; i++) 
	        {
	            int x1 = graphsPoints.get(i).x;
	            int y1 = graphsPoints.get(i).y;
	            int x2 = graphsPoints.get(i + 1).x;
	            int y2 = graphsPoints.get(i + 1).y;
	            g2.drawLine(x1, y1, x2, y2);
	        }
		}
	}
	
	private int getMaxValue()
	{
		Integer max = 0;
		for(Integer a : this.values)
		{
			if(a>max)
			{
				max = a;
			}
		}
		return max;
	}

	public void setValues(ArrayList<Integer> values) 
	{
		this.values = values;
	}
}
