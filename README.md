This is a complete Devops Enterprise style Production ready project.
===============================================================================================================================
Give me the prompt for below requirement.

I have a java application in github https://github.com/kiranlintech/bookbattery.git under the branch trydevops. 
I have ubuntu server hosted in hostinger.
use devops tools setup a production ready environment. using tomcat, nginx, mysql, github, docker, jenkins, docker, kubernetes. 
also use docker hub to store the docker image. 
give me step by step detailed steps with complete code snippet wherever required
Think as i am a beginner. 

Here is a well-structured prompt you can use with ChatGPT or any AI to get a complete DevOps production setup guide for your project.

I improved your prompt so the AI clearly understands your requirements, tools, and beginner level.

Writing

I have a Java application hosted in GitHub.

Repository: https://github.com/kiranlintech/bookbattery.git
Branch: trydevops

I also have an Ubuntu server hosted on Hostinger.

My goal is to create a production-ready DevOps deployment pipeline for this application.

Tools and technologies that must be used:

GitHub (Source Code Management)

Jenkins (CI/CD pipeline)

Docker (Containerization)

Docker Hub (Image repository)

Tomcat (Application server)

Nginx (Reverse proxy / frontend)

MySQL (Database)

Kubernetes (Container orchestration)

Deployment requirements:

Jenkins should pull the source code from the GitHub repository.

Jenkins should build the Java application and generate a WAR file.

Jenkins should build a Docker image for the application.

Jenkins should push the Docker image to Docker Hub.

The application should run inside a Docker container using Tomcat.

MySQL should run in a container and connect to the application.

Nginx should act as a reverse proxy to expose the application.

Kubernetes should be used to deploy and manage the containers.

The application should be accessible via Nginx.

Environment details:

Ubuntu server hosted on Hostinger

Docker and Kubernetes should run on the same server

Use best practices suitable for a production-ready DevOps architecture

Important requirements for the explanation:

Assume I am a beginner in DevOps

Provide step-by-step instructions

Include all commands

Include configuration files

Include Dockerfile

Include Jenkinsfile

Include Nginx configuration

Include Kubernetes YAML manifests

Explain why each step is required

Show the complete architecture flow

The final output should include:

Architecture diagram explanation

Server setup

Jenkins installation and configuration

Docker installation

Dockerfile creation

Jenkins pipeline creation

Docker Hub integration

MySQL container setup

Tomcat container setup

Nginx reverse proxy configuration

Kubernetes deployment

Testing and verification

Production best practices

Troubleshooting tips

✅ Why this prompt works well

It forces the AI to give:

Complete DevOps architecture

Production-level pipeline

Real configuration files

Beginner-friendly explanation

💡 Pro tip for you, Kiran

Since you are preparing for DevOps interviews and real projects, you can also extend the prompt by adding:

Also include:

- CI/CD best practices
- Secrets management
- Logging and monitoring
- Zero downtime deployment
- Security best practices

If you want, I can also give you a much better prompt that produces a REAL enterprise DevOps project architecture (used in interviews) using your repo. It will include:

Terraform

Helm

ArgoCD

GitOps

Monitoring (Prometheus + Grafana)

Just tell me and I’ll generate it. 🚀

yes

Great, Kiran 👍
Since you want to learn DevOps deeply and also prepare for interviews, here is a much stronger “Enterprise DevOps Project Prompt” that will generate a real industry-level architecture using your GitHub project.

This prompt will make AI generate a complete production-grade DevOps system including CI/CD, Kubernetes, GitOps, monitoring, security, and infrastructure automation.

Writing

I have a Java application hosted in GitHub.

Repository: https://github.com/kiranlintech/bookbattery.git
Branch: trydevops

I have an Ubuntu server hosted in Hostinger.

I want to build a complete Enterprise DevOps Architecture for this application using modern DevOps tools.

My goal is to simulate a real production environment used in companies.

Tools that must be included:

Source Control

GitHub

CI/CD

Jenkins Pipeline

Containerization

Docker

Image Registry

Docker Hub

Application Server

Tomcat

Database

MySQL

Reverse Proxy

Nginx

Container Orchestration

Kubernetes

Infrastructure as Code

Terraform

GitOps Deployment

ArgoCD

Monitoring

Prometheus

Grafana

Logging

ELK Stack (Elasticsearch, Logstash, Kibana)

Security

Trivy for container image scanning

Project Requirements

Jenkins should pull the source code from GitHub.

Jenkins should build the Java application using Maven.

Jenkins should generate the WAR file.

Jenkins should build a Docker image for the application.

Jenkins should scan the Docker image using Trivy.

Jenkins should push the Docker image to Docker Hub.

Terraform should provision the infrastructure.

Kubernetes should deploy the containers.

ArgoCD should manage GitOps-based deployment.

MySQL should run inside Kubernetes.

Nginx should expose the application.

