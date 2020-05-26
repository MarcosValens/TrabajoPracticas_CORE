pipeline {
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
                branch 'desarrollo';
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
            when{
                branch 'produccion'
            }
            steps {
                sh  '''
                    echo "Contruimos la imagen docker"
                    docker build -t imagen-core .
                    '''
                cleanWs()
            }
    }

    stage('Upload image to registry'){
        when{
            branch 'produccion'
        }
        steps  {
            sh  '''
                echo "Subimos la imagen docker creada"
                docker tag  imagen-core  registry-back.esliceu.com/imagen-core
                docker push registry-back.esliceu.com/imagen-core
                '''
            cleanWs()
        }
    }

    stage('Deploying on produccion'){
        when{
            branch 'produccion'
        }
        steps  {
        sh  '''
            echo "desplegamos"core_i_menjador
            ssh deploy.esliceu.com "cd core_i_menjador; docker-compose stop; docker-compose pull; docker-compose up -d"
            '''
        cleanWs()
        }
    }
    stage("Informando via Slack"){
        when{
            branch 'produccion'
        }
        steps{
            slackSend channel: '#builds', message: 'hello world'
            cleanWs()
        }
    }
  }
}
