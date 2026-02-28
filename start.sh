#!/bin/bash
# Quick start script for the Cortex backend
mvn clean install -DskipTests

echo "🧠 Starting Cortex Backend (Java Spring Boot)..."
echo "📡 API will be available at http://localhost:8000"
echo ""

mvn spring-boot:run
