package observable;

public class SharesPublicData
{
	private String shortName;
	private int priceInGrosze;
	
	public SharesPublicData(String shortName, int priceInGrosze)
	{
		this.shortName = shortName;
		this.priceInGrosze = priceInGrosze;
	}

	public int getPriceInGrosze() 
	{
		return priceInGrosze;
	}
	
	public void setPriceInGrosze(int newPrice)
	{
		this.priceInGrosze = newPrice;
	}

	public String getShortName() 
	{
		return shortName;
	}
	
	public boolean equals(Object share)
	{
		if(((SharesPublicData)share).getShortName().equals(shortName))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int hashCode()
	{
		return this.shortName.hashCode();
	}
}
