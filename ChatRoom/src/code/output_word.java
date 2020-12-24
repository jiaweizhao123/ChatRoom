package code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class output_word {
	private Set<String> set=new HashSet<>();

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}
	public void add(String words) {
		set.add(words);
	}
	public void remove(String words) {
		set.remove(words);
	}
	
}
