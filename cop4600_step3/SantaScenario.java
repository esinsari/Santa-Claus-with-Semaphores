package cop4600_santa;

import java.util.ArrayList;
import java.util.List;

public class SantaScenario {
	
	public Santa santa;
	public List<Elf> elves;
	public List<Reindeer> reindeers;  
	public List<Elf> troubledElves;    /*Step3*/
	public List<Elf> atSantasDoor; /*Step3*/
	public boolean santasDoor = false;
	public boolean isDecember;		 
	
	
	public static void main(String args[]) {
		
		SantaScenario scenario = new SantaScenario();
		scenario.isDecember = false;
		// create the participants		
		// Santa
		scenario.santa = new Santa(scenario);
		
		Thread th = new Thread(scenario.santa);
		
		th.start();
		
		/*Step3*/
		scenario.troubledElves = new ArrayList<>();
        scenario.atSantasDoor = new ArrayList<>();
        
		// The elves: in this case: 10
		scenario.elves = new ArrayList<>();
		
		for(int i = 0; i != 10; i++) {
			Elf elf = new Elf(i+1, scenario);
			scenario.elves.add(elf);
			th = new Thread(elf);
			th.start();
		}
		
		// The reindeer: in this case: 9
		scenario.reindeers = new ArrayList<>();
		
		for(int i=0; i != 9; i++) {
			Reindeer reindeer = new Reindeer(i+1, scenario);
			scenario.reindeers.add(reindeer);
			th = new Thread(reindeer);
			th.start();
		}
		
		// now, start the passing of time
		for(int day = 1; day < 500; day++) {
			// wait a day
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// turn on December
			if (day > (365 - 31)) {
				scenario.isDecember = true;
			}
			
			if(day == 370) {
				/*Terminate all elves*/
				for(int i = 0; i != 10; i++) {
					scenario.elves.get(i).terminate();
				}
				/*Terminate all reindeers*/
				for(int i = 0; i != 9; i++) {
					scenario.reindeers.get(i).terminate();
				}
				/*Terminate santa*/
				scenario.santa.terminate();
				
			}
			
			
			// print out the state:
			System.out.println("***********  Day " + day + " *************************");
			
			scenario.santa.report();
			
			for(Elf elf: scenario.elves) {
				elf.report();
			}
			
			for(Reindeer reindeer: scenario.reindeers) {
				reindeer.report();
			}
		}
	}

}
