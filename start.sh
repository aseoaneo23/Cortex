#!/bin/bash
# Quick start script for the Digital Brain backend
mvn clean install -DskipTests

echo "🧠 Starting Digital Brain Backend (Java Spring Boot)..."
echo "📡 API will be available at http://localhost:8000"
echo "📖 Swagger docs at http://localhost:8000/swagger-ui.html (if enabled)"
echo ""

mvn spring-boot:run
