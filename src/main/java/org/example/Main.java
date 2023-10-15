package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Se crea un nuevo archivo de texto para cada linea en el archivo template que combine
 * la información de la plantilla con los datos de ese cliente concreto
 */
public class Main {

    /**
     * Metodo main
     * @param args Sin argumentos
     */
    public static void main(String[] args){
        //leer archivos
        LeerArchivo leerDatos = new LeerArchivo("data2.csv");
        LeerArchivo leerTemplate = new LeerArchivo("template2.txt");
        List<String> textoDatosConCabecera = leerDatos.leer();
        List<String> textoTemplate = leerTemplate.leer();

        //leer y quitar la primera linea del archivo (patrones)
        String patrones = extraerPatrones(textoDatosConCabecera);
        List<String> textoDatos = quitarprimeraLinea(textoDatosConCabecera);

        //escribir un archivo por cada linea sustituyendo los patrones
        ArchivoConPatrones acp = new ArchivoConPatrones(textoDatos, patrones, textoTemplate);
        acp.escribirArchivosConPatrones();
    }

    /**
     *Quita la primera linea del archivo
     * @param textoDatosConCabecera Texto con los patrones en la primera linea y los datos de los clientes en las demas
     * @return devuelve el archivo sin la cabecera y solo con los datos
     */
    private static List<String> quitarprimeraLinea(List<String> textoDatosConCabecera) {
        List<String> textoDatos = new ArrayList<String>(textoDatosConCabecera);
        textoDatos.remove(0);
        return textoDatos;
    }

    /**
     *Devuelve la primera linea del archivo
     * @param textoDatosConCabecera Texto con los patrones en la primera linea y los datos de los clientes en las demas
     * @return devuelve la primera linea del archivo, correspondiente a los patrones
     */
    private static String extraerPatrones(List<String> textoDatosConCabecera) {
        return textoDatosConCabecera.get(0);
    }


}