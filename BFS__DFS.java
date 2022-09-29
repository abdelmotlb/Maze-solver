import java.util.*;
import java.awt.*;
import java.io.File;

interface IMazeSolver {
  
  public int[][] solveBFS(File maze);

  public int[][] solveDFS(File maze);
}

public class BFS__DFS implements IMazeSolver{

    
	/**
	 * Read the maze file, and solve it using Breadth First Search
	* @param maze maze file
	* @return the coordinates of the found path from point ’S’
	*
	to point ’E’ inclusive, or null if no path is found.
	*/
    public int[][] solveDFS(File maze){

      try{
          Scanner in = new Scanner(maze); 
      
          int rows = in.nextInt();
          int cols = in.nextInt();
          in.nextLine();
          char[][] forest = new char[rows][cols];
          for(int i = 0 ; i < rows ; i++){
              forest[i] = in.nextLine().toCharArray();
          }
          MyStack st = new MyStack();
          int[] coor = SearchStart(forest);
          boolean[][] visited = new boolean[forest.length][forest[0].length];
          st.push(coor);
          visited[coor[0]][coor[1]] = true;
          boolean R_goal = false;
          int[] temp = new int[2];
          Point[][] parents = new Point[forest.length][forest[0].length];

          while(!st.isEmpty()){
              coor = st.pop();
              visited[coor[0]][coor[1]] = true;
              if(forest[coor[0]][coor[1]] == 'E'){
              R_goal = true;
              break;
              }
              

              temp[1] = coor[1];temp[0] = coor[0] - 1;
              if(ValidUnvisit(temp, forest, visited, st)){st.push(temp); parents[temp[0]][temp[1]] = new Point(coor[0],coor[1]);}
              temp[1] = coor[1] - 1;temp[0] = coor[0];
              if(ValidUnvisit(temp, forest, visited, st)){st.push(temp); parents[temp[0]][temp[1]] = new Point(coor[0],coor[1]);}
              temp[1] = coor[1];temp[0] = coor[0] + 1;
              if(ValidUnvisit(temp, forest, visited, st)){st.push(temp); parents[temp[0]][temp[1]] = new Point(coor[0],coor[1]);}
              temp[1] = coor[1] + 1;temp[0] = coor[0];
              if(ValidUnvisit(temp, forest, visited, st)){st.push(temp); parents[temp[0]][temp[1]] = new Point(coor[0],coor[1]);}
          }
          if(R_goal){
            temp = SearchStart(forest);
            MyStack outst = new MyStack();
            int a = 0, b = 0;
            outst.push(new int[]{coor[0], coor[1]});

			//get parents
            while(parents[coor[0]][coor[1]].x != temp[0] || parents[coor[0]][coor[1]].y != temp[1]){
            outst.push(new int[]{ parents[coor[0]][coor[1]].x , parents[coor[0]][coor[1]].y });
            a = parents[coor[0]][coor[1]].x;
            b = parents[coor[0]][coor[1]].y;
            coor[0] = a;
            coor[1] = b;
            }
            outst.push(new int[]{temp[0] , temp[1] });
            int[][] result = new int[outst.size()][2];
            for(int i = 0 ; i < result.length ; i++){
              result[i] = outst.pop();
            }
            return result;
		}
      }catch(Exception e){  }
	  return null;

        
    }


    public int[][] solveBFS(File maze){

	/**
	* Read the maze file, and solve it using Depth First Search
	* @param maze maze file
	* @return the coordinates of the found path from point ’S’
	*
	to point ’E’ inclusive, or null if no path is found.
	*/

      
      try{ 
      Scanner in = new Scanner(maze); 
      
      int rows = in.nextInt();
      int cols = in.nextInt();
      in.nextLine();
      char[][] forest = new char[rows][cols];
      for(int i = 0 ; i < rows ; i++){
        forest[i] = in.nextLine().toCharArray();
      }


      MyQueue que = new MyQueue(100);
      int[] coor = SearchStart(forest);
      boolean[][] visited = new boolean[forest.length][forest[0].length];
      que.enqueue(coor);
      visited[coor[0]][coor[1]] = true;
      boolean R_goal = false;
      int[] temp = new int[2];
      Point[][] parents = new Point[forest.length][forest[0].length];

      
      while(!que.isEmpty()){
        try{  coor = que.dequeue();  }catch(Exception e){}; 
        visited[coor[0]][coor[1]] = true;
        if(forest[coor[0]][coor[1]] == 'E'){
          R_goal = true;
          break;
        }
        

        temp[1] = coor[1] + 1;temp[0] = coor[0];
        if(ValidUnvisit(temp, forest, visited, que)){que.enqueue(temp); parents[temp[0]][temp[1]] = new Point(coor[0],coor[1]);}
        
        temp[1] = coor[1];temp[0] = coor[0] + 1;
        if(ValidUnvisit(temp, forest, visited, que)){que.enqueue(temp); parents[temp[0]][temp[1]] = new Point(coor[0],coor[1]);}
        
        temp[1] = coor[1] - 1;temp[0] = coor[0];
        if(ValidUnvisit(temp, forest, visited, que)){que.enqueue(temp); parents[temp[0]][temp[1]] = new Point(coor[0],coor[1]);}

        temp[1] = coor[1];temp[0] = coor[0] - 1;
        if(ValidUnvisit(temp, forest, visited, que)){que.enqueue(temp); parents[temp[0]][temp[1]] = new Point(coor[0],coor[1]);}
        
      
      }
      if(R_goal){

        temp = SearchStart(forest);
        MyStack outst = new MyStack();
        int a = 0, b = 0;
        outst.push(new int[]{coor[0], coor[1]});

		//get parents
        while(parents[coor[0]][coor[1]].x != temp[0] || parents[coor[0]][coor[1]].y != temp[1]){
			outst.push(new int[]{ parents[coor[0]][coor[1]].x , parents[coor[0]][coor[1]].y });
			a = parents[coor[0]][coor[1]].x;
			b = parents[coor[0]][coor[1]].y;
			coor[0] = a;
			coor[1] = b;
        }

        outst.push(new int[]{temp[0] , temp[1] });
        int[][] result = new int[outst.size()][2];
        
		for(int i = 0 ; i < result.length ; i++){
          result[i] = outst.pop();
        }
		return result;
	  	}
      
      }catch(Exception e){ System.out.println("Error"); }
	  return null;
      
    } 


