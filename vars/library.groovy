def call ()
{
pipeline {
agent {label 'SPRING'}
stages {
       stage('test') {
                 steps{
            sh 'echo hira vamsi movieki velthunava'
}
}
}
}
}
