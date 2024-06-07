package org.example.model;
import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"idColeta","name","type","cep","logradouro","numero","bairro","cidade","uf","complemento","telefone","imagem_url"})
public class PontoColeta {
    private int idColeta;
    private String name;
    private String type;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;
    private String telefone;
    private String imagemUrl;
    public PontoColeta() {
    }

    public PontoColeta(int idColeta, String name, String type, String cep, String logradouro, String numero, String bairro, String cidade, String uf, String complemento, String telefone, String imagemUrl) {
        this.idColeta = idColeta;
        this.name = name;
        this.type = type;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.complemento = complemento;
        this.telefone = telefone;
        this.imagemUrl = imagemUrl;
    }

    public int getidColeta() {
        return idColeta;
    }

    public void setidColeta(int idColeta) {
        this.idColeta = idColeta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    @Override
    public String toString() {
        return "PontoColeta{" +
                "idColeta=" + idColeta +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", uf='" + uf + '\'' +
                ", complemento='" + complemento + '\'' +
                ", telefone='" + telefone + '\'' +
                ", imagemUrl='" + imagemUrl + '\'' +
                '}';
    }
}
