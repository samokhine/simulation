The minimum requirements to build the code:

1) Apache Maven 3+
2) Java 1.7+

To test and build:

mvn clean install

To run:

java -jar target/simulation.jar src/test/resources/field.txt src/test/resources/script.txt

Main assumptions:

1) The vehicle can go outside of the cube
2) Don't fail on invalid instructions 

