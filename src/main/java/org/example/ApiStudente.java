package org.example;
import spark.Request;
import spark.Response;
import spark.Route;
import com.google.gson.Gson;
import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static spark.Spark .*;

//L'ApiStudente usa la classe Json Oggetto per rappresentare l'oggeto Studente e non la classe Java
public class ApiStudente {


    public static Route getAllStudenti=(Request request, Response response)->{

            Path path = Paths.get("src/main/java/org/example/studente/" );

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
                                response.body("Errore durante l'estrazione degli studenti.");
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
                    response.body("Nessun studenti trovato nella cartella.");
                }

    } else {
                response.status(404);
                response.body("La cartela studenti non esiste.");
            }

            return ""; // Restituisce una stringa vuota se non si trova lo studente
        };


    public static Route getSingoloStudente=(Request request, Response response)->{
        int matricola = Integer.parseInt(request.params(":matricola"));
        Path path = Paths.get("src/main/java/org/example/studente/" + matricola + ".json");
        if (Files.exists(path)) {
            try {
                String json = new String(Files.readAllBytes(path));
                response.status(200);
                response.type("application/json");
                return json;

            } catch (IOException e) {
                e.printStackTrace();
                response.status(500);
                response.body("Errore durante l'estrazione dello studente.");
            }
        } else {
            response.status(404);
            response.body("file non trovata");
            return "Studente non trovato.";
        }
        return "";
    };
    public static Route deleteStudente=(Request request, Response response)->{
        int matricola = Integer.parseInt(request.params(":matricola"));
        Path path = Paths.get("src/main/java/org/example/studente/" + matricola + ".json");
        try {
            Files.delete(path);
            response.status(200);
            response.body("Studente eliminato con successo.");
        } catch (IOException e) {
            e.printStackTrace();
            response.status(500);
            response.body("Errore durante l'eliminazione dello studente.");
        }
        return response.body();
    };
    public static Route changeStudente=(Request request, Response response)-> {
        int matricola = Integer.parseInt(request.params(":matricola"));
        Path path = Paths.get("src/main/java/org/example/studente/" +  matricola + ".json");
        if (Files.exists(path)) {
            try {
                Files.write(path, request.body().getBytes());
                response.status(200);
                response.body("Studente aggiornato con successo.");
            } catch (IOException e) {
                e.printStackTrace();
                response.status(500);
                response.body("Errore durante l'aggiornamento dello studente.");
            }
        } else {
            response.status(404);
            response.body("Studente non trovato.");
        }

        return response.body();
    };
    public static Route addStudente=(Request request, Response response)->{

        JsonObject req = new Gson().fromJson(request.body(), JsonObject.class);

        if(req!=null && req.has("nome") && req.has("Cognome")) {
        //int matricola = req.get("matricola").getA;
           // Studente studente= new Studente(nome, Cognome, Corso);
            Path path = Paths.get("src/main/java/org/example/studente/" + req.get("matricola").getAsInt() + ".json");

            try {
                Files.write(path, request.body().getBytes());

                response.status(201);
                response.body("studente aggiunto con successo. ");


            } catch (IOException e) {
                e.printStackTrace();
                response.status(500);
                response.body("Errore durante l'aggiunto dello studente. ");

            }
        }else {
            halt(403);

        }
        return response.body();

    };
}
