pipeline{
    agent any

    tools{
        maven 'maven_3_5_0'
    }
    stages{
       stage('Build maven'){
           steps{
               checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/LG-BaoPTIT/devops']])
               sh 'mvn clean install'
           }
       }
       stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t lgbptit/devops-integration .'
                }
            }
       }
       stage('Push image to Hub'){
           steps{
               script{
                    withCredentials([string(credentialsId: 'dockerhup-pwd', variable: 'dockerhuppwd')]) {
                        sh 'docker login -u lgbptit -p ${dockerhuppwd}'
                    }
                    sh 'docker push lgbptit/devops-integration'
               }
           }
       }

    }
}