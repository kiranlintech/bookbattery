pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    options {
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    environment {
        TOMCAT_USER = "ubuntu"
        TOMCAT_HOST = "40.192.15.143"
        TOMCAT_DIR  = "/opt/tomcat"
        WAR_NAME    = "bookbattery.war"
        SSH_CRED    = "jenkins_id"
    }

    stages {

        stage('Cleanup') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'deployment',
                credentialsId: env.SSH_CRED,
                    url: 'git@github.com:kiranlintech/bookbattery.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Archive WAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }

        stage('Stop Tomcat') {
            steps {
                withCredentials([
                    sshUserPrivateKey(
                        credentialsId: env.SSH_CRED,
                        keyFileVariable: 'SSH_KEY'
                    )
                ]) {
                    sh '''
                    ssh -i $SSH_KEY -o StrictHostKeyChecking=no \
                        ${TOMCAT_USER}@${TOMCAT_HOST} "
                        if [ -x ${TOMCAT_DIR}/bin/shutdown.sh ]; then
                            ${TOMCAT_DIR}/bin/shutdown.sh || true
                            sleep 10
                        else
                            echo 'Tomcat not found, skipping stop'
                        fi
                        "
                    '''
                }
            }
        }

        stage('Deploy WAR to Tomcat') {
            steps {
                withCredentials([
                    sshUserPrivateKey(
                        credentialsId: env.SSH_CRED,
                        keyFileVariable: 'SSH_KEY'
                    )
                ]) {
                    sh '''
                    if [ ! -f target/${WAR_NAME} ]; then
                        echo "WAR not found"
                        exit 1
                    fi

                    ssh -i $SSH_KEY -o StrictHostKeyChecking=no \
                        ${TOMCAT_USER}@${TOMCAT_HOST} "
                        rm -rf ${TOMCAT_DIR}/webapps/bookbattery*
                        "

                    scp -i $SSH_KEY -o StrictHostKeyChecking=no \
                        target/${WAR_NAME} \
                        ${TOMCAT_USER}@${TOMCAT_HOST}:${TOMCAT_DIR}/webapps/
                    '''
                }
            }
        }

        stage('Start Tomcat') {
            steps {
                withCredentials([
                    sshUserPrivateKey(
                        credentialsId: env.SSH_CRED,
                        keyFileVariable: 'SSH_KEY'
                    )
                ]) {
                    sh '''
                    ssh -i $SSH_KEY ${TOMCAT_USER}@${TOMCAT_HOST} "${TOMCAT_DIR}/bin/startup.sh"
                    '''
                }
            }
        }
    }

    post {
        success {
            echo "WAR deployed and Tomcat started successfully"
        }
        failure {
            echo "Deployment failed"
        }
    }
}
