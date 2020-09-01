import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Automaton {
	int cont = 0;
	public String input = "", expression = "", word = "";
	public ArrayList<String> arrayExpressions;
	Set<Character> s = new HashSet<Character>();
	public int state = 0, index = 0, indexExpression = 0, initialPosition = 0, sizeLock = 0;

	public void automaton() {
		while (true) {
			switch (state) {
			case 0:
				state0();
				break;
			case 1:
				state1();
				break;
			case 3:
				return;
			default:
				return;
			}
		}

	}

	public void state0() {
		if (input.length() - 1 < index) {
			state = 3;
		} else {
			if (s.contains(input.charAt(index))) {
				initialPosition = index;
				state = 1;
			} else {
				index++;
			}
		}
	}

	public void state1() {
		sizeLock = 0;
		for (int i = 0; i < arrayExpressions.size(); i++) {
			for (int j = 0; j < arrayExpressions.get(i).length(); j++) {
				if (input.length() - 1 < index) {
					index = initialPosition;
					break;
				}

				if (input.charAt(index) != arrayExpressions.get(i).charAt(j)) {
					index = initialPosition;
					sizeLock = 0;
					break;
				}

				if (j < arrayExpressions.get(i).length() - 1) {
					if (arrayExpressions.get(i).charAt(j + 1) == '*') {
						state2(i, j);
						j++;
					}
				}

				if (j >= arrayExpressions.get(i).length() - 1) {
					input = input.substring(0, initialPosition) + word + input.substring(initialPosition
							+ arrayExpressions.get(i).length() - lockOcurrences(arrayExpressions.get(i)) + sizeLock);
//					System.out.println(input);

					index = initialPosition + word.length();
//					System.out.println(initialPosition);
//					System.out.println(word.length());
//					System.out.println(sizeLock);
//					System.out.println(index);
//					System.out.println("");
					state = 0;
//					cont++;
//					if(cont == 3) {
//						state = 3;
//						return;
//					}
					return;
				}
				index++;
			}
		}
		index = initialPosition + 1;
		state = 0;

	}

	public void state2(int i, int j) {
		while (input.length() - 1 >= index) {
			if (input.charAt(index) == arrayExpressions.get(i).charAt(j)) {
				index++;
				sizeLock++;
			} else {
				index--;
				break;
			}
		}
	}

	public void inputAutomaton() {
		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();
		expression = sc.nextLine();
		word = sc.nextLine();

		expression = expression.replace("+", ",");
		arrayExpressions = new ArrayList<>(Arrays.asList(expression.split(",")));

		String tmp = "";
		for (int i = 0; i < arrayExpressions.size(); i++) {
			tmp = "";
			for (int j = 0; j < arrayExpressions.get(i).length(); j++) {
				if (arrayExpressions.get(i).charAt(j) == '*') {
					tmp = arrayExpressions.get(i).substring(0, j - 1)
							+ arrayExpressions.get(i).substring(j + 1, arrayExpressions.get(i).length());
					if (!tmp.equals("")) {
						arrayExpressions.add(tmp);
					}
				}
			}
		}

//		for (int i = 0; i < arrayExpressions.size(); i++) {
//			System.out.println(arrayExpressions.get(i));
//		}

		for (int i = 0; i < arrayExpressions.size(); i++) {
			s.add(arrayExpressions.get(i).charAt(0));
		}
	}

	public int lockOcurrences(String a) {
		int count = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) == '*') {
				count++;
			}
		}
		return count * 2;
	}
}