package org.example;

import org.example.model.PontoColeta;

public class PontoColetaBO {

    // Método para validar o telefone
    public boolean isValidTelefone(String telefone) {
        // Verifica se o telefone está no formato correto (XX)XXXXX-XXXX ou (XX)XXXX-XXXX
        return telefone != null && telefone.matches("\\(\\d{2}\\)\\d{4,5}-\\d{4}");
    }

    // Método para formatar o telefone (remover parênteses e traço)
    public String formatTelefone(String telefone) {
        if (telefone != null) {
            return telefone.replaceAll("[()\\-]", "");
        }
        return telefone;
    }

    // Método para atualizar o telefone em um PontoColeta com validação
    public boolean updateTelefone(PontoColeta pontoColeta, String novoTelefone) {
        if (isValidTelefone(novoTelefone)) {
            pontoColeta.setTelefone(formatTelefone(novoTelefone));
            return true;
        }
        return false;
    }

    // Método para exibir informações do ponto de coleta
    public String displayPontoColetaInfo(PontoColeta pontoColeta) {
        return "Ponto de Coleta: " + pontoColeta.getName() + ", Telefone: " + pontoColeta.getTelefone();
    }

    public static void main(String[] args) {
        // Exemplo de uso da camada BO
        PontoColeta ponto = new PontoColeta(1, "Ponto A", "Reciclagem", "12345-678", "Rua A", "123", "Bairro A", "Cidade A", "UF", "Complemento A", "(12)34567-8901", "url_imagem");
        PontoColetaBO pontoColetaBO = new PontoColetaBO();


        String novoTelefone = "(12)98765-4321";
        if (pontoColetaBO.updateTelefone(ponto, novoTelefone)) {
            System.out.println("Telefone atualizado com sucesso!");
        } else {
            System.out.println("Telefone inválido.");
        }

        // Exibição de informações do ponto de coleta
        System.out.println(pontoColetaBO.displayPontoColetaInfo(ponto));
    }
}
