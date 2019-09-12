Introduction
============

This project holds common data transfer objects (DTO) used in Estes web applications.

Dependencies
============

This project is built upon the following components:
J2SDK 1.8+
Maven 3.2.5

Subversion Instructions
=======================

Do not check-in any content in the ".settings" folder.  These are local Eclipse settings files that can cause conflicts.
Do not check-in any content in the "target" folder.  These are temporary files generated for the build.

Special Instructions
====================

Ensure the version in the POM (pom.xml) file is correct before running a Jenkins build.
This will ensure the version numbers are correct in Artifactory.

The build uses JAXB to automatically regenerate certain DTOs based on the XSD files in src/main/java/xsd.
Generated source files should be identical for each build except for ObjectFactory files and any schema changes.

Build Instructions
==================

via Command Line (recommended):
Open command line and navigate to /ESTESCommonDTO/pom.xml where the is pom.xml located.
Enter the maven command: mvn clean install
The JAR file is built in the "target" folder.

via Eclipse:
Right-click the project and click Run As > Maven install.
NOTE: You may have to delete the files in the "target" folder to force re-generation of source code.
The JAR file is built in the "target" folder.
