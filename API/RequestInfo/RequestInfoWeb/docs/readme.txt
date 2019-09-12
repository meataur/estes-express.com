Introduction
============

This is the web application to support document retrieval for Estes customers.

Dependencies
============

This project is built based upon the following components:
J2SDK 1.6+
Maven-3.2.1:
Refer to the setup.txt document for information regarding Maven web project

Special Instructions
====================

See following link if Maven -> Update fails with "Unsupported IClasspathEntry kind=4" error
http://stackoverflow.com/questions/10564684/how-to-fix-error-updating-maven-project-unsupported-iclasspathentry-kind-4

Building instructions
=====================

Right-click the pom.xml Maven script and select "Run As -> Maven build"
The WAR file is built in the "target" folder.

Testing
=======

...

Deployment instructions
=======================

1) Install the web application into the Websphere profile. 

Version
=======

See POM <version> tag in parent project