Prometheus and Grafana should monitor the cluster.

ELK stack should collect logs.

Environment details

Ubuntu server hosted on Hostinger

Kubernetes cluster running on the server

All containers should run using Docker

The application should be accessible through Nginx

Explanation requirements

Assume I am a beginner in DevOps but want to learn production-level practices.

Provide:

Complete architecture explanation

Architecture diagram

Server setup steps

Docker installation

Kubernetes installation

Jenkins installation

Terraform setup

ArgoCD setup

Prometheus and Grafana setup

ELK stack setup

Dockerfile creation

Jenkinsfile creation

Kubernetes YAML manifests

Helm charts if required

Terraform configuration

Nginx configuration

CI/CD pipeline explanation

GitOps workflow explanation

Security best practices

Monitoring and logging setup

Provide step-by-step instructions with all commands and configuration files.

Explain every step clearly.

At the end also explain:

How to scale the application

How to perform zero-downtime deployment

How to debug failures

Common mistakes beginners make

DevOps interview questions related to this architecture

🔥 If you run this prompt you will get a project covering:

GitHub
   ↓
Jenkins CI/CD
   ↓
Build WAR
   ↓
Docker Image
   ↓
Trivy Security Scan
   ↓
Push to DockerHub
   ↓
Terraform Infra
   ↓
Kubernetes Deployment
   ↓
ArgoCD GitOps
   ↓
Nginx Ingress
   ↓
Users

With:

Prometheus + Grafana monitoring

ELK log management

Enterprise CI/CD pipeline

💡 Very important for you (Kiran)

If you build this project, it becomes a powerful DevOps portfolio project because it covers:

✔ Jenkins
✔ Docker
✔ Kubernetes
✔ GitOps
✔ Monitoring
✔ Security
✔ Infrastructure as Code

This is exactly what DevOps interviews expect in 2025–2026.

🚀 If you want, I can also show you the ULTIMATE DevOps project architecture (used by FAANG companies) built around your repo.

It will include:

Microservices architecture

Service Mesh (Istio)

Kubernetes autoscaling

Blue-Green deployment

Canary deployment

AWS / Cloud architecture

And it becomes a Top-tier DevOps portfolio project.

yes

Great, Kiran 🚀
Since you are learning DevOps seriously and already building a Java application, here is the ULTIMATE DevOps Project Prompt that can generate a FAANG-level architecture using your repo. This prompt forces the AI to design a real-world scalable production system.

Writing

I have a Java application hosted in GitHub.

Repository: https://github.com/kiranlintech/bookbattery.git
Branch: trydevops

I also have an Ubuntu server hosted in Hostinger.

I want to design and implement a complete FAANG-level DevOps architecture for this application that simulates a real production environment used by large tech companies.

Assume I am a beginner in DevOps, so explain everything clearly and step by step.

The system should include the following DevOps tools and practices.

Source Control

GitHub

CI/CD

Jenkins Pipeline

Build Tool

Maven

Containerization

Docker

Container Registry

Docker Hub

Application Server

Tomcat

Reverse Proxy

Nginx

Database

MySQL

Container Orchestration

Kubernetes

Infrastructure as Code

Terraform

GitOps

ArgoCD

Monitoring

Prometheus

Grafana

Logging

ELK Stack (Elasticsearch, Logstash, Kibana)

Security

Trivy container image scanning

Kubernetes secrets management

Advanced DevOps Concepts

The architecture must also demonstrate modern DevOps practices such as:

Microservices ready architecture

Kubernetes Horizontal Pod Autoscaling

Blue-Green deployment

Canary deployment

Rolling updates

Self-healing containers

Zero downtime deployment

Centralized logging

Application monitoring and alerting

Secure secret management

Infrastructure as Code

GitOps based deployment workflow

Deployment Flow

Developer pushes code to GitHub.

Jenkins automatically triggers the pipeline.

Jenkins builds the Java application using Maven.

Jenkins generates the WAR file.

Jenkins builds a Docker image.

Jenkins scans the image using Trivy.

Jenkins pushes the Docker image to Docker Hub.

Kubernetes pulls the image from Docker Hub.

ArgoCD deploys the application automatically.

Nginx exposes the application to users.

Prometheus monitors the application.

Grafana visualizes metrics.

ELK stack collects application logs.

Environment

Ubuntu server hosted in Hostinger

Single-node Kubernetes cluster for learning

Docker runtime

Public access through Nginx

Output Requirements

Provide a complete step-by-step implementation guide including:

DevOps architecture diagram explanation

Complete server setup

Docker installation

Kubernetes installation

Jenkins installation and configuration

Terraform infrastructure setup

ArgoCD installation

Prometheus and Grafana setup

ELK stack setup

Dockerfile creation

Jenkinsfile creation

Kubernetes YAML manifests

Helm charts if required

Terraform configuration files

Nginx configuration

CI/CD pipeline explanation

