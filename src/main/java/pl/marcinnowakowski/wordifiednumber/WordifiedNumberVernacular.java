package pl.marcinnowakowski.wordifiednumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for creating word representation of cardinal numbers
 * using following vernacular dialectic rules:
 * 	- no hyphens are use in between tens and ones
 *  - additional "and" separating word is added between hundreds and tens 
 * 
 * Possible future requirements:
 * 1) This class will be the base for the vernacular sequence number representations
 * There are two solution for a given problem:
 *  1.1) Creating a deriving class which should make dictionary operation on the already word-spelled
 *  cardinal number. This operation will change only last part of the number giving proper it ending.
 *  Example: 
 *     "one million [one]" -> "one million [first]"; 
 *     "two [million]" -> "two [millionth]". 
 *  1.2) Changing following class to enable ending management on the level of triple creation
 *  and then on the level of power of thousand connector addition. Different behavior of the class
 *  should be indicated with the flag given on the method call. This option is not recommended.    
 * 
 * @author marcin.nowakowski
 *
 */
public class WordifiedNumberVernacular implements WordifiedNumber{

	//========================================================//
	//                    DICTIONARIES                        //
	//========================================================//
	
	@SuppressWarnings("serial")
	private final Map<String, String> DICT_ZERO = Collections.unmodifiableMap( 
		(new HashMap<String, String>()
			{ public Map<String, String> initDictionary() {
				put("0", "zero");
		    return this; } }).initDictionary());
	
	@SuppressWarnings("serial")
	private final Map<String, String> DICT_ONES = Collections.unmodifiableMap( 
		(new HashMap<String, String>()
			{ public Map<String, String> initDictionary() {
				put("1", "one");
				put("2", "two");
		        put("3", "three"); 
		        put("4", "four");
		        put("5", "five");
		        put("6", "six"); 
		        put("7", "seven");
		        put("8", "eight");
		        put("9", "nine");
		    return this; } }).initDictionary());
	
	@SuppressWarnings("serial")
	private final Map<String, String> DICT_TEENS = Collections.unmodifiableMap( 
		(new HashMap<String, String>()
			{ public Map<String, String> initDictionary() {
				put("0", "ten");
				put("1", "eleven");
				put("2", "twelve");
		        put("3", "thirteen"); 
		        put("4", "fourteen");
		        put("5", "fifteen");
		        put("6", "sixteen"); 
		        put("7", "seventeen");
		        put("8", "eighteen");
		        put("9", "nineteen");
		    return this; } }).initDictionary());
	
	@SuppressWarnings("serial")
	private final Map<String, String> DICT_TENS = Collections.unmodifiableMap( 
		(new HashMap<String, String>()
			{ public Map<String, String> initDictionary() { 
				put("2", "twenty");
		        put("3", "thirty"); 
		        put("4", "forty");
		        put("5", "fifty");
		        put("6", "sixty"); 
		        put("7", "seventy");
		        put("8", "eighty");
		        put("9", "ninety");
		    return this; } }).initDictionary());
	
	@SuppressWarnings("serial")
	private final Map<String, String> DICT_POWERS_OF_TEN = Collections.unmodifiableMap( 
		(new HashMap<String, String>()
			{ public Map<String, String> initDictionary() { 
		        put("2", "hundred"); 
		        put("3", "thousand");
		        put("6", "million");
		    return this; } }).initDictionary());
	
	@SuppressWarnings("serial")
	private final Map<String, String> DICT_CONNECTORS = Collections.unmodifiableMap( 
		(new HashMap<String, String>()
			{ public Map<String, String> initDictionary() { 
				put("01", " "); // connector between one and tens
				put("12", " and "); // connector between hundreds
				put("2", " "); // connector for hundreds
				put("23", " "); // connector between hundreds and thousands
				put("3", " "); // connector for thousands
		    return this; } }).initDictionary());
	
	
	//========================================================//
	//                       METHODS                          //
	//========================================================//
	
