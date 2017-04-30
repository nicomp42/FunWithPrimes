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

//	private static String testFileName = "M74207281";
//	private static String testFileName = "100DigitNOTPrime";
	private static String testFileName = "100DigitPrime";
//	private static String testFileName = "10DigitPrime";
//	private static String testFileName = "10DigitNOTPrime";
//	private static String testFileName = "20DigitPrime";

	public static void main(String[] args) {
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
		System.out.println("Checking length of the BigInteger we just read...");
		startTime = System.currentTimeMillis();
		System.out.println("Length of number = " + new DecimalFormat("#,###").format(myLKP.getLengthOfNum()));
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time to check length of serialized BigInteger that we just read: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );

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
		System.out.println("Checking length of the BigInteger we just read...");
		startTime = System.currentTimeMillis();
		System.out.println("Length of number = " + new DecimalFormat("#,###").format(myLKP.getLengthOfSquareRoot()));
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time to check length of serialized BigInteger square root that we just read: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );

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
		// Run a brief test to see how long a few iterations of prime-ness testing would take
		BigInteger mod;
		BigInteger divisor = new BigInteger("3");	//  We will only check odd numbers
		System.out.println("Starting test loop...");
//		int testIterations = 10000000;
		startTime = System.currentTimeMillis();
		long t1, t2;
		t1 = System.currentTimeMillis();
		int counter = 0;
//		for (int i = 2; i < testIterations; i++) {
		BigInteger bigIntegerTwo = new BigInteger("2");
		BigInteger num = myLKP.getNum();		// Create a local copy of the reference.
		if (num.mod(bigIntegerTwo).compareTo(BigInteger.ZERO) == 0) {
			System.out.println("******************* number is divisible by 2 *********************");
		} else {
			while (true) {
				mod = num.mod(divisor);
				if (mod.compareTo(BigInteger.ZERO) == 0) {
					System.out.println("******************* Divisor found *********************");
					break;
				}
				//System.out.println("i = " + i + " mod = " + mod.toString());
				if (counter % 100_000_000 == 0) {
					t2 = System.currentTimeMillis();
					System.out.print(new DecimalFormat("#,#######.0000").format(((double)((t2 - t1))/1000)/60) + " minutes.");
					//System.out.println(divisor.toString());
					System.out.println(" Divisor has " + divisor.toString().length() + " digits.");
					counter = 0;
					t1 = t2;
				}
				counter++;
				divisor = divisor.add(bigIntegerTwo);	// Only check the odd numbers
				// Are we done?
				if (divisor.compareTo(myLKP.getMyNum_SquareRoot()) > 0) {System.out.println("No divisor found, number is prime"); break;}
			}
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to run the test loop: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		}
	}
}
