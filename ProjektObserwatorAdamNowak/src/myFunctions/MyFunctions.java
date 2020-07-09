package myFunctions;

public class MyFunctions {
	public static boolean isPositiveInteger(String string) {
		try {
			int i = Integer.parseInt(string);
			if(i>0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public static String amountZlotowki(long amountGrosze) {
		long zlotowki = (amountGrosze-(amountGrosze%100))/100;
		String zlotowkiString = "";
		String[] zlotowkiTab = (new Long(zlotowki).toString()).split("");

		for(int i=0; i<zlotowkiTab.length; i++) {
			zlotowkiString = zlotowkiTab[zlotowkiTab.length-1-i] + zlotowkiString;
			if((i+1)%3==0&&i!=0&&i<zlotowkiTab.length-1) {
				zlotowkiString = "."+zlotowkiString;
			}
		}
		long grosze = amountGrosze-100*zlotowki;
		return zlotowkiString + "zł "+grosze+"gr";
	}
	
	public static boolean isAlphanumerical(String string) {
		if(string.length() != 0) {
			char[] charArr = string.toCharArray();

		    for(char c : charArr) {
		        if(!(Character.isAlphabetic(c)||Character.isDigit(c))) {
		            return false;
		        }
		    }
		    return true;
		}
		return false;
	}
}
