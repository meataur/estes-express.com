Introduction
============

This is the web application for all Estes common services.

Dependencies
============

This project is built based upon the following components:
J2SDK 1.8+
Maven-3.5.0:
Refer to the setup.txt document for information regarding Maven web project

Special Instructions
====================

See following link if Maven -> Update fails with "Unsupported IClasspathEntry kind=4" error
http://stackoverflow.com/questions/10564684/how-to-fix-error-updating-maven-project-unsupported-iclasspathentry-kind-4

Building instructions
=====================

Right-click the pom.xml Maven script and select "Run As -> Maven build"
The WAR file is built in the "target" folder.

Deployment instructions
=======================

1) Run the Jenkins job for the appropriate environment. 

Version
=======

See POM <version> tag in parent project
