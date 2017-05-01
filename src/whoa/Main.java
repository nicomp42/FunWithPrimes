/*
 * Bill Nichlson
 * nicholdw@ucmail.uc.edu
 * Some test primes: https://primes.utm.edu/lists/small/small.html
 */
package whoa;

import java.io.File;
import java.math.BigInteger;
import java.text.DecimalFormat;

import config.Config;

public class Main {

	private static String testFileName = "M74207281";
//	private static String testFileName = "100DigitNOTPrime";
//	private static String testFileName = "100DigitPrime";
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
		int startingPrime = 61;		// Where the divisor will start. 
		BigInteger divisor = new BigInteger(String.valueOf(startingPrime));
		System.out.println("Starting test loop...");
		startTime = System.currentTimeMillis();
		long t1, t2;
		t1 = System.currentTimeMillis();
		int counter = 0;
		BigInteger bigIntegerTwo = new BigInteger("2");
		BigInteger num = myLKP.getNum();		// Create a local copy of the reference to the number we are checking for primeness.
		BigInteger num_SquareRoot = myLKP.getMyNum_SquareRoot();	// Create a local copy of the reference to the square root
		int checkCounter = startingPrime;
		// Check the first few primes. Make sure this stops at the last prime before the value of startingPrime
		// 3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61, 
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
			} else {
				// We are out of tricks. Supposedly. Time to brute-force it, mostly
				boolean checkThisDivisor = true;
				while (true) {
					if (checkThisDivisor == true) {
						System.out.println(divisor.toString());
						mod = num.mod(divisor);
						if (mod.compareTo(BigInteger.ZERO) == 0) {System.out.println("******************* Divisor found *********************"); System.out.println(divisor.toString()); break;}
						//System.out.println("i = " + i + " mod = " + mod.toString());

						if (counter % 100_000_000 == 0) {
							t2 = System.currentTimeMillis();
							System.out.print(new DecimalFormat("#,#######.0000").format(((double)((t2 - t1))/1000)/60) + " minutes.");
							//System.out.println(divisor.toString());
							System.out.print(" Divisor has " + divisor.toString().length() + " digits.");
							System.out.println(new DecimalFormat(" Elapsed time = #,#######.0").format(((double)((t2 - startTime))/1000)/60) + " minutes.");
							counter = 0;
							t1 = t2;
						}
						counter++;
					}
					divisor = divisor.add(bigIntegerTwo);	// Only check the odd numbers
					checkCounter += 2;
					checkThisDivisor = true;
					if         (checkCounter % 3 == 0)  {checkThisDivisor = false;
					} else 	if (checkCounter % 5 == 0)  {checkThisDivisor = false;
					} else 	if (checkCounter % 7 == 0)  {checkThisDivisor = false;
					} else 	if (checkCounter % 11 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 13 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 17 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 19 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 23 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 29 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 31 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 37 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 41 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 43 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 47 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 53 == 0) {checkThisDivisor = false;
					} else 	if (checkCounter % 59 == 0) {checkThisDivisor = false; checkCounter = 59; //System.out.println("found a divisor that is a factor of 59: checkCounter = " + checkCounter );
					}
					// Are we done?
					// ToDo - we could check this every other iteration to speed it up.
					if (divisor.compareTo(num_SquareRoot) > 0) {System.out.println("No divisor found, number is prime"); break;}
				}
			}
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to run the test loop: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		}
	}

