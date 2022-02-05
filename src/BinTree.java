/*
 * Ben Juan bgj170000
 */

public class BinTree <E extends Comparable<E>>
{
	private Node<E> root;
	
	public BinTree()
	{
		root = null;
	}
	
	public BinTree(Node<E> root)
	{
		this.root = root;
	}
	
	public Node<E> getRoot()
	{
		return root;
	}
	
	public E search(E payload)
	{
		if(root == null)
		{
			return null;
		}
		return search(root, payload);
	}
	
	public E search(Node<E> cur, E payload)
	{
		
		int compareResult = cur.getElement().compareTo(payload);
		//If the current node is less than the node you are searching
		if(compareResult < 0 && cur.left != null)
			return search(cur.left,payload);
		//If the current node is greater than the node you are seaching
		else if(compareResult > 0 && cur.right != null)
			return search(cur.right,payload);
		//If the current node is equal to the node you are searching for
		else if(compareResult == 0)
		{
			//Found
			return cur.getElement();
		}
		else 
			return null;
	
	}
	
	public void insert(E payload)
	{
		if( root == null) 
		{
			root = new Node<>(payload);
		}
		else
		{
			insert(root, payload);
		}
	}
	
	public void insert(Node<E> cur, E payload)
	{
		int compareResult = cur.getElement().compareTo(payload);
		//If the current node is less than the node you are inserting
		if(compareResult < 0)
		{
			if(cur.left != null)
			{
				insert(cur.left, payload);
			}
			else
			{
				// insert left
				cur.left = new Node<>(payload);
			}
		}
		//If the current node is greater than the node you are inserting
		else if(compareResult > 0)
		{
			if( cur.right != null)
			{
				insert(cur.right, payload);
			}
			else
			{
				// insert right
				cur.right = new Node<>(payload);
			}
		}
	}
	
	//Helper delete function for recursion
	public E delete(E payload)
	{
		Node<E> parent = root;
		if(root == null)
		{
			return null;
		}
		else
		{
			return delete(root, parent, payload, false);
		}
	}
	
	public E delete(Node<E> cur, Node<E> parent, E payload, boolean found)
	{
		int compareResult = cur.getElement().compareTo(payload);
		
		if (!found)
		{
			//If the node is to the left
			if(compareResult < 0 && cur.getLeft() != null)
			{
				parent = cur;
				delete(cur.getLeft(),parent,payload,false);
			}
			//If the node is to the right
			else if (compareResult > 0 && cur.getRight() != null)
			{
				parent = cur;
				delete(cur.getRight(),parent,payload,false);
			}
			//The node is found
			else if (compareResult == 0)
			{
				delete(cur,parent,payload,true);
			}
		}
		else
		{
			if(cur != null)
			{
				//If the node has 0 children
				if(cur.getLeft() == null && cur.getRight() == null)
				{
					if(parent.getLeft() == cur)
					{
						parent.setLeft(null);
					}
					else
					{
						parent.setRight(null);
					}
					
					if(cur.equals(root))
					{
						root = null;
					}
				}
				//If the node only has a right child
				else if(cur.getLeft() == null)
				{
					//If we are deleting the root
					if(cur == root)
					{
						root = root.getRight();
					}
					else
					{
						if(parent.getLeft() == cur)
						{
							parent.setLeft(cur.getRight());
						}
						else
						{
							parent.setRight(cur.getRight());
						}
						cur.setRight(null);
					}
				}
				//If the node only has a left child
				else if(cur.getRight() == null)
				{
					//If we are deleting the root
					if(cur == root)
					{
						root = root.getLeft();
					}
					else
					{
						if(parent.getRight() == cur)
						{
							parent.setRight(cur.getLeft());
						}
						else
						{
							parent.setLeft(cur.getLeft());
						}
						cur.setLeft(null);
					}
					
				}
				//If cur has 2 children
				else
				{
					parent = cur;
					cur = cur.getLeft();
					Node<E> prev = null;
					
					while(cur.getRight() != null)
					{
						prev = cur;
						cur = cur.getRight();
					}
					parent.setElement(cur.getElement());
					
					if(prev != null)
					{
						prev.setRight(cur.getLeft());
					}
					else
					{
						parent.setLeft(null);
					}
					cur.setLeft(null);
				}
			}
		}
		return cur.getElement();
	}
	
	//Helper function to find the max
	public E findMax()
	{
		return findMax(root);
	}
	
	public E findMax(Node<E> cur)
	{
		E element = cur.getElement();
		//If there is somthing to the right, then go to the right
		if(cur.getRight() != null)
		{
			return findMax(cur.getRight());
		}
		else
		{
			return element;
		}
	}
}

