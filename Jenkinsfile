def dockerimage = null
pipeline {
    environment {
        COMMITTER_EMAIL = sh (
            script: 'git --no-pager show -s --format=\'%ae\'',
            returnStdout: true
        ).trim()
    }
    agent any
    stages {
        stage('Prepare enviroment') {
            steps {
                sh  '''
            echo "Copiamos el properties dentro del proyecto"
            mv src/main/resources/application.properties-sample src/main/resources/application.properties
            '''
            }
        }
        stage('Testing') {
            when {
                not {
                    branch 'produccion'
                }
            }
            steps {
                sh  '''
                echo "Hacemos testing"
                mvn test
                '''
            }
        }
        stage('Compile') {
            when {
                anyOf {
                    branch 'desarrollo'
                    branch 'produccion'
                }
            }
            steps {
                sh  '''
                echo "Hacemos el package"
                mvn package
                '''
            }
        }
        stage('Build docker image') {
            when {
                branch 'produccion'
            }
            steps {
                
                script {
                    sh  '''
                    echo "Contruimos la imagen docker"
                    '''
                    dockerimage = docker.build("imagen-core")
                    
                cleanWs()
                }
                
            }
        }

        stage('Upload image to registry') {
            when {
                branch 'produccion'
            }
            steps  {
                sh  '''
                echo "Subimos la imagen docker creada"
                '''
                script {
                    docker.withRegistry('https://registry-back.esliceu.com', 'registry_jenkins') {
                        dockerimage.push()
                    }
                    cleanWs()
                }
                
            }
        }

        stage('Deploying on produccion') {
            when {
                branch 'produccion'
            }
            steps  {
                sh  '''
            echo "desplegamos core_i_menjador"
            ssh deploy.esliceu.com "cd core_i_menjador; docker-compose stop; docker-compose pull; docker-compose up -d"
            '''
                cleanWs()
            }
        }
    }
    post {
        success {
            slackSend channel: '#jenkins-builds',  color: 'good', message: "The pipeline ${currentBuild.fullDisplayName} completed successfully from ${env.COMMITTER_EMAIL}. You can check it on ${env.BUILD_URL}."
        }
        failure {
            slackSend channel: '#jenkins-builds', color: '#ff0000', message: "The pipeline ${currentBuild.fullDisplayName} failed from ${env.COMMITTER_EMAIL}. You can check it on ${env.BUILD_URL}."
        }
    }
}
