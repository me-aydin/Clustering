/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author me-aydin
 */
public class Operations {
    private List string;
    private double fitness;
    private int cluster;
    
    public Operations(){
        string = new ArrayList();
        fitness = 0.0;
        cluster = 1;
    }
    public void init(int n, int c){
        Random rand = new Random();
        cluster = c;
        for(int i=0;i<n;i++){
            int t = rand.nextInt(cluster);
            string.add(t);
        }
    }
    
    public void setString(List l){
        Iterator it = l.iterator();
        
        if (string.isEmpty()){
            while(it.hasNext())
                string.add(it.next());
        }
        else {
            int i = 0;
            while(it.hasNext()){
                string.set(i++, it.next());
            }
        }
    }
    
    public List getString(){
        return string;
    }
    
    public void setFitness(double f) {
        fitness = f;
    }
    
    public double getFitness(){
        return fitness;
    }
    
    public int getCluster(){
        return cluster;
    }
    
    public List cloneList(List str){
        List nStr = new ArrayList();
        Iterator it = str.iterator();
        
        if (!str.isEmpty()){
            while(it.hasNext())
                nStr.add(it.next());
        }

        return nStr;
    }
    public List mutate(){
        List nStr = cloneList(string);
        
        int m = (new Random()).nextInt(string.size());
        
        if (!nStr.isEmpty()){
            int buff = (new Random()).nextInt(cluster);
            nStr.set(m, buff);
        }
        return nStr;
    }
    
    public List swap(){
        List nStr = cloneList(string);
        
        int m = (new Random()).nextInt(string.size());
        int n = (new Random()).nextInt(string.size());
        
        if (!nStr.isEmpty()){
            int first = (int)nStr.get(m);
            int second = (int)nStr.get(n);
            nStr.set(m, second);
            nStr.set(n, first);
        }
        return nStr; 
    }
    
    public List twoOpt(){
        List nStr = cloneList(string);

        int base = (nStr.size()/2);
        int tenth = nStr.size()/10;
                
        int m = tenth + (new Random()).nextInt(base - tenth);
        int n = (base) + (new Random()).nextInt(base - tenth );
        int i = m, j = n;
        while(i<=n && j>=m){
            nStr.set(i++, string.get(j--));
        }
        
        return nStr;     
    }
    
    public void display(List l){       
        if (l.isEmpty())
            System.out.println("This list is EMPTY");
        else {
            Iterator it = l.iterator();
 
            while(it.hasNext())
                System.out.print(it.next().toString()+ " ");
            
            System.out.println();
        }
    }
 /*   
    public static void main(String[] str){
        Operations sl = new Operations();
        sl.init(15, 5);
        sl.display(sl.getString());
        sl.display(sl.mutate());
        sl.display(sl.swap());
        sl.display(sl.twoOpt());
        
    }
    */
}