pipeline {
    agent {
        docker { image 'maven:alpine' }
    }
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean surefire:test'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }
}