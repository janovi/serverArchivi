package org.example;

public class Studente {
    private  int matricola;
    private String nome;
    private String Cognome;
    private String Corso;


    public Studente(int matricola, String nome, String cognome , String corso){
        this.matricola=matricola;
        this.nome=nome;
        this.Cognome=cognome;
        this.Corso=corso;
    }

    public String getCorso() {
        return Corso;
    }

    public void setCorso(String corso) {
        Corso = corso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }
}