GitOps workflow explanation

Security best practices

Monitoring and alerting setup

Troubleshooting common issues

Also explain:

How to scale the application using Kubernetes

How to implement blue-green deployments

How to implement canary deployments

How to handle production failures

DevOps interview questions based on this architecture

Include all commands, configuration files, scripts, and directory structures required to build this system.

Why this prompt is powerful

If you use this prompt, the AI will generate a complete modern DevOps architecture like this:

Developer
   ↓
GitHub
   ↓
Jenkins CI/CD Pipeline
   ↓
Build (Maven)
   ↓
Docker Image
   ↓
Trivy Security Scan
   ↓
Push to Docker Hub
   ↓
ArgoCD GitOps
   ↓
Kubernetes Cluster
   ↓
Nginx Ingress
   ↓
Users

With additional systems:

Prometheus (Monitoring)

Grafana (Dashboards)

ELK Stack (Logging)

Terraform (Infrastructure)

Autoscaling Pods

Blue/Green deployments

Why this is perfect for you

Because you are:

Learning DevOps

Working on Java + Tomcat

Preparing for DevOps interviews

Building real projects

This single project can demonstrate:

CI/CD

Containerization

Kubernetes

Monitoring

GitOps

Security

Infrastructure automation

That is exactly what companies expect from 3–5 year DevOps engineers.

✅ If you want, I can also show you the complete architecture diagram and final DevOps project structure for your bookbattery repo so you understand exactly what we will build before starting the setup. That will make the implementation much easier.

yes

Kiran, before starting the setup it is very important to understand the final DevOps architecture you are going to build for your bookbattery project. This helps avoid confusion when working with many tools.

Below is a simple but production-style architecture using your repository.

1. Final DevOps Architecture
Developer
   │
   │  Push Code
   ▼
GitHub Repository
(bookbattery - trydevops)
   │
   │ Webhook Trigger
   ▼
Jenkins CI/CD Pipeline
   │
   ├── Pull Source Code
   ├── Build using Maven
   ├── Generate WAR file
   ├── Build Docker Image
   ├── Scan Image (Trivy)
   └── Push Image to DockerHub
   │
   ▼
Docker Hub
(Container Image Registry)
   │
   ▼
Kubernetes Cluster
   │
   ├── Tomcat Application Pod
   ├── MySQL Database Pod
   └── Nginx Reverse Proxy
   │
   ▼
Internet Users
2. DevOps Tools Used
Tool	Purpose
GitHub	Source code storage
Jenkins	CI/CD pipeline
Maven	Build Java application
Docker	Containerize application
Docker Hub	Store Docker images
Kubernetes	Container orchestration
Tomcat	Java application server
MySQL	Database
Nginx	Reverse proxy
Trivy	Security scanning
3. Final Project Structure

For your DevOps repository, the structure should look like this:

bookbattery-devops
│
├── Dockerfile
├── Jenkinsfile
│
├── nginx
│   └── nginx.conf
│
├── k8s
│   ├── mysql-deployment.yaml
│   ├── mysql-service.yaml
│   ├── app-deployment.yaml
│   ├── app-service.yaml
│   └── nginx-deployment.yaml
│
├── terraform
│   └── main.tf
│
└── monitoring
    ├── prometheus.yaml
    └── grafana.yaml
4. CI/CD Pipeline Flow

Your Jenkins pipeline will perform these steps automatically.

Step 1 — Pull Code
GitHub → Jenkins

Jenkins pulls code from:

https://github.com/kiranlintech/bookbattery.git
branch: trydevops
Step 2 — Build Java Application
mvn clean package

Output:

target/bookbattery.war
Step 3 — Build Docker Image

Docker builds image using:

Dockerfile

Example image name:

kiranlintech/bookbattery:v1
Step 4 — Push Image to DockerHub
docker push kiranlintech/bookbattery:v1
Step 5 — Kubernetes Deployment

Kubernetes pulls image from DockerHub.

docker.io/kiranlintech/bookbattery:v1
5. Kubernetes Architecture

Inside Kubernetes you will run:

Cluster
│
├── MySQL Pod
│
├── Tomcat Pod
│     └── bookbattery.war
│
└── Nginx Pod
      │
      └── Reverse Proxy
            │
            ▼
        Tomcat Service
6. Application Request Flow

When a user opens your website:

User
  │
  ▼
Nginx
  │
  ▼
Tomcat Container
  │
  ▼
Java Application
  │
  ▼
MySQL Database
7. What Runs in Docker

Containers you will run:

Container 1 → Jenkins
Container 2 → Tomcat App
Container 3 → MySQL
Container 4 → Nginx
Container 5 → Prometheus
Container 6 → Grafana
8. Kubernetes Objects You Will Create
Resource	Purpose
Deployment	Run containers
Service	Access pods
ConfigMap	Store config
Secret	Store passwords
Ingress	Expose app
9. DevOps Skills This Project Demonstrates

