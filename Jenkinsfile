#!groovy

stage 'Checkout'
node {

    echo "Checking out the code from Github"
    checkout scm
}

stage 'Clean'
node {
    echo "Cleaning Up"
    sh './gradlew clean'  
}

stage 'Static Code Check'
node {
    echo "Running CheckStyle, FindBugs & PMD"
    sh './gradlew app:check'

    echo "Publishing checker results"
    step([$class: 'CheckStylePublisher', pattern: '**app/build/reports/checkstyle/*.xml'])
    step([$class: 'FindBugsPublisher', pattern: '**app/build/reports/findbugs/*.xml'])
    step([$class: 'PmdPublisher', pattern: '**app/build/reports/pmd/*.xml'])
}


stage 'Testing'
node {
    echo "Building and running the unit tests for the project"
    sh 'sh JenkinsAndroidTest.sh'

    echo "Publishing Unit Tests results"
    step([$class: 'JUnitResultArchiver', testResults: '**app/build/test-results/release/TEST-*.xml'])
}

