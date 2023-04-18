/*
CASO PRÁCTICO A - MiniTerminal & MiniFileManager
Implementa un programa que funcione como una pequeña terminal Linux, con algunos comandos
(simplificados) que permitan al usuario realizar distintas operaciones de gestión de archivos. Los
comandos que el usuario podrá ejecutar son:
● pwd: Muestra cual es la carpeta actual.
● cd <DIR>: Cambia la carpeta actual a ‘DIR’. Con .. cambia a la carpeta superior.
● ls: Muestra la lista de directorios y archivos de la carpeta actual (primero directorios, luego
archivos, ambos ordenados alfabéticamente).
● ll: Como ls pero muestra también el tamaño y la fecha de última modificación.
● mkdir <DIR>: Crea el directorio ‘DIR’ en la carpeta actual.
● rm <FILE>: Borra ‘FILE’. Si es una carpeta, borrará primero sus archivos y luego la carpeta. Si
tiene subcarpetas, las dejará intactas y mostrará un aviso al usuario.
● mv <FILE1> <FILE2>: Mueve o renombra ‘FILE1’ a ‘FILE2’.
● help: Muestra una breve ayuda con los comandos disponibles.
● exit: Termina el programa.

Clase MiniTerminal: Clase principal (con función main) que se encargará de interactuar con el
usuario e interpretar los comandos (qué comando se pide, argumentos, etc.). Utilizará la segunda
clase para realizar las operaciones de gestión de archivos. Manejará todas las posibles excepciones.

Clase MiniFileManager: Tendrá los atributos y métodos que necesites, para realizar las distintas
operaciones relacionadas con la gestión de archivos. Necesitarás al menos un método por cada
operación. Se lanzará una excepción si se produce un error o la operación solicitada no es posible.
Algunos ejemplos que podrías implementar:
● String getPWD(): Devuelve una cadena de texto con la carpeta actual.
● boolean changeDir(String dir): Cambia la carpeta actual a dir. Devuelve ‘true’ si fue posible.
● void printList(boolean info): Muestra una lista con los directorios y archivos de la carpeta
actual. Si info es ‘true’ mostrará también su tamaño y fecha de modificación.
● etc.
 */
