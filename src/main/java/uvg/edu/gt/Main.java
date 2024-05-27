package uvg.edu.gt;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

/*
 * Autor: Javier Alexander Linares Chang - 231135
 * Fecha: 26/05/2024
 * Descripción: Clase principal para la ejecución del programa de gestión de rutas.
 * Contiene el método main y métodos auxiliares para leer y modificar el grafo.
 */

public class Main {
    private static Graph graph;
    private static FloydAlgorithm floydAlgorithm;
    private static GraphCenter graphCenter;

    /**
     * Método principal para la ejecución del programa.
     * @param args Argumentos de línea de comando (no utilizados).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            String filename = "grafo.txt";
            int numCities = countCitiesInFile(filename);
            graph = new Graph(numCities);
            readGraphFromFile(filename);

            floydAlgorithm = new FloydAlgorithm(graph);
            floydAlgorithm.floydWarshall();

            graphCenter = new GraphCenter(floydAlgorithm, graph.getSize());

            boolean running = true;
            while (running) {
                System.out.println("Opciones:");
                System.out.println("1. Encontrar ruta más corta");
                System.out.println("2. Centro del grafo");
                System.out.println("3. Modificar grafo");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume la linea

                switch (option) {
                    case 1:
                        findShortestPath(scanner);
                        break;
                    case 2:
                        findGraphCenter();
                        break;
                    case 3:
                        modifyGraph(scanner);
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    /**
     * Cuenta el número de ciudades únicas en el archivo especificado.
     * @param filename El nombre del archivo que contiene el grafo.
     * @return El número de ciudades únicas.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private static int countCitiesInFile(String filename) throws IOException {
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

    /**
     * Lee el grafo desde el archivo especificado y lo construye.
     * @param filename El nombre del archivo que contiene el grafo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private static void readGraphFromFile(String filename) throws IOException {
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

    /**
     * Encuentra y muestra la ruta más corta entre dos ciudades ingresadas por el usuario.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    private static void findShortestPath(Scanner scanner) {
        System.out.print("Ingrese la ciudad de origen: ");
        String from = scanner.nextLine();
        System.out.print("Ingrese la ciudad de destino: ");
        String to = scanner.nextLine();

        int fromIndex = graph.getCityIndex(from);
        int toIndex = graph.getCityIndex(to);
        int distance = floydAlgorithm.getDistance(fromIndex, toIndex);
        String path = floydAlgorithm.getPath(fromIndex, toIndex, graph.getCities());

        if (distance == Integer.MAX_VALUE) {
            System.out.println("No hay camino entre " + from + " y " + to);
        } else {
            System.out.println("Distancia más corta: " + distance);
            System.out.println("Ruta: " + path);
        }
    }

    /**
     * Encuentra y muestra el centro del grafo.
     */
    private static void findGraphCenter() {
        int centerIndex = graphCenter.getGraphCenter();
        String centerCity = graph.getCities()[centerIndex];
        System.out.println("El centro del grafo es: " + centerCity);
    }

    /**
     * Modifica el grafo según las opciones ingresadas por el usuario.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    private static void modifyGraph(Scanner scanner) {
        System.out.println("Opciones de modificación:");
        System.out.println("a) Interrupción de tráfico entre un par de ciudades");
        System.out.println("b) Establecer una conexión entre ciudades");
        System.out.print("Seleccione una opción: ");
        String option = scanner.nextLine();

        System.out.print("Ingrese la ciudad 1: ");
        String city1 = scanner.nextLine();
        System.out.print("Ingrese la ciudad 2: ");
        String city2 = scanner.nextLine();

        switch (option) {
            case "a":
                graph.removeEdge(city1, city2);
                break;
            case "b":
                System.out.print("Ingrese la distancia (en KM): ");
                int distance = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                graph.updateEdge(city1, city2, distance);
                break;
            default:
                System.out.println("Opción no válida");
                return;
        }

        floydAlgorithm = new FloydAlgorithm(graph);
        floydAlgorithm.floydWarshall();
        graphCenter = new GraphCenter(floydAlgorithm, graph.getSize());

        System.out.println("Grafo modificado y rutas recalculadas.");
    }
}