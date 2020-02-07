import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GlobalMinCut {

    static Random random;

    /**
     * One run of the contraction algorithm
     * Returns the min cut found
     *
     * @param   graph - the graph to find min cut of
     * @return  two ArrayLists of characters (placed together in another ArrayList)
     *          representing the partitions of the cut
     */
    public static ArrayList<ArrayList<Character>> global_min_cut(Graph graph) {
        ArrayList<ArrayList<Character>> cut = new ArrayList<ArrayList<Character>>();

        // For each node v, we will record
        // the list S(v) of nodes that have been contracted into v
        Map<Character, ArrayList<Character>> S = new HashMap<Character, ArrayList<Character>>();

        // TODO: Initialize S(v) = {v} for each v

        ArrayList<Edge> allEdges=graph.getEdges();
        ArrayList<Character> allNodes=graph.getNodes();

        for (int i=0;i<allNodes.size();i++) {
            ArrayList<Character> nodesSet=new ArrayList<Character>();
            nodesSet.add(allNodes.get(i));
            S.put(allNodes.get(i),nodesSet);

        }
        char u='\0';
        char v='\0';
        while (graph.getNbNodes() > 2) {

            // select an edge randomly (DO NOT CHANGE THIS LINE)
            Edge edge = graph.getEdge(random.nextInt(graph.getNbEdges()));
           
            u = edge.node_1;
            v = edge.node_2;
            //System.out.println("the edges generated is "+u+"-"+v);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //if there's no edge between uv, then do nothing
            for(int j=0;j<allEdges.size();j++) {

                    ArrayList<Character> nodesU=S.get(u);
                    ArrayList<Character> copyU=nodesU;
                    ArrayList<Character> nodesV=S.get(v);
                    graph.contractEdge(edge);

                   // System.out.println("u    ??????????????"+u+"  "+nodesU);
                    //System.out.println("nodes U is empty???"+ nodesU.isEmpty());
                    if(copyU!=null ){
                        for(int z=0;z< copyU.size();z++) {
                            if(nodesU.get(z)!=null){
                                nodesV.add(copyU.get(z));

                        }}
                        S.remove(u);
                        S.remove(v);
                        S.put(v,nodesV);

                        continue;

                }else{
                    continue;
                }
            }
        }


        // TODO: assemble the output object
        // System.out.println("edge now is only "+allEdges);

        u=(char) S.keySet().toArray()[0];
        v=(char) S.keySet().toArray()[1];
        //u=allEdges.get(0).node_1;
        //v=allEdges.get(0).node_2;
        cut.add(S.get(u) );
        cut.add(S.get(v) );
//        System.out.println("--------------end of while ");
//        System.out.println("u is and v is "+"   "+u+"~~~"+v);
//
//        System.out.println("s is "+"    "+S+S.get(u)+"///"+S.get(v));
//        System.out.println("CUT is "+cut);
//        System.out.println("--------------end of global min cut ");
        return cut;
    }


    /**
     * repeatedly calls global_min_cut until finds min cut or exceeds the max allowed number of iterations
     *
     * @param graph         the graph to find min cut of
     * @param r             a Random object, don't worry about this, we only use it for grading so we can use seeds
     * @param maxIterations some large number of iterations, you expect the algorithm to have found the min cut
     *                      before then (with high probability), used as a sanity check / to avoid infinite loop
     * @return              two lists of nodes representing the cut
     */
    public static ArrayList<ArrayList<Character>> global_min_cut_repeated(Graph graph, Random r, int maxIterations) {
        random = (r != null) ? r : new Random();

        ArrayList<ArrayList<Character>> cut = new ArrayList<ArrayList<Character>>();
        //cut = global_min_cut(graph);
        //Graph copy=new Graph(graph);
        int count = 0;
        do  {
            while(count<maxIterations) {
                // TODO: use global_min_cut()
                Graph copy=new Graph(graph);
                cut = global_min_cut(copy);

                ++count;
                if(get_cut_size(graph, cut) <=graph.getExpectedMinCutSize() ){
                    return cut;
                }
            }
            //if (get_cut_size(graph, cut) > min_cut_size)
            //    System.out.println("Cut has " + get_cut_size(graph, cut) + " edges but the min cut should have " +
            //      graph.getExpectedMinCutSize() + ", restarting");

        } while (get_cut_size(graph, cut) > graph.getExpectedMinCutSize() && count < maxIterations);

        if (count > maxIterations) System.out.println("Forced stop after " + maxIterations + " iterations... did something go wrong?");
        return cut;

    }

    /**
     * @param graph  the original unchanged graph
     * @param cut    the partition of graph into two lists of nodes
     * @return       the number of edges between the partitions
     */
    public static int get_cut_size(Graph graph, ArrayList<ArrayList<Character>> cut) {
        ArrayList<Edge> edges = graph.getEdges();
        int cut_size = 0;
        for (int i = 0; i < edges.size(); ++i) {
            Edge edge = edges.get(i);
            if (cut.get(0).contains(edge.node_1) && cut.get(1).contains(edge.node_2) ||
                    cut.get(0).contains(edge.node_2) && cut.get(1).contains(edge.node_1)) {
                ++cut_size;
            }
        }
        return cut_size;
    }
}
