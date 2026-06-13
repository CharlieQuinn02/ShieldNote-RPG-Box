package br.ifg.urt.shieldnoterpgbox.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;

@Embeddable
public class Atributo {

    @Min(value = 1, message = "Forca must be at least 1")
    @Column(name = "forca")
    private int forca;

    @Min(value = 1, message = "Destreza must be at least 1")
    @Column(name = "destreza")
    private int destreza;

    @Min(value = 1, message = "Constituicao must be at least 1")
    @Column(name = "constituicao")
    private int constituicao;

    @Min(value = 1, message = "Inteligencia must be at least 1")
    @Column(name = "inteligencia")
    private int inteligencia;

    @Min(value = 1, message = "Sabedoria must be at least 1")
    @Column(name = "sabedoria")
    private int sabedoria;

    @Min(value = 1, message = "Carisma must be at least 1")
    @Column(name = "carisma")
    private int carisma;

    public Atributo() {
        this.forca = 10;
        this.destreza = 10;
        this.constituicao = 10;
        this.inteligencia = 10;
        this.sabedoria = 10;
        this.carisma = 10;
    }

    public Atributo(int forca, int destreza, int constituicao, int inteligencia, int sabedoria, int carisma) {
        this.forca = forca;
        this.destreza = destreza;
        this.constituicao = constituicao;
        this.inteligencia = inteligencia;
        this.sabedoria = sabedoria;
        this.carisma = carisma;
    }

    public int getScore(Ability ability) {
        return switch (ability) {
            case STRENGTH -> this.forca;
            case DEXTERITY -> this.destreza;
            case CONSTITUTION -> this.constituicao;
            case INTELLIGENCE -> this.inteligencia;
            case WISDOM -> this.sabedoria;
            case CHARISMA -> this.carisma;
        };
    }

    public int getModificador(Ability ability) {
        return calcularModificador(getScore(ability));
    }

    public int getForca() { return forca; }
    public void setForca(int forca) { this.forca = forca; }

    public int getDestreza() { return destreza; }
    public void setDestreza(int destreza) { this.destreza = destreza; }

    public int getConstituicao() { return constituicao; }
    public void setConstituicao(int constituicao) { this.constituicao = constituicao; }

    public int getInteligencia() { return inteligencia; }
    public void setInteligencia(int inteligencia) { this.inteligencia = inteligencia; }

    public int getSabedoria() { return sabedoria; }
    public void setSabedoria(int sabedoria) { this.sabedoria = sabedoria; }

    public int getCarisma() { return carisma; }
    public void setCarisma(int carisma) { this.carisma = carisma; }

    public int calcularModificador(int valor) {
        return (int) Math.floor((valor - 10) / 2.0);
    }

    public int getModificadorForca() { return calcularModificador(this.forca); }
    public int getModificadorDestreza() { return calcularModificador(this.destreza); }
    public int getModificadorConstituicao() { return calcularModificador(this.constituicao); }
    public int getModificadorInteligencia() { return calcularModificador(this.inteligencia); }
    public int getModificadorSabedoria() { return calcularModificador(this.sabedoria); }
    public int getModificadorCarisma() { return calcularModificador(this.carisma); }
}

