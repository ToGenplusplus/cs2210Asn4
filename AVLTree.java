import java.util.ArrayList;
/**
 * AVLTree class implements an AVL Tree.
 * CS2210 Asn 4
 * @author Temi Owoaje
 *
 */
public class AVLTree implements AVLTreeADT{

	private int size;	// number of records in tree
	
	private AVLTreeNode root;	//root of the AVL tree
	
	public AVLTree()
	{
		this.size = 0;		//intialize size to be 0
		root = new AVLTreeNode();	//initialize root of tree 
		
	}
	

	/**
	 * Given a node, set it as the root of the AVL Tree
	 */
	public void setRoot(AVLTreeNode node) {
		
		this.root = node;
	
		
	}
	
	/**
	 * Return the root of the AVL Tree
	 */
	public AVLTreeNode root() {
		
		return this.root;
	}
	
	/**
	 * Given node, is node the root of the AVL
	 *Tree? Return true if node is the root, and return false otherwise.
	 */
	public boolean isRoot(AVLTreeNode node) {
		
		return (node.isRoot());
	}
	
	
	/**
	 * Returns the number of records in tree
	 */
	public int getSize() {

		return this.size;
	}
	
	
	/**
	 * check if subtree rooted at node is balanced
	 * @param node
	 * @return true if balanced false otherwise
	 */
	private boolean isBalanced(AVLTreeNode node) 
	{	
		boolean balanced = false;
		if(node != null )
		  balanced = Math.abs(node.getLeft().getHeight() - node.getRight().getHeight()) <= 1; 
		return balanced;
	}
	
	/**
	 *  Given the root of a binary search
	 *tree node and a key, return the node containing key as its key; otherwise return the leaf node
	 *where k should have been in the AVL Tree.
	 */
	public AVLTreeNode get(AVLTreeNode node, int key) {
		// check if node is a leaf
		
		if (node.isLeaf())
		{
			// return the node
			return node;
		}
		// if its not a leaf
		else 
		{
			if(key == node.getKey()) //check if key is equal to key store in node
			{
				return node;
			}
			else if( key < node.getKey()) // check if key is less than hey stored in node
			{
				return get(node.getLeft(), key); // iterate over left subtree if true
			}
			else
			{
				return get(node.getRight(), key); // iterate over right subtree if false
			}
		}
		
		
	}

	/**
	 * Given the root of a binary search tree
	 *node, return the node containing the smallest key; return null if the AVLTree has no data
	 *stored in it.
	 */
	public AVLTreeNode smallest(AVLTreeNode node) {
		// check if node is a leaf
			if (node.isLeaf())
			{
				// return null because there is no data
				return null;
			}
			else
			{
				AVLTreeNode p = node; // set parent = root
				while(p.isInternal() && !p.getLeft().isLeaf()) // loop while p isn't a leaf
				{
					p = p.getLeft();// check left subtree
				}
				return p; // since all leaf nodes are null, we return the parent
			}
		
	}

	/**
	 *  Given the root of a binary search tree node and a key-value pair key
	 *and data, return the node storing the the new node containing record (key, data); 
	 *throws the TreeException if a record with a duplicate key is attempted to be inserted
	 */
	public AVLTreeNode put(AVLTreeNode node, int key, int data) throws TreeException {

		AVLTreeNode newNode = get(node,key); // calls on the get function to return node if it exist
		if (newNode.isInternal()) //checks to see if node already exits in tree
		{
			throw new TreeException("Tried to insert node with duplicate key into tree");
		}
		else
		{ 
			root().setParent(null);// make sure parent of root is always null
			newNode.setKey(key);
			newNode.setData(data);
			AVLTreeNode leaf1 = new AVLTreeNode(newNode);	//creates leaves and sets them as children of newnode
			AVLTreeNode leaf2 = new AVLTreeNode(newNode);
			newNode.setLeft(leaf1);
			newNode.setRight(leaf2);
			this.size += 1;		//increment number of records
			//return newNode;
			return newNode;
		}
		
	}

