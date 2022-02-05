/*
 * Ben Juan bgj170000
 */

public class Node<E extends Comparable<E>> // implements Comparable<Node<E>>
{
	E element;
	Node<E> right;
	Node<E> left;
	
	public Node()
	{
		element = null;
		right = null;
		left = null;
	}
	
	public Node(E element)
	{
		this.element = element;
	}
	
	public void setElement(E element)
	{
		this.element = element;
	}
	
	public void setLeft(Node<E> left)
	{
		this.left = left;
	}
	
	public void setRight(Node<E> right)
	{
		this.right = right;
	}
	
	public Node<E> getRight()
	{
		return right;
	}
	
	public Node<E> getLeft()
	{
		return left;
	}
	
	public E getElement()
	{
		return element;
	}
	
	public int compareTo(Node<E> o) 
	{
		return element.compareTo(o.getElement());
	}

	
}
