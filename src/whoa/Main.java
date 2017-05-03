/*
 * Bill Nichlson
 * nicholdw@ucmail.uc.edu
 * Some test primes: https://primes.utm.edu/lists/small/small.html
 * 
 * 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 
 * 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 
 * 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199
 * 
 */
package whoa;

import java.io.File;
import java.math.BigInteger;
import java.text.DecimalFormat;

import config.Config;

public class Main {

//	private static String testFileName = "M74207281";
//	private static String testFileName = "100DigitNOTPrime";
	private static String testFileName = "100DigitPrime";
//	private static String testFileName = "10DigitPrime";
//	private static String testFileName = "10DigitNOTPrime";
//	private static String testFileName = "20DigitPrime";

	public static void main(String[] args) {
		runTest(false);
	}
	/**
	 * Run a prime-ness test on a BigInt
	 * @param checkLength True if the method should compute and print the length of the BigInteger objects read from serialized files. 
	 */
	private static void runTest(boolean checkLength) {
		
		System.out.println("Test data will be taken from " + testFileName + "...");
		LKP myLKP = new LKP(testFileName);

		long startTime = System.currentTimeMillis();
		long endTime;
		try {
			myLKP.readSerializedBigIntegerFromDiskFile();
		} catch (Exception e) {
			// If we could not read the serialized number from disk, we probably need to create it.
			//e.printStackTrace();
			myLKP.loadLKPFromTextFile(testFileName + ".txt");
			System.out.println("Length of number = " + myLKP.getLengthOfNum());
			try {
				startTime = System.currentTimeMillis();
				myLKP.serializeTargetBigIntegerToDiskFile();
				endTime = System.currentTimeMillis();
				System.out.println("Total execution time to serialize BigInteger: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time to read serialized BigInteger: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		if (checkLength) {
			System.out.println("Checking length of the BigInteger we just read...");
			startTime = System.currentTimeMillis();
			System.out.println("Length of number = " + new DecimalFormat("#,###").format(myLKP.getLengthOfNum()));
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to check length of serialized BigInteger that we just read: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		}
		// If the square root does not exist in a serialized file, create it now.
		File f = new File(Config.addPathToDataFileName(testFileName + myLKP.squareRootSuffix + ".ser"));
		if(!f.exists()) {
			System.out.println("The file '" + testFileName + myLKP.squareRootSuffix + ".ser' does not exist. We will create it!");
			System.out.println("Computing and serializing square root of the BigInteger...");
			startTime = System.currentTimeMillis();
			BigInteger squareRoot = myLKP.sqrt();
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to compute square root: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
			System.out.println("Length of square root = " + new DecimalFormat("#,###").format(squareRoot.toString().length()));
			myLKP.setMyNum_SquareRoot(squareRoot);
			try {
				myLKP.serializeTargetBigIntegerSquareRootToDiskFile();
			} catch (Exception ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		} else {
			System.out.println("The file '" + testFileName + myLKP.squareRootSuffix + ".ser' DOES exist.");
			System.out.println("Reading serialized square root of the BigInteger...");
			startTime = System.currentTimeMillis();
			try {
				myLKP.readSerializedSquareRootFromDiskFile();
				endTime = System.currentTimeMillis();
				System.out.println("Total execution time to read serialized BigInteger square root: " + ((double)((endTime - startTime))/1000)/60 + " minutes." );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (checkLength) {
			System.out.println("Checking length of the BigInteger we just read...");
			startTime = System.currentTimeMillis();
			System.out.println("Length of number = " + new DecimalFormat("#,###").format(myLKP.getLengthOfSquareRoot()));
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to check length of serialized BigInteger square root that we just read: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		}
/*
		System.out.println("Computing Square Root of the BigInteger...");
		startTime = System.currentTimeMillis();
		BigInteger squareRoot = myLKP.sqrt();
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time to compute square root: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		System.out.println("Length of square root = " + new DecimalFormat("#,###").format(squareRoot.toString().length()));
		myLKP.setMyNum_SquareRoot(squareRoot);
		try {myLKP.serializeTargetBigIntegerSquareRootToDiskFile();} catch (Exception ex) {}

		System.out.println("Squaring the Square Root...");
		startTime = System.currentTimeMillis();
		BigInteger result = squareRoot.multiply(squareRoot);
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time to square the square root: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		System.out.println("Length of result = " + new DecimalFormat("#,###").format(result.toString().length()));

		System.out.println("Comparing with original number...");
		startTime = System.currentTimeMillis();
		int comparison = myLKP.getNum().compareTo(result);
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time to compare: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		System.out.println("Result of comparison = " + comparison);
*/
		BigInteger mod;
		int startingPrime = 71;		// Where the divisor will start. 
		BigInteger divisor = new BigInteger(String.valueOf(startingPrime));
		startTime = System.currentTimeMillis();
		long t1, t2;
		t1 = System.currentTimeMillis();
		int counter = 0;
		BigInteger bigIntegerTwo = new BigInteger("2");
		BigInteger num = myLKP.getNum();		// Create a local copy of the reference to the number we are checking for primeness.
		BigInteger num_SquareRoot = myLKP.getMyNum_SquareRoot();	// Create a local copy of the reference to the square root
		int checkCounter03 = startingPrime, checkCounter05 = startingPrime, checkCounter07 = startingPrime, checkCounter11 = startingPrime, checkCounter13 = startingPrime, checkCounter17 = startingPrime;
		int checkCounter19 = startingPrime, checkCounter23 = startingPrime, checkCounter29 = startingPrime, checkCounter31 = startingPrime, checkCounter37 = startingPrime, checkCounter41 = startingPrime;
		int checkCounter43 = startingPrime, checkCounter47 = startingPrime, checkCounter53 = startingPrime, checkCounter59 = startingPrime, checkCounter61 = startingPrime, checkCounter67 = startingPrime;
		
		// Check the first few primes. Make sure this stops at the last prime before the value of startingPrime
		if            (num.mod(new BigInteger("02")).compareTo(BigInteger.ZERO) == 0) { System.out.println("******************* number is divisible by 2 *********************");
			} else if (num.mod(new BigInteger("03")).compareTo(BigInteger.ZERO) == 0) { System.out.println("******************* number is divisible by 3 *********************");
			} else if (num.mod(new BigInteger("05")).compareTo(BigInteger.ZERO) == 0) { System.out.println("******************* number is divisible by 5 *********************");
			} else if (num.mod(new BigInteger("07")).compareTo(BigInteger.ZERO) == 0) { System.out.println("******************* number is divisible by 7 *********************");
			} else if (num.mod(new BigInteger("11")).compareTo(BigInteger.ZERO) == 0) { System.out.println("******************* number is divisible by 11 *********************");
			} else if (num.mod(new BigInteger("13")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 13 *********************");
			} else if (num.mod(new BigInteger("17")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 17 *********************");
			} else if (num.mod(new BigInteger("19")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 19 *********************");
			} else if (num.mod(new BigInteger("23")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 23 *********************");
			} else if (num.mod(new BigInteger("29")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 29 *********************");
			} else if (num.mod(new BigInteger("31")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 31 *********************");
			} else if (num.mod(new BigInteger("37")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 37 *********************");
			} else if (num.mod(new BigInteger("41")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 41 *********************");
			} else if (num.mod(new BigInteger("43")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 43 *********************");
			} else if (num.mod(new BigInteger("47")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 47 *********************");
			} else if (num.mod(new BigInteger("53")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 53 *********************");
			} else if (num.mod(new BigInteger("59")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 59 *********************");
			} else if (num.mod(new BigInteger("61")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 61 *********************");
			} else if (num.mod(new BigInteger("67")).compareTo(BigInteger.ZERO) == 0) {	System.out.println("******************* number is divisible by 67 *********************");
			} else {
				// We are out of tricks. Supposedly. Time to brute-force it, mostly
				boolean checkThisDivisor = true;
				System.out.println("Starting test loop...");
				BigInteger prevDivisor = new BigInteger(divisor.toByteArray());
				BigInteger bigInteger100 = new BigInteger("100");
				int counterIncrement = 100_000_000;
				while (true) {
					if (checkThisDivisor == true) {
						//System.out.println(divisor.toString());
						mod = num.mod(divisor);
						if (mod.compareTo(BigInteger.ZERO) == 0) {System.out.println("******************* Divisor found *********************"); System.out.println(divisor.toString()); break;}
						//System.out.println("i = " + i + " mod = " + mod.toString());

//						if (counter % 1_000 == 0) {			// Use this for the 20 million digit number
						if (counter % counterIncrement == 0) {		// Use this for smaller numbers, such as 20
							t2 = System.currentTimeMillis();
							System.out.print(new DecimalFormat("#,#######.0000").format(((double)((t2 - t1))/1000)/60) + " minutes.   ");
							System.out.print(" Divisor has " + divisor.toString().length() + " digits.");
							System.out.print(new DecimalFormat(" Elapsed time = #,#######.0").format(((double)((t2 - startTime))/1000)/60) + " minutes. divisor = ");
							System.out.print(divisor.toString() + ". ");	
							try {
							System.out.print(new BigInteger(((Integer)counterIncrement).toString()) + " divisors were tested out of " + divisor.subtract(prevDivisor).toString() + 
									           " possible values (" + new BigInteger(((Integer)counterIncrement).toString()).multiply(bigInteger100).divide(divisor.subtract(prevDivisor)) + "%)" );
							} catch (Exception ex) {}
							System.out.println();
							counter = 0;
							t1 = t2;
							prevDivisor = new BigInteger(divisor.toByteArray());
						}
						counter++;
					}
					divisor = divisor.add(bigIntegerTwo);	// Only check the odd numbers
					checkCounter03 += 2; checkCounter05 += 2; checkCounter07 += 2; checkCounter11 += 2; checkCounter13 += 2; checkCounter17 += 2;
					checkCounter19 += 2; checkCounter23 += 2; checkCounter29 += 2; checkCounter31 += 2; checkCounter37 += 2; checkCounter41 += 2;
					checkCounter43 += 2; checkCounter47 += 2; checkCounter53 += 2; checkCounter59 += 2; checkCounter61 += 2; checkCounter67 += 2;
					
					checkThisDivisor = true;
					
					// Have we added a multiple of a prime number to the divisor?
					if (checkCounter03 %  3 == 0)  {checkThisDivisor = false; checkCounter03 = 0;}
					if (checkCounter05 %  5 == 0)  {checkThisDivisor = false; checkCounter05 = 0;}
					if (checkCounter07 %  7 == 0)  {checkThisDivisor = false; checkCounter07 = 0;}
					if (checkCounter11 % 11 == 0)  {checkThisDivisor = false; checkCounter11 = 0;}
					if (checkCounter13 % 13 == 0)  {checkThisDivisor = false; checkCounter13 = 0;}
					if (checkCounter17 % 17 == 0)  {checkThisDivisor = false; checkCounter17 = 0;}

					if (checkCounter19 % 19 == 0)  {checkThisDivisor = false; checkCounter19 = 0;}
					if (checkCounter23 % 23 == 0)  {checkThisDivisor = false; checkCounter23 = 0;}
					if (checkCounter29 % 29 == 0)  {checkThisDivisor = false; checkCounter29 = 0;}
					if (checkCounter31 % 31 == 0)  {checkThisDivisor = false; checkCounter31 = 0;}
					if (checkCounter37 % 37 == 0)  {checkThisDivisor = false; checkCounter37 = 0;}
					if (checkCounter41 % 41 == 0)  {checkThisDivisor = false; checkCounter41 = 0;}
					
					if (checkCounter43 % 43 == 0)  {checkThisDivisor = false; checkCounter43 = 0;}
					if (checkCounter47 % 47 == 0)  {checkThisDivisor = false; checkCounter47 = 0;}
					if (checkCounter53 % 53 == 0)  {checkThisDivisor = false; checkCounter53 = 0;}
					if (checkCounter59 % 59 == 0)  {checkThisDivisor = false; checkCounter59 = 0;}
					if (checkCounter61 % 61 == 0)  {checkThisDivisor = false; checkCounter61 = 0;}
					if (checkCounter67 % 67 == 0)  {checkThisDivisor = false; checkCounter67 = 0;}
					
					// Are we done?
					// ToDo - we could check this every other iteration to speed it up.
					if (divisor.compareTo(num_SquareRoot) > 0) {System.out.println("No divisor found, number is prime"); break;}
				}
			}
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to run the test loop: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		}
	}

