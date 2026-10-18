import repository.InMemoryTransactionRepository;
import repository.PostgreSQLTransactionRepository;
import repository.TransactionRepository;
import service.TransactionService;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        TransactionRepository repository = new PostgreSQLTransactionRepository();
        TransactionService service = new TransactionService(repository);
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}