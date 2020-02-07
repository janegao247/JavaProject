import java.io.*;
import java.util.*;


class Edge {

    public char node_1;
    public char node_2;
    
    Edge(char u, char v) {
        this.node_1 = u;
        this.node_2 = v;
    }

    @Override
    public String toString() {
        return "(" + node_1 + ", " + node_2 + ")";
    }
}

public class Graph {

    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private ArrayList<Character> nodes = new ArrayList<Character>();
    private int expectedMinCutSize;

    // copy constructor
    Graph(Graph other_graph) {
        expectedMinCutSize = other_graph.expectedMinCutSize;
        edges = new ArrayList<Edge>();
        nodes = new ArrayList<Character>();
        for (int i = 0; i < other_graph.edges.size(); ++i) {
            Edge other_edge = other_graph.edges.get(i);
            char u = other_edge.node_1;
            char v = other_edge.node_2;
            Edge edge = new Edge(u, v);
            edges.add(edge);
            add_node(u);
            add_node(v);
        }
    }

    // constructor from file. called by the tester
    Graph(String file) throws RuntimeException {
        try {
            Scanner f = new Scanner(new File(file));
            expectedMinCutSize = Integer.parseInt(f.nextLine()); // First line is the size of the min cut
            while (f.hasNext()) {
                String[] line = f.nextLine().split("\\s+");
                // Make sure there is 2 elements on the line
                if (line.length != 2) {
                    continue;
                }

                char u = line[0].charAt(0);
                char v = line[1].charAt(0);
                Edge e = new Edge(u, v);
                edges.add(e);
                add_node(e.node_1);
                add_node(e.node_2);
            }
            f.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
            System.exit(1);
        }
    }

    // contract node u into node v
    public void contractEdge(Edge edge_to_contract) {
        char u = edge_to_contract.node_1;
        char v = edge_to_contract.node_2;
        //System.out.println("before contraction: "+edges);
        //System.out.println("the egdes being contracted now is "+u+"-"+v);
        // TODO: implement me
        //remove edge uv
        edges.remove(edge_to_contract);
        //remove node u
        removeNode(u);

        //replace u with v for other edges
        ArrayList<Edge> allEdges=getEdges();
        for (int i=0;i<allEdges.size();i++){
            Edge one=allEdges.get(i);
            char s = one.node_1;
            char f = one.node_2;
            if(s==u && f==v){
                edges.remove(one);
                continue;
            }
            if(s==v && f==u){
                edges.remove(one);
                continue;
            }
            if(s==u){
                one.node_1=v;
                continue;
            }
            if(f==u){
                one.node_2=v;
                continue;
            }
        }
        //System.out.println("after contraction: "+edges);

    }

    public void removeNode(char toRemove) {
        nodes.remove((Character)toRemove);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Character> getNodes() {
        return nodes;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void setNodes(ArrayList<Character> nodes) {
        this.nodes = nodes;
    }

    public Edge getEdge(int edge_idx) {
        return edges.get(edge_idx);
    }

    public int getNbNodes() {
        return nodes.size();
    }

    public int getNbEdges() {
        return edges.size();
    }

    public String toString() {
        String out = Integer.toString(nodes.size());
        for (Edge e : edges) {
            out += "\n" + e.node_1 + " " + e.node_2;
        }
        return out;
    }

    public void add_node(char n) {
        if (!nodes.contains(n)) nodes.add(n);
    }

    public int getExpectedMinCutSize() {
        return expectedMinCutSize;
    }

    public void setExpectedMinCutSize(int expectedMinCutSize) {
        this.expectedMinCutSize = expectedMinCutSize;
    }
}
