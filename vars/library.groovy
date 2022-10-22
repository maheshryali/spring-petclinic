def call ()
{
pipeline {
agent {label 'SPRING'}
stages {
       stage('test') {
                 steps{
            sh 'echo hellow world'
}
}
}
}
}
