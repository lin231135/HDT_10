package uvg.edu.gt;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa un grafo dirigido usando una matriz de adyacencia.
 */
public class Graph {
    private final int INF = Integer.MAX_VALUE;
    private Map<String, Integer> cityIndex;
    private String[] cities;
    private int[][] adjMatrix;
    private int size;
    private int currentIndex;

    /**
     * Constructor de la clase Graph.
     * @param numCities El número de ciudades (vértices) en el grafo.
     */
    public Graph(int numCities) {
        this.size = numCities;
        this.cityIndex = new HashMap<>();
        this.cities = new String[numCities];
        this.adjMatrix = new int[numCities][numCities];
        this.currentIndex = 0;
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i == j) {
                    adjMatrix[i][j] = 0;
                } else {
                    adjMatrix[i][j] = INF;
                }
            }
        }
    }

    /**
     * Agrega una ciudad al grafo.
     * @param city El nombre de la ciudad a agregar.
     */
    public void addCity(String city) {
        if (!cityIndex.containsKey(city)) {
            cityIndex.put(city, currentIndex);
            cities[currentIndex] = city;
            currentIndex++;
        }
    }

    /**
     * Agrega un arco dirigido entre dos ciudades con una distancia específica.
     * @param from La ciudad de origen.
     * @param to La ciudad de destino.
     * @param distance La distancia entre las ciudades.
     */
    public void addEdge(String from, String to, int distance) {
        int fromIndex = cityIndex.get(from);
        int toIndex = cityIndex.get(to);
        adjMatrix[fromIndex][toIndex] = distance;
    }

    /**
     * Obtiene la matriz de adyacencia del grafo.
     * @return La matriz de adyacencia.
     */
    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    /**
     * Obtiene el arreglo de ciudades.
     * @return El arreglo de nombres de ciudades.
     */
    public String[] getCities() {
        return cities;
    }

    /**
     * Obtiene el índice de una ciudad en el grafo.
     * @param city El nombre de la ciudad.
     * @return El índice de la ciudad.
     */
    public int getCityIndex(String city) {
        return cityIndex.get(city);
    }

    /**
     * Obtiene el tamaño del grafo (número de ciudades).
     * @return El tamaño del grafo.
     */
    public int getSize() {
        return size;
    }

    /**
     * Elimina un arco entre dos ciudades.
     * @param from La ciudad de origen.
     * @param to La ciudad de destino.
     */
    public void removeEdge(String from, String to) {
        int fromIndex = cityIndex.get(from);
        int toIndex = cityIndex.get(to);
        adjMatrix[fromIndex][toIndex] = INF;
    }

    /**
     * Actualiza o establece un arco entre dos ciudades con una distancia específica.
     * @param from La ciudad de origen.
     * @param to La ciudad de destino.
     * @param distance La distancia entre las ciudades.
     */
    public void updateEdge(String from, String to, int distance) {
        addEdge(from, to, distance);
    }

    /**
     * Imprime la matriz de adyacencia del grafo.
     */
    public void printAdjMatrix() {
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print(cities[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print(cities[i] + " ");
            for (int j = 0; j < size; j++) {
                if (adjMatrix[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(adjMatrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}