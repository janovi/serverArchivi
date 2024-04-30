package org.example;

public class Insegnante {
    private  int matricola;
    private String email;
    private String nome;
    private String cognome;

    private String materia;

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }





    public Insegnante(int matricola, String email, String nome, String cognome , String materia){
        this.matricola=matricola;
        this.email=email;
        this.nome=nome;
        this.cognome=cognome;
        this.materia=materia;
    }

}
