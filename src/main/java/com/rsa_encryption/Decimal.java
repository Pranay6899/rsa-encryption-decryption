package com.rsa_encryption;

import java.util.Arrays;

public class Decimal {
	protected char [] digits;
	protected int length;
	protected static final Decimal zero = new Decimal ( "0" );
	protected Decimal ( String integer ) {
		// Attempt to see if passed integer is compatible
		try {
			// Check to see if the integer is compatible
			if ( Decimal.isHugeUnsignedInt ( integer ) ) {
				// If it is compatible then parse it
				this.parse ( integer );
			}
			// If it isn't comparable, then through an exception
			else {
				// Throw error stating that passed integer is incompatible
				throw new RSAException ( "Non-Natural integer was passed to constructor." );
			}
		}
		// We don't wanna catch this exception, just quit
		catch ( RSAException exception ) {
			// Exit the program
			System.exit ( 0 );
		}
	}

	private void parse ( String integer ) {
		// Check the whole string and see if its just zeros
		int padding = 0;
		boolean flag = true;
		// Loop through string
		for ( int i = 0; i < integer.length (); i++ ) {
			// Check if current char is zero
			if ( integer.charAt ( i ) == '0' && flag ) {
				// Increment padding
				padding++;
			}
			else {
				// Change flag
				flag = false;
			}
		}
		// Check to see if only zero was passed
		if ( flag ) {
			// Set the length
			this.length = 1;
			// Set the digit
			this.digits = new char [ 1 ];
			this.digits [ 0 ] = '0';
			// Return, for this special case
			return;
		}
		// Remove trailing zeros
		integer = integer.substring ( padding );
		// Convert and save the string to a char array
		this.digits = integer.toCharArray ();
		// Get the length of the integer
		int length = this.digits.length;
		// Reverse string for preferred index access
		for ( int i = 0; i < length / 2; i++ ) {
			// Swap current character with mirrored one
			this.digits [ i ] ^= this.digits [ length - i - 1 ];
			this.digits [ length - i - 1 ] ^= this.digits [ i ];
			this.digits [ i ] ^= this.digits [ length - i - 1 ];
		}
		// Set the length of the integer
		this.length = integer.length ();
	}

	protected int get ( int index ) {
		// Check to see that the index is within the bounds of the character array
		if ( this.digits.length > index && index >= 0 ) {
			// If it is then cast it as an integer and return
			return Decimal.parseInt ( this.digits [ index ] );
		}
		// Otherwise, the index is out of bounds
		else {
			// Return zero by default
			return 0;
		}
	}

	protected String stringify () {
		// Create a new char array
		char [] number = Arrays.copyOf ( this.digits, this.length );
		// Save the length
		int length = number.length;
		// Reverse the char array
		for ( int i = 0; i < length / 2; i++ ) {
			// Swap current character with mirrored one
			number [ i ] ^= number [ length - i - 1 ];
			number [ length - i - 1 ] ^= number [ i ];
			number [ i ] ^= number [ length - i - 1 ];
		}
		// Create a new string and return it
		return new String ( number );
	}

	protected String stringify ( int padding ) {
		// Get the result
		String result = this.stringify ();
		// Loop through until desired size is met
		while ( result.length () < padding ) {
			// Append a zero to the result
			result = "0" + result;
		}
		// Return the resulting string
		return result;
	}

	protected void print () {
		// Print out the huge unsigned integer
		System.out.println ( this.stringify () );
	}

	protected static boolean isHugeUnsignedInt ( String integer ) {
		// Get the number of digits in integer
		int length = integer.length ();
		// Loop through the whole integer character by character
		for ( int i = 0; i < length; i++ ) {
			// Check if the character is out of bounds
			if ( integer.charAt ( i ) < 48 || integer.charAt ( i ) > 57 ) {
				// If it is then return false
				return false;
			}
		}
		// Otherwise, return true
		return true;
	}

	protected static int parseInt ( char c ) {
		// Cast character to corresponding integer value
		return Character.getNumericValue ( c );
	}

	protected static char parseChar ( int i ) {
		// Cast to string, then get the first char at string position zero
		return Integer.toString ( i ).charAt ( 0 );
	}

}
