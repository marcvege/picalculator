pipeline {
    agent {
        docker { image 'maven:alpine' }
    }
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        stage('Image') {
            steps {
                app = docker.build("ciberado/picalculator")
            }
        }
    }
}