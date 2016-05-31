#!groovy

stage 'build'
node {

    echo "Checking out the code from Github"
    checkout scm

    echo "Running Instrumentation Tests"
    build job: ' ../mybus-emulator/branch/${BRANCH_NAME}'


    echo "Building and running the unit tests for the project"
    sh './gradlew clean test'

    echo "Publishing Unit Tests results"
    step([$class: 'JUnitResultArchiver', testResults: '**app/build/test-results/release/TEST-*.xml'])
}
