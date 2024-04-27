import java.util.Scanner;

public class Editor {

    public static void main(String[] args) {
        Actions action = new Actions(100);
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();
            String[] str = choice.split("\\s+");
            String command = str[0];
            switch (command) {
                case "ins" : int lineNoToInsert = Integer.parseInt(str[1]);
                             StringBuilder sb = new StringBuilder();
                             for(int i=2; i<str.length; i++) {
                                 if (!sb.isEmpty())
                                     sb.append(" ").append(str[i]);
                                 else
                                     sb.append(str[i]);
                             }
                             String text = sb.toString();
                             action.insert(lineNoToInsert, text);
                             break;
                case "del" : int lineNoToDelete = Integer.parseInt(str[1]);
                             action.delete(lineNoToDelete);
                             break;
                case "list" : action.list();
                              break;
                case "save" : action.save();
                              break;
                case "quit" : action.quit();
                              break;
                default : System.out.println("Invalid choice");
            }
        }
    }
}