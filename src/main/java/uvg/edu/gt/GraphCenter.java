package uvg.edu.gt;

/**
 * Clase que calcula el centro del grafo.
 */
public class GraphCenter {
    private int[] eccentricity;
    private int[][] dist;
    private int size;

    /**
     * Constructor de la clase GraphCenter.
     * @param FloydAlgorithm El objeto FloydWarshall que contiene las distancias calculadas.
     * @param size El tamaño del grafo.
     */
    public GraphCenter(FloydAlgorithm floydWarshall, int size) {
        this.size = size;
        this.dist = floydWarshall.getDistMatrix();
        this.eccentricity = new int[size];
        calculateEccentricity();
    }

    /**
     * Calcula la excentricidad de cada vértice en el grafo.
     */
    private void calculateEccentricity() {
        for (int i = 0; i < size; i++) {
            int maxDist = 0;
            for (int j = 0; j < size; j++) {
                if (dist[i][j] != Integer.MAX_VALUE && dist[i][j] > maxDist) {
                    maxDist = dist[i][j];
                }
            }
            eccentricity[i] = maxDist;
        }
    }

    /**
     * Obtiene el índice del centro del grafo.
     * @return El índice del vértice central.
     */
    public int getGraphCenter() {
        int minEccentricity = Integer.MAX_VALUE;
        int center = -1;
        for (int i = 0; i < size; i++) {
            if (eccentricity[i] < minEccentricity) {
                minEccentricity = eccentricity[i];
                center = i;
            }
        }
        return center;
    }
}