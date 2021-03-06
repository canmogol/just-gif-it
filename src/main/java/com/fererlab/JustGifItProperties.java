package com.fererlab;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.io.File;

@ConfigurationProperties(prefix = "com.fererlab")
@PropertySource("classpath:application.yml")
public class JustGifItProperties {

    /**
     * The location of the animated gifs
     */
    private File gifLocation;

    /**
     * Whether or not to optimize web filters
     */
    private Boolean optimize;

    public File getGifLocation() {
        return gifLocation;
    }

    public void setGifLocation(File gifLocation) {
        this.gifLocation = gifLocation;
    }

    public Boolean getOptimize() {
        return optimize;
    }

    public void setOptimize(Boolean optimize) {
        this.optimize = optimize;
    }
}