package MiniTerminalCarpeta;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MiniFileManager {

    private File directorio;

    //=======================constructor==============================
    public MiniFileManager(File directorio) {
        this.directorio = directorio;
    }

    //====================getters y setters===========================
    public File getDirectorio() {
        return directorio;
    }

    public void setDirectorio(File directorio) {
        this.directorio = directorio;
    }

    //===========================funciones============================
    //----------------------pwd-------------------
    public String getPWD() {
        return directorio.getAbsolutePath();
    }

    //----------------------cd--------------------
    public boolean changeDir(String dir) {

        File nuevoDir = new File(dir);
        if (dir.equals("..")) {
            File nuevoDir2 = new File(directorio.getParent());
            setDirectorio(nuevoDir2);
            return true;
        } else if (nuevoDir.exists()) {
            setDirectorio(nuevoDir);
            return true;
        } else {
            return false;
        }
    }

    //-------------------ls y ll-----------------
    public void printList(boolean info) throws FileNotFoundException {

        if (info) {
            if (directorio.exists()) {
                //muestra el nombre del archivo
                System.out.println("+------------------------------+");
                System.out.println("Nombre -> " + directorio.getName());
                System.out.println("+------------------------------+");

                File[] files = directorio.listFiles();

                System.out.println("Los documentos son:");

                //muestra directorios
                for (int i = 0; i < files.length; i++) {
                    Arrays.sort(files);
                    if (files[i].isDirectory()) {
                        System.out.println("[*]" + files[i].getName());                    //imprime nombre
                        System.out.println("Tamaño -> " + files[i].length() + " bytes");   //imprime tamaño
                        long milisegundos = files[i].lastModified();                       //guarda los milisegundos en una variable de tipo long 
                        fechaModificacion(milisegundos);                                   //funcion que imprime la fecha recibiendo los milisegundos  
                        System.out.println("---------------------------");
                    }
                }

                //muestra archivos
                for (int i = 0; i < files.length; i++) {
                    Arrays.sort(files);
                    if (files[i].isFile()) {
                        System.out.println("[A]" + files[i].getName());                    //imprime nombre
                        System.out.println("Tamaño -> " + files[i].length() + " bytes");   //imprime tamaño
                        long milisegundos = files[i].lastModified();                       //guarda los milisegundos en una variable de tipo long
                        fechaModificacion(milisegundos);                                   //funcion que imprime la fecha recibiendo los milisegundos
                        System.out.println("--------------------------");
                    }
                }

                System.out.println("+------------------------------+");

            } else {
                throw new FileNotFoundException("No se encuentra el archivo");
            }

        } else {
            if (directorio.exists()) {
                //muestra el nombre del archivo
                System.out.println("+------------------------------+");
                System.out.println("Nombre -> " + directorio.getName());
                System.out.println("+------------------------------+");

                File[] files = directorio.listFiles();

                //muestra los elementos
                System.out.println("Los documentos son:");
                for (int i = 0; i < files.length; i++) {
                    Arrays.sort(files);
                    if (files[i].isDirectory()) {               //muestra directorios
                        System.out.println("[*]" + files[i].getName());
                    }
                }
                for (int i = 0; i < files.length; i++) {
                    Arrays.sort(files);
                    if (files[i].isFile()) {                  //muestra archivos
                        System.out.println("[A]" + files[i].getName());
                    }
                }

                System.out.println("------------------------------");

            } else {
                throw new FileNotFoundException("No se encuentra el archivo");
            }
        }
    }

    public void infoArchivos(boolean estado) throws FileNotFoundException {

        if (estado) {
            System.out.println("[A]" + directorio.getName());
            System.out.println("Tamaño -> " + directorio.length() + " bytes");
            long milisegundos = directorio.lastModified();
            fechaModificacion(milisegundos);
        } else if (estado == false) {
            System.out.println("[A]" + directorio.getName());
        }
    }

    public static void fechaModificacion(long milisegundos) {
        Date fecha = new Date(milisegundos);
        Calendar c = new GregorianCalendar();
        c.setTime(fecha);

        String dia, mes, año, hora, minuto, segundo;

        dia = Integer.toString(c.get(Calendar.DATE));
        mes = Integer.toString(c.get(Calendar.MONTH));
        año = Integer.toString(c.get(Calendar.YEAR));
        hora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        minuto = Integer.toString(c.get(Calendar.MINUTE));
        segundo = Integer.toString(c.get(Calendar.SECOND));

        System.out.println("Ultima modificacion -> " + hora + ":" + minuto + ":" + segundo + " " + dia + "/" + mes + "/" + año);
    }

    //--------------------mkdir----------------
    public boolean createDir(String dir) {
        File nuevoDir = new File(dir);
        boolean resultado = nuevoDir.mkdir();

        return resultado;
    }

    //--------------------rm-------------------
    public boolean deleteDir(String dir) throws FileNotFoundException {
        boolean resultado = false;

        File nuevoDir = new File(dir);

        if (nuevoDir.exists()) {

            if (nuevoDir.isFile()) {

                resultado = nuevoDir.delete();

            } else if (nuevoDir.isDirectory()) {

                File[] lista = nuevoDir.listFiles();
                for (int i = 0; i < lista.length; i++) {
                    File f = lista[i];
                    f.delete();
                }
                resultado = nuevoDir.delete();
            }

        } else {
            throw new FileNotFoundException("No se ha encontrado el archivo/carpeta");
        }

        return resultado;
    }

    //---------------------mv-----------------
    public boolean moveDir(String rutaOrigen, String rutaDestino) {
        File origen = new File(rutaOrigen);
        File destino = new File(rutaDestino);

        boolean resultado = origen.renameTo(destino);
        return resultado;
    }

    //-------------------help------------------
    public void help() {
        System.out.println("- pwd: Muestra cual es la carpeta actual.\n"
                + "- cd <DIR>: Cambia la carpeta actual a ‘DIR’. Con .. cambia a la carpeta superior.\n"
                + "- ls: Muestra la lista de directorios y archivos de la carpeta actual (primero directorios, luego archivos, ambos ordenados alfabéticamente).\n"
                + "- ll: Como ls pero muestra también el tamaño y la fecha de última modificación.\n"
                + "- mkdir <DIR>: Crea el directorio ‘DIR’ en la carpeta actual.\n"
                + "- rm <FILE>: Borra ‘FILE’. Si es una carpeta, borrará primero sus archivos y luego la carpeta. Si tiene subcarpetas, las dejará intactas y mostrará un aviso al usuario.\n"
                + "- mv <FILE1> <FILE2>: Mueve o renombra ‘FILE1’ a ‘FILE2’.\n"
                + "- help: Muestra una breve ayuda con los comandos disponibles.\n"
                + "- exit: Termina el programa.\n"
                + "------------------------------------------------------------------------------------------------------");
    }

}
