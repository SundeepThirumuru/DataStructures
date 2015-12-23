import java.beans.VetoableChangeListener;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: sthirumuru
 * Date: 15/12/15
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheckLoopGraph {

    public static boolean checkLoop(Graph graph)
    {
        if(graph.vertices == null || graph.vertices.isEmpty()) return false;

        Set<Vertex> visitedVertices = new LinkedHashSet<Vertex>();
        Stack<Vertex> loopPath = new Stack<Vertex>();
        for(Vertex vertex : graph.vertices)
        {
            if(!visitedVertices.contains(vertex) && checkLoopUsingDfs(vertex, null, graph, visitedVertices, loopPath))
            {
                System.out.println(loopPath);
                return true;
            }
        }
        return false;
    }

    public static boolean checkLoopUsingDfs(Vertex vertex, Vertex parent, Graph graph, Set<Vertex> visitedVertices, Stack<Vertex> loopPath)
    {
        if(visitedVertices.contains(vertex)) return true;
        visitedVertices.add(vertex);
        loopPath.push(vertex);
        for(Edge edge : vertex.edges)
        {
            Vertex neighbor = edge.a.equals(vertex) ? edge.z : edge.a;
            if(!neighbor.equals(parent))
            {
                if(checkLoopUsingDfs(neighbor, vertex, graph, visitedVertices, loopPath))
                {
                    return true;
                }
            }
        }
        loopPath.pop();
        return false;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        List<Vertex> vertices = new ArrayList<Vertex>();
        List<Edge> edges = new ArrayList<Edge>();
        Map<Integer, Vertex> vertexMap = new LinkedHashMap<Integer, Vertex>();
        for(int i=1; i < 6; i++)
        {
            Vertex vertex =  new Vertex();
            vertex.vertexId = i;
            vertex.edges = new ArrayList<Edge>();
            vertices.add(vertex);
            vertexMap.put(vertex.vertexId, vertex);
        }

        Map<Integer, Integer> connectionMap = new LinkedHashMap<Integer, Integer>();
        connectionMap.put(1, 2);
        connectionMap.put(2, 3);
        connectionMap.put(3, 4);
        connectionMap.put(4, 1);
        connectionMap.put(5, 4);
        for(Integer a : connectionMap.keySet())
        {
            Vertex aVertex = vertexMap.get(a);
            Vertex zVertex = vertexMap.get(connectionMap.get(a));
            Edge edge = new Edge();
            edge.a = aVertex;
            edge.z = zVertex;
            edges.add(edge);
            aVertex.edges.add(edge);
            zVertex.edges.add(edge);
        }
        graph.vertices = vertices;
        graph.edges = edges;

        System.out.println("Graph has Loop " + checkLoop(graph));
    }
}
