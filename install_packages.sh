#!/bin/bash

# Script to install required packages for the BookBattery Java web application on Ubuntu server
# This script installs Java 17, Maven, Tomcat 9, MySQL, Nginx, and Docker

# Update package list
echo "Updating package list..."
sudo apt update

# Install OpenJDK 17
echo "Installing OpenJDK 17..."
sudo apt install -y openjdk-17-jdk

# Set JAVA_HOME (optional, but good practice)
echo "Setting JAVA_HOME..."
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
echo "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64" >> ~/.bashrc
source ~/.bashrc

# Install Maven
echo "Installing Maven..."
sudo apt install -y maven

# Install Tomcat 9
echo "Installing Tomcat 9..."
sudo apt install -y tomcat9

# Install MySQL Server
echo "Installing MySQL Server..."
sudo apt install -y mysql-server

# Secure MySQL installation (run mysql_secure_installation manually if needed)
# sudo mysql_secure_installation

# Install Nginx
echo "Installing Nginx..."
sudo apt install -y nginx

# Install Docker
echo "Installing Docker..."
sudo apt install -y docker.io

# Enable and start services
echo "Enabling and starting services..."
sudo systemctl enable tomcat9
sudo systemctl start tomcat9

sudo systemctl enable mysql
sudo systemctl start mysql

sudo systemctl enable nginx
sudo systemctl start nginx

sudo systemctl enable docker
sudo systemctl start docker

# Add user to docker group (optional, for non-root Docker usage)
# sudo usermod -aG docker $USER

echo "Installation complete. You may need to configure MySQL database and deploy the application."</content>
<parameter name="filePath">c:\Users\USER\Desktop\Mar2026\bookbattery\install_packages.sh