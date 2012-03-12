package pl.marcinnowakowski.wordifiednumber;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class prepared to translate between classical and selected vernacular
 * representation of English numbers
 * 
 * @author marcin nowakowski
 * 
 */
public class NumberTranslatorClassicalVernacularEnglish {

	@SuppressWarnings("serial")
	private final Set<String> REVERT_DICT_ONES = Collections.unmodifiableSet( 
		(new HashSet<String>()
			{ public Set<String> initDictionary() {
				add("one");
				add("two");
		        add("three"); 
		        add("four");
		        add("five");
		        add("six"); 
		        add("seven");
		        add("eight");
		        add("nine");
		    return this; } }).initDictionary());
	
	@SuppressWarnings("serial")
	private final Set<String> REVERT_DICT_TENS = Collections.unmodifiableSet( 
		(new HashSet<String>()
			{ public Set<String> initDictionary() {
				add("twenty");
		        add("thirty"); 
		        add("forty");
		        add("fifty");
		        add("sixty"); 
		        add("seventy");
		        add("eighty");
		        add("ninety");
		    return this; } }).initDictionary());
	
	/**
	 * translates vernacular -> classical
	 * 
	 * @param number
	 *            - vernacular representation of the number
	 * @return - classical representation of a number
	 */
	public String toClassical(String number) {

		// remove ands
		number = number.replaceAll(" and ", " ");

		// add hyphens
		String[] tokens = number.split(" ");
		StringBuilder result = new StringBuilder(tokens[0]);
		for (int i = 1; i < tokens.length; i++) {
			if (REVERT_DICT_TENS.contains(tokens[i-1])
					&& REVERT_DICT_ONES.contains(tokens[i])) {
				result.append("-").append(tokens[i]);
			} else {
				result.append(" ").append(tokens[i]);
			}
			
		}

		return result.toString();
	}

	/**
	 * translates classical -> vernacular
	 * 
	 * @param number
	 *            - classical representation of the number
	 * @return - vernacular representation of a number
	 */
	public String toVernacular(String number) {
		
		// remove hyphens
		number = number.replaceAll("-", " ");

		// add ands
		String[] tokens = number.split(" ");
		StringBuilder result = new StringBuilder(tokens[0]);
		for (int i = 1; i < tokens.length; i++) {
			if (tokens[i-1].equals("hundred") && !tokens[i].equals("thousand")
					&& !tokens[i].equals("million")) {
				result.append(" and ").append(tokens[i]);
			} else {
				result.append(" ").append(tokens[i]);
			}
			
		}

		return result.toString();
	}

}
