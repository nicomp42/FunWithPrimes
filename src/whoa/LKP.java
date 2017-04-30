/*
 * Bill Nichlson
 * nicholdw@ucmail.uc.edu
 *
 * Prime numbers are of divisible only by themselves and the number one.
 * The largest known prime number now is an astounding 22,338,618 digits
 * and was discovered by a researcher at the University of Central Missouri,
 *
 * http://abcnews.go.com/Technology/math-nerds-geek-prime-discovery/story?id=36399058
 *
 */

package whoa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;

import config.Config;
/**
 * LKP = Largest Known Prime
 * @author nicomp
 *
 */
public class LKP {
	private BigInteger myNum;
	private BigInteger myNum_SquareRoot;
	private String numberFileName;
	public final String squareRootSuffix = "_SquareRoot";

	public BigInteger getNum() {return myNum;}		// We will return a reference because cloning it would take forever

	public LKP(String numberFileName) {
		this.numberFileName = numberFileName;
		myNum = new BigInteger("0");
	}
	public int getLengthOfNum() {return myNum.toString().length();}
	public int getLengthOfSquareRoot() {return myNum_SquareRoot.toString().length();}

	/**
	 * Read an existing serialized BigInteger object from the disk
	 * @param fileName
	 * @throws Exception
	 */
	public void readSerializedBigIntegerFromDiskFile() throws Exception {
		try {
			InputStream fos = new FileInputStream(Config.addPathToDataFileName(numberFileName + ".ser"));
			ObjectInputStream inputStream = new ObjectInputStream(fos);
			myNum = (BigInteger) inputStream.readObject();
			fos.close();
		} catch (Exception ex) {
			System.out.println("LKP.readSerializedBigIntegerFromDiskFile: " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	/**
	 * Read an existing serialized BigInteger object from the disk
	 * @param fileName
	 * @throws Exception
	 */
	public void readSerializedSquareRootFromDiskFile() throws Exception {
		try {
			InputStream fos = new FileInputStream(Config.addPathToDataFileName(numberFileName + squareRootSuffix  + ".ser"));
			ObjectInputStream inputStream = new ObjectInputStream(fos);
			myNum_SquareRoot = (BigInteger) inputStream.readObject();
			fos.close();
		} catch (Exception ex) {
			System.out.println("LKP.readSerializedSquareRootFromDiskFile: " + ex.getLocalizedMessage());
			throw ex;
		}
	}

	/**
	 * Write a BigInteger object to a file
	 * @throws Exception
	 */
	private void serializeBigIntegerToDiskFile(String fileName) throws Exception {
		try {
		OutputStream fos = new FileOutputStream(Config.addPathToDataFileName(fileName + ".ser"));
		ObjectOutputStream outputStream = new ObjectOutputStream(fos);
		outputStream.writeObject(myNum);
		fos.close();
		} catch (Exception ex) {
			System.out.println("LKP.serializeBigIntegerToDiskFile(" + fileName + ".ser" + "): " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	/**
	 * Write the BigInteger object to a file
	 * @throws Exception
	 */
	public void serializeTargetBigIntegerToDiskFile() throws Exception {
		try {
			serializeBigIntegerToDiskFile(numberFileName);
		} catch (Exception ex) {
			System.out.println("LKP.serializeTargetBigIntegerToDiskFile(" + numberFileName + "): " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	/**
	 * Write the BigInteger object to a file
	 * @throws Exception
	 */
	public void serializeTargetBigIntegerSquareRootToDiskFile() throws Exception {
		try {
		OutputStream fos = new FileOutputStream(Config.addPathToDataFileName(numberFileName + squareRootSuffix));
		ObjectOutputStream outputStream = new ObjectOutputStream(fos);
		outputStream.writeObject(myNum_SquareRoot);
		fos.close();
		} catch (Exception ex) {
			System.out.println("LKP.serializeTargetBigIntegerSquareRootToDiskFile(" + numberFileName + squareRootSuffix + "): " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	/**
	 * Loading the number from a text file is almost instantaneous.
	 * Converting to a BigInteger takes about 3 hours.
	 * @param fileName
	 */
	public void loadLKPFromTextFile(String fileName) {
		long startTime = System.currentTimeMillis();
		StringBuilder buff = new StringBuilder("");
		try {
			FileReader fr = new FileReader(Config.addPathToDataFileName(fileName));
			BufferedReader br = new BufferedReader(fr);
			try {
				System.out.println("Reading from file " + fileName + "...");
				int lines = 0;
				while (true) {
					String tmp;
					tmp = br.readLine();
//					System.out.print(", ");
//					System.out.print(tmp.length());
					if (tmp == null) break;
					lines++;
					//if (lines % 10000 == 0) System.out.println(lines);
					buff.append(tmp);
				}
				long endTime = System.currentTimeMillis();
				System.out.println("Done reading from file " + fileName + "...");
				System.out.println("Total execution time to load from text file: " + new DecimalFormat("#,###.0000").format(((double)(endTime - startTime))/1000) + " seconds." );
				System.out.println("StringBuilder object is " + buff.length() + " bytes.");
				System.out.println("Converting to BigInteger...");
				startTime = System.currentTimeMillis();
				myNum = new BigInteger(buff.toString());		// Takes about 3 hours.
				endTime = System.currentTimeMillis();
				System.out.println("Total execution time to convert to BigInteger: " + new DecimalFormat("#,###.0000").format(((double)(endTime - startTime))/1000) + " seconds." );

			} catch (Exception ex) {

			} finally {
				try {br.close();} catch (Exception ex) {}	// OK to eat this exception
			}
		} catch(Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
	}
	/**
	 * Polymorphic.
	 * @return square root of BigInteger object stored in this object
	 */
	public BigInteger sqrt() {
		return sqrt(myNum);
	}

	/**
	 * http://stackoverflow.com/questions/4407839/how-can-i-find-the-square-root-of-a-java-biginteger
	 * @param num
	 * @return Square root of num
	 */
	public BigInteger sqrt(BigInteger num) {
	    BigInteger div = BigInteger.ZERO.setBit(num.bitLength()/2);
	    BigInteger div2 = div;
	    // Loop until we hit the same value twice in a row, or wind
	    // up alternating.
	    for(;;) {
	        BigInteger y = div.add(num.divide(div)).shiftRight(1);
	        if (y.equals(div) || y.equals(div2))
	            return y;
	        div2 = div;
	        div = y;
	    }
	}
	/**
	 * Get the reference to the square root of the number we are processing
	 * @return the reference
	 */
	public BigInteger getMyNum_SquareRoot() {
		return myNum_SquareRoot;
	}

	/**
	 * Set the reference to the square root of the number we are processing
	 * @param myNum_SquareRoot The reference to the square root of the number we are processing
	 */
	public void setMyNum_SquareRoot(BigInteger myNum_SquareRoot) {
		this.myNum_SquareRoot = myNum_SquareRoot;
	}
}
