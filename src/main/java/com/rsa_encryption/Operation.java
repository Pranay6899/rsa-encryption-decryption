package com.rsa_encryption;

import java.util.Arrays;

public class Operation {

	
	protected static Decimal add ( Decimal a, Decimal b ) {
		// Initialize the digit and carry local variables
		int digit = 0;
		int carry = 0;
		// Initialize an empty string that will hold the result
		String result = "";
		// Get the maximum number of digits that is required for this operation to be successful
		int max = ( a.length >= b.length ) ? a.length + 1 : b.length + 1;
		// Loop though the max number of indexes possible
		for ( int i = 0; i < max; i++ ) {
			// Calculate the sum of both integers at current index
			digit = a.get ( i ) + b.get ( i ) + carry;
			// Calculate the current digit and the carry number
			carry = digit / 10;
			digit = digit % 10;
			// Append to the result integer
			result = digit + result;
			// Check if we are on the last index and check if we can append a carry numeral
			if ( i == max -1 && digit == 0 && carry > 0 ) {
				// Append carry numeral to the result integer
				result += carry;
			}
		}
		// Return the new huge unsigned integer
		return new Decimal ( result );
	}

	protected static Decimal subtract ( Decimal a, Decimal b ) {
		// Initialize the result decimal to null
		Decimal decimal = null;
		// Try to subtract two huge unsigned integers
		try {
			// Check to see if the result will be negative
			if ( Operation.lessThan ( a, b ) ) {
				// Throw an error stating that negative number are not allowed
				throw new RSAException ( "Subtraction operation will yield a negative number." );
			}
			// Otherwise continue with the algorithm
			else {
				// Initialize the carry integer and calculate the max decimal places
				int carry = 0;
				int max = ( a.length > b.length ) ? a.length : b.length;
				// Initialize result char array and fill with zeros
				char [] result = new char [ max ];
				Arrays.fill ( result, '0' );
				// Loop through all decimal places in result array
				for ( int i = 0; i < max; i++ ) {
					// If we are able to subtract without an extra carry ( accounting for carry )
					if ( ( a.get ( i ) - carry ) >= b.get ( i ) ) {
						// Subtract the digits as well as the carry digit
						result [ i ] = Decimal.parseChar ( a.get ( i ) - b.get ( i ) - carry );
						// Reset the carry digit because we accounted for it
						carry = 0;
					}
					// Otherwise we need to barrow ( we will account for carry if there is one )
					else {
						// Subtract with adding 10 to 'a' and account for carry bit
						result [ i ] = Decimal.parseChar ( ( a.get ( i ) - carry ) + 10 - b.get ( i ) );
						// Since we borrowed, set carry bit to one
						carry = 1;
					}
				}
				// Reverse the result string to be represented correctly
				for ( int i = 0; i < max / 2; i++ ) {
					// Swap the current and mirrored elements
					result [ i ] ^= result [ max - i - 1 ];
					result [ max - i - 1 ] ^= result [ i ];
					result [ i ] ^= result [ max - i - 1 ];
				}
				// Return the result huge unsigned integer after casting as a string
				decimal = new Decimal ( new String ( result ) );
			}
		}
		// Catch the exception if possible
		catch ( RSAException exception ) {
			// Exit the program after throwing exception
			System.exit ( 0 );
		}
		// By default return null, even through we will never get here
		return decimal;
	}

	protected static Decimal multiply ( Decimal a, Decimal b ) {
		// Initialize the digit and carry local variables
		int digit = 0;
		int carry = 0;
		// Initialize the empty string that will hold the result and fill with zeros
		int max = a.length + b.length;
		char [] result = new char [ max ];
		Arrays.fill ( result, '0' );
		// Loop through the first decimals digits
		for ( int j = 0; j < b.length; j++ ) {
			// Loop through second decimals digits
			for ( int i = 0; i < a.length; i++ ) {
				// Calculate the result digit and carry
				digit = Decimal.parseInt ( result [ i + j ] );
				digit += carry + ( a.get ( i ) * b.get ( j ) );
				carry = digit / 10;
				// Set to current position
				result [ i + j ] = Decimal.parseChar ( digit % 10 );
			}
			// Append the carry when we are done with line
			result [ j + a.length ] += carry % 10;
			carry = carry / 10;
		}
		// Reverse the resulting digit
		for ( int i = 0; i < max / 2; i++ ) {
			// Swap current char with the mirrored one
			result [ i ] ^= result [ max - i - 1 ];
			result [ max - i - 1 ] ^= result [ i ];
			result [ i ] ^= result [ max - i - 1 ];
		}
		// Cast result char array to string and return resulting Decimal result
		return new Decimal ( new String ( result ) );
	}

