/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author me-aydin
 */
public class Clustering {

    /**
     * @param args the command line arguments
     */
    
    public void createMatrix(String fileName){
                
        Random rand = new Random();
        int matrix[][] = new int[15][15];

        for (int i = 0; i < matrix.length; i++) {
                // do the for in the row according to the column size
                for (int j = 0; j < matrix[i].length; j++) {
                        // multiple the random by 10 and then cast to in
                        matrix[i][j] = rand.nextInt(2);;
                       // System.out.print(matrix[i][j] + "\t");
                }
                // add a new line
               // System.out.println();
        }
        //System.out.println("Done");
        try {
                FileWriter writer = new FileWriter(fileName, false);
                for (int i = 0; i < matrix.length; i++) {
                        for (int j = 0; j < matrix[i].length; j++) {
                                writer.write(matrix[i][j] + "\t");
                        }
                        //this is the code that you change, this will make a new line between each y value in the array
                        writer.write("\n"); // write new line
                }
                writer.close();
        } catch(IOException e) {
                e.printStackTrace();
        }
    }
    
     public static List readFile(String fileName){
        
        //String fileName="/Users/AmirulHaziq/Desktop/Functions.txt";
        List list = new ArrayList();
        
        try{ 
            Scanner scn = new Scanner(new BufferedReader(new FileReader(fileName)));

            while (scn.hasNextLine()) {
                String line = scn.nextLine(); 
                String[] numbers = line.trim().split("\t");
                List inList = new ArrayList();

                for (int i = 0; i < numbers.length; i++) {
                    //String temp = numbers[i];
                    inList.add(Integer.parseInt(numbers[i].trim())); 
                }

                list.add(inList);
            }
           
        }
        catch(Exception e){
            System.out.println("Error while reading file line by line:" + e.getMessage());                      
        } 
        
        return list;
    } 
    public static void main(String[] args) {
        //String fileName = "/Users/me-aydin/Downloads/VNS/Functions.txt";
        String fileName = "/Users/me-aydin/Downloads/VNS/CaseHam.txt";
       // Clustering cl = new Clustering();
        List dependency = Clustering.readFile(fileName);
     /*   
        for (int i=0;i<dependency.size();i++){
            List tmp = (List) dependency.get(i);
            for(int j=0;j<dependency.size();j++){
                    System.out.print(tmp.get(j)+" ");
            }
            System.out.println();
        }
      */  
        VNS vns = new VNS(33, 33);
        vns.setDependency(dependency);
        vns.algorithm();
        
        // TODO code application logic here
    }
    
}