	/**
	 * Given the root of a binary search tree node and a key, remove the
	 *record with key from the tree. The method must return the node where the removed node used to be
 	 *If there is no node storing a record with key, throw the
	 *TreeException
	 */
	public AVLTreeNode remove(AVLTreeNode node, int key) throws TreeException {
		AVLTreeNode node_to_remove = get(node,key); // calls on the get function to return node if it exist
		
		AVLTreeNode other_child = null;
		
		if (node_to_remove.isLeaf()) //checks to see if node is not  in tree 
		{
			throw new TreeException("Node doesn't exist in tree");
		}
		
		//case 0 - node is a leaf node
		else if (node_to_remove.getLeft().isLeaf() && node_to_remove.getRight().isLeaf())
		{
			
			AVLTreeNode leaf = null;
			if(node_to_remove == node_to_remove.getParent().getLeft()) //check to see if node trying to remove left child of parent
			{
				leaf = new AVLTreeNode(node_to_remove.getParent());//create new leaf
				node_to_remove.getParent().setLeft(leaf); // set the position of where the node used to be to leaf
			}
			else
			{
				leaf = new AVLTreeNode(node_to_remove.getParent());
				node_to_remove.getParent().setRight(leaf);;
			}
			
			this.size -= 1; // decrement record in tree
			return node_to_remove; //return the node we just removed
		}
		// check if node trying to remove has at least one child thats a leaf(case 2)
		else if(node_to_remove.getLeft().isLeaf() || node_to_remove.getRight().isLeaf())
		{
			
		
			AVLTreeNode node_parent = node_to_remove.getParent(); //get parent of node_to_remove
				
			other_child  = (node_to_remove.getLeft().isLeaf() ? node_to_remove.getRight():node_to_remove.getLeft()); //other child that isn't leaf
				
				
			// if the node we are trying to remove is the root, then we set the root as other child
			if(node_to_remove.isRoot())				
			{
				setRoot(other_child);
				other_child.setParent(null);
			}
			else		
			{
				other_child.setParent(node_parent);
				
				if (other_child == node_to_remove.getRight())
				{
					node_parent.setRight(other_child);
				}
				else
				{
					node_parent.setLeft(other_child);
				}
					
			}
			this.size -= 1;
			
			}// if both children of node we are tying to remove aren't leaves
		else
		{
			
			// get the smallest node from the right subtree
			AVLTreeNode smallest_node = smallest(node_to_remove.getRight());
			
			//copy the key and data from the smallest node into node we are removing
				
			node_to_remove.setKey(smallest_node.getKey());
			node_to_remove.setData(smallest_node.getData());
				
			// remove the smallest node
			other_child = remove(smallest_node,smallest_node.getKey());
			
		}
			
		return other_child;
		}
		
	
	
	
/**
 * returns an array list with AVLTreeNode objects from an inorder traversal
 */
	public ArrayList<AVLTreeNode> inorder(AVLTreeNode node) {
		ArrayList<AVLTreeNode> nodes_in_list = new ArrayList<AVLTreeNode>();
		// TODO Auto-generated method stub
		if(!node.isLeaf())
		{
			inorderRec(node,nodes_in_list);
		}
		return nodes_in_list;
	}
	
	

	public void inorderRec(AVLTreeNode node, ArrayList<AVLTreeNode> list) {
		// TODO Auto-generated method stub
		if(!node.getLeft().isLeaf())
		{
			inorderRec(node.getLeft(),list);
		}
		list.add(node);
		
		if(!node.getRight().isLeaf())
		{
			inorderRec(node.getRight(),list);
		}
	
		
	}


	
	
//METHODS SPECIFICALLY FOR AVL Tree
//-------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------	
	/**
	 * Recomputes the height of node.
	 */
	public void recomputeHeight(AVLTreeNode node) {
		// assumes node is internal
		if(node.isInternal()) {
			node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight()));
		}
			
		
	}

	/**
	 *  This method re-balances
	 *the tree and updates the heights of nodes as the method moves 
	 *up to the root of the tree
	 *Takes as input root of tree r and node where imbalance might have occurred v
	 */
	public void rebalanceAVL(AVLTreeNode r, AVLTreeNode v) {
		// if r is not a leaf then we recompute height of v
		if(! r.isLeaf())
		{
			recomputeHeight(v);
		}
		// do while v does not equal root
		while( v != r)
		{
			
				v= v.getParent();
				
				if(!isBalanced(v)) // if subtree is not balanced
				{
					AVLTreeNode y = taller(v); // get taller child of v
					
					AVLTreeNode x = taller(y);	// get taller child of y
						
					v = rotation(v,y,x); // perform the necessary rotation
						
				}
			
				recomputeHeight(v); // recompute the height
		}
		root().setParent(null);// make sure new root is root by setting its parent to null everytime
								//the function is called
		
	}
	
	/**
	 * inserts a given node with key and data into tree and rebalances AVLTree if needed
	 */
	public void putAVL(AVLTreeNode node, int key, int data) throws TreeException {
		
		
		AVLTreeNode inserted_node = put(node,key,data);/// insert new node with key and data into tree  get the new node
		
		rebalanceAVL(node,inserted_node); // re-balance tree if needed
	}

	/**
	 * deletes a node from AVLTree and rebalances tree if needed
	 */
	public void removeAVL(AVLTreeNode node, int key) throws TreeException {
		
		AVLTreeNode node_replaced = remove(node, key); // get the node that replaced deleted node
		
		rebalanceAVL(node,node_replaced); // re-balance tree if needed
		
		
	}
	
	

	
	
