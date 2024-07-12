package Spiel2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import Spiel2.Spiel2.Level;

// Graph class 
  public class Graph implements List{ 
    private ArrayList<Node> nodeList;
    private static ArrayList<String> nodes;

/**
	 * @return the nodes
	 */
	public static ArrayList<String> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(ArrayList<String> nodes) {
		Graph.nodes = nodes;
	}

/**
	 * @return the nodeList
	 */
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	/**
	 * @param nodeList the nodeList to set
	 */
	public void setNodeList(ArrayList<Node> nodeList) {
		this.nodeList = nodeList;
	}


    public Graph(Level level){
      nodeList = new ArrayList<Node>();
//      edgeList = new HashMap<Node, ArrayList<Node>>();
      createGraph(level);
//      System.out.println(printNodeList());
      
    }

    public void createGraph(Level level){
    	//10 types of fish
      String[] arr = {"Alaska","Moorish", "Seahorse", "Bluefish", "Horn Shark", "Goldfish", "Dragonet", "Tang", "Catfish", "Rabbitfish"};
      nodes = new ArrayList<String>();
   
      nodes.addAll(Arrays.asList(arr));
      int no_of_nodes = level.getNodeNo();
      Random ran = new Random();

      while(nodeList.size() < no_of_nodes) {
          int i = ran.nextInt(nodes.size());
          Node node = new Node(nodes.get(i));
          boolean add = true;
          for(Node e: nodeList){
            if(node.getName() == e.getName()){
              add = false;
              break;
            }
          }
          if(add){
            nodeList.add(node);
          }
        }
       int conflicts = level.getConflictsNo();
//       System.out.println("conflicts = " + conflicts);

      while(conflicts>0){
        int j = ran.nextInt(no_of_nodes);
        Node node = nodeList.get(j);
        
        int k = ran.nextInt(no_of_nodes);
        Node conflictingNode = nodeList.get(k);
        if(node.getAvailNeighbor().get(conflictingNode.getName())==1) {
        	node.addNeighbor(conflictingNode);
        	conflicts-=1;
        	node.getAvailNeighbor().replace(conflictingNode.getName(), 0);
        	conflictingNode.getAvailNeighbor().replace(node.getName(), 0);
        }
 
//        boolean add = true;
//        if( node.getName() == conflictingNode.getName()){
//            add = false;
//          }
//        else {    	
//        	for(Node e: node.getAdj_list()){
//        		if(conflictingNode.getName() == e.getName()){
//        			add = false;
//        			break;
//        		}
//        	}
//        }
//        if(add){
//          node.addNeighbor(conflictingNode);
//          conflicts-=1;
//        } 
      }     
//      for (Node node: nodeList) {
//    	  edgeList.putIfAbsent(node,node.getAdj_list());	  
//      }
      
    }

    public String printNodeList(){
//      System.out.println("This graph contains: ");
      String s = "";
      for(Node node : nodeList){
        s = s.concat(node.getName() + ", ");
      }
	return s;
    }

    @Override
    public int size() {
      // TODO Auto-generated method stub
      return nodeList.size();
    }

    @Override
    public boolean isEmpty() {
      // TODO Auto-generated method stub
      return nodeList.size() > 0;
    }

    @Override
    public boolean contains(Object o) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public Iterator iterator() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Object[] toArray() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Object[] toArray(Object[] a) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public boolean add(Object e) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public boolean remove(Object o) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public boolean containsAll(Collection c) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public boolean addAll(Collection c) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public boolean removeAll(Collection c) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public boolean retainAll(Collection c) {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public void clear() {
      // TODO Auto-generated method stub
      
    }

    @Override
    public Object get(int index) {
      // TODO Auto-generated method stub
      return nodeList.get(index);
    }

    @Override
    public Object set(int index, Object element) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void add(int index, Object element) {
      // TODO Auto-generated method stub
      nodeList.add(index,(Node) element);
    }

    @Override
    public Object remove(int index) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public int indexOf(Object o) {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public ListIterator listIterator() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public ListIterator listIterator(int index) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
      // TODO Auto-generated method stub
      return null;
    }

}

