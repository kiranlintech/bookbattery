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
                echo "🚀 Building Application..."
                sh 'mvn -B -ntp -q clean package'
                echo "✅ Build Completed"
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t image . > /dev/null'
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
                docker push $IMAGE:$TAG > /dev/null
                '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                export KUBECONFIG=/etc/rancher/k3s/k3s.yaml
                kubectl apply -f k8s/
                '''
            }
        }

    }
}
