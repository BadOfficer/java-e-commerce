# JavaWeb-E-Commerce

## Environment variables

The application reads database configuration from a local `.env` file or from process environment variables.

Required variables:

- `DB_HOST`
- `DB_PORT`
- `DB_NAME`
- `DB_USER`
- `DB_PASSWORD`
- `HEALTH_DB_TIMEOUT_SECONDS` (optional, default: `2`)

1. Create a local `.env` file from `.env.example`.
2. Fill in the database settings.
3. Start the application.
