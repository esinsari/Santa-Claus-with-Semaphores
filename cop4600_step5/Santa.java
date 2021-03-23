package cop4600_santa;

import cop4600_santa.Elf.ElfState;
import cop4600_santa.Reindeer.ReindeerState;

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
	
	/*Step 5*/
	public SantaState getState() {
		return state;
	}

	/**
	 * Santa might call this function to fix the trouble
	 * @param state
	 */
	public void setState(SantaState state) {
		this.state = state;
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
					
					/*Step 3*/
					try{

						for (Elf elf : scenario.atSantasDoor)
						{
							elf.setState(ElfState.WORKING);
							
						}
						
						scenario.atSantasDoor.clear();
						
					}catch(java.util.ConcurrentModificationException e){
						
					}
						 				
					this.state = SantaState.SLEEPING;
					
					/*Step4*/
					scenario.santaSemaphore.release();
							
					break;
				case WOKEN_UP_BY_REINDEER: 
					// FIXME: assemble the reindeer to the sleigh then change state to ready 
					for(Reindeer reindeer: scenario.reindeers) {
			            reindeer.setState(ReindeerState.AT_THE_SLEIGH);
			            scenario.reindeerSemaphore.release();
			        }
					
					this.state = SantaState.READY_FOR_CHRISTMAS;
					
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
	public void wakeUpSantaElves() {
		if(state == SantaState.SLEEPING) {
			this.state =  SantaState.WOKEN_UP_BY_ELVES;
		}
	}
	
	/*Step5: will be used by elves to wake Santa in case of trouble*/
	public void wakeUpSantaReindeers() {
		if(state == SantaState.SLEEPING) {
			this.state =  SantaState.WOKEN_UP_BY_REINDEER;
		}
	}
	
}
