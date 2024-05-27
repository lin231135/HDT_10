import org.junit.Before;
import org.junit.Test;

import uvg.edu.gt.Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import static org.junit.Assert.*;

public class GraphTest {
    private Graph graph;

    @Before
    public void setUp() throws IOException {
        graph = new Graph(countCitiesInFile("grafo.txt"));
        readGraphFromFile("grafo.txt");
    }

    private int countCitiesInFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        HashSet<String> citiesSet = new HashSet<>();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            citiesSet.add(parts[0]);
            citiesSet.add(parts[1]);
        }
        reader.close();
        return citiesSet.size();
    }

    private void readGraphFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            String city1 = parts[0];
            String city2 = parts[1];
            int distance = Integer.parseInt(parts[2]);

            graph.addCity(city1);
            graph.addCity(city2);
            graph.addEdge(city1, city2, distance);
        }
        reader.close();
    }

    @Test
    public void testAddCity() {
        assertEquals(4, graph.getSize());
        assertEquals(0, graph.getCityIndex("Mixco"));
        assertEquals(1, graph.getCityIndex("Antigua"));
        assertEquals(2, graph.getCityIndex("Escuintla"));
        assertEquals(3, graph.getCityIndex("SantaLucia"));
    }

    @Test
    public void testAddEdge() {
        int[][] adjMatrix = graph.getAdjMatrix();
        assertEquals(30, adjMatrix[graph.getCityIndex("Mixco")][graph.getCityIndex("Antigua")]);
        assertEquals(25, adjMatrix[graph.getCityIndex("Antigua")][graph.getCityIndex("Escuintla")]);
        assertEquals(15, adjMatrix[graph.getCityIndex("Escuintla")][graph.getCityIndex("SantaLucia")]);
        assertEquals(Integer.MAX_VALUE, adjMatrix[graph.getCityIndex("Mixco")][graph.getCityIndex("SantaLucia")]);
    }

    @Test
    public void testRemoveEdge() {
        graph.removeEdge("Mixco", "Antigua");
        int[][] adjMatrix = graph.getAdjMatrix();
        assertEquals(Integer.MAX_VALUE, adjMatrix[graph.getCityIndex("Mixco")][graph.getCityIndex("Antigua")]);
    }

    @Test
    public void testUpdateEdge() {
        graph.updateEdge("Mixco", "Antigua", 35);
        int[][] adjMatrix = graph.getAdjMatrix();
        assertEquals(35, adjMatrix[graph.getCityIndex("Mixco")][graph.getCityIndex("Antigua")]);
    }

    @Test
    public void testGetCityIndex() {
        assertEquals(0, graph.getCityIndex("Mixco"));
        assertEquals(1, graph.getCityIndex("Antigua"));
        assertEquals(2, graph.getCityIndex("Escuintla"));
        assertEquals(3, graph.getCityIndex("SantaLucia"));
    }

    @Test
    public void testGetCities() {
        String[] cities = graph.getCities();
        assertEquals("Mixco", cities[0]);
        assertEquals("Antigua", cities[1]);
        assertEquals("Escuintla", cities[2]);
        assertEquals("SantaLucia", cities[3]);
    }

    @Test
    public void testPrintAdjMatrix() {
        graph.printAdjMatrix();
        // No hay aserción aquí; solo imprime la matriz de adyacencia en la consola.
        // Puedes verificar visualmente si la impresión es correcta o no.
    }
}
