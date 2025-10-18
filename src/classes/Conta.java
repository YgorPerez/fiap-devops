package classes;

public class Conta {
  private int numeroConta;
  private double saldo;

  // Métodos públicos para acessar e modificar os atributos privados
  public int getNumeroConta() {
    return numeroConta;
  }

  public void setNumeroConta(int numeroConta) {
    this.numeroConta = numeroConta;
  }

  public double getSaldo() {
    return saldo;
  }

  protected void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  // Métodos para operações bancárias
  public void depositar(double valor) {
    if(valor > 0){
      saldo += valor;
      System.out.println("Depósito de R$" + valor + " realizado com sucesso.");
    } else {
      System.out.println("Valor de depósito inválido.");
    }
  }

  public void sacar(double valor) {
    if(valor > 0 && saldo >= valor){
      saldo -= valor;
      System.out.println("Saque de R$" + valor + " realizado com sucesso.");
    } else {
      System.out.println("Saldo insuficiente ou valor inválido.");
    }
  }
  public Conta(int numeroConta) {
    this.numeroConta = numeroConta;
    this.saldo = 0.0;
  }

  public void aplicarRendimento() {
  }
}
