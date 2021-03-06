//File ready for static review - John Galvin 11330960
import java.util.Scanner;


public class PayFineUi {
    public enum PayFineUserInterfaceState { INITIALISED, READY, PAYING, COMPLETED, CANCELLED }
    private PayFineControl control;
    private Scanner input;
    private PayFineUserInterfaceState state;


    public PayFineUi(PayFineControl control) {
        this.control = control;
        input = new Scanner(System.in);
        state = PayFineUserInterfaceState.INITIALISED;
        control.setUserInterface(this);
    }


    public void setState(PayFineUserInterfaceState state) {
        this.state = state;
    }


    public void run() {
        output("Pay Fine Use Case UI\n");
        while (true) {
            switch (state) {
            case READY:
                String memberCard = input("Swipe Member card (press <enter> to cancel): ");
                if (memberCard.length() == 0) {
                    control.cancel();
                    break;
                }
                try {
                    int memberId = Integer.valueOf(memberCard);
                    control.cardSwiped(memberId);
                } catch (NumberFormatException e) {
                    output("Invalid memberId");
                }
                break;
            case PAYING:
                double amount = 0;
                String fineAmount = input("Enter amount (<Enter> cancels) : ");
                if (fineAmount.length() == 0) {
                    control.cancel();
                    break;
                } try {
                    amount = Double.valueOf(fineAmount);
                }
                catch (NumberFormatException e) {
                    output("Invalid number format.");
                }
                if (amount <= 0) {
                    output("Amount must be positive");
                    break;
                }
                control.payFine(amount);
                break;
            case CANCELLED:
                output("Pay Fine process cancelled");
                return;
            case COMPLETED:
                output("Pay Fine process complete");
                return;
            default:
                output("Unhandled state");
                throw new RuntimeException("FixBookUi : unhandled state :" + state);
            }
        }
    }


    private String input(String userInput) {
        System.out.print(userInput);
        return input.nextLine();
    }


    private void output(Object outputObject) {
        System.out.println(outputObject);
    }


    public void display(Object displayObject) {
        output(displayObject);
    }
}
