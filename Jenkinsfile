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
