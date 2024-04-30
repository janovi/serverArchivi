package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//L'ApiInsegnante usa la classe Json Oggetto per rappresentare l'oggeto Insegnante e non la classe Java
public class ApiInsegnante {
    public static Route getSingoloInsegnante = (Request request, Response response)->{

        int matricola = Integer.parseInt(request.params(":matricola"));
        Path path = Paths.get("src/main/java/org/example/insegnante/" + matricola + ".json");
        if (Files.exists(path)) {
            try {
                String json = new String(Files.readAllBytes(path));
                response.status(200);
                response.type("application/json");
                return json;

            } catch (IOException e) {
                e.printStackTrace();
                response.status(500);
                response.body("Errore durante l'estrazione dell' insegnante.");
            }
        } else {
            response.status(404);
            response.body("Cartella non trovata.");
            return "Insegnante non trovato.";

        }
        return "";
    };


    public static Route getAllInsegnanti= (Request request, Response response)->{


        Path path = Paths.get("src/main/java/org/example/insegnante/" );

        if (Files.exists(path)) {
            File[] files = path.toFile().listFiles();
            if (files != null && files.length > 0) {
                StringBuilder jsonBuilder = new StringBuilder("[");
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json")) {
                        try {
                            String json = new String(Files.readAllBytes(file.toPath()));
                            jsonBuilder.append(json).append(",");
                        } catch (IOException e) {
                            e.printStackTrace();
                            response.status(500);
                            response.body("Errore durante l'estrazione degli insegnanti.");
                            return "";
                        }
                    }
                }
                // Rimuovi l'ultima virgola
                if (jsonBuilder.length() > 1) {
                    jsonBuilder.setLength(jsonBuilder.length() - 1);
                }
                jsonBuilder.append("]");
                response.status(200);
                response.type("application/json");
                return jsonBuilder.toString();
            } else {
                response.status(404);
                response.body("Nessun insegnanti trovato nella cartella.");
            }

        } else {
            response.status(404);
            response.body("La cartela studenti non esiste.");
        }

        return ""; // Restituisce una stringa vuota se non si trova lo studente
    };



    public static Route addInsegnante=(Request request, Response response)-> {
        JsonObject req = new Gson().fromJson(request.body(), JsonObject.class);


        Path path = Paths.get("src/main/java/org/example/insegnante/" + req.get("matricola").getAsInt() +".json");
        try{
            Files.write(path,request.body().getBytes());
            //  Files.write(path, new Gson().toJson(insegnante).getBytes());
            response.status(201);
            response.body("Insegnante aggiunto con successo. ");
        } catch (IOException e){
            e.printStackTrace();
            response.status(500);
            response.body("Errore durante l'aggiunto dell' insegnante. ");

        }

        return response.body();
    };




    public static Route changeInsegnante=(Request request, Response response)->{

        int matricola = Integer.parseInt(request.params(":matricola"));
        Path path = Paths.get("src/main/java/org/example/insegnante/" + matricola + ".json");
        if (Files.exists(path)) {
            try {
                Files.write(path, request.body().getBytes());
                response.status(200);
                response.body("insegnante aggiornato con successo.");
            } catch (IOException e) {
                e.printStackTrace();
                response.status(500);
                response.body("Errore durante l'aggiornamento dell' insegnante.");
            }
        } else {
            response.status(404);
            response.body("Insegnante non trovato.");
        }

        return response.body();
    };
    public static Route deleteInsegnante=(Request request, Response response)->{
        int matricola = Integer.parseInt(request.params(":matricola"));
        Path path = Paths.get("src/main/java/org/example/insegnante/" + matricola + ".json");
        try {
            Files.delete(path);
            response.status(200);
            response.body("insegnante eliminato con successo.");
        } catch (IOException e) {
            e.printStackTrace();
            response.status(500);
            response.body("Errore durante l'eliminazione dell' insegnante.");
        }
        return response.body();
    };


}
