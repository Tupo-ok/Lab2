cmake_minimum_required(VERSION 3.22.1)

# Declares and names the project.

project("lab1")

add_library( # Sets the name of the library.
        lab1

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp)

add_library(spdlog STATIC IMPORTED)
set_target_properties(spdlog PROPERTIES IMPORTED_LOCATION
        /Users/katek/AndroidStudioProjects/spdlog/build/arm64-v8a/libspdlog.a)

add_library(mbedcrypto STATIC IMPORTED)
set_target_properties(mbedcrypto PROPERTIES IMPORTED_LOCATION
        /Users/katek/AndroidStudioProjects/mbedtls/build/arm64-v8a/library/libmbedcrypto.so)

target_include_directories(lab1 PUBLIC
        /Users/katek/AndroidStudioProjects/spdlog/spdlog/include
        /Users/katek/AndroidStudioProjects/mbedtls/mbedtls/include)

find_library(
        log-lib
        log)

target_link_libraries( # Specifies the target library.
        lab1
        mbedcrypto
        spdlog
        ${log-lib})