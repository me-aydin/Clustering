/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author me-aydin
 */
public class VNS {
    private List dependency;
    private List best;
    private Operations operation;
    
    public VNS(int n, int c){
        //dependency = new ArrayList();
        best = new ArrayList();
        
        operation = new Operations();
        operation.init(n, c);
        best = operation.getString();
        
     //   System.out.println("Constructor");
    }
    public void setDependency(List d){
        dependency = d;
     //   System.out.println("Dependency");
    }
    
    public void shake(){
        operation.setString(operation.mutate());
       // operation.setString(operation.twoOpt());
      //  System.out.println("Shake");
    }
    
    public void move(){
        List temp ;//= new ArrayList();
        double fT = 1000.0, fC = 1000.0; 
        /**/
        if ((new Random()).nextInt(100) >= 40)
            temp = operation.twoOpt();
            //temp = operation.mutate();
        else
            temp = operation.swap();
        
        fT = fitness(temp);
        fC = fitness(operation.getString());

        if (fT < fC) {
            operation.setString(temp);
            if (fT <= fitness(best)){
                best = operation.cloneList(temp);
                System.out.printf("%.2f\n",fitness(best));
            }
        }
       // System.out.println("Move");
    }
    public boolean checkCons(List list){
        boolean bool = true;
        
        for(Object i:list){
           for(Object j:(List)list){
               if((int)i!=(int)j)
                   return false;
            } 
        }       
        return bool;
    }
    
    public boolean checkClusters(List list){
        boolean bool = true;
        int count = 0;
        
        for(int i=0;i<(list.size()-1);i++)
            for(int j=i+1;j<list.size();j++){
                if(list.get(i)!=list.get(j)) 
                    count++;
            }
        if (count<3)
            bool = false;
        return bool;
    }
    public double checkFlatness(List list){
        double flatness = 0.0;
        int total = 0;
        double avrg = 0.0, diff = 0.0;
        
        final int C = operation.getCluster();
        int[] count = new int[C];
        for(int i=0;i<count.length;i++)
            count[i] = 0;
        
    //    List std = new ArrayList();
        
        for(int i=0;i<(list.size()-1);i++)
            for(int j=i+1;j<list.size();j++){
                if(list.get(i)!=list.get(j)) 
                    count[(int)list.get(i)]++;
            }
        
        for(int i=0;i<count.length;i++) 
            total += count[i];
        avrg = (double)total/(double)count.length;
        
        for(int i=0;i<count.length;i++) 
            diff += Math.pow((double)(avrg - count[i]), (double)2.0);
        flatness = Math.sqrt(diff)/(double)count.length;
        
        //System.out.println(flatness);
        return flatness;
    }
    
    public double fitness(List list){
        double fitness = 1000.0;
        double coh = 0.0, cpl = 0.0;
        
       // operation.display(list);
        for (int i=0;i<list.size();i++) {
            List tmp = (List) dependency.get(i);
           // operation.display(tmp);
            for(int j=0;j<list.size();j++){
                //int num = (int)tmp.get(j);
                //double buff = (num>0)?(1.0/(double)num):0.0;
                
                if (list.get(i)==list.get(j))
                    coh += (double)((int)tmp.get(j));//buff;
                else 
                    cpl += (double)(int)tmp.get(j);//buff;
            }
        }
        //fitness = (double)cpl/(double)(cpl+list.size());
        fitness = (double)(cpl/(double)(cpl+list.size())*0.5 - 0.5*(double)coh/100.0); //1/(double)coh);
        //fitness = (double)(cpl/(double)(cpl+list.size()) + 1/(double)coh) + checkFlatness(list)*operation.getCluster()/(double)list.size();
       /* 
       if(checkCons(list)){
            fitness += 10.0;
            operation.setFitness(fitness);
        }
       if(!checkClusters(list)){
            fitness += 10.0;
            operation.setFitness(fitness);
        }
       */ 
     //   System.out.print("cpl ="+cpl + "\t coh = "+coh+"\t");
     //   System.out.printf("%s = %.2f\n","fitness", fitness);
        
        return fitness;
    }
    
    public void algorithm(){
        final int OuterL = 500;
        final int InnerL = 1000;
        
        double fB = 1000.0, fC = 1000.0;
        
        for(int i=0;i<OuterL;i++) {
            shake();
            fB = fitness(best);
            fC = fitness(operation.getString());
   
            operation.setFitness(fC);
            
            if (fC <= fB) {
                best = operation.cloneList(operation.getString());
                System.out.printf("%.4f\t",fitness(best));
                operation.display(best);
            }
            
            for(int j=0;j<InnerL;j++)     move();
        }
        operation.display(best);
        
        System.out.println("Final="+fitness(best));       
    }
    
    public static void main(String [] str){
        String fileName = "/Users/me-aydin/Downloads/VNS/CaseHam.txt";
        List dependency = Clustering.readFile(fileName);
        
        VNS vns = new VNS(33, 33);
        vns.setDependency(dependency);
             /*   
        for (int i=0;i<dependency.size();i++){
            List tmp = (List) dependency.get(i);
            for(int j=0;j<dependency.size();j++){
                    System.out.print(tmp.get(j)+" ");
            }
            System.out.println();
        }
       */ 

        
        int [] st = {0,0,3,3,3,0,0,4,4,4,4,1,1,1,1,0,1,1,1,1,2,2,2,2,2,3,3,3,3,3,3,0,1};
        List myList = Arrays.asList(st);
        System.out.println(vns.fitness(myList));
        
    }
}
