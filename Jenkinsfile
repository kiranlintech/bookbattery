pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        APP_NAME      = "bookbattery"
        IMAGE_NAME    = "bookbattery-tomcat"
        CONTAINER     = "bookbattery-app"
        DOCKER_PORT   = "8081"
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'jenkins-docker',
                    credentialsId: 'jenkins_id',
                    url: 'git@github.com:kiranlintech/bookbattery.git'
            }
        }

        stage('Build & Unit Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Package WAR') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    COMMIT_ID = sh(
                        script: "git rev-parse --short HEAD",
                        returnStdout: true
                    ).trim()

                    sh """
                    docker build -t ${IMAGE_NAME}:${COMMIT_ID} .
                    docker tag ${IMAGE_NAME}:${COMMIT_ID} ${IMAGE_NAME}:latest
                    """
                }
            }
        }

        stage('Stop Old Container') {
            steps {
                sh "docker rm -f ${CONTAINER} || true"
            }
        }

        stage('Run New Container') {
            steps {
                sh """
                docker run -d \
                  --name ${CONTAINER} \
                  -p ${DOCKER_PORT}:8080 \
                  --restart unless-stopped \
                  ${IMAGE_NAME}:${COMMIT_ID}
                """
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                sleep 15
                curl -f http://localhost:8081 || exit 1
                '''
            }
        }

        stage('Cleanup Old Images') {
            steps {
                sh 'docker image prune -f'
            }
        }
    }

    post {
        success {
            echo "Deployment successful"
        }
        failure {
            echo "Deployment failed – rollback required"
        }
    }
}