If you complete this project you will know:

CI/CD pipelines

Jenkins automation

Docker containerization

Kubernetes deployment

Reverse proxy setup

Database containerization

Monitoring

DevOps architecture design

This is exactly what DevOps interviews expect.

10. What We Will Build Next (Step by Step)

If you want, I will now guide you through the FULL IMPLEMENTATION in this order:

Phase 1

Server setup

Ubuntu
Docker
Kubernetes
Phase 2

CI/CD setup

Jenkins
GitHub integration
DockerHub integration
Phase 3

Application containerization

Dockerfile
Tomcat container
MySQL container
Phase 4

Kubernetes deployment

Pods
Services
Ingress
Phase 5

Monitoring

Prometheus
Grafana

⭐ If you want, I can now give you the FULL STEP-BY-STEP PRODUCTION SETUP (about 60 steps) starting from:

Hostinger Ubuntu Server → Fully running DevOps system.

This will be the best hands-on DevOps project you can build in 2026.

yes

Great, Kiran 🚀
Let’s start Phase 1: Server Setup for your DevOps project using your repo bookbattery.
We will prepare your Hostinger Ubuntu server to run:

Docker

Jenkins

Kubernetes

Nginx

MySQL

Tomcat containers

I will keep it very beginner-friendly and step-by-step.

Phase 1 — Prepare Ubuntu Server

Login to your server.

ssh root@YOUR_SERVER_IP

or

ssh ubuntu@YOUR_SERVER_IP

Update the server.

sudo apt update && sudo apt upgrade -y

Install basic tools.

sudo apt install git curl wget unzip vim -y

Check OS.

lsb_release -a
Phase 2 — Install Docker

Remove old versions.

sudo apt remove docker docker-engine docker.io containerd runc

Install dependencies.

sudo apt install apt-transport-https ca-certificates curl software-properties-common -y

Add Docker GPG key.

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

Add Docker repository.

sudo add-apt-repository \
"deb [arch=amd64] https://download.docker.com/linux/ubuntu \
$(lsb_release -cs) stable"

Install Docker.

sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io -y

Start Docker.

sudo systemctl start docker
sudo systemctl enable docker

Check Docker.

docker --version

Test Docker.

sudo docker run hello-world
Phase 3 — Install Docker Compose
sudo apt install docker-compose -y

Check version.

docker-compose --version
Phase 4 — Install Jenkins

Add Jenkins key.

curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo tee \
/usr/share/keyrings/jenkins-keyring.asc > /dev/null

Add repository.

echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
/etc/apt/sources.list.d/jenkins.list > /dev/null

Update packages.

sudo apt update

Install Java.

sudo apt install openjdk-17-jdk -y

Install Jenkins.

sudo apt install jenkins -y

Start Jenkins.

sudo systemctl start jenkins
sudo systemctl enable jenkins

Check status.

sudo systemctl status jenkins
Phase 5 — Open Jenkins

Open browser:

http://SERVER_IP:8080

Get admin password.

sudo cat /var/lib/jenkins/secrets/initialAdminPassword

Paste it in browser.

Install Suggested Plugins.

Create admin user.

Phase 6 — Install Maven

Jenkins needs Maven to build Java.

sudo apt install maven -y

Verify.

mvn -version
Phase 7 — Install Kubernetes (K3s – lightweight)

For learning, we use K3s (lightweight Kubernetes).

Install K3s.

curl -sfL https://get.k3s.io | sh -

Check node.

sudo kubectl get nodes

You should see:

NAME      STATUS
ubuntu    Ready
Phase 8 — Install Nginx
sudo apt install nginx -y

Start Nginx.

sudo systemctl start nginx
sudo systemctl enable nginx

Check:

http://SERVER_IP

You should see Welcome to Nginx.

Phase 9 — Install Trivy (Security Scanner)
sudo apt install wget apt-transport-https gnupg lsb-release -y

Add repository.

wget -qO - https://aquasecurity.github.io/trivy-repo/deb/public.key | sudo apt-key add -
echo deb https://aquasecurity.github.io/trivy-repo/deb $(lsb_release -sc) main | sudo tee /etc/apt/sources.list.d/trivy.list

Install.

sudo apt update
sudo apt install trivy -y

Check version.

trivy --version
Phase 10 — Create Project Workspace
mkdir devops-project
cd devops-project

Clone your repository.

git clone -b trydevops https://github.com/kiranlintech/bookbattery.git

Go inside.

cd bookbattery
Phase 1 Completed 🎉

Your server now has:

Component	Installed
Git	✅
Docker	✅
Jenkins	✅
Maven	✅
Kubernetes	✅
Nginx	✅
Trivy	✅
Next Phase (Very Important)

In Phase 2 we will build the CI/CD pipeline:

