# LambdaAthenaJDBC

A Sample Lmabda function for executing Athena Queries

 mvn install:install-file -Dfile=AthenaJDBC41-1.0.0.jar -DgroupId=com.amazonaws.athena.jdbc.AthenaDriver -DartifactId=AthenaDriver -Dversion=1.0.0 -Dpackaging=jar
 
aws lambda create-function \
    --function-name athena-jdbc \
    --runtime java8 \
    --zip-file fileb:///path/to/LambdaAthenaJDBC-1.0-SNAPSHOT.jar \
    --handler com.tayo.AthenaJDBCConnection::handleRequest \
    --role arn:aws:iam::<account-number>:role/lambda_basic_execution \
    --timeout 60 \
    --memory-size 512 \
    --environment Variables={myathenastg=s3://my-bucket/athena_results}