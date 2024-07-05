pipeline {
    agent any

    environment {
        IMAGE_TAG = "v1"
    }

    tools {
        maven 'maven_3_5_0'
    }

    stages {
        stage('Build maven') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/LG-BaoPTIT/devops']])
                sh 'mvn clean install'
            }
        }

        stage('Clean old docker image') {
            steps {
                script {
                    sh 'docker rmi -f lgbptit/devops-integration:latest || true'
                }
            }
        }

        stage('Build docker image') {
            steps {
                script {
                    sh "docker build -t lgbptit/devops-integration:${IMAGE_TAG} ."
                }
            }
        }

        stage('Push image to Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhup-pwd', variable: 'dockerhuppwd')]) {
                        sh "docker login -u lgbptit -p ${dockerhuppwd}"
                        sh "docker push lgbptit/devops-integration:${IMAGE_TAG}"
                    }
                }
            }
        }

        stage('Run docker container') {
            steps {
                script {
                    sh 'docker stop devops-container || true'
                    sh 'docker rm devops-container || true'
                    sh "docker run -d --name devops-container -p 9090:9090 lgbptit/devops-integration:${IMAGE_TAG}"
                }
            }
        }
    }
}
