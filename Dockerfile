FROM openjdk:8
EXPOSE 8089
ADD target/gestion-station-ski-1.0.jar KhaledSALHI-5SAE6-G7-skiStation.jar
ENTRYPOINT ["java","-jar","KhaledSALHI-5SAE6-G7-skiStation.jar"]