/*
CASO PRÁCTICO A - MiniTerminalCopia & MiniFileManager
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

Clase MiniTerminalCopia: Clase principal (con función main) que se encargará de interactuar con el
usuario e interpretar los comandos (qué comando se pide, argumentos, etc.). Utilizará la segunda
clase para realizar las operaciones de gestión de archivos. Manejará todas las posibles excepciones.

Clase MiniFileManager: Tendrá los atributos y métodos que necesites, para realizar las distintas
operaciones relacionadas con la gestión de archivos. Necesitarás al menos un método por cada
operación. Se lanzará una excepción si se produce un error o la operación solicitada no es posible.
Algunos ejemplos que podrías implementar:
- String getPWD(): Devuelve una cadena de texto con la carpeta actual.
- boolean changeDir(String dir): Cambia la carpeta actual a dir. Devuelve ‘true’ si fue posible.
- void printList(boolean info): Muestra una lista con los directorios y archivos de la carpeta
actual. Si info es ‘true’ mostrará también su tamaño y fecha de modificación.
 */
package MiniTerminalCarpeta;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MiniTerminal {

    public static Scanner lector = new Scanner(System.in);

    public static void main(String[] args) {
        String ruta;
        String comando = "";
        boolean comprobarRuta = false;

        System.out.println("Introduzca una direccion inicial");

        while (comprobarRuta == false) {
            
            ruta = lector.nextLine();
            File f = new File(ruta);

            if (f.exists()) {
                MiniFileManager funciones = new MiniFileManager(f);

                while (!comando.equals("exit")) {
                    System.out.println("+===============================================MINITERMINAL=============================================+");
                    funciones.help();

                    //lector de comandos
                    String ruta1 = "";
                    String ruta2 = "";

                    comando = lector.next();            //primera entrada                                          

                    if (comando.equals("cd") || comando.equals("mkdir") || comando.equals("rm")) {
                        ruta1 = lector.next();
                    } else if (comando.equals("mv")) {
                        ruta1 = lector.next();
                        ruta2 = lector.next();
                    }

                    switch (comando.toLowerCase()) {
                        case "pwd":  //--------------------------------------------
                            System.out.println(funciones.getPWD());
                            break;
                        case "cd":  //--------------------------------------------
                            System.out.println("¿Se ha movido? " + funciones.changeDir(ruta1));
                            break;
                        case "ls":  //--------------------------------------------
                    try {
                            if (f.isFile()) {
                                funciones.infoArchivos(false);           //si es un archivo tiene que escribirse con la extension
                            } else if (f.isDirectory()) {
                                funciones.printList(false);
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                        case "ll":  //--------------------------------------------
                    try {
                            if (f.isFile()) {
                                funciones.infoArchivos(true);           //si es un archivo tiene que escribirse con la extension
                            } else if (f.isDirectory()) {
                                funciones.printList(true);
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                        case "mkdir": //--------------------------------------------
                            System.out.println("¿Se ha creado la carpeta? " + funciones.createDir(ruta1));
                            break;
                        case "rm":    //--------------------------------------------
                    try {
                            System.out.println("¿Se ha borrado la carpeta? " + funciones.deleteDir(ruta1));
                        } catch (FileNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                        case "mv":  //--------------------------------------------
                            System.out.println("¿Se ha movido/renombrado la carpeta? " + funciones.moveDir(ruta1, ruta2));
                            break;
                        case "help":  //--------------------------------------------
                            funciones.help();
                            break;
                    }
                }
            }else if(!f.exists()){
                System.out.println("La ruta no existe");
                System.out.println("Introduzca la ruta otra vez");
            }
        }
    }
}

//   C:\Users\DAW\Desktop\Documentos   C:\Users\DAW\Desktop\Documentos\frases.txt
