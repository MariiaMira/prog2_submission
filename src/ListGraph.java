import java.util.*;

public class ListGraph <T> implements Graph<T>{
    private final Map<T, Set<Edge<T>>> locations = new HashMap<>();

    @Override
    public void add(T node) {
        locations.putIfAbsent(node, new HashSet<>());
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {

    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {

    }

    @Override
    public Set<T> getNodes() {
        return null;
    }

    @Override
    public Collection<Edge> getEdgesFrom(T node) {
        return null;
    }

    @Override
    public Edge getEdgeBetween(T node1, T node2) {
        return null;
    }

    @Override
    public void disconnect(T node1, T node2) {

    }

    @Override
    public void remove(T node) {

    }

    @Override
    public boolean pathExists(T from, T to) {
        return false;
    }

    @Override
    public List<Edge> getPath(T from, T to) {
        return null;
    }
}
