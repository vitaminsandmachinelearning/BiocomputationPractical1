package genetic32;

import java.util.Random;
import java.util.Scanner;

public class Genetic32 {

    static Random r = new Random();
    
    static int popsize = 4;
    static int poplength = 5;
    
    static char[][] population = new char[popsize][poplength];
    static int[] populationValues = new int[popsize];
    static char[][] matingPopulation = new char[popsize][poplength];
    
    static int maxValues = 0;
    static float mutationchance;

    public static void main(String[] args) {
        mutationchance = (1f / popsize + 1f / poplength) / 2f;
        genPop();
        for(int i = 0; i < 300; i++)
        {
            genValues();
            printPop();
            buildPool();
            genValues();
            printPop();
            crossPool();
            genValues();
            printPop();
            mutatePool();
            genValues();
            printPop();
            System.out.println("-----------------------");
            
        }
    }
    
    static void genPop()
    {
        System.out.println("Generating population");
        for(int i = 0; i < population.length; i++)
            for(int j = 0; j < poplength; j++)
                population[i][j] = r.nextBoolean() ? '1' : '0';
    }
    
    static void genValues()
    {
        System.out.println("Assigning values to population");
        maxValues = 0;
        for(int i = 0; i < population.length; i++)
        {
            populationValues[i] = (int) Math.pow(Integer.parseInt(new String(population[i]), 2), 2);    
            maxValues += populationValues[i];
        }
    }
    
    static void buildPool()
    {
        System.out.println("Building mating pool");
        int sum = 0;
        for(int m = 0; m < matingPopulation.length; m++)
        {
            sum = 0;
            for(int i = 0; i < population.length; i++)
            {
                sum += populationValues[i];
                if(r.nextInt(maxValues) < sum)
                {
                    matingPopulation[m] = population[i];
                }
            }
        }
    }
    
    static void crossPool()
    {
        System.out.println("Crossing mating pool");
        int[] indexes = {r.nextInt(popsize), r.nextInt(popsize)};
        int point = r.nextInt(poplength - 1);
        char t;
        for(int i = point; i < poplength; i++)
        {
            t = population[indexes[0]][i];
            population[indexes[0]][i] = population[indexes[1]][i];
            population[indexes[1]][i] = t;
        }
    }
    
    static void mutatePool()
    {
        System.out.println("Mutating mating pool");
        for(int i = 0; i < population.length; i++)
        {
            int s = r.nextInt(poplength);
            if(r.nextFloat() < mutationchance)
            {
                population[i][s] = population[i][s] == '0' ? '1' : '0';
                System.out.println("mutated member: " + (i + 1) + " at position: " + (s + 1));
            }
        }
    }
    
    static void printPop()
    {
        for(int i = 0; i < population.length; i++)
        {
            for(char c : population[i])
                System.out.print(c);
            System.out.println(" " + populationValues[i]);
        }
        System.out.println();
    }
}
