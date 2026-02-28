#!/bin/bash
# Quick start script for the Digital Brain backend
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "🧠 Starting Digital Brain Backend (Java Spring Boot)..."
echo "📡 API will be available at http://localhost:8000"
echo "📖 Swagger docs at http://localhost:8000/swagger-ui.html (if enabled)"
echo ""

mvn spring-boot:run
