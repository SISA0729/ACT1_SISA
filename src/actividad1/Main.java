package actividad1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String ficheroCsv = "src/actividad1/CONCIERTOS.csv";
        String ficheroTexto = "src/actividad1/CONCIERTOS.txt";
        String ficheroAleatorio = "src/actividad1/CONCIERTOS.aleatorio";
        String ficheroBin = "src/actividad1/CONCIERTOS.bin";
        String ficheroXml = "src/actividad1/CONCIERTOS.XML";

        convertirCsvATexto(ficheroCsv, ficheroTexto);
        convertirCsvAAleatorio(ficheroCsv, ficheroAleatorio);
        convertirCsvABinario(ficheroCsv, ficheroBin);
        convertirBinAXml(ficheroBin, ficheroXml);

        System.out.println("==========================================");
    }

    public static void convertirCsvATexto(String ficheroCsv, String ficheroTexto) {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroCsv));
             BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroTexto))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea.toLowerCase());
                bw.newLine();
            }
            System.out.println("Fichero " + ficheroCsv + " convertido a " + ficheroTexto);
        } catch (IOException e) {
            System.out.println("Error de lectura/escritura");
            e.printStackTrace();
        }
    }

    public static void convertirCsvAAleatorio(String ficheroCsv, String ficheroAleatorio) {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroCsv));
             RandomAccessFile raf = new RandomAccessFile(ficheroAleatorio, "rw")) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                if (campos.length >= 4) {
                    raf.writeBytes(String.format("%-50s", campos[0]));
                    raf.writeBytes(String.format("%-50s", campos[1]));
                    raf.writeBytes(String.format("%-30s", campos[2]));
                    raf.writeBytes(String.format("%-10s", campos[3]));
                    raf.writeBytes("\n");
                }
            }
            System.out.println("Fichero " + ficheroCsv + " convertido a " + ficheroAleatorio);
        } catch (IOException e) {
            System.out.println("Error de lectura/escritura");
            e.printStackTrace();
        }
    }

    public static void convertirCsvABinario(String ficheroCsv, String ficheroBin) {
        List<Concierto> conciertos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ficheroCsv));
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheroBin))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");
                if (campos.length >= 4) {
                    conciertos.add(new Concierto(
                            campos[0],
                            campos[1],
                            campos[2],
                            campos[3]
                    ));
                }
            }

            oos.writeObject(conciertos);
            System.out.println("Fichero " + ficheroCsv + " convertido a " + ficheroBin);

        } catch (IOException e) {
            System.out.printf("Error: %s%n", e.getMessage());
        }
    }

    public static void convertirBinAXml(String ficheroBin, String ficheroXml) {
        List<Concierto> listaConciertos;

        try (ObjectInputStream entradaBinaria = new ObjectInputStream(new FileInputStream(ficheroBin))) {
            listaConciertos = (List<Concierto>) entradaBinaria.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el archivo binario: " + e.getMessage());
            return;
        }

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element raiz = document.createElement("Conciertos");
            document.appendChild(raiz);

            for (Concierto concierto : listaConciertos) {
                Element conciertoElement = document.createElement("Concierto");

                Element grupoElement = document.createElement("Grupo");
                grupoElement.appendChild(document.createTextNode(concierto.getGrupo()));
                conciertoElement.appendChild(grupoElement);

                Element lugarElement = document.createElement("Lugar");
                lugarElement.appendChild(document.createTextNode(concierto.getLugar()));
                conciertoElement.appendChild(lugarElement);

                Element fechaElement = document.createElement("Fecha");
                fechaElement.appendChild(document.createTextNode(concierto.getFecha()));
                conciertoElement.appendChild(fechaElement);

                Element horaElement = document.createElement("Hora");
                horaElement.appendChild(document.createTextNode(concierto.getHora()));
                conciertoElement.appendChild(horaElement);

                raiz.appendChild(conciertoElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(ficheroXml));

            transformer.transform(source, result);
            System.out.println("Archivo XML generado exitosamente: " + ficheroXml);
        } catch (Exception e) {
            System.err.println("Error al generar el archivo XML: " + e.getMessage());
        }}
}
