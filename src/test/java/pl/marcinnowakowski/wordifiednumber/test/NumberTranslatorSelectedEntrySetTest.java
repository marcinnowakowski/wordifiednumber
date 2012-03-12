package pl.marcinnowakowski.wordifiednumber.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pl.marcinnowakowski.wordifiednumber.NumberTranslatorClassicalVernacularEnglish;

/**
 * Simple test checking translator behavior for chosen examples
 * 
 * @author marcin.nowakowski
 *
 */
public class NumberTranslatorSelectedEntrySetTest {

	NumberTranslatorClassicalVernacularEnglish numberTranslator;
	Map<String, String> classicalToVernacularTranslationTestSet = new HashMap<String, String>(); 
	
	@Before
	public void setUp() {
		numberTranslator = new NumberTranslatorClassicalVernacularEnglish();
		
		classicalToVernacularTranslationTestSet.put("one", "one");
		classicalToVernacularTranslationTestSet.put("twenty-one", "twenty one");
		classicalToVernacularTranslationTestSet.put("one hundred five", "one hundred and five");
		classicalToVernacularTranslationTestSet.put(
			"fifty-six million nine hundred forty-five thousand seven " +
				"hundred eighty-one", 
			"fifty six million nine hundred and forty five thousand seven " +
				"hundred and eighty one");
		classicalToVernacularTranslationTestSet.put(
			"six hundred sixty thousand six hundred sixty-six",
				"six hundred and sixty thousand six hundred and sixty six");
	}
	
	@Test
	public void testTranslationVernacular2Classical() {
		
		for(Map.Entry<String, String> entry: classicalToVernacularTranslationTestSet.entrySet()) {
			assertEquals("Translation not equal for number: " + entry.getKey(), 
					entry.getKey(), numberTranslator.toClassical((entry.getValue())));
		}

	}
	
	@Test
	public void testTranslationClassical2Vernacular() {
		
		for(Map.Entry<String, String> entry: classicalToVernacularTranslationTestSet.entrySet()) {
			assertEquals("Translation not equal for number: " + entry.getKey(), 
					entry.getValue(), numberTranslator.toVernacular((entry.getKey())));
		}

	}

}
