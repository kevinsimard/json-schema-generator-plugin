package com.kevinsimard.schema.validator.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.stringtemplate.v4.ST;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @goal generate
 * @phase generate-sources
 * @requiresDependencyResolution compile
 */
public class GenerateMojo extends AbstractMojo {

    private static final Charset CHARSET = Charset.defaultCharset();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @parameter property="sourceDirectory"
     */
    private String sourceDirectory;

    /**
     * @parameter property="targetDirectory"
     */
    private String targetDirectory;

    /**
     * @parameter property="targetPackage"
     */
    private String targetPackage;

    public void execute() throws MojoExecutionException, MojoFailureException {
        String[] extensions = new String[]{"json"};

        generateAbstractValidator();

        Iterator<File> files = FileUtils.iterateFiles(
            new File(sourceDirectory), extensions, true);

        while (files.hasNext()) {
            File file = files.next();
            JsonNode schema = getJsonSchema(file);

            if (schema.has("type") &&
                    schema.get("type").asText().equals("object")) {
                generateValidator(file, schema);
            }
        }
    }

    private JsonNode getJsonSchema(File file) throws MojoExecutionException {
        try {
            return MAPPER.readTree(file);
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to parse JSON schema file", e);
        }
    }

    private void generateAbstractValidator() throws MojoExecutionException {
        ST template = getStringTemplate("templates/abstract.st");
        template.add("package", targetPackage);

        String path = targetDirectory + "/" + "AbstractValidator.java";

        try {
            FileUtils.writeStringToFile(new File(path), template.render(), CHARSET);
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to create abstract validator class file", e);
        }
    }

    private void generateValidator(File file, JsonNode schema) throws MojoExecutionException {
        ST template = getStringTemplate("templates/validator.st");
        template.add("package", targetPackage);
        template.add("directory", schema.get("directory").asText());
        template.add("className", schema.get("name").asText() + "Validator");
        template.add("schemaPath", file.getAbsolutePath().replace(sourceDirectory, ""));

        String path = targetDirectory + "/" +
            schema.get("directory").asText() + "/" +
            schema.get("name").asText() + "Validator.java";

        try {
            FileUtils.writeStringToFile(new File(path), template.render(), CHARSET);
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to create validator class file", e);
        }
    }

    private ST getStringTemplate(String path) throws MojoExecutionException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);

        try {
            return new ST(IOUtils.toString(stream, CHARSET), '$', '$');
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to locate string template file", e);
        }
    }
}
