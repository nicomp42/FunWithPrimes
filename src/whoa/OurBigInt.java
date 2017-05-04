package whoa;

import java.math.BigInteger;

import org.huldra.math.BigInt;

public class OurBigInt extends BigInt  {

	private static final long serialVersionUID = 1L;

	public OurBigInt(char[] s) {
		super(s);
	}
	public OurBigInt(String s) {
		super(s);
	}
	public OurBigInt(OurBigInt num) {
		super(num.toString());
	}
	/**
	 * A mod method that does not destroy the argument passed to the method
	 * @param divisor The number to divide into the object
	 * @return The integer remainder of the division, calls the rem method in the base class.
	 */
	public OurBigInt modReturn(OurBigInt divisor) {
		OurBigInt biMod = new OurBigInt(this);
		biMod.rem(divisor);
		return biMod;
	}
	public OurBigInt multiplyReturn(OurBigInt num) {
		OurBigInt bi = new OurBigInt(this);
		bi.mul(num);
		return bi;
	}
	public OurBigInt subtractReturn(OurBigInt num) {
		OurBigInt bi = new OurBigInt(this);
		bi.sub(num);
		return bi;
	}
	public OurBigInt divideReturn(OurBigInt num) {
		OurBigInt bi = new OurBigInt(this);
		bi.div(num);
		return bi;
	}
	/**
	 * http://stackoverflow.com/questions/4407839/how-can-i-find-the-square-root-of-a-java-biginteger
	 * @param num
	 * @return Square root of num
	 */
	public OurBigInt sqrt(OurBigInt num) {
		//OurBigInt OurBigIntZero = new OurBigInt("0");
		OurBigInt div = new OurBigInt(num); 			// OurBigIntZero;				//.setBit(num.      .bitLength()/2);
		div.div(new OurBigInt("2"));		// Start somewhere
		OurBigInt div2 = new OurBigInt(div);
	    // Loop until we hit the same value twice in a row, or wind up alternating.
    	OurBigInt y;
	    for(;;) {
	    	y = new OurBigInt(num);
	    	y.div(div);
	    	y.add(div);
	    	y.shiftRight(1);
	    	
	        if (y.equals(div) || y.equals(div2)) {
	        	return y;
	        }
	        div2 = div;
	        div = y;
	    }
	}
}