	/**
	 * Builds word representation of a number based on triple separation.
	 * Every triple: ones, thousands, millions is served separately.
	 * Each triple is proceeded with number translator
	 * and enriched with key word giving it proper position in hierarchy of power of ten.
	 * 
	 */
	public String toWords(int number) {
		
		if (number < 0 || number > 999999999) {
			throw new IllegalArgumentException("Number out of range 0-999999999");
		}
		
		// create triple list
		List<String> listOfTriples = cutInTriples(completeNumberWithLeadingZeros("" + number));
		Collections.reverse(listOfTriples);
		
		// iterate over the list
		int powerOfTen = 0;
		StringBuilder result = new StringBuilder();
		for(String triple : listOfTriples) {
			StringBuilder convertedTriple = getWordRepresentationFromTriple(triple);
			if (convertedTriple.length() > 0) {
				// separetor between triple if needed
				if(result.length() > 0) {
					result.insert(0, DICT_CONNECTORS.get("23"));
				}
				if (powerOfTen > 0) {
					// thousands and millions
					result.insert(0, DICT_POWERS_OF_TEN.get("" + powerOfTen));
					result.insert(0, DICT_CONNECTORS.get("3"));
					result.insert(0, convertedTriple);
				} else {  
					// below thousand
					result.insert(0, convertedTriple);
				}
			}
			
			powerOfTen += 3;
		}
		
		// if the result is empty it means it's zero
		if(result.length() == 0) {
			result.append(DICT_ZERO.get("0"));
		}
		
		return result.toString();
	}
	
	/**
	 * If length of number representation is not congruent to 0 mod 3 
	 * then leading zeros are added
	 * 
	 * @param triple
	 * @return
	 */
	private String completeNumberWithLeadingZeros(String number) {
		
		switch(number.length() % 3) {
			case 1: 
				number = "00" + number;
			break;
			case 2:
				number = "0" + number;
			break;
			default:
		}
		
		return number;
	}
	
	/**
	 * Split number representation into triples
	 * 
	 * @param number - representation of a number which length is a multiplicand of 3
	 * @return - list of strings of length 3 representing power of thousand parts of the number   
	 */
	private List<String> cutInTriples(String number) {
		
		List<String> listOfTriples = new ArrayList<String>();
		for(int startIndex = 0, endIndex = 3; ; startIndex += 3, endIndex += 3) {
			if (endIndex < number.length()) {
				listOfTriples.add(number.substring(startIndex, endIndex));
			} else {
				listOfTriples.add(number.substring(startIndex));
				break;
			}
		}
		
		return listOfTriples;
		
	}

	/**
	 * Creates full word representation for a digit triple.
	 * 
	 * Example: "123" -> "one hundred and twenty two" 
	 * 
	 * @param triple - string representation of a number containing exactly three digits, 
	 *  if the number represented by digits is smaller then one hundred leading zeros should be used.
	 *    
	 * @return - word representation of a number
	 */
	private StringBuilder getWordRepresentationFromTriple(String triple){
		
		StringBuilder words = new StringBuilder();
		
		//hundreds
		if(triple.charAt(0) != '0') {
			words.append(DICT_ONES.get(triple.substring(0, 1)));
			words.append(DICT_CONNECTORS.get("2"));
			words.append(DICT_POWERS_OF_TEN.get("2"));
		}
		//between hundreds and tens
		if(triple.charAt(0) != '0' && (triple.charAt(1) != '0' || triple.charAt(2) != '0')) {
			words.append(DICT_CONNECTORS.get("12"));
		}
		//tens
		if(triple.charAt(1) != '0') {
			if(triple.charAt(1) == '1') {
				//teens
				words.append(DICT_TEENS.get(triple.substring(2, 3)));
			} else {
				//tens
				words.append(DICT_TENS.get(triple.substring(1, 2)));
			}
		}
		//between tens and ones
		if(triple.charAt(1) != '0' && triple.charAt(1) != '1' && triple.charAt(2) != '0') {
			words.append(DICT_CONNECTORS.get("01"));
		}
		//ones but not teens
		if(triple.charAt(2) != '0' &&  triple.charAt(1) != '1') {
			words.append(DICT_ONES.get(triple.substring(2, 3)));
		}
		
		return words;
		
	}

}
