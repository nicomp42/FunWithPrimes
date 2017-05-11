/*
 * Optimization Notes
 *    An int array appears to be quite a bit faster than a byte array
 * 
 * 
 * 
 * 
 * Test runs: 
		CustomBigInt.justCount(): Counting with 12 digits...
		LKP.justCount(): Total execution time to count: 26.885083333333334 minutes.
		
		CustomBigInt.justCount(): Counting with 11 digits...
		CustomBigInt.justCount(): Total execution time to count: 2.5260499999999997 minutes.
		
		count with an int to (2^31 -1) = 0.014266666666666667 minutes = .85 seconds
		
		count with an int to 999,999,999 = 6.666666666666667E-5 minutes = .004 seconds
		
 */

package whoa;

import java.util.Arrays;

import config.Debug;

public class CustomBigInt {
	
	private int length;
	int  digits[];		// byte bytes[]
	boolean verbose = true;
	
	public CustomBigInt(int length, boolean terse) {
		setLength(length);
		setTerse(terse);
		digits =	new int[length];			// new byte[length];
		for (int i = 0; i < length; i++) {
			digits[i] = 0;
		}
	}

	public void setTerse(boolean terse) {this.verbose = terse;}
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * This method counts to 99999 if length is 5, so it's closer to a count to (length-1) digits than length digits
	 */
	public void justCountVersion02() {
		int overflowLimit = 999_999_999;
		boolean printTheNumber = true;
		int counter = 1;
		long startTime = System.currentTimeMillis();
		long intervalStop, intervalStart = startTime;
		System.out.println("CustomBigInt.justCountVersion02(): Counting with " + length + " digits...");
		int i = length - 1;
		int tmp;			//byte tmp;
		int digitsUsed = 0;
		boolean keepGoing = true;
		int[] num = new int[length/12];		// one integer per 12 digits
		try {
			while (keepGoing == true) {
			    i = num.length - 1;
				while (true) {
//					printNumber(num);
					num[i]++;
					if (num[i] > overflowLimit) {		// Overflow. "Carry the 1"
						num[i] = 0;
						i--;
						if (i < 0) {keepGoing = false; break;}	// We overflowed the last set of 12 digits
						for (int j = i; j >= 0; j--) {
							num[j]++;
							if (num[j] > overflowLimit) {
								num[j] = 0;
							} else {
								break;
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			// We should end up here when the number overflows and the value of the index becomes -1
			keepGoing = false;
		}
		long endTime = System.currentTimeMillis();
		System.out.println("CustomBigInt.justCountVersion02: Total execution time to count: " + ((double)((endTime - startTime))/1000)/60 + " minutes." );
		if (verbose) {
			printNumber(num);
		}
	}
	
	private void printNumber(int[] num) {
		for (int k = 0; k < num.length; k++) {
			System.out.print(String.format("%09d",num[k]));
		}
		System.out.println();
	}
	
	private String IntArrayToString(int[] num) {
		String tmp = "";
		for (int k = 0; k < num.length; k++) {
			tmp += String.format("%09d",num[k]);
		}
		return tmp;
	}
	
	
	/**
	 * This method counts to 99999 if length is 5, so it's closer to a count to (length-1) digits than length digits
	 */
	public void justCount() {	
		// This is quite a bit faster than the other Big Integer counting tests.
		// It works out to about .06 minutes per iteration of 2^31 counts, even with 22 million digits.
		boolean printTheNumber = true;
		int counter = 1;
		long startTime = System.currentTimeMillis();
		long intervalStop, intervalStart = startTime;
		System.out.println("CustomBigInt.justCount(): Counting with " + length + " digits...");
		int i = length - 1;
		int tmp;			//byte tmp;
		int digitsUsed = 0;
		boolean keepGoing = true;
		try {
			while (keepGoing == true) {
			    if (Debug.ON) {		// Conditional Compilation!
					counter++;
					if (counter < 0) {
						intervalStop = System.currentTimeMillis();
						counter = 0;
						if (verbose) {
							System.out.print(" digits : " + Arrays.toString(digits).replaceAll(", ", ""));
						}
						digitsUsed = countDigitsUsed();
						System.out.println(digitsUsed + " digits used, " + ((double)((intervalStop - intervalStart))/1000)/60 + " minutes." );
						intervalStart = intervalStop;	
					}
			    }
				i = length - 1;
				while (true) {	
					tmp = digits[i];
					tmp++;
					if (tmp > 9) {		// Overflow. "Carry the 1"
						tmp = 0;
						digits[i] = tmp;
						i--;
					} else {
						digits[i] = tmp;
						break;
					}
				}			
			}
		} catch (Exception ex) {
			// We should end up here when the number overflows and the value of the index becomes -1
			keepGoing = false;
		}
		long endTime = System.currentTimeMillis();
		System.out.println("CustomBigInt.justCount(): Total execution time to count: " + ((double)((endTime - startTime))/1000)/60 + " minutes." );
		if (verbose) {System.out.println("CustomBigInt.justCount(): final number = " + Arrays.toString(digits).replaceAll(", ", ""));}
	}
	/**
	 * For development only. Do not use this during production runs.
	 * @return the number of digits in the number
	 */
	private int countDigitsUsed() {
		int i = 0;
		for (; digits[i] == 0; i++) {
			
		}
		return (length + 1) - (i + 1) ;
	}
}
