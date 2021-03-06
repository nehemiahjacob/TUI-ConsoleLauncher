package ohi.andre.consolelauncher.managers;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import ohi.andre.consolelauncher.tuils.Tuils;
import ohi.andre.consolelauncher.tuils.interfaces.Reloadable;

import static android.R.id.list;
import static ohi.andre.consolelauncher.managers.XMLPrefsManager.resetFile;
import static ohi.andre.consolelauncher.managers.XMLPrefsManager.set;

public class AliasManager implements Reloadable {

    public static final String PATH = "alias.txt";

    private Map<String, String> aliases;

    public AliasManager() {
        reload();
    }

    public String printAliases() {
        String output = Tuils.EMPTYSTRING;
        for (Map.Entry<String, String> entry : aliases.entrySet()) {
            output = output.concat(entry.getKey() + " --> " + entry.getValue() + Tuils.NEWLINE);
        }

        return output.trim();
    }

    public String getAlias(String s) {
        return aliases.get(s);
    }

    @Override
    public void reload() {
        if(aliases != null) aliases.clear();
        else aliases = new HashMap();

        File file = new File(Tuils.getFolder(), PATH);

        try {
            if(!file.exists()) file.createNewFile();

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String line;
            while((line = reader.readLine()) != null) {
                String[] splatted = line.split("=");
                if(splatted.length < 2) continue;
                aliases.put(splatted[0], splatted[1]);
            }
        } catch (Exception e) {}
    }

    public boolean add(String name, String value) {
        reload();

        if(aliases.containsKey(name)) return false;

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(Tuils.getFolder(), PATH));
            fos.write((name + "=" + value + Tuils.NEWLINE).getBytes());
            fos.close();

            aliases.put(name, value);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean remove(String name) {
        reload();

        try {
            File inputFile = new File(Tuils.getFolder(), PATH);
            File tempFile = new File(Tuils.getFolder(), PATH + "2");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String prefix = name + "=";
            String line;
            while((line = reader.readLine()) != null) {
                if(line.startsWith(prefix)) continue;
                writer.write(line + Tuils.NEWLINE);
            }
            writer.close();
            reader.close();


            aliases.remove(name);

            return tempFile.renameTo(inputFile);
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getAliases() {
        if(aliases == null) return new ArrayList<>(0);
        return new ArrayList<>(aliases.keySet());
    }
}
