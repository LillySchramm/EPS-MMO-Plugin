#updatating the system

apt-get update
apt-get upgrade -y

#installing dependencies

apt-get install openjdk-8-jdk-headless screen git

#installing the spigot server

wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
java -Xms512M -Xmx512M -jar BuildTools.jar --rev 1.12.2

echo "eula=true" > eula.txt
echo "java -Xms1G -Xmx1G -jar spigot-1.12.2.jar" > start.sh

chmod +x ./*
