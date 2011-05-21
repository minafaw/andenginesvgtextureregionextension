package com.larvalabs.svgandroid;

import java.util.ArrayList;

import org.xml.sax.Attributes;

/**
 * @author Larva Labs, LLC
 * @author Nicolas Gramlich
 * @since 16:50:17 - 21.05.2011
 */
public class NumberParser {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public static NumberParserResult parse(final String pString) {
		final int length = pString.length();
		int p = 0;
		final ArrayList<Float> numbers = new ArrayList<Float>();
		boolean skipChar = false;
		for (int i = 1; i < length; i++) {
			if (skipChar) {
				skipChar = false;
				continue;
			}
			final char c = pString.charAt(i);
			switch (c) {
				// This ends the parsing, as we are on the next element
				case 'M':
				case 'm':
				case 'Z':
				case 'z':
				case 'L':
				case 'l':
				case 'H':
				case 'h':
				case 'V':
				case 'v':
				case 'C':
				case 'c':
				case 'S':
				case 's':
				case 'Q':
				case 'q':
				case 'T':
				case 't':
				case 'a':
				case 'A':
				case ')': {
					final String str = pString.substring(p, i);
					if (str.trim().length() > 0) {
						//Util.debug("  Last: " + str);
						final Float f = Float.parseFloat(str);
						numbers.add(f);
					}
					p = i;
					return new NumberParserResult(numbers, p);
				}
				case '\n':
				case '\t':
				case ' ':
				case ',':
				case '-': {
					final String str = pString.substring(p, i);
					// Just keep moving if multiple whitespace
					if (str.trim().length() > 0) {
						//Util.debug("  Next: " + str);
						final Float f = Float.parseFloat(str);
						numbers.add(f);
						if (c == '-') {
							p = i;
						} else {
							p = i + 1;
							skipChar = true;
						}
					} else {
						p++;
					}
					break;
				}
			}
		}
		final String last = pString.substring(p);
		if (last.length() > 0) {
			try {
				numbers.add(Float.parseFloat(last));
			} catch (final NumberFormatException nfe) {
				// Just white-space, forget it
			}
			p = pString.length();
		}
		return new NumberParserResult(numbers, p);
	}

	static NumberParserResult parseFromAttributes(final Attributes pAttributes, final String pAttributeName) {
		final int n = pAttributes.getLength();
		for (int i = 0; i < n; i++) {
			if (pAttributes.getLocalName(i).equals(pAttributeName)) {
				return NumberParser.parse(pAttributes.getValue(i));
			}
		}
		return null;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static class NumberParserResult {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		private final ArrayList<Float> mNumbers;

		private final int mNextCommand;

		// ===========================================================
		// Constructors
		// ===========================================================

		public NumberParserResult(final ArrayList<Float> pNumbers, final int pNextCommand) {
			this.mNumbers = pNumbers;
			this.mNextCommand = pNextCommand;
		}

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		public int getNextCommand() {
			return this.mNextCommand;
		}

		public ArrayList<Float> getNumbers() {
			return this.mNumbers;
		}

		public float getNumber(final int pIndex) {
			return this.mNumbers.get(pIndex);
		}

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}