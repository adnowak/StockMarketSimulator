package observers;

import observable.SharesPublicData;

public interface Observer 
{
	public void update();
	
	public void update(SharesPublicData sharesPublicData);
}
