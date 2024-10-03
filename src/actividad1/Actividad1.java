package actividad1;

import java.io.*;

public class Actividad1 {
    public static void main(String[] args) {
        String ficheroCsv = "src/actividad1/CONCIERTOS.csv";
        String ficheroTexto = "src/actividad1/CONCIERTOS.txt";

        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            br = new BufferedReader(new FileReader(ficheroCsv));
            bw = new BufferedWriter(new FileWriter(ficheroTexto));
            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea.toLowerCase());
                bw.newLine();
            }
            System.out.println("Fichero " + ficheroCsv + " convertido a " + ficheroTexto);
        } catch (IOException e) {
            System.out.println("Error de lectura/escritura");
        } finally {
            try {
                if (br != null) br.close();
                if (bw != null) bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