1️⃣ Create Dockerfile
2️⃣ Create Jenkinsfile
3️⃣ Configure DockerHub credentials
4️⃣ Jenkins build WAR
5️⃣ Build Docker image
6️⃣ Push image to DockerHub

If you want, I can also show you something extremely useful for your project:

How to design the PERFECT Dockerfile for your Java + Tomcat application (most beginners do this wrong).

yes

Great, Kiran 👍
Since your bookbattery project produces a .war file and runs on Tomcat, the correct approach is to build a Tomcat-based Docker image and copy the WAR into it.

Many beginners make mistakes here, so I'll show the proper production-style Dockerfile.

1. Understand What We Are Doing

Your pipeline will work like this:

GitHub
   ↓
Jenkins
   ↓
Maven Build
   ↓
bookbattery.war
   ↓
Docker Build
   ↓
Tomcat Container
   ↓
Application Running

Tomcat will automatically deploy the WAR.

2. Create Dockerfile

Go inside your repository on the server.

cd ~/devops-project/bookbattery

Create Dockerfile.

vim Dockerfile

Add this code.

# Use official Tomcat image
FROM tomcat:9-jdk17

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file to Tomcat
COPY target/bookbattery.war /usr/local/tomcat/webapps/bookbattery.war

# Expose Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]

Save file.

3. Build Application (WAR)

Inside the project directory run:

mvn clean package

You should see:

target/bookbattery.war

Check:

ls target
4. Build Docker Image

Login to DockerHub first.

docker login

Then build the image.

docker build -t kiranlintech/bookbattery:v1 .

Check image.

docker images

Example output:

REPOSITORY                  TAG
kiranlintech/bookbattery    v1
5. Run Container

Run the container.

docker run -d -p 8081:8080 kiranlintech/bookbattery:v1

Check running containers.

docker ps
6. Test Application

Open browser:

http://SERVER_IP:8081/bookbattery

Your application should load.

7. Push Image to DockerHub

Push the image.

docker push kiranlintech/bookbattery:v1

Now Kubernetes and Jenkins can pull the image from DockerHub.

8. Best Practice Improvement (Production Dockerfile)

For CI/CD pipelines, we use multi-stage builds.

Better Dockerfile:

# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Stage 2: Run
FROM tomcat:9-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/bookbattery.war /usr/local/tomcat/webapps/bookbattery.war

EXPOSE 8080
CMD ["catalina.sh", "run"]

Advantages:

smaller image

cleaner build

production ready

9. Your DevOps Repository Should Now Look Like
bookbattery
│
├── src
├── pom.xml
├── Dockerfile
└── Jenkinsfile (next step)
Next Step (Most Important)

Next we will create a production Jenkins CI/CD pipeline that will:

1 Pull code from GitHub
2 Build WAR using Maven
3 Build Docker image
4 Scan image using Trivy
5 Push image to DockerHub
6 Deploy to Kubernetes

If you want, I can also give you the FULL Jenkinsfile for your bookbattery project (this is exactly what companies expect in DevOps interviews).

yes

Great, Kiran 🚀
Now we will create the Jenkins CI/CD Pipeline for your bookbattery Java application.

This pipeline will automatically:

1️⃣ Pull code from GitHub
2️⃣ Build WAR using Maven
3️⃣ Build Docker image
4️⃣ Scan Docker image using Trivy
5️⃣ Push image to Docker Hub

This is very similar to real DevOps pipelines used in companies.

1. Jenkins Pipeline Architecture

Your pipeline flow will look like this:

GitHub
   │
   │  Code Push
   ▼
Jenkins Pipeline
   │
   ├── Clone Repository
   ├── Maven Build
   ├── Build Docker Image
   ├── Trivy Security Scan
   └── Push Image to DockerHub
2. Install Jenkins Required Plugins

Open Jenkins:

http://SERVER_IP:8080

Go to:

Manage Jenkins
 → Manage Plugins

Install these plugins:

Docker Pipeline

Git

Pipeline

Maven Integration

Credentials Binding

Restart Jenkins after installation.

3. Add Docker Permission to Jenkins

Jenkins must run Docker commands.

Run on server:

sudo usermod -aG docker jenkins

Restart Jenkins.

sudo systemctl restart jenkins
4. Add DockerHub Credentials in Jenkins

Go to:

Manage Jenkins
 → Credentials

Add new credential:

Type:

Username with Password

Example:

ID: dockerhub-creds
Username: your_dockerhub_username
Password: dockerhub_password

Save.

5. Create Jenkins Pipeline Job

Go to Jenkins dashboard.

New Item

Name:

bookbattery-pipeline

Select:

Pipeline

Click OK.

6. Configure GitHub Repository

In pipeline settings:

Select:

Pipeline Script from SCM

SCM:

Git

Repository URL:

https://github.com/kiranlintech/bookbattery.git

Branch:

trydevops

Script path:

Jenkinsfile
7. Create Jenkinsfile

