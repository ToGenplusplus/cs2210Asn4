
import java.util.Iterator;
import java.util.Stack;

/**
 * class implements depth-first search algorithm that finds a path 
 * given a starting vertex and end vertex
 * CS2210 ASN4
 * @author Temi Owoaje
 *
 */
public class DepthFirstSearch {

	private RouteGraph inputGraph;	//used to get graph depth first search will work on
	
	private Stack<Intersection> stack; //used to store path found by algorithm
	
	public DepthFirstSearch(RouteGraph graph)
	{
		this.inputGraph = graph;
	}
		
	/**
	 * resets stack and calls pathRec method
	 * @param startVertex
	 * @param endVertex
	 * @returns a stack
	 * @throws GraphException
	 */
	public Stack<Intersection> path(Intersection startVertex, Intersection endVertex) throws GraphException
	{
		stack = new Stack<Intersection>();
		if(stack.isEmpty())
		{
			pathRec(startVertex,endVertex);
		}
		return stack;
		
	}
	
	/**
	 * computes a path via depth-first Search.
	 * @param startVertex
	 * @param endVertex
	 * @return
	 */
	public boolean pathRec(Intersection startVertex, Intersection endVertex)
	{
		boolean path = false;
		//Mark the starting vertex
		startVertex.setMark(true);
		
		stack.push(startVertex);
		
		if(startVertex == endVertex) path = true;//reached the end of path
		else
		{
			while(stack.isEmpty())	// keep iterating until we have found the end(when stack is empty
			{
			Iterator<Road> edges;
			
			try {
				edges = inputGraph.incidentRoads(startVertex); //get all roads incident on start vertex(u)
				while(edges.hasNext())//iterate over all raods
				{
					if(edges.next().getSecondEndpoint().getMark() == false)// the other endpoint of road isnt marked
																			// and we have reached our end vertex, set path to true
					{
						if(pathRec(edges.next().getSecondEndpoint(),endVertex) == true)
							path = true;
					}
				}  
				stack.pop();// if not pop
				path = false;// we havent reached end vertex
			} catch (GraphException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		}
		
		return path;
	}
	
	
}
