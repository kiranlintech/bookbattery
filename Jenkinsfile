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
                sh '''
                kubectl apply -f /home/ubuntu/k8s/
                '''
            }
        }

    }
}
