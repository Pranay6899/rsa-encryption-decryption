package com.rsa_encryption;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;


public class Prime {

	
	private static final String filepath = "primes.rsc";

	private ArrayList <Decimal> primes;

	private long seed = System.currentTimeMillis ();

	protected Prime () {
	// Initialize dynamic array of Decimal instances
	this.primes = new ArrayList <Decimal> ();
	// Try to open the file and read in primes
	try {
	// Get class loader and open resource file with list of primes
	ClassLoader classLoader = getClass ().getClassLoader ();
	InputStream inputStream = classLoader.getResourceAsStream (
	Prime.filepath
	);
	// Initialize scanner instance based on input stream
	Scanner scanner = new Scanner ( inputStream );
	// Loop through until we have no more input
	while ( scanner.hasNextLine () ) {
	// Add this Decimal to the primes array
	this.primes.add ( new Decimal ( scanner.nextLine () ) );
	}
	}
	// If we throw an exception, then catch it
	catch ( Exception exception ) {
	// Exit the program, since we don't want to encounter such an error
	System.exit ( 0 );
	}
	}

	protected Decimal random () {
	// Seed our random number generator
	this.seed *= System.currentTimeMillis ();
	// Apply modulus to our counting seed
	this.seed %= System.currentTimeMillis ();
	// Create a random number generator
	Random generator = new Random ( this.seed );
	// Return a random index from the primes array list
	return this.primes.get ( generator.nextInt ( this.primes.size () ) );
	}

}
