package seonjae.plugin.util.object;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.logging.Level;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.Files;

import lombok.Cleanup;

public class UTF8YamlConfiguration extends YamlConfiguration {
	
	public String saveToString() {
		
		String data = new String();
		
		boolean first = true;
		
		for(String s : super.saveToString().split("\\\\u")) {
			if(s.length() >= 4 && !first) {
				data += (char)Integer.parseInt(s.substring(0, 4), 16);
				if(s.length() >= 5) data += s.substring(4);
			} else {
				data += s;
				first = false;
			}
		}
		return data;
	}
	
	public void save(File file) throws IOException {
		Validate.notNull(file, "File cannot be null");
		Files.createParentDirs(file);
		String data = saveToString();
		@Cleanup FileOutputStream fos = new FileOutputStream(file);
		@Cleanup Writer writer = new OutputStreamWriter(fos, Charset.forName("MS949"));
		writer.write(data);
	}
	
	public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
		Validate.notNull(file, "File cannot be null");
		@Cleanup FileInputStream stream = new FileInputStream(file);
		load(new InputStreamReader(stream, Charset.forName("MS949")));
	}
	
	@Deprecated
	public void load(InputStream stream) throws IOException, InvalidConfigurationException {
		Validate.notNull(stream, "Stream cannot be null");
		@Cleanup InputStreamReader isr = new InputStreamReader(stream, Charset.forName("MS949"));
		load(isr);
	}
	public static UTF8YamlConfiguration loadConfiguration(File file) {
		Validate.notNull(file, "File cannot be null");
		UTF8YamlConfiguration config = new UTF8YamlConfiguration();
		try {
			config.load(file);
		} catch(FileNotFoundException e) {
		} catch(IOException | InvalidConfigurationException e){
			Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, e);
		}
		return config;
	}
	
	@Deprecated
	public static UTF8YamlConfiguration loadConfiguration(InputStream stream) {
		Validate.notNull(stream, "Stream cannot be null");
		UTF8YamlConfiguration config = new UTF8YamlConfiguration();
		try {
			config.load(stream);
		} catch(IOException | InvalidConfigurationException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Cannot load configuration from stream", e);
		}
		return config;
	}
	
	public static UTF8YamlConfiguration loadConfiguration(Reader reader) {
		Validate.notNull(reader, "Stream cannot be null");
		UTF8YamlConfiguration config = new UTF8YamlConfiguration();
		try {
			config.load(reader);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Cannot load configuration from stream", e);
		}
		return config;
	}
}
