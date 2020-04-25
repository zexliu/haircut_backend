FROM openjdk:8-jre

MAINTAINER liuze liu.ze@qq.com

COPY build/libs/haircut-0.0.1-SNAPSHOT.jar /app.jar

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

EXPOSE 80

ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]