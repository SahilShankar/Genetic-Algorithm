package generation;

import java.util.Random;
public class Generation {
	Population population = new Population(10);
	Individual fittest;
	Individual secondFittest;
	int generationCount = 0;
	public static void main(String[] args) {
		Generation gen = new Generation();
		Random ran = new Random();
        gen.population.calculateFitness();
        System.out.println("Generation: " + gen.generationCount + " Fittest: " + gen.population.fittest);
        while (gen.population.fittest < 5) {
            ++gen.generationCount;
            gen.selection();
            gen.crossover();
            if (ran.nextInt()%7 < 5)
                gen.mutation();
            gen.addFittestOffspring();
            gen.population.calculateFitness();
            System.out.println("Generation: " + gen.generationCount + " Fittest: " + gen.population.fittest);
        }

        System.out.println("\nSolution in generation " + gen.generationCount);
        System.out.println("Fitness: "+gen.population.getTheFittest().fitness);
        System.out.print("Genes: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(gen.population.getTheFittest().genes[i]);
        }

        System.out.println("");

    }

    //Selection
    void selection() {
        fittest = population.getTheFittest();
        secondFittest = population.getTheSecondFittest();
    }

    //Crossover
    void crossover() {
        Random rn = new Random();
        int crossOverPoint = rn.nextInt(population.individuals[0].genes.length);
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;
        }
    }

    //Mutation
    void mutation() {
        Random rn = new Random();
        int mutationPoint = rn.nextInt(population.individuals[0].genes.length);
        if (fittest.genes[mutationPoint] == 0)
            fittest.genes[mutationPoint] = 1;
        else 
            fittest.genes[mutationPoint] = 0;
        mutationPoint = rn.nextInt(population.individuals[0].genes.length);
        if (secondFittest.genes[mutationPoint] == 0)
            secondFittest.genes[mutationPoint] = 1;
        else 
            secondFittest.genes[mutationPoint] = 0;
    }

    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness)
            return fittest;
        return secondFittest;
    }

    void addFittestOffspring() {
        fittest.calcFitness();
        secondFittest.calcFitness();
        int leastFittestIndex = population.getTheLeastFittestIndex();
        population.individuals[leastFittestIndex] = getFittestOffspring();
	}

}

class Individual{
	int fitness;
	int[] genes = new int[5];
	
	public Individual(){
		fitness = 0;
		Random ran = new Random();
		for(int i=0;i<genes.length;i++)
			genes[i] = Math.abs((ran.nextInt()%2));
	}
	
	void calcFitness() {
		fitness = 0;
		for(int i=0;i<genes.length;i++) {
			if(genes[i] == 1)
				++fitness;
		}
	}
}

class Population{
	int populationSize;
	Individual[] individuals;
	int fittest;
	
	public Population(int size) {
		this.populationSize = size;
		individuals = new Individual[size];
		for(int i=0;i<individuals.length;i++)
			individuals[i] = new Individual();
		fittest = 0;
	}
	
	public Individual getTheFittest() {
		int max = Integer.MIN_VALUE;
		int maxIndex = 0;
		for(int i=0;i<individuals.length;i++) {
			if(max<=individuals[i].fitness) {
				max = individuals[i].fitness;
				maxIndex = i;
			}
		}
		fittest = individuals[maxIndex].fitness;
		return individuals[maxIndex];
	}
	
	public Individual getTheSecondFittest() {
		int max1 = 0,max2 = 0;
		for(int i=0;i<individuals.length;i++) {
			if(individuals[i].fitness > individuals[max1].fitness) {
				max2 = max1;
				max1 = i;
			}
			else if(individuals[i].fitness < individuals[max1].fitness) {
				max2 = i;
			}
		}
		return individuals[max2];
	}
	
	public int getTheLeastFittestIndex() {
        int minFitnessValue = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (minFitnessValue >= individuals[i].fitness) {
                minFitnessValue = individuals[i].fitness;
                minIndex = i;
            }
        }
        return minIndex;
	}
	
	  public void calculateFitness() {

	        for (int i = 0; i < individuals.length; i++) {
	            individuals[i].calcFitness();
	        }
	        getTheFittest();
	    }
}
