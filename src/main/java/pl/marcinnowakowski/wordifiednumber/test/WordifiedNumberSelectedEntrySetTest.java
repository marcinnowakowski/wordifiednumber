package pl.marcinnowakowski.wordifiednumber.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pl.marcinnowakowski.wordifiednumber.WordifiedNumber;
import pl.marcinnowakowski.wordifiednumber.WordifiedNumberVernacular;

/**
 * Simple test checking wordifier behavior for chosen examples
 * 
 * @author marcin.nowakowski
 *
 */
public class WordifiedNumberSelectedEntrySetTest {

	WordifiedNumber wordified;
	Map<Integer, String> numberToWordsTestSet = new HashMap<Integer, String>(); 
	
	@Before
	public void setUp() {
		wordified = new WordifiedNumberVernacular();
		
		numberToWordsTestSet.put(1, "one");
		numberToWordsTestSet.put(21, "twenty one");
		numberToWordsTestSet.put(105, "one hundred and five");
		numberToWordsTestSet.put(56945781, "fifty six million nine hundred and forty five thousand seven " +
			"hundred and eighty one");
		numberToWordsTestSet.put(660666, "six hundred and sixty thousand six hundred and sixty six");
		numberToWordsTestSet.put(551418078, "five hundred and fifty one million four hundred " +
				"and eighteen thousand seventy eight");
		
	}
	
	@Test
	public void testToWords() {
		
		for(Map.Entry<Integer, String> entry: numberToWordsTestSet.entrySet()) {
			assertEquals("Not equal for number: " + entry.getKey(), 
					entry.getValue(), wordified.toWords(entry.getKey()));
		}
		
	}

}
