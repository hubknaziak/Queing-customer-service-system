package lab07_pop;

import java.io.Serializable;
import java.util.List;

public class Info implements Serializable 
{
	public List<Bilet> obslugiwane;
	public int oczekujace;
	public String sprawa; // w przyszlosci do usuniecia
	
	Info()
	{
		
	}
	
	Info(List<Bilet> b, int i, String s)
	{
		this.obslugiwane = b;
		this.oczekujace = i;
		this.sprawa = s;
	}
}
