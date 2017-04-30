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
	private static final String dataFolder = "data";
	
	public Config(int numOfClients) {
		this.numOfClients = numOfClients;
	}

	public int getNumOfClients() {
		return numOfClients;
	}

	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
	
	/**
	 * Adjust a file path so it contains the data folder
	 * @param fileName The file name, no path
	 * @return The file name with the data folder added to it
	 */
	public static String addPathToDataFileName(String fileName) {
		return dataFolder + "\\" + fileName;
	}
	
}
