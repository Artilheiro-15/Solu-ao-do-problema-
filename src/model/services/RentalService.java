package model.services;

import java.time.Duration;
import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

  private Double pricePerHour;
  private Double priceperDay;

  private BrazilTaxService taxService;

  public RentalService(
    Double pricePerHour,
    Double priceperDay,
    BrazilTaxService taxService
  ) {
    this.pricePerHour = pricePerHour;
    this.priceperDay = priceperDay;
    this.taxService = taxService;
  }

  public void processInvoice(CarRental CarRental) {
    double minutes = Duration
      .between(CarRental.getStart(), CarRental.getFisish())
      .toMinutes();
    double hours = minutes / 60.0;

    double basicPayment;
    if (hours <= 12.0) {
      basicPayment = pricePerHour * Math.ceil(hours);
    } else {
      basicPayment = priceperDay * Math.ceil(hours / 24.0);
    }

    double tax = taxService.tax(basicPayment);

    CarRental.setInvoice(new Invoice(basicPayment, tax));
  }
}
