# Santa-Claus-with-Semaphores

# The goal is to:

•	Implement deferred termination of threads from scratch
•	Use semaphores 

The only synchronization primitive allowed is java.util.concurrent.Semaphore and its acquire() and release() functions (which correspond to wait() and signal() in other    semaphore implementations,.

# The problem:

Santa Claus sleeps in his shop at the North Pole and can only be woken by either:

•	All nine reindeer being back from their vacation in the South Pacific or
•	Some of the elves having difficulties making toys.

To allow Santa to get some sleep, the elves can only wake him when three of them are having problems. When three elves are having their problems solved, any other elves wishing to visit Santa must wait for those elves to return. It is assumed that the reindeer do not want to leave the tropics, and therefore stay there until the last possible moment – they only return in December. The last reindeer to arrive must get Santa while the others wait in a warming hut before being harnessed to the sleigh. 


# Step 1: 

Observe that the threads corresponding to Santa, the reindeer and the elves never terminate. Implement a way to terminate these threads at day 370 using deferred termination (i.e. do not kill the threads explicitly). 

Note: the main thread with the counting of the days will continue, that is fine. 

# Step 2: 

Starting from step 1, create a version where:

-there is no reindeer present
-as soon as an elf runs into a problem, it goes to Santa’s door
-as soon as an elf is at Santa’s door and Santa is sleeping, he wakes up Santa
-if woken up, Santa solves the problems of all elves who are at the door.

# Step 3: 

Starting from step 2, create a version where

-there is no reindeer present
-unless there are three elves which are in TROUBLE, the elves stay in TROUBLE. When the third elf gets in TROUBLE, they go to Santa’s door. 
-if there is any elf at Santa’s door, the elves who get into TROUBLE, they stay in TROUBLE, and only go to Santa’s door when the previous elves came back. 
 -as soon as an elf is at Santa’s door and Santa is sleeping, he wakes up Santa
-if woken up, Santa solves the problems of the three elves who are at the door.

# Step 4: 

Now, notice that Step 3 still did not use any synchronization primitives – even when in TROUBLE or at Santa’s door, the elf threads are spinning. 

Using semaphores, create a new version starting from the code from Step 3 in such a way that the threads of the Elves are waiting in the acquire() function of a semaphore when they are in the TROUBLE mode. 

# Step 5: 

Now, bring in the reindeers, and implement the code necessary such that:

-the first 8 reindeers to come back from BEACH stay at the WARMING_SHED. 
-the reindeers in the WARMING_SHED are in the waiting in the acquire() function of a semaphore
-the last reindeer wakes up Santa 
-Santa hooks up the reindeers to the SLEIGH and wakes their thread up


