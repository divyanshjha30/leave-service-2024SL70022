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
    }
}
