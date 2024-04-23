public class Edge <T>{
    private final T destination;
    private final String name;
    private int weight;

    public Edge(T destination, String name, int weight) {
        this.destination = destination;
        this.name = name;
        this.weight = weight;
    }

    public T getDestination(){
        return destination;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        if (weight < 0){
            throw new IllegalArgumentException("The weight is negative");
        }
        this.weight = weight;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return "";
    }
}
