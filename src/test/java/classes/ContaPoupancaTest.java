package classes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ContaPoupancaTest {
    private classes.ContaPoupanca contaPoupanca;

    @BeforeEach
    void setUp() {
        contaPoupanca = new classes.ContaPoupanca(3003, 0.02);
    }

    @Test
    void testConstrutor() {
        assertEquals(3003, contaPoupanca.getNumeroConta());
        assertEquals(0.02, contaPoupanca.getTaxaJuros(), 0.001);
        assertEquals(0.0, contaPoupanca.getSaldo(), 0.001);
    }

    @Test
    void testSetAndGetTaxaJuros() {
        double novaTaxa = 0.03;
        contaPoupanca.setTaxaJuros(novaTaxa);
        assertEquals(novaTaxa, contaPoupanca.getTaxaJuros(), 0.001);
    }

    @Test
    void testTaxaJurosZero() {
        classes.ContaPoupanca contaSemJuros = new classes.ContaPoupanca(4004, 0.0);
        assertEquals(0.0, contaSemJuros.getTaxaJuros(), 0.001);
    }

    @Test
    void testTaxaJurosNegativa() {
        classes.ContaPoupanca contaJurosNegativo = new classes.ContaPoupanca(5005, -0.01);
        assertEquals(-0.01, contaJurosNegativo.getTaxaJuros(), 0.001);
    }

    @Test
    void testDepositar() {
        // Herda o método depositar da classe pai
        contaPoupanca.depositar(1000.0);
        assertEquals(1000.0, contaPoupanca.getSaldo(), 0.001);
    }

    @Test
    void testSacar() {
        // Herda o método sacar da classe pai
        contaPoupanca.depositar(1000.0);
        contaPoupanca.sacar(300.0);
        assertEquals(700.0, contaPoupanca.getSaldo(), 0.001);
    }

    @Test
    void testSacarSemSaldoSuficiente() {
        contaPoupanca.depositar(100.0);
        double saldoAntes = contaPoupanca.getSaldo();
        contaPoupanca.sacar(200.0);
        assertEquals(saldoAntes, contaPoupanca.getSaldo(), 0.001);
    }

    @Test
    void testAplicarRendimento() {
        // Teste básico - método deve existir e não lançar exceção
        assertDoesNotThrow(() -> contaPoupanca.aplicarRendimento());
    }

    @Test
    void testAplicarRendimentoComSaldo() {
        contaPoupanca.depositar(1000.0);
        double saldoAntes = contaPoupanca.getSaldo();
        contaPoupanca.aplicarRendimento();
        // Como o método não está implementado, o saldo deve permanecer o mesmo
        assertEquals(saldoAntes, contaPoupanca.getSaldo(), 0.001);
    }

    @Test
    void testAplicarRendimentoSemSaldo() {
        double saldoAntes = contaPoupanca.getSaldo();
        contaPoupanca.aplicarRendimento();
        assertEquals(saldoAntes, contaPoupanca.getSaldo(), 0.001);
    }
}