    public static int[] SearchStart(char[][] arr){
      int i = 0 , j = 0;
      label:
      for( i = 0 ; i < arr.length ; i++){
        for( j = 0 ; j < arr[0].length ; j++){
          if(arr[i][j] == 'S'){
            break label;
          }
        }
      }
      int[] point = {i , j};
      return point;
    }
    public static boolean VCoor(int[] coor , int rows , int cols){
      return ( 0 <= coor[0] && coor[0] < rows && 0 <= coor[1] && coor[1] < cols );
    }

    public static boolean ValidUnvisit(int[] coor, char[][] forest , boolean[][] visited , MyStack st ){
      if(VCoor(coor, forest.length, forest[0].length) && forest[coor[0]][coor[1]] != '#' && visited[coor[0]][coor[1]] == false ){
        return true;
      }
      else return false;
    }

    public static boolean ValidUnvisit(int[] coor, char[][] forest , boolean[][] visited , MyQueue que ){
      if(VCoor(coor, forest.length, forest[0].length) && forest[coor[0]][coor[1]] != '#' && visited[coor[0]][coor[1]] == false && !que.check(coor)){
        return true;
      }
      else return false;
    }
    
 
 
    public static void main(String[] args){
		
	  Scanner in = new Scanner(System.in);
	  System.out.print("please Enter the path of file that has input: ");
	  String input = in.nextLine();
      File maze = new File(input);
	  BFS__DFS obj = new BFS__DFS();
	  int[][] BFSpath =  obj.solveBFS(maze);
	  int[][] DFSpath =  obj.solveDFS(maze);
	 
	  	
      System.out.println( "BFS path:" + Arrays.deepToString( obj.solveBFS(maze) ) );
	  System.out.println( "DFS path:" + Arrays.deepToString( obj.solveDFS(maze) ) );
    }
}







/********************************implementations***************************************/

//the stack implementation


class MyStack{
    class Node {
      private int x;
      private int y;
      private Node next;
      public int[] getItem() { return new int[]{x , y}; }
      public void setItem(int[] point) { x = point[0]; y = point[1]; }
      public Node getNext() { return next; }
      public void setNext(Node next) { this.next = next; }
    }
    private Node top = null;
    private int size = 0;
    //[1]
    public int[] pop(){
      if(top == null){ System.out.println("Error pop"); return null; }
      else{
          int[] val = top.getItem();
          if(size != 1)top = top.getNext();
          else top = null;
          size--;
          return val;
      }
    }
  
    //[2]
    public void push(int[] element){
      Node newnode = new Node();
      newnode.setItem(element);
      newnode.setNext(top);
      top = newnode;
      size++;
    }
  
    //[3]
    public int[] peek(){
      if(top == null) { System.out.println("error peek"); return null; }
      else { return top.getItem(); }
    }
  
    //[4]
    public int size(){ return size; }
    public boolean isEmpty(){ return (top == null); }

    public boolean check(int[] coor){
      Node cur = top;
      boolean found = false;
      int[] arr = new int[2];
      while( cur != null){
        arr[0] = cur.x;
        arr[1] = cur.y;
        if(arr[0] == coor[0] && arr[1] == coor[1]){ found = true; break; }
        cur = cur.next;
      }
      return found;
    }
  }

  //the queue implementation


class MyQueue{
  int MaxSize;
  int S,front,rear;
  int[][] Q ;
  public MyQueue (int MaxSize){
      Q = new int[MaxSize][2];
      this.MaxSize = MaxSize; 
      this.S = 0;
      this.front = 0;
      this.rear = 0;
  } 
  public boolean isEmpty(){
      return S == 0;
  }
  public int size(){
      return S;
  }
  public void enqueue(int[] item){
      if(this.size() == this.MaxSize) System.out.println("Error");
      else{
          Q[rear][0] = item[0];
          Q[rear][1] = item[1];
          rear = (rear+1) % MaxSize;
          S++;
      }
  }
  public int[] dequeue() throws Exception{
      if( this.size() == 0 ) throw new Exception("Error");
      int[] temp = Q[front];
      front = (front+1) % MaxSize;
      S--;
      return temp;
  }

  public boolean check(int[] coor){
    boolean found = false;
    int cur = front;
    while(cur != rear){
      if( coor[0] == Q[cur][0] && coor[1] == Q[cur][1] ){ found = true; break; }
      cur++;
    }
    return found;
  }

}
