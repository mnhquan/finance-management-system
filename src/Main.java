import repository.InMemoryTransactionRepository;
import repository.TransactionRepository;
import service.TransactionService;
import ui.ConsoleUI;

void main() {
    TransactionRepository repository = new InMemoryTransactionRepository();
    TransactionService service = new TransactionService(repository);
    ConsoleUI ui = new ConsoleUI(service);
    ui.start();
}