package com.rsa_encryption;

import java.io.StringWriter;
import java.io.PrintWriter;


@SuppressWarnings ( "serial" )
public class RSAException extends Exception {

	protected static final String RESET = "\033[49;39m";

	protected static final String ERROR = "\033[31;49m";


	protected static final String header = ERROR + "RSAException: " + RESET;

	protected RSAException () {
		// Call the super constructor first
		super ();
		// Alert user that an unknown exception was thrown
		System.out.println ( header + "Unknown exception was thrown!" );
		// Print the stack trace
		StringWriter errors = new StringWriter ();
		super.printStackTrace ( new PrintWriter ( errors ) );
		String stack = errors.toString ();
		System.out.print ( stack.substring ( stack.indexOf ('\n') + 1 ) );
	}

	
	protected RSAException ( String message ) {
		// Call the super constructor first
		super ( message );
		// Print out the exception's message
		System.out.println ( header + message );
		// Print the stack trace
		StringWriter errors = new StringWriter ();
		super.printStackTrace ( new PrintWriter ( errors ) );
		String stack = errors.toString ();
		System.out.println ( stack.substring ( stack.indexOf ('\n') + 1 ) );
	}

}
