public class BellmanFord{

	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *  
         *  When throwing an exception, choose an appropriate one from the ones given above
         */
        
        /* YOUR CODE GOES HERE */
       // d to store distance estimate
        int[] d =new int[g.getNbNodes()];
        int[] p =new int[g.getNbNodes()];

        d[source]=0;//distance to source=0
        p[source]=-1;
        for(int i=1;i<g.getNbNodes();i++) {
            d[i] = Integer.MAX_VALUE;
            //System.out.println("initial distance   "+d[i]);
        }


        int u=0;int v=0;int w=0;
//        for(Edge ff:edges){
//            System.out.println("edges from   "+ff.nodes[0]+"  to  "+ff.nodes[1]);
//
//            }
//        int du=Integer.MAX_VALUE;
//        int dv=Integer.MAX_VALUE;
///////////////////////////////////
        for(int i=0;i<g.getNbNodes();i++) {
            for (int j = 0; j < g.getNbNodes() ; j++) {
                if (g.getEdge(i, j) == null) {
                    continue;
                }

                u = d[i];
               // System.out.println("u    is--- " + i + "    distance now is " + u);
                if(u==Integer.MAX_VALUE){
                   // System.out.println(">>>>>>>skip this edge");

                    continue;
                }
                v = d[j];
                // d end node of the edge
               // System.out.println("v    is--- " + j + "    distance now is " + v);
                w = g.getEdge(i,j).weight;
                //System.out.println("weight    is--- " + w);
                if(u+w<v){

                    d[j]=u+w;
                    p[j]=i;
                   // System.out.println("predecessor of node   "+j+"  is--- "+i);
                   // System.out.println("distance of  node   "+j+"  is--- "+ d[j]);
                    continue;
                }
                //detect cycle if sum of distance of cycle is negative throw exception
//                if(v>u+w){
//                    throw new NegativeWeightException();
//
//                }
            }
        }
        for(int i=0;i<g.getNbNodes();i++) {
            for (int j = 0; j < g.getNbNodes() ; j++) {
                if (g.getEdge(i, j) == null) {
                    continue;
                }
                u = d[i];
                //System.out.println("u    is--- " + i + "    distance now is " + u);
                if(u==Integer.MAX_VALUE){
                   // System.out.println(">>>>>>>skip this edge");

                    continue;
                }
                v = d[j];
                // d end node of the edge
                //System.out.println("v    is--- " + j + "    distance now is " + v);
                w = g.getEdge(i,j).weight;
                //System.out.println("weight    is--- " + w);
                if(v>u+w){
                    throw new NegativeWeightException();

                }
            }
        }
 //////////////////////////////////////
//            for(Edge e:g.getEdges()){
//
//                u=d[e.nodes[0]];// d start node of the edge
//                System.out.println("u    is--- "+e.nodes[0]+"    distance now is "+u);
//
//                v=d[e.nodes[1]];// d end node of the edge
//                System.out.println("v    is--- "+e.nodes[1]+"    distance now is "+v);
//                w=e.weight;
//                System.out.println("weight    is--- "+w);
//
//                if(u+w<v){
//                    d[e.nodes[1]]=u+w;
//                    p[e.nodes[1]]=e.nodes[0];
//                    System.out.println("predecessor of node   "+e.nodes[1]+"  is--- "+p[e.nodes[1]]);
//                    System.out.println("distance of  node   "+e.nodes[1]+"  is--- "+ d[e.nodes[1]]);
//                    continue;
//                }
//                if(v>u+w){
//                    throw new NegativeWeightException();
//
//                }
//                //System.out.println("new distance of node   "+e.nodes[0]+ "--"+e.nodes[1]+"  is--- "+d[e.nodes[1]]);
//
//            }

            distances=d;
            predecessors=p;
            predecessors[0]=-1;
//        for(int j=0;j<d.length;j++){
//            System.out.println("shortest distance for node "+j+"  is   "+distances[j]);
//            System.out.println("predecessor for node "+j+"  is   "+predecessors[j]);
//
//            }
        }


    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given 
         */

        int count=1;int index=destination;int dex=0;
        if(source==destination){
            return new int [source];
        }

       // if((distances[destination]<Integer.MAX_VALUE) && (distances[destination]>0)){
            if((distances[destination]<Integer.MAX_VALUE) ){
            while(predecessors[index]!=-1){
                count++;
                index=predecessors[index];
            }
            int[] path=new int[count];
            dex=destination;

            for(int j=count-1;j>=0;j--){
                path[j]=dex;
                if(dex!=source){
                    dex=predecessors[dex];
                }
            }return path;
        }else{
            throw new PathDoesNotExistException();
        }

    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   } 
}
