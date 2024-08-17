<!-- ABOUT THE PROJECT -->
## About The Project

project for creating tasks, the ability to set their importance level, execution status, assign an executor, and also leave comments that all users can see.


### Built With


* [![Spring][Spring]][Spring-url]
* [![Hibernate][Hibernate]][Hibernate-url]
* [![Liquibase][Liquibase]][Liquibase-url]
* [![Postgresql][Postgresql]][Postgresql-url]
* [![Swagger][Swagger]][Swagger-url]




<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Installation

1. Install Docker
2. Clone the repo
   ```sh
   git clone https://github.com/R0n316/task-management-system.git
   ```
3. execute command
   ```sh
   docker-compose up
   ```


<!-- USAGE EXAMPLES -->
## Usage
### _All the data that needs to be sent can be viewed in swagger-ui_

To access the API, you need to register to receive a JWT token
```
/api/auth/register
```
You can log into an existing account
```
/api/auth/login
```
To create a new task, you need to send a POST request to the following url:
```
/api/tasks
```
the same url should be used to get all tasks (you can also pass the page number and page size to get the required data)

to update or change a task you must contact the url
```
/api/tasks/{id}
```

where id is the task id. Only the task author can modify or delete a task.

There is also a separate url for changing the status.
```
/api/tasks/{id}/edit-status
```

Its difference is that the status can be changed by both the author of the task and its executor.

__Any user__ can leave a comment on any task. But only the author of the comment can delete it.

To leave a comment on a task, you need to send a request to the following url:
```
/api/comments
```

to change/delete on url:
```
/api/comments/{id}
```

the only difference is in the request type


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[Spring]: https://camo.githubusercontent.com/621ad45e5af2fa8ce30932b8e9a5c6561ec0b3180845ec409a932da8bb5e09f6/68747470733a2f2f696d672e736869656c64732e696f2f7374617469632f76313f7374796c653d666f722d7468652d6261646765266d6573736167653d537072696e6726636f6c6f723d364442333346266c6f676f3d537072696e67266c6f676f436f6c6f723d464646464646266c6162656c3d
[Spring-url]: https://spring.io/projects/spring-framework
[Hibernate]: https://camo.githubusercontent.com/06b0c4daa865c184fbaccc029d1cd443dac10e534d6715dd1b51c2463a30210b/68747470733a2f2f696d672e736869656c64732e696f2f7374617469632f76313f7374796c653d666f722d7468652d6261646765266d6573736167653d48696265726e61746526636f6c6f723d353936363643266c6f676f3d48696265726e617465266c6f676f436f6c6f723d464646464646266c6162656c3d
[Hibernate-url]: https://hibernate.org/
[Liquibase]: https://camo.githubusercontent.com/69f28a75c3e7afd63269bd7e775cfea378001cd4aaa612844ad2234d6c17a5a6/68747470733a2f2f696d672e736869656c64732e696f2f7374617469632f76313f7374796c653d666f722d7468652d6261646765266d6573736167653d4c697175696261736526636f6c6f723d323936324646266c6f676f3d4c6971756962617365266c6f676f436f6c6f723d464646464646266c6162656c3d
[Liquibase-url]: https://www.liquibase.com/
[Postgresql]: https://img.shields.io/static/v1?style=for-the-badge&message=PostgreSQL&color=4169E1&logo=PostgreSQL&logoColor=FFFFFF&label=
[Postgresql-url]: https://www.postgresql.org/
[Swagger]: https://img.shields.io/static/v1?style=for-the-badge&message=Swagger&color=222222&logo=Swagger&logoColor=85EA2D&label=
[Swagger-url]: https://swagger.io/

---
### 16.08.2024
