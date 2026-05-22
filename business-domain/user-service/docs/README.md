Si hacemos `mvn clean install` tal cual nos va a dar un error debido al fichero `logback-spring.xml`.

(Solucionar más adelante)

Para compilar con `mvn clean install` se necesita borrar
el fichero `logback-spring.xml` y seguidamente se podrá compilar para generar el JAR y ya poder generar la imagen Docker.