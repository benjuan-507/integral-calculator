/*
 * Ben Juan bgj170000
 */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Main {
	
	public static void main(String [] args) throws FileNotFoundException
	{
		String equation;
		int lowerBound, upperBound;
		
		
		Scanner s = new Scanner(System.in);
		String fileName = s.nextLine();
		File inputFile = new File(fileName);
		Scanner sc = new Scanner(inputFile);
		
		String integral;

		if(inputFile.exists())
		{
			while(sc.hasNextLine())
			{
				BinTree<Payload> tree = new BinTree<Payload>();
				equation = sc.nextLine();
				addToTree(tree, equation);
				lowerBound = lowerBound(equation);
				upperBound = upperBound(equation);
				integral = displayIntegral(tree,lowerBound,upperBound);
				
				System.out.println(integral);						
			}
		}
		sc.close();
		s.close();
	}
	
	public static void addToTree(BinTree<Payload> tree, String integral)
	{
		int coefficient, exponent;
		//Temp is to hold in the string version of coefficient and exponent
		String temp;
		
		//This removes the integral and bounds of the equation and 'dx'
		int index = getEquation(integral);
		String equation = integral.substring(index, integral.indexOf('d'));
		equation = equation.replaceAll("\\s", "");
		
		//Pattern helps format out the equation and pulls out the terms as either
		// a, ax^b, or ax form where a and b are numbers
		Pattern p = Pattern.compile("[+-]?\\d*x\\^-?\\d*|[+-]?\\d*x|[x-]?\\d*");
		Matcher m = p.matcher(equation);
		
		while(m.find())
		{
			String s = m.group();
			
			if(s.length() != 0)
			{
				//Checks if the String is a term or a constant
				if(s.contains("x"))
				{
					//Grabs the coefficient
					temp = s.substring(0,s.indexOf('x'));
					//If there is no number, that means the coefficient is 1
					if (temp.equals("") || temp.equals("-") || temp.equals("+"))
					{
						if(temp.equals("-"))
						{
							coefficient = -1;
						}
						else
						{
							coefficient = 1;
						}
					}
					//Set the coefficient to 1
					else
					{	
						coefficient = Integer.valueOf(temp);
					}	
					//Checks if there is "^" in the String
					//If there is not, the exponent is 1
					if(s.contains("^"))
					{
						temp = s.substring(s.indexOf('^')+1);
						exponent = Integer.valueOf(temp);
					}
					else
					{
						exponent = 1;
					}
					
				}
				//If it is a constant, set the coefficient to the constant and exponent to 0
				else
				{
					coefficient = Integer.valueOf(s);
					exponent = 0;
				}
				
				Payload payload = new Payload(coefficient,exponent);
				Payload search = tree.search(payload);
				
				//Checks if there are the same exponents to combine like terms
				if(search != null && search.equals(payload))
				{					
					search.setCoefficient(search.getCoefficient() + coefficient);
				}
				else
				{				
					tree.insert(payload);
				}
			}
		}		
	}
	
	public static String displayIntegral(BinTree<Payload> tree, int lowerBound, int upperBound)
	{
		String integral = "";
		boolean firstTerm = true;
		while(tree.getRoot() != null)
		{
			Payload max = tree.findMax();
			int tempCoefficient = max.getCoefficient();
			int tempExponent = max.getExponent();
			String newCoefficient;

			//This is to check for natrual log
			if(tempExponent == -1)
			{
				
			}
			else
			{
				tempExponent++;
				newCoefficient = asFraction(tempCoefficient,tempExponent);
				//If the coefficient is a whole number
				if(firstTerm)
				{
					if(!(newCoefficient.charAt(0) == '0'))
					{
						//This means the coefficient is a whole number
						if(newCoefficient.substring(newCoefficient.indexOf('/')).equals("/1"))
						{
							//If the coefficient is negative
							if(newCoefficient.contains("-"))
							{
								newCoefficient = newCoefficient.replaceAll("-", "");
								if(newCoefficient.equals("1/1"))
								{
									newCoefficient = "";
									integral += "-x";
								}
								else
								{
									newCoefficient = newCoefficient.substring(0,newCoefficient.indexOf('/'));
									integral += "-" + newCoefficient + "x";
								}
							}
							//If the coefficient is positive
							else
							{
								if(newCoefficient.equals("1/1"))
								{
									newCoefficient = "";
									integral += "x";
								}
								else
								{
									newCoefficient = newCoefficient.substring(0,newCoefficient.indexOf('/'));
									integral += newCoefficient + "x";
								}
							}
							
							if(tempExponent != 1 && (!(newCoefficient.equals("0"))))
							{
								integral += "^" + tempExponent;
							}
						}
						//If the coefficient is a fraction
						else
						{
							//If the coefficient is negative
							if(newCoefficient.contains("-"))
							{
								newCoefficient = newCoefficient.replaceAll("-", "");
								integral += "(-" + newCoefficient + ")x";
							}
							//If the coefficient is positive
							else
							{
								integral += "(" + newCoefficient + ")x";
							}
							
							if(tempExponent != 1)
							{
								integral += "^" + tempExponent;
							}						
						}
					}
					else
					{
						integral += "0";
					}
					integral += " ";
					firstTerm = false;
				}
				else
				{
					//This deals with negative/positive
					if(newCoefficient.contains("-"))
					{
						integral += "- ";
						newCoefficient = newCoefficient.replaceAll("-", "");
					}
					else
					{
						integral += "+ ";
					}
					//This means the coefficient is a whole number
					if(newCoefficient.substring(newCoefficient.indexOf('/')).equals("/1"))
					{
						if(newCoefficient.equals("1/1"))
						{
							newCoefficient = "";
							integral += "x";
						}
						else
						{
							newCoefficient = newCoefficient.substring(0,newCoefficient.indexOf('/'));
							integral += newCoefficient + "x";
						}
					}
					else
					{
						integral += "(" + newCoefficient + ")x";
					}
					if (tempExponent != 1)
					{
						integral += "^" + tempExponent;
					}
					integral += " ";
				}
			}	
			tree.delete(max);
		}
		//Checks if it is definite or not
		if(upperBound == lowerBound)
		{
			integral += "+ C";
		}
		return integral;
	}
	
	//This collects the lowerbound
	public static int lowerBound(String equation)
	{
		String temp = equation.substring(0,equation.indexOf('|'));
		int lowerBound = 0;
		//If there is a lower bound
		if(!(temp.equals("")))
		{
			lowerBound = Integer.valueOf(temp);
		}
		return lowerBound;
		
	}
	
	//This collects the upperbound
	public static int upperBound(String equation)
	{
		String temp = equation.substring(equation.indexOf('|')+1,equation.indexOf(' '));
		int upperBound = 0;
		//If there is an upper bound
		if(!(temp.equals("")))
		{
			upperBound = Integer.valueOf(temp);
		}
		return upperBound;
	}
	
	public static int getEquation(String integral)
	{
		int index = integral.indexOf('|');
		int spaceIndex = integral.indexOf(' ', index);
		
		return spaceIndex+1;
	}
	
	//GCM finds the greatest common multiple between a and b
	public static int gcm(int a, int b) 
	{
		//If b is 0 return a, else return gcm
	    return b == 0 ? a : gcm(b, a % b);
	}

	public static String asFraction(int a, int b) 
	{
	    int gcm = gcm(a, b);
	    return (a / gcm) + "/" + (b / gcm);
	}
}