	protected static Decimal divide ( Decimal a, Decimal b ) {
		// Initialize the result decimal to null
		Decimal decimal = null;
		// Try to preform division
		try {
			// Check to see that the divisor is less than or equal to zero
			if ( Operation.lessThanEqual ( b, Decimal.zero ) ) {
				// Throw usage error if it is
				throw new RSAException ("Attempting to divide by non-natural number or zero.");
			}
			// Declare the multiplier variable
			int multiplier = 0;
			// Initialize the dividend, remainder, and the quotient as strings
			String dividend = a.stringify ();
			String remainder = "";
			String quotient = "";
			// Loop until we dropped all digits of dividend to remainder
			while ( dividend.length () > 0 ) {
				// Drop down first digit from dividend to the remainder
				remainder += dividend.charAt ( 0 );
				dividend = dividend.substring ( 1 );
				// Reset multiplier counter
				multiplier = 0;
				// Loop through and attempt to divide remainder by quotient
				while ( Operation.greaterThanEqual ( new Decimal ( remainder ), b ) ) {
					// Subtract a unit of quotient from the remainder
					remainder = Operation.subtract ( new Decimal ( remainder ), b ).stringify ();
					// Increment multiplier counter
					multiplier++;
				}
				// Append the multiplier to the resultant quotient
				quotient += Decimal.parseChar ( multiplier );
			}
			// Save the return value to be the quotient as an instance of the Decimal object
			decimal = new Decimal ( quotient );
		}
		// Attempt to catch this exception
		catch ( RSAException exception ) {
			// Exit the program
			System.exit ( 0 );
		}
		// By default we return null
		return decimal;
	}

	protected static Decimal modulo ( Decimal a, Decimal b ) {
		// Initialize the result decimal to null
		Decimal decimal = null;
		// Try to preform division
		try {
			// Check to see that the divisor is less than or equal to zero
			if ( Operation.lessThanEqual ( b, Decimal.zero ) ) {
				// Throw usage error if it is
				throw new RSAException ("Attempting to divide by non-natural number or zero.");
			}
			// Declare the multiplier variable
			int multiplier = 0;
			// Initialize the dividend, remainder, and the quotient as strings
			String dividend = a.stringify ();
			String remainder = "";
			String quotient = "";
			// Loop until we dropped all digits of dividend to remainder
			while ( dividend.length () > 0 ) {
				// Drop down first digit from dividend to the remainder
				remainder += dividend.charAt ( 0 );
				dividend = dividend.substring ( 1 );
				// Reset multiplier counter
				multiplier = 0;
				// Loop through and attempt to divide remainder by quotient
				while ( Operation.greaterThanEqual ( new Decimal ( remainder ), b ) ) {
					// Subtract a unit of quotient from the remainder
					remainder = Operation.subtract ( new Decimal ( remainder ), b ).stringify ();
					// Increment multiplier counter
					multiplier++;
				}
				// Append the multiplier to the resultant quotient
				quotient += Decimal.parseChar ( multiplier );
			}
			// Save the return value to be the remainder as an instance of the Decimal object
			decimal = new Decimal ( remainder );
		}
		// Attempt to catch this exception
		catch ( RSAException exception ) {
			// Exit the program
			System.exit ( 0 );
		}
		// By default we return null
		return decimal;
	}

	protected static Decimal shift ( Decimal a, int shift ) {
		// Get the target decimal as a string
		String result = a.stringify ();
		// If the shift is a negative shift, then we want to append zeros
		if ( shift < 0 ) {
			// Create a char buffer equal to the absolute value of shift
			char [] buffer = new char [ shift * -1 ];
			// Fill buffer with zeros and append to target decimal
			Arrays.fill ( buffer, '0' );
			result += new String ( buffer );
		}
		// Otherwise shift is positive, remove n chars from back of string
		else {
			// Preform a substring and save back to result string
			result = result.substring ( 0, result.length () - 1 - shift );
		}
		// Cast the result string as a decimal and return
		return new Decimal ( result );
	}

	protected static boolean equal ( Decimal a, Decimal b ) {
		// Get the maximum number of digits in relation to both the Decimal integers
		int max = ( a.length >= b.length ) ? a.length: b.length;
		// Loop though both the integers
		for ( int i = 0; i < max; i++ ) {
			// Check to see if the digits are equal
			if ( a.get ( i ) != b.get ( i ) ) {
				// If they don't match then return false
				return false;
			}
		}
		// Otherwise if all went well, then return true
		return true;
	}

	protected static boolean greaterThan ( Decimal a, Decimal b ) {
		// Get the max number of for the loop
		int max = ( a.length > b.length ) ? a.length : b.length;
		// Loop through that many times
		for ( int i = max - 1; i >= 0; i-- ) {
			// Check to see if a is greater at this point
			if ( a.get ( i ) > b.get ( i ) ) {
				// Return true since it is
				return true;
			}
			// If its the other way around, but not equal
			else if ( a.get ( i ) < b.get ( i ) ) {
				// Return false
				return false;
			}
		}
		// Otherwise return false
		return false;
	}

	protected static boolean lessThan ( Decimal a, Decimal b ) {
		// Get the max number of for the loop
		int max = ( a.length > b.length ) ? a.length : b.length;
		// Loop through that many times
		for ( int i = max - 1; i >= 0; i-- ) {
			// Check to see if a is greater at this point
			if ( a.get ( i ) < b.get ( i ) ) {
				// Return true since it is
				return true;
			}
			// If its the other way around, but not equal
			else if ( a.get ( i ) > b.get ( i ) ) {
				// Return false
				return false;
			}
		}
		// Otherwise return false
		return false;
	}

	protected static boolean greaterThanEqual ( Decimal a, Decimal b ) {
		// Return true if either the equals or greater than operator return true
		return Operation.equal ( a, b ) || Operation.greaterThan ( a, b );
	}


	protected static boolean lessThanEqual ( Decimal a, Decimal b ) {
		// Return true if either the equals or less than operator return true
		return Operation.equal ( a, b ) || Operation.lessThan ( a, b );
	}

	protected static boolean isEven ( Decimal a ) {
		// Preform modulo on least significant digit and see if it is dividable by two
		return ( ( a.digits [ 0 ] % 2 == 0 ) ? true : false );
	}



}
