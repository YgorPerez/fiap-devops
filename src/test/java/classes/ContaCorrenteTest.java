package classes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ContaCorrenteTest {
    private ContaCorrente contaCorrente;

    @BeforeEach
    void setUp() {
        contaCorrente = new ContaCorrente(2002, 1000.0);
    }

    @Test
    void testConstrutor() {
        assertEquals(2002, contaCorrente.getNumeroConta());
        assertEquals(1000.0, contaCorrente.getLimite(), 0.001);
        assertEquals(0.0, contaCorrente.getSaldo(), 0.001);
    }

    @Test
    void testSetAndGetLimite() {
        double novoLimite = 2000.0;
        contaCorrente.setLimite(novoLimite);
        assertEquals(novoLimite, contaCorrente.getLimite(), 0.001);
    }

    @Test
    void testSacarComSaldoSuficiente() {
        contaCorrente.depositar(500.0);
        contaCorrente.sacar(300.0);
        assertEquals(200.0, contaCorrente.getSaldo(), 0.001);
    }

    @Test
    void testSacarUsandoLimite() {
        // Saldo: 0, Limite: 1000, Total disponível: 1000
        contaCorrente.sacar(500.0);
        assertEquals(-500.0, contaCorrente.getSaldo(), 0.001);
    }

    @Test
    void testSacarAcimaDoLimite() {
        // Saldo: 0, Limite: 1000, Total disponível: 1000
        double saldoInicial = contaCorrente.getSaldo();
        contaCorrente.sacar(1500.0);
        assertEquals(saldoInicial, contaCorrente.getSaldo(), 0.001);
    }

    @Test
    void testSacarValorZero() {
        contaCorrente.depositar(100.0);
        double saldoAntes = contaCorrente.getSaldo();
        contaCorrente.sacar(0.0);
        assertEquals(saldoAntes, contaCorrente.getSaldo(), 0.001);
    }

    @Test
    void testSacarValorNegativo() {
        contaCorrente.depositar(100.0);
        double saldoAntes = contaCorrente.getSaldo();
        contaCorrente.sacar(-50.0);
        assertEquals(saldoAntes, contaCorrente.getSaldo(), 0.001);
    }

    @Test
    void testDepositar() {
        // Herda o método depositar da classe pai
        contaCorrente.depositar(200.0);
        assertEquals(200.0, contaCorrente.getSaldo(), 0.001);
    }

    @Test
    void testLimiteZero() {
        ContaCorrente contaSemLimite = new ContaCorrente(3003, 0.0);
        assertEquals(0.0, contaSemLimite.getLimite(), 0.001);

        contaSemLimite.depositar(100.0);
        contaSemLimite.sacar(50.0);
        assertEquals(50.0, contaSemLimite.getSaldo(), 0.001);
    }
}
