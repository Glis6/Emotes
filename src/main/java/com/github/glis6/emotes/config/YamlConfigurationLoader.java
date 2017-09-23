package com.github.glis6.emotes.config;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * @author Glis
 */
public abstract class YamlConfigurationLoader implements Configuration<YamlConfiguration> {
    /**
     * The name of the file we are loading.
     */
    private final String fileName;

    /**
     * The {@link JavaPlugin} the configuration is for.
     */
    private final JavaPlugin javaPlugin;

    /**
     * The {@link YamlConfiguration} to use.
     */
    @Getter(AccessLevel.PROTECTED)
    private final YamlConfiguration yamlConfiguration = new YamlConfiguration();

    /**
     * @param javaPlugin The {@link JavaPlugin} the configuration is for.
     */
    public YamlConfigurationLoader(String fileName, JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.fileName = fileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("all")
    public YamlConfiguration loadConfiguration() throws IOException, InvalidConfigurationException {
        File file = new File(javaPlugin.getDataFolder(), fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            createDefaults();
            yamlConfiguration.options().copyDefaults(true);
            yamlConfiguration.save(file);
        } else {
            yamlConfiguration.load(file);
        }
        return yamlConfiguration;
    }

    /**
     * Creates the default configuration.
     */
    protected abstract void createDefaults();
}
