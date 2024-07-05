pipeline {
    agent any

    environment {
        IMAGE_TAG = "v4.0.0"
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

         stage('Stop and remove old container') {
            steps {
                script {
                    sh 'docker stop devops-container || true'
                    sh 'docker rm devops-container || true'
                }
            }
         }

        stage('Run docker container') {
            steps {
                script {
                    sh "docker run -d --name devops-container -p 9090:9090 lgbptit/devops-integration:${IMAGE_TAG}"
                }
            }
        }

        stage('Clean old docker image') {
            steps {
                script {
                    sh 'docker rmi -f lgbptit/devops-integration:v1 || true'
                }
            }
        }

    }
}
