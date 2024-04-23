import java.util.*;

public class ListGraph <T> implements Graph<T>{
    private final Map<T, Set<Edge<T>>> locations = new HashMap<>();

    @Override
    public void add(T node) {
        locations.putIfAbsent(node, new HashSet<>());
    }

    @Override
    public void connect(T node1, T node2, String name, int weight) {
        if (!locations.containsKey(node1) || !locations.containsKey(node2)){
            throw new NoSuchElementException("The node is not found");
        }
        if (weight < 0){
            throw new IllegalArgumentException("The weight is negative");
        }

        Edge<T> edge1 = new Edge(node2, name, weight);
        Edge<T> edge2 = new Edge(node1, name, weight);

        Set<Edge<T>> fromDestinations = locations.get(node1);
        Set<Edge<T>> toDestinations = locations.get(node2);

        if (fromDestinations.contains(edge1) || toDestinations.contains(edge2)){
            throw new IllegalStateException("The edge already exists");
        }

        fromDestinations.add(edge1);
        toDestinations.add(edge2);

    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) {
        if (!locations.containsKey(node1) || !locations.containsKey(node2)){
            throw new NoSuchElementException("The node is not found");
        }
        if (weight < 0){
            throw new IllegalArgumentException("The weight is negative");
        }

        Edge<T> edge1 = getEdgeBetween(node1, node2);
        Edge<T> edge2 = getEdgeBetween(node2, node1);

        if(edge1 == null || edge2 == null){
            throw new NullPointerException();
        }

        edge1.setWeight(weight);
        edge2.setWeight(weight);
    }

    @Override
    public Set<T> getNodes() { // TODO ingående?
        return Collections.unmodifiableSet(locations.keySet());
    }

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) {
        if (!locations.containsKey(node)){
            throw new NoSuchElementException("The node is not found");
        }
        Set<Edge<T>> edgeList = locations.get(node);
        return Set.copyOf(edgeList);
    }

    @Override
    public Edge getEdgeBetween(T node1, T node2) {
        if (!locations.containsKey(node1) || !locations.containsKey(node2)){
            throw new NoSuchElementException("The node is not found");
        }

        Set<Edge<T>> edges = locations.get(node1);
        for(Edge e: edges){
            if(e.getDestination().equals(node2)){
                return e;
            }
        }
        return null;
    }

    @Override
    public void disconnect(T node1, T node2) {

    }

    @Override
    public void remove(T node) {
        if (!locations.containsKey(node)){
            throw new NoSuchElementException("The node is not found");
        }
        locations.remove(node);
        for (Set<Edge<T>> edges : locations.values()) {
            edges.removeIf(edge -> edge.getDestination().equals(node));
        }
    }

    @Override
    public boolean pathExists(T from, T to) {
        return false;
    }

    @Override
    public List<Edge<T>> getPath(T from, T to) {
        Map<T, T> connection = new HashMap<>();

        return null;
    }
}