//SUGGESTED METHODS
//-----------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------

	/**
	 * 
	 * @param node
	 * @return taller child of node
	 */
	public AVLTreeNode taller(AVLTreeNode node)
	{
		
		AVLTreeNode taller_node = null;
		
		
		{
				//get the left and right children of node
				AVLTreeNode l = node.getLeft();
				AVLTreeNode r = node.getRight();
				// if their heights equal check to see which child node was to its parent
				if(l.getHeight() == r.getHeight())
				{
					if(node == node.getParent().getLeft())
					{
						taller_node = l;
					}
					else
					{
						taller_node = r;
					}
				}
				//check to see if left height is greater than right
				else if(l.getHeight() > r.getHeight())
				{
					taller_node = l;
				}
				else
				{
					taller_node = r;
				}
			
		}
		return taller_node;
	}
	
	// right right case single rotation
	private AVLTreeNode rr(AVLTreeNode node)
	{
		AVLTreeNode old = node.getParent();
		AVLTreeNode t2 = node.getLeft();
		AVLTreeNode t3 = old.getLeft();
		
		//perform rotation
		node.setLeft(old);
		old.setRight(t2);
		
		
		
		if(old.isRoot())
		{
		
			setRoot(node);
			
			old.setParent(root());
			
			
	
		}
		
		else	// not working with root
		{
			if(old == old.getParent().getRight())	// check to see if old was the right child of its parent
			{
				old.getParent().setRight(node);		////set right child of old  to node
				
			}
			else
			{
				old.getParent().setLeft(node);
			}
			node.setParent(old.getParent());
			
			t3.getParent().setParent(node); //make sure new root can access the left subtree of node
		}
		
		
		return node;
	}
	
	//left left  case single rotation
	private AVLTreeNode ll(AVLTreeNode node)
	{
		AVLTreeNode old = node.getParent();
		AVLTreeNode t2 = node.getRight();
		AVLTreeNode t3 = old.getRight();
		
		//perform rotation
		node.setRight(old);
		old.setLeft(t2);
		
		//check to see if nodes parent was the root
		if(old.isRoot())
		{
			
			setRoot(node);
			
			old.setParent(root());// set the old to new root, so doesnt get lost
			
		
		}
		
		else // not working with root
		{
			if(old == old.getParent().getRight())// check to see if old was the right child of its parent
			{
				old.getParent().setRight(node); //set right child of old  to node
			}
			else
			{
				old.getParent().setLeft(node);
			}
			node.setParent(old.getParent());	// let nodes parent be olds parent
			t3.getParent().setParent(node);		// make sure new root is can access the right subtree of old
		}
		
		
		return node;
	}
	
	/**
	 * Simple rotation, left or right.
	 * @param node
	 * @return new root of subtree
	 */
	public AVLTreeNode rotate(AVLTreeNode node)
	{
		AVLTreeNode result = null;
		if( node == node.getParent().getLeft())//right rotation
		{
			result = ll(node); //call left left case method
		}
		else //left rotation
		{
			result = rr(node); //right right case method
		}
		
		//recompute the heights of the internal children
		if(result.getLeft().isInternal() && result.getRight().isInternal())
		{
			
			recomputeHeight(result.getLeft());
			recomputeHeight(result.getRight());
		}
		return result; // return root of resulting subtree
	}

	
	
	/**
	 * 
	 * @param z - imbalance occurs
	 * @param y - y taller of two children of z
	 * @param x - taller of two children of y
	 * @return
	 */
	public AVLTreeNode rotation(AVLTreeNode z, AVLTreeNode y, AVLTreeNode x)
	{
		AVLTreeNode root_rotate = null;
		y = taller(z); //get taler child of z
		
		x = taller(y);	//get taller child of y
		
		//check for rr case or rl case
		if(y == z.getRight())
		{
			if(x == y.getRight())
			{
				root_rotate = rotate(y);
			}
			else
			{
				root_rotate = rotate(x);
				
				root_rotate = rotate(root_rotate);
			}
		}
		else //check for ll case or lr case.
		{
			if(x == y.getLeft())
			{
				root_rotate = rotate(y);
			}
			else
			{
				root_rotate = rotate(x);
				
				root_rotate = rotate(x);
			}
		}
		return root_rotate;
		
	}
	
	
	

}
