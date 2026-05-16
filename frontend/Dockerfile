# --- STAGE 1: Build ---
FROM node:20-alpine AS builder
WORKDIR /app

# Abhängigkeiten kopieren und installieren
COPY package.json package-lock.json* ./
RUN npm ci

# Quellcode kopieren und die App bauen
COPY . .
RUN npm run build

# --- STAGE 2: Run ---
FROM node:20-alpine AS runner
WORKDIR /app

ENV NODE_ENV=production
ENV PORT=3000
ENV HOSTNAME="0.0.0.0"

# Nur die für den Betrieb notwendigen Standalone-Dateien kopieren
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static
COPY --from=builder /app/public ./public

EXPOSE 3000

# Next.js startet im Standalone-Modus über den kopierten Server
CMD ["node", "server.js"]