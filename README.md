# How to run
Download the [User API](https://github.com/Noobos100/user-api) and the [Book API](https://github.com/Noobos100/book-api)

Then add the .war of both apis to the deployment tab in IntelliJ IDEA
Then to add a reservation  
```bash
curl -X POST -d "id=idBob&reference=ABC" http://localhost:8080/reservation-1.0-SNAPSHOT/api/reservations/
```
