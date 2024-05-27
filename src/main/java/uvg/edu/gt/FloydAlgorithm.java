package uvg.edu.gt;

/**
 * Clase que implementa el algoritmo de Floyd-Warshall para encontrar las rutas más cortas entre todos los pares de nodos en un grafo.
 */
public class FloydAlgorithm {
    private int[][] dist;
    private int[][] next;
    private int size;
    private final int INF = Integer.MAX_VALUE;

    /**
     * Constructor de la clase FloydWarshall.
     * @param graph El grafo sobre el cual se ejecutará el algoritmo de Floyd-Warshall.
     */
    public FloydAlgorithm(Graph graph) {
        this.size = graph.getSize();
        this.dist = new int[size][size];
        this.next = new int[size][size];
        initialize(graph.getAdjMatrix());
    }

    /**
     * Inicializa las matrices de distancias y siguientes utilizando la matriz de adyacencia del grafo.
     * @param adjMatrix La matriz de adyacencia del grafo.
     */
    private void initialize(int[][] adjMatrix) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                dist[i][j] = adjMatrix[i][j];
                if (adjMatrix[i][j] != INF && i != j) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }
    }

    /**
     * Ejecuta el algoritmo de Floyd-Warshall para encontrar las distancias más cortas entre todos los pares de nodos.
     */
    public void floydWarshall() {
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    /**
     * Obtiene la distancia más corta entre dos nodos.
     * @param from El índice del nodo de origen.
     * @param to El índice del nodo de destino.
     * @return La distancia más corta entre los nodos.
     */
    public int getDistance(int from, int to) {
        return dist[from][to];
    }

    /**
     * Obtiene la ruta más corta entre dos nodos.
     * @param from El índice del nodo de origen.
     * @param to El índice del nodo de destino.
     * @param cities El arreglo de nombres de ciudades.
     * @return Una cadena que representa la ruta más corta entre los nodos.
     */
    public String getPath(int from, int to, String[] cities) {
        if (dist[from][to] == INF) {
            return "No path";
        }
        StringBuilder path = new StringBuilder();
        int current = from;
        path.append(cities[current]);
        while (current != to) {
            current = next[current][to];
            if (current == -1) {
                return "No path";
            }
            path.append(" -> ").append(cities[current]);
        }
        return path.toString();
    }

    /**
     * Obtiene la matriz de distancias calculada.
     * @return La matriz de distancias.
     */
    public int[][] getDistMatrix() {
        return dist;
    }
}