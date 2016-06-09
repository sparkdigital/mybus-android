#SHELL Env Needed
export SHELL=/bin/bash

#Clear Android Temp Files
rm -rf /tmp/android-unknown

#Start the emulator
EMULATOR_NAME=emulator_gaps_arm
emulator -verbose -avd $EMULATOR_NAME -no-window -wipe-data -no-snapshot -memory 128 &
EMULATOR_PID=$!

#Checking Devices
adb devices

# Wait for Android to finish booting
WAIT_CMD="adb -e wait-for-device shell getprop init.svc.bootanim"
until $WAIT_CMD | grep -m 1 stopped; do echo "Waiting..."; sleep 20s; done

#Checking Devices
adb devices

# Clear and capture logcat
#adb -e logcat -c
#adb -e logcat > build/logcat.log &

./gradlew app:clean app:check app:test app:connectedAndroidTest -i

# Stop the background processes
kill -9 $EMULATOR_PID

#Sleeping for a few seconds
sleep 10s

#Checking Devices
adb devices