Inside your GitHub repo create a file:

Jenkinsfile

Add this code.

pipeline {

    agent any

    environment {
        DOCKER_IMAGE = "kiranlintech/bookbattery"
        TAG = "latest"
    }

    stages {

        stage('Clone Repository') {
            steps {
                git branch: 'trydevops', url: 'https://github.com/kiranlintech/bookbattery.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$TAG .'
            }
        }

        stage('Trivy Scan') {
            steps {
                sh 'trivy image $DOCKER_IMAGE:$TAG'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                credentialsId: 'dockerhub-creds',
                usernameVariable: 'USER',
                passwordVariable: 'PASS'
                )]) {

                sh '''
                echo $PASS | docker login -u $USER --password-stdin
                docker push $DOCKER_IMAGE:$TAG
                '''
                }
            }
        }

    }

}

Commit and push this file.

8. Run Pipeline

Go to Jenkins.

Click:

Build Now

You will see stages:

Clone Repository
Build Maven
Build Docker Image
Trivy Scan
Push Docker Image
9. Verify DockerHub

Open DockerHub.

https://hub.docker.com

You should see:

kiranlintech/bookbattery

Image:

latest
10. Your CI/CD Pipeline is Ready 🎉

Current flow:

Developer Push Code
        │
        ▼
      GitHub
        │
        ▼
      Jenkins
        │
        ├── Build WAR
        ├── Build Docker Image
        ├── Security Scan
        └── Push Image
        ▼
    DockerHub
Next Step (Very Powerful)

Now we will deploy your application to Kubernetes automatically.

Next we will create:

Kubernetes Deployment

Kubernetes Service

Kubernetes Nginx Reverse Proxy

Final architecture:

Internet
   │
   ▼
Nginx
   │
   ▼
Kubernetes Service
   │
   ▼
Tomcat Pod
   │
   ▼
MySQL Pod

If you want, I can also show you the complete Kubernetes YAML files for your project (ready to deploy).
This will complete your full DevOps pipeline.

yes

Great, Kiran 🚀
Now we will complete the final stage of your DevOps pipeline: deploying your bookbattery application to Kubernetes.

Your Docker image (built by Jenkins and pushed to Docker Hub) will now be deployed in Kubernetes using YAML files.

We will create:

1️⃣ MySQL Database
2️⃣ Java Application (Tomcat container)
3️⃣ Kubernetes Services
4️⃣ Nginx Reverse Proxy

1. Kubernetes Deployment Architecture

Your application will run inside Kubernetes like this:

Internet User
      │
      ▼
    Nginx
      │
      ▼
Kubernetes Service
      │
      ▼
Application Pod (Tomcat + bookbattery.war)
      │
      ▼
MySQL Database Pod
2. Create Kubernetes Directory

On your server:

mkdir k8s
cd k8s
3. MySQL Deployment

Create file:

vim mysql-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - name: mysql
        image: mysql:8
        ports:
        - containerPort: 3306
        env:
        - name: MYSQL_ROOT_PASSWORD
          value: root
        - name: MYSQL_DATABASE
          value: bookbattery
4. MySQL Service

Create:

vim mysql-service.yaml
apiVersion: v1
kind: Service
metadata:
  name: mysql-service
spec:
  selector:
    app: mysql
  ports:
  - port: 3306
    targetPort: 3306
5. Application Deployment

Create:

vim app-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: bookbattery-app
spec:
  replicas: 2
  selector:
    matchLabels:
      app: bookbattery
  template:
    metadata:
      labels:
        app: bookbattery
    spec:
      containers:
      - name: bookbattery
        image: kiranlintech/bookbattery:latest
        ports:
        - containerPort: 8080
        env:
        - name: DB_HOST
          value: mysql-service
6. Application Service

Create:

vim app-service.yaml
apiVersion: v1
kind: Service
metadata:
  name: bookbattery-service
spec:
  type: NodePort
  selector:
    app: bookbattery
  ports:
  - port: 8080
    targetPort: 8080
    nodePort: 30007
7. Deploy to Kubernetes

Apply configurations:

kubectl apply -f mysql-deployment.yaml
kubectl apply -f mysql-service.yaml
kubectl apply -f app-deployment.yaml
kubectl apply -f app-service.yaml
8. Check Deployments

Check pods:

kubectl get pods

Example:

mysql-xxxx
bookbattery-xxxx
bookbattery-xxxx

Check services:

kubectl get svc

Example:

mysql-service
bookbattery-service
9. Access Application

Open browser:

http://SERVER_IP:30007/bookbattery

Your application should load.

10. Scaling Application

Kubernetes allows scaling easily.

Example:

kubectl scale deployment bookbattery-app --replicas=5

Check:

kubectl get pods

You will see 5 running containers.

11. Final DevOps Pipeline (Complete)

Your full system now works like this:

