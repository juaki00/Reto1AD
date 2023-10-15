package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Se sustituyen los patrones de una plantilla y se crean ficheros por los que haya en cada linea de un fichero de datos
 */
public class ArchivoConPatrones {

    private final List<String> textoDatos;
    private final String patrones;
    private final List<String> textoTemplate;

    public ArchivoConPatrones(List<String> textoDatos, String patrones, List<String> textoTemplate) {

        this.textoDatos = textoDatos;
        this.patrones = patrones;
        this.textoTemplate = textoTemplate;
    }

    /**
     * Se sustituyen los patrones del textoTemplate y se crean ficheros por los que haya en cada linea de textoDatos
     */
    public void escribirArchivosConPatrones(){
        //Para cada linea del archivo de datos
        for (String lineaDatos : textoDatos) {

            //separamos los patrones y datos en un array
            String[] patronesArray = patrones.split(",");
            String[] datosArray = lineaDatos.split(",");

            //sustituimos los patrones por los datos
            List<String> textoCambiado = sustituirPatrones(textoTemplate, patronesArray, datosArray);

            //escribimos linea a linea el nuevo archivo
            escribirArchivos(datosArray[0], textoCambiado);
        }
    }

    /**
     * Se escriben los archivos por cada String de la lista con los textos definitivos
     * @param idArchivo id de cada cliente, se usa para nombrar al archivo con cada id
     * @param textoCambiado Lista de los textos con los patrones sustituidos
     */
    private static void escribirArchivos(String idArchivo, List<String> textoCambiado) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter("archivo" + idArchivo, false));
            bw.write("");
            bw = new BufferedWriter(new FileWriter("archivo" + idArchivo, true));
            for (String lineaTexto : textoCambiado) {
                bw.newLine();
                bw.append(lineaTexto);
                bw.flush();
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Se sustituyen los patrones y devuelve la lista con los archivos definitivos
     * @param textoTemplate Plantilla con patrones {{patron}}
     * @param patrones      Cada uno de los patrones válidos a sustituir
     * @param valores       Cada uno de los valores de una linea
     * @return Lista con los patrones sustituidos por patrones
     */
    private static List<String> sustituirPatrones(List<String> textoTemplate, String[] patrones, String[] valores) {
        List<String> textoCambiado = new ArrayList<>();

        //Para cada linea de la plantilla busca el inicio y final de los patones
        for (String linea : textoTemplate) {
            int indexInicio = linea.indexOf("{{");
            int indexFinal = linea.indexOf("}}");

            //Iteramos mientras se encuentren los patrones de inicio y final
            while (indexInicio != -1 && indexFinal != -1) {
                String patron = linea.substring(indexInicio + 2, indexFinal);

                //Avanzamos el indice hasta encontrar el patron que buscamos
                int indice = 0;
                while (indice < patrones.length && !patrones[indice].equals(patron)) indice++;

                //Comprobamos que el patron existe y el numero de patrones corresponde con los datos y sustituimos
                if (indice >= patrones.length) {
                    throw new RuntimeException("El patrón " + patron + " no existe");
                } else if (patrones.length != valores.length) {
                    throw new RuntimeException("Numero de datos erroneos, debe haber " + patrones.length + " datos por linea separadas por comas");
                } else {
                    linea = linea.replace("{{" + patrones[indice] + "}}", valores[indice]);
                }

                //Comprobamos que no hay mas patrones en la linea
                indexInicio = linea.indexOf("{{", indexInicio + 1);
                indexFinal = linea.indexOf("}}", indexFinal + 1);
            }

            textoCambiado.add(linea);
        }
        return textoCambiado;
    }
}
