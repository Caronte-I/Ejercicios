package psp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ProcesoGoogle {

	public static void main(String[] args) {
		
	//Se especifica el destino, que es Google
		String destino = "google.com";
				
	//Realizar los 3 pings
		realizarPing(destino, 3);
		
	}
	
	public static void realizarPing(String destino, int intentos) {
		System.out.println("\nRealizando " + intentos + " pings a " + destino + ":");
		
		//Se declara el bucle for
		for (int i = 0; i< intentos; i++) {
			try {
		//Se crea el proceso		
			
				ProcessBuilder processBuilder = new ProcessBuilder("ping", "-n", "1", destino);
				Process proceso = processBuilder.start();	
			
		//Salida del proceso
				
				InputStream inputStream = proceso.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				
				String linea;
				while ((linea = bufferedReader.readLine()) != null) {
					System.out.println(linea);
				}
				
				//Proceso en ejecución, esperar su finalización
				int exitCode = proceso.waitFor();
				if (exitCode == 0) {
					System.out.println("Ping realizado correctamente");
				} else {
					System.out.println("Error en el ping");
					
				}
				
			} catch (IOException | InterruptedException e) {
				
				System.out.println("Error al realizar el ping a " + destino + ": " + e.getMessage());
				
			} 
                 
				
		
		}

	}

}
