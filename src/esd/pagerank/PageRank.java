/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esd.pagerank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PageRank {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        PageRank pr = new PageRank();
        System.out.println("Aqui"+pr.matrizM());
    }

    public ArrayList matrizM() throws FileNotFoundException, IOException {
        ArrayList<String> info = new ArrayList();
        int cont = 0;

        File dir = new File("C:\\Users\\Javier\\Documents\\NetBeansProjects\\Sistemas Operativos\\ESD-PageRank\\prueba");
        String[] ficheros = dir.list();
        if (ficheros == null) {
            System.out.println("No hay ficheros en el directorio especificado");
        } else {
            for (int x = 0; x < ficheros.length; x++) {
                cont++;
                int conta = 0;

                System.out.println(cont);
                System.out.println(ficheros[x]);
                String archivo = "C:\\Users\\Javier\\Documents\\NetBeansProjects\\Sistemas Operativos\\ESD-PageRank\\prueba\\" + ficheros[x];
                String cadena;
                info.add(ficheros[x]+"@PR@1");
                FileReader f = new FileReader(archivo);
                BufferedReader b = new BufferedReader(f);
                ArrayList<String> contadores = new ArrayList();
                contadores.add(ficheros[x] + ":0");
                while ((cadena = b.readLine()) != null) {
                    System.out.println(cadena);
                    String[] aux = cadena.split("href=\"");

                    if (aux.length == 2) {
                        aux = aux[1].split("\"");
                        if (aux[0].trim().length() != 0) {
                            int tamano = contadores.size();
                            boolean bandera = true;
                            for (int i = 0; i < tamano; i++) {
                                String[] aux2 = contadores.get(i).split(":");
                                System.out.println(aux2[0] + " --- " + aux[0]);
                                if (aux2[0].equals(aux[0])) { //ya hay contador
                                    System.out.println("aux2[1]" + aux2[1]);
                                    int contaC = Integer.parseInt(aux2[1]);
                                    System.out.println(ficheros[x] + " equals " + aux[0]);
                                    if (!ficheros[x].equals(aux[0])) {
                                        contaC++;
                                    }
                                    //contadores.add(i, aux2[0] + ":" + contaC); Para que cuente los links
                                    if(contaC > 1){ //**
                                        contadores.add(i, aux2[0] + ":1"); //Solo aparecerá con un 1
                                    }else{
                                     contadores.add(i, aux2[0] + ":" + contaC); //Solo aparecerá con un 1 de referencia   
                                    }
                                     int auxiliar = i + 1;
                                    contadores.remove(auxiliar);
                                    bandera = false;
                                }
                            }
                            if (bandera) {
                                contadores.add(aux[0] + ":1");
                            }
                        }

                    }

                }

                int tamano2 = contadores.size();
                for (int i = 0; i < tamano2; i++) {
                    String[] aux2 = contadores.get(i).split(":");
                    String aux = info.get(cont - 1);
                    info.add(cont - 1, aux + ":" + aux2[0] + ":" + aux2[1]);
                    info.remove(cont);
                    System.out.println("link: " + aux2[0] + " contador: " + aux2[1]);
                }

                for (int i = 0; i < info.size(); i++) {
                    System.out.println("Arreglo " + info.get(i));
                }
             
                System.out.println("-----------------------------------");
                b.close();
            }
        }
        return info;
    }

}
