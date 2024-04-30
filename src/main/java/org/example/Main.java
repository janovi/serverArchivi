package org.example;
import spark.ModelAndView;

import spark.template.velocity.VelocityTemplateEngine;


import java.util.HashMap;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        port(8080); // Spark will run on port 8080

        get("/", (request, response)-> "Welcome, Your spark server run at port" + port());
        get("/api/", (request, response)-> {
            String folderPath = "src/main/resources/";
            staticFileLocation(folderPath);

            // Configurazione del motore di template Jade
            VelocityTemplateEngine Engine = new VelocityTemplateEngine();
            // Percorso del file Jade
            String jadeFilePath = "spark";

            // Restituisci il contenuto del file Jade renderizzato come risposta
            return Engine.render(new ModelAndView(null, jadeFilePath));

        });

        path("/api", () -> {


            path("/studenti", () -> {
                get("/", ApiStudente.getAllStudenti);
                get("/:matricola", ApiStudente.getSingoloStudente);
                post("/add",       ApiStudente.addStudente);
                put("/change/:matricola",     ApiStudente.changeStudente);
                delete("/remove/:matricola",  ApiStudente.deleteStudente);
            });

            path("/insegnanti", () -> {
                get("/", ApiInsegnante.getAllInsegnanti);
                get("/:matricola", ApiInsegnante.getSingoloInsegnante);
                post("/add",  ApiInsegnante.addInsegnante);
                put("/change/:matricola",     ApiInsegnante.changeInsegnante);
                delete("/remove/:matricola",  ApiInsegnante.deleteInsegnante);
            });

            path("/tecnico", () -> {
                get("/", ApiTecnico.getAllTecnici);
                get("/:id", ApiTecnico.getSingoloTecnico);
                post("/add",  ApiTecnico.addTecnico);
                delete("/remove/:id",  ApiTecnico.deleteTecnico);
            });

        });
    }

}