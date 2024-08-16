# Docker Compose Setup

This project uses Docker Compose to define and run a multi-container Docker application. The setup includes a frontend, a backend, and a PostgreSQL database. Below are details on how to build and run the services defined in the `docker-compose.yml` file.

Swagger of application: http://localhost:8083/swagger-ui/index.html

## Services

### Frontend

- **Image**: `frontend`
- **Build Context**: `./frontend`
- **Ports**: `4200:4200`
- **Depends On**: `backend`

The frontend service is responsible for serving the client-side application. It listens on port 4200 inside the container and is exposed on port 4200 on the host.

### Backend

- **Image**: `backend`
- **Container Name**: `backend`
- **Build Context**: `./backend`
- **Environment Variables**:
  - `DB_HOST`: `database`
- **Ports**: `8083:8080`
- **Depends On**: `database`

The backend service handles API requests and connects to the PostgreSQL database. It listens on port 8080 inside the container and is exposed on port 8083 on the host.

### Database

- **Image**: `postgres:15.3-alpine`
- **Container Name**: `database`
- **Restart Policy**: `always`
- **Environment Variables**:
  - `POSTGRES_PASSWORD`: `password`
  - `POSTGRES_USER`: `postgres`
- **Ports**: `5432:5432`
- **Volumes**:
  - `./data/postgres:/var/lib/postgresql/data`

The database service runs PostgreSQL and stores its data in a volume mapped to `./data/postgres` on the host. It is accessible on port 5432.

## Getting Started

To build and start the services, run:

```bash
docker-compose up --build
