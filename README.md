# Mountain Cottage Rental

Short description
---
A compact Spring Boot + Angular app for listing and renting mountain cottages in Serbia. Roles: Tourist, Host, Administrator. Registration requires admin approval. This repo is a portfolio project (minimal, no Docker, no CI, no automated tests).

Tech stack
---
- Backend: Spring Boot 3.5.5  
- Frontend: Angular 18
- Database: MySQL 
- Map: Leaflet  
- Calendar: FullCalendar

Core features
---
- Roles: Tourist, Host, Administrator (admin login on separate route)  
- Registration: Tourist/Host → pending request → admin approval  
- Authentication: session/form-based login; password hashed (bcrypt)  
- Password policy: 6–10 chars; starts with a letter; ≥1 uppercase; ≥3 lowercase; ≥1 digit; ≥1 special char  
- Profile image upload: JPEG/PNG; 100×100–300×300 px; default image if omitted  
- Credit-card detection (client): Diners / MasterCard / Visa by prefix/length; show card icon; store only masked value (last 4 digits)  
- Public home: site stats, searchable & sortable cottage list (name, place)  
- Cottage page: gallery, services, seasonal prices, phone, comments/ratings, interactive map  
- Multi-step booking: date pickers, guest counts, review + payment (prefilled masked card), optional 500-char note, server-side availability checks  
- Host dashboard: profile, manage cottages (CRUD), pending bookings (approve/reject with comment), calendar view  
- User reservations: current + history; leave rating/comment after completed stay  
- Admin: approve registrations, manage users, view cottages, temporarily block problematic listings

Quick local setup (minimal)
---
1. Clone repo.  
2. Create DB using `init.sql`. Configure DB in `application.properties`.  
3. Start backend: `mvn spring-boot:run` (or `./mvnw spring-boot:run`).  
4. Frontend: `cd frontend && npm install && npm start`.  
5. Ensure `uploads/` is writable and in `.gitignore`. Use seeded admins from `admin_info.txt` to approve pending users.

Security notes
---
- Hash passwords (bcrypt). Never store plaintext.  
- Validate uploads server-side (type, size, dimensions).  
- Validate all client-side checks again on server.