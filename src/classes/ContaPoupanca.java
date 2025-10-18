package classes;

public class ContaPoupanca extends Conta {
  private double taxaJuros;

  // Construtor da subclasse
  public ContaPoupanca(int numeroConta, double taxaJuros) {
    super(numeroConta);
    this.taxaJuros = taxaJuros;
  }

  // Métodos acessores para 'taxaJuros'
  public double getTaxaJuros() {
    return taxaJuros;
  }

  public void setTaxaJuros(double taxaJuros) {
    this.taxaJuros = taxaJuros;
  }
}
