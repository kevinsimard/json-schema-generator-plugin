package com.kevinsimard.schema.validator.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.IOException;

/**
 * @goal clean
 * @phase generate-sources
 * @requiresDependencyResolution compile
 */
public class CleanMojo extends AbstractMojo {

    /**
     * @parameter property="targetDirectory"
     */
    private String targetDirectory;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            FileUtils.cleanDirectory(new File(targetDirectory));
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to clean target directory", e);
        }
    }
}
