# sb-api-test
Trying the latest and greatest features in Spring Boot and Java. Currently v3.3 and v21 LTS, respectively.

## Critterz API
To show something useful being accomplished, will create simple application allowing random fetch of animals by 3rd party API and persist to local store.

### Api doc
Fetch remote images and save to database:
```
curl --location --request POST 'http://localhost:8080/api/critterz?critterType=BEAR'
```

Get last critter:
```
curl --location --request POST 'http://localhost:8080/api/critterz/last'
```

Get last critter image:
```
curl --location --request POST 'http://localhost:8080/api/critterz/last/image'
```