FROM alpine:3.19 as main

FROM container-registry.oracle.com/graalvm/native-image:21-ol9 as base
RUN microdnf install findutils gcc gcc-c++ make wget

WORKDIR /opt

# https://www.graalvm.org/latest/reference-manual/native-image/guides/build-static-executables/
RUN wget https://more.musl.cc/10/x86_64-linux-musl/x86_64-linux-musl-native.tgz
RUN tar -xzf x86_64-linux-musl-native.tgz

ENV TOOLCHAIN_DIR "/opt/x86_64-linux-musl-native"
ENV PATH "${TOOLCHAIN_DIR}/bin:${PATH}"
ENV CC "${TOOLCHAIN_DIR}/bin/gcc"

ARG ZLIB_VERSION=1.3.1
RUN wget https://zlib.net/zlib-${ZLIB_VERSION}.tar.gz
RUN tar -xzf zlib-${ZLIB_VERSION}.tar.gz
RUN cd zlib-${ZLIB_VERSION} && ./configure --prefix=$TOOLCHAIN_DIR --static && make && make install


# ----------------------------------------------------------------------------------------------------------------------
# 의존성 설치
# ----------------------------------------------------------------------------------------------------------------------
FROM base AS deps
WORKDIR /app
COPY gradle gradle
COPY settings.gradle build.gradle gradlew ./

RUN chmod +x gradlew && ./gradlew dependencies --no-daemon


# ----------------------------------------------------------------------------------------------------------------------
# 빌드
# ----------------------------------------------------------------------------------------------------------------------
FROM deps AS build

COPY . .

RUN ./gradlew nativeCompile -xtest --no-daemon


# ----------------------------------------------------------------------------------------------------------------------
# 실행
# ----------------------------------------------------------------------------------------------------------------------
FROM main
WORKDIR /app
# 코드 COPY
COPY --from=build /app/build/native/nativeCompile/main /app/main

# Port
EXPOSE 8080

ENTRYPOINT ["./main"]