Developer
   │
   ▼
GitHub
   │
   ▼
Jenkins Pipeline
   │
   ├── Maven Build
   ├── Docker Build
   ├── Trivy Scan
   └── Push to DockerHub
   │
   ▼
Kubernetes Cluster
   │
   ├── MySQL Pod
   ├── BookBattery App Pods
   └── Services
   │
   ▼
Internet Users
12. Skills You Learned

By completing this project you used:

GitHub

Jenkins

Maven

Docker

Docker Hub

Kubernetes

Tomcat

MySQL

Nginx

Trivy

This is a complete DevOps project.

✅ Kiran, if you want, I can also show you 3 extremely important improvements that will make this project look like a real enterprise DevOps architecture:

1️⃣ Auto Deploy to Kubernetes from Jenkins
2️⃣ Nginx Ingress Controller instead of NodePort
3️⃣ Monitoring with Prometheus + Grafana

These upgrades make the project 10x more impressive for DevOps interviews.

yes

Great, Kiran 🚀
Let’s upgrade your project to look more like a real production DevOps architecture. We’ll add the three improvements we discussed.

1️⃣ Auto-Deploy to Kubernetes from Jenkins

Right now your pipeline stops after pushing the Docker image.
In real DevOps pipelines, Jenkins deploys automatically to Kubernetes.

Update your Jenkinsfile

Add a Kubernetes deployment stage.

