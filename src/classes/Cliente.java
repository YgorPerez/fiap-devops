package classes;

public class Cliente {
  private String nome;
  private String cpf;

  // Métodos públicos para acessar e modificar os atributos privados
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }
}
