import java.util.Scanner;
import java.util.Stack;

public class Exam_Advance_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a 10 digit number: ");
        String isbn = scanner.nextLine();

        if (isbn.length() != 10) {
            System.out.println("Invalid input. ISBN number must be 10 digits.");
            return;
        }

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < isbn.length(); i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            stack.push(digit);
        }

        int sum = 0;
        for (int i = 1; i <= 10; i++) {
            sum += i * stack.pop();
        }

        if (sum % 11 == 0) {
            System.out.println("The number is a valid ISBN.");
        } else {
            System.out.println("The number is not a valid ISBN.");
        }
    }
}