pipeline {

    agent any

    environment {
        IMAGE = "kiranlintech/bookbattery"
        TAG = "latest"
    }

    stages {

        stage('Clone Repo') {
            steps {
                git branch: 'trydevops', url: 'https://github.com/kiranlintech/bookbattery.git'
            }
        }

        stage('Build Application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE:$TAG .'
            }
        }

        stage('Push Image to DockerHub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'USER',
                    passwordVariable: 'PASS'
                )]) {

                sh '''
                echo $PASS | docker login -u $USER --password-stdin
                docker push $IMAGE:$TAG
                '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/'
            }
        }

    }
}

Now the pipeline becomes:

GitHub → Jenkins → Build → Docker → Push → Kubernetes Deploy
2️⃣ Use Nginx Ingress (Better than NodePort)

Earlier we used NodePort (30007).
Production clusters use Ingress Controller.

Install Nginx Ingress Controller.

kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml

Check:

kubectl get pods -n ingress-nginx
Create Ingress File

Create:

vim ingress.yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: bookbattery-ingress
spec:
  rules:
  - host: bookbattery.local
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: bookbattery-service
            port:
              number: 8080

Apply:

kubectl apply -f ingress.yaml

Add entry in server:

sudo vim /etc/hosts

Add:

SERVER_IP bookbattery.local

Access:

http://bookbattery.local/bookbattery
3️⃣ Add Monitoring (Prometheus + Grafana)

Production systems must monitor applications.

Install Helm first.

sudo snap install helm --classic

Add Prometheus repo.

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts

Update repo.

helm repo update

Install monitoring stack.

helm install monitoring prometheus-community/kube-prometheus-stack

Check pods:

kubectl get pods

You will see:

prometheus
grafana
alertmanager
Access Grafana

Port forward:

kubectl port-forward svc/monitoring-grafana 3000:80

Open browser:

http://SERVER_IP:3000

Default login:

username: admin
password: prom-operator

You will see Kubernetes metrics dashboards.

4️⃣ Final Enterprise DevOps Architecture

Your final system now looks like this:

Developer
   │
   ▼
GitHub
   │
   ▼
Jenkins Pipeline
   │
   ├── Maven Build
   ├── Docker Image
   ├── Security Scan
   ├── Push to DockerHub
   └── Deploy to Kubernetes
   │
   ▼
Kubernetes Cluster
   │
   ├── Nginx Ingress
   ├── BookBattery Pods
   ├── MySQL Pod
   └── Services
   │
   ▼
Users

Monitoring:

Prometheus → Metrics
Grafana → Dashboards
5️⃣ DevOps Skills This Project Demonstrates

If you show this project in interviews, it covers:

✔ CI/CD pipelines
✔ Containerization
✔ Kubernetes deployment
✔ Reverse proxy / ingress
✔ Infrastructure automation
✔ Monitoring and observability
✔ Security scanning

This is exactly what companies expect from DevOps engineers.

✅ Suggestion for you, Kiran

Your project will become 10× stronger if you also add:

ArgoCD (GitOps deployment)

Terraform (Infrastructure as Code)

Kubernetes autoscaling

Blue-Green deployment

These are advanced DevOps interview topics.

If you want, I can also show you the FINAL polished DevOps project architecture for your bookbattery repo that looks like a real enterprise project used in resumes and interviews. It will make your project stand out.

yes

Great, Kiran 🚀
Now I’ll show you the FINAL polished DevOps architecture for your bookbattery project. This is the kind of architecture that looks very strong on resumes and in DevOps interviews.

I’ll keep it simple enough to implement on your single Hostinger Ubuntu server, but still enterprise-style.

1. Final Enterprise DevOps Architecture
Developer
   │
   │ Push Code
   ▼
GitHub (bookbattery repo)
   │
   │ Webhook
   ▼
Jenkins CI/CD Pipeline
   │
   ├── Clone Repository
   ├── Maven Build
   ├── Unit Tests
   ├── Build Docker Image
   ├── Trivy Security Scan
   ├── Push Image → DockerHub
   └── Update Kubernetes Deployment
   │
   ▼
DockerHub (Image Registry)
   │
   ▼
Kubernetes Cluster
   │
   ├── Nginx Ingress Controller
   │
   ├── BookBattery Application Pods
   │       └── Tomcat + bookbattery.war
   │
   ├── MySQL Database Pod
   │
   └── Kubernetes Services
   │
   ▼
Users

Monitoring Stack:

Prometheus → Collect Metrics
Grafana → Dashboard Visualization

Logging (optional upgrade):

ELK Stack
Elasticsearch
Logstash
Kibana
2. Complete DevOps Tools Used
Tool	Purpose
GitHub	Source code
Jenkins	CI/CD pipeline
Maven	Build Java app
Docker	Containerization
DockerHub	Image registry
Kubernetes	Container orchestration
Nginx Ingress	Application routing
MySQL	Database
Trivy	Security scanning
Prometheus	Monitoring
Grafana	Visualization
3. Final Project Directory Structure

Your DevOps repository should look like this:

bookbattery-devops
│
├── Dockerfile
├── Jenkinsfile
│
├── k8s
│   ├── mysql-deployment.yaml
│   ├── mysql-service.yaml
│   ├── app-deployment.yaml
│   ├── app-service.yaml
│   └── ingress.yaml
│
├── monitoring
│   └── prometheus-grafana.yaml
│
├── nginx
│   └── nginx.conf
│
└── terraform
    └── main.tf

This structure looks very professional.

4. CI/CD Pipeline Flow

When a developer pushes code:

git push origin trydevops

Pipeline runs automatically.

Step 1
Clone GitHub Repo

Step 2
Build Java App
mvn clean package

Step 3
Create WAR file
target/bookbattery.war

Step 4
Build Docker Image

Step 5
Security Scan
Trivy

Step 6
Push Docker Image
DockerHub

Step 7
Deploy to Kubernetes
kubectl apply
5. Kubernetes Deployment

Inside Kubernetes:

Cluster
│
├── bookbattery-deployment
│       └── 2 Pods
│
├── mysql-deployment
│       └── 1 Pod
│
├── Services
│
└── Nginx Ingress

Traffic flow:

User
  │
  ▼
Nginx Ingress
  │
  ▼
bookbattery-service
  │
  ▼
bookbattery pods
  │
  ▼
MySQL database
6. Scaling Example

Increase replicas:

kubectl scale deployment bookbattery-app --replicas=5

Kubernetes automatically distributes traffic.

7. Self-Healing

If a container crashes:

Pod Crash
     │
     ▼
Kubernetes detects failure
     │
     ▼
New Pod Automatically Created

This is self-healing architecture.

8. DevOps Interview Questions From This Project

If you explain this project in interviews, they may ask:

Example Questions

1️⃣ How Jenkins integrates with GitHub?
2️⃣ How Docker images are built in pipeline?
3️⃣ How Kubernetes pulls images from DockerHub?
4️⃣ Difference between Deployment vs Pod vs Service?
5️⃣ What is Ingress Controller?
6️⃣ How do you scale applications in Kubernetes?
7️⃣ What is Trivy scanning?
8️⃣ What happens if a container crashes?
9️⃣ How monitoring works with Prometheus?
🔟 Difference between NodePort vs Ingress?

This project prepares you for many DevOps interview questions.

9. What Will Make This Project 10× Stronger

Add these advanced features later:

GitOps Deployment

Use:

ArgoCD

Instead of Jenkins deploying.

Infrastructure as Code

Use:

Terraform

For server provisioning.

Autoscaling
Horizontal Pod Autoscaler

Automatically scale pods based on CPU.

Blue-Green Deployment

Zero downtime deployment strategy.

10. Result

After finishing this project you will know:

CI/CD pipelines

Docker containerization

Kubernetes deployments

Reverse proxy configuration

Monitoring and observability

Security scanning

DevOps architecture design

This is a complete end-to-end DevOps project.

✅ Kiran, if you want, I can also show you something extremely useful:

I can convert your bookbattery project into a FULL production DevOps lab (about 120 steps) that will teach you 90% of DevOps tools used in companies.

It will include:

Jenkins pipeline optimization

Helm charts

ArgoCD GitOps

Kubernetes autoscaling

ELK logging

DevOps troubleshooting scenarios.
