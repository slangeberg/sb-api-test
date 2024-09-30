# sb-api-test
Trying the latest and greatest features in Spring Boot and Java. Currently v3.3 and v21 LTS, respectively.

## Critterz API
To show something useful being accomplished, will create simple application allowing random fetch of animals by 3rd party API and persist to local store.

### Install Java JDK
On mac, is as simple as:   
`brew install openjdk@21`

### Build and run app
With JDK 21 present in local console: 
```
./gradlew build -is; 
docker build -t geekadonis/sb-api-test:latest .; 
docker run -p 8080:8080 geekadonis/sb-api-test:latest;
```
This should result in successful build of artifact and running in console.

### Api doc
**Fetch remote images and save to database:**  
```
# critterType=BEAR|DOG|KITTEN 
# optional: &count=n&width=n&height=n
curl --location --request POST 'http://localhost:8080/api/critterz?critterType=BEAR'
```

**Get all critterz as JSON list**:   
`curl --location 'http://localhost:8080/api/critterz'`

**Get last critter as JSON:**   
`curl -location 'http://localhost:8080/api/critterz/last'`

**Get last critter image:**   
`curl --location 'http://localhost:8080/api/critterz/last/image' -o ./last-critter.jpg`

### Future improvements
- **Externalize the remote api configurations**. 
  - Currently, we have a few hard-coded endpoints to match 'critterType'. Those could easily be configured by external file or db with type -> uri mappings.
- **Standard CRUD api.** 
  - Would be simple to add `/api/critterz/{ID}` and similar endpoints. 
  - Also seems like if we really want 'last' entry, we'd want to query by type, like `/api/critterz/last?findByCritterType=BEAR` 
- **Security.** 
  - Right now these apis are wide open. Would be interesting to see if there is any evolution in Spring Security in Boot 3 that I'm missing out on.
- **OpenAPI.** 
  - No reason not to publish swagger api docs for convenience.
- **Modular code structure.** 
  - So far, the 'critterz' logic is dumped into simple modular structure. As things expand, would be wise to add more source control org.
- **Current testing is minimal** 
  - Could easilty use MockMvc for controller and DataJpaTest for speedy query validation
- **Simple UI**
  - I'm interested to see how libraries like Vue.js have come along in recent years. Could be fun to line them up for side-by-side comparison examples.