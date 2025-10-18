package classes;

public class TesteFintech {
  public static void main(String[] args) {
    // Testando ContaCorrente
    ContaCorrente contaCorrente = new ContaCorrente(2002, 1000.0);
    contaCorrente.depositar(500.0);
    contaCorrente.sacar(600.0);
    System.out.println("Saldo atual da Conta Corrente " + contaCorrente.getNumeroConta() + ": R$" + contaCorrente.getSaldo());

    // Testando ContaPoupanca
    ContaPoupanca contaPoupanca = new ContaPoupanca(3003, 0.02);
    contaPoupanca.depositar(1000.0);
    contaPoupanca.aplicarRendimento();
    System.out.println("Saldo atual da Conta Poupança " + contaPoupanca.getNumeroConta() + ": R$" + contaPoupanca.getSaldo());
  }
}
