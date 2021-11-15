package algoProject;




/*
CPCS 324 project pt1 
section:GAR
team members:
Shather Mohammed Alshubbak
Reem Abdulrhman Alghamdi 
Jumana abdulrahman almadhoun
15/11/2012
*/



//--------------------------class Edge------------------------------------------

/**
 *
 * class Edge to create edge objects for the graph
 */
class Edge {

    /**
     *source Vertex
     */
    private int sourceVert;

    /**
     * destination vertex;
     */
    private int destinationVert;

    /**
     *weight of the edge
     */
    private int weight;

    /**
     * setter to assign a source vertex
     * @param sourceVert
     */
    public void setSourceVert(int sourceVert) {
        this.sourceVert = sourceVert;
    }

    /**
     *setter to assign a Destination vertex
     * @param destinationVert
     */
    public void setDestinationVert(int destinationVert) {
        this.destinationVert = destinationVert;
    }

    /**
     *setter to assign a Weight to the edge
     * @param weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     *
     * @return Source Vertex
     */
    public int getSourceVert() {
        return sourceVert;
    }

    /**
     *
     * @return Destination Vertex
     */
    public int getDestinationVert() {
        return destinationVert;
    }

    /**
     *
     * @return Weight
     */
    public int getWeight() {
        return weight;
    }

//--------------------constructor-----------------------------------------------

    /**
     * Edge constructor
     * @param source source vertex
     * @param destination destination vertex
     * @param weight   weight of edge
     */
    public Edge(int source, int destination, int weight) {
        this.sourceVert = source;
        this.destinationVert = destination;
        this.weight = weight;
    }
//----------------------printing the edge info----------------------------------

    /**
     *printing the edge info 
     * @return string of edge source vertex, destination vertex and weight 
     */
    @Override
    public String toString() {
        return "source "+sourceVert + "-" +"destenation "+ destinationVert + ": " + weight;
    }
}
