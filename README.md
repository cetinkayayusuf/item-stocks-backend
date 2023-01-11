# Read Me First
This project uses docker postgres image to run database (PostgreSql)

To run this project, there is two options:
- Use provided docker compose file to run database
  - Go to project directory, run 'docker compose up' command at terminal
- Use your own postgres database
  - Edit application.properties file to connect your database

After running database, 
- you can either press start button at your ide,
- or run './mvnw spring-boot:run' command at project directory

About Project:
- Authentication => Spring Security and Json Web Token
- ORM => JPA Repository
- Database => Postgres

There are two type of users at the api:
- Admin => Can list, add, update, delete items
- User => Can list items and Can list, add, update, delete stocks

When Registering a user, 
- To Register an Admin => you could add 'admin' as role
- To Register as User => you dont have to add any roles

