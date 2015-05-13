
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author Richard Luby, Copyright 2013
 */
/**
 * model and processor for the data contained in the program
 */
public class RPNModel {

	/**
	 * special commands for the program
	 */
	final public static class COMMANDS {

		/**
		 * clear the entire stack
		 */
		final public static char CLEAR_STACK = 'c';
		/**
		 * swap the top two elements of the stack
		 */
		final public static char SWAP_TOP_STACK = 's';
	}
	/**
	 * the stack for the program
	 */
	private Deque<Double> stack;

	/**
	 * creates and returns a basic dataModel
	 */
	public RPNModel() {
		stack = new LinkedList<Double>();
	}

	/**
	 * parses an action based on the top two elements in the stack, and on the
	 * operator/command given
	 * <p>
	 * @param operator the operator to use on the top two stack items
	 * @return returns the result of an arithmetic operation
	 * @throws NoSuchElementException thrown when there are not enough items in
	 *                                the stack
	 */
	public double parseAction(char operator) throws NoSuchElementException {
		double result = 0;
		if (!stack.isEmpty()) {
			double a = stack.pop(), b = 0;
			if (!stack.isEmpty()) {
				b = stack.pop();//NOTE: this is used to ensure that there can be both items before popping 'a' off the stack
			} else {
				stack.push(a);//replace 'a' in the stack
				throw new NoSuchElementException("Not enough elements in stack");
			}
			switch (operator) {
				case '+':
					result = b + a;
					stack.push(result);
					break;
				case '-':
					result = b - a;
					stack.push(result);
					break;
				case '*':
					result = b * a;
					stack.push(result);
					break;
				case '/':
					result = b / a;
					stack.push(result);
					break;
				case 'M'://mod the top two items
					result = b % a;
					stack.push(result);
					break;
				//break;
			}
		} else {
			throw new NoSuchElementException("Empty Stack");
		}
		return result;
	}

	/**
	 * pushes a number onto the stack
	 * <p>
	 * @param num the number to push onto the stack
	 */
	public void push(double num) {
		stack.push(num);
	}

	/**
	 * returns the top two items on the stack
	 * <p>
	 * @return returns top two items on the stack
	 */
	public double[] getTopTwoStackElements() {
		if (!stack.isEmpty()) {
			double a = stack.pop(), b = -1;
			if (stack.isEmpty()) {
				stack.push(a);
				return null;
			}
			b = stack.pop();
			stack.push(b);
			stack.push(a);
			return new double[]{a, b};
		} else {
			return null;
		}
	}
}
