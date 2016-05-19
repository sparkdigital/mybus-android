#!groovy

stage 'build'
node {
    echo "Checking out the code from Github"
    checkout scm

    echo "Building the project with Gradle Wrapper"
    sh './gradlew build -x test'    
}

stage 'unit-test'
node {
    echo "Running the unit tests for the project"
    sh './gradlew clean test'

    echo "Publishing Unit Tests results"
    step([$class: 'JUnitResultArchiver', testResults: '**app/build/test-results/release/TEST-*.xml'])
}
