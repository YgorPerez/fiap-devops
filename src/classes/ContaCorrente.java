package classes;

public class ContaCorrente extends Conta {
  private double limite;

  // Construtor da subclasse
  public ContaCorrente(int numeroConta, double limite) {
    super(numeroConta);
    this.limite = limite;
  }

  public void sacar(double valor) {
    double saldo = 0;
    if(valor > 0 && (saldo + limite) >= valor){
      saldo -= valor;
      System.out.println("Saque de R$" + valor + " realizado na conta " + getNumeroConta());
    } else {
      System.out.println("Saldo insuficiente ou valor inválido.");
    }
  }

  // Métodos acessores para 'limite'
  public double getLimite() {
    return limite;
  }

  public void setLimite(double limite) {
    this.limite = limite;
  }
}
