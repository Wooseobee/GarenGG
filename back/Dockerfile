#base image
FROM openjdk:17

# 컨테이너 내부에서 애플리케이션을 실행할 포트 지정
EXPOSE 8080

# 빌드된 애플리케이션 JAR 파일을 이미지 내부로 복사
ADD ./build/libs/back-0.0.1-SNAPSHOT.jar app.jar

# 애플리케이션 실행 방식
ENTRYPOINT ["java", "-jar", "/app.jar"]
