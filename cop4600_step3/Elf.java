package cop4600_santa;

import java.util.Random;

public class Elf implements Runnable{
	
	enum ElfState {WORKING, TROUBLE, AT_SANTAS_DOOR};
	
	private ElfState state;
	private Boolean terminateElf = false;
	private Boolean atSantasDoor = false;

	/**
	 * The number associated with the Elf
	 */
	private int number = 0;
	private int numTrouble = 0;
	private Random rand = new Random();
	private SantaScenario scenario;

	public Elf(int number, SantaScenario scenario) {
		this.number = number;
		this.scenario = scenario;
		this.state = ElfState.WORKING;
	}


	public ElfState getState() {
		return state;
	}

	/**
	 * Santa might call this function to fix the trouble
	 * @param state
	 */
	public void setState(ElfState state) {
		this.state = state;
	}


	@Override
	public void run() {
		
		while (!terminateElf) {
      // wait a day
  		try {
  			Thread.sleep(100);
  		} catch (InterruptedException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
			switch (state) {
				case WORKING: {
					// at each day, there is a 1% chance that an elf runs into
					// trouble.
					if (rand.nextDouble() < 0.01) {
						this.state = ElfState.TROUBLE;
						this.scenario.troubledElves.add(this);
					}
					break;
				}
				case TROUBLE:
					// FIXME: if possible, move to Santa's door
					
					numTrouble = this.scenario.troubledElves.size();
					
					if(numTrouble > 2 && scenario.santasDoor == true) {
						/*PASS ALL TROUBLE ELVES AT SANTA DOOR*/
						for(Elf elf: scenario.elves) {
							if(elf.state == ElfState.TROUBLE) {
								this.scenario.troubledElves.remove(elf);
								scenario.atSantasDoor.add(elf);
								elf.state = ElfState.AT_SANTAS_DOOR;
							}
						}
						
						if(scenario.troubledElves.size() == 0)
							this.scenario.santasDoor = false;
					}
					
					if(scenario.troubledElves.size() > 2)
						this.scenario.santasDoor = true;
						
					break;
				case AT_SANTAS_DOOR:
					// FIXME: if feasible, wake up Santa
						scenario.santa.wakeUpSanta();
					break;
			}
		}
	}

	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Elf " + number + " : " + state);
	}
	
	/*Step1: will used to terminate after day 370*/
	public void terminate() {
		this.terminateElf = true;
	}

	/*Step2: will be used by Santa to check if elf needs help*/
	public Boolean atSantasDoor() {
		return this.atSantasDoor;
	}
}
