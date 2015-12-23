import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sthirumuru
 * Date: 16/12/15
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class Vertex {
        public int vertexId;
        public List<Edge> edges;
        public String toString()
        {
            return Integer.toString(vertexId);
        }
}
