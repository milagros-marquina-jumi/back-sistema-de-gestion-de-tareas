//// Para poder ejecutar en un Docker ////

1. Ir a frontend y ejecutar en el CMD:
   npm i
   ng build --configuration=production

2. Ir ruta backend y ejecutar en el CMD:
   mvn clean install -DskipTests

   docker-compose up --build

3. Copiar URL en el navegador:
   http://localhost:4200/
