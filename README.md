# kotlin-exposed-example
Example of Exposed database framework. DSL and DAO 


Run database in docker `docker run --name exposed-example-db -p 7878:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -d postgres`

docker rm -f exposed-example-db

##  Sql
CREATE SEQUENCE Customer_Id_Seq START 50001;
DROP SEQUENCE IF EXISTS Customer_Id_Seq;


SELECT nextval ('Customer_Id_Seq');
select nextval ('orders_order_id_seq');