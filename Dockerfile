FROM tomcat
WORKDIR /usr/local/tomcat
RUN mkdir uploads
COPY ./target/sample.war ./webapps/ROOT.war
RUN ls -la
EXPOSE 8080
