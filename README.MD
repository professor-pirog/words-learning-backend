#Words learning backend

This application provides API for the Words learning application.

Before starting this application local, you may run a database in docker:

```bash
docker run --name words-learning-backend -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=words_learning_backend -d -p 5432:5432 postgres
```

Then you have to create liquibase schema:

```postgresql
CREATE SCHEMA liquibase
```

For building docker image run:
```bash
docker build -t pirogsoft/words-learning-backend:version .
docker push pirogsoft/words-learning-backend:version
```