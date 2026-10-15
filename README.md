# rental-api

REST-API för Fulköpings Uthyrnings AB: utrustning, kunder och uthyrningar med historik.

`Java 25 · Spring Boot 4.1.1 · Spring Data JPA · PostgreSQL 17 (Docker) · Spring Security · Swagger`

---

## Starta

Kräver Java 25 och Docker Desktop.

```bash
git clone https://github.com/AlexGritA/rental-api.git
cd rental-api

docker compose up -d        # startar PostgreSQL
./mvnw spring-boot:run      # startar API:et på port 8080
```

- **Swagger:** `http://localhost:8080/swagger-ui.html` (klicka **Authorize**)
- **Postman:** importera `rental-api.postman_collection.json` och kör *Run collection*

**Inloggning (HTTP Basic)**

| Användare | Lösenord   | Roll  | Får        |
|-----------|------------|-------|------------|
| `user`    | `user123`  | USER  | läsa (GET) |
| `admin`   | `admin123` | ADMIN | allt       |

---

## Raderingsstrategi

En person eller sak som har uthyrningshistorik kan inte raderas. Försöket ger **409 Conflict**.

Det gäller även när alla uthyrningar är återlämnade, så att historiken alltid finns kvar. Personer och saker utan historik raderas som vanligt (204). Uthyrningar kan aldrig raderas.

---

## VG-kraven

| Krav                                | Så här                                                                                                                                                     |
|-------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Ingen dubbelhyrning**             | Innan en uthyrning skapas kollar `RentalService` om saken har en uthyrning där `returnedDate` är `null`. Finns en sådan blir svaret 409.                   |
| **Historik**                        | `Rental` är en egen entitet (ersätter `@ManyToMany`) med `startDate`, `endDate` och `returnedDate`. Återlämning sätter bara `returnedDate`, inget raderas. |
| **Historiken skyddas vid radering** | Se raderingsstrategin.                                                                                                                                     |
| **Inloggning och roller**           | Spring Security med HTTP Basic. Alla `/api`-endpoints kräver inloggning. USER får GET, ADMIN får allt. 401 utan inloggning, 403 vid fel roll.              |
| **Hashade lösenord**                | BCrypt via en `PasswordEncoder`-bean.                                                                                                                      |
| **Inloggare separata från Person**  | Inloggningsanvändarna ligger i `SecurityConfig` och har ingen koppling till `Person`.                                                                      |
| **Persistent databas**              | PostgreSQL via `docker-compose.yml`. Datan ligger kvar i en Docker-volym.                                                                                  |
| **Postman-samling**                 | `rental-api.postman_collection.json` med tester för både lyckade anrop och felfall (400, 401, 403, 404, 409).                                              |
