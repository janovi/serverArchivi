package org.example;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//L'ApiTecnico usa la classe java tecnico per rappresentare l'oggeto tecnico
public class ApiTecnico {

    public static Route getAllTecnici=(Request request , Response response)->{
        Path path = Paths.get("src/main/java/org/example/tecnico/" );

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
                            response.body("Errore durante l'estrazione degli tecnici.");
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
                response.body("Nessun tecnico trovato nella cartella.");
            }

        } else {
            response.status(404);
            response.body("La cartela tecnico non esiste.");
        }
            return "";
    };
    public static Route addTecnico=(Request request, Response response)->{
        int id = Integer.parseInt(request.queryParams("id"));
        String nome = request.queryParams("nome");
        String cognome= request.queryParams("cognome");
        String email= request.queryParams("email");
        Tecnico tecnico = new Tecnico(id, nome, cognome, email);

        Path path = Paths.get("src/main/java/org/example/tecnico/" + id +".json");
       // if (Files.exists(path)) {
        Gson gson = new Gson();
        String jsonTecnico = gson.toJson(tecnico);
            try {
                Files.write(path, jsonTecnico.getBytes());
                response.status(201);
                response.body("Tecnico aggiunto con successo. ");
            } catch (IOException e) {
                e.printStackTrace();
                response.status(500);
                response.body("Errore durante l'aggiunto delle tecnico. ");

            }
       // }else {response.body("path non esiste");}

        return request.body();

    };
    public static Route getSingoloTecnico= (Request request, Response response)->{

        int id = Integer.parseInt(request.params(":id"));
        Path path = Paths.get("src/main/java/org/example/tecnico/" + id + ".json");
        if (Files.exists(path)) {
            try {
                String json = new String(Files.readAllBytes(path));
                response.status(200);
                response.type("application/json");
                return json;

            } catch (IOException e) {
                e.printStackTrace();
                response.status(500);
                response.body("Errore durante l'estrazione delle tecnico.");
            }
        } else {
            response.status(404);
            response.body("Cartella non trovata.");
            return "Tecnico non trovato.";

        }
        return "";
    };
    public static Route deleteTecnico=(Request request, Response response)->{
        int id = Integer.parseInt(request.params(":id"));
        Path path = Paths.get("src/main/java/org/example/tecnico/" + id + ".json");
        try {
            Files.delete(path);
            response.status(200);
            response.body("tecnico eliminato con successo.");
        } catch (IOException e) {
            e.printStackTrace();
            response.status(500);
            response.body("Errore durante l'eliminazione delle tecnico.");
        }
        return response.body();
    };
}
