package fi.tuni.prog3.sisu.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Scanner;

/**
 * Utility class for saving app state to file
 */
public class Persistent<T> {

    private final Gson gson = new GsonBuilder().create();
    private final String fileName;
    private final Class<? extends T> clazz;

    /**
     * Default constructor.
     * Take class as param, because GSON doesn't like generics and will fail with type parameter.
     * @param fileName File to use for saving and loading
     * @param clazz Class to cast to
     */
    public Persistent(String fileName, Class<? extends T> clazz) {
        this.fileName = fileName;
        this.clazz = clazz;
    }


    /**
     * Save object of type T to disk.
     * @param object Object to save
     * @return True if success, otherwise false
     */
    public boolean save(T object) {
        String contents = gson.toJson(object);

        try {
            File file = new File(this.fileName);

            if(file.createNewFile()) {
                System.out.println("Created file " + fileName);
            } else {
                System.out.println("Using existing file " + fileName);
            }

            FileWriter writer = new FileWriter(file);
            writer.write(contents);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Load object of type T from disk.
     * @return object if found, else null
     */
    public T load() {
        try {
            File file = new File(this.fileName);

            if(file.exists() && file.canRead()) {
                Scanner reader = new Scanner(file);
                StringBuilder content = new StringBuilder();
                while(reader.hasNext()) {
                    content.append(reader.nextLine());
                }
                reader.close();
                return gson.fromJson(content.toString(), this.clazz);

            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
