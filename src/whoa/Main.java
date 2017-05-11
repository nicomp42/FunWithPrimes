/*
 * Bill Nichlson
 * nicholdw@ucmail.uc.edu
 * Some test primes: https://primes.utm.edu/lists/small/small.html
 * 
 * 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 
 * 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 
 * 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199
 * 
 */
package whoa;

import java.io.File;
//import java.math.BigInteger;
import java.text.DecimalFormat;

import config.Config;

public class Main {

//	private static String testFileName = "M74207281";
//	private static String testFileName = "100DigitNOTPrime";
//	private static String testFileName = "100DigitPrime";
//	private static String testFileName = "10DigitPrime";
//	private static String testFileName = "3DigitPrime";
//	private static String testFileName = "10DigitNOTPrime";
//	private static String testFileName = "20DigitPrime";
	private static String testFileName = "30DigitPrime";

	public static void main(String[] args) {
		//testSquareRootMethod();
		//testPrimeChecker(true);
		//justCount();
		justCountWithOurCustomBigInt();
	}
	private static void justCountWithOurCustomBigInt() {
//		CustomBigInt cbi = new CustomBigInt(22_000_000, false);		// 22 million digits
		CustomBigInt cbi = new CustomBigInt(900_000, false);
		cbi.justCountVersion02();	// Make sure length is a multiple of 12
	}
	/**
	 * Run a simple test to see how long it takes to count up to a big number using Big Integer data types
	 */
	private static void justCount() {
		LKP myLKP = new LKP("20DigitPrime");
		try {
			myLKP.readSerializedOurBigIntFromDiskFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		myLKP.justCount(myLKP.getNum());
	}
	
	/**
	 * Run a prime-ness test on a BigInt
	 * @param checkLength True if the method should compute and print the length of the OurBigInt objects read from serialized files. 
	 */
	private static void testPrimeChecker(boolean checkLength) {
		OurBigInt OurBigIntZERO = new OurBigInt("0");

		System.out.println("Test data will be taken from " + testFileName + "...");
		LKP myLKP = new LKP(testFileName);

		long startTime = System.currentTimeMillis();
		long endTime;
		try {
			myLKP.readSerializedOurBigIntFromDiskFile();
		} catch (Exception e) {
			// If we could not read the serialized number from disk, we probably need to create it.
			//e.printStackTrace();
			myLKP.loadLKPFromTextFile(testFileName + ".txt");
			System.out.println("Length of number = " + myLKP.getLengthOfNum());
			try {
				startTime = System.currentTimeMillis();
				myLKP.serializeTargetOurBigIntToDiskFile();
				endTime = System.currentTimeMillis();
				System.out.println("Total execution time to serialize OurBigInt: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time to read serialized OurBigInt: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		if (checkLength) {
			System.out.println("Checking length of the OurBigInt we just read...");
			startTime = System.currentTimeMillis();
			System.out.println("Length of number = " + new DecimalFormat("#,###").format(myLKP.getLengthOfNum()));
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to check length of serialized OurBigInt that we just read: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		}
		// If the square root does not exist in a serialized file, create it now.
		File f = new File(Config.addPathToDataFileName(testFileName + myLKP.squareRootSuffix + ".ser"));
		if(!f.exists()) {
			System.out.println("The file '" + testFileName + myLKP.squareRootSuffix + ".ser' does NOT exist. We will create it!");
			System.out.println("Computing and serializing square root of the OurBigInt...");
			startTime = System.currentTimeMillis();
			OurBigInt squareRoot = myLKP.getNum().sqrt(myLKP.getNum());
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to compute square root: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
			System.out.println("Length of square root = " + new DecimalFormat("#,###").format(squareRoot.toString().length()));
			myLKP.setMyNum_SquareRoot(squareRoot);
			try {
				myLKP.serializeTargetOurBigIntSquareRootToDiskFile();
			} catch (Exception ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		} else {
			System.out.println("The file '" + testFileName + myLKP.squareRootSuffix + ".ser' DOES exist.");
			System.out.println("Reading serialized square root of the OurBigInt...");
			startTime = System.currentTimeMillis();
			try {
				myLKP.readSerializedSquareRootFromDiskFile();
				endTime = System.currentTimeMillis();
				System.out.println("Total execution time to read serialized OurBigInt square root: " + ((double)((endTime - startTime))/1000)/60 + " minutes." );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (checkLength) {
			System.out.println("Checking length of the OurBigInt we just read...");
			startTime = System.currentTimeMillis();
			System.out.println("Length of number = " + new DecimalFormat("#,###").format(myLKP.getLengthOfSquareRoot()));
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to check length of serialized OurBigInt square root that we just read: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		}
/*
		System.out.println("Computing Square Root of the OurBigInt...");
		startTime = System.currentTimeMillis();
		OurBigInt squareRoot = myLKP.sqrt();
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time to compute square root: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		System.out.println("Length of square root = " + new DecimalFormat("#,###").format(squareRoot.toString().length()));
		myLKP.setMyNum_SquareRoot(squareRoot);
		try {myLKP.serializeTargetOurBigIntSquareRootToDiskFile();} catch (Exception ex) {}

		System.out.println("Squaring the Square Root...");
		startTime = System.currentTimeMillis();
		OurBigInt result = squareRoot.multiply(squareRoot);
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
		OurBigInt mod;
		int startingPrime = 101;		// Where the divisor will start. Needs to be a prime number.
		OurBigInt divisor = new OurBigInt(String.valueOf(startingPrime));
		startTime = System.currentTimeMillis();
		long t1, t2;
		t1 = System.currentTimeMillis();
		int counter = 0;
		OurBigInt OurBigIntTwo = new OurBigInt("2");
		OurBigInt num = myLKP.getNum();		// Create a local copy of the reference to the number we are checking for primeness.
		OurBigInt num_SquareRoot = myLKP.getMyNum_SquareRoot();	// Create a local copy of the reference to the square root
		int checkCounter03 = startingPrime, checkCounter05 = startingPrime, checkCounter07 = startingPrime, checkCounter11 = startingPrime, checkCounter13 = startingPrime, checkCounter17 = startingPrime;
		int checkCounter19 = startingPrime, checkCounter23 = startingPrime, checkCounter29 = startingPrime, checkCounter31 = startingPrime, checkCounter37 = startingPrime, checkCounter41 = startingPrime;
		int checkCounter43 = startingPrime, checkCounter47 = startingPrime, checkCounter53 = startingPrime, checkCounter59 = startingPrime, checkCounter61 = startingPrime, checkCounter67 = startingPrime;
		int checkCounter71 = startingPrime, checkCounter73 = startingPrime, checkCounter79 = startingPrime, checkCounter83 = startingPrime, checkCounter89 = startingPrime, checkCounter97 = startingPrime;
		
		// Check the first few primes. Make sure this stops at the last prime before the value of startingPrime
		if            (num.modReturn(new OurBigInt("02")).compareTo(OurBigIntZERO) == 0) { System.out.println("******************* number is divisible by 2 *********************");
			} else if (num.modReturn(new OurBigInt("03")).compareTo(OurBigIntZERO) == 0) { System.out.println("******************* number is divisible by 3 *********************");
			} else if (num.modReturn(new OurBigInt("05")).compareTo(OurBigIntZERO) == 0) { System.out.println("******************* number is divisible by 5 *********************");
			} else if (num.modReturn(new OurBigInt("07")).compareTo(OurBigIntZERO) == 0) { System.out.println("******************* number is divisible by 7 *********************");
			} else if (num.modReturn(new OurBigInt("11")).compareTo(OurBigIntZERO) == 0) { System.out.println("******************* number is divisible by 11 *********************");
			} else if (num.modReturn(new OurBigInt("13")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 13 *********************");
			} else if (num.modReturn(new OurBigInt("17")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 17 *********************");
			} else if (num.modReturn(new OurBigInt("19")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 19 *********************");
			} else if (num.modReturn(new OurBigInt("23")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 23 *********************");
			} else if (num.modReturn(new OurBigInt("29")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 29 *********************");
			} else if (num.modReturn(new OurBigInt("31")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 31 *********************");
			} else if (num.modReturn(new OurBigInt("37")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 37 *********************");
			} else if (num.modReturn(new OurBigInt("41")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 41 *********************");
			} else if (num.modReturn(new OurBigInt("43")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 43 *********************");
			} else if (num.modReturn(new OurBigInt("47")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 47 *********************");
			} else if (num.modReturn(new OurBigInt("53")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 53 *********************");
			} else if (num.modReturn(new OurBigInt("59")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 59 *********************");
			} else if (num.modReturn(new OurBigInt("61")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 61 *********************");
			} else if (num.modReturn(new OurBigInt("67")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 67 *********************");
			} else if (num.modReturn(new OurBigInt("71")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 71 *********************");
			} else if (num.modReturn(new OurBigInt("73")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 73 *********************");
			} else if (num.modReturn(new OurBigInt("79")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 79 *********************");
			} else if (num.modReturn(new OurBigInt("83")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 83 *********************");
			} else if (num.modReturn(new OurBigInt("89")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 89 *********************");
			} else if (num.modReturn(new OurBigInt("97")).compareTo(OurBigIntZERO) == 0) {	System.out.println("******************* number is divisible by 97 *********************");
			} else {
				// We are out of tricks. Supposedly. Time to brute-force it, mostly
				boolean checkThisDivisor = true;
				System.out.println("Starting test loop...");
				OurBigInt prevDivisor = new OurBigInt(divisor.toString());  //.toByteArray());
				OurBigInt OurBigInt100 = new OurBigInt("100");
				int counterIncrement = 100_000_000;
				boolean checkLimit = true;
				while (true) {
					if (checkThisDivisor == true) {
						//System.out.println(divisor.toString());
						mod = num.modReturn(divisor);
						if (mod.compareTo(OurBigIntZERO) == 0) {System.out.println("******************* Divisor found *********************"); System.out.println(divisor.toString()); break;}
						//System.out.println("i = " + i + " mod = " + mod.toString());

						if (counter % counterIncrement == 0) {		// Use this for smaller numbers, such as 20
							t2 = System.currentTimeMillis();
							System.out.print(new DecimalFormat("#,#######.0000").format(((double)((t2 - t1))/1000)/60) + " minutes.   ");
							System.out.print(" Divisor has " + divisor.toString().length() + " digits.");
							System.out.print(new DecimalFormat(" Elapsed time = #,#######.0").format(((double)((t2 - startTime))/1000)/60) + " minutes. divisor = ");
							System.out.print(divisor.toString() + ". ");	
							try {
								String percentage;
								OurBigInt tmp1, tmp = new OurBigInt(((Integer)counterIncrement).toString()).multiplyReturn(OurBigInt100);
								tmp1 = divisor.subtractReturn(prevDivisor);
								tmp = tmp.divideReturn(tmp1);
								percentage = tmp.toString();
								System.out.print(new OurBigInt(((Integer)counterIncrement).toString()) + " divisors were tested out of " + divisor.subtractReturn(prevDivisor).toString() + 
									           " possible values (" + percentage + "%)" );
							} catch (Exception ex) {}
							System.out.println();
							counter = 0;
							t1 = t2;
							prevDivisor = new OurBigInt(divisor.toString());
						}
						counter++;
					}
					divisor.add(OurBigIntTwo);	// Only check the odd numbers
					checkCounter03 += 2; checkCounter05 += 2; checkCounter07 += 2; checkCounter11 += 2; checkCounter13 += 2; checkCounter17 += 2;
					checkCounter19 += 2; checkCounter23 += 2; checkCounter29 += 2; checkCounter31 += 2; checkCounter37 += 2; checkCounter41 += 2;
					checkCounter43 += 2; checkCounter47 += 2; checkCounter53 += 2; checkCounter59 += 2; checkCounter61 += 2; checkCounter67 += 2;
//					checkCounter71 += 2; checkCounter73 += 2; checkCounter79 += 2; checkCounter83 += 2; checkCounter89 += 2; checkCounter97 += 2; 
					
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
					
					/* Diminishing returns start about here...
					if (checkCounter71 % 71 == 0)  {checkThisDivisor = false; checkCounter71 = 0;}
					if (checkCounter73 % 73 == 0)  {checkThisDivisor = false; checkCounter73 = 0;}
					if (checkCounter79 % 79 == 0)  {checkThisDivisor = false; checkCounter79 = 0;}
					if (checkCounter83 % 83 == 0)  {checkThisDivisor = false; checkCounter83 = 0;}
					if (checkCounter89 % 89 == 0)  {checkThisDivisor = false; checkCounter89 = 0;}
					if (checkCounter97 % 97 == 0)  {checkThisDivisor = false; checkCounter97 = 0;}
					*/
					// Are we done?
					
					// ToDo - we could check this every other iteration to speed it up. More work needs to be done on this
					if (checkLimit == true) {
						if (divisor.compareTo(num_SquareRoot) > 0) {System.out.println("No divisor found, number is prime"); break;}
					}
					//checkLimit = !checkLimit;
				}
			}
			endTime = System.currentTimeMillis();
			System.out.println("Total execution time to run the test loop: " + new DecimalFormat("#,###.0000").format(((double)((endTime - startTime))/1000)/60) + " minutes." );
		}
	private static void testSquareRootMethod() {
		LKP foo = new LKP("");
		OurBigInt x = new OurBigInt("100");
		//OurBigInt xx = foo.sqrt(x);
		//System.out.println(xx.toString());
		
		OurBigInt bi = new OurBigInt("100");		
		OurBigInt sqrt = bi.sqrt(bi);
		System.out.println(sqrt.toString());

		bi = new OurBigInt("64");		
		sqrt = bi.sqrt(bi);
		System.out.println(sqrt.toString());

		bi = new OurBigInt("65536");		
		sqrt = bi.sqrt(bi);
		System.out.println(sqrt.toString());
	
	
	}
	}

