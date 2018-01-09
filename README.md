# JSON Schema Class Generator Maven Plugin

## Code Structure

    ├── src
    │   └── main
    │       ├── java
    │       │   └── com
    │       │       └── kevinsimard
    │       │           └── schema
    │       │               └── validator
    │       │                   └── plugin
    │       │                       ├── CleanMojo.java
    │       │                       └── GenerateMojo.java
    │       └── resources
    │           └── templates
    │               ├── abstract.st
    │               └── validator.st
    ├── .editorconfig
    ├── .gitattributes
    ├── .gitignore
    ├── LICENSE.md
    ├── README.md
    └── pom.xml

## Usage

Use `$ mvn install` to install the JAR locally.

See [schema-validator-example](https://github.com/kevinsimard/schema-validator-example) project for a real implementation.
