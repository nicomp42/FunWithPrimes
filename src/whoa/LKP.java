/*
 * Bill Nicholson
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
//import java.math.BigInteger;
import java.text.DecimalFormat;

import config.Config;
/**
 * LKP = Largest Known Prime
 * @author nicomp
 *
 */
public class LKP {
	private OurBigInt myNum;
	private OurBigInt myNum_SquareRoot;
	private String numberFileName;
	public final String squareRootSuffix = "_SquareRoot";

	public OurBigInt getNum() {return myNum;}		// We will return a reference because cloning it would take forever

	public LKP(String numberFileName) {
		this.numberFileName = numberFileName;
		myNum = new OurBigInt("0");
	}
	public int getLengthOfNum() {return myNum.toString().length();}
	public int getLengthOfSquareRoot() {return myNum_SquareRoot.toString().length();}

	/**
	 * Read an existing serialized OurBigInt object from the disk
	 * @param fileName
	 * @throws Exception
	 */
	public void readSerializedOurBigIntFromDiskFile() throws Exception {
		try {
			InputStream fos = new FileInputStream(Config.addPathToDataFileName(numberFileName + ".ser"));
			ObjectInputStream inputStream = new ObjectInputStream(fos);
			myNum = (OurBigInt) inputStream.readObject();
			fos.close();
		} catch (Exception ex) {
			System.out.println("LKP.readSerializedOurBigIntFromDiskFile: " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	/**
	 * Read an existing serialized OurBigInt object from the disk
	 * @param fileName
	 * @throws Exception
	 */
	public void readSerializedSquareRootFromDiskFile() throws Exception {
		try {
			InputStream fos = new FileInputStream(Config.addPathToDataFileName(numberFileName + squareRootSuffix  + ".ser"));
			ObjectInputStream inputStream = new ObjectInputStream(fos);
			myNum_SquareRoot = (OurBigInt) inputStream.readObject();
			fos.close();
		} catch (Exception ex) {
			System.out.println("LKP.readSerializedSquareRootFromDiskFile: " + ex.getLocalizedMessage());
			throw ex;
		}
	}

	/**
	 * Write a OurBigInt object to a file
	 * @throws Exception
	 */
	private void serializeOurBigIntToDiskFile(String fileName) throws Exception {
		try {
		OutputStream fos = new FileOutputStream(Config.addPathToDataFileName(fileName  +  ".ser"));
		ObjectOutputStream outputStream = new ObjectOutputStream(fos);
		outputStream.writeObject(myNum);
		fos.close();
		} catch (Exception ex) {
			System.out.println("LKP.serializeOurBigIntToDiskFile(" + fileName + ".ser" + "): " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	/**
	 * Write the OurBigInt object to a file
	 * @throws Exception
	 */
	public void serializeTargetOurBigIntToDiskFile() throws Exception {
		try {
			serializeOurBigIntToDiskFile(numberFileName);
		} catch (Exception ex) {
			System.out.println("LKP.serializeTargetOurBigIntToDiskFile(" + numberFileName + "): " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	/**
	 * Write the OurBigInt object to a file
	 * @throws Exception
	 */
	public void serializeTargetOurBigIntSquareRootToDiskFile() throws Exception {
		try {
		OutputStream fos = new FileOutputStream(Config.addPathToDataFileName(numberFileName + squareRootSuffix + ".ser"));
		ObjectOutputStream outputStream = new ObjectOutputStream(fos);
		outputStream.writeObject(myNum_SquareRoot);
		fos.close();
		} catch (Exception ex) {
			System.out.println("LKP.serializeTargetOurBigIntSquareRootToDiskFile(" + numberFileName + squareRootSuffix + "): " + ex.getLocalizedMessage());
			throw ex;
		}
	}
	/**
	 * Loading the number from a text file is almost instantaneous.
	 * Converting to a OurBigInt takes about 3 hours.
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
				//int lines = 0;
				while (true) {
					String tmp;
					tmp = br.readLine();
//					System.out.print(", ");
//					System.out.print(tmp.length());
					if (tmp == null) break;
					//lines++;
					//if (lines % 10000 == 0) System.out.println(lines);
					buff.append(tmp);
				}
				long endTime = System.currentTimeMillis();
				System.out.println("Done reading from file " + fileName + "...");
				System.out.println("Total execution time to load from text file: " + new DecimalFormat("#,###.0000").format(((double)(endTime - startTime))/1000) + " seconds." );
				System.out.println("StringBuilder object is " + buff.length() + " bytes.");
				System.out.println("Converting to OurBigInt...");
				startTime = System.currentTimeMillis();
				myNum = new OurBigInt(buff.toString());		// Takes about 3 hours.
				endTime = System.currentTimeMillis();
				System.out.println("Total execution time to convert to OurBigInt: " + new DecimalFormat("#,###.0000").format(((double)(endTime - startTime))/1000) + " seconds." );

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
	 * @return square root of OurBigInt object stored in this object
	 */
	/*
	public OurBigInt sqrt() {
		return sqrt(myNum);
	}
*/
	/**
	 * http://stackoverflow.com/questions/4407839/how-can-i-find-the-square-root-of-a-java-OurBigInt
	 * @param num
	 * @return Square root of num
	 */
	/*
	public OurBigInt sqrt(OurBigInt num) {
		
		
//	    OurBigInt div = OurBigInt.ZERO.setBit(num.bitLength()/2);
	    OurBigInt div = num.divide(new OurBigInt("2"));
	    OurBigInt div2 = div;
	    // Loop until we hit the same value twice in a row, or wind
	    // up alternating.
	    for(;;) {
//	        OurBigInt y = div.add(num.divide(div)).shiftRight(1);
	        OurBigInt y = num.divide(div);
	        y = div.add(y);
	        y = y.shiftRight(1);
	        if (y.equals(div) || y.equals(div2))
	            return y;
	        div2 = div;
	        div = y;
	    }
	    
	}
	*/
	/**
	 * Get the reference to the square root of the number we are processing
	 * @return the reference
	 */
	public OurBigInt getMyNum_SquareRoot() {
		return myNum_SquareRoot;
	}

	/**
	 * Set the reference to the square root of the number we are processing
	 * @param myNum_SquareRoot The reference to the square root of the number we are processing
	 */
	public void setMyNum_SquareRoot(OurBigInt myNum_SquareRoot) {
		this.myNum_SquareRoot = myNum_SquareRoot;
	}
}
