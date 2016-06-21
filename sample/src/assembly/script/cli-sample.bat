@echo off

SET ${project.artifactId}_HOME=%~dp0

SET ${project.artifactId}_OPTS="-Djava.util.logging.config.file=%${project.artifactId}_HOME%conf/logging.properties"

java -jar %${project.artifactId}_OPTS% %${project.artifactId}_HOME%lib/${project.artifactId}-${project.version}.jar
