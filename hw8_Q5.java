import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

//the vertex class
class Vertex
{
    int id;//the id of the vertex
    Vertex next;
    Vertex(int id_input)
    {
        id = id_input;
        next = null;
    }
}

//the graph class
class Graph
{
    private int[] color;//the array for storing the vertex colors during BFS
    private int[] pre; // the array for storing the predecessors during BFS
    private int[] dis;// the array for storing the shortest path lengths during BFS

    public int n;//the number of vertices, the ids of the vertices are from 0 to n-1
    public Vertex[] adj;//adj[i] is the head of the adjacency list of vertex i, for i from 0 to n-1

    //class constructor, initialize the graph by getting the number of vertices from n_input
    public Graph(int n_input)
    {
        n = n_input;
        adj = new Vertex [n];
        //initialize adj[i] to null
        for(int i = 0; i < n; i++)
            adj[i] = null;

        color = new int [n];//create the array for color information
        pre = new int [n];//create the array for predecessor information
        dis = new int [n];//create the array for shortest path distance information

    }

    //build the adjacency lists from the adjacency matrix adjM
    public void setAdjLists(int[][] adjM)
    {
        for(int i = 0; i < n; i++)
        {
            //create the i-th adj list adj[i], note that it scans the vertices in the reverse order from n-1 to 0 so that it can
            //construct the adjacent list of each vertex in the increasing index order because a new vertex is always inserted to the front of a list
            for(int j = n-1; j >= 0; j--)
            {
                if(adjM[i][j] == 1)
                {
                    //create a new node and add it to the front of adj[i]
                    Vertex v = new Vertex (j);
                    v.next = adj[i];
                    adj[i] = v;
//                    System.out.println(adj[i].id);
                }
            }
        }
    }

    //print the adjacency lists of the graph
    public void printAdjLists()
    {
        for(int i = 0; i < n; i++)
        {
            System.out.print("Adj list of vertex " + i + ": ");
            Vertex v = adj[i];
            while(v != null)
            {
                if (v.next != null)
                    System.out.print(v.id + "->");
                else
                    System.out.print(v.id);
                v = v.next;
            }
            System.out.println();
        }
        System.out.println();
    }

    public void BFS(int startingIndex){
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(startingIndex);

        for(int i = 0; i < n; i++){
            color[i] = -1;
        }

        color[0] = 1;
        System.out.print(startingIndex + " ");

        while(!queue.isEmpty()){
            int temp = queue.poll();
            color[temp] = 1;
            Vertex current = adj[temp];

            while(current.next != null){
                while(current.next != null && color[current.id] == -1){
                    color[current.id] = 0;
                    dis[current.id] += 1;
                    pre[current.id] = temp;
                    queue.add(current.id);
                    System.out.print(current.id + " ");
                }
                current = current.next;
                if(color[current.id] == -1){
                    color[current.id] = 0;
                    dis[current.id] += 1;
                    pre[current.id] = temp;
                    queue.add(current.id);
                    System.out.print(current.id + " ");
                }
            }
        }
        System.out.println();
    }

    public void printSP(int startingIndex, int endIndex){
        System.out.print("A shortest path from vertex " + startingIndex + " to vertex " + endIndex + " is:");
        int pointer = endIndex;
        int[] path = new int[n];
        int i = 0;
        while(pointer != startingIndex){
            path[i] = pointer;
            pointer = pre[pointer];
            i++;
        }
        for(int j = i; j > 0; j--){
            System.out.print(path[j] +"->");
        }
        System.out.println(endIndex);

    }

    //the following two functions are for the BFS traversal, as we discussed in class, but you may choose to use different ways
    //public void BFS(int id) { }//BFS traversal, id is the source vertex
    //public void BFSVisit(int id) { }//the function that actually does BFS traversal on a particular connected component from vertex id

    //the following is the function I would use to print shortest path from the source to v, as discussed in class, again you may use different ways
    //public void printSP(int source, int v)   {}
}

public class hw8_Q5 {

    public static void main(String[] args) throws IOException {
        //open files
        String inputFile = "hw8_Q5_input.txt"; // the name of the input file
        File myFile = new File(inputFile);
        Scanner input = new Scanner(myFile);

        //read the number in the first line, which is the number of vertices of the input graph
        int n;//the number of vertices of the graph
        n = input.nextInt();

        //System.out.println("The number of vertices is: " + n);

        //Next we read the adjacency matrix from the file to an two-dimensional array M
        int[][] M = new int[n][n];
        int i = 0;//row index for M
        int j = 0;//column index for M
        int value;
        while(input.hasNext())
        {
            value = input.nextInt();
            M[i][j] = value;
            j++;
            if(j == n)//j reaches the end of a row
            {
                i++;//start a new row
                j=0;//the column index becomes zero
            }
        }
        input.close();

        //uncomment the following piece of code if you want to see the adj matrix
     	/*System.out.println("The following is the matrix:");
	    for(i = 0; i < n; i++)
	    {
	        for(j = 0; j < n; j++)
	            System.out.print(M[i][j] + " ");
            System.out.println();
	    }*/

        //initialize the graph by passing n to it
        Graph graph = new Graph(n);

        //construct the adj lists from M by using the method setAdjLists()
        graph.setAdjLists(M);


        graph.BFS(0);

        System.out.println();
        System.out.println("Here are the shortest paths from 0 to all other vertices:");
        for(int k = 1; k <n; k++){
            graph.printSP(0,k);
        }

        //uncomment the following line if you want to print the adj lists
//        graph.printAdjLists();


        //the following pieces of code is what I would use to output the information required by the assignment

        //do BFS the print the traversal order out, starting from vertex 0
//        System.out.println("The following is the BFS traversal vertex order, staring from vertex 0:");
//        graph.BFS(0);

        //the following prints shortest paths from 0 to each of the other vertices
//        System.out.println("\nHere are the shortest paths from 0 to all other vertices:");
//        for(i = 1; i < n; i++)
//        {
//            System.out.print("A shortest path from vertex 0 to vertex " + i + ": ");
//            graph.printSP(0,i);
//            System.out.println();
//        }

    }
}