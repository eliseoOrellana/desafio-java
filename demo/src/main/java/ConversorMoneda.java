import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class ConversorMoneda {
    

    private static final String API_KEY = "f064672563728c0518e2d5a9";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    public static void main(String[] args) {
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            boolean salir = false;

            while (!salir) {
                // Mostrar menú de opciones
                System.out.println("***** Menú *****");
                System.out.println("1. Convertir moneda");
                System.out.println("2. Salir");
                System.out.println("Selecciona una opción: ");
                System.out.println("*****************");
                int opcion = Integer.parseInt(inputReader.readLine());

                switch (opcion) {
                    case 1:
                        convertirMoneda(inputReader);
                        break;
                    case 2:
                        salir = true;
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void convertirMoneda(BufferedReader inputReader) {
        try {
            // Realizar solicitud a la API
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Leer la respuesta de la API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Procesar la respuesta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");

            // Mostrar menú de países disponibles
            System.out.println("Selecciona el país al que deseas convertir:");
            System.out.println("1. Pesos argentinos (ARS)");
            System.out.println("2. Pesos chilenos (CLP)");
            System.out.print("Ingresa el número correspondiente al país: ");

            // Leer la selección del usuario
            int opcion = Integer.parseInt(inputReader.readLine());
            String monedaDestino;

            switch (opcion) {
                case 1:
                    monedaDestino = "ARS";
                    break;
                case 2:
                    monedaDestino = "CLP";
                    break;
                default:
                    System.out.println("Opción no válida. Seleccionando pesos argentinos por defecto.");
                    monedaDestino = "ARS";
                    break;
            }

            // Obtener la tasa de conversión de dólares a la moneda seleccionada
            double rate = conversionRates.getDouble(monedaDestino);

            // Solicitar al usuario la cantidad a convertir
            System.out.print("Ingresa el valor en dólares a convertir: ");
            double amountUSD = Double.parseDouble(inputReader.readLine());

            // Realizar la conversión
            double amountConverted = amountUSD * rate;

            // Mostrar el resultado al usuario
            System.out.println("El valor de " + amountUSD + " dólares corresponde a " + amountConverted + " " + monedaDestino);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}