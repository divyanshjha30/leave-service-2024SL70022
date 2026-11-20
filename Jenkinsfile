pipeline {
    agent any

    environment {
        IMAGE_NAME = 'leave-service-2024sl70022'
        IMAGE_TAG  = "${BUILD_NUMBER}"
        SONAR_URL  = 'http://localhost:9000'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/divyanshjha30/leave-service-2024SL70022.git'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn -B clean verify'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Code Inspection') {
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh 'mvn -B sonar:sonar -Dsonar.host.url=$SONAR_URL -Dsonar.token=$SONAR_TOKEN'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
                sh 'docker images $IMAGE_NAME'
            }
        }

        stage('Deploy to Staging') {
            steps {
                sh 'docker rm -f leave-service-staging || true'
                sh 'docker run -d --name leave-service-staging -e APP_ENV=staging -p 8085:8080 $IMAGE_NAME:$IMAGE_TAG'
                sh 'sleep 5 && curl -s http://localhost:8085'
            }
        }

        stage('Approval') {
            steps {
                input message: 'Staging verified on http://localhost:8085. Deploy this image to production?',
                      ok: 'Approve and deploy to production'
            }
        }

        stage('Deploy to Production') {
            steps {
                sh 'docker rm -f leave-service-production || true'
                sh 'docker run -d --name leave-service-production -e APP_ENV=production -p 8086:8080 $IMAGE_NAME:$IMAGE_TAG'
                sh 'sleep 5 && curl -s http://localhost:8086'
                sh 'docker ps --filter name=leave-service --format "table {{.Names}}\\t{{.Image}}\\t{{.Ports}}"'
            }
        }
    }
}
