package pa3;

public class Pa3Test {

	public static void main(String[] passedArguments) {
		// Anagram Tests
		testAnagrams("Anagrams, 1: Single-character", "q", "q");
		testAnagrams("Anagrams, 2: Normal", "creative", "reactive");
		testAnagrams("Anagrams, 3: Mixed Case", "LEAF", "FLEA");
		testAnagrams("Anagrams, 4: Nonsense", "fjekajfeklajfeaxmfjeiao", "ajjeklafeifkjafexmfjeao");
		testAnagrams("Non-Anagrams, 1: Single-character", "f", "z");
		testAnagrams("Non-Anagrams, 2: Normal", "cheese", "bologna");
		testAnagrams("Non-Anagrams, 3: Mixed Case", "GirAFfE", "piZzA");
		testAnagrams("Non-Anagrams, 4: Nonsense", "utieounvmncxfjeamdzn", "tuinamjizeiailea");
	}
	
	private static final void testAnagrams(String passedMessage, String passedFirst, String passedSecond) {
		System.out.println(passedMessage);
		testAnagrams(new Anagram(passedFirst), new Anagram(passedSecond));
		System.out.println();
	}
	
	private static void testAnagrams(Anagram passedFirst, Anagram passedSecond) {
		String whether = (passedFirst.areAnagrams(passedSecond)) ? "ARE" : "ARE NOT";
		System.out.format("\"%s\" and \"%s\" %s anagrams.\n", passedFirst, passedSecond, whether);
	}

}
