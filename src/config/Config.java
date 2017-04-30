/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package config;

/**
 * Configuration class for the application
 * @author nicomp
 *
 */
public class Config {
	private int numOfClients;
	
	public Config(int numOfClients) {
		this.numOfClients = numOfClients;
	}

	public int getNumOfClients() {
		return numOfClients;
	}

	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
}
