package cop4600_santa;

import cop4600_santa.Elf.ElfState;

//import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;

public class Santa implements Runnable {
	
	enum SantaState {SLEEPING, READY_FOR_CHRISTMAS, WOKEN_UP_BY_ELVES, WOKEN_UP_BY_REINDEER};
	private SantaState state;
	private SantaScenario scenario;
	private Boolean terminateSanta = false;

	
	public Santa(SantaScenario scenario) {
		this.state = SantaState.SLEEPING;
		this.scenario = scenario;
	}

	
	@Override
	public void run() {
		
		while(!terminateSanta) {
			// wait a day...
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(state) {
				case SLEEPING: // if sleeping, continue to sleep
					break;
				case WOKEN_UP_BY_ELVES: 
					// FIXME: help the elves who are at the door and go back to sleep 
					
					/*Step2: loop through all elves and if they are at door, fixes their problem*/
					for(Elf elf: scenario.elves) {
						if(elf.atSantasDoor()) {
							elf.setState(ElfState.WORKING);
						}
					}
			
					this.state = SantaState.SLEEPING;
					
					break;
				case WOKEN_UP_BY_REINDEER: 
					// FIXME: assemble the reindeer to the sleigh then change state to ready 
					break;
				case READY_FOR_CHRISTMAS: // nothing more to be done
					break;
			}
		}
	}

	
	/**
	 * Report about my state
	 */
	public void report() {
		System.out.println("Santa : " + state);
	}
	
	/*Step1: will used to terminate after day 370*/
	public void terminate() {
		this.terminateSanta = true;
	}
	
	/*Step2: will be used by elves to wake Santa in case of trouble*/
	public void wakeUpSanta() {
		if(state == SantaState.SLEEPING) {
			this.state =  SantaState.WOKEN_UP_BY_ELVES;
		}
	}
	
}
