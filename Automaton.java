import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Automaton {
	//Atributos
	public String input = "", 
			      expression = "", 
			      word = "";
	
	public int state = 0, 
			   index = 0, 
			   initialPosition = 0, 
			   sizeLock = 0;

	public ArrayList<String> arrayExpressions;
	Set<Character> s = new HashSet<Character>();

	//Automata
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

	//Estado 0 (Checar si un caracter de la cadena coincide con al menos uno de los componentes formados
	//          por la expresión regular)
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

	//Estado 1 (si alguna parte de la cadena coincide completamente con alguno de los componentes
	//          se reemplaza la cadena)
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

					index = initialPosition + word.length();
					state = 0;
					return;
				}
				index++;
			}
		}
		index = initialPosition + 1;
		state = 0;

	}

	//Estado 2 (Cerradura)
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
		//Recibir 3 inputs principales(cadena, expresión regular, cadena reemplazadora)
		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();
		expression = sc.nextLine();
		word = sc.nextLine();
		
		//Se descompone la expresión regular en componentes y se almacenan dentro de un ArrayList
		expression = expression.replace("+", ",");
		arrayExpressions = new ArrayList<>(Arrays.asList(expression.split(",")));

		// Si alguno de los componentes contiene uno o más símbolos de "*" de descompone este mismo en más subcomponentes
		// y se almacenan dentro de la misma ArrayList
		// Ejemplo: de aba* se obtiene ab
		//             c*b*a se obtiene b*a c*a y a
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

		//Se almacena dentro de un set solo el primer caracter de los componentes
		//formados anteriormente
		for (int i = 0; i < arrayExpressions.size(); i++) {
			s.add(arrayExpressions.get(i).charAt(0));
		}
	}
	
	//Método que saca el numero de ocurrencias de "*"
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