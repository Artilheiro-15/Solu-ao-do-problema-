import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class App {

  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    Locale.setDefault(Locale.US);
    System.out.println("====================================================");

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    System.out.println("Entre com os dados do aluguel: ");
    System.out.print("Modelo do carro: ");
    String carModel = sc.nextLine();
    System.out.print("Retirada (dd/MM/yyyy hh:mm): ");
    LocalDateTime start = LocalDateTime.parse(sc.nextLine(), fmt);
    System.out.print("Retorno (dd/MM/yyyy hh:mm): ");
    LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), fmt);

    CarRental cr = new CarRental(start, finish, new Vehicle(carModel));

    System.out.print("Entre com o preço por hora: ");
    double pricePerHour = sc.nextDouble();
    System.out.print("Entre com o preço por dia: ");
    double priceperDay = sc.nextDouble();

    RentalService rentalService = new RentalService(
      pricePerHour,
      priceperDay,
      new BrazilTaxService()
    );

    rentalService.processInvoice(cr);

    System.out.println("====================================================");
    System.out.println("FATURA: ");
    System.out.println(
      "Pagamento basico: " +
      String.format("%.2f", cr.getInvoice().getBasicPayment())
    );
    System.out.println(
      "IMPOSTO: " + String.format("%.2f", cr.getInvoice().getTax())
    );
    System.out.println(
      "Pagamento total: " +
      String.format("%.2f", cr.getInvoice().getTotalPayment())
    );

    System.out.println("====================================================");
    sc.close();
  }
}
