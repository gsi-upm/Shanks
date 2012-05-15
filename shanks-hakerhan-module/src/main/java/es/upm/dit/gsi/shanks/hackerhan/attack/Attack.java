package es.upm.dit.gsi.shanks.hackerhan.attack;

/**
 * Interface
 * An attack from the hacker
 * 
 */
public interface Attack {
	
	/**
	 * Runs the attack
	 */
	public void execute();
	
	/**
	 * Stops the attack();
	 */
	public void stop();
	
	/**
	 * Returns the number of steps the attack should last.
	 * @return int -the number of steps
	 */
	public int numberSteps();
	
	/**
	 * Returns true if the attack succeeded.
	 * 
	 * @return true - if the attack was successful.
	 */
	public boolean isSuccessful();
	
	
	/**
	 * Returns true if the attack is still running
	 * 
	 * @return true - if the attack is still running
	 */
	public boolean isRunning();
}
