package GUI;

import controller.Controller;

public class View 
{
	public void refresh()
	{
		Controller.setTimeLabelText(Controller.getTime());
	}
}
