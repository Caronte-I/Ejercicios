package psp;

import java.util.Arrays;
import java.util.Random;

public class Temperaturas {

    // Simular temperaturas aleatorias de los últimos 10 años (3650 días)
    private static int[] temperaturas = new int[3650];

    // Número de partes en las que dividir el array
    private static int numPartes = 10;
    private static int tamanoParte = temperaturas.length / numPartes;

    // Variable global para almacenar la temperatura máxima encontrada
    private static int temperaturaMaximaGlobal = Integer.MIN_VALUE;
    private static final Object lock = new Object();

    // Función que busca la temperatura máxima en una parte específica del array
    private static void buscarTemperaturaMaxima(int[] parteTemperaturas) {
        // Encontrar la temperatura máxima local en la parte asignada
        int temperaturaMaximaLocal = Arrays.stream(parteTemperaturas).max().orElse(Integer.MIN_VALUE);

        // Actualizar la temperatura máxima global utilizando un bloque sincronizado
        synchronized (lock) {
            if (temperaturaMaximaLocal > temperaturaMaximaGlobal) {
                temperaturaMaximaGlobal = temperaturaMaximaLocal;
            }
        }
    }

    public static void main(String[] args) {
        // Inicializar temperaturas aleatorias
        Random random = new Random();
        for (int i = 0; i < temperaturas.length; i++) {
            temperaturas[i] = random.nextInt(81) - 30; // Números aleatorios entre -30 y 50
        }

        // Crear hilos y asignarles partes del array para buscar la temperatura máxima local
        Thread[] hilos = new Thread[numPartes];
        for (int i = 0; i < numPartes; i++) {
            int inicio = i * tamanoParte;
            int fin = (i + 1) * tamanoParte;
            int[] parteTemperaturas = Arrays.copyOfRange(temperaturas, inicio, fin);

            // Crear un hilo que ejecuta la función buscarTemperaturaMaxima con la parte asignada
            hilos[i] = new Thread(() -> buscarTemperaturaMaxima(parteTemperaturas));
        }

        // Iniciar los hilos
        for (Thread hilo : hilos) {
            hilo.start();
        }

        // Esperar a que todos los hilos terminen
        try {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Se imprime la temperatura máxima global encontrada
        System.out.println("Temperatura máxima de los últimos 10 años: " + temperaturaMaximaGlobal);
    }
}

