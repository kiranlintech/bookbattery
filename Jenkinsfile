pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        IMAGE_NAME = "bookbattery-tomcat"
        CONTAINER  = "bookbattery-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'jenkins-docker',
                    url: 'git@github.com:kiranlintech/bookbattery.git'
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME:latest .'
            }
        }

        stage('Stop Old Container') {
            steps {
                sh '''
                docker rm -f $CONTAINER || true
                '''
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                docker run -d \
                  --name $CONTAINER \
                  -p 8080:8080 \
                  $IMAGE_NAME:latest
                '''
            }
        }
    }
}
