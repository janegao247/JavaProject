import java.io.*;
import java.util.*;


public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		
		int n=graph.getNbNodes();
		Stack.add(source);
		ArrayList<Integer> adj = new ArrayList<Integer>();

		Stack ss = new Stack();
		Integer begin=source;
		ss.push(source);

		while(!ss.isEmpty()) {
			adj.clear();
			//System.out.println("top: List have: "+Stack);
			for (int j = 0; j < n; j++) {
				if (graph.getEdge(begin, j) != null && graph.getEdge(begin, j).weight!=0 && (Stack.contains(j)==false)) {
					//System.out.println("adj "+j+" repeat? ---"+(Stack.contains(j)));
							adj.add(j);
					}
			}
			for(int i=0;i<adj.size();i++){
				ss.push(adj.get(i));
			}
//				System.out.println("adj "+begin+" are : "+adj);
//			    System.out.println("ss have: "+ss);
				Integer w=(Integer) ss.pop();
//				System.out.println("ss after pop: "+ss);
//				System.out.println("List before add new: "+Stack);
//			    System.out.println("Want to add: "+w);

			if(adj.isEmpty()) {
				Stack.remove(Stack.size()-1);
			}

				Stack.add(w);

				//System.out.println("List have: "+Stack);
				begin = w;

				if (begin==destination) {
					//System.out.println("should terminate! "+destination);
					break;
					//return Stack;
				}
			}

		int count=0;
		for(int o=0;o<Stack.size();o++) {
			if (Stack.get(o)==0) {
				count ++;
			}
		}
		if(count>1){
			return null;
		}
//		System.out.println("stack ");
//		System.out.println(Arrays.toString(Stack.toArray()));
//		System.out.println("------ ");
		return Stack;
	}
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath) {
		String answer = "";
		String myMcGillID = "260750643"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		///////////////////
		WGraph r = new WGraph();
		r = graph;
		Integer[] ogW=new Integer [graph.listOfEdgesSorted().size()] ;
		for(int i=0;i<graph.listOfEdgesSorted().size();i++){
			ogW[i]=graph.getEdges().get(i).weight;
			//System.out.println("og weight " +ogW[i]);
		}

		int minW = 0;

			while (pathDFS(source, destination, r) != null) {
				ArrayList<Integer> Stack = pathDFS(source, destination, r);
				//System.out.println("path now is " + Stack);
				minW = r.getEdge(Stack.get(0), Stack.get(1)).weight;
				for (int i = 1; i < Stack.size() - 1; i++) {
					//System.out.print(Stack.get(i + 1));

//					System.out.println("minW init is " + minW);
//					System.out.println("start and end? " +Stack.get(i)+"    "+Stack.get(i + 1));
//					System.out.println("start and end? " +Stack.get(i)+"    "+Stack.get(i + 1));
					if (minW > r.getEdge(Stack.get(i), Stack.get(i + 1)).weight) {        
						//System.out.println("where? " + i);
						minW = r.getEdge(Stack.get(i), Stack.get(i + 1)).weight;
						//System.out.println("where? " + i);
					}
				}
				//System.out.println("min weight is " + minW);
				maxFlow += minW;
				//System.out.println("max flow is  " + maxFlow);
				for (int i = 0; i < Stack.size() - 1; i++) {
					r.setEdge(Stack.get(i), Stack.get(i + 1), r.getEdge(Stack.get(i), Stack.get(i + 1)).weight - minW);
					//System.out.println("new weight on the path is " + Stack.get(i) + " " + Stack.get(i + 1) + "   " + (r.getEdge(Stack.get(i), Stack.get(i + 1)).weight));
				}

			}

		Integer[] newW=new Integer [r.listOfEdgesSorted().size()] ;
		Integer[] gW=new Integer [r.listOfEdgesSorted().size()] ;
		for(int i=0;i<graph.listOfEdgesSorted().size();i++){
			newW[i]=r.getEdges().get(i).weight;
			//System.out.println("new weight " +newW[i]);
		}
		for(int i=0;i<ogW.length;i++){
			gW[i]=(Integer)(ogW[i]-newW[i]);
		}
		ArrayList <Edge> www=graph.getEdges();
		for(int i=0;i<www.size();i++){
			graph.setEdge(www.get(i).nodes[0],www.get(i).nodes[1],gW[i]);
			}

		answer += maxFlow + "\n" + graph.toString();
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}

	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
