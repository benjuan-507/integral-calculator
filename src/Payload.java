/*
 * Ben Juan bgj170000
 */


public class Payload implements Comparable<Payload>
{
	int coefficient;
	int exponent;
	
	public Payload()
	{
		coefficient = 0;
		exponent = 0;
	}
	
	public Payload(int coefficient, int exponent)
	{
		this.coefficient = coefficient;
		this.exponent = exponent;
	}
	
	public int getCoefficient()
	{
		return coefficient;
	}
	
	public int getExponent()
	{
		return exponent;
	}
	
	public void setCoefficient(int coefficient)
	{
		this.coefficient = coefficient;
	}
	
	public void setExponent(int exponent)
	{
		this.exponent = exponent;
	}

	public int compareTo(Payload o)
	{	
		if (exponent < o.getExponent())
		{
			return 1;
		}
		else if (exponent > o.getExponent())
		{
			return -1;
		}
		else
			return 0;
	}
	
	public boolean equals(Payload o)
	{
		if (exponent == o.getExponent())
		{
			return true;
		}
		else
			return false;
	}

	
	
	
	
}
