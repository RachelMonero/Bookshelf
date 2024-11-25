Bookshelf Project

Overview

The Bookshelf project is a Java-based web application for managing libraries and books. It provides functionalities like user registration, login, book search and reservation, and data management for libraries and books. The application follows a modular structure, ensuring scalability and ease of maintenance.

Code Structure

Controllers: Handle HTTP requests and responses.

AuthenticationController

DashboardController

SearchController

Models: Represent application data and business logic.

Book

Library

LibraryBook

Address

Repositories: Manage database interactions.

BookRepository

LibraryRepository

Services: Contain the core business logic.

AuthenticationService

SearchService

Web Resources: Includes JSP files for front-end views and CSS for styling.

index.jsp (Home Page)

dashboard.jsp

login.jsp

Features

User Authentication: Register and log in securely.

Book Search: Search for books across libraries with an intuitive interface.

Book Reservation: Reserve books across various libraries.

Library Management: Manage library data, including book inventories.

Instructions to Run

Prerequisites

Java 17 or above

Apache Tomcat (v9.0)

MySQL Server

MySQL Workbench for database management

Eclipse IDE

Steps to Run

Import the project into Eclipse:

Open Eclipse.

Go to File -> Import -> Existing Projects into Workspace.

Select the  ZIP file.

Ensure your MySQL Workbench localhost server credentials match the dbutils configuration in the project:

Update the dbutils file with your MySQL credentials (username, password, and port).

Start the application:

The database will be automatically created on startup.

Run the project on Tomcat:

Right-click the project in Eclipse and select Run As -> Run on Server.

Choose your configured Apache Tomcat server.

Access the application:

Open your browser and go to http://localhost:8080/Bookshelf.

Troubleshooting

If you encounter any issues, ensure that:

The database credentials in dbutils are correct.

Apache Tomcat is running on the configured port.
